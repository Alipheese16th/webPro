<template>
  <v-dialog v-model="dialog" persistent width="1000px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 규제 대상 물질 선택 -->
        <v-toolbar-title>{{ $t('LB00000595') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="realgrid_container">
        <!-- 물질목록 -->
        <div class="sub_title">
          <h3>{{ $t('LB00000334') }}</h3>
        </div>
        <div class="grid_header">
          <!-- 총 {{ totalcnt }}건 -->
          <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
          <div>
            <!-- 그리드 버튼 영역 -->
          </div>
        </div>
        <div id="rgltsbstpop" style="width: 100%; height: 281px"></div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
        <!-- 선택 -->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00000106') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = ''
let dataProvider = ''
export default {
  components: {},
  props: {
    mkey: {
      type: String,
      default: 'rgltsbstpop',
    },
  },
  data() {
    return {
      dialog: false,
      valid: true,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      rgltNo: null,
      schData: {},
      frmData: {},
      detail: false,
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
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chk_req_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_type_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_rati',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_note',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000270'), // 물질구분
        name: 'sbst_type_nm',
        fieldName: 'sbst_type_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000394'), // 물질코드
        name: 'sbst_no',
        fieldName: 'sbst_no',
        width: '200',
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_nm',
        fieldName: 'sbst_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000195'), // 함량(%)
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000095'), // 비고
        name: 'sbst_note',
        fieldName: 'sbst_note',
        width: '100',
        styleName: 'left',
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    const gridId = document.getElementById('rgltsbstpop')
    gridId.id = this.mkey
    gridView = new GridView(this.mkey)
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true, exclusive: true, width: 50, height: 40, showAll: false })
    gridView.setRowIndicator({ width: 50 })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    gridView.refresh()
  },
  updated() {},
  methods: {
    open(multiYn, frmData, rgltno) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      this.multiYn = multiYn
      gridView.setCheckBar({ visible: true, exclusive: !this.multiYn, width: 50, height: 40, showAll: false })
      this.dialog = true
      this.rgltNo = rgltno
      this.frmData = frmData
      // dataProvider.setRows(frmData)
      // gridView.refresh()
      // gridView.setTopItem(0)
      // this.totalcnt = dataProvider.getRowCount()
      console.log(frmData) // eslint-disable-line no-console
      this.select()
    },
    async select() {
      const result = await this.$axios.$get(`/api/v1/public/healthcheck`)

      if (result) {
        dataProvider.setRows(this.frmData)
        gridView.refresh()
        gridView.setTopItem(0)
      }
    },
    submit() {
      dataProvider.setRows(this.frmData)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.schData = {}
      this.frmData = {}
    },
    close() {
      this.$emit('close', undefined, this.rgltNo)
      this.reset()
    },
    async returnData() {
      const rows = gridView.getCheckedRows(true)
      if (rows.length < 0) {
        this.$toast.show(this.$t('MS00000167'), { className: 'toast_error', duration: 180000 }) // 선택된 데이터가 없습니다.
        return false
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(dataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData, this.rgltNo)
      this.reset()
    },
  },
}
</script>
