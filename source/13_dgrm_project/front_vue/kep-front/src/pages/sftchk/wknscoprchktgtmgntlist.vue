<template>
  <div>
    <div class="location">
      <!-- 안전 점검 및 개선 --><!-- 작업장 합동점검 대상 관리-->
      <span>Home > {{ $t('LB00000735') }} > {{ $t('LB00001011') }}</span>
    </div>
    <div class="work_title">
      <!-- 작업장 합동점검 대상 관리-->
      <h2>{{ $t('LB00001011') }}</h2>
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
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--사업장-->
                <div class="label_tit">{{ $t('LB00000021') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="wkpl_id"
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
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--점검명-->
                <div class="label_tit">{{ $t('LB00000640') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="jnt_chk_tgt_nm"
                  v-model="schData.jnt_chk_tgt_nm"
                  outlined
                  single-line
                  label="ALL"
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  @keyup.enter="selectJntTgtList"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--점검 담당자-->
                <div class="label_tit">{{ $t('LB00000679') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="jnt_chk_supe_emp_nm"
                  v-model="schData.jnt_chk_supe_emp_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  clearable
                  height="30"
                  class="icon_sch"
                  readonly
                  @click="popOpen"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--점검코스-->
                <div class="label_tit">{{ $t('LB00001013') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="jnt_chk_dtl_loc_nm"
                  v-model="schData.jnt_chk_dtl_loc_nm"
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
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="usg_yn"
                  v-model="schData.usg_yn"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="usg_yn_cd"
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
            <v-btn color="primary" depressed height="36" @click="selectJntTgtList">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--작업장 합동점검 대상 목록-->
        <h3>{{ $t('LB00001019') }}</h3>
      </div>
      <div class="grid_header">
        <!--총 건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }} {{ $t('LB00000040') }}</div>
        <div>
          <!--QR출력-->
          <!-- <v-btn color="primary" height="30">QR출력</v-btn> -->
          <!--등록-->
          <v-btn color="primary" height="30" @click="submit">{{ $t('LB00000111') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
      <popup ref="pop" mkey1="wknscoprchktgtmgntlist1" mkey2="wknscoprchktgtmgntlist2" @close="popClose" />
      <rgnslct ref="pop2" mkey="wknscoprchktgtmgntlist1" @close="popLocClose" />
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from '../common/userslct'
import rgnslct from '../common/rgnslct'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '작업장 합동점검 대상 관리',
    key(route) {
      return `/sftchk/wknscoprchktgtmgntlist/${route.params.catalog}`
    },
  },
  components: {
    // quillEditor,
    popup,
    rgnslct,
  },
  data() {
    return {
      schData: {
        usg_yn: 'Y',
      },
      frmData: {},
      tempData: {},
      size: 0,
      menu1: false,
      menu2: false,
      usg_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['ST00000002', 'CO00000009'],
      codes: {
        ST00000002: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      textRules: [(v) => !!v || '필수항목입니다.'],
      totalcnt: 0,
    }
  },
  async beforePageLeave(tab, type) {
    gridView.commit()
    if (dataProvider.getRowStateCount('all') - dataProvider.getRowStateCount('createAndDeleted') > 0 || JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
  computed: {
    routeTab() {
      return this.$t('LB00001011')
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {
    if (this.$route.params.tmpSchData !== null && this.$route.params.tmpSchData !== undefined) {
      this.schData = this.$route.params.tmpSchData
    } else {
      const user = this.$store.getters['auth/getUser']
      this.schData.cmpy_cd = user.cmpn_cd
      this.schData.wkpl_id = user.wkpl_id
    }
  },
  mounted() {
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
        this.codes.ST00000002.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
        // 그리드 세팅
        const fields = [
          {
            fieldName: 'jnt_chk_tgt_id',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'cmpy_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'wkpl_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_tgt_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_wkpl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_dtl_loc_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'jnt_chk_supe_emp_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'usg_yn',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'fscr_nm',
            dataType: ValueType.TEXT,
          },
          {
            fieldName: 'crt_dt',
            dataType: ValueType.TEXT,
          },
        ]

        const columns = [
          {
            header: 'ID', // ID
            name: 'jnt_chk_tgt_id',
            fieldName: 'jnt_chk_tgt_id',
            width: '100',
          },
          {
            header: this.$t('LB00000700'), // 회사
            name: 'cmpy_nm',
            fieldName: 'cmpy_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000021'), // 사업장
            name: 'wkpl_nm',
            fieldName: 'wkpl_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000640'), // 점검명
            name: 'jnt_chk_tgt_nm',
            fieldName: 'jnt_chk_tgt_nm',
            width: '200',
            styleName: 'link left',
          },
          {
            header: this.$t('LB00001013'), // 점검코스
            name: 'jnt_chk_dtl_loc_nm',
            fieldName: 'jnt_chk_dtl_loc_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000679'), // 점검 담당자
            name: 'jnt_chk_supe_emp_nm',
            fieldName: 'jnt_chk_supe_emp_nm',
            width: '200',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000049'), // 사용여부
            name: 'usg_yn',
            fieldName: 'usg_yn',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000055'), // 등록자
            name: 'fscr_nm',
            fieldName: 'fscr_nm',
            width: '100',
            styleName: 'left',
          },
          {
            header: this.$t('LB00000752'), // 등록일자
            name: 'crt_dt',
            fieldName: 'crt_dt',
            width: '100',
            styleName: 'left',
          },
        ]
        dataProvider = new LocalDataProvider(false)
        dataProvider.setFields(fields)
        gridView = new GridView('realgrid')
        gridView.setDataSource(dataProvider)
        gridView.setColumns(columns)
        gridView.setFooters({ visible: false })

        gridView.setStateBar({ visible: false })
        gridView.setCheckBar({ visible: true, width: 50 })
        gridView.setRowIndicator({ visible: false })
        gridView.editOptions.editable = false
        gridView.displayOptions.selectionStyle = 'singleRow'

        gridView.header.height = 39
        gridView.displayOptions.rowHeight = 40
        gridView.footer.height = 40
        gridView.displayOptions.fitStyle = 'fill'

        gridView.setColumnProperty('jnt_chk_tgt_id', 'visible', false)

        gridView.onCellClicked = function (grid, index, clickData) {
          gridView.commit()
          if (index.itemIndex > -1) this.openDetail(index)
        }.bind(this)

        this.selectJntTgtList()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    popOpen() {
      this.$refs.pop.open(this.frmData, false)
    },
    popLocOpen() {
      this.$refs.pop2.open(this.frmData, false)
    },
    popClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        returnData.forEach((i) => {
          this.$set(this.schData, 'jnt_chk_supe_emp_nm', i.emp_nm)
          this.$set(this.schData, 'jnt_chk_supe_emp_id', i.emp_no)
        })
      }
    },
    popLocClose(returnData) {
      if (returnData === 'undefined' || returnData === undefined) {
        return false
      } else {
        this.$set(this.schData, 'jnt_chk_wkpl_loc_nm', returnData.upp_incl_wkpl_loc_nm)
        this.$set(this.schData, 'jnt_chk_wkpl_loc_id', returnData.wkpl_loc_id)
      }
    },
    reset() {
      gridView.commit()
      this.schData = {
        cmpy_cd: '',
      }
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
    async selectJntTgtList() {
      if (this.isEmpty(this.schData.jnt_chk_supe_emp_nm)) {
        this.schData.jnt_chk_supe_emp_id = ''
      }
      const rows = await this.$axios.$get(`/api/v1/she/sftchk/wknscoprchktgtmgntlist`, { params: this.schData })
      this.tempData = JSON.parse(JSON.stringify(this.frmData))
      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    submit() {
      // eslint-disable-next-line no-console
      this.$router.push({
        name: 'sftchk-wknscoprchktgtmgntdtledt-wknscoprchktgtmgntdtledt',
        params: {
          tmpSchData: {
            wkpl_id: this.schData.wkpl_id,
            jnt_chk_tgt_nm: this.schData.jnt_chk_tgt_nm,
            jnt_chk_supe_emp_nm: this.schData.jnt_chk_supe_emp_nm,
            jnt_chk_supe_emp_id: this.schData.jnt_chk_supe_emp_id,
            jnt_chk_dtl_loc_nm: this.schData.jnt_chk_dtl_loc_nm,
            usg_yn: this.schData.usg_yn,
            cmpy_cd: this.schData.cmpy_cd,
          },
        },
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    openDetail(index) {
      if (index.itemIndex < 0) return
      if (index.column === 'jnt_chk_tgt_nm') {
        const value = gridView.getValue(index.itemIndex, 'jnt_chk_tgt_id')

        // 권한 체크
        if (!this.isEmpty(value)) {
          this.$axios.$get(`/api/v1/she/sftchk/auth-check`, { params: { gubun: 'JNTGT', jnt_chk_tgt_id: value } }).then((res) => {
            // eslint-disable-next-line no-console
            if (res === 'Success') {
              this.$router.push({
                name: `sftchk-wknscoprchktgtmgntdtledt-wknscoprchktgtmgntdtledt`,
                params: {
                  wknscoprchktgtmgntdtledt: value,
                  tmpSchData: {
                    wkpl_id: this.schData.wkpl_id,
                    jnt_chk_tgt_nm: this.schData.jnt_chk_tgt_nm,
                    jnt_chk_supe_emp_nm: this.schData.jnt_chk_supe_emp_nm,
                    jnt_chk_supe_emp_id: this.schData.jnt_chk_supe_emp_id,
                    jnt_chk_dtl_loc_nm: this.schData.jnt_chk_dtl_loc_nm,
                    usg_yn: this.schData.usg_yn,
                    cmpy_cd: this.schData.cmpy_cd,
                  },
                },
              })
            } else {
              this.$router.push({
                name: `sftchk-wknscoprchktgtmgntdtllup-wknscoprchktgtmgntdtllup`,
                params: {
                  wknscoprchktgtmgntdtllup: value,
                  tmpSchData: {
                    wkpl_id: this.schData.wkpl_id,
                    jnt_chk_tgt_nm: this.schData.jnt_chk_tgt_nm,
                    jnt_chk_supe_emp_nm: this.schData.jnt_chk_supe_emp_nm,
                    jnt_chk_supe_emp_id: this.schData.jnt_chk_supe_emp_id,
                    jnt_chk_dtl_loc_nm: this.schData.jnt_chk_dtl_loc_nm,
                    usg_yn: this.schData.usg_yn,
                    cmpy_cd: this.schData.cmpy_cd,
                  },
                },
              })
            }
          })
        }
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
