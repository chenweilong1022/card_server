<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">{{$t("查询")}}</el-button>
        <el-button v-if="isAuth('ltt:lttuser:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('ltt:lttuser:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
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
        prop="nickname"
        header-align="center"
        align="center"
        label="昵称">
      </el-table-column>
      <el-table-column
        prop="sex"
        header-align="center"
        align="center"
        label="性别">
      </el-table-column>
      <el-table-column
        prop="sexDesc"
        header-align="center"
        align="center"
        label="性别描述">
      </el-table-column>
      <el-table-column
        prop="countryCode"
        header-align="center"
        align="center"
        label="区号">
      </el-table-column>
      <el-table-column
        prop="language"
        header-align="center"
        align="center"
        label="语言">
      </el-table-column>
      <el-table-column
        prop="country"
        header-align="center"
        align="center"
        label="国家">
      </el-table-column>
      <el-table-column
        prop="province"
        header-align="center"
        align="center"
        label="省">
      </el-table-column>
      <el-table-column
        prop="city"
        header-align="center"
        align="center"
        label="市">
      </el-table-column>
      <el-table-column
        prop="headimgUrl"
        header-align="center"
        align="center"
        label="微信头像">
      </el-table-column>
      <el-table-column
        prop="openid"
        header-align="center"
        align="center"
        label="openId">
      </el-table-column>
      <el-table-column
        prop="token"
        header-align="center"
        align="center"
        label="token">
      </el-table-column>
      <el-table-column
        prop="money"
        header-align="center"
        align="center"
        label="累计消费金额">
      </el-table-column>
      <el-table-column
        prop="number"
        header-align="center"
        align="center"
        label="累计使用次数">
      </el-table-column>
      <el-table-column
        prop="phoneNumber"
        header-align="center"
        align="center"
        label="用户绑定的手机号（国外手机号会有区号）">
      </el-table-column>
      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        label="没有区号的手机号">
      </el-table-column>
      <el-table-column
        prop="brand"
        header-align="center"
        align="center"
        label="手机品牌">
      </el-table-column>
      <el-table-column
        prop="model"
        header-align="center"
        align="center"
        label="手机型号">
      </el-table-column>
      <el-table-column
        prop="version"
        header-align="center"
        align="center"
        label="微信版本号">
      </el-table-column>
      <el-table-column
        prop="system"
        header-align="center"
        align="center"
        label="操作系统版本">
      </el-table-column>
      <el-table-column
        prop="platform"
        header-align="center"
        align="center"
        label="客户端平台">
      </el-table-column>
      <el-table-column
        prop="sdkVersion"
        header-align="center"
        align="center"
        label="客户端基础库版本">
      </el-table-column>
      <el-table-column
        prop="ip"
        header-align="center"
        align="center"
        label="ip地址">
      </el-table-column>
      <el-table-column
        prop="latitude"
        header-align="center"
        align="center"
        label="纬度">
      </el-table-column>
      <el-table-column
        prop="longitude"
        header-align="center"
        align="center"
        label="经度">
      </el-table-column>
      <el-table-column
        prop="source"
        header-align="center"
        align="center"
        label="1:公众号2:小程序">
      </el-table-column>
      <el-table-column
        prop="isAuth"
        header-align="center"
        align="center"
        label="1:未授权2:已授权">
      </el-table-column>
      <el-table-column
        prop="isPhone"
        header-align="center"
        align="center"
        label="1:未授权2:已授权">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        :label="$t('创建时间')">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        header-align="center"
        align="center"
        label="修改时间">
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
  import AddOrUpdate from './lttuser-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
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
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/ltt/lttuser/list'),
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
            url: this.$http.adornUrl('/ltt/lttuser/delete'),
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
