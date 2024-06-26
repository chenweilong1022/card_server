<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">{{$t("查询")}}</el-button>
        <el-button v-if="isAuth('ltt:cdcardgroup:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('ltt:cdcardgroup:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        label="id">
      </el-table-column>
      <el-table-column
        prop="groupName"
        header-align="center"
        align="center"
        label="分组名称">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        label="取码类型">
        <template slot-scope="scope">
          {{getTypeStr(scope.row.projectWorkEntity)}}
        </template>
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        label="账号">
        <template slot-scope="scope">
          {{getLabelStr(scope.row.projectWorkEntity)}}
        </template>
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        label="卡数量">
        <template slot-scope="scope">
          {{scope.row.groupByDeviceIdVO ? scope.row.groupByDeviceIdVO.initSuccessNumber : ""}}
        </template>
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
          <el-button type="text" size="small" @click="configAddOrUpdateHandle(scope.row.id,scope.row.groupName)">配置</el-button>
<!--          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>-->
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          <el-button type="text" size="small" @click="clearFirefoxHandle(scope.row.id)">火狐狸清空</el-button>
          <el-button type="text" size="small" @click="generateStatistics(scope.row.id)">生成报表</el-button>
          <el-button type="text" size="small" @click="noCodeClearHandle(scope.row.id)">未回码清空</el-button>
          <el-button type="text" size="small" @click="exportPhoneHandler(scope.row.id)">手机号导出</el-button>
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
    <config-add-or-update  v-if="configAddOrUpdateVisible" ref="configAddOrUpdate" @refreshDataList="getDataList"></config-add-or-update>
    <no-code-clear v-if="noCodeClearVisible" ref="noCodeClear" @refreshDataList="getDataList"></no-code-clear>
  </div>
</template>

<script>
  import AddOrUpdate from './cdcardgroup-add-or-update'
  import NoCodeClear from './cdcardgroup-no-code-clear'
  import ConfigAddOrUpdate from '../sys/config-add-or-update.vue'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        codeAcquisitionTypes: [
          {
            type: 1,
            typeStr: "指定项目"
          },
          {
            type: 2,
            typeStr: "挂机模式"
          },
          {
            type: 3,
            typeStr: "自己注册"
          }
        ],
        codeApiUrlDataListOptions: [
          {
            value: 1,
            label: '挂机(76082377BDE44F99)',
            url: 'https://ks.firefox.fun/ksapi.ashx?key=76082377BDE44F99'
          },
          {
            value: 2,
            label: 'TG(A379FA5332B2FB70)',
            url: 'https://ks.firefox.fun/ksapi.ashx?key=A379FA5332B2FB70'
          },
          {
            value: 3,
            label: 'WS(2EEE96B60EA080A2)',
            url: 'https://ks.firefox.fun/ksapi.ashx?key=2EEE96B60EA080A2'
          }
        ],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        configAddOrUpdateVisible: false,
        noCodeClearVisible: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate,
      ConfigAddOrUpdate,
      NoCodeClear
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/ltt/cdcardgroup/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
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
      getLabelStr (entity) {
        if (entity) {
          for (let i = 0; i < this.codeApiUrlDataListOptions.length; i++) {
            let codeApiUrlDataListOption = this.codeApiUrlDataListOptions[i]
            if (entity.codeApiUrl === codeApiUrlDataListOption.url) {
              return codeApiUrlDataListOption.label;
            }
          }
        }
        return "";
      },
      getTypeStr(entity) {
        if (entity) {
          for (let i = 0; i < this.codeAcquisitionTypes.length; i++) {
            let codeAcquisitionType = this.codeAcquisitionTypes[i]
            if (entity.codeAcquisitionType === codeAcquisitionType.type) {
              return codeAcquisitionType.typeStr;
            }
          }
        }
        return "";
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
      exportPhoneHandler (id) {
        window.open(this.$http.adornUrl(`/ltt/cdcardgroup/exportPhoneTxt?id=${id}&token=${this.$cookie.get('token')}`));
      },
      // 新增 / 修改
      noCodeClearHandle (id) {
        this.noCodeClearVisible = true
        this.$nextTick(() => {
          this.$refs.noCodeClear.init(id)
        })
      },
      // 配置修改
      configAddOrUpdateHandle (id,name) {
        this.configAddOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.configAddOrUpdate.init(id,name)
        })
      },
      // 清空火狐狸
      clearFirefoxHandle (id) {
        this.$confirm(`确定对[id=${id}]进行[${id ? '清空火狐狸' : '批量清空火狐狸'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/ltt/cddevices/phoneDeleteAll2'),
            method: 'post',
            data: {
              id:id
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
            url: this.$http.adornUrl('/ltt/cdcardgroup/delete'),
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
      generateStatistics (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '生成报表' : '批量生成报表'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/ltt/cdprojectstatistics/generateStatistics'),
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
