<template>
  <div>
    <div class="location">
      <!-- Home > 규제 검토 > 규제 검토 요청 상세(향) -->
      <span>Home > {{ $t('LB00000336') }} > {{ $t('LB00000406') }}</span>
    </div>
    <!-- 규제 검토 요청 상세(향) -->
    <div class="work_title">
      <!-- eslint-disable -->
      <h2>{{ $t('LB00000406') }} <span v-html="titleHtml" class="work_title2"></span></h2>
    </div>
    <div class="container_detail">
      <!-- 규제 검토 요청 -->
      <div class="sub_title">
        <h3>{{ $t('MN00000007') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 요청번호 -->
                <div class="label_tit">{{ $t('LB00000151') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청자 -->
                <div class="label_tit">{{ $t('LB00000150') }} / {{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_req_emp_nm }} / {{ frmData.rglt_chk_req_dept_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 요청일 -->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_req_dt }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- 자재 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000405') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 자재코드 -->
                <div class="label_tit">{{ $t('LB00000024') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재명 -->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.mtrl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재구분 -->
                <div class="label_tit">{{ $t('LB00000022') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_clsf_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 품목구분 -->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.macl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재등록일 -->
                <div class="label_tit">{{ $t('LB00000404') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_reg_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재등록 요청자 -->
                <div class="label_tit">{{ $t('LB00000403') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_reg_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재등록 사업장 -->
                <div class="label_tit">{{ $t('LB00000402') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_site }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 자재 성상 -->
                <div class="label_tit">{{ $t('LB00000355') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.phase_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재 용도 -->
                <div class="label_tit">{{ $t('LB00000354') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_puse_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 자재 기타 용도 -->
                <div class="label_tit">{{ $t('LB00000353') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtrl_puse_cntn }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <!-- MSDS 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000506') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- MSDS 국문 -->
                <div class="label_tit">{{ $t('LB00000177') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <file-upload ref="upload1" input-id="file" :multiple="false" />
                </div>
              </v-col>
              <v-col cols="1">
                <!-- MSDS 영문 -->
                <div class="label_tit">{{ $t('LB00000176') }}</div>
              </v-col>
              <v-col cols="5">
                <div class="label_con">
                  <file-upload ref="upload2" input-id="file2" :multiple="false" />
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <v-container>
      <v-row>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <!-- 성분 정보 -->
            <div class="sub_title">
              <h3>{{ $t('LB00000401') }}</h3>
            </div>
            <div class="grid_header">
              <!-- 총 {{ cnstTotalcnt }}건 -->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ cnstTotalcnt }}{{ $t('LB00000040') }}</div>
            </div>
            <div id="realgrid" style="width: 100%; height: 280px"></div>
          </div>
        </v-col>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <!-- 구성물질 정보 -->
            <div class="sub_title">
              <h3>{{ $t('LB00000400') }}</h3>
            </div>
            <div class="grid_header">
              <!-- 총 {{ sbstTotalcnt }}건 -->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbstTotalcnt }}{{ $t('LB00000040') }}</div>
            </div>
            <div id="realgrid2" style="width: 100%; height: 280px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 예상규제 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000399') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ rgltTotalcnt }}건 -->
        <!-- 예상되는 규제 정보입니다. 규제검토 결과 대상 규제는 달라질 수 있습니다. -->
        <div class="grid_header_left">
          {{ $t('LB00000039') }} {{ rgltTotalcnt }}{{ $t('LB00000040') }}<span class="grid_info">{{ $t('MS00000168') }}</span>
        </div>
      </div>
      <div id="realgrid3" style="width: 100%; height: 280px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 규제검토 결과조회 -->
      <v-btn v-if="btnYn" depressed width="120" color="primary" height="40" @click="rsltSrch()">{{ $t('LB00000398') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

let gridView = GridView
let dataProvider = LocalDataProvider
let gridView2 = GridView
let dataProvider2 = LocalDataProvider
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '규제검토-규제검토요청상세(향)',
    key(route) {
      return `/rgltchk/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      titleHtml: '',
      tmpSchData: {},
      tmpDtlSchData: {},
      btnYn: false,
      schData: {},
      frmData: {},
      cnstTotalcnt: 0,
      sbstTotalcnt: 0,
      rgltTotalcnt: 0,
      rgltChkNo: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000414') + '(향)'
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.rgltChkNo = this.$route.params.rgltchkreqmtrllup
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        rglt_chk_req_prog_stt_cd: '',
        rglt_chk_req_emp_no: '',
        userno: '',
        username: '',
        sDate: '',
        eDate: '',
      }
    }
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'comp_cnst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'comp_cnst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'comp_cnst_ratio',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chk_req_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
    ]

    const fields2 = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chk_req_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_type_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_type_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_rati',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_note',
        dataType: ValueType.TEXT,
      },
    ]

    const fields3 = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_chk_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'chk_req_clsf_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mtrl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'law_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'law_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cas_no',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000407'), // 성분코드
        name: 'comp_cnst_no',
        fieldName: 'comp_cnst_no',
        width: '100',
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'comp_cnst_nm',
        fieldName: 'comp_cnst_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000195'), // 함량(%)
        name: 'comp_cnst_ratio',
        fieldName: 'comp_cnst_ratio',
        width: '100',
        styleName: 'right',
      },
    ]

    const columns2 = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000270'), // 물질구분
        name: 'sbst_type_nm',
        fieldName: 'sbst_type_nm',
        width: '100',
      },
      {
        header: 'CAS No.',
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
      {
        header: this.$t('LB00000196'), // 물질명
        name: 'sbst_nm',
        fieldName: 'sbst_nm',
        width: '100',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000195'), // 함량(%)
        name: 'sbst_rati',
        fieldName: 'sbst_rati',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000095'), // 비고
        name: 'sbst_note',
        fieldName: 'sbst_note',
        width: '100',
        styleName: 'left',
      },
    ]

    const columns3 = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000272'), // 법
        name: 'law_nm',
        fieldName: 'law_nm',
        width: '150',
      },
      {
        header: this.$t('LB00000028'), // 규제
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000408'), // 대상물질
        name: 'cas_no',
        fieldName: 'cas_no',
        width: '100',
      },
    ]
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

    gridView.setColumnProperty('id', 'visible', false)

    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
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

    gridView2.setColumnProperty('id', 'visible', false)

    dataProvider3 = new LocalDataProvider(false)
    dataProvider3.setFields(fields3)
    gridView3 = new GridView('realgrid3')
    gridView3.setDataSource(dataProvider3)
    gridView3.setColumns(columns3)
    gridView3.setFooters({ visible: false })
    gridView3.setStateBar({ visible: false })
    gridView3.setCheckBar({ visible: false })
    gridView3.editOptions.editable = false
    gridView3.displayOptions.selectionStyle = 'block'

    gridView3.header.height = 39
    gridView3.displayOptions.rowHeight = 40
    gridView3.footer.height = 40
    gridView3.displayOptions.fitStyle = 'fill'

    gridView3.setColumnProperty('id', 'visible', false)

    this.select()
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/ch/rgltchk/rglt-chk-req-dtl/${this.rgltChkNo}`)

      this.frmData = data.basic[0]

      if (this.frmData.rglt_chk_req_prog_stt_cd === '30') {
        this.btnYn = true
      }

      this.$refs.upload1.setEdit(false)
      this.$refs.upload2.setEdit(false)

      if (!this.isEmpty(this.frmData.ko_atfile_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.frmData.ko_atfile_no}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles1(data)
          }
        })
      }
      if (!this.isEmpty(this.frmData.en_atfile_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.frmData.en_atfile_no}`).then((data) => {
          console.log('files2 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles2(data)
          }
        })
      }

      dataProvider.setRows(data.cnst)
      gridView.refresh()
      gridView.setTopItem(0)

      this.cnstTotalcnt = dataProvider.getRowCount()

      dataProvider2.setRows(data.sbst)
      gridView2.refresh()
      gridView2.setTopItem(0)

      this.sbstTotalcnt = dataProvider2.getRowCount()

      dataProvider3.setRows(data.rglt)
      gridView3.refresh()
      gridView3.setTopItem(0)

      this.rgltTotalcnt = dataProvider3.getRowCount()

      // 둘다 값이 와이이면...'※ MSDS 제출 대상 자재'
      if (this.frmData.imp_yn === 'Y' && this.frmData.sbms_tgt_yn === 'Y') {
        this.titleHtml = this.$t('LB00000620')
      }
    },
    download(key, fileName) {
      if (this.isEmpty(key) || this.isEmpty(fileName)) return
      console.log('download...', key) // eslint-disable-line no-console
      this.$axios
        .$get(`/api/v1/common/file-down/${key}`, {
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          // IE 10+
          if (window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(new Blob([res]), fileName)
          } else {
            // not IE
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(new Blob([res]))
            link.target = '_self'
            link.download = fileName
            link.click()
          }
        })
    },
    downKoFile() {
      // 다운로드 처리
      const koDownKey = this.frmData.ko_atfile_key
      const koDownNm = this.frmData.ko_atfile_nm
      this.download(koDownKey, koDownNm)
    },
    downEnFile() {
      // 다운로드 처리
      const enDownKey = this.frmData.en_atfile_key
      const enDownNm = this.frmData.en_atfile_nm
      this.download(enDownKey, enDownNm)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    rsltSrch() {
      this.$router.push({
        name: `rgltchk-rgltchkmtrllup-rgltchkmtrllup`,
        params: {
          rgltchkmtrllup: this.frmData.rglt_chk_no,
          schData: {
            mtrl_no: this.frmData.mtrl_no,
            rglt_chk_no: this.frmData.rglt_chk_no,
            chk_req_clsf_cd: '2',
          },
          tmpSchData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
            macl_cd: this.tmpSchData.macl_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            rglt_chk_prog_stt_cd: this.tmpSchData.rglt_chk_prog_stt_cd,
            rglt_chk_req_emp_no: this.tmpSchData.rglt_chk_req_emp_no,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.changeEditMode(false)
      })
    },
    setFiles2(files) {
      this.$nextTick(() => {
        this.$refs.upload2.setFiles(files)
        this.$refs.upload2.setEdit(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
      this.$refs.upload2.setEdit(mode)
    },
    goList() {
      this.$router.push({
        name: `rgltchk-rgltchkreqlist`,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            mtrl_clsf_cd: this.tmpSchData.mtrl_clsf_cd,
            macl_cd: this.tmpSchData.macl_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            rglt_chk_req_prog_stt_cd: this.tmpSchData.rglt_chk_req_prog_stt_cd,
            rglt_chk_req_emp_no: this.tmpSchData.rglt_chk_req_emp_no,
            userno: this.tmpSchData.userno,
            username: this.tmpSchData.username,
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
