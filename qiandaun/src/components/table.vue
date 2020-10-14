<!-- 表格 -->
<template>
  <div class="h100">
    <el-table border highlight-current-row height="100%"
      :data="data" :size="size"
      @row-click="(row, column, event) => $emit('check', {row, column, event})"
      @selection-change="(val) => $emit('selectionChange', {val})"
      style="width: 100%">
      <el-table-column v-if="selection" type="selection" width="40"></el-table-column>
      <el-table-column v-for="(item, index) in header" :key="'table-' + tkey + '-' + index" :prop="item.key" :label="item.label" :width="item.width">
        <template slot-scope="scope">
          <span v-if="item.type == 'enum'">
            <span v-for="(eItem, eIndex) in enums[scope.column.property]"
              :key="'enum' + scope.column.property + eIndex">
              <span v-if="eItem.code == scope.row[scope.column.property]">
                {{eItem.name}} 
              </span>
            </span>
          </span>
          <span v-else-if="item.type == 'link'">
            <span v-if="scope.row[scope.column.property].message">{{scope.row[scope.column.property].message}}</span>
            <el-link v-else type="primary" :href="scope.row[scope.column.property].url" target="_blank">{{scope.row[scope.column.property].name}}</el-link>
          </span>
          <el-button v-else-if="item.type == 'operation'" 
            @click="$emit('operateClick', { tkey: tkey, index: scope.$index, type: item.itype })"
            type="text" size="small">{{item.title}}</el-button>
          <span v-else>
            {{scope.row[scope.column.property] || '-'}}
          </span>
        </template>
      </el-table-column>
      <el-table-column 
        v-if="operation.length"
        fixed="right" label="操作">
        <template slot-scope="scope">
          <template v-for="item in operation">
            <el-button v-if="item.type != 'import'"
              :key="'table-' + tkey + '-' + item.title" 
              v-show="scope.row[item.type] != undefined ? scope.row[item.type] : true"
              @click="$emit('operateClick', { tkey: tkey, index: scope.$index, type: item.type })"
              type="text" size="small">{{item.title}}</el-button>
            <!-- 专门写给工资模块的 -->
            <el-upload v-else
              :key="'table-' + tkey + '-' + item.title" :with-credentials="true"
              :action="scope.row.importurl" 
              :data="{time: scope.row.time, type: item.itype}"
              :show-file-list="false"
              >
              <el-button size="small" type="text">{{item.title}}</el-button>
            </el-upload>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-show="showPage"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="innerPage.currentPage"
      :page-sizes="innerPage.pageSizes"
      :page-size="innerPage.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.length">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: 'Table',
	props: {
    tkey: {
      type: String,
      default() {
        return ''
      }
    },
    /**
     * 表头数据
     * [{
     *  key: 在数据中的键
     *  label: 表头名称
     * }]
     */
    header: {
      type: Array,
      default() {
        return []
      }
    },
    /**
     * 要显示的数据
     * {
     *  
     * }
     */
    data: {
      type: Array,
      default() {
        return []
      } 
    },
    // 多选
    selection: {
      type: Boolean,
      default() {
        return false
      }
    },
    /**
     * 操作
     */
    operation: {
      type: Array,
      default() {
        return []
      }
    },
    // 显示分页
    showPage: {
      type: Boolean,
      default() {
        return true
      }
    },
    /**
     * 分页器的数据
     * {
     *  currentPage: 当前页
     *  pageSize: 每页显示条数
     * }
     */
    page: {
      type: Object,
      default() {
        return {
          currentPage: 1,
          pageSize: 10
        }
      }
    },
    size: {
      type: String,
      default: 'medium'
    }
  },
  data() {
    return {
      
    }
  },
  computed: {
    innerPage() {
      let pageSizes = []
      let num = Math.ceil(this.data.length / 10)
      num = num <= 10 ? num : 10
      for (let size = 1; size <= num; size++) {
        pageSizes.push(size * 10)
      }
      return {
        currentPage: this.page.currentPage,
        pageSize: this.page.pageSize,
        pageSizes: pageSizes
      }
    },
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
      return {
        nation: this.$store.state.nation,
        politicalLandscape: this.$store.state.politicalLandscape,
        quasiDrivingType: this.$store.state.quasiDrivingType,
        exserviceman: this.$store.state.exserviceman,
        sex: this.$store.state.sex,
        education: this.$store.state.education,
        personnelType: this.$store.state.personnelType,
        placeOfWork: this.$store.state.placeOfWork,
        authorizedStrengthType: this.$store.state.authorizedStrengthType,
        jobGrade: this.$store.state.jobGrade,
        treatmentGrade: this.$store.state.treatmentGrade,
        enrollWay: this.$store.state.enrollWay,
        dimissionType: this.$store.state.dimissionType,
        jobCategory: this.$store.state.jobCategory,
        maritalStatus: this.$store.state.maritalStatus,
        specialPeople: this.$store.state.specialPeople,
        unit: unit
      }
    },
  },
  // watch: {
  //   header(newd) {
  //     console.log('newd',newd)
  //   },
  //   data(newd) {
  //     console.log('newd',newd)
  //   }
  // },
  methods: {
    handleCurrentChange() {

    },
    handleSizeChange() {

    },
  }
}
</script>

<style>
.el-table th, .el-table tr {
  background-color: transparent;
}
.el-table thead {
  color: #000;
}
</style>
