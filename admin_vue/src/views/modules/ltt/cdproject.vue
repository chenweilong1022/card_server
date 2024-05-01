<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.name" placeholder="项目名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.countryId">
          <el-option :label="item.name" v-for="item in countryList" :value="item.id">
            {{item.name}}
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="dataForm.auditStatus">
          <el-option :label="item.value" v-for="item in auditStatusList" :value="item.key">
            {{item.value}}
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="getDataList()">{{$t("查询")}}</el-button>
        <el-button v-if="isAuth('ltt:cdproject:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('ltt:cdproject:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
        <el-button v-if="isAuth('ltt:cdproject:delete')" type="danger" @click="auditHandle(null,3,'批量审核通过')" :disabled="dataListSelections.length <= 0">批量通过</el-button>
        <el-button v-if="isAuth('ltt:cdproject:delete')" type="danger" @click="auditHandle(null,2,'批量审核拒绝')" :disabled="dataListSelections.length <= 0">批量拒绝</el-button>
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
        :label="$t('主键')">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="项目名称">
      </el-table-column>
      <el-table-column
        prop="countryName"
        header-align="center"
        align="center"
        label="国家">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="价格">
      </el-table-column>
      <el-table-column
        prop="auditStatusStr"
        header-align="center"
        align="center"
        label="审核状态">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        :label="$t('创建时间')">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          <el-button type="text" size="small" @click="auditHandle(scope.row.id,3,'审核通过')">通过</el-button>
          <el-button type="text" size="small" @click="auditHandle(scope.row.id,2,'审核通过')">拒绝</el-button>
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
  </div>
</template>

<script>
  import AddOrUpdate from './cdproject-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          name: '',
          auditStatus: null,
          countryId: null
        },
        dataList: [],
        countryList: [],
        auditStatusList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
      this.getCountryList()
      this.getAuditStatusList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/ltt/cdproject/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'countryId': this.dataForm.countryId,
            'auditStatus': this.dataForm.auditStatus,
            'name': this.dataForm.name
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
      getCountryList () {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdcountry/list'),
          method: 'get',
          params: this.$http.adornParams({
            'limit': 200
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.countryList = data.page.list
          }
        })
      },
      getAuditStatusList () {
        this.$http({
          url: this.$http.adornUrl('/app/enums/auditStatus'),
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log(data)
            this.auditStatusList = data.data
          }
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
            url: this.$http.adornUrl('/ltt/cdproject/delete'),
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
      auditHandle (id, auditStatus, auditStatusStr) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? auditStatusStr : '批量' + auditStatusStr}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: id ? this.$http.adornUrl('/ltt/cdproject/audit') : this.$http.adornUrl('/ltt/cdproject/auditAll'),
            method: 'post',
            data: {
              id: id,
              ids: ids,
              auditStatus: auditStatus
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
      }
    }
  }
</script>
