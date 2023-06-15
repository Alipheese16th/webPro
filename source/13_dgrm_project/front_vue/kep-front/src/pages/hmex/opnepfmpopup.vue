<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="1200px">
    <v-card class="container_pop" style="height: 800px !important">
      <v-toolbar flat class="mb-6">
        <!-- 상담이력 -->
        <v-toolbar-title>{{ $t('LB00001123') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <div class="container_detail">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 사업장 -->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 부서 -->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.org_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 사번 -->
                <div class="label_tit">{{ $t('LB00000073') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.hmex_tgt_emp_id }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 성명 -->
                <div class="label_tit">{{ $t('LB00000701') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.hmex_trgp_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 입사일 -->
                <div class="label_tit">{{ $t('LB00000851') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.jncm_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 퇴사일 -->
                <div class="label_tit">{{ $t('LB00001058') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.rsgn_dt }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 검진년도 -->
                <div class="label_tit">{{ $t('LB00001055') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.hmex_year }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 기간제 여부 -->
                <div class="label_tit">{{ $t('LB00001124') }}</div>
              </v-col>
              <v-col>
                <div class="label_con">{{ frmData.cwkr_yn }}</div>
              </v-col>
            </v-row>
          </v-container>
        </div>
        <div class="group_padd"></div>
        <div class="realgrid_container">
          <div id="opnepfm_grid01" style="width: 100%; height: 151px"></div>
        </div>
        <v-container>
          <v-row>
            <v-col cols="8" style="padding: 0">
              <div class="realgrid_container">
                <div class="sub_title">
                  <!--상담내용-->
                  <h3>{{ $t('LB00001125') }}</h3>
                </div>
                <div class="grid_header">
                  <div class="grid_header_left"></div>
                  <div>
                    <!-- 추가 -->
                    <v-btn style="bottom: 3px, color: primary" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
                    <!-- 삭제 -->
                    <v-btn style="bottom: 3px, color: primary" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
                  </div>
                </div>
                <div id="opnepfm_grid02" style="width: 100%; height: 241px"></div>
              </div>
            </v-col>
            <v-col cols="4" style="padding: 0">
              <div class="container_detail">
                <div class="sub_title"></div>
                <div class="grid_header">
                  <div class="grid_header_left"></div>
                </div>
                <div>
                  <v-container>
                    <!-- 상세내용 -->
                    <v-textarea
                      ref="hmex_cnsl_txt"
                      v-model="frmData.hmex_cnsl_txt"
                      outlined
                      rows="10"
                      row-height="30"
                      :hide-details="true"
                      dense
                      :placeholder="$t('LB00001121')"
                      maxlength="2000"
                      name="input-7-4"
                    ></v-textarea>
                  </v-container>
                </div>
              </div>
            </v-col>
          </v-row>
        </v-container>
        <filePopup ref="filepop" mkey1="openpfmpop" @close="filepopclose" />
      </div>
      <v-card-actions class="pop_btn">
        <!--저장-->
        <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import filePopup from '../common/atflgrid'

const date = new Date()
export default {
  components: {
    filePopup,
  },
  data() {
    return {
      dialog: false,
      gridViewPop: '',
      dataProviderPop: '',
      gridViewPop2: '',
      dataProviderPop2: '',
      schData: {},
      frmData: {},
      selCareYn: {
        cd: ['', 'Y', 'N'],
        cd_nm: ['SELECT', 'Y', 'N'],
      },
      current: {},
      rowNum: 0,
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    const fields = [
      {
        fieldName: 'hmex_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'deal_mtra_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_insp_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_insp_orga_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_jgmt_cd1',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_nm1',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_jgmt_cd2',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_nm2',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_jgmt_cd3',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_nm3',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_jgmt_cd4',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_nm4',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_jgmt_cd5',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_ovr_opin_nm5',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00001042'), // 검진구분
        name: 'hmex_cl_nm',
        fieldName: 'hmex_cl_nm',
        width: '90',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001041'), // 취급물질
        name: 'deal_mtra_nm',
        fieldName: 'deal_mtra_nm',
        width: '200',
        styleName: 'left',
        renderer: {
          showTooltip: true,
        },
      },
      {
        header: this.$t('LB00001092'), // 검진일자
        name: 'hmex_insp_dt',
        fieldName: 'hmex_insp_dt',
        width: '90',
      },
      {
        header: this.$t('LB00001093'), // 검진기관
        name: 'hmex_insp_orga_nm',
        fieldName: 'hmex_insp_orga_nm',
        width: '150',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001000') + '1', // 판정1
        name: 'hmex_ovr_opin_jgmt_cd1',
        fieldName: 'hmex_ovr_opin_jgmt_cd1',
        width: '80',
      },
      {
        header: this.$t('LB00001095') + '1', // 소견1
        name: 'hmex_ovr_opin_nm1',
        fieldName: 'hmex_ovr_opin_nm1',
        width: '80',
      },
      {
        header: this.$t('LB00001000') + '2', // 판정2
        name: 'hmex_ovr_opin_jgmt_cd2',
        fieldName: 'hmex_ovr_opin_jgmt_cd2',
        width: '80',
      },
      {
        header: this.$t('LB00001095') + '2', // 소견2
        name: 'hmex_ovr_opin_nm2',
        fieldName: 'hmex_ovr_opin_nm2',
        width: '80',
      },
      {
        header: this.$t('LB00001000') + '3', // 판정3
        name: 'hmex_ovr_opin_jgmt_cd3',
        fieldName: 'hmex_ovr_opin_jgmt_cd3',
        width: '80',
      },
      {
        header: this.$t('LB00001095') + '3', // 소견3
        name: 'hmex_ovr_opin_nm3',
        fieldName: 'hmex_ovr_opin_nm3',
        width: '80',
      },
      {
        header: this.$t('LB00001000') + '4', // 판정4
        name: 'hmex_ovr_opin_jgmt_cd4',
        fieldName: 'hmex_ovr_opin_jgmt_cd4',
        width: '80',
      },
      {
        header: this.$t('LB00001095') + '4', // 소견4
        name: 'hmex_ovr_opin_nm4',
        fieldName: 'hmex_ovr_opin_nm4',
        width: '80',
      },
      {
        header: this.$t('LB00001000') + '5', // 판정5
        name: 'hmex_ovr_opin_jgmt_cd5',
        fieldName: 'hmex_ovr_opin_jgmt_cd5',
        width: '80',
      },
      {
        header: this.$t('LB00001095') + '5', // 소견5
        name: 'hmex_ovr_opin_nm5',
        fieldName: 'hmex_ovr_opin_nm5',
        width: '80',
      },
    ]
    this.dataProviderPop = new LocalDataProvider(false)
    this.dataProviderPop.setFields(fields)
    this.gridViewPop = new GridView('opnepfm_grid01')
    this.gridViewPop.setDataSource(this.dataProviderPop)
    this.gridViewPop.setColumns(columns)
    this.gridViewPop.setFooters({ visible: false })
    this.gridViewPop.setStateBar({ visible: false })
    this.gridViewPop.setCheckBar({ visible: false })

    this.gridViewPop.header.height = 39
    this.gridViewPop.displayOptions.rowHeight = 40
    this.gridViewPop.footer.height = 40
    this.gridViewPop.displayOptions.fitStyle = 'fill'
    this.gridViewPop.displayOptions.selectionStyle = 'block'
    this.gridViewPop.editOptions.editable = false

    const fields2 = [
      {
        fieldName: 'hmex_cnsl_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_cnsl_schd_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'drug_care_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cnsl_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cnsl_atfl_cnt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'hmex_cnsl_txt',
        dataType: ValueType.TEXT,
      },
    ]

    const columns2 = [
      {
        header: this.$t('LB00001116'), // 상담일자
        name: 'hmex_cnsl_dt',
        fieldName: 'hmex_cnsl_dt',
        width: '100',
        styles: {
          datetimeFormat: 'yyyy-MM-dd',
        },
        editor: {
          type: 'date',
          mask: {
            editMask: '9999-99-99',
            placeHolder: 'yyyy-MM-dd',
            includedFormat: true,
          },
          datetimeFormat: 'yyyy-MM-dd',
        },
        styleCallback(grid, dataCell) {
          const ret = {}

          if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
            ret.editable = true
          } else {
            ret.editable = false
          }

          return ret
        },
      },
      {
        header: this.$t('LB00001119'), // 다음 상담일
        name: 'hmex_cnsl_schd_dt',
        fieldName: 'hmex_cnsl_schd_dt',
        width: '100',
        styles: {
          datetimeFormat: 'yyyy-MM-dd',
        },
        editor: {
          type: 'date',
          mask: {
            editMask: '9999-99-99',
            placeHolder: 'yyyy-MM-dd',
            includedFormat: true,
          },
          datetimeFormat: 'yyyy-MM-dd',
        },
      },
      {
        header: this.$t('LB00001120'), // 약물치료 여부
        name: 'drug_care_yn',
        fieldName: 'drug_care_yn',
        width: '80',
        values: this.selCareYn.cd,
        labels: this.selCareYn.cd_nm,
        lookupDisplay: true,
        editor: {
          type: 'list',
          domainOnly: true,
          textReadOnly: true,
        },
      },
      {
        header: this.$t('LB00001121'), // 상세내용
        name: 'hmex_cnsl_txt',
        fieldName: 'hmex_cnsl_txt',
        width: '80',
        editable: false,
      },
      {
        header: this.$t('LB00001122'), // 상담첨부
        name: 'cnsl_atfl_cnt',
        fieldName: 'cnsl_atfl_cnt',
        width: '80',
        button: 'action',
        editable: false,
      },
    ]
    this.dataProviderPop2 = new LocalDataProvider(false)
    this.dataProviderPop2.setFields(fields2)
    this.gridViewPop2 = new GridView('opnepfm_grid02')
    this.gridViewPop2.setDataSource(this.dataProviderPop2)
    this.gridViewPop2.setColumns(columns2)
    this.gridViewPop2.setFooters({ visible: false })
    this.gridViewPop2.setStateBar({ visible: false })
    this.gridViewPop2.setCheckBar({ visible: true })

    this.gridViewPop2.header.height = 39
    this.gridViewPop2.displayOptions.rowHeight = 40
    this.gridViewPop2.footer.height = 40
    this.gridViewPop2.displayOptions.fitStyle = 'fill'
    this.gridViewPop2.displayOptions.selectionStyle = 'block'
    this.gridViewPop2.editOptions.editable = true

    this.gridViewPop2.onCellButtonClicked = function (grid, index, column) {
      this.rowNum = index.dataRow
      if (index.column === 'cnsl_atfl_cnt') {
        this.schData.atfile_no = this.gridViewPop2.getValue(index.itemIndex, 'cnsl_atfl_no')
        this.$refs.filepop.open(this.schData)
      }
    }.bind(this)

    this.gridViewPop2.onCellClicked = function (grid, clickData) {
      if (clickData.cellType === 'header') {
        this.current = {}
        this.$set(this.frmData, 'hmex_cnsl_txt', '')
        return
      }
      if (clickData.column === 'hmex_cnsl_txt') {
        if (this.current) {
          this.gridViewPop2.setValue(this.current.dataRow, 'hmex_cnsl_txt', this.frmData.hmex_cnsl_txt)
          this.current = grid.getCurrent()
        } else {
          this.current = grid.getCurrent()
        }
        this.$set(this.frmData, 'hmex_cnsl_txt', this.gridViewPop2.getValue(clickData.itemIndex, 'hmex_cnsl_txt'))
        this.$refs.hmex_cnsl_txt.focus()
      } else {
        if (this.current) {
          this.gridViewPop2.setValue(this.current.dataRow, 'hmex_cnsl_txt', this.frmData.hmex_cnsl_txt)
        }
        this.current = {}
        this.$set(this.frmData, 'hmex_cnsl_txt', '')
      }
    }.bind(this)

    this.gridViewPop2.onCellEdited = function (grid, itemIndex, row, field) {
      this.frmData.ckFlag = true
    }.bind(this)
  },
  methods: {
    open(empNo, hmexYear) {
      this.frmData.emp_no = empNo + '@' + hmexYear
      this.search()
      this.dialog = true
    },
    search() {
      this.$axios.$get(`/api/v1/she/hlth/hmexrslt/${this.frmData.emp_no}`).then((res) => {
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.org_nm = res.org_nm
        this.frmData.hmex_trgp_nm = res.hmex_trgp_nm
        this.frmData.hmex_tgt_emp_id = res.hmex_tgt_emp_id
        this.frmData.hmex_year = res.hmex_year
        this.frmData.jncm_dt = res.jncm_dt
        this.frmData.rsgn_dt = res.rsgn_dt
        this.frmData.cwkr_yn = res.cwkr_yn
        this.$set(this.frmData, 'wkpl_nm', res.wkpl_nm)

        this.dataProviderPop.setRows(res.rslt_list)
        this.gridViewPop.refresh()
        this.gridViewPop.setTopItem(0)

        this.search2()
      })
    },
    search2() {
      const data = {}
      data.type = 'dtl'
      data.hmex_year = this.frmData.hmex_year
      data.hmex_tgt_emp_id = this.frmData.hmex_tgt_emp_id
      this.$axios.$get(`/api/v1/she/hlth/abfr`, { params: data }).then((res) => {
        this.dataProviderPop2.setRows(res)
        this.gridViewPop2.refresh()
        this.gridViewPop2.setTopItem(0)
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
    add() {
      if (this.dataProviderPop2.getRowCount() > 0 && this.dataProviderPop2.getRowState(0) === 'created') {
        return
      }
      this.frmData.ckFlag = true
      this.totalcnt++
      this.gridViewPop2.commit()
      if (this.current) {
        this.gridViewPop2.setValue(this.current.dataRow, 'hmex_cnsl_txt', this.frmData.hmex_cnsl_txt)
        this.current = {}
        this.$set(this.frmData, 'hmex_cnsl_txt', '')
      }
      this.dataProviderPop2.insertRow(0, {
        hmex_cnsl_dt: this.get_date_str_gubun(date, '-'),
        hmex_cnsl_schd_dt: '',
        drug_care_yn: '',
        cnsl_atfl_cnt: '0',
        cnsl_atfl_no: '0',
        hmex_cnsl_txt: '',
      })
      // this.gridViewPop2.setCurrent({ dataRow: 0, fieldName: 'hmex_cnsl_dt' })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      this.gridViewPop2.commit()
      for (let i = 0; i < this.dataProviderPop2.getRowCount(); i++) {
        if (this.dataProviderPop2.getRowState(i) === 'createAndDeleted' || this.dataProviderPop2.getRowState(i) === 'deleted') {
          continue
        }

        // if (this.isEmpty(this.dataProviderPop2.getValue(i, 'hmex_cnsl_dt'))) {
        //   this.gridViewPop2.setCurrent({ dataRow: i, fieldName: 'hmex_cnsl_dt' })
        //   this.$toast.show(this.$t('MS00000564'), { className: 'toast_error' }) // 상담일자를 입력해주세요.
        //   return false
        // }

        // if (this.isEmpty(this.dataProviderPop2.getValue(i, 'hmex_cnsl_schd_dt'))) {
        //   this.gridViewPop2.setCurrent({ dataRow: i, fieldName: 'hmex_cnsl_schd_dt' })
        //   this.$toast.show(this.$t('MS00000565'), { className: 'toast_error' }) // 다음 상담일 입력해주세요.
        //   return false
        // }

        if (this.dataProviderPop2.getValue(i, 'hmex_cnsl_dt') >= this.dataProviderPop2.getValue(i, 'hmex_cnsl_schd_dt')) {
          this.gridViewPop2.setCurrent({ dataRow: i, fieldName: 'hmex_cnsl_schd_dt' })
          this.$toast.show(this.$t('MS00000566'), { className: 'toast_error' }) // 다음 상담일은 상담일자보다 이전이거나 같으면 안됩니다.
          return false
        }

        // if (this.isEmpty(this.dataProviderPop2.getValue(i, 'drug_care_yn'))) {
        //   this.gridViewPop2.setCurrent({ dataRow: i, fieldName: 'drug_care_yn' })
        //   this.$toast.show(this.$t('MS00000567'), { className: 'toast_error' }) // 약물치료 여부를 입력해주세요.
        //   return false
        // }

        if (i > 0 && this.dataProviderPop2.getRowState(0) === 'created') {
          if (this.dataProviderPop2.getValue(0, 'hmex_cnsl_dt') === this.dataProviderPop2.getValue(i, 'hmex_cnsl_dt')) {
            this.gridViewPop2.setCurrent({ dataRow: 0, fieldName: 'hmex_cnsl_dt' })
            this.$toast.show(this.$t('MS00000568'), { className: 'toast_error' }) // 동일한 상담일자가 존재합니다.
            return false
          }
        }
      }
      return true
    },
    async save() {
      this.gridViewPop2.commit()
      if (this.current) {
        this.gridViewPop2.setValue(this.current.dataRow, 'hmex_cnsl_txt', this.frmData.hmex_cnsl_txt)
        this.current = {}
        this.$set(this.frmData, 'hmex_cnsl_txt', '')
      }
      if (!this.validation()) return
      // if (!this.frmData.ckFlag) {
      //   this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      //   return
      // }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.insert_list = []
      this.frmData.update_list = []
      for (let i = 0; i < this.dataProviderPop2.getRowCount(); i++) {
        if (this.dataProviderPop2.getRowState(i) === 'createAndDeleted' || this.dataProviderPop2.getRowState(i) === 'deleted') {
          continue
        }

        const obj = {}
        obj.hmex_year = this.frmData.hmex_year
        obj.wkpl_id = this.frmData.wkpl_id
        obj.hmex_tgt_emp_id = this.frmData.hmex_tgt_emp_id
        obj.hmex_cnsl_dt = this.dataProviderPop2.getValue(i, 'hmex_cnsl_dt')
        obj.hmex_cnsl_schd_dt = this.dataProviderPop2.getValue(i, 'hmex_cnsl_schd_dt')
        obj.drug_care_yn = this.dataProviderPop2.getValue(i, 'drug_care_yn')
        obj.cnsl_atfl_no = this.dataProviderPop2.getValue(i, 'cnsl_atfl_no')
        obj.hmex_cnsl_txt = this.dataProviderPop2.getValue(i, 'hmex_cnsl_txt')

        if (this.dataProviderPop2.getRowState(i) === 'created') {
          this.frmData.insert_list.push(obj)
        } else if (this.dataProviderPop2.getRowState(i) === 'updated') {
          this.frmData.update_list.push(obj)
        }
      }

      this.$axios.post('/api/v1/she/hlth/abfr', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.ckFlag = false
          this.$set(this.frmData, 'hmex_cnsl_txt', '')
          this.search2()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async del() {
      const items = this.gridViewPop2.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000526'), { className: 'toast_error' }) // 삭제할 데이터가 없습니다.
        return
      }

      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?(KO)
      if (!res) {
        return
      }

      let id = ''
      for (let i = 0; i < items.length; i++) {
        if (this.dataProviderPop2.getRowState(items[i]) === 'created') {
          continue
        }
        id = this.frmData.hmex_year + '-' + this.frmData.hmex_tgt_emp_id + '-' + String(this.dataProviderPop2.getValue(items[i], 'hmex_cnsl_dt')).replace(/-/gi, '')
        console.log('id : ', id) // eslint-disable-line no-console
        await this.$axios.delete(`/api/v1/she/hlth/abfr/${id}`).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
      this.$set(this.frmData, 'hmex_cnsl_txt', '')
      this.search2()
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.gridViewPop2.commit()
      this.dialog = false
      this.frmData = {}
      this.current = {}
    },
    filepopclose(returnData) {
      this.gridViewPop2.commit()
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.dataProviderPop2.setValue(this.rowNum, 'cnsl_atfl_no', returnData.atfile_no)
        this.dataProviderPop2.setValue(this.rowNum, 'cnsl_atfl_cnt', returnData.file_cnt)
      }
      // this.search()
    },
  },
}
</script>
