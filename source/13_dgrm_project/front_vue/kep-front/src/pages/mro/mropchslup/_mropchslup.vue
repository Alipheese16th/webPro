<template>
  <div>
    <div class="location">
      <!--MRO 구매요청(KO)--><!--MRO구매요청등록-최초구매(KO)-->
      <span>Home > {{ $t('LB00000153') }} > {{ $t('LB00000171') }}</span>
    </div>
    <div class="work_title">
      <!--MRO구매요청등록-최초구매(KO)-->
      <h2>{{ $t('LB00000171') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--MRO 구매요청(KO)-->
        <h3>{{ $t('LB00000153') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--요청번호-->
                <div class="label_tit">{{ $t('LB00000151') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.mro_pchs_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--요청자 / 부서-->
                <div class="label_tit">{{ $t('LB00000150') }} / {{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.upt_emp_nm }} / {{ mroSaveData.upt_dept_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--요청일-->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.wkpl_id_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--품목명(KO)-->
                <div class="label_tit">{{ $t('LB00000159') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ mtrlSaveData.mtrl_ko_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구매수량-->
                <div class="label_tit">{{ $t('LB00000527') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ mtrlSaveData.pchs_qty }}</div>
              </v-col>
              <v-col cols="1">
                <!--단위중량-->
                <div class="label_tit">{{ $t('LB00000528') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <div class="label_con">{{ mtrlSaveData.pchs_unit_weght }}</div>
                <span>&nbsp;</span>
                <div class="label_con">{{ mtrlSaveData.pchs_unit_nm }}</div>
              </v-col>
              <!-- <v-col cols="1">
                // 주문단위별 순중량(KO)
                <div class="label_tit">{{ $t('LB00000169') }}</div>
              </v-col>
              <v-col cols="7">
                <v-container style="border-top: 0px; border-bottom: 0px">
                  <v-row style="border-top: 0px; border-bottom: 0px">
                    <v-col cols="3">
                      <div class="label_con">{{ mtrlSaveData.pchs_unit_nweght_qty }}</div>
                    </v-col>
                    <v-col cols="3">
                      // 알 수 없음
                      <v-checkbox
                        v-model="mtrlSaveData.nweght_uncnfm_yn"
                        :label="$t('LB00000351')"
                        :disabled="true"
                        height="30"
                        style="width: 200px"
                        :hide-details="true"
                        class="ml-1"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                </v-container>
                <v-container v-if="mtrlSaveData.nweght_uncnfm_yn1" style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="2" justify-center>
                      // 순중량 url
                      <div class="label_tit">{{ $t('LB00000507') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <div class="label_con">{{ mtrlSaveData.pchs_url }}</div>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col> -->
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--품목구분-->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ mtrlSaveData.macl_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--수입여부(KO)-->
                <div class="label_tit">{{ $t('LB00000157') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!--수입-->
                <v-checkbox v-model="mtrlSaveData.impt_yn" :disabled="true" :label="$t('LB00000197')" height="30" :hide-details="true" class="ml-1"></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--자재 성상-->
                <div class="label_tit essn">{{ $t('LB00000355') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mtrlSaveData.phase_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--자재 용도-->
                <div class="label_tit essn">{{ $t('LB00000354') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mtrlSaveData.mtrl_puse_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--자재 기타 용도-->
                <div class="label_tit">{{ $t('LB00000353') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mtrlSaveData.mtrl_puse_cntn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구매처(KO)-->
                <div class="label_tit">{{ $t('LB00000166') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <div class="label_con">{{ mtrlSaveData.vndr_nm }}</div>
                <!--직접입력(KO)-->
                <v-checkbox v-model="mtrlSaveData.vndr_kyin_yn" :disabled="true" :label="$t('LB00000165')" height="30" :hide-details="true" class="ml-1"></v-checkbox>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처명-->
                <div class="label_tit">{{ $t('LB00000158') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--구매처 주소(KO)-->
                <div class="label_tit">{{ $t('LB00000164') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_addr }}</div>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 국가(KO)-->
                <div class="label_tit">{{ $t('LB00000163') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.nat_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--구매처 연락처(KO)-->
                <div class="label_tit">{{ $t('LB00000162') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_phon_no }}</div>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 대표자명(KO)-->
                <div class="label_tit">{{ $t('LB00000161') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_rprs_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_coreg_no }}</div>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 홈페이지(KO)-->
                <div class="label_tit">{{ $t('LB00000181') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_url }}</div>
              </v-col>
              <v-col cols="1">
                <!--LB00000180-->
                <div class="label_tit">{{ $t('LB00000180') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.vndr_email }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title" style="display: flex">
        <!--MSDS-->
        <h3>{{ $t('LB00000179') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1" justify-center>
                <!-- 제정일 -->
                <div class="label_tit essn">{{ $t('LB00000202') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.enact_da }}</div>
              </v-col>
              <v-col cols="1" justify-center>
                <!-- 개정일 -->
                <div class="label_tit essn">{{ $t('LB00000203') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.rev_da }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS버전 -->
                <div class="label_tit essn">{{ $t('LB00000266') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ mtrlSaveData.msds_ver }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--MSDS(국문)-->
                <div class="label_tit">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col cols="5">
                <file-upload ref="upload1" :multiple="true" />
              </v-col>
              <v-col cols="1">
                <!--MSDS(영문)-->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col cols="5">
                <file-upload ref="upload2" :multiple="true" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--구성 물질 정보(KO)-->
        <h3>{{ $t('LB00000175') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbstSaveData.totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 경고표지 -->
        <h3>{{ $t('LB00000038') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--자재명-->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ mtrlSaveData.mtrl_ko_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--공급자(KO)-->
                <div class="label_tit">{{ $t('LB00000188') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <div class="label_con">{{ gwsgnSaveData.vndr_nm }}</div>
                <!--직접입력(KO)-->
                <v-checkbox v-model="gwsgnSaveData.vndr_kyin_yn" :disabled="true" :label="$t('LB00000165')" height="30" :hide-details="true" class="ml-1"></v-checkbox>
                <!--구매처와 동일(KO)-->
                <v-checkbox v-model="gwsgnSaveData.vndr_same_yn" :disabled="true" :label="$t('LB00000187')" height="30" :hide-details="true" class="ml-1"></v-checkbox>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체명-->
                <div class="label_tit">{{ $t('LB00000130') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--공급업체 주소-->
                <div class="label_tit">{{ $t('LB00000186') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_addr }}</div>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체 국가-->
                <div class="label_tit">{{ $t('LB00000185') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.nat_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--공급업체 연락처-->
                <div class="label_tit">{{ $t('LB00000184') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_phon_no }}</div>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체 대표자명-->
                <div class="label_tit">{{ $t('LB00000183') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_rprs_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_coreg_no }}</div>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--구매처 홈페이지(KO)-->
                <div class="label_tit">{{ $t('LB00000181') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_url }}</div>
              </v-col>
              <v-col cols="1">
                <!--구매처 E-mail(KO)-->
                <div class="label_tit">{{ $t('LB00000180') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ gwsgnSaveData.vndr_email }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--신호어-->
                <div class="label_tit">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <v-radio-group v-model="gwsgnSaveData.sgw_cd" :disabled="true" row :hide-details="true">
                  <v-radio v-for="n in selCH00000009.cd.length" :key="n" :label="selCH00000009.cd_nm[n - 1]" :value="selCH00000009.cd[n - 1]" :disabled="!modify"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--그림문자-->
                <div class="label_tit">{{ $t('LB00000172') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <table>
                  <tbody>
                    <tr>
                      <td>
                        <img src="~assets/images/P01.png" alt="" :class="{ img_unchk: !pichSaveData.ck1 }" />
                        <v-checkbox v-model="pichSaveData.ck1" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P02.png" alt="" :class="{ img_unchk: !pichSaveData.ck2 }" />
                        <v-checkbox v-model="pichSaveData.ck2" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P03.png" alt="" :class="{ img_unchk: !pichSaveData.ck3 }" />
                        <v-checkbox v-model="pichSaveData.ck3" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P04.png" alt="" :class="{ img_unchk: !pichSaveData.ck4 }" />
                        <v-checkbox v-model="pichSaveData.ck4" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P05.png" alt="" :class="{ img_unchk: !pichSaveData.ck5 }" />
                        <v-checkbox v-model="pichSaveData.ck5" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~assets/images/P06.png" alt="" :class="{ img_unchk: !pichSaveData.ck6 }" />
                        <v-checkbox v-model="pichSaveData.ck6" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P07.png" alt="" :class="{ img_unchk: !pichSaveData.ck7 }" />
                        <v-checkbox v-model="pichSaveData.ck7" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P08.png" alt="" :class="{ img_unchk: !pichSaveData.ck8 }" />
                        <v-checkbox v-model="pichSaveData.ck8" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P09.png" alt="" :class="{ img_unchk: !pichSaveData.ck9 }" />
                        <v-checkbox v-model="pichSaveData.ck9" height="30" :hide-details="true" :disabled="!modify"></v-checkbox>
                      </td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--유해위험문구-->
                <div class="label_tit">{{ $t('LB00000193') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  v-model="gwsgnSaveData.hars_cntn"
                  :disabled="true"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  name=""
                  value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--예방조치문구-->
                <div class="label_tit">{{ $t('LB00000192') }}</div>
              </v-col>
              <v-col cols="11">
                <v-container style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="1" justify-center>
                      <!--예방-->
                      <div class="label_tit">{{ $t('LB00000191') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.prv_cntn" :disabled="true" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--대응-->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.act_cntn" :disabled="true" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--저장-->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.str_cntn" :disabled="true" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--폐기-->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.scr_cntn" :disabled="true" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <!-- <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        // 구매요청내용(KO)
        <h3>{{ $t('LB00000147') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor v-model="mroSaveData.pchs_cntn" :options="editorOption" :disabled="true" />
          </v-col>
        </v-row>
      </v-container>
    </div> -->
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: 'MRO구매요청상세-최초구매',
    key(route) {
      return `/mro/mropchs/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    FileUpload,
  },
  data() {
    return {
      codeid: ['CH00000009'],
      codes: {
        CH00000009: [{ cd: '', cd_nm: '' }], // 신호어
      },
      selCH00000009: {
        cd: [],
        cd_nm: [],
      },
      modify: false,
      // Mro_구매 Mst
      mroSaveData: {
        mro_pchs_no: '', // mro구매번호 yymmdd + 6자리시퀀스 tb_ch_mro_pchs_seq 201207000001
        upt_emp_no: '',
        upt_emp_nm: '',
        upt_dt: '',
        wkpl_id: '',
        pchs_cntn: '', // 구매내용
        pchs_prog_stt_cd: '', // 구매내용
        aprv_no: '', // 결재번호
        aprv_crt_dt: '', // 결재 생성 일자
        aprv_cmpt_dt: '', // 결재 승인 일자
        multi_yn: 'N', // 다건여부
        wkpl_id_nm: '', // 사업장 이름
      },
      // Mro구매 자재
      mtrlSaveData: {
        mro_pchs_no: '',
        mtrl_no: 'Z', // 자재번호(단건일경우 Z)
        mtrl_ko_nm: '', // 자재명
        pchs_unit_nm: '', // 구매 단위 명
        pchs_unit_nweght_qty: '', // 구매 단위 순중량
        pchs_qty: '', // 구매 량
        macl_cd: '', // 품목 구문 코드
        impt_yn: '', // 수입 여부
        ko_atfile_no: '', // 한글 첨부파일
        en_atfile_no: '', // 영문 첨부파일
        vndr_no: '', // 공급업체번호
        vndr_kyin_yn: '', // 공급업체 직접입력 여부
        vndr_nm: '', // 공급업체명
        vndr_type_cd: '2', // 공급업체 유형 코드
        nat_cd: '', // 국가코드
        vndr_addr: '', // 공급업체 주소
        vndr_phon_no: '', // 전화번호
        vndr_rprs_nm: '', // 대표자 명
        vndr_coreg_no: '', // 사업자 등록번호
        vndr_email: '', // 이메일
        vndr_url: '', // url
        vndr_kyin_yn1: false,
        macl_cd_nm: '', // 품목 구분 이름
        nat_cd_nm: '', // 국가 이름
        nweght_uncnfm_yn1: false, // 순중량_미확정_여부
        nweght_uncnfm_yn: '', // 순중량_미확정_여부
        pchs_url: '', // 구매_URL
        phase_cd: '', // 성상_코드
        mtrl_puse_cd: '', // 자재_용도_코드
        mtrl_puse_cntn: '', // 자재_용도_내용
        phase_cd_nm: '', // 성상_코드
        mtrl_puse_cd_nm: '', // 자재_용도_코드
        msds_ver: '', // MSDS_버전
        enact_da: '', // 제정_일자
        rev_da: '', // 개정_일자
      },
      // Mro_구매 물질
      sbstSaveData: {
        totalcnt: 0,
      },
      // Mro_구매 경고표지
      gwsgnSaveData: {
        mro_pchs_no: '', // mro 구매번호
        mtrl_no: 'Z', // 자재번호
        sgw_cd: '', // 신호어코드
        hars_cntn: '', // 유해위험_내용
        prv_cntn: '', // 에방내용
        act_cntn: '', // 대응내용
        str_cntn: '', // 보관내용
        scr_cntn: '', // 폐기내용
        vndr_no: '', // 공급업체번호
        vndr_kyin_yn: '', // 직접입력여부
        vndr_same_yn: '', // 구매처와동일여부
        vndr_nm: '', // 공급업체명
        vndr_type_cd: '', // 공급업체유형코드
        nat_cd: '', // 공급업체 국가코드
        vndr_addr: '', // 공급업체 주소
        vndr_phon_no: '', // 공급업체 전화번호
        vndr_rprs_nm: '', // 공급업체 대표자명
        vndr_coreg_no: '', // 사업자등록번호
        vndr_email: '', // 이메일
        vndr_url: '', // url
        vndr_kyin_yn2: false,
        vndr_same_yn2: false,
        nat_cd_nm: '', // 국가 이름
      },
      // Mro구매 그림문자
      pichSaveData: {
        mro_pchs_no: '', // mro 구매번호
        mtrl_no: 'Z', // 자재번호
        ck1: false,
        ck2: false,
        ck3: false,
        ck4: false,
        ck5: false,
        ck6: false,
        ck7: false,
        ck8: false,
        ck9: false,
      },
      sbstlist: [], //  물질 정보
      pic: [], // 그림 정보
      editorOption: {
        // some quill options
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // toggled buttons
            ['blockquote', 'code-block'],

            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }], // outdent/indent

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ align: [] }],

            ['link', 'image'],

            ['clean'], // remove formatting button
          ],
        },
        placeholder: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000171')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    this.mroSaveData.upt_emp_nm = this.usrinfo.emp_nm
    this.mroSaveData.upt_dept_nm = this.usrinfo.dept_nm
    this.mroSaveData.upt_emp_no = this.usrinfo.emp_no
    this.mroSaveData.mro_pchs_no = this.$route.params.mropchslup
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        macl_cd: '',
        pchs_prog_stt_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        emp_nm: '',
        sDate: '',
        eDate: '',
        multi_yn: '',
      }
    }
    this.selectDtl(this.mroSaveData.mro_pchs_no)
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      res.CH00000009.forEach((i) => {
        this.selCH00000009.cd.push(i.cd)
        this.selCH00000009.cd_nm.push(i.cd_nm)
      })
    })
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000194'), // 물질유형
        name: 'sbst_type_nm',
        fieldName: 'sbst_type_nm',
        width: '100',
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '300',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000195'), // 함량(%)
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000095'), // 비고(KO)
        name: 'sbst_note',
        fieldName: 'sbst_note',
        width: '100',
        type: 'button',
        styleName: 'left',
      },
    ]
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
  },
  methods: {
    selectDtl(mropchsno) {
      this.$axios.$get(`/api/v1/ch/mro/mropchs/${mropchsno}`).then((res) => {
        console.log(res) // eslint-disable-line no-console

        this.mroSaveData = res.mroSaveData
        this.mtrlSaveData = res.mtrlSaveData
        dataProvider.setRows(res.sbstlist)
        gridView.refresh()
        gridView.setTopItem(0)
        this.sbstSaveData.totalcnt = gridView.getItemCount()
        this.gwsgnSaveData = res.gwsgnSaveData

        if (res.mtrlSaveData.impt_yn === 'Y') {
          this.mtrlSaveData.impt_yn = true
        } else {
          this.mtrlSaveData.impt_yn = false
        }
        if (res.mtrlSaveData.vndr_kyin_yn === 'Y') {
          this.mtrlSaveData.vndr_kyin_yn1 = true
          this.mtrlSaveData.vndr_kyin_yn = true
        } else {
          this.mtrlSaveData.vndr_kyin_yn1 = false
          this.mtrlSaveData.vndr_kyin_yn = false
        }
        // 주문단위 별 순중량 알수없음 미확정 여부
        if (this.mtrlSaveData.nweght_uncnfm_yn === 'Y') {
          this.mtrlSaveData.nweght_uncnfm_yn1 = true
          this.mtrlSaveData.nweght_uncnfm_yn = true
        } else {
          this.mtrlSaveData.nweght_uncnfm_yn1 = false
          this.mtrlSaveData.nweght_uncnfm_yn = false
        }
        if (res.gwsgnSaveData.vndr_kyin_yn === 'Y') {
          this.gwsgnSaveData.vndr_kyin_yn2 = true
          this.gwsgnSaveData.vndr_kyin_yn = true
        } else {
          this.gwsgnSaveData.vndr_kyin_yn2 = false
          this.gwsgnSaveData.vndr_kyin_yn = false
        }
        if (res.gwsgnSaveData.vndr_same_yn === 'Y') {
          this.gwsgnSaveData.vndr_same_yn2 = true
          this.gwsgnSaveData.vndr_same_yn = true
        } else {
          this.gwsgnSaveData.vndr_same_yn2 = false
          this.gwsgnSaveData.vndr_same_yn = false
        }

        if (res.pic === null || res.pic === []) {
          this.pic = []
        } else {
          this.pic = res.pic
          for (let i = 0; i < res.pic.length; i++) {
            if (res.pic[i].picg_cd === '1') {
              this.pichSaveData.ck1 = true
            } else if (res.pic[i].picg_cd === '2') {
              this.pichSaveData.ck2 = true
            } else if (res.pic[i].picg_cd === '3') {
              this.pichSaveData.ck3 = true
            } else if (res.pic[i].picg_cd === '4') {
              this.pichSaveData.ck4 = true
            } else if (res.pic[i].picg_cd === '5') {
              this.pichSaveData.ck5 = true
            } else if (res.pic[i].picg_cd === '6') {
              this.pichSaveData.ck6 = true
            } else if (res.pic[i].picg_cd === '7') {
              this.pichSaveData.ck7 = true
            } else if (res.pic[i].picg_cd === '8') {
              this.pichSaveData.ck8 = true
            } else if (res.pic[i].picg_cd === '9') {
              this.pichSaveData.ck9 = true
            }
          }
        }
        this.changeEditMode(false)
        if (!this.isEmpty(res.mtrlSaveData.ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${res.mtrlSaveData.ko_atfile_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(res.mtrlSaveData.en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${res.mtrlSaveData.en_atfile_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
      })
    },
    goList() {
      this.$router.push({
        name: this.$route.params.goData,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            macl_cd: this.tmpSchData.macl_cd,
            pchs_prog_stt_cd: this.tmpSchData.pchs_prog_stt_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            emp_nm: this.tmpSchData.emp_nm,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
            multi_yn: this.tmpSchData.multi_yn,
          },
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'mro_pchs_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_seq',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_rati',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_note',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_ko_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_en_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cas_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'row_status',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'use_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'del_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style></style>
