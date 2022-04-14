package com.example.demo.entity;

import lombok.Data;

@Data
public class GradeWithStudentName {
    private String id;
    private String name;
    private Integer usualGrade;
    private Integer finalGrade;
    private Integer totalGrade;
}
