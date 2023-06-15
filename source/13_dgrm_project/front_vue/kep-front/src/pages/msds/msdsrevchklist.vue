<template>
  <div>
    <div class="location">
      <!-- MSDS 개정검토 -->
      <span>Home > MSDS > {{ $t('LB00000287') }}</span>
    </div>
    <!-- MSDS 개정검토 -->
    <div class="work_title">
      <h2>{{ $t('LB00000287') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
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
                <!-- 자재코드 -->
                <div class="label_tit">{{ $t('LB00000024') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.mtrl_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="19"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.mtrl_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 진행상태 -->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.rev_prog_stt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000010"
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
                <!-- 요청부서 -->
                <div class="label_tit">{{ $t('LB00000617') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="schData.dept_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 요청자 -->
                <div class="label_tit">{{ $t('LB00000150') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.req_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 요청일 -->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
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
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="schReset()">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- MSDS 개정검토목록 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000290') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 건 -->
          {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
          <!--붉은색 자재명은 MSDS 제출 대상 자재입니다.-->
          <div class="label_info mrL16">{{ $t('LB00000619') }}</div>
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
    title: 'MSDS-MSDS개정검토',
    key(route) {
      return `/msds/msdsChkRevReq/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      schData: {},
      frmData: {
        user: {
          dept_cd: '',
        },
      },
      menu1: false,
      menu2: false,
      size: 0,
      codeid: ['CO00000006', 'CO00000017', 'CH00000006', 'CH00000010'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000017: [{ cd: '', cd_nm: '' }],
        CH00000006: [{ cd: '', cd_nm: '' }],
        CH00000010: [{ cd: '', cd_nm: '' }],
        F_CH00000006: [{ cd: '', cd_nm: 'ALL' }],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000287')
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
    this.frmData.user = this.$store.getters['auth/getUser']
  },
  mounted() {
    const columns = [
      {
        header: this.$t('LB00000275'), // 개정요청번호
        name: 'msds_rev_no',
        fieldName: 'msds_rev_no',
        width: '100',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
      },
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
        styleName: 'link left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const mtrlClsfCd = grid.getValue(dataCell.index.itemIndex, 'mtrl_clsf_cd')
          const impYn = grid.getValue(dataCell.index.itemIndex, 'imp_yn')
          const sbmsTgtYn = grid.getValue(dataCell.index.itemIndex, 'sbms_tgt_yn')
          // eslint-disable-next-line
          if (mtrlClsfCd === '1' && impYn === 'Y' && sbmsTgtYn === 'Y') {
            ret.styleName = 'link2 left'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000149'), // 요청일
        name: 'req_da',
        fieldName: 'req_da',
        width: '100',
      },
      {
        header: this.$t('LB00000617'), // 요청부서
        name: 'dept_nm',
        fieldName: 'dept_nm',
        width: '50',
      },
      {
        header: this.$t('LB00000150'), // 요청자
        name: 'emp_nm',
        fieldName: 'emp_nm',
        width: '50',
      },
      {
        header: this.$t('LB00000284'), // 검토일
        name: 'rev_chk_dt',
        fieldName: 'rev_chk_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000283'), // 검토자
        name: 'rev_chk_emp_nm',
        fieldName: 'rev_chk_emp_nm',
        width: '50',
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'rev_prog_stt_nm',
        fieldName: 'rev_prog_stt_nm',
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

    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      gridView.commit()
      if (index.itemIndex > -1) this.openDetail(index)
    }.bind(this)
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CO00000017.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000010.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
        this.schData = this.$route.params.schData
        this.codInit(this.schData.mtrl_clsf_cd)
      } else {
        this.codChg('')
      }
      this.select()
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
      const rows = await this.$axios.$get(`/api/v1/ch/msds/msds-chk`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)

      this.size = dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        rev_prog_stt_cd: '',
        req_emp_nm: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
      this.codChg('')
    },
    async openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column !== 'mtrl_nm') return
      const value = gridView.getValue(index.itemIndex, 'msds_rev_no')
      // 현재 위치에서 업데이트 가능 혹은 조회 화면으로 분기해야함.(업무 담당자 그룹내 emp_no 동일 여부, 진행 코드 여부, 사업장 동일 여부)
      const auth = await this.$axios.$get(`/api/v1/ch/msds/auth-revchk`, { params: { msds_rev_no: value, emp_no: this.frmData.user.emp_no } })
      if (auth !== 'READ') {
        // 검토 화면
        this.$router.push({
          name: `msds-msdsrevchkedt-msdsrevchkedt`,
          params: {
            msdsrevchkedt: value,
            schData: {
              wkpl_id: this.schData.wkpl_id,
              mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
              macl_cd: this.schData.macl_cd,
              mtrl_no: this.schData.mtrl_no,
              mtrl_nm: this.schData.mtrl_nm,
              rev_prog_stt_cd: this.schData.rev_prog_stt_cd,
              req_emp_nm: this.schData.req_emp_nm,
              sDate: this.schData.sDate,
              eDate: this.schData.eDate,
            },
          },
        })
      } else {
        // 검토 조회 화면
        this.$router.push({
          name: `msds-msdsrevchklup-msdsrevchklup`,
          params: {
            msdsrevchklup: value,
            schData: {
              wkpl_id: this.schData.wkpl_id,
              mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
              macl_cd: this.schData.macl_cd,
              mtrl_no: this.schData.mtrl_no,
              mtrl_nm: this.schData.mtrl_nm,
              rev_prog_stt_cd: this.schData.rev_prog_stt_cd,
              req_emp_nm: this.schData.req_emp_nm,
              sDate: this.schData.sDate,
              eDate: this.schData.eDate,
            },
          },
        })
      }
    },
    codInit(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000006 = this.codes.CH00000006.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
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
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'msds_rev_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_clsf_cd',
    dataType: ValueType.TEXT,
  },
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
    fieldName: 'req_da',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_chk_dt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_chk_emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rev_prog_stt_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'imp_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbms_tgt_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
