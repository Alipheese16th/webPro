<template>
  <div>
    <div class="location">
      <!-- Home > 사업장 정보 > 사업장 관리 -->
      <span>Home > 사업장 정보 > {{ $t('LB00001201') }}</span>
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
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wkpl_nm"
                  v-model="frmData.wkpl_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
                <!-- <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.ST00000002p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="!wkplModify"
                  height="30"
                ></v-select> -->
              </v-col>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit essn">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="frmData.cmpy_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.CO00000009p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--주소-->
                <div class="label_tit essn">{{ $t('LB00000725') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="wkpl_addr"
                  v-model="frmData.wkpl_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="2000"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--표준산업 분류-->
                <div class="label_tit essn">{{ $t('LB00001199') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="std_ind_cat_cd"
                  v-model="frmData.std_ind_cat_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.ST00000010p"
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
                <!--연간 생산량 (톤)-->
                <div class="label_tit">{{ $t('LB00001198') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="yrly_mfg_qty"
                  v-model="frmData.yrly_mfg_qty"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="13"
                  type="number"
                  min="0"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사업장 안전보건 조직도-->
                <div class="label_tit">{{ $t('LB00001197') }}</div>
              </v-col>
              <v-col ref="uploadOne" cols="3">
                <!-- 입력하세요. -->
                <file-upload ref="upload1" input-id="file1" :multiple="false" @onDrop="chkFile()" />
              </v-col>
              <v-col cols="1">
                <!--주요업무-->
                <div class="label_tit essn">{{ $t('LB00001196') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_cl_cd"
                  v-model="frmData.wkpl_cl_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.ST00000001p"
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
                <!--사업자 등록번호-->
                <div class="label_tit essn">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="bspr_no"
                  v-model="frmData.bspr_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="13"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--대표자 명-->
                <div class="label_tit essn">{{ $t('LB00000724') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="rprv_nm"
                  v-model="frmData.rprv_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--안전보건 관리책임자-->
                <div class="label_tit">{{ $t('LB00001195') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="sfhe_mgnt_rspn_emp_id"
                  v-model="frmData.sfhe_mgnt_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popOpen('sft')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--산재 관리번호-->
                <div class="label_tit">{{ $t('LB00001194') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="inai_wkpl_mgnt_no"
                  v-model="frmData.inai_wkpl_mgnt_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="11"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--공장 관리번호-->
                <div class="label_tit">{{ $t('LB00001193') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="plnt_mgnt_no"
                  v-model="frmData.plnt_mgnt_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="20"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--총괄 책임자-->
                <div class="label_tit">{{ $t('LB00001192') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="clgt_rspn_emp_id"
                  v-model="frmData.clgt_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popOpen('she')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장 관리번호-->
                <div class="label_tit">{{ $t('LB00001191') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wkpl_mgnt_no"
                  v-model="frmData.wkpl_mgnt_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="11"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--전화번호-->
                <div class="label_tit">{{ $t('LB00000723') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wkpl_tphn"
                  v-model="frmData.wkpl_tphn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="20"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--임직원 수 (명)-->
                <div class="label_tit essn">{{ $t('LB00001190') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wkpl_work_prsn_cnt"
                  v-model="frmData.wkpl_work_prsn_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="8"
                  type="number"
                  min="0"
                ></v-text-field>
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
                <div class="label_tit essn">{{ $t('LB00001178') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_main_bsns_cd"
                  v-model="frmData.wkpl_main_bsns_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.ST00000015p"
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
                <!--기상정보 지역-->
                <div class="label_tit essn">{{ $t('기상정보 지역') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="weat_rgn_cd"
                  v-model="frmData.weat_rgn_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000023"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장 위치정보-->
                <div class="label_tit">{{ $t('사업장 위치정보') }}</div>
              </v-col>
              <v-col cols="3">
                <div v-if="frmData.wkpl_id">
                  <div class="grid_header">
                    <!--좌표 범위: 1~99-->
                    <div class="grid_header_left">{{ $t('좌표 범위: 0~95') }}</div>
                    <div style="display: block">
                      <!--추가-->
                      <v-btn style="width: 50%; min-width: inherit; float: left" color="" outlined height="30" @click="addLoc">{{ $t('LB00000047') }}</v-btn>
                      <!--삭제-->
                      <v-btn style="width: 50%; min-width: inherit; float: left" color="" outlined height="30" @click="delLoc">{{ $t('LB00000046') }}</v-btn>
                    </div>
                  </div>
                  <div id="realgrid4" style="width: 100%; height: 150px"></div>
                </div>
              </v-col>
              <v-col cols="1">
                <!--사업장 그룹정보-->
                <div class="label_tit essn">{{ $t('사업장 그룹정보') }}</div>
              </v-col>
              <v-col cols="3">
                <div id="realgrid5" style="width: 100%; height: 190px"></div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장 조감도-->
                <div class="label_tit essn">{{ $t('사업장 조감도') }}</div>
              </v-col>
              <v-col cols="7" @contextmenu="rightClick($event)">
                <picpreview id="occurPicPop" ref="occurPicPop" style="height: 420px"></picpreview>
                <v-menu v-model="showMenu" :position-x="x" :position-y="y" absolute offset-y>
                  <v-list>
                    <v-list-item v-for="(item, index) in dropdownItems" :key="index">
                      <v-list-item-title @click="selectRightClick(item)">{{ item.wkpl_loc_nm }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
                <v-spacer></v-spacer>
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
        <h3 style="display: flex">
          <span style="width: 10%">{{ $t('LB00001187') }}</span>
          <span style="width: 90%; text-align: right">* 법적선임항목이 없는 경우 본사 시스템 담당자에 요청바랍니다.</span>
        </h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ elcTotalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="addElc">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="delElc">{{ $t('LB00000046') }}</v-btn>
        </div>
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
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="addSbcn">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="delSbcn">{{ $t('LB00000046') }}</v-btn>
        </div>
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
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="addApal">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="delApal">{{ $t('LB00000046') }}</v-btn>
        </div>
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
        <div class="container_button">
          <!--임시 저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="tpSave">{{ $t('LB00001338') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="wkplmgntdtledtuserslct1" mkey2="wkplmgntdtledtuserslct2" @close="popclose" />
    <filePopup ref="filepop" mkey1="wkplmgntdtledtatflgrid1" @close="filepopclose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import filePopup from '../../common/atflgrid.vue'
import picpreview from '../../common/picpreview'
import FileUpload from '~/components/FileUpload.vue'

let dateVal = new Date()

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
    popup,
    FileUpload,
    filePopup,
    picpreview,
  },
  data() {
    return {
      user: {},
      quillChk: false,
      wkplModify: false,
      codeid: [
        'CO00000009',
        'ST00000001',
        'ST00000003',
        'ST00000004',
        'ST00000005',
        'ST00000006',
        'ST00000002',
        'ST00000010',
        'ST00000015',
        'SH00000088',
        'SH00000089',
        'ST00000023',
        'ST00000022',
      ],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        CO00000009p: [],
        ST00000001: [{ cd: '', cd_nm: '' }],
        ST00000001p: [],
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000002p: [],
        ST00000003: [{ cd: '', cd_nm: '' }],
        ST00000004: [{ cd: '', cd_nm: '' }],
        ST00000005: [{ cd: '', cd_nm: '' }],
        ST00000006: [{ cd: '', cd_nm: '' }],
        ST00000010: [{ cd: '', cd_nm: '' }],
        ST00000010p: [],
        ST00000015: [{ cd: '', cd_nm: '' }],
        ST00000015p: [],
        SH00000089: [{ cd: '', cd_nm: '' }],
        ST00000023: [{ cd: '', cd_nm: '' }],
        ST00000022: [{ cd: '', cd_nm: '' }],
      },
      selST00000003: {
        cd: [],
        cd_nm: [],
      },
      selST00000004: {
        cd: [],
        cd_nm: [],
      },
      selSH00000088: {
        cd: [],
        cd_nm: [],
      },
      selST00000005: {
        cd: [],
        cd_nm: [],
      },
      selST00000006: {
        cd: [],
        cd_nm: [],
      },
      selSH00000089: {
        cd: [],
        cd_nm: [],
      },
      selLoc: {
        cd: [],
        cd_nm: [],
      },
      use_yn_cd: [
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      elcTotalcnt: 0,
      sbcnTotalcnt: 0,
      apalTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      userPopGb: '',
      elcRowVal: 0,
      apalRowVal: 0,
      filePopGb: '',
      wkplId: '',
      newYn: 'Y',
      frmTempData: {},
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        cd_grp_no: '',
        wkpl_nm: '',
        org_wkpl_id: '',
        kor_wkpl_nm: '',
        engl_wkpl_nm: '',
        chns_wkpl_nm: '',
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
        wkpl_ogch_atfl_no: '',
        wkpl_atfl_no: '',
        sbcn_prsn_sum_cnt: '',
        sbcn_sum_cnt: '',
        use_yn: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
        lsch_nm: '',
        wkpl_main_bsns_cd: '',
      },
      tempData: {
        wkpl_id: '',
        pchs_cntn: '',
      },
      // 우클릭 메뉴 설정 START
      showMenu: false,
      x: 0,
      y: 0,
      dropdownItems: [],
      posX: -1,
      posY: -1,
      locPinList: [],
      // 우클릭 메뉴 설정 END
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001201') // 사업장 관리
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.wkplmgntdtledt !== null && this.$route.params.wkplmgntdtledt !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkplmgntdtledt
      this.wkplId = this.$route.params.wkplmgntdtledt
      this.wkplModify = false
      this.newYn = 'N'
    } else {
      this.frmData.wkpl_id = ''
      this.frmData.usg_yn = 'Y'
      this.wkplId = ''
      this.wkplModify = true
      this.newYn = 'Y'
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
    dateVal = this.getTzDateObj(this.user.time_zone)
  },
  mounted() {
    // 선임정보 데이터프로바이더 셋팅
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
        fieldName: 'elc_cl_oth_txt', // 선임구분기타내용
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_dtl_cl_cd', // SHE시스템선임상세구분코드
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
        dataType: ValueType.DATETIME,
        datetimeFormat: 'yyyyMMdd',
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
      {
        fieldName: 'retire_yn', // 퇴사여부
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
        fieldName: 'apal_atht_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'org_apal_atht_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_atht_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_atht_grd_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_re_atht_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_atht_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_atfl_cnt',
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

    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      const tempCmpny = []
      res.CO00000009.forEach((e) => {
        tempCmpny.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.CO00000009p = tempCmpny
      this.codes.CO00000009p.unshift({ value: '', label: 'SELECT' })

      const tempWkplGb = []
      res.ST00000001.forEach((e) => {
        tempWkplGb.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000001p = tempWkplGb
      this.codes.ST00000001p.unshift({ value: '', label: 'SELECT' })

      const tempWkpl = []
      res.ST00000002.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000002p = tempWkpl
      this.codes.ST00000002p.unshift({ value: '', label: 'SELECT' })

      const tempInd = []
      res.ST00000010.forEach((e) => {
        tempInd.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000010p = tempInd
      this.codes.ST00000010p.unshift({ value: '', label: 'SELECT' })

      const tempWkplDtlGb = []
      res.ST00000015.forEach((e) => {
        tempWkplDtlGb.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000015p = tempWkplDtlGb
      this.codes.ST00000015p.unshift({ value: '', label: 'SELECT' })

      this.codes.ST00000023 = res.ST00000023
      this.codes.ST00000023.unshift({ cd: '', cd_nm: 'SELECT' })

      this.selST00000003.cd.push('')
      this.selST00000003.cd_nm.push('SELECT')
      res.ST00000003.forEach((i) => {
        this.selST00000003.cd.push(i.cd)
        this.selST00000003.cd_nm.push(i.cd_nm)
      })

      this.selST00000004.cd.push('')
      this.selST00000004.cd_nm.push('SELECT')
      res.ST00000004.forEach((i) => {
        this.selST00000004.cd.push(i.cd)
        this.selST00000004.cd_nm.push(i.cd_nm)
      })

      this.selSH00000088.cd.push('')
      this.selSH00000088.cd_nm.push('SELECT')
      res.SH00000088.forEach((i) => {
        this.selSH00000088.cd.push(i.cd)
        this.selSH00000088.cd_nm.push(i.cd_nm)
      })
      this.selST00000005.cd.push('')
      this.selST00000005.cd_nm.push('SELECT')
      res.ST00000005.forEach((i) => {
        this.selST00000005.cd.push(i.cd)
        this.selST00000005.cd_nm.push(i.cd_nm)
      })

      this.selST00000006.cd.push('')
      this.selST00000006.cd_nm.push('SELECT')
      res.ST00000006.forEach((i) => {
        this.selST00000006.cd.push(i.cd)
        this.selST00000006.cd_nm.push(i.cd_nm)
      })

      // this.selSH00000089.cd.push('')
      // this.selSH00000089.cd_nm.push('SELECT')
      res.SH00000089.forEach((i) => {
        this.selSH00000089.cd.push(i.cd)
        this.selSH00000089.cd_nm.push(i.cd_nm)
      })
      this.$refs.occurPicPop.setMaxFileCnt(1) // 사고 발생/현장 사진 MAX 갯수 설정

      // 선임현황 그리드 시작
      const columns = [
        {
          header: '선임명', // 선임명
          name: 'elc_cl_cd',
          fieldName: 'elc_cl_cd',
          lookupDisplay: true,
          width: '180',
          values: this.selST00000004.cd,
          labels: this.selST00000004.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        // {
        //   header: '기타 선임명', // 기타 선임명
        //   name: 'elc_cl_oth_txt',
        //   fieldName: 'elc_cl_oth_txt',
        //   width: '180',
        //   styleName: 'left',
        //   editor: {
        //     maxLength: 1000,
        //   },
        //   styleCallback(grid, dataCell) {
        //     const ret = {}
        //     const elcClCd = grid.getValue(dataCell.index.itemIndex, 'elc_cl_cd')
        //     if (elcClCd === 'ELC28') {
        //       // 선임구분을 기타로 선택 시
        //       ret.editable = true
        //     } else {
        //       ret.editable = false
        //     }
        //     return ret
        //   },
        // },
        {
          header: '구분', // 구분
          name: 'elc_dtl_cl_cd',
          fieldName: 'elc_dtl_cl_cd',
          lookupDisplay: true,
          width: '80',
          values: this.selSH00000088.cd,
          labels: this.selSH00000088.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        {
          header: this.$t('LB00001214'), // 대행여부
          name: 'ag_yn',
          fieldName: 'ag_yn',
          width: '70',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
        },
        {
          header: '대행기관', // 대행기관
          name: 'bsns_agen_orga_nm',
          fieldName: 'bsns_agen_orga_nm',
          width: '120',
          editor: {
            maxLength: 50,
          },
          styleName: 'left',
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'left field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: '계약/갱신일', // 계약/갱신일
          name: 'bsns_agen_cont_rnew_dt',
          fieldName: 'bsns_agen_cont_rnew_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'elc_pric_nm',
          fieldName: 'elc_pric_nm',
          width: '80',
          editable: false,
          editor: {
            maxLength: 10,
          },
          button: 'action',
          buttonVisibility: 'default',
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
          buttonVisibleCallback: (grid, index, focused, mouseEntered) => {
            const agYn = grid.getValue(index.itemIndex, 'ag_yn')
            if (agYn === 'N' && (focused || mouseEntered)) {
              return true
            } else {
              return false
            }
          },
        },
        {
          header: '소속부서', // 소속부서
          name: 'elc_pric_dept_nm',
          fieldName: 'elc_pric_dept_nm',
          width: '100',
          styleName: 'left',
          editable: false,
          editor: {
            maxLength: 20,
          },
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.styleName = 'left field-edit-disabled'
            }
            return ret
          },
        },
        {
          header: '직급', // 직급
          name: 'elc_pric_pstn_nm',
          fieldName: 'elc_pric_pstn_nm',
          width: '80',
          editable: false,
          editor: {
            maxLength: 20,
          },
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.styleName = 'field-edit-disabled'
            }
            return ret
          },
        },
        {
          header: '근속년수', // 근속년수
          name: 'ctsv_yrs',
          fieldName: 'ctsv_yrs',
          width: '80',
          editor: {
            type: 'number',
            editFormat: '##0',
            maxLength: 2,
          },
          displayCallback: (grid, index, value) => {
            let str = ''
            if (!this.isEmpty(value)) {
              str += value + '년'
            }
            return str
          },
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            } else {
              ret.editable = true
            }

            return ret
          },
        },
        {
          header: '근속개월수', // 근속개월수
          name: 'ctsv_mths',
          fieldName: 'ctsv_mths',
          width: '80',
          editor: {
            type: 'number',
            editFormat: '##0',
            maxLength: 2,
          },
          displayCallback: (grid, index, value) => {
            let str = ''
            if (!this.isEmpty(value)) {
              str += value + '개월'
            }
            return str
          },
          styleCallback: (grid, dataCell) => {
            const agYn = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (agYn === 'Y') {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            } else {
              ret.editable = true
            }

            return ret
          },
        },
        {
          header: this.$t('LB00001213'), // 선임일
          name: 'wkpl_elc_dt',
          fieldName: 'wkpl_elc_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: '선임경력년수', // 선임경력년수
          name: 'elc_crr_yrs',
          fieldName: 'elc_crr_yrs',
          width: '100',
          editable: false,
          editor: {
            type: 'number',
            editFormat: '##0',
            maxLength: 2,
          },
          displayCallback: (grid, index, value) => {
            let str = ''
            if (!this.isEmptyNaN(value)) {
              if (value > 0) {
                str += value + '년'
              }
            }
            return str
          },
          styleCallback: (grid, dataCell) => {
            const wkplElcDt = grid.getValue(dataCell.index.itemIndex, 'wkpl_elc_dt')
            const ret = {}
            if (this.isEmptyNaN(wkplElcDt)) {
              ret.styleName = 'field-edit-disabled'
            }
            return ret
          },
        },
        {
          header: '선임경력개월수', // 선임경력개월수
          name: 'elc_crr_mths',
          fieldName: 'elc_crr_mths',
          width: '100',
          editable: false,
          editor: {
            type: 'number',
            editFormat: '##0',
            maxLength: 2,
          },
          displayCallback: (grid, index, value) => {
            let str = ''
            if (!this.isEmptyNaN(value)) {
              if (value > 0) {
                str += value + '개월'
              }
            }
            return str
          },
          styleCallback: (grid, dataCell) => {
            const wkplElcDt = grid.getValue(dataCell.index.itemIndex, 'wkpl_elc_dt')
            const ret = {}
            if (this.isEmptyNaN(wkplElcDt)) {
              ret.styleName = 'field-edit-disabled'
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000723'), // 전화번호
          name: 'elc_pric_tel_no',
          fieldName: 'elc_pric_tel_no',
          width: '130',
          editor: {
            maxLength: 50,
          },
          styleCallback: (grid, dataCell) => {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}

            if (gubun === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
            }

            return ret
          },
        },
        {
          header: this.$t('LB00000910'), // 이메일주소
          name: 'elc_pric_email',
          fieldName: 'elc_pric_email',
          width: '240',
          styleName: 'left',
          editor: {
            maxLength: 50,
          },
          styleCallback: (grid, dataCell) => {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}

            if (gubun === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
            }

            return ret
          },
        },
        {
          header: '관련자격(자격증, 교육 등)', // 관련자격(자격증, 교육 등)
          name: 'crtf_nm',
          fieldName: 'crtf_nm',
          width: '200',
          styleName: 'left',
          editor: {
            maxLength: 25,
          },
        },
        {
          header: '법정교육', // 법정교육
          name: 'mand_tlgl_trn_yn',
          fieldName: 'mand_tlgl_trn_yn',
          width: '70',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
          styleCallback: (grid, dataCell) => {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'ag_yn')
            const ret = {}
            if (gubun === 'Y') {
              ret.editable = false
            } else {
              ret.editable = true
            }

            return ret
          },
        },
        {
          header: '최초교육일', // 최초교육일
          name: 'fst_trn_fcst_dt',
          fieldName: 'fst_trn_fcst_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
          styleCallback: (grid, dataCell) => {
            const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
            const ret = {}
            if (mandTlglTrnYn === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: '정기/보수교육일', // 정기/보수교육일
          name: 'rglr_trn_fcst_dt',
          fieldName: 'rglr_trn_fcst_dt',
          width: '100',
          datetimeFormat: 'yyyy-MM-dd',
          styleCallback: (grid, dataCell) => {
            const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
            const ret = {}
            const fstTrnFcstDt = grid.getValue(dataCell.index.itemIndex, 'fst_trn_fcst_dt')
            if (mandTlglTrnYn === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            }

            if (fstTrnFcstDt !== undefined && fstTrnFcstDt !== null && fstTrnFcstDt !== '') {
              ret.editor = {
                type: 'date',
                minDate: new Date(fstTrnFcstDt),
                mask: {
                  editMask: '9999-99-99',
                  placeHolder: 'yyyy-MM-dd',
                  includedFormat: true,
                },
                datetimeFormat: 'yyyy-MM-dd',
              }
            } else {
              ret.editor = {
                type: 'date',
                defaultShowDate: 'todayWhenNull',
                mask: {
                  editMask: '9999-99-99',
                  placeHolder: 'yyyy-MM-dd',
                  includedFormat: true,
                },
                datetimeFormat: 'yyyy-MM-dd',
              }
            }

            return ret
          },
        },
        {
          header: '교육주기', // 교육주기
          name: 'elc_trn_cyc_cd',
          fieldName: 'elc_trn_cyc_cd',
          width: '70',
          lookupDisplay: true,
          values: this.selSH00000089.cd,
          labels: this.selSH00000089.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback: (grid, dataCell) => {
            const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
            const ret = {}
            if (mandTlglTrnYn === 'Y') {
              ret.editable = true
            } else {
              ret.editable = false
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: '만료일', // 만료일
          name: 'trn_expr_dt',
          fieldName: 'trn_expr_dt',
          width: '100',
          editable: false,
          datetimeFormat: 'yyyy-MM-dd',
          styleCallback: (grid, dataCell) => {
            const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
            const ret = {}
            if (mandTlglTrnYn !== 'Y') {
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: '교육이수증', // 교육이수증
          name: 'trn_fcst_cert_reg_yn_nm',
          fieldName: 'trn_fcst_cert_reg_yn_nm',
          width: '80',
          editable: false,
          styleCallback: (grid, dataCell) => {
            const mandTlglTrnYn = grid.getValue(dataCell.index.itemIndex, 'mand_tlgl_trn_yn')
            const ret = {}
            if (mandTlglTrnYn !== 'Y') {
              ret.styleName = 'field-edit-disabled'
            }

            return ret
          },
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'elc_atfl_cnt',
          fieldName: 'elc_atfl_cnt',
          width: '70',
          button: 'action',
          alwaysShowButton: true,
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
          items: ['elc_cl_cd', 'elc_dtl_cl_cd'], // 그룹에 들어갈 컬럼 : 선임명, 구분
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
          header: '선임경력', // 선임경력
          name: 'headerGroup3',
          direction: 'horizontal',
          height: '60',
          items: ['elc_crr_yrs', 'elc_crr_mths'], // 그룹에 들어갈 컬럼 : 선임경력년수, 선임경력개월수
        },
        'elc_pric_tel_no', // 전화번호
        'elc_pric_email', // 이메일주소
        'crtf_nm', // 관련자격(자격증, 교육 등)
        'mand_tlgl_trn_yn', // 법정교육
        'fst_trn_fcst_dt', // 최초교육일
        'rglr_trn_fcst_dt', // 정기/보수교육일
        'elc_trn_cyc_cd', // 교육주기
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
      gridView.setCheckBar({ visible: true, width: 30 })
      gridView.editOptions.editable = true

      gridView.header.height = 50
      gridView.footer.height = 40
      gridView.displayOptions.rowHeight = 40
      gridView.displayOptions.selectionStyle = 'singleRow'
      gridView.displayOptions.fitStyle = 'fill'

      // 퇴사여부가 Y인것 ROW색 변경
      gridView.setRowStyleCallback(function (grid, item, fixed) {
        const retireYn = dataProvider.getValue(item.dataRow, 'retire_yn')
        if (retireYn === 'Y') {
          return 'row-highlight'
        }
      })

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        // console.log('Edit done! at ' + itemIndex + ', ' + row + ', ' + field) // eslint-disable-line no-console
        // console.log('=> getFieldName: ' + dataProvider.getFieldName(field)) // eslint-disable-line no-console
        if (dataProvider.getFieldName(field) === 'ELC_CL_CD') {
          // 선임명 입력 시
          const elcClCd = grid.getValue(itemIndex, 'elc_cl_cd')
          if (elcClCd !== 'ELC28') {
            // 기타가 아닐 경우 초기화
            grid.setValue(itemIndex, 'elc_cl_oth_txt', '')
            grid.commit()
          }
        } else if (dataProvider.getFieldName(field) === 'AG_YN') {
          // 대행여부 입력 시 초기화
          grid.commit()
          const agYn = grid.getValue(itemIndex, 'ag_yn')
          if (agYn === 'Y') {
            grid.setValue(itemIndex, 'mand_tlgl_trn_yn', 'N') // 법정교육
            grid.setValue(itemIndex, 'fst_trn_fcst_dt', '') // 최초교육일
            grid.setValue(itemIndex, 'rglr_trn_fcst_dt', '') // 정기/보수교육일
            grid.setValue(itemIndex, 'elc_trn_cyc_cd', null) // 교육주기
            grid.setValue(itemIndex, 'trn_expr_dt', '') // 만료일
            grid.setValue(itemIndex, 'trn_fcst_cert_reg_yn_nm', '') // 교육이수증
          }
          grid.setValue(itemIndex, 'bsns_agen_orga_nm', '') // 대행기관
          grid.setValue(itemIndex, 'bsns_agen_cont_rnew_dt', '') // 계약/갱신일
          dataProvider.setValue(row, 'elc_rspn_emp_id', '') // 사번
          grid.setValue(itemIndex, 'elc_pric_nm', '') // 성명
          grid.setValue(itemIndex, 'elc_pric_dept_nm', '') // 소속부서
          grid.setValue(itemIndex, 'elc_pric_pstn_nm', '') // 직급
          grid.setValue(itemIndex, 'ctsv_yrs', '') // 근속년수
          grid.setValue(itemIndex, 'ctsv_mths', '') // 근속개월수
          grid.setValue(itemIndex, 'elc_pric_tel_no', '') // 전화번호
          grid.setValue(itemIndex, 'elc_pric_email', '') // 이메일주소
          grid.commit()
        } else if (dataProvider.getFieldName(field) === 'MAND_TLGL_TRN_YN') {
          // 법정교육 입력 시
          const mandTlglTrnYn = grid.getValue(itemIndex, 'mand_tlgl_trn_yn')
          if (mandTlglTrnYn === 'N') {
            // 법정교육이 N일 경우 초기화
            grid.setValue(itemIndex, 'fst_trn_fcst_dt', '') // 최초교육일
            grid.setValue(itemIndex, 'rglr_trn_fcst_dt', '') // 정기/보수교육일
            grid.setValue(itemIndex, 'elc_trn_cyc_cd', null) // 교육주기
            grid.setValue(itemIndex, 'trn_expr_dt', '') // 만료일
            grid.setValue(itemIndex, 'trn_fcst_cert_reg_yn_nm', '') // 교육이수증
            grid.commit()
          }
        } else if (
          dataProvider.getFieldName(field) === 'FST_TRN_FCST_DT' ||
          dataProvider.getFieldName(field) === 'RGLR_TRN_FCST_DT' ||
          dataProvider.getFieldName(field) === 'ELC_TRN_CYC_CD'
        ) {
          if (dataProvider.getFieldName(field) === 'FST_TRN_FCST_DT') {
            const fstEduDt = grid.getValue(itemIndex, 'fst_trn_fcst_dt')
            const rglrEduDt = grid.getValue(itemIndex, 'rglr_trn_fcst_dt')
            if (rglrEduDt < fstEduDt) {
              grid.setValue(row, 'rglr_trn_fcst_dt', '')
            }
          }
          // 최초교육일, 정기/보수교육일, 교육주기 입력 시
          const fstEduDt = grid.getValue(itemIndex, 'fst_trn_fcst_dt')
          const rglrEduDt = grid.getValue(itemIndex, 'rglr_trn_fcst_dt')
          const eduTerm = grid.getValue(itemIndex, 'elc_trn_cyc_cd')
          if (this.isEmpty(fstEduDt) && this.isEmpty(rglrEduDt)) return // 최초교육일, 정기/보수교육일 둘다 값이 없으면 return
          if (this.isEmpty(eduTerm)) return // 교육주기 값이 없으면 return

          let calcDt = fstEduDt
          if (!this.isEmpty(rglrEduDt)) {
            calcDt = rglrEduDt
          }
          // console.log('=> calcDt: ' + calcDt) // eslint-disable-line no-console
          // console.log('=> eduTerm: ' + eduTerm) // eslint-disable-line no-console
          // 만료일 계산 시작
          const calcRstDt = new Date(calcDt)
          if (eduTerm === '3') {
            // 분기
            const chkMonth = calcRstDt.getMonth() + 1
            // console.log('=> chkMonth: ' + chkMonth) // eslint-disable-line no-console
            if (chkMonth >= 1 && chkMonth < 4) {
              // 1분기이면 3월 31일
              calcRstDt.setMonth(2)
              calcRstDt.setDate(31)
            } else if (chkMonth >= 4 && chkMonth < 7) {
              // 2분기이면 6월 30일
              calcRstDt.setMonth(5)
              calcRstDt.setDate(30)
            } else if (chkMonth >= 7 && chkMonth < 10) {
              // 3분기이면 9월 30일
              calcRstDt.setMonth(8)
              calcRstDt.setDate(30)
            } else if (chkMonth >= 10) {
              // 4분기이면 12월 31일
              calcRstDt.setMonth(11)
              calcRstDt.setDate(31)
            }
          } else if (eduTerm === '6') {
            // 반기
            const chkMonth = calcRstDt.getMonth() + 1
            // console.log('=> chkMonth: ' + chkMonth) // eslint-disable-line no-console
            if (chkMonth >= 1 && chkMonth < 7) {
              // 상반기이면 6월 30일
              calcRstDt.setMonth(5)
              calcRstDt.setDate(30)
            } else if (chkMonth >= 7) {
              // 하반기이면 12월 31일
              calcRstDt.setMonth(11)
              calcRstDt.setDate(31)
            }
          } else {
            // 나머지
            calcRstDt.setDate(calcRstDt.getDate() - 1)
            calcRstDt.setMonth(calcRstDt.getMonth() + parseInt(eduTerm))
          }
          // console.log('=> calcRstDt: ' + calcRstDt) // eslint-disable-line no-console

          grid.setValue(itemIndex, 'trn_expr_dt', this.get_date_str_gubun(calcRstDt, ''))
          grid.commit()

          // console.log('=> grid.getValue: ' + grid.getValue(itemIndex, 'trn_expr_dt')) // eslint-disable-line no-console
          // console.log('=> dataProvider.getValue: ' + dataProvider.getValue(row, 'trn_expr_dt')) // eslint-disable-line no-console
        } else if (dataProvider.getFieldName(field) === 'WKPL_ELC_DT') {
          const wkplElcDt = grid.getValue(itemIndex, 'wkpl_elc_dt')

          const calcRstDt = new Date(wkplElcDt)
          const calcRstDtYear = calcRstDt.getFullYear()
          const calcRstDtMonth = calcRstDt.getMonth() + 1
          const calcRstDtDay = calcRstDt.getDate()
          const nowDt = new Date()
          const nowDtYear = nowDt.getFullYear()
          const nowDtMonth = nowDt.getMonth() + 1
          const nowtDtDay = nowDt.getDate()
          let monthCheck = 0
          if (nowtDtDay < calcRstDtDay) {
            monthCheck = 1
          }
          if (calcRstDtYear <= nowDtYear) {
            grid.setValue(itemIndex, 'elc_crr_yrs', nowDtYear - calcRstDtYear)
            if (calcRstDtMonth - monthCheck < nowDtMonth) {
              grid.setValue(itemIndex, 'elc_crr_mths', nowDtMonth - calcRstDtMonth - monthCheck)
            } else {
              grid.setValue(itemIndex, 'elc_crr_mths', 0)
            }
          } else {
            grid.setValue(itemIndex, 'elc_crr_yrs', 0)
            grid.setValue(itemIndex, 'elc_crr_mths', 0)
          }
        }
      }.bind(this)

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        // const current = gridView.getCurrent()
        // this.elcRowVal = itemIndex.dataRow
        // if (itemIndex.column === 'elc_pric_nm') {
        //   const userNm = gridView.getValue(itemIndex.itemIndex, 'elc_pric_nm')
        //   this.schData.usr_nm = userNm
        //   console.log(itemIndex) // eslint-disable-line no-console
        //   this.userPopGb = 'elc'
        //   gridView.commit()
        //   this.$refs.pop.open(this.schData, false)
        // }
        if (itemIndex.column === 'elc_atfl_cnt') {
          this.elcRowVal = itemIndex.dataRow
          this.filePopGb = 'elc'
          const atfileNo = grid.getValue(itemIndex.itemIndex, 'elc_infr_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData)
        }
      }.bind(this)

      gridView.onCellClicked = function (grid, clickData) {
        // console.log('=> grid.onCellClicked clickData: ' + JSON.stringify(clickData)) // eslint-disable-line no-console
        if (clickData.cellType !== 'data') return
        if (clickData.itemIndex < 0) return

        const agyn = gridView.getValue(clickData.itemIndex, 'ag_yn')
        if (agyn === 'N') {
          // 대행여부가 N인경우
          if (clickData.column === 'elc_pric_nm') {
            const userNm = gridView.getValue(clickData.itemIndex, 'elc_pric_nm')
            this.schData.usr_nm = userNm
            this.userPopGb = 'elc'
            this.$refs.pop.open(this.schData, false)
          }
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
          editor: {
            maxLength: 50,
          },
        },
        {
          header: this.$t('LB00001212'), // 사업자등록번호
          name: 'bspr_no',
          fieldName: 'bspr_no',
          width: '80',
          editor: {
            maxLength: 13,
          },
        },
        {
          header: this.$t('LB00001211'), // 산재관리번호
          name: 'inai_wkpl_mgnt_no',
          fieldName: 'inai_wkpl_mgnt_no',
          width: '80',
          editor: {
            maxLength: 11,
          },
        },
        {
          header: this.$t('LB00001196'), // 주요업무
          name: 'sbcn_main_bsns_cd',
          fieldName: 'sbcn_main_bsns_cd',
          lookupDisplay: true,
          width: '60',
          values: this.selST00000005.cd,
          labels: this.selST00000005.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            // const cdGrpNo = grid.getValue(dataCell.index.itemIndex, 'cd_grp_no')
            // // eslint-disable-next-line eqeqeq
            // if (cdGrpNo != '') {
            //   ret.editable = false
            // }
            return ret
          },
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
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00000101'), // 담당자
          name: 'sbcn_rprv_nm',
          fieldName: 'sbcn_rprv_nm',
          width: '100',
          styleName: 'left',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00001208'), // 담당자 이메일주소
          name: 'sbcn_rprs_emad',
          fieldName: 'sbcn_rprs_emad',
          width: '100',
          styleName: 'left',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00001195'), // 안전보건 관리책임자
          name: 'mgnt_pric_nm',
          fieldName: 'mgnt_pric_nm',
          width: '80',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00001207'), // 안전관리자
          name: 'sft_pric_nm',
          fieldName: 'sft_pric_nm',
          width: '80',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00001206'), // 보건관리자
          name: 'hyg_pric_nm',
          fieldName: 'hyg_pric_nm',
          width: '80',
          editor: {
            maxLength: 100,
          },
        },
        {
          header: this.$t('LB00000727'), // 최종 수정자
          name: 'upt_emp_no',
          fieldName: 'upt_emp_no',
          width: '80',
          editable: false,
        },
        {
          header: this.$t('LB00000939'), // 최종 수정일
          name: 'upt_dt',
          fieldName: 'upt_dt',
          width: '80',
          editable: false,
        },
      ]
      const headergroup = [
        'sbcn_nm', // 업체명
        'bspr_no', // 사업자등록번호
        'inai_wkpl_mgnt_no', // 산재관리번호
        'sbcn_main_bsns_cd', // 주요업무
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
        {
          name: 'companyGroup2',
          direction: 'horizontal',
          items: ['upt_emp_no', 'upt_dt'], // 그룹에 들어갈 컬럼
          header: {
            text: '작성정보', // 작성정보
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
      gridView2.setCheckBar({ visible: true, width: 50 })
      gridView2.editOptions.editable = true

      gridView2.header.height = 50
      gridView2.footer.height = 40
      gridView2.displayOptions.rowHeight = 40
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝

      // 그리드 3 시작
      const columns3 = [
        {
          header: this.$t('LB00001205'), // 인증구분
          name: 'apal_atht_cl_cd',
          fieldName: 'apal_atht_cl_cd',
          lookupDisplay: true,
          width: '100',
          values: this.selST00000003.cd,
          labels: this.selST00000003.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            // const cdGrpNo = grid.getValue(dataCell.index.itemIndex, 'cd_grp_no')
            // // eslint-disable-next-line eqeqeq
            // if (cdGrpNo != '') {
            //   ret.editable = false
            // }
            return ret
          },
        },
        {
          header: this.$t('LB00001204'), // 인증일자
          name: 'apal_atht_dt',
          fieldName: 'apal_atht_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00001203'), // 등급
          name: 'apal_atht_grd_cd',
          fieldName: 'apal_atht_grd_cd',
          width: '100',
          values: this.selST00000006.cd,
          labels: this.selST00000006.cd_nm,
          styleCallback(grid, dataCell) {
            gridView3.commit()
            const apalAthtClCd = grid.getValue(dataCell.index.itemIndex, 'apal_atht_cl_cd')
            // eslint-disable-next-line eqeqeq
            if (apalAthtClCd != 'A01') {
              grid.setValue(dataCell.index.dataRow, 'apal_atht_grd_cd', '')
              return {
                editable: false,
                editor: {
                  type: 'line',
                },
              }
            } else {
              return {
                lookupDisplay: true,
                editable: true,
                editor: {
                  type: 'dropdown',
                  domainOnly: true,
                  textReadOnly: true,
                  displayLabels: 'label',
                },
              }
              // ret.editable = true
              // ret.editor = "type: 'dropdown'"
            }
          },
        },
        {
          header: this.$t('LB00001202'), // 재인증 예정일자
          name: 'apal_re_atht_dt',
          fieldName: 'apal_re_atht_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
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
      gridView3.setCheckBar({ visible: true, width: 50 })
      gridView3.editOptions.editable = true

      gridView3.header.height = 39
      gridView3.footer.height = 40
      gridView3.displayOptions.rowHeight = 40
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.displayOptions.fitStyle = 'fill'

      gridView3.onCellButtonClicked = function (grid, itemIndex, column) {
        // const current = gridView.getCurrent()
        this.apalRowVal = itemIndex.dataRow
        if (itemIndex.column === 'apal_atfl_cnt') {
          this.filePopGb = 'apal'
          const atfileNo = gridView3.getValue(itemIndex.itemIndex, 'apal_atht_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData)
        }
      }.bind(this)
      // 그리드 3 끝

      if (this.frmData.wkpl_id) {
        this.select().then((data) => {
          this.selLoc.cd.push('')
          this.selLoc.cd_nm.push('SELECT')
          data.loc.forEach((i) => {
            if (i.level === 1) {
              this.selLoc.cd.push(i.wkpl_loc_id)
              this.selLoc.cd_nm.push(i.wkpl_loc_nm)
              this.dropdownItems.push(i)
            }
          })
          // 그리드 4 시작, this.selLoc 데이터 생성 시점을 맞추기 위해 이곳에서 그리드4 생성
          const fields4 = [
            {
              fieldName: 'wkpl_id',
              dataType: ValueType.TEXT,
            },
            {
              fieldName: 'wkpl_loc_id',
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
              header: this.$t('지역도명'), // 지역도명
              name: 'wkpl_loc_id',
              fieldName: 'wkpl_loc_id',
              lookupDisplay: true,
              width: '100',
              values: this.selLoc.cd,
              labels: this.selLoc.cd_nm,
              styleName: 'left',
              editor: {
                type: 'dropdown',
                domainOnly: true,
                textReadOnly: true,
                displayLabels: 'label',
              },
            },
            {
              header: this.$t('x좌표'), // x좌표
              name: 'wkpl_loc_xcrd',
              fieldName: 'wkpl_loc_xcrd',
              width: '70',
              editor: {
                type: 'number',
                maxLength: 2,
                positiveOnly: true,
                intergerOnly: true,
              },
            },
            {
              header: this.$t('y좌표'), // y좌표
              name: 'wkpl_loc_ycrd',
              fieldName: 'wkpl_loc_ycrd',
              width: '70',
              editor: {
                type: 'number',
                maxLength: 2,
                positiveOnly: true,
                intergerOnly: true,
              },
            },
          ]
          dataProvider4 = new LocalDataProvider(false)
          dataProvider4.setFields(fields4)
          gridView4 = new GridView('realgrid4')
          gridView4.setDataSource(dataProvider4)
          gridView4.setColumns(columns4)
          gridView4.setFooters({ visible: false })
          gridView4.setStateBar({ visible: false })
          gridView4.setCheckBar({ visible: true })
          gridView4.editOptions.editable = true

          gridView4.header.height = 39
          gridView4.footer.height = 40
          gridView4.displayOptions.rowHeight = 40
          gridView4.displayOptions.selectionStyle = 'block'
          gridView4.displayOptions.fitStyle = 'fill'
          // 그리드 4 끝

          dataProvider4.setRows(data.rgn)
          gridView4.refresh()
        })
      }
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
          header: this.$t('LB00000106'), // 선택
          name: 'chk',
          fieldName: 'chk',
          width: '50',
          editable: false,
          renderer: {
            type: 'check',
            trueValues: 'Y',
            falseValues: 'N',
          },
        },
        {
          header: this.$t('그룹명'), // 그룹명
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

      gridView5.header.height = 39
      gridView5.displayOptions.rowHeight = 40
      gridView5.footer.height = 40
      gridView5.displayOptions.fitStyle = 'fill'
      gridView5.displayOptions.selectionStyle = 'block'
      gridView5.editOptions.appendable = false
      dataProvider5.setRows(this.codes.ST00000022)
      gridView5.refresh()
      // 그리드 5 끝
    })
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    gridView2.commit()
    gridView3.commit()
    if (!this.wkplModify) {
      if (
        dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() ||
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
    rightClick(e) {
      e.preventDefault()
      this.posX = -1
      this.posY = -1
      if (e.button === 2) {
        this.showMenu = false
        this.x = e.clientX
        this.y = e.clientY

        let eleImg = document.getElementById('occurPicPop').getElementsByTagName('img')[0]
        if (eleImg.length === 0) {
          return false
        } else {
          eleImg = document.getElementById('occurPicPop').getElementsByTagName('img')[0]
        }
        // const rect = event.target.getBoundingClientRect()
        const rect = eleImg.getBoundingClientRect()
        const rectWidth = rect.width
        const rectHeight = rect.height
        const x = e.clientX - rect.left // x position within the element.
        const y = e.clientY - rect.top // y position within the element.
        if (x < 0 || y < 0 || x > rectWidth || y > rectHeight) {
          return false
        }

        this.posX = ((x / rectWidth) * 100).toFixed(3)
        this.posY = ((y / rectHeight) * 100).toFixed(3)
        this.$nextTick(() => {
          this.showMenu = true
        })
      }
    },
    selectRightClick(item) {
      gridView4.commit()
      this.locPinList.push({ wkpl_id: item.wkpl_id, wkpl_loc_id: item.wkpl_loc_id, wkpl_loc_xcrd: this.posX, wkpl_loc_ycrd: this.posY })
      dataProvider4.addRow({
        wkpl_id: item.wkpl_id,
        wkpl_loc_id: item.wkpl_loc_id,
        wkpl_loc_xcrd: this.posX,
        wkpl_loc_ycrd: this.posY,
      })
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
    async select() {
      const data = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-mgnt-dtl/${this.frmData.wkpl_id}`)
      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        if (this.frmData.wkpl_img_atfl_no > 0) {
          await this.$refs.occurPicPop.load(this.frmData.wkpl_img_atfl_no)
        }
        for (let i = 0; i < dataProvider5.getRowCount(); i++) {
          const wkplCatCd = dataProvider5.getValue(i, 'cd')
          data.cat.forEach((row) => {
            if (row.wkpl_cat_cd === wkplCatCd) {
              dataProvider5.setValue(i, 'chk', 'Y')
              dataProvider5.setRowState(i)
            }
          })
        }
        if (!this.isEmpty(this.frmData.wkpl_ogch_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.wkpl_ogch_atfl_no}`).then((data) => {
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
        this.frmTempData = []
      }
      // console.log(data) // eslint-disable-line no-console
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

      // dataProvider4.setRows(data.rgn)
      // gridView4.refresh()
      // gridView4.setTopItem(0)

      let retireCnt = 0
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, 'retire_yn') === 'Y') {
          // 퇴사자가 있을 경우
          retireCnt++
        }
      }
      if (retireCnt > 0) {
        await this.$confirm('사업장 선임 인원 중 퇴사 인원이 발생 하였습니다.<br />법규 준수를 위해 신규 담당자 지정 바랍니다.') // 사업장 선임 인원 중 퇴사 인원이 발생 하였습니다.<br />법규 준수를 위해 신규 담당자 지정 바랍니다.
      }
      return data
    },
    // 선임자 추가
    addElc() {
      gridView.commit()

      dataProvider.addRow({
        wkpl_id: '',
        elc_reg_sn: '',
        elc_cl_cd: '',
        elc_cl_oth_txt: '',
        elc_dtl_cl_cd: '',
        ag_yn: 'N',
        bsns_agen_orga_nm: '',
        bsns_agen_cont_rnew_dt: '',
        elc_rspn_emp_id: '',
        elc_pric_nm: '',
        elc_pric_dept_nm: '',
        elc_pric_pstn_nm: '',
        ctsv_yrs: '',
        ctsv_mths: '',
        wkpl_elc_dt: '',
        elc_crr_yrs: '',
        elc_crr_mths: '',
        elc_pric_tel_no: '',
        elc_pric_email: '',
        crtf_nm: '',
        mand_tlgl_trn_yn: 'N',
        fst_trn_fcst_dt: '',
        rglr_trn_fcst_dt: '',
        elc_trn_cyc_cd: null,
        trn_expr_dt: '',
        trn_fcst_cert_reg_yn: '',
        trn_fcst_cert_reg_yn_nm: '',
        elc_infr_atfl_no: '',
        elc_atfl_cnt: 0,
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      })
    },
    delElc() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    addSbcn() {
      gridView2.commit()
      dataProvider2.addRow({
        wkpl_id: '',
        sbcn_reg_sn: '',
        sbcn_nm: '',
        bspr_no: '',
        inai_wkpl_mgnt_no: '',
        sbcn_main_bsns_cd: '',
        sbcn_prsn_cnt: '',
        sbcn_fld_pric_nm: '',
        mgnt_pric_nm: '',
        sft_pric_nm: '',
        hyg_pric_nm: '',
        sbcn_rprv_nm: '',
        sbcn_rprs_emad: '',
        wkpl_note_txt: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      })
    },
    delSbcn() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    addApal() {
      gridView3.commit()
      const reAthtDt = new Date()
      reAthtDt.setDate(dateVal.getDate() + 1)
      dataProvider3.addRow({
        wkpl_id: '',
        apal_atht_reg_sn: '',
        apal_atht_cl_cd: '',
        apal_atht_dt: this.get_date_str_gubun(dateVal, '-'),
        apal_atht_grd_cd: '',
        apal_re_atht_dt: this.get_date_str_gubun(reAthtDt, '-'),
        apal_atht_atfl_no: '',
        apal_atfl_cnt: 0,
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      })
    },
    delApal() {
      gridView3.commit()
      dataProvider3.setOptions({ softDeleting: true })
      const checkedRows = gridView3.getCheckedRows()
      dataProvider3.hideRows(checkedRows)
      dataProvider3.removeRows(checkedRows)
    },
    addLoc() {
      gridView4.commit()
      dataProvider4.addRow({
        wkpl_id: '',
        wkpl_loc_id: '',
        wkpl_loc_xcrd: '',
        wkpl_loc_ycrd: '',
      })
    },
    delLoc() {
      gridView4.commit()
      dataProvider4.setOptions({ softDeleting: true })
      const checkedRows = gridView4.getCheckedRows()
      dataProvider4.hideRows(checkedRows)
      dataProvider4.removeRows(checkedRows)
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
        this.$refs.upload1.setDelete(true)
        this.changeEditMode(true)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.$refs.upload2.setDelete(true)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    async tpSave() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()
      gridView5.commit()

      if (!this.tpSaveValidate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000666')) // 임시저장 하시겠습니까?
      if (!res) {
        return
      }

      // 선임 정보
      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertElcRow = []
        const updateElcRow = []
        const deleteElcRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          let temp = {}
          const options = { nullText: '', datetimeFormat: 'yyyyMMdd' }
          temp = dataProvider.getOutputRow(options, i)
          temp.wkpl_id = this.frmData.wkpl_id
          temp.username = this.user.emp_now

          if (dataProvider.getRowState(i) === 'created') {
            insertElcRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateElcRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteElcRow.push(temp)
          }
        }
        this.frmData.insertElcRow = insertElcRow
        this.frmData.updateElcRow = updateElcRow
        this.frmData.deleteElcRow = deleteElcRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0 || dataProvider2.getRowStateCount('deleted') > 0 || dataProvider2.getRowStateCount('created') > 0) {
        const insertSbcnRow = []
        const updateSbcnRow = []
        const deleteSbcnRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.wkpl_id = this.frmData.wkpl_id
          temp.sbcn_reg_sn = dataProvider2.getValue(i, 'sbcn_reg_sn')
          temp.sbcn_nm = dataProvider2.getValue(i, 'sbcn_nm')
          temp.bspr_no = dataProvider2.getValue(i, 'bspr_no')
          temp.inai_wkpl_mgnt_no = dataProvider2.getValue(i, 'inai_wkpl_mgnt_no')
          temp.sbcn_main_bsns_cd = dataProvider2.getValue(i, 'sbcn_main_bsns_cd')
          temp.sbcn_prsn_cnt = dataProvider2.getValue(i, 'sbcn_prsn_cnt')
          temp.sbcn_fld_pric_nm = dataProvider2.getValue(i, 'sbcn_fld_pric_nm')
          temp.mgnt_pric_nm = dataProvider2.getValue(i, 'mgnt_pric_nm')
          temp.sft_pric_nm = dataProvider2.getValue(i, 'sft_pric_nm')
          temp.hyg_pric_nm = dataProvider2.getValue(i, 'hyg_pric_nm')
          temp.sbcn_rprv_nm = dataProvider2.getValue(i, 'sbcn_rprv_nm')
          temp.sbcn_rprs_emad = dataProvider2.getValue(i, 'sbcn_rprs_emad')
          temp.wkpl_note_txt = dataProvider2.getValue(i, 'wkpl_note_txt')
          temp.username = this.user.emp_no

          if (dataProvider2.getRowState(i) === 'created') {
            insertSbcnRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateSbcnRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteSbcnRow.push(temp)
          }
        }
        this.frmData.insertSbcnRow = insertSbcnRow
        this.frmData.updateSbcnRow = updateSbcnRow
        this.frmData.deleteSbcnRow = deleteSbcnRow
      }

      if (dataProvider3.getRowStateCount('updated') > 0 || dataProvider3.getRowStateCount('deleted') > 0 || dataProvider3.getRowStateCount('created') > 0) {
        const insertApalRow = []
        const updateApalRow = []
        const deleteApalRow = []

        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          const temp = {}

          let athtDtVal = dataProvider3.getValue(i, 'apal_atht_dt')
          athtDtVal = athtDtVal.replace(/-/gi, '')
          let reAthtDtVal = dataProvider3.getValue(i, 'apal_re_atht_dt')
          reAthtDtVal = reAthtDtVal.replace(/-/gi, '')

          temp.wkpl_id = this.frmData.wkpl_id
          temp.apal_atht_reg_sn = dataProvider3.getValue(i, 'apal_atht_reg_sn')
          temp.apal_atht_cl_cd = dataProvider3.getValue(i, 'apal_atht_cl_cd')
          temp.apal_atht_dt = athtDtVal
          temp.apal_atht_grd_cd = dataProvider3.getValue(i, 'apal_atht_grd_cd')
          temp.apal_re_atht_dt = reAthtDtVal
          temp.apal_atht_atfl_no = dataProvider3.getValue(i, 'apal_atht_atfl_no')
          temp.username = this.user.emp_no

          if (dataProvider3.getRowState(i) === 'created') {
            insertApalRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'updated') {
            updateApalRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'deleted') {
            deleteApalRow.push(temp)
          }
        }
        this.frmData.insertApalRow = insertApalRow
        this.frmData.updateApalRow = updateApalRow
        this.frmData.deleteApalRow = deleteApalRow
      }

      if (this.frmData.wkpl_id) {
        gridView4.commit()
        if (dataProvider4.getRowStateCount('updated') > 0 || dataProvider4.getRowStateCount('deleted') > 0 || dataProvider4.getRowStateCount('created') > 0) {
          const insertRgnRow = []
          const updateRgnRow = []
          const deleteRgnRow = []

          for (let i = 0; i < dataProvider4.getRowCount(); i++) {
            const temp = {}

            temp.wkpl_id = this.frmData.wkpl_id
            temp.wkpl_loc_id = dataProvider4.getValue(i, 'wkpl_loc_id')
            temp.wkpl_loc_xcrd = dataProvider4.getValue(i, 'wkpl_loc_xcrd')
            temp.wkpl_loc_ycrd = dataProvider4.getValue(i, 'wkpl_loc_ycrd')
            temp.username = this.user.emp_no

            if (dataProvider4.getRowState(i) === 'created') {
              insertRgnRow.push(temp)
            } else if (dataProvider4.getRowState(i) === 'updated') {
              updateRgnRow.push(temp)
            } else if (dataProvider4.getRowState(i) === 'deleted') {
              deleteRgnRow.push(temp)
            }
          }
          this.frmData.insertRgnRow = insertRgnRow
          this.frmData.updateRgnRow = updateRgnRow
          this.frmData.deleteRgnRow = deleteRgnRow
        }
        if (dataProvider5.getRowStateCount('updated') > 0) {
          gridView5.commit()
          const insertCatRow = []
          const deleteCatRow = []

          for (let i = 0; i < dataProvider5.getRowCount(); i++) {
            const temp = {}
            temp.wkpl_cat_cd = dataProvider5.getValue(i, 'cd')
            if (dataProvider5.getValue(i, 'chk') === 'Y') {
              insertCatRow.push(temp)
            } else if (dataProvider5.getValue(i, 'chk') === 'N' || dataProvider5.getValue(i, 'chk') === undefined || dataProvider5.getValue(i, 'chk') === '') {
              deleteCatRow.push(temp)
            }
          }
          this.frmData.insertCatRow = insertCatRow
          this.frmData.deleteCatRow = deleteCatRow
        }
      }

      const infoRow = []
      const temp = {}
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          temp.wkpl_img_atfl_no = data // 첨부파일 번호
        } else {
          console.warn('사업장 조감도 업로드실패') // eslint-disable-line no-console
        }
      })
      temp.wkpl_id = this.frmData.wkpl_id
      temp.cd_grp_no = this.frmData.cd_grp_no
      temp.wkpl_nm = this.frmData.wkpl_nm
      temp.org_wkpl_id = this.frmData.org_wkpl_id
      temp.cmpy_cd = this.frmData.cmpy_cd
      temp.std_ind_cat_cd = this.frmData.std_ind_cat_cd
      temp.wkpl_cl_cd = this.frmData.wkpl_cl_cd
      temp.wkpl_addr = this.frmData.wkpl_addr
      temp.wkpl_tphn = this.frmData.wkpl_tphn
      temp.cntr_cd = this.frmData.cntr_cd
      temp.lang_cd = this.frmData.lang_cd
      temp.curr_cd = this.frmData.curr_cd
      temp.sfhe_mgnt_rspn_emp_id = this.frmData.sfhe_mgnt_rspn_emp_id
      temp.clgt_rspn_emp_id = this.frmData.clgt_rspn_emp_id
      temp.wkpl_work_prsn_cnt = this.frmData.wkpl_work_prsn_cnt
      temp.yrly_mfg_qty = this.frmData.yrly_mfg_qty
      temp.bspr_no = this.frmData.bspr_no
      temp.rprv_nm = this.frmData.rprv_nm
      temp.inai_wkpl_mgnt_no = this.frmData.inai_wkpl_mgnt_no
      temp.plnt_mgnt_no = this.frmData.plnt_mgnt_no
      temp.wkpl_mgnt_no = this.frmData.wkpl_mgnt_no
      temp.username = this.user.emp_no
      temp.wkpl_ogch_atfl_no = this.frmData.wkpl_ogch_atfl_no
      temp.wkpl_atfl_no = this.frmData.wkpl_atfl_no
      temp.wkpl_main_bsns_cd = this.frmData.wkpl_main_bsns_cd
      temp.weat_rgn_cd = this.frmData.weat_rgn_cd
      temp.usg_yn = this.frmData.usg_yn
      temp.new_yn = this.newYn
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false
      // console.log('=> this.frmData.insertElcRow: ' + JSON.stringify(this.frmData.insertElcRow)) // eslint-disable-line no-console
      // console.log('=> this.frmData.updateElcRow: ' + JSON.stringify(this.frmData.updateElcRow)) // eslint-disable-line no-console
      // console.log('=> this.frmData.deleteElcRow: ' + JSON.stringify(this.frmData.deleteElcRow)) // eslint-disable-line no-console

      await this.$axios.put('/api/v1/st/stndinfr/wkpl-mgnt-dtl', this.frmData).then((res) => {
        // console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wkpl_id = res.data.key
          this.newYn = 'N'
          this.select()
        } else if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()
      gridView5.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.wkpl_ogch_atfl_no = data
        }
      })

      await this.$refs.upload2.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.wkpl_atfl_no = data
        }
      })

      if (this.frmData.wkpl_id) {
        gridView4.commit()
        if (
          dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
          dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
          dataProvider3.getRowStateCount('none') === dataProvider3.getRowCount() &&
          dataProvider4.getRowStateCount('none') === dataProvider4.getRowCount() &&
          dataProvider5.getRowStateCount('none') === dataProvider5.getRowCount() &&
          JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
          this.frmData.wkpl_img_atfl_no === this.$refs.occurPicPop.updateKey
        ) {
          this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
          return
        }
      } else if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        dataProvider3.getRowStateCount('none') === dataProvider3.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        this.frmData.wkpl_img_atfl_no === this.$refs.occurPicPop.updateKey
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.wkpl_ogch_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.wkpl_ogch_atfl_no}`).then((data) => {
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
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertElcRow = []
        const updateElcRow = []
        const deleteElcRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          let temp = {}
          const options = { nullText: '', datetimeFormat: 'yyyyMMdd' }
          temp = dataProvider.getOutputRow(options, i)
          temp.wkpl_id = this.frmData.wkpl_id
          temp.username = this.user.emp_now

          if (dataProvider.getRowState(i) === 'created') {
            insertElcRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateElcRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteElcRow.push(temp)
          }
        }
        this.frmData.insertElcRow = insertElcRow
        this.frmData.updateElcRow = updateElcRow
        this.frmData.deleteElcRow = deleteElcRow
      }
      if (dataProvider2.getRowStateCount('updated') > 0 || dataProvider2.getRowStateCount('deleted') > 0 || dataProvider2.getRowStateCount('created') > 0) {
        const insertSbcnRow = []
        const updateSbcnRow = []
        const deleteSbcnRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.wkpl_id = this.frmData.wkpl_id
          temp.sbcn_reg_sn = dataProvider2.getValue(i, 'sbcn_reg_sn')
          temp.sbcn_nm = dataProvider2.getValue(i, 'sbcn_nm')
          temp.bspr_no = dataProvider2.getValue(i, 'bspr_no')
          temp.inai_wkpl_mgnt_no = dataProvider2.getValue(i, 'inai_wkpl_mgnt_no')
          temp.sbcn_main_bsns_cd = dataProvider2.getValue(i, 'sbcn_main_bsns_cd')
          temp.sbcn_prsn_cnt = dataProvider2.getValue(i, 'sbcn_prsn_cnt')
          temp.sbcn_fld_pric_nm = dataProvider2.getValue(i, 'sbcn_fld_pric_nm')
          temp.mgnt_pric_nm = dataProvider2.getValue(i, 'mgnt_pric_nm')
          temp.sft_pric_nm = dataProvider2.getValue(i, 'sft_pric_nm')
          temp.hyg_pric_nm = dataProvider2.getValue(i, 'hyg_pric_nm')
          temp.sbcn_rprv_nm = dataProvider2.getValue(i, 'sbcn_rprv_nm')
          temp.sbcn_rprs_emad = dataProvider2.getValue(i, 'sbcn_rprs_emad')
          temp.wkpl_note_txt = dataProvider2.getValue(i, 'wkpl_note_txt')
          temp.username = this.user.emp_no

          if (dataProvider2.getRowState(i) === 'created') {
            insertSbcnRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateSbcnRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteSbcnRow.push(temp)
          }
        }
        this.frmData.insertSbcnRow = insertSbcnRow
        this.frmData.updateSbcnRow = updateSbcnRow
        this.frmData.deleteSbcnRow = deleteSbcnRow
      }

      if (dataProvider3.getRowStateCount('updated') > 0 || dataProvider3.getRowStateCount('deleted') > 0 || dataProvider3.getRowStateCount('created') > 0) {
        const insertApalRow = []
        const updateApalRow = []
        const deleteApalRow = []

        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          const temp = {}

          let athtDtVal = dataProvider3.getValue(i, 'apal_atht_dt')
          athtDtVal = athtDtVal.replace(/-/gi, '')
          let reAthtDtVal = dataProvider3.getValue(i, 'apal_re_atht_dt')
          reAthtDtVal = reAthtDtVal.replace(/-/gi, '')

          temp.wkpl_id = this.frmData.wkpl_id
          temp.apal_atht_reg_sn = dataProvider3.getValue(i, 'apal_atht_reg_sn')
          temp.apal_atht_cl_cd = dataProvider3.getValue(i, 'apal_atht_cl_cd')
          temp.apal_atht_dt = athtDtVal
          temp.apal_atht_grd_cd = dataProvider3.getValue(i, 'apal_atht_grd_cd')
          temp.apal_re_atht_dt = reAthtDtVal
          temp.apal_atht_atfl_no = dataProvider3.getValue(i, 'apal_atht_atfl_no')
          temp.username = this.user.emp_no

          if (dataProvider3.getRowState(i) === 'created') {
            insertApalRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'updated') {
            updateApalRow.push(temp)
          } else if (dataProvider3.getRowState(i) === 'deleted') {
            deleteApalRow.push(temp)
          }
        }
        this.frmData.insertApalRow = insertApalRow
        this.frmData.updateApalRow = updateApalRow
        this.frmData.deleteApalRow = deleteApalRow
      }

      if (this.frmData.wkpl_id) {
        gridView4.commit()
        if (dataProvider4.getRowStateCount('updated') > 0 || dataProvider4.getRowStateCount('deleted') > 0 || dataProvider4.getRowStateCount('created') > 0) {
          const insertRgnRow = []
          const updateRgnRow = []
          const deleteRgnRow = []

          for (let i = 0; i < dataProvider4.getRowCount(); i++) {
            const temp = {}

            temp.wkpl_id = this.frmData.wkpl_id
            temp.wkpl_loc_id = dataProvider4.getValue(i, 'wkpl_loc_id')
            temp.wkpl_loc_xcrd = dataProvider4.getValue(i, 'wkpl_loc_xcrd')
            temp.wkpl_loc_ycrd = dataProvider4.getValue(i, 'wkpl_loc_ycrd')
            temp.username = this.user.emp_no

            if (dataProvider4.getRowState(i) === 'created') {
              insertRgnRow.push(temp)
            } else if (dataProvider4.getRowState(i) === 'updated') {
              updateRgnRow.push(temp)
            } else if (dataProvider4.getRowState(i) === 'deleted') {
              deleteRgnRow.push(temp)
            }
          }
          this.frmData.insertRgnRow = insertRgnRow
          this.frmData.updateRgnRow = updateRgnRow
          this.frmData.deleteRgnRow = deleteRgnRow
        }
        if (dataProvider5.getRowStateCount('updated') > 0) {
          gridView5.commit()
          const insertCatRow = []
          const deleteCatRow = []
          for (let i = 0; i < dataProvider5.getRowCount(); i++) {
            const temp = {}
            temp.wkpl_cat_cd = dataProvider5.getValue(i, 'cd')
            if (dataProvider5.getValue(i, 'chk') === 'Y') {
              insertCatRow.push(temp)
            } else if (dataProvider5.getValue(i, 'chk') === 'N' || dataProvider5.getValue(i, 'chk') === undefined || dataProvider5.getValue(i, 'chk') === '') {
              deleteCatRow.push(temp)
            }
          }
          this.frmData.insertCatRow = insertCatRow
          this.frmData.deleteCatRow = deleteCatRow
        }
      }
      const infoRow = []
      const temp = {}

      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          temp.wkpl_img_atfl_no = data // 첨부파일 번호
        } else {
          console.warn('사업장 조감도 업로드실패') // eslint-disable-line no-console
        }
      })
      temp.wkpl_id = this.frmData.wkpl_id
      temp.cd_grp_no = this.frmData.cd_grp_no
      temp.wkpl_nm = this.frmData.wkpl_nm
      temp.org_wkpl_id = this.frmData.org_wkpl_id
      temp.cmpy_cd = this.frmData.cmpy_cd
      temp.std_ind_cat_cd = this.frmData.std_ind_cat_cd
      temp.wkpl_cl_cd = this.frmData.wkpl_cl_cd
      temp.wkpl_addr = this.frmData.wkpl_addr
      temp.wkpl_tphn = this.frmData.wkpl_tphn
      temp.cntr_cd = this.frmData.cntr_cd
      temp.lang_cd = this.frmData.lang_cd
      temp.curr_cd = this.frmData.curr_cd
      temp.sfhe_mgnt_rspn_emp_id = this.frmData.sfhe_mgnt_rspn_emp_id
      temp.clgt_rspn_emp_id = this.frmData.clgt_rspn_emp_id
      temp.wkpl_work_prsn_cnt = this.frmData.wkpl_work_prsn_cnt
      temp.yrly_mfg_qty = this.frmData.yrly_mfg_qty
      temp.bspr_no = this.frmData.bspr_no
      temp.rprv_nm = this.frmData.rprv_nm
      temp.inai_wkpl_mgnt_no = this.frmData.inai_wkpl_mgnt_no
      temp.plnt_mgnt_no = this.frmData.plnt_mgnt_no
      temp.wkpl_mgnt_no = this.frmData.wkpl_mgnt_no
      temp.username = this.user.emp_no
      temp.wkpl_ogch_atfl_no = this.frmData.wkpl_ogch_atfl_no
      temp.wkpl_atfl_no = this.frmData.wkpl_atfl_no
      temp.wkpl_main_bsns_cd = this.frmData.wkpl_main_bsns_cd
      temp.weat_rgn_cd = this.frmData.weat_rgn_cd
      temp.usg_yn = this.frmData.usg_yn
      temp.new_yn = this.newYn
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/st/stndinfr/wkpl-mgnt-dtl', this.frmData).then((res) => {
        // console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wkpl_id = res.data.key
          this.newYn = 'N'
          this.select()
        } else if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popOpen(val) {
      this.userPopGb = val
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      const row = gridView.getCurrent().dataRow
      gridView.commit()
      gridView3.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          if (this.userPopGb === 'sft') {
            this.$set(this.frmData, 'sfhe_mgnt_rspn_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'sfhe_mgnt_rspn_emp_id', i.emp_no)
          } else if (this.userPopGb === 'elc') {
            dataProvider.setValue(row, 'wkpl_id', this.frmData.wkpl_id)
            dataProvider.setValue(row, 'elc_rspn_emp_id', i.emp_no)
            dataProvider.setValue(row, 'elc_pric_nm', i.emp_nm)
            dataProvider.setValue(row, 'elc_pric_dept_nm', i.dept_nm)
            if (i.jkw_ko_nm === 'undefined' || i.jkw_ko_nm == null || i.jkw_ko_nm === '') {
              dataProvider.setValue(row, 'elc_pric_pstn_nm', '-')
            } else {
              dataProvider.setValue(row, 'elc_pric_pstn_nm', i.jkw_ko_nm)
            }
            dataProvider.setValue(row, 'elc_pric_tel_no', i.emp_cpho_no)
            dataProvider.setValue(row, 'elc_pric_email', i.emp_email)
          } else {
            this.$set(this.frmData, 'clgt_rspn_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'clgt_rspn_emp_id', i.emp_no)
          }
        })
      }
    },
    filepopclose(returnData) {
      gridView.commit()
      gridView3.commit()
      // console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.filePopGb === 'elc') {
        dataProvider.setValue(this.elcRowVal, 'elc_infr_atfl_no', returnData.atfile_no)
        dataProvider.setValue(this.elcRowVal, 'elc_atfl_cnt', returnData.file_cnt)
      } else if (this.filePopGb === 'apal') {
        dataProvider3.setValue(this.apalRowVal, 'apal_atht_atfl_no', returnData.atfile_no)
        dataProvider3.setValue(this.apalRowVal, 'apal_atfl_cnt', returnData.file_cnt)
      }
    },
    tpSaveValidate() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()

      if (this.isEmpty(this.frmData.wkpl_nm)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000339'), { className: 'toast_error' }) // 회사를 입력해주세요.
        this.$refs.cmpy_cd.focus()
        return false
      }
      return true
    },
    saveValidate() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()
      // // if (this.isEmpty(this.frmData.wkpl_id)) {
      // //   this.$toast.show('사업장을 입력해주세요.', { className: 'toast_error' }) // 사업장을 입력해주세요.
      // //   this.$refs.wkpl_id.focus()
      // //   return false
      // // }
      if (this.isEmpty(this.frmData.wkpl_nm)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000339'), { className: 'toast_error' }) // 회사를 입력해주세요.
        this.$refs.cmpy_cd.focus()
        return false
      }
      return true
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
<style scoped>
.custom_render_span {
  flex: 1;
  text-align: left;
  overflow: hidden;
}
</style>
