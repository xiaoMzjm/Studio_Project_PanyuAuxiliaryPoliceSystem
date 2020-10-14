<!-- 高级查询弹窗 -->
<template>
  <div>
    <el-dialog width="80%" :title="data.title" :visible.sync="data.show">
      <User style="height: 500px" :data="userData" :superSearch="true"></User>
      <div slot="footer" class="dialog-footer" v-show="!data.hideOperate">
        <el-button @click="data.show = false">取 消</el-button>
        <el-button @click="reset">重 置</el-button>
        <el-button type="primary" @click="success">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import User from '../../components/user.vue'
import userConfig from '../../config/user.json'

export default {
  name: 'SuperSearch',
  components: {
    User
  },
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
  data() {
    return {
      userData: {}
    }
  },
  computed: {
    
  },
  methods: {
    success() {
      this.data.show = false
      this.$emit('success', this.userData)
    },
    // 添加人员
    addUser() {
      let data = {}
      for (let item in userConfig) {
        if (userConfig[item].type == 'enums' || userConfig[item].type == 'table' || userConfig[item].type == 'range'  || userConfig[item].type == 'multiple') {
          data[item] = []
        } else {
          data[item] = ''
        }
      }
      this.userData = data
    },
    reset() {
      this.addUser()
    }
  },
  mounted() {
    this.addUser()
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
</style>
