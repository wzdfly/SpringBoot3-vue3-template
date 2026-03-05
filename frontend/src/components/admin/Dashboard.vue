<template>
  <div class="dashboard">
    <h2>管理员控制台</h2>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">1,234</div>
            <div class="stat-label">总用户数</div>
          </div>
          <el-icon class="stat-icon"><User /></el-icon>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">56</div>
            <div class="stat-label">今日新增</div>
          </div>
          <el-icon class="stat-icon"><Plus /></el-icon>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">89%</div>
            <div class="stat-label">系统负载</div>
          </div>
          <el-icon class="stat-icon"><Monitor /></el-icon>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-number">342</div>
            <div class="stat-label">在线用户</div>
          </div>
          <el-icon class="stat-icon"><Connection /></el-icon>
        </el-card>
      </el-col>
    </el-row>
    
    <el-card style="margin-top: 20px;">
      <h3>快速操作</h3>
      <el-row :gutter="20" style="margin-top: 15px;">
        <el-col :span="8">
          <el-button type="primary" size="large" style="width: 100%;" @click="router.push('/admin/users')">
            用户管理
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="success" size="large" style="width: 100%;" @click="router.push('/admin/settings')">
            系统设置
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="warning" size="large" style="width: 100%;">
            数据备份
          </el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import router from "@/router";
import { User, Plus, Monitor, Connection } from "@element-plus/icons-vue";
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  position: relative;
  overflow: hidden;
}

.stat-content {
  position: relative;
  z-index: 2;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.stat-icon {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 40px;
  color: #e6f7ff;
  z-index: 1;
}
</style>