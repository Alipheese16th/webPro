<template>
  <v-dialog v-model="dialog" persistent width="800px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 자재 선택 -->
        <v-toolbar-title>{{ $t('LB00000134') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 자재코드 -->
              <div class="label_tit">{{ $t('LB00000024') }}</div>
            </v-col>
            <v-col cols="4">
              <v-text-field
                v-model="schData.mtrl_no"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
            <v-col cols="2">
              <!-- 자재명 -->
              <div class="label_tit">{{ $t('LB00000025') }}</div>
            </v-col>
            <v-col cols="4">
              <v-text-field
                v-model="schData.mtrl_nm"
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
          <!-- 자재 목록 -->
          <div class="sub_title">
            <h3>{{ $t('LB00000133') }}</h3>
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
          <div id="realgrid_pop01" style="width: 100%; height: 281px"></div>
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

// let gridView = ''
// let dataProvider = ''
export default {
  components: {},
  props: {
    mkey: {
      type: String,
      default: 'realgrid_pop01',
    },
  },
  data() {
    return {
      gridView: '',
      dataProvider: '',
      dialog: false,
      schData: {},
      frmData: {},
      chkYnAll: '',
      size: 0,
      detail: false,
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
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
      },
      {
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '100',
      },
    ]
    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    // 그리드 ID를 조회하여 동적으로 수정해주는 부분
    const gridId = document.getElementById('realgrid_pop01')
    gridId.id = this.mkey + 'mtrlslct'
    this.gridView = new GridView(this.mkey + 'mtrlslct')
    this.gridView.setDataSource(this.dataProvider)
    this.gridView.setColumns(columns)
    this.gridView.setFooters({ visible: false })

    this.gridView.setStateBar({ visible: false })
    this.gridView.setCheckBar({ visible: true, exclusive: true, width: 50, showAll: false })
    this.gridView.setRowIndicator({ width: 50 })
    this.gridView.editOptions.editable = false
    this.gridView.displayOptions.selectionStyle = 'block'

    this.gridView.header.height = 39
    this.gridView.displayOptions.rowHeight = 40
    this.gridView.footer.height = 40
    this.gridView.displayOptions.fitStyle = 'fill'
  },
  updated() {},
  methods: {
    open(detail, chkYnAll) {
      this.detail = detail
      this.chkYnAll = chkYnAll
      this.dialog = true
      this.gridView.refresh()
    },
    async select() {
      if (this.chkYnAll === 'Y') {
        this.schData.chkYnAll = 'Y'
      } else if (this.chkYnAll === 'N') {
        this.schData.chkYnAll = 'N'
      } else {
        this.schData.chkYnAll = 'A'
      }

      const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-mtrl-list`, { params: this.schData })

      this.dataProvider.setRows(rows)
      this.gridView.refresh()
      this.gridView.setTopItem(0)

      this.size = this.dataProvider.getRowCount()
    },
    refresh() {
      this.schData = {}
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
    submit() {
      const rows = this.gridView.getCheckedRows(true)

      const mtrlNo = this.gridView.getValue(rows, 'mtrl_no')

      this.$emit('close', mtrlNo)
      this.reset()
    },
  },
}
// 그리드 세팅
const fields = [
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
  },
]
</script>
