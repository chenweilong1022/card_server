webpackJsonp([1,40,41,42,43,44,45],{"0lFL":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,list:[],dataForm:{id:0,deviceId:"",online:"",deleteFlag:"",createTime:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.deviceId=e,this.visible=!0,this.$nextTick(function(){t.dataForm.deviceId&&t.$http({url:t.$http.adornUrl("/ltt/cdcard/listByDevicesId"),method:"post",data:t.$http.adornData({deviceId:t.dataForm.deviceId})}).then(function(e){var a=e.data;a&&0===a.code&&(t.list=a.list)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/changeCard"),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,iccid:e.dataForm.iccid,boardIndexed:e.dataForm.boardIndexed,indexed:e.dataForm.indexed})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[e._l(e.list,function(t,i){return a("div",[a("div",{staticStyle:{margin:"0 auto"}},[a("el-row",[a("el-col",{attrs:{span:24,type:"flex",justify:"center",align:"middle"}},[a("div",{staticStyle:{"text-align":"center",color:"red"}},[e._v("卡区"+e._s(1*i+1))])]),e._v(" "),a("el-col",{attrs:{span:24,type:"flex",justify:"center",align:"middle"}},[a("i",{staticClass:"el-icon-caret-top",staticStyle:{"text-align":"center",color:"red",margin:"0 auto"}})])],1)],1),e._v(" "),a("el-row",e._l(t,function(t){return a("el-col",{attrs:{span:8,type:"flex",justify:"center",align:"middle"}},[t.phone?a("el-card",[a("div",[e._v(e._s(1*i+1)+"-"+e._s(t.indexed+1))]),e._v(" "),a("div",[e._v(e._s(t.phone?t.phone:"无卡"))]),e._v(" "),a("div",[e._v(e._s(t.iccid?t.iccid:"无卡"))])]):a("el-card",{staticStyle:{"background-color":"#ffd400"}},[a("div",[e._v(e._s(1*i+1)+"-"+e._s(t.indexed+1))]),e._v(" "),a("div",[e._v(e._s(t.phone?t.phone:"无卡"))]),e._v(" "),a("div",[e._v(e._s(t.iccid?t.iccid:"无卡"))])])],1)}))],1)}),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("关闭")])],1)],2)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},"7mQ2":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{id:0,iccid:"",online:"",deleteFlag:"",createTime:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cdDevices.iccid,t.dataForm.online=a.cdDevices.online,t.dataForm.deleteFlag=a.cdDevices.deleteFlag,t.dataForm.createTime=a.cdDevices.createTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/changeCard"),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,iccid:e.dataForm.iccid,boardIndexed:e.dataForm.boardIndexed,indexed:e.dataForm.indexed})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"设备id",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"设备id"},model:{value:e.dataForm.iccid,callback:function(t){e.$set(e.dataForm,"iccid",t)},expression:"dataForm.iccid"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"板子区号",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"板子区号"},model:{value:e.dataForm.boardIndexed,callback:function(t){e.$set(e.dataForm,"boardIndexed",t)},expression:"dataForm.boardIndexed"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"卡号",prop:"iccid"}},[a("el-input",{attrs:{placeholder:"卡号"},model:{value:e.dataForm.indexed,callback:function(t){e.$set(e.dataForm,"indexed",t)},expression:"dataForm.indexed"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},IqNs:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("ok75"),r=a("7mQ2"),n=a("lM9O"),o=a("M454"),l=a("sSid"),s=a("0lFL"),d={data:function(){return{online:0,getTimeType:null,sortIndex:null,workType:null,groupId:null,dataForm:{key:"",online:null,workType:null,iccid:null,phone:null,fq:null,packageVersion:null},options:[{value:0,label:"在线"},{value:1,label:"离线"}],workOptions:[{value:1,label:"闲置"},{value:2,label:"初始化"},{value:3,label:"接码"}],getTimeOptions:[{value:1,label:"有取码时间"},{value:2,label:"无取码时间"}],sortIndexOptions:[{value:1,label:"当前板排序"}],dataList:[],dataListGroup:[],pageIndex:1,pageSize:100,totalPage:0,dataListLoading:!1,dataListSelections:[],addOrUpdateVisible:!1,cddevicesChangeCardVisible:!1,cardListVisible:!1,cddevicesInitVisible:!1,cddevicesGroupChangeVisible:!1,cddevicesUpdateAppVisible:!1}},components:{AddOrUpdate:i.default,CddevicesChangeCard:r.default,CddevicesUpdateApp:n.default,CddevicesGroupChange:o.default,CddevicesInit:l.default,CardList:s.default},activated:function(){this.getDataList(),this.getGroupDataList()},methods:{getGroupDataList:function(){var e=this;this.$http({url:this.$http.adornUrl("/ltt/cdcardgroup/list"),method:"get",params:this.$http.adornParams({page:1,limit:100})}).then(function(t){var a=t.data;a&&0===a.code?e.dataListGroup=a.page.list:e.dataListGroup=[]})},updateBatchHandler:function(e,t){var a=this,i=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+i.join(",")+"]进行["+(e?"修改状态":"批量修改状态")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){a.$http({url:a.$http.adornUrl("/ltt/cddevices/updateBatch"),method:"post",data:{ids:i,workType:t}}).then(function(e){var t=e.data;t&&0===t.code?a.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){a.getDataList()}}):a.$message.error(t.msg)})})},rebootHandler:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"重启":"批量重启")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/reboot"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},cddevicesGroupChangeHandle:function(e){var t=this;this.cddevicesGroupChangeVisible=!0,this.$nextTick(function(){var a=e?[e]:t.dataListSelections.map(function(e){return e.id});t.$refs.cddevicesGroupChange.init(a)})},initHandle:function(e){var t=this;this.cddevicesInitVisible=!0,this.$nextTick(function(){var a=e?[e]:t.dataListSelections.map(function(e){return e.id});t.$refs.cddevicesInit.init(a)})},cardListVisibleHandle:function(e){var t=this;this.cardListVisible=!0,this.$nextTick(function(){t.$refs.cardList.init(e)})},getCode:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"重置接码":"批量重置接码")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/getCode"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},initHandle3:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/initCard3"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},withBlack:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/withBlack"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},phoneDeleteAllHandle3:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/phoneDeleteAll"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},phoneDeleteAllHandle32:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"初始化":"批量初始化")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/phoneDeleteAll2"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},getDataList:function(){var e=this;this.dataListLoading=!0,this.$http({url:this.$http.adornUrl("/ltt/cddevices/list"),method:"get",params:this.$http.adornParams({page:this.pageIndex,limit:this.pageSize,online:this.online,groupId:this.groupId,workType:this.workType,iccid:this.dataForm.iccid,phone:this.dataForm.phone,packageVersion:this.dataForm.packageVersion,key:this.dataForm.key})}).then(function(t){var a=t.data;a&&0===a.code?(e.dataList=a.page.list,1===e.getTimeType?e.dataList=e.dataList.filter(function(e){return e.phoneGetTime}):2===e.getTimeType&&(e.dataList=e.dataList.filter(function(e){return!e.phoneGetTime})),1===e.sortIndex&&(e.dataList=e.dataList.sort(function(e,t){return(null==e.heartbeatRequest?1:e.heartbeatRequest.workFq)-(null==t.heartbeatRequest?1:t.heartbeatRequest.workFq)})),e.totalPage=a.page.totalCount):(e.dataList=[],e.totalPage=0),e.dataListLoading=!1})},sizeChangeHandle:function(e){this.pageSize=e,this.pageIndex=1,this.getDataList()},currentChangeHandle:function(e){this.pageIndex=e,this.getDataList()},selectionChangeHandle:function(e){this.dataListSelections=e},addOrUpdateHandle:function(e){var t=this;this.addOrUpdateVisible=!0,this.$nextTick(function(){t.$refs.addOrUpdate.init(e)})},cddevicesChangeCardHandle:function(e){var t=this;this.cddevicesChangeCardVisible=!0,this.$nextTick(function(){t.$refs.cddevicesChangeCard.init(e)})},cddevicesUpdateAppCardHandle:function(){var e=this;this.cddevicesUpdateAppVisible=!0,this.$nextTick(function(){var t=e.dataListSelections.map(function(e){return e.id});e.$refs.cddevicesUpdateApp.init(t)})},deleteHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"删除":"批量删除")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/delete"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})},updateAppHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"更新":"批量更新")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/cddevices/updateApp"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})}}},c={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mod-config"},[a("el-form",{attrs:{inline:!0,model:e.dataForm},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.getDataList()}}},[a("el-form-item",[a("el-input",{attrs:{placeholder:"参数名",clearable:""},model:{value:e.dataForm.fq,callback:function(t){e.$set(e.dataForm,"fq",t)},expression:"dataForm.fq"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"版本号",clearable:""},model:{value:e.dataForm.packageVersion,callback:function(t){e.$set(e.dataForm,"packageVersion",t)},expression:"dataForm.packageVersion"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"手机号",clearable:""},model:{value:e.dataForm.phone,callback:function(t){e.$set(e.dataForm,"phone",t)},expression:"dataForm.phone"}})],1),e._v(" "),a("el-form-item",[a("el-input",{attrs:{placeholder:"设备编码",clearable:""},model:{value:e.dataForm.iccid,callback:function(t){e.$set(e.dataForm,"iccid",t)},expression:"dataForm.iccid"}})],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"在线",clearable:""},model:{value:e.online,callback:function(t){e.online=t},expression:"online"}},e._l(e.options,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"工作流程",clearable:""},model:{value:e.workType,callback:function(t){e.workType=t},expression:"workType"}},e._l(e.workOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"分组",clearable:""},model:{value:e.groupId,callback:function(t){e.groupId=t},expression:"groupId"}},e._l(e.dataListGroup,function(e){return a("el-option",{key:e.id,attrs:{label:e.groupName,value:e.id}})}))],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"取码时间筛选",clearable:""},model:{value:e.getTimeType,callback:function(t){e.getTimeType=t},expression:"getTimeType"}},e._l(e.getTimeOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("el-select",{attrs:{placeholder:"码排序",clearable:""},model:{value:e.sortIndex,callback:function(t){e.sortIndex=t},expression:"sortIndex"}},e._l(e.sortIndexOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:function(t){e.getDataList()}}},[e._v("查询")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.cddevicesUpdateAppCardHandle()}}},[e._v("app更新")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.initHandle()}}},[e._v("批量初始化")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.withBlack()}}},[e._v("号码拉黑")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.getCode()}}},[e._v("项目切换")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.rebootHandler()}}},[e._v("批量重启")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.updateBatchHandler(null,1)}}},[e._v("批量闲置")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.updateBatchHandler(null,3)}}},[e._v("批量工作")]),e._v(" "),a("el-button",{attrs:{type:"primary",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.cddevicesGroupChangeHandle()}}},[e._v("分组")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.dataListLoading,expression:"dataListLoading"}],staticStyle:{width:"100%"},attrs:{data:e.dataList,border:""},on:{"selection-change":e.selectionChangeHandle}},[a("el-table-column",{attrs:{type:"selection","header-align":"center",align:"center",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"number","header-align":"center",align:"center",label:"编号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"deviceId","header-align":"center",align:"center",label:"设备id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"packageVersion","header-align":"center",align:"center",label:"版本号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"phoneGetTime","header-align":"center",align:"center",label:"取码时间"}}),e._v(" "),a("el-table-column",{attrs:{prop:"workFq","header-align":"center",align:"center",label:"当前板"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        "+e._s(null==t.row.heartbeatRequest?1:t.row.heartbeatRequest.workFq)+"\n      ")]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"fq","header-align":"center",align:"center",label:"板子数"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        "+e._s(null==t.row.heartbeatRequest?1:t.row.heartbeatRequest.fq)+"\n      ")]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"initSuccessNumber","header-align":"center",align:"center",label:"初始化比例"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        ("+e._s(t.row.initSuccessNumber)+"/"+e._s(t.row.initTotalNumber)+")\n      ")]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"onlineStr","header-align":"center",align:"center",label:"是否在线"}}),e._v(" "),a("el-table-column",{attrs:{prop:"workTypeStr","header-align":"center",align:"center",label:"工作流程"}}),e._v(" "),a("el-table-column",{attrs:{prop:"userId","header-align":"center",align:"center",label:"用户id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"projectId","header-align":"center",align:"center",label:"项目id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"phone","header-align":"center",align:"center",label:"phone"}}),e._v(" "),a("el-table-column",{attrs:{prop:"iccid","header-align":"center",align:"center",label:"iccid"}}),e._v(" "),a("el-table-column",{attrs:{fixed:"right","header-align":"center",align:"center",width:"150",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.initHandle(t.row.id)}}},[e._v("初始化")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.rebootHandler(t.row.id)}}},[e._v("重启")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.updateBatchHandler(t.row.id,1)}}},[e._v("闲置")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.updateBatchHandler(t.row.id,3)}}},[e._v("工作")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.cddevicesChangeCardHandle(t.row.id)}}},[e._v("切换卡")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.cardListVisibleHandle(t.row.deviceId)}}},[e._v("卡详情")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.pageIndex,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.totalPage,layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.sizeChangeHandle,"current-change":e.currentChangeHandle}}),e._v(" "),e.addOrUpdateVisible?a("add-or-update",{ref:"addOrUpdate",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesChangeCardVisible?a("cddevices-change-card",{ref:"cddevicesChangeCard",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesUpdateAppVisible?a("cddevices-update-app",{ref:"cddevicesUpdateApp",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesInitVisible?a("cddevices-init",{ref:"cddevicesInit",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cddevicesGroupChangeVisible?a("cddevices-group-change",{ref:"cddevicesGroupChange",on:{refreshDataList:e.getDataList}}):e._e(),e._v(" "),e.cardListVisible?a("card-list",{ref:"cardList",on:{refreshDataList:e.getDataList}}):e._e()],1)},staticRenderFns:[]},u=a("46Yf")(d,c,!1,null,null,null);t.default=u.exports},M454:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{groupId:null,visible:!1,dataList:[],dataForm:{ids:null},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{getDataList:function(){var e=this;this.$http({url:this.$http.adornUrl("/ltt/cdcardgroup/list"),method:"get",params:this.$http.adornParams({page:1,limit:100})}).then(function(t){var a=t.data;a&&0===a.code?e.dataList=a.page.list:e.dataList=[]})},init:function(e){this.dataForm.ids=e||0,this.visible=!0,this.getDataList()},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/changeGroup"),method:"post",data:e.$http.adornData({ids:e.dataForm.ids||void 0,groupId:e.groupId})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"分组名称"}},[a("el-select",{attrs:{placeholder:"分组名称",clearable:""},model:{value:e.groupId,callback:function(t){e.groupId=t},expression:"groupId"}},e._l(e.dataList,function(e){return a("el-option",{key:e.id,attrs:{label:e.groupName,value:e.id}})}))],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},lM9O:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{ids:null,iccid:"",online:"",deleteFlag:"",createTime:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.ids=e||[],this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.ids&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cdDevices.iccid,t.dataForm.online=a.cdDevices.online,t.dataForm.deleteFlag=a.cdDevices.deleteFlag,t.dataForm.createTime=a.cdDevices.createTime)})})},dataFormSubmit:function(e){var t=this;this.$refs.dataForm.validate(function(a){a&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/updateApp"),method:"post",data:t.$http.adornData({ids:t.dataForm.ids||void 0,httpUrl:t.dataForm.httpUrl,updateType:e})}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.visible=!1,t.$emit("refreshDataList")}}):t.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:(e.dataForm.id,"更新app"),"close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"apk下载路径",prop:"httpUrl"}},[a("el-input",{attrs:{placeholder:"apk下载路径"},model:{value:e.dataForm.httpUrl,callback:function(t){e.$set(e.dataForm,"httpUrl",t)},expression:"dataForm.httpUrl"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit(1)}}},[e._v("更新选择设备")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit(2)}}},[e._v("更新全部设备")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},ok75:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{visible:!1,dataForm:{id:0,iccid:"",online:"",workType:"",deleteFlag:"",createTime:"",packageVersion:"",number:""},dataRule:{iccid:[{required:!0,message:"不能为空",trigger:"blur"}],online:[{required:!0,message:"不能为空",trigger:"blur"}],workType:[{required:!0,message:"不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"不能为空",trigger:"blur"}],createTime:[{required:!0,message:"不能为空",trigger:"blur"}],packageVersion:[{required:!0,message:"不能为空",trigger:"blur"}],number:[{required:!0,message:"不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cddevices/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.iccid=a.cddevices.iccid,t.dataForm.online=a.cddevices.online,t.dataForm.workType=a.cddevices.workType,t.dataForm.deleteFlag=a.cddevices.deleteFlag,t.dataForm.createTime=a.cddevices.createTime,t.dataForm.packageVersion=a.cddevices.packageVersion,t.dataForm.number=a.cddevices.number)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,iccid:e.dataForm.iccid,online:e.dataForm.online,workType:e.dataForm.workType,deleteFlag:e.dataForm.deleteFlag,createTime:e.dataForm.createTime,packageVersion:e.dataForm.packageVersion,number:e.dataForm.number})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"",prop:"iccid"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.iccid,callback:function(t){e.$set(e.dataForm,"iccid",t)},expression:"dataForm.iccid"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"online"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.online,callback:function(t){e.$set(e.dataForm,"online",t)},expression:"dataForm.online"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"workType"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.workType,callback:function(t){e.$set(e.dataForm,"workType",t)},expression:"dataForm.workType"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"deleteFlag"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.deleteFlag,callback:function(t){e.$set(e.dataForm,"deleteFlag",t)},expression:"dataForm.deleteFlag"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"createTime"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.createTime,callback:function(t){e.$set(e.dataForm,"createTime",t)},expression:"dataForm.createTime"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"packageVersion"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.packageVersion,callback:function(t){e.$set(e.dataForm,"packageVersion",t)},expression:"dataForm.packageVersion"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"",prop:"number"}},[a("el-input",{attrs:{placeholder:""},model:{value:e.dataForm.number,callback:function(t){e.$set(e.dataForm,"number",t)},expression:"dataForm.number"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports},sSid:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i={data:function(){return{type:"2",visible:!1,dataForm:{ids:null,type:"2",ussd:""},dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){this.dataForm.ids=e||[],this.visible=!0},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/initCard"),method:"post",data:e.$http.adornData({ids:e.dataForm.ids||void 0,type:e.type,ussd:e.dataForm.ussd})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},r={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:(e.dataForm.id,"更新app"),"close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"初始化类型",prop:"type"}},[a("el-radio-group",{model:{value:e.type,callback:function(t){e.type=t},expression:"type"}},[a("el-radio",{attrs:{label:"1",size:"large"}},[e._v("从头开始")]),e._v(" "),a("el-radio",{attrs:{label:"2",size:"large"}},[e._v("读取历史")])],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"ussd",prop:"ussd"}},[a("el-input",{attrs:{placeholder:"ussd"},model:{value:e.dataForm.ussd,callback:function(t){e.$set(e.dataForm,"ussd",t)},expression:"dataForm.ussd"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},n=a("46Yf")(i,r,!1,null,null,null);t.default=n.exports}});