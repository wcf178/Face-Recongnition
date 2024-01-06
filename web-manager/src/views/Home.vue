<template>
<!--  主题容器  -->
  <el-container class="home-container">
    <el-header>
      <el-row style="height: 100%">
        <el-col :span="3" style="text-align: center; height: 100%">
          <el-avatar :size="60" :src="require('@/assets/R-C.jpg')"></el-avatar>
        </el-col>
        <el-col :span="15" class="title">
          人脸识别考勤后台管理中心
        </el-col>
        <el-col :span="6" class="out-button" >
          <el-button type="info"  @click="logout" style="background-color: #FFBA00">退出登录</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside :width="menuActive ? '200px' : '60px'">
        <div style="font-size: 20px; background-color: #64AAD0	; color: #ffe603" @click="menuActive = !menuActive">
          <i class="el-icon-s-fold"></i>
        </div>
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#4573D5"
          text-color="#fff"
          :collapse="!menuActive"
          router
          active-text-color="#ffd04b">
          <el-menu-item index="/Center">
            <template >
              <i class="el-icon-user"></i>
              <span>个人资料</span>
            </template>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-finished"></i>
              <span>考勤管理</span>
            </template>
            <el-menu-item index="/attdcheck">考勤统计</el-menu-item>
            <el-menu-item index="/attdpublish">考勤发布</el-menu-item>
          </el-submenu>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">请假管理（未实现）</span>
          </el-menu-item>
          <el-submenu index="3" :disabled="!teachManager">
            <template slot="title">
              <i class="el-icon-school"></i>
              <span>{{teachText}}</span>
            </template>
            <el-menu-item index="/teachermanage">教师管理</el-menu-item>
            <el-menu-item index="/subjectmanage">学科管理</el-menu-item>
            <el-menu-item index="/classmanage">班级管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <div>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item  v-for="item in breadcrumbList" :key="item.path">
              {{item.meta.title}}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
          <router-view> </router-view>
        
        <span v-show="$router.currentRoute.path === '/home'" class="main-title">欢迎使用人脸考勤后台管理系统</span>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      permission:'',
      menuActive:true,
      teachManager:false,
      teachText:'教务管理（无权限）'
      
    }
  },
  computed:{
    breadcrumbList(){
      return this.$route.matched;
    }
  },
  methods:{
    logout(){
      this.$confirm('确认登出吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端接口清楚本地缓存（需要用到Spring Security 暂未实现）
        /*
        this.$ajax.get('user/logout').then((res)->{
        
        });
        */
        //直接返回登录窗口
        this.$router.replace('/');
        this.$message({
          type: 'success',
          message: '登出成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消退出'
        });
      });
    }
  },
  created() {
    this.permission = this.$store.state.permission;
    console.log(this.permission)
    if(this.permission == 'A'){
      this.teachManager = true;
      this.teachText='教务管理'
    }
    
  },
  //这里必须使用监听，否则无法实时获取路由变动信息。
  //监听后路由会实时变动，不然需要手动刷新路径才会改变
}
</script>
<style scoped>
.home-container{
	height: 100%;
	
}
.el-header, .el-footer {
    background-color: #6C8DD5;
    color: #FFBE40;
    text-align: center;
    line-height: 60px;
}

.el-aside {
    background-color: #4573D5;
    color: #333;
    
}

.el-main {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 160px;
}
.title{
	text-align: left;
	font-size:25px;
	font-family: "Bookshelf Symbol 7";
}
.out-button{
	text-align: right;
}
.main-title{
	margin: 200px;
	font-size: 60px;
	font-family: 方正舒体;
	color: #64AAD0;
}
.el-menu-item{
	background-color: #FFD200;
}
</style>