<template>
  <div class="major-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-graduation-cap"></i> 专业管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :span="2">
            <el-button @click="create" icon="el-icon-plus" circle></el-button>
          </el-col>
          <el-col :offset="13" :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="专业名"
              v-model="queryForm.name"
              :fetch-suggestions="querySearchMajor"
              :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="系名"
              v-model="queryForm.departmentName"
              :fetch-suggestions="querySearchDep"
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
          <el-table-column label="专业名" prop="name" />
          <el-table-column label="所属系" prop="departmentName" />
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
          <el-form-item label="专业名">
            <el-input v-model="entityForm.name"></el-input>
          </el-form-item>
          <el-form-item label="所属系">
            <el-select placeholder="请选择系" v-model="entityForm.departmentId">
              <el-option
                :key="index"
                :label="item.name"
                :value="item.id"
                v-for="(item, index) in departments"
              />
            </el-select>
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
import * as api from "../../api/admin/major";
import * as departmentApi from "../../api/admin/department";
import * as adminApi from "../../api/admin/admin";

export default {
  name: "AdminMajor",
  data() {
    return {
      queryForm: {
        departmentName: "",
        name: ""
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      departments: [],
      majorDepartments: [],
      depDepartments: []
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
    querySearchDep(queryString, cb) {
      const depDepartments = this.depDepartments;
      const results = queryString
              ? depDepartments.filter(this.createFilterPinyin(queryString))
              : depDepartments;
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
      adminApi.getLikeData(2).then(res => {
        this.depDepartments = res;
      });
      adminApi.getLikeData(1).then(res => {
        this.majorDepartments = res;
      });
    },
    query() {
      api
        .getPageCount(this.queryForm.departmentName, this.queryForm.name)
        .then(res => {
          this.pageCount = res;
          this.pageIndex = 1;
          this.getPage(1);
        });
    },
    getPage(pageIndex) {
      api
        .getPage(pageIndex, this.queryForm.departmentName, this.queryForm.name)
        .then(res => {
          this.tableData = res;
        });
    },
    create() {
      this.entityForm = {
        id: -1,
        name: "",
        departmentId: ""
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
    getDepartments() {
      departmentApi.listName().then(res => {
        this.departments = res;
      });
    }
  },
  created() {
    this.query();
    this.getDepartments();
    this.getLikeData();
  }
};
</script>

<style scoped></style>
