<!-- 人员信息 -->
<template>
  <div class="h100">
    <el-form label-width="140px" label-position="left" class="h100">
      <el-tabs v-model="activeTab" class="h100">
        <el-tab-pane label="个人信息" name="index">
          <el-row>
            <el-col :span="superSearch ? 24 : 20">
              <el-row>
                <el-col v-for="(item, index) in data" :key="'user-' + index" :span="12">
                  <el-form-item v-if="userConfig[index] && userConfig[index].type != 'table' && userConfig[index].type != 'pic'" :label="userConfig[index].name">
                    <template v-if="superSearch">
                      <!-- 日期 -->
                      <el-date-picker v-if="userConfig[index].type === 'date'"
                        format="yyyy/MM/dd" value-format="yyyy/MM/dd"
                        v-model="data[index]" type="daterange" start-placeholder="开始日期" end-placeholder="结束日期">
                      </el-date-picker>
                      <!-- 枚举 -->
                      <!-- 多选 -->
                      <el-select v-else-if="userConfig[index].type === 'multiple' || userConfig[index].type === 'enum'"
                        v-model="data[index]" placeholder="请选择" 
                        filterable multiple>
                        <el-option v-for="item in enums[index]" :key="item.code"
                          :label="item.name" :value="item.code">
                        </el-option>
                      </el-select>
                      <!-- 单位 -->
                      <el-cascader v-else-if="userConfig[index].type === 'enums'" v-model="data[index]" change-on-select
                        :options="enums['unit']" :props="{ label: 'name', value: 'code', checkStrictly: true }" clearable>
                      </el-cascader>
                      <!-- 数字范围 -->
                      <template v-else-if="userConfig[index].type === 'range'">
                        <el-input style="width: 40%" v-model="data[index][0]"></el-input>
                        -
                        <el-input style="width: 40%" v-model="data[index][1]"></el-input>
                      </template>
                      <!-- 普通输入 -->
                      <el-input v-else v-model="data[index]"></el-input>
                    </template>
                    <template v-else>
                      <!-- 日期 -->
                      <el-date-picker v-if="userConfig[index].type === 'date'" 
                        format="yyyy/MM/dd" value-format="yyyy/MM/dd"
                        v-model="data[index]" type="date" placeholder="选择日期">
                      </el-date-picker>
                      <!-- 枚举 -->
                      <el-select v-else-if="userConfig[index].type === 'enum'"
                      v-model="data[index]" placeholder="请选择" filterable>
                        <el-option v-for="item in enums[index]" :key="item.code"
                          :label="item.name" :value="item.code">
                        </el-option>
                      </el-select>
                      <!-- 多选 -->
                      <el-select v-else-if="userConfig[index].type === 'multiple'"
                      v-model="data[index]" placeholder="请选择" filterable multiple>
                        <el-option v-for="item in enums[index]" :key="item.code"
                          :label="item.name" :value="item.code">
                        </el-option>
                      </el-select>
                      <!-- 单位 -->
                      <el-cascader v-else-if="userConfig[index].type === 'enums'" v-model="data[index]" change-on-select
                        :options="enums['unit']" :props="{ label: 'name', value: 'code', checkStrictly: true }" clearable>
                      </el-cascader>
                      <!-- 普通输入 -->
                      <el-input v-else v-model="data[index]" :disabled="userConfig[index].disabled || false"></el-input>
                    </template>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="4" v-if="!superSearch">
              <!-- 头像 -->
              <img v-if="data.headPicCode" :src="data.headPicUrl" class="avatar">
              <div v-else class="headimg">
                <i class="el-icon-user-solid avatar-uploader-icon"></i>
              </div>
              <el-upload
                class="avatar-uploader"
                :action="Config.server + Config.uploadServer"
                :with-credentials="true"
                :show-file-list="false"
                :on-success="uploadCb">
                <!-- <img v-if="data.headPicCode" :src="data.headPicUrl" class="avatar"> -->
                <template>
                  <!-- <i class="el-icon-user-solid avatar-uploader-icon"></i> -->
                  <el-button class="upload-btn" size="mini" type="primary">上传</el-button>
                </template>
              </el-upload>
            </el-col>
          </el-row>
        </el-tab-pane>
        <template v-if="!superSearch">
          <el-tab-pane 
            class="h100"
            v-for="(item, index) in headerData" 
            :key="index" :name="index"
            :label="userConfig[index].name" 
            >
            <div class="table-title">
              <el-button size="mini" type="primary" @click="showDialog(index)">新增</el-button>
            </div>
            <div style="height: calc(100% - 70px)">
              <Table
                :tkey="index"
                :header="item" 
                :data="data[index]" 
                :operation="operation"
                :showPage="false"
                @operateClick="operateClick"
              ></Table>
            </div>
          </el-tab-pane>
        </template>
      </el-tabs>
    </el-form>
    <Dialog :data="dialogData" @success="dialogSuccess"></Dialog>
  </div>
</template>

<script>
import Table from '../components/table.vue'
import Dialog from '../components/dialog/index.vue'
import userConfig from '../config/user.json'
import Config from '../config/common.json'

export default {
  name: 'User',
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
      type: Object,
      default() {
        return {}
      } 
    },
    /**
     * 操作
     */
    operate: {
      type: Array
    },
    /**
     * 高级查询
     */
    superSearch: {
      type: Boolean,
      default: false
    }
  },
  components: {
    Table,
    Dialog
  },
  data() {
    return {
      userConfig: userConfig,
      Config: Config,
      value: '',
      imageUrl: '',
      operation: [
        { title: '编辑', type: 'edit' },
        { title: '删除', type: 'del' }
      ],
      headerData: {
        personalExperience: [
          { key: 'timeStart', label: '起始日期', type: 'date' },
          { key: 'timeEnd', label: '结束日期', type: 'date' },
          { key: 'unit', label: '单位或组织名称' },
          { key: 'department', label: '部门' },
          { key: 'duty', label: '所属职务' },
        ],
        familyMember: [
          { key: 'name', label: '姓名' },
          { key: 'relation', label: '关系' },
          { key: 'company', label: '单位' },
          { key: 'duty', label: '职务' },
          { key: 'identityCard', label: '身份证' },
          { key: 'phone', label: '电话' },
          { key: 'politicalLandscape', label: '政治面貌', type: 'enum' }
        ],
        award: [
          { key: 'name', label: '奖惩名称' },
          { key: 'time', label: '奖惩时间', type: 'date' },
          { key: 'reason', label: '奖惩原因' },
          { key: 'company', label: '批准单位' }
        ],
        assessment: [
          { key: 'time', label: '发生时间', type: 'date' },
          { key: 'grade', label: '等级' },
          { key: 'remark', label: '备注' }
        ]
      },
      dialogData: {
        title: '',
        show: false,
        form: []
      },
      activeTab: 'index'
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
        quasiDrivingTypeList: this.$store.state.quasiDrivingType,
        exserviceman: this.$store.state.exserviceman,
        sex: this.$store.state.sex,
        education: this.$store.state.education,
        personnelType: this.$store.state.personnelType,
        placeOfWork: this.$store.state.placeOfWork,
        authorizedStrengthType: this.$store.state.authorizedStrengthType,
        jobGrade: this.$store.state.jobGrade,
        treatmentGrade: this.$store.state.treatmentGrade,
        enrollWay: this.$store.state.enrollWay,
        dimissionTypeList: this.$store.state.dimissionType,
        jobCategory: this.$store.state.jobCategory,
        maritalStatus: this.$store.state.maritalStatus,
        specialPeopleList: this.$store.state.specialPeople,
        dueContract: this.$store.state.dueContract,
        unit: unit,
      }
    },
  },
  mounted() {

  },
  methods: {
    uploadCb(response) {
      this.data.headPicUrl = Config.server + response.data.url
      this.data.headPicCode = response.data.code
    },
    showDialog(type, index) {
      let value = undefined
      if (!isNaN(index)) value = this.data[type][index]
      let form = []
      this.headerData[type].forEach(item => {
        form.push({
          label: item.label,
          key: item.key,
          type: item.type,
          value:  value ? value[item.key] : (item.type == 'enums' ? [] : '')
        })
      })
      console.log(form)
      this.dialogData = {
        title: this.userConfig[type].name,
        type: type,
        index: index,
        show: true,
        form: form
      }
    },
    dialogSuccess() {
      let addData = {}
      for (let item in this.dialogData.form) {
        addData[this.dialogData.form[item].key] = this.dialogData.form[item].value
      }
      if (!isNaN(this.dialogData.index)) {
        this.data[this.dialogData.type].splice(this.dialogData.index, 1, addData)
      } else if (this.data[this.dialogData.type]){
        this.data[this.dialogData.type].push(addData)
      } else {
        this.data[this.dialogData.type] = [addData]
      }
    },
    operateClick(opt) {
      console.log(opt)
      this.currUnit = opt.data || {}
      switch(opt.type) {
        case 'edit': {
          this.showDialog(opt.tkey, opt.index)
          break
        }
        case 'del': {
          this.data[opt.tkey].splice(opt.index, 1)
          break
        }
      }
    }
  }
}
</script>

<style scoped>
.h100 {
  height: 100%;
}
.el-form-item {
  padding-right: 20px;
}
.headimg {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
}
.avatar-uploader {
  /* border: 1px dashed #d9d9d9; */
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 60px;
  color: #8c939d;
  width: 100%;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 100%;
  height: auto;
  /* display: block; */
}
.upload-btn {
  width: 100%;
  /* margin-bottom: 20px; */
  margin-top: 10px;
}
.el-date-editor.el-input, .el-date-editor.el-input__inner, .el-select, .el-cascader {
  width: 100%;
}
.el-cascader {
  line-height: 1;
}
.el-cascader__label {
  line-height: 40px;
}
</style>

<style>
.avatar-uploader .el-upload {
  width: 100%;
}
.table-title {
  margin: 10px 0;
}
.table-title > span {
  font-weight: bold;
  font-size: larger;
  margin-right: 10px;
}
.el-form-item__content {
  line-height: 1;
}
.el-cascader>span, .el-select>span {
  line-height: 40px;
}
.el-tabs__content {
  overflow-y: scroll;
  height: calc(100% - 50px);
}

</style>

