<template>
  <div>
    <div class="location">
      <!--규제대응--><!--규제 알람 내용-->
      <span>Home > {{ $t('LB00000540') }} > {{ $t('LB00000548') }}</span>
    </div>
    <div class="work_title">
      <!--규제 알람 내용-->
      <h2>{{ $t('LB00000548') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--규제 알람 내용-->
        <h3>{{ $t('LB00000548') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="2">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="10">
                <div class="label_con">{{ tmpData.alrmTtl }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!--알람유형-->
                <div class="label_tit">{{ $t('LB00000545') }}</div>
              </v-col>
              <v-col cols="4">
                <div class="label_con">{{ tmpData.alrmTypeNm }}</div>
              </v-col>
              <v-col cols="2">
                <!--발생일-->
                <div class="label_tit">{{ $t('LB00000544') }}</div>
              </v-col>
              <v-col cols="4">
                <div class="label_con">{{ tmpData.crtDa }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 알람 대상 자재 목록 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000549') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀-->
          <v-btn color="primary" depressed height="30" @click="excelDown()">{{ $t('LB00000572') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
const date = new Date()

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '규제 알람 상세',
    key(route) {
      return `/rgltact/rgltactalrmlist/${route.params.catalog}`
    },
  },
  props: {},
  data() {
    return {
      usrinfo: {},
      totalcnt: 0,
      searchdatas: {},
      menu: false,
      tmpSchData: {
        alrm_ttl: '',
        alrm_cntn: '',
        alrm_type_cd: '',
        sDate: '',
        eDate: '',
      },
      tmpData: {
        alrmTtl: '',
        alrmTypeNm: '',
        crtDa: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000548') + ' ' + this.$t('LB00000597')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    // 파라미터 조회 - 파라미터가 존재할 경우
    if (this.$route.params.rgltactalrmlup !== null && this.$route.params.rgltactalrmlup !== undefined) {
      this.searchdatas.rgltactalrmlup = this.$route.params.rgltactalrmlup
      this.selectDtl()
    }
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        alrm_ttl: this.searchdatas.alrm_ttl,
        alrm_cntn: this.searchdatas.alrm_cntn,
        alrm_type_cd: this.searchdatas.alrm_type_cd,
        sDate: date.toISOString().substr(0, 10),
        eDate: new Date().toISOString().substr(0, 10),
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'mtrl_clsf_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'macl_nm',
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
        fieldName: 'anul_use_tot',
        dataType: ValueType.NUMBER,
      },
      {
        fieldName: 'wh_da',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000022'), // 자재구분
        name: 'mtrl_clsf_nm',
        fieldName: 'mtrl_clsf_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000023'), // 품목구분
        name: 'macl_nm',
        fieldName: 'macl_nm',
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
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000573'), // 사용량
        name: 'anul_use_tot',
        fieldName: 'anul_use_tot',
        width: '100',
      },
      {
        header: this.$t('LB00000550'), // 최근 입고일
        name: 'wh_da',
        fieldName: 'wh_da',
        width: '100',
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

    gridView.formatOptions.numberFormat = null

    gridView.setColumnProperty('id', 'visible', false)
  },
  methods: {
    excelDown() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '알람_대상_자재_목록_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
    chkFile() {
      this.tempData.fileModify = true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async selectDtl() {
      // eslint-disable-next-line no-console
      console.log(this.searchdatas)
      const rows = await this.$axios.$get(`/api/v1/ch/rgltact/alrm/${this.searchdatas.rgltactalrmlup}`)

      this.tmpData.alrmTtl = rows[0].alrm_ttl
      this.tmpData.alrmTypeNm = rows[0].alrm_type_nm
      this.tmpData.crtDa = rows[0].crt_da

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    goList() {
      this.$router.push({
        name: `rgltact-rgltactalrmlist`,
        params: {
          schData: {
            alrm_ttl: this.tmpSchData.alrm_ttl,
            alrm_cntn: this.tmpSchData.alrm_cntn,
            alrm_type_cd: this.tmpSchData.alrm_type_cd,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
  },
}
</script>
<style lang="scss" scoped></style>
