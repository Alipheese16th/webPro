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
              <v-stepper-step step="1." editable @click="goStep('1')"> {{ $t('평가대상 세부작업 선정') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="2." editable @click="goStep('2')"> {{ $t('유해,위험요인 파악') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="3." editable @click="goStep('3')"> {{ $t('위험성 추정,결정') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="4." editable @click="goStep('4')"> {{ $t('감소대책 수립') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="5." complete> {{ $t('평가완료') }} </v-stepper-step>
            </v-stepper-header>
          </v-stepper>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px"></span>
          {{ $t('5단계 : 감소대책에 대한 세부계획을 수립하고 실행 내용을 작성합니다.') }}
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
        <div></div>
      </div>
      <div id="realgrid" style="width: 100%; height: 380px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--이전단계-->
          <v-btn depressed width="120" color="success" height="40" @click="pre">{{ $t('이전단계') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '위험성평가 등록/조회',
    key(route) {
      return `/riskeval/${route.params.catalog}`
    },
  },
  components: {},
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

        this.createGrid()

        if (this.$route.params.riskevalcmpllup) {
          this.frmData.dgsn_apal_id = this.$route.params.riskevalcmpllup
          this.select()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
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
        },
        {
          header: this.$t('위험발생 상황 및 결과'), // 위험발생 상황 및 결과
          name: 'risk_occr_rslt_txt',
          fieldName: 'risk_occr_rslt_txt',
          width: '100',
          editable: false,
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
        },
        {
          header: this.$t('위험성'), // 위험성
          name: 'dgsn_apal_txt',
          fieldName: 'dgsn_apal_txt',
          width: '50',
          editable: false,
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
          editable: false,
        },
        {
          header: {
            text: this.$t('개선후\n위험성'), // 개선후\n위험성
            styleName: 'multiline-editor',
          },
          name: 'btt_af_dgsn_apal_txt',
          fieldName: 'btt_af_dgsn_apal_txt',
          width: '50',
          editable: false,
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
          editable: false,
        },
        {
          header: this.$t('조치완료일'), // 조치완료일
          name: 'dgsn_btt_cmpn_schd_dt',
          fieldName: 'dgsn_btt_cmpn_schd_dt',
          width: '50',
          editable: false,
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
      gridView.editOptions.editable = false
      gridView.editOptions.appendable = false
      gridView.sortingOptions.enabled = false
      gridView.rowIndicator.displayValue = 'row'
    },
    async select() {
      const param = {}
      param.dgsn_apal_id = this.frmData.dgsn_apal_id
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
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
      }
      this.isStep = true
    },
    pre() {
      this.$router.push({
        name: `riskeval-riskevalriskrdtplanlup-riskevalriskrdtplanlup`,
        params: {
          schData: this.schData,
          riskevalriskrdtplanlup: this.frmData.dgsn_apal_id,
        },
      })
    },
    goStep(step) {
      let url = ''
      const param = {}
      param.schData = this.schData
      switch (step) {
        case '0':
          url = 'riskeval-riskevalbasiclup-riskevalbasiclup'
          param.riskevalbasiclup = this.frmData.dgsn_apal_id
          break
        case '1':
          url = 'riskeval-riskevaltgtdtlwklup-riskevaltgtdtlwklup'
          param.riskevaltgtdtlwklup = this.frmData.dgsn_apal_id
          break
        case '2':
          url = 'riskeval-riskevalharmdngftlup-riskevalharmdngftlup'
          param.riskevalharmdngftlup = this.frmData.dgsn_apal_id
          break
        case '3':
          url = 'riskeval-riskevalriskestmtlup-riskevalriskestmtlup'
          param.riskevalriskestmtlup = this.frmData.dgsn_apal_id
          break
        case '4':
          url = 'riskeval-riskevalriskrdtplanlup-riskevalriskrdtplanlup'
          param.riskevalriskrdtplanlup = this.frmData.dgsn_apal_id
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
