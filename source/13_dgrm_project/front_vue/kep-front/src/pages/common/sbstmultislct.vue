<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1100px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!--물질선택-->
        <v-toolbar-title>{{ $t('LB00000346') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_popSch">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!--CAS No. 또는 물질명-->
              <div class="label_tit">{{ $t('LB00000594') }}</div>
            </v-col>
            <v-col cols="10">
              <!--입력하세요-->
              <v-text-field
                v-model="schData.sbst_nm"
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
                <!--물질목록-->
                <h3>{{ $t('LB00000334') }}</h3>
              </div>
              <div class="grid_header" style="display: flex">
                <div class="grid_header_left">
                  <!--총--><!--건-->
                  {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
                </div>
                <div class="grid_header_left">
                  <span class="label_info" style="padding-bottom: 6px; padding-top: 6px"></span>
                  <!-- Cas No. 조회가 되지 않을 경우 물질명에 "신규" 입력 후 검색 -->
                  {{ $t('LB00000518') }}
                </div>
                <div>
                  <!-- 그리드 버튼 영역 -->
                </div>
              </div>
              <div id="sbstmultislct_pop01" style="width: 100%; height: 281px"></div>
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
                <!--선택 물질목록-->
                <h3>{{ $t('LB00000347') }}</h3>
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
              <div id="sbstmultislct_pop02" style="width: 100%; height: 281px"></div>
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
      default: 'sbstmultislct_pop01',
    },
    mkey2: {
      type: String,
      default: 'sbstmultislct_pop02',
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
      rowchk: false,
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
    const gridId1 = document.getElementById('sbstmultislct_pop01')
    gridId1.id = this.mkey1 + 'sbstmultislct'
    this.gridView = new GridView(this.mkey1 + 'sbstmultislct')
    this.gridView.setDataSource(this.dataProvider)
    const columns = [
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '100',
        styleName: 'left',
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
    const gridId2 = document.getElementById('sbstmultislct_pop02')
    gridId2.id = this.mkey2 + 'sbstmultislct'
    this.gridView2 = new GridView(this.mkey2 + 'sbstmultislct')
    this.gridView2.setDataSource(this.dataProvider2)
    const columns2 = [
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_ko_nm',
        fieldName: 'sbst_ko_nm',
        width: '100',
        styleName: 'left',
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
  },
  methods: {
    open(detail, chkYnAll) {
      this.detail = detail
      this.chkYnAll = chkYnAll
      this.dialog = true
      this.gridView.refresh()
      this.gridView2.refresh()
      //  this.select()
    },
    async select() {
      if (this.isEmpty(this.schData.sbst_nm)) {
        this.$toast.show(this.$t('MS00000009'), { className: 'toast_error' }) // 물질명 또는 CASNO를 입력해주세요.
      } else {
        if (this.chkYnAll === 'Y') {
          this.schData.chkYnAll = 'Y'
        } else if (this.chkYnAll === 'N') {
          this.schData.chkYnAll = 'N'
        } else {
          this.schData.chkYnAll = 'A'
        }

        const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-sbst-list`, { params: this.schData })

        this.dataProvider.setRows(rows)
        this.gridView.refresh()
        this.gridView.setTopItem(0)

        this.size = this.dataProvider.getRowCount()
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
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
          const fields = ['sbst_no']
          const values = [i.sbst_no]
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
              sbst_no: i.sbst_no,
              cas_no: i.cas_no,
              sbst_ko_nm: i.sbst_ko_nm,
              sbst_en_nm: i.sbst_en_nm,
              ke_no: i.ke_no,
              use_yn: i.use_yn,
              sbst_type_cd: i.sbst_type_cd,
              sbst_type_nm: i.sbst_type_nm,
              kyin_yn: i.kyin_yn,
            })
          } else {
            this.$toast.show(i.sbst_ko_nm + this.$t('MS00000036'), { className: 'toast_error' }) // 자재는 이미 선택 되었습니다.
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
    fieldName: 'sbst_no',
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
    fieldName: 'sbst_en_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'ke_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'use_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'kyin_yn',
    dataType: ValueType.TEXT,
  },
]
const fields2 = [
  {
    fieldName: 'sbst_no',
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
    fieldName: 'sbst_en_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'ke_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'use_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbst_type_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'kyin_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
