<template>
  <div>
    <div class="location">
      <!-- Home > 유해위험기계기구 관리 > 안전검사 결과 관리 -->
      <span>Home > {{ $t('LB00000980') }} > {{ $t('LB00001032') }}</span>
    </div>
    <div class="work_title">
      <!-- 안전검사 결과 관리 -->
      <h2>{{ $t('LB00001032') }}</h2>
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
                <!--검사기관-->
                <div class="label_tit">{{ $t('LB00001031') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.hrm_sfip_orga_nm }}</div>
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
                <!--제목-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.hrm_sfip_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--검사기간-->
                <div class="label_tit">{{ $t('LB00001030') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <!-- 검사기간 일자 -->
                <div class="label_con">{{ frmData.hrm_sfip_bgn_dt }} ~ {{ frmData.hrm_sfip_end_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--검사결과-->
                <div class="label_tit">{{ $t('LB00001029') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.hrm_sfip_rslt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--검사내용-->
                <div class="label_tit">{{ $t('LB00001028') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="hrm_sfip_txt" v-model="frmData.hrm_sfip_txt" outlined single-line :hide-details="true" height="100" class="mt-2" dense disabled></v-textarea>
              </v-col>
            </v-row>
          </v-container>
          <div class="group_padd"></div>
          <!-- 첨부파일 목록 -->
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
    title: '안전검사 결과 관리',
    key(route) {
      return `/hmflmcn/sftinsp/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      quillChk: false,
      tmpSchData: {},
      schData: {},
      menu1: false,
      menu2: false,
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        hrm_sfip_no: '',
        hrm_sfip_orga_nm: '',
        hrm_sfip_nm: '',
        hrm_sfip_bgn_dt: '',
        hrm_sfip_end_dt: '',
        hrm_sfip_txt: '',
        hrm_sfip_atfl_no: '',
        hrm_sfip_rslt: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001032') // 안전검사 결과 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.sftinsprsltmgntdtllup !== null && this.$route.params.sftinsprsltmgntdtllup !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.hrm_sfip_no = this.$route.params.sftinsprsltmgntdtllup
      this.wkplId = this.$route.params.wkpl_id
      this.hrmSfipNo = this.$route.params.sftinsprsltmgntdtllup
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.hrm_sfip_no = this.$route.params.sftinsprsltmgntdtllup
    } else {
      this.frmData.wkpl_id = ''
      this.frmData.hrm_sfip_no = ''
      this.wkplId = ''
      this.hrmSfipNo = ''
      this.schData.wkpl_id = ''
      this.schData.hrm_sfip_no = ''
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        sDate: '',
        eDate: '',
      }
    }
  },
  mounted() {
    if (this.frmData.hrm_sfip_no !== '') {
      this.select()
    }
  },
  methods: {
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/hmflmcn/sft-insp-rslt-dtl/${this.schData.hrm_sfip_no}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
        if (!this.isEmpty(this.frmData.hrm_sfip_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.hrm_sfip_atfl_no}`).then((data) => {
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
        name: `hmflmcn-sftinsprsltmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
  },
}
</script>
<style></style>
