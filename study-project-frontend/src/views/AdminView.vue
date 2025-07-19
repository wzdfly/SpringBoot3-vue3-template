<template>
  <el-container class="admin_layout">
    <el-container style="height: auto">
      <el-header style="padding: 0;height: 80px">
        <div id="logo">
          <img src="../img/logo.png" alt="" id="img1" />
          <span id="title">管理员控制台</span>
        </div>
        <div class="flex flex-wrap items-center">
          <el-dropdown>
            <el-button type="primary" id="zhanghu">
              管理员<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>系统设置</el-dropdown-item>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <el-container>
        <el-aside width="200px">
          <el-menu
            active-text-color="#409eff"
            background-color="#545c64"
            class="el-menu-vertical-demo"
            default-active="1"
            text-color="#fff"
          >
            <el-menu-item index="1" @click="router.push('/admin')">
              <el-icon><Monitor /></el-icon>
              <span>控制台</span>
            </el-menu-item>
            
            <el-sub-menu index="2">
              <template #title>
                <el-icon><User /></el-icon>
                <span>用户管理</span>
              </template>
              <el-menu-item index="2-1" @click="router.push('/admin/users')">
                用户列表
              </el-menu-item>
              <el-menu-item index="2-2">
                角色管理
              </el-menu-item>
            </el-sub-menu>
            
            <el-sub-menu index="3">
              <template #title>
                <el-icon><Setting /></el-icon>
                <span>系统管理</span>
              </template>
              <el-menu-item index="3-1" @click="router.push('/admin/settings')">
                系统设置
              </el-menu-item>
              <el-menu-item index="3-2">
                日志管理
              </el-menu-item>
            </el-sub-menu>
            
            <el-menu-item index="4">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据统计</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        
        <el-main>
          <router-view v-slot="{ Component }">
            <transition name="el-fade-in-linear">
              <component :is="Component" />
            </transition>
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script setup>
import { ElMessage } from "element-plus";
import { get } from "@/net";
import router from "@/router";
import {
  Monitor,
  User,
  Setting,
  DataAnalysis,
  ArrowDown
} from "@element-plus/icons-vue";

const logout = () => {
  get("/api/auth/logout", (message) => {
    ElMessage.success(message);
    router.push("/");
  });
};
</script>

<style>
.admin_layout {
  height: 100vh;
  background-color: #f0f2f5;
}

.admin_layout .el-aside {
  background-color: #001529;
}

.admin_layout .el-aside .el-menu {
  border-right: none;
}

.admin_layout .el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

.admin_layout .el-header {
  background-color: #001529;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 25px;
  font-family: "微软雅黑";
  font-weight: bolder;
}

.admin_layout #logo {
  display: flex;
  align-items: center;
}

.admin_layout #title {
  margin-left: 25px;
  color: #fff;
}

.admin_layout #img1 {
  width: 10vh;
  height: 10vh;
  margin-left: 1vh;
}

.admin_layout .flex {
  margin-right: 5vh;
}
</style>