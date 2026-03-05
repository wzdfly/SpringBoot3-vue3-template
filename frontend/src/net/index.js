import axios from "axios";
import 'element-plus/es/components/message/style/css';
import {ElMessage} from "element-plus";

// 设置axios默认配置
axios.defaults.baseURL = 'http://localhost:8080';

const defaultError = () => ElMessage.error('发生了一些错误，请联系管理员');
const defaultFailure = (message) => ElMessage.warning(message);

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        withCredentials: true
    }).then(({ data }) => {
        console.log('完整的后端响应：', data);
        
        if (data.success) {
            console.log('成功响应，data.message：', data.message);
            // 修改：传递 data.message 而不是 data.data
            success(data.message, data.status);
        } else {
            console.log('失败响应，data：', data);
            // 处理失败情况
            const errorMessage = (typeof data.message === 'object' && data.message.message) 
                ? data.message.message 
                : data.message;
            failure(errorMessage, data.status);
        }
    }).catch(error);
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        withCredentials: true
    }).then(({ data }) => {
        if (data.success) {
            success(data.message, data.status);
        } else {
            const errorMessage = (typeof data.message === 'object' && data.message.message) 
                ? data.message.message 
                : data.message;
            failure(errorMessage, data.status);
        }
    }).catch(error);
}

export { get, post };
