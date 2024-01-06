<template>
	<div class="center-contain">
      <el-card class="box-card">
        <div class="infocard">
          <div class="infohead">
            <div class="lefthead">
              <p style="font-family:黑体;font-size:20px;line-height: 20px">个人信息</p>
            </div>
            <el-button style="font-size:16px;float: right; padding: 16px 0" type="text">修改信息</el-button>
          </div>
          <div class="line" />
          <div class="infodetile">
            <div class="detile-left">
              <p>姓名  :  {{user.teahName}}</p>
              <p>年龄  :  {{user.teahAge}}</p>
              <p>联系电话  :  {{user.teahPhone}}</p>
              <p>教师编号  :  {{user.teahId}}</p>
              <p>职位  :  {{user.teahPosts}}</p>
              <p>住址  :  {{user.teahAddress}}</p>
            </div>
            <div class="detile-right">
              <div class="demo-image">
                  <el-image
                    style="width: 200px; height: 256px"
                  :src="require('@/assets/R-C.jpg')"
                  :fit="fit"></el-image>
              </div>
            </div>
            
          </div>
        </div>
        
      </el-card>
	</div>
	
</template>

<script>
export default {
  name:'Center.vue',
  userId:'',
  data(){
    return {
      user:{
        teahName: '',
        teahPhone: '',
        teahId: '',
        teahAge:'',
        teahAddress:'',
        teahPosts:'',
        permission:'',
        teahPwd:''
      },
      
      img: require("@/assets/R-C.jpg"),
      fit: 'cover'

    }
  },
  
  created() {
    
    // 路由传值获取用户Id X -->  出现的问题：跳转页面后数据无法保存
    // 该为从全局变量获取数据 -->  还是不行
    // 改为登陆后将id存在state，在将state存在浏览器缓存中 --》问题解决
    this.user.teahId = this.$store.state.teahId;
    
    console.log(this.user.teahId);
    this.$ajax.get('/user/info?teahId='+ this.user.teahId).then((res) => {
      console.log(res.data.row);
      console.log(res.data);
      if (res.data.flag) {
        this.user = res.data.data;
        //this.form = {};
        this.$message({
          message: '获取个人信息成功！',
          type: 'success'

        });
      }
    })
  },
  methods:{
  
  }

}
</script>
<style>
.center-contain{
	padding:14px;
}
.box-card {
    height: 570px;
    width: 100%;
}
.infocard{
	position: relative;
	height: 90%;
	width: 90%;
    padding-left: 40px;
	
}
.infohead{
	position: relative;
	text-align: left;
	padding-left: 0px;
	height: 30px;
}
.lefthead{
   background: #409EFF;
	position:absolute;
	top:0px;
	eft:0px;
	height: 10px;
	float: left;
	padding-left: 0px;
	padding-top: 0px
}
/* 横线 */
.line{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 10px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.infodetile{
	
 }
.detile-left{
	position: absolute;
	left: 40px;
	width: 50%;
    padding-top: 100px;
    line-height: 40px;
    text-align: left;
}
.detile-right{
    position: absolute;
	
    right: 5px;
    width: 50%;
	height: 400px;
    padding-top: 100px;
    line-height: 40px;
    text-align: center;
	
}

</style>