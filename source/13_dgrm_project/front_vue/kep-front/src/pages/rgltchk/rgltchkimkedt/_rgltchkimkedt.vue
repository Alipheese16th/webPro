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
                <div class="label_tit essn">{{ $t('LB00000355') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="phase_cd"
                  v-model="frmData.phase_cd"
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
                <!-- 자재 용도 -->
                <div class="label_tit essn">{{ $t('LB00000354') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtrl_puse_cd"
                  v-model="frmData.mtrl_puse_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.CO00000018p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 자재 기타 용도 -->
                <div :class="[basic, puse_yn]">{{ $t('LB00000353') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="mtrl_puse_cntn"
                  v-model="frmData.mtrl_puse_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="!mtrlModify"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 단위중량 -->
                <div class="label_tit essn">{{ $t('LB00000528') }}</div>
              </v-col>
              <v-col cols="11">
                <div style="display: flex">
                  <v-text-field
                    ref="pchs_unit_weght"
                    v-model="frmData.pchs_unit_weght"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    :placeholder="$t('LB00000528')"
                    text-xs-right
                    style="max-width: 70px"
                    height="30"
                    maxlength="18"
                  ></v-text-field>
                  <span>&nbsp;</span>
                  <v-select
                    ref="pchs_unit_cd"
                    v-model="frmData.pchs_unit_cd"
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
                    @change="frmData.pchs_unit_nm = ''"
                  ></v-select>
                  <span>&nbsp;</span>
                  <div v-if="frmData.pchs_unit_cd === '6'">
                    <v-text-field
                      ref="pchs_unit_nm"
                      v-model="frmData.pchs_unit_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000551')"
                      text-xs-right
                      style="max-width: 100px"
                      height="30"
                      maxlength="99"
                    ></v-text-field>
                  </div>
                  <!--단위중량: 최소단위 PACKING 무게정보-->
                  <div class="label_info ml-1">{{ $t('LB00000170') }}</div>
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- MSDS 정보 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000506') }}</h3>
        <v-spacer></v-spacer>
        <!--첨부된 MSDS의 “16번 그 밖의 참고사항” 에서 확인 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000610') }}</div>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 제정일 -->
                <div class="label_tit essn">{{ $t('LB00000202') }}</div>
              </v-col>
              <v-col cols="5">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="enact_da"
                      v-model="frmData.enact_da"
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
                  <v-date-picker v-model="frmData.enact_da" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!-- 개정일 -->
                <div class="label_tit essn">{{ $t('LB00000203') }}</div>
              </v-col>
              <v-col cols="5">
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="rev_da"
                      v-model="frmData.rev_da"
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
                  <v-date-picker v-model="frmData.rev_da" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS버전 -->
                <div class="label_tit essn">{{ $t('LB00000266') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field ref="msds_ver" v-model="frmData.msds_ver" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS(국문)  -->
                <div class="label_tit essn">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col ref="uploadOne" cols="11">
                <file-upload ref="upload1" input-id="file" :multiple="false" @onDrop="chkFile()" />
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS(영문) -->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col ref="uploadTwo" cols="11">
                <!-- 입력하세요 -->
                <file-upload ref="upload2" input-id="file2" :multiple="false" @onDrop="chkFile()" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 구성물질 정보 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000400') }}</h3>
        <v-spacer></v-spacer>
        <!--첨부된 MSDS의 “3번 구성성분의 명칭 및 함량에서 확인 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000611') }}</div>
      </div>
      <div class="grid_header">
        <!-- 총 {{ sbstTotalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbstTotalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn color="" outlined height="30" @click="sbstAdd">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn color="" outlined height="30" @click="sbstDel">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제검토 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000268') }}</h3>
        <v-spacer></v-spacer>
        <!--안전환경부서에서 검토합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000612') }}</div>
      </div>
      <div class="grid_header">
        <!-- 총 {{ rgltTotalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ rgltTotalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 규제DB기준으로 초기화 -->
          <v-btn v-if="authChk" color="" outlined height="30" @click="rgltDbReset()">{{ $t('LB00000418') }}</v-btn>
        </div>
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
              <v-col cols="3">
                <v-select
                  ref="dngr_kind_cd"
                  v-model="frmData.dngr_kind_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000027"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="dngrChk === false ? false : true"
                  @change="codChg2"
                ></v-select>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dngr_dtl_kind_cd"
                  v-model="frmData.dngr_dtl_kind_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000028"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="dngrChk === false ? false : true"
                ></v-select>
              </v-col>
              <v-col cols="3">
                <!-- 규제 대상 물질 선택 -->
                <v-btn outlined href="http://hazmat.mpss.kfi.or.kr/index.do" target="_blank" height="30">{{ $t('LB00000595') }}</v-btn>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 검토결과 -->
                <div class="label_tit">{{ $t('LB00000282') }}</div>
              </v-col>
              <v-col cols="11">
                <v-radio-group ref="rgltchkrslt" v-model="rsltData.rglt_chk_mtrl_use_yn" row :hide-details="true">
                  <v-radio v-for="n in rglt_chk_yn.cd.length" :key="n" :label="rglt_chk_yn.cd_nm[n - 1]" :value="rglt_chk_yn.cd[n - 1]" :disabled="!modify"></v-radio>
                </v-radio-group>
                <v-textarea ref="rgltchkrsltcntn" v-model="rsltData.rglt_chk_rslt_cntn" outlined single-line :hide-details="true" height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
            <v-row v-show="frmData.chmt_mtra_rgln_exmt_excl_txt !== undefined">
              <v-col cols="1">
                <!-- 제외요청 사유 -->
                <div class="label_tit">{{ $t('LB00001295') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="chmtmtrarglnexmtexcltxt"
                  v-model="frmData.chmt_mtra_rgln_exmt_excl_txt"
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
                <v-textarea ref="rgltchkrejccntn" v-model="rsltData.rglt_chk_rejc_cntn" outlined single-line :hide-details="true" height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- 경고표지 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000038') }}</h3>
        <v-spacer></v-spacer>
        <!--첨부된 MSDS의 “2번 유해성·위험성에서 확인 가능합니다.-->
        <div class="label_info ml-1">{{ $t('LB00000613') }}</div>
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
                <div class="label_tit essn">{{ $t('LB00000173') }}</div>
              </v-col>
              <v-col cols="11">
                <v-radio-group ref="sgwCd" v-model="alertData.sgw_cd" row :hide-details="true">
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
                      <td>
                        <img src="~assets/images/P01.png" alt="" :class="{ img_unchk: !picData.ck1 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck1"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('1')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck1"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('1')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td align="center">
                        <img src="~assets/images/P02.png" alt="" :class="{ img_unchk: !picData.ck2 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck2"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('2')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck2"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('2')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P03.png" alt="" :class="{ img_unchk: !picData.ck3 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck3"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('3')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck3"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('3')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P04.png" alt="" :class="{ img_unchk: !picData.ck4 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck4"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('4')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck4"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('4')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P05.png" alt="" :class="{ img_unchk: !picData.ck5 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck5"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('5')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck5"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('5')"
                          ></v-checkbox>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~assets/images/P06.png" alt="" :class="{ img_unchk: !picData.ck6 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck6"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('6')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck6"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('6')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P07.png" alt="" :class="{ img_unchk: !picData.ck7 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck7"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('7')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck7"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('7')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P08.png" alt="" :class="{ img_unchk: !picData.ck8 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck8"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('8')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck8"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('8')"
                          ></v-checkbox>
                        </div>
                      </td>
                      <td>
                        <img src="~assets/images/P09.png" alt="" :class="{ img_unchk: !picData.ck9 }" />
                        <div style="width: 80px; margin-left: auto; margin-right: auto">
                          <v-checkbox
                            v-model="picData.ck9"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000212')"
                            @click="chkboxChngPrint('9')"
                          ></v-checkbox>
                          <v-checkbox
                            v-model="picTempData.ck9"
                            height="30"
                            :hide-details="true"
                            :disabled="!modify"
                            :label="$t('LB00000391')"
                            @click="chkboxChngTgt('9')"
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
                <div class="label_tit essn">{{ $t('LB00000385') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="harsCntn" v-model="alertData.hars_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
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
                      <v-textarea ref="prvCntn" v-model="alertData.prv_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 대응 -->
                      <div class="label_tit">{{ $t('LB00000190') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="actCntn" v-model="alertData.act_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 저장 -->
                      <div class="label_tit">{{ $t('LB00000045') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="strCntn" v-model="alertData.str_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="1">
                      <!-- 폐기 -->
                      <div class="label_tit">{{ $t('LB00000189') }}</div>
                    </v-col>
                    <v-col cols="11">
                      <v-textarea ref="scrCntn" v-model="alertData.scr_cntn" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
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
                  @click="popVendor"
                ></v-text-field>
                <v-checkbox v-model="t_kyin_yn" label="직접입력" height="30" :hide-details="true" class="ml-1" @click="vndrFlip"></v-checkbox>
              </v-col>
              <v-col cols="6" style="display: flex">
                <div class="label_info ml-1">
                  <!-- 첨부된 MSDS의 “1번 화학제품과 회사에 관한 정보” 에서 확인 가능합니다. -->
                  {{ $t('LB00000614') }}
                </div>
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
                  v-model="alertData.vndr_nm"
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
                <div class="label_tit">{{ $t('LB00000413') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrAddr"
                  v-model="alertData.vndr_addr"
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
                <div class="label_tit essn">{{ $t('LB00000412') }}</div>
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
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 공급자 연락처 -->
                <div class="label_tit essn">{{ $t('LB00000411') }}</div>
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
                  clearable
                  height="30"
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
                  ref="vndrRprsNm"
                  v-model="alertData.vndr_rprs_nm"
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
                <div class="label_tit">{{ $t('LB00000409') }}</div>
              </v-col>
              <v-col cols="5">
                <v-text-field
                  ref="vndrCoregNo"
                  v-model="alertData.vndr_coreg_no"
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
      <!-- 결재 -->
      <v-btn v-if="!exc_yn && reject_af && authChk" depressed width="120" color="primary" height="40" @click="chkAccept()">
        {{ $t('LB00000146') }}
      </v-btn>
      <!-- 검토반려 -->
      <v-btn v-if="!exc_yn && btn_reject_yn && authChk" depressed width="120" color="primary" height="40" @click="chkReject()">
        {{ $t('LB00000419') }}
      </v-btn>
      <!-- 임시저장 -->
      <v-btn v-if="!exc_yn && reject_af && authChk" depressed width="120" color="primary" height="40" @click="save">
        {{ $t('LB00001234') }}
      </v-btn>
      <!-- 검토제외 승인 -->
      <v-btn v-if="exc_yn && authChk" depressed width="120" color="primary" height="40" @click="chkException()">{{ $t('LB00001294') }}</v-btn>
      <!-- 검토제외 반려 -->
      <v-btn v-if="exc_yn && authChk" depressed width="120" color="primary" height="40" @click="chkExcReject()">{{ $t('LB00001287') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popup ref="pop" mkey="rgltchkimkedtvndrmtrlslct" @close="submitSc" />
    <popup2 ref="pop2" mkey="rgltchkimkedtvndrmroslct" @close="submitTh" />
    <popup3 ref="pop3" mkey1="rgltchkimkedtsbstslct1" mkey2="rgltchkimkedtsbstslct2" @close="popclose" />
    <popup4 ref="pop4" mkey="rgltchkimkedtrgltsbstslct" @close="sbstclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/vndrmtrlslct'
import popup2 from '../../common/vndrmroslct'
import popup3 from '../../common/sbstmultislct'
import popup4 from '../rgltsbstslct'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '규제검토-규제검토상세',
    key(route) {
      return `/rgltchk/rgltchk/${route.params.catalog}`
    },
  },
  components: {
    popup,
    popup2,
    popup3,
    popup4,
    FileUpload,
  },
  data() {
    return {
      user: {},
      menu1: false,
      menu2: false,
      t_kyin_yn: false,
      btn_reject_yn: false,
      reject_af: true,
      kyin_yn: false,
      modify: true,
      mtrlModify: false,
      exc_yn: false,
      rgltChkNo: '',
      basic: 'label_tit',
      puse_yn: '',
      mtrl_puse_cd: '',
      tmpSchData: {},
      tmpDtlSchData: {},
      schData: {},
      sbstSchData: {},
      frmData: {
        ckFlag: false,
        rglt_chk_no: '',
        chk_req_clsf_cd: '',
        wkpl_id: '',
        wkpl_nm: '',
        rglt_chk_prog_stt_cd: '',
        aprv_no: '',
        aprv_crt_dt: '',
        aprv_cmpt_dt: '',
        rglt_chk_req_da: '',
        rglt_chk_req_dt: '',
        rglt_chk_req_emp_no: '',
        rglt_chk_req_emp_nm: '',
        mtrl_no: '',
        mtrl_nm: '',
        mtrl_clsf_cd: '',
        mtrl_clsf_nm: '',
        mtrl_kind_cd: '',
        macl_cd: '',
        macl_nm: '',
        mtrl_reg_emp_no: '',
        mtrl_reg_emp_nm: '',
        mtrl_reg_dt: '',
        mtrl_reg_da: '',
        phase_cd: '',
        phase_nm: '',
        mtrl_puse_cd: '',
        mtrl_puse_nm: '',
        mtrl_puse_cntn: '',
        pchs_unit_cd: '',
        pchs_unit_nm: '',
        pchs_unit_weght: '',
        mtrl_plant: '',
        mtrl_site: '',
        rglt_chk_emp_nm: '',
        rglt_chk_emp_no: '',
        rglt_chk_dt: '',
        msds_ver: '',
        ko_atfile_no: '',
        en_atfile_no: '',
        ko_atfile_nm: '',
        en_atfile_nm: '',
        ko_atfile_key: '',
        en_atfile_key: '',
        enact_da: '',
        rev_da: '',
        dngr_kind_cd: '',
        dngr_dtl_kind_cd: '',
        rgln_exmt_excl_rq_rsn_cd: '',
      },
      frmTempData: {},
      sbstData: {},
      alertData: {
        hars_cntn: '',
        vndr_nm: '',
        nat_cd: '',
      },
      alertTempData: {},
      rsltData: {
        rglt_chk_emp_nm: '',
        rglt_chk_dt: '',
        rglt_chk_mtrl_use_nm: '',
        rglt_chk_rslt_cntn: '',
        rglt_chk_rejc_cntn: '',
        rglt_chk_mtrl_use_yn: '',
        dngr_kind_cd: '',
        dngr_dtl_kind_cd: '',
      },
      rsltTempData: {},
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
      picTempData: {
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
      picTData: {},
      picChgData: {},
      cnstTotalcnt: 0,
      sbstTotalcnt: 0,
      rgltTotalcnt: 0,
      codeid: ['CH00000009', 'CO00000003', 'CH00000004', 'CO00000018', 'CH00000017', 'CH00000026', 'CH00000027', 'CH00000028', 'F_CH00000027', 'F_CH00000028'],
      codes: {
        CH00000009: [{ cd: '', cd_nm: '' }],
        CO00000003: [{ cd: '', cd_nm: '' }],
        CH00000004: [{ cd: '', cd_nm: '' }],
        CO00000018: [{ cd: '', cd_nm: '' }],
        CO00000018p: [],
        CH00000017: [{ cd: '', cd_nm: '' }],
        CH00000026: [{ cd: '', cd_nm: '' }],
        CH00000027: [{ cd: '', cd_nm: '' }],
        CH00000028: [{ cd: '', cd_nm: '' }],
        F_CH00000027: [{ cd: '', cd_nm: '' }],
        F_CH00000028: [{ cd: '', cd_nm: '' }],
      },
      selCH00000009: {
        cd: [],
        cd_nm: [],
      },
      selCH00000004: {
        cd: [],
        cd_nm: [],
      },
      rglt_chk_yn: {
        cd: ['Y', 'N'],
        cd_nm: ['사용가능', '사용불가능'],
      },
      authChk: true,
      dngrChk: true,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000420') + '(IMK)'
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.rgltChkNo = this.$route.params.rgltchkimkedt
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
        res.CH00000026.forEach((e) => {
          this.codes.CH00000026.push({ cd: e.cd, cd_nm: e.cd_nm })
        })
        this.codes.CH00000026.unshift({ cd: '', cd_nm: 'SELECT' })
        res.CH00000027.forEach((e) => {
          this.codes.CH00000027.push({ cd: e.cd, cd_nm: e.cd_nm })
        })
        this.codes.CH00000027.unshift({ cd: '', cd_nm: 'SELECT' })
        res.CH00000028.forEach((e) => {
          this.codes.CH00000028.push({ cd: e.cd, cd_nm: e.cd_nm })
        })
        this.codes.F_CH00000027.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.F_CH00000028.unshift({ cd: '', cd_nm: 'SELECT' })

        // 그리드 세팅
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
            fieldName: 'sbst_no',
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
          {
            fieldName: 'sbst_seq',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'kyin_yn',
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
          {
            fieldName: 'sbst_seq',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lggosi_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_da',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_law_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_law_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'lgnoti_law_cntn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_crtr_rati',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_crtr_cntn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_sbst_note',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: 'ID',
            name: 'id',
            fieldName: 'id',
            width: '50',
            styles: {
              textAlignment: 'center',
            },
          },
          {
            header: this.$t('LB00000270'), // 물질구분
            name: 'sbst_type_nm',
            fieldName: 'sbst_type_nm',
            width: '100',
            editable: false,
          },
          {
            header: this.$t('LB00000394'), // 물질코드
            name: 'sbst_no',
            fieldName: 'sbst_no',
            width: '200',
            editable: false,
          },
          {
            header: 'CAS No.',
            name: 'cas_no',
            fieldName: 'cas_no',
            width: '100',
            editable: false,
            styleCallback(grid, dataCell) {
              const ret = {}
              const sbstTypeCd = grid.getValue(dataCell.index.itemIndex, 'sbst_type_cd')
              const kyinYn = grid.getValue(dataCell.index.itemIndex, 'kyin_yn')
              // eslint-disable-next-line eqeqeq
              if (sbstTypeCd != '10' && kyinYn == 'Y') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000196'), // 물질명
            name: 'sbst_nm',
            fieldName: 'sbst_nm',
            width: '100',
            styleName: 'left',
            editable: false,
            styleCallback(grid, dataCell) {
              const ret = {}
              const sbstTypeCd = grid.getValue(dataCell.index.itemIndex, 'sbst_type_cd')
              const kyinYn = grid.getValue(dataCell.index.itemIndex, 'kyin_yn')
              // eslint-disable-next-line eqeqeq
              if (sbstTypeCd != '10' && kyinYn == 'Y') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000195'), // 함량(%)
            name: 'sbst_rati',
            fieldName: 'sbst_rati',
            width: '100',
            styleName: 'right',
            editor: {
              type: 'number',
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
            header: this.$t('LB00000095'), // 비고
            name: 'sbst_note',
            fieldName: 'sbst_note',
            width: '100',
            styleName: 'left',
            editable: true,
          },
          {
            header: 'kyin', // 키인
            name: 'kyin_yn',
            fieldName: 'kyin_yn',
            width: '100',
            editable: true,
          },
        ]

        const columns2 = [
          {
            header: 'ID',
            name: 'id',
            fieldName: 'id',
            width: '50',
            styles: {
              textAlignment: 'center',
            },
          },
          {
            header: this.$t('LB00000272'), // 법
            name: 'law_nm',
            fieldName: 'law_nm',
            width: '150',
          },
          {
            header: this.$t('LB00000028'), // 규제
            name: 'rglt_nm',
            fieldName: 'rglt_nm',
            width: '200',
          },
          {
            header: this.$t('LB00000392'), // 대상여부
            name: 'tgt_yn',
            fieldName: 'tgt_yn',
            width: '100',
            editable: false,
            renderer: {
              type: 'check',
              editable: true,
            },
          },
          {
            header: this.$t('LB00000408'), // 대상물질
            name: 'cas_no',
            fieldName: 'cas_no',
            width: '100',
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
        gridView.setCheckBar({ visible: true })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('id', 'visible', false)
        gridView.setColumnProperty('sbst_no', 'visible', false)
        gridView.setColumnProperty('kyin_yn', 'visible', false)

        dataProvider2 = new LocalDataProvider(false)
        dataProvider2.setFields(fields2)
        gridView2 = new GridView('realgrid3')
        gridView2.setDataSource(dataProvider2)
        gridView2.setColumns(columns2)
        gridView2.setFooters({ visible: false })
        gridView2.setStateBar({ visible: false })
        gridView2.setCheckBar({ visible: false })
        gridView2.editOptions.editable = true
        gridView2.displayOptions.selectionStyle = 'block'

        gridView2.header.height = 39
        gridView2.displayOptions.rowHeight = 40
        gridView2.footer.height = 40
        gridView2.displayOptions.fitStyle = 'fill'

        gridView2.setColumnProperty('id', 'visible', false)

        gridView2.onEditRowChanged = function (grid, itemIndex, dataRow, field, oldValue, newValue) {
          gridView2.commit()
          const current = gridView2.getCurrent()

          console.log('onEditRowChanged, ' + current + ': ' + field + ': ' + oldValue + ' => ' + newValue) // eslint-disable-line no-console

          if (current.column === 'tgt_yn') {
            const rgltNo = gridView2.getValue(current.dataRow, 'rglt_no')

            if (newValue === true) {
              this.$refs.pop4.open(true, this.sbstData, rgltNo)
            } else {
              dataProvider2.setValue(current.dataRow, 'sbst_no', '')
              dataProvider2.setValue(current.dataRow, 'cas_no', '')

              // 규제검토-위험물안전관리법-위험물(rglt_no : RGLT000020) 대상여부 해제
              if (gridView2.getValue(current.dataRow, 'rglt_no') === 'RGLT000020' && this.isEmpty(gridView2.getValue(current.dataRow, 'cas_no'))) {
                // 위험물 여부 초기화 및 사용제한
                this.frmData.dngr_kind_cd = '9'
                this.codChg2(this.frmData.dngr_kind_cd)
                // this.codes.F_CH00000028 = { cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' }
                this.dngrChk = true
              }
            }
          }
        }.bind(this)

        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    chkFile() {
      this.frmData.ckFlag = true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/ch/rgltchk/rglt-chk-dtl-edt/${this.rgltChkNo}`)

      if (data.auth[0].auth !== 'Y') {
        this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
        this.authChk = false

        // 화학물질 TO-DO 알림 메일의 바로가기 클릭시 권한이 없는 경우 상세 페이지로 이동
        this.$router.push({
          name: `rgltchk-rgltchkimklup-rgltchkimklup`,
          params: {
            rgltchkimklup: this.rgltChkNo,
          },
        })
      }

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        if (this.isEmpty(this.frmData.mtrl_puse_cd)) {
          this.mtrl_puse_cd = { value: '', label: 'SELECT' }
        } else {
          this.mtrl_puse_cd = { value: this.frmData.mtrl_puse_cd, label: this.frmData.mtrl_puse_nm }
        }

        // 검토반려 버튼 활성화체크
        const sttCd = this.frmData.rglt_chk_prog_stt_cd
        if (sttCd === '30' || sttCd === '50') {
          this.btn_reject_yn = true
        } else {
          this.btn_reject_yn = false
        }

        const excCd = this.frmData.rgln_exmt_excl_rq_rsn_cd
        if (excCd !== undefined) {
          this.exc_yn = true
        }

        // if (this.frmData.mtrl_kind_cd === '3') {
        //   this.btn_reject_yn = false
        // }

        if (this.frmData.mtrl_puse_cd === 'U55') {
          this.mtrlModify = true
          this.puse_yn = 'essn'
        } else {
          this.mtrlModify = false
          this.puse_yn = ''
        }

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
      } else {
        this.frmData = []
        this.frmTempData = []
      }

      this.sbstData = data.sbst
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
        this.rsltTempData = JSON.parse(JSON.stringify(this.rsltData)) // 수정여부 비교 데이터
      } else {
        this.rsltTempData = JSON.parse(JSON.stringify(this.rsltData))
        this.rsltData.rglt_chk_mtrl_use_yn = 'Y'
      }

      // 물질 위험여부 사용여부 처리
      data.rglt.forEach((i) => {
        // 처리위험물안전관리법 && mtrl_no: "M001804"(위험물) && 대상
        if (i.law_cd === 'L04' && i.rglt_no === 'RGLT000020' && i.org_tgt_yn === 'Y') {
          this.dngrChk = false
        }
      })

      if (this.dngrChk === false) {
        // SELECT 초기화
        if (this.isEmpty(data.basic[0].dngr_kind_cd)) {
          this.frmData.dngr_kind_cd = ''
          this.codChg2(this.frmData.dngr_kind_cd)
        } else {
          // 위험물 여부 상세 조회 및 설정
          this.codChg2(data.basic[0].dngr_kind_cd)
        }
      } else {
        // 해당없음 초기화
        this.frmData.dngr_kind_cd = '9'
        this.codChg2(this.frmData.dngr_kind_cd)
      }

      if (data.alert.length > 0) {
        this.alertData = data.alert[0]
        this.alertTempData = JSON.parse(JSON.stringify(this.alertData)) // 수정여부 비교 데이터
        if (this.alertData.vndr_kyin_yn === 'Y') {
          this.t_kyin_yn = true
          this.kyin_yn = true
        } else {
          this.t_kyin_yn = false
          this.kyin_yn = false
        }
      } else {
        this.alertData = []
        this.alertData.sgw_cd = '4'
        this.alertTempData = []
        this.alertTempData.sgw_cd = '4'
      }

      this.picTData.pic = []
      if (data.pic.length > 0) {
        for (let i = 0; i < data.pic.length; i++) {
          if (data.pic[i].picg_cd === '1') {
            this.picData.ck1 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck1 = true
              this.picTData.pic.push({ picg_cd: '1', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '1', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '2') {
            this.picData.ck2 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck2 = true
              this.picTData.pic.push({ picg_cd: '2', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '2', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '3') {
            this.picData.ck3 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck3 = true
              this.picTData.pic.push({ picg_cd: '3', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '3', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '4') {
            this.picData.ck4 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck4 = true
              this.picTData.pic.push({ picg_cd: '4', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '4', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '5') {
            this.picData.ck5 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck5 = true
              this.picTData.pic.push({ picg_cd: '5', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '5', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '6') {
            this.picData.ck6 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck6 = true
              this.picTData.pic.push({ picg_cd: '6', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '6', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '7') {
            this.picData.ck7 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck7 = true
              this.picTData.pic.push({ picg_cd: '7', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '7', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '8') {
            this.picData.ck8 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck8 = true
              this.picTData.pic.push({ picg_cd: '8', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '8', print_yn: 'N' })
            }
          } else if (data.pic[i].picg_cd === '9') {
            this.picData.ck9 = true
            if (data.pic[i].print_yn === 'Y') {
              this.picTempData.ck9 = true
              this.picTData.pic.push({ picg_cd: '9', print_yn: 'Y' })
            } else {
              this.picTData.pic.push({ picg_cd: '9', print_yn: 'N' })
            }
          }
        }
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
    popVendor() {
      let mode = ''
      if (this.isEmpty(this.frmData.mtrl_no)) {
        this.$toast.show(this.$t('MS00000096'), { className: 'toast_error' }) // 자재를 먼저 선택해주세요.
        return
      }
      // 1 == mtr, 2 == mro
      if (this.frmData.mtrl_clsf_cd === '1') {
        mode = '1'
        this.$refs.pop.open(mode)
      } else if (this.frmData.mtrl_clsf_cd === '2') {
        mode = '2'
        this.$refs.pop2.open(mode)
      }
    },
    submitSc(rows) {
      if (rows === undefined) return
      this.$set(this.alertData, 'vndr_no', rows.vndr_no)
      this.alertData.vndr_nm = rows.vndr_nm
      this.alertData.vndr_phon_no = rows.vndr_phon_no
      this.alertData.vndr_addr = rows.vndr_addr
      this.alertData.nat_cd = rows.nat_cd
      this.alertData.vndr_coreg_no = rows.vndr_coreg_no
      this.alertData.vndr_rprs_nm = rows.vndr_rprs_nm
      this.alertData.vndr_email = rows.vndr_email
      this.alertData.vndr_type_cd = rows.vndr_type_cd
      this.$forceUpdate()
    },
    submitTh(rows) {
      if (rows === undefined) return
      this.$set(this.alertData, 'vndr_no', rows.vndr_no)
      this.alertData.vndr_nm = rows.vndr_nm
      this.alertData.vndr_phon_no = rows.vndr_phon_no
      this.alertData.vndr_addr = rows.vndr_addr
      this.alertData.nat_cd = rows.nat_cd
      this.alertData.vndr_coreg_no = rows.vndr_coreg_no
      this.alertData.vndr_rprs_nm = rows.vndr_rprs_nm
      this.alertData.vndr_email = rows.vndr_email
      this.alertData.vndr_type_cd = rows.vndr_type_cd
      this.$forceUpdate()
    },
    async vndrFlip() {
      if (this.t_kyin_yn && !this.isEmpty(this.alertData.vndr_nm)) {
        const res = await this.$confirm(this.$t('MS00000108')) // 공급자를 직접 입력하시면 설정되어있던 내용이 사라집니다. 선택하시겠습니까?
        if (!res) {
          this.t_kyin_yn = false
          this.kyin_yn = false
          return
        }

        this.alertData.vndr_nm = ''
        this.alertData.vndr_phon_no = ''
        this.alertData.vndr_addr = ''
        this.alertData.nat_cd = ''
        this.alertData.vndr_coreg_no = ''
        this.alertData.vndr_type_cd = '2'
        this.$set(this.alertData, 'vndr_rprs_nm', '')
        this.$forceUpdate()
        this.kyin_yn = true
      } else if (!this.t_kyin_yn && !this.isEmpty(this.alertData.vndr_nm)) {
        const res = await this.$confirm(this.$t('MS00000177')) // 공급자를 선택하시면 직접입력한 내용이 사라집니다. 선택하시겠습니까?
        if (!res) {
          this.t_kyin_yn = true
          this.kyin_yn = true
          return
        }

        this.alertData.vndr_nm = ''
        this.alertData.vndr_phon_no = ''
        this.alertData.vndr_addr = ''
        this.alertData.nat_cd = ''
        this.alertData.vndr_coreg_no = ''
        this.alertData.vndr_type_cd = ''
        this.$set(this.alertData, 'vndr_rprs_nm', '')
        this.$forceUpdate()
        this.kyin_yn = false
      } else if (this.t_kyin_yn) {
        this.kyin_yn = true
      } else {
        this.kyin_yn = false
      }
    },
    sbstAdd() {
      gridView.commit()
      this.$refs.pop3.open(this.schData, true)
    },
    async popclose(returnData) {
      console.log(returnData) // eslint-disable-line no-console
      gridView.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          let passyn = null
          for (let j = 0; j < dataProvider.getRowCount(); j++) {
            if (dataProvider.getRowState(j) !== 'deleted' && dataProvider.getRowState(j) !== 'createAndDeleted') {
              if (i.sbst_no === dataProvider.getValue(j, 'sbst_no') && i.sbst_no !== '100000000001') {
                passyn = 'Y'
              }
            }
          }

          if (passyn === null) {
            dataProvider.addRow({
              rglt_chk_no: this.frmData.rglt_chk_no,
              chk_req_clsf_cd: this.frmData.chk_req_clsf_cd,
              mtrl_no: this.frmData.mtrl_no,
              sbst_no: i.sbst_no,
              sbst_type_cd: i.sbst_type_cd,
              sbst_type_nm: i.sbst_type_nm,
              cas_no: i.cas_no,
              sbst_nm: i.sbst_ko_nm,
              sbstn_ratio: 0,
              kyin_yn: i.kyin_yn,
            })
          } else {
            this.$toast.show(i.sbst_ko_nm + this.$t('MS00000132'), { className: 'toast_error' }) // 물질이 이미 존재합니다.
          }
        })
        // 예상 규제 조회
        // this.selectSbstAddDel()
      }
    },
    sbstDel() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)

      // 예상 규제 조회
      // this.selectSbstAddDel()
    },
    async chkAccept() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        } else {
          this.frmData.en_atfile_no = null
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      // Validation 체크
      if (!this.validate()) {
        // 첨부파일 불러오기
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
        return
      }

      if (this.isEmpty(this.frmData.dngr_kind_cd) || (this.dngrChk === false && this.frmData.dngr_kind_cd === '9')) {
        this.$toast.show(this.$t('MS00000233'), { className: 'toast_error' }) // 위험물여부를 선택해 주세요.
        this.$refs.dngr_kind_cd.focus()
        return
      }
      if (this.isEmpty(this.frmData.dngr_dtl_kind_cd) || (this.dngrChk === false && this.frmData.dngr_dtl_kind_cd === '999')) {
        this.$toast.show(this.$t('MS00000234'), { className: 'toast_error' }) // 위험물여부 상세를 선택해 주세요.
        this.$refs.dngr_dtl_kind_cd.focus()
        return
      }
      // if (this.isEmpty(this.rsltData.rglt_chk_rslt_cntn)) {
      //   this.$toast.show(this.$t('MS00000223'), { className: 'toast_error' }) // 검토결과 내용을 입력해주세요.
      //   this.$refs.rgltchkrsltcntn.focus()
      //   return
      // }
      if (!this.isEmpty(this.frmData.pchs_unit_cd) && !(this.frmData.pchs_unit_cd === '2')) {
        this.$toast.show(this.$t('MS00000243'), { className: 'toast_error' }) // 단위중량은 g으로만 선택해주세요.
        this.$refs.pchs_unit_cd.focus()
        return
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재 하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0) {
        const rgltRow = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider2.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider2.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider2.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider2.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider2.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider2.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider2.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider2.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider2.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider2.getValue(i, 'rglt_abbr')
          if (dataProvider2.getValue(i, 'tgt_yn') === true) {
            temp.tgt_yn = 'Y'
          } else {
            temp.tgt_yn = 'N'
          }
          temp.org_tgt_yn = dataProvider2.getValue(i, 'org_tgt_yn')
          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_note = dataProvider2.getValue(i, 'rglt_note')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }

      let date = new Date()
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      date = this.getTzDateObj(this.user.time_zone)

      const rsltRow = []
      const rsltTemp = {}
      rsltTemp.rglt_chk_no = this.frmData.rglt_chk_no
      rsltTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      rsltTemp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      rsltTemp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      rsltTemp.rglt_chk_dt_timezone = this.get_date_str_gubun(date, '')
      rsltTemp.username = this.user.emp_no
      rsltTemp.rglt_chk_mtrl_use_yn = this.rsltData.rglt_chk_mtrl_use_yn
      rsltTemp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      rsltRow.push(rsltTemp)
      this.frmData.rsltRow = rsltRow

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.macl_cd = this.frmData.macl_cd
      temp.username = this.user.emp_no
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.pchs_unit_cd = this.frmData.pchs_unit_cd
      temp.pchs_unit_nm = this.frmData.pchs_unit_nm
      temp.pchs_unit_weght = this.frmData.pchs_unit_weght
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      temp.msds_ver = this.frmData.msds_ver
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      if (!this.isEmpty(this.frmData.enact_da)) {
        temp.enact_da = this.replaceAll(this.frmData.enact_da, '')
      }
      if (!this.isEmpty(this.frmData.rev_da)) {
        temp.rev_da = this.replaceAll(this.frmData.rev_da, '')
      }
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      this.frmData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (this.t_kyin_yn === true) {
        this.alertData.vndr_kyin_yn = 'Y'
      } else {
        this.alertData.vndr_kyin_yn = 'N'
      }

      const alertRow = []
      const alertTemp = {}
      alertTemp.rglt_chk_no = this.frmData.rglt_chk_no
      alertTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      alertTemp.mtrl_no = this.frmData.mtrl_no
      alertTemp.vndr_kyin_yn = this.alertData.vndr_kyin_yn
      alertTemp.sgw_cd = this.alertData.sgw_cd
      alertTemp.hars_cntn = this.alertData.hars_cntn
      alertTemp.prv_cntn = this.alertData.prv_cntn
      alertTemp.act_cntn = this.alertData.act_cntn
      alertTemp.str_cntn = this.alertData.str_cntn
      alertTemp.scr_cntn = this.alertData.scr_cntn
      alertTemp.vndr_no = this.alertData.vndr_no
      alertTemp.vndr_nm = this.alertData.vndr_nm
      alertTemp.nat_cd = this.alertData.nat_cd
      alertTemp.vndr_addr = this.alertData.vndr_addr
      alertTemp.vndr_phon_no = this.alertData.vndr_phon_no
      alertTemp.vndr_rprs_nm = this.alertData.vndr_rprs_nm
      alertTemp.vndr_coreg_no = this.alertData.vndr_coreg_no
      alertTemp.vndr_type_cd = this.alertData.vndr_type_cd
      alertTemp.username = this.user.emp_no
      alertRow.push(alertTemp)
      this.frmData.alertRow = alertRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.rgltAcpt()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async rgltAcpt() {
      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      temp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-rglt-accept', this.frmData).then((res) => {
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
          window.open(url, 'rglt-chk-rglt-accept', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `rgltchk-rgltchkimklup-rgltchkimklup`,
            params: {
              rgltchkimklup: this.frmData.rglt_chk_no,
              schData: {
                mtrl_no: this.frmData.mtrl_no,
                rglt_chk_no: this.frmData.rglt_chk_no,
                chk_req_clsf_cd: this.frmData.chk_req_clsf_cd,
              },
              tmpSchData: {
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
    async chkReject() {
      gridView.commit()
      gridView2.commit()

      /*
      if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        this.rsltData.rglt_chk_emp_nm === this.rsltTempData.rglt_chk_emp_nm &&
        this.rsltData.rglt_chk_dt === this.rsltTempData.rglt_chk_dt &&
        this.rsltData.rglt_chk_mtrl_use_nm === this.rsltTempData.rglt_chk_mtrl_use_nm &&
        this.rsltData.rglt_chk_rslt_cntn === this.rsltTempData.rglt_chk_rslt_cntn &&
        this.rsltData.rglt_chk_rejc_cntn === this.rsltTempData.rglt_chk_rejc_cntn &&
        this.rsltData.rglt_chk_mtrl_use_yn === this.rsltTempData.rglt_chk_mtrl_use_yn &&
        JSON.stringify(this.alertData) === JSON.stringify(this.alertTempData) &&
        JSON.stringify(this.picChgData) === JSON.stringify(this.picTData)
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }
      */

      /*
      if (this.isEmpty(this.frmData.dngr_kind_cd) || (this.dngrChk === false && this.frmData.dngr_kind_cd === '9')) {
        this.$toast.show(this.$t('MS00000233'), { className: 'toast_error' }) // 위험물여부를 선택해 주세요.
        this.$refs.dngr_kind_cd.focus()
        return
      }
      if (this.isEmpty(this.frmData.dngr_dtl_kind_cd) || (this.dngrChk === false && this.frmData.dngr_dtl_kind_cd === '999')) {
        this.$toast.show(this.$t('MS00000234'), { className: 'toast_error' }) // 위험물여부 상세를 선택해 주세요.
        this.$refs.dngr_dtl_kind_cd.focus()
        return
      }
      */
      if (this.isEmpty(this.rsltData.rglt_chk_rejc_cntn)) {
        this.$toast.show(this.$t('MS00000195'), { className: 'toast_error' }) // 검토반려 내용을 입력해주세요.
        this.$refs.rgltchkrejccntn.focus()
        return
      }

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      /*
      // Validation 체크
      if (!this.validate()) {
        // 첨부파일 불러오기
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
        return
      }
      */

      const res = await this.$confirm(this.$t('MS00000181')) // 검토반려 하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0) {
        const rgltRow = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider2.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider2.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider2.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider2.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider2.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider2.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider2.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider2.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider2.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider2.getValue(i, 'rglt_abbr')
          if (dataProvider2.getValue(i, 'tgt_yn') === true) {
            temp.tgt_yn = 'Y'
          } else {
            temp.tgt_yn = 'N'
          }
          temp.org_tgt_yn = dataProvider2.getValue(i, 'org_tgt_yn')
          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_note = dataProvider2.getValue(i, 'rglt_note')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }

      let date = new Date()
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      date = this.getTzDateObj(this.user.time_zone)

      const rsltRow = []
      const rsltTemp = {}
      rsltTemp.rglt_chk_no = this.frmData.rglt_chk_no
      rsltTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      rsltTemp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      rsltTemp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      rsltTemp.rglt_chk_dt_timezone = this.get_date_str_gubun(date, '')
      rsltTemp.username = this.user.emp_no
      rsltTemp.rglt_chk_mtrl_use_yn = this.rsltData.rglt_chk_mtrl_use_yn
      rsltTemp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      rsltRow.push(rsltTemp)
      this.frmData.rsltRow = rsltRow

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.macl_cd = this.frmData.macl_cd
      temp.username = this.user.emp_no
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.pchs_unit_cd = this.frmData.pchs_unit_cd
      temp.pchs_unit_nm = this.frmData.pchs_unit_nm
      temp.pchs_unit_weght = this.frmData.pchs_unit_weght
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      temp.msds_ver = this.frmData.msds_ver
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      if (!this.isEmpty(this.frmData.enact_da)) {
        temp.enact_da = this.replaceAll(this.frmData.enact_da, '')
      }
      if (!this.isEmpty(this.frmData.rev_da)) {
        temp.rev_da = this.replaceAll(this.frmData.rev_da, '')
      }
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      this.frmData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (this.t_kyin_yn === true) {
        this.alertData.vndr_kyin_yn = 'Y'
      } else {
        this.alertData.vndr_kyin_yn = 'N'
      }

      const alertRow = []
      const alertTemp = {}
      alertTemp.rglt_chk_no = this.frmData.rglt_chk_no
      alertTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      alertTemp.mtrl_no = this.frmData.mtrl_no
      alertTemp.vndr_kyin_yn = this.alertData.vndr_kyin_yn
      alertTemp.sgw_cd = this.alertData.sgw_cd
      alertTemp.hars_cntn = this.alertData.hars_cntn
      alertTemp.prv_cntn = this.alertData.prv_cntn
      alertTemp.act_cntn = this.alertData.act_cntn
      alertTemp.str_cntn = this.alertData.str_cntn
      alertTemp.scr_cntn = this.alertData.scr_cntn
      alertTemp.vndr_no = this.alertData.vndr_no
      alertTemp.vndr_nm = this.alertData.vndr_nm
      alertTemp.nat_cd = this.alertData.nat_cd
      alertTemp.vndr_addr = this.alertData.vndr_addr
      alertTemp.vndr_phon_no = this.alertData.vndr_phon_no
      alertTemp.vndr_rprs_nm = this.alertData.vndr_rprs_nm
      alertTemp.vndr_coreg_no = this.alertData.vndr_coreg_no
      alertTemp.vndr_type_cd = this.alertData.vndr_type_cd
      alertTemp.username = this.user.emp_no
      alertRow.push(alertTemp)
      this.frmData.alertRow = alertRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.rjt()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async rjt() {
      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no
      temp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-rglt-reject', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000182')) // 검토반려 되었습니다.
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
    async rgltDbReset() {
      gridView.commit()
      gridView2.commit()

      // Validation 체크
      if (!this.validate()) {
        return
      }

      const res = await this.$confirm(this.$t('MS00000183')) // 기존 데이터는 복구되지 않습니다.규제DB 기준으로 초기화하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-rglt-reset', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000184')) // 초기화하였습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        } else {
          this.frmData.en_atfile_no = null
          this.frmTempData.en_atfile_no = null
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        this.rsltData.rglt_chk_emp_nm === this.rsltTempData.rglt_chk_emp_nm &&
        this.rsltData.rglt_chk_dt === this.rsltTempData.rglt_chk_dt &&
        this.rsltData.rglt_chk_mtrl_use_nm === this.rsltTempData.rglt_chk_mtrl_use_nm &&
        this.rsltData.rglt_chk_rslt_cntn === this.rsltTempData.rglt_chk_rslt_cntn &&
        this.rsltData.rglt_chk_rejc_cntn === this.rsltTempData.rglt_chk_rejc_cntn &&
        this.rsltData.rglt_chk_mtrl_use_yn === this.rsltTempData.rglt_chk_mtrl_use_yn &&
        JSON.stringify(this.alertData) === JSON.stringify(this.alertTempData) &&
        JSON.stringify(this.picChgData) === JSON.stringify(this.picTData)
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        // 첨부파일 불러오기
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
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0) {
        const rgltRow = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider2.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider2.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider2.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider2.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider2.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider2.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider2.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider2.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider2.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider2.getValue(i, 'rglt_abbr')
          if (dataProvider2.getValue(i, 'tgt_yn') === true) {
            temp.tgt_yn = 'Y'
          } else {
            temp.tgt_yn = 'N'
          }
          temp.org_tgt_yn = dataProvider2.getValue(i, 'org_tgt_yn')
          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_note = dataProvider2.getValue(i, 'rglt_note')
          temp.username = this.user.emp_no
          temp.sbst_seq = dataProvider2.getValue(i, 'sbst_seq')
          temp.lggosi_id = dataProvider2.getValue(i, 'lggosi_id')
          temp.lgnoti_no = dataProvider2.getValue(i, 'lgnoti_no')
          temp.lgnoti_nm = dataProvider2.getValue(i, 'lgnoti_nm')
          temp.lgnoti_da = dataProvider2.getValue(i, 'lgnoti_da')
          temp.lgnoti_law_nm = dataProvider2.getValue(i, 'lgnoti_law_nm')
          temp.lgnoti_law_no = dataProvider2.getValue(i, 'lgnoti_law_no')
          temp.lgnoti_law_cntn = dataProvider2.getValue(i, 'lgnoti_law_cntn')
          temp.rglt_crtr_rati = dataProvider2.getValue(i, 'rglt_crtr_rati')
          temp.rglt_crtr_cntn = dataProvider2.getValue(i, 'rglt_crtr_cntn')
          temp.rglt_sbst_note = dataProvider2.getValue(i, 'rglt_sbst_note')

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }

      let date = new Date()
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      date = this.getTzDateObj(this.user.time_zone)
      const rsltRow = []
      const rsltTemp = {}
      rsltTemp.rglt_chk_no = this.frmData.rglt_chk_no
      rsltTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      rsltTemp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      rsltTemp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      rsltTemp.rglt_chk_dt_timezone = this.get_date_str_gubun(date, '')
      rsltTemp.username = this.user.emp_no
      rsltTemp.rglt_chk_mtrl_use_yn = this.rsltData.rglt_chk_mtrl_use_yn
      rsltTemp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      rsltRow.push(rsltTemp)
      this.frmData.rsltRow = rsltRow

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.macl_cd = this.frmData.macl_cd
      temp.username = this.user.emp_no
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.pchs_unit_cd = this.frmData.pchs_unit_cd
      temp.pchs_unit_nm = this.frmData.pchs_unit_nm
      temp.pchs_unit_weght = this.frmData.pchs_unit_weght
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      temp.msds_ver = this.frmData.msds_ver
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      if (!this.isEmpty(this.frmData.enact_da)) {
        temp.enact_da = this.replaceAll(this.frmData.enact_da, '')
      }
      if (!this.isEmpty(this.frmData.rev_da)) {
        temp.rev_da = this.replaceAll(this.frmData.rev_da, '')
      }
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      this.frmData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (this.t_kyin_yn === true) {
        this.alertData.vndr_kyin_yn = 'Y'
      } else {
        this.alertData.vndr_kyin_yn = 'N'
      }

      const alertRow = []
      const alertTemp = {}
      alertTemp.rglt_chk_no = this.frmData.rglt_chk_no
      alertTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      alertTemp.mtrl_no = this.frmData.mtrl_no
      alertTemp.vndr_kyin_yn = this.alertData.vndr_kyin_yn
      alertTemp.sgw_cd = this.alertData.sgw_cd
      alertTemp.hars_cntn = this.alertData.hars_cntn
      alertTemp.prv_cntn = this.alertData.prv_cntn
      alertTemp.act_cntn = this.alertData.act_cntn
      alertTemp.str_cntn = this.alertData.str_cntn
      alertTemp.scr_cntn = this.alertData.scr_cntn
      alertTemp.vndr_no = this.alertData.vndr_no
      alertTemp.vndr_nm = this.alertData.vndr_nm
      alertTemp.nat_cd = this.alertData.nat_cd
      alertTemp.vndr_addr = this.alertData.vndr_addr
      alertTemp.vndr_phon_no = this.alertData.vndr_phon_no
      alertTemp.vndr_rprs_nm = this.alertData.vndr_rprs_nm
      alertTemp.vndr_coreg_no = this.alertData.vndr_coreg_no
      alertTemp.vndr_type_cd = this.alertData.vndr_type_cd
      alertTemp.username = this.user.emp_no
      alertRow.push(alertTemp)
      this.frmData.alertRow = alertRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    replaceAll(str, rep) {
      return str.split('-').join(rep)
    },
    async sbstclose(returnData, rgltNo) {
      console.log('returnData : ', returnData) // eslint-disable-line no-console
      gridView2.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        // 팝업 닫기 후 해당 ROW 대상여부 체크 해제
        for (let j = 0; j < dataProvider2.getRowCount(); j++) {
          if (gridView2.getValue(j, 'rglt_no') === rgltNo) {
            dataProvider2.setValue(j, 'tgt_yn', '')
          }
        }
        return false
      } else {
        await returnData.forEach((i) => {
          let passyn = null
          for (let j = 0; j < dataProvider2.getRowCount(); j++) {
            if (
              dataProvider2.getValue(j, 'rglt_no') === rgltNo &&
              typeof dataProvider2.getValue(j, 'sbst_no') !== 'undefined' &&
              dataProvider2.getValue(j, 'sbst_no') !== null &&
              dataProvider2.getValue(j, 'sbst_no') !== ''
            ) {
              const sbstArr = dataProvider2.getValue(j, 'sbst_no').split(',')
              for (let k = 0; k < sbstArr.length; k++) {
                if (i.sbst_no === sbstArr[k]) {
                  passyn = 'Y'
                }
              }
            }
          }

          if (passyn === null) {
            for (let j = 0; j < dataProvider2.getRowCount(); j++) {
              if (dataProvider2.getValue(j, 'rglt_no') === rgltNo) {
                if (typeof dataProvider2.getValue(j, 'sbst_no') !== 'undefined' && dataProvider2.getValue(j, 'sbst_no') !== null && dataProvider2.getValue(j, 'sbst_no') !== '') {
                  dataProvider2.setValue(j, 'cas_no', dataProvider2.getValue(j, 'cas_no') + ',' + i.cas_no)
                  dataProvider2.setValue(j, 'sbst_no', dataProvider2.getValue(j, 'sbst_no') + ',' + i.sbst_no)
                } else {
                  dataProvider2.setValue(j, 'cas_no', i.cas_no)
                  dataProvider2.setValue(j, 'sbst_no', i.sbst_no)
                }

                console.log('cas_no' + dataProvider2.getValue(j, 'cas_no')) // eslint-disable-line no-console
                console.log('sbst_no' + dataProvider2.getValue(j, 'sbst_no')) // eslint-disable-line no-console
              }
            }
          } else {
            this.$toast.show(i.sbst_nm + this.$t('MS00000132'), { className: 'toast_error' }) // 물질이 이미 존재합니다.
          }
        })

        for (let j = 0; j < dataProvider2.getRowCount(); j++) {
          // 규제검토-위험물안전관리법-위험물(rglt_no : RGLT000020) 대상여부 선택 허용
          if (gridView2.getValue(j, 'rglt_no') === 'RGLT000020' && gridView2.getValue(j, 'tgt_yn') === true) {
            // 위험물 여부 초기화 및 사용제한, 해당없음 제외
            this.frmData.dngr_kind_cd = ''
            this.codChg2(this.frmData.dngr_kind_cd)
            // 대상여부 허용
            this.dngrChk = false
          }
        }
      }
    },
    // 검토제외 승인
    async chkException() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        } else {
          this.frmData.en_atfile_no = null
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      // Validation 체크
      // if (!this.validate()) {
      //   // 첨부파일 불러오기
      //   if (!this.isEmpty(this.frmData.ko_atfile_no)) {
      //     this.$axios.$get(`/api/v1/common/file/${this.frmData.ko_atfile_no}`).then((data) => {
      //       console.log('files1 : ', data) // eslint-disable-line no-console
      //       if (data.length > 0) {
      //         this.setFiles1(data)
      //       }
      //     })
      //   }
      //   if (!this.isEmpty(this.frmData.en_atfile_no)) {
      //     this.$axios.$get(`/api/v1/common/file/${this.frmData.en_atfile_no}`).then((data) => {
      //       console.log('files2 : ', data) // eslint-disable-line no-console
      //       if (data.length > 0) {
      //         this.setFiles2(data)
      //       }
      //     })
      //   }
      //   return
      // }

      // if (this.isEmpty(this.frmData.dngr_kind_cd) || (this.dngrChk === false && this.frmData.dngr_kind_cd === '9')) {
      //   this.$toast.show(this.$t('MS00000233'), { className: 'toast_error' }) // 위험물여부를 선택해 주세요.
      //   this.$refs.dngr_kind_cd.focus()
      //   return
      // }
      // if (this.isEmpty(this.frmData.dngr_dtl_kind_cd) || (this.dngrChk === false && this.frmData.dngr_dtl_kind_cd === '999')) {
      //   this.$toast.show(this.$t('MS00000234'), { className: 'toast_error' }) // 위험물여부 상세를 선택해 주세요.
      //   this.$refs.dngr_dtl_kind_cd.focus()
      //   return
      // }
      // if (this.isEmpty(this.rsltData.rglt_chk_rslt_cntn)) {
      //   this.$toast.show(this.$t('MS00000223'), { className: 'toast_error' }) // 검토결과 내용을 입력해주세요.
      //   this.$refs.rgltchkrsltcntn.focus()
      //   return
      // }
      // if (!this.isEmpty(this.frmData.pchs_unit_cd) && !(this.frmData.pchs_unit_cd === '2')) {
      //   this.$toast.show(this.$t('MS00000243'), { className: 'toast_error' }) // 단위중량은 g으로만 선택해주세요.
      //   this.$refs.pchs_unit_cd.focus()
      //   return
      // }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000651')) // 검토제외를 승인하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0) {
        const rgltRow = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider2.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider2.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider2.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider2.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider2.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider2.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider2.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider2.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider2.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider2.getValue(i, 'rglt_abbr')
          if (dataProvider2.getValue(i, 'tgt_yn') === true) {
            temp.tgt_yn = 'Y'
          } else {
            temp.tgt_yn = 'N'
          }
          temp.org_tgt_yn = dataProvider2.getValue(i, 'org_tgt_yn')
          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_note = dataProvider2.getValue(i, 'rglt_note')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }

      let date = new Date()
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      date = this.getTzDateObj(this.user.time_zone)

      const rsltRow = []
      const rsltTemp = {}
      rsltTemp.rglt_chk_no = this.frmData.rglt_chk_no
      rsltTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      rsltTemp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      rsltTemp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      rsltTemp.rglt_chk_dt_timezone = this.get_date_str_gubun(date, '')
      rsltTemp.username = this.user.emp_no
      rsltTemp.rglt_chk_mtrl_use_yn = this.rsltData.rglt_chk_mtrl_use_yn
      rsltTemp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      rsltRow.push(rsltTemp)
      this.frmData.rsltRow = rsltRow

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.macl_cd = this.frmData.macl_cd
      temp.username = this.user.emp_no
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.pchs_unit_cd = this.frmData.pchs_unit_cd
      temp.pchs_unit_nm = this.frmData.pchs_unit_nm
      temp.pchs_unit_weght = this.frmData.pchs_unit_weght
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      temp.msds_ver = this.frmData.msds_ver
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      if (!this.isEmpty(this.frmData.enact_da)) {
        temp.enact_da = this.replaceAll(this.frmData.enact_da, '')
      }
      if (!this.isEmpty(this.frmData.rev_da)) {
        temp.rev_da = this.replaceAll(this.frmData.rev_da, '')
      }
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      this.frmData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (this.t_kyin_yn === true) {
        this.alertData.vndr_kyin_yn = 'Y'
      } else {
        this.alertData.vndr_kyin_yn = 'N'
      }

      const alertRow = []
      const alertTemp = {}
      alertTemp.rglt_chk_no = this.frmData.rglt_chk_no
      alertTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      alertTemp.mtrl_no = this.frmData.mtrl_no
      alertTemp.vndr_kyin_yn = this.alertData.vndr_kyin_yn
      alertTemp.sgw_cd = this.alertData.sgw_cd
      alertTemp.hars_cntn = this.alertData.hars_cntn
      alertTemp.prv_cntn = this.alertData.prv_cntn
      alertTemp.act_cntn = this.alertData.act_cntn
      alertTemp.str_cntn = this.alertData.str_cntn
      alertTemp.scr_cntn = this.alertData.scr_cntn
      alertTemp.vndr_no = this.alertData.vndr_no
      alertTemp.vndr_nm = this.alertData.vndr_nm
      alertTemp.nat_cd = this.alertData.nat_cd
      alertTemp.vndr_addr = this.alertData.vndr_addr
      alertTemp.vndr_phon_no = this.alertData.vndr_phon_no
      alertTemp.vndr_rprs_nm = this.alertData.vndr_rprs_nm
      alertTemp.vndr_coreg_no = this.alertData.vndr_coreg_no
      alertTemp.vndr_type_cd = this.alertData.vndr_type_cd
      alertTemp.username = this.user.emp_no
      alertRow.push(alertTemp)
      this.frmData.alertRow = alertRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.exception()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    // 적용제외 - 승인
    async exception() {
      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no // 규제 검토 번호
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd // 검토 요청 구분 코드
      temp.mtrl_no = this.frmData.mtrl_no // 자재 번호
      temp.mtrl_nm = this.frmData.mtrl_nm // 자재명
      temp.imk_item_no = this.frmData.imk_item_no // imk 자재 코드
      temp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd // 규제 검토 분야 코드
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no // 규제 검토 요청 사원 번호
      temp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn // 규제 검토 분야 결과
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-exception', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000652')) // 검토제외가 승인되었습니다.
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
    async chkExcReject() {
      gridView.commit()
      gridView2.commit()

      /*
      if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData) &&
        this.rsltData.rglt_chk_emp_nm === this.rsltTempData.rglt_chk_emp_nm &&
        this.rsltData.rglt_chk_dt === this.rsltTempData.rglt_chk_dt &&
        this.rsltData.rglt_chk_mtrl_use_nm === this.rsltTempData.rglt_chk_mtrl_use_nm &&
        this.rsltData.rglt_chk_rslt_cntn === this.rsltTempData.rglt_chk_rslt_cntn &&
        this.rsltData.rglt_chk_rejc_cntn === this.rsltTempData.rglt_chk_rejc_cntn &&
        this.rsltData.rglt_chk_mtrl_use_yn === this.rsltTempData.rglt_chk_mtrl_use_yn &&
        JSON.stringify(this.alertData) === JSON.stringify(this.alertTempData) &&
        JSON.stringify(this.picChgData) === JSON.stringify(this.picTData)
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }
      */

      /*
      if (this.isEmpty(this.frmData.dngr_kind_cd) || (this.dngrChk === false && this.frmData.dngr_kind_cd === '9')) {
        this.$toast.show(this.$t('MS00000233'), { className: 'toast_error' }) // 위험물여부를 선택해 주세요.
        this.$refs.dngr_kind_cd.focus()
        return
      }
      if (this.isEmpty(this.frmData.dngr_dtl_kind_cd) || (this.dngrChk === false && this.frmData.dngr_dtl_kind_cd === '999')) {
        this.$toast.show(this.$t('MS00000234'), { className: 'toast_error' }) // 위험물여부 상세를 선택해 주세요.
        this.$refs.dngr_dtl_kind_cd.focus()
        return
      }
      */
      if (this.isEmpty(this.rsltData.rglt_chk_rejc_cntn)) {
        this.$toast.show(this.$t('MS00000195'), { className: 'toast_error' }) // 검토반려 내용을 입력해주세요.
        this.$refs.rgltchkrejccntn.focus()
        return
      }

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      /*
      // Validation 체크
      if (!this.validate()) {
        // 첨부파일 불러오기
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
        return
      }
      */

      const res = await this.$confirm(this.$t('MS00000653')) // 검토제외 반려하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0) {
        const rgltRow = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider2.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider2.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider2.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider2.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider2.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider2.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider2.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider2.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider2.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider2.getValue(i, 'rglt_abbr')
          if (dataProvider2.getValue(i, 'tgt_yn') === true) {
            temp.tgt_yn = 'Y'
          } else {
            temp.tgt_yn = 'N'
          }
          temp.org_tgt_yn = dataProvider2.getValue(i, 'org_tgt_yn')
          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_note = dataProvider2.getValue(i, 'rglt_note')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }

      let date = new Date()
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      date = this.getTzDateObj(this.user.time_zone)

      const rsltRow = []
      const rsltTemp = {}
      rsltTemp.rglt_chk_no = this.frmData.rglt_chk_no
      rsltTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      rsltTemp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      rsltTemp.rglt_chk_rslt_cntn = this.rsltData.rglt_chk_rslt_cntn
      rsltTemp.rglt_chk_dt_timezone = this.get_date_str_gubun(date, '')
      rsltTemp.username = this.user.emp_no
      rsltTemp.rglt_chk_mtrl_use_yn = this.rsltData.rglt_chk_mtrl_use_yn
      rsltTemp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      rsltRow.push(rsltTemp)
      this.frmData.rsltRow = rsltRow

      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.macl_cd = this.frmData.macl_cd
      temp.username = this.user.emp_no
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.pchs_unit_cd = this.frmData.pchs_unit_cd
      temp.pchs_unit_nm = this.frmData.pchs_unit_nm
      temp.pchs_unit_weght = this.frmData.pchs_unit_weght
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      temp.msds_ver = this.frmData.msds_ver
      temp.dngr_kind_cd = this.frmData.dngr_kind_cd
      temp.dngr_dtl_kind_cd = this.frmData.dngr_dtl_kind_cd
      if (!this.isEmpty(this.frmData.enact_da)) {
        temp.enact_da = this.replaceAll(this.frmData.enact_da, '')
      }
      if (!this.isEmpty(this.frmData.rev_da)) {
        temp.rev_da = this.replaceAll(this.frmData.rev_da, '')
      }
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      this.frmData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.frmData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (this.t_kyin_yn === true) {
        this.alertData.vndr_kyin_yn = 'Y'
      } else {
        this.alertData.vndr_kyin_yn = 'N'
      }

      const alertRow = []
      const alertTemp = {}
      alertTemp.rglt_chk_no = this.frmData.rglt_chk_no
      alertTemp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      alertTemp.mtrl_no = this.frmData.mtrl_no
      alertTemp.vndr_kyin_yn = this.alertData.vndr_kyin_yn
      alertTemp.sgw_cd = this.alertData.sgw_cd
      alertTemp.hars_cntn = this.alertData.hars_cntn
      alertTemp.prv_cntn = this.alertData.prv_cntn
      alertTemp.act_cntn = this.alertData.act_cntn
      alertTemp.str_cntn = this.alertData.str_cntn
      alertTemp.scr_cntn = this.alertData.scr_cntn
      alertTemp.vndr_no = this.alertData.vndr_no
      alertTemp.vndr_nm = this.alertData.vndr_nm
      alertTemp.nat_cd = this.alertData.nat_cd
      alertTemp.vndr_addr = this.alertData.vndr_addr
      alertTemp.vndr_phon_no = this.alertData.vndr_phon_no
      alertTemp.vndr_rprs_nm = this.alertData.vndr_rprs_nm
      alertTemp.vndr_coreg_no = this.alertData.vndr_coreg_no
      alertTemp.vndr_type_cd = this.alertData.vndr_type_cd
      alertTemp.username = this.user.emp_no
      alertRow.push(alertTemp)
      this.frmData.alertRow = alertRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.ejt()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    // 적용제외 - 반려
    async ejt() {
      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.mtrl_no = this.frmData.mtrl_no
      temp.rglt_chk_doma_cd = this.rsltData.rglt_chk_doma_cd
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no
      temp.rglt_chk_rejc_cntn = this.rsltData.rglt_chk_rejc_cntn
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-exc-reject', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000654')) // 검토제외가 반려되었습니다.
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
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    validate() {
      let count = 0
      if (this.picTempData.ck1) {
        count++
      }
      if (this.picTempData.ck2) {
        count++
      }
      if (this.picTempData.ck3) {
        count++
      }
      if (this.picTempData.ck4) {
        count++
      }
      if (this.picTempData.ck5) {
        count++
      }
      if (this.picTempData.ck6) {
        count++
      }
      if (this.picTempData.ck7) {
        count++
      }
      if (this.picTempData.ck8) {
        count++
      }
      if (this.picTempData.ck9) {
        count++
      }
      gridView.commit()
      gridView2.commit()
      if (this.isEmpty(this.frmData.phase_cd)) {
        this.$toast.show(this.$t('MS00000172'), { className: 'toast_error' }) // 자재 성상을 입력해주세요.
        this.$refs.phase_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.mtrl_puse_cd)) {
        this.$toast.show(this.$t('MS00000173'), { className: 'toast_error' }) // 자재 용도를 입력해주세요.
        this.$refs.mtrl_puse_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.mtrl_puse_cntn) && this.frmData.mtrl_puse_cd === 'U55') {
        this.$toast.show(this.$t('MS00000151'), { className: 'toast_error' }) // 자재 기타 용도를 입력해주세요.
        this.$refs.mtrl_puse_cntn.focus()
        return false
      }
      if (this.isEmpty(this.frmData.pchs_unit_weght)) {
        this.$toast.show(this.$t('MS00000050'), { className: 'toast_error' }) // 단위중량을 입력해주세요.
        this.$refs.pchs_unit_weght.focus()
        return false
      }
      if (isNaN(this.frmData.pchs_unit_weght)) {
        this.$toast.show(this.$t('MS00000236'), { className: 'toast_error' }) // 단위중량은 숫자만 입력해주세요.
        this.$refs.pchs_unit_weght.focus()
        return false
      }
      if (this.frmData.pchs_unit_weght < 0.001 || this.frmData.pchs_unit_weght > 999999999999999) {
        this.$toast.show(this.$t('MS00000237'), { className: 'toast_error' }) // 단위중량을 범위에 맞게 입력해 주세요.(0.001 이상, 999999999999999 미만)
        this.$refs.pchs_unit_weght.focus()
        return false
      }
      if (this.isEmpty(this.frmData.pchs_unit_cd)) {
        this.$toast.show(this.$t('MS00000232'), { className: 'toast_error' }) // 단위중량을 선택해주세요.
        this.$refs.pchs_unit_cd.focus()
        return false
      }
      if (this.frmData.pchs_unit_cd === '6' && this.isEmpty(this.frmData.pchs_unit_nm)) {
        this.$toast.show(this.$t('MS00000235'), { className: 'toast_error' }) // 기타단위를 입력해주세요.
        this.$refs.pchs_unit_nm.focus()
        return false
      }
      if (this.frmData.pchs_unit_cd === '6' && !this.isEmpty(this.frmData.pchs_unit_nm) && this.frmData.pchs_unit_nm.length > 10) {
        this.$toast.show(this.$t('MS00000238'), { className: 'toast_error' }) // 기타단위는 15자 이하로 입력해주세요.
        this.$refs.pchs_unit_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.enact_da)) {
        this.$toast.show(this.$t('MS00000204'), { className: 'toast_error' }) // 제정 일자를 입력해주세요.
        this.$refs.enact_da.focus()
        return false
      }
      if (this.isEmpty(this.frmData.rev_da)) {
        this.$toast.show(this.$t('MS00000205'), { className: 'toast_error' }) // 개정 일자를 입력해주세요.
        this.$refs.rev_da.focus()
        return false
      }
      if (this.isEmpty(this.frmData.msds_ver)) {
        this.$toast.show(this.$t('MS00000226'), { className: 'toast_error' }) // MSDS 버전을 입력해주세요.
        this.$refs.msds_ver.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.msds_ver) && this.frmData.msds_ver.length > 20) {
        this.$toast.show(this.$t('MS00000202'), { className: 'toast_error' }) // MSDS 버전은 20자 이하로 입력해주세요.
        this.$refs.msds_ver.focus()
        return false
      }
      if (this.isEmpty(this.frmData.ko_atfile_no)) {
        this.$toast.show(this.$t('MS00000217'), { className: 'toast_error' }) // MSDS(국문) 파일을 선택해주세요.
        this.$refs.uploadOne.focus()
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'sbst_type_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'sbst_type_cd' })
          this.$toast.show(this.$t('MS00000140'), { className: 'toast_error' }) // 물질구분을 입력해주세요.
          return false
        }
        if (this.isEmptyNaN(dataProvider.getValue(i, 'sbst_rati'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'sbst_rati' })
          this.$toast.show(this.$t('MS00000174'), { className: 'toast_error' }) // 함량을 입력해주세요.
          return false
        }
        if (isNaN(dataProvider.getValue(i, 'sbst_rati'))) {
          this.$toast.show(this.$t('MS00000163'), { className: 'toast_error' }) // 함량은 숫자만 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'sbst_rati') * 1 < 0.0000001 || dataProvider.getValue(i, 'sbst_rati') * 1 > 100) {
          this.$toast.show(this.$t('MS00000228'), { className: 'toast_error' }) // 함량을 범위에 맞게 입력해 주세요.(0.0000001 이상, 100 미만)
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'sbst_note')) && dataProvider.getValue(i, 'sbst_note').length > 200) {
          gridView.setCurrent({ dataRow: i, fieldName: 'sbst_note' })
          this.$toast.show(this.$t('MS00000176'), { className: 'toast_error' }) // 비고는 200자 이하로 입력해주세요.
          return false
        }
      }
      if (count > 4) {
        this.$toast.show(this.$t('MS00000214'), { className: 'toast_error' }) // 출력 문자는 5개 이상 선택할 수 없습니다.
        return false
      }
      if (this.isEmpty(this.alertData.hars_cntn)) {
        this.$toast.show(this.$t('MS00000179'), { className: 'toast_error' }) // 유해위험문구를 입력해주세요.
        this.$refs.harsCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.hars_cntn) && this.alertData.hars_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000157'), { className: 'toast_error' }) // 유해위험문구는 500자 이하로 작성해주세요.
        this.$refs.harsCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.prv_cntn) && this.alertData.prv_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000158'), { className: 'toast_error' }) // 예방 문구는 500자 이하로 작성해주세요.
        this.$refs.prvCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.act_cntn) && this.alertData.act_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000159'), { className: 'toast_error' }) // 대응 문구는 500자 이하로 작성해주세요.
        this.$refs.actCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.str_cntn) && this.alertData.str_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000160'), { className: 'toast_error' }) // 저장 문구는 500자 이하로 작성해주세요.
        this.$refs.strCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.scr_cntn) && this.alertData.scr_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000161'), { className: 'toast_error' }) // 폐기 문구는 500자 이하로 작성해주세요.
        this.$refs.scrCntn.focus()
        return false
      }
      if (this.t_kyin_yn) {
        if (this.isEmpty(this.alertData.vndr_nm)) {
          this.$toast.show(this.$t('MS00000105'), { className: 'toast_error' }) // 공급업체명을 입력해주세요.
          this.$refs.vndrNm.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_nm) && this.alertData.vndr_nm.length > 100) {
          this.$toast.show(this.$t('MS00000206'), { className: 'toast_error' }) // 공급업체명은 100자 이하로 입력해주세요.
          this.$refs.vndrNm.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_addr) && this.alertData.vndr_addr.length > 200) {
          this.$toast.show(this.$t('MS00000207'), { className: 'toast_error' }) // 공급업체 주소는 200자 이하로 입력해주세요.
          this.$refs.vndrAddr.focus()
          return false
        }
        if (this.isEmpty(this.alertData.nat_cd)) {
          this.$toast.show(this.$t('MS00000107'), { className: 'toast_error' }) // 공급업체 국가를 입력해주세요.
          this.$refs.ntcd.focus()
          return false
        }
        if (this.isEmpty(this.alertData.vndr_phon_no)) {
          this.$toast.show(this.$t('MS00000106'), { className: 'toast_error' }) // 공급업체 연락처를 입력해주세요.
          this.$refs.vndrPhon.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_phon_no) && this.alertData.vndr_phon_no.length > 100) {
          this.$toast.show(this.$t('MS00000208'), { className: 'toast_error' }) // 공급업체 연락처는 100자 이하로 입력해주세요.
          this.$refs.vndrPhon.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_rprs_nm) && this.alertData.vndr_rprs_nm.length > 100) {
          this.$toast.show(this.$t('MS00000209'), { className: 'toast_error' }) // 공급업체 대표자명은 100자 이하로 입력해주세요.
          this.$refs.vndrRprsNm.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_coreg_no) && this.alertData.vndr_coreg_no.length > 100) {
          this.$toast.show(this.$t('MS00000210'), { className: 'toast_error' }) // 사업자 등록번호는 100자 이하로 입력해주세요.
          this.$refs.vndrCoregNo.focus()
          return false
        }
      } else {
        if (this.isEmpty(this.alertData.vndr_no)) {
          this.$toast.show(this.$t('MS00000178'), { className: 'toast_error' }) // 공급업체를 입력해주세요.
          this.$refs.vndr.focus()
          return false
        }
        if (this.isEmpty(this.alertData.vndr_nm)) {
          this.$toast.show(this.$t('MS00000178'), { className: 'toast_error' }) // 공급업체를 입력해주세요.
          this.$refs.vndr.focus()
          return false
        }
      }
      return true
    },
    saveValidate() {
      let count = 0
      if (this.picTempData.ck1) {
        count++
      }
      if (this.picTempData.ck2) {
        count++
      }
      if (this.picTempData.ck3) {
        count++
      }
      if (this.picTempData.ck4) {
        count++
      }
      if (this.picTempData.ck5) {
        count++
      }
      if (this.picTempData.ck6) {
        count++
      }
      if (this.picTempData.ck7) {
        count++
      }
      if (this.picTempData.ck8) {
        count++
      }
      if (this.picTempData.ck9) {
        count++
      }
      gridView.commit()
      gridView2.commit()
      if (!this.isEmpty(this.frmData.pchs_unit_weght) && (this.frmData.pchs_unit_weght < 0.001 || this.frmData.pchs_unit_weght > 999999999999999)) {
        this.$toast.show(this.$t('MS00000237'), { className: 'toast_error' }) // 단위중량을 범위에 맞게 입력해 주세요.(0.001 이상, 999999999999999 미만)
        this.$refs.pchs_unit_weght.focus()
        return false
      }
      if (this.frmData.pchs_unit_cd === '6' && !this.isEmpty(this.frmData.pchs_unit_nm) && this.frmData.pchs_unit_nm.length > 10) {
        this.$toast.show(this.$t('MS00000238'), { className: 'toast_error' }) // 기타단위는 15자 이하로 입력해주세요.
        this.$refs.pchs_unit_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.msds_ver) && this.frmData.msds_ver.length > 20) {
        this.$toast.show(this.$t('MS00000202'), { className: 'toast_error' }) // MSDS 버전은 20자 이하로 입력해주세요.
        this.$refs.msds_ver.focus()
        return false
      }
      if (gridView.getItemCount() <= 0) {
        this.$toast.show(this.$t('MS00000052'), { className: 'toast_error' }) // 구성 물질 정보를 최소 한개 추가 해 주세요.
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (!this.isEmptyNaN(dataProvider.getValue(i, 'sbst_rati')) && isNaN(dataProvider.getValue(i, 'sbst_rati'))) {
          this.$toast.show(this.$t('MS00000163'), { className: 'toast_error' }) // 함량은 숫자만 입력해주세요.
          return false
        }
        if (!this.isEmptyNaN(dataProvider.getValue(i, 'sbst_rati')) && (dataProvider.getValue(i, 'sbst_rati') * 1 < 0.0000001 || dataProvider.getValue(i, 'sbst_rati') * 1 > 100)) {
          this.$toast.show(this.$t('MS00000228'), { className: 'toast_error' }) // 함량을 범위에 맞게 입력해 주세요.(0.0000001 이상, 100 미만)
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'sbst_note')) && dataProvider.getValue(i, 'sbst_note').length > 200) {
          gridView.setCurrent({ dataRow: i, fieldName: 'sbst_note' })
          this.$toast.show(this.$t('MS00000176'), { className: 'toast_error' }) // 비고는 200자 이하로 입력해주세요.
          return false
        }
      }
      if (count > 4) {
        this.$toast.show(this.$t('MS00000214'), { className: 'toast_error' }) // 출력 문자는 5개 이상 선택할 수 없습니다.
        return false
      }
      if (!this.isEmpty(this.alertData.hars_cntn) && this.alertData.hars_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000157'), { className: 'toast_error' }) // 유해위험문구는 500자 이하로 작성해주세요.
        this.$refs.harsCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.prv_cntn) && this.alertData.prv_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000158'), { className: 'toast_error' }) // 예방 문구는 500자 이하로 작성해주세요.
        this.$refs.prvCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.act_cntn) && this.alertData.act_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000159'), { className: 'toast_error' }) // 대응 문구는 500자 이하로 작성해주세요.
        this.$refs.actCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.str_cntn) && this.alertData.str_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000160'), { className: 'toast_error' }) // 저장 문구는 500자 이하로 작성해주세요.
        this.$refs.strCntn.focus()
        return false
      }
      if (!this.isEmpty(this.alertData.scr_cntn) && this.alertData.scr_cntn.length > 500) {
        this.$toast.show(this.$t('MS00000161'), { className: 'toast_error' }) // 폐기 문구는 500자 이하로 작성해주세요.
        this.$refs.scrCntn.focus()
        return false
      }
      if (this.t_kyin_yn) {
        if (!this.isEmpty(this.alertData.vndr_nm) && this.alertData.vndr_nm.length > 100) {
          this.$toast.show(this.$t('MS00000206'), { className: 'toast_error' }) // 공급업체명은 100자 이하로 입력해주세요.
          this.$refs.vndrNm.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_addr) && this.alertData.vndr_addr.length > 200) {
          this.$toast.show(this.$t('MS00000207'), { className: 'toast_error' }) // 공급업체 주소는 200자 이하로 입력해주세요.
          this.$refs.vndrAddr.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_phon_no) && this.alertData.vndr_phon_no.length > 100) {
          this.$toast.show(this.$t('MS00000208'), { className: 'toast_error' }) // 공급업체 연락처는 100자 이하로 입력해주세요.
          this.$refs.vndrPhon.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_rprs_nm) && this.alertData.vndr_rprs_nm.length > 100) {
          this.$toast.show(this.$t('MS00000209'), { className: 'toast_error' }) // 공급업체 대표자명은 100자 이하로 입력해주세요.
          this.$refs.vndrRprsNm.focus()
          return false
        }
        if (!this.isEmpty(this.alertData.vndr_coreg_no) && this.alertData.vndr_coreg_no.length > 100) {
          this.$toast.show(this.$t('MS00000210'), { className: 'toast_error' }) // 사업자 등록번호는 100자 이하로 입력해주세요.
          this.$refs.vndrCoregNo.focus()
          return false
        }
      }
      return true
    },
    codChg(data) {
      if (data === 'U55') {
        this.mtrlModify = true
        this.puse_yn = 'essn'
      } else {
        this.mtrlModify = false
        this.frmData.mtrl_puse_cntn = null
        this.puse_yn = ''
      }
    },
    codChg2(data) {
      if (data === '9') {
        // 상위 코드 초기화 필터링
        this.codes.F_CH00000027 = this.codes.CH00000027.filter(function (n) {
          // 해당없음
          return n.buf_1st_cntn === '0'
        })
      } else {
        this.codes.F_CH00000027 = this.codes.CH00000027.filter(function (n) {
          // 해당없음 제외
          return n.buf_1st_cntn === '1'
        })
      }
      this.codes.F_CH00000027.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })

      // 상위 코드 조회 후 필터링
      this.codes.F_CH00000028 = this.codes.CH00000028.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000028.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })

      // 위험물여부가 해당없는경우 상세종류 설정
      if (this.frmData.dngr_kind_cd === '9') {
        this.frmData.dngr_dtl_kind_cd = '999'
      }
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
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    chkboxChngPrint(val) {
      if (val === '1') {
        if (this.picData.ck1 === true) {
          this.picTempData.ck1 = true
        } else {
          this.picTempData.ck1 = false
        }
      }
      if (val === '2') {
        if (this.picData.ck2 === true) {
          this.picTempData.ck2 = true
        } else {
          this.picTempData.ck2 = false
        }
      }
      if (val === '3') {
        if (this.picData.ck3 === true) {
          this.picTempData.ck3 = true
        } else {
          this.picTempData.ck3 = false
        }
      }
      if (val === '4') {
        if (this.picData.ck4 === true) {
          this.picTempData.ck4 = true
        } else {
          this.picTempData.ck4 = false
        }
      }
      if (val === '5') {
        if (this.picData.ck5 === true) {
          this.picTempData.ck5 = true
        } else {
          this.picTempData.ck5 = false
        }
      }
      if (val === '6') {
        if (this.picData.ck6 === true) {
          this.picTempData.ck6 = true
        } else {
          this.picTempData.ck6 = false
        }
      }
      if (val === '7') {
        if (this.picData.ck7 === true) {
          this.picTempData.ck7 = true
        } else {
          this.picTempData.ck7 = false
        }
      }
      if (val === '8') {
        if (this.picData.ck8 === true) {
          this.picTempData.ck8 = true
        } else {
          this.picTempData.ck8 = false
        }
      }
      if (val === '9') {
        if (this.picData.ck9 === true) {
          this.picTempData.ck9 = true
        } else {
          this.picTempData.ck9 = false
        }
      }
    },
    chkboxChngTgt(val) {
      if (val === '1') {
        if (this.picTempData.ck1 === true) {
          this.picData.ck1 = true
        }
      }
      if (val === '2') {
        if (this.picTempData.ck2 === true) {
          this.picData.ck2 = true
        }
      }
      if (val === '3') {
        if (this.picTempData.ck3 === true) {
          this.picData.ck3 = true
        }
      }
      if (val === '4') {
        if (this.picTempData.ck4 === true) {
          this.picData.ck4 = true
        }
      }
      if (val === '5') {
        if (this.picTempData.ck5 === true) {
          this.picData.ck5 = true
        }
      }
      if (val === '6') {
        if (this.picTempData.ck6 === true) {
          this.picData.ck6 = true
        }
      }
      if (val === '7') {
        if (this.picTempData.ck7 === true) {
          this.picData.ck7 = true
        }
      }
      if (val === '8') {
        if (this.picTempData.ck8 === true) {
          this.picData.ck8 = true
        }
      }
      if (val === '9') {
        if (this.picTempData.ck9 === true) {
          this.picData.ck9 = true
        }
      }
    },
    async goList() {
      gridView.commit()
      gridView2.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.ko_atfile_no = data
        }
      })
      await this.$refs.upload2.uploadStart(true).then((data2) => {
        console.log('File Id : ', data2) // eslint-disable-line no-console
        if (!this.isEmptyFile(data2)) {
          this.frmData.en_atfile_no = data2
        } else {
          this.frmData.en_atfile_no = null
          this.frmTempData.en_atfile_no = null
        }
      })

      this.picChgData.pic = []
      if (this.picData.ck1 === true) {
        if (this.picTempData.ck1 === true) {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '1', print_yn: 'N' })
        }
      }
      if (this.picData.ck2 === true) {
        if (this.picTempData.ck2 === true) {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '2', print_yn: 'N' })
        }
      }
      if (this.picData.ck3 === true) {
        if (this.picTempData.ck3 === true) {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '3', print_yn: 'N' })
        }
      }
      if (this.picData.ck4 === true) {
        if (this.picTempData.ck4 === true) {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '4', print_yn: 'N' })
        }
      }
      if (this.picData.ck5 === true) {
        if (this.picTempData.ck5 === true) {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '5', print_yn: 'N' })
        }
      }
      if (this.picData.ck6 === true) {
        if (this.picTempData.ck6 === true) {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '6', print_yn: 'N' })
        }
      }
      if (this.picData.ck7 === true) {
        if (this.picTempData.ck7 === true) {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '7', print_yn: 'N' })
        }
      }
      if (this.picData.ck8 === true) {
        if (this.picTempData.ck8 === true) {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '8', print_yn: 'N' })
        }
      }
      if (this.picData.ck9 === true) {
        if (this.picTempData.ck9 === true) {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'Y' })
        } else {
          this.picChgData.pic.push({ picg_cd: '9', print_yn: 'N' })
        }
      }

      if (
        dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() ||
        dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData) ||
        JSON.stringify(this.rsltData) !== JSON.stringify(this.rsltTempData) ||
        JSON.stringify(this.alertData) !== JSON.stringify(this.alertTempData) ||
        JSON.stringify(this.picChgData) !== JSON.stringify(this.picTData)
      ) {
        const res = await this.$confirm(this.$t('MS00000131')) // 수정된 내용이 있습니다. 화면을 이동할 경우 작업내용을 저장할 수 없습니다.
        if (res) {
          if (this.isEmpty(this.tmpSchData.sDate)) {
            // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
            sDate = this.getTzDateObj(this.user.time_zone)
            sDate.setDate(sDate.getDate() - 7)
            this.tmpSchData.sDate = this.get_date_str_gubun(sDate, '-')
          }
          if (this.isEmpty(this.tmpSchData.eDate)) {
            // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
            eDate = this.getTzDateObj(this.user.time_zone)
            this.tmpSchData.eDate = this.get_date_str_gubun(eDate, '-')
          }

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
        }
      } else {
        if (this.isEmpty(this.tmpSchData.sDate)) {
          // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
          sDate = this.getTzDateObj(this.user.time_zone)
          sDate.setDate(sDate.getDate() - 7)
          this.tmpSchData.sDate = this.get_date_str_gubun(sDate, '-')
        }
        if (this.isEmpty(this.tmpSchData.eDate)) {
          // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
          eDate = this.getTzDateObj(this.user.time_zone)
          this.tmpSchData.eDate = this.get_date_str_gubun(eDate, '-')
        }

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
      }
    },
  },
}
</script>
<style></style>
