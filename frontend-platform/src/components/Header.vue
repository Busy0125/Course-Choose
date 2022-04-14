<template>
  <!--头部，分为三部分-->
  <div style="display: flex; height: 50px; border-bottom: 1px solid deepskyblue;">
    <!--1、名称-->
    <div style="width: 200px; color: dodgerblue; margin: auto; padding-left: 30px; font-weight: bold;">选课管理系统</div>

    <!--2、中间栏，无内容-->
    <div style="flex: 1;"></div>

    <!--3、下拉框，包括修改密码和退出登录-->
    <div style="width: 200px; margin: auto;">
      <el-dropdown>
        <el-row>
          <!--此处会在当前用户姓名左侧显示欢迎词，但效果不理想，登录后需要重新刷新界面才能正常显示-->
          <span style="color: #409EFF">{{ departmentName }}</span>
          <span class="el-dropdown-link">{{ currentUser }}
            <el-icon class="el-icon--right"><arrow-down/></el-icon>
          </span>
        </el-row>

        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="changePwd">修改密码</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <el-dialog v-model="dialogVisible" title="修改密码" width="30%">
    <el-form :model="form" label-width="120px">
      <el-form-item label="学号/工号">
        <el-input v-model="form.id" style="width: 80%;" readonly/>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.name" style="width: 80%;" readonly/>
      </el-form-item>
      <el-form-item label="输入密码">
        <el-input v-model="form.password" style="width: 80%;" type="password"/>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.confirm" style="width: 80%;" type="password"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import {ArrowDown} from '@element-plus/icons-vue'
import request from "@/utils/request";

export default {
  name: "Header",
  components: {
    ArrowDown,
  },
  data() {
    return {
      currentUser: sessionStorage.getItem("currentName"),
      departmentName: sessionStorage.getItem("currentDepName"),
      dialogVisible: false,
      form: {}
    }
  },
  methods: {
    changePwd() {
      this.form = {}
      this.form.id = sessionStorage.getItem("currentId");
      this.form.name = sessionStorage.getItem("currentName");
      this.dialogVisible = true;
    },
    update() {
      if (this.form.password != this.form.confirm) {
        this.$message({
          type: "error",
          message: "两次密码不一致，请重新输入"
        })
        this.form.password = ""
        this.form.confirm = ""
        return;
      }
      if (this.form.id.length == 8) { // 学生修改密码
        request.put("/student", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "修改成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      } else { // 教师修改密码
        request.put("/teacher", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "修改成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      }
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>