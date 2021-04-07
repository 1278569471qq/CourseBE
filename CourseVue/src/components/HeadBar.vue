<template>
  <div class="head-bar">
    <div class="header-ico">
      <i class="el-icon-s-home"></i>
    </div>
    <div class="logo">大学在线选课系统</div>
    <div class="head-right">
      <div class="head-user-con">
        <div @click="handleFullScreen" class="btn-fullscreen">
          <el-tooltip
            :content="fullscreen ? `退出全屏` : `全屏`"
            effect="dark"
            placement="bottom"
          >
            <i class="el-icon-rank"></i>
          </el-tooltip>
        </div>

        <div class="user-avatar">
          <img v-bind:src="imgUrl" />
        </div>

        <el-dropdown @command="handleCommand" class="user-name" trigger="click">
          <span class="el-dropdown-link">
            {{ username }}
            <i class="el-icon-caret-bottom"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            <el-dropdown-item v-if="check" command="binding">绑定QQ</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<script>
import { logout } from "../api/user";
import * as api from "../api/user";

export default {
  name: "Header",
  data() {
    return {
      fullscreen: false,
      check: true,
      imgUrl: "http://zzxblog.top/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20200208165111.jpg"
    };
  },
  computed: {
    username() {
      return this.$store.state.status.username;
    }
  },
  methods: {
    handleCommand(command) {
      if (command === "logout") {
        // eslint-disable-next-line no-undef
        QC.Login.signOut();
        logout().then(() => {
          this.$store.commit("logout");
          this.$message.success("注销成功");
          this.$router.push("/login");
        });
      };
      if (command === "binding") {
        console.log("绑定qq");
        // eslint-disable-next-line no-undef
        window.open('https://graph.qq.com/oauth2.0/show?which=Login&display=pc&client_id=101934691&response_type=token&scope=all&redirect_uri=http%3A%2F%2Fwww.zzxblog.top%2F%23%2Fauth', 'newwindow', 'height=500, width=700, top=100,left=550, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
      }
    },
    handleFullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          // IE11
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    }
  },
  created() {
    // eslint-disable-next-line no-undef
    const che = QC.Login.check();
    if (che) {
       api.getAuthorUrl().then(res => {
          this.imgUrl = res;
      });
    }
    api.getAuthorBind().then(res => {
      if (res){
        this.check = false;
      }
    });
  }
};
</script>
<style scoped>
.head-bar {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #242f42;
  background-color: #fff;
}

.header-ico {
  float: left;
  padding: 0 21px;
  line-height: 70px;
}

.head-bar .logo {
  float: left;
  width: 250px;
  line-height: 70px;
}

.head-right {
  float: right;
  padding-right: 50px;
}

.head-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}

.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}

.btn-fullscreen {
  position: relative;
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}

.btn-bell .el-icon-bell {
  color: #000000;
}

.user-name {
  margin-left: 10px;
}

.user-avatar {
  margin-left: 20px;
}

.user-avatar img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-dropdown-link {
  color: #000000;
  cursor: pointer;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>
