<template>
  <div>
    <div class="location">
      <!-- Home > 사업장 관리 > 사업장 관리 -->
      <span>Home > {{ $t('LB00001201') }} > {{ $t('LB00001201') }}</span>
    </div>
    <div class="work_title">
      <!-- 사업장 관리 -->
      <h2>{{ $t('LB00001201') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 사업장 개요 -->
        <h3>{{ $t('LB00001200') }}</h3>
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
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cmpy_nm }}</div>
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
                <!--주소-->
                <div class="label_tit">{{ $t('LB00000725') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.wkpl_addr }}</div>
              </v-col>
              <v-col cols="1">
                <!--표준산업 분류-->
                <div class="label_tit">{{ $t('LB00001199') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.std_ind_cat_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--연간 생산량 (톤)-->
                <div class="label_tit">{{ $t('LB00001198') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.yrly_mfg_qty }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업장 안전보건 조직도-->
                <div class="label_tit">{{ $t('LB00001197') }}</div>
              </v-col>
              <v-col ref="uploadOne" cols="3">
                <!-- 입력하세요. -->
                <file-upload ref="upload1" input-id="file" :multiple="false" @onDrop="chkFile()" />
              </v-col>
              <v-col cols="1">
                <!--주요업무-->
                <div class="label_tit">{{ $t('LB00001196') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_cl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.bspr_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--대표자 명-->
                <div class="label_tit">{{ $t('LB00000724') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rprv_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--안전보건 관리책임자-->
                <div class="label_tit">{{ $t('LB00001195') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sfhe_mgnt_rspn_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--산재 관리번호-->
                <div class="label_tit">{{ $t('LB00001194') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.inai_wkpl_mgnt_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--공장 관리번호-->
                <div class="label_tit">{{ $t('LB00001193') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.plnt_mgnt_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--총괄 책임자-->
                <div class="label_tit">{{ $t('LB00001192') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.clgt_rspn_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장 관리번호-->
                <div class="label_tit">{{ $t('LB00001191') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_mgnt_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--전화번호-->
                <div class="label_tit">{{ $t('LB00000723') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_tphn }}</div>
              </v-col>
              <v-col cols="1">
                <!--임직원 수 (명)-->
                <div class="label_tit">{{ $t('LB00001190') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_work_prsn_cnt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--도급 회사 수-->
                <div class="label_tit">{{ $t('LB00001189') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sbcn_sum_cnt }}</div>
              </v-col>
              <v-col cols="1">
                <!--도급사 총 인원 수-->
                <div class="label_tit">{{ $t('LB00001188') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sbcn_prsn_sum_cnt }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업장 구분-->
                <div class="label_tit">{{ $t('LB00001178') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_main_bsns_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--기상정보 지역-->
                <div class="label_tit">기상정보 지역</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.weat_rgn_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업장 위치정보-->
                <div class="label_tit">사업장 위치정보</div>
              </v-col>
              <v-col cols="3">
                <div v-if="frmData.wkpl_id">
                  <div class="grid_header">
                    <!--좌표 범위: 1~99-->
                    <div class="grid_header_left">좌표 범위: 0~95</div>
                  </div>
                  <div id="realgrid4" style="width: 100%; height: 150px"></div>
                </div>
              </v-col>
              <v-col cols="1">
                <!--사업장 그룹정보-->
                <div class="label_tit">사업장 그룹정보</div>
              </v-col>
              <v-col cols="3">
                <div id="realgrid5" style="width: 100%; height: 190px"></div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장 조감도-->
                <div class="label_tit">사업장 조감도</div>
              </v-col>
              <v-col cols="7">
                <picpreview ref="occurPicPop" style="height: 420px" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 선임 현황-->
        <h3>{{ $t('LB00001187') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ elcTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 400px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--상주 협력사 현황-->
        <h3>{{ $t('LB00001185') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbcnTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 400px"></div>
    </div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 평가 / 인증 현황-->
        <h3>{{ $t('LB00001186') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ apalTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 400px"></div>
    </div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 첨부-->
        <h3>{{ $t('LB00001141') }}</h3>
      </div>
      <div class="group_padd"></div>
      <!--첨부파일 목록-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
        <table>
          <div v-if="true">
            <file-upload ref="upload2" input-id="file2" :multiple="true" @onDrop="chkFile()" />
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
    <filePopup ref="filepop" mkey1="wkplmgntdtledtatflgrid1" :edit-chk="false" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import filePopup from '../../common/atflgrid.vue'
import picpreview from '../../common/picpreview'
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

// 그리드 5
let gridView5 = GridView
let dataProvider5 = LocalDataProvider

export default {
  meta: {
    title: '사업장 관리',
    key(route) {
      return `/stndinfr/stndinfr/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
    filePopup,
    picpreview,
  },
  data() {
    return {
      user: {},
      quillChk: false,
      elcTotalcnt: 0,
      sbcnTotalcnt: 0,
      apalTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      userPopGb: '',
      elcRowVal: 0,
      wkplId: '',
      codeid: ['ST00000022'],
      codes: {
        ST00000022: [{ cd: '', cd_nm: '' }],
      },
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        wkpl_nm: '',
        cmpy_cd: '',
        std_ind_cat_cd: '',
        wkpl_cl_cd: '',
        wkpl_addr: '',
        wkpl_tphn: '',
        cntr_cd: '',
        lang_cd: '',
        curr_cd: '',
        sfhe_mgnt_rspn_emp_id: '',
        sfhe_mgnt_rspn_emp_nm: '',
        clgt_rspn_emp_id: '',
        clgt_rspn_emp_nm: '',
        wkpl_work_prsn_cnt: '',
        yrly_mfg_qty: '',
        bspr_no: '',
        rprv_nm: '',
        inai_wkpl_mgnt_no: '',
        plnt_mgnt_no: '',
        wkpl_mgnt_no: '',
        wkpl_ogch_atch_file_no: '',
        wkpl_atfl_no: '',
        wkpl_atch_file_no: '',
        sbcn_prsn_sum_cnt: '',
        sbcn_sum_cnt: '',
        use_yn: '',
        del_yn: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
        lsch_nm: '',
        wkpl_main_bsns_nm: '',
        wkpl_main_bsns_cd: '',
      },
      tempData: {
        wkpl_id: '',
        pchs_cntn: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001201') // 사업장 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.wkplmgntdtllup !== null && this.$route.params.wkplmgntdtllup !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkplmgntdtllup
      this.wkplId = this.$route.params.wkplmgntdtllup
    } else {
      this.frmData.wkpl_id = ''
      this.wkplId = ''
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        wkpl_nm: '',
        use_yn: '',
        cmpy_cd: '',
      }
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      this.$refs.occurPicPop.setEdit(false)
      // 선임정보
      const fields1 = [
        {
          fieldName: 'wkpl_id', // SHE시스템사업장ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_reg_sn', // 선임등록순번
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_cl_cd', // SHE시스템선임구분코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_cl_nm', // SHE시스템선임구분명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_cl_oth_txt', // 선임구분기타내용
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_dtl_cl_cd', // SHE시스템선임상세구분코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_dtl_cl_nm', // SHE시스템선임상세구분명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_rspn_emp_id', // 선임담당직원ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ag_yn', // 대행여부
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bsns_agen_orga_nm', // 업무대행기관명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bsns_agen_cont_rnew_dt', // 업무대행계약갱신일자
          dataType: ValueType.DATETIME,
          datetimeFormat: 'yyyyMMdd',
        },
        {
          fieldName: 'elc_pric_nm', // 선임담당자명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_pric_dept_nm', // 선임담당자부서명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_pric_pstn_nm', // 선임담당자직급명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_pric_tel_no', // 선임담당자전화번호
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_pric_email', // 선임담당자이메일
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ctsv_yrs', // 근속년수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ctsv_mths', // 근속개월수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_crr_yrs', // 선임경력년수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_crr_mths', // 선임경력개월수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_elc_dt', // 사업장선임일자
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crtf_nm', // 자격증명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mand_tlgl_trn_yn', // 필수법정교육여부
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'fst_trn_fcst_dt', // 최초교육이수일자
          dataType: ValueType.DATETIME,
          datetimeFormat: 'yyyyMMdd',
        },
        {
          fieldName: 'rglr_trn_fcst_dt', // 정기교육이수일자
          dataType: ValueType.DATETIME,
          datetimeFormat: 'yyyyMMdd',
        },
        {
          fieldName: 'elc_trn_cyc_cd', // SHE시스템선임교육주기코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_trn_cyc_nm', // SHE시스템선임교육주기명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_expr_dt', // 교육만료일자
          dataType: ValueType.DATETIME,
          datetimeFormat: 'yyyyMMdd',
        },
        {
          fieldName: 'lst_trn_fcst_dt', // 최종교육이수일자
          dataType: ValueType.DATETIME,
          datetimeFormat: 'yyyyMMdd',
        },
        {
          fieldName: 'aprv_prst_cd', // SHE시스템결재진행상태코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'aprv_no', // SHE시스템결재번호
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cert_reg_yn', // 교육이수인증서등록여부
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_cert_reg_yn_nm', // 교육이수인증서등록여부명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_infr_atfl_no', // 선임정보화학물질관리시스템첨부파일번호
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'elc_atfl_cnt', // 첨부파일갯수
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_emp_no', // 최초생성ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_dt', // 최초생성시각
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_emp_no', // 최종변경ID
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_dt', // 최종변경시각
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sdtp_cd', // 표준시간대코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'reg_appl_cl_cd', // 등록어플리케이션구분코드
          dataType: ValueType.TEXT,
        },
      ]
      // 협력사
      const fields2 = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_reg_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'bspr_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'inai_wkpl_mgnt_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_main_bsns_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_main_bsns_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_prsn_cnt',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'sbcn_fld_pric_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_rprv_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mgnt_pric_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sft_pric_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hyg_pric_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sbcn_rprs_emad',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_note_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sdtp_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'reg_appl_cl_cd',
          dataType: ValueType.TEXT,
        },
      ]
      // 인증정보
      const fields3 = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atht_reg_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atht_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atht_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atht_grd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_re_atht_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atht_atch_file_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sdtp_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'reg_appl_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'apal_atfl_cnt',
          dataType: ValueType.TEXT,
        },
      ]

      // 선임현황 그리드 시작
      const columns = [
        {
          header: '선임명', // 선임명
          name: 'elc_cl_nm',
          fieldName: 'elc_cl_nm',
          width: '180',
          styleName: 'left',
        },
        {
          header: '기타 선임명', // 기타 선임명
          name: 'elc_cl_oth_txt',
          fieldName: 'elc_cl_oth_txt',
          width: '180',
          styleName: 'left',
        },
        {
          header: '구분', // 구분
          name: 'elc_dtl_cl_nm',
          fieldName: 'elc_dtl_cl_nm',
          width: '80',
        },
        {
          header: this.$t('LB00001214'), // 대행여부
          name: 'ag_yn',
          fieldName: 'ag_yn',
          width: '70',
        },
        {
          header: '대행기관', // 대행기관
          name: 'bsns_agen_orga_nm',
          fieldName: 'bsns_agen_orga_nm',
          width: '120',
          styleName: 'left',
        },
        {
          header: '계약/갱신일', // 계약/갱신일
          name: 'bsns_agen_cont_rnew_dt',
          fieldName: 'bsns_agen_cont_rnew_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'elc_pric_nm',
          fieldName: 'elc_pric_nm',
          width: '80',
        },
        {
          header: '소속부서', // 소속부서
          name: 'elc_pric_dept_nm',
          fieldName: 'elc_pric_dept_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: '직급', // 직급
          name: 'elc_pric_pstn_nm',
          fieldName: 'elc_pric_pstn_nm',
          width: '80',
        },
        {
          header: '근속년수', // 근속년수
          name: 'ctsv_yrs',
          fieldName: 'ctsv_yrs',
          width: '80',
        },
        {
          header: '근속개월수', // 근속개월수
          name: 'ctsv_mths',
          fieldName: 'ctsv_mths',
          width: '80',
        },
        {
          header: this.$t('LB00001213'), // 선임일
          name: 'wkpl_elc_dt',
          fieldName: 'wkpl_elc_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
        },
        {
          header: '선임경력년수', // 선임경력년수
          name: 'elc_crr_yrs',
          fieldName: 'elc_crr_yrs',
          width: '100',
        },
        {
          header: '선임경력개월수', // 선임경력개월수
          name: 'elc_crr_mths',
          fieldName: 'elc_crr_mths',
          width: '100',
        },
        {
          header: this.$t('LB00000723'), // 전화번호
          name: 'elc_pric_tel_no',
          fieldName: 'elc_pric_tel_no',
          width: '130',
        },
        {
          header: this.$t('LB00000910'), // 이메일주소
          name: 'elc_pric_email',
          fieldName: 'elc_pric_email',
          width: '240',
          styleName: 'left',
        },
        {
          header: '관련자격(자격증, 교육 등)', // 관련자격(자격증, 교육 등)
          name: 'crtf_nm',
          fieldName: 'crtf_nm',
          width: '200',
          styleName: 'left',
        },
        {
          header: '법정교육', // 법정교육
          name: 'mand_tlgl_trn_yn',
          fieldName: 'mand_tlgl_trn_yn',
          width: '70',
        },
        {
          header: '최초교육일', // 최초교육일
          name: 'fst_trn_fcst_dt',
          fieldName: 'fst_trn_fcst_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
        },
        {
          header: '정기/보수교육일', // 정기/보수교육일
          name: 'rglr_trn_fcst_dt',
          fieldName: 'rglr_trn_fcst_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
        },
        {
          header: '교육주기', // 교육주기
          name: 'elc_trn_cyc_nm',
          fieldName: 'elc_trn_cyc_nm',
          width: '80',
        },
        {
          header: '만료일', // 만료일
          name: 'trn_expr_dt',
          fieldName: 'trn_expr_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
        },
        {
          header: '교육이수증', // 교육이수증
          name: 'trn_fcst_cert_reg_yn_nm',
          fieldName: 'trn_fcst_cert_reg_yn_nm',
          width: '80',
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'elc_atfl_cnt',
          fieldName: 'elc_atfl_cnt',
          width: '70',
          alwaysShowButton: false,
          editable: false,
        },
      ]
      const headergroup2 = [
        {
          header: {
            text: '법적선임구분', // 법적선임구분
          },
          name: 'headerGroup1',
          direction: 'horizontal',
          height: '60',
          items: ['elc_cl_nm', 'elc_cl_oth_txt', 'elc_dtl_cl_nm'], // 그룹에 들어갈 컬럼 : 선임명, 기타선임명, 구분
        },
        'ag_yn', // 대행여부
        'bsns_agen_orga_nm', // 대행기관
        'bsns_agen_cont_rnew_dt', // 계약/갱신일
        'elc_pric_nm', // 성명
        'elc_pric_dept_nm', // 부서
        'elc_pric_pstn_nm', // 직급
        {
          header: {
            text: '근속년수', // 근속년수
          },
          name: 'headerGroup2',
          direction: 'horizontal',
          height: '60',
          items: ['ctsv_yrs', 'ctsv_mths'], // 그룹에 들어갈 컬럼 : 근속년수, 근속개월수
        },
        'wkpl_elc_dt', // 선임일
        {
          header: {
            text: '선임경력', // 선임경력
          },
          name: 'headerGroup3',
          direction: 'horizontal',
          height: '60',
          items: ['elc_crr_yrs', 'elc_crr_mths'], // 그룹에 들어갈 컬럼 : 근속년수, 근속개월수
        },
        'elc_pric_tel_no', // 전화번호
        'elc_pric_email', // 이메일주소
        'crtf_nm', // 관련자격(자격증, 교육 등)
        'mand_tlgl_trn_yn', // 법정교육
        'fst_trn_fcst_dt', // 최초교육일
        'rglr_trn_fcst_dt', // 정기/보수교육일
        'elc_trn_cyc_nm', // 교육주기
        'trn_expr_dt', // 만료일
        'trn_fcst_cert_reg_yn_nm', // 교육이수증
        'elc_atfl_cnt', // 첨부파일
      ]
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields1)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumnLayout(headergroup2)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })
      gridView.editOptions.editable = false
      gridView.header.height = 50
      gridView.footer.height = 40
      gridView.displayOptions.selectionStyle = 'singleRow'
      gridView.displayOptions.rowHeight = 40
      gridView.displayOptions.fitStyle = 'fill'

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        // const current = gridView.getCurrent()
        this.elcRowVal = itemIndex.dataRow
        if (itemIndex.column === 'elc_atfl_cnt') {
          this.filePopGb = 'elc'
          const atfileNo = gridView.getValue(itemIndex.itemIndex, 'elc_infr_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData, false)
        }
      }.bind(this)
      // 그리드 1 끝

      // 그리드 2 시작
      const columns2 = [
        {
          header: this.$t('LB00001054'), // 업체명
          name: 'sbcn_nm',
          fieldName: 'sbcn_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001212'), // 사업자등록번호
          name: 'bspr_no',
          fieldName: 'bspr_no',
          width: '80',
        },
        {
          header: this.$t('LB00001211'), // 산재관리번호
          name: 'inai_wkpl_mgnt_no',
          fieldName: 'inai_wkpl_mgnt_no',
          width: '80',
        },
        {
          header: this.$t('LB00001196'), // 주요업무
          name: 'sbcn_main_bsns_nm',
          fieldName: 'sbcn_main_bsns_nm',
          width: '60',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001210'), // 업체인원
          name: 'sbcn_prsn_cnt',
          fieldName: 'sbcn_prsn_cnt',
          width: '60',
          styleName: 'right',
          numberFormat: '#,##0',
          editor: {
            maxLength: 8,
          },
        },
        {
          header: this.$t('LB00001209'), // 현장책임자
          name: 'sbcn_fld_pric_nm',
          fieldName: 'sbcn_fld_pric_nm',
          width: '80',
        },
        {
          header: this.$t('LB00000101'), // 담당자
          name: 'sbcn_rprv_nm',
          fieldName: 'sbcn_rprv_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001208'), // 담당자 이메일주소
          name: 'sbcn_rprs_emad',
          fieldName: 'sbcn_rprs_emad',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001195'), // 안전보건 관리책임자
          name: 'mgnt_pric_nm',
          fieldName: 'mgnt_pric_nm',
          width: '80',
        },
        {
          header: this.$t('LB00001207'), // 안전관리자
          name: 'sft_pric_nm',
          fieldName: 'sft_pric_nm',
          width: '80',
        },
        {
          header: this.$t('LB00001206'), // 보건관리자
          name: 'hyg_pric_nm',
          fieldName: 'hyg_pric_nm',
          width: '80',
        },
      ]
      const headergroup = [
        'sbcn_nm', // 업체명
        'bspr_no', // 사업자등록번호
        'inai_wkpl_mgnt_no', // 산재관리번호
        'sbcn_main_bsns_nm', // 주요업무
        'sbcn_prsn_cnt', // 업체인원
        'sbcn_fld_pric_nm', // 현장책임자
        'sbcn_rprv_nm', // 담당자
        'sbcn_rprs_emad', // 담당자 이메일주소
        {
          name: 'companyGroup1',
          direction: 'horizontal',
          items: ['mgnt_pric_nm', 'sft_pric_nm', 'hyg_pric_nm'], // 그룹에 들어갈 컬럼
          header: {
            text: '법적선임', // 법적선임
          },
        },
      ]
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumnLayout(headergroup)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })
      gridView2.editOptions.editable = false
      gridView2.displayOptions.selectionStyle = 'block'

      gridView2.header.height = 50
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝

      // 그리드 3 시작
      const columns3 = [
        {
          header: this.$t('LB00001205'), // 인증구분
          name: 'apal_atht_cl_nm',
          fieldName: 'apal_atht_cl_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001204'), // 인증일자
          name: 'apal_atht_dt',
          fieldName: 'apal_atht_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00001203'), // 등급
          name: 'apal_atht_grd_nm',
          fieldName: 'apal_atht_grd_nm',
          width: '100',
        },
        {
          header: this.$t('LB00001202'), // 재인증 예정일자
          name: 'apal_re_atht_dt',
          fieldName: 'apal_re_atht_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'apal_atfl_cnt',
          fieldName: 'apal_atfl_cnt',
          width: '70',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
      ]
      dataProvider3 = new LocalDataProvider(false)
      dataProvider3.setFields(fields3)
      gridView3 = new GridView('realgrid3')
      gridView3.setDataSource(dataProvider3)
      gridView3.setColumns(columns3)
      gridView3.setFooters({ visible: false })
      gridView3.setStateBar({ visible: false })
      gridView3.setCheckBar({ visible: false })
      gridView3.editOptions.editable = false
      gridView3.displayOptions.selectionStyle = 'block'

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'
      gridView3.onCellButtonClicked = function (grid, itemIndex, column) {
        // const current = gridView.getCurrent()
        this.apalRowVal = itemIndex.dataRow
        if (itemIndex.column === 'apal_atfl_cnt') {
          this.filePopGb = 'apal'
          const atfileNo = gridView3.getValue(itemIndex.itemIndex, 'apal_atht_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData, false)
        }
      }.bind(this)
      // 그리드 3 끝
      if (this.frmData.wkpl_id) {
        // 그리드 4 시작, this.selLoc 데이터 생성 시점을 맞추기 위해 이곳에서 그리드4 생성
        const fields4 = [
          {
            fieldName: 'wkpl_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_loc_xcrd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_loc_ycrd',
            dataType: ValueType.TEXT,
          },
        ]
        const columns4 = [
          {
            header: '지역도명', // 지역도명
            name: 'wkpl_loc_nm',
            fieldName: 'wkpl_loc_nm',
            lookupDisplay: true,
            width: '100',
            styleName: 'left',
          },
          {
            header: 'x좌표', // x좌표
            name: 'wkpl_loc_xcrd',
            fieldName: 'wkpl_loc_xcrd',
            width: '70',
          },
          {
            header: 'y좌표', // y좌표
            name: 'wkpl_loc_ycrd',
            fieldName: 'wkpl_loc_ycrd',
            width: '70',
          },
        ]

        dataProvider4 = new LocalDataProvider(false)
        dataProvider4.setFields(fields4)
        gridView4 = new GridView('realgrid4')
        gridView4.setDataSource(dataProvider4)
        gridView4.setColumns(columns4)
        gridView4.setFooters({ visible: false })
        gridView4.setStateBar({ visible: false })
        gridView4.setCheckBar({ visible: false })
        gridView4.editOptions.editable = false

        gridView4.header.height = 39
        gridView4.footer.height = 40
        gridView4.displayOptions.rowHeight = 40
        gridView4.displayOptions.selectionStyle = 'block'
        gridView4.displayOptions.fitStyle = 'fill'
        // 그리드 4 끝
        gridView4.refresh()
        // 그리드 5 시작, this.selLoc 데이터 생성 시점을 맞추기 위해 이곳에서 그리드5 생성
        const fields5 = [
          {
            fieldName: 'cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chk',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cd_nm',
            dataType: ValueType.TEXT,
          },
        ]
        const columns5 = [
          {
            header: '선택', // 선택
            name: 'chk',
            fieldName: 'chk',
            width: '50',
            editable: false,
            renderer: {
              editable: false,
              type: 'check',
              trueValues: 'Y',
              falseValues: 'N',
            },
          },
          {
            header: '그룹명', // 그룹명
            name: 'cd_nm',
            fieldName: 'cd_nm',
            width: '180',
            editable: false,
          },
        ]
        dataProvider5 = new LocalDataProvider(false)
        dataProvider5.setFields(fields5)
        gridView5 = new GridView('realgrid5')
        gridView5.setDataSource(dataProvider5)
        gridView5.setColumns(columns5)
        gridView5.setFooters({ visible: false })
        gridView5.setStateBar({ visible: false })
        gridView5.setCheckBar({ visible: false })
        gridView4.editOptions.editable = false

        gridView5.header.height = 39
        gridView5.displayOptions.rowHeight = 40
        gridView5.footer.height = 40
        gridView5.displayOptions.fitStyle = 'fill'
        gridView5.displayOptions.selectionStyle = 'block'
        gridView5.editOptions.appendable = false
        dataProvider5.setRows(this.codes.ST00000022)
        gridView5.refresh()
      }
      // 그리드 5 끝
      if (this.frmData.wkpl_id !== '') {
        this.select()
      }
    })
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-mgnt-dtl/${this.frmData.wkpl_id}`)
      for (let i = 0; i < dataProvider5.getRowCount(); i++) {
        const wkplCatCd = dataProvider5.getValue(i, 'cd')
        data.cat.forEach((row) => {
          if (row.wkpl_cat_cd === wkplCatCd) {
            dataProvider5.setValue(i, 'chk', 'Y')
            dataProvider5.setRowState(i)
          }
        })
      }
      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        if (this.frmData.wkpl_img_atfl_no > 0) {
          await this.$refs.occurPicPop.load(this.frmData.wkpl_img_atfl_no)
        }
        if (!this.isEmpty(this.frmData.wkpl_ogch_atch_file_no)) {
          this.$axios.$get(`/api/v1/common/comm/file/${this.frmData.wkpl_ogch_atch_file_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.wkpl_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.wkpl_atfl_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
      } else {
        this.frmData = []
      }

      dataProvider.setRows(data.elc)
      gridView.refresh()
      gridView.setTopItem(0)
      this.elcTotalcnt = dataProvider.getRowCount()

      dataProvider2.setRows(data.sbcn)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.sbcnTotalcnt = dataProvider2.getRowCount()

      dataProvider3.setRows(data.apal)
      gridView3.refresh()
      gridView3.setTopItem(0)
      this.apalTotalcnt = dataProvider3.getRowCount()

      dataProvider4.setRows(data.rgn)
      gridView4.refresh()
      gridView4.setTopItem(0)
      this.rgn = dataProvider4.getRowCount()
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
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(false)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.$refs.upload2.setDelete(false)
        this.changeEditMode(false)
      })
    },
    chkFile() {
      this.frmData.ckFlag = true
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    goList() {
      this.$router.push({
        name: `stndinfr-wkplmgntlist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            wkpl_nm: this.tmpSchData.wkpl_nm,
            use_yn: this.tmpSchData.use_yn,
            cmpy_cd: this.tmpSchData.cmpy_cd,
          },
        },
      })
    },
  },
}
</script>
<style></style>
