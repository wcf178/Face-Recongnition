<template>
  <div class="center-contain3">
    <el-card class="box-card3">
      <div class="infocard3">
        <div class="infohead3">
          <div class="lefthead3">
            <p style="font-family:黑体;font-size:20px;line-height: 20px">教务管理</p>
          </div>
        </div>
        <div class="line1" />
      </div>

    </el-card>
  </div>
</template>
<script>
export default {
  name:'TeachManage.vue',
  data(){
    return{

    }

  }
}
</script>
<style>
.center-contain3{
    padding:14px;
}
.box-card3 {
    height: 570px;
    width: 100%;
}
.infocard3{
    position: relative;
    height: 90%;
    width: 90%;
    padding-left: 40px;

}
.infohead3{
    position: relative;
    text-align: left;
    padding-left: 0px;
    height: 30px;
}
.lefthead3{
    background: #409EFF;
    position:absolute;
    top:0px;
    eft:0px;
    height: 10px;
    float: left;
    padding-left: 0px;
    padding-top: 0px
}
.line1{
    float:right;
    width: 100%;
    height: 1px;
    margin-top: 30px;
    margin-bottom: 10px;
    background: #3c5364;
    position: relative;
    text-align: center;
}
</style>