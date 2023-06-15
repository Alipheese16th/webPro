<template>
  <v-dialog v-model="dialog" persistent width="700px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 환경 설정 -->
        <v-toolbar-title>{{ $t('LB00000349') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 사업장 -->
              <div class="label_tit">{{ $t('LB00000021') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 사업장 -->
              <v-select
                v-model="schData.wkplId"
                item-text="cd_nm"
                item-value="cd"
                :items="F_codes.F_00000001"
                :label="$t('LB00000021')"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
              ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 언어 -->
              <div class="label_tit">{{ $t('LB00000050') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 언어 -->
              <v-select
                v-model="schData.langCd"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.CO00000002"
                :label="$t('LB00000050')"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
              ></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 타임존 -->
              <div class="label_tit">{{ $t('LB00000350') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 타임존 -->
              <v-combobox
                v-model="tzonCd"
                :items="codes.CO00000004p"
                :label="$t('LB00000350')"
                item-text="label"
                item-value="value"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
                @input="tzInput"
              ></v-combobox>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- workspace -->
              <div class="label_tit">Workspace</div>
            </v-col>
            <v-col cols="10">
              <!-- workspace -->
              <v-select
                v-model="schData.workspaceCd"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.CO00000016"
                :label="$t('LB00001356')"
                outlined
                single-line
                :hide-details="true"
                dense
                height="30"
              ></v-select>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!-- 저장 -->
        <v-btn color="primary" depressed width="120" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
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
      tzonCd: '',
      schData: {
        wkplId: '',
        langCd: '',
        tzonCd: '',
        workspaceCd: '',
      },
      tempData: {
        wkplId: '',
        langCd: '',
        tzonCd: '',
        workspaceCd: '',
      },
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
      codeid: ['CO00000002', 'CO00000004', 'CO00000006', 'CO00000009', 'CO00000016'],
      codes: {
        CO00000002: [{ cd: '', cd_nm: '' }],
        // CO00000004: [{ cd: '', cd_nm: '' }],
        CO00000004p: [],
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
        CO00000016: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
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
  methods: {
    open(data) {
      // 수정여부, 데이터, 작성자
      this.dialog = true
      this.$toast.show(this.$t('MS00000193'), { className: 'toast_error' }) // 필수 환경이 설정되지 않았습니다. 설정 후 사용해주시기 바랍니다.
      // 사업장 조회
      this.$axios
        .$get(`/api/v1/comm/wkpl`, {})
        .then((res) => {
          this.FF_00000001 = res
          const cmpy = data.cmpn_cd
          this.codChgReturn(cmpy)
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
      this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
        this.codes = res
        this.schData.wkplId = data.wkpl_id
        this.schData.langCd = data.lang_cd
        this.schData.tzonCd = data.time_zone_cd
        this.schData.workspaceCd = data.workspace_cd
        this.tempData.wkplId = data.wkpl_id
        this.tempData.langCd = data.lang_cd
        this.tempData.tzonCd = data.time_zone_cd
        this.tempData.workspaceCd = data.workspace_cd
        this.tempTzonCd = { value: data.time_zone_cd, label: data.time_zone }
        this.tzonCd = { value: data.time_zone_cd, label: data.time_zone }
        const temp = []
        res.CO00000004.forEach((e) => {
          temp.push({ value: e.cd, label: e.cd_nm })
        })
        this.codes.CO00000004p = temp
      })
    },
    close() {
      this.$emit('close', 'Fail')
      this.reset()
      this.$router.push('/authInfo')
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.frmData = {}
    },
    tzInput() {
      if (this.isEmpty(this.tzonCd)) {
        this.schData.tzonCd = ''
      } else {
        this.schData.tzonCd = this.tzonCd.value
      }
    },
    validation() {
      if (this.isEmpty(this.schData.wkplId)) {
        this.$toast.show(this.$t('MS00000039'), { className: 'toast_error' }) // 사업장 코드를 설정해주세요.
        return false
      } else if (this.isEmpty(this.schData.tzonCd)) {
        this.$toast.show(this.$t('MS00000194'), { className: 'toast_error' }) // 타임존 코드를 설정해주세요.
        return false
      }

      return true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
    },
    async save() {
      if (!this.validation()) {
        return false
      }
      const res = await this.$confirm(this.$t('MS00000149')) // 저장하시면 해당 환경 설정으로 화면이 갱신됩니다. 저장하시겠습니까?
      if (!res) {
      } else {
        this.$axios.put(`/api/v1/public/config`, this.schData).then((res) => {
          if (res.data !== 'Fail' && res.data !== 'READ' && res.data !== 'TIMEFAIL') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.config = false
            this.reset()
            window.location.reload()
          } else if (res.data === 'TIMEFAIL') {
            this.$toast.show(this.$t('MS00000150'), { className: 'toast_error' }) // 잘못된 타임존 설정입니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
  },
}
</script>
