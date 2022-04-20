<template>
  <div style="padding: 10px;">

    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 30%;" clearable/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column prop="teacherId" label="教师号"/>
      <el-table-column prop="teacherName" label="教师名"/>
      <el-table-column prop="term" label="学期" sortable/>
      <el-table-column prop="courseId" label="课号" sortable/>
      <el-table-column prop="courseName" label="课名" sortable/>
      <el-table-column prop="credit" label="学分"/>
      <el-table-column prop="time" label="时间"/>
      <el-table-column prop="currentNum" label=" 当前人数"/>
      <el-table-column prop="limitNum" label="人数上限"/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="primary" plain size="small" @click="goToCheckGrade(scope.row)">成绩登入</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">共 {{ total }} 条</div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TeacherCurriculum",
  created() {
    this.getCredit();
    this.load();
  },
  data() {
    return {
      search: '',
      tableData: [],
      total: 0,
      credit: []
    }
  },
  methods: {
    load() {
      request.get("/classes/forTeacher", {
        params: {
          search: this.search,
          teacherId: sessionStorage.getItem("currentId")
        }
      }).then(res => {
        console.log(res)
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
    goToCheckGrade(row) {
      // 缓存当前课程信息
      sessionStorage.setItem("currentCourse", row.courseId);
      sessionStorage.setItem("currentCourseName", row.courseName);
      sessionStorage.setItem("currentTerm", row.term);
      sessionStorage.setItem("currentTime", row.time);
      sessionStorage.setItem("currentCredit", row.credit);
      sessionStorage.setItem("currentLimitNum", row.limitNum);
      sessionStorage.setItem("currentCurrentNum",row.currentNum);
      // 跳转路由
      this.$router.push("/teacherGrade");
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