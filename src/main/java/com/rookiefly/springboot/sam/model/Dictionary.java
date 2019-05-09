package com.rookiefly.springboot.sam.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 字典键
     */
    private String key;

    /**
     * 字典键名
     */
    private String keyName;

    /**
     * 字典值名
     */
    private String valueName;

    /**
     * 字典值别名
     */
    private String valueSlug;

    /**
     * 字典值
     */
    private String value;

    /**
     * 是否可编辑
     */
    private Integer editable;

    /**
     * 字典分类 id
     */
    private Long dictionaryCategoryId;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreated;
}