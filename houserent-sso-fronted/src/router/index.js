import { createRouter, createWebHashHistory } from "vue-router"
const Home = () => import("../views/Home")
const nullPage = () => import("../views/404")
const houseSearch = () => import("../views/houseSearch")

//模拟visitor路由
export const visitorRouter = [
  {
    path: "/houseSearch",
    name: "房源查询",
    component: houseSearch
  }
]

const routes = [
  {
    path: "/home",
    name: "home",
    component: Home
  },
  {
    path: "/404",
    name: "404",
    component: nullPage
  }
]

export const router = createRouter({
  history: createWebHashHistory(),
  routes
})
