<template>
  <v-dialog v-model="dialog" scrollable persistent width="1000px">
    <v-card class="container_pop">
      <v-card-text style="height: 800px">
        <v-toolbar flat class="mb-6">
          <!--경고표지 출력 양식 선택-->
          <v-toolbar-title>{{ $t('LB00000430') }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <!--닫기-->
          <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
        </v-toolbar>
        <div class="container_detail">
          <!--경고표지 2형-->
          <div class="sub_title">
            <h3>{{ $t('LB00000431') }}</h3>
          </div>
          <v-row>
            <v-col cols="12">
              <v-container>
                <v-row>
                  <v-col cols="4">
                    <!--경고표지 형식(가로:세로)-->
                    <div class="label_tit">{{ $t('LB00000432') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--5ℓ ≤ 용량 ＜ 50ℓ-->
                    <div class="label_tit">5ℓ ≤ {{ $t('LB00000433') }} ＜ 50ℓ</div>
                  </v-col>
                  <v-col cols="2">
                    <!--50ℓ ≤ 용량 ＜ 200ℓ-->
                    <div class="label_tit">50ℓ ≤ {{ $t('LB00000433') }} ＜ 200ℓ</div>
                  </v-col>
                  <v-col cols="2">
                    <!--200ℓ ≤ 용량 ＜ 500ℓ-->
                    <div class="label_tit">200ℓ ≤ {{ $t('LB00000433') }} ＜ 500ℓ</div>
                  </v-col>
                  <v-col cols="2">
                    <!--용량 ≥ 500ℓ-->
                    <div class="label_tit">{{ $t('LB00000433') }} ≥ 500ℓ</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <!--1:1-->
                    <div class="label_con">1:1</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('5_1')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('50_1')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('200_1')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('500_1')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <!--1:1.2-->
                    <div class="label_con">1:1.2</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('5_2')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('50_2')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('200_2')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('500_2')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <!--1:1.5-->
                    <div class="label_con">1:1.5</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('5_3')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('50_3')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('200_3')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('500_3')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <!--1.2:1-->
                    <div class="label_con">1.2:1</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('5_4')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('50_4')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('200_4')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('500_4')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">
                    <!--1.5:1-->
                    <div class="label_con">1.5:1</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('5_5')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('50_5')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('200_5')">{{ $t('LB00000038') }}</div>
                  </v-col>
                  <v-col cols="2">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('500_5')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </div>
        <div class="container_detail">
          <!--소형용기-->
          <div class="sub_title">
            <h3>{{ $t('LB00000443') }}</h3>
          </div>
          <v-row>
            <v-col cols="12">
              <v-container>
                <v-row>
                  <v-col cols="4">
                    <!--100 ㎖ 이하-->
                    <div class="label_tit">100 ㎖ {{ $t('LB00000444') }}</div>
                  </v-col>
                  <v-col cols="8">
                    <!--경고표지-->
                    <div class="label_con link" @click="getReport('100')">{{ $t('LB00000038') }}</div>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </div>
        <div class="container_detail">
          <div class="sub_title">
            <!--샘플용기-->
            <h3 style="display: inline">{{ $t('LB00000442') }}</h3>
            <!--해당되는 그림샘플을 클릭하시면 경고표지로 연동됩니다.-->
            <p style="display: inline">&nbsp; {{ $t('LB00000441') }}</p>
          </div>
          <v-row>
            <v-col cols="12">
              <v-container>
                <v-row>
                  <v-col cols="3">
                    <!--18ℓ(사각 캔)-->
                    <div class="label_tit">18ℓ({{ $t('LB00000440') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--18ℓ(원형 캔)-->
                    <div class="label_tit">18ℓ({{ $t('LB00000439') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--20ℓ(플라스틱 용기)-->
                    <div class="label_tit">20ℓ({{ $t('LB00000438') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--25㎏(포대)-->
                    <div class="label_tit">25㎏({{ $t('LB00000437') }})</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample1.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('5_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample2.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('5_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample3.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('5_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample4.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('5_2')" />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="12">
                    <!--90㎠ 1:1.2-->
                    <div class="label_tit" style="text-align: center">90㎠ 1:1.2</div>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </div>
        <div class="container_detail">
          <v-row>
            <v-col cols="12">
              <v-container>
                <v-row>
                  <v-col cols="3">
                    <!--180ℓ(드럼)-->
                    <div class="label_tit">180ℓ({{ $t('LB00000436') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--200ℓ(드럼)-->
                    <div class="label_tit">200ℓ({{ $t('LB00000436') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--500㎏(PP마대)-->
                    <div class="label_tit">500㎏(PP{{ $t('LB00000435') }})</div>
                  </v-col>
                  <v-col cols="3">
                    <!--1t(탱크)-->
                    <div class="label_tit">1t({{ $t('LB00000434') }})</div>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample5.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('50_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample6.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('200_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample7.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('500_2')" />
                  </v-col>
                  <v-col cols="3">
                    <v-img src="/images/ContainerSample8.jpg" width="100" height="110" style="cursor: pointer" @click="getReport('500_2')" />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="3">
                    <!--180㎠ 1:1.2-->
                    <div class="label_tit">180㎠ 1:1.2</div>
                  </v-col>
                  <v-col cols="3">
                    <!--300㎠ 1:1.2-->
                    <div class="label_tit">300㎠ 1:1.2</div>
                  </v-col>
                  <v-col cols="6">
                    <!--450㎠ 1:1.2-->
                    <div class="label_tit" style="text-align: center">450㎠ 1:1.2</div>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </div>
        <v-card-actions class="pop_btn">
          <!--닫기-->
          <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
        </v-card-actions>
      </v-card-text>
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
      frmData: {
        mtrlNoList: [],
        reportForm: '',
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
  updated() {},
  methods: {
    open(mtrlNoList) {
      this.frmData.mtrlNoList = mtrlNoList
      this.dialog = true
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
    getReport(reportForm) {
      const reportdata = {
        mtrlNoList: this.frmData.mtrlNoList,
        reportForm,
      }
      this.$axios.post(`/api/v1/comm/report/gwsgn`, reportdata).then((res) => {
        const popupWidth = 900
        const popupHeight = window.screen.height - 300
        const popupX = window.screen.width / 2 - popupWidth / 2
        const popupY = window.screen.height / 2 - popupHeight / 2
        const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
        const wnd = window.open('', 'report', options)
        wnd.document.open()
        wnd.document.write(res.data)
        wnd.document.close()
      })
      // const popupWidth = 900
      // const popupHeight = window.screen.height - 30
      // const popupX = window.screen.width / 2 - popupWidth / 2
      // const popupY = window.screen.height / 2 - popupHeight / 2
      // const options = 'status=no, menubar=no, toolbar=no, resizable=no,width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
      // // window.open('https://donggramisoft.co.kr/api/oz80/gwsgnprint.html?mtrl_no=' + mtrlNo + '&ozform=' + reportForm + '', 'report', options)
      // window.open('http://localhost:3000/oz80/gwsgnprint3.html?mtrl_no=60000023&ozform=5_1', 'testname', options)
    },
  },
}
</script>
