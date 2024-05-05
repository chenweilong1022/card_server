<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="phone" placeholder="手机号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.endTime"
          type="date"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="时间"
        />
      </el-form-item>
      <el-form-item>
        <el-select v-model="exportStatus" placeholder="导出状态" clearable>
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select v-model="expireTimeStatus" placeholder="时间筛选" clearable>
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="groupId" :placeholder="$t('分组')" clearable>
          <el-option
            v-for="item in dataListGroup"
            :key="item.id"
            :label="item.groupName"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="getDataList()">{{$t("查询")}}</el-button>
        <el-button @click="exportTxt()">导出</el-button>
        <el-button @click="refreshPhoneHandler()">更新手机号</el-button>
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
        prop="phone"
        header-align="center"
        align="center"
        :label="$t('手机号')">
      </el-table-column>
      <el-table-column
        prop="iccid"
        header-align="center"
        align="center"
        :label="$t('卡的iccid')">
      </el-table-column>
      <el-table-column
        prop="groupName"
        header-align="center"
        align="center"
        label="分组名称">
      </el-table-column>
      <el-table-column
        prop="deviceNumber"
        header-align="center"
        align="center"
        label="设备编号">
      </el-table-column>
      <el-table-column
        prop="ussdMsg"
        header-align="center"
        align="center"
        label="拨号信息">
      </el-table-column>
      <el-table-column
        prop="expireTime"
        header-align="center"
        align="center"
        label="到期时间">
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
  import AddOrUpdate from './cdiccidphone-add-or-update'
  export default {
    data () {
      return {
        options: [
          {
            value: 1,
            label: '未导出'
          },
          {
            value: 2,
            label: '已导出'
          }
        ],
        options1: [
          {
            value: 1,
            label: '有时间'
          },
          {
            value: 2,
            label: '没有时间'
          }
        ],
        dataForm: {
          key: '',
          endTime: null
        },
        dataList: [],
        dataListGroup: [],
        pageIndex: 1,
        exportStatus: 1,
        expireTimeStatus: 1,
        groupId: null,
        phone: null,
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
      this.getGroupDataList()
    },
    methods: {
      exportTxt () {
        if (!this.dataForm.endTime) {
          if (1 === this.expireTimeStatus) {
            this.$message.error("导出文件时间不能为空")
            return;
          }
        }
        window.open(this.$http.adornUrl(`/ltt/cdiccidphone/exportTxt?exportStatus=${this.exportStatus}&token=${this.$cookie.get('token')}&endTime=${this.dataForm.endTime ? this.dataForm.endTime : ''}&expireTimeStatus=${this.expireTimeStatus}`));
      },

      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/ltt/cdiccidphone/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'exportStatus': this.exportStatus,
            'expireTimeStatus': this.expireTimeStatus,
            'groupId': this.groupId,
            'phone': this.phone,
            'endTime': this.dataForm.endTime
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
      // 获取数据列表
      refreshPhoneHandler () {
        this.$http({
          url: this.$http.adornUrl('/ltt/cdiccidphone/refreshPhone'),
          method: 'get'
        }).then(({data}) => {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500
          })
        })
      },
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
            url: this.$http.adornUrl('/ltt/cdiccidphone/delete'),
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
