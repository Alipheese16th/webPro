<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1000px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 조치내용등록 -->
        <v-toolbar-title>{{ $t('LB00000795') }}</v-toolbar-title>
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
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="acdt_acco_dt"
                      v-model="frmData.acdt_acco_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.acdt_acco_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
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
                <v-textarea ref="acdt_actn_txt" v-model="frmData.acdt_actn_txt" outlined single-line :hide-details="true" dense name="input-7-4"></v-textarea>
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
        <!--저장-->
        <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
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
    // this.$refs.picPop.setEdit(true)
  },
  methods: {
    open(obj) {
      this.$refs.picPop.setEdit(true)
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
    async save() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }
      // 사진 업로드 과정
      await this.$refs.picPop.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.acdt_actn_rslt_atfl_no = data
        } else {
          this.frmData.acdt_actn_rslt_atfl_no = 0
        }
      })
      this.$emit('close', this.frmData)
      this.reset()
    },
    validate() {
      if (!this.frmData.acdt_acco_dt) {
        this.$toast.show(this.$t('MS00000279'), { className: 'toast_error' }) // 조치완료일을 입력해주세요.
        this.$refs.acdt_acco_dt.focus()
        return false
      }
      if (!this.frmData.acdt_actn_txt) {
        this.$toast.show(this.$t('MS00000348'), { className: 'toast_error' }) // 조치내용을 입력해주세요.
        this.$refs.acdt_actn_txt.focus()
        return false
      }

      return true
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
