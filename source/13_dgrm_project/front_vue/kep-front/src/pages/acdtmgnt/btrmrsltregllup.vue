<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 조치완료내용 -->
        <v-toolbar-title>{{ $t('LB00000953') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="2">
                <!--조치완료일-->
                <div class="label_tit">{{ $t('LB00000684') }}</div>
              </v-col>
              <v-col cols="10">
                <div class="label_con">{{ frmData.acdt_acco_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!--조치자-->
                <div class="label_tit">{{ $t('LB00000796') }}</div>
              </v-col>
              <v-col cols="10">
                {{ frmData.acdt_actn_emp_nm }}
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!--조치내용-->
                <div class="label_tit">{{ $t('LB00000797') }}</div>
              </v-col>
              <v-col cols="10">
                <v-textarea ref="acdt_actn_txt" v-model="frmData.acdt_actn_txt" outlined single-line :hide-details="true" disabled height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!--조치사진-->
                <div class="label_tit">{{ $t('LB00000798') }}</div>
              </v-col>
              <v-col cols="10" class="warning_container">
                <picpreview ref="picPop" />
              </v-col>
            </v-row>
          </v-container>
        </div>
      </div>
      <v-card-actions class="pop_btn">
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import picpreview from '../common/picpreview'

export default {
  components: {
    picpreview,
  },
  data() {
    return {
      dialog: false,
      menu1: false,
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
  mounted() {
    this.$refs.picPop.setEdit(false)
  },
  methods: {
    open(obj) {
      this.frmData.data_row = obj.row
      this.frmData.acdt_id = obj.id
      this.frmData.acdt_actn_plan_sn = obj.sn
      this.frmData.acdt_acco_dt = obj.dt
      this.frmData.acdt_actn_emp_nm = obj.nm
      this.frmData.acdt_actn_txt = obj.txt
      this.frmData.acdt_actn_rslt_atfl_no = obj.atfl
      // 이미지 로딩
      if (this.frmData.acdt_actn_rslt_atfl_no) {
        this.$refs.picPop.load(this.frmData.acdt_actn_rslt_atfl_no)
      }
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
