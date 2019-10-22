package com.strady.rabbit.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: strady
 * @Date: 2019-10-19
 * @Time: 23:27:46
 * @Description:
 */
@Data
public class User implements Serializable {

    private String id;

    private String userName;

    private Integer age;

    private Long createTime;
}
