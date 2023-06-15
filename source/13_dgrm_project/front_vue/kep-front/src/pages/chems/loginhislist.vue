<template>
  <div>
    <div class="location">
      <!-- 시스템관리 --><!-- 로그인 이력 조회 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000252') }}</span>
    </div>
    <!-- 로그인 이력 조회 -->
    <div class="work_title">
      <h2>{{ $t('LB00000252') }}</h2>
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
                <v-text-field
                  v-model="schData.userName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 날짜 -->
                <div class="label_tit">{{ $t('LB00000247') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.startDate"
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
                  <v-date-picker v-model="schData.startDate" :max="schData.endDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.endDate"
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
                  <v-date-picker v-model="schData.endDate" :min="schData.endDate" no-title @input="menu2 = false"> </v-date-picker>
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
      <!-- 로그인 이력 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000253') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!-- 총 --><!-- 건 -->
          {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd"></div>
    <popup ref="pop" mkey1="loginhislist1" mkey2="loginhislist2" @close="popClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'

export default {
  meta: {
    title: '시스템관리-화면접속 이력 조회',
  },
  components: {
    popup,
  },
  fetch() {
    
  },
  data() {
    return {
      schData: {},
      frmData: {},
      size: 0,
      menu1: false,
      menu2: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
      sDate: null,
      eDate: null,
      gridView: null,
      dataProvider: null
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
    this.gridView = GridView
    this.dataProvider = LocalDataProvider
    this.sDate = new Date()
    this.eDate = new Date()
    const user = this.$store.getters['auth/getUser']

    console.log(user.time_zone);

    // // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    this.eDate = this.getTzDateObj(user.time_zone)
    this.sDate = this.getTzDateObj(user.time_zone)
    this.sDate.setDate(this.sDate.getDate() - 7)

    this.schData.startDate = this.get_date_str_gubun(this.sDate, '-')
    this.schData.endDate = this.get_date_str_gubun(this.eDate, '-')
    this.frmData.userName = this.$store.getters['auth/getUsername']

    this.select();
  },
  mounted() {
    const columns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'userId',
        fieldName: 'userId',
        width: '100',
      },
      {
        header: this.$t('LB00000105'), // 사용자명
        name: 'userName',
        fieldName: 'userName',
        width: '100',
      },
      {
        header: this.$t('LB00000247'), // 날짜
        name: 'loginDate',
        fieldName: 'loginDate',
        width: '100',
      },
      {
        header: this.$t('LB00000249'), // 시간
        name: 'loginTime',
        fieldName: 'loginTime',
        width: '100',
      },
      {
        header: 'IP address',
        name: 'ipAddress',
        fieldName: 'ipAddress',
        width: '100',
      },
      {
        header: this.$t('LB00000250'), // 보유 권한
        name: 'roleName',
        fieldName: 'roleName',
        width: '200',
      },
    ]
    const headergroup = [
      'userId',
      'userName',
      {
        name: 'login',
        direction: 'horizontal',
        items: ['loginDate', 'loginTime'],
        header: {
          text: this.$t('LB00000254'), // 로그인
        },
      },
      'ipAddress',
      'roleName',
    ]

    // 그리드 세팅
    const fields = [
      {
        fieldName: 'userId',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'loginDate',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'loginTime',
        dataType: ValueType.TEXT,
        // datetimeFormat: 'iso',
      },
      {
        fieldName: 'ipAddress',
        dataType: ValueType.TEXT,
        // datetimeFormat: 'iso',
      },
      {
        fieldName: 'roleName',
        dataType: ValueType.TEXT,
        // datetimeFormat: 'iso',
      },
    ]
    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    this.gridView = new GridView('realgrid')
    this.gridView.setDataSource(this.dataProvider)
    this.gridView.setColumns(columns)
    this.gridView.setFooters({ visible: false })
    this.gridView.setColumnLayout(headergroup)

    this.gridView.setStateBar({ visible: false })
    this.gridView.setCheckBar({ visible: false })
    this.gridView.editOptions.editable = false
    this.gridView.displayOptions.selectionStyle = 'block'

    this.gridView.header.height = 60
    this.gridView.displayOptions.rowHeight = 40
    this.gridView.footer.height = 40
    this.gridView.displayOptions.fitStyle = 'fill'
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
      console.log(this.schData);
      const rows = await this.$axios.$get(`/api/v1/common/chems/login-his`, { params: this.schData })

      this.dataProvider.setRows(rows)
      this.gridView.refresh()
      this.gridView.setTopItem(0)

      this.size = this.dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        userName: '',
        // startDate: this.get_date_str_gubun(startDate, '-'),
        // endDate: this.get_date_str_gubun(endDate, '-'),
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
          this.$set(this.schData, 'userName', i.usr_nm)
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
