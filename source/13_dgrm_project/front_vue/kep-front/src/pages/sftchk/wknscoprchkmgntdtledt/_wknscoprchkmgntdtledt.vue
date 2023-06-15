<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 합동점검 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001044') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 합동점검 관리-->
      <h2>{{ $t('LB00001044') }}</h2>
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
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
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
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ frmData.jnt_chk_tgt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--점검 일시-->
                <div class="label_tit essn">{{ $t('LB00000648') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="jnt_chk_dt"
                      v-model="frmData.jnt_chk_dt"
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
                  <v-date-picker v-model="frmData.jnt_chk_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-select
                  ref="jnt_chk_hm_h"
                  v-model="frmData.jnt_chk_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="jnt_chk_hm_s"
                  v-model="frmData.jnt_chk_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검코스-->
                <div class="label_tit">{{ $t('LB00001013') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.jnt_chk_dtl_loc_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--특이사항-->
                <div class="label_tit">{{ $t('LB00000994') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.jnt_chk_note_txt }}</div>
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
    <div class="container_detail">
      <div class="sub_title">
        <h3>{{ $t('LB00000646') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="1">
            <!--점검내용-->
            <div class="label_tit essn">{{ $t('LB00000901') }}</div>
          </v-col>
          <v-col cols="11">
            <v-textarea ref="jnt_chk_rslt_txt" v-model="frmData.jnt_chk_rslt_txt" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="1">
            <!--첨부파일-->
            <div class="label_tit essn">{{ $t('LB00000121') }}</div>
          </v-col>
          <v-col cols="11">
            <file-upload ref="upload1" :multiple="true" @onDrop="chkFile()" />
          </v-col>
        </v-row>
      </v-container>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" @click="aprv">{{ $t('LB00000763') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="edtpop" mkey1="wknscoprchkmgntdtledt1" mkey2="wknscoprchkmgntdtledt2" @close="popClose" />
    <rgnslct ref="edtpop2" mkey="wknscoprchkmgntdtledt1" @close="popLocClose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let date = new Date()

export default {
  meta: {
    title: '작업장 합동점검 대상 관리',
    key(route) {
      return `/sftchk/wknscoprchkmgntlist/${route.params.catalog}`
    },
  },
  components: {
    popup,
    rgnslct,
    FileUpload,
    // quillEditor,
  },
  data() {
    return {
      menu1: false,
      menu2: false,
      atntotalcnt: 0,
      selSH00000022: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['SH00000022', 'SH00000074'],
      codes: {
        SH00000022: [{ cd: '', cd_nm: '' }],
      },
      schData: {
        jnt_chk_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        wkpl_nm: '',
        jnt_chk_tgt_id: '',
        jnt_chk_id: '',
        jnt_chk_supe_emp_id: '',
        jnt_chk_supe_emp_nm: '',
        jnt_chk_wkpl_loc_id: '',
        jnt_chk_wkpl_loc_nm: '',
        jnt_chk_dtl_loc_nm: '',
        jnt_chk_tgt_nm: '',
        jnt_chk_note_txt: '',
        chec_cl_cd: '',
        lsch_nm: '',
        usg_yn: '',
      },
      mm5: [
        { cd: '00', cd_nm: '00' },
        { cd: '05', cd_nm: '05' },
        { cd: '10', cd_nm: '10' },
        { cd: '15', cd_nm: '15' },
        { cd: '20', cd_nm: '20' },
        { cd: '25', cd_nm: '25' },
        { cd: '30', cd_nm: '30' },
        { cd: '35', cd_nm: '35' },
        { cd: '40', cd_nm: '40' },
        { cd: '45', cd_nm: '45' },
        { cd: '50', cd_nm: '50' },
        { cd: '55', cd_nm: '55' },
      ],
      menu: false,
      fileModify: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001044')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (
      dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
      JSON.stringify(this.tempData) !== JSON.stringify(this.frmData) ||
      this.fileModify === true
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
        usg_yn: '',
        cmpy_cd: '',
      }
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
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

        // 권한 체크. 같은 부서 여부 조회
        if (!this.isEmpty(this.$route.params.wknscoprchkmgntdtledt)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'JCHK', jnt_chk_id: this.$route.params.wknscoprchkmgntdtledt } }).then((res) => {
            if (res === 'Success') {
              this.schData.jnt_chk_id = this.$route.params.wknscoprchkmgntdtledt
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000169'), { className: 'toast_error' }) // 권한이 없습니다.
              this.$router.push({
                name: 'sftchk-wknscoprchkmgntlist',
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
    allowedStep: (m) => m % 5 === 0,
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
      this.$refs.edtpop2.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else if (this.rowChk) {
        returnData.forEach((i) => {
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
    chkFile() {
      this.fileModify = true
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
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    goList() {
      this.$router.push({
        name: 'sftchk-wknscoprchkmgntlist',
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
    validation() {
      gridView.commit()

      if (this.isEmpty(this.frmData.jnt_chk_supe_emp_id)) {
        this.$toast.show(this.$t('MS00000269'), { className: 'toast_error' }) // 점검 담당자를 선택해주세요.
        this.$refs.jnt_chk_supe_emp_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.jnt_chk_dt)) {
        this.$toast.show(this.$t('MS00000270'), { className: 'toast_error' }) // 점검 일자를 입력해주세요.
        this.$refs.jnt_chk_dt.focus()
        return false
      }
      if (this.isEmpty(this.frmData.jnt_chk_hm_h || this.isEmpty(this.frmData.jnt_chk_hm_s))) {
        this.$toast.show(this.$t('MS00000271'), { className: 'toast_error' }) // 점검 시간을 입력해주세요.
        this.$refs.jnt_chk_hm_h.focus()
        return false
      }
      if (dataProvider.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000378'), { className: 'toast_error' }) // 참석자를 등록해주세요.
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
        if (this.isEmpty(dataProvider.getValue(i, 'jnt_chk_atnd_emad'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_emad' })
          this.$toast.show(this.$t('MS00000287'), { className: 'toast_error' }) // 이메일 주소를 입력해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'jnt_chk_atnd_emad').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'jnt_chk_atnd_emad' })
          this.$toast.show(this.$t('MS00000387'), { className: 'toast_error' }) // 이메일 주소는 50자를 초과할 수 없습니다.
          return false
        }
      }
      if (this.isEmpty(this.frmData.jnt_chk_rslt_txt)) {
        this.$toast.show(this.$t('MS00000406'), { className: 'toast_error' }) // 점검 내용을 입력해주세요.
        this.$refs.jnt_chk_rslt_txt.focus()
        return false
      }
      if (this.frmData.jnt_chk_rslt_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000256'), { className: 'toast_error' }) // 점검 내용은 1000자를 초과할 수 없습니다.
        this.$refs.jnt_chk_rslt_txt.focus()
        return false
      }
      return true
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    async select() {
      gridView.commit()

      // jnt_chk_id를 통해 기존 데이터 조회
      await this.$axios.get('/api/v1/she/sftchk/wknscoprchkmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const jntChkResult = res.data.jntChkResult[0]

          this.frmData.jnt_chk_id = jntChkResult.jnt_chk_id
          this.frmData.jnt_chk_supe_emp_id = jntChkResult.jnt_chk_supe_emp_id
          this.frmData.jnt_chk_supe_emp_nm = jntChkResult.jnt_chk_supe_emp_nm
          this.frmData.jnt_chk_dtl_loc_nm = jntChkResult.jnt_chk_dtl_loc_nm
          this.frmData.jnt_chk_hm_h = jntChkResult.jnt_chk_hm.substring(0, 2)
          this.frmData.jnt_chk_hm_s = jntChkResult.jnt_chk_hm.substring(2, 4)
          this.frmData.jnt_chk_wkpl_loc_id = jntChkResult.jnt_chk_wkpl_loc_id
          this.frmData.jnt_chk_wkpl_loc_nm = jntChkResult.jnt_chk_wkpl_loc_nm
          this.frmData.jnt_chk_tgt_nm = jntChkResult.jnt_chk_tgt_nm
          this.frmData.jnt_chk_tgt_id = jntChkResult.jnt_chk_tgt_id
          this.frmData.jnt_chk_note_txt = jntChkResult.jnt_chk_note_txt
          this.frmData.wkpl_id = jntChkResult.wkpl_id
          this.frmData.wkpl_nm = jntChkResult.wkpl_nm
          this.frmData.upt_emp_no = jntChkResult.upt_emp_no
          this.frmData.lsch_nm = jntChkResult.lsch_nm
          this.frmData.jnt_chk_rslt_txt = jntChkResult.jnt_chk_rslt_txt
          if (!this.isEmpty(jntChkResult.jnt_chk_dt)) {
            this.frmData.jnt_chk_dt = jntChkResult.jnt_chk_dt.substring(0, 4) + '-' + jntChkResult.jnt_chk_dt.substring(4, 6) + '-' + jntChkResult.jnt_chk_dt.substring(6, 8)
          }

          if (!this.isEmpty(jntChkResult.jnt_chk_atfl_no)) {
            this.$axios.$get(`/api/v1/common/file/${jntChkResult.jnt_chk_atfl_no}`).then((data) => {
              if (data.length > 0) {
                this.setFiles(data)
              }
            })
          }

          this.fileModify = false // 첨부파일 수정 여부 초기화

          dataProvider.setRows(res.data.jntAtndResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.atntotalcnt = gridView.getItemCount()

          this.tempData = JSON.parse(JSON.stringify(this.frmData))
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
    },
    async save() {
      gridView.commit()

      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.tempData) && this.fileModify === false) {
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

          temp.jnt_chk_atnd_cl_cd = dataProvider.getValue(i, 'jnt_chk_atnd_cl_cd')
          temp.jnt_chk_atnc_emp_id = dataProvider.getValue(i, 'jnt_chk_atnc_emp_id')
          temp.jnt_chk_atnd_nm = dataProvider.getValue(i, 'jnt_chk_atnd_nm')
          temp.jnt_chk_atnd_dept_nm = dataProvider.getValue(i, 'jnt_chk_atnd_dept_nm')
          temp.jnt_chk_atnd_pstn_nm = dataProvider.getValue(i, 'jnt_chk_atnd_pstn_nm')
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
      // 파일 업로드
      await this.$refs.upload1.uploadStart(true).then((data) => {
        if (!this.isEmptyFile(data)) {
          this.frmData.jnt_chk_atfl_no = data + ''
          this.$axios.$get(`/api/v1/common/file/${data}`).then((data) => {
            this.setFiles(data)
          })
        }
      })

      this.frmData.jnt_chk_prst_cd = '20'
      await this.$axios.post('/api/v1/she/sftchk/wknscoprchkmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          // 가져온 키 값으로 재조회
          this.schData.jnt_chk_id = res.data
          this.frmData.jnt_chk_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async aprv() {
      gridView.commit()

      if (!this.validation()) {
        return
      }

      const res = await this.$confirm(this.$t('MS00000092')) // 결제하시겠습니까?
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
          temp.jnt_chk_atnd_dept_nm = dataProvider.getValue(i, 'jnt_chk_atnd_dept_nm')
          temp.jnt_chk_atnd_pstn_nm = dataProvider.getValue(i, 'jnt_chk_atnd_pstn_nm')
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
      // 파일 업로드
      await this.$refs.upload1.uploadStart(true).then((data) => {
        if (!this.isEmptyFile(data)) {
          this.frmData.jnt_chk_atfl_no = data + ''
          this.$axios.$get(`/api/v1/common/file/${data}`).then((data) => {
            this.setFiles(data)
          })
        } else {
          this.frmData.jnt_chk_atfl_no = '0'
        }
      })

      this.frmData.jnt_chk_prst_cd = '20'
      await this.$axios.post('/api/v1/she/sftchk/wknscoprchkmgntaprv', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        if (res.data !== 'Fail') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empno
          const url = config.default.approvalUrl5 + aprvno + empno
          window.open(url, 'jnt-appr', options)
          this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.tempData = JSON.parse(JSON.stringify(this.frmData))
          this.$router.push({
            name: `sftchk-wknscoprchkmgntdtllup-wknscoprchkmgntdtllup`,
            params: {
              wknscoprchkmgntdtllup: this.$route.params.wknscoprchkmgntdtledt,
              tmpSchData: this.tmpSchData,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
  },
}
</script>
<style></style>
