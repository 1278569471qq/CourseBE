<template>
  <div class="course-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-edit"></i> 学生课程
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="table">
        <el-table :data="tableData" stripe>
          <el-table-column label="选课Id" prop="studentCourseId" />
          <el-table-column label="课程名" prop="courseName" />
          <el-table-column label="教师" prop="teacherName" />
          <el-table-column label="学分" prop="credit" />
          <el-table-column label="日常成绩" prop="dailyScore" />
          <el-table-column label="考试成绩" prop="examScore" />
          <el-table-column label="总成绩" prop="score" />
          <el-table-column align="center" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button
                @click="deleteItem(scope.row.studentCourseId)"
                size="mini"
                type="danger"
                :disabled="allow"
                >退选
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import * as api from "../../api/student/course";
import * as api2 from "../../api/student/courseSelect";

export default {
  name: "StudentCourse",
  data() {
    return {
      tableData: [],
      allow: false
    };
  },
  methods: {
    getList() {
      api.list().then(res => {
        this.tableData = this.scoreFilter(res);
      });
    },
    deleteItem(studentCourseId) {
      api.deleteItem(studentCourseId).then(() => {
        this.$message.success("退选成功!");
        this.getList();
      });
    },
    scoreFilter(data) {
      let filtered = [];
      for (let i = 0; i < data.length; i++) {
        let item = data[i];
        if (
                item.dailyScore == null ||
                item.examScore == null ||
                item.score == null
        ) {
          filtered.push(item);
        }
      }
      return filtered;
    }
  },
  created() {
    this.getList();
    api2.allow().then(res =>{
      this.allow = res;
    });
  }
};
</script>

<style scoped></style>
