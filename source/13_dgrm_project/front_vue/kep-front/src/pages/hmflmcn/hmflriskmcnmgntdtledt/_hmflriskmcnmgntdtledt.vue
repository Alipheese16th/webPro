<template>
  <div>
    <div class="location">
      <!-- Home > 유해위험기계기구 관리 > 유해위험기계기구 관리 -->
      <span>Home > {{ $t('LB00000980') }} > {{ $t('LB00000980') }}</span>
    </div>
    <div class="work_title">
      <!-- 유해위험기계기구 관리 -->
      <h2>{{ $t('LB00000980') }}</h2>
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
                <!--사업장-->
                <div class="label_tit essn">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
                  v-model="frmData.wkpl_id"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="F_codes.F_00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--기기종류-->
                <div class="label_tit essn">{{ $t('LB00000982') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="hrm_type_cd"
                  v-model="frmData.hrm_type_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000004p"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
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
                <!--기기명-->
                <div class="label_tit essn">{{ $t('LB00000983') }}</div>
              </v-col>
              <v-col cols="7">
                <v-text-field
                  ref="hrm_nm"
                  v-model="frmData.hrm_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--유해위험기계기구 번호-->
                <div class="label_tit essn">{{ $t('LB00000984') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_mgnt_no"
                  v-model="frmData.hrm_mgnt_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="50"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--설치일자-->
                <div class="label_tit">{{ $t('LB00000985') }}</div>
              </v-col>
              <v-col cols="3">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="hrm_istl_dt"
                      v-model="frmData.hrm_istl_dt"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar mr-1"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="frmData.hrm_istl_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--제조사-->
                <div class="label_tit">{{ $t('LB00000986') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_mnfr_nm"
                  v-model="frmData.hrm_mnfr_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--자산번호-->
                <div class="label_tit">{{ $t('LB00000987') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_asst_no"
                  v-model="frmData.hrm_asst_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="50"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--위치-->
                <div class="label_tit essn">{{ $t('LB00000644') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_wkpl_loc_id"
                  v-model="frmData.hrm_wkpl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popRgnOpen()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--상세위치-->
                <div class="label_tit">{{ $t('LB00000681') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_dtl_loc_nm"
                  v-model="frmData.hrm_dtl_loc_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  text-xs-right
                  clearable
                  height="30"
                  maxlength="200"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--점검주기-->
                <div class="label_tit essn">{{ $t('LB00000907') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="hrm_chk_cyc_cd"
                  v-model="frmData.hrm_chk_cyc_cd"
                  label="SELECT"
                  item-text="label"
                  item-value="value"
                  :items="codes.SH00000005p"
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
                <!--담당자(정)-->
                <div class="label_tit essn">{{ $t('LB00000989') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_rspn_emp_id"
                  v-model="frmData.hrm_rspn_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popOpen('pric')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--담당자(부)-->
                <div class="label_tit">{{ $t('LB00000990') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="hrm_spic_emp_id"
                  v-model="frmData.hrm_spic_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  class="icon_sch"
                  clearable
                  readonly
                  @click="popOpen('spic')"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="hrm_usg_yn" v-model="frmData.hrm_usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--유해위험기계기구 SPEC-->
                <div class="label_tit">{{ $t('LB00000991') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="hrm_spec_txt" v-model="frmData.hrm_spec_txt" outlined single-line :hide-details="true" height="60" class="mt-2" dense></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--안전점검 목록-->
        <h3>{{ $t('LB00000993') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="realgrid_container">
      <div class="group_padd"></div>
      <!-- 첨부파일 목록 -->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
        <table>
          <div v-if="true">
            <file-upload ref="upload1" input-id="file1" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="hmflriskmcnmgntdtledtuserslct1" mkey2="hmflriskmcnmgntdtledtuserslct2" @close="popclose" />
    <filePopup ref="filepop" mkey1="hmflriskmcnmgntdtledtatflgrid1" @close="filepopclose" />
    <rgnslct ref="rgbpop" :iswkpl="false" :wkplid="frmData.wkpl_id" mkey="hmflriskmcnmgntdtledt1" @close="popRgnClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import rgnslct from '../../common/rgnslct'
import filePopup from '../../common/atflgrid.vue'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

let dateVal = new Date()

export default {
  meta: {
    title: '유해위험기계기구 관리',
    key(route) {
      return `/hmflmcn/hmflmcn/${route.params.catalog}`
    },
  },
  components: {
    popup,
    FileUpload,
    filePopup,
    rgnslct,
  },
  data() {
    return {
      quillChk: false,
      wkplModify: false,
      codeid: ['SH00000005', 'SH00000004', 'SH00000006'],
      codes: {
        SH00000005: [{ cd: '', cd_nm: '' }],
        SH00000004: [{ cd: '', cd_nm: '' }],
        SH00000005p: [{ cd: '', cd_nm: '' }],
        SH00000004p: [{ cd: '', cd_nm: '' }],
        SH00000006: [{ cd: '', cd_nm: '' }],
      },
      selSH00000006: {
        cd: [],
        cd_nm: [],
      },
      wkpl_cd: [{ cd: '', cd_nm: '' }],
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      FF_00000001: [],
      F_codes: {
        F_00000001: [{ cd: '', cd_nm: '', up_cd: '' }],
      },
      totalcnt: 0,
      tmpSchData: {},
      schData: {},
      menu1: false,
      chkRowVal: 0,
      userPopGb: '',
      wkplId: '',
      hrmId: '',
      frmTempData: '',
      frmData: {
        ckFlag: false,
        wkpl_id: '',
        hrm_id: '',
        hrm_type_cd: '',
        hrm_type_nm: '',
        hrm_nm: '',
        hrm_mgnt_no: '',
        hrm_istl_dt: '',
        hrm_mnfr_nm: '',
        hrm_asst_no: '',
        hrm_wkpl_loc_id: '',
        hrm_wkpl_loc_nm: '',
        hrm_dtl_loc_nm: '',
        hrm_chk_cyc_cd: '',
        hrm_rspn_emp_id: '',
        hrm_spic_emp_id: '',
        hrm_rspn_emp_nm: '',
        hrm_spic_emp_nm: '',
        hrm_spec_txt: '',
        hrm_usg_yn: 'Y',
        hrm_atfl_no: '',
        lsch_nm: '',
      },
      // 안전검검
      listData: {
        hrm_id: '',
        hrm_chk_dt: '',
        hrm_chk_txt: '',
        hrm_chk_rslt_cd: '',
        hrm_chk_rslt_nm: '',
        hrm_chk_atfl_no: '',
        hrm_chk_atfl_cnt: 0,
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      },
      menu: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000980') // 유해위험기계기구 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.hmflriskmcnmgntdtledt !== null && this.$route.params.hmflriskmcnmgntdtledt !== undefined) {
      this.frmData.wkpl_id = this.$route.params.wkpl_id
      this.frmData.hrm_id = this.$route.params.hmflriskmcnmgntdtledt
      this.wkplId = this.$route.params.wkpl_id
      this.hrmId = this.$route.params.hmflriskmcnmgntdtledt
      this.schData.wkpl_id = this.$route.params.wkpl_id
      this.schData.hrm_id = this.$route.params.hmflriskmcnmgntdtledt
      this.wkplModify = false
    } else {
      this.frmData.wkpl_id = this.user.wkpl_id
      this.frmData.hrm_id = ''
      this.wkplId = ''
      this.hrmId = ''
      this.schData.wkpl_id = ''
      this.schData.hrm_id = ''
      this.wkplModify = true
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        hrm_type_cd: '',
        hrm_wkpl_loc_id: '',
        hrm_nm: '',
        use_yn: '',
        sDate: '',
        eDate: '',
      }
    }
    dateVal = this.getTzDateObj(this.user.time_zone)
    this.frmData.hrm_istl_dt = this.get_date_str_gubun(dateVal, '-')
  },
  mounted() {
    // 사업장 조회
    this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
      console.log(res) // eslint-disable-line no-console
      const tempWkpl = []
      res.forEach((e) => {
        tempWkpl.push({ value: e.cd, label: e.cd_nm, up_cd: e.up_cd })
      })
      this.FF_00000001 = tempWkpl
      this.F_codes.F_00000001 = tempWkpl
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT' })

      // 사업장 등록&수정하는 화면에서만 필터링 할 수 있게끔...
      const cmpy = this.user.cmpn_cd
      if (this.wkplModify === true) {
        this.codChg(cmpy)
      }
    })

    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      const tempCyc = []
      res.SH00000005.forEach((e) => {
        tempCyc.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000005p = tempCyc
      this.codes.SH00000005p.unshift({ value: '', label: 'SELECT' })

      const tempType = []
      res.SH00000004.forEach((e) => {
        tempType.push({ value: e.cd, label: e.cd_nm })
      })
      this.codes.SH00000004p = tempType
      this.codes.SH00000004p.unshift({ value: '', label: 'SELECT' })

      this.selSH00000006.cd.push('')
      this.selSH00000006.cd_nm.push('SELECT')
      res.SH00000006.forEach((i) => {
        this.selSH00000006.cd.push(i.cd)
        this.selSH00000006.cd_nm.push(i.cd_nm)
      })

      // 그리드 세팅
      const fields = [
        {
          fieldName: 'chk_gb',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_rslt_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_rslt_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_atfl_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hrm_chk_atfl_cnt',
          dataType: ValueType.TEXT,
        },
      ]
      const columns = [
        {
          header: this.$t('LB00000917'), // 점검일자
          name: 'hrm_chk_dt',
          fieldName: 'hrm_chk_dt',
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
          styleCallback(grid, dataCell) {
            const gubun = grid.getValue(dataCell.index.itemIndex, 'chk_gb')
            const ret = {}

            switch (gubun) {
              case 'N':
                ret.editable = true
                break
              case 'Y':
                ret.editable = false
                break
            }
            return ret
          },
        },
        {
          header: this.$t('LB00000901'), // 점검내용
          name: 'hrm_chk_txt',
          fieldName: 'hrm_chk_txt',
          width: '400',
          styleName: 'left',
          editor: {
            maxLength: 1000,
          },
        },
        {
          header: this.$t('LB00000646'), // 점검결과
          name: 'hrm_chk_rslt_cd',
          fieldName: 'hrm_chk_rslt_cd',
          width: '200',
          lookupDisplay: true,
          values: this.selSH00000006.cd,
          labels: this.selSH00000006.cd_nm,
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
          header: this.$t('LB00000121'), // 첨부파일
          name: 'hrm_chk_atfl_cnt',
          fieldName: 'hrm_chk_atfl_cnt',
          width: '200',
          button: 'action',
          alwaysShowButton: true,
          editable: false,
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
      gridView.setCheckBar({ visible: true, width: 50 })
      gridView.editOptions.editable = true
      gridView.displayOptions.selectionStyle = 'block'

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'

      gridView.onCellButtonClicked = function (grid, itemIndex, column) {
        // const current = gridView.getCurrent()
        this.chkRowVal = itemIndex.dataRow
        if (itemIndex.column === 'hrm_chk_atfl_cnt') {
          const atfileNo = gridView.getValue(itemIndex.itemIndex, 'hrm_chk_atfl_no')
          this.schData.atfile_no = atfileNo
          this.$refs.filepop.open(this.schData)
        }
      }.bind(this)

      // 그리드 1 끝

      if (this.frmData.hrm_id !== '') {
        this.select()
      }
    })
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (!this.wkplModify) {
      if (dataProvider.getRowStateCount('none') !== dataProvider.getRowCount() || JSON.stringify(this.frmData) !== JSON.stringify(this.frmTempData)) {
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
    add() {
      gridView.commit()
      dataProvider.addRow({
        hrm_id: '',
        hrm_chk_dt: this.get_date_str_gubun(dateVal, '-'),
        hrm_chk_txt: '',
        hrm_chk_rslt_cd: 'R01',
        hrm_chk_rslt_nm: '',
        hrm_chk_atfl_no: '',
        hrm_chk_atfl_cnt: 0,
        crt_emp_no: '',
        crt_dt: '',
        upt_emp_no: '',
        upt_dt: '',
        sdtp_cd: '',
        reg_appl_cl_cd: '',
      })
    },
    del() {
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/hmflmcn/hmfl-mcn-dtl/${this.schData.hrm_id}`)

      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        this.frmTempData = JSON.parse(JSON.stringify(this.frmData)) // 수정여부 비교 데이터

        if (!this.isEmpty(this.frmData.hrm_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.hrm_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
      } else {
        this.frmData = []
        this.frmTempData = []
      }

      this.listData = data.chk
      dataProvider.setRows(data.chk)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
    },
    chkFile() {
      this.frmData.ckFlag = true
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyNaN(str) {
      if (typeof str === 'undefined' || str == null || str === '' || isNaN(str)) return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    popOpen(val) {
      this.userPopGb = val
      this.$refs.pop.open(this.frmData, false)
    },
    popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          if (this.userPopGb === 'pric') {
            this.$set(this.frmData, 'hrm_rspn_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'hrm_rspn_emp_id', i.emp_no)
          } else {
            this.$set(this.frmData, 'hrm_spic_emp_nm', i.emp_nm)
            this.$set(this.frmData, 'hrm_spic_emp_id', i.emp_no)
          }
        })
      }
    },
    saveValidate() {
      gridView.commit()
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000300'), { className: 'toast_error' }) // 사업장을 입력해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_type_cd)) {
        this.$toast.show(this.$t('MS00000474'), { className: 'toast_error' }) // 기기종류를 입력해주세요.
        this.$refs.hrm_type_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_nm)) {
        this.$toast.show(this.$t('MS00000473'), { className: 'toast_error' }) // 기기명을 입력해주세요.
        this.$refs.hrm_nm.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_nm) && this.frmData.hrm_nm.length > 200) {
        this.$toast.show(this.$t('MS00000472'), { className: 'toast_error' }) // 기기명은 200자 이하로 입력해주세요.
        this.$refs.hrm_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_mgnt_no)) {
        this.$toast.show(this.$t('MS00000471'), { className: 'toast_error' }) // 유해위험기계기구 번호를 입력해주세요.
        this.$refs.hrm_mgnt_no.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_mgnt_no) && this.frmData.hrm_mgnt_no.length > 50) {
        this.$toast.show(this.$t('MS00000470'), { className: 'toast_error' }) // 유해위험기계기구 번호는 50자 이하로 입력해주세요.
        this.$refs.hrm_mgnt_no.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.hrm_istl_dt)) {
      //   this.$toast.show(this.$t('MS00000469'), { className: 'toast_error' }) // 설치일자를 입력해주세요.
      //   this.$refs.hrm_istl_dt.focus()
      //   return false
      // }
      // if (this.isEmpty(this.frmData.hrm_mnfr_nm)) {
      //   this.$toast.show(this.$t('MS00000468'), { className: 'toast_error' }) // 제조사를 입력해주세요.
      //   this.$refs.hrm_mnfr_nm.focus()
      //   return false
      // }
      // if (!this.isEmpty(this.frmData.hrm_mnfr_nm) && this.frmData.hrm_mnfr_nm.length > 200) {
      //   this.$toast.show(this.$t('MS00000467'), { className: 'toast_error' }) // 제조사는 200자 이하로 입력해주세요.
      //   this.$refs.hrm_mnfr_nm.focus()
      //   return false
      // }
      // if (this.isEmpty(this.frmData.hrm_asst_no)) {
      //   this.$toast.show(this.$t('MS00000466'), { className: 'toast_error' }) // 자산번호를 입력해주세요.
      //   this.$refs.hrm_asst_no.focus()
      //   return false
      // }
      // if (!this.isEmpty(this.frmData.hrm_asst_no) && this.frmData.hrm_asst_no.length > 50) {
      //   this.$toast.show(this.$t('MS00000465'), { className: 'toast_error' }) // 자산번호는 50자 이하로 입력해주세요.
      //   this.$refs.hrm_asst_no.focus()
      //   return false
      // }
      if (this.isEmpty(this.frmData.hrm_wkpl_loc_id)) {
        this.$toast.show(this.$t('MS00000403'), { className: 'toast_error' }) // 위치를 입력해주세요.
        this.$refs.hrm_wkpl_loc_id.focus()
        return false
      }
      if (!this.isEmpty(this.frmData.hrm_dtl_loc_nm) && this.frmData.hrm_dtl_loc_nm.length > 200) {
        this.$toast.show(this.$t('MS00000428'), { className: 'toast_error' }) // 상세위치는 200자 이하로 입력해주세요.
        this.$refs.hrm_dtl_loc_nm.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_chk_cyc_cd)) {
        this.$toast.show(this.$t('MS00000464'), { className: 'toast_error' }) // 점검주기를 입력해주세요.
        this.$refs.hrm_chk_cyc_cd.focus()
        return false
      }
      if (this.isEmpty(this.frmData.hrm_rspn_emp_id)) {
        this.$toast.show(this.$t('MS00000463'), { className: 'toast_error' }) // 담당자(정)을 입력해주세요.
        this.$refs.hrm_rspn_emp_id.focus()
        return false
      }
      // if (this.isEmpty(this.frmData.hrm_spic_emp_id)) {
      //   this.$toast.show(this.$t('MS00000462'), { className: 'toast_error' }) // 담당자(부)를 입력해주세요.
      //   this.$refs.hrm_spic_emp_id.focus()
      //   return false
      // }
      // if (this.isEmpty(this.frmData.hrm_usg_yn)) {
      //   this.$toast.show(this.$t('MS00000304'), { className: 'toast_error' }) // 사용여부를 입력해주세요.
      //   this.$refs.hrm_usg_yn.focus()
      //   return false
      // }

      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hrm_chk_dt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_dt' })
          this.$toast.show(this.$t('MS00000461'), { className: 'toast_error' }) // 점검일자를 입력해주세요.
          return false
        }
        let passyn = null
        for (let j = 0; j < dataProvider.getRowCount(); j++) {
          if (i === j) {
            continue
          }
          if (dataProvider.getValue(j, 'hrm_chk_dt') === dataProvider.getValue(i, 'hrm_chk_dt')) {
            passyn = 'N'
          }
        }
        if (passyn === 'N') {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_dt' })
          this.$toast.show(this.$t('MS00000460'), { className: 'toast_error' }) // 동일한 점검일자가 있습니다.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hrm_chk_txt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_txt' })
          this.$toast.show(this.$t('MS00000406'), { className: 'toast_error' }) // 점검내용을 입력해주세요.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'hrm_chk_txt')) && dataProvider.getValue(i, 'hrm_chk_txt').length > 1000) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_txt' })
          this.$toast.show(this.$t('MS00000256'), { className: 'toast_error' }) // 점검내용은 1000자 이하로 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hrm_chk_rslt_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hrm_chk_rslt_cd' })
          this.$toast.show(this.$t('MS00000258'), { className: 'toast_error' }) // 점검결과를 입력해주세요.
          return false
        }
      }
      return true
    },
    async save() {
      gridView.commit()

      // 파일 결과 함수
      await this.$refs.upload1.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmptyFile(data)) {
          this.frmData.hrm_atfl_no = data
        }
      })

      if (dataProvider.getRowStateCount('none') === dataProvider.getRowCount() && JSON.stringify(this.frmData) === JSON.stringify(this.frmTempData)) {
        this.$toast.show(this.$t('MS00000130'), { className: 'toast_error' }) // 수정사항이 없습니다.
        return
      }

      // Validation 체크
      if (!this.saveValidate()) {
        if (!this.isEmpty(this.frmData.hrm_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.hrm_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles1(data)
            }
          })
        }
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

          let hrmDtVal = dataProvider.getValue(i, 'hrm_chk_dt')
          if (!this.isEmpty(dataProvider.getValue(i, 'hrm_chk_dt'))) {
            hrmDtVal = hrmDtVal.replace(/-/gi, '')
          }

          temp.hrm_id = this.frmData.hrm_id
          temp.hrm_chk_dt = hrmDtVal
          temp.hrm_chk_txt = dataProvider.getValue(i, 'hrm_chk_txt')
          temp.hrm_chk_rslt_cd = dataProvider.getValue(i, 'hrm_chk_rslt_cd')
          temp.hrm_chk_atfl_no = dataProvider.getValue(i, 'hrm_chk_atfl_no')
          temp.username = this.user.emp_now

          if (dataProvider.getRowState(i) === 'created') {
            insertRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'updated') {
            updateRow.push(temp)
          } else if (dataProvider.getRowState(i) === 'deleted') {
            deleteRow.push(temp)
          }
        }
        this.frmData.insertRow = insertRow
        this.frmData.updateRow = updateRow
        this.frmData.deleteRow = deleteRow
      }

      const infoRow = []
      const temp = {}
      let hrmIstlDtVal = this.frmData.hrm_istl_dt
      if (!this.isEmpty(this.frmData.hrm_istl_dt)) {
        hrmIstlDtVal = hrmIstlDtVal.replace(/-/gi, '')
      }

      temp.wkpl_id = this.frmData.wkpl_id
      temp.hrm_id = this.frmData.hrm_id
      temp.hrm_type_cd = this.frmData.hrm_type_cd
      temp.hrm_nm = this.frmData.hrm_nm
      temp.hrm_mgnt_no = this.frmData.hrm_mgnt_no
      temp.hrm_istl_dt = hrmIstlDtVal
      temp.hrm_mnfr_nm = this.frmData.hrm_mnfr_nm
      temp.hrm_asst_no = this.frmData.hrm_asst_no
      temp.hrm_wkpl_loc_id = this.frmData.hrm_wkpl_loc_id
      temp.hrm_dtl_loc_nm = this.frmData.hrm_dtl_loc_nm
      temp.hrm_chk_cyc_cd = this.frmData.hrm_chk_cyc_cd
      temp.hrm_rspn_emp_id = this.frmData.hrm_rspn_emp_id
      temp.hrm_spic_emp_id = this.frmData.hrm_spic_emp_id
      temp.hrm_spec_txt = this.frmData.hrm_spec_txt
      temp.hrm_usg_yn = this.frmData.hrm_usg_yn
      temp.hrm_atfl_no = this.frmData.hrm_atfl_no
      temp.username = this.user.emp_no
      infoRow.push(temp)
      this.frmData.infoRow = infoRow

      // 파일 수정 플래그 초기화
      this.frmData.ckFlag = false

      await this.$axios.put('/api/v1/she/hmflmcn/hmfl-mcn-dtl', this.frmData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data.result === 'Success') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.schData.wkpl_id = res.data.key1
          this.schData.hrm_id = res.data.key2
          this.wkplModify = false
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    filepopclose(returnData) {
      gridView.commit()
      console.log(returnData) // eslint-disable-line no-console
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        dataProvider.setValue(this.chkRowVal, 'hrm_chk_atfl_no', returnData.atfile_no)
        dataProvider.setValue(this.chkRowVal, 'hrm_chk_atfl_cnt', returnData.file_cnt)
      }
    },
    setFiles1(files) {
      this.$nextTick(() => {
        this.$refs.upload1.setFiles(files)
        this.$refs.upload1.setEdit(false)
        this.$refs.upload1.setDelete(false)
        this.changeEditMode(true)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload1.setEdit(mode)
    },
    popRgnOpen() {
      if (this.isEmpty(this.frmData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }
      this.$refs.rgbpop.open(this.frmData, false)
    },
    popRgnClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.frmData, 'hrm_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.frmData, 'hrm_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ value: '', label: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `hmflmcn-hmflriskmcnmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            hrm_type_cd: this.tmpSchData.hrm_type_cd,
            hrm_wkpl_loc_id: this.tmpSchData.hrm_wkpl_loc_id,
            hrm_nm: this.tmpSchData.hrm_nm,
            use_yn: this.tmpSchData.use_yn,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
  },
}
</script>
<style></style>
