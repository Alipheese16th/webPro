<template>
  <v-dialog v-model="dialog" persistent width="700px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 본인 인증 -->
        <v-toolbar-title>{{ $t('LB00000422') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="3">
              <!-- 휴대폰 번호 -->
              <div class="label_tit">{{ $t('LB00000423') }}</div>
            </v-col>
            <v-col cols="9">
              <!-- 등록되어 있는 번호가 없습니다. -->
              <v-text-field ref="nm" v-model="frmData.phoneNo" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000185')" height="30" disabled></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="3">
              <!-- SMS 승인번호 -->
              <div class="label_tit">{{ $t('LB00000424') }}</div>
            </v-col>
            <v-col cols="7">
              <!-- SNS 승인번호를 입력해주세요. -->
              <v-text-field ref="url" v-model="frmData.authNo" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000186')" height="30"></v-text-field>
            </v-col>
            <v-col cols="2">
              <!-- 승인번호 요청 -->
              <v-btn color="primary" depressed width="100" height="30" @click="req()">{{ $t('LB00000425') }}</v-btn>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 인증하기 -->
        <v-btn color="primary" depressed width="120" height="40" @click="chk()">{{ $t('LB00000426') }}</v-btn>
        <!-- 취소 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000427') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      yn: [
        {
          name: 'Y',
          val: 'Y',
        },
        {
          name: 'N',
          val: 'N',
        },
      ],
      dialog: false,
      valid: true,
      frmData: {
        phoneNo: '',
        authNo: '',
        certNo: '',
        reqDate: '',
      },
      tempData: {},
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  methods: {
    open(telNo) {
      // 수정여부, 데이터, 작성자
      this.dialog = true
      this.$toast.show(this.$t('MS00000187'), { className: 'toast_error' }) // 90일 이상 로그인 기록이 없습니다. 본인 인증 후 사용해주시기 바랍니다.
      this.frmData.phoneNo = telNo
    },
    close() {
      this.$emit('close', 'Fail')
      this.reset()
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.frmData = {}
    },
    req() {
      this.$axios.post('/api/v1/common/sms-check', this.frmData).then((res) => {
        if (res.data === 'Fail') {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        } else if (res.data.r_result === 'E' && res.data.r_resultCode === 'SMSAUTHT0905') {
          this.$toast.show(this.$t('MS00000188'), { className: 'toast_error' }) // 휴대전화 번호가 잘못되었습니다. 사용자 정보 수정 수 다시 접속해주세요.
          this.$router.push('/authInfo')
        } else if (res.data.r_result === 'E' && res.data.r_resultCode === 'SMSAUTHT0906') {
          this.$toast.show(this.$t('MS00000188'), { className: 'toast_error' }) // 휴대전화 번호가 잘못되었습니다. 사용자 정보 수정 수 다시 접속해주세요.
          this.$router.push('/authInfo')
        } else if (res.data.r_result === 'E') {
          this.$toast.show(this.$t('MS00000189'), { className: 'toast_error' }) // 인증처리에 오류가 발생했습니다. 다시 시도해주시기 바랍니다.
        } else {
          this.$toast.show(this.$t('MS00000190')) // 요청하였습니다.
        }
      })
    },
    async chk() {
      const result = await this.$axios.$get(`/api/v1/common/sms-check`, { params: this.frmData })
      if (result === 'Fail') {
        this.$toast.show(this.$t('MS00000191'), { className: 'toast_error' }) // 인증번호가 일치하지 않습니다.
      } else {
        this.$toast.show(this.$t('MS00000192')) // 인증되었습니다.
        this.$emit('close', 'Success')
        this.reset()
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
  },
}
</script>
