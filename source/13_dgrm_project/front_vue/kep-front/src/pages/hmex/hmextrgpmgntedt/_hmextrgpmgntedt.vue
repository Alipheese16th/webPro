<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 일반검진 대상자 등록 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001084') }}</span>
    </div>
    <div class="work_title">
      <!-- 일반검진 대상자 등록 -->
      <h2>{{ $t('LB00001084') }}</h2>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--일반검진 대상자-->
        <h3>{{ $t('LB00001079') }}</h3>
      </div>
      <div v-if="excel">
        <v-row>
          <v-col cols="6"> </v-col>
          <v-col cols="6">
            <v-file-input ref="fileinput" v-model="files" accept=".xlsx" placeholder="Upload Excel File" @change="handleXlsFile"></v-file-input>
          </v-col>
        </v-row>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀양식 다운로드-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="excelExport">{{ $t('LB00000988') }}</v-btn>
          <!--엑셀 업로드-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="excel = !excel">{{ $t('LB00001081') }}</v-btn>
          <!--당해/기간제 입사자 추가-->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add2">{{ $t('LB00001082') }}</v-btn>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 581px"></div>
      <div id="exceltemp" style="width: 100%; height: 581px; display: none"></div>
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
    <userslct ref="pop" mkey1="hmex3edt1" mkey2="hmex3edt2" @close="popClose" />
    <popup ref="pop2" @close="popClose2" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../../common/userslct'
import popup from '../hmextrgpempapd'

let gridView = GridView
let dataProvider = LocalDataProvider

let gridView1 = GridView
let dataProvider1 = LocalDataProvider

let yyyy = '2021'

export default {
  meta: {
    title: '일반검진 대상자 등록',
    key(route) {
      return `/hmex3/${route.params.catalog}`
    },
  },
  components: {
    userslct,
    popup,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {},
      frmData: {},
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
      },
      selST00000002: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      year: [],
      excel: false,
      files: [],
      cmpy: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001084') // 일반검진 대상자 등록
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
    yyyy = this.getTzDateObj(user.time_zone).getFullYear()
    for (let y = yyyy; y >= 2020; y--) {
      this.year.push(y)
    }

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        // res.ST00000002.forEach((i) => {
        //   this.selST00000002.cd.push(i.cd)
        //   this.selST00000002.cd_nm.push(i.cd_nm)
        // })

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
          fieldName: 'cwkr_yn_nm',
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
          name: 'cwkr_yn_nm',
          fieldName: 'cwkr_yn_nm',
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
      gridView.columnByName('cwkr_yn_nm').editable = false

      gridView.onCellClicked = function (grid, clickData) {
        if (clickData.cellType === 'data' && (clickData.column === 'org_nm' || clickData.column === 'hmex_tgt_emp_id' || clickData.column === 'hmex_trgp_nm')) {
          const schData = {}
          grid.commit()
          this.$refs.pop.open(schData, false)
        }
      }.bind(this)

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)

      const columns1 = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '100',
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          lookupDisplay: true,
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
          name: 'cwkr_yn_nm',
          fieldName: 'cwkr_yn_nm',
          width: '100',
        },
      ]

      dataProvider1 = new LocalDataProvider(false)
      dataProvider1.setFields(fields)
      gridView1 = new GridView('exceltemp')
      gridView1.setDataSource(dataProvider1)
      gridView1.setColumns(columns1)
      gridView1.setFooters({ visible: false })
      gridView1.setStateBar({ visible: false })
      gridView1.setCheckBar({ visible: false })

      gridView1.header.height = 39
      gridView1.displayOptions.rowHeight = 40
      gridView1.footer.height = 40
      gridView1.displayOptions.fitStyle = 'fill'
      gridView1.displayOptions.selectionStyle = 'block'
      gridView1.editOptions.editable = false
    },
    add() {
      this.frmData.ckFlag = true
      this.totalcnt++
      const user = this.$store.getters['auth/getUser']
      gridView.commit()
      dataProvider.addRow({
        hmex_year: this.getTzDateObj(user.time_zone).getFullYear(),
        wkpl_id: user.wkpl_id,
        hmex_trgp_org_cd: '',
        org_nm: '',
        hmex_tgt_emp_id: '',
        hmex_trgp_nm: '',
        jncm_dt: '',
        rsgn_dt: '',
        cwkr_yn: '',
      })
    },
    add2() {
      this.$refs.pop2.open()
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
          cwkr_yn: returnData[0].emp_clsf_cd,
          cwkr_yn_nm: '',
        }
        dataProvider.updateRow(row, values)
      }
    },
    popClose2(returnData) {
      this.frmData.ckFlag = true
      gridView.commit()
      if (returnData && returnData.length > 0) {
        returnData.forEach((i) => {
          const values = {
            wkpl_id: '',
            hmex_year: yyyy,
            hmex_trgp_org_cd: i.hmex_trgp_org_cd,
            org_nm: i.org_nm,
            hmex_tgt_emp_id: i.hmex_tgt_emp_id,
            hmex_trgp_nm: i.hmex_trgp_nm,
            jncm_dt: i.jncm_dt,
            cwkr_yn: i.cwkr_yn,
            cwkr_yn_nm: i.cwkr_yn_nm,
          }
          dataProvider.addRow(values)
        })
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()
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
        if (this.isEmpty(dataProvider.getValue(items[i], 'hmex_trgp_nm'))) {
          gridView.setCurrent({ dataRow: items[i], fieldName: 'hmex_trgp_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
      }
      return true
    },
    async save() {
      gridView.commit()
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
      for (let i = 0; i < items.length; i++) {
        if (dataProvider.getRowState(items[i]) === 'createAndDeleted' || dataProvider.getRowState(items[i]) === 'deleted') {
          continue
        }
        const trgp = {}
        trgp.hmex_year = dataProvider.getValue(items[i], 'hmex_year')
        trgp.wkpl_id = dataProvider.getValue(items[i], 'wkpl_id')
        trgp.hmex_cl_cd = 'CG01'
        trgp.hmex_trgp_org_cd = dataProvider.getValue(items[i], 'hmex_trgp_org_cd')
        trgp.org_nm = dataProvider.getValue(items[i], 'org_nm')
        trgp.hmex_tgt_emp_id = dataProvider.getValue(items[i], 'hmex_tgt_emp_id')
        trgp.hmex_trgp_nm = dataProvider.getValue(items[i], 'hmex_trgp_nm')
        trgp.jncm_dt = dataProvider.getValue(items[i], 'jncm_dt')
        // trgp.rsgn_dt = dataProvider.getValue(items[i], 'rsgn_dt')
        trgp.cwkr_yn = dataProvider.getValue(items[i], 'cwkr_yn')
        trgp.cmpy_cd = this.cmpy
        console.log(trgp.cmpy_cd) // eslint-disable-line no-console
        this.frmData.trgp_list.push(trgp)
      }

      this.frmData.type = 'mgnt'

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
        name: `hmex-hmextrgpmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'hidden'
      const header = 'visible'
      const footer = 'hidden'
      // 일반검진대상자_업로드양식
      const fileName = this.$t('LB00001086') + '.xlsx'

      gridView1.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    handleXlsFile(e) {
      this.frmData.ckFlag = true
      console.log('File : ', this.files) // eslint-disable-line no-console
      const f = this.files
      const reader = new FileReader()
      reader.onload = function (e) {
        const data = e.target.result
        const arr = this.fixdata(data)
        const workbook = XLSX.read(btoa(arr), { type: 'base64' })

        this.process_wb(workbook)
        this.totalcnt = gridView.getItemCount()
        /* DO SOMETHING WITH workbook HERE */
      }.bind(this)
      reader.readAsArrayBuffer(f)
      this.excel = !this.excel
      this.files = []
    },
    fixdata(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    },
    process_wb(wb) {
      const output = this.to_json(wb)
      console.log('output : ', output) // eslint-disable-line no-console
      const sheetNames = Object.keys(output)
      if (sheetNames.length > 0) {
        const colsObj = output[sheetNames][0]
        if (colsObj) {
          dataProvider.fillJsonData(output, { rows: sheetNames[0] })
        }
      }
    },
    to_json(workbook) {
      const result = {}
      workbook.SheetNames.forEach(function (sheetName) {
        const roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {})
        if (roa.length > 0) {
          roa.forEach((o) => {
            o.hmex_year = o.검진년도
            o.wkpl_id = o.사업장
            o.org_nm = o.부서
            o.hmex_tgt_emp_id = o.사번
            o.hmex_trgp_nm = o.성명
            o.jncm_dt = o.입사일
            o.cwkr_yn_nm = o.비고
          })
          console.log('roa : ', roa) // eslint-disable-line no-console
          result[sheetName] = roa
        }
      })
      return this.excelCodeSet(result)
    },
    excelCodeSet(result) {
      for (let i = 0; i < result.sheet1.length; i++) {
        const orgVal = result.sheet1[i].wkpl_id
        const idx = this.selST00000002.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selST00000002.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].wkpl_id = cdVal
      }
      return result
    },
  },
}
</script>
<style lang="scss" scoped></style>
