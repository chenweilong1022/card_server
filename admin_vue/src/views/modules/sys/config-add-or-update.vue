<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-form-item label="参数名">
        <el-select v-model="type" placeholder="类型" clearable>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="参数名" prop="paramKey" v-if="type === 1">
        <el-input v-model="dataForm.paramKey" placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item label="参数值" prop="paramValue"  v-if="type === 1">
        <el-input v-model="dataForm.paramValue" placeholder="参数值"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark"  v-if="type === 1">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>

      <el-form-item label="用户id" prop="userId" v-if="type === 2">
        <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
      </el-form-item>
      <el-form-item label="项目id" prop="projectId"  v-if="type === 2">
        <el-input v-model="dataForm.projectId" placeholder="项目id"></el-input>
      </el-form-item>
      <el-form-item label="手机号前缀" prop="phonePre"  v-if="type === 2">
        <el-input v-model="dataForm.phonePre" placeholder="手机号前缀"></el-input>
      </el-form-item>
      <el-form-item label="接码平台接口" prop="codeApiUrl"  v-if="type === 2">
        <el-input v-model="dataForm.codeApiUrl" placeholder="备注"></el-input>
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
        type: 2,
        visible: false,
        options: [
          {
            value: 1,
            label: '云存储'
          },
          {
            value: 2,
            label: '项目配置'
          }
        ],
        dataForm: {
          id: 0,
          paramKey: '',
          paramValue: '',
          userId: '',
          projectId: '',
          phonePre: '',
          codeApiUrl: '',
          remark: ''
        },
        dataRule: {
          paramKey: [
            { required: true, message: '参数名不能为空', trigger: 'blur' }
          ],
          paramValue: [
            { required: true, message: '参数值不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/sys/config/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.paramKey = data.config.paramKey
                this.dataForm.paramValue = data.config.paramValue
                this.dataForm.remark = data.config.remark
                this.dataForm.userId = data.config.userId
                this.dataForm.projectId = data.config.projectId
                this.dataForm.phonePre = data.config.phonePre
                this.dataForm.codeApiUrl = data.config.codeApiUrl
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
              url: this.$http.adornUrl(`/sys/config/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'paramKey': this.dataForm.paramKey,
                'paramValue': this.dataForm.paramValue,
                'userId': this.dataForm.userId,
                'projectId': this.dataForm.projectId,
                'phonePre': this.dataForm.phonePre,
                'codeApiUrl': this.dataForm.codeApiUrl,
                'type': this.type,
                'remark': this.dataForm.remark
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
