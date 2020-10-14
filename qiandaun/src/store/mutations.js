import menuConfig from '../config/menu.json'

let mutations = {
  setNickname (state, data) { 
    console.log(123,state)
    state.nickname = data 
  },
  user (state, data) { state.user = data },
  menu (state, data) {
    //  对齐前端url
    data.forEach(menu => {
      menuConfig.forEach(cmenu => {
        if (cmenu.code == menu.code) {
          menu.url = '/' + menu.code
          menu.icon = cmenu.icon
          menu.children && menu.children.forEach(children => {
            cmenu.children && cmenu.children.forEach(cchildren => {
              if (cchildren.code == children.code) {
                children.url = '/' + menu.code + '/' + children.code
                children.icon = cchildren.icon
              }
            })
          })
        }
      })
      
    });
    state.menu = data
  },
  unit (state, data) {
    state.unit = data
  },
  nation (state, data) { state.nation = data },
  politicalLandscape (state, data) { state.politicalLandscape = data },
  quasiDrivingType (state, data) { state.quasiDrivingType = data },
  exserviceman (state, data) { state.exserviceman = data },
  sex (state, data) { state.sex = data },
  education (state, data) { state.education = data },
  personnelType (state, data) { state.personnelType = data },
  authorizedStrengthType (state, data) { state.authorizedStrengthType = data },
  placeOfWork (state, data) { state.placeOfWork = data },
  jobGrade (state, data) { state.jobGrade = data },
  treatmentGrade (state, data) { state.treatmentGrade = data },
  enrollWay (state, data) { state.enrollWay = data },
  dimissionType (state, data) { state.dimissionType = data },
  jobCategory (state, data) { state.jobCategory = data },
  maritalStatus (state, data) { state.maritalStatus = data },
  specialPeople (state, data) { state.specialPeople = data },
  dueContract (state, data) { state.dueContract = data },
  role (state, data) { state.role = data },
}

export default mutations