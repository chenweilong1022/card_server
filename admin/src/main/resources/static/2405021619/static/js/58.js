webpackJsonp([58],{"6i8u":function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var l={computed:{navbarLayoutType:{get:function(){return this.$store.state.common.navbarLayoutType},set:function(e){this.$store.commit("common/updateNavbarLayoutType",e)}},sidebarLayoutSkin:{get:function(){return this.$store.state.common.sidebarLayoutSkin},set:function(e){this.$store.commit("common/updateSidebarLayoutSkin",e)}}},created:function(){this.selectValue=void 0===localStorage.lang?"zh":localStorage.lang},data:function(){return{selectValue:""}},methods:{langChange:function(e){console.log(e),localStorage.setItem("lang",e),this.$i18n.locale=e}}},o={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("el-form",[t("h2",[e._v("布局设置")]),e._v(" "),t("el-form-item",{attrs:{label:"导航条类型"}},[t("el-radio-group",{model:{value:e.navbarLayoutType,callback:function(a){e.navbarLayoutType=a},expression:"navbarLayoutType"}},[t("el-radio",{attrs:{label:"default",border:""}},[e._v("default")]),e._v(" "),t("el-radio",{attrs:{label:"inverse",border:""}},[e._v("inverse")])],1)],1),e._v(" "),t("el-form-item",{attrs:{label:"侧边栏皮肤"}},[t("el-radio-group",{model:{value:e.sidebarLayoutSkin,callback:function(a){e.sidebarLayoutSkin=a},expression:"sidebarLayoutSkin"}},[t("el-radio",{attrs:{label:"light",border:""}},[e._v("light")]),e._v(" "),t("el-radio",{attrs:{label:"dark",border:""}},[e._v("dark")])],1)],1),e._v(" "),t("el-form-item",{attrs:{label:"语言"}},[t("el-radio-group",{on:{change:e.langChange},model:{value:e.selectValue,callback:function(a){e.selectValue=a},expression:"selectValue"}},[t("el-radio",{attrs:{label:"zh"}},[e._v("简体中文")]),e._v(" "),t("el-radio",{attrs:{label:"km"}},[e._v("កម្ពុជា។")])],1)],1)],1)},staticRenderFns:[]},r=t("46Yf")(l,o,!1,null,null,null);a.default=r.exports}});