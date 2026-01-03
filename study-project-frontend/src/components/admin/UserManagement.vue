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
import { ref, reactive, onMounted } from 'vue'
import { post, get } from '@/net'
import { ElMessage } from 'element-plus'

// 用户数据
const users = ref([])

// 对话框状态
const dialogVisible = ref(false)

// 表单数据
const formData = reactive({
  username: '',
  email: '',
  role: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' },
    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9_-]+$/, message: '用户名只能包含中文、英文、数字、下划线和横线', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 表格选择
const multipleSelection = ref([])

// 获取用户列表
const fetchUsers = () => {
  get('/api/admin/users', 
    (data) => {
      users.value = data
    },
    (error) => {
      ElMessage.error('获取用户列表失败: ' + error)
      // 如果获取失败，使用模拟数据
      users.value = [
        { id: 1, username: 'admin', email: 'admin@example.com', role: 'ADMIN', createTime: '2024-01-01 10:00:00' },
        { id: 2, username: '李四', email: 'lisi@example.com', role: 'USER', createTime: '2024-01-02 11:00:00' },
        { id: 3, username: '王五', email: '1660448615@qq.com', role: 'USER', createTime: '2024-01-03 12:00:00' }
      ]
    }
  )
}

// 处理表格选择变化
const handleSelectionChange = (val) => {
  multipleSelection.value = val
}

// 批量删除
const handleBatchDelete = () => {
  if (multipleSelection.value.length === 0) {
    ElMessage.warning('请先选择要删除的用户')
    return
  }
  ElMessage.info('批量删除功能待实现')
}

// 编辑用户
const handleEdit = (row) => {
  ElMessage.info('编辑用户功能待实现')
}

// 删除用户
const handleDelete = (row) => {
  ElMessage.info('删除用户功能待实现')
}

// 提交表单
const submitForm = () => {
  // 验证表单
  if (!formData.username || !formData.email || !formData.role) {
    ElMessage.error('请填写完整的用户信息')
    return
  }

  // 调用后端管理员添加用户API
  const userData = new URLSearchParams()
  userData.append('username', formData.username)
  userData.append('email', formData.email)
  userData.append('role', formData.role)

  post('/api/admin/users', userData, 
    (message) => {
      ElMessage.success('用户添加成功')
      dialogVisible.value = false
      resetForm()
      fetchUsers() // 重新获取用户列表
    },
    (error) => {
      ElMessage.error(error || '添加用户失败')
    }
  )
}

// 重置表单
const resetForm = () => {
  formData.username = ''
  formData.email = ''
  formData.role = ''
}

// 处理对话框关闭
const handleDialogClose = () => {
  resetForm()
}

// 组件挂载时获取用户列表
onMounted(() => {
  fetchUsers()
})

</script>

<style scoped>
.user-management {
  padding: 20px;
}
</style>