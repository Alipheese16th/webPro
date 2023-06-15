<template>
  <v-dialog v-model="dialog" persistent width="1000px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 규제검토 적용 제외 사유 선택(필수) -->
        <v-toolbar-title>{{ $t('LB00001288') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="realgrid_container">
        <div id="rgltexceptpop" style="width: 100%; height: 281px"></div>
      </div>
      <!-- 적용제외 여부 문의 (안전환경지원팀 함종호) -->
      <div style="padding: 15px 0px 0 30px; font-size: 14px; font-weight: bold">{{ $t('LB00001289') }}</div>
      <v-card-actions class="pop_btn">
        <!-- 제외검토 요청 -->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00001290') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
      <!-- [상기 내용은 검토 후 승인됩니다.] -->
      <div style="text-align: center; padding: 0px 0px 20px 0px; font-size: 13px; font-weight: bold">[{{ $t('LB00001291') }}]</div>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = ''
let dataProvider = ''

export default {
  components: {},
  data() {
    return {
      dialog: false,
      frmData: {},
      codeid: [
        'CH00000032', // 적용제외 사유
      ],
      codes: {
        CH00000032: [{ cd: '', cd_nm: '' }], // 적용제외 사유
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
    this.user = this.$store.getters['auth/getUser']
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cd_nm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00001292'), // 적용제외 사유
        name: 'cd_nm',
        fieldName: 'cd_nm',
        width: '250',
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const cd = grid.getValue(dataCell.index.itemIndex, 'cd')
          if (cd === '5') {
            return {
              editable: true,
            }
          }
        },
      },
    ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('rgltexceptpop')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setRowIndicator({ visible: false })
    gridView.setCheckBar({ exclusive: true, width: 50 })

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'
    gridView.displayOptions.selectionStyle = 'block'
    gridView.editOptions.editable = false

    gridView.onCellClicked = function (grid, index, clickData) {
      gridView.commit()
      const cd = gridView.getValue(index.itemIndex, 'cd')
      if (cd === '5') {
        dataProvider.setValue(index.itemIndex, 'cd_nm', '')
      }
    }
  },
  methods: {
    open(frmData) {
      this.dialog = true
      this.frmData = frmData
      this.select()
    },
    async select() {
      await this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
        this.codes = res
      })

      dataProvider.setRows(this.codes.CH00000032)
      gridView.refresh()
      gridView.setTopItem(0)
    },
    async returnData() {
      gridView.commit()

      const row = gridView.getCheckedRows()

      // Validation 체크
      if (!this.validation()) {
        return
      }

      const res = await this.$confirm(this.$t('MS00000646')) // 제외검토 요청을 하시겠습니까?
      if (!res) {
        return
      }

      const returnData = {}
      returnData.rgln_exmt_excl_rq_rsn_cd = dataProvider.getValue(row[0], 'cd')
      returnData.chmt_mtra_rgln_exmt_excl_txt = dataProvider.getValue(row[0], 'cd_nm')
      returnData.exc_confirm_yn = res

      this.$emit('close', returnData)
      this.reset()
    },
    validation() {
      const rows = gridView.getCheckedRows()

      if (rows.length < 1) {
        this.$toast.show(this.$t('MS00000647'), { className: 'toast_error' }) // 적용제외 사유를 선택해주세요.
        return false
      }

      if (rows[0] === 4) {
        if (this.isEmpty(dataProvider.getValue(rows[0], 'cd_nm'))) {
          gridView.setCurrent({ dataRow: rows[0], fieldName: 'cd_nm' })
          this.$toast.show(this.$t('MS00000648'), { className: 'toast_error' }) // 적용제외 사유를 입력해주세요.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(rows[0], 'cd_nm')) && dataProvider.getValue(rows[0], 'cd_nm').length > 1000) {
          gridView.setCurrent({ dataRow: rows[0], fieldName: 'cd_nm' })
          this.$toast.show(this.$t('MS00000649'), { className: 'toast_error' }) // 적용제외 사유는 1000자 이내로 입력해주세요.
          return false
        }
      }

      return true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    reset() {
      this.dialog = false
      dataProvider.clearRows()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
  },
}
</script>
