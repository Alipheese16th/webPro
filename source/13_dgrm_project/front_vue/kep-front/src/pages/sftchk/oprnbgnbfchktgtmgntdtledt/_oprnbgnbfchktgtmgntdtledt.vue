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
                <!--관리 감독자-->
                <div class="label_tit essn">{{ $t('LB00000738') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_supe_emp_nm"
                  v-model="frmData.oprb_chk_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('supe')"
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
                <!--점검 대상명-->
                <div class="label_tit essn">{{ $t('LB00000667') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_tgt_nm"
                  v-model="frmData.oprb_chk_tgt_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--부관리 감독자-->
                <div class="label_tit essn">{{ $t('LB00001275') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_supe_spic_emp_nm"
                  v-model="frmData.oprb_chk_supe_spic_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen('spic')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검위치-->
                <div class="label_tit essn">{{ $t('LB00000739') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="oprb_chk_wkpl_loc_nm"
                  v-model="frmData.oprb_chk_wkpl_loc_nm"
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
                <!--상세위치-->
                <div class="label_tit essn">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="oprb_chk_dtl_loc_nm"
                  v-model="frmData.oprb_chk_dtl_loc_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--비고-->
                <div class="label_tit">{{ $t('LB00000095') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="oprb_chk_note_txt" v-model="frmData.oprb_chk_note_txt" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--설비목록-->
        <h3>{{ $t('LB00000754') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
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
    <popup ref="edtpop" mkey1="oprnbgnbfchktgtmgntedt1" mkey2="oprnbgnbfchktgtmgntedt2" @close="popClose" />
    <rgnslct ref="edtpop2" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="oprnbgnbfchktgtmgntedt1" @close="popLocClose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import rgnslct from '../../common/rgnslct'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

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
    // quillEditor,
  },
  data() {
    return {
      usg_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      selST00000007: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000002', 'ST00000007'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000007: [{ cd: '', cd_nm: '' }],
      },
      schData: {
        oprb_chk_tgt_id: '',
        date: '',
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      tempData: {},
      tmpSchData: {},
      isAdmin: false,
      frmData: {
        wkpl_id: '',
        oprb_chk_tgt_id: '',
        oprb_chk_supe_emp_id: '',
        oprb_chk_supe_emp_nm: '',
        oprb_chk_supe_spic_emp_id: '',
        oprb_chk_supe_spic_emp_nm: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        oprb_chk_dtl_loc_nm: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_note_txt: '',
        usg_yn: 'Y',
        lsch_nm: '',
      },
      menu: false,
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
  created() {
    const user = this.$store.getters['auth/getUser']

    // 저장된 timezone 정보를 파라미터로 하여 해당 timezone의 Date 객체를 반환
    date = this.getTzDateObj(user.time_zone)

    date.setDate(date.getDate() - 30)
    this.schData.date = this.get_date_str_gubun(date, '-')

    if (this.$route.params.oprnbgnbfchktgtmgntdtledt !== null && this.$route.params.oprnbgnbfchktgtmgntdtledt !== undefined) {
      // eslint-disable-next-line prefer-promise-reject-errors
    } else {
      this.frmData.wkpl_id = user.wkpl_id
    }

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        oprb_chk_tgt_nm: '',
        oprb_chk_supe_emp_nm: '',
        oprb_chk_supe_emp_id: '',
        oprb_chk_supe_spic_emp_nm: '',
        oprb_chk_supe_spic_emp_id: '',
        oprb_chk_wkpl_loc_id: '',
        oprb_chk_wkpl_loc_nm: '',
        usg_yn: '',
        cmpy_cd: '',
      }
    }
    user.roles.forEach((i) => {
      if (i === 'RZZZZ' || i === 'R0005' || i === 'R0007') {
        this.isAdmin = true
      }
    })
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
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'SELECT' })
        this.selST00000007.cd.push('')
        this.selST00000007.cd_nm.push('SELECT')
        res.ST00000007.forEach((i) => {
          this.selST00000007.cd.push(i.cd)
          this.selST00000007.cd_nm.push(i.cd_nm)
        })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'oprb_chk_eqp_sn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_eqp_type_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'oprb_chk_eqp_nm',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000757'), // 순번
            name: 'oprb_chk_eqp_sn',
            fieldName: 'oprb_chk_eqp_sn',
            width: '300',
          },
          {
            header: this.$t('LB00000758'), // 설비 종류
            name: 'oprb_chk_eqp_type_cd',
            fieldName: 'oprb_chk_eqp_type_cd',
            width: '200',
            sortable: false,
            lookupDisplay: true,
            values: this.selST00000007.cd,
            labels: this.selST00000007.cd_nm,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: false,
              displayLabels: 'label',
            },
            styleName: 'left',
          },
          {
            header: this.$t('LB00000760'), // 설비명
            name: 'oprb_chk_eqp_nm',
            fieldName: 'oprb_chk_eqp_nm',
            width: '300',
            styleName: 'left',
          },
        ]

        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.displayOptions.selectionStyle = 'block'
        gridView.setDataSource(dataProvider)
        gridView.setColumns(columns)
        gridView.setFooters({ visible: false })
        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('oprb_chk_eqp_sn', 'visible', false)
        // 권한 체크. 같은 부서 여부 조회
        if (!this.isEmpty(this.$route.params.oprnbgnbfchktgtmgntdtledt)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'OPTGT', tgt_id: this.$route.params.oprnbgnbfchktgtmgntdtledt } }).then((res) => {
            if (res === 'Success' || this.isAdmin === true) {
              this.schData.oprb_chk_tgt_id = this.$route.params.oprnbgnbfchktgtmgntdtledt
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
              this.$router.push({
                name: 'sftchk-oprnbgnbfchktgtmgntlist',
                params: {
                  tmpSchData: this.tmpSchData,
                },
              })
            }
          })
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    popOpen(gb) {
      this.userSwitch = gb
      this.$refs.edtpop.open(this.frmData, false)
    },
    popLocOpen() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.edtpop2.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          if (this.userSwitch === 'supe') {
            this.$set(this.frmData, 'oprb_chk_supe_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'oprb_chk_supe_emp_id', i.emp_no)
          } else if (this.userSwitch === 'spic') {
            this.$set(this.frmData, 'oprb_chk_supe_spic_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'oprb_chk_supe_spic_emp_id', i.emp_no)
          }
        })
      }
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'oprb_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'oprb_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    add() {
      gridView.commit()
      dataProvider.addRow({
        oprb_chk_eqp_sn: '',
        oprb_chk_eqp_type_cd: '',
        oprb_chk_eqp_nm: '',
      })
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-oprnbgnbfchktgtmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    validation() {
      gridView.commit()

      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.oprb_chk_supe_emp_id)) {
        this.$toast.show(this.$t('MS00000314'), { className: 'toast_error' }) // 관리 감독자를 선택해주세요.
        this.$refs.oprb_chk_supe_emp_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.oprb_chk_tgt_nm)) {
        this.$toast.show(this.$t('MS00000315'), { className: 'toast_error' }) // 점검 대상명을 입력해주세요.
        this.$refs.oprb_chk_tgt_nm.focus()
        return false
      }
      if (this.frmData.oprb_chk_tgt_nm.length > 50) {
        this.$toast.show(this.$t('MS00000316'), { className: 'toast_error' }) // 점검 대상명이 50자를 초과할 수 없습니다
        this.$refs.oprb_chk_tgt_nm.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.usg_yn)) {
      //   this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용 여부를 선택해주세요.
      //   this.$refs.usg_yn.focus()
      //   return false
      // }
      if (this.isEmpty(this.frmData.oprb_chk_wkpl_loc_id)) {
        this.$toast.show(this.$t('MS00000317'), { className: 'toast_error' }) // 점검 위치를 선택해주세요.
        this.$refs.oprb_chk_wkpl_loc_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.oprb_chk_dtl_loc_nm)) {
        this.$toast.show(this.$t('MS00000273'), { className: 'toast_error' }) // 상세위치를 입력해주세요.
        this.$refs.oprb_chk_dtl_loc_nm.focus()
        return false
      }
      if (this.frmData.oprb_chk_dtl_loc_nm.length > 50) {
        this.$toast.show(this.$t('MS00000274'), { className: 'toast_error' }) // 상세위치가 50자를 초과할 수 없습니다.
        this.$refs.oprb_chk_dtl_loc_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.oprb_chk_note_txt) && this.frmData.oprb_chk_note_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000134'), { className: 'toast_error' }) // 비고는 1000자를 초과할 수 없습니다.
        this.$refs.oprb_chk_note_txt.focus()
        return false
      }
      if (dataProvider.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000318'), { className: 'toast_error' }) // 설비목록을 등록해주세요.
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'oprb_chk_eqp_type_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprb_chk_eqp_type_cd' })
          this.$toast.show(this.$t('MS00000319'), { className: 'toast_error' }) // 설비 종류를 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'oprb_chk_eqp_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprb_chk_eqp_nm' })
          this.$toast.show(this.$t('MS00000320'), { className: 'toast_error' }) // 설비명을 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'oprb_chk_eqp_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprb_chk_eqp_nm' })
          this.$toast.show(this.$t('MS00000321'), { className: 'toast_error' }) // 설비명이 50자를 초과할 수 없습니다.
          return false
        }
      }
      return true
    },
    async select() {
      gridView.commit()

      await this.$axios.get('/api/v1/she/sftchk/oprnbgnbfchkgtmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const oprbChkResult = res.data.oprbChkResult[0]

          this.frmData.oprb_chk_supe_emp_id = oprbChkResult.oprb_chk_supe_emp_id
          this.frmData.oprb_chk_supe_emp_nm = oprbChkResult.oprb_chk_supe_emp_nm
          this.frmData.oprb_chk_supe_spic_emp_id = oprbChkResult.oprb_chk_supe_spic_emp_id
          this.frmData.oprb_chk_supe_spic_emp_nm = oprbChkResult.oprb_chk_supe_spic_emp_nm
          this.frmData.oprb_chk_dtl_loc_nm = oprbChkResult.oprb_chk_dtl_loc_nm
          this.frmData.oprb_chk_wkpl_loc_id = oprbChkResult.oprb_chk_wkpl_loc_id
          this.frmData.oprb_chk_wkpl_loc_nm = oprbChkResult.oprb_chk_wkpl_loc_nm
          this.frmData.oprb_chk_tgt_nm = oprbChkResult.oprb_chk_tgt_nm
          this.frmData.oprb_chk_tgt_id = oprbChkResult.oprb_chk_tgt_id
          this.frmData.oprb_chk_note_txt = oprbChkResult.oprb_chk_note_txt
          this.frmData.wkpl_id = oprbChkResult.wkpl_id
          this.frmData.usg_yn = oprbChkResult.usg_yn
          this.frmData.lsch_nm = oprbChkResult.lsch_nm

          this.tempData = JSON.parse(JSON.stringify(this.frmData))

          dataProvider.setRows(res.data.oprbChkEqpResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.totalcnt = gridView.getItemCount()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      gridView.commit()

      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      if (!this.validation()) {
        return
      }

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.oprb_chk_eqp_type_cd = dataProvider.getValue(i, 'oprb_chk_eqp_type_cd')
          temp.oprb_chk_eqp_nm = dataProvider.getValue(i, 'oprb_chk_eqp_nm')
          temp.oprb_chk_eqp_sn = dataProvider.getValue(i, 'oprb_chk_eqp_sn')

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      await this.$axios.post('/api/v1/she/sftchk/oprnbgnbfchkgtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          // 가져온 키 값으로 재조회
          this.schData.oprb_chk_tgt_id = res.data
          this.frmData.oprb_chk_tgt_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
