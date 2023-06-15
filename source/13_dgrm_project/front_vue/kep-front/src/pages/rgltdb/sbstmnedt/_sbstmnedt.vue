<template>
  <div>
    <div class="location">
      <!-- Home > 규제 DB > 물질 상세 -->
      <span>Home > {{ $t('LB00000218') }} > {{ $t('LB00000318') }}</span>
    </div>
    <!-- 물질 상세 -->
    <div class="work_title">
      <h2>{{ $t('LB00000318') }}</h2>
    </div>
    <div class="container_detail">
      <!-- 물질 -->
      <div class="sub_title">
        <h3>{{ $t('CD00000366') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <!-- 물질번호 
              <v-col cols="1">
                <div class="label_tit">{{ $t('LB00000317') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.sbst_no }}</div>
              </v-col>
              -->
              <v-col cols="1">
                <div class="label_tit">CAS No.</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cas_no }}</div>
              </v-col>
              <v-col cols="1">
                <!-- 사용여부 -->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.use_yn }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 물질명 -->
                <div class="label_tit">{{ $t('LB00000196') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.sbst_ko_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!-- 물질명(영문) -->
                <div class="label_tit">{{ $t('LB00000316') }}</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.sbst_en_nm }}</div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <!-- 규제 정보 -->
      <div class="sub_title">
        <h3>{{ $t('LB00000315') }}</h3>
      </div>
      <div class="grid_header">
        <!-- 총 {{ totalcnt }}건 -->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 350px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!-- 저장 -->
      <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!-- 목록 -->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '규제DB-물질 상세',
    key(route) {
      return `/rgltdb/sbstmn/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      user: {},
      tmpSchData: {},
      srchData: {},
      frmData: {},
      gridData: {},
      totalcnt: 0,
      textRules: [(v) => !!v || '필수항목입니다.'],
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000318') + ' ' + this.$t('LB00000597')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        cas_no: '',
        sbst_nm: '',
        rglt_chk_doma_cd: '',
        law_cd: '',
        rglt_nm: '',
      }
    }
    this.frmData.username = this.$store.getters['auth/getUsername']
    // 파라미터 조회
    this.srchData.sbst_no = this.$route.params.sbstmnedt
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sbst_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'law_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'tgt_yn',
        dataType: ValueType.BOOLEAN,
      },
      {
        fieldName: 'org_tgt_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_crtr_rati',
        dataType: ValueType.TEXT,
      },
      /* 
      {
        fieldName: 'rglt_crtr_use_qty',
        dataType: ValueType.NUMBER,
      },
      */
      {
        fieldName: 'rglt_crtr_cntn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_sbst_note',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rglt_type_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'use_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'del_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_emp_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'crt_dt',
        dataType: ValueType.TEXT,
      },
    ]

    const columns = [
      {
        header: 'ID',
        name: 'id',
        fieldName: 'id',
        width: '50',
      },
      {
        header: this.$t('LB00000322'), // 법
        name: this.$t('LB00000322'),
        fieldName: 'law_nm',
        width: '100',
      },
      {
        header: this.$t('LB00000028'), // 규제
        name: this.$t('LB00000028'),
        fieldName: 'rglt_nm',
        width: '210',
      },
      {
        header: this.$t('LB00000212'), // 대상여부
        name: this.$t('LB00000212'),
        fieldName: 'tgt_yn',
        width: '60',
        editable: false,
        renderer: {
          type: 'check',
          editable: true,
        },
        // styleCallback(grid, dataCell) {
        //   const rgltTypeCd = grid.getValue(dataCell.index.itemIndex, 'rglt_type_cd')
        //   // eslint-disable-next-line eqeqeq
        //   if (rgltTypeCd === '1') {
        //     return {
        //       renderer: {
        //         type: 'check',
        //         editable: false,
        //       },
        //     }
        //   } else {
        //     return {
        //       renderer: {
        //         type: 'check',
        //         editable: true,
        //       },
        //     }
        //   }
        // },
      },
      {
        header: this.$t('LB00000319') + '(%)', // 규제대상 함량기준(%)
        name: this.$t('LB00000319'),
        fieldName: 'rglt_crtr_rati',
        width: '90',
        editable: true,
        editor: {
          maxLength: 10,
        },
        styleName: 'right',
        styleCallback(grid, dataCell) {
          const ret = {}
          const rgltTypeCd = grid.getValue(dataCell.index.itemIndex, 'rglt_type_cd')
          // eslint-disable-next-line eqeqeq
          if (rgltTypeCd === '1') {
            ret.editable = false
          } else {
            ret.editable = true
          }
          return ret
        },
      },
      {
        header: this.$t('LB00000113'), // 내용
        name: this.$t('LB00000113'),
        fieldName: 'rglt_crtr_cntn',
        width: '240',
        editable: true,
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const rgltTypeCd = grid.getValue(dataCell.index.itemIndex, 'rglt_type_cd')
          // eslint-disable-next-line eqeqeq
          if (rgltTypeCd === '1') {
            ret.editable = false
          } else {
            ret.editable = true
          }
          return ret
        },
      },
      /* 
      {
        header: this.$t('LB00000321'), // 값
        name: 'group2',
        fieldName: 'rglt_crtr_use_qty',
        width: '100',
        editable: true,
        editor: {
          type: 'number',
          maxIntegerLength: 15,
        },
        styleName: 'right',
        styleCallback(grid, dataCell) {
          const ret = {}
          const rgltTypeCd = grid.getValue(dataCell.index.itemIndex, 'rglt_type_cd')
          // eslint-disable-next-line eqeqeq
          if (rgltTypeCd === '1') {
            ret.editable = false
          } else {
            ret.editable = true
          }
          return ret
        },
      },
      */
      {
        header: this.$t('LB00000095'), // 비고
        name: this.$t('LB00000095'),
        fieldName: 'rglt_sbst_note',
        width: '200',
        editable: true,
        styleName: 'left',
        styleCallback(grid, dataCell) {
          const ret = {}
          const rgltTypeCd = grid.getValue(dataCell.index.itemIndex, 'rglt_type_cd')
          // eslint-disable-next-line eqeqeq
          if (rgltTypeCd === '1') {
            ret.editable = false
          } else {
            ret.editable = true
          }
          return ret
        },
      },
    ]

    const headergroup = [
      this.$t('LB00000272'), // 법
      this.$t('LB00000028'), // 규제
      this.$t('LB00000212'), // 대상여부
      this.$t('LB00000319'), // 규제대상 함량기준
      this.$t('LB00000113'), // 내용
      /* 
      {
        name: 'companyGroup',
        direction: 'horizontal',
        items: ['group1', 'group2'],
        header: {
          text: this.$t('LB00000320'), // 노출기준
        },
      },
      */
      this.$t('LB00000095'), // 비고
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setColumnLayout(headergroup)

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = true
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 60
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('id', 'visible', false)

    this.select()
  },
  methods: {
    async select() {
      // eslint-disable-next-line no-unused-vars
      const rows = await this.$axios
        .$get(`/api/v1/ch/rgltdb/sbst-rglt-dtl/${this.srchData.sbst_no}`, { params: this.srchData })
        .then((res) => {
          this.frmData = res
          this.selectDtl()
        })
        .catch((err) => {
          // eslint-disable-next-line no-console
          console.log(err)
        })
    },
    async selectDtl() {
      const rows = await this.$axios.$get(`/api/v1/ch/rgltdb/sbst-rglt-dtl-list`, { params: this.srchData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
      console.log(rows) // eslint-disable-line no-console
    },
    async save() {
      gridView.commit()
      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount()) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }
      // Validation 체크
      // if (!this.validate()) {
      //   return
      // }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      gridView.commit()
      // 바뀐 row 가 한개라도 있다면 저장 시작
      if (dataProvider.getRowStateCount('updated') > 0) {
        const updateRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          if (dataProvider.getRowState(i) === 'updated') {
            temp.sbst_no = this.frmData.sbst_no
            temp.rglt_no = dataProvider.getValue(i, 'rglt_no')
            temp.rglt_crtr_rati = dataProvider.getValue(i, 'rglt_crtr_rati')
            // temp.rglt_crtr_use_qty = dataProvider.getValue(i, 'rglt_crtr_use_qty')
            temp.rglt_crtr_cntn = dataProvider.getValue(i, 'rglt_crtr_cntn')
            temp.rglt_sbst_note = dataProvider.getValue(i, 'rglt_sbst_note')
            temp.use_yn = dataProvider.getValue(i, 'use_yn')
            temp.del_yn = dataProvider.getValue(i, 'del_yn')
            temp.crt_emp_no = dataProvider.getValue(i, 'crt_emp_no')
            temp.crt_dt = dataProvider.getValue(i, 'crt_dt')
            temp.username = this.user.emp_no
            temp.org_tgt_yn = dataProvider.getValue(i, 'org_tgt_yn')
            if (dataProvider.getValue(i, 'tgt_yn') === true) {
              temp.tgt_yn = 'Y'
            } else {
              temp.tgt_yn = 'N'
            }
            updateRow.push(temp)
          }
        }

        this.frmData.updateRow = updateRow

        await this.$axios.put('/api/v1/ch/rgltdb/sbst-rglt-dtl', this.frmData).then((res) => {
          console.log(res) // eslint-disable-line no-console
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
    async goList() {
      gridView.commit()
      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount()) {
        const res = await this.$confirm(this.$t('MS00000131')) // 수정된 내용이 있습니다. 화면을 이동할 경우 작업내용을 저장할 수 없습니다.
        if (res) {
          this.$router.push({
            name: `rgltdb-sbstmnlist`,
            params: {
              schData: {
                cas_no: this.tmpSchData.cas_no,
                sbst_nm: this.tmpSchData.sbst_nm,
                rglt_chk_doma_cd: this.tmpSchData.rglt_chk_doma_cd,
                law_cd: this.tmpSchData.law_cd,
                rglt_nm: this.tmpSchData.rglt_nm,
              },
            },
          })
        }
      } else {
        this.$router.push({
          name: `rgltdb-sbstmnlist`,
          params: {
            schData: {
              cas_no: this.tmpSchData.cas_no,
              sbst_nm: this.tmpSchData.sbst_nm,
              rglt_chk_doma_cd: this.tmpSchData.rglt_chk_doma_cd,
              law_cd: this.tmpSchData.law_cd,
              rglt_nm: this.tmpSchData.rglt_nm,
            },
          },
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
