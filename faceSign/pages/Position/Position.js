// location_check_in/location_check_in.js
const util = require('../../utils/util')
const app = getApp()
const urlList = require("../../utils/api.js")  // 根据实际项目自己配置
 
// 实例化API核心类
const qqmapsdk = app.globalData.qqmapsdk
 
Page({
 
  /**
   * 页面的初始数据
   */
  data: {
    markers: '',
    poi: {
      latitude: '',
      longitude: ''
    },
    
    attdStuId:'',
    signFlag:'',
    attdStuName:'',
    clickTime:'',
    flag:'',
    attdId:'',
   
    startTime:'',
    endTime:'',
    addressName: '',
    time: '',
    timer: '',
    timer2: '',  // 用来每个一段时间自动刷新一次定位
    canClick: true,
    

  },
 
  getAddress(e) {
    var that = this;
    wx.getLocation({//获取地址
      type: 'gcj02',
      success(res) {
        var latitude = res.latitude
        var longitude = res.longitude
        
        //console.log(latitude, longitude)
        qqmapsdk.reverseGeocoder({
          //位置坐标，默认获取当前位置，非必须参数
          
            // location: {
            //   latitude: 39.984060,
            //   longitude: 116.307520
            // },
          location: { latitude, longitude },
          // 成功后的回调
          
          success: function(res) {
            
            //console.log(res);
            that.setData({
              addressName: res.result.address
            })
            var res = res.result;
            var mks = [];
            //当get_poi为0时或者为不填默认值时，检索目标位置，按需使用
            mks.push({ // 获取返回结果，放到mks数组中
              title: res.address,
              id: 0,
              latitude: res.location.lat,
              longitude: res.location.lng,
              iconPath: '../../image/zcxj/myPosition.png', // 图标路径
              width: 21,
              height: 28,
              // callout: { //在markers上展示地址名称，根据需求是否需要
              //   content: res.address,
              //   color: '#000',
              //   display: 'ALWAYS'
              // }
            });
            that.setData({ // 设置markers属性和地图位置poi，将结果在地图展示
              markers: mks,
              poi: {
                latitude: res.location.lat,
                longitude: res.location.lng
              }
            });
            
          },
          fail: function(error) {
            console.log("worinidaye");
            console.error(error);
          },
          complete: function(res) {
            //console.log("worinidaye");
            //console.log(res);
          }
        })
      } 
    })
    //var lat = that.poi.latitude
    
  },
  getTime: function () {
    let that = this
    let time = that.data.time
    that.setData({
      timer: setInterval(function () {
        time = util.formatTime(new Date())
        that.setData({
          time: time.substr(-8)
        });
        if (time == 0) {
          // 页面跳转后，要把定时器清空掉，免得浪费性能
          clearInterval(that.data.timer)
        }
      }, 1000)
    })
  },
  rePosition: function () {
    console.log('用户点了重新定位')
    this.getAddress()
  },
  checkIn: function () {
    this.setData({
      canClick: false
    })
    console.log('用户点击了签到')
 
    
    var that = this
    var nowTime = util.formatTime(new Date())
    var lat = wx.getStorageSync('latitude')
    var lng = wx.getStorageSync('longitude')
    // 是否在范围内
    var distance = util.getDistance(that.data.poi.latitude,that.data.poi.longitude,lat,lng)
    var requiedDis = wx.getStorageSync('distance')
    
    console.log("distance:"+distance)
    console.log("request"+requiedDis)
    if(parseInt(distance) > requiedDis){
      wx.showToast({
        title: '当前不在范围内',
        icon:'error',
        duration:1000
      })
      this.setData({
        canClick: true
      })
    }
    else{

    
    //迟到早退逻辑判断
    that.getFlag()

    this.setData({
      clickTime:nowTime
    })
    wx.showModal({
      title: '请确认打卡信息',
      // content: '请确认待整改项已整改完毕！',
      content: 
      `姓名：${this.data.attdStuName}\n地点：${this.data.addressName}\n时间：${nowTime}`,  
      confirmText: '确认',
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
          // 调起签到接口
          if(that.data.attdStuId != 0){
            that.realyCheckIn()
          }
          else{
            wx.showToast({
              title: "非本人签到！",
              icon: 'error',
              duration: 2000,
            })
            wx.navigateBack({
              delta: 2  // 返回上一级页面。
            })
          }
        } else if (res.cancel) {
          console.log('用户点击取消')
          that.setData({
            canClick: true
          })
        }
      }
    })
  }
},
  realyCheckIn: function() {
    var that = this
    
    // 拼接："经度,纬度"
    //patrolForm.latandlon = this.data.poi.longitude + "," + this.data.poi.latitude
    
 
    console.log(this.data)
    console.log("↑ 签到提交的post参数")
    
    var tmpNumber = 0
    wx.request({
      url: app.globalData.url+'/sign',
      data: {
        attdStuId:this.data.attdStuId,
        signFlag:this.data.signFlag,
        attdStuName:this.data.attdStuName,
        time: this.data.clickTime,
        flag:this.data.flag,
        attdId:this.data.attdId,
      },
      method: "POST",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data)
        if(res.data.flag) {
          console.log("请求成功")
          // // 看怎么取到返回的id
          // debugger
          if(that.data.signFlag == 'signIn'){
            wx.setStorageSync('ifSignIn', 'YES')
          }
          wx.showToast({
            title: res.data.message,
            icon: 'success',
            duration: 2000,
            success: function () {
              setTimeout(function () {
                  wx.navigateBack({
                    delta: 2  // 返回上一级页面。
                  })
                }, 2000);
            }
          })
          
        }
        else{
          wx.showToast({
            title: res.data.message,
            icon: 'error',
            duration: 2000,
          })
        }
        
      },
      fail: function(res) {
        that.setData({
          canClick: true
        })
      }
    })
    console.log("ces")
    
  },
  getFlag: function (){
    var timeToCompare = util.formatTodayTime(new Date())
    var signFlag = this.data.signFlag
    console.log(timeToCompare)
    console.log("signFlag"+signFlag)
    if(signFlag == 'signIn'){
      // 统一换算成分钟数来计算
      var res = timeToCompare - util.caculate(this.data.startTime)
      console.log(res)
      
      if(res>15){
        this.setData({
          flag : 0
        })
      }
      else if(res>0 ){
        this.setData({
          
            flag : 1
          
        })
      }
      else{
        this.setData({
          
            flag : 2
          
  
        })
      }
    }
    else{
      // 统一换算成分钟数来计算
      var res = util.caculate(this.data.endTime) -timeToCompare
      
      if(res > 0 ){
        this.setData({
          flag : 1
        })
      }
      else{
        this.setData({
          flag : 2
        })
      }
    }
    console.log("this.data.flag")
    console.log(this.data.flag)
    
  },
  judgeDistance(lat,long){

  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    that.getTime()
    that.getAddress()
    
    that.setData({
      /*调试位置功能，暂时屏蔽*/
      attdStuName: wx.getStorageSync('attdStuName'),
      attdStuId : wx.getStorageSync('attdStuId'),
      attdId : wx.getStorageSync('attdId'),
      signFlag : wx.getStorageSync('signFlag'),
      startTime : wx.getStorageSync('startTime'),
      endTime : wx.getStorageSync('endTime'),
      canClick: true, // 允许用户点击，防止多次提交
      timer2: setInterval(function () {
        that.getAddress()
      }, 20000)  // 每20秒刷新一次定位
    })
  },
 
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearInterval(this.data.timer)
    clearInterval(this.data.timer2)
    console.log("定时器已被清除")
  },
 
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
 
  },
 
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
 
  },
 
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
 
  },
 
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
 
  }
})