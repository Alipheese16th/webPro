<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 건강검진 결과 관리 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001088') }}</span>
    </div>
    <div class="work_title">
      <!-- 건강검진 결과 관리 -->
      <h2>{{ $t('LB00001088') }}</h2>
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
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
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
                <v-text-field
                  v-model="schData.hmex_trgp_nm"
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
                <!--검진 년도-->
                <div class="label_tit">{{ $t('LB00001055') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="hmex_year" v-model="schData.hmex_year" label="ALL" :items="year" outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
              <v-col cols="1">
                <!--수검여부-->
                <div class="label_tit">{{ $t('LB00001090') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.hmex_insp_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="inspYn"
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
        <!--건강검진 결과 목록-->
        <h3>{{ $t('LB00001091') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <!-- <v-btn color="primary" height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn> -->
          <!--삭제-->
          <v-btn color="primary" height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 449px"></div>
      <div id="exceltemp" style="width: 100%; height: 481px; display: none"></div>
    </div>
    <popup ref="pop" mkey="hmexrslt" @close="popClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './hmextrgpinsphist'

let gridView = GridView
let dataProvider = LocalDataProvider

// let gridView2 = GridView
// let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '건강검진 결과 관리',
    key(route) {
      return `/hmex4/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      totalcnt: 0,
      codeid: ['ST00000002', 'SH00000037', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000037: [{ cd: '', cd_nm: '' }], // 검진구분
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      year: [],
      succnt: 0,
      failcnt: 0,
      inspYn: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001088') // 건강검진 결과 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
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
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
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
          fieldName: 'hmex_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'deal_mtra_nm',
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
          fieldName: 'jncm_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'rsgn_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'abfd_xtra_cnsl_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_insp_orga_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'ovr_opin_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm1',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm2',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm3',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm4',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd5',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm5',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_ovr_opin_nm5',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'full_hmex_trgp_nm',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000700'), // 회사
          name: 'cmpy_nm',
          fieldName: 'cmpy_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_nm',
          fieldName: 'wkpl_nm',
          width: '120',
        },
        {
          header: this.$t('LB00001055'), // 검진년도
          name: 'hmex_year',
          fieldName: 'hmex_year',
          width: '70',
        },
        {
          header: this.$t('LB00000757'), // 순번
          name: 'hmex_sn',
          fieldName: 'hmex_sn',
          width: '50',
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'hmex_cl_nm',
          fieldName: 'hmex_cl_nm',
          width: '80',
        },
        {
          header: this.$t('LB00001041'), // 취급물질
          name: 'deal_mtra_nm',
          fieldName: 'deal_mtra_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '120',
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hmex_tgt_emp_id',
          fieldName: 'hmex_tgt_emp_id',
          width: '80',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hmex_trgp_nm',
          fieldName: 'hmex_trgp_nm',
          width: '80',
          styleName: 'link',
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'jncm_dt',
          fieldName: 'jncm_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001058'), // 퇴사일
          name: 'rsgn_dt',
          fieldName: 'rsgn_dt',
          width: '80',
        },
        {
          header: this.$t('LB00001090'), // 수검여부
          name: 'hmex_insp_yn',
          fieldName: 'hmex_insp_yn',
          width: '80',
        },
        {
          header: this.$t('LB00001092'), // 검진일자
          name: 'hmex_insp_dt',
          fieldName: 'hmex_insp_dt',
          width: '100',
        },
        {
          header: this.$t('LB00001093'), // 검진기관
          name: 'hmex_insp_orga_nm',
          fieldName: 'hmex_insp_orga_nm',
          width: '150',
        },
        {
          header: this.$t('LB00001380'), // 추가상담
          name: 'abfd_xtra_cnsl_yn',
          fieldName: 'abfd_xtra_cnsl_yn',
          width: '80',
        },
        {
          header: this.$t('LB00001094'), // 유소견여부
          name: 'ovr_opin_yn',
          fieldName: 'ovr_opin_yn',
          width: '80',
        },
        {
          header: this.$t('LB00001000') + '1', // 판정1
          name: 'hmex_ovr_opin_jgmt_cd_nm1',
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm1',
          width: '80',
        },
        {
          header: this.$t('LB00001095') + '1', // 소견1
          name: 'hmex_ovr_opin_nm1',
          fieldName: 'hmex_ovr_opin_nm1',
          width: '80',
        },
        {
          header: this.$t('LB00001000') + '2', // 판정2
          name: 'hmex_ovr_opin_jgmt_cd_nm2',
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm2',
          width: '80',
        },
        {
          header: this.$t('LB00001095') + '2', // 소견2
          name: 'hmex_ovr_opin_nm2',
          fieldName: 'hmex_ovr_opin_nm2',
          width: '80',
        },
        {
          header: this.$t('LB00001000') + '3', // 판정3
          name: 'hmex_ovr_opin_jgmt_cd_nm3',
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm3',
          width: '80',
        },
        {
          header: this.$t('LB00001095') + '3', // 소견3
          name: 'hmex_ovr_opin_nm3',
          fieldName: 'hmex_ovr_opin_nm3',
          width: '80',
        },
        {
          header: this.$t('LB00001000') + '4', // 판정4
          name: 'hmex_ovr_opin_jgmt_cd_nm4',
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm4',
          width: '80',
        },
        {
          header: this.$t('LB00001095') + '4', // 소견4
          name: 'hmex_ovr_opin_nm4',
          fieldName: 'hmex_ovr_opin_nm4',
          width: '80',
        },
        {
          header: this.$t('LB00001000') + '5', // 판정5
          name: 'hmex_ovr_opin_jgmt_cd_nm5',
          fieldName: 'hmex_ovr_opin_jgmt_cd_nm5',
          width: '80',
        },
        {
          header: this.$t('LB00001095') + '5', // 소견5
          name: 'hmex_ovr_opin_nm5',
          fieldName: 'hmex_ovr_opin_nm5',
          width: '80',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true })
      gridView.setCopyOptions({ enabled: false })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'none'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.column === 'hmex_trgp_nm') {
          grid.commit()
          const empNo = dataProvider.getValue(clickData.dataRow, 'hmex_tgt_emp_id')
          this.$refs.pop.open(empNo)
        }
      }.bind(this)

      // const excelColumns = [
      //   {
      //     header: this.$t('LB00000700'), // 회사
      //     name: 'cmpy_nm',
      //     fieldName: 'cmpy_nm',
      //     width: '100',
      //   },
      //   {
      //     header: this.$t('LB00000021'), // 사업장
      //     name: 'wkpl_nm',
      //     fieldName: 'wkpl_nm',
      //     width: '120',
      //   },
      //   {
      //     header: this.$t('LB00001055'), // 검진년도
      //     name: 'hmex_year',
      //     fieldName: 'hmex_year',
      //     width: '70',
      //   },
      //   {
      //     header: this.$t('LB00000757'), // 순번
      //     name: 'hmex_sn',
      //     fieldName: 'hmex_sn',
      //     width: '50',
      //   },
      //   {
      //     header: this.$t('LB00001042'), // 검진구분
      //     name: 'hmex_cl_nm',
      //     fieldName: 'hmex_cl_nm',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001041'), // 취급물질
      //     name: 'deal_mtra_nm',
      //     fieldName: 'deal_mtra_nm',
      //     width: '100',
      //   },
      //   {
      //     header: this.$t('LB00000071'), // 부서
      //     name: 'org_nm',
      //     fieldName: 'org_nm',
      //     width: '120',
      //   },
      //   {
      //     header: this.$t('LB00000073'), // 사번
      //     name: 'hmex_tgt_emp_id',
      //     fieldName: 'hmex_tgt_emp_id',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00000701'), // 성명
      //     name: 'hmex_trgp_nm',
      //     fieldName: 'hmex_trgp_nm',
      //     width: '80',
      //     styleName: 'link',
      //   },
      //   {
      //     header: this.$t('LB00000851'), // 입사일
      //     name: 'jncm_dt',
      //     fieldName: 'jncm_dt',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001058'), // 퇴사일
      //     name: 'rsgn_dt',
      //     fieldName: 'rsgn_dt',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001090'), // 수검여부
      //     name: 'hmex_insp_yn',
      //     fieldName: 'hmex_insp_yn',
      //     width: '70',
      //   },
      //   {
      //     header: this.$t('LB00001092'), // 검진일자
      //     name: 'hmex_insp_dt',
      //     fieldName: 'hmex_insp_dt',
      //     width: '100',
      //   },
      //   {
      //     header: this.$t('LB00001093'), // 검진기관
      //     name: 'hmex_insp_orga_nm',
      //     fieldName: 'hmex_insp_orga_nm',
      //     width: '150',
      //   },
      //   {
      //     header: this.$t('LB00001380'), // 추가상담
      //     name: 'abfd_xtra_cnsl_yn',
      //     fieldName: 'abfd_xtra_cnsl_yn',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001094'), // 유소견여부
      //     name: 'ovr_opin_yn',
      //     fieldName: 'ovr_opin_yn',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001000') + '1', // 판정1
      //     name: 'hmex_ovr_opin_jgmt_cd1',
      //     fieldName: 'hmex_ovr_opin_jgmt_cd1',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001095') + '1', // 소견1
      //     name: 'hmex_ovr_opin_nm1',
      //     fieldName: 'hmex_ovr_opin_nm1',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001000') + '2', // 판정2
      //     name: 'hmex_ovr_opin_jgmt_cd2',
      //     fieldName: 'hmex_ovr_opin_jgmt_cd2',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001095') + '2', // 소견2
      //     name: 'hmex_ovr_opin_nm2',
      //     fieldName: 'hmex_ovr_opin_nm2',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001000') + '3', // 판정3
      //     name: 'hmex_ovr_opin_jgmt_cd3',
      //     fieldName: 'hmex_ovr_opin_jgmt_cd3',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001095') + '3', // 소견3
      //     name: 'hmex_ovr_opin_nm3',
      //     fieldName: 'hmex_ovr_opin_nm3',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001000') + '4', // 판정4
      //     name: 'hmex_ovr_opin_jgmt_cd4',
      //     fieldName: 'hmex_ovr_opin_jgmt_cd4',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001095') + '4', // 소견4
      //     name: 'hmex_ovr_opin_nm4',
      //     fieldName: 'hmex_ovr_opin_nm4',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001000') + '5', // 판정5
      //     name: 'hmex_ovr_opin_jgmt_cd5',
      //     fieldName: 'hmex_ovr_opin_jgmt_cd5',
      //     width: '80',
      //   },
      //   {
      //     header: this.$t('LB00001095') + '5', // 소견5
      //     name: 'hmex_ovr_opin_nm5',
      //     fieldName: 'hmex_ovr_opin_nm5',
      //     width: '80',
      //   },
      // ]
      // 그리드 3 시작
      // dataProvider2 = new LocalDataProvider(false)
      // dataProvider2.setFields(fields)
      // gridView2 = new GridView('exceltemp')
      // gridView2.displayOptions.selectionStyle = 'block'
      // gridView2.setDataSource(dataProvider2)
      // gridView2.setColumns(excelColumns)
      // gridView2.setFooters({ visible: false })
      // gridView2.setStateBar({ visible: false })
      // gridView2.setCheckBar({ visible: false })
      // gridView2.editOptions.editable = true
      // gridView2.displayOptions.selectionStyle = 'block'

      // gridView2.header.height = 39
      // gridView2.displayOptions.rowHeight = 40
      // gridView2.footer.height = 40
      // gridView2.displayOptions.fitStyle = 'fill'
    },
    async search() {
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexrslt`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      // dataProvider2.setRows(rows)
      // gridView2.refresh()
      // gridView2.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.schData = {
        cmpy_cd: '',
      }
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
    add() {
      this.$router.push({
        name: `hmex-hmexrsltmgntedt-hmexrsltmgntedt`,
        params: {
          schData: this.schData,
        },
      })
    },
    async del() {
      let tmpData = {}
      const delData = []
      const items = gridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000526'), { className: 'toast_error' }) // 삭제할 데이터가 없습니다.
        return
      }

      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      for (let i = 0; i < items.length; i++) {
        tmpData.hmex_year = dataProvider.getValue(items[i], 'hmex_year')
        tmpData.hmex_tgt_emp_id = dataProvider.getValue(items[i], 'hmex_tgt_emp_id')
        tmpData.hmex_sn = dataProvider.getValue(items[i], 'hmex_sn')
        delData.push(tmpData)
        this.frmData.delRow = delData
        tmpData = {}
      }
      this.$axios.put(`/api/v1/she/hlth/hmexrslt`, this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.search()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popClose() {},
    // excelExport() {
    //   const excelType = false
    //   const showProgress = false
    //   const indicator = 'visible'
    //   const header = 'visible'
    //   const footer = 'hidden'
    //   // 건강검진 결과 목록
    //   const fileName = this.$t('LB00001091') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

    //   gridView2.exportGrid({
    //     type: 'excel',
    //     target: 'local',
    //     fileName,
    //     showProgress,
    //     progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
    //     indicator,
    //     header,
    //     footer,
    //     compatibility: excelType,
    //     done() {
    //       //
    //     },
    //   })
    // },
  },
}
</script>
<style lang="scss" scoped></style>
