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
      <el-table-column prop="currentNum" label=" 当前人数"/>
      <el-table-column prop="limitNum" label="人数上限"/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-popconfirm title="确定选择此课程吗？" @confirm="choose(scope.row)">
            <template #reference>
              <el-button type="primary" plain size="small">选课</el-button>
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
  name: "StudentChoose",
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
      request.get("/classes/forStudent", {
        params: {
          search: this.search,
          dep: sessionStorage.getItem("currentDep")
        }
      }).then(res => {
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

    // 原计划是“判断当前row中的课程是否已选修”单独作为一个方法，但函数嵌套时被调用函数的返回值无法正常传递给调用者，原因不明
    choose(row) {
      // 选课之前需要先判断本门课程是否已选过
      // 有五个东西需要传到后台进行验证，学号，学期，课号，工号，时间，对它们进行封装
      let chooseObj = {
        "studentId": sessionStorage.getItem("currentId"),
        "term": row.term,
        "courseId": row.courseId,
        "teacherId": row.teacherId,
        "time": row.time
      }
      // 去grade表中进行查询
      request.post("/grade/isChosen", chooseObj).then(res => {
        console.log(res);
        if (res.code !== '0') { // 不能选课
          this.$message({
            type: "warning",
            message: res.msg
          })
          return;
        } else {
          // 创建一条grade实体（id不传，自动生成），插入到grade表中
          let gradeObj = {
            "studentId": sessionStorage.getItem("currentId"),
            "term": row.term,
            "courseId": row.courseId,
            "teacherId": row.teacherId,
            "time": row.time,
            "usualGrade": '0',
            "finalGrade": '0',
            "totalGrade": '0'
          }
          request.post("/grade", gradeObj).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "选课成功"
              })
              this.load();
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
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