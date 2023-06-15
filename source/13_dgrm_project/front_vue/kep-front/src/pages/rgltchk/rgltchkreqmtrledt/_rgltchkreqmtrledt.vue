<template>
  <div>
    <div class="location">
      <!-- Home > 규제 검토 > 규제 검토 요청 상세(향) -->
      <span>Home > {{ $t('LB00000336') }} > {{ $t('LB00000406') }}</span>
    </div>
    <!-- 규제 검토 요청 상세(향) -->
    <div class="work_title">
      <!-- eslint-disable -->
      <h2>{{ $t('LB00000406') }} <span v-html="titleHtml" class="work_title2"></span></h2>
    </div>
    <div class="container_detail">
      <!-- 규제 검토 요청 -->
      <div class="sub_title">
        <h3>{{ $t('MN00000007') }}</h3>
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
                <div class="label_con">{{ frmData.rglt_chk_no }}</div>
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
    <v-container>
      <v-row>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <!-- 성분 정보 -->
            <div class="sub_title">
              <h3>{{ $t('LB00000401') }}</h3>
            </div>
            <div class="grid_header">
              <!-- 총 {{ cnstTotalcnt }}건 -->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ cnstTotalcnt }}{{ $t('LB00000040') }}</div>
            </div>
            <div id="realgrid" style="width: 100%; height: 280px"></div>
          </div>
        </v-col>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <!-- 구성물질 정보 -->
            <div class="sub_title">
              <h3>{{ $t('LB00000400') }}</h3>
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
        </v-col>
      </v-row>
    </v-container>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 예상규제 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000399') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ rgltTotalcnt }}건 -->
        <!-- 예상되는 규제 정보입니다. 규제검토 결과 대상 규제는 달라질 수 있습니다. -->
        <div class="grid_header_left">
          {{ $t('LB00000039') }} {{ rgltTotalcnt }}{{ $t('LB00000040') }}<span class="grid_info">{{ $t('MS00000168') }}</span>
        </div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 규제검토 요청 -->
      <v-btn v-if="apr_yn && authChk" depressed width="120" color="primary" height="40" @click="saveApr">{{ $t('LB00000337') }}</v-btn>
      <!-- 임시저장 -->
      <v-btn v-if="apr_yn && authChk" depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00001234') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popup ref="pop" mkey="rgltchkreqmtrledtsbstslct" @close="popclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/sbstmultislct'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '규제검토-규제검토요청상세',
    key(route) {
      return `/rgltchk/${route.params.catalog}`
    },
  },
  components: {
    popup,
    FileUpload,
  },
  data() {
    return {
      titleHtml: '',
      mtrlModify: false,
      apr_yn: true,
      user: {},
      tmpSchData: {},
      tmpDtlSchData: {},
      schData: {},
      sbstSchData: {},
      frmData: {
        rglt_chk_no: '',
        chk_req_clsf_cd: '',
        rglt_chk_req_emp_nm: '',
        rglt_chk_req_emp_no: '',
        rglt_chk_req_dt: '',
        rglt_chk_req_da: '',
        mtrl_no: '',
        mtrl_nm: '',
        mtrl_clsf_cd: '',
        mtrl_clsf_nm: '',
        macl_cd: '',
        macl_nm: '',
        mtrl_reg_emp_no: '',
        mtrl_reg_emp_nm: '',
        mtrl_reg_dt: '',
        mtrl_reg_da: '',
        wkpl_id: '',
        wkpl_nm: '',
        rglt_chk_req_prog_stt_cd: '',
        phase_cd: '',
        phase_nm: '',
        mtrl_puse_cd: '',
        mtrl_puse_nm: '',
        mtrl_puse_cntn: '',
        mtrl_plant: '',
        mtrl_site: '',
        msds_ver_seq: '',
        ko_atfile_no: '',
        en_atfile_no: '',
        ko_atfile_nm: '',
        en_atfile_nm: '',
        ko_atfile_key: '',
        en_atfile_key: '',
      },
      frmTempData: {},
      mtrl_puse_cd: '',
      basic: 'label_tit',
      puse_yn: '',
      cnstTotalcnt: 0,
      sbstTotalcnt: 0,
      rgltTotalcnt: 0,
      codeid: ['CH00000004', 'CO00000018', 'CH00000017'],
      codes: {
        CH00000004: [{ cd: '', cd_nm: '' }],
        CO00000018: [{ cd: '', cd_nm: '' }],
        CO00000018p: [],
        CH00000017: [{ cd: '', cd_nm: '' }],
      },
      selCH00000004: {
        cd: [],
        cd_nm: [],
      },
      rgltChkNo: '',
      authChk: true,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000414')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.rgltChkNo = this.$route.params.rgltchkreqmtrledt
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
        rglt_chk_req_prog_stt_cd: '',
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
        this.codes.CO00000018.unshift({ cd: '', cd_nm: 'SELECT' })
        const temp = []
        res.CO00000018.forEach((e) => {
          temp.push({ value: e.cd, label: e.cd_nm })
        })
        this.codes.CO00000018p = temp
        this.codes.CH00000017.unshift({ cd: '', cd_nm: 'SELECT' })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'comp_cnst_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cas_no',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'comp_cnst_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'comp_cnst_ratio',
            dataType: ValueType.NUMBER,
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

        const fields3 = [
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
            fieldName: 'rglt_chk_doma_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'nat_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_abbr',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'rglt_type_cd',
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
            header: this.$t('LB00000407'), // 성분코드
            name: 'comp_cnst_no',
            fieldName: 'comp_cnst_no',
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
            name: 'comp_cnst_nm',
            fieldName: 'comp_cnst_nm',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000195'), // 함량(%)
            name: 'comp_cnst_ratio',
            fieldName: 'comp_cnst_ratio',
            width: '100',
            styleName: 'right',
          },
        ]

        const columns3 = [
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
            width: '150',
          },
          {
            header: this.$t('LB00000028'), // 규제
            name: 'rglt_nm',
            fieldName: 'rglt_nm',
            width: '200',
          },
          {
            header: this.$t('LB00000408'), // 대상물질
            name: 'cas_no',
            fieldName: 'cas_no',
            width: '100',
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
            editable: true,
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
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('id', 'visible', false)

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

        gridView2.setColumnProperty('id', 'visible', false)
        gridView2.setColumnProperty('sbst_no', 'visible', false)
        gridView2.setColumnProperty('kyin_yn', 'visible', false)

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

        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/ch/rgltchk/rglt-chk-req-dtl-edt/${this.rgltChkNo}`)
      if (data.auth[0].auth !== 'Y') {
        this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
        this.authChk = false

        // 화학물질 TO-DO 알림 메일의 바로가기 클릭시 권한이 없는 경우 상세 페이지로 이동
        this.$router.push({
          name: `rgltchk-rgltchkreqmtrllup-rgltchkreqmtrllup`,
          params: {
            rgltchkreqmtrllup: this.rgltChkNo,
          },
        })
      }

      this.$refs.upload1.setEdit(false)
      this.$refs.upload2.setEdit(false)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터

        if (this.isEmpty(this.frmData.mtrl_puse_cd)) {
          this.mtrl_puse_cd = { value: '', label: 'SELECT' }
        } else {
          this.mtrl_puse_cd = { value: this.frmData.mtrl_puse_cd, label: this.frmData.mtrl_puse_nm }
        }

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
      }

      dataProvider.setRows(data.cnst)
      gridView.refresh()
      gridView.setTopItem(0)

      this.cnstTotalcnt = dataProvider.getRowCount()

      dataProvider2.setRows(data.sbst)
      gridView2.refresh()
      gridView2.setTopItem(0)

      this.sbstTotalcnt = dataProvider2.getRowCount()

      dataProvider3.setRows(data.rglt)
      gridView3.refresh()
      gridView3.setTopItem(0)

      this.rgltTotalcnt = dataProvider3.getRowCount()

      // 둘다 값이 와이이면...'※ MSDS 제출 대상 자재'
      if (this.frmData.imp_yn === 'Y' && this.frmData.sbms_tgt_yn === 'Y') {
        this.titleHtml = this.$t('LB00000620')
      }
    },
    async save() {
      gridView.commit()
      gridView2.commit()
      gridView3.commit()

      if (dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // this.frmData.mtrl_puse_cd = this.mtrl_puse_cd
      // Validation 체크
      if (!this.validate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (
        dataProvider2.getRowStateCount('updated') > 0 ||
        dataProvider2.getRowStateCount('deleted') > 0 ||
        dataProvider2.getRowStateCount('created') > 0 ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)
      ) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider2.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider2.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider2.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider2.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider2.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider2.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow

        const rgltRow = []

        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider3.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider3.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider3.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider3.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider3.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider3.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider3.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider3.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider3.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider3.getValue(i, 'rglt_abbr')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow

        const infoRow = []
        const temp = {}
        temp.rglt_chk_no = this.frmData.rglt_chk_no
        temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
        temp.mtrl_no = this.frmData.mtrl_no
        temp.username = this.user.emp_no
        temp.rglt_chk_req_prog_stt_cd = this.frmData.rglt_chk_req_prog_stt_cd
        temp.macl_cd = this.frmData.macl_cd
        temp.phase_cd = this.frmData.phase_cd
        temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
        temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
        infoRow.push(temp)
        this.frmData.infoRow = infoRow

        await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-req-dtl-mtrl', this.frmData).then((res) => {
          console.log(res) // eslint-disable-line no-console
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
    async saveApr() {
      // this.frmData.mtrl_puse_cd = this.mtrl_puse_cd
      // Validation 체크
      if (!this.validate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000170')) // 검토요청 하시겠습니까?
      if (!res) {
        return
      }

      gridView.commit()
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (
        dataProvider2.getRowStateCount('updated') > 0 ||
        dataProvider2.getRowStateCount('deleted') > 0 ||
        dataProvider2.getRowStateCount('created') > 0 ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)
      ) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider2.getValue(i, 'sbst_no')
          temp.rglt_chk_no = this.frmData.rglt_chk_no
          temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
          temp.mtrl_no = this.frmData.mtrl_no
          temp.sbst_seq = dataProvider2.getValue(i, 'sbst_seq')
          temp.sbst_type_cd = dataProvider2.getValue(i, 'sbst_type_cd')
          temp.cas_no = dataProvider2.getValue(i, 'cas_no')
          temp.sbst_nm = dataProvider2.getValue(i, 'sbst_nm')
          temp.sbst_rati = dataProvider2.getValue(i, 'sbst_rati')
          temp.sbst_note = dataProvider2.getValue(i, 'sbst_note')
          temp.username = this.user.emp_no

          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow

        const rgltRow = []

        for (let i = 0; i < dataProvider3.getRowCount(); i++) {
          const temp = {}

          temp.rglt_chk_no = dataProvider3.getValue(i, 'rglt_chk_no')
          temp.chk_req_clsf_cd = dataProvider3.getValue(i, 'chk_req_clsf_cd')
          temp.mtrl_no = dataProvider3.getValue(i, 'mtrl_no')
          temp.rglt_no = dataProvider3.getValue(i, 'rglt_no')
          temp.rglt_chk_doma_cd = dataProvider3.getValue(i, 'rglt_chk_doma_cd')
          temp.nat_cd = dataProvider3.getValue(i, 'nat_cd')
          temp.rglt_type_cd = dataProvider3.getValue(i, 'rglt_type_cd')
          temp.law_cd = dataProvider3.getValue(i, 'law_cd')
          temp.rglt_nm = dataProvider3.getValue(i, 'rglt_nm')
          temp.rglt_abbr = dataProvider3.getValue(i, 'rglt_abbr')
          temp.username = this.user.emp_no

          rgltRow.push(temp)
        }

        this.frmData.rgltRow = rgltRow
      }
      const infoRow = []
      const temp = {}
      temp.rglt_chk_no = this.frmData.rglt_chk_no
      temp.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      temp.rglt_chk_req_emp_no = this.frmData.rglt_chk_req_emp_no
      temp.rglt_chk_req_da = this.frmData.rglt_chk_req_da
      temp.mtrl_no = this.frmData.mtrl_no
      temp.wkpl_id = this.frmData.wkpl_id
      temp.username = this.user.emp_no
      temp.rglt_chk_req_prog_stt_cd = this.frmData.rglt_chk_req_prog_stt_cd
      temp.macl_cd = this.frmData.macl_cd
      temp.phase_cd = this.frmData.phase_cd
      temp.mtrl_puse_cd = this.frmData.mtrl_puse_cd
      temp.mtrl_puse_cntn = this.frmData.mtrl_puse_cntn
      temp.ko_atfile_no = this.frmData.ko_atfile_no
      temp.en_atfile_no = this.frmData.en_atfile_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/ch/rgltchk/rglt-chk-req-dtl-apr', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000171')) // 검토요청하였습니다.
          this.$router.push({
            name: `rgltchk-rgltchkreqlist`,
            params: {
              schData: {
                wkpl_id: this.tmpSchData.wkpl_id,
                mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
                macl_cd: this.tmpSchData.macl_cd,
                mtrl_no: this.tmpSchData.mtrl_no,
                mtrl_nm: this.tmpSchData.mtrl_nm,
                rglt_chk_req_prog_stt_cd: this.tmpSchData.rglt_chk_req_prog_stt_cd,
                rglt_chk_req_emp_no: this.tmpSchData.rglt_chk_req_emp_no,
                userno: this.tmpSchData.userno,
                username: this.tmpSchData.username,
                sDate: this.tmpSchData.sDate,
                eDate: this.tmpSchData.eDate,
              },
            },
          })
          // this.apr_yn = false
          // this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    sbstAdd() {
      gridView2.commit()
      this.$refs.pop.open(this.frmData, true)
    },
    async popclose(returnData) {
      gridView2.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          let passyn = null
          for (let j = 0; j < dataProvider2.getRowCount(); j++) {
            if (dataProvider2.getRowState(j) !== 'deleted' && dataProvider2.getRowState(j) !== 'createAndDeleted') {
              if (i.sbst_no === dataProvider2.getValue(j, 'sbst_no') && i.sbst_no !== '100000000001') {
                passyn = 'Y'
              }
            }
          }

          if (passyn === null) {
            dataProvider2.addRow({
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
            this.$toast.show(i.sbst_ko_nm + this.$t('MS00000132'), { className: 'toast_error' })
          }
        })
        // 예상 규제 조회
        // this.selectSbstAddDel()
      }
    },
    sbstDel() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)

      // 예상 규제 조회
      // this.selectSbstAddDel()
    },
    async selectSbstAddDel() {
      // 물질이 추가되거나 삭제되면 규제를 새로 조회
      let searchSbst = ''
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        if (dataProvider2.getRowState(i) !== 'deleted' && dataProvider2.getRowState(i) !== 'createAndDeleted') {
          searchSbst = searchSbst + "'" + dataProvider2.getValue(i, 'sbst_no') + "',"
        }
      }
      searchSbst = searchSbst.substring(0, searchSbst.length - 1)

      this.sbstSchData.search_sbst_no = searchSbst
      this.sbstSchData.rglt_chk_no = this.frmData.rglt_chk_no
      this.sbstSchData.chk_req_clsf_cd = this.frmData.chk_req_clsf_cd
      this.sbstSchData.mtrl_no = this.frmData.mtrl_no

      const data = await this.$axios.$get('/api/v1/ch/rgltchk/rglt-chk-req-sbst-add-list', { params: this.sbstSchData })

      dataProvider3.setRows(data)
      gridView3.refresh()
      gridView3.setTopItem(0)

      this.rgltTotalcnt = dataProvider3.getRowCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    validate() {
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
      if (gridView2.getItemCount() <= 0) {
        this.$toast.show(this.$t('MS00000052'), { className: 'toast_error' }) // 구성 물질 정보를 최소 한개 추가 해 주세요.
        return false
      }
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'sbst_type_cd'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'sbst_type_cd' })
          this.$toast.show(this.$t('MS00000140'), { className: 'toast_error' }) // 물질구분을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'sbst_rati'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'sbst_rati' })
          this.$toast.show(this.$t('MS00000174'), { className: 'toast_error' }) // 함량을 입력해주세요.
          return false
        }
        if (isNaN(dataProvider2.getValue(i, 'sbst_rati'))) {
          this.$toast.show(this.$t('MS00000163'), { className: 'toast_error' }) // 함량은 숫자만 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'sbst_rati') * 1 < 0.0000001 || dataProvider2.getValue(i, 'sbst_rati') * 1 > 100) {
          this.$toast.show(this.$t('MS00000228'), { className: 'toast_error' }) // 함량을 범위에 맞게 입력해 주세요.(0.0000001 이상, 100 미만)
          return false
        }
        if (!this.isEmpty(dataProvider2.getValue(i, 'sbst_note')) && dataProvider2.getValue(i, 'sbst_note').length > 200) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'sbst_note' })
          this.$toast.show(this.$t('MS00000176'), { className: 'toast_error' }) // 비고는 200자 이하로 입력해주세요.
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
        // mtrl_puse_cntn
        // this.$refs.mtrl_puse_cntn.$props
      }
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
    async goList() {
      gridView2.commit()

      if (dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() || JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
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
            name: `rgltchk-rgltchkreqlist`,
            params: {
              schData: {
                wkpl_id: this.tmpSchData.wkpl_id,
                mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
                macl_cd: this.tmpSchData.macl_cd,
                mtrl_no: this.tmpSchData.mtrl_no,
                mtrl_nm: this.tmpSchData.mtrl_nm,
                rglt_chk_req_prog_stt_cd: this.tmpSchData.rglt_chk_req_prog_stt_cd,
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
          name: `rgltchk-rgltchkreqlist`,
          params: {
            schData: {
              wkpl_id: this.tmpSchData.wkpl_id,
              mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
              macl_cd: this.tmpSchData.macl_cd,
              mtrl_no: this.tmpSchData.mtrl_no,
              mtrl_nm: this.tmpSchData.mtrl_nm,
              rglt_chk_req_prog_stt_cd: this.tmpSchData.rglt_chk_req_prog_stt_cd,
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
