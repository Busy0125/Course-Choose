<template>
  <div>
    <el-menu router style="width: 200px; min-height: 100vh;" default-active="/StudentChoose">
      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <Lollipop/>
          </el-icon>
          <span>学生课程管理</span>
        </template>
        <el-menu-item index="/StudentChoose">选课</el-menu-item>
        <el-menu-item index="/StudentQuit">退课</el-menu-item>
        <el-menu-item index="/StudentCurriculum">我的课表</el-menu-item>
        <el-menu-item index="/StudentGrade">我的成绩</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import {Lollipop} from '@element-plus/icons-vue'

export default {
  name: "AsideForStudent",
  components: {
    Lollipop
  }
}
</script>

<style scoped>

</style>