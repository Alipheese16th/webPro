<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업 시작 전 점검 관리 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00000785') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업 시작 전 점검 관리 -->
      <h2>{{ $t('LB00000785') }}</h2>
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
              <v-col cols="1">
                <!--관리 감독자-->
                <div class="label_tit">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.oprb_chk_supe_emp_nm }}</div>
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
                <!--점검 대상명-->
                <div class="label_tit">{{ $t('LB00000667') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.oprb_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--부관리 감독자-->
                <div class="label_tit">{{ $t('LB00001275') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.oprb_chk_supe_spic_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사용 여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.usg_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검위치-->
                <div class="label_tit">{{ $t('LB00000739') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.oprb_chk_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.oprb_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.oprb_chk_note_txt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="realgrid_container">
      <div class="group_padd"></div>
      <div class="sub_title">
        <!--점검 목록-->
        <h3>{{ $t('LB00001048') }} {{ $t('LB00000118') }}</h3>
      </div>
      <v-row>
        <!--점검월-->
        <div class="label_tit" style="padding-top: 15px; padding-left: 15px">{{ $t('LB00000789') }}</div>
        <v-col cols="2" style="padding-left: 20px">
          <v-menu v-model="menu" :close-on-content-click="false" :nudge-right="40" min-width="290px">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="schData.oprb_chk_schd_mt"
                prepend-icon="event"
                readonly
                outlined
                :hide-details="true"
                dense
                height="30"
                v-bind="attrs"
                class="comp_calendar"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="schData.oprb_chk_schd_mt" type="month" no-title @input="menu = false" @change="select"> </v-date-picker>
          </v-menu>
        </v-col>
      </v-row>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--제외처리-->
          <v-btn color="primary" width="120" height="40" :disabled="!authYn" @click="remove">{{ $t('LB00000602') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <oprnChkEdtPop ref="oprnChkEdtPop" mkey1="oprnbgnbfchkmgntdltlup1" @close="popClose" />
    <oprnChkLupPop ref="oprnChkLupPop" mkey1="oprnbgnbfchkmgntdltlup2" @close="popClose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import oprnChkEdtPop from '../oprnbgnbfchkmgntchkrsltedt'
import oprnChkLupPop from '../oprnbgnbfchkmgntchkrsltlup'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업 시작 전 점검 관리',
    key(route) {
      return `/sftchk/oprnbgnbfchkmgnt/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    oprnChkEdtPop,
    oprnChkLupPop,
  },
  data() {
    return {
      schData: {
        oprb_chk_tgt_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        oprb_chk_tgt_id: '',
        oprb_chk_rspn_emp_id: '',
        oprb_chk_rspn_emp_nm: '',
        oprb_chk_spic_emp_id: '',
        oprb_chk_spic_emp_nm: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        oprb_chk_dtl_loc_nm: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_note_txt: '',
        usg_yn: '',
        lsch_nm: '',
      },
      menu: false,
      authYn: false,
      isSysAdmin: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000785')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    // 시스템관리자 권한 체크
    user.roles.forEach((i) => {
      if (i === 'RZZZZ') {
        this.isSysAdmin = true
      }
    })
    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    date = this.getTzDateObj(user.time_zone)

    this.schData.oprb_chk_schd_mt = this.get_date_str_gubun(date, '-').substring(0, 7)

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_dt: '',
        oprb_chk_rspn_emp_nm: '',
        oprb_chk_rspn_emp_id: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        oprb_chk_prst_cd: '',
        cmpy_cd: '',
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'oprb_chk_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_schd_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_rspn_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_spic_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_prst_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_prst_cd_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crin_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'oprb_chk_dt_hm',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00001379'), // 점검계획일
        name: 'oprb_chk_schd_dt',
        fieldName: 'oprb_chk_schd_dt',
        width: '100',
        styleName: 'link center',
      },
      {
        header: this.$t('LB00000648'), // 점검일시
        name: 'oprb_chk_dt_hm',
        fieldName: 'oprb_chk_dt_hm',
        width: '200',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000738'), // 관리 감독자
        name: 'oprb_chk_rspn_emp_nm',
        fieldName: 'oprb_chk_rspn_emp_nm',
        width: '200',
        styleName: 'center',
      },
      {
        header: this.$t('LB00001275'), // 부관리 감독자
        name: 'oprb_chk_spic_emp_nm',
        fieldName: 'oprb_chk_spic_emp_nm',
        width: '200',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000791'), // 점검결과
        name: 'oprb_chk_prst_cd',
        fieldName: 'oprb_chk_prst_cd',
        width: '150',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000646'), // 점검결과
        name: 'oprb_chk_prst_cd_nm',
        fieldName: 'oprb_chk_prst_cd_nm',
        width: '300',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000792'), // 시정지시서 발행여부
        name: 'crin_yn',
        fieldName: 'crin_yn',
        width: '150',
      },
    ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.displayOptions.selectionStyle = 'block'
    gridView.setDataSource(dataProvider)

    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: true, width: 50 })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('oprb_chk_prst_cd', 'visible', false)

    gridView.onCellClicked = function (grid, index, clickData) {
      gridView.commit()
      if (index.itemIndex > -1) this.openDetail(index)
    }.bind(this)

    this.schData.oprb_chk_id = this.$route.params.oprnbgnbfchkmgntdtllup
    this.select()
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
    toDateStringfromGubun(str, gubun) {
      const arr = str.split(gubun)
      return arr.join('')
    },
    popOpen(key) {
      this.$refs.oprnChkEdtPop.open(key)
    },
    popLupOpen(key) {
      this.$refs.oprnChkLupPop.open(key)
    },
    popClose(returnData) {
      // 재조회
      this.select()
    },
    goList() {
      this.$router.push({
        name: 'sftchk-oprnbgnbfchkmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    async remove() {
      const checkedRows = gridView.getCheckedRows()
      const data = []
      for (let i = 0; i < checkedRows.length; i++) {
        if (gridView.getValue(checkedRows[i], 'oprb_chk_prst_cd') !== '10') {
          // 점검 결과가 미진행 상태가 아님
          this.$toast.show(this.$t('MS00000343'), { className: 'toast_error' }) // 제외처리는 미진행 상태건만 처리가능합니다.
          return
        }
        data.push({ oprb_chk_id: gridView.getValue(checkedRows[i], 'oprb_chk_id') })
      }
      if (data.length === 0) {
        this.$toast.show(this.$t('MS00000344'), { className: 'toast_error' }) // 제외처리할 점검을 선택해주세요.
        return
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000345')) // 제외처리 하시겠습니까?
      if (!res) {
        return
      }
      const param = { excptList: data }
      await this.$axios.put('/api/v1/she/sftchk/oprnbgnbfchkexcept', param).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000346')) // '처리되었습니다.'
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async select() {
      gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      if (!this.isEmpty(this.schData.oprb_chk_schd_mt)) {
        this.schData.sch_oprb_chk_schd_mt = this.toDateStringfromGubun(this.schData.oprb_chk_schd_mt, '-')
      }
      await this.$axios.get('/api/v1/she/sftchk/oprnbgnbfchkmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const oprbChkResult = res.data.oprbChkResult[0]
          this.frmData.oprb_chk_rspn_emp_id = oprbChkResult.oprb_chk_rspn_emp_id
          this.frmData.oprb_chk_rspn_emp_nm = oprbChkResult.oprb_chk_rspn_emp_nm
          this.frmData.oprb_chk_spic_emp_id = oprbChkResult.oprb_chk_spic_emp_id
          this.frmData.oprb_chk_spic_emp_nm = oprbChkResult.oprb_chk_spic_emp_nm
          this.frmData.oprb_chk_supe_emp_id = oprbChkResult.oprb_chk_supe_emp_id
          this.frmData.oprb_chk_supe_emp_nm = oprbChkResult.oprb_chk_supe_emp_nm
          this.frmData.oprb_chk_supe_spic_emp_id = oprbChkResult.oprb_chk_supe_spic_emp_id
          this.frmData.oprb_chk_supe_spic_emp_nm = oprbChkResult.oprb_chk_supe_spic_emp_nm
          this.frmData.oprb_chk_dtl_loc_nm = oprbChkResult.oprb_chk_dtl_loc_nm
          this.frmData.oprb_chk_wkpl_loc_id = oprbChkResult.oprb_chk_wkpl_loc_id
          this.frmData.oprb_chk_wkpl_loc_nm = oprbChkResult.oprb_chk_wkpl_loc_nm
          this.frmData.oprb_chk_tgt_nm = oprbChkResult.oprb_chk_tgt_nm
          this.frmData.oprb_chk_tgt_id = oprbChkResult.oprb_chk_tgt_id
          this.frmData.oprb_chk_note_txt = oprbChkResult.oprb_chk_note_txt
          this.frmData.wkpl_id = oprbChkResult.wkpl_id
          this.frmData.wkpl_nm = oprbChkResult.wkpl_nm
          this.frmData.usg_yn = oprbChkResult.usg_yn
          this.frmData.lsch_nm = oprbChkResult.lsch_nm

          dataProvider.setRows(res.data.oprbChkEqpResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.totalcnt = gridView.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
      const user = this.$store.getters['auth/getUser']
      // 권한 체크
      if (
        (!this.isEmpty(this.frmData.oprb_chk_rspn_emp_id) && this.frmData.oprb_chk_rspn_emp_id === user.emp_no) ||
        (!this.isEmpty(this.frmData.oprb_chk_spic_emp_id) && this.frmData.oprb_chk_spic_emp_id === user.emp_no)
      ) {
        this.authYn = true
      } else {
        this.authYn = false
      }
    },
    openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column === 'oprb_chk_schd_dt') {
        const value = gridView.getValue(index.itemIndex, 'oprb_chk_id')
        // 권한 체크
        if (!this.isEmpty(value)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'OP', oprb_chk_id: value } }).then((res) => {
            // eslint-disable-next-line no-console
            if (res === 'Success' || this.isSysAdmin === true) {
              this.popOpen(value)
            } else {
              this.popLupOpen(value)
            }
          })
        }
      }
    },
  },
}
</script>
<style></style>
