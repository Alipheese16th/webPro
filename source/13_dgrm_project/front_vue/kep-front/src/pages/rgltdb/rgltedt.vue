<template>
  <v-dialog v-model="dialog" persistent width="1000px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 규제 -->
        <v-toolbar-title>{{ $t('LB00000028') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_grid">
        <div class="container_detail">
          <v-container>
            <!-- 검토분야   
            <v-row>
              <v-col cols="2">
                <div class="label_tit">{{ $t('LB00000271') }}</div>
              </v-col>
              <v-col cols="10">
                <v-select
                  ref="rgltChkDomaCd"
                  v-model="frmData.rglt_chk_doma_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000002"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            -->
            <v-row>
              <v-col cols="2">
                <!-- 규제유형 -->
                <div class="label_tit">{{ $t('LB00000306') }}</div>
              </v-col>
              <v-col cols="10">
                <v-select
                  ref="rgltTypeCd"
                  v-model="frmData.rglt_type_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000001"
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
                <!-- 국가 -->
                <div class="label_tit">{{ $t('LB00000314') }}</div>
              </v-col>
              <v-col cols="10">
                <v-select
                  ref="natCd"
                  v-model="frmData.nat_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="cdLoad"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!-- 법률 -->
                <div class="label_tit">{{ $t('LB00000027') }}</div>
              </v-col>
              <v-col cols="10">
                <v-select
                  ref="lawCd"
                  v-model="frmData.law_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="dwCodes"
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
                <!-- 규제명 -->
                <div class="label_tit">{{ $t('LB00000213') }}</div>
              </v-col>
              <v-col cols="10">
                <v-text-field ref="rgltNm" v-model="frmData.rglt_nm" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!-- 규제명 약어 -->
                <div class="label_tit">{{ $t('LB00000305') }}</div>
              </v-col>
              <v-col cols="10">
                <!-- 입력하세요 -->
                <v-text-field ref="rgltAbbr" v-model="frmData.rglt_abbr" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!-- 정렬순서 -->
                <div class="label_tit">{{ $t('LB00000091') }}</div>
              </v-col>
              <v-col cols="10" style="display: flex">
                <!-- 입력하세요 -->
                <v-text-field ref="seq" v-model="frmData.sort_seq" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" height="30"></v-text-field>
                <!-- 정렬순서는 법률 이상으로 적용되지 않습니다. -->
                <div class="label_info ml-2">{{ $t('MS00000121') }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!-- 사용여부 -->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="10">
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
      </div>
      <v-card-actions class="pop_btn">
        <!-- 저장 -->
        <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
        <!-- 삭제 -->
        <v-btn v-if="detail" color="primary" depressed width="120" height="40" @click="del">{{ $t('LB00000046') }}</v-btn>
        <!-- 닫기 -->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
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
      frmTempData: {},
      srchData: {},
      cdSrchData: {},
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
      codeid: ['CH00000001', 'CH00000002', 'CO00000003', 'CH00000003'],
      codes: {
        CH00000001: [{ cd: '', cd_nm: '' }],
        CH00000002: [{ cd: '', cd_nm: '' }],
        CO00000003: [{ cd: '', cd_nm: '' }],
      },
      dwCodes: [{ cd: '', cd_nm: '' }],
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000028')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {},
  methods: {
    async open(detail, srchValue, writer) {
      this.dwCodes.unshift({ cd: '', cd_nm: 'SELECT' })
      await this.$axios
        .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
        .then((res) => {
          this.codes = res
          this.codes.CH00000001.unshift({ cd: '', cd_nm: 'SELECT' })
          //  this.codes.CH00000002.unshift({ cd: '', cd_nm: 'SELECT' })
          this.codes.CO00000003.unshift({ cd: '', cd_nm: 'SELECT' })
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
      this.detail = detail
      this.srchData.rgltNo = srchValue
      this.writer = writer
      this.dialog = true
      if (this.detail) {
        this.select()
      }
    },
    async select() {
      await this.$axios
        .$get(`/api/v1/ch/rgltdb/rglt-dtl/${this.srchData.rgltNo}`, { params: this.srchData })
        .then((res) => {
          this.frmData = res
          this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
      this.cdLoad()
    },
    async save() {
      if (JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

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
      this.frmData.rglt_chk_doma_cd = this.codes.CH00000002[0].cd // 검토분야 - 전체검토 default 값 셋팅
      console.log(this.frmData) // eslint-disable-line no-console
      if (!this.detail) {
        // 신규 저장
        this.insert()
      } else {
        // 업데이트
        this.update()
      }
    },
    close() {
      this.$emit('close', this.frmData.rglt_no)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
    async insert() {
      await this.$axios.post('/api/v1/ch/rgltdb/rglt-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Fail') {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        } else {
          this.detail = true
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        }
      })
    },
    async update() {
      await this.$axios.put('/api/v1/ch/rgltdb/rglt-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async del() {
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }
      this.frmData.username = this.writer
      await this.$axios.delete('/api/v1/ch/rgltdb/rglt-dtl', { params: this.frmData }).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
      this.close()
    },
    cdLoad() {
      this.cdSrchData.up_cd = this.frmData.nat_cd
      this.dwCodes = [{ cd: '', cd_nm: '' }]
      this.dwCodes.unshift({ cd: '', cd_nm: 'SELECT' })
      this.codes.CH00000003.forEach((i) => {
        if (i.up_cd === this.cdSrchData.up_cd) {
          this.dwCodes.push({ cd: i.cd, cd_nm: i.cd_nm })
        }
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validate() {
      /* 
      if (this.isEmpty(this.frmData.rglt_chk_doma_cd)) {
        this.$toast.show(this.$t('MS00000129'), { className: 'toast_error' }) // 검토분야를 입력해주세요.
        this.$refs.rgltChkDomaCd.focus()
        return false
      } else
      */
      if (this.isEmpty(this.frmData.rglt_type_cd)) {
        this.$toast.show(this.$t('MS00000128'), { className: 'toast_error' }) // 규제유형을 입력해주세요.
        this.$refs.rgltTypeCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.nat_cd)) {
        this.$toast.show(this.$t('MS00000127'), { className: 'toast_error' }) // 국가를 입력해주세요.
        this.$refs.natCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.law_cd)) {
        this.$toast.show(this.$t('MS00000126'), { className: 'toast_error' }) // 법률을 입력해주세요.
        this.$refs.lawCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.rglt_nm)) {
        this.$toast.show(this.$t('MS00000125'), { className: 'toast_error' }) // 규제명를 입력해주세요.
        this.$refs.rgltNm.focus()
        return false
      } else if (this.isEmpty(this.frmData.rglt_abbr)) {
        this.$toast.show(this.$t('MS00000124'), { className: 'toast_error' }) // 규제명 약어를 입력해주세요.
        this.$refs.rgltAbbr.focus()
        return false
      } else if (this.isEmpty(this.frmData.sort_seq)) {
        this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }) // 정렬순서를 입력해주세요.
        this.$refs.seq.focus()
        return false
      } else if (this.isEmpty(this.frmData.use_yn)) {
        this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용여부를 선택해 주세요.
        this.$refs.useYn.focus()
        return false
      } else if (this.frmData.rglt_nm.length > 200) {
        this.$toast.show(this.$t('MS00000123'), { className: 'toast_error' }) // 규제명은 200자 이하로 작성해주세요.
        this.$refs.rgltNm.focus()
        return false
      } else if (this.frmData.rglt_abbr.length > 200) {
        this.$toast.show(this.$t('MS00000122'), { className: 'toast_error' }) // 규제명 약어는 200자 이하로 작성해주세요.
        this.$refs.rgltAbbr.focus()
        return false
      } else if (!/^[0-9]*$/.test(this.frmData.sort_seq)) {
        this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }) // 정렬순서는 숫자만 입력해주세요.
        this.$refs.seq.focus()
        return false
      }
      return true
    },
  },
}
</script>
