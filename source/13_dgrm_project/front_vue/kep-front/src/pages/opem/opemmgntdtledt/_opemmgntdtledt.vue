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
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--의뢰자-->
                <div class="label_tit essn">{{ $t('LB00000970') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wem_req_emp_nm"
                  v-model="frmData.wem_req_emp_nm"
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
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--측정구분-->
                <div class="label_tit essn">{{ $t('LB00000962') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wem_cl_cd"
                  v-model="frmData.wem_cl_cd"
                  label="SELECT"
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
              <v-col cols="1">
                <!--측정연도-->
                <div class="label_tit essn">{{ $t('LB00000963') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      v-model="frmData.wem_year"
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
                    v-model="frmData.wem_year"
                    reactive
                    show-current
                    min="2010"
                    max="2050"
                    no-title
                    @click:year="saveDate(frmData.wem_year)"
                  ></v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--반기-->
                <div class="label_tit essn">{{ $t('LB00000964') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wem_cyc_cd"
                  v-model="frmData.wem_cyc_cd"
                  label="SELECT"
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
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--측정기간-->
                <div class="label_tit essn">{{ $t('LB00000973') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="clickChk" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="wem_range_dt"
                      v-model="wemRangeDtChange"
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
                  <v-date-picker v-model="frmData.wem_range_dt" range no-title @click="menu1 = true"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--측정기관-->
                <div class="label_tit essn">{{ $t('LB00000972') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  ref="wem_orga_nm"
                  v-model="frmData.wem_orga_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--개선대상 여부-->
                <div class="label_tit essn">{{ $t('LB00000974') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wem_btrm_tgt_yn"
                  v-model="frmData.wem_btrm_tgt_yn"
                  label="SELECT"
                  :items="tgtYn"
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
                <!--측정결과 종합의견-->
                <div class="label_tit essn">{{ $t('LB00000979') }}</div>
              </v-col>
              <v-col cols="11">
                <!-- 입력하세요 -->
                <v-textarea
                  ref="wem_oval_opin_txt"
                  v-model="frmData.wem_oval_opin_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  name="input-7-4"
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--측정결과-->
        <h3>{{ $t('LB00000981') }}</h3>
      </div>
      <div v-if="excel">
        <v-row>
          <v-col cols="6"> </v-col>
          <v-col cols="6">
            <v-file-input v-model="files" accept=".xlsx" placeholder="Upload Excel File" @change="handleXlsFile"></v-file-input>
          </v-col>
        </v-row>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀양식 다운로드-->
          <v-btn color="" outlined height="30" @click="excelExport">{{ $t('LB00000988') }}</v-btn>
          <!--엑셀업로드-->
          <v-btn color="" outlined height="30" @click="excel = !excel">{{ $t('LB00000720') }}</v-btn>
          <!-- 추가 -->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div id="exceltemp" style="width: 100%; height: 581px; display: none"></div>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 개선계획 정보 -->
        <h3>{{ $t('LB00000992') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.wem_btrm_plan_txt" :options="editorOption" />
          </v-col>
        </v-row>
      </v-container>
      <div class="group_padd"></div>
      <!--첨부파일 목록(KO)-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist">
        <table>
          <div v-if="true">
            <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" @click="appr">{{ $t('LB00000763') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="opemedt1" mkey2="opemedt2" @close="popClose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let gridView1 = GridView
let dataProvider1 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '작업환경측정 관리',
    key(route) {
      return `/opem/${route.params.catalog}`
    },
  },
  components: {
    userslct,
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      modify: false,
      menu: false,
      codeid: ['SH00000016', 'SH00000017', 'SH00000018', 'SH00000046'],
      codes: {
        SH00000016: [{ cd: '', cd_nm: '' }], // 측정구분
        SH00000017: [{ cd: '', cd_nm: '' }], // 반기
        SH00000018: [{ cd: '', cd_nm: '' }], // 유해인자
        SH00000046: [{ cd: '', cd_nm: '' }], // 판정
      },
      selSH00000018: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000046: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      year: [],
      tgtYn: ['Y', 'N'],
      totalcnt: 0,
      excel: false,
      files: [],
      schData: {},
      frmData: {
        wem_range_dt: [],
      },
      tempData: {},
      menu1: false,
      menu2: false,
      editorOption: {
        // some quill options
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // toggled buttons
            ['blockquote', 'code-block'],

            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }], // outdent/indent

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ align: [] }],

            ['link', 'image'],

            ['clean'], // remove formatting button
          ],
        },
        placeholder: '',
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000961') // 작업환경측정 관리
    },
    // 측정기간 날짜 변경 시
    wemRangeDtChange() {
      const date = this.frmData.wem_range_dt
      if (date.length === 2) {
        const orderedDate = date.sort().join(' ~ ')
        this.wemRangeDtSet()
        return orderedDate
      }
      return ''
    },
    clickChk() {
      if (this.frmData.wem_range_dt.length === 2) {
        return true
      } else {
        return false
      }
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (this.modify && JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
  watch: {
    menu(val) {
      val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    sDate.setDate(eDate.getDate() - 7)
    this.frmData.wem_bgn_dt = this.get_date_str_gubun(sDate, '-')
    this.frmData.wem_end_dt = this.get_date_str_gubun(eDate, '-')
    this.frmData.wem_range_dt[0] = this.frmData.wem_bgn_dt
    this.frmData.wem_range_dt[1] = this.frmData.wem_end_dt
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']

    sDate = this.getTzDateObj(user.time_zone)
    eDate = this.getTzDateObj(user.time_zone)

    sDate.setDate(eDate.getDate() - 7)
    this.frmData.wem_bgn_dt = this.get_date_str_gubun(sDate, '-')
    this.frmData.wem_end_dt = this.get_date_str_gubun(eDate, '-')
    this.frmData.wem_year = this.get_date_str_gubun2(sDate, '-') + ''

    const yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    for (let y = yyyy; y >= 2020; y--) {
      this.year.push(String(y))
    }

    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = user.cmpn_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        res.SH00000018.forEach((i) => {
          this.selSH00000018.cd.push(i.cd)
          this.selSH00000018.cd_nm.push(i.cd_nm)
        })
        res.SH00000046.forEach((i) => {
          this.selSH00000046.cd.push(i.cd)
          this.selSH00000046.cd_nm.push(i.cd_nm)
        })

        if (this.$route.params.opemmgntdtledt !== null && this.$route.params.opemmgntdtledt !== undefined) {
          this.frmData.wem_id = this.$route.params.opemmgntdtledt
          this.schData.wem_id = this.$route.params.opemmgntdtledt
        } else {
          this.frmData.wem_id = ''
          this.schData.wem_id = ''
          this.frmData.wkpl_id = user.wkpl_id
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.wem_id = this.schData.wem_id
        }

        this.createGrid()

        // ID 존재할 경우
        if (this.frmData.wem_id) {
          this.modify = true
          this.select()
          this.$refs.quill.quill.enable(false)
        } else {
          const user = this.$store.getters['auth/getUser']
          this.frmData.lsch_nm = user.emp_nm
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    wemRangeDtSet() {
      this.frmData.wem_bgn_dt = this.frmData.wem_range_dt[0]
      this.frmData.wem_end_dt = this.frmData.wem_range_dt[1]
    },
    createGrid() {
      const fields = [
        {
          fieldName: 'wem_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_pl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_unit_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_msrm_vl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_exps_stnd_vl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_rslt_jgmt_cd',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000996'), // 대상부서
          name: 'wem_dept_nm',
          fieldName: 'wem_dept_nm',
          width: '200',
        },
        {
          header: this.$t('LB00000997'), // 측정장소
          name: 'wem_pl_nm',
          fieldName: 'wem_pl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000998'), // 유해인자
          name: 'hmfc_cd',
          fieldName: 'hmfc_cd',
          width: '100',
          values: this.selSH00000018.cd,
          labels: this.selSH00000018.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000470'), // 단위
          name: 'hmfc_unit_nm',
          fieldName: 'hmfc_unit_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000999'), // 측정값
          name: 'hmfc_msrm_vl',
          fieldName: 'hmfc_msrm_vl',
          width: '100',
          styleName: 'right',
          editor: {
            type: 'number',
            editFormat: '###0.###', // 소수는 3자리까지만 입력가능
            maxIntegerLength: 7, // 정수는 7자리까지만 입력가능
          },
        },
        {
          header: this.$t('LB00000320'), // 노출기준
          name: 'hmfc_exps_stnd_vl',
          fieldName: 'hmfc_exps_stnd_vl',
          width: '100',
          styleName: 'right',
          editor: {
            type: 'number',
            editFormat: '###0.###', // 소수는 3자리까지만 입력가능
            maxIntegerLength: 7, // 정수는 7자리까지만 입력가능
          },
        },
        {
          header: this.$t('LB00001000'), // 판정
          name: 'hmfc_rslt_jgmt_cd',
          fieldName: 'hmfc_rslt_jgmt_cd',
          width: '100',
          values: this.selSH00000046.cd,
          labels: this.selSH00000046.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
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
      gridView.editOptions.appendable = true

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 1 끝

      const columns1 = [
        {
          header: this.$t('LB00000996'), // 대상부서
          name: 'wem_dept_nm',
          fieldName: 'wem_dept_nm',
          width: '200',
        },
        {
          header: this.$t('LB00000997'), // 측정장소
          name: 'wem_pl_nm',
          fieldName: 'wem_pl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000998'), // 유해인자
          name: 'hmfc_cd',
          fieldName: 'hmfc_cd',
          width: '100',
          values: this.selSH00000018.cd,
          labels: this.selSH00000018.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000470'), // 단위
          name: 'hmfc_unit_nm',
          fieldName: 'hmfc_unit_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000999'), // 측정값
          name: 'hmfc_msrm_vl',
          fieldName: 'hmfc_msrm_vl',
          width: '100',
          styleName: 'right',
          editor: {
            maxLength: 7, // 정수는 7자리까지만 입력가능
          },
          numberFormat: '#,##0',
        },
        {
          header: this.$t('LB00000320'), // 노출기준
          name: 'hmfc_exps_stnd_vl',
          fieldName: 'hmfc_exps_stnd_vl',
          width: '100',
          styleName: 'right',
          editor: {
            type: 'number',
            editFormat: '###0.###', // 소수는 3자리까지만 입력가능
            maxIntegerLength: 7, // 정수는 7자리까지만 입력가능
          },
        },
        {
          header: this.$t('LB00001000'), // 판정
          name: 'hmfc_rslt_jgmt_cd',
          fieldName: 'hmfc_rslt_jgmt_cd',
          width: '100',
          values: this.selSH00000046.cd,
          labels: this.selSH00000046.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
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
      // 엑셀 양식 다운로드 그리드 끝
    },
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/hlth/opem/${this.frmData.wem_id}`).then((res) => {
        this.frmData.wem_id = res.wem_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.wem_year = res.wem_year
        this.frmData.wem_sn = res.wem_sn
        this.frmData.wem_cl_cd = res.wem_cl_cd
        this.frmData.wem_cyc_cd = res.wem_cyc_cd
        this.frmData.wem_bgn_dt = res.wem_bgn_dt
        this.frmData.wem_end_dt = res.wem_end_dt
        this.frmData.wem_orga_nm = res.wem_orga_nm
        this.frmData.wem_range_dt = []
        this.frmData.wem_range_dt[0] = this.frmData.wem_bgn_dt
        this.frmData.wem_range_dt[1] = this.frmData.wem_end_dt
        this.frmData.wem_req_emp_id = res.wem_req_emp_id
        this.frmData.wem_req_emp_nm = res.wem_req_emp_nm
        this.frmData.wem_oval_opin_txt = res.wem_oval_opin_txt
        this.frmData.wem_btrm_tgt_yn = res.wem_btrm_tgt_yn
        this.frmData.wem_btrm_plan_txt = res.wem_btrm_plan_txt
        this.frmData.wem_atfl_no = res.wem_atfl_no
        this.frmData.wem_prst_cd = res.wem_prst_cd
        this.frmData.wem_aprv_no = res.wem_aprv_no
        // this.frmData.lsch_nm = res.lsch_nm
        this.$set(this.frmData, 'lsch_nm', res.lsch_nm)

        // 의제
        dataProvider.setRows(res.rslt_list)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()

        if (this.frmData.wem_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.wem_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, true)
            }
          })
        }

        this.frmData.ckFlag = false
        this.frmData.ckFile = false
        this.tempData = JSON.parse(JSON.stringify(this.frmData))

        // 에디터 포커스 제거
        this.$nextTick(function () {
          this.$refs.quill.quill.enable(true)
          this.$refs.quill.quill.blur()
        })
      })
    },
    add() {
      this.frmData.ckFlag = true
      this.totalcnt++
      gridView.commit()
      dataProvider.addRow({
        hmfc_cd: '',
        hmfc_unit_nm: '',
        hmfc_msrm_vl: '',
        hmfc_exps_stnd_vl: '',
        hmfc_rslt_jgmt_cd: '',
        wem_dept_nm: '',
        wem_pl_nm: '',
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    save() {
      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          this.update()
        }
      } else {
        this.insert()
      }
    },
    async insert() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      await this.saveFiles(true)

      this.frmData.wem_prst_cd = '10' // 기본값 '작성중'
      if (!this.chkGrid()) {
        return
      }

      await this.$axios.post('/api/v1/she/hlth/opem', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wem_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async update() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.ckFile) {
        await this.saveFiles(true)
      }

      if (!this.chkGrid()) {
        return
      }

      await this.$axios.put('/api/v1/she/hlth/opem', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wem_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    chkGrid() {
      const arr = []
      this.frmData.rslt_list = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        const columns = dataProvider.getValues(i)
        for (let i = 0; i < columns.length; i++) {
          if (!columns[i]) {
            this.$toast.show(this.$t('MS00000476'), { className: 'toast_error' }) // 측정결과에 빈 데이터가 존재합니다.
            return false
          }
        }
        const rslt = {}
        rslt.hmfc_cd = dataProvider.getValue(i, 'hmfc_cd')
        rslt.hmfc_unit_nm = dataProvider.getValue(i, 'hmfc_unit_nm')
        rslt.hmfc_msrm_vl = dataProvider.getValue(i, 'hmfc_msrm_vl')
        rslt.hmfc_exps_stnd_vl = dataProvider.getValue(i, 'hmfc_exps_stnd_vl')
        rslt.hmfc_rslt_jgmt_cd = dataProvider.getValue(i, 'hmfc_rslt_jgmt_cd')
        rslt.wem_dept_nm = dataProvider.getValue(i, 'wem_dept_nm')
        rslt.wem_pl_nm = dataProvider.getValue(i, 'wem_pl_nm')
        this.frmData.rslt_list.push(rslt)
        arr.push(dataProvider.getValue(i, 'hmfc_cd')) // 중복 체크용 배열
      }

      // if (arr.some((e) => arr.indexOf(e) !== arr.lastIndexOf(e))) {
      //   this.$toast.show(this.$t('MS00000475'), { className: 'toast_error' }) // 측정결과에 중복된 유해인자가 존재합니다.
      //   return false
      // }

      return true
    },
    validate() {
      gridView.commit()

      if (!this.frmData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (!this.frmData.wem_req_emp_nm) {
        this.$toast.show(this.$t('MS00000477'), { className: 'toast_error' }) // 의뢰자를 입력해주세요.
        this.$refs.wem_req_emp_nm.focus()
        return false
      }
      if (!this.frmData.wem_cl_cd) {
        this.$toast.show(this.$t('MS00000478'), { className: 'toast_error' }) // 측정구분을 선택해주세요.
        this.$refs.wem_cl_cd.focus()
        return false
      }
      if (!this.frmData.wem_year) {
        this.$toast.show(this.$t('MS00000479'), { className: 'toast_error' }) // 측정연도를 선택해주세요.
        this.$refs.wem_year.focus()
        return false
      }
      if (!this.frmData.wem_cyc_cd) {
        this.$toast.show(this.$t('MS00000480'), { className: 'toast_error' }) // 반기를 선택해주세요.
        this.$refs.wem_cyc_cd.focus()
        return false
      }
      if (!this.frmData.wem_orga_nm) {
        this.$toast.show(this.$t('MS00000481'), { className: 'toast_error' }) // 측정기관을 입력해주세요.
        this.$refs.wem_orga_nm.focus()
        return false
      }
      if (this.frmData.wem_orga_nm.length > 100) {
        this.$toast.show(this.$t('MS00000482'), { className: 'toast_error' }) // 측정기관을 100자 이하로 입력해주세요.
        this.$refs.wem_orga_nm.focus()
        return false
      }
      if (!this.frmData.wem_btrm_tgt_yn) {
        this.$toast.show(this.$t('MS00000483'), { className: 'toast_error' }) // 개선대상 여부를 선택해주세요.
        this.$refs.wem_btrm_tgt_yn.focus()
        return false
      }
      if (!this.frmData.wem_oval_opin_txt) {
        this.$toast.show(this.$t('MS00000484'), { className: 'toast_error' }) // 종합의견을 입력해주세요.
        this.$refs.wem_oval_opin_txt.focus()
        return false
      }
      if (this.frmData.wem_range_dt.length !== 2) {
        this.$toast.show(this.$t('MS00000668'), { className: 'toast_error' }) // 측정기간을 입력해주세요.
        this.$refs.wem_range_dt.focus()
        return false
      }
      if (this.frmData.wem_oval_opin_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000485'), { className: 'toast_error' }) // 종합의견을 1000자 이하로 입력해주세요.
        this.$refs.wem_oval_opin_txt.focus()
        return false
      }

      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (!dataProvider.getValue(i, 'wem_dept_nm')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wem_dept_nm' })
          this.$toast.show(this.$t('MS00000486'), { className: 'toast_error' }) // 대상부서를 입력해주세요.
          return
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'wem_dept_nm')) && dataProvider.getValue(i, 'wem_dept_nm').length > 100) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wem_dept_nm' })
          this.$toast.show(this.$t('MS00000487'), { className: 'toast_error' }) // 대상부서는 100자를 초과할 수 없습니다.
          return false
        }
        if (!dataProvider.getValue(i, 'wem_pl_nm')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wem_pl_nm' })
          this.$toast.show(this.$t('MS00000488'), { className: 'toast_error' }) // 측정장소를 입력해주세요.
          return
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'wem_pl_nm')) && dataProvider.getValue(i, 'wem_pl_nm').length > 100) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wem_pl_nm' })
          this.$toast.show(this.$t('MS00000489'), { className: 'toast_error' }) // 측정장소는 100자를 초과할 수 없습니다.
          return false
        }
        if (!dataProvider.getValue(i, 'hmfc_cd')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_cd' })
          this.$toast.show(this.$t('MS00000490'), { className: 'toast_error' }) // 유해인자를 입력해주세요.
          return
        }
        if (!dataProvider.getValue(i, 'hmfc_unit_nm')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_unit_nm' })
          this.$toast.show(this.$t('MS00000491'), { className: 'toast_error' }) // 단위를 입력해주세요.
          return
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'hmfc_unit_nm')) && dataProvider.getValue(i, 'hmfc_unit_nm').length > 20) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_unit_nm' })
          this.$toast.show(this.$t('MS00000492'), { className: 'toast_error' }) // 단위는 20자를 초과할 수 없습니다.
          return false
        }
        if (!dataProvider.getValue(i, 'hmfc_msrm_vl').trim()) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_msrm_vl' })
          this.$toast.show(this.$t('MS00000493'), { className: 'toast_error' }) // 측정값을 입력해주세요.
          return
        }
        if (!dataProvider.getValue(i, 'hmfc_exps_stnd_vl').trim()) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_exps_stnd_vl' })
          this.$toast.show(this.$t('MS00000494'), { className: 'toast_error' }) // 노출기준을 입력해주세요.
          return
        }
        if (!dataProvider.getValue(i, 'hmfc_rslt_jgmt_cd')) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hmfc_rslt_jgmt_cd' })
          this.$toast.show(this.$t('MS00000495'), { className: 'toast_error' }) // 판정을 입력해주세요.
          return
        }
      }
      return true
    },
    async appr() {
      if (!this.validate()) {
        return
      }

      // 측정 결과
      // if (dataProvider.getRowCount() === 0) {
      //   this.$toast.show(this.$t('MS00000580'), { className: 'toast_error' }) // 측정 결과를 등록해주세요.
      //   return false
      // }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          // this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          if (this.frmData.ckFile) {
            await this.saveFiles(true)
          }

          if (!this.chkGrid()) {
            return
          }

          await this.$axios.put('/api/v1/she/hlth/opem', this.frmData).then((res) => {
            if (res.data !== 'Fail') {
              this.frmData.wem_id = res.data
              this.schData.wem_id = res.data
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      } else {
        await this.saveFiles(true)

        this.frmData.wem_prst_cd = '10' // 기본값 '작성중'
        if (!this.chkGrid()) {
          return
        }

        await this.$axios.post('/api/v1/she/hlth/opem', this.frmData).then((res) => {
          if (res.data !== 'Fail') {
            this.frmData.wem_id = res.data
            this.schData.wem_id = res.data
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }

      this.modify = false

      // this.frmData.wem_prst_cd = '1' // 결재상신
      this.frmData.type = 'appr'

      await this.$axios.put('/api/v1/she/hlth/opem', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl10 + aprvno + empno
          window.open(url, 'opem', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `opem-opemmgntdtllup-opemmgntdtllup`,
            params: {
              schData: this.schData,
              opemmgntdtllup: this.schData.wem_id,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
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
    get_date_str_gubun2(date) {
      const sYear = date.getFullYear()
      return sYear
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'wem_req_emp_nm', returnData[0].emp_nm)
        this.$set(this.frmData, 'wem_req_emp_id', returnData[0].emp_no)
      }
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `opem-opemmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
    async saveFiles(mode) {
      await this.$refs.upload.uploadStart().then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data) {
          this.frmData.wem_atfl_no = data
          // this.$axios.$get(`/api/v1/common/file/${this.frmData.wem_atfl_no}`).then((data) => {
          //   console.log('files : ', data) // eslint-disable-line no-console
          //   if (data.length > 0) {
          //     this.setFiles(data, mode)
          //   }
          // })
        }
      })
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.frmData.wem_year = dd
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'hidden'
      const header = 'visible'
      const footer = 'hidden'
      // 작업환경측정결과_업로드양식
      const fileName = this.$t('MS00000496') + '.xlsx'

      gridView1.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'),
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
            o.wem_dept_nm = o.대상부서
            o.wem_pl_nm = o.측정장소
            o.hmfc_cd = o.유해인자
            o.hmfc_unit_nm = o.단위
            o.hmfc_msrm_vl = o.측정값
            o.hmfc_exps_stnd_vl = o.노출기준
            o.hmfc_rslt_jgmt_cd = o.판정
          })
          console.log('roa : ', roa) // eslint-disable-line no-console
          result[sheetName] = roa
        }
      })
      return this.excelCodeSet(result)
    },
    excelCodeSet(result) {
      for (let i = 0; i < result.sheet1.length; i++) {
        let orgVal = result.sheet1[i].hmfc_cd
        let idx = this.selSH00000018.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selSH00000018.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmfc_cd = cdVal

        orgVal = result.sheet1[i].hmfc_rslt_jgmt_cd
        idx = this.selSH00000046.cd_nm.indexOf(orgVal)
        if (idx > -1) {
          cdVal = this.selSH00000046.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].hmfc_rslt_jgmt_cd = cdVal
      }
      return result
    },
  },
}
</script>
<style></style>
