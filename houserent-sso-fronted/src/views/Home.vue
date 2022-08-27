<template>
  <div class="home">
    <el-container>
      <el-header style="background-color: #67c23a; height: 50px">
        <span v-if="store.state.userInfo.userName">
          {{ store.state.userInfo.userName }}
        </span>
        <span v-else>正在以游客身份访问</span>
        <el-button v-if="store.state.userInfo.userName" @click="logout">
          注销
        </el-button>
        <el-button v-else v-on:click="login()">登录</el-button>
      </el-header>
      <el-container>
        <el-aside
          width="200px"
          style="background-color: #79bbff; height: calc(100vh - 50px)"
        >
          <div class="p-side-bar">
            <el-menu :default-active="$route.path" :router="true">
              <el-menu-item
                :index="menu.path"
                v-for="menu in menuList"
                :key="menu.name"
              >
                <span>{{ menu.name }}</span>
              </el-menu-item>
            </el-menu>
          </div>
        </el-aside>
        <el-container>
          <el-main>
            <router-view></router-view>
          </el-main>
          <el-footer style="background-color: #337ecc; height: 50px">
            Footer
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import request from "@/utils/request"
import { ElMessage } from "element-plus"

const router = useRouter()
const store = useStore()

const menuList = ref([])

const login = () => {
  window.location.href = "http://localhost:8088/user/login"
  // 登录逻辑
  // 1.页面加载时调用用户登录接口
  // 2.如果调到了说明登录过了
  //   2.1 把loginStatus设置为authorized
  //   2.2 调用getUserInfo接口，把拿到的userInfo存入store，无需跳转到/home
  //   2.3 在调用store的login方法的过程中调用getMenu方法
  //   2.4 获取到路由信息，存入到store
  //   2.5 页面根据store里的路由信息动态渲染路由
  //   2.6 登录完成
  // 3.如果调不到，loginStatus设置为visitor，以游客模式访问
}

const initUserInfo = () => {
  console.log("开始用户信息初始化")
  request({
    url: "api/user/login",
    method: "get"
  }).then((res) => {
    if (res === "Logging in, please wait...") {
      // 拿到信息表示登录成功，去请求用户数据
      request({
        url: "api/user/getUserInfo",
        method: "get"
      }).then((res) => {
        // 将用户数据存入store中
        // store.commit("LOGIN", res.data.userInfo[0])
        store.dispatch("login", res.data.userInfo[0]).then(() => {
          // 存入store后初始化菜单
          initMenu()
        })
        // 修改loginStatus
        localStorage.setItem("loginStatus", "authorized")
        ElMessage.success("登录成功")
      })
    } else {
      // 请求不到，以游客身份访问
      store.dispatch("loginForVisitor").then(() => {
        // 存入store后初始化菜单
        initMenu()
      })
      localStorage.setItem("loginStatus", "visitor")
      ElMessage.info("以游客身份访问中...")
    }
  })
  console.log("用户信息初始化完成")
}

initUserInfo()

const initMenu = () => {
  var routerList = router.getRoutes()
  console.log("HOME页面里查到的路由:", routerList)
  menuList.value = routerList.filter((route) => {
    if (route.name !== "home" && route.name !== "404") {
      console.log("过滤后的路由：", route)
      return route
    }
  })
  // console.log("home", router.getRoutes().length)
  // console.log("home", window.location.href)
  // 3 home houseSearch
  // 3 other(houseSearch or 404) next
  // 9 home houseSearch
  // 9 other next
  console.log(135, routerList.length)
  console.log(window.location.href)
  // 菜单初始化完成后跳转到/houseSearch页面
  if (window.location.href.indexOf("home") !== -1) {
    // if ((routerList.length === 3 || routerList.length === 9) && window.location.href.indexOf("home") !== -1) {
    router.push({ path: "/houseSearch" })
  }
}

//注销
const logout = async () => {
  request({
    url: "http://localhost:8088/user/logout",
    method: "get"
  }).then((res) => {
    console.log(res)
    ElMessage.info("注销成功")
  })
  localStorage.setItem("loginStatus", "visitor")
  await store.dispatch("logout")
  console.log("注销后的url:", window.location.href)
  window.location.href = "http://localhost:9092/#/home"
  window.location.reload()
  // await router.push({ path: "/home" })
  // window.location = "http://localhost:9092/#/home"
  // window.location.replace("http://localhost:9092/#/home")
}
</script>

<style scoped>
/*.p-side-bar {*/
/*  border: solid 1px red;*/
/*  width: 200px;*/
/*}*/
</style>
