<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1300px">
    <v-card class="container_pop" style="height: 780px !important">
      <v-toolbar flat class="mb-6">
        <v-toolbar-title>미리보기</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="12">
              <!-- eslint-disable-next-line vue/no-v-html -->
              <div v-html="frmData.imgHtml"></div>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  meta: {
    title: '미리보기',
  },
  components: {},
  data() {
    return {
      dialog: false,
      frmData: {},
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
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
    },
  },
}
</script>
<style></style>
