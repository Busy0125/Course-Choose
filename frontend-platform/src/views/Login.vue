<template>
  <div :style="bg" style="overflow: hidden;">
    <el-form ref="form" :model="form" class="login" :rules="rules">
      <div style="color: darkslategrey; font-size: 30px; text-align: center; padding: 40px 0;">欢迎登录</div>
      <el-form-item prop="id">
        <el-input :prefix-icon="Avatar" placeholder="请输入学号/工号" v-model="form.id"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input :prefix-icon="Lock" placeholder="请输入密码" v-model="form.password" show-password/>
      </el-form-item>
      <!--      <div style="text-align: center;">-->
      <!--        <el-button style="width: 80%;" type="info" @click="login">登 录</el-button>-->
      <!--        <div style="height: 50px; background-color: deepskyblue; width: 80%; margin: 0 auto;"></div>-->
      <!--      </div>-->
      <el-button style="width: 100%;" type="info" @click="login">登 录</el-button>
    </el-form>
  </div>
  <!--
    元素居中可以通过设置：style="margin: 0 auto;"来实现，但el-button的margin不知为何会失效。
    可以加一个div，设置div的style="text-align: center;"，也可以实现内部元素el-button的居中。
    此处直接设置el-button的width: 100%，没有设置居中，也可以得到理想的效果。
  -->
</template>

<script>
import {Avatar, Lock} from '@element-plus/icons-vue';
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      Avatar,
      Lock,
      bg: {
        backgroundImage: "url(" + require("../assets/loginBg.jpg") + ")",
        backgroundRepeat: 'no-repeat',
        backgroundSize: "cover",
        minHeight: "calc(100vh - 1000px)",
        position: "relative",
        backgroundPosition: "center center",
      },
      form: {},
      rules: {
        id: [
          {required: true, message: '请输入学号/工号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id.length == 8) { // 学生登录
            request.post("/student/login", this.form).then(res => {
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "登录成功"
                })
                request.get("/department/forHeader", {
                  params: {
                    dep: res.data.department
                  }
                }).then(res2 => {
                  sessionStorage.setItem("currentDepName", "你好，" + res2.data[0].departmentName + "，")
                })
                sessionStorage.setItem("currentId", res.data.id)
                sessionStorage.setItem("currentName", res.data.name)
                sessionStorage.setItem("currentDep",res.data.department)
                this.$router.push("/student"); // 登录成功之后进行页面跳转，跳转到主页
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else if (this.form.id.length == 3) { // 教师或管理员登录
            request.post("/teacher/login", this.form).then(res => {
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "登录成功"
                })
                sessionStorage.setItem("currentId", res.data.id)
                sessionStorage.setItem("currentName", res.data.name)
                if (this.form.id == "100") {
                  sessionStorage.setItem("currentDep", 0);
                  sessionStorage.setItem("currentDepName", "")
                  this.$router.push("/manageCourse"); // 登录成功之后进行页面跳转
                } else {
                  request.get("/department/forHeader", {
                    params: {
                      dep: res.data.department
                    }
                  }).then(res2 => {
                    sessionStorage.setItem("currentDepName", "你好，" + res2.data[0].departmentName + "，")
                  })
                  sessionStorage.setItem("currentDep", res.data.department);
                  this.$router.push("/teacher");
                }
              } else {
                this.$message({
                  type: "error",
                  message: res.msg
                })
              }
            })
          } else {
            this.$message({
              type: "error",
              message: "用户名不合法"
            })
          }
        }
      })
    }
  }
}
// 注意，登录后sessionShort存储情况如下：
// key=currentId,value=学号/工号
// key=currentName,value=姓名
// key=currentDep,value=院系号，管理员为0
// key=currentDepName,value="你好，xxx学院，某某"，管理员为空字符串
</script>

<style>
.login {
  background-color: wheat;
  width: 30%;
  height: 320px;
  opacity: 0.6;
  border-radius: 30px;
  margin: 160px auto;

  /* 为其整体设置接近透明的效果*/
  background-color: rgba(255, 255, 255, 0.3);
  /* 设置box-shadow使其有立体感 */
  box-shadow: 5px 5px 0 0 rgba(0, 0, 0, 0.3);

  padding: 0 50px;
}
</style>