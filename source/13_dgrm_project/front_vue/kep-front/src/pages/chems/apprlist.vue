<template>
  <div>
    <div class="location">
      <!-- 시스템관리 --><!-- 결재 관리 -->
      <span>Home > {{ $t('LB00000042') }} > 결재 관리</span>
    </div>
    <!-- 결재 관리 -->
    <div class="work_title">
      <h2>결재 관리</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">등록일시</div>
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
                <div class="label_tit">결재번호</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="aprv_no"
                  v-model="schData.aprv_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  clearable
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">구분</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="arpv_cd"
                  v-model="schData.arpv_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000023"
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
                <div class="label_tit">업무코드</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="biz_no"
                  v-model="schData.biz_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  @keyup.enter="select"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">상신자</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.aprv_usr_nm"
                  outlined
                  single-line
                  append-icon="fas fa-search"
                  :hide-details="true"
                  dense
                  height="30"
                  clearable
                  readonly
                  @click:append="popOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">상태</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="aprv_stt_cd"
                  v-model="schData.aprv_stt_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000024"
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
                <div class="label_tit">사용여부</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="usg_yn"
                  v-model="schData.usg_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="usg_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">제목</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="aprv_ttl"
                  v-model="schData.aprv_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  clearable
                  @keyup.enter="select"
                ></v-text-field>
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
      <div class="sub_title">
        <h3>결재 관리 목록</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
    </div>
    <popup ref="pop" mkey1="apprlist1" mkey2="apprlist2" @close="popClose" />
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
    title: '결재관리',
    key(route) {
      return `/chems/apprlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  fetch() {
    // this.select()
  },
  data() {
    return {
      codeid: ['CO00000023', 'CO00000024'],
      codes: {
        CO00000023: [{ cd: '', cd_nm: '' }],
        CO00000024: [{ cd: '', cd_nm: '' }],
      },
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'T', cd_nm: 'T' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      menu1: false,
      menu2: false,
      schData: {},
      size: 0,
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

    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
      // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
      eDate = this.getTzDateObj(user.time_zone)
      sDate = this.getTzDateObj(user.time_zone)
      sDate.setDate(sDate.getDate() - 30)

      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
    }
  },
  mounted() {
    // 데이터프로바이더 셋팅
    const fields = [
      {
        fieldName: 'aprv_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'arpv_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'arpv_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'biz_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_usr_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_usr_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_stt_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_stt_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_mthd_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_ttl',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'aprv_cntn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usg_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_dt',
        dataType: ValueType.TEXT,
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)

    // 그리드 셋팅
    const columns = [
      {
        header: '결재번호',
        name: 'aprv_no',
        fieldName: 'aprv_no',
        width: '220',
        styleName: 'link center',
      },
      {
        header: '구분',
        name: 'arpv_nm',
        fieldName: 'arpv_nm',
        width: '140',
      },
      {
        header: '업무코드',
        name: 'biz_no',
        fieldName: 'biz_no',
        width: '150',
      },
      {
        header: '상신자',
        name: 'aprv_usr_nm',
        fieldName: 'aprv_usr_nm',
        width: '80',
      },
      {
        header: '상태',
        name: 'aprv_stt_nm',
        fieldName: 'aprv_stt_nm',
        width: '70',
      },
      {
        header: '후처리메소드',
        name: 'aprv_mthd_nm',
        fieldName: 'aprv_mthd_nm',
        width: '190',
      },
      {
        header: '제목',
        name: 'aprv_ttl',
        fieldName: 'aprv_ttl',
        width: '200',
        styleName: 'left',
      },
      {
        header: '사용여부',
        name: 'usg_yn',
        fieldName: 'usg_yn',
        width: '60',
      },
      {
        header: '등록일시',
        name: 'crt_dt',
        fieldName: 'crt_dt',
        width: '150',
      },
    ]

    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.header.height = 60
    gridView.footer.height = 40

    gridView.onCellClicked = function (grid, clickData) {
      if (clickData.itemIndex < 0) return
      if (clickData.cellType === 'data') {
        if (clickData.column === 'aprv_no') {
          this.$router.push({
            name: `chems-apprdtledt-apprdtledt`,
            params: {
              apprdtledt: grid.getValue(clickData.itemIndex, 'aprv_no'),
              schData: this.schData,
            },
          })
        }
      }
    }.bind(this)

    // 공통코드 조회
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000023.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000024.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async select() {
      if (this.isEmpty(this.schData.aprv_usr_nm)) {
        this.schData.aprv_usr_id = ''
      }

      const rows = await this.$axios.$get(`/api/v1/common/chems/appr-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)

      this.size = dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        sDate: this.get_date_str_gubun(sDate, '-'),
        eDate: this.get_date_str_gubun(eDate, '-'),
      }
    },
    popOpen() {
      this.$refs.pop.open('', false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'aprv_usr_id', i.usr_id)
          this.$set(this.schData, 'aprv_usr_nm', i.usr_nm)
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
