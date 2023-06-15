<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 시정지시서 관리 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001087') }}</span>
    </div>
    <div class="work_title">
      <!-- 시정지시서 관리 -->
      <h2>{{ $t('LB00001087') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 구분-->
                <div class="label_tit">{{ $t('LB00000669') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crin_cl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 담당자-->
                <div class="label_tit">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crin_chk_supe_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검일시-->
                <div class="label_tit">{{ $t('LB00000648') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ frmData.crin_chk_dt }} {{ frmData.crin_chk_hm }}</div>
              </v-col>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crin_chk_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crin_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.crin_chk_tgt_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="crin_chk_note_txt"
                  v-model="frmData.crin_chk_note_txt"
                  outlined
                  single-line
                  disabled
                  :hide-details="true"
                  height="60"
                  class="mt-2"
                  dense
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <h3>{{ $t('LB00000995') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--개선 희망일-->
                <div class="label_tit">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                <!--개선 희망일-->
                <div class="label_con">{{ frmData.actn_hope_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_actn_schd_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000672') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_risk_tp_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000672') }}2</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_risk_tp_cd_2_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--시정지시 내용-->
                <div class="label_tit">{{ $t('LB00000799') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="crin_txt" v-model="frmData.crin_txt" outlined single-line :hide-details="true" disabled height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000902') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop1" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <h3>{{ $t('LB00000683') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--조치완료일-->
                <div class="label_tit">{{ $t('LB00000684') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ actnData.acco_dt }} {{ actnData.acco_hm }}</div>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000673') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ actnData.crin_actn_rspn_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--조치 내용-->
                <div class="label_tit">{{ $t('LB00000797') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="actn_txt" v-model="actnData.actn_txt" outlined single-line :hide-details="true" disabled height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000902') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop2" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import picpreview from '../../common/picpreview'

let dateVal = new Date()

export default {
  meta: {
    title: '시정지시서 관리',
    key(route) {
      return `/sftchk/crrtdrtmgntlist/${route.params.catalog}`
    },
  },
  components: {
    picpreview,
  },
  data() {
    return {
      quillChk: false,
      apprModify: true,
      tmpSchData: {},
      schData: {},
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      menu5: false,
      wkplId: '',
      crinId: '',
      frmData: {
        wkpl_id: '',
        crin_id: '',
        crin_risk_tp_cd: '',
        crin_risk_tp_cd_nm: '',
        crin_risk_tp_cd_2: '',
        crin_risk_tp_cd_2_nm: '',
        wkpl_nm: '',
        crin_cl_cd: '',
        crin_cl_nm: '',
        crin_chk_dt: '',
        crin_chk_hm: '',
        crin_chk_supe_emp_id: '',
        crin_chk_supe_emp_nm: '',
        crin_chk_wkpl_loc_id: '',
        crin_chk_wkpl_loc_nm: '',
        crin_chk_dtl_loc_nm: '',
        actn_hope_dt: '',
        crin_actn_schd_emp_id: '',
        crin_actn_schd_emp_nm: '',
        crin_txt: '',
        crin_img_atfl_no: '',
        lnk_chk_id: '',
        crin_chk_tgt_nm: '',
      },
      // 조치정보
      actnData: {
        crin_id: '',
        wkpl_id: '',
        acco_dt: '',
        acco_hm: '',
        crin_actn_rspn_emp_id: '',
        crin_actn_rspn_emp_nm: '',
        actn_txt: '',
        crin_img_atfl_no: '',
        lnk_chk_id: '',
        crin_prst_cd: '',
        crin_aprv_no: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001087')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']

    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        crin_cl_cd: '',
        crin_chk_wkpl_loc_id: '',
        crin_chk_wkpl_loc_nm: '',
        crin_chk_supe_emp_id: '',
        crin_chk_supe_emp_nm: '',
        crin_chk_gb: '',
        crin_chk_emp_id: '',
        crin_chk_emp_nm: '',
        crin_prst_cd: '',
        crin_chk_st_dt: '',
        crin_chk_ed_dt: '',
        cmpy_cd: '',
      }
    }
    dateVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.actn_hope_dt = this.get_date_str_gubun(dateVal, '-')
    this.frmData.crin_chk_dt = this.get_date_str_gubun(dateVal, '-')
    this.actnData.acco_dt = this.get_date_str_gubun(dateVal, '-')
  },
  mounted() {
    this.$refs.picPop1.setEdit(false)
    this.$refs.picPop2.setEdit(false)

    if (this.$route.params.crrtdrtmgntdtllup !== null && this.$route.params.crrtdrtmgntdtllup !== undefined) {
      this.schData.crin_id = this.$route.params.crrtdrtmgntdtllup
      this.crinId = this.$route.params.crrtdrtmgntdtllup
      this.select()
      this.wkplModify = false
    } else {
      this.wkplId = ''
      this.crinId = ''
      this.schData.wkpl_id = ''
      this.schData.crin_id = ''
      this.wkplModify = true
    }
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/sftchk/crin-dtl`, { params: this.schData })

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.schData.wkpl_id = this.frmData.wkpl_id
        this.wkplId = this.frmData.wkpl_id
        // 이미지 로딩
        if (!this.isEmpty(this.frmData.crin_img_atfl_no)) {
          this.$refs.picPop1.load(this.frmData.crin_img_atfl_no)
        }
      } else {
        this.frmData = []
      }

      if (data.actn.length > 0) {
        this.actnData = data.actn[0]

        // 이미지 로딩
        if (!this.isEmpty(this.actnData.crin_img_atfl_no)) {
          this.$refs.picPop2.load(this.actnData.crin_img_atfl_no)
        }
      } else {
        this.actnData = []
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    goList() {
      this.$router.push({
        name: `sftchk-crrtdrtmgntlist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            crin_cl_cd: this.tmpSchData.crin_cl_cd,
            crin_chk_wkpl_loc_id: this.tmpSchData.crin_chk_wkpl_loc_id,
            crin_chk_wkpl_loc_nm: this.tmpSchData.crin_chk_wkpl_loc_nm,
            crin_chk_supe_emp_id: this.tmpSchData.crin_chk_supe_emp_id,
            crin_chk_supe_emp_nm: this.tmpSchData.crin_chk_supe_emp_nm,
            crin_chk_gb: this.tmpSchData.crin_chk_gb,
            crin_chk_emp_id: this.tmpSchData.crin_chk_emp_id,
            crin_chk_emp_nm: this.tmpSchData.crin_chk_emp_nm,
            crin_prst_cd: this.tmpSchData.crin_prst_cd,
            crin_chk_st_dt: this.tmpSchData.crin_chk_st_dt,
            crin_chk_ed_dt: this.tmpSchData.crin_chk_ed_dt,
            cmpy_cd: this.tmpSchData.cmpy_cd,
          },
        },
      })
    },
  },
}
</script>
<style></style>
