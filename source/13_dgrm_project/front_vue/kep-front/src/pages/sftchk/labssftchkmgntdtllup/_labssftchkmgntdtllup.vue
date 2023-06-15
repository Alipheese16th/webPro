<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 연구실 일상점검 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001104') }}</span>
    </div>
    <div class="work_title">
      <!-- 연구실 일상점검 -->
      <h2>{{ $t('LB00001104') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1" style="padding: 0px">
                <!--점검월-->
                <div class="label_tit">{{ $t('LB00000789') }}</div>
              </v-col>
              <v-col cols="3" style="padding: 0px">
                <div class="label_con">{{ frmData.labs_sft_chk_yrmn }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.labs_sft_chk_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정일-->
                <div class="label_tit">{{ $t('LB00000939') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검내용-->
                <div class="label_tit">{{ $t('LB00000642') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="labs_sft_txt" v-model="frmData.labs_sft_chk_txt" outlined single-line :hide-details="true" height="100" class="mt-2" dense disabled></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--안전점검대상-->
        <h3>{{ $t('LB00001113') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="container_detail">
      <v-container>
        <v-row>
          <v-col cols="1">
            <!--비고-->
            <div class="label_tit">{{ $t('LB00000095') }}</div>
          </v-col>
          <v-col cols="11">
            <v-textarea ref="labs_sft_note" v-model="frmData.labs_sft_chk_note_txt" outlined single-line :hide-details="true" height="100" class="mt-2" dense disabled></v-textarea>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <filePopup ref="filepop" :edit-chk="false" mkey1="labssftchkmgntdtllupatflgrid1" @close="filepopclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import filePopup from '../../common/atflgrid.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider
let date = new Date()

export default {
  meta: {
    title: '연구실 일상점검',
    key(route) {
      return `/sftchk/labssftchkmgntlist/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    filePopup,
  },
  data() {
    return {
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000002p: [{ cd: '', cd_nm: '' }],
      },
      totalcnt: '',
      tempData: {},
      tmpSchData: {},
      schData: {
        labs_sft_chk_id: '',
        date: '',
      },
      frmData: {
        labs_sft_chk_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_nm: '',
        labs_sft_chk_txt: '',
        labs_sft_chk_note_txt: '',
        lsch_nm: '',
        upt_dt: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001104')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    date = this.getTzDateObj(this.user.time_zone)

    date.setDate(date.getDate() - 30)
    this.schData.date = this.get_date_str_gubun(date, '-')

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_nm: '',
        wkpl_id: '',
        wkpl_nm: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_nm: '',
        fin_yn: '',
        labs_sft_chk_note_txt: '',
      }
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempWkpl = []
      res.ST00000002.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000002p = tempWkpl
      this.codes.ST00000002p.unshift({ value: '', label: 'SELECT' })

      // 그리드 세팅
      const fields = [
        {
          fieldName: 'labs_sft_chk_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_rspn_org_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_rspn_org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_mgnt_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mgnt_labs_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_tgt_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_tgt_atfl_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_exct_yn',
          dataType: ValueType.TEXT,
        },
      ]
      const columns = [
        {
          header: this.$t('LB00000757'), // 순번
          name: 'labs_sft_chk_sn',
          fieldName: 'labs_sft_chk_sn',
          width: '70',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'labs_rspn_org_nm',
          fieldName: 'labs_rspn_org_nm',
          width: '70',
        },
        {
          header: this.$t('LB00001114'), // 연구책임자명(관리감독자)
          name: 'labs_mgnt_emp_nm',
          fieldName: 'labs_mgnt_emp_nm',
          width: '150',
        },
        {
          header: this.$t('LB00001115'), // 관리 실험실 개수
          name: 'mgnt_labs_cnt',
          fieldName: 'mgnt_labs_cnt',
          width: '50',
        },
        {
          header: this.$t('LB00001117'), // 안전점검결과표
          name: 'labs_sft_chk_tgt_atfl_cnt',
          fieldName: 'labs_sft_chk_tgt_atfl_cnt',
          width: '50',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
        // Y: 파란색, N: 빨간색
        {
          header: this.$t('LB00000846'), // 점검 이행여부
          name: 'labs_sft_chk_exct_yn',
          fieldName: 'labs_sft_chk_exct_yn',
          width: '100',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const chkyn = grid.getValue(dataCell.index.itemIndex, 'labs_sft_chk_exct_yn')
            if (chkyn !== 'N') {
              return (ret.styleName = 'rgsample-blue-column')
            } else return (ret.styleName = 'rgsample-red-column')
          },
        },
      ]
      // 그리드 1 시작
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })
      gridView.editOptions.editable = false
      gridView.displayOptions.selectionStyle = 'block'

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.setColumnProperty('labs_sft_chk_sn', 'visible', false)

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        this.chkRowVal = itemIndex.dataRow // row index 값
        if (itemIndex.column === 'labs_sft_chk_tgt_atfl_cnt') {
          const atfileNo = gridView.getValue(itemIndex.itemIndex, 'labs_sft_chk_tgt_atfl_no')
          this.schData.atfile_no = atfileNo
          if (!this.isEmpty(this.schData.atfile_no)) {
            this.$refs.filepop.open(this.schData)
          }
        }
      }.bind(this)

      // 기존 데이터 조회
      this.schData.labs_sft_chk_id = this.$route.params.labssftchkmgntdtllup
      this.select()
    })
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    filepopclose(returnData) {
      gridView.commit()
    },
    async select() {
      gridView.commit()

      await this.$axios.get('/api/v1/she/sftchk/labssftchkmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const labsChkResult = res.data.labsChkResult[0]

          this.frmData.wkpl_id = labsChkResult.wkpl_id
          this.frmData.wkpl_nm = labsChkResult.wkpl_nm
          this.frmData.labs_sft_chk_id = labsChkResult.labs_sft_chk_id
          this.frmData.labs_sft_chk_yrmn = labsChkResult.labs_sft_chk_yrmn
          this.frmData.labs_sft_chk_nm = labsChkResult.labs_sft_chk_nm
          this.frmData.labs_sft_chk_txt = labsChkResult.labs_sft_chk_txt
          this.frmData.labs_sft_chk_note_txt = labsChkResult.labs_sft_chk_note_txt
          this.frmData.lsch_nm = labsChkResult.lsch_nm
          this.frmData.upt_dt = labsChkResult.upt_dt

          this.tempData = JSON.parse(JSON.stringify(this.frmData))

          dataProvider.setRows(res.data.labsChkTgtResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.totalcnt = gridView.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    goList() {
      this.$router.push({
        name: `sftchk-labssftchkmgntlist`,
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
  },
}
</script>
<style>
.rgsample-blue-column {
  color: blue;
}
.rgsample-red-column {
  color: red;
}
</style>
