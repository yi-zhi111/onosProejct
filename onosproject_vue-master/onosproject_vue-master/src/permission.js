// 路由守卫
import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/register'] // no redirect whitelist

// 全局前置路由守卫，初始化的时候被调用、每次路由切换之前被调用
// to：目标路由；from：源路由；next：放行
router.beforeEach(async(to, from, next) => {
  // start progress bar：进度条插件
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  // 需要到后端验证
  const hasToken = getToken()
  if (hasToken) { // 如果有token，
    if (to.path === '/login') { // 看看路由是否定向到login页面直接进入
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else { // 若在主页刷新，则需保留用户信息
        try {
          // get user info
          await store.dispatch('user/getJwt')
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error({
            type: 'error',
            message: error || 'Has Error'
          })
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

// 全局后置路由守卫，初始化的时候被调用、每次路由切换之后被调用
// 后置路由守卫只有to和from，没有next
// 一般用的比较少
router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
