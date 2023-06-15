<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 사업장 공정 관리-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('사업장 공정 관리') }}</span>
    </div>
    <div class="work_title">
      <!-- 사업장 공정 관리 -->
      <h2>{{ $t('사업장 공정 관리') }}</h2>
    </div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사업장 공정 관리 목록-->
        <h3>{{ schData.wkpl_nm }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left"></div>
        <div>
          <!-- 대분류추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add(true)">{{ $t('대분류추가') }}</v-btn>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add(false)">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
          <!-- 사용안함 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="noUse">{{ $t('사용안함') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 581px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { TreeView, LocalTreeDataProvider, ValueType } from 'realgrid'

// 그리드 1
let gridView = TreeView
let dataProvider = LocalTreeDataProvider

export default {
  meta: {
    title: '사업장 공정 관리',
    key(route) {
      return `/riskeval/wkplprcsmgnt/${route.params.catalog}`
    },
  },
  components: {},
  data() {
    return {
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
      },
      schData: {},
      frmData: {},
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('사업장 공정 관리') // 사업장 공정 관리
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (typeof dataProvider === 'object' && dataProvider.getRowStateCount(['created', 'updated', 'deleted'])) {
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
  mounted() {
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    }
    // this.user = this.$store.getters['auth/getUser']

    // this.$axios
    //   .$get(`/api/v1/comm/wkpl`, {})
    //   .then((res) => {
    //     this.FF_00000001 = res
    //     const cmpy = this.user.cmpn_cd
    //     this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
    //       return n.up_cd === cmpy
    //     })
    //     this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    //   })
    //   .catch((err) => {
    //     console.log(err) // eslint-disable-line no-console
    //   })

    this.createGrid()
    this.select()
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    createGrid() {
      // 그리드 1 시작
      const fields = [
        {
          fieldName: 'dngp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'up_dngp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dngp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'usg_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level',
          dataType: ValueType.INT,
        },
        {
          fieldName: 'level1_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level1_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level2_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level2_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level3_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'level3_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'path',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sort_sn',
          dataType: ValueType.INT,
        },
      ]

      const columns = [
        {
          header: this.$t(' '),
          name: 'dngp_nm',
          fieldName: 'dngp_nm',
          width: '100',
        },
        {
          header: this.$t('대분류'), // 대분류
          name: 'level1_nm',
          fieldName: 'level1_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('공정명'), // 공정명
          name: 'level2_nm',
          fieldName: 'level2_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('세부공정'),
          name: 'level3_nm',
          fieldName: 'level3_nm',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('사용여부'), // 사용여부
          name: 'usg_yn',
          fieldName: 'usg_yn',
          width: '50',
          editable: false,
        },
        // {
        //   header: this.$t('순서'), // 순서
        //   name: 'sort_sn',
        //   fieldName: 'sort_sn',
        //   width: '50',
        // },
      ]
      dataProvider = new LocalTreeDataProvider()
      dataProvider.setFields(fields)
      gridView = new TreeView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = true
      gridView.editOptions.appendable = true
      gridView.editOptions.movable = true

      gridView.treeOptions.iconImages = [
        'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAWCAYAAADeiIy1AAAABHNCSVQICAgIfAhkiAAAAbJJREFUSEu1lrFOwkAYx3sQWIXJEZwcIfEBhCdQn0Dd3KwJJWzACgx1dLKP0DcQNhcTHJ3U0Q1HIaH+PsI1rYA0cG1yob27/n/f/+67ryjHcapKqbZlWee0lSsIggnjbq/X664bT9qnAPkInSE42vBSmfESY519YKrZbMIIRv1+v7YOZNt2IZ/PDxmrMM/jV9rWC71Y4FtBoiiwXC7nifOthOUEghrPZrO667oT6UoE0uK4t7kvJICVmXNJu2O53RXQMvJKAqF/p8zn82o2m3Vx9cAS3sRAPIxZmtt9IdH3AcmjB+x6sXRE8JLJZE7ofKX5pmCAagR/ivaR3qM3Oo4B1geDwdAUCBMdtNqiqx19Qi2lAPIkKabTaVE7+sLRIRmiTLkRHYrBUJZOdDXom46DFEAf6E7Qreql+6HjeVN12NVltOpoR6K1sQztAmq1WmX2/J3Mu8eAHYJw1MWiZImRq9Fo1EiwJ8QWuqmBSIQrgn8EdAHID0FYTO0MydlMExSeIangIYiSXtQl3cQmRc9QtKhaZIbpwxqeoRiIjTNhJKYR/XLLfwZjRfRvpATv6w/fL6ChOqp14SOCAAAAAElFTkSuQmCC',
        'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAWCAYAAADeiIy1AAAABHNCSVQICAgIfAhkiAAAAbJJREFUSEu1lrFOwkAYx3sQWIXJEZwcIfEBhCdQn0Dd3KwJJWzACgx1dLKP0DcQNhcTHJ3U0Q1HIaH+PsI1rYA0cG1yob27/n/f/+67ryjHcapKqbZlWee0lSsIggnjbq/X664bT9qnAPkInSE42vBSmfESY519YKrZbMIIRv1+v7YOZNt2IZ/PDxmrMM/jV9rWC71Y4FtBoiiwXC7nifOthOUEghrPZrO667oT6UoE0uK4t7kvJICVmXNJu2O53RXQMvJKAqF/p8zn82o2m3Vx9cAS3sRAPIxZmtt9IdH3AcmjB+x6sXRE8JLJZE7ofKX5pmCAagR/ivaR3qM3Oo4B1geDwdAUCBMdtNqiqx19Qi2lAPIkKabTaVE7+sLRIRmiTLkRHYrBUJZOdDXom46DFEAf6E7Qreql+6HjeVN12NVltOpoR6K1sQztAmq1WmX2/J3Mu8eAHYJw1MWiZImRq9Fo1EiwJ8QWuqmBSIQrgn8EdAHID0FYTO0MydlMExSeIangIYiSXtQl3cQmRc9QtKhaZIbpwxqeoRiIjTNhJKYR/XLLfwZjRfRvpATv6w/fL6ChOqp14SOCAAAAAElFTkSuQmCC',
        'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAbCAYAAABm409WAAAABHNCSVQICAgIfAhkiAAAAVNJREFUSEvtlk9Og0AYxTsEWJlITbeNvYHuXagnkCPgCcoC2LolbOAK7QX0BtboCdyaNN241a4Jf3wvoaRp6swEumSSCQx53/ebb3gMI3zfd2zbfqrr2hNCOCONBu0I/acsSzdN03dZiAjDMEPiuUbeVkIAYggpi6K4l0FEFEVbRJ7neT7Osoz3yhYEwYdhGDcUqiAE1BQmSSKUmRsBql6hglsMf9HHMkgvAKvG+1vLIL0ArJomsSzrCxVNjlXSG8BVayCfgEwPIScByCC9AFVVuZh16zzM/gzjJfrFrpJOADjPxayfVa4DZN0JwMT4Fu7wLXhIMjsE4ZmDKq5w/e4MkM2+gb8C8DYAjq7UsEQq++8sPLjo/5UaXKR00d5uu+Cx5QU73wM2Jv7IV8poPYEH2Qw5Hwm4xoCQS71YbdUC/2yvParwzWuHKoSmaW7iON5Q9gfWdVtssFz/RQAAAABJRU5ErkJggg==',
      ]
      gridView.treeOptions.expandedIcon = 0
      gridView.treeOptions.collapsedIcon = 1
      gridView.treeOptions.defaultIcon = 2

      gridView.setRowStyleCallback(function (grid, item, fixed) {
        const use = grid.getValue(item.index, 'usg_yn')
        if (use === 'N') {
          return 'row-highlight'
        }
      })

      gridView.onItemChecked = function (grid, itemIndex, checked) {
        gridView.checkChildren(itemIndex, checked, true, false)
      }

      dataProvider.onRowsSiblingMoved = function (provider, rows, offset) {
        const children = provider.getChildren(provider.getParent(rows[0]))
        for (let i = 0; i < children.length; i++) {
          provider.setValue(children[i], 'sort_sn', i + 1)
          if (provider.getRowState(children[i]) !== 'created') {
            provider.setRowState(children[i], 'updated')
          }
        }
      }
      // 그리드 1 끝
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/riskeval/wkplprcsmgnt`, { params: this.schData })
      dataProvider.resetHiddenRows()
      dataProvider.setRows(rows, 'path', false, '', '')
      gridView.refresh()
      gridView.expandAll()
      gridView.resetCurrent()
      const usrinfo = this.$store.getters['auth/getUser']
      // 시스템그룹 사용자
      usrinfo.roleTypeCds.forEach((i) => {
        if (i === 'M01') {
          this.isAdminGrp = true
        }
      })
    },
    async add(isLevel1) {
      gridView.commit()
      const current = isLevel1 === true ? { itemIndex: -1, column: null, dataRow: -1, fieldIndex: -1, fieldName: null } : gridView.getCurrent()
      // console.log('current is ' + JSON.stringify(current)) // eslint-disable-line no-console
      const pId = dataProvider.getValue(current.dataRow, 'dngp_id')
      const children = dataProvider.getChildren(current.dataRow) === null ? [] : dataProvider.getChildren(current.dataRow)
      const n = dataProvider.getValue(current.dataRow, 'level') === null ? 0 : dataProvider.getValue(current.dataRow, 'level')
      if (n < 3) {
        const id = await this.$axios.$get(`/api/v1/she/riskeval/wkplprcsmgnt/0`)
        // console.log('new id is ' + JSON.stringify(id)) // eslint-disable-line no-console
        const i = dataProvider.addChildRow(current.dataRow, { dngp_id: id, dngp_nm: '', up_dngp_id: pId, level: n + 1, sort_sn: children.length + 1 }, -1, false)
        gridView.expandAll()
        gridView.setCurrent({ dataRow: i, fieldName: 'dngp_nm' })
        gridView.showEditor()
        gridView.setFocus()
      }
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
      dataProvider.setRowStates(checkedRows, 'deleted', true)
    },
    noUse() {
      gridView.commit()
      const checkedRows = gridView.getCheckedRows()
      for (let i = 0; i < checkedRows.length; i++) {
        dataProvider.setValue(checkedRows[i], 'usg_yn', 'N')
      }
      gridView.checkRows(checkedRows, false)
      dataProvider.setRowStates(checkedRows, 'updated', true)
    },
    async save() {
      gridView.commit()
      if (!dataProvider.getRowStateCount(['created', 'updated', 'deleted'])) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.insertList = []
      this.frmData.updateList = []
      this.frmData.deleteList = []

      let rows = dataProvider.getStateRows('created')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.dngp_id = dataProvider.getValue(rows[i], 'dngp_id')
        obj.upp_dngp_id = dataProvider.getValue(rows[i], 'up_dngp_id') === null ? '' : dataProvider.getValue(rows[i], 'up_dngp_id')
        obj.wkpl_id = this.schData.wkpl_id
        obj.dngp_nm = dataProvider.getValue(rows[i], 'dngp_nm')
        obj.sort_sn = dataProvider.getValue(rows[i], 'sort_sn')
        this.frmData.insertList.push(obj)
      }
      // console.log('created is ' + JSON.stringify(this.frmData.insertList)) // eslint-disable-line no-console

      rows = dataProvider.getStateRows('updated')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.dngp_id = dataProvider.getValue(rows[i], 'dngp_id')
        obj.wkpl_id = this.schData.wkpl_id
        obj.dngp_nm = dataProvider.getValue(rows[i], 'dngp_nm')
        obj.sort_sn = dataProvider.getValue(rows[i], 'sort_sn')
        obj.usg_yn = dataProvider.getValue(rows[i], 'usg_yn')
        this.frmData.updateList.push(obj)
      }
      // console.log('updated is ' + JSON.stringify(this.frmData.updateList)) // eslint-disable-line no-console

      rows = dataProvider.getStateRows('deleted')
      for (let i = 0; i < rows.length; i++) {
        const obj = {}
        obj.dngp_id = dataProvider.getValue(rows[i], 'dngp_id')
        this.frmData.deleteList.push(obj)
      }
      // console.log('deleted is ' + JSON.stringify(this.frmData.deleteList)) // eslint-disable-line no-console

      this.$axios.put('/api/v1/she/riskeval/wkplprcsmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      gridView.commit()

      // 기본정보
      if (!this.schData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }

      const rows = dataProvider.getStateRows('created')
      rows.push(...dataProvider.getStateRows('updated'))

      for (let i = 0; i < rows.length; i++) {
        if (!dataProvider.getValue(rows[i], 'dngp_nm')) {
          gridView.setCurrent({ dataRow: rows[i], fieldName: 'dngp_nm' })
          this.$toast.show(this.$t('공정명을 입력해주세요'), { className: 'toast_error' }) // 공정명을 입력해주세요.
          return false
        }
      }
      return true
    },
    goList() {
      this.$router.push({
        name: `riskeval-riskevalwkplprcsmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
  },
}
</script>
<style></style>
