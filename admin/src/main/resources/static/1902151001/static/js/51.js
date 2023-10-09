webpackJsonp([51,94],{AB7v:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{memberBargainingInformationId:0,bargainingActivityId:"",commodityId:"",memberId:"",cuttingDownNumber:"",finalPrice:"",state:"",createTime:""},dataRule:{bargainingActivityId:[{required:!0,message:"砍价活动id不能为空",trigger:"blur"}],commodityId:[{required:!0,message:"商品id不能为空",trigger:"blur"}],memberId:[{required:!0,message:"会员id不能为空",trigger:"blur"}],cuttingDownNumber:[{required:!0,message:"被砍次数不能为空",trigger:"blur"}],finalPrice:[{required:!0,message:"最终价格不能为空",trigger:"blur"}],state:[{required:!0,message:"状态不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.memberBargainingInformationId=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.memberBargainingInformationId&&t.$http({url:t.$http.adornUrl("/cellar/cellarmemberbargaininginformationdb/info/"+t.dataForm.memberBargainingInformationId),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.bargainingActivityId=a.cellarmemberbargaininginformationdb.bargainingActivityId,t.dataForm.commodityId=a.cellarmemberbargaininginformationdb.commodityId,t.dataForm.memberId=a.cellarmemberbargaininginformationdb.memberId,t.dataForm.cuttingDownNumber=a.cellarmemberbargaininginformationdb.cuttingDownNumber,t.dataForm.finalPrice=a.cellarmemberbargaininginformationdb.finalPrice,t.dataForm.state=a.cellarmemberbargaininginformationdb.state,t.dataForm.createTime=a.cellarmemberbargaininginformationdb.createTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/cellar/cellarmemberbargaininginformationdb/"+(e.dataForm.memberBargainingInformationId?"update":"save")),method:"post",data:e.$http.adornData({memberBargainingInformationId:e.dataForm.memberBargainingInformationId||void 0,bargainingActivityId:e.dataForm.bargainingActivityId,commodityId:e.dataForm.commodityId,memberId:e.dataForm.memberId,cuttingDownNumber:e.dataForm.cuttingDownNumber,finalPrice:e.dataForm.finalPrice,state:e.dataForm.state,createTime:e.dataForm.createTime})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"砍价活动id",prop:"bargainingActivityId"}},[a("el-input",{attrs:{placeholder:"砍价活动id"},model:{value:e.dataForm.bargainingActivityId,callback:function(t){e.$set(e.dataForm,"bargainingActivityId",t)},expression:"dataForm.bargainingActivityId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"商品id",prop:"commodityId"}},[a("el-input",{attrs:{placeholder:"商品id"},model:{value:e.dataForm.commodityId,callback:function(t){e.$set(e.dataForm,"commodityId",t)},expression:"dataForm.commodityId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"会员id",prop:"memberId"}},[a("el-input",{attrs:{placeholder:"会员id"},model:{value:e.dataForm.memberId,callback:function(t){e.$set(e.dataForm,"memberId",t)},expression:"dataForm.memberId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"被砍次数",prop:"cuttingDownNumber"}},[a("el-input",{attrs:{placeholder:"被砍次数"},model:{value:e.dataForm.cuttingDownNumber,callback:function(t){e.$set(e.dataForm,"cuttingDownNumber",t)},expression:"dataForm.cuttingDownNumber"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"最终价格",prop:"finalPrice"}},[a("el-input",{attrs:{placeholder:"最终价格"},model:{value:e.dataForm.finalPrice,callback:function(t){e.$set(e.dataForm,"finalPrice",t)},expression:"dataForm.finalPrice"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"状态",prop:"state"}},[a("el-input",{attrs:{placeholder:"状态"},model:{value:e.dataForm.state,callback:function(t){e.$set(e.dataForm,"state",t)},expression:"dataForm.state"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"创建时间",prop:"createTime"}},[a("el-input",{attrs:{placeholder:"创建时间"},model:{value:e.dataForm.createTime,callback:function(t){e.$set(e.dataForm,"createTime",t)},expression:"dataForm.createTime"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},WYPh:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{dataForm:{key:""},dataList:[],pageIndex:1,pageSize:10,totalPage:0,dataListLoading:!1,dataListSelections:[],addOrUpdateVisible:!1}},components:{AddOrUpdate:a("AB7v").default},activated:function(){this.getDataList()},methods:{getDataList:function(){var e=this;this.dataListLoading=!0,this.$http({url:this.$http.adornUrl("/cellar/cellarmemberbargaininginformationdb/list"),method:"get",params:this.$http.adornParams({page:this.pageIndex,limit:this.pageSize,key:this.dataForm.key})}).then(function(t){var a=t.data;a&&0===a.code?(e.dataList=a.page.list,e.totalPage=a.page.totalCount):(e.dataList=[],e.totalPage=0),e.dataListLoading=!1})},sizeChangeHandle:function(e){this.pageSize=e,this.pageIndex=1,this.getDataList()},currentChangeHandle:function(e){this.pageIndex=e,this.getDataList()},selectionChangeHandle:function(e){this.dataListSelections=e},addOrUpdateHandle:function(e){var t=this;this.addOrUpdateVisible=!0,this.$nextTick(function(){t.$refs.addOrUpdate.init(e)})},deleteHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.memberBargainingInformationId});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"删除":"批量删除")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/cellar/cellarmemberbargaininginformationdb/delete"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mod-config"},[a("el-form",{attrs:{inline:!0,model:e.dataForm},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.getDataList()}}},[a("el-form-item",[a("el-input",{attrs:{placeholder:"参数名",clearable:""},model:{value:e.dataForm.key,callback:function(t){e.$set(e.dataForm,"key",t)},expression:"dataForm.key"}})],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:function(t){e.getDataList()}}},[e._v("查询")]),e._v(" "),e.isAuth("cellar:cellarmemberbargaininginformationdb:save")?a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addOrUpdateHandle()}}},[e._v("新增")]):e._e(),e._v(" "),e.isAuth("cellar:cellarmemberbargaininginformationdb:delete")?a("el-button",{attrs:{type:"danger",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.deleteHandle()}}},[e._v("批量删除")]):e._e()],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.dataListLoading,expression:"dataListLoading"}],staticStyle:{width:"100%"},attrs:{data:e.dataList,border:""},on:{"selection-change":e.selectionChangeHandle}},[a("el-table-column",{attrs:{type:"selection","header-align":"center",align:"center",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"memberBargainingInformationId","header-align":"center",align:"center",label:"会员砍价信息id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"bargainingActivityId","header-align":"center",align:"center",label:"砍价活动id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"commodityId","header-align":"center",align:"center",label:"商品id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"memberId","header-align":"center",align:"center",label:"会员id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"cuttingDownNumber","header-align":"center",align:"center",label:"被砍次数"}}),e._v(" "),a("el-table-column",{attrs:{prop:"finalPrice","header-align":"center",align:"center",label:"最终价格"}}),e._v(" "),a("el-table-column",{attrs:{prop:"state","header-align":"center",align:"center",label:"状态"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime","header-align":"center",align:"center",label:"创建时间"}}),e._v(" "),a("el-table-column",{attrs:{fixed:"right","header-align":"center",align:"center",width:"150",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.addOrUpdateHandle(t.row.memberBargainingInformationId)}}},[e._v("修改")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.deleteHandle(t.row.memberBargainingInformationId)}}},[e._v("删除")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.pageIndex,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.totalPage,layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.sizeChangeHandle,"current-change":e.currentChangeHandle}}),e._v(" "),e.addOrUpdateVisible?a("add-or-update",{ref:"addOrUpdate",on:{refreshDataList:e.getDataList}}):e._e()],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports}});