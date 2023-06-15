<template>
  <div>
    <div class="location">
      <h1>사업장정보</h1>
      <!-- Home > 사업장 정보 > 사업장 관리 -->
      <span>
        Home > 사업장 정보 > <b>{{ $t('LB00001201') }}</b>
      </span>
    </div>
    <div class="work_title">
      <!-- 사업장 관리 -->
      <h2>{{ $t('LB00001201') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">회사</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="cmpy_cd" placeholder="선택하세요."></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장명-->
                <div class="label_tit">{{ $t('LB00001217') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field placeholder="입력하세요."></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn class="btn_reset" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn class="btn_search" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 목록-->
        <h3>{{ $t('LB00001216') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }} <b>{{ totalcnt }}</b>
          {{ $t('LB00000040') }}
        </div>
        <div class="grid_header_right">
          <!--엑셀 다운로드-->
          <v-btn class="btn_grid_border" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn class="btn_grid_solid" @click="openNew">{{ $t('LB00000111') }}</v-btn>
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
    title: '사업장 관리',
    key(route) {
      return `/stndinfr/stndinfr/${route.params.catalog}`
    },
  },
  components: {},
  data() {
    return {
      isAdmin: false,
      totalcnt: 0,
      schData: {
        wkpl_id: '',
        wkpl_nm: '',
        use_yn: '',
        cmpy_cd: '',
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['CO00000009'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001201') // 사업장 관리
    },
  },
  created() {},
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    // 그리드 세팅
    const fields = [
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'btn_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'std_ind_cat_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'std_ind_cat_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_addr',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_tphn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sfhe_mgnt_rspn_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'clgt_rspn_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sfhe_mgnt_rspn_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'clgt_rspn_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_work_prsn_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbcn_prsn_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_atht_grd_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'apal_re_atht_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_emp_no',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00001217'), // 사업장명
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000909'), // 회사명
        name: 'cmpy_nm',
        fieldName: 'cmpy_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: '선임현황', // 선임현황
        name: 'btn_nm',
        fieldName: 'btn_nm',
        width: '60',
        renderer: {
          type: 'button',
        },
      },
      {
        header: this.$t('LB00001224'), // 표준산업분류
        name: 'std_ind_cat_nm',
        fieldName: 'std_ind_cat_nm',
        width: '70',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001196'), // 주요업무
        name: 'wkpl_cl_nm',
        fieldName: 'wkpl_cl_nm',
        width: '70',
      },
      {
        header: this.$t('LB00000725'), // 주소
        name: 'wkpl_addr',
        fieldName: 'wkpl_addr',
        width: '70',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000723'), // 전화번호
        name: 'wkpl_tphn',
        fieldName: 'wkpl_tphn',
        width: '70',
      },
      {
        header: this.$t('LB00001223'), // 안전보건관리책임자
        name: 'sfhe_mgnt_rspn_emp_nm',
        fieldName: 'sfhe_mgnt_rspn_emp_nm',
        width: '70',
      },
      {
        header: this.$t('LB00001222'), // 총괄책임자
        name: 'clgt_rspn_emp_nm',
        fieldName: 'clgt_rspn_emp_nm',
        width: '70',
      },
      {
        header: this.$t('LB00001221'), // 내부
        name: 'wkpl_work_prsn_cnt',
        fieldName: 'wkpl_work_prsn_cnt',
        width: '70',
        styleName: 'right',
      },
      {
        header: this.$t('LB00001220'), // 도급
        name: 'sbcn_prsn_cnt',
        fieldName: 'sbcn_prsn_cnt',
        width: '70',
        styleName: 'right',
      },
      {
        header: this.$t('LB00001219'), // PSM등급
        name: 'apal_atht_grd_cd',
        fieldName: 'apal_atht_grd_cd',
        width: '70',
      },
      {
        header: this.$t('LB00001218'), // 인증만료일(ISO45001)
        name: 'apal_re_atht_dt',
        fieldName: 'apal_re_atht_dt',
        width: '70',
      },
    ]
    const headergroup = [
      'wkpl_nm', // 사업장명
      'cmpy_nm', // 회사명
      'btn_nm', // 선임현황
      'std_ind_cat_nm', // 표준산업분류
      'wkpl_cl_nm', // 주요업무
      'wkpl_addr', // 주소
      'wkpl_tphn', // 전화번호
      'sfhe_mgnt_rspn_emp_nm', // 안전보건관리책임자
      'clgt_rspn_emp_nm', // 총괄책임자
      {
        name: 'companyGroup1',
        direction: 'horizontal',
        items: ['wkpl_work_prsn_cnt', 'sbcn_prsn_cnt'], // 그룹에 들어갈 컬럼
        header: {
          text: this.$t('LB00001225'), // 근무인원(명)
        },
      },
      'apal_atht_grd_cd', // PSM등급
      'apal_re_atht_dt', // 인증만료일(ISO45001)
    ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumnLayout(headergroup)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.fitStyle = 'fill'

    gridView.header.height = 70
    gridView.footer.height = 40

    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellItemClicked = function (grid, index, clickData) {
      console.log('=> onCellItemClicked: ' + index) // eslint-disable-line no-console
      console.log('=> onCellItemClicked: ' + JSON.stringify(clickData)) // eslint-disable-line no-console
      if (clickData.column === 'btn_nm') {
        const cmpyCd = grid.getValue(index.itemIndex, 'cmpy_cd')
        const wkplId = grid.getValue(index.itemIndex, 'wkpl_id')
        this.$router.push({
          name: `stndinfr-wkplelcmgntdtledt-wkplelcmgntdtledt`,
          params: {
            cmpy_cd: cmpyCd,
            wkpl_id: wkplId,
            schData: {
              cmpy_cd: cmpyCd,
            },
          },
        })
      }
    }.bind(this)

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = grid.getCurrent()
      if (index.cellType === 'header') return // 그리드 헤더 클릭은 제외

      if (current.column === 'wkpl_nm') {
        const wkplId = grid.getValue(index.itemIndex, 'wkpl_id')
        const user = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        user.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdmin = true
          }
        })

        if (wkplId === user.wkpl_id || this.isAdmin) {
          this.$router.push({
            name: `stndinfr-wkplmgntdtledt-wkplmgntdtledt`,
            params: {
              wkplmgntdtledt: wkplId,
              schData: {
                wkpl_id: wkplId,
              },
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                wkpl_nm: this.schData.wkpl_nm,
                use_yn: this.schData.use_yn,
                cmpy_cd: this.schData.cmpy_cd,
              },
            },
          })
        } else {
          this.$router.push({
            name: `stndinfr-wkplmgntdtllup-wkplmgntdtllup`,
            params: {
              wkplmgntdtllup: wkplId,
              schData: {
                wkpl_id: wkplId,
              },
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                wkpl_nm: this.schData.wkpl_nm,
                use_yn: this.schData.use_yn,
                cmpy_cd: this.schData.cmpy_cd,
              },
            },
          })
        }
      }
    }.bind(this)
  },
  methods: {
    async select() {
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-mgnt-list`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData.wkpl_id = ''
      this.schData.wkpl_nm = ''
      this.schData.use_yn = ''
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    openNew() {
      this.$router.push({
        name: `stndinfr-wkplmgntdtledt-wkplmgntdtledt`,
        params: {
          wkplmgntdtledt: null,
          schData: {},
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
            wkpl_nm: this.schData.wkpl_nm,
            use_yn: this.schData.use_yn,
            cmpy_cd: this.schData.cmpy_cd,
          },
        },
      })
    },
    schReset() {
      this.schData = {
        wkpl_id: '',
        wkpl_nm: '',
        use_yn: '',
        cmpy_cd: '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001226') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 사업장관리

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
