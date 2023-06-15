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
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--조치 요청자-->
                <div class="label_tit essn">{{ $t('LB00001012') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wem_actn_prgr_emp_nm"
                  v-model="frmData.wem_actn_prgr_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('reqr')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--작업환경측정 명-->
                <div class="label_tit essn">{{ $t('LB00001014') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  ref="acrq_nm"
                  v-model="frmData.acrq_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit essn">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="acrq_wkpl_loc_nm"
                  v-model="frmData.acrq_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen2"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit essn">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  ref="acrq_dtl_loc_nm"
                  v-model="frmData.acrq_dtl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
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
                <!-- 입력하세요 -->
                <v-textarea
                  ref="acrq_note_txt"
                  v-model="frmData.acrq_note_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  name="input-7-4"
                ></v-textarea>
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
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="acrq_hope_dt"
                      v-model="frmData.acrq_hope_dt"
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
                  <v-date-picker v-model="frmData.acrq_hope_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wem_acrq_emp_nm"
                  v-model="frmData.wem_acrq_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('acrq')"
                ></v-text-field>
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
                <!-- 입력하세요 -->
                <v-textarea ref="acrq_txt" v-model="frmData.acrq_txt" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" name="input-7-4"></v-textarea>
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
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="acco_dt"
                      v-model="frmData.acco_dt"
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
                  <v-date-picker v-model="frmData.acco_dt" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--조치 담당자-->
                <div class="label_tit">{{ $t('LB00000678') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="wem_acco_emp_nm"
                  v-model="frmData.wem_acco_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('acco')"
                ></v-text-field>
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
                <!-- 입력하세요 -->
                <v-textarea ref="acco_txt" v-model="frmData.acco_txt" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" name="input-7-4"></v-textarea>
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
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" @click="appr">{{ $t('LB00000763') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="opemrsltedt1" mkey2="opemrsltedt2" @close="popClose" />
    <rgnslct ref="pop2" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="opemrsltedt" @close="popClose2" />
    <filePopup ref="pop3" mkey1="opemrsltedt" @close="popClose3" />
  </div>
</template>

<script>
import userslct from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
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
    userslct,
    rgnslct,
    filePopup,
    picpreview,
  },
  data() {
    return {
      modify: false,
      codeid: [],
      menu1: false,
      menu2: false,
      schData: {},
      frmData: {},
      tempData: {},
      popupNm: '',
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
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
  async beforePageLeave(tab, type) {
    if (this.modify && JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
      const res = await this.$confirm(this.$t('MS00000060'))
      return new Promise((resolve, reject) => {
        if (res) {
          resolve()
        } else {
          // eslint-disable-next-line prefer-promise-reject-errors
          reject('reject')
        }
      })
    }
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = user.cmpn_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res

        if (this.$route.params.opemactnrsltmgntdtledt !== null && this.$route.params.opemactnrsltmgntdtledt !== undefined) {
          this.frmData.wem_actn_id = this.$route.params.opemactnrsltmgntdtledt
          this.schData.wem_actn_id = this.$route.params.opemactnrsltmgntdtledt
        } else {
          this.frmData.wem_actn_id = ''
          this.schData.wem_actn_id = ''
          this.frmData.wkpl_id = user.wkpl_id
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.wem_actn_id = this.schData.wem_actn_id
        }

        this.$refs.picPop1.setEdit(true)
        this.$refs.picPop2.setEdit(true)

        // ID 존재할 경우
        if (this.frmData.wem_actn_id) {
          this.modify = true
          this.select()
        } else {
          // const user = this.$store.getters['auth/getUser']
          // this.frmData.acrq_hope_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')
          // this.frmData.acco_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')
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
        // this.frmData.acco_prst_cd = res.acco_prst_cd
        this.$set(this.frmData, 'wem_actn_prgr_emp_nm', res.wem_actn_prgr_emp_nm)
        this.$set(this.frmData, 'acrq_wkpl_loc_nm', res.acrq_wkpl_loc_nm)
        this.$set(this.frmData, 'wem_acrq_emp_nm', res.wem_acrq_emp_nm)
        this.$set(this.frmData, 'acrq_atfl_cnt', res.acrq_atfl_cnt)
        this.$set(this.frmData, 'wem_acco_emp_nm', res.wem_acco_emp_nm)
        this.$set(this.frmData, 'acco_atfl_cnt', res.acco_atfl_cnt)
        this.$set(this.frmData, 'acco_prst_cd', res.acco_prst_cd)

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
    save() {
      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          this.update()
        }
      } else {
        this.insert()
      }
    },
    async insert() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.acco_prst_cd = '10' // 기본값 '조치필요'
      if (this.frmData.wem_acrq_emp_id) {
        this.frmData.acco_prst_cd = '20' // '조치중'
      }
      if (this.frmData.wem_acco_emp_id) {
        this.frmData.acco_prst_cd = '40' // '조치완료'
      }

      // 사진 업로드 과정
      await this.$refs.picPop1.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.acrq_img_atfl_no1 = data
        } else {
          this.frmData.acrq_img_atfl_no1 = '0'
        }
      })
      await this.$refs.picPop2.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.acco_img_atfl_no1 = data
        } else {
          this.frmData.acco_img_atfl_no1 = '0'
        }
      })

      await this.$axios.post('/api/v1/she/hlth/opemactn', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wem_actn_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async update() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.wem_acrq_emp_id) {
        this.frmData.acco_prst_cd = '20' // '조치중'
      }
      // if (this.frmData.wem_acco_emp_id) {
      //   this.frmData.acco_prst_cd = '40' // '조치완료'
      // }

      // 사진 업로드 과정
      await this.$refs.picPop1.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.acrq_img_atfl_no1 = data
        } else {
          this.frmData.acrq_img_atfl_no1 = '0'
        }
      })
      await this.$refs.picPop2.save().then((data) => {
        console.log('Image info : ', data) // eslint-disable-line no-console
        if (data !== undefined) {
          // 이미지가 존재할 경우 frmData에 설정해준다.
          this.frmData.acco_img_atfl_no1 = data
        } else {
          this.frmData.acco_img_atfl_no1 = '0'
        }
      })

      await this.$axios.put('/api/v1/she/hlth/opemactn', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.wem_actn_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      if (!this.frmData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (!this.frmData.wem_actn_prgr_emp_nm) {
        this.$toast.show(this.$t('MS00000497'), { className: 'toast_error' }) // 조치요청자를 입력해주세요.
        this.$refs.wem_actn_prgr_emp_nm.focus()
        return false
      }
      if (!this.frmData.acrq_nm) {
        this.$toast.show(this.$t('MS00000498'), { className: 'toast_error' }) // 작업환경측정명을 입력해주세요.
        this.$refs.acrq_nm.focus()
        return false
      }
      if (this.frmData.acrq_nm.length > 100) {
        this.$toast.show(this.$t('MS00000499'), { className: 'toast_error' }) // 작업환경측정명을 100자 이하로 입력해주세요.
        this.$refs.acrq_nm.focus()
        return false
      }
      if (!this.frmData.acrq_wkpl_loc_nm) {
        this.$toast.show(this.$t('MS00000403'), { className: 'toast_error' }) // 위치를 입력해주세요.
        this.$refs.acrq_wkpl_loc_nm.focus()
        return false
      }
      if (!this.frmData.acrq_dtl_loc_nm) {
        this.$toast.show(this.$t('MS00000500'), { className: 'toast_error' }) // 상세위치를 입력해주세요.
        this.$refs.acrq_dtl_loc_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.acrq_dtl_loc_nm) && this.frmData.acrq_dtl_loc_nm.length > 20) {
        this.$toast.show(this.$t('MS00000501'), { className: 'toast_error' }) // 상세위치는 20자 이하로 입력해주세요.
        this.$refs.acrq_dtl_loc_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.acrq_note_txt) && this.frmData.acrq_note_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000134'), { className: 'toast_error' }) // 비고는 1000자 이하로 입력해주세요.
        this.$refs.acrq_note_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.acrq_txt) && this.frmData.acrq_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000502'), { className: 'toast_error' }) // 조치요청 내용은 1000자 이하로 입력해주세요.
        this.$refs.acrq_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.acco_txt) && this.frmData.acco_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000503'), { className: 'toast_error' }) // 조치결과 내용은 1000자 이하로 입력해주세요.
        this.$refs.acco_txt.focus()
        return false
      }

      return true
    },
    async appr() {
      if (!this.validate()) {
        return
      }

      if (!this.frmData.acrq_hope_dt) {
        this.$toast.show(this.$t('MS00000261'), { className: 'toast_error' }) // 개선 희망일을 입력해주세요.
        this.$refs.acrq_hope_dt.focus()
        return false
      }
      if (!this.frmData.wem_acrq_emp_nm) {
        this.$toast.show(this.$t('MS00000504'), { className: 'toast_error' }) // 조치담당자를 입력해주세요.
        this.$refs.wem_acrq_emp_nm.focus()
        return false
      }
      if (!this.frmData.acrq_txt) {
        this.$toast.show(this.$t('MS00000505'), { className: 'toast_error' }) // 조치요청 내용을 입력해주세요.
        this.$refs.acrq_txt.focus()
        return false
      }
      if (!this.frmData.acco_dt) {
        this.$toast.show(this.$t('MS00000506'), { className: 'toast_error' }) // 조치완료일을 입력해주세요.
        this.$refs.acco_dt.focus()
        return false
      }
      if (!this.frmData.wem_acco_emp_nm) {
        this.$toast.show(this.$t('MS00000504'), { className: 'toast_error' }) // 조치담당자를 입력해주세요.
        this.$refs.wem_acco_emp_nm.focus()
        return false
      }
      if (!this.frmData.acco_txt) {
        this.$toast.show(this.$t('MS00000507'), { className: 'toast_error' }) // 조치요청 내용을 입력해주세요.
        this.$refs.acco_txt.focus()
        return false
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          // this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          if (this.frmData.wem_acrq_emp_id) {
            this.frmData.acco_prst_cd = '20' // '조치중'
          }
          // if (this.frmData.wem_acco_emp_id) {
          //   this.frmData.acco_prst_cd = '40' // '조치완료'
          // }

          // 사진 업로드 과정
          await this.$refs.picPop1.save().then((data) => {
            console.log('Image info : ', data) // eslint-disable-line no-console
            if (data !== undefined) {
              // 이미지가 존재할 경우 frmData에 설정해준다.
              this.frmData.acrq_img_atfl_no1 = data
            } else {
              this.frmData.acrq_img_atfl_no1 = '0'
            }
          })
          await this.$refs.picPop2.save().then((data) => {
            console.log('Image info : ', data) // eslint-disable-line no-console
            if (data !== undefined) {
              // 이미지가 존재할 경우 frmData에 설정해준다.
              this.frmData.acco_img_atfl_no1 = data
            } else {
              this.frmData.acco_img_atfl_no1 = '0'
            }
          })

          await this.$axios.put('/api/v1/she/hlth/opemactn', this.frmData).then((res) => {
            if (res.data !== 'Fail') {
              this.frmData.wem_actn_id = res.data
              this.schData.wem_actn_id = res.data
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      } else {
        this.frmData.acco_prst_cd = '10' // 기본값 '조치필요'
        if (this.frmData.wem_acrq_emp_id) {
          this.frmData.acco_prst_cd = '20' // '조치중'
        }
        if (this.frmData.wem_acco_emp_id) {
          this.frmData.acco_prst_cd = '40' // '조치완료'
        }

        // 사진 업로드 과정
        await this.$refs.picPop1.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.acrq_img_atfl_no1 = data
          } else {
            this.frmData.acrq_img_atfl_no1 = '0'
          }
        })
        await this.$refs.picPop2.save().then((data) => {
          console.log('Image info : ', data) // eslint-disable-line no-console
          if (data !== undefined) {
            // 이미지가 존재할 경우 frmData에 설정해준다.
            this.frmData.acco_img_atfl_no1 = data
          } else {
            this.frmData.acco_img_atfl_no1 = '0'
          }
        })

        await this.$axios.post('/api/v1/she/hlth/opemactn', this.frmData).then((res) => {
          if (res.data !== 'Fail') {
            this.frmData.wem_actn_id = res.data
            this.schData.wem_actn_id = res.data
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }

      this.modify = false

      // this.frmData.acco_prst_cd = '1' // 결재상신
      this.frmData.type = 'appr'

      await this.$axios.put('/api/v1/she/hlth/opemactn', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl10 + aprvno + empno
          window.open(url, 'opemactn', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `opem-opemactnrsltmgntdtllup-opemactnrsltmgntdtllup`,
            params: {
              schData: this.schData,
              opemactnrsltmgntdtllup: this.schData.wem_actn_id,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
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
    popOpen(name) {
      this.popupNm = name
      this.$refs.pop.open(this.frmData, false)
    },
    popOpen2() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open()
    },
    popOpen3(name, fileNo) {
      this.popupNm = name
      const obj = {}
      obj.atfile_no = fileNo
      this.$refs.pop3.open(obj)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.popupNm === 'reqr') {
        this.$set(this.frmData, 'wem_actn_prgr_emp_nm', returnData[0].emp_nm)
        this.$set(this.frmData, 'wem_actn_prgr_emp_id', returnData[0].emp_no)
      } else if (this.popupNm === 'acrq') {
        this.$set(this.frmData, 'wem_acrq_emp_nm', returnData[0].emp_nm)
        this.$set(this.frmData, 'wem_acrq_emp_id', returnData[0].emp_no)
      } else if (this.popupNm === 'acco') {
        this.$set(this.frmData, 'wem_acco_emp_nm', returnData[0].emp_nm)
        this.$set(this.frmData, 'wem_acco_emp_id', returnData[0].emp_no)
      }
    },
    popClose2(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'acrq_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'acrq_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    popClose3(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.popupNm === 'acrq') {
        this.$set(this.frmData, 'acrq_atfl_no', returnData.atfile_no)
        this.$set(this.frmData, 'acrq_atfl_cnt', returnData.file_cnt)
      } else if (this.popupNm === 'acco') {
        this.$set(this.frmData, 'acco_atfl_no', returnData.atfile_no)
        this.$set(this.frmData, 'acco_atfl_cnt', returnData.file_cnt)
      }
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `opem-opemactnrsltmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
  },
}
</script>
<style></style>
