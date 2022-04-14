package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Department;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DepartmentMapper extends BaseMapper<Department> {
    @Select("select * from department where department = #{dep}")
    List<Department> findDepartmentNameById(Integer dep);
}
