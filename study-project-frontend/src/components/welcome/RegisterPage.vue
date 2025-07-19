

<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 70px">
            <div style="font-size: 25px">注册新用户</div>
            <div style="font-size: 14px">欢迎注册，请填写相关信息</div>
        </div>
        <div style="margin-top: 50px">
            <el-form  :model="form" :rules="rules" @validate="onValidate" ref="formRef">
                <el-form-item prop="username">
                    <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input  v-model="form.password" :maxlength="16" type="password" placeholder="密码">
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password_repeat">
                    <el-input  v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复输入密码">
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input v-model="form.email"  type="email" placeholder="邮箱">
                        <template #prefix>
                            <el-icon><Message /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row :gutter="10" style="display:flow; width:100%;">
                        <el-col :span="17" style="display: flex">
                            <el-input v-model="form.code" :maxlength="6" type="text" placeholder="验证码">
                                <template #prefix>
                                    <el-icon><EditPen /></el-icon>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="6" style="margin-top: 10px;">
                            <el-button type="success" @click="validateEmail" 
                                       :disabled="!isEmailValid || coldTime > 0 || isLoading"
                                       :loading="isLoading">
                                {{ isLoading ? '发送中...' : (coldTime > 0 ? `请稍后${coldTime}秒` : '获取验证码') }}
                            </el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin-top: 0px">

        </div>
        <div style="margin-top: 30px">
            <el-button style="width: 200px" type="warning" @click="register">立即注册</el-button>
        </div>
        <div style="margin-top: 20px">
           <el-text style="align-items: center;" >已有账号？<el-link style="vertical-align: 0;" @click="router.push('/')">立即登录</el-link> </el-text>
        </div>

    </div>
</template>

<script setup>

import router from "@/router";
import {Lock, User, Message, EditPen} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {post} from "@/net";
import {ElMessage} from "element-plus";
import axios from 'axios';

const form = reactive(
    {
        username:'',
        password:'',
        password_repeat:'',
        email:'',
        code:''
    }
)
const validateUsername = (rule, value , callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符，只能是中文、英文')) 
  }else{
    callback()
  }
}


const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}
const rules = {
    username:[
        { validator:validateUsername, trigger: ['blur','change'] },
        { min: 2, max: 10, message: '用户名长度必须在2-10个字符之间', trigger: ['blur','change'] },
    ],
    password:[
        {required: true,message: '请输入密码',tigger: 'blur'},
        { min: 6, max: 16, message: '密码长度必须在6-16个字符之间', trigger: ['blur','change'] },
    ],
    password_repeat:[
        { validator:validatePassword, trigger: ['blur','change'] },
    ],
    email:[
        {required: true,message: '请输入邮件地址',tigger: 'blur'},
        {type:'email',message: '请输入合法的邮件地址',trigger: ['blur', 'change'],}
    ],
    code:[
        {required: true,message: '请输入获取的验证码',tigger: 'blur'},
    ]
}
const formRef = ref() //获取表单信息
const isEmailValid = ref(false) // 修正：应该是 false 而不是 'false'
const isSendEmail = ref(false)
const coldTime = ref(0)
const countdownTimer = ref(null) // 新增：用于存储定时器引用
const isLoading = ref(false) // 新增：加载状态

const validateEmail = () => {
    if (isLoading.value) return; // 防止重复点击
    
    isLoading.value = true; // 开始加载
    
    // 使用URLSearchParams发送form-data格式
    const params = new URLSearchParams();
    params.append('email', form.email);
    
    axios.post('/api/auth/valid-register-email', params, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({ data }) => {
        if (data.success) {
            ElMessage.success(data.message || '验证码已发送，请注意查收');
            coldTime.value = 60;
            
            // 清除之前的定时器
            if (countdownTimer.value) {
                clearInterval(countdownTimer.value);
            }
            
            // 创建新的定时器
            countdownTimer.value = setInterval(() => {
                coldTime.value--;
                if (coldTime.value <= 0) {
                    clearInterval(countdownTimer.value);
                    countdownTimer.value = null;
                }
            }, 1000);
        } else {
            ElMessage.warning(data.message || '发送失败，请重试');
        }
    }).catch((error) => {
        console.error('验证码发送失败:', error);
        ElMessage.error('网络错误，请检查网络连接后重试');
    }).finally(() => {
        isLoading.value = false; // 结束加载
    });
}
const onValidate = (prop,isValid)=>{
    if(prop==='email')
        isEmailValid.value = isValid
}
const register = () => {
    formRef.value.validate((isValid)=>{
        if(isValid){
            // 使用URLSearchParams发送form-data格式
            const params = new URLSearchParams();
            params.append('username', form.username);
            params.append('password', form.password);
            params.append('email', form.email);
            params.append('code', form.code);
            
            axios.post('/api/auth/register', params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                withCredentials: true
            }).then(({ data }) => {
                if (data.success) {
                    ElMessage.success(data.message);
                    router.push("/");
                } else {
                    ElMessage.warning(data.message);
                }
            }).catch(() => {
                ElMessage.error('发生了一些错误，请联系管理员');
            });
        }else{
            ElMessage.warning('请完整填写上述表单注册内容')
        }
    })
}
</script>



<style scoped>

</style>