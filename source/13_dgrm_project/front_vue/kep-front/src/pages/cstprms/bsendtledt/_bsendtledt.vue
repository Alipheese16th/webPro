<template>
  <div>
    <div class="location">
      <!-- Home > 작업허가 > 협력사 관리 -->
      <span>Home > {{ $t('LB00000734') }} > {{ $t('LB00000732') }}</span>
    </div>
    <div class="work_title">
      <!-- 협력사 관리 -->
      <h2>{{ $t('LB00000732') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--협력사번호-->
                <div class="label_tit">{{ $t('LB00000729') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.otsr_con_bsen_id }}</div>
              </v-col>
              <v-col cols="1">
                <!--협력사명-->
                <div class="label_tit essn">{{ $t('LB00000699') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_nm"
                  v-model="frmData.otsr_con_bsen_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--주소-->
                <div class="label_tit essn">{{ $t('LB00000725') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="otsr_con_bsen_addr"
                  v-model="frmData.otsr_con_bsen_addr"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="2000"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사업자 등록번호-->
                <div class="label_tit essn">{{ $t('LB00000409') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="bspr_no"
                  v-model="frmData.bspr_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="13"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--대표자 명-->
                <div class="label_tit essn">{{ $t('LB00000724') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="rprv_nm"
                  v-model="frmData.rprv_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="100"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--전화번호-->
                <div class="label_tit essn">{{ $t('LB00000723') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="otsr_con_bsen_tphn"
                  v-model="frmData.otsr_con_bsen_tphn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="20"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <v-container>
      <v-row>
        <v-col cols="8" style="padding: 0">
          <div class="realgrid_container">
            <!--작업자 정보-->
            <div class="sub_title">{{ $t('LB00000722') }}</div>
            <div v-if="excel">
              <v-row>
                <v-col cols="6"> </v-col>
                <v-col cols="6">
                  <v-file-input v-model="files" accept=".xlsx" placeholder="Upload Excel File" @change="handleXlsFile"></v-file-input>
                </v-col>
              </v-row>
            </div>
            <div class="grid_header">
              <!--총 건-->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ oprrTotalcnt }}{{ $t('LB00000040') }}</div>
              <div>
                <!--양식 다운로드-->
                <v-btn color="" outlined height="30" @click="excelTemp()">{{ $t('LB00000721') }}</v-btn>
                <!--엑셀업로드-->
                <v-btn color="" outlined height="30" @click="excel = !excel">{{ $t('LB00000720') }}</v-btn>
                <!--추가-->
                <v-btn color="" outlined height="30" @click="oprrAdd()">{{ $t('LB00000047') }}</v-btn>
                <!--삭제-->
                <v-btn color="" outlined height="30" @click="oprrDel()">{{ $t('LB00000046') }}</v-btn>
              </div>
            </div>
            <div id="realgrid" style="width: 100%; height: 581px"></div>
            <div id="exceltemp" style="width: 100%; height: 581px; display: none"></div>
          </div>
        </v-col>
        <v-col cols="4" style="padding: 0">
          <div class="container_detail">
            <!--사업장 교육 이수 정보-->
            <div class="sub_title">{{ $t('LB00000718') }}</div>
            <div class="grid_header">
              <!--총 건-->
              <div class="grid_header_left">{{ $t('LB00000039') }} {{ fcstTotalcnt }}{{ $t('LB00000040') }}</div>
              <div>
                <!--추가-->
                <v-btn color="" outlined height="30" @click="fcstAdd()">{{ $t('LB00000047') }}</v-btn>
                <!--삭제-->
                <v-btn color="" outlined height="30" @click="fcstDel()">{{ $t('LB00000046') }}</v-btn>
              </div>
            </div>
            <div id="realgrid2" style="width: 100%; height: 581px"></div>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save('save')">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <filePopup ref="filepop" mkey1="_bsendtledtatflgrid1" @close="filepopclose" />
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import filePopup from '../../common/atflgrid.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

// 그리드 3
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

let dateVal = new Date()

export default {
  meta: {
    title: '업체 관리',
    key(route) {
      return `/cstprms/cstprms/${route.params.catalog}`
    },
  },
  components: {
    filePopup,
  },
  data() {
    return {
      excel: false,
      files: [],
      modify: false,
      codeid: ['SH00000010', 'SH00000011', 'ST00000002'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        SH00000010: [{ cd: '', cd_nm: '' }],
        SH00000011: [{ cd: '', cd_nm: '' }],
      },
      selST00000002: {
        cd: [],
        cd_nm: [],
      },
      selSH00000010: {
        cd: [],
        cd_nm: [],
      },
      selSH00000011: {
        cd: [],
        cd_nm: [],
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      oprrTotalcnt: 0,
      fcstTotalcnt: 0,
      tmpSchData: {},
      schData: {},
      chkRowVal: 0,
      oprrRow: 0,
      chkYn: 'N',
      otsr_con_bsen_id: '',
      frmTempData: '',
      frmData: {
        ckFlag: false,
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        otsr_con_bsen_addr: '',
        usg_yn: 'Y',
        rprv_nm: '',
        bspr_no: '',
        otsr_con_bsen_tphn: '',
        otsr_con_bsen_dtrm_yn: '',
        lsch_nm: '',
      },
      // 작업자정보
      oprrData: {
        chk_gb: '',
        otsr_con_bsen_id: '',
        oprr_id: '',
        oprr_nm: '',
        oprr_tphn: '',
        oprn_cl_cd: '',
        oprn_cl_nm: 0,
        crtf_vld_end_dt: '',
        buil_bsc_sft_trn_fcst_dt: '',
        oprr_dtrm_yn: '',
        oprr_del_yn: '',
        oprr_atfl_no: '',
        oprr_atfl_cnt: 0,
        lsch_nm: '',
        upt_dt: '',
      },
      // 사업장 교육 이수 정보
      fcstData: {
        oprr_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        trn_fcst_sn: '',
        trn_cl_cd: '',
        trn_cl_nm: '',
        trn_fcst_dt: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000732') // 협력사 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.bsendtledt !== null && this.$route.params.bsendtledt !== undefined) {
      this.frmData.otsr_con_bsen_id = this.$route.params.bsendtledt
      this.otsr_con_bsen_id = this.$route.params.bsendtledt
      this.schData.otsr_con_bsen_id = this.$route.params.bsendtledt
      this.modify = true
    } else {
      this.frmData.otsr_con_bsen_id = ''
      this.otsr_con_bsen_id = ''
      this.schData.otsr_con_bsen_id = ''
      this.modify = false
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        otsr_con_bsen_id: '',
        otsr_con_bsen_nm: '',
        usg_yn: '',
      }
    }
    dateVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.hrm_istl_dt = this.get_date_str_gubun(dateVal, '-')
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.selST00000002.cd.push('')
      this.selST00000002.cd_nm.push('SELECT')
      res.ST00000002.forEach((i) => {
        this.selST00000002.cd.push(i.cd)
        this.selST00000002.cd_nm.push(i.cd_nm)
      })
      this.selSH00000010.cd.push('')
      this.selSH00000010.cd_nm.push('SELECT')
      res.SH00000010.forEach((i) => {
        this.selSH00000010.cd.push(i.cd)
        this.selSH00000010.cd_nm.push(i.cd_nm)
      })
      this.selSH00000011.cd.push('')
      this.selSH00000011.cd_nm.push('SELECT')
      res.SH00000011.forEach((i) => {
        this.selSH00000011.cd.push(i.cd)
        this.selSH00000011.cd_nm.push(i.cd_nm)
      })

      // 그리드 세팅
      const fields = [
        {
          fieldName: '_chk',
          dataType: ValueType.BOOLEAN,
        },
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'otsr_con_bsen_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_tphn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprn_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprn_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'crtf_vld_end_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'buil_bsc_sft_trn_fcst_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_dtrm_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_del_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_atfl_cnt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'lsch_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'upt_dt',
          dataType: ValueType.TEXT,
        },
      ]

      const fields2 = [
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'oprr_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'trn_fcst_dt',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: ' ',
          name: '_chk',
          fieldName: '_chk',
          type: 'data',
          width: '30',
          editable: false,
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'check',
                }
                break
              case 'Y':
                ret.editor = {
                  type: 'text',
                }
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000748'), // 작업자ID
          name: 'oprr_id',
          fieldName: 'oprr_id',
          width: '100',
          editable: false,
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'oprr_nm',
          fieldName: 'oprr_nm',
          width: '100',
          editor: {
            maxLength: 20,
          },
        },
        {
          header: this.$t('LB00000745'), // 작업구분
          name: 'oprn_cl_cd',
          fieldName: 'oprn_cl_cd',
          width: '80',
          lookupDisplay: true,
          values: this.selSH00000010.cd,
          labels: this.selSH00000010.cd_nm,
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            return ret
          },
        },
        {
          header: this.$t('LB00000744'), // 자격 유효일
          name: 'crtf_vld_end_dt',
          fieldName: 'crtf_vld_end_dt',
          width: '80',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00000743'), // 건설기초안전교육 이수일
          name: 'buil_bsc_sft_trn_fcst_dt',
          fieldName: 'buil_bsc_sft_trn_fcst_dt',
          width: '100',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
        {
          header: this.$t('LB00000742'), // 삭제여부
          name: 'oprr_del_yn',
          fieldName: 'oprr_del_yn',
          width: '80',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            values: ['Y', 'N'],
            labels: ['Y', 'N'],
          },
        },
        {
          header: this.$t('LB00000121'), // 첨부파일
          name: 'oprr_atfl_cnt',
          fieldName: 'oprr_atfl_cnt',
          width: '100',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
        },
      ]

      const columns2 = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '100',
          lookupDisplay: true,
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          styleName: 'left',
          editable: false,
          // editor: {
          //   type: 'dropdown',
          //   domainOnly: true,
          //   textReadOnly: true,
          //   displayLabels: 'label',
          // },
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.renderer = {
                  type: 'dropdown',
                }
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000741'), // 구분
          name: 'trn_cl_cd',
          fieldName: 'trn_cl_cd',
          width: '80',
          lookupDisplay: true,
          values: this.selSH00000011.cd,
          labels: this.selSH00000011.cd_nm,
          styleName: 'left',
          editor: {
            type: 'dropdown',
            domainOnly: true,
            textReadOnly: true,
            displayLabels: 'label',
          },
          styleCallback(grid, dataCell) {
            const ret = {}
            return ret
          },
        },
        {
          header: this.$t('LB00000740'), // 교육 이수일
          name: 'trn_fcst_dt',
          fieldName: 'trn_fcst_dt',
          width: '80',
          styles: {
            datetimeFormat: 'yyyy-MM-dd',
          },
          editor: {
            type: 'date',
            mask: {
              editMask: '9999-99-99',
              placeHolder: 'yyyy-MM-dd',
              includedFormat: true,
            },
            datetimeFormat: 'yyyy-MM-dd',
          },
        },
      ]
      // 그리드 1 시작
      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: false })
      gridView.editOptions.editable = true
      gridView.displayOptions.selectionStyle = 'block'
      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'

      gridView.onCellClicked = async function (grid, index, clickData) {
        if (dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount()) {
          gridView.commit()
          gridView2.commit()
          // 저장 여부 확인
          const res = await this.$confirm(this.$t('MS00000060'))
          if (!res) {
            return
          }
        }
        if (index.itemIndex < 0) return
        const value = gridView.getValue(index.itemIndex, 'oprr_id')
        const chkGb = gridView.getValue(index.itemIndex, 'chk_gb')
        this.oprrRow = index.itemIndex
        if (chkGb === 'Y') {
          this.chkYn = 'Y'
          this.schData.oprr_id = value
          // 사업장 교육 이수 정보 조회
          this.selectFcst()
        } else {
          this.chkYn = 'N'
        }
      }.bind(this)

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        this.chkRowVal = itemIndex.dataRow
        if (itemIndex.column === 'oprr_atfl_cnt') {
          const atfileNo = gridView.getValue(itemIndex.itemIndex, 'oprr_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData)
        }
      }.bind(this)

      // 그리드 1 끝

      // 그리드 2 시작
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: true, width: 50 })
      gridView2.editOptions.editable = true
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      // 그리드 2 끝

      const excelcolumns = [
        {
          header: this.$t('LB00000701'), // 성명
          name: 'oprr_nm',
          fieldName: 'oprr_nm',
          width: '100',
          editor: {
            maxLength: 20,
          },
        },
        {
          header: this.$t('LB00000745'), // 작업구분
          name: 'oprn_cl_cd',
          fieldName: 'oprn_cl_cd',
          width: '80',
          styleName: 'left',
        },
        {
          header: this.$t('LB00000750'), // 자격유효일
          name: 'crtf_vld_end_dt',
          fieldName: 'crtf_vld_end_dt',
          width: '80',
        },
        {
          header: this.$t('LB00000749'), // 건설기초안전교육이수일
          name: 'buil_bsc_sft_trn_fcst_dt',
          fieldName: 'buil_bsc_sft_trn_fcst_dt',
          width: '100',
        },
        {
          header: this.$t('LB00000742'), // 삭제여부
          name: 'oprr_del_yn',
          fieldName: 'oprr_del_yn',
          width: '80',
        },
      ]

      // 그리드 3 시작
      dataProvider3 = new LocalDataProvider(false)
      dataProvider3.setFields(fields)
      gridView3 = new GridView('exceltemp')
      gridView3.displayOptions.selectionStyle = 'block'
      gridView3.setDataSource(dataProvider3)
      gridView3.setColumns(excelcolumns)
      gridView3.setFooters({ visible: false })
      gridView3.setStateBar({ visible: false })
      gridView3.setCheckBar({ visible: false })
      gridView3.editOptions.editable = true
      gridView3.displayOptions.selectionStyle = 'block'

      gridView3.header.height = 39
      gridView3.displayOptions.rowHeight = 40
      gridView3.footer.height = 40
      gridView3.displayOptions.fitStyle = 'fill'

      if (this.frmData.otsr_con_bsen_id !== '') {
        this.select()
      }
    })
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    gridView2.commit()
    if (this.modify) {
      if (
        dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() ||
        dataProvider2.getRowStateCount('none') !== dataProvider2.getRowCount() ||
        JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)
      ) {
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
    }
  },
  methods: {
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    oprrAdd() {
      gridView.commit()
      dataProvider.addRow({
        _chk: false,
        chk_gb: 'N',
        otsr_con_bsen_id: '',
        oprr_id: '',
        oprr_nm: '',
        oprr_tphn: '',
        oprn_cl_cd: '',
        oprn_cl_nm: '',
        crtf_vld_end_dt: this.get_date_str_gubun(dateVal, '-'),
        buil_bsc_sft_trn_fcst_dt: this.get_date_str_gubun(dateVal, '-'),
        oprr_dtrm_yn: '',
        oprr_del_yn: 'N',
        oprr_atfl_no: '',
        oprr_atfl_cnt: 0,
        lsch_nm: '',
        upt_dt: '',
      })
    },
    oprrDel() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getValue(i, '_chk')) {
          checkedRows.push(i)
        }
      }
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    fcstAdd() {
      if (this.chkYn === 'N') {
        this.$toast.show(this.$t('MS00000295'), { className: 'toast_error' }) // 저장된 작업자 정보가 아닙니다.
        return
      }
      gridView2.commit()
      dataProvider2.addRow({
        oprr_id: dataProvider.getValue(this.oprrRow, 'oprr_id'),
        wkpl_id: this.user.wkpl_id,
        wkpl_nm: '',
        trn_fcst_sn: '',
        trn_cl_cd: '',
        trn_cl_nm: '',
        trn_fcst_dt: this.get_date_str_gubun(dateVal, '-'),
      })
    },
    fcstDel() {
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    async selectFcst() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/otsr-con-bsen-fcst-dtl`, { params: this.schData })
      console.log(data) // eslint-disable-line no-console
      this.fcstData = data.fcst
      dataProvider2.setRows(data.fcst)
      gridView2.refresh()
      gridView2.setTopItem(0)
      this.fcstTotalcnt = dataProvider2.getRowCount()
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/otsr-con-bsen-dtl/${this.schData.otsr_con_bsen_id}`)
      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터
      } else {
        this.frmData = []
        this.frmTempData = []
      }
      console.log(data) // eslint-disable-line no-console
      this.oprrData = data.oprr
      dataProvider.setRows(data.oprr)
      gridView.refresh()
      gridView.setTopItem(0)
      this.oprrTotalcnt = dataProvider.getRowCount()
    },
    async save(gbVal) {
      gridView.commit()

      if (
        dataProvider.getRowStateCount('none') === dataProvider.getRowCount() &&
        dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount() &&
        JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)
      ) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        return
      }

      // Validation 체크
      if (!this.saveValidateFcst()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider.getRowStateCount('updated') > 0 || dataProvider.getRowStateCount('deleted') > 0 || dataProvider.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider.getRowCount(); i++) {
          const temp = {}

          let vldDtVal = dataProvider.getValue(i, 'crtf_vld_end_dt')
          if (!this.isEmpty(dataProvider.getValue(i, 'crtf_vld_end_dt'))) {
            vldDtVal = vldDtVal.replace(/-/gi, '')
          }
          let builDtVal = dataProvider.getValue(i, 'buil_bsc_sft_trn_fcst_dt')
          if (!this.isEmpty(dataProvider.getValue(i, 'buil_bsc_sft_trn_fcst_dt'))) {
            builDtVal = builDtVal.replace(/-/gi, '')
          }

          temp.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id
          temp.crtf_vld_end_dt = vldDtVal
          temp.buil_bsc_sft_trn_fcst_dt = builDtVal
          temp.oprr_id = dataProvider.getValue(i, 'oprr_id')
          temp.oprr_nm = dataProvider.getValue(i, 'oprr_nm')
          temp.oprr_tphn = dataProvider.getValue(i, 'oprr_tphn')
          temp.oprn_cl_cd = dataProvider.getValue(i, 'oprn_cl_cd')
          temp.oprr_dtrm_yn = dataProvider.getValue(i, 'oprr_dtrm_yn')
          temp.oprr_del_yn = dataProvider.getValue(i, 'oprr_del_yn')
          temp.oprr_atfl_no = dataProvider.getValue(i, 'oprr_atfl_no')
          temp.username = this.user.emp_now

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertOprrRow = insertRow
        this.frmData.updateOprrRow = updateRow
        this.frmData.deleteOprrRow = deleteRow
      }

      if (dataProvider2.getRowStateCount('updated') > 0 || dataProvider2.getRowStateCount('deleted') > 0 || dataProvider2.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          let fcstDtVal = dataProvider2.getValue(i, 'trn_fcst_dt')
          if (!this.isEmpty(dataProvider2.getValue(i, 'trn_fcst_dt'))) {
            fcstDtVal = fcstDtVal.replace(/-/gi, '')
          }

          temp.trn_fcst_dt = fcstDtVal
          temp.oprr_id = dataProvider2.getValue(i, 'oprr_id')
          temp.wkpl_id = dataProvider2.getValue(i, 'wkpl_id')
          temp.trn_fcst_sn = dataProvider2.getValue(i, 'trn_fcst_sn')
          temp.trn_cl_cd = dataProvider2.getValue(i, 'trn_cl_cd')
          temp.username = this.user.emp_now

          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertFcstRow = insertRow
        this.frmData.updateFcstRow = updateRow
        this.frmData.deleteFcstRow = deleteRow
      }

      const infoRow = []
      const temp = {}

      temp.otsr_con_bsen_id = this.frmData.otsr_con_bsen_id
      temp.otsr_con_bsen_nm = this.frmData.otsr_con_bsen_nm
      temp.otsr_con_bsen_addr = this.frmData.otsr_con_bsen_addr
      temp.usg_yn = this.frmData.usg_yn
      temp.rprv_nm = this.frmData.rprv_nm
      temp.bspr_no = this.frmData.bspr_no
      temp.otsr_con_bsen_tphn = this.frmData.otsr_con_bsen_tphn
      temp.otsr_con_bsen_dtrm_yn = this.frmData.otsr_con_bsen_dtrm_yn
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      await this.$axios.put('/api/v1/she/cstprms/otsr-con-bsen-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.otsr_con_bsen_id = res.data.key1
          this.select()
          this.selectFcst()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async fcstSave() {
      gridView2.commit()

      if (this.chkYn === 'N') {
        this.$toast.show(this.$t('MS00000295'), { className: 'toast_error' }) // 저장된 작업자 정보가 아닙니다.
        return
      }

      if (dataProvider2.getRowStateCount('none') === dataProvider2.getRowCount()) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidateFcst()) {
        return
      }

      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (dataProvider2.getRowStateCount('updated') > 0 || dataProvider2.getRowStateCount('deleted') > 0 || dataProvider2.getRowStateCount('created') > 0) {
        const insertRow = []
        const updateRow = []
        const deleteRow = []

        for (let i = 0; i < dataProvider2.getRowCount(); i++) {
          const temp = {}

          let fcstDtVal = dataProvider2.getValue(i, 'trn_fcst_dt')
          if (!this.isEmpty(dataProvider2.getValue(i, 'trn_fcst_dt'))) {
            fcstDtVal = fcstDtVal.replace(/-/gi, '')
          }

          temp.trn_fcst_dt = fcstDtVal
          temp.oprr_id = dataProvider2.getValue(i, 'oprr_id')
          temp.wkpl_id = dataProvider2.getValue(i, 'wkpl_id')
          temp.trn_fcst_sn = dataProvider2.getValue(i, 'trn_fcst_sn')
          temp.trn_cl_cd = dataProvider2.getValue(i, 'trn_cl_cd')
          temp.username = this.user.emp_now

          if (dataProvider2.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider2.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertFcstRow = insertRow
        this.frmData.updateFcstRow = updateRow
        this.frmData.deleteFcstRow = deleteRow
      }

      await this.$axios.put('/api/v1/she/cstprms/otsr-con-bsen-fcst-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.selectFcst()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    saveValidate() {
      gridView.commit()
      if (this.isEmpty(this.frmData.otsr_con_bsen_nm)) {
        this.$toast.show(this.$t('MS00000284'), { className: 'toast_error' }) // 협력사명을 입력해주세요.
        this.$refs.otsr_con_bsen_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.otsr_con_bsen_nm) && this.frmData.otsr_con_bsen_nm.length > 100) {
        this.$toast.show(this.$t('MS00000313'), { className: 'toast_error' }) // 협력사명은 100자 이하로 입력해주세요.
        this.$refs.otsr_con_bsen_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.otsr_con_bsen_addr)) {
        this.$toast.show(this.$t('MS00000312'), { className: 'toast_error' }) // 주소를 입력해주세요.
        this.$refs.otsr_con_bsen_addr.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.otsr_con_bsen_addr) && this.frmData.otsr_con_bsen_addr.length > 2000) {
        this.$toast.show(this.$t('MS00000311'), { className: 'toast_error' }) // 주소는 2000자 이하로 입력해주세요.
        this.$refs.otsr_con_bsen_addr.focus()
        return false
      }
      if (this.isEmpty(this.frmData.bspr_no)) {
        this.$toast.show(this.$t('MS00000310'), { className: 'toast_error' }) // 사업자 등록번호를 입력해주세요.
        this.$refs.bspr_no.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.bspr_no) && this.frmData.bspr_no.length > 13) {
        this.$toast.show(this.$t('MS00000309'), { className: 'toast_error' }) // 사업자 등록번호는 13자 이하로 입력해주세요.
        this.$refs.bspr_no.focus()
        return false
      }
      if (this.isEmpty(this.frmData.rprv_nm)) {
        this.$toast.show(this.$t('MS00000308'), { className: 'toast_error' }) // 대표자명을 입력해주세요.
        this.$refs.rprv_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.rprv_nm) && this.frmData.rprv_nm.length > 100) {
        this.$toast.show(this.$t('MS00000307'), { className: 'toast_error' }) // 대표자명은 100자 이하로 입력해주세요.
        this.$refs.rprv_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.otsr_con_bsen_tphn)) {
        this.$toast.show(this.$t('MS00000306'), { className: 'toast_error' }) // 전화번호를 입력해주세요.
        this.$refs.otsr_con_bsen_tphn.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.otsr_con_bsen_tphn) && this.frmData.otsr_con_bsen_tphn.length > 20) {
        this.$toast.show(this.$t('MS00000305'), { className: 'toast_error' }) // 전화번호는 20자 이하로 입력해주세요.
        this.$refs.otsr_con_bsen_tphn.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.usg_yn)) {
      //   this.$toast.show(this.$t('MS00000304'), { className: 'toast_error' }) // 사용여부를 입력해주세요.
      //   this.$refs.usg_yn.focus()
      //   return false
      // }

      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'oprr_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprr_nm' })
          this.$toast.show(this.$t('MS00000303'), { className: 'toast_error' }) // 작업자를 입력해주세요.
          return false
        }
        // let passyn = null
        // for (let j = 0; j < dataProvider.getRowCount(); j++) {
        //   if (i === j) {
        //     continue
        //   }
        //   if (dataProvider.getValue(j, 'oprr_id') === dataProvider.getValue(i, 'oprr_id')) {
        //     passyn = 'N'
        //   }
        // }
        // if (passyn === 'N') {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'oprr_id' })
        //   this.$toast.show('동일한 작업자가 있습니다.', { className: 'toast_error' }) // 동일한 작업자가 있습니다.
        //   return false
        // }
        if (this.isEmpty(dataProvider.getValue(i, 'oprn_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprn_cl_cd' })
          this.$toast.show(this.$t('MS00000302'), { className: 'toast_error' }) // 작업구분을 입력해주세요.
          return false
        }
        // if (this.isEmpty(dataProvider.getValue(i, 'crtf_vld_end_dt'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'crtf_vld_end_dt' })
        //   this.$toast.show('자격유효일을 입력해주세요.', { className: 'toast_error' }) // 자격유효일을 입력해주세요.
        //   return false
        // }
        // if (this.isEmpty(dataProvider.getValue(i, 'buil_bsc_sft_trn_fcst_dt'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'buil_bsc_sft_trn_fcst_dt' })
        //   this.$toast.show('건설기초안전 교육이수일을 입력해주세요.', { className: 'toast_error' }) // 건설기초안전 교육이수일을 입력해주세요.
        //   return false
        // }
        if (this.isEmpty(dataProvider.getValue(i, 'oprr_del_yn'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'oprr_del_yn' })
          this.$toast.show(this.$t('MS00000301'), { className: 'toast_error' }) // 삭제여부를 입력해주세요.
          return false
        }
      }
      return true
    },
    saveValidateFcst() {
      gridView2.commit()
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'wkpl_id'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'wkpl_id' })
          this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'trn_cl_cd'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'trn_cl_cd' })
          this.$toast.show(this.$t('MS00000299'), { className: 'toast_error' }) // 교육구분을 입력해주세요.
          return false
        }
        let passyn = null
        for (let j = 0; j < dataProvider2.getRowCount(); j++) {
          if (i === j) {
            continue
          }
          if (
            dataProvider2.getValue(j, 'oprr_id') === dataProvider2.getValue(i, 'oprr_id') &&
            dataProvider2.getValue(j, 'trn_cl_cd') === dataProvider2.getValue(i, 'trn_cl_cd') &&
            dataProvider2.getValue(j, 'wkpl_id') === dataProvider2.getValue(i, 'wkpl_id')
          ) {
            passyn = 'N'
          }
        }
        if (passyn === 'N') {
          gridView2.setCurrent({ dataRow: i, fieldName: 'trn_cl_cd' })
          this.$toast.show(this.$t('MS00000298'), { className: 'toast_error' }) // 동일한 교육구분이 있습니다.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'trn_fcst_dt'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'trn_fcst_dt' })
          this.$toast.show(this.$t('MS00000297'), { className: 'toast_error' }) // 교육이수일을 입력해주세요.
          return false
        }
      }
      return true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    filepopclose(returnData) {
      gridView.commit()
      console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        dataProvider.setValue(this.chkRowVal, 'oprr_atfl_no', returnData.atfile_no)
        dataProvider.setValue(this.chkRowVal, 'oprr_atfl_cnt', returnData.file_cnt)
      }
    },
    goList() {
      this.$router.push({
        name: `cstprms-bsenlist`,
        params: {
          schData: {
            otsr_con_bsen_id: this.tmpSchData.otsr_con_bsen_id,
            otsr_con_bsen_nm: this.tmpSchData.otsr_con_bsen_nm,
            usg_yn: this.tmpSchData.usg_yn,
          },
        },
      })
    },
    excelTemp() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00000950') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 업로드양식

      gridView3.exportGrid({
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
    handleXlsFile(e) {
      const f = this.files
      const reader = new FileReader()
      reader.onload = function (e) {
        const data = e.target.result
        const arr = this.fixdata(data)
        const workbook = XLSX.read(btoa(arr), { type: 'base64' })

        this.process_wb(workbook)
        this.totalcnt = gridView.getItemCount()
        /* DO SOMETHING WITH workbook HERE */
      }.bind(this)
      reader.readAsArrayBuffer(f)
      this.excel = !this.excel
      this.files = []
    },
    fixdata(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    },
    process_wb(wb) {
      gridView.commit()
      const output = this.to_json(wb)
      const sheetNames = Object.keys(output)
      if (sheetNames.length > 0) {
        const colsObj = output[sheetNames][0]
        if (colsObj) {
          dataProvider.fillJsonData(output, { rows: sheetNames[0] })
        }
      }
    },
    to_json(workbook) {
      const result = {}
      workbook.SheetNames.forEach(function (sheetName) {
        const roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {})
        // 엑셀 컬럼과 그리드 컬럼 매핑
        if (roa.length > 0) {
          roa.forEach((o) => {
            o.oprr_nm = o.성명
            o.oprn_cl_cd = o.작업구분
            o.crtf_vld_end_dt = o.자격유효일
            o.buil_bsc_sft_trn_fcst_dt = o.건설기초안전교육이수일
            o.oprr_del_yn = o.삭제여부
          })
          result[sheetName] = roa
        }
      })
      // 코드값 세팅
      const rtnResult = this.excelCodeSet(result)
      return rtnResult
    },
    excelCodeSet(obj) {
      const result = obj
      // 코드값 세팅
      // 엑셀 row 갯수만큼
      for (let i = 0; i < result.sheet1.length; i++) {
        // 작업구분
        const orgVal = result.sheet1[i].oprn_cl_cd
        const idx = this.selSH00000010.cd_nm.indexOf(orgVal)
        let cdVal = ''
        if (idx > -1) {
          cdVal = this.selSH00000010.cd[idx]
        } else {
          cdVal = ''
        }
        result.sheet1[i].oprn_cl_cd = cdVal

        // let crtfDt = result.sheet1[i].crtf_vld_end_dt
        // crtfDt = crtfDt.replace(/-/gi, '')
        // result.sheet1[i].crtf_vld_end_dt = crtfDt

        // 삭제여부
        if (result.sheet1[i].oprr_del_yn !== 'Y' && result.sheet1[i].oprr_del_yn !== 'N') {
          result.sheet1[i].oprr_del_yn = ''
        }
      }
      return result
    },
  },
}
</script>
<style lang="scss" scoped></style>
