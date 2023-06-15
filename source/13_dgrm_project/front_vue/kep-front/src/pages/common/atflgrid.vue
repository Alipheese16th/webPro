<template>
  <v-dialog v-model="dialog" persistent width="800px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 첨부파일 -->
        <v-toolbar-title>첨부파일</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="group_padd"></div>
      <v-container style="padding: 0px 30px">
        <!--첨부파일 목록(KO)-->
        <h3>{{ $t('LB00000120') }}</h3>
        <div class="container_filelist">
          <table>
            <div v-if="true">
              <file-upload ref="popUpload" :input-id="mkey1" :multiple="true" @onDrop="chkFilePop()" />
            </div>
          </table>
        </div>
      </v-container>
      <v-card-actions class="pop_btn">
        <!--저장-->
        <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import FileUpload from '~/components/FileUpload.vue'

const date = new Date()

export default {
  components: {
    FileUpload,
  },
  props: {
    mkey1: {
      type: [String],
      default() {
        return 'atflgrid'
      },
    },
    editChk: {
      type: [Boolean],
      default() {
        return true
      },
    },
    ispub: {
      type: [Boolean],
      default() {
        return false
      },
    },
  },
  data() {
    return {
      gridView: '',
      dialog: false,
      saveData: {},
      rtnData: {
        atfile_no: '',
        atfile_seq: '',
        name: '',
        size: '',
        key: '',
        atfile_url: '',
        crt_emp_no: '',
        crt_dt: '',
      },
      chkYnAll: '',
      size: 0,
      detail: false,
      textRules: [(v) => !!v || '필수항목입니다.'],
      fileModify: false,
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {},
  created() {},
  methods: {
    open(frmData) {
      this.saveData = frmData
      this.usrinfo = this.$store.getters['auth/getUser']
      this.saveData.username = this.usrinfo.emp_nm
      this.saveData.upt_emp_no = this.usrinfo.emp_no
      this.saveData.date = this.get_date_str_gubun(date, '-')
      this.dialog = true
      this.rtnData = {
        atfile_no: '',
        atfile_seq: '',
        name: '',
        size: '',
        key: '',
        atfile_url: '',
        crt_emp_no: '',
        crt_dt: '',
      }
      if (this.saveData.atfile_no !== null && this.saveData.atfile_no !== undefined) {
        this.select()
      }
    },
    chkFilePop() {
      this.fileModify = true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async select() {
      let files = []
      if (!this.isEmpty(this.saveData.atfile_no)) {
        files = await this.$axios.$get(`/api/v1/common/file/${this.saveData.atfile_no}`)
        this.rtnData = files
        console.log('files : ', files) // eslint-disable-line no-console
        console.log('rtnData : ', this.rtnData) // eslint-disable-line no-console
      }
      console.log('files s: ', files) // eslint-disable-line no-console
      if (files.length > 0) {
        await this.setFiles(files)
      }
      if (this.editChk === true) {
        await this.changeEditMode(true)
      } else {
        await this.changeEditMode(false)
      }
      this.fileModify = false // 첨부파일 수정 여부 초기화
    },
    validationChk() {
      if (this.fileModify === false) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return false
      }
      return true
    },
    async save() {
      if (this.validationChk()) {
        const savenext = await this.$confirm(this.$t('MS00000010')) // 저장
        if (await !savenext) {
        } else {
          await this.$refs.popUpload.uploadStart(this.ispub).then((data) => {
            console.log('File Id : ', data) // eslint-disable-line no-console
            if (!this.isEmpty(data)) {
              this.saveData.atfile_no = data + ''
              this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
              this.select()
            } else {
              this.saveData.atfile_no = ''
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    changeEditMode(mode) {
      this.$refs.popUpload.setEdit(mode)
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.popUpload.setFiles(files)
        this.$refs.popUpload.setEdit(false)
      })
    },
    close() {
      const rtnVal = []
      rtnVal.atfile_no = this.saveData.atfile_no
      rtnVal.file_list = this.rtnData
      if (!this.isEmpty(this.saveData.atfile_no)) {
        rtnVal.file_cnt = this.$refs.popUpload.files.length
      } else {
        rtnVal.file_cnt = 0
      }
      this.reset()
      this.$emit('close', rtnVal)
    },
    reset() {
      this.dialog = false
      this.saveData = {}
      this.$refs.popUpload.reset()
    },
  },
}
</script>
<style lang="scss" scoped></style>
