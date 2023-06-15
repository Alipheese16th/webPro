<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 규제 관리 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000302') }}</span>
    </div>
    <!-- 규제 관리 -->
    <div class="work_title">
      <h2>{{ $t('LB00000302') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
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
              <v-col cols="3">
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
              <v-col cols="1">
                <!-- 사용여부(사용여부) -->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="useYn"
                  v-model="schData.useyn"
                  label="Y"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <!--
            <v-row>
               검토분야 
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
            </v-row>
            -->
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
      <!-- 규제목록 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000301') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="pop" @close="refresh" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './rgltedt'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '규제DB-규제관리',
    key(route) {
      return `/rgltdb/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  fetch() {
    this.schData.useyn = 'Y'
    this.select()
  },
  data() {
    return {
      schData: {
        rgltnm: '',
        // rgltchkdomacd: '',
        lawcd: '',
        useyn: 'Y',
      },
      frmData: {},
      menu1: false,
      menu2: false,
      totalcnt: 0,
      codeid: ['CH00000002', 'CH00000003'],
      codes: {
        CH00000002: [{ cd: '', cd_nm: '' }],
        CH00000003: [{ cd: '', cd_nm: '' }],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000302')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
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
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_type_nm',
        dataType: ValueType.TEXT,
      },
      /* 
      {
        fieldName: 'rglt_chk_doma_nm',
        dataType: ValueType.TEXT,
      },
      */
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
      {
        fieldName: 'use_yn',
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
        header: this.$t('LB00000307'), // 규제번호
        name: 'rglt_no',
        fieldName: 'rglt_no',
        width: '100',
      },
      {
        header: this.$t('LB00000306'), // 규제유형
        name: 'rglt_type_nm',
        fieldName: 'rglt_type_nm',
        width: '100',
      },
      /* 
      {
        header: this.$t('LB00000271'), // 검토분야
        name: 'rglt_chk_doma_nm',
        fieldName: 'rglt_chk_doma_nm',
        width: '100',
      },
      */
      {
        header: this.$t('LB00000027'), // 법률
        name: 'law_nm',
        fieldName: 'law_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000213'), // 규제명
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000305'), // 규제명 약어
        name: 'rglt_abbr',
        fieldName: 'rglt_abbr',
        width: '150',
      },
      {
        header: this.$t('LB00000091'), // 정렬순서
        name: 'sort_seq',
        fieldName: 'sort_seq',
        width: '100',
      },
      {
        header: this.$t('LB00000304'), // 규제대상 물질(건)
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
        header: this.$t('LB00000055'), // 등록자
        name: 'upt_emp_nm',
        fieldName: 'upt_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
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
      if (clickData.cellType === 'data' && clickData.column === 'rglt_nm') {
        const value = dataProvider.getValue(clickData.dataRow, 'rglt_no')
        this.$refs.pop.open(true, value, this.frmData.username)
      }

      if (clickData.cellType === 'data' && clickData.column === 'rglt_sbst_cnt') {
        const value = gridView.getValue(clickData.dataRow, 'rglt_no')
        this.$router.push({
          name: `rgltdb-rgltsbstmnedt-rgltsbstmnedt`,
          params: {
            rgltsbstmnedt: value,
            schData: {
              //  rgltchkdomacd: this.schData.rgltchkdomacd,
              lawcd: this.schData.lawcd,
              rgltnm: this.schData.rgltnm,
              useyn: this.schData.useyn,
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
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/rglt-list`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    schReset() {
      this.schData = {
        rgltnm: '',
        // rgltchkdomacd: '',
        lawcd: '',
        useyn: 'Y',
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
    openNew() {
      const frmData = this.frmData
      this.$refs.pop.open(false, frmData, this.frmData.username)
    },
  },
}
</script>
<style lang="scss" scoped></style>
