<template>
  <div class="student-course-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 选课管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="query-form">
        <el-row :gutter="20">
          <el-col :span="2">
            <el-button @click="create" icon="el-icon-plus" circle></el-button>
          </el-col>
          <el-col :span="2" style="width: 170px;margin-top:10px">
            <el-switch
              @change="updateAllowSelect"
              v-model="optionForm.allowSelect"
              active-color="#13ce66"
              inactive-color="#ff4949"
              inactive-text="允许学生选课"
            >
            </el-switch>
          </el-col>
          <el-col :span="2" style="width: 170px;margin-top: 10px">
            <el-switch
              @change="updateAllowGrade"
              v-model="optionForm.allowGrade"
              active-color="#13ce66"
              inactive-color="#ff4949"
              inactive-text="允许教师打分"
            >
            </el-switch>
          </el-col>
          <el-col :offset="5" :span="3" style="margin:0px;margin-left: 170px">
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
              :fetch-suggestions="querySearchMaj"
              :trigger-on-focus="false"
            ><i slot="prefix" class="el-input__icon el-icon-search"></i></el-autocomplete>
          </el-col>
          <el-col :span="3">
            <el-autocomplete
              @keyup.enter.native="query"
              placeholder="班级名"
              v-model="queryForm.className"
              :fetch-suggestions="querySearchCla"
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
          <el-table-column label="课程名" prop="courseName" />
          <el-table-column label="学生名" prop="studentName" />
          <el-table-column label="学生班级" prop="className" />
          <el-table-column label="日常分" min-width="80px" prop="dailyScore" />
          <el-table-column label="期末分" prop="examScore" width="80px" />
          <el-table-column label="总分" prop="score" width="80px" />
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
          <el-form-item label="学生">
            <el-select
              :disabled="entityForm.id !== -1"
              placeholder="请选择学生"
              v-model="entityForm.studentId"
            >
              <el-option
                :key="index"
                :label="item.name"
                :value="item.id"
                v-for="(item, index) in students"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="课程">
            <el-select
              :disabled="entityForm.id !== -1"
              placeholder="请选择课程"
              v-model="entityForm.courseId"
            >
              <el-option
                :key="index"
                :label="item.name"
                :value="item.id"
                v-for="(item, index) in courses"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="日常得分">
            <el-input type="number" v-model="entityForm.dailyScore"></el-input>
          </el-form-item>
          <el-form-item label="考试得分">
            <el-input type="number" v-model="entityForm.examScore"></el-input>
          </el-form-item>
          <el-form-item label="总分">
            <el-input type="number" v-model="entityForm.score"></el-input>
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
import * as api from "../../api/admin/studentCourse";
import * as CourseApi from "../../api/admin/course";
import * as StudentApi from "../../api/admin/student";
import * as OptionApi from "../../api/option";
import * as adminApi from "../../api/admin/admin";

export default {
  name: "AdminStudentCourse",
  data() {
    return {
      queryForm: {
        className: "",
        courseName: "",
        studentName: ""
      },
      optionForm: {
        allowSelect: true,
        allowGrade: true
      },
      entityForm: {},
      tableData: [],
      pageSize: api.pageSize,
      pageCount: 1,
      pageIndex: 1,
      editing: false,
      courses: [],
      students: [],
      claDepartments: [],
      majDepartments: [],
      courseDepartments: []
    };
  },
  methods: {
    querySearchMaj(queryString, cb) {
      const majDepartments = this.majDepartments;
      const results = queryString
              ? majDepartments.filter(this.createFilterPinyin(queryString))
              : majDepartments;
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
    querySearchCla(queryString, cb) {
      const claDepartments = this.claDepartments;
      const results = queryString
              ? claDepartments.filter(this.createFilterPinyin(queryString))
              : claDepartments;
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
      adminApi.getLikeData(1).then(res => {
        this.majDepartments = res;
      });
      adminApi.getLikeData(3).then(res => {
        this.claDepartments = res;
      });
      adminApi.getLikeData(6).then(res => {
        this.courseDepartments = res;
      });
    },
    query() {
      api
        .getPageCount(
          this.queryForm.className,
          this.queryForm.courseName,
          this.queryForm.studentName
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
          this.queryForm.className,
          this.queryForm.courseName,
          this.queryForm.studentName
        )
        .then(res => {
          this.tableData = res;
        });
    },
    create() {
      this.entityForm = {
        id: -1,
        studentId: null,
        courseId: null,
        dailyScore: null,
        examScore: null,
        score: null
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
    getCoursesAndStudents() {
      CourseApi.listName().then(res => {
        this.courses = res;
      });
      StudentApi.listName().then(res => {
        this.students = res;
      });
    },
    getSelectAndGradeStatus() {
      OptionApi.getAllowStudentSelect().then(res => {
        this.optionForm.allowSelect = res;
      });
      OptionApi.getAllowTeacherGrade().then(res => {
        this.optionForm.allowGrade = res;
      });
    },
    updateAllowSelect(val) {
      OptionApi.setAllowStudentSelect(val);
    },
    updateAllowGrade(val) {
      OptionApi.setAllowTeacherGrade(val);
    }
  },
  created() {
    this.query();
    this.getCoursesAndStudents();
    this.getSelectAndGradeStatus();
    this.getLikeData();
  }
};
</script>

<style scoped></style>
