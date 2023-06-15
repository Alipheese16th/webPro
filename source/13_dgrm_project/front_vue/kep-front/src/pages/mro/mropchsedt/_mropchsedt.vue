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
      <div class="sub_title" style="display: flex">
        <!--MRO 구매요청(KO)-->
        <h3>{{ $t('LB00000153') }}</h3>
        <v-spacer></v-spacer>
        <!--"저장"은 MRO 구매요청 필수값 입력 후 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000603') }}</div>
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
                <v-select
                  ref="wkpl_id"
                  v-model="mroSaveData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000006"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--품목명(KO)-->
                <div class="label_tit essn">{{ $t('LB00000159') }}</div>
              </v-col>
              <v-col cols="7">
                <!--입력하세요-->
                <v-text-field
                  ref="mtrl_ko_nm"
                  v-model="mtrlSaveData.mtrl_ko_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="99"
                  @change="selectMtrlList($event)"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구매수량-->
                <div class="label_tit essn">{{ $t('LB00000527') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!--
                <v-select
                  ref="pchs_qty"
                  v-model="mtrlSaveData.pchs_qty"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.NumList"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  style="max-width: 100px"
                ></v-select>
                 -->
                <v-text-field
                  ref="pchs_qty"
                  v-model="mtrlSaveData.pchs_qty"
                  outlined
                  single-line
                  :hide-details="true"
                  type="number"
                  dense
                  style="max-width: 100px"
                  height="30"
                  min="0"
                  max="4"
                  @keyup="mtrlSaveData.pchs_qty = numlengthchk(mtrlSaveData.pchs_qty)"
                  @change="calcTotalWeght('A', $event)"
                ></v-text-field>
                <!-- <div class="label_con" style="max-width: 150px">{{ mtrlSaveData.pchs_unit_cd === '6' ? mtrlSaveData.pchs_unit_cd : '' }}</div> -->
              </v-col>
              <v-col cols="1">
                <!--단위중량-->
                <div class="label_tit essn">{{ $t('LB00000528') }}</div>
              </v-col>
              <v-col cols="3">
                <div style="display: flex">
                  <v-text-field
                    ref="pchs_unit_weght"
                    v-model="mtrlSaveData.pchs_unit_weght"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00000528')"
                    text-xs-right
                    style="max-width: 70px"
                    height="30"
                    maxlength="18"
                    @change="calcTotalWeght('B', $event)"
                  ></v-text-field>
                  <span>&nbsp;</span>
                  <v-select
                    ref="pchs_unit_cd"
                    v-model="mtrlSaveData.pchs_unit_cd"
                    label="SELECT"
                    item-text="cd_nm"
                    item-value="cd"
                    :items="codes.CH00000026"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                    style="max-width: 70px"
                    @change="calcTotalWeght('C', $event)"
                  ></v-select>
                  <span>&nbsp;</span>
                  <div v-if="mtrlSaveData.pchs_unit_cd === '6'">
                    <v-text-field
                      ref="pchs_unit_nm"
                      v-model="mtrlSaveData.pchs_unit_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000551')"
                      text-xs-right
                      style="max-width: 100px"
                      height="30"
                      maxlength="99"
                      @change="calcTotalWeght('D', $event)"
                    ></v-text-field>
                  </div>
                  <!--단위중량: 최소단위 PACKING 무게정보-->
                  <div class="label_info ml-1">{{ $t('LB00000170') }}</div>
                </div>
              </v-col>
              <v-col cols="1">
                <!--총구매량-->
                <div class="label_tit">총구매량</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mtrlSaveData.pchs_total_weght }}</div>
              </v-col>
              <!-- <v-col cols="1"> //주문단위별 순중량 주석처리
                <div class="label_tit essn">{{ $t('LB00000169') }}</div>
              </v-col>
              <v-col cols="7">
                <v-container style="border-top: 0px; border-bottom: 0px">
                  <v-row style="border-top: 0px; border-bottom: 0px">
                    <v-col cols="3">
                      <v-text-field
                        ref="pchs_unit_nweght_qty"
                        v-model="mtrlSaveData.pchs_unit_nweght_qty"
                        outlined
                        single-line
                        :hide-details="true"
                        type="number"
                        dense
                        style="width: 200px"
                        height="30"
                        min="0"
                        max="999999999"
                        :disabled="mtrlSaveData.nweght_uncnfm_yn1"
                        @keyup="mtrlSaveData.pchs_unit_nweght_qty = numlengthchk(mtrlSaveData.pchs_unit_nweght_qty)"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="3">
                      //알 수 없음
                      <v-checkbox
                        v-model="mtrlSaveData.nweght_uncnfm_yn"
                        :label="$t('LB00000351')"
                        height="30"
                        style="width: 200px"
                        :hide-details="true"
                        class="ml-1"
                        @click="
                          ;(mtrlSaveData.nweght_uncnfm_yn1 = !mtrlSaveData.nweght_uncnfm_yn1),
                            mtrlSaveData.nweght_uncnfm_yn1 === true ? (mtrlSaveData.pchs_unit_nweght_qty = null) : (mtrlSaveData.pchs_url = '')
                        "
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="6">
                      <div class="label_info ml-1">
                        //주문 단위별 순중량(포장재 무게 제외)을 g(그램) 단위로 입력해주세요.(KO)
                        {{ $t('LB00000168') }}
                        <br />
                        //부피 등 무게단위 주문 품목이 아닌 경우 MSDS 내 비중 값을 적용하여 변환한 순중량 값을 입력해 주시기 바랍니다.(KO)
                        {{ $t('LB00000167') }}
                      </div>
                    </v-col>
                  </v-row>
                </v-container>
                <v-container v-if="mtrlSaveData.nweght_uncnfm_yn1" style="border-top: 1px solid #dcdcdc">
                  <v-row>
                    <v-col cols="2" justify-center>
                      //순중량 url 
                      <div class="label_tit">{{ $t('LB00000507') }}</div>
                    </v-col>
                    <v-col cols="10">
                      //구매 사이트 상품 URL을 입력하세요.
                      <v-text-field
                        ref="pchs_url"
                        v-model="mtrlSaveData.pchs_url"
                        outlined
                        single-line
                        :hide-details="true"
                        type="text"
                        dense
                        :placeholder="$t('LB00000352')"
                        height="30"
                        maxlength="289"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col> -->
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--품목구분-->
                <div class="label_tit essn">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-select
                  ref="macl_cd"
                  v-model="mtrlSaveData.macl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000006"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--수입여부(KO)-->
                <div class="label_tit essn">{{ $t('LB00000157') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <!--수입-->
                <v-checkbox v-model="mtrlSaveData.impt_yn" :label="$t('LB00000197')" height="30" :hide-details="true" class="ml-1"></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--자재 성상-->
                <div class="label_tit essn">{{ $t('LB00000355') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="phase_cd"
                  v-model="mtrlSaveData.phase_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000017"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--자재 용도-->
                <div class="label_tit essn">{{ $t('LB00000354') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtrl_puse_cd"
                  v-model="mtrlSaveData.mtrl_puse_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000018"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--자재 기타 용도-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000353') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="mtrl_puse_cntn"
                  v-model="mtrlSaveData.mtrl_puse_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  maxlength="199"
                  :disabled="!mtrlModify"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구매처(KO)-->
                <div class="label_tit essn">{{ $t('LB00000166') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <!--입력하세요-->
                <v-text-field
                  ref="vndrselct"
                  v-model="mtrlSaveData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  readonly
                  height="30"
                  class="icon_sch"
                  :disabled="mtrlSaveData.vndr_kyin_yn1 == true"
                  @click="popVndr1"
                ></v-text-field>
                <!--직접입력(KO)-->
                <v-checkbox
                  v-model="mtrlSaveData.vndr_kyin_yn"
                  :label="$t('LB00000165')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  @click="
                    ;(mtrlSaveData.vndr_kyin_yn1 = !mtrlSaveData.vndr_kyin_yn1),
                      mtrlSaveData.vndr_kyin_yn1 == true ? (mtrlSaveData.vndr_no = '') : '',
                      gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''
                  "
                ></v-checkbox>
              </v-col>
              <v-col cols="6">
                <div class="label_info ml-1">
                  <!-- 구매하신 홈페이지 운영업체 정보 (아이마켓, 등) -->
                  {{ $t('LB00000524') }}
                </div>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처명-->
                <div class="label_tit essn">{{ $t('LB00000158') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_nm1"
                  v-model="mtrlSaveData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--구매처 주소(KO)-->
                <div class="label_tit essn">{{ $t('LB00000164') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_addr1"
                  v-model="mtrlSaveData.vndr_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="199"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 국가(KO)-->
                <div class="label_tit essn">{{ $t('LB00000163') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="nat_cd1"
                  v-model="mtrlSaveData.nat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  maxlength="199"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--구매처 연락처(KO)-->
                <div class="label_tit essn">{{ $t('LB00000162') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_phon_no1"
                  v-model="mtrlSaveData.vndr_phon_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 대표자명(KO)-->
                <div class="label_tit">{{ $t('LB00000161') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="mtrlSaveData.vndr_rprs_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="mtrlSaveData.vndr_coreg_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="mtrlSaveData.vndr_kyin_yn1">
              <v-col cols="1">
                <!--구매처 홈페이지(KO)-->
                <div class="label_tit essn">{{ $t('LB00000181') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_url"
                  v-model="mtrlSaveData.vndr_url"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="299"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
                <!--업체와 정기 계약을 통한 직접납품일 경우에는 “직납＂으로 입력-->
                <div class="label_info ml-1">{{ $t('LB00000599') }}</div>
              </v-col>
              <v-col cols="1">
                <!--구매처 E-mail(KO)-->
                <div class="label_tit">{{ $t('LB00000180') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="mtrlSaveData.vndr_email"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="299"
                  @change="gwsgnSaveData.vndr_same_yn2 == true ? vndrSameOnChange() : ''"
                ></v-text-field>
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
        <v-spacer></v-spacer>
        <!--PDF, word, 한글, 엑셀(KO)-->
        <div class="label_info ml-1">{{ $t('LB00000178') }}</div>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1" justify-center>
                <!-- 제정일 -->
                <div class="label_tit essn">{{ $t('LB00000202') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="enact_da"
                      v-model="mtrlSaveData.enact_da"
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
                  <v-date-picker v-model="mtrlSaveData.enact_da" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1" justify-center>
                <!-- 개정일 -->
                <div class="label_tit essn">{{ $t('LB00000203') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="rev_da"
                      v-model="mtrlSaveData.rev_da"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      :disabled="mtrlSaveData.enact_rev_same"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="mtrlSaveData.rev_da" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
                <v-checkbox v-model="mtrlSaveData.enact_rev_same" :label="$t('LB00000598')" height="30" :hide-details="true" class="ml-1" @click="enactSame()"></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS버전 -->
                <div class="label_tit essn">{{ $t('LB00000266') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="msds_ver"
                  v-model="mtrlSaveData.msds_ver"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="19"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--MSDS(국문)-->
                <div class="label_tit essn">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col cols="5">
                <file-upload ref="upload1" :multiple="false" @onDrop="chkFile()" />
              </v-col>
              <v-col cols="1">
                <!--MSDS(영문)-->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col cols="5">
                <file-upload ref="upload2" input-id="file2" :multiple="false" @onDrop="chkFile()" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title" style="display: flex">
        <!--구성물질-->
        <h3>{{ $t('LB00000026') }}</h3>
        <v-spacer></v-spacer>
        <!--첨부된 MSDS의 “3. 구성성분 정보”항목에서 확인 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000525') }}</div>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbstSaveData.totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="popupSbst()">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" ref="grid" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title" style="display: flex">
        <!-- 경고표지 -->
        <h3>{{ $t('LB00000038') }}</h3>
        <v-spacer></v-spacer>
        <!--첨부된 MSDS의 “2번 유해성·위험성에서 확인 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000609') }}</div>
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
                <div class="label_tit essn">{{ $t('LB00000188') }}</div>
              </v-col>
              <v-col cols="5" style="display: flex">
                <!--MSDS 기입된 공급자 정보 입력하세요-->
                <v-text-field
                  ref="vndrslect2"
                  v-model="gwsgnSaveData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000523')"
                  readonly
                  height="30"
                  class="icon_sch"
                  :disabled="gwsgnSaveData.vndr_kyin_yn2 == true || gwsgnSaveData.vndr_same_yn2 == true"
                  @click="popVndr2"
                ></v-text-field>
                <!--직접입력(KO)-->
                <v-checkbox
                  v-model="gwsgnSaveData.vndr_kyin_yn"
                  :label="$t('LB00000165')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                  @click="gwsgnSaveData.vndr_kyin_yn2 = !gwsgnSaveData.vndr_kyin_yn2"
                ></v-checkbox>
                <!--구매처와 동일(KO)-->
                <v-checkbox
                  v-model="gwsgnSaveData.vndr_same_yn"
                  :label="$t('LB00000187')"
                  height="30"
                  :hide-details="true"
                  class="ml-1"
                  @click=";(gwsgnSaveData.vndr_same_yn2 = !gwsgnSaveData.vndr_same_yn2), vndrSame()"
                ></v-checkbox>
              </v-col>
              <v-col cols="6">
                <div class="label_info ml-1">
                  <!--첨부된 MSDS의 “1번 화학제품과 회사에 관한 정보” 에서 확인 가능합니다-->
                  {{ $t('LB00000614') }}
                </div>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체명-->
                <div class="label_tit essn">{{ $t('LB00000130') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_nm2"
                  v-model="gwsgnSaveData.vndr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000523')"
                  clearable
                  height="30"
                  maxlength="99"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--공급업체 주소-->
                <div class="label_tit essn">{{ $t('LB00000186') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_addr2"
                  v-model="gwsgnSaveData.vndr_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체 국가-->
                <div class="label_tit essn">{{ $t('LB00000185') }}</div>
              </v-col>
              <v-col cols="5">
                <v-select
                  ref="nat_cd2"
                  v-model="gwsgnSaveData.nat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--공급업체 연락처-->
                <div class="label_tit essn">{{ $t('LB00000184') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  ref="vndr_phon_no2"
                  v-model="gwsgnSaveData.vndr_phon_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--공급업체 대표자명-->
                <div class="label_tit">{{ $t('LB00000183') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="gwsgnSaveData.vndr_rprs_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit">{{ $t('LB00000182') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="gwsgnSaveData.vndr_coreg_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row v-if="gwsgnSaveData.vndr_kyin_yn2">
              <v-col cols="1">
                <!--구매처 홈페이지(KO)-->
                <div class="label_tit">{{ $t('LB00000181') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="gwsgnSaveData.vndr_url"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="299"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--구매처 E-mail(KO)-->
                <div class="label_tit">{{ $t('LB00000180') }}</div>
              </v-col>
              <v-col cols="5">
                <!--입력하세요-->
                <v-text-field
                  v-model="gwsgnSaveData.vndr_email"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="299"
                  :disabled="gwsgnSaveData.vndr_same_yn2 == true"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--신호어-->
                <div class="label_tit">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <v-radio-group v-model="gwsgnSaveData.sgw_cd" row :hide-details="true">
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
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  name=""
                  maxlength="30000"
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
                      <v-textarea v-model="gwsgnSaveData.prv_cntn" outlined single-line :hide-details="true" dense :no-resize="true" maxlength="30000"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--대응-->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.act_cntn" outlined single-line :hide-details="true" dense :no-resize="true" maxlength="30000"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--저장-->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.str_cntn" outlined single-line :hide-details="true" dense :no-resize="true" maxlength="30000"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!--폐기-->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea v-model="gwsgnSaveData.scr_cntn" outlined single-line :hide-details="true" dense :no-resize="true" maxlength="30000"></v-textarea>
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
        //구매요청내용(KO)
      <h3>{{ $t('LB00000147') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor v-model="mroSaveData.pchs_cntn" :options="editorOption" :disabled="quillChk" @change="onEditorChange" />
          </v-col>
        </v-row>
      </v-container>
    </div> -->
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--결재(KO)-->
      <v-btn depressed width="120" color="primary" height="40" @click=";(aprv_yn = 'Y'), save()">{{ $t('LB00000146') }}</v-btn>
      <!--결재담기-->
      <v-btn v-if="mroSaveData.pchs_prog_stt_cd != '15'" depressed width="120" color="primary" height="40" @click=";(aprv_yn = 'Y'), cart()">{{ $t('LB00000608') }}</v-btn>
      <!--임시저장-->
      <v-btn depressed width="120" color="primary" height="40" @click=";(aprv_yn = 'N'), save()">{{ $t('LB00001234') }}</v-btn>
      <!--삭제-->
      <v-btn v-if="mroSaveData.mro_pchs_no != ''" depressed width="120" color="primary" height="40" @click="delsave()">{{ $t('LB00000046') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popup1 ref="popvndr1" mkey="mropchsdet_vndr" @close="vndrsubmit" />
    <popup2 ref="popupsbst" mkey1="mropchsdet_sbst1" mkey2="mropchsdet_sbst2" @close="sbstsubmit" />
    <popup3 ref="popvndr2" mkey="mropchsdet_vndr" @close="vndrsubmit" />
    <gwsgnpopup ref="gwsgnpopup" @close="gwgnsubmit" />
  </div>
</template>

<script>
// 품목구분은 mro의 품목구분만
// 채번규칙은 yymmdd + 6자리시퀀스 tb_ch_mro_pchs_seq 'ex)201207000001
// 지금은 화면에서 저장 까지 개발
// CH00000015 진행상태 상태별로 변경 10 작성중, 20 결재중, 30 완료, 40 반려
// 같은 부서이거나 작성자 = 구매자 인 경우는 수정가능
// 다른 부서이거나 결재중, 완료는 조회모드화면으로 가게
// 반려상태라면 수정 가능
// save,update,delete 시 같은 부서, 같은 사번, post/put/delete 가능한 진행상태인지
//   db 데이터 다시 select 해서 비교하고 체크 완료 되면 update/delete
// 단건 등록일경우 자재번호 Z 다건일경우 자재테이블에서 자재코드 가져다가
// 물질성분 팝업 sbstslct 구매처 팝업 vndrmroslct
// 구매처 직접입력 버튼 클릭시에 아래영역 활성화 아니면 팝업으로 호출 디폴트
// 주문수량 옆 텍스트는 주문단위 입력한것
// 물질 조회 선택하고 난 뒤에
// sbst_type_cd = 10 이라면 있는대로 넣어주고 비고,함량 만 입력할 수 있게
//               10인 것들은 중복 불가
//               10이 아니면 물질명, cas no,비고,함량  컬럼도 입력할 수 있게
//               10번 아닌 것들은 중복 가능
// 결재 상신 전에는 삭제 가능
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup1 from '../../common/vndrmtrlslct'
import popup2 from '../../common/sbstmultislct'
import popup3 from '../../common/vndrmroslct'
import gwsgnpopup from '../../common/gwsgnslct'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()

export default {
  meta: {
    title: 'MRO구매요청등록-최초구매',
    key(route) {
      return `/mro/mropchs/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    popup1,
    popup2,
    popup3,
    gwsgnpopup,
    FileUpload,
  },
  data() {
    return {
      menu1: false,
      menu2: false,
      mask: '####',
      quillChk: true,
      aprv_yn: 'N', // 결재여부
      basic: 'label_tit',
      puse_yn: '',
      mtrlModify: false,
      codeid: ['CO00000006', 'CO00000003', 'CH00000006', 'CH00000009', 'CH00000017', 'CO00000018', 'CH00000026'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }], // 사업장
        CO00000003: [{ cd: '', cd_nm: '' }], // 국가
        CH00000006: [{ cd: '', cd_nm: '' }], // 품목구분 : mro거만 필터링
        CH00000009: [{ cd: '', cd_nm: '' }], // 신호어
        CH00000017: [{ cd: '', cd_nm: '' }], // 자재 성상
        CO00000018: [{ cd: '', cd_nm: '' }], // 자재 용도
        CH00000026: [{ cd: '', cd_nm: '' }], // 단위중량
        NumList: [], // 주문수량
      },
      selCH00000009: {
        cd: [],
        cd_nm: [],
      },
      modify: true,
      vndrpop1: false, // 구매처 선택 위아래 구분
      vndrpop2: false, // 구매처 선택 위아래 구분
      savenext: false, // 저장시 다음로직으로
      fileModify: false, // 첨부파일 수정 여부
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
      },
      // Mro구매 자재
      mtrlSaveData: {
        mro_pchs_no: '',
        mtrl_no: 'Z', // 자재번호(단건일경우 Z)
        mtrl_ko_nm: '', // 자재명
        pchs_unit_cd: '', // 구매 단위 코드
        pchs_unit_nm: '', // 구매 단위 명
        pchs_unit_weght: '',
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
        nweght_uncnfm_yn1: false, // 순중량_미확정_여부
        nweght_uncnfm_yn: '', // 순중량_미확정_여부
        pchs_url: '', // 구매_URL
        phase_cd: '', // 성상_코드
        mtrl_puse_cd: '', // 자재_용도_코드
        mtrl_puse_cntn: '', // 자재_용도_내용
        msds_ver: '', // MSDS_버전
        enact_da: '', // 제정_일자
        rev_da: '', // 개정_일자
        enact_rev_same: false, // 제정, 개정 동일
      },
      // Mro_구매 물질
      sbstSaveData: {
        totalcnt: 0,
      },
      // Mro_자재명
      mtrlListData: {
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
      tempData: {},
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
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (
      (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
        JSON.stringify(this.tempData.mroSaveData) !== JSON.stringify(this.mroSaveData) ||
        JSON.stringify(this.tempData.mtrlSaveData) !== JSON.stringify(this.mtrlSaveData) ||
        JSON.stringify(this.tempData.gwsgnSaveData) !== JSON.stringify(this.gwsgnSaveData) ||
        JSON.stringify(this.tempData.pichSaveData) !== JSON.stringify(this.pichSaveData) ||
        this.fileModify === true) &&
      this.aprv_yn !== 'FIN'
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
  },
  created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    this.mroSaveData.upt_emp_nm = this.usrinfo.emp_nm
    this.mroSaveData.upt_dept_nm = this.usrinfo.dept_nm
    this.mroSaveData.upt_emp_no = this.usrinfo.emp_no
    this.mroSaveData.upt_dt = this.get_date_str_gubun(date, '-')
    this.mtrlSaveData.enact_da = this.get_date_str_gubun(date, '-')
    this.mtrlSaveData.rev_da = this.get_date_str_gubun(date, '-')
    this.mroSaveData.wkpl_id = this.usrinfo.wkpl_id
    // 단위중량 Kg 기본값 설정
    this.mtrlSaveData.pchs_unit_cd = '1'

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
    // 파라미터 조회 - 파라미터가 존재할 경우
    if (this.$route.params.mropchsedt !== null && this.$route.params.mropchsedt !== undefined) {
      this.mroSaveData.mro_pchs_no = this.$route.params.mropchsedt
      this.selectDtl(this.mroSaveData.mro_pchs_no)
    } else {
      this.mroSaveData.mro_pchs_no = ''
      this.onEditorChange()
      this.tempData.pichSaveData = JSON.parse(JSON.stringify(this.pichSaveData))
      this.tempData.mroSaveData = JSON.parse(JSON.stringify(this.mroSaveData))
      this.tempData.mtrlSaveData = JSON.parse(JSON.stringify(this.mtrlSaveData))
      this.tempData.gwsgnSaveData = JSON.parse(JSON.stringify(this.gwsgnSaveData))
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes.CO00000003 = res.CO00000003
      this.codes.CO00000003.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
      this.codes.CO00000006 = res.CO00000006
      this.codes.CO00000006.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
      this.codes.CO00000018 = res.CO00000018
      this.codes.CO00000018.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000017 = res.CH00000017
      this.codes.CH00000017.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000026 = res.CH00000026
      this.codes.CH00000026.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000006 = []
      res.CH00000006.forEach((i) => {
        if (i.up_cd === '2') {
          this.codes.CH00000006.push(i)
        }
      })
      res.CH00000009.forEach((i) => {
        this.selCH00000009.cd.push(i.cd)
        this.selCH00000009.cd_nm.push(i.cd_nm)
      })
      this.codes.CH00000006.unshift({ cd: '', cd_nm: 'SELECT' })
    })

    // 주문수량 생성
    this.codes.NumList.push({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
    for (let i = 1; i <= 50; i++) {
      this.codes.NumList.push({ cd: i, cd_nm: i })
    }

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
        styles: {
          textAlignment: 'center',
        },
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
        editor: {
          maxLength: 18,
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const typecd = grid.getValue(dataCell.index.itemIndex, 'sbst_type_cd')
          if (typecd === '10') {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '300',
        styleName: 'left',
        editor: {
          maxLength: 199,
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const typecd = grid.getValue(dataCell.index.itemIndex, 'sbst_type_cd')
          if (typecd === '10') {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000195'), // 함량(%) 99.0000000 10자리
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '100',
        styleName: 'right',
        editor: {
          maxLength: 10,
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          // 구성물질 함량정보 스타일 적용
          if (dataCell.value === '' || dataCell.value === null || dataCell.value === undefined) {
            ret.styleName = 'field-empty'
          } else {
            ret.styleName = 'right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000095'), // 비고(KO)
        name: 'sbst_note',
        fieldName: 'sbst_note',
        width: '100',
        type: 'button',
        styleName: 'left',
        editor: {
          maxLength: 169,
        },
      },
    ]
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true })
    gridView.editOptions.editable = true
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'
    gridView.setColumnProperty('sbst_type_nm', 'editable', false)

    // gridView.onKeyDown = function (grid, event) {
    //   if (event.key === 'Enter') {
    //     console.log('grid A: ', grid.onCurrentChanging) // eslint-disable-line no-console
    //     // const i = grid.getCurrent()
    //     // grid.setCurrent({ dataRow: i, fieldName: 'sbst_rati' })
    //   }
    // }
  },
  methods: {
    calcTotalWeght(param, e) {
      const pchsQty = this.mtrlSaveData.pchs_qty
      const pchsUnitWeght = this.mtrlSaveData.pchs_unit_weght
      const pchsUnitNm = this.mtrlSaveData.pchs_unit_nm
      let pchsUnitCd = this.$refs.pchs_unit_cd.selectedItems[0].cd_nm // 단위명가져오기
      // console.log('v-select value: ', this.$refs.pchs_unit_cd.selectedItems[0].cd_nm) // eslint-disable-line no-console

      // 단위 콤보 선택시...
      if (param === 'C' && e !== '6') {
        this.codes.CH00000026.forEach((i) => {
          if (i.cd === e) {
            pchsUnitCd = i.cd_nm
          }
        })
        this.mtrlSaveData.pchs_unit_nm = ''
      }

      // 기타단위를 선택했을때
      if (pchsUnitCd === '기타') {
        pchsUnitCd = pchsUnitNm
      }

      if (isNaN(pchsQty) || isNaN(pchsUnitWeght)) {
        // this.mtrlSaveData.pchs_total_weght = ''
        this.$set(this.mtrlSaveData, 'pchs_total_weght', '')
      } else {
        // this.mtrlSaveData.pchs_total_weght = pchsQty * pchsUnitWeght
        // this.$refs.mtrl_puse_cntn.$props
        this.$set(this.mtrlSaveData, 'pchs_total_weght', pchsQty * pchsUnitWeght + '' + pchsUnitCd)
      }
    },
    chkFile() {
      this.fileModify = true
    },
    numlengthchk(value) {
      if (value.length > 9) {
        return value.substr(0, 9)
      } else {
        return value
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
    onEditorChange(editor) {
      this.quillChk = false
    },
    selectMtrlList(mtrlkonm) {
      const selectparams = {}
      selectparams.mtrl_ko_nm = mtrlkonm
      this.$axios.$get(`/api/v1/ch/mro/mropchs-mtrl-list`, { params: selectparams }).then((res) => {
        // this.mroSaveData = res.mroMtrlList
        // 자재명 중복  체크
        this.mtrlListData.totalcnt = res.mroMtrlList.length
      })
    },
    selectDtl(mropchsno) {
      this.$axios.$get(`/api/v1/ch/mro/mropchs/${mropchsno}`).then((res) => {
        this.mroSaveData = res.mroSaveData
        this.mtrlSaveData = res.mtrlSaveData
        dataProvider.setRows(res.sbstlist)
        gridView.refresh()
        gridView.setTopItem(0)
        this.sbstSaveData.totalcnt = gridView.getItemCount()
        this.gwsgnSaveData = res.gwsgnSaveData
        // 구매자재 수입여부
        if (res.mtrlSaveData.impt_yn === 'Y') {
          this.mtrlSaveData.impt_yn = true
        } else {
          this.mtrlSaveData.impt_yn = false
        }

        // 자재 기타용도 필수값 설정
        if (this.mtrlSaveData.mtrl_puse_cd === 'U55') {
          this.mtrlModify = true
          this.puse_yn = 'essn'
        } else {
          this.mtrlModify = false
          this.puse_yn = ''
        }

        // 구매자재 구매처 직접입력 여부
        if (res.mtrlSaveData.vndr_kyin_yn === 'Y') {
          this.mtrlSaveData.vndr_kyin_yn1 = true
          this.mtrlSaveData.vndr_kyin_yn = true
        } else {
          this.mtrlSaveData.vndr_kyin_yn1 = false
          this.mtrlSaveData.vndr_kyin_yn = false
        }
        // 주문단위 별 순중량 알수없음 미확정 여부
        // if (this.mtrlSaveData.nweght_uncnfm_yn === 'Y') {
        //   this.mtrlSaveData.nweght_uncnfm_yn1 = true
        //   this.mtrlSaveData.nweght_uncnfm_yn = true
        // } else {
        //   this.mtrlSaveData.nweght_uncnfm_yn1 = false
        //   this.mtrlSaveData.nweght_uncnfm_yn = false
        // }
        // 경고표지 구매처 직접입력 여부
        if (res.gwsgnSaveData.vndr_kyin_yn === 'Y') {
          this.gwsgnSaveData.vndr_kyin_yn2 = true
          this.gwsgnSaveData.vndr_kyin_yn = true
        } else {
          this.gwsgnSaveData.vndr_kyin_yn2 = false
          this.gwsgnSaveData.vndr_kyin_yn = false
        }
        // 경고표지 구매처와 동일 여부
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
          if (res.pic.length === 0) {
            this.tempData.pichSaveData = JSON.parse(JSON.stringify(this.pichSaveData))
          }
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
            if (res.pic.length === i + 1) {
              this.tempData.pichSaveData = JSON.parse(JSON.stringify(this.pichSaveData))
            }
          }
        }
        this.tempData.mroSaveData = JSON.parse(JSON.stringify(res.mroSaveData))
        this.tempData.mtrlSaveData = JSON.parse(JSON.stringify(res.mtrlSaveData))
        this.tempData.gwsgnSaveData = JSON.parse(JSON.stringify(res.gwsgnSaveData))
        if (!this.isEmpty(res.mtrlSaveData.ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${res.mtrlSaveData.ko_atfile_no}`).then((data) => {
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(res.mtrlSaveData.en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${res.mtrlSaveData.en_atfile_no}`).then((data) => {
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
        this.fileModify = false // 첨부파일 수정 여부 초기화
        this.onEditorChange() // 에디터 초기화
      })
    },
    // 구매요청 구매처 선택 팝업
    popVndr1() {
      this.vndrpop1 = true
      this.vndrpop2 = false
      const mode = '2'
      this.$refs.popvndr1.open(mode)
    },
    // 경고표지 공급자 선택 팝업
    popVndr2() {
      this.vndrpop1 = false
      this.vndrpop2 = true
      const mode = '2'
      this.$refs.popvndr2.open(mode)
    },
    // 공급자 구매처 선택 callback
    vndrsubmit(returnData) {
      if (returnData === undefined) return
      if (this.vndrpop1) {
        this.$set(this.mtrlSaveData, 'vndr_no', returnData.vndr_no)
        this.$set(this.mtrlSaveData, 'vndr_nm', returnData.vndr_nm)
        this.$set(this.mtrlSaveData, 'vndr_phon_no', returnData.vndr_phon_no)
        this.$set(this.mtrlSaveData, 'vndr_addr', returnData.vndr_addr)
        this.$set(this.mtrlSaveData, 'nat_cd', returnData.nat_cd)
        this.$set(this.mtrlSaveData, 'vndr_rprs_nm', returnData.vndr_rprs_nm)
        this.$set(this.mtrlSaveData, 'vndr_coreg_no', returnData.vndr_coreg_no)
        this.$set(this.mtrlSaveData, 'vndr_url', returnData.vndr_url)
        this.$set(this.mtrlSaveData, 'vndr_email', returnData.vndr_email)
        this.$set(this.mtrlSaveData, 'vndr_type_cd', returnData.vndr_type_cd)
        if (this.gwsgnSaveData.vndr_same_yn2) {
          this.vndrSameOnChange()
        }
      } else {
        this.$set(this.gwsgnSaveData, 'vndr_no', returnData.vndr_no)
        this.$set(this.gwsgnSaveData, 'vndr_nm', returnData.vndr_nm)
        this.$set(this.gwsgnSaveData, 'vndr_phon_no', returnData.vndr_phon_no)
        this.$set(this.gwsgnSaveData, 'vndr_addr', returnData.vndr_addr)
        this.$set(this.gwsgnSaveData, 'nat_cd', returnData.nat_cd)
        this.$set(this.gwsgnSaveData, 'vndr_rprs_nm', returnData.vndr_rprs_nm)
        this.$set(this.gwsgnSaveData, 'vndr_coreg_no', returnData.vndr_coreg_no)
        this.$set(this.gwsgnSaveData, 'vndr_url', returnData.vndr_url)
        this.$set(this.gwsgnSaveData, 'vndr_email', returnData.vndr_email)
        this.$set(this.gwsgnSaveData, 'vndr_type_cd', returnData.vndr_type_cd)
      }
      this.$forceUpdate()
    },
    // 구성물질 선택 팝업
    popupSbst() {
      gridView.commit()
      this.$refs.popupsbst.open()
    },
    // 구성물질 선택 팝업 callback
    async sbstsubmit(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          const passyn = dataProvider.searchData({ fields: ['sbst_no'], value: i.sbst_no })
          if (passyn === null) {
            dataProvider.addRow({
              mro_pchs_no: this.mroSaveData.mro_pchs_no,
              mtrl_no: 'Z',
              sbst_no: i.sbst_no,
              sbst_ko_nm: i.sbst_ko_nm,
              sbst_en_nm: i.sbst_en_nm,
              cas_no: i.cas_no,
              sbst_type_cd: i.sbst_type_cd,
              sbst_type_nm: i.sbst_type_nm,
              sbst_seq: '',
              sbst_rati: '',
              sbst_note: '',
            })
          } else if (i.sbst_type_cd === '10') {
            this.$toast.show(i.sbst_ko_nm + this.$t('MS00000053'), { className: 'toast_error' }) // 물질이 이미 존재합니다.
          } else {
            dataProvider.addRow({
              mro_pchs_no: this.mroSaveData.mro_pchs_no,
              mtrl_no: 'Z',
              sbst_no: i.sbst_no,
              sbst_ko_nm: i.sbst_ko_nm,
              sbst_en_nm: i.sbst_en_nm,
              cas_no: i.cas_no,
              sbst_type_cd: i.sbst_type_cd,
              sbst_type_nm: i.sbst_type_nm,
              sbst_seq: '',
              sbst_rati: '',
              sbst_note: '',
            })
          }
        })
      }
      this.sbstSaveData.totalcnt = gridView.getItemCount()
    },
    gwgnsubmit() {},
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
      this.sbstSaveData.totalcnt = gridView.getItemCount()
    },
    // 경고표지 구매처와 동일 체크
    vndrSame() {
      this.vndrSameOnChange()
    },
    enactSame() {
      if (this.mtrlSaveData.enact_rev_same) {
        this.mtrlSaveData.rev_da = this.mtrlSaveData.enact_da
      } else {
        this.mtrlSaveData.rev_da = this.get_date_str_gubun(date, '-')
      }
    },
    // 구매처와 동일 시 값 위아래 일치시키기
    vndrSameOnChange() {
      if (this.gwsgnSaveData.vndr_same_yn2) {
        this.gwsgnSaveData.vndr_no = this.mtrlSaveData.vndr_no
        this.gwsgnSaveData.vndr_nm = this.mtrlSaveData.vndr_nm
        this.gwsgnSaveData.vndr_phon_no = this.mtrlSaveData.vndr_phon_no
        this.gwsgnSaveData.vndr_addr = this.mtrlSaveData.vndr_addr
        this.gwsgnSaveData.nat_cd = this.mtrlSaveData.nat_cd
        this.gwsgnSaveData.vndr_rprs_nm = this.mtrlSaveData.vndr_rprs_nm
        this.gwsgnSaveData.vndr_coreg_no = this.mtrlSaveData.vndr_coreg_no
        this.gwsgnSaveData.vndr_url = this.mtrlSaveData.vndr_url
        this.gwsgnSaveData.vndr_email = this.mtrlSaveData.vndr_email
        this.gwsgnSaveData.vndr_type_cd = this.mtrlSaveData.vndr_type_cd
        this.gwsgnSaveData.vndr_kyin_yn = this.mtrlSaveData.vndr_kyin_yn
        this.gwsgnSaveData.vndr_kyin_yn2 = this.mtrlSaveData.vndr_kyin_yn1
      } else {
        this.gwsgnSaveData.vndr_no = ''
        this.gwsgnSaveData.vndr_nm = ''
        this.gwsgnSaveData.vndr_phon_no = ''
        this.gwsgnSaveData.vndr_addr = ''
        this.gwsgnSaveData.nat_cd = ''
        this.gwsgnSaveData.vndr_rprs_nm = ''
        this.gwsgnSaveData.vndr_coreg_no = ''
        this.gwsgnSaveData.vndr_url = ''
        this.gwsgnSaveData.vndr_email = ''
        this.gwsgnSaveData.vndr_type_cd = ''
        this.gwsgnSaveData.vndr_kyin_yn = ''
        this.gwsgnSaveData.vndr_kyin_yn2 = false
      }
    },
    async save() {
      gridView.commit()
      const lowstatus = dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      if ((await this.validation()) && (await this.sbstvalidation(lowstatus))) {
        this.sbstlist = [] //  저장될 배열 비우기
        const crechk = await this.createPush(lowstatus)
        const upchk = await this.updatePush(lowstatus)
        const dechk = await this.deletePush(lowstatus)
        if (await (crechk && upchk && dechk)) {
          this.pic = []
          if (this.pichSaveData.ck1 === true) {
            this.pic.push({ picg_cd: '1' })
          }
          if (this.pichSaveData.ck2 === true) {
            this.pic.push({ picg_cd: '2' })
          }
          if (this.pichSaveData.ck3 === true) {
            this.pic.push({ picg_cd: '3' })
          }
          if (this.pichSaveData.ck4 === true) {
            this.pic.push({ picg_cd: '4' })
          }
          if (this.pichSaveData.ck5 === true) {
            this.pic.push({ picg_cd: '5' })
          }
          if (this.pichSaveData.ck6 === true) {
            this.pic.push({ picg_cd: '6' })
          }
          if (this.pichSaveData.ck7 === true) {
            this.pic.push({ picg_cd: '7' })
          }
          if (this.pichSaveData.ck8 === true) {
            this.pic.push({ picg_cd: '8' })
          }
          if (this.pichSaveData.ck9 === true) {
            this.pic.push({ picg_cd: '9' })
          }
          const saveparams = {}
          this.mroSaveData.pchs_prog_stt_cd = '10' // 결재담기 상태
          saveparams.mroSaveData = this.mroSaveData
          saveparams.mtrlSaveData = this.mtrlSaveData
          saveparams.sbstlist = this.sbstlist // 구매자재 물질 데이터 리스트
          saveparams.gwsgnSaveData = this.gwsgnSaveData
          saveparams.pic = this.pic
          if (this.aprv_yn === 'Y') {
            let saveMsg = this.$t('MS00000092')
            if (this.mtrlListData.totalcnt > 0) {
              saveMsg = this.$t('MS00000251') + '\n' + this.$t('MS00000092')
            } else {
            }
            this.savenext = await this.$confirm(saveMsg) // 결재하시겠습니까?
          } else {
            let saveMsg = this.$t('MS00000010')
            if (this.mtrlListData.totalcnt > 0) {
              saveMsg = this.$t('MS00000251') + '\n' + this.$t('MS00000010')
            }
            this.savenext = await this.$confirm(saveMsg) // 저장하시겠습니까?
          }
          if (await !this.savenext) {
          } else {
            // 구매자재 수입여부
            if (this.mtrlSaveData.impt_yn) {
              this.mtrlSaveData.impt_yn = await 'Y'
            } else {
              this.mtrlSaveData.impt_yn = await 'N'
            }
            // 구매자재 구매처 직접입력 여부
            if (this.mtrlSaveData.vndr_kyin_yn1) {
              this.mtrlSaveData.vndr_kyin_yn = await 'Y'
            } else {
              this.mtrlSaveData.vndr_kyin_yn = await 'N'
            }
            // 주문단위 별 순중량 알수없음 미확정 여부
            // if (this.mtrlSaveData.nweght_uncnfm_yn1) {
            //   this.mtrlSaveData.nweght_uncnfm_yn = await 'Y'
            // } else {
            //   this.mtrlSaveData.nweght_uncnfm_yn = await 'N'
            // }
            // 경고표지 구매처 직접입력 여부
            if (this.gwsgnSaveData.vndr_kyin_yn2) {
              this.gwsgnSaveData.vndr_kyin_yn = await 'Y'
            } else {
              this.gwsgnSaveData.vndr_kyin_yn = await 'N'
            }
            // 경고표지 구매처와 동일 여부
            if (this.gwsgnSaveData.vndr_same_yn2) {
              this.gwsgnSaveData.vndr_same_yn = await 'Y'
            } else {
              this.gwsgnSaveData.vndr_same_yn = await 'N'
            }
            await this.$axios.post('/api/v1/ch/mro/mropchs', saveparams).then((res) => {
              const config = require('../../../../nuxt.config.js')
              if (res.data !== 'Fail' && res.data !== 'READ') {
                if (this.aprv_yn === 'Y') {
                  // 추후 결제 로직 생기면 이곳에 결제 처리
                  // 저장 후 반환한 mro_pchs_no 문자로 변경
                  this.mroSaveData.mro_pchs_no = res.data + ''
                  saveparams.mroSaveData = this.mroSaveData
                  this.$axios.post('/api/v1/ch/mro/mropchs-aprv', saveparams).then((res) => {
                    console.log(res) // eslint-disable-line no-console
                    // his.$toast.show(this.$t('MS00000093')) // 결재되었습니다.(KO) 추후 //결재 팝업 화면으로 변경
                    this.aprv_yn = 'FIN'
                    if (res.data.Success === 'Success') {
                      const popupWidth = 1575
                      const popupHeight = window.screen.height - 300
                      const popupX = window.screen.width / 2 - popupWidth / 2
                      const popupY = window.screen.height / 2 - popupHeight / 2
                      const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
                      const aprvno = '&dataurl=' + res.data.aprvNo
                      const empno = '&typ=' + res.data.empno
                      let url = config.default.approvalUrl2 + aprvno + empno
                      // 연구소라면
                      if (this.mroSaveData.wkpl_id === 'S02') {
                        url = config.default.approvalUrl3 + aprvno + empno
                      }
                      window.open(url, 'mropchs-aprv', options)
                      // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
                      this.goLup()
                    } else {
                      this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
                    }
                  })
                } else {
                  this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
                  // 가져온 키 값으로 재조회
                  this.selectDtl(res.data)
                }
              } else if (res.data === 'READ') {
                this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
              }
            })
          }
        }
      }
    },
    async cart() {
      gridView.commit()
      const lowstatus = dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      if ((await this.validation()) && (await this.sbstvalidation(lowstatus))) {
        this.sbstlist = [] //  저장될 배열 비우기
        const crechk = await this.createPush(lowstatus)
        const upchk = await this.updatePush(lowstatus)
        const dechk = await this.deletePush(lowstatus)
        if (await (crechk && upchk && dechk)) {
          this.pic = []
          if (this.pichSaveData.ck1 === true) {
            this.pic.push({ picg_cd: '1' })
          }
          if (this.pichSaveData.ck2 === true) {
            this.pic.push({ picg_cd: '2' })
          }
          if (this.pichSaveData.ck3 === true) {
            this.pic.push({ picg_cd: '3' })
          }
          if (this.pichSaveData.ck4 === true) {
            this.pic.push({ picg_cd: '4' })
          }
          if (this.pichSaveData.ck5 === true) {
            this.pic.push({ picg_cd: '5' })
          }
          if (this.pichSaveData.ck6 === true) {
            this.pic.push({ picg_cd: '6' })
          }
          if (this.pichSaveData.ck7 === true) {
            this.pic.push({ picg_cd: '7' })
          }
          if (this.pichSaveData.ck8 === true) {
            this.pic.push({ picg_cd: '8' })
          }
          if (this.pichSaveData.ck9 === true) {
            this.pic.push({ picg_cd: '9' })
          }
          const saveparams = {}
          this.mroSaveData.pchs_prog_stt_cd = '15' // 결재담기 상태
          saveparams.mroSaveData = this.mroSaveData
          saveparams.mtrlSaveData = this.mtrlSaveData
          saveparams.sbstlist = this.sbstlist // 구매자재 물질 데이터 리스트
          saveparams.gwsgnSaveData = this.gwsgnSaveData
          saveparams.pic = this.pic
          if (this.aprv_yn === 'Y') {
            this.savenext = await this.$confirm(this.$t('MS00000244')) // 결재하시겠습니까?
          }
          if (await !this.savenext) {
          } else {
            // 구매자재 수입여부
            if (this.mtrlSaveData.impt_yn) {
              this.mtrlSaveData.impt_yn = await 'Y'
            } else {
              this.mtrlSaveData.impt_yn = await 'N'
            }
            // 구매자재 구매처 직접입력 여부
            if (this.mtrlSaveData.vndr_kyin_yn1) {
              this.mtrlSaveData.vndr_kyin_yn = await 'Y'
            } else {
              this.mtrlSaveData.vndr_kyin_yn = await 'N'
            }
            // 주문단위 별 순중량 알수없음 미확정 여부
            // if (this.mtrlSaveData.nweght_uncnfm_yn1) {
            //   this.mtrlSaveData.nweght_uncnfm_yn = await 'Y'
            // } else {
            //   this.mtrlSaveData.nweght_uncnfm_yn = await 'N'
            // }
            // 경고표지 구매처 직접입력 여부
            if (this.gwsgnSaveData.vndr_kyin_yn2) {
              this.gwsgnSaveData.vndr_kyin_yn = await 'Y'
            } else {
              this.gwsgnSaveData.vndr_kyin_yn = await 'N'
            }
            // 경고표지 구매처와 동일 여부
            if (this.gwsgnSaveData.vndr_same_yn2) {
              this.gwsgnSaveData.vndr_same_yn = await 'Y'
            } else {
              this.gwsgnSaveData.vndr_same_yn = await 'N'
            }
            await this.$axios.post('/api/v1/ch/mro/mropchs', saveparams).then((res) => {
              if (res.data !== 'Fail' && res.data !== 'READ') {
                this.$toast.show('담았습니다.') // 저장되었습니다.
                this.aprv_yn = 'FIN'
                // 가져온 키 값으로 재조회
                // this.selectDtl(res.data)
                this.goList()
              } else if (res.data === 'READ') {
                this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
              }
            })
          }
        }
      }
    },
    async delsave() {
      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?(KO)
      if (!res) {
        return
      }
      await this.$axios.delete(`/api/v1/ch/mro/mropchs/${this.mroSaveData.mro_pchs_no}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
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
    goLup() {
      this.$router.push({
        name: 'mro-mropchslup-mropchslup',
        params: {
          mropchslup: this.mroSaveData.mro_pchs_no,
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
    validation() {
      if (this.isEmpty(this.mroSaveData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000039'), { className: 'toast_error' }) // 사업장 코드를 선택해주세요.(KO)
        this.$refs.wkpl_id.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.mtrl_ko_nm)) {
        this.$toast.show(this.$t('MS00000051'), { className: 'toast_error' }) // 품목명을 입력해주세요.
        this.$refs.mtrl_ko_nm.focus()
        return false
      } else if (this.isEmptyFile(this.mtrlSaveData.pchs_qty)) {
        this.$toast.show(this.$t('MS00000048'), { className: 'toast_error' }) // 구매수량을선택해주세요.
        this.$refs.pchs_qty.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.pchs_unit_weght)) {
        this.$toast.show(this.$t('MS00000050'), { className: 'toast_error' }) // 단위중량을 입력해주세요.
        this.$refs.pchs_unit_weght.focus()
        return false
      } else if (isNaN(this.mtrlSaveData.pchs_unit_weght)) {
        this.$toast.show(this.$t('MS00000236'), { className: 'toast_error' }) // 단위중량은 숫자만 입력해주세요.
        this.$refs.pchs_unit_weght.focus()
        return false
      } else if (this.mtrlSaveData.pchs_unit_weght < 0.001 || this.mtrlSaveData.pchs_unit_weght > 999999999999999) {
        this.$toast.show(this.$t('MS00000237'), { className: 'toast_error' }) // 단위중량을 범위에 맞게 입력해 주세요.(0.001 이상, 999999999999999 미만)
        this.$refs.pchs_unit_weght.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.pchs_unit_cd)) {
        this.$toast.show(this.$t('MS00000232'), { className: 'toast_error' }) // 단위중량을 선택해주세요.
        this.$refs.pchs_unit_cd.focus()
        return false
      } else if (this.mtrlSaveData.pchs_unit_cd === '6' && this.isEmpty(this.mtrlSaveData.pchs_unit_nm)) {
        this.$toast.show(this.$t('MS00000235'), { className: 'toast_error' }) // 기타단위를 입력해주세요.
        this.$refs.pchs_unit_nm.focus()
        return false
      } else if (this.mtrlSaveData.pchs_unit_cd === '6' && !this.isEmpty(this.mtrlSaveData.pchs_unit_nm) && this.mtrlSaveData.pchs_unit_nm.length > 10) {
        this.$toast.show(this.$t('MS00000238'), { className: 'toast_error' }) // 기타단위는 15자 이하로 입력해주세요.
        this.$refs.pchs_unit_nm.focus()
        return false
      }
      // else if (this.isEmptyFile(this.mtrlSaveData.pchs_unit_nweght_qty) && !this.mtrlSaveData.nweght_uncnfm_yn1) {
      //  this.$toast.show(this.$t('MS00000049'), { className: 'toast_error' }) // 주문단위별 순중량을 입력해주세요.
      //  this.$refs.pchs_unit_nweght_qty.focus()
      //  return false
      // }
      // else if (this.isEmpty(this.mtrlSaveData.pchs_url) && this.mtrlSaveData.nweght_uncnfm_yn1) {
      //   this.$toast.show(this.$t('MS00000154'), { className: 'toast_error' }) // 구매사이트 URL을 입력해 주세요.
      //   this.$refs.pchs_url.focus()
      //   return false
      // }
      else if (this.isEmpty(this.mtrlSaveData.macl_cd)) {
        this.$toast.show(this.$t('MS00000047'), { className: 'toast_error' }) // 품목구분을 선택해주세요.
        this.$refs.macl_cd.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.phase_cd)) {
        this.$toast.show(this.$t('MS00000153'), { className: 'toast_error' }) // 자재 성상을 선택해주세요.
        this.$refs.phase_cd.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.mtrl_puse_cd)) {
        this.$toast.show(this.$t('MS00000152'), { className: 'toast_error' }) // 자재용도를 선택해주세요.
        this.$refs.mtrl_puse_cd.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.mtrl_puse_cntn) && this.mtrlSaveData.mtrl_puse_cd === 'U55') {
        this.$toast.show(this.$t('MS00000151'), { className: 'toast_error' }) // 자재 기타 용도를 입력해주세요.
        this.$refs.mtrl_puse_cntn.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.vndr_no) && !this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show(this.$t('MS00000058'), { className: 'toast_error' }) // 구매처를 선택해주세요.
        this.$refs.vndrselct.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.vndr_nm) && this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show(this.$t('MS00000057'), { className: 'toast_error' }) // 구매처명을 입력해주세요.
        this.$refs.vndr_nm1.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.vndr_addr) && this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show(this.$t('MS00000056'), { className: 'toast_error' }) // 구매처주소를 입력해주세요.
        this.$refs.vndr_addr1.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.nat_cd) && this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show(this.$t('MS00000055'), { className: 'toast_error' }) // 구매처국가를 선택해주세요.
        this.$refs.nat_cd1.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.vndr_phon_no) && this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show(this.$t('MS00000054'), { className: 'toast_error' }) // 구매처연락처를 입력해주세요.
        this.$refs.vndr_phon_no1.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.vndr_url) && this.mtrlSaveData.vndr_kyin_yn1) {
        this.$toast.show('구매처홈페이지를 입력해주세요.', { className: 'toast_error' }) // 구매처홈페이지를 입력해주세요.
        this.$refs.vndr_url.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.enact_da) && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000204'), { className: 'toast_error' }) // 제정일자를 입력해주세요.
        this.$refs.enact_da.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.rev_da) && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000205'), { className: 'toast_error' }) // 개정일자를 입력해주세요.
        this.$refs.rev_da.focus()
        return false
      } else if (this.isEmpty(this.mtrlSaveData.msds_ver) && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000203'), { className: 'toast_error' }) // 버전을 입력해주세요.
        this.$refs.msds_ver.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.vndr_no) && !this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000058'), { className: 'toast_error' }) // 구매처를 선택해주세요.
        this.$refs.vndrslect2.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.vndr_nm) && this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000057'), { className: 'toast_error' }) // 구매처명을 입력해주세요.
        this.$refs.vndr_nm2.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.vndr_addr) && this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000056'), { className: 'toast_error' }) // 구매처주소를 입력해주세요.
        this.$refs.vndr_addr2.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.nat_cd) && this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000055'), { className: 'toast_error' }) // 구매처국가를 선택해주세요.
        this.$refs.nat_cd2.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.vndr_phon_no) && this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000054'), { className: 'toast_error' }) // 구매처연락처를 입력해주세요.
        this.$refs.vndr_phon_no2.focus()
        return false
      } else if (this.isEmpty(this.gwsgnSaveData.vndr_phon_no) && this.gwsgnSaveData.vndr_kyin_yn2 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000054'), { className: 'toast_error' }) // 구매처연락처를 입력해주세요.
        this.$refs.vndr_phon_no2.focus()
        return false
      } else {
        const chk = this.$refs.upload1.uploadStart(true).then((data) => {
          if (!this.isEmptyFile(data)) {
            this.mtrlSaveData.ko_atfile_no = data + ''
            this.$axios.$get(`/api/v1/common/file/${data}`).then((data) => {
              this.setFiles1(data)
            })
          } else if (this.aprv_yn === 'Y') {
            this.mtrlSaveData.ko_atfile_no = ''
            this.$toast.show(this.$t('MS00000164'), { className: 'toast_error' }) // MSDS(국문)를 첨부 해 주세요
            return false
          } else {
            return true
          }
          const chk2 = this.$refs.upload2.uploadStart(true).then((data2) => {
            if (!this.isEmptyFile(data2)) {
              this.mtrlSaveData.en_atfile_no = data2 + ''
              this.$axios.$get(`/api/v1/common/file/${data2}`).then((data2) => {
                this.setFiles2(data2)
              })
              if (
                dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') === 0 &&
                JSON.stringify(this.tempData.mroSaveData) === JSON.stringify(this.mroSaveData) &&
                JSON.stringify(this.tempData.mtrlSaveData) === JSON.stringify(this.mtrlSaveData) &&
                JSON.stringify(this.tempData.gwsgnSaveData) === JSON.stringify(this.gwsgnSaveData) &&
                JSON.stringify(this.tempData.pichSaveData) === JSON.stringify(this.pichSaveData) &&
                this.aprv_yn === 'N'
              ) {
                this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
                return false
              } else {
                console.log('데이터 검사 끝>>>>') // eslint-disable-line no-console
                return true
              }
            } else {
              // this.mtrlSaveData.en_atfile_no = ''
              // this.$toast.show(this.$t('MS00000165'), { className: 'toast_error' }) // MSDS(영문)를 첨부 해 주세요
              return true
            }
          })
          return chk2
        })
        return chk
      }
    },
    codChg(data) {
      if (data === 'U55') {
        this.mtrlModify = true
        this.puse_yn = 'essn'
      } else {
        this.mtrlModify = false
        this.mtrlSaveData.mtrl_puse_cntn = null
        this.puse_yn = ''
      }
    },
    sbstvalidation(lowstatus) {
      console.log('그리드 검사 시작') // eslint-disable-line no-console
      if (gridView.getItemCount() <= 0 && this.aprv_yn === 'Y') {
        this.$toast.show(this.$t('MS00000052'), { className: 'toast_error' }) // 구성 물질 정보를 최소 한개 추가 해 주세요.
        return false
      }
      const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      if (chkRow.length === 0) {
        return true
      }
      for (let i = 0; i < chkRow.length; i++) {
        if (this.isEmpty(dataProvider.getValue(chkRow[i], 'sbst_ko_nm'))) {
          this.$toast.show(this.$t('MS00000045'), { className: 'toast_error' }) // 물질명을 입력해 주세요.
          return false
        } else if (this.isEmpty(dataProvider.getValue(chkRow[i], 'sbst_rati'))) {
          this.$toast.show(this.$t('MS00000044'), { className: 'toast_error' }) // 함량을 입력해 주세요.
          return false
        } else if (isNaN(dataProvider.getValue(chkRow[i], 'sbst_rati'))) {
          this.$toast.show(this.$t('MS00000163'), { className: 'toast_error' }) // 함량은 숫자만 입력해주세요.
          return false
        } else if (dataProvider.getValue(chkRow[i], 'sbst_rati') * 1 < 0.0000001 || dataProvider.getValue(chkRow[i], 'sbst_rati') * 1 > 100) {
          this.$toast.show(this.$t('MS00000228'), { className: 'toast_error' }) // 함량을 범위에 맞게 입력해 주세요.(0.0000001 이상, 100 미만)
          return false
        } else if (i + 1 === chkRow.length) {
          console.log('그리드 검사 통과') // eslint-disable-line no-console
          return true
        }

        // if (this.isEmpty(dataProvider.getValue(chkRow[i], 'cas_no'))) {
        //   this.$toast.show(this.$t('MS00000046'), { className: 'toast_error' }) // CAS No.를 입력해 주세요.
        //   return false
        // } else

        //  else if (dataProvider.getValue(chkRow[i], 'sbst_rati').toString().length > 3) {
        //   this.$toast.show(this.$t('MS00000175'), { className: 'toast_error' }) // 함량은 3자 이하로 입력해주세요.
        //   return false
        // }

        // else if (!/^[0-9]*$/.test(dataProvider.getValue(chkRow[i], 'sbst_rati'))) {
        //   this.$toast.show(this.$t('MS00000163'), { className: 'toast_error' }) // 함량은 숫자만 입력해주세요.
        //   return false
        // }
      }
    },
    async createPush(lowstatus) {
      // 새로 생성된 row push
      if ((await dataProvider.getRowStateCount('created')) > 0) {
        lowstatus.created.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'C')
          this.sbstlist.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async updatePush(lowstatus) {
      // 수정된 row push
      if ((await dataProvider.getRowStateCount('updated')) > 0) {
        lowstatus.updated.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'U')
          this.sbstlist.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async deletePush(lowstatus) {
      // 삭제 row push
      if ((await dataProvider.getRowStateCount('deleted')) > 0) {
        lowstatus.deleted.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'D')
          dataProvider.setValue(i, 'use_yn', 'N')
          dataProvider.setValue(i, 'del_yn', 'Y')
          this.sbstlist.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
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
        this.changeEditMode(true)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    gwsgn_pop() {
      // const mtrlno = this.mroSaveData.mro_pchs_no
      const mtrlno = '60000023'
      if (this.isEmpty(mtrlno)) {
        return false
      } else {
        const mtrlnoList = []
        mtrlnoList[0] = mtrlno
        this.$refs.gwsgnpopup.open(mtrlnoList)
      }
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
