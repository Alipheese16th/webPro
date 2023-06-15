<template>
  <div>
    <div class="location">
      <!-- Home > 규제 검토 > 규제검토 요청 -->
      <span>Home > {{ $t('LB00000336') }} > {{ $t('LB00000337') }}</span>
    </div>
    <!-- 규제검토 요청 -->
    <div class="work_title">
      <h2>{{ $t('LB00000337') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 사업장  -->
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
                  ref="mtrl_no"
                  v-model="schData.mtrl_no"
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
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="mtrl_nm"
                  v-model="schData.mtrl_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 진행상태 -->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.rglt_chk_req_prog_stt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000012"
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
                <!-- 검토요청부서 -->
                <div class="label_tit">{{ $t('LB00000616') }}</div>
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
                <!-- 검토요청자 -->
                <div class="label_tit">{{ $t('LB00000338') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field v-model="schData.username" outlined single-line :hide-details="true" dense height="30" class="icon_sch" readonly @click="popOpen"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 검토요청일 -->
                <div class="label_tit">{{ $t('LB00000339') }}</div>
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
            <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제검토요청 목록 -->
      <div class="sub_title" style="display: flex">
        <h3>{{ $t('LB00000340') }}</h3>
        <v-spacer></v-spacer>
        <!--붉은색 자재명은 MSDS 제출 대상 자재입니다.-->
        <div class="label_info ml-1">{{ $t('LB00000619') }}</div>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 그리드 버튼 영역 -->
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="pop" mkey1="rgltchkreqlistuserslct1" mkey2="rgltchkreqlistuserslct2" @close="popclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'

let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '규제검토-규제검토요청 목록',
    key(route) {
      return `/rgltchk/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
      totalcnt: 0,
      codeid: ['CO00000006', 'CO00000017', 'CH00000006', 'CH00000012'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000017: [{ cd: '', cd_nm: '' }],
        CH00000006: [{ cd: '', cd_nm: '' }],
        CH00000012: [{ cd: '', cd_nm: '' }],
        F_CH00000006: [{ cd: '', cd_nm: 'ALL' }],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000337')
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
    sDate.setDate(sDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    this.frmData.username = this.$store.getters['auth/getUsername']
    this.frmData.userempno = user.emp_no
    this.frmData.sessDeptCd = user.dept_cd
    console.log(user) // eslint-disable-line no-console
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    }
  },
  mounted() {
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
        fieldName: 'mtrl_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_clsf_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'macl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'macl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_id',
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
        fieldName: 'mtrl_reg_da',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_reg_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_reg_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_reg_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_da',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tat',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_prog_stt_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_prog_stt_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chk_req_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_kind_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_req_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_nm',
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

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000151'), // 요청번호
        name: 'rglt_chk_no',
        fieldName: 'rglt_chk_no',
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
        header: this.$t('LB00000343'), // 자재 등록일
        name: 'mtrl_reg_dt',
        fieldName: 'mtrl_reg_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000616'), // 규제검토 요청부서
        name: 'dept_nm',
        fieldName: 'dept_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000344'), // 규제검토 요청자
        name: 'rglt_chk_req_emp_nm',
        fieldName: 'rglt_chk_req_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000345'), // 규제검토 요청일
        name: 'rglt_chk_req_dt',
        fieldName: 'rglt_chk_req_dt',
        width: '100',
      },
      {
        header: 'TAT',
        name: 'tat',
        fieldName: 'tat',
        width: '50',
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'rglt_chk_req_prog_stt_nm',
        fieldName: 'rglt_chk_req_prog_stt_nm',
        width: '100',
      },
    ]
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000017.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CH00000006.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CH00000012.unshift({ cd: '', cd_nm: 'ALL' })
        if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
          this.codInit(this.schData.mtrl_clsf_cd)
        } else {
          this.codChg('')
        }
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

    // TAT 값 3 이상인경우 ROW색 변경
    gridView.setRowStyleCallback(function (grid, item, fixed) {
      const stt = grid.getValue(item.index, 'rglt_chk_req_prog_stt_cd')
      if (stt !== '30') {
        const tat = grid.getValue(item.index, 'tat')
        if (tat >= 3) {
          return 'row-highlight'
        }
      }
    })

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()

      if (current.column === 'mtrl_nm') {
        const mtrlNo = gridView.getValue(index.itemIndex, 'mtrl_no')
        const rgltChkNo = gridView.getValue(index.itemIndex, 'rglt_chk_no')
        const chkReqClsfCd = gridView.getValue(index.itemIndex, 'chk_req_clsf_cd')
        const mtrlKindCd = gridView.getValue(index.itemIndex, 'mtrl_kind_cd')
        const sttCd = gridView.getValue(index.itemIndex, 'rglt_chk_req_prog_stt_cd')
        const reqDeptCd = gridView.getValue(index.itemIndex, 'dept_cd')

        // 자재가 향료일 경우
        if (mtrlKindCd === '2') {
          // 진행상태가 검토요청인 경우
          if (sttCd === '30') {
            this.$router.push({
              name: `rgltchk-rgltchkreqmtrllup-rgltchkreqmtrllup`,
              params: {
                rgltchkreqmtrllup: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
            // 진행상태가 검토요청이 아니고 검토요청자의 부서와 동일부서 사람인경우
          } else if (sttCd !== '30' && reqDeptCd === this.frmData.sessDeptCd) {
            this.$router.push({
              name: `rgltchk-rgltchkreqmtrledt-rgltchkreqmtrledt`,
              params: {
                rgltchkreqmtrledt: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          } else {
            this.$router.push({
              name: `rgltchk-rgltchkreqmtrllup-rgltchkreqmtrllup`,
              params: {
                rgltchkreqmtrllup: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          }
          // 자재가 IMK인 경우
        } else if (mtrlKindCd === '4') {
          // 진행상태가 검토요청인 경우
          if (sttCd === '30' || sttCd === '90') {
            this.$router.push({
              name: `rgltchk-rgltchkreqimklup-rgltchkreqimklup`,
              params: {
                rgltchkreqimklup: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
            // 진행상태가 검토요청이 아니고 검토요청자의 부서와 동일부서 사람인경우
          } else if (sttCd !== '30' && reqDeptCd === this.frmData.sessDeptCd) {
            this.$router.push({
              name: `rgltchk-rgltchkreqimkedt-rgltchkreqimkedt`,
              params: {
                rgltchkreqimkedt: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          } else {
            this.$router.push({
              name: `rgltchk-rgltchkreqimklup-rgltchkreqimklup`,
              params: {
                rgltchkreqimklup: rgltChkNo,
                schData: {
                  mtrl_no: mtrlNo,
                  rglt_chk_no: rgltChkNo,
                  chk_req_clsf_cd: chkReqClsfCd,
                },
                tmpSchData: {
                  wkpl_id: this.schData.wkpl_id,
                  mtrl_clsf_cd: this.schData.mtrl_clsf_cd,
                  macl_cd: this.schData.macl_cd,
                  mtrl_no: this.schData.mtrl_no,
                  mtrl_nm: this.schData.mtrl_nm,
                  rglt_chk_req_prog_stt_cd: this.schData.rglt_chk_req_prog_stt_cd,
                  rglt_chk_req_emp_no: this.schData.rglt_chk_req_emp_no,
                  userno: this.schData.userno,
                  username: this.schData.username,
                  sDate: this.schData.sDate,
                  eDate: this.schData.eDate,
                },
              },
            })
          }
        }
      }
    }.bind(this)
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
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/ch/rgltchk/rglt-chk-req-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        rglt_chk_req_prog_stt_cd: '',
        userno: '',
        username: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
      this.codChg('')
    },
    refresh() {
      this.select()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      if (!this.isEmpty(this.schData.mtrl_no) && this.schData.mtrl_no.length > 20) {
        this.$toast.show(this.$t('MS00000212'), { className: 'toast_error' }) // 자재코드는 20자 이하로 입력해주세요.
        this.$refs.mtrl_no.focus()
        return false
      }
      if (!this.isEmpty(this.schData.mtrl_nm) && this.schData.mtrl_nm.length > 100) {
        this.$toast.show(this.$t('MS00000211'), { className: 'toast_error' }) // 자재명은 100자 이하로 입력해주세요.
        this.$refs.mtrl_nm.focus()
        return false
      }
      return true
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
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'username', i.emp_nm)
          this.$set(this.schData, 'userno', i.emp_no)
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped>
//css
.rgsample-blue-column {
  color: red;
}
.rgsample-bold-column {
  font-weight: bold;
}
</style>
