<template>
  <v-app id="app">
    <v-app-bar id="nav" :clipped-left="$vuetify.breakpoint.lgAndUp" app flat>
      <div class="nav_wrap--left">
        <v-toolbar-title>
          <!-- 열기 -->
          <button class="btn_sitemap" @click="siteMap = !siteMap">{{ $t('LB00000348') }}</button>
          <button class="btn_home" style="white-space: nowrap; overflow: hidden" @click.stop="goHome">home</button>
        </v-toolbar-title>
        <div class="top_menu_wrap">
          <v-btn v-for="menu in mainMenus" :key="menu.menu_no" :to="menu.menu_url" text tile class="btn_topmenu"
            @click.stop="
              drawer = true
            id = menu.menu_no
            subTitle = menu.menu_nm
              ">
            {{ menu.menu_nm }}
          </v-btn>
        </div>
      </div>
      <div class="nav_wrap--right">
        <v-btn class="btn_top"><img src="~assets/images/btn_top_sch.svg" alt="" /></v-btn>
        <v-btn class="btn_top"><img src="~assets/images/btn_top_alarm.svg" alt="" /></v-btn>
        <v-btn class="btn_top"><img src="~assets/images/btn_top_user.svg" alt="" /></v-btn>
        <v-btn class="btn_top"><img src="~assets/images/btn_top_allmenu.svg" alt="" /></v-btn>
        <v-btn class="btn_top"><img src="~assets/images/btn_top_lang.svg" alt="" /></v-btn>
        <!-- <div style="white-space: nowrap; overflow: hidden">
          <span class="text-decoration-none font-weight-bold font_gray">{{ getUser.emp_nm }}</span
          ><span class="mrR8">{{ $t('MS00000147') }}</span
          ><span>({{ $t('LB00000526') }} : {{ getUser.last_login_dt }} / {{ getUser.last_ip_addr }})</span>
        </div> -->
        <!-- <v-menu v-model="config" :close-on-content-click="false" :nudge-width="200" offset-y class="container_set">
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on"><img src="~assets/images/btn_top_set.png" /></v-btn>
          </template>
          <div class="set_layout">
            <v-row>
              <v-col cols="12">
                <h3 class="mb-4">{{ $t('LB00000349') }}</h3>
                <div class="set_col mb-3">
                  <div class="label_con mb-1">{{ $t('LB00000021') }}</div>
                  <v-select
                    v-model="schData.wkplId"
                    item-text="cd_nm"
                    item-value="cd"
                    :items="F_codes.F_00000001"
                    :label="$t('LB00000021')"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                  ></v-select>
                </div>
                <div class="set_col mb-3">
                  <div class="label_con mb-1">{{ $t('LB00000050') }}</div>
                  <v-select
                    v-model="schData.langCd"
                    item-text="cd_nm"
                    item-value="cd"
                    :items="codes.CO00000002"
                    :label="$t('LB00000050')"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                  ></v-select>
                </div>
                <div class="set_col mb-3">
                  <div class="label_con mb-1">{{ $t('LB00000350') }}</div>
                  <v-combobox
                    v-model="tzonCd"
                    :items="codes.CO00000004p"
                    :label="$t('LB00000350')"
                    item-text="label"
                    item-value="value"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                    @input="tzInput"
                  ></v-combobox>
                </div>
                <div class="set_col mb-3">
                  <div class="label_con mb-1">Workspace</div>
                  <v-select
                    v-model="schData.workspaceCd"
                    item-text="cd_nm"
                    item-value="cd"
                    :items="codes.CO00000016"
                    :label="$t('LB00001356')"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                  ></v-select>
                </div>
              </v-col>
            </v-row>
            <v-card-actions style="padding: 0 !important">
              <v-btn depressed color="primary" height="35" style="width: 100%" @click="closePop"> {{ $t('LB00000045') }} </v-btn>
            </v-card-actions>
          </div>
        </v-menu> -->
      </div>
    </v-app-bar>
    <div v-if="siteMap" class="top_sub_menu">
      <ul>
        <li v-for="menu in mainMenus" :key="menu.menu_no">
          <h3>{{ menu.menu_nm }}</h3>
          <ul>
            <li v-for="item in getSubMenus(menu.menu_no)" :key="item.menu_no">
              <a href="#" @click.stop="
                siteMap = !siteMap
              id = menu.menu_no
              subTitle = menu.menu_nm
              drawer = true
              goMenu(item.menu_url, item.menu_nm)
                ">
                {{ item.menu_nm }}
              </a>
            </li>
          </ul>
        </li>
      </ul>
      <!-- 닫기 -->
      <button class="btn_sitemap_cls" @click.stop="siteMap = !siteMap">{{ $t('LB00000109') }}</button>
    </div>
    <v-navigation-drawer v-model="drawer" app clipped hide-overlay class="leftmene_wrap">
      <div class="design_lefttit">
        <h1>{{ subTitle }}</h1>
      </div>
      <!-- left menu start -->
      <v-list dense class="pt-0">
        <v-list-group v-for="item in subMenus" :key="item.menu_no" no-action
          @click.stop="goMenu(item.menu_url, item.menu_nm)">
          <template v-slot:activator>
            <v-list-item-content class="leftmenu_style_btn2">
              <v-list-item-title>{{ item.menu_nm }}</v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item v-for="subItem in getSubMenus(item.menu_no)" :key="subItem.menu_no" link class="leftmenu_style"
            retain-focus-on-click @click.stop="goMenu(subItem.menu_url, subItem.menu_nm)">
            <v-list-item-content class="leftmenu_style_btn">
              <v-list-item-title>{{ item.menu_nm }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>
    <v-btn v-if="drawer" class="lnb_hide" icon tile @click.stop="drawer = false"><img src="~assets/images/btn_lnb.svg"
        alt="" /></v-btn>
    <v-btn v-if="!drawer" class="lnb_show" icon tile @click.stop="drawer = true"><img src="~assets/images/btn_lnb.svg"
        alt="" /></v-btn>
    <router-tab v-if="loading" :class="{ mainFrame: !drawer }" lang="en" style="min-width: 1024px; height: 100vh" />
    <!-- <div class="footer_wrap">
      <div>
        <span>Family Site</span>
      </div>
    </div> -->
    <popup ref="pop" @close="refresh" />
    <popup2 ref="pop2" @close="refresh" />
  </v-app>
</template>

<script>
import popup from '../pages/common/authcheck'
import popup2 from '../pages/common/reconfigure'

export default {
  components: {
    popup,
    popup2,
  },
  data() {
    return {
      id: null,
      idSpan: false,
      drawer: false,
      config: false,
      subTitle: null,
      siteMap: false,
      saveChk: false,
      select: [],
      tzonCd: '',
      schData: {
        wkplId: '',
        langCd: '',
        tzonCd: '',
        workspaceCd: '',
      },
      tempData: {
        wkplId: '',
        langCd: '',
        tzonCd: '',
        workspaceCd: '',
      },
      items: ['item1', 'item2', 'item3', 'item4'],
      user: {},
      loading: false,
      codeid: ['CO00000002', 'CO00000004', 'CO00000006', 'CO00000009', 'CO00000016'],
      codes: {
        CO00000002: [{ cd: '', cd_nm: '' }],
        // CO00000004: [{ cd: '', cd_nm: '' }],
        CO00000004p: [],
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
        CO00000016: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    mainMenus() {
      return this.$store.getters['menu/getMainMenus']
    },
    subMenus() {
      return this.$store.getters['menu/getSubMenus'](this.id)
    },
    isLogin() {
      return this.$store.getters['auth/isLogin']
    },
    getUser() {
      return this.$store.getters['auth/getUser']
    },
    getUsername() {
      return this.$store.getters['auth/getUsername']
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  watch: {
    config(newVal) {
      if (!newVal && !this.saveChk) {
        // 유효성 검사
        this.validation()
      }
    },
  },
  created() {
    // eslint-disable-next-line nuxt/no-globals-in-created
    const urlCheck = window.location.host
    const chk = urlCheck.substring(0, 8)
    if (chk === 'dehs') {
      this.$axios
        .$get(`/api/v1/common/whoami`, { params: { id: '' } })
        .then((res) => {
          console.log('user : ', res) // eslint-disable-line no-console
          const data = res
          if (res.result === 'reauthenticate') {
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.$refs.pop.open(data.telNo)
            // })
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.$refs.pop.open(data.telNo)
            })
            // this.$router.push('/authInfo2')
          } else if (res.result === 'reconfigure') {
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.$refs.pop2.open(data)
            // })
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.$refs.pop2.open(data)
            })
          } else {
            this.$store.commit('auth/add', res)

            this.$axios.$get(`/api/v1/common/menus`).then((res) => {
              console.log('menu : ', res) // eslint-disable-line no-console
              this.$store.commit('menu/add', res)
            })
            // 사업장 조회
            this.$axios
              .$get(`/api/v1/comm/wkpl`, {})
              .then((res) => {
                this.FF_00000001 = res
                const cmpy = this.getUser.cmpn_cd
                this.codChgReturn(cmpy)
              })
              .catch((err) => {
                console.log(err) // eslint-disable-line no-console
              })
            this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
              this.codes = res
              this.schData.wkplId = this.getUser.wkpl_id
              this.schData.langCd = this.getUser.lang_cd
              this.schData.tzonCd = this.getUser.time_zone_cd
              this.schData.workspaceCd = this.getUser.workspace_cd
              this.tempData.wkplId = this.getUser.wkpl_id
              this.tempData.langCd = this.getUser.lang_cd
              this.tempData.tzonCd = this.getUser.time_zone_cd
              this.tempData.workspaceCd = this.getUser.workspace_cd
              this.tempTzonCd = { value: this.getUser.time_zone_cd, label: this.getUser.time_zone }
              this.tzonCd = { value: this.getUser.time_zone_cd, label: this.getUser.time_zone }
              const temp = []
              res.CO00000004.forEach((e) => {
                temp.push({ value: e.cd, label: e.cd_nm })
              })
              this.codes.CO00000004p = temp
            })

            // 다국어 조회
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.loading = true
            // })

            // 다국어 조회
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.loading = true
            })
          }
        })
        .catch((e) => {
          console.log('Authenticate Fail') // eslint-disable-line no-console
          console.log(e) // eslint-disable-line no-console
        })
    } else {
      const result = prompt('LOGIN ID(default : AC926031) | 2021-02-15')
      this.$axios
        .$get(`/api/v1/common/whoami`, { params: { id: result } })
        .then((res) => {
          console.log('user : ', res) // eslint-disable-line no-console
          const data = res
          if (res.result === 'reauthenticate') {
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.$refs.pop.open(data.telNo)
            // })
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.$refs.pop.open(data.telNo)
            })
            // this.$router.push('/authInfo2')
          } else if (res.result === 'reconfigure') {
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.$refs.pop2.open(data)
            // })
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.$refs.pop2.open(data)
            })
          } else {
            this.$store.commit('auth/add', res)

            this.$axios.$get(`/api/v1/common/menus`).then((res) => {
              console.log('menu : ', res) // eslint-disable-line no-console
              this.$store.commit('menu/add', res)
            })
            // 사업장 조회
            this.$axios
              .$get(`/api/v1/comm/wkpl`, {})
              .then((res) => {
                this.FF_00000001 = res
                const cmpy = this.getUser.cmpn_cd
                this.codChgReturn(cmpy)
              })
              .catch((err) => {
                console.log(err) // eslint-disable-line no-console
              })
            this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
              this.codes = res
              this.schData.wkplId = this.getUser.wkpl_id
              this.schData.langCd = this.getUser.lang_cd
              this.schData.tzonCd = this.getUser.time_zone_cd
              this.schData.workspaceCd = this.getUser.workspace_cd
              this.tempData.wkplId = this.getUser.wkpl_id
              this.tempData.langCd = this.getUser.lang_cd
              this.tempData.tzonCd = this.getUser.time_zone_cd
              this.tempData.workspaceCd = this.getUser.workspace_cd
              this.tempTzonCd = { value: this.getUser.time_zone_cd, label: this.getUser.time_zone }
              this.tzonCd = { value: this.getUser.time_zone_cd, label: this.getUser.time_zone }
              const temp = []
              res.CO00000004.forEach((e) => {
                temp.push({ value: e.cd, label: e.cd_nm })
              })
              this.codes.CO00000004p = temp
            })

            // 다국어 조회
            // this.$axios.$get(`/api/v1/comm/mlang`).then((res) => {
            //   this.$store.commit('mlang/add', res)
            //   this.loading = true
            // })

            // 다국어 조회
            this.$axios.$get(`/api/v1/comm/mlang/i18n`).then((res) => {
              this.$i18n.locale = 'ko'
              this.$i18n.setLocaleMessage('ko', res)
              this.loading = true
            })
          }
        })
        .catch((e) => {
          console.log('Authenticate Fail') // eslint-disable-line no-console
          console.log(e) // eslint-disable-line no-console
        })
    }
  },
  methods: {
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
    },
    refresh(result) {
      if (result === 'Success') {
        window.location.reload()
      } else {
        this.$router.push('/authInfo2')
      }
    },
    validation() {
      if (JSON.stringify(this.tempData) !== JSON.stringify(this.schData) || JSON.stringify(this.tzonCd) !== JSON.stringify(this.tempTzonCd)) {
        this.$toast.show(this.$t('MS00000148'), { className: 'toast_error' }) // 설정이 저장되지 않았습니다.
        this.schData.wkplId = this.tempData.wkplId
        this.schData.langCd = this.tempData.langCd
        this.schData.tzonCd = this.tempData.tzonCd
        this.schData.workspaceCd = this.tempData.workspaceCd
        this.tzonCd = this.tempTzonCd
        this.tzInput()
      }
    },
    tzInput() {
      if (this.isEmpty(this.tzonCd)) {
        this.schData.tzonCd = ''
      } else {
        this.schData.tzonCd = this.tzonCd.value
      }
    },
    goMenu(url, menuName) {
      const current = {}
      current.subTitle = this.subTitle
      current.menuName = menuName
      this.$store.commit('menu/setCurrent', current)

      this.$router.push(url)
    },
    goHome() {
      this.drawer = false
      // this.$router.push('/')
      this.$tabs.reset('/')
    },
    getSubMenus(id) {
      return this.$store.getters['menu/getSubMenus'](id)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async closePop() {
      const res = await this.$confirm(this.$t('MS00000149')) // 저장하시면 해당 환경 설정으로 화면이 갱신됩니다. 저장하시겠습니까?
      if (!res) {
      } else {
        this.saveChk = true
        this.$axios.put(`/api/v1/comm/config`, this.schData).then((res) => {
          if (res.data !== 'Fail' && res.data !== 'READ' && res.data !== 'TIMEFAIL') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.config = false
            window.location.reload()
          } else if (res.data === 'TIMEFAIL') {
            this.$toast.show(this.$t('MS00000150'), { className: 'toast_error' }) // 잘못된 타임존 설정입니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
  },
}
</script>

<style scss>
@media (min-width: 960px) {
  .container {
    max-width: 100% !important;
  }
}

@media (min-width: 1264px) {
  .container {
    max-width: 100% !important;
  }
}

@media (min-width: 1904px) {
  .container {
    max-width: 100% !important;
  }
}

* {
  transition: none !important;
}

html {
  overflow: hidden !important;
  transition: none !important;
  font-size: 13px;
}

#app {
  font-family: NotoSans-Regular !important;
  color: #444;
  min-width: 1200px;
}

ul {
  list-style: none;
}

.v-dialog-modal .v-dialog-body {
  padding: 0 !important;
}

.is-active {
  background: red !important;
}

.v-btn:before {
  display: none;
}

.font_gray {
  color: #222 !important;
}

.topmenu_style {
  background: #fff !important;
  border-bottom: 1px solid #efefef !important;
}

/* kkd : 추가 => design_lefttit를 design_centertit로 변경 */
.design_centertit h1 {
  font-size: 22px;
  font-weight: bold;
  padding: 35px 30px 25px 30px;
  color: #222;
  text-align: center;
}

.router-tab__item {}

.router-tab__item.is-active {
  background: #fff !important;
  color: #222 !important;
  /* border-radius: 3px 3px 0 0; */
  font-weight: bold;
}

.top_sub_menu {
  background-color: #fafafa;
  border-bottom: 1px solid #efefef;
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 60px;
  z-index: 10000;
  padding: 0 100px;
  min-width: 1264px;
  display: block;
  box-shadow: 0 5px 5px #ddd;
  overflow: auto;
}

.btn_sitemap_cls {
  background: url('../assets/images/btn_sitemap_cls.png') no-repeat center;
  width: 34px;
  height: 34px;
  text-indent: -9999px;
  position: absolute;
  right: 30px;
  top: 30px;
}

.btn_sitemap_cls:hover {
  background: url('../assets/images/btn_sitemap_clsO.png') no-repeat center;
}

.btn_sitemap {
  background: url('../assets/images/btn_sitemap.png') no-repeat center;
  width: 16px;
  height: 22px;
  line-height: 22px;
  text-indent: -9999px;
}

.btn_home {
  background: url('../assets/images/logo.png') no-repeat center;
  width: 197px;
  height: 47px;
  margin-left: 7px;
  line-height: 47px;
  text-indent: -9999px;
}

.top_sub_menu>ul {
  list-style: none;
  padding: 25px 0 15px !important;
}

.top_sub_menu>ul>li {
  float: left;
  width: 25%;
  padding: 15px 0;
}

.top_sub_menu>ul>li:nth-child(5) {
  clear: both;
}

.top_sub_menu>ul>li h3 {
  position: relative;
  font-size: 18px;
  border-bottom: 1px solid #777;
  width: 70%;
  padding: 7px 0 10px 0;
  margin: 0 auto;
}

.top_sub_menu>ul>li h3:after {
  content: '';
  width: 20px;
  height: 3px;
  position: absolute;
  left: 0;
  top: 0;
  background: #444;
}

.top_sub_menu>ul>li>ul {
  list-style: none;
  padding-left: 0 !important;
  margin: 0 auto;
  width: 70%;
}

.top_sub_menu>ul>li>ul>li {
  border-bottom: 1px solid #f1f1f1;
}

.top_sub_menu>ul>li>ul>li>a {
  padding: 10px 5px;
  font-size: 14px;
  display: block;
  background: transparent url('../assets/images/btn_lnb_bul.png') no-repeat 97% center;
  text-decoration: none;
}

.top_sub_menu>ul>li>ul>li>a:hover {
  text-decoration: underline;
  color: #e44025;
}

.container_set {
  z-index: 10;
}

.set_layout {
  background: #fff;
  padding: 10px !important;
  border-radius: 4px;
}</style>
