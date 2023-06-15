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
              <v-stepper-step step="1." complete> {{ $t('평가대상 세부작업 선정') }} </v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="2." :editable="frmData.dgsn_apal_drup_stp_cd >= 2" @click="frmData.dgsn_apal_drup_stp_cd >= 2 && save('2')">
                {{ $t('유해,위험요인 파악') }}
              </v-stepper-step>
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
          {{ $t('1단계 : 위험성평가 대상 공정과 세부작업 목록을 작성 합니다.') }}
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
              <v-col cols="1">
                <!--공정선택(세부공정)-->
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('공정선택') }}</div>
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('(세부공정)') }}</div>
              </v-col>
              <v-col cols="3">
                <v-row style="border-bottom: 0">
                  <v-col cols="9">
                    <v-select
                      ref="smcl_dngp_id"
                      v-model="frmData.smcl_dngp_id"
                      label="SELECT"
                      item-text="dngp_nm"
                      item-value="dngp_id"
                      :items="levels.LEVEL3"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      height="30"
                      return-object
                      @change="showFlag = frmData.smcl_dngp_id.dngp_id === 'add'"
                    ></v-select>
                    <br v-show="showFlag" />
                    <v-text-field
                      v-show="showFlag"
                      ref="new_dngp_nm"
                      v-model="frmData.new_dngp_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      height="30"
                      autofocus
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-btn style="display: block" outlined height="30" @click="row_add">{{ $t('LB00000047') }}</v-btn>
                    <br v-show="showFlag" />
                    <v-btn v-show="showFlag" color="primary" style="box-shadow: none" height="30" @click="subcategory_save">{{ $t('저장') }}</v-btn>
                  </v-col>
                </v-row>
                <v-row style="border-bottom: 0">
                  <p v-show="showFlag" style="margin-bottom: 0; color: blue">*주의: 신규 공정 추가/저장시, 위험성평가 시스템 공정 관리 항목에 반영 됩니다.</p>
                </v-row>
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
          <!-- 위로 이동 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="up_move">{{ $t('위로 이동') }}</v-btn>
          <!-- 아래로 이동 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="down_move">{{ $t('아래로 이동') }}</v-btn>
          <!-- 복사 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="copy">{{ $t('행복사') }}</v-btn>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add">{{ $t('행추가') }}</v-btn>
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
      moveCheck: false,
      isStep: false,
      codeid: ['SH00000080', 'SH00000081'],
      SH00000081: [],
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
      levels: {
        LEVEL3: [{ dngp_id: '', dngp_nm: '' }],
      },
      showFlag: false,

      level1Data: { id: 'level1Data', levels: 1, keys: [''], values: ['SELECT'] },
      level2Data: { id: 'level2Data', levels: 2, tags: [], keys: [], values: [] },
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

    this.user = this.$store.getters['auth/getUser']
    // 시스템그룹 사용자(RZZZZ) + 환경안전부서(R0005)
    this.user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdminGrp = true
      }
    })
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        res.SH00000080.forEach((i) => {
          this.level1Data.keys.push(i.cd)
          this.level1Data.values.push(i.cd_nm)

          res.SH00000081.forEach((j) => {
            if (i.cd === j.buf_1st_cntn) {
              this.level2Data.tags.push(j.cd)
              this.level2Data.keys.push([i.cd, j.cd])
              this.level2Data.values.push(j.cd_nm)
            }
          })
        })
        this.SH00000081 = res.SH00000081
        // res.SH00000081.forEach((i) => {
        //   this.selSH00000081.cd.push(i.cd)
        //   this.selSH00000081.cd_nm.push(i.cd_nm)
        // })
        this.createGrid()

        if (this.$route.params.riskevaltgtdtlwkedt) {
          this.frmData.dgsn_apal_id = this.$route.params.riskevaltgtdtlwkedt
          this.select()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    async getDngpData() {
      const obj = {}
      obj.usg_yn = 'Y'
      obj.upp_dngp_id = this.frmData.mdcl_dngp_id
      this.dngpData = await this.$axios.$get(`/api/v1/she/riskeval/wkplprcsmgnt/subcategory`, { params: obj })
      this.chgLevel3()
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
          fieldName: 'smcl_dngp_cd',
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
          fieldName: 'hmfl_risk_fact_nm',
          dataType: ValueType.TEXT,
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
          placeHolder: this.$t('원자재 00을 하차'), // 원자재 00을 하차
          placeHolderStyleName: 'place-holder',
        },
        {
          header: this.$t('도구/설비'), // 도구/설비
          name: 'dgsn_apal_tgt_eqp_nm',
          fieldName: 'dgsn_apal_tgt_eqp_nm',
          width: '120',
          placeHolder: this.$t('화물자동차(5톤)'), // 화물자동차(5톤)
          placeHolderStyleName: 'place-holder',
        },
        {
          header: this.$t('물질'), // 물질
          name: 'dgsn_apal_tgt_mtra_nm',
          fieldName: 'dgsn_apal_tgt_mtra_nm',
          width: '130',
          placeHolder: this.$t('원자재 00물질'), // 원자재 00물질
          placeHolderStyleName: 'place-holder',
        },
        {
          header: this.$t('위험분류'), // 위험분류
          name: 'risk_cat_cd',
          fieldName: 'risk_cat_cd',
          width: '100',
          lookupDisplay: true,
          values: this.level1Data.keys,
          labels: this.level1Data.values,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('유해위험요인'), // 유해위험요인
          name: 'hmfl_risk_fact_cd',
          fieldName: 'hmfl_risk_fact_cd',
          width: '150',
          lookupDisplay: true,
          lookupSourceId: 'level2Data',
          lookupKeyFields: ['risk_cat_cd', 'hmfl_risk_fact_cd'],
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
          renderer: {
            showTooltip: true,
          },
        },
      ]
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      let options = {}
      options = dataProvider.getOptions()
      options.softDeleting = true
      dataProvider.setOptions(options)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true, showGroup: false })
      gridView.groupPanel.visible = false
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
      gridView.editOptions.movable = false
      gridView.sortingOptions.enabled = false
      gridView.rowIndicator.displayValue = 'row'
      gridView.hideDeletedRows = true
      gridView.addLookupSource(this.level2Data)

      gridView.onEditCommit = function (grid, index, oldValue, newValue) {
        if (index.fieldName === 'risk_cat_cd') {
          if (oldValue !== newValue) {
            grid.setValue(index.itemIndex, 'hmfl_risk_fact_cd', '')
          }
        }
      }
    },
    async select() {
      const param = {}
      param.dgsn_apal_id = this.frmData.dgsn_apal_id
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
      if (this.frmData.mode !== 'U' && !this.isAdminGrp) {
        const urlParam = {}
        urlParam.schData = this.schData
        urlParam.riskevaltgtdtlwklup = this.frmData.dgsn_apal_id
        this.$router.push({
          name: 'riskeval-riskevaltgtdtlwklup-riskevaltgtdtlwklup',
          params: urlParam,
        })
      } else {
        const rows = await this.$axios.$get(`/api/v1/she/riskeval/tgtdtlwk`, { params: param })
        this.getDngpData()
        dataProvider.setRows(rows)
        gridView.refresh()
      }
    },
    up_move() {
      gridView.commit()
      const row = gridView.getCheckedItems(false)
      if (row.length !== 1) {
        this.$toast.show(this.$t('이동할 항목을 하나만 선택해주세요.'), { className: 'toast_error' }) // 이동할 항목을 하나만 선택해주세요.
        return
      }
      const checkedRows = gridView.getCheckedRows()
      const item2 = gridView.getModelOfRow(checkedRows[0])
      const parent = gridView.getParentModel(item2, true)
      const children = gridView.getChildModels(parent, true)
      if (children[0].itemIndex < row) {
        const targetRow = gridView.getDataRow(gridView.getItemIndex(checkedRows[0]) - 1)
        dataProvider.moveRow(checkedRows[0], targetRow)
      }
      this.moveCheck = true
    },
    down_move() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length !== 1) {
        this.$toast.show(this.$t('이동할 항목을 하나만 선택해주세요.'), { className: 'toast_error' }) // 이동할 항목을 하나만 선택해주세요.
        return
      }

      const row = gridView.getCheckedItems()
      const item = gridView.getModelOfRow(checkedRows[0])
      if (item.type === 'row') {
        const parent = gridView.getParentModel(item, true)
        const children = gridView.getChildModels(parent, true)
        if (children[children.length - 2].itemIndex > row) {
          dataProvider.moveRow(checkedRows[0], checkedRows[0] + 1)
        }
      }
      this.moveCheck = true
    },
    row_add() {
      gridView.commit()
      if (!this.frmData.smcl_dngp_id || this.frmData.smcl_dngp_id.dngp_id === 'add' || this.frmData.smcl_dngp_id.dngp_id === '') {
        this.$toast.show(this.$t('세부공정을 선택해주세요.'), { className: 'toast_error' }) // 세부공정을 선택해주세요.
        this.$refs.smcl_dngp_id.focus()
        return false
      }
      const rows = dataProvider.getRows()
      let row = 0
      for (let i = rows.length; i > 0; i--) {
        const ssdn = dataProvider.getValue(i - 1, 'smcl_dngp_nm')
        if (ssdn === this.frmData.smcl_dngp_id.dngp_nm) {
          row = i
          break
        }
      }

      dataProvider.insertRow(row, {
        smcl_dngp_nm: this.frmData.smcl_dngp_id.dngp_nm,
        smcl_dngp_cd: this.frmData.smcl_dngp_id.dngp_id,
        dgsn_apal_dtl_oprn_txt: '',
        dgsn_apal_tgt_eqp_nm: '',
        dgsn_apal_tgt_mtra_nm: '',
        risk_cat_cd: '',
        hmfl_risk_fact_cd: '',
      })
    },
    async subcategory_save() {
      if (this.frmData.smcl_dngp_id.dngp_id === 'add' && !this.frmData.new_dngp_nm) {
        this.$toast.show(this.$t('신규 세부공정명을 입력해주세요.'), { className: 'toast_error' }) // 신규 세부공정명을 입력해주세요.
        this.$refs.new_dngp_nm.focus()
        return false
      }

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      this.frmData.smcl_dngp_id = this.frmData.smcl_dngp_id.dngp_id
      this.frmData.dngp_id = await this.$axios.$get(`/api/v1/she/riskeval/wkplprcsmgnt/0`)
      this.frmData.subcategory = '1'
      this.$axios.put('/api/v1/she/riskeval/wkplprcsmgnt/subcategory', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.smcl_dngp_id = { dngp_id: this.frmData.dngp_id, dngp_nm: this.frmData.new_dngp_nm }
          this.frmData.new_dngp_nm = ''
          this.getDngpData()
          this.showFlag = false
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
      gridView.commit()
      gridView.refresh()
    },
    add() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length !== 1) {
        this.$toast.show(this.$t('추가할 항목을 하나만 선택해주세요.'), { className: 'toast_error' }) // 추가할 항목을 하나만 선택해주세요.
        return
      }

      dataProvider.insertRow(gridView.getDataRow(gridView.getCheckedItems()) + 1, {
        smcl_dngp_nm: dataProvider.getValue(checkedRows[0], 'smcl_dngp_nm'),
        smcl_dngp_cd: dataProvider.getValue(checkedRows[0], 'smcl_dngp_cd'),
        dgsn_apal_dtl_oprn_txt: '',
        dgsn_apal_tgt_eqp_nm: '',
        dgsn_apal_tgt_mtra_nm: '',
        risk_cat_cd: '',
        hmfl_risk_fact_cd: '',
      })
    },
    copy() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length !== 1) {
        this.$toast.show(this.$t('복사할 항목을 하나만 선택해주세요.'), { className: 'toast_error' }) // 복사할 항목을 하나만 선택해주세요.
        return
      }
      dataProvider.insertRow(gridView.getDataRow(gridView.getCheckedItems()) + 1, {
        smcl_dngp_nm: dataProvider.getValue(checkedRows[0], 'smcl_dngp_nm'),
        smcl_dngp_cd: dataProvider.getValue(checkedRows[0], 'smcl_dngp_cd'),
        dgsn_apal_dtl_oprn_txt: dataProvider.getValue(checkedRows[0], 'dgsn_apal_dtl_oprn_txt'),
        dgsn_apal_tgt_eqp_nm: dataProvider.getValue(checkedRows[0], 'dgsn_apal_tgt_eqp_nm'),
        dgsn_apal_tgt_mtra_nm: dataProvider.getValue(checkedRows[0], 'dgsn_apal_tgt_mtra_nm'),
        risk_cat_cd: '',
        hmfl_risk_fact_cd: '',
      })
    },
    async del() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length === 0) {
        this.$toast.show(this.$t('삭제할 항목을 선택해주세요.'), { className: 'toast_error' }) // 삭제할 항목을 선택해주세요.
        return
      }
      const res = await this.$confirm(this.$t('이전단계, 다음단계에서 작성한 내용도 모두 삭제 됩니다.')) // 이전단계, 다음단계에서 작성한 내용도 모두 삭제 됩니다.
      if (!res) {
        return
      }
      dataProvider.removeRows(checkedRows)
    },
    async save(step) {
      gridView.commit()
      const thisStep = 1
      if ((step > thisStep || step === 'next') && !this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.dgsn_apal_drup_stp_cd === '0') {
        this.frmData.dgsn_apal_drup_stp_cd = '1'
      }
      this.frmData.dgsn_apal_prst_cd = 'E01'

      this.frmData.insertList = []
      this.frmData.updateList = []
      this.frmData.deleteList = []
      let sn = 1
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        const obj = {}
        obj.sort_sn = sn
        obj.dgsn_apal_dtl_id = dataProvider.getValue(i, 'dgsn_apal_dtl_id')
        obj.dgsn_apal_id = this.frmData.dgsn_apal_id
        obj.smcl_dngp_nm = dataProvider.getValue(i, 'smcl_dngp_nm')
        obj.smcl_dngp_cd = dataProvider.getValue(i, 'smcl_dngp_cd')
        obj.dgsn_apal_dtl_oprn_txt = dataProvider.getValue(i, 'dgsn_apal_dtl_oprn_txt')
        obj.dgsn_apal_tgt_eqp_nm = dataProvider.getValue(i, 'dgsn_apal_tgt_eqp_nm')
        obj.dgsn_apal_tgt_mtra_nm = dataProvider.getValue(i, 'dgsn_apal_tgt_mtra_nm')
        obj.risk_cat_cd = dataProvider.getValue(i, 'risk_cat_cd')
        obj.hmfl_risk_fact_cd = dataProvider.getValue(i, 'hmfl_risk_fact_cd')
        if (dataProvider.getRowState(i) === 'created') {
          this.frmData.insertList.push(obj)
          sn++
        } else if (dataProvider.getRowState(i) === 'updated' || dataProvider.getRowState(i) === 'none') {
          this.frmData.updateList.push(obj)
          sn++
        } else if (dataProvider.getRowState(i) === 'deleted') {
          this.frmData.deleteList.push(obj)
        }
      }
      this.$axios.put('/api/v1/she/riskeval/tgtdtlwk', this.frmData).then((res) => {
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
      rows.push(...dataProvider.getStateRows('none'))
      for (let i = 0; i < rows.length; i++) {
        if (!dataProvider.getValue(rows[i], 'smcl_dngp_nm')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'smcl_dngp_nm' })
          this.$toast.show(this.$t('세부작업을 입력해주세요'), { className: 'toast_error' }) // 세부작업을 입력해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'smcl_dngp_cd')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'smcl_dngp_cd' })
          this.$toast.show(this.$t('세부작업코드가 입려되지 않았습니다.'), { className: 'toast_error' }) // 세부작업코드가 입려되지 않았습니다.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'dgsn_apal_dtl_oprn_txt')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_apal_dtl_oprn_txt' })
          this.$toast.show(this.$t('상세작업설명을 입력해주세요'), { className: 'toast_error' }) // 상세작업설명을 입력해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'dgsn_apal_tgt_eqp_nm')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_apal_tgt_eqp_nm' })
          this.$toast.show(this.$t('도구/설비를 입력해주세요'), { className: 'toast_error' }) // 도구/설비를 입력해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'dgsn_apal_tgt_mtra_nm')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dgsn_apal_tgt_mtra_nm' })
          this.$toast.show(this.$t('물질을 입력해주세요'), { className: 'toast_error' }) // 물질을 입력해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'risk_cat_cd')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'risk_cat_cd' })
          this.$toast.show(this.$t('위험분류를 선택해주세요'), { className: 'toast_error' }) // 위험분류를 선택해주세요.
          return false
        }
        if (!dataProvider.getValue(rows[i], 'hmfl_risk_fact_cd')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'hmfl_risk_fact_cd' })
          this.$toast.show(this.$t('유해위험요인을 선택해주세요'), { className: 'toast_error' }) // 유해위험요인을 선택해주세요.
          return false
        }
      }
      return true
    },
    pre() {
      this.$router.push({
        name: `riskeval-riskevalbasicedt-riskevalbasicedt`,
        params: {
          schData: this.schData,
          riskevalbasicedt: this.frmData.dgsn_apal_id,
        },
      })
    },
    next() {
      if (gridView.getItemCount() === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      this.$router.push({
        name: `riskeval-riskevalharmdngftedt-riskevalharmdngftedt`,
        params: {
          schData: this.schData,
          riskevalharmdngftedt: this.frmData.dgsn_apal_id,
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
    chgLevel3(isNew) {
      this.levels.LEVEL3 = this.dngpData
      this.levels.LEVEL3.unshift({ dngp_id: '', dngp_nm: 'SELECT' })
      if (isNew) {
        this.frmData.smcl_dngp_id = ''
        this.showFlag = false
      }
      if (this.frmData.mdcl_dngp_id !== '') {
        this.levels.LEVEL3.push({ dngp_id: 'add', dngp_nm: '직접 작성' })
      }
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
