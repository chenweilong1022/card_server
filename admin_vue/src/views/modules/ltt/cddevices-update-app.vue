<template>
  <el-dialog
    :title="!dataForm.id ? '更新app' : '更新app'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="apk下载路径" prop="httpUrl">
      <el-input v-model="dataForm.httpUrl" placeholder="apk下载路径"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit(1)">更新选择设备</el-button>
      <el-button type="primary" @click="dataFormSubmit(2)">更新全部设备</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          ids: null,
          iccid: '',
          online: '',
          deleteFlag: '',
          createTime: ''
        },
        dataRule: {
          iccid: [
            { required: true, message: '设备id不能为空', trigger: 'blur' }
          ],
          online: [
            { required: true, message: '是否在线不能为空', trigger: 'blur' }
          ],
          deleteFlag: [
            { required: true, message: '删除标志不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (ids) {
        this.dataForm.ids = ids || []
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.ids) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.iccid = data.cdDevices.iccid
                this.dataForm.online = data.cdDevices.online
                this.dataForm.deleteFlag = data.cdDevices.deleteFlag
                this.dataForm.createTime = data.cdDevices.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit (type) {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/updateApp`),
              method: 'post',
              data: this.$http.adornData({
                'ids': this.dataForm.ids || undefined,
                'httpUrl': this.dataForm.httpUrl,
                'updateType': type
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
