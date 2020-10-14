// import Mock from "mockjs"
 
const rolelist = {
  "success": true,
  "data": [{
    code: 0,
    name:"管理员" 
  },{
    code: 1,
    name:"用户" 
  }]
}

const listauthority = {
  "data": [
    {
      "children": [
        {
          "children": [],
          "code": "CompanyList",
          "fatherCode": "CompanyManager",
          "name": "单位列表",
          "type": "page",
          "rows": [0,1],
        }
      ],
      "rows": [0,1],
      "code": "CompanyManager",
      "name": "单位管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "UserList",
          "fatherCode": "UserManager",
          "name": "人员列表",
          "type": "page"
        }
      ],
      "code": "UserManager",
      "name": "人员管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "YearStatistics",
          "fatherCode": "BaseDataStatistics",
          "name": "年度报表",
          "type": "page"
        },
        {
          "children": [],
          "code": "MonthStatistics",
          "fatherCode": "BaseDataStatistics",
          "name": "月度报表",
          "type": "page"
        }
      ],
      "rows": [1],
      "code": "BaseDataStatistics",
      "name": "年月报表管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "ExpireEmployeeCard",
          "fatherCode": "ExpireManager",
          "name": "工作证到期提醒",
          "type": "page"
        },
        {
          "children": [],
          "code": "ExpireContract",
          "fatherCode": "ExpireManager",
          "name": "合同证到期提醒",
          "type": "page"
        },
        {
          "children": [],
          "code": "ExpireRetire",
          "fatherCode": "ExpireManager",
          "name": "退休提醒",
          "type": "page"
        }
      ],
      "rows": [0],
      "code": "ExpireManager",
      "name": "到期管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "WagesDetail",
          "fatherCode": "WagesManager",
          "name": "工资明细",
          "type": "page"
        },
        {
          "children": [],
          "code": "WagesReimbursement",
          "fatherCode": "WagesManager",
          "name": "工资报销封面",
          "type": "page"
        },
        {
          "children": [],
          "code": "WagesChange",
          "fatherCode": "WagesManager",
          "name": "工资变动",
          "type": "page"
        }
      ],
      "code": "WagesManager",
      "name": "工资管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "AssessmentList",
          "fatherCode": "AssessmentManager",
          "name": "年度考核列表",
          "type": "page"
        },
        {
          "children": [],
          "code": "AssessmentResult",
          "fatherCode": "AssessmentManager",
          "name": "年度考核结果登记",
          "type": "page"
        }
      ],
      "code": "AssessmentManager",
      "name": "年度考核管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "WorkShiftList",
          "fatherCode": "WorkShiftManger",
          "name": "轮岗人员列表",
          "type": "page"
        }
      ],
      "code": "WorkShiftManger",
      "name": "轮岗管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "AuthorityManager",
          "fatherCode": "SystemManager",
          "name": "权限管理",
          "type": "page"
        },
        {
          "children": [],
          "code": "RoleManager",
          "fatherCode": "SystemManager",
          "name": "角色管理",
          "type": "page"
        },
        {
          "children": [],
          "code": "RoleUserBindManager",
          "fatherCode": "SystemManager",
          "name": "角色绑定管理",
          "type": "page"
        }
      ],
      "code": "SystemManager",
      "name": "系统管理",
      "type": "page"
    },
    {
      "children": [
        {
          "children": [],
          "code": "ChartList",
          "fatherCode": "ChartManger",
          "name": "图表列表",
          "type": "page"
        }
      ],
      "code": "ChartManger",
      "name": "图表管理",
      "type": "page"
    }
  ],
  "success": true
}
 
export {rolelist, listauthority}