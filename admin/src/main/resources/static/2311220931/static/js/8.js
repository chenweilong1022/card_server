webpackJsonp([8],{G5Dz:function(t,e,n){(t.exports=n("acE3")(!1)).push([t.i,"\n.mod-demo-ueditor {\n  position: relative;\n  z-index: 510;\n}\n.mod-demo-ueditor > .el-alert {\n    margin-bottom: 10px;\n}\n",""])},IZno:function(t,e,n){var i=n("G5Dz");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);n("XkoO")("b5304840",i,!0)},t1fE:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=n("fZIC"),o=n.n(i),s={props:["pp"],data:function(){return{ue:null,ueId:"J_ueditorBox_"+(new Date).getTime(),ueContent:"",dialogVisible:!1}},mounted:function(){this.ue=o.a.getEditor(this.ueId,{serverUrl:this.$http.adornUrl("/app/file/config"),zIndex:3e3})},methods:{init:function(t){this.ue.setContent(t)},getContent:function(){var t=this;return this.dialogVisible=!0,this.ue.ready(function(){t.ueContent=t.ue.getContent()}),this.ueContent}}},r={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"mod-demo-ueditor"},[e("script",{staticClass:"ueditor-box",staticStyle:{width:"100%",height:"260px"},attrs:{id:this.ueId,type:"text/plain"}})])},staticRenderFns:[]};var u=n("46Yf")(s,r,!1,function(t){n("IZno")},null,null);e.default=u.exports}});