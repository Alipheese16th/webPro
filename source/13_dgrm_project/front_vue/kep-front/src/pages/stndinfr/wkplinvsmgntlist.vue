<template>
  <div>
    <div class="location">
      <h1>사업장정보</h1>
      <!-- Home > 투자계획 관리 > 사업장별 투자 관리 -->
      <span
        >Home > 사업장 정보 > <b>{{ $t('LB00001152') }}</b></span
      >
    </div>
    <div class="work_title">
      <!-- 사업장별 투자 관리 -->
      <h2>{{ $t('LB00001152') }}</h2>
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
                <v-select ref="cmpy_cd" v-model="schData.cmpy_cd" placeholder="선택하세요." item-text="cd_nm" item-value="cd" :items="codes.CO00000009" @change="codChg"></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="wkpl_id" v-model="schData.wkpl_id" placeholder="선택하세요." item-text="cd_nm" item-value="cd" :items="F_codes.F_00000001"></v-select>
              </v-col>
              <v-col cols="1">
                <!--카테고리-->
                <div class="label_tit">{{ $t('LB00000934') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="invs_cat_cd" v-model="schData.invs_cat_cd" placeholder="선택하세요." item-text="cd_nm" item-value="cd" :items="codes.ST00000014"></v-select>
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
                    <v-text-field slot="activator" ref="invs_year" v-model="schData.invs_year" prepend-icon="event" v-bind="attrs" class="comp_calendar" v-on="on"></v-text-field>
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
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn class="btn_reset" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn class="btn_search" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="sub_title">
      <!--투자 목록-->
      <h3>{{ $t('LB00001173') }}</h3>
    </div>
    <div class="realgrid_container">
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">
          {{ $t('LB00000039') }} <b>{{ totalcnt }}</b>
          {{ $t('LB00000040') }}
        </div>
        <div class="grid_header_right">
          <!--엑셀 다운로드-->
          <v-btn class="btn_grid_border" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn class="btn_grid_solid" @click="openNew">{{ $t('LB00000111') }}</v-btn>
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

let yearVal = new Date()

export default {
  meta: {
    title: '사업장별 투자 관리',
    key(route) {
      return `/stndinfr/wkplinvsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      tempData: {},
      menu: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['ST00000014', 'CO00000009'],
      codes: {
        ST00000014: [{ cd: '', cd_nm: '' }],
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
      return this.$t('LB00001152') // 사업장별 투자 관리
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
    eDate = this.getTzDateObj(user.time_zone)
    sDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(sDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

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
        fieldName: 'cmpy_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_prst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_item_amt',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'prfr_amt',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'amt_per',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'invs_year',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_main_bsns_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_main_bsns_cd',
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
        header: this.$t('LB00001178'), // 사업장 구분
        name: 'wkpl_main_bsns_nm',
        fieldName: 'wkpl_main_bsns_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'left link',
      },
      // {
      //   header: '진행상태', // 진행상태
      //   name: 'invs_prst_nm',
      //   fieldName: 'invs_prst_nm',
      //   width: '100',
      // },
      {
        header: this.$t('LB00001177'), // 투자금액(천원)
        name: 'invs_item_amt',
        fieldName: 'invs_item_amt',
        width: '100',
        styleName: 'right',
        numberFormat: '#,##0',
      },
      {
        header: this.$t('LB00001176'), // 실행금액(천원)
        name: 'prfr_amt',
        fieldName: 'prfr_amt',
        width: '100',
        styleName: 'right',
        numberFormat: '#,##0',
      },
      {
        header: this.$t('LB00001175'), // 실적율(%)
        name: 'amt_per',
        fieldName: 'amt_per',
        width: '100',
        styleName: 'right',
        numberFormat: '#,##0',
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

      if (current.column === 'wkpl_nm') {
        const wkplId = gridView.getValue(index.itemIndex, 'wkpl_id')
        const invsYear = gridView.getValue(index.itemIndex, 'invs_year')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        // const statCd = gridView.getValue(index.itemIndex, 'invs_prst_cd')
        const user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.deleteModify = true
          }
        })
        if (deptCd === user.dept_cd || this.deleteModify) {
          this.$router.push({
            name: `stndinfr-wkplinvsmgntdtledt-wkplinvsmgntdtledt`,
            params: {
              wkplinvsmgntdtledt: wkplId + '-' + invsYear,
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                invs_cat_cd: this.schData.invs_cat_cd,
                invs_year: this.schData.invs_year,
              },
            },
          })
          // if (statCd === 'S01' || statCd === 'S03') {
          //   this.$router.push({
          //     name: `stndinfr-wkplinvsmgntdtledt-wkplinvsmgntdtledt`,
          //     params: {
          //       wkplinvsmgntdtledt: wkplId + '-' + invsYear,
          //       tmpSchData: {
          //         cmpy_cd: this.schData.cmpy_cd,
          //         wkpl_id: this.schData.wkpl_id,
          //         invs_cat_cd: this.schData.invs_cat_cd,
          //         invs_year: this.schData.invs_year,
          //       },
          //     },
          //   })
          // } else {
          //   this.$router.push({
          //     name: `stndinfr-wkplinvsmgntdtllup-wkplinvsmgntdtllup`,
          //     params: {
          //       wkplinvsmgntdtllup: wkplId + '-' + invsYear,
          //       tmpSchData: {
          //         cmpy_cd: this.schData.cmpy_cd,
          //         wkpl_id: this.schData.wkpl_id,
          //         invs_cat_cd: this.schData.invs_cat_cd,
          //         invs_year: this.schData.invs_year,
          //       },
          //     },
          //   })
          // }
        } else {
          this.$router.push({
            name: `stndinfr-wkplinvsmgntdtllup-wkplinvsmgntdtllup`,
            params: {
              wkplinvsmgntdtllup: wkplId + '-' + invsYear,
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                invs_cat_cd: this.schData.invs_cat_cd,
                invs_year: this.schData.invs_year,
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
        name: `stndinfr-wkplinvsmgntdtledt-wkplinvsmgntdtledt`,
        params: {
          wkplinvsmgntdtllup: null,
          tmpSchData: {
            cmpy_cd: this.schData.cmpy_cd,
            wkpl_id: this.schData.wkpl_id,
            invs_cat_cd: this.schData.invs_cat_cd,
            invs_year: this.schData.invs_year,
          },
        },
      })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      return sYear
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/invs-mgnt-list`, { params: this.schData })
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
        invs_cat_cd: '',
        invs_year: this.get_date_str_gubun(yearVal, '-') + '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001179') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 사업장별투자관리

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
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.schData.invs_year = dd
    },
  },
}
</script>
<style lang="scss" scoped></style>
