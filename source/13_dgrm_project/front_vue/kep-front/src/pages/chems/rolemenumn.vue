<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--권한별 메뉴관리(KO)-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000080') }}</span>
    </div>
    <div class="work_title">
      <!--권한별 메뉴관리(KO)-->
      <h2>{{ $t('LB00000080') }}</h2>
    </div>
    <div class="realgrid_container">
      <v-row>
        <v-col cols="6">
          <div class="realgrid_container">
            <div class="sub_title">
              <!--권한 목록(KO)-->
              <h3>{{ $t('LB00000079') }}</h3>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="6 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title">
              <!--권한-메뉴(KO)-->
              <h3>{{ $t('LB00000078') }}</h3>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let roleGridView = GridView
let roleDataProvider = LocalDataProvider
let menuGridView = GridView
let menuDataProvider = LocalDataProvider

export default {
  meta: {
    title: '권한별 메뉴관리',
  },
  components: {
    // quillEditor,
  },
  fetch() {
    // 권한 목록 조회
    this.selectRole()
  },
  data() {
    return {
      savedatas: [], //  저장될 배열 생성
      schData: {
        roleNo: '',
      },
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
    menuGridView.commit()
    if (menuDataProvider.getRowStateCount('all') - menuDataProvider.getRowStateCount('createAndDeleted') > 0) {
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
  created() {},
  mounted() {
    roleDataProvider = new LocalDataProvider(false)
    roleDataProvider.setFields(roleFields)
    roleGridView = new GridView('realgrid')
    roleGridView.setDataSource(roleDataProvider)
    const roleColumns = [
      {
        header: this.$t('LB00000063'), // 권한구분
        name: 'roleTypeCodeName',
        fieldName: 'roleTypeCodeName',
        width: '100',
      },
      {
        header: this.$t('LB00000064'), // 권한명
        name: 'roleExplain',
        fieldName: 'roleExplain',
        width: '100',
      },
    ]

    roleGridView.setColumns(roleColumns)
    roleGridView.setFooters({ visible: false })

    roleGridView.setStateBar({ visible: false })
    roleGridView.setCheckBar({ visible: false })
    roleGridView.editOptions.editable = false
    roleGridView.displayOptions.selectionStyle = 'block'

    roleGridView.header.height = 39
    roleGridView.displayOptions.rowHeight = 40
    roleGridView.footer.height = 40
    roleGridView.displayOptions.fitStyle = 'fill'

    roleGridView.onCellClicked = function (grid, index, clickData) {
      const current = roleGridView.getCurrent()
      if (current.itemIndex < 0) return
      if (index.cellType === 'header' || index.cellType === 'head') return
      const value = roleDataProvider.getValue(current.dataRow, 'roleNo')
      this.schData.roleNo = value
      // 권한별 메뉴 매핑정보 조회
      this.selectMenu()
    }.bind(this)

    menuDataProvider = new LocalDataProvider(false)
    menuDataProvider.setFields(menuFields)
    menuGridView = new GridView('realgrid2')
    menuGridView.setDataSource(menuDataProvider)
    const menuColumns = [
      {
        header: this.$t('LB00000077'), // 레벨
        name: 'menuLevel',
        fieldName: 'menuLevel',
        width: '50',
      },
      {
        header: this.$t('LB00000076'), // 메뉴명
        name: 'menuName',
        fieldName: 'menuName',
        width: '200',
      },
      {
        header: this.$t('LB00000075'), // 권한
        name: 'roleCheck',
        fieldName: 'roleCheck',
        editable: false,
        width: '50',
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: '0',
          trueValues: '1',
        },
      },
    ]
    menuGridView.setColumns(menuColumns)
    menuGridView.setFooters({ visible: false })

    menuGridView.setStateBar({ visible: false })
    menuGridView.setCheckBar({ visible: false })
    // menuGridView.editOptions.editable = false
    menuGridView.displayOptions.selectionStyle = 'block'

    menuGridView.header.height = 39
    menuGridView.displayOptions.rowHeight = 40
    menuGridView.footer.height = 40
    menuGridView.displayOptions.fitStyle = 'fill'
  },
  methods: {
    async selectRole() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/role-menu`, { params: this.schData })

      roleDataProvider.setRows(rows)
      roleGridView.refresh()
      roleGridView.setTopItem(0)
    },
    async selectMenu() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/role-menu/${this.schData.roleNo}`)

      menuDataProvider.setRows(rows)
      menuGridView.refresh()
      menuGridView.setTopItem(0)
    },
    async save() {
      menuGridView.commit()
      this.savedatas = [] //  저장될 배열 비우기
      const lowstatus = menuDataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      // 수정된 row push
      if ((await menuDataProvider.getRowStateCount('updated')) > 0) {
        await lowstatus.updated.forEach((i) => {
          menuDataProvider.setValue(i, 'rowStatus', 'U')
          this.savedatas.push(menuDataProvider.getJsonRow(i, i))
        })
        const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
        if (!res) {
          return false
        } else {
          this.saveparams = {
            savedatas: this.savedatas,
            roleNo: this.schData.roleNo,
          };
          await this.$axios.post('/api/v1/common/chems/role-menu', this.saveparams).then((res) => {
            if (res.data === 'Success') {
              this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
            this.selectMenu()
          })
        }
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
      }
    },
  },
}

// 그리드 세팅
const roleFields = [
  {
    fieldName: 'roleNo', // 권한 번호
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleTypeCodeName', // 권한 구분명
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplain', // 권한 설명
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplainName', // 권한 설명 다국어
    dataType: ValueType.TEXT,
  },
]

const menuFields = [
  {
    fieldName: 'menuNo',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'menuLevel',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'menuName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleCheck',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rowStatus',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
