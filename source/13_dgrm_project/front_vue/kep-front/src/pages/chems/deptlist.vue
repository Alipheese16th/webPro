<template>
  <div>
    <!-- [start breadcrumb] -->
    <!-- TODO (번역필요) 부서관리 -->
    <div class="location">
      <span>Home > {{ $t('LB00000042') }} > 부서관리</span>
    </div>
    <div class="work_title">
      <h2>부서관리</h2>
    </div>
    <!-- [end breadcrumb] -->
    <!-- [start realgrid container] -->
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--부서명-->
                <div class="label_tit">{{ $t('LB00000108') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="searchdatas.departmentName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  maxlength="99"
                  @click:clear="onClearClicked"
                  @keypress.enter="searchDept"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <!--초기화-->
            <v-btn color="" outlined height="36" @click="onClearClicked">{{ $t('LB00000029') }}</v-btn>
            <!--검색-->
            <v-btn color="primary" depressed height="36" @click="searchDept">{{ $t('LB00000030') }}</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <br />
    <div>
      <div class="container_pop_section">
        <div class="sub_title">
          <!-- TODO (번역필요) 부서 목록 -->
          <h3>부서 목록</h3>
        </div>
        <div class="grid_header">
          <!--총--><!--건-->
          <!-- <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div> -->
        </div>
        <v-row>
          <v-col>
            <div v-show="treeYn" class="container_tree" style="height: 420px">
              <v-treeview ref="treeview" activatable color="warning" :open-all="expanded" :items="items">
                <template slot="label" slot-scope="{ item }">
                  <a @click="deptTreeClick(item)">{{ item.departmentName }}</a>
                </template>
              </v-treeview>
            </div>
            <div v-show="!treeYn" id="deptgrid" style="height: 420px"></div>
          </v-col>
        </v-row>
      </div>
    </div>
    <!-- [end realgrid container] -->
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid';
export default {
  meta: {
    title: '시스템관리-부서관리',
  },
  components: {},
  props: {
    mkey1: {
      type: String,
      default: 'deptgrid',
    },
    mkey2: {
      type: String,
      default: 'usrgrid',
    },
  },
  data() {
    return {
      deptgridView: '',
      deptdataProvider: '',
      usrgridView: '',
      usrdataProvider: '',
      dialog: false,
      multiYn: false, // 다중선택 여부 true = 다중선택 가능
      expanded: false,
      frmData: {},
      treeYn: true,
      items: [], // 트리데이터
      searchdatas: {
        departmentName: '',
        usr_nm: '',
        departmentCode: '',
      },
      totalcnt: 0,
    };
  },
  mounted() {
    this.deptdataProvider = new LocalDataProvider(false);
    this.deptdataProvider.setFields(deptfields);
    const gridId1 = document.getElementById('deptgrid');
    gridId1.id = this.mkey1 + 'userslct';
    this.deptgridView = new GridView(this.mkey1 + 'userslct');
    this.deptgridView.setDataSource(this.deptdataProvider);
    const deptcolumns = [
      {
        header: this.$t('LB00000108'), // 부서명
        name: 'departmentName',
        fieldName: 'departmentName',
        width: '50',
        styleName: 'left',
      },
    ];
    this.deptgridView.setColumns(deptcolumns);
    this.deptgridView.setFooters({ visible: false });

    this.deptgridView.setStateBar({ visible: false });
    this.deptgridView.setCheckBar({ visible: false });
    this.deptgridView.editOptions.editable = false;
    this.deptgridView.displayOptions.selectionStyle = 'block';

    this.deptgridView.header.height = 39;
    this.deptgridView.displayOptions.rowHeight = 40;
    this.deptgridView.footer.height = 40;
    this.deptgridView.displayOptions.fitStyle = 'fill';
    this.deptgridView.onCellClicked = function (grid, index, clickData) {};
    this.selectDeptTree();
  },
  methods: {
    async selectDeptTree() {
      const data = await this.$axios.$get(`/api/v1/common/chems/user/dept-tree-new`);
      const treedata = await this.listToTree(data);
      this.items = await treedata;
    },
    async listToTree(list) {
      const map = {};
      let node;
      const roots = [];
      let i;
      for (i = 0; i < list.length; i += 1) {
        map[list[i].departmentCode] = i;
        list[i].children = [];
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.upDepartmentCode !== '$0') {
          await list[map[node.upDepartmentCode]].children.push(node);
        } else {
          await roots.push(node);
        }
      }
      return roots;
    },
    onClearClicked() {
      this.treeYn = true;
      this.searchdatas.departmentCode = '';
      this.searchdatas.departmentName = '';
      this.deptgridView.refresh();
    },
    async deptTreeClick(item) {
      this.searchdatas.departmentCode = await item.id;
    },
    async searchDept() {
      if(this.searchdatas.departmentName){
        this.treeYn = false;
        const rows = await this.$axios.$get(`/api/v1/common/chems/user/dept-search-new`, {
          params: {
            departmentName: this.searchdatas.departmentName,
          },
        });
        this.deptdataProvider.setRows(rows);
        this.deptgridView.refresh();
        this.deptgridView.setTopItem(0);
      } else {
        this.treeYn = true;
        const rows = await this.$axios.$get(`/api/v1/common/chems/user/dept-search-new`, {
          params: {
            departmentName: this.searchdatas.departmentName,
          },
        });
        this.deptdataProvider.setRows(rows);
        this.deptgridView.refresh();
        this.deptgridView.setTopItem(0);
      }
    },
  },
};
// 그리드 세팅
const deptfields = [
  {
    fieldName: 'departmentCode',
    dataType: ValueType.TEXT,
  },
  {
    fieldName: 'departmentName',
    dataType: ValueType.TEXT,
  },
];
</script>
