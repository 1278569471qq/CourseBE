<template>
  <div class="course-wrap">
    <el-tabs type="border-card">
      <el-tab-pane>
        <span slot="label"><i class="el-icon-date"></i> 日志记录</span>
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
      </el-tab-pane>
      <el-tab-pane label="角色管理">
        <div class="table">
          <el-table
                  :data="userTableData"
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
      </el-tab-pane>
    </el-tabs>
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
import {getLoginStatus} from "../api/user";
import Config from "../common/config";
export default {
  name: "feedback",
  data() {
    return {
      tableData: [],
      userTableData: [],
      websock: null,
      total: 0
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
      if (this.websock != null) {
        return;
      }
      getLoginStatus().then(res => {
        if (res.loggedIn && this.websock == null) {
          const url = Config.backSockUrl + "/log/" + res.username;
          this.websock = new WebSocket(url);
          this.websock.onmessage = this.websocketonmessage;
          this.websock.onopen = this.websocketonopen;
          this.websock.onerror = this.websocketonerror;
          this.websock.onclose = this.websocketclose;
        } else {
          return;
        }
      });
    },
    websocketonopen() {
      console.log("//连接建立成功")
      //连接建立之后执行send方法发送数据
      // const actions = { test: "12345" };
      // this.websocketsend(JSON.stringify(actions));
    },
    websocketonerror() {
      console.log("//连接建立失败重连");
      if(this.websock != null) {
        this.websock.close(); //离开路由之后断开websocket连接
      }
      this.initWebSocket();
    },
    websocketonmessage(e) {
      //数据接收
      if (e == null) {
        return;
      }
      console.log("message");
      const result = JSON.parse(e.data);
      this.tableData.unshift(result);
    },
    websocketsend(Data) {
      //数据发送
      this.websock.send(Data);
    },
    websocketclose(e) {
      //关闭
      if(this.websock != null) {
        this.websock.close(); //离开路由之后断开websocket连接
      }
      console.log("断开连接", e);
    }
  },
  destroyed() {
    if(this.websock != null) {
      this.websock.close(); //离开路由之后断开websocket连接
    }
    Config.backEndUrl
  },
  created() {
    this.initWebSocket();
    api.getLogs(1).then(res => {
      this.tableData = res;
    });
    api.getCurrentUsers().then(res => {
      this.userTableData = res.users;
      this.total = res.total;
    });
  },
  mounted() {
    setInterval(()=>{
      api.getCurrentUsers().then(res => {
        this.userTableData = res.users;
        this.total = res.total;
      });
    },10000);
  }
};
</script>

<style scoped></style>
