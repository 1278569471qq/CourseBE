<template>
  <div class="home-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-id-badge"></i> 首页-教务要闻
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main-wrap">
      <el-container class="home-container">
        <el-aside class="home-aside" width="65%">
          <el-table :data="tableData" stripe>
            <el-table-column
              align="center"
              label="日期"
              prop="date"
              width="120px"
            ></el-table-column>
            <el-table-column
              align="center"
              label="标题"
              prop="title"
            ></el-table-column>
            <el-table-column align="center" label="操作" width="120px">
              <template slot-scope="scope">
                <el-button
                  @click="openNews(scope.row.url)"
                  size="mini"
                  type="primary"
                  >查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-aside>
        <el-aside class="home-aside" width="35%">
          <div class="aside-container">
            <el-card :body-style="{ padding: '0px' }">
              <div style="padding: 20px">
                <div>
                  <el-link href="https://element.eleme.io" target="_blank">默认链接</el-link>
                  <el-link type="primary">主要链接</el-link>
                  <el-link type="success">成功链接</el-link>
                  <el-link type="warning">警告链接</el-link>
                  <el-link type="danger">危险链接</el-link>
                  <el-link type="info">信息链接</el-link>
                </div>
              </div>
            </el-card>
          </div>
        </el-aside>
      </el-container>
    </div>
  </div>
</template>

<script>
import * as api from "../api/news";

export default {
  name: "Home",
  data() {
    return {
      tableData: []
    };
  },
  methods: {
    query() {
      api.get().then(res => {
        this.tableData = res;
      });
    },
    openNews(url) {
      window.open(url, "_blank");
    }
  },
  created() {
    this.query();
  }
};
</script>

<style scoped>
.home-wrap {
  height: 100%;
  width: 100%;
}

.main-wrap {
  margin-top: 10px;
  height: 100%;
  width: 100%;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.home-container {
  height: 100%;
  width: 90%;
  margin: auto;
}

.home-aside {
  height: 100%;
}

.aside-container {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  height: 200px;
  margin-top: 20px;
}

.aside-img {
  width: 100%;
}

.aside-title {
  color: #333;
  font-size: 18px;
}

.aside-content {
  font-size: 12px;
  color: #999;
}
</style>
