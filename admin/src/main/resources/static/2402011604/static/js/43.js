webpackJsonp([43],{sSid:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{type:"2",visible:!1,dataForm:{ids:null,type:"2",ussd:""},bh:["*833#","*102*9#"],dataRule:{iccid:[{required:!0,message:"设备id不能为空",trigger:"blur"}],online:[{required:!0,message:"是否在线不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){this.dataForm.ids=e||[],this.visible=!0},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevices/initCard"),method:"post",data:e.$http.adornData({ids:e.dataForm.ids||void 0,type:e.type,ussd:e.dataForm.ussd})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},i={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:(e.dataForm.id,"更新app"),"close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"初始化类型",prop:"type"}},[a("el-radio-group",{model:{value:e.type,callback:function(t){e.type=t},expression:"type"}},[a("el-radio",{attrs:{label:"1",size:"large"}},[e._v("从头开始")]),e._v(" "),a("el-radio",{attrs:{label:"2",size:"large"}},[e._v("读取历史")])],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"ussd",prop:"ussd"}},[a("el-select",{attrs:{placeholder:"工作流程",clearable:""},model:{value:e.dataForm.ussd,callback:function(t){e.$set(e.dataForm,"ussd",t)},expression:"dataForm.ussd"}},e._l(e.bh,function(e){return a("el-option",{key:e,attrs:{label:e,value:e}})}))],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},s=a("46Yf")(r,i,!1,null,null,null);t.default=s.exports}});