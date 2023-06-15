<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 유해물질 점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001245') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 유해물질 점검 대상 관리-->
      <h2>{{ $t('LB00001245') }}</h2>
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
                <!--점검 담당자-->
                <div class="label_tit">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_supe_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000906') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.chec_cl_cd_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.circ_chk_tgt_nm }}</div>
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
                <!--점검주기-->
                <div class="label_tit">
                  {{ $t('LB00000907') }}
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                    </template>
                    <span>{{ $t('MS00000253') }}<br />{{ $t('MS00000254') }}</span>
                  </v-tooltip>
                </div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_cyc_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.circ_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.circ_chk_note_txt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <h3>{{ $t('LB00000908') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ totalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
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
    title: '작업장 유해물질 점검 대상 관리',
    key(route) {
      return `/sftchk/wknstourchktgtmgntchlist/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      totalcnt: 0,
      usg_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      selSH00000022: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000002', 'ST00000018', 'SH00000021', 'SH00000022'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000018: [{ cd: '', cd_nm: '' }],
        SH00000021: [{ cd: '', cd_nm: '' }],
        SH00000022: [{ cd: '', cd_nm: '' }],
      },
      schData: {
        circ_chk_tgt_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        circ_chk_tgt_id: '',
        circ_chk_supe_emp_id: '',
        circ_chk_supe_emp_nm: '',
        circ_chk_wkpl_loc_id: '',
        circ_chk_wkpl_loc_nm: '',
        circ_chk_dtl_loc_nm: '',
        circ_chk_tgt_nm: '',
        circ_chk_note_txt: '',
        chec_cl_cd: '',
        chec_cl_cd_nm: '',
        usg_yn: '',
        circ_chk_cyc_cd: '',
        circ_chk_cyc_cd_nm: '',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001245')
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
        circ_chk_tgt_nm: '',
        circ_chk_supe_emp_nm: '',
        circ_chk_supe_emp_id: '',
        circ_chk_wkpl_loc_id: '',
        circ_chk_wkpl_loc_nm: '',
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
        this.codes.ST00000018.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000021.unshift({ cd: '', cd_nm: 'SELECT' })
        this.selSH00000022.cd.push('')
        this.selSH00000022.cd_nm.push('SELECT')
        res.SH00000022.forEach((i) => {
          this.selSH00000022.cd.push(i.cd)
          this.selSH00000022.cd_nm.push(i.cd_nm)
        })

        // 그리드 세팅
        const fields = [
          {
            fieldName: 'circ_chk_atnd_sn', // 기본키로 쓰임
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnc_emp_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_dept_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_tphn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_emad',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'circ_chk_atnd_pstn_nm',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'circ_chk_atnd_cl_cd',
            fieldName: 'circ_chk_atnd_cl_cd',
            width: '100',
            sortable: false,
            lookupDisplay: true,
            values: this.selSH00000022.cd,
            labels: this.selSH00000022.cd_nm,
            styleName: 'left',
            editable: false,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00000701'), // 성명
            name: 'circ_chk_atnd_nm',
            fieldName: 'circ_chk_atnd_nm',
            width: '100',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000766'), // 직책
            name: 'circ_chk_atnd_pstn_nm',
            fieldName: 'circ_chk_atnd_pstn_nm',
            width: '100',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000909'), // 회사명
            name: 'circ_chk_atnd_dept_nm',
            fieldName: 'circ_chk_atnd_dept_nm',
            width: '100',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000723'), // 전화번호
            name: 'circ_chk_atnd_tphn',
            fieldName: 'circ_chk_atnd_tphn',
            width: '200',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000910'), // 이메일주소
            name: 'circ_chk_atnd_emad',
            fieldName: 'circ_chk_atnd_emad',
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
        gridView.setRowIndicator({ visible: true })
        gridView.editOptions.editable = false
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('circ_chk_atnd_tphn', 'visible', false)

        this.schData.circ_chk_tgt_id = this.$route.params.wknstourchktgtmgntdtllup
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-wknstourchktgtmgntchlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    async select() {
      gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      // circ_chk_tgt_id를 통해 기존 데이터 조회
      await this.$axios.get('/api/v1/she/sftchk/wknstourchktgtmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const wknsChkResult = res.data.wknsChkResult[0]

          this.frmData.circ_chk_supe_emp_id = wknsChkResult.circ_chk_supe_emp_id
          this.frmData.circ_chk_supe_emp_nm = wknsChkResult.circ_chk_supe_emp_nm
          this.frmData.circ_chk_dtl_loc_nm = wknsChkResult.circ_chk_dtl_loc_nm
          this.frmData.circ_chk_wkpl_loc_id = wknsChkResult.circ_chk_wkpl_loc_id
          this.frmData.circ_chk_wkpl_loc_nm = wknsChkResult.circ_chk_wkpl_loc_nm
          this.frmData.circ_chk_tgt_nm = wknsChkResult.circ_chk_tgt_nm
          this.frmData.circ_chk_tgt_id = wknsChkResult.circ_chk_tgt_id
          this.frmData.circ_chk_note_txt = wknsChkResult.circ_chk_note_txt
          this.frmData.wkpl_id = wknsChkResult.wkpl_id
          this.frmData.wkpl_nm = wknsChkResult.wkpl_nm
          this.frmData.usg_yn = wknsChkResult.usg_yn
          this.frmData.circ_chk_cyc_cd = wknsChkResult.circ_chk_cyc_cd
          this.frmData.chec_cl_cd = wknsChkResult.chec_cl_cd
          this.frmData.circ_chk_cyc_cd_nm = wknsChkResult.circ_chk_cyc_cd_nm
          this.frmData.chec_cl_cd_nm = wknsChkResult.chec_cl_cd_nm
          this.frmData.circ_chk_atnd_pstn_nm = wknsChkResult.circ_chk_atnd_pstn_nm
          this.frmData.circ_chk_atnd_dept_nm = wknsChkResult.circ_chk_atnd_dept_nm

          dataProvider.setRows(res.data.wknsAtndResult)
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
