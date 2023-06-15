<template>
  <div>
    <div class="location">
      <!--규제 DB--><!--화학물질 규제 조회-->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000217') }}</span>
    </div>
    <div class="work_title">
      <!--화학물질 규제 조회-->
      <h2>{{ $t('LB00000217') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--CAS NO.-->
                <div class="label_tit">{{ $t('LB00000216') }}</div>
              </v-col>
              <v-col cols="3">
                <!--CAS NO. 선택-->
                <v-text-field
                  ref="casno"
                  v-model="searchdatas.cas_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000455')"
                  readonly
                  height="30"
                  class="icon_sch"
                  @click="sbstPop()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--함량(%)-->
                <div class="label_tit">{{ $t('LB00000195') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  ref="rati"
                  v-model="searchdatas.rglt_crtr_rati"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  maxlength="20"
                  height="30"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--대상여부-->
                <div class="label_tit">{{ $t('LB00000212') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.target_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="target_yn_cd"
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
        <!--화학물질 규제 목록-->
        <h3>{{ $t('LB00000462') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <!--해당 자료는 참조자료이므로, 업무에 참고만 부탁드립니다. 자세한 사항은 자재 별 MSDS를 참조 부탁드립니다.-->
        <div class="label_info ml-1">{{ $t('LB00000463') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <popup ref="sbstpop" mkey="sbstList_sbstpop" @close="sbstPopClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/sbstslct'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '화학물질 규제 조회',
  },
  components: {
    popup,
  },
  data() {
    return {
      totalcnt: 0,
      searchdatas: {
        cas_no: '',
        rglt_crtr_rati: '',
        sbst_no: '',
        target_yn: '',
      },
      target_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000217')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {},
  mounted() {
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000027'), // 법률
        name: 'law_nm',
        fieldName: 'law_nm',
        width: '40',
      },
      {
        header: this.$t('LB00000213'), // 규제명
        name: 'rglt_nm',
        fieldName: 'rglt_nm',
        width: '70',
      },
      {
        header: this.$t('LB00000319'), // 규제대상 함량기준
        name: 'rglt_crtr_rati',
        fieldName: 'rglt_crtr_rati',
        width: '30',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000212'), // 대상여부
        name: 'target_yn',
        fieldName: 'target_yn',
        width: '40',
        styles: {
          textAlignment: 'center',
        },
      },
      {
        header: this.$t('LB00000515'), // 규제 고시 내용
        name: 'lgnoti_cntn',
        fieldName: 'lgnoti_cntn',
        width: '150',
        styleName: 'left',
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
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (to.params.sbstRgltSchData !== null && to.params.sbstRgltSchData !== undefined) {
        vm.searchdatas.cas_no = to.params.sbstRgltSchData.cas_no
        vm.searchdatas.rglt_crtr_rati = to.params.sbstRgltSchData.rglt_crtr_rati
        vm.searchdatas.sbst_no = to.params.sbstRgltSchData.sbst_no
        vm.select()
      }
    })
  },
  methods: {
    async select() {
      if (!this.isEmpty(this.searchdatas.cas_no) && !this.isEmpty(this.searchdatas.rglt_crtr_rati)) {
        const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/sbst-rglt`, { params: this.searchdatas })
        dataProvider.setRows(rows)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()
      } else if (this.isEmpty(this.searchdatas.cas_no)) {
        this.$toast.show(this.$t('MS00000046'), { className: 'toast_error' }) // CASNo.를입력해주세요.
        this.$refs.casno.focus()
      } else if (this.isEmpty(this.searchdatas.rglt_crtr_rati)) {
        this.$toast.show(this.$t('MS00000174'), { className: 'toast_error' }) // 함량을 입력해주세요.
        this.$refs.rati.focus()
      }
    },
    reset() {
      gridView.commit()
      this.searchdatas.cas_no = ''
      this.searchdatas.rglt_crtr_rati = ''
      this.searchdatas.sbst_no = ''
      this.searchdatas.target_yn = ''
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    sbstPop() {
      this.$refs.sbstpop.open('', false)
    },
    sbstPopClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.searchdatas.cas_no = returnData[0].cas_no
        this.searchdatas.sbst_no = returnData[0].sbst_no
      }
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'law_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rglt_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'rglt_crtr_rati',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'target_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'lgnoti_cntn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
