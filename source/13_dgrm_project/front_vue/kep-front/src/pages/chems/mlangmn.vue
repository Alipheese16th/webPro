<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--다국어관리-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000043') }}</span>
    </div>
    <div class="work_title">
      <!--다국어관리-->
      <h2>{{ $t('LB00000043') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--다국어유형-->
                <div class="label_tit">다국어유형</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.mlang_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000005"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000052') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.biz_cd"
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
                <!--라벨/메시지ID-->
                <div class="label_tit">{{ $t('LB00000044') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="searchdatas.mlang_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--언어-->
                <div class="label_tit">{{ $t('LB00000050') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.lang_cd"
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
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.usg_yn"
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
              <v-col cols="1">
                <!--라벨/메시지명-->
                <div class="label_tit">{{ $t('LB00000051') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.mlang_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--권한번호-->
                <div class="label_tit">{{ $t('LB00000226') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="searchdatas.role_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--메뉴번호-->
                <div class="label_tit">{{ $t('LB00000222') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="searchdatas.menu_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--코드그룹번호-->
                <div class="label_tit">{{ $t('LB00000241') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="searchdatas.cd_grp_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--코드-->
                <div class="label_tit">{{ $t('LB00000240') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="searchdatas.cd"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="599"
                  clearable
                  @keypress.enter="select()"
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
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--다국어 목록(KO)-->
        <h3>{{ $t('LB00000048') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="primary" outlined height="28" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="primary" outlined height="28" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div class="group_padd"></div>
    </div>

    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '다국어관리',
  },
  components: {
    // quillEditor,
  },
  fetch() {},

  data() {
    return {
      savedatas: [], //  저장될 배열 생성
      schData: {},
      codeid: ['CO00000001', 'CO00000002', 'CO00000005'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000002: [{ cd: '', cd_nm: '' }],
        CO00000005: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      selCO00000005: {
        cd: [],
        cd_nm: [],
      },
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      totalcnt: 0,
      searchdatas: {
        mlang_no: '',
        mlang_type_cd: '',
        biz_cd: '',
        lang_cd: '',
        usg_yn: '',
        mlang_cntn: '',
      },
      usrinfo: {},
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
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0) {
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
    this.usrinfo = this.$store.getters['auth/getUser']
    this.searchdatas.lang_cd = this.usrinfo.lang_cd
  },
  async mounted() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCO00000001.cd.push('')
        this.selCO00000001.cd_nm.push('SELECT')
        this.selCO00000005.cd.push('')
        this.selCO00000005.cd_nm.push('SELECT')
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })
        res.CO00000005.forEach((i) => {
          this.selCO00000005.cd.push(i.cd)
          this.selCO00000005.cd_nm.push(i.cd_nm)
        })

        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000005.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })

    // 다국어 데이터프로바이더 셋팅
    const fields = [
      {
        fieldName: 'mlang_no', // 라벨/메시지ID(KO)
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mlang_type_cd', // 다국어 유형
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'biz_cd', // 업무구분(KO)
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mlang_cntn', // 라벨/메시지
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usg_yn', // 사용여부
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'role_no', // 권한번호
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'menu_no', // 메뉴번호
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cd_grp_no', // 코드그룹번호
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cd', // 코드
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usr_nm', // 등록자
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_dt', // 등록일
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'row_status', // 셀 행 등록 상태
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lang_cd', // 언어코드
        dataType: ValueType.TEXT,
      },
    ]

    // 다국어 그리드 셋팅
    const columns = [
      {
        header: this.$t('LB00000044'), // 라벨/메시지ID(KO)
        name: 'mlang_no',
        fieldName: 'mlang_no',
        width: '100',
      },
      {
        header: this.$t('LB00000053'), // 다국어 유형
        name: 'mlang_type_cd',
        fieldName: 'mlang_type_cd',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        values: this.selCO00000005.cd,
        labels: this.selCO00000005.cd_nm,
        editor: {
          type: 'list',
          domainOnly: true,
          textReadOnly: true,
          values: ['', 'LB', 'MS'],
          labels: ['SELECT', this.$t('LB00000058'), this.$t('LB00000057')], // 라벨 // 메세지
          displayLabels: 'label',
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          const mlangno = grid.getValue(dataCell.index.itemIndex, 'mlang_no')
          if (mlangno !== '') {
            ret.editable = false
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000052'), // 업무구분(KO)
        name: 'biz_cd',
        fieldName: 'biz_cd',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        values: this.selCO00000001.cd,
        labels: this.selCO00000001.cd_nm,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: this.$t('LB00000056'), // 라벨/메시지
        name: 'mlang_cntn',
        fieldName: 'mlang_cntn',
        width: '200',
        editor: {
          maxLength: 560,
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
          domainOnly: true,
          textReadOnly: true,
          values: ['Y', 'N'],
          labels: ['Y', 'N'],
        },
      },
      {
        header: this.$t('LB00000226'), // 권한번호
        name: 'role_no',
        fieldName: 'role_no',
        width: '120',
      },
      {
        header: this.$t('LB00000222'), // 메뉴번호
        name: 'menu_no',
        fieldName: 'menu_no',
        width: '120',
      },
      {
        header: this.$t('LB00000241'), // 코드그룹번호
        name: 'cd_grp_no',
        fieldName: 'cd_grp_no',
        width: '120',
      },
      {
        header: this.$t('LB00000240'), // 코드
        name: 'cd',
        fieldName: 'cd',
        width: '120',
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'usr_nm',
        fieldName: 'usr_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
    ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })

    gridView.rowIndicator.visible = true
    gridView.displayOptions.selectionStyle = 'block'
    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('mlang_no', 'editable', false)
    gridView.setColumnProperty('usr_nm', 'editable', false)
    gridView.setColumnProperty('upt_dt', 'editable', false)
    gridView.setColumnProperty('role_no', 'editable', false)
    gridView.setColumnProperty('menu_no', 'editable', false)
    gridView.setColumnProperty('cd_grp_no', 'editable', false)
    gridView.setColumnProperty('cd', 'editable', false)
    gridView.setEditOptions({
      insertable: true,
      appendable: true,
    })
    await this.select()
  },
  methods: {
    async select() {
      gridView.commit()
      const rows = await this.$axios.$get(`/api/v1/common/chems/mlang`, { params: this.searchdatas })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    // 검색조건 초기화
    reset() {
      gridView.commit()
      this.searchdatas.mlang_type_cd = ''
      this.searchdatas.mlang_no = ''
      this.searchdatas.biz_cd = ''
      this.searchdatas.usg_yn = ''
      this.searchdatas.mlang_cntn = ''
      this.searchdatas.lang_cd = this.usrinfo.lang_cd
      this.searchdatas.role_no = ''
      this.searchdatas.menu_no = ''
      this.searchdatas.cd_grp_no = ''
      this.searchdatas.cd = ''
    },
    add() {
      gridView.commit()
      dataProvider.insertRows(0, [
        {
          mlang_no: '',
          mlang_type_cd: '',
          biz_cd: '',
          mlang_cntn: '',
          usg_yn: 'Y',
          lang_cd: '',
        },
      ])
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.removeRows(checkedRows)
      dataProvider.hideRows(checkedRows)
      this.totalcnt = gridView.getItemCount()
    },
    validationChk(lowstatus) {
      if (gridView.getItemCount() <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return false
      }
      if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return false
      }
      // const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      const chkRow = [...lowstatus.created, ...lowstatus.updated]
      if (chkRow.length === 0) {
        return true
      }
      for (let i = 0; i < chkRow.length; i++) {
        if (dataProvider.getValue(chkRow[i], 'mlang_type_cd') === '') {
          this.$toast.show(this.$t('MS00000012'), { className: 'toast_error' }) // 다국어 유형을 선택해 주세요.
          gridView.setCurrent({ dataRow: i, column: 'mlang_type_cd' })
          return false
        } else if (dataProvider.getValue(chkRow[i], 'biz_cd') === '') {
          this.$toast.show(this.$t('MS00000018'), { className: 'toast_error' }) // 업무구분을 선택해 주세요.
          gridView.setCurrent({ dataRow: i, column: 'biz_cd' })
          return false
        } else if (dataProvider.getValue(chkRow[i], 'mlang_cntn') === '') {
          this.$toast.show(this.$t('MS00000017'), { className: 'toast_error' }) // 라벨/메세지를 입력해 주세요.
          gridView.setCurrent({ dataRow: i, column: 'mlang_cntn' })
          return false
        } else if (dataProvider.getValue(chkRow[i], 'usg_yn') === '') {
          this.$toast.show(this.$t('MS00000016'), { className: 'toast_error' }) // 사용여부를 선택해 주세요.
          gridView.setCurrent({ dataRow: i, column: 'usg_yn' })
          return false
        }
        if (i + 1 === chkRow.length) {
          return true
        }
      }
    },
    async createPush(lowstatus) {
      // 새로 생성된 row push
      if ((await dataProvider.getRowStateCount('created')) > 0) {
        if ((await this.searchdatas.lang_cd) === '') {
          this.$toast.show(this.$t('MS00000015'), { className: 'toast_error' }) // 언어코드를 선택해 주세요.
          return false
        } else {
          lowstatus.created.forEach((i) => {
            dataProvider.setValue(i, 'row_status', 'C')
            dataProvider.setValue(i, 'lang_cd', this.searchdatas.lang_cd)
            this.savedatas.push(dataProvider.getJsonRows(i, i))
          })
          return true
        }
      } else {
        return true
      }
    },
    async updatePush(lowstatus) {
      // 수정된 row push
      if ((await dataProvider.getRowStateCount('updated')) > 0) {
        lowstatus.updated.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'U')
          this.savedatas.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async deletePush(lowstatus) {
      // 삭제 row push
      if ((await dataProvider.getRowStateCount('deleted')) > 0) {
        lowstatus.deleted.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'D')
          this.savedatas.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async save() {
      gridView.commit()
      // 바뀐 row 가 한개라도 있다면 저장 시작
      const lowstatus = dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      this.savedatas = [] //  저장될 배열 비우기
      if (await this.validationChk(lowstatus)) {
        const crechk = await this.createPush(lowstatus)
        const upchk = await this.updatePush(lowstatus)
        const dechk = await this.deletePush(lowstatus)
        if (await (crechk && upchk && dechk)) {
          // 저장 여부 확인
          const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
          if (!res) {
            return false
          } else {
            this.saveparams = {}
            this.saveparams.savedatas = this.savedatas
            await this.$axios.post('/api/v1/common/chems/mlang', this.saveparams).then((res) => {
              if (res.data === 'Success') {
                this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
              }
              this.select()
            })
          }
        }
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
