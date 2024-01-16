<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
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
    <el-form-item label="拨号信息" prop="ussdMsg">
      <el-input v-model="dataForm.ussdMsg" placeholder="拨号信息"></el-input>
    </el-form-item>
    <el-form-item label="到期时间" prop="expireTime">
      <el-input v-model="dataForm.expireTime" placeholder="到期时间"></el-input>
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
          phone: '',
          iccid: '',
          deleteFlag: '',
          createTime: '',
          ussdMsg: '',
          expireTime: ''
        },
        dataRule: {
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
          ],
          ussdMsg: [
            { required: true, message: '拨号信息不能为空', trigger: 'blur' }
          ],
          expireTime: [
            { required: true, message: '到期时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/ltt/cdiccidphone/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.phone = data.cdiccidphone.phone
                this.dataForm.iccid = data.cdiccidphone.iccid
                this.dataForm.deleteFlag = data.cdiccidphone.deleteFlag
                this.dataForm.createTime = data.cdiccidphone.createTime
                this.dataForm.ussdMsg = data.cdiccidphone.ussdMsg
                this.dataForm.expireTime = data.cdiccidphone.expireTime
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
              url: this.$http.adornUrl(`/ltt/cdiccidphone/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'phone': this.dataForm.phone,
                'iccid': this.dataForm.iccid,
                'deleteFlag': this.dataForm.deleteFlag,
                'createTime': this.dataForm.createTime,
                'ussdMsg': this.dataForm.ussdMsg,
                'expireTime': this.dataForm.expireTime
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
