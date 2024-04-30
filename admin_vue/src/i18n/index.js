import Vue from 'vue'
import VueI18n from 'vue-i18n'

import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import ElementLocale from 'element-ui/lib/locale'

Vue.use(VueI18n)

const messages = {
  en: {
    message: 'hello1122',
    ...enLocale
  },
  zh: {
    message: '你好',
    ...zhLocale
  }
}

const i18n = new VueI18n({
  locale: 'zh',
  messages
})

ElementLocale.i18n((key, value) => i18n.t(key, value))
export default i18n;
