<template>
  <div>
    <div class="location">
      <!-- Home > 작업환경측정 > 작업환경측정 조치결과 관리 -->
      <span>Home > {{ $t('LB00000960') }} > {{ $t('LB00001010') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업환경측정 조치결과 관리 -->
      <h2>{{ $t('LB00001010') }}</h2>
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
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wem_acrq_emp_nm"
                  v-model="schData.wem_acrq_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="acrq_wkpl_loc_nm"
                  v-model="schData.acrq_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen2"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
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
                  <v-date-picker v-model="schData.sDate" no-title @input="menu1 = false"> </v-date-picker>
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
                  <v-date-picker v-model="schData.eDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.acco_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000047"
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
        <!--작업환경측정 초치 목록-->
        <h3>{{ $t('LB00001026') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <userslct ref="pop" mkey1="opemrsltedt1" mkey2="opemrsltedt2" @close="popClose" />
    <rgnslct ref="pop2" :iswkpl="false" :wkplid="schData.wkpl_id" mkey="acdtedt" @close="popClose2" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../common/userslct'
import rgnslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '작업환경측정 조치결과 관리',
    key(route) {
      return `/opemactn/${route.params.catalog}`
    },
  },
  components: {
    userslct,
    rgnslct,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000047', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000047: [{ cd: '', cd_nm: '' }], // 진행상태
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
      return this.$t('LB00001010') // 작업환경측정 조치결과 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
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
    } else {
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
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
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000047.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })

        this.createGrid()
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'wem_actn_id',
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
          fieldName: 'acrq_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acrq_wkpl_loc_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acrq_dtl_loc_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acrq_dttm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acrq_hope_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acco_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_acrq_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acco_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acco_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acco_aprv_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
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
          header: this.$t('LB00001014'), // 작업환경측정 명
          name: 'acrq_nm',
          fieldName: 'acrq_nm',
          width: '200',
          styleName: 'link left',
        },
        {
          header: this.$t('LB00000644'), // 위치
          name: 'acrq_wkpl_loc_nm',
          fieldName: 'acrq_wkpl_loc_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000681'), // 상세위치
          name: 'acrq_dtl_loc_nm',
          fieldName: 'acrq_dtl_loc_nm',
          width: '200',
        },
        {
          header: this.$t('LB00001015'), // 등록일시
          name: 'acrq_dttm',
          fieldName: 'acrq_dttm',
          width: '100',
        },
        {
          header: this.$t('LB00001027'), // 조치 희망일
          name: 'acrq_hope_dt',
          fieldName: 'acrq_hope_dt',
          width: '70',
        },
        {
          header: this.$t('LB00001020'), // 조치 완료일
          name: 'acco_dt',
          fieldName: 'acco_dt',
          width: '70',
        },
        {
          header: this.$t('LB00000678'), // 조치 담당자
          name: 'wem_acrq_emp_nm',
          fieldName: 'wem_acrq_emp_nm',
          width: '70',
        },
        {
          header: this.$t('LB00000143'), // 진행상태
          name: 'acco_prst_nm',
          fieldName: 'acco_prst_nm',
          width: '70',
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

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, index, clickData) {
        const current = gridView.getCurrent()
        if (current.itemIndex < 0) return
        this.schData.wem_actn_id = dataProvider.getValue(current.dataRow, 'wem_actn_id')
        if (current.column === 'acrq_nm') {
          this.$router.push({
            name:
              (dataProvider.getValue(current.dataRow, 'mode') === 'U' && dataProvider.getValue(current.dataRow, 'acco_prst_cd') === '10') ||
              dataProvider.getValue(current.dataRow, 'acco_prst_cd') === '20'
                ? 'opem-opemactnrsltmgntdtledt-opemactnrsltmgntdtledt'
                : 'opem-opemactnrsltmgntdtllup-opemactnrsltmgntdtllup',
            params: {
              schData: this.schData,
              opemactnrsltmgntdtledt: this.schData.wem_actn_id,
              opemactnrsltmgntdtllup: this.schData.wem_actn_id,
            },
          })
        }
      }.bind(this)
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/hlth/opemactn`, { params: this.schData })
      dataProvider.setRows(rows)
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
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
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
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    add() {
      this.schData.wem_actn_id = ''
      this.$router.push({
        name: `opem-opemactnrsltmgntdtledt-opemactnrsltmgntdtledt`,
        params: {
          schData: this.schData,
        },
      })
    },
    popOpen() {
      this.$refs.pop.open(this.schData, false)
    },
    popOpen2() {
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open()
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'wem_acrq_emp_nm', returnData[0].emp_nm)
        this.$set(this.schData, 'wem_acrq_emp_id', returnData[0].emp_no)
      }
    },
    popClose2(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'acrq_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'acrq_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      // 작업환경측정 조치결과 관리
      const fileName = this.$t('LB00001010') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
