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
              <v-stepper-step step="2." complete> {{ $t('유해,위험요인 파악') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="3." :editable="frmData.dgsn_apal_drup_stp_cd >= 3" @click="frmData.dgsn_apal_drup_stp_cd >= 3 && save('3')">
                {{ $t('위험성 추정,결정') }}
              </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="4." :editable="frmData.dgsn_apal_drup_stp_cd >= 4" @click="frmData.dgsn_apal_drup_stp_cd >= 4 && save('4')">
                {{ $t('감소대책 수립') }}
              </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="5."> {{ $t('평가완료') }} </v-stepper-step>
            </v-stepper-header>
          </v-stepper>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px"></span>
          {{ $t('2단계 : 세부작업별 유해위험요인을 파악하여 작성 합니다.') }}
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
      <div id="realgrid" style="width: 100%; height: 380px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save('')">{{ $t('LB00000045') }}</v-btn>
          <!--이전단계-->
          <v-btn depressed width="120" color="success" height="40" @click="save('pre')">{{ $t('이전단계') }}</v-btn>
          <!--다음단계-->
          <v-btn depressed width="120" color="success" height="40" @click="save('next')">{{ $t('다음단계') }}</v-btn>
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
      codeid: ['SH00000080', 'SH00000081'],
      selSH00000080: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000081: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
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
        res.SH00000081.forEach((i) => {
          this.selSH00000081.cd.push(i.cd)
          this.selSH00000081.cd_nm.push(i.cd_nm)
        })

        this.user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자(RZZZZ) + 환경안전부서(R0005)
        this.user.roles.forEach((i) => {
          if (i === 'RZZZZ' || i === 'R0005') {
            this.isAdminGrp = true
          }
        })
        this.createGrid()

        if (this.$route.params.riskevalharmdngftedt) {
          this.frmData.dgsn_apal_id = this.$route.params.riskevalharmdngftedt
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
          width: '150',
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
          header: this.$t('유해위험요인'), // 유해위험요인
          name: 'hmfl_risk_fact_cd',
          fieldName: 'hmfl_risk_fact_cd',
          width: '100',
          values: this.selSH00000081.cd,
          labels: this.selSH00000081.cd_nm,
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
          width: '150',
          placeHolder: this.$t('적재물 무너짐에 의한 맞음/깔림 위험'), // 적재물 무너짐에 의한 맞음/깔림 위험
          placeHolderStyleName: 'place-holder',
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

      gridView.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
        this.isStep = false
      }.bind(this)
    },
    async select() {
      const param = {}
      param.dgsn_apal_id = this.frmData.dgsn_apal_id
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
      if (this.frmData.mode !== 'U' && !this.isAdminGrp) {
        const urlParam = {}
        urlParam.schData = this.schData
        urlParam.riskevalharmdngftlup = this.frmData.dgsn_apal_id
        this.$router.push({
          name: 'riskeval-riskevalharmdngftlup-riskevalharmdngftlup',
          params: urlParam,
        })
      } else {
        const rows = await this.$axios.$get(`/api/v1/she/riskeval/tgtdtlwk`, { params: param })
        dataProvider.setRows(rows)
        gridView.refresh()
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          if (!dataProvider.getValue(i, 'risk_occr_rslt_txt')) {
            dataProvider.setRowState(i, 'updated')
          }
        }
        this.isStep = true
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
        } else if (step === 'pre') {
          this.pre()
        } else if (step === 'next') {
          this.next()
        } else {
          this.goStep(step)
        }
        return
      }
      const thisStep = 2
      if ((step > thisStep || step === 'next') && !this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.dgsn_apal_drup_stp_cd === '1') {
        this.frmData.dgsn_apal_drup_stp_cd = '2'
      }
      this.frmData.dgsn_apal_prst_cd = 'E01'

      this.frmData.insertList = []
      this.frmData.updateList = []
      this.frmData.deleteList = []

      let rows = dataProvider.getStateRows('updated')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.risk_occr_rslt_txt = dataProvider.getValue(rows[i], 'risk_occr_rslt_txt')
        obj.dgsn_apal_dtl_id = dataProvider.getValue(rows[i], 'dgsn_apal_dtl_id')
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

      this.$axios.put('/api/v1/she/riskeval/harmdngft', this.frmData).then((res) => {
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
            this.next()
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
        if (!dataProvider.getValue(rows[i], 'risk_occr_rslt_txt')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'risk_occr_rslt_txt' })
          this.$toast.show(this.$t('위험발생 상황 및 결과를 입력해주세요'), { className: 'toast_error' }) // 위험발생 상황 및 결과를 입력해주세요.
          return false
        }
      }
      return true
    },
    pre() {
      this.$router.push({
        name: `riskeval-riskevaltgtdtlwkedt-riskevaltgtdtlwkedt`,
        params: {
          schData: this.schData,
          riskevaltgtdtlwkedt: this.frmData.dgsn_apal_id,
        },
      })
    },
    next() {
      if (gridView.getItemCount() === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      this.$router.push({
        name: `riskeval-riskevalriskestmtedt-riskevalriskestmtedt`,
        params: {
          schData: this.schData,
          riskevalriskestmtedt: this.frmData.dgsn_apal_id,
        },
      })
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
  },
}
</script>
<style>
.place-holder {
  color: rgb(188, 188, 188);
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
