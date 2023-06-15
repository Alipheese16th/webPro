<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1300px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <v-toolbar-title>사고 속보 미리보기</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="12">
              <!-- eslint-disable-next-line vue/no-v-html -->
              <div v-html="acdtReportHtml"></div>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <v-btn v-show="isShow" color="primary" depressed width="120" height="40" @click="sendMail()">메일발송</v-btn>
        <v-btn v-show="isShow" color="primary" depressed width="120" height="40" @click="reportOutput()">리포트출력</v-btn>
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  meta: {
    title: '사고속보리포트',
  },
  data() {
    return {
      tempData: {},
      dialog: false,
      acdtReportData: {},
      acdtReportHtml: {},
      frmData: { dfdt_sn: '' },
      isShow: false,
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
  methods: {
    open(frmData) {
      this.frmData = frmData
      this.dialog = true
      if (Number(this.frmData.acdt_prst_cd) > 0) {
        this.isShow = true
      } else {
        this.isShow = false
      }
      this.select()
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/she/sfty/acdtmgnt/report/${this.frmData.acdt_id}`, { params: this.frmData })
      this.acdtReportHtml = rows
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
    async sendMail() {
      const res = await this.$confirm(
        '‘사고 속보‘ 발송 하시겠습니까? 발송된 후에는 메일을 회수 할 수 없습니다. <br/><br/> *잘못된 정보, 변경사항, 오타 등 사고 속보 내용을 충분히 검토하신 후<br/> 발송 바랍니다. <br/> *수신/참조인 변경은 ‘사고 속보 수신인 관리’ 메뉴에서 변경 가능 합니다.'
      )
      if (!res) {
        return
      }

      await this.$axios.post('/api/v1/she/sfty/acdtmgnt/report', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000286')) // 발송되었습니다.
        } else if (res.data === 'MAILRECVNOTDATA') {
          this.$toast.show('수신처 항목이 없습니다.', { className: 'toast_error' })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    reportOutput() {
      const reportdata = {
        acdt_id: this.frmData.acdt_id,
        dfdt_sn: this.frmData.dfdt_sn,
        reportForm: 'acdtNewsRpt',
      }
      this.$axios.post(`/api/v1/comm/report/acdtNewsRpt`, reportdata).then((res) => {
        const popupWidth = 900
        const popupHeight = window.screen.height - 300
        const popupX = window.screen.width / 2 - popupWidth / 2
        const popupY = window.screen.height / 2 - popupHeight / 2
        const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
        const wnd = window.open('', 'report', options)
        wnd.document.open()
        wnd.document.write(res.data)
        wnd.document.close()
        // this.print()
      })
    },
  },
}
</script>
<style></style>
