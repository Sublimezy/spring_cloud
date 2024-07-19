<template>
  <div class="register">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <h3 class="title">渐行渐远商家入驻</h3>

      <el-form-item prop="userName">
        <el-input v-model="registerForm.userName" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="nickName">
        <el-input v-model="registerForm.nickName" type="text" auto-complete="off" placeholder="经营人">
          <svg-icon slot="prefix" icon-class="shenfeng" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="registerForm.name" type="text" auto-complete="off" placeholder="店铺名称">
          <svg-icon slot="prefix" icon-class="shop" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <el-form-item prop="type">

        <el-select style="width: 100%" v-model="registerForm.type" placeholder="店铺类型">
          <el-option
            v-for="dict in dict.type.distance_shop_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
          <svg-icon slot="prefix" icon-class="shopType" class="el-input__icon input-icon"/>
        </el-select>


      </el-form-item>
      <el-form-item prop="avgPrice">
        <el-input v-model="registerForm.avgPrice" minlength="0" maxlength="999999"
                  oninput="value=value.replace(/[^0-9.]/g,'')" auto-complete="off" placeholder="均价(元)">
          <svg-icon slot="prefix" icon-class="shop" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="images">
        <div class="text-center">
          <userAvatar :avatar="registerForm.images"/>
        </div>
      </el-form-item>

      <el-form-item prop="images">
        <div style="height: 600px">
          <mymap/>
        </div>

      </el-form-item>

      <el-form-item prop="area">
        <el-input v-model="registerForm.area" placeholder="请输入商圈"/>
      </el-form-item>
      <el-form-item prop="address">
        <el-input disabled v-model="address" placeholder="请输入地址"/>
      </el-form-item>

      <el-form-item prop="address">
        <el-input disabled v-model="lng" placeholder="请输入经度"/>
      </el-form-item>

      <el-form-item prop="address">
        <el-input disabled v-model="lat" placeholder="请输入纬度"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="registerForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleRegister"
          show-password
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <el-form-item prop="confirmPassword">
        <el-input
          v-model="registerForm.confirmPassword"
          type="password"
          auto-complete="off"
          placeholder="确认密码"
          @keyup.enter.native="handleRegister"
          show-password
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          v-model="registerForm.email"
          auto-complete="off"
          placeholder="邮箱"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="emailpro" class="el-input__icon input-icon"/>
        </el-input>
      </el-form-item>

      <el-form-item prop="emailCode">
        <el-input
          style="width: 220px"
          v-model="registerForm.emailCode"
          type="password"
          auto-complete="off"
          placeholder="邮箱验证码"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="emailCode" class="el-input__icon input-icon"/>
        </el-input>
        <div class="register-code">
          <el-button type="primary" :loading="emailLoading" @click="email">{{ showEmail }}</el-button>
        </div>
      </el-form-item>


      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="registerForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 220px"
          @keyup.enter.native="handleRegister"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon"/>
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img"/>
        </div>
      </el-form-item>

      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleRegister"
        >
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <span>Copyright © 2018-2024 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import {getCodeImg, getEmail, register} from "@/api/login";
import {v4 as uuidv4} from 'uuid';
import Cookies from "js-cookie";
import userAvatar from "@/views/system/user/profile/userAvatar.vue";
import mymap from "@/views/map/map/index.vue";
import {mapGetters} from "vuex";

export default {
  name: "Register",
  components: {mymap, userAvatar},
  dicts: ['distance_shop_type'],
  computed: {
    ...mapGetters(['img', 'address', 'lng', 'lat'])
  },
  watch: {
    address(newValue, oldValue) {
      this.registerForm.address = newValue
    },
    lng(newValue, oldValue) {
      this.registerForm.x = newValue
    },
    lat(newValue, oldValue) {
      this.registerForm.y = newValue
    },

    img(newValue, oldValue) {
      this.flag = null
      this.registerForm.images = newValue
    }
  },
  data() {

    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      flag: null,
      seconds: 120,
      emailLoading: false,
      showEmail: '邮箱验证',
      uuid: '',
      codeUrl: "",
      registerForm: {
        userName: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
        email: "",
        emailCode: "",
        emailType: "REGISTER",//邮箱验证码主题
        type: "",    //店铺类型
        images: "",
        source: "seller-user",
        avgPrice: ""
      },
      registerRules: {
        userName: [
          {required: true, trigger: ['blur', 'change'], message: "请输入您的账号"},
          {min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur'}
        ],
        nickName: [
          {required: true, trigger: ['blur', 'change'], message: "请输入您的姓名"},
        ],
        name: [
          {required: true, trigger: ['blur', 'change'], message: "请输入您的店铺名"},
        ],
        type: [
          {required: true, message: '请选择店铺类型', trigger: ['blur', 'change']}
        ],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        emailCode: [
          {required: true, trigger: ['blur', 'change'], message: "请输入您的邮箱验证码"},
        ],
        password: [
          {required: true, trigger: "blur", message: "请输入您的密码"},
          {min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur"},
          {pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur"}
        ],
        confirmPassword: [
          {required: true, trigger: "blur", message: "请再次输入您的密码"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ],
        code: [{required: true, trigger: ['blur', 'change'], message: "请输入验证码"}]
      },
      loading: false,
      captchaEnabled: true
    };
  },

  created() {
    this.uuid = this.generateUUID();
    this.getCode();
  },
  methods: {

    email() {
      this.$refs.registerForm.validateField('email', valid => {

        if (!valid) {

          this.emailLoading = true;
          this.seconds = 120;
          this.showEmail = '邮箱'

          getEmail(this.registerForm).then(res => {
            this.$modal.msgSuccess("成功发送，请即时接收");
            const countdownInterval = setInterval(() => {
              this.seconds--;
              this.showEmail = '邮箱' + this.seconds
              if (this.seconds === 0) {
                clearInterval(countdownInterval);
                this.emailLoading = false;
                this.showEmail = '邮箱验证'
              }
            }, 1000);
          }).catch(error => {
            this.emailLoading = false;
            this.showEmail = '邮箱验证'
            this.$modal.msgError("请求错误");
          });
        } else {
          this.$modal.msgError("邮箱地址不合法");
        }
      })


    },
    generateUUID() {
      return uuidv4().replace(/-/g, ''); // 去掉UUID中的连字符
    },
    getCode() {
      getCodeImg({uuid: this.uuid}).then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.registerForm.uuid = res.uuid;
        }
      });


    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {

        if (valid) {


          if (this.flag === null) {
            this.registerForm.images = this.$store.getters.img
          } else {
            this.registerForm.images = this.flag
          }


          this.loading = true;
          register(this.registerForm).then(res => {
            const userName = this.registerForm.userName;
            this.$alert("<font color='red'>恭喜你，您的账号 " + userName + " 注册成功！</font>", '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => {
            });
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  margin-top: 50px;
  border-radius: 6px;
  background: #ffffff;
  width: 1200px;
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

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.register-code {
  width: 101px;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-register-footer {
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

.register-code-img {
  height: 38px;
}
</style>
