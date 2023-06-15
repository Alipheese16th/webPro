<template>
  <div>
    <div class="location">
      <!-- Home > 현황/통계 > 화학물질배출량조사 -->
      <span>Home > {{ $t('LB00000488') }} > {{ $t('LB00000489') }}</span>
    </div>
    <!-- 화학물질배출량조사 -->
    <div class="work_title">
      <h2>{{ $t('LB00000489') }}</h2>
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
    <div class="realgrid_container">
      <!-- 화학물질 배출량조사 목록 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000574') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 그리드 버튼 영역 -->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
        </div>
      </div>
      <div id="realgridMtrl" style="width: 100%; height: 481px"></div>
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
    title: '현황/통계-화학물질배출량조사',
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
      totalcnt: 0,
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
      return this.$t('LB00000489')
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
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'macl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_puse_nm',
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
        fieldName: 'mtrl_use_qty',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_qty',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'group_nm',
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
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
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
          text: this.$t('LB00000025'), // 자재명
        },
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '350',
      },
      {
        header: {
          text: this.$t('LB00000494'), // 용도
        },
        name: 'macl_nm',
        fieldName: 'macl_nm',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000495'), // 세부용도
        },
        name: 'mtrl_puse_nm',
        fieldName: 'mtrl_puse_nm',
        width: '100',
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
          text: 'CAS No.',
        },
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '80',
      },
      {
        header: {
          text: this.$t('LB00000195'), // 함량(%)
        },
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '70',
      },
      {
        header: {
          text: this.$t('LB00000496'), // 자재사용량(kg)
        },
        name: 'mtrl_use_qty',
        fieldName: 'mtrl_use_qty',
        width: '80',
      },
      {
        header: {
          text: this.$t('LB00000491'), // 물질사용량(kg)
        },
        name: 'use_qty',
        fieldName: 'use_qty',
        width: '80',
      },
      /*
      {
        header: {
          text: this.$t('LB00000497'), // 자재사용량(ton)
        },
        name: 'cnst_info_null',
        fieldName: 'upt_dt',
        width: '80',
      },
      {
        header: {
          text: this.$t('LB00000492'), // 물질사용량(ton)
        },
        name: this.$t('LB00000454'),
        fieldName: 'upt_dt',
        width: '80',
      },
      */
      {
        header: {
          text: this.$t('LB00000493'), // 그룹
        },
        name: 'group_nm',
        fieldName: 'group_nm',
        width: '80',
      },
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
    gridView = new GridView('realgridMtrl')
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

    gridView.setColumnProperty('id', 'visible', false)
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
      const rows = await this.$axios.$get(`/api/v1/ch/csttstat/chems-sqtt-resch-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
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
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '화학물질_배출량조사_목록_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
