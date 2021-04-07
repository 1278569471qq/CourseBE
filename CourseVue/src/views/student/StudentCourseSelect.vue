<template>
  <div class="course-select-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-book"></i> 选修课程
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
                placeholder="教师"
                v-model="queryForm.teacherName"
                :fetch-suggestions="querySearchTeacher"
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
          <el-table-column label="课程Id" prop="courseId" />
          <el-table-column label="课程名" prop="courseName" width="180px" />
          <el-table-column label="教师" prop="teacherName" />
          <el-table-column label="学分" prop="credit" />
          <el-table-column
            align="center"
            label="上课时间"
            prop="time"
            width="130px"
          />
          <el-table-column label="已选人数" prop="selectedCount" />
          <el-table-column label="课程容量" prop="maxSize" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button
                @click="select(scope.row.courseId)"
                size="mini"
                type="success"
                :disabled="allow"
                >选修
              </el-button>
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
    </div>
  </div>
</template>

<script>
import * as api from "../../api/student/courseSelect";
import * as adminApi from "../../api/admin/admin";

export default {
  name: "StudentCourseSelect",
  data() {
    return {
      queryForm: {
        courseName: "",
        teacherName: ""
      },
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      courseDepartments: [],
      teaDepartments: [],
      allow: false
    };
  },
  methods: {
    query() {
      api
        .getPageCount(this.queryForm.courseName, this.queryForm.teacherName)
        .then(res => {
          this.pageCount = res;
          this.pageIndex = 1;
          this.getPage(1);
        });
      adminApi.getStudentLikeData(1).then(res => {
        this.courseDepartments = res;
      });
      adminApi.getStudentLikeData(2).then(res => {
        this.teaDepartments = res;
      });
    },
    querySearchCour(queryString, cb) {
      const courseDepartments = this.courseDepartments;
      const results = queryString
          ? courseDepartments.filter(this.createFilterPinyin(queryString))
          : courseDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilterPinyin(queryString) {
      return restaurants => {
        return (restaurants.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) ||
            (restaurants.pinyin.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    querySearchTeacher(queryString, cb) {
      const teaDepartments = this.teaDepartments;
      const results = queryString
          ? teaDepartments.filter(this.createFilterPinyin(queryString))
          : teaDepartments;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    getPage(pageIndex) {
      api
        .getPage(
          pageIndex,
          this.queryForm.courseName,
          this.queryForm.teacherName
        )
        .then(res => {
          this.tableData = res;
        });
    },
    select(id) {
      api.select(id).then(() => {
        this.$message.success("选修成功!");
        this.getPage(this.pageIndex);
      });
    }
  },
  created() {
    this.query();
    api.allow().then(res =>{
      this.allow = res;
    });
  }
};
</script>

<style scoped></style>
