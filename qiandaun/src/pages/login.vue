<template>
  <div class="container">
    <div class="header">
      <div>
        <img src="../assets/logo.png" />
        <span>广州市公安局番禺区分局政工系统</span>
      </div>
    </div>
    <div class="login-container">
      <div class="inner">
        <div class="login-title">
          <span>广州市公安局番禺区分局政工系统</span>
        </div>
        <div class="login-box">
          <div>
            <div class="login-box-item">
              <el-input prefix-icon="el-icon-user-solid" type="text" placeholder="请输入用户名" v-model="loginForm.account" autocomplete="off" @keyup.enter.native="login"></el-input>
            </div>
            <div class="login-box-item">
              <el-input prefix-icon="el-icon-key" type="password" placeholder="请输入密码" v-model="loginForm.password" autocomplete="off" @keyup.enter.native="login"></el-input>
            </div>
            <div class="login-box-item">
              <el-checkbox v-model="remenberPass">记住密码</el-checkbox>
            </div>
            <div class="login-box-item">
              <el-button class="login-btn" type="primary" @click="login">登录</el-button>  
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <span>Copyright © 2020-2030 Panyu branch of Guangzhou Public Security Bureau</span>
    </div>
    <div class="bg">
      <img class="map" src="../assets/map.png" />
      <img class="people" src="../assets/map.png" />
      <img class="house" src="../assets/house.png" />
    </div>
  </div>
</template>

<script>
import md5 from 'md5'
import { mapMutations } from 'vuex'

export default {
  name: 'Login',
  components: {
    
  },
  computed: {
    
  },
  data() {
    return {
      remenberPass: this.$cookie.get('remenberpass') ? true : false,
      loginForm: {
        account: '',
        password: ''
      }
    }
  },
  watch: {
    remenberPass(val) {
      if (val) {
        this.$cookie.set('remenberpass', 1, 7)
      } else {
        this.$cookie.delete('remenberpass');
      }
    },
    'loginForm.account'(val) {
      if (this.remenberPass && val == this.$cookie.get('account')) {
        this.loginForm.password = this.$cookie.get('password')
      }
    }
  },
  methods: {
    ...mapMutations([
      'setNickname'
    ]),
    login() {
      if (this.remenberPass) {
        this.savePass()
      } else {
        this.delPass()
      }
      // 校验
      if (!this.loginForm.account || !this.loginForm.password) {
        this.$message({
          showClose: true,
          message: '账号或密码不能为空',
          type: 'warning'
        });
        return
      }
      console.log(md5(this.loginForm.password))
      this.request.post('/user/login', {
        account: this.loginForm.account,
        // password: this.loginForm.password 
        password: this.loginForm.password.length != 32 ? md5(this.loginForm.password) : this.loginForm.password
      }).then(res => {
        console.log(res)
        if (res.status == 200 & res.data.success) {
          this.$router.push('/UserManager/UserList')
          console.log(res.data.data.name)
          this.$store.commit('setNickname', res.data.data.name)
          this.$store.dispatch('getUser', res.data.data.code)
          this.$cookie.set('nickname', res.data.data.name, 7)
          this.$cookie.set('code', res.data.data.code, 7)
        } else {
          this.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          });
        }
      }) 
    },
    // 记住密码
    savePass() {
      this.$cookie.set('account', this.loginForm.account, 7)
      // this.$cookie.set('password', this.loginForm.password, 7)
      this.$cookie.set('password', md5(this.loginForm.password), 7)
      console.log(this.$cookie.get('account'))
      console.log(this.$cookie.get('password'))
    },
    // 删除记住的密码
    delPass() {
      this.$cookie.delete('account');
      this.$cookie.delete('password');
    }
  },
  mounted() {
    
  }
}
</script>

<style scoped>

.container {
  /* background-image: url(../assets/bg1.png); */
  background-position: center;
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.header {
  width: calc(100% - 60px);
  padding: 20px 30px;
  background-color: #f7fafc;
}
.header img {
  width: 32px;
  height: 32px;
  padding-right: 10px;
  vertical-align: middle;
}
.login-container {
  /* width: 660px;
  position: absolute;
  left: 50%;
  margin-left: -300px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  margin-top: 16%;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  border-radius: 10px; */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
  width: 100%;
  height: calc(100% - 162px);
}
.inner {
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  border-radius: 10px;
}
.login-title {
  font-size: 1.5em;
  text-align: center;
  width: 460px;
  padding: 30px 100px;
}
.login-box {
  width: 460px;
  padding: 0 100px 50px;
  
}
.login-box-item {
  margin: 16px 0;
}
.login-btn {
  width: 100%;
}
.footer {
  background-color: rgb(32, 65, 105);
  width: 100%;
  height: 100px;
  line-height: 100px;
  text-align: center;
  color: #F2F6FC;
  font-size: 0.8em;
  position: absolute;
  bottom: 0;
}
.bg {    
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  bottom: 0;
  z-index: -1;
  overflow: hidden;
}
.map, .people {
  position: relative;
  height: auto;
  opacity:0.1;
}
.map {
  left: -20%;
  top: -10%;
  width: 50%;
}
.people {
  left: 20%;
  top: -10%;
  width: 50%;
}
.house {
  position: absolute;
  height: auto;
  z-index: -1;
  opacity:0.1;
  width: 80%;
  left: 10%;
  bottom: 100px;
}
.background {
  position: absolute;
  width: 100%;
  height: 30%;
  bottom: 0;
  z-index: -1;
  background-color: #50a7e3;
}
</style>
