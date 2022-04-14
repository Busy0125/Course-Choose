<template>
  <div style="padding: 10px;">
    <div style="margin: 10px 0;">
      <el-select id="mySelect" v-model="selectTerm" placeholder="--选择学期--" @change="load">
        <el-option label="--选择学期--" :value="''">--选择学期--</el-option>
        <el-option
            v-for="item in allTerms"
            :label="item"
            :value="item"
        />
      </el-select>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column prop="term" label="学期"/>
      <el-table-column prop="courseId" label="课号"/>
      <el-table-column prop="courseName" label="课名"/>
      <el-table-column prop="credit" label="学分"/>
      <el-table-column prop="teacherId" label="教师号"/>
      <el-table-column prop="teacherName" label="教师名"/>
      <el-table-column prop="usualGrade" label="平时成绩" sortable/>
      <el-table-column prop="finalGrade" label="考试成绩" sortable/>
      <el-table-column prop="totalGrade" label="综合成绩" sortable/>
    </el-table>
    <el-row style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">
      <el-col :span="20">
        总学分：{{ totalCredit }}
      </el-col>
      <el-col :span="4">
        平均绩点：{{ gpa }}
      </el-col>
    </el-row>
    <el-row style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">
      共 {{ total }} 条
    </el-row>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentGrade",
  data() {
    return {
      tableData: [],
      selectTerm: "",
      total: 0,
      gpa: 0,
      allTerms: [],
      credit: [],
      totalCredit: 0
    }
  },
  created() {
    this.getCredit();
    this.load();
    this.getAllTerm();
  },
  methods: {
    load() {
      request.get("/grade/forStudentDisplay", {
        params: {
          // 后台查询所需数据在此做封装
          studentId: sessionStorage.getItem("currentId"),
          selectTerm: this.selectTerm
        }
      }).then(res => {
        this.tableData = res.data.list;
        this.total = res.data.total;
        console.log(res)
        for (let i = 0; i < this.tableData.length; i++) {
          for (let j = 0; j < this.credit.length; j++) {
            if (this.tableData[i].courseId == this.credit[j].id) {
              this.tableData[i].credit = this.credit[j].credit;
            }
          }
        }
        // 获取总学分
        this.getTotalCredit();

        // 计算平均绩点
        this.gpa = 0;
        for (let i = 0; i < this.tableData.length; i++) {
          this.gpa += this.getSingleGpa(this.tableData[i].totalGrade) * this.tableData[i].credit;
        }
        this.gpa = (this.gpa /= this.totalCredit).toFixed(2)
      })
    },

    // 获取所有学期
    getAllTerm() {
      request.get("/classes/allTerm").then(res => {
        this.allTerms = res.data
      })
    },
    getCredit() {
      request.get("/course/getCredit").then(res => {
        console.log(res);
        this.credit = res.data;
      })
    },
    getTotalCredit() {
      this.totalCredit = 0
      for (let i = 0; i < this.tableData.length; i++) {
        this.totalCredit += this.tableData[i].credit
      }
    },
    getSingleGpa(t) {
      if (t >= 90 && t <= 100) {
        return 4.0
      } else if (t >= 85 && t <= 89) {
        return 3.7
      } else if (t >= 82 && t <= 84) {
        return 3.3
      } else if (t >= 78 && t <= 81) {
        return 3.0
      } else if (t >= 75 && t <= 77) {
        return 2.7
      } else if (t >= 72 && t <= 74) {
        return 2.3
      } else if (t >= 68 && t <= 71) {
        return 2.0
      } else if (t >= 66 && t <= 67) {
        return 1.7
      } else if (t >= 64 && t <= 65) {
        return 1.5
      } else if (t >= 60 && t <= 63) {
        return 1.0
      } else if (t >= 0 && t <= 59) {
        return 0
      }
    }
  }
}
</script>

<style scoped>

</style>