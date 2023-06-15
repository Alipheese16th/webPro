<template>
  <div>
    <div class="location">
      <!-- Home > 현황/통계 > 화학물질 현황 -->
      <span>Home > {{ $t('LB00000488') }} > {{ $t('LB00000559') }}</span>
    </div>
    <!-- 화학물질 현황 -->
    <div class="work_title">
      <h2>{{ $t('LB00000559') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 조회기간 -->
                <div class="label_tit">{{ $t('LB00000450') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sDate"
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
                  <v-date-picker v-model="schData.sDate" :max="schData.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.eDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="schData.eDate" :min="schData.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!-- 사업장 -->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.wkpl_id"
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
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재구분 -->
                <div class="label_tit">{{ $t('LB00000022') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.mtrl_clsf_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000017"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 품목구분 -->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.macl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000006"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 위험물안전관리법 -->
                <div class="label_tit">{{ $t('CD00000737') }}</div>
              </v-col>
              <!-- <v-col cols="3">
                <v-checkbox v-model="schData.dng_all" :label="$t('LB00000504')" height="30" :hide-details="true" @change="chkboxChng('dng')"></v-checkbox>
                <v-combobox
                  v-model="dng_cd"
                  label="ALL"
                  item-text="label"
                  item-value="value"
                  :items="dngData"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                ></v-combobox>
              </v-col> -->
              <v-col cols="3">
                <v-checkbox v-model="schData.dngr_kind_all" :label="$t('LB00000504')" height="30" :hide-details="true" @change="chkboxChng('dngr')"></v-checkbox>
                <v-combobox
                  v-model="dngr_kind_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000027"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                  @change="codChg2"
                ></v-combobox>
              </v-col>
              <v-col cols="3">
                <div style="height: 30px"></div>
                <v-combobox
                  v-model="dngr_dtl_kind_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000028"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                ></v-combobox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 화관법 -->
                <div class="label_tit">{{ $t('LB00000503') }}</div>
              </v-col>
              <v-col cols="3">
                <v-checkbox v-model="schData.osha_all" :label="$t('LB00000504')" height="30" :hide-details="true" @change="chkboxChng('osha')"></v-checkbox>
                <v-combobox
                  v-model="osha_cd"
                  label="ALL"
                  item-text="label"
                  item-value="value"
                  :items="oshaData"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                ></v-combobox>
              </v-col>
              <v-col cols="1">
                <!-- 화평법 -->
                <div class="label_tit">{{ $t('LB00000502') }}</div>
              </v-col>
              <v-col cols="3">
                <v-checkbox v-model="schData.arec_all" :label="$t('LB00000504')" height="30" :hide-details="true" @change="chkboxChng('arec')"></v-checkbox>
                <v-combobox
                  v-model="arec_cd"
                  label="ALL"
                  item-text="label"
                  item-value="value"
                  :items="arecData"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                ></v-combobox>
              </v-col>
              <v-col cols="1">
                <!-- 산안법 -->
                <div class="label_tit">{{ $t('LB00000501') }}</div>
              </v-col>
              <v-col cols="3">
                <v-checkbox v-model="schData.tcca_all" :label="$t('LB00000504')" height="30" :hide-details="true" @change="chkboxChng('tcca')"></v-checkbox>
                <v-combobox
                  ref="tccaCombo"
                  v-model="tcca_cd"
                  label="ALL"
                  item-text="label"
                  item-value="value"
                  :items="tccaData"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  multiple
                ></v-combobox>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 화학물질 현황 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000559') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 그리드 버튼 영역 -->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '현황/통계-화학물질 현황',
  },
  components: {},
  data() {
    return {
      tabs: null,
      schData: {
        dngr_kind_all: false,
        dngr_dtl_kind_all: false,
      },
      frmData: {},
      dngr_kind_cd: [],
      dngr_dtl_kind_cd: [],
      tccaData: [],
      arecData: [],
      oshaData: [],
      dngData: [],
      tcca_cd: '',
      arec_cd: '',
      osha_cd: '',
      dng_cd: '',
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CO00000006', 'CO00000017', 'CH00000006', 'CH00000027', 'CH00000028'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000017: [{ cd: '', cd_nm: '' }],
        CH00000006: [{ cd: '', cd_nm: '' }],
        CH00000027: [{ cd: '', cd_nm: '' }],
        CH00000028: [{ cd: '', cd_nm: '' }],
        F_CH00000006: [{ cd: '', cd_nm: 'ALL' }],
        F_CH00000028: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000559')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(user.time_zone)
    sDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(sDate.getDate() - 7)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'macl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'vndr_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'vndr_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'impt_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_puse_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wrk_center_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_rati',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt01_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt02_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt03_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt04_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt05_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt06_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt07_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt08_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt09_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt10_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt11_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt12_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt13_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt14_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt15_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt16_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt17_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt18_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt19_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt20_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_qty',
        dataType: ValueType.NUMBER,
      },
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000021'), // 사업장
        },
        name: this.$t('LB00000021'),
        fieldName: 'wkpl_nm',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000023'), // 품목구분
        },
        name: this.$t('LB00000023'),
        fieldName: 'macl_nm',
        width: '250',
      },
      {
        header: {
          text: this.$t('LB00000024'), // 자재코드
        },
        name: this.$t('LB00000024'),
        fieldName: 'mtrl_no',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000025'), // 자재명
        },
        name: this.$t('LB00000025'),
        fieldName: 'mtrl_nm',
        width: '300',
      },
      {
        header: {
          text: this.$t('LB00000561'), // 벤더코드
        },
        name: this.$t('LB00000561'),
        fieldName: 'vndr_no',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000562'), // 벤더명
        },
        name: this.$t('LB00000562'),
        fieldName: 'vndr_nm',
        width: '150',
      },
      {
        header: {
          text: this.$t('LB00000563'), // 내/외자
        },
        name: this.$t('LB00000563'),
        fieldName: 'impt_yn',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000494'), // 용도
        },
        name: this.$t('LB00000494'),
        fieldName: 'mtrl_puse_nm',
        width: '150',
      },
      {
        header: {
          text: this.$t('LB00000564'), // 사용부서
        },
        name: this.$t('LB00000564'),
        fieldName: 'wrk_center_nm',
        width: '150',
      },
      {
        header: {
          text: this.$t('LB00000026'), // 구성물질
        },
        name: this.$t('LB00000026'),
        fieldName: 'sbst_nm',
        width: '350',
      },
      {
        header: {
          text: this.$t('LB00000565'), // CAS번호
        },
        name: this.$t('LB00000565'),
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000566'), // 함유량
        },
        name: this.$t('LB00000566'),
        fieldName: 'sbst_rati',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000567'), // 유독물질
        },
        name: this.$t('LB00000567'),
        fieldName: 'rglt01_yn',
        width: '60',
      },
      {
        header: {
          text: this.$t('LB00000571'), // 사고대비물질
        },
        name: this.$t('LB00000571'),
        fieldName: 'rglt02_yn',
        width: '60',
      },
      {
        header: {
          text: this.$t('LB00000576'), // 배출량조사대상물질
        },
        name: this.$t('LB00000576'),
        fieldName: 'rglt03_yn',
        width: '70',
      },
      {
        header: {
          text: this.$t('LB00000577'), // 취급제한물질
        },
        name: this.$t('LB00000577'),
        fieldName: 'rglt04_yn',
        width: '60',
      },
      {
        header: {
          text: this.$t('LB00000578'), // 취급금지물질
        },
        name: this.$t('LB00000578'),
        fieldName: 'rglt05_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000569'), // 허가물질
        },
        name: this.$t('LB00000569'),
        fieldName: 'rglt06_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000579'), // 기존화학물질
        },
        name: this.$t('LB00000579'),
        fieldName: 'rglt07_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000580'), // 등록대상기존화학물질
        },
        name: this.$t('LB00000580'),
        fieldName: 'rglt08_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000581'), // 21년까지 등록하여야 할 CMR물질
        },
        name: this.$t('LB00000581'),
        fieldName: 'rglt09_yn',
        width: '105',
      },
      {
        header: {
          text: this.$t('LB00000582'), // 중점관리물질
        },
        name: this.$t('LB00000582'),
        fieldName: 'rglt10_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000589'), // 노출기준설정물질
        },
        name: this.$t('LB00000589'),
        fieldName: 'rglt11_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000583'), // 허용기준설정물질
        },
        name: this.$t('LB00000583'),
        fieldName: 'rglt12_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000584'), // 관리대상유해물질
        },
        name: this.$t('LB00000584'),
        fieldName: 'rglt13_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000585'), // 특별관리대상물질
        },
        name: this.$t('LB00000585'),
        fieldName: 'rglt14_yn',
        width: '90',
      },
      {
        header: {
          text: this.$t('LB00000586'), // 작업환경측정대상 유해인자
        },
        name: this.$t('LB00000586'),
        fieldName: 'rglt15_yn',
        width: '90',
      },
      {
        header: {
          text: this.$t('LB00000587'), // 특수건강진단대상 유해인자
        },
        name: this.$t('LB00000587'),
        fieldName: 'rglt16_yn',
        width: '90',
      },
      {
        header: {
          text: this.$t('LB00000588'), // 제조허가 유해물질
        },
        name: this.$t('LB00000588'),
        fieldName: 'rglt17_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000590'), // 제조금지물질
        },
        name: this.$t('LB00000590'),
        fieldName: 'rglt18_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000591'), // PSM대상물질
        },
        name: this.$t('LB00000591'),
        fieldName: 'rglt19_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000575'), // 위험물
        },
        name: this.$t('LB00000575'),
        fieldName: 'rglt20_yn',
        width: '85',
      },
      {
        header: {
          text: this.$t('LB00000428'), // 사용량
        },
        name: this.$t('LB00000428'),
        fieldName: 'use_qty',
        width: '100',
      },
    ]
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.F_CH00000028 = []
        this.codes.CH00000027 = this.codes.CH00000027.filter(function (n) {
          // 해당없음 제외
          return n.buf_1st_cntn === '1'
        })
        this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000017.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CH00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CH00000028.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
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

    gridView.header.height = 60
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.formatOptions.numberFormat = null

    gridView.setColumnProperty('id', 'visible', false)

    this.selectRgltComnbo()
    this.select()
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
    async selectRgltComnbo() {
      const data = await this.$axios.$get(`/api/v1/ch/csttstat/rglt-combo-list`, { params: this.schData })

      const temp1 = []
      data.tcca.forEach((e) => {
        temp1.push({ value: e.value, label: e.label })
      })
      console.log(temp1) // eslint-disable-line no-console
      this.tccaData = temp1

      const temp2 = []
      data.arec.forEach((e) => {
        temp2.push({ value: e.value, label: e.label })
      })
      console.log(temp2) // eslint-disable-line no-console
      this.arecData = temp2

      const temp3 = []
      data.osha.forEach((e) => {
        temp3.push({ value: e.value, label: e.label })
      })
      console.log(temp3) // eslint-disable-line no-console
      this.oshaData = temp3

      const temp4 = []
      data.dng.forEach((e) => {
        temp4.push({ value: e.value, label: e.label })
      })
      console.log(temp4) // eslint-disable-line no-console
      this.dngData = temp4
    },
    async select() {
      // 화관법, 화평법, 산안법 선택한 코드값을 Y로 변환
      this.rgltChange()
      console.log('data : ', this.schData) // eslint-disable-line no-console
      await this.$axios.post(`/api/v1/ch/csttstat/rglt-chk-total-list`, this.schData).then((res) => {
        dataProvider.setRows(res.data)
        gridView.refresh()
        gridView.setTopItem(0)
      })
      this.totalcnt = dataProvider.getRowCount()

      let tccaCdVal = ''
      for (let i = 0; i < this.tcca_cd.length; i++) {
        tccaCdVal = tccaCdVal + "'" + this.tcca_cd[i].value + "',"
      }
      tccaCdVal = tccaCdVal.substring(0, tccaCdVal.length - 1)

      let arecCdVal = ''
      for (let i = 0; i < this.arec_cd.length; i++) {
        arecCdVal = arecCdVal + "'" + this.arec_cd[i].value + "',"
      }
      arecCdVal = arecCdVal.substring(0, arecCdVal.length - 1)

      let oshaCdVal = ''
      for (let i = 0; i < this.osha_cd.length; i++) {
        oshaCdVal = oshaCdVal + "'" + this.osha_cd[i].value + "',"
      }
      oshaCdVal = oshaCdVal.substring(0, oshaCdVal.length - 1)

      let dngCdVal = ''
      for (let i = 0; i < this.dng_cd.length; i++) {
        dngCdVal = dngCdVal + "'" + this.dng_cd[i].value + "',"
      }
      dngCdVal = dngCdVal.substring(0, dngCdVal.length - 1)

      this.schData.tcca_cd = tccaCdVal
      this.schData.arec_cd = arecCdVal
      this.schData.osha_cd = oshaCdVal
      this.schData.dng_cd = dngCdVal

      console.log(oshaCdVal) // eslint-disable-line no-console
    },
    schReset() {
      this.schData.wkpl_id = null
      this.schData.mtrl_clsf_cd = null
      this.schData.macl_cd = null
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
      this.schData.tcca_cd = null
      this.schData.arec_cd = null
      this.schData.osha_cd = null
      this.tcca_cd = []
      this.arec_cd = []
      this.osha_cd = []
      this.dng_cd = []

      // 화관법, 화평법, 산안법, 위험물안전관리법 초기화
      this.rgltRest()
    },
    refresh() {
      this.select()
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000006 = this.codes.CH00000006.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.schData.macl_cd = ''
    },
    codChg2(data) {
      const temp = []
      // 코드 초기화
      this.$set(this, 'dngr_dtl_kind_cd', [])
      this.$set(this.schData, 'dngr_dtl_kind_all', false)
      if (data === '') {
        for (let j = 0; j < this.codes.CH00000028.length; j++) {
          if (this.codes.CH00000028[j].cd !== '' && this.codes.CH00000028[j].cd !== '999') {
            temp.push({ cd: this.codes.CH00000028[j].cd, cd_nm: this.codes.CH00000028[j].cd_nm })
          }
        }
      } else {
        for (let i = 0; i < data.length; i++) {
          // 상위 코드 조회 후 필터링
          for (let j = 0; j < this.codes.CH00000028.length; j++) {
            if (this.codes.CH00000028[j].up_cd === data[i].cd) {
              temp.push({ cd: this.codes.CH00000028[j].cd, cd_nm: this.codes.CH00000028[j].cd_nm })
            }
          }
        }
      }
      this.codes.F_CH00000028 = temp
    },
    chkboxChng(val) {
      if (val === 'tcca') {
        if (this.schData.tcca_all === true) {
          this.tcca_cd = []
          for (let i = 0; i < this.tccaData.length; i++) {
            this.tcca_cd.push({ value: this.tccaData[i].value, label: this.tccaData[i].label })
          }
        } else {
          this.tcca_cd = []
        }
      } else if (val === 'arec') {
        if (this.schData.arec_all === true) {
          this.arec_cd = []
          for (let i = 0; i < this.arecData.length; i++) {
            this.arec_cd.push({ value: this.arecData[i].value, label: this.arecData[i].label })
          }
        } else {
          this.arec_cd = []
        }
      } else if (val === 'osha') {
        if (this.schData.osha_all === true) {
          this.osha_cd = []
          for (let i = 0; i < this.oshaData.length; i++) {
            this.osha_cd.push({ value: this.oshaData[i].value, label: this.oshaData[i].label })
          }
        } else {
          this.osha_cd = []
        }
      } else if (val === 'dng') {
        if (this.schData.dng_all === true) {
          this.dng_cd = []
          for (let i = 0; i < this.dngData.length; i++) {
            this.dng_cd.push({ value: this.dngData[i].value, label: this.dngData[i].label })
          }
        } else {
          this.dng_cd = []
        }
      } else if (val === 'dngr') {
        if (this.schData.dngr_kind_all === true) {
          this.$set(this, 'dngr_kind_cd', [])
          this.$set(this, 'dngr_dtl_kind_cd', [])
          this.codChg2('')
          for (let i = 0; i < this.codes.CH00000027.length; i++) {
            this.dngr_kind_cd.push({ cd: this.codes.CH00000027[i].cd, cd_nm: this.codes.CH00000027[i].cd_nm })
          }
        } else {
          this.$set(this.codes, 'F_CH00000028', [])
          this.$set(this.schData, 'dngr_dtl_kind_all', false)
          this.$set(this, 'dngr_kind_cd', [])
          this.$set(this, 'dngr_dtl_kind_cd', [])
        }
      }
    },
    rgltChange() {
      // 법 선택여부 초기화
      this.rgltRest()
      // 법 선택여부 확인
      if (this.osha_cd.length > 0 || this.arec_cd.length > 0 || this.tcca_cd.length > 0 || this.dng_cd.length > 0) {
        this.schData.rgltSelect = true
      } else {
        this.schData.rgltSelect = ''
      }
      // eslint-disable-next-line no-console
      console.log('this.schData.rgltSelect : ', this.schData.rgltSelect)

      // 화관법 여부 저장
      // eslint-disable-next-line no-console
      console.log('this.osha_cd : ', this.osha_cd)
      for (let i = 0; i < this.osha_cd.length; i++) {
        switch (this.osha_cd[i].value) {
          case 'RGLT000001':
            this.schData.rglt01_yn = 'Y'
            break
          case 'RGLT000002':
            this.schData.rglt02_yn = 'Y'
            break
          case 'RGLT000003':
            this.schData.rglt03_yn = 'Y'
            break
          case 'RGLT000004':
            this.schData.rglt04_yn = 'Y'
            break
          case 'RGLT000005':
            this.schData.rglt05_yn = 'Y'
            break
          case 'RGLT000006':
            this.schData.rglt06_yn = 'Y'
            break
        }
      }
      // 화평법 여부 저장
      // eslint-disable-next-line no-console
      console.log('this.arec_cd : ', this.arec_cd)
      for (let i = 0; i < this.arec_cd.length; i++) {
        switch (this.arec_cd[i].value) {
          case 'RGLT000007':
            this.schData.rglt07_yn = 'Y'
            break
          case 'RGLT000008':
            this.schData.rglt08_yn = 'Y'
            break
          case 'RGLT000009':
            this.schData.rglt09_yn = 'Y'
            break
          case 'RGLT000010':
            this.schData.rglt10_yn = 'Y'
            break
        }
      }
      // 산안법 여부 저장
      // eslint-disable-next-line no-console
      console.log('this.tcca_cd : ', this.tcca_cd)
      for (let i = 0; i < this.tcca_cd.length; i++) {
        switch (this.tcca_cd[i].value) {
          case 'RGLT000011':
            this.schData.rglt11_yn = 'Y'
            break
          case 'RGLT000012':
            this.schData.rglt12_yn = 'Y'
            break
          case 'RGLT000013':
            this.schData.rglt13_yn = 'Y'
            break
          case 'RGLT000014':
            this.schData.rglt14_yn = 'Y'
            break
          case 'RGLT000015':
            this.schData.rglt15_yn = 'Y'
            break
          case 'RGLT000016':
            this.schData.rglt16_yn = 'Y'
            break
          case 'RGLT000017':
            this.schData.rglt17_yn = 'Y'
            break
          case 'RGLT000018':
            this.schData.rglt18_yn = 'Y'
            break
          case 'RGLT000019':
            this.schData.rglt19_yn = 'Y'
            break
        }
      }

      // 위험물안전관리법 여부 저장
      // eslint-disable-next-line no-console
      console.log('this.dng_cd : ', this.dng_cd)

      if (this.dngr_kind_cd.length > 0) {
        this.schData.rglt20_yn = 'Y'
        this.schData.dngr_kind_cd_json = JSON.stringify(this.dngr_kind_cd)
        this.schData.dngr_dtl_kind_cd_json = JSON.stringify(this.dngr_dtl_kind_cd)
      }
    },
    rgltRest() {
      // 화관법, 화평법, 산안법 초기화
      this.schData.rgltSelect = null

      this.schData.rglt01_yn = null
      this.schData.rglt02_yn = null
      this.schData.rglt03_yn = null
      this.schData.rglt04_yn = null
      this.schData.rglt05_yn = null
      this.schData.rglt06_yn = null
      this.schData.rglt07_yn = null
      this.schData.rglt08_yn = null
      this.schData.rglt09_yn = null
      this.schData.rglt10_yn = null
      this.schData.rglt11_yn = null
      this.schData.rglt12_yn = null
      this.schData.rglt13_yn = null
      this.schData.rglt14_yn = null
      this.schData.rglt15_yn = null
      this.schData.rglt16_yn = null
      this.schData.rglt17_yn = null
      this.schData.rglt18_yn = null
      this.schData.rglt19_yn = null
      this.schData.rglt20_yn = null
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '화학물질_현황_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
