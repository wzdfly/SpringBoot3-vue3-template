<template>
  <div class="user-management">
    <h2>用户管理</h2>
    
    <el-card style="margin-top: 20px;">
      <div style="margin-bottom: 20px;">
        <el-button type="primary">新增用户</el-button>
        <el-button type="danger">批量删除</el-button>
      </div>
      
      <el-table :data="users" style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="role" label="角色" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.role === 'ADMIN' ? 'danger' : 'primary'">
              {{ scope.row.role === 'ADMIN' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" type="primary" @click="editUser(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteUser(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';

// 模拟用户数据
const users = ref([
  {
    id: 1,
    username: 'admin',
    email: 'admin@example.com',
    role: 'ADMIN',
    createTime: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    username: 'user1',
    email: 'user1@example.com',
    role: 'USER',
    createTime: '2024-01-02 14:30:00'
  }
]);

// 编辑用户
const editUser = (user) => {
  ElMessage.info(`编辑用户: ${user.username}`);
  // 这里可以添加编辑用户的逻辑
};

// 删除用户
const deleteUser = (userId) => {
  ElMessageBox.confirm('确定要删除这个用户吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 从数组中移除用户
    const index = users.value.findIndex(user => user.id === userId);
    if (index > -1) {
      users.value.splice(index, 1);
      ElMessage.success('删除成功');
    }
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
};
</script>

<style scoped>
.user-management {
  padding: 20px;
}
</style>