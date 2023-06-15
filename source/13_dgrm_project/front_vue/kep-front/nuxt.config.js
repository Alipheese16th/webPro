export default {
  srcDir: 'src/',
  /*
   ** Nuxt rendering mode
   ** See https://nuxtjs.org/api/configuration-mode
   */
  mode: 'spa',
  /*
   ** Nuxt target
   ** See https://nuxtjs.org/api/configuration-target
   */
  target: 'server',
  /*
   ** Headers of the  page
   ** See https://nuxtjs.org/api/configuration-head
   */
  head: {
    titleTemplate: process.env.npm_package_name,
    title: process.env.npm_package_name || '',
    meta: [
      { 'http-equiv': 'X-UA-Compatible', content: 'IE=11' },
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || '',
      },
    ],
    script: [{ src: '~/assets/js/shim.min.js' }, { src: '~/assets/js/xlsx.full.min.js' }],
    // script: [{ src: '/js/shim.min.js' }, { src: '/js/xlsx.full.min.js' }],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      { rel: 'stylesheet', href: 'https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons' },
      { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css' },
      { rel: 'stylesheet', href: 'https://cdnjs.cloudflare.com/ajax/libs/MaterialDesign-Webfont/4.4.95/css/materialdesignicons.min.css' },
      { rel: 'stylesheet', href: 'https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0' },
    ],
  },
  /*
   ** Global CSS
   */
  css: [
    '~/assets/font/font.css',
    '~/assets/css/design.scss',
    '~/assets/css/realgrid-style.css',
    'quill/dist/quill.snow.css',
    'quill/dist/quill.bubble.css',
    'quill/dist/quill.core.css',
  ],
  /*
   ** Plugins to load before mounting the App
   ** https://nuxtjs.org/guide/plugins
   */
  plugins: [
    '~/plugins/axios',
    '~/plugins/routerTab',
    { src: '~/plugins/vue-quill-editor.js', ssr: false },
    { src: '~/plugins/v-confirm.js', ssr: false },
    '~/plugins/timeZoneDate',
  ],
  loading: '~/components/LoadingBar.vue',
  /*
   ** Auto import components
   ** See https://nuxtjs.org/api/configuration-components
   */
  components: false,
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
    '@nuxtjs/vuetify',
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/toast',
    '@nuxtjs/i18n',
  ],
  server: {
    // port: 80,
    // host: '192.168.0.180',
  },
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    credentials: true,
    baseURL: 'http://localhost:8081', // local
    // baseURL: 'https://192.168.0.180:8443', // local
  },
  i18n: {
    locales: ['ko'],
    defaultLocale: 'ko',
    strategy: 'no_prefix',
    vueI18n: {
      fallbackLocale: 'ko',
      messages: {
        ko: { sample: '샘플' },
      },
    },
  },
  approvalUrl1: '', // 결재url
  /*
   ** vuetify module configuration
   ** https://github.com/nuxt-community/vuetify-module
   */
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    // defaultAssets: false,
    theme: {
      dark: false,
      default: 'light',
      disable: false,
      options: {
        cspNonce: undefined,
        customProperties: undefined,
        minifyTheme: undefined,
        themeCache: undefined,
        variations: true,
      },
      themes: {
        light: {
          primary: '#222',
          secondary: '#444',
          accent: '#82B1FF',
          error: '#FF5252',
          info: '#2196F3',
          success: '#4CAF50',
          warning: '#FB8C00',
        },
        dark: {
          primary: '#2196F3',
          secondary: '#424242',
          accent: '#FF4081',
          error: '#FF5252',
          info: '#2196F3',
          success: '#4CAF50',
          warning: '#FB8C00',
        },
      },
    },
  },
  /*
   ** Build configuration
   ** See https://nuxtjs.org/api/configuration-build/
   */
  build: {
    // Babel transpile dependencies
    transpile: ['vue-router-tab'],
  },
  loaders: {
    css: {
      modules: true,
    },
  },
  /*
   ** toast module configuration
   ** https://github.com/nuxt-community/modules/tree/master/packages/toast
   */
  toast: {
    position: 'top-center',
    duration: 3000,
    className: 'toast',
    icon: 'check_circle',
    action: {
      text: ' X ',
      onClick: (e, toastObject) => {
        toastObject.goAway(0)
      },
    },
    register: [
      // Register custom toasts
      {
        options: {
          name: 'custom toast',
        },
      },
    ],
  },
}
