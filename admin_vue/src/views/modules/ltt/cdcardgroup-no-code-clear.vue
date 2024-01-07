<template>
  <el-dialog
    :title="!dataForm.id ? '未回码清空' : '未回码清空'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="分组名称">
        <el-select v-model="projectId" placeholder="分组名称" clearable>
          <el-option
            v-for="item in dataList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit(1)">未回码清空</el-button>
      <el-button type="primary" @click="dataFormSubmit(2)">全部清空</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        projectId: null,
        dataList: [],
        dataForm: {
          id: 0,
          groupName: '',
          paramValue: '',
          deleteFlag: '',
          createTime: ''
        },
        dataRule: {
          groupName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          paramValue: [
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
      getDataList () {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdproject/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': 1,
            'limit': 100
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
          } else {
            this.dataList = []
          }
        })
      },
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.getDataList();
      },
      // 表单提交
      dataFormSubmit (type) {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cdprojectsmsrecord/noClearReplyCode`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id,
                'projectId': this.projectId,
                'clearType': type
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
