webpackJsonp([39],{vGLy:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{visible:!1,dataForm:{id:0,deviceId:"",number:"",deleteFlag:"",createTime:""},dataRule:{deviceId:[{required:!0,message:"设备id不能为空",trigger:"blur"}],number:[{required:!0,message:"编号不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cddevicesnumber/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.deviceId=a.cddevicesnumber.deviceId,t.dataForm.number=a.cddevicesnumber.number,t.dataForm.deleteFlag=a.cddevicesnumber.deleteFlag,t.dataForm.createTime=a.cddevicesnumber.createTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cddevicesnumber/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,deviceId:e.dataForm.deviceId,number:e.dataForm.number,deleteFlag:e.dataForm.deleteFlag,createTime:e.dataForm.createTime})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},d={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"设备id",prop:"deviceId"}},[a("el-input",{attrs:{placeholder:"设备id"},model:{value:e.dataForm.deviceId,callback:function(t){e.$set(e.dataForm,"deviceId",t)},expression:"dataForm.deviceId"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"编号",prop:"number"}},[a("el-input",{attrs:{placeholder:"编号"},model:{value:e.dataForm.number,callback:function(t){e.$set(e.dataForm,"number",t)},expression:"dataForm.number"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"删除标志",prop:"deleteFlag"}},[a("el-input",{attrs:{placeholder:"删除标志"},model:{value:e.dataForm.deleteFlag,callback:function(t){e.$set(e.dataForm,"deleteFlag",t)},expression:"dataForm.deleteFlag"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"创建时间",prop:"createTime"}},[a("el-input",{attrs:{placeholder:"创建时间"},model:{value:e.dataForm.createTime,callback:function(t){e.$set(e.dataForm,"createTime",t)},expression:"dataForm.createTime"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},i=a("46Yf")(r,d,!1,null,null,null);t.default=i.exports}});