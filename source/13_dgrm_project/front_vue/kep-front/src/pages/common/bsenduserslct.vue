<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="700px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!--협력사 작업자 조회/선택-->
        <v-toolbar-title>{{ $t('LB00000702') }}</v-toolbar-title>
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
                  <v-col cols="1">
                    <!--협력사명-->
                    <div class="label_tit">{{ $t('LB00000699') }}</div>
                  </v-col>
                  <v-col cols="3">
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
                      @keypress.enter="searchBsendUser()"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="1">
                    <!--성명-->
                    <div class="label_tit">{{ $t('LB00000701') }}</div>
                  </v-col>
                  <v-col cols="3">
                    <!--입력하세요-->
                    <v-text-field
                      ref="oprr_nm"
                      v-model="searchdatas.oprr_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      maxlength="99"
                      @click:clear="onClearClicked()"
                      @keypress.enter="searchBsendUser()"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3" style="text-align: right">
                    <!--검색-->
                    <v-btn color="primary" depressed height="30" @click="searchBsendUser()">{{ $t('LB00000030') }}</v-btn>
                  </v-col>
                </v-row>
              </div>
              <div class="group_padd"></div>
              <div id="bsenduserslctgrid" style="height: 420px"></div>
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
      default: 'bsendusergrid',
    },
  },
  data() {
    return {
      bsendusergridView: '',
      bsenduserdataProvider: '',
      dialog: false,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      expanded: false,
      frmData: {},
      searchdatas: {
        otsr_con_bsen_nm: '',
        oprr_nm: '',
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
    this.bsenduserdataProvider = new LocalDataProvider(false)
    this.bsenduserdataProvider.setFields(bsendfields)
    const gridId1 = document.getElementById('bsenduserslctgrid')
    gridId1.id = this.mkey + 'bsenduserslct'
    this.bsendusergridView = new GridView(this.mkey + 'bsenduserslct')
    this.bsendusergridView.setDataSource(this.bsenduserdataProvider)
    const bsendcolumns = [
      {
        header: this.$t('LB00000699'), // 협력사명
        name: 'otsr_con_bsen_nm',
        fieldName: 'otsr_con_bsen_nm',
        width: '50',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000701'), // 성명
        name: 'oprr_nm',
        fieldName: 'oprr_nm',
        width: '50',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000703'), // 작업자 번호
        name: 'oprr_id',
        fieldName: 'oprr_id',
        width: '50',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000704'), // 자격구분
        name: 'oprn_cl_nm',
        fieldName: 'oprn_cl_nm',
        width: '50',
        styleName: 'left',
      },
    ]
    this.bsendusergridView.setColumns(bsendcolumns)
    this.bsendusergridView.setFooters({ visible: false })

    this.bsendusergridView.setStateBar({ visible: false })
    this.bsendusergridView.setCheckBar({ visible: false })
    this.bsendusergridView.editOptions.editable = false
    this.bsendusergridView.displayOptions.selectionStyle = 'block'

    this.bsendusergridView.header.height = 39
    this.bsendusergridView.displayOptions.rowHeight = 40
    this.bsendusergridView.footer.height = 40
    this.bsendusergridView.displayOptions.fitStyle = 'fill'
  },
  methods: {
    open(frmData, multiYn) {
      // frmData = param ,multiYn = true 일 경우 멀티 선택 가능
      this.searchdatas = frmData
      if (frmData.otsr_con_bsen_nm !== null && frmData.otsr_con_bsen_nm !== undefined) {
        this.searchdatas.otsr_con_bsen_nm = frmData.otsr_con_bsen_nm
      }
      // this.multiYn = multiYn
      this.bsendusergridView.setCheckBar({ visible: true, exclusive: !this.multiYn })
      this.dialog = true
      this.bsendusergridView.refresh()
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
      this.bsenduserdataProvider.setFields(bsendfields)
      this.bsendusergridView.refresh()
    },
    onClearClicked() {
      this.searchdatas.otsr_con_bsen_id = ''
      this.searchdatas.otsr_con_bsen_nm = ''
      this.searchdatas.oprr_nm = ''
      this.bsendusergridView.refresh()
    },
    async searchBsendUser() {
      if (!this.searchdatas.otsr_con_bsen_nm) {
        this.$toast.show(this.$t('MS00000284'), { className: 'toast_error' }) // 협력사명을 입력해주세요.
        return false
      }
      const rows = await this.$axios.$get(`/api/v1/comm/pop/commpop-bsend-user-list`, { params: this.searchdatas })
      this.bsenduserdataProvider.setRows(rows)
      this.bsendusergridView.refresh()
      this.bsendusergridView.setTopItem(0)
      if (rows.length === 1) {
        this.returnDataOne(rows)
      }
    },
    async returnData() {
      this.bsendusergridView.commit()
      const items = this.bsendusergridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000283'), { className: 'toast_error' }) // 선택한 데이터가 없습니다.
        return
      }
      const rows = this.bsendusergridView.getCheckedRows(true)
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.bsenduserdataProvider.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
    },
    async returnDataOne(oneItem) {
      const returnData = []
      await returnData.push(this.bsenduserdataProvider.getJsonRows(0, 0)[0])
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
// 그리드 세팅
const bsendfields = [
  {
    fieldName: 'oprr_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'otsr_con_bsen_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'otsr_con_bsen_id',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'oprr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'oprn_cl_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'oprn_cl_nm',
    dataType: ValueType.TEXT,
  },
]
</script>
