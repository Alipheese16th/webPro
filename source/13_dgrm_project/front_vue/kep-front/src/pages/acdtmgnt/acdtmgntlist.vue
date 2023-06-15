<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 사고 보고 및 조사 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00001045') }}</span>
    </div>
    <div class="work_title">
      <!-- 사고 조회 및 등록 -->
      <h2>{{ $t('LB00000801') }}</h2>
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
              <v-col cols="3" class="dp_flex">
                <v-select
                  v-model="F_codes.F_00000001_val"
                  style="width: calc(100% - 56px)"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  :hide-details="true"
                  dense
                  height="30"
                  multiple
                >
                  <template v-slot:prepend-item>
                    <v-list-item ripple @mousedown.prevent @click="toggleAll">
                      <v-list-item-action>
                        <v-icon :color="F_codes.F_00000001_val.length > 0 ? 'indigo darken-4' : ''">
                          {{ wkplIcon }}
                        </v-icon>
                      </v-list-item-action>
                      <v-list-item-content>
                        <v-list-item-title>Select All</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                    <v-divider class="mt-2"></v-divider>
                  </template>
                  <template v-slot:selection="{ item, index }" class="dp_flex">
                    <v-chip v-if="index === 0" style="height: 24px; max-width: 75%">
                      <span class="ellipsis">{{ item.cd_nm }}</span>
                    </v-chip>
                    <span v-if="index === 1" class="grey--text text-caption"> 외 {{ F_codes.F_00000001_val.length - 1 }}건 </span>
                  </template>
                </v-select>
              </v-col>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.dfdt_dept_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="select"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사고발생일-->
                <div class="label_tit">{{ $t('LB00000803') }}</div>
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
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.acdt_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000056"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사고구분-->
                <div class="label_tit">{{ $t('LB00000806') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.acdt_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000024"
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
                <!--사고유형-->
                <div class="label_tit">{{ $t('LB00001279') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.acdt_tp_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000071"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--산재여부-->
                <div class="label_tit">{{ $t('LB00000807') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="inac_yn"
                  v-model="schData.inac_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000070"
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
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사고목록-->
        <h3>{{ $t('LB00000808') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--URL복사-->
          <v-btn depressed color="primary" height="30" @click="onUrlCopy()">URL복사</v-btn>
          <!--사고속보 미리보기/발송-->
          <v-btn v-show="isSendAuthority" depressed color="primary" height="30" @click="onAcdtReport()">사고 속보 미리보기/발송</v-btn>
          <!--엑셀다운로드-->
          <v-btn depressed color="primary" height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn depressed color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <acdtrptpopup ref="acdtrptpopup" />
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div id="excelgrid" style="width: 100%; height: 0px; display: none"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import acdtrptpopup from './acdtrptpopup'

let gridView = GridView
let dataProvider = LocalDataProvider

let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '사고관리',
    key(route) {
      return `/acdtmgnt/${route.params.catalog}`
    },
  },
  components: {
    acdtrptpopup,
  },
  data() {
    return {
      isSendAuthority: false,
      schData: {},
      frmData: {},
      tempData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000056', 'SH00000024', 'CO00000009', 'SH00000070', 'SH00000071'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000056: [{ cd: '', cd_nm: '' }], // 진행상태
        SH00000024: [{ cd: '', cd_nm: '' }], // 사고구분
        CO00000009: [{ cd: '', cd_nm: '' }], // 회사코드
        SH00000070: [{ cd: '', cd_nm: '' }], // 사고관리 산재여부
        SH00000071: [{ cd: '', cd_nm: '' }], // 사고유형
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
        F_00000001_val: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000801') // 사고관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
    likesAllwkpl() {
      return this.F_codes.F_00000001_val.length === this.F_codes.F_00000001.length
    },
    likesSomewkpl() {
      return this.F_codes.F_00000001_val.length > 0
    },
    wkplIcon() {
      if (this.likesAllwkpl) return 'mdi-close-box'
      if (this.likesSomewkpl) return 'mdi-minus-box'
      return 'mdi-checkbox-blank-outline'
    },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']

    sDate = this.getTzDateObj(user.time_zone)
    eDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(eDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
      this.F_codes.F_00000001_val = this.schData.chk_list.split(',')
      console.log(this.schData.chk_list) // eslint-disable-line no-console
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.F_codes.F_00000001_val = [user.wkpl_id]
    }

    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        // this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
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
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000056.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000024.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000070.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000071.unshift({ cd: '', cd_nm: 'ALL' })

        this.createGrid()
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    // 시스템관리자 그룹 사용자 or 환경안전 부서 인지 체크
    user.roleTypeCds.forEach((i) => {
      if (i === 'M01' || i === 'S01') {
        this.isSendAuthority = true
      }
    })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'acdt_id', // SHE시스템사고ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm', // 사업장
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_occr_dt', // 사고발생일
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_occr_hm', // 사고발생시간
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'day_week', // 요일
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cmpy_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_dtl_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_tp_nm', // 사고유형
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_caus_tgt_txt', // 사고기인물 상세내용
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'inac_ivst_sbms_tgt_yn', // 산업재해조사표작성여부
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'kor_acdt_occr_org_nm', // 사고발생 부서
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_wkpl_loc_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_dtl_loc_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'writer_department',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_brie_reg_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'inac_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ivst_rprt_reg_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btrm_rslt_reg_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_brie_aprv_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_oprn_txt_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'usft_txt_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'usft_st_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_nm', // 성명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_sex_nm', // 성별
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_age', // 연령
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_los', // 근속연수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_care_days', // 휴업기간(일)
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_occr_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_news_eml_send_yn', // 사고속보발송여부
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cmpy_cd', // 사업장
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_id', // SHE시스템사업장ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_cl_cd', // SHE시스템사고구분코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_sn', // 사고자순번
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: {
            text: '사고번호',
          },
          name: 'acdt_id',
          fieldName: 'acdt_id',
          width: '120',
          styleName: 'link',
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '110',
        },
        {
          header: this.$t('LB00000803'), // 사고발생일
          name: 'acdt_occr_dt',
          fieldName: 'acdt_occr_dt',
          width: '100',
        },
        {
          header: '요일', // 요일
          name: 'day_week',
          fieldName: 'day_week',
          width: '40',
        },
        {
          header: '사고발생시간', // 사고발생시간
          name: 'acdt_occr_hm',
          fieldName: 'acdt_occr_hm',
          width: '80',
        },
        {
          header: this.$t('LB00000806'), // 사고구분
          name: 'acdt_cl_nm',
          fieldName: 'acdt_cl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000809'), // 사고 상세구분
          name: 'acdt_dtl_cl_nm',
          fieldName: 'acdt_dtl_cl_nm',
          width: '160',
        },
        {
          header: this.$t('LB00001279'), // 사고유형
          name: 'acdt_tp_nm',
          fieldName: 'acdt_tp_nm',
          width: '70',
        },
        {
          header: {
            text: '사고발생내용',
          },
          name: 'acdt_occr_txt',
          fieldName: 'acdt_occr_txt',
          width: '320',
          styleName: 'left',
        },
        {
          header: '기인물', // 기인물
          name: 'acdt_caus_tgt_txt',
          fieldName: 'acdt_caus_tgt_txt',
          width: '160',
          styleName: 'left',
        },
        {
          header: {
            text: '산업재해조사표작성여부',
          },
          name: 'inac_ivst_sbms_tgt_yn',
          fieldName: 'inac_ivst_sbms_tgt_yn',
          width: '100',
        },
        {
          header: this.$t('LB00000807'), // 산재여부
          name: 'inac_yn',
          fieldName: 'inac_yn',
          width: '80',
        },
        {
          header: this.$t('LB00001285'), // 사고자 구분
          name: 'dfdt_cl_nm',
          fieldName: 'dfdt_cl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'dfdt_dept_nm',
          fieldName: 'dfdt_dept_nm',
          width: '120',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'dfdt_nm',
          fieldName: 'dfdt_nm',
          width: '70',
        },
        {
          header: '성별', // 성별
          name: 'dfdt_sex_nm',
          fieldName: 'dfdt_sex_nm',
          width: '40',
        },
        {
          header: '연령', // 연령
          name: 'dfdt_age',
          fieldName: 'dfdt_age',
          width: '40',
        },
        {
          header: '근속', // 근속
          name: 'dfdt_los',
          fieldName: 'dfdt_los',
          width: '40',
        },
        {
          header: '휴업기간(일)', // 휴업기간(일)
          name: 'dfdt_care_days',
          fieldName: 'dfdt_care_days',
          width: '100',
        },
        {
          header: this.$t('LB00000810'), // 사고위치
          name: 'acdt_wkpl_loc_nm',
          fieldName: 'acdt_wkpl_loc_nm',
          width: '130',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000811'), // 사고 상세위치
          name: 'acdt_dtl_loc_nm',
          fieldName: 'acdt_dtl_loc_nm',
          width: '150',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'acdt_oprn_txt_cl_nm',
          fieldName: 'acdt_oprn_txt_cl_nm',
          width: '200',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000839'), // 불안전한 행동(시스템적 원인)
          name: 'usft_txt_cl_nm',
          fieldName: 'usft_txt_cl_nm',
          width: '300',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000840'), // 불안전한 상태(설비적 원인)
          name: 'usft_st_nm',
          fieldName: 'usft_st_nm',
          width: '300',
          styleName: 'left',
        },
        {
          header: {
            text: '사고속보발송', // 사고속보발송
          },
          name: 'acdt_news_eml_send_yn',
          fieldName: 'acdt_news_eml_send_yn',
          width: '100',
        },
        {
          header: {
            text: '작성자', // 작성자 부서 + 작성자명
          },
          name: 'writer_department',
          fieldName: 'writer_department',
          width: '150',
        },
        {
          header: this.$t('LB00000812'), // 사고보고 등록일
          name: 'acdt_brie_reg_dt',
          fieldName: 'acdt_brie_reg_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000813'), // 조사보고서 등록일
          name: 'ivst_rprt_reg_dt',
          fieldName: 'ivst_rprt_reg_dt',
          width: '110',
        },
        {
          header: this.$t('LB00000814'), // 개선결과 등록일
          name: 'btrm_rslt_reg_dt',
          fieldName: 'btrm_rslt_reg_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000143'), // 진행상태
          name: 'acdt_prst_nm',
          fieldName: 'acdt_prst_nm',
          width: '100',
        },
        // {
        //   header: this.$t('LB00000700'), // 회사
        //   name: 'cmpy_nm',
        //   fieldName: 'cmpy_nm',
        //   width: '100',
        // },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true, exclusive: true, width: 30, showAll: false })
      gridView.setRowIndicator({ width: 50 })

      gridView.header.height = 50
      gridView.footer.height = 40
      gridView.displayOptions.rowHeight = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'singleRow'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.cellType === 'header') return // 그리드 헤더 클릭은 제외
        if (clickData.itemIndex < 0) return

        grid.checkItem(clickData.itemIndex, true, true)

        if (clickData.column === 'acdt_id') {
          // 사고번호 클릭 시

          const userempno = this.$store.getters['auth/getUser']
          let isAdmin = false
          userempno.roleTypeCds.forEach((i) => {
            if (i === 'M01') {
              // 시스템관리일 경우
              isAdmin = true
            }
          })

          this.schData.acdt_id = grid.getValue(clickData.itemIndex, 'acdt_id')
          this.schData.acdtprstcd = grid.getValue(clickData.itemIndex, 'acdt_prst_cd')
          this.schData.chk_list = this.F_codes.F_00000001_val.join()
          const fscrid = grid.getValue(clickData.itemIndex, 'crt_emp_no')
          this.$router.push({
            name:
              grid.getValue(clickData.itemIndex, 'mode') === 'U' || userempno.emp_no === fscrid || isAdmin
                ? 'acdtmgnt-acdtmgntdtledt-acdtmgntdtledt'
                : 'acdtmgnt-acdtmgntdtllup-acdtmgntdtllup',
            params: {
              mode: grid.getValue(clickData.itemIndex, 'mode'),
              schData: this.schData,
              acdtmgntdtledt: this.schData.acdt_id,
              acdtmgntdtllup: this.schData.acdt_id,
              acdt_prst_cd: this.schData.acdtprstcd,
              admin: isAdmin,
            },
          })
        }
      }.bind(this)

      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields)
      gridView2 = new GridView('excelgrid')
      gridView2.setDataSource(dataProvider)
      // gridView2.setColumns(columns2)
      gridView2.setColumns(columns)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })

      gridView2.header.height = 45
      gridView2.displayOptions.minRowHeight = 35
      gridView2.displayOptions.fitStyle = 'fill'
    },
    async select() {
      this.schData.wkpl_id_list = this.F_codes.F_00000001_val.join()
      const rows = await this.$axios.$get(`/api/v1/she/sfty/acdtmgnt`, { params: this.schData })
      dataProvider.setRows(rows)
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (this.isEmpty(rows[i].acdt_occr_dt) === false) {
          const now = new Date(rows[i].acdt_occr_dt)
          const week = ['일', '월', '화', '수', '목', '금', '토']
          const dayOfWeek = week[now.getDay()]
          gridView.setValue(i, 'day_week', dayOfWeek)
        }
      }

      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.F_codes.F_00000001_val = [user.wkpl_id]
      this.codChgReturn(user.cmpn_cd)
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return data.length > 0 ? n.up_cd === data : true
      })
      this.F_codes.F_00000001_val = []
      this.F_codes.F_00000001.forEach((e) => {
        this.F_codes.F_00000001_val.push(e.cd)
      })
      this.schData.wkpl_chk = false
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    add() {
      this.schData.acdt_id = ''
      this.schData.acdt_prst_cd = ''
      this.$router.push({
        name: `acdtmgnt-acdtmgntdtledt-acdtmgntdtledt`,
        params: {
          schData: this.schData,
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'default'
      const footer = 'hidden'
      // 사고관리
      const fileName = this.$t('LB00000801') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView2.exportGrid({
        type: 'excel',
        target: 'local',
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
    onAcdtReport() {
      const userempno = this.$store.getters['auth/getUser']
      this.tempData.userempno = userempno.emp_no

      if (gridView.getDataRow(gridView.getCheckedItems()) < 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }

      this.frmData.acdt_id = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'acdt_id')

      this.frmData.cmpy_cd = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'cmpy_cd')
      this.frmData.wkpl_id = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'wkpl_id')
      this.frmData.acdt_cl_cd = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'acdt_cl_cd')
      this.frmData.dfdt_sn = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'dfdt_sn') // 사고자 순번 (리포트출력시 하나의 데이터를 추출하기 위해)
      this.frmData.acdt_prst_cd = dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'acdt_prst_cd') // 진행상태에 따라 메일 발송, 리포트출력 버튼 출력 여부 결정
      this.$refs.acdtrptpopup.open(this.frmData)
    },
    onUrlCopy() {
      if (gridView.getDataRow(gridView.getCheckedItems()) < 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }

      // console.log('--> getCheckedItems : ' + gridView.getCheckedItems())
      // console.log('--> getDataRow : ' + gridView.getDataRow(gridView.getCheckedItems()))
      // console.log('--> getValue : ' + dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'acdt_id'))

      let urlCopy = ''
      // eslint-disable-next-line nuxt/no-globals-in-created
      urlCopy = window.location.href
      urlCopy = urlCopy.replace('acdtmgntlist', 'acdtmgntdtllup')
      urlCopy = urlCopy + '/' + dataProvider.getValue(gridView.getDataRow(gridView.getCheckedItems()), 'acdt_id')
      const tempElem = document.createElement('textarea')
      tempElem.value = urlCopy
      document.body.appendChild(tempElem)
      tempElem.select()
      document.execCommand('copy')
      document.body.removeChild(tempElem)
      this.$toast.show('URL 복사가 완료되었습니다.') // URL 복사가 완료되었습니다.
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    toggleAll() {
      this.$nextTick(() => {
        if (this.likesAllwkpl) {
          this.F_codes.F_00000001_val = []
        } else {
          this.F_codes.F_00000001_val = []
          this.F_codes.F_00000001.forEach((e) => {
            this.F_codes.F_00000001_val.push(e.cd)
          })
        }
      })
    },
    toggleChk() {
      const arr = ['S16', 'S22', 'S23', 'S24', 'S25', 'S26', 'S27', 'S28', 'S21']
      if (this.schData.wkpl_chk) {
        const merged = this.F_codes.F_00000001_val.concat(arr)
        this.F_codes.F_00000001_val = merged.filter((e, i) => merged.indexOf(e) === i)
      } else {
        this.F_codes.F_00000001_val = this.F_codes.F_00000001_val.filter((e) => !arr.includes(e))
      }
    },
  },
}
</script>
<style>
.rgsample-blue-column {
  color: blue;
}
.ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
