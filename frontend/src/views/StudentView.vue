<template>
  <el-container class="student_layout">
    <el-container style="height: auto">
      <el-header style="padding: 0;height: 80px">
        <div id="logo">
          <img src="../img/logo.png" alt="" id="img1" />
          <span id="title">学生端</span>
        </div>
        <div class="flex flex-wrap items-center">
          <el-dropdown>
            <el-button type="primary" id="zhanghu">
              学生<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
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
            <el-menu-item index="1" @click="router.push('/student')">
              <el-icon><Monitor /></el-icon>
              <span>控制台</span>
            </el-menu-item>
            
            <el-menu-item index="2">
              <el-icon><House /></el-icon>
              <span>我的宿舍</span>
            </el-menu-item>
            
            <el-menu-item index="3">
              <el-icon><EditPen /></el-icon>
              <span>提交报修申请</span>
            </el-menu-item>
            
            <el-menu-item index="4">
              <el-icon><CreditCard /></el-icon>
              <span>费用查询与缴纳</span>
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
  House,
  EditPen,
  CreditCard,
  ArrowDown
} from "@element-plus/icons-vue";

const logout = () => {
  get("/api/auth/logout", (message) => {
    ElMessage.success(message);
    router.push("/");
  });
};
</script>

<style scoped>
.student_layout {
  height: 100vh;
}
.el-header {
  background-color: #313743;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #409EFF;
  font-size: 25px;
  font-weight: bolder;
}
#logo {
  display: flex;
  align-items: center;
}
#title {
  margin-left: 25px;
}
#img1 {
  width: 10vh;
  height: 10vh;
  margin-left: 1vh;
}
.el-aside {
  background-color: #313743;
}
.el-aside .el-menu {
  border-right: none;
}
.el-main {
  background-color: #e9edf1;
}
.flex {
  margin-right: 5vh;
}
</style>
