<template>
  <v-dialog v-model="dialog" persistent width="1000px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 공급자 선택 -->
        <v-toolbar-title>{{ $t('LB00000291') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 공급자명 -->
              <div class="label_tit">{{ $t('LB00000293') }}</div>
            </v-col>
            <v-col cols="10">
              <v-text-field
                v-model="schData.vndr_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
        <div class="sch_btn">
          <!-- 초기화 -->
          <v-btn color="" outlined height="36" @click="refresh()">{{ $t('LB00000029') }}</v-btn>
          <!-- 검색 -->
          <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
        </div>
      </div>
      <div class="container_grid">
        <div class="realgrid_container">
          <!-- 공급자 목록 -->
          <div class="sub_title">
            <h3>{{ $t('LB00000294') }}</h3>
          </div>
          <div class="grid_header">
            <div class="grid_header_left">
              <!-- 총 건 -->
              {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
            </div>
            <div>
              <!-- 그리드 버튼 영역 -->
            </div>
          </div>
          <div id="realgrid_pop02" style="width: 100%; height: 281px"></div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 선택 -->
        <v-btn color="primary" depressed width="120" height="40" @click="submit()">{{ $t('LB00000106') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

// let gridView = GridView
// let dataProvider = LocalDataProvider
export default {
  components: {},
  props: {
    mkey: {
      type: String,
      default: 'realgrid_pop02',
    },
  },
  data() {
    return {
      gridView: '',
      dataProvider: '',
      dialog: false,
      valid: true,
      schData: {},
      frmData: {},
      detail: false,
      size: 0,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
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
        header: this.$t('LB00000292'), // 공급자코드
        name: 'vndr_no',
        fieldName: 'vndr_no',
        width: '100',
      },
      {
        header: this.$t('LB00000293'), // 공급자명
        name: 'vndr_nm',
        fieldName: 'vndr_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000295'), // 연락처
        name: 'vndr_phon_no',
        fieldName: 'vndr_phon_no',
        width: '100',
      },
      {
        header: this.$t('LB00000296'), // 최근공급일
        name: 'vndr_dt',
        fieldName: 'vndr_dt',
        width: '100',
      },
    ]

    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    // 그리드 ID를 조회하여 동적으로 수정해주는 부분
    const gridId = document.getElementById('realgrid_pop02')
    gridId.id = this.mkey + 'vndrmroslct'
    this.gridView = new GridView(this.mkey + 'vndrmroslct')
    this.gridView.setDataSource(this.dataProvider)
    this.gridView.setColumns(columns)
    this.gridView.setFooters({ visible: false })

    this.gridView.setStateBar({ visible: false })
    this.gridView.setCheckBar({ visible: true, exclusive: true, width: 50, showAll: false })
    this.gridView.setRowIndicator({ width: 50 })
    this.gridView.editOptions.editable = false
    this.gridView.displayOptions.selectionStyle = 'singleRow'

    this.gridView.setColumnProperty('vndr_no', 'visible', false)

    this.gridView.header.height = 39
    this.gridView.displayOptions.rowHeight = 40
    this.gridView.footer.height = 40
    this.gridView.displayOptions.fitStyle = 'fill'
  },
  updated() {},
  methods: {
    open(mode) {
      this.schData.vndr_type_cd = mode
      this.dialog = true
      this.gridView.refresh()
    },
    refresh() {
      const tempTypeCd = this.schData.vndr_type_cd
      this.schData = {}
      this.schData.vndr_type_cd = tempTypeCd
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.schData = {}
      this.frmData = {}
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-vndr-list`, { params: this.schData })

      this.dataProvider.setRows(rows)
      this.gridView.refresh()
      this.gridView.setTopItem(0)

      this.size = this.dataProvider.getRowCount()
    },
    submit() {
      const rows = this.gridView.getCheckedRows(true)

      const data = {}
      data.vndr_no = this.gridView.getValue(rows, 'vndr_no')
      data.vndr_nm = this.gridView.getValue(rows, 'vndr_nm')
      data.vndr_phon_no = this.gridView.getValue(rows, 'vndr_phon_no')
      data.vndr_addr = this.gridView.getValue(rows, 'vndr_addr')
      data.nat_cd = this.gridView.getValue(rows, 'nat_cd')
      data.vndr_rprs_nm = this.gridView.getValue(rows, 'vndr_rprs_nm')
      data.vndr_coreg_no = this.gridView.getValue(rows, 'vndr_coreg_no')
      data.vndr_url = this.gridView.getValue(rows, 'vndr_url')
      data.vndr_email = this.gridView.getValue(rows, 'vndr_email')
      data.vndr_type_cd = this.gridView.getValue(rows, 'vndr_type_cd')

      console.log(data) // eslint-disable-line no-console

      this.$emit('close', data)
      this.reset()
    },
  },
}
// 그리드 세팅
const fields = [
  {
    fieldName: 'vndr_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_phon_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_dt',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'vndr_addr',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'nat_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_rprs_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_coreg_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_url',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_email',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_type_cd',
    dataType: ValueType.TEXT,
  },
]
</script>
