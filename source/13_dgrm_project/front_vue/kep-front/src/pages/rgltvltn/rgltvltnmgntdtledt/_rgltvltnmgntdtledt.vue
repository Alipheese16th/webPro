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
                <!--점검구분-->
                <div class="label_tit essn">{{ $t('LB00000669') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_chk_cl_cd"
                  v-model="frmData.rglt_chk_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000012"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검기관-->
                <div class="label_tit essn">{{ $t('LB00000921') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="rglt_chk_orga_nm"
                  v-model="frmData.rglt_chk_orga_nm"
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
                <!--관련법규-->
                <div class="label_tit essn">{{ $t('LB00000916') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rel_rglt_cd"
                  v-model="frmData.rel_rglt_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000013"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit essn">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_vltn_bsns_cd"
                  v-model="frmData.rglt_vltn_bsns_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000014"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검일자-->
                <div class="label_tit essn">{{ $t('LB00000917') }}</div>
              </v-col>
              <v-col cols="3">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="rglt_vltn_chk_dt"
                      v-model="frmData.rglt_vltn_chk_dt"
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
                  <v-date-picker v-model="frmData.rglt_vltn_chk_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit essn">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="rglt_vltn_wkpl_loc_nm"
                  v-model="frmData.rglt_vltn_wkpl_loc_nm"
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
                <!--주관부서-->
                <div class="label_tit essn">{{ $t('LB00000924') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="kor_rglt_vltn_org_nm"
                  v-model="frmData.kor_rglt_vltn_org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen3"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--담당자-->
                <div class="label_tit essn">{{ $t('LB00000101') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="rglt_vltn_emp_nm"
                  v-model="frmData.rglt_vltn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검내용-->
                <div class="label_tit essn">{{ $t('LB00000901') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="admn_dspt_txt"
                  v-model="frmData.admn_dspt_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  name="input-7-4"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반여부-->
                <div class="label_tit essn">{{ $t('LB00000925') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_vltn_yn"
                  v-model="frmData.rglt_vltn_yn"
                  label="SELECT"
                  :items="vltnYn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위반구분-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000918') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_dspt_cl_cd"
                  v-model="frmData.rglt_dspt_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000015"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="frmData.rglt_vltn_yn === 'N' || frmData.rglt_vltn_yn === undefined"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--위반금액/건수-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000926') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-text-field
                  ref="fine_amt"
                  v-model="frmData.fine_amt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  :disabled="frmData.rglt_vltn_yn === 'N' || frmData.rglt_vltn_yn === undefined"
                  maxlength="12"
                  @keyup="inputComma"
                ></v-text-field>
                <v-text-field
                  ref="rglt_vltn_cnt"
                  v-model="frmData.rglt_vltn_cnt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  :disabled="frmData.rglt_vltn_yn === 'N' || frmData.rglt_vltn_yn === undefined"
                  maxlength="10"
                  @keyup="inputComma2"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반명-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000920') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="rglt_vltn_ttl"
                  v-model="frmData.rglt_vltn_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  :disabled="frmData.rglt_vltn_yn === 'N' || frmData.rglt_vltn_yn === undefined"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위반내용-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000927') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="rglt_vltn_txt"
                  v-model="frmData.rglt_vltn_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  name="input-7-4"
                  :disabled="frmData.rglt_vltn_yn === 'N' || frmData.rglt_vltn_yn === undefined"
                ></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--조치결과-->
                <div class="label_tit">{{ $t('LB00000685') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="rglt_vltn_actn_rslt_txt"
                  v-model="frmData.rglt_vltn_actn_rslt_txt"
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
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" @click="complete">{{ $t('LB00000763') }}</v-btn>
          <!--삭제-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!deleteModify" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="rgltedt1" mkey2="rgltedt2" @close="popClose" />
    <rgnslct ref="pop2" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="rgltedt" @close="popClose2" />
    <deptslct ref="pop3" mkey="rgltedt" @close="popClose3" />
  </div>
</template>

<script>
import userslct from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
import deptslct from '../../common/deptslct'
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: '대외점검 관리',
    key(route) {
      return `/rgltvltn/${route.params.catalog}`
    },
  },
  components: {
    userslct,
    rgnslct,
    deptslct,
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
      puse_yn: '',
      basic: 'label_tit',
      vltnYn: ['Y', 'N'],
      menu1: false,
      deleteModify: false,
      schData: {},
      frmData: {},
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      isAdminGrp: false,
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
    this.user = this.$store.getters['auth/getUser']
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = this.user.cmpn_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res

        if (this.$route.params.rgltvltnmgntdtledt !== null && this.$route.params.rgltvltnmgntdtledt !== undefined) {
          this.frmData.rglt_vltn_id = this.$route.params.rgltvltnmgntdtledt
          this.schData.rglt_vltn_id = this.$route.params.rgltvltnmgntdtledt
        } else {
          this.frmData.rglt_vltn_id = ''
          this.schData.rglt_vltn_id = ''
          this.frmData.wkpl_id = this.user.wkpl_id
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.rglt_vltn_id = this.schData.rglt_vltn_id
        }

        // ID 존재할 경우
        if (this.frmData.rglt_vltn_id) {
          this.modify = true
          this.select()
          this.inputComma()
          this.inputComma2()
        } else {
          const user = this.$store.getters['auth/getUser']
          this.frmData.rglt_vltn_chk_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')
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
        // this.frmData.rglt_vltn_wkpl_loc_nm = res.rglt_vltn_wkpl_loc_nm
        this.frmData.rglt_vltn_org_cd = res.rglt_vltn_org_cd
        // this.frmData.kor_rglt_vltn_org_nm = res.kor_rglt_vltn_org_nm
        this.frmData.engl_rglt_vltn_org_nm = res.engl_rglt_vltn_org_nm
        this.frmData.rglt_vltn_emp_id = res.rglt_vltn_emp_id
        this.frmData.admn_dspt_txt = res.admn_dspt_txt
        this.frmData.rglt_vltn_ttl = res.rglt_vltn_ttl
        this.frmData.rglt_chk_orga_nm = res.rglt_chk_orga_nm
        this.frmData.rglt_vltn_txt = res.rglt_vltn_txt
        this.frmData.rglt_vltn_actn_rslt_txt = res.rglt_vltn_actn_rslt_txt
        this.frmData.rglt_vltn_yn = res.rglt_vltn_yn
        this.frmData.rglt_vltn_prst_cd = res.rglt_vltn_prst_cd
        this.frmData.rglt_vltn_atfl_no = res.rglt_vltn_atfl_no
        this.frmData.crt_emp_no = res.crt_emp_no
        // this.frmData.rglt_vltn_cnt = res.rglt_vltn_cnt
        // this.frmData.fine_amt = res.fine_amt
        // this.frmData.rglt_vltn_emp_nm = res.rglt_vltn_emp_nm
        this.$set(this.frmData, 'fine_amt', res.fine_amt)
        this.$set(this.frmData, 'rglt_vltn_cnt', res.rglt_vltn_cnt)
        this.$set(this.frmData, 'rglt_vltn_wkpl_loc_nm', res.rglt_vltn_wkpl_loc_nm)
        this.$set(this.frmData, 'kor_rglt_vltn_org_nm', res.kor_rglt_vltn_org_nm)
        this.$set(this.frmData, 'rglt_vltn_emp_nm', res.rglt_vltn_emp_nm)

        if (this.frmData.rglt_vltn_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.rglt_vltn_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, true)
            }
          })
        }

        const usrinfo = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if ((this.frmData.rglt_vltn_prst_cd === '10' && this.frmData.crt_emp_no === this.user.emp_no) || this.isAdminGrp) {
          this.deleteModify = true
        }

        this.frmData.ckFlag = false
        this.frmData.ckFile = false
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

      await this.saveFiles(true)
      this.frmData.rglt_vltn_prst_cd = '10'

      await this.$axios.post('/api/v1/she/sfty/rgltvltn', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.rglt_vltn_id = res.data
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

      if (this.frmData.ckFile) {
        await this.saveFiles(true)
      }

      await this.$axios.put('/api/v1/she/sfty/rgltvltn', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.rglt_vltn_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async complete() {
      if (!this.validate()) {
        return
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res) {
        return
      }

      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          // this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          if (this.frmData.ckFile) {
            await this.saveFiles(true)
          }
          await this.$axios.put('/api/v1/she/sfty/rgltvltn', this.frmData).then((res) => {
            if (res.data !== 'Fail') {
              this.frmData.rglt_vltn_id = res.data
              this.schData.rglt_vltn_id = res.data
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      } else {
        await this.saveFiles(true)
        this.frmData.rglt_vltn_prst_cd = '10'

        await this.$axios.post('/api/v1/she/sfty/rgltvltn', this.frmData).then((res) => {
          if (res.data !== 'Fail') {
            this.frmData.rglt_vltn_id = res.data
            this.schData.rglt_vltn_id = res.data
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }

      this.modify = false

      this.frmData.type = 'complete'

      this.$axios.put('/api/v1/she/sfty/rgltvltn', this.frmData).then((res) => {
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
          const url = config.default.approvalUrl11 + aprvno + empno
          window.open(url, 'rgltvltn', options)
          // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `rgltvltn-rgltvltnmgntdtllup-rgltvltnmgntdtllup`,
            params: {
              schData: this.schData,
              rgltvltnmgntdtllup: this.schData.rglt_vltn_id,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async delInfo() {
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.put(`/api/v1/she/sfty/rgltvltn/${this.frmData.rglt_vltn_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
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
      if (!this.frmData.rglt_chk_cl_cd) {
        this.$toast.show(this.$t('MS00000398'), { className: 'toast_error' }) // 점검구분을 선택해주세요.
        this.$refs.rglt_chk_cl_cd.focus()
        return false
      }
      if (!this.frmData.rglt_chk_orga_nm) {
        this.$toast.show(this.$t('MS00000399'), { className: 'toast_error' }) // 점검기관을 입력해주세요.
        this.$refs.rglt_chk_orga_nm.focus()
        return false
      }
      if (this.frmData.rglt_chk_orga_nm.length > 100) {
        this.$toast.show(this.$t('MS00000400'), { className: 'toast_error' }) // 점검기관을 100자 이하로 입력해주세요.
        this.$refs.rglt_chk_orga_nm.focus()
        return false
      }
      if (!this.frmData.rel_rglt_cd) {
        this.$toast.show(this.$t('MS00000401'), { className: 'toast_error' }) // 관련법규를 선택해주세요.
        this.$refs.rel_rglt_cd.focus()
        return false
      }
      if (!this.frmData.rglt_vltn_bsns_cd) {
        this.$toast.show(this.$t('MS00000402'), { className: 'toast_error' }) // 업무구분을 선택해주세요.
        this.$refs.rglt_vltn_bsns_cd.focus()
        return false
      }
      if (!this.frmData.rglt_vltn_wkpl_loc_nm) {
        this.$toast.show(this.$t('MS00000403'), { className: 'toast_error' }) // 위치를 입력해주세요.
        this.$refs.rglt_vltn_wkpl_loc_nm.focus()
        return false
      }
      if (!this.frmData.kor_rglt_vltn_org_nm) {
        this.$toast.show(this.$t('MS00000404'), { className: 'toast_error' }) // 주관부서를 입력해주세요.
        this.$refs.kor_rglt_vltn_org_nm.focus()
        return false
      }
      if (!this.frmData.rglt_vltn_emp_nm) {
        this.$toast.show(this.$t('MS00000405'), { className: 'toast_error' }) // 담당자를 입력해주세요.
        this.$refs.rglt_vltn_emp_nm.focus()
        return false
      }
      if (!this.frmData.admn_dspt_txt) {
        this.$toast.show(this.$t('MS00000406'), { className: 'toast_error' }) // 점검내용을 입력해주세요.
        this.$refs.admn_dspt_txt.focus()
        return false
      }
      if (this.frmData.admn_dspt_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000407'), { className: 'toast_error' }) // 점검내용을 1000자 이하로 입력해주세요.
        this.$refs.admn_dspt_txt.focus()
        return false
      }
      if (!this.frmData.rglt_vltn_yn) {
        this.$toast.show(this.$t('MS00000408'), { className: 'toast_error' }) // 위반여부를 선택해주세요.
        this.$refs.rglt_vltn_yn.focus()
        return false
      }
      if (this.frmData.rglt_vltn_yn === 'Y') {
        if (!this.frmData.rglt_dspt_cl_cd) {
          this.$toast.show(this.$t('MS00000409'), { className: 'toast_error' }) // 위반구분을 선택해주세요.
          this.$refs.rglt_dspt_cl_cd.focus()
          return false
        }
        if (!this.frmData.fine_amt) {
          this.$toast.show(this.$t('MS00000410'), { className: 'toast_error' }) // 위반금액을 입력해주세요.
          this.$refs.fine_amt.focus()
          return false
        }
        if (!this.frmData.rglt_vltn_cnt) {
          this.$toast.show(this.$t('MS00000411'), { className: 'toast_error' }) // 위반건수를 입력해주세요.
          this.$refs.rglt_vltn_cnt.focus()
          return false
        }
        if (!this.frmData.rglt_vltn_ttl) {
          this.$toast.show(this.$t('MS00000412'), { className: 'toast_error' }) // 위반명을 입력해주세요.
          this.$refs.rglt_vltn_ttl.focus()
          return false
        }
        if (this.frmData.rglt_vltn_ttl.length > 100) {
          this.$toast.show(this.$t('MS00000413'), { className: 'toast_error' }) // 위반명을 100자 이하로 입력해주세요.
          this.$refs.rglt_vltn_ttl.focus()
          return false
        }
        if (!this.frmData.rglt_vltn_txt) {
          this.$toast.show(this.$t('MS00000414'), { className: 'toast_error' }) // 위반내용을 입력해주세요.
          this.$refs.rglt_vltn_txt.focus()
          return false
        }
        if (this.frmData.rglt_vltn_txt.length > 1000) {
          this.$toast.show(this.$t('MS00000415'), { className: 'toast_error' }) // 위반내용을 1000자 이하로 입력해주세요.
          this.$refs.rglt_vltn_txt.focus()
          return false
        }
      }
      if (!this.isEmpty(this.frmData.rglt_vltn_actn_rslt_txt) && this.frmData.rglt_vltn_actn_rslt_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000416'), { className: 'toast_error' }) // 조치결과는 1000자를 초과할 수 없습니다.
        this.$refs.rglt_vltn_actn_rslt_txt.focus()
        return false
      }
      return true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popOpen2() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open()
    },
    popOpen3() {
      this.$refs.pop3.open()
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'rglt_vltn_emp_nm', returnData[0].emp_nm)
        this.$set(this.frmData, 'rglt_vltn_emp_id', returnData[0].emp_no)
      }
    },
    popClose2(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'rglt_vltn_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'rglt_vltn_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    popClose3(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'kor_rglt_vltn_org_nm', returnData[0].dept_nm)
        this.$set(this.frmData, 'rglt_vltn_org_cd', returnData[0].dept_cd)
      }
    },
    inputComma() {
      let amt = this.frmData.fine_amt.replace(/[^0-9]/g, '')
      if (amt.length > 12) {
        amt = amt.substring(0, 12)
        this.$toast.show(this.$t('MS00000417'), { className: 'toast_error' }) // 위반금액이 너무 큽니다.
      }
      this.frmData.fine_amt = Number(amt).toLocaleString()
    },
    inputComma2() {
      let cnt = this.frmData.rglt_vltn_cnt.replace(/[^0-9]/g, '')
      if (cnt.length > 10) {
        cnt = cnt.substring(0, 10)
        this.$toast.show(this.$t('MS00000598'), { className: 'toast_error' }) // 위반건수가 너무 많습니다.
      }
      this.frmData.rglt_vltn_cnt = Number(cnt).toLocaleString()
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    codChg(data) {
      if (data === 'Y') {
        this.puse_yn = 'essn'
      } else {
        this.frmData.rglt_dspt_cl_cd = ''
        this.frmData.fine_amt = ''
        this.frmData.rglt_vltn_cnt = ''
        this.frmData.rglt_vltn_ttl = ''
        this.frmData.rglt_vltn_txt = ''
        this.puse_yn = ''
      }
    },
    goList() {
      this.$router.push({
        name: `rgltvltn-rgltvltnmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
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
    async saveFiles(mode) {
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data) {
          this.frmData.rglt_vltn_atfl_no = data
          // this.$axios.$get(`/api/v1/common/file/${this.frmData.rglt_vltn_atfl_no}`).then((data) => {
          //   console.log('files : ', data) // eslint-disable-line no-console
          //   if (data.length > 0) {
          //     this.setFiles(data, mode)
          //   }
          // })
        }
      })
    },
  },
}
</script>
<style></style>
