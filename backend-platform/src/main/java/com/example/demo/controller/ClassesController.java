package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Classes;
import com.example.demo.mapper.ClassesMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classes")
public class ClassesController {
    @Resource
    ClassesMapper classesMapper;

    //该方法返回到前端的是HashMap，出现未知原因无法实现分页，往后的页面若存在多表查询，则抛弃分页效果
    @GetMapping
    public Result<?> findPage(
//            @RequestParam(defaultValue = "1") Integer pageNum,
//            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer selectDep) {
        // 前端传入模糊查询关键词search和院系号dep
        // 使用sql中limit关键字实现分页
        // limit后第一个参数为表起始下标，第二个参数为步长，其中第一个参数需要做一下映射
//        pageNum = (pageNum - 1) * pageSize;

        // 返回给前端的数据中需要包括data和total，使用一个Map来存

        // 之前manage相关操作不需要单独处理total是因为使用了Mybatis-plus自带功能
        // 而此处需要多表查询，可能不适用Mybatis-plus，因此自己编写了sql进行查询，将total和查询结果打包发送至前端
        List<Classes> page = new ArrayList<>();
        Integer total;
//        if (selectDep.intValue() == 0) { // 查询所有
//            page = classesMapper.findPageBySearch(pageNum, pageSize, search);
//            total = classesMapper.selectTotalBySearch(pageNum, pageSize, search);
//        } else { // 按学院查询
//            page = classesMapper.findPageBySearchAndDep(pageNum, pageSize, search, selectDep);
//            total = classesMapper.selectTotalBySearchAndDep(pageNum, pageSize, search, selectDep);
//        }
        if (selectDep.intValue() == 0) { // 查询所有
            page = classesMapper.findListBySearch(search);
            total = classesMapper.selectListTotalBySearch(search);
        } else { // 按学院查询
            page = classesMapper.findListBySearchAndDep(search, selectDep);
            total = classesMapper.selectListTotalBySearchAndDep(search, selectDep);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("page", page);
        res.put("total", total);
        return Result.success(res);
    }

    @GetMapping("/forStudent")
    public Result<?> findClassForStudent(@RequestParam(defaultValue = "") String search,
                                         @RequestParam(defaultValue = "0") Integer dep) {
        List<Classes> data = classesMapper.findListBySearchAndDep(search, dep);
        Integer total = classesMapper.selectListTotalBySearchAndDep(search, dep);
        Map<String, Object> res = new HashMap<>();
        res.put("list", data);
        res.put("total", total);
        return Result.success(res);
    }

    @PostMapping
    public Result<?> save(@RequestBody Classes classes) {
        if (classesMapper.isKeyRepeat(classes.getId()).intValue() != 0) {
            return Result.error("-1", "班级号已存在");
        }
        // 判断时间是否冲突
        Integer res = classesMapper.findIsConflicting(classes.getTerm(), classes.getTime(), classes.getTeacherId());
        if(res.intValue() != 0)
        {
            return Result.error("-1","新增班级失败，该教师时间冲突");
        }
        classesMapper.insert(classes);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Classes classes) {
        // 判断时间是否冲突
        Integer res = classesMapper.findIsConflicting(classes.getTerm(), classes.getTime(), classes.getTeacherId());
        if(res.intValue() != 0)
        {
            return Result.error("-1","更新失败，该教师时间冲突");
        }
        classesMapper.updateById(classes);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) {
        classesMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/forQuit")
    public Result<?> findForQuit(@RequestParam(defaultValue = "") String studentId,
                                 @RequestParam(defaultValue = "") String search) {
        List<Classes> data = classesMapper.findListForQuit(studentId, search);
        Integer total = classesMapper.selectListTotalForQuit(studentId, search);
        Map<String, Object> res = new HashMap<>();
        res.put("list", data);
        res.put("total", total);
        return Result.success(res);
    }

    @GetMapping("/forTeacher")
    private Result<?> findForTeacher(@RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "") String teacherId) {
        List<Classes> data = classesMapper.findListByTeacherId(search, teacherId);
        Integer total = data.size();
        Map<String, Object> res = new HashMap<>();
        res.put("list", data);
        res.put("total", total);
        return Result.success(res);
    }

    @GetMapping("/forStudentDisplay")
    public Result<?> findForStudentDisPlay(@RequestParam(defaultValue = "") String studentId,
                                           @RequestParam(defaultValue = "") String selectTerm) {
        List<Classes> data = classesMapper.findListForStudentDisplay(studentId, selectTerm);
        Integer total = data.size();
        Map<String, Object> res = new HashMap<>();
        res.put("list", data);
        res.put("total", total);
        return Result.success(res);
    }

    @GetMapping("/allTerm")
    private Result<?> findAllTerm() {
        return Result.success(classesMapper.findAllTermForStudent());
    }
}
