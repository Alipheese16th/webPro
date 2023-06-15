<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 위험성평가 등록-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('위험성평가 등록') }}</span>
    </div>
    <div class="work_title">
      <!-- 위험성평가 등록 -->
      <h2>{{ $t('위험성평가 등록') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보작성 -->
        <h3>{{ $t('기본정보작성') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cmpy_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--관리감독자-->
                <div class="label_tit">{{ $t('관리감독자') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.dgsn_apal_supe_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--실시일자-->
                <div class="label_tit">{{ $t('실시일자') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.dgsn_apal_bgn_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--위험성평가-->
                <div class="label_tit">{{ $t('위험성평가') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.dgsn_apal_type_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--수시평가종류-->
                <div class="label_tit">{{ $t('수시평가종류') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.dgsn_spot_apal_type_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--대분류-->
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('대분류') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lrcl_dngp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--공정명-->
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('공정명') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mdcl_dngp_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--다음단계-->
          <v-btn depressed width="120" color="success" height="40" @click="next">{{ $t('다음단계') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  meta: {
    title: '위험성평가 등록/조회',
    key(route) {
      return `/riskeval/${route.params.catalog}`
    },
  },
  components: {},
  data() {
    return {
      codeid: ['CO00000009', 'SH00000078', 'SH00000079'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        SH00000078: [{ cd: '', cd_nm: '' }],
        SH00000079: [{ cd: '', cd_nm: '' }],
        SH00000079_copy: [{ cd: '', cd_nm: '' }],
      },
      menu1: false,
      schData: {},
      frmData: {},
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('위험성평가 등록/조회') // 위험성평가 등록/조회
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.user = this.$store.getters['auth/getUser']
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        const cmpy = this.user.cmpn_cd
        this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
          return n.up_cd === cmpy
        })
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    // 이전 검색 결과 저장
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    }
    if (this.$route.params.riskevalbasiclup) {
      this.frmData.dgsn_apal_id = this.$route.params.riskevalbasiclup
      this.select()
    } else {
      this.frmData.cmpy_cd = this.user.cmpn_cd
      this.frmData.wkpl_id = this.user.wkpl_id
      this.frmData.dgsn_apal_bgn_dt = this.getTzDate(this.user.time_zone, '-')
    }
  },
  methods: {
    async select() {
      this.modify = true
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
    },
    next() {
      this.$router.push({
        name: `riskeval-riskevaltgtdtlwklup-riskevaltgtdtlwklup`,
        params: {
          schData: this.schData,
          riskevaltgtdtlwklup: this.frmData.dgsn_apal_id,
        },
      })
    },
    goList() {
      this.$router.push({
        name: `riskeval-riskevallist`,
        params: {
          schData: this.schData,
        },
      })
    },
  },
}
</script>
<style></style>
