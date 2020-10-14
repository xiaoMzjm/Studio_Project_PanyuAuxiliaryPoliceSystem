import Vue from 'vue/dist/vue.esm.js'
import Vuex from 'vuex'
import mutations from './mutations'
import actions from './actions'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    nickname: '',
    user: {}, // 个人信息
    menu: [], // 菜单
    unit: [], // 单位
    nation: [], // 名族
    politicalLandscape: [], // 政治身份
    quasiDrivingType: [], // 准驾车型
    exserviceman: [], // 是否退役军人
    sex: [], // 性别
    education: [], // 学历
    personnelType: [], // 人员类型
    authorizedStrengthType: [], // 编制类型
    placeOfWork: [], // 岗位
    jobGrade: [], // 职级
    treatmentGrade: [], // 待遇级别
    enrollWay: [], // 招录方式
    dimissionType: [], // 离职类别
    jobCategory: [], // 岗位类别
    maritalStatus: [], // 婚姻状况
    specialPeople: [], // 特殊人员
    dueContract: [], // 到期合同
    role: [], // 角色
  },
  mutations: mutations,
  actions: actions
})

export default store