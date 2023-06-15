<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--안전보건정보(KO)-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('MN00001070') }}</span>
    </div>
    <div class="work_title">
      <!--안전보건정보(KO)-->
      <h2>{{ $t('MN00001070') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_ttl"
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
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.bbs_doc_cntn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--업무구분(KO)-->
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
                <!--분류(KO)-->
                <div class="label_tit">{{ $t('LB00000123') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.doc_type_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000013"
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
            <v-btn color="" outlined height="36" @click="reset()">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="select()">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--안전보건정보 목록(KO)-->
        <h3>{{ $t('LB00001346') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <div>
            <!--등록-->
            <v-btn v-if="searchdatas.userApi" color="primary" depressed height="30" @click="openForm()">{{ $t('LB00000111') }}</v-btn>
          </div>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 1040px"></div>
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
    title: '안전보건정보',
    key(route) {
      return `/bbs/arch/${route.params.catalog}`
    },
  },
  data() {
    return {
      codeid: ['CO00000001', 'CO00000013'],
      codes: {
        CO00000001: [{ cd: '', cd_nm: '' }],
        CO00000013: [{ cd: '', cd_nm: '' }],
      },
      selCO00000001: {
        cd: [],
        cd_nm: [],
      },
      selCO00000013: {
        cd: [],
        cd_nm: [],
      },
      totalcnt: 0,
      searchdatas: {
        bbs_type_cd: 'ARCH',
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        doc_type_cd: '',
        userApi: false,
        bbs_doc_no: '',
        biz_cd: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('MN00001070')
    },
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MN') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async created() {
    // eslint-disable-next-line no-unused-vars
    const rows = await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.selCO00000001.cd.push('')
        this.selCO00000001.cd_nm.push('SELECT')
        this.selCO00000013.cd.push('')
        this.selCO00000013.cd_nm.push('SELECT')
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd)
          this.selCO00000001.cd_nm.push(i.cd_nm)
        })
        res.selCO00000013.forEach((i) => {
          this.selCO00000013.cd.push(i.cd)
          this.selCO00000013.cd_nm.push(i.cd_nm)
        })

        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.selCO00000013.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    const usrinfo = this.$store.getters['auth/getUser']
    usrinfo.roleTypeCds.forEach((i) => {
      if (i === 'M01' || i === 'C01') {
        // 사용자 유저 권한 체크 role type cd = M01,C01  아닐경우 등록 버튼 숨기기 , 상세페이지로만이동
        this.searchdatas.userApi = true
      }
    })
  },
  mounted() {
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000123'), // 분류(KO)
        name: 'doc_type_cd_nm',
        fieldName: 'doc_type_cd_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000114'), // 제목(KO)
        name: 'bbs_doc_ttl',
        fieldName: 'bbs_doc_ttl',
        width: '300',
        styleName: 'link left',
      },
      {
        header: this.$t('LB00000121'), // 첨부파일(KO)
        name: 'atfile_no',
        fieldName: '',
        width: '100',
        type: 'button',
        styleCallback(grid, dataCell) {
          const ret = {}
          const atfileno = grid.getValue(dataCell.index.itemIndex, 'atfile_no')
          if (typeof atfileno !== 'undefined' && atfileno !== null && atfileno !== '' && atfileno !== 0) {
            ret.styleName = 'btn_download'
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'emp_nm',
        fieldName: 'emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'upt_dt',
        fieldName: 'upt_dt',
        width: '100',
      },
      {
        header: this.$t('조회수'), // 조회수
        name: 'view_cnt',
        fieldName: 'view_cnt',
        width: '50',
      },
    ]
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
      if (current.itemIndex < 0) return
      const value = dataProvider.getValue(current.dataRow, 'bbs_doc_no')
      this.searchdatas.bbs_doc_no = value
      // 상세 or 수정 화면으로 이동
      if (current.column === 'bbs_doc_ttl') {
        if (this.searchdatas.userApi) {
          this.$router.push({
            name: 'bbs-archedt-archedt',
            params: {
              archedt: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                doc_type_cd: this.searchdatas.doc_type_cd,
                biz_cd: this.searchdatas.biz_cd,
              },
            },
          })
        } else {
          this.$router.push({
            name: 'bbs-archlup-archlup',
            params: {
              archlup: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                doc_type_cd: this.searchdatas.doc_type_cd,
                biz_cd: this.searchdatas.biz_cd,
              },
            },
          })
        }
      }
      if (current.column === 'atfile_no') {
        const key = dataProvider.getValue(current.dataRow, 'atfile_no')
        if (typeof key !== 'undefined' && key !== null && key !== '' && key !== 0) {
          this.$axios.$get(`/api/v1/common/file/${key}`).then((res) => {
            this.downloadAll(res)
          })
        }
      }
    }.bind(this)
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas.bbs_doc_ttl = this.$route.params.schData.bbs_doc_ttl
      this.searchdatas.bbs_doc_cntn = this.$route.params.schData.bbs_doc_cntn
      this.searchdatas.doc_type_cd = this.$route.params.schData.doc_type_cd
      this.searchdatas.biz_cd = this.$route.params.schData.biz_cd
    }
    this.select()
  },
  methods: {
    downloadAll(files) {
      files.forEach((i) => {
        this.download(i.key, i.name)
      })
    },
    download(key, fileName) {
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
    openForm() {
      this.$router.push({
        name: 'bbs-archedt-archedt',
        params: {
          schData: {
            bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
            bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
            doc_type_cd: this.searchdatas.doc_type_cd,
            biz_cd: this.searchdatas.biz_cd,
            bbs_type_cd: 'ARCH',
          },
        },
      })
    },
    async select() {
      // eslint-disable-next-line no-console
      await console.log(this.searchdatas)
      const rows = await this.$axios.$get(`/api/v1/common/bbs/arch`, { params: this.searchdatas })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      this.searchdatas.bbs_doc_ttl = ''
      this.searchdatas.bbs_doc_cntn = ''
      this.searchdatas.doc_type_cd = ''
      this.searchdatas.bbs_doc_no = ''
      this.searchdatas.biz_cd = ''
      // this.select()
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'bbs_doc_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'doc_type_cd_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'bbs_doc_ttl',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'atfile_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'upt_dt',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'view_cnt',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
