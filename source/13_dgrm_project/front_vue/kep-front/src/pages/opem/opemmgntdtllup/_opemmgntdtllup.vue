<template>
  <div>
    <div class="location">
      <!-- Home > 작업환경측정 > 작업환경측정 관리 -->
      <span>Home > {{ $t('LB00000960') }} > {{ $t('LB00000961') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업환경측정 관리 -->
      <h2>{{ $t('LB00000961') }}</h2>
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
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000002"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--의뢰자-->
                <div class="label_tit">{{ $t('LB00000970') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_req_emp_nm }}</div>
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
                <!--측정구분-->
                <div class="label_tit">{{ $t('LB00000962') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wem_cl_cd"
                  v-model="frmData.wem_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000016"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--측정연도-->
                <div class="label_tit">{{ $t('LB00000963') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_year }}</div>
              </v-col>
              <v-col cols="1">
                <!--반기-->
                <div class="label_tit">{{ $t('LB00000964') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wem_cyc_cd"
                  v-model="frmData.wem_cyc_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000017"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--측정기관-->
                <div class="label_tit">{{ $t('LB00000973') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex"> {{ frmData.wem_bgn_dt }} ~ {{ frmData.wem_end_dt }} </v-col>
              <v-col cols="1">
                <!--측정기관-->
                <div class="label_tit">{{ $t('LB00000972') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_orga_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--개선대상 여부-->
                <div class="label_tit">{{ $t('LB00000974') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_btrm_tgt_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--측정결과 종합의견-->
                <div class="label_tit">{{ $t('LB00000979') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="wem_oval_opin_txt" v-model="frmData.wem_oval_opin_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--측정결과-->
        <h3>{{ $t('LB00000981') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ cnt }} {{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 개선계획 정보 -->
        <h3>{{ $t('LB00000992') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.wem_btrm_plan_txt" :options="editorOption" />
          </v-col>
        </v-row>
      </v-container>
      <div class="group_padd"></div>
      <!--첨부파일 목록(KO)-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist">
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
import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '작업환경측정 관리',
    key(route) {
      return `/opem/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      modify: false,
      codeid: ['ST00000002', 'SH00000016', 'SH00000017', 'SH00000018', 'SH00000046'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000016: [{ cd: '', cd_nm: '' }], // 측정구분
        SH00000017: [{ cd: '', cd_nm: '' }], // 반기
        SH00000018: [{ cd: '', cd_nm: '' }], // 유해인자
        SH00000046: [{ cd: '', cd_nm: '' }], // 판정
      },
      selSH00000018: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000046: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      year: ['2020', '2021'],
      tgtYn: ['Y', 'N'],
      cnt: 0,
      schData: {},
      frmData: {},
      tempData: {},
      editorOption: {
        // some quill options
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // toggled buttons
            ['blockquote', 'code-block'],

            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }], // outdent/indent

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ align: [] }],

            ['link', 'image'],

            ['clean'], // remove formatting button
          ],
        },
        placeholder: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000961') // 작업환경측정 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']

    sDate = this.getTzDateObj(user.time_zone)
    eDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(eDate.getDate() - 7)
    this.frmData.wem_bgn_dt = this.get_date_str_gubun(sDate, '-')
    this.frmData.wem_end_dt = this.get_date_str_gubun(eDate, '-')

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        res.SH00000018.forEach((i) => {
          this.selSH00000018.cd.push(i.cd)
          this.selSH00000018.cd_nm.push(i.cd_nm)
        })
        res.SH00000046.forEach((i) => {
          this.selSH00000046.cd.push(i.cd)
          this.selSH00000046.cd_nm.push(i.cd_nm)
        })

        if (this.$route.params.opemmgntdtllup !== null && this.$route.params.opemmgntdtllup !== undefined) {
          this.frmData.wem_id = this.$route.params.opemmgntdtllup
          this.schData.wem_id = this.$route.params.opemmgntdtllup
        } else {
          this.frmData.wem_id = ''
          this.schData.wem_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.wem_id = this.schData.wem_id
        }

        this.createGrid()

        // ID 존재할 경우
        if (this.frmData.wem_id) {
          this.select()
          this.$refs.quill.quill.enable(false)
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'wem_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wem_pl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_unit_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hmfc_msrm_vl',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'hmfc_exps_stnd_vl',
          dataType: ValueType.NUMBER,
        },
        {
          fieldName: 'hmfc_rslt_jgmt_cd',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000996'), // 대상부서
          name: 'wem_dept_nm',
          fieldName: 'wem_dept_nm',
          width: '200',
        },
        {
          header: this.$t('LB00000997'), // 측정장소
          name: 'wem_pl_nm',
          fieldName: 'wem_pl_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000998'), // 유해인자
          name: 'hmfc_cd',
          fieldName: 'hmfc_cd',
          width: '100',
          values: this.selSH00000018.cd,
          labels: this.selSH00000018.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000470'), // 단위
          name: 'hmfc_unit_nm',
          fieldName: 'hmfc_unit_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000999'), // 측정값
          name: 'hmfc_msrm_vl',
          fieldName: 'hmfc_msrm_vl',
          width: '100',
          styleName: 'right',
        },
        {
          header: this.$t('LB00000320'), // 노출기준
          name: 'hmfc_exps_stnd_vl',
          fieldName: 'hmfc_exps_stnd_vl',
          width: '100',
          styleName: 'right',
        },
        {
          header: this.$t('LB00001000'), // 판정
          name: 'hmfc_rslt_jgmt_cd',
          fieldName: 'hmfc_rslt_jgmt_cd',
          width: '100',
          values: this.selSH00000046.cd,
          labels: this.selSH00000046.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
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

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = false
      gridView.editOptions.appendable = false
      // 그리드 1 끝
    },
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/hlth/opem/${this.frmData.wem_id}`).then((res) => {
        this.frmData.wem_id = res.wem_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.wem_year = res.wem_year
        this.frmData.wem_sn = res.wem_sn
        this.frmData.wem_cl_cd = res.wem_cl_cd
        this.frmData.wem_cyc_cd = res.wem_cyc_cd
        this.frmData.wem_bgn_dt = res.wem_bgn_dt
        this.frmData.wem_end_dt = res.wem_end_dt
        this.frmData.wem_orga_nm = res.wem_orga_nm
        this.frmData.wem_req_emp_id = res.wem_req_emp_id
        this.frmData.wem_req_emp_nm = res.wem_req_emp_nm
        this.frmData.wem_oval_opin_txt = res.wem_oval_opin_txt
        this.frmData.wem_btrm_tgt_yn = res.wem_btrm_tgt_yn
        this.frmData.wem_btrm_plan_txt = res.wem_btrm_plan_txt
        this.frmData.wem_atfl_no = res.wem_atfl_no
        this.frmData.wem_prst_cd = res.wem_prst_cd
        this.frmData.wem_aprv_no = res.wem_aprv_no
        // this.frmData.lsch_nm = res.lsch_nm
        this.$set(this.frmData, 'lsch_nm', res.lsch_nm)

        // 의제
        dataProvider.setRows(res.rslt_list)
        gridView.refresh()
        gridView.setTopItem(0)
        this.cnt = gridView.getItemCount()

        if (this.frmData.wem_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.wem_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, false)
            }
          })
        } else {
          this.$refs.upload.setEdit(false)
        }

        // 에디터 포커스 제거
        this.$nextTick(function () {
          // this.$refs.quill.quill.enable(true)
          this.$refs.quill.quill.blur()
        })
      })
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
        name: `opem-opemmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
    async saveFiles(mode) {
      await this.$refs.upload.uploadStart().then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data) {
          this.frmData.wem_atfl_no = data
          // this.$axios.$get(`/api/v1/common/file/${this.frmData.wem_atfl_no}`).then((data) => {
          //   console.log('files : ', data) // eslint-disable-line no-console
          //   if (data.length > 0) {
          //     this.setFiles(data, mode)
          //   }
          // })
        }
      })
    },
  },
}
</script>
<style></style>
