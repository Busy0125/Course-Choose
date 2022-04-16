package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Department;
import com.example.demo.mapper.DepartmentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    DepartmentMapper departmentMapper;

    @GetMapping
    public Result<?> getDepartment() {
        return Result.success(departmentMapper.selectList(null));
    }

    @GetMapping("/forHeader")
    public Result<?> getDepartmentNameById(@RequestParam(defaultValue = "0") Integer dep) {
        return Result.success(departmentMapper.findDepartmentNameById(dep));
    }

    @PostMapping
    public Result<?> save(@RequestBody Department department) {
        if (departmentMapper.isKeyRepeat(department.getDepartment()).intValue() != 0) {
            return Result.error("-1", "院系号已存在");
        }
        return Result.success(departmentMapper.insertDepartment(department.getDepartment(), department.getDepartmentName()));
    }

    @PutMapping
    public Result<?> update(@RequestBody Department department) {
        return Result.success(departmentMapper.updateDepartment(department.getDepartment(), department.getDepartmentName()));
    }

    @DeleteMapping("/{department}")
    public Result<?> delete(@PathVariable Integer department) {
        return Result.success(departmentMapper.deleteDepartment(department));
    }

    @GetMapping("/getInfo")
    public Result<?> getInfo() {
        List<Department> data = departmentMapper.selectList(null);
        List<Map<String, Object>> res = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = new HashMap();
            map.put("department",data.get(i).getDepartment());
            map.put("depStudentNum",null);
            map.put("depTeacherNum",null);
            map.put("depCourseNum",null);
            departmentMapper.getDepartmentInfo(map);
            map.put("departmentName",data.get(i).getDepartmentName());
            res.add(map);
        }
        return Result.success(res);
    }
}
