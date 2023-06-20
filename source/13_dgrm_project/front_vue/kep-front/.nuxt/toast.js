import Vue from 'vue'
import Toasted from 'vue-toasted'

Vue.use(Toasted, {"position":"top-center","duration":3000,"className":"toast","icon":"check_circle","action":{"text":" X ","onClick":(e, toastObject) => {
        toastObject.goAway(0);
      }}})

const globals = [{"options":{"name":"custom toast"}}]
if(globals) {
  globals.forEach(global => {
    Vue.toasted.register(global.name, global.message, global.options)
  })
}

export default function (ctx, inject) {
  inject('toast', Vue.toasted)
}
