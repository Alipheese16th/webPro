<template>
  <v-dialog v-model="dialog" persistent width="1000px" :eager="true">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-3">
        <!-- 첨부파일 -->
        <v-toolbar-title>교육 이수증 관리</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <v-col cols="12" style="padding: 0 30px 30px 30px; color: blue">
        - 교육 만료일 이후 교육을 이수한 경우, '법규 미준수' 건으로 등록 후에도 '미등록‘ 표시 됩니다.
        <br />
        <span style="margin-left: 10px"></span>* '미등록' 건 변경이 필요한 경우, Helpdesk 시스템의 '데이터 수정 서비스' 통해 변경 요청 바랍니다.
        <br />
        <span style="margin-left: 20px"></span>(데이터 수정 서비스 등록시 참조인에 안전보건전략팀 이태현 포함 바랍니다.)
      </v-col>
      <div class="container_detail">
        <div class="sub_title">
          <!-- 기본정보-->
          <h3>기본정보</h3>
        </div>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="2">
                <!--이름-->
                <div class="label_tit">{{ $t('LB00000072') }}</div>
              </v-col>
              <v-col cols="2" style="background-color: #ffffff">
                <div class="label_con">{{ schData.elc_pric_nm }}</div>
              </v-col>
              <v-col cols="2">
                <!--선임명-->
                <div class="label_tit">선임명</div>
              </v-col>
              <v-col cols="6" style="background-color: #ffffff">
                <div class="label_con">{{ schData.elc_cl_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="2">
                <!--최종 교육 이수일-->
                <div class="label_tit">최종 교육 이수일</div>
              </v-col>
              <v-col cols="2" style="background-color: #ffffff">
                <div class="label_con">{{ schData.trn_fcst_dt }}</div>
              </v-col>
              <v-col cols="2">
                <!--첨부파일-->
                <div class="label_tit essn">{{ $t('LB00000121') }}</div>
              </v-col>
              <v-col cols="6">
                <file-upload ref="popUpload" :input-id="mkey1" :multiple="false" @onDrop="chkFilePop()" />
              </v-col>
            </v-row>
          </v-container>
          <div class="group_padd"></div>
        </v-col>
        <div class="group_padd"></div>
        <div class="realgrid_container" style="width: 970px; padding-left: 0px">
          <div class="sub_title">
            <!--등록 이력-->
            <h3>등록 이력</h3>
          </div>
          <div class="grid_header">
            <!--총 건-->
            <div class="grid_header_left">
              <span>{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</span>
            </div>
            <span style="line-height: 30px; color: blue; padding-top: 6px">*교육이수일자 변경이 필요한 경우, 소속 사업장 안전부서로 요청 바랍니다.</span>
          </div>
          <div id="realgrid3" style="width: 940px; height: 200px"></div>
        </div>
        <v-card-actions class="pop_btn">
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
          <!-- 닫기 -->
          <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
        </v-card-actions>
      </div>
    </v-card>
  </v-dialog>
</template>
<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import FileUpload from '~/components/FileUpload.vue'

// 그리드 1
let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  components: { FileUpload },
  props: {
    mkey1: {
      type: [String],
      default() {
        return 'atflgrid'
      },
    },
    ispub: {
      type: [Boolean],
      default() {
        return false
      },
    },
  },
  data() {
    return {
      menu1: false,
      dialog: false,
      filemodify: false,
      returnData: '',
      trn_fcst_dt: '',
      totalcnt: '',
      frmData: {},
      schData: {},
      saveData: {},
      searchdatas: {},
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    // 그리드 세팅
    const fields = [
      {
        fieldName: 'elc_rspn_emp_id',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'elc_cl_cd',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_sn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_dt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_atfl_no',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_fcst_atfl_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'delete_field',
        dataType: ValueType.TEXT,
      },
    ]
    const columns = [
      {
        header: '교육이수일자', // 교육이수일자
        name: 'trn_fcst_dt',
        fieldName: 'trn_fcst_dt',
        width: '420',
      },
      {
        header: '교육이수증', // 교육이수증
        name: 'trn_fcst_atfl_nm',
        fieldName: 'trn_fcst_atfl_nm',
        width: '420',
        styleCallback(grid, dataCell) {
          const ret = {}
          const atfileno = grid.getValue(dataCell.index.itemIndex, 'trn_fcst_atfl_no')
          if (typeof atfileno !== 'undefined' && atfileno !== null && atfileno !== '' && atfileno !== 0) {
            ret.styleName = 'link left'
          }
          return ret
        },
      },
      {
        header: '삭제', // 삭제
        name: 'delete_field',
        fieldName: 'delete_field',
        width: '60',
        renderer: {
          type: 'icon',
          iconCallback(grid, cell) {
            return 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAABHNCSVQICAgIfAhkiAAAANZJREFUOE+t1U0OgjAQBeD3AjtvpCt+3BoXbjyFZ/EUJoaVWwOs9FZkTE1qSm1pobCE5MvMvE5hXdf3YRiufd+/kPAURbHLsuzCqqr2AG4icui67r3ELMtyS/IB4EwFpKAm1rbt8wsuRW1MOT9wLurC/sBY1Ic5wRCq560CUDOzQxy1bH50BaXekWxE5OTCvBW6giK5CWFB0Gi/AZADOPoq00V4W7aqbEjmIpIGmnNMbnnVUKZWcfaxidnr6IMdg+mwgqs3B5tC17++llRm7+/ogl37F/ABUgfuHQjDo70AAAAASUVORK5CYII='
          },
          iconWidth: 10,
          iconHeight: 10,
        },
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid3')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.fitStyle = 'fill'
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.header.height = 39
    gridView.footer.height = 40
    gridView.setColumnProperty('id', 'visible', false)

    gridView.onCellClicked = async function (grid, clickData) {
      console.log('=> grid.onCellClicked clickData: ' + JSON.stringify(clickData)) // eslint-disable-line no-console
      if (clickData.cellType !== 'data') return
      if (clickData.itemIndex < 0) return

      if (clickData.column === 'trn_fcst_atfl_nm') {
        // 첨부파일 클릭 시
        const fileNo = dataProvider.getValue(clickData.dataRow, 'trn_fcst_atfl_no')
        const fileNm = grid.getValue(clickData.itemIndex, 'trn_fcst_atfl_nm')
        this.fileDownload(fileNo, fileNm)
      }
      if (clickData.column === 'delete_field') {
        const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
        if (!res) {
          return
        }
        const ElcRspnEmpId = dataProvider.getValue(clickData.dataRow, 'elc_rspn_emp_id')
        const elcClCd = dataProvider.getValue(clickData.dataRow, 'elc_cl_cd')
        const trnFcstSn = dataProvider.getValue(clickData.dataRow, 'trn_fcst_sn')
        const trnFcstDt = dataProvider.getValue(clickData.dataRow, 'trn_fcst_dt')
        const wkplId = this.schData.wkpl_id
        const postData = {
          elc_rspn_emp_id: ElcRspnEmpId,
          elc_cl_cd: elcClCd,
          trn_fcst_sn: trnFcstSn,
          wkpl_id: wkplId,
          trn_fcst_dt: trnFcstDt,
        }
        await this.$axios.put(`/api/v1/st/stndinfr/wkpl-trn-pop`, postData).then((res) => {
          if (res.data.result === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    }.bind(this)
  },
  methods: {
    async select() {
      const rows = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-trn-pop`, { params: this.schData })
      console.log(rows) // eslint-disable-line no-console
      this.frmData = rows.trnlist
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = dataProvider.getRowCount()
    },
    chkFilePop() {
      this.fileModify = true
    },
    open(frmData) {
      console.log(frmData) // eslint-disable-line no-console
      this.dialog = true

      this.schData.trn_fcst_dt = frmData.fst_trn_fcst_dt
      if (!this.isEmpty(frmData.rglr_trn_fcst_dt)) {
        this.schData.trn_fcst_dt = frmData.rglr_trn_fcst_dt
      }
      if (frmData.elc_cl_cd === 'ELC28') {
        this.schData.elc_cl_nm = frmData.elc_cl_nm.concat(' / ' + frmData.elc_cl_oth_txt)
      } else {
        this.schData.elc_cl_nm = frmData.elc_cl_nm
      }
      this.schData.elc_cl_cd = frmData.elc_cl_cd
      this.schData.elc_pric_nm = frmData.elc_pric_nm
      this.schData.elc_rspn_emp_id = frmData.elc_rspn_emp_id
      this.schData.wkpl_id = frmData.wkpl_id
      console.log(this.schData) // eslint-disable-line no-console
      this.select()
    },
    changeEditMode(mode) {
      this.$refs.popUpload.setEdit(mode)
    },
    setFiles(files) {
      this.$nextTick(() => {
        this.$refs.popUpload.setFiles(files)
        this.$refs.popUpload.setEdit(false)
      })
    },
    fileDownload(key, fileName) {
      if (this.isEmpty(key) || this.isEmpty(fileName)) return
      console.log('download...', key) // eslint-disable-line no-console
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
    close() {
      const rtnVal = []
      rtnVal.trn_fcst_atfl_no = this.saveData.trn_fcst_atfl_no
      rtnVal.file_list = this.rtnData
      if (!this.isEmpty(this.saveData.trn_fcst_atfl_no)) {
        rtnVal.file_cnt = this.$refs.popUpload.files.length
      } else {
        rtnVal.file_cnt = 0
      }
      this.reset()
      this.$emit('close', rtnVal)
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.$refs.popUpload.reset()
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    isEmptyFile(str) {
      if (typeof str === 'undefined' || str == null || str === '' || str === 0) return true
      else return false
    },
    async validationChk() {
      const chk = await this.$refs.popUpload.uploadStart().then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (!this.isEmpty(data)) {
          this.saveData.trn_fcst_atfl_no = data
          this.saveData.wkpl_id = this.schData.wkpl_id
          this.saveData.elc_rspn_emp_id = this.schData.elc_rspn_emp_id
          this.saveData.elc_cl_cd = this.schData.elc_cl_cd
          this.saveData.trn_fcst_dt = this.schData.trn_fcst_dt.replaceAll('-', '')
          console.log(this.saveData) // eslint-disable-line no-console
          return true
        } else {
          return false
        }
      })
      return chk
    },
    async save() {
      if (this.$refs.popUpload.files.length > 0) {
        const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
        if (!res) {
          return
        }
      } else {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return
      }

      if (!this.isEmpty(this.saveData.trn_fcst_atfl_no)) {
        this.$axios.$get(`/api/v1/common/file/${this.saveData.trn_fcst_atfl_no}`).then((data) => {
          console.log('files1 : ', data) // eslint-disable-line no-console
          if (data.length > 0) {
            this.setFiles1(data)
          }
        })
      }
      if (await this.validationChk()) {
        console.log(this.schData) // eslint-disable-line no-console
        await this.$axios.put(`/api/v1/st/stndinfr/wkpl-trn-pop-save`, this.saveData).then((res) => {
          console.log(res) // eslint-disable-line no-console
          if (res.data.result === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
            this.select()
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
        })
      }
    },
  },
}
</script>

<style scoped>
.rg-data-row .btn_pop_cls {
  background-size: 15px 15px;
}
</style>
