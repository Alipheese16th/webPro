<template>
  <div>
    <div class="location">
      <!-- Home > 투자계획 관리 > 전사 투자 상세현황 -->
      <span>Home > {{ $t('LB00001151') }} > {{ $t('LB00001181') }}</span>
    </div>
    <div class="work_title">
      <!-- 전사 투자 상세현황 -->
      <h2>{{ $t('LB00001181') }}</h2>
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
                <!--사업장 구분-->
                <div class="label_tit">{{ $t('LB00001178') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_main_bsns_cd"
                  v-model="schData.wkpl_main_bsns_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000015"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
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
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--연도-->
                <div class="label_tit">{{ $t('LB00001174') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      ref="invs_year"
                      v-model="schData.invs_year"
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
                  <v-date-picker
                    ref="picker"
                    v-model="schData.invs_year"
                    reactive
                    show-current
                    min="2010"
                    max="2030"
                    no-title
                    @click:year="saveDate(schData.invs_year)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--카테고리-->
                <div class="label_tit">{{ $t('LB00000934') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="invs_cat_cd"
                  v-model="schData.invs_cat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000014"
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
      <div class="sub_title" style="display: flex">
        <!--실적 목록-->
        <h3>{{ $t('LB00001180') }}</h3>
        <v-spacer></v-spacer>
        <!--단위 : 천원-->
        <div class="label_info ml-1">{{ $t('LB00001144') }}</div>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

let yearVal = new Date()

export default {
  meta: {
    title: '전사 투자 상세현황',
    key(route) {
      return `/stndinfr/iiwcinvsdtlps/${route.params.catalog}`
    },
  },
  components: {},
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      tempData: {},
      menu: false,
      totalcnt: 0,
      codeid: ['ST00000014', 'CO00000009', 'ST00000015'],
      codes: {
        ST00000014: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
        ST00000015: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001181') // 전사 투자 상세현황
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  watch: {
    menu(val) {
      val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  created() {
    const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    yearVal = this.getTzDateObj(user.time_zone)
    this.schData.invs_year = this.get_date_str_gubun(yearVal, '-') + ''

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
        this.codes.ST00000014.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.ST00000015.unshift({ cd: '', cd_nm: 'ALL' })
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
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_main_bsns_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_main_bsns_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_year',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_cat_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_stpl_item_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_cat_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_stpl_item_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_item_amt',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'amt_per',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_tot',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_01',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_02',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_03',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_04',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_05',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_06',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_07',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_08',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_09',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_10',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_11',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt_12',
        dataType: ValueType.NUMBER,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00000700'), // 회사
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '40',
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'left field-empty'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001178'), // 사업장 구분
        name: 'wkpl_main_bsns_nm',
        fieldName: 'wkpl_main_bsns_nm',
        width: '70',
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'left field-empty'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '70',
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'left field-empty'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000934'), // 카테고리
        name: 'invs_cat_nm',
        fieldName: 'invs_cat_nm',
        width: '70',
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001170'), // 약정항목
        name: 'invs_stpl_item_nm',
        fieldName: 'invs_stpl_item_nm',
        width: '70',
        footer: {
          text: '합계',
          groupText: '소계',
        },
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001183'), // 투자금액
        name: 'invs_item_amt',
        fieldName: 'invs_item_amt',
        width: '70',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          groupExpression: 'sum',
          expression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001182'), // 누계
        name: 'prfr_amt_tot',
        fieldName: 'prfr_amt_tot',
        width: '70',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001155'), // 실적(%)
        name: 'amt_per',
        fieldName: 'amt_per',
        width: '70',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001156'), // 1월
        name: 'prfr_amt_01',
        fieldName: 'prfr_amt_01',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001157'), // 2월
        name: 'prfr_amt_02',
        fieldName: 'prfr_amt_02',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001158'), // 3월
        name: 'prfr_amt_03',
        fieldName: 'prfr_amt_03',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001159'), // 4월
        name: 'prfr_amt_04',
        fieldName: 'prfr_amt_04',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001160'), // 5월
        name: 'prfr_amt_05',
        fieldName: 'prfr_amt_05',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001161'), // 6월
        name: 'prfr_amt_06',
        fieldName: 'prfr_amt_06',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001162'), // 7월
        name: 'prfr_amt_07',
        fieldName: 'prfr_amt_07',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001163'), // 8월
        name: 'prfr_amt_08',
        fieldName: 'prfr_amt_08',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001164'), // 9월
        name: 'prfr_amt_09',
        fieldName: 'prfr_amt_09',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001165'), // 10월
        name: 'prfr_amt_10',
        fieldName: 'prfr_amt_10',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001166'), // 11월
        name: 'prfr_amt_11',
        fieldName: 'prfr_amt_11',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001167'), // 12월
        name: 'prfr_amt_12',
        fieldName: 'prfr_amt_12',
        width: '50',
        styleName: 'right',
        numberFormat: '#,##0',
        footer: {
          expression: 'sum',
          groupExpression: 'sum',
          numberFormat: '#,##0',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const catCd = grid.getValue(dataCell.index.itemIndex, 'invs_cat_cd')
          if (catCd === 'zzzzzz') {
            ret.styleName = 'field-empty right'
          }

          return ret
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
    // gridView.setRowGroup({ mergeMode: true, summaryMode: 'aggregate', expandedAdornments: 'both', collapsedAdornments: 'header' })
    // gridView.setRowGroup({ mergeMode: true, summaryMode: 'aggregate', expandedAdornments: 'footer', collapsedAdornments: 'footer' })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'
    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      return sYear
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async select() {
      if (!this.searchValidate()) {
        return
      }

      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/invs-mgnt-tot-list`, { params: this.schData })
      dataProvider.setRows(rows)
      // gridView.groupBy(['cmpy_nm'])
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    searchValidate() {
      if (this.isEmpty(this.schData.invs_year)) {
        this.$toast.show(this.$t('MS00000599'), { className: 'toast_error' }) // 연도를 입력해주세요.
        this.$refs.invs_year.focus()
        return false
      }
      return true
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
        wkpl_id: '',
        invs_cat_cd: '',
        cmpy_cd: '',
        wkpl_main_bsns_cd: '',
        invs_year: this.get_date_str_gubun(yearVal, '-') + '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.schData.invs_year = dd
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
      const fileName = this.$t('LB00001184') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 전사투자상세현황

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
