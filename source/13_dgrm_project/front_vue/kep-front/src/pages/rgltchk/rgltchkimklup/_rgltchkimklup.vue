<template>
  <div>
    <div class="location">
      <!-- Home > 규제 검토 > 규제 검토 상세 -->
      <span>Home > {{ $t('LB00000336') }} > {{ $t('LB00000420') }}</span>
    </div>
    <!-- 규제 검토 상세 -->
    <div class="work_title">
      <h2>{{ $t('LB00000420') }}</h2>
    </div>
    <div class="container_detail">
      <!-- 규제 검토 상세 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000420') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 검토번호 -->
                <div class="label_tit">{{ $t('LB00000397') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검토자 -->
                <div class="label_tit">{{ $t('LB00000283') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검토일 -->
                <div class="label_tit">{{ $t('LB00000284') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 규제검토 사업장 -->
                <div class="label_tit">{{ $t('LB00000416') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청자 -->
                <div class="label_tit">{{ $t('LB00000150') }} / {{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_req_emp_nm }} / {{ frmData.rglt_chk_req_dept_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청일 -->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_req_dt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- 자재 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000405') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 자재코드 -->
                <div class="label_tit">{{ $t('LB00000024') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.mtrl_nm }}</div>
              </v-col>
            </v-row>
            <v-row v-if="frmData.imk_item_yn">
              <v-col cols="1">
                <!-- IMK자재코드 -->
                <div class="label_tit">IMK {{ $t('LB00000024') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.imk_item_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- IMK URL -->
                <div class="label_tit">IMK URL</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con"><a href="http://www.imkmall.com" target="_blank">http://www.imkmall.com</a></div>
              </v-col>
            </v-row>
            <v-row v-if="frmData.imk_item_yn1">
              <v-col cols="1">
                <!-- IMK자재코드 -->
                <div class="label_tit">구매처</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.vndr_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- IMK URL -->
                <div class="label_tit">구매처 URL</div>
              </v-col>
              <v-col cols="7">
                <!-- eslint-disable-next-line -->
                <a v-bind:href="frmData.vndr_url" target="_blank">{{ frmData.vndr_url }}</a>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재구분 -->
                <div class="label_tit">{{ $t('LB00000022') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_clsf_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 품목구분 -->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.macl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재등록일 -->
                <div class="label_tit">{{ $t('LB00000404') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_reg_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재등록 요청자 -->
                <div class="label_tit">{{ $t('LB00000403') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_reg_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재등록 사업장 -->
                <div class="label_tit">{{ $t('LB00000402') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_site }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재 성상 -->
                <div class="label_tit">{{ $t('LB00000355') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.phase_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재 용도 -->
                <div class="label_tit">{{ $t('LB00000354') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_puse_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재 기타 용도 -->
                <div class="label_tit">{{ $t('LB00000353') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_puse_cntn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 단위중량 -->
                <div class="label_tit">{{ $t('LB00000528') }}</div>
              </v-col>
              <v-col cols="11" style="display: flex">
                <div class="label_con">{{ frmData.pchs_unit_weght }}</div>
                <span>&nbsp;</span>
                <div class="label_con">{{ frmData.pchs_unit_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- MSDS 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000506') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 제정일 -->
                <div class="label_tit">{{ $t('LB00000202') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.enact_da }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 개정일 -->
                <div class="label_tit">{{ $t('LB00000203') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.rev_da }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS버전 -->
                <div class="label_tit">{{ $t('LB00000266') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.msds_ver }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS 국문 -->
                <div class="label_tit">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <file-upload ref="upload1" input-id="file" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- MSDS 영문 -->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <file-upload ref="upload2" input-id="file2" :multiple="false" />
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 구성물질 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000400') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ sbstTotalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbstTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제검토 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000268') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ rgltTotalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ rgltTotalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- 규제검토 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000268') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 검토담당자 -->
                <div class="label_tit">{{ $t('LB00000395') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ rsltData.rglt_chk_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검토일시 -->
                <div class="label_tit">{{ $t('LB00000415') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ rsltData.rglt_chk_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 위험물여부 -->
                <div class="label_tit">{{ $t('LB00000538') }}</div>
              </v-col>
              <v-col cols="11" style="display: flex">
                <div class="label_con">{{ frmData.dngr_kind_nm }} / {{ frmData.dngr_dtl_kind_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 검토결과 -->
                <div class="label_tit">{{ $t('LB00000282') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ rsltData.rglt_chk_mtrl_use_nm }}</div>
                <v-textarea
                  ref="rgltchkrsltcntn"
                  v-model="rsltData.rglt_chk_rslt_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  height="60"
                  class="mt-2"
                  dense
                  disabled
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row v-show="rsltData.chmt_mtra_rgln_exmt_excl_txt !== undefined">
              <v-col cols="1">
                <!-- 제외요청 사유 -->
                <div class="label_tit">{{ $t('LB00001295') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="rgltchkreqexcnm"
                  v-model="rsltData.chmt_mtra_rgln_exmt_excl_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  height="60"
                  class="mt-2"
                  dense
                  disabled
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 검토반려 -->
                <div class="label_tit">{{ $t('LB00000419') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="rgltchkrejccntn"
                  v-model="rsltData.rglt_chk_rejc_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  height="60"
                  class="mt-2"
                  dense
                  disabled
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- 경고표지 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000038') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.mtrl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 신호어 -->
                <div class="label_tit">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ alertData.sgw_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 그림문자 -->
                <div class="label_tit">{{ $t('LB00000172') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <table>
                  <tbody>
                    <tr>
                      <td>
                        <img src="~assets/images/P01.png" alt="" :class="{ img_unchk: !picData.ck1 }" />
                        <v-checkbox v-model="picData.ck1" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P02.png" alt="" :class="{ img_unchk: !picData.ck2 }" />
                        <v-checkbox v-model="picData.ck2" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P03.png" alt="" :class="{ img_unchk: !picData.ck3 }" />
                        <v-checkbox v-model="picData.ck3" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P04.png" alt="" :class="{ img_unchk: !picData.ck4 }" />
                        <v-checkbox v-model="picData.ck4" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P05.png" alt="" :class="{ img_unchk: !picData.ck5 }" />
                        <v-checkbox v-model="picData.ck5" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~assets/images/P06.png" alt="" :class="{ img_unchk: !picData.ck6 }" />
                        <v-checkbox v-model="picData.ck6" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P07.png" alt="" :class="{ img_unchk: !picData.ck7 }" />
                        <v-checkbox v-model="picData.ck7" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P08.png" alt="" :class="{ img_unchk: !picData.ck8 }" />
                        <v-checkbox v-model="picData.ck8" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td>
                        <img src="~assets/images/P09.png" alt="" :class="{ img_unchk: !picData.ck9 }" />
                        <v-checkbox v-model="picData.ck9" height="30" :hide-details="true" disabled></v-checkbox>
                      </td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 유해위험문구 -->
                <div class="label_tit">{{ $t('LB00000385') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ alertData.hars_cntn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 예방조치문구 -->
                <div class="label_tit">{{ $t('LB00000192') }}</div>
              </v-col>
              <v-col cols="11">
                <v-container style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="1" justify-center>
                      <!-- 예방 -->
                      <div class="label_tit">{{ $t('LB00000191') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <div class="label_con">{{ alertData.prv_cntn }}</div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 대응 -->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <div class="label_con">{{ alertData.act_cntn }}</div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 저장 -->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <div class="label_con">{{ alertData.str_cntn }}</div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 폐기 -->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <div class="label_con">{{ alertData.scr_cntn }}</div>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 공급업체 -->
                <div class="label_tit">{{ $t('LB00000277') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <v-text-field
                  ref="vndr"
                  v-model="alertData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  readonly
                  :disabled="kyin_yn"
                  :placeholder="$t('LB00000033')"
                  height="30"
                  class="icon_sch"
                ></v-text-field>
                <v-checkbox v-model="t_kyin_yn" label="직접입력" height="30" :hide-details="true" readonly class="ml-1"></v-checkbox>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체명 -->
                <div class="label_tit">{{ $t('LB00000130') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrNm"
                  v-model="alertData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  height="30"
                  readonly
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 공급업체 주소 -->
                <div class="label_tit">{{ $t('LB00000413') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  v-model="alertData.vndr_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  height="30"
                  readonly
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체 국가 -->
                <div class="label_tit">{{ $t('LB00000412') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="ntcd"
                  v-model="alertData.nat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!kyin_yn"
                  dense
                  height="30"
                  readonly
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 공급자 연락처 -->
                <div class="label_tit">{{ $t('LB00000411') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrPhon"
                  v-model="alertData.vndr_phon_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  height="30"
                  readonly
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체 대표자명 -->
                <div class="label_tit">{{ $t('LB00000410') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  v-model="alertData.vndr_rprs_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  height="30"
                  readonly
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 사업자 등록번호 -->
                <div class="label_tit">{{ $t('LB00000409') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  v-model="alertData.vndr_coreg_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  height="30"
                  readonly
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 검토반려취소 -->
      <v-btn v-if="btn_reject_cancel_yn && authChk" depressed width="120" color="primary" height="40" @click="chkRejectCancel()">{{ $t('LB00001244') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '규제검토-규제검토상세',
    key(route) {
      return `/rgltchk/rgltchk/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      authChk: false,
      btn_reject_cancel_yn: false,
      t_kyin_yn: false,
      kyin_yn: false,
      tmpSchData: {},
      tmpDtlSchData: {},
      schData: {},
      rsltData: {
        rglt_chk_emp_nm: null,
        rglt_chk_dt: null,
        rglt_chk_mtrl_use_nm: null,
        rglt_chk_rslt_cntn: null,
      },
      frmData: {},
      alertData: {},
      picData: {
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
      sbstTotalcnt: 0,
      rgltTotalcnt: 0,
      rgltChkNo: '',
      codeid: ['CH00000009', 'CO00000003', 'CH00000004', 'CO00000018', 'CH00000017'],
      codes: {
        CH00000009: [{ cd: '', cd_nm: '' }],
        CO00000003: [{ cd: '', cd_nm: '' }],
        CH00000004: [{ cd: '', cd_nm: '' }],
        CO00000018: [{ cd: '', cd_nm: '' }],
        CO00000018p: [],
        CH00000017: [{ cd: '', cd_nm: '' }],
      },
      selCH00000009: {
        cd: [],
        cd_nm: [],
      },
      selCH00000004: {
        cd: [],
        cd_nm: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000420') + '(MRO)'
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.rgltChkNo = this.$route.params.rgltchkimklup
    this.user = this.$store.getters['auth/getUser']
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        rglt_chk_prog_stt_cd: '',
        rglt_chk_req_emp_no: '',
        userno: '',
        username: '',
        sDate: '',
        eDate: '',
      }
    }
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCH00000004.cd.push('')
        this.selCH00000004.cd_nm.push('SEL')
        res.CH00000004.forEach((i) => {
          this.selCH00000004.cd.push(i.cd)
          this.selCH00000004.cd_nm.push(i.cd_nm)
        })
        res.CH00000009.forEach((i) => {
          this.selCH00000009.cd.push(i.cd)
          this.selCH00000009.cd_nm.push(i.cd_nm)
        })
        this.codes.CO00000018.unshift({ cd: '', cd_nm: 'SELECT' })
        const temp = []
        res.CO00000018.forEach((e) => {
          temp.push({ value: e.cd, label: e.cd_nm })
        })
        this.codes.CO00000018p = temp
        this.codes.CH00000017.unshift({ cd: '', cd_nm: 'SELECT' })
        const fields = [
          {
            fieldName: 'id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_chk_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chk_req_clsf_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'mtrl_no',
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
            fieldName: 'cas_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'sbst_nm',
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
        ]

        const fields2 = [
          {
            fieldName: 'id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_chk_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chk_req_clsf_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'mtrl_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'law_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'law_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cas_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_note',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'sbst_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'org_tgt_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'tgt_yn',
            dataType: ValueType.BOOLEAN,
          },
          {
            fieldName: 'rglt_abbr',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_type_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_chk_doma_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'nat_cd',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: 'ID',
            name: 'id',
            fieldName: 'id',
            width: '50',
          },
          {
            header: this.$t('LB00000270'), // 물질구분
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
            name: 'sbst_nm',
            fieldName: 'sbst_nm',
            width: '100',
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
            header: this.$t('LB00000095'), // 비고
            name: 'sbst_note',
            fieldName: 'sbst_note',
            width: '100',
            styleName: 'left',
          },
        ]

        const columns2 = [
          {
            header: 'ID',
            name: 'id',
            fieldName: 'id',
            width: '50',
          },
          {
            header: this.$t('LB00000272'), // 법
            name: 'law_nm',
            fieldName: 'law_nm',
            width: '100',
          },
          {
            header: this.$t('LB00000028'), // 규제
            name: 'rglt_nm',
            fieldName: 'rglt_nm',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000392'), // 대상여부
            name: 'tgt_yn',
            fieldName: 'tgt_yn',
            width: '100',
            editable: false,
            renderer: {
              type: 'check',
              editable: false,
            },
          },
          {
            header: this.$t('LB00000408'), // 대상물질
            name: 'cas_no',
            fieldName: 'cas_no',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000095'), // 비고
            name: 'rglt_note',
            fieldName: 'rglt_note',
            width: '200',
            styleName: 'left',
          },
        ]

        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid2')
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

        gridView.setColumnProperty('id', 'visible', false)

        dataProvider2 = new LocalDataProvider(false)
        dataProvider2.setFields(fields2)
        gridView2 = new GridView('realgrid3')
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

        gridView2.setColumnProperty('id', 'visible', false)

        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/ch/rgltchk/rglt-chk-dtl/${this.rgltChkNo}`)

      // 권한체크
      if (data.auth[0].auth === 'Y') {
        this.authChk = true
      }

      this.frmData = data.basic[0]

      // 검토반려 버튼 활성화체크
      const sttCd = this.frmData.rglt_chk_prog_stt_cd
      if (sttCd === '40') {
        this.btn_reject_cancel_yn = true
      } else {
        this.btn_reject_cancel_yn = false
      }

      this.$refs.upload1.setEdit(false)
      this.$refs.upload2.setEdit(false)

      if (!this.isEmpty(this.frmData.ko_atfile_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.frmData.ko_atfile_no}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles1(data)
          }
        })
      }
      if (!this.isEmpty(this.frmData.en_atfile_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.frmData.en_atfile_no}`).then((data) => {
          console.log('files2 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles2(data)
          }
        })
      }

      dataProvider.setRows(data.sbst)
      gridView.refresh()
      gridView.setTopItem(0)

      this.sbstTotalcnt = dataProvider.getRowCount()

      dataProvider2.setRows(data.rglt)
      gridView2.refresh()
      gridView2.setTopItem(0)

      this.rgltTotalcnt = dataProvider2.getRowCount()

      if (data.rslt.length > 0) {
        this.rsltData = data.rslt[0]
      } else {
        this.rsltData = []
      }

      if (data.alert.length > 0) {
        this.alertData = data.alert[0]
        if (this.alertData.vndr_kyin_yn === 'Y') {
          this.t_kyin_yn = true
          this.kyin_yn = true
        } else {
          this.t_kyin_yn = false
          this.kyin_yn = false
        }
      } else {
        this.alertData = []
      }

      if (data.pic.length > 0) {
        this.frmData.pic = data.pic
        for (let i = 0; i < data.pic.length; i++) {
          if (data.pic[i].picg_cd === '1') {
            this.picData.ck1 = true
          } else if (data.pic[i].picg_cd === '2') {
            this.picData.ck2 = true
          } else if (data.pic[i].picg_cd === '3') {
            this.picData.ck3 = true
          } else if (data.pic[i].picg_cd === '4') {
            this.picData.ck4 = true
          } else if (data.pic[i].picg_cd === '5') {
            this.picData.ck5 = true
          } else if (data.pic[i].picg_cd === '6') {
            this.picData.ck6 = true
          } else if (data.pic[i].picg_cd === '7') {
            this.picData.ck7 = true
          } else if (data.pic[i].picg_cd === '8') {
            this.picData.ck8 = true
          } else if (data.pic[i].picg_cd === '9') {
            this.picData.ck9 = true
          }
        }
      } else {
        this.frmData.pic = []
      }

      // IMK자재번호 존재 여부
      if (!this.isEmpty(this.frmData.imk_item_no)) {
        this.frmData.imk_item_yn = true
        this.frmData.imk_item_yn1 = false
      } else {
        this.frmData.imk_item_yn = false
        this.frmData.imk_item_yn1 = true
      }
    },
    download(key, fileName) {
      if (this.isEmpty(key) || this.isEmpty(fileName)) return
      console.log('download...', key) // eslint-disable-line no-console
      this.$axios
        .$get(`/api/v1/common/file-down/${key}`, {
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          // IE 10+
          if (window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(new Blob([res]), fileName)
          } else {
            // not IE
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(new Blob([res]))
            link.target = '_self'
            link.download = fileName
            link.click()
          }
        })
    },
    downKoFile() {
      // 다운로드 처리
      const koDownKey = this.frmData.ko_atfile_key
      const koDownNm = this.frmData.ko_atfile_nm
      this.download(koDownKey, koDownNm)
    },
    downEnFile() {
      // 다운로드 처리
      const enDownKey = this.frmData.en_atfile_key
      const enDownNm = this.frmData.en_atfile_nm
      this.download(enDownKey, enDownNm)
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.changeEditMode(false)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    // 검토반려 취소
    async chkRejectCancel() {
      const res = await this.$confirm(this.$t('MS00000641')) // 검토반려를 취소하시겠습니까?
      if (!res) {
        return
      }

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      console.log('this.frmData => ', this.frmData) // eslint-disable-line no-console

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-rglt-reject-cancel', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000642')) // 검토반려가 취소되었습니다.
          this.$router.push({
            name: `rgltchk-rgltchklist`,
            params: {
              schData: {
                wkpl_id: this.tmpSchData.wkpl_id,
                mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
                macl_cd: this.tmpSchData.macl_cd,
                mtrl_no: this.tmpSchData.mtrl_no,
                mtrl_nm: this.tmpSchData.mtrl_nm,
                rglt_chk_req_prog_stt_cd: this.tmpSchData.rglt_chk_req_prog_stt_cd,
                rglt_chk_prog_stt_cd: this.tmpSchData.rglt_chk_prog_stt_cd,
                rglt_chk_req_emp_no: this.tmpSchData.rglt_chk_req_emp_no,
                userno: this.tmpSchData.userno,
                username: this.tmpSchData.username,
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: `rgltchk-rgltchklist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
            macl_cd: this.tmpSchData.macl_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            rglt_chk_prog_stt_cd: this.tmpSchData.rglt_chk_prog_stt_cd,
            rglt_chk_req_emp_no: this.tmpSchData.rglt_chk_req_emp_no,
            userno: this.tmpSchData.userno,
            username: this.tmpSchData.username,
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
