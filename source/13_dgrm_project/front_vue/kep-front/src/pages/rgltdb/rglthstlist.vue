<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 규제 변경 이력 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000310') }}</span>
    </div>
    <!-- 규제 변경 이력 -->
    <div class="work_title">
      <h2>{{ $t('LB00000310') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <!-- 검토분야 
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000271') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.rgltchkdomacd"
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
                  v-model="schData.lawcd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 규제명 -->
                <div class="label_tit">{{ $t('LB00000213') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="rglt_nm"
                  v-model="schData.rgltnm"
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
              <v-col cols="1">
                <!-- 변경자 -->
                <div class="label_tit">{{ $t('LB00000311') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field v-model="schData.username" outlined single-line :hide-details="true" dense height="30" class="icon_sch" readonly @click="popOpen"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 변경일 -->
                <div class="label_tit">{{ $t('LB00000312') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sDate"
                      prepend-icon="event"
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
              <v-col cols="1">
                <!-- 변경구분 -->
                <div class="label_tit">{{ $t('LB00000313') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.chgtypecd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000015"
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
      <!-- 규제변경이력 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000310') }}</h3>
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
    <popup ref="pop" mkey1="rglthstlistuserslct1" mkey2="rglthstlistuserslct2" @close="popclose" />
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
    title: '규제DB-규제변경이력',
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
      totalcnt: 0,
      codeid: ['CH00000002', 'CH00000003', 'CO00000015'],
      codes: {
        CH00000002: [{ cd: '', cd_nm: '' }],
        CH00000003: [{ cd: '', cd_nm: '' }],
        CO00000015: [{ cd: '', cd_nm: '' }],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000310')
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
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chg_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_type_nm',
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
        fieldName: 'rglt_abbr',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sort_seq',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_yn',
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
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
        styles: {
          textAlignment: 'center',
        },
      },
      {
        header: this.$t('LB00000313'), // 변경구분
        name: 'chg_type_nm',
        fieldName: 'chg_type_nm',
        width: '100',
      },
      /* 
      {
        header: this.$t('LB00000307'), // 규제번호
        name: 'rglt_no',
        fieldName: 'rglt_no',
        width: '100',
      },
      {
        header: this.$t('LB00000271'), // 검토분야
        name: 'rglt_chk_doma_nm',
        fieldName: 'rglt_chk_doma_nm',
        width: '100',
        styleName: 'left',
      },
      */
      {
        header: this.$t('LB00000027'), // 법률
        name: 'law_nm',
        fieldName: 'law_nm',
        width: '180',
      },
      {
        header: this.$t('LB00000213'), // 규제명
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '300',
        styleName: 'left',
      },
      {
        header: {
          text: this.$t('LB00000305'), // 규제명 약어
        },
        direction: 'horizontal',
        name: 'rglt_abbr',
        fieldName: 'rglt_abbr',
        width: '200',
        styleName: 'left',
      },
      {
        header: {
          text: this.$t('LB00000091'), // 정렬순서
        },
        direction: 'horizontal',
        name: 'sort_seq',
        fieldName: 'sort_seq',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000049'), // 사용여부
        },
        direction: 'horizontal',
        name: 'use_yn',
        fieldName: 'use_yn',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000311'), // 변경자
        },
        direction: 'horizontal',
        name: 'upt_emp_nm',
        fieldName: 'upt_emp_nm',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000312'), // 변경일
        },
        direction: 'horizontal',
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '120',
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
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/rglt-hst-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData = {
        rgltnm: '',
        rgltchkdomacd: '',
        law_cd: '',
        userno: '',
        username: '',
        chgtypecd: '',
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
    },
    refresh() {
      this.select()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      if (!this.isEmpty(this.schData.rgltnm) && this.schData.rgltnm.length > 200) {
        this.$toast.show(this.$t('MS00000213'), { className: 'toast_error' }) // 규제명은 200자 이하로 입력해주세요.
        this.$refs.rglt_nm.focus()
        return false
      }
      return true
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
<style lang="scss" scoped></style>
