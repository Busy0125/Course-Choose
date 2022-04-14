package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("department")
@Data
public class Department {
    @TableId(value = "department", type = IdType.AUTO)
    private Integer department;
    private String departmentName;
}
