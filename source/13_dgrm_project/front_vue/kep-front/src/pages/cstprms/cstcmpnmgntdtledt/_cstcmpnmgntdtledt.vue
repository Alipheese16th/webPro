<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 작업완료 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000861') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업완료 관리 -->
      <h2>{{ $t('LB00000861') }}</h2>
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
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
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
        <!-- 작업 전,중,후Check Sheet -->
        <v-tab :style="styleObject" @click="tabChg('4')">{{ $t('LB00000896') }}</v-tab>
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
                    <file-upload ref="upload1" input-id="file1" :multiple="true" @onDrop="chkFile()" />
                  </div>
                </table>
                <div class="group_padd"></div>
              </div>
            </div>
          </v-card>
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header"></div>
              <div class="sub_title">
                <!-- 작업 허가 첨부파일 정보-->
                <h3>{{ $t('LB00000898') }}</h3>
              </div>
              <div class="container_filelist" style="max-height: 100px">
                <table>
                  <div v-if="true">
                    <file-upload ref="upload2" input-id="file2" :multiple="true" @onDrop="chkReqFile()" />
                  </div>
                </table>
                <div class="group_padd"></div>
              </div>
            </div>
          </v-card>
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header"></div>
              <div class="sub_title">
                <!-- 작업 완료 첨부파일 정보-->
                <h3>{{ $t('LB00000899') }}</h3>
              </div>
              <div class="container_filelist" style="max-height: 100px">
                <table>
                  <div v-if="true">
                    <file-upload ref="upload3" input-id="file3" :multiple="true" @onDrop="cmplReqFile()" />
                  </div>
                </table>
                <div class="group_padd"></div>
              </div>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="group_padd"></div>
            <div class="container_detail">
              <div class="sub_title">
                <!-- 점검결과 목록-->
                <h3>{{ $t('LB00000646') + $t('LB00000118') }}</h3>
              </div>
            </div>
          </v-card>
          <v-card flat>
            <v-container>
              <v-row>
                <v-col cols="5" style="padding: 0">
                  <div class="realgrid_container">
                    <div id="realgrid3" style="width: 100%; height: 340px"></div>
                  </div>
                </v-col>
                <v-col cols="7" style="padding: 0">
                  <div class="container_detail">
                    <v-row>
                      <v-col cols="12">
                        <v-container>
                          <v-row>
                            <v-col cols="1">
                              <!--점검자-->
                              <div class="label_tit">{{ $t('LB00000638') }}</div>
                            </v-col>
                            <v-col cols="3">
                              <div class="label_con">{{ chklData.lsch_nm }}</div>
                            </v-col>
                            <v-col cols="1">
                              <!--점검일시-->
                              <div class="label_con">{{ $t('LB00000648') }}</div>
                            </v-col>
                            <v-col cols="3">
                              <div class="label_con">{{ chklData.con_chk_dttm }}</div>
                            </v-col>
                            <v-col cols="1">
                              <!--점검상태-->
                              <div class="label_tit">{{ $t('LB00000900') }}</div>
                            </v-col>
                            <v-col cols="3">
                              <div class="label_con">{{ chklData.con_chk_prst_nm }}</div>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col cols="1">
                              <!--점검내용-->
                              <div class="label_tit">{{ $t('LB00000901') }}</div>
                            </v-col>
                            <v-col cols="11">
                              <v-textarea
                                ref="con_txt"
                                v-model="chklData.con_chk_txt"
                                outlined
                                single-line
                                :hide-details="true"
                                height="60"
                                class="mt-2"
                                disabled
                                dense
                              ></v-textarea>
                            </v-col>
                          </v-row>
                          <v-row>
                            <v-col cols="1">
                              <!-- 점검사진-->
                              <div class="label_tit">{{ $t('LB00000902') }}</div>
                            </v-col>
                            <v-col cols="11" class="warning_container">
                              <picpreview ref="picPop" />
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-col>
                    </v-row>
                  </div>
                  <div class="realgrid_container">
                    <div class="grid_header">
                      <!--총 건-->
                      <div class="grid_header_left">{{ $t('LB00000039') }} {{ chkDtlTotalcnt }}{{ $t('LB00000040') }}</div>
                    </div>
                    <div id="realgrid4" style="width: 100%; height: 300px"></div>
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-tab-item>
      </v-tabs>
      <br />
      <div class="container_detail">
        <div class="grid_header">
          <div class="grid_header_left"></div>
          <div>
            <!--저장-->
            <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
            <!--결재상신-->
            <v-btn depressed width="120" color="primary" height="40" @click="appr()">{{ $t('LB00000763') }}</v-btn>
            <!--목록-->
            <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
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

export default {
  meta: {
    title: '작업완료 관리',
    key(route) {
      return `/cstprms/cstcmpnmgntlist/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
    picpreview,
  },
  data() {
    return {
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      forN: 3,
      oprrTotalcnt: 0,
      obeyTotalcnt: 0,
      chkTotalcnt: 0,
      chkDtlTotalcnt: 0,
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
      cmplTempData: {},
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
      // 완료정보
      cmplData: {
        ckFlag: false,
        con_prms_id: '',
        con_cmpn_atfl_no: '',
        con_cmpn_prst_cd: '',
        con_oprn_cmpn_aprv_no: '',
      },
      // 점검
      chklData: {
        con_prms_id: '',
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_cl_nm: '',
        wkpl_chec_sn: '',
        con_chk_txt: '',
        con_chk_prst_cd: '',
        con_chk_prst_nm: '',
        con_chk_dttm: '',
        con_chk_atfl_no: '',
        lsch_nm: '',
        upt_emp_no: '',
      },
      // 점검 상세
      chklDtlData: {
        con_prms_id: '',
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_cl_nm: '',
        wkpl_chec_dtl_cl_cd: '',
        wkpl_chec_dtl_cl_nm: '',
        wkpl_chec_sn: '',
        wkpl_chec_in_cl_cd: '',
        wkpl_chec_txt: '',
        mand_chec_yn: '',
        chec_vl: '',
      },
      chklList: {},
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000861') // 작업완료 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.cstcmpnmgntdtledt !== null && this.$route.params.cstcmpnmgntdtledt !== undefined) {
      this.schData.key_id = this.$route.params.cstcmpnmgntdtledt
      this.schData.auth_slct = 'Y'
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

    // 그리드 3 시작
    const fields3 = [
      {
        fieldName: 'con_prms_id',
        dataType: ValueType.TEXT,
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
        fieldName: 'wkpl_chec_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_chk_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_chk_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_chk_prst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_chk_dttm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_chk_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_emp_no',
        dataType: ValueType.TEXT,
      },
    ]
    const columns3 = [
      {
        header: this.$t('LB00000745'), // 작업구분
        name: 'wkpl_chec_cl_nm',
        fieldName: 'wkpl_chec_cl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000638'), // 점검자
        name: 'lsch_nm',
        fieldName: 'lsch_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000648'), // 점검일시
        name: 'con_chk_dttm',
        fieldName: 'con_chk_dttm',
        width: '200',
      },
      {
        header: this.$t('LB00000900'), // 점검상태
        name: 'con_chk_prst_nm',
        fieldName: 'con_chk_prst_nm',
        width: '80',
      },
    ]
    dataProvider3 = new LocalDataProvider(false)
    dataProvider3.setFields(fields3)
    gridView3 = new GridView('realgrid3')
    gridView3.displayOptions.selectionStyle = 'block'
    gridView3.setDataSource(dataProvider3)
    gridView3.setColumns(columns3)
    gridView3.setFooters({ visible: false })
    gridView3.setStateBar({ visible: false })
    gridView3.setCheckBar({ visible: false })
    gridView3.rowIndicator.visible = false
    gridView3.editOptions.editable = false
    gridView3.displayOptions.selectionStyle = 'block'

    gridView3.header.height = 39
    gridView3.displayOptions.rowHeight = 40
    gridView3.footer.height = 40
    gridView3.displayOptions.fitStyle = 'fill'
    gridView3.onCellClicked = function (grid, index, clickData) {
      const wkplChecClId = gridView3.getValue(index.itemIndex, 'wkpl_chec_cl_cd')
      this.schData.wkpl_chec_cl_cd = wkplChecClId
      this.chklData = this.chklList[index.itemIndex]

      // 이미지 로딩
      this.$refs.picPop.setEdit(false)
      if (!this.isEmpty(this.chklData.con_chk_atfl_no)) {
        this.$refs.picPop.load(this.chklData.con_chk_atfl_no)
      }

      this.selectChkDtl()
    }.bind(this)
    // 그리드 3 끝

    // 그리드 4 시작
    const fields4 = [
      {
        fieldName: 'con_prms_id',
        dataType: ValueType.TEXT,
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
        fieldName: 'wkpl_chec_cl_nm',
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
        fieldName: 'wkpl_chec_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_in_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_chec_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mand_chec_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chec_vl',
        dataType: ValueType.TEXT,
      },
    ]
    const columns4 = [
      {
        header: this.$t('LB00000880'), // 작업종류
        name: 'wkpl_chec_dtl_cl_nm',
        fieldName: 'wkpl_chec_dtl_cl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000904'), // No.
        name: 'wkpl_chec_sn',
        fieldName: 'wkpl_chec_sn',
        width: '50',
      },
      {
        header: this.$t('LB00000903'), // 체크리스트
        name: 'wkpl_chec_txt',
        fieldName: 'wkpl_chec_txt',
        width: '200',
      },
      {
        header: this.$t('LB00000765'), // 결과
        name: 'chec_vl',
        fieldName: 'chec_vl',
        width: '80',
      },
    ]
    dataProvider4 = new LocalDataProvider(false)
    dataProvider4.setFields(fields4)
    gridView4 = new GridView('realgrid4')
    gridView4.displayOptions.selectionStyle = 'block'
    gridView4.setDataSource(dataProvider4)
    gridView4.setColumns(columns4)
    gridView4.setFooters({ visible: false })
    gridView4.setStateBar({ visible: false })
    gridView4.setCheckBar({ visible: false })
    gridView4.rowIndicator.visible = false
    gridView4.editOptions.editable = false
    gridView4.displayOptions.selectionStyle = 'block'

    gridView4.header.height = 39
    gridView4.displayOptions.rowHeight = 40
    gridView4.footer.height = 40
    gridView4.displayOptions.fitStyle = 'fill'
    // 그리드 4 끝

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
    cmplReqFile() {
      this.cmplData.ckFlag = true
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

      if (data.auth[0].auth !== 'Y') {
        this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
        // this.authChk = false

        this.$router.replace({
          name: `cstprms-cstcmpnmgntdtllup-cstcmpnmgntdtllup`,
          params: {
            cstcmpnmgntdtllup: this.schData.key_id,
            tmpSchData: {
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
      }

      this.changeEditMode(true)

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
              this.setFiles1(data)
            }
          })
        }
      } else {
        this.frmData = []
      }

      if (data.req.length > 0) {
        this.reqData = data.req[0]
        this.reqTempData = JSON.parse(JSON.stringify(this.reqData)) // 수정여부 비교 데이터

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
              this.setFiles2(data)
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

      if (data.cmpl.length > 0) {
        this.cmplData = data.cmpl[0]
        this.cmplTempData = JSON.parse(JSON.stringify(this.cmplData)) // 수정여부 비교 데이터

        if (!this.isEmpty(this.cmplData.con_cmpn_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.cmplData.con_cmpn_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles3(data)
            }
          })
        }
      } else {
        this.cmplData = []
      }

      if (data.chkl.length > 0) {
        this.chklData = data.chkl[0]
        this.chklList = data.chkl
        console.log(this.chklList) // eslint-disable-line no-console
        dataProvider3.setRows(data.chkl)
        gridView3.refresh()
        gridView3.setTopItem(0)
        this.chkTotalcnt = dataProvider3.getRowCount()

        // 이미지 로딩
        this.$refs.picPop.setEdit(false)
        if (!this.isEmpty(data.chkl[0].con_chk_atfl_no)) {
          this.$refs.picPop.load(data.chkl[0].con_chk_atfl_no)
        }
      }

      this.oprnSlct()
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
    async selectChkDtl() {
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-cmpl-chk-dtl`, { params: this.schData })
      dataProvider4.setRows(rows)
      gridView4.refresh()
      gridView4.setTopItem(0)
      this.chkDtlTotalcnt = gridView4.getItemCount()
    },
    async save() {
      // 파일 결과 함수
      await this.$refs.upload3.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.cmplData.con_cmpn_atfl_no = data
        }
      })

      // if (JSON.stringify(this.cmplData) === JSON.stringify(this.cmplTempData)) {
      //   this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
      //   return
      // }

      // Validation 체크
      // if (!this.saveValidate()) {
      //   if (!this.isEmpty(this.cmplData.con_cmpn_atfl_no)) {
      //     this.$axios.$get(`/api/v1/common/file/${this.cmplData.con_cmpn_atfl_no}`).then((data) => {
      //       console.log('files1 : ', data) // eslint-disable-line no-console
      //       if (data.length > 0) {
      //         this.setFiles3(data)
      //       }
      //     })
      //   }
      //   return
      // }
      if (!this.isEmpty(this.cmplData.con_cmpn_atfl_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.cmplData.con_cmpn_atfl_no}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles3(data)
          }
        })
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 완료정보
      const cmplRow = []
      const tempCmpl = {}

      tempCmpl.con_prms_id = this.reqData.con_prms_id
      tempCmpl.con_cmpn_atfl_no = this.cmplData.con_cmpn_atfl_no
      tempCmpl.con_cmpn_prst_cd = this.cmplData.con_cmpn_prst_cd
      tempCmpl.username = this.user.emp_no
      cmplRow.push(tempCmpl)
      this.frmData.cmplRow = cmplRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false
      this.reqData.ckFlag = false
      this.cmplData.ckFlag = false

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-cmpl-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
          this.oprnSlct()
          this.selectOprr()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      // 파일 결과 함수
      await this.$refs.upload3.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.cmplData.con_cmpn_atfl_no = data
        }
      })

      // if (!this.isEmpty(this.cmplData.con_cmpn_atfl_no)) {
      //   this.$axios.$get(`/api/v1/common/file/${this.cmplData.con_cmpn_atfl_no}`).then((data) => {
      //     console.log('files1 : ', data) // eslint-disable-line no-console
      //     if (data.length > 0) {
      //       this.setFiles3(data)
      //     }
      //   })
      // } else {
      //   this.$toast.show('작업 완료 첨부파일을 첨부해주세요.', { className: 'toast_error' }) // 작업 완료 첨부파일을 첨부해주세요.
      //   return
      // }

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      // 완료정보
      const cmplRow = []
      const tempCmpl = {}

      tempCmpl.con_prms_id = this.reqData.con_prms_id
      tempCmpl.con_cmpn_atfl_no = this.cmplData.con_cmpn_atfl_no
      tempCmpl.con_cmpn_prst_cd = 'S01'
      tempCmpl.username = this.user.emp_no
      cmplRow.push(tempCmpl)
      this.frmData.cmplRow = cmplRow

      // 파일 수정 플래그 초기화
      this.cmplData.ckFlag = false

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-cmpl-dtl', this.frmData).then((res) => {
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
      temp.con_id = this.frmData.con_id
      temp.con_prms_id = this.reqData.con_prms_id
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-cmpl-dtl-appr', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empno
          const url = config.default.approvalUrl1 + aprvno + empno
          window.open(url, 'con-oprn-prms-cmpl-dtl-appr', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.replace({
            name: `cstprms-cstcmpnmgntdtllup-cstcmpnmgntdtllup`,
            params: {
              cstcmpnmgntdtllup: this.schData.key_id,
              tmpSchData: {
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
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(true)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.$refs.upload2.setDelete(false)
        this.changeEditMode(true)
      })
    },
    setFiles3(files) {
      this.$nextTick(() => {
        this.$refs.upload3.setFiles(files)
        this.$refs.upload3.setEdit(false)
        this.$refs.upload3.setDelete(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(false)
      this.$refs.upload2.setEdit(false)
      this.$refs.upload3.setEdit(mode)
    },
    tabChg(val) {
      if (val === '1') {
        gridView.refresh()
      } else if (val === '2') {
        gridView2.refresh()
      } else if (val === '4') {
        gridView3.refresh()
        gridView4.refresh()
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
      this.$router.replace({
        name: `cstprms-cstcmpnmgntlist`,
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
