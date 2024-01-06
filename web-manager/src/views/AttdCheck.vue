<template>
  <div class="center-contain2">
    <el-card class="box-card2">
      <div class="infocard2">
        <div class="infohead2">
          <div class="lefthead2">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">考勤统计</p>
          </div>
          
        </div>
        <div class="main-data">
          <div class="select-part">
            <el-row :gutter="10" class="el-row-select">
              <el-col :span="8" class="el-col-select">
                <div class="grid-content bg-purple">
                  学科 :
                  <el-select v-model="subId" placeholder="请选择" @change="reGetId">
                    <el-option
                      v-for="item in subjects"
                      :key="item.subId"
                      :label="item.subName"
                      :value="item.subId">
                    </el-option>
                  </el-select>
                </div>
              </el-col>
              
              <el-col :span="8" class="el-col-select">
                <div class="grid-content bg-purple">
                  日期 :
                  <el-date-picker
                    v-model="attdDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions"
                    @change="reGetId"
                    placeholder="选择日期">
                  </el-date-picker>
                </div>
              </el-col>
              <el-col :span="8" class="el-col-select">
                <div class="grid-content bg-purple">
                  状态 :
                  <el-select v-model="state" placeholder="请选择">
                    <el-option
                      v-for="item in states"
                      :key="item.state"
                      :label="item.label"
                      :value="item.state">
                    </el-option>
                  </el-select>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="table-data">
            <el-table
              height="100%"
              ref="multipleTable"
              current-row-key="id"
              :data="dataList"
              tooltip-effect="dark"
              style="width: 100%;line-height: 20px">
<!--              @selection-change="handleSelectionChange"-->
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="stuName" label="姓名"></el-table-column>
              <el-table-column prop="stuId"  label="学号" show-overflow-tooltip></el-table-column>
              <el-table-column prop="attdTime" label="签到时间" show-overflow-tooltip></el-table-column>
              <el-table-column prop="leaveTime" label="签退时间" show-overflow-tooltip></el-table-column>
              <el-table-column prop="attdFlag" label="签到状态" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.attdFlag == 1"><el-tag type="danger">迟到</el-tag></span>
                  <span v-if="scope.row.attdFlag == 2"><el-tag type="success">正常</el-tag></span>
                  <span v-if="scope.row.attdFlag == 0"><el-tag type="info">缺席</el-tag></span>
                </template>
              </el-table-column>
              <el-table-column prop="leaveFlag" label="签退状态" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.leaveFlag == 1"><el-tag type="warning">早退</el-tag></span>
                  <span v-if="scope.row.leaveFlag == 2"><el-tag type="success">正常</el-tag></span>
                  <span v-if="scope.row.leaveFlag == 0"><el-tag type="info">缺席</el-tag></span>
                </template>
              </el-table-column>
              <el-table-column fixed="right" label="详情" width="100">
                <template slot-scope="scope">
                  <el-button @click="handleDetail(scope.row)" type="text" size="small">详情</el-button>
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
        <div class="line2" />
      </div>

    </el-card>
  </div>
</template>
<script>

export default {
  name: 'AttdPublishCheck.vue',
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      subjects: [{
        subId: '1',
        subName: '数据库概论'
      }],
      states: [{
        state: '1',
        label: '迟到'
      }, {
        state: '2',
        label: '早退'
      }, {
        state: '3',
        label: '缺席'
      }, {
        state: '4',
        label: '全部'
      }
      ],
      teahId: '',
      subId: '',
      state:'',
      classId: '',
     
      attdTime: '',
      leaveTime:'',
      attdFlag:'',
      leaveFlag:'',
      attdId: '',
      attdDate:'',

      pagination: {//分页相关模型数据
        currentPage: 1,//当前页码
        pageSize: 10,//每页显示的记录数
        total: 0,//总记录数
        queryString: this.attdId,//查询条件
        queryState:this.state
      },
      dataList:[],//表格数据
      rows:[],
    }
    

  },
  watch:{
    state: 'judgeOpt',
    attdDate: 'judgeOpt',
    subId: 'judgeOpt',
  },
  created() {
    this.teahId = this.$store.state.teahId;
    
    this.$ajax.get('/user/checkSub?teahId='+this.teahId).then((res)=>{
      
      console.log(res.data);
      if (res.data.flag) {
        this.subjects = res.data.data;
        this.$message({
          message: '获取学科信息成功！',
          type: 'success'
        });
      }
    });
    
  },
  methods:{
    judgeOpt(){
      console.log(this.attdId);
      console.log(this.subId);
      
      console.log(this.state);
      console.log(this.attdDate);
      
      if(this.subId!=''&&this.state!=''&&this.attdDate!=''){
        console.log("panduan");
        this.queryAttdId();
        // 这里获取的attdId为空是因为上面的函数还没有执行完，attd还没有被赋值
        //setTimeout( 2000 );
        
        
        //console.log(this.attdId+"122");
        //this.queryTable();
      };
      //在3000毫秒后跳出弹窗‘‘对不起, 要你久候’’

      
      if(this.attdId !=''){
      
      }
     
      
    },
    reGetId(){
      this.attdId = '';
      this.queryAttdId();
    },
    
    queryAttdId(){
      if(this.subId!=''&&this.state!=''&&this.attdDate!='') {
        this.$ajax.get("/attd/getAttdId", {
          params: {
            teahId: this.teahId,
            subId: this.subId,
            attdDate: this.attdDate
          }
        }).then((res) => {
          console.log(res.data);
          if (res.data.flag) {
            this.attdId = res.data.data;
            console.log(this.attdId);
            this.findPage();
            this.attdId = '';
          } else {
            this.dataList = undefined;
          }

        }).catch(function(error) {
          this.$message({ message: "获取失败" + error, type: "error" });
        });
      }2
    },
    /**
     * 将日期格式化为指定格式
     * @param val  当前时间： 例- new Data();
     * @param pattern  转化的格式 例- yyyy年-MM月-dd日 hh时:mm分:ss秒
     * @returns 返回格式化后的时间
     */
    /*formatDate(val, pattern) {
      if (!val) {
        return null;
      }
      if (!pattern) {
        pattern = "yyyy-MM-dd hh:mm:ss"
      }
      return new Date(val).format(pattern);
    },*/
    dateFormat(){
    
    },
    handleDetail(row){
    
    },
    handleCurrentChange(currentPage) {
      //currentPage为切换后的页码
      this.pagination.currentPage = currentPage;
      this.findPage();
    },
    findPage() {
      var param = {
        currentPage:this.pagination.currentPage,//页码
        pageSize:this.pagination.pageSize,//每页显示的记录数
        queryString:this.attdId,//查询条件
        queryState: this.state
      };
      //请求后台
      this.$ajax.post("/attd/check",param).then((response)=> {
        console.log(response.data);
        //为模型数据赋值，基于Vue的双向绑定展示到页面
        this.pagination.total = response.data.total;
        this.dataList = response.data.rows;

      });
    },
    
  },
  
}
</script>
<style>
.center-contain2{
    padding:14px;
}
.box-card2 {
    height: 570px;
    width: 100%;
}
.infocard2{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead2{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead2{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line2{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.main-data{
	position: absolute;
	top: 90px;
	width: 100%;
	//background-color: #3078d2;
	height: 400px;
}
.select-part{
	//background-color: #ffe603;
	padding-top: 10px;
    padding-bottom: 10px;
	line-height: 20px;
	
}
.el-row-select {
	margin-top: 0px;
    margin-bottom: 0px;
    &:last-child {
        margin-bottom: 0;
    }
}
.el-col-select {
    border-radius: 4px;
}
.table-data{
	width: 100%;
	height: 340px;
}
</style>
