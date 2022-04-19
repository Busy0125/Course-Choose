<template>
  <div style="padding: 10px;">
    <div style="margin: 10px 0;">
      <el-select v-model="selectTerm" placeholder="--选择学期--" @change="selectChange">
        <el-option label="--选择学期--" :value="''">--选择学期--</el-option>
        <el-option
            v-for="item in allTerms"
            :label="item"
            :value="item"
        />
      </el-select>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column type="index" :index="indexMethod"/>
      <el-table-column prop="term" label="学期"/>
      <el-table-column prop="courseId" label="课号"/>
      <el-table-column prop="courseName" label="课名"/>
      <el-table-column prop="credit" label="学分"/>
      <el-table-column prop="teacherId" label="教师号"/>
      <el-table-column prop="teacherName" label="教师名"/>
      <el-table-column prop="time" label="时间"/>
    </el-table>
    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">共 {{ total }} 条</div>
    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);" v-if="tableHidden == false">
      总学分：{{ totalCredit }}
    </div>

    <table :hidden="tableHidden" id="schedule">
      <caption style="margin: 5px;"><h2>课程表</h2></caption>
      <tr style="background-color: #fafafa">
        <td style="font-weight: bold; width: 150px;">时间段</td>
        <td style="font-weight: bold">周一</td>
        <td style="font-weight: bold">周二</td>
        <td style="font-weight: bold">周三</td>
        <td style="font-weight: bold">周四</td>
        <td style="font-weight: bold">周五</td>
      </tr>
      <tr>
        <td>1-2&nbsp;&nbsp;&nbsp;8:00-8:45</td>
        <td id="一1-2"></td>
        <td id="二1-2"></td>
        <td id="三1-2"></td>
        <td id="四1-2"></td>
        <td id="五1-2"></td>
      </tr>
      <tr>
        <td>3-4&nbsp;&nbsp;&nbsp;10:00-11:40</td>
        <td id="一3-4"></td>
        <td id="二3-4"></td>
        <td id="三3-4"></td>
        <td id="四3-4"></td>
        <td id="五3-4"></td>
      </tr>
      <tr>
        <td>5-6&nbsp;&nbsp;&nbsp;13:00-14:40</td>
        <td id="一5-6"></td>
        <td id="二5-6"></td>
        <td id="三5-6"></td>
        <td id="四5-6"></td>
        <td id="五5-6"></td>
      </tr>
      <tr>
        <td>7-8&nbsp;&nbsp;&nbsp;15:00-16:40</td>
        <td id="一7-8"></td>
        <td id="二7-8"></td>
        <td id="三7-8"></td>
        <td id="四7-8"></td>
        <td id="五7-8"></td>
      </tr>
      <tr>
        <td>9-10&nbsp;&nbsp;&nbsp;18:00-19:40</td>
        <td id="一9-10"></td>
        <td id="二9-10"></td>
        <td id="三9-10"></td>
        <td id="四9-10"></td>
        <td id="五9-10"></td>
      </tr>
      <tr>
        <td>11-12&nbsp;&nbsp;&nbsp;20:00-21:40</td>
        <td id="一11-12"></td>
        <td id="二11-12"></td>
        <td id="三11-12"></td>
        <td id="四11-12"></td>
        <td id="五11-12"></td>
      </tr>
    </table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "StudentCurriculum",
  data() {
    return {
      tableData: [],
      selectTerm: "",
      total: 0,
      allTerms: [],
      tableHidden: true,
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
      request.get("/classes/forStudentDisplay", {
        params: {
          // 后台查询所需数据在此做封装
          studentId: sessionStorage.getItem("currentId"),
          selectTerm: this.selectTerm
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
        if (this.tableHidden === false) {
          // 先把表清空
          // childNodes方法返回元素的子节点集合
          // item(i)方法返回集合中指定索引的节点
          // console.log(document.getElementById("schedule").childNodes.length) // 8:含标题和七行
          let myTable = document.getElementById("schedule").childNodes
          for (let i = 2; i < 8; i++) { // 每一行
            for (let j = 1; j < 6; j++) { // 每一列
              myTable.item(i).childNodes.item(j).innerText = "";
            }
          }
          // 建表
          for (let i = 0; i < this.tableData.length; i++) {
            document.getElementById(this.tableData[i].time).innerText = String.fromCharCode(i + 1 + 64);
          }
          // 获取总学分
          this.getTotalCredit();
        }
      })
    },

    // 获取所有学期
    getAllTerm() {
      request.get("/classes/allTerm").then(res => {
        this.allTerms = res.data
      })
    },
    indexMethod(index) {
      return String.fromCharCode(index + 1 + 64)
    },
    selectChange(val) {
      this.load()
      if (val === "") {
        this.tableHidden = true;
      } else {
        this.tableHidden = false;
      }
    },
    // 此处方法内语句执行顺序总感觉有点问题，原因不明，但效果理想

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
    }
  }
}
</script>

<style scoped>
table {
  border: 1px solid #ebeef5;
  width: 100%;
  border-collapse: collapse;
  color: rgb(96, 98, 102);
}

td {
  border: 1px solid #ebeef5;
  padding: 10px;
}

tr:hover {
  background-color: #fafafa;
}
</style>