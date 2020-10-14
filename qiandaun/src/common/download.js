/**
 * 下载文件
 */
import axios from 'axios'
import Config from '../config/common.json'

const download = axios.create({
  baseURL: Config.server,
  timeout: 600000,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true,
  responseType: 'blob',
  transformResponse: [function (data) {
    // 对 data 进行任意转换处理
    // console.log('transformResponse', data)
    // return data;
    if (!data) {
      return
    }
    let url = window.URL.createObjectURL(new Blob([data]))
    let link = document.createElement('a')
    link.style.display = 'none'
    link.href = url
    link.setAttribute('download', '人员导出.xlsx')
    document.body.appendChild(link)
    link.click()
  }],
})

// 添加请求拦截器
download.interceptors.request.use(function (config) {
  // console.log(1111, config)
  return config;
}, (error) => {
  // console.log(222, error)
  return Promise.reject(error);
});


export default download