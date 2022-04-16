import {createRouter, createWebHistory} from 'vue-router'
import Manage from "@/views/Manage";
import ManageCourse from "@/views/ManageCourse";
import ManageStudent from "@/views/ManageStudent";
import ManageTeacher from "@/views/ManageTeacher";
import Login from "@/views/Login";
import Student from "@/views/Student";
import StudentChoose from "@/views/StudentChoose";
import StudentQuit from "@/views/StudentQuit";
import StudentCurriculum from "@/views/StudentCurriculum";
import StudentGrade from "@/views/StudentGrade";
import Teacher from "@/views/Teacher";
import TeacherCurriculum from "@/views/TeacherCurriculum";
import TeacherGrade from "@/views/TeacherGrade";
import ManageClasses from "@/views/ManageClasses";
import ManageDepartment from "@/views/ManageDepartment";

const routes = [
    {
        path: '/',
        name: 'Manage',
        component: Manage,
        redirect: '/login',
        children: [
            {
                path: '/manageCourse',
                name: 'ManageCourse',
                component: ManageCourse
            },
            {
                path: '/manageStudent',
                name: 'ManageStudent',
                component: ManageStudent
            },
            {
                path: '/manageTeacher',
                name: 'ManageTeacher',
                component: ManageTeacher
            },
            {
                path: '/manageClasses',
                name: 'ManageClasses',
                component: ManageClasses
            },
            {
                path: '/manageDepartment',
                name: 'ManageDepartment',
                component: ManageDepartment
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/student',
        name: 'Student',
        component: Student,
        redirect: '/studentChoose',
        children: [
            {
                path: '/studentChoose',
                name: 'StudentChoose',
                component: StudentChoose
            },
            {
                path: '/studentQuit',
                name: 'StudentQuit',
                component: StudentQuit
            },
            {
                path: '/studentCurriculum',
                name: 'StudentCurriculum',
                component: StudentCurriculum
            },
            {
                path: '/studentGrade',
                name: 'StudentGrade',
                component: StudentGrade
            }
        ]
    },
    {
        path: '/teacher',
        name: 'Teacher',
        component: Teacher,
        redirect: '/teacherCurriculum',
        children: [
            {
                path: '/teacherCurriculum',
                name: 'TeacherCurriculum',
                component: TeacherCurriculum
            },
            {
                path: '/teacherGrade',
                name: 'TeacherGrade',
                component: TeacherGrade
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
