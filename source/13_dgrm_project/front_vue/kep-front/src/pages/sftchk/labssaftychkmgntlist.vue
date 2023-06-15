<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 연구실 정기안전점검 -->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001136') }}</span>
    </div>
    <div class="work_title">
      <!-- 연구실 정기안전점검 -->
      <h2>{{ $t('LB00001136') }}</h2>
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
                  :items="codes.ST00000002"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검년도-->
                <div class="label_tit">{{ $t('LB00001105') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu ref="menu" v-model="menu" :close-on-content-click="true" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      slot="activator"
                      ref="wem_year"
                      v-model="schData.wem_year"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    ref="picker"
                    v-model="schData.wem_year"
                    reactive
                    show-current
                    min="2010"
                    max="2050"
                    no-title
                    @click:year="saveDate(schData.wem_year)"
                  ></v-date-picker>
                </v-menu>
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
      <div class="sub_title">
        <!--연구실 정기안전점검 목록-->
        <h3>{{ $t('LB00001137') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀다운로드-->
          <v-btn color="primary" depressed height="30" @click="excelExport()">{{ $t('LB00000214') }}</v-btn>
          <!-- 등록 -->
          <v-btn color="primary" height="30" @click="openNew">{{ $t('LB00000111') }}</v-btn>
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

let yearVal = new Date()

export default {
  meta: {
    title: '연구실 정기안전점검',
    key(route) {
      return `/sftchk/labssafty/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  fetch() {
    this.select()
  },
  data() {
    return {
      menu: false,
      schData: {},
      tempData: {},
      totalcnt: 0,
      codeid: ['ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001136')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  watch: {
    menu(val) {
      val && this.$nextTick(() => (this.$refs.picker.activePicker = 'YEAR'))
    },
  },
  created() {
    const user = this.$store.getters['auth/getUser']

    yearVal = this.getTzDateObj(user.time_zone)
    this.schData.wem_year = this.get_date_str_gubun(yearVal, '-') + ''

    this.tempData.username = this.$store.getters['auth/getUsername']
    this.tempData.userempno = user.emp_no
    console.log(user) // eslint-disable-line no-console
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.wkpl_id = user.wkpl_id
    }
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'labs_sft_chk_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'cmpy_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_yrmn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_orga_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'upt_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lsch_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'dept_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfile_key',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'atfile_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'labs_sft_chk_atfl',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: this.$t('LB00000700'), // 회사
        name: '회사',
        fieldName: 'cmpy_nm',
        width: '150',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000021'), // 사업장
        name: '사업장',
        fieldName: 'wkpl_nm',
        width: '100',
        styleName: 'center',
      },
      {
        header: this.$t('LB00001105'), // 점검년도
        name: '점검년도',
        fieldName: 'labs_sft_chk_yrmn',
        width: '100',
        styleName: 'center',
      },
      {
        header: this.$t('LB00000640'), // 점검명
        name: '점검명',
        fieldName: 'labs_sft_chk_nm',
        width: '300',
        styleName: 'link center',
      },
      {
        header: this.$t('LB00001138'), // 실시기관
        name: '실시기관',
        fieldName: 'labs_sft_chk_orga_nm',
        width: '150',
        styleName: 'center',
      },
      {
        header: this.$t('LB00001139'), // 보고서
        name: '보고서',
        fieldName: 'labs_sft_chk_atfl',
        width: '150',
        styleCallback(grid, dataCell) {
          const ret = {}
          const atfileno = grid.getValue(dataCell.index.itemIndex, 'atfile_no')
          if (typeof atfileno !== 'undefined' && atfileno !== null && atfileno !== '' && atfileno !== 0) {
            ret.styleName = 'btn_download'
          }
          return ret
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
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.onCellClicked = function (grid, index, clickData) {
      const current = gridView.getCurrent()

      if (current.column === '점검명') {
        const labsSftChkId = gridView.getValue(index.itemIndex, 'labs_sft_chk_id')
        const wkplId = gridView.getValue(index.itemIndex, 'wkpl_nm')
        const deptCd = gridView.getValue(index.itemIndex, 'dept_cd')
        const user = this.$store.getters['auth/getUser']
        if (deptCd === user.dept_cd) {
          this.$router.push({
            name: `sftchk-labssaftychkmgntdtledt-labssaftychkmgntdtledt`,
            params: {
              labssaftychkmgntdtledt: labsSftChkId,
              wkpl_id: wkplId,
              labs_sft_chk_id: labsSftChkId,
              schData: {
                wkpl_id: wkplId,
                labs_sft_chk_id: labsSftChkId,
              },
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                labs_sft_chk_yrmn: this.schData.labs_sft_chk_yrmn,
              },
            },
          })
        } else {
          this.$router.push({
            name: `sftchk-labssaftychkmgntdtllup-labssaftychkmgntdtllup`,
            params: {
              labssaftychkmgntdtllup: labsSftChkId,
              wkpl_id: wkplId,
              labs_sft_chk_id: labsSftChkId,
              schData: {
                wkpl_id: wkplId,
                labs_sft_chk_id: labsSftChkId,
              },
              tmpSchData: {
                wkpl_id: this.schData.wkpl_id,
                labs_sft_chk_yrmn: this.schData.labs_sft_chk_yrmn,
              },
            },
          })
        }
      }
      if (current.column === '보고서') {
        // 다운로드 처리
        const DownKey = gridView.getValue(index.itemIndex, 'atfile_key')
        const DownNo = gridView.getValue(index.itemIndex, 'atfile_no')
        this.download(DownKey, DownNo)
      }
    }.bind(this)
  },
  methods: {
    openNew() {
      this.$router.push({
        name: `sftchk-labssaftychkmgntdtledt-labssaftychkmgntdtledt`,
        params: {
          labssaftychkmgntdtledt: null,
          wkpl_id: null,
          labs_sft_chk_id: null,
          schData: {
            wkpl_id: '',
            labs_sft_chk_id: '',
          },
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
          },
        },
      })
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
    get_date_str_gubun(date) {
      const sYear = date.getFullYear()
      return sYear
    },
    saveDate(date) {
      const dd = date.substring(0, 4)
      this.schData.wem_year = dd
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    searchValidate() {
      return true
    },
    async select() {
      if (!this.searchValidate()) {
        return
      }
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/labssaftychkmgntlist`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    schReset() {
      const user = this.$store.getters['auth/getUser']
      this.schData = {
        wkpl_id: '',
      }
      this.schData.wkpl_id = user.wkpl_id
      this.schData.wem_year = this.get_date_str_gubun(yearVal)
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001140') + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: '엑셀 Export중입니다.',
        indicator,
        header,
        footer,
        compatibility: excelType,
        done() {
          //
        },
      })
    },
  },
}
</script>
<style lang="scss" scoped></style>
