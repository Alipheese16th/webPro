<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 회의체 관리-->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000719') }}</span>
    </div>
    <div class="work_title">
      <!-- 회의체 관리 -->
      <h2>{{ $t('LB00000719') }}</h2>
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
                <!--회의 구분-->
                <div class="label_tit">{{ $t('LB00000728') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtng_cl_cd"
                  v-model="frmData.mtng_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000001"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--정기/임시 구분-->
                <div class="label_tit">{{ $t('LB00000733') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtng_cyc_cd"
                  v-model="frmData.mtng_cyc_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000003"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--회의명-->
                <div class="label_tit">{{ $t('LB00000730') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.mtng_ttl }}</div>
              </v-col>
              <v-col cols="1">
                <!--회의 장소-->
                <div class="label_tit">{{ $t('LB00000755') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtng_pl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--회의 일자-->
                <div class="label_tit">{{ $t('LB00000726') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtng_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--회의 시간-->
                <div class="label_tit">{{ $t('LB00000756') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.mtng_bgn_hm }} ~ {{ frmData.mtng_end_hm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 안건-->
        <h3>{{ $t('LB00000759') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbjcCnt }} {{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 참석자-->
        <h3>{{ $t('LB00000761') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ atndCnt }} {{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 결과-->
        <h3>{{ $t('LB00000762') }}</h3>
      </div>
    </div>
    <div class="container_detail">
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.mtng_rslt_txt" :options="editorOption" />
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

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '회의체 관리',
    key(route) {
      return `/mtng/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      modify: false,
      codeid: ['ST00000002', 'SH00000001', 'SH00000002', 'SH00000003', 'SH00000007'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000001: [{ cd: '', cd_nm: '' }], // 회의구분
        SH00000002: [{ cd: '', cd_nm: '' }], // 의제구분
        SH00000003: [{ cd: '', cd_nm: '' }], // 정기/임시 구분
        SH00000007: [{ cd: '', cd_nm: '' }], // 자사/협력사 구분
      },
      selSH00000002: {
        cd: [],
        cd_nm: [],
      },
      selSH00000007: {
        cd: [],
        cd_nm: [],
      },
      sbjcCnt: 0,
      atndCnt: 0,
      menu1: false,
      menu2: false,
      menu3: false,
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
      return this.$t('LB00000719') // 회의체 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        res.SH00000002.forEach((i) => {
          this.selSH00000002.cd.push(i.cd)
          this.selSH00000002.cd_nm.push(i.cd_nm)
        })
        res.SH00000007.forEach((i) => {
          this.selSH00000007.cd.push(i.cd)
          this.selSH00000007.cd_nm.push(i.cd_nm)
        })

        if (this.$route.params.mtngmgntdtllup !== null && this.$route.params.mtngmgntdtllup !== undefined) {
          this.frmData.mtng_id = this.$route.params.mtngmgntdtllup
          this.schData.mtng_id = this.$route.params.mtngmgntdtllup
        } else {
          this.frmData.mtng_id = ''
          this.schData.mtng_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.mtng_id = this.schData.mtng_id
        }

        this.createGrid()

        // ID 존재할 경우
        if (this.frmData.mtng_id) {
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
      // 그리드 1 시작
      const fields = [
        {
          fieldName: 'mtng_sbjc_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_rslt_txt',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        // {
        //   header: this.$t('LB00001135'), // 의제구분
        //   name: 'mtng_sbjc_cl_cd',
        //   fieldName: 'mtng_sbjc_cl_cd',
        //   width: '100',
        //   values: this.selSH00000002.cd,
        //   labels: this.selSH00000002.cd_nm,
        //   lookupDisplay: true,
        //   editor: {
        //     type: 'list',
        //     domainOnly: true,
        //     textReadOnly: true,
        //   },
        // },
        {
          header: this.$t('LB00000764'), // 의제
          name: 'mtng_sbjc_txt',
          fieldName: 'mtng_sbjc_txt',
          width: '400',
        },
        {
          header: this.$t('LB00000765'), // 결과
          name: 'mtng_sbjc_rslt_txt',
          fieldName: 'mtng_sbjc_rslt_txt',
          width: '400',
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

      // 그리드 2 시작
      const fields2 = [
        {
          fieldName: 'mtng_atnd_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnc_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_pstn_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_tphn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_emad',
          dataType: ValueType.TEXT,
        },
      ]

      const columns2 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'mtng_atnd_cl_cd',
          fieldName: 'mtng_atnd_cl_cd',
          width: '100',
          values: this.selSH00000007.cd,
          labels: this.selSH00000007.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'mtng_atnd_nm',
          fieldName: 'mtng_atnd_nm',
          width: '100',
        },
        {
          header: this.$t('LB00000766'), // 직책
          name: 'mtng_atnd_pstn_nm',
          fieldName: 'mtng_atnd_pstn_nm',
          width: '150',
          editable: false,
        },
        {
          header: this.$t('LB00000700'), // 회사
          name: 'mtng_atnd_dept_nm',
          fieldName: 'mtng_atnd_dept_nm',
          width: '150',
          editable: false,
        },
      ]
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: false })

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.editOptions.editable = false
      gridView2.editOptions.appendable = false
      // 그리드 2 끝
    },
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/sfty/mtng/${this.frmData.mtng_id}`).then((res) => {
        this.frmData.mtng_id = res.mtng_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.mtng_cl_cd = res.mtng_cl_cd
        this.frmData.mtng_cyc_cd = res.mtng_cyc_cd
        this.frmData.mtng_ttl = res.mtng_ttl
        this.frmData.mtng_pl_nm = res.mtng_pl_nm
        this.frmData.mtng_dt = res.mtng_dt
        this.frmData.mtng_bgn_hm = res.mtng_bgn_hm
        this.frmData.mtng_end_hm = res.mtng_end_hm
        this.frmData.mtng_rslt_txt = res.mtng_rslt_txt
        this.frmData.mtng_rslt_prst_cd = res.mtng_rslt_prst_cd
        this.frmData.mtng_rslt_atfl_no = res.mtng_rslt_atfl_no
        // this.frmData.crt_emp_no = res.crt_emp_no
        this.frmData.lsch_nm = res.lsch_nm
        this.$set(this.frmData, 'crt_emp_no', res.crt_emp_no)
        // 의제
        dataProvider.setRows(res.sbjc_list)
        gridView.refresh()
        gridView.setTopItem(0)
        this.sbjcCnt = gridView.getItemCount()
        // 참석자
        dataProvider2.setRows(res.atnd_list)
        gridView2.refresh()
        gridView2.setTopItem(0)
        this.atndCnt = gridView2.getItemCount()

        if (this.frmData.mtng_rslt_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.mtng_rslt_atfl_no}`).then((data) => {
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
    goList() {
      this.$router.push({
        name: `mtng-mtngmgntlist`,
        params: {
          schData: this.schData,
        },
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
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
  },
}
</script>
<style></style>
