<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.fq" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.packageVersion" placeholder="版本号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.phone" placeholder="手机号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.iccid" placeholder="设备编码" clearable></el-input>
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
        <el-select v-model="groupId" placeholder="分组" clearable>
          <el-option
            v-for="item in dataListGroup"
            :key="item.id"
            :label="item.groupName"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="getTimeType" placeholder="取码时间筛选" clearable>
          <el-option
            v-for="item in getTimeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="sortIndex" placeholder="码排序" clearable>
          <el-option
            v-for="item in sortIndexOptions"
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
        <el-button type="primary" @click="withBlack()" :disabled="dataListSelections.length <= 0">号码拉黑</el-button>
        <el-button type="primary" @click="getCode()" :disabled="dataListSelections.length <= 0">项目切换</el-button>
        <el-button type="primary" @click="rebootHandler()" :disabled="dataListSelections.length <= 0">批量重启</el-button>
        <el-button type="primary" @click="updateBatchHandler(null,1)" :disabled="dataListSelections.length <= 0">批量闲置</el-button>
        <el-button type="primary" @click="updateBatchHandler(null,3)" :disabled="dataListSelections.length <= 0">批量工作</el-button>
        <el-button type="primary" @click="releaseMobileAllHandler()" :disabled="dataListSelections.length <= 0">释放卡</el-button>
        <el-button type="primary" @click="cddevicesGroupChangeHandle()" :disabled="dataListSelections.length <= 0">分组</el-button>
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
        prop="number"
        header-align="center"
        align="center"
        label="编号">
      </el-table-column>
      <el-table-column
        prop="deviceId"
        header-align="center"
        align="center"
        label="设备id">
      </el-table-column>
      <el-table-column
        prop="packageVersion"
        header-align="center"
        align="center"
        label="版本号">
      </el-table-column>

      <el-table-column
        prop="phoneGetTime"
        header-align="center"
        align="center"
        label="取码时间">
      </el-table-column>

      <el-table-column
        prop="workFq"
        header-align="center"
        align="center"
        label="当前板">
        <template slot-scope="scope">
          {{scope.row.heartbeatRequest == null ? 1 : scope.row.heartbeatRequest.workFq}}
        </template>
      </el-table-column>
      <el-table-column
        prop="fq"
        header-align="center"
        align="center"
        label="板子数">
        <template slot-scope="scope">
          {{scope.row.heartbeatRequest == null ? 1 : scope.row.heartbeatRequest.fq}}
        </template>
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
        prop="userId"
        header-align="center"
        align="center"
        label="用户id">
      </el-table-column>

      <el-table-column
        prop="projectId"
        header-align="center"
        align="center"
        label="项目id">
      </el-table-column>

      <el-table-column
        prop="phone"
        header-align="center"
        align="center"
        label="phone">
      </el-table-column>
      <el-table-column
        prop="iccid"
        header-align="center"
        align="center"
        label="iccid">
      </el-table-column>

      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="initHandle(scope.row.id)">初始化</el-button>
          <el-button type="text" size="small" @click="rebootHandler(scope.row.id)">重启</el-button>
          <el-button type="text" size="small" @click="updateBatchHandler(scope.row.id,1)">闲置</el-button>
          <el-button type="text" size="small" @click="updateBatchHandler(scope.row.id,3)">工作</el-button>
          <el-button type="text" size="small" @click="cddevicesChangeCardHandle(scope.row.id)">切换卡</el-button>
          <el-button type="text" size="small" @click="cardListVisibleHandle(scope.row.deviceId)">卡详情</el-button>
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
    <cddevices-init v-if="cddevicesInitVisible" ref="cddevicesInit" @refreshDataList="getDataList"></cddevices-init>

    <cddevices-group-change v-if="cddevicesGroupChangeVisible" ref="cddevicesGroupChange" @refreshDataList="getDataList"></cddevices-group-change>
    <card-list v-if="cardListVisible" ref="cardList" @refreshDataList="getDataList"></card-list>
  </div>
</template>

<script>
import AddOrUpdate from './cddevices-add-or-update'
import CddevicesChangeCard from './cddevices-change-card'
import CddevicesUpdateApp from './cddevices-update-app'
import CddevicesGroupChange from './cddevices-group-change'
import CddevicesInit from './cddevices-init.vue'
import CardList from './cddevices-card-list.vue'
export default {
  data () {
    return {
      online: 0,
      getTimeType: null,
      sortIndex: null,
      workType: null,
      groupId: null,
      dataForm: {
        key: '',
        online: null,
        workType: null,
        iccid: null,
        phone: null,
        fq: null,
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
      getTimeOptions: [
        {
          value: 1,
          label: '有取码时间'
        },
        {
          value: 2,
          label: '无取码时间'
        }
      ],
      sortIndexOptions: [
        {
          value: 1,
          label: '当前板排序'
        }
      ],
      dataList: [],
      dataListGroup: [],
      pageIndex: 1,
      pageSize: 100,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      cddevicesChangeCardVisible: false,
      cardListVisible: false,
      cddevicesInitVisible: false,
      cddevicesGroupChangeVisible: false,
      cddevicesUpdateAppVisible: false
    }
  },
  components: {
    AddOrUpdate,
    CddevicesChangeCard,
    CddevicesUpdateApp,
    CddevicesGroupChange,
    CddevicesInit,
    CardList
  },
  activated () {
    this.getDataList()
    this.getGroupDataList()
  },
  methods: {
    // 获取数据列表
    getGroupDataList () {
      this.$http({
        url: this.$http.adornUrl('/ltt/cdcardgroup/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': 1,
          'limit': 100
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataListGroup = data.page.list
        } else {
          this.dataListGroup = []
        }
      })
    },
    updateBatchHandler (id, workType) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '修改状态' : '批量修改状态'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/updateBatch'),
          method: 'post',
          data: {
            'ids': ids,
            'workType': workType
          }
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
    releaseMobileAllHandler (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '释放' : '批量释放状态'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdcardlock/releaseMobileAll'),
          method: 'post',
          data: {
            'ids': ids,
            'token': '545148dc498842ca8cea980b1a677b27'
          }
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
    cddevicesGroupChangeHandle (id) {
      this.cddevicesGroupChangeVisible = true
      this.$nextTick(() => {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$refs.cddevicesGroupChange.init(ids)
      })
    },
    initHandle (id) {
      this.cddevicesInitVisible = true
      this.$nextTick(() => {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$refs.cddevicesInit.init(ids)
      })
    },
    cardListVisibleHandle (deviceId) {
      this.cardListVisible = true
      this.$nextTick(() => {
        this.$refs.cardList.init(deviceId)
      })
    },
    getCode (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '重置接码' : '批量重置接码'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/getCode'),
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
    withBlack (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/withBlack'),
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
    phoneDeleteAllHandle3 (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/phoneDeleteAll'),
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
    phoneDeleteAllHandle32 (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.id
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '初始化' : '批量初始化'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/ltt/cddevices/phoneDeleteAll2'),
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
          'groupId': this.groupId,
          'workType': this.workType,
          'iccid': this.dataForm.iccid,
          'phone': this.dataForm.phone,
          'packageVersion': this.dataForm.packageVersion,
          'key': this.dataForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          if (1 === this.getTimeType) {
            this.dataList = this.dataList.filter(x => x.phoneGetTime);
          }else if (2 === this.getTimeType) {
            this.dataList = this.dataList.filter(x => !x.phoneGetTime);
          }

          if (1 === this.sortIndex) {
            this.dataList = this.dataList.sort(function (a, b) {
              var an = a.heartbeatRequest == null ? 1 :a.heartbeatRequest.workFq;
              var bn = b.heartbeatRequest == null ? 1 :b.heartbeatRequest.workFq;
              return an - bn;
            });
          }

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
