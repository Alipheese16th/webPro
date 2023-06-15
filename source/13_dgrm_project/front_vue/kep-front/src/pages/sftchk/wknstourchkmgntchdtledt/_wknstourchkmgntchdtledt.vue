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
                <div class="label_tit essn">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="circ_chk_supe_emp_nm"
                  v-model="frmData.circ_chk_supe_emp_nm"
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
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.circ_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 일시-->
                <div class="label_tit essn">{{ $t('LB00000680') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="circ_chk_dt"
                      v-model="frmData.circ_chk_dt"
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
                  <v-date-picker v-model="frmData.circ_chk_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="circ_chk_hm_h"
                  v-model="frmData.circ_chk_hm_h"
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
                  ref="circ_chk_hm_s"
                  v-model="frmData.circ_chk_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="mm5"
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
      <!-- 참석자 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000908') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ totalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
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
                <div class="label_tit essn">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="5">
                <v-menu v-model="menu3" :close-on-content-click="false" :nudge-right="40" min-width="290px">
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
                  <v-date-picker v-model="frmData.actn_hope_dt" no-title @input="menu3 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
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
                <!--위험유형-->
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
          <!--완료-->
          <v-btn depressed width="120" color="primary" height="40" @click="comp">{{ $t('LB00000802') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="edtpop" mkey1="wknstourchkmgntdtledt1" mkey2="wknstourchkmgntdtledt2" @close="popClose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import picpreview from '../../common/picpreview'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let date = new Date()
let crinYn = false
export default {
  meta: {
    title: '작업장 유해물질 점검 관리',
    key(route) {
      return `/sftchk/wknstourchkmgnt/${route.params.catalog}`
    },
  },
  components: {
    popup,
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
      codeid: ['ST00000002', 'ST00000018', 'SH00000021', 'SH00000022', 'SH00000063', 'SH00000068', 'SH00000074'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000018: [{ cd: '', cd_nm: '' }],
        SH00000021: [{ cd: '', cd_nm: '' }],
        SH00000022: [{ cd: '', cd_nm: '' }],
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
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        crin_risk_tp_cd: '',
        crin_risk_tp_cd_2: '',
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
        circ_chk_hm: '',
        circ_chk_hm_h: '',
        circ_chk_hm_s: '',
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
  async beforePageLeave(tab, type) {
    gridView.commit()
    gridView2.commit()
    if (
      dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
      dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0 ||
      JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)
    ) {
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
        this.codes.SH00000068.unshift({ cd: '', cd_nm: 'SELECT' })
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
            button: 'action',
            alwaysShowButton: false,
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
            header: this.$t('LB00000910'), // 이메일주소
            name: 'circ_chk_atnd_emad',
            fieldName: 'circ_chk_atnd_emad',
            width: '300',
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
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.setRowIndicator({ visible: false })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('circ_chk_atnd_tphn', 'visible', false)

        // 유저 팝업 띄우기
        gridView.onCellButtonClicked = function (grid, itemIndex, column) {
          // const current = gridView.getCurrent()
          this.circChkUsr = itemIndex.dataRow
          if (itemIndex.column === 'circ_chk_atnd_nm') {
            const gubun = grid.getValue(itemIndex.dataRow, 'circ_chk_atnd_cl_cd')
            if (gubun === 'U01') {
              this.rowChk = true
              gridView.commit()
              this.popOpen()
            } else if (gubun === 'U02' || gubun === 'U03') {
              this.$toast.show(this.$t('MS00000370'), { className: 'toast_error' }) // 임직원만 선택 가능합니다.
            } else {
              this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            }
          }
        }.bind(this)

        gridView.onCellEdited = function (grid, itemIndex, row, field) {
          if (field === 0) {
            gridView.commit()
            dataProvider.setValue(row, 'circ_chk_atnc_emp_id', '')
            dataProvider.setValue(row, 'circ_chk_atnd_nm', '')
            dataProvider.setValue(row, 'circ_chk_atnd_pstn_nm', '')
            dataProvider.setValue(row, 'circ_chk_atnd_dept_nm', '')
            dataProvider.setValue(row, 'circ_chk_atnd_tphn', '')
            dataProvider.setValue(row, 'circ_chk_atnd_emad', '')
          }
        }

        gridView.columnByName('circ_chk_atnd_nm').buttonVisibleCallback = function (grid, index, focused, mouseEntered) {
          return grid.getValue(index.itemIndex, 'circ_chk_atnd_cl_cd') === 'U01' && (focused || mouseEntered)
        }

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
            fieldName: 'chec_chk_cl_cd_nm', // 구분명
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
            header: this.$t('LB00000741'), // 구분명
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
            editable: true,
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'circ_chk_chec_in_cl_cd')
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
                  break
                case 'IN04':
                  ret.editor = {
                    type: 'dropdown',
                    values: ['', 'Y', 'N', 'X'],
                    labels: ['SELECT', '문제없음', '자체점검 시 조치완료', '정밀 재점검 필요'],
                    domainOnly: true,
                    textReadOnly: true,
                  }
                  ret.editable = true
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
            crinYn = true
          } else {
            // 그리드 내에 상태가 N인 값이 존재하는지 아닌지 체크한다.
            for (let i = 0; i < grid.getItemCount(); i++) {
              const chk = grid.getValue(i, 'chec_chk_rslt_vl')
              if ((chk === 'N' || chk === 'X') && grid.getValue(i, 'circ_chk_chec_in_cl_cd') !== 'IN01') {
                return
              }
            }
            dom1.style.display = 'none'

            crinYn = false
          }
        }

        // 권한 체크. 같은 부서 여부 조회
        if (!this.isEmpty(this.$route.params.wknstourchkmgntchdtledt)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'WCHK', circ_chk_id: this.$route.params.wknstourchkmgntchdtledt } }).then((res) => {
            if (res === 'Success') {
              this.schData.circ_chk_id = this.$route.params.wknstourchkmgntchdtledt
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
              this.$router.push({
                name: 'sftchk-wknstourchkmgntchlist',
                params: {
                  tmpSchData: this.tmpSchData,
                },
              })
            }
          })
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    setDefaultValue() {
      gridView2.commit()
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 구분 값이 텍스트 입력이 아닐때, 값이 공백일때
        if (dataProvider2.getValue(i, 'circ_chk_chec_in_cl_cd') !== 'IN01' && dataProvider2.getValue(i, 'chec_chk_rslt_vl') === '') {
          // 해당 컬럼의 default 값을 Y로 설정한다.
          dataProvider2.setValue(i, 'chec_chk_rslt_vl', 'Y')
        }
      }
      gridView2.commit()
    },
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
          this.frmData.circ_chk_supe_emp_id = i.emp_no
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
          if (!this.isEmpty(wknsChkResult.circ_chk_hm_h) || !this.isEmpty(wknsChkResult.circ_chk_hm_s)) {
            this.$set(this.frmData, 'circ_chk_hm_h', wknsChkResult.circ_chk_hm_h)
            this.$set(this.frmData, 'circ_chk_hm_s', wknsChkResult.circ_chk_hm_s)
            this.$set(this.frmData, 'circ_chk_hm', wknsChkResult.circ_chk_hm_h.concat(wknsChkResult.circ_chk_hm_s))
          }

          // 시정지시 정보
          if (wknsCrinResult != null && wknsCrinResult !== undefined) {
            this.frmData.actn_hope_dt =
              wknsCrinResult.actn_hope_dt.substring(0, 4) + '-' + wknsCrinResult.actn_hope_dt.substring(4, 6) + '-' + wknsCrinResult.actn_hope_dt.substring(6, 8)
            this.frmData.crin_actn_schd_emp_nm = wknsCrinResult.crin_actn_schd_emp_nm
            this.frmData.crin_actn_schd_emp_id = wknsCrinResult.crin_actn_schd_emp_id
            this.frmData.crin_txt = wknsCrinResult.crin_txt
            this.frmData.crin_risk_tp_cd = wknsCrinResult.crin_risk_tp_cd
            this.frmData.crin_risk_tp_cd_2 = wknsCrinResult.crin_risk_tp_cd_2

            // 이미지 로딩
            if (!this.isEmpty(wknsCrinResult.crin_img_atfl_no)) {
              this.$refs.picPopChk.load(wknsCrinResult.crin_img_atfl_no)
            }
            crinYn = true
            const dom1 = document.getElementById('crinYn')
            dom1.style.display = 'block'
          } else {
            crinYn = false
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

          this.tempData = JSON.parse(JSON.stringify(this.frmData))

          this.setDefaultValue()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      if (this.isEmpty(this.frmData.circ_chk_supe_emp_nm)) {
        this.$toast.show(this.$t('MS00000269'), { className: 'toast_error' }) // 점검 담당자를 선택해주세요.
        this.$refs.circ_chk_supe_emp_nm.focus()
        return false
      } else if (this.isEmpty(this.frmData.circ_chk_dt)) {
        this.$toast.show(this.$t('MS00000270'), { className: 'toast_error' }) // 점검 일자를 선택해주세요.
        this.$refs.circ_chk_dt.focus()
        return false
      } else if (this.isEmpty(this.frmData.circ_chk_hm_h) || this.isEmpty(this.frmData.circ_chk_hm_s)) {
        this.$toast.show(this.$t('MS00000271'), { className: 'toast_error' }) // 점검 시간을 선택해주세요.
        this.$refs.circ_chk_hm_h.focus()
        return false
      }
      gridView.commit()
      if (dataProvider.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000378'), { className: 'toast_error' }) // 참가자를 등록해주세요.
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_cl_cd' })
          this.$toast.show(this.$t('MS00000380'), { className: 'toast_error' }) // 구분값을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'circ_chk_atnd_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_nm' })
          this.$toast.show(this.$t('MS00000337'), { className: 'toast_error' }) // 성명은 50자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_pstn_nm'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_pstn_nm' })
        //   this.$toast.show('직책을 입력해주세요.', { className: 'toast_error' }) // 직책을 입력해주세요.
        //   return false
        // }
        if (!this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_pstn_nm')) && dataProvider.getValue(i, 'circ_chk_atnd_pstn_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_pstn_nm' })
          this.$toast.show(this.$t('MS00000338'), { className: 'toast_error' }) // 직책은 50자를 초과할 수 없습니다.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_dept_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_dept_nm' })
          this.$toast.show(this.$t('MS00000383'), { className: 'toast_error' }) // 회사명을 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'circ_chk_atnd_dept_nm').length > 100) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_dept_nm' })
          this.$toast.show(this.$t('MS00000385'), { className: 'toast_error' }) // 회사명은 100자를 초과할 수 없습니다.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'circ_chk_atnd_emad'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_emad' })
          this.$toast.show(this.$t('MS00000287'), { className: 'toast_error' }) // 이메일 주소를 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'circ_chk_atnd_emad').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'circ_chk_atnd_emad' })
          this.$toast.show(this.$t('MS00000387'), { className: 'toast_error' }) // 이메일 주소는 50자를 초과할 수 없습니다.
          return false
        }
      }
      gridView2.commit()
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'chec_chk_rslt_vl'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'chec_chk_rslt_vl' })
          this.$toast.show(this.$t('MS00000278'), { className: 'toast_error' }) // 점검결과를 입력해주세요.
          return false
        }
      }
      if (crinYn) {
        // 시정지시서 입력이 필요할 경우
        if (this.isEmpty(this.frmData.actn_hope_dt)) {
          this.$toast.show(this.$t('MS00000261'), { className: 'toast_error' }) // 개선 희망일을 입력해주세요.
          this.$refs.actn_hope_dt.focus()
          return false
        } else if (this.isEmpty(this.frmData.crin_risk_tp_cd)) {
          this.$toast.show(this.$t('MS00000262'), { className: 'toast_error' }) // 위험 유형을 선택해주세요.
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
      return true
    },
    async save() {
      gridView.commit()
      gridView2.commit()
      const val1 = this.frmData.circ_chk_hm_h
      const val2 = this.frmData.circ_chk_hm_s
      if (!this.isEmpty(val1) && !this.isEmpty(val2)) {
        this.frmData.circ_chk_hm = val1.concat(val2)
      }
      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        if (dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
          this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
          return
        }
      }

      if (!this.validate()) return

      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.circ_chk_atnd_cl_cd = dataProvider.getValue(i, 'circ_chk_atnd_cl_cd')
          temp.circ_chk_atnc_emp_id = dataProvider.getValue(i, 'circ_chk_atnc_emp_id')
          temp.circ_chk_atnd_nm = dataProvider.getValue(i, 'circ_chk_atnd_nm')
          temp.circ_chk_atnd_pstn_nm = dataProvider.getValue(i, 'circ_chk_atnd_pstn_nm')
          temp.circ_chk_atnd_dept_nm = dataProvider.getValue(i, 'circ_chk_atnd_dept_nm')
          temp.circ_chk_atnd_tphn = dataProvider.getValue(i, 'circ_chk_atnd_tphn')
          temp.circ_chk_atnd_emad = dataProvider.getValue(i, 'circ_chk_atnd_emad')
          temp.circ_chk_atnd_sn = dataProvider.getValue(i, 'circ_chk_atnd_sn')

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.atndInsert = insertRow
        this.frmData.atndUpdate = updateRow
        this.frmData.atndDelete = deleteRow
      }

      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
        const updateRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}
          temp.chec_chk_cl_cd = dataProvider2.getValue(i, 'chec_chk_cl_cd')
          temp.circ_chk_chec_sn = dataProvider2.getValue(i, 'circ_chk_chec_sn')
          temp.chec_lang_cd = dataProvider2.getValue(i, 'chec_lang_cd')
          temp.circ_chk_chec_in_cl_cd = dataProvider2.getValue(i, 'circ_chk_chec_in_cl_cd')
          temp.chec_chk_rslt_vl = dataProvider2.getValue(i, 'chec_chk_rslt_vl')
          if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          }
        }
        this.frmData.resultRow = updateRow
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사진 업로드 과정
      if (crinYn === true) {
        await this.$refs.picPopChk.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.crin_img_atfl_no = data
          } else {
            this.frmData.crin_img_atfl_no = ''
          }
        })
      }

      if (this.isEmpty(this.frmData.crin_img_atfl_no) && crinYn === true) {
        this.$toast.show(this.$t('MS00000267'), { className: 'toast_error' }) // 점검 사진을 등록해주세요.
        // this.$refs.picPopChk.focus()
        return
      }

      this.frmData.crinYn = crinYn // 시정지시서 등록 여부
      this.frmData.circ_chk_prst_cd = '20' // 작성중
      this.frmData.sch_gb = 'tourch'
      await this.$axios.put('/api/v1/she/sftchk/wknstourchkmgntchkrslt', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async comp() {
      gridView.commit()
      gridView2.commit()
      const val1 = this.frmData.circ_chk_hm_h
      const val2 = this.frmData.circ_chk_hm_s
      if (!this.isEmpty(val1) && !this.isEmpty(val2)) {
        this.frmData.circ_chk_hm = val1.concat(val2)
      }
      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        if (dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
          this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
          return
        }
      }

      if (!this.validate()) return

      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.circ_chk_atnd_cl_cd = dataProvider.getValue(i, 'circ_chk_atnd_cl_cd')
          temp.circ_chk_atnc_emp_id = dataProvider.getValue(i, 'circ_chk_atnc_emp_id')
          temp.circ_chk_atnd_nm = dataProvider.getValue(i, 'circ_chk_atnd_nm')
          temp.circ_chk_atnd_pstn_nm = dataProvider.getValue(i, 'circ_chk_atnd_pstn_nm')
          temp.circ_chk_atnd_dept_nm = dataProvider.getValue(i, 'circ_chk_atnd_dept_nm')
          temp.circ_chk_atnd_tphn = dataProvider.getValue(i, 'circ_chk_atnd_tphn')
          temp.circ_chk_atnd_emad = dataProvider.getValue(i, 'circ_chk_atnd_emad')
          temp.circ_chk_atnd_sn = dataProvider.getValue(i, 'circ_chk_atnd_sn')

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.atndInsert = insertRow
        this.frmData.atndUpdate = updateRow
        this.frmData.atndDelete = deleteRow
      }

      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
        const updateRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}
          temp.chec_chk_cl_cd = dataProvider2.getValue(i, 'chec_chk_cl_cd')
          temp.circ_chk_chec_sn = dataProvider2.getValue(i, 'circ_chk_chec_sn')
          temp.chec_lang_cd = dataProvider2.getValue(i, 'chec_lang_cd')
          temp.circ_chk_chec_in_cl_cd = dataProvider2.getValue(i, 'circ_chk_chec_in_cl_cd')
          temp.chec_chk_rslt_vl = dataProvider2.getValue(i, 'chec_chk_rslt_vl')
          if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          }
        }
        this.frmData.resultRow = updateRow
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사진 업로드 과정
      if (crinYn === true) {
        await this.$refs.picPopChk.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.crin_img_atfl_no = data
          } else {
            this.frmData.crin_img_atfl_no = ''
          }
        })
      }

      if (this.isEmpty(this.frmData.crin_img_atfl_no) && crinYn === true) {
        this.$toast.show(this.$t('MS00000267'), { className: 'toast_error' }) // 점검 사진을 등록해주세요.
        // this.$refs.picPopChk.focus()
        return
      }

      this.frmData.crinYn = crinYn // 시정지시서 등록 여부
      if (crinYn === true) {
        this.frmData.circ_chk_prst_cd = '40' // 점검완료
      } else {
        this.frmData.circ_chk_prst_cd = '40' // 점검완료
      }
      this.frmData.sch_gb = 'tourch'
      await this.$axios.put('/api/v1/she/sftchk/wknstourchkmgntchkrslt', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.tempData = JSON.parse(JSON.stringify(this.frmData))
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
