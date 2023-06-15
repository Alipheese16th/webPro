<template>
  <div>
    <div class="location">
      <!--MRO 구매(KO)--><!--화학물질구매 알괄품의-->
      <span>Home > {{ $t('LB00000145') }} > {{ $t('LB00000605') }}</span>
    </div>
    <div class="work_title">
      <!--화학물질구매 알괄품의-->
      <h2>{{ $t('LB00000605') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.wkpl_id"
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
                <!--품목구분-->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.macl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000006"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--구매품의일(KO)-->
                <div class="label_tit">{{ $t('LB00000141') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchdatas.sDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                      @keypress.enter="select()"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="searchdatas.sDate" no-title :max="searchdatas.eDate" @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchdatas.eDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                      @keypress.enter="select()"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="searchdatas.eDate" no-title :min="searchdatas.sDate" @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구매품의번호(KO)-->
                <div class="label_tit">{{ $t('LB00000137') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.srch_mro_pchs_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="20"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--자재명-->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.mtrl_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="99"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--구매 유형-->
                <div class="label_tit">{{ $t('LB00000448') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.multi_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000020"
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
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="reset()">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title" style="display: flex">
        <!-- 화학물질구매품의 목록 -->
        <h3>{{ $t('LB00000606') }}</h3>
        <v-spacer></v-spacer>
        <!--구매항목 및 주문수량 수정이 필요할 경우에는 해당 품목명을 클릭하여, 내용수정→저장→결재담기 순으로 다시 진행 부탁드립니다.-->
        <div class="label_info ml-1">{{ $t('LB00000615') }}</div>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--일괄결재-->
          <v-btn color="primary" depressed height="30" @click="aprvPkg()">{{ $t('LB00000607') }}</v-btn>
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
    title: 'MRO 구매 품의',
    key(route) {
      return `/mro/mropchs/${route.params.catalog}`
    },
  },
  data() {
    return {
      codeid: ['CO00000006', 'CH00000006', 'CH00000015', 'CH00000020'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CH00000006: [{ cd: '', cd_nm: '' }],
        CH00000015: [{ cd: '', cd_nm: '' }],
        CH00000020: [{ cd: '', cd_nm: '' }],
      },
      totalcnt: 0,
      searchdatas: {
        wkpl_id: '',
        macl_cd: '',
        pchs_prog_stt_cd: '',
        srch_mro_pchs_no: '',
        mtrl_nm: '',
        dept_nm: '',
        emp_nm: '',
        pchs_emp_no: '',
        sDate: '',
        eDate: '',
        mro_pchs_no: '',
        multi_yn: '',
      },
      menu1: false,
      menu2: false,
      usrinfo: {},
      multi_yn: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000605')
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

    this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
    this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
    this.usrinfo = this.$store.getters['auth/getUser']
    this.usrinfo.roles.forEach((i) => {})
    // eslint-disable-next-line no-console
    console.log(this.usrinfo)

    // 자동선택
    this.searchdatas.wkpl_id = this.usrinfo.wkpl_id
    this.searchdatas.pchs_prog_stt_cd = '15' // 결재담기 상태만...
    this.searchdatas.pchs_emp_no = this.usrinfo.emp_no

    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas.wkpl_id = this.$route.params.schData.wkpl_id
      this.searchdatas.macl_cd = this.$route.params.schData.macl_cd
      this.searchdatas.pchs_prog_stt_cd = '15' // 결재담기 상태만...
      this.searchdatas.srch_mro_pchs_no = this.$route.params.schData.srch_mro_pchs_no
      this.searchdatas.mtrl_nm = this.$route.params.schData.mtrl_nm
      this.searchdatas.dept_nm = this.$route.params.schData.dept_nm
      this.searchdatas.emp_nm = this.$route.params.schData.emp_nm
      this.searchdatas.pchs_emp_no = this.usrinfo.emp_no
      this.searchdatas.sDate = this.$route.params.schData.sDate
      this.searchdatas.eDate = this.$route.params.schData.eDate
      this.searchdatas.multi_yn = this.$route.params.schData.multi_yn
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes.CO00000006 = res.CO00000006
      this.codes.CH00000015 = res.CH00000015
      this.codes.CH00000020 = res.CH00000020
      this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000015.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000020.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000006 = []
      res.CH00000006.forEach((i) => {
        if (i.up_cd === '2') {
          this.codes.CH00000006.push(i)
        }
      })
      this.codes.CH00000006.unshift({ cd: '', cd_nm: 'ALL' })
    })
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000137'), // 구매품의번호(KO)
        name: 'mro_pchs_no',
        fieldName: 'mro_pchs_no',
        width: '100',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000023'), // 품목구분
        name: 'macl_cd_nm',
        fieldName: 'macl_cd_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000160'), // 품목코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
      },
      {
        header: this.$t('LB00000159'), // 품목명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000158'), // 구매처명
        name: 'vndr_nm',
        fieldName: 'vndr_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000157'), // 수입여부
        name: 'impt_yn',
        fieldName: 'impt_yn',
        width: '50',
      },
      {
        header: this.$t('LB00000528'), // 단위중량(KO)
        name: 'pchs_req_unit_weght_nm',
        fieldName: 'pchs_req_unit_weght_nm',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000154'), // 주문수량(KO)
        name: 'pchs_qty',
        fieldName: 'pchs_qty',
        width: '100',
        editor: {
          type: 'number',
          maxLength: 10,
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          // 구성물질 함량정보 스타일 적용
          if (dataCell.value === '' || dataCell.value === null || dataCell.value === undefined || dataCell.value === 0 || dataCell.value === '0') {
            ret.styleName = 'field-empty'
          } else {
            ret.styleName = 'right'
          }

          return ret
        },
      },
      {
        header: this.$t('LB00000600'), // 총구매량(KO)
        name: 'pchs_req_total_weght',
        fieldName: 'pchs_req_total_weght',
        width: '100',
        styleName: 'right',
      },
    ]
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true, width: 50 })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.onCellClicked = function (grid, clickData) {
      const value = dataProvider.getValue(clickData.dataRow, 'mro_pchs_no')
      this.searchdatas.mro_pchs_no = value
      const sttcd = dataProvider.getValue(clickData.dataRow, 'pchs_prog_stt_cd')
      const deptcd = dataProvider.getValue(clickData.dataRow, 'pchs_dept_cd')
      const empno = dataProvider.getValue(clickData.dataRow, 'pchs_emp_no')

      // 상세 or 수정 화면으로 이동
      if (clickData.cellType === 'data' && clickData.column === 'mtrl_nm') {
        // 다건
        if (dataProvider.getValue(clickData.dataRow, 'multi_yn') === 'Y') {
          // 결재중,완료 건이면 조회페이지로
          if (sttcd === '20' || sttcd === '30') {
            this.$router.push({
              name: 'mro-mropchsmultilup-mropchsmultilup',
              params: {
                mropchsmultilup: this.searchdatas.mro_pchs_no,
                schData: {
                  wkpl_id: this.searchdatas.wkpl_id,
                  macl_cd: this.searchdatas.macl_cd,
                  pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                  srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                  mtrl_nm: this.searchdatas.mtrl_nm,
                  dept_nm: this.searchdatas.dept_nm,
                  emp_nm: this.searchdatas.emp_nm,
                  pchs_emp_no: this.searchdatas.pchs_emp_no,
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchspkglist',
              },
            })
          } else if (deptcd === this.usrinfo.dept_cd || empno === this.usrinfo.emp_no) {
            this.$router.push({
              name: 'mro-mropchsmultiedt-mropchsmultiedt',
              params: {
                mropchsmultiedt: this.searchdatas.mro_pchs_no,
                schData: {
                  wkpl_id: this.searchdatas.wkpl_id,
                  macl_cd: this.searchdatas.macl_cd,
                  pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                  srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                  mtrl_nm: this.searchdatas.mtrl_nm,
                  dept_nm: this.searchdatas.dept_nm,
                  emp_nm: this.searchdatas.emp_nm,
                  pchs_emp_no: this.searchdatas.pchs_emp_no,
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchspkglist',
              },
            })
            // 같은 부서가 아니라면 조회페이지로
          } else {
            this.$router.push({
              name: 'mro-mropchsmultilup-mropchsmultilup',
              params: {
                mropchsmultilup: this.searchdatas.mro_pchs_no,
                schData: {
                  wkpl_id: this.searchdatas.wkpl_id,
                  macl_cd: this.searchdatas.macl_cd,
                  pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                  srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                  mtrl_nm: this.searchdatas.mtrl_nm,
                  dept_nm: this.searchdatas.dept_nm,
                  emp_nm: this.searchdatas.emp_nm,
                  pchs_emp_no: this.searchdatas.pchs_emp_no,
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchspkglist',
              },
            })
          }
          // 단건
          // 결재중,완료 건이면 조회페이지로
        } else if (sttcd === '20' || sttcd === '30') {
          this.$router.push({
            name: 'mro-mropchslup-mropchslup',
            params: {
              mropchslup: this.searchdatas.mro_pchs_no,
              schData: {
                wkpl_id: this.searchdatas.wkpl_id,
                macl_cd: this.searchdatas.macl_cd,
                pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                mtrl_nm: this.searchdatas.mtrl_nm,
                dept_nm: this.searchdatas.dept_nm,
                emp_nm: this.searchdatas.emp_nm,
                pchs_emp_no: this.searchdatas.pchs_emp_no,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchspkglist',
            },
          })
          // 같은 부서면서 상태값이 수정가능이라면 수정페이지로
        } else if (deptcd === this.usrinfo.dept_cd || empno === this.usrinfo.emp_no) {
          this.$router.push({
            name: 'mro-mropchsedt-mropchsedt',
            params: {
              mropchsedt: this.searchdatas.mro_pchs_no,
              schData: {
                wkpl_id: this.searchdatas.wkpl_id,
                macl_cd: this.searchdatas.macl_cd,
                pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                mtrl_nm: this.searchdatas.mtrl_nm,
                dept_nm: this.searchdatas.dept_nm,
                emp_nm: this.searchdatas.emp_nm,
                pchs_emp_no: this.searchdatas.pchs_emp_no,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchspkglist',
            },
          })
          // 같은 부서가 아니라면 조회페이지로
        } else {
          this.$router.push({
            name: 'mro-mropchslup-mropchslup',
            params: {
              mropchslup: this.searchdatas.mro_pchs_no,
              schData: {
                wkpl_id: this.searchdatas.wkpl_id,
                macl_cd: this.searchdatas.macl_cd,
                pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
                srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
                mtrl_nm: this.searchdatas.mtrl_nm,
                dept_nm: this.searchdatas.dept_nm,
                emp_nm: this.searchdatas.emp_nm,
                pchs_emp_no: this.searchdatas.pchs_emp_no,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchspkglist',
            },
          })
        }
      }
    }.bind(this)
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
    async select() {
      // eslint-disable-next-line no-console
      await console.log(this.searchdatas)
      const rows = await this.$axios.$get(`/api/v1/ch/mro/mropchs-pkg-list`, { params: this.searchdatas })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.searchdatas.wkpl_id = ''
      this.searchdatas.macl_cd = ''
      this.searchdatas.srch_mro_pchs_no = ''
      this.searchdatas.mtrl_nm = ''
      this.searchdatas.dept_nm = ''
      this.searchdatas.emp_nm = ''
      this.searchdatas.multi_yn = ''
      const user = this.$store.getters['auth/getUser']

      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      eDate = this.getTzDateObj(user.time_zone)
      sDate = this.getTzDateObj(user.time_zone)
      sDate.setDate(sDate.getDate() - 30)

      this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
      this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
    },
    singleOpen() {
      this.$router.push({
        name: 'mro-mropchsedt-mropchsedt',
        params: {
          schData: {
            wkpl_id: this.searchdatas.wkpl_id,
            macl_cd: this.searchdatas.macl_cd,
            pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
            srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
            mtrl_nm: this.searchdatas.mtrl_nm,
            dept_nm: this.searchdatas.dept_nm,
            emp_nm: this.searchdatas.emp_nm,
            pchs_emp_no: this.searchdatas.pchs_emp_no,
            sDate: this.searchdatas.sDate,
            eDate: this.searchdatas.eDate,
            multi_yn: this.searchdatas.multi_yn,
          },
        },
      })
    },
    multiOpen() {
      this.$router.push({
        name: 'mro-mropchsmultiedt-mropchsmultiedt',
        params: {
          schData: {
            wkpl_id: this.searchdatas.wkpl_id,
            macl_cd: this.searchdatas.macl_cd,
            pchs_prog_stt_cd: this.searchdatas.pchs_prog_stt_cd,
            srch_mro_pchs_no: this.searchdatas.srch_mro_pchs_no,
            mtrl_nm: this.searchdatas.mtrl_nm,
            dept_nm: this.searchdatas.dept_nm,
            emp_nm: this.searchdatas.emp_nm,
            pchs_emp_no: this.searchdatas.pchs_emp_no,
            sDate: this.searchdatas.sDate,
            eDate: this.searchdatas.eDate,
            multi_yn: this.searchdatas.multi_yn,
          },
        },
      })
    },
    async validation(chkRow) {
      // const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      // eslint-disable-next-line no-console
      // await console.log(chkRow.length)
      if (chkRow.length === 0) {
        return true
      } else {
        for (let i = 0; i < chkRow.length; i++) {
          if ((await dataProvider.getValue(chkRow[i], 'pchs_prog_stt_cd')) !== '15') {
            this.$toast.show('진행상태가 작성중인 항목만 선택해주세요.', { className: 'toast_error' }) // 주문수량을 입력해 주세요.(KO)
            gridView.setCurrent({ dataRow: chkRow[i], fieldName: 'pchs_prog_stt_cd' })
            return false
          } else if (i + 1 === chkRow.length) {
            return true
          }
        }
      }
    },
    async aprvPkg() {
      gridView.commit()
      const rows = gridView.getCheckedRows(true)
      if (rows.length === 0) {
        this.$toast.show(this.$t('MS00000245'), { className: 'toast_error' }) // 일괄결재 할 품목을 선택해주세요.
        return false
      }
      if (await this.validation(rows)) {
        this.savenext = await this.$confirm(this.$t('MS00000246')) // 선택한 품목으로 결재하시겠습니까?
        if (await this.savenext) {
          const selectRow = [] //  저장될 배열 비우기
          for (let i = 0; i < rows.length; i++) {
            const temp = {}
            temp.mro_pchs_no = dataProvider.getValue(rows[i], 'mro_pchs_no')
            temp.wkpl_nm = dataProvider.getValue(rows[i], 'wkpl_nm')
            temp.macl_cd_nm = dataProvider.getValue(rows[i], 'macl_cd_nm')
            temp.mtrl_no = dataProvider.getValue(rows[i], 'mtrl_no')
            temp.mtrl_nm = dataProvider.getValue(rows[i], 'mtrl_nm')
            temp.vndr_nm = dataProvider.getValue(rows[i], 'vndr_nm')
            temp.impt_yn = dataProvider.getValue(rows[i], 'impt_yn')
            temp.pchs_req_unit_weght_nm = dataProvider.getValue(rows[i], 'pchs_req_unit_weght_nm')
            temp.pchs_qty = dataProvider.getValue(rows[i], 'pchs_qty')
            temp.pchs_req_total_weght = dataProvider.getValue(rows[i], 'pchs_req_total_weght')
            temp.multi_yn = dataProvider.getValue(rows[i], 'multi_yn')
            selectRow.push(temp)
          }
          // 추후 결제 로직 생기면 이곳에 결제 처리
          // 선택한 로우 정보 담기
          const saveparams = {}
          saveparams.mroSaveData = selectRow
          this.$axios.post('/api/v1/ch/mro/mropchs-pkg-aprv', saveparams).then((res) => {
            const config = require('../../../nuxt.config.js')
            console.log(res) // eslint-disable-line no-console
            // his.$toast.show(this.$t('MS00000093')) // 결재되었습니다.(KO) 추후 //결재 팝업 화면으로 변경
            this.aprv_yn = 'FIN'
            if (res.data.Success === 'Success') {
              const popupWidth = 1575
              const popupHeight = window.screen.height - 300
              const popupX = window.screen.width / 2 - popupWidth / 2
              const popupY = window.screen.height / 2 - popupHeight / 2
              const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
              const aprvno = '&dataurl=' + res.data.aprvNo
              const empno = '&typ=' + res.data.empno
              let url = config.default.approvalUrl2 + aprvno + empno
              // 연구소라면
              if (this.usrinfo.wkpl_id === 'S02') {
                url = config.default.approvalUrl3 + aprvno + empno
              }
              window.open(url, 'mropchs-aprv', options)
              // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
              this.goLup()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      }
    },
    async createPush(lowstatus) {
      // 새로 생성된 row push
      if ((await dataProvider.getRowStateCount('created')) > 0) {
        lowstatus.created.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'C')
          this.sbstlist.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async updatePush(lowstatus) {
      // 수정된 row push
      if ((await dataProvider.getRowStateCount('updated')) > 0) {
        lowstatus.updated.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'U')
          this.sbstlist.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'mro_pchs_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'wkpl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd_nm',
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
    fieldName: 'vndr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'impt_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght_nm',
    dataType: ValueType.TEXT,
    valueCallback(prod, dataRow, fieldName, fieldNames, values) {
      const pchsReqUnitWeght = values[fieldNames.indexOf('pchs_req_unit_weght')]
      if (isNaN(pchsReqUnitWeght)) {
        return ''
      } else {
        const pchsReqUnitNm = values[fieldNames.indexOf('pchs_req_unit_nm')]
        return pchsReqUnitWeght + '' + pchsReqUnitNm
      }
    },
  },
  {
    fieldName: 'pchs_qty',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_total_weght',
    dataType: ValueType.TEXT,
    // valueExpression: 'values["pchs_req_unit_weght"] * values["pchs_qty"]',
    valueCallback(prod, dataRow, fieldName, fieldNames, values) {
      const pchsReqUnitWeght = values[fieldNames.indexOf('pchs_req_unit_weght')]
      const pchsQty = values[fieldNames.indexOf('pchs_qty')]
      if (isNaN(pchsReqUnitWeght) || isNaN(pchsQty)) {
        return ''
      } else {
        const pchsReqUnitNm = values[fieldNames.indexOf('pchs_req_unit_nm')]
        return pchsReqUnitWeght * pchsQty + '' + pchsReqUnitNm
      }
    },
  },
  {
    fieldName: 'multi_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_prog_stt_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_dept_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_emp_no',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
