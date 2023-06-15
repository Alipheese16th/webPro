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
                <div class="label_tit">{{ $t('LB00000786') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <div class="label_con">{{ frmData.oprb_chk_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 점검 시간 -->
                <div class="label_tit">{{ $t('LB00000793') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <div class="label_con">{{ frmData.oprb_chk_hm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 관리 감독자 -->
                <div class="label_tit">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <div class="label_con">{{ frmData.oprb_chk_rspn_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 부관리 감독자 -->
                <div class="label_tit">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3" style="background: #fff">
                <div class="label_con">{{ frmData.oprb_chk_rspn_emp_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </div>
        <div class="group_padd"></div>
        <div class="realgrid_container">
          <div id="oprnchkrsltluppop" style="width: 100%; height: 281px"></div>
        </div>
        <div class="group_padd"></div>
        <div v-show="crinYn" id="crinTitle" class="realgrid_container">
          <div class="sub_title">
            <!--시정지시 등록-->
            <h3>{{ $t('LB00000794') }}</h3>
          </div>
        </div>
        <div v-show="crinYn" id="crinText" class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 개선 희망일 -->
                <div class="label_tit">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.actn_hope_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 조치 담당자 -->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_actn_schd_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험 유형-->
                <div class="label_tit">{{ $t('LB00000672') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_risk_tp_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--위험유형2-->
                <div class="label_tit">{{ $t('LB00000672') }}2</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.crin_risk_tp_cd_2_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--시정지시 내용-->
                <div class="label_tit">{{ $t('LB00000799') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.crin_txt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 사진-->
                <div class="label_tit">{{ $t('LB00000800') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop2" />
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import picpreview from '../common/picpreview'

export default {
  components: {
    // FileUpload,
    picpreview,
  },
  props: {
    mkey: {
      type: String,
      default: 'oprnchkrsltluppop',
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
      menu1: false,
      menu2: false,
      menu3: false,
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
      crinYn: false,
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
        header: this.$t('LB00000741'),
        name: 'oprn_bf_chec_cl_cd_nm',
        fieldName: 'oprn_bf_chec_cl_cd_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000642'),
        name: 'oprn_bf_chec_txt',
        fieldName: 'oprn_bf_chec_txt',
        width: '400',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000646'),
        name: 'chec_chk_rslt_vl',
        fieldName: 'chec_chk_rslt_vl',
        width: '200',
        editable: false,
        styleCallback(grid, dataCell) {
          const gubun = grid.getValue(dataCell.index.itemIndex, 'oprn_bf_chec_in_cl_cd')
          const ret = {}

          switch (gubun) {
            // 구분값이 T이면 text에디터를 표시
            case 'IN01':
              ret.editor = {
                type: 'text',
              }
              ret.editable = false
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
              ret.editable = false
              break
            case 'IN03':
              ret.editor = {
                type: 'dropdown',
                values: ['', 'Y', 'N'],
                labels: ['SELECT', 'Y', 'N'],
                domainOnly: true,
                textReadOnly: true,
              }
              ret.editable = false
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
    const gridId = document.getElementById('oprnchkrsltluppop')
    gridId.id = this.mkey + 'oprnchkrsltluppop'
    this.gridView = new GridView(this.mkey + 'oprnchkrsltluppop')

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

    this.gridView.setColumnProperty('id', 'visible', false)
  },
  updated() {},
  methods: {
    open(key) {
      this.$refs.picPop2.setEdit(false)
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
      const dom1 = document.getElementById('crinTitle')
      const dom2 = document.getElementById('crinText')
      dom1.style.display = 'none'
      dom2.style.display = 'none'
      this.frmData = {}
      this.schData = {}
      this.$refs.picPop2.reset()
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
    popOpen() {
      this.userSwitch = 'OPRB'
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
    async select() {
      this.gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      await this.$axios.get('/api/v1/she/sftchk/oprnbgnbfchkmgntchkrslt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const oprbChkResult = res.data.oprbChkResult[0]
          const oprbCrinResult = res.data.oprbCrinResult[0]

          // 기본 정보
          this.frmData.oprb_chk_dt = oprbChkResult.oprb_chk_dt.substring(0, 4) + '-' + oprbChkResult.oprb_chk_dt.substring(4, 6) + '-' + oprbChkResult.oprb_chk_dt.substring(6, 8)
          this.frmData.oprb_chk_rspn_emp_nm = oprbChkResult.oprb_chk_rspn_emp_nm
          this.frmData.oprb_chk_rspn_emp_id = oprbChkResult.oprb_chk_rspn_emp_id
          this.frmData.oprb_chk_spic_emp_nm = oprbChkResult.oprb_chk_spic_emp_nm
          this.frmData.oprb_chk_spic_emp_id = oprbChkResult.oprb_chk_spic_emp_id
          this.frmData.oprb_chk_schd_dt = oprbChkResult.oprb_chk_schd_dt
          this.$set(this.frmData, 'oprb_chk_hm_h', oprbChkResult.oprb_chk_hm_h)
          this.$set(this.frmData, 'oprb_chk_hm_s', oprbChkResult.oprb_chk_hm_s)
          this.$set(this.frmData, 'oprb_chk_hm', oprbChkResult.oprb_chk_hm_h + ':' + oprbChkResult.oprb_chk_hm_s)

          // 시정지시 정보
          if (oprbCrinResult != null && oprbCrinResult !== undefined) {
            this.frmData.actn_hope_dt =
              oprbCrinResult.actn_hope_dt.substring(0, 4) + '-' + oprbCrinResult.actn_hope_dt.substring(4, 6) + '-' + oprbCrinResult.actn_hope_dt.substring(6, 8)
            this.frmData.crin_actn_schd_emp_nm = oprbCrinResult.crin_actn_schd_emp_nm
            this.frmData.crin_actn_schd_emp_id = oprbCrinResult.crin_actn_schd_emp_id
            this.frmData.crin_txt = oprbCrinResult.crin_txt
            this.frmData.crin_risk_tp_cd = oprbCrinResult.crin_risk_tp_cd
            this.frmData.crin_risk_tp_cd_nm = oprbCrinResult.crin_risk_tp_cd_nm
            this.frmData.crin_risk_tp_cd_2 = oprbCrinResult.crin_risk_tp_cd_2
            this.frmData.crin_risk_tp_cd_2_nm = oprbCrinResult.crin_risk_tp_cd_2_nm

            // 이미지 로딩
            if (!this.isEmpty(oprbCrinResult.crin_img_atfl_no)) {
              this.$refs.picPop2.load(oprbCrinResult.crin_img_atfl_no)
            }
            const dom1 = document.getElementById('crinTitle')
            const dom2 = document.getElementById('crinText')
            dom1.style.display = 'block'
            dom2.style.display = 'block'
          } else {
            const dom1 = document.getElementById('crinTitle')
            const dom2 = document.getElementById('crinText')
            dom1.style.display = 'none'
            dom2.style.display = 'none'
          }

          this.dataProvider.setRows(res.data.oprbChkList)
          this.gridView.refresh()
          this.gridView.setTopItem(0)
          this.totalcnt = this.gridView.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
