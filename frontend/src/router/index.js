import { createRouter, createWebHistory } from 'vue-router'

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
      path:'/index',
      name:'index',
      component:() => import('@/views/IndexView.vue'),
      children:[
        {
          path:'',
          name:'datashow-index',
          component:()=>import('@/components/datashow/index.vue')
        },
        {
          path:'fileimport',
          name:'datasource-fileimport',
          component:()=>import('@/components/datasource/fileimport.vue')
        },
        {
          path:'dbmove',
          name:'datasource-dbmove',
          component:()=>import('@/components/datasource/dbmove.vue')
        },
      ]
    },{
      // 新增管理员路由
      path:'/admin',
      name:'admin',
      component:() => import('@/views/AdminView.vue'),
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
    }
  ]
})

export default router
