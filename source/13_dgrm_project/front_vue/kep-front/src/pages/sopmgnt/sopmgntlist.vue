<template>
  <div>
    <div class="location">
      <!-- Home > 안전 > SOP 관리 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000931') }}</span>
    </div>
    <div class="work_title">
      <!-- SOP 관리 -->
      <h2>{{ $t('LB00000931') }}</h2>
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
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.sop_bsns_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--SOP명-->
                <div class="label_tit">{{ $t('LB00000932') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.sop_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="select"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.usg_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="useYn"
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
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--SOP 목록-->
        <h3>{{ $t('LB00000933') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="add">{{ $t('LB00000111') }}</v-btn>
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

export default {
  meta: {
    title: 'SOP 관리',
    key(route) {
      return `/sopmgnt/${route.params.catalog}`
    },
  },
  components: {},
  data() {
    return {
      schData: {},
      frmData: {},
      totalcnt: 0,
      codeid: ['SH00000009', 'CO00000009'],
      codes: {
        SH00000009: [{ cd: '', cd_nm: '' }], // 업무구분
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      useYn: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      deleteModify: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000931') // SOP 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
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
        this.codes.SH00000009.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })

        this.createGrid()
        this.select()
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
          fieldName: 'sop_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sop_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sop_bsns_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sop_doc_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sop_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'rglt_rel_sop_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sop_rel_rglt_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'fscr_dept',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'usg_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mode',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000700'), // 회사
          name: 'cmpy_nm',
          fieldName: 'cmpy_nm',
          width: '80',
        },
        {
          header: this.$t('LB00000934'), // 카테고리
          name: 'sop_cat_nm',
          fieldName: 'sop_cat_nm',
          width: '40',
        },
        {
          header: this.$t('LB00000062'), // 업무구분
          name: 'sop_bsns_cl_nm',
          fieldName: 'sop_bsns_cl_nm',
          width: '70',
        },
        {
          header: this.$t('LB00000935'), // 문서번호
          name: 'sop_doc_no',
          fieldName: 'sop_doc_no',
          width: '70',
        },
        {
          header: this.$t('LB00000932'), // SOP명
          name: 'sop_nm',
          fieldName: 'sop_nm',
          width: '200',
          styleName: 'link left',
        },
        {
          header: this.$t('LB00000936'), // 관련법규 유무
          name: 'rglt_rel_sop_yn',
          fieldName: 'rglt_rel_sop_yn',
          width: '50',
        },
        {
          header: this.$t('LB00000937'), // 관련법규명
          name: 'sop_rel_rglt_nm',
          fieldName: 'sop_rel_rglt_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000938'), // 등록부서
          name: 'fscr_dept',
          fieldName: 'fscr_dept',
          width: '70',
        },
        {
          header: this.$t('LB00000939'), // 최종 수정일
          name: 'upt_dt',
          fieldName: 'upt_dt',
          width: '70',
        },
        {
          header: this.$t('LB00000049'), // 사용여부
          name: 'usg_yn',
          fieldName: 'usg_yn',
          width: '50',
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

      gridView.onCellClicked = function (grid, index, clickData) {
        const current = gridView.getCurrent()
        if (current.itemIndex < 0) return
        this.schData.sop_id = dataProvider.getValue(current.dataRow, 'sop_id')
        const user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.deleteModify = true
          }
        })
        if (current.column === 'sop_nm') {
          this.$router.push({
            name:
              (dataProvider.getValue(current.dataRow, 'mode') === 'U' && dataProvider.getValue(current.dataRow, 'fscr_dept') === user.dept_nm) || this.deleteModify
                ? 'sopmgnt-sopmgntdtledt-sopmgntdtledt'
                : 'sopmgnt-sopmgntdtllup-sopmgntdtllup',
            params: {
              schData: this.schData,
              sopmgntdtledt: this.schData.sop_id,
              sopmgntdtllup: this.schData.sop_id,
            },
          })
        }
      }.bind(this)
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/sfty/sopmgnt`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.codChgReturn(user.cmpn_cd)
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
      this.schData.sop_id = ''
      this.$router.push({
        name: `sopmgnt-sopmgntdtledt-sopmgntdtledt`,
        params: {
          schData: this.schData,
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      // SOP 관리
      const fileName = this.$t('LB00000931') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

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
  },
}
</script>
<style lang="scss" scoped></style>
