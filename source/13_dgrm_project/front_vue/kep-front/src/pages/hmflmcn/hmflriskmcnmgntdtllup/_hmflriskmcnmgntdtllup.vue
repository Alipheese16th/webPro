<template>
  <div>
    <div class="location">
      <!-- Home > 유해위험기계기구 관리 > 유해위험기계기구 관리 -->
      <span>Home > {{ $t('LB00000980') }} > {{ $t('LB00000980') }}</span>
    </div>
    <div class="work_title">
      <!-- 유해위험기계기구 관리 -->
      <h2>{{ $t('LB00000980') }}</h2>
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
                <!--기기종류-->
                <div class="label_tit">{{ $t('LB00000982') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_type_nm }}</div>
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
                <!--기기명-->
                <div class="label_tit">{{ $t('LB00000983') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.hrm_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--유해위험기계기구 번호-->
                <div class="label_tit">{{ $t('LB00000984') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_mgnt_no }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--설치일자-->
                <div class="label_tit">{{ $t('LB00000985') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_istl_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--제조사-->
                <div class="label_tit">{{ $t('LB00000986') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_mnfr_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--자산번호-->
                <div class="label_tit">{{ $t('LB00000987') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_asst_no }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_dtl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검주기-->
                <div class="label_tit">{{ $t('LB00000907') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_chk_cyc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--담당자(정)-->
                <div class="label_tit">{{ $t('LB00000989') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_rspn_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--담당자(부)-->
                <div class="label_tit">{{ $t('LB00000990') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_spic_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch
                  ref="hrm_usg_yn"
                  v-model="frmData.hrm_usg_yn"
                  true-value="Y"
                  false-value="N"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--유해위험기계기구 SPEC-->
                <div class="label_tit">{{ $t('LB00000991') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="hrm_spec_txt" v-model="frmData.hrm_spec_txt" outlined single-line :hide-details="true" height="60" class="mt-2" dense disabled></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--안전점검 목록-->
        <h3>{{ $t('LB00000993') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="realgrid_container">
      <div class="group_padd"></div>
      <!-- 첨부파일 목록 -->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
        <table>
          <div v-if="true">
            <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
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
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '유해위험기계기구 관리',
    key(route) {
      return `/hmflmcn/hmflmcn/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      quillChk: false,
      totalcnt: 0,
      tmpSchData: {},
      schData: {},
      menu1: false,
      userPopGb: '',
      wkplId: '',
      hrmId: '',
      frmTempData: '',
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        hrm_id: '',
        hrm_type_cd: '',
        hrm_type_nm: '',
        hrm_nm: '',
        hrm_mgnt_no: '',
        hrm_istl_dt: '',
        hrm_mnfr_nm: '',
        hrm_asst_no: '',
        hrm_wkpl_loc_id: '',
        hrm_dtl_loc_nm: '',
        hrm_chk_cyc_cd: '',
        hrm_rspn_emp_id: '',
        hrm_spic_emp_id: '',
        hrm_rspn_emp_nm: '',
        hrm_spic_emp_nm: '',
        hrm_spec_txt: '',
        hrm_usg_yn: '',
        hrm_atch_file_no: '',
        lsch_nm: '',
      },
      // 안전검검
      listData: {
        wkpl_id: '',
        hrm_id: '',
        hrm_chk_dt: '',
        hrm_chk_txt: '',
        hrm_chk_rslt_cd: '',
        hrm_chk_rslt_nm: '',
        hrm_chk_atch_file_no: '',
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000980') // 유해위험기계기구 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.hmflriskmcnmgntdtllup !== null && this.$route.params.hmflriskmcnmgntdtllup !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.hrm_id = this.$route.params.hmflriskmcnmgntdtllup
      this.wkplId = this.$route.params.wkpl_id
      this.hrmId = this.$route.params.hmflriskmcnmgntdtllup
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.hrm_id = this.$route.params.hmflriskmcnmgntdtllup
    } else {
      this.frmData.wkpl_id = ''
      this.frmData.hrm_id = ''
      this.wkplId = ''
      this.hrmId = ''
      this.schData.wkpl_id = ''
      this.schData.hrm_id = ''
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        hrm_type_cd: '',
        hrm_wkpl_loc_id: '',
        hrm_nm: '',
        use_yn: '',
        sDate: '',
        eDate: '',
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_txt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_rslt_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_rslt_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hrm_chk_atch_file_no',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00000917'), // 점검일자
        name: 'hrm_chk_dt',
        fieldName: 'hrm_chk_dt',
        width: '100',
      },
      {
        header: this.$t('LB00000901'), // 점검내용
        name: 'hrm_chk_txt',
        fieldName: 'hrm_chk_txt',
        width: '400',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000646'), // 점검결과
        name: 'hrm_chk_rslt_nm',
        fieldName: 'hrm_chk_rslt_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000121'), // 첨부파일
        name: 'hrm_chk_atch_file_no',
        fieldName: 'hrm_chk_atch_file_no',
        width: '200',
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
    // 그리드 1 끝

    if (this.frmData.hrm_id !== '') {
      this.select()
    }
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/hmflmcn/hmfl-mcn-dtl/${this.schData.hrm_id}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        if (!this.isEmpty(this.frmData.hrm_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.hrm_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
      } else {
        this.frmData = []
        this.frmTempData = []
      }

      this.listData = data.chk
      dataProvider.setRows(data.chk)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
    },
    chkFile() {
      this.frmData.ckFlag = true
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
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
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    goList() {
      this.$router.push({
        name: `hmflmcn-hmflriskmcnmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            hrm_type_cd: this.tmpSchData.hrm_type_cd,
            hrm_wkpl_loc_id: this.tmpSchData.hrm_wkpl_loc_id,
            hrm_nm: this.tmpSchData.hrm_nm,
            use_yn: this.tmpSchData.use_yn,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
  },
}
</script>
<style></style>
