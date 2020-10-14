import Mock from "mockjs"
 
const user = {
  "success": true,
  "data": {
    "name": Mock.Random.cname(),
    "headPicCode":"",
    "headPicUrl":"../../assets/user.jpg",
    "birthdate": Mock.Random.date('yyyy/MM/dd'),
    "nation":"民族",
    "politicalLandscape":"中共党员",
    "graduateInstitutions":"广东工业大学",
    "policeCode":"PY000001",
    "quasiDrivingType":"A1",
    "speciality":"特长",
    "exserviceman":"是否退役军人",
    "permanentResidenceAddress": Mock.Random.county(true),
    "familyAddress": Mock.Random.county(true),
    "sex":"男",
    "age":"25",
    "nativePlace":"广东广州",
    "education":"本科",
    "major":"计算机",
    "maritalStatus":"已婚",
    "identityCard": Mock.Random.id(),
    "phone":"13631476874",
    "personnelType":"在职",
    "authorizedStrengthType":"共建辅警",
    "placeOfWork":"医务",
    "jobGrade":"三级辅警",
    "treatmentGrade":"三级",
    "enrollWay":"公开招聘",
    "beginWorkTime": Mock.Random.date('yyyy/MM/dd'),
    "effectiveDateOfTheContract": Mock.Random.date('yyyy/MM/dd'),
    "retirementDate": Mock.Random.date('yyyy/MM/dd'),
    "dimissionType":"离职类别1",
    "workUnitCode":["35f68180f5b446b396aa66ab3b8c8195"],
    "organizationUnitCode":"人事组",
    "jobCategory":"人事组",
    "duty":"医务",
    "socialSecurityNumber":"6712752298761933",
    "beginPoliceWorkTime": Mock.Random.date('yyyy/MM/dd'),
    "contractExpirationDate": Mock.Random.date('yyyy/MM/dd'),
    "dimissionDate": Mock.Random.date('yyyy/MM/dd'),
    "dimissionReason":"离职原因",
    "personalExperience":[
      {
        "timeStart": Mock.Random.date('yyyy/MM/dd'),
        "timeEnd": Mock.Random.date('yyyy/MM/dd'),
        "unit":"番禺公安局",
        "department":"人事组",
        "duty":"医务"
      }
    ],
    "familyMember":[
      {
        "name": Mock.Random.cname(),
        "relation":"父子",
        "company":"人事组",
        "duty":"医务",
        "identityCard": Mock.Random.id(),
        "phone":"13687697684",
        "politicalLandscapeCode":"中共党员"
      }
    ],
    "award":[
      {
        "name":"积极分子",
        "time": Mock.Random.date('yyyy/MM/dd'),
        "reason":"工作积极",
        "company":"人数组"
      }
    ],
    "assessment":[
      {
        "time": Mock.Random.date('yyyy/MM/dd'),
        "grade":"优秀",
        "reamrk":"表现有益"
      }
    ]
  }
}
 
export {user}