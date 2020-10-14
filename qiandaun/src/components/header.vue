<!-- 顶部菜单 -->
<template>
  <div>
    <el-header class="header">
      <el-row>
        <el-col :span="12">
          <div>
            <img src="../assets/logo.png" />
            <span>广州市公安局番禺区分局政工系统</span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="user">
            <a :href="downloadhelp">
              <el-button type="text" class="sm-menu">帮助</el-button>
            </a>
            <el-dropdown @command="logout">
              <span class="nickname">
                {{nickname}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>  
        </el-col>
      </el-row>
    </el-header>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import Config from '../config/common.json'

export default {
  name: 'Header',
  props: {

  },
  data() {
    return {
      downloadhelp: Config.server + '/system/downloadhelp',
    }
  },
  computed: mapState({
    nickname: 'nickname'
  }),
  methods: {
    ...mapMutations([
      'setNickname'
    ]),
    logout() {
      // console.log(222, this.$cookie.get('token'))
      // this.$router.push('/login')
      // this.$cookie.delete('token')
      this.request.post('/user/logout', {}).then(res => {
        console.log(res)
        if (res.status == 200 && res.data.success) {
          this.$cookie.delete('nickname')
          this.$router.push('/login')
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
    if (!this.nickname) {
      this.$store.commit('setNickname', this.$cookie.get('nickname'))
      this.$store.dispatch('getUser', this.$cookie.get('code'))
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
.el-dropdown {
  vertical-align: top;
}
.nickname {
  color: #ffffff;
}
.sm-menu {
  margin-right: 10px;
  color: #ffffff;
}
</style>
