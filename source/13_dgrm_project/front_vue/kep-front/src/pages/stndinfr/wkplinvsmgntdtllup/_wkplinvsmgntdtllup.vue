<template>
  <div>
    <div class="location">
      <!-- Home > 투자계획 관리 > 사업장별 투자 관리 -->
      <span>Home > {{ $t('LB00001151') }} > {{ $t('LB00001152') }}</span>
    </div>
    <div class="work_title">
      <!-- 사업장별 투자 관리 -->
      <h2>{{ $t('LB00001152') }}</h2>
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
                <!--투자년도-->
                <div class="label_tit">{{ $t('LB00001150') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.invs_year }}</div>
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
                <!--연간 투자금액 -->
                <div class="label_tit">{{ $t('LB00001149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.yrly_invs_amt }}</div>
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
                <div class="label_con">{{ frmData.invs_rel_guid_txt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--설명-->
                <div class="label_tit">{{ $t('LB00001146') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.wkpl_note_txt }}</div>
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
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ planTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 400px"></div>
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

let dateVal = new Date()

export default {
  meta: {
    title: '사업장별 투자 관리',
    key(route) {
      return `/stndinfr/wkplinvsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      quillChk: false,
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      planTotalcnt: 0,
      rsltTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      userPopGb: '',
      wkplId: '',
      invsYear: '',
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
        upt_dt: '',
        lsch_nm: '',
      },
      // 투자계획대비집행 정보
      totData: {
        wkpl_id: '',
        invs_year: '',
        invs_cat_cd: '',
        invs_stpl_item_cd: '',
        invs_cat_nm: '',
        invs_stpl_item_nm: '',
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
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    dateVal = this.getTzDateObj(this.user.time_zone)
    dateVal.setDate(dateVal.getDate() - 30)
    this.frmData.invs_year = this.get_date_str_gubun(dateVal, '-')
    if (this.$route.params.wkplinvsmgntdtllup !== null && this.$route.params.wkplinvsmgntdtllup !== undefined) {
      this.schData.key_id = this.$route.params.wkplinvsmgntdtllup
    } else {
      this.schData.key_id = ''
      this.frmData.invs_prst_cd = 'S01'
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
        fieldName: 'invs_stpl_item_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_cat_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'invs_stpl_item_nm',
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
        footer: {
          text: 0,
          expression: 'sum',
          numberFormat: '#,##0',
        },
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
    ]
    const columns2 = [
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
    gridView2.setCheckBar({ visible: false })
    gridView2.editOptions.editable = false
    gridView2.displayOptions.selectionStyle = 'block'

    gridView2.header.height = 39
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'
    // 그리드 2 끝

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
    ]
    const columns3 = [
      {
        header: this.$t('LB00001171'), // 실적 월
        name: 'prfr_reg_mth_nm',
        fieldName: 'prfr_reg_mth_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000934'), // 카테고리
        name: 'invs_cat_nm',
        fieldName: 'invs_cat_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001170'), // 약정항목
        name: 'invs_stpl_item_nm',
        fieldName: 'invs_stpl_item_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001169'), // 집행내용
        name: 'prfr_txt',
        fieldName: 'prfr_txt',
        width: '300',
        styleName: 'left',
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
    gridView3.setCheckBar({ visible: false })
    gridView3.editOptions.editable = false
    gridView3.displayOptions.selectionStyle = 'block'

    gridView3.header.height = 39
    gridView3.displayOptions.rowHeight = 40
    gridView3.footer.height = 40
    gridView3.displayOptions.fitStyle = 'fill'
    // 그리드 3 끝

    if (this.schData.key_id !== '') {
      this.select()
    }
  },
  methods: {
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

        if (!this.isEmpty(this.frmData.invs_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.invs_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = []
      }

      if (data.plan.length > 0) {
        this.planData = data.plan
        dataProvider2.setRows(data.plan)
        gridView2.refresh()
        gridView2.setTopItem(0)
        this.planTotalcnt = dataProvider2.getRowCount()
      }

      if (data.rslt.length > 0) {
        this.rsltData = data.rslt
        dataProvider3.setRows(data.rslt)
        gridView3.refresh()
        gridView3.setTopItem(0)
        this.rsltTotalcnt = dataProvider3.getRowCount()
      }

      if (data.tot.length > 0) {
        this.totData = data.tot
        dataProvider.setRows(data.tot)
        gridView.refresh()
        gridView.setTopItem(0)
      }
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
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
  },
}
</script>
<style></style>
