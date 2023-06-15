<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 건강검진 결과 등록 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001096') }}</span>
    </div>
    <div class="work_title">
      <!-- 건강검진 결과 등록 -->
      <h2>{{ $t('LB00001096') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--검진년도-->
                <div class="label_tit">{{ $t('LB00001055') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      ref="hmex_year"
                      v-model="schData.hmex_year"
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
                    v-model="schData.hmex_year"
                    reactive
                    show-current
                    min="2010"
                    max="2050"
                    no-title
                    @click:year="saveDate(schData.hmex_year)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--성명-->
                <div class="label_tit">{{ $t('LB00000701') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hmex_trgp_nm"
                  v-model="schData.hmex_trgp_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  clearable
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @click:clear="schReset"
                  @keyup.enter="search"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사번-->
                <div class="label_tit">{{ $t('LB00000073') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hmex_tgt_emp_id"
                  v-model="schData.hmex_tgt_emp_id"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  clearable
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="search"
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
            <v-btn color="primary" depressed height="36" @click="search">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--건강검진 결과 목록-->
        <h3>{{ $t('LB00001091') }}</h3>
      </div>
      <div v-if="excel">
        <v-row>
          <v-col cols="6"> </v-col>
          <v-col cols="6">
            <v-file-input ref="fileinput" v-model="files" accept=".xlsx" placeholder="Upload Excel File" @change="handleXlsFile"></v-file-input>
          </v-col>
        </v-row>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀양식 다운로드-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="excelExport">{{ $t('LB00000988') }}</v-btn>
          <!--엑셀 업로드-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="excel = !excel">{{ $t('LB00001081') }}</v-btn>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 581px"></div>
      <div id="exceltemp" style="width: 100%; height: 581px; display: none"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="hmex1edt1" mkey2="hmex1edt2" @close="popClose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let gridView1 = GridView
let dataProvider1 = LocalDataProvider

let yyyy = '2021'
let date = new Date()

export default {
  meta: {
    title: '건강검진 결과 등록',
    key(route) {
      return `/hmex4/${route.params.catalog}`
    },
  },
  components: {
    userslct,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {},
      menu: false,
      tmpSchData: {},
      frmData: {},
      codeid: ['ST00000002', 'SH00000035', 'SH00000037', 'SH00000038'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000035: [{ cd: '', cd_nm: '' }], // 취급물질
        SH00000037: [{ cd: '', cd_nm: '' }], // 검진구분
        SH00000038: [{ cd: '', cd_nm: '' }], // 건강검진 판정
      },
      selST00000002: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000035: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000037: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000038_1: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000038_2: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000038_3: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000038_4: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000038_5: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selInspYn: {
        cd: ['', 'Y', 'N'],
        cd_nm: ['SELECT', 'Y', 'N'],
      },
      selOpinYn: {
        cd: ['', 'Y', 'N'],
        cd_nm: ['SELECT', 'Y', 'N'],
      },
      year: [],
      excel: false,
      files: [],
      cmpy: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001096') // 건강검진 결과 등록
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
  async beforePageLeave(tab, type) {
    if (this.frmData.ckFlag) {
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
  created() {
    const user = this.$store.getters['auth/getUser']
    date = this.getTzDateObj(user.time_zone)
    this.schData.hmex_year = this.get_date_str_gubun2(date, '-') + ''
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    this.cmpy = user.cmpn_cd
    yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    for (let y = yyyy; y >= 2020; y--) {
      this.year.push(y)
    }
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        // res.ST00000002.forEach((i) => {
        //   this.selST00000002.cd.push(i.cd)
        //   this.selST00000002.cd_nm.push(i.cd_nm)
        // })
        res.SH00000035.forEach((i) => {
          this.selSH00000035.cd.push(i.cd)
          this.selSH00000035.cd_nm.push(i.cd_nm)
        })
        res.SH00000037.forEach((i) => {
          this.selSH00000037.cd.push(i.cd)
          this.selSH00000037.cd_nm.push(i.cd_nm)
        })
        res.SH00000038.forEach((i) => {
          this.selSH00000038_1.cd.push(i.cd)
          this.selSH00000038_1.cd_nm.push(i.cd_nm)

          this.selSH00000038_2.cd.push(i.cd)
          this.selSH00000038_2.cd_nm.push(i.cd_nm)

          this.selSH00000038_3.cd.push(i.cd)
          this.selSH00000038_3.cd_nm.push(i.cd_nm)

          this.selSH00000038_4.cd.push(i.cd)
          this.selSH00000038_4.cd_nm.push(i.cd_nm)

          this.selSH00000038_5.cd.push(i.cd)
          this.selSH00000038_5.cd_nm.push(i.cd_nm)
        })

        // 사업장 조회
        this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
          res.forEach((e) => {
            // 상위 코드 조회하여 필터링
            if (e.up_cd === this.cmpy) {
              this.selST00000002.cd.push(e.cd)
              this.selST00000002.cd_nm.push(e.cd_nm)
            }
          })
          this.createGrid()
          this.search()
        })

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.tmpSchData = this.$route.params.schData
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'deal_mtra_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_trgp_org_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_tgt_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_trgp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'jncm_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'rsgn_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_orga_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'abfd_xtra_cnsl_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ovr_opin_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd5',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm5',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '120',
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          lookupDisplay: true,
          // editable: false,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001055'), // 검진년도
          name: 'hmex_year',
          fieldName: 'hmex_year',
          width: '70',
          values: this.year,
          labels: this.year,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000757'), // 순번
          name: 'hmex_sn',
          fieldName: 'hmex_sn',
          width: '50',
          editor: {
            maxLength: 5,
          },
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'hmex_cl_cd',
          fieldName: 'hmex_cl_cd',
          width: '80',
          values: this.selSH00000037.cd,
          labels: this.selSH00000037.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001041'), // 취급물질
          name: 'deal_mtra_cd',
          fieldName: 'deal_mtra_cd',
          width: '100',
          values: this.selSH00000035.cd,
          labels: this.selSH00000035.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '120',
          editor: {
            maxLength: 50,
          },
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hmex_tgt_emp_id',
          fieldName: 'hmex_tgt_emp_id',
          width: '80',
          editor: {
            maxLength: 12,
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hmex_trgp_nm',
          fieldName: 'hmex_trgp_nm',
          width: '80',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'jncm_dt',
          fieldName: 'jncm_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001058'), // 퇴사일
          name: 'rsgn_dt',
          fieldName: 'rsgn_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001090'), // 수검여부
          name: 'hmex_insp_yn',
          fieldName: 'hmex_insp_yn',
          width: '70',
          values: this.selInspYn.cd,
          labels: this.selInspYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001092'), // 검진일자
          name: 'hmex_insp_dt',
          fieldName: 'hmex_insp_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00001093'), // 검진기관
          name: 'hmex_insp_orga_nm',
          fieldName: 'hmex_insp_orga_nm',
          width: '150',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001380'), // 추가상담
          name: 'abfd_xtra_cnsl_yn',
          fieldName: 'abfd_xtra_cnsl_yn',
          width: '80',
          values: this.selInspYn.cd,
          labels: this.selInspYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001094'), // 유소견여부
          name: 'ovr_opin_yn',
          fieldName: 'ovr_opin_yn',
          width: '80',
          values: this.selOpinYn.cd,
          labels: this.selOpinYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001000') + '1', // 판정1
          name: 'hmex_ovr_opin_jgmt_cd1',
          fieldName: 'hmex_ovr_opin_jgmt_cd1',
          width: '80',
          values: this.selSH00000038_1.cd,
          labels: this.selSH00000038_1.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '1', // 소견1
          name: 'hmex_ovr_opin_nm1',
          fieldName: 'hmex_ovr_opin_nm1',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '2', // 판정2
          name: 'hmex_ovr_opin_jgmt_cd2',
          fieldName: 'hmex_ovr_opin_jgmt_cd2',
          width: '80',
          values: this.selSH00000038_2.cd,
          labels: this.selSH00000038_2.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '2', // 소견2
          name: 'hmex_ovr_opin_nm2',
          fieldName: 'hmex_ovr_opin_nm2',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '3', // 판정3
          name: 'hmex_ovr_opin_jgmt_cd3',
          fieldName: 'hmex_ovr_opin_jgmt_cd3',
          width: '80',
          values: this.selSH00000038_3.cd,
          labels: this.selSH00000038_3.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '3', // 소견3
          name: 'hmex_ovr_opin_nm3',
          fieldName: 'hmex_ovr_opin_nm3',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '4', // 판정4
          name: 'hmex_ovr_opin_jgmt_cd4',
          fieldName: 'hmex_ovr_opin_jgmt_cd4',
          width: '80',
          values: this.selSH00000038_4.cd,
          labels: this.selSH00000038_4.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '4', // 소견4
          name: 'hmex_ovr_opin_nm4',
          fieldName: 'hmex_ovr_opin_nm4',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '5', // 판정5
          name: 'hmex_ovr_opin_jgmt_cd5',
          fieldName: 'hmex_ovr_opin_jgmt_cd5',
          width: '80',
          values: this.selSH00000038_5.cd,
          labels: this.selSH00000038_5.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '5', // 소견5
          name: 'hmex_ovr_opin_nm5',
          fieldName: 'hmex_ovr_opin_nm5',
          width: '80',
          editor: {
            maxLength: 200,
          },
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

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = true
      gridView.columnByName('org_nm').editable = false
      gridView.columnByName('hmex_tgt_emp_id').editable = false
      gridView.columnByName('hmex_trgp_nm').editable = false
      gridView.columnByName('jncm_dt').editable = false
      gridView.columnByName('rsgn_dt').editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.cellType === 'data' && (clickData.column === 'org_nm' || clickData.column === 'hmex_tgt_emp_id' || clickData.column === 'hmex_trgp_nm')) {
          const schData = {}
          grid.commit()
          this.$refs.pop.open(schData, false)
        }
      }.bind(this)

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)

      const columns1 = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '120',
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001055'), // 검진년도
          name: 'hmex_year',
          fieldName: 'hmex_year',
          width: '70',
          values: this.year,
          labels: this.year,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000757'), // 순번
          name: 'hmex_sn',
          fieldName: 'hmex_sn',
          width: '50',
          editor: {
            maxLength: 5,
          },
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'hmex_cl_cd',
          fieldName: 'hmex_cl_cd',
          width: '80',
          values: this.selSH00000037.cd,
          labels: this.selSH00000037.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001041'), // 취급물질
          name: 'deal_mtra_cd',
          fieldName: 'deal_mtra_cd',
          width: '100',
          values: this.selSH00000035.cd,
          labels: this.selSH00000035.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '120',
          editor: {
            maxLength: 50,
          },
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hmex_tgt_emp_id',
          fieldName: 'hmex_tgt_emp_id',
          width: '80',
          editor: {
            maxLength: 12,
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hmex_trgp_nm',
          fieldName: 'hmex_trgp_nm',
          width: '80',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'jncm_dt',
          fieldName: 'jncm_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001058'), // 퇴사일
          name: 'rsgn_dt',
          fieldName: 'rsgn_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001090'), // 수검여부
          name: 'hmex_insp_yn',
          fieldName: 'hmex_insp_yn',
          width: '70',
          values: this.selInspYn.cd,
          labels: this.selInspYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001092'), // 검진일자
          name: 'hmex_insp_dt',
          fieldName: 'hmex_insp_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00001093'), // 검진기관
          name: 'hmex_insp_orga_nm',
          fieldName: 'hmex_insp_orga_nm',
          width: '150',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001380'), // 추가상담
          name: 'abfd_xtra_cnsl_yn',
          fieldName: 'abfd_xtra_cnsl_yn',
          width: '80',
          values: this.selInspYn.cd,
          labels: this.selInspYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001094'), // 유소견여부
          name: 'ovr_opin_yn',
          fieldName: 'ovr_opin_yn',
          width: '80',
          values: this.selOpinYn.cd,
          labels: this.selOpinYn.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001000') + '1', // 판정1
          name: 'hmex_ovr_opin_jgmt_cd1',
          fieldName: 'hmex_ovr_opin_jgmt_cd1',
          width: '80',
          values: this.selSH00000038_1.cd,
          labels: this.selSH00000038_1.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '1', // 소견1
          name: 'hmex_ovr_opin_nm1',
          fieldName: 'hmex_ovr_opin_nm1',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '2', // 판정2
          name: 'hmex_ovr_opin_jgmt_cd2',
          fieldName: 'hmex_ovr_opin_jgmt_cd2',
          width: '80',
          values: this.selSH00000038_2.cd,
          labels: this.selSH00000038_2.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '2', // 소견2
          name: 'hmex_ovr_opin_nm2',
          fieldName: 'hmex_ovr_opin_nm2',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '3', // 판정3
          name: 'hmex_ovr_opin_jgmt_cd3',
          fieldName: 'hmex_ovr_opin_jgmt_cd3',
          width: '80',
          values: this.selSH00000038_3.cd,
          labels: this.selSH00000038_3.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '3', // 소견3
          name: 'hmex_ovr_opin_nm3',
          fieldName: 'hmex_ovr_opin_nm3',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '4', // 판정4
          name: 'hmex_ovr_opin_jgmt_cd4',
          fieldName: 'hmex_ovr_opin_jgmt_cd4',
          width: '80',
          values: this.selSH00000038_4.cd,
          labels: this.selSH00000038_4.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '4', // 소견4
          name: 'hmex_ovr_opin_nm4',
          fieldName: 'hmex_ovr_opin_nm4',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
        {
          header: this.$t('LB00001000') + '5', // 판정5
          name: 'hmex_ovr_opin_jgmt_cd5',
          fieldName: 'hmex_ovr_opin_jgmt_cd5',
          width: '80',
          values: this.selSH00000038_5.cd,
          labels: this.selSH00000038_5.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001095') + '5', // 소견5
          name: 'hmex_ovr_opin_nm5',
          fieldName: 'hmex_ovr_opin_nm5',
          width: '80',
          editor: {
            maxLength: 200,
          },
        },
      ]

      dataProvider1 = new LocalDataProvider(false)
      dataProvider1.setFields(fields)
      gridView1 = new GridView('exceltemp')
      gridView1.setDataSource(dataProvider1)
      gridView1.setColumns(columns1)
      gridView1.setFooters({ visible: false })
      gridView1.setStateBar({ visible: false })
      gridView1.setCheckBar({ visible: false })

      gridView1.header.height = 39
      gridView1.displayOptions.rowHeight = 40
      gridView1.footer.height = 40
      gridView1.displayOptions.fitStyle = 'fill'
      gridView1.displayOptions.selectionStyle = 'block'
      gridView1.editOptions.editable = false
    }, // 엑셀 양식 다운로드 그리드 끝

    async search() {
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = this.cmpy
      this.schData.wkpl_id = user.wkpl_id
      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const res = await this.$confirm(this.$t('MS00000667')) // 저장되지 않은 데이터가 있습니다. 검색을 진행하시겠습니까?
        if (!res) {
          return
        }
      }
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexrslt`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    schReset() {
      this.schData = {}
      this.schData.hmex_year = this.get_date_str_gubun2(date, '-') + ''
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
      this.schData.hmex_year = dd
    },
    add() {
      this.frmData.ckFlag = true
      this.totalcnt++
      const user = this.$store.getters['auth/getUser']
      gridView.commit()
      dataProvider.addRow({
        hmex_year: this.getTzDateObj(user.time_zone).getFullYear(),
        hmex_sn: '1',
        wkpl_id: user.wkpl_id,
        hmex_cl_cd: '',
        deal_mtra_cd: '',
        hmex_trgp_org_cd: '',
        org_nm: '',
        hmex_tgt_emp_id: '',
        hmex_trgp_nm: '',
        jncm_dt: '',
        rsgn_dt: '',
        hmex_insp_yn: '',
        hmex_insp_dt: '',
        hmex_insp_orga_nm: '',
        abfd_xtra_cnsl_yn: 'N',
        ovr_opin_yn: '',
        hmex_ovr_opin_jgmt_cd_nm1: '',
        hmex_ovr_opin_nm1: '',
        hmex_ovr_opin_jgmt_cd_nm2: '',
        hmex_ovr_opin_nm2: '',
        hmex_ovr_opin_jgmt_cd_nm3: '',
        hmex_ovr_opin_nm3: '',
        hmex_ovr_opin_jgmt_cd_nm4: '',
        hmex_ovr_opin_nm4: '',
        hmex_ovr_opin_jgmt_cd_nm5: '',
        hmex_ovr_opin_nm5: '',
      })
    },
    del() {
      this.frmData.ckFlag = true
      const items = gridView.getCheckedItems()
      this.totalcnt -= items.length
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        const row = gridView.getCurrent().dataRow
        const values = {
          hmex_trgp_org_cd: returnData[0].dept_cd,
          org_nm: returnData[0].dept_nm,
          hmex_tgt_emp_id: returnData[0].emp_no,
          hmex_trgp_nm: returnData[0].emp_nm,
          jncm_dt: returnData[0].joinc_da,
          cwkr_yn: returnData[0].emp_clsf_cd,
        }
        dataProvider.updateRow(row, values)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      gridView.commit()
      const items = gridView.getItemCount()
      for (let i = 0; i < items; i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'wkpl_id'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wkpl_id' })
          this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hmex_sn'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmex_sn' })
          this.$toast.show(this.$t('MS00000551'), { className: 'toast_error' }) // 순번을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hmex_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmex_cl_cd' })
          this.$toast.show(this.$t('MS00000527'), { className: 'toast_error' }) // 검진구분을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hmex_tgt_emp_id'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmex_trgp_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hmex_insp_yn'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmex_insp_yn' })
          this.$toast.show(this.$t('MS00000552'), { className: 'toast_error' }) // 수검여부를 입력해주세요.
          return false
        }
      }
      return true
    },
    async save() {
      gridView.commit()
      if (!this.frmData.ckFlag) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      if (!this.validation()) return
      // const items = gridView.getCheckedItems()
      // if (items.length < 1) {
      //   this.$toast.show(this.$t('MS00000525'), { className: 'toast_error' }) // 선택된 대상자가 없습니다.
      //   return
      // }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.rslt_list = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        const rslt = {}
        rslt.hmex_year = dataProvider.getValue(i, 'hmex_year')
        rslt.hmex_sn = dataProvider.getValue(i, 'hmex_sn')
        rslt.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
        rslt.hmex_tgt_emp_id = dataProvider.getValue(i, 'hmex_tgt_emp_id')
        rslt.org_nm = dataProvider.getValue(i, 'org_nm')
        rslt.hmex_trgp_nm = dataProvider.getValue(i, 'hmex_trgp_nm')
        rslt.hmex_cl_cd = dataProvider.getValue(i, 'hmex_cl_cd')
        rslt.deal_mtra_cd = dataProvider.getValue(i, 'deal_mtra_cd')
        rslt.hmex_insp_yn = dataProvider.getValue(i, 'hmex_insp_yn')
        rslt.hmex_insp_dt = dataProvider.getValue(i, 'hmex_insp_dt')
        rslt.hmex_insp_orga_nm = dataProvider.getValue(i, 'hmex_insp_orga_nm')
        rslt.abfd_xtra_cnsl_yn = dataProvider.getValue(i, 'abfd_xtra_cnsl_yn')
        rslt.ovr_opin_yn = dataProvider.getValue(i, 'ovr_opin_yn')
        rslt.hmex_ovr_opin_jgmt_cd1 = dataProvider.getValue(i, 'hmex_ovr_opin_jgmt_cd1')
        rslt.hmex_ovr_opin_nm1 = dataProvider.getValue(i, 'hmex_ovr_opin_nm1')
        rslt.hmex_ovr_opin_jgmt_cd2 = dataProvider.getValue(i, 'hmex_ovr_opin_jgmt_cd2')
        rslt.hmex_ovr_opin_nm2 = dataProvider.getValue(i, 'hmex_ovr_opin_nm2')
        rslt.hmex_ovr_opin_jgmt_cd3 = dataProvider.getValue(i, 'hmex_ovr_opin_jgmt_cd3')
        rslt.hmex_ovr_opin_nm3 = dataProvider.getValue(i, 'hmex_ovr_opin_nm3')
        rslt.hmex_ovr_opin_jgmt_cd4 = dataProvider.getValue(i, 'hmex_ovr_opin_jgmt_cd4')
        rslt.hmex_ovr_opin_nm4 = dataProvider.getValue(i, 'hmex_ovr_opin_nm4')
        rslt.hmex_ovr_opin_jgmt_cd5 = dataProvider.getValue(i, 'hmex_ovr_opin_jgmt_cd5')
        rslt.hmex_ovr_opin_nm5 = dataProvider.getValue(i, 'hmex_ovr_opin_nm5')
        rslt.cmpy_cd = this.cmpy
        console.log(rslt.cmpy_cd) // eslint-disable-line no-console
        this.frmData.rslt_list.push(rslt)
      }

      this.$axios.post('/api/v1/she/hlth/hmexrslt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.ckFlag = false
          this.search()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    goList() {
      this.$router.push({
        name: `hmex-hmexrsltmgntlist`,
        params: {
          schData: this.tmpSchData,
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'hidden'
      const header = 'visible'
      const footer = 'hidden'
      // 건강검진결과_업로드양식
      const fileName = this.$t('LB00001097') + '.xlsx'

      gridView1.exportGrid({
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
    handleXlsFile(e) {
      this.frmData.ckFlag = true
      console.log('File : ', this.files) // eslint-disable-line no-console
      const f = this.files
      const reader = new FileReader()
      reader.onload = function (e) {
        const data = e.target.result
        const arr = this.fixdata(data)
        const workbook = XLSX.read(btoa(arr), { type: 'base64' })

        this.process_wb(workbook)
        this.totalcnt = gridView.getItemCount()
        /* DO SOMETHING WITH workbook HERE */
      }.bind(this)
      reader.readAsArrayBuffer(f)
      this.excel = !this.excel
      this.files = []
    },
    fixdata(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    },
    process_wb(wb) {
      const output = this.to_json(wb)
      console.log('output : ', output) // eslint-disable-line no-console
      const sheetNames = Object.keys(output)
      if (sheetNames.length > 0) {
        const colsObj = output[sheetNames][0]
        if (colsObj) {
          dataProvider.fillJsonData(output, { rows: sheetNames[0] })
        }
      }
    },
    to_json(workbook) {
      const result = {}
      workbook.SheetNames.forEach(function (sheetName) {
        const roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {})
        if (roa.length > 0) {
          roa.forEach((o) => {
            o.hmex_year = o.검진년도
            o.hmex_sn = o.순번
            o.wkpl_id = o.사업장
            o.hmex_cl_cd = o.검진구분
            o.deal_mtra_cd = o.취급물질
            o.org_nm = o.부서
            o.hmex_tgt_emp_id = o.사번
            o.hmex_trgp_nm = o.성명
            o.jncm_dt = o.입사일
            o.rsgn_dt = o.퇴사일
            o.hmex_insp_yn = o.수검여부
            o.hmex_insp_dt = o.검진일자
            o.hmex_insp_orga_nm = o.검진기관
            o.abfd_xtra_cnsl_yn = o.추가상담
            o.ovr_opin_yn = o.유소견여부
            o.hmex_ovr_opin_jgmt_cd1 = o.판정1
            o.hmex_ovr_opin_nm1 = o.소견1
            o.hmex_ovr_opin_jgmt_cd2 = o.판정2
            o.hmex_ovr_opin_nm2 = o.소견2
            o.hmex_ovr_opin_jgmt_cd3 = o.판정3
            o.hmex_ovr_opin_nm3 = o.소견3
            o.hmex_ovr_opin_jgmt_cd4 = o.판정4
            o.hmex_ovr_opin_nm4 = o.소견4
            o.hmex_ovr_opin_jgmt_cd5 = o.판정5
            o.hmex_ovr_opin_nm5 = o.소견5
          })
          console.log('roa : ', roa) // eslint-disable-line no-console
          result[sheetName] = roa
        }
      })
      return this.excelCodeSet(result)
    },
    excelCodeSet(result) {
      for (let i = 0; i < result.sheet1.length; i++) {
        let orgVal = result.sheet1[i].wkpl_id
        let idx = this.selST00000002.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selST00000002.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].wkpl_id = cdVal

        orgVal = result.sheet1[i].deal_mtra_cd
        idx = this.selSH00000035.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000035.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].deal_mtra_cd = cdVal

        orgVal = result.sheet1[i].hmex_cl_cd
        idx = this.selSH00000037.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000037.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_cl_cd = cdVal

        orgVal = result.sheet1[i].hmex_insp_yn
        idx = this.selInspYn.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selInspYn.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_insp_yn = cdVal

        orgVal = result.sheet1[i].ovr_opin_yn
        idx = this.selOpinYn.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selOpinYn.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].ovr_opin_yn = cdVal

        orgVal = result.sheet1[i].hmex_ovr_opin_jgmt_cd1
        idx = this.selSH00000038_1.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000038_1.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_ovr_opin_jgmt_cd1 = cdVal

        orgVal = result.sheet1[i].hmex_ovr_opin_jgmt_cd2
        idx = this.selSH00000038_2.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000038_2.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_ovr_opin_jgmt_cd2 = cdVal

        orgVal = result.sheet1[i].hmex_ovr_opin_jgmt_cd3
        idx = this.selSH00000038_3.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000038_3.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_ovr_opin_jgmt_cd3 = cdVal

        orgVal = result.sheet1[i].hmex_ovr_opin_jgmt_cd4
        idx = this.selSH00000038_4.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000038_4.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_ovr_opin_jgmt_cd4 = cdVal

        orgVal = result.sheet1[i].hmex_ovr_opin_jgmt_cd5
        idx = this.selSH00000038_5.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000038_5.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmex_ovr_opin_jgmt_cd5 = cdVal
      }
      return result
    },
  },
}
</script>
<style lang="scss" scoped></style>
