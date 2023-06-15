<template>
  <div>
    <div class="location">
      <!-- Home > 안전 > SOP 관리 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000931') }}</span>
    </div>
    <div class="work_title">
      <!-- SOP 관리 -->
      <h2>{{ $t('LB00000931') }}</h2>
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
                <!--카테고리-->
                <div class="label_tit">{{ $t('LB00000934') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="sop_cat_cd"
                  v-model="frmData.sop_cat_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000008"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="sop_bsns_cl_cd"
                  v-model="frmData.sop_bsns_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000009"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
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
                <!--등록회사-->
                <div class="label_tit">{{ $t('LB00000940') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cmpy_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록부서-->
                <div class="label_tit">{{ $t('LB00000938') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.fscr_dept }}</div>
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
                <!--문서번호-->
                <div class="label_tit">{{ $t('LB00000935') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sop_doc_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--문서개정번호-->
                <div class="label_tit">{{ $t('LB00000941') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sop_doc_rvsn_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--SOP명-->
                <div class="label_tit">{{ $t('LB00000932') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sop_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관련법규 유무-->
                <div class="label_tit">{{ $t('LB00000936') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_rel_sop_yn }}</div>
              </v-col>
              <v-col cols="1">
                <!--관련 법규명-->
                <div class="label_tit">{{ $t('LB00000942') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sop_rel_rglt_nm }}</div>
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
                <!--SOP 개요-->
                <div class="label_tit">{{ $t('LB00000943') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.sop_txt" readonly outlined single-line :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 주요내용 -->
        <h3>{{ $t('LB00000944') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.sop_rel_rglt_txt" :options="editorOption" />
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
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: 'SOP 관리',
    key(route) {
      return `/sopmgnt/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      modify: false,
      codeid: ['SH00000008', 'SH00000009'],
      codes: {
        SH00000008: [{ cd: '', cd_nm: '' }], // 카테고리
        SH00000009: [{ cd: '', cd_nm: '' }], // 업무구분
      },
      sopYn: ['Y', 'N'],
      useYn: ['Y', 'N'],
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
      return this.$t('LB00000931') // SOP 관리
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

        if (this.$route.params.sopmgntdtllup !== null && this.$route.params.sopmgntdtllup !== undefined) {
          this.frmData.sop_id = this.$route.params.sopmgntdtllup
          this.schData.sop_id = this.$route.params.sopmgntdtllup
        } else {
          this.frmData.sop_id = ''
          this.schData.sop_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.sop_id = this.schData.sop_id
        }

        // ID 존재할 경우
        if (this.frmData.sop_id) {
          this.modify = true
          this.selectWkpl('select')
          this.$refs.quill.quill.enable(false)
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    selectWkpl(mode) {
      this.$axios.$get(`/api/v1/she/sfty/sopmgnt`, { params: { type: 'base_wkpl' } }).then((res) => {
        if (mode) {
          this.select()
        }
      })
    },
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/sfty/sopmgnt/${this.frmData.sop_id}`).then((res) => {
        this.frmData.sop_id = res.sop_id
        this.frmData.sop_cat_cd = res.sop_cat_cd
        this.frmData.sop_bsns_cl_cd = res.sop_bsns_cl_cd
        this.frmData.sop_doc_no = res.sop_doc_no
        this.frmData.sop_doc_rvsn_no = res.sop_doc_rvsn_no
        this.frmData.sop_nm = res.sop_nm
        this.frmData.rglt_rel_sop_yn = res.rglt_rel_sop_yn
        this.frmData.sop_rel_rglt_nm = res.sop_rel_rglt_nm
        this.frmData.sop_rel_rglt_txt = res.sop_rel_rglt_txt
        this.frmData.usg_yn = res.usg_yn
        this.frmData.sop_atfl_no = res.sop_atfl_no
        this.frmData.cmpy_nm = res.cmpy_nm
        this.frmData.sop_txt = res.sop_txt
        this.frmData.fscr_dept = res.fscr_dept
        this.frmData.lsch_nm = res.lsch_nm
        // this.frmData.upt_dt = res.upt_dt
        this.$set(this.frmData, 'upt_dt', res.upt_dt)
        // 적용사업장
        // for (let i = 0; i < dataProvider.getRowCount(); i++) {
        //   if (res.wkpl_list.findIndex((e) => e.wkpl_id === dataProvider.getValue(i, 'wkpl_id')) >= 0) {
        //     dataProvider.setValue(i, 'chk', 'Y')
        //   }
        // }

        if (this.frmData.sop_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.sop_atfl_no}`).then((data) => {
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
        name: `sopmgnt-sopmgntlist`,
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
  },
}
</script>
<style></style>
