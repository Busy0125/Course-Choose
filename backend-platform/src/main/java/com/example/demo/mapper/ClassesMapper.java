package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Classes;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClassesMapper extends BaseMapper<Classes> {
    @Select("select c.id,c.term,c.course_id,c.course_name,c.teacher_id,c.teacher_name,c.time from classes as c join course " +
            "on course.id = c.course_id " +
            "and c.course_name like concat('%', #{search}, '%') " +
            "limit #{pageNum}, #{pageSize}")
    List<Classes> findPageBySearch(Integer pageNum, Integer pageSize, String search);


    @Select("select count(*) from classes as c join course " +
            "on course.id = c.course_id " +
            "and c.course_name like concat('%', #{search}, '%') " +
            "limit #{pageNum}, #{pageSize}")
    Integer selectTotalBySearch(Integer pageNum, Integer pageSize, String search);
    // 以上两个方法为实现分页效果而存在，已废弃


    @Select("select c.id,c.term,c.course_id,c.course_name,c.teacher_id,c.teacher_name,c.time,c.limit_num,c.current_num from classes as c join course " +
            "on course.id = c.course_id " +
            "and c.course_name like concat('%', #{search}, '%') ")
    List<Classes> findListBySearch(String search);

    @Select("select c.id,c.term,c.course_id,c.course_name,c.teacher_id,c.teacher_name,c.time,c.limit_num,c.current_num from classes as c join course " +
            "on course.id = c.course_id and course.department = #{selectDep} " +
            "and c.course_name like concat('%', #{search}, '%') ")
    List<Classes> findListBySearchAndDep(String search, Integer selectDep);

    @Select("select count(*) from classes as c join course " +
            "on course.id = c.course_id " +
            "and c.course_name like concat('%', #{search}, '%') ")
    Integer selectListTotalBySearch(String search);

    @Select("select count(*) from classes as c join course " +
            "on course.id = c.course_id and course.department = #{selectDep} " +
            "and c.course_name like concat('%', #{search}, '%') ")
    Integer selectListTotalBySearchAndDep(String search, Integer selectDep);

    @Select("select c.term,c.course_id,c.course_name,c.teacher_id,c.teacher_name,c.time " +
            "from classes as c join grade as g on g.student_id = #{studentId} " +
            "and c.term = g.term and c.course_id = g.course_id and c.teacher_id = g.teacher_id " +
            "and c.course_name like concat('%', #{search}, '%') " +
            "and c.time = g.time")
    List<Classes> findListForQuit(String studentId, String search);

    @Select("select count(*) " +
            "from classes as c join grade as g on g.student_id = #{studentId} " +
            "and c.term = g.term and c.course_id = g.course_id and c.teacher_id = g.teacher_id " +
            "and c.course_name like concat('%', #{search}, '%') " +
            "and c.time = g.time")
    Integer selectListTotalForQuit(String studentId, String search);

    @Select("select teacher_id,teacher_name,term,course_id,course_name,time,limit_num,current_num from classes " +
            "where teacher_id = #{teacherId} " +
            "and course_name like concat('%', #{search}, '%') ")
    List<Classes> findListByTeacherId(String search, String teacherId);

    @Select("select distinct term from classes")
    List<String> findAllTermForStudent();

    @Select("select c.term,c.course_id,c.course_name,c.teacher_id,c.teacher_name,c.time " +
            "from classes as c join grade as g on g.student_id = #{studentId} " +
            "and c.term = g.term and c.course_id = g.course_id and c.teacher_id = g.teacher_id " +
            "and c.term like concat('%', #{term}, '%') " +
            "and c.time = g.time")
    List<Classes> findListForStudentDisplay(String studentId, String term);

    @Select("select count(*) from classes where term = #{term} and time = #{time} " +
            "and teacher_id = #{teacherId}")
    Integer findIsConflicting(String term, String time, String teacherId);

    @Select("select count(*) from classes where id = #{id}")
    Integer isKeyRepeat(String id);

    // 检查选课人数是否已满
    @Select("select count(*) from classes where term = #{term} and teacher_id = #{teacherId} " +
            "and course_id = #{courseId} and time = #{time} " +
            "and limit_num = current_num")
    Integer isFull(String term, String courseId, String teacherId, String time);

    // 新增一名学生
    @Update("update classes set current_num = current_num + 1 where term = #{term} and " +
            "teacher_id = #{teacherId} and course_id = #{courseId} and time = #{time}")
    void newStudent(String term, String courseId, String teacherId, String time);

    // 删除一名学生
    @Update("update classes set current_num = current_num - 1 where term = #{term} and " +
            "teacher_id = #{teacherId} and course_id = #{courseId} and time = #{time}")
    void deleteOneStudent(String term, String courseId, String teacherId, String time);
}
