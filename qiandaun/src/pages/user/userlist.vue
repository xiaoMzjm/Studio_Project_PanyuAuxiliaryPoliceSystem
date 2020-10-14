<template>
  <div class="h100">
    <el-row :gutter="20" class="h100">
      <el-col :span="6" class="h100">
        <div class="tree">
          <h3 class="unit-title">单位列表</h3>
          <div class="user-unit">
            <Tree :data="showUnitList" @check="checkUnit" :selectAll="false" :showCheckbox="true"></Tree>
          </div>
        </div>
        <div class="user-table">
          <Table 
            class="pointer"
            :header="headerData" 
            :data="userList" 
            :showPage="false" size="mini"
            @check="checkUser"
          ></Table>
        </div>
        <div class="operate" v-loading="operatLoading">
          <el-row :gutter="20">
            <el-col :span="12"><el-input size="mini" v-model="serachData.name" placeholder="请输入姓名" @input="changeName"></el-input></el-col>
            <el-col :span="12"><el-button size="mini" @click="dialogData.show=true">高级查询</el-button></el-col>
            <el-col :span="12"><el-button size="mini" @click="noUser = false;addUser()">添加人员</el-button></el-col>
            <el-col :span="12">
              <a :href="downloadimportdemo" target="_blank"><el-button size="mini">下载导入模板</el-button></a>
            </el-col>
            <el-col :span="12">
              <el-upload
                class="avatar-uploader"
                :action="importuser"
                :with-credentials="true"
                :show-file-list="false"
                :on-progress="uploadProgress"
                :on-success="uploadCb">
                <el-button size="mini">导入</el-button>
              </el-upload>
            </el-col>
            <el-col :span="12"><el-button size="mini" @click="importImageData.show = true">导入相片</el-button></el-col>
            <el-col :span="12"><el-button size="mini" @click="downloadUser">导出所选人员</el-button></el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="18" class="h100" v-loading="loading">
        <div class="user-detail">
          <User v-if="!noUser" :data="userData"></User>
          <div v-else class="no-user">暂无选择人员</div>
        </div>
        <div class="user-operate">
          <template v-if="userData.identityCard">
            <el-button type="danger" size="mini" @click="delUser" v-if="UserListDeleteUser">删除</el-button>
            <a :href="exportuser + userData.code" class="button-a" target="_blank"><el-button size="mini">导出个人信息</el-button></a>
            <a :href="exportincomecertificate + userData.code" class="button-a" target="_blank"><el-button size="mini">导出收入证明</el-button></a>
            <a :href="exportonthejobcertificate + userData.code" class="button-a" target="_blank"><el-button size="mini">导出在职证明</el-button></a>
          </template>
          <el-button type="primary" size="mini" @click="saveUser">保存</el-button>
        </div>
      </el-col>
    </el-row>
    <!-- 超级查询 -->
    <SuperSearch :data="dialogData" @success="superPagelist"></SuperSearch>
    <!-- 导入相片 -->
    <ImportImage :data="importImageData" 
    @uploadProgress="operatLoading = true"
    @uploadCb="operatLoading = false"
    @success="getUserData(currCode)"></ImportImage>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Tree from '../../components/tree.vue'
import Table from '../../components/table.vue'
import User from '../../components/user.vue'
// import Dialog from '../../components/dialog/index.vue'
import SuperSearch from '../../components/dialog/superSearch.vue'
import ImportImage from '../../components/dialog/importImage.vue'
import userConfig from '../../config/user.json'
import Config from '../../config/common.json'
import { findRootUnit } from '../../common/util.js'

export default {
  name: 'Unit',
  components: {
    Tree,
    Table,
    User,
    SuperSearch,
    ImportImage
    // Dialog
  },
  computed: {
    ...mapState({
      unitList: 'unit',
      user: 'user'
    }),
    funMenu() {//UserListSelectAllCompany "UserListDeleteUser"
      console.log('menu', this.$store.state.menu)
      let menu = []
      this.$store.state.menu.forEach(item => {
        if (item.code == 'UserManager') {
          item.children.forEach(citem => {
            if (citem.code == 'UserList') {
              menu = citem.children || []
            }
          }) 
        }
      })
      menu.forEach(item => {
        if (item.code == 'UserListDeleteUser') this.UserListDeleteUser = true
      })
      return menu
    }
  },
  data() {
    return {
      loading: false,
      operatLoading: false,
      headerData: [
        { key: 'name', label: '姓名', width: 70 },
        { key: 'companyName', label: '工作单位' },
      ],
      // 查询条件
      serachData: {
        name: '',
        companyCodeList: []
      },
      noUser: true,
      userList: [],
      userData: {},
      downloadimportdemo: Config.server + '/user/downloadimportdemo',
      exportuser: Config.server + '/user/exportuser?userCode=',
      exportincomecertificate: Config.server + '/user/exportincomecertificate?userCode=',
      exportonthejobcertificate: Config.server + '/user/exportonthejobcertificate?userCode=',
      importuser: Config.server + '/user/importuser',
      importimage: Config.server + '/user/importimage',
      dialogData: {
        title: '高级查询',
        show: false
      },
      importImageData: {
        show: false
      },
      currCode: null,
      showUnitList: [],
      UserListDeleteUser: false
    }
  },
  methods: {
    ...mapActions([
      'unit', 'nation', 'getAll'
    ]),
    // 单位变化时
    checkUnit(list) {
      this.serachData.companyCodeList = list;
      this.getUserList()
    },
    // 姓名变化时
    changeName() {
      this.getUserList()
    },
    // 获取人员列表
    getUserList() {
      let companyCodeList = []
      this.showUnitList.forEach(item => {
        companyCodeList.push(item.code)
      })
      let param = {
        companyCodeList: this.serachData.companyCodeList.length ? this.serachData.companyCodeList : companyCodeList,
        name: this.serachData.name
      }
      this.request.post('/user/pagelist', param).then(res => {
        console.log('/user/pagelist', res)
        if (res.status == 200 & res.data.success) {
          this.userList = res.data.data
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    // 选中姓名变化时
    checkUser(opt) {
      this.noUser = false;
      this.currCode = opt.row.code
      this.getUserData(opt.row.code)
    },
    // 将组织转成数组
    unit2Array(code) {
      let arr = []
      this.unitList.forEach(element => {
        if (element.code == code) {
          arr.push(code)
        } else {
          element.children.forEach(item => {
            if (item.code == code) {
              arr.push(element.code)
              arr.push(item.code)
            }
          })
        }
      });
      return arr
    },
    // 获取code的节点
    getUnitChildren(code) {
      let arr = []
      this.unitList.forEach(element => {
        if (element.code == code) {
          arr.push(code)
          element.children.forEach(item => {
            arr.push(item.code)
          })
        } else {
          element.children.forEach(item => {
            if (item.code == code) {
              arr.push(code)
            }
          })
        }
      })
      return arr
    },
    // 获取人员详情
    getUserData(code) {
      this.loading = true
      this.request.post('/user/detail', {
        code: code
      }).then(res => {
        this.loading = false
        if (res.status == 200 & res.data.success) {
          for(let item in this.userData) {
            if (userConfig[item] && userConfig[item].type == 'enums') {
              this.userData[item] = this.unit2Array(res.data.data[item])
              console.log(res.data.data[item], this.userData[item])
            } else {
              this.userData[item] = res.data.data[item]
            }
          }
          this.userData.code = res.data.data.code
          this.userData.headPicUrl = Config.server + res.data.data.headPicUrl
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    // 添加人员
    addUser() {
      let data = {}
      for (let item in userConfig) {
        if (userConfig[item].type == 'enums' || userConfig[item].type == 'table') {
          data[item] = []
        } else {
          data[item] = ''
        }
      }
      this.userData = data
    },
    // 保存人员
    saveUser() {
      let reqData = JSON.parse(JSON.stringify(this.userData))
      let url = reqData.code ? '/user/update' : '/user/add'
      reqData.workUnit = reqData.workUnit[reqData.workUnit.length-1]
      reqData.organizationUnit = reqData.organizationUnit[reqData.organizationUnit.length-1]
      this.request.post(url, reqData).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: reqData.code ? '更新人员成功' : '添加人员成功',
            type: 'success'
          });
          this.getUserList()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    // 导入进度
    uploadProgress(event) {
      console.log(event)
      this.$message({
        showClose: true,
        message: '上传中……',
        type: 'warning',
        duration: 0
      });
      this.operatLoading = true
    },
    // 导入成功回调
    uploadCb(res) {
      this.$message.closeAll()
      this.operatLoading = false
      console.log(res)
      if (res.success) {
        this.$message({
          showClose: true,
          message: '导入成功',
          type: 'success'
        });
        this.getUserList()
      } else {
        this.$message({
          showClose: true,
          message: res.message,
          type: 'error'
        }); 
      }
    },
    // 高级查询
    superPagelist(resData) {
      let reqData = JSON.parse(JSON.stringify(resData))
      for(let item in this.userData) {
        if (userConfig[item]) {
          if (userConfig[item].type == 'date' || userConfig[item].type == 'range') {
            reqData[item + 'Begin'] = reqData[item] ? reqData[item][0] : ''
            reqData[item + 'End'] = reqData[item] ? reqData[item][1] : ''
            delete reqData[item]
          } else if (userConfig[item].type == 'enum') {
            reqData[item + 'List'] = reqData[item] || []
            delete reqData[item]
          } else if( userConfig[item].type == 'enums') {
            reqData[item + 'List'] = this.getUnitChildren(reqData[item][reqData[item].length-1]) || []
            delete reqData[item]
          } else {
            // this.userData[item] = res.data.data[item]
          }
        }
      }
      console.log(reqData)
      this.request.post('/user/superpagelist', reqData).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '查询人员成功',
            type: 'success'
          });
          this.userList = res.data.data || []
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    // 删除人员
    delUser() {
      this.$confirm('确认删除 ' + this.userData.name + ' ?').then(res => {
        if (res == 'confirm') {
          this.request.post('/user/delete', {
          userCode: this.userData.code
        }).then(res => {
          if (res.status == 200 & res.data.success) {
            this.$message({
              showClose: true,
              message: '删除人员成功',
              type: 'success'
            });
            this.getUserList()
            this.noUser = true
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: 'error'
            });
          }
        })
        }
      }).catch(err => {
        console.log(123,err)
      });
    },
    // 下载人员
    downloadUser() {
      if (!this.userList.length) {
        this.$message({
          showClose: true,
          message: '所选成员为空',
          type: 'error'
        });
        return
      }
      const reqData = {
        userCodes: []
      }
      this.userList.forEach(item => {
        reqData.userCodes.push(item.code)
      })
      this.$message({
        showClose: true,
        message: '导出中……',
        type: 'warning',
        duration: 0
      });
      this.operatLoading = true
      this.download.post('/user/exportselectuser', reqData).then(res => {
        this.$message.closeAll()
        this.operatLoading = false
        if (res.status == 200) {
          this.$message({
            showClose: true,
            message: '导出所选人员成功',
            type: 'success'
          });
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    // 设置单位列表
    setUnit() {
      let list = []
      // 找到父单位
      // console.log('父单位', this.user.organizationUnit, this.unitList, findRootUnit(this.user.organizationUnit, this.unitList))
      let oFather = findRootUnit(this.user.organizationUnit, this.unitList)
      if (oFather) {
        list = [oFather]
        if (this.user.organizationUnit != this.user.workUnit) {
          let wFather = findRootUnit(this.user.workUnit, this.unitList)
          list.push(wFather)
        }
      }
      // 查询所有单位
      let UserListSelectAllCompany = false
      this.funMenu.forEach(item => {
        if (item.code == 'UserListSelectAllCompany') UserListSelectAllCompany = true
      })
      if (!UserListSelectAllCompany) {
        this.showUnitList = list
        console.log('this.searchUnitList[0].code', this.showUnitList)
      } else {
        this.showUnitList = this.unitList
      }
      // this.showUnitList = list
    }
  },
  mounted() {
    this.unit()
    this.getAll()
    // this.getUserList()
    this.addUser()
  },
  watch: {
    funMenu(val) {
      if (val) this.setUnit()
      console.log('funMenu',val)
    },
    unitList(val) {
      if (val) this.setUnit()
      console.log('unitList',val)
    },
    user(val) {
      if (val) this.setUnit()
      console.log('user',val)
    }
  }
}
</script>

<style scoped>
.unit-title {
  margin: 10px 0 0 10px;
  font-size: 0.9em;
}
.tree {
  border: 2px #EBEEF5 solid;
  height: 35%;
}
.tree>div {
  height: calc(100% - 50px);
  overflow-y: scroll;
  padding-bottom: 20px; 
}
.user-table {
  height: 35%;
  /* overflow-y: scroll; */
  /* padding: 10px; */
  /* border: 2px #EBEEF5 solid; */
}
.operate {
  padding: 10px;
  border: 2px #EBEEF5 solid;
  height: calc(30% - 60px);
  overflow-y: scroll;
}
.operate .el-button {
  width: 100%;
  margin-bottom: 10px;
}
.user-detail {
  height: calc(100% - 80px);
  /* overflow-y: scroll; */
  padding: 10px;
  border: 2px #EBEEF5 solid;
}
.user-operate {
  margin-top: 20px;
  text-align: right;
}
.no-user {
  display:flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.el-button+.button-a, .button-a+.el-button, .button-a+.button-a {
  margin-left: 10px;
}
.h100 {
  height: 100%;
}
.pointer {
  cursor: pointer
}
</style>
