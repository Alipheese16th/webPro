<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--공지사항(KO)-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('LB00000116') }}</span>
    </div>
    <div class="work_title">
      <!--공지사항(KO)-->
      <h2>{{ $t('LB00000116') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
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
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="searchdatas.sDate" :max="searchdatas.eDate" no-title @input="menu1 = false"> </v-date-picker>
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
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="searchdatas.eDate" :min="searchdatas.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--업무구분(KO)-->
                <div class="label_tit">{{ $t('LB00000052') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.biz_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--팝업공지 여부(KO)-->
                <div class="label_tit">{{ $t('LB00000112') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.noti_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="noti_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--등록자-->
                <div class="label_tit">{{ $t('LB00000055') }}</div>
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
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
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
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <div>
            <!--등록-->
            <v-btn v-if="searchdatas.userApi" color="primary" depressed height="30" @click="openForm">{{ $t('LB00000111') }}</v-btn>
          </div>
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
    title: '공지사항 목록',
    key(route) {
      return `/bbs/noti/${route.params.catalog}`
    },
  },
  data() {
    return {
      codeid: ['CO00000001'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      nowDate: new Date().toISOString().slice(0, 10),
      totalcnt: 0,
      searchdatas: {
        bbs_type_cd: 'NOTI',
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        noti_yn: '',
        biz_cd: '',
        sDate: '',
        eDate: '',
        userApi: false,
        bbs_doc_no: '',
        emp_nm: '',
      },
      noti_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      usrinfo: {},
      menu1: false,
      menu2: false,
      notiColYn: true,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000116')
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
    mainMenus() {
      return this.$store.getters['menu/getMainMenus']
    },
    subMenus() {
      return this.$store.getters['menu/getSubMenus'](this.id)
    },
    isLogin() {
      return this.$store.getters['auth/isLogin']
    },
    getUser() {
      return this.$store.getters['auth/getUser']
    },
    getUsername() {
      return this.$store.getters['auth/getUsername']
    },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCO00000001.cd.push('')
        this.selCO00000001.cd_nm.push('SELECT')
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })

        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    this.usrinfo = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(this.usrinfo.time_zone)
    sDate = this.getTzDateObj(this.usrinfo.time_zone)
    sDate.setDate(sDate.getDate() - 365)

    this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
    this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
    this.usrinfo.roleTypeCds.forEach((i) => {
      if (i === 'M01' || i === 'C01' || i === 'S01') {
        // 사용자 유저 권한 체크, 상세페이지로만이동, 그리드 팝업공지여부 숨기기
        this.searchdatas.userApi = true
      }
    })

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000052'), // 업무구분
        name: 'biz_nm',
        fieldName: 'biz_nm',
        width: '50',
      },
      {
        header: this.$t('LB00000114'), // 제목(KO)
        name: 'bbs_doc_ttl',
        fieldName: 'bbs_doc_ttl',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'emp_nm',
        fieldName: 'emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
      {
        header: this.$t('조회수'), // 조회수
        name: 'view_cnt',
        fieldName: 'view_cnt',
        width: '50',
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
      if (clickData.cellType === 'gridEmpty' || clickData.cellType === 'header') return

      // alert('onCellClicked: ' + JSON.stringify(clickData))
      const current = gridView.getCurrent()
      const empno = dataProvider.getValue(current.dataRow, 'crt_emp_no')
      const deptcd = dataProvider.getValue(current.dataRow, 'dept_cd')
      const value = dataProvider.getValue(current.dataRow, 'bbs_doc_no')
      this.searchdatas.bbs_doc_no = value
      // 상세 or 수정 화면으로 이동
      if (current.column === 'bbs_doc_ttl') {
        if (this.searchdatas.userApi && (empno === this.usrinfo.emp_no || deptcd === this.usrinfo.dept_cd)) {
          this.$router.push({
            name: 'bbs-notiedt-notiedt',
            params: {
              notiedt: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                noti_yn: this.searchdatas.noti_yn,
                biz_cd: this.searchdatas.biz_cd,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                emp_nm: this.searchdatas.emp_nm,
              },
            },
          })
        } else {
          this.$router.push({
            name: 'bbs-notilup-notilup',
            params: {
              notilup: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                noti_yn: this.searchdatas.noti_yn,
                biz_cd: this.searchdatas.biz_cd,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
                emp_nm: this.searchdatas.emp_nm,
              },
            },
          })
        }
      }
    }.bind(this)
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas.bbs_doc_ttl = this.$route.params.schData.bbs_doc_ttl
      this.searchdatas.bbs_doc_cntn = this.$route.params.schData.bbs_doc_cntn
      this.searchdatas.noti_yn = this.$route.params.schData.noti_yn
      this.searchdatas.biz_cd = this.$route.params.schData.biz_cd
      this.searchdatas.sDate = this.$route.params.schData.sDate
      this.searchdatas.eDate = this.$route.params.schData.eDate
      this.searchdatas.emp_nm = this.$route.params.schData.emp_nm
    }
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
    openForm() {
      this.$router.push({
        name: 'bbs-notiedt-notiedt',
        params: {
          schData: {
            bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
            bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
            noti_yn: this.searchdatas.noti_yn,
            sDate: this.searchdatas.sDate,
            eDate: this.searchdatas.eDate,
            biz_cd: this.searchdatas.biz_cd,
          },
        },
      })
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/bbs/noti`, { params: this.searchdatas })

      const obj = {}
      obj.rowCount = 0
      rows.forEach((r) => {
        if (r.top_sn === 1) {
          obj.rowCount += 1
        }
      })

      dataProvider.setRows(rows)
      gridView.setFixedOptions(obj)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.searchdatas.bbs_doc_ttl = ''
      this.searchdatas.bbs_doc_cntn = ''
      this.searchdatas.noti_yn = ''
      this.searchdatas.biz_cd = ''
      this.searchdatas.bbs_doc_no = ''
      this.searchdatas.emp_nm = ''
      const user = this.$store.getters['auth/getUser']

      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      eDate = this.getTzDateObj(user.time_zone)
      sDate = this.getTzDateObj(user.time_zone)
      sDate.setDate(sDate.getDate() - 365)

      this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
      this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
      // this.select()
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'bbs_doc_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'bbs_doc_ttl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_emp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_dt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'noti_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'crt_emp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'view_cnt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'top_sn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
