<template>
  <div>
    <div class="location">
      <!--MRO 구매요청(KO)--><!--MRO 구매요청-다건(KO)-->
      <span>Home > {{ $t('LB00000153') }} > {{ $t('LB00000152') }}</span>
    </div>
    <div class="work_title">
      <!--MRO 구매요청-다건(KO)-->
      <h2>{{ $t('LB00000152') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--MRO 구매요청(KO)-->
        <h3>{{ $t('LB00000153') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--요청번호-->
                <div class="label_tit">{{ $t('LB00000151') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.mro_pchs_no }}</div>
              </v-col>
              <v-col cols="1">
                <!--요청자 / 부서-->
                <div class="label_tit">{{ $t('LB00000150') }} / {{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.upt_emp_nm }} / {{ mroSaveData.upt_dept_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--요청일-->
                <div class="label_tit">{{ $t('LB00000149') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ mroSaveData.wkpl_id_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--구매 품목(KO)-->
        <h3>{{ $t('LB00000148') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 200px"></div>
    </div>
    <!-- <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        // 구매요청내용(KO)
        <h3>{{ $t('LB00000147') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor v-model="mroSaveData.pchs_cntn" :options="editorOption" :disabled="true" />
          </v-col>
        </v-row>
      </v-container>
    </div> -->
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: 'MRO구매요청-MRO구매요청-다건',
    key(route) {
      return `/mro/mropchs/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      codeid: ['CO00000006'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }], // 사업장
      },
      totalcnt: 0,
      // Mro_구매 Mst
      mroSaveData: {
        mro_pchs_no: '', // mro구매번호 yymmdd + 6자리시퀀스 tb_ch_mro_pchs_seq 201207000001
        upt_emp_no: '',
        upt_emp_nm: '',
        upt_dt: '',
        wkpl_id: '',
        pchs_cntn: '', // 구매내용
        pchs_prog_stt_cd: '', // 구매진행상태코드
        aprv_no: '', // 결재번호
        aprv_crt_dt: '', // 결재 생성 일자
        aprv_cmpt_dt: '', // 결재 승인 일자
        multi_yn: 'Y', // 다건여부
      },
      savedatas: [], //  품목 정보
      editorOption: {
        // some quill options
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // toggled buttons
            ['blockquote', 'code-block'],

            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }], // outdent/indent

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ align: [] }],

            ['link', 'image'],

            ['clean'], // remove formatting button
          ],
        },
        placeholder: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000152')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },

  created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    this.mroSaveData.upt_emp_nm = this.usrinfo.emp_nm
    this.mroSaveData.upt_dept_nm = this.usrinfo.dept_nm
    this.mroSaveData.upt_emp_no = this.usrinfo.emp_no
    this.mroSaveData.upt_dt = date.toISOString().substr(0, 10)
    this.mroSaveData.mro_pchs_no = this.$route.params.mropchsmultilup
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        wkpl_id: '',
        macl_cd: '',
        pchs_prog_stt_cd: '',
        mtrl_no: '',
        mtrl_nm: '',
        emp_nm: '',
        sDate: '',
        eDate: '',
        multi_yn: '',
      }
    }
    if (this.mroSaveData.mro_pchs_no !== 'null') {
      this.selectDtl(this.mroSaveData.mro_pchs_no)
    } else {
      this.mroSaveData.mro_pchs_no = ''
    }
  },
  mounted() {
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000160'), // 품목코드(KO)
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
        styles: {
          textAlignment: 'center',
        },
      },
      {
        header: this.$t('LB00000159'), // 품목명(KO)
        name: 'mtrl_nm',
        fieldName: 'mtrl_nm',
        width: '300',
        styleName: 'left',
      },
      {
        header: this.$t('LB00000158'), // 구매처명
        name: 'vndr_nm',
        fieldName: 'vndr_nm',
        width: '200',
      },
      {
        header: this.$t('LB00000157'), // 수입여부(KO)
        name: 'impt_yn',
        fieldName: 'impt_yn',
        width: '200',
      },
      {
        header: this.$t('LB00000528'), // 단위중량(KO)
        name: 'pchs_req_unit_weght_nm',
        fieldName: 'pchs_req_unit_weght_nm',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000156'), // 주문단위(KO)
        name: 'pchs_req_unit_nm',
        fieldName: 'pchs_req_unit_nm',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000154'), // 주문수량(KO)
        name: 'pchs_qty',
        fieldName: 'pchs_qty',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000600'), // 총구매량(KO)
        name: 'pchs_req_total_weght',
        fieldName: 'pchs_req_total_weght',
        width: '100',
        styleName: 'right',
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

    gridView.setColumnProperty('pchs_req_unit_nm', 'visible', false)
  },
  methods: {
    selectDtl(mropchsno) {
      this.$axios.$get(`/api/v1/ch/mro/mropchs-multi/${mropchsno}`).then((res) => {
        console.log(res) // eslint-disable-line no-console

        this.mroSaveData = res.mroSaveData
        dataProvider.setRows(res.mtrlSaveData)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()
      })
    },
    goList() {
      this.$router.push({
        name: this.$route.params.goData,
        params: {
          schData: {
            wkpl_id: this.tmpSchData.wkpl_id,
            macl_cd: this.tmpSchData.macl_cd,
            pchs_prog_stt_cd: this.tmpSchData.pchs_prog_stt_cd,
            mtrl_no: this.tmpSchData.mtrl_no,
            mtrl_nm: this.tmpSchData.mtrl_nm,
            emp_nm: this.tmpSchData.emp_nm,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
            multi_yn: this.tmpSchData.multi_yn,
          },
        },
      })
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'mro_pchs_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'mtrl_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'vndr_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'impt_yn',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_weght_nm',
    dataType: ValueType.TEXT,
    valueCallback(prod, dataRow, fieldName, fieldNames, values) {
      const pchsReqUnitWeght = values[fieldNames.indexOf('pchs_req_unit_weght')]
      if (isNaN(pchsReqUnitWeght)) {
        return ''
      } else {
        const pchsReqUnitNm = values[fieldNames.indexOf('pchs_req_unit_nm')]
        return pchsReqUnitWeght + '' + pchsReqUnitNm
      }
    },
  },
  {
    fieldName: 'pchs_qty',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'row_status',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'macl_cd_nm',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_total_weght',
    dataType: ValueType.TEXT,
    // valueExpression: 'values["pchs_req_unit_weght"] * values["pchs_qty"]',
    valueCallback(prod, dataRow, fieldName, fieldNames, values) {
      const pchsReqUnitWeght = values[fieldNames.indexOf('pchs_req_unit_weght')]
      const pchsQty = values[fieldNames.indexOf('pchs_qty')]
      if (isNaN(pchsReqUnitWeght) || isNaN(pchsQty)) {
        return ''
      } else {
        const pchsReqUnitNm = values[fieldNames.indexOf('pchs_req_unit_nm')]
        return pchsReqUnitWeght * pchsQty + '' + pchsReqUnitNm
      }
    },
  },
]
</script>
<style></style>
