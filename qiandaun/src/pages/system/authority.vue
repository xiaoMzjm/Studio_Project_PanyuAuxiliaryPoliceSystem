<template>
  <div class="h100">
    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      height="90%"
      row-key="code"
      border
      default-expand-all ref="tableTreeRef"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        prop="name"
        label="权限树"
        sortable>
      </el-table-column>
      <el-table-column
        v-for="key in role"
        :key="key.code"
        :prop="'role-' + key.code"
        :label="key.name">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row['role-' + key.code]" @change="(val) => { roleChange(val, scope.row, key.code) }"></el-checkbox>
        </template>
      </el-table-column>
    </el-table>
    <div class="user-operate">
      <el-button type="primary" size="mini" @click="save">保存</el-button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'Authority',
  components: {
    
  },
  computed: {
    ...mapState({
      unitList: 'unit',
      role: 'role'
    }),
  },
  data() {
    return {
      tableData: []
    }
  },
  methods: {
    ...mapActions([
      'unit', 'getRole'
    ]),
    showSelect(root) {
      root.forEach(unit => {
        unit.roles && unit.roles.forEach(role => {
          unit['role-' + role] = true
        })
        if (unit.children.length) {
          this.showSelect(unit.children)
        }
      });
    },
    // 获取绑定关系
    getAuthority() {
      this.request.post('/authority/listall', this.serachData).then(res => {
        if (res.status == 200 & res.data.success) {
          this.tableData = res.data.data || []
          // 拼合表格
          this.showSelect(this.tableData)
          this.$nextTick(() => {
            this.unFoldAll()
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
    getAuthorityCodeList(code, root) {
      let res = []
      root = root || this.tableData
      root.forEach(unit => {
        if (unit.roles && unit.roles.includes(code)) res.push(unit.code)
        if (unit.children.length) {
          res = res.concat(this.getAuthorityCodeList(code, unit.children))
        }
      })
      return res
    },
    // 保存
    save() {
      let param = []
      this.role.forEach(role => {
        let authorityCodeList = []
        authorityCodeList = this.getAuthorityCodeList(role.code, null)
        param.push({
          roleCode: role.code,
          authorityCodeList: authorityCodeList
        })
      })
      this.request.post('/authority/bindauthority', { array: param }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '绑定成功',
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
    // selectChildren(code) {

    // },
    // 父子选择联动
    roleChange(val, row, code) {
      console.log(val, row, code)
      if (val) {
        row.roles = row.roles || []
        row.roles.push(code)
      } else {
        row['role-' + code] = false
        row.roles.splice(row.roles.indexOf(code), 1)
      }
      // 根节点，底下节点全部选中
      if (!row.fatherCode) {
        this.tableData.forEach((unit, index) => {
          if (unit.code == row.code) {
            unit.children.forEach(cunit => {
              cunit.roles = cunit.roles || []
              if (val) {
                cunit.roles.push(code)
                cunit['role-' + code] = true
              } else {
                cunit.roles.splice(cunit.roles.indexOf(code), 1)
                cunit['role-' + code] = false
              }
              this.$set(this.tableData, index, this.tableData[index])
            })
          }
        })
      } else {
        this.tableData.forEach((unit, index) => {
          if (unit.code == row.fatherCode) {
            // 没有选中，取消全选
            if (!val) {
              let notSelectAll = true
              unit.children.forEach(cunit => {
                if (cunit['role-' + code]) notSelectAll = false
              })
              if (notSelectAll) {
                console.log('notSelectAll', notSelectAll)
                unit.roles.splice(unit.roles.indexOf(code), 1)
                unit['role-' + code] = false
              }
            } else {
              if (!unit['role-' + code]){
                unit.roles = unit.roles || []
                unit.roles.push(code)
                unit['role-' + code] = true
              } 
            }
          }
          this.$set(this.tableData, index, this.tableData[index])
        })
      }
    },
    // 展开全部
    unFoldAll () {
      if (!this.$refs.tableTreeRef) return
      let queryResult = this.$refs.tableTreeRef.$el.children[2].querySelectorAll('tr')
      for (let i = 0; i < queryResult.length; i++) {
        let item = queryResult[i]
        item.style.display = ''
        let classList = item.querySelectorAll('td > div > div')[0] && item.querySelectorAll('td > div > div')[0].classList
        if (classList) {
          classList.contains('el-table__expand-icon') && item.querySelectorAll('td > div > div')[0] && item.querySelectorAll('td > div > div')[0].classList.add('el-table__expand-icon--expanded')
        }
      }
    },
  },
  mounted() {
    this.getRole()
    this.getAuthority()
  }
}
</script>

<style scoped>
.h100 {
  height: 100%;
}
.user-operate {
  margin-top: 20px;
  text-align: right;
}
</style>
