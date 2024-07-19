import {login, logout, getInfo, refreshToken} from '@/api/login'
import {getToken, setToken, setExpiresIn, removeToken} from '@/utils/auth'
import Cookies from "js-cookie";
import {getAppUserInfo} from "@/api/user/user";

const user = {
  state: {
    token: getToken(),
    id: '',
    name: '',
    avatar: '',
    roles: [],
    permissions: [],
    img: '',
    shopId:''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_EXPIRES_IN: (state, time) => {
      state.expires_in = time
    },
    SET_ID: (state, id) => {
      state.id = id
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_IMG: (state, img) => {
      state.img = img
    },
    SET_SHOPID: (state, shopId) => {
      state.shopId = shopId
    },
  },

  actions: {
    // 登录
    Login({commit}, userInfo) {
      const userName = userInfo.userName.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      const source = userInfo.source

      return new Promise((resolve, reject) => {
        login(userName, password, code, uuid, source).then(res => {
          let data = res.data

          setToken(data.access_token)
          commit('SET_TOKEN', data.access_token)

          setExpiresIn(data.expires_in)
          commit('SET_EXPIRES_IN', data.expires_in)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })


    },

    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const user = res.user
          const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/profile.jpg") : user.avatar;
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          Cookies.set("userId",user.userId);
          commit('SET_ID', user.userId)
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })


    },
    GetAppUserInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        getAppUserInfo().then(res => {
          const user = res.user
          const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/profile.jpg") : user.avatar;
          commit('SET_ID', user.userId)
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          Cookies.set("userId",user.userId);
          commit('SET_SHOPID', user.shopId)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },


    // 刷新token
    RefreshToken({commit, state}) {
      return new Promise((resolve, reject) => {
        refreshToken(state.token).then(res => {
          setExpiresIn(res.data)
          commit('SET_EXPIRES_IN', res.data)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 退出系统
    LogOut({commit, state}) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          console.log(Cookies.get("source"))
          Cookies.remove("source");
          Cookies.remove("userId");
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({commit}) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  },

}

export default user
