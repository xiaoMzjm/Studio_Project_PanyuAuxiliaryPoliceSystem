<template>
  <div id="app">
    <div id="app-content" v-if="!login">
      <el-container direction="vertical">
        <Header></Header>
        <el-container class="main-box">
          <Sidebar class="sidebar"></Sidebar>
          <el-main class="main">
            <Bread></Bread>
            <div class="main-detail">
              <router-view></router-view>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </div>
    <div id="app-content" v-else>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Header from './components/header.vue'
import Sidebar from './components/sidebar.vue'
import Bread from './components/bread.vue'

export default {
  name: 'App',
  components: {
    Header,
    Sidebar,
    Bread
  }, 
  data() {
    return {
      login: true,
      menuOk: false
    }
  },
  computed: {
    ...mapState({
      menu: 'menu'
    }),
  },
  watch: {
    $route (newRoute) {
      console.log(newRoute)
      this.changeRoute(newRoute)
    },
    menu(val) {
      if (val) {
        this.menuOk = true
        this.changeRoute(this.$route)
      }
    }
  },
  created() {
    // document.title = '首页'
  },
  mounted() {
    this.changeRoute(this.$route)
  },
  methods: {
    changeRoute(newRoute) {
      console.log('changeRoute', newRoute.path)
      if (!newRoute.path.includes('/login')) this.login = false
      else this.login = true
      if (this.menuOk && this.$route.path != '/error') {
        let isOk = false
        let initUrl = ''
        this.menu.forEach((item, index) => {
          if (!initUrl.length && index == 0 && item.children.length) initUrl = item.children[0].url || ''
          if (this.$route.path.includes(item.code)) {
            item.children.forEach(citem => {
              if (this.$route.path.includes(citem.code)) {
                isOk = true
              }
            })
          }
        });
        initUrl = initUrl || '/error'
        console.log(initUrl)
        if (!isOk && !newRoute.path.includes('/login')) this.$router.push(initUrl)
        console.log(!isOk, !newRoute.path.includes('/login'), this.menu)
      }
    }
  }
}
</script>

<style>
html, body, #app, #app-content, .container {
  height: 100%;
}
body {
  margin: 0;
  padding: 0;
  color: #333333;
}
.el-container {
  height: 100%;
}
.main-box {
  height: calc(100% - 60px);
}
.sidebar, .main {
  height: 100%;
}
.main {
  background-color: #E9EEF3;
  color: #333;
}
.main-detail {
  background-color: #ffffff;
  padding: 20px;
  height: calc(100% - 85px);
  /* overflow-y: scroll; */
}
.bread {
    margin-bottom: 20px;
}
</style>
