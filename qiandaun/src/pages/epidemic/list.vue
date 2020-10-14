<template>
  <div class="h100 epidemic-list">
    <el-tabs v-model="activeTab" @tab-click="handleClick">
      <el-tab-pane label="当前登记" name="current"></el-tab-pane>
      <el-tab-pane label="全部登记" name="history"></el-tab-pane>
    </el-tabs>
    <div>
      <!-- <el-row>
        <el-col :span="12"> -->
          <template v-if="activeTab == 'history'">
            <el-date-picker size="mini"
              format="yyyy/MM/dd" value-format="yyyy/MM/dd" start-placeholder="开始日期" end-placeholder="结束日期"
              v-model="serachData.date" type="daterange" placeholder="选择日期">
            </el-date-picker>
            <el-input size="mini" v-model="serachData.name" placeholder="请输入人员名称" style="width: initial;" class="ml10 mt10"></el-input>
            <el-cascader size="mini" v-model="serachData.unit" class="ml10" change-on-select
              :options="searchUnitList" :props="{ label: 'name', value: 'code', checkStrictly: true }" clearable>
            </el-cascader>
            <el-select size="mini" v-model="serachData.typeList" placeholder="请选择类型" class="ml10 mt10" multiple collapse-tags>
              <el-option v-for="item in enums.type" :key="item.value" :label="item.name" :value="item.code"></el-option>
            </el-select>
            <el-select size="mini" v-model="serachData.locationList" placeholder="请选择地点" class="ml10 mt10" multiple collapse-tags>
              <el-option v-for="item in enums.location" :key="item.value" :label="item.name" :value="item.code"></el-option>
            </el-select>
            <el-select size="mini" v-model="serachData.statusList" placeholder="请选择状态" class="ml10 mt10" multiple collapse-tags>
              <el-option v-for="item in enums.status" :key="item.value" :label="item.name" :value="item.code"></el-option>
            </el-select>
            <el-button size="mini" class="ml10 mt10" type="primary" @click="search">查询</el-button>
          </template>
          <el-button size="mini" class="ml10 mt10" type="primary" @click="editCode=false;dialogData.show=true;">登记</el-button>
        <!-- </el-col>
      </el-row> -->
    </div>
    <div class="detail h100" v-loading="loading">
      <Table 
        style="height: calc(100% - 160px);"
        :header="headerData" 
        :data="data" 
        size="mini"
        :operation="operation"
        @operateClick="operateClick"
        :showPage="false"
      ></Table>
    </div>
    <Dialog :data="dialogData" @success="dialogSuccess" @enumChange="enumChange"></Dialog>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import Table from '../../components/table.vue'
import Dialog from '../../components/dialog/index.vue'
import { findRootUnit } from '../../common/util.js'

export default {
  name: 'Employeecard',
  components: {
    Table,
    Dialog
  },
  computed: {
    unitList() {
      let unit = []
      this.$store.state.unit.forEach((element, index) => {
        unit.push({
          code: element.code,
          name: element.name
        })
        element.children.forEach(item => {
          unit[index].children = unit[index].children || []
          unit[index].children.push({
            code: item.code,
            name: item.name
          })
        })
      });
      return unit
    },
    user() {
      return this.$store.state.user
    },
    funMenu() {
      console.log('menu', this.$store.state.menu)
      let menu = []
      this.$store.state.menu.forEach(item => {
        if (item.code == 'EpidemicManager') {
          item.children.forEach(citem => {
            if (citem.code == 'EpidemicListManager') {
              menu = citem.children || []
            }
          }) 
        }
      })
      return menu
    }
  },
  data() {
    return {
      activeTab: 'current',
      serachData: {
        date: '',
        type: []
      },
      enums: {},
      loading: false,
      year: (new Date()).getFullYear().toString(),
      headerData: [
        { key: 'companyName', label: '单位' },
        { key: 'gmtCreateStr', label: '录入时间' },
        { key: 'typeName', label: '类型' },
        { key: 'locationName', label: '地点' },
        { key: 'detailLocation', label: '详细地址' },
        { key: 'userName', label: '姓名' },
        { key: 'beginTime', label: '开始时间' },
        { key: 'endTime', label: '结束时间' },
        { key: 'detail', label: '备注' },
        { key: 'leaderName', label: '审批领导' },
        { key: 'statusName', label: '状态' }
      ],
      operation: [
        { title: '确认', type: 'sure' },
        { title: '修改', type: 'edit' },
        { title: '删除', type: 'del' }
      ],
      data: [],
      dialogData: {
        title: '登记',
        show: false,
        form: [
          { label: '单位', value: '', placeholder: '请输入', key: 'unit', type: 'enum', list: [] },
          { label: '类型', value: '', placeholder: '请输入', type: 'enum', list: [] },
          { label: '地点', value: '', placeholder: '请输入', type: 'enum', list: [] },
          { label: '详细地址', value: '', placeholder: '请输入详细地址' },
          { label: '人员', value: '', placeholder: '请输入', type: 'enum', list: [], multiple: true },
          { label: '起始日期', value: '', placeholder: '请输入', type: 'date' },
          { label: '结束日期', value: '', placeholder: '请输入', type: 'date' },
          { label: '备注', value: '', placeholder: '请输入', type: 'textarea' },
          { label: '审批领导', value: '', placeholder: '请输入', type: 'enum', list: [] },
        ]
      },
      fatherUnitList: {},
      editCode: null,
      searchUnitList: [],
      EpidemicListManagerSuperUpdate: false
    }
  },
  methods: {
    ...mapActions([
      'unit'
    ]),
    operateClick(opt) {
      console.log(opt)
      switch(opt.type) {
        case 'sure': {
          this.$confirm('确认登记?').then(res => {
            if (res == 'confirm') this.sure(this.data[opt.index].code)
          }).catch(err => {
            console.log(err)
          });
          break
        }
        case 'edit': {
          this.editCode = this.data[opt.index].code
          this.dialogData.form[0].value = this.data[opt.index].companyCode;
          this.dialogData.form[1].value = this.data[opt.index].type
          this.dialogData.form[2].value = this.data[opt.index].location
          this.dialogData.form[3].value = this.data[opt.index].detailLocation
          this.dialogData.form[4].value = this.data[opt.index].userCode
          this.dialogData.form[5].value = this.data[opt.index].beginTime
          this.dialogData.form[6].value = this.data[opt.index].endTime
          this.dialogData.form[7].value = this.data[opt.index].detail
          this.dialogData.form[8].value = this.data[opt.index].leaderCode
          this.dialogData.show = true
          this.enumChange({ key: 'unit', val: this.dialogData.form[0].value })
          break
        }
        case 'del': {
          this.$confirm('确认删除?').then(res => {
            if (res == 'confirm') this.del(this.data[opt.index].code)
          }).catch(err => {
            console.log(err)
          });
          break
        }
      }
    },
    // 查询
    search() {
      console.log('menu', this.funMenu)
      console.log(this.serachData)
      let param = {
        companyCodeList: this.serachData.unit ? [this.serachData.unit[this.serachData.unit.length - 1]] : [],
        typeList: this.serachData.typeList,
        locationList: this.serachData.locationList,
        statusList: this.serachData.statusList,
        userName: this.serachData.name,
        beginTime: this.serachData.date ? this.serachData.date[0] : null,
        endTime: this.serachData.date ? this.serachData.date[1] : null
      }
      this.loading = true
      let url = this.activeTab == 'current' ? '/epidemic/selectCurrent' : '/epidemic/select'
      this.request.post(url, param).then(res => {
        this.loading = false
        if (res.status == 200 & res.data.success) {
          this.data = res.data.data || []
          let enums = {
            type: {},
            location: {},
            status: {}
          }
          this.enums.type.forEach(item => { enums.type[item.code] = item.name })
          this.enums.location.forEach(item => { enums.location[item.code] = item.name })
          this.enums.status.forEach(item => { enums.status[item.code] = item.name })
          this.data.forEach(item => {
            item.typeName = enums.type[item.type]
            item.locationName = enums.location[item.location]
            item.statusName = enums.status[item.status]
            if (!this.EpidemicListManagerSuperUpdate && item.status == 3) {
              item.sure = false
              item.edit = false
              item.del = false
            }
            if (!this.EpidemicListManagerSuperUpdate && item.status == 2) {
              item.sure = false
            }
          })
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 操作角色，添加 or 修改
    operateRole() {
      const url = !this.editCode ? '/epidemic/add' : '/epidemic/update'
      let param = {
        code: this.editCode || null,
        companyCode: this.dialogData.form[0].value,
        type: this.dialogData.form[1].value,
        location: this.dialogData.form[2].value,
        detailLocation: this.dialogData.form[3].value,
        userCode: this.dialogData.form[4].value.join(','),
        beginTime: this.dialogData.form[5].value,
        endTime: this.dialogData.form[6].value,
        detail: this.dialogData.form[7].value,
        leaderCode: this.dialogData.form[8].value,
      }
      this.request.post(url, param).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: this.editCode ? '修改成功' : '登记成功',
            type: 'success'
          });
          this.search()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 弹窗
    setUnit() {
      // this.dialogData.show = true
      console.log('user', this.user)
      let list = []
      // 找到父单位
      // console.log('父单位', this.user.organizationUnit, this.unitList, findRootUnit(this.user.organizationUnit, this.unitList))
      // let oFather = findRootUnit(this.user.organizationUnit, this.unitList)
      // if (oFather) {
      //    this.fatherUnitList[oFather.code] = oFather
      //   list = [{
      //     code: this.fatherUnitList[oFather.code].code,
      //     name: this.fatherUnitList[oFather.code].name
      //   }]
      //   if (this.user.organizationUnit != this.user.workUnit) {
      //     let wFather = findRootUnit(this.user.workUnit, this.unitList)
      //     this.fatherUnitList[wFather.code] = wFather
      //     list.push({
      //       code: this.fatherUnitList[wFather.code].code,
      //       name: this.fatherUnitList[wFather.code].name
      //     })
      //   }
      // }
      // 工作单位
      let wFather = findRootUnit(this.user.workUnit, this.unitList)
      this.fatherUnitList[wFather.code] = wFather
      list.push({
        code: this.fatherUnitList[wFather.code].code,
        name: this.fatherUnitList[wFather.code].name
      })
      // 查询所有单位
      let EpidemicListManagerSelectAllCompany = false
      this.funMenu.forEach(item => {
        if (item.code == 'EpidemicListManagerSelectAllCompany') EpidemicListManagerSelectAllCompany = true
        if (item.code == 'EpidemicListManagerSuperUpdate') this.EpidemicListManagerSuperUpdate = true
      })
      // if (list.length) {
        if (!EpidemicListManagerSelectAllCompany) {
          this.searchUnitList = list
          console.log('this.searchUnitList[0].code', this.searchUnitList)
          this.serachData.unit = [this.searchUnitList[0].code]
        } else {
          this.searchUnitList = this.unitList
        }
        this.search()
      // }
      this.dialogData.form[0].list = list
    },
    // 弹窗change回调
    enumChange(opt) {
      if (opt.key == 'unit') {
        // 获取人员信息
        let companyCodeList = [opt.val]
        console.log(this.fatherUnitList, opt.val)
        let cunit = this.fatherUnitList[opt.val].children || []
        while(cunit.length) {
          cunit.forEach(item => {
            companyCodeList.push(item.code)
          })
          cunit = cunit.children || []
        }
        this.request.post('/user/pagelist', {
          companyCodeList: companyCodeList
        }).then(res => {
          if (res.status == 200 & res.data.success) {
            console.log(res)
            let data = []
            res.data.data.forEach(item => {
              data.push({
                code: item.code,
                name: item.name + ' | ' + item.policeCode
              })
            })
            this.dialogData.form[4].list = data
            this.dialogData.form[8].list = data
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: 'error'
            });
          }
        }) 
      }
    },
    // 弹窗成功回调
    dialogSuccess() {
      console.log('dialogSuccess', this.dialogData.form)
      let notOK = false
      this.dialogData.form.forEach((item, index) => {
        if (!item.value && index != 7) notOK = true
      })
      if (notOK) {
        this.dialogData.show = true
        this.$message({
          showClose: true,
          message: '请完整填写信息',
          type: 'warning'
        });
        return
      } else {
        this.operateRole()
      }
    },
    // 确认登记
    sure(code) {
      this.request.post('/epidemic/confirm', {
        code: code
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '确认登记成功',
            type: 'success'
          });
          this.search()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 删除登记
    del(code) {
      this.request.post('/epidemic/delete', {
        code: code
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '删除登记成功',
            type: 'success'
          });
          this.search()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 获取枚举信息
    getEnums() {
      this.request.post('/epidemic/enum/all', {}).then(res => {
        if (res.status == 200 & res.data.success) {
          console.log(res.data.data)
          this.enums = res.data.data || {}
          this.dialogData.form[1].list = this.enums.type || []
          this.dialogData.form[2].list = this.enums.location || []
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    handleClick(tab, event) {
      console.log(this.activeTab,tab.name, event);
      this.search()
    },
    // 初始化
    init() {
      this.unit()
      this.getEnums()
      // this.search()
    }
  },
  watch: {
    $route(val) {
      if (val) this.init()
    },
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
      console.log('unitList',val)
    }
  },
  mounted() {
    this.init()
  }
}
</script>

<style scoped>
.h100 {
  height: 100%;
}
.ml10 {
  margin-left: 10px;
}
.mt10 {
  margin-top: 10px;
}
.detail {
  margin-top: 20px;
}
</style>
<style>
.epidemic-list .el-cascader>span {
  line-height: 28px;
}
</style>
