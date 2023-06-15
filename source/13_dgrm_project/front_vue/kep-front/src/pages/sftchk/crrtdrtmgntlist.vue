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
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검구분-->
                <div class="label_tit">{{ $t('LB00000669') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="crin_cl_cd"
                  v-model="schData.crin_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000053"
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
                <!--점검일자-->
                <div class="label_tit">{{ $t('LB00000917') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.crin_chk_st_dt"
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
                  <v-date-picker v-model="schData.crin_chk_st_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.crin_chk_ed_dt"
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
                  <v-date-picker v-model="schData.crin_chk_ed_dt" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--점검위치-->
                <div class="label_tit">{{ $t('LB00000739') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="crin_chk_wkpl_loc_nm"
                  v-model="schData.crin_chk_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popLocOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--담당자-->
                <div class="label_tit">{{ $t('LB00000101') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!--점검 담당자-->
                <v-select
                  v-model="schData.crin_chk_gb"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  style="max-width: 110px"
                ></v-select>
                <v-text-field
                  ref="crin_chk_emp_nm"
                  v-model="schData.crin_chk_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="crin_prst_cd"
                  v-model="schData.crin_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000060"
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
        <!--시정지시서 목록-->
        <h3>{{ $t('LB00001089') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="crrtdrtmgntlistuserslct1" mkey2="crrtdrtmgntlistuserslct2" @close="popclose" />
    <locslct ref="locpop" mkey1="crrtdrtmgntlist1" mkey2="crrtdrtmgntlist2" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popLocClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import locslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

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
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {
        crin_chk_gb: 'supe',
        cmpy_cd: '',
      },
      frmData: {},
      tempData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      use_yn_cd: [
        { cd: 'supe', cd_nm: '점검 담당자' },
        { cd: 'actn', cd_nm: '조치 담당자' },
      ],
      codeid: ['SH00000060', 'ST00000002', 'SH00000053', 'CO00000009'],
      codes: {
        SH00000060: [{ cd: '', cd_nm: '' }],
        ST00000002: [{ cd: '', cd_nm: '' }],
        SH00000053: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
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
    const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    date = this.getTzDateObj(user.time_zone)

    // 첫날 마지막 날로 초기화
    const firstDay = new Date(date.getFullYear(), date.getMonth(), 1)
    const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)

    this.schData.crin_chk_st_dt = this.get_date_str_gubun(firstDay, '-')
    this.schData.crin_chk_ed_dt = this.get_date_str_gubun(lastDay, '-')

    this.tempData.username = this.$store.getters['auth/getUsername']
    this.tempData.userempno = user.emp_no

    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
  },
  mounted() {
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
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.SH00000060.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000053.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'crin_id',
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
        fieldName: 'crin_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_wkpl_loc_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_wkpl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_dtl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_risk_tp_cd_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_risk_tp_cd_2_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_dttm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'actn_hope_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'acco_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_supe_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_supe_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_actn_schd_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_actn_schd_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_prst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_chk_tgt_nm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000700'), // 회사
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000669'), // 점검구분
        name: 'crin_cl_nm',
        fieldName: 'crin_cl_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000640'), // 점검명
        name: 'crin_chk_tgt_nm',
        fieldName: 'crin_chk_tgt_nm',
        width: '200',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000739'), // 점검위치
        name: 'crin_chk_wkpl_loc_nm',
        fieldName: 'crin_chk_wkpl_loc_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000681'), // 상세위치
        name: 'crin_chk_dtl_loc_nm',
        fieldName: 'crin_chk_dtl_loc_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000672'), // 위험유형
        name: 'crin_risk_tp_cd_nm',
        fieldName: 'crin_risk_tp_cd_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000672') + '2', // 위험유형2
        name: 'crin_risk_tp_cd_2_nm',
        fieldName: 'crin_risk_tp_cd_2_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000648'), // 점검 일시
        name: 'crin_chk_dttm',
        fieldName: 'crin_chk_dttm',
        width: '200',
      },
      {
        header: this.$t('LB00001027'), // 조치 희망일
        name: 'actn_hope_dt',
        fieldName: 'actn_hope_dt',
        width: '100',
      },
      {
        header: this.$t('LB00001020'), // 조치 완료일
        name: 'acco_dt',
        fieldName: 'acco_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000679'), // 점검 담당자
        name: 'crin_chk_supe_emp_nm',
        fieldName: 'crin_chk_supe_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000673'), // 조치 담당자
        name: 'crin_actn_schd_emp_nm',
        fieldName: 'crin_actn_schd_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'crin_prst_nm',
        fieldName: 'crin_prst_nm',
        width: '100',
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()

      if (current.column === 'crin_chk_tgt_nm') {
        const crinId = gridView.getValue(index.itemIndex, 'crin_id')
        /*
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const statCd = gridView.getValue(index.itemIndex, 'crin_prst_cd')
        const user = this.$store.getters['auth/getUser']
        
        if (deptCd === user.dept_cd) {
          if (statCd !== '30') {
            this.$router.push({
              name: `sftchk-crrtdrtmgntdtledt-crrtdrtmgntdtledt`,
              params: {
                crrtdrtmgntdtledt: crinId,
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  crin_cl_cd: this.schData.crin_cl_cd,
                  crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
                  crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
                  crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
                  crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
                  crin_chk_gb: this.schData.crin_chk_gb,
                  crin_chk_emp_id: this.schData.crin_chk_emp_id,
                  crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
                  crin_prst_cd: this.schData.crin_prst_cd,
                  crin_chk_st_dt: this.schData.crin_chk_st_dt,
                  crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
                  cmpy_cd: this.schData.cmpy_cd,
                },
              },
            })
          } else {
            this.$router.push({
              name: `sftchk-crrtdrtmgntdtllup-crrtdrtmgntdtllup`,
              params: {
                crrtdrtmgntdtllup: crinId,
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  crin_cl_cd: this.schData.crin_cl_cd,
                  crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
                  crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
                  crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
                  crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
                  crin_chk_gb: this.schData.crin_chk_gb,
                  crin_chk_emp_id: this.schData.crin_chk_emp_id,
                  crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
                  crin_prst_cd: this.schData.crin_prst_cd,
                  crin_chk_st_dt: this.schData.crin_chk_st_dt,
                  crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
                  cmpy_cd: this.schData.cmpy_cd,
                },
              },
            })
          }
        } else {
          this.$router.push({
            name: `sftchk-crrtdrtmgntdtllup-crrtdrtmgntdtllup`,
            params: {
              crrtdrtmgntdtllup: crinId,
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                crin_cl_cd: this.schData.crin_cl_cd,
                crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
                crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
                crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
                crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
                crin_chk_gb: this.schData.crin_chk_gb,
                crin_chk_emp_id: this.schData.crin_chk_emp_id,
                crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
                crin_prst_cd: this.schData.crin_prst_cd,
                crin_chk_st_dt: this.schData.crin_chk_st_dt,
                crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
                cmpy_cd: this.schData.cmpy_cd,
              },
            },
          })
        }
        */
        this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'CRIN', crin_id: crinId } }).then((res) => {
          // eslint-disable-next-line no-console
          if (res === 'Success') {
            this.$router.push({
              name: `sftchk-crrtdrtmgntdtledt-crrtdrtmgntdtledt`,
              params: {
                crrtdrtmgntdtledt: crinId,
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  crin_cl_cd: this.schData.crin_cl_cd,
                  crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
                  crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
                  crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
                  crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
                  crin_chk_gb: this.schData.crin_chk_gb,
                  crin_chk_emp_id: this.schData.crin_chk_emp_id,
                  crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
                  crin_prst_cd: this.schData.crin_prst_cd,
                  crin_chk_st_dt: this.schData.crin_chk_st_dt,
                  crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
                  cmpy_cd: this.schData.cmpy_cd,
                },
              },
            })
          } else {
            this.$router.push({
              name: `sftchk-crrtdrtmgntdtllup-crrtdrtmgntdtllup`,
              params: {
                crrtdrtmgntdtllup: crinId,
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  crin_cl_cd: this.schData.crin_cl_cd,
                  crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
                  crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
                  crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
                  crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
                  crin_chk_gb: this.schData.crin_chk_gb,
                  crin_chk_emp_id: this.schData.crin_chk_emp_id,
                  crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
                  crin_prst_cd: this.schData.crin_prst_cd,
                  crin_chk_st_dt: this.schData.crin_chk_st_dt,
                  crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
                  cmpy_cd: this.schData.cmpy_cd,
                },
              },
            })
          }
        })
      }
    }.bind(this)
  },
  methods: {
    openNew() {
      this.$router.push({
        name: `sftchk-crrtdrtmgntdtledt-crrtdrtmgntdtledt`,
        params: {
          name: `sftchk-crrtdrtmgntdtledt-crrtdrtmgntdtledt`,
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
            crin_cl_cd: this.schData.crin_cl_cd,
            crin_chk_wkpl_loc_id: this.schData.crin_chk_wkpl_loc_id,
            crin_chk_wkpl_loc_nm: this.schData.crin_chk_wkpl_loc_nm,
            crin_chk_supe_emp_id: this.schData.crin_chk_supe_emp_id,
            crin_chk_supe_emp_nm: this.schData.crin_chk_supe_emp_nm,
            crin_chk_gb: this.schData.crin_chk_gb,
            crin_chk_emp_id: this.schData.crin_chk_emp_id,
            crin_chk_emp_nm: this.schData.crin_chk_emp_nm,
            crin_prst_cd: this.schData.crin_prst_cd,
            crin_chk_st_dt: this.schData.crin_chk_st_dt,
            crin_chk_ed_dt: this.schData.crin_chk_ed_dt,
            cmpy_cd: this.schData.cmpy_cd,
          },
        },
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      return true
    },
    async select() {
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/crin-list`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    schReset() {
      this.schData = {
        wkpl_id: '',
        crin_cl_cd: '',
        crin_chk_wkpl_loc_id: '',
        crin_chk_wkpl_loc_nm: '',
        crin_chk_supe_emp_id: '',
        crin_chk_supe_emp_nm: '',
        crin_chk_gb: 'supe',
        crin_chk_emp_id: '',
        crin_chk_emp_nm: '',
        crin_prst_cd: '',
        crin_chk_st_dt: '',
        crin_chk_ed_dt: '',
        cmpy_cd: '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)

      date = this.getTzDateObj(user.time_zone)

      // 첫날 마지막 날로 초기화
      const firstDay = new Date(date.getFullYear(), date.getMonth(), 1)
      const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)

      this.schData.crin_chk_st_dt = this.get_date_str_gubun(firstDay, '-')
      this.schData.crin_chk_ed_dt = this.get_date_str_gubun(lastDay, '-')
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
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'crin_chk_emp_nm', i.emp_nm)
          this.$set(this.schData, 'crin_chk_emp_id', i.emp_no)
        })
      }
    },
    popLocOpen() {
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.locpop.open(this.schData, false)
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'crin_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'crin_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001098') + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
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
<style lang="scss" scoped></style>
