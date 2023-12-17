<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

    <el-form-item label="分组名称">
      <el-select v-model="groupId" placeholder="分组名称" clearable>
        <el-option
          v-for="item in dataList"
          :key="item.id"
          :label="item.groupName"
          :value="item.id">
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
        groupId: null,
        visible: false,
        dataList: [],
        dataForm: {
          ids: null
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
      // 获取数据列表
      getDataList () {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdcardgroup/list'),
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
      init (ids) {
        this.dataForm.ids = ids || 0
        this.visible = true
        this.getDataList();
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/changeGroup`),
              method: 'post',
              data: this.$http.adornData({
                'ids': this.dataForm.ids || undefined,
                'groupId': this.groupId
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
