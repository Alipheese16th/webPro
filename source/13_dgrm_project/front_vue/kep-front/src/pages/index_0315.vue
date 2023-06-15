<template>
  <div class="container_homeui">
    <div class="homeui">
      <div class="container_step cols-2">
        <div style="display: flex; justify-content: space-between">
          <div class="container_step img_main" style="width: 94%; height: 220px">
            <!--Welcome-->
            <p class="txt_aside">D-EHS Solution package</p>
            <h3 class="tit_aside">DONGGRAMISOFT<br />Environment &amp; Health &amp; Safety</h3>
          </div>
        </div>
        <v-col cols="12">
          <v-row>
            <v-col cols="8" style="padding-left: 2px">
              <div class="container_step box_line">
                <!-- Notice -->
                <h3 style="font-size: 20px">Notice</h3>
                <!-- Notice -->
                <button class="btn_more" @click="goNotiList()">{{ $t('LB00000358') }}</button>
                <div class="step">
                  <ul class="container_list">
                    <li v-for="item in frmData.notiList" :key="item.bbs_doc_no" style="height: 30px; font-size: 15px">
                      <a style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 860px" @click="goNoti(item.bbs_doc_no)">
                        <em>[{{ item.crt_dt }}]</em> {{ item.bbs_doc_ttl }}</a
                      >
                    </li>
                  </ul>
                </div>
              </div>
            </v-col>
            <v-col cols="4" style="padding-left: 20px">
              <div class="container_step box_line2">
                <!-- 담당자 -->
                <h3 style="font-size: 20px">담당자</h3>
                <div class="step">
                  <ul class="container_list">
                    <li v-for="item in frmData.useSafeList" :key="item.cd" style="height: 30px; font-size: 15px">
                      {{ item.dept_user_nm }}
                    </li>
                  </ul>
                </div>
              </div>
            </v-col>
          </v-row>
        </v-col>
        <div style="display: flex; justify-content: space-between">
          <div class="container_step list" style="width: calc(66.666666% - 20px); border-top: 2px solid #f2f2f2">
            <!--안전/보건-->
            <h3>안전/보건</h3>
            <div style="display: flex; justify-content: space-between">
              <div class="container_step" style="width: calc(50% - 20px)">
                <div class="step manual manual05" style="cursor: pointer" @click="goMenu1List()">
                  <!--사업장 관리-->
                  <h4>사업장 관리</h4>
                </div>
                <div class="step manual manual06" style="cursor: pointer" @click="goMenu3List()">
                  <!--예산/투자 관리-->
                  <h4>예산/투자 관리</h4>
                </div>
                <div class="step manual manual07" style="cursor: pointer" @click="goMenu5List()">
                  <!--안전보건 회의체-->
                  <h4>안전보건 회의체</h4>
                </div>
                <div class="step manual manual08" style="cursor: pointer" @click="goMenu7List()">
                  <!--유해위험기계기구 관리-->
                  <h4>유해위험기계기구 관리</h4>
                </div>
                <div class="step manual manual02" style="cursor: pointer" @click="goMenu9List()">
                  <!--작업환경측정-->
                  <h4>작업환경측정</h4>
                </div>
              </div>
              <div class="container_step" style="width: calc(50% - 20px)">
                <div class="step manual manual09" style="cursor: pointer" @click="goMenu2List()">
                  <!--안전작업허가-->
                  <h4>안전작업허가</h4>
                </div>
                <div class="step manual manual08" style="cursor: pointer" @click="goMenu4List()">
                  <!--안전점검 및 개선-->
                  <h4>안전점검 및 개선</h4>
                </div>
                <div class="step manual manual11" style="cursor: pointer" @click="goMenu6List()">
                  <!--교육 및 훈련-->
                  <h4>교육 및 훈련</h4>
                </div>
                <div class="step manual manual12" style="cursor: pointer" @click="goMenu8List()">
                  <!--사고 보고 및 조사-->
                  <h4>사고 보고 및 조사</h4>
                </div>
                <div class="step manual manual10" style="cursor: pointer" @click="goMenu10List()">
                  <!--건강검진-->
                  <h4>건강검진</h4>
                </div>
              </div>
            </div>
          </div>
          <div class="container_step list" style="width: calc(33.333333333% - 20px); border-top: 2px solid #f2f2f2">
            <!--화학물질-->
            <h3>화학물질</h3>
            <div class="step manual manual01" style="cursor: pointer" @click="goMenu11List()">
              <!--MRO 구매-->
              <h4>MRO 구매</h4>
            </div>
            <div class="step manual manual02" style="cursor: pointer" @click="goMenu12List()">
              <!--규제검토요청-->
              <h4>규제검토요청</h4>
            </div>
            <div class="step manual manual03" style="cursor: pointer" @click="goMenu13List()">
              <!--MSDS-->
              <h4>MSDS</h4>
            </div>
            <div class="step manual manual05" style="cursor: pointer" @click="goMenu14List()">
              <!--현황/통계-->
              <h4>현황/통계</h4>
            </div>
            <div class="step manual manual04" style="cursor: pointer" @click="goMenu15List()">
              <!--화학물질 규제조회-->
              <h4>화학물질 규제조회</h4>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="footer_wrap">
      <div class="footer_con">
        <div class="footer_left">Copyright <b>DONGGRAMISOFT</b> All Rights Reserved.</div>
      </div>
    </div>

    <!-- 1번째 공지사항 시작-->
    <div v-for="item in noticeList" :key="item.bbs_doc_no">
      <div v-if="item.cookie" class="container_notice" :style="item.style">
        <h2>{{ item.bbs_doc_ttl }}</h2>
        <v-btn icon class="btn_notice_cls" @click=";(item.cookie = false), setCookie(item.check, item.bbs_doc_no, 'done', 1)"></v-btn>
        <div class="notice_con">
          <quill-editor v-model="item.bbs_doc_cntn" :options="editorOption" :disabled="true" />
        </div>
        <v-container style="padding: 0">
          <v-row>
            <v-col cols="12" style="display: flex; justify-content: flex-end">
              <v-checkbox
                :label="$t('LB00000486')"
                height="30"
                width="120"
                :hide-details="true"
                style="display: inline-block"
                class="mr-4"
                @click="item.check = !item.check"
              ></v-checkbox>
              <v-btn color="" outlined height="30" @click=";(item.cookie = false), setCookie(item.check, item.bbs_doc_no, 'done', 1)">{{ $t('LB00000109') }}</v-btn>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </div>
    <!-- 1번째 공지사항 끝-->
    <!-- <popup ref="sbstpop" mkey="indsx_sbstpop" @close="sbstPopClose" /> -->
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
// import popup from './common/sbstslct'

const date = new Date()

export default {
  // middleware: 'authenticated',
  meta: {
    title: 'Home',
    // closable: false,
  },
  components: {
    // popup,
    quillEditor,
  },
  fetch() {
    this.selectNoti()
    this.selectMainList()
  },
  data() {
    return {
      all: false,
      fst: true,
      snd: true,
      thd: true,
      schData: {
        wkpl_id: '',
      },
      sbstRgltSchData: {
        cas_no: '',
        rglt_crtr_rati: '',
        sbst_no: '',
      },
      frmData: {
        notiList: [{ bbs_doc_no: '', crt_dt: '', bbs_doc_ttl: '' }],
        alrmList: [{ rglt_act_alrm_no: '', crt_da: '', alrm_ttl: '' }],
        countList: {
          mtrlcount: 0,
          mrocount: 0,
          mtrlmrocount: 0,
          mtrlchkcount: 0,
          mrochkcount: 0,
          mtrlmrochkcount: 0,
          mtrlper: 0,
          mroper: 0,
          mtrlmroper: 0,
        },
        rgltList: [],
        useBizList: [],
        whList: {
          lastmonthwhqty: 0,
          thisyearwhqty: 0,
        },
        rgltSbstList: {
          icmmtrlcnt1: '',
          icsmtrlcnt1: '',
          ihfmtrlcnt1: '',
          iocmtrlcnt1: '',
          iosmtrlcnt1: '',
          rgtmtrlcnt1: '',
          mntmtrlcnt1: '',
          icmmtrlcnt2: '',
          icsmtrlcnt2: '',
          ihfmtrlcnt2: '',
          iocmtrlcnt2: '',
          iosmtrlcnt2: '',
          rgtmtrlcnt2: '',
          mntmtrlcnt2: '',
          icmmtrlnm: '',
          icsmtrlnm: '',
          ihfmtrlnm: '',
          iocmtrlnm: '',
          iosmtrlnm: '',
          rgtmtrlnm: '',
          mntmtrlnm: '',
        },
      },
      noticeList: [],
      useSafeList: [],
      notice1: false,
      notice2: false,
      chartData: {
        labels: ['2019-06', '2019-07', '2019-08', '2019-09', '2019-10', '2019-11', '2019-12', '2020-01', '2020-02', '2020-03', '2020-04', '2020-05'],
        datasets: [
          {
            label: 'Visits',
            data: [10, 15, 20, 30, 40, 50, 60, 70, 34, 45, 11, 78, 45],
            backgroundColor: '#359FEB',
          },
          {
            label: 'Users',
            data: [45, 65, 30, 53, 34, 35, 26, 37, 34, 45, 67, 87, 98],
            backgroundColor: '#F22C6F',
          },
        ],
      },
      chartOptions: {
        responsive: true,
        legend: {
          display: false,
          fontColor: 'rgba(255, 255, 255, 0.75)',
        },
        title: {
          display: true,
          text: 'Google analytics data',
          fontSize: 18,
          fontColor: 'rgba(255, 255, 255, 0.75)',
        },
        tooltips: {
          backgroundColor: '#359FEB',
        },
        fill: false,
        scales: {
          xAxes: [
            {
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
              },
              gridLines: {
                display: false,
              },
            },
          ],
        },
      },
      editorOption: {
        // some quill options
        modules: {
          toolbar: false,
        },
        placeholder: '',
      },
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
    dDate() {
      return this.get_date_str_gubun(date, '-')
    },
    dMonth() {
      return this.get_month_str(date)
    },
    dYear() {
      return this.get_year_str(date)
    },
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
    get_month_str(date, gubun) {
      let sMonth = date.getMonth() + 1

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      return sMonth
    },
    get_year_str(date, gubun) {
      const sYear = date.getFullYear()
      return sYear
    },
    async selectNoti() {
      await this.$axios
        .$get(`/api/v1/common/bbs/noti-pop`, { params: '' })
        .then((res) => {
          res.forEach((value, index) => {
            value.cookie = true
            value.check = false
            value.style = 'left: ' + (50 + index * 2) + '%; top: ' + (50 + index * 6) + '%;'
            this.noticeList.push(value)
          })
          this.getCookie()
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    setCookie(check, name, value, expiredays) {
      if (check) {
        const todayDate = new Date()
        todayDate.setDate(todayDate.getDate() + 1)
        document.cookie = name + '=' + escape(value) + '; path=/; expires=' + todayDate.toGMTString() + ';'
      }
    },
    async getCookie() {
      const cookiedata = document.cookie
      await this.noticeList.forEach((value, index) => {
        if (cookiedata.includes(value.bbs_doc_no + '=done')) {
          value.cookie = false
        }
      })
    },
    selectMainList(wkplId) {
      if (!this.isEmpty(wkplId)) {
        this.schData.wkpl_id = wkplId
      }
      this.$axios
        .$get(`/api/v1/common/home/main-list`, { params: this.schData })
        .then((res) => {
          console.log(res) // eslint-disable-line no-console
          if (this.isEmpty(this.schData.wkpl_id)) {
            this.schData.wkpl_id = 'ALL'
          }
          this.frmData.notiList = res.notiList
          this.frmData.alrmList = res.alrmList
          this.frmData.countList = res.countList[0]
          this.frmData.rgltList = res.rgltList
          this.frmData.useBizList = res.useBizList
          this.frmData.whList = res.whList[0]
          this.frmData.rgltSbstList = res.rgltSbstList[0]
          this.frmData.useSafeList = res.useSafeList
        })
        .catch((err) => {
          console.log(err) // eslint-disable-line no-console
        })
    },
    goNoti(docNo) {
      this.$router.push({
        name: `bbs-notilup-notilup`,
        params: {
          notilup: docNo,
        },
      })
    },
    goAlrm(alrmNo) {
      this.$router.push({
        name: `rgltact-rgltactalrmlup-rgltactalrmlup`,
        params: {
          rgltactalrmlup: alrmNo,
        },
      })
    },
    goMenu1List() {
      this.$router.push({
        name: `stndinfr-wkplmgntlist`,
      })
    },
    goMenu2List() {
      this.$router.push({
        name: `cstprms-cstprmsmgntlist`,
      })
    },
    goMenu3List() {
      this.$router.push({
        name: `stndinfr-wkplinvsmgntlist`,
      })
    },
    goMenu4List() {
      this.$router.push({
        name: `sftchk-oprnbgnbfchktgtmgntlist`,
      })
    },
    goMenu5List() {
      this.$router.push({
        name: `mtng-mtngmgntlist`,
      })
    },
    goMenu6List() {
      this.$router.push({
        name: `trnmgnt-trnrsltmgntlist`,
      })
    },
    goMenu7List() {
      this.$router.push({
        name: `hmflmcn-hmflriskmcnmgntlist`,
      })
    },
    goMenu8List() {
      this.$router.push({
        name: `acdtmgnt-acdtmgntlist`,
      })
    },
    goMenu9List() {
      this.$router.push({
        name: `opem-opemmgntlist`,
      })
    },
    goMenu10List() {
      this.$router.push({
        name: `hmex-hmexmstrmgntlist`,
      })
    },
    goMenu11List() {
      this.$router.push({
        name: `mro-mropchslist`,
      })
    },
    goMenu12List() {
      this.$router.push({
        name: `rgltchk-rgltchkreqlist`,
      })
    },
    goMenu13List() {
      this.$router.push({
        name: `msds-msdslist`,
      })
    },
    goMenu14List() {
      this.$router.push({
        name: `csttstat-statreschlist`,
      })
    },
    goMenu15List() {
      this.$router.push({
        name: `csttstat-rgltchktotallist`,
      })
    },
    goNotiList() {
      this.$router.push({
        name: `bbs-notilist`,
      })
    },
    toggleAll() {
      this.schData.wkpl_id = ''
      this.all = false
      this.fst = true
      this.snd = true
      this.thd = true
      this.selectMainList()
    },
    toggleFst() {
      this.schData.wkpl_id = 'S03'
      this.all = true
      this.fst = false
      this.snd = true
      this.thd = true
      this.selectMainList('S03')
    },
    toggleSnd() {
      this.schData.wkpl_id = 'S04'
      this.all = true
      this.fst = true
      this.snd = false
      this.thd = true
      this.selectMainList('S04')
    },
    toggleThd() {
      this.schData.wkpl_id = 'S02'
      this.all = true
      this.fst = true
      this.snd = true
      this.thd = false
      this.selectMainList('S02')
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    sbstPop() {
      this.$refs.sbstpop.open('', false)
    },
    sbstPopClose(returnData) {
      // eslint-disable-next-line no-console
      console.log(returnData)
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.sbstRgltSchData.cas_no = returnData[0].cas_no
        this.sbstRgltSchData.sbst_no = returnData[0].sbst_no
      }
    },
    goSbstRgltList() {
      if (!this.isEmpty(this.sbstRgltSchData.cas_no) && !this.isEmpty(this.sbstRgltSchData.cas_no)) {
        this.$router.push({
          name: `rgltdb-sbstRgltList`,
          params: {
            sbstRgltSchData: this.sbstRgltSchData,
          },
        })
      } else {
        this.$toast.show(this.$t('MS00000198'), { className: 'toast_error' }) // 빠른규제 검토 검색조건을 모두 입력 해 주세요.
        if (this.isEmpty(this.sbstRgltSchData.cas_no)) {
          this.$refs.sbstselct.focus()
        } else {
          this.$refs.sbstrati.focus()
        }
      }
    },
  },
}
</script>
<style scoped>
.container_homeui {
  /* background: #ecedf2; */
  padding-bottom: 30px;
}
.homeui {
  margin: 0 auto;
  width: 1340px;
  padding-top: 30px;
  display: flex;
}

.homeui > div {
  width: 450px;
  margin-right: 40px;
}
.homeui > div:last-child {
  margin-right: 0;
}
.container_step {
  position: relative;
}
.container_step.box_line {
  overflow: hidden;
  overflow-y: hidden;
  padding: 10px;
  height: 210px;
  width: 870px;
  border: 1px solid #ededed;
  border-radius: 4px;
}

.container_step.box_line2 {
  overflow: hidden;
  overflow-y: hidden;
  padding: 10px;
  height: 210px;
  width: 427px;
  border: 1px solid #ededed;
  border-radius: 4px;
}
.container_step.box_line .step {
  border: 0;
}
.container_step h3 {
  font-size: 16px;
  font-weight: normal;
  color: #222222;
}
.btn_more {
  position: absolute;
  right: 13px;
  top: 10px;
  background: url('~assets/images/btn_more.png') no-repeat right;
  font-size: 20px;
  padding-right: 12px;
}
.info_more {
  position: absolute;
  right: 0;
  top: 0;
  font-size: 13px;
  color: #777;
}
.btn_more:hover {
  color: #359feb;
}
.container_step .step {
  background: #fff;
  border-radius: 4px;
  padding: 15px 0;
}
.container_step .step ul {
  padding: 0;
}
.container_step .step ul li {
  padding: 0;
}
.container_step .step ul li a {
  display: inline-block;
  line-height: 30px;
  color: #444;
  text-decoration: none;
}
.container_step .step ul li a:hover {
  color: #359feb;
  text-decoration: underline;
}
.container_step .group_padd {
  margin-top: 35px;
}
.main-color {
  background: #062c5e !important;
  color: rgba(255, 255, 255, 0.85);
}
.home_info {
  padding-left: 10px !important;
  font-size: 14px;
  background-image: url('~assets/images/home_info.png') !important;
  background-origin: content-box !important;
  background-color: #fff !important;
  background-repeat: no-repeat !important;
  background-position: left !important;
  background-size: 40px 40px !important;
}
.home_info ul {
  margin-left: 50px !important;
}
.home_info b {
  color: #062c5e;
}
.container_list em {
  color: #777;
  font-style: normal;
}
.home_link a {
  display: block;
  font-size: 14px;
  background: url('~assets/images/home_link.png') no-repeat right;
  line-height: 30px;
  text-decoration: none;
  columns: #444;
}
.home_link a:hover {
  color: #359feb;
}
.container_step.img_main {
  background: url('~assets/images/img_main.png') no-repeat right;
  padding-right: 12px;
}
.txt_aside {
  font-size: 32px;
  font-weight: normal;
  color: #3dacfb;
  margin-top: 20px;
  margin-left: 70px;
}
.container_step {
  z-index: 1;
}
.container_step h3.tit_aside {
  color: #062c5e;
  font-weight: normal;
  font-size: 30px;
  margin-left: 70px;
}
.container_step.list > h3 {
  font-size: 20px;
  padding: 20px 0;
}
.container_step .manual {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 11px 0px 12px 80px !important;
  margin-bottom: 10px;
}
.container_step .manual:after {
  content: '';
  display: inline-block;
  width: 10px;
  height: 17px;
  background: url('~assets/images/ic_more.png') no-repeat;
}
.container_step .manual01 {
  background: #fff url('~assets/images/m_01.png') no-repeat 20px center !important;
}
.container_step .manual02 {
  background: #fff url('~assets/images/m_02.png') no-repeat 20px center !important;
}
.container_step .manual03 {
  background: #fff url('~assets/images/m_03.png') no-repeat 20px center !important;
}
.container_step .manual04 {
  background: #fff url('~assets/images/m_04.png') no-repeat 20px center !important;
}
.container_step .manual05 {
  background: url('~assets/images/m_05.png') no-repeat 20px center !important;
}
.container_step .manual06 {
  background: #fff url('~assets/images/m_06.png') no-repeat 20px center !important;
}
.container_step .manual07 {
  background: #fff url('~assets/images/m_07.png') no-repeat 20px center !important;
}
.container_step .manual08 {
  background: #fff url('~assets/images/m_08.png') no-repeat 20px center !important;
}
.container_step .manual09 {
  background: #fff url('~assets/images/m_09.png') no-repeat 20px center !important;
}
.container_step .manual10 {
  background: #fff url('~assets/images/m_10.png') no-repeat 20px center !important;
}
.container_step .manual11 {
  background: #fff url('~assets/images/m_11.png') no-repeat 20px center !important;
}
.container_step .manual12 {
  background: #fff url('~assets/images/m_12.png') no-repeat 20px center !important;
}
.manual a {
  text-decoration: none;
}
.manual a:hover {
  text-decoration: underline;
}
.manual h4 {
  font-size: 16px;
  font-weight: normal;
  color: #353535;
}
.manual p {
  color: #777777;
  margin: 0;
}
.t_cnt {
  color: #3dacfb;
}
.cols-2 {
  width: 100% !important;
}
.new_group {
  display: flex;
  justify-content: space-between;
}
.new_group > div {
  width: 390px;
}
.new_group table {
  width: 100%;
  border-collapse: collapse;
  border-top: 1px solid #777;
  border-bottom: 1px solid #dcdcdc;
}
.new_group table th {
  height: 30px;
}
.new_group table tr td {
  height: 30px;
  border: 1px solid #eee;
  text-align: right;
  font-size: 18px;
  padding: 0 10px;
  font-weight: bold;
  color: #062c5e;
  width: 35%;
}
.new_group table tr td:first-child {
  text-align: left;
  font-size: 15px;
  font-weight: normal;
  padding: 0;
  color: #444;
  border-left: 0px none;
  width: 30%;
}
.new_group table tr td:last-child {
  border-right: 0px none;
}
.new_group table tr:last-child td {
  border-bottom: 0px none;
}
.new_group .list_table {
  background: #f4f6fb;
  padding: 10px 15px;
  border-radius: 4px;
}
.new_group .list_table table {
  border: 0px none;
}
.new_group .list_table table td {
  height: 32px;
  border: 0px none;
  letter-spacing: -1px;
}
.new_group .list_table table td:first-child {
  width: 60%;
}
.main_sch {
  display: flex;
}
.banner_list {
  display: flex;
  justify-content: space-between;
}
.banner_list > div {
  padding: 0 !important;
}
.banner_list a {
  width: 130px;
  height: 68px;
  display: inline-block;
  color: #fff;
  font-size: 18px;
  line-height: 68px;
  text-align: center;
  text-decoration: none;
  letter-spacing: -1;
}
.board_list li {
  display: flex;
  justify-content: space-between;
  padding: 5px 0 !important;
  font-size: 15px;
}
.board_list .board_left {
  padding: 10px 0;
}
.board_list .board_right {
  display: flex;
  justify-content: space-between;
  width: 50%;
  background: #f4f6fb;
  border-radius: 4px;
  padding: 10px;
}
.board_list .board_right .t_cnt {
  font-size: 30px;
  font-weight: bold;
  color: #359feb;
  padding-top: 9px;
}
.board_list p {
  margin: 0;
}
.board_list p:last-child {
  font-size: 22px;
  color: #359feb;
  font-weight: bold;
}
.board_list li em {
  color: #777 !important;
  font-size: 15px !important;
  font-style: normal;
}
.btn_zone button {
  background: #f6f6f6;
  border: 1px solid #dcdcdc;
  height: 30px;
  width: 50px;
  margin-left: -1px;
  font-weight: bold;
}
.btn_zone button:first-child {
  border-radius: 2px 0 0 2px;
}
.btn_zone button:last-child {
  border-radius: 0 2px 2px 0;
}
.btn_zone .on {
  background: #359feb;
  border: 1px solid #359feb;
  color: #fff;
}

/** **/
.container_home {
  padding: 50px 80px;
}
.home_1step {
  background: #062c5e;
  border-radius: 6px;
  padding: 20px 30px;
}
.home_1step h2 {
  color: rgba(255, 255, 255, 0.75);
  font-size: 18px;
}

.container_notice {
  display: table;
  z-index: 1;
  position: relative;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 800px;
  height: 490px;
  background: #fff;
  border-radius: 4px;
  padding: 30px 30px 0 30px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.35);
}
.container_notice h2 {
  border-bottom: 1px solid #777;
  padding: 0 0 10px 0;
  font-size: 20px;
  color: #222222;
}
.notice_con {
  overflow: auto;
  height: 360px;
  border-bottom: 1px solid #dcdcdc;
}
.notice_con .contents {
  padding: 5px;
  line-height: 2;
  font-size: 15px;
}
.btn_notice_cls {
  position: absolute !important;
  right: 25px;
  top: 25px;
  background: url('~assets/images/btn_pop_cls.png') no-repeat center;
  width: 20px;
  height: 20px;
}
.footer_wrap {
  margin-top: 24px;
}
.footer_wrap .footer_con {
  display: flex;
  justify-content: space-between;
  padding: 16px 0;
  margin: 0 auto;
  max-width: 1340px;
  border-top: 1px solid #ddd;
}
.footer_wrap .footer_right b {
  font-weight: bold;
}
.footer_wrap .footer_right img {
  height: 28px;
}
</style>
