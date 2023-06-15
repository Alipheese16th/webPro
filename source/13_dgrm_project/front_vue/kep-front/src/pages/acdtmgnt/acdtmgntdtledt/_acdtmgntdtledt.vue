<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 사고 보고 및 조사 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00001045') }}</span>
    </div>
    <div class="work_title">
      <!-- 사고관리 -->
      <h2>
        <!-- 사고 조회 및 등록 -->
        <span>{{ $t('LB00000801') }}</span>
        &nbsp; &nbsp;
        <!-- *사고 보고 및 조사결과는 사고 발생 24시간 이내 작성 바랍니다. -->
        <span style="color: red">{{ $t('LB00001335') }}</span>
      </h2>
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
                <!-- 사업장 -->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!-- 사고구분 -->
                <div class="label_tit essn">{{ $t('LB00000806') }}</div>
                <!-- 사고구분툴팁 -->
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                  </template>
                  <p>
                    <!-- 중대재해 -->
                    {{ $t('LB00000818') }}<br />
                    <!-- 사망자 1명 발생 -->
                    <span>· {{ $t('LB00000815') }}</span
                    ><br />
                    <!-- 3개월 이상 부상자 동시 2명 발생 -->
                    <span>· {{ $t('LB00000816') }}</span
                    ><br />
                    <!-- 부상자 동시 10명 이상 발생 -->
                    <span>· {{ $t('LB00000817') }}</span>
                  </p>
                  <p>
                    <!-- 고위험사고 -->
                    {{ $t('LB00000820') }}<br />
                    <!-- 중상해 이상의 사고로 인한 부상자 1명 이상 발생 -->
                    <span
                      >· *{{ $t('LB00000821') }}<br />
                      <!-- (모든부위 해당) 골절, 절단, 2도 이상 화상 -->
                      &nbsp;&nbsp;*{{ $t('LB00000822') }}<br />
                      <!-- (재해자 부상정도 상관없이) 감전, 폭발, 질식사고 -->
                      &nbsp;&nbsp;{{ $t('LB00000823') }}<br />
                      <!-- 그 밖의 안전사고로 전치 12주 이상 상해(업무상 교통사고, 출퇴근사고 포함) -->
                      &nbsp;&nbsp;{{ $t('LB00000824') }}</span
                    ><br />
                  </p>
                  <p>
                    <!-- 병원치료 -->
                    {{ $t('LB00000828') }}<br />
                    <!-- 중상해 사고 외의 건으로 부상으로 인해 병원치료가 필요한 인명피해 발생 -->
                    <span>· {{ $t('LB00000825') }}</span>
                  </p>
                  <p>
                    <!-- 경미사고 -->
                    {{ $t('LB00000829') }}<br />
                    <!-- 간단한 약이나 의료 조치가 필요한 경미한 부상 발생(사업장 내부 치료) -->
                    <span>· {{ $t('LB00000826') }}</span>
                  </p>
                  <p>
                    <!-- 아차사고 -->
                    {{ $t('LB00000830') }}<br />
                    <!-- 정상적인 상황을 이탈하는 사고가 발생하였으나, 인적 상해가 수반되지 않은 사고 -->
                    <span>· {{ $t('LB00000827') }}</span>
                  </p>
                </v-tooltip>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 사고구분 분류 콤보박스 -->
                <v-select
                  ref="acdt_cl_cd"
                  v-model="frmData.acdt_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000024"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                  class="mr-1"
                ></v-select>
                <!-- 사고구분 상세 콤보박스 -->
                <v-select
                  ref="acdt_dtl_cl_cd"
                  v-model="frmData.acdt_dtl_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000025"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                ></v-select>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!-- 사고유형 -->
                <div class="label_tit essn">{{ $t('LB00001279') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 사고유형 콤보박스 -->
                <v-select
                  ref="acdt_tp_cd"
                  v-model="frmData.acdt_tp_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000071"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고기인물 -->
                <div class="label_tit essn">{{ $t('LB00001339') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 사고기인물코드(시스템사고원인대상코드) -->
                <v-select
                  ref="acdt_caus_tgt_cd"
                  v-model="frmData.acdt_caus_tgt_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000072"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="frmData.acdt_prst_cd === '4'"
                  dense
                  height="30"
                ></v-select>
                &nbsp;
                <!-- 사고원인대상내용 -->
                <v-text-field
                  ref="acdt_caus_tgt_txt"
                  v-model="frmData.acdt_caus_tgt_txt"
                  placeholder="사고기인물 상세 작성"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="frmData.acdt_prst_cd === '4'"
                  dense
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사고위치-->
                <div class="label_tit essn">{{ $t('LB00000810') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 사고위치 선택 -->
                <v-text-field
                  ref="acdt_wkpl_loc_nm"
                  v-model="frmData.acdt_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                  readonly
                  class="icon_sch"
                  @click="popOpen2"
                ></v-text-field>
                &nbsp;
                <!-- 사고위치 상세입력 -->
                <v-text-field
                  ref="acdt_dtl_loc_nm"
                  v-model="frmData.acdt_dtl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사고발생 부서-->
                <div class="label_tit essn">{{ $t('LB00000831') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="kor_acdt_occr_org_nm"
                  v-model="frmData.kor_acdt_occr_org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  :disabled="frmData.acdt_prst_cd === '4'"
                  readonly
                  class="icon_sch"
                  @click="popOpen3"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사고일시-->
                <div class="label_tit essn">{{ $t('LB00000832') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="acdt_occr_dt"
                      v-model="frmData.acdt_occr_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      :disabled="frmData.acdt_prst_cd === '4'"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                    <!--사고일시 요일-->
                    <v-text-field
                      v-model="dateAccidentText"
                      class="centered-input"
                      outlined
                      single-line
                      dense
                      placeholder="요일"
                      readonly
                      :hide-details="true"
                      :disabled="frmData.acdt_prst_cd === '4'"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.acdt_occr_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                &nbsp;
                <!-- 사고일시 시간 -->
                <v-text-field
                  ref="acdt_occr_hour"
                  v-model="frmData.acdt_occr_hour"
                  class="centered-input"
                  outlined
                  single-line
                  :maxlength="2"
                  oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00001341')"
                  height="30"
                  background-color="white"
                  :disabled="frmData.acdt_prst_cd === '4'"
                ></v-text-field>
                &nbsp;
                <span style="margin-top: 5px">:</span>
                &nbsp;
                <!-- 사고일시 분 -->
                <v-text-field
                  ref="acdt_occr_minute"
                  v-model="frmData.acdt_occr_minute"
                  class="centered-input"
                  outlined
                  single-line
                  :maxlength="2"
                  oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00001342')"
                  height="30"
                  background-color="white"
                  :disabled="frmData.acdt_prst_cd === '4'"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--업무상 사고여부-->
                <div class="label_tit essn">{{ $t('LB00000833') }}</div>
              </v-col>
              <v-col cols="3">
                <v-radio-group v-model="frmData.bsns_rel_acdt_yn" row :hide-details="true" dense>
                  <v-radio label="Y" value="Y"></v-radio>
                  <v-radio label="N" value="N"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--산업재해조사표제출대상-->
                <div class="label_tit essn">{{ $t('LB00001340') }}</div>
              </v-col>
              <v-col cols="3">
                <v-radio-group v-model="frmData.inac_ivst_sbms_tgt_yn" row :hide-details="true" dense :disabled="frmData.acdt_prst_cd === '4'">
                  <v-radio label="Y" value="Y"></v-radio>
                  <v-radio label="N" value="N"></v-radio>
                </v-radio-group>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!--산재여부-->
                <div class="label_tit essn">{{ $t('LB00000807') }}</div>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                  </template>
                  <span>*산재로 해당/변경되는 경우 반드시 수정 바랍니다.</span>
                </v-tooltip>
              </v-col>
              <v-col cols="3">
                <v-radio-group v-model="frmData.inac_yn" row :hide-details="true" dense>
                  <v-radio v-for="n in codes.SH00000070" :key="n.cd_nm" :label="`${n.cd_nm}`" :value="n.cd"></v-radio>
                </v-radio-group>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고발생  -->
                <div class="label_tit essn">[사고발생]</div>
                <div style="display: flex">
                  <v-text-field
                    ref="occrhour"
                    v-model="frmData.occrhour"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001341')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                  &nbsp;
                  <span style="margin-top: 5px">:</span>
                  &nbsp;
                  <v-text-field
                    ref="occrminute"
                    v-model="frmData.occrminute"
                    class="centered-input"
                    outlined
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001342')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_occr_txt"
                  v-model="frmData.acdt_occr_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="사고 발생 시점 상황을 6하 원칙에 의거하여 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고접수  -->
                <div class="label_tit essn">[사고접수]</div>
                <div style="display: flex">
                  <v-text-field
                    ref="accphour"
                    v-model="frmData.accphour"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001341')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                  &nbsp;
                  <span style="margin-top: 5px">:</span>
                  &nbsp;
                  <v-text-field
                    ref="accpminute"
                    v-model="frmData.accpminute"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001342')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_accp_txt"
                  v-model="frmData.acdt_accp_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="사고 접수(신고) 주관부서 및 사고대응부서, 사고 접수 당시 담당자 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 초기대응  -->
                <div class="label_tit essn">[초기대응]</div>
                <div style="display: flex">
                  <v-text-field
                    ref="bgng_rposhour"
                    v-model="frmData.bgng_rposhour"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001341')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                  &nbsp;
                  <span style="margin-top: 5px">:</span>
                  &nbsp;
                  <v-text-field
                    ref="bgng_rposminute"
                    v-model="frmData.bgng_rposminute"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001342')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_bgng_rpos_txt"
                  v-model="frmData.acdt_bgng_rpos_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="현장통제, 응급처치 및 구조요청 등 초기 대응(조치) 내용을 시간순으로 6하 원칙에 의거 작성 바랍니다. (설비정지, 밸브차단, 응급처치 등 조치 내용 작성) 
  2차 사고 예방, 현장보존, 손실확인 등 초기 대응(조치) 내용을 시간순으로 6하 원칙에 의거 작성 바랍니다. (현장통제, 현장보존, 피해상황 파악 등 조치 내용 작성)"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사후조치  -->
                <div class="label_tit essn">[사후조치]</div>
                <div style="display: flex">
                  <v-text-field
                    ref="af_actnhour"
                    v-model="frmData.af_actnhour"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001341')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                  &nbsp;
                  <span style="margin-top: 5px">:</span>
                  &nbsp;
                  <v-text-field
                    ref="af_actnminute"
                    v-model="frmData.af_actnminute"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001342')"
                    height="30"
                    background-color="white"
                    :disabled="frmData.acdt_prst_cd === '4'"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_af_actn_txt"
                  v-model="frmData.acdt_af_actn_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="재해자, 피해설비 등에 대한 조치 내용을 시간순으로 6하 원칙에 의거 구체적으로 작성 (재해자 병원이송, 치료경과, 복귀여부, 피해설비 재가동, 수선계획 등 상세 작성) "
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사고발생원인-->
                <div class="label_tit essn">{{ $t('LB00000836') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_occr_caus_txt"
                  v-model="frmData.acdt_occr_caus_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="사고 발생 원인을 구체적으로 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--재발방지대책-->
                <div class="label_tit essn">{{ $t('LB00000837') }}</div>
              </v-col>
              <v-col cols="11">
                · 사고발생부서
                <!-- 재발방지대책(사고발생부서) -->
                <v-textarea
                  ref="acdt_dept_prvt_meas_txt"
                  v-model="frmData.acdt_dept_prvt_meas_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="사고 재발 방지 및 근본 원인 개선을 위한 재발방지대책 작성 바랍니다."
                ></v-textarea>
                <br />
                · 안전관리부서
                <!-- 재발방지대책(안전관리부서) -->
                <v-textarea
                  ref="sft_dept_prvt_meas_txt"
                  v-model="frmData.sft_dept_prvt_meas_txt"
                  rows="3"
                  auto-grow
                  outlined
                  :hide-details="true"
                  dense
                  :disabled="frmData.acdt_prst_cd === '4'"
                  placeholder="사고 재발 방지 및 근본 원인 개선을 위한 재발방지대책 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <v-container>
      <v-row>
        <v-col cols="4" style="padding: 0">
          <div class="realgrid_container">
            <div class="grid_header">
              <div class="sub_title" style="width: 100%">
                <!-- 작업내용 및 과정 -->
                <h3>{{ $t('LB00000838') }}</h3>
              </div>
            </div>
            <div id="realgrid" style="width: 100%; height: 400px"></div>
          </div>
        </v-col>
        <v-col cols="4" style="padding: 0">
          <div class="container_detail">
            <div class="grid_header">
              <div class="sub_title" style="width: 100%">
                <!-- 불안전한 행동(시스템적 원인) -->
                <h3>{{ $t('LB00000839') }}</h3>
              </div>
            </div>
            <div id="realgrid2" style="width: 100%; height: 400px"></div>
          </div>
        </v-col>
        <v-col cols="4" style="padding: 0">
          <div class="container_detail">
            <div class="grid_header">
              <div class="sub_title" style="width: 100%">
                <!-- 불안전한 상태(설비적 원인) -->
                <h3>{{ $t('LB00000840') }}</h3>
              </div>
            </div>
            <div id="realgrid3" style="width: 100%; height: 400px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사고자 인적사항-->
        <h3>{{ $t('LB00000841') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn v-if="frmData.acdt_prst_cd !== '4'" color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn v-if="frmData.acdt_prst_cd !== '4'" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid4" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사고조사 결과-->
        <h3>{{ $t('LB00000842') }}</h3>
      </div>
      <div class="grid_header">
        <!--원인분석-->
        <div class="grid_header_left">1. {{ $t('LB00000843') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn v-if="frmData.acdt_prst_cd !== '4'" ref="add2" color="" outlined height="30" @click="add2">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn v-if="frmData.acdt_prst_cd !== '4'" color="" outlined height="30" @click="del2">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid5" style="width: 100%; height: 200px"></div>
      <div class="group_padd"></div>
      <div class="grid_header">
        <!--시정조치-->
        <div class="grid_header_left">2. {{ $t('LB00000844') }}</div>
        <div>
          <!-- 추가 -->
          <!-- 진행상태 :임시저장, 등록중일때 -->
          <v-btn v-if="frmData.acdt_prst_cd === undefined || frmData.acdt_prst_cd === '0' || frmData.acdt_prst_cd === '1'" ref="add3" color="" outlined height="30" @click="add3">{{
            $t('LB00000047')
          }}</v-btn>
          <!-- 삭제 -->
          <!-- 진행상태 :임시저장, 등록중일때 -->
          <v-btn v-if="frmData.acdt_prst_cd === undefined || frmData.acdt_prst_cd === '0' || frmData.acdt_prst_cd === '1'" color="" outlined height="30" @click="del3">{{
            $t('LB00000046')
          }}</v-btn>
        </div>
      </div>
      <div id="realgrid6" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!--첨부파일 정보-->
        <h3>{{ $t('LB00000845') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="label_tit essn">사고 발생/현장 사진</div>
              </v-col>
              <v-col style="align-self" cols="5"><picpreview ref="occurPicPop" style="height: 420px" /></v-col>
              <v-col cols="1">
                <div class="label_tit essn">사고 재현 사진</div>
              </v-col>
              <v-col style="align-self" cols="5"><picpreview ref="reenactPicPop" style="height: 420px" /></v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <div class="grid_header">
            <div class="grid_header_left"></div>
            <div>
              <!--양식 다운로드-->
              <v-btn color="" outlined height="30" @click="formDown">{{ $t('LB00000721') }}</v-btn>
            </div>
          </div>
          <div id="realgrid7" style="width: 100%; height: 230px"></div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div v-show="isComplete2">
      <div class="realgrid_container">
        <div class="sub_title">
          <!--재발방지대책 이행결과-->
          <h3>{{ $t('LB00000846') }}</h3>
        </div>
        <div id="realgrid8" style="width: 100%; height: 200px"></div>
      </div>
      <div class="group_padd"></div>
      <div class="realgrid_container">
        <div class="sub_title">
          <!--개선결과 첨부파일 정보-->
          <h3>{{ $t('LB00000847') }}</h3>
        </div>
        <div id="realgrid9" style="width: 100%; height: 150px"></div>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--결재상신-->
          <v-btn v-show="apprBtn" depressed width="120" color="primary" height="40" @click="appr()">{{ $t('LB00000763') }}</v-btn>
          <!--조치완료-->
          <v-btn v-show="cplt2Btn" depressed width="120" color="primary" height="40" @click="completedFuc()">{{ $t('LB00000686') }}</v-btn>
          <!--조사보고완료-->
          <v-btn v-show="cplt1Btn" depressed width="120" color="primary" height="40" @click="invstRprcmFuc()">{{ $t('LB00000848') }}</v-btn>
          <!--삭제-->
          <v-btn v-show="deleteModify" depressed width="120" color="primary" height="40" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
          <!--임시저장-->
          <v-btn v-show="tempSaveBtn" depressed width="120" color="primary" height="40" @click="tempSave()">{{ $t('LB00001234') }}</v-btn>
          <!--저장-->
          <v-btn v-show="saveBtn" depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <!-- 유저팝업 -->
    <userslct ref="pop" mkey1="acdtedt1" mkey2="acdtedt2" @close="popClose" />
    <!-- 사고위치팝업 -->
    <rgnslct ref="pop2" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="acdtedt" @close="popClose2" />
    <!-- 사고발생팝업 -->
    <deptslct ref="pop3" mkey="acdtedt" @close="popClose3" />
    <!-- 첨부파일 팝업 -->
    <filePopup ref="pop4" mkey1="acdtedt" :ispub="true" @close="popClose4" />
    <!-- 조치내용 팝업 -->
    <rsltreg ref="pop5" @close="popClose5" />
    <!-- 조치내용 저장없는 팝업 -->
    <rsltregllup ref="pop6" @close="popClose5" />
    <!-- 양식 다운로드 팝업 -->
    <filePopup ref="filepop" mkey1="acdtedt" :edit-chk="false" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
import deptslct from '../../common/deptslct'
import filePopup from '../../common/atflgrid'
import rsltreg from '../btrmrsltreg'
import rsltregllup from '../btrmrsltregllup'
import picpreview from '../../common/picpreview'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider
let gridView3 = GridView
let dataProvider3 = LocalDataProvider
let gridView4 = GridView
let dataProvider4 = LocalDataProvider
let gridView5 = GridView
let dataProvider5 = LocalDataProvider
let gridView6 = GridView
let dataProvider6 = LocalDataProvider
let gridView7 = GridView
let dataProvider7 = LocalDataProvider
let gridView8 = GridView
let dataProvider8 = LocalDataProvider
let gridView9 = GridView
let dataProvider9 = LocalDataProvider

export default {
  meta: {
    title: '사고관리',
    key(route) {
      return `/acdtmgnt/${route.params.catalog}`
    },
  },
  components: {
    userslct,
    deptslct,
    rgnslct,
    filePopup,
    rsltreg,
    rsltregllup,
    picpreview,
  },
  data() {
    return {
      today: new Date().getDay(),
      modify: false,
      // isComplete1: false,
      isComplete2: false,
      saveBtn: true,
      tempSaveBtn: true,
      cplt1Btn: true,
      cplt2Btn: false,
      apprBtn: false,
      deleteModify: false,
      isAdminGrp: false,
      codeid: [
        'ST00000002',
        'SH00000024',
        'SH00000025',
        'SH00000026',
        'SH00000027',
        'SH00000028',
        'SH00000029',
        'SH00000030',
        'SH00000031',
        'SH00000057',
        'SH00000058',
        'SH00000059',
        'SH00000061',
        'SH00000062',
        'SH00000069',
        'SH00000070',
        'SH00000071',
        'SH00000072',
      ],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000024: [{ cd: '', cd_nm: '' }], // 사고구분
        SH00000025: [{ cd: '', cd_nm: '' }], // 사고유형
        SH00000026: [{ cd: '', cd_nm: '' }], // 작업내용과정
        SH00000027: [{ cd: '', cd_nm: '' }], // 불완전내용
        SH00000028: [{ cd: '', cd_nm: '' }], // 불완전상태
        SH00000029: [{ cd: '', cd_nm: '' }], // 원인분석-구분
        SH00000030: [{ cd: '', cd_nm: '' }], // 원인분석-분류
        SH00000031: [{ cd: '', cd_nm: '' }], // 원인분석-내용
        SH00000057: [{ cd: '', cd_nm: '' }], // 남녀구분
        SH00000058: [{ cd: '', cd_nm: '' }], // 시정조치-구분
        SH00000059: [{ cd: '', cd_nm: '' }], // 재해구분
        SH00000061: [{ cd: '', cd_nm: '' }], // 사고조사 첨부파일
        SH00000062: [{ cd: '', cd_nm: '' }], // 개선결과 첨부파일
        SH00000069: [{ cd: '', cd_nm: '' }], // 자사/협력사/고객 구분
        SH00000070: [{ cd: '', cd_nm: '' }], // 산재여부
        SH00000071: [{ cd: '', cd_nm: '' }], // 사고유형
        SH00000072: [{ cd: '', cd_nm: '' }], // 사고기인물
      },

      level1Data: { id: 'level1Data', levels: 1, keys: [''], values: ['SELECT'] },
      level2Data: { id: 'level2Data', levels: 2, tags: [], keys: [], values: [] },
      level3Data: { id: 'level3Data', levels: 3, tags: [], keys: [], values: [] },
      selSH00000057: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000058: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000059: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000061: [{ cd: '', cd_nm: '', buf_1st_cntn_nm: '' }],
      selSH00000069: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      acdtYn: ['Y', 'N'], // 업무상 사고여부
      checkdCount: 0,
      totalcnt: 0,
      menu1: false,
      menu2: false,
      popupNm: '',
      dataRow: 0,
      schData: {},
      frmData: {
        bsns_rel_acdt_yn: 'N', // 업무상 사고여부
        inac_ivst_sbms_tgt_yn: 'N', // 산업재해조사표제출대상
        inac_yn: 'N', // 산재여부
      },
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      dateAccidentText: '', // 사고발생시간에 따른 요일
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000801') // 사고관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  watch: {
    menu1() {
      this.getDateAccident()
    },
  },
  async beforePageLeave(tab, type) {
    if (this.modify && JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
  mounted() {
    const user = this.$store.getters['auth/getUser']
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = user.cmpn_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        res.SH00000057.forEach((i) => {
          this.selSH00000057.cd.push(i.cd)
          this.selSH00000057.cd_nm.push(i.cd_nm)
        })
        res.SH00000058.forEach((i) => {
          this.selSH00000058.cd.push(i.cd)
          this.selSH00000058.cd_nm.push(i.cd_nm)
        })
        res.SH00000059.forEach((i) => {
          this.selSH00000059.cd.push(i.cd)
          this.selSH00000059.cd_nm.push(i.cd_nm)
        })
        let cnt = 0
        res.SH00000061.forEach((i) => {
          if (i.buf_1st_cntn === 'E') {
            // E일 경우 필수
            this.selSH00000061[cnt++] = { cd: i.cd, cd_nm: i.cd_nm, buf_1st_cntn: i.buf_1st_cntn, buf_1st_cntn_nm: '필수' }
          } else if (i.buf_1st_cntn === 'S') {
            // S일 경우는 선택
            this.selSH00000061[cnt++] = { cd: i.cd, cd_nm: i.cd_nm, buf_1st_cntn: i.buf_1st_cntn, buf_1st_cntn_nm: this.$t('LB00000106') }
          } else if (i.buf_1st_cntn === 'SE') {
            // SE일 경우는 해당시 필수
            this.selSH00000061[cnt++] = { cd: i.cd, cd_nm: i.cd_nm, buf_1st_cntn: i.buf_1st_cntn, buf_1st_cntn_nm: '해당시 필수' }
          }
        })
        res.SH00000069.forEach((i) => {
          this.selSH00000069.cd.push(i.cd)
          this.selSH00000069.cd_nm.push(i.cd_nm)
        })
        res.SH00000029.forEach((i) => {
          this.level1Data.keys.push(i.cd)
          this.level1Data.values.push(i.cd_nm)

          res.SH00000030.forEach((j) => {
            if (i.cd === j.up_cd) {
              this.level2Data.tags.push(j.cd)
              this.level2Data.keys.push([i.cd, j.cd])
              this.level2Data.values.push(j.cd_nm)
            }

            res.SH00000031.forEach((k) => {
              if (i.cd === j.up_cd && j.cd === k.up_cd) {
                this.level3Data.tags.push(k.cd)
                this.level3Data.keys.push([i.cd, j.cd, k.cd])
                this.level3Data.values.push(k.cd_nm)
              }
            })
          })
        })

        if (this.$route.params.acdtmgntdtledt !== null && this.$route.params.acdtmgntdtledt !== undefined) {
          this.frmData.acdt_id = this.$route.params.acdtmgntdtledt
          this.schData.acdt_id = this.$route.params.acdtmgntdtledt
        } else {
          this.frmData.acdt_id = ''
          this.schData.acdt_id = ''
          this.frmData.wkpl_id = user.wkpl_id
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.acdt_id = this.schData.acdt_id
        }

        this.createGrid()
        this.createGrid2()
        this.createGrid3()

        // 결재완료난 경우 수정X
        if (this.schData.acdtprstcd === '4') {
          this.$refs.occurPicPop.setEdit(false)
          this.$refs.reenactPicPop.setEdit(false)
        } else {
          this.$refs.occurPicPop.setEdit(true)
          this.$refs.occurPicPop.setMaxFileCnt(1) // 사고 발생/현장 사진 MAX 갯수 설정
          this.$refs.reenactPicPop.setEdit(true)
          this.$refs.reenactPicPop.setMaxFileCnt(1) // 사고 재현 사진 MAX 갯수 설정
        }

        // ID 존재할 경우
        if (this.frmData.acdt_id) {
          this.select()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    createGrid() {
      // 그리드 1 시작
      const fields = [
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

      const columns = [
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
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
          editable: false,
        },
      ]
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView.editOptions.readOnly = true
      }

      dataProvider.setRows(this.codes.SH00000026)
      gridView.refresh()

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 1 끝

      // 그리드 2 시작
      const fields2 = [
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

      const columns2 = [
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
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
          editable: false,
        },
      ]
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView2.editOptions.readOnly = true
      }

      dataProvider2.setRows(this.codes.SH00000027)
      gridView2.refresh()

      gridView2.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 2 끝

      // 그리드 3 시작
      const fields3 = [
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

      const columns3 = [
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
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
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

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView3.editOptions.readOnly = true
      }

      dataProvider3.setRows(this.codes.SH00000028)
      gridView3.refresh()

      gridView3.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 3 끝

      // 그리드 4 시작
      const fields4 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_pric_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_nm', // 사고자명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_sex_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_age',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'jncm_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'disa_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_disa_prts_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_hspz_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_care_days',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_dgns_rslt_txt',
          dataType: ValueType.TEXT,
        },
      ]

      const columns4 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'dfdt_cl_cd',
          fieldName: 'dfdt_cl_cd',
          width: '70',
          values: this.selSH00000069.cd,
          labels: this.selSH00000069.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'dfdt_pric_id',
          fieldName: 'dfdt_pric_id',
          width: '90',
          editable: false,
          styleCallback(grid, dataCell) {
            const dfdtclcd = grid.getValue(dataCell.index.itemIndex, 'dfdt_cl_cd')
            if (dfdtclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'dfdt_nm',
          fieldName: 'dfdt_nm',
          width: '50',
          editable: false,
          styleCallback(grid, dataCell) {
            const dfdtclcd = grid.getValue(dataCell.index.itemIndex, 'dfdt_cl_cd')
            if (dfdtclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
        },
        {
          header: '소속부서/협력사', // 소속부서/협력사
          name: 'dfdt_dept_nm',
          fieldName: 'dfdt_dept_nm',
          width: '110',
          editable: false,
          styleCallback(grid, dataCell) {
            const dfdtclcd = grid.getValue(dataCell.index.itemIndex, 'dfdt_cl_cd')
            if (dfdtclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
        },
        {
          header: this.$t('LB00000849'), // 성별
          name: 'dfdt_sex_cd',
          fieldName: 'dfdt_sex_cd',
          width: '30',
          values: this.selSH00000057.cd,
          labels: this.selSH00000057.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000850'), // 연령
          name: 'dfdt_age',
          fieldName: 'dfdt_age',
          width: '30',
          editor: {
            type: 'number',
            maxLength: 3,
          },
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'jncm_dt',
          fieldName: 'jncm_dt',
          width: '70',
        },
        {
          header: this.$t('LB00000852'), // 재해 구분
          name: 'disa_cl_cd',
          fieldName: 'disa_cl_cd',
          width: '70',
          lookupDisplay: true,
          values: this.selSH00000059.cd,
          labels: this.selSH00000059.cd_nm,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000853'), // 재해 부위
          name: 'dfdt_disa_prts_nm',
          fieldName: 'dfdt_disa_prts_nm',
          width: '60',
        },
        {
          header: this.$t('LB00000854'), // 입원유무
          name: 'dfdt_hspz_yn',
          fieldName: 'dfdt_hspz_yn',
          width: '40',
          values: ['', 'Y', 'N'],
          labels: ['SELECT', 'Y', 'N'],
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: '휴업기간(일)', // 휴업기간(일)
          name: 'dfdt_care_days',
          fieldName: 'dfdt_care_days',
          width: '70',
          editor: {
            type: 'number',
            maxLength: 5,
          },
        },
        {
          name: 'dfdt_dgns_rslt_txt',
          fieldName: 'dfdt_dgns_rslt_txt',
          width: '200',
          styleName: 'left',
          placeHolder: '예시) 우측 세번째 손가락 골절, 얼굴 왼쪽 뺨 2도 화상',
          header: {
            text: this.$t('LB00000856'), // 진단결과,
          },
          renderer: {
            showTooltip: true,
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

      if (this.schData.acdtprstcd === '4') {
        gridView4.setCheckBar({ visible: false })
      }

      gridView4.header.height = 49
      gridView4.displayOptions.rowHeight = 40
      gridView4.footer.height = 40
      gridView4.displayOptions.fitStyle = 'fill'
      gridView4.displayOptions.selectionStyle = 'block'
      gridView4.editOptions.editable = true
      gridView4.editOptions.appendable = true
      // gridView4.columnByName('dfdt_dept_nm').editable = false
      // gridView4.columnByName('dfdt_pric_id').editable = false
      // gridView4.columnByName('dfdt_nm').editable = false
      gridView4.columnByName('jncm_dt').editable = false

      if (this.schData.acdtprstcd === '4') {
        gridView4.editOptions.readOnly = true
      }

      gridView4.onCellClicked = function (grid, clickData) {
        if (this.schData.acdtprstcd !== '4') {
          const dfdtclcd = grid.getValue(clickData.dataRow, 'dfdt_cl_cd')
          if (dfdtclcd === '01') {
            if (clickData.column === 'dfdt_dept_nm' || clickData.column === 'dfdt_pric_id' || clickData.column === 'dfdt_nm') {
              grid.commit()
              this.popupNm = 'dfdt'
              const schData = {}
              this.$refs.pop.open(schData, false)
            }
          }
        }
      }.bind(this)

      gridView4.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 4 끝

      // 그리드 5 시작
      const fields5 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_ivst_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_ivst_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_ivst_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_caus_anl_txt_cd',
          dataType: ValueType.TEXT,
        },
      ]

      const columns5 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'acdt_ivst_cl_cd',
          fieldName: 'acdt_ivst_cl_cd',
          width: '200',
          lookupDisplay: true,
          values: this.level1Data.keys,
          labels: this.level1Data.values,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000123'), // 분류
          name: 'acdt_ivst_cat_cd',
          fieldName: 'acdt_ivst_cat_cd',
          width: '200',
          lookupDisplay: true,
          lookupSourceId: 'level2Data',
          lookupKeyFields: ['acdt_ivst_cl_cd', 'acdt_ivst_cat_cd'],
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000857'), // 원인 분석 내용
          name: 'acdt_caus_anl_txt_cd',
          fieldName: 'acdt_caus_anl_txt_cd',
          width: '600',
          lookupDisplay: true,
          lookupSourceId: 'level3Data',
          lookupKeyFields: ['acdt_ivst_cl_cd', 'acdt_ivst_cat_cd', 'acdt_caus_anl_txt_cd'],
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
      ]
      dataProvider5 = new LocalDataProvider(false)
      dataProvider5.setFields(fields5)
      gridView5 = new GridView('realgrid5')
      gridView5.setDataSource(dataProvider5)
      gridView5.setColumns(columns5)
      gridView5.setFooters({ visible: false })
      gridView5.setStateBar({ visible: false })

      if (this.schData.acdtprstcd === '4') {
        gridView5.setCheckBar({ visible: false })
      }

      gridView5.header.height = 49
      gridView5.displayOptions.rowHeight = 40
      gridView5.footer.height = 40
      gridView5.displayOptions.fitStyle = 'fill'
      gridView5.displayOptions.selectionStyle = 'block'
      gridView5.editOptions.editable = true
      gridView5.editOptions.appendable = true

      if (this.schData.acdtprstcd === '4') {
        gridView5.editOptions.readOnly = true
      }

      gridView5.addLookupSource(this.level2Data)
      gridView5.addLookupSource(this.level3Data)

      // console.log('level1 : ', this.level1Data) // eslint-disable-line no-console
      // console.log('level2 : ', this.level2Data) // eslint-disable-line no-console
      // console.log('level3 : ', this.level3Data) // eslint-disable-line no-console

      gridView5.onEditCommit = function (grid, index, oldValue, newValue) {
        if (index.fieldName === 'acdt_ivst_cl_cd') {
          if (oldValue !== newValue) {
            grid.setValue(index.itemIndex, 'acdt_ivst_cat_cd', '')
            grid.setValue(index.itemIndex, 'acdt_caus_anl_txt_cd', '')
          }
        } else if (index.fieldName === 'acdt_ivst_cat_cd') {
          if (oldValue !== newValue) {
            grid.setValue(index.itemIndex, 'acdt_caus_anl_txt_cd', '')
          }
        }
      }

      gridView5.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 5 끝

      // 그리드 6 시작
      const fields6 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_plan_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_plan_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_tmlm_dt',
          dataType: ValueType.TEXT,
        },
      ]

      const columns6 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'acdt_actn_cl_cd',
          fieldName: 'acdt_actn_cl_cd',
          width: '200',
          values: this.selSH00000058.cd,
          labels: this.selSH00000058.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000858'), // 시정 조치 내용
          name: 'acdt_actn_plan_txt',
          fieldName: 'acdt_actn_plan_txt',
          width: '600',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000796'), // 조치자
          name: 'acdt_actn_emp_nm',
          fieldName: 'acdt_actn_emp_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00000859'), // 조치기한
          name: 'acdt_actn_tmlm_dt',
          fieldName: 'acdt_actn_tmlm_dt',
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
      ]
      dataProvider6 = new LocalDataProvider(false)
      dataProvider6.setFields(fields6)
      gridView6 = new GridView('realgrid6')
      gridView6.setDataSource(dataProvider6)
      gridView6.setColumns(columns6)
      gridView6.setFooters({ visible: false })
      gridView6.setStateBar({ visible: false })
      if (this.schData.acdtprstcd === '3' || this.schData.acdtprstcd === '4' || this.schData.acdtprstcd === '5') {
        gridView6.setCheckBar({ visible: false })
      }
      gridView6.header.height = 49
      gridView6.displayOptions.rowHeight = 40
      gridView6.footer.height = 40
      gridView6.displayOptions.fitStyle = 'fill'
      gridView6.displayOptions.selectionStyle = 'block'
      gridView6.editOptions.editable = true
      gridView6.editOptions.appendable = true

      if (this.schData.acdtprstcd === '2' || this.schData.acdtprstcd === '3' || this.schData.acdtprstcd === '4' || this.schData.acdtprstcd === '5') {
        gridView6.editOptions.readOnly = true
      }

      gridView6.onCellClicked = function (grid, index, clickData) {
        const current = gridView6.getCurrent()
        if (current.column === 'acdt_actn_emp_nm') {
          gridView6.commit()
          this.frmData.ckFlag = true
          this.popupNm = 'actn'
          const schData = {}
          this.$refs.pop.open(schData, false)
        }

        // if (this.schData.acdt_prst_cd === '' || this.schData.acdtprstcd === '1') {
        //   if (clickData.column === 'acdt_actn_emp_id') {
        //     this.frmData.ckFlag = true
        //     this.popupNm = 'actn'
        //     const schData = {}
        //     this.$refs.pop.open(schData, false)
        //   }
        // }
      }.bind(this)

      gridView6.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 6 끝
    },
    createGrid2() {
      // 그리드 7 시작
      const fields7 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_item_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_size',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_1st_cntn', // 구분 코드
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_1st_cntn_nm', // 구분명
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_size_vl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_buf_1st_cntn', // 버퍼1 값(E, SE, S)
          dataType: ValueType.TEXT,
        },
      ]

      const columns7 = [
        {
          header: '첨부파일', // 첨부파일
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
          styleName: 'left',
        },
        // {
        //   header: '양식다운로드', // 양식다운로드
        //   name: 'acdt_atfl',
        //   fieldName: 'acdt_atfl',
        //   width: '200',
        //   styleCallback(grid, dataCell) {
        //     const ret = {}
        //     const atfilenm = grid.getValue(dataCell.index.itemIndex, 'atfile_nm')
        //     if (typeof atfilenm !== 'undefined' && atfilenm !== null && atfilenm !== '' && atfilenm !== 0) {
        //       ret.styleName = 'btn_download'
        //     }
        //     return ret
        //   },
        // },
        {
          header: this.$t('LB00000741'), // 구분
          name: 'buf_1st_cntn_nm',
          fieldName: 'buf_1st_cntn_nm',
          width: '70',
          editable: false,
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'acdt_atfl_nm',
          fieldName: 'acdt_atfl_nm',
          width: '300',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
        {
          header: this.$t('LB00000433'), // 용량
          name: 'acdt_atfl_size_vl',
          fieldName: 'acdt_atfl_size_vl',
          width: '100',
        },
      ]
      dataProvider7 = new LocalDataProvider(false)
      dataProvider7.setFields(fields7)
      gridView7 = new GridView('realgrid7')
      gridView7.setDataSource(dataProvider7)
      gridView7.setColumns(columns7)
      gridView7.setFooters({ visible: false })
      gridView7.setStateBar({ visible: false })
      gridView7.setCheckBar({ visible: false })

      gridView7.header.height = 49
      gridView7.displayOptions.rowHeight = 40
      gridView7.footer.height = 40
      gridView7.displayOptions.fitStyle = 'fill'
      gridView7.displayOptions.selectionStyle = 'block'
      gridView7.editOptions.editable = false
      gridView7.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView7.editOptions.readOnly = true
      }

      dataProvider7.setRows(this.selSH00000061)
      gridView7.refresh()

      // gridView7.onCellClicked = function (grid, index, clickData) {
      //   const current = gridView7.getCurrent()
      //   grid.commit()
      //   if (current.column === 'acdt_atfl') {
      //     // 다운로드 처리
      //     const DownKey = gridView7.getValue(index.itemIndex, 'atfile_key')
      //     const DownNo = gridView7.getValue(index.itemIndex, 'atfile_nm')
      //     this.download(DownKey, DownNo)
      //   }
      // }.bind(this)

      gridView7.onCellButtonClicked = function (grid, itemIndex, column) {
        if (itemIndex.column === 'acdt_atfl_nm') {
          grid.commit()
          this.frmData.ckFlag = true
          this.popupNm = 'file1'
          const atfileNo = gridView7.getValue(itemIndex.itemIndex, 'acdt_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.pop4.open(this.schData)
          // this.popOpen4('file1', itemIndex.dataRow, grid.getValue(itemIndex.itemIndex, 'acdt_atfl_no'))
        }
      }.bind(this)

      // gridView7.onCellEdited = function (grid, itemIndex, row, field) {
      //   this.frmData.ckFlag = true
      // }.bind(this)
      // 그리드 7 끝
    },
    createGrid3() {
      // 그리드 8 시작
      const fields8 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_plan_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_plan_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_tmlm_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_acco_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_actn_rslt_atfl_no',
          dataType: ValueType.TEXT,
        },
      ]

      const columns8 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'acdt_actn_cl_nm',
          fieldName: 'acdt_actn_cl_nm',
          width: '200',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000858'), // 시정 조치 내용
          name: 'acdt_actn_plan_txt',
          fieldName: 'acdt_actn_plan_txt',
          width: '500',
          styleName: 'left link',
        },
        {
          header: this.$t('LB00000796'), // 조치자
          name: 'acdt_actn_emp_nm',
          fieldName: 'acdt_actn_emp_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000859'), // 조치기한
          name: 'acdt_actn_tmlm_dt',
          fieldName: 'acdt_actn_tmlm_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000684'), // 조치완료일
          name: 'acdt_acco_dt',
          fieldName: 'acdt_acco_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000797'), // 조치내용
          name: 'acdt_actn_txt',
          fieldName: 'acdt_actn_txt',
          width: '100',
        },
      ]
      dataProvider8 = new LocalDataProvider(false)
      dataProvider8.setFields(fields8)
      gridView8 = new GridView('realgrid8')
      gridView8.setDataSource(dataProvider8)
      gridView8.setColumns(columns8)
      gridView8.setFooters({ visible: false })
      gridView8.setStateBar({ visible: false })
      gridView8.setCheckBar({ visible: false })

      gridView8.header.height = 49
      gridView8.displayOptions.rowHeight = 40
      gridView8.footer.height = 40
      gridView8.displayOptions.fitStyle = 'fill'
      gridView8.displayOptions.selectionStyle = 'block'
      gridView8.editOptions.editable = false
      gridView8.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView8.editOptions.readOnly = true
      }

      gridView8.onCellClicked = function (grid, index, clickData) {
        const current = gridView8.getCurrent()
        if (current.itemIndex < 0) return
        if (current.column === 'acdt_actn_plan_txt') {
          this.frmData.ckFlag = true
          const obj = {}
          obj.row = current.dataRow
          obj.id = dataProvider8.getValue(current.dataRow, 'acdt_id')
          obj.sn = dataProvider8.getValue(current.dataRow, 'acdt_actn_plan_sn')
          obj.nm = dataProvider8.getValue(current.dataRow, 'acdt_actn_emp_nm')
          obj.dt = dataProvider8.getValue(current.dataRow, 'acdt_acco_dt')
          obj.txt = dataProvider8.getValue(current.dataRow, 'acdt_actn_txt')
          obj.atfl = dataProvider8.getValue(current.dataRow, 'acdt_actn_rslt_atfl_no')
          // this.$refs.pop5.open(obj)
          if (this.schData.acdtprstcd !== '4') {
            this.$refs.pop5.open(obj)
          } else {
            this.$refs.pop6.open(obj)
          }
        }
      }.bind(this)

      // gridView8.onCellClicked = function (grid, index, clickData) {
      //   const current = gridView8.getCurrent()
      //   if (current.itemIndex < 0) return
      //   if (current.column === 'acdt_actn_plan_txt') {
      //     this.frmData.ckFlag = true
      //     if (this.schData.acdtprstcd !== '4') {
      //       const obj = {}
      //       obj.row = current.dataRow
      //       obj.id = dataProvider8.getValue(current.dataRow, 'acdt_id')
      //       obj.sn = dataProvider8.getValue(current.dataRow, 'acdt_actn_plan_sn')
      //       obj.nm = dataProvider8.getValue(current.dataRow, 'acdt_actn_emp_nm')
      //       obj.dt = dataProvider8.getValue(current.dataRow, 'acdt_acco_dt')
      //       obj.txt = dataProvider8.getValue(current.dataRow, 'acdt_actn_txt')
      //       obj.atfl = dataProvider8.getValue(current.dataRow, 'acdt_actn_rslt_atfl_no')
      //       this.$refs.pop5.open(obj)
      //     } else {
      //       const obj = {}
      //       obj.row = current.dataRow
      //       obj.id = dataProvider8.getValue(current.dataRow, 'acdt_id')
      //       obj.sn = dataProvider8.getValue(current.dataRow, 'acdt_actn_plan_sn')
      //       obj.nm = dataProvider8.getValue(current.dataRow, 'acdt_actn_emp_nm')
      //       obj.dt = dataProvider8.getValue(current.dataRow, 'acdt_acco_dt')
      //       obj.txt = dataProvider8.getValue(current.dataRow, 'acdt_actn_txt')
      //       obj.atfl = dataProvider8.getValue(current.dataRow, 'acdt_actn_rslt_atfl_no')
      //       this.$refs.pop6.open(obj)
      //     }
      //   }
      // }.bind(this)

      // gridView8.onCellEdited = function (grid, itemIndex, row, field) {
      //   this.frmData.ckFlag = true
      // }.bind(this)
      // 그리드 8 끝

      // 그리드 9 시작
      const fields9 = [
        {
          fieldName: 'acdt_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_item_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'acdt_atfl_size_vl',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'atfile_size',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_nm',
          dataType: ValueType.TEXT,
        },
      ]

      const columns9 = [
        {
          header: this.$t('LB00000860'), // 첨부파일 구분
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '400',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'acdt_atfl_nm',
          fieldName: 'acdt_atfl_nm',
          width: '300',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
        {
          header: this.$t('LB00000433'), // 용량
          name: 'acdt_atfl_size_vl',
          fieldName: 'acdt_atfl_size_vl',
          width: '100',
        },
      ]
      dataProvider9 = new LocalDataProvider(false)
      dataProvider9.setFields(fields9)
      gridView9 = new GridView('realgrid9')
      gridView9.setDataSource(dataProvider9)
      gridView9.setColumns(columns9)
      gridView9.setFooters({ visible: false })
      gridView9.setStateBar({ visible: false })
      gridView9.setCheckBar({ visible: false })

      gridView9.header.height = 49
      gridView9.displayOptions.rowHeight = 40
      gridView9.footer.height = 40
      gridView9.displayOptions.fitStyle = 'fill'
      gridView9.displayOptions.selectionStyle = 'block'
      gridView9.editOptions.editable = false
      gridView9.editOptions.appendable = false

      if (this.schData.acdtprstcd === '4') {
        gridView9.editOptions.readOnly = true
      }

      dataProvider9.setRows(this.codes.SH00000062)
      gridView9.refresh()

      gridView9.onCellButtonClicked = function (grid, itemIndex, column) {
        if (itemIndex.column === 'acdt_atfl_nm') {
          grid.commit()
          this.frmData.ckFlag = true
          this.popupNm = 'file2'
          const atfileNo = gridView9.getValue(itemIndex.itemIndex, 'acdt_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.pop4.open(this.schData)
          // this.popOpen4('file2', itemIndex.dataRow, grid.getValue(itemIndex.itemIndex, 'acdt_atfl_no'))
        }
      }.bind(this)

      // gridView9.onCellEdited = function (grid, itemIndex, row, field) {
      //   this.frmData.ckFlag = true
      // }.bind(this)
      // 그리드 9 끝
    },
    async select() {
      this.modify = true
      // this.isComplete1 = true
      const user = this.$store.getters['auth/getUser']
      this.frmData = await this.$axios.$get(`/api/v1/she/sfty/acdtmgnt/${this.frmData.acdt_id}`)

      if (this.$route.params.mode !== 'U' && this.$route.params.admin === false) {
        this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
        this.$router.replace({
          name: `acdtmgnt-acdtmgntdtllup-acdtmgntdtllup`,
          params: {
            acdtmgntdtllup: this.schData.acdt_id,
            schData: this.schData,
          },
        })
      }

      // 작업내용과정
      dataProvider.setRows(this.frmData.oprn_list)
      gridView.refresh()
      gridView.setTopItem(0)
      // 불완전한행동
      dataProvider2.setRows(this.frmData.usfttxt_list)
      gridView2.refresh()
      gridView2.setTopItem(0)
      // 불완전한상태
      dataProvider3.setRows(this.frmData.usftst_list)
      gridView3.refresh()
      gridView3.setTopItem(0)
      // 사고자
      dataProvider4.setRows(this.frmData.dfdt_list)
      gridView4.refresh()
      gridView4.setTopItem(0)
      this.totalcnt = gridView4.getItemCount()
      // 원인분석
      dataProvider5.setRows(this.frmData.ivst_list)
      gridView5.refresh()
      gridView5.setTopItem(0)
      // 시정조치
      dataProvider6.setRows(this.frmData.actn_list)
      gridView6.refresh()
      gridView6.setTopItem(0)

      // this.createGrid2()
      // 첨부파일정보
      dataProvider7.setRows(this.frmData.atfl_list1)
      gridView7.refresh()
      gridView7.setTopItem(0)

      // this.createGrid3()
      // 재발방지대책 이행결과
      dataProvider8.setRows(this.frmData.actn_list)
      gridView8.refresh()
      gridView8.setTopItem(0)

      // 개선결과 첨부파일정보
      dataProvider9.setRows(this.frmData.atfl_list2)
      gridView9.refresh()
      gridView9.setTopItem(0)

      this.isComplete2 = true
      if (this.frmData.acdt_prst_cd === '0' || this.frmData.acdt_prst_cd === '1') {
        this.isComplete2 = false
        this.cplt2Btn = false
        this.cplt1Btn = true
      } else if (this.frmData.acdt_prst_cd === '5') {
        this.cplt2Btn = true
        this.apprBtn = true
        this.cplt1Btn = false
        this.tempSaveBtn = false // 임시저장 버튼
      } else if (this.frmData.acdt_prst_cd === '2' || this.frmData.acdt_prst_cd === '6') {
        this.cplt2Btn = false
        this.apprBtn = true
        this.cplt1Btn = false
        this.tempSaveBtn = false // 임시저장 버튼
      } else if (this.frmData.acdt_prst_cd === '3') {
        this.cplt2Btn = false
        this.apprBtn = false
        this.cplt1Btn = false
        this.tempSaveBtn = false // 임시저장 버튼
      } else if (this.frmData.acdt_prst_cd === '4') {
        this.cplt1Btn = false
        this.tempSaveBtn = false // 임시저장 버튼
      }

      // this.saveBtn = !this.isComplete2
      const usrinfo = this.$store.getters['auth/getUser']
      // 시스템그룹 사용자
      usrinfo.roleTypeCds.forEach((i) => {
        if (i === 'M01') {
          this.isAdminGrp = true
        }
      })

      if (this.frmData.crt_emp_no === user.emp_no || this.isAdminGrp) {
        this.deleteModify = true
      } else {
        this.deleteModify = false
      }

      this.frmData.ckFlag = false
      this.frmData.ckFile = false
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      // 요일 셋팅
      if (this.isEmpty(this.frmData.acdt_occr_dt) === false) {
        this.getDateAccident()
      }

      // 사고 발생/현장 사진
      if (this.frmData.occur_atfl_list.length > 0) {
        await this.$refs.occurPicPop.load(this.frmData.occur_atfl_list[0].acdt_atfl_no)
      }
      // 사고 재현 사진
      if (this.frmData.reenact_atfl_list.length > 0) {
        await this.$refs.reenactPicPop.load(this.frmData.reenact_atfl_list[0].acdt_atfl_no)
      }
    },
    add() {
      this.frmData.ckFlag = true
      gridView4.commit()
      dataProvider4.addRow({
        acdt_id: '',
        dfdt_sn: '',
        dfdt_pric_id: '',
        dfdt_cl_cd: '',
        dfdt_nm: '',
        dfdt_dept_nm: '',
        dfdt_sex_cd: '',
        dfdt_age: '',
        jncm_dt: '',
        disa_cl_cd: '',
        dfdt_disa_txt: '',
        dfdt_hspz_yn: '',
        dfdt_care_days: '',
        // dfdt_dgns_rslt_txt: '',
      })
    },
    del() {
      this.frmData.ckFlag = true
      gridView4.commit()
      dataProvider4.setOptions({ softDeleting: true })
      const checkedRows = gridView4.getCheckedRows()
      dataProvider4.hideRows(checkedRows)
      dataProvider4.removeRows(checkedRows)
    },
    add2() {
      this.frmData.ckFlag = true
      gridView5.commit()
      dataProvider5.addRow({
        acdt_id: '',
        acdt_ivst_sn: '',
        acdt_ivst_cl_cd: '',
        acdt_ivst_cat_cd: '',
        acdt_caus_anl_txt_cd: '',
      })
    },
    del2() {
      this.frmData.ckFlag = true
      gridView5.commit()
      dataProvider5.setOptions({ softDeleting: true })
      const checkedRows = gridView5.getCheckedRows()
      dataProvider5.hideRows(checkedRows)
      dataProvider5.removeRows(checkedRows)
    },
    add3() {
      this.frmData.ckFlag = true
      gridView6.commit()
      dataProvider6.addRow({
        acdt_id: '',
        acdt_actn_plan_sn: '',
        acdt_actn_cl_cd: '',
        acdt_actn_plan_txt: '',
        acdt_actn_emp_id: '',
        acdt_actn_emp_nm: '',
        acdt_actn_tmlm_dt: '',
      })
    },
    del3() {
      this.frmData.ckFlag = true
      gridView6.commit()
      dataProvider6.setOptions({ softDeleting: true })
      const checkedRows = gridView6.getCheckedRows()
      dataProvider6.hideRows(checkedRows)
      dataProvider6.removeRows(checkedRows)
    },
    // 밸리데이션 체크
    validate(chkType) {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()
      gridView4.commit()
      gridView5.commit()
      gridView6.commit()
      gridView7.commit()
      gridView8.commit()
      gridView9.commit()

      // 사업장
      if (!this.frmData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }

      // 사고구분
      if (!this.frmData.acdt_cl_cd) {
        this.$toast.show(this.$t('MS00000355'), { className: 'toast_error' }) // 사고구분을 선택해주세요.
        this.$refs.acdt_cl_cd.focus()
        return false
      }

      // 사고구분 상세
      if (!this.frmData.acdt_dtl_cl_cd) {
        this.$toast.show('사고구분 상세를 선택해주세요.', { className: 'toast_error' })
        this.$refs.acdt_dtl_cl_cd.focus()
        return false
      }

      // 사고유형
      if (!this.frmData.acdt_tp_cd) {
        this.$toast.show(this.$t('MS00000645'), { className: 'toast_error' }) // 사고유형을 선택해주세요.
        this.$refs.acdt_tp_cd.focus()
        return false
      }

      // chkType
      // 0 = 임시저장
      // 1 = 저장
      // 2 = 조사보고완료
      // 3 = 조치완료 / 조치지연
      // 4 = 결재상신

      // 임시저장일때
      if (chkType === 0) {
        // 사고일시 입력을 하나라도 했을 경우 체크
        if (this.frmData.acdt_occr_dt || this.frmData.acdt_occr_hour || this.frmData.acdt_occr_minute) {
          // 사고일시 일자 체크
          if (!this.frmData.acdt_occr_dt) {
            this.$toast.show(this.$t('MS00000361'), { className: 'toast_error' }) // 사고일시를 입력해주세요.
            this.$refs.acdt_occr_dt.focus()
            return false
          }
          // 사고일시 시간 체크
          if (!this.frmData.acdt_occr_hour) {
            this.$toast.show(this.$t('MS00000362'), { className: 'toast_error' }) // 사고시간을 입력해주세요.
            this.$refs.acdt_occr_hour.focus()
            return false
          }
          if (Number(this.frmData.acdt_occr_hour.length) < 2) {
            this.$toast.show('사고시간은 두자리로 입력해주세요.', { className: 'toast_error' })
            this.$refs.acdt_occr_hour.focus()
            return false
          }
          if (Number(this.frmData.acdt_occr_hour) > 23) {
            this.$toast.show('사고시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
            this.$refs.acdt_occr_hour.focus()
            return false
          }
          // 사고일시 분 체크
          if (!this.frmData.acdt_occr_minute) {
            this.$toast.show(this.$t('MS00000362'), { className: 'toast_error' }) // 사고시간을 입력해주세요.
            this.$refs.acdt_occr_minute.focus()
            return false
          }
          if (Number(this.frmData.acdt_occr_minute.length) < 2) {
            this.$toast.show('사고시간 분은 두자리로 입력해주세요.', { className: 'toast_error' })
            this.$refs.acdt_occr_minute.focus()
            return false
          }
          if (Number(this.frmData.acdt_occr_minute) > 59) {
            this.$toast.show('사고시간 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
            this.$refs.acdt_occr_minute.focus()
            return false
          }
        }
      }

      // 저장, 조사보고완료, 조치완료, 결재상신 일때 체크
      if (chkType > 0) {
        // 사고기인물
        if (!this.frmData.acdt_caus_tgt_cd) {
          this.$toast.show('사고기인물을 선택해주세요.', { className: 'toast_error' })
          this.$refs.acdt_caus_tgt_cd.focus()
          return false
        }
        if (!this.frmData.acdt_caus_tgt_txt) {
          this.$toast.show('사고기인물 상세를 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_caus_tgt_txt.focus()
          return false
        }
        // 사고위치
        if (!this.frmData.acdt_wkpl_loc_nm) {
          this.$toast.show(this.$t('MS00000357'), { className: 'toast_error' }) // 사고위치를 입력해주세요.
          this.$refs.acdt_wkpl_loc_nm.focus()
          return false
        }
        // 사고위치 상세
        if (!this.frmData.acdt_dtl_loc_nm) {
          this.$toast.show(this.$t('MS00000358'), { className: 'toast_error' }) // 사고위치 상세정보를 입력해주세요.
          this.$refs.acdt_dtl_loc_nm.focus()
          return false
        }
        // 사고발생 부서
        if (!this.frmData.kor_acdt_occr_org_nm) {
          this.$toast.show(this.$t('MS00000360'), { className: 'toast_error' }) // 사고발생부서를 입력해주세요.
          this.$refs.kor_acdt_occr_org_nm.focus()
          return false
        }
        // // 사고일시 체크 시작
        // 사고일시 날짜
        if (!this.frmData.acdt_occr_dt) {
          this.$toast.show(this.$t('MS00000361'), { className: 'toast_error' }) // 사고일시를 입력해주세요.
          this.$refs.acdt_occr_dt.focus()
          return false
        }
        // 사고일시 시간
        if (!this.frmData.acdt_occr_hour) {
          this.$toast.show(this.$t('MS00000362'), { className: 'toast_error' }) // 사고시간을 입력해주세요.
          this.$refs.acdt_occr_hour.focus()
          return false
        }
        if (Number(this.frmData.acdt_occr_hour.length) < 2) {
          this.$toast.show('사고시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_occr_hour.focus()
          return false
        }
        if (Number(this.frmData.acdt_occr_hour) > 23) {
          this.$toast.show('사고시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_occr_hour.focus()
          return false
        }
        // 사고일시 분
        if (!this.frmData.acdt_occr_minute) {
          this.$toast.show(this.$t('MS00000362'), { className: 'toast_error' }) // 사고시간을 입력해주세요.
          this.$refs.acdt_occr_minute.focus()
          return false
        }
        if (Number(this.frmData.acdt_occr_minute.length) < 2) {
          this.$toast.show('사고시간 분은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_occr_minute.focus()
          return false
        }
        if (Number(this.frmData.acdt_occr_minute) > 59) {
          this.$toast.show('사고시간 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_occr_minute.focus()
          return false
        }
        // // 사고일시 체크 끝

        // 업무상 사고 여부
        if (!this.frmData.bsns_rel_acdt_yn) {
          this.$toast.show(this.$t('MS00000363'), { className: 'toast_error' }) // 업무상사고여부를 선택해주세요.
          this.$refs.bsns_rel_acdt_yn.focus()
          return false
        }
        // 산업재해조사표제출대상
        if (!this.frmData.inac_ivst_sbms_tgt_yn) {
          this.$toast.show('산업재해조사표제출대상', { className: 'toast_error' })
          this.$refs.inac_ivst_sbms_tgt_yn.focus()
          return false
        }
        // 산재여부
        if (!this.frmData.inac_yn) {
          this.$toast.show(this.$t('MS00000364'), { className: 'toast_error' }) // 산재여부를 선택해주세요.
          this.$refs.inac_yn.focus()
          return false
        }
        // // 사고발생 체크 시작
        // 사고발생 시간
        if (!this.frmData.occrhour) {
          this.$toast.show('사고발생시간을 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrhour.focus()
          return false
        }
        if (Number(this.frmData.occrhour.length) < 2) {
          this.$toast.show('사고발생시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrhour.focus()
          return false
        }
        if (Number(this.frmData.occrhour) > 23 || Number(this.frmData.occrhour) < 0) {
          this.$toast.show('사고발생시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrhour.focus()
          return false
        }
        // 사고발생 분
        if (!this.frmData.occrminute) {
          this.$toast.show('사고발생 분을 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrminute.focus()
          return false
        }
        if (Number(this.frmData.occrminute.length) < 2) {
          this.$toast.show('사고발생 분은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrminute.focus()
          return false
        }
        if (Number(this.frmData.occrminute) > 59 || Number(this.frmData.occrminute) < 0) {
          this.$toast.show('사고발생 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.occrminute.focus()
          return false
        }
        // // 사고발생 체크 끝

        if (!this.frmData.acdt_occr_txt) {
          this.$toast.show(this.$t('MS00000365'), { className: 'toast_error' }) // 사고발생현황을 입력해주세요.
          this.$refs.acdt_occr_txt.focus()
          return false
        }
        if (this.frmData.acdt_occr_txt.length > 2000) {
          this.$toast.show(this.$t('MS00000366'), { className: 'toast_error' }) // 사고발생현황을 2000자 이하로 입력해주세요.
          this.$refs.acdt_occr_txt.focus()
          return false
        }
        // // 사고접수 시간 체크 시작
        // 사고접수 시간
        if (!this.frmData.accphour) {
          this.$toast.show('사고접수 시간을 입력해주세요.', { className: 'toast_error' })
          this.$refs.accphour.focus()
          return false
        }
        if (Number(this.frmData.accphour.length) < 2) {
          this.$toast.show('사고접수시간 두자리를 입력해주세요.', { className: 'toast_error' })
          this.$refs.accphour.focus()
          return false
        }
        if (Number(this.frmData.accphour) > 23 || Number(this.frmData.accphour) < 0) {
          this.$toast.show('사고접수시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.accphour.focus()
          return false
        }
        // 사고접수 시간 분
        if (!this.frmData.accpminute) {
          this.$toast.show('사고접수 분을 입력해주세요.', { className: 'toast_error' })
          this.$refs.accpminute.focus()
          return false
        }
        if (Number(this.frmData.accpminute.length) < 2) {
          this.$toast.show('사고접수분 두자리를 입력해주세요.', { className: 'toast_error' })
          this.$refs.accpminute.focus()
          return false
        }
        if (Number(this.frmData.accpminute) > 59 || Number(this.frmData.accpminute) < 0) {
          this.$toast.show('사고접수 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.accpminute.focus()
          return false
        }
        // // 사고접수 시간 체크 끝
        // 사고접수내용
        if (!this.frmData.acdt_accp_txt) {
          this.$toast.show('사고접수내용을 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_accp_txt.focus()
          return false
        }
        if (this.frmData.acdt_accp_txt.length > 2000) {
          this.$toast.show('사고접수 내용을 2000자 이하로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_accp_txt.focus()
          return false
        }
        // // 초기대응 시간 체크 시작
        // 초기대응 시간
        if (!this.frmData.bgng_rposhour) {
          this.$toast.show('초기대응시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposhour.focus()
          return false
        }
        if (Number(this.frmData.bgng_rposhour.length) < 2) {
          this.$toast.show('초기대응시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposhour.focus()
          return false
        }
        if (Number(this.frmData.bgng_rposhour) > 23 || Number(this.frmData.bgng_rposhour) < 0) {
          this.$toast.show('초기대응시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposhour.focus()
          return false
        }
        // 초기대응 분
        if (!this.frmData.bgng_rposminute) {
          this.$toast.show('초기대응 분을 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposminute.focus()
          return false
        }
        if (Number(this.frmData.bgng_rposminute.length) < 2) {
          this.$toast.show('초기대응 분은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposminute.focus()
          return false
        }
        if (Number(this.frmData.bgng_rposminute) > 59 || Number(this.frmData.bgng_rposminute) < 0) {
          this.$toast.show('초기대응 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.bgng_rposminute.focus()
          return false
        }
        // // 초기대응 시간 체크 끝
        if (!this.frmData.acdt_bgng_rpos_txt) {
          this.$toast.show('초기대응내용을 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_bgng_rpos_txt.focus()
          return false
        }
        if (this.frmData.acdt_bgng_rpos_txt.length > 2000) {
          this.$toast.show('초기대응 내용을 2000자 이하로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_bgng_rpos_txt.focus()
          return false
        }
        // // 사후조치 시간 체크 시작
        // 사후조치 시간
        if (!this.frmData.af_actnhour) {
          this.$toast.show('사고발생시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnhour.focus()
          return false
        }
        if (Number(this.frmData.af_actnhour.length) < 2) {
          this.$toast.show('사후조치시간은 두자리로 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnhour.focus()
          return false
        }
        if (Number(this.frmData.af_actnhour) > 23 || Number(this.frmData.af_actnhour) < 0) {
          this.$toast.show('사후조치시간은 0~23 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnhour.focus()
          return false
        }
        // 사후조치 분
        if (!this.frmData.af_actnminute) {
          this.$toast.show('사후조치 분을 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnminute.focus()
          return false
        }
        if (Number(this.frmData.af_actnminute.length) < 2) {
          this.$toast.show('사후조치분 두자리를 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnminute.focus()
          return false
        }
        if (Number(this.frmData.af_actnminute) > 59 || Number(this.frmData.af_actnminute) < 0) {
          this.$toast.show('사후조치 분은 0~59 사이의 값으로 입력해주세요.', { className: 'toast_error' })
          this.$refs.af_actnminute.focus()
          return false
        }
        // // 사후조치 시간 체크 끝
        if (!this.frmData.acdt_af_actn_txt) {
          this.$toast.show('사후조치내용을 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_af_actn_txt.focus()
          return false
        }
        if (this.frmData.acdt_af_actn_txt.length > 2000) {
          this.$toast.show('사후조치 내용을 2000자 이하로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_af_actn_txt.focus()
          return false
        }
        // 사고발생원인 내용
        if (!this.frmData.acdt_occr_caus_txt) {
          this.$toast.show(this.$t('MS00000367'), { className: 'toast_error' }) // 사고발생원인을 입력해주세요.
          this.$refs.acdt_occr_caus_txt.focus()
          return false
        }
        if (this.frmData.acdt_occr_caus_txt.length > 2000) {
          this.$toast.show(this.$t('MS00000368'), { className: 'toast_error' }) // 사고발생원인을 2000자 이하로 입력해주세요.
          this.$refs.acdt_occr_caus_txt.focus()
          return false
        }
        // 재발방지대책 사고발생부서내용 + 안전관리부서내용
        if (!this.frmData.acdt_dept_prvt_meas_txt) {
          this.$toast.show('사고발생부서내용을 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_dept_prvt_meas_txt.focus()
          return false
        }
        if (this.frmData.acdt_dept_prvt_meas_txt.length > 1000) {
          this.$toast.show('사고발생부서내용을 1000자 이하로 입력해주세요.', { className: 'toast_error' })
          this.$refs.acdt_dept_prvt_meas_txt.focus()
          return false
        }
        if (!this.frmData.sft_dept_prvt_meas_txt) {
          this.$toast.show('안전관리부서을 입력해주세요.', { className: 'toast_error' })
          this.$refs.sft_dept_prvt_meas_txt.focus()
          return false
        }
        if (this.frmData.sft_dept_prvt_meas_txt.length > 1000) {
          this.$toast.show('안전관리부서을 1000자 이하로 입력해주세요.', { className: 'toast_error' })
          this.$refs.sft_dept_prvt_meas_txt.focus()
          return false
        }
        // 작업내용 및 과정,
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          if (dataProvider.getValue(i, 'chk') === 'Y' || dataProvider.getValue(i, 'chk') === 'N') {
            this.checkdCount++
          }
        }
        if (this.checkdCount <= 0) {
          this.$toast.show('작업내용 및 과정을 선택해주세요.', { className: 'toast_error' })
          gridView.setCurrent({ column: 'chk' })
          return false
        } else {
          this.checkdCount = 0
        }

        // 불안전한 행동(시스템적 원인),
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          if (dataProvider2.getValue(i, 'chk') === 'Y' || dataProvider2.getValue(i, 'chk') === 'N') {
            this.checkdCount++
          }
        }
        if (this.checkdCount <= 0) {
          this.$toast.show('불안전한 행동을 선택해주세요.', { className: 'toast_error' })
          gridView2.setCurrent({ column: 'chk' })
          return false
        } else {
          this.checkdCount = 0
        }

        // 불안전한 상태(설비적 원인)
        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          if (dataProvider3.getValue(i, 'chk') === 'Y' || dataProvider3.getValue(i, 'chk') === 'N') {
            this.checkdCount++
          }
        }
        if (this.checkdCount <= 0) {
          this.$toast.show('불안전한 상태을 선택해주세요.', { className: 'toast_error' })
          gridView3.setCurrent({ column: 'chk' })
          return false
        } else {
          this.checkdCount = 0
        }

        // 사고자 인적사항 (구분, 사번, 성명, 소속부서/협력사 , 성별, 연령, 입사일, 재해 구분, 재해 부위, 입원유무)
        if (dataProvider4.getRowCount() <= 0) {
          this.$toast.show('사고자 인적사항을 추가해주세요.', { className: 'toast_error' })
          return false
        }
        for (let i = 0; i < dataProvider4.getRowCount(); i++) {
          if (dataProvider4.getRowState(i) === 'createAndDeleted' || dataProvider4.getRowState(i) === 'deleted') {
            continue
          }
          // 구분
          if (!dataProvider4.getValue(i, 'dfdt_cl_cd')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_cl_cd' })
            this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            return false
          }
          // 사번
          if (!dataProvider4.getValue(i, 'dfdt_pric_id')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_pric_id' })
            this.$toast.show('사번을 입력하세요', { className: 'toast_error' })
            return false
          }
          // 성명
          if (!dataProvider4.getValue(i, 'dfdt_nm')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_nm' })
            this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
            return false
          }
          // 소속부서/협력사
          if (!dataProvider4.getValue(i, 'dfdt_dept_nm')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_dept_nm' })
            this.$toast.show('소속부서/협력사을 입력해주세요.', { className: 'toast_error' })
            return false
          }
          // 성별
          if (!dataProvider4.getValue(i, 'dfdt_sex_cd')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_sex_cd' })
            this.$toast.show(this.$t('MS00000373'), { className: 'toast_error' }) // 성별을 선택해주세요.
            return false
          }
          // 연령
          if (!dataProvider4.getValue(i, 'dfdt_age')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_age' })
            this.$toast.show(this.$t('MS00000374'), { className: 'toast_error' }) // 연령을 입력해주세요.
            return false
          }
          // 입사일
          // if (!dataProvider4.getValue(i, 'jncm_dt')) {
          //   gridView4.setCurrent({ dataRow: i, fieldName: 'jncm_dt' })
          //   this.$toast.show('입사일을 입력해주세요.', { className: 'toast_error' })
          //   return false
          // }
          // 재해 구분
          if (!dataProvider4.getValue(i, 'disa_cl_cd')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'disa_cl_cd' })
            this.$toast.show(this.$t('MS00000377'), { className: 'toast_error' }) // 재해구분을 선택해주세요.
            return false
          }
          // 재해 부위
          if (!dataProvider4.getValue(i, 'dfdt_disa_prts_nm')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_disa_prts_nm' })
            this.$toast.show(this.$t('MS00000379'), { className: 'toast_error' }) // 재해부위를 입력해주세요.
            return false
          }
          if (!this.isEmpty(dataProvider4.getValue(i, 'dfdt_disa_prts_nm')) && dataProvider4.getValue(i, 'dfdt_disa_prts_nm').length > 200) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_disa_prts_nm' })
            this.$toast.show(this.$t('MS00000381'), { className: 'toast_error' }) // 재해부위는 200자를 초과할 수 없습니다.
            return false
          }
          // 입원유무
          if (!dataProvider4.getValue(i, 'dfdt_hspz_yn')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_hspz_yn' })
            this.$toast.show(this.$t('MS00000382'), { className: 'toast_error' }) // 입원유무를 선택해주세요.
            return false
          }
        }
        // 사고발생사진 체크
        if (this.$refs.occurPicPop.validate() === false) {
          this.$toast.show('사고 발생/현장 첨부사진을 첨부해주세요.', { className: 'toast_error' })
          return false
        }
        if (this.$refs.reenactPicPop.validate() === false) {
          this.$toast.show('사고 재현 첨부사진을 첨부해주세요.', { className: 'toast_error' })
          return false
        }
      }

      // 조사보고완료, 조치완료, 결재상신 일때 체크
      if (chkType > 1) {
        for (let i = 0; i < dataProvider4.getRowCount(); i++) {
          if (dataProvider4.getRowState(i) === 'createAndDeleted' || dataProvider4.getRowState(i) === 'deleted') {
            continue
          }
          // 휴업기간(일)
          if (!dataProvider4.getValue(i, 'dfdt_care_days')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_care_days' })
            this.$toast.show('휴업기간(일)을 입력해주세요.', { className: 'toast_error' })
            return false
          }
          if (!this.isEmpty(dataProvider4.getValue(i, 'dfdt_care_days')) && dataProvider4.getValue(i, 'dfdt_care_days').length > 5) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_care_days' })
            this.$toast.show(this.$t('MS00000384'), { className: 'toast_error' }) // 치료기간은 5자를 초과할 수 없습니다.
            return false
          }

          // 진단결과
          if (!dataProvider4.getValue(i, 'dfdt_dgns_rslt_txt')) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_dgns_rslt_txt' })
            this.$toast.show('진단결과를 입력해주세요.', { className: 'toast_error' })
            return false
          }
          if (!this.isEmpty(dataProvider4.getValue(i, 'dfdt_dgns_rslt_txt')) && dataProvider4.getValue(i, 'dfdt_dgns_rslt_txt').length > 200) {
            gridView4.setCurrent({ dataRow: i, fieldName: 'dfdt_dgns_rslt_txt' })
            this.$toast.show(this.$t('MS00000388'), { className: 'toast_error' }) // 진단결과는 200자를 초과할 수 없습니다.
            return false
          }
        }

        // 사고조사 결과 1. 원인분석
        if (dataProvider5.getRowCount() <= 0) {
          this.$toast.show(this.$t('MS00000350'), { className: 'toast_error' }) // 사고조사 결과(1.원인분석)를 등록해주세요.
          return false
        }
        // 1.원인분석
        for (let i = 0; i < dataProvider5.getRowCount(); i++) {
          if (dataProvider5.getRowState(i) === 'createAndDeleted' || dataProvider5.getRowState(i) === 'deleted') {
            continue
          }
          if (!dataProvider5.getValue(i, 'acdt_ivst_cl_cd')) {
            gridView5.setCurrent({ dataRow: i, fieldName: 'acdt_ivst_cl_cd' })
            this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            return false
          }
          if (!dataProvider5.getValue(i, 'acdt_ivst_cat_cd')) {
            gridView5.setCurrent({ dataRow: i, fieldName: 'acdt_ivst_cat_cd' })
            this.$toast.show(this.$t('MS00000389'), { className: 'toast_error' }) // 분류를 선택해주세요.
            return false
          }
          if (!dataProvider5.getValue(i, 'acdt_caus_anl_txt_cd')) {
            gridView5.setCurrent({ dataRow: i, fieldName: 'acdt_caus_anl_txt_cd' })
            this.$toast.show(this.$t('MS00000390'), { className: 'toast_error' }) // 원인분석내용을 선택해주세요.
            return false
          }
        }

        // 사고조사 결과 2. 시정조치
        if (dataProvider6.getRowCount() <= 0) {
          this.$toast.show(this.$t('MS00000353'), { className: 'toast_error' }) // 사고조사 결과(2.시정조치)를 등록해주세요.
          return false
        }
        // 2.시정 조치
        for (let i = 0; i < dataProvider6.getRowCount(); i++) {
          if (dataProvider6.getRowState(i) === 'createAndDeleted' || dataProvider6.getRowState(i) === 'deleted') {
            continue
          }
          if (!dataProvider6.getValue(i, 'acdt_actn_cl_cd')) {
            gridView6.setCurrent({ dataRow: i, fieldName: 'acdt_actn_cl_cd' })
            this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            return false
          }
          if (!dataProvider6.getValue(i, 'acdt_actn_plan_txt')) {
            gridView6.setCurrent({ dataRow: i, fieldName: 'acdt_actn_plan_txt' })
            this.$toast.show(this.$t('MS00000391'), { className: 'toast_error' }) // 시정조치내용을 입력해주세요.
            return false
          }
          if (!this.isEmpty(dataProvider6.getValue(i, 'acdt_actn_plan_txt')) && dataProvider6.getValue(i, 'acdt_actn_plan_txt').length > 1000) {
            gridView6.setCurrent({ dataRow: i, fieldName: 'acdt_actn_plan_txt' })
            this.$toast.show(this.$t('MS00000392'), { className: 'toast_error' }) // 시정조치내용은 1000자를 초과할 수 없습니다.
            return false
          }
          if (!dataProvider6.getValue(i, 'acdt_actn_emp_nm')) {
            gridView6.setCurrent({ dataRow: i, fieldName: 'acdt_actn_emp_nm' })
            this.$toast.show(this.$t('MS00000393'), { className: 'toast_error' }) // 조치자를 입력해주세요.
            return false
          }
          if (!dataProvider6.getValue(i, 'acdt_actn_tmlm_dt')) {
            gridView6.setCurrent({ dataRow: i, fieldName: 'acdt_actn_tmlm_dt' })
            this.$toast.show(this.$t('MS00000394'), { className: 'toast_error' }) // 조치기한을 입력해주세요.
            return false
          }
        }

        // 첨부파일buf_1st_cntn : E (필수), S (선택), SE (해당시 필수)
        for (let i = 0; i < dataProvider7.getRowCount(); i++) {
          // 첨부파일 구분이 선택일때
          if (dataProvider7.getValue(i, 'cd_buf_1st_cntn') === 'S') {
            continue
          }
          // 첨부파일 구분이 필수일때
          if (dataProvider7.getValue(i, 'cd_buf_1st_cntn') === 'E') {
            if (!dataProvider7.getValue(i, 'acdt_atfl_nm')) {
              this.$toast.show(this.$t('MS00000352'), { className: 'toast_error' }) // 첨부파일을 등록해주세요.
              gridView7.setCurrent({ dataRow: i, fieldName: 'acdt_atfl_nm' })
              return false
            }
          }

          // 첨부파일 구분이 해당시 필수일 경우
          if (dataProvider7.getValue(i, 'cd_buf_1st_cntn') === 'SE') {
            // *산업재해조사표는 제출 대상 ‘Y’체크 하였을때만 필수정보로 판정
            if (this.frmData.inac_ivst_sbms_tgt_yn === 'Y' && !dataProvider7.getValue(i, 'acdt_atfl_nm')) {
              this.$toast.show(this.$t('MS00000352'), { className: 'toast_error' }) // 첨부파일을 등록해주세요.
              gridView7.setCurrent({ dataRow: i, fieldName: 'acdt_atfl_nm' })
              return false
            }
          }
        }
      }

      // 조치완료, 결재상신 일때 체크
      if (chkType >= 3) {
        // 재발방지대책 이행결과
        for (let i = 0; i < dataProvider8.getRowCount(); i++) {
          if (dataProvider8.getRowState(i) === 'createAndDeleted' || dataProvider8.getRowState(i) === 'deleted') {
            continue
          }
          if (!dataProvider8.getValue(i, 'acdt_acco_dt')) {
            gridView8.setCurrent({ dataRow: i, fieldName: 'acdt_acco_dt' })
            this.$toast.show(this.$t('MS00000279'), { className: 'toast_error' }) // 조치완료일을 입력해주세요.
            return false
          }
          if (!dataProvider8.getValue(i, 'acdt_actn_txt')) {
            gridView8.setCurrent({ dataRow: i, fieldName: 'acdt_actn_txt' })
            this.$toast.show(this.$t('MS00000348'), { className: 'toast_error' }) // 조치내용을 입력해주세요.
            return false
          }
        }
        // 개선결과 첨부파일 정보
        for (let i = 0; i < dataProvider9.getRowCount(); i++) {
          if (!dataProvider9.getValue(i, 'acdt_atfl_nm')) {
            gridView9.setCurrent({ dataRow: i, fieldName: 'acdt_atfl_nm' })
            this.$toast.show(this.$t('MS00000352'), { className: 'toast_error' }) // 첨부파일을 등록해주세요.
            return false
          }
        }
      }

      // 결재상신 일때 체크
      if (chkType >= 4) {
        // 재발방지대책 이행결과
        for (let i = 0; i < dataProvider8.getRowCount(); i++) {
          if (dataProvider8.getRowState(i) === 'createAndDeleted' || dataProvider8.getRowState(i) === 'deleted') {
            continue
          }
          if (!dataProvider8.getValue(i, 'acdt_acco_dt')) {
            gridView8.setCurrent({ dataRow: i, fieldName: 'acdt_acco_dt' })
            this.$toast.show(this.$t('MS00000279'), { className: 'toast_error' }) // 조치완료일을 입력해주세요.
            return false
          }
          if (!dataProvider8.getValue(i, 'acdt_actn_txt')) {
            gridView8.setCurrent({ dataRow: i, fieldName: 'acdt_actn_txt' })
            this.$toast.show(this.$t('MS00000348'), { className: 'toast_error' }) // 조치내용을 입력해주세요.
            return false
          }
        }
        // 개선결과 첨부파일 정보
        for (let i = 0; i < dataProvider9.getRowCount(); i++) {
          if (!dataProvider9.getValue(i, 'acdt_atfl_nm')) {
            gridView9.setCurrent({ dataRow: i, fieldName: 'acdt_atfl_nm' })
            this.$toast.show(this.$t('MS00000352'), { className: 'toast_error' }) // 첨부파일을 등록해주세요.
            return false
          }
        }
      }

      return true
    },
    async appr() {
      // 결재상신(완료) 는 결재 코드에서 수정처리

      // 결재상신(완료)
      if (this.validate(0) === false) return

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.temp_save_yn = 'N' // 임시저장여부

      // 필요 데이터 셋팅
      await this.setDataList()

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      // 사고 발생 / 재현 사진 업로드
      // 사고발생현장사진
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '01' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.occur_atfl_list.push(obj)
        } else {
          console.warn('사고발생현장사진 업로드실패') // eslint-disable-line no-console
        }
      })
      // 사고재현사진
      await this.$refs.reenactPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '02' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.reenact_atfl_list.push(obj)
        } else {
          console.warn('사고재현사진 업로드실패') // eslint-disable-line no-console
        }
      })

      this.frmData.type = 'appr'

      await this.$axios.post('/api/v1/she/sfty/acdtmgnt', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl1 + aprvno + empno
          window.open(url, 'acdtmgnt', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.replace({
            name: `acdtmgnt-acdtmgntdtllup-acdtmgntdtllup`,
            params: {
              acdtmgntdtllup: this.schData.acdt_id,
              schData: this.schData,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })

      this.modify = false
    },
    // 저장 버튼 클릭 시
    async save() {
      // 유효성 체크
      if (this.validate(0) === false) return

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 사고번호가 등록 안되어져 있을 경우
      if (this.isEmpty(this.frmData.acdt_id)) {
        this.frmData.acdt_prst_cd = '1' // 기본값 '등록중'
      }

      // 임시저장에서 저장 클릭시
      if (!this.isEmpty(this.frmData.acdt_id) && this.frmData.acdt_prst_cd === '0') {
        this.frmData.acdt_prst_cd = '1' // 기본값 '등록중'
      }

      this.frmData.temp_save_yn = 'N' // 임시저장여부

      // 필요 데이터 셋팅
      this.setDataList()

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      // 사고 발생 / 재현 사진 업로드
      // 사고발생현장사진
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '01' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.occur_atfl_list.push(obj)
        } else {
          console.warn('사고발생현장사진 업로드실패') // eslint-disable-line no-console
        }
      })
      // 사고재현사진
      await this.$refs.reenactPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '02' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.reenact_atfl_list.push(obj)
        } else {
          console.warn('사고재현사진 업로드실패') // eslint-disable-line no-console
        }
      })

      const user = this.$store.getters['auth/getUser']
      this.frmData.acdt_brie_reg_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-') // 사고보고등록일자

      this.$axios.post('/api/v1/she/sfty/acdtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.acdt_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    // 임시저장 버튼 클릭 시
    async tempSave() {
      // 임시저장 유효성 체크
      if (this.validate(0) === false) return

      // const res = await this.$confirm(
      //   '지금까지 작성한 내용을 ‘임시저장’ 하시겠습니까? <br/> 임시저장된 내용은 ‘일주일＇뒤 자동 삭제 됩니다. <br/> <p style="margin-bottom:0; color:#496176;">*삭제를 원하지 않으시면, 필수정보 입력 후 ‘저장’ 부탁 드립니다.<br/> *사고속보발송은 필수정보 입력 후 송부 가능 합니다.</p>'
      // )
      // if (!res) {
      //   return
      // }

      this.frmData.acdt_prst_cd = '0' // 코드값 '임시저장'
      this.frmData.temp_save_yn = 'Y' // 임시저장여부

      // 필요 데이터 셋팅
      await this.setDataList()

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      // 사고 발생 / 재현 사진 업로드
      // 사고발생현장사진
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '01' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.occur_atfl_list.push(obj)
        } else {
          console.warn('사고발생현장사진 업로드실패') // eslint-disable-line no-console
        }
      })
      // 사고재현사진
      await this.$refs.reenactPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '02' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.reenact_atfl_list.push(obj)
        } else {
          console.warn('사고재현사진 업로드실패') // eslint-disable-line no-console
        }
      })

      const user = this.$store.getters['auth/getUser']
      this.frmData.acdt_brie_reg_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')

      this.$axios.post('/api/v1/she/sfty/acdtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.acdt_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },

    async formDown() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '73' // doc id
      await this.$axios.get('/api/v1/common/arch-file', { params: this.schData }).then((res) => {
        // eslint-disable-next-line no-console
        if (!this.isEmpty(res.data)) {
          // 조회해온 file ID로 팝업 띄우기
          this.schData.atfile_no = res.data
          this.$refs.filepop.open(this.schData)
        } else {
          this.$toast.show(this.$t('MS00000347'), { className: 'toast_error' }) // 첨부파일이 존재하지 않습니다.
        }
      })
    },
    async delInfo() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()
      gridView4.commit()
      gridView5.commit()
      gridView6.commit()
      gridView7.commit()
      gridView8.commit()
      gridView9.commit()

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.put(`/api/v1/she/sfty/acdtmgnt/${this.frmData.acdt_id}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },

    // 조사보고완료
    async invstRprcmFuc() {
      // 조사보고완료
      if (this.validate(0) === false) return

      const res = await this.$confirm(this.$t('MS00000351')) // 확정하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.acdt_prst_cd = '5' // '조사보고완료'
      this.frmData.temp_save_yn = 'N' // 임시저장여부

      this.isComplete2 = true
      const user = this.$store.getters['auth/getUser']
      this.frmData.ivst_rprt_reg_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')

      // 필요 데이터 셋팅
      await this.setDataList()

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      // 사고 발생 / 재현 사진 업로드
      // 사고발생현장사진
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '01' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.occur_atfl_list.push(obj)
        } else {
          console.warn('사고발생현장사진 업로드실패') // eslint-disable-line no-console
        }
      })
      // 사고재현사진
      await this.$refs.reenactPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '02' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.reenact_atfl_list.push(obj)
        } else {
          console.warn('사고재현사진 업로드실패') // eslint-disable-line no-console
        }
      })

      this.$axios.post('/api/v1/she/sfty/acdtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000354')) // 확정되었습니다.
          this.frmData.acdt_id = res.data
          this.schData.acdt_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },

    // 조치완료
    async completedFuc() {
      // 조치완료
      if (this.validate(0) === false) return

      const res = await this.$confirm(this.$t('MS00000351')) // 확정하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.acdt_prst_cd = '2' // '조치완료'
      this.frmData.temp_save_yn = 'N' // 임시저장여부

      // 필요 데이터 셋팅
      await this.setDataList()

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      // 사고 발생 / 재현 사진 업로드
      // 사고발생현장사진
      await this.$refs.occurPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '01' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.occur_atfl_list.push(obj)
        } else {
          console.warn('사고발생현장사진 업로드실패') // eslint-disable-line no-console
        }
      })
      // 사고재현사진
      await this.$refs.reenactPicPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          const obj = {}
          obj.acdt_atfl_no = data // 첨부파일 번호
          obj.acdt_atfl_item_cd = '02' // 첨부파일항목코드
          obj.acdt_atfl_nm = data.file_name // 첨부파일이름
          obj.acdt_atfl_size_vl = data.file_size // 첨부파일사이즈
          this.frmData.reenact_atfl_list.push(obj)
        } else {
          console.warn('사고재현사진 업로드실패') // eslint-disable-line no-console
        }
      })

      const user = this.$store.getters['auth/getUser']
      this.frmData.btrm_rslt_reg_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')

      await this.$axios.post('/api/v1/she/sfty/acdtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000354')) // 확정되었습니다.
          this.frmData.acdt_id = res.data
          this.schData.acdt_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },

    popOpen2() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open()
    },
    popOpen3() {
      this.$refs.pop3.open()
    },
    // popOpen4(name, dataRow, fileNo) {
    //   this.popupNm = name
    //   this.dataRow = dataRow
    //   const obj = {}
    //   obj.atfile_no = fileNo
    //   this.$refs.pop4.open(obj)
    // },
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        if (this.popupNm === 'dfdt') {
          const row = gridView4.getCurrent().dataRow
          const values = {
            dfdt_pric_id: returnData[0].emp_no,
            dfdt_nm: returnData[0].emp_nm,
            dfdt_dept_nm: returnData[0].dept_nm,
            jncm_dt: returnData[0].joinc_da,
          }
          dataProvider4.updateRow(row, values)
        } else if (this.popupNm === 'actn') {
          const row = gridView6.getCurrent().dataRow
          const values = {
            acdt_actn_emp_id: returnData[0].emp_no,
            acdt_actn_emp_nm: returnData[0].emp_nm,
          }
          dataProvider6.updateRow(row, values)
        }
      }
    },
    popClose2(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'acdt_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'acdt_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    popClose3(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'kor_acdt_occr_org_nm', returnData[0].dept_nm)
        this.$set(this.frmData, 'acdt_occr_org_cd', returnData[0].dept_cd)
      }
    },
    popClose4(returnData) {
      gridView7.commit()
      gridView9.commit()

      // eslint-disable-next-line no-console
      console.log(returnData.atfile_no)
      // eslint-disable-next-line no-console
      console.log(this.dataRow)
      // eslint-disable-next-line no-console
      console.log(this.popupNm)

      if (returnData.atfile_no === 'undefined' || returnData.atfile_no === undefined) {
        return false
      } else if (this.popupNm === 'file1') {
        dataProvider7.setValue(gridView7.getCurrent().dataRow, 'acdt_atfl_no', returnData.atfile_no)
        dataProvider7.setValue(gridView7.getCurrent().dataRow, 'acdt_atfl_nm', returnData.file_list[0].name)
        dataProvider7.setValue(gridView7.getCurrent().dataRow, 'acdt_atfl_size_vl', returnData.file_list[0].size)
      } else if (this.popupNm === 'file2') {
        dataProvider9.setValue(gridView9.getCurrent().dataRow, 'acdt_atfl_no', returnData.atfile_no)
        dataProvider9.setValue(gridView9.getCurrent().dataRow, 'acdt_atfl_nm', returnData.file_list[0].name)
        dataProvider9.setValue(gridView9.getCurrent().dataRow, 'acdt_atfl_size_vl', returnData.file_list[0].size)
      }
    },
    popClose5(returnData) {
      if (returnData) {
        dataProvider8.setValue(returnData.data_row, 'acdt_acco_dt', returnData.acdt_acco_dt)
        dataProvider8.setValue(returnData.data_row, 'acdt_actn_txt', returnData.acdt_actn_txt)
        dataProvider8.setValue(returnData.data_row, 'acdt_actn_rslt_atfl_no', returnData.acdt_actn_rslt_atfl_no)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `acdtmgnt-acdtmgntlist`,
        params: {
          schData: this.schData,
        },
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
    getDateAccident() {
      const now = new Date(this.frmData.acdt_occr_dt)
      const week = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
      const dayOfWeek = week[now.getDay()]
      this.dateAccidentText = dayOfWeek
      return this.dateAccidentText
    },
    // 필요 데이터 셋팅
    setDataList() {
      // 사고사진 set
      this.frmData.occur_atfl_list = []
      this.frmData.reenact_atfl_list = []

      // 첨부파일 (목격자, 재해(피해자) 진술서, 사고조사 보고서, 수시위험성 평가)
      // 첨부파일 정보
      this.frmData.atfl_list1 = []
      for (let i = 0; i < dataProvider7.getRowCount(); i++) {
        if (dataProvider7.getRowState(i) === 'createAndDeleted' || dataProvider7.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider7.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider7.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider7.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider7.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list1.push(obj)
      }

      this.frmData.actn_list = [] // SHAMSD_사고조치계획
      for (let i = 0; i < dataProvider6.getRowCount(); i++) {
        if (dataProvider6.getRowState(i) === 'createAndDeleted' || dataProvider6.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_actn_cl_cd = dataProvider6.getValue(i, 'acdt_actn_cl_cd')
        obj.acdt_actn_plan_txt = dataProvider6.getValue(i, 'acdt_actn_plan_txt')
        obj.acdt_actn_emp_id = dataProvider6.getValue(i, 'acdt_actn_emp_id')
        obj.acdt_actn_tmlm_dt = dataProvider6.getValue(i, 'acdt_actn_tmlm_dt')
        this.frmData.actn_list.push(obj)
      }

      this.frmData.dfdt_list = [] // SHAMSM_사고자
      for (let i = 0; i < dataProvider4.getRowCount(); i++) {
        if (dataProvider4.getRowState(i) === 'createAndDeleted' || dataProvider4.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.dfdt_pric_id = dataProvider4.getValue(i, 'dfdt_pric_id')
        obj.dfdt_cl_cd = dataProvider4.getValue(i, 'dfdt_cl_cd')
        obj.dfdt_nm = dataProvider4.getValue(i, 'dfdt_nm')
        obj.dfdt_dept_nm = dataProvider4.getValue(i, 'dfdt_dept_nm')
        obj.dfdt_age = dataProvider4.getValue(i, 'dfdt_age')
        obj.disa_cl_cd = dataProvider4.getValue(i, 'disa_cl_cd')
        obj.dfdt_disa_prts_nm = dataProvider4.getValue(i, 'dfdt_disa_prts_nm')
        obj.dfdt_hspz_yn = dataProvider4.getValue(i, 'dfdt_hspz_yn')
        obj.dfdt_care_days = dataProvider4.getValue(i, 'dfdt_care_days')
        obj.dfdt_dgns_rslt_txt = dataProvider4.getValue(i, 'dfdt_dgns_rslt_txt')
        obj.dfdt_sex_cd = dataProvider4.getValue(i, 'dfdt_sex_cd')
        this.frmData.dfdt_list.push(obj)
      }

      this.frmData.ivst_list = [] // SHAMSD_사고조사결과
      for (let i = 0; i < dataProvider5.getRowCount(); i++) {
        if (dataProvider5.getRowState(i) === 'createAndDeleted' || dataProvider5.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_ivst_cl_cd = dataProvider5.getValue(i, 'acdt_ivst_cl_cd')
        obj.acdt_ivst_cat_cd = dataProvider5.getValue(i, 'acdt_ivst_cat_cd')
        obj.acdt_caus_anl_txt_cd = dataProvider5.getValue(i, 'acdt_caus_anl_txt_cd')
        this.frmData.ivst_list.push(obj)
      }

      // 사고
      this.frmData.atfl_list1 = []
      for (let i = 0; i < this.codes.SH00000061.length; i++) {
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_atfl_item_cd = this.codes.SH00000061[i].cd
        this.frmData.atfl_list1.push(obj)
      }

      this.frmData.atfl_list2 = []
      for (let i = 0; i < this.codes.SH00000062.length; i++) {
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_atfl_item_cd = this.codes.SH00000062[i].cd
        this.frmData.atfl_list2.push(obj)
      }
      // SHAMSD_사고작업내용
      this.frmData.oprn_list = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_oprn_txt_cl_cd = dataProvider.getValue(i, 'cd')
        obj.oprn_txt_chc_yn = dataProvider.getValue(i, 'chk')
        this.frmData.oprn_list.push(obj)
      }

      this.frmData.usfttxt_list = []
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.usft_txt_cl_cd = dataProvider2.getValue(i, 'cd')
        obj.usft_txt_chc_yn = dataProvider2.getValue(i, 'chk')
        this.frmData.usfttxt_list.push(obj)
      }

      this.frmData.usftst_list = []
      for (let i = 0; i < dataProvider3.getRowCount(); i++) {
        if (dataProvider3.getRowState(i) === 'createAndDeleted' || dataProvider3.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.usft_st_cd = dataProvider3.getValue(i, 'cd')
        obj.usft_txt_chc_yn = dataProvider3.getValue(i, 'chk')
        this.frmData.usftst_list.push(obj)
      }

      // 재발방지대책 이행결과
      this.frmData.actn_list2 = []
      for (let i = 0; i < dataProvider8.getRowCount(); i++) {
        if (dataProvider8.getRowState(i) === 'createAndDeleted' || dataProvider8.getRowState(i) === 'deleted') {
          continue
        }
        const obj = {}
        obj.wkpl_id = this.frmData.wkpl_id
        obj.acdt_actn_cl_cd = dataProvider8.getValue(i, 'acdt_actn_cl_cd')
        obj.acdt_actn_plan_txt = dataProvider8.getValue(i, 'acdt_actn_plan_txt')
        obj.acdt_actn_tmlm_dt = dataProvider8.getValue(i, 'acdt_actn_tmlm_dt')
        obj.acdt_acco_dt = dataProvider8.getValue(i, 'acdt_acco_dt')
        obj.acdt_actn_txt = dataProvider8.getValue(i, 'acdt_actn_txt')
        obj.acdt_actn_rslt_atfl_no = dataProvider8.getValue(i, 'acdt_actn_rslt_atfl_no')
        this.frmData.actn_list2.push(obj)
      }

      // 개선결과 첨부파일 정보
      this.frmData.atfl_list2 = []
      for (let i = 0; i < dataProvider9.getRowCount(); i++) {
        const obj = {}
        obj.acdt_atfl_item_cd = dataProvider9.getValue(i, 'cd')
        obj.acdt_atfl_nm = dataProvider9.getValue(i, 'acdt_atfl_nm')
        obj.acdt_atfl_size_vl = dataProvider9.getValue(i, 'acdt_atfl_size_vl')
        obj.acdt_atfl_no = dataProvider9.getValue(i, 'acdt_atfl_no')
        this.frmData.atfl_list2.push(obj)
      }
    },
  },
}
</script>
<style scoped>
.centered-input >>> input {
  text-align: center;
}
</style>
