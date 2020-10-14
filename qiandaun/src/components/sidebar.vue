<!-- 左侧菜单 -->
<template>
  <div class="sidebar">
    <el-aside :width="isCollapse ? 'auto' : '220px'">
      <el-menu :style="isCollapse ? '' : 'overflow-y: scroll;'"
       :router="true" :collapse="isCollapse" :default-active="defaultActive" :collapse-transition="false" class="el-menu-vertical-demo" background-color="#204169" text-color="#fff" active-text-color="#ffd04b">
        <template v-for="(menuItem, index) in menu">
          <el-menu-item :index="menuItem.url" :route="menuItem.url" v-if="!menuItem.children" :key="menuItem.code">
            <i :class="menuItem.icon ? menuItem.icon : defaultIcon"></i>
            <span>{{menuItem.name}}</span>
          </el-menu-item>
          <el-submenu :index="index.toString()" :key="index.toString()" v-else>
            <template slot="title">
              <i :class="menuItem.icon ? menuItem.icon : defaultIcon"></i>
              <span>{{menuItem.name}}</span>
            </template>
            <el-menu-item v-for="childItem in menuItem.children" :index="childItem.url" :key="childItem.code" :route="childItem.url">
              <!-- <i :class="childItem.icon"></i> -->
              <span>{{childItem.name}}</span>
            </el-menu-item>
          </el-submenu>
        </template>
      </el-menu>
      <el-row class="flod-menu" :style="isCollapse ? 'width: 64px;' : 'width: 199px'">
        <el-col :span="24">
          <i v-show="!isCollapse" class="el-icon-d-arrow-left" @click="isCollapse = true"></i>
          <i v-show="isCollapse" class="el-icon-d-arrow-right" @click="isCollapse = false"></i>
        </el-col>
      </el-row>
    </el-aside>
    <!-- <el-row class="flod-menu" :style="isCollapse ? 'width: 64px;' : 'width: 100%'">
        <el-col :span="24">
          <i v-show="!isCollapse" class="el-icon-d-arrow-left" @click="isCollapse = true"></i>
          <i v-show="isCollapse" class="el-icon-d-arrow-right" @click="isCollapse = false"></i>
        </el-col>
      </el-row> -->
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'Sidebar',
  data() {
    return {
      isCollapse: false,
      defaultActive: '',
      defaultIcon: 'el-icon-goods'
    }
  },
  computed: mapState([
    'menu'
  ]),
  watch: {
    $route(val) {
      if (val.path === '/UserManager/UserList') {
        this.isCollapse = true
      }
    }
  },
  mounted() {
    if (this.$route.path === '/UserManager/UserList') {
      this.isCollapse = true
    }
    this.defaultActive = this.$route.path
    this.getMenu({
      "this": this
    })
  },
  methods: {
    ...mapActions([
      'getMenu'
    ]),
  }
}
</script>

<style scoped>
.el-aside, .el-menu {
  height: 100%; 
  /* calc(100% - 40px); */
  position: relative;
}
.sidebar {
  position: relative;
}
.flod-menu {
  position: absolute;
  bottom: 0;
  width: 64px;
  height: 40px;
  padding: 3px 0px 3px 20px;
  font-size: 1.5em;
  color: #909399;
  background-color: rgb(32, 65, 105);
}
</style>
