<template>
  <div>
    <div class="location">
      <span>Home > 기준정보 > 순회점검 체크리스트 관리</span>
    </div>
    <div class="work_title">
      <h2>순회점검 체크리스트 관리</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--구분-->
                <div class="label_tit">구분</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="chec_cl_cd"
                  v-model="schData.chec_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000018"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--상세구분-->
                <div class="label_tit">상세구분</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="chec_dtl_cl_cd"
                  v-model="schData.chec_dtl_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000019"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">사용여부</div>
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
            <v-btn color="" outlined height="36" @click="reset">초기화</v-btn>
            <v-btn color="primary" depressed height="36" @click="selectCircChkList">검색</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title"><h3>순회점검 체크리스트 목록</h3></div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <v-btn color="" outlined height="30" @click="del">삭제</v-btn>
          <v-btn color="" outlined height="30" @click="add">추가</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
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
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '순회점검 체크리스트 관리',
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      user: {},
      totalcnt: 0,
      schData: {
        chec_cl_cd: '',
        chec_dtl_cl_cd: '',
        usg_yn: '',
      },
      frmData: {},
      tempData: {},
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      selST00000018: {
        cd: [],
        cd_nm: [],
      },
      selST00000019: {
        cd: [],
        cd_nm: [],
      },
      selST00000009: {
        cd: [],
        cd_nm: [],
      },
      codeid: ['ST00000018', 'ST00000019', 'ST00000009'],
      codes: {
        ST00000018: [{ cd: '', cd_nm: '' }],
        ST00000019: [{ cd: '', cd_nm: '' }],
        ST00000009: [{ cd: '', cd_nm: '' }],
      },
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
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selST00000018.cd.push('')
        this.selST00000018.cd_nm.push('SELECT')
        this.selST00000019.cd.push('')
        this.selST00000019.cd_nm.push('SELECT')
        this.selST00000009.cd.push('')
        this.selST00000009.cd_nm.push('SELECT')
        res.ST00000018.forEach((i) => {
          this.selST00000018.cd.push(i.cd)
          this.selST00000018.cd_nm.push(i.cd_nm)
        })
        res.ST00000019.forEach((i) => {
          this.selST00000019.cd.push(i.cd)
          this.selST00000019.cd_nm.push(i.cd_nm)
        })
        res.ST00000009.forEach((i) => {
          this.selST00000009.cd.push(i.cd)
          this.selST00000009.cd_nm.push(i.cd_nm)
        })

        this.codes.ST00000018.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.ST00000019.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.ST00000009.unshift({ cd: '', cd_nm: 'ALL' })
        // 그리드 세팅
        const fields = [
          {
            fieldName: '_chk',
            dataType: ValueType.BOOLEAN,
          },
          {
            fieldName: 'chec_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_dtl_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_in_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_sn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chec_txt',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'usg_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'chk_yn',
            dataType: ValueType.BOOLEAN,
          },
        ]

        const columns = [
          {
            header: ' ',
            name: '_chk',
            fieldName: '_chk',
            type: 'data',
            width: '30',
            editable: false,
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_yn')
              const ret = {}

              switch (gubun) {
                case true:
                  ret.renderer = {
                    type: 'check',
                  }
                  break
                case false:
                  ret.editor = {
                    type: 'text',
                  }
                  break
              }
              return ret
            },
          },
          {
            header: '구분', // 구분
            name: 'chec_cl_cd',
            fieldName: 'chec_cl_cd',
            width: '200',
            sortable: false,
            lookupDisplay: true,
            values: this.selST00000018.cd,
            labels: this.selST00000018.cd_nm,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: false,
              displayLabels: 'label',
            },
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_yn')
              const ret = {}

              switch (gubun) {
                case true:
                  ret.editable = true
                  break
                case false:
                  ret.editable = false
                  break
              }
              return ret
            },
          },
          {
            header: '상세구분', // 상세구분
            name: 'chec_dtl_cl_cd',
            fieldName: 'chec_dtl_cl_cd',
            width: '100',
            lookupDisplay: true,
            values: this.selST00000019.cd,
            labels: this.selST00000019.cd_nm,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: false,
              displayLabels: 'label',
            },
            styleCallback(grid, dataCell) {
              const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_yn')
              const ret = {}

              switch (gubun) {
                case true:
                  ret.editable = true
                  break
                case false:
                  ret.editable = false
                  break
              }
              return ret
            },
          },
          {
            header: '순회체크리스트 순번', // 체크리스트 내용
            name: 'chec_sn',
            fieldName: 'chec_sn',
            width: '300',
          },
          {
            header: '체크리스트 내용', // 체크리스트 내용
            name: 'chec_txt',
            fieldName: 'chec_txt',
            width: '300',
            editable: true,
          },
          {
            header: '입력구분', // 입력구분
            name: 'chec_in_cl_cd',
            fieldName: 'chec_in_cl_cd',
            width: '100',
            lookupDisplay: true,
            values: this.selST00000009.cd,
            labels: this.selST00000009.cd_nm,
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: '사용여부', // 사용여부
            name: 'usg_yn',
            fieldName: 'usg_yn',
            width: '100',
            styles: {
              textAlignment: 'center',
            },
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
        ]

        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.setDataSource(dataProvider)
        gridView.setColumns(columns)
        gridView.setFooters({ visible: false })

        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: false })
        gridView.setRowIndicator({ visible: false })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'singleRow'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('id', 'visible', false)
        gridView.setColumnProperty('chec_sn', 'visible', false)

        this.selectCircChkList() // 조회
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    add() {
      gridView.commit()
      dataProvider.insertRows(0, [
        {
          _chk: false,
          chec_cl_cd: '',
          chec_dtl_cl_cd: '',
          chec_in_cl_cd: '',
          chec_sn: '',
          chec_txt: '',
          usg_yn: 'Y',
          chk_yn: true,
        },
      ])
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, '_chk')) {
          checkedRows.push(i)
        }
      }
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData.chec_cl_cd = ''
      this.schData.chec_dtl_cl_cd = ''
      this.schData.usg_yn = ''
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    async selectCircChkList() {
      this.schData.chec_gb = 'CHK002' // 순회 점검 구분 코드
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/oprnBfChkListMgnt`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    validation() {
      gridView.commit()
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) !== 'createAndDeleted' && dataProvider.getRowState(i) !== 'deleted') {
          if (this.isEmpty(dataProvider.getValue(i, 'chec_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'chec_cl_cd' })
            this.$toast.show('구분을 선택해주세요.', { className: 'toast_error' })
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'chec_dtl_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'chec_dtl_cl_cd' })
            this.$toast.show('상세구분을 선택해주세요.', { className: 'toast_error' })
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'chec_txt'))) {
            gridView.setCurrent({ dataRow: i, column: 'chec_txt' })
            this.$toast.show('체크리스트 내용을 작성해주세요.', { className: 'toast_error' })
            return false
          } else if (dataProvider.getValue(i, 'chec_txt').length > 100) {
            gridView.setCurrent({ dataRow: i, column: 'chec_txt' })
            this.$toast.show('체크리스트 내용은 100자를 초과할 수 없습니다.', { className: 'toast_error' })
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'chec_in_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'chec_in_cl_cd' })
            this.$toast.show('입력구분을 선택해주세요.', { className: 'toast_error' })
            return false
          }
        }
      }
      return true
    },
    async save() {
      gridView.commit()

      if (!this.validation()) return
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.savedatas = [] //  저장될 배열 생성
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []
        // insert, delete, update 구분 처리
        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}
          temp.chec_cl_cd = dataProvider.getValue(i, 'chec_cl_cd')
          temp.chec_dtl_cl_cd = dataProvider.getValue(i, 'chec_dtl_cl_cd')
          temp.chec_sn = dataProvider.getValue(i, 'chec_sn')
          temp.chec_txt = dataProvider.getValue(i, 'chec_txt')
          temp.chec_in_cl_cd = dataProvider.getValue(i, 'chec_in_cl_cd')
          temp.usg_yn = dataProvider.getValue(i, 'usg_yn')
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
        this.frmData.chec_gb = 'CHK002'
        await this.$axios.post('/api/v1/st/stndinfr/oprnBfChkListMgnt', this.frmData).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.selectCircChkList()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
