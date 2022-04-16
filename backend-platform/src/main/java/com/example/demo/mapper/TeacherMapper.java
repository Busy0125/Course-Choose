package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CourseWithName;
import com.example.demo.entity.Teacher;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select id,name from teacher")
    List<CourseWithName> findAllTeacherWithName();

    @Select("select count(*) from teacher where id = #{id}")
    Integer isKeyRepeat(String id);
}
