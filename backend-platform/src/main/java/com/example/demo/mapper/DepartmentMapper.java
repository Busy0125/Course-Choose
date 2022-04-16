package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Department;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DepartmentMapper extends BaseMapper<Department> {
    @Select("select * from department where department = #{dep}")
    List<Department> findDepartmentNameById(Integer dep);

    @Select("select count(*) from department where department = #{department}")
    Integer isKeyRepeat(Integer id);

    // 添加部门
    Integer insertDepartment(Integer department, String departmentName);

    // 编辑部门
    Integer updateDepartment(Integer department, String departmentName);

    // 删除部门
    Integer deleteDepartment(Integer department);

    // 调用存储过程，返回学院学生数，教师数，课程数
    void getDepartmentInfo(Map<String, Object> map);
}
