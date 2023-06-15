<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 당해/기간제 입사자 추가 -->
        <v-toolbar-title>{{ $t('LB00001082') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-row>
            <v-col cols="10">
              <v-container>
                <v-row>
                  <v-col cols="1">
                    <!-- 구분 -->
                    <div class="label_tit">{{ $t('LB00000741') }}</div>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      v-model="schData.kind"
                      label="ALL"
                      item-text="cd_nm"
                      item-value="cd"
                      :items="codes.SH00000052"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      height="30"
                    ></v-select>
                  </v-col>
                  <v-col cols="1">
                    <!-- 부서 -->
                    <div class="label_tit">{{ $t('LB00000071') }}</div>
                  </v-col>
                  <v-col cols="3">
                    <v-text-field
                      v-model="schData.org_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      @keypress.enter="search"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="1">
                    <!-- 성명 -->
                    <div class="label_tit">{{ $t('LB00000701') }}</div>
                  </v-col>
                  <v-col cols="3">
                    <v-text-field
                      v-model="schData.hmex_trgp_nm"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00000033')"
                      clearable
                      height="30"
                      @keypress.enter="search"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
            <v-col cols="2">
              <v-container>
                <v-row>
                  <v-col cols="6">
                    <!-- 초기화 -->
                    <v-btn color="" outlined height="30" @click="reset">{{ $t('LB00000029') }}</v-btn>
                  </v-col>
                  <v-col cols="6">
                    <!-- 검색 -->
                    <v-btn color="primary" depressed height="30" @click="search">{{ $t('LB00000030') }}</v-btn>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </div>
        <div class="group_padd"></div>
        <div class="realgrid_container">
          <div id="popgrid" style="width: 100%; height: 381px"></div>
        </div>
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
  data() {
    return {
      dialog: false,
      gridViewPop: '',
      dataProviderPop: '',
      codeid: ['SH00000052'],
      codes: {
        SH00000052: [{ cd: '', cd_nm: '' }], // 당해입사자,기간제
      },
      frmData: {},
      schData: {},
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
    this.user = this.$store.getters['auth/getUser']
    const fields = [
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
        header: this.$t('LB00000741'), // 구분
        name: 'cwkr_yn_nm',
        fieldName: 'cwkr_yn_nm',
        width: '100',
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
    ]
    this.dataProviderPop = new LocalDataProvider(false)
    this.dataProviderPop.setFields(fields)
    this.gridViewPop = new GridView('popgrid')
    this.gridViewPop.setDataSource(this.dataProviderPop)
    this.gridViewPop.setColumns(columns)
    this.gridViewPop.setFooters({ visible: false })
    this.gridViewPop.setStateBar({ visible: false })
    this.gridViewPop.setCheckBar({ visible: true })

    this.gridViewPop.header.height = 39
    this.gridViewPop.displayOptions.rowHeight = 40
    this.gridViewPop.footer.height = 40
    this.gridViewPop.displayOptions.fitStyle = 'fill'
    this.gridViewPop.displayOptions.selectionStyle = 'block'
    this.gridViewPop.editOptions.editable = false
  },
  methods: {
    open() {
      this.$axios
        .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
        .then((res) => {
          this.codes = res
          this.codes.SH00000052.unshift({ cd: '', cd_nm: 'ALL' })
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })

      this.dialog = true
      this.gridViewPop.refresh()
    },
    async search() {
      this.schData.type = 'curr'
      this.schData.cmpn_cd = this.user.cmpn_cd
      const rows = await this.$axios.$get(`/api/v1/she/hlth/hmexmgnt`, { params: this.schData })
      this.dataProviderPop.setRows(rows)
      this.gridViewPop.refresh()
      this.gridViewPop.setTopItem(0)
      this.totalcnt = this.gridViewPop.getItemCount()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
      this.dialog = false
    },
    reset() {
      this.schData = {}
      this.dataProviderPop.clearRows()
    },
    async returnData() {
      const rows = this.gridViewPop.getCheckedRows(true)
      if (!rows.length) {
        this.$toast.show(this.$t('MS00000558'), { className: 'toast_error' }) // 선택해 주십시오.
        return
      }
      const returnData = []
      await rows.forEach((i) => {
        returnData.push(this.dataProviderPop.getJsonRows(i, i)[0])
      })
      this.$emit('close', returnData)
      this.reset()
      this.dialog = false
    },
  },
}
</script>
