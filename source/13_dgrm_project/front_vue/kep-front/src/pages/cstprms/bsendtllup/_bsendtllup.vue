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
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--협력사번호-->
                <div class="label_tit">{{ $t('LB00000729') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_id }}</div>
              </v-col>
              <v-col cols="1">
                <!--협력사명-->
                <div class="label_tit essn">{{ $t('LB00000699') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--주소-->
                <div class="label_tit essn">{{ $t('LB00000725') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.otsr_con_bsen_addr }}</div>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30" disabled></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit essn">{{ $t('LB00000409') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.bspr_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--대표자 명-->
                <div class="label_tit essn">{{ $t('LB00000724') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rprv_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--전화번호-->
                <div class="label_tit essn">{{ $t('LB00000723') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_tphn }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <v-container>
      <v-row>
        <v-col cols="8" style="padding: 0">
          <div class="realgrid_container">
            <!--작업자 정보-->
            <div class="sub_title">{{ $t('LB00000722') }}</div>
            <div class="grid_header">
              <!--총 건-->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ oprrTotalcnt }}{{ $t('LB00000040') }}</div>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="4" style="padding: 0">
          <div class="container_detail">
            <!--사업장 교육 이수 정보-->
            <div class="sub_title">{{ $t('LB00000718') }}</div>
            <div class="grid_header">
              <!--총 건-->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ fcstTotalcnt }}{{ $t('LB00000040') }}</div>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <filePopup ref="filepop" mkey1="_bsendtledtatflgrid1" @close="filepopclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import filePopup from '../../common/atflgrid.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '업체 관리',
    key(route) {
      return `/cstprms/cstprms/${route.params.catalog}`
    },
  },
  components: {
    filePopup,
  },
  data() {
    return {
      quillChk: false,
      oprrTotalcnt: 0,
      fcstTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      otsr_con_bsen_id: '',
      frmTempData: '',
      frmData: {
        ckFlag: false,
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        otsr_con_bsen_addr: '',
        usg_yn: '',
        rprv_nm: '',
        bspr_no: '',
        otsr_con_bsen_tphn: '',
        otsr_con_bsen_dtrm_yn: '',
        lsch_nm: '',
      },
      // 작업자정보
      oprrData: {
        chk_gb: '',
        otsr_con_bsen_id: '',
        oprr_id: '',
        oprr_nm: '',
        oprr_tphn: '',
        oprn_cl_cd: '',
        oprn_cl_nm: 0,
        crtf_vld_end_dt: '',
        buil_bsc_sft_trn_fcst_dt: '',
        oprr_dtrm_yn: '',
        oprr_del_yn: '',
        oprr_atfl_no: '',
        oprr_atfl_cnt: 0,
        lsch_nm: '',
        upt_dt: '',
      },
      // 사업장 교육 이수 정보
      fcstData: {
        oprr_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        trn_fcst_sn: '',
        trn_cl_cd: '',
        trn_cl_nm: '',
        trn_fcst_dt: '',
      },
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
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.bsendtllup !== null && this.$route.params.bsendtllup !== undefined) {
      this.frmData.otsr_con_bsen_id = this.$route.params.bsendtllup
      this.otsr_con_bsen_id = this.$route.params.bsendtllup
      this.schData.otsr_con_bsen_id = this.$route.params.bsendtllup
    } else {
      this.frmData.otsr_con_bsen_id = ''
      this.otsr_con_bsen_id = ''
      this.schData.otsr_con_bsen_id = ''
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        usg_yn: '',
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: '_chk',
        dataType: ValueType.BOOLEAN,
      },
      {
        fieldName: 'chk_gb',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'otsr_con_bsen_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_tphn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprn_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crtf_vld_end_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'buil_bsc_sft_trn_fcst_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_dtrm_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_del_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_atfl_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_dt',
        dataType: ValueType.TEXT,
      },
    ]

    const fields2 = [
      {
        fieldName: 'oprr_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_dt',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000748'), // 작업자ID
        name: 'oprr_id',
        fieldName: 'oprr_id',
        width: '100',
      },
      {
        header: this.$t('LB00000701'), // 성명
        name: 'oprr_nm',
        fieldName: 'oprr_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000745'), // 작업구분
        name: 'oprn_cl_nm',
        fieldName: 'oprn_cl_nm',
        width: '80',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000744'), // 자격 유효일
        name: 'crtf_vld_end_dt',
        fieldName: 'crtf_vld_end_dt',
        width: '80',
        styles: {
          datetimeFormat: 'yyyy-MM-dd',
        },
      },
      {
        header: this.$t('LB00000743'), // 건설기초안전교육 이수일
        name: 'buil_bsc_sft_trn_fcst_dt',
        fieldName: 'buil_bsc_sft_trn_fcst_dt',
        width: '100',
        styles: {
          datetimeFormat: 'yyyy-MM-dd',
        },
      },
      {
        header: this.$t('LB00000742'), // 삭제여부
        name: 'oprr_del_yn',
        fieldName: 'oprr_del_yn',
        width: '80',
      },
      {
        header: this.$t('LB00000121'), // 첨부파일
        name: 'oprr_atfl_cnt',
        fieldName: 'oprr_atfl_cnt',
        width: '100',
        button: 'action',
        alwaysShowButton: true,
        editable: false,
      },
    ]

    const columns2 = [
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000741'), // 구분
        name: 'trn_cl_nm',
        fieldName: 'trn_cl_nm',
        width: '80',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000740'), // 교육 이수일
        name: 'trn_fcst_dt',
        fieldName: 'trn_fcst_dt',
        width: '80',
        styles: {
          datetimeFormat: 'yyyy-MM-dd',
        },
      },
    ]
    // 그리드 1 시작
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

    gridView.onCellClicked = function (grid, index, clickData) {
      if (index.itemIndex < 0) return
      const value = gridView.getValue(index.itemIndex, 'oprr_id')
      this.schData.oprr_id = value
      // 사업장 교육 이수 정보 조회
      this.selectFcst()
    }.bind(this)

    gridView.onCellButtonClicked = function (grid, itemIndex, column) {
      this.chkRowVal = itemIndex.dataRow
      if (itemIndex.column === 'oprr_atfl_cnt') {
        const atfileNo = gridView.getValue(itemIndex.itemIndex, 'oprr_atfl_no')
        this.schData.atfile_no = atfileNo
        this.$refs.filepop.open(this.schData, false)
      }
    }.bind(this)

    // 그리드 1 끝

    // 그리드 2 시작
    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
    gridView2.setDataSource(dataProvider2)
    gridView2.setColumns(columns2)
    gridView2.setFooters({ visible: false })
    gridView2.setStateBar({ visible: false })
    gridView2.setCheckBar({ visible: false })
    gridView2.editOptions.editable = false
    gridView2.displayOptions.selectionStyle = 'block'
    gridView2.header.height = 39
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'
    // 그리드 2 끝

    if (this.frmData.otsr_con_bsen_id !== '') {
      this.select()
    }
  },
  methods: {
    async selectFcst() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/otsr-con-bsen-fcst-dtl`, { params: this.schData })
      console.log(data) // eslint-disable-line no-console
      this.fcstData = data.fcst
      dataProvider2.setRows(data.fcst)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.fcstTotalcnt = dataProvider2.getRowCount()
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/otsr-con-bsen-dtl/${this.schData.otsr_con_bsen_id}`)
      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
      } else {
        this.frmData = []
      }
      console.log(data) // eslint-disable-line no-console
      this.oprrData = data.oprr
      dataProvider.setRows(data.oprr)
      gridView.refresh()
      gridView.setTopItem(0)
      this.oprrTotalcnt = dataProvider.getRowCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    filepopclose(returnData) {
      gridView.commit()
      console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        dataProvider.setValue(this.chkRowVal, 'oprr_atfl_no', returnData.atfile_no)
        dataProvider.setValue(this.chkRowVal, 'oprr_atfl_cnt', returnData.file_cnt)
      }
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000951') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 작업자정보

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    goList() {
      this.$router.push({
        name: `cstprms-bsenlist`,
        params: {
          schData: {
            otsr_con_bsen_id: this.tmpSchData.otsr_con_bsen_id,
            otsr_con_bsen_nm: this.tmpSchData.otsr_con_bsen_nm,
            usg_yn: this.tmpSchData.usg_yn,
          },
        },
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
