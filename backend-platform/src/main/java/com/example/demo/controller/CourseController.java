package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseVO;
import com.example.demo.entity.Department;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.mapper.DepartmentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    CourseMapper courseMapper;

    @Resource
    DepartmentMapper departmentMapper;

    @PostMapping
    public Result<?> save(@RequestBody Course course) {
        courseMapper.insert(course);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "0") Integer selectDep) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Course::getName, search);
        }
        if (selectDep.intValue() != 0) {
            wrapper.eq(Course::getDepartment, selectDep);
        }
        Page<Course> coursePage = courseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(coursePage);

//        return Result.success(queryCourseDe());
    }

    @PutMapping
    public Result<?> update(@RequestBody Course course) {
        courseMapper.updateById(course);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        courseMapper.deleteById(id);
        return Result.success();
    }


    // 该函数能够将course实体中department字段中数字转换为汉字，方便在前端展示。
    // 但使用后分页问题无法解决，因此没有使用该函数，转换在前端想办法完成了
    public List<CourseVO> queryCourseDe() {
        List<Course> courses = courseMapper.selectList(null);
        List<CourseVO> courseVOS = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            Department department = departmentMapper.selectById(course.getDepartment());
            CourseVO courseVO = new CourseVO(course, department);

            courseVOS.add(courseVO);

        }
        return courseVOS;
    }

    @GetMapping("/withName")
    public Result<?> findAllWithName() {
        return Result.success(courseMapper.findAllCourseWithName());
    }

    @GetMapping("/getCredit")
    public Result<?> findAllCredit() {
        return Result.success(courseMapper.findCredit());
    }
}
