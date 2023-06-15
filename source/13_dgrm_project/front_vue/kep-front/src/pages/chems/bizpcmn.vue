<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--담당자 관리(KO)-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000103') }}</span>
    </div>
    <div class="work_title">
      <!--담당자 관리(KO)-->
      <h2>{{ $t('LB00000103') }}</h2>
    </div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--담당자 지정 기준(KO)-->
        <h3>{{ $t('LB00000102') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalgrpcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn v-if="false" color="" outlined height="28" @click="addGrp()">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn v-if="false" color="" outlined height="28" @click="deleteGrp()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="grpgrid" style="width: 100%; height: 281px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--담당자(KO)-->
        <h3>{{ $t('LB00000101') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalbizcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="popUsrList()">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="deleteBiz()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="bizgrid" style="width: 100%; height: 281px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
    </div>
    <popup ref="pop" mkey1="bizpcmn_deptgrid" mkey2="bizpcmn_usrgrid" @close="popclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'

let grpgridView = GridView
let grpdataProvider = LocalDataProvider

let bizgridView = GridView
let bizdataProvider = LocalDataProvider

export default {
  meta: {
    title: '업무 담당자관리',
  },
  components: {
    popup,
  },
  data() {
    return {
      delrow: true,
      grpCrrentrowindex: '',
      saveGrpdatas: [], //  저장될 배열 생성
      saveBizdatas: [], //  저장될 배열 생성
      frmData: {},
      searchdatas: {
        biz_pc_grp_cd: '',
      },
      selGrpAllList: {
        cd: [],
        cd_nm: [],
      },
      lookUpTreeData: {
        cd: [],
        cd_nm: [],
      },
      selCdAllList: {
        cd: [],
        cd_nm: [],
        up_cd: [],
        up_cd_nm: [],
      },
      totalgrpcnt: 0,
      totalbizcnt: 0,
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    grpgridView.commit()
    bizgridView.commit()
    if (
      grpdataProvider.getRowStateCount('all') - grpdataProvider.getRowStateCount('createAndDeleted') > 0 ||
      bizdataProvider.getRowStateCount('all') - bizdataProvider.getRowStateCount('createAndDeleted') > 0
    ) {
      const res = await this.$confirm(this.$t('MS00000060'))
      return new Promise((resolve, reject) => {
        if (res) {
          resolve()
        } else {
          // eslint-disable-next-line prefer-promise-reject-errors
          reject('reject')
        }
      })
    }
  },
  created() {},
  async mounted() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd/all`, { params: '' })
      .then((res) => {
        this.selGrpAllList.cd.push('')
        this.selGrpAllList.cd_nm.push('SELECT')
        res.GrpAllList.forEach((i) => {
          this.selGrpAllList.cd.push(i.cd)
          this.selGrpAllList.cd_nm.push(i.cd_nm)
          this.lookUpTreeData.cd.push(i.cd)
          this.lookUpTreeData.cd_nm.push(i.cd_nm)
        })
        res.CdAllList.forEach((i) => {
          this.selCdAllList.cd.push(i.cd)
          this.selCdAllList.cd_nm.push(i.cd_nm)
          this.selCdAllList.up_cd.push(i.up_cd)
          this.selCdAllList.up_cd_nm.push(i.up_cd_nm)
        })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // LookupTree 데이터
    const grpCd1 = { id: 'cnct_1st_cd_grp_no', levels: 1, keys: [], values: [] }
    const bigCd1 = { id: 'cnct_1st_cd', levels: 2, tags: [], keys: [], values: [] }
    const grpCd2 = { id: 'cnct_2nd_cd_grp_no', levels: 1, keys: [], values: [] }
    const bigCd2 = { id: 'cnct_2nd_cd', levels: 2, tags: [], keys: [], values: [] }
    const grpCd3 = { id: 'cnct_3rd_cd_grp_no', levels: 1, keys: [], values: [] }
    const bigCd3 = { id: 'cnct_3rd_cd', levels: 2, tags: [], keys: [], values: [] }
    // 샘플 데이터 작성
    const datSample1 = []
    for (let i = 0; i < this.lookUpTreeData.cd.length; i++) {
      for (let j = 0; j < this.selCdAllList.cd.length; j++) {
        if (this.lookUpTreeData.cd[i] === this.selCdAllList.up_cd[j]) {
          const dat = {}
          dat.cnct_1st_cd_grp_no = this.lookUpTreeData.cd[i]
          dat.cnct_1st_cd_grp_no_nm = this.lookUpTreeData.cd_nm[i]
          dat.cnct_1st_cd = this.selCdAllList.cd[j]
          dat.cnct_1st_cd_nm = this.selCdAllList.cd_nm[j]
          dat.cnct_2nd_cd_grp_no = this.lookUpTreeData.cd[i]
          dat.cnct_2nd_cd_grp_no_nm = this.lookUpTreeData.cd_nm[i]
          dat.cnct_2nd_cd = this.selCdAllList.cd[j]
          dat.cnct_2nd_cd_nm = this.selCdAllList.cd_nm[j]
          dat.cnct_3rd_cd_grp_no = this.lookUpTreeData.cd[i]
          dat.cnct_3rd_cd_grp_no_nm = this.lookUpTreeData.cd_nm[i]
          dat.cnct_3rd_cd = this.selCdAllList.cd[j]
          dat.cnct_3rd_cd_nm = this.selCdAllList.cd_nm[j]
          datSample1.push(dat)
        }
      }
    }
    // LookupTree 데이터 작성
    for (let i = 0; i < datSample1.length; i++) {
      const codes = datSample1[i]
      if (!grpCd1.keys.includes(codes.cnct_1st_cd_grp_no)) {
        grpCd1.keys.push(codes.cnct_1st_cd_grp_no)
        grpCd1.values.push(codes.cnct_1st_cd_grp_no_nm)
      }
      if (!bigCd1.tags.includes(codes.cnct_1st_cd)) {
        bigCd1.tags.push(codes.cnct_1st_cd)
        bigCd1.keys.push([codes.cnct_1st_cd_grp_no, codes.cnct_1st_cd])
        bigCd1.values.push(codes.cnct_1st_cd_nm)
      }
      if (!grpCd2.keys.includes(codes.cnct_2nd_cd_grp_no)) {
        grpCd2.keys.push(codes.cnct_2nd_cd_grp_no)
        grpCd2.values.push(codes.cnct_2nd_cd_grp_no_nm)
      }
      if (!bigCd2.tags.includes(codes.cnct_2nd_cd)) {
        bigCd2.tags.push(codes.cnct_2nd_cd)
        bigCd2.keys.push([codes.cnct_2nd_cd_grp_no, codes.cnct_2nd_cd])
        bigCd2.values.push(codes.cnct_2nd_cd_nm)
      }
      if (!grpCd3.keys.includes(codes.cnct_3rd_cd_grp_no)) {
        grpCd3.keys.push(codes.cnct_3rd_cd_grp_no)
        grpCd3.values.push(codes.cnct_3rd_cd_grp_no_nm)
      }
      if (!bigCd3.tags.includes(codes.cnct_3rd_cd)) {
        bigCd3.tags.push(codes.cnct_3rd_cd)
        bigCd3.keys.push([codes.cnct_3rd_cd_grp_no, codes.cnct_3rd_cd])
        bigCd3.values.push(codes.cnct_3rd_cd_nm)
      }
    }
    grpdataProvider = new LocalDataProvider(false)
    grpdataProvider.setFields(grpfields)
    grpgridView = new GridView('grpgrid')
    grpgridView.setDataSource(grpdataProvider)
    const grpcolumns = [
      {
        header: this.$t('LB00000100'), // 업무 담당자 그룹 번호(KO)
        name: 'biz_pc_grp_cd',
        fieldName: 'biz_pc_grp_cd',
        width: '100',
      },
      {
        header: this.$t('LB00000099'), // 담당자 지정 기준명(KO)
        name: 'biz_pc_grp_nm',
        fieldName: 'biz_pc_grp_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000098'), // 1차 그룹 코드(KO)
        name: 'cnct_1st_cd_grp_no',
        fieldName: 'cnct_1st_cd_grp_no',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        values: this.selGrpAllList.cd,
        labels: this.selGrpAllList.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const editableyn = grid.getValue(dataCell.index.itemIndex, 'editableyn')
          if (editableyn === 'y') {
            ret.editable = true
          } else {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000097'), // 2차 그룹 코드(KO)
        name: 'cnct_2nd_cd_grp_no',
        fieldName: 'cnct_2nd_cd_grp_no',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        values: this.selGrpAllList.cd,
        labels: this.selGrpAllList.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const cnct1stcdgrpno = grid.getValue(dataCell.index.itemIndex, 'cnct_1st_cd_grp_no')
          const editableyn = grid.getValue(dataCell.index.itemIndex, 'editableyn')
          if (cnct1stcdgrpno === '') {
            ret.editable = false
          } else if (editableyn === 'y') {
            ret.editable = true
          } else {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000096'), // 3차 그룹 코드(KO)
        name: 'cnct_3rd_cd_grp_no',
        fieldName: 'cnct_3rd_cd_grp_no',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        values: this.selGrpAllList.cd,
        labels: this.selGrpAllList.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const cnct2ndcdgrpno = grid.getValue(dataCell.index.itemIndex, 'cnct_2nd_cd_grp_no')
          const editableyn = grid.getValue(dataCell.index.itemIndex, 'editableyn')
          if (cnct2ndcdgrpno === '') {
            ret.editable = false
          } else if (editableyn === 'y') {
            ret.editable = true
          } else {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000095'), // 비고(KO)
        name: 'biz_pc_note',
        fieldName: 'biz_pc_note',
        width: '100',
        styleName: 'left',
        editor: {
          maxLength: 169,
        },
      },
    ]
    grpgridView.setColumns(grpcolumns)
    grpgridView.setFooters({ visible: false })
    const grpheadergroup = [
      'biz_pc_grp_cd',
      'biz_pc_grp_nm',
      {
        name: 'Group1',
        direction: 'horizontal',
        items: ['cnct_1st_cd_grp_no', 'cnct_2nd_cd_grp_no', 'cnct_3rd_cd_grp_no'],
        header: {
          text: this.$t('LB00000090'), // 업무 담당자 구분 그룹 코드(KO)
        },
      },
      'biz_pc_note',
    ]
    grpgridView.setColumnLayout(grpheadergroup)

    grpgridView.setStateBar({ visible: false })
    grpgridView.setCheckBar({ visible: true })
    grpgridView.editOptions.editable = true
    grpgridView.displayOptions.selectionStyle = 'block'

    grpgridView.header.height = 60
    grpgridView.displayOptions.rowHeight = 40
    grpgridView.footer.height = 40
    grpgridView.displayOptions.fitStyle = 'fill'
    grpgridView.setColumnProperty('biz_pc_grp_cd', 'editable', false)
    grpgridView.setColumnProperty('biz_pc_grp_nm', 'editable', false)

    grpgridView.onCellClicked = function (grid, index, clickData) {
      grpgridView.commit()
      bizgridView.commit()
      const current = grpgridView.getCurrent()
      this.grpCrrentrowindex = current.itemIndex
      if (current.itemIndex < 0) return
      if (index.cellType === 'header' || index.cellType === 'head') return
      const value = grpgridView.getValue(current.itemIndex, 'biz_pc_grp_cd')
      this.searchdatas.biz_pc_grp_cd = value
      this.delrow = false
      if (value === '') {
        bizdataProvider.clearRows()
        this.searchdatas.biz_pc_grp_cd = ''
      } else {
        this.selectBiz(this.schData) // 담당자 목록 조회
      }
    }.bind(this)
    grpgridView.onEditChange = function (grid, index, value) {
      const current = grpgridView.getCurrent()
      if (current.column === 'biz_pc_note') {
      } else {
        grpgridView.commit()
        bizgridView.commit()
        const cnct1stcdgrpno = grid.getValue(current.itemIndex, 'cnct_1st_cd_grp_no')
        const cnct2ndcdgrpno = grid.getValue(current.itemIndex, 'cnct_2nd_cd_grp_no')
        const cnct3rdcdgrpno = grid.getValue(current.itemIndex, 'cnct_3rd_cd_grp_no')
        if (current.column === 'cnct_1st_cd_grp_no') {
          if (cnct1stcdgrpno === '') {
            grid.setValue(current.itemIndex, 'cnct_2nd_cd_grp_no', '')
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
          if (value === cnct2ndcdgrpno) {
            grid.setValue(current.itemIndex, 'cnct_2nd_cd_grp_no', '')
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
          if (value === cnct3rdcdgrpno) {
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
        } else if (current.column === 'cnct_2nd_cd_grp_no') {
          if (cnct1stcdgrpno === value) {
            grid.setValue(current.itemIndex, 'cnct_2nd_cd_grp_no', '')
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
          if (cnct2ndcdgrpno === '') {
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
        } else if (current.column === 'cnct_3rd_cd_grp_no') {
          if (cnct1stcdgrpno === value || cnct2ndcdgrpno === value) {
            grid.setValue(current.itemIndex, 'cnct_3rd_cd_grp_no', '')
          }
        }
      }
    }
    bizdataProvider = new LocalDataProvider(false)
    bizdataProvider.setFields(bizfields)
    bizgridView = new GridView('bizgrid')
    bizgridView.setDataSource(bizdataProvider)
    // LookupTree 추가
    bizgridView.addLookupSource(grpCd1)
    bizgridView.addLookupSource(bigCd1)
    bizgridView.addLookupSource(grpCd2)
    bizgridView.addLookupSource(bigCd2)
    bizgridView.addLookupSource(grpCd3)
    bizgridView.addLookupSource(bigCd3)
    const bizcolumns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'usr_id',
        fieldName: 'usr_id',
        width: '100',
      },
      {
        header: this.$t('LB00000072'), // 이름
        name: 'usr_nm',
        fieldName: 'usr_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000094'), // 1차 구분 코드(KO)
        name: 'cnct_1st_cd',
        fieldName: 'cnct_1st_cd',
        values: this.selCdAllList.cd,
        labels: this.selCdAllList.cd_nm,
        lookupDisplay: true,
        // lookupTree 설정
        lookupSourceId: 'cnct_1st_cd',
        lookupKeyFields: ['cnct_1st_cd_grp_no', 'cnct_1st_cd'],
        width: '100',
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
        },
      },
      {
        header: this.$t('LB00000093'), // 2차 구분 코드(KO)
        name: 'cnct_2nd_cd',
        fieldName: 'cnct_2nd_cd',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        // lookupTree 설정
        lookupSourceId: 'cnct_2nd_cd',
        lookupKeyFields: ['cnct_2nd_cd_grp_no', 'cnct_2nd_cd'],
        values: this.selCdAllList.cd,
        labels: this.selCdAllList.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: this.$t('LB00000092'), // 3차 구분 코드(KO)
        name: 'cnct_3rd_cd',
        fieldName: 'cnct_3rd_cd',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        // lookupTree 설정
        lookupSourceId: 'cnct_3rd_cd',
        lookupKeyFields: ['cnct_3rd_cd_grp_no', 'cnct_3rd_cd'],
        values: this.selCdAllList.cd,
        labels: this.selCdAllList.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: this.$t('LB00000091'), // 정렬순서
        name: 'sort_seq',
        fieldName: 'sort_seq',
        width: '100',
        editor: {
          type: 'number',
          maxLength: 4,
        },
      },
      {
        header: this.$t('LB00000095'), // 비고(KO)
        name: 'biz_pc_note',
        fieldName: 'biz_pc_note',
        width: '100',
        styleName: 'left',
        editor: {
          maxLength: 169,
        },
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'usg_yn',
        fieldName: 'usg_yn',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          values: ['Y', 'N'],
          labels: ['Y', 'N'],
        },
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'upt_usr_nm',
        fieldName: 'upt_usr_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
    ]
    bizgridView.setColumns(bizcolumns)
    bizgridView.setFooters({ visible: false })
    const bizheadergroup = [
      'usr_id',
      'usr_nm',
      {
        name: 'Group1',
        direction: 'horizontal',
        items: ['cnct_1st_cd', 'cnct_2nd_cd', 'cnct_3rd_cd'],
        header: {
          text: this.$t('LB00000089'), // 담당자 구분 코드(KO)
        },
      },
      'sort_seq',
      'biz_pc_note',
      'usg_yn',
      'upt_usr_nm',
      'upt_dt',
    ]
    bizgridView.setColumnLayout(bizheadergroup)

    bizgridView.setStateBar({ visible: false })
    bizgridView.setCheckBar({ visible: true })
    bizgridView.editOptions.editable = true
    bizgridView.displayOptions.selectionStyle = 'block'

    bizgridView.header.height = 60
    bizgridView.displayOptions.rowHeight = 40
    bizgridView.footer.height = 40
    bizgridView.displayOptions.fitStyle = 'fill'
    bizgridView.setColumnProperty('usr_id', 'editable', false)
    bizgridView.setColumnProperty('usr_nm', 'editable', false)
    bizgridView.setColumnProperty('upt_usr_nm', 'editable', false)
    bizgridView.setColumnProperty('upt_dt', 'editable', false)
    // 업무 담당 그룹 목록 조회
    this.selectGrp()
  },
  methods: {
    async selectGrp() {
      grpgridView.commit()
      const rows = await this.$axios.$get(`/api/v1/common/chems/bizpc`, { params: '' })
      grpdataProvider.setRows(rows)
      grpgridView.refresh()
      grpgridView.setTopItem(0)
      this.totalgrpcnt = grpgridView.getItemCount()
      bizdataProvider.clearRows()
      this.searchdatas.biz_pc_grp_cd = ''
      this.grpCrrentrowindex = ''
      this.delrow = true
    },
    async selectBiz() {
      bizgridView.commit()
      const rows = await this.$axios.$get(`/api/v1/common/chems/bizpc/${this.searchdatas.biz_pc_grp_cd}`)
      bizdataProvider.setRows(rows)
      bizgridView.refresh()
      bizgridView.setTopItem(0)
      this.totalbizcnt = bizgridView.getItemCount()
      if (rows.length > 0) {
        grpgridView.setValue(this.grpCrrentrowindex, 'editableyn', 'n')
      } else {
        grpgridView.setValue(this.grpCrrentrowindex, 'editableyn', 'y')
      }
    },
    addGrp() {
      grpgridView.commit()
      bizgridView.commit()
      if (grpdataProvider.getRowStateCount('created') > 0) {
        this.$toast.show(this.$t('MS00000029'), { className: 'toast_error' }) // 이미 추가된 행이 존재합니다.
        return
      }
      grpdataProvider.insertRows(0, [
        {
          biz_pc_grp_cd: '',
          cnct_1st_cd_grp_no: '',
          cnct_2nd_cd_grp_no: '',
          cnct_3rd_cd_grp_no: '',
          biz_pc_grp_nm: '',
          del_yn: '',
          editableyn: 'y',
        },
      ])
      bizdataProvider.clearRows()
      this.searchdatas.biz_pc_grp_cd = ''
      this.delrow = true
      this.grpCrrentrowindex = ''
      this.totalgrpcnt = grpgridView.getItemCount()
      this.totalbizcnt = bizgridView.getItemCount()
      grpgridView.setCurrent({ dataRow: this.totalgrpcnt, column: 'biz_pc_grp_nm' })
    },
    deleteGrp() {
      grpgridView.commit()
      bizgridView.commit()
      grpdataProvider.setOptions({ softDeleting: true })
      const checkedRows = grpgridView.getCheckedRows()
      grpdataProvider.hideRows(checkedRows)
      grpdataProvider.removeRows(checkedRows)
      if (checkedRows.length > 0) {
        bizdataProvider.clearRows()
        this.searchdatas.biz_pc_grp_cd = ''
        this.delrow = true
        this.grpCrrentrowindex = ''
      }
      this.totalgrpcnt = grpgridView.getItemCount()
      this.totalbizcnt = bizgridView.getItemCount()
    },
    popUsrList() {
      grpgridView.commit()
      bizgridView.commit()
      if (this.delrow) {
        this.$toast.show(this.$t('MS00000028'), { className: 'toast_error' }) // 담당자 지정 기준 행을 선택해 주세요.(KO)
      } else if (grpgridView.getValue(this.grpCrrentrowindex, 'cnct_1st_cd_grp_no') === '') {
        this.$toast.show(this.$t('MS00000027'), { className: 'toast_error' }) // 1차 그룹코드는 필수 입니다.(KO)
      } else {
        this.$refs.pop.open(this.frmData, true)
      }
    },
    async popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          // const passyn = bizdataProvider.searchData({ fields: ['usr_id'], value: i.usr_id })
          // if (passyn === null) {
          bizdataProvider.insertRows(0, [
            {
              biz_pc_grp_cd: grpgridView.getValue(this.grpCrrentrowindex, 'biz_pc_grp_cd'),
              usr_id: i.usr_id,
              usr_nm: i.usr_nm,
              cnct_1st_cd_grp_no: grpgridView.getValue(this.grpCrrentrowindex, 'cnct_1st_cd_grp_no'),
              cnct_2nd_cd_grp_no: grpgridView.getValue(this.grpCrrentrowindex, 'cnct_2nd_cd_grp_no'),
              cnct_3rd_cd_grp_no: grpgridView.getValue(this.grpCrrentrowindex, 'cnct_3rd_cd_grp_no'),
              cnct_1st_cd: '',
              cnct_2nd_cd: '',
              cnct_3rd_cd: '',
              sort_seq: '',
              biz_pc_note: '',
              usg_yn: 'Y',
              upt_usr_nm: '',
              upt_dt: '',
            },
          ])
          // } else {
          //   bizdataProvider.showHiddenRows(passyn.dataRow)
          //   const rowState = bizdataProvider.getRowState(passyn.dataRow)
          //   if (rowState === 'createAndDeleted') {
          //     bizdataProvider.setRowState(passyn.dataRow, 'created')
          //   } else {
          //     bizdataProvider.setRowState(passyn.dataRow, 'updated')
          //   }
          // }
        })
        grpgridView.setValue(this.grpCrrentrowindex, 'editableyn', 'n')
      }
      this.totalbizcnt = bizgridView.getItemCount()
    },
    deleteBiz() {
      bizgridView.commit()
      bizgridView.commit()
      bizdataProvider.setOptions({ softDeleting: true })
      const checkedRows = bizgridView.getCheckedRows()
      bizdataProvider.hideRows(checkedRows)
      bizdataProvider.removeRows(checkedRows)
      if (bizdataProvider.getRowStateCount('all') - bizdataProvider.getRowStateCount('createAndDeleted') - bizdataProvider.getRowStateCount('deleted') === 0) {
        grpgridView.setValue(this.grpCrrentrowindex, 'editableyn', 'y')
      }
      this.totalbizcnt = bizgridView.getItemCount()
    },
    async grpvalidationChk(lowstatus) {
      const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      if (chkRow.length === 0) {
        return true
      }
      for (let i = 0; i < chkRow.length; i++) {
        if ((await grpdataProvider.getValue(chkRow[i], 'biz_pc_grp_nm')) === '') {
          this.$toast.show(this.$t('MS00000026'), { className: 'toast_error' }) // 담당자 지정 기준명을 입력해 주세요.(KO)
          return false
        } else if ((await grpdataProvider.getValue(chkRow[i], 'cnct_1st_cd_grp_no')) === '') {
          this.$toast.show(this.$t('MS00000025'), { className: 'toast_error' }) // 1차 그룹 코드을 선택해 주세요.(KO)
          return false
        } else if (i + 1 === chkRow.length) {
          return true
        }
      }
    },
    async bizvalidationChk(lowstatus) {
      const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      if (chkRow.length === 0) {
        return true
      }
      for (let i = 0; i < chkRow.length; i++) {
        if ((await bizdataProvider.getValue(chkRow[i], 'cnct_1st_cd')) === '') {
          this.$toast.show(this.$t('MS00000146'), { className: 'toast_error' }) // 1차 구분코드를 선택해 주세요
          return false
        } else if ((await bizdataProvider.getValue(chkRow[i], 'sort_seq')) === '') {
          this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }) // 정렬순서를 입력해 주세요.
          return false
        } else if ((await bizdataProvider.getValue(chkRow[i], 'usg_yn')) === '') {
          this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용여부를 선택해 주세요.
          return false
        } else if (!/^[0-9]*$/.test(await bizdataProvider.getValue(chkRow[i], 'sort_seq'))) {
          this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }) // 정렬순서는 숫자만 입력해주세요.
          return false
        } else if (i + 1 === chkRow.length) {
          return true
        }
      }
    },
    async createPush(lowstatus, cd) {
      if (cd === 'grp') {
        if ((await grpdataProvider.getRowStateCount('created')) > 0) {
          await lowstatus.created.forEach((i) => {
            grpdataProvider.setValue(i, 'row_status', 'C')
            this.saveGrpdatas.push(grpdataProvider.getJsonRows(i, i))
          })
        }
      } else if ((await bizdataProvider.getRowStateCount('created')) > 0) {
        await lowstatus.created.forEach((i) => {
          bizdataProvider.setValue(i, 'row_status', 'C')
          this.saveBizdatas.push(bizdataProvider.getJsonRows(i, i))
        })
      }
      return true
    },
    async updatePush(lowstatus, cd) {
      if (cd === 'grp') {
        if ((await grpdataProvider.getRowStateCount('updated')) > 0) {
          await lowstatus.updated.forEach((i) => {
            grpdataProvider.setValue(i, 'row_status', 'U')
            this.saveGrpdatas.push(grpdataProvider.getJsonRows(i, i))
          })
        }
      } else if ((await bizdataProvider.getRowStateCount('updated')) > 0) {
        await lowstatus.updated.forEach((i) => {
          bizdataProvider.setValue(i, 'row_status', 'U')
          this.saveBizdatas.push(bizdataProvider.getJsonRows(i, i))
        })
      }
      return true
    },
    async deletePush(lowstatus, cd) {
      if (cd === 'grp') {
        if ((await grpdataProvider.getRowStateCount('deleted')) > 0) {
          await lowstatus.deleted.forEach((i) => {
            grpdataProvider.setValue(i, 'row_status', 'D')
            this.saveGrpdatas.push(grpdataProvider.getJsonRows(i, i))
          })
        }
      } else if ((await bizdataProvider.getRowStateCount('deleted')) > 0) {
        await lowstatus.deleted.forEach((i) => {
          bizdataProvider.setValue(i, 'row_status', 'D')
          this.saveBizdatas.push(bizdataProvider.getJsonRows(i, i))
        })
      }
      return true
    },
    async saveApi() {
      this.saveparams = {}
      this.saveparams.saveGrpdatas = this.saveGrpdatas
      this.saveparams.saveBizdatas = this.saveBizdatas
      await this.$axios.post('/api/v1/common/chems/bizpc', this.saveparams).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.selectGrp()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      grpgridView.commit()
      bizgridView.commit()
      if (
        grpdataProvider.getRowStateCount('all') - grpdataProvider.getRowStateCount('createAndDeleted') > 0 ||
        bizdataProvider.getRowStateCount('all') - bizdataProvider.getRowStateCount('createAndDeleted') > 0
      ) {
        const lowstatus = grpdataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
        this.saveGrpdatas = [] //  저장될 배열 비우기
        const lowstatus2 = bizdataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
        this.saveBizdatas = [] //  저장될 배열 비우기
        if ((await this.grpvalidationChk(lowstatus)) && (await this.bizvalidationChk(lowstatus2))) {
          const crechk1 = await this.createPush(lowstatus, 'grp')
          const upchk1 = await this.updatePush(lowstatus, 'grp')
          const dechk1 = await this.deletePush(lowstatus, 'grp')
          const crechk2 = await this.createPush(lowstatus2, 'biz')
          const upchk2 = await this.updatePush(lowstatus2, 'biz')
          const dechk2 = await this.deletePush(lowstatus2, 'biz')
          if (await (crechk1 && upchk1 && dechk1 && crechk2 && upchk2 && dechk2)) {
            // 저장 여부 확인
            const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
            if (!res) {
              return
            }
            await this.saveApi()
          }
        }
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      }
    },
  },
}

// 그리드 세팅
const grpfields = [
  {
    fieldName: 'biz_pc_grp_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_1st_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_2nd_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_3rd_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_pc_note',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_pc_grp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usg_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'del_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'row_status',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'editableyn',
    dataType: ValueType.TEXT,
  },
]

const bizfields = [
  {
    fieldName: 'biz_pc_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_pc_grp_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usr_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_1st_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_2nd_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_3rd_cd_grp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_1st_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_2nd_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'cnct_3rd_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sort_seq',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_pc_note',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usg_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_usr_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_usr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_dt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'row_status',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
