<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 사고 보고 및 조사 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00001045') }}</span>
    </div>
    <div class="work_title">
      <!-- 사고관리 -->
      <h2>
        <!-- 사고 보고 및 조사 -->
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
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!-- 사고구분 -->
                <div class="label_tit">{{ $t('LB00000806') }}</div>
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
                <!-- 사고구분 분류 콤보박스 / 사고구분 상세 콤보박스 -->
                <div class="label_con">{{ frmData.acdt_cl_nm }} / {{ frmData.acdt_dtl_cl_nm }}</div>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!-- 사고유형 -->
                <div class="label_tit">{{ $t('LB00001279') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 사고유형 콤보박스 -->
                <div class="label_con">{{ frmData.acdt_tp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고기인물 -->
                <div class="label_tit">{{ $t('LB00001339') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 사고기인물코드(SHE시스템사고원인대상코드) / 사고원인대상내용 -->
                <div class="label_con">{{ frmData.acdt_caus_tgt_cd_nm }} / {{ frmData.acdt_caus_tgt_txt }}</div>
              </v-col>
              <v-col cols="1">
                <!--사고위치-->
                <div class="label_tit">{{ $t('LB00000810') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 사고위치 선택 / 사고위치 상세입력 -->
                <div class="label_con">{{ frmData.acdt_wkpl_loc_nm }} / {{ frmData.acdt_dtl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사고발생 부서-->
                <div class="label_tit">{{ $t('LB00000831') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.kor_acdt_occr_org_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사고일시-->
                <div class="label_tit">{{ $t('LB00000832') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <div class="label_con">{{ frmData.acdt_occr_dt }}</div>
                &nbsp;
                <!--사고일시 요일-->
                <div class="label_con">{{ dateAccidentText }}</div>
                &nbsp;
                <!-- 사고일시 시간 -->
                <div class="label_con">{{ frmData.acdt_occr_hour }}</div>
                &nbsp;
                <span style="margin-top: 5px">:</span>
                &nbsp;
                <!-- 사고일시 분 -->
                <div class="label_con">{{ frmData.acdt_occr_minute }}</div>
              </v-col>
              <v-col cols="1">
                <!--업무상 사고여부-->
                <div class="label_tit">{{ $t('LB00000833') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.bsns_rel_acdt_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--산업재해조사표제출대상-->
                <div class="label_tit">{{ $t('LB00001340') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.inac_ivst_sbms_tgt_yn }}</div>
              </v-col>
              <v-col cols="1" style="display: flex">
                <!--산재여부-->
                <div class="label_tit">{{ $t('LB00000807') }}</div>
                <v-tooltip bottom>
                  <template v-slot:activator="{ on, attrs }">
                    <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                  </template>
                  <span>*산재 해당시 변경/수정 바랍니다.</span>
                </v-tooltip>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.inac_yn_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고발생  -->
                <div class="label_tit">[사고발생]</div>
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
                    disabled
                    background-color="white"
                  ></v-text-field>
                  &nbsp;
                  <span style="margin-top: 5px">:</span>
                  &nbsp;
                  <v-text-field
                    ref="occrminute"
                    v-model="frmData.occrminute"
                    class="centered-input"
                    outlined
                    single-line
                    :maxlength="2"
                    oninput="javascript: this.value = this.value.replace(/[^0-9]/g, '');"
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00001342')"
                    height="30"
                    disabled
                    background-color="white"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="acdt_occr_txt" v-model="frmData.acdt_occr_txt" rows="3" outlined auto-grow :hide-details="true" dense disabled></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사고접수  -->
                <div class="label_tit">[사고접수]</div>
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
                    disabled
                    background-color="white"
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
                    disabled
                    background-color="white"
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
                  disabled
                  placeholder="사고 접수(신고) 주관부서 및 사고대응부서, 사고 접수 당시 담당자 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 초기대응  -->
                <div class="label_tit">[초기대응]</div>
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
                    disabled
                    background-color="white"
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
                    disabled
                    background-color="white"
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
                  disabled
                  placeholder="현장통제, 응급처치 및 구조요청 등 초기 대응(조치) 내용을 시간순으로 6하 원칙에 의거 작성 바랍니다. (설비정지, 밸브차단, 응급처치 등 조치 내용 작성) 
  2차 사고 예방, 현장보존, 손실확인 등 초기 대응(조치) 내용을 시간순으로 6하 원칙에 의거 작성 바랍니다. (현장통제, 현장보존, 피해상황 파악 등 조치 내용 작성)"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사후조치  -->
                <div class="label_tit">[사후조치]</div>
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
                    disabled
                    background-color="white"
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
                    disabled
                    background-color="white"
                  ></v-text-field>
                </div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="acdt_af_actn_txt"
                  v-model="frmData.acdt_af_actn_txt"
                  rows="3"
                  outlined
                  auto-grow
                  :hide-details="true"
                  dense
                  disabled
                  placeholder="재해자, 피해설비 등에 대한 조치 내용을 시간순으로 6하 원칙에 의거 구체적으로 작성 (재해자 병원이송, 치료경과, 복귀여부, 피해설비 재가동, 수선계획 등 상세 작성) "
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사고발생원인-->
                <div class="label_tit">{{ $t('LB00000836') }}</div>
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
                  disabled
                  placeholder="사고 발생 원인을 구체적으로 작성 바랍니다."
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--재발방지대책-->
                <div class="label_tit">{{ $t('LB00000837') }}</div>
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
                  disabled
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
                  disabled
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
      </div>
      <div id="realgrid5" style="width: 100%; height: 200px"></div>
      <div class="group_padd"></div>
      <div class="grid_header">
        <!--시정조치-->
        <div class="grid_header_left">2. {{ $t('LB00000844') }}</div>
      </div>
      <div id="realgrid6" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd"></div>
    <div v-show="isComplete1" class="container_detail">
      <div class="sub_title">
        <!--첨부파일 정보-->
        <h3>{{ $t('LB00000845') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">사고 발생/현장 사진</div>
              </v-col>
              <v-col cols="5"><picpreview ref="occurPicPop" style="height: 420px" /></v-col>
              <v-col cols="1">
                <div class="label_tit">사고 재현 사진</div>
              </v-col>
              <v-col cols="5"><picpreview ref="reenactPicPop" style="height: 420px" /></v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12">
          <div class="grid_header">
            <div class="grid_header_left"></div>
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
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="acdtedt1" mkey2="acdtedt2" @close="popClose" />
    <rgnslct ref="pop2" mkey="acdtedt" @close="popClose2" />
    <deptslct ref="pop3" mkey="acdtedt" @close="popClose3" />
    <filePopup ref="pop4" mkey1="acdtedt" :edit-chk="false" @close="popClose4" />
    <rsltregllup ref="pop5" @close="popClose5" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
import deptslct from '../../common/deptslct'
import filePopup from '../../common/atflgrid'
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
    rsltregllup,
    picpreview,
  },
  data() {
    return {
      modify: false,
      isComplete1: false,
      isComplete2: false,
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
      inacYn: ['Y', 'N'], // 산재여부
      totalcnt: 0,
      menu1: false,
      menu2: false,
      popupNm: '',
      dataRow: 0,
      schData: {},
      frmData: {},
      tempData: {},
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
  mounted() {
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

        if (this.$route.params.acdtmgntdtllup !== null && this.$route.params.acdtmgntdtllup !== undefined) {
          this.frmData.acdt_id = this.$route.params.acdtmgntdtllup
          this.acdtid = this.$route.params.acdtmgntdtllup
          this.schData.acdt_id = this.$route.params.acdtmgntdtllup
        } else {
          this.frmData.acdt_id = ''
          this.acdtid = ''
          this.schData.acdt_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.acdt_id = this.schData.acdt_id
        }

        this.createGrid()
        this.createGrid2()
        this.createGrid3()

        this.$refs.occurPicPop.setEdit(false)
        this.$refs.reenactPicPop.setEdit(false)

        // ID 존재할 경우
        // if (this.frmData.acdt_id) {
        //   this.select()
        // }
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
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
          renderer: {
            type: 'check',
            editable: false,
          },
        },
        {
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
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
      gridView.editOptions.editable = false
      gridView.editOptions.appendable = false

      dataProvider.setRows(this.codes.SH00000026)
      gridView.refresh()
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
          renderer: {
            type: 'check',
            editable: false,
          },
        },
        {
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
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
      gridView2.editOptions.editable = false
      gridView2.editOptions.appendable = false

      dataProvider2.setRows(this.codes.SH00000027)
      gridView2.refresh()
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
          renderer: {
            type: 'check',
            editable: false,
          },
        },
        {
          header: this.$t('LB00000838'), // 작업내용 및 과정
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '300',
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
      gridView3.editOptions.editable = false
      gridView3.editOptions.appendable = false

      dataProvider3.setRows(this.codes.SH00000028)
      gridView3.refresh()
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
          fieldName: 'dfdt_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_pric_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dfdt_pric_nm',
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
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'dfdt_nm',
          fieldName: 'dfdt_nm',
          width: '50',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'dfdt_dept_nm',
          fieldName: 'dfdt_dept_nm',
          width: '110',
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
          values: this.selSH00000059.cd,
          labels: this.selSH00000059.cd_nm,
          lookupDisplay: true,
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
        },
        {
          header: this.$t('LB00000856'), // 진단결과
          name: 'dfdt_dgns_rslt_txt',
          fieldName: 'dfdt_dgns_rslt_txt',
          width: '200',
          styleName: 'left',
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
      gridView4.setCheckBar({ visible: false })

      gridView4.header.height = 49
      gridView4.displayOptions.rowHeight = 40
      gridView4.footer.height = 40
      gridView4.displayOptions.fitStyle = 'fill'
      gridView4.displayOptions.selectionStyle = 'block'
      gridView4.editOptions.editable = false
      gridView4.editOptions.appendable = false
      // gridView4.columnByName('org_nm').editable = false
      // gridView4.columnByName('dfdt_pric_id').editable = false
      // gridView4.columnByName('dfdt_pric_nm').editable = false
      gridView4.columnByName('jncm_dt').editable = false
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
      gridView5.setCheckBar({ visible: false })

      gridView5.header.height = 49
      gridView5.displayOptions.rowHeight = 40
      gridView5.footer.height = 40
      gridView5.displayOptions.fitStyle = 'fill'
      gridView5.displayOptions.selectionStyle = 'block'
      gridView5.editOptions.editable = false
      gridView5.editOptions.appendable = false

      gridView5.addLookupSource(this.level2Data)
      gridView5.addLookupSource(this.level3Data)
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
      gridView6.setCheckBar({ visible: false })

      gridView6.header.height = 49
      gridView6.displayOptions.rowHeight = 40
      gridView6.footer.height = 40
      gridView6.displayOptions.fitStyle = 'fill'
      gridView6.displayOptions.selectionStyle = 'block'
      gridView6.editOptions.editable = false
      gridView6.editOptions.appendable = false
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
        this.chkRowVal = itemIndex.dataRow // row index 값
        if (itemIndex.column === 'acdt_atfl_nm') {
          this.filePopGb = 'file1'
          const atfileNo = gridView7.getValue(itemIndex.itemIndex, 'acdt_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.pop4.open(this.schData, false)
        }
      }.bind(this)
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

      gridView8.onCellClicked = function (grid, index, clickData) {
        const current = gridView8.getCurrent()
        if (current.itemIndex < 0) return
        if (current.column === 'acdt_actn_plan_txt') {
          const obj = {}
          obj.row = current.dataRow
          obj.id = dataProvider8.getValue(current.dataRow, 'acdt_id')
          obj.sn = dataProvider8.getValue(current.dataRow, 'acdt_actn_plan_sn')
          obj.nm = dataProvider8.getValue(current.dataRow, 'acdt_actn_emp_nm')
          obj.dt = dataProvider8.getValue(current.dataRow, 'acdt_acco_dt')
          obj.txt = dataProvider8.getValue(current.dataRow, 'acdt_actn_txt')
          obj.atfl = dataProvider8.getValue(current.dataRow, 'acdt_actn_rslt_atfl_no')
          this.$refs.pop5.open(obj)
        }
      }.bind(this)
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

      dataProvider9.setRows(this.codes.SH00000062)
      gridView9.refresh()

      gridView9.onCellButtonClicked = function (grid, itemIndex, column) {
        this.chkRowVal = itemIndex.dataRow // row index 값
        if (itemIndex.column === 'acdt_atfl_nm') {
          this.filePopGb = 'file2'
          const atfileNo = gridView9.getValue(itemIndex.itemIndex, 'acdt_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.pop4.open(this.schData, false)
        }
      }.bind(this)
      gridView9.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = false
      }.bind(this)
      // 그리드 9 끝
    },
    async select() {
      this.modify = true
      this.isComplete1 = true
      this.frmData = await this.$axios.$get(`/api/v1/she/sfty/acdtmgnt/${this.frmData.acdt_id}`)

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

      if (this.frmData.ivst_rprt_reg_dt) {
        this.isComplete2 = true
      }

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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    popOpen2() {
      this.$refs.pop2.open()
    },
    popOpen3() {
      this.$refs.pop3.open()
    },
    popOpen4(name, dataRow, fileNo) {
      this.popupNm = name
      this.dataRow = dataRow
      const obj = {}
      obj.atfile_no = fileNo
      this.$refs.pop4.open(obj)
    },
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        if (this.popupNm === 'dfdt') {
          const row = gridView4.getCurrent().dataRow
          const values = {
            dfdt_pric_id: returnData[0].emp_no,
            dfdt_pric_nm: returnData[0].emp_nm,
            org_nm: returnData[0].dept_nm,
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
  },
}
</script>
<style scoped>
.centered-input >>> input {
  text-align: center;
}
</style>
