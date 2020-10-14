/**
 * 上传图片
 */
import axios from 'axios'
import Config from '../config/common.json'

const upload = axios.create({
  baseURL: Config.server + '/resource/uploadpic',
  timeout: 600000,
  headers: {
    'Content-Type': 'multipart/form-data'
  },
  transformResponse: [function (data) {
    // 对 data 进行任意转换处理
    // console.log('transformResponse', data)
    return data;
  }],
})

// 添加请求拦截器
upload.interceptors.request.use(function (config) {
  // console.log(1111, config)
  return config;
}, (error) => {
  // console.log(222, error)
  return Promise.reject(error);
});


export default upload