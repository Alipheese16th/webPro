<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 작업허가 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000928') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업허가 관리 -->
      <h2>{{ $t('LB00000928') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 작업 기본정보-->
        <h3>{{ $t('LB00000862') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--작업 계획번호-->
                <div class="label_tit">{{ $t('LB00000863') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.con_id }}</div>
              </v-col>
              <v-col cols="1">
                <!--작업 사업장-->
                <div class="label_con">{{ $t('LB00001299') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cmpy_nm }} - {{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--작업 요청자-->
                <div class="label_tit">{{ $t('LB00000864') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.con_rq_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업 계획기간-->
                <div class="label_tit">{{ $t('LB00000865') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!-- 작업 계획일자 -->
                <div class="label_con">{{ frmData.con_bgn_dt }} ~ {{ frmData.con_end_dt }}</div>
                <!-- 주중-->
                <v-checkbox ref="wekd_con_yn" v-model="c_wekd_con_yn" :label="$t('LB00000866')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 주말-->
                <v-checkbox ref="wknd_con_yn" v-model="c_wknd_con_yn" :label="$t('LB00000867')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 야간-->
                <v-checkbox ref="nght_con_yn" v-model="c_nght_con_yn" :label="$t('LB00000868')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
              </v-col>
              <v-col cols="1">
                <!--작업 주관부서-->
                <div class="label_tit">{{ $t('LB00000869') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.con_rspn_org_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업명-->
                <div class="label_tit">{{ $t('LB00000636') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.con_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--긴급작업 여부-->
                <div class="label_tit">{{ $t('LB00000870') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.ugnc_con_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--협력사명-->
                <div class="label_tit">{{ $t('LB00000699') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--협력사 작업책임자-->
                <div class="label_tit">{{ $t('LB00000871') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_pric_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--협력사 담당자 전화번호-->
                <div class="label_tit">{{ $t('LB00000872') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_pric_tphn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업내용-->
                <div class="label_tit">{{ $t('LB00000873') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="con_txt" v-model="frmData.con_txt" outlined single-line :hide-details="true" auto-grow rows="3" readonly dense></v-textarea>
              </v-col>
            </v-row>
          </v-container>
          <div class="group_padd"></div>
        </v-col>
      </v-row>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 작업허가정보-->
        <h3>{{ $t('LB00000875') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--허가번호-->
                <div class="label_tit">{{ $t('LB00000876') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_prms_id }}</div>
              </v-col>
              <v-col cols="1">
                <!--작업일시-->
                <div class="label_tit">{{ $t('LB00000878') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!--작업일시-->
                <div class="label_con">{{ reqData.con_dt }} {{ reqData.con_bgn_hm }} ~ {{ reqData.con_end_hm }}</div>
                <!-- 주중-->
                <v-checkbox ref="wekd_con_yn_req" v-model="c_wekd_con_yn_req" :label="$t('LB00000866')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 주말-->
                <v-checkbox ref="wknd_con_yn_req" v-model="c_wknd_con_yn_req" :label="$t('LB00000867')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 야간-->
                <v-checkbox ref="nght_con_yn_req" v-model="c_nght_con_yn_req" :label="$t('LB00000868')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업종류-->
                <div class="label_tit">{{ $t('LB00000880') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!-- 화기-->
                <v-checkbox ref="h_yn" v-model="c_h_yn" :label="$t('LB00000881')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 고소-->
                <v-checkbox ref="g_yn" v-model="c_g_yn" :label="$t('LB00000882')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 밀폐-->
                <v-checkbox ref="m_yn" v-model="c_m_yn" :label="$t('LB00000883')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 전기차단-->
                <v-checkbox ref="j_yn" v-model="c_j_yn" :label="$t('LB00000884')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 유해/위험물질-->
                <v-checkbox ref="u_yn" v-model="c_u_yn" :label="$t('LB00000885')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 중장비사용-->
                <v-checkbox ref="v_yn" v-model="c_v_yn" :label="$t('LB00000886')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
                <!-- 일반-->
                <v-checkbox ref="n_yn" v-model="c_n_yn" :label="$t('LB00000887')" height="30" :hide-details="true" class="ml-1" disabled></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업위치-->
                <div class="label_tit">{{ $t('LB00000888') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_oprn_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_oprn_dtl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--PSM대상-->
                <div class="label_tit">{{ $t('LB00000889') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.psm_tgt_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관리감독자-->
                <div class="label_tit">{{ $t('LB00000890') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_supe_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--작업 담당자-->
                <div class="label_tit">{{ $t('LB00000891') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_oprn_supe_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--안전관리(담당)자-->
                <div class="label_tit">{{ $t('LB00000892') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ reqData.con_sft_rspn_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업내용-->
                <div class="label_tit">{{ $t('LB00000873') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="con_oprn_txt" v-model="reqData.con_oprn_txt" outlined single-line :hide-details="true" auto-grow rows="3" readonly dense></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--기타 주의사항-->
                <div class="label_tit">{{ $t('LB00000893') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="oth_prct_txt" v-model="reqData.oth_prct_txt" outlined single-line :hide-details="true" auto-grow rows="3" readonly dense></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div>
      <v-tabs align-with-title slider-size="3" color="#062C5E">
        <v-tabs-slider color="#062C5E"></v-tabs-slider>
        <!-- 작업준수사항 -->
        <v-tab :style="styleObject" @click="tabChg('1')">{{ $t('LB00000894') }}</v-tab>
        <!-- 작업인원 -->
        <v-tab :style="styleObject" @click="tabChg('2')">{{ $t('LB00000895') }}</v-tab>
        <!-- 첨부파일 -->
        <v-tab :style="styleObject" @click="tabChg('3')">{{ $t('LB00000121') }}</v-tab>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header">
                <!--총 건-->
                <div class="grid_header_left">{{ $t('LB00000039') }} {{ obeyTotalcnt }}{{ $t('LB00000040') }}</div>
              </div>
              <div id="realgrid" style="width: 100%; height: 481px"></div>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header">
                <!--총 건-->
                <div class="grid_header_left">{{ $t('LB00000039') }} {{ oprrTotalcnt }}{{ $t('LB00000040') }}</div>
              </div>
              <div id="realgrid2" style="width: 100%; height: 481px"></div>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header"></div>
              <div class="sub_title">
                <!-- 작업 계획 첨부파일 정보-->
                <h3>{{ $t('LB00000897') }}</h3>
              </div>
              <div class="container_filelist" style="max-height: 100px">
                <table>
                  <div v-if="true">
                    <file-upload ref="upload1" :multiple="true" />
                  </div>
                </table>
                <div class="group_padd"></div>
              </div>
            </div>
          </v-card>
        </v-tab-item>
      </v-tabs>
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
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '작업허가 관리',
    key(route) {
      return `/cstprms/cstprmsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      oprrTotalcnt: 0,
      obeyTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      selectOprn: '',
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      menu5: false,
      conId: '',
      prmsId: '',
      c_h_yn: false,
      c_g_yn: false,
      c_m_yn: false,
      c_j_yn: false,
      c_u_yn: false,
      c_v_yn: false,
      c_n_yn: false,
      c_wekd_con_yn: false,
      c_wknd_con_yn: false,
      c_nght_con_yn: false,
      c_wekd_con_yn_req: false,
      c_wknd_con_yn_req: false,
      c_nght_con_yn_req: false,
      frmData: {
        ckFlag: false,
        con_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        con_rq_emp_id: '',
        con_rq_emp_nm: '',
        con_bgn_dt: '',
        con_end_dt: '',
        wekd_con_yn: '',
        wknd_con_yn: '',
        nght_con_yn: '',
        con_rspn_org_cd: '',
        con_rspn_org_nm: '',
        con_nm: '',
        ugnc_con_yn: '',
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        otsr_con_bsen_pric_nm: '',
        otsr_con_bsen_pric_tphn: '',
        con_txt: '',
        oprn_plan_atfl_no: 0,
      },
      // 요청정보
      reqData: {
        ckFlag: false,
        con_id: '',
        con_prms_id: '',
        con_dt: '',
        con_bgn_hm: '',
        con_end_hm: '',
        wekd_con_yn: '',
        wknd_con_yn: '',
        nght_con_yn: '',
        psm_tgt_yn: '',
        con_oprn_wkpl_loc_id: '',
        con_oprn_wkpl_loc_nm: '',
        con_oprn_dtl_loc_nm: '',
        con_supe_emp_id: '',
        con_supe_emp_nm: '',
        con_sft_rspn_emp_id: '',
        con_sft_rspn_emp_nm: '',
        con_oprn_supe_emp_id: '',
        con_oprn_supe_emp_nm: '',
        con_oprn_txt: '',
        oth_prct_txt: '',
        con_req_atfl_no: 0,
        con_req_prst_cd: '',
        con_req_aprv_no: '',
      },
      // 작업정보
      oprnData: {
        h_yn: '',
        g_yn: '',
        m_yn: '',
        j_yn: '',
        u_yn: '',
        v_yn: '',
        n_yn: '',
        con_prms_id: '',
      },
      // 작업준수사항
      obeyData: {
        chk_yn: '',
        con_prms_id: '',
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_dtl_cl_cd: '',
        wkpl_chec_dtl_cl_nm: '',
        wkpl_chec_cl_nm: '',
        wkpl_chec_txt: '',
        wkpl_chec_sn: '',
        wkpl_chec_in_cl_cd: '',
        mand_chec_yn: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      },
      // 작업인원
      oprrData: {
        oprr_id: '',
        oprr_nm: '',
        con_prms_id: '',
        oprn_cl_cd: '',
        oprn_cl_nm: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000928') // 작업허가 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.cstprmsmgntdtllup !== null && this.$route.params.cstprmsmgntdtllup !== undefined) {
      this.schData.key_id = this.$route.params.cstprmsmgntdtllup
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        con_nm: '',
        con_oprn_wkpl_loc_id: '',
        con_oprn_wkpl_loc_nm: '',
        con_rspn_org_cd: '',
        con_rspn_org_nm: '',
        con_rq_emp_id: '',
        con_rq_emp_nm: '',
        con_req_prst_cd: '',
        sDate: '',
        eDate: '',
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'chk_yn',
        dataType: ValueType.BOOLEAN,
      },
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_dtl_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_dtl_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_in_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mand_chec_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_req_prst_cd',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000880'), // 작업종류
        name: 'wkpl_chec_dtl_cl_nm',
        fieldName: 'wkpl_chec_dtl_cl_nm',
        width: '100',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000904'), // No.
        name: 'wkpl_chec_sn',
        fieldName: 'wkpl_chec_sn',
        width: '50',
        editable: false,
      },
      {
        header: this.$t('LB00000894'), // 작업준수사항
        name: 'wkpl_chec_txt',
        fieldName: 'wkpl_chec_txt',
        width: '200',
        editable: false,
        styleName: 'left',
      },
      {
        header: this.$t('LB00000905'), // 작업해당 여부
        name: 'chk_yn',
        fieldName: 'chk_yn',
        width: '80',
        renderer: {
          type: 'check',
          editable: false,
        },
      },
    ]
    // 그리드 1 시작
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
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
    // 그리드 1 끝

    // 그리드 세팅
    const fields2 = [
      {
        fieldName: 'oprr_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_prms_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_cl_cd',
        dataType: ValueType.TEXT,
      },
    ]

    const columns2 = [
      {
        header: this.$t('LB00000701'), // 성명
        name: 'oprr_nm',
        fieldName: 'oprr_nm',
        width: '200',
        editable: false,
      },
      {
        header: this.$t('LB00000703'), // 작업자 번호
        name: 'oprr_id',
        fieldName: 'oprr_id',
        width: '200',
        editable: false,
      },
      {
        header: this.$t('LB00000704'), // 자격구분
        name: 'oprn_cl_nm',
        fieldName: 'oprn_cl_nm',
        width: '200',
        editable: false,
        styleName: 'left',
      },
    ]
    // 그리드 2 시작
    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
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

    // 첨부파일 수정 기능 끄기
    this.$refs.upload1.setEdit(false)
    this.$refs.upload1.setDelete(false)

    if (this.schData.key_id !== '') {
      this.select()
    }
  },
  methods: {
    chkFile() {
      this.frmData.ckFlag = true
    },
    chkReqFile() {
      this.reqData.ckFlag = true
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
      const data = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-dtl/${this.schData.key_id}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]

        if (this.frmData.wekd_con_yn === 'Y') {
          this.c_wekd_con_yn = true
        } else {
          this.c_wekd_con_yn = false
        }
        if (this.frmData.wknd_con_yn === 'Y') {
          this.c_wknd_con_yn = true
        } else {
          this.c_wknd_con_yn = false
        }
        if (this.frmData.nght_con_yn === 'Y') {
          this.c_nght_con_yn = true
        } else {
          this.c_nght_con_yn = false
        }

        // this.schData.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id

        if (!this.isEmpty(this.frmData.oprn_plan_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.oprn_plan_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = []
      }

      if (data.req.length > 0) {
        this.reqData = data.req[0]

        if (this.reqData.con_req_prst_cd === 'S01') {
          this.apprModify = true
        } else {
          this.apprModify = false
        }

        if (this.reqData.wekd_con_yn === 'Y') {
          this.c_wekd_con_yn_req = true
        } else {
          this.c_wekd_con_yn_req = false
        }
        if (this.reqData.wknd_con_yn === 'Y') {
          this.c_wknd_con_yn_req = true
        } else {
          this.c_wknd_con_yn_req = false
        }
        if (this.reqData.nght_con_yn === 'Y') {
          this.c_nght_con_yn_req = true
        } else {
          this.c_nght_con_yn_req = false
        }

        if (!this.isEmpty(this.reqData.con_req_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.reqData.con_req_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.reqData = []
      }

      if (data.oprn.length > 0) {
        this.oprnData = data.oprn[0]
        if (this.oprnData.h_yn === 'Y') {
          this.c_h_yn = true
        } else {
          this.c_h_yn = false
        }
        if (this.oprnData.g_yn === 'Y') {
          this.c_g_yn = true
        } else {
          this.c_g_yn = false
        }
        if (this.oprnData.m_yn === 'Y') {
          this.c_m_yn = true
        } else {
          this.c_m_yn = false
        }
        if (this.oprnData.j_yn === 'Y') {
          this.c_j_yn = true
        } else {
          this.c_j_yn = false
        }
        if (this.oprnData.u_yn === 'Y') {
          this.c_u_yn = true
        } else {
          this.c_u_yn = false
        }
        if (this.oprnData.v_yn === 'Y') {
          this.c_v_yn = true
        } else {
          this.c_v_yn = false
        }
        if (this.oprnData.n_yn === 'Y') {
          this.c_n_yn = true
        } else {
          this.c_n_yn = false
        }
      } else {
        this.oprnData = []
      }

      if (data.oprr.length > 0) {
        this.oprrData = data.oprr
        dataProvider2.setRows(data.oprr)
        gridView2.refresh()
        gridView2.setTopItem(0)
        this.oprrTotalcnt = dataProvider2.getRowCount()
      }

      this.oprnSlct()
      // this.selectOprr()
    },
    async selectObey() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-dtl-obey`, { params: this.schData })

      this.obeyData = data.obey
      dataProvider.setRows(data.obey)
      gridView.refresh()
      gridView.setTopItem(0)
      this.obeyTotalcnt = dataProvider.getRowCount()
    },
    async selectOprr() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-dtl-oprr`, { params: this.schData })

      this.oprrData = data.oprr
      dataProvider2.setRows(data.oprr)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.oprrTotalcnt = dataProvider2.getRowCount()
      this.schData.otsr_con_bsen_id = null
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        // this.$refs.upload2.setFiles(files)
        // this.$refs.upload2.setEdit(false)
        // this.$refs.upload2.setDelete(false)
        // this.$refs.upload1.setEdit(true)
        // this.$refs.upload2.setEdit(mode)
      })
    },
    tabChg(val) {
      if (val === '1') {
        gridView.refresh()
      } else if (val === '2') {
        gridView2.refresh()
      }
    },
    oprnSlct() {
      this.selectOprn = ''
      if (this.c_h_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD01',"
      }
      if (this.c_g_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD02',"
      }
      if (this.c_m_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD03',"
      }
      if (this.c_j_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD04',"
      }
      if (this.c_u_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD05',"
      }
      if (this.c_v_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD06',"
      }
      if (this.c_n_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD07',"
      }
      this.selectOprn = this.selectOprn.substring(0, this.selectOprn.length - 1)
      this.schData.wkpl_chec_dtl_cl_cd = this.selectOprn
      this.schData.wkpl_id = this.frmData.wkpl_id
      this.schData.con_prms_id = this.reqData.con_prms_id
      this.selectObey()
    },
    goList() {
      this.$router.push({
        name: `cstprms-cstprmsmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            con_nm: this.tmpSchData.con_nm,
            con_oprn_wkpl_loc_id: this.tmpSchData.con_oprn_wkpl_loc_id,
            con_oprn_wkpl_loc_nm: this.tmpSchData.con_oprn_wkpl_loc_nm,
            con_rspn_org_cd: this.tmpSchData.con_rspn_org_cd,
            con_rspn_org_nm: this.tmpSchData.con_rspn_org_nm,
            con_rq_emp_id: this.tmpSchData.con_rq_emp_id,
            con_rq_emp_nm: this.tmpSchData.con_rq_emp_nm,
            con_req_prst_cd: this.tmpSchData.con_req_prst_cd,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
  },
}
</script>
<style></style>
