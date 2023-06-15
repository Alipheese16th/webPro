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
                <!--점검년도-->
                <div class="label_tit essn">{{ $t('LB00001105') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      v-model="frmData.labs_sft_chk_yrmn"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    ref="picker"
                    v-model="frmData.labs_sft_chk_yrmn"
                    reactive
                    show-current
                    min="2010"
                    max="2050"
                    no-title
                    @click:year="saveDate(frmData.labs_sft_chk_yrmn)"
                  ></v-date-picker>
                </v-menu>
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
                <!--실시 기관-->
                <div class="label_tit essn">{{ $t('LB00001138') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="labs_sft_chk_orga_nm"
                  v-model="frmData.labs_sft_chk_orga_nm"
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
                <div class="label_tit essn">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="11">
                <v-text-field
                  ref="labs_sft_chk_nm"
                  v-model="frmData.labs_sft_chk_nm"
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
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검내용-->
                <div class="label_tit essn">{{ $t('LB00000642') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  ref="labs_sft_chk_txt"
                  v-model="frmData.labs_sft_chk_txt"
                  outlined
                  single-line
                  :hide-details="true"
                  height="100"
                  class="mt-2"
                  dense
                  :placeholder="$t('LB00000033')"
                  maxlength="2000"
                ></v-textarea>
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
            <div class="group_padd"></div>
          </div>
        </v-col>
      </v-row>
    </div>
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

let yearVal = new Date()

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
      quillChk: false,
      saveModify: false,
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        ST00000002p: [{ cd: '', cd_nm: '' }],
      },
      menu: false,
      tmpSchData: {},
      schData: {},
      frmData: {
        ckFlag: true,
        wkpl_id: '',
        labs_sft_chk_id: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_orga_nm: '',
        labs_sft_chk_nm: '',
        labs_sft_chk_txt: '',
        labs_sft_chk_atfl_no: '',
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
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
  watch: {
    menu(val) {
      val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  async beforePageLeave(tab, type) {
    if (this.frmData.ckFlag) {
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
  created() {
    this.user = this.$store.getters['auth/getUser']

    if (this.$route.params.labssaftychkmgntdtledt !== null && this.$route.params.labssaftychkmgntdtledt !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.labs_sft_chk_id = this.$route.params.labssaftychkmgntdtledt
      this.wkplId = this.$route.params.wkpl_id
      this.labsSftChkId = this.$route.params.labssaftychkmgntdtledt
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.labs_sft_chk_id = this.$route.params.labssaftychkmgntdtledt
      this.saveModify = false
    } else {
      this.frmData.wkpl_id = this.user.wkpl_id
      this.frmData.labs_sft_chk_id = ''
      this.wkplId = ''
      this.labsSftChkId = ''
      this.schData.wkpl_id = ''
      this.schData.labs_sft_chk_id = ''
      this.saveModify = true
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        labs_sft_chk_yrmn: '',
      }
    }
    yearVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.labs_sft_chk_yrmn = this.get_date_str_gubun(yearVal, '-') + ''
  },
  mounted() {
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
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempWkpl = []
      res.ST00000002.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000002p = tempWkpl
      this.codes.ST00000002p.unshift({ value: '', label: 'SELECT' })

      if (this.frmData.labs_sft_chk_id !== '') {
        this.select()
      }
    })
  },
  methods: {
    get_date_str_gubun(date) {
      const sYear = date.getFullYear()
      return sYear
    },
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
        this.$toast.show(this.$t('MS00000553'), { className: 'toast_error' }) // 사업장명을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_yrmn)) {
        this.$toast.show(this.$t('MS00000578'), { className: 'toast_error' }) // 점검년도를 입력해주세요.
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_orga_nm)) {
        this.$toast.show(this.$t('MS00000579'), { className: 'toast_error' }) // 실시기관을 입력해주세요.
        this.$refs.labs_sft_chk_orga_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_nm)) {
        this.$toast.show(this.$t('MS00000275'), { className: 'toast_error' }) // 점검명을 입력해주세요.
        this.$refs.labs_sft_chk_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.labs_sft_chk_nm) && this.frmData.labs_sft_chk_nm.length > 100) {
        this.$toast.show(this.$t('MS00000555'), { className: 'toast_error' }) // 점검명은 100자 이하로 입력해주세요.
        this.$refs.labs_sft_chk_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_txt)) {
        this.$toast.show(this.$t('MS00000406'), { className: 'toast_error' }) // 점검내용을 입력해주세요.
        this.$refs.labs_sft_chk_txt.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.labs_sft_chk_txt) && this.frmData.labs_sft_chk_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000407'), { className: 'toast_error' }) // 점검내용은 1000자 이하로 입력해주세요.
        this.$refs.labs_sft_chk_txt.focus()
        return false
      }
      return true
    },
    async save() {
      // 파일 결과 함수
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.labs_sft_chk_atfl_no = data
        }
      })

      if (JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.labs_sft_chk_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.labs_sft_chk_atfl_no}`).then((data) => {
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

      temp.wkpl_id = this.frmData.wkpl_id
      temp.labs_sft_chk_id = this.frmData.labs_sft_chk_id
      temp.labs_sft_chk_yrmn = this.frmData.labs_sft_chk_yrmn
      temp.labs_sft_chk_orga_nm = this.frmData.labs_sft_chk_orga_nm
      temp.labs_sft_chk_nm = this.frmData.labs_sft_chk_nm
      temp.labs_sft_chk_txt = this.frmData.labs_sft_chk_txt
      temp.labs_sft_chk_atfl_no = this.frmData.labs_sft_chk_atfl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/she/sftchk/labs-safty-chk-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.wkpl_id = res.data.key1
          this.schData.labs_sft_chk_id = res.data.key2
          this.select()
          this.saveModify = false
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.frmData.labs_sft_chk_yrmn = dd
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
