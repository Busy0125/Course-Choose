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
      <el-table-column prop="id" label="课号" sortable/>
      <el-table-column prop="name" label="课名"/>
      <el-table-column prop="credit" label="学分" sortable/>
      <el-table-column prop="department" label="学院" sortable/>

      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除此课程吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="text" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        style="margin-top: 10px;"
        :currentPage="currentPage"
        :page-size="pageSize"
        :page-sizes="[5, 10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />

    <el-dialog v-model="dialogVisible" title="新增课程" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="课号">
          <el-input v-model="form.id" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="课名">
          <el-input v-model="form.name" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="form.credit" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="学院">
          <el-select v-model="form.department" placeholder="选择学院">
            <el-option
                v-for="item in dep"
                :label="item.departmentName"
                :value="item.department"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogVisible2" title="编辑课程信息" width="30%">
      <el-form :model="form2" label-width="120px">
        <el-form-item label="课号">
          <el-input v-model="form2.id" style="width: 80%;" readonly/>
        </el-form-item>
        <el-form-item label="课名">
          <el-input v-model="form2.name" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="form2.credit" style="width: 80%;"/>
        </el-form-item>
        <el-form-item label="学院">
          <el-select v-model="form2.department" placeholder="选择学院">
            <el-option
                v-for="item in dep"
                :label="item.departmentName"
                :value="item.department"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span>
        <el-button @click="dialogVisible2 = false;">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ManageCourse",
  data() {
    return {
      search: '',
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogVisible2: false,
      form: {},
      form2: {},
      dep: [],
      selectDep: ''
    }
  },
  created() {
    this.getDepartment();
    this.load();
  },
  methods: {
    load() {
      request.get("/course", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          selectDep: this.selectDep
        }
      }).then(res => {
        // 后端传过来的res中department字段为数字，在前端需要展示汉字，因此在此做一个转换
        for (let i = 0; i < res.data.records.length; i++) {
          for (let j = 0; j < this.dep.length; j++) {
            if (res.data.records[i]["department"] == this.dep[j]["department"]) {
              res.data.records[i]["department"] = this.dep[j]["departmentName"]
            }
          }
        }
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    save() {
      request.post("/course", this.form).then(res => {
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
      request.put("/course", this.form2).then(res => {
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
    },

    // 获取department表
    getDepartment() {
      request.get("/department").then(res => {
        this.dep = res["data"];
      })
    },

    handleEdit(row) {
      // 此处实参row当中department字段中为汉字，需要将其转换为数字，保证与后端交互时字段一一对应
      for (let i = 0; i < this.dep.length; i++) {
        if (this.dep[i].departmentName == row.department) {
          row.department = this.dep[i].department;
        }
      }
      this.form2 = JSON.parse(JSON.stringify(row));
      this.dialogVisible2 = true;
    },
    handleDelete(id) {
      request.delete("/course/" + id).then(res => {
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