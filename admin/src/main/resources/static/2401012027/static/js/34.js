webpackJsonp([34],{"do/j":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{visible:!1,dataForm:{id:0,token:"",balance:"",password:"",account:"",deleteFlag:"",createTime:""},dataRule:{token:[{required:!0,message:"秘钥不能为空",trigger:"blur"}],balance:[{required:!0,message:"余额不能为空",trigger:"blur"}],password:[{required:!0,message:"密码不能为空",trigger:"blur"}],account:[{required:!0,message:"账号不能为空",trigger:"blur"}],deleteFlag:[{required:!0,message:"删除标志不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/cduser/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.token=a.cdUser.token,t.dataForm.balance=a.cdUser.balance,t.dataForm.password=a.cdUser.password,t.dataForm.account=a.cdUser.account,t.dataForm.deleteFlag=a.cdUser.deleteFlag,t.dataForm.createTime=a.cdUser.createTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/cduser/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,token:e.dataForm.token,balance:e.dataForm.balance,password:e.dataForm.password,account:e.dataForm.account,deleteFlag:e.dataForm.deleteFlag,createTime:e.dataForm.createTime})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},o={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"密码",prop:"password"}},[a("el-input",{attrs:{placeholder:"密码"},model:{value:e.dataForm.password,callback:function(t){e.$set(e.dataForm,"password",t)},expression:"dataForm.password"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"账号",prop:"account"}},[a("el-input",{attrs:{placeholder:"账号"},model:{value:e.dataForm.account,callback:function(t){e.$set(e.dataForm,"account",t)},expression:"dataForm.account"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},d=a("46Yf")(r,o,!1,null,null,null);t.default=d.exports}});