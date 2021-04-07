<template>
  <div class="grade-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 成绩录入
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :offset="15" :span="3">
            <el-autocomplete
                @keyup.enter.native="query"
                placeholder="课程名"
                v-model="queryForm.courseName"
                :fetch-suggestions="querySearchCour"
                :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-autocomplete
                @keyup.enter.native="query"
                placeholder="学生姓名"
                v-model="queryForm.studentName"
                :fetch-suggestions="querySearchStu"
                :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>

          <el-col :span="3">
            <el-button @click="query" icon="el-icon-search" type="primary"
              >搜索
            </el-button>
          </el-col>
        </el-row>
      </div>

      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="选课Id" prop="studentCourseId" />
          <el-table-column label="课程名" prop="courseName" />
          <el-table-column label="学生姓名" prop="studentName" />
          <el-table-column label="日常分" prop="dailyScore" />
          <el-table-column label="笔试分" prop="examScore" />
          <el-table-column label="成绩" prop="score" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button
                @click="edit(scope.row.studentCourseId)"
                size="mini"
                type="success"
                :disabled="allow">打分
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog :visible.sync="editing" title="编辑" width="30%">
        <el-form :model="entityForm" label-width="70px" ref="form">
          <el-form-item label="选课Id">
            <el-input
              disabled
              type="number"
              v-model="entityForm.studentCourseId"
            ></el-input>
          </el-form-item>
          <el-form-item label="日常成绩">
            <el-input type="number" v-model="entityForm.dailyScore"></el-input>
          </el-form-item>
          <el-form-item label="笔试成绩">
            <el-input type="number" v-model="entityForm.examScore"></el-input>
          </el-form-item>
          <el-form-item label="总成绩">
            <el-input type="number" v-model="entityForm.score"></el-input>
          </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
          <el-button @click="save" type="primary">确 定</el-button>
          <el-button @click="editing = false">取 消</el-button>
        </span>
      </el-dialog>
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
  </div>
</template>

<script>
import * as api from "../../api/teacher/grade";
import * as adminApi from "../../api/admin/admin";

export default {
  name: "TeacherGrade",
  data() {
    return {
      queryForm: {
        courseName: "",
        studentName: ""
      },
      entityForm: {},
      tableData: [],
      student: [],
      courseDepartments: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      allow: true
    };
  },
  methods: {
    query() {
      api
        .getPageCount(this.queryForm.courseName, this.queryForm.studentName)
        .then(res => {
          this.pageCount = res;
          this.pageIndex = 1;
          this.getPage(1);
        });
      adminApi.getTeacherLikeData(1).then(res => {
        this.student = res;
      });
      adminApi.getTeacherLikeData(2).then(res => {
        this.courseDepartments = res;
      });
    },
    allowT(){
      api.getAllow().then(res => {
        this.allow=res;
      });
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.courseName,
          this.queryForm.studentName
        )
        .then(res => {
          this.tableData = res;
        });
    },
    createFilterPinyin(queryString) {
      return restaurants => {
        return (restaurants.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) ||
            (restaurants.pinyin.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    querySearchStu(queryString, cb) {
      const stuDepartments = this.student;
      const results = queryString
          ? stuDepartments.filter(this.createFilterPinyin(queryString))
          : stuDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    querySearchCour(queryString, cb) {
      const courseDepartments = this.courseDepartments;
      const results = queryString
          ? courseDepartments.filter(this.createFilterPinyin(queryString))
          : courseDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    edit(id) {
      api.get(id).then(res => {
        this.entityForm = res;
        this.editing = true;
      });
    },
    save() {
      api.update(this.entityForm).then(() => {
        this.$message.success("成功");
        this.getPage(this.pageIndex);
        this.editing = false;
      });
    }
  },
  created() {
    this.allowT();
    this.query();
  }
};
</script>

<style scoped></style>
