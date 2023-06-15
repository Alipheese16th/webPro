<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 물질 관리 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000333') }}</span>
    </div>
    <!-- 물질 관리 -->
    <div class="work_title">
      <h2>{{ $t('LB00000333') }}</h2>
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
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="32" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="32" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 물질목록 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000334') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000047') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="pop" @close="refresh" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './sbstedt'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '규제DB-물질 관리',
    key(route) {
      return `/rgltdb/sbstmn/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  data() {
    return {
      user: {},
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
      totalcnt: 0,
      codeid: ['CH00000002', 'CH00000003'],
      codes: {
        CH00000002: [{ cd: '', cd_nm: '' }],
        CH00000003: [{ cd: '', cd_nm: '' }],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000333')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    this.frmData.username = this.$store.getters['auth/getUsername']
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
      {
        fieldName: 'rglt_sbst_cnt',
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
      /* 
      {
        header: this.$t('LB00000317'), // 물질번호
        name: 'sbst_no',
        fieldName: 'sbst_no',
        width: '150',
      },
      */
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '150',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000335'), // 대상규제(건)
        name: 'rglt_sbst_cnt',
        fieldName: 'rglt_sbst_cnt',
        width: '100',
        styleName: 'link right',
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'use_yn',
        fieldName: 'use_yn',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000055'), // 등록자
        },
        direction: 'horizontal',
        name: 'upt_emp_nm',
        fieldName: 'upt_emp_nm',
        width: '100',
      },
      {
        header: {
          text: this.$t('LB00000054'), // 등록일
        },
        direction: 'horizontal',
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

    gridView.onCellClicked = function (grid, clickData) {
      if (clickData.cellType === 'data' && clickData.column === 'sbst_ko_nm') {
        const value = dataProvider.getValue(clickData.dataRow, 'sbst_no')
        this.$refs.pop.open(true, value, this.frmData.username)
      }
      if (clickData.cellType === 'data' && clickData.column === 'rglt_sbst_cnt') {
        const value = gridView.getValue(clickData.dataRow, 'sbst_no')
        this.$router.push({
          name: `rgltdb-sbstmnedt-sbstmnedt`,
          params: {
            sbstmnedt: value,
            schData: {
              cas_no: this.schData.cas_no,
              sbst_nm: this.schData.sbst_nm,
              // rglt_chk_doma_cd: this.schData.rglt_chk_doma_cd,
              law_cd: this.schData.law_cd,
              rglt_nm: this.schData.rglt_nm,
            },
          },
        })
      }
    }.bind(this)
  },
  methods: {
    async select() {
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/sbst-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData.cas_no = ''
      this.schData.sbst_nm = ''
      this.schData.rglt_nm = ''
      // this.schData.rglt_chk_doma_cd = ''
      this.schData.law_cd = ''

      // this.select()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      if (this.isEmpty(this.schData.cas_no) && this.isEmpty(this.schData.sbst_nm)) {
        this.$toast.show(this.$t('MS00000009'), { className: 'toast_error' }) // 물질명 또는 CAS NO.를 입력하세요.
        this.$refs.cas_no.focus()
        return false
      }
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
    refresh() {
      this.select()
    },
    openNew() {
      const frmData = this.frmData
      const writer = this.user.emp_no
      this.$refs.pop.open(false, frmData, writer)
    },
  },
}
</script>
<style lang="scss" scoped></style>
