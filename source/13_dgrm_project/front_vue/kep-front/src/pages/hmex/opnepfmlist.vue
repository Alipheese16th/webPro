<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 유소견자 사후관리 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001109') }}</span>
    </div>
    <div class="work_title">
      <!-- 유소견자 사후관리 -->
      <h2>{{ $t('LB00001109') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="schData.cmpy_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--검진년도-->
                <div class="label_tit">{{ $t('LB00001055') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="hmex_year" v-model="schData.hmex_year" label="ALL" :items="year" outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  v-model="schData.org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="search"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--성명-->
                <div class="label_tit">{{ $t('LB00000701') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  v-model="schData.hmex_trgp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @click:clear="reset"
                  @keypress.enter="search"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--검진구분-->
                <div class="label_tit">{{ $t('LB00001042') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.hmex_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000037"
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
                <!-- 판정 -->
                <div class="label_tit">{{ $t('LB00001000') }}</div>
              </v-col>
              <v-col cols="3">
                <template>
                  <v-container fluid>
                    <v-select
                      v-model="hmex_ovr_opin_jgmt_cd"
                      label="ALL"
                      item-text="cd_nm"
                      item-value="cd"
                      :items="codes.SH00000038"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      multiple
                      clearable
                      small-chips
                    >
                      <template v-slot:prepend-item>
                        <v-list-item ripple @mousedown.prevent @click="itemCheck">
                          <v-list-item-action>
                            <v-icon :color="hmex_ovr_opin_jgmt_cd.length > 0 ? 'indigo darken-4' : ''">
                              {{ icon }}
                            </v-icon>
                          </v-list-item-action>
                          <v-list-item-content>
                            <v-list-item-title>ALL</v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>
                      </template>
                      <template v-slot:selection="{ item, index }">
                        <v-chip v-if="index === 0" style="height: 18px">
                          <span>{{ item.cd_nm }}</span>
                        </v-chip>
                        <span v-if="index === 1" class="grey--text text-caption"> 외 {{ hmex_ovr_opin_jgmt_cd.length - 1 }}건 </span>
                      </template>
                    </v-select>
                  </v-container>
                </template>
              </v-col>
              <v-col cols="1">
                <!-- 상담 -->
                <div class="label_tit">{{ $t('LB00001112') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.hmex_cnsl_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="hmexcnslyn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="search">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!-- 유소견자 목록 -->
        <h3>{{ $t('LB00001110') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <!-- <v-btn color="primary" height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn> -->
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="pop" @close="popClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './opnepfmpopup'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '유소견자 사후관리',
  },
  components: {
    popup,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      hmex_ovr_opin_jgmt_cd: [], // 판정
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000037', 'SH00000038', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000037: [{ cd: '', cd_nm: '' }], // 검진구분
        SH00000038: [{ cd: '', cd_nm: '' }], // 판정
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      year: [],
      // 상담여부
      hmexcnslyn: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001109') // 유소견자 사후관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
    selectAllCheck() {
      return this.hmex_ovr_opin_jgmt_cd.length === this.codes.SH00000038.length
    },
    selectSomeCheck() {
      return this.hmex_ovr_opin_jgmt_cd.length > 0 && !this.selectAllCheck
    },
    icon() {
      if (this.selectAllCheck) return 'mdi-close-box'
      if (this.selectSomeCheck) return 'mdi-minus-box'
      return 'mdi-checkbox-blank-outline'
    },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    const yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    for (let y = yyyy; y >= 2020; y--) {
      this.year.push(y)
    }

    // this.schData.hmex_year = yyyy
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000037.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })

        this.createGrid()
        this.search()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'cmpy_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_tgt_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_trgp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cnsl_schd_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cnsl_yn',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000700'), // 회사
          name: 'cmpy_nm',
          fieldName: 'cmpy_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '120',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001055'), // 검진년도
          name: 'hmex_year',
          fieldName: 'hmex_year',
          width: '100',
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'hmex_cl_nm',
          fieldName: 'hmex_cl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '120',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hmex_tgt_emp_id',
          fieldName: 'hmex_tgt_emp_id',
          width: '100',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hmex_trgp_nm',
          fieldName: 'hmex_trgp_nm',
          width: '100',
          styleName: 'link',
        },
        {
          header: this.$t('LB00001111'), // 상담예정일
          name: 'hmex_cnsl_schd_dt',
          fieldName: 'hmex_cnsl_schd_dt',
          width: '100',
        },
        {
          header: this.$t('LB00001112'), // 상담
          name: 'hmex_cnsl_yn',
          fieldName: 'hmex_cnsl_yn',
          width: '70',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.column === 'hmex_trgp_nm') {
          grid.commit()
          const empNo = dataProvider.getValue(clickData.dataRow, 'hmex_tgt_emp_id')
          const hmexYear = dataProvider.getValue(clickData.dataRow, 'hmex_year')
          this.$refs.pop.open(empNo, hmexYear)
        }
      }.bind(this)
    },
    async search() {
      this.schData.hmex_ovr_opin_jgmt_cd = this.hmex_ovr_opin_jgmt_cd.join()

      const rows = await this.$axios.$get(`/api/v1/she/hlth/abfr`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.schData = {
        cmpy_cd: '',
      }
      this.hmex_ovr_opin_jgmt_cd = []
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
      // const user = this.$store.getters['auth/getUser']
      // this.schData.hmex_year = this.getTzDateObj(user.time_zone).getFullYear()
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    popClose() {
      this.search()
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      // 유소견자 목록
      const fileName = this.$t('LB00001110') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    itemCheck() {
      if (this.selectAllCheck) {
        this.hmex_ovr_opin_jgmt_cd = []
      } else {
        this.hmex_ovr_opin_jgmt_cd = []
        this.codes.SH00000038.forEach((i) => {
          this.hmex_ovr_opin_jgmt_cd.push(i.cd)
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
