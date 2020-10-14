<template>
  <div class="h100">
    <div>
      <el-row>
        <el-col :span="12">
          <el-button class="ml10" type="primary" size="mini" @click="editCode = null;dialogData.form[0].value = '';dialogData.show = true">新建角色</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="detail h100" v-loading="loading">
      <Table 
        style="height: calc(100% - 50px);"
        :header="headerData" 
        :data="data" 
        size="mini"
        :operation="operation"
        @operateClick="operateClick"
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
      unitList: 'unit',
    }),
  },
  data() {
    return {
      loading: false,
      year: (new Date()).getFullYear().toString(),
      headerData: [
        { key: 'name', label: '角色' }
      ],
      operation: [
        { title: '编辑', type: 'edit' },
        { title: '删除', type: 'del' }
      ],
      data: [],
      dialogData: {
        title: '新建角色',
        show: false,
        form: [
          { label: '角色名', value: '', placeholder: '请输入角色名' }
        ]
      },
      editCode: null
    }
  },
  methods: {
    ...mapActions([
      'unit'
    ]),
    operateClick(opt) {
      console.log(opt)
      switch(opt.type) {
        case 'edit': {
          this.editCode = this.data[opt.index].code
          this.dialogData.form[0].value = this.data[opt.index].name;
          this.dialogData.show = true
          break
        }
        case 'del': {
          this.$confirm('确认删除 ' + this.data[opt.index].name + ' ?').then(res => {
            if (res == 'confirm') this.delRole(this.data[opt.index].code)
          }).catch(err => {
            console.log(err)
          });
          break
        }
      }
    },
    // 查询
    search() {
      this.loading = true
      this.request.post('/role/listall', {}).then(res => {
        this.loading = false
        if (res.status == 200 & res.data.success) {
          this.data = res.data.data || []
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
    operateRole(name) {
      const url = !this.editCode ? '/role/add' : '/role/updatename'
      this.request.post(url, {
        code: this.editCode || null,
        name: name
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '创建角色成功',
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
    // 弹窗成功回调
    dialogSuccess() {
      console.log('dialogSuccess', this.dialogData.form)
      this.operateRole(this.dialogData.form[0].value)
    },
    // 删除角色
    delRole(code) {
      this.request.post('/role/delete', {
        code: code
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '删除角色成功',
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
    // 初始化
    init() {
      this.search()
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
