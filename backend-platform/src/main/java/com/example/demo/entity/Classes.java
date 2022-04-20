package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("classes")
@Data
public class Classes {
    @TableId(type = IdType.AUTO)
    private String id;
    private String term;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
    private String time;
    private Integer limitNum;
    private Integer currentNum;
}
