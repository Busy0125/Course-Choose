<template>
  <div style="padding: 10px;">
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="add">新增</el-button>
    </div>

    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 30%;" clearable/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>

      <el-select v-model="selectDep" placeholder="--选择学院--" style="margin-left: 400px;" @change="load">
        <el-option label="--选择学院--" :value="0">--选择学院--</el-option>
        <el-option
            v-for="item in dep"
            :label="item.departmentName"
            :value="item.department"
        />
      </el-select>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="term" label="学期"/>
      <el-table-column prop="courseId" label="课号"/>
      <el-table-column prop="courseName" label="课名"/>
      <el-table-column prop="teacherId" label="教师号"/>
      <el-table-column prop="teacherName" label="教师名"/>
      <el-table-column prop="time" label="时间"/>
      <el-table-column prop="currentNum" label=" 当前人数"/>
      <el-table-column prop="limitNum" label="人数上限"/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除此班级吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="text" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">共 {{ total }} 条</div>

    <!--    <el-pagination-->
    <!--        style="margin-top: 10px;"-->
    <!--        :currentPage="currentPage"-->
    <!--        :page-size="pageSize"-->
    <!--        :page-sizes="[5, 10, 20]"-->
    <!--        layout="total, sizes, prev, pager, next, jumper"-->
    <!--        :total="total"-->
    <!--        @size-change="handleSizeChange"-->
    <!--        @current-change="handleCurrentChange"-->
    <!--    />-->
    <!--该页面返回到前端的是HashMap，出现未知原因无法实现分页，往后的页面若存在多表查询，则抛弃分页效果-->

    <el-dialog v-model="dialogVisible" title="新增班级" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="ID">
          <el-input v-model="form.id" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="学年">
          <el-input v-model="form.year" style="width: 80%;" placeholder="请填写19/20/21"/>
        </el-form-item>
        <el-form-item label="季节">
          <el-select v-model="form.season" placeholder="--选择季节--" style="width: 80%;">
            <el-option label="--选择季节--" disabled></el-option>
            <el-option label="秋季学期" value="秋"></el-option>
            <el-option label="冬季学期" value="冬"></el-option>
            <el-option label="春季学期" value="春"></el-option>
            <el-option label="夏季学期" value="夏"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课号">
          <el-input v-model="form.courseId" @blur="form.courseName = this.getNameById(form.courseId,8);"
                    style="width: 80%;" placeholder="请填写课程号"/>
        </el-form-item>
        <el-form-item label="课名">
          <el-input v-model="form.courseName" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="教师号">
          <el-input v-model="form.teacherId" @blur="form.teacherName = this.getNameById(form.teacherId,3);"
                    style="width: 80%;" placeholder="请填写教师号"/>
        </el-form-item>
        <el-form-item label="教师名">
          <el-input v-model="form.teacherName" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-radio-group v-model="form.week">
            <el-radio label="一">周一</el-radio>
            <el-radio label="二">周二</el-radio>
            <el-radio label="三">周三</el-radio>
            <el-radio label="四">周四</el-radio>
            <el-radio label="五">周五</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="节数">
          <el-radio-group v-model="form.session">
            <el-radio label="1-2">1-2</el-radio>
            <el-radio label="3-4">3-4</el-radio>
            <el-radio label="5-6">5-6</el-radio>
            <el-radio label="7-8">7-8</el-radio>
            <el-radio label="9-10">9-10</el-radio>
            <el-radio label="11-12">11-12</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="人数上限">
          <el-input v-model="form.limitNum" style="width: 80%;"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogVisible2" title="编辑班级" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item label="ID">
          <el-input v-model="form2.id" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="学年">
          <el-input v-model="form2.year" style="width: 80%;" placeholder="请填写19/20/21"/>
        </el-form-item>
        <el-form-item label="季节">
          <el-select v-model="form2.season" placeholder="--选择季节--" style="width: 80%;">
            <el-option label="--选择季节--" disabled></el-option>
            <el-option label="秋季学期" value="秋"></el-option>
            <el-option label="冬季学期" value="冬"></el-option>
            <el-option label="春季学期" value="春"></el-option>
            <el-option label="夏季学期" value="夏"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课号">
          <el-input v-model="form2.courseId" @blur="form2.courseName = this.getNameById(form2.courseId,8);"
                    style="width: 80%;" placeholder="请填写课程号"/>
        </el-form-item>
        <el-form-item label="课名">
          <el-input v-model="form2.courseName" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="教师号">
          <el-input v-model="form2.teacherId" @blur="form2.teacherName = this.getNameById(form2.teacherId,3);"
                    style="width: 80%;" placeholder="请填写教师号"/>
        </el-form-item>
        <el-form-item label="教师名">
          <el-input v-model="form2.teacherName" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-radio-group v-model="form2.week">
            <el-radio label="一">周一</el-radio>
            <el-radio label="二">周二</el-radio>
            <el-radio label="三">周三</el-radio>
            <el-radio label="四">周四</el-radio>
            <el-radio label="五">周五</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="节数">
          <el-radio-group v-model="form2.session">
            <el-radio label="1-2">1-2</el-radio>
            <el-radio label="3-4">3-4</el-radio>
            <el-radio label="5-6">5-6</el-radio>
            <el-radio label="7-8">7-8</el-radio>
            <el-radio label="9-10">9-10</el-radio>
            <el-radio label="11-12">11-12</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="当前人数">
          <el-input v-model="form2.currentNum" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="人数上限">
          <el-input v-model="form2.limitNum" style="width: 80%;" readonly/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ManageClasses",
  data() {
    return {
      search: '',
      tableData: [],
      // currentPage: 1,
      // pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogVisible2: false,
      form: {},
      form2: {},
      dep: [],
      courseWithName: [],
      teacherWithName: [],
      selectDep: ''
    }
  },
  created() {
    this.getDepartment();
    this.getCourseWithName();
    this.getTeacherWithName();
    this.load();
  },
  methods: {
    load() {
      request.get("/classes", {
        params: {
          // pageNum: this.currentPage,
          // pageSize: this.pageSize,
          search: this.search,
          selectDep: this.selectDep
        }
      }).then(res => {
        this.total = res.data.total;
        this.tableData = res.data.page;
      })
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    save() {
      let formObj = {
        "id": this.form.id,
        "term": this.form.year + "年" + this.form.season,
        "courseId": this.form.courseId,
        "courseName": this.form.courseName,
        "teacherId": this.form.teacherId,
        "teacherName": this.form.teacherName,
        "time": this.form.week + this.form.session,
        "limitNum": this.form.limitNum,
        "currentNum": 0
      }
      request.post("/classes", formObj).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "新增成功"
          })
          this.dialogVisible = false;
          this.load();
        } else {
          this.$message({
            type: "warning",
            message: res.msg
          })
        }
      })
    },
    update() {
      let formObj = {
        "id": this.form2.id,
        "term": this.form2.year + "年" + this.form2.season,
        "courseId": this.form2.courseId,
        "courseName": this.form2.courseName,
        "teacherId": this.form2.teacherId,
        "teacherName": this.form2.teacherName,
        "time": this.form2.week + this.form2.session,
        "limitNum": this.form2.limitNum,
        "currentNum": this.form2.currentNum
      }
      request.put("/classes", formObj).then(res => {
        console.log(res);
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          this.dialogVisible2 = false;
          this.load();
        } else {
          this.$message({
            type: "warning",
            message: res.msg
          })
        }
      })
    },

    // 获取department表
    getDepartment() {
      request.get("/department").then(res => {
        this.dep = res["data"];
      })
    },

    getCourseWithName() {
      request.get("/course/withName").then(res => {
        this.courseWithName = res.data;
      })
    },

    getTeacherWithName() {
      request.get("/teacher/withName").then(res => {
        this.teacherWithName = res.data;
      })
    },

    handleEdit(row) {
      let formObj = {
        "id": row.id,
        "year": row.term.substr(0, 2),
        "season": row.term[3],
        "courseId": row.courseId,
        "courseName": row.courseName,
        "teacherId": row.teacherId,
        "teacherName": row.teacherName,
        "week": row.time.substr(0, 1),
        "session": row.time.substr(1, 3),
        "currentNum": row.currentNum,
        "limitNum": row.limitNum,
      };
      this.form2 = formObj;
      this.dialogVisible2 = true;
    },
    handleDelete(id) {
      request.delete("/classes/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
          this.load()
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },

    // 该函数配合失去焦点事件可以实现输入课号/工号后，自动生成课名/教师名
    getNameById(id, idLength) {
      if (id != null) {
        if (id.length == 3 && idLength == 3) {
          let i;
          for (i = 0; i < this.teacherWithName.length; i++) {
            if (this.teacherWithName[i].id == id) {
              return this.teacherWithName[i].name;
            }
          }
          if (i == this.teacherWithName.length) {
            return "教师号不存在";
          }
        } else if (id.length == 8 && idLength == 8) {
          let i;
          for (i = 0; i < this.courseWithName.length; i++) {
            if (this.courseWithName[i].id == id) {
              return this.courseWithName[i].name;
            }
          }
          if (i == this.courseWithName.length) {
            return "课号不存在";
          }
        } else {
          return "ID长度不合法";
        }
      } else {
        return "请按要求ID";
      }
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    }
  }
}
</script>

<style scoped>

</style>