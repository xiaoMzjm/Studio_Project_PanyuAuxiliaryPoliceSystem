<!-- 表格 -->
<template>
  <div>
    <el-tree style="margin-top: 20px"
      ref="tree"
      :data="data"
      :show-checkbox="showCheckbox"
      node-key="code"
      :default-expand-all="expandAll"
      :check-on-click-node="true"
      :default-checked-keys="checkedList"
      @check="check">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button v-for="item in operate" :key="item.type" type="text" size="mini" 
          @click="$emit('operateClick', { data: data, type: item.type })">
            {{item.title}}
          </el-button>
        </span>
      </span>
    </el-tree>
  </div>
</template>

<script>
export default {
  name: 'Tree',
	props: {
    /**
     * 要显示的数据
     * [{
     *  id,
     *  label,
     *  children: [{
     *    id,
     *    label
     *  }]
     * }]
     */
    data: {
      type: Array,
      default() {
        return []
      } 
    },
    /**
     * 操作
     */
    operate: {
      type: Array
    },
    selectAll: {
      type: Boolean,
      default: false
    },
    expandAll: {
      type: Boolean,
      default: false
    },
    showCheckbox: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    'data'(val) {
      if (val.length) {
        this.setSelectAll()
      }
    }
  },
  data() {
    return {
      checkedList: []
    }
  },
  methods: {
    check() {
      let checkData = []
      let check = this.$refs.tree.getCheckedNodes()
      check.forEach(element => {
        checkData.push(element.code)
      });
      this.$emit('check', checkData)
    },
    setSelectAll() {
      if (this.selectAll) {
        this.data.forEach(item => {
          this.checkedList.push(item.code)
        })
        console.log(this.data,this.data.length,this.checkedList)
      }
    }
  }
}
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
