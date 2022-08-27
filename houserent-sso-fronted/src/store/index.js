import { createStore } from "vuex"

//页面刷新不丢失插件
import createPersistedState from "vuex-persistedstate"
import { router } from "@/router"
import { filterAsyncRouter } from "@/utils"
import request from "@/utils/request"
import { visitorRouter } from "../router"
let routerList = []

export default createStore({
  // 全局参数
  state: {
    userInfo: {
      avater: "",
      email: "",
      nickname: "",
      phoneNumber: "",
      sex: "",
      updateTime: "",
      userName: "",
      routerList: []
    }
  },
  // set方法
  mutations: {
    SET_USER_INFO(state, val) {
      state.userInfo = val
    },
    // eslint-disable-next-line no-unused-vars
    ADD_ROUTE(state) {
      console.log("路由添加前", router.getRoutes())
      //路由未添加之前是2个,我们判断是否添加过，没添加过就添加
      console.log(39, router.getRoutes().length)
      console.log("添加路由前vuex里的state", state.userInfo.routerList)
      if (router.getRoutes().length === 2) {
        let addRouterList
        if (state.userInfo.routerList.length === 1) {
          addRouterList = state.userInfo.routerList
        } else {
          addRouterList = filterAsyncRouter(
            JSON.parse(JSON.stringify(state.userInfo.routerList)) //这里深拷贝下，不然会出问题
          )
        }
        console.log("要添加的路由列表1", addRouterList)
        console.log("要添加的路由列表2", state.userInfo.routerList)
        addRouterList.forEach((i) => {
          console.log("添加路由", i)
          router.addRoute("home", i)
        })
      }
      console.log("路由添加后", router.getRoutes())
    }
  },
  actions: {
    //登陆
    login({ commit }, userInfo) {
      const {
        avater,
        email,
        nickname,
        phoneNumber,
        sex,
        updateTime,
        userName
      } = userInfo
      return new Promise((resolve) => {
        console.log(
          "store.index.js获取用户router之前的routerList：",
          routerList
        )

        //模拟登陆，获取用户信息， 权限路由列表
        //假设返回的有token, 路由列表(根据不同用户返回不同)
        /**********************模拟后端传过来的路由列表----START***********************/
        // let routerList = []
        // if (username === "admin") {
        //   routerList = authRouter
        // } else if (username === "commonUser") {
        //   routerList = [authRouter[0]]
        // }
        /**********************模拟后端传过来的路由列表----END***********************/
        // let token = "testToken"
        request({
          url: "api/user/getMenu",
          method: "get"
        }).then((resp) => {
          console.log("请求到的用户路由：", resp.data.routes)
          routerList = resp.data.routes
          //把用户信息存入vuex
          commit("SET_USER_INFO", {
            avater,
            email,
            nickname,
            phoneNumber,
            sex,
            updateTime,
            userName,
            routerList
          })
          console.log("login over")
          console.log("用户信息保存完毕后的routerList：", routerList)
          //添加路由
          commit("ADD_ROUTE")
          resolve()
        })
      })
    },
    loginForVisitor({ commit }) {
      return new Promise((resolve) => {
        console.log(
          "store.index.js获取用户router之前的routerList：",
          routerList
        )
        routerList = visitorRouter
        //把用户信息存入vuex
        commit("SET_USER_INFO", {
          routerList
        })
        console.log("login over")
        console.log("用户信息保存完毕后的routerList：", routerList)
        //添加路由
        commit("ADD_ROUTE")
        // console.log(
        //   "保存vuex后的state：",
        //   this.state.userInfo.routerList.length
        // )
        resolve()
      })
    },
    //添加路由
    addRoute({ commit }) {
      commit("ADD_ROUTE", this.state)
    },
    //注销
    logout({ commit, state }) {
      return new Promise((resolve) => {
        //拷贝一下
        const delRouterList = JSON.parse(
          JSON.stringify(state.userInfo.routerList)
        )
        console.log("待删除的路由表", delRouterList)
        //删除添加的路由，如果路由是多层的 递归下。。
        delRouterList.forEach((route) => {
          console.log("删除route:", route)
          router.removeRoute(route.name)
        })
        //删除路由
        commit("SET_USER_INFO", {
          avater: "",
          email: "",
          nickname: "",
          phoneNumber: "",
          sex: "",
          updateTime: "",
          userName: "",
          routerList: []
        })
        console.log("删除用户信息后:", state.userInfo)
        resolve("注销 success， 清空路由，用户信息")
      })
    }
  },
  modules: {},
  plugins: [createPersistedState()]
})
