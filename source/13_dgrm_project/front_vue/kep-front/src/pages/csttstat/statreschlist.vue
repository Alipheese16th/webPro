<template>
  <div>
    <div class="location">
      <!-- Home > 현황/통계 > 화학물질 통계조사 -->
      <span>Home > {{ $t('LB00000488') }} > {{ $t('LB00000531') }}</span>
    </div>
    <!-- 화학물질 통계조사 -->
    <div class="work_title">
      <h2>{{ $t('LB00000531') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 구매/입고 기간 -->
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
                ></v-select>
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
    <div>
      <v-tabs align-with-title slider-size="5" color="#062C5E">
        <v-tabs-slider color="yellow"></v-tabs-slider>
        <!-- 제품정보 -->
        <v-tab :style="styleObject" @click="tabChg('1')">{{ $t('LB00000445') }}</v-tab>
        <!-- 구성성분정보 -->
        <v-tab :style="styleObject" @click="tabChg('2')">{{ $t('LB00000446') }}</v-tab>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header">
                <!-- 총 {{ prodTotalcnt }}건 -->
                <div class="grid_header_left">{{ $t('LB00000039') }} {{ prodTotalcnt }}{{ $t('LB00000040') }}</div>
                <div>
                  <!-- 그리드 버튼 영역 -->
                  <v-btn color="primary" depressed height="30" @click="excelExport('prod')">{{ $t('LB00000214') }}</v-btn>
                </div>
              </div>
              <div id="realgridProd" style="width: 100%; height: 480px"></div>
            </div>
          </v-card>
        </v-tab-item>
        <v-tab-item :eager="true">
          <v-card flat>
            <div class="realgrid_container">
              <div class="grid_header">
                <!-- 총 {{ cnstTotalcnt }}건 -->
                <div class="grid_header_left">{{ $t('LB00000039') }} {{ cnstTotalcnt }}{{ $t('LB00000040') }}</div>
                <div>
                  <!-- 그리드 버튼 영역 -->
                  <v-btn color="primary" depressed height="30" @click="excelExport('cnst')">{{ $t('LB00000214') }}</v-btn>
                </div>
              </div>
              <div id="realgridCnst" style="width: 100%; height: 420px"></div>
            </div>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '현황/통계-화학물질 통계조사',
  },
  components: {},
  fetch() {
    // this.select()
  },
  data() {
    return {
      tabs: null,
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      prodTotalcnt: 0,
      cnstTotalcnt: 0,
      codeid: ['CO00000006', 'CO00000017'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000017: [{ cd: '', cd_nm: '' }],
      },
      styleObject: {
        // color: 'red',
        fontSize: '20px',
        fontWeight: 'bold',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000531')
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
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_clsf',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_puse',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_cmp',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'phase_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_unit',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wh03',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dl01',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'user_nm',
        dataType: ValueType.TEXT,
      },
    ]

    const fields2 = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
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
        fieldName: 'ke_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_rati',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'make_clsf',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'no_sbst_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'user_nm',
        dataType: ValueType.TEXT,
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
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '80',
      },
      {
        header: this.$t('LB00000464'), // 제품구분
        name: 'mtrl_clsf',
        fieldName: 'mtrl_clsf',
        width: '100',
      },
      {
        header: this.$t('LB00000465'), // 제품명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '250',
      },
      {
        header: this.$t('LB00000466'), // 용도구분
        name: 'mtrl_puse',
        fieldName: 'mtrl_puse',
        width: '150',
      },
      {
        header: this.$t('LB00000467'), // 기타용도구분
        name: 'mtrl_puse_cntn',
        fieldName: 'mtrl_puse_cntn',
        width: '150',
      },
      {
        header: this.$t('LB00000468'), // 제품구성
        name: 'mtrl_cmp',
        fieldName: 'mtrl_cmp',
        width: '100',
      },
      {
        header: this.$t('LB00000469'), // 제품형태
        name: 'phase_cd',
        fieldName: 'phase_cd',
        width: '100',
      },
      {
        header: this.$t('LB00000470'), // 단위
        name: 'mtrl_unit',
        fieldName: 'mtrl_unit',
        width: '50',
      },
      {
        header: this.$t('LB00000139'), // 구매
        name: 'wh03',
        fieldName: 'wh03',
        width: '200',
      },
      {
        header: this.$t('LB00000472'), // 사용
        name: 'dl01',
        fieldName: 'dl01',
        width: '200',
      },
      {
        header: this.$t('LB00000395'), // 검토담당자
        name: 'user_nm',
        fieldName: 'user_nm',
        width: '100',
      },
      /* 
      {
        header: this.$t('LB00000024'), // 보관시설
        name: this.$t('LB00000024'),
        fieldName: '',
        width: '100',
      },
      {
        header: this.$t('LB00000024'), // 저장시설
        name: this.$t('LB00000024'),
        fieldName: '',
        width: '100',
      },
      {
        header: this.$t('LB00000024'), // 제품취급시설현황
        name: this.$t('LB00000024'),
        fieldName: '',
        width: '100',
      },
      */
    ]

    const headergroup = [
      'mtrl_no', // 자재코드
      'mtrl_clsf', // 제품구분
      'mtrl_nm', // 제품명
      'mtrl_puse', // 용도구분
      'mtrl_puse_cntn', // 기타용도구분
      'mtrl_cmp', // 제품구성
      'phase_cd', // 제품형태
      'mtrl_unit', // 단위
      {
        name: 'companyGroup1',
        direction: 'horizontal',
        items: ['wh03'],
        header: {
          text: this.$t('LB00000479'), // 입고량
        },
      },
      {
        name: 'companyGroup2',
        direction: 'horizontal',
        items: ['dl01'],
        header: {
          text: this.$t('LB00000480'), // 출고량
        },
      },
      /*
      {
        name: 'companyGroup3',
        direction: 'horizontal',
        items: ['nanosbst_make', 'nanosbst_impt', 'nanosbst_na', 'nanosbst_ud'],
        header: {
          text: this.$t('LB00000481'), // 나노물질 포함여부
        },
      },
       */
      {
        name: 'companyGroup3',
        direction: 'horizontal',
        items: ['user_nm'],
        header: {
          text: this.$t('LB00000101'), // 담당자
        },
      },
      /* 
      {
        name: 'companyGroup5',
        direction: 'horizontal',
        items: ['strequip_yn', 'strequip_sav_qtt'],
        header: {
          text: this.$t('LB00000482'), // 보관시설
        },
      },
      {
        name: 'companyGroup6',
        direction: 'horizontal',
        items: ['savequip_yn', 'savequip_sav_qtt'],
        header: {
          text: this.$t('LB00000483'), // 저장시설
        },
      },
      {
        name: 'companyGroup7',
        direction: 'horizontal',
        items: ['prod_equip_cstt'],
        header: {
          text: this.$t('LB00000484'), // 제품취급시설현황
        },
      },
      */
    ]

    const columns2 = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '100',
      },
      {
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
      },
      {
        header: {
          text: 'CAS No.',
        },
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '150',
      },
      {
        header: {
          text: this.$t('LB00000196'), // 물질명
        },
        name: 'sbst_nm',
        fieldName: 'sbst_nm',
        width: '250',
      },
      {
        header: {
          text: 'KE No.',
        },
        name: 'ke_no',
        fieldName: 'ke_no',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000452'), // 순도 및 함량
        },
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000451'), // 제조구분
        },
        name: 'make_clsf',
        fieldName: 'make_clsf',
        width: '140',
      },
      {
        header: {
          text: this.$t('LB00000453'), // 성분정보없음
        },
        name: 'no_sbst_yn',
        fieldName: 'no_sbst_yn',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000454'), // 구매자 or 담당자
        },
        name: 'user_nm',
        fieldName: 'user_nm',
        width: '100',
      },
    ]

    const headergroup2 = [
      'mtrl_no', // 자재코드
      {
        name: 'companyGroup',
        direction: 'horizontal',
        items: ['cas_no', 'sbst_nm', 'sbst_rati', 'make_clsf', 'no_sbst_yn'],
        header: {
          text: this.$t('LB00000485'), // 구성성분 정보
        },
      },
      'user_nm', // 구매자 or 담당자
    ]

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000017.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgridProd')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setColumnLayout(headergroup)

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 60
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgridCnst')
    gridView2.setDataSource(dataProvider2)
    gridView2.setColumns(columns2)
    gridView2.setFooters({ visible: false })
    gridView2.setColumnLayout(headergroup2)

    gridView2.setStateBar({ visible: false })
    gridView2.setCheckBar({ visible: false })
    gridView2.editOptions.editable = false
    gridView2.displayOptions.selectionStyle = 'block'

    gridView2.header.height = 60
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'

    gridView2.setColumnProperty('id', 'visible', false)
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
      const rows = await this.$axios.$get(`/api/v1/ch/csttstat/stat-resch-list`, { params: this.schData })

      dataProvider.setRows(rows.tab1)
      gridView.refresh()
      gridView.setTopItem(0)
      this.prodTotalcnt = dataProvider.getRowCount()

      dataProvider2.setRows(rows.tab2)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.cnstTotalcnt = dataProvider2.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData.wkpl_id = null
      this.schData.mtrl_clsf_cd = null
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')

      this.select()
    },
    refresh() {
      this.select()
    },
    tabChg(val) {
      if (val === '1') {
        gridView.refresh()
      } else {
        gridView2.refresh()
      }
    },
    excelExport(tab) {
      if (tab === 'prod') {
        const excelType = false
        const showProgress = false
        const indicator = 'visible'
        const header = 'visible'
        const footer = 'hidden'
        const fileName = '제품정보_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
      } else {
        const excelType = false
        const showProgress = false
        const indicator = 'visible'
        const header = 'visible'
        const footer = 'hidden'
        const fileName = '구성성분정보_' + new Date().toISOString().substr(0, 10) + '.xlsx'

        gridView2.exportGrid({
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
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
