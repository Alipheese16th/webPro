<template>
  <div>
    <div class="location">
      <!-- 시스템관리 -->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('템플릿관리') }}</span>
    </div>
    <div class="work_title">
      <!-- 템플릿관리 -->
      <h2>{{ $t('템플릿관리') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000052') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.bizCd"
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
                <!--업무구분-->
                <div class="label_tit">{{ $t('템플릿구분') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.tmptDivCd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000039"
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
                  v-model="searchdatas.usgYn"
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
            <v-row>
              <v-col cols="1">
                <!--템플릿명-->
                <div class="label_tit">{{ $t('템플릿명') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.tmptNm"
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
              </v-col>
              <v-col cols="3">
              </v-col>
              <v-col cols="1">
              </v-col>
              <v-col cols="3">
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="schReset()">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!-- 템플릿 목록 -->
        <h3>{{ $t('템플릿 목록') }}</h3>
        <v-spacer></v-spacer>
        <div>
          <!-- 추가 -->
          <v-btn color="" outlined height="28" @click="openNew()">{{ $t('LB00000047') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 581px"></div>
    </div>
    <popup ref="pop" @close="refresh" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from './tmptcntn'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '템플릿 관리',
  },
  components: {
    popup,
  },
  data() {
    return {
      savedatas: [], //  저장될 배열 생성
      schData: {},
      codeid: ['CO00000001', 'CO00000039'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000039: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      selCO00000039: {
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
        tmptNm: '',
        bizCd: '',
        usgYn: '',
        tmptDivCd: '',
      },
      usrinfo: {},

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
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCO00000001.cd.push('')
        this.selCO00000001.cd_nm.push('SELECT')
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })
        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })

        this.selCO00000039.cd.push('')
        this.selCO00000039.cd_nm.push('SELECT')
        res.CO00000039.forEach((i) => {
          this.selCO00000039.cd.push(i.cd)
          this.selCO00000039.cd_nm.push(i.cd_nm)
        })
        this.codes.CO00000039.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })

    // 그리드 세팅
    const fields = [
      {
        fieldName: 'tmptId',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'bizCd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'bizNm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tmptNm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usgYn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tmptDivCd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tmptDivNm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tmptCntn',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000062'), // 업무구분
        name: 'bizNm',
        fieldName: 'bizNm',
        width: '80',
      },
      {
        header: this.$t('템플릿구분'), // 템플릿구분
        name: 'tmptDivNm',
        fieldName: 'tmptDivNm',
        width: '80',
      },
      {
        header: this.$t('템플릿ID'), // 템플릿ID
        name: 'tmptId',
        fieldName: 'tmptId',
        width: '80',
      },
      {
        header: this.$t('템플릿명'), // 템플릿명
        name: 'tmptNm',
        fieldName: 'tmptNm',
        width: '80',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'usgYn',
        fieldName: 'usgYn',
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
    gridView.setCheckBar({ visible: false })
    gridView.setRowIndicator({ width: 50 })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.onCellClicked = (grid, index, clickData) => {
      if (index.column === 'tmptNm') {
        this.frmData.tmptId = dataProvider.getValue(index.dataRow, 'tmptId')
        this.frmData.bizCd = dataProvider.getValue(index.dataRow, 'bizCd')
        this.frmData.tmptNm = dataProvider.getValue(index.dataRow, 'tmptNm')
        this.frmData.usgYn = dataProvider.getValue(index.dataRow, 'usgYn')
        this.frmData.tmptDivCd = dataProvider.getValue(index.dataRow, 'tmptDivCd')
        this.frmData.tmptCntn = dataProvider.getValue(index.dataRow, 'tmptCntn')
        this.$refs.pop.open(false, this.frmData, '')
      }
    }

    this.select()
  },
  methods: {
    openNew() {
      this.frmData.tempId = ''
      this.$refs.pop.open(false, this.frmData, '')
    },
    validation() {
      return true
    },
    async select() {
      // 메뉴 목록 조회
      const rows = await this.$axios.$get(`/api/v1/common/chems/tmpt-list`, { params: this.searchdatas })

      // 데이터 등록
      dataProvider.setRows(rows)
      gridView.refresh()
    },
    refresh() {
      this.select()
    },
    schReset() {
      gridView.commit()
      this.searchdatas = {
        tmptNm: '',
        tmptDivCd: '',
        bizCd: '',
        usgUn: '',
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
