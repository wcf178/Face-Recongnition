<template>
  <div class="center-contain32">
    <el-card class="box-card32">
      <div class="infocard32">
        <div class="infohead32">
          <div class="lefthead32">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">学科管理</p>
          </div>
        </div>
        <div class="line12" />

        <div class="main-data2">
          <div class="data-head2">
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
                  <el-button  type="primary" @click="handleCreate">添加学科</el-button>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="table-body2">
            <el-table
              height="100%"
              ref="multipleTable"
              current-row-key="id"
              :data="dataList"
              tooltip-effect="dark"
              style="width: 100%;line-height: 20px">
              <!--              @selection-change="handleSelectionChange"-->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="subId"  label="编号" show-overflow-tooltip></el-table-column>
              <el-table-column prop="subName" label="学科名称" ></el-table-column>
              <el-table-column prop="subAcademy" label="所属院系" show-overflow-tooltip></el-table-column>
              <el-table-column prop="subInfo" label="学科介绍" show-overflow-tooltip></el-table-column>
              <el-table-column fixed="right" label="操作" width="100">
                      <template slot-scope="scope">
                        <el-button @click="deleteSubject(scope.row)" type="text" size="small">删除</el-button>
                      </template>
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
          <el-dialog title="新增学科" :visible.sync="dialogFormVisible" style="line-height: 30px;">
            <el-form ref="dataAddForm1" :model="formData" :rules="rules1" label-position="right" label-width="100px">
              <el-row class="add-row">
                <el-col :span="12" :offset="6">
                  <el-form-item label="学科编码" prop="subId">
                    <el-input v-model="formData.subId" ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="add-row">
                <el-col :span="12" :offset="6">
                  <el-form-item label="学科名称" prop="subName">
                    <el-input v-model="formData.subName" ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row class="add-row">
                <el-col :span="12" :offset="6">
                  <el-form-item label="开课学院">
                    <el-select v-model="formData.subAcademy">
                      <el-option label="计算机学院" value="计算机学院"></el-option>
                      <el-option label="物理学院" value="物理学院"></el-option>
                      <el-option label="生化学院" value="生化学院"></el-option>
                      <el-option label="材料学院" value="材料学院"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              
              </el-row>
              <el-row class="add-row">
                <el-col :span="12" :offset="6">
                  <el-form-item label="学科描述">
                    <el-input v-model="formData.subInfo" ></el-input>
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
  name:'SubjectManage.vue',
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
        subId:'',
        subName:'',
        subAcademy:'',
        subInfo:''
      },//表单数据
      dialogFormVisible: false,//增加表单是否可见
      dialogFormVisible4Edit:false,//编辑表单是否可见
      rules1: {//校验规则
        subId: [{ required: true, message: '学科编码为必填项', trigger: 'blur' }],
        subName: [{ required: true, message: '学科名称为必填项', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.findPage()
  },
  methods: {
    
    _findPage() {
      this.pagination.currentPage = 1;
      if (this.pagination.queryString == '') {
        this.pagination.queryString = null;
        this.findPage();
      } else {
        this.findPage();
      }
    },
    findPage() {
      var param = {
        currentPage: this.pagination.currentPage,//页码
        pageSize: this.pagination.pageSize,//每页显示的记录数
        queryString: this.pagination.queryString
      };
      //请求后台
      this.$ajax.post("/teach/checkSubject", param).then((response) => {
        console.log(response.data);
        //为模型数据赋值，基于Vue的双向绑定展示到页面
        this.pagination.total = response.data.total;
        this.dataList = response.data.rows;

      });
    },
    handleCurrentChange(currentPage) {
      //currentPage为切换后的页码
      this.pagination.currentPage = currentPage;
      this.findPage();
    },
    deleteSubject(row) {
      this.$confirm("您确定要删除此条学科信息吗？","提示",{type:'waring'}).then(()=>{
        this.$ajax.get("/teach/deleteSubject?subId=" + row.subId).then((res)=> {
          console.log(res.data);
         
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
    handleCreate(){
      this.resetForm();
      this.dialogFormVisible=true;
    },
    resetForm() {
      this.formData = {};
    },
    handleAdd(){
      this.$refs['dataAddForm1'].validate((valid) => {
        if (valid) {
          //表单数据校验通过，发送ajax请求将表单数据提交到后台
          this.$ajax.post("/teach/addSubject",this.formData).then((response)=> {
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
.center-contain32{
    padding:14px;
}
.box-card32 {
    height: 570px;
    width: 100%;
}
.infocard32{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead32{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead32{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line12{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.main-data2{
    position: absolute;
    top: 90px;
    width: 100%;
    //background-color: #3078d2;
    height: 400px;
}
.data-head2{
    padding-top: 10px;
    padding-bottom: 10px;

    line-height: 20px;
    //background-color: #ffe603;
}
.table-body2{
    width: 100%;
    height: 340px;
}
.add-row{
	align-items: center;
}
</style>