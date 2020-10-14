<template>
  <div>
    <div>
      <el-row>
        <el-col :span="12">
          <el-button type="primary" size="mini" @click="currUnit = '';dialogData.show = true">添加单位</el-button>
        </el-col>
      </el-row>
    </div>
    <Tree :data="unitList" :operate="operate" :expandAll="true" @operateClick="operateClick"></Tree>
    <Dialog :data="dialogData" @success="dialogSuccess"></Dialog>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import Tree from '../../components/tree.vue'
import Dialog from '../../components/dialog/index.vue'

export default {
  name: 'Unit',
  components: {
    Tree,
    Dialog
  },
  computed: {
    ...mapState({
      unitList: 'unit',
    }),
  },
  data() {
    return {
      dialogData: {
        title: '添加单位',
        show: false,
        form: [
          { label: '名称', value: '', placeholder: '请输入单位名称' },
          { label: '描述', value: '', type: "textarea" }
        ]
      },
      operate: [
        { title: '查看详情', type: 'detail' },
        { title: '添加子单位', type: 'add' },
        { title: '删除单位', type: 'del' }
      ],
      currUnit: {} // 当前操作单位
    }
  },
  methods: {
    ...mapActions([
      'unit'
    ]),
    // 弹窗成功回调
    dialogSuccess() {
      console.log('dialogSuccess', this.dialogData.form)
      this.addUnit()
    },
    operateClick(opt) {
      console.log(opt)
      this.currUnit = opt.data || {}
      switch(opt.type) {
        case 'detail': {
          this.dialogData = {
            title: '查看详情',
            show: true,
            hideOperate: true,
            form: [
              { label: '名称', value: opt.data.name, placeholder: '请输入单位名称' },
              { label: '描述', value: opt.data.description, type: "textarea" }
            ]
          }
          break
        }
        case 'add': {
          console.log('addd')
          this.dialogData = {
            title: '添加子单位',
            show: true,
            form: [
              { label: '名称', value: '', placeholder: '请输入单位名称' },
              { label: '描述', value: '', type: "textarea" },
              { label: '所属单位', value: this.currUnit.name, type: 'span'  },
            ]
          }
          break
        }
        case 'del': {
          this.$confirm('确认删除 ' + this.currUnit.name + ' ?').then(res => {
            if (res == 'confirm') this.delUnit()
          }).catch(err => {
            console.log(err)
          });
          break
        }
      }
    },
    // 添加单位
    addUnit() {
      this.request.post('/company/add', {
        name: this.dialogData.form[0].value,
        desc: this.dialogData.form[1].value,
        fatherCode: this.currUnit.code
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '添加单位成功',
            type: 'success'
          });
          this.unit()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 删除单位
    delUnit() {
      this.request.post('/company/delete', {
        code: this.currUnit.code
      }).then(res => {
        if (res.status == 200 & res.data.success) {
          this.$message({
            showClose: true,
            message: '删除单位成功',
            type: 'success'
          });
          this.unit()
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    }
  },
  mounted() {
    this.unit()
  }
}
</script>

<style scoped>

</style>
