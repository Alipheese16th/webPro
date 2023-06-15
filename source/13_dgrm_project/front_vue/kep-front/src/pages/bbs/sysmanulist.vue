<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--메뉴얼-->
      <span>Home > {{ $t('LB00000117') }} > {{ $t('LB00000129') }}</span>
    </div>
    <div class="work_title">
      <!--메뉴얼-->
      <h2>{{ $t('LB00000129') }}</h2>
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
              <v-col cols="1">
                <!--메뉴얼 유형-->
                <div class="label_tit">{{ $t('LB00000127') }}</div>
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
        <!--메뉴얼 목록-->
        <h3>{{ $t('LB00000128') }}</h3>
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
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '메뉴얼 목록',
    key(route) {
      return `/bbs/sysmanu/${route.params.catalog}`
    },
  },
  data() {
    return {
      codeid: ['CO00000013'],
      codes: {
        CO00000013: [{ cd: '', cd_nm: '' }],
      },
      totalcnt: 0,
      searchdatas: {
        bbs_type_cd: 'MANU',
        bbs_doc_ttl: '',
        bbs_doc_cntn: '',
        doc_type_cd: '',
        userApi: false,
        bbs_doc_no: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000129')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async created() {
    // eslint-disable-next-line no-unused-vars
    const rows = await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        res.CO00000013.forEach((i) => {
          if (i.up_cd === 'MANU') {
            this.codes.CO00000013.push(i)
          }
        })
        this.codes.CO00000013.unshift({ cd: '', cd_nm: 'ALL' })
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
        header: this.$t('LB00000127'), // 메뉴얼 유형
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
            name: 'bbs-sysmanuedt-sysmanuedt',
            params: {
              sysmanuedt: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                doc_type_cd: this.searchdatas.doc_type_cd,
              },
            },
          })
        } else {
          this.$router.push({
            name: 'bbs-sysmanulup-sysmanulup',
            params: {
              sysmanulup: this.searchdatas.bbs_doc_no,
              schData: {
                bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
                bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
                doc_type_cd: this.searchdatas.doc_type_cd,
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
    }
    this.select()
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (to.params.cd !== null && to.params.cd !== undefined) {
        vm.searchdatas.doc_type_cd = to.params.cd
        vm.select()
      }
    })
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
        name: 'bbs-sysmanuedt-sysmanuedt',
        params: {
          schData: {
            bbs_doc_ttl: this.searchdatas.bbs_doc_ttl,
            bbs_doc_cntn: this.searchdatas.bbs_doc_cntn,
            doc_type_cd: this.searchdatas.doc_type_cd,
          },
        },
      })
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/bbs/manu`, { params: this.searchdatas })

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
]
</script>
<style lang="scss" scoped></style>
