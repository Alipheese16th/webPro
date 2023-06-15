<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="400px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!--협력사 조회/선택-->
        <v-toolbar-title>{{ $t('LB00000698') }}</v-toolbar-title>
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
                    <!--협력사명-->
                    <div class="label_tit">{{ $t('LB00000699') }}</div>
                  </v-col>
                  <v-col cols="7">
                    <!--입력하세요-->
                    <v-text-field
                      ref="otsr_con_bsen_nm"
                      v-model="searchdatas.otsr_con_bsen_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      maxlength="99"
                      @click:clear="onClearClicked()"
                      @keypress.enter="searchBsend()"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3" style="text-align: right">
                    <!--검색-->
                    <v-btn color="primary" depressed height="30" @click="searchBsend()">{{ $t('LB00000030') }}</v-btn>
                  </v-col>
                </v-row>
              </div>
              <div class="group_padd"></div>
              <div id="bsendslctgrid" style="height: 420px"></div>
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
      default: 'bsendgrid',
    },
  },
  data() {
    return {
      bsendgridView: '',
      bsenddataProvider: '',
      dialog: false,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      expanded: false,
      frmData: {},
      searchdatas: {
        otsr_con_bsen_nm: '',
        usr_nm: '',
        otsr_con_bsen_id: '',
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
    this.bsenddataProvider = new LocalDataProvider(false)
    this.bsenddataProvider.setFields(bsendfields)
    const gridId1 = document.getElementById('bsendslctgrid')
    gridId1.id = this.mkey + 'bsendslct'
    this.bsendgridView = new GridView(this.mkey + 'bsendslct')
    this.bsendgridView.setDataSource(this.bsenddataProvider)
    const bsendcolumns = [
      {
        header: this.$t('LB00000699'), // 협력사명
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '50',
        styleName: 'left',
      },
    ]
    this.bsendgridView.setColumns(bsendcolumns)
    this.bsendgridView.setFooters({ visible: false })

    this.bsendgridView.setStateBar({ visible: false })
    this.bsendgridView.setCheckBar({ visible: false })
    this.bsendgridView.editOptions.editable = false
    this.bsendgridView.displayOptions.selectionStyle = 'block'

    this.bsendgridView.header.height = 39
    this.bsendgridView.displayOptions.rowHeight = 40
    this.bsendgridView.footer.height = 40
    this.bsendgridView.displayOptions.fitStyle = 'fill'
    this.bsendgridView.onCurrentRowChanged = function (grid, oldRow, newRow) {
      grid.checkRow(newRow, true, true)
    }
    this.bsendgridView.onCellDblClicked = function () {
      this.returnData()
    }.bind(this)
  },
  methods: {
    open(frmData, multiYn) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      // this.frmData = frmData
      // if (frmData.usr_nm !== null && frmData.usr_nm !== undefined) {
      //   this.searchdatas.usr_nm = frmData.usr_nm
      // }
      // this.multiYn = multiYn
      this.bsendgridView.setCheckBar({ visible: true, exclusive: !this.multiYn })
      this.dialog = true
      this.bsendgridView.refresh()
      setTimeout(() => this.$refs.otsr_con_bsen_nm.$refs.input.focus(), 100)
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
      this.bsenddataProvider.setFields(bsendfields)
      this.bsendgridView.refresh()
    },
    onClearClicked() {
      this.searchdatas.otsr_con_bsen_id = ''
      this.searchdatas.otsr_con_bsen_nm = ''
      this.bsendgridView.refresh()
    },
    async searchBsend() {
      const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-bsend-list`, { params: this.searchdatas })
      this.bsenddataProvider.setRows(rows)
      this.bsendgridView.refresh()
      this.bsendgridView.setTopItem(0)
      if (rows.length === 1) {
        this.returnDataOne(rows)
      }
    },
    async returnData() {
      this.bsendgridView.commit()
      const items = this.bsendgridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }
      const rows = this.bsendgridView.getCheckedRows(true)
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.bsenddataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
    },
    async returnDataOne(oneItem) {
      const returnData = []
      await returnData.push(this.bsenddataProvider.getJsonRows(0, 0)[0])
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
// 그리드 세팅
const bsendfields = [
  {
    fieldName: 'otsr_con_bsen_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'otsr_con_bsen_nm',
    dataType: ValueType.TEXT,
  },
]
</script>
