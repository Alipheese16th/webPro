<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 합동점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001011') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 합동점검 대상 관리-->
      <h2>{{ $t('LB00001011') }}</h2>
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
                <div class="label_con">{{ frmData.jnt_chk_supe_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30" disabled></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.jnt_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검코스-->
                <div class="label_tit">{{ $t('LB00001013') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.jnt_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.jnt_chk_note_txt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 참석자 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000908') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ atntotalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 점검계획 -->
      <div class="sub_title">
        <h3>{{ $t('LB00001021') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ chktotalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 481px"></div>
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

// 그리드 3
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업장 합동점검 대상 관리',
    key(route) {
      return `/sftchk/wknscoprchktgtmgntlist/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      atntotalcnt: 0,
      chktotalcnt: 0,
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
        jnt_chk_tgt_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        jnt_chk_tgt_id: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
        jnt_chk_dtl_loc_nm: '',
        jnt_chk_tgt_nm: '',
        jnt_chk_note_txt: '',
        chec_cl_cd: '',
        chec_cl_cd_nm: '',
        usg_yn: '',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001011')
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
        jnt_chk_tgt_nm: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
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
            fieldName: 'jnt_chk_atnd_sn', // 기본키로 쓰임
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnc_emp_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_pstn_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_dept_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_tphn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_emad',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'jnt_chk_atnd_cl_cd',
            fieldName: 'jnt_chk_atnd_cl_cd',
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
            name: 'jnt_chk_atnd_nm',
            fieldName: 'jnt_chk_atnd_nm',
            width: '100',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000766'), // 직책
            name: 'jnt_chk_atnd_pstn_nm',
            fieldName: 'jnt_chk_atnd_pstn_nm',
            width: '200',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000909'), // 회사명
            name: 'jnt_chk_atnd_dept_nm',
            fieldName: 'jnt_chk_atnd_dept_nm',
            width: '200',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000723'), // 전화번호
            name: 'jnt_chk_atnd_tphn',
            fieldName: 'jnt_chk_atnd_tphn',
            width: '200',
            styleName: 'left',
            editable: false,
          },
          {
            header: this.$t('LB00000910'), // 이메일주소
            name: 'jnt_chk_atnd_emad',
            fieldName: 'jnt_chk_atnd_emad',
            width: '300',
            styleName: 'left',
            editable: false,
          },
        ]
        // 그리드 1 시작
        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.displayOptions.selectionStyle = 'block'
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

        gridView.setColumnProperty('jnt_chk_atnd_tphn', 'visible', false)
        // 그리드 1 끝

        // 그리드 2 시작
        // 그리드 세팅
        const fields2 = [
          {
            fieldName: 'jnt_chk_tgt_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_schd_dt',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_schd_tm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_ttl',
            dataType: ValueType.TEXT,
          },
        ]
        const columns2 = [
          {
            header: this.$t('LB00001022'), // 합동점검대상 Id
            name: 'jnt_chk_tgt_id',
            fieldName: 'jnt_chk_tgt_id',
            width: '200',
          },
          {
            header: this.$t('LB00001023'), // 점검예정일
            name: 'jnt_chk_schd_dt',
            fieldName: 'jnt_chk_schd_dt',
            width: '100',
            textFormat: '([0-9]{4})([0-9]{2})([0-9]{2})$;$1-$2-$3',
            editable: false,
            editor: {
              type: 'text',
              textReadOnly: true,
              mask: {
                editMask: '9999-99-99',
              },
            },
            styleName: 'left',
          },
          {
            header: this.$t('LB00001024'), // 점검예정시간
            name: 'jnt_chk_schd_tm',
            fieldName: 'jnt_chk_schd_tm',
            width: '100',
            editor: {
              mask: {
                definitions: {
                  b: '[0-2]',
                  c: '[0-9]',
                  d: '[0-5]',
                  e: '[0-9]',
                },
                editMask: 'bc:de',
                includedFormat: true,
                overWrite: true,
                allowEmpty: true,
              },
            },
            textFormat: '([0-9]{2})([0-9]{2});$1:$2',
            editable: false,
            styleName: 'left',
          },
          {
            header: this.$t('LB00001025'), // 중점 점검 사항
            name: 'jnt_chk_ttl',
            fieldName: 'jnt_chk_ttl',
            width: '200',
            styleName: 'left',
          },
        ]
        dataProvider2 = new LocalDataProvider(false)
        dataProvider2.setFields(fields2)
        gridView2 = new GridView('realgrid2')
        gridView2.displayOptions.selectionStyle = 'block'
        gridView2.setDataSource(dataProvider2)

        gridView2.setColumns(columns2)
        gridView2.setFooters({ visible: false })
        gridView2.setStateBar({ visible: false })
        gridView2.setCheckBar({ visible: false })
        gridView2.editOptions.editable = false
        gridView2.displayOptions.selectionStyle = 'block'

        gridView2.header.height = 39
        gridView2.displayOptions.rowHeight = 40
        gridView2.footer.height = 40
        gridView2.displayOptions.fitStyle = 'fill'
        gridView2.setColumnProperty('jnt_chk_tgt_id', 'visible', false)
        // 그리드 2 끝

        this.schData.jnt_chk_tgt_id = this.$route.params.wknscoprchktgtmgntdtllup
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
        name: 'sftchk-wknscoprchktgtmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    async select() {
      gridView.commit()
      this.tempData = JSON.parse(JSON.stringify(this.frmData))

      // jnt_chk_tgt_id를 통해 기존 데이터 조회
      await this.$axios.get('/api/v1/she/sftchk/wknscoprchktgtmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const jntChkResult = res.data.jntChkResult[0]

          this.frmData.jnt_chk_supe_emp_id = jntChkResult.jnt_chk_supe_emp_id
          this.frmData.jnt_chk_supe_emp_nm = jntChkResult.jnt_chk_supe_emp_nm
          this.frmData.jnt_chk_dtl_loc_nm = jntChkResult.jnt_chk_dtl_loc_nm
          this.frmData.jnt_chk_wkpl_loc_id = jntChkResult.jnt_chk_wkpl_loc_id
          this.frmData.jnt_chk_wkpl_loc_nm = jntChkResult.jnt_chk_wkpl_loc_nm
          this.frmData.jnt_chk_tgt_nm = jntChkResult.jnt_chk_tgt_nm
          this.frmData.jnt_chk_tgt_id = jntChkResult.jnt_chk_tgt_id
          this.frmData.jnt_chk_note_txt = jntChkResult.jnt_chk_note_txt
          this.frmData.wkpl_id = jntChkResult.wkpl_id
          this.frmData.wkpl_nm = jntChkResult.wkpl_nm
          this.frmData.usg_yn = jntChkResult.usg_yn
          this.frmData.chec_cl_cd = jntChkResult.chec_cl_cd
          this.frmData.chec_cl_cd_nm = jntChkResult.chec_cl_cd_nm

          dataProvider.setRows(res.data.jntAtndResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.atntotalcnt = gridView.getItemCount()

          dataProvider2.setRows(res.data.jntDtResult)
          gridView2.refresh()
          gridView2.setTopItem(0)
          this.chktotalcnt = gridView2.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
