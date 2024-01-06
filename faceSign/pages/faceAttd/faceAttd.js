// pages/faceRec/faceRec.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      attdStuId:'',
      attdStuName:'',
      stuId:'',
      teahId:'',
      subId:'',
      src:''
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
  //上传图片，获取此次拍照的 face_code
  localhostimgesupdata:function(imgPath){
    var that = this
    console.log("hhh1"+that.data.teahId)
    wx.uploadFile({
        url: app.globalData.url + '/faceCompare', // 图片上传服务器真实的接口地址
    　　filePath:imgPath,
        name:'file',
        formData:{
           teahId:that.data.teahId,
           subId:that.data.subId,
           attdDate:'forTest'
         },
        success:function(res){
            console.log("返回值"+res.data);
            console.log(res.data.flag)
            if(JSON.parse(res.data).flag==true){
              that.setData({
                attdStuId:JSON.parse(res.data).data.stuId,
                attdStuName:JSON.parse(res.data).data.stuName
              });
              console.log("attdstuid"+that.data.attdStuId)
              
              if(that.data.stuId == that.data.attdStuId){
                wx.setStorageSync('attdStuId', that.data.attdStuId);
                wx.setStorageSync('attdStuName', that.data.attdStuName);
                wx.showToast({
                  title:JSON.parse(res.data).data.stuName,
                  icon:'success',
                  duration:1000,
                  });
                  setTimeout(function () {
                    wx.navigateTo({
                      url:'../Position/Position'
                    })
                  }, 1000);
              }else{
                wx.showToast({
                  title:'非本人！',
                  icon:'error',
                  duration:1500
              });
              // wx.navigateBack({
              //     delta: 1  // 返回上一级页面。
              // })  
            }
            }else{
                wx.showToast({
                    title:JSON.parse(res.data).message,
                    icon:'error',
                    duration:1500
                })
            } 
        }
    })
   
    console.log(that.data)
  },
  error(e) {
    console.log(e.detail)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      stuId : wx.getStorageSync('stuId'),
      teahId : wx.getStorageSync('teahId'),
      subId : wx.getStorageSync('subId')
    })
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