<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1300px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!--자재 선택-->
        <v-toolbar-title>{{ $t('LB00000134') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!--자재코드-->
              <div class="label_tit">{{ $t('LB00000024') }}</div>
            </v-col>
            <v-col cols="4">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.mtrl_no"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
            <v-col cols="2">
              <!--자재명-->
              <div class="label_tit">{{ $t('LB00000025') }}</div>
            </v-col>
            <v-col cols="4">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.mtrl_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!--구매부서-->
              <div class="label_tit">{{ $t('LB00000529') }}</div>
            </v-col>
            <v-col cols="4">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.dept_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
            <v-col cols="2">
              <!--구매자-->
              <div class="label_tit">{{ $t('LB00000135') }}</div>
            </v-col>
            <v-col cols="4">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.emp_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!--구매일-->
              <div class="label_tit">{{ $t('LB00000530') }}</div>
            </v-col>
            <v-col cols="4" style="display: flex">
              <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="schData.sDate"
                    prepend-icon="event"
                    readonly
                    outlined
                    :hide-details="true"
                    dense
                    height="30"
                    v-bind="attrs"
                    class="comp_calendar mr-1"
                    v-on="on"
                    @keypress.enter="select()"
                  ></v-text-field>
                </template>
                <v-date-picker v-model="schData.sDate" no-title :max="schData.eDate" @input="menu1 = false"> </v-date-picker>
              </v-menu>
              <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="schData.eDate"
                    prepend-icon="event"
                    readonly
                    outlined
                    :hide-details="true"
                    dense
                    height="30"
                    v-bind="attrs"
                    class="comp_calendar"
                    v-on="on"
                    @keypress.enter="select()"
                  ></v-text-field>
                </template>
                <v-date-picker v-model="schData.eDate" no-title :min="schData.sDate" @input="menu2 = false"> </v-date-picker>
              </v-menu>
            </v-col>
            <v-col cols="2">
              <!--구매처명-->
              <div class="label_tit">{{ $t('LB00000158') }}</div>
            </v-col>
            <v-col cols="4">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.vndr_nm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
                @keypress.enter="select()"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
        <div class="sch_btn">
          <!--초기화-->
          <v-btn color="" outlined height="36" @click="refresh()">{{ $t('LB00000029') }}</v-btn>
          <!--검색-->
          <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
        </div>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 0; display: flex">
            <div class="realgrid_container" style="width: 100%">
              <div class="sub_title">
                <!--자재 목록-->
                <h3>{{ $t('LB00000133') }}</h3>
              </div>
              <div class="grid_header">
                <div class="grid_header_left">
                  <!--총--><!--건-->
                  {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
                </div>
                <div>
                  <!-- 그리드 버튼 영역 -->
                </div>
              </div>
              <div id="realgrid_pop01" style="width: 100%; height: 281px"></div>
            </div>
            <div style="padding-top: 150px">
              <v-btn class="ma-2" outlined x-small fab color="gray" @click="rightdata()">
                <v-icon dark>mdi-arrow-right</v-icon>
              </v-btn>
              <v-btn class="ma-2" outlined x-small fab color="gray" @click="leftdata()">
                <v-icon dark>mdi-arrow-left</v-icon>
              </v-btn>
            </div>
            <div class="realgrid_container" style="width: 100%">
              <div class="sub_title">
                <!--선택 자재 목록-->
                <h3>{{ $t('LB00000132') }}</h3>
              </div>
              <div class="grid_header">
                <div class="grid_header_left">
                  <!--총--><!--건-->
                  {{ $t('LB00000039') }}<em> {{ size2 }} </em>{{ $t('LB00000040') }}
                </div>
                <div>
                  <!--삭제-->
                  <v-btn color="" outlined height="30" @click="leftdata()">{{ $t('LB00000046') }}</v-btn>
                </div>
              </div>
              <div id="realgrid_pop02" style="width: 100%; height: 281px"></div>
            </div>
          </v-col>
        </v-row>
      </v-container>
      <v-card-actions class="pop_btn">
        <!--선택-->
        <v-btn color="primary" depressed width="120" height="40" @click="submit()">{{ $t('LB00000106') }}</v-btn>
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

// let gridView = ''
// let dataProvider = ''
// let gridView2 = ''
// let dataProvider2 = ''
export default {
  components: {},
  props: {
    mkey1: {
      type: String,
      default: 'realgrid_pop01',
    },
    mkey2: {
      type: String,
      default: 'realgrid_pop02',
    },
  },
  data() {
    return {
      gridView: '',
      dataProvider: '',
      gridView2: '',
      dataProvider2: '',
      dialog: false,
      schData: {},
      frmData: {},
      chkYnAll: '',
      size: 0,
      size2: 0,
      detail: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
      rowchk: false,
      menu1: false,
      menu2: false,
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
    this.dataProvider = new LocalDataProvider(false)
    this.dataProvider.setFields(fields)
    const gridId1 = document.getElementById('realgrid_pop01')
    gridId1.id = this.mkey1 + 'mtrlmultislct'
    this.gridView = new GridView(this.mkey1 + 'mtrlmultislct')
    this.gridView.setDataSource(this.dataProvider)
    const columns = [
      {
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '50',
      },
      {
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '100',
        renderer: {
          showTooltip: true,
        },
        styleName: 'left',
      },
      /*
      {
        header: this.$t('LB00000131'), // 공급업체코드
        name: 'vndr_no',
        fieldName: 'vndr_no',
        width: '100',
      },
      */
      {
        header: this.$t('LB00000158'), // 구매처명
        name: 'vndr_nm',
        fieldName: 'vndr_nm',
        width: '100',
        renderer: {
          showTooltip: true,
        },
        styleName: 'left',
      },
      {
        header: this.$t('LB00000528'), // 단위중량
        name: 'pchs_req_unit_weght_nm',
        fieldName: 'pchs_req_unit_weght_nm',
        width: '50',
        renderer: {
          showTooltip: true,
        },
        styleName: 'right',
      },
    ]
    this.gridView.setColumns(columns)
    this.gridView.setFooters({ visible: false })

    this.gridView.setStateBar({ visible: false })
    this.gridView.setCheckBar({ visible: true })
    this.gridView.setRowIndicator({ width: 50 })
    this.gridView.editOptions.editable = false
    this.gridView.displayOptions.selectionStyle = 'block'

    this.gridView.header.height = 39
    this.gridView.displayOptions.rowHeight = 40
    this.gridView.footer.height = 40
    this.gridView.displayOptions.fitStyle = 'fill'

    this.dataProvider2 = new LocalDataProvider(false)
    this.dataProvider2.setFields(fields2)
    const gridId2 = document.getElementById('realgrid_pop02')
    gridId2.id = this.mkey2 + 'mtrlmultislct'
    this.gridView2 = new GridView(this.mkey2 + 'mtrlmultislct')
    this.gridView2.setDataSource(this.dataProvider2)
    const columns2 = [
      {
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '50',
      },
      {
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '100',
        renderer: {
          showTooltip: true,
        },
        styleName: 'left',
      },
      /*
      {
        header: this.$t('LB00000131'), // 공급업체코드
        name: 'vndr_no',
        fieldName: 'vndr_no',
        width: '100',
      },
      */
      {
        header: this.$t('LB00000158'), // 구매처명
        name: 'vndr_nm',
        fieldName: 'vndr_nm',
        width: '100',
        renderer: {
          showTooltip: true,
        },
        styleName: 'left',
      },
      {
        header: this.$t('LB00000528'), // 단위중량
        name: 'pchs_req_unit_weght_nm',
        fieldName: 'pchs_req_unit_weght_nm',
        width: '50',
        renderer: {
          showTooltip: true,
        },
        styleName: 'right',
      },
    ]
    this.gridView2.setColumns(columns2)
    this.gridView2.setFooters({ visible: false })

    this.gridView2.setStateBar({ visible: false })
    this.gridView2.setCheckBar({ visible: true })
    this.gridView2.setRowIndicator({ width: 50 })
    this.gridView2.editOptions.editable = false
    this.gridView2.displayOptions.selectionStyle = 'block'

    this.gridView2.header.height = 39
    this.gridView2.displayOptions.rowHeight = 40
    this.gridView2.footer.height = 40
    this.gridView2.displayOptions.fitStyle = 'fill'
    this.gridView.onCellClicked = function (grid, index, clickData) {
      console.log(grid) // eslint-disable-line no-console
      console.log(index) // eslint-disable-line no-console
      console.log(clickData) // eslint-disable-line no-console
    }
  },
  methods: {
    open(detail, chkYnAll) {
      this.detail = detail
      this.chkYnAll = chkYnAll
      this.dialog = true
      this.gridView.refresh()
      this.gridView2.refresh()
      this.select()
    },
    async select() {
      if (this.chkYnAll === 'Y') {
        this.schData.chkYnAll = 'Y'
      } else if (this.chkYnAll === 'N') {
        this.schData.chkYnAll = 'N'
      } else {
        this.schData.chkYnAll = 'A'
      }

      const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-mtrl-multi-list`, { params: this.schData })

      this.dataProvider.setRows(rows)
      this.gridView.refresh()
      this.gridView.setTopItem(0)

      this.size = this.dataProvider.getRowCount()
    },
    refresh() {
      this.schData = {}
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.schData = {}
      this.frmData = {}
      this.dataProvider2.clearRows()
      this.size2 = this.gridView2.getItemCount()
    },
    async submit() {
      this.gridView2.checkAll(true)
      const rows = this.gridView2.getCheckedRows(true)
      if (rows.length < 1) {
        this.$toast.show(this.$t('MS00000030'), { className: 'toast_error' }) // 선택된 데이터가 없습니다.
        return false
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.dataProvider2.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.dataProvider2.clearRows()
      this.reset()
    },
    async rightdata() {
      const rows = this.gridView.getCheckedRows(true)
      if (rows.length < 0) {
        this.$toast.show(this.$t('MS00000030'), { className: 'toast_error' }) // 선택된 데이터가 없습니다.
        return false
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.dataProvider.getJsonRows(i, i)[0])
      })
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          const fields = ['mtrl_no', 'vndr_no']
          const values = [i.mtrl_no, i.vndr_no]
          const options = {
            fields,
            values,
            allFields: true,
            startIndex: 0,
            wrap: true,
            caseSensitive: false,
            partialMatch: true,
          }
          const passyn1 = this.gridView2.searchItem(options)
          if (passyn1 === -1) {
            this.dataProvider2.addRow({
              mtrl_no: i.mtrl_no,
              mtrl_nm: i.mtrl_nm,
              vndr_no: i.vndr_no,
              vndr_nm: i.vndr_nm,
              macl_cd: i.macl_cd,
              macl_cd_nm: i.macl_cd_nm,
              pchs_unit_cd: i.pchs_unit_cd,
              pchs_req_unit_cd: i.pchs_req_unit_cd,
              pchs_unit_nm: i.pchs_unit_nm,
              pchs_req_unit_nm: i.pchs_req_unit_nm,
              pchs_unit_weght: i.pchs_unit_weght,
              pchs_req_unit_weght: i.pchs_req_unit_weght,
              pchs_req_unit_weght_nm: i.pchs_req_unit_weght_nm,
              pchs_unit_nweght_qty: i.pchs_unit_nweght_qty,
              impt_yn: i.impt_yn,
            })
          } else {
            this.$toast.show(i.mtrl_nm + this.$t('MS00000036'), { className: 'toast_error' }) // 자재는 이미 선택 되었습니다.
          }
        })
      }
      this.gridView.checkAll(false)
      this.size2 = this.gridView2.getItemCount()
    },
    leftdata() {
      const checkedRows = this.gridView2.getCheckedRows()
      this.dataProvider2.removeRows(checkedRows)
      this.size2 = this.gridView2.getItemCount()
    },
  },
}
// 그리드 세팅
const fields = [
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght_nm',
    dataType: ValueType.TEXT,
    valueCallback(prod, dataRow, fieldName, fieldNames, values) {
      const pchsReqUnitWeght = values[fieldNames.indexOf('pchs_req_unit_weght')]
      if (isNaN(pchsReqUnitWeght)) {
        return ''
      } else {
        const pchsReqUnitNm = values[fieldNames.indexOf('pchs_req_unit_nm')]
        return pchsReqUnitWeght + '' + pchsReqUnitNm
      }
    },
  },
  {
    fieldName: 'pchs_unit_nweght_qty',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'impt_yn',
    dataType: ValueType.TEXT,
  },
]
const fields2 = [
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_nweght_qty',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'impt_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
