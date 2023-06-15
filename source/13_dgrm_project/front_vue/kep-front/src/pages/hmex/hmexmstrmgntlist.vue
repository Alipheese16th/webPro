<template>
  <div>
    <div class="location">
      <!-- Home > 건강검진 > 특수검진 마스터 관리 -->
      <span>Home > {{ $t('LB00001033') }} > {{ $t('LB00001034') }}</span>
    </div>
    <div class="work_title">
      <!-- 특수검진 마스터 관리 -->
      <h2>{{ $t('LB00001034') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.dept_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--성명-->
                <div class="label_tit">{{ $t('LB00000701') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="select2()"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="search">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--특수검진 마스터 목록-->
        <h3>{{ $t('LB00001035') }}</h3>
      </div>
    </div>
    <v-container>
      <v-row>
        <v-col cols="4" style="padding: 0">
          <div class="realgrid_container">
            <div class="grid_header"></div>
            <div id="realgrid" style="width: 100%; height: 465px"></div>
          </div>
        </v-col>
        <v-col cols="5" style="padding: 0">
          <div class="container_detail">
            <div class="grid_header"></div>
            <div id="realgrid2" style="width: 100%; height: 465px"></div>
          </div>
        </v-col>
        <v-col cols="3" style="padding: 0">
          <div class="container_detail">
            <div class="grid_header">
              <v-text-field
                v-model="schData.deal_mtra_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                clearable
                height="30"
                @change="select3()"
              ></v-text-field>
            </div>
            <div id="realgrid3" style="width: 100%; height: 465px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="insert">{{ $t('LB00000045') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

// 그리드 3
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

export default {
  meta: {
    title: '특수검진 마스터 관리',
    key(route) {
      return `/hmex1/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      codeid: ['ST00000002', 'SH00000037', 'SH00000035'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000037: [{ cd: '', cd_nm: '' }], // 건강검진코드
        SH00000035: [{ cd: '', cd_nm: '' }], // 특수검진 취급물질
      },
      selSH00000037: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000035: {
        cd: [''],
        cd_nm: ['ALL'],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001034') // 특수검진 마스터 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000035.unshift({ cd: '', cd_nm: 'ALL' })
        res.SH00000037.forEach((i) => {
          if (i.buf_1st_cntn === 'Y') {
            this.selSH00000037.cd.push(i.cd)
            this.selSH00000037.cd_nm.push(i.cd_nm)
          }
        })

        this.createGrid()
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'dept_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'up_dept_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'up_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'dept_nm',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00001036'), // 팀명
          name: 'up_dept_nm',
          fieldName: 'up_dept_nm',
          width: '100',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'dept_nm',
          fieldName: 'dept_nm',
          width: '100',
          styleName: 'link left',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = false

      gridView.onCellClicked = function (grid, index, clickData) {
        const current = gridView.getCurrent()
        if (current.itemIndex < 0) return
        this.schData.dept_cd = dataProvider.getValue(current.dataRow, 'dept_cd')
        if (current.column === 'dept_nm') {
          this.$set(this.schData, 'emp_nm', '')
          this.select2()
        }
      }.bind(this)

      const fields2 = [
        {
          fieldName: 'dept_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'emp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'joinc_da',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'aply_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'note',
          dataType: ValueType.TEXT,
        },
      ]

      const columns2 = [
        {
          header: this.$t('LB00000073'), // 사번
          name: 'emp_no',
          fieldName: 'emp_no',
          width: '100',
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'emp_nm',
          fieldName: 'emp_nm',
          width: '100',
          styleName: 'link',
        },
        {
          header: this.$t('LB00000851'), // 입사일
          name: 'joinc_da',
          fieldName: 'joinc_da',
          width: '100',
        },
        {
          header: this.$t('LB00001040'), // 취급건수
          name: 'aply_cnt',
          fieldName: 'aply_cnt',
          width: '50',
        },
        {
          header: this.$t('LB00000095'), // 비고
          name: 'note',
          fieldName: 'note',
          width: '100',
        },
      ]

      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.editOptions.editable = false

      gridView2.onCellClicked = function (grid, index, clickData) {
        const current = gridView2.getCurrent()
        if (current.itemIndex < 0) return
        this.schData.emp_no = dataProvider2.getValue(current.dataRow, 'emp_no')
        if (current.column === 'emp_nm') {
          this.select3()
        }
      }.bind(this)

      const fields3 = [
        {
          fieldName: 'deal_mtra_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'deal_mtra_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'spc_hmex_cd',
          dataType: ValueType.TEXT,
        },
      ]

      const columns3 = [
        {
          header: this.$t('LB00001041'), // 취급물질
          name: 'deal_mtra_nm',
          fieldName: 'deal_mtra_nm',
          width: '130',
          styleName: 'left',
        },
        {
          header: this.$t('LB00001042'), // 검진구분
          name: 'spc_hmex_cd',
          fieldName: 'spc_hmex_cd',
          width: '70',
          values: this.selSH00000037.cd,
          labels: this.selSH00000037.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
      ]

      dataProvider3 = new LocalDataProvider(false)
      dataProvider3.setFields(fields3)
      gridView3 = new GridView('realgrid3')
      gridView3.setDataSource(dataProvider3)
      gridView3.setColumns(columns3)
      gridView3.setFooters({ visible: false })
      gridView3.setStateBar({ visible: false })
      gridView3.setCheckBar({ visible: false })

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.editOptions.editable = true
      gridView3.columnByName('deal_mtra_nm').editable = false
      gridView3.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
    },
    async select() {
      this.schData.type = 'dept'
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexmstr`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.frmData.ckFlag = false
    },
    async select2() {
      this.schData.type = 'user'
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexmstr`, { params: this.schData })
      dataProvider2.setRows(rows)
      gridView2.refresh()
      gridView2.setTopItem(0)
      gridView3.commit()
      dataProvider3.clearRows()
      this.frmData.ckFlag = false
    },
    async select3() {
      gridView3.commit()
      dataProvider3.clearRows()
      this.schData.type = 'mtra'
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexmstr`, { params: this.schData })
      dataProvider3.setRows(rows)
      gridView3.refresh()
      gridView3.setTopItem(0)
      this.frmData.ckFlag = false
    },
    async search() {
      dataProvider2.clearRows()
      gridView3.commit()
      dataProvider3.clearRows()
      await this.select()
      if (this.schData.emp_nm) {
        this.schData.dept_cd = ''
        await this.select2()
      }
    },
    reset() {
      this.schData = {}
    },
    async insert() {
      if (!this.frmData.ckFlag) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      const current = gridView2.getCurrent()
      if (current.itemIndex < 0) {
        this.$toast.show(this.$t('MS00000523'), { className: 'toast_error' }) // 성명을 선택해주세요.
        return
      }

      gridView3.commit()
      this.frmData.spc_hmex_tgt_emp_id = dataProvider2.getValue(current.dataRow, 'emp_no')
      this.frmData.trgp_list = []
      for (let i = 0; i < dataProvider3.getRowCount(); i++) {
        if (!dataProvider3.getValue(i, 'spc_hmex_cd')) {
          continue
        }
        const trgp = {}
        trgp.wkpl_id = ''
        trgp.spc_hmex_tgt_emp_id = dataProvider2.getValue(current.dataRow, 'emp_no')
        trgp.spc_hmex_trgp_org_cd = dataProvider2.getValue(current.dataRow, 'dept_cd')
        trgp.deal_mtra_cd = dataProvider3.getValue(i, 'deal_mtra_cd')
        trgp.spc_hmex_cd = dataProvider3.getValue(i, 'spc_hmex_cd')
        this.frmData.trgp_list.push(trgp)
      }

      // if (this.frmData.trgp_list.length === 0) {
      //   this.$toast.show(this.$t('MS00000524'), { className: 'toast_error' }) // 검진구분을 선택해주세요.
      //   return
      // }

      await this.$axios.post('/api/v1/she/hlth/hmexmstr', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.ckFlag = false
          this.select3()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
