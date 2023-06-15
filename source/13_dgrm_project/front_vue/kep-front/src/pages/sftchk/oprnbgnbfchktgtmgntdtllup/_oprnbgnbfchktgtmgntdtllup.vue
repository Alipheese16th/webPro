<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업 시작 전 점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00000737') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업 시작 전 점검 대상 관리-->
      <h2>{{ $t('LB00000737') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
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
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30" disabled></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 위치-->
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
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--설비목록-->
        <h3>{{ $t('LB00000754') }}</h3>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업 시작 전 점검 대상 관리',
    key(route) {
      return `/sftchk/oprnbgnbfchktgtmgnt/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      usg_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      selST00000007: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000002', 'ST00000007'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000007: [{ cd: '', cd_nm: '' }],
      },
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
        oprb_chk_supe_emp_id: '',
        oprb_chk_supe_emp_nm: '',
        oprb_chk_supe_spic_emp_id: '',
        oprb_chk_supe_spic_emp_nm: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        oprb_chk_dtl_loc_nm: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_note_txt: '',
        usg_yn: '',
        lsch_nm: '',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000737')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    date = this.getTzDateObj(user.time_zone)

    date.setDate(date.getDate() - 30)
    this.schData.date = this.get_date_str_gubun(date, '-')

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_supe_emp_nm: '',
        oprb_chk_supe_emp_id: '',
        oprb_chk_supe_spic_emp_nm: '',
        oprb_chk_supe_spic_emp_id: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        usg_yn: '',
        cmpy_cd: '',
      }
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'SELECT' })
        this.selST00000007.cd.push('')
        this.selST00000007.cd_nm.push('SELECT')
        res.ST00000007.forEach((i) => {
          this.selST00000007.cd.push(i.cd)
          this.selST00000007.cd_nm.push(i.cd_nm)
        })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'oprb_chk_eqp_sn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_eqp_type_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_eqp_nm',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000757'), // 순번
            name: 'oprb_chk_eqp_sn',
            fieldName: 'oprb_chk_eqp_sn',
            width: '300',
          },
          {
            header: this.$t('LB00000758'), // 설비 종류
            name: 'oprb_chk_eqp_type_cd',
            fieldName: 'oprb_chk_eqp_type_cd',
            width: '200',
            sortable: false,
            lookupDisplay: true,
            values: this.selST00000007.cd,
            labels: this.selST00000007.cd_nm,
            editable: false,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: false,
              displayLabels: 'label',
            },
            styleName: 'left',
          },
          {
            header: this.$t('LB00000760'), // 설비명
            name: 'oprb_chk_eqp_nm',
            fieldName: 'oprb_chk_eqp_nm',
            width: '300',
            styleName: 'left',
            editable: false,
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
        gridView.setCheckBar({ visible: false })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('oprb_chk_eqp_sn', 'visible', false)

        this.schData.oprb_chk_tgt_id = this.$route.params.oprnbgnbfchktgtmgntdtllup
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
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
    add() {
      gridView.commit()
      dataProvider.addRow({
        oprb_chk_eqp_sn: '',
        oprb_chk_eqp_type_cd: '',
        oprb_chk_eqp_nm: '',
      })
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-oprnbgnbfchktgtmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    async select() {
      gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      await this.$axios.get('/api/v1/she/sftchk/oprnbgnbfchkgtmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const oprbChkResult = res.data.oprbChkResult[0]

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
    },
  },
}
</script>
<style></style>
