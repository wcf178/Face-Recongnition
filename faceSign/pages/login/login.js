// pages/login/login.js
//index.js
//获取应用实例
const app = getApp()
 
Page({
  data: {
    stuId: '',
    stuPwd: ''
  },
  //事件处理函数
  bindViewTap: function() {
    wx.switchTab({
      url: '../index/index',
    })
  },
  onShow: function () {
    // 生命周期函数--监听页面显示
    wx.hideTabBar({})
  },
  onLoad: function () {
   
  },
 
 
  // 获取输入账号 
  userIdInput: function (e) {
    //console.log(e);
    this.setData({
      stuId:e.detail.value
    })
  },
 
  // 获取输入密码 
  passwordInput: function (e) {
    this.setData({
      stuPwd: e.detail.value
    })
  },
 
  // 登录处理
  login: function () {
    var that = this;
    console.log(this.data.stuId+" " + this.data.stuPwd);
    console.log(that.data.stuId+" " + that.data.stuPwd);
    if (that.data.stuId.length == 0 || that.data.stuPwd.length == 0) {
      wx.showToast({
        title: '账号或密码不能为空',
        icon: 'none',
        duration: 2000
      })
    } else {
      wx.request({
        url: app.globalData.url +'/login', 
        method: 'post',
        data: {
          userId: that.data.stuId,
          password: that.data.stuPwd
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success(res) {
          if (res.data.flag == true) {
            console.log(res.data)
            console.log(res.data.data);
            //console.log(this.data.stuId);
            wx.setStorageSync('stuId', that.data.stuId);
            wx.setStorageSync('faceRec', res.data.data);
            wx.switchTab({
              url: '../index/index',
            });
            wx.showToast({
              title: res.data.message,
              icon: 'none',
              duration: 2000
            })
          } else {
            wx.showToast({
              title: res.data.message,
              icon: 'none',
              duration: 2000
            })
          }
        }
      })
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})
 

  