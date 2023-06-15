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
        <!-- 작업계획 기본정보-->
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
                <!-- 작업 사업장-->
                <div class="label_tit essn">{{ $t('LB00001299') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 회사 -->
                <v-select
                  ref="cmpy_cd"
                  v-model="frmData.cmpy_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify || !wkplModify"
                  @change="codChg"
                ></v-select>
                &nbsp;
                <!-- 사업장 -->
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify || !wkplModify"
                  @change="wkplChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--작업 요청자-->
                <div class="label_tit essn">{{ $t('LB00000864') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_rq_emp_id"
                  v-model="frmData.con_rq_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popOpen('main')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업 계획기간-->
                <div class="label_tit essn">{{ $t('LB00000865') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="clickChk" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="con_range_dt"
                      v-model="conRangeDtChange"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      :disabled="!apprModify"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.con_range_dt" range no-title @click="menu1 = true"> </v-date-picker>
                </v-menu>
                <!-- 주중-->
                <v-checkbox
                  ref="wekd_con_yn"
                  v-model="c_wekd_con_yn"
                  :label="$t('LB00000866')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('wekd')"
                ></v-checkbox>
                <!-- 주말/휴일-->
                <v-checkbox
                  ref="wknd_con_yn"
                  v-model="c_wknd_con_yn"
                  :label="$t('LB00001333')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('wknd')"
                ></v-checkbox>
                <!-- 야간-->
                <v-checkbox
                  ref="nght_con_yn"
                  v-model="c_nght_con_yn"
                  :label="$t('LB00000868')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('nght')"
                ></v-checkbox>
              </v-col>
              <v-col cols="1">
                <!--작업 주관부서-->
                <div class="label_tit">{{ $t('LB00000869') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_rspn_org_cd"
                  v-model="frmData.con_rspn_org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popDeptOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업명-->
                <div class="label_tit essn">{{ $t('LB00000636') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="con_nm"
                  v-model="frmData.con_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--긴급작업 여부-->
                <div class="label_tit essn">{{ $t('LB00000870') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="ugnc_con_yn"
                  v-model="frmData.ugnc_con_yn"
                  label="Y"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!apprModify"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--협력사명-->
                <div class="label_tit essn">{{ $t('LB00000699') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_id"
                  v-model="frmData.otsr_con_bsen_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  :disabled="!apprModify"
                  readonly
                  @click="popBsendOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--협력사 작업책임자-->
                <div class="label_tit essn">{{ $t('LB00000871') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_pric_nm"
                  v-model="frmData.otsr_con_bsen_pric_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--협력사 담당자 전화번호-->
                <div class="label_tit">{{ $t('LB00000872') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_pric_tphn"
                  v-model="frmData.otsr_con_bsen_pric_tphn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="20"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업목적-->
                <div class="label_tit essn">{{ $t('LB00001321') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="con_txt" v-model="frmData.con_txt" outlined :hide-details="true" auto-grow rows="3" maxlength="2000" :disabled="!apprModify" dense></v-textarea>
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
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--초기화-->
          <v-btn color="" outlined height="30" @click="resetReq()">{{ $t('LB00000029') }}</v-btn>
        </div>
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
                <div class="label_tit essn">{{ $t('LB00000878') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <v-menu v-model="menu3" :close-on-content-click="false" :nudge-right="40" min-width="100px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="con_dt"
                      v-model="conDtChange"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      :disabled="!apprModify"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="reqData.con_dt" :min="frmData.con_bgn_dt" :max="frmData.con_end_dt" no-title @input="menu3 = false"></v-date-picker>
                </v-menu>
                <v-select
                  ref="con_bgn_hm_h"
                  v-model="reqData.con_bgn_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="con_bgn_hm_s"
                  v-model="reqData.con_bgn_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;~&nbsp;</div>
                <v-select
                  ref="con_end_hm_h"
                  v-model="reqData.con_end_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="con_end_hm_s"
                  v-model="reqData.con_end_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!apprModify"
                  @change="hourChg()"
                ></v-select>
                <!-- 주중-->
                <v-checkbox
                  ref="wekd_con_yn_req"
                  v-model="c_wekd_con_yn_req"
                  :label="$t('LB00000866')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('wekdreq')"
                ></v-checkbox>
                <!-- 주말/휴일-->
                <v-checkbox
                  ref="wknd_con_yn_req"
                  v-model="c_wknd_con_yn_req"
                  :label="$t('LB00001333')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('wkndreq')"
                ></v-checkbox>
                <!-- 야간-->
                <v-checkbox
                  ref="nght_con_yn_req"
                  v-model="c_nght_con_yn_req"
                  :label="$t('LB00000868')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="conChk('nghtreq')"
                ></v-checkbox>
                &nbsp;&nbsp;
                <!-- 기존 작업 불러오기-->
                <v-btn color="" outlined height="30" class="ml-1" :disabled="!apprModify || wkplModify" @click="prxsPop()">{{ $t('LB00000929') }}</v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업종류-->
                <div class="label_tit essn">{{ $t('LB00000880') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!-- 화기-->
                <v-checkbox
                  ref="h_yn"
                  v-model="c_h_yn"
                  :label="$t('LB00000881')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 고소-->
                <v-checkbox
                  ref="g_yn"
                  v-model="c_g_yn"
                  :label="$t('LB00000882')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 밀폐-->
                <v-checkbox
                  ref="m_yn"
                  v-model="c_m_yn"
                  :label="$t('LB00000883')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 전기차단-->
                <v-checkbox
                  ref="j_yn"
                  v-model="c_j_yn"
                  :label="$t('LB00000884')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 유해/위험물질-->
                <v-checkbox
                  ref="u_yn"
                  v-model="c_u_yn"
                  :label="$t('LB00000885')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 중장비사용-->
                <v-checkbox
                  ref="v_yn"
                  v-model="c_v_yn"
                  :label="$t('LB00000886')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
                <!-- 일반-->
                <v-checkbox
                  ref="n_yn"
                  v-model="c_n_yn"
                  :label="$t('LB00000887')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="!apprModify"
                  @change="oprnSlct()"
                ></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--작업위치-->
                <div class="label_tit essn">{{ $t('LB00000888') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_oprn_wkpl_loc_id"
                  v-model="reqData.con_oprn_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popLocOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_oprn_dtl_loc_nm"
                  v-model="reqData.con_oprn_dtl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  :disabled="!apprModify"
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--PSM대상-->
                <div class="label_tit essn">{{ $t('LB00000889') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="psm_tgt_yn"
                  v-model="reqData.psm_tgt_yn"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  :disabled="!apprModify"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관리감독자-->
                <div class="label_tit essn">{{ $t('LB00000890') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_supe_emp_id"
                  v-model="reqData.con_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popOpen('supe')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--작업 담당자-->
                <div class="label_tit essn">{{ $t('LB00000891') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_oprn_supe_emp_id"
                  v-model="reqData.con_oprn_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popOpen('oprn')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--안전관리(담당)자-->
                <div class="label_tit essn">{{ $t('LB00000892') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="con_sft_rspn_emp_id"
                  v-model="reqData.con_sft_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  :disabled="!apprModify"
                  @click="popOpen('sft')"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--당일작업내용-->
                <div class="label_tit essn">{{ $t('LB00001322') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="con_oprn_txt"
                  v-model="reqData.con_oprn_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  auto-grow
                  rows="3"
                  maxlength="2000"
                  :disabled="!apprModify"
                  dense
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--기타 주의사항-->
                <div class="label_tit">{{ $t('LB00000893') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="oth_prct_txt"
                  v-model="reqData.oth_prct_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  auto-grow
                  rows="3"
                  maxlength="2000"
                  :disabled="!apprModify"
                  dense
                ></v-textarea>
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
                <div>
                  <!--추가-->
                  <v-btn color="" outlined height="30" @click="oprrAdd()">{{ $t('LB00000047') }}</v-btn>
                  <!--삭제-->
                  <v-btn color="" outlined height="30" @click="oprrDel()">{{ $t('LB00000046') }}</v-btn>
                </div>
              </div>
              <div id="realgrid2" style="width: 100%; height: 481px"></div>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header"></div>
              <div class="sub_title" style="display: flex">
                <!-- 작업 계획 첨부파일 정보-->
                <h3>{{ $t('LB00000897') }}</h3>
                &nbsp; &nbsp;
                <div class="label_tit essn">
                  <!-- ! 작업계획서 작성 대상 작업 -->
                  {{ $t('LB00001334') }}
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                    </template>
                    <p>
                      <!-- ■ 산업안전보건기준에 관한 규칙  -->
                      {{ $t('LB00001323') }} <br />
                      <!-- 차량계 하역운반기계 등을 사용하는 작업 -->
                      <span>· {{ $t('LB00001324') }}</span>
                      <br />
                      <!-- 차량계 건설기계를 사용하는 작업 -->
                      <span>· {{ $t('LB00001325') }}</span>
                      <br />
                      <!-- 화학설비와 그 부속설비 사용작업 -->
                      <span>· {{ $t('LB00001326') }}</span>
                      <br />
                      <!-- 제318조에 따른 전기작업 -->
                      <span>· {{ $t('LB00001327') }}</span>
                      <br />
                      <!-- 굴착작업 -->
                      <span>· {{ $t('LB00001328') }}</span>
                      <br />
                      <!-- 건물 등의 해체작업 -->
                      <span>· {{ $t('LB00001329') }}</span>
                      <br />
                      <!-- 중량물의 취급작업 -->
                      <span>· {{ $t('LB00001330') }}</span>
                      <br />
                      <!-- 이외 타워크레인 작업 등 자사 사업특성을 반영하여 일부 작업 미기재-->
                      <span>· {{ $t('LB00001331') }}</span>
                    </p>
                  </v-tooltip>
                </div>
                <v-spacer></v-spacer>
                <div>
                  <!-- 양식 다운로드 -->
                  <v-btn style="bottom: 3px" color="" outlined height="27" @click="formDown1">{{ $t('LB00000721') }}</v-btn>
                </div>
              </div>
              <div class="container_filelist" style="max-height: 100px">
                <table>
                  <div v-if="true">
                    <file-upload ref="upload" input-id="file1" :multiple="true" @onDrop="chkFile()" />
                  </div>
                </table>
                <div class="group_padd"></div>
              </div>
            </div>
          </v-card>
        </v-tab-item>
      </v-tabs>
      <br />
      <div class="container_detail">
        <div class="grid_header">
          <div class="grid_header_left"></div>
          <div>
            <!--작업 미실시 처리-->
            <v-btn v-if="undoModify" depressed width="120" color="primary" height="40" @click="oprnUndo()">{{ $t('LB00001354') }}</v-btn>
            <!--허가서출력-->
            <v-btn v-if="printModify" depressed width="120" color="primary" height="40" @click="getReport()">{{ $t('LB00000930') }}</v-btn>
            <!--결재상신-->
            <v-btn v-if="apprModify" depressed width="120" color="primary" height="40" @click="appr()">{{ $t('LB00000763') }}</v-btn>
            <!--삭제-->
            <v-btn v-if="deleteModify" depressed width="120" color="primary" height="40" @click="del()">{{ $t('LB00000046') }}</v-btn>
            <!--저장-->
            <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
            <!--목록-->
            <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
          </div>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="cstprmsmgntdtledtuserslct1" mkey2="cstprmsmgntdtledtuserslct2" @close="popclose" />
    <locslct ref="locpop" mkey="cstprmsmgntdtledt1" :iswkpl="false" :wkplid="frmData.wkpl_id" @close="popLocClose" />
    <deptslct ref="deptpop" mkey="cstprmsmgntdtledt1" @close="popDeptClose" />
    <bsendslct ref="bsendpop" mkey="cstprmsmgntdtledt1" @close="popBsendClose" />
    <bsenduserslct ref="bsenduserpop" mkey="cstprmsmgntdtledt1" @close="popBsendUserClose" />
    <prxsoprnldslct ref="prxsoprnldpop" mkey="cstprmsmgntdtledt1" @close="popPrxsoprnldClose" />
    <filePopup ref="filepop" mkey1="cstprmsmgntdtledtfilepop1" :edit-chk="false" />
    <conOprnUndo ref="undopop" mkey="cstprmsmgntdtledtconOprnUndo" @close="oprnUndoClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import locslct from '../../common/rgnslct'
import deptslct from '../../common/deptslct'
import bsendslct from '../../common/bsendslct'
import bsenduserslct from '../../common/bsenduserslct'
import prxsoprnldslct from '../prxsoprnld'
import FileUpload from '../../../components/FileUpload.vue'
import filePopup from '../../common/atflgrid'
import conOprnUndo from '../conOprnUndo'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let dateVal = new Date()

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '작업허가 관리',
    key(route) {
      return `/cstprms/cstprmsmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    FileUpload,
    locslct,
    deptslct,
    bsendslct,
    bsenduserslct,
    prxsoprnldslct,
    filePopup,
    conOprnUndo,
  },
  data() {
    return {
      quillChk: false,
      wkplModify: false,
      apprModify: false,
      printModify: false,
      deleteModify: false,
      pageMoveAppr: false,
      isAdminGrp: false,
      undoModify: false,
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      codeid: ['CO00000009', 'ST00000002', 'SH00000074'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000002p: [{ cd: '', cd_nm: '' }],
        SH00000074: [{ cd: '', cd_nm: '' }],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      mm5: [
        { cd: '00', cd_nm: '00' },
        { cd: '05', cd_nm: '05' },
        { cd: '10', cd_nm: '10' },
        { cd: '15', cd_nm: '15' },
        { cd: '20', cd_nm: '20' },
        { cd: '25', cd_nm: '25' },
        { cd: '30', cd_nm: '30' },
        { cd: '35', cd_nm: '35' },
        { cd: '40', cd_nm: '40' },
        { cd: '45', cd_nm: '45' },
        { cd: '50', cd_nm: '50' },
        { cd: '55', cd_nm: '55' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
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
      chkRowVal: 0,
      userPopGb: '',
      conId: '',
      prmsId: '',
      frmTempData: {},
      reqTempData: {},
      oprnTempData: {},
      oprnTempDataChk1: {},
      oprnTempDataChk2: {},
      oprnChkSet: {},
      oprnChgData: {},
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
      con_hm_yn: false,
      selectOprnOrg: '',
      frmData: {
        ckFlag: false,
        con_id: '',
        cmpy_cd: '',
        wkpl_id: '',
        wkpl_nm: '',
        con_rq_emp_id: '',
        con_rq_emp_nm: '',
        con_range_dt: [],
        con_bgn_dt: '',
        con_end_dt: '',
        wekd_con_yn: '',
        wknd_con_yn: '',
        nght_con_yn: '',
        con_rspn_org_cd: '',
        con_rspn_org_nm: '',
        con_nm: '',
        ugnc_con_yn: 'N',
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
        con_bgn_hm_h: '',
        con_bgn_hm_s: '',
        con_end_hm_h: '',
        con_end_hm_s: '',
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
        crt_emp_no: '',
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
    // 작업계획기간 입력
    conRangeDtChange() {
      const date = this.frmData.con_range_dt
      if (date.length === 2) {
        const orderedDate = date.sort().join(' ~ ')

        // 작업계획기간 체크박스 설정
        this.conRangeDtSet()
        return orderedDate
      }

      return ''
    },
    // 작업계획기간 달력클릭 시
    clickChk() {
      if (this.frmData.con_range_dt.length === 2) {
        return true
      } else {
        return false
      }
    },
    // 작업일시 날짜 변경 시
    conDtChange() {
      const date = this.reqData.con_dt

      // 작업일시 체크박스 설정
      this.conDtSet()
      return date
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    console.log(this.user) // eslint-disable-line no-console
    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(this.user.time_zone)
    sDate = this.getTzDateObj(this.user.time_zone)
    sDate.setDate(sDate.getDate() - 30)
    this.frmData.sDate = this.get_date_str_gubun(sDate, '-')
    this.frmData.eDate = this.get_date_str_gubun(eDate, '-')
    if (this.$route.params.cstprmsmgntdtledt !== null && this.$route.params.cstprmsmgntdtledt !== undefined) {
      this.schData.key_id = this.$route.params.cstprmsmgntdtledt
      this.wkplModify = false
      this.schData.auth_slct = 'Y'
    } else {
      this.schData.key_id = ''
      this.frmData.con_id = ''
      this.frmData.con_prms_id = ''
      this.wkplModify = true
      this.reqData.con_req_prst_cd = 'S01'
      this.c_wekd_con_yn = true
      this.c_wekd_con_yn_req = true
      this.frmData.cmpy_cd = this.user.cmpn_cd
      this.frmData.wkpl_id = this.user.wkpl_id
      this.apprModify = true
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

    dateVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.con_bgn_dt = this.get_date_str_gubun(dateVal, '-')
    const lastDay = new Date(dateVal.getFullYear(), dateVal.getMonth(), dateVal.getDate() + 7, 0)
    this.frmData.con_end_dt = this.get_date_str_gubun(lastDay, '-')
    this.reqData.con_dt = this.get_date_str_gubun(dateVal, '-')
    this.frmData.con_range_dt[0] = this.frmData.con_bgn_dt
    this.frmData.con_range_dt[1] = this.frmData.con_end_dt
    this.frmData.con_rq_emp_id = this.user.emp_no
    this.frmData.con_rq_emp_nm = this.user.emp_nm
  },
  mounted() {
    // 코드 조회
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 사업장 조회
    this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
      const tempWkpl = []
      res.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm, up_cd: e.up_cd })
      })
      this.FF_00000001 = tempWkpl
      this.F_codes.F_00000001 = tempWkpl
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT' })
      if (this.frmData.wkpl_id === 'S03') {
        this.reqData.psm_tgt_yn = 'Y'
      } else {
        this.reqData.psm_tgt_yn = ''
      }
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
          editable: true,
          renderer: {
            type: 'check',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const gubun = grid.getValue(dataCell.index.itemIndex, 'con_req_prst_cd')
            if (gubun !== 'S01') {
              ret.editable = false
              ret.renderer = {
                type: 'check',
                editable: false,
              }
            } else {
              ret.editable = false
              // ret.renderer = {
              //   type: 'check',
              //   editable: false,
              // }
            }
            return ret
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
      gridView.editOptions.editable = true
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
      gridView2.setCheckBar({ visible: true })
      gridView2.editOptions.editable = true
      gridView2.displayOptions.selectionStyle = 'block'

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝

      // key_id가 있다면 수정모드
      if (this.schData.key_id !== '') {
        this.select('N')
      }
    })
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    gridView2.commit()
    this.oprnChgData.chk = []
    if (this.c_h_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD01' })
    }
    if (this.c_g_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD02' })
    }
    if (this.c_m_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD03' })
    }
    if (this.c_j_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD04' })
    }
    if (this.c_u_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD05' })
    }
    if (this.c_v_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD06' })
    }
    if (this.c_n_yn === true) {
      this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD07' })
    }

    if (!this.wkplModify && !this.pageMoveAppr) {
      if (
        dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() ||
        dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData) ||
        JSON.stringify(this.reqData) !== JSON.stringify(this.reqTempData) ||
        JSON.stringify(this.oprnChgData) !== JSON.stringify(this.oprnTempData)
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
    allowedStep: (m) => m % 5 === 0,
    oprrAdd() {
      gridView2.commit()
      if (this.isEmpty(this.frmData.otsr_con_bsen_id)) {
        this.$toast.show(this.$t('MS00000418'), { className: 'toast_error' }) // 협력사를 입력해주세요.
        return
      }
      this.$refs.bsenduserpop.open(this.frmData, true)
    },
    // 작업계획기간 설정
    conRangeDtSet() {
      this.frmData.con_bgn_dt = this.frmData.con_range_dt[0]
      this.frmData.con_end_dt = this.frmData.con_range_dt[1]
      const chkDate = {
        bgn_dt: this.frmData.con_range_dt[0],
        end_dt: this.frmData.con_range_dt[1],
      }
      this.$axios
        .$get('/api/v1/she/cstprms/chkDt', { params: chkDate })
        .then((res) => {
          if (res.wekdyn === 'Y') {
            this.c_wekd_con_yn = true
          } else {
            this.c_wekd_con_yn = false
          }
          if (res.holiyn === 'Y') {
            this.c_wknd_con_yn = true
          } else {
            this.c_wknd_con_yn = false
          }
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    // 작업일시 설정
    conDtSet() {
      const chkDate = {
        bgn_dt: this.reqData.con_dt,
        end_dt: this.reqData.con_dt,
      }
      this.$axios
        .$get('/api/v1/she/cstprms/chkDt', { params: chkDate })
        .then((res) => {
          if (res.wekdyn === 'Y') {
            this.c_wekd_con_yn_req = true
          } else {
            this.c_wekd_con_yn_req = false
          }
          if (res.holiyn === 'Y') {
            this.c_wknd_con_yn_req = true
          } else {
            this.c_wknd_con_yn_req = false
          }
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    oprrDel() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    // 작업 미실시 처리
    oprnUndo() {
      this.reqData.con_rq_emp_id = this.frmData.con_rq_emp_id
      this.reqData.con_nm = this.frmData.con_nm
      this.$refs.undopop.open(this.reqData)
    },
    oprnUndoClose(returnData) {
      if (returnData !== 'Success') {
        return false
      } else {
        this.select()
      }
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
    async select(gbVal) {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-dtl/${this.schData.key_id}`)
      if (data.auth[0].auth !== 'Y') {
        this.$router.replace({
          name: `cstprms-cstprmsmgntdtllup-cstprmsmgntdtllup`,
          params: {
            cstprmsmgntdtllup: this.schData.key_id,
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

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]

        // 작업계획기간 설정
        this.frmData.con_range_dt = []
        this.frmData.con_range_dt[0] = this.frmData.con_bgn_dt
        this.frmData.con_range_dt[1] = this.frmData.con_end_dt

        console.log('frmData : ', this.frmData) // eslint-disable-line no-console
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
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
        this.frmTempData = []
      }

      if (data.req.length > 0) {
        this.reqData = data.req[0]
        // 기존 작업를 불러왔을 경우
        if (gbVal === 'B') {
          this.reqData.con_prms_id = ''
          this.reqData.con_req_prst_cd = 'S01'
          this.reqData.con_req_atfl_no = ''
        }

        this.reqTempData = JSON.parse(JSON.stringify(this.reqData)) // 수정여부 비교 데이터

        const usrinfo = this.$store.getters['auth/getUser']
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if (this.reqData.con_req_prst_cd === 'S01') {
          this.apprModify = true
          if (!this.isEmpty(this.reqData.con_prms_id) && (this.reqData.crt_emp_no === this.user.emp_no || this.isAdminGrp)) {
            this.deleteModify = true
          }
        } else {
          this.apprModify = false
        }

        if (this.reqData.con_req_prst_cd === 'S03' || this.reqData.con_req_prst_cd === 'S04') {
          this.printModify = true
        } else {
          this.printModify = false
        }

        if (this.reqData.con_req_prst_cd === 'S03') {
          this.undoModify = true
        } else {
          this.undoModify = false
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
        // this.$refs.upload2.reset()
        // if (!this.isEmpty(this.reqData.con_req_atfl_no)) {
        //   this.$axios.$get(`/api/v1/common/file/${this.reqData.con_req_atfl_no}`).then((data) => {
        //     console.log('files1 : ', data) // eslint-disable-line no-console
        //     if (data.length > 0) {
        //       this.setFiles2(data)
        //     }
        //   })
        // }
      } else {
        this.reqData = []
        this.reqTempData = []
      }

      this.oprnTempData.chk = []
      this.oprnTempDataChk1.chk = []
      if (data.oprn.length > 0) {
        this.oprnData = data.oprn[0]
        if (this.oprnData.h_yn === 'Y') {
          this.c_h_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD01' })
          this.oprnTempDataChk1.chk.push('WCD01')
        } else {
          this.c_h_yn = false
        }
        if (this.oprnData.g_yn === 'Y') {
          this.c_g_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD02' })
          this.oprnTempDataChk1.chk.push('WCD02')
        } else {
          this.c_g_yn = false
        }
        if (this.oprnData.m_yn === 'Y') {
          this.c_m_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD03' })
          this.oprnTempDataChk1.chk.push('WCD03')
        } else {
          this.c_m_yn = false
        }
        if (this.oprnData.j_yn === 'Y') {
          this.c_j_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD04' })
          this.oprnTempDataChk1.chk.push('WCD04')
        } else {
          this.c_j_yn = false
        }
        if (this.oprnData.u_yn === 'Y') {
          this.c_u_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD05' })
          this.oprnTempDataChk1.chk.push('WCD05')
        } else {
          this.c_u_yn = false
        }
        if (this.oprnData.v_yn === 'Y') {
          this.c_v_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD06' })
          this.oprnTempDataChk1.chk.push('WCD06')
        } else {
          this.c_v_yn = false
        }
        if (this.oprnData.n_yn === 'Y') {
          this.c_n_yn = true
          this.oprnTempData.chk.push({ con_oprn_type_cd: 'WCD07' })
          this.oprnTempDataChk1.chk.push('WCD07')
        } else {
          this.c_n_yn = false
        }
        this.selectOprnOrg = this.selectOprnOrg.substring(0, this.selectOprnOrg.length - 1)
      } else {
        this.oprnData = []
        this.oprnTempData = []
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
      gridView.setTopItem(0)
      this.obeyTotalcnt = dataProvider.getRowCount()

      // 수정 상태일 경우에만
      if (!this.isEmpty(this.frmData.con_id)) {
        // 이번에 체크한 작업종류가 기존에 저장된 작업종류에 없으면 분류
        let chkIdx = -1
        this.oprnChkSet = []
        for (let i = 0; i < this.oprnTempDataChk2.chk.length; i++) {
          chkIdx = this.oprnTempDataChk1.chk.indexOf(this.oprnTempDataChk2.chk[i])
          if (chkIdx === -1) {
            this.oprnChkSet.push(this.oprnTempDataChk2.chk[i])
          }
        }
        // 새로 추가한 작업종류의 작업해당 여부를 전부 체크
        gridView.commit()
        for (let i = 0; i < this.oprnChkSet.length; i++) {
          for (let j = 0; j < dataProvider.getRowCount(); j++) {
            if (dataProvider.getValue(j, 'wkpl_chec_dtl_cl_cd') === this.oprnChkSet[i]) {
              dataProvider.setValue(j, 'chk_yn', true)
            }
          }
        }
      }
      gridView.refresh()
    },
    async formDown1() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '74' // doc id
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
    async formDown2() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '75' // doc id
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
    // 작업인원 조회
    async selectOprr() {
      gridView2.commit()
      const data = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-dtl-oprr`, { params: this.schData })

      this.oprrData = data.oprr
      dataProvider2.setRows(data.oprr)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.oprrTotalcnt = dataProvider2.getRowCount()
      this.schData.otsr_con_bsen_id = null
    },
    async save() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.oprn_plan_atfl_no = data
        }
      })
      // await this.$refs.upload2.uploadStart(true).then((data) => {
      //   console.log('File Id : ', data) // eslint-disable-line no-console
      //   if (!this.isEmptyFile(data)) {
      //     this.reqData.con_req_atfl_no = data
      //   }
      // })

      this.oprnChgData.chk = []
      if (this.c_h_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD01' })
      }
      if (this.c_g_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD02' })
      }
      if (this.c_m_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD03' })
      }
      if (this.c_j_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD04' })
      }
      if (this.c_u_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD05' })
      }
      if (this.c_v_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD06' })
      }
      if (this.c_n_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD07' })
      }

      if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        JSON.stringify(this.reqData) === JSON.stringify(this.reqTempData) &&
        JSON.stringify(this.oprnChgData) === JSON.stringify(this.oprnTempData)
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.oprn_plan_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.oprn_plan_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        // if (!this.isEmpty(this.reqData.con_req_atfl_no)) {
        //   this.$axios.$get(`/api/v1/common/file/${this.reqData.con_req_atfl_no}`).then((data) => {
        //     console.log('files2 : ', data) // eslint-disable-line no-console
        //     if (data.length > 0) {
        //       this.setFiles2(data)
        //     }
        //   })
        // }
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 작업준수사항
      const obeyList = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, 'chk_yn') === true) {
          const temp = {}

          temp.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
          temp.con_prms_id = this.reqData.con_prms_id
          temp.wkpl_chec_cl_cd = dataProvider.getValue(i, 'wkpl_chec_cl_cd')
          temp.wkpl_chec_dtl_cl_cd = dataProvider.getValue(i, 'wkpl_chec_dtl_cl_cd')
          temp.wkpl_chec_sn = dataProvider.getValue(i, 'wkpl_chec_sn')
          temp.wkpl_chec_in_cl_cd = dataProvider.getValue(i, 'wkpl_chec_in_cl_cd')
          temp.wkpl_chec_txt = dataProvider.getValue(i, 'wkpl_chec_txt')
          temp.mand_chec_yn = dataProvider.getValue(i, 'mand_chec_yn')
          temp.username = this.user.emp_now
          if (dataProvider.getRowState(i) !== 'deleted') {
            obeyList.push(temp)
          }
        }
      }
      this.frmData.obeyList = obeyList

      // 작업인원
      const oprrList = []
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        const temp = {}

        temp.con_prms_id = this.reqData.con_prms_id
        temp.oprr_id = dataProvider2.getValue(i, 'oprr_id')
        temp.username = this.user.emp_now
        if (dataProvider2.getRowState(i) !== 'deleted') {
          oprrList.push(temp)
        }
      }
      this.frmData.oprrList = oprrList

      // 기본정보
      const infoRow = []
      const temp = {}
      let conBgnDtVal = this.frmData.con_bgn_dt
      if (!this.isEmpty(this.frmData.con_bgn_dt)) {
        conBgnDtVal = conBgnDtVal.replace(/-/gi, '')
      }
      let conEndDtVal = this.frmData.con_end_dt
      if (!this.isEmpty(this.frmData.con_end_dt)) {
        conEndDtVal = conEndDtVal.replace(/-/gi, '')
      }
      if (this.c_wekd_con_yn === true) {
        this.frmData.wekd_con_yn = 'Y'
      } else {
        this.frmData.wekd_con_yn = ''
      }
      if (this.c_wknd_con_yn === true) {
        this.frmData.wknd_con_yn = 'Y'
      } else {
        this.frmData.wknd_con_yn = ''
      }
      if (this.c_nght_con_yn === true) {
        this.frmData.nght_con_yn = 'Y'
      } else {
        this.frmData.nght_con_yn = ''
      }
      temp.con_id = this.frmData.con_id
      temp.wkpl_id = this.frmData.wkpl_id
      temp.con_rq_emp_id = this.frmData.con_rq_emp_id
      temp.con_bgn_dt = conBgnDtVal
      temp.con_end_dt = conEndDtVal
      temp.wekd_con_yn = this.frmData.wekd_con_yn
      temp.wknd_con_yn = this.frmData.wknd_con_yn
      temp.nght_con_yn = this.frmData.nght_con_yn
      temp.con_rspn_org_cd = this.frmData.con_rspn_org_cd
      temp.con_nm = this.frmData.con_nm
      temp.ugnc_con_yn = this.frmData.ugnc_con_yn
      temp.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id
      temp.otsr_con_bsen_pric_nm = this.frmData.otsr_con_bsen_pric_nm
      temp.otsr_con_bsen_pric_tphn = this.frmData.otsr_con_bsen_pric_tphn
      temp.con_txt = this.frmData.con_txt
      temp.oprn_plan_atfl_no = this.frmData.oprn_plan_atfl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 허가정보
      const reqRow = []
      const tempReq = {}
      let conDtVal = this.reqData.con_dt
      if (!this.isEmpty(this.reqData.con_dt)) {
        conDtVal = conDtVal.replace(/-/gi, '')
      }
      if (this.c_wekd_con_yn_req === true) {
        this.reqData.wekd_con_yn = 'Y'
      } else {
        this.reqData.wekd_con_yn = ''
      }
      if (this.c_wknd_con_yn_req === true) {
        this.reqData.wknd_con_yn = 'Y'
      } else {
        this.reqData.wknd_con_yn = ''
      }
      if (this.c_nght_con_yn_req === true) {
        this.reqData.nght_con_yn = 'Y'
      } else {
        this.reqData.nght_con_yn = ''
      }
      let stCd = ''
      if (this.reqData.con_req_prst_cd !== 'S01') {
        stCd = this.reqData.con_req_prst_cd
      } else {
        stCd = 'S01'
      }
      tempReq.con_id = this.reqData.con_id
      tempReq.con_prms_id = this.reqData.con_prms_id
      tempReq.con_rq_emp_id = this.reqData.con_rq_emp_id
      tempReq.con_dt = conDtVal
      tempReq.wekd_con_yn = this.reqData.wekd_con_yn
      tempReq.wknd_con_yn = this.reqData.wknd_con_yn
      tempReq.nght_con_yn = this.reqData.nght_con_yn
      tempReq.con_bgn_hm = this.reqData.con_bgn_hm_h.concat(this.reqData.con_bgn_hm_s)
      tempReq.con_end_hm = this.reqData.con_end_hm_h.concat(this.reqData.con_end_hm_s)
      tempReq.psm_tgt_yn = this.reqData.psm_tgt_yn
      tempReq.con_oprn_wkpl_loc_id = this.reqData.con_oprn_wkpl_loc_id
      tempReq.con_oprn_dtl_loc_nm = this.reqData.con_oprn_dtl_loc_nm
      tempReq.con_supe_emp_id = this.reqData.con_supe_emp_id
      tempReq.con_sft_rspn_emp_id = this.reqData.con_sft_rspn_emp_id
      tempReq.con_oprn_supe_emp_id = this.reqData.con_oprn_supe_emp_id
      tempReq.con_oprn_txt = this.reqData.con_oprn_txt
      tempReq.oth_prct_txt = this.reqData.oth_prct_txt
      tempReq.con_req_atfl_no = this.reqData.con_req_atfl_no
      tempReq.con_req_prst_cd = stCd
      tempReq.username = this.user.emp_no
      reqRow.push(tempReq)
      this.frmData.reqRow = reqRow

      // 작업정보
      const oprnRow = []
      if (this.c_h_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD01'
        oprnRow.push(temp)
      }
      if (this.c_g_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD02'
        oprnRow.push(temp)
      }
      if (this.c_m_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD03'
        oprnRow.push(temp)
      }
      if (this.c_j_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD04'
        oprnRow.push(temp)
      }
      if (this.c_u_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD05'
        oprnRow.push(temp)
      }
      if (this.c_v_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD06'
        oprnRow.push(temp)
      }
      if (this.c_n_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD07'
        oprnRow.push(temp)
      }
      this.frmData.oprnRow = oprnRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false
      this.reqData.ckFlag = false

      console.log(this.frmData) // eslint-disable-line no-console

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-dtl', this.frmData).then((res) => {
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.con_hm_yn = false
          this.wkplModify = false
          this.schData.wkpl_id = this.frmData.wkpl_id
          this.schData.key_id = res.data.key1 + '-' + res.data.key2
          // this.schData.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id
          this.select('N')
          this.oprnSlct()
          this.selectOprr()
        } else if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async del() {
      gridView.commit()
      gridView2.commit()

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.delete(`/api/v1/she/cstprms/con-oprn-prms-dtl/${this.reqData.con_prms_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.oprn_plan_atfl_no = data
        }
      })
      // await this.$refs.upload2.uploadStart(true).then((data) => {
      //   console.log('File Id : ', data) // eslint-disable-line no-console
      //   if (!this.isEmptyFile(data)) {
      //     this.reqData.con_req_atfl_no = data
      //   }
      // })

      this.oprnChgData.chk = []
      if (this.c_h_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD01' })
      }
      if (this.c_g_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD02' })
      }
      if (this.c_m_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD03' })
      }
      if (this.c_j_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD04' })
      }
      if (this.c_u_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD05' })
      }
      if (this.c_v_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD06' })
      }
      if (this.c_n_yn === true) {
        this.oprnChgData.chk.push({ con_oprn_type_cd: 'WCD07' })
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.oprn_plan_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.oprn_plan_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        // if (!this.isEmpty(this.reqData.con_req_atfl_no)) {
        //   this.$axios.$get(`/api/v1/common/file/${this.reqData.con_req_atfl_no}`).then((data) => {
        //     console.log('files2 : ', data) // eslint-disable-line no-console
        //     if (data.length > 0) {
        //       this.setFiles2(data)
        //     }
        //   })
        // }
        return
      }

      // 결재 여부 확인
      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      // 작업준수사항
      const obeyList = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, 'chk_yn') === true) {
          const temp = {}

          temp.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
          temp.con_prms_id = this.reqData.con_prms_id
          temp.wkpl_chec_cl_cd = dataProvider.getValue(i, 'wkpl_chec_cl_cd')
          temp.wkpl_chec_dtl_cl_cd = dataProvider.getValue(i, 'wkpl_chec_dtl_cl_cd')
          temp.wkpl_chec_sn = dataProvider.getValue(i, 'wkpl_chec_sn')
          temp.wkpl_chec_in_cl_cd = dataProvider.getValue(i, 'wkpl_chec_in_cl_cd')
          temp.wkpl_chec_txt = dataProvider.getValue(i, 'wkpl_chec_txt')
          temp.mand_chec_yn = dataProvider.getValue(i, 'mand_chec_yn')
          temp.username = this.user.emp_now
          if (dataProvider.getRowState(i) !== 'deleted') {
            obeyList.push(temp)
          }
        }
      }
      this.frmData.obeyList = obeyList

      // 작업인원
      const oprrList = []
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        const temp = {}

        temp.con_prms_id = this.reqData.con_prms_id
        temp.oprr_id = dataProvider2.getValue(i, 'oprr_id')
        temp.username = this.user.emp_now
        if (dataProvider2.getRowState(i) !== 'deleted') {
          oprrList.push(temp)
        }
      }
      this.frmData.oprrList = oprrList

      // 기본정보
      const infoRow = []
      const temp = {}
      let conBgnDtVal = this.frmData.con_bgn_dt
      if (!this.isEmpty(this.frmData.con_bgn_dt)) {
        conBgnDtVal = conBgnDtVal.replace(/-/gi, '')
      }
      let conEndDtVal = this.frmData.con_end_dt
      if (!this.isEmpty(this.frmData.con_end_dt)) {
        conEndDtVal = conEndDtVal.replace(/-/gi, '')
      }
      if (this.c_wekd_con_yn === true) {
        this.frmData.wekd_con_yn = 'Y'
      }
      if (this.c_wknd_con_yn === true) {
        this.frmData.wknd_con_yn = 'Y'
      }
      if (this.c_nght_con_yn === true) {
        this.frmData.nght_con_yn = 'Y'
      }
      temp.con_id = this.frmData.con_id
      temp.wkpl_id = this.frmData.wkpl_id
      temp.con_rq_emp_id = this.frmData.con_rq_emp_id
      temp.con_bgn_dt = conBgnDtVal
      temp.con_end_dt = conEndDtVal
      temp.wekd_con_yn = this.frmData.wekd_con_yn
      temp.wknd_con_yn = this.frmData.wknd_con_yn
      temp.nght_con_yn = this.frmData.nght_con_yn
      temp.con_rspn_org_cd = this.frmData.con_rspn_org_cd
      temp.con_nm = this.frmData.con_nm
      temp.ugnc_con_yn = this.frmData.ugnc_con_yn
      temp.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id
      temp.otsr_con_bsen_pric_nm = this.frmData.otsr_con_bsen_pric_nm
      temp.otsr_con_bsen_pric_tphn = this.frmData.otsr_con_bsen_pric_tphn
      temp.con_txt = this.frmData.con_txt
      temp.oprn_plan_atfl_no = this.frmData.oprn_plan_atfl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 허가정보
      const reqRow = []
      const tempReq = {}
      let conDtVal = this.reqData.con_dt
      if (!this.isEmpty(this.reqData.con_dt)) {
        conDtVal = conDtVal.replace(/-/gi, '')
      }
      if (this.c_wekd_con_yn_req === true) {
        this.reqData.wekd_con_yn = 'Y'
      }
      if (this.c_wknd_con_yn_req === true) {
        this.reqData.wknd_con_yn = 'Y'
      }
      if (this.c_nght_con_yn_req === true) {
        this.reqData.nght_con_yn = 'Y'
      }
      let stCd = ''
      stCd = 'S01'
      tempReq.con_id = this.reqData.con_id
      tempReq.con_prms_id = this.reqData.con_prms_id
      tempReq.con_rq_emp_id = this.reqData.con_rq_emp_id
      tempReq.con_dt = conDtVal
      tempReq.wekd_con_yn = this.reqData.wekd_con_yn
      tempReq.wknd_con_yn = this.reqData.wknd_con_yn
      tempReq.nght_con_yn = this.reqData.nght_con_yn
      tempReq.con_bgn_hm = this.reqData.con_bgn_hm_h.concat(this.reqData.con_bgn_hm_s)
      tempReq.con_end_hm = this.reqData.con_end_hm_h.concat(this.reqData.con_end_hm_s)
      tempReq.psm_tgt_yn = this.reqData.psm_tgt_yn
      tempReq.con_oprn_wkpl_loc_id = this.reqData.con_oprn_wkpl_loc_id
      tempReq.con_oprn_dtl_loc_nm = this.reqData.con_oprn_dtl_loc_nm
      tempReq.con_supe_emp_id = this.reqData.con_supe_emp_id
      tempReq.con_sft_rspn_emp_id = this.reqData.con_sft_rspn_emp_id
      tempReq.con_oprn_supe_emp_id = this.reqData.con_oprn_supe_emp_id
      tempReq.con_oprn_txt = this.reqData.con_oprn_txt
      tempReq.oth_prct_txt = this.reqData.oth_prct_txt
      tempReq.con_req_atfl_no = this.reqData.con_req_atfl_no
      tempReq.con_req_prst_cd = stCd
      tempReq.username = this.user.emp_no
      reqRow.push(tempReq)
      this.frmData.reqRow = reqRow

      // 작업정보
      const oprnRow = []
      if (this.c_h_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD01'
        oprnRow.push(temp)
      }
      if (this.c_g_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD02'
        oprnRow.push(temp)
      }
      if (this.c_m_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD03'
        oprnRow.push(temp)
      }
      if (this.c_j_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD04'
        oprnRow.push(temp)
      }
      if (this.c_u_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD05'
        oprnRow.push(temp)
      }
      if (this.c_v_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD06'
        oprnRow.push(temp)
      }
      if (this.c_n_yn === true) {
        const temp = {}
        temp.con_prms_id = this.reqData.con_prms_id
        temp.con_oprn_type_cd = 'WCD07'
        oprnRow.push(temp)
      }
      this.frmData.oprnRow = oprnRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false
      this.reqData.ckFlag = false

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-dtl', this.frmData).then((res) => {
        if (res.data.result === 'Success') {
          this.schData.wkpl_id = this.frmData.wkpl_id
          this.schData.key_id = res.data.key1 + '-' + res.data.key2
          this.reqData.con_prms_id = res.data.key2
          this.frmData.con_id = res.data.key1
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

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-dtl-appr', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        if (res.data.Success === 'Success') {
          this.pageMoveAppr = true
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empno
          const url = config.default.approvalUrl6 + aprvno + empno
          window.open(url, 'con-oprn-prms-dtl-appr', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.replace({
            name: `cstprms-cstprmsmgntdtllup-cstprmsmgntdtllup`,
            params: {
              cstprmsmgntdtllup: this.schData.key_id,
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
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    getReport() {
      const reportdata = {
        con_id: this.frmData.con_id,
        wkpl_id: 'S00',
        con_prms_id: this.reqData.con_prms_id,
        emp_no: this.user.emp_no,
        reportForm: 'conoprnprms',
      }
      this.$axios.post(`/api/v1/comm/report/cstprms`, reportdata).then((res) => {
        const popupWidth = 900
        const popupHeight = window.screen.height - 300
        const popupX = window.screen.width / 2 - popupWidth / 2
        const popupY = window.screen.height / 2 - popupHeight / 2
        const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
        const wnd = window.open('', 'report', options)
        wnd.document.open()
        wnd.document.write(res.data)
        wnd.document.close()
        // this.print()
      })
      // const popupWidth = 900
      // const popupHeight = window.screen.height - 30
      // const popupX = window.screen.width / 2 - popupWidth / 2
      // const popupY = window.screen.height / 2 - popupHeight / 2
      // const options = 'status=no, menubar=no, toolbar=no, resizable=no,width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
      // // window.open('https://donggramisoft.co.kr/api/oz80/gwsgnprint.html?mtrl_no=' + mtrlNo + '&ozform=' + reportForm + '', 'report', options)
      // window.open('http://localhost:3000/oz80/gwsgnprint3.html?mtrl_no=60000023&ozform=5_1', 'testname', options)
    },
    async print() {
      // 허가정보
      const reqRow = []
      const tempReq = {}

      tempReq.con_id = this.reqData.con_id
      tempReq.con_prms_id = this.reqData.con_prms_id
      tempReq.username = this.user.emp_no
      reqRow.push(tempReq)
      this.frmData.infoRow = reqRow

      await this.$axios.put('/api/v1/she/cstprms/con-oprn-prms-dtl-print', this.frmData).then((res) => {
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000420')) // 출력되었습니다.
          this.select('N')
          this.oprnSlct()
          this.selectOprr()
          this.apprModify = false
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    saveValidate() {
      gridView.commit()
      gridView2.commit()
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.con_rq_emp_id) || this.isEmpty(this.frmData.con_rq_emp_nm)) {
        this.$toast.show(this.$t('MS00000444'), { className: 'toast_error' }) // 작업요청자를 입력해주세요.
        this.$refs.con_rq_emp_id.focus()
        return false
      }
      if (this.frmData.con_range_dt.length !== 2) {
        this.$toast.show(this.$t('MS00000669'), { className: 'toast_error' }) // 작업 계획기간을 입력해주세요.
        this.$refs.con_range_dt.focus()
        return false
      }
      if (this.c_wekd_con_yn === false && this.c_wknd_con_yn === false && this.c_nght_con_yn === false) {
        this.$toast.show(this.$t('MS00000441'), { className: 'toast_error' }) // 작업기간 주중/주말/야간을 선택해주세요.
        // this.$refs.wekd_con_yn.focus()
        return false
      }
      if (this.isEmpty(this.frmData.con_nm)) {
        this.$toast.show(this.$t('MS00000440'), { className: 'toast_error' }) // 작업명을 입력해주세요.
        this.$refs.con_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.con_nm) && this.frmData.con_nm.length > 200) {
        this.$toast.show(this.$t('MS00000439'), { className: 'toast_error' }) // 작업명은 200자 이하로 입력해주세요.
        this.$refs.con_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.ugnc_con_yn)) {
        this.$toast.show(this.$t('MS00000438'), { className: 'toast_error' }) // 긴급작업여부를 입력해주세요.
        this.$refs.ugnc_con_yn.focus()
        return false
      }
      if (this.isEmpty(this.frmData.otsr_con_bsen_id) || this.isEmpty(this.frmData.otsr_con_bsen_nm)) {
        this.$toast.show(this.$t('MS00000418'), { className: 'toast_error' }) // 협력사를 입력해주세요.
        this.$refs.otsr_con_bsen_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.otsr_con_bsen_pric_nm)) {
        this.$toast.show(this.$t('MS00000437'), { className: 'toast_error' }) // 협력사작업책임자를 입력해주세요.
        this.$refs.otsr_con_bsen_pric_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.otsr_con_bsen_pric_nm) && this.frmData.otsr_con_bsen_pric_nm.length > 100) {
        this.$toast.show(this.$t('MS00000436'), { className: 'toast_error' }) // 협력사작업책임자는 100자 이하로 입력해주세요.
        this.$refs.otsr_con_bsen_pric_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.con_txt)) {
        this.$toast.show(this.$t('MS00000423'), { className: 'toast_error' }) // 작업내용을 입력해주세요.
        this.$refs.con_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.con_txt) && this.frmData.con_txt.length > 2000) {
        this.$toast.show(this.$t('MS00000422'), { className: 'toast_error' }) // 작업내용은 2000자 이하로 입력해주세요.
        this.$refs.con_txt.focus()
        return false
      }

      if (this.isEmpty(this.reqData.con_dt)) {
        this.$toast.show(this.$t('MS00000435'), { className: 'toast_error' }) // 작업일자를 입력해주세요.
        this.$refs.con_dt.focus()
        return false
      }
      if (this.frmData.con_bgn_dt > this.reqData.con_dt || this.reqData.con_dt > this.frmData.con_end_dt) {
        this.$toast.show(this.$t('MS00000434'), { className: 'toast_error' }) // 작업일자는 작업기간안에서 입력해주세요.
        this.$refs.con_dt.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_bgn_hm_h) || this.isEmpty(this.reqData.con_bgn_hm_s)) {
        this.$toast.show(this.$t('MS00000433'), { className: 'toast_error' }) // 작업시작시간을 입력해주세요.
        this.$refs.con_bgn_hm_h.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_end_hm_h) || this.isEmpty(this.reqData.con_end_hm_s)) {
        this.$toast.show(this.$t('MS00000432'), { className: 'toast_error' }) // 작업종료시간을 입력해주세요.
        this.$refs.con_end_hm_h.focus()
        return false
      }
      if (this.hourChg() === true) {
        this.$toast.show(this.$t('MS00000671'), { className: 'toast_error' }) // 작업종료 시간을 작업시작 시간전으로 설정할 수 없습니다.
        this.$refs.con_bgn_hm_h.focus()
        return false
      }
      if (this.c_wekd_con_yn_req === false && this.c_wknd_con_yn_req === false && this.c_nght_con_yn_req === false) {
        this.$toast.show(this.$t('MS00000431'), { className: 'toast_error' }) // 작업허가 작업일시 주중/주말/야간을 선택해주세요.
        return false
      }
      if (
        this.c_h_yn === false &&
        this.c_g_yn === false &&
        this.c_m_yn === false &&
        this.c_j_yn === false &&
        this.c_u_yn === false &&
        this.c_v_yn === false &&
        this.c_n_yn === false
      ) {
        this.$toast.show(this.$t('MS00000430'), { className: 'toast_error' }) // 작업종류를 입력해주세요.
        return false
      }
      if (this.isEmpty(this.reqData.con_oprn_wkpl_loc_id) || this.isEmpty(this.reqData.con_oprn_wkpl_loc_nm)) {
        this.$toast.show(this.$t('MS00000429'), { className: 'toast_error' }) // 작업위치를 입력해주세요.
        this.$refs.con_oprn_wkpl_loc_id.focus()
        return false
      }
      if (!this.isEmpty(this.reqData.con_oprn_dtl_loc_nm) && this.reqData.con_oprn_dtl_loc_nm.length > 200) {
        this.$toast.show(this.$t('MS00000428'), { className: 'toast_error' }) // 상세위치는 200자 이하로 입력해주세요.
        this.$refs.con_oprn_dtl_loc_nm.focus()
        return false
      }
      if (this.isEmpty(this.reqData.psm_tgt_yn)) {
        this.$toast.show(this.$t('MS00000427'), { className: 'toast_error' }) // PSM대상을 입력해주세요.
        this.$refs.psm_tgt_yn.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_supe_emp_id) || this.isEmpty(this.reqData.con_supe_emp_nm)) {
        this.$toast.show(this.$t('MS00000426'), { className: 'toast_error' }) // 관리감독자를 입력해주세요.
        this.$refs.con_supe_emp_id.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_oprn_supe_emp_id) || this.isEmpty(this.reqData.con_oprn_supe_emp_nm)) {
        this.$toast.show(this.$t('MS00000425'), { className: 'toast_error' }) // 작업담당자를 입력해주세요.
        this.$refs.con_oprn_supe_emp_id.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_sft_rspn_emp_id) || this.isEmpty(this.reqData.con_sft_rspn_emp_nm)) {
        this.$toast.show(this.$t('MS00000424'), { className: 'toast_error' }) // 안전관리(담당)자를 입력해주세요.
        this.$refs.con_sft_rspn_emp_id.focus()
        return false
      }
      if (this.isEmpty(this.reqData.con_oprn_txt)) {
        this.$toast.show(this.$t('MS00000423'), { className: 'toast_error' }) // 작업내용을 입력해주세요.
        this.$refs.con_oprn_txt.focus()
        return false
      }
      if (!this.isEmpty(this.reqData.con_oprn_txt) && this.reqData.con_oprn_txt.length > 2000) {
        this.$toast.show(this.$t('MS00000422'), { className: 'toast_error' }) // 작업내용은 2000자 이하로 입력해주세요.
        this.$refs.con_oprn_txt.focus()
        return false
      }
      if (!this.isEmpty(this.reqData.oth_prct_txt) && this.reqData.oth_prct_txt.length > 2000) {
        this.$toast.show(this.$t('MS00000421'), { className: 'toast_error' }) // 기타 주의사항은 2000자 이하로 입력해주세요.
        this.$refs.oth_prct_txt.focus()
        return false
      }
      return true
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(true)
        this.$refs.upload.setDelete(true)
        this.changeEditMode(true)
      })
    },
    // setFiles2(files) {
    //   this.$nextTick(() => {
    //     this.$refs.upload2.setFiles(files)
    //     this.$refs.upload2.setEdit(false)
    //     this.$refs.upload2.setDelete(false)
    //     this.changeEditMode(true)
    //   })
    // },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
      // this.$refs.upload2.setEdit(mode)
    },
    // 초기화 버튼 클릭
    resetReq() {
      this.reqData = {}
      this.reqData.con_id = this.frmData.con_id
      this.reqData.con_req_prst_cd = 'S01'
      this.c_wekd_con_yn_req = true
      this.c_wknd_con_yn_req = false
      this.c_nght_con_yn_req = false
      this.apprModify = true
      this.undoModify = false
      this.printModify = false
      this.deleteModify = false
      this.schData.key_id = ''

      this.schData.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id

      this.selectOprr()
      // this.$refs.upload2.reset()

      // 작업종류
      this.oprnData = {}
      this.c_h_yn = false
      this.c_g_yn = false
      this.c_m_yn = false
      this.c_j_yn = false
      this.c_u_yn = false
      this.c_v_yn = false
      this.c_n_yn = false

      // 작업준수사항
      this.obeyData = {}
      dataProvider.setRows(this.obeyData)
      gridView.refresh()
      gridView.setTopItem(0)
      this.obeyTotalcnt = dataProvider.getRowCount()

      // 작업인원
      this.oprrData = {}
      dataProvider2.setRows(this.oprrData)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.oprrTotalcnt = dataProvider2.getRowCount()
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
      this.oprnTempDataChk2.chk = []
      if (this.c_h_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD01',"
        this.oprnTempDataChk2.chk.push('WCD01')
      }
      if (this.c_g_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD02',"
        this.oprnTempDataChk2.chk.push('WCD02')
      }
      if (this.c_m_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD03',"
        this.oprnTempDataChk2.chk.push('WCD03')
      }
      if (this.c_j_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD04',"
        this.oprnTempDataChk2.chk.push('WCD04')
      }
      if (this.c_u_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD05',"
        this.oprnTempDataChk2.chk.push('WCD05')
      }
      if (this.c_v_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD06',"
        this.oprnTempDataChk2.chk.push('WCD06')
      }
      if (this.c_n_yn === true) {
        this.selectOprn = this.selectOprn + "'WCD07',"
        this.oprnTempDataChk2.chk.push('WCD07')
      }
      this.selectOprn = this.selectOprn.substring(0, this.selectOprn.length - 1)
      this.schData.wkpl_chec_dtl_cl_cd = this.selectOprn
      this.schData.wkpl_id = this.frmData.wkpl_id
      this.schData.con_prms_id = this.reqData.con_prms_id
      gridView.commit()
      this.selectObey()
    },
    popOpen(val) {
      this.userPopGb = val
      this.$refs.pop.open(this.frmData, false)
    },
    async popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          if (this.userPopGb === 'main') {
            this.$set(this.frmData, 'con_rq_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'con_rq_emp_id', i.emp_no)
          } else if (this.userPopGb === 'supe') {
            this.$set(this.reqData, 'con_supe_emp_nm', i.emp_nm)
            this.$set(this.reqData, 'con_supe_emp_id', i.emp_no)
          } else if (this.userPopGb === 'sft') {
            this.$set(this.reqData, 'con_sft_rspn_emp_nm', i.emp_nm)
            this.$set(this.reqData, 'con_sft_rspn_emp_id', i.emp_no)
          } else if (this.userPopGb === 'oprn') {
            this.$set(this.reqData, 'con_oprn_supe_emp_nm', i.emp_nm)
            this.$set(this.reqData, 'con_oprn_supe_emp_id', i.emp_no)
          }
        })
      }
    },
    popLocOpen() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        return false
      }
      this.$refs.locpop.open(this.frmData, false)
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.reqData, 'con_oprn_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.reqData, 'con_oprn_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    popDeptOpen() {
      this.$refs.deptpop.open()
    },
    popDeptClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'con_rspn_org_nm', returnData[0].dept_nm)
        this.$set(this.frmData, 'con_rspn_org_cd', returnData[0].dept_cd)
      }
    },
    popBsendOpen() {
      this.$refs.bsendpop.open()
    },
    popBsendClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'otsr_con_bsen_nm', returnData[0].otsr_con_bsen_nm)
        this.$set(this.frmData, 'otsr_con_bsen_id', returnData[0].otsr_con_bsen_id)
        this.schData.otsr_con_bsen_id = returnData[0].otsr_con_bsen_id
        this.selectOprr()
      }
    },
    prxsPop() {
      this.$refs.prxsoprnldpop.open(this.frmData)
    },
    popPrxsoprnldClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        // this.schData.con_prms_id = returnData[0].con_prms_id
        this.schData.key_id = this.frmData.con_id + '-' + returnData[0].con_prms_id
        this.select('B')
      }
    },
    async popBsendUserClose(returnData) {
      gridView2.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          let passyn = null
          let besnChk = null
          for (let j = 0; j < dataProvider2.getRowCount(); j++) {
            if (dataProvider2.getRowState(j) !== 'deleted' && dataProvider2.getRowState(j) !== 'createAndDeleted') {
              if (i.oprr_id === dataProvider2.getValue(j, 'oprr_id') && i.oprn_cl_cd === dataProvider2.getValue(j, 'oprn_cl_cd')) {
                passyn = 'Y'
              }
            }
          }
          if (i.otsr_con_bsen_id !== this.frmData.otsr_con_bsen_id) {
            besnChk = 'N'
          }

          if (passyn === null && besnChk === null) {
            dataProvider2.addRow({
              oprr_id: i.oprr_id,
              oprr_nm: i.oprr_nm,
              con_prms_id: this.reqData.con_prms_id,
              oprn_cl_cd: i.oprn_cl_cd,
              oprn_cl_nm: i.oprn_cl_nm,
            })
          } else if (passyn === 'Y') {
            this.$toast.show(i.oprr_nm + this.$t('MS00000445'), { className: 'toast_error' }) // 작업자가 존재합니다.
          } else if (besnChk === 'N') {
            this.$toast.show(this.$t('MS00000446'), { className: 'toast_error' }) // 협력사가 다릅니다.
          }
        })
      }
    },
    conChk(str) {
      if (str === 'wekd') {
        this.frmData.wekd_con_yn = 'Y'
      }
      if (str === 'wknd') {
        this.frmData.wknd_con_yn = 'Y'
      }
      if (str === 'nght') {
        this.frmData.nght_con_yn = 'Y'
      }
      if (str === 'wekdreq') {
        this.reqData.wekd_con_yn = 'Y'
      }
      if (str === 'wkndreq') {
        this.reqData.wknd_con_yn = 'Y'
      }
      if (str === 'nghtreq') {
        this.reqData.nght_con_yn = 'Y'
      }
    },
    // 회사 콤보 변경 시
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT', up_cd: '' })
      this.frmData.wkpl_id = ''
    },
    // 사업장 콤보 변경 시
    wkplChg() {
      // 위치 정보 초기화
      this.reqData.con_oprn_wkpl_loc_id = ''
      this.reqData.con_oprn_wkpl_loc_nm = ''
    },
    // 시간 변경시 시작시간과 종료시간 비교
    hourChg() {
      const val1 = this.reqData.con_bgn_hm_h
      const val2 = this.reqData.con_bgn_hm_s
      const val3 = this.reqData.con_end_hm_h
      const val4 = this.reqData.con_end_hm_s
      if (!this.isEmpty(val1) && !this.isEmpty(val2) && !this.isEmpty(val3) && !this.isEmpty(val4)) {
        const bgnhm = val1.concat(val2)
        const endhm = val3.concat(val4)
        return bgnhm >= endhm
      }
    },
    goList() {
      this.$router.replace({
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
