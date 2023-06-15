<template>
  <div>
    <div class="location">
      <!-- Home > 교육 및 훈련 > 교육/훈련 결과 관리 -->
      <span>Home > {{ $t('LB00001046') }} > {{ $t('LB00001067') }}</span>
    </div>
    <div class="work_title">
      <!-- 교육/훈련 결과 관리 -->
      <h2>{{ $t('LB00001067') }}</h2>
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
                <!--구분-->
                <div class="label_tit">{{ $t('LB00000741') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="trn_cl_cd"
                  v-model="schData.trn_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000011"
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
                <!--기간-->
                <div class="label_tit">{{ $t('LB00001078') }}</div>
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
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="usg_yn"
                  v-model="schData.usg_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="dept_nm"
                  v-model="schData.dept_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popDeptOpen"
                ></v-text-field>
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
        <!--교육 및 훈련 목록-->
        <h3>{{ $t('LB00001080') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <deptslct ref="deptpop" mkey="trnrsltmgntlist1" @close="popDeptClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import deptslct from '../common/deptslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '교육 및 훈련',
    key(route) {
      return `/trnmgnt/trnrsltmgntlist/${route.params.catalog}`
    },
  },
  components: {
    deptslct,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      tempData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['SH00000011', 'CO00000009'],
      codes: {
        SH00000011: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      deleteModify: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001046') // 교육 및 훈련
    },
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
    sDate.setDate(sDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

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
        this.codes.SH00000011.unshift({ cd: '', cd_nm: 'ALL' })
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
        fieldName: 'trn_id',
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
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'prsn_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cnt_per',
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
        header: this.$t('LB00000741'), // 구분
        name: 'trn_cl_nm',
        fieldName: 'trn_cl_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001066'), // 교육/훈련명
        name: 'trn_nm',
        fieldName: 'trn_nm',
        width: '200',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00001078'), // 기간
        name: 'trn_dt',
        fieldName: 'trn_dt',
        width: '200',
      },
      {
        header: this.$t('LB00001061'), // 대상자
        name: 'prsn_cnt',
        fieldName: 'prsn_cnt',
        width: '100',
      },
      {
        header: this.$t('LB00001083'), // 이수율
        name: 'cnt_per',
        fieldName: 'cnt_per',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'lsch_nm',
        fieldName: 'lsch_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001015'), // 등록일시
        name: 'lsch_dt',
        fieldName: 'lsch_dt',
        width: '100',
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

      if (current.column === 'trn_nm') {
        const trnId = gridView.getValue(index.itemIndex, 'trn_id')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const statCd = gridView.getValue(index.itemIndex, 'trn_prst_cd')
        const user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.deleteModify = true
          }
        })
        if (deptCd === user.dept_cd || this.deleteModify) {
          if (statCd !== 'S02') {
            this.$router.push({
              name: `trnmgnt-trnrsltmgntdtledt-trnrsltmgntdtledt`,
              params: {
                trnrsltmgntdtledt: trnId,
                trn_id: trnId,
                schData: {
                  trn_id: trnId,
                },
                tmpSchData: {
                  cmpy_cd: this.schData.cmpy_cd,
                  wkpl_id: this.schData.wkpl_id,
                  trn_cl_cd: this.schData.trn_cl_cd,
                  usg_yn: this.schData.usg_yn,
                  dept_cd: this.schData.dept_cd,
                  dept_nm: this.schData.dept_nm,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          } else {
            this.$router.push({
              name: `trnmgnt-trnrsltmgntdtllup-trnrsltmgntdtllup`,
              params: {
                trnrsltmgntdtllup: trnId,
                trn_id: trnId,
                schData: {
                  trn_id: trnId,
                },
                tmpSchData: {
                  cmpy_cd: this.schData.cmpy_cd,
                  wkpl_id: this.schData.wkpl_id,
                  trn_cl_cd: this.schData.trn_cl_cd,
                  usg_yn: this.schData.usg_yn,
                  dept_cd: this.schData.dept_cd,
                  dept_nm: this.schData.dept_nm,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          }
        } else {
          this.$router.push({
            name: `trnmgnt-trnrsltmgntdtllup-trnrsltmgntdtllup`,
            params: {
              trnrsltmgntdtllup: trnId,
              trn_id: trnId,
              schData: {
                trn_id: trnId,
              },
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                trn_cl_cd: this.schData.trn_cl_cd,
                usg_yn: this.schData.usg_yn,
                dept_cd: this.schData.dept_cd,
                dept_nm: this.schData.dept_nm,
                sDate: this.schData.sDate,
                eDate: this.schData.eDate,
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
        name: `trnmgnt-trnrsltmgntdtledt-trnrsltmgntdtledt`,
        params: {
          trnrsltmgntdtledt: null,
          trn_id: null,
          schData: {
            trn_id: '',
          },
          tmpSchData: {
            cmpy_cd: this.schData.cmpy_cd,
            wkpl_id: this.schData.wkpl_id,
            trn_cl_cd: this.schData.trn_cl_cd,
            usg_yn: this.schData.usg_yn,
            dept_cd: this.schData.dept_cd,
            dept_nm: this.schData.dept_nm,
            sDate: this.schData.sDate,
            eDate: this.schData.eDate,
          },
        },
      })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
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
      const rows = await this.$axios.$get(`/api/v1/she/trnmgnt/trn-list`, { params: this.schData })
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
        trn_cl_cd: '',
        usg_yn: '',
        dept_cd: '',
        dept_nm: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    popDeptOpen() {
      this.$refs.deptpop.open()
    },
    popDeptClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'con_rspn_org_nm', returnData[0].dept_nm)
        this.$set(this.schData, 'con_rspn_org_cd', returnData[0].dept_cd)
      }
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
      const fileName = this.$t('LB00001085') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 교육훈련결과관리

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
