<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--공지사항(KO)-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('LB00000116') }}</span>
    </div>
    <div class="work_title">
      <!--공지사항(KO)-->
      <h2>{{ $t('LB00000116') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--공지사항(KO)-->
        <h3>{{ $t('LB00000116') }}</h3>
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
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000052') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.biz_cd_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--팝업공지 여부-->
                <div class="label_tit">{{ $t('LB00000112') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ datas.noti_yn }}</div>
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
          </v-container>
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

const date = new Date()
date.setDate(date.getDate() - 365)

export default {
  meta: {
    title: '커뮤니티-공지사항 상세',
    key(route) {
      return `/bbs/noti/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      searchdatas: {
        bbs_doc_no: '',
      }, // 목록 화면에서 보내준 데이터
      datas: {
        bbs_type_cd: 'NOTI',
        doc_type_cd: '',
        bbs_doc_ttl: '',
        username: 'AC926549', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        upt_emp_no: 'AC926549', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        date: '', // 보여주기만 하는 데이터 수정 불가 java에서 현재 날짜 session id insert ,update
        noti_yn: '',
        biz_cd_nm: '',
        bbs_doc_cntn: '',
        atfile_no: '',
        bbs_doc_no: '',
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
      return this.$t('LB00000116') + ' ' + this.$t('LB00000597')
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async created() {
    this.searchdatas.bbs_doc_no = await this.$route.params.notilup
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        noti_yn: '',
        biz_cd: '',
        sDate: date.toISOString().substr(0, 10),
        eDate: new Date().toISOString().substr(0, 10),
        emp_nm: '',
      }
    }
    if ((await this.searchdatas.bbs_doc_no) !== '') {
      this.selectDtl()
    }
  },
  methods: {
    async selectDtl() {
      const data = await this.$axios.$get(`/api/v1/common/bbs/noti/${this.searchdatas.bbs_doc_no}`)
      this.datas.bbs_doc_ttl = data.bbs_doc_ttl
      this.datas.bbs_doc_cntn = data.bbs_doc_cntn
      this.datas.username = data.upt_emp_no // 추후 emp_nm으로 변경
      this.datas.upt_emp_no = data.upt_emp_no
      this.datas.date = data.upt_dt
      this.datas.noti_yn = data.noti_yn
      this.datas.biz_cd_nm = data.biz_cd_nm
      this.datas.atfile_no = data.atfile_no
      this.datas.bbs_doc_no = data.bbs_doc_no
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
        name: `bbs-notilist`,
        params: {
          schData: {
            bbs_doc_ttl: this.tmpSchData.bbs_doc_ttl,
            bbs_doc_cntn: this.tmpSchData.bbs_doc_cntn,
            noti_yn: this.tmpSchData.noti_yn,
            biz_cd: this.tmpSchData.biz_cd,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
            emp_nm: this.tmpSchData.emp_nm,
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
