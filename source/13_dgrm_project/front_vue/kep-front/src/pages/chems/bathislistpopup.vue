<template>
  <v-dialog v-model="dialog" persistent width="800px">
    <v-card class="container_pop" style="height: 480px !important">
      <v-toolbar flat class="mb-6">
        <!-- 배치 수행 결과 내용 -->
        <v-toolbar-title>{{ $t('LB00000264') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section" style="overflow: hidden; padding: 1px 0">
        <div class="container_msg">
          <div class="label_con">
            {{ frmData.bat_rslt_cntn }}
          </div>
        </div>
      </div>
      <v-card-actions class="pop_btn">
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
      detail: false,
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
    open(detail, frmData) {
      this.detail = detail
      this.frmData = frmData
      console.log(this.frmData) // eslint-disable-line no-console
      this.dialog = true
    },
    close() {
      this.$emit('close')
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
  },
}
</script>
