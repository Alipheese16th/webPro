<template>
  <div>
    <div class="location">
      <!--규제대응--><!--인허가-->
      <span>Home > {{ $t('MN00000016') }} > {{ $t('MN98989907') }}</span>
    </div>
    <div class="work_title">
      <!--인허가-->
      <h2>{{ $t('MN98989907') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--인허가-->
        <h3>{{ $t('MN98989907') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="11">
                <!--입력하세요-->
                <v-text-field
                  v-model="savedatas.bbs_doc_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--인허가 구분-->
                <div class="label_tit">{{ $t('LB00000499') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="savedatas.doc_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000013"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--등록자-->
                <div class="label_tit">{{ $t('LB00000055') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ savedatas.username }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ savedatas.date }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" style="padding: 20px 0 !important">
                <quill-editor v-model="savedatas.bbs_doc_cntn" :options="editorOption" :disabled="quillChk" @change="onEditorChange" />
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
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!--삭제-->
      <v-btn v-if="searchdatas.bbs_doc_no != ''" depressed width="120" color="primary" height="40" @click="del">{{ $t('LB00000046') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>
<script>
import { quillEditor } from 'vue-quill-editor'
import FileUpload from '~/components/FileUpload.vue'

const date = new Date()

export default {
  meta: {
    title: '인허가 등록',
    key(route) {
      return `/rgltact/lncs/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      usrinfo: {},
      menu: false,
      quillChk: true,
      codeid: ['CO00000013'],
      codes: {
        CO00000013: [{ cd: '', cd_nm: '' }],
      },
      searchdatas: {
        bbs_doc_no: '',
      }, // 목록 화면에서 보내준 데이터
      savedatas: {
        bbs_type_cd: 'LNCS',
        doc_type_cd: '',
        bbs_doc_ttl: '',
        username: '', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        upt_emp_no: '', // 보여주기만 하는 데이터 수정 불가 java에서 session id insert ,update
        date: '', // 보여주기만 하는 데이터 수정 불가 java에서 현재 날짜 session id insert ,update
        noti_yn: '',
        bbs_doc_cntn: '',
        atfile_no: '',
        bbs_doc_no: '',
      },
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
      tempData: {
        bbs_doc_ttl: '',
        doc_type_cd: '',
        bbs_doc_cntn: '',
        fileModify: false, // 첨부파일 수정 여부
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('MN98989907') + ' ' + this.$t('LB00000111')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (
      this.tempData.bbs_doc_ttl !== this.savedatas.bbs_doc_ttl ||
      this.tempData.doc_type_cd !== this.savedatas.doc_type_cd ||
      this.tempData.bbs_doc_cntn !== this.savedatas.bbs_doc_cntn ||
      this.tempData.fileModify === true
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
  async created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    this.savedatas.username = this.usrinfo.emp_nm
    this.savedatas.upt_emp_no = this.usrinfo.emp_no
    this.savedatas.date = this.get_date_str_gubun(date, '-')
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        res.CO00000013.forEach((i) => {
          if (i.up_cd === 'LNCS') {
            this.codes.CO00000013.push(i)
          }
        })
        this.codes.CO00000013.unshift({ cd: '', cd_nm: 'SELECT' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        doc_type_cd: '',
      }
    }
    // 파라미터 조회 - 파라미터가 존재할 경우
    if (this.$route.params.rgltactlncsedt !== null && this.$route.params.rgltactlncsedt !== undefined) {
      this.searchdatas.bbs_doc_no = await this.$route.params.rgltactlncsedt
      this.selectDtl()
    } else {
      this.onEditorChange()
    }
  },
  mounted() {},
  methods: {
    chkFile() {
      this.tempData.fileModify = true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async selectDtl() {
      const data = await this.$axios.$get(`/api/v1/ch/rgltact/lncs/${this.searchdatas.bbs_doc_no}`)
      this.savedatas.bbs_doc_ttl = data.bbs_doc_ttl
      this.savedatas.doc_type_cd = data.doc_type_cd
      this.savedatas.bbs_doc_cntn = data.bbs_doc_cntn
      this.savedatas.username = data.emp_nm
      this.savedatas.upt_emp_no = data.upt_emp_no
      this.savedatas.date = data.upt_dt
      this.savedatas.atfile_no = data.atfile_no
      this.savedatas.bbs_doc_no = data.bbs_doc_no

      this.tempData.bbs_doc_ttl = data.bbs_doc_ttl
      this.tempData.doc_type_cd = data.doc_type_cd
      this.tempData.bbs_doc_cntn = data.bbs_doc_cntn
      let files = []
      if (!this.isEmpty(this.savedatas.atfile_no)) {
        files = await this.$axios.$get(`/api/v1/common/file/${this.savedatas.atfile_no}`)
        console.log('files : ', files) // eslint-disable-line no-console
      }
      if (files.length > 0) {
        await this.setFiles(files)
      }
      await this.changeEditMode(true)
      this.tempData.fileModify = false // 첨부파일 수정 여부 초기화
    },
    goList() {
      this.$router.push({
        name: `rgltact-rgltactlncslist`,
        params: {
          schData: {
            bbs_doc_ttl: this.tmpSchData.bbs_doc_ttl,
            bbs_doc_cntn: this.tmpSchData.bbs_doc_cntn,
            doc_type_cd: this.tmpSchData.doc_type_cd,
          },
        },
      })
    },
    async validationChk() {
      if ((await this.savedatas.bbs_doc_ttl) === '') {
        this.$toast.show(this.$t('MS00000034'), { className: 'toast_error' }) // 제목을 입력해 주세요.(KO)
        return false
      } else if ((await this.savedatas.doc_type_cd) === '') {
        this.$toast.show(this.$t('MS00000201'), { className: 'toast_error' }) // 인허가 구분을 선택해 주세요.
        return false
      } else if ((await this.savedatas.bbs_doc_cntn) === '') {
        this.$toast.show(this.$t('MS00000032'), { className: 'toast_error' }) // 내용을 입력해 주세요.(KO)
        return false
      } else {
        const chk = await this.$refs.upload.uploadStart().then((data) => {
          console.log('File Id : ', data) // eslint-disable-line no-console
          if (!this.isEmpty(data)) {
            this.savedatas.atfile_no = data + ''
          } else {
            this.savedatas.atfile_no = ''
          }
          if (
            this.tempData.bbs_doc_ttl === this.savedatas.bbs_doc_ttl &&
            this.tempData.doc_type_cd === this.savedatas.doc_type_cd &&
            this.tempData.bbs_doc_cntn === this.savedatas.bbs_doc_cntn &&
            this.tempData.fileModify === false
          ) {
            this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
            return false
          } else {
            // eslint-disable-next-line no-console
            console.log('유효성검사 끝>>>>')
            return true
          }
        })
        return chk
      }
    },
    async save() {
      if (await this.validationChk()) {
        const savenext = await this.$confirm(this.$t('MS00000010')) // 저장
        if (await !savenext) {
        } else {
          this.$axios.post('/api/v1/ch/rgltact/lncs', this.savedatas).then((res) => {
            if (res.data !== 'Fail' && res.data !== 'READ') {
              this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
              this.searchdatas.bbs_doc_no = res.data
              this.selectDtl()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      }
    },
    async del() {
      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?(KO)
      if (!res) {
        return
      }
      await this.$axios.delete(`/api/v1/ch/rgltact/lncs/${this.searchdatas.bbs_doc_no}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(false)
      })
    },
    onEditorChange(editor) {
      this.quillChk = false
    },
  },
}
</script>
<style lang="scss" scoped></style>
