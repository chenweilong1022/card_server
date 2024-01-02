webpackJsonp([0],{"/caQ":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("HzJ8"),o=a.n(r),n={data:function(){return{codeAcquisitionType:null,type:2,platform:1,visible:!1,projectDataList:[],codeAcquisitionTypeOptions:[{value:1,label:"指定项目"},{value:2,label:"挂机模式"},{value:3,label:"自己注册"}],platformOptions:[{value:1,label:"火狐狸",url:"https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99"},{value:2,label:"沃码",url:"https://www.worldcode.win/ksapi.ashx?key=066131D94DADB4B3"}],options:[{value:1,label:"云存储"},{value:2,label:"项目配置"}],groupName:"",dataForm:{id:0,paramKey:"",paramValue:"",userId:"",platform:null,codeAcquisitionType:null,projectId:"",phonePre:"",codeApiUrl:"",remark:""},dataRule:{paramKey:[{required:!0,message:"参数名不能为空",trigger:"blur"}],paramValue:[{required:!0,message:"参数值不能为空",trigger:"blur"}]}}},methods:{platformHandler:function(e){var t=!0,a=!1,r=void 0;try{for(var n,l=o()(this.platformOptions);!(t=(n=l.next()).done);t=!0){var s=n.value;s.value===e&&(this.dataForm.codeApiUrl=s.url)}}catch(e){a=!0,r=e}finally{try{!t&&l.return&&l.return()}finally{if(a)throw r}}},init:function(e,t){var a=this;this.dataForm.id=e||0,this.groupName=t,this.visible=!0,this.projectList(),this.$nextTick(function(){a.$refs.dataForm.resetFields(),a.dataForm.id&&a.$http({url:a.$http.adornUrl("/sys/config/info/"+a.dataForm.id),method:"get",params:a.$http.adornParams()}).then(function(e){var t=e.data;t&&0===t.code&&(a.dataForm.paramKey=t.config.paramKey,a.dataForm.paramValue=t.config.paramValue,a.dataForm.remark=t.config.remark,a.dataForm.userId=t.config.userId,a.dataForm.projectId=t.config.projectId,a.dataForm.phonePre=t.config.phonePre,a.dataForm.codeApiUrl=t.config.codeApiUrl,a.codeAcquisitionType=t.config.codeAcquisitionType)})})},projectList:function(){var e=this;this.$http({url:this.$http.adornUrl("/ltt/cdproject/list"),method:"get",params:this.$http.adornParams({page:1,limit:100})}).then(function(t){var a=t.data;a&&0===a.code&&(e.projectDataList=a.page.list)})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/sys/config/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,paramKey:e.dataForm.paramKey,paramValue:e.dataForm.paramValue,userId:e.dataForm.userId,projectId:e.dataForm.projectId,phonePre:e.dataForm.phonePre,codeApiUrl:e.dataForm.codeApiUrl,type:e.type,platform:e.platform,codeAcquisitionType:e.codeAcquisitionType,remark:e.dataForm.remark})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改-"+e.groupName:"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"参数名"}},[a("el-select",{attrs:{placeholder:"类型",clearable:""},model:{value:e.type,callback:function(t){e.type=t},expression:"type"}},e._l(e.options,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"参数名",prop:"paramKey"}},[a("el-input",{attrs:{placeholder:"参数名"},model:{value:e.dataForm.paramKey,callback:function(t){e.$set(e.dataForm,"paramKey",t)},expression:"dataForm.paramKey"}})],1):e._e(),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"参数值",prop:"paramValue"}},[a("el-input",{attrs:{placeholder:"参数值"},model:{value:e.dataForm.paramValue,callback:function(t){e.$set(e.dataForm,"paramValue",t)},expression:"dataForm.paramValue"}})],1):e._e(),e._v(" "),1===e.type?a("el-form-item",{attrs:{label:"备注",prop:"remark"}},[a("el-input",{attrs:{placeholder:"备注"},model:{value:e.dataForm.remark,callback:function(t){e.$set(e.dataForm,"remark",t)},expression:"dataForm.remark"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"平台",prop:"platform"}},[a("el-select",{attrs:{placeholder:"平台",clearable:""},on:{change:e.platformHandler},model:{value:e.platform,callback:function(t){e.platform=t},expression:"platform"}},e._l(e.platformOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"接码平台接口",prop:"codeApiUrl"}},[a("el-input",{attrs:{placeholder:"备注"},model:{value:e.dataForm.codeApiUrl,callback:function(t){e.$set(e.dataForm,"codeApiUrl",t)},expression:"dataForm.codeApiUrl"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"项目id",prop:"projectId"}},[a("el-select",{attrs:{placeholder:"平台",clearable:""},on:{change:e.platformHandler},model:{value:e.dataForm.projectId,callback:function(t){e.$set(e.dataForm,"projectId",t)},expression:"dataForm.projectId"}},e._l(e.projectDataList,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})}))],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"手机号前缀",prop:"phonePre"}},[a("el-input",{attrs:{placeholder:"手机号前缀"},model:{value:e.dataForm.phonePre,callback:function(t){e.$set(e.dataForm,"phonePre",t)},expression:"dataForm.phonePre"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"用户id",prop:"userId"}},[a("el-input",{attrs:{placeholder:"用户id",readonly:""},model:{value:e.dataForm.userId,callback:function(t){e.$set(e.dataForm,"userId",t)},expression:"dataForm.userId"}})],1):e._e(),e._v(" "),2===e.type?a("el-form-item",{attrs:{label:"取码类型",prop:"codeAcquisitionType"}},[a("el-select",{attrs:{placeholder:"取码类型",clearable:""},model:{value:e.codeAcquisitionType,callback:function(t){e.codeAcquisitionType=t},expression:"codeAcquisitionType"}},e._l(e.codeAcquisitionTypeOptions,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1):e._e()],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},s=a("46Yf")(n,l,!1,null,null,null);t.default=s.exports},HzJ8:function(e,t,a){e.exports={default:a("vY6q"),__esModule:!0}},"XO/m":function(e,t,a){var r=a("7NgR"),o=a("/tnA");e.exports=a("DH3n").getIterator=function(e){var t=o(e);if("function"!=typeof t)throw TypeError(e+" is not iterable!");return r(t.call(e))}},"cdA+":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("0xDb"),o={data:function(){var e=this;return{visible:!1,dataForm:{password:"",newPassword:"",confirmPassword:""},dataRule:{password:[{required:!0,message:"原密码不能为空",trigger:"blur"}],newPassword:[{required:!0,message:"新密码不能为空",trigger:"blur"}],confirmPassword:[{required:!0,message:"确认密码不能为空",trigger:"blur"},{validator:function(t,a,r){e.dataForm.newPassword!==a?r(new Error("确认密码与新密码不一致")):r()},trigger:"blur"}]}}},computed:{userName:{get:function(){return this.$store.state.user.name}},mainTabs:{get:function(){return this.$store.state.common.mainTabs},set:function(e){this.$store.commit("common/updateMainTabs",e)}}},methods:{init:function(){var e=this;this.visible=!0,this.$nextTick(function(){e.$refs.dataForm.resetFields()})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/sys/user/password"),method:"post",data:e.$http.adornData({password:e.dataForm.password,newPassword:e.dataForm.newPassword})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$nextTick(function(){e.mainTabs=[],Object(r.a)(),e.$router.replace({name:"login"})})}}):e.$message.error(a.msg)})})}}},n={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:"修改密码",visible:e.visible,"append-to-body":!0},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"账号"}},[a("span",[e._v(e._s(e.userName))])]),e._v(" "),a("el-form-item",{attrs:{label:"原密码",prop:"password"}},[a("el-input",{attrs:{type:"password"},model:{value:e.dataForm.password,callback:function(t){e.$set(e.dataForm,"password",t)},expression:"dataForm.password"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"新密码",prop:"newPassword"}},[a("el-input",{attrs:{type:"password"},model:{value:e.dataForm.newPassword,callback:function(t){e.$set(e.dataForm,"newPassword",t)},expression:"dataForm.newPassword"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"确认密码",prop:"confirmPassword"}},[a("el-input",{attrs:{type:"password"},model:{value:e.dataForm.confirmPassword,callback:function(t){e.$set(e.dataForm,"confirmPassword",t)},expression:"dataForm.confirmPassword"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},l=a("46Yf")(o,n,!1,null,null,null);t.default=l.exports},oZaA:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={name:"sub-menu",props:{menu:{type:Object,required:!0},dynamicMenuRoutes:{type:Array,required:!0}},components:{SubMenu:l},computed:{sidebarLayoutSkin:{get:function(){return this.$store.state.common.sidebarLayoutSkin}}},methods:{gotoRouteHandle:function(e){var t=this.dynamicMenuRoutes.filter(function(t){return t.meta.menuId===e.menuId});t.length>=1&&this.$router.push({name:t[0].name})}}},o={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return e.menu.list&&e.menu.list.length>=1?a("el-submenu",{attrs:{index:e.menu.menuId+"","popper-class":"site-sidebar--"+e.sidebarLayoutSkin+"-popper"}},[a("template",{attrs:{slot:"title"},slot:"title"},[a("icon-svg",{staticClass:"site-sidebar__menu-icon",attrs:{name:e.menu.icon||""}}),e._v(" "),a("span",[e._v(e._s(e.menu.name))])],1),e._v(" "),e._l(e.menu.list,function(t){return a("sub-menu",{key:t.menuId,attrs:{menu:t,dynamicMenuRoutes:e.dynamicMenuRoutes}})})],2):a("el-menu-item",{attrs:{index:e.menu.menuId+""},on:{click:function(t){e.gotoRouteHandle(e.menu)}}},[a("icon-svg",{staticClass:"site-sidebar__menu-icon",attrs:{name:e.menu.icon||""}}),e._v(" "),a("span",[e._v(e._s(e.menu.name))])],1)},staticRenderFns:[]},n=a("46Yf")(r,o,!1,null,null,null),l=t.default=n.exports},vY6q:function(e,t,a){a("8LqW"),a("g5OY"),e.exports=a("XO/m")}});