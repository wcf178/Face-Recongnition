



<template xmlns:el-col="http://www.w3.org/1999/html">
  <div class="center-contain1">
    <el-card class="box-card1">
      <div class="infocard1">
        <div class="infohead1">
          <div class="lefthead1">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">考勤发布</p>
          </div>
          
        </div>
        <div class="line3" />
        <div class="main-data0">
          <div class="select-part0">
            <el-row :gutter="10" class="el-row-select1">
              <el-col :span="24" class="el-col-select">
                <div class="grid-content bg-purple">
                  学科 :
                  <el-select v-model="subId" placeholder="请选择">
                    <el-option
                      v-for="item in subjects"
                      :key="item.subId"
                      :label="item.subName"
                      :value="item.subId">
                    </el-option>
                  </el-select>
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="10" class="el-row-select2">
             
              <el-col :span="12" :offset="6" class="el-col-select">
                <div class="address-text">
                  地点     :     <el-button style="width: 220px" @click="selectAdress">选择地点</el-button>
                </div>
              </el-col>
              
            </el-row>
            <el-row :gutter="10" class="el-row-select2">
              <el-col :span="24" class="el-col-select">
                <div class="grid-content bg-purple">
                  距离 :
                  <el-select v-model="distance" placeholder="单位：m">
                    <el-option
                      v-for="item in distances"
                      :key="item.id"
                      :label="item.distance"
                      :value="item.distance">
                    </el-option>
                  </el-select>m
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="10" class="el-row-select2">
              <el-col :span="24" class="el-col-select">
                <div class="grid-content bg-purple">
                  考勤时间 :
                  <el-time-picker
                    style="width: 220px"
                    is-range
                    v-model="time"
                    format='HH:mm'
                    value-format="HH:mm"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                    placeholder="选择考勤时间">
                  </el-time-picker>
                </div>
              </el-col>
            </el-row>
            <el-row class="el-row-select2">
              <el-col :span="24">
                <el-button style="width: 30%" type="primary" :disabled="uncompleted" @click="buttonControl">发布考勤任务</el-button>
              </el-col>
            </el-row>
            
          </div>
          <div class="table-data">

          </div>
        </div>
   <addressDialog
     :updatelatitude="updatelatitude"
     :updatelongitude="updatelongitude"
     ref="dialog" ></addressDialog>
      </div>

    </el-card>
  </div>
</template>
<script>
import addressDialog from "@/views/dialog/addressDialog.vue";

export default {
  name: 'AttdPublish.vue',
  components: {addressDialog},
  data() {
    return {
      subjects: [{
        subId: '1',
        subName: '数据库概论'
      }],
      distances:[
        {
          id: 1,
          distance: 100
        },
        {
          id:2,
          distance:200
        },
        {
          id:3,
          distance:300
        },
        {
          id:4,
          distance:400
        },
        {
          id:5,
          distance: 500000
        }],

   
      latitude: '',
      longitude: '',
      distance:'',
      
      teahId: '',
      subId: '',
      
      time:'',
      attdDate: new Date(),
      uncompleted: true
    }

  },

  watch: {
    time: 'judgeOpt',
    subId: 'judgeOpt',
	latitude:'judgeOpt',
    longitude:'judgeOpt',
    distance:'judgeOpt',
  },
  /*computed:{
        completed: function(){
          let flag = this.completed;
          if(this.sub!=''&&this.clas!=''&&this.sadTime!=''&&this.hapTime!=''){
            flag = true;
          }
          else{
            flag = false;
          }
          return flag;
        }
	  },*/
  mounted() {
  
  },
  created() {
    this.teahId = this.$store.state.teahId;
    this.getSubject();
  },
  methods: {
    judgeOpt() {
      console.log("publish"+this.longitude +"lai"+this.latitude)
      console.log(this.time)
      console.log(this.time[1])
      if (this.subId != '' &&
        this.time != ''&& this.time != null&&
        this.longitude !=''&&this.latitude!=''&&
        this.distance!=null&&this.distance!='') {
        this.uncompleted = false;
      } else {
        this.uncompleted = true;
      }
      console.log(this.uncompleted);

    },
    dateFormat(){
      var date = new Date()
      var year = date.getFullYear()
      var month = date.getMonth() + 1 < 10 ?
        '0' + (date.getMonth() + 1) : date.getMonth()+ 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      return year + '-' +month + '-' + day
    },
    buttonControl() {
      this.completed = true;
      var attdDate1 = this.getDate();
      
      console.log(attdDate1);
      console.log(this.subId);
      console.log(this.teahId);
      //console.log(this.sadTime);
      this.$ajax.get('/attd/publish', {
        params: {
          teahId: this.teahId,
          subId: this.subId,
          attdDate:attdDate1,
          startTime:this.time[0],
          endTime:this.time[1],
          latitude: this.latitude,
          longitude: this.longitude,
          distance: this.distance
        }
      }).then((res) => {
        console.log(res.data);
        if (res.data.flag) {
          this.$message({
            message: '发布考勤成功！',
            type: 'success'
          });
        }
        else{
          this.$message({
            message:res.data.message,
            type: 'error'
          });
        }
      });
      this.subId = '';
      this.time = [];
     

    },
    getSubject() {
      this.$ajax.get('/user/checkSub?teahId=' + this.teahId).then((res) => {

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
    getDate(){
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      month = month > 9 ? month : '0' + month;
      strDate = strDate > 9 ? strDate : '0' + strDate;
      var newdata = year + '-' + month + '-' + strDate;
      return newdata;
    },
    getTime(){
      let hh = new Date().getHours();
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
      var newdata = hh+':'+mf+':'+ss;
      return newdata;
    },
    selectAdress(){
        this.$refs.dialog.openDialog("选择地点")
    },
    showDialog(visible) {
      this.mapOut = visible;
    },
    updatelatitude(key){
      console.log(key)
      this.latitude = key;
      
      console.log("publish1 "+this.latitude)
    },
    updatelongitude(key){
      this.longitude = key;
      console.log("publish2 "+this.longitude)
    }
    
    
    
  }
}
</script>
<style>
.center-contain1{
    padding:14px;
}
.box-card1 {
    height: 570px;
    width: 100%;
}
.infocard1{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead1{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead1{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line3{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
	margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
.main-data0{
    position: absolute;
    top: 90px;
    width: 100%;
    //background-color: #3078d2;
    height: 400px;
}
.select-part0{
    //background-color: #ffe603;
    padding-top: 10px;
    padding-bottom: 10px;
    line-height: 20px;

}
.el-row-select1 {
    margin-top: 0px;
    margin-bottom: 0px;
    &:last-child {
        margin-bottom: 0;
    }
}
.el-row-select2 {
    margin-top: 40px;
    margin-bottom: 0px;
    &:last-child {
        margin-bottom: 0;
    }
}
.el-col-select {
    border-radius: 4px;
}
.address-text{

}
.input-address{
    width: 220px
}
</style>