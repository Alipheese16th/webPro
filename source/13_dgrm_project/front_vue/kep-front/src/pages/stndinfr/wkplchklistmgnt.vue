<template>
  <div>
    <div class="location">
      <!-- Home > 기준정보 > 작업 체크리스트 관리 -->
      <span>Home > {{ $t('LB00000696') }} > {{ $t('LB00001229') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업 체크리스트 관리 -->
      <h2>{{ $t('LB00001229') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--구분-->
                <div class="label_tit">{{ $t('LB00000741') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_chec_cl_cd"
                  v-model="schData.wkpl_chec_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000012"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--상세구분-->
                <div class="label_tit">{{ $t('LB00001228') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_chec_dtl_cl_cd"
                  v-model="schData.wkpl_chec_dtl_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.ST00000013"
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
                  ref="use_yn"
                  v-model="schData.use_yn"
                  label="Y"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="use_yn_cd"
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
            <v-btn color="" outlined height="36" @click="schReset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!--체크리스트 목록-->
      <div class="sub_title">
        <h3>{{ $t('LB00001227') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
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

export default {
  meta: {
    title: '작업 체크리스트 관리',
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_dtl_cl_cd: '',
        use_yn: '',
      },
      schTempData: {},
      listData: {
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_dtl_cl_cd: '',
        wkpl_chec_sn: '',
        wkpl_chec_in_cl_cd: '',
        wkpl_chec_txt: '',
        mand_chec_yn: '',
        usg_yn: '',
      },
      tempData: {
        wkpl_id: '',
        wkpl_chec_cl_cd: '',
        wkpl_chec_dtl_cl_cd: '',
        wkpl_chec_sn: '',
        wkpl_chec_in_cl_cd: '',
        wkpl_chec_txt: '',
        mand_chec_yn: '',
        usg_yn: '',
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['ST00000012', 'ST00000013', 'ST00000009'],
      codes: {
        ST00000012: [{ cd: '', cd_nm: '' }],
        ST00000013: [{ cd: '', cd_nm: '' }],
        ST00000009: [{ cd: '', cd_nm: '' }],
      },
      selST00000012: {
        cd: [],
        cd_nm: [],
      },
      selST00000013: {
        cd: [],
        cd_nm: [],
      },
      selST00000009: {
        cd: [],
        cd_nm: [],
      },
    }
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || JSON.stringify(this.tempData) !== JSON.stringify(this.listData)) {
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
      return this.$t('LB00001230') // 사업장 체크리스트 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {},
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selST00000012.cd.push('')
        this.selST00000012.cd_nm.push('SELECT')
        this.selST00000013.cd.push('')
        this.selST00000013.cd_nm.push('SELECT')
        this.selST00000009.cd.push('')
        this.selST00000009.cd_nm.push('SELECT')
        res.ST00000012.forEach((i) => {
          this.selST00000012.cd.push(i.cd)
          this.selST00000012.cd_nm.push(i.cd_nm)
        })
        res.ST00000013.forEach((i) => {
          this.selST00000013.cd.push(i.cd)
          this.selST00000013.cd_nm.push(i.cd_nm)
        })
        res.ST00000009.forEach((i) => {
          this.selST00000009.cd.push(i.cd)
          this.selST00000009.cd_nm.push(i.cd_nm)
        })
        this.codes.ST00000012.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.ST00000013.unshift({ cd: '', cd_nm: 'ALL' })

        // 그리드 세팅
        const fields = [
          {
            fieldName: 'wkpl_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_chec_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_chec_dtl_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_chec_sn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_chec_in_cl_cd',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_chec_txt',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'mand_chec_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'usg_yn',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: this.$t('LB00000741'), // 구분
            name: 'wkpl_chec_cl_cd',
            fieldName: 'wkpl_chec_cl_cd',
            width: '200',
            lookupDisplay: true,
            values: this.selST00000012.cd,
            labels: this.selST00000012.cd_nm,
            styleName: 'left',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00001228'), // 상세구분
            name: 'wkpl_chec_dtl_cl_cd',
            fieldName: 'wkpl_chec_dtl_cl_cd',
            width: '100',
            lookupDisplay: true,
            values: this.selST00000013.cd,
            labels: this.selST00000013.cd_nm,
            styleName: 'left',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00001233'), // 체크리스트 내용
            name: 'wkpl_chec_txt',
            fieldName: 'wkpl_chec_txt',
            width: '300',
            styleName: 'left',
          },
          {
            header: this.$t('LB00001232'), // 입력구분
            name: 'wkpl_chec_in_cl_cd',
            fieldName: 'wkpl_chec_in_cl_cd',
            width: '100',
            lookupDisplay: true,
            values: this.selST00000009.cd,
            labels: this.selST00000009.cd_nm,
            styleName: 'left',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              displayLabels: 'label',
            },
          },
          {
            header: this.$t('LB00001231'), // 필수 대상여부
            name: 'mand_chec_yn',
            fieldName: 'mand_chec_yn',
            width: '100',
            editor: {
              type: 'dropdown',
              domainOnly: true,
              textReadOnly: true,
              values: ['Y', 'N'],
              labels: ['Y', 'N'],
            },
          },
          {
            header: this.$t('LB00000049'), // 사용여부
            name: 'usg_yn',
            fieldName: 'usg_yn',
            width: '100',
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
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.editOptions.editable = true
        gridView.displayOptions.selectionStyle = 'block'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('id', 'visible', false)

        this.select()
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
          wkpl_id: 'S00',
          wkpl_chec_cl_cd: '',
          wkpl_chec_dtl_cl_cd: '',
          wkpl_chec_sn: '',
          wkpl_chec_in_cl_cd: '',
          wkpl_chec_txt: '',
          mand_chec_yn: 'Y',
          usg_yn: 'Y',
        },
      ])
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)

      this.totalcnt = gridView.getItemCount()
    },
    async select() {
      gridView.commit()
      if (this.schValidation()) {
        const rows = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-chk-list`, { params: this.schData })
        this.tempData = JSON.parse(JSON.stringify(this.listData))
        dataProvider.setRows(rows)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()
        this.schTempData = JSON.parse(JSON.stringify(this.schData)) // 수정여부 비교 데이터
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    schReset() {
      this.schData = {
        wkpl_chec_cl_cd: '',
        wkpl_chec_dtl_cl_cd: '',
        use_yn: '',
      }
    },
    schValidation() {
      return true
    },
    validation() {
      gridView.commit()
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' })
        return false
      }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) !== 'createAndDeleted' && dataProvider.getRowState(i) !== 'deleted') {
          if (this.isEmpty(dataProvider.getValue(i, 'wkpl_chec_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_chec_cl_cd' })
            this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
            return false
          }
          if (this.isEmpty(dataProvider.getValue(i, 'wkpl_chec_dtl_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_chec_dtl_cl_cd' })
            this.$toast.show(this.$t('MS00000639'), { className: 'toast_error' }) // 상세구분을 선택해주세요.
            return false
          }
          if (this.isEmpty(dataProvider.getValue(i, 'wkpl_chec_txt'))) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_chec_txt' })
            this.$toast.show(this.$t('MS00000638'), { className: 'toast_error' }) // 체크리스트 내용을 작성해주세요.
            return false
          }
          if (!this.isEmpty(dataProvider.getValue(i, 'wkpl_chec_txt')) && dataProvider.getValue(i, 'wkpl_chec_txt').length > 1000) {
            gridView.setCurrent({ dataRow: i, fieldName: 'wkpl_chec_txt' })
            this.$toast.show(this.$t('MS00000637'), { className: 'toast_error' }) // 체크리스트 내용은 1000자 이하로 입력해주세요.
            return false
          }
          if (this.isEmpty(dataProvider.getValue(i, 'wkpl_chec_in_cl_cd'))) {
            gridView.setCurrent({ dataRow: i, column: 'wkpl_chec_in_cl_cd' })
            this.$toast.show(this.$t('MS00000636'), { className: 'toast_error' }) // 입력구분을 선택해주세요.
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
          temp.wkpl_id = 'S00'
          temp.wkpl_chec_cl_cd = dataProvider.getValue(i, 'wkpl_chec_cl_cd')
          temp.wkpl_chec_dtl_cl_cd = dataProvider.getValue(i, 'wkpl_chec_dtl_cl_cd')
          temp.wkpl_chec_sn = dataProvider.getValue(i, 'wkpl_chec_sn')
          temp.wkpl_chec_in_cl_cd = dataProvider.getValue(i, 'wkpl_chec_in_cl_cd')
          temp.wkpl_chec_txt = dataProvider.getValue(i, 'wkpl_chec_txt')
          temp.mand_chec_yn = dataProvider.getValue(i, 'mand_chec_yn')
          temp.usg_yn = dataProvider.getValue(i, 'usg_yn')
          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }

        this.listData.insertRow = insertRow
        this.listData.updateRow = updateRow
        this.listData.deleteRow = deleteRow

        await this.$axios.put('/api/v1/st/stndinfr/wkpl-chk-list', this.listData).then((res) => {
          console.log(res.data) // eslint-disable-line no-console
          if (res.data.result === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.select()
          } else if (res.data === 'OverlapFail') {
            this.$toast.show(this.$t('MS00000419'), { className: 'toast_error' }) // 중복된 데이터가 있습니다.
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
