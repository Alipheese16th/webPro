<template>
  <div>
    <div class="location">
      <!-- MSDS 제출상세 -->
      <span>Home > MSDS > {{ $t('LB00000625') }}</span>
    </div>
    <!-- MSDS 상세 -->
    <div class="work_title">
      <!-- eslint-disable -->
      <h2>{{ $t('LB00000625') }} <span v-html="titleHtml" class="work_title2"></span></h2>
    </div>
    <div class="container_detail">
      <!-- MSDS 상세 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000265') }}</h3>
        <v-spacer></v-spacer>
        <div>
          <!-- 경고표지 조회/출력 -->
          <v-btn style="bottom: 3px" color="" outlined height="25" @click="gwsgn_pop()">{{ $t('LB00000174') }}</v-btn>
        </div>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
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
            <v-row>
              <v-col cols="1">
                <!-- 사업장 -->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.msds.wkpl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- MSDS버전 -->
                <div class="label_tit">{{ $t('LB00000266') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.msds_ver }}</div>
              </v-col>
              <v-col cols="1">
                <!-- MSDS(국문) -->
                <div class="label_tit">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">
                  <!-- <a class="link" @click="downKoFile">{{ frmData.msds.ko_atfile_nm }}</a> -->
                  <file-upload ref="upload1" input-id="file" :multiple="false" />
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 최근개정일 -->
                <div class="label_tit">{{ $t('LB00000034') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.msds.rev_da }}</div>
              </v-col>
              <v-col cols="1">
                <!-- MSDS(영문) -->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">
                  <!-- <a class="link" @click="downEnFile">{{ frmData.msds.en_atfile_nm }}</a> -->
                  <file-upload ref="upload2" input-id="file2" :multiple="false" />
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- MSDS 제출검토결과 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000626') }}</h3>
        <v-spacer></v-spacer>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- MSDS 제출상태 -->
                <div class="label_tit essn">{{ $t('LB00000629') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="submit_stt_cd"
                  v-model="frmData.msds.submit_stt_cd"
                  label="SEL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000031"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="setField($event)"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- MSDS 번호 -->
                <div :class="[basic, submit_msds_no]">{{ $t('LB00000627') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="submit_msds_no"
                  v-model="frmData.msds.submit_msds_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="99"
                  :disabled="!submit_msds_noModify"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 제출완료 결과 -->
                <div :class="[basic, submit_complete_cntn]">{{ $t('LB00000630') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="submit_complete_cntn" v-model="frmData.msds.submit_complete_cntn" :disabled="!submit_complete_cntnModify" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 제출제외사유 -->
                <div :class="[basic, submit_reject_cntn]">{{ $t('LB00000631') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="submit_reject_cntn" v-model="frmData.msds.submit_reject_cntn" :disabled="!submit_reject_cntnModify" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 물질 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000267') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 건 -->
          {{ $t('LB00000039') }}<em> {{ mtrlSize }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제검토 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000268') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 건 -->
          {{ $t('LB00000039') }}<em> {{ rgltSize }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid2" ref="realgrid2" style="width: 100%; height: 280px"></div>
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
                <!-- 위험물여부 -->
                <div class="label_tit">{{ $t('LB00000538') }}</div>
              </v-col>
              <v-col cols="11" style="display: flex">
                <div class="label_con">{{ frmData.msds.dngr_kind_nm }} / {{ frmData.msds.dngr_dtl_kind_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title" style="display: flex">
        <!-- 경고표지 -->
        <h3>{{ $t('LB00000038') }}</h3>
        <v-spacer></v-spacer>
        <div>
          <!-- 경고표지 조회/출력 -->
          <v-btn style="bottom: 3px" color="" outlined height="25" @click="gwsgn_pop()">{{ $t('LB00000174') }}</v-btn>
        </div>
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
                <!-- 입력하세요 -->
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
                        <img src="~static/images/P01.png" alt="" :class="{ img_unchk: !ck1 }" />
                        <v-checkbox v-model="ck1" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P02.png" alt="" :class="{ img_unchk: !ck2 }" />
                        <v-checkbox v-model="ck2" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P03.png" alt="" :class="{ img_unchk: !ck3 }" />
                        <v-checkbox v-model="ck3" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P04.png" alt="" :class="{ img_unchk: !ck4 }" />
                        <v-checkbox v-model="ck4" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P05.png" alt="" :class="{ img_unchk: !ck5 }" />
                        <v-checkbox v-model="ck5" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                    </tr>
                    <tr>
                      <td style="text-align: center">
                        <img src="~static/images/P06.png" alt="" :class="{ img_unchk: !ck6 }" />
                        <v-checkbox v-model="ck6" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P07.png" alt="" :class="{ img_unchk: !ck7 }" />
                        <v-checkbox v-model="ck7" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P08.png" alt="" :class="{ img_unchk: !ck8 }" />
                        <v-checkbox v-model="ck8" height="30" :hide-details="true" :disabled="true"></v-checkbox>
                      </td>
                      <td>
                        <img src="~static/images/P09.png" alt="" :class="{ img_unchk: !ck9 }" />
                        <v-checkbox v-model="ck9" height="30" :hide-details="true" :disabled="true"></v-checkbox>
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
              <v-col cols="1">
                <!-- 공급업체 연락처 -->
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
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 개정이력 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000269') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 건 -->
          {{ $t('LB00000039') }}<em> {{ revSize }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 저장 -->
      <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
    </div>
    <gwsgnpopup ref="gwsgnpopup" @close="gwgnsubmit" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import gwsgnpopup from '../../common/gwsgnslct'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: 'MSDS-MSDS상세',
    key(route) {
      return `/msds/${route.params.catalog}`
    },
  },
  components: {
    gwsgnpopup,
    FileUpload,
  },
  data() {
    return {
      titleHtml: '',
      basic: 'label_tit',
      submit_msds_no: '',
      submit_complete_cntn: '',
      submit_reject_cntn: '',
      submit_msds_noModify: false,
      submit_complete_cntnModify: false,
      submit_reject_cntnModify: false,
      schData: {},
      frmData: {
        msds: {
          mtrl_no: '',
          mtrl_nm: '',
          wkpl_nm: '',
          msds_ver: '',
          rev_da: '',
          ko_atfile_no: '',
          en_atfile_no: '',
          ko_atfile_nm: '',
          en_atfile_nm: '',
          ko_atfile_key: '',
          en_atfile_key: '',
          submit_stt_cd: '',
          submit_complete_cntn: '',
          submit_reject_cntn: '',
          dept_cd: '',
          req_emp_no: '',
        },
        alert: {
          sgw_cd: '',
          sgw_nm: '',
          hars_cntn: '',
          prv_cntn: '',
          act_cntn: '',
          str_cntn: '',
          scr_cntn: '',
          vndr_nm: '',
          vndr_phon_no: '',
          vndr_addr: '',
          nat_nm: '',
          vndr_rprs_nm: '',
          vndr_coreg_no: '',
          vndr_email: '',
        },
      },
      codeid: ['CH00000031'],
      codes: {
        CH00000031: [{ cd: '', cd_nm: '' }],
      },
      selCH00000031: {
        cd: [],
        cd_nm: [],
      },
      ck1: false,
      ck2: false,
      ck3: false,
      ck4: false,
      ck5: false,
      ck6: false,
      ck7: false,
      ck8: false,
      ck9: false,
      mtrlNo: '',
      mtrlNm: '',
      row: '',
      mtrlSize: 0,
      rgltSize: 0,
      revSize: 0,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000265')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    // 파라미터 조회
    this.mtrlNo = this.$route.params.msdssubmitedt
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        submit_stt_cd: '',
      }
    }
    this.schData.mtrl_no = this.mtrlNo
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      res.CH00000031.forEach((i) => {
        this.selCH00000031.cd.push(i.cd)
        this.selCH00000031.cd_nm.push(i.cd_nm)
      })
    })
    const columns = [
      {
        header: this.$t('LB00000270'), // 물질구분
        name: 'sbst_type_cd',
        fieldName: 'sbst_type_cd',
        width: '100',
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '50',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_nm',
        fieldName: 'sbst_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000195'), // 함량
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '50',
        styleName: 'right',
      },
      // {
      //   header: this.$t('LB00000267'), // 물질정보
      //   name: '',
      //   fieldName: '',
      //   width: '50',
      //   styleName: 'btn_sch_pop',
      // },
    ]

    const columns2 = [
      {
        header: this.$t('LB00000272'), // 법
        name: 'law_cd',
        fieldName: 'law_cd',
        width: '100',
      },
      {
        header: this.$t('LB00000028'), // 규제
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000212'), // 대상여부
        name: 'chk',
        fieldName: 'chk',
        width: '100',
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: 'N',
          trueValues: 'Y',
          editable: false,
        },
      },
      {
        header: this.$t('LB00000324'), // 규제 대상 물질
        name: 'rglt_rsn',
        fieldName: 'rglt_rsn',
        width: '200',
      },
    ]

    const columns3 = [
      {
        header: this.$t('LB00000203'), // 개정일
        name: 'rev_da',
        fieldName: 'rev_da',
        width: '100',
      },
      {
        header: this.$t('LB00000266'), // MSDS버전
        name: 'msds_ver',
        fieldName: 'msds_ver',
        width: '100',
      },
      {
        header: this.$t('LB00000177'), // 국문
        name: 'msds_ko_ver',
        fieldName: 'msds_ko_ver',
        width: '100',
        styleName: 'btn_download',
      },
      {
        header: this.$t('LB00000176'), // 영문
        name: 'msds_en_ver',
        fieldName: 'msds_en_ver',
        width: '100',
        styleName: 'btn_download',
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
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 0
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
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

    gridView2.setColumnProperty('rglt_chk_doma_cd', 'equalBlank', true)
    gridView2.setColumnProperty('law_cd', 'equalBlank', true)

    dataProvider3 = new LocalDataProvider(false)
    dataProvider3.setFields(fields3)
    gridView3 = new GridView('realgrid3')
    gridView3.setDataSource(dataProvider3)
    gridView3.setColumns(columns3)
    gridView3.setFooters({ visible: false })

    gridView3.setStateBar({ visible: false })
    gridView3.setCheckBar({ visible: false })
    gridView3.editOptions.editable = false
    gridView3.displayOptions.selectionStyle = 'block'

    gridView3.header.height = 39
    gridView3.displayOptions.rowHeight = 40
    gridView3.footer.height = 40
    gridView3.displayOptions.fitStyle = 'fill'

    gridView3.setColumnProperty('id', 'visible', false)
    gridView3.onCellClicked = function (grid, index, clickData) {
      gridView3.commit()
      if (index.itemIndex > -1) {
        if (index.column === 'msds_ko_ver') {
          // 다운로드 처리
          const koDownKey = gridView3.getValue(index.itemIndex, 'rev_af_ko_atfile_key')
          const koDownNm = gridView3.getValue(index.itemIndex, 'rev_af_ko_atfile_nm')
          this.download(koDownKey, koDownNm)
        } else if (index.column === 'msds_en_ver') {
          // 다운로드 처리
          const enDownKey = gridView3.getValue(index.itemIndex, 'rev_af_en_atfile_key')
          const enDownNm = gridView3.getValue(index.itemIndex, 'rev_af_en_atfile_nm')
          this.download(enDownKey, enDownNm)
        }
      }
    }.bind(this)

    this.select()
  },
  methods: {
    setField(e) {
      if (e === 'F') {
        this.submit_msds_no = 'essn'
        this.submit_complete_cntn = 'essn'
        this.submit_reject_cntn = ''

        this.submit_msds_noModify = true
        this.submit_complete_cntnModify = true
        this.submit_reject_cntnModify = false

        this.frmData.msds.submit_msds_no = ''
        this.frmData.msds.submit_complete_cntn = ''
        this.frmData.msds.submit_reject_cntn = ''
      } else {
        this.submit_msds_no = ''
        this.submit_complete_cntn = ''
        this.submit_reject_cntn = 'essn'

        this.submit_msds_noModify = true
        this.submit_complete_cntnModify = true
        this.submit_reject_cntnModify = true

        this.frmData.msds.submit_msds_no = ''
        this.frmData.msds.submit_complete_cntn = ''
        this.frmData.msds.submit_reject_cntn = ''
      }
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/ch/msds/msds-dtl/${this.mtrlNo}`)

      this.frmData.msds = data.msds[0]
      if (data.alert === null || data.alert.length === 0 || data.alert === 'undefined') {
        this.frmData.alert = { sgw_cd: '' }
      } else {
        this.frmData.alert = data.alert[0]
        // carriage return 수정
        if (!this.isEmpty(this.frmData.alert.hars_cntn)) {
          this.frmData.alert.hars_cntn = this.frmData.alert.hars_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
        }
        if (!this.isEmpty(this.frmData.alert.prv_cntn)) {
          this.frmData.alert.prv_cntn = this.frmData.alert.prv_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
        }
        if (!this.isEmpty(this.frmData.alert.act_cntn)) {
          this.frmData.alert.act_cntn = this.frmData.alert.act_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
        }
        if (!this.isEmpty(this.frmData.alert.str_cntn)) {
          this.frmData.alert.str_cntn = this.frmData.alert.str_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
        }
        if (!this.isEmpty(this.frmData.alert.scr_cntn)) {
          this.frmData.alert.scr_cntn = this.frmData.alert.scr_cntn.replace(/(?:\r\n|\r|\n)/g, '<br />')
        }
      }
      if (data.pic === null || data.pic === []) {
        this.frmData.pic = []
      } else {
        this.frmData.pic = data.pic
        for (let i = 0; i < data.pic.length; i++) {
          if (data.pic[i].picg_cd === '1') {
            this.ck1 = true
          } else if (data.pic[i].picg_cd === '2') {
            this.ck2 = true
          } else if (data.pic[i].picg_cd === '3') {
            this.ck3 = true
          } else if (data.pic[i].picg_cd === '4') {
            this.ck4 = true
          } else if (data.pic[i].picg_cd === '5') {
            this.ck5 = true
          } else if (data.pic[i].picg_cd === '6') {
            this.ck6 = true
          } else if (data.pic[i].picg_cd === '7') {
            this.ck7 = true
          } else if (data.pic[i].picg_cd === '8') {
            this.ck8 = true
          } else if (data.pic[i].picg_cd === '9') {
            this.ck9 = true
          }
        }
        if (!this.isEmpty(this.frmData.msds.ko_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.ko_atfile_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
        if (!this.isEmpty(this.frmData.msds.en_atfile_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.msds.en_atfile_no}`).then((data) => {
            console.log('files2 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles2(data)
            }
          })
        }
        this.changeEditMode(false)
      }

      dataProvider.setRows(data.mtrl)
      gridView.refresh()
      gridView.setTopItem(0)

      this.mtrlSize = dataProvider.getRowCount()

      dataProvider2.setRows(data.rglt)
      gridView2.refresh()
      gridView2.setTopItem(0)

      this.rgltSize = dataProvider2.getRowCount()

      dataProvider3.setRows(data.rev)
      gridView3.refresh()
      gridView3.setTopItem(0)

      this.revSize = dataProvider3.getRowCount()

      // 행 높이 자동 조절
      const rowCount = gridView2.getItemCount()
      const headerHeight = gridView2.getHeader().height
      const rowHeight = gridView2.getDisplayOptions().rowHeight
      const footerHeight = gridView2.getFooter().height
      const totalHeight = headerHeight + footerHeight + rowHeight * rowCount + 2
      this.$refs.realgrid2.style.height = totalHeight - 40 + 'px'
      gridView2.resetSize()

      // 둘다 값이 와이이면...'※ MSDS 제출 대상 자재'
      // if (this.frmData.msds.imp_yn === 'Y' && this.frmData.msds.sbms_tgt_yn === 'Y') {
      //   this.titleHtml = this.$t('LB00000620')
      // }
    },
    validation() {
      if (this.frmData.msds.submit_stt_cd === 'F') {
        if (this.isEmpty(this.frmData.msds.submit_msds_no)) {
          this.$toast.show(this.$t('MS00000247'), { className: 'toast_error' }) // MSDS 번호를 입력해주세요.(KO)
          this.$refs.submit_msds_no.focus()
          return false
        } else if (this.isEmpty(this.frmData.msds.submit_complete_cntn)) {
          this.$toast.show(this.$t('MS00000248'), { className: 'toast_error' }) // 제출완료 결과를 입력해주세요.(KO)
          this.$refs.submit_complete_cntn.focus()
          return false
        } else {
          return true
        }
      } else if (this.frmData.msds.submit_stt_cd === 'R') {
        if (this.isEmpty(this.frmData.msds.submit_reject_cntn)) {
          this.$toast.show(this.$t('MS00000249'), { className: 'toast_error' }) // 제출제외 사유를 입력해주세요.(KO)
          this.$refs.submit_reject_cntn.focus()
          return false
        } else {
          return true
        }
      } else {
        this.$toast.show(this.$t('MS00000250'), { className: 'toast_error' }) // MSDS 제출상태를 선택해주세요.(KO)
        this.$refs.submit_stt_cd.focus()
        return false
      }
    },
    async save() {
      const user = this.$store.getters['auth/getUser']

      if (await this.validation()) {
        this.savenext = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
        if (await !this.savenext) {
        } else {
          this.frmData.msds.req_emp_no = user.emp_no
          await this.$axios.put('/api/v1/ch/msds/msds-submit', this.frmData).then((res) => {
            if (res.data !== 'Fail' && res.data !== 'READ') {
              this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
              // 가져온 키 값으로 재조회
              this.goList()
            } else if (res.data === 'READ') {
              this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
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
      this.download(this.frmData.msds.ko_atfile_key, this.frmData.msds.ko_atfile_nm)
    },
    downEnFile() {
      this.download(this.frmData.msds.en_atfile_key, this.frmData.msds.en_atfile_nm)
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
    goList() {
      this.$router.push({
        name: `msds-msdssubmitlist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
            macl_cd: this.tmpSchData.macl_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            submit_stt_cd: this.tmpSchData.submit_stt_cd,
          },
        },
      })
    },
    // 경고표지 조회/출력
    async gwsgn_pop() {
      const mtrlNoList = []
      mtrlNoList.push(this.frmData.msds.mtrl_no)
      await this.$refs.gwsgnpopup.open(mtrlNoList)
    },
    gwgnsubmit() {},
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'sbst_type_cd',
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
]

const fields2 = [
  {
    fieldName: 'rglt_chk_doma_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'law_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rglt_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'chk',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rglt_rsn',
    dataType: ValueType.TEXT,
  },
]

const fields3 = [
  {
    fieldName: 'rev_da',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'msds_ver',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'msds_ko_ver',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'msds_en_ver',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_ko_atfile_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_en_atfile_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_ko_atfile_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_en_atfile_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_ko_atfile_key',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_af_en_atfile_key',
    dataType: ValueType.TEXT,
  },
]
</script>
<style></style>
