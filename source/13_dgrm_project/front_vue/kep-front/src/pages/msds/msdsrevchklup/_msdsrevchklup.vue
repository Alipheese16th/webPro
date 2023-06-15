<template>
  <div>
    <div class="location">
      <!-- MSDS 개정 검토 상세 -->
      <span>Home > MSDS > {{ $t('LB00000286') }}</span>
    </div>
    <!-- MSDS 개정 검토 상세 -->
    <div class="work_title">
      <!-- eslint-disable -->
      <h2>{{ $t('LB00000286') }} <span v-html="titleHtml" class="work_title2"></span></h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- MSDS 개정 검토 -->
        <h3>{{ $t('LB00000287') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 요청번호 -->
                <div class="label_tit">{{ $t('LB00000151') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.msds_rev_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청자 -->
                <div class="label_tit">{{ $t('LB00000150') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.req_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청일 -->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.req_da }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재번호 -->
                <div class="label_tit">{{ $t('LB00000199') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.mtrl_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.msds.mtrl_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 검토자 -->
                <div class="label_tit">{{ $t('LB00000283') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.msds.rev_chk_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검토일 -->
                <div class="label_tit">{{ $t('LB00000284') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.rev_chk_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 검토결과 -->
                <div class="label_tit">{{ $t('LB00000282') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.msds.rev_chk_cntn" outlined single-line :hide-details="true" readonly dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 반려자 -->
                <div class="label_tit">{{ $t('LB00000279') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.msds.rev_chk_rejc_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 반려일 -->
                <div class="label_tit">{{ $t('LB00000280') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.rev_chk_rejc_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 반려내용 -->
                <div class="label_tit">{{ $t('LB00000281') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.msds.rev_chk_rejc_cntn" outlined single-line :hide-details="true" readonly dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title"><h3>MSDS</h3></div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 개정 전 제/개정일 -->
                <div class="label_tit">{{ $t('LB00000201') }}</div>
              </v-col>
              <v-col cols="5">
                <v-container style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="2" justify-center>
                      <!-- 제정일 -->
                      <div class="label_tit">{{ $t('LB00000202') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="frmData.msds.rev_bf_enact_da"
                            prepend-icon="event"
                            readonly
                            disabled
                            outlined
                            :hide-details="true"
                            dense
                            height="30"
                            v-bind="attrs"
                            class="comp_calendar mr-1"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="frmData.msds.rev_bf_enact_da" no-title @input="menu1 = false"> </v-date-picker>
                      </v-menu>
                    </v-col>
                    <v-col cols="2" justify-center>
                      <!-- 개정일 -->
                      <div class="label_tit">{{ $t('LB00000203') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="frmData.msds.rev_bf_last_rev_da"
                            prepend-icon="event"
                            readonly
                            disabled
                            outlined
                            :hide-details="true"
                            dense
                            height="30"
                            v-bind="attrs"
                            class="comp_calendar mr-1"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="frmData.msds.rev_bf_last_rev_da" no-title @input="menu2 = false"> </v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 제/개정일 -->
                <div class="label_tit">{{ $t('LB00000204') }}</div>
              </v-col>
              <v-col cols="5">
                <v-container style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="2" justify-center>
                      <!-- 제정일 -->
                      <div class="label_tit">{{ $t('LB00000202') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <v-menu v-model="menu3" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            v-model="frmData.msds.rev_af_enact_da"
                            prepend-icon="event"
                            readonly
                            disabled
                            outlined
                            :hide-details="true"
                            dense
                            height="30"
                            v-bind="attrs"
                            class="comp_calendar mr-1"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="frmData.msds.rev_af_enact_da" no-title @input="menu3 = false"> </v-date-picker>
                      </v-menu>
                    </v-col>
                    <v-col cols="2" justify-center>
                      <!-- 개정일 -->
                      <div class="label_tit">{{ $t('LB00000203') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <v-menu v-model="menu4" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            ref="revDt"
                            v-model="frmData.msds.rev_af_last_rev_da"
                            prepend-icon="event"
                            readonly
                            disabled
                            outlined
                            :hide-details="true"
                            dense
                            height="30"
                            v-bind="attrs"
                            class="comp_calendar mr-1"
                            v-on="on"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="frmData.msds.rev_af_last_rev_da" no-title @input="menu4 = false"> </v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 개정 전 버전 -->
                <div class="label_tit">{{ $t('LB00000205') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.msds.rev_bf_ver }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 버전 -->
                <div class="label_tit">{{ $t('LB00000206') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.msds.rev_af_ver }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 개정 전 MSDS(국문) -->
                <div class="label_tit">{{ $t('LB00000207') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <!-- <p class="link" style="margin-bottom: 0px" @click="downKoFile">{{ frmData.msds.rev_bf_ko_atfile_nm }}</p> -->
                  <file-upload ref="upload1" input-id="file" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 MSDS(국문) -->
                <div class="label_tit">{{ $t('LB00000208') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <!-- <p class="link" style="margin-bottom: 0px" @click="downKoAfFile">{{ frmData.msds.rev_af_ko_atfile_nm }}</p> -->
                  <file-upload ref="upload2" input-id="file2" :multiple="false" />
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 개정 전 MSDS(영문) -->
                <div class="label_tit">{{ $t('LB00000209') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <!-- <p class="link" style="margin-bottom: 0px" @click="downEnFile">{{ frmData.msds.rev_bf_en_atfile_nm }}</p> -->
                  <file-upload ref="upload3" input-id="file3" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 MSDS(영문) -->
                <div class="label_tit">{{ $t('LB00000210') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <!-- <p class="link" style="margin-bottom: 0px" @click="downEnAfFile">{{ frmData.msds.rev_af_en_atfile_nm }}</p> -->
                  <file-upload ref="upload4" input-id="file4" :multiple="false" />
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 개정 경고표지 -->
        <h3>{{ $t('LB00000211') }}</h3>
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
                <div class="label_con">{{ frmData.msds.mtrl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 신호어 -->
                <div class="label_tit">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.alert.sgw_nm }}</div>
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
                        <img src="~static/images/P01.png" alt="" :class="{ img_unchk: !frmData.alert.ck1 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck1" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck1" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P02.png" alt="" :class="{ img_unchk: !frmData.alert.ck2 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck2" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck2" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P03.png" alt="" :class="{ img_unchk: !frmData.alert.ck3 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck3" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck3" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P04.png" alt="" :class="{ img_unchk: !frmData.alert.ck4 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck4" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck4" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P05.png" alt="" :class="{ img_unchk: !frmData.alert.ck5 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck5" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck5" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~static/images/P06.png" alt="" :class="{ img_unchk: !frmData.alert.ck6 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck6" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck6" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P07.png" alt="" :class="{ img_unchk: !frmData.alert.ck7 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck7" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck7" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P08.png" alt="" :class="{ img_unchk: !frmData.alert.ck8 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck8" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck8" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P09.png" alt="" :class="{ img_unchk: !frmData.alert.ck9 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck9" height="30" :hide-details="true" :label="$t('LB00000392')" disabled></v-checkbox>
                          <v-checkbox v-model="frmData.alert.sck9" height="30" :hide-details="true" :label="$t('LB00000391')" disabled></v-checkbox>
                        </div>
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
                <div class="label_tit">{{ $t('LB00000193') }}</div>
              </v-col>
              <v-col cols="11">
                <!-- eslint-disable-next-line vue/no-v-html-->
                <div class="label_con" v-html="frmData.alert.hars_cntn"></div>
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
                      <!-- eslint-disable-next-line vue/no-v-html-->
                      <div class="label_con" v-html="frmData.alert.prv_cntn"></div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 대응 -->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <!-- eslint-disable-next-line vue/no-v-html-->
                      <div class="label_con" v-html="frmData.alert.act_cntn"></div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 저장 -->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <!-- eslint-disable-next-line vue/no-v-html-->
                      <div class="label_con" v-html="frmData.alert.str_cntn"></div>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 폐기 -->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <!-- eslint-disable-next-line vue/no-v-html-->
                      <div class="label_con" v-html="frmData.alert.scr_cntn"></div>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 공급업체명 -->
                <div class="label_tit">{{ $t('LB00000130') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.vndr_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 공급업체 주소 -->
                <div class="label_tit">{{ $t('LB00000186') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.vndr_addr }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 공급업체 국가 -->
                <div class="label_tit">{{ $t('LB00000185') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.nat_nm }}</div>
              </v-col>
              <!-- 공급자 연락처 -->
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000184') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.vndr_phon_no }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 공급업체 대표자명 -->
                <div class="label_tit">{{ $t('LB00000183') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.vndr_rprs_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 사업자 등록번호 -->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">{{ frmData.alert.vndr_coreg_no }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
import FileUpload from '~/components/FileUpload.vue'

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: 'MSDS-MSDS개정검토상세',
    key(route) {
      return `/msds/msdsChkRevReq/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      titleHtml: '',
      t_kyin_yn: false,
      kyin_yn: false,
      modify: true,
      chk_yn: false,
      rejc_yn: false,
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      schData: {},
      frmData: {
        msds: {
          msds_rev_no: '',
          req_emp_nm: '',
          req_emp_no: '',
          req_da: '',
          mtrl_no: '',
          mtrl_nm: '',
          mtrl_clsf_cd: '',
          rev_bf_ver: '',
          rev_af_ver: '',
          rev_bf_ko_atfile_no: '',
          rev_bf_ko_atfile_key: '',
          rev_bf_ko_atfile_nm: '',
          rev_af_ko_atfile_no: '',
          rev_af_ko_atfile_key: '',
          rev_af_ko_atfile_nm: '',
          rev_bf_en_atfile_no: '',
          rev_bf_en_atfile_key: '',
          rev_bf_en_atfile_nm: '',
          rev_af_en_atfile_no: '',
          rev_af_en_atfile_key: '',
          rev_af_en_atfile_nm: '',
          rev_chk_emp_nm: '',
          rev_chk_dt: '',
          rev_chk_cntn: '',
          rev_chk_rejc_emp_no: '',
          rev_chk_rejc_dt: '',
          rev_chk_rejc_cntn: '',
          rev_prog_stt_cd: '',
          rev_bf_enact_da: '',
          rev_af_enact_da: '',
          rev_bf_last_rev_da: '',
          rev_af_last_rev_da: '',
          rev_wkpl_id: '',
        },
        alert: {
          sgw_cd: '',
          hars_cntn: '',
          prv_cntn: '',
          act_cntn: '',
          str_cntn: '',
          scr_cntn: '',
          vndr_no: '',
          vndr_nm: '',
          vndr_phon_no: '',
          vndr_addr: '',
          nat_cd: '',
          vndr_rprs_nm: '',
          vndr_coreg_no: '',
          vndr_email: '',
          vndr_kyin_yn: 'N',
          ck1: false,
          ck2: false,
          ck3: false,
          ck4: false,
          ck5: false,
          ck6: false,
          ck7: false,
          ck8: false,
          ck9: false,
          sck1: false,
          sck2: false,
          sck3: false,
          sck4: false,
          sck5: false,
          sck6: false,
          sck7: false,
          sck8: false,
          sck9: false,
        },
      },
      codeid: ['CH00000009', 'CO00000003', 'CO00000006'],
      codes: {
        CH00000009: [{ cd: '', cd_nm: '' }],
        CO00000003: [{ cd: '', cd_nm: '' }],
        CO00000006: [{ cd: '', cd_nm: '' }],
      },
      selCH00000009: {
        cd: [],
        cd_nm: [],
      },
      mtrlNo: '',
      mtrlNm: '',
      row: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000286')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    this.frmData.dept_cd = user.dept_cd

    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        rev_prog_stt_cd: '',
        req_emp_nm: '',
        sDate: date.toISOString().substr(0, 10),
        eDate: new Date().toISOString().substr(0, 10),
      }
    }
    // 파라미터 조회 - 파라미터가 존재할 경우(작성 중)
    if (this.$route.params.msdsrevchklup !== null && this.$route.params.msdsrevchklup !== undefined) {
      this.msdsRevNo = this.$route.params.msdsrevchklup
      this.schData.msds_rev_no = this.msdsRevNo

      this.selectMsdsList(this.msdsRevNo)
    } else {
      this.$set(this.frmData.msds, 'rev_wkpl_id', user.wkpl_id)
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      res.CH00000009.forEach((i) => {
        this.selCH00000009.cd.push(i.cd)
        this.selCH00000009.cd_nm.push(i.cd_nm)
      })
    })
    // this.select()
  },
  methods: {
    selectMsdsList(msdsRevNo) {
      this.$axios.$get(`/api/v1/ch/msds/msds-rechk/${msdsRevNo}`).then((res) => {
        // res 값의 정보에 따라 백엔드의 res 체크로 화면 구조 변경
        // 작성중(10), 완료(50), 그 외의 상태는 유동적으로 화면 구조 변경
        if (res.msds[0].rev_prog_stt_cd === '10') {
          this.chk_yn = false
          this.rejc_yn = false
        } else if (res.msds[0].rev_prog_stt_cd === '50') {
          this.chk_yn = true
          this.rejc_yn = false
        } else {
          if (this.isEmpty(res.msds[0].rev_chk_emp_nm)) {
            this.chk_yn = false
          } else {
            this.chk_yn = true
          }
          if (this.isEmpty(res.msds[0].rev_chk_rejc_emp_nm)) {
            this.rejc_yn = false
          } else {
            this.rejc_yn = true
          }
        }
        this.frmData.msds = res.msds[0]
        if (res.alert === null || res.alert.length === 0 || res.alert === 'undefined') {
          this.frmData.alert = { sgw_cd: '' }
        } else {
          this.frmData.alert = res.alert[0]
          // carriage return 수정
          this.frmData.alert.hars_cntn = this.frmData.alert.hars_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
          this.frmData.alert.prv_cntn = this.frmData.alert.prv_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
          this.frmData.alert.act_cntn = this.frmData.alert.act_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
          this.frmData.alert.str_cntn = this.frmData.alert.str_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
          this.frmData.alert.scr_cntn = this.frmData.alert.scr_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
          if (this.frmData.alert.vndr_kyin_yn === true) {
            this.t_kyin_yn = true
            this.kyin_yn = true
          } else {
            this.t_kyin_yn = false
            this.kyin_yn = false
          }
        }
        if (res.pic === null || res.pic === []) {
          this.frmData.pic = []
        } else {
          this.frmData.pic = res.pic
          for (let i = 0; i < res.pic.length; i++) {
            if (res.pic[i].picg_cd === '1') {
              this.frmData.alert.ck1 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck1 = true
              }
            } else if (res.pic[i].picg_cd === '2') {
              this.frmData.alert.ck2 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck2 = true
              }
            } else if (res.pic[i].picg_cd === '3') {
              this.frmData.alert.ck3 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck3 = true
              }
            } else if (res.pic[i].picg_cd === '4') {
              this.frmData.alert.ck4 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck4 = true
              }
            } else if (res.pic[i].picg_cd === '5') {
              this.frmData.alert.ck5 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck5 = true
              }
            } else if (res.pic[i].picg_cd === '6') {
              this.frmData.alert.ck6 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck6 = true
              }
            } else if (res.pic[i].picg_cd === '7') {
              this.frmData.alert.ck7 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck7 = true
              }
            } else if (res.pic[i].picg_cd === '8') {
              this.frmData.alert.ck8 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck8 = true
              }
            } else if (res.pic[i].picg_cd === '9') {
              this.frmData.alert.ck9 = true
              if (res.pic[i].print_yn === 'Y') {
                this.frmData.alert.sck9 = true
              }
            }
          }
        }
        if (!this.isEmpty(this.frmData.msds.rev_af_ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_af_ko_atfile_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_af_en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_af_en_atfile_no}`).then((data) => {
            console.log('files4 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles4(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_bf_ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_bf_ko_atfile_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_bf_en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_bf_en_atfile_no}`).then((data) => {
            console.log('files3 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles3(data)
            }
          })
        }
        this.changeEditMode(false)

        // 둘다 값이 와이이면...'※ MSDS 제출 대상 자재'
        if (this.frmData.msds.imp_yn === 'Y' && this.frmData.msds.sbms_tgt_yn === 'Y') {
          this.titleHtml = this.$t('LB00000620')
        }
      })
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
      const koDownKey = this.frmData.msds.rev_bf_ko_atfile_key
      const koDownNm = this.frmData.msds.rev_bf_ko_atfile_nm
      this.download(koDownKey, koDownNm)
    },
    downEnFile() {
      // 다운로드 처리
      const enDownKey = this.frmData.msds.rev_bf_en_atfile_key
      const enDownNm = this.frmData.msds.rev_bf_en_atfile_nm
      this.download(enDownKey, enDownNm)
    },
    downKoAfFile() {
      // 다운로드 처리
      const koDownKey = this.frmData.msds.rev_af_ko_atfile_key
      const koDownNm = this.frmData.msds.rev_af_ko_atfile_nm
      this.download(koDownKey, koDownNm)
    },
    downEnAfFile() {
      // 다운로드 처리
      const enDownKey = this.frmData.msds.rev_af_en_atfile_key
      const enDownNm = this.frmData.msds.rev_af_en_atfile_nm
      this.download(enDownKey, enDownNm)
    },
    setFiles3(files) {
      this.$nextTick(() => {
        this.$refs.upload3.setFiles(files)
        this.$refs.upload3.setEdit(false)
        this.changeEditMode(false)
      })
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.changeEditMode(false)
      })
    },
    setFiles4(files) {
      this.$nextTick(() => {
        this.$refs.upload4.setFiles(files)
        this.$refs.upload4.setEdit(false)
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
      this.$refs.upload3.setEdit(mode)
      this.$refs.upload4.setEdit(mode)
    },
    goList() {
      this.$router.push({
        name: `msds-msdsrevchklist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
            macl_cd: this.tmpSchData.macl_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            rev_prog_stt_cd: this.tmpSchData.rev_prog_stt_cd,
            req_emp_nm: this.tmpSchData.req_emp_nm,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    getFormatDate(data) {
      const year = date.getFullYear()
      let month = 1 + date.getMonth()
      month = month >= 10 ? month : '0' + month
      let day = date.getDate()
      day = day >= 10 ? day : '0' + day
      return year + '' + month + '' + day
    },
  },
}
</script>
<style></style>
