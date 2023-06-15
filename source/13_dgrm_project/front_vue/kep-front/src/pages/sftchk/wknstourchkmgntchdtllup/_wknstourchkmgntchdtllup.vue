<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 유해물질 점검 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001247') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 유해물질 점검 관리-->
      <h2>{{ $t('LB00001247') }}</h2>
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
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 담당자-->
                <div class="label_tit">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_supe_emp_nm }}</div>
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
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.circ_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 일시-->
                <div class="label_tit">{{ $t('LB00000680') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ frmData.circ_chk_dt }} {{ frmData.circ_chk_tm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검주기-->
                <div class="label_tit">{{ $t('LB00000907') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_cyc_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--특이사항-->
                <div class="label_tit">{{ $t('LB00000994') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.circ_chk_note_txt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <h3>{{ $t('LB00000908') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ totalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div id="realgrid2" style="width: 100%; height: 481px"></div>
    </div>
    <div id="crinYn" class="container_detail">
      <div class="group_padd"></div>
      <div class="sub_title">
        <!-- 시정지시서 발행 -->
        <h3>{{ $t('LB00000995') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--개선 희망일-->
                <div class="label_tit">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                {{ frmData.actn_hope_dt }}
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="5">
                {{ frmData.crin_actn_schd_emp_nm }}
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위험유형-->
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
                {{ frmData.crin_txt }}
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 사진-->
                <div class="label_tit">{{ $t('LB00000800') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPopChk" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import picpreview from '../../common/picpreview'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업장 유해물질 점검 관리',
    key(route) {
      return `/sftchk/wknstourchkmgnt/${route.params.catalog}`
    },
  },
  components: {
    picpreview,
    // quillEditor
  },
  data() {
    return {
      schData: {
        circ_chk_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      selSH00000022: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000002', 'ST00000018', 'SH00000021', 'SH00000022', 'SH00000063'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000018: [{ cd: '', cd_nm: '' }],
        SH00000021: [{ cd: '', cd_nm: '' }],
        SH00000022: [{ cd: '', cd_nm: '' }],
        SH00000063: [{ cd: '', cd_nm: '' }],
      },
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        crin_risk_tp_cd: '',
        crin_risk_tp_cd_nm: '',
        crin_risk_tp_cd_2: '',
        crin_risk_tp_cd_2_nm: '',
        circ_chk_supe_emp_id: '',
        circ_chk_supe_emp_nm: '',
        circ_chk_wkpl_loc_id: '',
        circ_chk_wkpl_loc_nm: '',
        circ_chk_dtl_loc_nm: '',
        circ_chk_tgt_nm: '',
        circ_chk_note_txt: '',
        circ_chk_cyc_cd: '',
        circ_chk_cyc_cd_nm: '',
        circ_chk_dt: '',
        circ_chk_tm: '',
        lsch_nm: '',
        actn_hope_dt: '',
        crin_actn_schd_emp_id: '',
        crin_actn_schd_emp_nm: '',
        crin_txt: '',
        crinYn: false,
      },
      menu1: false,
      menu2: false,
      menu3: false,
      totalcnt: 0,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001247')
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
    date = this.getTzDateObj(user.time_zone)

    date.setDate(date.getDate() - 30)
    this.schData.date = this.get_date_str_gubun(date, '-')

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        circ_chk_tgt_nm: '',
        circ_chk_st_dt: '',
        circ_chk_ed_dt: '',
        circ_chk_supe_emp_nm: '',
        circ_chk_supe_emp_id: '',
        circ_chk_wkpl_loc_id: '',
        circ_chk_wkpl_loc_nm: '',
        circ_chk_prst_cd: '',
        cmpy_cd: '',
      }
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.ST00000018.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000021.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000063.unshift({ cd: '', cd_nm: 'SELECT' })
        this.selSH00000022.cd.push('')
        this.selSH00000022.cd_nm.push('SELECT')
        res.SH00000022.forEach((i) => {
          this.selSH00000022.cd.push(i.cd)
          this.selSH00000022.cd_nm.push(i.cd_nm)
        })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'circ_chk_atnd_sn', // 기본키로 쓰임
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnc_emp_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_pstn_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_dept_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_tphn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_emad',
            dataType: ValueType.TEXT,
          },
        ]
        const columns = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'circ_chk_atnd_cl_cd',
            fieldName: 'circ_chk_atnd_cl_cd',
            width: '100',
            sortable: false,
            lookupDisplay: true,
            values: this.selSH00000022.cd,
            labels: this.selSH00000022.cd_nm,
            styleName: 'left',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00000701'), // 성명
            name: 'circ_chk_atnd_nm',
            fieldName: 'circ_chk_atnd_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000766'), // 직책
            name: 'circ_chk_atnd_pstn_nm',
            fieldName: 'circ_chk_atnd_pstn_nm',
            width: '100',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'circ_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000909'), // 회사명
            name: 'circ_chk_atnd_dept_nm',
            fieldName: 'circ_chk_atnd_dept_nm',
            width: '100',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'circ_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000723'), // 전화번호
            name: 'circ_chk_atnd_tphn',
            fieldName: 'circ_chk_atnd_tphn',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000910'), // 이메일주소
            name: 'circ_chk_atnd_emad',
            fieldName: 'circ_chk_atnd_emad',
            width: '300',
            styleName: 'left',
          },
        ]
        // 그리드 1 시작
        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.displayOptions.selectionStyle = 'block'
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

        gridView.setColumnProperty('circ_chk_atnd_tphn', 'visible', false)
        // 그리드 세팅
        const fields2 = [
          {
            fieldName: 'chec_chk_cl_cd', // 구분
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_chec_sn', // 순번
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_lang_cd', // 언어 코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_chk_cl_cd_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_txt', // 순회 점검 내용 없음
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_chec_in_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_chk_rslt_vl',
            dataType: ValueType.TEXT,
          },
        ]
        // 그리드 1 끝
        const columns2 = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'chec_chk_cl_cd_nm',
            fieldName: 'chec_chk_cl_cd_nm',
            width: '100',
          },
          {
            header: this.$t('LB00000901'), // 점검내용
            name: 'chec_txt',
            fieldName: 'chec_txt',
            width: '400',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000646'), // 점검결과
            name: 'chec_chk_rslt_vl',
            fieldName: 'chec_chk_rslt_vl',
            width: '200',
            editable: false,
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'circ_chk_chec_in_cl_cd')
              const ret = {}

              switch (gubun) {
                // 구분값이 T이면 text에디터를 표시
                case 'IN01':
                  ret.editor = {
                    type: 'text',
                  }
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
                  break
                case 'IN03':
                  ret.editor = {
                    type: 'dropdown',
                    values: ['', 'Y', 'N'],
                    labels: ['SELECT', 'Y', 'N'],
                    domainOnly: true,
                    textReadOnly: true,
                  }
                  break
                case 'IN04':
                  ret.editor = {
                    type: 'dropdown',
                    values: ['', 'Y', 'N', 'X'],
                    labels: ['SELECT', '문제없음', '자체점검 시 조치완료', '정밀 재점검 필요'],
                    domainOnly: true,
                    textReadOnly: true,
                  }
              }
              return ret
            },
            displayCallback(grid, index, value) {
              let retValue = value
              const gubun = grid.getValue(index.itemIndex, 'circ_chk_chec_in_cl_cd')

              if (gubun === 'IN02') {
                const idx = ['', 'Y', 'N'].indexOf(value)

                retValue = ['SELECT', '양호', '불량'][idx]
              }
              if (gubun === 'IN03') {
                const idx = ['', 'Y', 'N'].indexOf(value)

                retValue = ['SELECT', 'Y', 'N'][idx]
              }
              if (gubun === 'IN04') {
                const idx = ['', 'Y', 'N', 'X'].indexOf(value)

                retValue = ['SELECT', '문제없음', '자체점검 시 조치완료', '정밀 재점검 필요'][idx]
              }

              return retValue
            },
          },
        ]
        // 그리드 2 시작
        dataProvider2 = new LocalDataProvider(false)
        dataProvider2.setFields(fields2)
        gridView2 = new GridView('realgrid2')
        gridView2.displayOptions.selectionStyle = 'block'
        gridView2.setDataSource(dataProvider2)

        gridView2.setColumns(columns2)
        gridView2.setFooters({ visible: false })
        gridView2.setStateBar({ visible: false })
        gridView2.setCheckBar({ visible: false })
        gridView2.editOptions.editable = false
        gridView2.displayOptions.selectionStyle = 'block'

        gridView2.header.height = 39
        gridView2.displayOptions.rowHeight = 40
        gridView2.footer.height = 40
        gridView2.displayOptions.fitStyle = 'fill'
        // 그리드 2 끝

        gridView2.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
          grid.commit()
          // eslint-disable-next-line no-console
          const dom1 = document.getElementById('crinYn')

          if (grid.getValue(itemIndex, 'circ_chk_chec_in_cl_cd') === 'IN01') return
          if (newValue === 'N' || newValue === 'X') {
            if (dom1.style.display === 'none') {
              dom1.style.display = 'block'
            }
          } else {
            // 그리드 내에 상태가 N인 값이 존재하는지 아닌지 체크한다.
            for (let i = 0; i < grid.getItemCount(); i++) {
              const chk = grid.getValue(i, 'chec_chk_rslt_vl')
              if ((chk === 'N' || chk === 'X') && grid.getValue(i, 'circ_chk_chec_in_cl_cd') !== 'IN01') {
                return
              }
            }
            dom1.style.display = 'none'
          }
        }
        this.$refs.picPopChk.setEdit(false)
        this.schData.circ_chk_id = this.$route.params.wknstourchkmgntchdtllup
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    popOpen() {
      this.userSwitch = 'WCHK'
      this.$refs.edtpop.open(this.frmData, false)
    },
    poptOpen() {
      this.userSwitch = 'CRIN'
      this.$refs.edtpop.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.rowChk) {
        returnData.forEach((i) => {
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnc_emp_id', i.emp_no)
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnd_nm', i.emp_nm)
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnd_pstn_nm', i.jkw_ko_nm)
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnd_dept_nm', i.cmpy_nm)
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnd_tphn', i.emp_cpho_no)
          dataProvider.setValue(this.circChkUsr, 'circ_chk_atnd_emad', i.emp_email)
        })
        this.rowChk = false
      } else if (this.userSwitch === 'CRIN') {
        returnData.forEach((i) => {
          this.$set(this.frmData, 'crin_actn_schd_emp_nm', i.emp_nm)
          this.$set(this.frmData, 'crin_actn_schd_emp_id', i.emp_no)
        })
      } else {
        returnData.forEach((i) => {
          this.$set(this.frmData, 'circ_chk_supe_emp_nm', i.emp_nm)
          this.$set(this.frmData, 'circ_chk_supe_emp_id', i.emp_no)
        })
      }
    },
    add() {
      gridView.commit()
      dataProvider.addRow({
        circ_chk_atnd_cl_cd: '',
        circ_chk_atnc_emp_id: '',
        circ_chk_atnd_nm: '',
        circ_chk_atnd_pstn_nm: '',
        circ_chk_atnd_dept_nm: '',
        circ_chk_atnd_tphn: '',
        circ_chk_atnd_emad: '',
      })
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    goList() {
      this.$router.push({
        name: 'sftchk-wknstourchkmgntchlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async select() {
      gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      await this.$axios.get('/api/v1/she/sftchk/wknstourchkmgntchkrslt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const wknsChkResult = res.data.wknsChkResult[0]
          const wknsCrinResult = res.data.wknsCrinResult[0]

          // 기본 정보
          this.frmData.circ_chk_id = wknsChkResult.circ_chk_id
          this.frmData.circ_chk_supe_emp_id = wknsChkResult.circ_chk_supe_emp_id
          this.frmData.circ_chk_supe_emp_nm = wknsChkResult.circ_chk_supe_emp_nm
          this.frmData.circ_chk_dtl_loc_nm = wknsChkResult.circ_chk_dtl_loc_nm
          this.frmData.circ_chk_wkpl_loc_id = wknsChkResult.circ_chk_wkpl_loc_id
          this.frmData.circ_chk_wkpl_loc_nm = wknsChkResult.circ_chk_wkpl_loc_nm
          this.frmData.circ_chk_tgt_nm = wknsChkResult.circ_chk_tgt_nm
          this.frmData.circ_chk_note_txt = wknsChkResult.circ_chk_note_txt
          this.frmData.wkpl_id = wknsChkResult.wkpl_id
          this.frmData.wkpl_nm = wknsChkResult.wkpl_nm
          this.frmData.lsch_nm = wknsChkResult.lsch_nm
          if (!this.isEmpty(wknsChkResult.circ_chk_dt)) {
            this.frmData.circ_chk_dt = wknsChkResult.circ_chk_dt.substring(0, 4) + '-' + wknsChkResult.circ_chk_dt.substring(4, 6) + '-' + wknsChkResult.circ_chk_dt.substring(6, 8)
          }
          this.frmData.circ_chk_cyc_cd_nm = wknsChkResult.circ_chk_cyc_cd_nm
          if (!this.isEmpty(wknsChkResult.circ_chk_tm)) {
            this.$set(this.frmData, 'circ_chk_tm', wknsChkResult.circ_chk_tm.substring(0, 2) + ':' + wknsChkResult.circ_chk_tm.substring(2, 4))
          }

          // 시정지시 정보
          if (wknsCrinResult != null && wknsCrinResult !== undefined) {
            this.frmData.actn_hope_dt =
              wknsCrinResult.actn_hope_dt.substring(0, 4) + '-' + wknsCrinResult.actn_hope_dt.substring(4, 6) + '-' + wknsCrinResult.actn_hope_dt.substring(6, 8)
            this.frmData.crin_actn_schd_emp_nm = wknsCrinResult.crin_actn_schd_emp_nm
            this.frmData.crin_actn_schd_emp_id = wknsCrinResult.crin_actn_schd_emp_id
            this.frmData.crin_txt = wknsCrinResult.crin_txt
            this.frmData.crin_risk_tp_cd = wknsCrinResult.crin_risk_tp_cd
            this.frmData.crin_risk_tp_cd_nm = wknsCrinResult.crin_risk_tp_cd_nm
            this.frmData.crin_risk_tp_cd_2 = wknsCrinResult.crin_risk_tp_cd_2
            this.frmData.crin_risk_tp_cd_2_nm = wknsCrinResult.crin_risk_tp_cd_2_nm

            // 이미지 로딩
            if (!this.isEmpty(wknsCrinResult.crin_img_atfl_no)) {
              this.$refs.picPopChk.load(wknsCrinResult.crin_img_atfl_no)
            }
            const dom1 = document.getElementById('crinYn')
            dom1.style.display = 'block'
          } else {
            const dom1 = document.getElementById('crinYn')
            dom1.style.display = 'none'
          }

          dataProvider.setRows(res.data.wknsChkAtndList)
          gridView.refresh()
          gridView.setTopItem(0)
          this.totalcnt = gridView.getItemCount()

          dataProvider2.setRows(res.data.wknsChkList)
          gridView2.refresh()
          gridView2.setTopItem(0)

          this.totalcnt = gridView.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
