<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="500px">
    <v-card class="container_pop" style="height: 700px !important">
      <v-toolbar flat class="mb-6">
        <!-- 조치사진등록 -->
        <v-toolbar-title>{{ $t('조치사진등록') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="12" class="warning_container">
                <picpreview ref="picPop" style="height: 490px" />
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
      frmData: {},
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    // this.$refs.picPop.setEdit(true)
  },
  methods: {
    open(obj) {
      this.$refs.picPop.setEdit(true)
      this.$refs.picPop.setMaxFileCnt(1)
      this.frmData.atfile_no = obj.atfile_no
      // 이미지 로딩
      if (this.frmData.atfile_no) {
        this.$refs.picPop.load(this.frmData.atfile_no)
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
          this.frmData.atfile_no = data
        } else {
          this.frmData.atfile_no = 0
        }
      })
      this.$emit('close', this.frmData)
      this.reset()
    },
    validate() {
      if (this.$refs.picPop.validate() === false) {
        this.$toast.show('조치사진사진을 첨부해주세요.', { className: 'toast_error' })
        return false
      }

      return true
    },
    close() {
      this.frmData.checkFile = this.$refs.picPop.retrunData()
      this.$emit('close', this.frmData)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.$refs.picPop.reset()
    },
  },
}
</script>
