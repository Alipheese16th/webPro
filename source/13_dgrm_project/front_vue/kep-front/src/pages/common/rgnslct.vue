<template>
  <v-dialog v-model="dialog" persistent width="800px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 지역도 선택 -->
        <v-toolbar-title>{{ $t('LB00000691') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row style="padding-bottom: 10px !important">
            <v-col v-show="iswkpl" cols="2">
              <!-- 사업장 -->
              <div class="label_tit">{{ $t('LB00000021') }}</div>
            </v-col>
            <v-col v-show="iswkpl" cols="2">
              <v-select
                ref="wkpl_id"
                v-model="schData.wkpl_id"
                label="ALL"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.ST00000002"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
                @change="wkplChng()"
              ></v-select>
            </v-col>
            <v-col cols="2">
              <!-- 건물 -->
              <div class="label_tit">{{ $t('LB00000692') }}</div>
            </v-col>
            <v-col cols="2">
              <v-select
                ref="wkpl_bud_id"
                v-model="schData.wkpl_bud_id"
                label="ALL"
                item-text="cd_nm"
                item-value="cd"
                :items="buldCodes"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
                @change="buldChng()"
              ></v-select>
            </v-col>
            <v-col cols="2">
              <!-- 층 -->
              <div class="label_tit">{{ $t('LB00000693') }}</div>
            </v-col>
            <v-col cols="2">
              <v-select
                ref="wkpl_bud_id"
                v-model="schData.wkpl_flo_id"
                label="ALL"
                item-text="cd_nm"
                item-value="cd"
                :items="floCodes"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
              ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!--지역검색-->
              <div class="label_tit">{{ $t('LB00001319') }}</div>
            </v-col>
            <v-col cols="6">
              <v-text-field
                ref="upp_incl_wkpl_loc_nm"
                v-model="schData.upp_incl_wkpl_loc_nm"
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
      <div class="realgrid_container">
        <!-- 자재 목록 -->
        <div class="sub_title">
          <h3>{{ $t('LB00000694') }}</h3>
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
        <div id="rgnslctpop" style="width: 100%; height: 281px"></div>
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
      default: 'rgnslctpop',
    },
    iswkpl: {
      type: Boolean,
      default: true,
    },
    wkplid: {
      type: String,
      default: '',
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
      codeid: ['ST00000002'],
      rgnCodes: '',
      buldCodes: [{ cd: '', cd_nm: 'ALL' }],
      floCodes: [{ cd: '', cd_nm: 'ALL' }],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
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
  created() {},
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_loc_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upp_incl_wkpl_loc_nm',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000710'), // 지역도ID
        name: 'wkpl_loc_id',
        fieldName: 'wkpl_loc_id',
        width: '80',
      },
      {
        header: this.$t('LB00000695'), // 지역도명
        name: 'upp_incl_wkpl_loc_nm',
        fieldName: 'upp_incl_wkpl_loc_nm',
        width: '80',
      },
    ]
    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    // 그리드 ID를 조회하여 동적으로 수정해주는 부분
    const gridId = document.getElementById('rgnslctpop')
    gridId.id = this.mkey + 'rgnslct'
    this.gridView = new GridView(this.mkey + 'rgnslct')
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

    this.gridView.setColumnProperty('wkpl_loc_id', 'visible', false)

    this.getComCod()
    this.getRgnMgntList()
    this.gridView.onCurrentRowChanged = function (grid, oldRow, newRow) {
      // eslint-disable-next-line no-console
      console.log('oldRow => ', oldRow, 'newRow => ', newRow)
      grid.checkRow(newRow, true, true)
    }
    this.gridView.onCellDblClicked = function () {
      this.submit()
    }.bind(this)
  },
  updated() {},
  methods: {
    getComCod() {
      this.$axios
        .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
        .then((res) => {
          this.codes = res
          this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    getRgnMgntList() {
      this.$axios
        .$get(`/api/v1/st/stndinfr/rgn-mgnt-cd`, { params: this.codeid })
        .then((res) => {
          this.rgnCodes = res
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    open() {
      this.dialog = true
      if (!this.iswkpl) {
        this.schData.wkpl_id = this.wkplid
      }
      this.gridView.refresh()
      this.select()
      this.wkplChng()
      this.buldChng()
    },
    wkplChng() {
      this.buldCodes = [{ cd: '', cd_nm: '' }]
      this.buldCodes.unshift({ cd: '', cd_nm: 'ALL' })
      this.rgnCodes.forEach((i) => {
        if (i.wkpl_id === this.schData.wkpl_id && this.isEmpty(i.upp_wkpl_loc_id)) {
          // 건물이며 사업장ID가 같은 값
          this.buldCodes.push({ cd: i.cd, cd_nm: i.cd_nm })
        }
      })
    },
    buldChng() {
      this.floCodes = [{ cd: '', cd_nm: '' }]
      this.floCodes.unshift({ cd: '', cd_nm: 'ALL' })
      this.rgnCodes.forEach((i) => {
        if (i.upp_wkpl_loc_id === this.schData.wkpl_bud_id && !this.isEmpty(i.upp_wkpl_loc_id)) {
          // 건물이며 사업장ID가 같은 값
          this.floCodes.push({ cd: i.cd, cd_nm: i.cd_nm })
        }
      })
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/rgnmgnt-pop-list`, { params: this.schData })

      this.dataProvider.setRows(rows)
      this.gridView.refresh()
      this.gridView.setTopItem(0)

      this.size = this.dataProvider.getRowCount()
    },
    refresh() {
      if (!this.iswkpl) {
        this.schData = {}
        this.schData.wkpl_id = this.wkplid
      } else {
        this.schData = {}
      }
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.schData = {}
      this.frmData = {}
      this.dataProvider.clearRows()
    },
    submit() {
      const rows = this.gridView.getCheckedRows(true)
      const locId = this.dataProvider.getValue(rows, 'wkpl_loc_id')
      const locNm = this.dataProvider.getValue(rows, 'upp_incl_wkpl_loc_nm')

      const value = { wkpl_loc_id: locId, upp_incl_wkpl_loc_nm: locNm }

      this.$emit('close', value)
      this.reset()
    },
  },
}
</script>
