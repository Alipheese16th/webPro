<template>
  <div>
    <div class="location">
      <!--MRO 구매(KO)--><!--화학물질구매품의-->
      <span>Home > {{ $t('LB00000145') }} > {{ $t('LB00000520') }}</span>
    </div>
    <div class="work_title">
      <!--화학물질구매품의-->
      <h2>{{ $t('LB00000520') }}</h2>
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
                <!--진행상태-->
                <div class="label_tit">{{ $t('LB00000143') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.pchs_prog_stt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000015"
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
            <v-row>
              <v-col cols="1">
                <!--구매부서-->
                <div class="label_tit">{{ $t('LB00000529') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.dept_nm"
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
                <!--구매자(KO)-->
                <div class="label_tit">{{ $t('LB00000135') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.emp_nm"
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
        <h3>{{ $t('LB00000522') }}</h3>
        <v-spacer></v-spacer>
        <!--※ 대상: 부서별 직접 구매하는 화학물질에 대해서 품의 작성(IMK로 작성할 경우는 별도 메일 안내)-->
        <div class="label_info ml-1">{{ $t('LB00000521') }}</div>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--최초구매(KO)-->
          <v-btn color="primary" depressed height="30" @click="singleOpen()">{{ $t('LB00000517') }}</v-btn>
          <!--재구매(KO)-->
          <v-btn color="primary" depressed height="30" @click="multiOpen()">{{ $t('LB00000138') }}</v-btn>
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
      return this.$t('LB00000520')
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

    // 사용자 사업장 자동선택
    this.searchdatas.wkpl_id = this.usrinfo.wkpl_id

    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas.wkpl_id = this.$route.params.schData.wkpl_id
      this.searchdatas.macl_cd = this.$route.params.schData.macl_cd
      this.searchdatas.pchs_prog_stt_cd = this.$route.params.schData.pchs_prog_stt_cd
      this.searchdatas.srch_mro_pchs_no = this.$route.params.schData.srch_mro_pchs_no
      this.searchdatas.mtrl_nm = this.$route.params.schData.mtrl_nm
      this.searchdatas.dept_nm = this.$route.params.schData.dept_nm
      this.searchdatas.emp_nm = this.$route.params.schData.emp_nm
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
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000136'), // 구매품의일(KO)
        name: 'aprv_crt_dt',
        fieldName: 'aprv_crt_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000529'), // 구매부서
        name: 'upt_dept_nm',
        fieldName: 'upt_dept_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000135'), // 구매자(KO)
        name: 'upt_emp_nm',
        fieldName: 'upt_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000143'), // 진행상태
        name: 'pchs_prog_stt_cd_nm',
        fieldName: 'pchs_prog_stt_cd_nm',
        width: '100',
      },
    ]
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
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchslist',
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
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchslist',
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
                  sDate: this.searchdatas.sDate,
                  eDate: this.searchdatas.eDate,
                  multi_yn: this.searchdatas.multi_yn,
                },
                goData: 'mro-mropchslist',
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
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchslist',
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
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchslist',
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
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                multi_yn: this.searchdatas.multi_yn,
              },
              goData: 'mro-mropchslist',
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
      const rows = await this.$axios.$get(`/api/v1/ch/mro/mropchs-list`, { params: this.searchdatas })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.searchdatas.wkpl_id = ''
      this.searchdatas.macl_cd = ''
      this.searchdatas.pchs_prog_stt_cd = ''
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
            sDate: this.searchdatas.sDate,
            eDate: this.searchdatas.eDate,
            multi_yn: this.searchdatas.multi_yn,
          },
          goData: 'mro-mropchslist',
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
            sDate: this.searchdatas.sDate,
            eDate: this.searchdatas.eDate,
            multi_yn: this.searchdatas.multi_yn,
          },
          goData: 'mro-mropchslist',
        },
      })
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
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_dt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_dept_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_prog_stt_cd_nm',
    dataType: ValueType.TEXT,
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
  {
    fieldName: 'aprv_crt_dt',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
