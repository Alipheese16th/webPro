<template>
  <div>
    <div class="location">
      <!-- 시스템 관리 --><!-- API관리 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000232') }}</span>
    </div>
    <div class="work_title">
      <!-- API관리 -->
      <h2>{{ $t('LB00000232') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!-- 업무 -->
                <div class="label_tit">{{ $t('LB00000082') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.biz_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="dtlChange()"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- 상세업무 -->
                <div class="label_tit">{{ $t('LB00000081') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.dtl_biz_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="chgUpCodes"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!-- API URL -->
                <div class="label_tit">{{ $t('LB00000233') }}</div>
              </v-col>
              <!-- API URL을 입력해주세요. -->
              <v-col cols="3">
                <v-text-field
                  v-model="schData.api_url"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('MS00000073')"
                  height="30"
                  maxlength="99"
                  @keypress.enter="clearSch()"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="clearSch()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <v-container>
      <v-row>
        <v-col cols="7">
          <div class="realgrid_container">
            <div class="sub_title" style="display: flex">
              <h3>API</h3>
              <v-spacer></v-spacer>
              <div>
                <!-- 추가 -->
                <v-btn style="bottom: 3px" color="" outlined height="28" @click="add">{{ $t('LB00000047') }}</v-btn>
                <!-- 삭제 -->
                <v-btn style="bottom: 3px" color="" outlined height="28" @click="del">{{ $t('LB00000046') }}</v-btn>
              </div>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="5 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- API 권한 -->
              <h3>{{ $t('LB00000234') }}</h3>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="group_padd"></div>
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

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: 'API관리',
  },
  components: {
    // quillEditor,
  },
  fetch() {},
  data() {
    return {
      schData: {
        biz_cd: '',
        dtl_biz_cd: '',
        api_url: '',
      },
      frmData: {},
      codeid: ['CO00000001', 'CO00000008'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000008: [{ cd: '', cd_nm: '' }],
      },
      chgUpCodes: [{ cd: '', cd_nm: '' }],
      lookUpTreeData: {
        cd: [],
        cd_nm: [],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      selCO00000008: {
        cd: [],
        cd_nm: [],
        up_cd: [],
      },
      menu1: false,
      menu2: false,
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
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || dataProvider2.getRowStateCount('updated') > 0) {
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
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        res.CO00000001.forEach((i) => {
          this.lookUpTreeData.cd.push(i.cd)
          this.lookUpTreeData.cd_nm.push(i.cd_nm)
        })
        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
        this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })
        res.CO00000008.forEach((i) => {
          this.selCO00000008.cd.push(i.cd)
          this.selCO00000008.cd_nm.push(i.cd_nm)
          this.selCO00000008.up_cd.push(i.up_cd)
        })

        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.setDataSource(dataProvider)

        // LookupTree 데이터
        const bizCd = { id: 'biz_cd', levels: 1, keys: [], values: [] }
        const dtlBizCd = { id: 'dtl_biz_cd', levels: 2, tags: [], keys: [], values: [] }
        // 샘플 데이터 작성
        const datSample = []
        for (let i = 0; i < this.selCO00000001.cd.length; i++) {
          for (let j = 0; j < this.selCO00000008.cd.length; j++) {
            if (this.selCO00000001.cd[i] === this.selCO00000008.up_cd[j]) {
              const dat = {}
              dat.biz_cd = this.selCO00000001.cd[i]
              dat.biz_cd_nm = this.selCO00000001.cd_nm[i]
              dat.dtl_biz_cd = this.selCO00000008.cd[j]
              dat.dtl_biz_cd_nm = this.selCO00000008.cd_nm[j]

              datSample.push(dat)
            }
          }
        }
        // LookupTree 데이터 작성
        for (let i = 0; i < datSample.length; i++) {
          const codes = datSample[i]
          if (!bizCd.keys.includes(codes.biz_cd)) {
            bizCd.keys.push(codes.biz_cd)
            bizCd.values.push(codes.biz_cd_nm)
          }
          if (!dtlBizCd.tags.includes(codes.dtl_biz_cd)) {
            dtlBizCd.tags.push(codes.dtl_biz_cd)
            dtlBizCd.keys.push([codes.biz_cd, codes.dtl_biz_cd])
            dtlBizCd.values.push(codes.dtl_biz_cd_nm)
          }
        }
        // LookupTree 추가
        gridView.addLookupSource(bizCd)
        gridView.addLookupSource(dtlBizCd)
        // LookupTree가 적용될 컬럼 선언
        const col = [
          {
            header: this.$t('LB00000235'), // API번호
            name: 'api_no',
            fieldName: 'api_no',
            width: '100',
            editable: false,
          },
          {
            header: this.$t('LB00000082'), // 업무
            name: 'biz_cd',
            fieldName: 'biz_cd',
            width: '100',
            lookupDisplay: true,
            values: this.lookUpTreeData.cd,
            labels: this.lookUpTreeData.cd_nm,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
            },
          },
          {
            header: this.$t('LB00000081'), // 상세업무
            name: 'dtl_biz_cd',
            fieldName: 'dtl_biz_cd',
            values: this.selCO00000008.cd,
            labels: this.selCO00000008.cd_nm,
            lookupDisplay: true,
            // lookupTree 설정
            lookupSourceId: 'dtl_biz_cd',
            lookupKeyFields: ['biz_cd', 'dtl_biz_cd'],
            width: '100',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
            },
          },
          {
            header: this.$t('LB00000236'), // API명
            name: 'api_expl',
            fieldName: 'api_expl',
            width: '200',
          },
          {
            header: this.$t('LB00000233'), // API URL
            name: 'api_url',
            fieldName: 'api_url',
            width: '200',
            styleName: 'left',
          },
        ]
        // 컬럼 설정
        gridView.setColumns(col)
        gridView.setFooters({ visible: false })

        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.rowIndicator.visible = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'
        gridView.setRowIndicator({ width: 50 })

        gridView.onEditChange = function (grid, index, value) {
          const current = gridView.getCurrent()
          if (current.column !== 'biz_cd') {
          } else {
            gridView.commit()
            dataProvider.setValue(current.dataRow, 'dtl_biz_cd', '')
          }
        }

        gridView.onCellClicked = async function (grid, index, clickData) {
          if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
            // 저장 여부 확인
            const res = await this.$confirm(this.$t('MS00000060'))
            if (!res) {
              return
            }
          }
          if (index.itemIndex < 0) return
          const value = gridView.getValue(index.itemIndex, 'api_no')
          this.schData.api_no = value
          // 메뉴 권한 조회
          this.selectApiRole(this.schData)
        }.bind(this)

        this.select()
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    const columns2 = [
      {
        header: this.$t('LB00000226'), // 권한번호
        name: 'role_no',
        fieldName: 'role_no',
        width: '200',
      },
      {
        header: this.$t('LB00000064'), // 권한명
        name: 'rolenm',
        fieldName: 'rolenm',
        width: '200',
      },
      {
        header: 'GET',
        name: 'gemthd_yn',
        fieldName: 'gemthd_yn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: 'N',
          trueValues: 'Y',
        },
      },
      {
        header: 'POST',
        name: 'pomthd_yn',
        fieldName: 'pomthd_yn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: 'N',
          trueValues: 'Y',
        },
      },
      {
        header: 'PUT',
        name: 'pumthd_yn',
        fieldName: 'pumthd_yn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: 'N',
          trueValues: 'Y',
        },
      },
      {
        header: 'DELETE',
        name: 'demthd_yn',
        fieldName: 'demthd_yn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: 'N',
          trueValues: 'Y',
        },
      },
    ]
    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
    gridView2.setDataSource(dataProvider2)
    gridView2.setColumns(columns2)
    gridView2.setFooters({ visible: false })

    gridView2.setStateBar({ visible: false })
    gridView2.setCheckBar({ visible: false })
    gridView2.setRowIndicator({ width: 50 })
    gridView2.displayOptions.selectionStyle = 'block'

    gridView2.header.height = 39
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'
  },
  methods: {
    clearSch() {
      dataProvider2.clearRows()
      this.select()
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/api`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)

      gridView.setFocus()
      gridView.setCurrent(0, 'api_no')

      gridView.commit()
      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      const value = gridView.getValue(current.dataRow, 'api_no')
      this.schData.api_no = value
      // 메뉴 권한 조회
      this.selectApiRole(this.schData)
    },
    async selectApiRole() {
      gridView2.cancel()
      // 메뉴 목록 조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/api-role`, { params: this.schData })

      // 데이터 등록
      dataProvider2.setRows(rows)
      gridView2.refresh()
      gridView2.setTopItem(0)
    },
    schReset() {
      this.schData = {}
      this.chgUpCodes = [{ cd: '', cd_nm: '' }]
      this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL' })
    },
    dtlChange() {
      this.chgUpCodes = [{ cd: '', cd_nm: '' }]
      this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL' })
      this.codes.CO00000008.forEach((i) => {
        if (i.up_cd === this.schData.biz_cd) {
          this.chgUpCodes.push({ cd: i.cd, cd_nm: i.cd_nm })
        }
      })
    },
    add() {
      if (dataProvider.getRowStateCount('created') > 0) {
        this.$toast.show(this.$t('MS00000029'), { className: 'toast_error' }) // 이미 추가된 행이 존재합니다.
        return
      }
      gridView.commit()
      const rowno = dataProvider.getRowCount() + 1
      dataProvider.insertRows(0, [
        {
          no: rowno,
          api_no: '',
          biz_cd: 'CO',
          dtl_biz_cd: 'CO01',
          api_url: '',
          api_expl: '',
        },
      ])
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
      if (checkedRows.length > 0) {
        dataProvider2.clearRows()
      }
    },
    async save() {
      if (!this.validation()) return
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      gridView.commit()
      gridView2.commit()
      this.savedatas = [] //  저장될 배열 생성
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || dataProvider2.getRowStateCount('updated') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}
          temp.api_no = dataProvider.getValue(i, 'api_no')
          temp.biz_cd = dataProvider.getValue(i, 'biz_cd')
          temp.dtl_biz_cd = dataProvider.getValue(i, 'dtl_biz_cd')
          temp.api_url = dataProvider.getValue(i, 'api_url')
          temp.api_expl = dataProvider.getValue(i, 'api_expl')
          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        // 데이터 커밋
        gridView2.commit()
        this.chkData = {}
        const updateValue = []
        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          // 수정된 데이터만 처리해준다.
          if (dataProvider2.getRowState(i) === 'updated') {
            const temp = {}
            temp.role_no = dataProvider2.getValue(i, 'role_no')
            temp.gemthd_yn = dataProvider2.getValue(i, 'gemthd_yn')
            temp.pomthd_yn = dataProvider2.getValue(i, 'pomthd_yn')
            temp.pumthd_yn = dataProvider2.getValue(i, 'pumthd_yn')
            temp.demthd_yn = dataProvider2.getValue(i, 'demthd_yn')
            updateValue.push(temp)
          }
        }
        this.chkData.api_no = this.schData.api_no
        this.chkData.updateValue = updateValue
        this.chkData.username = this.frmData.username

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
        this.frmData.chkData = this.chkData

        await this.$axios.post('/api/v1/common/chems/api', this.frmData).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.clearSch()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      }
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
          if (this.isEmpty(dataProvider.getValue(i, 'api_url'))) {
            gridView.setCurrent({ dataRow: i, column: 'api_url' })
            this.$toast.show(this.$t('MS00000073'), { className: 'toast_error' }) // API URL을 입력해주세요.
            return false
          } else if (dataProvider.getValue(i, 'api_url').length > 300) {
            gridView.setCurrent({ dataRow: i, column: 'api_url' })
            this.$toast.show(this.$t('MS00000074'), { className: 'toast_error' }) // API URL은 300자 이하로 입력해주세요.
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'api_expl'))) {
            gridView.setCurrent({ dataRow: i, column: 'api_expl' })
            this.$toast.show(this.$t('MS00000075'), { className: 'toast_error' }) // API 명을 입력해주세요.
            return false
          } else if (dataProvider.getValue(i, 'api_expl').length > 300) {
            gridView.setCurrent({ dataRow: i, column: 'api_expl' })
            this.$toast.show(this.$t('MS00000076'), { className: 'toast_error' }) // API 명은 300자 이하로 입력해주세요.
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'dtl_biz_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'dtl_biz_cd' })
            this.$toast.show(this.$t('MS00000077'), { className: 'toast_error' }) // 상세 업무를 선택해주세요.
            return false
          }
        }
      }
      return true
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'api_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'biz_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'dtl_biz_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'api_url',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'api_expl',
    dataType: ValueType.TEXT,
  },
]

const fields2 = [
  {
    fieldName: 'role_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rolenm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'gemthd_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pomthd_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pumthd_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'demthd_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
