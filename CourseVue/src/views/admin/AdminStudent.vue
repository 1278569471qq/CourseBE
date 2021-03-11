<template>
  <div class="student-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-user"></i> 学生管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :span="2">
            <el-button @click="create" icon="el-icon-plus" circle></el-button>
          </el-col>
          <el-col :offset="10" :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="学生姓名"
              v-model="queryForm.name"
              :fetch-suggestions="querySearchStu"
              :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="专业名"
              v-model="queryForm.majorName"
              :fetch-suggestions="querySearchMajor"
              :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="班级名"
              v-model="queryForm.className"
              :fetch-suggestions="querySearchClass"
              :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-button @click="query" icon="el-icon-search" circle></el-button>
          </el-col>
        </el-row>
      </div>

      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="学号" prop="number" />
          <el-table-column label="姓名" prop="name" />
          <el-table-column label="班级" prop="className" />
          <el-table-column label="专业" min-width="150px" prop="majorName" />
          <el-table-column label="性别" prop="sex" width="80px" />
          <el-table-column
            label="上次登录"
            prop="lastLoginTime"
            width="130px"
          />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button @click="edit(scope.row.id)" type="primary" icon="el-icon-edit" circle></el-button>
              <el-button @click="deleteItem(scope.row.id)" type="danger" icon="el-icon-delete" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-row justify="center" type="flex" style="padding: 15px;">
        <el-pagination
          :current-page.sync="pageIndex"
          :page-size="pageSize"
          :total="pageSize * pageCount"
          @current-change="getPage"
          background
          layout="prev, pager, next"
        >
        </el-pagination>
      </el-row>
      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="姓名">
            <el-input v-model="entityForm.name"></el-input>
          </el-form-item>
          <el-form-item label="学号">
            <el-input type="number" v-model="entityForm.number"></el-input>
          </el-form-item>
          <el-form-item label="所属班级">
            <el-select placeholder="请选择班级" v-model="entityForm.classId">
              <el-option
                :key="index"
                :label="item.name"
                :value="item.id"
                v-for="(item, index) in classes"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="entityForm.password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="entityForm.email"></el-input>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker
              format="yyyy-MM-dd"
              placeholder="选择生日"
              type="date"
              v-model="entityForm.birthday"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="entityForm.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
          <el-button @click="save" type="primary">确 定</el-button>
          <el-button @click="editing = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as api from "../../api/admin/student";
import * as classApi from "../../api/admin/class";
import * as adminApi from "../../api/admin/admin";

export default {
  name: "AdminStudent",
  data() {
    return {
      queryForm: {
        majorName: "",
        className: "",
        name: ""
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      classes: [],
      majorDepartments: [],
      claDepartments: [],
      stuDepartments: []
    };
  },
  methods: {
    querySearchMajor(queryString, cb) {
      const majorDepartments = this.majorDepartments;
      const results = queryString
              ? majorDepartments.filter(this.createFilterPinyin(queryString))
              : majorDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    querySearchClass(queryString, cb) {
      const claDepartments = this.claDepartments;
      const results = queryString
              ? claDepartments.filter(this.createFilterPinyin(queryString))
              : claDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    querySearchStu(queryString, cb) {
      const stuDepartments = this.stuDepartments;
      const results = queryString
              ? stuDepartments.filter(this.createFilterPinyin(queryString))
              : stuDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilterPinyin(queryString) {
      return restaurants => {
        return (restaurants.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) ||
                (restaurants.pinyin.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    getLikeData() {
      adminApi.getLikeData(4).then(res => {
        this.stuDepartments = res;
      });
      adminApi.getLikeData(1).then(res => {
        this.majorDepartments = res;
      });
      adminApi.getLikeData(3).then(res => {
        this.claDepartments = res;
      });
    },
    query() {
      api
        .getPageCount(
          this.queryForm.majorName,
          this.queryForm.className,
          this.queryForm.name
        )
        .then(res => {
          this.pageCount = res;
          this.pageIndex = 1;
          this.getPage(1);
        });
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.majorName,
          this.queryForm.className,
          this.queryForm.name
        )
        .then(res => {
          for (let i = 0; i < res.length; i++) {
            res[i].sex = res[i].sex === 1 ? "男" : "女";
          }
          this.tableData = res;
        });
    },
    create() {
      this.entityForm = {
        id: -1,
        number: "",
        name: "",
        classId: "",
        password: "",
        email: null,
        birthday: null,
        sex: 0
      };
      this.editing = true;
    },
    edit(id) {
      api.get(id).then(res => {
        this.entityForm = res;
        this.editing = true;
      });
    },
    save() {
      if (this.entityForm.id === -1) {
        api.create(this.entityForm).then(() => {
          this.finishSave();
        });
      } else {
        api.update(this.entityForm).then(() => {
          this.finishSave();
        });
      }
    },
    finishSave() {
      this.$message.success("成功");
      this.getPage(this.pageIndex);
      this.editing = false;
    },
    deleteItem(id) {
      api.deleteItem(id).then(() => {
        this.$message.success("删除成功");
        this.getPage(this.pageIndex);
      });
    },
    getClasses() {
      classApi.listName().then(res => {
        this.classes = res;
      });
    }
  },
  created() {
    this.query();
    this.getClasses();
    this.getLikeData();
  }
};
</script>

<style scoped></style>
