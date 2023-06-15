<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="400px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!--부서 조회/선택-->
        <v-toolbar-title>{{ $t('LB00001332') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="12">
              <div class="container_sch" style="padding: 0 0 10px 0">
                <v-row>
                  <v-col cols="2">
                    <!--부서명-->
                    <div class="label_tit">{{ $t('LB00000108') }}</div>
                  </v-col>
                  <v-col cols="7">
                    <!--입력하세요-->
                    <v-text-field
                      ref="dept_nm"
                      v-model="searchdatas.dept_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      maxlength="99"
                      @click:clear="onClearClicked()"
                      @keypress.enter="searchDept()"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3" style="text-align: right">
                    <!--검색-->
                    <v-btn color="primary" depressed height="30" @click="searchDept()">{{ $t('LB00000030') }}</v-btn>
                  </v-col>
                </v-row>
              </div>
              <div class="group_padd"></div>
              <div id="deptslctgrid" style="height: 420px"></div>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!--선택-->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00000106') }}</v-btn>
        <!--닫기-->
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
      default: 'deptgrid',
    },
  },
  data() {
    return {
      deptgridView: '',
      deptdataProvider: '',
      dialog: false,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      expanded: false,
      frmData: {},
      searchdatas: {
        dept_nm: '',
        usr_nm: '',
        dept_cd: '',
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
    this.deptdataProvider = new LocalDataProvider(false)
    this.deptdataProvider.setFields(deptfields)
    const gridId1 = document.getElementById('deptslctgrid')
    gridId1.id = this.mkey + 'deptslct'
    this.deptgridView = new GridView(this.mkey + 'deptslct')
    this.deptgridView.setDataSource(this.deptdataProvider)
    const deptcolumns = [
      {
        header: this.$t('LB00000108'), // 부서명
        name: 'dept_nm',
        fieldName: 'dept_nm',
        width: '50',
        styleName: 'left',
      },
    ]
    this.deptgridView.setColumns(deptcolumns)
    this.deptgridView.setFooters({ visible: false })
    this.deptgridView.setStateBar({ visible: false })
    this.deptgridView.setCheckBar({ visible: false })
    this.deptgridView.header.height = 39
    this.deptgridView.footer.height = 40
    this.deptgridView.displayOptions.rowHeight = 40
    this.deptgridView.displayOptions.fitStyle = 'fill'
    this.deptgridView.displayOptions.selectionStyle = 'singleRow'
    this.deptgridView.editOptions.editable = false
    this.deptgridView.onCellClicked = function (grid, clickData) {
      if (clickData.cellType === 'indicator' || clickData.cellType === 'data') {
        if (grid.getCheckBar().exclusive === true) {
          // 한개만 체크할 수 있을때
          grid.checkRow(clickData.dataRow, true, true)
        } else {
          // 여러개 체크할 수 있을때
          const isChk = grid.isCheckedRow(clickData.dataRow)
          grid.checkRow(clickData.dataRow, !isChk, false)
        }
      }
    }
    this.deptgridView.onCellDblClicked = function (grid, clickData) {
      if (grid.getCheckBar().exclusive === true) {
        this.returnData()
      }
    }.bind(this)
  },
  methods: {
    open(frmData, multiYn) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      // this.frmData = frmData
      // if (frmData.usr_nm !== null && frmData.usr_nm !== undefined) {
      //   this.searchdatas.usr_nm = frmData.usr_nm
      // }
      this.multiYn = multiYn
      this.deptgridView.setCheckBar({ visible: true, exclusive: !this.multiYn, width: 30 })
      this.dialog = true
      this.deptgridView.refresh()
      setTimeout(() => this.$refs.dept_nm.$refs.input.focus(), 100)
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
      this.deptdataProvider.setFields(deptfields)
      this.deptgridView.refresh()
    },
    onClearClicked() {
      this.searchdatas.dept_cd = ''
      this.searchdatas.dept_nm = ''
      this.deptgridView.refresh()
    },
    async searchDept() {
      if (!this.searchdatas.dept_nm) {
        this.$toast.show(this.$t('MS00000031'), { className: 'toast_error' }) // 부서명을 입력해 주세요.
        return false
      }
      const rows = await this.$axios.$get(`/api/v1/common/chems/user/dept-search`, { params: this.searchdatas })
      this.deptdataProvider.setRows(rows)
      this.deptgridView.refresh()
      this.deptgridView.setTopItem(0)
      if (rows.length === 1) {
        this.returnDataOne(rows)
      }
    },
    async returnData() {
      this.deptgridView.commit()
      const items = this.deptgridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show('선택한 데이터가 없습니다.', { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }
      const rows = this.deptgridView.getCheckedRows(true)
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.deptdataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
    },
    async returnDataOne(oneItem) {
      const returnData = []
      await returnData.push(this.deptdataProvider.getJsonRows(0, 0)[0])
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
// 그리드 세팅
const deptfields = [
  {
    fieldName: 'dept_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_nm',
    dataType: ValueType.TEXT,
  },
]
</script>
