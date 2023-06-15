<template>
  <div>
    <div class="location">
      <!-- Home > 건강관리실 관리 -->
      <span>Home > {{ $t('LB00001126') }}</span>
    </div>
    <div class="work_title">
      <!-- 건강관리실 관리 -->
      <h2>{{ $t('LB00001126') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--회사-->
                <div class="label_tit">{{ $t('LB00000700') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="cmpy_cd"
                  v-model="schData.cmpy_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  disabled
                  @change="codChg"
                ></v-select>
              </v-col>
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
                <!--직영구분-->
                <div class="label_tit">{{ $t('LB00001127') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.user_blng_bsen_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000054"
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
                <!--이용일자-->
                <div class="label_tit">{{ $t('LB00001128') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sDate"
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
                  <v-date-picker v-model="schData.sDate" :max="schData.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.eDate"
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
                  <v-date-picker v-model="schData.eDate" :min="schData.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  v-model="schData.org_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  @keypress.enter="search"
                ></v-text-field>
              </v-col>
              <!-- <v-col cols="1"> -->
              <!--조치구분-->
              <!-- <div class="label_tit">{{ $t('LB00001129') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.hlth_maof_actn_cl_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000039"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col> -->
              <v-col cols="1">
                <!--증상분류-->
                <div class="label_tit">{{ $t('LB00001130') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="schData.user_symp_cat_cd"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000040"
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
            <!-- 초기화 -->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!-- 검색 -->
            <v-btn color="primary" depressed height="36" @click="search">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--건강관리실 이용 목록-->
        <h3>{{ $t('LB00001131') }}</h3>
      </div>
      <div class="grid_header">
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--엑셀 다운로드-->
          <!-- <v-btn color="primary" depressed height="30" @click="excelExport">{{ $t('LB00000214') }}</v-btn> -->
          <!-- 추가 -->
          <v-btn color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn color="primary" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 429px"></div>
      <div id="realgrid1" style="width: 100%; height: 481px; display: none"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
        </div>
      </div>
    </div>
    <userslct ref="pop" mkey1="htcrrmedt1" mkey2="htcrrmedt2" @close="popClose" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import userslct from '../common/userslct'

let gridView = GridView
let dataProvider = LocalDataProvider

// let gridView2 = GridView
// let dataProvider2 = LocalDataProvider

let sDate = new Date()
let eDate = new Date()

export default {
  meta: {
    title: '건강관리실 관리',
  },
  components: {
    userslct,
  },
  data() {
    return {
      totalcnt: 0,
      schData: {},
      frmData: {},
      menu1: false,
      menu2: false,
      codeid: ['ST00000002', 'SH00000039', 'SH00000040', 'SH00000041', 'SH00000054', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000039: [{ cd: '', cd_nm: '' }], // 조치구분
        SH00000040: [{ cd: '', cd_nm: '' }], // 증상분류
        SH00000041: [{ cd: '', cd_nm: '' }], // 증상
        SH00000054: [{ cd: '', cd_nm: '' }], // 직영구분
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      selST00000002: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000039: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000040: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000041: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selSH00000054: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      selHsptYn: {
        cd: ['', 'Y', 'N'],
        cd_nm: ['SELECT', 'Y', 'N'],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001126') // 건강관리실 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    const cmpy = user.cmpn_cd
    sDate = this.getTzDateObj(user.time_zone)
    eDate = this.getTzDateObj(user.time_zone)
    sDate.setDate(eDate.getDate() - 30)
    this.schData.sDate = this.get_date_str_gubun(sDate, '-')
    this.schData.eDate = this.get_date_str_gubun(eDate, '-')

    if (this.$route.params.schData) {
      this.schData = this.$route.params.schData
    } else {
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL' })
        const cmpy = this.schData.cmpy_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        // res.ST00000002.forEach((i) => {
        //   this.selST00000002.cd.push(i.cd)
        //   this.selST00000002.cd_nm.push(i.cd_nm)
        // })
        res.SH00000039.forEach((i) => {
          this.selSH00000039.cd.push(i.cd)
          this.selSH00000039.cd_nm.push(i.cd_nm)
        })
        res.SH00000040.forEach((i) => {
          this.selSH00000040.cd.push(i.cd)
          this.selSH00000040.cd_nm.push(i.cd_nm)
        })
        res.SH00000041.forEach((i) => {
          this.selSH00000041.cd.push(i.cd)
          this.selSH00000041.cd_nm.push(i.cd_nm)
        })
        res.SH00000054.forEach((i) => {
          this.selSH00000054.cd.push(i.cd)
          this.selSH00000054.cd_nm.push(i.cd_nm)
        })
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000039.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000040.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000041.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.SH00000054.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })

        // 사업장 조회
        this.$axios.$get(`/api/v1/comm/wkpl`, {}).then((res) => {
          res.forEach((e) => {
            // 상위 코드 조회하여 필터링
            if (e.up_cd === cmpy) {
              this.selST00000002.cd.push(e.cd)
              this.selST00000002.cd_nm.push(e.cd_nm)
            }
          })
          this.createGrid()
          this.search()
        })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    createGrid() {
      const fields = [
        {
          fieldName: 'hlth_maof_usg_no',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_usg_dt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_blng_bsen_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_user_org_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'org_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_user_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_user_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_actn_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_symp_cat_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_symp_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hspt_care_yn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'symp_dtl_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'symp_note_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'wkpl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_blng_bsen_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'hlth_maof_actn_cl_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_symp_cat_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'user_symp_nm',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        {
          header: this.$t('LB00000021'), // 사업장
          name: 'wkpl_id',
          fieldName: 'wkpl_id',
          width: '100',
          values: this.selST00000002.cd,
          labels: this.selST00000002.cd_nm,
          lookupDisplay: true,
          editable: false,
          // editor: {
          //   type: 'list',
          //   domainOnly: true,
          //   textReadOnly: true,
          // },
        },
        {
          header: this.$t('LB00001128'), // 이용일자
          name: 'hlth_maof_usg_dt',
          fieldName: 'hlth_maof_usg_dt',
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
          // styleCallback(grid, dataCell) {
          //   const ret = {}

          //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
          //     ret.editable = true
          //   } else {
          //     ret.editable = false
          //   }

          //   return ret
          // },
        },
        {
          header: this.$t('LB00001127'), // 직영구분
          name: 'user_blng_bsen_cl_cd',
          fieldName: 'user_blng_bsen_cl_cd',
          width: '80',
          values: this.selSH00000054.cd,
          labels: this.selSH00000054.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'hlth_maof_user_nm',
          fieldName: 'hlth_maof_user_nm',
          width: '80',
          editable: false,
          styleCallback(grid, dataCell) {
            const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
            if (userblngbsenclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
          // button: 'action',
          // alwaysShowButton: true,
          // styleCallback(grid, dataCell) {
          //   const ret = {}

          //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
          //     ret.editable = true
          //   } else {
          //     ret.editable = false
          //   }

          //   return ret
          // },
        },
        {
          header: this.$t('LB00001132'), // 부서 (도급사)
          name: 'org_nm',
          fieldName: 'org_nm',
          width: '100',
          editable: false,
          styleCallback(grid, dataCell) {
            const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
            if (userblngbsenclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
          // styleCallback(grid, dataCell) {
          //   const gubun = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
          //   const ret = {}

          //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
          //     if (gubun === '02') {
          //       ret.editable = true
          //     } else {
          //       ret.editable = false
          //     }
          //   } else {
          //     ret.editable = false
          //   }

          //   return ret
          // },
        },
        {
          header: this.$t('LB00000073'), // 사번
          name: 'hlth_maof_user_id',
          fieldName: 'hlth_maof_user_id',
          width: '100',
          editable: false,
          styleCallback(grid, dataCell) {
            const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
            if (userblngbsenclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
          // styleCallback(grid, dataCell) {
          //   const gubun = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
          //   const ret = {}

          //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
          //     if (gubun === '02') {
          //       ret.editable = true
          //     } else {
          //       ret.editable = false
          //     }
          //   } else {
          //     ret.editable = false
          //   }

          //   return ret
          // },
        },
        // {
        //   header: this.$t('LB00001129'), // 조치구분
        //   name: 'hlth_maof_actn_cl_cd',
        //   fieldName: 'hlth_maof_actn_cl_cd',
        //   width: '70',
        //   values: this.selSH00000039.cd,
        //   labels: this.selSH00000039.cd_nm,
        //   lookupDisplay: true,
        //   editor: {
        //     type: 'list',
        //     domainOnly: true,
        //     textReadOnly: true,
        //   },
        // },
        {
          header: this.$t('LB00001130'), // 증상분류
          name: 'user_symp_cat_cd',
          fieldName: 'user_symp_cat_cd',
          width: '80',
          values: this.selSH00000040.cd,
          labels: this.selSH00000040.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00001133'), // 증상
          name: 'user_symp_cd',
          fieldName: 'user_symp_cd',
          width: '80',
          values: this.selSH00000041.cd,
          labels: this.selSH00000041.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        // {
        //   header: this.$t('LB00000828'), // 병원치료
        //   name: 'hspt_care_yn',
        //   fieldName: 'hspt_care_yn',
        //   width: '60',
        //   values: this.selHsptYn.cd,
        //   labels: this.selHsptYn.cd_nm,
        //   lookupDisplay: true,
        //   editor: {
        //     type: 'list',
        //     domainOnly: true,
        //     textReadOnly: true,
        //   },
        // },
        {
          header: this.$t('LB00001134'), // 증상 상세정보
          name: 'symp_dtl_txt',
          fieldName: 'symp_dtl_txt',
          width: '150',
        },
        {
          header: this.$t('LB00000095'), // 비고
          name: 'symp_note_txt',
          fieldName: 'symp_note_txt',
          width: '150',
        },
      ]

      dataProvider = new LocalDataProvider(false)
      dataProvider.setFields(fields)
      gridView = new GridView('realgrid')
      gridView.setDataSource(dataProvider)
      gridView.setColumns(columns)
      gridView.setFooters({ visible: false })
      gridView.setStateBar({ visible: false })
      gridView.setCheckBar({ visible: true })

      gridView.header.height = 39
      gridView.displayOptions.rowHeight = 40
      gridView.footer.height = 40
      gridView.displayOptions.fitStyle = 'fill'
      gridView.displayOptions.selectionStyle = 'block'
      gridView.editOptions.editable = true

      gridView.onCellClicked = function (grid, clickData) {
        this.frmData.ckFlag = true
        const userblngbsenclcd = gridView.getValue(clickData.dataRow, 'user_blng_bsen_cl_cd')
        if (userblngbsenclcd === '01') {
          if (clickData.cellType === 'data' && (clickData.column === 'hlth_maof_user_nm' || clickData.column === 'org_nm' || clickData.column === 'hlth_maof_user_id')) {
            grid.commit()
            this.popupNm = 'dfdt'
            this.schData.usr_nm = gridView.getValue(clickData.dataRow, 'hlth_maof_user_nm')
            this.$refs.pop.open(this.schData, false)
          }
        }
      }.bind(this)

      // gridView.onCellButtonClicked = function (grid, item, column) {
      //   grid.commit()
      //   if (
      //     dataProvider.getRowState(item.itemIndex) === 'created' ||
      //     dataProvider.getRowState(item.itemIndex) === 'appending' ||
      //     dataProvider.getRowState(item.itemIndex) === 'inserting'
      //   ) {
      //     if (column.fieldName === 'hlth_maof_user_nm') {
      //       this.schData.usr_nm = gridView.getValue(item.itemIndex, 'hlth_maof_user_nm')
      //       this.$refs.pop.open(this.schData, false)
      //     }
      //   }
      // }.bind(this)

      // gridView.onCellEdited = function (grid, itemIndex, row, field) {
      //   gridView.commit()
      //   this.frmData.ckFlag = true
      //   if (gridView.getValue(itemIndex, 'user_blng_bsen_cl_cd') !== '02') {
      //      dataProvider.setValue(row, 'hlth_maof_user_nm', '')
      //      dataProvider.setValue(row, 'org_nm', '')
      //      dataProvider.setValue(row, 'hlth_maof_user_id', '')
      //    }
      // }.bind(this)

      // const columns2 = [
      //   {
      //   header: this.$t('LB00000021'), // 사업장
      //   name: 'wkpl_id',
      //   fieldName: 'wkpl_id',
      //   width: '100',
      //   values: this.selST00000002.cd,
      //   labels: this.selST00000002.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      // {
      //   header: this.$t('LB00001128'), // 이용일자
      //   name: 'hlth_maof_usg_dt',
      //   fieldName: 'hlth_maof_usg_dt',
      //   width: '80',
      //   styles: {
      //     datetimeFormat: 'yyyy-MM-dd',
      //   },
      //   editor: {
      //     type: 'date',
      //     mask: {
      //       editMask: '9999-99-99',
      //       placeHolder: 'yyyy-MM-dd',
      //       includedFormat: true,
      //     },
      //     datetimeFormat: 'yyyy-MM-dd',
      //   },
      // styleCallback(grid, dataCell) {
      //   const ret = {}

      //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
      //     ret.editable = true
      //   } else {
      //     ret.editable = false
      //   }

      //   return ret
      // },
      // },
      // {
      //   header: this.$t('LB00001127'), // 직영구분
      //   name: 'user_blng_bsen_cl_cd',
      //   fieldName: 'user_blng_bsen_cl_cd',
      //   width: '80',
      //   values: this.selSH00000054.cd,
      //   labels: this.selSH00000054.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      // {
      //   header: this.$t('LB00000701'), // 성명
      //   name: 'hlth_maof_user_nm',
      //   fieldName: 'hlth_maof_user_nm',
      //   width: '80',
      //   editable: false,
      //   styleCallback(grid, dataCell) {
      //     const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
      //     if (userblngbsenclcd !== '01') {
      //       return {
      //         editable: true,
      //       }
      //     }
      //   },
      // button: 'action',
      // alwaysShowButton: true,
      // styleCallback(grid, dataCell) {
      //   const ret = {}

      //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
      //     ret.editable = true
      //   } else {
      //     ret.editable = false
      //   }

      //   return ret
      // },
      // },
      // {
      //   header: this.$t('LB00001132'), // 부서 (도급사)
      //   name: 'org_nm',
      //   fieldName: 'org_nm',
      //   width: '100',
      //   editable: false,
      //   styleCallback(grid, dataCell) {
      //     const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
      //     if (userblngbsenclcd !== '01') {
      //       return {
      //         editable: true,
      //       }
      //     }
      //   },
      // styleCallback(grid, dataCell) {
      //   const gubun = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
      //   const ret = {}

      //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
      //     if (gubun === '02') {
      //       ret.editable = true
      //     } else {
      //       ret.editable = false
      //     }
      //   } else {
      //     ret.editable = false
      //   }

      //   return ret
      // },
      // },
      // {
      //   header: this.$t('LB00000073'), // 사번
      //   name: 'hlth_maof_user_id',
      //   fieldName: 'hlth_maof_user_id',
      //   width: '100',
      //   editable: false,
      //   styleCallback(grid, dataCell) {
      //     const userblngbsenclcd = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
      //     if (userblngbsenclcd !== '01') {
      //       return {
      //         editable: true,
      //       }
      //     }
      //   },
      // styleCallback(grid, dataCell) {
      //   const gubun = grid.getValue(dataCell.index.itemIndex, 'user_blng_bsen_cl_cd')
      //   const ret = {}

      //   if (dataCell.item.rowState === 'created' || dataCell.item.itemState === 'appending' || dataCell.item.itemState === 'inserting') {
      //     if (gubun === '02') {
      //       ret.editable = true
      //     } else {
      //       ret.editable = false
      //     }
      //   } else {
      //     ret.editable = false
      //   }

      //   return ret
      // },
      // },
      // {
      //   header: this.$t('LB00001129'), // 조치구분
      //   name: 'hlth_maof_actn_cl_cd',
      //   fieldName: 'hlth_maof_actn_cl_cd',
      //   width: '70',
      //   values: this.selSH00000039.cd,
      //   labels: this.selSH00000039.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      // {
      //   header: this.$t('LB00001130'), // 증상분류
      //   name: 'user_symp_cat_cd',
      //   fieldName: 'user_symp_cat_cd',
      //   width: '80',
      //   values: this.selSH00000040.cd,
      //   labels: this.selSH00000040.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      // {
      //   header: this.$t('LB00001133'), // 증상
      //   name: 'user_symp_cd',
      //   fieldName: 'user_symp_cd',
      //   width: '80',
      //   values: this.selSH00000041.cd,
      //   labels: this.selSH00000041.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      //  {
      //    header: this.$t('LB00000828'), // 병원치료
      //    name: 'hspt_care_yn',
      //    fieldName: 'hspt_care_yn',
      //    width: '60',
      //   values: this.selHsptYn.cd,
      //   labels: this.selHsptYn.cd_nm,
      //   lookupDisplay: true,
      //   editor: {
      //     type: 'list',
      //     domainOnly: true,
      //     textReadOnly: true,
      //   },
      // },
      // {
      //   header: this.$t('LB00001134'), // 증상 상세정보
      //   name: 'symp_dtl_txt',
      //   fieldName: 'symp_dtl_txt',
      //   width: '150',
      // },
      // {
      //   header: this.$t('LB00000095'), // 비고
      //   name: 'symp_note_txt',
      //   fieldName: 'symp_note_txt',
      //   width: '150',
      // },
      // ]

      // dataProvider2 = new LocalDataProvider(false)
      // dataProvider2.setFields(fields)
      // gridView2 = new GridView('realgrid1')
      // gridView2.setDataSource(dataProvider2)
      // gridView2.setColumns(columns2)
      // gridView2.setFooters({ visible: false })
      // gridView2.setStateBar({ visible: false })
      // gridView2.setCheckBar({ visible: true })

      // gridView2.header.height = 39
      // gridView2.displayOptions.rowHeight = 40
      // gridView2.footer.height = 40
      // gridView2.displayOptions.fitStyle = 'fill'
      // gridView2.displayOptions.selectionStyle = 'block'
      // gridView2.editOptions.editable = true

      // gridView.onCellEdited = function (grid, itemIndex, row, field) {
      //   this.frmData.ckFlag = true
      //   if (field === 6) {
      //     this.schData.usr_nm = grid.getValue(itemIndex, field)
      //     grid.commit()
      //     this.$refs.pop.open(this.schData, false)
      //   }
      // }.bind(this)
    },
    async search() {
      const rows = await this.$axios.$get(`/api/v1/she/hlth/htcrrm`, { params: this.schData })
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      // dataProvider2.setRows(rows)
      // gridView2.refresh()
      // gridView2.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
      this.schData.sDate = this.get_date_str_gubun(sDate, '-')
      this.schData.eDate = this.get_date_str_gubun(eDate, '-')
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
      this.codChgReturn(user.cmpn_cd)
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.schData, 'wkpl_id', '')
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    add() {
      this.frmData.ckFlag = true
      this.totalcnt++
      gridView.commit()
      dataProvider.insertRow(0, {
        wkpl_id: this.schData.wkpl_id,
        hlth_maof_usg_dt: this.get_date_str_gubun(eDate, '-'),
        hlth_maof_user_id: '',
        user_blng_bsen_cl_cd: '',
        hlth_maof_user_org_cd: '',
        org_nm: '',
        hlth_maof_user_nm: '',
        hlth_maof_actn_cl_cd: '',
        user_symp_cat_cd: '',
        user_symp_cd: '',
        hspt_care_yn: '',
        symp_dtl_txt: '',
        symp_note_txt: '',
      })
      // gridView.setCurrent({ dataRow: 0, fieldName: 'wkpl_id' })
    },
    async del() {
      const items = gridView.getCheckedItems()
      if (items.length === 0) {
        this.$toast.show(this.$t('MS00000526'), { className: 'toast_error' }) // 삭제할 데이터가 없습니다.
        return
      }

      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?(KO)
      if (!res) {
        return
      }

      let id = ''
      for (let i = 0; i < items.length; i++) {
        if (dataProvider.getRowState(items[i]) === 'created') {
          continue
        }
        id = dataProvider.getValue(items[i], 'hlth_maof_usg_no')
        console.log('id : ', id) // eslint-disable-line no-console
        await this.$axios.delete(`/api/v1/she/hlth/htcrrm/${id}`).then((res) => {
          if (res.data === 'Success') {
            this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
      this.search()
    },
    popClose(returnData) {
      if (returnData && returnData.length > 0) {
        const row = gridView.getCurrent().dataRow
        const values = {
          hlth_maof_user_org_cd: returnData[0].dept_cd,
          org_nm: returnData[0].dept_nm,
          hlth_maof_user_id: returnData[0].emp_no,
          hlth_maof_user_nm: returnData[0].emp_nm,
        }
        dataProvider.updateRow(row, values)
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    validation() {
      gridView.commit()
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'wkpl_id'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'wkpl_id' })
          this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hlth_maof_usg_dt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_usg_dt' })
          this.$toast.show(this.$t('MS00000569'), { className: 'toast_error' }) // 이용일자를 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'user_blng_bsen_cl_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'user_blng_bsen_cl_cd' })
          this.$toast.show(this.$t('MS00000570'), { className: 'toast_error' }) // 직영구분을 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'hlth_maof_user_nm'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_user_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'hlth_maof_user_nm')) && dataProvider.getValue(i, 'hlth_maof_user_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_user_nm' })
          this.$toast.show(this.$t('MS00000337'), { className: 'toast_error' }) // 성명은 50자를 초과할 수 없습니다.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'org_nm')) && dataProvider.getValue(i, 'org_nm').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'org_nm' })
          this.$toast.show(this.$t('MS00000571'), { className: 'toast_error' }) // 부서는 50자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider.getValue(i, 'hlth_maof_user_id'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_user_id' })
        //   this.$toast.show(this.$t('MS00000547'), { className: 'toast_error' }) // 사번을 입력해주세요.
        //   return false
        // }
        if (!this.isEmpty(dataProvider.getValue(i, 'hlth_maof_user_id')) && dataProvider.getValue(i, 'hlth_maof_user_id').length > 12) {
          gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_user_id' })
          this.$toast.show(this.$t('MS00000572'), { className: 'toast_error' }) // 사변은 12자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider.getValue(i, 'hlth_maof_actn_cl_cd'))) {
        //   gridView.setCurrent({ dataRow: i, fieldName: 'hlth_maof_actn_cl_cd' })
        //   this.$toast.show(this.$t('MS00000573'), { className: 'toast_error' }) // 조치구분을 입력해주세요.
        //   return false
        // }
        if (this.isEmpty(dataProvider.getValue(i, 'user_symp_cat_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'user_symp_cat_cd' })
          this.$toast.show(this.$t('MS00000574'), { className: 'toast_error' }) // 증상분류를 입력해주세요.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'user_symp_cd'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'user_symp_cd' })
          this.$toast.show(this.$t('MS00000575'), { className: 'toast_error' }) // 증상을 입력해주세요.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'symp_dtl_txt')) && dataProvider.getValue(i, 'symp_dtl_txt').length > 500) {
          gridView.setCurrent({ dataRow: i, fieldName: 'symp_dtl_txt' })
          this.$toast.show(this.$t('MS00000576'), { className: 'toast_error' }) // 증상 상세정보는 500자를 초과할 수 없습니다.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'symp_note_txt')) && dataProvider.getValue(i, 'symp_note_txt').length > 500) {
          gridView.setCurrent({ dataRow: i, fieldName: 'symp_note_txt' })
          this.$toast.show(this.$t('MS00000277'), { className: 'toast_error' }) // 비고는 500자를 초과할 수 없습니다.
          return false
        }
      }
      return true
    },
    async save() {
      if (!this.frmData.ckFlag) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }
      if (!this.validation()) return
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.frmData.insert_list = []
      this.frmData.update_list = []
      gridView.commit()
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }

        const maof = {}
        maof.hlth_maof_usg_no = dataProvider.getValue(i, 'hlth_maof_usg_no')
        maof.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
        maof.hlth_maof_usg_dt = dataProvider.getValue(i, 'hlth_maof_usg_dt')
        maof.hlth_maof_user_id = dataProvider.getValue(i, 'hlth_maof_user_id')
        maof.user_blng_bsen_cl_cd = dataProvider.getValue(i, 'user_blng_bsen_cl_cd')
        maof.hlth_maof_user_org_cd = dataProvider.getValue(i, 'hlth_maof_user_org_cd')
        maof.org_nm = dataProvider.getValue(i, 'org_nm')
        maof.hlth_maof_user_nm = dataProvider.getValue(i, 'hlth_maof_user_nm')
        maof.hlth_maof_actn_cl_cd = dataProvider.getValue(i, 'hlth_maof_actn_cl_cd')
        maof.user_symp_cat_cd = dataProvider.getValue(i, 'user_symp_cat_cd')
        maof.user_symp_cd = dataProvider.getValue(i, 'user_symp_cd')
        maof.hspt_care_yn = dataProvider.getValue(i, 'hspt_care_yn')
        maof.symp_dtl_txt = dataProvider.getValue(i, 'symp_dtl_txt')
        maof.symp_note_txt = dataProvider.getValue(i, 'symp_note_txt')

        if (dataProvider.getRowState(i) === 'created') {
          this.frmData.insert_list.push(maof)
        } else if (dataProvider.getRowState(i) === 'updated') {
          this.frmData.update_list.push(maof)
        }
      }

      this.$axios.post('/api/v1/she/hlth/htcrrm', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.ckFlag = false
          this.search()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      // 건강관리실 이용 목록
      const fileName = this.$t('LB00001131') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx'

      gridView.exportGrid({
        type: 'excel',
        target: 'local',
        fileName,
        showProgress,
        progressMessage: this.$t('LB00000751'), // 엑셀 Export중입니다.
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
