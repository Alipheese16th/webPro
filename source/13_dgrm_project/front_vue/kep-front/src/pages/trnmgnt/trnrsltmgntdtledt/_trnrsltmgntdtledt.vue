<template>
  <div>
    <div class="location">
      <!-- Home > 교육 및 훈련 > 교육/훈련 결과 관리 -->
      <span>Home > {{ $t('LB00001046') }} > {{ $t('LB00001067') }}</span>
    </div>
    <div class="work_title">
      <!-- 교육/훈련 결과 관리 -->
      <h2>{{ $t('LB00001067') }}</h2>
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
                  item-text="label"
                  item-value="value"
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
                <!--구분-->
                <div class="label_tit essn">{{ $t('LB00000741') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="trn_cl_cd"
                  v-model="frmData.trn_cl_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000011p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify"
                ></v-select>
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
                <!--교육/훈련명-->
                <div class="label_tit essn">{{ $t('LB00001066') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <v-text-field
                  ref="trn_nm"
                  v-model="frmData.trn_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--강사명-->
                <div class="label_tit">{{ $t('LB00001064') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="trn_techer_nm"
                  v-model="frmData.trn_techer_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="20"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--일시-->
                <div class="label_tit essn">{{ $t('LB00001063') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="100px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="trn_bgn_dt"
                      v-model="frmData.trn_bgn_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      :disabled="!apprModify"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.trn_bgn_dt" :max="frmData.trn_end_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="trn_bgn_hm_h"
                  v-model="frmData.trn_bgn_hm_h"
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
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="trn_bgn_hm_s"
                  v-model="frmData.trn_bgn_hm_s"
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
                  @change="hourChg()"
                ></v-select>
                &nbsp;&nbsp;
                <v-menu v-model="menu3" :close-on-content-click="false" :nudge-right="40" min-width="100px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="trn_end_dt"
                      v-model="frmData.trn_end_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      :disabled="!apprModify"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.trn_end_dt" :min="frmData.trn_bgn_dt" no-title @input="menu3 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="trn_end_hm_h"
                  v-model="frmData.trn_end_hm_h"
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
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="trn_end_hm_s"
                  v-model="frmData.trn_end_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="hourChg()"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--시간-->
                <div class="label_tit essn">{{ $t('LB00000249') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="trn_time_txt"
                  v-model="frmData.trn_time_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="trn_note_txt"
                  v-model="frmData.trn_note_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  auto-grow
                  rows="3"
                  class="mt-2"
                  :disabled="!apprModify"
                  dense
                  maxlength="2000"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--이수실적 (이수자/대상자)-->
                <div class="label_tit">{{ $t('LB00001062') }}</div>
              </v-col>
              <v-col cols="11" style="display: flex">
                <v-text-field
                  ref="trn_fcst_prsn_cnt"
                  v-model="frmData.trn_fcst_prsn_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  disabled
                  height="30"
                  maxlength="8"
                  @change="editPerCalc()"
                ></v-text-field>
                <v-text-field
                  ref="trn_tgt_prsn_cnt"
                  v-model="frmData.trn_tgt_prsn_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  disabled
                  height="30"
                  maxlength="8"
                  @change="editPerCalc()"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!-- 대상자 -->
        <h3>{{ $t('LB00001061') }}</h3>
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
        <!--실적-->
        <div class="grid_header_left">{{ $t('LB00001060') }} : {{ frmData.trn_fcst_prsn_cnt }} / {{ frmData.trn_tgt_prsn_cnt }} ({{ per_val }}%)</div>
        <div>
          <!--미이수자 다운로드-->
          <!-- <v-btn color="" outlined height="30" @click="hdselect()">미이수자 다운로드</v-btn> -->
          <!--양식 다운로드-->
          <v-btn color="" outlined height="30" @click="excelTemp()">{{ $t('LB00000721') }}</v-btn>
          <!--엑셀업로드-->
          <v-btn color="" outlined height="30" :disabled="!apprModify" @click="excel = !excel">{{ $t('LB00000720') }}</v-btn>
          <!--추가-->
          <v-btn color="" outlined height="30" :disabled="!apprModify" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" :disabled="!apprModify" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div id="hdrealgrid" style="width: 100%; height: 481px; display: none"></div>
      <div id="exceltemp" style="width: 100%; height: 481px; display: none"></div>
    </div>
    <div class="realgrid_container">
      <div class="group_padd"></div>
      <!-- 첨부파일 목록 -->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
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
          <!--메일발송-->
          <!-- <v-btn depressed width="120" color="primary" height="40" @click="mailpop()">메일발송</v-btn> -->
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!apprModify" @click="appr()">{{ $t('LB00000763') }}</v-btn>
          <!--삭제-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!deleteModify" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!apprModify" @click="save()">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="trnrsltmgndttledtuserslct1" mkey2="trnrsltmgntdtledtuserslct2" mkey3="trnrsltmgntdtledtuserslct3" @close="popclose" />
    <mailpopup ref="mailpopup" mkey="_trnrsltmgntdtledt1" @close="mailpopclose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/usermultislct'
import mailpopup from '../../common/mailsend'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider
let tempDataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

// 그리드 3
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

let dateVal = new Date()

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '교육훈련 결과 관리',
    key(route) {
      return `/trnmgnt/trnrsltmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    mailpopup,
    FileUpload,
  },
  data() {
    return {
      excel: false,
      files: [],
      quillChk: false,
      wkplModify: false,
      apprModify: true,
      cntModify: true,
      deleteModify: false,
      pageMoveAppr: false,
      isAdminGrp: false,
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      codeid: ['SH00000019', 'SH00000011', 'SH00000074'],
      codes: {
        SH00000019: [{ cd: '', cd_nm: '' }],
        SH00000011: [{ cd: '', cd_nm: '' }],
        SH00000011p: [{ cd: '', cd_nm: '' }],
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
      selSH00000019: {
        cd: [],
        cd_nm: [],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
      },
      totalcnt: 0,
      tmpSchData: {},
      schData: {},
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      trnId: '',
      frmTempData: {},
      per_val: 0,
      mailData: {},
      frmData: {
        ckFlag: false,
        trn_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        trn_cl_cd: '',
        trn_cl_nm: '',
        trn_nm: '',
        trn_dt: '',
        trn_bgn_dt: '',
        trn_end_dt: '',
        trn_bgn_hm: '',
        trn_bgn_hm_h: '',
        trn_bgn_hm_s: '',
        trn_end_hm: '',
        trn_end_hm_h: '',
        trn_end_hm_s: '',
        trn_time_txt: '',
        trn_note_txt: '',
        trn_prst_cd: '',
        trn_aprv_no: '',
        crt_emp_no: '',
        upt_emp_no: '',
        lsch_nm: '',
        trn_tgt_prsn_cnt: 0,
        trn_fcst_prsn_cnt: 0,
        lsch_dt: '',
        trn_atfl_no: 0,
        trn_techer_nm: '',
      },
      // 대상자정보
      userData: {
        chk_gb: '',
        trn_id: '',
        trn_tgt_emp_id: '',
        trn_tgt_emp_nm: '',
        wkpl_id: '',
        trn_fcst_cl_cd: '',
        trn_fcst_cl_nm: '',
        trn_fcst_yn: '',
        unit_nm: '',
        div_nm: '',
        team_nm: '',
      },
      // 메일정보
      mailInfoData: {
        org_1st_cd: '',
        org_2nd_cd: '',
        org_3rd_cd: '',
        cnt: '',
        cnt_y: '',
        cnt_n: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001046') // 교육 및 훈련
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(this.user.time_zone)
    sDate = this.getTzDateObj(this.user.time_zone)
    sDate.setDate(sDate.getDate() - 30)
    this.frmData.sDate = this.get_date_str_gubun(sDate, '-')
    this.frmData.eDate = this.get_date_str_gubun(eDate, '-')

    if (this.$route.params.trnrsltmgntdtledt !== null && this.$route.params.trnrsltmgntdtledt !== undefined) {
      this.frmData.trn_id = this.$route.params.trnrsltmgntdtledt
      this.trnId = this.$route.params.trnrsltmgntdtledt
      this.schData.trn_id = this.$route.params.trnrsltmgntdtledt
      this.wkplModify = false
      this.schData.auth_slct = 'Y'
    } else {
      this.frmData.trn_id = ''
      this.trnId = ''
      this.schData.trn_id = ''
      this.wkplModify = true
      this.frmData.trn_prst_cd = 'S01'
      this.frmData.wkpl_id = this.user.wkpl_id
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        trn_cl_cd: '',
        usg_yn: '',
        dept_cd: '',
        dept_nm: '',
        sDate: '',
        eDate: '',
      }
    }
    dateVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.trn_bgn_dt = this.get_date_str_gubun(dateVal, '-')
    this.frmData.trn_end_dt = this.get_date_str_gubun(dateVal, '-')
  },
  mounted() {
    // 사업장 조회
    this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
      console.log(res) // eslint-disable-line no-console
      const tempWkpl = []
      res.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm, up_cd: e.up_cd })
      })
      this.FF_00000001 = tempWkpl
      this.F_codes.F_00000001 = tempWkpl
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT' })

      // 사업장 등록&수정하는 화면에서만 필터링 할 수 있게끔...
      const cmpy = this.user.cmpn_cd
      if (this.wkplModify === true) {
        this.codChg(cmpy)
      }
    })
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempTrnCl = []
      this.codes.SH00000074 = res.SH00000074
      res.SH00000011.forEach((e) => {
        tempTrnCl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000011p = tempTrnCl
      this.codes.SH00000011p.unshift({ value: '', label: 'SELECT' })

      this.selSH00000019.cd.push('')
      this.selSH00000019.cd_nm.push('SELECT')
      res.SH00000019.forEach((i) => {
        this.selSH00000019.cd.push(i.cd)
        this.selSH00000019.cd_nm.push(i.cd_nm)
      })

      // 그리드 세팅
      const fields = [
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_tgt_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_tgt_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'unit_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'div_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'team_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_yn',
          dataType: ValueType.TEXT,
        },
      ]
      const columns = [
        {
          header: this.$t('LB00000073'), // 사번
          name: 'trn_tgt_emp_id',
          fieldName: 'trn_tgt_emp_id',
          width: '100',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'trn_tgt_emp_nm',
          fieldName: 'trn_tgt_emp_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00001071'), // Unit
          name: 'unit_nm',
          fieldName: 'unit_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00001070'), // Div
          name: 'div_nm',
          fieldName: 'div_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00001036'), // 팀명
          name: 'team_nm',
          fieldName: 'team_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00001069'), // 이수구분
          name: 'trn_fcst_cl_cd',
          fieldName: 'trn_fcst_cl_cd',
          width: '200',
          lookupDisplay: true,
          values: this.selSH00000019.cd,
          labels: this.selSH00000019.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            return ret
          },
        },
        {
          header: this.$t('LB00001068'), // 이수여부
          name: 'trn_fcst_yn',
          fieldName: 'trn_fcst_yn',
          width: '200',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
        },
      ]
      // 그리드 1 시작
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      tempDataProvider = new LocalDataProvider(false)
      tempDataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.displayOptions.selectionStyle = 'none'
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true })
      gridView.setCopyOptions({ enabled: false })

      gridView.editOptions.editable = true
      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'

      gridView.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
        gridView.commit()
        this.frmData.trn_tgt_prsn_cnt = gridView.getItemCount()
        let yCnt = 0
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
            continue
          }
          if (dataProvider.getValue(i, 'trn_fcst_yn') === 'Y') {
            yCnt++
          }
        }
        this.frmData.trn_fcst_prsn_cnt = yCnt
        const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
        this.per_val = perVal
      }.bind(this)

      // 그리드 1 끝

      // 그리드 2 시작
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields)
      gridView2 = new GridView('hdrealgrid')
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })
      gridView2.editOptions.editable = true
      gridView2.displayOptions.selectionStyle = 'block'

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝
      // 그리드 세팅
      const excelfields = [
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_tgt_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_tgt_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_yn',
          dataType: ValueType.TEXT,
        },
      ]
      const excelcolumns = [
        {
          header: this.$t('LB00000073'), // 사번
          name: 'trn_tgt_emp_id',
          fieldName: 'trn_tgt_emp_id',
          width: '100',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'trn_tgt_emp_nm',
          fieldName: 'trn_tgt_emp_nm',
          width: '100',
        },
        {
          header: this.$t('LB00001069'), // 이수구분
          name: 'trn_fcst_cl_cd',
          fieldName: 'trn_fcst_cl_cd',
          width: '200',
          lookupDisplay: true,
          values: this.selSH00000019.cd,
          labels: this.selSH00000019.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            return ret
          },
        },
        {
          header: this.$t('LB00001068'), // 이수여부
          name: 'trn_fcst_yn',
          fieldName: 'trn_fcst_yn',
          width: '200',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
        },
      ]
      // 그리드 3 시작
      dataProvider3 = new LocalDataProvider(false)
      dataProvider3.setFields(excelfields)
      gridView3 = new GridView('exceltemp')
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.setDataSource(dataProvider3)
      gridView3.setColumns(excelcolumns)
      gridView3.setFooters({ visible: false })
      gridView3.setStateBar({ visible: false })
      gridView3.setCheckBar({ visible: false })
      gridView3.editOptions.editable = true
      gridView3.displayOptions.selectionStyle = 'block'

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'
      // 그리드 3 끝
      if (this.frmData.trn_id !== '') {
        this.select()
      }
    })
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (!this.wkplModify && !this.pageMoveAppr) {
      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() || JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
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
    }
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    hourChg() {
      const val1 = this.frmData.trn_bgn_hm_h
      const val2 = this.frmData.trn_bgn_hm_s
      const val3 = this.frmData.trn_end_hm_h
      const val4 = this.frmData.trn_end_hm_s
      this.frmData.trn_bgn_hm = this.frmData.trn_bgn_hm_h.concat(this.frmData.trn_bgn_hm_s)
      this.frmData.trn_end_hm = this.frmData.trn_end_hm_h.concat(this.frmData.trn_end_hm_s)
      if (!this.isEmpty(val1) && !this.isEmpty(val2) && !this.isEmpty(val3) && !this.isEmpty(val4) && this.frmData.trn_bgn_dt === this.frmData.trn_end_dt) {
        const bgnhm = val1.concat(val2)
        const endhm = val3.concat(val4)
        return bgnhm >= endhm
      }
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    chkFile() {
      this.frmData.ckFlag = true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/trnmgnt/trn-dtl/${this.schData.trn_id}`)

      if (data.auth[0].auth !== 'Y') {
        this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
        // this.authChk = false

        this.$router.replace({
          name: `trnmgnt-trnrsltmgntdtllup-trnrsltmgntdtllup`,
          params: {
            trnrsltmgntdtllup: this.schData.trn_id,
            trn_id: this.schData.trn_id,
            schData: {
              trn_id: this.schData.trn_id,
            },
            tmpSchData: {
              cmpy_cd: this.tmpSchData.cmpy_cd,
              wkpl_id: this.tmpSchData.wkpl_id,
              trn_cl_cd: this.tmpSchData.trn_cl_cd,
              usg_yn: this.tmpSchData.usg_yn,
              dept_cd: this.tmpSchData.dept_cd,
              dept_nm: this.tmpSchData.dept_nm,
              sDate: this.tmpSchData.sDate,
              eDate: this.tmpSchData.eDate,
            },
          },
        })
      }

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
        this.per_val = perVal

        const usrinfo = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if ((this.frmData.trn_prst_cd === 'S01' && this.frmData.crt_emp_no === this.user.emp_no) || this.isAdminGrp) {
          this.deleteModify = true
        }

        if (!this.isEmpty(this.frmData.trn_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.trn_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = []
        this.frmTempData = []
      }

      if (data.user.length > 0) {
        this.userData = data.user
        dataProvider.setRows(data.user)
        tempDataProvider.setRows(data.user)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = dataProvider.getRowCount()
        this.editPerCalc()
      }

      if (data.mail.length > 0) {
        this.mailInfoData = data.mail
      }

      this.cntModifySet()
    },
    async hdselect() {
      gridView.commit()
      gridView2.commit()

      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() || JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000528'), { className: 'toast_error' }) // 저장 후 다운로드하세요.
        return
      }

      const data = await this.$axios.$get(`/api/v1/she/trnmgnt/trn-dtl-excel`, { params: this.schData })

      if (data.user.length > 0) {
        dataProvider2.setRows(data.user)
        gridView2.refresh()
        gridView2.setTopItem(0)

        this.excelExport()
      }
    },
    async save() {
      gridView.commit()

      // 파일 결과 함수
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.trn_atfl_no = data
        }
      })

      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.trn_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.trn_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 대상자
      const insertRow = []
      const updateRow = []
      const deleteRow = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        const temp = {}

        temp.trn_id = this.frmData.trn_id
        temp.wkpl_id = this.frmData.wkpl_id
        temp.trn_tgt_emp_id = dataProvider.getValue(i, 'trn_tgt_emp_id')
        temp.trn_fcst_cl_cd = dataProvider.getValue(i, 'trn_fcst_cl_cd')
        temp.trn_tgt_emp_nm = dataProvider.getValue(i, 'trn_tgt_emp_nm')
        temp.trn_fcst_yn = dataProvider.getValue(i, 'trn_fcst_yn')
        temp.username = this.user.emp_now
        console.log('row ' + i + ' getRowState : ' + dataProvider.getRowState(i)) // eslint-disable-line no-console
        if (dataProvider.getRowState(i) === 'created' || dataProvider.getRowState(i) === 'none') {
          insertRow.push(temp)
        } else if (dataProvider.getRowState(i) === 'updated') {
          updateRow.push(temp)
        } else if (dataProvider.getRowState(i) === 'deleted') {
          deleteRow.push(temp)
        }
      }
      this.frmData.insertRow = insertRow
      this.frmData.updateRow = updateRow
      this.frmData.deleteRow = deleteRow

      // 기본정보
      const infoRow = []
      const temp = {}
      let trnBgnDtVal = this.frmData.trn_bgn_dt
      if (!this.isEmpty(this.frmData.trn_bgn_dt)) {
        trnBgnDtVal = trnBgnDtVal.replace(/-/gi, '')
      }
      let trnEndDtVal = this.frmData.trn_end_dt
      if (!this.isEmpty(this.frmData.trn_end_dt)) {
        trnEndDtVal = trnEndDtVal.replace(/-/gi, '')
      }
      let bgnHmVal = this.frmData.trn_bgn_hm
      if (!this.isEmpty(this.frmData.trn_bgn_hm)) {
        bgnHmVal = bgnHmVal.replace(/:/gi, '')
      }
      let endHmVal = this.frmData.trn_end_hm
      if (!this.isEmpty(this.frmData.trn_end_hm)) {
        endHmVal = endHmVal.replace(/:/gi, '')
      }
      let stCd = ''
      if (this.frmData.trn_prst_cd !== 'S01') {
        stCd = this.frmData.trn_prst_cd
      } else {
        stCd = 'S01'
      }
      if (this.isEmpty(this.frmData.trn_fcst_prsn_cnt)) {
        this.frmData.trn_fcst_prsn_cnt = 0
      }
      if (this.isEmpty(this.frmData.trn_tgt_prsn_cnt)) {
        this.frmData.trn_tgt_prsn_cnt = 0
      }
      temp.trn_id = this.frmData.trn_id
      temp.wkpl_id = this.frmData.wkpl_id
      temp.trn_cl_cd = this.frmData.trn_cl_cd
      temp.trn_bgn_dt = trnBgnDtVal
      temp.trn_end_dt = trnEndDtVal
      temp.trn_bgn_hm = bgnHmVal
      temp.trn_end_hm = endHmVal
      temp.trn_nm = this.frmData.trn_nm
      temp.trn_time_txt = this.frmData.trn_time_txt
      temp.trn_note_txt = this.frmData.trn_note_txt
      temp.trn_fcst_prsn_cnt = this.frmData.trn_fcst_prsn_cnt
      temp.trn_tgt_prsn_cnt = this.frmData.trn_tgt_prsn_cnt
      temp.trn_techer_nm = this.frmData.trn_techer_nm
      temp.trn_prst_cd = stCd
      temp.trn_atfl_no = this.frmData.trn_atfl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/she/trnmgnt/trn-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.trn_id = res.data.key
          this.wkplModify = false
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async delInfo() {
      gridView.commit()

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.delete(`/api/v1/she/trnmgnt/trn-dtl/${this.frmData.trn_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      gridView.commit()

      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() || JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000529'), { className: 'toast_error' }) // 저장 후 결재상신하세요.
        return
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      // 기본정보
      const infoRow = []
      const temp = {}
      temp.trn_id = this.frmData.trn_id
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/she/trnmgnt/trn-dtl-appr', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          this.pageMoveAppr = true
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empno
          const url = config.default.approvalUrl7 + aprvno + empno
          window.open(url, 'trn-dtl-appr', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.replace({
            name: `trnmgnt-trnrsltmgntdtllup-trnrsltmgntdtllup`,
            params: {
              trnrsltmgntdtllup: this.schData.trn_id,
              trn_id: this.schData.trn_id,
              schData: {
                trn_id: this.schData.trn_id,
              },
              tmpSchData: {
                cmpy_cd: this.tmpSchData.cmpy_cd,
                wkpl_id: this.tmpSchData.wkpl_id,
                trn_cl_cd: this.tmpSchData.trn_cl_cd,
                usg_yn: this.tmpSchData.usg_yn,
                dept_cd: this.tmpSchData.dept_cd,
                dept_nm: this.tmpSchData.dept_nm,
                sDate: this.tmpSchData.sDate,
                eDate: this.tmpSchData.eDate,
                strHtml: res.data.strHtml,
              },
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    saveValidate() {
      gridView.commit()
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_cl_cd)) {
        this.$toast.show(this.$t('MS00000531'), { className: 'toast_error' }) // 구분을 입력해주세요.
        this.$refs.trn_cl_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_nm)) {
        this.$toast.show(this.$t('MS00000532'), { className: 'toast_error' }) // 교육/훈련명을 입력해주세요.
        this.$refs.trn_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_nm) && this.frmData.trn_nm.length > 100) {
        this.$toast.show(this.$t('MS00000533'), { className: 'toast_error' }) // 교육/훈련명은 100자 이하로 입력해주세요.
        this.$refs.trn_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_techer_nm) && this.frmData.trn_techer_nm.length > 20) {
        this.$toast.show(this.$t('MS00000534'), { className: 'toast_error' }) // 강사명은 20자 이하로 입력해주세요.
        this.$refs.trn_techer_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_bgn_dt)) {
        this.$toast.show(this.$t('MS00000535'), { className: 'toast_error' }) // 시작일을 입력해주세요.
        this.$refs.trn_bgn_dt.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_bgn_hm)) {
        this.$toast.show(this.$t('MS00000536'), { className: 'toast_error' }) // 시작시간을 입력해주세요.
        this.$refs.trn_bgn_hm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_end_dt)) {
        this.$toast.show(this.$t('MS00000537'), { className: 'toast_error' }) // 종료일을 입력해주세요.
        this.$refs.trn_end_dt.focus()
        return false
      }
      if (this.hourChg() === true) {
        this.$toast.show(this.$t('MS00000540'), { className: 'toast_error' }) // 시작시간은 종료시간보다 이전이여야 합니다.
        this.$refs.trn_bgn_hm_h.focus()
        return false
      }
      if (this.isEmpty(this.frmData.trn_time_txt)) {
        this.$toast.show(this.$t('MS00000541'), { className: 'toast_error' }) // 시간을 입력해주세요.
        this.$refs.trn_time_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_time_txt) && this.frmData.trn_time_txt.length > 100) {
        this.$toast.show(this.$t('MS00000542'), { className: 'toast_error' }) // 시간은 100자 이하로 입력해주세요.
        this.$refs.trn_time_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_note_txt) && this.frmData.trn_note_txt.length > 2000) {
        this.$toast.show(this.$t('MS00000543'), { className: 'toast_error' }) // 내용은 2000자 이하로 입력해주세요.
        this.$refs.trn_note_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_fcst_prsn_cnt) && this.frmData.trn_fcst_prsn_cnt.length > 8) {
        this.$toast.show(this.$t('MS00000544'), { className: 'toast_error' }) // 이수자는 8자 이하로 입력해주세요.
        this.$refs.trn_fcst_prsn_cnt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.trn_tgt_prsn_cnt) && this.frmData.trn_tgt_prsn_cnt.length > 8) {
        this.$toast.show(this.$t('MS00000545'), { className: 'toast_error' }) // 대상자는 8자 이하로 입력해주세요.
        this.$refs.trn_tgt_prsn_cnt.focus()
        return false
      }

      if (this.frmData.trn_fcst_prsn_cnt > this.frmData.trn_tgt_prsn_cnt) {
        this.$toast.show(this.$t('MS00000546'), { className: 'toast_error' }) // 이수자는 대상자보다 클 수 없습니다.
        this.$refs.trn_fcst_prsn_cnt.focus()
        return false
      }

      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        let passyn = null
        for (let j = 0; j < dataProvider.getRowCount(); j++) {
          // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
          if (dataProvider.getRowState(j) === 'createAndDeleted' || dataProvider.getRowState(j) === 'deleted') {
            continue
          }
          if (i === j) {
            continue
          }
          if (!this.isEmpty(dataProvider.getValue(i, 'trn_tgt_emp_id')) && !this.isEmpty(dataProvider.getValue(j, 'trn_tgt_emp_id'))) {
            if (dataProvider.getValue(j, 'trn_tgt_emp_id') === dataProvider.getValue(i, 'trn_tgt_emp_id')) {
              passyn = 'N'
            }
          }
        }
        if (passyn === 'N') {
          gridView.setCurrent({ dataRow: i, fieldName: 'trn_tgt_emp_id' })
          this.$toast.show(this.$t('MS00000548'), { className: 'toast_error' }) // 동일한 대상자가 있습니다.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'trn_fcst_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'trn_fcst_cl_cd' })
          this.$toast.show(this.$t('MS00000549'), { className: 'toast_error' }) // 이수구분을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'trn_fcst_yn'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'trn_fcst_yn' })
          this.$toast.show(this.$t('MS00000550'), { className: 'toast_error' }) // 이수여부를 입력해주세요.
          return false
        }
      }
      return true
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(false)
        this.$refs.upload.setDelete(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    async popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        console.log(returnData) // eslint-disable-line no-console
        await returnData.forEach((i) => {
          const fields = ['trn_tgt_emp_id']
          const values = [i.emp_no]
          const options = {
            fields,
            values,
            allFields: true,
            startIndex: 0,
            wrap: true,
            caseSensitive: false,
            partialMatch: true,
          }
          const passyn1 = gridView.searchItem(options)
          if (passyn1 === -1) {
            dataProvider.addRow({
              chk_gb: '',
              trn_id: this.frmData.trn_id,
              wkpl_id: this.frmData.wkpl_id,
              trn_fcst_yn: 'Y',
              trn_fcst_cl_cd: 'S01',
              trn_fcst_cl_nm: '',
              trn_tgt_emp_id: i.emp_no,
              trn_tgt_emp_nm: i.emp_nm,
              unit_nm: i.unit_nm,
              div_nm: i.div_nm,
              team_nm: i.team_nm,
            })
          } else {
            this.$toast.show(i.emp_nm + this.$t('MS00000036'), { className: 'toast_error' }) // 이미 선택 되었습니다.
          }
        })
        this.perCalc()
        this.cntModifySet()
      }
    },
    add() {
      gridView.commit()
      this.$refs.pop.open(this.frmData)
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
      this.perCalc()
      this.cntModifySet()
    },
    cntModifySet() {
      gridView.commit()
      if (this.apprModify === true && gridView.getItemCount() > 0) {
        this.cntModify = false
      } else {
        this.cntModify = true
      }
    },
    perCalc() {
      this.frmData.trn_tgt_prsn_cnt = gridView.getItemCount()
      let yCnt = 0
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (dataProvider.getValue(i, 'trn_fcst_yn') === 'Y') {
          yCnt++
        }
      }
      this.frmData.trn_fcst_prsn_cnt = yCnt
      const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
      this.per_val = perVal
    },
    editPerCalc() {
      const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
      this.per_val = perVal
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `trnmgnt-trnrsltmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            trn_cl_cd: this.tmpSchData.trn_cl_cd,
            usg_yn: this.tmpSchData.usg_yn,
            dept_cd: this.tmpSchData.dept_cd,
            dept_nm: this.tmpSchData.dept_nm,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001073') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 미이수자

      gridView2.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    excelTemp() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000950') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 업로드양식

      gridView3.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    mailpop() {
      this.mailData.email_ttl = this.frmData.trn_cl_nm + ' ' + this.frmData.trn_nm
      this.mailData.atfl_no = this.frmData.trn_atfl_no
      let mailCntt = '<p style="color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;"><br></p>\r\n'
      mailCntt += '<p style="font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;">' + '기본정보' + '</p>\r\n'
      // mailCntt +=
      //   '<table class="txc-table" width="100%" cellspacing="0" cellpadding="0" style="border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;">\r\n'
      // mailCntt += '<tbody>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '사업장' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="3" style="width: 24%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.wkpl_nm + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '구분' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="3" style="width: 24%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_cl_nm + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '등록자' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="3" style="width: 23%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.lsch_nm + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '교육/훈련명' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="7" style="width: 40%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_nm + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '강사명' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="3" style="width: 40%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_techer_nm + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '일시' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="7" style="width: 40%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_dt + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '교육시간' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="3" style="width: 40%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_time_txt + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '내용' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="11" style="width: 90%; height: 75px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + this.frmData.trn_note_txt + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td colspan="1" style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '이수실적 (이수자/대상자)' + '</p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td colspan="11" style="width: 90%; height: 75px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
      // mailCntt += '<p>' + '(' + this.frmData.trn_fcst_prsn_cnt + '/' + this.frmData.trn_tgt_prsn_cnt + ')' + this.per_val + '%' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // mailCntt += '</tbody>\r\n'
      // mailCntt += '</table>\r\n'

      mailCntt += '<p style="color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;"><br></p>\r\n'
      mailCntt += '<p style="font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;">' + this.$t('LB00001061') + '</p>\r\n' // 대상자
      mailCntt +=
        '<table class="txc-table" width="100%" cellspacing="0" cellpadding="0" style="border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;">\r\n'
      mailCntt += '<tbody>\r\n'
      for (let i = 0; i < this.mailInfoData.length; i++) {
        mailCntt += '<tr>\r\n'
        mailCntt +=
          '<td colspan="12" style="width: 90%; height: 75px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;">\r\n'
        mailCntt +=
          '<p style="font-weight: bold;">' +
          this.mailInfoData[i].org_1st_cd +
          '-' +
          this.mailInfoData[i].org_2nd_cd +
          '-' +
          this.mailInfoData[i].org_3rd_cd +
          ' ' +
          this.$t('LB00000039') + // 총
          ' ' +
          this.mailInfoData[i].cnt +
          this.$t('LB00001075') + // 건 중 이수자
          ' ' +
          this.mailInfoData[i].cnt_y +
          this.$t('LB00001076') + // 건 미이수자
          ' ' +
          this.mailInfoData[i].cnt_n +
          this.$t('LB00000040') + // 건
          '</p>\r\n'
        mailCntt += '</td>\r\n'
        mailCntt += '</tr>\r\n'
      }
      mailCntt += '</tbody>\r\n'
      mailCntt += '</table>\r\n'

      // mailCntt += '<p style="color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;"><br></p>\r\n'
      // mailCntt += '<p style="font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;">' + '대상자' + '</p>\r\n'
      // mailCntt +=
      //   '<table class="txc-table" width="100%" cellspacing="0" cellpadding="0" style="border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;">\r\n'
      // mailCntt += '<tbody>\r\n'
      // mailCntt += '<tr>\r\n'
      // mailCntt +=
      //   '<td style="width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '사번' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '성명' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + 'Unit' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + 'Div.' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '팀명' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '이수구분' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt +=
      //   '<td style="width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;">\r\n'
      // mailCntt += '<p style="font-weight: bold;">' + '이수여부' + '<br></p>\r\n'
      // mailCntt += '</td>\r\n'
      // mailCntt += '</tr>\r\n'
      // for (let i = 0; i < dataProvider.getRowCount(); i++) {
      //   let unitNm = ''
      //   if (this.isEmpty(dataProvider.getValue(i, 'unit_nm'))) {
      //     unitNm = ''
      //   }
      //   let divNm = ''
      //   if (this.isEmpty(dataProvider.getValue(i, 'div_nm'))) {
      //     divNm = ''
      //   }
      //   let teamNm = ''
      //   if (this.isEmpty(dataProvider.getValue(i, 'team_nm'))) {
      //     teamNm = ''
      //   }
      //   mailCntt += '<tr>\r\n'
      //   mailCntt +=
      //     '<td style="width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + dataProvider.getValue(i, 'trn_tgt_emp_id') + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + dataProvider.getValue(i, 'trn_tgt_emp_nm') + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + unitNm + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + divNm + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 24%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + teamNm + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + dataProvider.getValue(i, 'trn_fcst_cl_nm') + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt +=
      //     '<td style="width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;">\r\n'
      //   mailCntt += '<p style="font-weight: bold;">' + dataProvider.getValue(i, 'trn_fcst_yn') + '<br></p>\r\n'
      //   mailCntt += '</td>\r\n'
      //   mailCntt += '</tr>\r\n'
      // }
      // mailCntt += '</tbody>\r\n'
      // mailCntt += '</table>\r\n'
      this.mailData.email_cntn = mailCntt
      console.log(mailCntt) // eslint-disable-line no-console
      this.$refs.mailpopup.open(this.mailData)
    },
    mailpopclose() {},
    handleXlsFile(e) {
      this.frmData.ckFlag = true
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
      gridView.commit()
      const output = this.to_json(wb)
      const sheetNames = Object.keys(output)
      if (sheetNames.length > 0) {
        const colsObj = output[sheetNames][0]
        if (colsObj) {
          dataProvider.fillJsonData(output, { fillMode: 'append', rows: sheetNames[0] })
          // 대상자 이수자 계산
          this.perCalc()
          this.cntModifySet()
        }
      }
    },
    to_json(workbook) {
      const result = {}
      workbook.SheetNames.forEach(function (sheetName) {
        const roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {})
        // 엑셀 컬럼과 그리드 컬럼 매핑
        if (roa.length > 0) {
          roa.forEach((o) => {
            o.trn_tgt_emp_id = o.사번
            o.trn_tgt_emp_nm = o.성명
            o.trn_fcst_cl_cd = o.이수구분
            o.trn_fcst_yn = o.이수여부
          })
          result[sheetName.toLowerCase()] = roa
        }
      })
      // 코드값 세팅
      const rtnResult = this.excelCodeSet(result)
      return rtnResult
    },
    excelCodeSet(obj) {
      const result = obj
      console.log(JSON.stringify(result)) // eslint-disable-line no-console
      // 코드값 세팅
      // 엑셀 row 갯수만큼
      for (let i = 0; i < result.sheet1.length; i++) {
        // 이수구분
        const orgVal = result.sheet1[i].trn_fcst_cl_cd
        const idx = this.selSH00000019.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selSH00000019.cd[idx]
        } else {
          cdVal = 'S01'
        }
        result.sheet1[i].trn_fcst_cl_cd = cdVal

        // 이수여부
        if (result.sheet1[i].trn_fcst_yn !== 'Y' && result.sheet1[i].trn_fcst_yn !== 'N') {
          result.sheet1[i].trn_fcst_yn = 'Y'
        }
      }
      return result
    },
  },
}
</script>
<style></style>
