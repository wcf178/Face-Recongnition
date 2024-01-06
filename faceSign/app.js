// app.js

var QQMapWX = require('/utils/qqmap-wx-jssdk.min.js')

App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    wx.setStorageSync('ifSignIn', 'NO')
    wx.setStorageSync('attdStuId', 0)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    userInfo: "hhh",
    url: 'http://localhost:8000/AppLetsController',
    patrolForm:{
      checkaddress:"",
    },
    qqmapsdk: new QQMapWX({
      key: 'GTABZ-OPIRV-I43PD-5D5GP-XU4QV-ISBGA' 
    }),
  },
  
   
  
})



