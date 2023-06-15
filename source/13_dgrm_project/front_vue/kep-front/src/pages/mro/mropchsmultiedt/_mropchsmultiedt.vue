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
                <v-select
                  ref="wkpl_id"
                  v-model="mroSaveData.wkpl_id"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000006"
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
        <div>
          <!--추가-->
          <v-btn color="" outlined height="30" @click="popupmtrl(false, 'Y')">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="30" @click="del()">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 400px"></div>
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
            <quill-editor v-model="mroSaveData.pchs_cntn" :options="editorOption" :disabled="quillChk" @change="onEditorChange" />
          </v-col>
        </v-row>
      </v-container>
    </div> -->
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--결재(KO)-->
      <v-btn v-if="mroSaveData.mro_pchs_no != ''" depressed width="120" color="primary" height="40" @click=";(mroSaveData.aprv_yn = 'Y'), save()">{{ $t('LB00000146') }}</v-btn>
      <!--결재담기-->
      <v-btn
        v-if="mroSaveData.mro_pchs_no != '' && mroSaveData.pchs_prog_stt_cd != '15'"
        depressed
        width="120"
        color="primary"
        height="40"
        @click=";(mroSaveData.aprv_yn = 'Y'), cart()"
        >{{ $t('LB00000608') }}</v-btn
      >
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click=";(mroSaveData.aprv_yn = 'N'), save()">{{ $t('LB00000045') }}</v-btn>
      <!--삭제-->
      <v-btn v-if="mroSaveData.mro_pchs_no != ''" depressed width="120" color="primary" height="40" @click="delsave()">{{ $t('LB00000046') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
    <popupmtrl ref="pop" mkey1="mropchsmultiedt_mtrl1" mkey2="mropchsmultiedt_mtrl2" @close="popclose" />
  </div>
</template>

<script>
// import { quillEditor } from 'vue-quill-editor'
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popupmtrl from '../../common/mtrlmultislct'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()

export default {
  meta: {
    title: 'MRO구매요청-다건',
    key(route) {
      return `/mro/mropchs/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    popupmtrl,
  },
  data() {
    return {
      quillChk: true,
      codeid: ['CO00000006'],
      codes: {
        CO00000006: [{ cd: '', cd_nm: '' }], // 사업장
      },
      totalcnt: 0,
      savenext: false, // 저장시 다음로직으로

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
        aprv_yn: 'N', // 결재여부
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
      tempData: {
        wkpl_id: '',
        pchs_cntn: '',
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
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (
      (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 ||
        this.tempData.wkpl_id !== this.mroSaveData.wkpl_id ||
        this.tempData.pchs_cntn !== this.mroSaveData.pchs_cntn) &&
      this.mroSaveData.aprv_yn !== 'FIN'
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
  },
  created() {
    this.usrinfo = this.$store.getters['auth/getUser']
    this.mroSaveData.upt_emp_nm = this.usrinfo.emp_nm
    this.mroSaveData.upt_dept_nm = this.usrinfo.dept_nm
    this.mroSaveData.upt_emp_no = this.usrinfo.emp_no
    this.mroSaveData.upt_dt = this.get_date_str_gubun(date, '-')
    this.mroSaveData.wkpl_id = this.usrinfo.wkpl_id

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
    // 파라미터 조회 - 파라미터가 존재할 경우
    if (this.$route.params.mropchsmultiedt !== null && this.$route.params.mropchsmultiedt !== undefined) {
      this.mroSaveData.mro_pchs_no = this.$route.params.mropchsmultiedt
      this.selectDtl(this.mroSaveData.mro_pchs_no)
    } else {
      this.mroSaveData.mro_pchs_no = ''
      this.onEditorChange()
      this.tempData.wkpl_id = this.mroSaveData.wkpl_id
      this.tempData.pchs_cntn = this.mroSaveData.pchs_cntn
    }
  },
  mounted() {
    this.$axios.$get(`/api/v1/comm/comm-cd`, { params: this.codeid }).then((res) => {
      this.codes.CO00000006 = res.CO00000006
      this.codes.CO00000006.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '', up_cd_nm: '' })
    })
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.displayOptions.selectionStyle = 'block'
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000160'), // 품목코드(KO)
        name: 'mtrl_no',
        fieldName: 'mtrl_no',
        width: '100',
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
        styleName: 'left',
      },
      {
        header: this.$t('LB00000157'), // 수입여부(KO)
        name: 'impt_yn',
        fieldName: 'impt_yn',
        width: '200',
      },
      {
        header: this.$t('LB00000155'), // 단위중량(KO)
        name: 'pchs_req_unit_weght',
        fieldName: 'pchs_req_unit_weght',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000155'), // 단위중량(KO)
        name: 'pchs_unit_weght',
        fieldName: 'pchs_unit_weght',
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
        header: this.$t('LB00000156'), // 주문단위(KO)
        name: 'pchs_unit_nm',
        fieldName: 'pchs_unit_nm',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000528'), // 단위중량(KO)
        name: 'pchs_req_unit_weght_nm',
        fieldName: 'pchs_req_unit_weght_nm',
        width: '100',
        styleName: 'right',
      },
      {
        header: this.$t('LB00000154'), // 주문수량(KO)
        name: 'pchs_qty',
        fieldName: 'pchs_qty',
        width: '100',
        editor: {
          type: 'number',
          maxLength: 10,
        },
        styleCallback(grid, dataCell) {
          const ret = {}
          // 구성물질 함량정보 스타일 적용
          if (dataCell.value === '' || dataCell.value === null || dataCell.value === undefined || dataCell.value === 0 || dataCell.value === '0') {
            ret.styleName = 'field-empty'
          } else {
            ret.styleName = 'right'
          }

          return ret
        },
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
    gridView.setCheckBar({ visible: true })
    gridView.editOptions.editable = true
    gridView.displayOptions.selectionStyle = 'block'
    gridView.editOptions.commitByCell = true

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.setColumnProperty('mtrl_no', 'editable', false)
    gridView.setColumnProperty('mtrl_nm', 'editable', false)
    gridView.setColumnProperty('vndr_nm', 'editable', false)
    gridView.setColumnProperty('impt_yn', 'editable', false)
    gridView.setColumnProperty('pchs_req_unit_nm', 'visible', false)
    gridView.setColumnProperty('pchs_req_unit_weght', 'visible', false)
    gridView.setColumnProperty('pchs_req_unit_weght_nm', 'editable', false)
    gridView.setColumnProperty('pchs_unit_weght', 'visible', false)
    gridView.setColumnProperty('pchs_unit_nm', 'visible', false)
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
    onEditorChange(editor) {
      this.quillChk = false
    },
    selectDtl(mropchsno) {
      this.$axios.$get(`/api/v1/ch/mro/mropchs-multi/${mropchsno}`).then((res) => {
        this.mroSaveData = res.mroSaveData
        this.tempData.wkpl_id = res.mroSaveData.wkpl_id
        this.tempData.pchs_cntn = res.mroSaveData.pchs_cntn

        dataProvider.setRows(res.mtrlSaveData)
        gridView.refresh()
        gridView.setTopItem(0)
        this.totalcnt = gridView.getItemCount()
        this.onEditorChange()
      })
    },
    popupmtrl(detail, chkYnAll) {
      this.$refs.pop.open(detail, chkYnAll)
    },
    async popclose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        await returnData.forEach((i) => {
          const fields = ['mtrl_no', 'vndr_no']
          const values = [i.mtrl_no, i.vndr_no]
          const options = {
            fields,
            values,
            allFields: true,
            startIndex: 0,
            wrap: true,
            caseSensitive: false,
            partialMatch: true,
          }
          const passyn1 = gridView.searchItem(options)
          if (passyn1 === -1) {
            dataProvider.addRow({
              mro_pchs_no: this.mroSaveData.mro_pchs_no,
              mtrl_no: i.mtrl_no,
              mtrl_nm: i.mtrl_nm,
              vndr_no: i.vndr_no,
              vndr_nm: i.vndr_nm,
              impt_yn: i.impt_yn,
              pchs_unit_cd: i.pchs_unit_cd,
              pchs_req_unit_cd: i.pchs_req_unit_cd,
              pchs_unit_nm: i.pchs_unit_nm,
              pchs_req_unit_nm: i.pchs_req_unit_nm,
              pchs_unit_weght: i.pchs_unit_weght,
              pchs_req_unit_weght: i.pchs_req_unit_weght,
              pchs_req_unit_weght_nm: i.pchs_req_unit_weght_nm,
              pchs_qty: '',
              macl_cd: i.macl_cd,
              macl_cd_nm: i.macl_cd_nm,
              row_status: 'C',
            })
          } else {
            const rowState = dataProvider.getRowState(passyn1.dataRow)
            if (rowState === 'createAndDeleted') {
              dataProvider.showHiddenRows(passyn1.dataRow)
              dataProvider.setRowState(passyn1.dataRow, 'created')
            } else if (rowState === 'deleted') {
              dataProvider.setRowState(passyn1.dataRow, 'updated')
            } else {
              this.$toast.show(i.mtrl_nm + this.$t('MS00000036'), { className: 'toast_error' }) // 자재는 이미 선택 되었습니다.
            }
          }
        })
      }
      this.totalcnt = gridView.getItemCount()
    },
    del() {
      gridView.commit()
      const rows = gridView.getCheckedRows(true)
      dataProvider.setOptions({ softDeleting: true })
      dataProvider.hideRows(rows)
      dataProvider.removeRows(rows)

      this.totalcnt = gridView.getItemCount()
    },
    async save() {
      gridView.commit()
      if (this.isEmpty(this.mroSaveData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000039'), { className: 'toast_error' }) // 사업장 코드를 선택해주세요.(KO)
        this.$refs.wkpl_id.focus()
        return false
      }
      this.savedatas = []
      const lowstatus = dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      if (await this.validation(lowstatus)) {
        const crechk = await this.createPush(lowstatus)
        const upchk = await this.updatePush(lowstatus)
        const delchk = await this.deletePush(lowstatus)
        if (await (crechk && upchk && delchk)) {
          const saveparams = {}
          if (this.$route.params.goData === 'mro-mropchslist') {
            this.mroSaveData.pchs_prog_stt_cd = '10' // 저장 상태
          } else {
            this.mroSaveData.pchs_prog_stt_cd = '15' // 결재담기 상태
          }
          saveparams.mroSaveData = this.mroSaveData
          saveparams.savedatas = this.savedatas
          if (this.mroSaveData.aprv_yn === 'Y') {
            this.savenext = await this.$confirm(this.$t('MS00000092')) // 결재하시겠습니까?
          } else {
            this.savenext = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
          }
          if (await !this.savenext) {
          } else {
            await this.$axios.post('/api/v1/ch/mro/mropchs-multi', saveparams).then((res) => {
              const config = require('../../../../nuxt.config.js')
              if (res.data !== 'Fail' && res.data !== 'READ') {
                if (this.mroSaveData.aprv_yn === 'Y') {
                  // 추후 결제 로직 생기면 이곳에 결제 처리
                  this.$axios.post('/api/v1/ch/mro/mropchs-multi-aprv', saveparams).then((res2) => {
                    console.log(res2) // eslint-disable-line no-console
                    // this.$toast.show(this.$t('MS00000093')) // 결재되었습니다.(KO)
                    this.mroSaveData.aprv_yn = 'FIN'
                    if (res2.data.Success === 'Success') {
                      const popupWidth = 1575
                      const popupHeight = window.screen.height - 300
                      const popupX = window.screen.width / 2 - popupWidth / 2
                      const popupY = window.screen.height / 2 - popupHeight / 2
                      const options = 'location=no, status=no, menubar=no, toolbar=no, width=' + popupWidth + ',height=' + popupHeight + ',left=' + popupX + ',top=' + popupY + ''
                      const aprvno = '&dataurl=' + res2.data.aprvNo
                      const empno = '&typ=' + res2.data.empno
                      let url = config.default.approvalUrl2 + aprvno + empno
                      // 연구소라면
                      if (this.mroSaveData.wkpl_id === 'S02') {
                        url = config.default.approvalUrl3 + aprvno + empno
                      }
                      window.open(url, 'mropchs-multi-aprv', options)
                      // this.$toast.show(this.$t('MS00000222')) // 결재 되었습니다.
                      this.goLup()
                    } else {
                      this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
                    }
                  })
                } else {
                  this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
                  // 가져온 키 값으로 재조회
                  this.selectDtl(res.data)
                }
              } else if (res.data === 'READ') {
                this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
              }
            })
          }
        }
      }
    },
    async cart() {
      gridView.commit()
      if (this.isEmpty(this.mroSaveData.wkpl_id)) {
        this.$toast.show(this.$t('MS00000039'), { className: 'toast_error' }) // 사업장 코드를 선택해주세요.(KO)
        this.$refs.wkpl_id.focus()
        return false
      }
      this.savedatas = []
      const lowstatus = dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
      if (await this.validation(lowstatus)) {
        const crechk = await this.createPush(lowstatus)
        const upchk = await this.updatePush(lowstatus)
        const delchk = await this.deletePush(lowstatus)
        if (await (crechk && upchk && delchk)) {
          const saveparams = {}
          this.mroSaveData.pchs_prog_stt_cd = '15' // 결재담기 상태
          saveparams.mroSaveData = this.mroSaveData
          saveparams.savedatas = this.savedatas
          if (this.mroSaveData.aprv_yn === 'Y') {
            this.savenext = await this.$confirm(this.$t('MS00000244')) // 결재하시겠습니까?
          }
          if (await !this.savenext) {
          } else {
            await this.$axios.post('/api/v1/ch/mro/mropchs-multi', saveparams).then((res) => {
              if (res.data !== 'Fail' && res.data !== 'READ') {
                this.$toast.show('담았습니다.') // 저장되었습니다.
                this.mroSaveData.aprv_yn = 'FIN'
                // 가져온 키 값으로 재조회
                // this.selectDtl(res.data)
                this.goList()
              } else if (res.data === 'READ') {
                this.$toast.show(this.$t('MS00000043'), { className: 'toast_error' }) // 잘못된 요청입니다.
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
              }
            })
          }
        }
      }
    },
    async delsave() {
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }
      await this.$axios.delete(`/api/v1/ch/mro/mropchs/${this.mroSaveData.mro_pchs_no}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
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
    goLup() {
      this.$router.push({
        name: 'mro-mropchsmultilup-mropchsmultilup',
        params: {
          mropchsmultilup: this.mroSaveData.mro_pchs_no,
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
    async validation(lowstatus) {
      if (gridView.getItemCount() <= 0) {
        this.$toast.show(this.$t('MS00000038'), { className: 'toast_error' }) // 구매 품목 정보를 최소 한개 추가 해 주세요.(KO)
        return false
      }
      if (
        dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') === 0 &&
        this.tempData.wkpl_id === this.mroSaveData.wkpl_id &&
        this.tempData.pchs_cntn === this.mroSaveData.pchs_cntn &&
        this.mroSaveData.aprv_yn === 'N'
      ) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return false
      }
      const chkRow = [...lowstatus.created, ...lowstatus.updated, ...lowstatus.deleted]
      if (chkRow.length === 0) {
        return true
      } else {
        for (let i = 0; i < chkRow.length; i++) {
          if ((await dataProvider.getValue(chkRow[i], 'pchs_qty')) === '' || (await dataProvider.getValue(chkRow[i], 'pchs_qty')) < 1) {
            this.$toast.show(this.$t('MS00000040'), { className: 'toast_error' }) // 주문수량을 입력해 주세요.(KO)
            gridView.setCurrent({ dataRow: chkRow[i], fieldName: 'pchs_qty' })
            return false
          } else if (await !/^[0-9]*$/.test(dataProvider.getValue(chkRow[i], 'pchs_qty'))) {
            this.$toast.show(this.$t('MS00000162'), { className: 'toast_error' }) // 주문수량은 숫자만 입력해주세요.
            gridView.setCurrent({ dataRow: chkRow[i], fieldName: 'pchs_qty' })
            return false
          } else if (i + 1 === chkRow.length) {
            return true
          }
        }
      }
    },
    async createPush(lowstatus) {
      // 새로 생성된 row push
      if ((await dataProvider.getRowStateCount('created')) > 0) {
        lowstatus.created.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'C')
          this.savedatas.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async updatePush(lowstatus) {
      // 수정된 row push
      if ((await dataProvider.getRowStateCount('updated')) > 0) {
        lowstatus.updated.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'U')
          this.savedatas.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    async deletePush(lowstatus) {
      // 수정된 row push
      if ((await dataProvider.getRowStateCount('deleted')) > 0) {
        lowstatus.deleted.forEach((i) => {
          dataProvider.setValue(i, 'row_status', 'D')
          this.savedatas.push(dataProvider.getJsonRows(i, i))
        })
        return true
      } else {
        return true
      }
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
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
    fieldName: 'pchs_unit_cd',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'pchs_req_unit_cd',
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
