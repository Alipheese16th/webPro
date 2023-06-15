<template>
  <div>
    <div class="location">
      <!--Home > 위험성평가 > 위험성평가 등록/조회-->
      <span>Home > {{ $t('위험성평가') }} > {{ $t('위험성평가 등록') }}</span>
    </div>
    <div class="work_title">
      <!-- 위험성평가 등록 -->
      <h2>{{ $t('위험성평가 등록') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보작성 -->
        <h3>{{ $t('기본정보작성') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit essn">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-select
                  ref="cmpy_cd"
                  v-model="frmData.cmpy_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
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
                  @change="chgLevel1(true)"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--관리감독자-->
                <div class="label_tit essn">{{ $t('관리감독자') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="dgsn_apal_supe_emp_nm"
                  v-model="frmData.dgsn_apal_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  readonly
                  append-icon="fas fa-search"
                  clearable
                  @click:append="popOpen"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--실시일자-->
                <div class="label_tit essn">{{ $t('실시일자') }}</div>
              </v-col>
              <v-col cols="3">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="mtng_dt"
                      v-model="frmData.dgsn_apal_bgn_dt"
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
                  <v-date-picker v-model="frmData.dgsn_apal_bgn_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--위험성평가-->
                <div class="label_tit essn">{{ $t('위험성평가') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dgsn_apal_type_cd"
                  v-model="frmData.dgsn_apal_type_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000078"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="typeChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--수시평가종류-->
                <div class="label_tit">{{ $t('수시평가종류') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="dgsn_spot_apal_type_cd"
                  v-model="frmData.dgsn_spot_apal_type_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000079"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  :disabled="disableFlag"
                  :filled="disableFlag"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--공정선택(대분류)-->
                <div class="label_tit essn" style="line-height: 1; min-height: auto">{{ $t('공정선택') }}</div>
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('(대분류)') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="lrcl_dngp_id"
                  v-model="frmData.lrcl_dngp_id"
                  label="SELECT"
                  item-text="dngp_nm"
                  item-value="dngp_id"
                  :items="levels.LEVEL1"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="selectDisplay"
                  height="30"
                  @change="chgLevel2(true)"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--공정선택(공정명)-->
                <div class="label_tit essn" style="line-height: 1; min-height: auto">{{ $t('공정선택') }}</div>
                <div class="label_tit" style="line-height: 1; min-height: auto">{{ $t('(공정명)') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mdcl_dngp_id"
                  v-model="frmData.mdcl_dngp_id"
                  label="SELECT"
                  item-text="dngp_nm"
                  item-value="dngp_id"
                  :items="levels.LEVEL2"
                  outlined
                  :disabled="selectDisplay"
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--삭제-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!deleteModify" @click="delInfo">{{ $t('LB00000046') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save(false)">{{ $t('LB00000045') }}</v-btn>
          <!--위험성평가 시작-->
          <v-btn depressed width="120" color="success" height="40" @click="save(true)">{{ $t('위험성평가 시작') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="riskevalbasic1" mkey2="riskevalbasic2" @close="popclose" />
    <filePopup ref="filepop" mkey1="riskevalbasic1" :edit-chk="false" />
  </div>
</template>

<script>
import popup from '../../common/userslct'
import filePopup from '../../common/atflgrid'

export default {
  meta: {
    title: '위험성평가 등록/조회',
    key(route) {
      return `/riskeval/${route.params.catalog}`
    },
  },
  components: {
    popup,
    filePopup,
  },
  data() {
    return {
      selectDisplay: true,
      modify: false,
      codeid: ['CO00000009', 'SH00000078', 'SH00000079'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }],
        SH00000078: [{ cd: '', cd_nm: '' }],
        SH00000079: [{ cd: '', cd_nm: '' }],
        SH00000079_copy: [{ cd: '', cd_nm: '' }],
      },
      menu1: false,
      schData: {},
      frmData: {},
      tempData: {},
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      levels: {
        LEVEL1: [{ dngp_id: '', dngp_nm: '' }],
        LEVEL2: [{ dngp_id: '', dngp_nm: '' }],
      },
      dngpData: [],
      deleteModify: false,
      isAdminGrp: false,
      showFlag: false,
      disableFlag: true,
    }
  },
  computed: {
    routeTab() {
      return this.$t('위험성평가 등록/조회') // 위험성평가 등록/조회
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
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
    if (!this.$route.params.riskevalbasicedt) {
      this.selectDisplay = false
    }
    // 이전 검색 결과 저장
    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    }
    this.user = this.$store.getters['auth/getUser']
    // 시스템그룹 사용자(RZZZZ) + 환경안전부서(R0005)
    this.user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005') {
        this.isAdminGrp = true
      }
    })
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        const cmpy = this.user.cmpn_cd
        this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
          return n.up_cd === cmpy
        })
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000078.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000079.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000079_copy = this.codes.SH00000079

        if (this.$route.params.riskevalbasicedt) {
          this.frmData.dgsn_apal_id = this.$route.params.riskevalbasicedt
          this.select()
        } else {
          this.frmData.cmpy_cd = this.user.cmpn_cd
          this.frmData.wkpl_id = this.user.wkpl_id
          this.frmData.dgsn_apal_bgn_dt = this.getTzDate(this.user.time_zone, '-')
          this.codes.SH00000079 = [{ cd: '', cd_nm: 'SELECT' }]
          this.getDngpData(true)
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    async getDngpData(isNew) {
      const obj = {}
      obj.usg_yn = 'Y'
      this.dngpData = await this.$axios.$get(`/api/v1/she/riskeval/wkpl-dngp-list`, { params: obj })

      if (isNew) {
        this.chgLevel1(isNew)
      } else {
        this.chgLevel1()
        this.chgLevel2()
      }
    },
    async select() {
      this.frmData = await this.$axios.$get(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`)
      if (this.frmData.mode !== 'U' && !this.isAdminGrp) {
        const param = {}
        param.schData = this.schData
        param.riskevalbasiclup = this.frmData.dgsn_apal_id
        this.$router.push({
          name: 'riskeval-riskevalbasiclup-riskevalbasiclup',
          params: param,
        })
      } else {
        this.modify = true
        this.getDngpData(false)

        if ((this.frmData.dgsn_apal_prst_cd === 'E01' && this.frmData.crt_emp_no === this.user.emp_no) || this.isAdminGrp) {
          this.deleteModify = true
        }

        if (this.frmData.dgsn_apal_type_cd === 'ETP03') {
          this.disableFlag = false
        } else {
          this.disableFlag = true
        }

        this.tempData = JSON.parse(JSON.stringify(this.frmData))
      }
    },
    save(isStart) {
      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          if (isStart) {
            this.start()
          } else {
            this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
          }
        } else {
          this.update(isStart)
        }
      } else {
        this.insert(isStart)
      }
    },
    async insert(isStart) {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.$axios.post('/api/v1/she/riskeval/basic', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.dgsn_apal_id = res.data
          this.showFlag = false
          if (isStart) {
            this.start()
          } else {
            this.select()
            this.showFlag = false
            this.frmData.new_dngp_nm = ''
          }
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async update(isStart) {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.$axios.put('/api/v1/she/riskeval/basic', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.dgsn_apal_id = res.data
          this.showFlag = false
          if (isStart) {
            this.frmData.ckFile = false
            this.tempData = JSON.parse(JSON.stringify(this.frmData))
            this.start()
          } else {
            this.select()
          }
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    start() {
      if (this.frmData.dgsn_apal_id) {
        this.$router.push({
          name: `riskeval-riskevaltgtdtlwkedt-riskevaltgtdtlwkedt`,
          params: {
            schData: this.schData,
            riskevaltgtdtlwkedt: this.frmData.dgsn_apal_id,
          },
        })
      }
    },
    validate() {
      // 기본정보
      if (!this.frmData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (!this.frmData.dgsn_apal_supe_emp_nm) {
        this.$toast.show(this.$t('관리감독자를 입력해주세요.'), { className: 'toast_error' }) // 관리감독자를 입력해주세요.
        this.$refs.dgsn_apal_supe_emp_nm.focus()
        return false
      }
      if (!this.frmData.dgsn_apal_type_cd) {
        this.$toast.show(this.$t('위험성평가를 선택해주세요.'), { className: 'toast_error' }) // 위험성평가를 선택해주세요.
        this.$refs.dgsn_apal_type_cd.focus()
        return false
      }
      if (this.frmData.dgsn_apal_type_cd === 'ETP03' && !this.frmData.dgsn_spot_apal_type_cd) {
        this.$toast.show(this.$t('수시평가종류를 선택해주세요.'), { className: 'toast_error' }) // 수시평가종류를 선택해주세요.
        this.$refs.dgsn_spot_apal_type_cd.focus()
        return false
      }
      if (!this.frmData.lrcl_dngp_id) {
        this.$toast.show(this.$t('대분류를 선택해주세요.'), { className: 'toast_error' }) // 대분류를 선택해주세요.
        this.$refs.lrcl_dngp_id.focus()
        return false
      }
      if (!this.frmData.mdcl_dngp_id) {
        this.$toast.show(this.$t('공정명을 선택해주세요.'), { className: 'toast_error' }) // 공정명을 선택해주세요.
        this.$refs.mdcl_dngp_id.focus()
        return false
      }
      return true
    },
    async delInfo() {
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      this.$axios.delete(`/api/v1/she/riskeval/basic/${this.frmData.dgsn_apal_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData && returnData.length > 0) {
        this.$set(this.frmData, 'dgsn_apal_supe_emp_nm', returnData[0].dept_nm + ' ' + returnData[0].emp_nm)
        this.$set(this.frmData, 'dgsn_apal_supe_emp_id', returnData[0].emp_no)
      }
    },
    codChg(data) {
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      this.frmData.wkpl_id = ''
    },
    typeChg(data) {
      if (data === 'ETP03') {
        this.codes.SH00000079 = this.codes.SH00000079_copy
        this.disableFlag = false
      } else {
        this.frmData.dgsn_spot_apal_type_cd = ''
        this.codes.SH00000079 = [{ cd: '', cd_nm: 'SELECT' }]
        this.disableFlag = true
      }
    },
    chgLevel1(isNew) {
      const wkplId = this.frmData.wkpl_id
      this.levels.LEVEL1 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.up_dngp_id === ''
      })
      this.levels.LEVEL1.unshift({ dngp_id: '', dngp_nm: 'SELECT', up_dngp_id: '' })
      if (isNew) {
        this.frmData.lrcl_dngp_id = ''
        this.showFlag = false
        this.chgLevel2(isNew)
      }
    },
    chgLevel2(isNew) {
      const wkplId = this.frmData.wkpl_id
      const upId = this.frmData.lrcl_dngp_id
      this.levels.LEVEL2 = this.dngpData.filter(function (n) {
        return n.wkpl_id === wkplId && n.level === 2 && n.up_dngp_id === upId
      })
      this.levels.LEVEL2.unshift({ dngp_id: '', dngp_nm: 'SELECT', up_dngp_id: '' })
      if (isNew) {
        this.frmData.mdcl_dngp_id = ''
        this.showFlag = false
      }
    },
    goList() {
      this.$router.push({
        name: `riskeval-riskevallist`,
        params: {
          schData: this.schData,
        },
      })
    },
  },
}
</script>
<style></style>
