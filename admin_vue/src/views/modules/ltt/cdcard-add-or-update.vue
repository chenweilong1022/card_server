<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item :label="$t('设备id')" prop="deviceId">
      <el-input v-model="dataForm.deviceId" placeholder="设备id"></el-input>
    </el-form-item>
    <el-form-item :label="$t('板子')" prop="boardIndexed">
      <el-input v-model="dataForm.boardIndexed" placeholder="板子"></el-input>
    </el-form-item>
    <el-form-item :label="$t('卡下标')" prop="indexed">
      <el-input v-model="dataForm.indexed" placeholder="卡下标"></el-input>
    </el-form-item>
    <el-form-item :label="$t('手机号')" prop="phone">
      <el-input v-model="dataForm.phone" :placeholder="$t('手机号')"></el-input>
    </el-form-item>
    <el-form-item :label="$t('卡的iccid')" prop="iccid">
      <el-input v-model="dataForm.iccid" placeholder="卡的iccid"></el-input>
    </el-form-item>
    <el-form-item label="删除标志" prop="deleteFlag">
      <el-input v-model="dataForm.deleteFlag" placeholder="删除标志"></el-input>
    </el-form-item>
    <el-form-item :label="$t('创建时间')" prop="createTime">
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
          deviceId: '',
          boardIndexed: '',
          indexed: '',
          phone: '',
          iccid: '',
          deleteFlag: '',
          createTime: ''
        },
        dataRule: {
          deviceId: [
            { required: true, message: '设备id不能为空', trigger: 'blur' }
          ],
          boardIndexed: [
            { required: true, message: '板子不能为空', trigger: 'blur' }
          ],
          indexed: [
            { required: true, message: '卡下标不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/ltt/cdcard/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.deviceId = data.cdcard.deviceId
                this.dataForm.boardIndexed = data.cdcard.boardIndexed
                this.dataForm.indexed = data.cdcard.indexed
                this.dataForm.phone = data.cdcard.phone
                this.dataForm.iccid = data.cdcard.iccid
                this.dataForm.deleteFlag = data.cdcard.deleteFlag
                this.dataForm.createTime = data.cdcard.createTime
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
              url: this.$http.adornUrl(`/ltt/cdcard/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'deviceId': this.dataForm.deviceId,
                'boardIndexed': this.dataForm.boardIndexed,
                'indexed': this.dataForm.indexed,
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
