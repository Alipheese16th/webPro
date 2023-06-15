<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 개선조치결과 등록/조회-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('개선조치결과 등록/조회') }}</span>
    </div>
    <div class="work_title">
      <!-- 개선조치결과 등록/조회 -->
      <h2>{{ $t('개선조치결과 등록/조회') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="schData.cmpy_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="schData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="chgLevel1"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--소속부서-->
                <div class="label_tit">{{ $t('소속부서') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="dgsn_org_nm"
                  v-model="schData.dgsn_org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  readonly
                  append-icon="fas fa-search"
                  clearable
                  @click:append="popDeptOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--기간-->
                <div class="label_tit">{{ $t('기간') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="schData.sDate" :max="schData.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.eDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="schData.eDate" :min="schData.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--평가종류-->
                <div class="label_tit">{{ $t('평가종류') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dgsn_apal_type_cd"
                  v-model="schData.dgsn_apal_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000078"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--관리감독자-->
                <div class="label_tit">{{ $t('관리감독자') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="dgsn_apal_supe_emp_nm"
                  v-model="schData.dgsn_apal_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  readonly
                  append-icon="fas fa-search"
                  clearable
                  @click:append="popOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--대분류-->
                <div class="label_tit">{{ $t('대분류') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="lrcl_dngp_id"
                  v-model="schData.lrcl_dngp_id"
                  label="ALL"
                  item-text="dngp_nm"
                  item-value="dngp_id"
                  :items="levels.LEVEL1"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="chgLevel2"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--공정명-->
                <div class="label_tit">{{ $t('공정명') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mdcl_dngp_id"
                  v-model="schData.mdcl_dngp_id"
                  label="ALL"
                  item-text="dngp_nm"
                  item-value="dngp_id"
                  :items="levels.LEVEL2"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dgsn_btt_prst_cd"
                  v-model="schData.dgsn_btt_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000085"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험분류-->
                <div class="label_tit">{{ $t('위험분류') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="risk_cat_cd"
                  v-model="schData.risk_cat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000080"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--유해위험요인-->
                <div class="label_tit">{{ $t('유해위험요인') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="hmfl_risk_fact_cd"
                  v-model="schData.hmfl_risk_fact_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000081"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위험성평가 번호-->
                <div class="label_tit">{{ $t('위험성평가 번호') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field v-model="schData.dgsn_apal_id" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" clearable height="30"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험도(개선전)-->
                <div class="label_tit">{{ $t('위험도(개선전)') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dgsn_apal_level"
                  v-model="schData.dgsn_apal_level"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000084"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위험도(개선후)-->
                <div class="label_tit">{{ $t('위험도(개선후)') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="btt_af_dgsn_apal_level"
                  v-model="schData.btt_af_dgsn_apal_level"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000084"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--개선조치결과 목록-->
        <h3>{{ $t('개선조치결과 목록') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--결재상신-->
          <v-btn v-if="isAdminGrp" color="primary" depressed height="30" @click="appr">{{ $t('LB00000763') }}</v-btn>
          <!--개선조치 요청 메일발송-->
          <v-btn v-if="isAdminGrp" color="primary" depressed height="30" @click="sendMail">{{ $t('개선조치 요청 메일발송') }}</v-btn>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn>
          <!-- 저장 -->
          <v-btn color="primary" depressed height="30" @click="save">{{ $t('LB00000045') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 400px"></div>
    </div>
    <popup ref="pop" mkey1="riskevalaftactlist1" mkey2="riskevalaftactlist2" @close="popclose" />
    <deptslct ref="deptpop" mkey="riskevalaftactlist1" @close="popDeptClose" />
    <imgPopup ref="imgPop" mkey1="riskevalaftactlist1" :ispub="true" @close="popFileClose" />
    <preview ref="preview" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import deptslct from '../../common/deptslct'
import imgPopup from '../imgpopup'
import preview from '../previewpopup'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '개선조치결과 등록/조회',
    key(route) {
      return `/riskeval/aftact/${route.params.catalog}`
    },
  },
  components: {
    popup,
    deptslct,
    imgPopup,
    preview,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CO00000009', 'SH00000078', 'SH00000080', 'SH00000081', 'SH00000084', 'SH00000085'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        SH00000078: [{ cd: '', cd_nm: '' }],
        SH00000080: [{ cd: '', cd_nm: '' }],
        SH00000081: [{ cd: '', cd_nm: '' }],
        SH00000084: [{ cd: '', cd_nm: '' }],
        SH00000085: [{ cd: '', cd_nm: '' }],
      },
      SH00000084: [],
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      levels: {
        LEVEL1: [{ dngp_id: '', dngp_nm: '' }],
        LEVEL2: [{ dngp_id: '', dngp_nm: '' }],
        LEVEL3: [{ dngp_id: '', dngp_nm: '' }],
      },
      dngpData: [],
      popupNm: '',
      profiles: '',
      serverUrl: '',
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('개선조치결과 등록/조회') // 개선조치결과 등록/조회
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
    this.schData.dgsn_apal_id = this.$route.params.riskevalaftactlist
    const user = this.$store.getters['auth/getUser']
    user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdminGrp = true
      }
    })

    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
      this.schData.eDate = this.getTzDate(user.time_zone, '-')
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }

    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000078.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000080.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000081.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000084.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000085.unshift({ cd: '', cd_nm: 'ALL' })
        this.SH00000084 = res.SH00000084

        this.createGrid()
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/she/riskeval/wkpl-dngp-list`, {})
      .then((res) => {
        this.dngpData = res
        this.chgLevel1()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'dgsn_apal_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_dtl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cmpy_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_type_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_type_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_spot_apal_type_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_spot_apal_type_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_supe_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_supe_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_bgn_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'lrcl_dngp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'lrcl_dngp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mdcl_dngp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mdcl_dngp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'smcl_dngp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'smcl_dngp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_dtl_oprn_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_tgt_eqp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_tgt_mtra_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfl_risk_fact_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfl_risk_fact_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_occr_rslt_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cur_sfhe_actn_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_level',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_dec_meas_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_level',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_rspn_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_rspn_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_cmpn_schd_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_bf_img_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_af_img_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'profiles',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'server_url',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('위험성평가 조치번호'), // 위험성평가 조치번호
          name: 'apal_id',
          fieldName: 'apal_id',
          width: '180',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('평가종류'), // 평가종류
          name: 'dgsn_apal_type_nm',
          fieldName: 'dgsn_apal_type_nm',
          width: '150',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('실시일자'), // 실시일자
          name: 'dgsn_apal_bgn_dt',
          fieldName: 'dgsn_apal_bgn_dt',
          width: '100',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('LB00000700'), // 회사
          name: 'cmpy_nm',
          fieldName: 'cmpy_nm',
          width: '150',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '150',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('소속부서'), // 소속부서
          name: 'dgsn_org_nm',
          fieldName: 'dgsn_org_nm',
          width: '100',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('관리감독자'), // 관리감독자
          name: 'dgsn_apal_supe_emp_nm',
          fieldName: 'dgsn_apal_supe_emp_nm',
          width: '100',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('대분류'), // 대분류
          name: 'lrcl_dngp_nm',
          fieldName: 'lrcl_dngp_nm',
          width: '130',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('공정명'), // 공정명
          name: 'mdcl_dngp_nm',
          fieldName: 'mdcl_dngp_nm',
          width: '130',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('세부공정'), // 세부공정
          name: 'smcl_dngp_nm',
          fieldName: 'smcl_dngp_nm',
          width: '150',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('세부작업설명'), // 세부작업설명
          name: 'dgsn_apal_dtl_oprn_txt',
          fieldName: 'dgsn_apal_dtl_oprn_txt',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('도구/설비'), // 도구/설비
          name: 'dgsn_apal_tgt_eqp_nm',
          fieldName: 'dgsn_apal_tgt_eqp_nm',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('물질'), // 물질
          name: 'dgsn_apal_tgt_mtra_nm',
          fieldName: 'dgsn_apal_tgt_mtra_nm',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('위험분류'), // 위험분류
          name: 'risk_cat_nm',
          fieldName: 'risk_cat_nm',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('유해위험요인'), // 유해위험요인
          name: 'hmfl_risk_fact_nm',
          fieldName: 'hmfl_risk_fact_nm',
          width: '350',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('위험발생상황 및 결과'), // 위험발생상황 및 결과
          name: 'risk_occr_rslt_txt',
          fieldName: 'risk_occr_rslt_txt',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('현재의 안전보건조치'), // 현재의 안전보건조치
          name: 'cur_sfhe_actn_txt',
          fieldName: 'cur_sfhe_actn_txt',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('위험성'), // 위험성
          name: 'dgsn_apal_txt',
          fieldName: 'dgsn_apal_txt',
          width: '70',
          editable: false,
          styleCallback(grid, dataCell) {
            const ret = {}
            const level = grid.getValue(dataCell.index.itemIndex, 'dgsn_apal_level')

            if (level === 'VL') {
              ret.styleName = 'level1'
            } else if (level === 'L') {
              ret.styleName = 'level2'
            } else if (level === 'H') {
              ret.styleName = 'level3'
            } else if (level === 'VH') {
              ret.styleName = 'level4'
            }

            return ret
          },
        },
        {
          header: this.$t('위험성 감소대책'), // 위험성 감소대책
          name: 'dgsn_dec_meas_txt',
          fieldName: 'dgsn_dec_meas_txt',
          width: '200',
          styleName: 'left',
          editable: false,
        },
        {
          header: {
            text: this.$t('개선후\n위험성'), // 개선후\n위험성
            styleName: 'multiline-editor',
          },
          name: 'btt_af_dgsn_apal_txt',
          fieldName: 'btt_af_dgsn_apal_txt',
          width: '70',
          editable: false,
          styleCallback(grid, dataCell) {
            const ret = {}
            const level = grid.getValue(dataCell.index.itemIndex, 'btt_af_dgsn_apal_level')

            if (level === 'VL') {
              ret.styleName = 'level1'
            } else if (level === 'L') {
              ret.styleName = 'level2'
            } else if (level === 'H') {
              ret.styleName = 'level3'
            } else if (level === 'VH') {
              ret.styleName = 'level4'
            }

            return ret
          },
        },
        {
          header: this.$t('조치담당자'), // 조치담당자
          name: 'dgsn_btt_rspn_emp_nm',
          fieldName: 'dgsn_btt_rspn_emp_nm',
          width: '150',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('조치완료예정일'), // 조치완료예정일
          name: 'dgsn_btt_cmpn_schd_dt',
          fieldName: 'dgsn_btt_cmpn_schd_dt',
          width: '100',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('진행상태'), // 진행상태
          name: 'dgsn_btt_prst_nm',
          fieldName: 'dgsn_btt_prst_nm',
          width: '100',
          styleName: 'center',
          editable: false,
        },
        {
          header: this.$t('조치내용'), // 조치내용
          name: 'dgsn_btt_txt',
          fieldName: 'dgsn_btt_txt',
          width: '250',
          styleName: 'left placeholder-default',
          editor: {
            type: 'multiline',
          },
          placeHolder: this.$t('조치내용을 입력하세요.'), // 조치내용을 입력하세요.
          placeHolderStyleName: 'place-holder',
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'mode')
            const ret = {}
            if (agYn === 'U') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'field-edit-disabled placeholder-left placeholder-disabled placeholder-default'
            }
            return ret
          },
        },
        {
          header: this.$t('조치사진(전)'), // 조치사진(전)
          name: 'dgsn_btt_bf_img_atfl_no',
          fieldName: 'dgsn_btt_bf_img_atfl_no',
          width: '100',
          editable: false,
          renderer: {
            type: 'image',
            imageField: 'imageUrl',
            exportImage: true,
            imageCallback(grid, cell) {
              const serverUrl = grid.getValue(cell.item.index, 'server_url')
              const img = grid.getValue(cell.item.index, 'dgsn_btt_bf_img_atfl_no')
              if (img) {
                return serverUrl + '/api/v1/public/download/' + img
              } else {
                return ''
              }
            },
            imageHeight: 50,
          },
          button: 'action',
          buttonVisibility: 'visible',
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'mode')
            const ret = {}
            if (agYn === 'U') {
            } else {
              ret.styleName = 'field-edit-disabled'
            }
            return ret
          },
          buttonVisibleCallback: (grid, index, focused, mouseEntered) => {
            const agYn = grid.getValue(index.itemIndex, 'mode')
            if (agYn === 'U' && (focused || mouseEntered)) {
              return true
            } else {
              return false
            }
          },
        },
        {
          header: this.$t('조치사진(후)'), // 조치사진(후)
          name: 'dgsn_btt_af_img_atfl_no',
          fieldName: 'dgsn_btt_af_img_atfl_no',
          width: '100',
          editable: false,
          renderer: {
            type: 'image',
            imageField: 'imageUrl',
            exportImage: true,
            imageCallback(grid, cell) {
              const serverUrl = grid.getValue(cell.item.index, 'server_url')
              const img = grid.getValue(cell.item.index, 'dgsn_btt_af_img_atfl_no')
              if (img) {
                return serverUrl + '/api/v1/public/download/' + img
              } else {
                return ''
              }
            },
            imageHeight: 50,
          },
          button: 'action',
          buttonVisibility: 'visible',
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'mode')
            const ret = {}
            if (agYn === 'U') {
            } else {
              ret.styleName = 'field-edit-disabled'
            }
            return ret
          },
          buttonVisibleCallback: (grid, index, focused, mouseEntered) => {
            const agYn = grid.getValue(index.itemIndex, 'mode')
            if (agYn === 'U' && (focused || mouseEntered)) {
              return true
            } else {
              return false
            }
          },
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({
        checkableExpression: "(value['dgsn_btt_prst_cd'] = 'EA01') or (value['dgsn_btt_prst_cd'] = 'EA02') or (value['dgsn_btt_prst_cd'] = 'EA10')",
      })
      gridView.applyCheckables()
      gridView.setFixedOptions({
        rightCount: 3,
      })

      gridView.header.height = 50
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = true

      gridView.onCellButtonClicked = function (grid, item, column) {
        if (grid.getValue(item.itemIndex, 'mode') === 'R') {
          return false
        }

        if (item.column === 'dgsn_btt_bf_img_atfl_no') {
          grid.commit()
          this.popupNm = 'file1'
          this.schData.atfile_no = grid.getValue(item.itemIndex, 'dgsn_btt_bf_img_atfl_no')
          this.$refs.imgPop.open(this.schData)
        } else if (item.column === 'dgsn_btt_af_img_atfl_no') {
          grid.commit()
          this.popupNm = 'file2'
          this.schData.atfile_no = grid.getValue(item.itemIndex, 'dgsn_btt_af_img_atfl_no')
          this.$refs.imgPop.open(this.schData)
        }
      }.bind(this)

      gridView.onCellItemClicked = function (grid, item, clickData) {
        const serverUrl = grid.getValue(clickData.itemIndex, 'server_url')
        let img = ''
        if (clickData.column === 'dgsn_btt_bf_img_atfl_no') {
          img = grid.getValue(clickData.itemIndex, 'dgsn_btt_bf_img_atfl_no')
        } else if (clickData.column === 'dgsn_btt_af_img_atfl_no') {
          img = grid.getValue(clickData.itemIndex, 'dgsn_btt_af_img_atfl_no')
        }
        if (img !== '' && img !== 0) {
          this.frmData.imgHtml = '<img src="' + serverUrl + '/api/v1/public/download/' + img + '" style="width: 1250px; height: 650px;">'
          this.$refs.preview.open(this.frmData)
        } else {
          this.frmData.imgHtml = ''
        }

        return false
      }.bind(this)
    },
    async select() {
      if (this.isEmpty(this.schData.dgsn_org_nm)) {
        this.schData.dgsn_org_cd = ''
      }
      if (this.isEmpty(this.schData.dgsn_apal_supe_emp_nm)) {
        this.schData.dgsn_apal_supe_emp_id = ''
      }

      const rows = await this.$axios.$get(`/api/v1/she/riskeval/aftact`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      // gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
      if (this.totalcnt > 0) {
        this.profiles = dataProvider.getValue(0, 'profiles')
        this.serverUrl = dataProvider.getValue(0, 'server_url')
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        const v = Number(dataProvider.getValue(i, 'dgsn_apal_scr'))
        const v2 = Number(dataProvider.getValue(i, 'btt_af_dgsn_apal_scr'))
        this.SH00000084.forEach((scr) => {
          if (scr.buf_1st_cntn <= v && scr.buf_2nd_cntn >= v) {
            dataProvider.setValue(i, 'dgsn_apal_txt', scr.cd_nm)
            dataProvider.setValue(i, 'dgsn_apal_level', scr.cd)
            dataProvider.setRowState(i, 'none')
          }

          if (scr.buf_1st_cntn <= v2 && scr.buf_2nd_cntn >= v2) {
            dataProvider.setValue(i, 'btt_af_dgsn_apal_txt', scr.cd_nm)
            dataProvider.setValue(i, 'btt_af_dgsn_apal_level', scr.cd)
            dataProvider.setRowState(i, 'none')
          }
        })

        if (!dataProvider.getValue(i, 'dgsn_btt_txt')) {
          dataProvider.setRowState(i, 'updated')
        }
      }
    },
    async save() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('저장할 항목을 선택해주세요.'), { className: 'toast_error' }) // 저장할 항목을 선택해주세요.
        return
      }

      for (let i = 0; i < checkedRows.length; i++) {
        if (!dataProvider.getValue(checkedRows[i], 'dgsn_btt_txt')) {
          gridView.setCurrent({ dataRow: checkedRows[i], fieldName: 'dgsn_btt_txt' })
          this.$toast.show(this.$t('조치내용을 입력해주세요.'), { className: 'toast_error' }) // 조치내용을 입력해주세요.
          return
        }
        if (this.profiles !== 'local') {
          if (!Number(dataProvider.getValue(checkedRows[i], 'dgsn_btt_bf_img_atfl_no'))) {
            gridView.setCurrent({ dataRow: checkedRows[i], fieldName: 'dgsn_btt_bf_img_atfl_no' })
            this.$toast.show(this.$t('조치사진(전)을 선택해주세요.'), { className: 'toast_error' }) // 조치사진(전)을 선택해주세요.
            return
          }
          if (!Number(dataProvider.getValue(checkedRows[i], 'dgsn_btt_af_img_atfl_no'))) {
            gridView.setCurrent({ dataRow: checkedRows[i], fieldName: 'dgsn_btt_af_img_atfl_no' })
            this.$toast.show(this.$t('조치사진(후)을 선택해주세요.'), { className: 'toast_error' }) // 조치사진(후)을 선택해주세요.
            return
          }
        }
      }

      this.frmData.updateList = []
      for (let i = 0; i < checkedRows.length; i++) {
        if (dataProvider.getRowState(checkedRows[i]) !== 'updated') {
          continue
        }
        const obj = {}
        obj.dgsn_apal_dtl_id = dataProvider.getValue(checkedRows[i], 'dgsn_apal_dtl_id')
        obj.dgsn_btt_prst_cd = 'EA02'
        obj.dgsn_btt_txt = dataProvider.getValue(checkedRows[i], 'dgsn_btt_txt')
        obj.dgsn_btt_bf_img_atfl_no = dataProvider.getValue(checkedRows[i], 'dgsn_btt_bf_img_atfl_no')
        obj.dgsn_btt_af_img_atfl_no = dataProvider.getValue(checkedRows[i], 'dgsn_btt_af_img_atfl_no')
        this.frmData.updateList.push(obj)
      }

      if (this.frmData.updateList.length === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.$axios.put('/api/v1/she/riskeval/aftact', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    codChg(data) {
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
      this.chgLevel1(true)
    },
    codChgReturn(data) {
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    chgLevel1() {
      const wkplId = this.schData.wkpl_id
      this.levels.LEVEL1 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.up_dngp_id === ''
      })
      this.levels.LEVEL1.unshift({ dngp_id: '', dngp_nm: 'ALL', up_dngp_id: '' })
      this.schData.lrcl_dngp_id = ''
      this.chgLevel2()
    },
    chgLevel2() {
      const wkplId = this.schData.wkpl_id
      const upId = this.schData.lrcl_dngp_id
      this.levels.LEVEL2 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.level === 2 && n.up_dngp_id === upId
      })
      this.levels.LEVEL2.unshift({ dngp_id: '', dngp_nm: 'ALL', up_dngp_id: '' })
      this.schData.mdcl_dngp_id = ''
    },
    schReset() {
      this.schData = {}
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
      this.schData.eDate = this.getTzDate(user.time_zone, '-')
      this.codChgReturn(user.cmpn_cd)
      this.chgLevel1()
    },
    popOpen() {
      this.$refs.pop.open(this.schData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'dgsn_apal_supe_emp_nm', i.emp_nm)
          this.$set(this.schData, 'dgsn_apal_supe_emp_id', i.emp_no)
        })
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    popDeptOpen() {
      this.$refs.deptpop.open()
    },
    popDeptClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'dgsn_org_nm', returnData[0].dept_nm)
        this.$set(this.schData, 'dgsn_org_cd', returnData[0].dept_cd)
      }
    },
    popFileClose(returnData) {
      gridView.commit()

      if (!returnData) {
        return false
      }
      let atfileNo = null
      if (returnData.checkFile !== 0) {
        atfileNo = returnData.atfile_no
      }
      if (this.popupNm === 'file1') {
        dataProvider.setValue(gridView.getCurrent().dataRow, 'dgsn_btt_bf_img_atfl_no', atfileNo)
      } else if (this.popupNm === 'file2') {
        dataProvider.setValue(gridView.getCurrent().dataRow, 'dgsn_btt_af_img_atfl_no', atfileNo)
      }
      gridView.commit()
      gridView.refresh()
    },
    async sendMail() {
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('메일 발송할 항목을 선택해주세요.'), { className: 'toast_error' }) // 메일 발송할 항목을 선택해주세요.
        return
      }

      let chk = false
      for (let i = 0; i < checkedRows.length; i++) {
        const cd = dataProvider.getValue(checkedRows[i], 'dgsn_btt_prst_cd')
        if (cd === 'EA02') {
          chk = true
          break
        }
      }
      if (chk) {
        this.$toast.show(this.$t('조치완료인 항목은 메일을 발송할 수 없습니다.'), { className: 'toast_error' }) // 조치완료인 항목은 메일을 발송할 수 없습니다.
        return
      }

      const res = await this.$confirm('메일을 발송하시겠습니까?') // 메일을 발송하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.kind = 'Mail'
      this.frmData.mailMap = {}
      for (let i = 0; i < checkedRows.length; i++) {
        const empId = dataProvider.getValue(checkedRows[i], 'dgsn_btt_rspn_emp_id')
        let mail = {}
        // eslint-disable-next-line no-prototype-builtins
        if (this.frmData.mailMap.hasOwnProperty(empId)) {
          mail = this.frmData.mailMap[empId]
        } else {
          mail = {}
          mail.dtlList = []
          this.frmData.mailMap[empId] = mail
        }

        const obj = {}
        obj.apal_id = dataProvider.getValue(checkedRows[i], 'apal_id')
        obj.smcl_dngp_nm = dataProvider.getValue(checkedRows[i], 'smcl_dngp_nm')
        obj.dgsn_btt_cmpn_schd_dt = dataProvider.getValue(checkedRows[i], 'dgsn_btt_cmpn_schd_dt')
        mail.dtlList.push(obj)
        mail.to = dataProvider.getValue(checkedRows[i], 'dgsn_btt_rspn_emp_id')
        mail.cc = dataProvider.getValue(checkedRows[i], 'dgsn_apal_supe_emp_id')
      }

      this.$axios.post('/api/v1/she/riskeval/aftact', this.frmData).then((res) => {
        if (res.data === 'Success') {
          gridView.checkAll(false)
          this.$toast.show(this.$t('MS00000286')) // 발송되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('결재할 항목을 선택해주세요.'), { className: 'toast_error' }) // 결재할 항목을 선택해주세요.
        return
      }
      let chk = false
      for (let i = 0; i < checkedRows.length; i++) {
        const cd = dataProvider.getValue(checkedRows[i], 'dgsn_btt_prst_cd')
        if (cd !== 'EA02') {
          chk = true
          break
        }
      }
      if (chk) {
        this.$toast.show(this.$t('조치완료인 항목만 결재할 수 있습니다.'), { className: 'toast_error' }) // 조치완료인 항목만 결재할 수 있습니다.
        return
      }
      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.kind = 'Appr'
      this.frmData.idList = []
      for (let i = 0; i < checkedRows.length; i++) {
        const id = dataProvider.getValue(checkedRows[i], 'dgsn_apal_dtl_id')
        this.frmData.idList.push(id)
      }

      this.$axios.post('/api/v1/she/riskeval/aftact', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl14 + aprvno + empno
          window.open(url, 'aftact', options)
          this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    excelExport() {
      const rows = dataProvider.getStateRows('created')
      rows.push(...dataProvider.getStateRows('updated'))
      rows.push(...dataProvider.getStateRows('none'))
      if (rows.length === 0) {
        this.$toast.show(this.$t('출력할 데이터가 존재하지 않습니다.'), { className: 'toast_error' }) // 출력할 데이터가 존재하지 않습니다.
        return
      }
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('개선조치결과') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 개선조치결과

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        exportImage: true,
        applyDynamicStyles: true,
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
  },
}
</script>
<style>
.level1 {
  background: rgb(0, 128, 0);
  color: rgb(255, 255, 255);
}
.level2 {
  background: rgb(76, 175, 80);
  color: rgb(255, 255, 255);
}
.level3 {
  background: rgb(248, 128, 48);
  color: rgb(255, 255, 255);
}
.level4 {
  background: rgb(255, 0, 0);
  color: rgb(255, 255, 255);
}
.multiline-editor {
  white-space: pre;
}
.rg-fixed-body .rg-data-row {
  background: #fff;
}
</style>
