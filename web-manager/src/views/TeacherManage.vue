<template>
  <div class="center-contain31">
    <el-card class="box-card31">
      <div class="infocard31">
        <div class="infohead31">
          <div class="lefthead31">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">教师管理</p>
          </div>
        </div>
        <div class="line11" />
        <div class="main-data1">
          <div class="data-head">
            <el-row :gutter="10" class="data-line">
              <el-col :span="10">
                <div>
                  <el-input
                    placeholder="请输入搜索内容"
                    v-model="pagination.queryString"
                    clearable>
                  </el-input>
                </div>
              </el-col>
              <el-col :span="2">
                <div >
                  <el-button style="background-color: #409EFF" icon="el-icon-search" circle @click="_findPage"></el-button>
                </div>
              </el-col>
              <el-col :span="4" style="float: right">
                <div>
                  <el-button  type="primary" @click="handleCreate">添加教师</el-button>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="table-body">
            <el-table
              height="100%"
              ref="multipleTable"
              current-row-key="id"
              :data="dataList"
              tooltip-effect="dark"
              style="width: 100%;line-height: 20px">
              <!--              @selection-change="handleSelectionChange"-->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="teahId"  label="编号" show-overflow-tooltip></el-table-column>
              <el-table-column prop="teahName" label="姓名" ></el-table-column>
              <el-table-column prop="teahAge"  label="年龄" show-overflow-tooltip></el-table-column>
              <el-table-column prop="teahAddress" label="地址" show-overflow-tooltip></el-table-column>
              <el-table-column prop="teahPhone" label="联系电话" show-overflow-tooltip></el-table-column>
              <el-table-column prop="teahPosts" label="职位" show-overflow-tooltip></el-table-column>
              <el-table-column fixed="right" label="操作" width="100">
                                <template slot-scope="scope">
								  <el-button @click="deleteTeacher(scope.row)" type="text" size="small">删除</el-button>
								</template>-->
              </el-table-column>
            </el-table>
            <el-col :span="24" class="toobar">
              <el-pagination @current-change="handleCurrentChange"
                             :current-page="pagination.currentPage"
                             :page-size="pagination.pageSize"
                             layout="total, prev, pager, next, jumper"
                             :total="pagination.total">
              </el-pagination>
            </el-col>
          </div>

        </div>
        <div class="add-form">
          <el-dialog title="新增教师" :visible.sync="dialogFormVisible" style="line-height: 30px">
            <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="教师编码" prop="teahId">
                    <el-input v-model="formData.teahId"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="教师姓名" prop="teahName">
                    <el-input v-model="formData.teahName"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="年龄">
                    <el-input v-model="formData.teahAge"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="职位">
                    <el-select v-model="formData.teahPosts">
                      <el-option label="普通教师" value="普通教师"></el-option>
                      <el-option label="资深教授" value="资深教授"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="联系电话">
                    <el-input v-model="formData.teahPhone" ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="权限">
                    <el-select v-model="formData.permission">
                      <el-option label="一般教师" value="D"></el-option>
                      <el-option label="管理员" value="A"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="住址">
                    <el-input v-model="formData.teahAddress" ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取消</el-button>
              <el-button type="primary" @click="handleAdd()">确定</el-button>
            </div>
          </el-dialog>
        </div>
      </div>
      

    </el-card>
  </div>
</template>
<script>
export default {
  name:'TeacherManage.vue',
  data(){
    return{
      
      dataList:[], // table中的data
      rows:[],
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 5,//每页显示的记录数
        total: 0,//总记录数
        queryString:null
      },
      formData: {
        teahPwd:'123456',
      },//表单数据
      dialogFormVisible: false,//增加表单是否可见
      dialogFormVisible4Edit:false,//编辑表单是否可见
      rules: {//校验规则
        teahId: [{ required: true, message: '教师编码为必填项', trigger: 'blur' }],
        teahName: [{ required: true, message: '教师姓名为必填项', trigger: 'blur' }]
      }
    }

  },
  created() {
    this.findPage()
  },
  methods:{
    _findPage(){
      
      this.pagination.currentPage=1;
      if(this.pagination.queryString ==''){
        this.pagination.queryString = null;
        this.findPage();
      }
      else{
        this.findPage();
      }
	},
    findPage() {
      var param = {
        currentPage:this.pagination.currentPage,//页码
        pageSize:this.pagination.pageSize,//每页显示的记录数
        queryString:this.pagination.queryString
      };
      //请求后台
      this.$ajax.post("/teach/checkTeacher",param).then((response)=> {
        console.log(response.data);
        
        //为模型数据赋值，基于Vue的双向绑定展示到页面
        this.pagination.total = response.data.total;
        this.dataList = response.data.rows;

      });
    },
    handleCurrentChange(currentPage){
      //currentPage为切换后的页码
      this.pagination.currentPage = currentPage;
      this.findPage();
    },
    handleCreate(){
      this.resetForm();
      this.dialogFormVisible=true;
    },
    resetForm() {
      this.formData = {};
    },
    deleteTeacher(row) {
      this.$confirm("您确定要删除此条教师信息吗？","提示",{type:'waring'}).then(()=>{
        this.$ajax.get("/teach/deleteTeacher?teahId=" + row.teahId).then((res)=> {
          console.log(res.data);
          console.log(row.id);
          console.log(row.teahId);
          if(!res.data.flag){
            this.$message.error(res.data.message);
            console.log(res.data.flag);
          }
          else{
            this.$message({
              message: res.data.message,
              type: 'success'
            });
            console.log(res.data.flag);
          }
          this.findPage();

        });
      });
    },
    handleAdd(){
      this.$refs['dataAddForm'].validate((valid) => {
        if (valid) {
          //表单数据校验通过，发送ajax请求将表单数据提交到后台
          this.$ajax.post("/teach/addTeacher",this.formData).then((response)=> {
            //隐藏新增窗口
            this.dialogFormVisible = false;
            //判断后台返回的flag值，true表示添加操作成功，false为添加操作失败

            if(response.data.flag){
              //Message不同状态
              this.$message({
                message: response.data.message,
                type: 'success'
              });
            }else{
              this.$message.error(response.data.message);
            }
          }).finally(()=> {
            this.findPage();
          });
        } else {
          this.$message.error("表单数据校验失败");
          return false;
        }
      });
    
    }
    
  }
}
</script>
<style>
.center-contain31{
    padding:14px;
}
.box-card31 {
    height: 570px;
    width: 100%;
}
.infocard31{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead31{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead31{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line11{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.main-data1{
    position: absolute;
    top: 90px;
    width: 100%;
    //background-color: #3078d2;
    height: 400px;
}
.data-head{
    padding-top: 10px;
    padding-bottom: 10px;
    
	line-height: 20px;
	//background-color: #ffe603;
}
.table-body{
    width: 100%;
    height: 340px;
}
.add-form{
	width: 200px;
	height: 300px;

}

</style>