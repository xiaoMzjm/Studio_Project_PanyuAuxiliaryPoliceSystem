<template>
  <div class="h100">
    <div>
      <!-- 单位 -->
      <el-cascader v-model="searchData.companyCodeList" change-on-select
        :options="enums['unit']" :props="{ label: 'name', value: 'code', checkStrictly: true }" clearable>
      </el-cascader>
      <el-input v-model="searchData.name" style="width: initial;" class="ml10" placeholder="请输入用户名" ></el-input>
      <el-button class="ml10" type="primary" @click="search">查询</el-button>
      <el-button class="ml10" type="primary" @click="editCode = selectCode; dialogData.form[0].value = []; dialogData.show = true">批量绑定</el-button>
    </div>
    <div class="detail h100" v-loading="loading">
      <Table 
        style="height: calc(100% - 50px);"
        :selection="true"
        :header="headerData" 
        :data="data" 
        size="mini"
        :operation="operation"
        @operateClick="operateClick"
        @selectionChange="selectionChange"
        :showPage="false"
      ></Table>
    </div>
    <Dialog :data="dialogData" @success="dialogSuccess"></Dialog>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Table from '../../components/table.vue'
import Dialog from '../../components/dialog/index.vue'

export default {
  name: 'Employeecard',
  components: {
    Table,
    Dialog
  },
  computed: {
    ...mapState({
      role: 'role',
    }),
    enums() {
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
      console.log(unit)
      return {
        unit: unit
      }
    }
  },
  data() {
    return {
      loading: false,
      year: (new Date()).getFullYear().toString(),
      headerData: [
        { key: 'policeCode', label: '警号' },
        { key: 'name', label: '用户名' },
        { key: 'role', label: '角色' },
        { key: 'companyName', label: '所属单位' }
      ],
      operation: [
        { title: '绑定', type: 'edit' }
      ],
      data: [],
      dialogData: {
        title: '绑定角色',
        show: false,
        form: [
          { label: '角色名', type: 'enum', key: 'role', value: [], multiple: true, placeholder: '请输入角色名' }
        ]
      },
      editCode: [],
      selectCode: [],
      searchData: {
        companyCodeList: [],
        name: ''
      }
    }
  },
  methods: {
    ...mapActions([
      'getRole', 'unit'
    ]),
    operateClick(opt) {
      console.log(opt)
      switch(opt.type) {
        case 'edit': {
          this.editCode = [this.data[opt.index]]
          this.dialogData.form[0].value = []
          this.data[opt.index].roleCodeList && this.data[opt.index].roleCodeList.forEach(item => {
            this.dialogData.form[0].value.push(item)
          })
          this.dialogData.show = true
          break
        }
      }
    },
    // 查询
    search() {
      this.loading = true
      this.request.post('/user/pagelist', {
        companyCodeList: this.searchData.companyCodeList || [],
        name: this.searchData.name || ''
      }).then(res => {
        this.loading = false
        if (res.status == 200 & res.data.success) {
          console.log(res.data)
          this.data = res.data.data || []
          let roleOpt = {}
          this.role.forEach(role => {
            roleOpt[role.code] = role.name
          })
          this.data.forEach(item => {
            let curRole = []
            item.roleCodeList.forEach(roleCode => {
              curRole.push(roleOpt[roleCode])
            })
            item.role = curRole.join('/')
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
    // 弹窗成功回调
    dialogSuccess() {
      console.log('dialogSuccess', this.dialogData.form)
      this.bind()
    },
    // 绑定角色
    bind() {
      console.log(this.editCode)
      if (!this.editCode.length) return
      let userCodeList = []
      this.editCode.forEach(item => {
        userCodeList.push(item.code)
      })
      this.request.post('/userrole/bind', {
        userCodeList: userCodeList,
        roleCodeList: this.dialogData.form[0].value || []
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '绑定角色成功',
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
    selectionChange(val) {
      console.log('selectionChange',val.val)
      this.selectCode = val.val
    },
    // 初始化
    init() {
      this.unit()
      this.getRole()
      // this.search()
    }
  },
  watch: {
    $route(val) {
      if (val) this.init()
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
.detail {
  margin-top: 20px;
}
</style>
