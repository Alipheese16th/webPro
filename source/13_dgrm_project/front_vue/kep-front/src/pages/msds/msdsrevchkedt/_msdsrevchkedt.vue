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
      <!-- MSDS 개정 검토 -->
      <div class="sub_title">
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
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.mtrl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 사업장 -->
                <div class="label_tit">{{ $t('LB00000200') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.rev_wkpl_nm }}</div>
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
                <v-textarea ref="chk_cntn" v-model="frmData.msds.rev_chk_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
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
                <v-textarea ref="rejc_cntn" v-model="frmData.msds.rev_chk_rejc_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
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
                      <div class="label_tit essn">{{ $t('LB00000203') }}</div>
                    </v-col>
                    <v-col cols="4">
                      <v-menu v-model="menu4" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                        <template v-slot:activator="{ on, attrs }">
                          <v-text-field
                            ref="revDt"
                            v-model="frmData.msds.rev_af_last_rev_da"
                            prepend-icon="event"
                            readonly
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
                <div class="label_tit essn">{{ $t('LB00000206') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="revVer"
                  v-model="frmData.msds.rev_af_ver"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                ></v-text-field>
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
                  <file-upload ref="upload3" input-id="file3" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 MSDS(국문) -->
                <div class="label_tit essn">{{ $t('LB00000208') }}</div>
              </v-col>
              <v-col ref="uploadOne" cols="5">
                <!-- 입력하세요. -->
                <file-upload ref="upload1" input-id="file" :multiple="false" @onDrop="chkFile()" />
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
                  <file-upload ref="upload4" input-id="file4" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- 개정 후 MSDS(영문) -->
                <div class="label_tit">{{ $t('LB00000210') }}</div>
              </v-col>
              <v-col ref="uploadTwo" cols="5">
                <!-- 입력하세요 -->
                <file-upload ref="upload2" input-id="file2" :multiple="false" @onDrop="chkFile()" />
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
                <div class="label_tit essn">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <v-radio-group v-model="frmData.alert.sgw_cd" row :hide-details="true">
                  <v-radio v-for="n in selCH00000009.cd.length" :key="n" :label="selCH00000009.cd_nm[n - 1]" :value="selCH00000009.cd[n - 1]" :disabled="!modify"></v-radio>
                </v-radio-group>
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
                      <!-- 대상여부, 출력여부 -->
                      <td>
                        <img src="~static/images/P01.png" alt="" :class="{ img_unchk: !frmData.alert.ck1 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck1" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk1"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck1"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck1"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P02.png" alt="" :class="{ img_unchk: !frmData.alert.ck2 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck2" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk2"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck2"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck2"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P03.png" alt="" :class="{ img_unchk: !frmData.alert.ck3 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck3" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk3"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck3"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck3"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P04.png" alt="" :class="{ img_unchk: !frmData.alert.ck4 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck4" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk4"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck4"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck4"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P05.png" alt="" :class="{ img_unchk: !frmData.alert.ck5 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck5" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk5"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck5"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck5"
                          ></v-checkbox>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~static/images/P06.png" alt="" :class="{ img_unchk: !frmData.alert.ck6 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck6" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk6"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck6"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck6"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P07.png" alt="" :class="{ img_unchk: !frmData.alert.ck7 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck7" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk7"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck7"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck7"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P08.png" alt="" :class="{ img_unchk: !frmData.alert.ck8 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck8" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk8"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck8"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck8"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~static/images/P09.png" alt="" :class="{ img_unchk: !frmData.alert.ck9 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox v-model="frmData.alert.ck9" height="30" :hide-details="true" :label="$t('LB00000392')" :disabled="!modify" @click="clkCk9"></v-checkbox>
                          <v-checkbox
                            v-model="frmData.alert.sck9"
                            height="30"
                            :hide-details="true"
                            :label="$t('LB00000391')"
                            :disabled="!modify"
                            @click="clkSck9"
                          ></v-checkbox>
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
                <v-textarea ref="harsCntn" v-model="frmData.alert.hars_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
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
                      <v-textarea ref="prvCntn" v-model="frmData.alert.prv_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 대응 -->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="actCntn" v-model="frmData.alert.act_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 저장 -->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="strCntn" v-model="frmData.alert.str_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 폐기 -->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="scrCntn" v-model="frmData.alert.scr_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 공급업체 -->
                <div class="label_tit essn">{{ $t('LB00000277') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <v-text-field
                  ref="vndr"
                  v-model="frmData.alert.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  readonly
                  :disabled="kyin_yn"
                  :placeholder="$t('LB00000033')"
                  height="30"
                  class="icon_sch"
                  @click="popVendor"
                ></v-text-field>
                <v-checkbox v-model="t_kyin_yn" label="직접입력" height="30" :hide-details="true" class="ml-1" @click="vndrFlip"></v-checkbox>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체명 -->
                <div class="label_tit essn">{{ $t('LB00000130') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrNm"
                  v-model="frmData.alert.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 공급업체 주소 -->
                <div class="label_tit">{{ $t('LB00000186') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrAddr"
                  v-model="frmData.alert.vndr_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체 국가 -->
                <div class="label_tit essn">{{ $t('LB00000185') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="ntcd"
                  v-model="frmData.alert.nat_cd"
                  label="SEL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!kyin_yn"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 공급자 연락처 -->
                <div class="label_tit essn">{{ $t('LB00000184') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrPhon"
                  v-model="frmData.alert.vndr_phon_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="kyin_yn">
              <v-col cols="1">
                <!-- 공급업체 대표자명 -->
                <div class="label_tit">{{ $t('LB00000183') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrRprsNm"
                  v-model="frmData.alert.vndr_rprs_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 사업자 등록번호 -->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrCoregNo"
                  v-model="frmData.alert.vndr_coreg_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  :disabled="!kyin_yn"
                  clearable
                  height="30"
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
      <!-- 검토완료 -->
      <v-btn depressed width="120" color="primary" height="40" @click="chk()">{{ $t('LB00000288') }}</v-btn>
      <!-- 반려 -->
      <v-btn depressed width="120" color="primary" height="40" @click="reject()">{{ $t('LB00000289') }}</v-btn>
      <!-- 저장 -->
      <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popup ref="pop" mkey="chkreq" @close="submitSc" />
    <popup2 ref="pop2" mkey="chkreq" @close="submitTh" />
  </div>
</template>

<script>
import popup from '../../common/vndrmtrlslct'
import popup2 from '../../common/vndrmroslct'
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
    popup,
    popup2,
    FileUpload,
  },
  data() {
    return {
      titleHtml: '',
      svFlg: false,
      tempData: {},
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
          ckFlag: false,
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
          rev_af_ko_atfile_nm: '',
          rev_bf_en_atfile_no: '',
          rev_bf_en_atfile_key: '',
          rev_bf_en_atfile_nm: '',
          rev_af_en_atfile_no: '',
          rev_af_en_atfile_nm: '',
          rev_chk_emp_nm: '',
          rev_chk_dt: '',
          ko_atfile_no: '',
          en_atfile_no: '',
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
          emp_no: '',
        },
        alert: {
          sgw_cd: '',
          sgw_nm: '',
          hars_cntn: '',
          prv_cntn: '',
          act_cntn: '',
          str_cntn: '',
          scr_cntn: '',
          vndr_no: '',
          vndr_nm: '',
          vndr_type_cd: '',
          vndr_phon_no: '',
          vndr_addr: '',
          nat_cd: '',
          nat_nm: '',
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
  async beforePageLeave(tab, type) {
    if (JSON.stringify(this.tempData) !== JSON.stringify(this.frmData) && !this.svFlg) {
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
  },
  async created() {
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
    const auth = await this.$axios.$get(`/api/v1/ch/msds/auth-revchk`, { params: { msds_rev_no: this.$route.params.msdsrevchkedt, emp_no: user.emp_no } })
    if (auth === 'READ') {
      this.$toast.show('권한이 없습니다.', { className: 'toast_error' }) // 권한이 없습니다.
    } else if (this.$route.params.msdsrevchkedt !== null && this.$route.params.msdsrevchkedt !== undefined) {
      // 파라미터 조회 - 파라미터가 존재할 경우(작성 중)
      this.msdsRevNo = this.$route.params.msdsrevchkedt
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
    chkFile() {
      this.frmData.msds.ckFlag = true
    },
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
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_af_en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_af_en_atfile_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_bf_ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_bf_ko_atfile_no}`).then((data) => {
            console.log('files3 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles3(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.rev_bf_en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_bf_en_atfile_no}`).then((data) => {
            console.log('files4 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles4(data)
            }
          })
        }
        // 객체 복사
        this.tempData = JSON.parse(JSON.stringify(this.frmData))
        this.chkData = JSON.parse(JSON.stringify(this.frmData))
        this.frmData = this.chkData

        // 둘다 값이 와이이면...'※ MSDS 제출 대상 자재'
        if (this.frmData.msds.imp_yn === 'Y' && this.frmData.msds.sbms_tgt_yn === 'Y') {
          this.titleHtml = this.$t('LB00000620')
        }
      })
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
    setFiles3(files) {
      this.$nextTick(() => {
        this.$refs.upload3.setFiles(files)
        this.$refs.upload3.setEdit(false)
        this.changeEditMode(true)
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
    setFiles4(files) {
      this.$nextTick(() => {
        this.$refs.upload4.setFiles(files)
        this.$refs.upload4.setEdit(false)
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
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
      this.$refs.upload3.setEdit(false)
      this.$refs.upload4.setEdit(false)
    },
    async popMtrl() {
      const detail = true
      // 'Y'는 규제 검토여부 true, 'N'은 규제 검토여부 false, 그 외는 ALL
      const chkYnAll = 'Y'
      if (!this.isEmpty(this.frmData.msds.msds_rev_no)) {
        const res = await this.$confirm(this.$t('MS00000095')) // 자재 번호를 다시 선택하시면 저장되어있던 내용이 사라집니다. 선택하시겠습니까?
        if (!res) {
          return
        }
      }
      this.$refs.pop.open(detail, chkYnAll)
    },
    popVendor() {
      let mode = ''
      if (this.isEmpty(this.frmData.msds.mtrl_no)) {
        this.$toast.show(this.$t('MS00000096'), { className: 'toast_error' }) // 자재를 먼저 선택해주세요.
        return
      }
      // 1 == mtr, 2 == mro
      if (this.frmData.msds.mtrl_clsf_cd === '1') {
        mode = '1'
        this.$refs.pop.open(mode)
      } else if (this.frmData.msds.mtrl_clsf_cd === '2') {
        mode = '2'
        this.$refs.pop2.open(mode)
      }
    },
    submitSc(rows) {
      if (rows === undefined) return
      this.$set(this.frmData.alert, 'vndr_no', rows.vndr_no)
      this.frmData.alert.vndr_nm = rows.vndr_nm
      this.frmData.alert.vndr_phon_no = rows.vndr_phon_no
      this.frmData.alert.vndr_addr = rows.vndr_addr
      this.frmData.alert.nat_cd = rows.nat_cd
      this.frmData.alert.vndr_coreg_no = rows.vndr_coreg_no
      this.frmData.alert.vndr_rprs_nm = rows.vndr_rprs_nm
      this.frmData.alert.vndr_email = rows.vndr_email
      this.$forceUpdate()
    },
    submitTh(rows) {
      if (rows === undefined) return
      this.$set(this.frmData.alert, 'vndr_no', rows.vndr_no)
      this.frmData.alert.vndr_nm = rows.vndr_nm
      this.frmData.alert.vndr_phon_no = rows.vndr_phon_no
      this.frmData.alert.vndr_addr = rows.vndr_addr
      this.frmData.alert.nat_cd = rows.nat_cd
      this.frmData.alert.vndr_coreg_no = rows.vndr_coreg_no
      this.frmData.alert.vndr_rprs_nm = rows.vndr_rprs_nm
      this.frmData.alert.vndr_email = rows.vndr_email
      this.$forceUpdate()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
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
    async save() {
      // Validation 체크
      if (!this.validation()) {
        return
      }
      // 파일 결과 함수
      let fileResult = false
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.msds.rev_af_ko_atfile_no = data
          fileResult = true
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_af_ko_atfile_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        } else {
          this.$toast.show(this.$t('LB00000308'), { className: 'toast_error' }) // 개정 후 MSDS(국문) 파일을 선택해주세요.
          this.$refs.uploadOne.focus()
          fileResult = false
          // this.frmData.msds.rev_af_ko_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.msds.rev_af_en_atfile_no = data2
          fileResult = true
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.rev_af_en_atfile_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        } else {
          this.frmData.msds.rev_af_en_atfile_no = null
          this.tempData.msds.rev_af_en_atfile_no = null
          // this.$toast.show(this.$t('LB00000309'), { className: 'toast_error' }) // 개정 후 MSDS(영문) 파일을 선택해주세요.
          // this.$refs.uploadTwo.focus()
          fileResult = true
          // this.frmData.msds.rev_af_en_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
        // 변경 사항 체크
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      // 반려 중 저장인지 작성중 저장인지 체크 필요함
      const user = this.$store.getters['auth/getUser']
      // 저장 시에 유저 권한 체크, 진행 상태 체크, 중복 여부 체크 필요
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      if (this.t_kyin_yn === true) {
        this.frmData.alert.vndr_kyin_yn = 'Y'
      } else {
        this.frmData.alert.vndr_kyin_yn = 'N'
      }

      this.frmData.pic = []
      if (this.frmData.alert.ck1 === true) {
        if (this.frmData.alert.sck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck2 === true) {
        if (this.frmData.alert.sck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck3 === true) {
        if (this.frmData.alert.sck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck4 === true) {
        if (this.frmData.alert.sck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck5 === true) {
        if (this.frmData.alert.sck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck6 === true) {
        if (this.frmData.alert.sck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck7 === true) {
        if (this.frmData.alert.sck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck8 === true) {
        if (this.frmData.alert.sck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck9 === true) {
        if (this.frmData.alert.sck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      this.frmData.msds.emp_no = user.emp_no
      this.frmData.alert.vndr_type_cd = this.frmData.msds.mtrl_clsf_cd
      // 파일 수정 플래그 초기화
      this.frmData.msds.ckFlag = false
      // update
      await this.$axios.put('/api/v1/ch/msds/msds-chk', this.frmData).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          // 가져온 키 값으로 재조회
          this.selectMsdsList(res.data)
        } else if (res.data === 'READ') {
          this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validation() {
      let count = 0
      if (this.frmData.alert.sck1) {
        count++
      }
      if (this.frmData.alert.sck2) {
        count++
      }
      if (this.frmData.alert.sck3) {
        count++
      }
      if (this.frmData.alert.sck4) {
        count++
      }
      if (this.frmData.alert.sck5) {
        count++
      }
      if (this.frmData.alert.sck6) {
        count++
      }
      if (this.frmData.alert.sck7) {
        count++
      }
      if (this.frmData.alert.sck8) {
        count++
      }
      if (this.frmData.alert.sck9) {
        count++
      }
      if (this.isEmpty(this.frmData.msds.mtrl_no)) {
        this.$toast.show(this.$t('MS00000099'), { className: 'toast_error' }) // 자재번호를 선택해주세요.
        this.$refs.mtrlNo.focus()
        return false
      } else if (this.isEmpty(this.frmData.msds.rev_wkpl_id)) {
        this.$toast.show(this.$t('MS00000100'), { className: 'toast_error' }) // 개정 사업장을 선택해주세요.
        this.$refs.revwkplId.focus()
        return false
      } else if (this.isEmpty(this.frmData.msds.rev_af_last_rev_da)) {
        this.$toast.show(this.$t('MS00000101'), { className: 'toast_error' }) // 개정 후 개정일을 입력해주세요.
        this.$refs.revDt.focus()
        return false
      } else if (this.isEmpty(this.frmData.msds.rev_af_ver)) {
        this.$toast.show(this.$t('MS00000102'), { className: 'toast_error' }) // 개정 후 버전을 입력해주세요.
        this.$refs.revVer.focus()
        return false
      } else if (this.frmData.msds.rev_af_ver.length > 20) {
        this.$toast.show(this.$t('MS00000103'), { className: 'toast_error' }) // 개정 후 버전은 20자 이하로 작성해주세요.
        this.$refs.revVer.focus()
        return false
      } else if (count > 4) {
        this.$toast.show(this.$t('MS00000214'), { className: 'toast_error' }) // 출력 문자는 5개 이상 선택할 수 없습니다.
        return false
      } else if (!this.isEmpty(this.frmData.alert.hars_cntn) && this.frmData.alert.hars_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000157'), { className: 'toast_error' }) // 유해위험문구는 500자 이하로 작성해주세요.
        this.$refs.harsCntn.focus()
        return false
      } else if (!this.isEmpty(this.frmData.alert.prv_cntn) && this.frmData.alert.prv_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000158'), { className: 'toast_error' }) // 예방 문구는 500자 이하로 작성해주세요.
        this.$refs.prvCntn.focus()
        return false
      } else if (!this.isEmpty(this.frmData.alert.act_cntn) && this.frmData.alert.act_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000159'), { className: 'toast_error' }) // 대응 문구는 500자 이하로 작성해주세요.
        this.$refs.actCntn.focus()
        return false
      } else if (!this.isEmpty(this.frmData.alert.str_cntn) && this.frmData.alert.str_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000160'), { className: 'toast_error' }) // 저장 문구는 500자 이하로 작성해주세요.
        this.$refs.strCntn.focus()
        return false
      } else if (!this.isEmpty(this.frmData.alert.scr_cntn) && this.frmData.alert.scr_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000161'), { className: 'toast_error' }) // 폐기 문구는 500자 이하로 작성해주세요.
        this.$refs.scrCntn.focus()
        return false
      } else if (!this.t_kyin_yn && this.isEmpty(this.frmData.alert.vndr_nm)) {
        this.$toast.show(this.$t('MS00000104'), { className: 'toast_error' }) // 공급업체를 선택해주세요.
        this.$refs.vndr.focus()
        return false
      } else if (this.t_kyin_yn && this.isEmpty(this.frmData.alert.vndr_nm)) {
        this.$toast.show(this.$t('MS00000105'), { className: 'toast_error' }) // 공급업체명을 입력해주세요.
        this.$refs.vndrNm.focus()
        return false
      } else if (this.t_kyin_yn && this.frmData.alert.vndr_nm.length > 100) {
        this.$toast.show(this.$t('MS00000116'), { className: 'toast_error' }) // 공급업체명은 100자 이하로 작성해주세요.
        this.$refs.vndrNm.focus()
        return false
      } else if (this.t_kyin_yn && this.isEmpty(this.frmData.alert.vndr_phon_no)) {
        this.$toast.show(this.$t('MS00000106'), { className: 'toast_error' }) // 공급업체 연락처를 입력해주세요.
        this.$refs.vndrPhon.focus()
        return false
      } else if (this.t_kyin_yn && this.frmData.alert.vndr_phon_no.length > 100) {
        this.$toast.show(this.$t('MS00000117'), { className: 'toast_error' }) // 공급업체 연락처는 100자 이하로 작성해주세요.
        this.$refs.vndrPhon.focus()
        return false
      } else if (this.t_kyin_yn && this.isEmpty(this.frmData.alert.nat_cd)) {
        this.$toast.show(this.$t('MS00000107'), { className: 'toast_error' }) // 공급업체 국가를 입력해주세요.
        this.$refs.ntcd.focus()
        return false
      } else if (this.t_kyin_yn && !this.isEmpty(this.frmData.alert.vndr_addr) && this.frmData.alert.vndr_addr.length > 200) {
        this.$toast.show(this.$t('MS00000118'), { className: 'toast_error' }) // 공급업체 주소는 200자 이하로 작성해주세요.
        this.$refs.vndrAddr.focus()
        return false
      } else if (this.t_kyin_yn && !this.isEmpty(this.frmData.alert.vndr_rprs_nm) && this.frmData.alert.vndr_rprs_nm.length > 100) {
        this.$toast.show(this.$t('MS00000119'), { className: 'toast_error' }) // 공급업체 대표자명은 100자 이하로 작성해주세요.
        this.$refs.vndrRprsNm.focus()
        return false
      } else if (this.t_kyin_yn && !this.isEmpty(this.frmData.alert.vndr_coreg_no) && this.frmData.alert.vndr_coreg_no.length > 100) {
        this.$toast.show(this.$t('MS00000120'), { className: 'toast_error' }) // 사업자 등록번호는 100자 이하로 작성해주세요.
        this.$refs.vndrCoregNo.focus()
        return false
      }
      return true
    },
    async vndrFlip() {
      if (this.t_kyin_yn && !this.isEmpty(this.frmData.alert.vndr_nm)) {
        const res = await this.$confirm(this.$t('MS00000108')) // 공급자를 직접 입력하시면 설정되어있던 내용이 사라집니다. 선택하시겠습니까?
        if (!res) {
          this.t_kyin_yn = false
          this.kyin_yn = false
          return
        }

        this.frmData.alert.vndr_nm = ''
        this.frmData.alert.vndr_phon_no = ''
        this.frmData.alert.vndr_addr = ''
        this.frmData.alert.nat_cd = ''
        this.frmData.alert.vndr_coreg_no = ''
        this.$set(this.frmData.alert, 'vndr_rprs_nm', '')
        this.$forceUpdate()
        this.kyin_yn = true
      } else if (!this.t_kyin_yn && !this.isEmpty(this.frmData.alert.vndr_nm)) {
        const res = await this.$confirm(this.$t('MS00000108')) // 공급자를 직접 입력하시면 설정되어있던 내용이 사라집니다. 선택하시겠습니까?
        if (!res) {
          this.t_kyin_yn = true
          this.kyin_yn = true
          return
        }

        this.frmData.alert.vndr_nm = ''
        this.frmData.alert.vndr_phon_no = ''
        this.frmData.alert.vndr_addr = ''
        this.frmData.alert.nat_cd = ''
        this.frmData.alert.vndr_coreg_no = ''
        this.$set(this.frmData.alert, 'vndr_rprs_nm', '')
        this.$forceUpdate()
        this.kyin_yn = false
      } else if (this.t_kyin_yn) {
        this.kyin_yn = true
      } else {
        this.kyin_yn = false
      }
    },
    async reject() {
      // Validation 체크
      if (!this.validation()) {
        return
      }
      // 파일 결과 함수
      let fileResult = false
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.msds.rev_af_ko_atfile_no = data
          fileResult = true
        } else {
          this.$toast.show(this.$t('LB00000308'), { className: 'toast_error' }) // 개정 후 MSDS(국문) 파일을 선택해주세요.
          this.$refs.uploadOne.focus()
          fileResult = false
          // this.frmData.msds.rev_af_ko_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.msds.rev_af_en_atfile_no = data2
          fileResult = true
        } else {
          // this.$toast.show(this.$t('LB00000309'), { className: 'toast_error' }) // 개정 후 MSDS(영문) 파일을 선택해주세요.
          // this.$refs.uploadTwo.focus()
          fileResult = true
          // this.frmData.msds.rev_af_en_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      if (this.isEmpty(this.frmData.msds.rev_chk_rejc_cntn)) {
        this.$toast.show(this.$t('MS00000109'), { className: 'toast_error' }) // 반려내용을 작성해주세요.
        this.$refs.rejc_cntn.focus()
        return
      }
      // 반려 중 저장인지 작성중 저장인지 체크 필요함
      const user = this.$store.getters['auth/getUser']
      // 저장 시에 유저 권한 체크, 진행 상태 체크, 중복 여부 체크 필요
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000110')) // 반려하시겠습니까?
      if (!res) {
        return
      }
      if (this.t_kyin_yn === true) {
        this.frmData.alert.vndr_kyin_yn = 'Y'
      } else {
        this.frmData.alert.vndr_kyin_yn = 'N'
      }

      this.frmData.pic = []
      if (this.frmData.alert.ck1 === true) {
        if (this.frmData.alert.sck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck2 === true) {
        if (this.frmData.alert.sck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck3 === true) {
        if (this.frmData.alert.sck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck4 === true) {
        if (this.frmData.alert.sck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck5 === true) {
        if (this.frmData.alert.sck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck6 === true) {
        if (this.frmData.alert.sck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck7 === true) {
        if (this.frmData.alert.sck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck8 === true) {
        if (this.frmData.alert.sck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck9 === true) {
        if (this.frmData.alert.sck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      this.frmData.msds.emp_no = user.emp_no
      this.frmData.alert.vndr_type_cd = this.frmData.msds.mtrl_clsf_cd
      this.frmData.msds.rev_chk_rejc_emp_nm = user.emp_nm
      this.frmData.msds.rev_chk_rejc_dt = date.toISOString().substr(0, 4) + date.toISOString().substr(4, 2) + date.toISOString().substr(6, 2)
      // 파일 수정 플래그 초기화
      this.frmData.msds.ckFlag = false
      // update
      await this.$axios.put('/api/v1/ch/msds/msds-chk', this.frmData).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          this.chgStt()
        } else if (res.data === 'READ') {
          this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async chgStt() {
      this.svFlg = true
      await this.$axios.put('/api/v1/ch/msds/msds-rjt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000111'))
          this.goList()
        } else if (res.data === 'READ') {
          this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async chk() {
      // Validation 체크
      if (!this.validation()) {
        return
      }
      // 파일 결과 함수
      let fileResult = false
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.msds.rev_af_ko_atfile_no = data
          fileResult = true
        } else {
          this.$toast.show(this.$t('LB00000308'), { className: 'toast_error' }) // 개정 후 MSDS(국문) 파일을 선택해주세요.
          this.$refs.uploadOne.focus()
          fileResult = false
          // this.frmData.msds.rev_af_ko_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.msds.rev_af_en_atfile_no = data2
          fileResult = true
        } else {
          this.frmData.msds.rev_af_en_atfile_no = null
          // this.$toast.show(this.$t('LB00000309'), { className: 'toast_error' }) // 개정 후 MSDS(영문) 파일을 선택해주세요.
          // this.$refs.uploadTwo.focus()
          fileResult = true
          // this.frmData.msds.rev_af_en_atfile_no = 641
          // fileResult = true
        }
      })
      if (!fileResult) {
        return
      }
      if (this.isEmpty(this.frmData.msds.rev_chk_cntn)) {
        this.$toast.show(this.$t('MS00000112'), { className: 'toast_error' }) // 검토내용을 작성해주세요.
        this.$refs.chk_cntn.focus()
        return
      }
      // 검토 중 저장인지 작성중 저장인지 체크 필요함
      const user = this.$store.getters['auth/getUser']
      // 저장 시에 유저 권한 체크, 진행 상태 체크, 중복 여부 체크 필요
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000113')) // 검토완료 하시겠습니까?
      if (!res) {
        return
      }
      if (this.t_kyin_yn === true) {
        this.frmData.alert.vndr_kyin_yn = 'Y'
      } else {
        this.frmData.alert.vndr_kyin_yn = 'N'
      }

      this.frmData.pic = []
      if (this.frmData.alert.ck1 === true) {
        if (this.frmData.alert.sck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck2 === true) {
        if (this.frmData.alert.sck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck3 === true) {
        if (this.frmData.alert.sck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck4 === true) {
        if (this.frmData.alert.sck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck5 === true) {
        if (this.frmData.alert.sck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck6 === true) {
        if (this.frmData.alert.sck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck7 === true) {
        if (this.frmData.alert.sck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck8 === true) {
        if (this.frmData.alert.sck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.frmData.alert.ck9 === true) {
        if (this.frmData.alert.sck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      this.frmData.msds.emp_no = user.emp_no
      this.frmData.alert.vndr_type_cd = this.frmData.msds.mtrl_clsf_cd
      this.frmData.msds.rev_chk_emp_nm = user.emp_nm
      this.frmData.msds.rev_chk_dt = date.toISOString().substr(0, 4) + date.toISOString().substr(4, 2) + date.toISOString().substr(6, 2)
      // 파일 수정 플래그 초기화
      this.frmData.msds.ckFlag = false
      // update
      await this.$axios.put('/api/v1/ch/msds/msds-chk', this.frmData).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          this.chkFinish()
        } else if (res.data === 'READ') {
          this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async chkFinish() {
      this.svFlg = true
      await this.$axios.put('/api/v1/ch/msds/chk-end', this.frmData).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          this.$toast.show(this.$t('MS00000114')) // 검토 완료하였습니다.
          this.goList()
        } else if (res.data === 'READ') {
          this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    clkCk1() {
      if (this.frmData.alert.ck1) {
        if (!this.frmData.alert.sck1) {
          this.frmData.alert.sck1 = true
        }
      } else if (this.frmData.alert.sck1) {
        this.frmData.alert.sck1 = false
      }
    },
    clkSck1() {
      if (this.frmData.alert.sck1) {
        if (!this.frmData.alert.ck1) {
          this.frmData.alert.ck1 = true
        }
      }
    },
    clkCk2() {
      if (this.frmData.alert.ck2) {
        if (!this.frmData.alert.sck2) {
          this.frmData.alert.sck2 = true
        }
      } else if (this.frmData.alert.sck2) {
        this.frmData.alert.sck2 = false
      }
    },
    clkSck2() {
      if (this.frmData.alert.sck2) {
        if (!this.frmData.alert.ck2) {
          this.frmData.alert.ck2 = true
        }
      }
    },
    clkCk3() {
      if (this.frmData.alert.ck3) {
        if (!this.frmData.alert.sck3) {
          this.frmData.alert.sck3 = true
        }
      } else if (this.frmData.alert.sck3) {
        this.frmData.alert.sck3 = false
      }
    },
    clkSck3() {
      if (this.frmData.alert.sck3) {
        if (!this.frmData.alert.ck3) {
          this.frmData.alert.ck3 = true
        }
      }
    },
    clkCk4() {
      if (this.frmData.alert.ck4) {
        if (!this.frmData.alert.sck4) {
          this.frmData.alert.sck4 = true
        }
      } else if (this.frmData.alert.sck4) {
        this.frmData.alert.sck4 = false
      }
    },
    clkSck4() {
      if (this.frmData.alert.sck4) {
        if (!this.frmData.alert.ck4) {
          this.frmData.alert.ck4 = true
        }
      }
    },
    clkCk5() {
      if (this.frmData.alert.ck5) {
        if (!this.frmData.alert.sck5) {
          this.frmData.alert.sck5 = true
        }
      } else if (this.frmData.alert.sck5) {
        this.frmData.alert.sck5 = false
      }
    },
    clkSck5() {
      if (this.frmData.alert.sck5) {
        if (!this.frmData.alert.ck5) {
          this.frmData.alert.ck5 = true
        }
      }
    },
    clkCk6() {
      if (this.frmData.alert.ck6) {
        if (!this.frmData.alert.sck6) {
          this.frmData.alert.sck6 = true
        }
      } else if (this.frmData.alert.sck6) {
        this.frmData.alert.sck6 = false
      }
    },
    clkSck6() {
      if (this.frmData.alert.sck6) {
        if (!this.frmData.alert.ck6) {
          this.frmData.alert.ck6 = true
        }
      }
    },
    clkCk7() {
      if (this.frmData.alert.ck7) {
        if (!this.frmData.alert.sck7) {
          this.frmData.alert.sck7 = true
        }
      } else if (this.frmData.alert.sck7) {
        this.frmData.alert.sck7 = false
      }
    },
    clkSck7() {
      if (this.frmData.alert.sck7) {
        if (!this.frmData.alert.ck7) {
          this.frmData.alert.ck7 = true
        }
      }
    },
    clkCk8() {
      if (this.frmData.alert.ck8) {
        if (!this.frmData.alert.sck8) {
          this.frmData.alert.sck8 = true
        }
      } else if (this.frmData.alert.sck8) {
        this.frmData.alert.sck8 = false
      }
    },
    clkSck8() {
      if (this.frmData.alert.sck8) {
        if (!this.frmData.alert.ck8) {
          this.frmData.alert.ck8 = true
        }
      }
    },
    clkCk9() {
      if (this.frmData.alert.ck9) {
        if (!this.frmData.alert.sck9) {
          this.frmData.alert.sck9 = true
        }
      } else if (this.frmData.alert.sck9) {
        this.frmData.alert.sck9 = false
      }
    },
    clkSck9() {
      if (this.frmData.alert.sck9) {
        if (!this.frmData.alert.ck9) {
          this.frmData.alert.ck9 = true
        }
      }
    },
  },
}
</script>
<style></style>
