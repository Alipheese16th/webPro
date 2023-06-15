<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업 시작 전 점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00000737') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업 시작 전 점검 대상 관리-->
      <h2>{{ $t('LB00000737') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="schData.cmpy_cd"
                  label="ALL"
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
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="schData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검 대상명-->
                <div class="label_tit">{{ $t('LB00000667') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_tgt_nm"
                  v-model="schData.oprb_chk_tgt_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="selectOprbChkList"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관리 감독자-->
                <div class="label_tit">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_supe_emp_nm"
                  v-model="schData.oprb_chk_supe_emp_nm"
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
              <v-col cols="1">
                <!--점검위치-->
                <div class="label_tit">{{ $t('LB00000739') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_wkpl_loc_nm"
                  v-model="schData.oprb_chk_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popLocOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="usg_yn"
                  v-model="schData.usg_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="usg_yn_cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="selectOprbChkList">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--작업 시작 전 점검 대상 목록-->
        <h3>{{ $t('LB00000737') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--QR출력-->
          <v-btn color="primary" height="30" @click="getReport()">{{ $t('LB00000747') }}</v-btn>
          <!--등록-->
          <v-btn color="primary" height="30" @click="submit()">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <popup ref="pop" mkey1="oprnbgnbfchktgtmgntlist1" mkey2="oprnbgnbfchktgtmgntlist2" @close="popClose" />
      <rgnslct ref="pop2" mkey="oprnbgnbfchktgtmgntlist1" :iswkpl="false" :wkplid="schData.wkpl_id" @close="popLocClose" />
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import rgnslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '작업 시작 전 점검 대상 관리',
    key(route) {
      return `/sftchk/oprnbgnbfchktgtmgnt/${route.params.catalog}`
    },
  },
  components: {
    popup,
    rgnslct,
  },
  data() {
    return {
      schData: {
        wkpl_id: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_supe_emp_nm: '',
        oprb_chk_supe_emp_id: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        usg_yn: 'Y',
        cmpy_cd: '',
        isAdmin: false,
      },
      frmData: {},
      tempData: {},
      size: 0,
      menu1: false,
      menu2: false,
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['ST00000002', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
      totalcnt: 0,
    }
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
  computed: {
    routeTab() {
      return this.$t('LB00000737')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.schData = this.$route.params.tmpSchData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
    // eslint-disable-next-line no-console
    console.log(user)
    user.roles.forEach((i) => {
      if (i === 'R0007' || i === 'R0005' || i === 'RZZZZ') {
        // 사용자 유저 권한 체크 role type cd = M01,C01  아닐경우 등록 버튼 숨기기 , 상세페이지로만이동
        this.isAdmin = true
      }
    })
  },
  mounted() {
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'oprb_chk_tgt_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cmpy_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_tgt_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_dtl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_supe_emp_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_supe_spic_emp_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'usg_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'fscr_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'crt_dt',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: 'ID', // ID
            name: 'oprb_chk_tgt_id',
            fieldName: 'oprb_chk_tgt_id',
            width: '100',
          },
          {
            header: this.$t('LB00000700'), // 회사
            name: 'cmpy_nm',
            fieldName: 'cmpy_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000021'), // 사업장
            name: 'wkpl_nm',
            fieldName: 'wkpl_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000667'), // 점검 대상명
            name: 'oprb_chk_tgt_nm',
            fieldName: 'oprb_chk_tgt_nm',
            width: '200',
            styleName: 'link left',
          },
          {
            header: this.$t('LB00000739'), // 점검위치
            name: 'oprb_chk_wkpl_loc_nm',
            fieldName: 'oprb_chk_wkpl_loc_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000681'), // 상세위치
            name: 'oprb_chk_dtl_loc_nm',
            fieldName: 'oprb_chk_dtl_loc_nm',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000738'), // 관리 감독자
            name: 'oprb_chk_supe_emp_nm',
            fieldName: 'oprb_chk_supe_emp_nm',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00001275'), // 부관리 감독자
            name: 'oprb_chk_supe_spic_emp_nm',
            fieldName: 'oprb_chk_supe_spic_emp_nm',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00000049'), // 사용여부
            name: 'usg_yn',
            fieldName: 'usg_yn',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00000055'), // 등록자
            name: 'fscr_nm',
            fieldName: 'fscr_nm',
            width: '100',
            styleName: 'center',
          },
          {
            header: this.$t('LB00000752'), // 등록일자
            name: 'crt_dt',
            fieldName: 'crt_dt',
            width: '100',
            styleName: 'left',
          },
        ]
        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.setDataSource(dataProvider)
        gridView.setColumns(columns)
        gridView.setFooters({ visible: false })

        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.setRowIndicator({ visible: false })
        gridView.editOptions.editable = false
        gridView.displayOptions.selectionStyle = 'singleRow'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('oprb_chk_tgt_id', 'visible', false)

        gridView.onCellClicked = function (grid, index, clickData) {
          gridView.commit()
          if (index.itemIndex > -1) this.openDetail(index)
        }.bind(this)

        this.selectOprbChkList()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popLocOpen() {
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.pop2.open(this.schData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'oprb_chk_supe_emp_nm', i.emp_nm)
          this.$set(this.schData, 'oprb_chk_supe_emp_id', i.emp_no)
        })
      }
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'oprb_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'oprb_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    async selectOprbChkList() {
      if (this.isEmpty(this.schData.oprb_chk_supe_emp_nm)) {
        this.schData.oprb_chk_supe_emp_id = ''
      }
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/oprnbgnbfchkgtmgntlist`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    submit() {
      this.$router.push({
        name: 'sftchk-oprnbgnbfchktgtmgntdtledt-oprnbgnbfchktgtmgntdtledt',
        params: {
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
            oprb_chk_tgt_nm: this.schData.oprb_chk_tgt_nm,
            oprb_chk_supe_emp_nm: this.schData.oprb_chk_supe_emp_nm,
            oprb_chk_supe_emp_id: this.schData.oprb_chk_supe_emp_id,
            oprb_chk_wkpl_loc_id: this.schData.oprb_chk_wkpl_loc_id,
            oprb_chk_wkpl_loc_nm: this.schData.oprb_chk_wkpl_loc_nm,
            usg_yn: this.schData.usg_yn,
            cmpy_cd: this.schData.cmpy_cd,
          },
        },
      })
    },
    getReport() {
      let keyVal = ''
      const checkedRows = gridView.getCheckedRows()
      if (checkedRows.length > 0) {
        for (let i = 0; i < checkedRows.length; i++) {
          keyVal = keyVal + "'" + dataProvider.getValue(checkedRows[i], 'oprb_chk_tgt_id') + "',"
        }
        keyVal = keyVal.substring(0, keyVal.length - 1)
        console.log(keyVal) // eslint-disable-line no-console
      } else {
        this.$toast.show(this.$t('MS00000296'), { className: 'toast_error' }) // 대상을 선택하세요.
        return
      }

      const reportdata = {
        tgt_id: keyVal,
        reportForm: 'oprnbgnbfchktgt',
        odi: 'safechk',
      }
      this.$axios.post(`/api/v1/comm/report/safechk`, reportdata).then((res) => {
        const popupWidth = 900
        const popupHeight = window.screen.height - 300
        const popupX = window.screen.width / 2 - popupWidth / 2
        const popupY = window.screen.height / 2 - popupHeight / 2
        const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
        const wnd = window.open('', 'report', options)
        wnd.document.open()
        wnd.document.write(res.data)
        wnd.document.close()
      })
      // const popupWidth = 900
      // const popupHeight = window.screen.height - 30
      // const popupX = window.screen.width / 2 - popupWidth / 2
      // const popupY = window.screen.height / 2 - popupHeight / 2
      // const options = 'status=no, menubar=no, toolbar=no, resizable=no,width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
      // // window.open('https://donggramisoft.co.kr/api/oz80/gwsgnprint.html?mtrl_no=' + mtrlNo + '&ozform=' + reportForm + '', 'report', options)
      // window.open('http://localhost:3000/oz80/gwsgnprint3.html?mtrl_no=60000023&ozform=5_1', 'testname', options)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column === 'oprb_chk_tgt_nm') {
        const value = gridView.getValue(index.itemIndex, 'oprb_chk_tgt_id')

        // 권한 체크
        if (!this.isEmpty(value)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'OPTGT', tgt_id: value } }).then((res) => {
            // eslint-disable-next-line no-console
            console.log(this.isAdmin)
            if (res === 'Success' || this.isAdmin === true) {
              this.$router.push({
                name: `sftchk-oprnbgnbfchktgtmgntdtledt-oprnbgnbfchktgtmgntdtledt`,
                params: {
                  oprnbgnbfchktgtmgntdtledt: value,
                  tmpSchData: {
                    wkpl_id: this.schData.wkpl_id,
                    oprb_chk_tgt_nm: this.schData.oprb_chk_tgt_nm,
                    oprb_chk_supe_emp_nm: this.schData.oprb_chk_supe_emp_nm,
                    oprb_chk_supe_emp_id: this.schData.oprb_chk_supe_emp_id,
                    oprb_chk_wkpl_loc_id: this.schData.oprb_chk_wkpl_loc_id,
                    oprb_chk_wkpl_loc_nm: this.schData.oprb_chk_wkpl_loc_nm,
                    usg_yn: this.schData.usg_yn,
                    cmpy_cd: this.schData.cmpy_cd,
                  },
                },
              })
            } else {
              this.$router.push({
                name: `sftchk-oprnbgnbfchktgtmgntdtllup-oprnbgnbfchktgtmgntdtllup`,
                params: {
                  oprnbgnbfchktgtmgntdtllup: value,
                  tmpSchData: {
                    wkpl_id: this.schData.wkpl_id,
                    oprb_chk_tgt_nm: this.schData.oprb_chk_tgt_nm,
                    oprb_chk_supe_emp_nm: this.schData.oprb_chk_supe_emp_nm,
                    oprb_chk_supe_emp_id: this.schData.oprb_chk_supe_emp_id,
                    oprb_chk_wkpl_loc_id: this.schData.oprb_chk_wkpl_loc_id,
                    oprb_chk_wkpl_loc_nm: this.schData.oprb_chk_wkpl_loc_nm,
                    usg_yn: this.schData.usg_yn,
                    cmpy_cd: this.schData.cmpy_cd,
                  },
                },
              })
            }
          })
        }
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
