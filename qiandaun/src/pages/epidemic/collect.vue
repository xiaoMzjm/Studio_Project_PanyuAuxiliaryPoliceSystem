<template>
  <!-- 报表页面 -->
  <div class="h100">
    <div>
      <el-row>
        <el-col :span="12">
          <el-date-picker size="mini"
            format="yyyy/MM/dd" value-format="yyyy/MM/dd"
            v-model="month" type="date" placeholder="选择月份">
          </el-date-picker>
          <el-button class="ml10" type="primary" size="mini" @click="search(month)">查询</el-button>
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
import Config from '../../config/common.json'

export default {
  name: 'EpidemicCollectManager',
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
      month: (new Date()).getFullYear() + '/' + ((new Date()).getMonth()+1) + '/' + (new Date()).getDate(),
      curMonth: '',
      curDay: '',
      headerData: [
        { key: 'day', label: '日期' },
        { label: '操作', title: '汇总', type: 'operation', itype: 'create' },
        { key: 'zfile', label: '政工统计表', type: 'link' },
        { key: 'sfile', label: '市局统计表', type: 'link' },
        { key: 'mfile', label: '今日防疫报告', type: 'link' },
        // { key: 'remark', label: '备注' },
      ],
      data: [],
      operation: [
        // { title: '汇总', type: 'operation', itype: 'create' }
      ],
      downloadurl: Config.server + '/epidemic/statistics/download?code=',
      // 当前到期类型
      type: '',
      // 请求接口名
      url: {
        EpidemicCollectManager: {
          search: '/epidemic/statistics/select',
          create: '/epidemic/statistics/create'
        }
      },
      dialogData: {
        title: '汇总',
        show: false,
        hideOperate: true,
        form: [
          { label: '备注', value: '', placeholder: '请输入备注', type: 'textarea' },
        ]
      },
    }
  },
  methods: {
    ...mapActions([
      'unit'
    ]),
    operateClick(opt) {
      console.log(opt)
      this.currUnit = opt.data || {}
      switch(opt.type) {
        case 'create': {
          console.log(opt)
          // this.curDay = opt.index+1
          // this.dialogData.hideOperate = false
          // this.dialogData.show = true
          this.createemployeecard()
          break
        }
        case 'search': {
          this.curDay = opt.index+1
          this.dialogData.form[0].value = this.data[opt.index].remark
          this.dialogData.hideOperate = true
          this.dialogData.show = true
          break
        }
      }
    },
    // 查询
    search(month) {
      if (!month && !this.month) {
        this.$message({
          showClose: true,
          message: '请选择要查询的日期',
          type: 'error'
        });
        return
      }
      this.loading = true
      month = month || this.month
      this.request.post(this.url[this.type].search, {
        date: month
      }).then(res => {
        this.curMonth = month
        this.loading = false
        if (res.status == 200 & res.data.success) {
          this.data = res.data.data || []
          this.data.forEach((item) => {
            item.day = this.curMonth //(index + 1) + ' 日',
            item.zfile = {
              name: item.zhengGongFileName,
              url: this.downloadurl + item.zhengGongFileCode,
              message: item.message || ''
            }
            item.sfile = {
              name: item.shiJuFileName,
              url: this.downloadurl + item.shiJuFileCode,
              message: item.message || ''
            }
            item.mfile = {
              name: item.messageFileName,
              url: this.downloadurl + item.messageFileCode,
              message: item.message || ''
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
    // 补生成
    createemployeecard() {
      this.loading = true
      // console.log(this.curMonth + '/' + this.curDay)
      this.request.post(this.url[this.type].create, {
        date: this.curMonth, // + '/' + this.curDay,
        remark: this.dialogData.form[0].value
      }).then(res => {
        this.loading = false
        if (res.status == 200 & res.data.success) {
          if (res.data.data) {
            this.$message({
              showClose: true,
              message: res.data.data,
              type: 'warn'
            });
          } else {
            this.$message({
              showClose: true,
              message: '操作成功',
              type: 'success'
            });
            this.search(this.curMonth)
          }
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
      this.createemployeecard()
    },
    // 初始化
    init() {
      if (this.$route.path == '/EpidemicManager/EpidemicCollectManager') {
        this.type = 'EpidemicCollectManager'
      }
      this.search(null)
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
