<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 규제별 물질 관리 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000323') }}</span>
    </div>
    <!-- 규제별 물질 관리 -->
    <div class="work_title">
      <h2>{{ $t('LB00000323') }}</h2>
    </div>
    <div class="container_detail">
      <!-- 규제 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000028') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 규제번호 -->
                <div class="label_tit">{{ $t('LB00000307') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검토분야 -->
                <div class="label_tit">{{ $t('LB00000271') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_doma_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 사용여부 -->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.use_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 법률 -->
                <div class="label_tit">{{ $t('LB00000027') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.law_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 규제명 -->
                <div class="label_tit">{{ $t('LB00000213') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.rglt_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 등록자 -->
                <div class="label_tit">{{ $t('LB00000055') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crt_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 등록일 -->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.crt_dt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제 대상 물질 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000324') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀-->
          <v-btn color="primary" outlined height="28" @click="excelDown()">{{ $t('LB00000572') }}</v-btn>
          <!-- 재조회 -->
          <v-btn color="primary" outlined height="28" @click="reset">{{ $t('LB00000325') }}</v-btn>
          <!-- 추가 -->
          <v-btn color="primary" outlined height="28" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn color="primary" outlined height="28" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 350px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 저장 -->
      <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popup ref="pop" mkey="rgltsbstmnedtsbstslct" @close="popclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/sbstslct'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '규제DB-규제별 물질 관리',
    key(route) {
      return `/rgltdb/${route.params.catalog}`
    },
  },
  components: {
    popup,
  },
  data() {
    return {
      user: {},
      srchData: {},
      tmpSchData: {},
      frmData: {},
      gridData: {},
      totalcnt: 0,
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000323')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        // rgltchkdomacd: '',
        lawcd: '',
        rgltnm: '',
        useyn: '',
      }
    }

    this.frmData.username = this.$store.getters['auth/getUsername']
    // 파라미터 조회
    this.srchData.rglt_no = this.$route.params.rgltsbstmnedt
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ke_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_ko_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_crtr_rati',
        dataType: ValueType.TEXT,
      },
      /* 
      {
        fieldName: 'rglt_crtr_use_qty',
        dataType: ValueType.NUMBER,
      },
      */
      {
        fieldName: 'rglt_crtr_cntn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_sbst_note',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'del_yn',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000317'), // 물질번호
        name: this.$t('LB00000317'),
        fieldName: 'sbst_no',
        width: '100',
        editable: false,
      },
      {
        header: 'CAS No.',
        name: 'CAS No.',
        fieldName: 'cas_no',
        width: '60',
        editable: false,
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: this.$t('LB00000196'),
        fieldName: 'sbst_ko_nm',
        width: '200',
        styleName: 'left',
        editable: false,
      },
      {
        header: this.$t('LB00000326') + '(%)', // 함량기준(%)
        name: this.$t('LB00000326') + '(%)',
        fieldName: 'rglt_crtr_rati',
        width: '60',
        styleName: 'right',
        editor: {
          maxLength: 6,
        },
      },
      {
        header: this.$t('LB00000113'), // 내용
        name: this.$t('LB00000113'),
        fieldName: 'rglt_crtr_cntn',
        width: '175',
        styleName: 'left',
      },
      /*
      {
        header: this.$t('LB00000321'), // 값
        name: this.$t('LB00000321'),
        fieldName: 'rglt_crtr_use_qty',
        width: '60',
        styleName: 'right',
        editor: {
          type: 'number',
          maxIntegerLength: 15,
        },
      },
       */
      {
        header: this.$t('LB00000095'), // 비고
        name: this.$t('LB00000095'),
        fieldName: 'rglt_sbst_note',
        width: '125',
        styleName: 'left',
      },
    ]
    const headergroup = [
      // this.$t('LB00000317'), // 물질번호
      'CAS No.',
      this.$t('LB00000196'), // 물질명
      this.$t('LB00000326') + '(%)', // 함량기준(%)
      this.$t('LB00000113'),
      this.$t('LB00000095'), // 비고
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setColumnLayout(headergroup)

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true, width: 50 })
    gridView.editOptions.editable = true
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 60
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)
    gridView.setColumnProperty(this.$t('LB00000317'), 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      if (current.column === 'sbst_ko_nm') {
        const value = dataProvider.getValue(current.dataRow, 'sbst_no')
        this.$refs.pop.open(true, value, this.frmData.username)
      }
    }.bind(this)

    this.select()
  },
  methods: {
    excelDown() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.frmData.rglt_nm + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
    async select() {
      // eslint-disable-next-line no-unused-vars
      const rows = await this.$axios
        .$get(`/api/v1/ch/rgltdb/rglt-sbst-dtl/${this.srchData.rglt_no}`, { params: this.srchData })
        .then((res) => {
          this.frmData = res
          this.selectDtl()
        })
        .catch((err) => {
          // eslint-disable-next-line no-console
          console.log(err)
        })
    },
    async selectDtl() {
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/rglt-sbst-dtl-list`, { params: this.srchData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    async save() {
      gridView.commit()
      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount()) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.validate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      gridView.commit()
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.sbst_no = dataProvider.getValue(i, 'sbst_no')
          temp.rglt_no = this.frmData.rglt_no
          temp.rglt_crtr_rati = dataProvider.getValue(i, 'rglt_crtr_rati')
          // temp.rglt_crtr_use_qty = dataProvider.getValue(i, 'rglt_crtr_use_qty')
          temp.rglt_crtr_cntn = dataProvider.getValue(i, 'rglt_crtr_cntn')
          temp.rglt_sbst_note = dataProvider.getValue(i, 'rglt_sbst_note')
          temp.use_yn = dataProvider.getValue(i, 'use_yn')
          temp.del_yn = dataProvider.getValue(i, 'del_yn')
          temp.crt_emp_no = dataProvider.getValue(i, 'crt_emp_no')
          temp.crt_dt = dataProvider.getValue(i, 'crt_dt')
          temp.username = this.user.emp_no

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow

        await this.$axios.put('/api/v1/ch/rgltdb/rglt-sbst-dtl', this.frmData).then((res) => {
          console.log(res) // eslint-disable-line no-console
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
    add() {
      this.$refs.pop.open(this.frmData, true)
    },
    async popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        // eslint-disable-next-line no-console
        console.log(returnData)
        await returnData.forEach((i) => {
          const passyn = dataProvider.searchData({ fields: ['sbst_no'], value: i.sbst_no })
          if (passyn === null) {
            dataProvider.addRow({
              sbst_no: i.sbst_no,
              ke_no: i.ke_no,
              cas_no: i.cas_no,
              sbst_ko_nm: i.sbst_ko_nm,
              rglt_crtr_rati: '',
              //  rglt_crtr_use_qty: '',
              rglt_crtr_cntn: '',
              rglt_sbst_note: '',
            })
          } else {
            this.$toast.show(i.sbst_ko_nm + ' ' + this.$t('MS00000132'), { className: 'toast_error' }) // 물질이 이미 존재합니다.
          }
        })
      }
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    async reset() {
      gridView.commit()
      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount()) {
        const res = await this.$confirm(this.$t('MS00000131')) // 수정된 내용이 있습니다. 화면을 이동할 경우 작업내용을 저장할 수 없습니다.
        if (res) {
          this.select()
        }
      } else {
        this.select()
      }
    },
    async goList() {
      gridView.commit()
      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount()) {
        const res = await this.$confirm(this.$t('MS00000133')) // 수정된 내용이 있습니다. 재조회 할 경우 작업내용을 저장할 수 없습니다.
        if (res) {
          this.$router.push({
            name: `rgltdb-rgltlist`,
            params: {
              schData: {
                // rgltchkdomacd: this.tmpSchData.rgltchkdomacd,
                lawcd: this.tmpSchData.lawcd,
                rgltnm: this.tmpSchData.rgltnm,
                useyn: this.tmpSchData.useyn,
              },
            },
          })
        }
      } else {
        this.$router.push({
          name: `rgltdb-rgltlist`,
          params: {
            schData: {
              // rgltchkdomacd: this.tmpSchData.rgltchkdomacd,
              lawcd: this.tmpSchData.lawcd,
              rgltnm: this.tmpSchData.rgltnm,
              useyn: this.tmpSchData.useyn,
            },
          },
        })
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validate() {
      gridView.commit()
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (this.isEmpty(dataProvider.getValue(i, 'rglt_crtr_rati')) && dataProvider.getRowState(i) !== 'createAndDeleted') {
          gridView.setCurrent({ dataRow: i, fieldName: 'rglt_crtr_rati' })
          this.$toast.show(this.$t('MS00000229'), { className: 'toast_error' }) // 함량기준을 입력해주세요.
          return false
        }
        if (isNaN(dataProvider.getValue(i, 'rglt_crtr_rati')) && dataProvider.getRowState(i) !== 'createAndDeleted') {
          this.$toast.show(this.$t('MS00000230'), { className: 'toast_error' }) // 함량기준은 숫자만 입력해주세요.
          return false
        }
        if (
          (dataProvider.getValue(i, 'rglt_crtr_rati') * 1 < 0.001 || dataProvider.getValue(i, 'rglt_crtr_rati') * 1 > 100) &&
          dataProvider.getRowState(i) !== 'createAndDeleted'
        ) {
          this.$toast.show(this.$t('MS00000231'), { className: 'toast_error' }) // 함량기준을 범위에 맞게 입력해 주세요.(0.001 이상, 100 미만)
          return false
        }
        if (
          !this.isEmpty(dataProvider.getValue(i, 'rglt_crtr_cntn')) &&
          dataProvider.getValue(i, 'rglt_crtr_cntn').length > 300 &&
          dataProvider.getRowState(i) !== 'createAndDeleted'
        ) {
          gridView.setCurrent({ dataRow: i, fieldName: 'rglt_crtr_cntn' })
          this.$toast.show(this.$t('MS00000136'), { className: 'toast_error' }) // 내용은 300자 이하로 입력해주세요.
          return false
        }
        /*
        if (
          !this.isEmpty(dataProvider.getValue(i, 'rglt_crtr_use_qty')) &&
          dataProvider.getValue(i, 'rglt_crtr_use_qty').length > 18 &&
          dataProvider.getRowState(i) !== 'createAndDeleted'
        ) {
          gridView.setCurrent({ dataRow: i, fieldName: 'rglt_crtr_use_qty' })
          this.$toast.show(this.$t('MS00000135'), { className: 'toast_error' }) // 값은 18자 이하로 입력해주세요.
          return false
        }
         */
        if (
          !this.isEmpty(dataProvider.getValue(i, 'rglt_sbst_note')) &&
          dataProvider.getValue(i, 'rglt_sbst_note').length > 1000 &&
          dataProvider.getRowState(i) !== 'createAndDeleted'
        ) {
          gridView.setCurrent({ dataRow: i, fieldName: 'rglt_sbst_note' })
          this.$toast.show(this.$t('MS00000134'), { className: 'toast_error' }) // 비고는 1000자 이하로 입력해주세요.
          return false
        }
      }
      return true
    },
  },
}
</script>
<style lang="scss" scoped></style>
