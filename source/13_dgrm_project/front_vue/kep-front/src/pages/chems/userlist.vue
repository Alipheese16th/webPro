<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--사용자 조회(KO)-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000104') }} &gt; </span>
    </div>
    <div class="work_title">
      <!--사용자 조회(KO)-->
      <h2>{{ $t('LB00000104') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--사번-->
                <div class="label_tit">{{ $t('LB00000073') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="searchdatas.userId" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--이름-->
                <div class="label_tit">{{ $t('LB00000072') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="searchdatas.userName" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="99" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="searchdatas.departmentName" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="99" @keypress.enter="select()"></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select v-model="searchdatas.useYn" label="ALL" item-text="cd_nm" item-value="cd" :items="use_yn_cd"
                  outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
              <v-col cols="1">
                <!--삭제여부-->
                <div class="label_tit">{{ $t('LB00000742') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select v-model="searchdatas.delYn" label="ALL" item-text="cd_nm" item-value="cd" :items="del_yn_cd"
                  outlined single-line :hide-details="true" dense height="30" maxlength="99"></v-select>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="reset">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="select">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="sub_title">
        <!--사용자 목록-->
        <h3>{{ $t('LB00000061') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="28" @click="addUser">{{ $t('LB00000047') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
    </div>
    <popup :infoData="dialog.frmData" :isOpen="dialog.isOpen" :isModify="dialog.isModify" @close="close" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'
import popup from "./userinfo";

export default {
  meta: {
    title: '사용자조회',
  },
  components: {
    popup
  },
  data() {
    return {
      dialog: {
        frmData: {},
        isOpen: false,
        isModify: false
      },
      savedatas: [],
      totalcnt: 0,
      searchdatas: {
        companyCode: '',
        wkpl_id: '',
        userId: '',
        userName: '',
        departmentName: '',
        useYn: '',
        delYn: '',
      },
      grid: {
        dataProvider: null,
        gridView: null,
        field: [],
        rowData: []
      },
      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      del_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      menu1: false,
      menu2: false,
    }
  },
  async beforePageLeave(tab, type) {
    this.grid.gridView.commit()
    if (this.grid.dataProvider.getRowStateCount('all') - this.grid.dataProvider.getRowStateCount('createAndDeleted') > 0) {
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
    // setTimeout(() => console.log("created"), 1000);
    console.log("created");
    this.user = this.$store.getters['auth/getUser']

    // 공통코드 조회
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.SH00000042.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  mounted() {
    console.log("mounted");
    // setTimeout(() => console.log("mounted"), 1000);
    // 그리드 세팅
    this.grid.fields = [
      {
        fieldName: 'userId',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userKoreanName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'departmentCode',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'jkwCode',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'jkwName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'jkcCode',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'jkcName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userEmail',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userCellPhoneNo',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'useYn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'delYn',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lastLoginDateTime',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'lastLoginIpAddress',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: "roleCode",
        dataType: ValueType.TEXT
      },
      {
        fieldName: "roleName",
        dataType: ValueType.TEXT
      }
    ]

    this.grid.dataProvider = new LocalDataProvider(false)
    this.grid.dataProvider.setFields(this.grid.fields)
    this.grid.gridView = new GridView('realgrid')
    this.grid.gridView.setDataSource(this.grid.dataProvider)

    const columns = [
      {
        header: this.$t('LB00000073'), // 사번
        name: 'userId',
        fieldName: 'userId',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000072'), // 이름
        name: 'userKoreanName',
        fieldName: 'userKoreanName',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000071'), // 부서
        name: 'departmentCode',
        fieldName: 'departmentCode',
        width: '140',
        editable: false,
      },
      {
        header: '직위 코드', // 직위 코드
        name: 'jkwCode',
        fieldName: 'jkwCode',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000069'), // 직위
        name: 'jkwName',
        fieldName: 'jkwName',
        width: '100',
        editable: false,
      },
      {
        header: '직책 코드', // 직책 코드
        name: 'jkcCode',
        fieldName: 'jkcCode',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000766'), // 직책
        name: 'jkcName',
        fieldName: 'jkcName',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000709'), // 이메일
        name: 'userEmail',
        fieldName: 'userEmail',
        width: '190',
        editable: false,
      },
      {
        header: "권한코드", // TODO: 번역 필요
        name: "roleCode",
        fieldName: "roleCode",
        width: "190",
        editable: false,
      },
      {
        header: "권한명", // TODO: 번역 필요
        name: "roleName",
        fieldName: "roleName",
        width: "190",
        editable: false,
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'useYn',
        fieldName: 'useYn',
        width: '100',
        sortable: true,
        lookupDisplay: true,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          values: ['Y', 'N'],
          labels: ['Y', 'N'],
        },
      },
      {
        header: this.$t('LB00000742'), // 삭제여부
        name: 'delYn',
        fieldName: 'delYn',
        width: '100',
        sortable: true,
        lookupDisplay: true,
        editor: {
          type: 'dropdown',
          domainOnly: true,
          textReadOnly: true,
          values: ['Y', 'N'],
          labels: ['Y', 'N'],
        },
      },
      {
        header: '최근 로그인 일자', // 로그인 일자
        name: 'lastLoginDateTime',
        fieldName: 'lastLoginDateTime',
        width: '180',
        editable: false,
      },
      {
        header: '최근 로그인 IP 주소', // 로그인 ip 주소
        name: 'lastLoginIpAddress',
        fieldName: 'lastLoginIpAddress',
        width: '180',
        editable: false,
      },
    ]
    this.grid.gridView.setColumns(columns)
    this.grid.gridView.setFooters({ visible: false })
    this.grid.gridView.setStateBar({ visible: false })
    this.grid.gridView.setCheckBar({ visible: false })
    this.grid.gridView.editOptions.editable = true
    this.grid.gridView.displayOptions.selectionStyle = 'block'

    this.grid.gridView.header.height = 39
    this.grid.gridView.displayOptions.rowHeight = 40
    this.grid.gridView.footer.height = 40
    this.grid.gridView.displayOptions.fitStyle = 'fill'
    this.select()

    this.grid.gridView.rowIndicator.visible = true

    this.grid.gridView.onCellDblClicked = this.rowClick;
  },
  methods: {
    rowClick(grid, clickData) {
      this.dialog.isModify = true;
      this.dialog.frmData = this.grid.rowData[clickData.dataRow];
      this.dialog.isOpen = true;
    },
    addUser() {
      this.dialog.frmData = {};
      this.dialog.isModify = false;
      this.dialog.isOpen = true;
      // this.$refs.pop.open(false, this.frmData, '')
    },
    close() {
      this.dialog.isOpen = false;
      this.select();
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/user`, { params: this.searchdatas })

      this.grid.rowData = rows;

      this.grid.dataProvider.setRows(this.grid.rowData)
      this.grid.gridView.refresh()
      this.grid.gridView.setTopItem(0)
      this.totalcnt = this.grid.gridView.getItemCount()
    },
    reset() {
      this.grid.gridView.commit()
      this.searchdatas = {}
      this.F_codes.F_00000001 = 'ALL'
    },
    codChg(data) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'ALL', up_cd: '' })
      this.$set(this.searchdatas, 'wkpl_id', '')
    },
    async save() {
      this.grid.gridView.commit()
      if (this.grid.dataProvider.getRowStateCount('all') - this.grid.dataProvider.getRowStateCount('createAndDeleted') === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        return false
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        this.select()
        return false
      } else {
        const lowstatus = this.grid.dataProvider.getAllStateRows() // 수정된 항목의 row index전부 가져오기
        lowstatus.updated.forEach((i) => {
          this.savedatas.push(this.grid.dataProvider.getJsonRows(i, i))
        })
        this.saveparams = {}
        this.savedatas = [] //  저장될 배열 생성
        this.saveparams.savedatas = this.savedatas
        await this.$axios.post('/api/v1/common/chems/user', this.saveparams).then((res) => {
          if (res.data.result === 'Success') {
            this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          } else {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
          }
          this.select()
        })
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
