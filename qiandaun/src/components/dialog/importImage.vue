<!-- 导入相片弹窗 -->
<template>
  <div>
    <el-dialog title="导入相片" :visible.sync="data.show">
      <div>
        <span>
          请将警员的图片放到一个文件夹中，图片名称为对应警员的身份证，然后将该文件夹压缩成.zip压缩包，点击“上传”按钮即可。
        </span>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-upload
          class="avatar-uploader"
          :action="importimage"
          :with-credentials="true"
          :show-file-list="false"
          :on-progress="uploadProgress"
          :on-success="uploadCb">
          <el-button type="primary">导入相片</el-button>
        </el-upload>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Config from '../../config/common.json'

export default {
  name: 'ImportImage',
  components: {
    
  },
  props: {
    data: {
      type: Object
    }
  },
  data() {
    return {
      importimage: Config.server + '/user/importimage',
    }
  },
  computed: {
    
  },
  methods: {
    // 导入进度
    uploadProgress(event) {
      console.log(event)
      this.$emit('uploadProgress')
      this.$message({
        showClose: true,
        message: '上传中……',
        type: 'warning',
        duration: 0
      });
    },
    // 导入成功回调
    uploadCb(res) {
      this.$message.closeAll()
      console.log(res)
      this.$emit('uploadCb')
      if (res.success) {
        this.$message({
          showClose: true,
          message: '导入成功',
          type: 'success'
        });
        this.data.show = false
        this.$emit('success')
      } else {
        this.$message({
          showClose: true,
          message: res.message,
          type: 'error'
        }); 
      }
    },
  },
  mounted() {
    
  }
}
</script>

<style scoped>

</style>
