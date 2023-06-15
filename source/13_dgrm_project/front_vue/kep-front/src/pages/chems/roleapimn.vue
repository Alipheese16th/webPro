<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--권한별 API관리-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000088') }}</span>
    </div>
    <div class="work_title">
      <!--권한별 API관리-->
      <h2>{{ $t('LB00000088') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--API 업무-->
                <div class="label_tit">{{ $t('LB00000085') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.businessCode"
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
                <!--API 상세업무-->
                <div class="label_tit">{{ $t('LB00000084') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.detailBusinessCode"
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
                <!--API 설명-->
                <div class="label_tit">{{ $t('LB00000087') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.apiExplain"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="299"
                  @keypress.enter="selectApi()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--API URL-->
                <div class="label_tit">{{ $t('LB00000086') }}</div>
              </v-col>
              <v-col cols="7">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.apiUrl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="299"
                  @keypress.enter="selectApi()"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="selectApi">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <v-row>
        <v-col cols="4">
          <div class="realgrid_container">
            <div class="sub_title">
              <!--권한 목록(KO)-->
              <h3>{{ $t('LB00000079') }}</h3>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
        <v-col cols="8 shuttle_padd">
          <div class="realgrid_container">
            <div class="sub_title">
              <!--권한 API-->
              <h3>{{ $t('LB00000083') }}</h3>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </div>
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
let apiGridView = GridView
let apiDataProvider = LocalDataProvider

export default {
  meta: {
    title: '권한별 API관리',
  },
  components: {
  },
  data() {
    return {
      codeid: ['CO00000001', 'CO00000008'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000008: [{ cd: '', cd_nm: '' }],
      },
      chgUpCodes: [{ cd: '', cd_nm: '' }],
      savedatas: [], //  저장될 배열 생성
      searchdatas: {
        roleNo: '',
        businessCode: '',
        detailBusinessCode: '',
        apiExplain: '',
        apiUrl: '',
      },
    }
  },
  async beforePageLeave(tab, type) {
    apiGridView.commit()
    if (apiDataProvider.getRowStateCount('all') - apiDataProvider.getRowStateCount('createAndDeleted') > 0) {
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
  async mounted() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000008.unshift({ cd: '', cd_nm: 'ALL' })
        this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
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
      this.searchdatas.roleNo = value
      // 권한별 메뉴 매핑정보 조회
      this.selectApi()
    }.bind(this)

    apiDataProvider = new LocalDataProvider(false)
    apiDataProvider.setFields(apiFields)
    apiGridView = new GridView('realgrid2')
    apiGridView.setDataSource(apiDataProvider)
    const apiColumns = [
      {
        header: this.$t('LB00000082'), // 업무
        name: 'businessCodeName',
        fieldName: 'businessCodeName',
        width: '100',
      },
      {
        header: this.$t('LB00000081'), // 상세업무
        name: 'detailBusinessCodeName',
        fieldName: 'detailBusinessCodeName',
        width: '200',
      },
      {
        header: this.$t('LB00000087'), // API 설명
        name: 'apiExplain',
        fieldName: 'apiExplain',
        width: '100',
      },
      {
        header: this.$t('LB00000086'), // API URL
        name: 'apiUrl',
        fieldName: 'apiUrl',
        width: '150',
        styleName: 'left',
      },
      {
        header: {
          text: 'get',
          checkLocation: 'left',
        },
        name: 'getMethodYn',
        fieldName: 'getMethodYn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: '0',
          trueValues: '1',
        },
      },
      {
        header: {
          text: 'post',
          checkLocation: 'left',
        },
        name: 'postMethodYn',
        fieldName: 'postMethodYn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: '0',
          trueValues: '1',
        },
      },
      {
        header: {
          text: 'put',
          checkLocation: 'left',
        },
        name: 'putMethodYn',
        fieldName: 'putMethodYn',
        width: '50',
        editable: false,
        renderer: {
          type: 'check',
          shape: 'box',
          falseValues: '0',
          trueValues: '1',
        },
      },
      {
        header: {
          text: 'delete',
          checkLocation: 'left',
        },
        name: 'deleteMethodYn',
        fieldName: 'deleteMethodYn',
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
    apiGridView.setColumns(apiColumns)
    apiGridView.setFooters({ visible: false })

    apiGridView.setStateBar({ visible: false })
    apiGridView.setCheckBar({ visible: false })
    apiGridView.editOptions.editable = true
    apiGridView.displayOptions.selectionStyle = 'block'

    apiGridView.header.height = 39
    apiGridView.displayOptions.rowHeight = 40
    apiGridView.footer.height = 40
    apiGridView.displayOptions.fitStyle = 'fill'

    apiGridView.onCellClicked = function (grid, index, clickData) {
      const current = apiGridView.getCurrent()
      console.log(current) // eslint-disable-line no-console
      console.log(index) // eslint-disable-line no-console
      // console.log(apiGridView)
      if (current.itemIndex < 0) {
        apiGridView.columnByName(index.column)._checked = false
        return false
      }
      if (index.cellType === 'header' || index.cellType === 'head') {
        const test = apiDataProvider.getFieldValues(apiGridView.columnByName(index.column), 0, 2)
        console.log(test) // eslint-disable-line no-console
        // console.log(apiGridView.columnByName(index.column))
        // apiGridView.checkRows(true)
      }
      // const value = apiDataProvider.getValue(current.dataRow, 'roleNo')
      // this.searchdatas.roleNo = value
    }

    // 권한 목록 조회
    this.selectRole()
  },
  methods: {
    dtlChange() {
      this.chgUpCodes = [{ cd: '', cd_nm: '' }]
      this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL' })
      this.codes.CO00000008.forEach((i) => {
        if (i.up_cd === this.searchdatas.businessCode) {
          this.chgUpCodes.push({ cd: i.cd, cd_nm: i.cd_nm })
        }
      })
    },
    async reset() {
      apiGridView.commit()
      const role = await this.searchdatas.roleNo
      this.searchdatas = await {}
      this.searchdatas.roleNo = await role
      this.chgUpCodes = [{ cd: '', cd_nm: '' }]
      this.chgUpCodes.unshift({ cd: '', cd_nm: 'ALL' })
    },
    async selectRole() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/role-menu`, { params: this.searchdatas })
      roleDataProvider.setRows(rows)
      roleGridView.refresh()
      roleGridView.setTopItem(0)
    },
    async selectApi() {
      apiGridView.commit()
      if ((await this.searchdatas.roleNo) === '') {
        this.$toast.show(this.$t('MS00000023'), { className: 'toast_error' }) // 권한을 선택해 주세요.
      } else {
        const rows = await this.$axios.$get(`/api/v1/common/chems/role-api`, { params: this.searchdatas })
        apiDataProvider.setRows(rows)
        apiGridView.refresh()
        apiGridView.setTopItem(0)
      }
    },
    async save() {
      apiGridView.commit()
      this.savedatas = [] //  저장될 배열 비우기
      const lowstatus = apiDataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      // 수정된 row push
      if ((await apiDataProvider.getRowStateCount('updated')) > 0) {
        await lowstatus.updated.forEach((i) => {
          apiDataProvider.setValue(i, 'rowStatus', 'U')
          this.savedatas.push(apiDataProvider.getJsonRow(i, i))
        })
        const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
        if (!res) {
          return false
        } else {
          this.saveparams = {
            savedatas: this.savedatas,
            roleNo: this.searchdatas.roleNo,
          };
          await this.$axios.post('/api/v1/common/chems/role-api', this.saveparams).then((res) => {
            if (res.data === 'Success') {
              this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
            this.selectApi()
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
    fieldName: 'roleNo',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleTypeCodeName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplain',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplainName',
    dataType: ValueType.TEXT,
  },
]

const apiFields = [
  {
    fieldName: 'apiNo',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'businessCodeName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'detailBusinessCodeName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'apiExplain',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'apiUrl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'getMethodYn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'postMethodYn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'putMethodYn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'deleteMethodYn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rowStatus',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
