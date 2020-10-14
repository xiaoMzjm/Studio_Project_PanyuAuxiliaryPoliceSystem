<template>
  <!-- 报表页面 -->
  <div class="h100">
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
      loading: false,
      yearType: false,
      year: (new Date()).getFullYear().toString(),
      headerData: [
        { key: 'month', label: '月份' },
        { key: 'file', label: '文件', type: 'link' },
      ],
      data: [],
      operation: [
        { title: '生成', type: 'create' }
      ],
      downloademployeecard: Config.server + '/expire/downloadexpire?code=',
      // 当前到期类型
      type: '',
      // 请求接口名
      url: {
        ExpireEmployeeCard: {
          search: '/expire/listemployeecard',
          create: '/expire/createemployeecard'
        },
        ExpireContract: {
          search: '/expire/listcontract',
          create: '/expire/createcontract'
        },
        ExpireRetire: {
          search: '/expire/listretire',
          create: '/expire/createretire'
        },
        YearStatistics: {
          search: '/expire/listretire',
          create: '/expire/createretire'
        },
        MonthStatistics: {
          search: '/expire/listretire',
          create: '/expire/createretire'
        },
      }
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
          this.createemployeecard(opt.index)
          break
        }
      }
    },
    // 查询
    search() {
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
        year: +this.year
      }).then(res => {
        // setTimeout(() => {
        //   this.loading = false
        // }, 2000)
        this.loading = false
        if (res.status == 200 & res.data.success) {
          this.data = res.data.data || []
          this.data.forEach((item, index) => {
            item.month = (index + 1) + ' 月',
            item.file = {
              name: item.name,
              url: this.downloademployeecard + item.code,
              message: item.message
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
    createemployeecard(month) {
      this.request.post(this.url[this.type].create, {
        year: +this.year,
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
      if (this.$route.path == '/ExpireManager/ExpireEmployeeCard') {
        this.type = 'ExpireEmployeeCard'
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
