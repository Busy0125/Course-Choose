package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("grade")
@Data
public class Grade {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String studentId;
    private String term;
    private String courseId;
    private String teacherId;
    private String time;
    private Integer usualGrade;
    private Integer finalGrade;
    private Integer totalGrade;
}
