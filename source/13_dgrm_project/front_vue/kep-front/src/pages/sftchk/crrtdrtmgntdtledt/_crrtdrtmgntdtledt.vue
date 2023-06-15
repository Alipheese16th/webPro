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
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  disabled
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검 구분-->
                <div class="label_tit essn">{{ $t('LB00000669') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="crin_cl_cd"
                  v-model="frmData.crin_cl_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000053p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="true"
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검 담당자-->
                <div class="label_tit essn">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="crin_chk_supe_emp_id"
                  v-model="frmData.crin_chk_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  :disabled="!apprModify"
                  readonly
                  @click="popOpen('mgnt')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검일시-->
                <div class="label_tit essn">{{ $t('LB00000648') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="crin_chk_dt"
                      v-model="frmData.crin_chk_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      :disabled="!apprModify"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.crin_chk_dt" :disabled="!apprModify" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="crin_chk_hm_h"
                  v-model="frmData.crin_chk_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="con_bgn_hm_s"
                  v-model="frmData.crin_chk_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit essn">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="crin_chk_wkpl_loc_id"
                  v-model="frmData.crin_chk_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  :disabled="!apprModify"
                  readonly
                  @click="popLocOpen()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit essn">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="crin_chk_dtl_loc_nm"
                  v-model="frmData.crin_chk_dtl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  :disabled="!apprModify"
                  clearable
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit essn">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="crin_chk_tgt_nm"
                  v-model="frmData.crin_chk_tgt_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  :disabled="!apprModify"
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
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
                  :disabled="!apprModify"
                  :hide-details="true"
                  height="60"
                  class="mt-2"
                  dense
                  maxlength="2000"
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
        <!-- 시정지시서 발행 -->
        <h3>{{ $t('LB00000995') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--개선 희망일-->
                <div class="label_tit essn">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="actn_hope_dt"
                      v-model="frmData.actn_hope_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      :disabled="!apprModify"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.actn_hope_dt" no-title :disabled="!apprModify" @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit essn">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="crin_actn_schd_emp_id"
                  v-model="frmData.crin_actn_schd_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  :disabled="!apprModify"
                  readonly
                  @click="popOpen('schd')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험 유형-->
                <div class="label_tit essn">{{ $t('LB00000672') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="crin_risk_tp_cd"
                  v-model="frmData.crin_risk_tp_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000063p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="!apprModify"
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위험유형2-->
                <div class="label_tit essn">{{ $t('LB00000672') }}2</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="crin_risk_tp_cd_2"
                  v-model="frmData.crin_risk_tp_cd_2"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000068p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="!apprModify"
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--시정지시 내용-->
                <div class="label_tit essn">{{ $t('LB00000799') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="crin_txt"
                  v-model="frmData.crin_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!apprModify"
                  height="60"
                  class="mt-2"
                  dense
                  maxlength="1000"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 점검사진 -->
                <div class="label_tit essn">{{ $t('LB00000902') }}</div>
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
        <!-- 조치정보 -->
        <h3>{{ $t('LB00000683') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--조치완료일-->
                <div class="label_tit essn">{{ $t('LB00000684') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu3" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="acco_dt"
                      v-model="actnData.acco_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      :disabled="!apprModify"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="actnData.acco_dt" :disabled="!apprModify" no-title @input="menu3 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="acco_hm_h"
                  v-model="actnData.acco_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="acco_hm_s"
                  v-model="actnData.acco_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="codes.SH00000075"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit essn">{{ $t('LB00000673') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="crin_actn_rspn_emp_id"
                  v-model="actnData.crin_actn_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  :disabled="!apprModify"
                  readonly
                  @click="popOpen('actn')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--조치 내용-->
                <div class="label_tit essn">{{ $t('LB00000797') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="actn_txt"
                  v-model="actnData.actn_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!apprModify"
                  height="60"
                  class="mt-2"
                  dense
                  maxlength="1000"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit essn">{{ $t('LB00000902') }}</div>
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
          <!--조치완료-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!sendYn" @click="appr()">{{ $t('LB00000686') }}</v-btn>
          <!--시정조치완료-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!apprModify" @click="send()">{{ $t('LB00001103') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!apprModify" @click="save()">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="crrtdrtmgntdtledtuserslct1" mkey2="crrtdrtmgntdtledtuserslct2" @close="popclose" />
    <locslct ref="locpop" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey1="crrtdrtmgntdtledt1" mkey2="crrtdrtmgntdtledt2" @close="popLocClose" />
  </div>
</template>

<script>
import popup from '../../common/userslct'
import locslct from '../../common/rgnslct'
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
    popup,
    locslct,
    picpreview,
  },
  data() {
    return {
      quillChk: false,
      wkplModify: false,
      apprModify: true,
      sendYn: false,
      codeid: ['SH00000053', 'ST00000002', 'SH00000063', 'SH00000068', 'SH00000074', 'SH00000075'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000002p: [{ cd: '', cd_nm: '' }],
        SH00000053: [{ cd: '', cd_nm: '' }],
        SH00000053p: [{ cd: '', cd_nm: '' }],
        SH00000063: [{ cd: '', cd_nm: '' }],
        SH00000063p: [{ cd: '', cd_nm: '' }],
        SH00000068: [{ cd: '', cd_nm: '' }],
        SH00000068p: [{ cd: '', cd_nm: '' }],
        SH00000074: [{ cd: '', cd_nm: '' }],
        SH00000075: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
      },
      mm5: [
        { cd: '00', cd_nm: '00' },
        { cd: '05', cd_nm: '05' },
        { cd: '10', cd_nm: '10' },
        { cd: '15', cd_nm: '15' },
        { cd: '20', cd_nm: '20' },
        { cd: '25', cd_nm: '25' },
        { cd: '30', cd_nm: '30' },
        { cd: '35', cd_nm: '35' },
        { cd: '40', cd_nm: '40' },
        { cd: '45', cd_nm: '45' },
        { cd: '50', cd_nm: '50' },
        { cd: '55', cd_nm: '55' },
      ],
      tmpSchData: {},
      schData: {},
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      menu5: false,
      userPopGb: '',
      wkplId: '',
      crinId: '',
      frmTempData: {},
      actnTempData: {},
      frmData: {
        wkpl_id: '',
        crin_id: '',
        wkpl_nm: '',
        crin_cl_cd: '',
        crin_cl_nm: '',
        crin_risk_tp_cd: '',
        crin_risk_tp_cd_nm: '',
        crin_risk_tp_cd_2: '',
        crin_risk_tp_cd_2_nm: '',
        crin_chk_dt: '',
        crin_chk_hm: '',
        crin_chk_hm_h: '',
        crin_chk_hm_s: '',
        crin_chk_supe_emp_id: '',
        crin_chk_supe_emp_nm: '',
        crin_chk_wkpl_loc_id: '',
        crin_chk_wkpl_loc_nm: '',
        crin_chk_dtl_loc_nm: '',
        crin_chk_note_txt: '',
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
        acco_hm_h: '',
        acco_hm_s: '',
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

    if (this.$route.params.crrtdrtmgntdtledt !== null && this.$route.params.crrtdrtmgntdtledt !== undefined) {
      // eslint-disable-next-line prefer-promise-reject-errors
    } else {
      this.frmData.wkpl_id = this.user.wkpl_id
    }

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
    // 사업장 조회
    this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
      console.log(res) // eslint-disable-line no-console
      const tempWkpl = []
      res.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm, up_cd: e.up_cd })
      })
      this.FF_00000001 = tempWkpl
      this.F_codes.F_00000001 = tempWkpl
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT' })

      // 사업장 등록&수정하는 화면에서만 필터링 할 수 있게끔...
      const cmpy = this.user.cmpn_cd
      if (this.wkplModify === true) {
        this.codChg(cmpy)
      }
    })
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempWkpl = []
      this.codes.SH00000074 = res.SH00000074
      this.codes.SH00000075 = res.SH00000075
      res.ST00000002.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000002p = tempWkpl
      this.codes.ST00000002p.unshift({ value: '', label: 'SELECT' })

      const tempGb = []
      res.SH00000053.forEach((e) => {
        tempGb.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000053p = tempGb
      this.codes.SH00000053p.unshift({ value: '', label: 'SELECT' })

      const tempList = []
      res.SH00000063.forEach((e) => {
        tempList.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000063p = tempList
      this.codes.SH00000063p.unshift({ value: '', label: 'SELECT' })

      const tempList2 = []
      res.SH00000068.forEach((e) => {
        tempList2.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000068p = tempList2
      this.codes.SH00000068p.unshift({ value: '', label: 'SELECT' })

      this.$refs.picPop1.setEdit(true)
      this.$refs.picPop2.setEdit(true)

      if (this.$route.params.crrtdrtmgntdtledt !== null && this.$route.params.crrtdrtmgntdtledt !== undefined) {
        this.schData.crin_id = this.$route.params.crrtdrtmgntdtledt
        this.crinId = this.$route.params.crrtdrtmgntdtledt
        this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'CRIN', crin_id: this.crinId } }).then((res) => {
          // eslint-disable-next-line no-console
          if (res === 'Success') {
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
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
          }
        })
        this.wkplModify = false
      } else {
        const cmpy = this.user.cmpn_cd
        this.wkplId = ''
        this.crinId = ''
        this.schData.wkpl_id = ''
        this.schData.crin_id = ''
        this.wkplModify = true
        this.frmData.crin_cl_cd = 'CRN04' // 수시 점검 고정
        this.codChg(cmpy)
      }
    })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
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
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터

        // 이미지 로딩
        if (!this.isEmpty(this.frmData.crin_img_atfl_no)) {
          this.$refs.picPop1.load(this.frmData.crin_img_atfl_no)
        }
      } else {
        this.frmData = {
          wkpl_id: '',
          crin_id: '',
          wkpl_nm: '',
          crin_cl_cd: '',
          crin_cl_nm: '',
          crin_risk_tp_cd: '',
          crin_risk_tp_cd_nm: '',
          crin_risk_tp_cd_2: '',
          crin_risk_tp_cd_2_nm: '',
          crin_chk_dt: '',
          crin_chk_hm: '',
          crin_chk_hm_h: '',
          crin_chk_hm_s: '',
          crin_chk_supe_emp_id: '',
          crin_chk_supe_emp_nm: '',
          crin_chk_wkpl_loc_id: '',
          crin_chk_wkpl_loc_nm: '',
          crin_chk_dtl_loc_nm: '',
          crin_chk_note_txt: '',
          actn_hope_dt: '',
          crin_actn_schd_emp_id: '',
          crin_actn_schd_emp_nm: '',
          crin_txt: '',
          crin_img_atfl_no: '',
          lnk_chk_id: '',
          crin_chk_tgt_nm: '',
        }
        this.frmTempData = {
          wkpl_id: '',
          crin_id: '',
          wkpl_nm: '',
          crin_cl_cd: '',
          crin_cl_nm: '',
          crin_risk_tp_cd: '',
          crin_risk_tp_cd_nm: '',
          crin_risk_tp_cd_2: '',
          crin_risk_tp_cd_2_nm: '',
          crin_chk_dt: '',
          crin_chk_hm: '',
          crin_chk_hm_h: '',
          crin_chk_hm_s: '',
          crin_chk_supe_emp_id: '',
          crin_chk_supe_emp_nm: '',
          crin_chk_wkpl_loc_id: '',
          crin_chk_wkpl_loc_nm: '',
          crin_chk_dtl_loc_nm: '',
          crin_chk_note_txt: '',
          actn_hope_dt: '',
          crin_actn_schd_emp_id: '',
          crin_actn_schd_emp_nm: '',
          crin_txt: '',
          crin_img_atfl_no: '',
          lnk_chk_id: '',
          crin_chk_tgt_nm: '',
        }
      }

      if (data.actn.length > 0) {
        this.actnData = data.actn[0]
        this.actnTempData = JSON.parse(JSON.stringify(this.actnData)) // 수정여부 비교 데이터

        // 이미지 로딩
        if (!this.isEmpty(this.actnData.crin_img_atfl_no)) {
          this.$refs.picPop2.load(this.actnData.crin_img_atfl_no)
        }

        if (this.actnData.crin_prst_cd === '20') {
          // 상태가 조치중일 경우(발송 버튼을 눌렀을 경우)
          this.sendYn = true
        } else {
          this.sendYn = false
        }
      } else {
        this.actnData = {
          crin_id: '',
          wkpl_id: '',
          acco_dt: '',
          acco_hm: '',
          acco_hm_h: '',
          acco_hm_s: '',
          crin_actn_rspn_emp_id: '',
          crin_actn_rspn_emp_nm: '',
          actn_txt: '',
          crin_img_atfl_no: '',
          lnk_chk_id: '',
          crin_prst_cd: '',
          crin_aprv_no: '',
        }
        this.actnTempData = {
          crin_id: '',
          wkpl_id: '',
          acco_dt: '',
          acco_hm: '',
          acco_hm_h: '',
          acco_hm_s: '',
          crin_actn_rspn_emp_id: '',
          crin_actn_rspn_emp_nm: '',
          actn_txt: '',
          crin_img_atfl_no: '',
          lnk_chk_id: '',
          crin_prst_cd: '',
          crin_aprv_no: '',
        }
      }
    },
    chkFile() {
      this.frmData.ckFlag = true
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
    popOpen(val) {
      this.userPopGb = val
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          if (this.userPopGb === 'mgnt') {
            this.$set(this.frmData, 'crin_chk_supe_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'crin_chk_supe_emp_id', i.emp_no)
          } else if (this.userPopGb === 'schd') {
            this.$set(this.frmData, 'crin_actn_schd_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'crin_actn_schd_emp_id', i.emp_no)
          } else if (this.userPopGb === 'actn') {
            this.$set(this.actnData, 'crin_actn_rspn_emp_nm', i.emp_nm)
            this.$set(this.actnData, 'crin_actn_rspn_emp_id', i.emp_no)
          }
        })
      }
    },
    saveValidate(valiGb) {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000553'), { className: 'toast_error' }) // 사업장명을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_cl_cd)) {
        this.$toast.show(this.$t('MS00000398'), { className: 'toast_error' }) // 점검구분을 입력해주세요.
        this.$refs.crin_cl_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_chk_supe_emp_id)) {
        this.$toast.show(this.$t('MS00000269'), { className: 'toast_error' }) // 점검담당자를 입력해주세요.
        this.$refs.crin_chk_supe_emp_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_chk_dt)) {
        this.$toast.show(this.$t('MS00000461'), { className: 'toast_error' }) // 점검일을 입력해주세요.
        this.$refs.crin_chk_dt.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_chk_hm_h) || this.isEmpty(this.frmData.crin_chk_hm_s)) {
        this.$toast.show(this.$t('MS00000271'), { className: 'toast_error' }) // 점검 일시를 입력해주세요.
        this.$refs.crin_chk_hm_h.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_chk_wkpl_loc_id)) {
        this.$toast.show(this.$t('MS00000403'), { className: 'toast_error' }) // 위치를 입력해주세요.
        this.$refs.crin_chk_wkpl_loc_id.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.crin_chk_dtl_loc_nm) && this.frmData.crin_chk_dtl_loc_nm.length > 200) {
        this.$toast.show(this.$t('MS00000428'), { className: 'toast_error' }) // 상세위치는 200자 이하로 입력해주세요.
        this.$refs.crin_chk_dtl_loc_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.crin_chk_tgt_nm)) {
        this.$toast.show(this.$t('MS00000275'), { className: 'toast_error' }) // 점검명을 입력해주세요.
        this.$refs.crin_chk_tgt_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.crin_chk_tgt_nm) && this.frmData.crin_chk_tgt_nm.length > 100) {
        this.$toast.show(this.$t('MS00000555'), { className: 'toast_error' }) // 점검명은 100자 이하로 입력해주세요.
        this.$refs.crin_chk_tgt_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.crin_chk_note_txt) && this.frmData.crin_chk_note_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000134'), { className: 'toast_error' }) // 비고는 1000자 이하로 입력해주세요.
        this.$refs.crin_chk_note_txt.focus()
        return false
      }
      if (valiGb === 'appr') {
        if (this.isEmpty(this.frmData.actn_hope_dt)) {
          this.$toast.show(this.$t('MS00000261'), { className: 'toast_error' }) // 개선희망일을 입력해주세요.
          this.$refs.actn_hope_dt.focus()
          return false
        }
        if (this.isEmpty(this.frmData.crin_risk_tp_cd)) {
          this.$toast.show(this.$t('MS00000262'), { className: 'toast_error' }) // 위험 유형을 선택해주세요.
          this.$refs.crin_risk_tp_cd.focus()
          return false
        }
        if (this.isEmpty(this.frmData.crin_risk_tp_cd_2)) {
          this.$toast.show(this.$t('MS00000263'), { className: 'toast_error' }) // 위험 유형2을 선택해주세요.
          this.$refs.crin_risk_tp_cd_2.focus()
          return false
        }
        if (this.isEmpty(this.frmData.crin_actn_schd_emp_id)) {
          this.$toast.show(this.$t('MS00000504'), { className: 'toast_error' }) // 조치담당자를 입력해주세요.
          this.$refs.crin_actn_schd_emp_id.focus()
          return false
        }
      }
      if (!this.isEmpty(this.frmData.crin_txt) && this.frmData.crin_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000556'), { className: 'toast_error' }) // 시정지시내용은 1000자 이하로 입력해주세요.
        this.$refs.crin_txt.focus()
        return false
      }

      if (valiGb === 'appr') {
        if (this.isEmpty(this.actnData.acco_dt)) {
          this.$toast.show(this.$t('MS00000556'), { className: 'toast_error' }) // 조치완료일을 입력해주세요.
          this.$refs.acco_dt.focus()
          return false
        }
        if (this.isEmpty(this.actnData.acco_hm_h) || this.isEmpty(this.actnData.acco_hm_s)) {
          this.$toast.show(this.$t('MS00000504'), { className: 'toast_error' }) // 조치 완료시간을 입력해주세요
          this.$refs.acco_hm_h.focus()
          return false
        }
      }
      if (!this.isEmpty(this.actnData.actn_txt) && this.actnData.actn_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000557'), { className: 'toast_error' }) // 조치내용은 1000자 이하로 입력해주세요.
        this.$refs.actn_txt.focus()
        return false
      }
      return true
    },
    async save() {
      // 사진 업로드 과정
      await this.$refs.picPop1.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.crin_img_atfl_no = data
        } else {
          this.frmData.crin_img_atfl_no = ''
        }
      })
      await this.$refs.picPop2.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.actnData.crin_img_atfl_no = data
        } else {
          this.actnData.crin_img_atfl_no = ''
        }
      })
      if (JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) && JSON.stringify(this.actnData) === JSON.stringify(this.actnTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      const infoRow = []
      const temp = {}
      let hopeDt = this.frmData.actn_hope_dt
      if (!this.isEmpty(this.frmData.actn_hope_dt)) {
        hopeDt = hopeDt.replace(/-/gi, '')
      }
      let chkDt = this.frmData.crin_chk_dt
      if (!this.isEmpty(this.frmData.crin_chk_dt)) {
        chkDt = chkDt.replace(/-/gi, '')
      }
      let chkHm = this.frmData.crin_chk_hm_h.concat(this.frmData.crin_chk_hm_s)
      if (!this.isEmpty(this.frmData.crin_chk_hm)) {
        chkHm = chkHm.replace(/:/gi, '')
      }

      temp.wkpl_id = this.frmData.wkpl_id
      temp.crin_id = this.frmData.crin_id
      temp.crin_cl_cd = this.frmData.crin_cl_cd
      temp.actn_hope_dt = hopeDt
      temp.crin_risk_tp_cd = this.frmData.crin_risk_tp_cd
      temp.crin_risk_tp_cd_2 = this.frmData.crin_risk_tp_cd_2
      temp.crin_actn_schd_emp_id = this.frmData.crin_actn_schd_emp_id
      temp.crin_txt = this.frmData.crin_txt
      temp.crin_img_atfl_no = this.frmData.crin_img_atfl_no
      temp.crin_chk_dt = chkDt
      temp.crin_chk_hm = chkHm
      temp.crin_chk_wkpl_loc_id = this.frmData.crin_chk_wkpl_loc_id
      temp.crin_chk_dtl_loc_nm = this.frmData.crin_chk_dtl_loc_nm
      temp.crin_chk_supe_emp_id = this.frmData.crin_chk_supe_emp_id
      temp.crin_chk_note_txt = this.frmData.crin_chk_note_txt
      temp.crin_chk_tgt_nm = this.frmData.crin_chk_tgt_nm
      temp.lnk_chk_id = this.frmData.lnk_chk_id
      temp.username = this.user.emp_no
      temp.send_yn = 'N'
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      const actnRow = []
      const tempActn = {}
      let actnDt = this.actnData.acco_dt
      if (!this.isEmpty(this.actnData.acco_dt)) {
        actnDt = actnDt.replace(/-/gi, '')
      }
      let actnHm = this.actnData.acco_hm_h.concat(this.actnData.acco_hm_s)
      if (!this.isEmpty(this.actnData.acco_hm)) {
        actnHm = actnHm.replace(/:/gi, '')
      }
      let stCd = ''
      if (this.isEmpty(this.frmData.crin_id)) {
        stCd = '10'
      } else {
        stCd = '01' // 등록중
      }

      tempActn.wkpl_id = this.frmData.wkpl_id
      tempActn.crin_id = this.frmData.crin_id
      tempActn.crin_cl_cd = this.frmData.crin_cl_cd
      tempActn.crin_actn_rspn_emp_id = this.actnData.crin_actn_rspn_emp_id
      tempActn.actn_txt = this.actnData.actn_txt
      tempActn.crin_img_atfl_no = this.actnData.crin_img_atfl_no
      tempActn.acco_dt = actnDt
      tempActn.acco_hm = actnHm
      tempActn.crin_prst_cd = stCd
      tempActn.username = this.user.emp_no
      actnRow.push(tempActn)
      this.frmData.actnRow = actnRow

      await this.$axios.put('/api/v1/she/sftchk/crin-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.crin_id = res.data.key1
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async send() {
      // 사진 업로드 과정
      await this.$refs.picPop1.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.crin_img_atfl_no = data
        } else {
          this.frmData.crin_img_atfl_no = ''
        }
      })
      await this.$refs.picPop2.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.actnData.crin_img_atfl_no = data
        } else {
          this.actnData.crin_img_atfl_no = ''
        }
      })
      if (JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) && JSON.stringify(this.actnData) === JSON.stringify(this.actnTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      const infoRow = []
      const temp = {}
      let hopeDt = this.frmData.actn_hope_dt
      if (!this.isEmpty(this.frmData.actn_hope_dt)) {
        hopeDt = hopeDt.replace(/-/gi, '')
      }
      let chkDt = this.frmData.crin_chk_dt
      if (!this.isEmpty(this.frmData.crin_chk_dt)) {
        chkDt = chkDt.replace(/-/gi, '')
      }
      let chkHm = this.frmData.crin_chk_hm
      if (!this.isEmpty(this.frmData.crin_chk_hm)) {
        chkHm = chkHm.replace(/:/gi, '')
      }

      temp.wkpl_id = this.frmData.wkpl_id
      temp.crin_id = this.frmData.crin_id
      temp.crin_cl_cd = this.frmData.crin_cl_cd
      temp.actn_hope_dt = hopeDt
      temp.crin_risk_tp_cd = this.frmData.crin_risk_tp_cd
      temp.crin_risk_tp_cd_2 = this.frmData.crin_risk_tp_cd_2
      temp.crin_actn_schd_emp_id = this.frmData.crin_actn_schd_emp_id
      temp.crin_txt = this.frmData.crin_txt
      temp.crin_img_atfl_no = this.frmData.crin_img_atfl_no
      temp.crin_chk_dt = chkDt
      temp.crin_chk_hm = chkHm
      temp.crin_chk_wkpl_loc_id = this.frmData.crin_chk_wkpl_loc_id
      temp.crin_chk_dtl_loc_nm = this.frmData.crin_chk_dtl_loc_nm
      temp.crin_chk_supe_emp_id = this.frmData.crin_chk_supe_emp_id
      temp.crin_chk_note_txt = this.frmData.crin_chk_note_txt
      temp.crin_chk_tgt_nm = this.frmData.crin_chk_tgt_nm
      temp.lnk_chk_id = this.frmData.lnk_chk_id
      temp.username = this.user.emp_no
      temp.send_yn = 'Y'
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      const actnRow = []
      const tempActn = {}
      let actnDt = this.actnData.acco_dt
      if (!this.isEmpty(this.actnData.acco_dt)) {
        actnDt = actnDt.replace(/-/gi, '')
      }
      let actnHm = this.actnData.acco_hm_h.concat(this.actnData.acco_hm_s)
      if (!this.isEmpty(this.actnData.acco_hm)) {
        actnHm = actnHm.replace(/:/gi, '')
      }
      let stCd = ''
      if (this.isEmpty(this.frmData.crin_id)) {
        stCd = '10'
      } else {
        stCd = '20' // 조치중
      }

      tempActn.wkpl_id = this.frmData.wkpl_id
      tempActn.crin_id = this.frmData.crin_id
      tempActn.crin_cl_cd = this.frmData.crin_cl_cd
      tempActn.crin_actn_rspn_emp_id = this.actnData.crin_actn_rspn_emp_id
      tempActn.actn_txt = this.actnData.actn_txt
      tempActn.crin_img_atfl_no = this.actnData.crin_img_atfl_no
      tempActn.acco_dt = actnDt
      tempActn.acco_hm = actnHm
      tempActn.crin_prst_cd = stCd
      tempActn.username = this.user.emp_no
      actnRow.push(tempActn)
      this.frmData.actnRow = actnRow

      await this.$axios.put('/api/v1/she/sftchk/crin-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.crin_id = res.data.key1
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      // Validation 체크
      if (!this.saveValidate('appr')) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사진 업로드 과정
      await this.$refs.picPop1.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.crin_img_atfl_no = data
        } else {
          this.frmData.crin_img_atfl_no = ''
        }
      })
      await this.$refs.picPop2.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.actnData.crin_img_atfl_no = data
        } else {
          this.actnData.crin_img_atfl_no = ''
        }
      })

      // if (this.isEmpty(this.frmData.crin_img_atfl_no)) {
      //   this.$toast.show('점검 사진을 등록해주세요.', { className: 'toast_error' }) // 점검 사진을 등록해주세요.
      //   // this.$refs.picPop.focus()
      //   return
      // }

      // if (this.isEmpty(this.actnData.crin_img_atfl_no)) {
      //   this.$toast.show('점검 사진을 등록해주세요.', { className: 'toast_error' }) // 점검 사진을 등록해주세요.
      //   // this.$refs.picPop.focus()
      //   return
      // }

      const infoRow = []
      const temp = {}
      let hopeDt = this.frmData.actn_hope_dt
      if (!this.isEmpty(this.frmData.actn_hope_dt)) {
        hopeDt = hopeDt.replace(/-/gi, '')
      }
      let chkDt = this.frmData.crin_chk_dt
      if (!this.isEmpty(this.frmData.crin_chk_dt)) {
        chkDt = chkDt.replace(/-/gi, '')
      }
      let chkHm = this.frmData.crin_chk_hm
      if (!this.isEmpty(this.frmData.crin_chk_hm)) {
        chkHm = chkHm.replace(/:/gi, '')
      }

      temp.wkpl_id = this.frmData.wkpl_id
      temp.crin_id = this.frmData.crin_id
      temp.crin_cl_cd = this.frmData.crin_cl_cd
      temp.actn_hope_dt = hopeDt
      temp.crin_risk_tp_cd = this.frmData.crin_risk_tp_cd
      temp.crin_risk_tp_cd_2 = this.frmData.crin_risk_tp_cd_2
      temp.crin_actn_schd_emp_id = this.frmData.crin_actn_schd_emp_id
      temp.crin_txt = this.frmData.crin_txt
      temp.crin_img_atfl_no = this.frmData.crin_img_atfl_no
      temp.crin_chk_dt = chkDt
      temp.crin_chk_hm = chkHm
      temp.crin_chk_wkpl_loc_id = this.frmData.crin_chk_wkpl_loc_id
      temp.crin_chk_dtl_loc_nm = this.frmData.crin_chk_dtl_loc_nm
      temp.crin_chk_supe_emp_id = this.frmData.crin_chk_supe_emp_id
      temp.crin_chk_note_txt = this.frmData.crin_chk_note_txt
      temp.crin_chk_tgt_nm = this.frmData.crin_chk_tgt_nm
      temp.lnk_chk_id = this.frmData.lnk_chk_id
      temp.username = this.user.emp_no
      temp.send_yn = 'N'
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      const actnRow = []
      const tempActn = {}
      let actnDt = this.actnData.acco_dt
      if (!this.isEmpty(this.actnData.acco_dt)) {
        actnDt = actnDt.replace(/-/gi, '')
      }
      let actnHm = this.actnData.acco_hm
      if (!this.isEmpty(this.actnData.acco_hm)) {
        actnHm = actnHm.replace(/:/gi, '')
      }
      const stCd = '30'

      tempActn.wkpl_id = this.frmData.wkpl_id
      tempActn.crin_id = this.frmData.crin_id
      tempActn.crin_cl_cd = this.frmData.crin_cl_cd
      tempActn.crin_actn_rspn_emp_id = this.actnData.crin_actn_rspn_emp_id
      tempActn.actn_txt = this.actnData.actn_txt
      tempActn.crin_img_atfl_no = this.actnData.crin_img_atfl_no
      tempActn.acco_dt = actnDt
      tempActn.acco_hm = actnHm
      tempActn.crin_prst_cd = stCd
      tempActn.username = this.user.emp_no
      actnRow.push(tempActn)
      this.frmData.actnRow = actnRow

      await this.$axios.put('/api/v1/she/sftchk/crin-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.crin_id = res.data.key1
          this.select()
          this.$refs.picPop1.setEdit(false)
          this.$refs.picPop2.setEdit(false)
          this.apprModify = false
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
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popLocOpen() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.locpop.open(this.frmData, false)
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        console.log(returnData) // eslint-disable-line no-console
        this.$set(this.frmData, 'crin_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'crin_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT', up_cd: '' })
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
