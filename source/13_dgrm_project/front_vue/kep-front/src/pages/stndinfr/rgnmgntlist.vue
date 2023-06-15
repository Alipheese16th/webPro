<template>
  <div>
    <div class="location">
      <span>Home > {{ $t('LB00000696') }} > {{ $t('LB00000697') }}</span>
    </div>
    <div class="work_title">
      <h2>{{ $t('LB00000697') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="schData.cmpy_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="schData.wkpl_id"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
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
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <v-btn color="primary" depressed height="36" @click="selectRgnmgntList">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <h3>{{ $t('LB00000694') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 469px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!-- 저장 -->
          <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
        </div>
      </div>
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
    title: '지역도 관리',
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {
        cmpy_cd: '',
        wkpl_id: '',
        usg_yn: '',
      },
      frmData: {},
      tempData: {},
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      chkData: {},
      codeid: ['ST00000002', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
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
    routeTab() {
      return this.$t('LB00000697')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    this.schData.cmpy_cd = user.cmpn_cd
    this.schData.wkpl_id = user.wkpl_id
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = this.schData.cmpy_cd
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
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
        // 그리드 세팅
        const fields = [
          {
            fieldName: '_chk',
            dataType: ValueType.BOOLEAN,
          },
          {
            fieldName: 'wkpl_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'level',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cmpy_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_loc_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'upp_incl_wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'upp_wkpl_loc_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'upp_wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'sort_sn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'usg_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'addbtn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'del_yn',
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
            header: this.$t('LB00000705'), // 사업장ID
            name: 'wkpl_id',
            fieldName: 'wkpl_id',
            width: '100',
            editable: false,
            styles: {
              textAlignment: 'center',
            },
          },
          {
            header: this.$t('LB00000077'), // 레벨
            name: 'level',
            fieldName: 'level',
            width: '100',
            editable: false,
            styles: {
              textAlignment: 'center',
            },
          },
          {
            header: this.$t('LB00000700'), // 회사
            name: 'cmpy_nm',
            fieldName: 'cmpy_nm',
            width: '200',
            editable: false,
          },
          {
            header: this.$t('LB00000710'), // 지역도ID
            name: 'wkpl_loc_id',
            fieldName: 'wkpl_loc_id',
            width: '200',
            editable: false,
          },
          {
            header: this.$t('LB00000695'), // 지역도명
            name: 'wkpl_loc_nm',
            fieldName: 'wkpl_loc_nm',
            width: '200',
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
            header: this.$t('LB00000711'), // 상위포함지역도명
            name: 'upp_incl_wkpl_loc_nm',
            fieldName: 'upp_incl_wkpl_loc_nm',
            width: '200',
          },
          {
            header: this.$t('LB00000713'), // 상위 지역도
            name: 'upp_wkpl_loc_id',
            fieldName: 'upp_wkpl_loc_id',
            width: '200',
            editable: false,
          },
          {
            header: this.$t('LB00000714'), // 상위 지역도
            name: 'upp_wkpl_loc_nm',
            fieldName: 'upp_wkpl_loc_nm',
            width: '200',
            editable: false,
          },
          {
            header: this.$t('LB00000091'), // 정렬순서
            name: 'sort_sn',
            fieldName: 'sort_sn',
            width: '100',
            styles: {
              textAlignment: 'right',
            },
          },
          {
            header: this.$t('LB00000049'), // 사용여부
            name: 'usg_yn',
            fieldName: 'usg_yn',
            width: '100',
            sortable: false,
            lookupDisplay: true,
            editor: {
              type: 'dropdown',
              editable: true,
              domainOnly: true,
              textReadOnly: true,
              values: ['Y', 'N'],
              labels: ['Y', 'N'],
            },
          },
          {
            header: this.$t('LB00000225'), // 하위추가
            name: 'addbtn',
            fieldName: 'addbtn',
            width: '100',
            type: 'button',
            styleCallback(grid, dataCell) {
              const ret = {}
              if (dataCell.value !== '') {
                ret.styleName = 'btn_grid_add'
              }
              ret.editable = false
              return ret
            },
          },
          {
            header: this.$t('LB00000716'), // 삭제 가능여부
            name: 'del_yn',
            fieldName: 'del_yn',
            width: '200',
            styles: {
              textAlignment: 'center',
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

        gridView.onCellClicked = function (grid, index, clickData) {
          const current = gridView.getCurrent()
          if (gridView.getValue(current.dataRow, 'level') !== '3' && current.column === 'addbtn' && gridView.getValue(current.dataRow, 'addbtn') !== '') {
            gridView.commit()
            dataProvider.insertRows(current.dataRow + 1, [
              {
                _chk: false,
                wkpl_id: this.schData.wkpl_id,
                cmpy_nm: this.$refs.cmpy_cd.selectedItems[0].cd_nm,
                level: parseInt(dataProvider.getValue(current.dataRow, 'level')) + 1,
                wkpl_loc_id: '',
                wkpl_loc_nm: '',
                upp_incl_wkpl_loc_nm: dataProvider.getValue(current.dataRow, 'upp_incl_wkpl_loc_nm'),
                upp_wkpl_loc_id: dataProvider.getValue(current.dataRow, 'wkpl_loc_id'),
                upp_wkpl_loc_nm: dataProvider.getValue(current.dataRow, 'wkpl_loc_nm'),
                sort_sn: '',
                usg_yn: 'Y',
                addbtn: '',
                chk_yn: true,
              },
            ])
            this.totalcnt = gridView.getItemCount()
          }
        }.bind(this)

        gridView.setColumnProperty('del_yn', 'visible', false)
        gridView.setColumnProperty('upp_incl_wkpl_loc_nm', 'visible', false)
        gridView.setColumnProperty('wkpl_id', 'visible', false)
        gridView.setColumnProperty('upp_wkpl_loc_id', 'visible', false)
        // this.selectRgnmgntList()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    add() {
      if (this.isEmpty(this.schData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000292'), { className: 'toast_error' }) // 회사는 필수값입니다.
        return false
      }
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장은 필수값입니다.
        return false
      }
      gridView.commit()
      dataProvider.insertRows(0, [
        {
          _chk: false,
          wkpl_id: this.schData.wkpl_id,
          level: 1,
          cmpy_nm: this.$refs.cmpy_cd.selectedItems[0].cd_nm,
          wkpl_loc_id: '',
          wkpl_loc_nm: '',
          upp_incl_wkpl_loc_nm: '',
          upp_wkpl_loc_id: '',
          upp_wkpl_loc_nm: this.$refs.wkpl_id.selectedItems[0].cd_nm,
          sort_sn: '',
          usg_yn: 'Y',
          addbtn: '',
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
      for (let i = 0; i < checkedRows.length; i++) {
        if (dataProvider.getValue(checkedRows[i], 'del_yn') === 'N') {
          this.$toast.show(this.$t('MS00000065'), { className: 'toast_error' }) // 하위메뉴가 존재하여 삭제할 수 없습니다.
          return
        }
      }
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    async selectRgnmgntList() {
      if (this.isEmpty(this.schData.cmpy_cd)) {
        this.$toast.show(this.$t('MS00000292'), { className: 'toast_error' }) // 회사는 필수값입니다.
        return false
      }
      if (this.isEmpty(this.schData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장은 필수값입니다.
        return false
      }
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/rgnmgntlist`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData.wkpl_id = ''
      this.schData.usg_yn = ''
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      gridView.commit()
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) !== 'createAndDeleted' && dataProvider.getRowState(i) !== 'deleted') {
          if (this.isEmpty(dataProvider.getValue(i, 'wkpl_loc_nm'))) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_loc_nm' })
            this.$toast.show(this.$t('MS00000293'), { className: 'toast_error' }) // 지역도 명을 입력해주세요.
            return false
          } else if (dataProvider.getValue(i, 'wkpl_loc_nm').length > 100) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_loc_nm' })
            this.$toast.show(this.$t('MS00000294'), { className: 'toast_error' }) // 지역도 명이 100자를 초과했습니다.
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'sort_sn'))) {
            gridView.setCurrent({ dataRow: i, column: 'sort_sn' })
            this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }) // 정렬 순서를 입력해주세요.
            return false
          } else if (!/^[0-9]*$/.test(dataProvider.getValue(i, 'sort_sn'))) {
            this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }) // 정렬순서는 숫자만 입력해주세요.
            return false
          } else if (this.isEmpty(dataProvider.getValue(i, 'usg_yn'))) {
            gridView.setCurrent({ dataRow: i, column: 'usg_yn' })
            this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용 여부를 선택해주세요.
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
          temp.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
          temp.level = dataProvider.getValue(i, 'level')
          temp.wkpl_loc_id = dataProvider.getValue(i, 'wkpl_loc_id')
          temp.wkpl_loc_nm = dataProvider.getValue(i, 'wkpl_loc_nm')
          temp.upp_wkpl_loc_id = dataProvider.getValue(i, 'upp_wkpl_loc_id')
          temp.sort_sn = dataProvider.getValue(i, 'sort_sn')
          temp.usg_yn = dataProvider.getValue(i, 'usg_yn')
          if (dataProvider.getRowState(i) === 'created') {
            if (temp.level === '1') {
              temp.upp_incl_wkpl_loc_nm = dataProvider.getValue(i, 'wkpl_loc_nm')
            } else {
              temp.upp_incl_wkpl_loc_nm = dataProvider.getValue(i, 'upp_incl_wkpl_loc_nm') + ' > ' + dataProvider.getValue(i, 'wkpl_loc_nm') // 기존 상위 지역도 명에 내 명을 더함.
            }
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            if (temp.level === '1') {
              temp.upp_incl_wkpl_loc_nm = dataProvider.getValue(i, 'wkpl_loc_nm')
            } else {
              const uppLocNm = dataProvider.getValue(i, 'upp_incl_wkpl_loc_nm').split(' > ') // 기존 상위 포함 명 배열로 형성
              uppLocNm[uppLocNm.length - 1] = dataProvider.getValue(i, 'wkpl_loc_nm') // 마지막 데이터를 현재 명으로 변경
              temp.upp_incl_wkpl_loc_nm = uppLocNm.join(' > ') // 생성된 배열을 string으로 변경
            }
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow

        await this.$axios.post('/api/v1/st/stndinfr/rgnmgntlist', this.frmData).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.selectRgnmgntList()
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
