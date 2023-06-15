<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 특수검진 대상자 등록 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001102') }}</span>
    </div>
    <div class="work_title">
      <!-- 특수검진 대상자 등록 -->
      <h2>{{ $t('LB00001102') }}</h2>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--특수검진 대상자-->
        <h3>{{ $t('LB00001065') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--대상자 추출-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add2">{{ $t('LB00001072') }}</v-btn>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 581px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="hmexedt1" mkey2="hmexedt2" @close="popClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '특수검진 대상자 등록',
    key(route) {
      return `/hmex2/${route.params.catalog}`
    },
  },
  components: {
    userslct,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {},
      frmData: {},
      codeid: ['ST00000002', 'SH00000035', 'SH00000037'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000035: [{ cd: '', cd_nm: '' }], // 취급물질
        SH00000037: [{ cd: '', cd_nm: '' }], // 검진구분
      },
      selST00000002: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000035: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000037: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      year: [],
      cmpy: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001102') // 특수검진 대상자 등록
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (this.frmData.ckFlag) {
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
    const user = this.$store.getters['auth/getUser']
    this.cmpy = user.cmpn_cd
    const yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    for (let y = yyyy; y >= 2020; y--) {
      this.year.push(y)
    }

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        /*
        res.ST00000002.forEach((i) => {
          this.selST00000002.cd.push(i.cd)
          this.selST00000002.cd_nm.push(i.cd_nm)
        })
        */
        res.SH00000035.forEach((i) => {
          this.selSH00000035.cd.push(i.cd)
          this.selSH00000035.cd_nm.push(i.cd_nm)
        })
        res.SH00000037.forEach((i) => {
          if (i.buf_1st_cntn === 'Y') {
            this.selSH00000037.cd.push(i.cd)
            this.selSH00000037.cd_nm.push(i.cd_nm)
          }
        })

        // 사업장 조회
        this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
          res.forEach((e) => {
            // 상위 코드 조회하여 필터링
            if (e.up_cd === this.cmpy) {
              this.selST00000002.cd.push(e.cd)
              this.selST00000002.cd_nm.push(e.cd_nm)
            }
          })
          this.createGrid()
        })

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_year',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'deal_mtra_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_trgp_org_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_tgt_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmex_trgp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'jncm_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'rsgn_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cwkr_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'extract_yn',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '100',
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          lookupDisplay: true,
          // editable: false,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001055'), // 검진년도
          name: 'hmex_year',
          fieldName: 'hmex_year',
          width: '100',
          values: this.year,
          labels: this.year,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'hmex_cl_cd',
          fieldName: 'hmex_cl_cd',
          width: '100',
          values: this.selSH00000037.cd,
          labels: this.selSH00000037.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001041'), // 취급물질
          name: 'deal_mtra_cd',
          fieldName: 'deal_mtra_cd',
          width: '100',
          values: this.selSH00000035.cd,
          labels: this.selSH00000035.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hmex_tgt_emp_id',
          fieldName: 'hmex_tgt_emp_id',
          width: '100',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hmex_trgp_nm',
          fieldName: 'hmex_trgp_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'jncm_dt',
          fieldName: 'jncm_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000095'), // 비고
          name: 'cwkr_yn',
          fieldName: 'cwkr_yn',
          width: '100',
        },
        {
          header: 'extract_yn', // 추출 여부 컬럼(숨겨짐)
          name: 'extract_yn',
          fieldName: 'extract_yn',
          width: '100',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
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
      gridView.columnByName('org_nm').editable = false
      gridView.columnByName('hmex_tgt_emp_id').editable = false
      gridView.columnByName('hmex_trgp_nm').editable = false
      gridView.columnByName('jncm_dt').editable = false
      gridView.columnByName('cwkr_yn').editable = false
      gridView.setColumnProperty('extract_yn', 'visible', false)

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.cellType === 'data' && gridView.getValue(clickData.itemIndex, 'extract_yn') !== 'Y') {
          if (clickData.column === 'org_nm' || clickData.column === 'hmex_tgt_emp_id' || clickData.column === 'hmex_trgp_nm') {
            grid.commit()
            const schData = {}
            this.$refs.pop.open(schData, false)
          }
        }
      }.bind(this)

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
    },
    add() {
      this.frmData.ckFlag = true
      this.totalcnt++
      const user = this.$store.getters['auth/getUser']
      gridView.commit()
      dataProvider.addRow({
        hmex_year: this.getTzDateObj(user.time_zone).getFullYear(),
        wkpl_id: user.wkpl_id,
        hmex_cl_cd: '',
        deal_mtra_cd: '',
        hmex_trgp_org_cd: '',
        org_nm: '',
        hmex_tgt_emp_id: '',
        hmex_trgp_nm: '',
        jncm_dt: '',
        rsgn_dt: '',
        cwkr_yn: '',
      })
    },
    async add2() {
      const user = this.$store.getters['auth/getUser']
      const wkplId = user.wkpl_id
      this.frmData.ckFlag = true
      this.frmData.type = 'mstr'
      this.frmData.cmpn_cd = this.cmpy
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexmgnt`, { params: this.frmData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
      // dataProvider 사업장 세팅
      for (let i = 0; i < gridView.getItemCount(); i++) {
        dataProvider.setValue(i, 'wkpl_id', wkplId)
        dataProvider.setValue(i, 'extract_yn', 'Y')
      }
    },
    del() {
      this.frmData.ckFlag = true
      const items = gridView.getCheckedItems()
      this.totalcnt -= items.length
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        const row = gridView.getCurrent().dataRow
        const values = {
          hmex_trgp_org_cd: returnData[0].dept_cd,
          org_nm: returnData[0].dept_nm,
          hmex_tgt_emp_id: returnData[0].emp_no,
          hmex_trgp_nm: returnData[0].emp_nm,
          jncm_dt: returnData[0].joinc_da,
          rsgn_dt: '',
          cwkr_yn: returnData[0].emp_clsf_cd,
        }
        dataProvider.updateRow(row, values)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      gridView.commit()
      const items = gridView.getCheckedItems()
      for (let i = 0; i < items.length; i++) {
        if (dataProvider.getRowState(items[i]) === 'createAndDeleted' || dataProvider.getRowState(items[i]) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(items[i], 'wkpl_id'))) {
          gridView.setCurrent({ dataRow: items[i], fieldName: 'wkpl_id' })
          this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(items[i], 'hmex_cl_cd'))) {
          gridView.setCurrent({ dataRow: items[i], fieldName: 'hmex_cl_cd' })
          this.$toast.show(this.$t('MS00000527'), { className: 'toast_error' }) // 검진구분을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(items[i], 'deal_mtra_cd'))) {
          gridView.setCurrent({ dataRow: items[i], fieldName: 'deal_mtra_cd' })
          this.$toast.show(this.$t('MS00000530'), { className: 'toast_error' }) // 취급물질을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(items[i], 'hmex_tgt_emp_id'))) {
          gridView.setCurrent({ dataRow: items[i], fieldName: 'hmex_trgp_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
      }
      return true
    },
    async save() {
      const items = gridView.getCheckedItems()
      if (!this.frmData.ckFlag) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      if (!this.validation()) return
      if (items.length < 1) {
        this.$toast.show(this.$t('MS00000525'), { className: 'toast_error' }) // 선택된 대상자가 없습니다.
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.trgp_list = []
      gridView.commit()
      for (let i = 0; i < items.length; i++) {
        if (dataProvider.getRowState(items[i]) === 'createAndDeleted' || dataProvider.getRowState(items[i]) === 'deleted') {
          continue
        }
        const trgp = {}
        trgp.hmex_year = dataProvider.getValue(items[i], 'hmex_year')
        trgp.wkpl_id = dataProvider.getValue(items[i], 'wkpl_id')
        trgp.hmex_cl_cd = dataProvider.getValue(items[i], 'hmex_cl_cd')
        trgp.deal_mtra_cd = dataProvider.getValue(items[i], 'deal_mtra_cd')
        trgp.hmex_trgp_org_cd = dataProvider.getValue(items[i], 'hmex_trgp_org_cd')
        trgp.org_nm = dataProvider.getValue(items[i], 'org_nm')
        trgp.hmex_tgt_emp_id = dataProvider.getValue(items[i], 'hmex_tgt_emp_id')
        trgp.hmex_trgp_nm = dataProvider.getValue(items[i], 'hmex_trgp_nm')
        trgp.jncm_dt = dataProvider.getValue(items[i], 'jncm_dt')
        // trgp.rsgn_dt = dataProvider.getValue(items[i], 'rsgn_dt')
        trgp.cwkr_yn = dataProvider.getValue(items[i], 'cwkr_yn')
        trgp.cmpy_cd = this.cmpy
        this.frmData.trgp_list.push(trgp)
      }

      this.$axios.post('/api/v1/she/hlth/hmexmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.ckFlag = false
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    goList() {
      this.$router.push({
        name: `hmex-hmexspctrgpmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
