<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="短信" prop="code">
      <el-input v-model="dataForm.code" placeholder="短信"></el-input>
    </el-form-item>
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="项目id" prop="projectId">
      <el-input v-model="dataForm.projectId" placeholder="项目id"></el-input>
    </el-form-item>
    <el-form-item label="设备id" prop="deviceId">
      <el-input v-model="dataForm.deviceId" placeholder="设备id"></el-input>
    </el-form-item>
    <el-form-item label="锁" prop="lock">
      <el-input v-model="dataForm.lock" placeholder="锁"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
    </el-form-item>
    <el-form-item label="卡的iccid" prop="iccid">
      <el-input v-model="dataForm.iccid" placeholder="卡的iccid"></el-input>
    </el-form-item>
    <el-form-item label="删除标志" prop="deleteFlag">
      <el-input v-model="dataForm.deleteFlag" placeholder="删除标志"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
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
          code: '',
          userId: '',
          projectId: '',
          deviceId: '',
          lock: '',
          phone: '',
          iccid: '',
          deleteFlag: '',
          createTime: ''
        },
        dataRule: {
          code: [
            { required: true, message: '短信不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          projectId: [
            { required: true, message: '项目id不能为空', trigger: 'blur' }
          ],
          deviceId: [
            { required: true, message: '设备id不能为空', trigger: 'blur' }
          ],
          lock: [
            { required: true, message: '锁不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' }
          ],
          iccid: [
            { required: true, message: '卡的iccid不能为空', trigger: 'blur' }
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
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cdcardlock/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.code = data.cdcardlock.code
                this.dataForm.userId = data.cdcardlock.userId
                this.dataForm.projectId = data.cdcardlock.projectId
                this.dataForm.deviceId = data.cdcardlock.deviceId
                this.dataForm.lock = data.cdcardlock.lock
                this.dataForm.phone = data.cdcardlock.phone
                this.dataForm.iccid = data.cdcardlock.iccid
                this.dataForm.deleteFlag = data.cdcardlock.deleteFlag
                this.dataForm.createTime = data.cdcardlock.createTime
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
              url: this.$http.adornUrl(`/ltt/cdcardlock/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'code': this.dataForm.code,
                'userId': this.dataForm.userId,
                'projectId': this.dataForm.projectId,
                'deviceId': this.dataForm.deviceId,
                'lock': this.dataForm.lock,
                'phone': this.dataForm.phone,
                'iccid': this.dataForm.iccid,
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
