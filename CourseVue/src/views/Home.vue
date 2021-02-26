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
        <!--        <el-aside class="home-aside" width="35%">
          <div class="aside-container">
            <el-card :body-style="{ padding: '0px' }">
              <div style="padding: 20px">
                <div v-for = "item in tableNewData" :key="item">
                  <el-link @click="openNews(item.url)" target="_blank">{{item.title}}</el-link>
                </div>
              </div>
            </el-card>
          </div>
        </el-aside>-->
        <!--        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane name="first" label="最新消息">
            <el-card :body-style="{ padding: '0px'  }">
              <div style="padding: 20px;">
                <div v-for = "item in tableNewData" :key="item">
                  <el-link @click="openNews(item.url)" target="_blank">{{item.title}}</el-link>
                </div>
              </div>
            </el-card>
          </el-tab-pane>
        </el-tabs>-->
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>最新消息</span>
          </div>
          <div v-for ="item in tableNewData" :key="item" class="text item">
            <el-link @click="openNews(item.url)" target="_blank">{{item.title}}</el-link>
          </div>
        </el-card>
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
      tableData: [],
      tableNewData: [],
      activeName: 'first'
    };
  },
  methods: {
    query() {
      api.get().then(res => {
        this.tableData = res;
      });
      api.getNew().then(res => {
        this.tableNewData = res;
      });
    },
    openNews(url) {
      window.open(url, "_blank");
    },
    handleClick(tab, event) {
      console.log(tab, event);
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
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
  height: 570px;
}
</style>
