<template>
  <div>
    <div class="location">
      <!-- Home > 교육 및 훈련 > 교육/훈련 결과 관리 -->
      <span>Home > {{ $t('LB00001046') }} > {{ $t('LB00001067') }}</span>
    </div>
    <div class="work_title">
      <!-- 교육/훈련 결과 관리 -->
      <h2>{{ $t('LB00001067') }}</h2>
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
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.wkpl_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--구분-->
                <div class="label_tit">{{ $t('LB00000741') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.trn_cl_nm }}</div>
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
                <!--교육/훈련명-->
                <div class="label_tit">{{ $t('LB00001066') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <div class="label_con">{{ frmData.trn_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--강사명-->
                <div class="label_tit">{{ $t('LB00001064') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.trn_techer_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--일시-->
                <div class="label_tit">{{ $t('LB00001063') }}</div>
              </v-col>
              <v-col cols="7" style="display: flex">
                <div class="label_con">{{ frmData.trn_bgn_dt }} {{ frmData.trn_bgn_hm }} ~ {{ frmData.trn_end_dt }} {{ frmData.trn_end_hm }}</div>
              </v-col>
              <v-col cols="1">
                <!--시간-->
                <div class="label_tit">{{ $t('LB00000249') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.trn_time_txt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="trn_note_txt" v-model="frmData.trn_note_txt" outlined single-line :hide-details="true" height="60" class="mt-2" disabled dense></v-textarea>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--이수실적 (이수자/대상자)-->
                <div class="label_tit">{{ $t('LB00001062') }}</div>
              </v-col>
              <v-col cols="11" style="display: flex">
                <div class="label_con">{{ frmData.trn_fcst_prsn_cnt }}</div>
                /
                <div class="label_con">{{ frmData.trn_tgt_prsn_cnt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">내용</div>
              </v-col>
              <v-col cols="11">
                <v-tabs slider-size="3" color="#062C5E">
                  <v-tab :style="styleObject">html</v-tab>
                  <v-tab :style="styleObject">소스</v-tab>
                  <v-tab-item :eager="true">
                    <v-card flat>
                      <br />
                      <!-- html -->
                      <div class="label_con">
                        <!-- eslint-disable-next-line vue/no-v-html -->
                        <div style="overflow: auto" v-html="tmpSchData.strHtml"></div>
                      </div>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item :eager="true">
                    <v-card flat>
                      <br />
                      <!-- 소스 -->
                      <div class="label_con">
                        <p>{{ tmpSchData.strHtml }}</p>
                      </div>
                    </v-card>
                  </v-tab-item>
                </v-tabs>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!-- 대상자 -->
        <h3>{{ $t('LB00001061') }}</h3>
      </div>
      <div class="grid_header">
        <!--실적-->
        <div class="grid_header_left">{{ $t('LB00001060') }} : {{ frmData.trn_fcst_prsn_cnt }} / {{ frmData.trn_tgt_prsn_cnt }} ({{ per_val }}%)</div>
        <div>
          <!--미이수자 다운로드-->
          <!-- <v-btn color="" outlined height="30" @click="hdselect()">미이수자 다운로드</v-btn> -->
          <!--양식 다운로드-->
          <v-btn color="" outlined height="30" @click="excelTemp()">{{ $t('LB00000721') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <div id="hdrealgrid" style="width: 100%; height: 481px; display: none"></div>
      <div id="exceltemp" style="width: 100%; height: 481px; display: none"></div>
    </div>
    <div class="realgrid_container">
      <div class="group_padd"></div>
      <!-- 첨부파일 목록 -->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist" style="max-height: 100px">
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
          <!--메일발송-->
          <!-- <v-btn depressed width="120" color="primary" height="40" @click="sendMail()">메일발송</v-btn> -->
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider
let tempDataProvider = LocalDataProvider

// 그리드 2
let gridView2 = GridView
let dataProvider2 = LocalDataProvider

// 그리드 3
let gridView3 = GridView
let dataProvider3 = LocalDataProvider

export default {
  meta: {
    title: '교육훈련 결과 관리',
    key(route) {
      return `/trnmgnt/trnrsltmgntlist/${route.params.catalog}`
    },
  },
  components: {
    FileUpload,
  },
  data() {
    return {
      files: [],
      quillChk: false,
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      totalcnt: 0,
      tmpSchData: {},
      schData: {},
      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      trnId: '',
      per_val: 0,
      frmData: {
        ckFlag: false,
        trn_id: '',
        wkpl_id: '',
        wkpl_nm: '',
        trn_cl_cd: '',
        trn_cl_nm: '',
        trn_nm: '',
        trn_dt: '',
        trn_bgn_dt: '',
        trn_end_dt: '',
        trn_bgn_hm: '',
        trn_end_hm: '',
        trn_time_txt: '',
        trn_note_txt: '',
        trn_prst_cd: '',
        trn_aprv_no: '',
        upt_emp_no: '',
        lsch_nm: '',
        trn_tgt_prsn_cnt: 0,
        trn_fcst_prsn_cnt: 0,
        lsch_dt: '',
        trn_atfl_no: 0,
        trn_techer_nm: '',
      },
      // 대상자정보
      userData: {
        chk_gb: '',
        trn_id: '',
        trn_tgt_emp_id: '',
        trn_tgt_emp_nm: '',
        wkpl_id: '',
        trn_fcst_cl_cd: '',
        trn_fcst_cl_nm: '',
        trn_fcst_yn: '',
        unit_nm: '',
        div_nm: '',
        team_nm: '',
      },
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00001046') // 교육 및 훈련
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    if (this.$route.params.trnrsltmgntdtllup !== null && this.$route.params.trnrsltmgntdtllup !== undefined) {
      this.frmData.trn_id = this.$route.params.trnrsltmgntdtllup
      this.trnId = this.$route.params.trnrsltmgntdtllup
      this.schData.trn_id = this.$route.params.trnrsltmgntdtllup
    } else {
      this.frmData.trn_id = '1'
      this.trnId = ''
      this.schData.trn_id = ''
      this.frmData.trn_prst_cd = 'S01'
    }
    // 이전 검색 결과 저장
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.tmpSchData = this.$route.params.tmpSchData
    } else {
      this.tmpSchData = {
        cmpy_cd: '',
        wkpl_id: '',
        trn_cl_cd: '',
        usg_yn: '',
        dept_cd: '',
        dept_nm: '',
        sDate: '',
        eDate: '',
      }
    }
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'chk_gb',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_tgt_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_tgt_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'unit_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'div_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'team_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_yn',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'trn_tgt_emp_id',
        fieldName: 'trn_tgt_emp_id',
        width: '100',
      },
      {
        header: this.$t('LB00000701'), // 성명
        name: 'trn_tgt_emp_nm',
        fieldName: 'trn_tgt_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001071'), // Unit
        name: 'unit_nm',
        fieldName: 'unit_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001070'), // Div
        name: 'div_nm',
        fieldName: 'div_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001036'), // 팀명
        name: 'team_nm',
        fieldName: 'team_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001069'), // 이수구분
        name: 'trn_fcst_cl_nm',
        fieldName: 'trn_fcst_cl_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001068'), // 이수여부
        name: 'trn_fcst_yn',
        fieldName: 'trn_fcst_yn',
        width: '200',
      },
    ]
    // 그리드 1 시작
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    tempDataProvider = new LocalDataProvider(false)
    tempDataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.displayOptions.selectionStyle = 'block'
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
    // 그리드 1 끝

    // 그리드 2 시작
    dataProvider2 = new LocalDataProvider(false)
    dataProvider2.setFields(fields)
    gridView2 = new GridView('hdrealgrid')
    gridView2.displayOptions.selectionStyle = 'block'
    gridView2.setDataSource(dataProvider2)
    gridView2.setColumns(columns)
    gridView2.setFooters({ visible: false })
    gridView2.setStateBar({ visible: false })
    gridView2.setCheckBar({ visible: false })
    gridView2.editOptions.editable = true
    gridView2.displayOptions.selectionStyle = 'block'

    gridView2.header.height = 39
    gridView2.displayOptions.rowHeight = 40
    gridView2.footer.height = 40
    gridView2.displayOptions.fitStyle = 'fill'
    // 그리드 2 끝
    // 그리드 세팅
    const excelfields = [
      {
        fieldName: 'chk_gb',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_tgt_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_tgt_emp_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'wkpl_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_cl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_yn',
        dataType: ValueType.TEXT,
      },
    ]
    const excelcolumns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'trn_tgt_emp_id',
        fieldName: 'trn_tgt_emp_id',
        width: '100',
      },
      {
        header: this.$t('LB00000701'), // 성명
        name: 'trn_tgt_emp_nm',
        fieldName: 'trn_tgt_emp_nm',
        width: '100',
      },
      {
        header: this.$t('LB00001069'), // 이수구분
        name: 'trn_fcst_cl_nm',
        fieldName: 'trn_fcst_cl_nm',
        width: '200',
        styleName: 'left',
      },
      {
        header: this.$t('LB00001068'), // 이수여부
        name: 'trn_fcst_yn',
        fieldName: 'trn_fcst_yn',
        width: '200',
      },
    ]
    // 그리드 3 시작
    dataProvider3 = new LocalDataProvider(false)
    dataProvider3.setFields(excelfields)
    gridView3 = new GridView('exceltemp')
    gridView3.displayOptions.selectionStyle = 'block'
    gridView3.setDataSource(dataProvider3)
    gridView3.setColumns(excelcolumns)
    gridView3.setFooters({ visible: false })
    gridView3.setStateBar({ visible: false })
    gridView3.setCheckBar({ visible: false })
    gridView3.editOptions.editable = false
    gridView3.displayOptions.selectionStyle = 'block'

    gridView3.header.height = 39
    gridView3.displayOptions.rowHeight = 40
    gridView3.footer.height = 40
    gridView3.displayOptions.fitStyle = 'fill'
    // 그리드 3 끝
    if (this.frmData.trn_id !== '') {
      this.select()
    }
  },
  methods: {
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
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/trnmgnt/trn-dtl/${this.schData.trn_id}`)
      this.$refs.upload.setEdit(false)
      if (data.basic.length > 0) {
        this.frmData = data.basic[0]
        const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
        this.per_val = perVal

        if (!this.isEmpty(this.frmData.trn_atfl_no)) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.trn_atfl_no}`).then((data) => {
            console.log('files1 : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data)
            }
          })
        }
      } else {
        this.frmData = []
      }

      if (data.user.length > 0) {
        this.userData = data.user
        dataProvider.setRows(data.user)
        tempDataProvider.setRows(data.user)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = dataProvider.getRowCount()
        this.editPerCalc()
      }
    },
    async hdselect() {
      gridView.commit()
      gridView2.commit()

      const data = await this.$axios.$get(`/api/v1/she/trnmgnt/trn-dtl-excel`, { params: this.schData })

      if (data.user.length > 0) {
        dataProvider2.setRows(data.user)
        gridView2.refresh()
        gridView2.setTopItem(0)

        this.excelExport()
      }
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(false)
        this.$refs.upload.setDelete(false)
        this.changeEditMode(false)
      })
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    perCalc() {
      this.frmData.trn_tgt_prsn_cnt = gridView.getItemCount()
      let yCnt = 0
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        if (dataProvider.getRowState(i) === 'createAndDeleted' || dataProvider.getRowState(i) === 'deleted') {
          continue
        }
        if (dataProvider.getValue(i, 'trn_fcst_yn') === 'Y') {
          yCnt++
        }
      }
      this.frmData.trn_fcst_prsn_cnt = yCnt
      const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
      this.per_val = perVal
    },
    editPerCalc() {
      const perVal = (this.frmData.trn_fcst_prsn_cnt / this.frmData.trn_tgt_prsn_cnt) * 100
      this.per_val = perVal
    },
    goList() {
      this.$router.push({
        name: `trnmgnt-trnrsltmgntlist`,
        params: {
          schData: {
            cmpy_cd: this.tmpSchData.cmpy_cd,
            wkpl_id: this.tmpSchData.wkpl_id,
            trn_cl_cd: this.tmpSchData.trn_cl_cd,
            usg_yn: this.tmpSchData.usg_yn,
            dept_cd: this.tmpSchData.dept_cd,
            dept_nm: this.tmpSchData.dept_nm,
            sDate: this.tmpSchData.sDate,
            eDate: this.tmpSchData.eDate,
          },
        },
      })
    },
    excelExport() {
      const excelType = false
      const showProgress = false
      const indicator = 'visible'
      const header = 'visible'
      const footer = 'hidden'
      const fileName = this.$t('LB00001073') + '_' + new Date().toISOString().substr(0, 10) + '.xlsx' // 미이수자

      gridView2.exportGrid({
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
  },
}
</script>
<style></style>
