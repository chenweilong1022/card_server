<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.packageVersion" placeholder="版本号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="online" placeholder="在线" clearable>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="workType" placeholder="工作流程" clearable>
          <el-option
            v-for="item in workOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="cddevicesUpdateAppCardHandle()" :disabled="dataListSelections.length <= 0">app更新</el-button>
        <el-button type="primary" @click="initHandle()" :disabled="dataListSelections.length <= 0">批量初始化</el-button>
        <el-button type="primary" @click="initHandle2()" :disabled="dataListSelections.length <= 0">批量初始化2</el-button>
        <el-button type="primary" @click="initHandle3()" :disabled="dataListSelections.length <= 0">批量初始化3</el-button>
        <el-button type="primary" @click="rebootHandler()" :disabled="dataListSelections.length <= 0">批量重启</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="主键">
      </el-table-column>
      <el-table-column
        prop="iccid"
        header-align="center"
        align="center"
        label="设备id">
      </el-table-column>
      <el-table-column
        prop="number"
        header-align="center"
        align="center"
        label="编号">
      </el-table-column>
      <el-table-column
        prop="packageVersion"
        header-align="center"
        align="center"
        label="版本号">
      </el-table-column>
      <el-table-column
        prop="initSuccessNumber"
        header-align="center"
        align="center"
        label="初始化比例">
        <template slot-scope="scope">
          ({{scope.row.initSuccessNumber}}/{{scope.row.initTotalNumber}})
        </template>
      </el-table-column>
      <el-table-column
        prop="onlineStr"
        header-align="center"
        align="center"
        label="是否在线">
      </el-table-column>
      <el-table-column
        prop="workTypeStr"
        header-align="center"
        align="center"
        label="工作流程">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="initHandle(scope.row.id)">初始化</el-button>
          <el-button type="text" size="small" @click="initHandle2(scope.row.id)">初始化2</el-button>
          <el-button type="text" size="small" @click="rebootHandler(scope.row.id)">重启</el-button>
          <el-button type="text" size="small" @click="cddevicesChangeCardHandle(scope.row.id)">切换卡</el-button>
          <!--          <el-button type="text" size="small" @click="cddevicesUpdateAppCardHandle(scope.row.id)">app更新</el-button>-->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <cddevices-change-card v-if="cddevicesChangeCardVisible" ref="cddevicesChangeCard" @refreshDataList="getDataList"></cddevices-change-card>
    <cddevices-update-app  v-if="cddevicesUpdateAppVisible" ref="cddevicesUpdateApp" @refreshDataList="getDataList"></cddevices-update-app>
  </div>
</template>

<script>
import AddOrUpdate from './cddevices-add-or-update'
import CddevicesChangeCard from './cddevices-change-card'
import CddevicesUpdateApp from './cddevices-update-app'
export default {
  data () {
    return {
      online: null,
      workType: null,
      dataForm: {
        key: '',
        online: null,
        workType: null,
        packageVersion: null
      },
      options: [
        {
          value: 0,
          label: '在线'
        },
        {
          value: 1,
          label: '离线'
        }
      ],
      workOptions: [
        {
          value: 1,
          label: '闲置'
        },
        {
          value: 2,
          label: '初始化'
        },
        {
          value: 3,
          label: '接码'
        }
      ],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      cddevicesChangeCardVisible: false,
      cddevicesUpdateAppVisible: false
    }
  },
  components: {
    AddOrUpdate,
    CddevicesChangeCard,
    CddevicesUpdateApp
  },
  activated () {
    this.getDataList()
  },
  methods: {
    rebootHandler (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '重启' : '批量重启'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/reboot'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    initHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/initCard'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    initHandle2 (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/initCard2'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    initHandle3 (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/initCard3'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/ltt/cddevices/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'online': this.online,
          'workType': this.workType,
          'packageVersion': this.dataForm.packageVersion,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 新增 / 修改
    cddevicesChangeCardHandle (id) {
      this.cddevicesChangeCardVisible = true
      this.$nextTick(() => {
        this.$refs.cddevicesChangeCard.init(id)
      })
    },
    // 更新app
    cddevicesUpdateAppCardHandle () {
      this.cddevicesUpdateAppVisible = true
      this.$nextTick(() => {
        var ids = this.dataListSelections.map(item => {
          return item.id
        })
        this.$refs.cddevicesUpdateApp.init(ids)
      })
    },
    // 删除
    deleteHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    updateAppHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '更新' : '批量更新'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/updateApp'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>
