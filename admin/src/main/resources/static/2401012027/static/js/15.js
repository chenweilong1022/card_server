webpackJsonp([15,33],{DpAR:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{dataForm:{key:""},dataList:[],pageIndex:1,pageSize:10,totalPage:0,dataListLoading:!1,dataListSelections:[],addOrUpdateVisible:!1}},components:{AddOrUpdate:a("q8si").default},activated:function(){this.getDataList()},methods:{getDataList:function(){var e=this;this.dataListLoading=!0,this.$http({url:this.$http.adornUrl("/ltt/lttuser/list"),method:"get",params:this.$http.adornParams({page:this.pageIndex,limit:this.pageSize,key:this.dataForm.key})}).then(function(t){var a=t.data;a&&0===a.code?(e.dataList=a.page.list,e.totalPage=a.page.totalCount):(e.dataList=[],e.totalPage=0),e.dataListLoading=!1})},sizeChangeHandle:function(e){this.pageSize=e,this.pageIndex=1,this.getDataList()},currentChangeHandle:function(e){this.pageIndex=e,this.getDataList()},selectionChangeHandle:function(e){this.dataListSelections=e},addOrUpdateHandle:function(e){var t=this;this.addOrUpdateVisible=!0,this.$nextTick(function(){t.$refs.addOrUpdate.init(e)})},deleteHandle:function(e){var t=this,a=e?[e]:this.dataListSelections.map(function(e){return e.id});this.$confirm("确定对[id="+a.join(",")+"]进行["+(e?"删除":"批量删除")+"]操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){t.$http({url:t.$http.adornUrl("/ltt/lttuser/delete"),method:"post",data:t.$http.adornData(a,!1)}).then(function(e){var a=e.data;a&&0===a.code?t.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){t.getDataList()}}):t.$message.error(a.msg)})})}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mod-config"},[a("el-form",{attrs:{inline:!0,model:e.dataForm},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.getDataList()}}},[a("el-form-item",[a("el-input",{attrs:{placeholder:"参数名",clearable:""},model:{value:e.dataForm.key,callback:function(t){e.$set(e.dataForm,"key",t)},expression:"dataForm.key"}})],1),e._v(" "),a("el-form-item",[a("el-button",{on:{click:function(t){e.getDataList()}}},[e._v("查询")]),e._v(" "),e.isAuth("ltt:lttuser:save")?a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.addOrUpdateHandle()}}},[e._v("新增")]):e._e(),e._v(" "),e.isAuth("ltt:lttuser:delete")?a("el-button",{attrs:{type:"danger",disabled:e.dataListSelections.length<=0},on:{click:function(t){e.deleteHandle()}}},[e._v("批量删除")]):e._e()],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.dataListLoading,expression:"dataListLoading"}],staticStyle:{width:"100%"},attrs:{data:e.dataList,border:""},on:{"selection-change":e.selectionChangeHandle}},[a("el-table-column",{attrs:{type:"selection","header-align":"center",align:"center",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"id","header-align":"center",align:"center",label:"id"}}),e._v(" "),a("el-table-column",{attrs:{prop:"nickname","header-align":"center",align:"center",label:"昵称"}}),e._v(" "),a("el-table-column",{attrs:{prop:"sex","header-align":"center",align:"center",label:"性别"}}),e._v(" "),a("el-table-column",{attrs:{prop:"sexDesc","header-align":"center",align:"center",label:"性别描述"}}),e._v(" "),a("el-table-column",{attrs:{prop:"countryCode","header-align":"center",align:"center",label:"区号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"language","header-align":"center",align:"center",label:"语言"}}),e._v(" "),a("el-table-column",{attrs:{prop:"country","header-align":"center",align:"center",label:"国家"}}),e._v(" "),a("el-table-column",{attrs:{prop:"province","header-align":"center",align:"center",label:"省"}}),e._v(" "),a("el-table-column",{attrs:{prop:"city","header-align":"center",align:"center",label:"市"}}),e._v(" "),a("el-table-column",{attrs:{prop:"headimgUrl","header-align":"center",align:"center",label:"微信头像"}}),e._v(" "),a("el-table-column",{attrs:{prop:"openid","header-align":"center",align:"center",label:"openId"}}),e._v(" "),a("el-table-column",{attrs:{prop:"token","header-align":"center",align:"center",label:"token"}}),e._v(" "),a("el-table-column",{attrs:{prop:"money","header-align":"center",align:"center",label:"累计消费金额"}}),e._v(" "),a("el-table-column",{attrs:{prop:"number","header-align":"center",align:"center",label:"累计使用次数"}}),e._v(" "),a("el-table-column",{attrs:{prop:"phoneNumber","header-align":"center",align:"center",label:"用户绑定的手机号（国外手机号会有区号）"}}),e._v(" "),a("el-table-column",{attrs:{prop:"mobile","header-align":"center",align:"center",label:"没有区号的手机号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"brand","header-align":"center",align:"center",label:"手机品牌"}}),e._v(" "),a("el-table-column",{attrs:{prop:"model","header-align":"center",align:"center",label:"手机型号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"version","header-align":"center",align:"center",label:"微信版本号"}}),e._v(" "),a("el-table-column",{attrs:{prop:"system","header-align":"center",align:"center",label:"操作系统版本"}}),e._v(" "),a("el-table-column",{attrs:{prop:"platform","header-align":"center",align:"center",label:"客户端平台"}}),e._v(" "),a("el-table-column",{attrs:{prop:"sdkVersion","header-align":"center",align:"center",label:"客户端基础库版本"}}),e._v(" "),a("el-table-column",{attrs:{prop:"ip","header-align":"center",align:"center",label:"ip地址"}}),e._v(" "),a("el-table-column",{attrs:{prop:"latitude","header-align":"center",align:"center",label:"纬度"}}),e._v(" "),a("el-table-column",{attrs:{prop:"longitude","header-align":"center",align:"center",label:"经度"}}),e._v(" "),a("el-table-column",{attrs:{prop:"source","header-align":"center",align:"center",label:"1:公众号2:小程序"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isAuth","header-align":"center",align:"center",label:"1:未授权2:已授权"}}),e._v(" "),a("el-table-column",{attrs:{prop:"isPhone","header-align":"center",align:"center",label:"1:未授权2:已授权"}}),e._v(" "),a("el-table-column",{attrs:{prop:"createTime","header-align":"center",align:"center",label:"创建时间"}}),e._v(" "),a("el-table-column",{attrs:{prop:"updateTime","header-align":"center",align:"center",label:"修改时间"}}),e._v(" "),a("el-table-column",{attrs:{fixed:"right","header-align":"center",align:"center",width:"150",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.addOrUpdateHandle(t.row.id)}}},[e._v("修改")]),e._v(" "),a("el-button",{attrs:{type:"text",size:"small"},on:{click:function(a){e.deleteHandle(t.row.id)}}},[e._v("删除")])]}}])})],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.pageIndex,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.totalPage,layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.sizeChangeHandle,"current-change":e.currentChangeHandle}}),e._v(" "),e.addOrUpdateVisible?a("add-or-update",{ref:"addOrUpdate",on:{refreshDataList:e.getDataList}}):e._e()],1)},staticRenderFns:[]},o=a("46Yf")(r,l,!1,null,null,null);t.default=o.exports},q8si:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r={data:function(){return{visible:!1,dataForm:{id:0,nickname:"",sex:"",sexDesc:"",countryCode:"",language:"",country:"",province:"",city:"",headimgUrl:"",openid:"",token:"",money:"",number:"",phoneNumber:"",mobile:"",brand:"",model:"",version:"",system:"",platform:"",sdkVersion:"",ip:"",latitude:"",longitude:"",source:"",isAuth:"",isPhone:"",createTime:"",updateTime:""},dataRule:{nickname:[{required:!0,message:"昵称不能为空",trigger:"blur"}],sex:[{required:!0,message:"性别不能为空",trigger:"blur"}],sexDesc:[{required:!0,message:"性别描述不能为空",trigger:"blur"}],countryCode:[{required:!0,message:"区号不能为空",trigger:"blur"}],language:[{required:!0,message:"语言不能为空",trigger:"blur"}],country:[{required:!0,message:"国家不能为空",trigger:"blur"}],province:[{required:!0,message:"省不能为空",trigger:"blur"}],city:[{required:!0,message:"市不能为空",trigger:"blur"}],headimgUrl:[{required:!0,message:"微信头像不能为空",trigger:"blur"}],openid:[{required:!0,message:"openId不能为空",trigger:"blur"}],token:[{required:!0,message:"token不能为空",trigger:"blur"}],money:[{required:!0,message:"累计消费金额不能为空",trigger:"blur"}],number:[{required:!0,message:"累计使用次数不能为空",trigger:"blur"}],phoneNumber:[{required:!0,message:"用户绑定的手机号（国外手机号会有区号）不能为空",trigger:"blur"}],mobile:[{required:!0,message:"没有区号的手机号不能为空",trigger:"blur"}],brand:[{required:!0,message:"手机品牌不能为空",trigger:"blur"}],model:[{required:!0,message:"手机型号不能为空",trigger:"blur"}],version:[{required:!0,message:"微信版本号不能为空",trigger:"blur"}],system:[{required:!0,message:"操作系统版本不能为空",trigger:"blur"}],platform:[{required:!0,message:"客户端平台不能为空",trigger:"blur"}],sdkVersion:[{required:!0,message:"客户端基础库版本不能为空",trigger:"blur"}],ip:[{required:!0,message:"ip地址不能为空",trigger:"blur"}],latitude:[{required:!0,message:"纬度不能为空",trigger:"blur"}],longitude:[{required:!0,message:"经度不能为空",trigger:"blur"}],source:[{required:!0,message:"1:公众号2:小程序不能为空",trigger:"blur"}],isAuth:[{required:!0,message:"1:未授权2:已授权不能为空",trigger:"blur"}],isPhone:[{required:!0,message:"1:未授权2:已授权不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}],updateTime:[{required:!0,message:"修改时间不能为空",trigger:"blur"}]}}},methods:{init:function(e){var t=this;this.dataForm.id=e||0,this.visible=!0,this.$nextTick(function(){t.$refs.dataForm.resetFields(),t.dataForm.id&&t.$http({url:t.$http.adornUrl("/ltt/lttuser/info/"+t.dataForm.id),method:"get",params:t.$http.adornParams()}).then(function(e){var a=e.data;a&&0===a.code&&(t.dataForm.nickname=a.lttuser.nickname,t.dataForm.sex=a.lttuser.sex,t.dataForm.sexDesc=a.lttuser.sexDesc,t.dataForm.countryCode=a.lttuser.countryCode,t.dataForm.language=a.lttuser.language,t.dataForm.country=a.lttuser.country,t.dataForm.province=a.lttuser.province,t.dataForm.city=a.lttuser.city,t.dataForm.headimgUrl=a.lttuser.headimgUrl,t.dataForm.openid=a.lttuser.openid,t.dataForm.token=a.lttuser.token,t.dataForm.money=a.lttuser.money,t.dataForm.number=a.lttuser.number,t.dataForm.phoneNumber=a.lttuser.phoneNumber,t.dataForm.mobile=a.lttuser.mobile,t.dataForm.brand=a.lttuser.brand,t.dataForm.model=a.lttuser.model,t.dataForm.version=a.lttuser.version,t.dataForm.system=a.lttuser.system,t.dataForm.platform=a.lttuser.platform,t.dataForm.sdkVersion=a.lttuser.sdkVersion,t.dataForm.ip=a.lttuser.ip,t.dataForm.latitude=a.lttuser.latitude,t.dataForm.longitude=a.lttuser.longitude,t.dataForm.source=a.lttuser.source,t.dataForm.isAuth=a.lttuser.isAuth,t.dataForm.isPhone=a.lttuser.isPhone,t.dataForm.createTime=a.lttuser.createTime,t.dataForm.updateTime=a.lttuser.updateTime)})})},dataFormSubmit:function(){var e=this;this.$refs.dataForm.validate(function(t){t&&e.$http({url:e.$http.adornUrl("/ltt/lttuser/"+(e.dataForm.id?"update":"save")),method:"post",data:e.$http.adornData({id:e.dataForm.id||void 0,nickname:e.dataForm.nickname,sex:e.dataForm.sex,sexDesc:e.dataForm.sexDesc,countryCode:e.dataForm.countryCode,language:e.dataForm.language,country:e.dataForm.country,province:e.dataForm.province,city:e.dataForm.city,headimgUrl:e.dataForm.headimgUrl,openid:e.dataForm.openid,token:e.dataForm.token,money:e.dataForm.money,number:e.dataForm.number,phoneNumber:e.dataForm.phoneNumber,mobile:e.dataForm.mobile,brand:e.dataForm.brand,model:e.dataForm.model,version:e.dataForm.version,system:e.dataForm.system,platform:e.dataForm.platform,sdkVersion:e.dataForm.sdkVersion,ip:e.dataForm.ip,latitude:e.dataForm.latitude,longitude:e.dataForm.longitude,source:e.dataForm.source,isAuth:e.dataForm.isAuth,isPhone:e.dataForm.isPhone,createTime:e.dataForm.createTime,updateTime:e.dataForm.updateTime})}).then(function(t){var a=t.data;a&&0===a.code?e.$message({message:"操作成功",type:"success",duration:1500,onClose:function(){e.visible=!1,e.$emit("refreshDataList")}}):e.$message.error(a.msg)})})}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dialog",{attrs:{title:e.dataForm.id?"修改":"新增","close-on-click-modal":!1,visible:e.visible},on:{"update:visible":function(t){e.visible=t}}},[a("el-form",{ref:"dataForm",attrs:{model:e.dataForm,rules:e.dataRule,"label-width":"80px"},nativeOn:{keyup:function(t){if(!("button"in t)&&e._k(t.keyCode,"enter",13,t.key))return null;e.dataFormSubmit()}}},[a("el-form-item",{attrs:{label:"昵称",prop:"nickname"}},[a("el-input",{attrs:{placeholder:"昵称"},model:{value:e.dataForm.nickname,callback:function(t){e.$set(e.dataForm,"nickname",t)},expression:"dataForm.nickname"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"性别",prop:"sex"}},[a("el-input",{attrs:{placeholder:"性别"},model:{value:e.dataForm.sex,callback:function(t){e.$set(e.dataForm,"sex",t)},expression:"dataForm.sex"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"性别描述",prop:"sexDesc"}},[a("el-input",{attrs:{placeholder:"性别描述"},model:{value:e.dataForm.sexDesc,callback:function(t){e.$set(e.dataForm,"sexDesc",t)},expression:"dataForm.sexDesc"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"区号",prop:"countryCode"}},[a("el-input",{attrs:{placeholder:"区号"},model:{value:e.dataForm.countryCode,callback:function(t){e.$set(e.dataForm,"countryCode",t)},expression:"dataForm.countryCode"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"语言",prop:"language"}},[a("el-input",{attrs:{placeholder:"语言"},model:{value:e.dataForm.language,callback:function(t){e.$set(e.dataForm,"language",t)},expression:"dataForm.language"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"国家",prop:"country"}},[a("el-input",{attrs:{placeholder:"国家"},model:{value:e.dataForm.country,callback:function(t){e.$set(e.dataForm,"country",t)},expression:"dataForm.country"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"省",prop:"province"}},[a("el-input",{attrs:{placeholder:"省"},model:{value:e.dataForm.province,callback:function(t){e.$set(e.dataForm,"province",t)},expression:"dataForm.province"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"市",prop:"city"}},[a("el-input",{attrs:{placeholder:"市"},model:{value:e.dataForm.city,callback:function(t){e.$set(e.dataForm,"city",t)},expression:"dataForm.city"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"微信头像",prop:"headimgUrl"}},[a("el-input",{attrs:{placeholder:"微信头像"},model:{value:e.dataForm.headimgUrl,callback:function(t){e.$set(e.dataForm,"headimgUrl",t)},expression:"dataForm.headimgUrl"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"openId",prop:"openid"}},[a("el-input",{attrs:{placeholder:"openId"},model:{value:e.dataForm.openid,callback:function(t){e.$set(e.dataForm,"openid",t)},expression:"dataForm.openid"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"token",prop:"token"}},[a("el-input",{attrs:{placeholder:"token"},model:{value:e.dataForm.token,callback:function(t){e.$set(e.dataForm,"token",t)},expression:"dataForm.token"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"累计消费金额",prop:"money"}},[a("el-input",{attrs:{placeholder:"累计消费金额"},model:{value:e.dataForm.money,callback:function(t){e.$set(e.dataForm,"money",t)},expression:"dataForm.money"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"累计使用次数",prop:"number"}},[a("el-input",{attrs:{placeholder:"累计使用次数"},model:{value:e.dataForm.number,callback:function(t){e.$set(e.dataForm,"number",t)},expression:"dataForm.number"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"用户绑定的手机号（国外手机号会有区号）",prop:"phoneNumber"}},[a("el-input",{attrs:{placeholder:"用户绑定的手机号（国外手机号会有区号）"},model:{value:e.dataForm.phoneNumber,callback:function(t){e.$set(e.dataForm,"phoneNumber",t)},expression:"dataForm.phoneNumber"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"没有区号的手机号",prop:"mobile"}},[a("el-input",{attrs:{placeholder:"没有区号的手机号"},model:{value:e.dataForm.mobile,callback:function(t){e.$set(e.dataForm,"mobile",t)},expression:"dataForm.mobile"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"手机品牌",prop:"brand"}},[a("el-input",{attrs:{placeholder:"手机品牌"},model:{value:e.dataForm.brand,callback:function(t){e.$set(e.dataForm,"brand",t)},expression:"dataForm.brand"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"手机型号",prop:"model"}},[a("el-input",{attrs:{placeholder:"手机型号"},model:{value:e.dataForm.model,callback:function(t){e.$set(e.dataForm,"model",t)},expression:"dataForm.model"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"微信版本号",prop:"version"}},[a("el-input",{attrs:{placeholder:"微信版本号"},model:{value:e.dataForm.version,callback:function(t){e.$set(e.dataForm,"version",t)},expression:"dataForm.version"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"操作系统版本",prop:"system"}},[a("el-input",{attrs:{placeholder:"操作系统版本"},model:{value:e.dataForm.system,callback:function(t){e.$set(e.dataForm,"system",t)},expression:"dataForm.system"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"客户端平台",prop:"platform"}},[a("el-input",{attrs:{placeholder:"客户端平台"},model:{value:e.dataForm.platform,callback:function(t){e.$set(e.dataForm,"platform",t)},expression:"dataForm.platform"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"客户端基础库版本",prop:"sdkVersion"}},[a("el-input",{attrs:{placeholder:"客户端基础库版本"},model:{value:e.dataForm.sdkVersion,callback:function(t){e.$set(e.dataForm,"sdkVersion",t)},expression:"dataForm.sdkVersion"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"ip地址",prop:"ip"}},[a("el-input",{attrs:{placeholder:"ip地址"},model:{value:e.dataForm.ip,callback:function(t){e.$set(e.dataForm,"ip",t)},expression:"dataForm.ip"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"纬度",prop:"latitude"}},[a("el-input",{attrs:{placeholder:"纬度"},model:{value:e.dataForm.latitude,callback:function(t){e.$set(e.dataForm,"latitude",t)},expression:"dataForm.latitude"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"经度",prop:"longitude"}},[a("el-input",{attrs:{placeholder:"经度"},model:{value:e.dataForm.longitude,callback:function(t){e.$set(e.dataForm,"longitude",t)},expression:"dataForm.longitude"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"1:公众号2:小程序",prop:"source"}},[a("el-input",{attrs:{placeholder:"1:公众号2:小程序"},model:{value:e.dataForm.source,callback:function(t){e.$set(e.dataForm,"source",t)},expression:"dataForm.source"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"1:未授权2:已授权",prop:"isAuth"}},[a("el-input",{attrs:{placeholder:"1:未授权2:已授权"},model:{value:e.dataForm.isAuth,callback:function(t){e.$set(e.dataForm,"isAuth",t)},expression:"dataForm.isAuth"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"1:未授权2:已授权",prop:"isPhone"}},[a("el-input",{attrs:{placeholder:"1:未授权2:已授权"},model:{value:e.dataForm.isPhone,callback:function(t){e.$set(e.dataForm,"isPhone",t)},expression:"dataForm.isPhone"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"创建时间",prop:"createTime"}},[a("el-input",{attrs:{placeholder:"创建时间"},model:{value:e.dataForm.createTime,callback:function(t){e.$set(e.dataForm,"createTime",t)},expression:"dataForm.createTime"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"修改时间",prop:"updateTime"}},[a("el-input",{attrs:{placeholder:"修改时间"},model:{value:e.dataForm.updateTime,callback:function(t){e.$set(e.dataForm,"updateTime",t)},expression:"dataForm.updateTime"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.visible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dataFormSubmit()}}},[e._v("确定")])],1)],1)},staticRenderFns:[]},o=a("46Yf")(r,l,!1,null,null,null);t.default=o.exports}});