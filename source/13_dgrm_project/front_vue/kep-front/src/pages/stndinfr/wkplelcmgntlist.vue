<template>
  <div>
    <div class="location">
      <!-- Home > 사업장 정보 > 사업장 관리 -->
      <span>Home > 사업장 정보 > 법적 선임 현황</span>
    </div>
    <div class="work_title" style="text_align: left">
      <!-- 법적인원관리 -->
      <h2>법적 선임 현황</h2>
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
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="isSearchDisabled"
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
                  :disabled="isSearchDisabled"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--법적선임명-->
                <div class="label_tit">법적선임명</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="elc_cl_cd"
                  v-model="schData.elc_cl_cd"
                  outlined
                  single-line
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000004"
                  :hide-details="true"
                  dense
                  height="30"
                  @keyup.enter="select"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--대행여부-->
                <div class="label_tit">대행여부</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="ag_yn"
                  v-model="schData.ag_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="ag_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--성명-->
                <div class="label_tit">성명</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="elc_pric_nm"
                  v-model="schData.elc_pric_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  clearable
                  :disabled="isSearchDisabled"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--법정교육-->
                <div class="label_tit">법정교육</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mand_tlgl_trn_yn"
                  v-model="schData.mand_tlgl_trn_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="mand_tlgl_trn_yn_cd"
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
                <!--교육수료일-->
                <div class="label_tit">교육수료일</div>
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
                <!--교육이수증-->
                <div class="label_tit">교육이수증</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="trn_fcst_cert_reg_yn"
                  v-model="schData.trn_fcst_cert_reg_yn"
                  outlined
                  single-line
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000090"
                  :hide-details="true"
                  dense
                  height="30"
                  @keyup.enter="select"
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
      <v-col cols="2">
        <div class="sch_btn">
          <!-- 초기화 -->
          <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
          <!-- 검색 -->
          <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
        </div>
      </v-col>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--선임 현황-->
        <h3>선임 현황</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--프린트-->
          <v-btn color="primary" depressed height="30" @click="reportOutput()">프린트</v-btn>
          <!--교육이수증 등록 요청 메일 발송-->
          <v-btn color="primary" depressed height="30" @click="sendMail()">교육이수증 등록 요청 메일 발송</v-btn>
          <!-- 결재상신 -->
          <v-btn color="primary" depressed height="30" @click="appr()">결재상신</v-btn>
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 436px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <wkplTrnPop ref="wkplTrnPop" mkey="wkplmgntlistPop" @close="wkplTrnPopClose" />
    <filePopup ref="filepop" mkey1="wkplmgntdtledtatflgrid2" :edit-chk="false" @close="filepopclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import wkplTrnPop from '../stndinfr/wkplTrnPop'
import filePopup from '../common/atflgrid.vue'

let sDate = new Date()
let eDate = new Date()

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '사업장 관리',
    key(route) {
      return `/stndinfr/stndinfr/${route.params.catalog}`
    },
  },
  components: {
    filePopup,
    wkplTrnPop,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      isAdmin: false,
      isSearchDisabled: true,
      totalcnt: 0,
      schData: {
        cmpy_cd: '',
        wkpl_id: '',
        elc_cl_cd: '',
        ag_yn: '',
        elc_pric_nm: '',
        mand_tlgl_trn_yn: '',
        trn_fcst_cert_reg_yn: '',
        sDate: '',
        eDate: '',
      },
      elc_atfl_cnt: 0,
      menu1: false,
      menu2: false,
      mand_tlgl_trn_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      ag_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      mailData: {},
      user: {},
      codeid: ['CO00000009', 'ST00000004', 'SH00000090'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        ST00000004: [{ cd: '', cd_nm: '' }],
        SH00000090: [{ cd: '', cd_nm: '' }],
      },
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001201') // 사업장 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 시스템관리자, 환경안전부서 권한 체크
    this.user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdmin = true
      }
    })

    // 관리자일 경우 검색조건 활성화
    if (this.isAdmin === true) {
      this.isSearchDisabled = false
    }

    eDate = this.getTzDateObj(this.user.time_zone)
    sDate = this.getTzDateObj(this.user.time_zone)
    sDate.setFullYear(sDate.getFullYear() - 5)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')
    if (this.$route.params.wkpl_id !== null && this.$route.params.wkpl_id !== undefined) {
      this.tmpSchData = this.$route.params.schData // 이전 검색조건 저장
      this.schData.cmpy_cd = this.$route.params.cmpy_cd
      this.schData.wkpl_id = this.$route.params.wkpl_id
    } else {
      this.tmpSchData = { cmpy_cd: this.user.cmpn_cd } // 이전 검색조건 저장
      this.schData.cmpy_cd = this.user.cmpn_cd
      this.schData.wkpl_id = this.user.wkpl_id
      // this.schData.elc_pric_nm = this.user.emp_nm
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.ST00000004.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000090.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 그리드 세팅
    // 선임정보
    const fields2 = [
      {
        fieldName: 'cmpy_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_cl_oth_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_reg_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_head_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_dtl_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_dtl_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ag_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'bsns_agen_orga_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'bsns_agen_cont_rnew_dt',
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
      },
      {
        fieldName: 'elc_pric_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_pric_dept_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_pric_pstn_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_rspn_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_rspn_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ctsv_yrs',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ctsv_mths',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_elc_dt',
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
      },
      {
        fieldName: 'elc_crr_yrs',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_crr_mths',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_pric_tel_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_pric_email',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crtf_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mand_tlgl_trn_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'fst_trn_fcst_dt',
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
      },
      {
        fieldName: 'rglr_trn_fcst_dt',
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
      },
      {
        fieldName: 'elc_trn_cyc_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_trn_cyc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cert_reg_yn_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cert_reg_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_expr_dt',
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
      },
      {
        fieldName: 'elc_atfl_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfile_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfile_seq',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfile_url',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_infr_atfl_no',
        dataType: ValueType.TEXT,
      },
    ]
    // 그리드 2 시작
    const columns2 = [
      {
        header: this.$t('LB00000909'), // 회사명
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '130',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001217'), // 사업장명
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '130',
        styleName: 'left',
      },
      {
        header: '선임명', // 선임명
        name: 'elc_cl_nm',
        fieldName: 'elc_cl_nm',
        width: '180',
        styleName: 'left',
      },
      // {
      //   header: '기타 선임명', // 기타 선임명
      //   name: 'elc_cl_oth_txt',
      //   fieldName: 'elc_cl_oth_txt',
      //   width: '180',
      //   styleName: 'left',
      // },
      {
        header: '구분', // 구분
        name: 'elc_dtl_cl_nm',
        fieldName: 'elc_dtl_cl_nm',
        width: '80',
      },
      {
        header: this.$t('LB00001214'), // 대행여부
        name: 'ag_yn',
        fieldName: 'ag_yn',
        width: '70',
      },
      {
        header: '대행기관', // 대행기관
        name: 'bsns_agen_orga_nm',
        fieldName: 'bsns_agen_orga_nm',
        width: '120',
        styleName: 'left',
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn !== 'Y') {
            ret.styleName = 'left field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '계약/갱신일', // 계약/갱신일
        name: 'bsns_agen_cont_rnew_dt',
        fieldName: 'bsns_agen_cont_rnew_dt',
        width: '100',
        datetimeFormat: 'yyyy-MM-dd',
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000701'), // 성명
        name: 'elc_pric_nm',
        fieldName: 'elc_pric_nm',
        width: '80',
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn === 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '소속부서', // 소속부서
        name: 'elc_pric_dept_nm',
        fieldName: 'elc_pric_dept_nm',
        width: '100',
        styleName: 'left',
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn === 'Y') {
            ret.styleName = 'left field-edit-disabled'
          }
          return ret
        },
      },
      {
        header: '직급', // 직급
        name: 'elc_pric_pstn_nm',
        fieldName: 'elc_pric_pstn_nm',
        width: '80',
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn === 'Y') {
            ret.styleName = 'field-edit-disabled'
          }
          return ret
        },
      },
      {
        header: '근속년수', // 근속년수
        name: 'ctsv_yrs',
        fieldName: 'ctsv_yrs',
        width: '80',
        displayCallback: (grid, index, value) => {
          let str = ''
          if (!this.isEmpty(value)) {
            str += value + '년'
          }
          return str
        },
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn === 'Y') {
            ret.styleName = 'field-edit-disabled'
          }
          return ret
        },
      },
      {
        header: '근속개월수', // 근속개월
        name: 'ctsv_mths',
        fieldName: 'ctsv_mths',
        width: '80',
        displayCallback: (grid, index, value) => {
          let str = ''
          if (!this.isEmpty(value)) {
            str += value + '개월'
          }
          return str
        },
        styleCallback: (grid, dataCell) => {
          const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
          const ret = {}
          if (agYn === 'Y') {
            ret.styleName = 'field-edit-disabled'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00001213'), // 선임일
        name: 'wkpl_elc_dt',
        fieldName: 'wkpl_elc_dt',
        width: '100',
        datetimeFormat: 'yyyy-MM-dd',
      },
      {
        header: '선임경력년수', // 선임경력년수
        name: 'elc_crr_yrs',
        fieldName: 'elc_crr_yrs',
        width: '100',
        displayCallback: (grid, index, value) => {
          let str = ''
          if (!this.isEmpty(value)) {
            str += value + '년'
          }
          return str
        },
      },
      {
        header: '선임경력개월수', // 선임경력개월수
        name: 'elc_crr_mths',
        fieldName: 'elc_crr_mths',
        width: '100',
        displayCallback: (grid, index, value) => {
          let str = ''
          if (!this.isEmpty(value)) {
            str += value + '개월'
          }
          return str
        },
      },
      {
        header: this.$t('LB00000723'), // 전화번호
        name: 'elc_pric_tel_no',
        fieldName: 'elc_pric_tel_no',
        width: '130',
      },
      {
        header: this.$t('LB00000910'), // 이메일주소
        name: 'elc_pric_email',
        fieldName: 'elc_pric_email',
        width: '240',
        styleName: 'left',
      },
      {
        header: '관련자격(자격증, 교육 등)', // 관련자격(자격증, 교육 등)
        name: 'crtf_nm',
        fieldName: 'crtf_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: '법정교육', // 법정교육
        name: 'mand_tlgl_trn_yn',
        fieldName: 'mand_tlgl_trn_yn',
        width: '70',
      },
      {
        header: '최초교육일', // 최초교육일
        name: 'fst_trn_fcst_dt',
        fieldName: 'fst_trn_fcst_dt',
        width: '100',
        datetimeFormat: 'yyyy-MM-dd',
        styleCallback: (grid, dataCell) => {
          const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
          const ret = {}
          if (mandTlglTrnYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '정기/보수교육일', // 정기/보수교육일
        name: 'rglr_trn_fcst_dt',
        fieldName: 'rglr_trn_fcst_dt',
        width: '100',
        datetimeFormat: 'yyyy-MM-dd',
        styleCallback: (grid, dataCell) => {
          const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
          const ret = {}
          if (mandTlglTrnYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '교육주기', // 교육주기
        name: 'elc_trn_cyc_nm',
        fieldName: 'elc_trn_cyc_nm',
        width: '70',
        styleCallback: (grid, dataCell) => {
          const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
          const ret = {}
          if (mandTlglTrnYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '만료일', // 만료일
        name: 'trn_expr_dt',
        fieldName: 'trn_expr_dt',
        width: '100',
        datetimeFormat: 'yyyy-MM-dd',
        styleCallback: (grid, dataCell) => {
          const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
          const ret = {}
          if (mandTlglTrnYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: '교육이수증', // 교육이수증
        name: 'trn_fcst_cert_reg_yn_nm',
        fieldName: 'trn_fcst_cert_reg_yn_nm',
        width: '80',
        button: 'action',
        buttonVisibility: 'always',
        buttonVisibleCallback: (grid, index, focused, mouseEntered) => {
          const mandTlglTrnYn = grid.getValue(index.itemIndex, 'mand_tlgl_trn_yn')
          if (mandTlglTrnYn === 'Y' && (focused || mouseEntered)) {
            return true
          } else {
            return false
          }
        },
        styleCallback: (grid, dataCell) => {
          const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
          const ret = {}
          if (mandTlglTrnYn !== 'Y') {
            ret.styleName = 'field-edit-disabled'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000121'), // 첨부파일
        name: 'elc_atfl_cnt',
        fieldName: 'elc_atfl_cnt',
        width: '70',
        button: 'action',
        alwaysShowButton: true,
        editable: false,
      },
    ]
    const headergroup = [
      'cmpy_nm', // 회사명
      'wkpl_nm', // 사업장명
      {
        name: 'companyGroup1',
        direction: 'horizontal',
        height: '60',
        items: ['elc_cl_nm', 'elc_dtl_cl_nm'], // 그룹에 들어갈 컬럼
        header: {
          text: '법적선임구분', // 법적선임구분
        },
      },
      'ag_yn', // 대행여부
      'bsns_agen_orga_nm', // 대행기관
      'bsns_agen_cont_rnew_dt', // 계약/갱신일
      'elc_pric_nm', // 성명
      'elc_pric_dept_nm', // 부서
      'elc_pric_pstn_nm', // 직급
      {
        name: 'companyGroup2',
        direction: 'horizontal',
        height: '60',
        items: ['ctsv_yrs', 'ctsv_mths'], // 그룹에 들어갈 컬럼
        header: {
          text: '근속기간', // 근속기간
        },
      },
      'wkpl_elc_dt', // 선임일
      {
        name: 'companyGroup2',
        direction: 'horizontal',
        height: '60',
        items: ['elc_crr_yrs', 'elc_crr_mths'], // 그룹에 들어갈 컬럼
        header: {
          text: '선임경력', // 선임경력
        },
      },
      'elc_pric_tel_no', // 전화번호
      'elc_pric_email', // 이메일주소
      'crtf_nm', // 관련자격(자격증, 교육 등)
      'mand_tlgl_trn_yn', // 법정교육
      'fst_trn_fcst_dt', // 최초교육일
      'rglr_trn_fcst_dt', // 정기/보수교육일
      'elc_trn_cyc_nm', // 교육주기
      'trn_expr_dt', // 만료일
      'trn_fcst_cert_reg_yn_nm', // 교육이수증
      'elc_atfl_cnt', // 첨부파일
    ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields2)
    gridView = new GridView('realgrid2')
    gridView.setDataSource(dataProvider)
    gridView.setColumnLayout(headergroup)
    gridView.setColumns(columns2)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true, width: 30 })
    gridView.editOptions.editable = false
    gridView.header.height = 50
    gridView.footer.height = 40
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.fitStyle = 'fill'

    gridView.onCellButtonClicked = function (grid, index, column) {
      if (index.column === 'trn_fcst_cert_reg_yn_nm') {
        const param = {}
        param.wkpl_id = dataProvider.getValue(index.dataRow, 'wkpl_id')
        param.elc_rspn_emp_id = dataProvider.getValue(index.dataRow, 'elc_rspn_emp_id')
        param.elc_pric_nm = dataProvider.getValue(index.dataRow, 'elc_pric_nm')
        param.elc_cl_cd = dataProvider.getValue(index.dataRow, 'elc_cl_cd')
        param.elc_cl_oth_txt = dataProvider.getValue(index.dataRow, 'elc_cl_oth_txt')
        param.elc_cl_nm = dataProvider.getValue(index.dataRow, 'elc_cl_nm')
        if (!this.isEmpty(dataProvider.getValue(index.dataRow, 'rglr_trn_fcst_dt'))) {
          param.rglr_trn_fcst_dt = this.get_date_str_gubun(dataProvider.getValue(index.dataRow, 'rglr_trn_fcst_dt'), '-')
        }
        if (!this.isEmpty(dataProvider.getValue(index.dataRow, 'fst_trn_fcst_dt'))) {
          param.fst_trn_fcst_dt = this.get_date_str_gubun(dataProvider.getValue(index.dataRow, 'fst_trn_fcst_dt'), '-')
        }
        this.$refs.wkplTrnPop.open(param)
      } else if (index.column === 'elc_atfl_cnt') {
        this.elcRowVal = index.dataRow
        this.filePopGb = 'elc'
        const atfileNo = grid.getValue(index.itemIndex, 'elc_infr_atfl_no')
        this.schData.atfile_no = atfileNo
        this.$refs.filepop.open(this.schData)
      }
    }.bind(this)
  },
  methods: {
    async select() {
      if (this.isEmpty(this.schData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000339'), { className: 'toast_warn' }) // 회사를 입력해주세요.
        this.$refs.cmpy_cd.focus()
        return
      }

      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-elc-mgnt-dtl`, { params: this.schData })
      dataProvider.setRows(rows.elc)
      gridView.refresh()
      gridView.setTopItem(0)

      this.totalcnt = dataProvider.getRowCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    async sendMail() {
      gridView.commit()
      const rows = gridView.getCheckedRows()
      if (rows.length === 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_warn' }) // 선택한 데이터가 없습니다.
        return false
      }
      if (rows.length > 1) {
        this.$toast.show('교육이수증 등록 요청 메일 발송은 한 건씩만 가능합니다.', { className: 'toast_warn' }) // 교육이수증 등록 요청 메일 발송은 한 건씩만 가능합니다.
        return false
      }
      const mandTlglTrnYn = dataProvider.getValue(rows[0], 'mand_tlgl_trn_yn')
      const trnFcstCertRegYn = dataProvider.getValue(rows[0], 'trn_fcst_cert_reg_yn')
      if (mandTlglTrnYn === 'N') {
        this.$toast.show('교육이수증 등록이 불필요한 대상입니다.', { className: 'toast_warn' }) // 교육이수증 등록이 불필요한 대상입니다.
        return false
      }
      if (mandTlglTrnYn === 'Y' && trnFcstCertRegYn === 'Y') {
        this.$toast.show('교육이수증이 이미 등록된 상태입니다.', { className: 'toast_warn' }) // 교육이수증이 이미 등록된 상태입니다.
        return false
      }

      const res = await this.$confirm(this.$t('MS00000285')) // 메일발송하시겠습니까?
      if (!res) {
        return
      }

      const dataOutputOptions = {
        nullText: '',
        datetimeFormat: 'yyyy-MM-dd',
      }
      const mailInfo = dataProvider.getOutputRow(dataOutputOptions, rows[0])
      this.mailData = mailInfo

      // eslint-disable-next-line no-console
      console.log(this.mailData)

      this.$axios.put(`/api/v1/st/stndinfr/wkpl-trn-mail`, this.mailData).then((res) => {
        if (res.result !== 'Success') {
          this.$toast.show('메일이 발송되었습니다.') // 메일이 발송되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    // 선임현황 출력
    reportOutput() {
      gridView.commit()
      const rows = dataProvider.getStateRows('created')
      rows.push(...dataProvider.getStateRows('updated'))
      rows.push(...dataProvider.getStateRows('none'))
      if (rows.length === 0) {
        this.$toast.show('출력할 데이터가 존재하지 않습니다.', { className: 'toast_error' }) // 출력할 데이터가 존재하지 않습니다.
        return
      }
      this.printData = this.schData
      this.printData.reportForm = 'wkplElcMgntDtledt'

      if (this.printData.elc_pric_nm === null || this.printData.elc_pric_nm === undefined) {
        this.printData.elc_pric_nm = ''
      }
      this.$axios.post(`/api/v1/comm/report/wkplElcRpt`, this.printData).then((res) => {
        const popupWidth = 900
        const popupHeight = window.screen.height - 300
        const popupX = window.screen.width / 2 - popupWidth / 2
        const popupY = window.screen.height / 2 - popupHeight / 2
        const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
        const wnd = window.open('', 'report', options)
        wnd.document.open()
        wnd.document.write(res.data)
        wnd.document.close()
        // this.print()
      })
    },
    async appr() {
      gridView.commit()
      const rows = gridView.getCheckedRows()

      if (rows.length === 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_warn' }) // 선택한 데이터가 없습니다.
        return false
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      const aprvData = { wkpl_id: '' }
      aprvData.aprvList = []
      const dataOutputOptions = {
        nullText: '',
        datetimeFormat: 'yyyy-MM-dd',
      }
      for (let i = 0; i < rows.length; i++) {
        const aprvInfo = dataProvider.getOutputRow(dataOutputOptions, rows[i])
        if (i === 0) {
          aprvData.wkpl_id = aprvInfo.wkpl_id
        }
        aprvData.aprvList.push(aprvInfo)
      }

      console.log('=> aprvData: ' + JSON.stringify(aprvData)) // eslint-disable-line no-console

      await this.$axios.post('/api/v1/st/stndinfr/wkpl-trn-aprv', aprvData).then((res) => {
        const config = require('../../../nuxt.config.js')
        if (res.data !== 'Fail') {
          console.log('=> wkpl-trn-aprv success') // eslint-disable-line no-console
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl13 + aprvno + empno
          window.open(url, 'tour-appr', options)
          gridView.setAllCheck(false)
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    schReset() {
      const user = this.$store.getters['auth/getUser']
      this.schData = {
        cmpy_cd: this.user.cmpn_cd,
        wkpl_id: this.user.wkpl_id,
        elc_cl_cd: '',
        ag_yn: '',
        elc_pric_nm: this.user.emp_nm,
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
        mand_tlgl_trn_yn: '',
        trn_fcst_cert_reg_yn: '',
      }

      this.codChgReturn(user.cmpn_cd)
    },
    filepopclose(returnData) {
      gridView.commit()
      // console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.filePopGb === 'elc') {
        dataProvider.setValue(this.elcRowVal, 'elc_infr_atfl_no', returnData.atfile_no)
        dataProvider.setValue(this.elcRowVal, 'elc_atfl_cnt', returnData.file_cnt)
      }
      this.select()
    },
    wkplTrnPopClose(returnData) {
      console.log(returnData) // eslint-disable-line no-console
      this.select()
    },
    excelExport() {
      gridView.commit()
      const rows = dataProvider.getStateRows('created')
      rows.push(...dataProvider.getStateRows('updated'))
      rows.push(...dataProvider.getStateRows('none'))
      if (rows.length === 0) {
        this.$toast.show('출력할 데이터가 존재하지 않습니다.', { className: 'toast_error' }) // 출력할 데이터가 존재하지 않습니다.
        return
      }
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '법적인원관리' + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 작업허가관리

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
        applyDynamicStyles: true,
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    goList() {
      this.$router.replace({
        name: `stndinfr-wkplmgntlist`,
        params: {
          schData: this.tmpSchData,
        },
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
