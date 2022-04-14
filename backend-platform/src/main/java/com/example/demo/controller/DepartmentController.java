package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.mapper.DepartmentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
}
