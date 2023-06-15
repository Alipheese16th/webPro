import Vue from 'vue'
import VuetifyConfirm from 'vuetify-confirm'

export default ({ app }) => {
  Vue.use(VuetifyConfirm, {
    vuetify: app.vuetify,
    buttonTrueText: '예',
    buttonFalseText: '아니오',
    buttonTrueColor: 'comfirm_y',
    buttonFalseColor: 'comfirm_n',
    buttonTrueFlat: false,
    buttonFalseFlat: false,
    iconfont: 'md',
    color: 'confirm',
    icon: 'confirm',
    title: 'confirm',
    width: 520,
  })
}
