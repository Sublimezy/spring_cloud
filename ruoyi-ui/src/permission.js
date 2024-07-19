import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'
import {isRelogin} from '@/utils/request'
import Cookies from "js-cookie";
import { constantRoutes, dynamicRoutes } from '@/router'
NProgress.configure({showSpinner: false})

const whiteList = ['/login', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  const source = Cookies.get('source')
  console.log(source)
  console.log(getToken())
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done()
    } else if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      console.log("111")
      console.log("判断当前用户是否已拉取完user_info信息")
      if (store.getters.roles.length === 0 && source === 'system-user') {
        console.log("判断当前用户是否已拉取完user_info信息")
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          console.log("判断当前用户是否已拉取完user_info信息")
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            console.log("根据roles权限生成可访问的路由表")
            // 根据roles权限生成可访问的路由表
            console.log(accessRoutes)
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({...to, replace: true}) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
          store.dispatch('LogOut').then(() => {
            Message.error(err)
            next({path: '/'})
          })
        })
      } else if (source === 'seller-user') {

        store.dispatch('GetAppUserInfo').then(accessRoutes => {

        })

        store.dispatch('GenerateSellerRoutes').then(accessRoutes => {

          router.addRoutes(constantRoutes) // 动态添加可访问路由表


          next({...to, replace: true}) // hack方法 确保addRoutes已完成
        })


        console.log("222")
        next()
      } else {
        console.log("333")
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${encodeURIComponent(to.fullPath)}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
