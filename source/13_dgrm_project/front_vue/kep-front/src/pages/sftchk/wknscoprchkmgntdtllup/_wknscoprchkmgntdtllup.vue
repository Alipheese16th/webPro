<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 합동점검 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001044') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 합동점검 관리-->
      <h2>{{ $t('LB00001044') }}</h2>
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
                <div class="label_con">{{ frmData.jnt_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 일시-->
                <div class="label_tit">{{ $t('LB00000648') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <div class="label_con">{{ frmData.jnt_chk_dt }} {{ frmData.jnt_chk_hm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검코스-->
                <div class="label_tit">{{ $t('LB00001013') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.jnt_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--특이사항-->
                <div class="label_tit">{{ $t('LB00000994') }}</div>
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
    <div class="container_detail">
      <!-- 점검결과 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000646') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="1">
            <!--점검내용-->
            <div class="label_tit">{{ $t('LB00000901') }}</div>
          </v-col>
          <v-col cols="11">
            <v-textarea ref="jnt_chk_rslt_txt" v-model="frmData.jnt_chk_rslt_txt" outlined single-line :hide-details="true" dense :no-resize="true" readonly></v-textarea>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="1">
            <!--첨부파일-->
            <div class="label_tit">{{ $t('LB00000121') }}</div>
          </v-col>
          <v-col cols="11">
            <file-upload ref="upload1" :multiple="true" @onDrop="chkFile()" />
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
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업장 합동점검 대상 관리',
    key(route) {
      return `/sftchk/wknscoprchkmgntlist/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
    // quillEditor,
  },
  data() {
    return {
      menu1: false,
      menu2: false,
      atntotalcnt: 0,
      selSH00000022: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['SH00000022'],
      codes: {
        SH00000022: [{ cd: '', cd_nm: '' }],
      },
      schData: {
        jnt_chk_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        jnt_chk_tgt_id: '',
        jnt_chk_id: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
        jnt_chk_dtl_loc_nm: '',
        jnt_chk_tgt_nm: '',
        jnt_chk_note_txt: '',
        chec_cl_cd: '',
        lsch_nm: '',
        usg_yn: '',
      },
      menu: false,
      fileModify: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001044')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (
      dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
      JSON.stringify(this.tempData) !== JSON.stringify(this.frmData) ||
      this.fileModify === true
    ) {
      const res = await this.$confirm(this.$t('MS00000060'))
      return new Promise((resolve, reject) => {
        if (res) {
          resolve()
        } else {
          // eslint-disable-next-line prefer-promise-reject-errors
          reject('reject')
        }
      })
    }
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
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
            editable: false,
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
        gridView.setRowIndicator({ visible: true, width: 50 })
        gridView.editOptions.editable = false
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('jnt_chk_atnd_tphn', 'visible', false)

        // 권한 체크. 같은 부서 여부 조회
        this.schData.jnt_chk_id = this.$route.params.wknscoprchkmgntdtllup
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
    chkFile() {
      this.fileModify = true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-wknscoprchkmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    async select() {
      gridView.commit()

      // jnt_chk_id를 통해 기존 데이터 조회
      await this.$axios.get('/api/v1/she/sftchk/wknscoprchkmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const jntChkResult = res.data.jntChkResult[0]

          this.frmData.jnt_chk_id = jntChkResult.jnt_chk_id
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
          this.frmData.upt_emp_no = jntChkResult.upt_emp_no
          this.frmData.lsch_nm = jntChkResult.lsch_nm
          this.frmData.jnt_chk_rslt_txt = jntChkResult.jnt_chk_rslt_txt
          if (!this.isEmpty(jntChkResult.jnt_chk_dt)) {
            this.frmData.jnt_chk_dt = jntChkResult.jnt_chk_dt.substring(0, 4) + '-' + jntChkResult.jnt_chk_dt.substring(4, 6) + '-' + jntChkResult.jnt_chk_dt.substring(6, 8)
          }
          if (!this.isEmpty(jntChkResult.jnt_chk_hm)) {
            this.$set(this.frmData, 'jnt_chk_hm', jntChkResult.jnt_chk_hm.substring(0, 2) + ':' + jntChkResult.jnt_chk_hm.substring(2, 4))
          }

          if (!this.isEmpty(jntChkResult.jnt_chk_atfl_no)) {
            this.$axios.$get(`/api/v1/common/file/${jntChkResult.jnt_chk_atfl_no}`).then((data) => {
              if (data.length > 0) {
                this.setFiles(data)
              }
            })
          }

          this.fileModify = false // 첨부파일 수정 여부 초기화

          dataProvider.setRows(res.data.jntAtndResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.atntotalcnt = gridView.getItemCount()

          this.tempData = JSON.parse(JSON.stringify(this.frmData))
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
    },
  },
}
</script>
<style></style>
