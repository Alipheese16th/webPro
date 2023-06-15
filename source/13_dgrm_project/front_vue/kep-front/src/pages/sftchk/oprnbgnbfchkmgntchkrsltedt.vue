<template>
  <v-dialog v-model="dialog" persistent width="1420px" :eager="true">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 점검결과 -->
        <v-toolbar-title>{{ $t('LB00000646') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--점검계획일-->
                <div class="label_tit">{{ $t('LB00001379') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <div class="label_con">{{ frmData.oprb_chk_schd_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 점검일자 -->
                <div class="label_tit essn">{{ $t('LB00000786') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="400px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="oprb_chk_dt"
                      v-model="frmData.oprb_chk_dt"
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
                  <v-date-picker v-model="frmData.oprb_chk_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!-- 점검 시간 -->
                <div class="label_tit essn">{{ $t('LB00000793') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex; background: #fff">
                <v-select
                  ref="oprb_chk_hm_h"
                  v-model="frmData.oprb_chk_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="oprb_chk_hm_s"
                  v-model="frmData.oprb_chk_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="codes.SH00000075"
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
                <!-- 관리 감독자 -->
                <div class="label_tit essn">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <v-text-field
                  ref="oprb_chk_rspn_emp_nm"
                  v-model="frmData.oprb_chk_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  append-icon="fas fa-search"
                  readonly
                  @click:append="popOpen('OPRB')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 부관리 감독자 -->
                <div class="label_tit essn">{{ $t('LB00001275') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <v-text-field
                  ref="oprb_chk_spic_emp_nm"
                  v-model="frmData.oprb_chk_spic_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  append-icon="fas fa-search"
                  readonly
                  @click:append="popOpen('OPRBS')"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </div>
        <div class="group_padd"></div>
        <div class="realgrid_container">
          <div id="oprnchkrsltpop" style="width: 100%; height: 281px"></div>
        </div>
        <div class="group_padd"></div>
        <div v-show="crinYn" id="crinTitle2" class="realgrid_container">
          <div class="sub_title">
            <!--시정지시 등록-->
            <h3>{{ $t('LB00000794') }}</h3>
          </div>
        </div>
        <div v-show="crinYn" id="crinText2" class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 개선 희망일 -->
                <div class="label_tit essn">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="actn_hope_dt"
                      v-model="frmData.actn_hope_dt"
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
                  <v-date-picker v-model="frmData.actn_hope_dt" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!-- 조치 담당자 -->
                <div class="label_tit essn">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="crin_actn_schd_emp_nm"
                  v-model="frmData.crin_actn_schd_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="poptOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험 유형-->
                <div class="label_tit essn">{{ $t('LB00000672') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="crin_risk_tp_cd"
                  v-model="frmData.crin_risk_tp_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000063"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위험유형2-->
                <div class="label_tit essn">{{ $t('LB00000672') }}2</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="crin_risk_tp_cd_2"
                  v-model="frmData.crin_risk_tp_cd_2"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000068"
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
                <!--시정지시 내용-->
                <div class="label_tit essn">{{ $t('LB00000799') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="crin_txt" v-model="frmData.crin_txt" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 사진-->
                <div class="label_tit essn">{{ $t('LB00000800') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop" />
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 완료 -->
        <v-btn color="primary" depressed width="120" height="40" @click="comp()">{{ $t('LB00000802') }}</v-btn>
        <!-- 저장 -->
        <v-btn color="primary" depressed width="120" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
    <popup ref="pop" mkey1="oprnbgnbfchkrsltuser1" mkey2="oprnbgnbfchkrsltuser2" @close="popClose" />
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import picpreview from '../common/picpreview'

let crinYn = false
export default {
  components: {
    // FileUpload,
    popup,
    picpreview,
  },
  props: {
    mkey: {
      type: String,
      default: 'oprnchkrsltpop',
    },
  },
  data() {
    return {
      gridView: '',
      dataProvider: '',
      dialog: false,
      valid: true,
      schData: {},
      frmData: {},
      codeid: ['SH00000063', 'SH00000068', 'SH00000074', 'SH00000075'],
      codes: {
        SH00000063: [{ cd: '', cd_nm: '' }],
        SH00000068: [{ cd: '', cd_nm: '' }],
        SH00000074: [{ cd: '', cd_nm: '' }],
      },
      mm5: [
        { cd: '00', cd_nm: '00' },
        { cd: '05', cd_nm: '05' },
        { cd: '10', cd_nm: '10' },
        { cd: '15', cd_nm: '15' },
        { cd: '20', cd_nm: '20' },
        { cd: '25', cd_nm: '25' },
        { cd: '30', cd_nm: '30' },
        { cd: '35', cd_nm: '35' },
        { cd: '40', cd_nm: '40' },
        { cd: '45', cd_nm: '45' },
        { cd: '50', cd_nm: '50' },
        { cd: '55', cd_nm: '55' },
      ],
      menu1: false,
      menu2: false,
      menu3: false,
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
      crinYn: false,
      tempLabel: ['SELECT', '양호', '불량'],
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.SH00000063.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000068.unshift({ cd: '', cd_nm: 'SELECT' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'oprn_bf_chec_cl_cd', // 구분
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_bf_chec_dtl_cl_cd', // 상세
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_bf_chec_sn', // 순번
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chec_lang_cd', // 언어 코드
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_bf_chec_cl_cd_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_bf_chec_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_bf_chec_in_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chec_chk_rslt_vl',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000741'), // 구분
        name: 'oprn_bf_chec_cl_cd_nm',
        fieldName: 'oprn_bf_chec_cl_cd_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000642'), // 점검내용
        name: 'oprn_bf_chec_txt',
        fieldName: 'oprn_bf_chec_txt',
        width: '400',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000646'), // 점검결과
        name: 'chec_chk_rslt_vl',
        fieldName: 'chec_chk_rslt_vl',
        width: '200',
        editable: true,
        styleCallback(grid, dataCell) {
          const gubun = grid.getValue(dataCell.index.itemIndex, 'oprn_bf_chec_in_cl_cd')
          const ret = {}

          switch (gubun) {
            // 구분값이 T이면 text에디터를 표시
            case 'IN01':
              ret.editor = {
                type: 'text',
              }
              ret.editable = true
              break
            // 구분값이 D이면 드롭다운 에디터를 표시
            case 'IN02':
              ret.editor = {
                type: 'dropdown',
                values: ['', 'Y', 'N'],
                labels: ['SELECT', '양호', '불량'],
                domainOnly: true,
                textReadOnly: true,
              }
              ret.editable = true
              break
            case 'IN03':
              ret.editor = {
                type: 'dropdown',
                values: ['', 'Y', 'N'],
                labels: ['SELECT', 'Y', 'N'],
                domainOnly: true,
                textReadOnly: true,
              }
              ret.editable = true
          }
          return ret
        },
        displayCallback(grid, index, value) {
          let retValue = value
          const gubun = grid.getValue(index.itemIndex, 'oprn_bf_chec_in_cl_cd')

          if (gubun === 'IN02') {
            const idx = ['', 'Y', 'N'].indexOf(value)

            retValue = ['SELECT', '양호', '불량'][idx]
          }

          return retValue
        },
      },
    ]

    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    // 그리드 ID를 조회하여 동적으로 수정해주는 부분
    const gridId = document.getElementById('oprnchkrsltpop')
    gridId.id = this.mkey + 'oprnchkrsltpop'
    this.gridView = new GridView(this.mkey + 'oprnchkrsltpop')

    this.gridView.setDataSource(this.dataProvider)
    this.gridView.setColumns(columns)
    this.gridView.setFooters({ visible: false })

    this.gridView.setStateBar({ visible: false })
    this.gridView.setCheckBar({ visible: false })
    this.gridView.editOptions.editable = false
    this.gridView.displayOptions.selectionStyle = 'singleRow'

    this.gridView.header.height = 39
    this.gridView.displayOptions.rowHeight = 40
    this.gridView.footer.height = 40
    this.gridView.displayOptions.fitStyle = 'fill'
    this.gridView.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
      grid.commit()
      // eslint-disable-next-line no-console
      const dom1 = document.getElementById('crinTitle2')
      const dom2 = document.getElementById('crinText2')

      if (grid.getValue(itemIndex, 'oprn_bf_chec_in_cl_cd') === 'IN01') return
      if (newValue === 'N') {
        if (dom1.style.display === 'none') {
          dom1.style.display = 'block'
        }
        if (dom2.style.display === 'none') {
          dom2.style.display = 'block'
        }
        crinYn = true
      } else {
        // 그리드 내에 상태가 N인 값이 존재하는지 아닌지 체크한다.
        for (let i = 0; i < grid.getItemCount(); i++) {
          const chk = grid.getValue(i, 'chec_chk_rslt_vl')
          if (chk === 'N' && grid.getValue(i, 'oprn_bf_chec_in_cl_cd') !== 'IN01') {
            return
          }
        }
        dom1.style.display = 'none'
        dom2.style.display = 'none'
        crinYn = false
      }
    }

    this.gridView.setColumnProperty('id', 'visible', false)
  },
  updated() {},
  methods: {
    allowedStep: (m) => m % 5 === 0,
    setDefaultValue() {
      this.gridView.commit()
      for (let i = 0; i < this.dataProvider.getRowCount(); i++) {
        // 구분 값이 텍스트 입력이 아닐때, 값이 공백일때
        if (this.dataProvider.getValue(i, 'oprn_bf_chec_in_cl_cd') !== 'IN01' && this.dataProvider.getValue(i, 'chec_chk_rslt_vl') === '') {
          // 해당 컬럼의 default 값을 Y로 설정한다.
          this.dataProvider.setValue(i, 'chec_chk_rslt_vl', 'Y')
        }
      }
      this.gridView.commit()
    },
    open(key) {
      this.$refs.picPop.setEdit(true)
      this.schData.oprb_chk_id = key // 조회 키 설정
      this.frmData.oprb_chk_id = key
      this.dialog = true
      this.gridView.refresh()

      const user = this.$store.getters['auth/getUser']

      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      this.date = this.getTzDateObj(user.time_zone)
      this.schData.oprb_chk_dt = this.get_date_str_gubun(this.date, '-').substring(0, 7)

      this.select()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      crinYn = false
      const dom1 = document.getElementById('crinTitle2')
      const dom2 = document.getElementById('crinText2')
      dom1.style.display = 'none'
      dom2.style.display = 'none'
      this.frmData = {}
      this.schData = {}
      this.$refs.picPop.reset()
    },
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
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
            const tempData = JSON.parse(JSON.stringify(this.frmData))
            tempData.oprb_chk_rspn_emp_id = i.emp_no
            tempData.oprb_chk_rspn_emp_nm = i.emp_nm
            this.frmData = tempData
          } else if (this.userSwitch === 'OPRBS') {
            const tempData = JSON.parse(JSON.stringify(this.frmData))
            tempData.oprb_chk_spic_emp_id = i.emp_no
            tempData.oprb_chk_spic_emp_nm = i.emp_nm
            this.frmData = tempData
          } else {
            this.$set(this.frmData, 'crin_actn_schd_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'crin_actn_schd_emp_id', i.emp_no)
          }
        })
      }
    },
    poptOpen() {
      this.userSwitch = 'CRIN'
      this.$refs.pop.open(this.frmData, false)
    },
    validate() {
      if (this.isEmpty(this.frmData.oprb_chk_dt)) {
        this.$toast.show(this.$t('MS00000270'), { className: 'toast_error' }) // 점검 일자를 선택해주세요.
        this.$refs.oprb_chk_dt.focus()
        return false
      } else if (this.isEmpty(this.frmData.oprb_chk_hm_h) || this.isEmpty(this.frmData.oprb_chk_hm_s)) {
        this.$toast.show(this.$t('MS00000271'), { className: 'toast_error' }) // 점검 시간을 선택해주세요.
        this.$refs.oprb_chk_hm_h.focus()
        return false
      } else if (this.isEmpty(this.frmData.oprb_chk_rspn_emp_nm)) {
        this.$toast.show(this.$t('MS00000314'), { className: 'toast_error' }) // 관리 감독자를 선택해주세요.
        this.$refs.oprb_chk_rspn_emp_nm.focus()
        return false
      } else if (this.isEmpty(this.frmData.oprb_chk_spic_emp_nm)) {
        this.$toast.show(this.$t('MS00000677'), { className: 'toast_error' }) // 부관리 감독자를 선택해주세요.
        this.$refs.oprb_chk_spic_emp_nm.focus()
        return false
      }
      if (crinYn) {
        // 시정지시서 입력이 필요할 경우
        if (this.isEmpty(this.frmData.actn_hope_dt)) {
          this.$toast.show(this.$t('MS00000261'), { className: 'toast_error' }) // 개선 희망일을 입력해주세요.
          this.$refs.actn_hope_dt.focus()
          return false
        } else if (this.isEmpty(this.frmData.crin_risk_tp_cd)) {
          this.$toast.show(this.$t('MS00000261'), { className: 'toast_error' }) // 위험 유형을 선택해주세요.
          this.$refs.crin_risk_tp_cd.focus()
          return false
        } else if (this.isEmpty(this.frmData.crin_risk_tp_cd_2)) {
          this.$toast.show(this.$t('MS00000263'), { className: 'toast_error' }) // 위험 유형을 선택해주세요.
          this.$refs.crin_risk_tp_cd_2.focus()
          return false
        } else if (this.isEmpty(this.frmData.crin_actn_schd_emp_nm)) {
          this.$toast.show(this.$t('MS00000264'), { className: 'toast_error' }) // 조치 담당자를 선택해주세요.
          this.$refs.crin_actn_schd_emp_nm.focus()
          return false
        } else if (this.isEmpty(this.frmData.crin_txt)) {
          this.$toast.show(this.$t('MS00000265'), { className: 'toast_error' }) // 시정 지시 내용을 입력해주세요.
          this.$refs.crin_txt.focus()
          return false
        } else if (this.frmData.crin_txt.length > 500) {
          this.$toast.show(this.$t('MS00000266'), { className: 'toast_error' }) // 시정 지시 내용은 500자를 초과할 수 없습니다.
          this.$refs.crin_txt.focus()
          return false
        }
      }
      this.gridView.commit()
      for (let i = 0; i < this.dataProvider.getRowCount(); i++) {
        if (this.isEmpty(this.dataProvider.getValue(i, 'chec_chk_rslt_vl')) && this.dataProvider.getRowState(i) !== 'createAndDeleted') {
          this.gridView.setCurrent({ dataRow: i, fieldName: 'chec_chk_rslt_vl' })
          this.$toast.show(this.$t('MS00000278'), { className: 'toast_error' }) // 점검 결과값을 입력해주세요.
          return false
        }
      }
      return true
    },
    async select() {
      this.gridView.commit()

      await this.$axios.get('/api/v1/she/sftchk/oprnbgnbfchkmgntchkrslt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const oprbChkResult = res.data.oprbChkResult[0]
          const oprbCrinResult = res.data.oprbCrinResult[0]
          // 기본 정보
          if (!this.isEmpty(oprbChkResult.oprb_chk_dt)) {
            this.frmData.oprb_chk_dt = oprbChkResult.oprb_chk_dt.substring(0, 4) + '-' + oprbChkResult.oprb_chk_dt.substring(4, 6) + '-' + oprbChkResult.oprb_chk_dt.substring(6, 8)
          }
          this.frmData.oprb_chk_rspn_emp_nm = oprbChkResult.oprb_chk_rspn_emp_nm
          this.frmData.oprb_chk_rspn_emp_id = oprbChkResult.oprb_chk_rspn_emp_id
          this.frmData.oprb_chk_spic_emp_nm = oprbChkResult.oprb_chk_spic_emp_nm
          this.frmData.oprb_chk_spic_emp_id = oprbChkResult.oprb_chk_spic_emp_id
          this.frmData.oprb_chk_schd_dt = oprbChkResult.oprb_chk_schd_dt
          this.$set(this.frmData, 'oprb_chk_hm_h', oprbChkResult.oprb_chk_hm_h)
          this.$set(this.frmData, 'oprb_chk_hm_s', oprbChkResult.oprb_chk_hm_s)
          this.$set(this.frmData, 'oprb_chk_hm', oprbChkResult.oprb_chk_hm_h.concat(oprbChkResult.oprb_chk_hm_s))
          // 시정지시 정보
          if (oprbCrinResult != null && oprbCrinResult !== undefined) {
            this.frmData.actn_hope_dt =
              oprbCrinResult.actn_hope_dt.substring(0, 4) + '-' + oprbCrinResult.actn_hope_dt.substring(4, 6) + '-' + oprbCrinResult.actn_hope_dt.substring(6, 8)
            this.frmData.crin_actn_schd_emp_nm = oprbCrinResult.crin_actn_schd_emp_nm
            this.frmData.crin_actn_schd_emp_id = oprbCrinResult.crin_actn_schd_emp_id
            this.frmData.crin_txt = oprbCrinResult.crin_txt
            this.frmData.crin_risk_tp_cd = oprbCrinResult.crin_risk_tp_cd
            this.$set(this.frmData, 'crin_risk_tp_cd_nm', oprbCrinResult.crin_risk_tp_cd_nm)
            this.frmData.crin_risk_tp_cd_2 = oprbCrinResult.crin_risk_tp_cd_2
            this.$set(this.frmData, 'crin_risk_tp_cd_2_nm', oprbCrinResult.crin_risk_tp_cd_2_nm)

            // 이미지 로딩
            if (!this.isEmpty(oprbCrinResult.crin_img_atfl_no)) {
              this.$refs.picPop.load(oprbCrinResult.crin_img_atfl_no)
            }
            crinYn = true
            const dom1 = document.getElementById('crinTitle2')
            const dom2 = document.getElementById('crinText2')
            dom1.style.display = 'block'
            dom2.style.display = 'block'
          } else {
            crinYn = false
            const dom1 = document.getElementById('crinTitle2')
            const dom2 = document.getElementById('crinText2')
            dom1.style.display = 'none'
            dom2.style.display = 'none'
          }

          this.dataProvider.setRows(res.data.oprbChkList)
          this.gridView.refresh()
          this.gridView.setTopItem(0)
          this.totalcnt = this.gridView.getItemCount()

          this.tempData = JSON.parse(JSON.stringify(this.frmData))
          // 기본 선택 점검 결과 값을 양호로 default 처리
          this.setDefaultValue()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      this.gridView.commit()
      const val1 = this.frmData.oprb_chk_hm_h
      const val2 = this.frmData.oprb_chk_hm_s
      if (!this.isEmpty(val1) && !this.isEmpty(val2)) {
        this.frmData.oprb_chk_hm = val1.concat(val2)
      }

      if (this.dataProvider.getRowStateCount('none') === this.dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      if (!this.validate()) return

      this.savedatas = [] //  저장될 배열 생성
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (this.dataProvider.getRowStateCount('all') - this.dataProvider.getRowStateCount('createAndDeleted') > 0) {
        const updateRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < this.dataProvider.getRowCount(); i++) {
          const temp = {}
          temp.oprn_bf_chec_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_cl_cd')
          temp.oprn_bf_chec_dtl_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_dtl_cl_cd')
          temp.oprn_bf_chec_sn = this.dataProvider.getValue(i, 'oprn_bf_chec_sn')
          temp.chec_lang_cd = this.dataProvider.getValue(i, 'chec_lang_cd')
          temp.oprn_bf_chec_txt = this.dataProvider.getValue(i, 'oprn_bf_chec_txt')
          temp.oprn_bf_chec_in_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_in_cl_cd')
          temp.chec_chk_rslt_vl = this.dataProvider.getValue(i, 'chec_chk_rslt_vl')
          if (this.dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          }
        }
        this.frmData.updateRow = updateRow
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사진 업로드 과정
      if (crinYn === true) {
        await this.$refs.picPop.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.crin_img_atfl_no = data
          } else {
            this.frmData.crin_img_atfl_no = ''
          }
        })
      }
      // eslint-disable-next-line no-console
      console.log(this.frmData)

      if (this.isEmpty(this.frmData.crin_img_atfl_no) && crinYn === true) {
        this.$toast.show(this.$t('MS00000267'), { className: 'toast_error' }) // 점검 사진을 등록해주세요.
        // this.$refs.picPop.focus()
        return
      }

      this.frmData.crinYn = crinYn // 시정지시서 등록 여부
      this.frmData.oprb_chk_prst_cd = '20' // 작성중
      await this.$axios.put('/api/v1/she/sftchk/oprnbgnbfchkmgntchkrslt', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async comp() {
      this.gridView.commit()
      const val1 = this.frmData.oprb_chk_hm_h
      const val2 = this.frmData.oprb_chk_hm_s
      if (!this.isEmpty(val1) && !this.isEmpty(val2)) {
        this.frmData.oprb_chk_hm = val1.concat(val2)
      }
      if (this.dataProvider.getRowStateCount('none') === this.dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      if (!this.validate()) return

      this.savedatas = [] //  저장될 배열 생성
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (this.dataProvider.getRowStateCount('all') - this.dataProvider.getRowStateCount('createAndDeleted') > 0) {
        const updateRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < this.dataProvider.getRowCount(); i++) {
          const temp = {}
          temp.oprn_bf_chec_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_cl_cd')
          temp.oprn_bf_chec_dtl_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_dtl_cl_cd')
          temp.oprn_bf_chec_sn = this.dataProvider.getValue(i, 'oprn_bf_chec_sn')
          temp.chec_lang_cd = this.dataProvider.getValue(i, 'chec_lang_cd')
          temp.oprn_bf_chec_txt = this.dataProvider.getValue(i, 'oprn_bf_chec_txt')
          temp.oprn_bf_chec_in_cl_cd = this.dataProvider.getValue(i, 'oprn_bf_chec_in_cl_cd')
          temp.chec_chk_rslt_vl = this.dataProvider.getValue(i, 'chec_chk_rslt_vl')
          if (this.dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          }
        }
        this.frmData.updateRow = updateRow
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사진 업로드 과정
      if (crinYn === true) {
        await this.$refs.picPop.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.crin_img_atfl_no = data
          } else {
            this.frmData.crin_img_atfl_no = ''
          }
        })
      }
      // eslint-disable-next-line no-console
      console.log(this.frmData)

      if (this.isEmpty(this.frmData.crin_img_atfl_no) && crinYn === true) {
        this.$toast.show(this.$t('MS00000267'), { className: 'toast_error' }) // 점검 사진을 등록해주세요.
        // this.$refs.picPop.focus()
        return
      }

      this.frmData.crinYn = crinYn // 시정지시서 등록 여부
      if (crinYn === true) {
        this.frmData.oprb_chk_prst_cd = '40' // 점검완료
      } else {
        this.frmData.oprb_chk_prst_cd = '40' // 점검완료
      }
      await this.$axios.put('/api/v1/she/sftchk/oprnbgnbfchkmgntchkrslt', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.close()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
