package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.dao.MallProductDao;
import com.demo.yihan_shop.entity.MallCart;
import com.demo.yihan_shop.entity.MallProduct;
import com.demo.yihan_shop.enums.ProductEnum;
import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.form.CartAddForm;
import com.demo.yihan_shop.form.CartUpdateForm;
import com.demo.yihan_shop.service.MallCartService;
import com.demo.yihan_shop.vo.CartProductVo;
import com.demo.yihan_shop.vo.CartVo;
import com.demo.yihan_shop.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-07 16:56
 * @version: 1.0
 */
@Service("mallCartService")
public class MallCartServiceImpl implements MallCartService {

    //写入redis里面的key值，写成动态的
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Resource
    private MallProductDao mallProductDao;

    @Autowired
    //spring自带的redis封装
    private StringRedisTemplate stringRedisTemplate;

    //导入谷歌的pojo转json
    private Gson gson = new Gson();

    /**
     *
     * 添加购物车
     * @param uid
     * @param cartAddForm
     * @return
     */
    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        Integer quantity=1;//添加到购物车数量

        MallProduct mallProduct = mallProductDao.queryById(cartAddForm.getProductId());

        //判断商品是否存在
        if(mallProduct == null){
            return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品是否在售
        if(!mallProduct.getStatus().equals(ProductEnum.ON_SALE.getCode())){
            return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品库存是否充足
        if (mallProduct.getStock()<= 0){
            return ResponseVo.errer(ResponseEnum.PRODUCT_NOT_STACT);
        }

        //写入redis（使map）
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        //redis里面的key
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        MallCart mallCart;
        //redis里面的value,获取当前的一个数据
        String value = hashOperations.get(redisKey,String.valueOf(mallProduct.getId()));
        if (StringUtils.isEmpty(value)){
            //购物车中没有商品，添加一个
            mallCart = new MallCart(mallProduct.getId(),quantity,cartAddForm.getSelect());
        }else {
            //有商品就再增加一个数量
            mallCart = gson.fromJson(value,MallCart.class);
            mallCart.setQuantity(mallCart.getQuantity()+quantity);
        }

        //将数据添加到redis
        hashOperations.put(redisKey,String.valueOf(mallProduct.getId()),gson.toJson(mallCart));


         return list(uid);
    }

/***
     * 将购物车列表显示出来
     * @param uid
     * @return
     */

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //获取redis所有数据
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();


        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            //拿到key
            Integer productId = Integer.valueOf(entry.getKey());
            //拿到cart对象
            MallCart cart = gson.fromJson(entry.getValue(), MallCart.class);

            //TODO 需要优化，使用mysql里的in
            //将数据库拿出来
            MallProduct product = mallProductDao.queryById(productId);
            if (product != null) {
                //赋值给CartProductVo
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );

                cartProductVoList.add(cartProductVo);

                //是否选中
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }

                //计算总价(只计算选中的)
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }

            cartTotalQuantity += cart.getQuantity();
        }

        //有一个没有选中，就不叫全选
        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 报错
            return ResponseVo.errer(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        //已经有了，修改内容
        MallCart cart = gson.fromJson(value, MallCart.class);
        if (form.getQuantity() != null
                && form.getQuantity() >= 0) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setProductSelected(form.getSelected());
        }

        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品, 报错
            return ResponseVo.errer(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }


    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (MallCart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (MallCart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    gson.toJson(cart));
        }

        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(MallCart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    //获取购物车
    public List<MallCart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey  = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<MallCart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), MallCart.class));
        }

        return cartList;
    }

}
