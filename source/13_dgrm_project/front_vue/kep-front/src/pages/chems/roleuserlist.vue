<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--권한별 사용자 조회-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000074') }}</span>
    </div>
    <div class="work_title">
      <!--권한별 사용자 조회-->
      <h2>{{ $t('LB00000074') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--권한명-->
                <div class="label_tit">{{ $t('LB00000064') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.roleExplain"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--권한구분-->
                <div class="label_tit">{{ $t('LB00000063') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.roleTypeCode"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000010"
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
                <!--사번-->
                <div class="label_tit">{{ $t('LB00000073') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.userId"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="19"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--이름-->
                <div class="label_tit">{{ $t('LB00000072') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.userName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="99"
                  @keypress.enter="select()"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--부서-->
                <div class="label_tit">{{ $t('LB00000071') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.deptName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="99"
                  @keypress.enter="select()"
                ></v-text-field>
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
        <!--권한별 사용자 목록-->
        <h3>{{ $t('LB00000070') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

let gridView = GridView
let dataProvider = LocalDataProvider

const date = new Date()
date.setDate(date.getDate() - 7)

export default {
  meta: {
    title: '권한별 사용자 조회',
  },
  components: {
    // quillEditor,
  },
  data() {
    return {
      codeid: ['CO00000010'],
      codes: {
        CO00000010: [{ cd: '', cd_nm: '' }],
      },
      totalcnt: 0,
      searchdatas: {
        roleExplain: '',
        roleTypeCode: '',
        userId: '',
        userName: '',
        deptName: '',
      },
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  created() {},
  async mounted() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000010.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err)
      })
    dataProvider = new LocalDataProvider(false)
    dataProvider.setFields(fields)
    gridView = new GridView('realgrid')
    gridView.setDataSource(dataProvider)
    const columns = [
      {
        header: this.$t('LB00000063'), // 권한구분
        name: 'roleTypeCodeName',
        fieldName: 'roleTypeCodeName',
        width: '100',
      },
      {
        header: this.$t('LB00000064'), // 권한명
        name: 'roleExplain',
        fieldName: 'roleExplain',
        width: '100',
      },
      {
        header: this.$t('LB00000073'), // 사번
        name: 'userId',
        fieldName: 'userId',
        width: '100',
        type: 'button',
      },
      {
        header: this.$t('LB00000072'), // 이름
        name: 'userName',
        fieldName: 'userName',
        width: '100',
      },
      {
        header: this.$t('LB00000071'), // 부서
        name: 'deptName',
        fieldName: 'deptName',
        width: '100',
      },
      {
        header: this.$t('LB00000069'), // 직위
        name: 'jkwName',
        fieldName: 'jkwName',
        width: '100',
      },
      /* 
      {
        header: this.$t('LB00000068'), // 휴대 전화번호
        name: 'emp_cpho_no',
        fieldName: 'emp_cpho_no',
        width: '100',
        styleName: 'left',
        styles: {
          textAlignment: 'center',
        },
      },
      */
      {
        header: this.$t('LB00000066'), // 유효여부
        name: 'usageYn',
        fieldName: 'usageYn',
        width: '100',
      },
    ]
    gridView.setColumns(columns)
    gridView.setFooters({ visible: false })

    gridView.setStateBar({ visible: false })
    gridView.setCheckBar({ visible: false })
    gridView.rowIndicator.visible = true
    gridView.editOptions.editable = false
    gridView.displayOptions.selectionStyle = 'block'

    gridView.header.height = 39
    gridView.displayOptions.rowHeight = 40
    gridView.footer.height = 40
    gridView.displayOptions.fitStyle = 'fill'
    await this.select()
  },
  methods: {
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/user-role`, { params: this.searchdatas })

      dataProvider.setRows(rows)
      gridView.refresh()
      gridView.setTopItem(0)
      this.totalcnt = gridView.getItemCount()
    },
    reset() {
      gridView.commit()
      this.searchdatas = {}
    },
  },
}

// 그리드 세팅
const fields = [
  {
    fieldName: 'userId',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleNo',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleTypeCode',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleTypeCodeName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplain',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'roleExplainName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'userName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'jkwName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'departmentCode',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'deptName',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'emp_cpho_no',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'usageYn',
    dataType: ValueType.TEXT,
  },
]
</script>
<style lang="scss" scoped></style>
