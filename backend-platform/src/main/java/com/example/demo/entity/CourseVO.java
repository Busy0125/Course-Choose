package com.example.demo.entity;

import lombok.Data;

@Data
public class CourseVO {
    private String id;
    private String name;
    private Integer credit;
    private String departmentName;

    public CourseVO(Course course, Department department){
        this.id=course.getId();
        this.name= course.getName();
        this.credit=course.getCredit();
        this.departmentName=department.getDepartmentName();
    }
}
// by zmm
// 通过使用该实体，可以在后端实现department转departmentName
// 转换已在前端实现，该实体没有使用
