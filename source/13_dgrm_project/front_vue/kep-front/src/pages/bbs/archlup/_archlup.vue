<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--자료실(KO)-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('LB00000125') }}</span>
    </div>
    <div class="work_title">
      <!--자료실(KO)-->
      <h2>{{ $t('LB00000125') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--자료실(KO)-->
        <h3>{{ $t('LB00000125') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ datas.bbs_doc_ttl }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록자-->
                <div class="label_tit">{{ $t('LB00000055') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.username }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--업무구분(KO)-->
                <div class="label_tit">{{ $t('LB00000123') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.biz_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--분류(KO)-->
                <div class="label_tit">{{ $t('LB00000123') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.doc_type_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.date }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" style="padding: 20px 0 !important">
                <quill-editor v-model="datas.bbs_doc_cntn" :options="editorOption" :disabled="true" />
              </v-col>
            </v-row>
            <div class="group_padd"></div>
            <!--첨부파일 목록(KO)-->
            <h3>{{ $t('LB00000120') }}</h3>
            <div class="container_filelist">
              <table>
                <div v-if="true">
                  <file-upload ref="upload" :multiple="true" />
                </div>
              </table>
            </div>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>
<script>
import { quillEditor } from 'vue-quill-editor'
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: '자료실 상세',
    key(route) {
      return `/bbs/arch/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      searchdatas: {}, // 목록 화면에서 보내준 데이터
      datas: {
        bbs_type_cd: 'ARCH',
        doc_type_cd: '',
        bbs_doc_ttl: '',
        username: 'AC926549', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        upt_emp_no: 'AC926549', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        date: '', // 보여주기만 하는 데이터 수정 불가 java에서 현재 날짜 session id insert ,update
        noti_yn: '',
        bbs_doc_cntn: '',
        atfile_no: '',
        bbs_doc_no: '',
        doc_type_cd_nm: '',
        biz_cd_nm: '',
      },
      editorOption: {
        // some quill options
        modules: {
          toolbar: false,
        },
        placeholder: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000125') + ' ' + this.$t('LB00000597')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async created() {
    this.searchdatas.bbs_doc_no = await this.$route.params.archlup
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        doc_type_cd: '',
        biz_cd: '',
      }
    }
    if ((await this.searchdatas.bbs_doc_no) !== '') {
      this.selectDtl()
    }
  },
  methods: {
    async selectDtl() {
      const data = await this.$axios.$get(`/api/v1/common/bbs/arch/${this.searchdatas.bbs_doc_no}`)
      this.datas.bbs_doc_ttl = data.bbs_doc_ttl
      this.datas.bbs_doc_cntn = data.bbs_doc_cntn
      this.datas.username = data.upt_emp_no // 추후 emp_nm으로 변경
      this.datas.upt_emp_no = data.upt_emp_no
      this.datas.date = data.upt_dt
      this.datas.atfile_no = data.atfile_no
      this.datas.bbs_doc_no = data.bbs_doc_no
      this.datas.doc_type_cd_nm = data.doc_type_cd_nm
      this.datas.biz_cd_nm = data.biz_cd_nm
      this.changeEditMode(false)

      let files = []
      if (!this.isEmpty(this.datas.atfile_no)) {
        files = await this.$axios.$get(`/api/v1/common/file/${this.datas.atfile_no}`)
        console.log('files : ', files) // eslint-disable-line no-console
      }
      if (files.length > 0) {
        await this.setFiles(files)
      }
    },
    goList() {
      this.$router.push({
        name: `bbs-archlist`,
        params: {
          schData: {
            bbs_doc_ttl: this.tmpSchData.bbs_doc_ttl,
            bbs_doc_cntn: this.tmpSchData.bbs_doc_cntn,
            doc_type_cd: this.tmpSchData.doc_type_cd,
            biz_cd: this.tmpSchData.biz_cd,
          },
        },
      })
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
  },
}
</script>
<style lang="scss" scoped></style>
