<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 위험성평가 등록/조회-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('위험성평가 등록/조회') }}</span>
    </div>
    <div class="work_title">
      <!-- 위험성평가 등록/조회 -->
      <h2>{{ $t('위험성평가 등록/조회') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 위험성평가 실시단계 -->
        <h3>{{ $t('위험성평가 실시단계') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-stepper class="stepper_css">
            <v-stepper-header>
              <v-stepper-step step="1." editable @click="save('1')"> {{ $t('평가대상 세부작업 선정') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="2." editable @click="save('2')"> {{ $t('유해,위험요인 파악') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="3." editable @click="save('3')"> {{ $t('위험성 추정,결정') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="4." complete> {{ $t('감소대책 수립') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="5."> {{ $t('평가완료') }} </v-stepper-step>
            </v-stepper-header>
          </v-stepper>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px"></span>
          {{ $t('4단계 : 3단계에서 추정된 위험성이 허용가능한지를 결정하고 위험성을 낮추기 위한 감소대책을 수립 합니다.') }}
        </v-col>
      </v-row>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 평가대상 공정 -->
        <h3>{{ $t('평가대상 공정') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--대분류-->
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('대분류') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lrcl_dngp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--공정명-->
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('공정명') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mdcl_dngp_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <br />
    <div class="realgrid_container">
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left"></div>
        <div>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('행삭제') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 300px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title" style="display: flex; padding-top: 0; border-bottom: 1px solid #444">
        <!--첨부파일 : 위험성평가 참여인원 (서명지 증빙)-->
        <h3 style="padding-top: 3px">{{ $t('첨부파일 : 위험성평가 참여인원 (서명지 증빙)') }}</h3>
        <v-spacer></v-spacer>
        <div>
          <!--양식 다운로드-->
          <v-btn style="margin-top: 1px" color="" outlined height="28" @click="formDown2">{{ $t('LB00000721') }}</v-btn>
        </div>
      </div>

      <div class="container_filelist">
        <div v-if="true">
          <file-upload ref="upload" :multiple="true" @onDrop="chkFile2()" />
        </div>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--양식 다운로드
          <v-btn depressed width="120" color="primary" height="40" @click="formDown">{{ $t('LB00000721') }}</v-btn>-->
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save('')">{{ $t('LB00000045') }}</v-btn>
          <!--이전단계-->
          <v-btn depressed width="120" color="success" height="40" @click="save('pre')">{{ $t('이전단계') }}</v-btn>
          <!--다음단계-->
          <v-btn depressed width="120" color="success" height="40" :disabled="frmData.dgsn_apal_prst_cd !== 'E01'" @click="save('next')">{{ $t('검토/실시 보고') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="riskrdtplan1" mkey2="riskrdtplan2" @close="popclose" />
    <filePopup ref="filepop" mkey1="riskrdtplan1" :edit-chk="false" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import filePopup from '../../common/atflgrid'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '위험성평가 등록/조회',
    key(route) {
      return `/riskeval/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
    popup,
    filePopup,
  },
  data() {
    return {
      isStep: false,
      codeid: ['SH00000080', 'SH00000082', 'SH00000083', 'SH00000084'],
      selSH00000080: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000082: {
        cd: ['0'],
        cd_nm: ['SELECT'],
      },
      selSH00000083: {
        cd: ['0'],
        cd_nm: ['SELECT'],
      },
      SH00000084: [],
      menu1: false,
      schData: {},
      frmData: {},
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('위험성평가 등록/조회') // 위험성평가 등록/조회
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (!this.isStep && typeof dataProvider === 'object' && dataProvider.getRowStateCount(['created', 'updated', 'deleted'])) {
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
  mounted() {
    // 이전 검색 결과 저장
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    }

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        res.SH00000080.forEach((i) => {
          this.selSH00000080.cd.push(i.cd)
          this.selSH00000080.cd_nm.push(i.cd_nm)
        })
        res.SH00000082.forEach((i) => {
          this.selSH00000082.cd.push(i.cd)
          this.selSH00000082.cd_nm.push(i.cd_nm)
        })
        res.SH00000083.forEach((i) => {
          this.selSH00000083.cd.push(i.cd)
          this.selSH00000083.cd_nm.push(i.cd_nm)
        })
        this.SH00000084 = res.SH00000084

        this.user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자(RZZZZ) + 환경안전부서(R0005)
        this.user.roles.forEach((i) => {
          if (i === 'RZZZZ' || i === 'R0005') {
            this.isAdminGrp = true
          }
        })
        this.createGrid()

        if (this.$route.params.riskevalriskrdtplanedt) {
          this.frmData.dgsn_apal_id = this.$route.params.riskevalriskrdtplanedt
          this.select()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    createGrid() {
      const fields = [
        {
          fieldName: 'dgsn_apal_dtl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'smcl_dngp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_dtl_oprn_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_dtl_oprn_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_tgt_eqp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_tgt_mtra_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfl_risk_fact_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_occr_rslt_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cur_sfhe_actn_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_occr_psbl_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'risk_occr_main_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_apal_level',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_dec_meas_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_risk_occr_psbl_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_risk_occr_main_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_scr',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'btt_af_dgsn_apal_level',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_rspn_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_rspn_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dgsn_btt_cmpn_schd_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sort_sn',
          dataType: ValueType.INT,
        },
      ]

      const columns = [
        {
          header: this.$t('세부공정'), // 세부공정
          name: 'smcl_dngp_nm',
          fieldName: 'smcl_dngp_nm',
          visible: false,
        },
        {
          header: this.$t('세부작업설명'), // 세부작업설명
          name: 'dgsn_apal_dtl_oprn_txt',
          fieldName: 'dgsn_apal_dtl_oprn_txt',
          width: '100',
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: this.$t('위험분류'), // 위험분류
          name: 'risk_cat_cd',
          fieldName: 'risk_cat_cd',
          width: '100',
          values: this.selSH00000080.cd,
          labels: this.selSH00000080.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: this.$t('위험발생 상황 및 결과'), // 위험발생 상황 및 결과
          name: 'risk_occr_rslt_txt',
          fieldName: 'risk_occr_rslt_txt',
          width: '100',
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: this.$t('현재의 안전보건 조치'), // 현재의 안전보건 조치
          name: 'cur_sfhe_actn_txt',
          fieldName: 'cur_sfhe_actn_txt',
          width: '100',
          editor: {
            type: 'multiline',
          },
          styleName: 'multiline-editor',
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: {
            text: this.$t('가능성\n(빈도)'), // 가능성\n(빈도)
            styleName: 'multiline-editor',
          },
          name: 'risk_occr_psbl_scr',
          fieldName: 'risk_occr_psbl_scr',
          width: '40',
          values: this.selSH00000082.cd,
          labels: this.selSH00000082.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: {
            text: this.$t('중대성\n(강도)'), // 중대성\n(강도)
            styleName: 'multiline-editor',
          },
          name: 'risk_occr_main_scr',
          fieldName: 'risk_occr_main_scr',
          width: '40',
          values: this.selSH00000083.cd,
          labels: this.selSH00000083.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          editable: false,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: this.$t('위험성'), // 위험성
          name: 'dgsn_apal_txt',
          fieldName: 'dgsn_apal_txt',
          width: '40',
          editable: false,
          renderer: {
            showTooltip: true,
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const level = grid.getValue(dataCell.index.itemIndex, 'dgsn_apal_level')

            if (level === 'VL') {
              ret.styleName = 'level1'
            } else if (level === 'L') {
              ret.styleName = 'level2'
            } else if (level === 'H') {
              ret.styleName = 'level3'
            } else if (level === 'VH') {
              ret.styleName = 'level4'
            }

            return ret
          },
        },
        {
          header: this.$t('위험성 감소대책'), // 위험성 감소대책
          name: 'dgsn_dec_meas_txt',
          fieldName: 'dgsn_dec_meas_txt',
          width: '150',
          editor: {
            type: 'multiline',
          },
          styleName: 'multiline-editor',
          placeHolder: this.$t('1. 지게차 동선과 작업자 동선 분리'), // 1. 지게차 동선과 작업자 동선 분리
          placeHolderStyleName: 'place-holder',
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: {
            text: this.$t('가능성\n(빈도)'), // 가능성\n(빈도)
            styleName: 'multiline-editor',
          },
          name: 'btt_af_risk_occr_psbl_scr',
          fieldName: 'btt_af_risk_occr_psbl_scr',
          width: '40',
          values: this.selSH00000082.cd,
          labels: this.selSH00000082.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: {
            text: this.$t('중대성\n(강도)'), // 중대성\n(강도)
            styleName: 'multiline-editor',
          },
          name: 'btt_af_risk_occr_main_scr',
          fieldName: 'btt_af_risk_occr_main_scr',
          width: '40',
          values: this.selSH00000083.cd,
          labels: this.selSH00000083.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: {
            text: this.$t('개선후\n위험성'), // 개선후\n위험성
            styleName: 'multiline-editor',
          },
          name: 'btt_af_dgsn_apal_txt',
          fieldName: 'btt_af_dgsn_apal_txt',
          width: '40',
          editable: false,
          renderer: {
            showTooltip: true,
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const level = grid.getValue(dataCell.index.itemIndex, 'btt_af_dgsn_apal_level')

            if (level === 'VL') {
              ret.styleName = 'level1'
            } else if (level === 'L') {
              ret.styleName = 'level2'
            } else if (level === 'H') {
              ret.styleName = 'level3'
            } else if (level === 'VH') {
              ret.styleName = 'level4'
            }

            return ret
          },
        },
        {
          header: this.$t('조치담당자'), // 조치담당자
          name: 'dgsn_btt_rspn_emp_nm',
          fieldName: 'dgsn_btt_rspn_emp_nm',
          width: '70',
          button: 'action',
          alwaysShowButton: true,
          renderer: {
            showTooltip: true,
          },
        },
        {
          header: this.$t('조치예정일'), // 조치예정일
          name: 'dgsn_btt_cmpn_schd_dt',
          fieldName: 'dgsn_btt_cmpn_schd_dt',
          width: '50',
          editor: {
            type: 'date',
            datetimeFormat: 'yyyy-MM-dd',
          },
          styles: {
            textAlignment: 'center',
            datetimeFormat: 'yyyy-MM-dd',
          },
          renderer: {
            showTooltip: true,
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
      gridView.setCheckBar({ visible: true, showGroup: false })
      gridView.groupBy(['smcl_dngp_nm'], false)
      gridView.setRowGroup({
        mergeMode: false,
        summaryMode: 'aggregate',
        expandedAdornments: 'header',
        collapsedAdornments: 'header',
        // eslint-disable-next-line no-template-curly-in-string
        headerStatement: '${groupValue}',
        mergeExpanderVisibility: false,
      })

      gridView.header.height = 50
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = true
      gridView.editOptions.appendable = false
      gridView.sortingOptions.enabled = false
      gridView.rowIndicator.displayValue = 'row'

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.column === 'dgsn_btt_rspn_emp_nm') {
          grid.commit()
          const schData = {}
          this.$refs.pop.open(schData, false)
        }
      }.bind(this)

      gridView.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
        this.isStep = false
        if (dataProvider.getFieldName(field) === 'BTT_AF_RISK_OCCR_PSBL_SCR' || dataProvider.getFieldName(field) === 'BTT_AF_RISK_OCCR_MAIN_SCR') {
          const v1 = grid.getValue(itemIndex, 'btt_af_risk_occr_psbl_scr')
          const v2 = grid.getValue(itemIndex, 'btt_af_risk_occr_main_scr')
          const v = v1 * v2
          if (!v) {
            return
          }
          this.SH00000084.forEach((i) => {
            if (i.buf_1st_cntn <= v && i.buf_2nd_cntn >= v) {
              grid.setValue(itemIndex, 'btt_af_dgsn_apal_scr', v)
              grid.setValue(itemIndex, 'btt_af_dgsn_apal_txt', i.cd_nm)
              grid.setValue(itemIndex, 'btt_af_dgsn_apal_level', i.cd)
            }
          })
          // console.log('btt_af_dgsn_apal_scr: ' + grid.getValue(itemIndex, 'btt_af_dgsn_apal_scr')) // eslint-disable-line no-console
        }
      }.bind(this)
    },
    async select() {
      const param = {}
      param.dgsn_apal_id = this.frmData.dgsn_apal_id
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
      if (this.frmData.mode !== 'U' && !this.isAdminGrp) {
        const urlParam = {}
        urlParam.schData = this.schData
        urlParam.riskevalriskrdtplanlup = this.frmData.dgsn_apal_id
        this.$router.push({
          name: 'riskeval-riskevalriskrdtplanlup-riskevalriskrdtplanlup',
          params: urlParam,
        })
      } else {
        const rows = await this.$axios.$get(`/api/v1/she/riskeval/tgtdtlwk`, { params: param })
        dataProvider.setRows(rows)
        gridView.refresh()
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const v = Number(dataProvider.getValue(i, 'dgsn_apal_scr'))
          const v2 = Number(dataProvider.getValue(i, 'btt_af_dgsn_apal_scr'))
          this.SH00000084.forEach((scr) => {
            if (scr.buf_1st_cntn <= v && scr.buf_2nd_cntn >= v) {
              dataProvider.setValue(i, 'dgsn_apal_txt', scr.cd_nm)
              dataProvider.setValue(i, 'dgsn_apal_level', scr.cd)
              dataProvider.setRowState(i, 'none')
            }

            if (scr.buf_1st_cntn <= v2 && scr.buf_2nd_cntn >= v2) {
              dataProvider.setValue(i, 'btt_af_dgsn_apal_txt', scr.cd_nm)
              dataProvider.setValue(i, 'btt_af_dgsn_apal_level', scr.cd)
              dataProvider.setRowState(i, 'none')
            }
          })

          if (!dataProvider.getValue(i, 'dgsn_dec_meas_txt')) {
            dataProvider.setRowState(i, 'updated')
          }
        }
        this.isStep = true

        if (this.frmData.dgsn_apal_sgn_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.dgsn_apal_sgn_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, true)
              this.chkFile()
            }
          })
        } else {
          this.frmData.dgsn_apal_sgn_atfl_no = 0
        }
      }
    },
    async del() {
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('삭제할 항목을 선택해주세요.'), { className: 'toast_error' }) // 삭제할 항목을 선택해주세요.
        return
      }
      const res = await this.$confirm(this.$t('이전단계, 다음단계에서 작성한 내용도 모두 삭제 됩니다.')) // 이전단계, 다음단계에서 작성한 내용도 모두 삭제 됩니다.
      if (!res) {
        return
      }
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    async save(step) {
      gridView.commit()
      if (!dataProvider.getRowStateCount(['created', 'updated', 'deleted'])) {
        if (!step) {
          this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
          return
        } else if (step === 'pre') {
          this.pre()
          return
        } else if (step === 'next') {
        } else {
          this.goStep(step)
          return
        }
      }

      const thisStep = 4
      if ((step > thisStep || step === 'next') && !this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.dgsn_apal_drup_stp_cd = '4'
      this.frmData.dgsn_apal_prst_cd = 'E01'

      this.frmData.insertList = []
      this.frmData.updateList = []
      this.frmData.deleteList = []

      let rows = dataProvider.getStateRows('updated')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.dgsn_apal_dtl_id = dataProvider.getValue(rows[i], 'dgsn_apal_dtl_id')
        obj.dgsn_dec_meas_txt = dataProvider.getValue(rows[i], 'dgsn_dec_meas_txt')
        obj.btt_af_risk_occr_psbl_scr = dataProvider.getValue(rows[i], 'btt_af_risk_occr_psbl_scr')
        obj.btt_af_risk_occr_main_scr = dataProvider.getValue(rows[i], 'btt_af_risk_occr_main_scr')
        obj.btt_af_dgsn_apal_scr = dataProvider.getValue(rows[i], 'btt_af_dgsn_apal_scr')
        obj.dgsn_btt_rspn_emp_id = dataProvider.getValue(rows[i], 'dgsn_btt_rspn_emp_id')
        obj.dgsn_btt_cmpn_schd_dt = dataProvider.getValue(rows[i], 'dgsn_btt_cmpn_schd_dt')
        this.frmData.updateList.push(obj)
      }
      // console.log('updated is ' + JSON.stringify(this.frmData.updateList)) // eslint-disable-line no-console

      rows = dataProvider.getStateRows('deleted')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.dgsn_apal_dtl_id = dataProvider.getValue(rows[i], 'dgsn_apal_dtl_id')
        this.frmData.deleteList.push(obj)
      }
      // console.log('deleted is ' + JSON.stringify(this.frmData.deleteList)) // eslint-disable-line no-console

      if (this.frmData.ckFile) {
        await this.saveFiles(true)
      } else {
        this.frmData.dgsn_apal_sgn_atfl_no = 0
      }
      this.$axios.put('/api/v1/she/riskeval/riskrdtplan', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          if (!step) {
            this.isStep = false
            this.select()
          } else if (step === 'pre') {
            this.isStep = true
            this.pre()
          } else if (step === 'next') {
            this.isStep = true
            this.select()
            this.appr()
          } else {
            this.isStep = true
            this.goStep(step)
          }
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      gridView.commit()
      const rows = dataProvider.getStateRows('created')
      rows.push(...dataProvider.getStateRows('updated'))

      for (let i = 0; i < rows.length; i++) {
        if (!dataProvider.getValue(rows[i], 'dgsn_dec_meas_txt')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_dec_meas_txt' })
          this.$toast.show(this.$t('위험성 감소대책을 입력해주세요'), { className: 'toast_error' }) // 위험성 감소대책을 입력해주세요.
          return false
        }
        if (!Number(dataProvider.getValue(rows[i], 'btt_af_risk_occr_psbl_scr'))) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'btt_af_risk_occr_psbl_scr' })
          this.$toast.show(this.$t('가능성(빈도)을 선택해주세요'), { className: 'toast_error' }) // 가능성(빈도)을 선택해주세요.
          return false
        }
        if (!Number(dataProvider.getValue(rows[i], 'btt_af_risk_occr_main_scr'))) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'btt_af_risk_occr_main_scr' })
          this.$toast.show(this.$t('중대성(강도)을 선택해주세요'), { className: 'toast_error' }) // 중대성(강도)을 선택해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'dgsn_btt_rspn_emp_nm')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_btt_rspn_emp_nm' })
          this.$toast.show(this.$t('조치담당자를 입력해주세요'), { className: 'toast_error' }) // 조치담당자를 입력해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'dgsn_btt_cmpn_schd_dt')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_btt_cmpn_schd_dt' })
          this.$toast.show(this.$t('조치예정일을 선택해주세요'), { className: 'toast_error' }) // 조치예정일을 선택해주세요.
          return false
        }
      }

      this.chkFile()
      if (!this.frmData.ckFile) {
        this.$toast.show(this.$t('위험성평가 참여인원(서명지) 첨부파일 등록 후 ‘검토/실시 보고’ 가능합니다.'), { className: 'toast_error' }) // 위험성평가 참여인원(서명지) 첨부파일 등록 후 ‘검토/실시 보고’ 가능합니다.
        return false
      }
      return true
    },
    pre() {
      this.$router.push({
        name: `riskeval-riskevalriskestmtedt-riskevalriskestmtedt`,
        params: {
          schData: this.schData,
          riskevalriskestmtedt: this.frmData.dgsn_apal_id,
        },
      })
    },
    async appr() {
      /*
      if (this.frmData.profiles === 'local') {
        this.$toast.show(this.$t('로컬에서는 사용이 불가합니다.'), { className: 'toast_error' }) // 로컬에서는 파일업로드가 안되어 저장은 개발에서 해야함
        return false
      }
      */
      const res = await this.$confirm(
        this.$t(
          '<p align="left">작성된 위험성평가를 사업장 안전부서 담당자에게 검토 요청 하시겠습니까?</p><p align="left" style="color:blue;">*결재선 지정 : 안전부서 담당자(합의) → 소속 부서장, 팀장(승인)<br/>*위험성평가 검토/실시보고 완료 후 위험성 감소대책을 일정 내 완료 바랍니다.</p>'
        )
      ) // <p align="left">작성된 위험성평가를 사업장 안전부서 담당자에게 검토 요청 하시겠습니까?</p><p align="left" style="color:blue;">*결재 Process : 안전부서 검토(합의) → 소속 부서장, 팀장(승인)<br/>*위험성평가 검토/실시보고 완료 후 위험성 감소대책을 일정 내 완료 바랍니다.</p>
      if (!res) {
        // this.select()
        return
      }

      this.$axios.post('/api/v1/she/riskeval/riskrdtplan', this.frmData).then((res) => {
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
          const url = config.default.approvalUrl14 + aprvno + empno
          window.open(url, 'riskrdtplan', options)
          this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `riskeval-riskevalriskrdtplanlup-riskevalriskrdtplanlup`,
            params: {
              schData: this.schData,
              riskevalriskrdtplanlup: this.frmData.dgsn_apal_id,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popclose(returnData) {
      if (returnData && returnData.length > 0) {
        const current = gridView.getCurrent()
        gridView.setValue(current.itemIndex, 'dgsn_btt_rspn_emp_id', returnData[0].emp_no)
        gridView.setValue(current.itemIndex, 'dgsn_btt_rspn_emp_nm', returnData[0].dept_nm + ' ' + returnData[0].emp_nm)
      }
    },
    goStep(step) {
      let url = ''
      const param = {}
      param.schData = this.schData
      switch (step) {
        case '0':
          url = 'riskeval-riskevalbasicedt-riskevalbasicedt'
          param.riskevalbasicedt = this.frmData.dgsn_apal_id
          break
        case '1':
          url = 'riskeval-riskevaltgtdtlwkedt-riskevaltgtdtlwkedt'
          param.riskevaltgtdtlwkedt = this.frmData.dgsn_apal_id
          break
        case '2':
          url = 'riskeval-riskevalharmdngftedt-riskevalharmdngftedt'
          param.riskevalharmdngftedt = this.frmData.dgsn_apal_id
          break
        case '3':
          url = 'riskeval-riskevalriskestmtedt-riskevalriskestmtedt'
          param.riskevalriskestmtedt = this.frmData.dgsn_apal_id
          break
        case '4':
          url = 'riskeval-riskevalriskrdtplanedt-riskevalriskrdtplanedt'
          param.riskevalriskrdtplanedt = this.frmData.dgsn_apal_id
          break
        case '5':
          url = 'riskeval-riskevalcmpllup-riskevalcmpllup'
          param.riskevalcmpllup = this.frmData.dgsn_apal_id
          break
      }

      this.$router.push({
        name: url,
        params: param,
      })
    },
    goList() {
      gridView.commit()
      this.$router.push({
        name: `riskeval-riskevallist`,
        params: {
          schData: this.schData,
        },
      })
    },
    async formDown() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '69' // doc id
      await this.$axios.get('/api/v1/common/arch-file', { params: this.schData }).then((res) => {
        // eslint-disable-next-line no-console
        if (res.data) {
          // 조회해온 file ID로 팝업 띄우기
          this.schData.atfile_no = res.data
          this.$refs.filepop.open(this.schData)
        } else {
          this.$toast.show(this.$t('MS00000347'), { className: 'toast_error' }) // 첨부파일이 존재하지 않습니다.
        }
      })
    },
    chkFile2() {
      this.frmData.fileDrop = true
      this.chkFile()
    },
    chkFile() {
      if (this.$refs.upload.files.length > 0) {
        this.frmData.ckFile = true
      } else {
        this.frmData.ckFile = false
      }
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
    async saveFiles(mode) {
      this.uploadFiles = this.$refs.upload.files
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data > 0) {
          this.frmData.dgsn_apal_sgn_atfl_no = data
          this.$refs.upload.files = this.uploadFiles
        } else {
          this.frmData.dgsn_apal_sgn_atfl_no = 0
        }
      })
    },
    async formDown2() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '1090' // doc id
      await this.$axios.get('/api/v1/common/arch-file', { params: this.schData }).then((res) => {
        // eslint-disable-next-line no-console
        if (res.data) {
          // 조회해온 file ID로 팝업 띄우기
          this.schData.atfile_no = res.data
          this.$refs.filepop.open(this.schData)
        } else {
          this.$toast.show(this.$t('MS00000347'), { className: 'toast_error' }) // 첨부파일이 존재하지 않습니다.
        }
      })
    },
  },
}
</script>
<style>
.place-holder {
  color: rgb(188, 188, 188);
}
.level1 {
  background: rgb(0, 128, 0);
  color: rgb(255, 255, 255);
}
.level2 {
  background: rgb(76, 175, 80);
  color: rgb(255, 255, 255);
}
.level3 {
  background: rgb(248, 128, 48);
  color: rgb(255, 255, 255);
}
.level4 {
  background: rgb(255, 0, 0);
  color: rgb(255, 255, 255);
}
.multiline-editor {
  white-space: pre;
}
.rg-rowgroup-bar-cell {
  background: #f9f9f9;
}
.rg-rowgroup-layer .rg-rowgroup-header {
  background: #ebebeb;
}
.rg-rowgroup-footer-cell {
  background: #fff;
}
.rg-rowgroup-header > div {
  font-size: 14px;
  font-weight: bold;
  padding-left: 10px;
}
.rg-header-renderer {
  font-size: 14px;
}
.rg-rowindicator-head {
  font-size: 14px;
}
</style>
