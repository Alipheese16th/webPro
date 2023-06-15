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
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
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
                <!--검사기관-->
                <div class="label_tit essn">{{ $t('LB00001031') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_sfip_orga_nm"
                  v-model="frmData.hrm_sfip_orga_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
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
                <div class="label_tit essn">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="hrm_sfip_nm"
                  v-model="frmData.hrm_sfip_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--검사기간-->
                <div class="label_tit essn">{{ $t('LB00001030') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu" :close-on-content-click="clickChk" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="hrm_sfip_bgn_dt_range"
                      v-model="hrmRangeDtChange"
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
                  <v-date-picker v-model="frmData.hrm_sfip_bgn_dt_range" range no-title @click="menu = true"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--검사결과-->
                <div class="label_tit">
                  {{ $t('LB00001029') }}
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <span class="label_info ml-1" style="padding-bottom: 8px; padding-top: 8px" v-bind="attrs" v-on="on"></span>
                    </template>
                    <span>{{ $t('MS00000255') }}</span>
                  </v-tooltip>
                </div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="hrm_sfip_rslt"
                  v-model="frmData.hrm_sfip_rslt"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('MS00000255')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--검사내용-->
                <div class="label_tit essn">{{ $t('LB00001028') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="hrm_sfip_txt"
                  v-model="frmData.hrm_sfip_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  height="100"
                  class="mt-2"
                  dense
                  maxlength="2000"
                ></v-textarea>
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
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FileUpload from '~/components/FileUpload.vue'

let sDateVal = new Date()
let eDateVal = new Date()

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
      wkplModify: false,
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
      },
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        hrm_sfip_no: '',
        hrm_sfip_orga_nm: '',
        hrm_sfip_nm: '',
        hrm_sfip_bgn_dt: '',
        hrm_sfip_bgn_dt_range: [],
        hrm_sfip_end_dt: '',
        hrm_sfip_txt: '',
        hrm_sfip_atfl_no: '',
        hrm_sfip_rslt: '',
      },
      menu: false,
      tmpSchData: {},
      schData: {},
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001032') // 안전검사 결과 관리
    },
    // 검사기간 입력
    hrmRangeDtChange() {
      const date = this.frmData.hrm_sfip_bgn_dt_range
      if (date.length === 2) {
        const orderedDate = date.sort().join(' ~ ')
        this.hrmRangeDtSet()
        return orderedDate
      }

      return ''
    },
    // 검사기간 달력클릭 시
    clickChk() {
      if (this.frmData.hrm_sfip_bgn_dt_range.length === 2) {
        return true
      } else {
        return false
      }
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.sftinsprsltmgntdtledt !== null && this.$route.params.sftinsprsltmgntdtledt !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.hrm_sfip_no = this.$route.params.sftinsprsltmgntdtledt
      this.wkplId = this.$route.params.wkpl_id
      this.hrmSfipNo = this.$route.params.sftinsprsltmgntdtledt
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.hrm_sfip_no = this.$route.params.sftinsprsltmgntdtledt
      this.wkplModify = false
    } else {
      this.frmData.wkpl_id = this.user.wkpl_id
      this.frmData.hrm_sfip_no = ''
      this.wkplId = ''
      this.hrmSfipNo = ''
      this.schData.wkpl_id = ''
      this.schData.hrm_sfip_no = ''
      this.wkplModify = true
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
    eDateVal = this.getTzDateObj(this.user.time_zone)
    sDateVal = this.getTzDateObj(this.user.time_zone)
    sDateVal.setDate(sDateVal.getDate() - 1)
    this.frmData.hrm_sfip_bgn_dt = this.get_date_str_gubun(sDateVal, '-')
    this.frmData.hrm_sfip_end_dt = this.get_date_str_gubun(eDateVal, '-')
    this.frmData.hrm_sfip_bgn_dt_range[0] = this.frmData.hrm_sfip_bgn_dt
    this.frmData.hrm_sfip_bgn_dt_range[1] = this.frmData.hrm_sfip_end_dt
  },
  mounted() {
    // 사업장 조회
    this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
      console.log(res) // eslint-disable-line no-console
      const tempWkpl = []
      res.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm, up_cd: e.up_cd })
      })
      this.FF_00000001 = tempWkpl
      this.F_codes.F_00000001 = tempWkpl
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT' })

      // 사업장 등록&수정하는 화면에서만 필터링 할 수 있게끔...
      const cmpy = this.user.cmpn_cd
      if (this.wkplModify === true) {
        this.codChg(cmpy)
      }

      if (this.frmData.hrm_sfip_no !== '') {
        this.select()
      }
    })
  },
  async beforePageLeave(tab, type) {
    if (!this.wkplModify) {
      if (JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
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
    }
  },
  methods: {
    hrmRangeDtSet() {
      this.frmData.hrm_sfip_bgn_dt = this.frmData.hrm_sfip_bgn_dt_range[0]
      this.frmData.hrm_sfip_end_dt = this.frmData.hrm_sfip_bgn_dt_range[1]
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/hmflmcn/sft-insp-rslt-dtl/${this.schData.hrm_sfip_no}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmData.hrm_sfip_bgn_dt_range = []
        this.frmData.hrm_sfip_bgn_dt_range[0] = this.frmData.hrm_sfip_bgn_dt
        this.frmData.hrm_sfip_bgn_dt_range[1] = this.frmData.hrm_sfip_end_dt
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
        this.changeEditMode(true)
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
    saveValidate() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_sfip_orga_nm)) {
        this.$toast.show(this.$t('MS00000522'), { className: 'toast_error' }) // 검사기관을 입력해주세요.
        this.$refs.hrm_sfip_orga_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_sfip_nm) && this.frmData.hrm_sfip_nm.length > 100) {
        this.$toast.show(this.$t('MS00000521'), { className: 'toast_error' }) // 검사기관은 100자 이하로 입력해주세요.
        this.$refs.hrm_sfip_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_sfip_nm)) {
        this.$toast.show(this.$t('MS00000291'), { className: 'toast_error' }) // 제목을 입력해주세요.
        this.$refs.hrm_sfip_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_sfip_nm) && this.frmData.hrm_sfip_nm.length > 100) {
        this.$toast.show(this.$t('MS00000520'), { className: 'toast_error' }) // 제목은 100자 이하로 입력해주세요.
        this.$refs.hrm_sfip_nm.focus()
        return false
      }
      if (this.frmData.hrm_sfip_bgn_dt_range.length !== 2) {
        this.$toast.show(this.$t('MS00000670'), { className: 'toast_error' }) // 검사기간을 입력해주세요.
        this.$refs.hrm_sfip_bgn_dt_range.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_sfip_txt)) {
        this.$toast.show(this.$t('MS00000517'), { className: 'toast_error' }) // 검사내용을 입력해주세요.
        this.$refs.hrm_sfip_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_sfip_txt) && this.frmData.hrm_sfip_txt.length > 2000) {
        this.$toast.show(this.$t('MS00000516'), { className: 'toast_error' }) // 검사내용은 2000자 이하로 입력해주세요.
        this.$refs.hrm_sfip_txt.focus()
        return false
      }
      return true
    },
    async save() {
      // 파일 결과 함수
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.hrm_sfip_atfl_no = data
        }
      })

      if (JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.hrm_sfip_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.hrm_sfip_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      const infoRow = []
      const temp = {}

      let hrmSfipBgDtVal = this.frmData.hrm_sfip_bgn_dt
      if (!this.isEmpty(this.frmData.hrm_sfip_bgn_dt)) {
        hrmSfipBgDtVal = hrmSfipBgDtVal.replace(/-/gi, '')
      }

      let hrmSfipEnDtVal = this.frmData.hrm_sfip_end_dt
      if (!this.isEmpty(this.frmData.hrm_sfip_end_dt)) {
        hrmSfipEnDtVal = hrmSfipEnDtVal.replace(/-/gi, '')
      }
      temp.wkpl_id = this.frmData.wkpl_id
      temp.hrm_sfip_no = this.frmData.hrm_sfip_no
      temp.hrm_sfip_orga_nm = this.frmData.hrm_sfip_orga_nm
      temp.hrm_sfip_nm = this.frmData.hrm_sfip_nm
      temp.hrm_sfip_bgn_dt = hrmSfipBgDtVal
      temp.hrm_sfip_end_dt = hrmSfipEnDtVal
      temp.hrm_sfip_txt = this.frmData.hrm_sfip_txt
      temp.hrm_sfip_atfl_no = this.frmData.hrm_sfip_atfl_no
      temp.hrm_sfip_rslt = this.frmData.hrm_sfip_rslt
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/she/hmflmcn/sft-insp-rslt-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.wkpl_id = res.data.key1
          this.schData.hrm_sfip_no = res.data.key2
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT', up_cd: '' })
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
