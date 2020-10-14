import Mock from "mockjs"
 
const users = []
for (let i=0; i<10; i++){
  users.push(Mock.mock({
    user_code: Mock.Random.integer(60, 100),
    name: Mock.Random.cname(),
    companyName: '人事组',
    roles: [{
      code: 0,
      name:"管理员" 
    },{
      code: 1,
      name:"用户" 
    }]
  }))
}

let res = {
  "data": users,
  "success": true
}
 
export {res}