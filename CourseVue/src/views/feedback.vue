<template>
  <div class="course-wrap">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-fa fa-book"></i> 日志
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="container">
      <div class="table">
        <el-table
          :data="tableData"
          style="width: 100%"
          :row-class-name="tableRowClassName"
        >
          <el-table-column prop="datetime" label="日期" width="180">
          </el-table-column>
          <el-table-column prop="userName" label="名字" width="180">
          </el-table-column>
          <el-table-column prop="ip" label="用户ip"> </el-table-column>
          <el-table-column prop="location" label="ip地址"> </el-table-column>
          <el-table-column prop="executeTime" label="执行时间（ms）">
          </el-table-column>
          <el-table-column prop="businessTarget" label="方法">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
<script>
import * as api from "../api/user";
export default {
  name: "feedback",
  data() {
    return {
      tableData: [],
      websock: null
    };
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    tableRowClassName({ row, rowIndex }) {
      if (row.exception != null) {
        return "warning-row";
      } else if (row.exception == null) {
        return "success-row";
      }
      return "";
    },
    initWebSocket() {
      //初始化weosocket
      this.websock = new WebSocket("ws://60.205.228.32:8080/web/socket/zzx");
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen() {
      console.log("//连接建立成功")
      //连接建立之后执行send方法发送数据
      // const actions = { test: "12345" };
      // this.websocketsend(JSON.stringify(actions));
    },
    websocketonerror() {
      console.log("//连接建立失败重连");
      this.initWebSocket();
    },
    websocketonmessage(e) {
      //数据接收
      console.log("socket")
      const result = JSON.parse(e.data);
      this.tableData.unshift(result);
    },
    websocketsend(Data) {
      //数据发送
      this.websock.send(Data);
    },
    websocketclose(e) {
      //关闭
      console.log("断开连接", e);
    }
  },
  destroyed() {
    this.websock.close(); //离开路由之后断开websocket连接
  },
  created() {
    this.initWebSocket();
    api.getLogs(1).then(res => {
      this.tableData = res;
    });

    // setInterval(() => {
    //   this.tableData.unshift({
    //     userId: 29,
    //     userName: "zsdsadasdzx",
    //     userType: 3,
    //     requestUrl: "/qq/author/bind",
    //     businessTarget: "sdk.QqAuth.getAuthorBind",
    //     message: "success",
    //     exception: null,
    //     ip: "103.107.216.231",
    //     location: "北京市",
    //     resultCode: 0,
    //     executeTime: 18,
    //     datetime: "2021-03-11 17:19:15"
    //   });
    // }, 1000);
  }
};
</script>

<style scoped></style>
