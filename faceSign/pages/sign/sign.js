// pages/sign/sign.js
const app = getApp()
const util = require('../../utils/util')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    attdId:'',
    stuId:'',
    teahName:'',
    subName:'',
    subId:'',
    teahId:'',
    startTime:'',
    latitude:'',
    longitude:'',
    distance:'',
    endTime:'',
    hide:true,
    //signflag用于识别签到还是签退
    signFlag:'',
    signIn:false,
    signOut:true,
    // 1为已经签过到 0为没有签到
    ifSignIn:'',
    interval:'',
    tip:'本次考勤正在进行',
    debug:false
  },

  // bindSignIn() {
  //   wx.navigateTo({
  //     url: '../faceAttd/faceAttd'
  //   })
  // },
  // 调试用
  bindSignIn() {
    
    this.setData({
      signFlag:'signIn'
    })
    wx.setStorageSync('signFlag', this.data.signFlag)
    wx.navigateTo({
      //url: '../Position/Position'
      url:'../faceAttd/faceAttd'
    })
    if(wx.getStorageSync('ifSignIn') == 'YES'){
      this.setData({
        signIn : true,
        signOut : false
      })
    }
  },
  bindSignOut() {
    this.setData({
      signFlag:'signOut'
    })
    wx.setStorageSync('signFlag', this.data.signFlag)
    wx.navigateTo({
      //url: '../Position/Position'
      url:'../faceAttd/faceAttd'
    })
  },
  flash(){
    //刷新页面，携带stuId获取最新的考勤Id
    console.log(wx.getStorageSync('stuId'))
    console.log(this.data.stuId);
    this.setData({
      stuId : wx.getStorageSync('stuId')
    })
    wx.request({
      url: app.globalData.url + '/getAttd',
      method:'get',
      data:{
        stuId:this.data.stuId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },
      success :(res)=> {
        if (res.data.flag == true) {
          console.log(res.data)
          console.log(res.data.data);
          var result = res.data.data;
          console.log(this.data.subId);
          this.setData({
            subName: result.subName,
            teahName: result.teahName,
            startTime : result.startTime,
            endTime : result.endTime,
            attdId : result.attdId,
            latitude:result.latitude,
            longitude:result.longitude,
            distance:result.distance,
            teahId:result.teahId,
            subId:result.subId,
            hide:false
          })
          wx.setStorageSync('attdId', this.data.attdId);
          wx.setStorageSync('startTime', this.data.startTime);
          wx.setStorageSync('endTime', this.data.endTime);
          wx.setStorageSync('latitude', this.data.latitude);
          wx.setStorageSync('longitude', this.data.longitude);
          wx.setStorageSync('distance', this.data.distance);
          wx.setStorageSync('teahId', this.data.teahId);
          wx.setStorageSync('subId', this.data.subId);
          // 上面的可以进行数据的回显
          // 下面的方法不行,猜测是因为上面的setData完成之后会进行数据重加载
          //破案了，就是这样
  
          console.log(this.data);
          
          wx.showToast({
            title: res.data.message,
            image: '../../image/icons/info.png',
            duration: 1500
          })
        } else {
          wx.showToast({
            title: res.data.message,
            icon: 'error',
            duration: 2000
          })
        }
      }
    })
  },
  setButtons(){
    var ifsignIn = wx.getStorageSync('ifSignIn')
    if(wx.getStorageSync('faceRec') == 1){

    if(ifsignIn == 'YES'){
      this.setData({
        signIn : true,
        signOut : false
      })
      clearInterval(this.data.interval)
    }
    var nowTime = util.formatTodayTime(new Date())
    var endTime = util.caculate(this.data.endTime)
    var startTime = util.caculate(this.data.startTime)
    if((nowTime > endTime && ifsignIn == 'NO') || (startTime - nowTime) > 15){
      //课都结束了你还签个锤子到
      this.setData({
        tip : '本次考勤已结束或未开启', 
        signIn : true,
        signOut : true
      })
      clearInterval(this.data.interval)
    }
  }
  else{
    this.setData({
      tip : '您未录入人脸，不可进行考勤', 
      signIn : true,
      signOut : true
    })
  }
    
  },
   /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var that = this
    this.setData({
      stuId : wx.getStorageSync('stuId'),
      ifSignIn : wx.getStorageSync('ifSignIn'),
      signIn : false,
      signOut : true,
      interval : setInterval(function() {
        that.setButtons();
        console.log("5秒了----------------------------------------------------")
      }, 3000)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    this.flash()
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
    //clearInterval(this.data.interval)
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {
    clearInterval(this.data.interval)
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {
    wx.showNavigationBarLoading(); //在标题栏中显示加载图标
    this.flash();
    this.setButtons();
    wx.hideNavigationBarLoading(); //完成停止加载图标
    wx.stopPullDownRefresh();
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