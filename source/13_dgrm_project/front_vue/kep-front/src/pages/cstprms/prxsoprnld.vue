<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 기존작업 불러오기 -->
        <v-toolbar-title>{{ $t('LB00000978') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="realgrid_container">
          <!-- 기존작업 불러오기 -->
          <div class="sub_title">
            <h3>{{ $t('LB00000978') }}</h3>
          </div>
          <div class="grid_header">
            <!-- 총 {{ totalcnt }}건 -->
            <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
            <div>
              <!-- 그리드 버튼 영역 -->
            </div>
          </div>
          <div id="prxsoprnldpop" style="width: 100%; height: 431px"></div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!--선택-->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00000106') }}</v-btn>
        <!--닫기 -->
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
      default: 'prxsoprnldgrid',
    },
  },
  data() {
    return {
      prxsoprnldgridView: '',
      prxsoprnlddataProvider: '',
      dialog: false,
      expanded: false,
      totalcnt: 0,
      frmData: {},
      searchdatas: {
        con_id: '',
      },
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
        header: this.$t('LB00000878'), // 작업일시
        name: 'con_dt',
        fieldName: 'con_dt',
        width: '200',
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
    ]
    this.prxsoprnlddataProvider = new LocalDataProvider(false)
    this.prxsoprnlddataProvider.setFields(fields)
    const gridId1 = document.getElementById('prxsoprnldpop')
    gridId1.id = this.mkey + 'prxsoprnldslct'
    this.prxsoprnldgridView = new GridView(this.mkey + 'prxsoprnldslct')
    this.prxsoprnldgridView.setDataSource(this.prxsoprnlddataProvider)
    this.prxsoprnldgridView.setColumns(columns)
    this.prxsoprnldgridView.setFooters({ visible: false })
    this.prxsoprnldgridView.setStateBar({ visible: false })
    this.prxsoprnldgridView.setCheckBar({ visible: false })
    this.prxsoprnldgridView.editOptions.editable = false
    this.prxsoprnldgridView.displayOptions.selectionStyle = 'singleRow'
    this.prxsoprnldgridView.displayOptions.fitStyle = 'fill'
    this.prxsoprnldgridView.displayOptions.rowHeight = 40
    this.prxsoprnldgridView.header.height = 39
    this.prxsoprnldgridView.footer.height = 40

    this.prxsoprnldgridView.onCurrentRowChanged = function (grid, oldRow, newRow) {
      // eslint-disable-next-line no-console
      console.log('oldRow => ', oldRow, 'newRow => ', newRow)
      grid.checkRow(newRow, true, true)
    }
  },
  methods: {
    open(frmData) {
      this.searchdatas = frmData
      if (frmData.con_id !== null && frmData.con_id !== undefined) {
        this.searchdatas.con_id = frmData.con_id
      }
      this.prxsoprnldgridView.setCheckBar({ visible: true, exclusive: true })
      this.dialog = true
      this.prxsoprnldgridView.refresh()
      this.searchPrxs()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
      this.prxsoprnlddataProvider.setFields(fields)
      this.prxsoprnldgridView.refresh()
    },
    onClearClicked() {
      this.searchdatas.con_id = ''
      this.prxsoprnldgridView.refresh()
    },
    async searchPrxs() {
      const rows = await this.$axios.$get(`/api/v1/she/cstprms/con-oprn-prms-be-data`, { params: this.searchdatas })
      this.prxsoprnlddataProvider.setRows(rows)
      this.prxsoprnldgridView.refresh()
      this.prxsoprnldgridView.setTopItem(0)
      this.totalcnt = this.prxsoprnlddataProvider.getRowCount()

      this.prxsoprnldgridView.checkRow(0, true)
    },
    async returnData() {
      this.prxsoprnldgridView.commit()
      const items = this.prxsoprnldgridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }
      const rows = this.prxsoprnldgridView.getCheckedRows(true)
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.prxsoprnlddataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
    },
  },
}

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
    fieldName: 'con_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'con_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'con_prms_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'con_oprn_type_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'con_dt',
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
]
</script>
