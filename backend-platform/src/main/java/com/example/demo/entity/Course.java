package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("course")
@Data
public class Course {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private Integer credit;
    private Integer department;
}
