<template>
  <div>
    <div class="location">
      <!-- Home > 안전 > 대외점검 관리 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000915') }}</span>
    </div>
    <div class="work_title">
      <!-- 대외점검 관리 -->
      <h2>{{ $t('LB00000915') }}</h2>
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
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000002"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검구분-->
                <div class="label_tit">{{ $t('LB00000669') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_chk_cl_cd"
                  v-model="frmData.rglt_chk_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000012"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검기관-->
                <div class="label_tit">{{ $t('LB00000921') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_chk_orga_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관련법규-->
                <div class="label_tit">{{ $t('LB00000916') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rel_rglt_cd"
                  v-model="frmData.rel_rglt_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000013"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_vltn_bsns_cd"
                  v-model="frmData.rglt_vltn_bsns_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000014"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검일자-->
                <div class="label_tit">{{ $t('LB00000917') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_vltn_chk_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_vltn_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--발생부서-->
                <div class="label_tit">{{ $t('LB00000924') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.kor_rglt_vltn_org_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--담당자-->
                <div class="label_tit">{{ $t('LB00000101') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.rglt_vltn_emp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검내용-->
                <div class="label_tit">{{ $t('LB00000901') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.admn_dspt_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반여부-->
                <div class="label_tit">{{ $t('LB00000925') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select ref="rglt_vltn_yn" v-model="frmData.rglt_vltn_yn" :items="vltnYn" :hide-details="true" dense height="30" disabled></v-select>
              </v-col>
              <v-col cols="1">
                <!--위반구분-->
                <div class="label_tit">{{ $t('LB00000918') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_dspt_cl_cd"
                  v-model="frmData.rglt_dspt_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000015"
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위반금액/건수-->
                <div class="label_tit">{{ $t('LB00000926') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.fine_amt }} / {{ frmData.rglt_vltn_cnt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반명-->
                <div class="label_tit">{{ $t('LB00000920') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.rglt_vltn_ttl }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반내용-->
                <div class="label_tit">{{ $t('LB00000927') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.rglt_vltn_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--조치결과-->
                <div class="label_tit">{{ $t('LB00000685') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.rglt_vltn_actn_rslt_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="group_padd"></div>
      <!--첨부파일 목록(KO)-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist">
        <table>
          <div v-if="true">
            <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
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
    title: '대외점검 관리',
    key(route) {
      return `/rgltvltn/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      modify: false,
      codeid: ['ST00000002', 'SH00000012', 'SH00000013', 'SH00000014', 'SH00000015'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000012: [{ cd: '', cd_nm: '' }], // 점검구분
        SH00000013: [{ cd: '', cd_nm: '' }], // 관련법규
        SH00000014: [{ cd: '', cd_nm: '' }], // 위반업무
        SH00000015: [{ cd: '', cd_nm: '' }], // 위반구분
      },
      vltnYn: ['Y', 'N'],
      menu1: false,
      schData: {},
      frmData: {},
      tempData: {},
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000915') // 대외점검 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res

        if (this.$route.params.rgltvltnmgntdtllup !== null && this.$route.params.rgltvltnmgntdtllup !== undefined) {
          this.frmData.rglt_vltn_id = this.$route.params.rgltvltnmgntdtllup
          this.schData.rglt_vltn_id = this.$route.params.rgltvltnmgntdtllup
        } else {
          this.frmData.rglt_vltn_id = ''
          this.schData.rglt_vltn_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.rglt_vltn_id = this.schData.rglt_vltn_id
        }

        // ID 존재할 경우
        if (this.frmData.rglt_vltn_id) {
          this.select()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/sfty/rgltvltn/${this.frmData.rglt_vltn_id}`).then((res) => {
        this.frmData.rglt_vltn_id = res.rglt_vltn_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.rglt_chk_cl_cd = res.rglt_chk_cl_cd
        this.frmData.rel_rglt_cd = res.rel_rglt_cd
        this.frmData.rglt_vltn_bsns_cd = res.rglt_vltn_bsns_cd
        this.frmData.rglt_dspt_cl_cd = res.rglt_dspt_cl_cd
        this.frmData.rglt_vltn_chk_dt = res.rglt_vltn_chk_dt
        this.frmData.rglt_vltn_wkpl_loc_id = res.rglt_vltn_wkpl_loc_id
        this.frmData.rglt_vltn_wkpl_loc_nm = res.rglt_vltn_wkpl_loc_nm
        this.frmData.rglt_vltn_org_cd = res.rglt_vltn_org_cd
        this.frmData.kor_rglt_vltn_org_nm = res.kor_rglt_vltn_org_nm
        this.frmData.engl_rglt_vltn_org_nm = res.engl_rglt_vltn_org_nm
        this.frmData.rglt_vltn_emp_id = res.rglt_vltn_emp_id
        this.frmData.fine_amt = res.fine_amt
        this.frmData.rglt_vltn_cnt = res.rglt_vltn_cnt
        this.frmData.admn_dspt_txt = res.admn_dspt_txt
        this.frmData.rglt_vltn_ttl = res.rglt_vltn_ttl
        this.frmData.rglt_chk_orga_nm = res.rglt_chk_orga_nm
        this.frmData.rglt_vltn_txt = res.rglt_vltn_txt
        this.frmData.rglt_vltn_actn_rslt_txt = res.rglt_vltn_actn_rslt_txt
        this.frmData.rglt_vltn_yn = res.rglt_vltn_yn
        this.frmData.rglt_vltn_atfl_no = res.rglt_vltn_atfl_no
        // this.frmData.rglt_vltn_emp_nm = res.rglt_vltn_emp_nm
        this.$set(this.frmData, 'rglt_vltn_emp_nm', res.rglt_vltn_emp_nm)

        if (this.frmData.rglt_vltn_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.rglt_vltn_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, false)
            }
          })
        } else {
          this.$refs.upload.setEdit(false)
        }
      })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    goList() {
      this.$router.push({
        name: `rgltvltn-rgltvltnmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
  },
}
</script>
<style></style>
