<template>
  <div style="padding: 10px;">

    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 30%;" clearable/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column prop="term" label="学期" sortable/>
      <el-table-column prop="courseId" label="课号" sortable/>
      <el-table-column prop="courseName" label="课名"/>
      <el-table-column prop="credit" label="学分"/>
      <el-table-column prop="teacherId" label="教师号" sortable/>
      <el-table-column prop="teacherName" label="教师名"/>
      <el-table-column prop="time" label="时间"/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-popconfirm title="确定删选此课程吗？" @confirm="quit(scope.row)">
            <template #reference>
              <el-button type="danger" plain size="small">退课</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">共 {{ total }} 条</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentQuit",
  data() {
    return {
      search: '',
      tableData: [],
      total: 0,
      credit: []
    }
  },
  created() {
    this.getCredit();
    this.load();
  },
  methods: {
    load() {
      // 该页面需要展示的数据：classes表中，当前学生选过的课
      request.get("/classes/forQuit", {
        params: {
          // 后台查询所需数据在此做封装
          studentId: sessionStorage.getItem("currentId"),
          search: this.search
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.list;
        this.total = res.data.total;
        for (let i = 0; i < this.tableData.length; i++) {
          for (let j = 0; j < this.credit.length; j++) {
            if (this.tableData[i].courseId == this.credit[j].id) {
              this.tableData[i].credit = this.credit[j].credit;
            }
          }
        }
      })
    },
    quit(row) {
      // // 退课本质是到grade表中删除相应数据,之后再更新界面就好
      // // 先将待删除数据进行封装
      // let forQuit = {
      //   term: row.term,
      //   courseId: row.courseId,
      //   teacherId: row.teacherId,
      //   studentId: sessionStorage.getItem("currentId")
      // }
      // // 删除数据
      // request.delete("/grade/" + forQuit).then(res => {
      //   console.log(res);
      // })

      // 上述方法出问题可能是由于前端封装时键值没有打双引号，后端SQL语句中字段名与数据库中不匹配，或是mapper中注解使用错误造成
      // 之后写的方法能够出效果，懒得改了

      // request类型为delete时，后端无法接受打包的数据，原因不明，看样子只能传单个数据给后端了
      // 先获取当前row对应课程在grade的id，再根据该id去删除grade表中对应字段，采取该方式需要发起两次request请求
      request.get("/grade/seekId", {
        params: {
          term: row.term,
          courseId: row.courseId,
          teacherId: row.teacherId,
          time: row.time,
          studentId: sessionStorage.getItem("currentId")
        }
      }).then(res => {
        console.log(res)
        if (res.code !== '0') {
          this.$message({
            type: "warning",
            message: res.msg
          })
          return;
        } else {
          // 删除grade表中对应元素
          request.delete("/grade/" + res.data).then(res2 => {
            console.log(res2)
            if (res2.code === '0') {
              this.$message({
                type: "success",
                message: "退课成功"
              })
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
            // 更新界面
            this.load();
          })
        }
      })
    },
    getCredit() {
      request.get("/course/getCredit").then(res => {
        console.log(res);
        this.credit = res.data;
      })
    }
  }
}
</script>

<style scoped>

</style>