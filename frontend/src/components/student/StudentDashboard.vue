<template>
  <div style="padding: 20px">
    <h2>学生端控制台</h2>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>我的宿舍</template>
          <div>查看分配到的宿舍与舍友</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>报修申请</template>
          <div>在线提交宿舍报修单</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>费用查询</template>
          <div>查看水费、电费等缴纳情况</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
</script>
