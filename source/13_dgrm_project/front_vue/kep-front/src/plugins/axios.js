export default function ({ $axios, redirect, store }) {
  $axios.onRequest((config) => {
    config.withCredentials = true

    store._vm.$nextTick(() => {
      store._vm.$nuxt.$loading.start()
      return config
    })

    // return config
  })

  $axios.onResponse((response) => {
    store._vm.$nextTick(() => {
      store._vm.$nuxt.$loading.finish()
      // setTimeout(() => store._vm.$nuxt.$loading.finish(), 2000)
      return response
    })
  })

  $axios.onError((error) => {
    const code = parseInt(error.response && error.response.status)
    console.warn('onError Code : ', code) // eslint-disable-line no-console
    if (code === 401) {
      redirect('/authInfo')
    } else if (code === 403) {
      redirect('/forbidden')
    } else if (code === 404) {
      redirect('/notfound')
    } else {
      redirect('/errorInfo')
    }
  })
}
