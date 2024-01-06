// pages/LogDetail/LogDetail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    attdState:'',
    leaveState:'',
    attdDate:'',
    teahName:'',
    subName:'',

    startTime:'',
    endTime:'',
    
    attdFlag:'',
    attdTime:'',
    leaveFlag:'',
    leaveTime:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    var logDetail = wx.getStorageSync('logDetail');
    var attdState;
    var leaveState;
    this.setData({
    attdDate:logDetail.attdDate,
    teahName:logDetail.teahName,
    subName:logDetail.subName,

    startTime:logDetail.startTime,
    endTime:logDetail.endTime,
    
    attdFlag:logDetail.attdFlag,
    attdTime:logDetail.attdTime,
    leaveFlag:logDetail.leaveFlag,
    leaveTime:logDetail.leaveTime,
    });
    switch(this.data.attdFlag) {
      case 1:
         attdState = '迟到'
         break;
      case 2:
         attdState = '正常'
         break;
      default:
         attdState = '缺席'
    };
    switch(this.data.leaveFlag) {
      case 1:
         leaveState = '早退'
         break;
      case 2:
         leaveState = '正常'
         break;
      default:
         leaveState = '缺席'
    };
    this.setData({
      attdState:attdState,
      leaveState:leaveState
    })
    
    console.log(this.date);
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