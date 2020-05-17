package com.demo.yihan_shop.service.impl;

import com.demo.yihan_shop.dao.MallUserDao;
import com.demo.yihan_shop.entity.MallUser;
import com.demo.yihan_shop.service.MallUserService;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.demo.yihan_shop.enums.ResponseEnum.*;

/**
 * (MallUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 21:21:52
 */
@Service("mallUserService")
public class MallUserServiceImpl implements MallUserService {
    @Resource
    private MallUserDao mallUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MallUser queryById(Integer id) {
        return this.mallUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<MallUser> queryAllByLimit(int offset, int limit) {
        return this.mallUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mallUser 实例对象
     * @return 实例对象
     */
    @Override
    public MallUser insert(MallUser mallUser) {
        this.mallUserDao.insert(mallUser);
        return mallUser;
    }

    /**
     * 修改数据
     *
     * @param mallUser 实例对象
     * @return 实例对象
     */
    @Override
    public MallUser update(MallUser mallUser) {
        this.mallUserDao.update(mallUser);
        return this.queryById(mallUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.mallUserDao.deleteById(id) > 0;
    }

    /**
     *注册功能实现
     * @param mallUser 用户表
     */
    @Override
    public ResponseVo<MallUser> registered(MallUser mallUser) {
        mallUser.setRole(1);

        //查询用户名是否重复
        int countByUserName = mallUserDao.countByUserName(mallUser.getUsername());//将username从前端拿过来
        if (countByUserName > 0){
            return ResponseVo.errer(USERNAME_EXIST);
        }
        //查询邮箱是否重复
        int countByEmail = mallUserDao.countByEmail(mallUser.getEmail());
        if (countByEmail > 0){
            return ResponseVo.errer(EMAIL_EXIST);
        }
        //密码MD5加密（DigestUtils是spring提供的MD5加密）
        //将前端密码拿回来进行md5加密存入数据库
        mallUser.setPassword(DigestUtils.md5DigestAsHex(mallUser.getPassword().getBytes(StandardCharsets.UTF_8)));

        int insert = mallUserDao.insert(mallUser);
        if (insert == 0){
            return ResponseVo.errer(ERROR);
        }

        return ResponseVo.success();

    }

    @Override
    public ResponseVo<MallUser> login(String username,String password) {
        MallUser mallUser = mallUserDao.selectUserName(username);
        if (mallUser == null){
            return ResponseVo.errer(USERNAME_OR_PASSWORD_ERROR);
        }
        //equalsIgnoreCase比较时不区分大小写
        //mallUser.getPassword()是数据库查出来的数据，password是从前端接收的数据
        if (!mallUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            return ResponseVo.errer(USERNAME_OR_PASSWORD_ERROR);
        }
        return ResponseVo.success(mallUser);
    }
}
