<template>
  <!-- 报表页面 -->
  <div class="h100">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="三级辅警" name="1"></el-tab-pane>
      <el-tab-pane label="四级辅警" name="2"></el-tab-pane>
    </el-tabs>
    <div v-show="!yearType">
      <el-row>
        <el-col :span="12">
          <el-date-picker size="mini"
            format="yyyy" value-format="yyyy"
            v-model="year" type="year" placeholder="选择年份">
          </el-date-picker>
          <el-button class="ml10" type="primary" size="mini" @click="search">查询</el-button>
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
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Table from '../../components/table.vue'
import Config from '../../config/common.json'

export default {
  name: 'Employeecard',
  components: {
    Table
  },
  computed: {
    ...mapState({
      unitList: 'unit',
    }),
  },
  data() {
    return {
      activeName: '1',
      loading: false,
      yearType: false,
      year: (new Date()).getFullYear().toString(),
      headerData: [
        { key: 'month', label: '月份' },
        { key: 'ifile', label: '导入明细表', type: 'link' },
        { key: 'sfile', label: '系统明细表', type: 'link' },
        { key: 'cfile', label: '正确明细表', type: 'link' },
      ],
      data: [],
      operation: [
        { title: '导入明细表', type: 'import', itype: 3 },
        { title: '导入正确明细表', type: 'import', itype: 5 }
      ],
      downloadwages: Config.server + '/wages/detail/download?code=',
      // 当前到期类型
      type: '',
      // 请求接口名
      url: {
        WagesDetail: {
          search: '/wages/detail/list',
          create: '/wages/detail/import'
        }
      }
    }
  },
  methods: {
    ...mapActions([
      'unit'
    ]),
    // 切换tab
    handleClick() {
      if (this.activeName == 1) {
        this.operation = [
          { title: '导入明细表', type: 'import', itype: 3 },
          { title: '导入正确明细表', type: 'import', itype: 5 }
        ]
      } else {
        this.operation = [
          { title: '导入明细表', type: 'import', itype: 4 },
          { title: '导入正确明细表', type: 'import', itype: 6 }
        ]
      }
      this.search()
    },
    operateClick(opt) {
      console.log(opt)
      this.currUnit = opt.data || {}
      switch(opt.type) {
        case 'import': {
          console.log(opt)
          this.createemployeecard(opt.index)
          break
        }
        case 'cimport': {
          console.log(opt)
          this.createemployeecard(opt.index)
          break
        }
      }
    },
    // 查询
    search() {
      let that = this
      if (!this.year) {
        this.$message({
          showClose: true,
          message: '请选择要查询的年份',
          type: 'error'
        });
        return
      }
      // this.loading = true
      this.request.post(this.url[this.type].search, {
        time: this.year,
        type: +this.activeName
      }).then(res => {
        // setTimeout(() => {
        //   this.loading = false
        // }, 2000)
        console.log(111)
        this.loading = false
        if (res.status == 200 & res.data.success) {
          that.data = res.data.data || []
          that.data.forEach((item, index) => {
            item.time = this.year + '/' + (index + 1)
            item.type = +this.activeName
            item.month = (index + 1) + ' 月',
            item.ifile = {
              name: item.importReportName,
              url: that.downloadwages + item.importReportCode,
              message: ''
            }
            item.sfile = {
              name: item.systemReportName,
              url: that.downloadwages + item.systemReportCode,
              message: ''
            }
            item.cfile = {
              name: item.correctReportName,
              url: that.downloadwages + item.correctReportCode,
              message: ''
            }
            item.importurl = Config.server + '/wages/detail/import'
          })
          console.log(that.data)
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
    createemployeecard(month) {
      this.request.post(this.url[this.type].create, {
        time: +this.year,
        month: month + 1
      }).then(res => {
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
            this.search()
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
    // 初始化
    init() {
      this.yearType = false
      this.headerData[0] = { key: 'month', label: '月份' }
      if (this.$route.path == '/WagesManager/WagesDetail') {
        this.type = 'WagesDetail'
      } else if (this.$route.path == '/ExpireManager/ExpireContract') {
        this.type = 'ExpireContract'
      } else if (this.$route.path == '/ExpireManager/ExpireRetire') {
        this.type = 'ExpireRetire'
      } else if (this.$route.path == '/BaseDataStatistics/YearStatistics') {
        this.type = 'YearStatistics'
        this.yearType = true
        this.headerData[0] = { key: 'year', label: '年份' }
      } else if (this.$route.path == '/BaseDataStatistics/MonthStatistics') {
        this.type = 'MonthStatistics'
      }
      this.$set(this.headerData, 0, this.headerData[0])
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
