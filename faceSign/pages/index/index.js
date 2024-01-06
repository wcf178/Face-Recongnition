//user.js
//获取应用实例
var app = getApp()
console.log(app)
Page({
  data: {
    userInfo: {},
    faceRec:'',
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onLoad: function () {
    console.log('进入页面加载');
    var that=this;
    that.setData({
      faceRec : wx.getStorageSync('faceRec'),
      hasUserInfo: true
    })
    if(that.data.faceRec == 2){
      wx.showModal({
        title: '请录入人脸信息！',
        content: '需要录入人脸信息以完成今后考勤',
        complete: (res) => {
          if (res.cancel) {
            
          }
          if (res.confirm) {
            wx.navigateTo({
              //url: '../Position/Position'
              url:'../faceRec/faceRec'
            })
          }
        }
      })
    }
  },



  // 退出登录
  logoff:function(event){
    wx.removeStorageSync('stuId');
    //wx.removeStorageSync('unitName')
    //wx.removeStorageSync('picture')
    // wx.removeStorage({
    //   key: 'logs',
    //   success: function (res) {
    //   },
    // })
    wx.clearStorageSync()
    this.setData({
      userInfo:null,
      
    })
    
    wx.redirectTo({
      url: '../login/login',
    })
  },

  //更换头像
  bindViewTap:function () {
    var that = this;
    wx.chooseImage({
      count: 1, // 默认9     
      sizeType: ['original', 'compressed'],
      // 指定是原图还是压缩图，默认两个都有     
      sourceType: ['album', 'camera'],
      // 指定来源是相册还是相机，默认两个都有   
      success: function (res) {  
        //console .log("头像更换"+res.tempFilePaths);
        wx.uploadFile({
          url: 'https://jonion.top/AppletsController/updataportrait', // 图片上传服务器真实的接口地址
        　filePath:res.tempFilePaths[0],
          name:'file',
          formData: { 
             // wx.getStorageSync('unitName')
            'account': wx.getStorageSync('unitName'),
            'id':wx.getStorageSync('unitId')
          }, 
          success:function(res){
            //console.log("返回值"+JSON.parse(res.data).obj);
            var pictureUrl='https://jonion.top'+JSON.parse(res.data).obj;
            //console.log("图片路径"+pictureUrl);
            if(JSON.parse(res.data).code==200){
              wx.setStorageSync('portrait', pictureUrl);
              that.setData({
                userInfo: pictureUrl,
              })
              wx.showToast({
                  title:'头像保存成功',
                  icon:'success',
                  duration:200
              })
            }else{
              wx.showToast({
                  title:'头像保存失败',
                  image: '../../images/myUser/error.png',
                  duration:200
              })
            } 
          }
        })
      }
    })
  },


  //进入人脸识别
  faceClick:function(){
    //检查人员是否允许进入
    wx.request({
      url: 'https://jonion.top/AppletsController/getentryaccess', // 仅为示例，并非真实的接口地址
      method: 'post',
      data: {
        id:wx.getStorageSync('unitId')
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success(res) {
        if (res.data.code == "200") {
          console.log("进出结果"+res.data.obj)
          if(res.data.obj==0){
            wx.showToast({
              title:'没有权限',
              image: '../../images/myUser/error.png',
              duration:2000
            })
          }else {
            wx.navigateTo({
              url: '../faceRec/faceRec',
            })
          }
        } else {
          wx.showToast({
            title: res.data.message,
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },
  faceRecord:function(){
    wx.navigateTo({
      url: '../faceRec/faceRec',
    })
  },
  // 关于我们
  businessInfo:function(){
    wx.navigateTo({
      url: '../aboutUs/aboutUs',
    })
 },
  // 个人信息
  myInfo: function(event){
    wx.navigateTo({
      url: '../myInfo/myInfo',
    })
  }

})
