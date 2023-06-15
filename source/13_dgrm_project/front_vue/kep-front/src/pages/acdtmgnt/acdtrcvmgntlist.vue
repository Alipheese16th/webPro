<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 사고 보고 및 조사 > 사고 속보 수신처 관리-->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00001045') }} > {{ $t('MN00001078') }}</span>
    </div>
    <div class="work_title">
      <!-- 사고관리 -->
      <h2>
        <!-- 사고 속보 수신처 관리 -->
        <span>{{ $t('MN00001078') }}</span>
        &nbsp; &nbsp;
        <!-- *소속 사업장 인원만 입력 바랍니다. (전사 공유/전파는 사고 검토 후 본사에서 실시 하겠음) -->
        <span style="font-size: 15px; color: red">{{ $t('LB00001345') }}</span>
      </h2>
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
                  item-text="cd_nm"
                  item-value="cd"
                  label="SELECT"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="!isSysAdmin"
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
                  item-text="cd_nm"
                  item-value="cd"
                  label="SELECT"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :disabled="!isSysAdmin"
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사고구분-->
                <div class="label_tit">{{ $t('LB00000806') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="acdt_cl_cd"
                  v-model="schData.acdt_cl_cd"
                  item-text="cd_nm"
                  item-value="cd"
                  label="SELECT"
                  :items="codes.SH00000024"
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
            <v-btn color="" outlined height="36" @click="schReset()">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="1">
            <!--회사-->
            <div class="label_tit">{{ $t('LB00000700') }}</div>
          </v-col>
          <v-col cols="3">
            <div class="label_con">{{ frmData.cmpy_nm }}</div>
          </v-col>
          <v-col cols="1">
            <!--사업장-->
            <div class="label_tit">{{ $t('LB00000021') }}</div>
          </v-col>
          <v-col cols="3" style="display: flex">
            <!-- 회사 -->
            <div class="label_con">{{ frmData.wkpl_nm }}</div>
          </v-col>
          <v-col cols="1">
            <!--사고구분-->
            <div class="label_tit">{{ $t('LB00000806') }}</div>
          </v-col>
          <v-col cols="3">
            <div class="label_con">{{ frmData.acdt_cl_cd_nm }}</div>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <v-container>
      <v-row>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- 수신처 -->
              <h3>{{ $t('LB00001343') }}</h3>
            </div>
            <div style="display: flex; margin-top: 8px">
              <div style="display: block; width: 50%">
                <span style="font-size: 14px; font-weight: bold">&nbsp;부서</span>
                <div style="float: right">
                  <!-- 추가 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="add('rcvDeptAdd')">{{ $t('LB00000047') }}</v-btn>
                  <!-- 삭제 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="del('rcvDeptDel')">{{ $t('LB00000046') }}</v-btn>
                </div>
                <div id="rcvDeptGrdView" style="width: 100%; height: 394px"></div>
              </div>
              &nbsp;&nbsp;&nbsp;&nbsp;
              <div style="display: block; width: 50%">
                <span style="font-size: 14px; font-weight: bold">&nbsp;인원</span>
                <div style="float: right">
                  <!-- 추가 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="add('rcvEmpAdd')">{{ $t('LB00000047') }}</v-btn>
                  <!-- 삭제 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="del('rcvEmpDel')">{{ $t('LB00000046') }}</v-btn>
                </div>
                <div id="rcvEmpGrdView" style="width: 100%; height: 394px"></div>
              </div>
            </div>
          </div>
        </v-col>
        <v-col cols="6" style="padding: 0">
          <div class="realgrid_container">
            <div class="sub_title" style="display: flex">
              <!-- 참조처 -->
              <h3>{{ $t('LB00001344') }}</h3>
            </div>
            <div style="display: flex; margin-top: 8px">
              <div style="display: block; width: 50%">
                <span style="font-size: 14px; font-weight: bold">&nbsp;부서</span>
                <div style="float: right">
                  <!-- 추가 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="add('refDeptAdd')">{{ $t('LB00000047') }}</v-btn>
                  <!-- 삭제 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="del('refDeptDel')">{{ $t('LB00000046') }}</v-btn>
                </div>
                <div id="refDeptGrdView" style="width: 100%; height: 394px"></div>
              </div>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <div style="display: block; width: 50%">
                <span style="font-size: 14px; font-weight: bold">&nbsp;인원</span>
                <div style="float: right">
                  <!-- 추가 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="add('refEmpAdd')">{{ $t('LB00000047') }}</v-btn>
                  <!-- 삭제 -->
                  <v-btn style="bottom: 3px" color="" outlined height="25" @click="del('refEmpDel')">{{ $t('LB00000046') }}</v-btn>
                </div>
                <div id="refEmpGrdView" style="width: 100%; height: 394px"></div>
              </div>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <div v-if="excel" style="display: flex">
            <v-file-input v-model="files" accept=".xlsx" placeholder="Upload Excel File" @change="handleXlsFile"></v-file-input>
            &nbsp;
            <v-btn style="top: 19px" height="30" color="" outlined @click="excel = !excel">{{ $t('LB00000427') }}</v-btn>
          </div>
          <!--엑셀양식 다운로드-->
          <v-btn width="160" height="40" color="primary" depressed @click="excelExport()">{{ $t('LB00000988') }}</v-btn>
          <!--엑셀 업로드-->
          <v-btn width="120" height="40" color="primary" depressed @click="excel = !excel">{{ $t('LB00001081') }}</v-btn>
          <!-- 저장 -->
          <v-btn width="120" height="40" color="primary" depressed @click="save()">{{ $t('LB00000045') }}</v-btn>
        </div>
      </div>
    </div>
    <div id="xlsGrdView" style="display: none; width: 100%; height: 0px"></div>
    <usermultislct ref="userPop" @close="userPopupClose" />
    <deptslct ref="deptPop" mkey="acdt2" @close="deptPopupClose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import usermultislct from '../common/usermultislct'
import deptslct from '../common/deptslct'

// 수신부서 그리드
let rcvDeptGrdView = GridView
let rcvDeptDataProvider = LocalDataProvider
// 수신인 그리드
let rcvEmpGrdView = GridView
let rcvEmpDataProvider = LocalDataProvider
// 참조부서 그리드
let refDeptGrdView = GridView
let refDeptDataProvider = LocalDataProvider
// 참조인 그리드
let refEmpGrdView = GridView
let refEmpDataProvider = LocalDataProvider
// 엑셀양식다운로드용 그리드
let xlsGrdView = GridView
let xlsDataProvider = LocalDataProvider

export default {
  meta: {
    title: '사고속보 수신처 관리',
  },
  components: {
    usermultislct,
    deptslct,
  },
  data() {
    return {
      schData: {},
      frmData: {},
      userPopupGb: '',
      deptPopupGb: '',
      isSysAdmin: false,
      codeid: ['CO00000009', 'ST00000002', 'SH00000024', 'SH00000076'],
      codes: {
        CO00000009: [{ cd: '', cd_nm: '' }], // 회사코드
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000024: [{ cd: '', cd_nm: '' }], // 사고구분
        SH00000076: [{ cd: '', cd_nm: '' }], // 수신 구분코드
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      excel: false,
      files: [],
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    const user = this.$store.getters['auth/getUser']
    // 시스템관리자 그룹 사용자인지 체크
    user.roleTypeCds.forEach((i) => {
      if (i === 'M01') {
        this.isSysAdmin = true
      }
    })
    this.schData.cmpy_cd = user.cmpn_cd
    this.schData.wkpl_id = user.wkpl_id
    this.schData.acdt_cl_cd = 'S01'
  },
  mounted() {
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 공통코드 조회
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000024.unshift({ cd: '', cd_nm: 'SELECT' })

        // 데이터프로바이더 셋팅
        const dsFields = [
          {
            fieldName: 'cmpy_cd', // 회사 코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_id', // 사업장 코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'acdt_cl_cd', // 사고구분
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_cl_cd', // 구분코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_id', // 수신처
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_nm', // 수신처 명칭
            dataType: ValueType.TEXT,
          },
        ]
        let options = {}
        // 수신부서 데이터 프로바이더 셋팅
        rcvDeptDataProvider = new LocalDataProvider(false)
        rcvDeptDataProvider.setFields(dsFields)
        options = rcvDeptDataProvider.getOptions()
        options.softDeleting = true
        rcvDeptDataProvider.setOptions(options)
        // 수신인 데이터 프로바이더 셋팅
        rcvEmpDataProvider = new LocalDataProvider(false)
        rcvEmpDataProvider.setFields(dsFields)
        options = rcvEmpDataProvider.getOptions()
        options.softDeleting = true
        rcvEmpDataProvider.setOptions(options)
        // 참조부서 데이터 프로바이더 셋팅
        refDeptDataProvider = new LocalDataProvider(false)
        refDeptDataProvider.setFields(dsFields)
        options = refDeptDataProvider.getOptions()
        options.softDeleting = true
        refDeptDataProvider.setOptions(options)
        // 참조인 데이터 프로바이더 셋팅
        refEmpDataProvider = new LocalDataProvider(false)
        refEmpDataProvider.setFields(dsFields)
        options = refEmpDataProvider.getOptions()
        options.softDeleting = true
        refEmpDataProvider.setOptions(options)

        // 수신부서 그리드 셋팅
        const rcvDeptGrdColumns = [
          {
            header: '수신부서',
            name: 'eml_dstn_nm',
            fieldName: 'eml_dstn_nm',
            width: '80',
            styleName: 'left',
          },
        ]
        rcvDeptGrdView = new GridView('rcvDeptGrdView')
        rcvDeptGrdView.setDataSource(rcvDeptDataProvider)
        rcvDeptGrdView.setColumns(rcvDeptGrdColumns)
        rcvDeptGrdView.setFooters({ visible: false })
        rcvDeptGrdView.setStateBar({ visible: false })
        rcvDeptGrdView.setCheckBar({ visible: true, width: 30 })

        rcvDeptGrdView.header.height = 39
        rcvDeptGrdView.footer.height = 40
        rcvDeptGrdView.displayOptions.rowHeight = 40
        rcvDeptGrdView.displayOptions.fitStyle = 'fill'
        rcvDeptGrdView.displayOptions.selectionStyle = 'singleRow'
        rcvDeptGrdView.editOptions.editable = false
        rcvDeptGrdView.hideDeletedRows = true
        rcvDeptGrdView.onCellClicked = function (grid, clickData) {
          if (clickData.cellType === 'indicator' || clickData.cellType === 'data') {
            if (grid.getCheckBar().exclusive === true) {
              // 한개만 체크할 수 있을때
              grid.checkRow(clickData.dataRow, true, true)
            } else {
              // 여러개 체크할 수 있을때
              const isChk = grid.isCheckedRow(clickData.dataRow)
              grid.checkRow(clickData.dataRow, !isChk, false)
            }
          }
        }

        // 수신인 그리드 셋팅
        const rcvEmpGrdColumns = [
          {
            header: '수신인',
            name: 'eml_dstn_nm',
            fieldName: 'eml_dstn_nm',
            width: '80',
            styleName: 'left',
          },
        ]
        rcvEmpGrdView = new GridView('rcvEmpGrdView')
        rcvEmpGrdView.setDataSource(rcvEmpDataProvider)
        rcvEmpGrdView.setColumns(rcvEmpGrdColumns)
        rcvEmpGrdView.setFooters({ visible: false })
        rcvEmpGrdView.setStateBar({ visible: false })
        rcvEmpGrdView.setCheckBar({ visible: true, width: 30 })

        rcvEmpGrdView.header.height = 39
        rcvEmpGrdView.footer.height = 40
        rcvEmpGrdView.displayOptions.rowHeight = 40
        rcvEmpGrdView.displayOptions.fitStyle = 'fill'
        rcvEmpGrdView.displayOptions.selectionStyle = 'singleRow'
        rcvEmpGrdView.editOptions.editable = false
        rcvEmpGrdView.hideDeletedRows = true
        rcvEmpGrdView.onCellClicked = function (grid, clickData) {
          if (clickData.cellType === 'indicator' || clickData.cellType === 'data') {
            if (grid.getCheckBar().exclusive === true) {
              // 한개만 체크할 수 있을때
              grid.checkRow(clickData.dataRow, true, true)
            } else {
              // 여러개 체크할 수 있을때
              const isChk = grid.isCheckedRow(clickData.dataRow)
              grid.checkRow(clickData.dataRow, !isChk, false)
            }
          }
        }

        // 참조부서 그리드 셋팅
        const refDeptGrdColumns = [
          {
            header: '참조부서',
            name: 'eml_dstn_nm',
            fieldName: 'eml_dstn_nm',
            width: '80',
            styleName: 'left',
          },
        ]
        refDeptGrdView = new GridView('refDeptGrdView')
        refDeptGrdView.setDataSource(refDeptDataProvider)
        refDeptGrdView.setColumns(refDeptGrdColumns)
        refDeptGrdView.setFooters({ visible: false })
        refDeptGrdView.setStateBar({ visible: false })
        refDeptGrdView.setCheckBar({ visible: true, width: 30 })

        refDeptGrdView.header.height = 39
        refDeptGrdView.footer.height = 40
        refDeptGrdView.displayOptions.rowHeight = 40
        refDeptGrdView.displayOptions.fitStyle = 'fill'
        refDeptGrdView.displayOptions.selectionStyle = 'singleRow'
        refDeptGrdView.editOptions.editable = false
        refDeptGrdView.hideDeletedRows = true
        refDeptGrdView.onCellClicked = function (grid, clickData) {
          if (clickData.cellType === 'indicator' || clickData.cellType === 'data') {
            if (grid.getCheckBar().exclusive === true) {
              // 한개만 체크할 수 있을때
              grid.checkRow(clickData.dataRow, true, true)
            } else {
              // 여러개 체크할 수 있을때
              const isChk = grid.isCheckedRow(clickData.dataRow)
              grid.checkRow(clickData.dataRow, !isChk, false)
            }
          }
        }

        // 참조인 그리드 셋팅
        const refEmpGrdColumns = [
          {
            header: '참조인',
            name: 'eml_dstn_nm',
            fieldName: 'eml_dstn_nm',
            width: '80',
            styleName: 'left',
          },
        ]
        refEmpGrdView = new GridView('refEmpGrdView')
        refEmpGrdView.setDataSource(refEmpDataProvider)
        refEmpGrdView.setColumns(refEmpGrdColumns)
        refEmpGrdView.setFooters({ visible: false })
        refEmpGrdView.setStateBar({ visible: false })
        refEmpGrdView.setCheckBar({ visible: true, width: 30 })

        refEmpGrdView.header.height = 39
        refEmpGrdView.footer.height = 40
        refEmpGrdView.displayOptions.rowHeight = 40
        refEmpGrdView.displayOptions.fitStyle = 'fill'
        refEmpGrdView.displayOptions.selectionStyle = 'singleRow'
        refEmpGrdView.editOptions.editable = false
        refEmpGrdView.hideDeletedRows = true
        refEmpGrdView.onCellClicked = function (grid, clickData) {
          if (clickData.cellType === 'indicator' || clickData.cellType === 'data') {
            if (grid.getCheckBar().exclusive === true) {
              // 한개만 체크할 수 있을때
              grid.checkRow(clickData.dataRow, true, true)
            } else {
              // 여러개 체크할 수 있을때
              const isChk = grid.isCheckedRow(clickData.dataRow)
              grid.checkRow(clickData.dataRow, !isChk, false)
            }
          }
        }

        // 엑셀다운로드용 데이터프로바이더 셋팅
        const xlsDsFields = [
          {
            fieldName: 'eml_rcv_tp_cd', // 이메일수신유형코드 -> TO : 수신, CC : 참조
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_cl_cd', // 수신처구분코드 -> E : 사원번호, D : 부서코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_id', // 수신처 사원번호 또는 부서코드
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'eml_dstn_nm', // 수신처명
            dataType: ValueType.TEXT,
          },
        ]
        xlsDataProvider = new LocalDataProvider(false)
        xlsDataProvider.setFields(xlsDsFields)

        // 엑셀다운로드용 그리드 셋팅
        const xlsGrdColumns = [
          {
            header: '이메일 수신유형코드 (TO : 수신, CC : 참조)',
            name: 'eml_rcv_tp_cd',
            fieldName: 'eml_rcv_tp_cd',
            width: '300',
          },
          {
            header: '수신처 구분코드 (E : 사원번호, D : 부서코드)',
            name: 'eml_dstn_cl_cd',
            fieldName: 'eml_dstn_cl_cd',
            width: '300',
          },
          {
            header: '수신처 사원번호 또는 부서코드',
            name: 'eml_dstn_id',
            fieldName: 'eml_dstn_id',
            width: '300',
          },
          {
            header: '수신처명',
            name: 'eml_dstn_nm',
            fieldName: 'eml_dstn_nm',
            width: '300',
          },
        ]
        xlsGrdView = new GridView('xlsGrdView')
        xlsGrdView.setDataSource(xlsDataProvider)
        xlsGrdView.setColumns(xlsGrdColumns)
        xlsGrdView.setFooters({ visible: false })
        xlsGrdView.setStateBar({ visible: false })
        xlsGrdView.setCheckBar({ visible: false })

        xlsGrdView.header.height = 30
        xlsGrdView.footer.height = 40
        xlsGrdView.displayOptions.rowHeight = 40
        xlsGrdView.displayOptions.fitStyle = 'fill'
        xlsGrdView.editOptions.editable = false

        // 조회
        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    // 목록조회
    async select() {
      if (this.isEmpty(this.schData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000339'), { className: 'toast_error' }) // 회사를 선택해주세요.
        this.$refs.cmpy_cd.focus()
        return
      }
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return
      }
      if (this.isEmpty(this.schData.acdt_cl_cd)) {
        this.$toast.show(this.$t('MS00000355'), { className: 'toast_error' }) // 사고구분을 선택해주세요
        this.$refs.acdt_cl_cd.focus()
        return
      }

      rcvDeptGrdView.commit()
      rcvEmpGrdView.commit()
      refDeptGrdView.commit()
      refEmpGrdView.commit()

      const recrows = await this.$axios.$get(`/api/v1/she/sfty/acdtrcvrec`, { params: this.schData })
      this.frmData = recrows.basic[0]
      rcvDeptDataProvider.setRows(recrows.rcvDeptList)
      rcvEmpDataProvider.setRows(recrows.rcvEmpList)
      refDeptDataProvider.setRows(recrows.refDeptList)
      refEmpDataProvider.setRows(recrows.refEmpList)
    },
    // 데이터 추가
    add(addType) {
      switch (addType) {
        // 수신부서일 경우
        case 'rcvDeptAdd':
          rcvDeptGrdView.commit()
          this.deptPopupGb = 'rcv'
          this.$refs.deptPop.open(this.frmData, true)
          break

        // 수신인일 경우
        case 'rcvEmpAdd':
          rcvEmpGrdView.commit()
          this.userPopupGb = 'rcv'
          this.$refs.userPop.open(this.frmData)
          break

        // 참조부서일 경우
        case 'refDeptAdd':
          refDeptGrdView.commit()
          this.deptPopupGb = 'ref'
          this.$refs.deptPop.open(this.frmData, true)
          break

        // 참조인일 경우
        case 'refEmpAdd':
          refEmpGrdView.commit()
          this.userPopupGb = 'ref'
          this.$refs.userPop.open(this.frmData)
          break
      }
    },
    // 데이터 삭제
    del(delType) {
      let checkedRows = []
      switch (delType) {
        // 수신부서일 경우
        case 'rcvDeptDel':
          rcvDeptGrdView.commit()
          checkedRows = rcvDeptGrdView.getCheckedRows()
          rcvDeptDataProvider.removeRows(checkedRows)
          break

        // 수신인일 경우
        case 'rcvEmpDel':
          rcvEmpGrdView.commit()
          checkedRows = rcvEmpGrdView.getCheckedRows()
          rcvEmpDataProvider.removeRows(checkedRows)
          break

        // 참조부서일 경우
        case 'refDeptDel':
          refDeptGrdView.commit()
          checkedRows = refDeptGrdView.getCheckedRows()
          refDeptDataProvider.removeRows(checkedRows)
          break

        // 참조인일 경우
        case 'refEmpDel':
          refEmpGrdView.commit()
          checkedRows = refEmpGrdView.getCheckedRows()
          refEmpDataProvider.removeRows(checkedRows)
          break
      }
    },
    schReset() {
      this.schData = {
        cmpy_cd: '',
        wkpl_id: '',
        acdt_cl_cd: 'S01',
      }
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async deptPopupClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      }

      await returnData.forEach((i) => {
        const fields = ['eml_dstn_id']
        const values = [i.dept_cd]
        const options = {
          fields,
          values,
          allFields: true,
          startIndex: 0,
          wrap: true,
          caseSensitive: false,
          partialMatch: true,
        }
        if (this.deptPopupGb === 'rcv') {
          // 수신인일 경우
          const passyn = rcvDeptGrdView.searchItem(options)
          if (passyn === -1) {
            rcvDeptDataProvider.addRow({
              eml_rcv_tp_cd: 'TO', // 수신/참조 구분 코드
              eml_dstn_cl_cd: 'D', // 부서/인원 구분 코드
              eml_dstn_id: i.dept_cd, // 수신처
              eml_dstn_nm: i.dept_nm, // 수신처
            })
          } else {
            this.$toast.show(i.dept_nm + '은(는) 이미 선택된 부서입니다.', { className: 'toast_warn' }) // 은 이미 선택된 부서입니다.
          }
        } else if (this.deptPopupGb === 'ref') {
          // 참조인일 경우
          const passyn = refDeptGrdView.searchItem(options)
          if (passyn === -1) {
            refDeptDataProvider.addRow({
              eml_rcv_tp_cd: 'CC', // 수신/참조 구분 코드
              eml_dstn_cl_cd: 'D', // 부서/인원 구분 코드
              eml_dstn_id: i.dept_cd, // 수신처
              eml_dstn_nm: i.dept_nm, // 수신처
            })
          } else {
            this.$toast.show(i.dept_nm + '은(는) 이미 선택된 부서입니다.', { className: 'toast_warn' }) // 은 이미 선택된 부서입니다.
          }
        }
      })
    },
    async userPopupClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      }

      await returnData.forEach((i) => {
        const fields = ['eml_dstn_id']
        const values = [i.emp_no]
        const options = {
          fields,
          values,
          allFields: true,
          startIndex: 0,
          wrap: true,
          caseSensitive: false,
          partialMatch: true,
        }
        if (this.userPopupGb === 'rcv') {
          // 수신인일 경우
          const passyn = rcvEmpGrdView.searchItem(options)
          if (passyn === -1) {
            rcvEmpDataProvider.addRow({
              eml_rcv_tp_cd: 'TO', // 수신/참조 구분 코드
              eml_dstn_cl_cd: 'E', // 부서/인원 구분 코드
              eml_dstn_id: i.emp_no, // 수신처
              eml_dstn_nm: i.dept_nm + ' ' + i.emp_nm, // 수신처
            })
          } else {
            this.$toast.show(i.emp_nm + '님은(는) 이미 선택된 인원입니다.', { className: 'toast_warn' }) // 님은 이미 선택된 인원입니다.
          }
        } else if (this.userPopupGb === 'ref') {
          // 참조인일 경우
          const passyn = refEmpGrdView.searchItem(options)
          if (passyn === -1) {
            refEmpDataProvider.addRow({
              eml_rcv_tp_cd: 'CC', // 수신/참조 구분 코드
              eml_dstn_cl_cd: 'E', // 부서/인원 구분 코드
              eml_dstn_id: i.emp_no, // 수신처
              eml_dstn_nm: i.dept_nm + ' ' + i.emp_nm, // 수신처
            })
          } else {
            this.$toast.show(i.emp_nm + '님은(는) 이미 선택된 인원입니다.', { className: 'toast_warn' }) // 님은 이미 선택된 인원입니다.
          }
        }
      })
    },
    // 엑셀양식다운로드
    excelExport() {
      const showProgress = false
      const indicator = 'hidden'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = '사고속보수신처_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      xlsGrdView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: false,
        done() {
          //
        },
      })
    },
    // 엑셀업로드
    handleXlsFile(e) {
      const f = this.files
      const reader = new FileReader()
      reader.onload = function (e) {
        const data = e.target.result
        const arr = this.fixdata(data)
        const workbook = XLSX.read(btoa(arr), { type: 'base64' })
        workbook.SheetNames.forEach(function (sheetName) {
          const rows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {})
          if (rows.length > 0) {
            rcvEmpGrdView.commit()
            refDeptGrdView.commit()
            rows.forEach((row) => {
              // 엑셀 컬럼과 그리드 컬럼 매핑
              row.eml_rcv_tp_cd = row['이메일 수신유형코드 (TO : 수신, CC : 참조)']
              row.eml_dstn_cl_cd = row['수신처 구분코드 (E : 사원번호, D : 부서코드)']
              row.eml_dstn_id = row['수신처 사원번호 또는 부서코드']
              row.eml_dstn_nm = row['수신처명']

              const rowData = {
                eml_rcv_tp_cd: row.eml_rcv_tp_cd, // 이메일 수신유형코드
                eml_dstn_cl_cd: row.eml_dstn_cl_cd, // 수신처 구분코드
                eml_dstn_id: row.eml_dstn_id, // 이메일 수신유형코드
                eml_dstn_nm: row.eml_dstn_nm, // 수신처명
              }
              if (row.eml_rcv_tp_cd === 'TO') {
                // 수신일 경우 수신처로
                if (row.eml_dstn_cl_cd === 'D') {
                  // 부서일때
                  rcvDeptDataProvider.addRow(rowData)
                } else if (row.eml_dstn_cl_cd === 'E') {
                  // 인원일때
                  rcvEmpDataProvider.addRow(rowData)
                }
              } else if (row.eml_rcv_tp_cd === 'CC') {
                // 참조일 경우 참조처로
                if (row.eml_dstn_cl_cd === 'D') {
                  // 부서일때
                  refDeptDataProvider.addRow(rowData)
                } else if (row.eml_dstn_cl_cd === 'E') {
                  // 인원일때
                  refEmpDataProvider.addRow(rowData)
                }
              }
            })
          }
        })
      }.bind(this)

      reader.readAsArrayBuffer(f)
      this.excel = !this.excel
      this.files = []
    },
    fixdata(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    },
    // 저장 버튼 클릭 시
    async save() {
      rcvDeptGrdView.commit()
      rcvEmpGrdView.commit()
      refDeptGrdView.commit()
      refEmpGrdView.commit()

      if (
        rcvDeptDataProvider.getRowStateCount(['created', 'updated', 'deleted']) === 0 &&
        rcvEmpDataProvider.getRowStateCount(['created', 'updated', 'deleted']) === 0 &&
        refDeptDataProvider.getRowStateCount(['created', 'updated', 'deleted']) === 0 &&
        refEmpDataProvider.getRowStateCount(['created', 'updated', 'deleted']) === 0
      ) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }

      // 수신부서와 참조부서 중복 체크
      for (let i = 0; i < rcvDeptDataProvider.getRowCount(); i++) {
        if (rcvDeptDataProvider.getRowState(i) === 'createAndDeleted' || rcvDeptDataProvider.getRowState(i) === 'deleted') {
          continue
        }

        for (let j = 0; j < refDeptDataProvider.getRowCount(); j++) {
          if (refDeptDataProvider.getRowState(j) === 'createAndDeleted' || refDeptDataProvider.getRowState(j) === 'deleted') {
            continue
          }

          const val1 = rcvDeptDataProvider.getValue(i, 'eml_dstn_id')
          const val2 = refDeptDataProvider.getValue(j, 'eml_dstn_id')
          if (val1 === val2) {
            rcvDeptGrdView.setCurrent({ dataRow: i, column: 'eml_dstn_id' })
            refDeptGrdView.setCurrent({ dataRow: j, column: 'eml_dstn_id' })
            this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 값이있습니다.
            return
          }
        }
      }

      // 수신인원과 참조인원 중복 체크
      for (let i = 0; i < rcvEmpDataProvider.getRowCount(); i++) {
        if (rcvEmpDataProvider.getRowState(i) === 'createAndDeleted' || rcvEmpDataProvider.getRowState(i) === 'deleted') {
          continue
        }

        for (let j = 0; j < refEmpDataProvider.getRowCount(); j++) {
          if (refEmpDataProvider.getRowState(j) === 'createAndDeleted' || refEmpDataProvider.getRowState(j) === 'deleted') {
            continue
          }

          const val1 = rcvEmpDataProvider.getValue(i, 'eml_dstn_id')
          const val2 = refEmpDataProvider.getValue(j, 'eml_dstn_id')
          if (val1 === val2) {
            rcvEmpGrdView.setCurrent({ dataRow: i, column: 'eml_dstn_id' })
            refEmpGrdView.setCurrent({ dataRow: j, column: 'eml_dstn_id' })
            this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 값이있습니다.
            return
          }
        }
      }

      // 저장 여부 확인
      const ref = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!ref) {
        return
      }

      const saveInfo = { wkpl_id: this.frmData.wkpl_id, acdt_cl_cd: this.frmData.acdt_cl_cd, saveRecList: [], saveRefList: [] }

      // 수신처 리스트 저장 전처리
      const saveRecList = []
      for (let i = 0; i < rcvDeptDataProvider.getRowCount(); i++) {
        // 추가했다가 삭제한 행, 삭제한 행은 제외
        if (rcvDeptDataProvider.getRowState(i) === 'createAndDeleted' || rcvDeptDataProvider.getRowState(i) === 'deleted') {
          continue
        }

        const saveTempData = {}
        saveTempData.wkpl_id = this.frmData.wkpl_id
        saveTempData.acdt_cl_cd = this.frmData.acdt_cl_cd
        saveTempData.eml_rcv_tp_cd = 'TO'
        saveTempData.eml_dstn_cl_cd = rcvDeptDataProvider.getValue(i, 'eml_dstn_cl_cd')
        saveTempData.eml_dstn_id = rcvDeptDataProvider.getValue(i, 'eml_dstn_id')
        saveRecList.push(saveTempData)
      }
      for (let i = 0; i < rcvEmpDataProvider.getRowCount(); i++) {
        // 추가했다가 삭제한 행, 삭제한 행은 제외
        if (rcvEmpDataProvider.getRowState(i) === 'createAndDeleted' || rcvEmpDataProvider.getRowState(i) === 'deleted') {
          continue
        }

        const saveTempData = {}
        saveTempData.wkpl_id = this.frmData.wkpl_id
        saveTempData.acdt_cl_cd = this.frmData.acdt_cl_cd
        saveTempData.eml_rcv_tp_cd = 'TO'
        saveTempData.eml_dstn_cl_cd = rcvEmpDataProvider.getValue(i, 'eml_dstn_cl_cd')
        saveTempData.eml_dstn_id = rcvEmpDataProvider.getValue(i, 'eml_dstn_id')
        saveRecList.push(saveTempData)
      }
      saveInfo.saveRecList = saveRecList

      // 참조처 리스트 저장 전처리
      const saveRefList = []
      for (let i = 0; i < refDeptDataProvider.getRowCount(); i++) {
        // 추가했다가 삭제한 행, 삭제한 행은 제외
        if (refDeptDataProvider.getRowState(i) === 'createAndDeleted' || refDeptDataProvider.getRowState(i) === 'deleted') {
          continue
        }
        const saveTempData = {}
        saveTempData.wkpl_id = this.frmData.wkpl_id
        saveTempData.acdt_cl_cd = this.frmData.acdt_cl_cd
        saveTempData.eml_rcv_tp_cd = 'CC'
        saveTempData.eml_dstn_cl_cd = refDeptDataProvider.getValue(i, 'eml_dstn_cl_cd')
        saveTempData.eml_dstn_id = refDeptDataProvider.getValue(i, 'eml_dstn_id')
        saveRefList.push(saveTempData)
      }
      for (let i = 0; i < refEmpDataProvider.getRowCount(); i++) {
        // 추가했다가 삭제한 행, 삭제한 행은 제외
        if (refEmpDataProvider.getRowState(i) === 'createAndDeleted' || refEmpDataProvider.getRowState(i) === 'deleted') {
          continue
        }
        const saveTempData = {}
        saveTempData.wkpl_id = this.frmData.wkpl_id
        saveTempData.acdt_cl_cd = this.frmData.acdt_cl_cd
        saveTempData.eml_rcv_tp_cd = 'CC'
        saveTempData.eml_dstn_cl_cd = refEmpDataProvider.getValue(i, 'eml_dstn_cl_cd')
        saveTempData.eml_dstn_id = refEmpDataProvider.getValue(i, 'eml_dstn_id')
        saveRefList.push(saveTempData)
      }
      saveInfo.saveRefList = saveRefList

      await this.$axios.post('/api/v1/she/sfty/acdtrcv', saveInfo).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.select()
        }
      })
    },
  },
}
</script>
