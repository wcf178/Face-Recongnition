// pages/myInfo/myInfo.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    stuPwd:'',
    stuPwd1:'',
    newPwd1:'',
    newPwd2:'',
    stuId:'',
    stuName:'',
    stuPhone:'', 
    hide1:true
  },
  getStuInfo(){
    wx.request({
      url: app.globalData.url+'/getMyInfo',
      method:'get',
      data:{
        stuId: this.data.stuId
      },
      success:(res) =>{
        if(res.data.flag){
          this.setData({
            stuName:res.data.data.stuName,
            stuPhone:res.data.data.stuPhone,
            stuId:res.data.data.stuId,
            stuPwd:res.data.data.stuPwd
          })
        }
      }
    })
  },
  changePwd(){
    
  },
  createWin(){
    this.setData({
      hide1:false
    })

  },
  modalinput(){
    this.setData({
      hide1:true,
      stuPwd1:'',
      newPwd1:'',
      newPwd2:''
    })
  },
  passwordInput: function (e) {
    this.setData({
      stuPwd1: e.detail.value
    })
  },
  newPassword1Input: function (e) {
    this.setData({
      newPwd1: e.detail.value
    })
  },
  newPassword2Input: function (e) {
    this.setData({
      newPwd2: e.detail.value
    })
  },
  confirm(){
    var msg;
    console.log(this.data.newPwd1)
    console.log(this.data.newPwd2)
    console.log(this.data.stuPwd1)
    console.log(this.data.stuPwd)
    if(this.data.stuPwd == this.data.stuPwd1){
      if(this.data.newPwd1 == this.data.newPwd2 ){
        wx.request({
          url: app.globalData.url+'/changePassword',
          method:'get',
          data:{
            userId: this.data.stuId,
            password:this.data.newPwd2
          },
          success:(res) =>{
            if(res.data.flag){
              this.setData({
                stuPwd:this.data.newPwd2
              })
              msg = "修改密码成功！"
            }
          }
        })
      }
      else{
        msg = "两次输入密码不一致"
      }
    }
    else{
      msg = "原密码输入错误"
    }
    wx.showToast({
      title: msg,
      icon:'none',
      duration:1500
    })
    
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.data.stuId =  wx.getStorageSync('stuId');
    this.getStuInfo();
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