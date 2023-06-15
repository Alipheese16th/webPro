<template>
  <v-dialog v-model="dialog" persistent width="1000px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 물질선택 -->
        <v-toolbar-title>{{ $t('LB00000346') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!--CAS No. 또는 물질명-->
              <div class="label_tit">{{ $t('LB00000594') }}</div>
            </v-col>
            <v-col cols="10">
              <v-text-field
                v-model="schData.sbst_nm"
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
          <div id="realgrid_pop01" style="width: 100%; height: 281px"></div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 선택 -->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00000106') }}</v-btn>
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
      valid: true,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      schData: {
        sbst_nm: '',
      },
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
        fieldName: 'sbst_no',
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
        fieldName: 'sbst_ko_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_en_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ke_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_yn',
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
        fieldName: 'kyin_yn',
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
      /* 
      {
        header: this.$t('LB00000317'), // 물질번호
        name: '',
        fieldName: 'sbst_no',
        width: '100',
      },
      */
      {
        header: 'CAS No.',
        name: '',
        fieldName: 'cas_no',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: '',
        fieldName: 'sbst_nm',
        width: '300',
        styleName: 'left',
      },
    ]

    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    const gridId = document.getElementById('realgrid_pop01')
    gridId.id = this.mkey
    this.gridView = new GridView(this.mkey)
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

    this.gridView.setColumnProperty('id', 'visible', false)
  },
  updated() {},
  methods: {
    open(frmData, multiYn) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      if (!this.isEmpty(frmData)) {
        this.schData.sbst_nm = frmData.sbst_nm
      }
      this.multiYn = multiYn
      this.dialog = true
      this.gridView.refresh()
    },
    async select() {
      if (this.isEmpty(this.schData.sbst_nm)) {
        this.$toast.show(this.$t('MS00000009'), { className: 'toast_error' }) // 물질명 또는 CASNO를 입력해주세요.
      } else {
        const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-sbst-list`, { params: this.schData })

        this.dataProvider.setRows(rows)
        this.gridView.refresh()
        this.gridView.setTopItem(0)
        this.totalcnt = this.dataProvider.getRowCount()
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    refresh() {
      this.schData = {}
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.schData = {}
    },
    async returnData() {
      const rows = this.gridView.getCheckedRows(true)
      if (rows.length < 1) {
        this.$toast.show(this.$t('MS00000167'), { className: 'toast_error' }) // 선택된 데이터가 없습니다.
        return false
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.dataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
</script>
