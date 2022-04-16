<template>
  <div style="padding: 10px;">
    <div style="margin: 10px 0;">
      <el-button type="primary" @click="add">新增</el-button>
    </div>

    <el-table :data="tableData" border stripe style="width: 100%;">
      <el-table-column prop="department" label="院系号" sortable/>
      <el-table-column prop="departmentName" label="院名"/>
      <el-table-column prop="depStudentNum" label="院学生数"/>
      <el-table-column prop="depTeacherNum" label="院教师数"/>
      <el-table-column prop="depCourseNum" label="院课程数"/>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除此学院吗？" @confirm="handleDelete(scope.row.department)">
            <template #reference>
              <el-button type="text" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 10px 10px; font-size: 10px; color: rgb(96, 98, 102);">共 {{ total }} 条</div>

    <el-dialog v-model="dialogVisible" title="新增学院" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="院系号">
          <el-input v-model="form.department" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="院名">
          <el-input v-model="form.departmentName" style="width: 80%;"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogVisible2" title="编辑学院信息" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item label="院系号">
          <el-input v-model="form2.department" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="院名">
          <el-input v-model="form2.departmentName" style="width: 80%;"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible2 = false;load();">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ManageDepartment",
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      dialogVisible2: false,
      form: {},
      form2: {},
      total: 0
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/department/getInfo").then(res => {
        console.log(res);
        this.tableData = res.data;
        this.total = this.tableData.length;
      })
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form2 = JSON.parse(JSON.stringify(row));
      this.dialogVisible2 = true;
    },
    handleDelete(department) {
      request.delete("/department/" + department).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
          this.load();
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    save() {
      request.post("/department", this.form).then(res => {
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
      request.put("/department", this.form2).then(res => {
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
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>