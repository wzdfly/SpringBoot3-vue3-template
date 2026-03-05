<script setup>

</script>

<template>
    <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
        <div style="flex: 1;background-color: dodgerblue">
            <el-image style="width: 100%;height: 100%;"  fit="cover"
                      src="https://plus.unsplash.com/premium_photo-1733306532535-a9c18fc0602e?q=80&w=871&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"/>
        </div>
        <div class="welcome-title" >
            <div style="font-size: 30px;font-weight: bold">欢迎进入曲易宿舍管理系统</div>
            <div style="margin-top: 10px">结合大数据平台，为客户提供数据获取、数据治理、数据共享等优质服务！</div>
        </div>
        <div style="width: 400px;background-color: white;z-index: 1">
            <router-view v-slot="{ Component }">
                <transition name="el-fade-in-linear" mode="out-in">
                    <component :is="Component" />
                </transition>
            </router-view>
        </div>
    </div>
</template>

<style scoped>
.welcome-title{
    position: absolute;
    bottom:30px;
    left: 30px;
    color:white;
    text-shadow: 0 0 10px black;
}
</style>