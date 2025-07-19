<template>
  <div class="system-settings">
    <h2>系统设置</h2>
    
    <el-card style="margin-top: 20px;">
      <h3>基础设置</h3>
      <el-form :model="settings" label-width="120px" style="margin-top: 20px;">
        <el-form-item label="系统名称">
          <el-input v-model="settings.systemName" style="width: 300px;" />
        </el-form-item>
        <el-form-item label="系统描述">
          <el-input v-model="settings.systemDesc" type="textarea" style="width: 500px;" />
        </el-form-item>
        <el-form-item label="邮件服务器">
          <el-input v-model="settings.mailServer" style="width: 300px;" />
        </el-form-item>
        <el-form-item label="邮件端口">
          <el-input v-model="settings.mailPort" style="width: 150px;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary">保存设置</el-button>
          <el-button>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const settings = ref({
  systemName: '物流中台系统',
  systemDesc: '结合大数据平台，为客户提供数据获取、数据治理、数据共享等优质服务！',
  mailServer: 'smtp.qq.com',
  mailPort: '587'
});
</script>

<style scoped>
.system-settings {
  padding: 20px;
}
</style>