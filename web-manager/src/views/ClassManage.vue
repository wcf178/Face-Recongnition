<template>
  <div class="center-contain33">
    <el-card class="box-card33">
      <div class="infocard33">
        <div class="infohead33">
          <div class="lefthead33">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">班级管理</p>
          </div>
        </div>
        <div class="line13" />
        <div class="main-data3">
          <div class="data-head3">
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
                  <el-button  type="primary" @click="handleCreate">添加班级</el-button>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="table-body3">
            <el-table
              height="100%"
              ref="multipleTable"
              :data="dataList"
              tooltip-effect="dark"
              style="width: 100%;line-height: 20px">
              <!--              @selection-change="handleSelectionChange"-->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="classId"  label="班级编号" show-overflow-tooltip></el-table-column>
              <el-table-column prop="subName" label="班级学科" show-overflow-tooltip></el-table-column>
              <el-table-column prop="stuId" label="学生ID" show-overflow-tooltip></el-table-column>
              <el-table-column prop="stuName"  label="学生姓名" show-overflow-tooltip></el-table-column>
              <el-table-column prop="stuPhone"  label="联系电话" show-overflow-tooltip></el-table-column>
              <el-table-column fixed="right" label="操作" width="100">
                           <template slot-scope="scope">
								  <el-button @click="deleteStudent(scope.row)" type="text" size="small">删除</el-button>
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
          <el-dialog title="新增班级" :visible.sync="dialogFormVisible" style="line-height: 20px">
            <template>
              <el-tabs v-model="activeName" type="card">
                
                  <el-form ref="dataAddForm" :model="formData" :rules="rules" label-position="right" label-width="100px">
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="教师姓名" prop="teahId">
                          <el-select
                            v-model="formData.teahId" placeholder="请选择教师" >
                            <!--防止key重复 -->
                          <el-option
                            v-for="item in teachers"
                            :key="item.id"
                            :label="item.teahName"
                            :value="item.teahId">
                          </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="学科名称" prop="subId">
                          <el-select
                            v-model="formData.subId" placeholder="请选择学科">
                          <el-option
                            v-for="item in subjects"
                            :key="item.id"
                            :label="item.subName"
                            :value="item.subId">
                            
                          </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col :span="12">
                        <el-form-item label="班级编码" prop="classId">
                          <el-input v-model="formData.classId"/>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </el-form>
                
                
                  <div class="checkScrol">
                    <table class="datatable">
                      <thead>
                      <tr>
                        <th>选择</th>
                        <th>学生学号</th>
                        <th>学生姓名</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr v-for="c in tableData">
                        <td>
                          <input :id="c.stuId" v-model="stuIds" type="checkbox" :value="c.stuId">
                        </td>
                        <td><label :for="c.stuId">{{c.stuId}}</label></td>
                        <td><label :for="c.stuId">{{c.stuName}}</label></td>
                      </tr>
                      </tbody>
                    </table>
                  </div>
                
              </el-tabs>
            </template>
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
  name:'ClassManage.vue',
  data(){
    return{
      activeName:'',
      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 5,//每页显示的记录数
        total: 0,//总记录数
        queryString:''
      },
      subjects:{
        subId:'',
        subtName:''
      },
      teachers:{
        teahId:'',
        teahName:''
      },
      
      dataList:[],//表格数据
      stuIds:[],
      tableData:[],
      formData: {},//表单数据
      dialogFormVisible: false,//增加表单是否可见
      dialogFormVisible4Edit:false,//编辑表单是否可见
      rules: {//校验规则
        teahId: [{ required: true, message: '教师编码为必填项', trigger: 'blur' }],
        subId: [{ required: true, message: '学科编码为必填项', trigger: 'blur' }],
        classId: [{ required: true, message: '班级编码为必填项', trigger: 'blur' }],
      },
    }

  },
  created() {
    this.findPage();
  },
  methods:{
    _findPage(){
      this.findPage();
      
    },
    findPage() {
      var param = {
        currentPage:this.pagination.currentPage,//页码
        pageSize:this.pagination.pageSize,//每页显示的记录数
        queryString:this.pagination.queryString
      };
      //请求后台
      if(this.pagination.queryString != '') {
        this.$ajax.post("/teach/checkClass", param).then((response) => {
          console.log(response.data);
          //为模型数据赋值，基于Vue的双向绑定展示到页面
          this.pagination.total = response.data.total;
          this.dataList = response.data.rows;
          
        });
      }
      else{
        this.$message({
          message: '请输入班级编号以查询',
          type: 'error'
        });
      }
    },
    handleCurrentChange(currentPage){
      //currentPage为切换后的页码
      this.pagination.currentPage = currentPage;
      this.findPage();
    },
    handleCreate(){
      this.resetForm();
      this.getTeacherAndSubject();
      this.dialogFormVisible = true;
      this.activeName="first";
      //获取检查组项目
      this.$ajax.get("/teach/findAllStudents").then((res)=>{
        console.log(res.data.data)
        if(res.data.flag){
          this.tableData = res.data.data;

        }
        else{
          this.$message.error("获取数据失败，请刷新当前页面");
        }

      });
    },
    resetForm() {
      this.formData = {};
      this.stuIds=[];
    },
    deleteStudent(row) {
      this.$confirm("您确定要删除此班级的该学生吗？","提示",{type:'waring'}).then(()=>{
        this.$ajax.get("/teach/deleteStudent?stuId=" + row.stuId).then((res)=> {
          console.log(res.data);
          console.log(row.stuId);
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
    getTeacherAndSubject(){
      this.$ajax.get("/teach/findAllTeacher").then((res)=> {
        console.log(res.data);

        if (!res.data.flag) {
          this.$message.error(res.data.message);
          console.log(res.data.flag);
        } else {
          this.teachers = res.data.data
          this.$message({
            message: res.data.message,
            type: 'success'
          });
          console.log(res.data.flag);
        }
      });
      this.$ajax.get("/teach/findAllSubject").then((res)=> {
        console.log(res.data);

        if (!res.data.flag) {
          this.$message.error(res.data.message);
          console.log(res.data.flag);
        } else {
          this.subjects = res.data.data
          this.$message({
            message: res.data.message,
            type: 'success'
          });
          console.log(res.data.flag);
        }
      });
    },
    handleAdd(){
      this.$refs['dataAddForm'].validate((valid) => {
        if (valid) {
          //表单数据校验通过，发送ajax请求将表单数据提交到后台
          console.log(this.formData);
          console.log(this.stuIds)
          var param = {
            classId:this.formData.classId,
            studentId:this.stuIds
          };
          if(this.stuIds != '') {
            this.$ajax.post("/teach/addClass", this.formData).then((response) => {
              //隐藏新增窗口
              this.dialogFormVisible = false;
              //判断后台返回的flag值，true表示添加操作成功，false为添加操作失败

              if (response.data.flag) {
                //Message不同状态
                this.$message({
                  message: response.data.message,
                  type: 'success'
                });
              } else {
                this.$message.error(response.data.message);
              }
            });
            this.$ajax.post("/teach/addStudents", param);
          }
          else{
            this.$message({
              message: "没有学生你创个锤子班级",
              type: 'error'
            });
          }
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
.center-contain33{
    padding:14px;
}
.box-card33 {
    height: 570px;
    width: 100%;
}
.infocard33{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead33{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead33{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line13{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.main-data3{
    position: absolute;
    top: 90px;
    width: 100%;
    //background-color: #3078d2;
    height: 400px;
}
.data-head3{
    padding-top: 10px;
    padding-bottom: 10px;

    line-height: 20px;
    //background-color: #ffe603;
}
.table-body3{
    width: 100%;
    height: 340px;
}
.checkScrol{
    height: 277px;
    overflow-y:scroll; ;
}
.datatable {
    position: relative;
    box-sizing: border-box;
    -webkit-box-flex: 1;
    width: 100%;
    max-width: 100%;
    font-size: 14px;
    color: rgb(96, 98, 102);
    overflow: hidden;
    flex: 1 1 0%;
}
.datatable td, .datatable th {
    padding: 12px 0;
    min-width: 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    text-overflow: ellipsis;
    vertical-align: middle;
    position: relative;
    text-align: left;
}
</style>