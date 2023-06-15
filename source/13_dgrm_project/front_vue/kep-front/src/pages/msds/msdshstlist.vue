<template>
  <div>
    <div class="location">
      <!-- MSDS 개정이력조회 -->
      <span>Home > MSDS > {{ $t('LB00000329') }}</span>
    </div>
    <div class="work_title">
      <h2>{{ $t('LB00000329') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000006"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--자재구분-->
                <div class="label_tit">{{ $t('LB00000022') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.mtrl_clsf_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000017"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--품목구분-->
                <div class="label_tit">{{ $t('LB00000023') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.macl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000006"
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
                <!--자재코드-->
                <div class="label_tit">{{ $t('LB00000024') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="schData.mtrl_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="19"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--자재명-->
                <div class="label_tit">{{ $t('LB00000025') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.mtrl_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--구성물질-->
                <div class="label_tit">{{ $t('LB00000026') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.sbst_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('MS00000009')"
                  clearable
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--법률-->
                <div class="label_tit">{{ $t('LB00000027') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.law_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CH00000003"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="lawChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--규제-->
                <div class="label_tit">{{ $t('LB00000028') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.rglt_no"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.F_CH00000001"
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
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="schReset()">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <h3>{{ $t('LB00000330') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">
          <!--총 건-->
          {{ $t('LB00000039') }}<em> {{ size }} </em>{{ $t('LB00000040') }}
          <!--붉은색 자재명은 MSDS 제출 대상 자재입니다.-->
          <div class="label_info mrL16">{{ $t('LB00000619') }}</div>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
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
    title: 'MSDS-MSDS개정이력조회',
    key(route) {
      return `/msds/msdshst/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      schData: {
        mtrl_clsf_cd: '',
      },
      frmData: {},
      menu1: false,
      menu2: false,
      size: 0,
      codeid: ['CO00000006', 'CO00000017', 'CH00000006', 'CH00000003'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }],
        CO00000017: [{ cd: '', cd_nm: '' }],
        CH00000006: [{ cd: '', cd_nm: '' }],
        CH00000003: [{ cd: '', cd_nm: '' }],
        lawCd: [{ cd: '', cd_nm: '' }],
        F_CH00000001: [{ cd: '', cd_nm: 'ALL' }],
        F_CH00000006: [{ cd: '', cd_nm: 'ALL' }],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000329')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.frmData.username = this.$store.getters['auth/getUsername']
  },
  mounted() {
    const columns = [
      {
        header: this.$t('LB00000275'), // 개정요청번호
        name: 'msds_rev_no',
        fieldName: 'msds_rev_no',
        width: '100',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: 'wkpl_nm',
        fieldName: 'wkpl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000022'), // 자재구분
        name: 'mtrl_clsf_nm',
        fieldName: 'mtrl_clsf_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000023'), // 품목구분
        name: 'macl_nm',
        fieldName: 'macl_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000024'), // 자재코드
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
      },
      {
        header: this.$t('LB00000025'), // 자재명
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '300',
        styleName: 'link left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const mtrlClsfCd = grid.getValue(dataCell.index.itemIndex, 'mtrl_clsf_cd')
          const impYn = grid.getValue(dataCell.index.itemIndex, 'imp_yn')
          const sbmsTgtYn = grid.getValue(dataCell.index.itemIndex, 'sbms_tgt_yn')
          // eslint-disable-next-line
          if (mtrlClsfCd === '1' && impYn === 'Y' && sbmsTgtYn === 'Y') {
            ret.styleName = 'link2 left'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000034'), // 최근 개정일
        name: 'msds_last_rev_da',
        fieldName: 'msds_last_rev_da',
        width: '100',
      },
      {
        header: this.$t('LB00000266'), // MSDS버전
        name: 'rev_af_ver',
        fieldName: 'rev_af_ver',
        width: '100',
      },
      {
        header: this.$t('LB00000177'), // MSDS(국문)
        name: 'msds_ko_ver',
        fieldName: 'msds_ko_ver',
        width: '100',
        // styleName: 'btn_download',
        styleCallback(grid, dataCell) {
          const ret = {}
          const atfileno = grid.getValue(dataCell.index.itemIndex, 'ko_atfile_no')
          if (typeof atfileno !== 'undefined' && atfileno !== null && atfileno !== '' && atfileno !== 0) {
            ret.styleName = 'btn_download'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000176'), // MSDS(영문)
        name: 'msds_en_ver',
        fieldName: 'msds_en_ver',
        width: '100',
        // styleName: 'btn_download',
        styleCallback(grid, dataCell) {
          const ret = {}
          const atfileno = grid.getValue(dataCell.index.itemIndex, 'en_atfile_no')
          if (typeof atfileno !== 'undefined' && atfileno !== null && atfileno !== '' && atfileno !== 0) {
            ret.styleName = 'btn_download'
          }
          return ret
        },
      },
    ]

    // const headergroup = [
    //   'msds_rev_no',
    //   'mtrl_clsf_cd',
    //   'macl_nm',
    //   'wkpl_nm',
    //   'mtrl_no',
    //   'mtrl_nm',
    //   'msds_last_rev_da',
    //   'rev_af_ver',
    //   'msds_ver_cntn',
    //   {
    //     name: 'companyGroup',
    //     direction: 'horizontal',
    //     items: ['msds_ko_ver', 'msds_en_ver'],
    //     header: {
    //       text: 'MSDS',
    //     },
    //   },
    // ]

    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    // gridView.setColumnLayout(headergroup)
    gridView.setRowIndicator({ width: 50 })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.onCellClicked = function (grid, index, clickData) {
      gridView.commit()
      if (index.itemIndex > -1) this.openDetail(index)
    }.bind(this)

    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes = res
      this.codes.CO00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CO00000017.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.codes.CH00000003.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.$axios.$get(`/api/v1/ch/msds/law`).then((res) => {
        this.codes.lawCd = res
        if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
          this.schData = this.$route.params.schData
          this.lawInit(this.schData.law_cd)
          this.codInit(this.schData.mtrl_clsf_cd)
        } else {
          this.lawChg('')
          this.codChg('')
        }
        this.select()
      })
    })
  },
  methods: {
    async select() {
      const rows = await this.$axios.$get(`/api/v1/ch/msds/msds-hst`, { params: this.schData })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)

      this.size = dataProvider.getRowCount()
    },
    schReset() {
      this.schData = {
        wkpl_id: '',
        mtrl_clsf_cd: '',
        macl_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        sbst_nm: '',
        law_cd: '',
        rglt_no: '',
      }
      this.codChg('')
    },
    openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column === 'mtrl_nm') {
        const value = gridView.getValue(index.itemIndex, 'msds_rev_no')
        this.$router.push({
          name: `msds-msdshstlup-msdshstlup`,
          params: {
            schData: this.schData,
            msdshstlup: value,
          },
        })
      } else if (index.column === 'msds_ko_ver') {
        // 다운로드 처리
        const koDownKey = gridView.getValue(index.itemIndex, 'ko_atfile_key')
        const koDownNm = gridView.getValue(index.itemIndex, 'ko_atfile_nm')
        this.download(koDownKey, koDownNm)
      } else if (index.column === 'msds_en_ver') {
        // 다운로드 처리
        const enDownKey = gridView.getValue(index.itemIndex, 'en_atfile_key')
        const enDownNm = gridView.getValue(index.itemIndex, 'en_atfile_nm')
        this.download(enDownKey, enDownNm)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    download(key, fileName) {
      if (this.isEmpty(key) || this.isEmpty(fileName)) return
      console.log('download...', key) // eslint-disable-line no-console
      this.$axios
        .$get(`/api/v1/common/file-down/${key}`, {
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          // IE 10+
          if (window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(new Blob([res]), fileName)
          } else {
            // not IE
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(new Blob([res]))
            link.target = '_self'
            link.download = fileName
            link.click()
          }
        })
    },
    codInit(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000006 = this.codes.CH00000006.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
    },
    lawInit(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000001 = this.codes.lawCd.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000006 = this.codes.CH00000006.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000006.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.schData.macl_cd = ''
    },
    lawChg(data) {
      // 상위 코드 조회하여 필터링
      this.codes.F_CH00000001 = this.codes.lawCd.filter(function (n) {
        // 품목 코드의 상위 코드가 선택한 데이터의 코드와 동일할 경우만 필터링
        return n.up_cd === data
      })
      this.codes.F_CH00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '', up_cd_nm: '' })
      this.schData.rglt_no = ''
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'msds_rev_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_clsf_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_clsf_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'wkpl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'msds_last_rev_da',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'rev_af_ver',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'msds_ko_ver',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'msds_en_ver',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'ko_atfile_no',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'en_atfile_no',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'ko_atfile_nm',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'en_atfile_nm',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'ko_atfile_key',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'en_atfile_key',
    dataType: ValueType.TEXT,
    // datetimeFormat: 'iso',
  },
  {
    fieldName: 'imp_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'sbms_tgt_yn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
