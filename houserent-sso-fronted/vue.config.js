const webpack = require("webpack")
module.exports = {
  devServer: {
    open: true,
    host: "localhost",
    port: 9092,
    //这里的ip和端口是前端项目的;下面为需要跨域访问后端项目
    proxy: {
      "/api": {
        target: "http://localhost:8088/", //这里填入你要请求的接口的前缀
        ws: true, //代理websocked
        changeOrigin: true, //虚拟的站点需要更管origin
        secure: false, //是否https接口
        pathRewrite: {
          "^/api": "" //重写路径
        }
      }
    }
  },
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "windows.jQuery": "jquery"
      })
    ]
  }
  // devServer: {
  //   port: 9092,
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:8088/user',//这里填入你要请求的接口的前缀
  //       ws: false, //代理websocked
  //       changeOrigin: true, //虚拟的站点需要更管origin
  //       secure: false,                   //是否https接口
  //       pathRewrite:{
  //         '^/api':''//重写路径
  //       }
  //     }
  //   }
  // }
}
