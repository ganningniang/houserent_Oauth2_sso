/**
 * 权限配置文件
 */
import { router } from "./router"
import store from "./store"
import { ElMessage } from "element-plus"

//const whiteRouter = ["login", "404"] //路由白名单，不需要添加路由和登陆

router.beforeEach(async (to, from, next) => {
  //获取用户信息
  let { userInfo } = store.state
  const { userName } = userInfo
  console.log("用户角色", userName ? userName : "未登陆")
  //有用户信息
  if (userName) {
    //触发添加路由方法，里面会判断是否需要添加
    await store.dispatch("addRoute")
    let { routerList } = userInfo
    //根据to.name来判断是否为动态路由, 是否有人知道还有更好的判断方法？
    if (!to.name) {
      //当前路由是动态的，确定是有的, 有就跳自己，没有就跳404,, tip: 刷新后动态路由的to.name为空
      if (routerList.findIndex((i) => i.path === to.path) !== -1) {
        next({ ...to, replace: true })
      } else {
        next("/404")
      }
    } else {
      console.log(28, router.getRoutes())
      console.log(to.name)
      if (to.path === "/home"){
        next("/houseSearch")
      } else {
        next()
      }
    }
  }
  //无用户信息
  else {
    //没有权限访问，跳入没有权限页面/或者登陆页面
    // 跳转之前要判断一下是否为需要跳转的界面，不然会进入死循环
    // 在没有权限的情况下，要不是去home 要不是去housesearch
    // 3 home houseSearch
    // 3 other(houseSearch or 404) next
    console.log(42, router.getRoutes())
    console.log(to.name)
    console.log(router.getRoutes().length)
    // if (router.getRoutes().length === 2){
    //   next("/home")
    // }
    if (to.path === "/houseSearch" || to.path === "/home") {
      next()
    } else {
      ElMessage.error("请先登陆！")
      next("/home")
    }
  }
})

router.afterEach(() => {})





// /**
//  * 权限配置文件
//  */
// import { router } from "./router"
// import store from "./store"
// // import { ElMessage } from "element-plus"
//
// //const whiteRouter = ["login", "404"] //路由白名单，不需要添加路由和登陆
//
// router.beforeEach(async (to, from, next) => {
//   //获取用户信息
//   console.log("permession.js", localStorage.getItem("loginStatus"))
//   let { userInfo } = store.state
//   const { userName } = userInfo
//   console.log("用户角色", userName ? userName : "未登陆")
//   // console.log(21, to.name)
//   // console.log(22, from.name)
//   next()
//   // if (to.name === "home"){
//   //   next("/houseSearch")
//   // } else {
//   //   next()
//   // }
//   //有用户信息
//   // if (userName) {
//   //   //触发添加路由方法，里面会判断是否需要添加
//   //   await store.dispatch("addRoute")
//   //   let { routerList } = userInfo
//   //   //根据to.name来判断是否为动态路由, 是否有人知道还有更好的判断方法？
//   //   console.log(21, to.name)
//   //   if (!to.name) {
//   //     //当前路由是动态的，确定是有的, 有就跳自己，没有就跳404,, tip: 刷新后动态路由的to.name为空
//   //     if (routerList.findIndex((i) => i.path === to.path) !== -1) {
//   //       next({ ...to, replace: true })
//   //     } else {
//   //       next("/404")
//   //     }
//   //   } else {
//   //     console.log(28, router.getRoutes())
//   //     next()
//   //   }
//   // }
//   //无用户信息
//   // else {
//   //   let { routerList } = userInfo
//   //   //没有权限访问，跳入没有权限页面/或者登陆页面
//   //   // 跳转之前要判断一下是否为需要跳转的界面，不然会进入死循环
//   //   console.log(37, to.path)
//   //   console.log(38, routerList)
//   //   if (to.path === "/home"){
//   //     // ElMessage.info("以游客身份访问中...")
//   //     next()
//   //   } else if (to.path === "/houseSearch") {
//   //     next()
//   //   } else {
//   //     ElMessage.warning("请登录...")
//   //     next("/home")
//   //   }
//     // if (to.path === "/login" || to.path === "/oauthLogin") {
//     //   next()
//     // } else {
//     //   if (to.path === "/home"){
//     //     ElMessage.info("oauth跳转成功")
//     //     next()
//     //   }
//     //   // ElMessage.error("请先登陆！")
//     //   next()
//     //   // next("/login")
//     // }
//   // }
// })

// router.afterEach(async (to, from) => {
//   console.log(68, to.name)
//   console.log(69, from.name)
// })
//   //获取用户信息
//   let { userInfo } = store.state
//   const { userName } = userInfo
//   console.log("用户角色", userName ? userName : "未登陆")
//   //有用户信息
//   if (userName) {
//     //触发添加路由方法，里面会判断是否需要添加
//     await store.dispatch("addRoute")
//     let { routerList } = userInfo
//     //根据to.name来判断是否为动态路由, 是否有人知道还有更好的判断方法？
//     console.log(21, to.name)
//     if (!to.name) {
//       //当前路由是动态的，确定是有的, 有就跳自己，没有就跳404,, tip: 刷新后动态路由的to.name为空
//       if (routerList.findIndex((i) => i.path === to.path) !== -1) {
//         next({ ...to, replace: true })
//       } else {
//         next("/404")
//       }
//     } else {
//       console.log(28, router.getRoutes())
//       next()
//     }
//   }
//   //无用户信息
//   else {
//     //没有权限访问，跳入没有权限页面/或者登陆页面
//     // 跳转之前要判断一下是否为需要跳转的界面，不然会进入死循环
//     console.log(37, to.path)
//     if (to.path === "/home"){
//       ElMessage.info("以游客身份访问中...")
//       next()
//     } else if (to.path === "/houseSearch") {
//       next()
//     } else {
//       ElMessage.warning("请登录...")
//       next("/home")
//     }
//     // if (to.path === "/login" || to.path === "/oauthLogin") {
//     //   next()
//     // } else {
//     //   if (to.path === "/home"){
//     //     ElMessage.info("oauth跳转成功")
//     //     next()
//     //   }
//     //   // ElMessage.error("请先登陆！")
//     //   next()
//     //   // next("/login")
//     // }
//   }
// })
