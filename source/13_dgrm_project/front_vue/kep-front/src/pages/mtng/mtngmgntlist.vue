<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 회의체 관리-->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000719') }}</span>
    </div>
    <div class="work_title">
      <!-- 회의체 관리 -->
      <h2>{{ $t('LB00000719') }}</h2>
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
                <!--회의 일자-->
                <div class="label_tit">{{ $t('LB00000726') }}</div>
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
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--회의구분-->
                <div class="label_tit">{{ $t('LB00000728') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.mtng_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--회의명-->
                <div class="label_tit">{{ $t('LB00000730') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  v-model="schData.mtng_ttl"
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
              <v-col cols="1">
                <!--정기/임시 구분-->
                <div class="label_tit">{{ $t('LB00000733') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.mtng_cyc_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000003"
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
        <!--회의체 목록-->
        <h3>{{ $t('LB00000736') }}</h3>
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
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '회의체 관리',
    key(route) {
      return `/mtng/${route.params.catalog}`
    },
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000001', 'SH00000002', 'SH00000003', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000001: [{ cd: '', cd_nm: '' }], // 회의구분
        SH00000002: [{ cd: '', cd_nm: '' }], // 의제구분
        SH00000003: [{ cd: '', cd_nm: '' }], // 정기/임시 구분
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
      return this.$t('LB00000719') // 회의체 관리
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
        this.codes.SH00000001.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000003.unshift({ cd: '', cd_nm: 'ALL' })
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
          fieldName: 'mtng_id',
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
          fieldName: 'mtng_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_ttl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_pl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_hm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_rslt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_rslt_prst_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_rslt_aprv_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_rslt_prst_cd',
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
          width: '70',
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '70',
        },
        {
          header: this.$t('LB00000730'), // 회의명
          name: 'mtng_ttl',
          fieldName: 'mtng_ttl',
          width: '220',
          styleName: 'link left',
        },
        {
          header: this.$t('LB00000728'), // 회의구분
          name: 'mtng_cl_nm',
          fieldName: 'mtng_cl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000746'), // 장소
          name: 'mtng_pl_nm',
          fieldName: 'mtng_pl_nm',
          width: '150',
        },
        {
          header: this.$t('LB00000726'), // 회의일자
          name: 'mtng_dt',
          fieldName: 'mtng_dt',
          width: '70',
        },
        {
          header: this.$t('LB00000249'), // 시간
          name: 'mtng_hm',
          fieldName: 'mtng_hm',
          width: '70',
        },
        {
          header: this.$t('LB00000143'), // 진행상태
          name: 'mtng_rslt_prst_nm',
          fieldName: 'mtng_rslt_prst_nm',
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
        this.schData.mtng_id = dataProvider.getValue(current.dataRow, 'mtng_id')
        const user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.deleteModify = true
          }
        })
        if (current.column === 'mtng_ttl') {
          this.$router.push({
            name:
              (dataProvider.getValue(current.dataRow, 'mode') === 'U' && dataProvider.getValue(current.dataRow, 'mtng_rslt_prst_cd') === '10') || this.deleteModify
                ? 'mtng-mtngmgntdtledt-mtngmgntdtledt'
                : 'mtng-mtngmgntdtllup-mtngmgntdtllup',
            params: {
              schData: this.schData,
              mtngmgntdtledt: this.schData.mtng_id,
              mtngmgntdtllup: this.schData.mtng_id,
            },
          })
        }
      }.bind(this)
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/sfty/mtng`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
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
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
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
      this.schData.mtng_id = ''
      this.$router.push({
        name: `mtng-mtngmgntdtledt-mtngmgntdtledt`,
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
      // 회의체 관리
      const fileName = this.$t('LB00000719') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
