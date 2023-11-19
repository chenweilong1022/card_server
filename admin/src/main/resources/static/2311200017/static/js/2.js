webpackJsonp([2,38,39,40],{"7mQ2":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{id:0,iccid:"",online:"",deleteFlag:"",createTime:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cdDevices.iccid,t.dataForm.online=a.cdDevices.online,t.dataForm.deleteFlag=a.cdDevices.deleteFlag,t.dataForm.createTime=a.cdDevices.createTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/changeCard"),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,iccid:e.dataForm.iccid,boardIndexed:e.dataForm.boardIndexed,indexed:e.dataForm.indexed})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"设备id",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"设备id"},model:{value:e.dataForm.iccid,callback:function(t){e.$set(e.dataForm,"iccid",t)},expression:"dataForm.iccid"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"板子区号",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"板子区号"},model:{value:e.dataForm.boardIndexed,callback:function(t){e.$set(e.dataForm,"boardIndexed",t)},expression:"dataForm.boardIndexed"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"卡号",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"卡号"},model:{value:e.dataForm.indexed,callback:function(t){e.$set(e.dataForm,"indexed",t)},expression:"dataForm.indexed"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},r=a("46Yf")(i,n,!1,null,null,null);t.default=r.exports},IqNs:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("ok75"),n=a("7mQ2"),r=a("lM9O"),o={data:function(){return{online:null,dataForm:{key:"",online:null,packageVersion:null},options:[{value:0,label:"在线"},{value:1,label:"离线"}],dataList:[],pageIndex:1,pageSize:10,totalPage:0,dataListLoading:!1,dataListSelections:[],addOrUpdateVisible:!1,cddevicesChangeCardVisible:!1,cddevicesUpdateAppVisible:!1}},components:{AddOrUpdate:i.default,CddevicesChangeCard:n.default,CddevicesUpdateApp:r.default},activated:function(){this.getDataList()},methods:{rebootHandler:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"重启":"批量重启")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/reboot"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},initHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/initCard"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},initHandle2:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/initCard2"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},getDataList:function(){var e=this;this.dataListLoading=!0,this.$http({url:this.$http.adornUrl("/ltt/cddevices/list"),method:"get",params:this.$http.adornParams({page:this.pageIndex,limit:this.pageSize,online:this.online,packageVersion:this.dataForm.packageVersion,key:this.dataForm.key})}).then(function(t){var a=t.data;a&&0===a.code?(e.dataList=a.page.list,e.totalPage=a.page.totalCount):(e.dataList=[],e.totalPage=0),e.dataListLoading=!1})},sizeChangeHandle:function(e){this.pageSize=e,this.pageIndex=1,this.getDataList()},currentChangeHandle:function(e){this.pageIndex=e,this.getDataList()},selectionChangeHandle:function(e){this.dataListSelections=e},addOrUpdateHandle:function(e){var t=this;this.addOrUpdateVisible=!0,this.$nextTick(function(){t.$refs.addOrUpdate.init(e)})},cddevicesChangeCardHandle:function(e){var t=this;this.cddevicesChangeCardVisible=!0,this.$nextTick(function(){t.$refs.cddevicesChangeCard.init(e)})},cddevicesUpdateAppCardHandle:function(){var e=this;this.cddevicesUpdateAppVisible=!0,this.$nextTick(function(){var t=e.dataListSelections.map(function(e){return e.id});e.$refs.cddevicesUpdateApp.init(t)})},deleteHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"删除":"批量删除")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/delete"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},updateAppHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"更新":"批量更新")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/updateApp"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})}}},d={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mod-config"},[a("el-form",{attrs:{inline:!0,model:e.dataForm},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.getDataList()}}},[a("el-form-item",[a("el-input",{attrs:{placeholder:"参数名",clearable:""},model:{value:e.dataForm.key,callback:function(t){e.$set(e.dataForm,"key",t)},expression:"dataForm.key"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"版本号",clearable:""},model:{value:e.dataForm.packageVersion,callback:function(t){e.$set(e.dataForm,"packageVersion",t)},expression:"dataForm.packageVersion"}})],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"请选择",clearable:""},model:{value:e.online,callback:function(t){e.online=t},expression:"online"}},e._l(e.options,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:function(t){e.getDataList()}}},[e._v("查询")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.cddevicesUpdateAppCardHandle()}}},[e._v("app更新")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.initHandle()}}},[e._v("批量初始化")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.initHandle2()}}},[e._v("批量初始化2")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.rebootHandler()}}},[e._v("批量重启")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.dataListLoading,expression:"dataListLoading"}],staticStyle:{width:"100%"},attrs:{data:e.dataList,border:""},on:{"selection-change":e.selectionChangeHandle}},[a("el-table-column",{attrs:{type:"selection","header-align":"center",align:"center",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"id","header-align":"center",align:"center",label:"主键"}}),e._v(" "),a("el-table-column",{attrs:{prop:"iccid","header-align":"center",align:"center",label:"设备id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"number","header-align":"center",align:"center",label:"编号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"packageVersion","header-align":"center",align:"center",label:"版本号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"initSuccessNumber","header-align":"center",align:"center",label:"初始化比例"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          ("+e._s(t.row.initSuccessNumber)+"/"+e._s(t.row.initTotalNumber)+")\n        ")]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"onlineStr","header-align":"center",align:"center",label:"是否在线"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime","header-align":"center",align:"center",label:"创建时间"}}),e._v(" "),a("el-table-column",{attrs:{fixed:"right","header-align":"center",align:"center",width:"150",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.initHandle(t.row.id)}}},[e._v("初始化")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.initHandle2(t.row.id)}}},[e._v("初始化2")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.rebootHandler(t.row.id)}}},[e._v("重启")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.cddevicesChangeCardHandle(t.row.id)}}},[e._v("切换卡")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.pageIndex,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.totalPage,layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.sizeChangeHandle,"current-change":e.currentChangeHandle}}),e._v(" "),e.addOrUpdateVisible?a("add-or-update",{ref:"addOrUpdate",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesChangeCardVisible?a("cddevices-change-card",{ref:"cddevicesChangeCard",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesUpdateAppVisible?a("cddevices-update-app",{ref:"cddevicesUpdateApp",on:{refreshDataList:e.getDataList}}):e._e()],1)},staticRenderFns:[]},l=a("46Yf")(o,d,!1,null,null,null);t.default=l.exports},lM9O:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{ids:null,iccid:"",online:"",deleteFlag:"",createTime:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.ids=e||[],this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.ids&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cdDevices.iccid,t.dataForm.online=a.cdDevices.online,t.dataForm.deleteFlag=a.cdDevices.deleteFlag,t.dataForm.createTime=a.cdDevices.createTime)})})},dataFormSubmit:function(e){var t=this;this.$refs.dataForm.validate(function(a){a&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/updateApp"),method:"post",data:t.$http.adornData({ids:t.dataForm.ids||void 0,httpUrl:t.dataForm.httpUrl,updateType:e})}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.visible=!1,t.$emit("refreshDataList")}}):t.$message.error(a.msg)})})}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:(e.dataForm.id,"更新app"),"close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"apk下载路径",prop:"httpUrl"}},[a("el-input",{attrs:{placeholder:"apk下载路径"},model:{value:e.dataForm.httpUrl,callback:function(t){e.$set(e.dataForm,"httpUrl",t)},expression:"dataForm.httpUrl"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit(1)}}},[e._v("更新选择设备")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit(2)}}},[e._v("更新全部设备")])],1)],1)},staticRenderFns:[]},r=a("46Yf")(i,n,!1,null,null,null);t.default=r.exports},ok75:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{id:0,iccid:"",online:"",deleteFlag:"",createTime:"",packageVersion:"",number:""},dataRule:{iccid:[{required:!0,message:"不能为空",trigger:"blur"}],online:[{required:!0,message:"不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"不能为空",trigger:"blur"}],createTime:[{required:!0,message:"不能为空",trigger:"blur"}],packageVersion:[{required:!0,message:"不能为空",trigger:"blur"}],number:[{required:!0,message:"不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cddevices.iccid,t.dataForm.online=a.cddevices.online,t.dataForm.deleteFlag=a.cddevices.deleteFlag,t.dataForm.createTime=a.cddevices.createTime,t.dataForm.packageVersion=a.cddevices.packageVersion,t.dataForm.number=a.cddevices.number)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,iccid:e.dataForm.iccid,online:e.dataForm.online,deleteFlag:e.dataForm.deleteFlag,createTime:e.dataForm.createTime,packageVersion:e.dataForm.packageVersion,number:e.dataForm.number})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"",prop:"iccid"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.iccid,callback:function(t){e.$set(e.dataForm,"iccid",t)},expression:"dataForm.iccid"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"online"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.online,callback:function(t){e.$set(e.dataForm,"online",t)},expression:"dataForm.online"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"deleteFlag"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.deleteFlag,callback:function(t){e.$set(e.dataForm,"deleteFlag",t)},expression:"dataForm.deleteFlag"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"createTime"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.createTime,callback:function(t){e.$set(e.dataForm,"createTime",t)},expression:"dataForm.createTime"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"packageVersion"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.packageVersion,callback:function(t){e.$set(e.dataForm,"packageVersion",t)},expression:"dataForm.packageVersion"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"number"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.number,callback:function(t){e.$set(e.dataForm,"number",t)},expression:"dataForm.number"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},r=a("46Yf")(i,n,!1,null,null,null);t.default=r.exports}});