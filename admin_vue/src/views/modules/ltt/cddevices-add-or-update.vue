<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="iccid">
      <el-input v-model="dataForm.iccid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="online">
      <el-input v-model="dataForm.online" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="workType">
      <el-input v-model="dataForm.workType" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="deleteFlag">
      <el-input v-model="dataForm.deleteFlag" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="packageVersion">
      <el-input v-model="dataForm.packageVersion" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="number">
      <el-input v-model="dataForm.number" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          iccid: '',
          online: '',
          workType: '',
          deleteFlag: '',
          createTime: '',
          packageVersion: '',
          number: ''
        },
        dataRule: {
          iccid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          online: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          workType: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          deleteFlag: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          packageVersion: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          number: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.iccid = data.cddevices.iccid
                this.dataForm.online = data.cddevices.online
                this.dataForm.workType = data.cddevices.workType
                this.dataForm.deleteFlag = data.cddevices.deleteFlag
                this.dataForm.createTime = data.cddevices.createTime
                this.dataForm.packageVersion = data.cddevices.packageVersion
                this.dataForm.number = data.cddevices.number
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'iccid': this.dataForm.iccid,
                'online': this.dataForm.online,
                'workType': this.dataForm.workType,
                'deleteFlag': this.dataForm.deleteFlag,
                'createTime': this.dataForm.createTime,
                'packageVersion': this.dataForm.packageVersion,
                'number': this.dataForm.number
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
