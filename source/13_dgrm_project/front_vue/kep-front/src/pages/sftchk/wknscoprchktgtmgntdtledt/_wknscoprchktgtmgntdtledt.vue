<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 합동점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001011') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 합동점검 대상 관리-->
      <h2>{{ $t('LB00001011') }}</h2>
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
                <!--점검 담당자-->
                <div class="label_tit essn">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="jnt_chk_supe_emp_nm"
                  v-model="frmData.jnt_chk_supe_emp_nm"
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
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit essn">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="jnt_chk_tgt_nm"
                  v-model="frmData.jnt_chk_tgt_nm"
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
                <!--점검코스-->
                <div class="label_tit essn">{{ $t('LB00001013') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="jnt_chk_dtl_loc_nm"
                  v-model="frmData.jnt_chk_dtl_loc_nm"
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
                <v-textarea ref="jnt_chk_note_txt" v-model="frmData.jnt_chk_note_txt" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 참석자 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000908') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ atntotalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="addatn">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="delatn">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 점검계획 -->
      <div class="sub_title">
        <h3>{{ $t('LB00001021') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">
          {{ $t('LB00000039') }}<em>{{ chktotalcnt }}</em
          >{{ $t('LB00000040') }}
        </div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="addchk">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="delchk">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 481px"></div>
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
    <popup ref="edtpop" mkey1="wknscoprchktgtmgntdtledt1" mkey2="wknscoprchktgtmgntdtledt2" @close="popClose" />
    <rgnslct ref="edtpop2" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="wknscoprchktgtmgntdtledt1" @close="popLocClose" />
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

// 그리드 3
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업장 합동점검 대상 관리',
    key(route) {
      return `/sftchk/wknscoprchktgtmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    rgnslct,
    // quillEditor,
  },
  data() {
    return {
      atntotalcnt: 0,
      chktotalcnt: 0,
      usg_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      selSH00000022: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000002', 'ST00000018', 'SH00000021', 'SH00000022'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000018: [{ cd: '', cd_nm: '' }],
        SH00000021: [{ cd: '', cd_nm: '' }],
        SH00000022: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      schData: {
        jnt_chk_tgt_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        jnt_chk_tgt_id: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
        jnt_chk_dtl_loc_nm: '',
        jnt_chk_tgt_nm: '',
        jnt_chk_note_txt: '',
        chec_cl_cd: '',
        usg_yn: 'Y',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001011')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    gridView2.commit()
    if (
      dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
      dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0 ||
      JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)
    ) {
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

    if (this.$route.params.wknscoprchktgtmgntdtledt !== null && this.$route.params.wknscoprchktgtmgntdtledt !== undefined) {
      // eslint-disable-next-line prefer-promise-reject-errors
    } else {
      this.frmData.wkpl_id = user.wkpl_id
    }

    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        jnt_chk_tgt_nm: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
        jnt_chk_dtl_loc_nm: '',
        usg_yn: '',
        cmpy_cd: '',
      }
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
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.ST00000018.unshift({ cd: '', cd_nm: 'SELECT' })
        this.codes.SH00000021.unshift({ cd: '', cd_nm: 'SELECT' })
        this.selSH00000022.cd.push('')
        this.selSH00000022.cd_nm.push('SELECT')
        res.SH00000022.forEach((i) => {
          this.selSH00000022.cd.push(i.cd)
          this.selSH00000022.cd_nm.push(i.cd_nm)
        })

        // 그리드 세팅
        const fields = [
          {
            fieldName: 'jnt_chk_atnd_sn', // 기본키로 쓰임
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnc_emp_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_pstn_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_dept_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_tphn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_atnd_emad',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'jnt_chk_atnd_cl_cd',
            fieldName: 'jnt_chk_atnd_cl_cd',
            width: '100',
            sortable: false,
            lookupDisplay: true,
            values: this.selSH00000022.cd,
            labels: this.selSH00000022.cd_nm,
            styleName: 'left',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00000701'), // 성명
            name: 'jnt_chk_atnd_nm',
            fieldName: 'jnt_chk_atnd_nm',
            width: '100',
            button: 'action',
            alwaysShowButton: false,
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000766'), // 직책
            name: 'jnt_chk_atnd_pstn_nm',
            fieldName: 'jnt_chk_atnd_pstn_nm',
            width: '200',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = true
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000909'), // 회사명
            name: 'jnt_chk_atnd_dept_nm',
            fieldName: 'jnt_chk_atnd_dept_nm',
            width: '200',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000723'), // 전화번호
            name: 'jnt_chk_atnd_tphn',
            fieldName: 'jnt_chk_atnd_tphn',
            width: '200',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
          {
            header: this.$t('LB00000910'), // 이메일주소
            name: 'jnt_chk_atnd_emad',
            fieldName: 'jnt_chk_atnd_emad',
            width: '300',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_atnd_cl_cd')
              const ret = {}

              if (gubun === 'U01') {
                ret.editable = false
              } else if (gubun === 'U02') {
                ret.editable = true
              } else if (gubun === 'U03') {
                ret.editable = true
              } else {
                ret.editable = false
              }
              return ret
            },
          },
        ]
        // 그리드 1 시작
        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.displayOptions.selectionStyle = 'block'
        gridView.setDataSource(dataProvider)

        gridView.setColumns(columns)
        gridView.setFooters({ visible: false })
        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.setRowIndicator({ visible: false })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('jnt_chk_atnd_tphn', 'visible', false)

        // 유저 팝업 띄우기
        gridView.onCellButtonClicked = function (grid, itemIndex, column) {
          // const current = gridView.getCurrent()
          this.circChkUsr = itemIndex.dataRow
          if (itemIndex.column === 'jnt_chk_atnd_nm') {
            const gubun = grid.getValue(itemIndex.dataRow, 'jnt_chk_atnd_cl_cd')
            if (gubun === 'U01') {
              this.rowChk = true
              gridView.commit()
              this.popOpen()
            } else if (gubun === 'U02' || gubun === 'U03') {
              this.$toast.show(this.$t('MS00000370'), { className: 'toast_error' }) // 임직원만 선택 가능합니다.
            } else {
              this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            }
          }
        }.bind(this)

        gridView.onCellEdited = function (grid, itemIndex, row, field) {
          if (field === 0) {
            gridView.commit()
            dataProvider.setValue(row, 'jnt_chk_atnc_emp_id', '')
            dataProvider.setValue(row, 'jnt_chk_atnd_nm', '')
            dataProvider.setValue(row, 'jnt_chk_atnd_pstn_nm', '')
            dataProvider.setValue(row, 'jnt_chk_atnd_dept_nm', '')
            dataProvider.setValue(row, 'jnt_chk_atnd_emad', '')
          }
        }

        gridView.columnByName('jnt_chk_atnd_nm').buttonVisibleCallback = function (grid, index, focused, mouseEntered) {
          return grid.getValue(index.itemIndex, 'jnt_chk_atnd_cl_cd') === 'U01' && (focused || mouseEntered)
        }
        // 그리드 1 끝

        // 그리드 2 시작
        // 그리드 세팅
        const fields2 = [
          {
            fieldName: 'jnt_chk_tgt_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_schd_dt',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_schd_tm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_ttl',
            dataType: ValueType.TEXT,
          },
        ]
        const columns2 = [
          {
            header: this.$t('LB00001022'), // 합동점검대상 Id
            name: 'jnt_chk_tgt_id',
            fieldName: 'jnt_chk_tgt_id',
            width: '200',
          },
          {
            header: this.$t('LB00001023'), // 점검예정일
            name: 'jnt_chk_schd_dt',
            fieldName: 'jnt_chk_schd_dt',
            width: '100',
            textFormat: '([0-9]{4})([0-9]{2})([0-9]{2})$;$1-$2-$3',
            styleName: 'left',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_tgt_id')
              const ret = {}

              switch (gubun !== null && gubun !== '') {
                case true:
                  ret.editable = false
                  ret.editor = {
                    type: 'text',
                    textReadOnly: true,
                    mask: {
                      editMask: '9999-99-99',
                    },
                  }
                  break
                case false:
                  ret.editable = true
                  ret.editor = {
                    type: 'date',
                    datetimeFormat: 'yyyyMMdd',
                    textReadOnly: true,
                    mask: {
                      editMask: '9999-99-99',
                    },
                  }
                  break
              }
              return ret
            },
          },
          {
            header: this.$t('LB00001024'), // 점검예정시간
            name: 'jnt_chk_schd_tm',
            fieldName: 'jnt_chk_schd_tm',
            width: '100',
            styleName: 'left',
            editor: {
              mask: {
                definitions: {
                  b: '[0-2]',
                  c: '[0-9]',
                  d: '[0-5]',
                  e: '[0-9]',
                },
                editMask: 'bc:de',
                includedFormat: true,
                overWrite: true,
                allowEmpty: true,
              },
            },
            textFormat: '([0-9]{2})([0-9]{2});$1:$2',
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'jnt_chk_tgt_id')
              const ret = {}

              switch (gubun !== null && gubun !== '') {
                case true:
                  ret.editable = false
                  break
                case false:
                  ret.editable = true
                  break
              }
              return ret
            },
          },
          {
            header: this.$t('LB00001025'), // 중점 점검 사항
            name: 'jnt_chk_ttl',
            fieldName: 'jnt_chk_ttl',
            width: '200',
            styleName: 'left',
          },
        ]
        dataProvider2 = new LocalDataProvider(false)
        dataProvider2.setFields(fields2)
        gridView2 = new GridView('realgrid2')
        gridView2.displayOptions.selectionStyle = 'block'
        gridView2.setDataSource(dataProvider2)

        gridView2.setColumns(columns2)
        gridView2.setFooters({ visible: false })
        gridView2.setStateBar({ visible: false })
        gridView2.setCheckBar({ visible: true, width: 50 })
        gridView2.setRowIndicator({ visible: false })
        gridView2.editOptions.editable = true
        gridView2.displayOptions.selectionStyle = 'block'

        gridView2.header.height = 39
        gridView2.displayOptions.rowHeight = 40
        gridView2.footer.height = 40
        gridView2.displayOptions.fitStyle = 'fill'
        gridView2.setColumnProperty('jnt_chk_tgt_id', 'visible', false)
        gridView2.setColumnProperty('jnt_chk_schd_tm', 'visible', false)
        // 그리드 2 끝

        // 권한 체크. 같은 부서 여부 조회
        if (!this.isEmpty(this.$route.params.wknscoprchktgtmgntdtledt)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'JNTGT', jnt_chk_tgt_id: this.$route.params.wknscoprchktgtmgntdtledt } }).then((res) => {
            if (res === 'Success') {
              this.schData.jnt_chk_tgt_id = this.$route.params.wknscoprchktgtmgntdtledt
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
              this.$router.push({
                name: 'sftchk-wknscoprchktgtmgntlist',
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
    popOpen() {
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
      } else if (this.rowChk) {
        returnData.forEach((i) => {
          // eslint-disable-next-line no-console
          console.log(i)
          dataProvider.setValue(this.circChkUsr, 'jnt_chk_atnc_emp_id', i.emp_no)
          dataProvider.setValue(this.circChkUsr, 'jnt_chk_atnd_nm', i.emp_nm)
          dataProvider.setValue(this.circChkUsr, 'jnt_chk_atnd_pstn_nm', i.jkw_ko_nm)
          dataProvider.setValue(this.circChkUsr, 'jnt_chk_atnd_dept_nm', i.cmpy_nm)
          dataProvider.setValue(this.circChkUsr, 'jnt_chk_atnd_emad', i.emp_email)
        })
        this.rowChk = false
      } else {
        returnData.forEach((i) => {
          this.$set(this.frmData, 'jnt_chk_supe_emp_nm', i.emp_nm)
          this.$set(this.frmData, 'jnt_chk_supe_emp_id', i.emp_no)
        })
      }
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'jnt_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'jnt_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    addatn() {
      gridView.commit()
      dataProvider.addRow({
        jnt_chk_atnd_cl_cd: '',
        jnt_chk_atnc_emp_id: '',
        jnt_chk_atnd_nm: '',
        jnt_chk_atnd_pstn_nm: '',
        jnt_chk_atnd_dept_nm: '',
        jnt_chk_atnd_emad: '',
      })
      this.atntotalcnt = gridView.getItemCount()
    },
    delatn() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.atntotalcnt = gridView.getItemCount()
    },
    addchk() {
      gridView2.commit()
      dataProvider2.addRow({
        jnt_chk_tgt_id: '',
        jnt_chk_schd_dt: '',
        jnt_chk_schd_tm: '',
        jnt_chk_ttl: '',
      })
      this.chktotalcnt = gridView2.getItemCount()
    },
    delchk() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.removeRows(checkedRows)
      dataProvider2.hideRows(checkedRows)
      this.chktotalcnt = gridView2.getItemCount()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-wknscoprchktgtmgntlist',
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
      if (this.isEmpty(this.frmData.jnt_chk_supe_emp_id)) {
        this.$toast.show(this.$t('MS00000269'), { className: 'toast_error' }) // 점검 담당자를 선택해주세요.
        this.$refs.jnt_chk_supe_emp_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.jnt_chk_tgt_nm)) {
        this.$toast.show(this.$t('MS00000275'), { className: 'toast_error' }) // 점검명을 입력해주세요.
        this.$refs.jnt_chk_tgt_nm.focus()
        return false
      }
      if (this.frmData.jnt_chk_tgt_nm.length > 50) {
        this.$toast.show(this.$t('MS00000276'), { className: 'toast_error' }) // 점검명은 50자를 초과할 수 없습니다.
        this.$refs.jnt_chk_tgt_nm.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.usg_yn)) {
      //   this.$toast.show(this.$t('MS00000457'), { className: 'toast_error' }) // 사용 여부를 선택해주세요.
      //   this.$refs.usg_yn.focus()
      //   return false
      // }
      if (this.isEmpty(this.frmData.jnt_chk_dtl_loc_nm)) {
        this.$toast.show(this.$t('MS00000508'), { className: 'toast_error' }) // 점검코스를 입력해주세요.
        this.$refs.jnt_chk_dtl_loc_nm.focus()
        return false
      }
      if (this.frmData.jnt_chk_dtl_loc_nm.length > 100) {
        this.$toast.show(this.$t('MS00000509'), { className: 'toast_error' }) // 점검코스는 100자를 초과할 수 없습니다.
        this.$refs.jnt_chk_dtl_loc_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.jnt_chk_note_txt) && this.frmData.jnt_chk_note_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000134'), { className: 'toast_error' }) // 비고는 1000자를 초과할 수 없습니다.
        this.$refs.jnt_chk_note_txt.focus()
        return false
      }
      if (dataProvider.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000378'), { className: 'toast_error' }) // 참가자를 등록해주세요.
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_cl_cd' })
          this.$toast.show(this.$t('MS00000380'), { className: 'toast_error' }) // 구분값을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'jnt_chk_atnd_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_nm' })
          this.$toast.show(this.$t('MS00000337'), { className: 'toast_error' }) // 성명은 50자를 초과할 수 없습니다.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_pstn_nm')) && dataProvider.getValue(i, 'jnt_chk_atnd_pstn_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_pstn_nm' })
          this.$toast.show(this.$t('MS00000338'), { className: 'toast_error' }) // 직책은 50자를 초과할 수 없습니다.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_dept_nm')) && dataProvider.getValue(i, 'jnt_chk_atnd_dept_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_dept_nm' })
          this.$toast.show(this.$t('MS00000510'), { className: 'toast_error' }) // 회사명은 50자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_emad'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_emad' })
        //   this.$toast.show('이메일 주소를 입력해주세요.', { className: 'toast_error' }) // 이메일 주소를 입력해주세요.
        //   return false
        // }
        if (dataProvider.getValue(i, 'jnt_chk_atnd_emad').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_emad' })
          this.$toast.show(this.$t('MS00000387'), { className: 'toast_error' }) // 이메일 주소는 50자를 초과할 수 없습니다.
          return false
        }
      }
      if (dataProvider2.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000511'), { className: 'toast_error' }) // 점검계획을 등록해주세요.
        return false
      }
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'jnt_chk_schd_dt'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'jnt_chk_schd_dt' })
          this.$toast.show(this.$t('MS00000512'), { className: 'toast_error' }) // 점검예정일을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'jnt_chk_ttl'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'jnt_chk_ttl' })
          this.$toast.show(this.$t('MS00000513'), { className: 'toast_error' }) // 중점 점검 사항을 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'jnt_chk_ttl').length > 50) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'jnt_chk_ttl' })
          this.$toast.show(this.$t('MS00000514'), { className: 'toast_error' }) // 중점 점검 사항은 50자를 초과할 수 없습니다.
          return false
        }
      }
      return true
    },
    async select() {
      gridView.commit()

      // jnt_chk_tgt_id를 통해 기존 데이터 조회
      await this.$axios.get('/api/v1/she/sftchk/wknscoprchktgtmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const jntChkResult = res.data.jntChkResult[0]

          this.frmData.jnt_chk_supe_emp_id = jntChkResult.jnt_chk_supe_emp_id
          this.frmData.jnt_chk_supe_emp_nm = jntChkResult.jnt_chk_supe_emp_nm
          this.frmData.jnt_chk_dtl_loc_nm = jntChkResult.jnt_chk_dtl_loc_nm
          this.frmData.jnt_chk_wkpl_loc_id = jntChkResult.jnt_chk_wkpl_loc_id
          this.frmData.jnt_chk_wkpl_loc_nm = jntChkResult.jnt_chk_wkpl_loc_nm
          this.frmData.jnt_chk_tgt_nm = jntChkResult.jnt_chk_tgt_nm
          this.frmData.jnt_chk_tgt_id = jntChkResult.jnt_chk_tgt_id
          this.frmData.jnt_chk_note_txt = jntChkResult.jnt_chk_note_txt
          this.frmData.wkpl_id = jntChkResult.wkpl_id
          this.frmData.usg_yn = jntChkResult.usg_yn
          this.frmData.chec_cl_cd = jntChkResult.chec_cl_cd

          dataProvider.setRows(res.data.jntAtndResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.atntotalcnt = gridView.getItemCount()

          dataProvider2.setRows(res.data.jntDtResult)
          gridView2.refresh()
          gridView2.setTopItem(0)
          this.chktotalcnt = gridView2.getItemCount()

          this.tempData = JSON.parse(JSON.stringify(this.frmData))
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async save() {
      gridView.commit()
      gridView2.commit()

      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
        if (dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData)) {
          this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
          return
        }
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

          temp.jnt_chk_atnd_cl_cd = dataProvider.getValue(i, 'jnt_chk_atnd_cl_cd')
          temp.jnt_chk_atnc_emp_id = dataProvider.getValue(i, 'jnt_chk_atnc_emp_id')
          temp.jnt_chk_atnd_nm = dataProvider.getValue(i, 'jnt_chk_atnd_nm')
          temp.jnt_chk_atnd_pstn_nm = dataProvider.getValue(i, 'jnt_chk_atnd_pstn_nm')
          temp.jnt_chk_atnd_dept_nm = dataProvider.getValue(i, 'jnt_chk_atnd_dept_nm')
          temp.jnt_chk_atnd_emad = dataProvider.getValue(i, 'jnt_chk_atnd_emad')
          temp.jnt_chk_atnd_sn = dataProvider.getValue(i, 'jnt_chk_atnd_sn')

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.atninsert = insertRow
        this.frmData.atnupdate = updateRow
        this.frmData.atndelete = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0 || dataProvider2.getRowStateCount('deleted') > 0 || dataProvider2.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          temp.jnt_chk_schd_dt = dataProvider2.getValue(i, 'jnt_chk_schd_dt')
          temp.jnt_chk_schd_tm = dataProvider2.getValue(i, 'jnt_chk_schd_tm')
          temp.jnt_chk_ttl = dataProvider2.getValue(i, 'jnt_chk_ttl')

          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.dtinsert = insertRow
        this.frmData.dtupdate = updateRow
        this.frmData.dtdelete = deleteRow
      }

      await this.$axios.post('/api/v1/she/sftchk/wknscoprchktgtmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          // 가져온 키 값으로 재조회
          this.schData.jnt_chk_tgt_id = res.data
          this.frmData.jnt_chk_tgt_id = res.data
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
