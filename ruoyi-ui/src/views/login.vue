<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">渐行渐远后台管理系统</h3>
      <el-form-item prop="userName">
        <el-input
          v-model="loginForm.userName"
          type="text"
          auto-complete="off"
          placeholder="账号"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          show-password
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <div style="display: flex;width: 100%;justify-content: space-between">
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
        <el-dropdown style="width: 100px" @command="handleClick" size="mini" split-button type="primary">
          <span>{{ sourceName }}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="seller-user" icon="el-icon-user-solid">商家端</el-dropdown-item>
            <el-dropdown-item command="system-user" icon="el-icon-s-platform">系统端</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">商家入驻</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2024 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import {getCodeImg} from "@/api/login";
import Cookies from "js-cookie";
import {encrypt, decrypt} from '@/utils/jsencrypt'
import {v4 as uuidv4} from 'uuid';

export default {
  name: "Login",
  data() {
    return {
      uuid: '',
      codeUrl: "",
      sourceName: '商家端',
      loginForm: {
        source: "seller-user",
        userName: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        userName: [
          {required: true, trigger: "blur", message: "请输入您的账号"}
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"}
        ],
        code: [{required: true, trigger: "change", message: "请输入验证码"}]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: true,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.uuid = this.generateUUID();
    this.getCode();
    this.getCookie();
  },
  methods: {
    generateUUID() {
      return uuidv4().replace(/-/g, ''); // 去掉UUID中的连字符
    },
    handleClick(command) {

      if (command === 'system-user') {
        this.sourceName = '系统端'
        this.loginForm.source = 'system-user'
      } else {
        this.sourceName = '商家端'
        this.loginForm.source = 'seller-user'
      }

    },
    getCode() {

      getCodeImg({uuid:this.uuid}).then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });


    },
    getCookie() {


      const userName = Cookies.get("userName");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')


      this.loginForm = {
        userName: userName === undefined ? this.loginForm.userName : userName,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
        source: this.loginForm.source,
      };


    },
    handleLogin() {

      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("userName", this.loginForm.userName, {expires: 30});
            Cookies.set("password", encrypt(this.loginForm.password), {expires: 30});
            Cookies.set('rememberMe', this.loginForm.rememberMe, {expires: 30});
          } else {
            Cookies.remove("userName");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          console.log(this.loginForm)
          this.$store.dispatch("Login", this.loginForm).then(() => {
            Cookies.set("source", this.loginForm.source, {expires: 30});

            this.$router.push({path: this.redirect || "/"}).catch(() => {
            });
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 101px;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>
