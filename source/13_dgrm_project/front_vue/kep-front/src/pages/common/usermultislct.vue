<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1300px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!--사용자 조회/선택-->
        <v-toolbar-title>{{ $t('LB00000110') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="3">
              <div class="container_sch" style="padding: 0 0 10px 0">
                <v-row>
                  <v-col cols="2">
                    <!--부서명-->
                    <div class="label_tit">{{ $t('LB00000108') }}</div>
                  </v-col>
                  <v-col cols="7">
                    <!--입력하세요-->
                    <v-text-field
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
              <div class="group_padd" style="padding-top: 10px"></div>
              <div v-show="treeYn" class="container_tree" style="height: 415px">
                <v-treeview ref="treeview" activatable color="warning" :open-all="expanded" :items="items">
                  <template slot="label" slot-scope="{ item }">
                    <a @click="deptTreeClick(item)">{{ item.name }}</a>
                  </template>
                </v-treeview>
              </div>
              <div v-show="!treeYn" id="deptgrid" style="height: 415px"></div>
            </v-col>
            <v-col cols="9">
              <div class="container_sch" style="padding: 0 0 10px 0">
                <v-row>
                  <v-col cols="1">
                    <!--이름 검색-->
                    <div class="label_tit">{{ $t('LB00001320') }}</div>
                  </v-col>
                  <v-col cols="8" style="text-align: left">
                    <!--입력하세요-->
                    <v-text-field
                      ref="usr_nm"
                      v-model="searchdatas.usr_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      maxlength="99"
                      @keypress.enter="searchUsr()"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3" style="text-align: right">
                    <!--검색-->
                    <v-btn color="primary" depressed height="30" @click="searchUsr()">{{ $t('LB00000030') }}</v-btn>
                  </v-col>
                </v-row>
              </div>
              <div style="padding: 0; display: flex">
                <div id="usrgrid" style="padding-top: 40px; width: 100%; height: 455px"></div>
                <div style="padding-top: 150px">
                  <v-btn class="ma-2" outlined x-small fab color="gray" @click="rightdata()">
                    <v-icon dark>mdi-arrow-right</v-icon>
                  </v-btn>
                  <v-btn class="ma-2" outlined x-small fab color="gray" @click="leftdata()">
                    <v-icon dark>mdi-arrow-left</v-icon>
                  </v-btn>
                </div>
                <div class="realgrid_container" style="padding: 0; width: 100%">
                  <div class="grid_header">
                    <v-spacer></v-spacer>
                    <div>
                      <!--삭제-->
                      <v-btn color="" outlined height="30" @click="leftdata()">{{ $t('LB00000046') }}</v-btn>
                    </div>
                  </div>
                  <div id="usrmultigrid" style="width: 100%; height: 415px"></div>
                </div>
              </div>
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
// let deptgridView = GridView
// let deptdataProvider = LocalDataProvider

// let usrgridView = GridView
// let usrdataProvider = LocalDataProvider
export default {
  components: {},
  props: {
    mkey1: {
      type: String,
      default: 'deptgrid',
    },
    mkey2: {
      type: String,
      default: 'usrgrid',
    },
    mkey3: {
      type: String,
      default: 'usrmultigrid',
    },
  },
  data() {
    return {
      deptgridView: '',
      deptdataProvider: '',
      usrgridView: '',
      usrdataProvider: '',
      usrmultigridView: '',
      usrmultidataProvider: '',
      dialog: false,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      expanded: false,
      frmData: {},
      treeYn: true,
      items: [], // 트리데이터
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
    const gridId1 = document.getElementById('deptgrid')
    gridId1.id = this.mkey1 + 'userslct'
    this.deptgridView = new GridView(this.mkey1 + 'userslct')
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
    this.deptgridView.editOptions.editable = false
    this.deptgridView.displayOptions.selectionStyle = 'block'

    this.deptgridView.header.height = 39
    this.deptgridView.displayOptions.rowHeight = 40
    this.deptgridView.footer.height = 40
    this.deptgridView.displayOptions.fitStyle = 'fill'
    this.deptgridView.onCellClicked = function (grid, index, clickData) {
      const current = this.deptgridView.getCurrent()
      if (current.itemIndex < 0) return
      // 상세 or 수정 화면으로 이동
      if (current.column === 'dept_nm') {
        const value = this.deptdataProvider.getValue(current.dataRow, 'dept_cd')
        this.searchdatas.dept_cd = value
        this.usrApiSearch()
      }
    }.bind(this)

    this.usrdataProvider = new LocalDataProvider(false)
    this.usrdataProvider.setFields(usrfields)
    const gridId2 = document.getElementById('usrgrid')
    gridId2.id = this.mkey2 + 'userslct'
    this.usrgridView = new GridView(this.mkey2 + 'userslct')
    this.usrgridView.setDataSource(this.usrdataProvider)
    const usrcolumns = [
      {
        header: this.$t('LB00000105'), // 사용자명
        name: 'emp_nm',
        fieldName: 'emp_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000108'), // 부서명
        name: 'dept_nm',
        fieldName: 'dept_nm',
        width: '150',
      },
      {
        header: this.$t('LB00000073'), // 사번
        name: 'emp_no',
        fieldName: 'emp_no',
        width: '80',
      },
    ]

    this.usrgridView.setColumns(usrcolumns)
    this.usrgridView.setFooters({ visible: false })
    this.usrgridView.setCheckBar({ visible: true, width: 30 })
    this.usrgridView.setStateBar({ visible: false })
    this.usrgridView.editOptions.editable = false

    this.usrgridView.header.height = 39
    this.usrgridView.footer.height = 40
    this.usrgridView.displayOptions.rowHeight = 40
    this.usrgridView.displayOptions.fitStyle = 'fill'
    this.usrgridView.displayOptions.selectionStyle = 'singleRow'
    this.usrgridView.onCellClicked = function (grid, clickData) {
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

    this.usrmultidataProvider = new LocalDataProvider(false)
    this.usrmultidataProvider.setFields(usrmultifields)
    const gridId3 = document.getElementById('usrmultigrid')
    gridId3.id = this.mkey3 + 'usermultislct'
    this.usrmultigridView = new GridView(this.mkey3 + 'usermultislct')
    this.usrmultigridView.setDataSource(this.usrmultidataProvider)
    const usrmulticolumns = [
      {
        header: this.$t('LB00000105'), // 사용자명
        name: 'emp_nm',
        fieldName: 'emp_nm',
        width: '80',
      },
      {
        header: this.$t('LB00000108'), // 부서명
        name: 'dept_nm',
        fieldName: 'dept_nm',
        width: '150',
      },
      {
        header: this.$t('LB00000073'), // 사번
        name: 'emp_no',
        fieldName: 'emp_no',
        width: '80',
      },
    ]

    this.usrmultigridView.setColumns(usrmulticolumns)
    this.usrmultigridView.setFooters({ visible: false })
    this.usrmultigridView.setCheckBar({ visible: true, width: 30 })
    this.usrmultigridView.setStateBar({ visible: false })
    this.usrmultigridView.editOptions.editable = false

    this.usrmultigridView.header.height = 39
    this.usrmultigridView.footer.height = 40
    this.usrmultigridView.displayOptions.rowHeight = 40
    this.usrmultigridView.displayOptions.fitStyle = 'fill'
    this.usrmultigridView.displayOptions.selectionStyle = 'singleRow'
    this.select()
    this.usrmultigridView.onCellClicked = function (grid, clickData) {
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
    this.usrmultigridView.onCellDblClicked = function (grid, clickData) {
      if (grid.getCheckBar().exclusive === true) {
        this.returnData()
      }
    }.bind(this)
  },
  methods: {
    open(frmData) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      this.frmData = frmData
      if (frmData.usr_nm !== null && frmData.usr_nm !== undefined) {
        this.searchdatas.usr_nm = frmData.usr_nm
      }
      this.dialog = true
      this.deptgridView.refresh()
      this.usrgridView.refresh()
      this.usrmultigridView.refresh()
      this.select()
      setTimeout(() => this.$refs.usr_nm.$refs.input.focus(), 100)
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
      this.usrdataProvider.setFields(usrfields)
      this.usrgridView.refresh()
      this.usrmultidataProvider.setFields(usrmultifields)
      this.usrmultigridView.refresh()
      this.$refs.treeview.updateAll(this.expanded)
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/common/chems/user/dept-tree`)
      const treedata = await this.listToTree(data)
      this.items = await treedata
    },
    async listToTree(list) {
      const map = {}
      let node
      const roots = []
      let i
      for (i = 0; i < list.length; i += 1) {
        map[list[i].id] = i
        list[i].children = []
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i]
        if (node.up_dept_cd !== '$0') {
          await list[map[node.up_dept_cd]].children.push(node)
        } else {
          await roots.push(node)
        }
      }
      return roots
    },
    onClearClicked() {
      this.treeYn = !this.treeYn
      this.searchdatas.dept_cd = ''
      this.searchdatas.dept_nm = ''
      this.deptgridView.refresh()
    },
    async deptTreeClick(item) {
      this.searchdatas.dept_cd = await item.id
      await this.usrApiSearch()
    },
    async usrApiSearch() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/user/pop`, { params: this.searchdatas })
      this.usrdataProvider.setRows(rows)
      this.usrgridView.refresh()
      this.usrgridView.setTopItem(0)
    },
    async searchDept() {
      if (this.searchdatas.dept_nm === (await '') || this.searchdatas.dept_nm === (await null)) {
        this.$toast.show(this.$t('MS00000031'), { className: 'toast_error' }) // 부서명을 입력해 주세요.
        return false
      }
      if (this.treeYn === true) {
        this.treeYn = await !this.treeYn
      }
      const rows = await this.$axios.$get(`/api/v1/common/chems/user/dept-search`, { params: this.searchdatas })
      this.deptdataProvider.setRows(rows)
      this.deptgridView.refresh()
      this.deptgridView.setTopItem(0)
    },
    async searchUsr() {
      if (this.searchdatas.usr_nm === (await '') || this.searchdatas.usr_nm === (await null)) {
        if (this.searchdatas.dept_cd === (await '') || this.searchdatas.dept_cd === (await null)) {
          this.$toast.show(this.$t('MS00000225'), { className: 'toast_error' }) // 검색어를 2자 이상 입력해 주세요.
          return false
        }
      }
      const rows = await this.$axios.$get(`/api/v1/common/chems/user/pop`, { params: this.searchdatas })
      this.usrdataProvider.setRows(rows)
      this.usrgridView.refresh()
      this.usrgridView.setTopItem(0)
      if (rows.length === 1) {
        this.returnDataOne(rows)
      }
    },
    async rightdata() {
      const rows = this.usrgridView.getCheckedRows(true)
      if (rows.length < 0) {
        this.$toast.show(this.$t('MS00000030'), { className: 'toast_error' }) // 선택된 데이터가 없습니다.
        return false
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.usrdataProvider.getJsonRows(i, i)[0])
      })
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          const fields = ['emp_no']
          const values = [i.emp_no]
          const options = {
            fields,
            values,
            allFields: true,
            startIndex: 0,
            wrap: true,
            caseSensitive: false,
            partialMatch: true,
          }
          const passyn1 = this.usrmultigridView.searchItem(options)
          if (passyn1 === -1) {
            this.usrmultidataProvider.addRow({
              emp_no: i.emp_no,
              dept_cd: i.dept_cd,
              dept_nm: i.dept_nm,
              emp_nm: i.emp_nm,
              emp_email: i.emp_email,
              emp_cpho_no: i.emp_cpho_no,
              joinc_da: i.joinc_da,
              emp_clsf_cd: i.emp_clsf_cd,
              unit_nm: i.unit_nm,
              div_nm: i.div_nm,
              team_nm: i.team_nm,
              cmpy_nm: i.cmpy_nm,
              jkw_ko_nm: i.jkw_ko_nm,
            })
          } else {
            this.$toast.show(i.emp_nm + '님은 이미 선택된 인원입니다.', { className: 'toast_warn' }) // 님은 이미 선택된 인원입니다.
          }
        })
      }
      this.usrgridView.checkAll(false)
      // this.size2 = this.usrmultigridView.getItemCount()
    },
    leftdata() {
      const checkedRows = this.usrmultigridView.getCheckedRows()
      this.usrmultidataProvider.removeRows(checkedRows)
      // this.size2 = this.usrmultigridView.getItemCount()
    },
    returnData() {
      // const rows = this.usrmultidataProvider.getRowCount()
      const returnData = []
      for (let i = 0; i < this.usrmultidataProvider.getRowCount(); i++) {
        returnData.push(this.usrmultidataProvider.getJsonRows(i, i)[0])
      }
      // await rows.forEach((i) => {
      //   returnData.push(this.usrmultidataProvider.getJsonRows(i, i)[0])
      // })
      this.$emit('close', returnData)
      this.reset()
    },
    async returnDataOne(oneItem) {
      const returnData = []
      await returnData.push(this.usrdataProvider.getJsonRows(0, 0)[0])
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
// 그리드 세팅
const usrfields = [
  {
    fieldName: 'emp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_email',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_cpho_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'joinc_da',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_clsf_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'div_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'team_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cmpy_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'jkw_ko_nm',
    dataType: ValueType.TEXT,
  },
]
const usrmultifields = [
  {
    fieldName: 'emp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dept_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_email',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_cpho_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'joinc_da',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_clsf_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'div_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'team_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cmpy_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'jkw_ko_nm',
    dataType: ValueType.TEXT,
  },
]
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
