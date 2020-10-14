<!-- 操作弹窗 -->
<template>
  <div>
    <el-dialog :title="data.title" :visible.sync="data.show">
      <el-form label-width="70px" labelPosition="left">
        <el-form-item v-for="(item, index) in data.form" :label="item.label" :key="'dialog-' + index">
          <el-input v-if="item.type == 'textarea'" type="textarea" :autosize="{ minRows: 4, maxRows: 6}" v-model="item.value" :placeholder="item.placeholder" autocomplete="off"></el-input>
          <span class="span-40" v-else-if="item.type == 'span'">{{item.value}}</span>
          <el-date-picker v-else-if="item.type == 'date'"
            format="yyyy/MM/dd" value-format="yyyy/MM/dd"
            v-model="item.value" type="date" placeholder="选择日期">
          </el-date-picker>
          <el-select v-else-if="item.type == 'enum'" @change="(val) => $emit('enumChange', { key: item.key, val})"
            v-model="item.value" placeholder="请选择" filterable :multiple="item.multiple || false">
            <template v-if="item.list">
              <el-option v-for="itemOpt in item.list" :key="itemOpt.code"
                :label="itemOpt.name" :value="itemOpt.code">
              </el-option>
            </template>
            <template v-else>
              <el-option v-for="itemOpt in enums[item.key]" :key="itemOpt.code"
              :label="itemOpt.name" :value="itemOpt.code">
              </el-option>
            </template>
          </el-select>
          <!-- 单位专属 -->
          <el-cascader v-else-if="item.type === 'enums'" v-model="item.value" change-on-select
            :options="enums['unit']" :props="{ label: 'name', value: 'code', checkStrictly: true }" clearable>
          </el-cascader>
          <el-input v-else v-model="item.value" :placeholder="item.placeholder" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" v-show="!data.hideOperate">
        <el-button @click="data.show = false">取 消</el-button>
        <el-button type="primary" @click="success">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Dialog',
  props: {
    /**
     * {
     *  title,
     *  show,
     *  hideOperate,
     *  form: {
     *    lebel,
     *    value,
     *    placeholder
     *  }
     * }
     */
    data: {
      type: Object
    }
  },
  computed: {
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
        unit: unit,
        role: this.$store.state.role,
      }
    },
  },
  methods: {
    success() {
      this.data.show = false
      this.$emit('success')
    }
  }
}
</script>

<style scoped>
.header {
  height: 60px;
  background-color: #102e52;
  color: #ffffff;
  line-height: 60px;
}
.header img {
  width: 32px;
  height: 32px;
  padding-right: 10px;
  vertical-align:middle;
}
.user {
  text-align: right;
}
.span-40 {
  line-height: 40px;
}
</style>

