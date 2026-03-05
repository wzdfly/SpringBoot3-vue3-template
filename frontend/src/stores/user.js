import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const user = ref({
    username: '',
    role: '',
    isAuthenticated: false
  })

  function setUser(username, role) {
    user.value.username = username
    user.value.role = role
    user.value.isAuthenticated = true
    // 同时保存到 localStorage 以防止刷新丢失
    localStorage.setItem('user_info', JSON.stringify({
      username,
      role
    }))
  }

  function clearUser() {
    user.value.username = ''
    user.value.role = ''
    user.value.isAuthenticated = false
    localStorage.removeItem('user_info')
  }

  // 从 localStorage 恢复
  function restoreUser() {
    const saved = localStorage.getItem('user_info')
    if (saved) {
      const { username, role } = JSON.parse(saved)
      user.value.username = username
      user.value.role = role
      user.value.isAuthenticated = true
    }
  }

  return { user, setUser, clearUser, restoreUser }
})
