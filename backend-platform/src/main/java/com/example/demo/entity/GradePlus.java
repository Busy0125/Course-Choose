package com.example.demo.entity;

import lombok.Data;

@Data
public class GradePlus {
    private String term;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
    private Integer usualGrade;
    private Integer finalGrade;
    private Integer totalGrade;
}
