<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 작업허가 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000928') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업허가 관리 -->
      <h2>{{ $t('LB00000928') }}</h2>
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
                <!--작업명-->
                <div class="label_tit">{{ $t('LB00000636') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_nm"
                  v-model="schData.con_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업일자-->
                <div class="label_tit">{{ $t('LB00000952') }}</div>
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
                <!--작업위치-->
                <div class="label_tit">{{ $t('LB00000888') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_oprn_wkpl_loc_nm"
                  v-model="schData.con_oprn_wkpl_loc_nm"
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
                <!--작업부서-->
                <div class="label_tit">{{ $t('LB00000954') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_rspn_org_nm"
                  v-model="schData.con_rspn_org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popDeptOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업 요청자-->
                <div class="label_tit">{{ $t('LB00000864') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_rq_emp_nm"
                  v-model="schData.con_rq_emp_nm"
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
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="con_req_prst_cd"
                  v-model="schData.con_req_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000042"
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
        <!--작업허가 목록-->
        <h3>{{ $t('LB00000968') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--당일작업 작업자 조회-->
          <v-btn color="" outlined height="30" @click="currOprrPop()">{{ $t('LB00000967') }}</v-btn>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="cstprmsmgntlistuserslct1" mkey2="cstprmsmgntlistuserslct2" @close="popclose" />
    <locslct ref="locpop" mkey="cstprmsmgntlist1" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popLocClose" />
    <deptslct ref="deptpop" mkey="cstprmsmgntlist1" @close="popDeptClose" />
    <curroprr ref="curroprrpop" mkey="cstprmsmgntlist1" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import locslct from '../common/rgnslct'
import deptslct from '../common/deptslct'
import curroprr from './cstprmsthdprsnpslist'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '작업허가 관리',
    key(route) {
      return `/cstprms/cstprmsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    locslct,
    deptslct,
    curroprr,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      bizData: {
        emp_no: '',
        cnct_1st_cd: '',
        cnct_2nd_cd: '',
        emp_nm: '',
      },
      tempData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['SH00000042', 'CO00000009'],
      codes: {
        SH00000042: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      deleteModify: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000928') // 작업허가 관리
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
    eDate = this.getTzDateObj(user.time_zone)
    sDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(sDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    this.tempData.username = this.$store.getters['auth/getUsername']
    this.tempData.userempno = user.emp_no
    console.log(user) // eslint-disable-line no-console
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.SH00000042.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
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
    const fields = [
      {
        fieldName: 'con_id',
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
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_prms_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_wkpl_loc_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_rq_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_rq_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'otsr_con_bsen_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'otsr_con_bsen_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_req_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_req_prst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_wkpl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_dtl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_warr_out_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_warr_out_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ntsg_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_warr_out_dttm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000700'), // 회사
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '105',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '110',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000863'), // 작업번호
        name: 'con_id',
        fieldName: 'con_id',
        width: '150',
      },
      {
        header: this.$t('LB00000636'), // 작업명
        name: 'con_nm',
        fieldName: 'con_nm',
        width: '200',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000876'), // 허가번호
        name: 'con_prms_id',
        fieldName: 'con_prms_id',
        width: '150',
      },
      {
        header: this.$t('LB00000880'), // 작업종류
        name: 'con_oprn_type_nm',
        fieldName: 'con_oprn_type_nm',
        width: '130',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000888'), // 작업위치
        name: 'con_oprn_wkpl_loc_nm',
        fieldName: 'con_oprn_wkpl_loc_nm',
        width: '130',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000681'), // 상세위치
        name: 'con_oprn_dtl_loc_nm',
        fieldName: 'con_oprn_dtl_loc_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000878'), // 작업일시
        name: 'con_dt',
        fieldName: 'con_dt',
        width: '200',
      },
      {
        header: this.$t('LB00000959'), // 작업요청자
        name: 'con_rq_emp_nm',
        fieldName: 'con_rq_emp_nm',
        width: '70',
      },
      {
        header: this.$t('LB00000958'), // 협력사
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '110',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000895'), // 작업인원
        name: 'oprr_cnt',
        fieldName: 'oprr_cnt',
        width: '60',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'con_req_prst_nm',
        fieldName: 'con_req_prst_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000969'), // 허가서 출력
        name: 'oprn_warr_out_dttm',
        fieldName: 'oprn_warr_out_dttm',
        width: '100',
      },
      {
        header: this.$t('LB00001348'), // 작업 미실시 사유
        name: 'ntsg_txt',
        fieldName: 'ntsg_txt',
        width: '200',
        styleName: 'left',
        renderer: {
          showTooltip: true,
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

      if (current.column === 'con_nm') {
        const conId = gridView.getValue(index.itemIndex, 'con_id')
        const conPrmsId = gridView.getValue(index.itemIndex, 'con_prms_id')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const statCd = gridView.getValue(index.itemIndex, 'con_req_prst_cd')
        const user = this.$store.getters['auth/getUser']
        // 시스템관리자 그룹 사용자인지 체크
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.deleteModify = true
          }
        })
        if (deptCd === user.dept_cd || this.deleteModify) {
          if (statCd !== 'S02') {
            if (statCd === 'S09') {
              this.$router.push({
                name: `cstprms-cstprmsmgntdtllup-cstprmsmgntdtllup`,
                params: {
                  cstprmsmgntdtllup: conId + '-' + conPrmsId,
                  tmpSchData: {
                    cmpy_cd: this.schData.cmpy_cd,
                    wkpl_id: this.schData.wkpl_id,
                    con_nm: this.schData.con_nm,
                    con_oprn_wkpl_loc_id: this.schData.con_oprn_wkpl_loc_id,
                    con_oprn_wkpl_loc_nm: this.schData.con_oprn_wkpl_loc_nm,
                    con_rspn_org_cd: this.schData.con_rspn_org_cd,
                    con_rspn_org_nm: this.schData.con_rspn_org_nm,
                    con_rq_emp_id: this.schData.con_rq_emp_id,
                    con_rq_emp_nm: this.schData.con_rq_emp_nm,
                    con_req_prst_cd: this.schData.con_req_prst_cd,
                    sDate: this.schData.sDate,
                    eDate: this.schData.eDate,
                  },
                },
              })
            } else {
              this.$router.push({
                name: `cstprms-cstprmsmgntdtledt-cstprmsmgntdtledt`,
                params: {
                  cstprmsmgntdtledt: conId + '-' + conPrmsId,
                  tmpSchData: {
                    cmpy_cd: this.schData.cmpy_cd,
                    wkpl_id: this.schData.wkpl_id,
                    con_nm: this.schData.con_nm,
                    con_oprn_wkpl_loc_id: this.schData.con_oprn_wkpl_loc_id,
                    con_oprn_wkpl_loc_nm: this.schData.con_oprn_wkpl_loc_nm,
                    con_rspn_org_cd: this.schData.con_rspn_org_cd,
                    con_rspn_org_nm: this.schData.con_rspn_org_nm,
                    con_rq_emp_id: this.schData.con_rq_emp_id,
                    con_rq_emp_nm: this.schData.con_rq_emp_nm,
                    con_req_prst_cd: this.schData.con_req_prst_cd,
                    sDate: this.schData.sDate,
                    eDate: this.schData.eDate,
                  },
                },
              })
            }
          } else {
            this.$router.push({
              name: `cstprms-cstprmsmgntdtllup-cstprmsmgntdtllup`,
              params: {
                cstprmsmgntdtllup: conId + '-' + conPrmsId,
                tmpSchData: {
                  cmpy_cd: this.schData.cmpy_cd,
                  wkpl_id: this.schData.wkpl_id,
                  con_nm: this.schData.con_nm,
                  con_oprn_wkpl_loc_id: this.schData.con_oprn_wkpl_loc_id,
                  con_oprn_wkpl_loc_nm: this.schData.con_oprn_wkpl_loc_nm,
                  con_rspn_org_cd: this.schData.con_rspn_org_cd,
                  con_rspn_org_nm: this.schData.con_rspn_org_nm,
                  con_rq_emp_id: this.schData.con_rq_emp_id,
                  con_rq_emp_nm: this.schData.con_rq_emp_nm,
                  con_req_prst_cd: this.schData.con_req_prst_cd,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          }
        } else {
          this.$router.push({
            name: `cstprms-cstprmsmgntdtllup-cstprmsmgntdtllup`,
            params: {
              cstprmsmgntdtllup: conId + '-' + conPrmsId,
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                con_nm: this.schData.con_nm,
                con_oprn_wkpl_loc_id: this.schData.con_oprn_wkpl_loc_id,
                con_oprn_wkpl_loc_nm: this.schData.con_oprn_wkpl_loc_nm,
                con_rspn_org_cd: this.schData.con_rspn_org_cd,
                con_rspn_org_nm: this.schData.con_rspn_org_nm,
                con_rq_emp_id: this.schData.con_rq_emp_id,
                con_rq_emp_nm: this.schData.con_rq_emp_nm,
                con_req_prst_cd: this.schData.con_req_prst_cd,
                sDate: this.schData.sDate,
                eDate: this.schData.eDate,
              },
            },
          })
        }
      }
    }.bind(this)
  },
  methods: {
    openNew() {
      this.$router.push({
        name: `cstprms-cstprmsmgntdtledt-cstprmsmgntdtledt`,
        params: {
          cstprmsmgntdtledt: null,
          tmpSchData: {
            cmpy_cd: this.schData.cmpy_cd,
            wkpl_id: this.schData.wkpl_id,
            con_nm: this.schData.con_nm,
            con_oprn_wkpl_loc_id: this.schData.con_oprn_wkpl_loc_id,
            con_oprn_wkpl_loc_nm: this.schData.con_oprn_wkpl_loc_nm,
            con_rspn_org_cd: this.schData.con_rspn_org_cd,
            con_rspn_org_nm: this.schData.con_rspn_org_nm,
            con_rq_emp_id: this.schData.con_rq_emp_id,
            con_rq_emp_nm: this.schData.con_rq_emp_nm,
            con_req_prst_cd: this.schData.con_req_prst_cd,
            sDate: this.schData.sDate,
            eDate: this.schData.eDate,
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
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-list`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    schReset() {
      this.schData = {
        cmpy_cd: '',
        wkpl_id: '',
        con_nm: '',
        con_oprn_wkpl_loc_id: '',
        con_oprn_wkpl_loc_nm: '',
        con_rspn_org_cd: '',
        con_rspn_org_nm: '',
        con_rq_emp_id: '',
        con_rq_emp_nm: '',
        con_req_prst_cd: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'con_rq_emp_nm', i.emp_nm)
          this.$set(this.schData, 'con_rq_emp_id', i.emp_no)
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
        this.$set(this.schData, 'con_oprn_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'con_oprn_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    popDeptOpen() {
      this.$refs.deptpop.open()
    },
    popDeptClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'con_rspn_org_nm', returnData[0].dept_nm)
        this.$set(this.schData, 'con_rspn_org_cd', returnData[0].dept_cd)
      }
    },
    currOprrPop() {
      this.$refs.curroprrpop.open()
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000971') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 작업허가관리

      gridView.exportGrid({
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
  },
}
</script>
<style lang="scss" scoped></style>
