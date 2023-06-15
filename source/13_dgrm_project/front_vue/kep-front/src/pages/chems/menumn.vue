<template>
  <div>
    <div class="location">
      <!-- 시스템관리 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000219') }}</span>
    </div>
    <div class="work_title">
      <!-- 메뉴관리 -->
      <h2>{{ $t('LB00000219') }}</h2>
    </div>
    <div class="realgrid_container">
      <v-row>
        <v-col cols="6" style="padding-left: 0">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- 메뉴 목록 -->
              <h3>{{ $t('LB00000220') }}</h3>
              <v-spacer></v-spacer>
              <div>
                <!-- 추가 -->
                <v-btn color="" outlined height="28" @click="openNew()">{{ $t('LB00000047') }}</v-btn>
              </div>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="6 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title">
              <!-- 메뉴 권한 -->
              <h3>{{ $t('LB00000221') }}</h3>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="container_button">
      <!-- 저장 -->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
    </div>
    <popup ref="pop" @close="refresh" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './menucntn'

let gridView = GridView
let gridView2 = GridView
let dataProvider = LocalDataProvider
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '시스템관리-메뉴관리',
  },
  components: {
    popup,
  },
  fetch() {
    // 메뉴 목록 조회
    this.select()
  },
  data() {
    return {
      schData: {},
      frmData: {},
      chkData: {},
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
    gridView2.commit()
    if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
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
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'up_menu_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'menu_url',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sort_seq',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'level',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'menu_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mlang_cntn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usg_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'addbtn',
        dataType: ValueType.TEXT,
        // datetimeFormat: 'iso',
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
        fieldName: 'rolechk',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000223'), // 상위메뉴번호
        name: 'up_menu_no',
        fieldName: 'up_menu_no',
        width: '80',
      },
      {
        header: this.$t('LB00000224'), // 메뉴 URL
        name: 'menu_url',
        fieldName: 'menu_url',
        width: '80',
      },
      {
        header: this.$t('LB00000091'), // 정렬순서
        name: 'sort_seq',
        fieldName: 'sort_seq',
        width: '80',
      },
      {
        header: this.$t('LB00000077'), // 레벨
        name: 'level',
        fieldName: 'level',
        width: '80',
      },
      {
        header: this.$t('LB00000222'), // 메뉴 번호
        name: 'menu_no',
        fieldName: 'menu_no',
        width: '80',
      },
      {
        header: this.$t('LB00000076'), // 메뉴명
        name: 'mlang_cntn',
        fieldName: 'mlang_cntn',
        width: '100',
        styleName: 'link left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const level = grid.getValue(dataCell.index.itemIndex, 'level')
          // eslint-disable-next-line eqeqeq
          if (level == '2') {
            ret.styleName = 'link left pl-4'
          } else if (level === '3') {
            ret.styleName = 'link left pl-6'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'usg_yn',
        fieldName: 'usg_yn',
        width: '100',
      },
      {
        header: this.$t('LB00000225'), // 하위추가
        name: 'addbtn',
        fieldName: 'addbtn',
        width: '50',
        type: 'button',
        styleCallback(grid, dataCell) {
          const ret = {}
          if (dataCell.value !== '') {
            ret.styleName = 'btn_grid_add'
          }
          return ret
        },
      },
    ]

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
        header: this.$t('LB00000075'), // 권한
        name: 'rolechk',
        fieldName: 'rolechk',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: '0',
          trueValues: '1',
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
    gridView.setRowIndicator({ width: 50 })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    // gridView.setColumnProperty('menu_no', 'visible', false)
    gridView.setColumnProperty('up_menu_no', 'visible', false)
    gridView.setColumnProperty('sort_seq', 'visible', false)
    gridView.setColumnProperty('menu_url', 'visible', false)

    gridView.onCellClicked = async function (grid, index, clickData) {
      if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') > 0) {
        gridView2.commit()
        gridView.commit()
        // 저장 여부 확인
        const res = await this.$confirm(this.$t('MS00000060'))
        if (!res) {
          return
        }
      }
      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      const value = dataProvider.getValue(current.dataRow, 'menu_no')
      this.schData.menu_no = value
      // 메뉴 권한 조회
      this.selectMenuRole(this.schData)

      // 폼 데이터 초기화
      this.frmData.menuNo = value
      this.frmData.menuNm = dataProvider.getValue(current.dataRow, 'mlang_cntn')
      this.frmData.menuUrl = dataProvider.getValue(current.dataRow, 'menu_url')
      this.frmData.sortSeq = dataProvider.getValue(current.dataRow, 'sort_seq')
      this.frmData.useYn = dataProvider.getValue(current.dataRow, 'usg_yn')
      this.frmData.upMenuNo = dataProvider.getValue(current.dataRow, 'up_menu_no')
      if (dataProvider.getValue(current.dataRow, 'level') === '1') {
        // 그리드 검색
        const indexDt = dataProvider.searchData({
          fields: ['up_menu_no'],
          value: this.frmData.menuNo,
        })
        this.frmData.deleteYn = indexDt != null ? 'N' : 'Y'
        this.frmData.upMenuNm = ''
      } else {
        // 그리드 검색
        const searchVal = this.frmData.upMenuNo
        const fields = dataProvider.getOrgFieldNames()
        const startFieldIndex = 0
        const options = {
          fields,
          value: searchVal,
          startIndex: 0,
          startFieldIndex,
          wrap: true,
          caseSensitive: false,
          partialMatch: true,
          select: false,
        }
        const indexDt = gridView.searchCell(options)
        this.frmData.upMenuNm = dataProvider.getValue(indexDt.itemIndex, 'mlang_cntn')
        this.frmData.deleteYn = 'Y'
      }

      // 팝업 창 호출(서브 트리)
      if (dataProvider.getValue(current.dataRow, 'level') !== '3' && current.column === 'addbtn') {
        // 새 메뉴 등록
        this.frmData.upMenuNo = value
        this.frmData.upMenuNm = this.frmData.menuNm
        this.frmData.menuNo = ''
        this.frmData.menuNm = ''
        this.frmData.menuUrl = ''
        this.frmData.sortSeq = ''
        this.frmData.useYn = 'Y'
        this.frmData.deleteYn = 'Y'
        this.$refs.pop.open(false, this.frmData, '')
      } else if (current.column === 'mlang_cntn') {
        // 메뉴 정보 수정
        this.$refs.pop.open(true, this.frmData, '')
      }
    }.bind(this)

    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields2)
    gridView2 = new GridView('realgrid2')
    gridView2.setDataSource(dataProvider2)
    gridView2.setColumns(columns2)
    gridView2.setFooters({ visible: false })

    gridView2.setStateBar({ visible: false })
    gridView2.setCheckBar({ visible: false })
    gridView2.setRowIndicator({ width: 50 })
    gridView2.editOptions.editable = true
    gridView2.displayOptions.selectionStyle = 'block'

    gridView2.header.height = 39
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'

    gridView2.setColumnProperty('role_no', 'visible', false)
  },
  methods: {
    openNew() {
      this.frmData.upMenuNo = ''
      this.frmData.upMenuNm = ''
      this.frmData.menuNo = ''
      this.frmData.menuNm = ''
      this.frmData.menuUrl = ''
      this.frmData.sortSeq = ''
      this.frmData.useYn = 'Y'
      this.frmData.deleteYn = 'Y'
      this.$refs.pop.open(false, this.frmData, '')
    },
    validation() {
      if (dataProvider2.getRowStateCount('all') - dataProvider2.getRowStateCount('createAndDeleted') <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      return true
    },
    async select() {
      // 메뉴 목록 조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/menu`, { params: this.schData })

      // 데이터 등록
      dataProvider.setRows(rows)
      gridView.refresh()
      // gridView.setTopItem(0)
      gridView.setFocus()
      gridView.setCurrent(0, 'level')

      const current = gridView.getCurrent()
      if (current.itemIndex < 0) return
      const value = dataProvider.getValue(current.dataRow, 'menu_no')
      this.schData.menu_no = value
      // 메뉴 권한 조회
      this.selectMenuRole(this.schData)
    },
    async selectMenuRole() {
      gridView2.cancel()
      // 메뉴 목록 조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/menu-role`, { params: this.schData })

      // 데이터 등록
      dataProvider2.setRows(rows)
      gridView2.refresh()
      gridView2.setTopItem(0)
    },
    async save() {
      // 데이터 커밋
      gridView2.commit()
      if (!this.validation()) {
        return
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010'))
      if (!res) {
        return
      }
      this.chkData = {}
      const chkValue = []
      const unChkValue = []
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 수정된 데이터만 처리해준다.
        if (dataProvider2.getRowState(i) === 'updated') {
          if (dataProvider2.getValue(i, 'rolechk') === '1') {
            chkValue.push(dataProvider2.getValue(i, 'role_no'))
          } else {
            unChkValue.push(dataProvider2.getValue(i, 'role_no'))
          }
        }
      }
      this.chkData.menuNo = this.schData.menu_no
      this.chkData.chkValue = chkValue
      this.chkData.unChkValue = unChkValue
      this.chkData.username = this.frmData.username

      await this.$axios.post('/api/v1/common/chems/menu-role', this.chkData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011'))
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' })
        }
      })

      // 그리드 초기화
      this.selectMenuRole()
    },
    refresh(menuNo) {
      this.select()
    },
  },
}
</script>
<style lang="scss" scoped></style>
