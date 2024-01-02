<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : `修改-${groupName}`"
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

      <el-form-item label="平台" prop="platform" v-if="type === 2">
        <el-select v-model="platform" placeholder="平台" clearable @change="platformHandler">
          <el-option
            v-for="item in platformOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接码平台接口" prop="codeApiUrl"  v-if="type === 2">
<!--        <el-input v-model="dataForm.codeApiUrl" placeholder="备注"></el-input>-->
        <el-select v-model="dataForm.projectId" placeholder="平台" clearable @change="platformHandler">
          <el-option
            v-for="item in projectDataList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="项目id" prop="projectId"  v-if="type === 2">
        <el-select v-model="dataForm.projectId" placeholder="平台" clearable @change="platformHandler">
          <el-option
            v-for="item in projectDataList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="手机号前缀" prop="phonePre"  v-if="type === 2">
        <el-input v-model="dataForm.phonePre" placeholder="手机号前缀"></el-input>
      </el-form-item>
      <el-form-item label="用户id" prop="userId" v-if="type === 2">
        <el-input v-model="dataForm.userId" placeholder="用户id" readonly></el-input>
      </el-form-item>
      <el-form-item label="取码类型" prop="codeAcquisitionType" v-if="type === 2">
        <el-select v-model="codeAcquisitionType" placeholder="取码类型" clearable>
          <el-option
            v-for="item in codeAcquisitionTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

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
        codeAcquisitionType: null,
        type: 2,
        platform: 1,
        visible: false,
        projectDataList: [],
        codeAcquisitionTypeOptions: [
          {
            value: 1,
            label: '指定项目'
          },
          {
            value: 2,
            label: '挂机模式'
          },
          {
            value: 3,
            label: '自己注册'
          }
        ],
        platformOptions: [
          {
            value: 1,
            label: '火狐狸',
            url: 'https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99'
          },
          {
            value: 2,
            label: '沃码',
            url: 'https://www.worldcode.win/ksapi.ashx?key=066131D94DADB4B3'
          }
        ],
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
        groupName: '',
        dataForm: {
          id: 0,
          paramKey: '',
          paramValue: '',
          userId: '',
          platform: null,
          codeAcquisitionType: null,
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
      platformHandler (item) {
        for (let platformOption of this.platformOptions) {
          if (platformOption.value === item) {
            this.dataForm.codeApiUrl = platformOption.url
          }
        }
      },
      init (id,groupName) {
        this.dataForm.id = id || 0
        this.groupName =groupName
        this.visible = true
        this.projectList()
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
                this.codeAcquisitionType = data.config.codeAcquisitionType
              }
            })
          }
        })
      },
      projectList () {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdproject/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': 1,
            'limit': 100
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.projectDataList = data.page.list
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
                'platform': this.platform,
                'codeAcquisitionType': this.codeAcquisitionType,
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
