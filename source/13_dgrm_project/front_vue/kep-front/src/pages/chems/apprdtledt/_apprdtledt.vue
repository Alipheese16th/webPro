<template>
  <div>
    <div class="location">
      <!-- 시스템관리 --><!-- 결재 관리 -->
      <span>Home > {{ $t('LB00000042') }} > 결재관리</span>
    </div>
    <div class="work_title">
      <h2>결재 상세</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <h3>기본정보</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">결재번호</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.aprv_no }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">결재구분</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.arpv_cd }} / {{ frmData.arpv_nm }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">업무코드</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.biz_no }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">상신자</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.aprv_emp_no }} / {{ frmData.aprv_emp_nm }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">결재상태</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.aprv_stt_cd }} / {{ frmData.aprv_stt_nm }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">후처리 메소드</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.aprv_mthd_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">사용여부</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">
                  <v-select
                    ref="use_yn"
                    v-model="frmData.use_yn"
                    label="SELECT"
                    item-text="cd_nm"
                    item-value="cd"
                    :items="use_yn_cd"
                    outlined
                    single-line
                    :hide-details="true"
                    dense
                    height="30"
                  ></v-select>
                </div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">등록일시</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.crt_dt }}</div>
              </v-col>
              <v-col cols="1">
                <div class="label_tit">수정일시</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="label_tit">제목</div>
              </v-col>
              <v-col cols="11">
                <div class="label_con">{{ frmData.aprv_ttl }}</div>
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
                        <div style="overflow: auto" v-html="frmData.aprv_cntn"></div>
                      </div>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item :eager="true">
                    <v-card flat>
                      <br />
                      <!-- 소스 -->
                      <div class="label_con">
                        <p>{{ frmData.aprv_cntn }}</p>
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
    <div class="container_detail">
      <div class="sub_title">
        <h3>수신 이력정보</h3>
      </div>
    </div>
    <div class="realgrid_container">
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 300px"></div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <v-btn v-if="isReqYn" depressed width="120" color="primary" height="40" @click="apprSave('1')">결재상신</v-btn>
          <v-btn v-if="isCmplYn" depressed width="120" color="primary" height="40" @click="apprSave('3')">결재완료</v-btn>
          <v-btn v-if="isRjtYn" depressed width="120" color="primary" height="40" @click="apprSave('9')">결재반려</v-btn>
          <v-btn v-if="isCancelYn" depressed width="120" color="primary" height="40" @click="apprSave('0')">결재취소</v-btn>
          <v-btn depressed width="120" color="primary" height="40" @click="save()">저장</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList()">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

export default {
  meta: {
    title: '결재 상세',
    key(route) {
      return `/chems/apprlist/${route.params.catalog}`
    },
  },
  components: {},
  data() {
    return {
      isReqYn: false,
      isCmplYn: false,
      isRjtYn: false,
      isCancelYn: false,
      styleObject: {
        // color: 'red',
        fontSize: '13px',
        fontWeight: 'bold',
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'SELECT' },
        { cd: 'T', cd_nm: 'T' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      totalcnt: 0,
      schData: {},
      tmpSchData: {},
      frmData: {},
    }
  },
  computed: {
    routeTab() {
      return '결재 관리'
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    this.user = this.$store.getters['auth/getUser']
    console.log(this.user) // eslint-disable-line no-console

    // 이전 검색 결과 저장
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.tmpSchData = this.$route.params.schData
    } else {
      this.tmpSchData = {
        sDate: '',
        eDate: '',
      }
    }
  },
  mounted() {
    // 데이터프로바이더 셋팅
    const fields = [
      {
        fieldName: 'aprvseq',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'reqno',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'unid',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'docst',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'docst_nm',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'ecm_url',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'recdt',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_yn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'trn_dt',
        dataType: ValueType.TEXT,
      },
    ]
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)

    // 그리드 셋팅
    const columns = [
      {
        header: '순번',
        name: 'aprvseq',
        fieldName: 'aprvseq',
        width: '80',
      },
      {
        header: '결재번호',
        name: 'reqno',
        fieldName: 'reqno',
        width: '250',
      },
      {
        header: '문서키',
        name: 'unid',
        fieldName: 'unid',
        width: '250',
      },
      {
        header: '상태코드',
        name: 'docst',
        fieldName: 'docst',
        width: '80',
      },
      {
        header: '상태',
        name: 'docst_nm',
        fieldName: 'docst_nm',
        width: '80',
      },
      {
        header: 'ECM URL',
        name: 'ecm_url',
        fieldName: 'ecm_url',
        width: '220',
      },
      {
        header: '수신일시',
        name: 'recdt',
        fieldName: 'recdt',
        width: '200',
      },
      {
        header: '처리여부',
        name: 'trn_yn',
        fieldName: 'trn_yn',
        width: '60',
      },
      {
        header: '처리일시',
        name: 'trn_dt',
        fieldName: 'trn_dt',
        width: '150',
      },
    ]

    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })
    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'singleRow'
    gridView.displayOptions.rowHeight = 40
    gridView.displayOptions.fitStyle = 'fill'

    gridView.header.height = 60
    gridView.footer.height = 40

    // 코드 조회
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.select()
  },
  methods: {
    async select() {
      this.schData.aprvNo = this.$route.params.apprdtledt
      const data = await this.$axios.$get(`/api/v1/common/chems/appr-dtl/${this.schData.aprvNo}`)
      if (data.apprInfo.length > 0) {
        this.frmData = data.apprInfo[0]
      } else {
        this.frmData = {}
      }

      dataProvider.setRows(data.apprRecvInfo)
      gridView.refresh()
      gridView.setTopItem(0)

      this.totalcnt = dataProvider.getRowCount()

      // 버튼 활성화 처리
      const sttChk = { reqCnt: 0, ingCnt: 0, cmplCnt: 0, rjtCnt: 0, cancelCnt: 0 }
      for (let i = 0; i < dataProvider.getRowCount(); i++) {
        switch (dataProvider.getValue(i, 'docst')) {
          // 상신
          case '1':
            sttChk.reqCnt++
            break
          // 진행
          case '2':
            sttChk.ingCnt++
            break
          // 완료
          case '3':
            sttChk.cmplCnt++
            break
          // 반려
          case '9':
            sttChk.rjtCnt++
            break
          // 취소
          case '0':
            sttChk.cancelCnt++
            break
        }
      }

      if (sttChk.reqCnt === 0) {
        // 상신 상태가 없을 경우
        this.isReqYn = true // 결재상신 on
        this.isCmplYn = false // 결재완료 off
        this.isRjtYn = false // 결재반려 off
        this.isCancelYn = false // 결재취소 off
      } else {
        this.isReqYn = false // 결재상신 off
        // 완료, 반려, 취소처리를 한번도 안했을 경우 -> 완료, 반려 가능
        if (sttChk.cmplCnt === 0 && sttChk.rjtCnt === 0 && sttChk.cancelCnt === 0) {
          this.isCmplYn = true // 결재완료 on
          this.isRjtYn = true // 결재반려 on
        } else {
          this.isCmplYn = false // 결재완료 off
          this.isRjtYn = false // 결재반려 off
        }
        // 취소처리를 한번도 안했을 경우 -> 취소 가능
        if (sttChk.cancelCnt === 0) {
          this.isCancelYn = true
        } else {
          this.isCancelYn = false
        }
      }
    },
    async save() {
      const res = await this.$confirm('저장하시겠습니까?')
      if (!res) {
        return
      }

      const tempData = {}
      tempData.save_type = 'save'
      tempData.aprv_no = this.frmData.aprv_no
      tempData.use_yn = this.frmData.use_yn
      console.log('tempData : ', JSON.stringify(tempData)) // eslint-disable-line no-console

      await this.$axios.post('/api/v1/common/chems/appr-dtl', tempData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async apprSave(sttCd) {
      let msgTxt = ''
      switch (sttCd) {
        case '1':
          msgTxt = '결재상신'
          break
        case '3':
          msgTxt = '결재완료'
          break
        case '9':
          msgTxt = '결재반려'
          break
        case '0':
          msgTxt = '결재취소'
          break
      }
      const res = await this.$confirm(msgTxt + ' 처리를 하시겠습니까?')
      if (!res) {
        return
      }

      const tempData = {}
      tempData.save_type = 'apprsave'
      tempData.aprv_no = this.frmData.aprv_no
      tempData.docst = sttCd
      console.log('tempData : ', JSON.stringify(tempData)) // eslint-disable-line no-console

      await this.$axios.post('/api/v1/common/chems/appr-dtl', tempData).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.apprBatchExec()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async apprBatchExec() {
      const result = await this.$axios.$get(`/api/v1/batch/exec/BA001`)

      if (result === 'Success') {
        this.$toast.show('처리 완료되었습니다.')
        this.select()
      } else {
        this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
      }
    },
    goList() {
      this.$router.replace({
        name: `chems-apprlist`,
        params: {
          schData: this.tmpSchData,
        },
      })
    },
  },
}
</script>
<style></style>
