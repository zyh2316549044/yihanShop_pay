package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.dao.MallOrderDao;
import com.demo.yihan_shop.dao.MallOrderItemDao;
import com.demo.yihan_shop.dao.MallProductDao;
import com.demo.yihan_shop.dao.MallShippingDao;
import com.demo.yihan_shop.entity.*;
import com.demo.yihan_shop.enums.OrderStatusEnum;
import com.demo.yihan_shop.enums.PaymentTypeEnum;
import com.demo.yihan_shop.enums.ProductEnum;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.service.MallCartService;
import com.demo.yihan_shop.service.MallOrderService;
import com.demo.yihan_shop.vo.OrderItemVo;
import com.demo.yihan_shop.vo.OrderVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (MallOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-05-11 21:04:28
 */
@Service("mallOrderService")
public class MallOrderServiceImpl implements MallOrderService {
    @Resource
    private MallOrderDao mallOrderDao;

    @Autowired
    private MallShippingDao mallShippingDao;

    @Autowired
    private MallCartService mallCartService;

    @Autowired
    private MallProductDao mallProductDao;

    @Autowired
    private MallOrderItemDao mallOrderItemDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallOrder queryById(Integer id) {
        return this.mallOrderDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MallOrder> queryAllByLimit(int offset, int limit) {
        return this.mallOrderDao.queryAllByLimit(offset, limit);
    }

    /***
     * 添加订单
     * @param uid 用户id
     * @param shippingId 购物车id
     * @return
     */
    @Override
    @Transactional//默认出现RuntimeExeception才会回归
    public ResponseVo<OrderVo> insert(Integer uid, Integer shippingId) {
        //收货地址校验
        MallShipping mallShipping = mallShippingDao.queryByIdandUidAndShipping(uid, shippingId);
        if (mallShipping == null){
            return ResponseVo.errer(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车校验
        //List<MallCart> listForCart(Integer uid)将redis中的数据取出封装到mallCart对象中创建程流
        List<MallCart> collect = mallCartService.listForCart(uid).stream()
                //将redis中mallCart对象中选中的商品拿出来
                .filter(MallCart::getProductSelected)
                //封装到一个list集合中
                .collect(Collectors.toList());
        //如果为空就是用户未选择商品
        if (CollectionUtils.isEmpty(collect)){
            return ResponseVo.errer(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        //通过productId获取collect的id封装到set上进行一个去重
        Set<Integer> productSet = collect.stream()
                .map(MallCart::getProductId)//将MallCart的id拿出来,在一个一个映射到
                .collect(Collectors.toSet());//这里这个set上
        List<MallProduct> mallProductSet = mallProductDao.queryAllByProductId(productSet);

        //将查出的和product对应在map中
        Map<Integer, MallProduct> mallProductMap = mallProductSet.stream().collect(Collectors.toMap(MallProduct::getId, mallProduct -> mallProduct));

        List<MallOrderItem> mallOrderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        //检查数据库中是否有这个商品,是否库存充足
        for (MallCart mallCart : collect) {
            MallProduct product = mallProductMap.get(mallCart.getProductId());
            //是否有这个商品
            if (product == null){
                return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在"+mallCart.getProductId());
            }
            //商品上下架状态
            if (!ProductEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_EXIST,"商品不是在售状态"+product.getName());
            }

            //是否库存充足
            if (product.getStock()<mallCart.getQuantity()){
                return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_STACT,"库存不正确"+product.getName());
            }

            MallOrderItem mallOrderItem = buildOrderItem(uid, orderNo, mallCart.getQuantity(), product);
            mallOrderItemList.add(mallOrderItem);

            //减库存(原库存减去购买的件数)
            product.setStock(product.getStock() - mallCart.getQuantity());
            int insert = mallProductDao.update(product);
            if (insert <= 0){
                return ResponseVo.errer(ResponseEnum.ERROR);
            }
        }

        //计算总价，只计算选中的商品
        //生成订单，同时入库：order和order_item，使用事务
        MallOrder mallOrder = buildOrder(uid, orderNo, shippingId, mallOrderItemList);
        int row = mallOrderDao.insert(mallOrder);
        if (row <= 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }

        int batchInsert = mallOrderItemDao.batchInsert(mallOrderItemList);
        if (batchInsert <= 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }

        //更新购物车（选中的商品）
        //Redis事务（打包命令）不能回滚
        for (MallCart mallCart : collect) {
            mallCartService.delete(uid,mallCart.getProductId());

        }

        //构造orderVo
        OrderVo orderVo = buildOrderVo(mallOrder, mallOrderItemList, mallShipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public MallOrder update(MallOrder mallOrder) {
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }


    /**
     * 查询订单列表
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        //查出订单表数据
        List<MallOrder> mallOrders = mallOrderDao.queryAllByLimitByUid(uid, pageNum, pageSize);
        //将OrderNo拿出来放入set去重复
        Set<Long> orderSet = mallOrders.stream().map(MallOrder::getOrderNo).collect(Collectors.toSet());

        //查询MallOrderItem里面有没有对应的
        List<MallOrderItem> mallOrderItemList = mallOrderItemDao.selectByOrderNoSet(orderSet);
        //list转map
        Map<Long, List<MallOrderItem>> ordermap = mallOrderItemList.stream().collect(Collectors.groupingBy(MallOrderItem::getOrderNo));
        //将shippingId拿出来放入set去重复
        Set<Integer> shippingId = mallOrders.stream().map(MallOrder::getShippingId).collect(Collectors.toSet());
        List<MallShipping> mallShippings = mallShippingDao.selectBySet(shippingId);
        Map<Integer, MallShipping> mallShippingMap = mallShippings.stream().collect(Collectors.toMap(MallShipping::getId, mallShipping -> mallShipping));

        List<OrderVo> orderVoList = new ArrayList<>();

        for (MallOrder mallOrder : mallOrders) {
            OrderVo orderVo = buildOrderVo(mallOrder,
                    ordermap.get(mallOrder.getOrderNo()),
                    mallShippingMap.get(mallOrder.getShippingId()));
            orderVoList.add(orderVo);

        }


        PageInfo pageInfo = new PageInfo(mallOrderItemList);

        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    /***
     * 查询订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {

        MallOrder mallOrder = mallOrderDao.selectByOrderNo(orderNo);
        //判断订单是不是这个人的
        if(mallOrder == null ||  !mallOrder.getUserId().equals(uid)){
            return ResponseVo.errer(ResponseEnum.ORDER_NULL);
        }
        Set<Long> orderNoset = new HashSet<>();
        List<MallOrderItem> mallOrderItemList = mallOrderItemDao.selectByOrderNoSet(orderNoset);
        MallShipping mallShipping = mallShippingDao.queryById(mallOrder.getShippingId());
        OrderVo orderVo = buildOrderVo(mallOrder,mallOrderItemList,mallShipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        MallOrder mallOrder = mallOrderDao.selectByOrderNo(orderNo);
        //判断订单是不是这个人的
        if(mallOrder == null || !mallOrder.getUserId().equals(uid)){
            return ResponseVo.errer(ResponseEnum.ORDER_NULL);
        }
        //只有未付款订单可以取消,看需求写
        if (!mallOrder.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.errer(ResponseEnum.ORDER_STATUS_ERROR);
        }

        //可以取消的话将其设置成已经取消
        mallOrder.setStatus(OrderStatusEnum.CANCELED.getCode());
        //更新时间
        mallOrder.setCloseTime(new Date());
        int row = mallOrderDao.update(mallOrder);
        if (row <= 0){
            return ResponseVo.errer(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    //修改订单状态
    @Override
    public void paid(Long orderNo) {
        MallOrder order = mallOrderDao.selectByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getMsg() + "订单id:" + orderNo);
        }
        //只有[未付款]订单可以变成[已付款]，看自己公司业务
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getMsg() + "订单id:" + orderNo);
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = mallOrderDao.update(order);
        if (row <= 0) {
            throw new RuntimeException("将订单更新为已支付状态失败，订单id:" + orderNo);
        }
    }


    private OrderVo buildOrderVo(MallOrder order, List<MallOrderItem> orderItemList, MallShipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> OrderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(OrderItemVoList);

        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setMallShipping(shipping);
        }

        return orderVo;
    }


    private MallOrder buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<MallOrderItem> orderItemList) {
        BigDecimal payment = orderItemList.stream()
                .map(MallOrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        MallOrder order = new MallOrder();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }



    /**
     * 企业级：分布式唯一id/主键
     * @return
     */
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }


    private MallOrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, MallProduct product) {
        MallOrderItem item = new MallOrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
