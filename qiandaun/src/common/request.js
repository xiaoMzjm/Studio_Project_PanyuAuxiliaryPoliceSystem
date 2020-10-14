/**
 * 统一请求接口
 */
import axios from 'axios'
import Config from '../config/common.json'

// mock
// import MockAdapter from 'axios-mock-adapter'
// import {res} from '../mock/user'
// import {menu} from '../mock/menu'
// import {unitList} from '../mock/unit'
// import {login} from '../mock/login'
// import {user} from '../mock/userdetail'
// import {nation,politicallandscape,drivingtype,exserviceman,sex,education,maritalstatus,
//   personneltype,authorizedstrengthype,placeofwork,jobgrade,treatmentgrade,enrollway,dimssiontype,
//   jobcategory,specialpeople} from '../mock/enums'
// import {listemployeecard} from '../mock/expiration'
// import {rolelist, listauthority} from '../mock/role'
// import {list} from '../mock/wages'
// import {statistics} from '../mock/epidemic'

// var mock = new MockAdapter(axios);
// mock.onPost("/user/getauthority").reply(200, menu);
// mock.onPost("/company/listall").reply(200, unitList);
// mock.onPost("/user/login").reply(200, login);
// mock.onPost("/user/detail").reply(200, user);
// mock.onPost("/user/pagelist").reply(200, res);
// mock.onPost("/user/enum/nation").reply(200, nation);
// mock.onPost("/user/enum/politicallandscape").reply(200, politicallandscape);
// mock.onPost("/user/enum/drivingtype").reply(200, drivingtype);
// mock.onPost("/user/enum/exserviceman").reply(200, exserviceman);
// mock.onPost("/user/enum/sex").reply(200, sex);
// mock.onPost("/user/enum/education").reply(200, education);
// mock.onPost("/user/enum/maritalstatus").reply(200, maritalstatus);
// mock.onPost("/user/enum/personneltype").reply(200, personneltype);
// mock.onPost("/user/enum/authorizedstrengthype").reply(200, authorizedstrengthype);
// mock.onPost("/user/enum/placeofwork").reply(200, placeofwork);
// mock.onPost("/user/enum/jobgrade").reply(200, jobgrade);
// mock.onPost("/user/enum/treatmentgrade").reply(200, treatmentgrade);
// mock.onPost("/user/enum/enrollway").reply(200, enrollway);
// mock.onPost("/user/enum/dimssiontype").reply(200, dimssiontype);
// mock.onPost("/user/enum/jobcategory").reply(200, jobcategory);
// mock.onPost("/user/enum/specialpeople").reply(200, specialpeople);
// mock.onPost("/expire/listemployeecard").reply(200, listemployeecard);
// mock.onPost("/expire/createemployeecard").reply(200, listemployeecard);
// mock.onPost("/role/listall").reply(200, rolelist);
// mock.onPost("/role/listauthority").reply(200, listauthority);
// mock.onPost("/wages/list").reply(200, list);
// mock.onPost("/epidemic/statistics/select").reply(200, statistics);

/**
 * 封装的请求
 */
const request = axios.create({
  baseURL: Config.server,
  timeout: 600000,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true,
  transformResponse: [function (data) {
    // 对 data 进行任意转换处理
    // console.log('transformResponse', data)
    try {
      data = JSON.parse(data)
    } catch (error) {
      console.log(error)
    }
    if (!data || data.status) {
      // 后台错误
      // console.log(data.status, data.error, data.message)
      let status = data ? data.status : 'return undefined'
      data = {
        success: false,
        message: '系统错误 [' + status + ']'
      }
    } else {
      // 登录态无效 TOKEN_NULL 
      if (!data.success && data.errorCode && data.errorCode.includes('TOKEN_')) {
        window.location.href = '/login'
      }
    }
    return data;
  }],
})

// 添加请求拦截器
request.interceptors.request.use(function (config) {
  console.log(1111, config)
  return config;
}, (error) => {
  console.log(222, error)
  // return Promise.reject(error);
});


export default request