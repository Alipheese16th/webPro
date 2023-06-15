<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 규제 물질 조회 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000429') }}</span>
    </div>
    <!-- 규제 물질 조회 -->
    <div class="work_title">
      <h2>{{ $t('LB00000429') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">CAS No.</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="cas_no"
                  v-model="schData.cas_no"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 물질명 -->
                <div class="label_tit">{{ $t('LB00000196') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="sbst_nm"
                  v-model="schData.sbst_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <!-- 검토분야 
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000271') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.rglt_chk_doma_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000002"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              -->
              <v-col cols="1">
                <!-- 법률 -->
                <div class="label_tit">{{ $t('LB00000027') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.law_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 규제명 -->
                <div class="label_tit">{{ $t('LB00000213') }}</div>
              </v-col>
              <v-col cols="7">
                <v-select
                  v-model="schData.rglt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
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
      <!-- 규제 물질 조회 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000429') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 그리드 버튼 영역 -->
          <!--엑셀다운로드-->
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

export default {
  meta: {
    title: '규제DB-규제물질조회',
  },
  components: {},
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CH00000002', 'CH00000003', 'CO00000015'],
      codes: {
        CH00000002: [{ cd: '', cd_nm: '' }],
        CH00000003: [{ cd: '', cd_nm: '' }],
        CO00000015: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000429')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
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
        fieldName: 'sbst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
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
        fieldName: 'use_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_doma_nm',
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
        fieldName: 'rglt_crtr_rati',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_crtr_cntn',
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
      /* 
      {
        header: this.$t('LB00000307'), // 규제번호
        name: 'rglt_no',
        fieldName: 'rglt_no',
        width: '80',
      },
      
      {
        header: this.$t('LB00000271'), // 검토분야
        name: 'rglt_chk_doma_nm',
        fieldName: 'rglt_chk_doma_nm',
        width: '80',
      },
      */
      {
        header: this.$t('LB00000027'), // 법률
        name: 'law_nm',
        fieldName: 'law_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000213'), // 규제명
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '150',
      },
      /* 
      {
        header: this.$t('LB00000317'), // 물질번호
        name: 'sbst_no',
        fieldName: 'sbst_no',
        width: '100',
      },
      */
      {
        header: {
          text: 'CAS No.',
        },
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000196'), // 물질명
        },
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: {
          text: this.$t('LB00000316'), // 물질명(영문)
        },
        name: 'sbst_en_nm',
        fieldName: 'sbst_en_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: {
          text: this.$t('LB00000049'), // 사용여부
        },
        name: 'use_yn',
        fieldName: 'use_yn',
        width: '70',
      },
      {
        header: {
          text: this.$t('LB00000311'), // 변경자
        },
        name: 'upt_emp_nm',
        fieldName: 'upt_emp_nm',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000312'), // 변경일
        },
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000326'), // 함량기준
        },
        name: 'rglt_crtr_rati',
        fieldName: 'rglt_crtr_rati',
        width: '50',
        styleName: 'right',
      },
      {
        header: {
          text: this.$t('LB00000095'), // 비고
        },
        name: 'rglt_crtr_cntn',
        fieldName: 'rglt_crtr_cntn',
        width: '100',
        styleName: 'left',
      },
    ]
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CH00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CH00000003.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000015.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    // 공통코드가 아닌 데이터를 items형식으로 조회
    // 규제명 목록
    this.$axios
      .$get(`/api/v1/ch/msds/law`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
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

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)
  },
  methods: {
    async select() {
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/rglt-sbst-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData.cas_no = ''
      this.schData.sbst_nm = ''
      // this.schData.rglt_chk_doma_cd = ''
      this.schData.law_cd = ''
      this.schData.rglt_nm = ''
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      if (!this.isEmpty(this.schData.cas_no) && this.schData.cas_no.length > 200) {
        this.$toast.show(this.$t('MS00000215'), { className: 'toast_error' }) // CAS No.은 200자 이하로 입력해주세요.
        this.$refs.cas_no.focus()
        return false
      }
      if (!this.isEmpty(this.schData.sbst_nm) && this.schData.sbst_nm.length > 200) {
        this.$toast.show(this.$t('MS00000216'), { className: 'toast_error' }) // 물질명은 200자 이하로 입력해주세요.
        this.$refs.sbst_nm.focus()
        return false
      }
      if (!this.isEmpty(this.schData.rglt_nm) && this.schData.rglt_nm.length > 200) {
        this.$toast.show(this.$t('MS00000213'), { className: 'toast_error' }) // 규제명은 200자 이하로 입력해주세요.
        this.$refs.rglt_nm.focus()
        return false
      }
      return true
    },
    refresh() {
      this.select()
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '규제물질_조회_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
