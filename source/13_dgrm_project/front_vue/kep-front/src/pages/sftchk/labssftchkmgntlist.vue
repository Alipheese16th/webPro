<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 연구실 일상점검 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001104') }}</span>
    </div>
    <div class="work_title">
      <!-- 연구실 일상점검 -->
      <h2>{{ $t('LB00001104') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
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
                  :items="codes.ST00000002"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검년도-->
                <div class="label_tit">{{ $t('LB00001105') }}</div>
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
        <!--연구실 일상점검 목록-->
        <h3>{{ $t('LB00001106') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
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
    title: '연구실 일상점검',
    key(route) {
      return `/sftchk/labssftchkmgntlist/${route.params.catalog}`
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
      tmpSchData: {},
      menu: false,
      menu1: false,
      totalcnt: 0,
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
      },
      year: [],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001104')
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
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.schData = this.$route.params.tmpSchData
    } else {
      this.schData.wkpl_id = user.wkpl_id
    }

    yearVal = this.getTzDateObj(user.time_zone)
    this.schData.wem_year = this.get_date_str_gubun(yearVal, '-') + ''
  },
  mounted() {
    // const user = this.$store.getters['auth/getUser']
    // const yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    // for (let y = yyyy; y >= 2020; y--) {
    //   this.year.push(y)
    // }
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'labs_sft_chk_id',
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
        fieldName: 'labs_sft_chk_yrmn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'fin_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_note_txt',
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
        styleName: 'center',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000789'), // 점검월
        name: 'labs_sft_chk_yrmn',
        fieldName: 'labs_sft_chk_yrmn',
        width: '100',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000640'), // 점검명
        name: 'labs_sft_chk_nm',
        fieldName: 'labs_sft_chk_nm',
        width: '200',
        styleName: 'link center',
      },
      // Y: 파란색, N: 빨간색
      {
        header: this.$t('LB00001107'), // 완료여부
        name: 'fin_yn',
        fieldName: 'fin_yn',
        width: '100',
        styleCallback(grid, dataCell) {
          const ret = {}
          const finyn = grid.getValue(dataCell.index.itemIndex, 'fin_yn')
          if (finyn !== 'N') {
            return (ret.styleName = 'rgsample-blue-column')
          } else return (ret.styleName = 'rgsample-red-column')
        },
      },
      {
        header: this.$t('LB00000095'), // 비고
        name: 'labs_sft_chk_note_txt',
        fieldName: 'labs_sft_chk_note_txt',
        width: '200',
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

    gridView.onCellClicked = function (grid, index, clickData) {
      gridView.commit()
      if (index.itemIndex > -1) this.openDetail(index)
    }.bind(this)

    this.select()
  },
  methods: {
    openNew() {
      this.$router.push({
        name: `sftchk-labssftchkmgntdtledt-labssftchkmgntdtledt`,
        params: {
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
            labs_sft_chk_yrmn: this.schData.labs_sft_chk_yrmn,
            labs_sft_chk_nm: this.schData.labs_sft_chk_nm,
            labs_sft_chk_txt: this.schData.labs_sft_chk_txt,
            fin_yn: this.schData.fin_yn,
            labs_sft_chk_note_txt: this.schData.labs_sft_chk_note_txt,
            labs_sft_chk_id: this.schData.labs_sft_chk_id,
          },
        },
      })
    },
    get_date_str_gubun(date) {
      const sYear = date.getFullYear()
      return sYear
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
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/labssftchkmgntlist`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    schReset() {
      const user = this.$store.getters['auth/getUser']
      gridView.commit()
      this.schData = {}
      this.schData.wkpl_id = user.wkpl_id
      this.schData.wem_year = this.get_date_str_gubun(yearVal, '-') + ''
    },
    openDetail(index) {
      const user = this.$store.getters['auth/getUser']
      if (index.itemIndex < 0) return
      if (index.column === 'labs_sft_chk_nm') {
        const value = gridView.getValue(index.itemIndex, 'labs_sft_chk_id') // id값
        const dept = gridView.getValue(index.itemIndex, 'dept_cd') // 부서

        if (dept === user.dept_cd) {
          this.$router.push({
            name: `sftchk-labssftchkmgntdtledt-labssftchkmgntdtledt`,
            params: {
              labssftchkmgntdtledt: value,
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                labs_sft_chk_id: this.schData.labs_sft_chk_id,
                cmpy_nm: this.schData.cmpy_nm,
                labs_sft_chk_yrmn: this.schData.labs_sft_chk_yrmn,
                labs_sft_chk_nm: this.schData.labs_sft_chk_nm,
                fin_yn: this.schData.fin_yn,
                labs_sft_chk_note_txt: this.schData.labs_sft_chk_note_txt,
              },
            },
          })
        } else {
          this.$router.push({
            name: `sftchk-labssftchkmgntdtllup-labssftchkmgntdtllup`,
            params: {
              labssftchkmgntdtllup: value,
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                labs_sft_chk_id: this.schData.labs_sft_chk_id,
                cmpy_nm: this.schData.cmpy_nm,
                labs_sft_chk_yrmn: this.schData.labs_sft_chk_yrmn,
                labs_sft_chk_nm: this.schData.labs_sft_chk_nm,
                fin_yn: this.schData.fin_yn,
                labs_sft_chk_note_txt: this.schData.labs_sft_chk_note_txt,
              },
            },
          })
        }
      }
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.schData.wem_year = dd
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001108') + new Date().toISOString().substr(0, 10) + '.xlsx'

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
<style>
.rgsample-blue-column {
  color: blue;
}
.rgsample-red-column {
  color: red;
}
</style>
