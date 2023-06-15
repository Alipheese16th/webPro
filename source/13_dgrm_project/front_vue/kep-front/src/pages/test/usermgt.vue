<template>
  <div>
    <div class="location">
      <span>Home > 테스트 메뉴 > 사용자 관리 </span>
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
                <!--사용자ID-->
                <div class="label_tit"> 사용자ID </div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="searchdatas.userId" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용자명-->
                <div class="label_tit"> 사용자명 </div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="searchdatas.userName" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="99" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select v-model="searchdatas.useYn" label="ALL" item-text="cd_nm" item-value="cd" :items="use_yn_cd"
                  outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--사용자등록일-->
                <div class="label_tit"> 사용자등록일 </div>
              </v-col>
              <v-col cols="3">

              <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchdatas.sDate"
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
                  <v-date-picker v-model="searchdatas.sDate" :max="searchdatas.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="searchdatas.eDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="30"
                      v-bind="attrs"
                      class="comp_calendar"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="searchdatas.eDate" :min="searchdatas.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>


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
          <!--등록-->
          <v-btn color="" outlined height="28" @click="addUser"> 등록 </v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>

  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid'

export default {
  meta: {
    title: '사용자 관리',
    key(route) {
      return `/test/user`
    },
  },
  components: {
  },
  data() {
    return {

      savedatas: [],
      totalcnt: 0,
      searchdatas: {
        userId: '',
        userName: '',
        useYn: '',
        sDate: '',
        eDate: '',
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
        fieldName: 'userName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userEnglishName',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userRegistDate',
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'useYn',
        dataType: ValueType.TEXT,
      }
    ]

    this.grid.dataProvider = new LocalDataProvider(false)
    this.grid.dataProvider.setFields(this.grid.fields)
    this.grid.gridView = new GridView('realgrid')
    this.grid.gridView.setDataSource(this.grid.dataProvider)

    const columns = [
      {
        header: '사용자아이디', // 사번
        name: 'userId',
        fieldName: 'userId',
        width: '100',
        styleName: 'link left',
        editable: false,
      },
      {
        header: '사용자명', // 이름
        name: 'userName',
        fieldName: 'userName',
        width: '100',
        editable: false,
      },
      {
        header: '사용자영문명', // 사용자영문명
        name: 'userEnglishName',
        fieldName: 'userEnglishName',
        width: '140',
        editable: false,
      },
      {
        header: '사용자등록일', // 사용자등록일
        name: 'userRegistDate',
        fieldName: 'userRegistDate',
        width: '100',
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
      }
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


    // 검색데이터 유지
    //console.log(this.$route.params.schData);
    if (this.$route.params.schData !== null && this.$route.params.schData !== undefined) {
      this.searchdatas.userId = this.$route.params.schData.userId
      this.searchdatas.userName = this.$route.params.schData.userName
      this.searchdatas.useYn = this.$route.params.schData.useYn
      this.searchdatas.sDate = this.$route.params.schData.sDate
      this.searchdatas.eDate = this.$route.params.schData.eDate
    }

    this.select()

    // 그리드 내 사용자 id 클릭시 상세페이지로  
    this.grid.gridView.rowIndicator.visible = true
    this.grid.gridView.onCellClicked = this.rowClick;

  },

  methods: {
    rowClick(grid, clickData) {
      console.log(clickData.column);
      console.log(this.grid.rowData[clickData.dataRow]);
      if(clickData.column === 'userId'){
        this.$router.push({
          name: 'test-usermgt_more',
          params: {
            userData : this.grid.rowData[clickData.dataRow],
            schData: {
                userId: this.searchdatas.userId,
                userName: this.searchdatas.userName,
                useYn: this.searchdatas.useYn,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
              },
          },
        });
      }

    },
    addUser() {
      this.$router.push({
          name: 'test-usermgt_more',
          params: {
            schData: {
                userId: this.searchdatas.userId,
                userName: this.searchdatas.userName,
                useYn: this.searchdatas.useYn,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
              },
          },
        });
    },

    async select() {

      console.log('검색데이터 ');
      console.log(this.searchdatas);

      const rows = await this.$axios.$get(`/api/v1/test/user/list`, { params: this.searchdatas })
      this.grid.rowData = rows;
      this.grid.dataProvider.setRows(this.grid.rowData)
      this.grid.gridView.refresh()
      this.grid.gridView.setTopItem(0)
      this.totalcnt = this.grid.gridView.getItemCount()
    },
    reset() {

      this.grid.gridView.commit()
      this.searchdatas.userId = ''
      this.searchdatas.userName = ''
      this.searchdatas.useYn = ''
      this.searchdatas.sDate = ''
      this.searchdatas.eDate = ''

    },

  },
}
</script>
<style lang="scss" scoped></style>
