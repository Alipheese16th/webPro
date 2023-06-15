<template>
  <div>
    <div class="location">
      <!-- Home > 작업환경측정 > 작업환경측정 관리 -->
      <span>Home > {{ $t('LB00000960') }} > {{ $t('LB00000961') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업환경측정 관리 -->
      <h2>{{ $t('LB00000961') }}</h2>
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
                <!--측정구분-->
                <div class="label_tit">{{ $t('LB00000962') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.wem_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000016"
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
                <!--측정연도-->
                <div class="label_tit">{{ $t('LB00000963') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      ref="wem_year"
                      v-model="schData.wem_year"
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
                    v-model="schData.wem_year"
                    reactive
                    show-current
                    min="2010"
                    max="2050"
                    no-title
                    @click:year="saveDate(schData.wem_year)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--반기-->
                <div class="label_tit">{{ $t('LB00000964') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.wem_cyc_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000017"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--측정일-->
                <div class="label_tit">{{ $t('LB00000965') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sch_dt"
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
                  <v-date-picker v-model="schData.sch_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
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
        <!--작업환경 측정 목록-->
        <h3>{{ $t('LB00000966') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <v-btn color="primary" height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
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

let date = new Date()
export default {
  meta: {
    title: '작업환경측정 관리',
    key(route) {
      return `/opem/${route.params.catalog}`
    },
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu: false,
      menu1: false,
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000016', 'SH00000017', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000016: [{ cd: '', cd_nm: '' }], // 측정구분
        SH00000017: [{ cd: '', cd_nm: '' }], // 반기
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      year: [],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000961') // 작업환경측정 관리
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
  mounted() {
    const user = this.$store.getters['auth/getUser']
    date = this.getTzDateObj(user.time_zone)
    // for (let y = yyyy; y >= 2020; y--) {
    //   this.year.push(y)
    // }
    date.setDate(date.getDate())
    this.schData.sch_dt = this.get_date_str_gubun(date, '-')
    date = this.getTzDateObj(user.time_zone)
    this.schData.wem_year = this.get_date_str_gubun2(date, '-') + ''

    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
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
        this.codes.SH00000016.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000017.unshift({ cd: '', cd_nm: 'ALL' })
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
          fieldName: 'wem_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_year',
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
          fieldName: 'wem_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_cyc_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_orga_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_req_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_btrm_tgt_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_prst_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_aprv_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dept_cd',
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
          header: this.$t('LB00000962'), // 측정구분
          name: 'wem_cl_nm',
          fieldName: 'wem_cl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000963'), // 측정연도
          name: 'wem_year',
          fieldName: 'wem_year',
          width: '100',
        },
        {
          header: this.$t('LB00000964'), // 반기
          name: 'wem_cyc_nm',
          fieldName: 'wem_cyc_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000970'), // 의뢰자
          name: 'wem_req_emp_nm',
          fieldName: 'wem_req_emp_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000972'), // 측정기관
          name: 'wem_orga_nm',
          fieldName: 'wem_orga_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000973'), // 측정기간
          name: 'wem_dt',
          fieldName: 'wem_dt',
          width: '200',
          styleName: 'link',
        },
        {
          header: this.$t('LB00000143'), // 진행상태
          name: 'wem_prst_nm',
          fieldName: 'wem_prst_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000974'), // 개선대상 여부
          name: 'wem_btrm_tgt_yn',
          fieldName: 'wem_btrm_tgt_yn',
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
        const user = this.$store.getters['auth/getUser']
        const current = gridView.getCurrent()
        if (current.itemIndex < 0) return
        this.schData.wem_id = dataProvider.getValue(current.dataRow, 'wem_id')
        const dept = gridView.getValue(index.itemIndex, 'dept_cd') // 부서

        if (current.column === 'wem_dt') {
          this.$router.push({
            name:
              dataProvider.getValue(current.dataRow, 'mode') === 'U' && dataProvider.getValue(current.dataRow, 'wem_prst_cd') === '10' && dept === user.dept_cd
                ? 'opem-opemmgntdtledt-opemmgntdtledt'
                : 'opem-opemmgntdtllup-opemmgntdtllup',
            params: {
              schData: this.schData,
              opemmgntdtledt: this.schData.wem_id,
              opemmgntdtllup: this.schData.wem_id,
            },
          })
        }
      }.bind(this)
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/hlth/opem`, { params: this.schData })
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
      this.schData.sch_dt = this.get_date_str_gubun(date, '-')
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.schData.wem_year = this.get_date_str_gubun2(date, '-') + ''
      this.codChgReturn(user.cmpn_cd)
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
    get_date_str_gubun2(date) {
      const sYear = date.getFullYear()
      return sYear
    },
    saveDate(date, gubun) {
      const dd = date.substring(0, 4)
      this.schData.wem_year = dd
    },
    add() {
      this.schData.wem_id = ''
      this.$router.push({
        name: `opem-opemmgntdtledt-opemmgntdtledt`,
        params: {
          schData: this.schData,
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      // 작업환경측정 관리
      const fileName = this.$t('LB00000961') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
