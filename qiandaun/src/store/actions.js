import request from '../common/request'

let actions = {
  getMenu (context, opt) {
    request.post('/user/getauthority', {}).then(res => {
      context.commit('menu', res.data.data)
      if (!res.data.data.length && opt.this.$route.path != '/error') opt.this.$router.push('/error')
      console.log('menu', res.data.data, opt, opt.this.$route)
    })
  },
  getUser (context, data) {
    console.log(context)
    request.post('/user/detail', {
      code: data
    }).then(res => {
      console.log(res.data.data)
      context.commit('user', res.data.data)
    })
  },
  unit (context) {
    return new Promise((resolve, reject) => {
      request.post('/company/listall', {}).then(res => {
        context.commit('unit', res.data.data)
        resolve()
      }).catch(err => {
        reject(err)
      })
    })
  },
  // 获取所有枚举信息
  getAll (context) {
    request.post('/user/enum/all', {}).then(res => {
      let data = res.data.data
      context.commit('nation', data.nation)
      context.commit('politicalLandscape', data.politicallandscape)
      context.commit('quasiDrivingType', data.drivingtype)
      context.commit('exserviceman', data.exserviceman)
      context.commit('sex', data.sex)
      context.commit('education', data.education)
      context.commit('personnelType', data.personneltype)
      context.commit('authorizedStrengthType', data.authorizedstrengthype)
      context.commit('placeOfWork', data.placeofwork)
      context.commit('jobGrade', data.jobgrade)
      context.commit('treatmentGrade', data.treatmentgrade)
      context.commit('enrollWay', data.enrollway)
      context.commit('dimissionType', data.dimissiontype)
      context.commit('jobCategory', data.jobcategory)
      context.commit('maritalStatus', data.maritalstatus)
      context.commit('specialPeople', data.specialpeople)
      context.commit('nation', data.nation)
      context.commit('dueContract', data.duecontract)
    })
  },
  // 获取角色列表
  getRole (context) {
    request.post('/role/listall', {}).then(res => {
      console.log(res.data.data)
      context.commit('role', res.data.data)
    })
  }
}

export default actions