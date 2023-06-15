<template>
  <div>
    <div class="location">
      <!-- Home > 유해위험기계기구 관리 > 유해위험기계기구 관리 -->
      <span>Home > {{ $t('LB00000980') }} > {{ $t('LB00000980') }}</span>
    </div>
    <div class="work_title">
      <!-- 유해위험기계기구 관리 -->
      <h2>{{ $t('LB00000980') }}</h2>
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
                <!--점검일-->
                <div class="label_tit">{{ $t('LB00000790') }}</div>
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
                <!--기계종류-->
                <div class="label_tit">{{ $t('LB00001004') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="hrm_type_cd"
                  v-model="schData.hrm_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000004"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_wkpl_loc_nm"
                  v-model="schData.hrm_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popRgnOpen()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--기계명-->
                <div class="label_tit">{{ $t('LB00001003') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_nm"
                  v-model="schData.hrm_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="use_yn"
                  v-model="schData.use_yn"
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
        <!--유해위험기계기구 목록-->
        <h3>{{ $t('LB00001002') }}</h3>
      </div>
      <div class="grid_header" style="justify-content: start; important!">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div style="margin-left: auto; margin-right: 5px">
          <v-menu v-model="menu9" :close-on-content-click="false" :nudge-right="40" min-width="290px">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                ref="hrm_chk_dt"
                v-model="dtData.hrm_chk_dt"
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
            <v-date-picker v-model="dtData.hrm_chk_dt" no-title @input="menu9 = false"> </v-date-picker>
          </v-menu>
        </div>
        <div>
          <!-- 저장 -->
          <v-btn color="primary" depressed height="30" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="rgltchklistuserslct1" mkey2="rgltchklistuserslct2" @close="popclose" />
    <rgnslct ref="rgbpop" mkey="hmflriskmcnmgntlist1" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popRgnClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import rgnslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '유해위험기계기구 관리',
    key(route) {
      return `/hmflmcn/hmflmcn/${route.params.catalog}`
    },
  },
  components: {
    popup,
    rgnslct,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      dtData: {},
      bizData: {
        emp_no: '',
        cnct_1st_cd: '',
        cnct_2nd_cd: '',
        emp_nm: '',
      },
      tempData: {},
      menu1: false,
      menu2: false,
      menu9: false,
      totalcnt: 0,
      codeid: ['CO00000006', 'SH00000004', 'CO00000009'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
        SH00000004: [{ cd: '', cd_nm: '' }],
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
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000980') // 유해위험기계기구 관리
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

    this.dtData.hrm_chk_dt = ''

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
        this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000004.unshift({ cd: '', cd_nm: 'ALL' })
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
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_type_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_mgnt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_istl_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_mnfr_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_asst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_wkpl_loc_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_wkpl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_dtl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_cyc_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_rspn_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_spic_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_rspn_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_spic_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_spec_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_usg_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_atch_file_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_fnl_chk_dt',
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
        styleName: 'left',
      },
      {
        header: this.$t('LB00000984'), // 유해위험기계기구 번호
        name: 'hrm_mgnt_no ',
        fieldName: 'hrm_mgnt_no',
        width: '150',
      },
      {
        header: this.$t('LB00000983'), // 기기명
        name: 'hrm_nm',
        fieldName: 'hrm_nm',
        width: '200',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000982'), // 기기종류
        name: 'hrm_type_nm',
        fieldName: 'hrm_type_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000644'), // 위치
        name: 'hrm_wkpl_loc_nm',
        fieldName: 'hrm_wkpl_loc_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000681'), // 상세위치
        name: 'hrm_dtl_loc_nm',
        fieldName: 'hrm_dtl_loc_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000986'), // 제조사
        name: 'hrm_mnfr_nm',
        fieldName: 'hrm_mnfr_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000101'), // 담당자
        name: 'hrm_rspn_emp_nm',
        fieldName: 'hrm_rspn_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001007'), // 설치일
        name: 'hrm_istl_dt',
        fieldName: 'hrm_istl_dt',
        width: '100',
      },
      {
        header: this.$t('LB00001006'), // 최종 안전검사일
        name: 'hrm_chk_dt',
        fieldName: 'hrm_chk_dt',
        width: '100',
      },
      {
        header: this.$t('LB00001005'), // 안전검사 예정일
        name: 'hrm_fnl_chk_dt',
        fieldName: 'hrm_fnl_chk_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'hrm_usg_yn',
        fieldName: 'hrm_usg_yn',
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
    gridView.setCheckBar({ visible: true })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()

      if (current.column === 'hrm_nm') {
        const wkplId = gridView.getValue(index.itemIndex, 'wkpl_id')
        const hrmId = gridView.getValue(index.itemIndex, 'hrm_id')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const user = this.$store.getters['auth/getUser']
        if (deptCd === user.dept_cd) {
          this.$router.push({
            name: `hmflmcn-hmflriskmcnmgntdtledt-hmflriskmcnmgntdtledt`,
            params: {
              hmflriskmcnmgntdtledt: hrmId,
              wkpl_id: wkplId,
              hrm_id: hrmId,
              schData: {
                wkpl_id: wkplId,
                hrm_id: hrmId,
              },
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                hrm_type_cd: this.schData.hrm_type_cd,
                hrm_wkpl_loc_id: this.schData.hrm_wkpl_loc_id,
                hrm_nm: this.schData.hrm_nm,
                use_yn: this.schData.use_yn,
                sDate: this.schData.sDate,
                eDate: this.schData.eDate,
              },
            },
          })
        } else {
          this.$router.push({
            name: `hmflmcn-hmflriskmcnmgntdtllup-hmflriskmcnmgntdtllup`,
            params: {
              hmflriskmcnmgntdtllup: hrmId,
              wkpl_id: wkplId,
              hrm_id: hrmId,
              schData: {
                wkpl_id: wkplId,
                hrm_id: hrmId,
              },
              tmpSchData: {
                cmpy_cd: this.schData.cmpy_cd,
                wkpl_id: this.schData.wkpl_id,
                hrm_type_cd: this.schData.hrm_type_cd,
                hrm_wkpl_loc_id: this.schData.hrm_wkpl_loc_id,
                hrm_nm: this.schData.hrm_nm,
                use_yn: this.schData.use_yn,
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
        name: `hmflmcn-hmflriskmcnmgntdtledt-hmflriskmcnmgntdtledt`,
        params: {
          hmflriskmcnmgntdtledt: null,
          wkpl_id: null,
          hrm_id: null,
          schData: {
            wkpl_id: '',
            hrm_id: '',
          },
          tmpSchData: {
            cmpy_cd: this.schData.cmpy_cd,
            wkpl_id: this.schData.wkpl_id,
            hrm_type_cd: this.schData.hrm_type_cd,
            hrm_wkpl_loc_id: this.schData.hrm_wkpl_loc_id,
            hrm_nm: this.schData.hrm_nm,
            use_yn: this.schData.use_yn,
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
    searchValidate() {
      return true
    },
    async select() {
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/she/hmflmcn/hmfl-mcn-list`, { params: this.schData })
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
        wkpl_nm: '',
        use_yn: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'username', i.emp_nm)
          this.$set(this.schData, 'rglt_chk_req_emp_no', i.emp_no)
        })
      }
    },
    popRgnOpen() {
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.rgbpop.open(this.schData, false)
    },
    popRgnClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'hrm_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'hrm_wkpl_loc_id', returnData.wkpl_loc_id)
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    saveValidate() {
      gridView.commit()
      if (this.isEmpty(this.dtData.hrm_chk_dt)) {
        this.$toast.show(this.$t('MS00000461'), { className: 'toast_error' }) // 점검일자를 입력해주세요.
        this.$refs.hrm_chk_dt.focus()
        return false
      }
      const items = gridView.getCheckedItems()
      for (let i = 0; i < items.length; i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(items[i]) === 'createAndDeleted' || dataProvider.getRowState(items[i]) === 'deleted') {
          continue
        }
        if (this.dtData.hrm_chk_dt === dataProvider.getValue(items[i], 'hrm_chk_dt')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_dt' })
          this.$toast.show(this.$t('MS00000460'), { className: 'toast_error' }) // 동일한 점검일자가 있습니다.
          return false
        }
      }
      return true
    },
    async save() {
      gridView.commit()

      const items = gridView.getCheckedItems()
      if (items.length < 1) {
        this.$toast.show(this.$t('MS00000030'), { className: 'toast_error' }) // 선택된 데이터가 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      const updateRow = []

      for (let i = 0; i < items.length; i++) {
        if (dataProvider.getRowState(items[i]) === 'createAndDeleted' || dataProvider.getRowState(items[i]) === 'deleted') {
          continue
        }
        const temp = {}

        let hrmDtVal = this.dtData.hrm_chk_dt
        if (!this.isEmpty(this.dtData.hrm_chk_dt)) {
          hrmDtVal = hrmDtVal.replace(/-/gi, '')
        }

        temp.hrm_id = dataProvider.getValue(items[i], 'hrm_id')
        temp.hrm_chk_dt = hrmDtVal

        updateRow.push(temp)
      }
      this.frmData.updateRow = updateRow

      await this.$axios.put('/api/v1/she/hmflmcn/hmfl-mcn-list', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001009') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 유해위험기계기구관리

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
