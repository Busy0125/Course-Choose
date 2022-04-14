package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("student")
@Data
public class Student {
    @TableId(type = IdType.AUTO)
    private String id;
    private String password;
    private String name;
    private String sex;
    private Integer department;
    private Double gpa;
}
