<template>
  <v-dialog v-model="dialog" persistent width="1000px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 템플릿 등록/조회 -->
        <v-toolbar-title>{{ $t('템플릿 등록/조회') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 템플릿ID -->
              <div class="label_tit">{{ $t('템플릿ID') }}</div>
            </v-col>
            <v-col cols="10">
              <div class="label_con">{{ frmData.tmptId }}</div>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 템플릿명 -->
              <div class="label_tit essn">{{ $t('템플릿명') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 템플릿명을 입력해주세요. -->
              <v-text-field
                ref="tmptNm"
                v-model="frmData.tmptNm"
                outlined
                single-line
                :hide-details="true"
                dense
                :placeholder="$t('템플릿명을 입력해주세요.')"
                height="30"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 업무코드 -->
              <div class="label_tit essn">{{ $t('업무코드') }}</div>
            </v-col>
            <v-col cols="10">
              <v-select
                ref="bizCd"
                v-model="frmData.bizCd"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.CO00000001"
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
              <!-- 업무코드 -->
              <div class="label_tit essn">{{ $t('템플릿구분') }}</div>
            </v-col>
            <v-col cols="10">
              <v-select
                ref="tmptDivCd"
                v-model="frmData.tmptDivCd"
                item-text="cd_nm"
                item-value="cd"
                :items="codes.CO00000039"
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
              <!-- 사용여부 -->
              <div class="label_tit">{{ $t('사용여부') }}</div>
            </v-col>
            <v-col cols="10">
              <v-select v-model="frmData.usgYn" :items="yn" item-text="name" item-value="val" outlined single-line :hide-details="true" dense height="30"></v-select>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 템플릿 내용 -->
              <div class="label_tit essn">{{ $t('템플릿 내용') }}</div>
            </v-col>
            <v-col cols="10">
              <v-tabs slider-size="3" color="#062C5E">
                <v-tab :style="styleObject">소스</v-tab>
                <v-tab :style="styleObject">미리보기</v-tab>
                <v-tab-item :eager="true">
                  <v-card flat>
                    <br />
                    <!-- 소스 -->
                    <v-textarea ref="tmptCntn" v-model="frmData.tmptCntn" outlined single-line :hide-details="true" height="200" class="mt-2" dense></v-textarea>
                  </v-card>
                </v-tab-item>
                <v-tab-item :eager="true">
                  <v-card flat>
                    <br />
                    <!-- html -->
                    <div class="label_con">
                      <!-- eslint-disable-next-line vue/no-v-html -->
                      <div style="overflow: auto; height: 200px" v-html="frmData.tmptCntn"></div>
                    </div>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <v-btn width="120" height="40" @click="testContent()">TEST</v-btn>
        <v-btn color="primary" depressed width="120" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
        <v-btn v-show="btnDelHide" color="primary" depressed width="120" height="40" @click="del()">{{ $t('삭제') }}</v-btn>
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
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
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
      codeid: ['CO00000001', 'CO00000039'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000039: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      selCO00000039: {
        cd: [],
        cd_nm: [],
      },
      dialog: false,
      valid: true,
      frmData: {
        bizCd: '',
        tmptId: '',
        tmptDivCd: '',
        tmptNm: '',
        tmptCntn: '',
        usgYn: '',
      },
      tempData: {
        bizCd: '',
        tmptId: '',
        tmptNm: '',
        tmptCntn: '',
        usgYn: '',
      },
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    btnDelHide() {
      return !this.isEmpty(this.frmData.tmptId)
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async mounted() {
    console.log('mounted')
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCO00000001.cd.push('')
        this.selCO00000001.cd_nm.push('SELECT')
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })

        this.selCO00000039.cd.push('')
        this.selCO00000039.cd_nm.push('SELECT')
        res.CO00000039.forEach((i) => {
          this.selCO00000039.cd.push(i.cd)
          this.selCO00000039.cd_nm.push(i.cd_nm)
        })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
  },
  activated() {
    console.log('activated')
  },
  created() {
    console.log('created')
  },
  methods: {
    open(detail, frmData, writer) {
      console.log('open')
      // 수정여부, 데이터, 작성자
      this.detail = detail // true일 경우 수정, false일 경우 삽입
      this.frmData = frmData
      this.tempData = JSON.parse(JSON.stringify(frmData))
      this.writer = writer
      this.dialog = true
    },
    async select() {
      const param = {
        tmptId: this.frmData.tmptId,
      }
      const rows = await this.$axios.$get(`/api/v1/common/chems/tmpt-list`, { params: param })
      console.log(rows)
      if (rows !== null && rows.length > 0) {
        this.frmData.bizCd = rows[0].bizCd
        this.frmData.tmptDivCd = rows[0].tmptDivCd
        this.frmData.tmptNm = rows[0].tmptNm
        this.frmData.tmptCntn = rows[0].tmptCntn
        this.frmData.usgYn = rows[0].usgYn
        this.frmData.delYn = rows[0].delYn
        this.tempData = JSON.parse(JSON.stringify(this.frmData))
      }
    },
    async save() {
      // Validation 체크
      if (!this.templateValidate()) {
        return
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      this.frmData.username = this.$store.getters['auth/getUsername']

      await this.$axios.post('/api/v1/common/chems/tmpt', this.frmData).then((res) => {
        this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
        this.close()
      })
    },
    async del() {
      // 삭제 여부 확인
      const res = await this.$confirm(this.$t('삭제하시겠습니까?')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      this.frmData.delYn = 'Y'

      await this.$axios.post('/api/v1/common/chems/tmpt', this.frmData).then((res) => {
        this.$toast.show(this.$t('삭제되었습니다.')) // 저장되었습니다.
        this.close()
      })
    },
    close() {
      this.$emit('close')
      this.reset()
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this.frmData = {
        bizCd: '',
        tmptId: '',
        tmptDivCd: '',
        tmptNm: '',
        tmptCntn: '',
        usgYn: '',
      }
    },
    isEmpty(str) {
      return typeof str === 'undefined' || str == null || str === ''
    },
    templateValidate() {
      if (this.isEmpty(this.frmData.tmptNm)) {
        this.$toast.show(this.$t('템플릿명을 입력하세요.'), { className: 'toast_error' }) // 템플릿명을 입력하세요.
        this.$refs.tmptNm.focus()
        return false
      } else if (this.isEmpty(this.frmData.bizCd)) {
        this.$toast.show(this.$t('업무코드를 선택하세요.'), { className: 'toast_error' }) // 업무코드를 선택하세요.
        this.$refs.bizCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.tmptDivCd)) {
        this.$toast.show(this.$t('템플릿구분을 선택하세요.'), { className: 'toast_error' }) // 업무코드를 선택하세요.
        this.$refs.tmptDivCd.focus()
        return false
      } else if (this.isEmpty(this.frmData.tmptCntn)) {
        this.$toast.show(this.$t('템플릿 내용을 입력하세요.'), { className: 'toast_error' }) // 템플릿 내용을 입력하세요.
        this.$refs.tmptCntn.focus()
        return false
      }
      if (
        this.tempData.bizCd === this.frmData.bizCd &&
        this.tempData.tmptDivCd === this.frmData.tmptDivCd &&
        this.tempData.tmptNm === this.frmData.tmptNm &&
        this.tempData.tmptCntn === this.frmData.tmptCntn &&
        this.tempData.usgYn === this.frmData.usgYn
      ) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      return true
    },

    async testContent() {
      const param = {
        tmptId: this.frmData.tmptId,
        content: {
          petList: [{
            name: 'CAT',
            price: '100',
          }, {
            name: 'DOG',
            price: '200'
          }, {
            name: 'LION',
            price: '800'
          }, {
            name: 'TIGER',
            price: '1000'
          }],
        },
      }
      this.$axios.$post(`/api/v1/common/chems/tmpt-cntn`, param).then(result => {
        console.log(result.content)
      })
    },
  },
}
</script>
