<template>
  <div style="padding: 20px">
    <h2>宿舍管理员控制台</h2>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>宿舍管理</template>
          <div>管理所属楼栋的房间信息</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>学生入离宿</template>
          <div>处理学生登记与迁出</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>报修处理</template>
          <div>查看并跟进宿舍报修申请</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
</script>
