<!-- 面包屑 -->
<template>
  <div>
    <!-- <i class="el-icon-s-fold"></i> -->
    <el-breadcrumb class="bread" separator-class="el-icon-arrow-right">
      <el-breadcrumb-item v-for="(item, index) in breadList" :key="'bread-' + index">{{item}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
import menuConfig from '../config/menu.json'

export default {
  name: 'Bread',
  data() {
    return {
      breadList: []
    }
  },
  watch: {
    $route() {
      this.changeBread()
    }
  },
  mounted() {
    this.changeBread()
  },
  methods: {
    changeBread() {
      let url = this.$route.path.split('/')
      this.breadList = []
      menuConfig.forEach(menu => {
        if (menu.code == url[1]) {
          this.breadList.push(menu.name)
        }
        menu.children && menu.children.forEach(children => {
          if (children.code == url[2]) {
            this.breadList.push(children.name)
          }
        })
      });
    }
  }
}
</script>

<style scoped>

</style>
