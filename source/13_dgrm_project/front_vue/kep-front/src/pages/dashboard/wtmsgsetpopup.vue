<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="900px">
    <v-card class="container_pop" style="height: 800px !important">
      <v-toolbar flat class="mb-6">
        <!--기상정보 주의사항-->
        <v-toolbar-title>{{ $t('주의사항 등록') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="12">
              <div class="grid_header">
                <div class="grid_header_left">{{ $t('기상정보') }}</div>
                <div>
                  <!--추가-->
                  <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
                  <!--삭제-->
                  <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
                </div>
              </div>
              <div id="wtgrid" style="height: 250px"></div>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <div class="grid_header">
                <div class="grid_header_left">{{ $t('기상특보') }}</div>
                <div>
                  <!--추가-->
                  <v-btn color="" outlined height="30" @click="add2">{{ $t('LB00000047') }}</v-btn>
                  <!--삭제-->
                  <v-btn color="" outlined height="30" @click="del2">{{ $t('LB00000046') }}</v-btn>
                </div>
              </div>
              <div id="wtgrid2" style="height: 240px"></div>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!--저장-->
        <v-btn color="primary" depressed width="120" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
export default {
  data() {
    return {
      wtGridView: '',
      wtDataProvider: '',
      wtGridView2: '',
      wtDataProvider2: '',
      dialog: false,
      schData: {},
      frmData: {},
      codeid: ['ST00000024', 'ST00000025', 'ST00000026', 'ST00000027'],
      selST00000024: {
        cd: [],
        cd_nm: [],
      },
      selST00000025: {
        cd: [],
        cd_nm: [],
      },
      selST00000026: {
        cd: [],
        cd_nm: [],
      },
      selST00000027: {
        cd: [],
        cd_nm: [],
      },
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.selST00000024.cd.push('')
      this.selST00000024.cd_nm.push('SELECT')
      res.ST00000024.forEach((i) => {
        this.selST00000024.cd.push(i.cd)
        this.selST00000024.cd_nm.push(i.cd_nm)
      })

      this.selST00000025.cd.push('')
      this.selST00000025.cd_nm.push('SELECT')
      res.ST00000025.forEach((i) => {
        this.selST00000025.cd.push(i.cd)
        this.selST00000025.cd_nm.push(i.cd_nm)
      })

      this.selST00000026.cd.push('')
      this.selST00000026.cd_nm.push('SELECT')
      res.ST00000026.forEach((i) => {
        this.selST00000026.cd.push(i.cd)
        this.selST00000026.cd_nm.push(i.cd_nm)
      })

      this.selST00000027.cd.push('')
      this.selST00000027.cd_nm.push('SELECT')
      res.ST00000027.forEach((i) => {
        this.selST00000027.cd.push(i.cd)
        this.selST00000027.cd_nm.push(i.cd_nm)
      })

      this.wtDataProvider = new LocalDataProvider(false)
      this.wtDataProvider.setFields(wtFields)
      this.wtGridView = new GridView('wtgrid')
      this.wtGridView.setDataSource(this.wtDataProvider)
      const wtColumns = [
        {
          header: this.$t('기상요소'), // 기상요소
          name: 'weat_elmt_cd',
          fieldName: 'weat_elmt_cd',
          width: '70',
          styleName: 'left',
          lookupDisplay: true,
          values: this.selST00000024.cd,
          labels: this.selST00000024.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        {
          header: this.$t('최소값'), // 최소값
          name: 'weat_elmt_min_vl',
          fieldName: 'weat_elmt_min_vl',
          width: '50',
          styleName: 'left',
          editor: {
            type: 'number',
          },
        },
        {
          header: this.$t('최소비교'), // 최소비교
          name: 'min_cppr_cd',
          fieldName: 'min_cppr_cd',
          width: '50',
          styleName: 'left',
          lookupDisplay: true,
          values: this.selST00000025.cd,
          labels: this.selST00000025.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        {
          header: this.$t('최대값'), // 최대값
          name: 'weat_elmt_max_vl',
          fieldName: 'weat_elmt_max_vl',
          width: '50',
          styleName: 'left',
          editor: {
            type: 'number',
          },
        },
        {
          header: this.$t('최대비교'), // 최대비교
          name: 'max_cppr_cd',
          fieldName: 'max_cppr_cd',
          width: '50',
          styleName: 'left',
          lookupDisplay: true,
          values: this.selST00000026.cd,
          labels: this.selST00000026.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        {
          header: this.$t('주의사항'), // 주의사항
          name: 'msg_txt',
          fieldName: 'msg_txt',
          width: '250',
          styleName: 'left',
        },
        {
          header: this.$t('정렬순서'), // 정렬순서
          name: 'sort_sn',
          fieldName: 'sort_sn',
          width: '50',
          styleName: 'left',
          editor: {
            type: 'number',
          },
        },
      ]
      this.wtGridView.setColumns(wtColumns)
      this.wtGridView.setFooters({ visible: false })
      this.wtGridView.setStateBar({ visible: false })
      this.wtGridView.setCheckBar({ visible: true })
      this.wtGridView.header.height = 39
      this.wtGridView.footer.height = 40
      this.wtGridView.displayOptions.rowHeight = 40
      this.wtGridView.displayOptions.selectionStyle = 'block'
      this.wtGridView.displayOptions.fitStyle = 'fill'
      this.wtGridView.editOptions.editable = true

      // 특보
      this.wtDataProvider2 = new LocalDataProvider(false)
      this.wtDataProvider2.setFields(wtFields2)
      this.wtGridView2 = new GridView('wtgrid2')
      this.wtGridView2.setDataSource(this.wtDataProvider2)
      const wtColumns2 = [
        {
          header: this.$t('기상특보'), // 기상특보
          name: 'weat_elmt_cd',
          fieldName: 'weat_elmt_cd',
          width: '50',
          styleName: 'left',
          lookupDisplay: true,
          values: this.selST00000027.cd,
          labels: this.selST00000027.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
        },
        {
          header: this.$t('주의사항'), // 주의사항
          name: 'msg_txt',
          fieldName: 'msg_txt',
          width: '250',
          styleName: 'left',
        },
        {
          header: this.$t('정렬순서'), // 정렬순서
          name: 'sort_sn',
          fieldName: 'sort_sn',
          width: '50',
          styleName: 'left',
          editor: {
            type: 'number',
          },
        },
      ]
      this.wtGridView2.setColumns(wtColumns2)
      this.wtGridView2.setFooters({ visible: false })
      this.wtGridView2.setStateBar({ visible: false })
      this.wtGridView2.setCheckBar({ visible: true })
      this.wtGridView2.header.height = 39
      this.wtGridView2.footer.height = 40
      this.wtGridView2.displayOptions.rowHeight = 40
      this.wtGridView2.displayOptions.selectionStyle = 'block'
      this.wtGridView2.displayOptions.fitStyle = 'fill'
      this.wtGridView2.editOptions.editable = true
    })
  },
  methods: {
    open() {
      this.select()
      this.dialog = true
    },
    async select() {
      this.schData.kind = 'weatMsg'
      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info`, { params: this.schData })

      this.wtDataProvider.setRows(data.weatMsgList1)
      this.wtGridView.refresh()
      this.wtGridView.setTopItem(0)

      this.wtDataProvider2.setRows(data.weatMsgList2)
      this.wtGridView2.refresh()
      this.wtGridView2.setTopItem(0)
    },
    async save() {
      this.wtGridView.commit()
      this.wtGridView2.commit()

      if (
        this.wtDataProvider.getRowStateCount('none') === this.wtDataProvider.getRowCount() &&
        this.wtDataProvider2.getRowStateCount('none') === this.wtDataProvider2.getRowCount()
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      for (let i = 0; i < this.wtDataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (this.wtDataProvider.getRowState(i) === 'createAndDeleted' || this.wtDataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (!this.wtDataProvider.getValue(i, 'weat_elmt_cd')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'weat_elmt_cd' })
          this.$toast.show(this.$t('기상요소를 선택해주세요.'), { className: 'toast_error' }) // 기상요소를 선택해주세요.
          return false
        }
        if (
          !this.wtDataProvider.getValue(i, 'min_cppr_cd') &&
          !this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') &&
          !this.wtDataProvider.getValue(i, 'max_cppr_cd') &&
          !this.wtDataProvider.getValue(i, 'weat_elmt_max_vl')
        ) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'min_cppr_cd' })
          this.$toast.show(this.$t('하나 이상의 비교, 값을 입력해주세요.'), { className: 'toast_error' }) // 하나 이상의 비교, 값을 입력해주세요.
          return false
        }
        if (this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') && !this.wtDataProvider.getValue(i, 'min_cppr_cd')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'min_cppr_cd' })
          this.$toast.show(this.$t('최소비교를 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (!this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') && this.wtDataProvider.getValue(i, 'min_cppr_cd')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'weat_elmt_min_vl' })
          this.$toast.show(this.$t('최소값을 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (this.wtDataProvider.getValue(i, 'weat_elmt_max_vl') && !this.wtDataProvider.getValue(i, 'max_cppr_cd')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'max_cppr_cd' })
          this.$toast.show(this.$t('최대비교를 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (!this.wtDataProvider.getValue(i, 'weat_elmt_max_vl') && this.wtDataProvider.getValue(i, 'max_cppr_cd')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'weat_elmt_max_vl' })
          this.$toast.show(this.$t('최대값을 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (
          this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') !== undefined &&
          !(this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') >= -999 && this.wtDataProvider.getValue(i, 'weat_elmt_min_vl') <= 999)
        ) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'weat_elmt_min_vl' })
          this.$toast.show(this.$t('최소값은 -999~999 사이의 값으로 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (
          this.wtDataProvider.getValue(i, 'weat_elmt_max_vl') !== undefined &&
          !(this.wtDataProvider.getValue(i, 'weat_elmt_max_vl') >= -999 && this.wtDataProvider.getValue(i, 'weat_elmt_max_vl') <= 999)
        ) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'weat_elmt_max_vl' })
          this.$toast.show(this.$t('최대값은 -999~999 사이의 값으로 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (!this.wtDataProvider.getValue(i, 'msg_txt')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'msg_txt' })
          this.$toast.show(this.$t('주의사항을 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (!this.wtDataProvider.getValue(i, 'sort_sn')) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'sort_sn' })
          this.$toast.show(this.$t('정렬순서를 입력해주세요.'), { className: 'toast_error' }) // 정렬순서를 입력해주세요.
          return false
        }
        if (this.wtDataProvider.getValue(i, 'sort_sn') !== undefined && !(this.wtDataProvider.getValue(i, 'sort_sn') >= 1 && this.wtDataProvider.getValue(i, 'sort_sn') <= 999)) {
          this.wtGridView.setCurrent({ dataRow: i, fieldName: 'sort_sn' })
          this.$toast.show(this.$t('정렬순서는 1~999 사이의 값으로 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
      }

      for (let i = 0; i < this.wtDataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (this.wtDataProvider2.getRowState(i) === 'createAndDeleted' || this.wtDataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (!this.wtDataProvider2.getValue(i, 'weat_elmt_cd')) {
          this.wtGridView2.setCurrent({ dataRow: i, fieldName: 'weat_elmt_cd' })
          this.$toast.show(this.$t('기상특보를 선택해주세요.'), { className: 'toast_error' }) // 기상특보를 선택해주세요.
          return false
        }
        if (!this.wtDataProvider2.getValue(i, 'msg_txt')) {
          this.wtGridView2.setCurrent({ dataRow: i, fieldName: 'msg_txt' })
          this.$toast.show(this.$t('주의사항을 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
        if (!this.wtDataProvider2.getValue(i, 'sort_sn')) {
          this.wtGridView2.setCurrent({ dataRow: i, fieldName: 'sort_sn' })
          this.$toast.show(this.$t('정렬순서를 입력해주세요.'), { className: 'toast_error' }) // 정렬순서를 입력해주세요.
          return false
        }
        if (
          this.wtDataProvider2.getValue(i, 'sort_sn') !== undefined &&
          !(this.wtDataProvider2.getValue(i, 'sort_sn') >= 1 && this.wtDataProvider2.getValue(i, 'sort_sn') <= 999)
        ) {
          this.wtGridView2.setCurrent({ dataRow: i, fieldName: 'sort_sn' })
          this.$toast.show(this.$t('정렬순서는 1~999 사이의 값으로 입력해주세요.'), { className: 'toast_error' }) // 주의사항을 입력해주세요.
          return false
        }
      }

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.wtDataProvider.getRowStateCount('updated') > 0 || this.wtDataProvider.getRowStateCount('deleted') > 0 || this.wtDataProvider.getRowStateCount('created') > 0) {
        this.frmData.insertList = []
        this.frmData.updateList = []
        this.frmData.deleteList = []

        for (let i = 0; i < this.wtDataProvider.getRowCount(); i++) {
          const obj = {}
          obj.weat_infr_msg_id = this.wtDataProvider.getValue(i, 'weat_infr_msg_id')
          obj.weat_elmt_cd = this.wtDataProvider.getValue(i, 'weat_elmt_cd')
          obj.weat_elmt_min_vl = this.wtDataProvider.getValue(i, 'weat_elmt_min_vl')
          obj.min_cppr_cd = this.wtDataProvider.getValue(i, 'min_cppr_cd')
          obj.weat_elmt_max_vl = this.wtDataProvider.getValue(i, 'weat_elmt_max_vl')
          obj.max_cppr_cd = this.wtDataProvider.getValue(i, 'max_cppr_cd')
          obj.msg_txt = this.wtDataProvider.getValue(i, 'msg_txt')
          obj.sort_sn = this.wtDataProvider.getValue(i, 'sort_sn')

          if (this.wtDataProvider.getRowState(i) === 'created') {
            this.frmData.insertList.push(obj)
          } else if (this.wtDataProvider.getRowState(i) === 'updated') {
            this.frmData.updateList.push(obj)
          } else if (this.wtDataProvider.getRowState(i) === 'deleted') {
            this.frmData.deleteList.push(obj)
          }
        }
      }

      if (this.wtDataProvider2.getRowStateCount('updated') > 0 || this.wtDataProvider2.getRowStateCount('deleted') > 0 || this.wtDataProvider2.getRowStateCount('created') > 0) {
        this.frmData.insertList2 = []
        this.frmData.updateList2 = []
        this.frmData.deleteList2 = []

        for (let i = 0; i < this.wtDataProvider2.getRowCount(); i++) {
          const obj = {}
          obj.weat_infr_msg_id = this.wtDataProvider2.getValue(i, 'weat_infr_msg_id')
          obj.weat_elmt_cd = this.wtDataProvider2.getValue(i, 'weat_elmt_cd')
          obj.msg_txt = this.wtDataProvider2.getValue(i, 'msg_txt')
          obj.sort_sn = this.wtDataProvider2.getValue(i, 'sort_sn')

          if (this.wtDataProvider2.getRowState(i) === 'created') {
            this.frmData.insertList2.push(obj)
          } else if (this.wtDataProvider2.getRowState(i) === 'updated') {
            this.frmData.updateList2.push(obj)
          } else if (this.wtDataProvider2.getRowState(i) === 'deleted') {
            this.frmData.deleteList2.push(obj)
          }
        }
      }

      await this.$axios.put('/api/v1/common/dashboard/dash-chart-info', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    add() {
      this.wtGridView.commit()
      this.wtDataProvider.addRow({
        weat_infr_msg_id: '',
        weat_elmt_cd: '',
        weat_elmt_min_vl: '',
        min_cppr_cd: '',
        weat_elmt_max_vl: '',
        max_cppr_cd: '',
        msg_txt: '',
        sort_sn: '',
      })
    },
    del() {
      this.wtGridView.commit()
      this.wtDataProvider.setOptions({ softDeleting: true })
      const checkedRows = this.wtGridView.getCheckedRows()
      this.wtDataProvider.hideRows(checkedRows)
      this.wtDataProvider.removeRows(checkedRows)
    },
    add2() {
      this.wtGridView2.commit()
      this.wtDataProvider2.addRow({
        weat_infr_msg_id: '',
        weat_elmt_cd: '',
        msg_txt: '',
        sort_sn: '',
      })
    },
    del2() {
      this.wtGridView2.commit()
      this.wtDataProvider2.setOptions({ softDeleting: true })
      const checkedRows = this.wtGridView2.getCheckedRows()
      this.wtDataProvider2.hideRows(checkedRows)
      this.wtDataProvider2.removeRows(checkedRows)
    },
    close() {
      this.dialog = false
      this.frmData = {}
      this.wtGridView.cancel()
      this.wtDataProvider.setFields(wtFields)
      this.wtGridView.refresh()
      this.wtGridView2.cancel()
      this.wtDataProvider2.setFields(wtFields2)
      this.wtGridView2.refresh()
    },
  },
}
// 그리드 세팅
const wtFields = [
  {
    fieldName: 'weat_infr_msg_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'weat_elmt_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'weat_elmt_min_vl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'min_cppr_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'weat_elmt_max_vl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'max_cppr_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'msg_txt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sort_sn',
    dataType: ValueType.TEXT,
  },
]

const wtFields2 = [
  {
    fieldName: 'weat_infr_msg_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'weat_elmt_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'msg_txt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sort_sn',
    dataType: ValueType.TEXT,
  },
]
</script>
