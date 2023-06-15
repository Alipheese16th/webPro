<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 협력사 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000732') }}</span>
    </div>
    <div class="work_title">
      <!-- 협력사 관리 -->
      <h2>{{ $t('LB00000732') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--협력사번호-->
                <div class="label_tit">{{ $t('LB00000729') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_id"
                  v-model="schData.otsr_con_bsen_id"
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
                <!--협력사명-->
                <div class="label_tit">{{ $t('LB00000699') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_nm"
                  v-model="schData.otsr_con_bsen_nm"
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
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="usg_yn"
                  v-model="schData.usg_yn"
                  label="ALL"
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
      <div class="sub_title">
        <!--협력사 목록-->
        <h3>{{ $t('LB00000946') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" depressed height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
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
    title: '업체 관리',
    key(route) {
      return `/cstprms/cstprms/${route.params.catalog}`
    },
  },
  components: {},
  fetch() {
    this.select()
  },
  data() {
    return {
      isSysAdmin: false,
      schData: {
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        usg_yn: '',
      },
      frmData: {
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        rprv_nm: '',
        oprr_cnt: '',
        usg_yn: '',
        dept_cd: '',
        lsch_nm: '',
        lsch_dt: '',
      },
      tempData: {},
      totalcnt: 0,
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000732') // 협력사 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    console.log(user) // eslint-disable-line no-console

    // 시스템관리자 그룹 사용자인지 체크
    //  user.roleTypeCds.forEach((i) => {
    //    if (i === 'M01' || i === 'S01' || i === 'C01') {
    //      this.isSysAdmin = true
    //    }
    //  })
    this.isSysAdmin = true
    this.tempData.username = this.$store.getters['auth/getUsername']
    this.tempData.userempno = user.emp_no

    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.usg_yn = 'Y'
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'otsr_con_bsen_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'otsr_con_bsen_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rprv_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usg_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_dt',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00000729'), // 협력사번호
        name: 'otsr_con_bsen_id',
        fieldName: 'otsr_con_bsen_id',
        width: '150',
      },
      {
        header: this.$t('LB00000699'), // 협력사명
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '150',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000948'), // 대표자명
        name: 'rprv_nm',
        fieldName: 'rprv_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000947'), // 작업자 수
        name: 'oprr_cnt',
        fieldName: 'oprr_cnt',
        width: '70',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'usg_yn',
        fieldName: 'usg_yn',
        width: '70',
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'lsch_nm',
        fieldName: 'lsch_nm',
        width: '70',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'lsch_dt',
        fieldName: 'lsch_dt',
        width: '100',
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

    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.fitStyle = 'fill'
    gridView.displayOptions.rowHeight = 40

    gridView.header.height = 39
    gridView.footer.height = 40
    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = function (grid, clickData) {
      // alert('onCellClicked: ' + JSON.stringify(clickData))
      if (clickData.cellType === 'gridEmpty' || clickData.cellType === 'header') return
      const current = gridView.getCurrent()

      if (current.column === 'otsr_con_bsen_nm') {
        const bsenId = gridView.getValue(current.dataRow, 'otsr_con_bsen_id')
        const deptCd = gridView.getValue(current.dataRow, 'dept_cd')
        const user = this.$store.getters['auth/getUser']
        if (this.isSysAdmin === true || deptCd === user.dept_cd) {
          this.$router.push({
            name: `cstprms-bsendtledt-bsendtledt`,
            params: {
              bsendtledt: bsenId,
              otsr_con_bsen_id: bsenId,
              schData: {
                otsr_con_bsen_id: bsenId,
              },
              tmpSchData: {
                otsr_con_bsen_id: this.schData.otsr_con_bsen_id,
                otsr_con_bsen_nm: this.schData.otsr_con_bsen_nm,
                usg_yn: this.schData.usg_yn,
              },
            },
          })
        } else {
          this.$router.push({
            name: `cstprms-bsendtllup-bsendtllup`,
            params: {
              bsendtllup: bsenId,
              otsr_con_bsen_id: bsenId,
              schData: {
                otsr_con_bsen_id: bsenId,
              },
              tmpSchData: {
                otsr_con_bsen_id: this.schData.otsr_con_bsen_id,
                otsr_con_bsen_nm: this.schData.otsr_con_bsen_nm,
                usg_yn: this.schData.usg_yn,
              },
            },
          })
        }
      }
    }.bind(this)
  },
  methods: {
    openNew() {
      this.$router.push({
        name: `cstprms-bsendtledt-bsendtledt`,
        params: {
          bsendtledt: null,
          otsr_con_bsen_id: null,
          schData: {
            otsr_con_bsen_id: '',
          },
          tmpSchData: {
            otsr_con_bsen_id: this.schData.otsr_con_bsen_id,
            otsr_con_bsen_nm: this.schData.otsr_con_bsen_nm,
            usg_yn: this.schData.usg_yn,
          },
        },
      })
    },
    saveDate(date, gubun) {
      const dd = date.substring(0, 4)
      this.schData.wem_year = dd
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/otsr-con-bsen-list`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    schReset() {
      this.schData = {
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        usg_yn: 'Y',
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000949') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 협력사관리

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
