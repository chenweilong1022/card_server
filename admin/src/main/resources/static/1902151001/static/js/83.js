webpackJsonp([83],{y46f:function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var r={data:function(){return{visible:!1,dataForm:{memberMessageId:0,state:"",createTime:"",memberId:"",messageContent:"",messageHead:"",messageType:"",haveRead:""},dataRule:{state:[{required:!0,message:"状态不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}],memberId:[{required:!0,message:"会员id不能为空",trigger:"blur"}],messageContent:[{required:!0,message:"消息内容不能为空",trigger:"blur"}],messageHead:[{required:!0,message:"消息标题不能为空",trigger:"blur"}],messageType:[{required:!0,message:"消息类型不能为空",trigger:"blur"}],haveRead:[{required:!0,message:"是否已读不能为空",trigger:"blur"}]}}},methods:{init:function(e){var a=this;this.dataForm.memberMessageId=e||0,this.visible=!0,this.$nextTick(function(){a.$refs.dataForm.resetFields(),a.dataForm.memberMessageId&&a.$http({url:a.$http.adornUrl("/cellar/cellarmembermessagedb/info/"+a.dataForm.memberMessageId),method:"get",params:a.$http.adornParams()}).then(function(e){var t=e.data;t&&0===t.code&&(a.dataForm.state=t.cellarmembermessagedb.state,a.dataForm.createTime=t.cellarmembermessagedb.createTime,a.dataForm.memberId=t.cellarmembermessagedb.memberId,a.dataForm.messageContent=t.cellarmembermessagedb.messageContent,a.dataForm.messageHead=t.cellarmembermessagedb.messageHead,a.dataForm.messageType=t.cellarmembermessagedb.messageType,a.dataForm.haveRead=t.cellarmembermessagedb.haveRead)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(a){a&&e.$http({url:e.$http.adornUrl("/cellar/cellarmembermessagedb/"+(e.dataForm.memberMessageId?"update":"save")),method:"post",data:e.$http.adornData({memberMessageId:e.dataForm.memberMessageId||void 0,state:e.dataForm.state,createTime:e.dataForm.createTime,memberId:e.dataForm.memberId,messageContent:e.dataForm.messageContent,messageHead:e.dataForm.messageHead,messageType:e.dataForm.messageType,haveRead:e.dataForm.haveRead})}).then(function(a){var t=a.data;t&&0===t.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(t.msg)})})}}},s={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(a){e.visible=a}}},[t("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(a){if(!("button"in a)&&e._k(a.keyCode,"enter",13,a.key))return null;e.dataFormSubmit()}}},[t("el-form-item",{attrs:{label:"状态",prop:"state"}},[t("el-input",{attrs:{placeholder:"状态"},model:{value:e.dataForm.state,callback:function(a){e.$set(e.dataForm,"state",a)},expression:"dataForm.state"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"创建时间",prop:"createTime"}},[t("el-input",{attrs:{placeholder:"创建时间"},model:{value:e.dataForm.createTime,callback:function(a){e.$set(e.dataForm,"createTime",a)},expression:"dataForm.createTime"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"会员id",prop:"memberId"}},[t("el-input",{attrs:{placeholder:"会员id"},model:{value:e.dataForm.memberId,callback:function(a){e.$set(e.dataForm,"memberId",a)},expression:"dataForm.memberId"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"消息内容",prop:"messageContent"}},[t("el-input",{attrs:{placeholder:"消息内容"},model:{value:e.dataForm.messageContent,callback:function(a){e.$set(e.dataForm,"messageContent",a)},expression:"dataForm.messageContent"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"消息标题",prop:"messageHead"}},[t("el-input",{attrs:{placeholder:"消息标题"},model:{value:e.dataForm.messageHead,callback:function(a){e.$set(e.dataForm,"messageHead",a)},expression:"dataForm.messageHead"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"消息类型",prop:"messageType"}},[t("el-input",{attrs:{placeholder:"消息类型"},model:{value:e.dataForm.messageType,callback:function(a){e.$set(e.dataForm,"messageType",a)},expression:"dataForm.messageType"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"是否已读",prop:"haveRead"}},[t("el-input",{attrs:{placeholder:"是否已读"},model:{value:e.dataForm.haveRead,callback:function(a){e.$set(e.dataForm,"haveRead",a)},expression:"dataForm.haveRead"}})],1)],1),e._v(" "),t("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(a){e.visible=!1}}},[e._v("取消")]),e._v(" "),t("el-button",{attrs:{type:"primary"},on:{click:function(a){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},m=t("46Yf")(r,s,!1,null,null,null);a.default=m.exports}});