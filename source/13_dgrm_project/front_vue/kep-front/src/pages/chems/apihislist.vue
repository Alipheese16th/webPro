<template>
  <div>
    <div class="location">
      <!-- 시스템 관리 --><!-- API 이력 조회 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000245') }}</span>
    </div>
    <div class="work_title">
      <!-- API 이력 조회 -->
      <h2>{{ $t('LB00000245') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 사용자 -->
                <div class="label_tit">{{ $t('LB00000246') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field v-model="schData.username" outlined single-line :hide-details="true" dense height="30" class="icon_sch" readonly @click="popOpen"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- API URL -->
                <div class="label_tit">{{ $t('LB00000233') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field v-model="schData.api_url" outlined single-line :hide-details="true" dense height="30" maxlength="299" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 날짜 -->
                <div class="label_tit">{{ $t('LB00000247') }}</div>
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
      <!-- API 이력 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000248') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 건 -->
          {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd"></div>
    <popup ref="pop" mkey1="apihislist1" mkey2="apihislist2" @close="popClose" />
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
    title: '시스템관리-API 이력 조회',
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
      size: 0,
      menu1: false,
      menu2: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
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
    // sDate.setDate(sDate.getDate() - 7)

    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    const columns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'usr_id',
        fieldName: 'usr_id',
        width: '100',
      },
      {
        header: this.$t('LB00000105'), // 사용자명
        name: 'usr_nm',
        fieldName: 'usr_nm',
        width: '100',
      },
      {
        header: 'ID',
        name: 'api_no',
        fieldName: 'api_no',
        width: '100',
      },
      {
        header: 'API URL',
        name: 'api_exe_url',
        fieldName: 'api_exe_url',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000247'), // 날짜
        name: 'exe_dt',
        fieldName: 'exe_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000249'), // 시간
        name: 'exe_tm',
        fieldName: 'exe_tm',
        width: '100',
      },
      {
        header: 'IP address',
        name: 'ip_addr',
        fieldName: 'ip_addr',
        width: '100',
      },
      {
        header: this.$t('LB00000250'), // 보유 권한
        name: 'role_nm',
        fieldName: 'role_nm',
        width: '200',
      },
    ]
    const headergroup = [
      {
        name: 'Group1',
        direction: 'horizontal',
        items: ['usr_id', 'usr_nm'],
        header: {
          text: this.$t('LB00000246'), // 사용자
        },
      },
      {
        name: 'Group2',
        direction: 'horizontal',
        items: ['api_no', 'api_exe_url'],
        header: {
          text: 'API',
        },
      },
      {
        name: 'Group3',
        direction: 'horizontal',
        items: ['exe_dt', 'exe_tm'],
        header: {
          text: this.$t('LB00000251'), // 접속시간
        },
      },
      'ip_addr',
      'role_nm',
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
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
      const rows = await this.$axios.$get(`/api/v1/common/chems/api-his`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)

      this.size = dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        username: '',
        api_url: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'username', i.usr_nm)
        })
      }
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'usr_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'api_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'api_exe_url',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'exe_dt',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'exe_tm',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'ip_addr',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'role_nm',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
]
</script>
<style lang="scss" scoped></style>
