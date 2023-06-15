<template>
  <div class="container_homeui">
    <div class="homeui">
      <div class="container_step cols-2">
        <div style="display: flex; justify-content: space-between">
          <div class="container_step" style="width: calc(50% - 20px)">
            <!-- 공지사항 -->
            <h3>{{ $t('LB00000116') }}</h3>
            <!-- 자세히 -->
            <button class="btn_more" @click="goNotiList()">{{ $t('LB00000358') }}</button>
            <div class="step" style="padding: 15px 20px !important">
              <ul class="container_list">
                <li v-for="item in frmData.notiList" :key="item.bbs_doc_no" style="height: 30px">
                  <a style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 250px" @click="goNoti(item.bbs_doc_no)"
                    ><em>[{{ item.crt_dt }}]</em> {{ item.bbs_doc_ttl }}</a
                  >
                </li>
              </ul>
            </div>
          </div>
          <div class="container_step" style="width: calc(50% - 20px)">
            <h3>Alarm</h3>
            <!-- 자세히 -->
            <button class="btn_more" @click="goAlrmList()">{{ $t('LB00000358') }}</button>
            <div class="step" style="padding: 15px 20px !important">
              <ul class="container_list">
                <li v-for="item in frmData.alrmList" :key="item.rglt_act_alrm_no" style="height: 30px">
                  <a style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; width: 250px" @click="goAlrm(item.rglt_act_alrm_no)"
                    ><em>[{{ item.crt_da }}]</em> {{ item.alrm_ttl }}</a
                  >
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="group_padd"></div>
        <div style="display: flex">
          <h3>{{ $t('LB00000359') }}</h3>
          <span class="ml-2 pt-1">({{ dDate }} 기준)</span>
          <v-spacer></v-spacer>
          <div class="btn_zone">
            <!-- 전체 -->
            <button :class="{ on: !all }" @click="toggleAll()">{{ $t('LB00000360') }}</button><button :class="{ on: !fst }" @click="toggleFst()">{{ $t('LB00000361') }}</button
            ><button :class="{ on: !snd }" @click="toggleSnd()">{{ $t('LB00000362') }}</button><button :class="{ on: !thd }" @click="toggleThd()">{{ $t('LB00000363') }}</button>
          </div>
        </div>
        <div class="step">
          <div class="new_group">
            <div>
              <ul class="board_list">
                <li style="border-bottom: 1px solid #eee">
                  <div class="board_left">
                    <!-- 원료 -->
                    <p>{{ $t('LB00000364') }}</p>
                    <p>
                      {{ frmData.countList.mtrlcount }}<em>{{ $t('LB00000365') }}</em>
                    </p>
                  </div>
                  <div class="board_right">
                    <div>
                      <!-- 규제원료 -->
                      <p>{{ $t('LB00000366') }}</p>
                      <p>
                        {{ frmData.countList.mtrlchkcount }}<em>{{ $t('LB00000365') }}</em>
                      </p>
                    </div>
                    <div class="t_cnt">{{ frmData.countList.mtrlper }}<em style="font-size: 14px">%</em></div>
                  </div>
                </li>
                <li>
                  <div class="board_left">
                    <!-- 소모성자재 -->
                    <p>{{ $t('LB00000367') }}</p>
                    <p>
                      {{ frmData.countList.mrocount }}<em>{{ $t('LB00000365') }}</em>
                    </p>
                  </div>
                  <div class="board_right">
                    <div>
                      <!-- 규제 자재 -->
                      <p>{{ $t('LB00000368') }}</p>
                      <p>
                        {{ frmData.countList.mrochkcount }}<em>{{ $t('LB00000365') }}</em>
                      </p>
                    </div>
                    <div class="t_cnt">{{ frmData.countList.mroper }}<em style="font-size: 14px">%</em></div>
                  </div>
                </li>
                <li style="background: #062c5e; border-radius: 4px">
                  <div class="board_left" style="padding-left: 15px">
                    <!-- 전체 물질 수 -->
                    <p style="color: #fff">{{ $t('LB00000369') }}</p>
                    <p>
                      {{ frmData.countList.mtrlmrocount }}<em style="color: rgba(255, 255, 255, 0.7) !important">{{ $t('LB00000365') }}</em>
                    </p>
                  </div>
                  <div class="board_right" style="background: none">
                    <div>
                      <!-- 규제 물질 -->
                      <p style="color: #fff">{{ $t('LB00000370') }}</p>
                      <p>
                        {{ frmData.countList.mtrlmrochkcount }}<em style="color: rgba(255, 255, 255, 0.7) !important">{{ $t('LB00000365') }}</em>
                      </p>
                    </div>
                    <div class="t_cnt">{{ frmData.countList.mtrlmroper }}<em style="font-size: 14px; color: rgba(255, 255, 255, 0.7) !important">%</em></div>
                  </div>
                </li>
              </ul>
              <div class="group_padd"></div>
              <!-- 규제물질 대상순위 -->
              <h3>{{ $t('LB00000371') }}</h3>
              <div class="list_table">
                <table>
                  <tr v-for="item in frmData.rgltList" :key="item.rglt_no">
                    <td>{{ item.rownum }}. {{ item.rglt_nm }}</td>
                    <td width="150" align="right">{{ item.rglt_count }}개</td>
                    <td width="200" align="right">{{ item.rgltper }}%</td>
                  </tr>
                </table>
              </div>
            </div>
            <div>
              <!-- 화학물질 입고량 -->
              <h3>{{ $t('LB00000372') }}</h3>
              <ul>
                <li style="display: flex; justify-content: space-between; line-height: 45px; border-bottom: 1px solid #eee; border-top: 1px solid #ccc; padding: 5px 0">
                  <!-- 전월 입고량 -->
                  <div style="font-size: 15px; color: #062c5e">{{ $t('LB00000373') }}</div>
                  <div class="t_cnt" style="font-size: 32px; font-weight: bold; background: #f4f6fb; width: 190px; border-radius: 4px; text-align: right; padding: 0 10px">
                    {{ frmData.whList.lastmonthwhqty }}<em style="font-size: 14px">ton</em>
                  </div>
                </li>
                <li style="display: flex; justify-content: space-between; line-height: 45px; border-bottom: 1px solid #dcdcdc; padding: 5px 0">
                  <!-- 연간 누적 입고량 -->
                  <div style="font-size: 15px; color: #062c5e">{{ $t('LB00000374') }}</div>
                  <div class="t_cnt" style="font-size: 32px; font-weight: bold; background: #f4f6fb; width: 190px; border-radius: 4px; text-align: right; padding: 0 10px">
                    {{ frmData.whList.thisyearwhqty }}<em style="font-size: 14px">ton</em>
                  </div>
                </li>
              </ul>
              <div class="group_padd"></div>
              <div>
                <!-- 규제물질 현황 및 비율 -->
                <h3>{{ $t('LB00000375') }}</h3>
                <table>
                  <tr height="38">
                    <th>물질</th>
                    <th>{{ dYear }}{{ $t('LB00000554') }} {{ dMonth }}{{ $t('LB00000555') }}{{ $t('LB00000557') }}</th>
                    <th>{{ dYear }}{{ $t('LB00000554') }} {{ $t('LB00000556') }}{{ $t('LB00000557') }}</th>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.icmmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.icmmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.icmmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.icsmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.icsmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.icsmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.ihfmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.ihfmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.ihfmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.iocmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.iocmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.iocmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.iosmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.iosmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.iosmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.rgtmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.rgtmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.rgtmtrlcnt2 }}</td>
                  </tr>
                  <tr height="33">
                    <td>{{ frmData.rgltSbstList.mntmtrlnm }}</td>
                    <td>{{ frmData.rgltSbstList.mntmtrlcnt1 }}</td>
                    <td>{{ frmData.rgltSbstList.mntmtrlcnt2 }}</td>
                  </tr>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="container_step" style="padding-left: 40px; border-left: 1px solid #dcdcdc">
        <!-- 주요업무 바로가기 -->
        <h3>{{ $t('LB00000376') }}</h3>
        <div class="banner_list">
          <!-- 구매품의 -->
          <div class="step main-color">
            <a @click="goMroPchsList()">{{ $t('LB00000377') }}</a>
          </div>
          <!-- 규제검토 요청 -->
          <div class="step main-color">
            <a @click="goRgltChkReqList()">{{ $t('LB00000337') }}</a>
          </div>
          <!-- MSDS 조회 -->
          <div class="step main-color">
            <a @click="goMsdsList()">{{ $t('LB00000041') }}</a>
          </div>
        </div>
        <div class="group_padd"></div>
        <!-- 빠른규제 검토 -->
        <h3>{{ $t('LB00000378') }}</h3>
        <div class="step main_sch">
          <!--CAS NO. 선택-->
          <v-text-field
            ref="sbstselct"
            v-model="sbstRgltSchData.cas_no"
            outlined
            single-line
            :hide-details="true"
            dense
            :placeholder="$t('LB00000455')"
            readonly
            height="30"
            class="icon_sch"
            @click="sbstPop()"
          ></v-text-field>
          <!--함량을입력해주세요.-->
          <v-text-field
            ref="sbstrati"
            v-model="sbstRgltSchData.rglt_crtr_rati"
            outlined
            single-line
            :hide-details="true"
            dense
            :placeholder="$t('MS00000044')"
            height="30"
            class="mr-1"
          ></v-text-field>
          <v-btn color="primary" depressed height="30" @click="goSbstRgltList()">{{ $t('LB00000030') }}</v-btn>
        </div>
        <div class="group_padd"></div>
        <!--매뉴얼 다운로드-->
        <h3>{{ $t('LB00000456') }}</h3>
        <div class="step manual manual01" style="cursor: pointer" @click="goSysmanuList('101')">
          <!--향-->
          <h4>{{ $t('CD00000783') }}</h4>
          <!--향료 전체 물질정보 등록 매뉴얼-->
          <p>{{ $t('LB00000457') }}</p>
        </div>
        <div class="step manual manual02" style="cursor: pointer" @click="goSysmanuList('102')">
          <!--소모성 자재-->
          <h4>{{ $t('CD00000782') }}</h4>
          <!--사업장 화학물질 구매 시 품의 작성 매뉴얼-->
          <p>{{ $t('LB00000458') }}</p>
        </div>
        <div class="step manual manual03" style="cursor: pointer" @click="goSysmanuList('201')">
          <!--MSDS-->
          <h4>{{ $t('LB00000179') }}</h4>
          <!--MSDS 개정 정보 업데이트 매뉴얼-->
          <p>{{ $t('LB00000459') }}</p>
        </div>
        <div class="step manual manual04" style="cursor: pointer" @click="goSysmanuList('301')">
          <!--환경안전-->
          <h4>{{ $t('CD00000786') }}</h4>
          <!--환경안전 담당자 물질 검토 매뉴얼-->
          <p>{{ $t('LB00000460') }}</p>
        </div>
        <div class="group_padd"></div>
        <!--이용문의-->
        <h3>{{ $t('LB00000461') }}</h3>
        <div class="step home_info">
          <ul>
            <li v-for="item in frmData.useBizList" :key="item.emp_no">
              {{ item.wkpl_nm }} - {{ item.dept_nm }} / <b>{{ item.emp_nm }}</b>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <popup ref="sbstpop" mkey="indsx_sbstpop" @close="sbstPopClose" />
  </div>
</template>

<script>
import popup from '../common/sbstslct'

const date = new Date()

export default {
  // middleware: 'authenticated',
  meta: {
    title: '화학물질 Main',
    // closable: false,
  },
  components: {
    popup,
  },
  fetch() {
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
        biz_cd: 'CH',
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
            value.style = 'left: ' + (50 + index * 2) + '%; top: ' + (40 + index * 6) + '%;'
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
    goNotiList() {
      this.$router.push({
        name: `bbs-notilist`,
      })
    },
    goAlrmList() {
      this.$router.push({
        name: `rgltact-rgltactalrmlist`,
      })
    },
    goMsdsList() {
      this.$router.push({
        name: `msds-msdslist`,
      })
    },
    goRgltChkReqList() {
      this.$router.push({
        name: `rgltchk-rgltchkreqlist`,
      })
    },
    goMroPchsList() {
      this.$router.push({
        name: `mro-mropchslist`,
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
    goSysmanuList(cd) {
      this.$router.push({
        name: `bbs-sysmanulist`,
        params: {
          cd,
        },
      })
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
  background: #ecedf2;
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
.container_step h3 {
  font-size: 16px;
  margin-bottom: 14px;
  color: #222222;
}
.btn_more {
  position: absolute;
  right: 0;
  top: 0;
  background: url('~assets/images/btn_more.png') no-repeat right;
  font-size: 13px;
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
  padding: 15px;
}
.container_step .step ul {
  padding: 0;
  margin: 0 5px;
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
  font-size: 15px;
  background: url('~assets/images/home_link.png') no-repeat right;
  line-height: 30px;
  text-decoration: none;
  columns: #444;
}
.home_link a:hover {
  color: #359feb;
}
.container_step .manual {
  padding: 11px 20px 12px 80px !important;
  margin-bottom: 10px;
}
.container_step .manual01 {
  background: #fff url('~assets/images/manual01.png') no-repeat 24px center !important;
}
.container_step .manual02 {
  background: #fff url('~assets/images/manual02.png') no-repeat 20px center !important;
}
.container_step .manual03 {
  background: #fff url('~assets/images/manual03.png') no-repeat 26px center !important;
}
.container_step .manual04 {
  background: #fff url('~assets/images/manual04.png') no-repeat 20px center !important;
}
.manual a {
  text-decoration: none;
}
.manual a:hover {
  text-decoration: underline;
}
.manual h4 {
  font-size: 16px;
  color: #062c5e;
}
.manual p {
  color: #777777;
  margin: 0;
}
.t_cnt {
  color: #3dacfb;
}
.cols-2 {
  width: 860px !important;
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
  position: relative;
  position: fixed;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 800px;
  height: 490px;
  background: #fff;
  border-radius: 4px;
  padding: 30px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.35);
}
.container_notice h2 {
  border-bottom: 1px solid #777;
  padding: 0 0 10px 0;
  font-size: 20px;
  color: #222;
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
</style>
