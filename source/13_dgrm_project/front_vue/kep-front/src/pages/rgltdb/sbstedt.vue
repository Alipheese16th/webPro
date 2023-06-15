<template>
  <v-dialog v-model="dialog" persistent width="1000px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 물질 -->
        <v-toolbar-title>{{ $t('LB00000328') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 물질번호 -->
              <div class="label_tit">{{ $t('LB00000317') }}</div>
            </v-col>
            <v-col cols="4">
              <div v-if="detail" class="label_con">{{ frmData.sbst_no }}</div>
              <v-text-field
                v-if="!detail"
                ref="sbstNo"
                v-model="frmData.sbst_no"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('LB00000033')"
                height="30"
              ></v-text-field>
            </v-col>
            <v-col cols="2">
              <!-- 물질구분 -->
              <div class="label_tit">{{ $t('LB00000270') }}</div>
            </v-col>
            <v-col cols="4">
              <v-select
                ref="sbstTypeCd"
                v-model="frmData.sbst_type_cd"
                label="SELECT"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.CH00000004"
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
              <!-- 물질명 -->
              <div class="label_tit">{{ $t('LB00000196') }}</div>
            </v-col>
            <v-col cols="10">
              <v-text-field ref="sbstKoNm" v-model="frmData.sbst_ko_nm" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 물질명(영문) -->
              <div class="label_tit">{{ $t('LB00000316') }}</div>
            </v-col>
            <v-col cols="10">
              <v-text-field ref="sbstEnNm" v-model="frmData.sbst_en_nm" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 사용여부 -->
              <div class="label_tit">{{ $t('LB00000049') }}</div>
            </v-col>
            <v-col cols="4">
              <v-select
                ref="useYn"
                v-model="frmData.use_yn"
                label="SELECT"
                item-text="cd_nm"
                item-value="cd"
                :items="use_yn_cd"
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
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
        <!-- 저장 -->
        <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
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
      dialog: false,
      valid: true,
      frmData: {},
      srchData: {},
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
      codeid: ['CH00000004'],
      codes: {
        CH00000004: [{ cd: '', cd_nm: '' }],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
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
    async open(detail, srchValue, writer) {
      // eslint-disable-next-line no-unused-vars
      const commCd = await this.$axios
        .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
        .then((res) => {
          this.codes = res
          this.codes.CH00000004.unshift({ cd: '', cd_nm: 'SELECT' })
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })

      this.detail = detail
      this.srchData.sbst_no = srchValue
      this.writer = writer
      this.dialog = true
      if (this.detail) {
        this.select()
      }
    },
    async select() {
      await this.$axios
        .$get(`/api/v1/ch/rgltdb/sbst-dtl/${this.srchData.sbst_no}`, { params: this.srchData })
        .then((res) => {
          this.frmData = res
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    async save() {
      // Validation 체크
      if (!this.validate()) {
        return
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      this.frmData.username = this.writer
      console.log(this.frmData) // eslint-disable-line no-console
      if (!this.detail) {
        // 신규 저장
        this.insert()
      } else {
        // 업데이트
        this.update()
      }
    },
    async insert() {
      await this.$axios.post('/api/v1/ch/rgltdb/sbst-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000138'), { className: 'toast_error' }) // 이미 존재하는 물질번호입니다.
        } else if (res.data === 'Fail') {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        } else {
          this.detail = true
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        }
      })
    },
    async update() {
      await this.$axios.put('/api/v1/ch/rgltdb/sbst-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validate() {
      if (this.isEmpty(this.frmData.sbst_no)) {
        this.$toast.show(this.$t('MS00000139'), { className: 'toast_error' }) // 물질번호를 입력해주세요.
        this.$refs.sbstNo.focus()
        return false
      } else if (this.isEmpty(this.frmData.sbst_type_cd)) {
        this.$toast.show(this.$t('MS00000140'), { className: 'toast_error' }) // 물질구분을 입력해주세요.
        this.$refs.sbstTypeCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.sbst_ko_nm)) {
        this.$toast.show(this.$t('MS00000141'), { className: 'toast_error' }) // 물질명을 입력해주세요.
        this.$refs.sbstKoNm.focus()
        return false
      } else if (this.isEmpty(this.frmData.sbst_en_nm)) {
        this.$toast.show(this.$t('MS00000142'), { className: 'toast_error' }) // 물질명(영문)을 입력해주세요.
        this.$refs.sbstEnNm.focus()
        return false
      } else if (this.isEmpty(this.frmData.use_yn)) {
        this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용여부를 선택해 주세요.
        this.$refs.useYn.focus()
        return false
      } else if (this.frmData.sbst_no.length > 20) {
        this.$toast.show(this.$t('MS00000143'), { className: 'toast_error' }) // 물질번호는 20자 이하로 작성해주세요.
        this.$refs.sbstNo.focus()
        return false
      } else if (this.frmData.sbst_ko_nm.length > 200) {
        this.$toast.show(this.$t('MS00000144'), { className: 'toast_error' }) // 물질명은 200자 이하로 작성해주세요.
        this.$refs.sbstKoNm.focus()
        return false
      } else if (this.frmData.sbst_en_nm.length > 200) {
        this.$toast.show(this.$t('MS00000145'), { className: 'toast_error' }) // 물질명(영문)은 200자 이하로 작성해주세요.
        this.$refs.sbstEnNm.focus()
        return false
      }
      return true
    },
  },
}
</script>
