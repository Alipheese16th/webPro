<template>
  <div>
    <div class="location">
      <!-- 시스템관리 --><!-- 코드관리 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000237') }}</span>
    </div>
    <!-- 코드관리 -->
    <div class="work_title">
      <h2>{{ $t('LB00000237') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 업무구분 -->
                <div class="label_tit">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schCdGrp.biz_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 코드그룹 -->
                <div class="label_tit">{{ $t('LB00000241') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 코드그룹을 입력해주세요. -->
                <v-text-field
                  v-model="schCdGrp.cd_grp_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  placeholder="코드그룹을 입력해주세요."
                  clearable
                  height="30"
                  maxlength="199"
                  @keypress.enter="clearSch()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!-- 코드그룹명 -->
                <div class="label_tit">{{ $t('LB00000238') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 코드그룹명을 입력해주세요. -->
                <v-text-field
                  v-model="schCdGrp.cd_grp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  placeholder="코드그룹명을 입력해주세요."
                  clearable
                  height="30"
                  maxlength="199"
                  @keypress.enter="clearSch()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 언어 -->
                <div class="label_tit">{{ $t('LB00000050') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schCdGrpDt.lang_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000002"
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
            <v-btn color="primary" depressed height="36" @click="clearSch()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <v-row>
        <v-col cols="5">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- 코드 그룹 -->
              <h3>{{ $t('LB00000239') }}</h3>
              <v-spacer></v-spacer>
              <div>
                <!-- 추가 -->
                <v-btn color="" outlined height="28" @click="addGrp()">{{ $t('LB00000047') }}</v-btn>
                <!-- 삭제 -->
                <v-btn color="" outlined height="28" @click="delGrp()">{{ $t('LB00000046') }}</v-btn>
              </div>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="7 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- 코드 -->
              <h3>{{ $t('LB00000240') }}</h3>
              <v-spacer></v-spacer>
              <div>
                <!-- 추가 -->
                <v-btn color="" outlined height="28" @click="add()">{{ $t('LB00000047') }}</v-btn>
                <!-- 삭제 -->
                <v-btn color="" outlined height="28" @click="del()">{{ $t('LB00000046') }}</v-btn>
              </div>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </div>
    <!-- <div class="group_padd"></div> -->
    <div class="container_button">
      <!-- 저장 -->
      <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let gridView2 = GridView
let dataProvider = LocalDataProvider
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '코드관리',
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      schCdGrp: {
        lang_cd: 'KO',
      },
      schCdGrpDt: {
        lang_cd: 'KO',
      },
      frmData: {},
      menu1: false,
      menu2: false,
      codeid: ['CO00000001', 'CO00000002'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000002: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
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
      dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0
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
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      this.selCO00000001.cd.push('')
      this.selCO00000001.cd_nm.push('SELECT')
      res.CO00000001.forEach((i) => {
        this.selCO00000001.cd.push(i.cd)
        this.selCO00000001.cd_nm.push(i.cd_nm)
      })
      this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })

      // 코드그룹 데이터프로바이더 셋팅
      const fields = [
        {
          fieldName: 'biz_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_grp_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_grp_nm',
          dataType: ValueType.TEXT,
        },
      ]

      // 코드그룹 그리드 셋팅
      const columns = [
        {
          header: this.$t('LB00000062'), // 업무구분
          name: 'biz_cd',
          fieldName: 'biz_cd',
          lookupDisplay: true,
          values: this.selCO00000001.cd,
          labels: this.selCO00000001.cd_nm,
          width: '50',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            const cdGrpNo = grid.getValue(dataCell.index.itemIndex, 'cd_grp_no')
            // eslint-disable-next-line eqeqeq
            if (cdGrpNo != '') {
              ret.editable = false
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000241'), // 코드그룹
          name: 'cd_grp_no',
          fieldName: 'cd_grp_no',
          width: '50',
          editable: false,
        },
        {
          header: this.$t('LB00000238'), // 코드그룹명
          name: 'cd_grp_nm',
          fieldName: 'cd_grp_nm',
          width: '150',
          styleName: 'left',
        },
      ]

      // 코드 데이터프로바이더 셋팅
      const fields2 = [
        {
          fieldName: 'cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'cd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mlang_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'sort_seq',
          dataType: ValueType.INT,
        },
        {
          fieldName: 'usg_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_1st_cntn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_2nd_cntn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_3rd_cntn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_4th_cntn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buf_5th_cntn',
          dataType: ValueType.TEXT,
        },
      ]

      // 코드 그리드 셋팅
      const columns2 = [
        {
          header: this.$t('LB00000242'), // 코드값
          name: 'cd',
          fieldName: 'cd',
          width: '70',
        },
        {
          header: this.$t('LB00000243'), // 코드명
          name: 'cd_nm',
          fieldName: 'cd_nm',
          width: '280',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000091'), // 정렬순서
          name: 'sort_seq',
          fieldName: 'sort_seq',
          width: '50',
          editor: {
            type: 'number',
            maxLength: 4,
          },
        },
        {
          header: this.$t('LB00000049'), // 사용여부
          name: 'usg_yn',
          fieldName: 'usg_yn',
          width: '50',
          sortable: false,
          lookupDisplay: true,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
        },
        {
          header: 'BUFF1',
          name: 'buf_1st_cntn',
          fieldName: 'buf_1st_cntn',
          width: '50',
        },
        {
          header: 'BUFF2',
          name: 'buf_2nd_cntn',
          fieldName: 'buf_2nd_cntn',
          width: '50',
        },
        {
          header: 'BUFF3',
          name: 'buf_3rd_cntn',
          fieldName: 'buf_3rd_cntn',
          width: '50',
        },
        {
          header: 'BUFF4',
          name: 'buf_4th_cntn',
          fieldName: 'buf_4th_cntn',
          width: '50',
        },
        {
          header: 'BUFF5',
          name: 'buf_5th_cntn',
          fieldName: 'buf_5th_cntn',
          width: '50',
        },
        {
          header: this.$t('LB00000244'), // 다국어키
          name: 'mlang_no',
          fieldName: 'mlang_no',
          width: '50',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true, width: 30 })
      gridView.setRowIndicator({ width: 30 })

      gridView.editOptions.editable = true
      gridView.displayOptions.selectionStyle = 'singleRow'
      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'

      gridView.onCellClicked = async function (grid, clickData) {
        if (clickData.itemIndex < 0) return
        gridView2.commit()
        if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
          const res = await this.$confirm(this.$t('MS00000060')) // 변경사항이 있습니다. 저장하지 않고 화면을 이동하시겠습니까?
          if (!res) {
            return
          }
        }

        // grid.commit()
        const value = grid.getValue(clickData.itemIndex, 'cd_grp_no')
        this.schCdGrpDt.cd_grp_no = value
        this.frmData.cd_grp_no = value

        // 코드그룹 상세조회
        const cdRows = await this.$axios.$get(`/api/v1/common/chems/cd`, { params: this.schCdGrpDt })
        dataProvider2.setRows(cdRows)
        gridView2.refresh()
        gridView2.setTopItem(0)
      }.bind(this)

      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: true, width: 30 })
      gridView2.setRowIndicator({ width: 30 })

      gridView2.editOptions.editable = true
      gridView2.displayOptions.selectionStyle = 'block'

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'

      gridView2.setColumnProperty('mlang_no', 'visible', false)

      this.clearSch()
    })
  },
  methods: {
    async clearSch() {
      gridView.commit()
      gridView2.commit()

      dataProvider.clearRows()
      dataProvider2.clearRows()

      // 코드그룹조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/cd-grp`, { params: this.schCdGrp })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      gridView.setCurrent(0, 'cd_grp_no')

      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      const value = gridView.getValue(current.dataRow, 'cd_grp_no')
      this.schCdGrpDt.cd_grp_no = value

      // 코드그룹 상세정보 조회
      const cdRows = await this.$axios.$get(`/api/v1/common/chems/cd`, { params: this.schCdGrpDt })
      dataProvider2.setRows(cdRows)
      gridView2.refresh()
      gridView2.setTopItem(0)
    },
    // 저장 버튼
    async save() {
      gridView.commit()
      gridView2.commit()

      if (!this.validation()) return
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.savedatas = [] //  저장될 배열 생성
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (
        dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
        dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0
      ) {
        const insertGrpRow = []
        const updateGrpRow = []
        const deleteGrpRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}
          temp.cd_grp_no = dataProvider.getValue(i, 'cd_grp_no')
          temp.biz_cd = dataProvider.getValue(i, 'biz_cd')
          temp.cd_grp_nm = dataProvider.getValue(i, 'cd_grp_nm')
          if (dataProvider.getRowState(i) === 'created') {
            insertGrpRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateGrpRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteGrpRow.push(temp)
          }
        }

        const insertRow = []
        const updateRow = []
        const deleteRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}
          temp.cd = dataProvider2.getValue(i, 'cd')
          temp.cd_nm = dataProvider2.getValue(i, 'cd_nm')
          temp.sort_seq = dataProvider2.getValue(i, 'sort_seq')
          temp.usg_yn = dataProvider2.getValue(i, 'usg_yn')
          temp.mlang_no = dataProvider2.getValue(i, 'mlang_no')
          temp.buf_1st_cntn = dataProvider2.getValue(i, 'buf_1st_cntn')
          temp.buf_2nd_cntn = dataProvider2.getValue(i, 'buf_2nd_cntn')
          temp.buf_3rd_cntn = dataProvider2.getValue(i, 'buf_3rd_cntn')
          temp.buf_4th_cntn = dataProvider2.getValue(i, 'buf_4th_cntn')
          temp.buf_5th_cntn = dataProvider2.getValue(i, 'buf_5th_cntn')
          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow

        this.frmData.insertGrpRow = insertGrpRow
        this.frmData.updateGrpRow = updateGrpRow
        this.frmData.deleteGrpRow = deleteGrpRow

        this.frmData.lang_cd = this.schCdGrpDt.lang_cd

        const current = gridView.getCurrent()
        this.frmData.set_biz_cd = gridView.getValue(current.dataRow, 'biz_cd')
        this.frmData.cd_grp_no = gridView.getValue(current.dataRow, 'cd_grp_no')

        await this.$axios.post('/api/v1/common/chems/cd', this.frmData).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.clearSch()
          } else if (res.data === 'OverlapFail') {
            this.$toast.show(this.$t('MS00000079'), { className: 'toast_error' }) // 이미 존재하는 키입니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      }
    },
    schReset() {
      gridView.commit()
      this.schCdGrpDt = { lang_cd: 'KO' }
      this.schCdGrpDt.cd_grp_no = ''
      this.frmData.cd_grp_no = ''
      this.schCdGrp.cd_grp_no = ''
      this.schCdGrp = { lang_cd: 'KO' }
    },
    addGrp() {
      gridView.commit()
      if (dataProvider.getRowStateCount('created') > 0) {
        this.$toast.show(this.$t('MS00000029'), { className: 'toast_error' }) // 이미 추가된 행이 존재합니다.
        return
      }
      dataProvider.insertRows(0, [
        {
          cd_grp_no: '',
          biz_cd: '',
          cd_grp_nm: '',
        },
      ])
      dataProvider2.clearRows()
    },
    delGrp() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
      if (checkedRows.length > 0) {
        dataProvider2.clearRows()
      }
    },
    add() {
      gridView2.commit()
      dataProvider2.insertRows(0, [
        {
          cd: '',
          cd_nm: '',
          sort_seq: '',
          buf_1st_cntn: '',
          buf_2nd_cntn: '',
          buf_3rd_cntn: '',
          buf_4th_cntn: '',
          buf_5th_cntn: '',
        },
      ])
    },
    del() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      gridView.commit()
      gridView2.commit()
      if (
        dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') <= 0 &&
        dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') <= 0
      ) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) !== 'createAndDeleted' && dataProvider.getRowState(i) !== 'deleted') {
          if (this.isEmpty(dataProvider.getValue(i, 'biz_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'biz_cd' })
            this.$toast.show(this.$t('MS00000018'), { className: 'toast_error' }) // 업무구분을 선택해주세요.
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'cd_grp_nm'))) {
            gridView.setCurrent({ dataRow: i, column: 'cd_grp_nm' })
            this.$toast.show(this.$t('MS00000078'), { className: 'toast_error' }) // 코드그룹명을 입력해주세요.
            return false
          } else if (dataProvider.getValue(i, 'cd_grp_nm').length > 100) {
            gridView.setCurrent({ dataRow: i, column: 'api_expl' })
            this.$toast.show(this.$t('MS00000080'), { className: 'toast_error' }) // 코드그룹명은 100자 이하로 입력해주세요.
            return false
          }
        }
      }
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        if (dataProvider2.getRowState(i) !== 'createAndDeleted' && dataProvider2.getRowState(i) !== 'deleted') {
          if (this.isEmpty(dataProvider2.getValue(i, 'cd'))) {
            gridView2.setCurrent({ dataRow: i, column: 'cd' })
            this.$toast.show(this.$t('MS00000081'), { className: 'toast_error' }) // 코드값을 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'cd').length > 20) {
            gridView2.setCurrent({ dataRow: i, column: 'cd' })
            this.$toast.show(this.$t('MS00000082'), { className: 'toast_error' }) // 코드값은 20자 이하로 입력해주세요.
            return false
          } else if (/[\s]/g.test(dataProvider2.getValue(i, 'cd'))) {
            gridView2.setCurrent({ dataRow: i, column: 'cd' })
            this.$toast.show(this.$t('MS00000083'), { className: 'toast_error' }) // 코드값에 공백문자는 사용할 수 없습니다.
            return false
          } else if (!/^[A-Za-z0-9+]*$/.test(dataProvider2.getValue(i, 'cd'))) {
            gridView2.setCurrent({ dataRow: i, column: 'cd' })
            this.$toast.show(this.$t('MS00000084'), { className: 'toast_error' }) // 코드값은 영문, 숫자로 작성해주세요.
            return false
          } else if (this.isEmpty(dataProvider2.getValue(i, 'cd_nm'))) {
            gridView2.setCurrent({ dataRow: i, column: 'cd_nm' })
            this.$toast.show(this.$t('MS00000085'), { className: 'toast_error' }) // 코드명을 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'cd_nm').length > 600) {
            gridView2.setCurrent({ dataRow: i, column: 'cd_nm' })
            this.$toast.show(this.$t('MS00000086'), { className: 'toast_error' }) // 코드명은 600자 이하로 입력해주세요.
            return false
          } else if (this.isEmpty(dataProvider2.getValue(i, 'sort_seq'))) {
            gridView2.setCurrent({ dataRow: i, column: 'sort_seq' })
            this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }) // 정렬순서를 입력해주세요.
            return false
          } else if (!/^[0-9]*$/.test(dataProvider2.getValue(i, 'sort_seq'))) {
            gridView2.setCurrent({ dataRow: i, column: 'sort_seq' })
            this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }) // 정렬순서는 숫자만 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'sort_seq').length > 9) {
            gridView2.setCurrent({ dataRow: i, column: 'sort_seq' })
            this.$toast.show(this.$t('MS00000066'), { className: 'toast_error' }) // 정렬순서는 9자 이하로 작성해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'buf_1st_cntn').length > 200) {
            gridView2.setCurrent({ dataRow: i, column: 'buf_1st_cntn' })
            this.$toast.show(this.$t('MS00000087'), { className: 'toast_error' }) // buffer 1차 내용은 200자 이하로 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'buf_2nd_cntn').length > 200) {
            gridView2.setCurrent({ dataRow: i, column: 'buf_2nd_cntn' })
            this.$toast.show(this.$t('MS00000088'), { className: 'toast_error' }) // buffer 2차 내용은 200자 이하로 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'buf_3rd_cntn').length > 200) {
            gridView2.setCurrent({ dataRow: i, column: 'buf_3rd_cntn' })
            this.$toast.show(this.$t('MS00000091'), { className: 'toast_error' }) // buffer 3차 내용은 200자 이하로 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'buf_4th_cntn').length > 200) {
            gridView2.setCurrent({ dataRow: i, column: 'buf_4th_cntn' })
            this.$toast.show(this.$t('MS00000090'), { className: 'toast_error' }) // buffer 4차 내용은 200자 이하로 입력해주세요.
            return false
          } else if (dataProvider2.getValue(i, 'buf_5th_cntn').length > 200) {
            gridView2.setCurrent({ dataRow: i, column: 'buf_5th_cntn' })
            this.$toast.show(this.$t('MS00000089'), { className: 'toast_error' }) // buffer 5차 내용은 200자 이하로 입력해주세요.
            return false
          }
        }
      }
      return true
    },
  },
}
</script>
<style lang="scss" scoped></style>
