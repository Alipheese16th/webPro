<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 당일작업 작업인원 -->
        <v-toolbar-title>{{ $t('LB00000975') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="realgrid_container">
          <!-- 당일작업 작업인원 -->
          <div class="sub_title">
            <h3>{{ $t('LB00000975') }}</h3>
          </div>
          <div class="grid_header">
            <!-- 총 {{ totalcnt }}건 -->
            <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
            <div>
              <!-- 그리드 버튼 영역 -->
            </div>
          </div>
          <div id="curroprrpop" style="width: 100%; height: 420px"></div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 엑셀 다운로드 -->
        <v-btn outlined width="120" height="40" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

export default {
  components: {},
  props: {
    mkey: {
      type: String,
      default: 'curroprrgrid',
    },
  },
  data() {
    return {
      curroprrgridView: '',
      curroprrdataProvider: '',
      dialog: false,
      schData: {},
      frmData: {},
      totalcnt: 0,
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'con_prms_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_id',
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
        fieldName: 'con_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_wkpl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_oprn_dtl_loc_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'con_rq_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'otsr_con_bsen_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprr_nm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000636'), // 작업명
        name: 'con_nm',
        fieldName: 'con_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000876'), // 허가번호
        name: 'con_prms_id',
        fieldName: 'con_prms_id',
        width: '150',
      },
      {
        header: this.$t('LB00000880'), // 작업종류
        name: 'con_oprn_type_nm',
        fieldName: 'con_oprn_type_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000888'), // 작업위치
        name: 'con_oprn_wkpl_loc_nm',
        fieldName: 'con_oprn_wkpl_loc_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000681'), // 상세위치
        name: 'con_oprn_dtl_loc_nm',
        fieldName: 'con_oprn_dtl_loc_nm',
        width: '150',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000959'), // 작업요청자
        name: 'con_rq_emp_nm',
        fieldName: 'con_rq_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000958'), // 협력사
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000976'), // 작업자번호
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
    ]
    this.curroprrdataProvider = new LocalDataProvider(false)
    this.curroprrdataProvider.setFields(fields)
    const gridId = document.getElementById('curroprrpop')
    gridId.id = this.mkey + 'curroprrslct'
    this.curroprrgridView = new GridView(this.mkey + 'curroprrslct')
    this.curroprrgridView.setDataSource(this.curroprrdataProvider)
    this.curroprrgridView.setColumns(columns)
    this.curroprrgridView.setFooters({ visible: false })

    this.curroprrgridView.setStateBar({ visible: false })
    this.curroprrgridView.setCheckBar({ visible: false })
    this.curroprrgridView.setRowIndicator({ width: 50 })
    this.curroprrgridView.editOptions.editable = false
    this.curroprrgridView.displayOptions.selectionStyle = 'block'

    this.curroprrgridView.header.height = 39
    this.curroprrgridView.displayOptions.rowHeight = 40
    this.curroprrgridView.footer.height = 40
    this.curroprrgridView.displayOptions.fitStyle = 'fill'

    this.curroprrgridView.setColumnProperty('id', 'visible', false)

    this.curroprrgridView.refresh()
  },
  methods: {
    open() {
      this.dialog = true
      this.select()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-curr-oprr`, { params: this.schData })
      this.curroprrdataProvider.setRows(rows)
      this.curroprrgridView.refresh()
      this.curroprrgridView.setTopItem(0)
      this.totalcnt = this.curroprrgridView.getItemCount()
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.frmData = {}
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000977') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 당일작업작원인원

      this.curroprrgridView.exportGrid({
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
  },
}
</script>
