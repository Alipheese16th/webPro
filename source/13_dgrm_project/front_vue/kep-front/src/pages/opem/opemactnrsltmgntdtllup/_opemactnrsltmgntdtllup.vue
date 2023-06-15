<template>
  <div>
    <div class="location">
      <!-- Home > 작업환경측정 > 작업환경측정 조치결과 관리 -->
      <span>Home > {{ $t('LB00000960') }} > {{ $t('LB00001010') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업환경측정 조치결과 관리 -->
      <h2>{{ $t('LB00001010') }}</h2>
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
                <!--조치 요청자-->
                <div class="label_tit">{{ $t('LB00001012') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_actn_prgr_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--작업환경측정 명-->
                <div class="label_tit">{{ $t('LB00001014') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acrq_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acrq_wkpl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acrq_dtl_loc_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록일시-->
                <div class="label_tit">{{ $t('LB00001015') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acrq_dttm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.acrq_note_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 조치 요청 정보 -->
        <h3>{{ $t('LB00001016') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--개선 희망일-->
                <div class="label_tit">{{ $t('LB00000671') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acrq_hope_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_acrq_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--첨부파일-->
                <div class="label_tit">{{ $t('LB00000121') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="acrq_atfl_no"
                  v-model="frmData.acrq_atfl_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen3('acrq', frmData.acrq_atfl_no)"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.acrq_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사진-->
                <div class="label_tit">{{ $t('LB00001017') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop1" />
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 조치 결과 정보 -->
        <h3>{{ $t('LB00001018') }}</h3>
      </div>
      <v-row>
        <v-col cols="40">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--조치 완료일-->
                <div class="label_tit">{{ $t('LB00001020') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.acco_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wem_acco_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--첨부파일-->
                <div class="label_tit">{{ $t('LB00000121') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="acco_atfl_no"
                  v-model="frmData.acco_atfl_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen3('acco', frmData.acco_atfl_no)"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea v-model="frmData.acco_txt" readonly :hide-details="true" dense name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사진-->
                <div class="label_tit">{{ $t('LB00001017') }}</div>
              </v-col>
              <v-col cols="11" class="warning_container">
                <picpreview ref="picPop2" />
              </v-col>
            </v-row>
          </v-container>
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
    <filePopup ref="pop3" mkey1="opemrsltlup" :edit-chk="edit" @close="popClose3" />
  </div>
</template>

<script>
import filePopup from '../../common/atflgrid'
import picpreview from '../../common/picpreview'

export default {
  meta: {
    title: '작업환경측정 조치결과 관리',
    key(route) {
      return `/opemactn/${route.params.catalog}`
    },
  },
  components: {
    filePopup,
    picpreview,
  },
  data() {
    return {
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
      },
      schData: {},
      frmData: {},
      popupNm: '',
      edit: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001010') // 작업환경측정 조치결과 관리
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

        if (this.$route.params.opemactnrsltmgntdtllup !== null && this.$route.params.opemactnrsltmgntdtllup !== undefined) {
          this.frmData.wem_actn_id = this.$route.params.opemactnrsltmgntdtllup
          this.schData.wem_actn_id = this.$route.params.opemactnrsltmgntdtllup
        } else {
          this.frmData.wem_actn_id = ''
          this.schData.wem_actn_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.wem_actn_id = this.schData.wem_actn_id
        }

        this.$refs.picPop1.setEdit(false)
        this.$refs.picPop2.setEdit(false)

        // ID 존재할 경우
        if (this.frmData.wem_actn_id) {
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
      this.$axios.$get(`/api/v1/she/hlth/opemactn/${this.frmData.wem_actn_id}`).then((res) => {
        this.frmData.wem_actn_id = res.wem_actn_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.wem_actn_prgr_emp_id = res.wem_actn_prgr_emp_id
        // this.frmData.wem_actn_prgr_emp_nm = res.wem_actn_prgr_emp_nm
        this.frmData.acrq_nm = res.acrq_nm
        this.frmData.acrq_wkpl_loc_id = res.acrq_wkpl_loc_id
        // this.frmData.acrq_wkpl_loc_nm = res.acrq_wkpl_loc_nm
        this.frmData.acrq_dtl_loc_nm = res.acrq_dtl_loc_nm
        this.frmData.acrq_dttm = res.acrq_dttm
        this.frmData.acrq_note_txt = res.acrq_note_txt
        this.frmData.acrq_hope_dt = res.acrq_hope_dt
        this.frmData.wem_acrq_emp_id = res.wem_acrq_emp_id
        // this.frmData.wem_acrq_emp_nm = res.wem_acrq_emp_nm
        this.frmData.acrq_txt = res.acrq_txt
        this.frmData.acrq_img_atfl_no1 = res.acrq_img_atfl_no1
        this.frmData.acrq_img_atfl_no2 = res.acrq_img_atfl_no2
        this.frmData.acrq_img_atfl_no3 = res.acrq_img_atfl_no3
        this.frmData.acrq_atfl_no = res.acrq_atfl_no
        // this.frmData.acrq_atfl_cnt = res.acrq_atfl_cnt
        this.frmData.acco_dt = res.acco_dt
        this.frmData.wem_acco_emp_id = res.wem_acco_emp_id
        // this.frmData.wem_acco_emp_nm = res.wem_acco_emp_nm
        this.frmData.acco_txt = res.acco_txt
        this.frmData.acco_img_atfl_no1 = res.acco_img_atfl_no1
        this.frmData.acco_img_atfl_no2 = res.acco_img_atfl_no2
        this.frmData.acco_img_atfl_no3 = res.acco_img_atfl_no3
        this.frmData.acco_atfl_no = res.acco_atfl_no
        // this.frmData.acco_atfl_cnt = res.acco_atfl_cnt
        this.frmData.acco_aprv_no = res.acco_aprv_no
        this.frmData.acco_prst_cd = res.acco_prst_cd
        this.$set(this.frmData, 'wem_actn_prgr_emp_nm', res.wem_actn_prgr_emp_nm)
        this.$set(this.frmData, 'acrq_wkpl_loc_nm', res.acrq_wkpl_loc_nm)
        this.$set(this.frmData, 'wem_acrq_emp_nm', res.wem_acrq_emp_nm)
        this.$set(this.frmData, 'acrq_atfl_cnt', res.acrq_atfl_cnt)
        this.$set(this.frmData, 'wem_acco_emp_nm', res.wem_acco_emp_nm)
        this.$set(this.frmData, 'acco_atfl_cnt', res.acco_atfl_cnt)

        // 이미지 로딩1
        if (this.frmData.acrq_img_atfl_no1) {
          this.$refs.picPop1.load(this.frmData.acrq_img_atfl_no1)
        }

        // 이미지 로딩2
        if (this.frmData.acco_img_atfl_no1) {
          this.$refs.picPop2.load(this.frmData.acco_img_atfl_no1)
        }

        this.frmData.ckFlag = false
        this.tempData = JSON.parse(JSON.stringify(this.frmData))
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
    popOpen3(name, fileNo) {
      this.popupNm = name
      const obj = {}
      obj.atfile_no = fileNo
      this.$refs.pop3.open(obj)
    },
    popClose3(returnData) {
      return false
    },
    goList() {
      this.$router.push({
        name: `opem-opemactnrsltmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
  },
}
</script>
<style></style>
