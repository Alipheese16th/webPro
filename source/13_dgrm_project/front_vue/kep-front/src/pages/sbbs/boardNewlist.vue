<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--공지사항(KO)-->
      <span>Home > 샘플게시판New </span>
    </div>
    <div class="work_title">
      <!--공지사항(KO)-->
      <h2>샘플게시판New</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}  new</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="search.title" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="199" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--내용-->
                <div class="label_tit">{{ $t('LB00000113') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="search.content" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" @keypress.enter="select()"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
              </v-col>
              <v-col cols="3" style="display: flex">
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="search.sDate" prepend-icon="event" readonly outlined :hide-details="true" dense
                      height="30" v-bind="attrs" class="comp_calendar mr-1" v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="search.sDate" :max="search.eDate" no-title @input="menu1 = false">
                  </v-date-picker>
                </v-menu>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="search.eDate" prepend-icon="event" readonly outlined :hide-details="true" dense
                      height="30" v-bind="attrs" class="comp_calendar" v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="search.eDate" :min="search.sDate" no-title @input="menu2 = false">
                  </v-date-picker>
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
            <v-btn color="primary" depressed height="36" @click="searchList">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="realgrid_container">
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <div>
            <!--등록-->
            <v-btn color="primary" depressed height="30" @click="create">{{ $t('LB00000111') }}</v-btn>
          </div>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
  </div>
</template>

<script>
import { GridView, LocalDataProvider } from 'realgrid'

export default {
  meta: {
    title: '샘플 게시판 목록',
    key(route) {
      return `/sbbs/${route.params.catalog}`
    },
  },
  data: () => ({
    search: {
      title: "",
      content: "",
      sDate: "",
      eDate: ""
    },
    totalcnt: 0,
    menu1: false,
    menu2: false,
    grid: {
      gridView: null,
      dataProvider: null,
      columns: [],
      fields: [],
      columnlayout: [],
      rows: []
    }
  }),
  async fetch() {
    console.log(this.search.title);

    const data = await this.$axios.$get("/api/v1/public/sbbsNew/list", { params:{
        title: this.search.title,
        user: this.search.user
  }});
    
    console.log(data);
    
    this.grid.rows = data;
    this.grid.dataProvider.setRows(this.grid.rows);
  },
  created() {
    this.grid.fields = [
      {
        fieldName: "boardId",
        dateType: "text"
      },
      {
        fieldName: "title",
        dataType: "text"
      },
      {
        fieldName: "createUser",
        dataType: "text"
      },
      {
        fieldName: "createDate",
        dataType: "date",
        datetimeFormat: "yyyy-MM-dd",
        amText: "오전",
        pmText: "오후"
      },
    ];

    this.grid.columns = [
      {
        name: "boardId",
        fieldName: "boardId",
        header: {
          text: "게시판아이디"
        }
      },
      {
        name: "title",
        fieldName: "title",
        width: "250",
        header: {
          text: "제목"
        },
      },
      {
        name: "createUser",
        fieldName: "createUser",
        header: {
          text: "작성자"
        }
      },
      {
        name: "createDate",
        fieldName: "createDate",
        header: {
          text: "등록일"
        }
      },
    ]

    this.grid.columnlayout = [
      "boardId",
      "title",
      "createUser",
      "createDate",
    ]
  },
  mounted() {
    this.grid.dataProvider = new LocalDataProvider(false);
    this.grid.gridView = new GridView("realgrid");
    this.grid.gridView.setDataSource(this.grid.dataProvider);
    this.grid.gridView.setFooters({ visible: false });
    this.grid.gridView.setStateBar({ visible: false });
    this.grid.gridView.setCheckBar({ visible: false });
    this.grid.gridView.editOptions.editable = false;
    this.grid.gridView.header.height = 39;
    this.grid.gridView.displayOptions.rowHeight = 40;
    this.grid.gridView.footer.height = 40;
    this.grid.gridView.displayOptions.fitStyle = 'fill';

    this.grid.gridView.setColumns(this.grid.columns);

    // 컬럼헤더를 custom하기 위한 값.
    // this.grid.gridView.setColumnLayout(this.grid.columnlayout);

    // columns의 fieldName으로 제어됨.
    this.grid.dataProvider.setFields(this.grid.fields);
    // console.log(this.grid.rows);

    // this.grid.dataProvider.setRows(this.grid.rows);

    /**
     * realGrid는 컬럼이 기본적으로 edit가능하도록 되어있으며
     * 수정 불가 기능옵션은 컬럼을 일일이 처리해주어야 한다.
     */
    // this.grid.gridView.columnByName("boardId").editable = false;

    /**
     * column전체 editable 제어
     * 필수값인 field를 가지고 제어처리함.
     */
    // this.grid.fields.forEach(f => {
    //   this.grid.gridView.columnByName(f.fieldName).editable = false;
    // })

    this.grid.gridView.onCellDblClicked = function (grid, clickData) {
      // console.log(this.grid.rows[clickData.dataRow]);
      const boardId = this.grid.rows[clickData.dataRow].boardId;
      console.log(boardId);

      this.$router.push({
        name: "sbbs-boardedt-boardNewEdt",
        params: {
          boardedt: boardId,
        }
      })
    }.bind(this);
  },
  methods: {
    searchList() {
      this.$fetch();
    },
    reset() {
      this.search = {
        title: "",
        content: "",
        sDate: "",
        eDate: ""
      }
    },
    create() {
      this.$router.push({
        name: "sbbs-boardedt-boardNewEdt",
      })
    }
  },
}
</script>
<style lang="scss" scoped></style>
