webpackJsonp([1,3],{"/caQ":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("HzJ8"),o=a.n(r),l={data:function(){return{type:2,platform:1,visible:!1,projectDataList:[],platformOptions:[{value:1,label:"火狐狸",url:"https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99"},{value:2,label:"沃码",url:"https://www.worldcode.win/ksapi.ashx?key=066131D94DADB4B3"}],options:[{value:1,label:"云存储"},{value:2,label:"项目配置"}],dataForm:{id:0,paramKey:"",paramValue:"",userId:"",platform:null,projectId:"",phonePre:"",codeApiUrl:"",remark:""},dataRule:{paramKey:[{required:!0,message:"参数名不能为空",trigger:"blur"}],paramValue:[{required:!0,message:"参数值不能为空",trigger:"blur"}]}}},methods:{platformHandler:function(e){var t=!0,a=!1,r=void 0;try{for(var l,n=o()(this.platformOptions);!(t=(l=n.next()).done);t=!0){var i=l.value;i.value===e&&(this.dataForm.codeApiUrl=i.url)}}catch(e){a=!0,r=e}finally{try{!t&&n.return&&n.return()}finally{if(a)throw r}}},init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.projectList(),this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/sys/config/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.paramKey=a.config.paramKey,t.dataForm.paramValue=a.config.paramValue,t.dataForm.remark=a.config.remark,t.dataForm.userId=a.config.userId,t.dataForm.projectId=a.config.projectId,t.dataForm.phonePre=a.config.phonePre,t.dataForm.codeApiUrl=a.config.codeApiUrl)})})},projectList:function(){var e=this;this.$http({url:this.$http.adornUrl("/ltt/cdproject/list"),method:"get",params:this.$http.adornParams({page:1,limit:100})}).then(function(t){var a=t.data;a&&0===a.code&&(e.projectDataList=a.page.list)})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/sys/config/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,paramKey:e.dataForm.paramKey,paramValue:e.dataForm.paramValue,userId:e.dataForm.userId,projectId:e.dataForm.projectId,phonePre:e.dataForm.phonePre,codeApiUrl:e.dataForm.codeApiUrl,type:e.type,platform:e.platform,remark:e.dataForm.remark})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"参数名"}},[a("el-select",{attrs:{placeholder:"类型",clearable:""},model:{value:e.type,callback:function(t){e.type=t},expression:"type"}},e._l(e.options,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"参数名",prop:"paramKey"}},[a("el-input",{attrs:{placeholder:"参数名"},model:{value:e.dataForm.paramKey,callback:function(t){e.$set(e.dataForm,"paramKey",t)},expression:"dataForm.paramKey"}})],1):e._e(),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"参数值",prop:"paramValue"}},[a("el-input",{attrs:{placeholder:"参数值"},model:{value:e.dataForm.paramValue,callback:function(t){e.$set(e.dataForm,"paramValue",t)},expression:"dataForm.paramValue"}})],1):e._e(),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"备注",prop:"remark"}},[a("el-input",{attrs:{placeholder:"备注"},model:{value:e.dataForm.remark,callback:function(t){e.$set(e.dataForm,"remark",t)},expression:"dataForm.remark"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"平台",prop:"platform"}},[a("el-select",{attrs:{placeholder:"平台",clearable:""},on:{change:e.platformHandler},model:{value:e.platform,callback:function(t){e.platform=t},expression:"platform"}},e._l(e.platformOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"接码平台接口",prop:"codeApiUrl"}},[a("el-input",{attrs:{placeholder:"备注"},model:{value:e.dataForm.codeApiUrl,callback:function(t){e.$set(e.dataForm,"codeApiUrl",t)},expression:"dataForm.codeApiUrl"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"项目id",prop:"projectId"}},[a("el-select",{attrs:{placeholder:"平台",clearable:""},on:{change:e.platformHandler},model:{value:e.dataForm.projectId,callback:function(t){e.$set(e.dataForm,"projectId",t)},expression:"dataForm.projectId"}},e._l(e.projectDataList,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}))],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"手机号前缀",prop:"phonePre"}},[a("el-input",{attrs:{placeholder:"手机号前缀"},model:{value:e.dataForm.phonePre,callback:function(t){e.$set(e.dataForm,"phonePre",t)},expression:"dataForm.phonePre"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"用户id",prop:"userId"}},[a("el-input",{attrs:{placeholder:"用户id",readonly:""},model:{value:e.dataForm.userId,callback:function(t){e.$set(e.dataForm,"userId",t)},expression:"dataForm.userId"}})],1):e._e()],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},i=a("46Yf")(l,n,!1,null,null,null);t.default=i.exports},HzJ8:function(e,t,a){e.exports={default:a("vY6q"),__esModule:!0}},MKmw:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{dataForm:{paramKey:""},dataList:[],pageIndex:1,pageSize:10,totalPage:0,dataListLoading:!1,dataListSelections:[],addOrUpdateVisible:!1}},components:{AddOrUpdate:a("/caQ").default},activated:function(){this.getDataList()},methods:{getDataList:function(){var e=this;this.dataListLoading=!0,this.$http({url:this.$http.adornUrl("/sys/config/list"),method:"get",params:this.$http.adornParams({page:this.pageIndex,limit:this.pageSize,paramKey:this.dataForm.paramKey})}).then(function(t){var a=t.data;a&&0===a.code?(e.dataList=a.page.list,e.totalPage=a.page.totalCount):(e.dataList=[],e.totalPage=0),e.dataListLoading=!1})},sizeChangeHandle:function(e){this.pageSize=e,this.pageIndex=1,this.getDataList()},currentChangeHandle:function(e){this.pageIndex=e,this.getDataList()},selectionChangeHandle:function(e){this.dataListSelections=e},addOrUpdateHandle:function(e){var t=this;this.addOrUpdateVisible=!0,this.$nextTick(function(){t.$refs.addOrUpdate.init(e)})},deleteHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"删除":"批量删除")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/sys/config/delete"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})}).catch(function(){})}}},o={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mod-config"},[a("el-form",{attrs:{inline:!0,model:e.dataForm},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.getDataList()}}},[a("el-form-item",[a("el-input",{attrs:{placeholder:"参数名",clearable:""},model:{value:e.dataForm.paramKey,callback:function(t){e.$set(e.dataForm,"paramKey",t)},expression:"dataForm.paramKey"}})],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:function(t){e.getDataList()}}},[e._v("查询")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addOrUpdateHandle()}}},[e._v("新增")]),e._v(" "),a("el-button",{attrs:{type:"danger",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.deleteHandle()}}},[e._v("批量删除")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.dataListLoading,expression:"dataListLoading"}],staticStyle:{width:"100%"},attrs:{data:e.dataList,border:""},on:{"selection-change":e.selectionChangeHandle}},[a("el-table-column",{attrs:{type:"selection","header-align":"center",align:"center",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"id","header-align":"center",align:"center",width:"80",label:"ID"}}),e._v(" "),a("el-table-column",{attrs:{prop:"paramKey","header-align":"center",align:"center",label:"参数名"}}),e._v(" "),a("el-table-column",{attrs:{prop:"paramValue","header-align":"center",align:"center",label:"参数值"}}),e._v(" "),a("el-table-column",{attrs:{prop:"remark","header-align":"center",align:"center",label:"备注"}}),e._v(" "),a("el-table-column",{attrs:{fixed:"right","header-align":"center",align:"center",width:"150",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.addOrUpdateHandle(t.row.id)}}},[e._v("修改")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.deleteHandle(t.row.id)}}},[e._v("删除")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.pageIndex,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.totalPage,layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.sizeChangeHandle,"current-change":e.currentChangeHandle}}),e._v(" "),e.addOrUpdateVisible?a("add-or-update",{ref:"addOrUpdate",on:{refreshDataList:e.getDataList}}):e._e()],1)},staticRenderFns:[]},l=a("46Yf")(r,o,!1,null,null,null);t.default=l.exports},"XO/m":function(e,t,a){var r=a("7NgR"),o=a("/tnA");e.exports=a("DH3n").getIterator=function(e){var t=o(e);if("function"!=typeof t)throw TypeError(e+" is not iterable!");return r(t.call(e))}},vY6q:function(e,t,a){a("8LqW"),a("g5OY"),e.exports=a("XO/m")}});