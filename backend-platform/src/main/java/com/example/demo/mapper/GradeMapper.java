package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CreditAndGrade;
import com.example.demo.entity.Grade;
import com.example.demo.entity.GradePlus;
import com.example.demo.entity.GradeWithStudentName;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GradeMapper extends BaseMapper<Grade> {
    // 该函数返回若非0，说明不满足选课条件
    // 此时未加入时间限制
    @Select("select count(*) from grade where student_id = #{studentId} and term = #{term} and " +
            "time = #{time} and " +
            "course_id = #{courseId} and teacher_id = #{teacherId}")
    Integer findIsChosen(String studentId, String term, String courseId, String teacherId, String time);

    // 加入时间限制
    @Select("select count(distinct c.term,c.time) from classes as c join grade as g " +
            "on g.student_id = #{studentId} and c.term = g.term and c.course_id = g.course_id " +
            "and c.teacher_id = g.teacher_id and c.time = g.time " +
            "and c.term = #{term} and c.time = #{time}")
    Integer findIsConflicting(String studentId, String term, String time);

    // 发起delete请求时，后端无法接受对象参数，原因不明，该方法废弃
    // 出问题可能是由于前端封装时键值没有打双引号，后端SQL语句中字段名与数据库中不匹配，或是此处注解使用错误造成
    // 之后写的方法能够出效果，懒得改了
    @Select("delete from grade where total_grade = 0 and term = #{term} " +
            "and courseId = #{courseId} and teacherId = #{teacherId} and studentId = #{studentId}")
    Integer deleteForQuit(String term, String courseId, String teacherId, String studentId);

    @Select("select id from grade where total_grade = 0 and term = #{term} " +
            "and course_id = #{courseId} and teacher_id = #{teacherId} and student_id = #{studentId} " +
            "and time = #{time}")
    Integer findCertainId(String term, String courseId, String teacherId, String studentId, String time);

    @Select("select s.id,s.name,g.usual_grade,g.final_grade,g.total_grade " +
            "from grade as g join student as s on g.term = #{term} and g.course_id = #{courseId} " +
            "and g.teacher_id = #{teacherId} and g.time = #{time} and g.student_id = s.id")
    List<GradeWithStudentName> findListForGrade(String term, String courseId, String teacherId, String time);

    @Update("update grade set usual_grade = #{usualGrade}, final_grade = #{finalGrade} " +
            "where term = #{term} and student_id = #{studentId} and teacher_id = #{teacherId} and " +
            "course_id = #{courseId} and time = #{time}")
    Integer updateByLogging(String term, String courseId, String teacherId, String studentId, String time, Integer usualGrade, Integer finalGrade);

    @Update("update grade set total_grade = usual_grade * #{usualGradeProportion} + " +
            "final_grade * #{finalGradeProportion} where term = #{term} and " +
            "teacher_id = #{teacherId} and course_id = #{courseId} and time = #{time}")
    Integer updateTotal(String term, String courseId, String teacherId, String time, Double usualGradeProportion, Double finalGradeProportion);

    @Select("select g.term,g.course_id,c.name as course_name,g.teacher_id,t.name as teacher_name,g.usual_grade,g.final_grade," +
            "g.total_grade from grade as g join course as c join teacher as t " +
            "on student_id = #{studentId} and g.course_id = c.id and g.teacher_id = t.id " +
            "and g.term like concat('%', #{term}, '%')")
    List<GradePlus> findMyGrade(String studentId, String term);

    @Select("select c.credit,g.total_grade from grade as g join course as c on student_id = #{id} and g.course_id = c.id")
    List<CreditAndGrade> findCertainGradeSet(String id);

    @Select("select count(*) from grade where student_id = #{studentId} and term = #{term} and course_id = #{courseId}")
    Integer findIsRepeat(String studentId, String term, String courseId);
}
