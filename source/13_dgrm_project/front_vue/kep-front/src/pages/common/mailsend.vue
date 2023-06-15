<template>
  <v-dialog v-model="dialog" :eager="true" scrollable persistent width="1200px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 메일발송 -->
        <v-toolbar-title>{{ $t('LB00000708') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 제목 -->
              <div class="label_tit">{{ $t('LB00000114') }}</div>
            </v-col>
            <v-col cols="10">
              <v-text-field ref="emailTtl" v-model="mailData.email_ttl" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 수신자 -->
              <div class="label_tit">{{ $t('LB00000707') }}</div>
            </v-col>
            <v-col cols="10">
              <div class="realgrid_container">
                <div class="grid_header">
                  <div class="grid_header_left"></div>
                  <div>
                    <!--추가-->
                    <v-btn color="" outlined height="30" @click="userAdd()">{{ $t('LB00000047') }}</v-btn>
                    <!--삭제-->
                    <v-btn color="" outlined height="30" @click="userDel()">{{ $t('LB00000046') }}</v-btn>
                  </div>
                </div>
                <div id="userrealgrid" style="width: 100%; height: 100px"></div>
              </div>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 내용 -->
              <div class="label_tit">{{ $t('LB00000113') }}</div>
            </v-col>
            <v-col cols="10" style="padding: 20px 0 !important">
              <!-- <quill-editor v-model="mailData.email_cntn" scrollable="true" :options="editorOption" @change="onEditorChange" /> -->
              <ckeditor-nuxt v-model="mailData.email_cntn" type="classic" :config="editorConfig" />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 첨부파일 -->
              <div class="label_tit">{{ $t('LB00000121') }}</div>
            </v-col>
            <v-col cols="10">
              <div class="container_filelist">
                <table>
                  <div v-if="true">
                    <file-upload ref="upload1" :multiple="true" @onDrop="chkFile()" />
                  </div>
                </table>
              </div>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 발송 -->
        <v-btn color="primary" depressed width="120" height="40" @click="send">{{ $t('LB00000706') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
    <muserpopup ref="muserpop" mkey1="mailsend1" mkey2="mailsend2" @close="popMuserClose" />
  </v-dialog>
</template>
<script>
// import Vue from 'vue'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
// import { quillEditor } from 'vue-quill-editor'
// import CKEditor from 'ckeditor4-vue'
import muserpopup from './mailuserslct'
import FileUpload from '~/components/FileUpload.vue'
// Vue.use(CKEditor)
// CKEditor.editorUrl = '../../../node_modules/ckeditor4/ckeditor'

export default {
  meta: {
    title: '메일발송',
    key(route) {
      return `/common/mailsend/${route.params.catalog}`
    },
  },
  components: {
    muserpopup,
    'ckeditor-nuxt': () => {
      if (process.client) {
        return import('@blowstack/ckeditor-nuxt')
      }
    },
    // quillEditor,
    FileUpload,
  },
  props: {
    mkey: {
      type: String,
      default: 'mailgrid',
    },
  },
  data() {
    return {
      editorConfig: {
        // The configuration of the editor.
        height: '500px',
        language: 'ko',
      },
      mailgridView: '',
      maildataProvider: '',
      dialog: false,
      valid: true,
      quillChk: true,
      fileNo: '',
      fileNm: '',
      pData: {
        email_ttl: '',
        email_cntn: '',
        atfl_no: 0,
      },
      rData: {},
      frmData: {},
      mailData: {
        email_ttl: '',
        email_cntn: '',
      },
      rcvData: {
        recv_emp_no: '',
        recv_email: '',
      },
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
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
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.maildataProvider = new LocalDataProvider(false)
    this.maildataProvider.setFields(mailfields)
    const gridId1 = document.getElementById('userrealgrid')
    gridId1.id = this.mkey + 'mailuserslct'
    this.mailgridView = new GridView(this.mkey + 'mailuserslct')
    this.mailgridView.setDataSource(this.maildataProvider)
    const mailcolumns = [
      {
        header: this.$t('LB00000701'), // 성명
        name: 'recv_emp_nm',
        fieldName: 'recv_emp_nm',
        width: '50',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000709'), // 이메일
        name: 'recv_email',
        fieldName: 'recv_email',
        width: '50',
        styleName: 'left',
      },
    ]
    this.mailgridView.setColumns(mailcolumns)
    this.mailgridView.setFooters({ visible: false })
    this.mailgridView.setStateBar({ visible: false })
    this.mailgridView.setCheckBar({ visible: true })
    this.mailgridView.editOptions.editable = false
    this.mailgridView.displayOptions.selectionStyle = 'block'
    this.mailgridView.header.height = 39
    this.mailgridView.displayOptions.rowHeight = 40
    this.mailgridView.footer.height = 40
    this.mailgridView.displayOptions.fitStyle = 'fill'
  },
  methods: {
    open(proVal) {
      this.pData = proVal
      if (!this.isEmpty(this.pData.email_ttl)) {
        this.mailData.email_ttl = this.pData.email_ttl
      }
      if (!this.isEmpty(this.pData.email_cntn)) {
        this.mailData.email_cntn = this.pData.email_cntn
      }
      if (!this.isEmpty(this.pData.atfl_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.pData.atfl_no}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles(data)
          }
        })
      }
      this.dialog = true
    },
    userAdd() {
      this.$refs.muserpop.open(this.mailData, false)
    },
    userDel() {
      this.mailgridView.commit()
      this.maildataProvider.setOptions({ softDeleting: true })
      const checkedRows = this.mailgridView.getCheckedRows()
      this.maildataProvider.hideRows(checkedRows)
      this.maildataProvider.removeRows(checkedRows)
    },
    async popMuserClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        console.log(returnData) // eslint-disable-line no-console
        await returnData.forEach((i) => {
          this.mailgridView.commit()
          this.maildataProvider.addRow({
            recv_emp_nm: i.emp_nm,
            recv_emp_no: i.emp_no,
            recv_email: i.emp_email,
          })
        })
      }
    },
    async send() {
      this.mailgridView.commit()
      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.fileNo = data
        }
      })
      // Validation 체크
      // if (!this.validate()) {
      //   return
      // }
      if (!this.isEmpty(this.fileNo)) {
        this.$axios.$get(`/api/v1/common/file/${this.fileNo}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles(data)
          }
        })
      }
      console.log(this.mailData.email_cntn) // eslint-disable-line no-console
      await this.makeForm(this.mailData.email_cntn)

      const res = await this.$confirm(this.$t('MS00000285')) // 메일발송하시겠습니까?
      if (!res) {
        return
      }
      console.log(this.mailData) // eslint-disable-line no-console

      if (this.maildataProvider.getRowStateCount('updated') > 0 || this.maildataProvider.getRowStateCount('deleted') > 0 || this.maildataProvider.getRowStateCount('created') > 0) {
        const rcvRow = []

        for (let i = 0; i < this.maildataProvider.getRowCount(); i++) {
          const temp = {}
          temp.recv_emp_nm = this.maildataProvider.getValue(i, 'recv_emp_nm')
          temp.recv_emp_no = this.maildataProvider.getValue(i, 'recv_emp_no')
          temp.recv_email = this.maildataProvider.getValue(i, 'recv_email')

          if (this.maildataProvider.getRowState(i) === 'created') {
            rcvRow.push(temp)
          }
        }
        this.frmData.rcv = rcvRow
      }

      const infoRow = []
      const temp = {}

      temp.email_ttl = this.mailData.email_ttl
      temp.email_cntn = this.mailData.email_cntn
      temp.biz_cd = '10'
      infoRow.push(temp)
      this.frmData.mail = infoRow

      this.mailData.ckFlag = true

      await this.$axios.post('/api/v1/comm/pop/send-mail', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'fail') {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        } else {
          this.$toast.show(this.$t('MS00000286')) // 발송되었습니다.
        }
      })
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
    },
    chkFile() {
      this.mailData.ckFlag = true
    },
    close() {
      this.$emit('close')
      this.reset()
    },
    reset() {
      this.dialog = false
      this.mailData = {}
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    validate() {
      this.mailgridView.commit()
      if (this.isEmpty(this.mailData.email_ttl)) {
        this.$toast.show(this.$t('MS00000291'), { className: 'toast_error' }) // 제목을 입력해주세요.
        this.$refs.emailTtl.focus()
        return false
      }
      if (this.isEmpty(this.mailData.email_cntn)) {
        this.$toast.show(this.$t('MS00000290'), { className: 'toast_error' }) // 내용을 입력해주세요.
        this.$refs.emailCntn.focus()
        return false
      }
      for (let i = 0; i < this.maildataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (this.maildataProvider.getRowState(i) === 'createAndDeleted' || this.maildataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(this.maildataProvider.getValue(i, 'recv_emp_no'))) {
          this.mailgridView.setCurrent({ dataRow: i, fieldName: 'recv_emp_no' })
          this.$toast.show(this.$t('MS00000289'), { className: 'toast_error' }) // 수신자를 입력해주세요.
          return false
        }
        let passyn = null
        for (let j = 0; j < this.maildataProvider.getRowCount(); j++) {
          if (i === j) {
            continue
          }
          if (this.maildataProvider.getValue(j, 'recv_emp_no') === this.maildataProvider.getValue(i, 'recv_emp_no')) {
            passyn = 'N'
          }
        }
        if (passyn === 'N') {
          this.mailgridView.setCurrent({ dataRow: i, fieldName: 'recv_emp_no' })
          this.$toast.show(this.$t('MS00000288'), { className: 'toast_error' }) // 동일한 수신자가 있습니다.
          return false
        }
        if (this.isEmpty(this.maildataProvider.getValue(i, 'recv_email'))) {
          this.mailgridView.setCurrent({ dataRow: i, fieldName: 'recv_email' })
          this.$toast.show(this.$t('MS00000287'), { className: 'toast_error' }) // 이메일을 입력해주세요.
          return false
        }
      }
      return true
    },
    makeForm(cntt) {
      let mailcn = cntt + '\r\n'
      if (!this.isEmpty(this.fileNo)) {
        mailcn = mailcn + '<p><a href=https://donggramisoft.co.kr/api/v1/public/download/' + this.fileNo + '/>' + this.$t('LB00000712') + '</a><br></p>\r\n' // 첨부파일다운로드
        console.log(mailcn) // eslint-disable-line no-console
      }
      this.mailData.email_cntn = mailcn
    },
    onEditorChange(editor) {
      this.quillChk = false
    },
  },
}
// 그리드 세팅
const mailfields = [
  {
    fieldName: 'recv_emp_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'recv_emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'recv_email',
    dataType: ValueType.TEXT,
  },
]
</script>
<style scoped></style>
