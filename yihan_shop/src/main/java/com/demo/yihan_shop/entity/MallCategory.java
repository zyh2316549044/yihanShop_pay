package com.demo.yihan_shop.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (MallCategory)实体类
 *
 * @author makejava
 * @since 2020-04-28 21:19:26
 */
@Data

public class MallCategory implements Serializable {
    private static final long serialVersionUID = 624153101630201153L;
    /**
    * 类别Id
    */
    private Integer id;
    /**
    * 父类别id当id=0时说明是根节点,一级类别
    */
    private Integer parentId;
    /**
    * 类别名称
    */
    private String name;
    /**
    * 类别状态1-正常,2-已废弃
    */
    private Object status;
    /**
    * 排序编号,同类展示顺序,数值相等则自然排序
    */
    private Integer sortOrder;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;



}
