<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :avatar="user.avatar"/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user"/>

                <span style="margin-left: 10px">用户名称</span>
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone"/>

                <span style="margin-left: 10px">手机号码</span>
                <div class="pull-right">{{ user.phonenumber }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email"/>

                <span style="margin-left: 10px">用户邮箱</span>
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li v-if="userType===false" class="list-group-item">
                <svg-icon icon-class="tree"/>
                <span style="margin-left: 10px">所属部门</span>
                <div class="pull-right" v-if="user.dept">{{ user.dept.deptName }} / {{ postGroup }}</div>
              </li>

              <li v-if="userType===false" class="list-group-item">
                <svg-icon icon-class="peoples"/>
                <span style="margin-left: 10px">所属角色</span>
                <div class="pull-right">{{ roleGroup }}</div>
              </li>

              <li class="list-group-item">
                <svg-icon icon-class="date"/>
                创建日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user"/>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd/>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import {getUserProfile} from "@/api/system/user";
import Cookies from "js-cookie";
import {getAppUserInfo} from "@/api/user/user";

export default {
  name: "Profile",
  components: {userAvatar, userInfo, resetPwd},
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo",
      userType: null
    };
  },
  created() {
    this.userType = Cookies.get('source') === 'seller-user'
    this.getUser();
  },
  methods: {
    getUser() {
      const source = Cookies.get('source')

      if (source === 'system-user') {
        getUserProfile().then(response => {
          this.user = response.data;
          console.log(this.user)
          this.roleGroup = response.roleGroup;
          this.postGroup = response.postGroup;
        });
      } else {
        getAppUserInfo().then(response => {
          this.user = response.user;
          console.log(this.user)
          this.roleGroup = response.roleGroup;
          this.postGroup = response.postGroup;
        });
      }

    }
  }
};
</script>
