import axios from "axios"

const request = axios.create({
  baseURL:
    process.env.NODE_ENV === "production" ? process.env.VUE_APP_BASE_API : "/", // api 的 base_url
  timeout: 12000, // 请求超时时间
  withCredentials: true
})

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  (response) => {
    let res = response.data
    // 如果是返回的文件
    if (response.config.responseType === "blob") {
      return res
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === "string") {
      if (res === "Logging in, please wait...") {
        return res
      }
      if (res.indexOf("Please sign in") !== -1) {
        return res
      }
      res = res ? JSON.parse(res) : res
    }

    console.log("request response：", res)
    return res
  },
  (error) => {
    console.log("err" + error) // for debug
    return Promise.reject(error)
  }
)

export default request

// import axios from "axios"
//
// // 创建axios实例
// const request= axios.create({
//   baseURL:
//     process.env.NODE_ENV === "production" ? process.env.VUE_APP_BASE_API : "/", // api 的 base_url
//     timeout: 12000, // 请求超时时间
//     withCredentials:true
// })
//
//
// // response 响应拦截器
// request.interceptors.response.use(
//     /**
//      * If you want to get http information such as headers or status
//      * Please return  response => response
//      */
//
//     /**
//      * Determine the request status by custom code
//      * Here is just an example
//      * You can also judge the status by HTTP Status Code
//      */
//     response => {
//         return response.data
//     },
//     error => {
//         if (error.code === 'ECONNABORTED') {
//             Message({
//                 message: '接口超时未响应',
//                 duration: 1000,
//                 forbidClick: true
//             })
//             return Promise.reject(error)
//         }
//         if (error.response.status === 401) {
//             Message({
//                 message: '您的登录已失效',
//                 duration: 1000,
//                 forbidClick: true
//             })
//         } else {
//             let msg = error.message
//             if (error.response) {
//                 const { data } = error.response
//                 msg = data.message
//             }
//             Message({
//                 message: msg,
//                 duration: 1000,
//                 type: 'error'
//             })
//         }
//         return Promise.reject(error)
//     }
// )
// export default request
