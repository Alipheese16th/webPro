<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 연구실 정기안전점검 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001136') }}</span>
    </div>
    <div class="work_title">
      <!-- 연구실 정기안전점검 -->
      <h2>{{ $t('LB00001136') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검년도-->
                <div class="label_tit">{{ $t('LB00001105') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.labs_sft_chk_yrmn }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--실시기관-->
                <div class="label_tit">{{ $t('LB00001138') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.labs_sft_chk_orga_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정시간-->
                <div class="label_tit">{{ $t('LB00000939') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.labs_sft_chk_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검내용-->
                <div class="label_tit">{{ $t('LB00000642') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="labs_sft_txt" v-model="frmData.labs_sft_chk_txt" outlined single-line :hide-details="true" height="100" class="mt-2" dense disabled></v-textarea>
              </v-col>
            </v-row>
          </v-container>
          <div class="group_padd"></div>
          <h3>{{ $t('LB00000120') }}</h3>
          <div class="container_filelist" style="max-height: 100px">
            <table>
              <div v-if="true">
                <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
              </div>
            </table>
          </div>
        </v-col>
      </v-row>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: '연구실 정기안전점검',
    key(route) {
      return `/sftchk/labssafty/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      tmpSchData: {},
      schData: {},
      frmData: {
        wkpl_id: '',
        labs_sft_chk_id: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_orga_nm: '',
        labs_sft_chk_nm: '',
        labs_sft_chk_txt: '',
        labs_sft_chk_atfl_no: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001136')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.labssaftychkmgntdtllup !== null && this.$route.params.labssaftychkmgntdtllup !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.labs_sft_chk_id = this.$route.params.labssaftychkmgntdtllup
      this.wkplId = this.$route.params.wkpl_id
      this.labsSftChkId = this.$route.params.labssaftychkmgntdtllup
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.labs_sft_chk_id = this.$route.params.labssaftychkmgntdtllup
    } else {
      this.frmData.wkpl_id = ''
      this.frmData.labs_sft_chk_id = ''
      this.wkplId = ''
      this.labsSftChkId = ''
      this.schData.wkpl_id = ''
      this.schData.labs_sft_chk_id = ''
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
      }
    }
  },
  mounted() {
    if (this.frmData.labs_sft_chk_id !== '') {
      this.select()
    }
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/sftchk/labs-safty-chk-dtl/${this.schData.labs_sft_chk_id}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        if (!this.isEmpty(this.frmData.labs_sft_chk_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.labs_sft_chk_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = []
        this.frmTempData = []
      }
    },
    chkFile() {
      this.frmData.ckFlag = true
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(false)
        this.$refs.upload.setDelete(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    goList() {
      this.$router.push({
        name: `sftchk-labssaftychkmgntlist`,
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
  },
}
</script>
<style></style>
