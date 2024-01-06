// pages/myClass/myClass.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    stuId:'',
   
    listData:[
      {
        attdDate:'',
        teahName:'',
        subName:'',

        startTime:'',
        endTime:'',
    
        attdFlag:'',
        attdTime:'',
        leaveFlag:'',
        leaveTime:'',
      }
    ]
  },
  /*
  *实现功能：点击按钮获取该条目下的索引值，并且能通过索引值获取到listData的指定条目信息
  *1.获取view的索引值：在button组件中使用data-**（data-index = {{index}})获取block中循
  *环列表数据ListDate的index值
  *2.根据获取到的index值，便可以获取指定元组的数据
  *附加：
  *1.bindtap和data-**必须放在同一个组件中，否则会出现undefined错误
  *2.data-**原理：在点击事件中的target中的dataset中插入属性名为**的属性
  */
  checkDetail: function(e){
    console.log(e);
    console.log(e.currentTarget.dataset.index);
    var index = e.currentTarget.dataset.index;
    console.log(this.data.listData[index]);
    var logDetail = this.data.listData[index];
    wx.setStorageSync('logDetail', logDetail)
    wx.navigateTo({
      url: '../LogDetail/LogDetail'
    })
  },
  getMyAttdRecord(){
    this.data.stuId = wx.getStorageSync('stuId');
    
    wx.request({
      url: app.globalData.url+'/getAttdLog',
      method:'get',
      data:{
        stuId : this.data.stuId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },

      success: (res) => {
        console.log(res.data)
        if(res.data.flag){
          this.setData({
            listData:res.data.data,
           
          });
          console.log(this.data.listData);
          wx.showToast({
            title: res.data.message,
            icon:'success',
            duration:1500
          });
        }
        else{
          wx.showToast({
            title: res.data.message,
            icon:'error',
            duration:1500
          })
          this.setData({
            listData : ''
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    
    this.setData({
      stuId : wx.getStorageSync('stuId')
    })
    this.getMyAttdRecord();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    //this.getMyAttdRecord();
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
    wx.showNavigationBarLoading(); //在标题栏中显示加载图标
    this.data.stuId = wx.getStorageSync('stuId');
    
    wx.request({
      url: app.globalData.url+'/getAttdLog',
      method:'get',
      data:{
        stuId : this.data.stuId
      },
      header: {
        'content-type': 'application/json' // 默认值
      },

      success: (res) => {
        if(res.data.flag){
          this.setData({
            listData:res.data.data
          })
          console.log(this.data.listData.length)
          wx.showToast({
            title: res.data.message,
            icon:'success',
            duration:1500
          })
        }
        else{
          wx.showToast({
            title: res.data.message,
            icon:'error',
            duration:1500
          })
        }
      },
      complete:(res)=>{
        wx.hideNavigationBarLoading(); //完成停止加载图标
        wx.stopPullDownRefresh();
      }
    })
    
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