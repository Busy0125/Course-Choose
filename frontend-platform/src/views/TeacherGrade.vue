<template>
  <div style="margin: 10px;">
    <el-button type="primary" plain :icon="ArrowLeft"
               @click="back">返回课程列表
    </el-button>
    <el-button type="primary" plain @click="displayGrade">查看成绩分布
    </el-button>
    <div style="text-align: center;">
      <h1 style="color: #409EFF;">学生名单</h1>
      <span>
        课程号：<ins>&nbsp;{{ courseId }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        课程名：<ins>&nbsp;{{ courseName }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        学期：<ins>&nbsp;{{ term }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <br><br>
        学分：<ins>&nbsp;{{ credit }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        教师号：<ins>&nbsp;{{ teacherId }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        教师名：<ins>&nbsp;{{ teacherName }}&nbsp;</ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        时间：<ins>&nbsp;{{ time }}&nbsp;</ins>
      </span>
      <hr>

      <!--表格-->
      <el-table :data="tableData" border stripe style="width: 100%;">
        <el-table-column prop="id" label="学生学号" sortable/>
        <el-table-column prop="name" label="学生姓名"/>
        <el-table-column prop="usualGrade" label="平时成绩" sortable/>
        <el-table-column prop="finalGrade" label="考试成绩" sortable/>
        <el-table-column prop="totalGrade" label="综合成绩" sortable/>

        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleGrade(scope.row)">编辑成绩</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-row>
      <el-col :span="16">
        <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">选课人数：{{ total }}</div>
      </el-col>
      <el-col :span="4">
        <div style="margin-top: 15px; margin-left: 20px; color: red;" v-if="needToUpdate">请更新综合成绩！</div>
      </el-col>
      <el-col :span="4">
        <el-button style="margin: 10px;" type="primary" plain @click="getTotalGrade">生成综合成绩</el-button>
      </el-col>
    </el-row>

    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="平时成绩">
          <el-input v-model="form.usualGrade" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="考试成绩">
          <el-input v-model="form.finalGrade" style="width: 80%;"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogVisible2" title="请输入平时分和考试分占比" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item label="平时成绩占比">
          <el-input v-model="form2.usualGradeProportion" style="width: 40%;"/>
          <span>&nbsp;&nbsp;%</span>
        </el-form-item>
        <el-form-item label="考试成绩占比">
          <el-input v-model="form2.finalGradeProportion" style="width: 40%;"/>
          <span>&nbsp;&nbsp;%</span>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogVisible3">
      <div style="width: 500px; height: 500px; margin: 0 auto;" id="distribution"></div>
    </el-dialog>

  </div>
</template>

<script>
import {ArrowLeft} from '@element-plus/icons-vue'
import request from "@/utils/request";
import * as echarts from "echarts";

export default {
  name: "TeacherGrade",
  data() {
    return {
      ArrowLeft,
      courseId: sessionStorage.getItem("currentCourse"),
      courseName: sessionStorage.getItem("currentCourseName"),
      teacherId: sessionStorage.getItem("currentId"),
      teacherName: sessionStorage.getItem("currentName"),
      term: sessionStorage.getItem("currentTerm"),
      time: sessionStorage.getItem("currentTime"),
      credit: sessionStorage.getItem("currentCredit"),

      tableData: [],
      total: 0,

      dialogVisible: false,
      form: {},
      dialogVisible2: false,
      form2: {},
      title: "",

      // 用于录入平时成绩和期末成绩暂存当前字段
      row: {},

      // 当前总成绩是否需要更新
      needToUpdate: false,

      // 成绩展示
      dialogVisible3: false,
      gradeData: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/grade/forGrade", {
        params: {
          term: this.term,
          courseId: this.courseId,
          teacherId: this.teacherId,
          time: this.time
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.list;
        this.total = res.data.total;
      })
    },
    handleGrade(row) {
      this.form = {};
      this.title = row.id + "-" + row.name + "的成绩信息";

      this.row.studentId = row.id;
      this.row.teacherId = this.teacherId;
      this.row.courseId = this.courseId;
      this.row.term = this.term;
      this.row.time = this.time;
      this.dialogVisible = true;
    },
    save() {
      this.row.usualGrade = this.form.usualGrade;
      this.row.finalGrade = this.form.finalGrade;
      console.log(this.row)
      request.post("/grade/logging", this.row).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '成绩更新成功'
          })
          this.load();
          this.dialogVisible = false;
          this.needToUpdate = true;
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },
    getTotalGrade() {
      // 此处可以保留上次输入的数据
      // this.form2 = {};
      this.dialogVisible2 = true;
    },
    back() {
      if (this.needToUpdate) {
        this.$message({
          type: 'warning',
          message: '综合成绩未更新'
        })
        return;
      } else {
        this.$router.push('/teacherCurriculum');
      }
    },
    update() {
      request.get("/grade/update", {
        params: {
          term: this.term,
          courseId: this.courseId,
          teacherId: this.teacherId,
          time: this.time,
          usualGradeProportion: this.form2.usualGradeProportion * 0.01,
          finalGradeProportion: this.form2.finalGradeProportion * 0.01
        }
      }).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '综合成绩更新成功'
          })
          this.needToUpdate = false;
          this.load();
          this.dialogVisible2 = false;
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },
    displayGrade() {
      let currentGrade = [
        {value: 0, name: '90分-100分'},
        {value: 0, name: '80分-89分'},
        {value: 0, name: '70分-79分'},
        {value: 0, name: '60-69分'},
        {value: 0, name: '60分以下'}
      ];
      for (let i = 0; i < this.tableData.length; i++) {
        let t = this.tableData[i].totalGrade
        if (t >= 90 && t <= 100) {
          currentGrade[0]["value"]++;
        } else if (t >= 80 && t <= 89) {
          currentGrade[1]["value"]++;
        } else if (t >= 70 && t <= 79) {
          currentGrade[2]["value"]++;
        } else if (t >= 60 && t <= 69) {
          currentGrade[3]["value"]++;
        } else {
          currentGrade[4]["value"]++;
        }
      }
      this.gradeData = currentGrade;
      this.dialogVisible3 = true;
      this.$nextTick(() => {
        let myChart = echarts.init(document.getElementById("distribution"));
        let option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: '该分数段人数',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: this.gradeData
            }
          ]
        };
        myChart.setOption(option);
      })
    }
  }
}
</script>

<style scoped>

</style>