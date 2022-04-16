<template>
  <div>
    <el-menu router style="width: 200px; min-height: 100vh;" default-active="/manageCourse">
      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <Lollipop/>
          </el-icon>
          <span>教务管理</span>
        </template>
        <el-menu-item index="/manageCourse">课程管理</el-menu-item>
        <el-menu-item index="/manageStudent">学生管理</el-menu-item>
        <el-menu-item index="/manageTeacher">教师管理</el-menu-item>
        <el-menu-item index="/manageClasses">班级管理</el-menu-item>
        <el-menu-item index="/manageDepartment">院系管理</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import {Lollipop} from '@element-plus/icons-vue'

export default {
  name: "Aside",
  components: {
    Lollipop
  }
}
</script>

<style scoped>

</style>