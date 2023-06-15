<template>
  <div>
    <div class="location">
      <h1>사업장정보</h1>
      <!-- Home > 투자계획 관리 > 사업장별 투자 관리 -->
      <span
        >Home > 사업장 정보 > <b>{{ $t('LB00001152') }}</b></span
      >
    </div>
    <div class="work_title">
      <!-- 사업장별 투자 관리 -->
      <h2>{{ $t('LB00001152') }}</h2>
    </div>
    <div class="container_detail">
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="wkpl_id" v-model="frmData.wkpl_id" placeholder="선택하세요." item-value="value" :items="F_codes.F_00000001" disabled @change="wkplChg"></v-select>
              </v-col>
              <v-col cols="1">
                <!--투자년도-->
                <div class="label_tit essn">{{ $t('LB00001150') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- <v-menu ref="menu" :close-on-content-click="false" transition="scale-transition" :nudge-right="40" min-width="290px"> -->
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      v-model="frmData.invs_year"
                      prepend-icon="event"
                      readonly
                      :disabled="!saveModify"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    ref="picker"
                    v-model="frmData.invs_year"
                    reactive
                    show-current
                    min="2010"
                    max="2030"
                    :disabled="!saveModify"
                    no-title
                    @click:year="saveDate(frmData.invs_year)"
                  ></v-date-picker>
                </v-menu>
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
                <!--연간 투자금액-->
                <div class="label_tit">{{ $t('LB00001149') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="yrly_invs_amt"
                  v-model="frmData.yrly_invs_amt"
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  disabled
                  maxlength="22"
                  type="number"
                  min="0"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.invs_prst_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정일시-->
                <div class="label_tit">{{ $t('LB00001148') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관련지침-->
                <div class="label_tit">{{ $t('LB00001147') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="invs_rel_guid_txt"
                  v-model="frmData.invs_rel_guid_txt"
                  single-line
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  maxlength="1000"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--설명-->
                <div class="label_tit">{{ $t('LB00001146') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="wkpl_note_txt"
                  v-model="frmData.wkpl_note_txt"
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  maxlength="1000"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title" style="display: flex">
        <!--투자 계획대비집행 목록-->
        <h3>{{ $t('LB00001145') }}</h3>
        <v-spacer></v-spacer>
        <!--단위 : 천원-->
        <div class="label_info ml-1">{{ $t('LB00001144') }}</div>
      </div>
      <div class="grid_header">
        <div class="grid_header_left"></div>
      </div>
      <div id="realgrid" style="width: 100%; height: 400px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--연간 계획 내용-->
        <h3>{{ $t('LB00001143') }}</h3>
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
        <div class="grid_header_left">
          {{ $t('LB00000039') }} <b>{{ planTotalcnt }}</b> {{ $t('LB00000040') }}
        </div>
        <div>
          <!--양식 다운로드-->
          <v-btn class="btn_grid_border" @click="excelTemp()">{{ $t('LB00000721') }}</v-btn>
          <!--엑셀업로드-->
          <v-btn class="btn_grid_border" :disabled="!apprModify" @click="excel = !excel">{{ $t('LB00000720') }}</v-btn>
          <!--추가-->
          <v-btn class="btn_grid_solid" :disabled="!apprModify" @click="planAdd()">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn class="btn_grid_solid" :disabled="!apprModify" @click="planDel()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 400px"></div>
      <div id="exceltemp" style="width: 100%; height: 400px; display: none"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--월별 집행 내용-->
        <h3>{{ $t('LB00001142') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ rsltTotalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn class="btn_grid_border" :disabled="!apprModify" @click="rsltAdd()">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn class="btn_grid_solid" :disabled="!apprModify" @click="rsltDel()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 400px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 첨부-->
        <h3>{{ $t('LB00001141') }}</h3>
      </div>
      <div class="group_padd"></div>
      <!-- 첨부파일 목록 -->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
        <table>
          <div v-if="true">
            <file-upload ref="upload1" input-id="file1" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
    </div>
    <div class="container_button">
      <!--목록-->
      <v-btn class="btn_detail_border" @click="goList">{{ $t('LB00000118') }}</v-btn>
      <!--삭제-->
      <v-btn class="btn_detail_border" :disabled="!deleteModify" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
      <!--저장-->
      <v-btn class="btn_detail_solid" :disabled="!apprModify" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!--결재상신-->
      <v-btn class="btn_detail_solid" :disabled="!apprModify" @click="appr">{{ $t('LB00000763') }}</v-btn>
    </div>
    <monthslct ref="pop" mkey="wkplinvsmgntdtledt1" @close="popclose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import monthslct from '../../common/monthslct'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

// 그리드 3
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

// 그리드 4
let gridView4 = GridView
let dataProvider4 = LocalDataProvider

let dateVal = new Date()

export default {
  meta: {
    title: '사업장별 투자 관리',
    key(route) {
      return `/stndinfr/wkplinvsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    monthslct,
    FileUpload,
  },
  data() {
    return {
      excel: false,
      files: [],
      quillChk: false,
      saveModify: false,
      apprModify: true,
      pageMoveAppr: false,
      deleteModify: false,
      isAdminGrp: false,
      new_yn: 'Y',
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      codeid: ['CO00000033', 'ST00000021', 'ST00000014'],
      codes: {
        CO00000033: [{ cd: '', cd_nm: '' }],
        ST00000021: [{ cd: '', cd_nm: '' }],
        ST00000014: [{ cd: '', cd_nm: '' }],
      },
      selCO00000033: {
        cd: [],
        cd_nm: [],
      },
      selST00000021: {
        cd: [],
        cd_nm: [],
      },
      selST00000014: {
        cd: [],
        cd_nm: [],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
      },
      planTotalcnt: 0,
      rsltTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      menu: false,
      userPopGb: '',
      wkplId: '',
      invsYear: '',
      frmTempData: {},
      planTempData: {},
      rsltTempData: {},
      org_yrly_invs_amt: '',
      org_wkpl_id: '',
      org_invs_year: '',
      saveData: {},
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        wkpl_nm: '',
        invs_year: '',
        yrly_invs_amt: '',
        invs_rel_guid_txt: '',
        wkpl_note_txt: '',
        invs_atfl_no: 0,
        invs_aprv_no: '',
        invs_prst_cd: '',
        invs_prst_nm: '',
        crt_emp_no: '',
        upt_dt: '',
        lsch_nm: '',
      },
      // 투자계획대비집행 정보
      totData: {
        wkpl_id: '',
        invs_year: '',
        invs_cat_cd: '',
        invs_cat_nm: '',
        invs_item_amt: '',
        amt_per: '',
        prfr_amt_tot: '',
        prfr_amt_01: '',
        prfr_amt_02: '',
        prfr_amt_03: '',
        prfr_amt_04: '',
        prfr_amt_05: '',
        prfr_amt_06: '',
        prfr_amt_07: '',
        prfr_amt_08: '',
        prfr_amt_09: '',
        prfr_amt_10: '',
        prfr_amt_11: '',
        prfr_amt_12: '',
      },
      // 계획정보
      planData: {
        wkpl_id: '',
        invs_year: '',
        invs_item_reg_sn: '',
        invs_item_nm: '',
        invs_item_amt: '',
        shcdsd_wkpl_invs_item: '',
        shcdsd_wkpl_invs_item_nm: '',
        invs_cat_cd: '',
        invs_cat_nm: '',
      },
      // 실적정보
      rsltData: {
        wkpl_id: '',
        invs_year: '',
        invs_item_reg_sn: '',
        prfr_reg_mth: '',
        prfr_reg_mth_nm: '',
        prfr_amt: '',
        prfr_txt: '',
        invs_stpl_item_cd: '',
        invs_stpl_item_nm: '',
        invs_cat_cd: '',
        invs_cat_nm: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001152') // 사업장별 투자 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  watch: {
    menu(val) {
      val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    dateVal = this.getTzDateObj(this.user.time_zone)
    dateVal.setDate(dateVal.getDate() - 30)
    this.frmData.invs_year = this.get_date_str_gubun(dateVal, '-') + ''

    if (this.$route.params.wkplinvsmgntdtledt !== null && this.$route.params.wkplinvsmgntdtledt !== undefined) {
      this.schData.key_id = this.$route.params.wkplinvsmgntdtledt
      this.saveModify = false
      this.new_yn = 'N'
    } else {
      this.schData.key_id = ''
      this.saveModify = true
      this.frmData.invs_prst_cd = 'S01'
      this.new_yn = 'Y'
      this.frmData.wkpl_id = this.user.wkpl_id
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        invs_cat_cd: '',
        invs_year: '',
      }
    }
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
      if (this.saveModify === true) {
        this.codChg(cmpy)
      }
    })
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res

      this.selCO00000033.cd.push('')
      this.selCO00000033.cd_nm.push('SELECT')
      res.CO00000033.forEach((i) => {
        this.selCO00000033.cd.push(i.cd)
        this.selCO00000033.cd_nm.push(i.cd_nm)
      })

      this.selST00000021.cd.push('')
      this.selST00000021.cd_nm.push('SELECT')
      res.ST00000021.forEach((i) => {
        this.selST00000021.cd.push(i.cd)
        this.selST00000021.cd_nm.push(i.cd_nm)
      })

      this.selST00000014.cd.push('')
      this.selST00000014.cd_nm.push('SELECT')
      res.ST00000014.forEach((i) => {
        this.selST00000014.cd.push(i.cd)
        this.selST00000014.cd_nm.push(i.cd_nm)
      })

      // 그리드 1 시작
      // 그리드 세팅
      const fields = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_amt',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_tot',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'amt_per',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_01',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_02',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_03',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_04',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_05',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_06',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_07',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_08',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_09',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_10',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_11',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_amt_12',
          dataType: ValueType.NUMBER,
        },
      ]
      const columns = [
        {
          header: this.$t('LB00000934'), // 카테고리
          name: 'invs_cat_nm',
          fieldName: 'invs_cat_nm',
          width: '100',
        },
        {
          header: this.$t('LB00001153'), // 계획금액
          name: 'invs_item_amt',
          fieldName: 'invs_item_amt',
          width: '100',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001154'), // 집행누계
          name: 'prfr_amt_tot',
          fieldName: 'prfr_amt_tot',
          width: '100',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001155'), // 실적(%)
          name: 'amt_per',
          fieldName: 'amt_per',
          width: '100',
          styleName: 'right',
          numberFormat: '#,##0',
        },
        {
          header: this.$t('LB00001156'), // 1월
          name: 'prfr_amt_01',
          fieldName: 'prfr_amt_01',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001157'), // 2월
          name: 'prfr_amt_02',
          fieldName: 'prfr_amt_02',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001158'), // 3월
          name: 'prfr_amt_03',
          fieldName: 'prfr_amt_03',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001159'), // 4월
          name: 'prfr_amt_04',
          fieldName: 'prfr_amt_04',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001160'), // 5월
          name: 'prfr_amt_05',
          fieldName: 'prfr_amt_05',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001161'), // 6월
          name: 'prfr_amt_06',
          fieldName: 'prfr_amt_06',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001162'), // 7월
          name: 'prfr_amt_07',
          fieldName: 'prfr_amt_07',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001163'), // 8월
          name: 'prfr_amt_08',
          fieldName: 'prfr_amt_08',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001164'), // 9월
          name: 'prfr_amt_09',
          fieldName: 'prfr_amt_09',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001165'), // 10월
          name: 'prfr_amt_10',
          fieldName: 'prfr_amt_10',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001166'), // 11월
          name: 'prfr_amt_11',
          fieldName: 'prfr_amt_11',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
        {
          header: this.$t('LB00001167'), // 12월
          name: 'prfr_amt_12',
          fieldName: 'prfr_amt_12',
          width: '50',
          styleName: 'right',
          numberFormat: '#,##0',
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
      ]
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.displayOptions.selectionStyle = 'block'
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: true })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })
      gridView.editOptions.editable = false
      gridView.displayOptions.selectionStyle = 'block'

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      // 그리드 1 끝

      // 그리드 2 시작
      // 그리드 세팅
      const fields2 = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_reg_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_amt',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'shcdsd_wkpl_invs_item',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'shcdsd_wkpl_invs_item_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
      ]
      const columns2 = [
        {
          header: this.$t('LB00000934'), // 카테고리
          name: 'invs_cat_cd',
          fieldName: 'invs_cat_cd',
          lookupDisplay: true,
          width: '100',
          values: this.selST00000014.cd,
          labels: this.selST00000014.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00001170'), // 약정항목
          name: 'shcdsd_wkpl_invs_item',
          fieldName: 'shcdsd_wkpl_invs_item',
          lookupDisplay: true,
          width: '100',
          values: this.selST00000021.cd,
          labels: this.selST00000021.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00001172'), // 계획내용
          name: 'invs_item_nm',
          fieldName: 'invs_item_nm',
          width: '300',
          styleName: 'left',
          editor: {
            maxLength: 200,
          },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.editable = true
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00001153'), // 계획금액
          name: 'invs_item_amt',
          fieldName: 'invs_item_amt',
          width: '100',
          styleName: 'right',
          numberFormat: '#,##0',
          editor: {
            type: 'number',
            editFormat: '#,##0',
            maxLength: 22,
          },
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
      ]
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: true })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: true })
      gridView2.editOptions.editable = true
      gridView2.displayOptions.selectionStyle = 'block'

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝

      // 그리드 2 시작
      // 그리드 세팅
      const excelFields = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_reg_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_amt',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'shcdsd_wkpl_invs_item',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'shcdsd_wkpl_invs_item_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
      ]
      const excelcolumns = [
        {
          header: this.$t('LB00000934'), // 카테고리
          name: 'invs_cat_nm',
          fieldName: 'invs_cat_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001170'), // 약정항목
          name: 'shcdsd_wkpl_invs_item_nm',
          fieldName: 'shcdsd_wkpl_invs_item_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001172'), // 계획내용
          name: 'invs_item_nm',
          fieldName: 'invs_item_nm',
          width: '300',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001153'), // 계획금액
          name: 'invs_item_amt',
          fieldName: 'invs_item_amt',
          width: '100',
          styleName: 'right',
        },
      ]

      // 그리드 4 시작
      dataProvider4 = new LocalDataProvider(false)
      dataProvider4.setFields(excelFields)
      gridView4 = new GridView('exceltemp')
      gridView4.displayOptions.selectionStyle = 'block'
      gridView4.setDataSource(dataProvider4)
      gridView4.setColumns(excelcolumns)
      gridView4.setFooters({ visible: false })
      gridView4.setStateBar({ visible: false })
      gridView4.setCheckBar({ visible: false })
      gridView4.editOptions.editable = true
      gridView4.displayOptions.selectionStyle = 'block'

      gridView4.header.height = 39
      gridView4.displayOptions.rowHeight = 40
      gridView4.footer.height = 40
      gridView4.displayOptions.fitStyle = 'fill'

      // 그리드 3 시작
      // 그리드 세팅
      const fields3 = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_item_reg_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'prfr_reg_mth',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'prfr_reg_mth_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'prfr_amt',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'prfr_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_stpl_item_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_stpl_item_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'invs_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
      ]
      const columns3 = [
        {
          header: this.$t('LB00001171'), // 실적 월
          name: 'prfr_reg_mth',
          fieldName: 'prfr_reg_mth',
          lookupDisplay: true,
          width: '100',
          values: this.selCO00000033.cd,
          labels: this.selCO00000033.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          filters: [
            {
              name: this.$t('LB00001156'), // 1월
              criteria: "value = '01'",
            },
            {
              name: this.$t('LB00001157'), // 2월
              criteria: "value = '02'",
            },
            {
              name: this.$t('LB00001158'), // 3월
              criteria: "value = '03'",
            },
            {
              name: this.$t('LB00001159'), // 4월
              criteria: "value = '04'",
            },
            {
              name: this.$t('LB00001160'), // 5월
              criteria: "value = '05'",
            },
            {
              name: this.$t('LB00001161'), // 6월
              criteria: "value = '06'",
            },
            {
              name: this.$t('LB00001162'), // 7월
              criteria: "value = '07'",
            },
            {
              name: this.$t('LB00001163'), // 8월
              criteria: "value = '08'",
            },
            {
              name: this.$t('LB00001164'), // 9월
              criteria: "value = '09'",
            },
            {
              name: this.$t('LB00001165'), // 10월
              criteria: "value = '10'",
            },
            {
              name: this.$t('LB00001166'), // 11월
              criteria: "value = '11'",
            },
            {
              name: this.$t('LB00001167'), // 12월
              criteria: "value = '12'",
            },
          ],
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000934'), // 카테고리
          name: 'invs_cat_cd',
          fieldName: 'invs_cat_cd',
          lookupDisplay: true,
          width: '100',
          values: this.selST00000014.cd,
          labels: this.selST00000014.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00001170'), // 약정항목
          name: 'invs_stpl_item_cd',
          fieldName: 'invs_stpl_item_cd',
          width: '100',
          lookupDisplay: true,
          values: this.selST00000021.cd,
          labels: this.selST00000021.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00001169'), // 집행내용
          name: 'prfr_txt',
          fieldName: 'prfr_txt',
          width: '300',
          editable: false,
          styleName: 'left',
          editor: {
            maxLength: 1000,
          },
        },
        {
          header: this.$t('LB00001168'), // 집행금액
          name: 'prfr_amt',
          fieldName: 'prfr_amt',
          width: '100',
          styleName: 'right',
          numberFormat: '#,##0',
          editor: {
            type: 'number',
            numberFormat: '#,##0',
            maxLength: 22,
          },
          footer: {
            text: 0,
            expression: 'sum',
            numberFormat: '#,##0',
          },
        },
      ]
      dataProvider3 = new LocalDataProvider(false)
      dataProvider3.setFields(fields3)
      gridView3 = new GridView('realgrid3')
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.setDataSource(dataProvider3)
      gridView3.setColumns(columns3)
      gridView3.setFooters({ visible: true })
      gridView3.setStateBar({ visible: false })
      gridView3.setCheckBar({ visible: true })
      gridView3.editOptions.editable = true
      gridView3.displayOptions.selectionStyle = 'block'

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'
      // 그리드 3 끝

      if (this.schData.key_id !== '') {
        this.select()
      }
    })
  },
  async beforePageLeave(tab, type) {
    gridView2.commit()
    gridView3.commit()
    if (!this.saveModify && !this.pageMoveAppr) {
      if (
        dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() ||
        dataProvider3.getRowStateCount('none') !== dataProvider3.getRowCount() ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)
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
    }
  },
  methods: {
    planAdd() {
      gridView2.commit()
      dataProvider2.addRow({
        wkpl_id: '',
        invs_year: '',
        invs_item_reg_sn: '',
        invs_cat_cd: '',
        shcdsd_wkpl_invs_item: '',
        invs_item_nm: '',
        invs_item_amt: '',
      })
    },
    planDel() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    rsltAdd() {
      gridView2.commit()
      gridView3.commit()
      if (dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() || dataProvider2.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000583'), { className: 'toast_error' }) // 연간 계획 내용을 먼저 저장해주세요.
        return
      }

      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'invs_cat_cd'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_cat_cd' })
          this.$toast.show(this.$t('MS00000582'), { className: 'toast_error' }) // 카테고리를 입력해주세요.
          return
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'shcdsd_wkpl_invs_item' })
          this.$toast.show(this.$t('MS00000581'), { className: 'toast_error' }) // 약정항목을 입력해주세요.
          return
        }
        // let passyn = null
        // for (let j = 0; j < dataProvider2.getRowCount(); j++) {
        //   if (i === j) {
        //     continue
        //   }
        //   if (
        //     dataProvider2.getValue(j, 'invs_cat_cd') === dataProvider2.getValue(i, 'invs_cat_cd') &&
        //     dataProvider2.getValue(j, 'shcdsd_wkpl_invs_item') === dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item')
        //   ) {
        //     passyn = 'N'
        //   }
        // }
        // if (passyn === 'N') {
        //   gridView2.setCurrent({ dataRow: i, fieldName: 'invs_cat_cd' })
        //   this.$toast.show('동일한 카테고리&약정항목이 있습니다.', { className: 'toast_error' }) // 동일한 카테고리&약정항목이 있습니다.
        //   return
        // }
      }
      this.$refs.pop.open(this.frmData)
    },
    rsltDel() {
      gridView3.commit()
      dataProvider3.setOptions({ softDeleting: true })
      const checkedRows = gridView3.getCheckedRows()
      dataProvider3.hideRows(checkedRows)
      dataProvider3.removeRows(checkedRows)
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      return sYear
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
      const data = await this.$axios.$get(`/api/v1/st/stndinfr/invs-mgnt-dtl/${this.schData.key_id}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터

        this.org_yrly_invs_amt = data.basic[0].yrly_invs_amt

        const usrinfo = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if ((this.frmData.invs_prst_cd === 'S01' && this.frmData.crt_emp_no === this.user.emp_no) || this.isAdminGrp) {
          this.deleteModify = true
        }

        if (!this.isEmpty(this.frmData.invs_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.invs_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = [
          {
            ckFlag: false,
            wkpl_id: '',
            wkpl_nm: '',
            invs_year: '',
            yrly_invs_amt: '',
            invs_rel_guid_txt: '',
            wkpl_note_txt: '',
            invs_atfl_no: 0,
            invs_aprv_no: '',
            invs_prst_cd: '',
            invs_prst_nm: '',
            upt_dt: '',
            lsch_nm: '',
          },
        ]
        this.frmTempData = []

        this.frmData.invs_year = this.org_invs_year
        this.frmData.wkpl_id = this.org_wkpl_id
      }

      if (data.plan.length > 0) {
        this.planData = data.plan
        dataProvider2.setRows(data.plan)
        gridView2.refresh()
        gridView2.setTopItem(0)
        this.planTotalcnt = dataProvider2.getRowCount()

        dataProvider4.setRows(data.plan)
        gridView4.refresh()
        gridView4.setTopItem(0)
      } else {
        dataProvider2.clearRows()
        dataProvider4.clearRows()
        gridView2.refresh()
        gridView2.setTopItem(0)
        gridView4.refresh()
        gridView4.setTopItem(0)

        this.planTotalcnt = dataProvider2.getRowCount()
      }

      if (data.rslt.length > 0) {
        this.rsltData = data.rslt
        dataProvider3.setRows(data.rslt)
        gridView3.refresh()
        gridView3.setTopItem(0)
        this.rsltTotalcnt = dataProvider3.getRowCount()
      } else {
        dataProvider3.clearRows()
        gridView3.refresh()
        gridView3.setTopItem(0)

        this.rsltTotalcnt = dataProvider3.getRowCount()
      }

      if (data.tot.length > 0) {
        this.totData = data.tot
        dataProvider.setRows(data.tot)
        gridView.refresh()
        gridView.setTopItem(0)
      } else {
        dataProvider.clearRows()
        gridView.refresh()
        gridView.setTopItem(0)
      }

      if (data.excel.length > 0) {
        dataProvider4.setRows(data.excel)
        gridView4.refresh()
        gridView4.setTopItem(0)
      } else {
        dataProvider4.clearRows()
        gridView4.refresh()
        gridView4.setTopItem(0)
      }
    },
    async save() {
      this.saveData = {}
      gridView2.commit()
      gridView3.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.invs_atfl_no = data
        }
      })

      if (
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        dataProvider3.getRowStateCount('none') === dataProvider3.getRowCount()
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.invs_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.invs_atfl_no}`).then((data) => {
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

      // 계획정보
      const insertPlanRow = []
      const updatePlanRow = []
      const deletePlanRow = []

      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        const temp = {}

        temp.wkpl_id = this.frmData.wkpl_id
        temp.invs_year = this.frmData.invs_year
        temp.invs_item_reg_sn = dataProvider2.getValue(i, 'invs_item_reg_sn')
        temp.invs_item_nm = dataProvider2.getValue(i, 'invs_item_nm')
        temp.invs_item_amt = dataProvider2.getValue(i, 'invs_item_amt')
        temp.shcdsd_wkpl_invs_item = dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item')
        temp.invs_cat_cd = dataProvider2.getValue(i, 'invs_cat_cd')

        if (dataProvider2.getRowState(i) === 'created' || dataProvider2.getRowState(i) === 'none') {
          insertPlanRow.push(temp)
        } else if (dataProvider2.getRowState(i) === 'updated') {
          updatePlanRow.push(temp)
        } else if (dataProvider2.getRowState(i) === 'deleted') {
          deletePlanRow.push(temp)
        }
      }
      this.saveData.insertPlanRow = insertPlanRow
      this.saveData.updatePlanRow = updatePlanRow
      this.saveData.deletePlanRow = deletePlanRow

      // 실적정보
      if (dataProvider3.getRowStateCount('updated') > 0 || dataProvider3.getRowStateCount('deleted') > 0 || dataProvider3.getRowStateCount('created') > 0) {
        const insertRsltRow = []
        const updateRsltRow = []
        const deleteRsltRow = []

        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          const temp = {}

          temp.wkpl_id = this.frmData.wkpl_id
          temp.invs_year = this.frmData.invs_year
          temp.invs_item_reg_sn = dataProvider3.getValue(i, 'invs_item_reg_sn')
          temp.prfr_reg_mth = dataProvider3.getValue(i, 'prfr_reg_mth')
          temp.prfr_amt = dataProvider3.getValue(i, 'prfr_amt')
          temp.prfr_txt = dataProvider3.getValue(i, 'prfr_txt')
          temp.invs_stpl_item_cd = dataProvider3.getValue(i, 'invs_stpl_item_cd')
          temp.invs_cat_cd = dataProvider3.getValue(i, 'invs_cat_cd')

          if (dataProvider3.getRowState(i) === 'created') {
            insertRsltRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'updated') {
            updateRsltRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'deleted') {
            deleteRsltRow.push(temp)
          }
        }
        this.saveData.insertRsltRow = insertRsltRow
        this.saveData.updateRsltRow = updateRsltRow
        this.saveData.deleteRsltRow = deleteRsltRow
      }

      // 기본정보
      const infoRow = []
      const temp = {}

      let stCd = ''
      if (this.frmData.invs_prst_cd !== 'S01') {
        stCd = this.frmData.invs_prst_cd
      } else {
        stCd = 'S01'
      }

      temp.wkpl_id = this.frmData.wkpl_id
      temp.invs_year = this.frmData.invs_year
      temp.yrly_invs_amt = this.frmData.yrly_invs_amt
      temp.invs_rel_guid_txt = this.frmData.invs_rel_guid_txt
      temp.wkpl_note_txt = this.frmData.wkpl_note_txt
      temp.invs_prst_cd = stCd
      temp.invs_atfl_no = this.frmData.invs_atfl_no
      temp.new_yn = this.new_yn
      infoRow.push(temp)
      this.saveData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      console.log(this.saveData) // eslint-disable-line no-console

      await this.$axios.put('/api/v1/st/stndinfr/invs-mgnt-dtl', this.saveData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.key_id = res.data.key1 + '-' + res.data.key2
          this.new_yn = 'N'
          this.select()
          this.saveModify = false
        } else if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async delInfo() {
      gridView2.commit()
      gridView3.commit()

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }
      const key = this.frmData.wkpl_id + '-' + this.frmData.invs_year
      await this.$axios.delete(`/api/v1/st/stndinfr/invs-mgnt-dtl/${key}`).then((res) => {
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
      gridView2.commit()
      gridView3.commit()
      if (dataProvider3.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000584'), { className: 'toast_error' }) // 월별 집행 내용을 저장해주세요.
        return
      }

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000092')) // 결재상신하시겠습니까?
      if (!res) {
        return
      }

      // 기본정보
      const infoRow = []
      const temp = {}

      temp.wkpl_id = this.frmData.wkpl_id
      temp.invs_year = this.frmData.invs_year
      temp.invs_aprv_no = this.frmData.invs_aprv_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/st/stndinfr/invs-mgnt-dtl-appr', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.apprAcpt()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async apprAcpt() {
      const infoRow = []
      const temp = {}
      temp.wkpl_id = this.frmData.wkpl_id
      temp.invs_year = this.frmData.invs_year
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/st/stndinfr/invs-mgnt-dtl-appr-af', this.frmData).then((res) => {
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
          const url = config.default.approvalUrl8 + aprvno + empno
          window.open(url, 'invs-mgnt-dtl-appr-af', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `stndinfr-wkplinvsmgntdtllup-wkplinvsmgntdtllup`,
            params: {
              wkplinvsmgntdtllup: this.schData.key_id,
              tmpSchData: {
                cmpy_cd: this.tmpSchData.cmpy_cd,
                wkpl_id: this.tmpSchData.wkpl_id,
                invs_cat_cd: this.tmpSchData.invs_cat_cd,
                invs_year: this.tmpSchData.invs_year,
              },
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    saveValidate() {
      gridView2.commit()
      gridView3.commit()
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.invs_year)) {
        this.$toast.show(this.$t('MS00000597'), { className: 'toast_error' }) // 투자년도를 입력해주세요.
        this.$refs.invs_year.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.yrly_invs_amt)) {
      //   this.$toast.show('연간 투자금액을 입력해주세요.', { className: 'toast_error' }) // 연간 투자금액을 입력해주세요.
      //   this.$refs.yrly_invs_amt.focus()
      //   return false
      // }
      if (!this.isEmpty(this.frmData.yrly_invs_amt) && this.frmData.yrly_invs_amt.length > 22) {
        this.$toast.show(this.$t('MS00000596'), { className: 'toast_error' }) // 연간 투자금액은 22자 이하로 입력해주세요.
        this.$refs.yrly_invs_amt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.invs_rel_guid_txt) && this.frmData.invs_rel_guid_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000595'), { className: 'toast_error' }) // 관련지침은 1000자 이하로 입력해주세요.
        this.$refs.invs_rel_guid_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.wkpl_note_txt) && this.frmData.wkpl_note_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000594'), { className: 'toast_error' }) // 설명은 1000자 이하로 입력해주세요.
        this.$refs.wkpl_note_txt.focus()
        return false
      }

      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'invs_cat_cd'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_cat_cd' })
          this.$toast.show(this.$t('MS00000582'), { className: 'toast_error' }) // 카테고리를 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'shcdsd_wkpl_invs_item' })
          this.$toast.show(this.$t('MS00000581'), { className: 'toast_error' }) // 약정항목을 입력해주세요.
          return false
        }
        // let passyn = null
        // for (let j = 0; j < dataProvider2.getRowCount(); j++) {
        //   if (dataProvider2.getRowState(j) === 'createAndDeleted' || dataProvider2.getRowState(j) === 'deleted') {
        //     continue
        //   }
        //   if (i === j) {
        //     continue
        //   }
        //   if (
        //     dataProvider2.getValue(j, 'invs_cat_cd') === dataProvider2.getValue(i, 'invs_cat_cd') &&
        //     dataProvider2.getValue(j, 'shcdsd_wkpl_invs_item') === dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item')
        //   ) {
        //     passyn = 'N'
        //   }
        // }
        // if (passyn === 'N') {
        //   gridView2.setCurrent({ dataRow: i, fieldName: 'invs_cat_cd' })
        //   this.$toast.show('동일한 카테고리&약정항목이 있습니다.', { className: 'toast_error' }) // 동일한 카테고리&약정항목이 있습니다.
        //   return false
        // }
        if (this.isEmpty(dataProvider2.getValue(i, 'invs_item_nm'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_item_nm' })
          this.$toast.show(this.$t('MS00000593'), { className: 'toast_error' }) // 계획내용을 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'invs_item_nm').length > 200) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_item_nm' })
          this.$toast.show(this.$t('MS00000592'), { className: 'toast_error' }) // 계획내용은 200자 이하로 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'invs_item_amt'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_item_amt' })
          this.$toast.show(this.$t('MS00000591'), { className: 'toast_error' }) // 계획금액을 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'invs_item_amt').length > 22) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'invs_item_amt' })
          this.$toast.show(this.$t('MS00000590'), { className: 'toast_error' }) // 계획금액은 22자 이하로 입력해주세요.
          return false
        }
      }

      gridView3.commit()
      for (let i = 0; i < dataProvider3.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider3.getRowState(i) === 'createAndDeleted' || dataProvider3.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider3.getValue(i, 'prfr_reg_mth'))) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'prfr_reg_mth' })
          this.$toast.show(this.$t('MS00000589'), { className: 'toast_error' }) // 실적 월을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider3.getValue(i, 'invs_cat_cd'))) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'invs_cat_cd' })
          this.$toast.show(this.$t('MS00000582'), { className: 'toast_error' }) // 카테고리를 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider3.getValue(i, 'invs_stpl_item_cd'))) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'invs_stpl_item_cd' })
          this.$toast.show(this.$t('MS00000581'), { className: 'toast_error' }) // 약정항목을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider3.getValue(i, 'prfr_txt'))) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'prfr_txt' })
          this.$toast.show(this.$t('MS00000588'), { className: 'toast_error' }) // 집행내용을 입력해주세요.
          return false
        }
        if (dataProvider3.getValue(i, 'prfr_txt').length > 1000) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'prfr_txt' })
          this.$toast.show(this.$t('MS00000587'), { className: 'toast_error' }) // 집행내용은 1000자 이하로 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider3.getValue(i, 'prfr_amt'))) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'prfr_amt' })
          this.$toast.show(this.$t('MS00000586'), { className: 'toast_error' }) // 집행금액을 입력해주세요.
          return false
        }
        if (dataProvider3.getValue(i, 'prfr_amt').length > 22) {
          gridView3.setCurrent({ dataRow: i, fieldName: 'prfr_amt' })
          this.$toast.show(this.$t('MS00000585'), { className: 'toast_error' }) // 집행금액은 22자 이하로 입력해주세요.
          return false
        }
      }
      return true
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
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
        name: `stndinfr-wkplinvsmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            invs_cat_cd: this.tmpSchData.invs_cat_cd,
            invs_year: this.tmpSchData.invs_year,
          },
        },
      })
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.frmData.invs_year = dd

      if (!this.isEmpty(this.frmData.wkpl_id) && !this.isEmpty(this.frmData.invs_year)) {
        this.schData.key_id = this.frmData.wkpl_id + '-' + this.frmData.invs_year
        this.org_invs_year = this.frmData.invs_year
        this.org_wkpl_id = this.frmData.wkpl_id
        this.new_yn = 'N'
        this.select()
      }
    },
    wkplChg() {
      if (!this.isEmpty(this.frmData.wkpl_id) && !this.isEmpty(this.frmData.invs_year)) {
        this.schData.key_id = this.frmData.wkpl_id + '-' + this.frmData.invs_year
        this.org_invs_year = this.frmData.invs_year
        this.org_wkpl_id = this.frmData.wkpl_id
        this.new_yn = 'N'
        this.select()
      }
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        console.log(returnData) // eslint-disable-line no-console
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
          if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
            continue
          }
          let passyn = 'Y'
          for (let j = 0; j < dataProvider3.getRowCount(); j++) {
            if (
              dataProvider3.getValue(j, 'invs_item_reg_sn') === dataProvider2.getValue(i, 'invs_item_reg_sn') &&
              dataProvider3.getValue(j, 'prfr_reg_mth') === returnData[0].monthval &&
              dataProvider3.getValue(j, 'invs_cat_cd') === dataProvider2.getValue(i, 'invs_cat_cd') &&
              dataProvider3.getValue(j, 'invs_stpl_item_cd') === dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item')
            ) {
              if (dataProvider3.getRowState(i) === 'createAndDeleted' || dataProvider3.getRowState(i) === 'deleted') {
                continue
              }
              passyn = 'N'
            }
          }
          if (passyn === 'Y') {
            gridView3.commit()
            dataProvider3.addRow({
              wkpl_id: this.frmData.wkpl_id,
              invs_year: this.frmData.invs_year,
              invs_item_reg_sn: dataProvider2.getValue(i, 'invs_item_reg_sn'),
              prfr_reg_mth: returnData[0].monthval,
              prfr_amt: 0,
              prfr_txt: dataProvider2.getValue(i, 'invs_item_nm'),
              invs_cat_cd: dataProvider2.getValue(i, 'invs_cat_cd'),
              invs_stpl_item_cd: dataProvider2.getValue(i, 'shcdsd_wkpl_invs_item'),
            })
          }
        }
      }
    },
    amtCalc() {
      let amt = this.org_yrly_invs_amt
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        amt = amt + dataProvider2.getValue(i, 'invs_item_amt')
      }
      this.frmData.yrly_invs_amt = amt
    },
    excelTemp() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000950') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 업로드양식

      gridView4.exportGrid({
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
      gridView2.commit()
      const output = this.to_json(wb)
      const sheetNames = Object.keys(output)
      if (sheetNames.length > 0) {
        const colsObj = output[sheetNames][0]
        if (colsObj) {
          dataProvider2.fillJsonData(output, { rows: sheetNames[0] })
          if (!this.isEmpty(this.org_yrly_invs_amt)) {
            this.amtCalc()
          }
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
            o.invs_cat_cd = o.카테고리
            o.shcdsd_wkpl_invs_item = o.약정항목
            o.invs_item_nm = o.계획내용
            o.invs_item_amt = o.계획금액
          })
          result[sheetName] = roa
        }
      })
      // 코드값 세팅
      const rtnResult = this.excelCodeSet(result)
      return rtnResult
    },
    excelCodeSet(obj) {
      const result = obj
      // 코드값 세팅
      // 엑셀 row 갯수만큼
      for (let i = 0; i < result.sheet1.length; i++) {
        // 카테고리
        const orgVal = result.sheet1[i].invs_cat_cd
        const idx = this.selST00000014.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selST00000014.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].invs_cat_cd = cdVal

        // 약정항목
        const itemVal = result.sheet1[i].shcdsd_wkpl_invs_item
        const itemIdx = this.selST00000021.cd_nm.indexOf(itemVal)
        let itemCdVal = ''
        if (itemIdx > -1) {
          itemCdVal = this.selST00000021.cd[itemIdx]
        } else {
          itemCdVal = ''
        }
        result.sheet1[i].shcdsd_wkpl_invs_item = itemCdVal
      }
      return result
    },
  },
}
</script>
<style></style>
