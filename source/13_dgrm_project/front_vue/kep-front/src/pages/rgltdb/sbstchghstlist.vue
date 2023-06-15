<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 물질 변경 이력 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000327') }}</span>
    </div>
    <!-- 물질 변경 이력 -->
    <div class="work_title">
      <h2>{{ $t('LB00000327') }}</h2>
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
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 규제명 -->
                <div class="label_tit">{{ $t('LB00000213') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="rglt_nm"
                  v-model="schData.rglt_nm"
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
              <v-col cols="1">
                <!-- 변경구분 -->
                <div class="label_tit">{{ $t('LB00000313') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.chg_type_cd"
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
      <!-- 물질 변경 이력 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000327') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="pop" mkey1="sbstchghstlistuserslct1" mkey2="sbstchghstlistuserslct2" @close="popclose" />
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
    title: '규제DB-물질 변경 이력',
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
      return this.$t('LB00000327')
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
        fieldName: 'chg_type_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chg_type_nm',
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
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000313'), // 변경구분
        name: 'chg_type_nm',
        fieldName: 'chg_type_nm',
        width: '80',
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
        width: '150',
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
        width: '300',
        styleName: 'left',
      },
      {
        header: {
          text: this.$t('LB00000049'), // 사용여부
        },
        name: 'use_yn',
        fieldName: 'use_yn',
        width: '100',
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
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/sbst-hst-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData = {
        cas_no: '',
        sbst_nm: '',
        rglt_chk_doma_cd: '',
        law_cd: '',
        rglt_nm: '',
        username: '',
        chg_emp_no: '',
        chg_type_cd: '',
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
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'username', i.emp_nm)
          this.$set(this.schData, 'chg_emp_no', i.emp_no)
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
