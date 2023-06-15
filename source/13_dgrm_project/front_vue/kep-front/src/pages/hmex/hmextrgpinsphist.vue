<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 수검이력 -->
        <v-toolbar-title>{{ $t('LB00001099') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 사업장 -->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 부서 -->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.org_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 성명 -->
                <div class="label_tit">{{ $t('LB00000701') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hmex_trgp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 사번 -->
                <div class="label_tit">{{ $t('LB00000073') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hmex_tgt_emp_id }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 입사일 -->
                <div class="label_tit">{{ $t('LB00000851') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.jncm_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 퇴사일 -->
                <div class="label_tit">{{ $t('LB00001058') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rsgn_dt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </div>
        <div class="group_padd"></div>
        <div class="realgrid_container">
          <div id="realgrid_insphist" style="width: 100%; height: 381px"></div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

export default {
  props: {
    mkey: {
      type: String,
      default: 'insphistgrid',
    },
  },
  data() {
    return {
      dialog: false,
      gridViewPop: '',
      dataProviderPop: '',
      frmData: {},
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
    const fields = [
      {
        fieldName: 'hmex_year',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_insp_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_insp_orga_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'deal_mtra_nm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00001055'), // 검진년도
        name: 'hmex_year',
        fieldName: 'hmex_year',
        width: '70',
      },
      {
        header: this.$t('LB00001100'), // 검진종류
        name: 'hmex_cl_nm',
        fieldName: 'hmex_cl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001101'), // 수검일
        name: 'hmex_insp_dt',
        fieldName: 'hmex_insp_dt',
        width: '100',
      },
      {
        header: this.$t('LB00001093'), // 검진기관
        name: 'hmex_insp_orga_nm',
        fieldName: 'hmex_insp_orga_nm',
        width: '200',
      },
      {
        header: this.$t('LB00001041'), // 취급물질
        name: 'deal_mtra_nm',
        fieldName: 'deal_mtra_nm',
        width: '100',
      },
    ]

    this.dataProviderPop = new LocalDataProvider(false)
    this.dataProviderPop.setFields(fields)
    const gridId = document.getElementById('realgrid_insphist')
    gridId.id = this.mkey + 'insphist'
    this.gridViewPop = new GridView(this.mkey + 'insphist')
    this.gridViewPop.setDataSource(this.dataProviderPop)
    this.gridViewPop.setColumns(columns)
    this.gridViewPop.setFooters({ visible: false })
    this.gridViewPop.setStateBar({ visible: false })
    this.gridViewPop.setCheckBar({ visible: false })

    this.gridViewPop.header.height = 39
    this.gridViewPop.displayOptions.rowHeight = 40
    this.gridViewPop.footer.height = 40
    this.gridViewPop.displayOptions.fitStyle = 'fill'
    this.gridViewPop.displayOptions.selectionStyle = 'block'
    this.gridViewPop.editOptions.editable = false
  },
  methods: {
    open(empNo) {
      this.frmData.emp_no = empNo
      this.search()
      this.dialog = true
    },
    search() {
      this.$axios.$get(`/api/v1/she/hlth/hmexrslt/${this.frmData.emp_no}`).then((res) => {
        // this.frmData.wkpl_nm = res.wkpl_nm
        this.frmData.org_nm = res.org_nm
        this.frmData.hmex_trgp_nm = res.hmex_trgp_nm
        this.frmData.hmex_tgt_emp_id = res.hmex_tgt_emp_id
        this.frmData.jncm_dt = res.jncm_dt
        this.frmData.rsgn_dt = res.rsgn_dt
        this.$set(this.frmData, 'wkpl_nm', res.wkpl_nm)

        this.dataProviderPop.setRows(res.rslt_list)
        this.gridViewPop.refresh()
        this.gridViewPop.setTopItem(0)
      })
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
  },
}
</script>
