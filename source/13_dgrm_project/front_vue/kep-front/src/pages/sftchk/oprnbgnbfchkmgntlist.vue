<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업 시작 전 점검 관리 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00000785') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업 시작 전 점검 관리 -->
      <h2>{{ $t('LB00000785') }}</h2>
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
                <!--점검 대상명-->
                <div class="label_tit">{{ $t('LB00000667') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_tgt_nm"
                  v-model="schData.oprb_chk_tgt_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="selectOprbChkList"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 일자-->
                <div class="label_tit">{{ $t('LB00000786') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.oprb_chk_st_dt"
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
                  <v-date-picker v-model="schData.oprb_chk_st_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.oprb_chk_ed_dt"
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
                  <v-date-picker v-model="schData.oprb_chk_ed_dt" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--점검위치-->
                <div class="label_tit">{{ $t('LB00000739') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_wkpl_loc_nm"
                  v-model="schData.oprb_chk_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popLocOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--관리 감독자-->
                <div class="label_tit">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_rspn_emp_nm"
                  v-model="schData.oprb_chk_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('OPRB')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--부관리 감독자-->
                <div class="label_tit">{{ $t('LB00001275') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_spic_emp_nm"
                  v-model="schData.oprb_chk_spic_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('OPRBS')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="oprb_chk_prst_cd"
                  v-model="schData.oprb_chk_prst_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000048"
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
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="selectOprbChkList">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--작업 시작 전 점검 목록-->
        <h3>{{ $t('LB00000787') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <!--엑셀다운로드-->
        <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
      <popup ref="pop" mkey1="oprnbgnbfchkmgntlist1" mkey2="oprnbgnbfchkmgntlist2" @close="popClose" />
      <rgnslct ref="pop2" mkey="oprnbgnbfchkmgntlist1" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popLocClose" />
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import rgnslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()
// date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '작업 시작 전 점검 관리',
    key(route) {
      return `/sftchk/oprnbgnbfchkmgnt/${route.params.catalog}`
    },
  },
  components: {
    popup,
    rgnslct,
    // quillEditor,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      tempData: {},
      userSwitch: '',
      size: 0,
      menu1: false,
      menu2: false,
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['ST00000002', 'SH00000048', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        SH00000048: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
      totalcnt: 0,
    }
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
      const res = await this.$confirm(this.$t('MS00000060'))
      return new Promise((resolve, reject) => {
        if (res) {
          resolve()
        } else {
          // eslint-disable-next-line prefer-promise-reject-errors
          reject('reject')
        }
      })
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000785')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    date = this.getTzDateObj(user.time_zone)

    // 첫날 마지막 날로 초기화
    const firstDay = new Date(date.getFullYear(), date.getMonth(), 1)
    const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)

    this.schData.oprb_chk_st_dt = this.get_date_str_gubun(firstDay, '-')
    this.schData.oprb_chk_ed_dt = this.get_date_str_gubun(lastDay, '-')

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.schData = this.$route.params.tmpSchData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
  },
  mounted() {
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
        this.codes.SH00000048.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })

        // 그리드 세팅
        const fields = [
          {
            fieldName: 'oprb_chk_id',
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
            fieldName: 'oprb_chk_tgt_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_dtl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_schd_dt',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_dt_hm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_rspn_emp_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_spic_emp_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_spic_emp_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_prst_cd_nm',
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
            styleName: 'left',
          },
          {
            header: this.$t('LB00000667'), // 점검 대상명
            name: 'oprb_chk_tgt_nm',
            fieldName: 'oprb_chk_tgt_nm',
            width: '200',
            styleName: 'link left',
          },
          {
            header: this.$t('LB00000739'), // 점검위치
            name: 'oprb_chk_wkpl_loc_nm',
            fieldName: 'oprb_chk_wkpl_loc_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000681'), // 상세위치
            name: 'oprb_chk_dtl_loc_nm',
            fieldName: 'oprb_chk_dtl_loc_nm',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00001379'), // 점검계획일
            name: 'oprb_chk_schd_dt',
            fieldName: 'oprb_chk_schd_dt',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00000680'), // 점검 일시
            name: 'oprb_chk_dt_hm',
            fieldName: 'oprb_chk_dt_hm',
            width: '130',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000738'), // 관리 감독자
            name: 'oprb_chk_rspn_emp_nm',
            fieldName: 'oprb_chk_rspn_emp_nm',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00001275'), // 부관리 감독자
            name: 'oprb_chk_spic_emp_nm',
            fieldName: 'oprb_chk_spic_emp_nm',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00000143'), // 진행상태
            name: 'oprb_chk_prst_cd_nm',
            fieldName: 'oprb_chk_prst_cd_nm',
            width: '100',
            styleName: 'center',
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
        gridView.displayOptions.selectionStyle = 'singleRow'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('id', 'visible', false)

        gridView.onCellClicked = function (grid, index, clickData) {
          gridView.commit()
          if (index.itemIndex > -1) this.openDetail(index)
        }.bind(this)

        this.selectOprbChkList()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
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
    toDateStringfromGubun(str, gubun) {
      const arr = str.split(gubun)
      return arr.join('')
    },
    popOpen(key) {
      this.userSwitch = key
      this.$refs.pop.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          // eslint-disable-next-line
          if (this.userSwitch == 'OPRB') {
            this.$set(this.schData, 'oprb_chk_rspn_emp_nm', i.emp_nm)
            this.$set(this.schData, 'oprb_chk_rspn_emp_id', i.emp_no)
          } else if (this.userSwitch === 'OPRBS') {
            this.$set(this.schData, 'oprb_chk_spic_emp_nm', i.emp_nm)
            this.$set(this.schData, 'oprb_chk_spic_emp_id', i.emp_no)
          }
        })
      }
    },
    popLocOpen() {
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open(this.schData, false)
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'oprb_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'oprb_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async selectOprbChkList() {
      if (!this.isEmpty(this.schData.oprb_chk_st_dt)) {
        this.schData.sch_oprb_chk_st_dt = this.toDateStringfromGubun(this.schData.oprb_chk_st_dt, '-')
      }
      if (!this.isEmpty(this.schData.oprb_chk_ed_dt)) {
        this.schData.sch_oprb_chk_ed_dt = this.toDateStringfromGubun(this.schData.oprb_chk_ed_dt, '-')
      }
      if (this.isEmpty(this.schData.oprb_chk_rspn_emp_nm)) {
        this.schData.oprb_chk_rspn_emp_id = ''
      }
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/oprnbgnbfchkmgntlist`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
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
      date = this.getTzDateObj(user.time_zone)

      // 첫날 마지막 날로 초기화
      const firstDay = new Date(date.getFullYear(), date.getMonth(), 1)
      const lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0)

      this.schData.oprb_chk_st_dt = this.get_date_str_gubun(firstDay, '-')
      this.schData.oprb_chk_ed_dt = this.get_date_str_gubun(lastDay, '-')
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
    openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column === 'oprb_chk_tgt_nm') {
        const value = gridView.getValue(index.itemIndex, 'oprb_chk_id')

        this.$router.push({
          name: `sftchk-oprnbgnbfchkmgntdtllup-oprnbgnbfchkmgntdtllup`,
          params: {
            oprnbgnbfchkmgntdtllup: value,
            tmpSchData: {
              wkpl_id: this.schData.wkpl_id,
              oprb_chk_tgt_nm: this.schData.oprb_chk_tgt_nm,
              oprb_chk_st_dt: this.schData.oprb_chk_st_dt,
              oprb_chk_ed_dt: this.schData.oprb_chk_ed_dt,
              oprb_chk_rspn_emp_nm: this.schData.oprb_chk_rspn_emp_nm,
              oprb_chk_rspn_emp_id: this.schData.oprb_chk_rspn_emp_id,
              oprb_chk_wkpl_loc_id: this.schData.oprb_chk_wkpl_loc_id,
              oprb_chk_wkpl_loc_nm: this.schData.oprb_chk_wkpl_loc_nm,
              oprb_chk_prst_cd: this.schData.oprb_chk_prst_cd,
              cmpy_cd: this.schData.cmpy_cd,
            },
          },
        })
      }
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000788') + new Date().toISOString().substr(0, 10) + '.xlsx'

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
