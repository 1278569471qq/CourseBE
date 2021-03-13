<template>
  <div class="login-wrap">
    <div class="login-form">
      <div class="form-title">大学生在线选课系统</div>
      <el-form
        :model="formData"
        :rules="rules"
        class="form-content"
        label-width="0px"
        ref="form"
      >
        <el-form-item prop="username">
          <el-input class="textarea" placeholder="学号/工号/用户名" v-model="formData.username" prefix-icon="el-icon-user">
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            @keyup.enter.native="submit()"
            placeholder="密码"
            type="password"
            v-model="formData.password"
            prefix-icon="el-icon-edit"
            class="textarea"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="userType">
          <el-radio-group v-model="formData.userType">
            <el-radio label="1">学生</el-radio>
            <el-radio label="2">教师</el-radio>
            <el-radio label="3">教务管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="login-btn" v-loading="this.$store.state.loading">
          <el-button style="border-radius: 30px;height: 44px" @click="submit()" type="primary">点击登录</el-button>
        </div>
        <el-divider content-position="center" style="color: #8c939d;"><span class="divider11">第三方登陆</span></el-divider>
        <a   href="javascript:void(0)"
             class="qqIcon"
             @click="qqLoginClick('qq')"
             id="qqLoginBtn"
        ><img src="../../public/static/qq.png" class="login-qq"/></a>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {getLoginStatus, login} from "../api/user";
export default {
  data: function() {
    return {
      formData: {
        username: "",
        password: "",
        userType: "1"
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        userType: [
          { required: true, message: "请选择用户类型", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          login(
            this.formData.username,
            this.formData.password,
            this.formData.userType
          ).then(res => {
            this.$message.success("登录成功: " + res.username);
            this.$store.commit("login", res);
            this.$router.push({ name: "container" });
          });
        }
      });
    },
    // QQ 第三方登录
    qqLoginClick() {
      window.open('https://graph.qq.com/oauth2.0/show?which=Login&display=pc&client_id=101934691&response_type=token&scope=all&redirect_uri=http%3A%2F%2Fwww.zzxblog.top%2F%23%2Fauth', 'newwindow', 'height=500, width=700, top=100,left=550, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
      // 直接弹出授权页面，授权过后跳转到回调页面进行登录处理
      // eslint-disable-next-line no-undef
      //  QC.Login.showPopup({
      //   appId: '101934691',
      //   redirectURI: 'http://www.zzxblog.top/#/auth'
      // });
    }
  },
  mounted: async function () {
    getLoginStatus().then(res => {
      this.$store.commit("login", res);
      if (res.loggedIn) {
        this.$router.push("/");
      }
    });
  }
};
</script>

<style scoped>

.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url("../assets/login-background.jpeg");
  background-size: 100% 100%;
}

.form-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 40px;
  font-weight: bold;
  color: #3a8ee6;
}

.login-form {
  position: absolute;
  left: 20%;
  top: 45%;
  width: 425px;
  margin: -190px 0 0 -175px;
  border-radius: 30px;
  background: rgba(0, 0, 0, 0);
  overflow: hidden;
}
.textarea>>>.el-input__inner{
  border-radius: 30px;
  height: 47px;
}
.divider11>>>.el-divider{
  color: #8c939d;
}
.el-divider__text {
  color: #a8acb5;
}
.form-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
}
.login-qq {
  width: 10%;
  height: 10%;
  padding-left: 46%;
  align:center;
}
.el-radio {
  color: #8c939d;
}
</style>
