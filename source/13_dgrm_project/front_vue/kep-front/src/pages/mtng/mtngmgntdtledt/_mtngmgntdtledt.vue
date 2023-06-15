<template>
  <div>
    <div class="location">
      <!--Home > 안전 > 회의체 관리-->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000719') }}</span>
    </div>
    <div class="work_title">
      <!-- 회의체 관리 -->
      <h2>{{ $t('LB00000719') }}</h2>
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
                <!--회의 구분-->
                <div class="label_tit essn">{{ $t('LB00000728') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtng_cl_cd"
                  v-model="frmData.mtng_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--정기/임시 구분-->
                <div class="label_tit essn">{{ $t('LB00000733') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="mtng_cyc_cd"
                  v-model="frmData.mtng_cyc_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000003"
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
                <!--회의 제목-->
                <div class="label_tit essn">{{ $t('LB00000730') }}</div>
              </v-col>
              <v-col cols="7">
                <!-- 입력하세요 -->
                <v-text-field
                  ref="mtng_ttl"
                  v-model="frmData.mtng_ttl"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--회의 장소-->
                <div class="label_tit essn">{{ $t('LB00000755') }}</div>
              </v-col>
              <v-col cols="3">
                <!-- 입력하세요 -->
                <v-text-field
                  ref="mtng_pl_nm"
                  v-model="frmData.mtng_pl_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--회의 일자-->
                <div class="label_tit essn">{{ $t('LB00000726') }}</div>
              </v-col>
              <v-col cols="3">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      ref="mtng_dt"
                      v-model="frmData.mtng_dt"
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
                  <v-date-picker v-model="frmData.mtng_dt" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="1">
                <!--회의시간-->
                <div class="label_tit essn">{{ $t('LB00000756') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-select
                  ref="mtng_bgn_hm_h"
                  v-model="frmData.mtng_bgn_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="mtng_bgn_hm_s"
                  v-model="frmData.mtng_bgn_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  vtext
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;~&nbsp;</div>
                <v-select
                  ref="mtng_end_hm_h"
                  v-model="frmData.mtng_end_hm_h"
                  style="width: 70px"
                  :label="$t('LB00001341')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000074"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="hourChg()"
                ></v-select>
                <div class="label_con">&nbsp;:&nbsp;</div>
                <v-select
                  ref="mtng_end_hm_s"
                  v-model="frmData.mtng_end_hm_s"
                  style="width: 70px"
                  :label="$t('LB00001342')"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="mm5"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="hourChg()"
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
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 안건-->
        <h3>{{ $t('LB00000759') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ sbjcCnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 참석자-->
        <h3>{{ $t('LB00000761') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ atndCnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!-- 추가 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="add2">{{ $t('LB00000047') }}</v-btn>
          <!-- 삭제 -->
          <v-btn style="bottom: 3px" color="" outlined height="30" @click="del2">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid2" style="width: 100%; height: 200px"></div>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--회의 결과-->
        <h3>{{ $t('LB00000762') }}</h3>
      </div>
    </div>
    <div class="container_detail">
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.mtng_rslt_txt" :options="editorOption" />
          </v-col>
        </v-row>
      </v-container>
      <div class="group_padd"></div>
      <!--첨부파일 목록(KO)-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist">
        <table>
          <div v-if="true">
            <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--양식 다운로드-->
          <v-btn depressed width="120" color="primary" height="40" @click="formDown">{{ $t('LB00000721') }}</v-btn>
          <!--결재상신-->
          <v-btn depressed width="120" color="primary" height="40" @click="appr">{{ $t('LB00000763') }}</v-btn>
          <!--삭제-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!deleteModify" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
    <popup ref="pop" mkey1="mtngedt1" mkey2="mtngedt2" @close="popclose" />
    <filePopup ref="filepop" mkey1="mtngmgntdtledtatflgrid1" :edit-chk="false" />
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../../common/userslct'
import filePopup from '../../common/atflgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

export default {
  meta: {
    title: '회의체 관리',
    key(route) {
      return `/mtng/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
    popup,
    filePopup,
  },
  data() {
    return {
      modify: false,
      codeid: ['ST00000002', 'SH00000001', 'SH00000002', 'SH00000003', 'SH00000007', 'SH00000074'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }], // 사업장
        SH00000001: [{ cd: '', cd_nm: '' }], // 회의구분
        SH00000002: [{ cd: '', cd_nm: '' }], // 의제구분
        SH00000003: [{ cd: '', cd_nm: '' }], // 정기/임시 구분
        SH00000007: [{ cd: '', cd_nm: '' }], // 자사/협력사 구분
        SH00000074: [{ cd: '', cd_nm: '' }], // 시간 구분 코드
      },
      mm5: [
        { cd: '00', cd_nm: '00' },
        { cd: '05', cd_nm: '05' },
        { cd: '10', cd_nm: '10' },
        { cd: '15', cd_nm: '15' },
        { cd: '20', cd_nm: '20' },
        { cd: '25', cd_nm: '25' },
        { cd: '30', cd_nm: '30' },
        { cd: '35', cd_nm: '35' },
        { cd: '40', cd_nm: '40' },
        { cd: '45', cd_nm: '45' },
        { cd: '50', cd_nm: '50' },
        { cd: '55', cd_nm: '55' },
      ],
      selSH00000002: {
        cd: [],
        cd_nm: [],
      },
      selSH00000007: {
        cd: [''],
        cd_nm: ['SELECT'],
      },
      sbjcCnt: 0,
      atndCnt: 0,
      menu1: false,
      menu2: false,
      menu3: false,
      schData: {},
      frmData: {
        mtng_bgn_hm_h: '',
        mtng_bgn_hm_s: '',
        mtng_end_hm_h: '',
        mtng_end_hm_s: '',
      },
      tempData: {},
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
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      deleteModify: false,
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000719') // 회의체 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (this.modify && JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
  },
  mounted() {
    this.user = this.$store.getters['auth/getUser']
    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = this.user.cmpn_cd
        this.codChgReturn(cmpy)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        // res.SH00000002.forEach((i) => {
        //   this.selSH00000002.cd.push(i.cd)
        //   this.selSH00000002.cd_nm.push(i.cd_nm)
        // })
        res.SH00000007.forEach((i) => {
          this.selSH00000007.cd.push(i.cd)
          this.selSH00000007.cd_nm.push(i.cd_nm)
        })

        if (this.$route.params.mtngmgntdtledt !== null && this.$route.params.mtngmgntdtledt !== undefined) {
          this.frmData.mtng_id = this.$route.params.mtngmgntdtledt
          this.schData.mtng_id = this.$route.params.mtngmgntdtledt
        } else {
          this.frmData.mtng_id = ''
          this.schData.mtng_id = ''
          this.frmData.wkpl_id = this.user.wkpl_id
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
        }

        this.createGrid()

        // ID 존재할 경우
        if (this.frmData.mtng_id) {
          this.select()
          this.$refs.quill.quill.enable(false)
        } else {
          this.frmData.mtng_dt = this.getTzDateObj(this.user.time_zone)
          this.frmData.mtng_dt = this.get_date_str_gubun(this.frmData.mtng_dt, '-')
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    allowedStep: (m) => m % 5 === 0,
    createGrid() {
      // 그리드 1 시작
      const fields = [
        {
          fieldName: 'mtng_sbjc_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_txt',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_sbjc_rslt_txt',
          dataType: ValueType.TEXT,
        },
      ]

      const columns = [
        // {
        //   header: this.$t('LB00001135'), // 의제구분
        //   name: 'mtng_sbjc_cl_cd',
        //   fieldName: 'mtng_sbjc_cl_cd',
        //   width: '100',
        //   values: this.selSH00000002.cd,
        //   labels: this.selSH00000002.cd_nm,
        //   lookupDisplay: true,
        //   editor: {
        //     type: 'list',
        //     domainOnly: true,
        //     textReadOnly: true,
        //   },
        // },
        {
          header: this.$t('LB00000764'), // 의제
          name: 'mtng_sbjc_txt',
          fieldName: 'mtng_sbjc_txt',
          width: '400',
        },
        {
          header: this.$t('LB00000765'), // 결과
          name: 'mtng_sbjc_rslt_txt',
          fieldName: 'mtng_sbjc_rslt_txt',
          width: '400',
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
      gridView.editOptions.appendable = true

      gridView.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)
      // 그리드 1 끝

      // 그리드 2 시작
      const fields2 = [
        {
          fieldName: 'mtng_atnd_sn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_cl_cd',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnc_emp_id',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_dept_nm',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_tphn',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_emad',
          dataType: ValueType.TEXT,
        },
        {
          fieldName: 'mtng_atnd_pstn_nm',
          dataType: ValueType.TEXT,
        },
      ]

      const columns2 = [
        {
          header: this.$t('LB00000741'), // 구분
          name: 'mtng_atnd_cl_cd',
          fieldName: 'mtng_atnd_cl_cd',
          width: '100',
          values: this.selSH00000007.cd,
          labels: this.selSH00000007.cd_nm,
          lookupDisplay: true,
          editor: {
            type: 'list',
            domainOnly: true,
            textReadOnly: true,
          },
        },
        {
          header: this.$t('LB00000701'), // 성명
          name: 'mtng_atnd_nm',
          fieldName: 'mtng_atnd_nm',
          width: '100',
          editable: false,
          styleCallback(grid, dataCell) {
            const mtngatndclcd = grid.getValue(dataCell.index.itemIndex, 'mtng_atnd_cl_cd')
            if (mtngatndclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
          // button: 'action',
          // alwaysShowButton: true,
        },
        {
          header: this.$t('LB00000766'), // 직책
          name: 'mtng_atnd_pstn_nm',
          fieldName: 'mtng_atnd_pstn_nm',
          width: '150',
          editable: true,
          /*
          styleCallback(grid, dataCell) {
            const mtngatndclcd = grid.getValue(dataCell.index.itemIndex, 'mtng_atnd_cl_cd')
            if (mtngatndclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
          */
        },
        {
          header: this.$t('LB00000700'), // 회사
          name: 'mtng_atnd_dept_nm',
          fieldName: 'mtng_atnd_dept_nm',
          width: '150',
          editable: false,
          styleCallback(grid, dataCell) {
            const mtngatndclcd = grid.getValue(dataCell.index.itemIndex, 'mtng_atnd_cl_cd')
            if (mtngatndclcd !== '01') {
              return {
                editable: true,
              }
            }
          },
        },
      ]
      dataProvider2 = new LocalDataProvider(false)
      dataProvider2.setFields(fields2)
      gridView2 = new GridView('realgrid2')
      gridView2.setDataSource(dataProvider2)
      gridView2.setColumns(columns2)
      gridView2.setFooters({ visible: false })
      gridView2.setStateBar({ visible: false })
      gridView2.setCheckBar({ visible: true })

      gridView2.header.height = 39
      gridView2.displayOptions.rowHeight = 40
      gridView2.footer.height = 40
      gridView2.displayOptions.fitStyle = 'fill'
      gridView2.displayOptions.selectionStyle = 'block'
      gridView2.editOptions.editable = true
      gridView2.editOptions.appendable = true

      // gridView2.onCellButtonClicked = function (grid, itemIndex, column) {
      //   if (column.fieldName === 'mtng_atnd_nm') {
      //     const schData = {}
      //     schData.usr_nm = grid.getValue(itemIndex.itemIndex, 'mtng_atnd_nm')
      //     grid.commit()
      //     this.$refs.pop.open(schData, false)
      //   }
      // }.bind(this)

      gridView2.onCellClicked = function (grid, clickData) {
        const current = gridView2.getCurrent()
        const mtngatndclcd = gridView2.getValue(current.dataRow, 'mtng_atnd_cl_cd')
        if (mtngatndclcd === '01') {
          if (clickData.column === 'mtng_atnd_nm') {
            grid.commit()
            const schData = {}
            this.$refs.pop.open(schData, false)
          }
        }
      }.bind(this)

      gridView2.onCellEdited = function (grid, itemIndex, row, field) {
        this.frmData.ckFlag = true
      }.bind(this)

      // gridView2.onCellEdited = function (grid, itemIndex, row, field) {
      //   this.frmData.ckFlag = true
      //   // 3 : 성명
      //   if (field === 3) {
      //     const schData = {}
      //     schData.usr_nm = grid.getValue(itemIndex, field)
      //     grid.commit()
      //     this.$refs.pop.open(schData, false)
      //   }
      // }.bind(this)
      // 그리드 2 끝
    },
    // 시간 변경시 시작시간과 종료시간 비교
    hourChg() {
      const val1 = this.frmData.mtng_bgn_hm_h
      const val2 = this.frmData.mtng_bgn_hm_s
      const val3 = this.frmData.mtng_end_hm_h
      const val4 = this.frmData.mtng_end_hm_s
      this.frmData.mtng_bgn_hm = val1.concat(val2)
      this.frmData.mtng_end_hm = val3.concat(val4)
      if (!this.isEmpty(val1) && !this.isEmpty(val2) && !this.isEmpty(val3) && !this.isEmpty(val4)) {
        const bgnhm = val1.concat(val2)
        const endhm = val3.concat(val4)
        return bgnhm >= endhm
      }
    },
    select() {
      this.modify = true
      this.$axios.$get(`/api/v1/she/sfty/mtng/${this.frmData.mtng_id}`).then((res) => {
        this.frmData.mtng_id = res.mtng_id
        this.frmData.wkpl_id = res.wkpl_id
        this.frmData.mtng_cl_cd = res.mtng_cl_cd
        this.frmData.mtng_cyc_cd = res.mtng_cyc_cd
        this.frmData.mtng_ttl = res.mtng_ttl
        this.frmData.mtng_pl_nm = res.mtng_pl_nm
        this.frmData.mtng_dt = res.mtng_dt
        this.frmData.mtng_bgn_hm = res.mtng_bgn_hm
        this.frmData.mtng_bgn_hm_h = res.mtng_bgn_hm_h
        this.frmData.mtng_bgn_hm_s = res.mtng_bgn_hm_s
        this.frmData.mtng_end_hm = res.mtng_end_hm
        this.frmData.mtng_end_hm_h = res.mtng_end_hm_h
        this.frmData.mtng_end_hm_s = res.mtng_end_hm_s
        this.frmData.mtng_rslt_txt = res.mtng_rslt_txt
        this.frmData.mtng_rslt_prst_cd = res.mtng_rslt_prst_cd
        this.frmData.mtng_rslt_atfl_no = res.mtng_rslt_atfl_no
        this.frmData.crt_emp_no = res.crt_emp_no
        this.frmData.lsch_nm = res.lsch_nm
        // 의제
        dataProvider.setRows(res.sbjc_list)
        gridView.refresh()
        gridView.setTopItem(0)
        this.sbjcCnt = gridView.getItemCount()
        // 참석자
        dataProvider2.setRows(res.atnd_list)
        gridView2.refresh()
        gridView2.setTopItem(0)
        this.atndCnt = gridView2.getItemCount()

        const usrinfo = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if ((this.frmData.mtng_rslt_prst_cd === '10' && this.frmData.crt_emp_no === this.user.emp_no) || this.isAdminGrp) {
          this.deleteModify = true
        }

        if (this.frmData.mtng_rslt_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.mtng_rslt_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, true)
            }
          })
        }

        this.frmData.ckFlag = false
        // this.frmData.ckFile = false
        this.$set(this.frmData, 'ckFile', false)
        this.tempData = JSON.parse(JSON.stringify(this.frmData))

        // 에디터 포커스 제거
        this.$nextTick(function () {
          this.$refs.quill.quill.enable(true)
          this.$refs.quill.quill.blur()
        })
      })
    },
    add() {
      this.frmData.ckFlag = true
      gridView.commit()
      dataProvider.addRow({
        mtng_sbjc_sn: '',
        mtng_sbjc_cl_cd: '',
        mtng_sbjc_txt: '',
        mtng_sbjc_rslt_txt: '',
      })
    },
    del() {
      this.frmData.ckFlag = true
      gridView.commit()
      dataProvider.setOptions({ softDeleting: true })
      const checkedRows = gridView.getCheckedRows()
      dataProvider.hideRows(checkedRows)
      dataProvider.removeRows(checkedRows)
    },
    add2() {
      this.frmData.ckFlag = true
      gridView2.commit()
      dataProvider2.addRow({
        mtng_atnd_sn: '',
        mtng_atnd_cl_cd: '',
        mtng_atnc_emp_id: '',
        mtng_atnd_nm: '',
        mtng_atnd_pstn_nm: '',
        mtng_atnd_dept_nm: '',
        mtng_atnd_tphn: '',
        mtng_atnd_emad: '',
      })
    },
    del2() {
      this.frmData.ckFlag = true
      gridView2.commit()
      dataProvider2.setOptions({ softDeleting: true })
      const checkedRows = gridView2.getCheckedRows()
      dataProvider2.hideRows(checkedRows)
      dataProvider2.removeRows(checkedRows)
    },
    async formDown() {
      // 자료실 doc id로 파일 id 조회
      this.schData.bbs_doc_no = '69' // doc id
      await this.$axios.get('/api/v1/common/arch-file', { params: this.schData }).then((res) => {
        // eslint-disable-next-line no-console
        if (!this.isEmpty(res.data)) {
          // 조회해온 file ID로 팝업 띄우기
          this.schData.atfile_no = res.data
          this.$refs.filepop.open(this.schData)
        } else {
          this.$toast.show(this.$t('MS00000347'), { className: 'toast_error' }) // 첨부파일이 존재하지 않습니다.
        }
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    save() {
      gridView.commit()
      gridView2.commit()
      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          this.update()
        }
      } else {
        this.insert()
      }
    },
    async insert() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      await this.saveFiles(true)

      this.frmData.mtng_rslt_prst_cd = '10' // 기본값 '작성중'

      this.$axios.post('/api/v1/she/sfty/mtng', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.mtng_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async update() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.ckFile) {
        await this.saveFiles(true)
      }

      await this.$axios.put('/api/v1/she/sfty/mtng', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.mtng_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      gridView.commit()
      gridView2.commit()

      // 기본정보
      if (!this.frmData.wkpl_id) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        this.$refs.wkpl_id.focus()
        return false
      }
      if (!this.frmData.mtng_cl_cd) {
        this.$toast.show(this.$t('MS00000324'), { className: 'toast_error' }) // 회의구분을 선택해주세요.
        this.$refs.mtng_cl_cd.focus()
        return false
      }
      if (!this.frmData.mtng_cyc_cd) {
        this.$toast.show(this.$t('MS00000325'), { className: 'toast_error' }) // 정기/임시구분을 선택해주세요.
        this.$refs.mtng_cyc_cd.focus()
        return false
      }
      if (!this.frmData.mtng_ttl) {
        this.$toast.show(this.$t('MS00000326'), { className: 'toast_error' }) // 회의명을 입력해주세요.
        this.$refs.mtng_ttl.focus()
        return false
      }
      if (this.frmData.mtng_ttl.length > 200) {
        this.$toast.show(this.$t('MS00000327'), { className: 'toast_error' }) // 회의명을 200자 이하로 입력해주세요.
        this.$refs.mtng_ttl.focus()
        return false
      }
      if (!this.frmData.mtng_pl_nm) {
        this.$toast.show(this.$t('MS00000328'), { className: 'toast_error' }) // 회의장소를 입력해주세요.
        this.$refs.mtng_pl_nm.focus()
        return false
      }
      if (this.frmData.mtng_pl_nm.length > 200) {
        this.$toast.show(this.$t('MS00000329'), { className: 'toast_error' }) // 회의장소를 200자 이하로 입력해주세요.
        this.$refs.mtng_pl_nm.focus()
        return false
      }
      if (
        this.isEmpty(this.frmData.mtng_bgn_hm_h) ||
        this.isEmpty(this.frmData.mtng_bgn_hm_s) ||
        this.isEmpty(this.frmData.mtng_end_hm_h) ||
        this.isEmpty(this.frmData.mtng_end_hm_s)
      ) {
        this.$toast.show(this.$t('MS00000330'), { className: 'toast_error' }) // 회의시간을 입력해주세요.
        this.$refs.mtng_bgn_hm_h.focus()
        return false
      }
      if (this.hourChg() === true) {
        this.$toast.show(this.$t('MS00000671'), { className: 'toast_error' }) // 작업종료 시간을 작업시작 시간전으로 설정할 수 없습니다.
        this.$refs.mtng_bgn_hm_h.focus()
        return false
      }

      // 회의 안건
      this.frmData.sbjc_list = []
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider.getValue(i, 'mtng_sbjc_txt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'mtng_sbjc_txt' })
          this.$toast.show(this.$t('MS00000331'), { className: 'toast_error' }) // 의제를 선택해주세요.
          return false
        }
        if (dataProvider.getValue(i, 'mtng_sbjc_txt').length > 50) {
          gridView.setCurrent({ dataRow: i, fieldName: 'mtng_sbjc_txt' })
          this.$toast.show(this.$t('MS00000332'), { className: 'toast_error' }) // 의제는 50자를 초과할 수 없습니다.
          return false
        }
        if (this.isEmpty(dataProvider.getValue(i, 'mtng_sbjc_rslt_txt'))) {
          gridView.setCurrent({ dataRow: i, fieldName: 'mtng_sbjc_rslt_txt' })
          this.$toast.show(this.$t('MS00000333'), { className: 'toast_error' }) // 결과를 입력해주세요.
          return false
        }
        if (!this.isEmpty(dataProvider.getValue(i, 'mtng_sbjc_rslt_txt')) && dataProvider.getValue(i, 'mtng_sbjc_rslt_txt').length > 150) {
          gridView.setCurrent({ dataRow: i, fieldName: 'mtng_sbjc_rslt_txt' })
          this.$toast.show(this.$t('MS00000334'), { className: 'toast_error' }) // 결과는 150자를 초과할 수 없습니다.
          return false
        }
        const sbjc = {}
        sbjc.mtng_sbjc_cl_cd = dataProvider.getValue(i, 'mtng_sbjc_cl_cd')
        sbjc.mtng_sbjc_txt = dataProvider.getValue(i, 'mtng_sbjc_txt')
        sbjc.mtng_sbjc_rslt_txt = dataProvider.getValue(i, 'mtng_sbjc_rslt_txt')
        this.frmData.sbjc_list.push(sbjc)
      }

      // 회의 참석자
      this.frmData.atnd_list = []
      for (let i = 0; i < dataProvider2.getRowCount(); i++) {
        // 삭제된 행 또는 신규추가했다가 삭제한 행은 유효성체크 제외
        if (dataProvider2.getRowState(i) === 'createAndDeleted' || dataProvider2.getRowState(i) === 'deleted') {
          continue
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_cl_cd'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_cl_cd' })
          this.$toast.show(this.$t('MS00000335'), { className: 'toast_error' }) // 구분을 선택해주세요.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_nm'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_nm' })
          this.$toast.show(this.$t('MS00000336'), { className: 'toast_error' }) // 성명을 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'mtng_atnd_nm').length > 50) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_nm' })
          this.$toast.show(this.$t('MS00000337'), { className: 'toast_error' }) // 성명은 50자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_pstn_nm'))) {
        //   gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_pstn_nm' })
        //   this.$toast.show(this.$t('MS00000342'), { className: 'toast_error' }) // 직책을 입력해주세요.
        //   return false
        // }
        if (!this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_pstn_nm')) && dataProvider2.getValue(i, 'mtng_atnd_pstn_nm').length > 50) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_pstn_nm' })
          this.$toast.show(this.$t('MS00000338'), { className: 'toast_error' }) // 직책은 50자를 초과할 수 없습니다.
          return false
        }
        if (this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_dept_nm'))) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_dept_nm' })
          this.$toast.show(this.$t('MS00000339'), { className: 'toast_error' }) // 회사를 입력해주세요.
          return false
        }
        if (dataProvider2.getValue(i, 'mtng_atnd_dept_nm').length > 50) {
          gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_dept_nm' })
          this.$toast.show(this.$t('MS00000340'), { className: 'toast_error' }) // 회사는 50자를 초과할 수 없습니다.
          return false
        }
        // if (this.isEmpty(dataProvider2.getValue(i, 'mtng_atnd_emad'))) {
        //   gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_emad' })
        //   this.$toast.show(this.$t('MS00000577'), { className: 'toast_error' }) // 이메일 주소를 입력해주세요.
        //   return false
        // }
        // if (dataProvider2.getValue(i, 'mtng_atnd_emad').length > 50) {
        //   gridView2.setCurrent({ dataRow: i, fieldName: 'mtng_atnd_emad' })
        //   this.$toast.show(this.$t('MS00000387'), { className: 'toast_error' }) // 이메일 주소는 50자를 초과할 수 없습니다.
        //   return false
        // }
        const atnd = {}
        atnd.mtng_atnd_cl_cd = dataProvider2.getValue(i, 'mtng_atnd_cl_cd')
        atnd.mtng_atnc_emp_id = dataProvider2.getValue(i, 'mtng_atnc_emp_id')
        atnd.mtng_atnd_nm = dataProvider2.getValue(i, 'mtng_atnd_nm')
        atnd.mtng_atnd_pstn_nm = dataProvider2.getValue(i, 'mtng_atnd_pstn_nm')
        atnd.mtng_atnd_dept_nm = dataProvider2.getValue(i, 'mtng_atnd_dept_nm')
        atnd.mtng_atnd_tphn = dataProvider2.getValue(i, 'mtng_atnd_tphn')
        atnd.mtng_atnd_emad = dataProvider2.getValue(i, 'mtng_atnd_emad')
        this.frmData.atnd_list.push(atnd)
      }
      return true
    },
    async delInfo() {
      gridView.commit()
      gridView2.commit()

      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.put(`/api/v1/she/sfty/mtng/${this.frmData.mtng_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async appr() {
      gridView.commit()
      gridView2.commit()

      if (!this.validate()) {
        return
      }

      // 회의 안건
      if (dataProvider.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000322'), { className: 'toast_error' }) // 회의 안건을 등록해주세요.
        return false
      }

      // 회의 참석자
      if (dataProvider2.getRowCount() === 0) {
        this.$toast.show(this.$t('MS00000341'), { className: 'toast_error' }) // 회의 참석자를 등록해주세요.
        return false
      }

      const res2 = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
      if (!res2) {
        return
      }

      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          // this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          if (this.frmData.ckFile) {
            await this.saveFiles(true)
          }

          await this.$axios.put('/api/v1/she/sfty/mtng', this.frmData).then((res) => {
            if (res.data !== 'Fail') {
              this.frmData.mtng_id = res.data
              this.schData.mtng_id = res.data
              this.select()
            } else {
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
            }
          })
        }
      } else {
        await this.saveFiles(true)

        this.frmData.mtng_rslt_prst_cd = '10' // 기본값 '작성중'

        await this.$axios.post('/api/v1/she/sfty/mtng', this.frmData).then((res) => {
          if (res.data !== 'Fail') {
            this.frmData.mtng_id = res.data
            this.schData.mtng_id = res.data
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }

      this.modify = false

      // this.frmData.mtng_rslt_prst_cd = '1' // 결재상신
      this.frmData.type = 'appr'

      this.$axios.put('/api/v1/she/sfty/mtng', this.frmData).then((res) => {
        const config = require('../../../../nuxt.config.js')
        console.log(res) // eslint-disable-line no-console
        if (res.data.Success === 'Success') {
          const popupWidth = 900
          const popupHeight = window.screen.height - 300
          const popupX = window.screen.width / 2 - popupWidth / 2
          const popupY = window.screen.height / 2 - popupHeight / 2
          const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
          const aprvno = '&dataurl=' + res.data.aprvNo
          const empno = '&typ=' + res.data.empNo
          const url = config.default.approvalUrl9 + aprvno + empno
          window.open(url, 'mtng', options)
          this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
          this.$router.push({
            name: `mtng-mtngmgntdtllup-mtngmgntdtllup`,
            params: {
              mtngmgntdtllup: this.schData.mtng_id,
              schData: this.schData,
            },
          })
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    popclose(returnData) {
      if (returnData && returnData.length > 0) {
        const row = gridView2.getCurrent().dataRow
        const values = {
          mtng_atnc_emp_id: returnData[0].emp_no,
          mtng_atnd_nm: returnData[0].emp_nm,
          mtng_atnd_pstn_nm: returnData[0].jkw_ko_nm,
          mtng_atnd_dept_nm: returnData[0].cmpy_nm,
          mtng_atnd_tphn: returnData[0].emp_cpho_no,
          mtng_atnd_emad: returnData[0].emp_email,
        }
        dataProvider2.updateRow(row, values)
      }
    },
    codChgReturn(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
    },
    goList() {
      this.$router.push({
        name: `mtng-mtngmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
    async saveFiles(mode) {
      await this.$refs.upload.uploadStart(true).then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data) {
          this.frmData.mtng_rslt_atfl_no = data
          // this.$axios.$get(`/api/v1/common/file/${this.frmData.mtng_rslt_atfl_no}`).then((data) => {
          //   console.log('files : ', data) // eslint-disable-line no-console
          //   if (data.length > 0) {
          //     this.setFiles(data, mode)
          //   }
          // })
        }
      })
    },
  },
}
</script>
<style></style>
