<template>
  <el-dialog
    :title="!dataForm.id ? '更新app' : '更新app'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-form-item label="初始化类型" prop="type">
        <el-radio-group v-model="type">
          <el-radio label="1" size="large">从头开始</el-radio>
          <el-radio label="2" size="large">读取历史</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="ussd" prop="ussd">
<!--        <el-input v-model="dataForm.ussd" placeholder="ussd"></el-input>-->
        <el-select v-model="dataForm.ussd" placeholder="工作流程" clearable>
          <el-option
            v-for="item in bh"
            :key="item"
            :label="item"
            :value="item">
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
        type: '2',
        visible: false,
        dataForm: {
          ids: null,
          type: '2',
          ussd: ''
        },
        bh: [
          "*833#",
          "*102*9#"
        ],
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
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cddevices/initCard`),
              method: 'post',
              data: this.$http.adornData({
                'ids': this.dataForm.ids || undefined,
                'type': this.type,
                'ussd': this.dataForm.ussd
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
