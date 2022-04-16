package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseWithCredit;
import com.example.demo.entity.CourseWithName;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {
    @Select("select id,name from course")
    List<CourseWithName> findAllCourseWithName();

    @Select("select id,credit from course")
    List<CourseWithCredit> findCredit();

    @Select("select count(*) from course where id = #{id}")
    Integer isKeyRepeat(String id);
}
