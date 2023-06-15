<template>
  <div>
    <div class="location">
      <!-- 시스템관리  -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000255') }}</span>
    </div>
    <!-- 배치 이력 조회 -->
    <div class="work_title">
      <h2>{{ $t('LB00000255') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 배치 -->
                <div class="label_tit">{{ $t('LB00000261') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchBatchDatas.cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000011"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 날짜 -->
                <div class="label_tit">{{ $t('LB00000247') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchHistoryDatas.sDate"
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
                  <v-date-picker v-model="searchHistoryDatas.sDate" :max="searchHistoryDatas.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchHistoryDatas.eDate"
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
                  <v-date-picker v-model="searchHistoryDatas.eDate" :min="searchHistoryDatas.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!-- 수행결과 -->
                <div class="label_tit">{{ $t('LB00000257') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchHistoryDatas.bat_rslt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000014"
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
            <v-btn color="" outlined height="36" @click="schReset()">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="clearSch()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>

    <div class="realgrid_container">
      <v-row>
        <v-col cols="5">
          <div class="realgrid_container">
            <div class="sub_title" style="display: flex">
              <h3>배치 목록</h3>
              <v-spacer></v-spacer>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="7 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title" style="display: flex">
              <h3>배치 수행 이력</h3>
              <v-spacer></v-spacer>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <popup ref="pop" @close="close" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './bathislistpopup'

let batchListGridView = GridView
let historyGridView = GridView
let batchListDataProvider = LocalDataProvider
let historyDataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '배치 수행 이력',
  },
  components: {
    popup,
  },
  data() {
    return {
      searchBatchDatas: {},
      searchHistoryDatas: {},

      frmData: {},
      codeid: ['CO00000011', 'CO00000014'],
      codes: {
        CO00000011: [{ cd: '', cd_nm: '' }],
        CO00000014: [{ cd: '', cd_nm: '' }],
      },
      selCO00000014: {
        cd: [],
        cd_nm: [],
      },
      menu1: false,
      menu2: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
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
    sDate.setDate(sDate.getDate() - 2)
    this.searchHistoryDatas.sDate = this.get_date_str_gubun(sDate, '-')
    this.searchHistoryDatas.eDate = this.get_date_str_gubun(eDate, '-')
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      res.CO00000014.forEach((i) => {
        this.selCO00000014.cd.push(i.cd)
        this.selCO00000014.cd_nm.push(i.cd_nm)
      })

      this.codes.CO00000011.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CO00000014.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })

      // 배치목록 데이터프로바이더 셋팅
      const batchListFields = [
        {
          fieldName: 'cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bat_button',
          dataType: ValueType.TEXT,
        },
      ]

      // 배치목록 그리드 셋팅
      const batchListColumns = [
        {
          header: this.$t('LB00000259'), // 배치로그코드
          name: 'bat_exe_log_no',
          fieldName: 'bat_exe_log_no',
          width: '0',
        },
        {
          header: this.$t('LB00000260'), // 배치코드
          name: 'cd',
          fieldName: 'cd',
          width: '50',
          editable: false,
        },
        {
          header: this.$t('LB00000261'), // 배치명
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
          styleName: 'left',
          editable: false,
        },
        {
          header: '수동실행', // 수동실행
          name: 'bat_button',
          fieldName: 'bat_button',
          width: '50',
          sortable: false,
          renderer: {
            type: 'button',
          },
          editable: false,
        },
      ]

      // 수행이력 데이터프로바이더 셋팅
      const historyField = [
        {
          fieldName: 'bat_exe_log_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bat_cd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bat_st_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bat_ed_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bat_rslt_cd',
          dataType: ValueType.TEXT,
        },
      ]

      // 수행이력 그리드 셋팅
      const historyColumns = [
        {
          header: this.$t('LB00000259'), // 배치로그코드
          name: 'bat_exe_log_no',
          fieldName: 'bat_exe_log_no',
          width: '0',
        },
        {
          header: this.$t('LB00000261'), // 배치명
          name: 'bat_cd_nm',
          fieldName: 'bat_cd_nm',
          width: '300',
          styleName: 'left',
          editable: false,
        },
        {
          header: this.$t('LB00000262'), // 시작일시
          name: 'bat_st_dt',
          fieldName: 'bat_st_dt',
          width: '90',
          editable: false,
        },
        {
          header: this.$t('LB00000263'), // 종료일시
          name: 'bat_ed_dt',
          fieldName: 'bat_ed_dt',
          width: '90',
          editable: false,
        },
        {
          header: this.$t('LB00000257'), // 수행결과
          name: 'bat_rslt_cd',
          fieldName: 'bat_rslt_cd',
          lookupDisplay: true,
          values: this.selCO00000014.cd,
          labels: this.selCO00000014.cd_nm,
          width: '50',
          editable: false,
        },
        {
          header: this.$t('LB00000113'), // 내용
          name: 'bat_rslt_cntn',
          fieldName: 'bat_rslt_cntn',
          type: 'button',
          styleName: 'btn_sch_pop',
          width: '50',
          editable: false,
        },
      ]

      batchListDataProvider = new LocalDataProvider(false)
      batchListDataProvider.setFields(batchListFields)
      batchListGridView = new GridView('realgrid')
      batchListGridView.setDataSource(batchListDataProvider)
      batchListGridView.setColumns(batchListColumns)
      batchListGridView.setFooters({ visible: false })

      batchListGridView.setStateBar({ visible: false })
      batchListGridView.setCheckBar({ visible: false, width: 50 })
      batchListGridView.setRowIndicator({ width: 30 })
      batchListGridView.editOptions.editable = true
      batchListGridView.displayOptions.selectionStyle = 'singleRow'

      batchListGridView.header.height = 60
      batchListGridView.displayOptions.rowHeight = 40
      batchListGridView.footer.height = 40
      batchListGridView.displayOptions.fitStyle = 'fill'
      batchListGridView.setColumnProperty('bat_exe_log_no', 'visible', false)

      batchListGridView.onCellItemClicked = function (grid, index, clickData) {
        historyGridView.commit()

        const value = batchListGridView.getValue(index.itemIndex, 'cd')
        this.exec(value)
        return true
      }.bind(this)

      batchListGridView.onCellClicked = async function (grid, index, clickData) {
        historyGridView.commit()
        // 배치 내용 상세 조회
        if (index.cellType === 'gridEmpty') return // 그리드 빈공간 클릭은 제외
        if (index.cellType === 'header') return // 그리드 헤더 클릭은 제외
        if (index.itemIndex < 0) return
        const value = batchListGridView.getValue(index.itemIndex, 'cd')
        this.searchHistoryDatas.bat_cd = value
        const rows = await this.$axios.$get(`/api/v1/common/chems/bat-his`, { params: this.searchHistoryDatas })
        historyDataProvider.setRows(rows)
        historyGridView.refresh()
        historyGridView.setTopItem(0)
      }.bind(this)

      const headergroup = [
        'bat_cd_nm',
        {
          name: 'executionTime',
          direction: 'horizontal',
          items: ['bat_st_dt', 'bat_ed_dt'],
          header: {
            text: '수행시간',
          },
        },
        'bat_rslt_cd', // 수행결과
        'bat_rslt_cntn', // 내용
      ]

      historyDataProvider = new LocalDataProvider(false)
      historyDataProvider.setFields(historyField)
      historyGridView = new GridView('realgrid2')
      historyGridView.setDataSource(historyDataProvider)
      historyGridView.setColumns(historyColumns)
      historyGridView.setFooters({ visible: false })
      historyGridView.setColumnLayout(headergroup)

      historyGridView.setStateBar({ visible: false })
      historyGridView.setCheckBar({ visible: false, width: 50 })
      historyGridView.setRowIndicator({ width: 30 })
      historyGridView.editOptions.editable = true
      historyGridView.displayOptions.selectionStyle = 'singleRow'

      historyGridView.header.height = 60
      historyGridView.displayOptions.rowHeight = 40
      historyGridView.footer.height = 40
      historyGridView.displayOptions.fitStyle = 'fill'

      historyGridView.setColumnProperty('bat_exe_log_no', 'visible', false)
      historyGridView.onCellClicked = async function (grid, index, clickData) {
        // 수행이력 내용 상세 조회
        const current = historyGridView.getCurrent()
        if (current.itemIndex < 0) return
        if (current.column !== 'bat_rslt_cntn') return
        const value = historyGridView.getValue(current.dataRow, 'bat_exe_log_no')
        const data = await this.$axios.$get(`/api/v1/common/chems/bat-his/${value}`)
        this.frmData.bat_rslt_cntn = data
        const detail = true
        const frmData = this.frmData
        this.$refs.pop.open(detail, frmData)
      }.bind(this)

      this.clearSch()
    })
  },
  methods: {
    async clearSch() {
      batchListDataProvider.clearRows()
      historyDataProvider.clearRows()

      // 배치이력 조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/bat-list`, { params: this.searchBatchDatas })
      rows.unshift({
        bat_button: '',
        buf_1st_cntn: '',
        buf_2nd_cntn: '',
        buf_3rd_cntn: '',
        buf_4th_cntn: '',
        buf_5th_cntn: '',
        cd: 'ALL',
        cd_ab: '',
        cd_grp_no: '',
        cd_nm: '',
        row_num: 1,
        sort_seq: 1,
        up_cd: '',
        up_cd_ab: '',
        up_cd_grp_no: '',
        up_cd_nm: '',
      })
      batchListDataProvider.setRows(rows)
      batchListGridView.refresh()
      batchListGridView.setTopItem(0)
      batchListGridView.setCurrent(0, 'cd')

      const batchCurrent = batchListGridView.getCurrent()
      if (batchCurrent.itemIndex < 0) return
      const value = batchListGridView.getValue(batchCurrent.dataRow, 'cd')
      this.searchHistoryDatas.bat_cd = value

      batchListGridView.layoutByColumn('cd').spanCallback = function (grid, layout, itemIndex) {
        const value = grid.getValue(itemIndex, 'cd')
        if (value === 'ALL') {
          return 3
        }
        return 1
      }

      // 배치수행이력 조회
      const batHisRows = await this.$axios.$get(`/api/v1/common/chems/bat-his`, { params: this.searchHistoryDatas })
      historyDataProvider.setRows(batHisRows)
      historyGridView.refresh()
      historyGridView.setTopItem(0)
    },
    async exec(param) {
      const res = await this.$confirm('실행하시겠습니까?')
      if (!res) {
        return
      }

      const result = await this.$axios.$get(`/api/v1/batch/exec/${param}`)

      if (result === 'Success') {
        this.$toast.show('실행 완료되었습니다.')
      } else {
        this.$toast.show('오류가 발생했습니다.', { className: 'toast_error' })
      }

      // 배치수행이력 조회
      const batHisRows = await this.$axios.$get(`/api/v1/common/chems/bat-his`, { params: this.searchHistoryDatas })
      historyDataProvider.setRows(batHisRows)
      historyGridView.refresh()
      historyGridView.setTopItem(0)
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    schReset() {
      this.searchBatchDatas = {
        cd: '',
        bat_cd: '',
        bat_rslt_cd: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }

      this.searchHistoryDatas = {
        cd: '',
        bat_cd: '',
        bat_rslt_cd: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
    },
    close() {},
  },
}
</script>
<style lang="scss" scoped></style>
