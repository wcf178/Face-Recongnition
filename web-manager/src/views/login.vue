<template>
  <div class = "poster">
    
    <div class="form-class">
	      <el-form :model="form" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
	      <el-form-item  prop="userId">
	        <el-input v-model="form.userId" placeholder="账号"   autocomplete="off"></el-input>
	      </el-form-item>
	      <el-form-item prop="password">
	        <el-input type="password"  placeholder="密码" v-model="form.password" ></el-input>
	      </el-form-item>
	      <el-form-item>
	        <el-button type="primary" class = "Logbutton" @click="submitForm">登录</el-button>
	        <el-button @click="resetForm">重置</el-button>
	      </el-form-item>
	    </el-form>
    </div>
   
  </div>
</template>

<script>
export default {
  name: "login.vue",
  data(){
    return {
      // 表单数据
      form:{
        userId:'',
        password:''
      },
      // 表单校验规则
      rules:{
        userId:[
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password:[
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  // 页面加载函数
  created() {
  
  },
  methods:{
    submitForm(){
        this.$refs.ruleForm.validate((valid) => {
            if(!valid) return this.$message.error("数据校验失败，请检查后提交！");
            this.load = true;
            this.loginText = '登录中...';
            //如果数据检验成功，则向后端发送请求，进行登录
            console.log(this.form)
            this.$ajax.post('/user/login',this.form).then((res) => {
              console.log(res.data);
              
              if(res.data.flag){
                //this.form = {};
                this.$message({
                  message:'成功登录，欢迎来到人脸识别考勤后台系统',
                  type: 'success'
                  
                });
                console.log(this.form.userId);
                this.$myGlobal.teahId=this.form.userId;
                this.$store.state.teahId = this.form.userId;
                this.$store.state.permission = res.data.data;
                console.log(this.$store.state.permission)
                this.$router.push('/home');
              }
              else{
                this.$message({
                  message:'登陆失败，请检查输入密码或账户',
                  type: 'error'
                });
              }
              
            }).catch(() =>{
                this.load = false;
                this.loginText = '登           录';
            });
          });
    },
    resetForm(){
        this.$refs.ruleForm.resetFields();
    }
  }

};
</script>

<style scoped>
.poster{
    background:url("../assets/background.jpg");
    width:100%;
    height:100%;
    position:fixed;
    background-size:100% 100%;
}
.form-class{
	border-radius: 15px;
	background-clip: padding-box;
	width:30%;
	margin: 200px auto;
	background: #49d3b0;
	padding: 35px 35px 15px 35px;
	border: 1px solid #35a667;
	box-shadow: 0 0 25px dimgray;
}
.Logbutton{
	background-color: #35a667;
}

</style>