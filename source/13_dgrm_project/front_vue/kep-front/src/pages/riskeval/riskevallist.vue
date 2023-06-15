<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 위험성평가 등록/조회-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('위험성평가 등록/조회') }}</span>
    </div>
    <div class="work_title">
      <!-- 위험성평가 등록/조회 -->
      <h2>{{ $t('위험성평가 등록/조회') }}</h2>
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
                  @change="chgLevel1(true)"
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
                <!--실시일자-->
                <div class="label_tit">{{ $t('실시일자') }}</div>
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
                  @change="chgLevel2(true)"
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
                  ref="dgsn_apal_prst_cd"
                  v-model="schData.dgsn_apal_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000077"
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
        <!--위험성평가 목록-->
        <h3>{{ $t('위험성평가 목록') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--복사하기-->
          <v-btn color="primary" depressed height="30" @click="copy">{{ $t('복사하기') }}</v-btn>
          <!--프린트-->
          <v-btn color="primary" depressed height="30" @click="reportOutput()">{{ $t('프린트') }}</v-btn>
          <!--결재상신-->
          <v-btn v-if="isAdminGrp" color="primary" depressed height="30" @click="appr">{{ $t('LB00000763') }}</v-btn>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="riskevallist1" mkey2="riskevallist2" @close="popclose" />
    <deptslct ref="deptpop" mkey="riskevallist1" @close="popDeptClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import deptslct from '../common/deptslct'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '위험성평가 등록/조회',
    key(route) {
      return `/riskeval/${route.params.catalog}`
    },
  },
  components: {
    popup,
    deptslct,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CO00000009', 'SH00000077', 'SH00000078'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        SH00000077: [{ cd: '', cd_nm: '' }],
        SH00000078: [{ cd: '', cd_nm: '' }],
      },
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
      isAdminGrp: false,
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
    const user = this.$store.getters['auth/getUser']
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
      if (!this.schData.sDate) {
        this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
        this.schData.eDate = this.getTzDate(user.time_zone, '-')
      }
    } else {
      this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
      this.schData.eDate = this.getTzDate(user.time_zone, '-')
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }

    user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdminGrp = true
      }
    })
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
        this.codes.SH00000077.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000078.unshift({ cd: '', cd_nm: 'ALL' })

        this.createGrid()
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/she/riskeval/wkpl-dngp-list`, {})
      .then((res) => {
        if (this.schData.cmpy_cd === 'undefined' || this.schData.cmpy_cd == null) {
          this.schData = {}
          this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
          this.schData.eDate = this.getTzDate(user.time_zone, '-')
          this.schData.cmpy_cd = user.cmpn_cd
          this.schData.wkpl_id = user.wkpl_id
          this.codChgReturn(this.schData.cmpy_cd)
        }
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
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_id',
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
          fieldName: 'dgsn_apal_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'high_risk_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_drup_stp_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_sgn_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('위험성평가 번호'), // 위험성평가 번호
          name: 'dgsn_apal_id',
          fieldName: 'dgsn_apal_id',
          width: '100',
          styleName: 'link center',
        },
        {
          header: this.$t('평가종류'), // 평가종류
          name: 'dgsn_apal_type_nm',
          fieldName: 'dgsn_apal_type_nm',
          width: '70',
        },
        {
          header: this.$t('실시일자'), // 실시일자
          name: 'dgsn_apal_bgn_dt',
          fieldName: 'dgsn_apal_bgn_dt',
          width: '50',
        },
        {
          header: this.$t('LB00000700'), // 회사
          name: 'cmpy_nm',
          fieldName: 'cmpy_nm',
          width: '70',
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '70',
        },
        {
          header: this.$t('소속부서'), // 소속부서
          name: 'dgsn_org_nm',
          fieldName: 'dgsn_org_nm',
          width: '50',
        },
        {
          header: this.$t('관리감독자'), // 관리감독자
          name: 'dgsn_apal_supe_emp_nm',
          fieldName: 'dgsn_apal_supe_emp_nm',
          width: '50',
        },
        {
          header: this.$t('대분류'), // 대분류
          name: 'lrcl_dngp_nm',
          fieldName: 'lrcl_dngp_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('공정명'), // 공정명
          name: 'mdcl_dngp_nm',
          fieldName: 'mdcl_dngp_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: {
            text: this.$t('고위험 항목\n(8점 이상)'), // 고위험 항목\n(8점 이상)
            styleName: 'multiline-editor',
          },
          name: 'high_risk_cnt',
          fieldName: 'high_risk_cnt',
          width: '70',
        },
        {
          header: this.$t('LB00000143'), // 진행상태
          name: 'dgsn_apal_prst_nm',
          fieldName: 'dgsn_apal_prst_nm',
          width: '50',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true })
      gridView.applyCheckables()

      gridView.header.height = 50
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.cellType === 'gridEmpty') return
        if (clickData.fieldName === 'dgsn_apal_id') {
          let url = ''
          const param = {}
          param.schData = this.schData
          const sdadscCheck = dataProvider.getValue(clickData.dataRow, 'dgsn_apal_prst_cd')
          const id = dataProvider.getValue(clickData.dataRow, 'dgsn_apal_id')
          if ((dataProvider.getValue(clickData.dataRow, 'mode') === 'U' || this.isAdminGrp) && !(sdadscCheck === 'E02' || sdadscCheck === 'E03')) {
            switch (dataProvider.getValue(clickData.dataRow, 'dgsn_apal_drup_stp_cd')) {
              case '0':
                url = 'riskeval-riskevalbasicedt-riskevalbasicedt'
                param.riskevalbasicedt = id
                break
              case '1':
                url = 'riskeval-riskevaltgtdtlwkedt-riskevaltgtdtlwkedt'
                param.riskevaltgtdtlwkedt = id
                break
              case '2':
                url = 'riskeval-riskevalharmdngftedt-riskevalharmdngftedt'
                param.riskevalharmdngftedt = id
                break
              case '3':
                url = 'riskeval-riskevalriskestmtedt-riskevalriskestmtedt'
                param.riskevalriskestmtedt = id
                break
              case '4':
                url = 'riskeval-riskevalriskrdtplanedt-riskevalriskrdtplanedt'
                param.riskevalriskrdtplanedt = id
                break
              case '5':
                url = 'riskeval-riskevalcmpllup-riskevalcmpllup'
                param.riskevalcmpllup = id
                break
            }
          } else {
            switch (dataProvider.getValue(clickData.dataRow, 'dgsn_apal_drup_stp_cd')) {
              case '0':
                url = 'riskeval-riskevalbasiclup-riskevalbasiclup'
                param.riskevalbasiclup = id
                break
              case '1':
                url = 'riskeval-riskevaltgtdtlwklup-riskevaltgtdtlwklup'
                param.riskevaltgtdtlwklup = id
                break
              case '2':
                url = 'riskeval-riskevalharmdngftlup-riskevalharmdngftlup'
                param.riskevalharmdngftlup = id
                break
              case '3':
                url = 'riskeval-riskevalriskestmtlup-riskevalriskestmtlup'
                param.riskevalriskestmtlup = id
                break
              case '4':
                url = 'riskeval-riskevalriskrdtplanlup-riskevalriskrdtplanlup'
                param.riskevalriskrdtplanlup = id
                break
              case '5':
                url = 'riskeval-riskevalcmpllup-riskevalcmpllup'
                param.riskevalcmpllup = id
                break
            }
          }
          this.$router.push({
            name: url,
            params: param,
          })
        }
      }.bind(this)
    },
    async select() {
      if (this.isEmpty(this.schData.dgsn_org_nm)) {
        this.schData.dgsn_org_cd = ''
      }
      if (this.isEmpty(this.schData.dgsn_apal_supe_emp_nm)) {
        this.schData.dgsn_apal_supe_emp_id = ''
      }

      const rows = await this.$axios.$get(`/api/v1/she/riskeval/basic`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      // gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    add() {
      this.$router.push({
        name: `riskeval-riskevalbasicedt-riskevalbasicedt`,
        params: {
          schData: this.schData,
        },
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
    chgLevel1(isSelect) {
      const wkplId = this.schData.wkpl_id
      this.levels.LEVEL1 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.up_dngp_id === ''
      })
      this.levels.LEVEL1.unshift({ dngp_id: '', dngp_nm: 'ALL', up_dngp_id: '' })
      if (isSelect) {
        this.schData.lrcl_dngp_id = ''
      }
      this.chgLevel2(isSelect)
    },
    chgLevel2(isSelect) {
      const wkplId = this.schData.wkpl_id
      const upId = this.schData.lrcl_dngp_id
      this.levels.LEVEL2 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.level === 2 && n.up_dngp_id === upId
      })
      this.levels.LEVEL2.unshift({ dngp_id: '', dngp_nm: 'ALL', up_dngp_id: '' })
      if (isSelect) {
        this.schData.mdcl_dngp_id = ''
      }
    },
    schReset() {
      this.schData = {}
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.schData.sDate = this.getTzDate(user.time_zone, '-', 30)
      this.schData.eDate = this.getTzDate(user.time_zone, '-')
      this.codChgReturn(user.cmpn_cd)
      this.chgLevel1(true)
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
    async appr() {
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('결재할 항목을 선택해주세요.'), { className: 'toast_error' }) // 결재할 항목을 선택해주세요.
        return
      }
      let checkAppr = false
      for (let i = 0; i < checkedRows.length; i++) {
        const sdadscCheck = dataProvider.getValue(checkedRows[i], 'dgsn_apal_prst_cd')
        if (sdadscCheck !== 'E03') {
          checkAppr = true
          break
        }
      }
      if (checkAppr) {
        this.$toast.show(this.$t('선택한 항목 중 결재가 불가능한 항목이 있습니다.'), { className: 'toast_error' }) // 선택한 항목 중 결재가 불가능한 항목이 있습니다.
        return
      }
      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.route = 'list'
      this.frmData.idList = []
      for (let i = 0; i < checkedRows.length; i++) {
        const id = dataProvider.getValue(checkedRows[i], 'dgsn_apal_id')
        this.frmData.idList.push(id)
      }

      this.$axios.post('/api/v1/she/riskeval/riskrdtplan', this.frmData).then((res) => {
        const config = require('../../../nuxt.config.js')
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
          window.open(url, 'riskrdtplanmulti', options)
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
      const fileName = this.$t('위험성평가') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 위험성평가

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
    reportOutput() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()

      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('프린트할 항목을 선택해주세요.'), { className: 'toast_error' }) // 프린트할 항목을 선택해주세요.
        return
      } else if (checkedRows.length !== 1) {
        this.$toast.show(this.$t('프린트는 한개의 데이터만 선택 가능합니다.'), { className: 'toast_error' }) // 프린트는 한개의 데이터만 선택 가능합니다.
        return
      }

      const reportdata = {
        dgsn_apal_id: `'` + gridView.getValue(checkedRows, 'dgsn_apal_id') + `'`,
        reportForm: 'riskEvalList',
      }
      /* 다중 프린트
      const reportdata = {
        reportForm: 'riskEvalList',
        dgsn_apal_id: '',
      }
      let idList = ''
      for (let i = 0; i < checkedRows.length; i++) {
        const id = dataProvider.getValue(checkedRows[i], 'dgsn_apal_id')
        idList = idList + `'` + id + `',`
      }
      reportdata.dgsn_apal_id = idList.slice(0, -1)
      */
      this.$axios.post(`/api/v1/comm/report/riskEvalList`, reportdata).then((res) => {
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
    async copy() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()

      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('복사할 항목을 선택해주세요.'), { className: 'toast_error' }) // 복사할 항목을 선택해주세요.
        return
      } else if (checkedRows.length !== 1) {
        this.$toast.show(this.$t('복사는 한개의 데이터만 선택 가능합니다.'), { className: 'toast_error' }) // 복사는 한개의 데이터만 선택 가능합니다.
        return
      }

      const sdadscCheck = dataProvider.getValue(checkedRows[0], 'dgsn_apal_prst_cd')
      if (sdadscCheck !== 'E03') {
        this.$toast.show(this.$t('복사는 결재완료된 건만 가능합니다.'), { className: 'toast_error' }) // 복사는 결재완료된 건만 가능합니다.
        return
      }

      const res = await this.$confirm(this.$t('복사하시겠습니까?')) // 복사하시겠습니까?
      if (!res) {
        return
      }
      const copydata = {
        wkpl_id: gridView.getValue(checkedRows[0], 'wkpl_id'),
        original_dgsn_apal_id: gridView.getValue(checkedRows[0], 'dgsn_apal_id'),
      }
      this.$axios.post(`/api/v1/she/riskeval/riskEvalCopy`, copydata).then((res) => {
        this.$toast.show(this.$t('복사 되었습니다.')) // 복사 되었습니다.
        this.select()
      })
    },
  },
}
</script>
<style>
.multiline-editor {
  white-space: pre;
}
</style>
