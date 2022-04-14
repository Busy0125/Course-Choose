package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {
    @Select("select id from student")
    List<String> findAllId();

    @Update("update student set gpa = round(#{gpa},2) where id = #{id}")
    Integer updateById(String id, Double gpa);
}
