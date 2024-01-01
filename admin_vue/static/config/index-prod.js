/**
 * 生产环境
 */
;(function () {
  window.SITE_CONFIG = {};

  // api接口请求地址 test
  // window.SITE_CONFIG['baseUrl'] = 'http://124.220.235.81:8880/';
  // 正式
  window.SITE_CONFIG['baseUrl'] = 'http://134.122.130.205:8880/';
  // 本地
  // window.SITE_CONFIG['baseUrl'] = 'http://localhost:8880/'

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain']  = './'; // 域名
  window.SITE_CONFIG['version'] = '';   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl']  = window.SITE_CONFIG.domain + window.SITE_CONFIG.version;
})();
