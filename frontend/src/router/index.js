import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      name:'welcome',
      component:() => import('@/views/WelcomeView.vue'),
      children:[
        {
          path:'',
          name:'welcome-login',
          component:()=>import('@/components/welcome/LoginPage.vue')
        },
        {
          path:'register',
          name:'welcome-register',
          component:()=>import('@/components/welcome/RegisterPage.vue')
        },
        {
          path:'forget',
          name:'welcome-forget',
          component:()=>import('@/components/welcome/ForgetPage.vue')
        }
      ]
    },{
      // 管理员路由
      path:'/admin',
      name:'admin',
      component:() => import('@/views/AdminView.vue'),
      meta: { roles: ['SYS_ADMIN', 'ADMIN'] },
      children:[
        {
          path:'',
          name:'admin-dashboard',
          component:()=>import('@/components/admin/Dashboard.vue')
        },
        {
          path:'users',
          name:'admin-users',
          component:()=>import('@/components/admin/UserManagement.vue')
        },
        {
          path:'settings',
          name:'admin-settings',
          component:()=>import('@/components/admin/SystemSettings.vue')
        }
      ]
    },{
      // 宿舍管理员路由
      path:'/dorm',
      name:'dorm',
      component:() => import('@/views/DormView.vue'),
      meta: { roles: ['DORM_ADMIN'] },
      children:[
        {
          path:'',
          name:'dorm-dashboard',
          component:()=>import('@/components/dorm/DormDashboard.vue')
        }
      ]
    },{
      // 学生端路由
      path:'/student',
      name:'student',
      component:() => import('@/views/StudentView.vue'),
      meta: { roles: ['STUDENT'] },
      children:[
        {
          path:'',
          name:'student-dashboard',
          component:()=>import('@/components/student/StudentDashboard.vue')
        }
      ]
    }
  ]
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 尝试从 localStorage 恢复用户
  if (!userStore.user.isAuthenticated) {
    userStore.restoreUser()
  }

  const isUnauthorized = to.meta.roles && !to.meta.roles.includes(userStore.user.role)
  
  if (to.name && to.name.startsWith('welcome-') && userStore.user.isAuthenticated) {
    // 已登录状态下尝试进入登录页，重定向到其对应控制台
    if (userStore.user.role === 'SYS_ADMIN' || userStore.user.role === 'ADMIN') next('/admin')
    else if (userStore.user.role === 'DORM_ADMIN') next('/dorm')
    else if (userStore.user.role === 'STUDENT') next('/student')
    else next()
  } else if (isUnauthorized) {
    // 权限不足
    if (userStore.user.isAuthenticated) {
      // 已登录但无权访问，返回来源页或首页
      next(from.fullPath || '/')
    } else {
      // 未登录，跳转到登录页
      next('/')
    }
  } else {
    next()
  }
})

export default router
