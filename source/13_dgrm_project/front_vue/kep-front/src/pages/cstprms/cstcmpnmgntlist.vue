<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 작업완료 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000861') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업완료 관리 -->
      <h2>{{ $t('LB00000861') }}</h2>
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
        <!--작업완료 목록-->
        <h3>{{ $t('LB00000955') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 결재상신 -->
          <v-btn depressed height="30" @click="apprTot()">{{ $t('LB00000763') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="cstcmpnmgntlistuserslct1" mkey2="cstcmpnmgntlistuserslct2" @close="popclose" />
    <locslct ref="locpop" mkey="cstcmpnmgntlist1" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popLocClose" />
    <deptslct ref="deptpop" mkey="cstcmpnmgntlist1" @close="popDeptClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import locslct from '../common/rgnslct'
import deptslct from '../common/deptslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '작업완료 관리',
    key(route) {
      return `/cstprms/cstcmpnmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    locslct,
    deptslct,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      isSysAdmin: false,
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
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000861') // 작업완료 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(this.user.time_zone)
    sDate = this.getTzDateObj(this.user.time_zone)
    sDate.setDate(sDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    this.tempData.username = this.$store.getters['auth/getUsername']
    this.tempData.userempno = this.user.emp_no
    console.log(this.user) // eslint-disable-line no-console
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.cmpy_cd = this.user.cmpn_cd
      this.schData.wkpl_id = this.user.wkpl_id
    }
  },
  mounted() {
    // 공통코드 조회
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

    // 데이터프로바이더 셋팅
    const fields = [
      {
        fieldName: '_chk',
        dataType: ValueType.BOOLEAN,
      },
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
        fieldName: 'oprn_warr_out_dttm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'print_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_req_aprv_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_cmpn_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_cmpn_prst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfl_yn',
        dataType: ValueType.TEXT,
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)

    // 그리드 셋팅
    const columns = [
      {
        header: ' ',
        name: '_chk',
        fieldName: '_chk',
        type: 'data',
        width: '30',
        editable: false,
        styleCallback(grid, dataCell) {
          const prstCd = grid.getValue(dataCell.index.itemIndex, 'con_cmpn_prst_cd')
          const ret = {}

          // 작성중일때만 체크박스 활성화
          if (prstCd === 'S01') {
            ret.renderer = {
              type: 'check',
            }
          } else {
            ret.editor = {
              type: 'text',
            }
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000700'), // 회사
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000863'), // 작업번호
        name: 'con_id',
        fieldName: 'con_id',
        width: '150',
        editable: false,
      },
      {
        header: this.$t('LB00000636'), // 작업명
        name: 'con_nm',
        fieldName: 'con_nm',
        width: '200',
        editable: false,
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000876'), // 허가번호
        name: 'con_prms_id',
        fieldName: 'con_prms_id',
        width: '150',
        editable: false,
      },
      {
        header: this.$t('LB00000880'), // 작업종류
        name: 'con_oprn_type_nm',
        fieldName: 'con_oprn_type_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000888'), // 작업위치
        name: 'con_oprn_wkpl_loc_nm',
        fieldName: 'con_oprn_wkpl_loc_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000681'), // 상세위치
        name: 'con_oprn_dtl_loc_nm',
        fieldName: 'con_oprn_dtl_loc_nm',
        width: '200',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000878'), // 작업일시
        name: 'con_dt',
        fieldName: 'con_dt',
        width: '200',
        editable: false,
      },
      {
        header: this.$t('LB00000959'), // 작업요청자
        name: 'con_rq_emp_nm',
        fieldName: 'con_rq_emp_nm',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000958'), // 협력사
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000895'), // 작업인원
        name: 'oprr_cnt',
        fieldName: 'oprr_cnt',
        width: '100',
        editable: false,
        styleName: 'right',
      },
      {
        header: this.$t('LB00000957'), // 허가서 첨부여부
        name: 'print_yn',
        fieldName: 'print_yn',
        width: '80',
        editable: false,
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'con_cmpn_prst_nm',
        fieldName: 'con_cmpn_prst_nm',
        width: '100',
        editable: false,
      },
    ]
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = true
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.header.height = 39
    gridView.footer.height = 40

    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()

      if (current.column === 'con_nm') {
        const conId = gridView.getValue(index.itemIndex, 'con_id')
        const conPrmsId = gridView.getValue(index.itemIndex, 'con_prms_id')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const statCd = gridView.getValue(index.itemIndex, 'con_req_prst_cd')
        const cmplStatCd = gridView.getValue(index.itemIndex, 'con_cmpn_prst_cd')
        const user = this.$store.getters['auth/getUser']
        // 시스템관리자 그룹 사용자인지 체크
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isSysAdmin = true
          }
        })

        if (deptCd === user.dept_cd || this.isSysAdmin === true) {
          if (statCd !== 'S02' && cmplStatCd === 'S01') {
            this.$router.push({
              name: `cstprms-cstcmpnmgntdtledt-cstcmpnmgntdtledt`,
              params: {
                cstcmpnmgntdtledt: conId + '-' + conPrmsId,
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
              name: `cstprms-cstcmpnmgntdtllup-cstcmpnmgntdtllup`,
              params: {
                cstcmpnmgntdtllup: conId + '-' + conPrmsId,
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
            name: `cstprms-cstcmpnmgntdtllup-cstcmpnmgntdtllup`,
            params: {
              cstcmpnmgntdtllup: conId + '-' + conPrmsId,
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
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-cmpl-list`, { params: this.schData })
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
      this.schData.wkpl_id = this.user.wkpl_id
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
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    async apprTot() {
      gridView.commit()

      const keyVal = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, '_chk')) {
          keyVal.push(dataProvider.getValue(i, 'con_prms_id'))
        }
      }

      if (keyVal.length > 0) {
        console.log(keyVal) // eslint-disable-line no-console
      } else {
        this.$toast.show(this.$t('MS00000296'), { className: 'toast_error' }) // 대상을 선택하세요.
        return
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      const infoRow = []
      const temp = {}
      temp.con_prms_ids = keyVal
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow
      console.log(infoRow) // eslint-disable-line no-console

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-cmpl-multi-appr', this.frmData).then((res) => {
        const config = require('../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empno
          const url = config.default.approvalUrl1 + aprvno + empno
          window.open(url, 'con-oprn-prms-cmpl-dtl-appr', options)
          this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
