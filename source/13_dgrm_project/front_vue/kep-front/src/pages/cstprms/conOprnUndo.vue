<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="581px">
    <v-card class="container_pop" style="max-height: 600px !important">
      <v-toolbar flat class="mb-6">
        <!--사유 입력-->
        <v-toolbar-title>{{ $t('LB00001349') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container>
          <v-row>
            <v-col cols="12" style="align-self: center">{{ $t('LB00001350') }}</v-col>
          </v-row>
          <v-row>
            <v-col cols="12">
              <v-text-field v-model="searchdatas.ntsg_txt" outlined single-line :hide-details="true" :placeholder="$t('LB00001351')" dense clearable height="60"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="12">{{ $t('LB00001352') }}<br />{{ $t('LB00001353') }}</v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!--취소-->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000427') }}</v-btn>
        <!--동의-->
        <v-btn color="primary" depressed width="120" height="40" @click="submit()">{{ $t('LB00001355') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      dialog: false,
      returnData: '',
      frmData: {
        ntsg_txt: '',
      },
      searchdatas: {},
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
      this.dialog = true
      this.frmData = frmData
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
    },
    onClearClicked() {
      this.searchdatas.ntsg_txt = ''
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async submit() {
      if (this.isEmpty(this.searchdatas.ntsg_txt)) {
        this.$toast.show(this.$t('MS00000675'), { className: 'toast_error' }) // 미실시 사유를 입력하세요
        return false
      }
      if (this.searchdatas.ntsg_txt.length > 30) {
        this.$toast.show(this.$t('MS00000676'), { className: 'toast_error' }) // 미실시 사유는 30자를 초과할수 없습니다.
        return false
      }
      const user = this.$store.getters['auth/getUser']
      this.frmData.time_zone = this.getTzTime(user.time_zone)
      this.frmData.ntsg_txt = this.searchdatas.ntsg_txt
      this.frmData.emp_nm = user.emp_nm
      this.frmData.dept_nm = user.dept_nm
      await this.$axios.put('/api/v1/she/cstprms/con-oprn-undo', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000674')) // 미실시 처리 완료되었습니다..
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
        this.$emit('close', res.data)
      })
    },
  },
}
</script>
