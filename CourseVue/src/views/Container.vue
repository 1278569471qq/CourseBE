<template>
  <div class="home">
    <head-bar />
    <side-bar />

    <div class="content-box">
      <div class="content" v-loading="this.$store.state.loading">
        <router-view />
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
  </div>
</template>

<script>
import { getLoginStatus } from "../api/user";
import SideBar from "../components/SideBar";
import HeadBar from "../components/HeadBar";
import UserType from "../common/userType";
import Config from "../common/config";

export default {
  name: "Container",
  data() {
    return {
      webSock: null
    };
  },
  components: {
    HeadBar,
    SideBar
  },
  methods: {
    redirectHome(userType) {
      if (userType === UserType.student) {
        this.$router.push({ name: "student-home" });
      } else if (userType === UserType.teacher) {
        this.$router.push({ name: "teacher-home" });
      } else if (userType === UserType.admin) {
        this.$router.push({ name: "admin-home" });
      }
    },
    initWebSocket() {
      //初始化weosocket
      // this.websock = new WebSocket("ws://60.205.228.32:8080/web/socket/"+ this.$store.state.status.username);
      if (this.webSock != null) {
        return;
      }
      getLoginStatus().then(res => {
        if (res.loggedIn && this.webSock == null) {
          const url = Config.backSockUrl + "/user/" + res.username;
          this.webSock = new WebSocket(url);
          this.webSock.onmessage = this.webSocketOnMessage();
          this.webSock.onopen = this.webSocketOnopen();
          this.webSock.onerror = this.webSocketOnError();
          this.webSock.onclose = this.webSocketClose();
        } else {
          return;
        }
      });
    },
    webSocketOnopen() {
      console.log("//连接建立成功")
      //连接建立之后执行send方法发送数据
      // const actions = { test: "12345" };
      // this.websocketsend(JSON.stringify(actions));
    },
    webSocketOnError() {
      console.log("//连接建立失败重连");
      this.initWebSocket();
    },
    webSocketOnMessage(e) {
      //数据接收
      if (e == null) {
        return;
      }
      const result = JSON.parse(e.data);
      console.log(result);
    },
    webSocketSend(Data) {
      //数据发送
      this.webSock.send(Data);
    },
    webSocketClose(e) {
      //关闭
      console.log("断开连接", e);
    }
  },
  created() {

  },
  mounted() {
    getLoginStatus().then(res => {
      this.initWebSocket();
      this.$store.commit("login", res);
      if (!res.loggedIn) {
        this.$router.push({ name: "login" });
      } else if (this.$route.path === "/") {
        this.redirectHome(res.userType);
      }
    });
  }
};
</script>

<style scoped>
.content-box {
  position: absolute;
  left: 200px;
  right: 0;
  top: 70px;
  bottom: 0;
  background: #f0f0f0;
}

.content {
  width: auto;
  height: 100%;
  padding: 10px;
  overflow-y: scroll;
  box-sizing: border-box;
}
</style>
