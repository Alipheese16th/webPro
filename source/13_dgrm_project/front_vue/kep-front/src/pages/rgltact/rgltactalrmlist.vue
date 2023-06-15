<template>
  <div>
    <div class="location">
      <!--규제대응--><!--규제 알람 조회-->
      <span>Home > {{ $t('LB00000540') }} > {{ $t('LB00000541') }}</span>
    </div>
    <div class="work_title">
      <!--규제 대응-->
      <h2>{{ $t('LB00000540') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--알람제목-->
                <div class="label_tit">{{ $t('LB00000542') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.alrm_ttl"
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
                <!--알람내용-->
                <div class="label_tit">{{ $t('LB00000543') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.alrm_cntn"
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
                <!--발생일-->
                <div class="label_tit">{{ $t('LB00000544') }}</div>
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
                <!--알람유형-->
                <div class="label_tit">{{ $t('LB00000545') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.alrm_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000018"
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
      <div class="sub_title">
        <!--규제 알람 목록-->
        <h3>{{ $t('LB00000546') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <div>
            <!--엑셀-->
            <v-btn color="primary" depressed height="30" @click="excelDown()">{{ $t('LB00000572') }}</v-btn>
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
    title: '규제 알람 조회',
    key(route) {
      return `/rgltact/rgltactalrmlist/${route.params.catalog}`
    },
  },
  data() {
    return {
      nowDate: new Date().toISOString().slice(0, 10),
      totalcnt: 0,
      searchdatas: {
        alrm_type_cd: '',
      },
      codeid: ['CH00000018'],
      codes: {
        CH00000018: [{ cd: '', cd_nm: '' }],
      },
      selCH00000018: {
        cd: [],
        cd_nm: [],
      },
      usrinfo: {},
      menu1: false,
      menu2: false,
      notiColYn: true,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000541')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
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
  created() {
    this.usrinfo = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    eDate = this.getTzDateObj(this.usrinfo.time_zone)
    sDate = this.getTzDateObj(this.usrinfo.time_zone)
    sDate.setDate(sDate.getDate() - 30)

    this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
    this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas = this.$route.params.schData
    }
  },
  async mounted() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCH00000018.cd.push('')
        this.selCH00000018.cd_nm.push('SEL')
        res.CH00000018.forEach((i) => {
          this.selCH00000018.cd.push(i.cd)
          this.selCH00000018.cd_nm.push(i.cd_nm)
        })
        this.codes.CH00000018.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000544'), // 발생일
        name: 'crt_da',
        fieldName: 'crt_da',
        width: '50',
      },
      {
        header: this.$t('LB00000545'), // 알람유형
        name: 'alrm_type_cd',
        fieldName: 'alrm_type_cd',
        width: '50',
      },
      {
        header: this.$t('LB00000542'), // 알람 제목
        name: 'alrm_ttl',
        fieldName: 'alrm_ttl',
        width: '200',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000547'), // 대상자재(건)
        name: 'mtrl_cnt',
        fieldName: 'mtrl_cnt',
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

    gridView.formatOptions.numberFormat = null

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      if (index.cellType === 'header' || index.cellType === 'head') return

      const rgltActAlrmNo = gridView.getValue(index.itemIndex, 'rglt_act_alrm_no')

      if (current.column === 'alrm_ttl') {
        this.$router.push({
          name: 'rgltact-rgltactalrmlup-rgltactalrmlup',
          params: {
            rgltactalrmlup: rgltActAlrmNo,
            schData: {
              alrm_ttl: this.searchdatas.alrm_ttl,
              alrm_cntn: this.searchdatas.alrm_cntn,
              alrm_type_cd: this.searchdatas.alrm_type_cd,
              sDate: this.searchdatas.sDate,
              eDate: this.searchdatas.eDate,
            },
          },
        })
      }
    }.bind(this)
    this.select()
  },
  methods: {
    excelDown() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '규제_알람_목록_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
      console.log(this.searchdatas)
      const rows = await this.$axios.$get(`/api/v1/ch/rgltact/alrm`, { params: this.searchdatas })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.searchdatas.alrm_ttl = ''
      this.searchdatas.alrm_cntn = ''
      this.searchdatas.alrm_type_cd = ''
      const user = this.$store.getters['auth/getUser']

      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      eDate = this.getTzDateObj(user.time_zone)
      sDate = this.getTzDateObj(user.time_zone)
      sDate.setDate(sDate.getDate() - 30)

      this.searchdatas.sDate = this.get_date_str_gubun(sDate, '-')
      this.searchdatas.eDate = this.get_date_str_gubun(eDate, '-')
      this.select()
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'rglt_act_alrm_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'crt_da',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'alrm_type_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'alrm_ttl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_cnt',
    dataType: ValueType.NUMBER,
  },
]
</script>
<style lang="scss" scoped></style>
