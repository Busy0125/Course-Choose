package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.CreditAndGrade;
import com.example.demo.entity.Student;
import com.example.demo.mapper.GradeMapper;
import com.example.demo.mapper.StudentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    StudentMapper studentMapper;

    @Resource
    GradeMapper gradeMapper;

    @PostMapping
    public Result<?> save(@RequestBody Student student) {
        if (studentMapper.isKeyRepeat(student.getId()).intValue() != 0) {
            return Result.error("-1", "学号已存在");
        }
        if (student.getPassword() == null) {
            student.setPassword(student.getId());
        }
        if (student.getGpa() == null) {
            student.setGpa(0.0);
        }
        studentMapper.insert(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        studentMapper.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Student student) {
        // 前端传过来的数据password和gpa两个字段为null。
        // 下列操作可以保证插入数据库的数据中password和gap两字段值不为null。
        // 后来发现包含null字段的实体插入数据库时，null不会覆盖原有数据，或许是框架自身功能，因此这一步可以不做
        // Student fromDb = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, student.getId()));
        // student.setPassword(fromDb.getPassword());
        // student.setGpa(fromDb.getGpa());

        // 前端表单中学号只读，理论上不会出现主键重复
//        if (studentMapper.isKeyRepeat(student.getId()).intValue() != 0) {
//            return Result.error("-1", "学号已存在");
//        }
        studentMapper.updateById(student);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "0") Integer selectDep) {
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Student::getName, search);
        }
        if (selectDep.intValue() != 0) {
            wrapper.eq(Student::getDepartment, selectDep);
        }
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(studentPage);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody Student student) {
        Student res = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getId, student.getId()).eq(Student::getPassword, student.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/getGpa")
    private Result<?> getGpa() {
        // 先获取学号集
        List<String> IdSet = studentMapper.findAllId();// [19122169, 19122170, 19122171, ...]
        List<CreditAndGrade> gradeSet;
        Double gpa;
        Integer totalCredit;
        for (int i = 0; i < IdSet.size(); i++) {
            // 计算当前学生的绩点
            gpa = 0.;

            // 获取当前学生的综合成绩集
            gradeSet = gradeMapper.findCertainGradeSet(IdSet.get(i));

            // 算绩点
            for (int j = 0; j < gradeSet.size(); j++) {
                gpa += getSingleGpa(gradeSet.get(j).getTotalGrade()) * gradeSet.get(j).getCredit();
            }
            // 计算总学分
            totalCredit = 0;
            for (int j = 0; j < gradeSet.size(); j++) {
                totalCredit += gradeSet.get(j).getCredit();
            }
            if (totalCredit.intValue() != 0) {
                gpa /= totalCredit;
            }
            // 小数点位数保留在sql中完成
            studentMapper.updateGpaById(IdSet.get(i), gpa);
        }
        return Result.success();
    }

    private Double getSingleGpa(Integer t) {
        if (t >= 90 && t <= 100) {
            return 4.0;
        } else if (t >= 85 && t <= 89) {
            return 3.7;
        } else if (t >= 82 && t <= 84) {
            return 3.3;
        } else if (t >= 78 && t <= 81) {
            return 3.0;
        } else if (t >= 75 && t <= 77) {
            return 2.7;
        } else if (t >= 72 && t <= 74) {
            return 2.3;
        } else if (t >= 68 && t <= 71) {
            return 2.0;
        } else if (t >= 66 && t <= 67) {
            return 1.7;
        } else if (t >= 64 && t <= 65) {
            return 1.5;
        } else if (t >= 60 && t <= 63) {
            return 1.0;
        } else if (t >= 0 && t <= 59) {
            return 0.;
        }
        return 0.;
    }
}
