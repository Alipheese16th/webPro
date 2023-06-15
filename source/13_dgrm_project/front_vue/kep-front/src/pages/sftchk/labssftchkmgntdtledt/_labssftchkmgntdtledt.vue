<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 연구실 일상점검 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001104') }}</span>
    </div>
    <div class="work_title">
      <!-- 연구실 일상점검 -->
      <h2>{{ $t('LB00001104') }}</h2>
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
              <v-col cols="1" style="padding: 0px">
                <!--점검월-->
                <div class="label_tit essn">{{ $t('LB00000789') }}</div>
              </v-col>
              <v-col cols="3" style="padding: 0px">
                <v-menu v-model="menu" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
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
                  <v-date-picker v-model="frmData.labs_sft_chk_yrmn" type="month" no-title @input="menu = false"> </v-date-picker>
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
                <!--점검명-->
                <div class="label_tit essn">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="7">
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
              <v-col cols="1">
                <!--최종 수정일-->
                <div class="label_tit">{{ $t('LB00000939') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
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
                  height="60"
                  class="mt-2"
                  dense
                  :placeholder="$t('LB00000033')"
                  maxlength="2000"
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--안전점검대상-->
        <h3>{{ $t('LB00001113') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="container_detail">
      <v-container>
        <v-row>
          <v-col cols="1">
            <!--비고-->
            <div class="label_tit">{{ $t('LB00000095') }}</div>
          </v-col>
          <v-col cols="11">
            <v-textarea ref="labs_sft_chk_note_txt" v-model="frmData.labs_sft_chk_note_txt" outlined single-line :hide-details="true" dense :no-resize="true"></v-textarea>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <deptslct ref="pop" mkey="rgltedt" @close="popClose" />
    <filePopup ref="filepop" mkey1="labssftchkmgntdtledtatflgrid1" @close="filepopclose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import deptslct from '../../common/deptslct'
import filePopup from '../../common/atflgrid.vue'
// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider
let date = new Date()

export default {
  meta: {
    title: '연구실 일상점검',
    key(route) {
      return `/sftchk/labssftchkmgntlist/${route.params.catalog}`
    },
  },
  components: {
    deptslct,
    filePopup,
  },
  data() {
    return {
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        ST00000002p: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      menu: false,
      totalcnt: '',
      chkRowVal: 0,
      schData: {
        labs_sft_chk_id: '',
        date: '',
      },
      tempData: {},
      tmpSchData: {},
      frmData: {
        wkpl_id: '',
        labs_sft_chk_id: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_nm: '',
        labs_sft_chk_txt: '',
        labs_sft_chk_note_txt: '',
        labs_sft_chk_sn: '',
        labs_rspn_org_cd: '',
        labs_mgnt_emp_nm: '',
        mgnt_labs_cnt: '',
        labs_sft_chk_tgt_atfl_no: '',
        labs_sft_chk_exct_yn: '',
      },
      userPopGb: '',
      wkplId: '',
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001104')
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

    // date.setDate(date.getMonth())
    this.frmData.labs_sft_chk_yrmn = this.get_date_str_gubun(date, '-')

    if (this.$route.params.labssftchkmgntdtledt !== null && this.$route.params.labssftchkmgntdtledt !== undefined) {
      // eslint-disable-next-line prefer-promise-reject-errors
    } else {
      this.frmData.wkpl_id = user.wkpl_id
    }

    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_nm: '',
        wkpl_id: '',
        labs_sft_chk_yrmn: '',
        labs_sft_chk_nm: '',
        fin_yn: '',
        labs_sft_chk_note_txt: '',
      }
    }
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
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
      const cmpy = user.cmpn_cd
      this.codChg(cmpy)
    })
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempWkpl = []
      res.ST00000002.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.ST00000002p = tempWkpl
      this.codes.ST00000002p.unshift({ value: '', label: 'SELECT' })

      // 그리드 세팅
      const fields = [
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_rspn_org_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_rspn_org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_mgnt_emp_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mgnt_labs_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_tgt_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_tgt_atfl_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'labs_sft_chk_exct_yn',
          dataType: ValueType.TEXT,
        },
      ]
      const columns = [
        {
          header: this.$t('LB00000757'), // 순번
          name: 'labs_sft_chk_sn',
          fieldName: 'labs_sft_chk_sn',
          width: '70',
        },
        {
          header: this.$t('LB00000071'), // 부서
          name: 'labs_rspn_org_nm',
          fieldName: 'labs_rspn_org_nm',
          width: '70',
          styleName: 'center',
        },
        {
          header: this.$t('LB00001114'), // 연구책임자명(관리감독자)
          name: 'labs_mgnt_emp_nm',
          fieldName: 'labs_mgnt_emp_nm',
          width: '150',
          styleName: 'center',
        },
        {
          header: this.$t('LB00001115'), // 관리 실험실 개수
          name: 'mgnt_labs_cnt',
          fieldName: 'mgnt_labs_cnt',
          width: '50',
          styleName: 'center',
        },
        {
          header: this.$t('LB00001117'), // 안전점검결과표
          name: 'labs_sft_chk_tgt_atfl_cnt',
          fieldName: 'labs_sft_chk_tgt_atfl_cnt',
          width: '50',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
        // Y: 파란색, N: 빨간색
        {
          header: this.$t('LB00000846'), // 점검 이행여부
          name: 'labs_sft_chk_exct_yn',
          fieldName: 'labs_sft_chk_exct_yn',
          width: '100',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            editable: false,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const chkyn = grid.getValue(dataCell.index.itemIndex, 'labs_sft_chk_exct_yn')
            if (chkyn !== 'N') {
              return (ret.styleName = 'rgsample-blue-column')
            } else return (ret.styleName = 'rgsample-red-column')
          },
        },
      ]
      // 그리드 1 시작
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
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
      gridView.setColumnProperty('labs_sft_chk_sn', 'visible', false)

      // 기존 데이터 조회
      if (!this.isEmpty(this.$route.params.labssftchkmgntdtledt)) {
        this.schData.labs_sft_chk_id = this.$route.params.labssftchkmgntdtledt
        this.select()
      }

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        this.chkRowVal = itemIndex.dataRow // row index 값
        if (itemIndex.column === 'labs_sft_chk_tgt_atfl_cnt') {
          const atfileNo = gridView.getValue(itemIndex.itemIndex, 'labs_sft_chk_tgt_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData)
        }
      }.bind(this)

      gridView.onCellClicked = function (grid, clickData) {
        // 부서 클릭
        if (clickData.column === 'labs_rspn_org_nm') {
          grid.commit()
          const schData = {}
          this.$refs.pop.open(schData, false)
        }
      }.bind(this)
    })
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      return sYear + gubun + sMonth
    },
    add() {
      gridView.commit()
      dataProvider.addRow({
        wkpl_id: '',
        labs_sft_chk_sn: '',
        labs_rspn_org_cd: '',
        labs_rspn_org_nm: '',
        labs_mgnt_emp_nm: '',
        labs_sft_chk_tgt_atfl_no: '',
        labs_sft_chk_tgt_atfl_cnt: 0,
        mgnt_labs_cnt: '',
        labs_sft_chk_exct_yn: '',
      })
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    filepopclose(returnData) {
      gridView.commit()
      console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      }
      dataProvider.setValue(this.chkRowVal, 'labs_sft_chk_tgt_atfl_no', returnData.atfile_no)
      dataProvider.setValue(this.chkRowVal, 'labs_sft_chk_tgt_atfl_cnt', returnData.file_cnt)
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
    popOpen(val) {
      this.userPopGb = val
      this.$refs.pop.open(this.frmData, false)
    },
    // 부서 데이터 가져오기
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        const row = gridView.getCurrent().dataRow
        const values = {
          labs_rspn_org_cd: returnData[0].dept_cd,
          labs_rspn_org_nm: returnData[0].dept_nm,
        }
        dataProvider.updateRow(row, values)
      }
    },
    saveValidate() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000553'), { className: 'toast_error' }) // 사업장명을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_yrmn)) {
        this.$toast.show(this.$t('MS00000559'), { className: 'toast_error' }) // 점검월을 입력해주세요.
        // this.$refs.labs_sft_chk_yrmn.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_nm)) {
        this.$toast.show(this.$t('MS00000275'), { className: 'toast_error' }) // 점검명을 입력해주세요.
        this.$refs.labs_sft_chk_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.labs_sft_chk_txt)) {
        this.$toast.show(this.$t('MS00000406'), { className: 'toast_error' }) // 점검내용을 입력해주세요.
        this.$refs.labs_sft_chk_txt.focus()
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'labs_rspn_org_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'labs_rspn_org_nm' })
          this.$toast.show(this.$t('MS00000031'), { className: 'toast_error' }) // 부서를 입력해주세요
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'labs_mgnt_emp_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'labs_mgnt_emp_nm' })
          this.$toast.show(this.$t('MS00000560'), { className: 'toast_error' }) // 연구책임자명(관리감독자)을 입력해주세요
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'mgnt_labs_cnt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'mgnt_labs_cnt' })
          this.$toast.show(this.$t('MS00000561'), { className: 'toast_error' }) // 관리 실험실 개수를 입력해주세요
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'labs_sft_chk_tgt_atfl_cnt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'labs_sft_chk_tgt_atfl_cnt' })
          this.$toast.show(this.$t('MS00000562'), { className: 'toast_error' }) // 안전점검결과표를 입력해주세요
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'labs_sft_chk_exct_yn'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'labs_sft_chk_exct_yn' })
          this.$toast.show(this.$t('MS00000563'), { className: 'toast_error' }) // 점검 이행여부를 입력해주세요
          return false
        }
      }
      return true
    },
    async select() {
      gridView.commit()

      await this.$axios.get('/api/v1/she/sftchk/labssftchkmgnt', { params: this.schData }).then((res) => {
        if (res.data !== 'Fail' && res.data !== 'READ') {
          const labsChkResult = res.data.labsChkResult[0]

          this.frmData.wkpl_id = labsChkResult.wkpl_id
          this.frmData.labs_sft_chk_id = labsChkResult.labs_sft_chk_id
          this.frmData.labs_sft_chk_yrmn = labsChkResult.labs_sft_chk_yrmn
          this.frmData.labs_sft_chk_nm = labsChkResult.labs_sft_chk_nm
          this.frmData.labs_sft_chk_txt = labsChkResult.labs_sft_chk_txt
          this.frmData.labs_sft_chk_note_txt = labsChkResult.labs_sft_chk_note_txt
          this.frmData.lsch_nm = labsChkResult.lsch_nm
          this.frmData.upt_dt = labsChkResult.upt_dt

          dataProvider.setRows(res.data.labsChkTgtResult)
          gridView.refresh()
          gridView.setTopItem(0)
          this.totalcnt = gridView.getItemCount()

          this.tempData = JSON.parse(JSON.stringify(this.frmData))
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

      // Validation 체크
      if (!this.saveValidate()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.labs_sft_chk_yrmn = this.frmData.labs_sft_chk_yrmn.replace('-', '')
      // 그리드 영역

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          temp.labs_sft_chk_sn = dataProvider.getValue(i, 'labs_sft_chk_sn')
          temp.labs_rspn_org_cd = dataProvider.getValue(i, 'labs_rspn_org_cd') // cd로 넘겨주기
          temp.labs_sft_chk_tgt_atfl_no = dataProvider.getValue(i, 'labs_sft_chk_tgt_atfl_no')
          temp.labs_mgnt_emp_nm = dataProvider.getValue(i, 'labs_mgnt_emp_nm')
          temp.mgnt_labs_cnt = dataProvider.getValue(i, 'mgnt_labs_cnt')
          temp.labs_sft_chk_exct_yn = dataProvider.getValue(i, 'labs_sft_chk_exct_yn')

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

      this.$axios.post('/api/v1/she/sftchk/labssftchkmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.labs_sft_chk_id = res.data
          this.schData.labs_sft_chk_id = res.data
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
        name: `sftchk-labssftchkmgntlist`,
        params: {
          tmpSchData: this.tmpSchData,
        },
      })
    },
  },
}
</script>
<style>
.rgsample-blue-column {
  color: blue;
}
.rgsample-red-column {
  color: red;
}
</style>
