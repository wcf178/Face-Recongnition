// pages/faceRec/faceRec.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      resdata:[]
  },

  record: function(){
    let that = this
    let ctx = wx.createCameraContext()
    
    ctx.takePhoto({
        quality:'high',
        
        success:(res)=>{
            //console.log(res.tempImagePath)
            that.setData({
                src: res.tempImagePath
                })
                that.localhostimgesupdata(res.tempImagePath)
        }
    })
    
  },
 localhostimgesupdata:function(imgPath){
    let url = app.globalData.url + '/appUpdate';
    wx.uploadFile({
        url: 'http://localhost:8000/AppLetsController/appUpdate', // 图片上传服务器真实的接口地址
    　　filePath:imgPath,
        name:'file',
        formData: { 
            // wx.getStorageSync('unitName')
            stuId : wx.getStorageSync('stuId')
            }, 
        success:function(res){
            console.log("返回值"+res.data);
            if(JSON.parse(res.data).flag==true){
                wx.setStorageSync('faceRec', 1);
                wx.showToast({
                    title:'人脸保存成功',
                    icon:'success',
                    duration:1500,
                    success: function () {
                      setTimeout(function () {
                          wx.navigateBack({
                            delta: 1  // 返回上一级页面。
                          })
                        }, 1000);
                    }
                  })
            }else{
                wx.showToast({
                    title:JSON.parse(res.data).message,
                    icon:'error',
                    duration:2000
                })
            } 
        }
    })
  },
  error(e) {
    console.log(e.detail)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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