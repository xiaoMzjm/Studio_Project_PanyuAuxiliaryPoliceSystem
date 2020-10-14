import Vue from 'vue/dist/vue.esm.js'
import VueRouter from 'vue-router'

import login from '../pages/login.vue'
import error from '../pages/error.vue'
import unit from '../pages/unit/unitlist.vue'
import user from '../pages/user/userlist.vue'
import employeecard from '../pages/expiration/employeecard.vue'
import chartlist from '../pages/chart/chartlist.vue'
import authority from '../pages/system/authority.vue'
import role from '../pages/system/role.vue'
import role_user_bind from '../pages/system/role_user_bind.vue'
import wages from '../pages/wages/manage.vue'
import epidemic_list from '../pages/epidemic/list.vue'
import epidemic_collect from '../pages/epidemic/collect.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/UserManager/UserList' },
  { path: '/error', component: error }, // 404
  { path: '/login', component: login }, // 登录
  { path: '/CompanyManager/CompanyList', component: unit }, // 单位管理
  { path: '/UserManager/UserList', component: user }, // 人员管理
  { path: '/ChartManger/ChartList', component: chartlist }, // 图表管理
  { path: '/SystemManager/AuthorityManager', component: authority }, // 权限管理
  { path: '/SystemManager/RoleManager', component: role }, // 角色管理
  { path: '/SystemManager/RoleUserBindManager', component: role_user_bind }, // 角色权限绑定
  { path: '/EpidemicManager/EpidemicListManager', component: epidemic_list }, // 防疫登记和查询
  { path: '/EpidemicManager/EpidemicCollectManager', component: epidemic_collect }, // 防疫汇总
  // 报表
  { path: '/ExpireManager/ExpireEmployeeCard', component: employeecard }, // 工作证到期提醒
  { path: '/ExpireManager/ExpireContract', component: employeecard }, // 合同证到期提醒
  { path: '/ExpireManager/ExpireRetire', component: employeecard }, // 退休提醒
  { path: '/BaseDataStatistics/YearStatistics', component: employeecard }, // 年度报表
  { path: '/BaseDataStatistics/MonthStatistics', component: employeecard }, // 月度报表
  { path: '/WagesManager/WagesDetail', component: wages }, // 月度报表
]

const router = new VueRouter({
  // mode: 'hash',
  mode: 'history',
  routes
})

export default router
