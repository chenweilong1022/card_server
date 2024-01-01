<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div v-for="(item,index) in list">
      <div style="margin: 0 auto">
        <el-row>
          <el-col :span="24" type="flex" justify="center" align="middle">
            <div style="text-align: center;color: red">卡区{{index*1+1}}</div>
          </el-col>
          <el-col :span="24" type="flex" justify="center" align="middle">
            <i style="text-align: center;color: red;margin: 0 auto" class="el-icon-caret-top"></i>
          </el-col>
        </el-row>
      </div>
      <el-row>
        <el-col :span="8" v-for="item2 in item" type="flex" justify="center" align="middle">
          <el-card v-if="item2.phone">
            <div >{{index*1+1}}-{{item2.indexed+1}}</div>
            <div >{{item2.phone ? item2.phone : '无卡'}}</div>
            <div >{{item2.iccid ? item2.iccid : '无卡'}}</div>
          </el-card>
          <el-card v-else style="background-color: #ffd400">
            <div >{{index*1+1}}-{{item2.indexed+1}}</div>
            <div >{{item2.phone ? item2.phone : '无卡'}}</div>
            <div >{{item2.iccid ? item2.iccid : '无卡'}}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        list: [],
        dataForm: {
          id: 0,
          deviceId: '',
          online: '',
          deleteFlag: '',
          createTime: ''
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
      init (deviceId) {
        this.dataForm.deviceId = deviceId
        this.visible = true
        this.$nextTick(() => {
          if (this.dataForm.deviceId) {
            this.$http({
              url: this.$http.adornUrl(`/ltt/cdcard/listByDevicesId`),
              method: 'post',
              data: this.$http.adornData({
                'deviceId': this.dataForm.deviceId
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.list = data.list;
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
              url: this.$http.adornUrl(`/ltt/cddevices/changeCard`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'iccid': this.dataForm.iccid,
                'boardIndexed': this.dataForm.boardIndexed,
                'indexed': this.dataForm.indexed
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
