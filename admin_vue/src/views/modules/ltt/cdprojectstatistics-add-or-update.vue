<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="projectName">
      <el-input v-model="dataForm.projectName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="projectId">
      <el-input v-model="dataForm.projectId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="totalCount">
      <el-input v-model="dataForm.totalCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="successCount">
      <el-input v-model="dataForm.successCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="errorCount">
      <el-input v-model="dataForm.errorCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="deleteFlag">
      <el-input v-model="dataForm.deleteFlag" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
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
          projectName: '',
          projectId: '',
          totalCount: '',
          successCount: '',
          errorCount: '',
          deleteFlag: '',
          createTime: ''
        },
        dataRule: {
          projectName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          projectId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          totalCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          successCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          errorCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          deleteFlag: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createTime: [
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
              url: this.$http.adornUrl(`/ltt/cdprojectstatistics/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.projectName = data.cdprojectstatistics.projectName
                this.dataForm.projectId = data.cdprojectstatistics.projectId
                this.dataForm.totalCount = data.cdprojectstatistics.totalCount
                this.dataForm.successCount = data.cdprojectstatistics.successCount
                this.dataForm.errorCount = data.cdprojectstatistics.errorCount
                this.dataForm.deleteFlag = data.cdprojectstatistics.deleteFlag
                this.dataForm.createTime = data.cdprojectstatistics.createTime
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
              url: this.$http.adornUrl(`/ltt/cdprojectstatistics/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'projectName': this.dataForm.projectName,
                'projectId': this.dataForm.projectId,
                'totalCount': this.dataForm.totalCount,
                'successCount': this.dataForm.successCount,
                'errorCount': this.dataForm.errorCount,
                'deleteFlag': this.dataForm.deleteFlag,
                'createTime': this.dataForm.createTime
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
