<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 사업장 공정 관리-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('사업장 공정 관리') }}</span>
    </div>
    <div class="work_title">
      <!-- 사업장 공정 관리 -->
      <h2>{{ $t('사업장 공정 관리') }}</h2>
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
                  label=""
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
                  label=""
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
        <!--사업장 공정 관리 목록-->
        <h3>{{ $t('사업장 공정 관리 목록') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn v-if="isAdminGrp" color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
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

export default {
  meta: {
    title: '사업장 공정 관리',
    key(route) {
      return `/riskeval/wkplprcsmgnt/${route.params.catalog}`
    },
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CO00000009'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('사업장 공정 관리') // 사업장 공정 관리
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdminGrp = true
      }
    })

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
        // this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.createGrid()
    this.select()
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'cls_id',
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
          fieldName: 'level1_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level2_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level3_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'id1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'id2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'id3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'id4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'date1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'date2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'date3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'date4',
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
          header: this.$t('대분류'), // 대분류
          name: 'level1_nm',
          fieldName: 'level1_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('공정명'), // 공정명
          name: 'level2_nm',
          fieldName: 'level2_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('세부공정'), // 세부공정
          name: 'level3_nm',
          fieldName: 'level3_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('작성일자1'), // 작성일자1
          name: 'date1',
          fieldName: 'date1',
          width: '70',
          styleName: 'link center',
        },
        {
          header: this.$t('작성일자2'), // 작성일자2
          name: 'date2',
          fieldName: 'date2',
          width: '70',
          styleName: 'link center',
        },
        {
          header: this.$t('작성일자3'), // 작성일자3
          name: 'date3',
          fieldName: 'date3',
          width: '70',
          styleName: 'link center',
        },
        {
          header: this.$t('작성일자4'), // 작성일자4
          name: 'date4',
          fieldName: 'date4',
          width: '70',
          styleName: 'link center',
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

      gridView.onCellClicked = function (grid, clickData) {
        if (!(clickData.fieldName === 'date1' || clickData.fieldName === 'date2' || clickData.fieldName === 'date3' || clickData.fieldName === 'date4')) return

        const url = 'riskeval-riskevalbasiclup-riskevalbasiclup'
        const param = {}
        param.schData = this.schData
        if (clickData.fieldName === 'date1') {
          param.riskevalbasiclup = grid.getValue(clickData.itemIndex, 'id1')
        } else if (clickData.fieldName === 'date2') {
          param.riskevalbasiclup = grid.getValue(clickData.itemIndex, 'id2')
        } else if (clickData.fieldName === 'date3') {
          param.riskevalbasiclup = grid.getValue(clickData.itemIndex, 'id3')
        } else if (clickData.fieldName === 'date4') {
          param.riskevalbasiclup = grid.getValue(clickData.itemIndex, 'id4')
        }

        if (!param.riskevalbasiclup) return

        this.$router.push({
          name: url,
          params: param,
        })
      }.bind(this)
    },
    async select() {
      this.schData.level = '2'
      // this.schData.usg_yn = 'Y'
      const rows = await this.$axios.$get(`/api/v1/she/riskeval/wkplprcsmgnt`, { params: this.schData })
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
      // this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', this.F_codes.F_00000001[0].cd)
    },
    codChgReturn(data) {
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      // this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    reset() {
      gridView.commit()
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
      const id = this.schData.wkpl_id
      const wkpl = this.FF_00000001.filter(function (n) {
        return n.cd === id
      })
      // console.log('wkpl_nm is ' + JSON.stringify(wkpl[0].cd_nm))
      this.schData.level = ''
      this.schData.wkpl_nm = wkpl[0].cd_nm
      this.$router.push({
        name: `riskeval-riskevalwkplprcsmgntedt-riskevalwkplprcsmgntedt`,
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
      // 사업장 공정 관리
      const fileName = this.$t('사업장 공정 관리') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
