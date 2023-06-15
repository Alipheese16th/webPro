<template>
  <div class="home_container">
    <p>Login Sample</p>
    <p>
      <v-form @submit.prevent="loginSubmit">
        <v-container>
          <v-row>
            <v-col cols="4">
              <v-text-field name="username" clearable label="userId" density="compact" variant="solo" single-line
                hide-details v-model="loginForm.username" />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="4">
              <v-text-field name="password" type="password" clearable label="password" density="compact" variant="solo"
                single-line hide-details v-model="loginForm.password" />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="8">
              <v-btn variant="tonal" type="submit">
                로그인
              </v-btn>
            </v-col>
            <v-col cols="12">
              <v-btn variant="tonal" @click="loginCheck">
                로그인 확인
              </v-btn>
            </v-col>
            <v-col cols="12">
              {{ loginUser }}
            </v-col>
          </v-row>
        </v-container>
      </v-form>
    </p>
    <p>
      <ckeditor-nuxt v-model="mailData.email_cntn" type="classic" :config="editorConfig" />
    </p>
    <br />
    <br />
    <p>{{ mailData.email_cntn }}</p>
    <br />
    <br />
    <div id="sampleGrid" style="width: 100%; height: 394px"></div>
    <br />
    <br />
    <div id="treeGrid" style="width: 100%; height: 394px"></div>
    <br />
    <br />
    <div id="pivotGrid" style="width: 100%; height: 394px"></div>
  </div>
</template>
<script>
import { GridView, LocalDataProvider, LocalTreeDataProvider, TreeView } from 'realgrid'

export default {
  meta: {
    title: 'Home',
  },
  components: {
    'ckeditor-nuxt': () => {
      if (process.client) {
        return import('@blowstack/ckeditor-nuxt')
      }
    },
  },
  data: () => ({
    loginForm: {
      username: "",
      password: "",
    },
    loginUser: "",
    mailData: {
      email_ttl: '',
      email_cntn: '<h1>&nbsp;</h1><p><span class="text-tiny">이준호 팀장 LKBT 회사 서명 저장</span></p>',
    },
    editorConfig: {
      // The configuration of the editor.
      height: '500px',
      language: 'ko',
    },
    grid: {
      gridView: null,
      dataProvider: null
    },
    tree: {
      treeView: null,
      dataProvider: null
    },
    pivot: {
      pivotView: null,
      dataProvider: null
    }
  }),
  mounted() {
    // realgrid는 dom구성이 완료된 후 초기화가 가능하기 때문에 mounted에서 처리.
    // ---------- 일반 grid
    this.grid.dataProvider = new LocalDataProvider(false);
    this.grid.gridView = new GridView("sampleGrid");
    this.grid.gridView.setDataSource(this.grid.dataProvider);

    this.grid.gridView.setDisplayOptions({
      rowHeight: 20
    })

    this.grid.gridView.setColumns([
      {
        name: "title",
        fieldName: "title",
        header: {
          text: "제목"
        }
      },
      {
        name: "name",
        fieldName: "name",
        header: {
          text: "이름"
        }
      },
      {
        name: "content",
        fieldName: "content",
        header: {
          text: "내용"
        }
      },
      {
        name: "createDate",
        fieldName: "createDate",
        header: {
          text: "등록일"
        }
      },
      {
        name: "2023-01-01",
        fieldName: "2023-01-01",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "ssss") {
              return `
                <div style='width: 200px;height:23px;background: red;'>
                  <span class="material-symbols-rounded">
                    description
                  </span>
                </div>
              `
            } else {
              return "<div style='width: 200px;height:23px;background: red;' />"
            }
          }
        }
      },
      {
        name: "2023-01-02",
        fieldName: "2023-01-02",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "ssss") {
              return `
                <div style='width: 200px;height:23px;background: red;'>
                  <span class="material-symbols-rounded">
                    description
                  </span>
                </div>
              `
            } else {
              return "<div style='width: 200px;height:23px;background: red;' />"
            }
          }
        }
      },
      {
        name: "2023-01-03",
        fieldName: "2023-01-03",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            return "<div style='width: 200px;height:23px;background: red;' />"
          }
        }
      },
      {
        name: "2023-01-04",
        fieldName: "2023-01-04",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            return "<div style='width: 200px;height:23px;background: red;' />"
          }
        }
      },
      {
        name: "2023-01-05",
        fieldName: "2023-01-05",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            return "<div style='width: 200px;height:23px;background: red;' />"
          }
        }
      },
      {
        name: "2023-01-06",
        fieldName: "2023-01-06",
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            return "<div style='width: 200px;height:23px;background: red;' />"
          }
        }
      },
    ])

    // columns의 name으로 제어처리됨.
    this.grid.gridView.setColumnLayout([
      "title",
      "name",
      "content",
      "createDate",
      {
        name: "WBS",
        direction: "horizontal",
        width: 600,
        items: [
          { column: "2023-01-01", width: 100 },
          { column: "2023-01-02", width: 100 },
          { column: "2023-01-03", width: 100 },
          { column: "2023-01-04", width: 100 },
          { column: "2023-01-05", width: 100 },
          { column: "2023-01-06", width: 100 },
        ]
      }
    ])

    // columns의 fieldName으로 제어됨.
    this.grid.dataProvider.setFields([
      {
        fieldName: "title",
        dataType: "text"
      },
      {
        fieldName: "name",
        dataType: "text"
      },
      {
        fieldName: "content",
        dataType: "text"
      },
      {
        fieldName: "createDate",
        dataType: "date"
      },
      {
        fieldName: "2023-01-01",
        dataType: "text"
      },
      {
        fieldName: "2023-01-02",
        dataType: "text"
      },
      {
        fieldName: "2023-01-03",
        dataType: "text"
      },
      {
        fieldName: "2023-01-04",
        dataType: "text"
      },
      {
        fieldName: "2023-01-05",
        dataType: "text"
      },
      {
        fieldName: "2023-01-06",
        dataType: "text"
      },
    ])

    this.grid.dataProvider.setRows([
      {
        title: "tttttt",
        name: "이현철1",
        content: "123123123123",
        createDate: "2022-00-00",
        '2023-01-01': "ssss"
      },
      {
        title: "tttttt",
        name: "이현철2",
        content: "123123123123",
        createDate: "2022-00-00",
        '2023-01-02': "ssss"
      }
    ])

    // ---------- tree
    this.tree.dataProvider = new LocalTreeDataProvider(false);
    this.tree.treeView = new TreeView("treeGrid");
    this.tree.treeView.setDataSource(this.tree.dataProvider);

    this.tree.treeView.setColumns([
      {
        name: "depth",
        fieldName: "depth",
        width: "200",
        header: {
          text: "Hierarchy"
        }
      },
      {
        name: "name",
        fieldName: "name",
        header: {
          text: "Responsible Person"
        }
      },
      {
        name: "06월",
        fieldName: "06월",
        header: {
          name: "06월"
        },
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "1") {
              return "<div style='width: 200px;height:23px;background: red;' />"
            } else {
              return "<div></div>";
            }
          }
        }
      },
      {
        name: "04월",
        fieldName: "04월",
        header: {
          name: "04월"
        },
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "1") {
              return "<div style='width: 200px;height:23px;background: red;' />"
            } else {
              return "<div></div>";
            }
          }
        }
      },
      {
        name: "05월",
        fieldName: "05월",
        header: {
          name: "05월"
        },
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "1") {
              return "<div style='width: 200px;height:23px;background: red;' />"
            } else {
              return "<div></div>";
            }
          }
        }
      },
      {
        name: "07월",
        fieldName: "07월",
        header: {
          name: "07월"
        },
        renderer: {
          type: "html",
          callback: function (grid, cell, w, h) {
            // field value 기준으로 예외처리.
            if (cell.value === "1") {
              return "<div style='width: 200px;height:23px;background: red;' />"
            } else {
              return "<div></div>";
            }
          }
        }
      }
    ])

    // columns의 name으로 제어처리됨.
    this.tree.treeView.setColumnLayout([
      "depth",
      "name",
      {
        name: "2022년",
        direction: "horizontal",
        width: 600,
        items: [
          { column: "06월", width: 100 },
        ]
      },
      {
        name: "2023년",
        direction: "horizontal",
        width: 600,
        items: [
          { column: "04월", width: 100 },
          { column: "05월", width: 100 },
          { column: "07월", width: 100 },
        ]
      }
    ])

    // columns의 fieldName으로 제어됨.
    this.tree.dataProvider.setFields([
      {
        fieldName: "depth",
        dataType: "text"
      },
      {
        fieldName: "name",
        dataType: "text"
      },
      {
        fieldName: "06월",
        dataType: "text"
      },
      {
        fieldName: "04월",
        dataType: "text"
      },
      {
        fieldName: "05월",
        dataType: "text"
      },
      {
        fieldName: "07월",
        dataType: "text"
      },
    ])

    this.tree.dataProvider.setObjectRows({
      rows: [
        {
          depth: "Depth 1",
          name: "이현철",
          "04월": "1",
          "05월": "1",
          "06월": "1",
          "07월": "1",
          rows: [
            {
              depth: "Depth 2",
              name: "박미리",
              "06월": "1"
            },
          ]
        },
        {
          depth: "Depth 1",
          name: "이준호",
          "07월": "1",
          rows: [
            {
              depth: "Depth 2",
              name: "이해민",
              "04월": "1",
            },
            {
              depth: "Depth 2",
              name: "임다영",
              "05월": "1",
              rows: [
                {
                  depth: "Depth 3",
                  name: "권구현",
                  "06월": "1",
                  "04월": "1",
                  "05월": "1",
                  "07월": "1"
                },
              ]
            },
          ]
        },
      ]
    }, "rows", "", "");

    this.tree.treeView.refresh();

    // ---------- pivot
    this.pivot.dataProvider = new LocalDataProvider();
    this.pivot.pivotView = new RealPivot("pivotGrid");
    this.pivot.pivotView.setDataProvider(this.pivot.dataProvider);

    this.pivot.dataProvider.setFields([
      {
        fieldName: "Area"
      },
      {
        fieldName: "Depth1"
      },
      {
        fieldName: "Depth2"
      },
      {
        fieldName: "Depth3"
      },
      {
        fieldName: "name"
      },
      {
        fieldName: "year"
      },
      {
        fieldName: "month"
      },
      {
        fieldName: "day"
      },
      {
        fieldName: "count",
        dataType: "number"
      }
    ])

    this.pivot.pivotView.setFieldMapping([
      {
        name: "Area",
        sourceField: "Area",
        fieldHeader: "Area"
      },
      {
        name: "Depth1",
        sourceField: "Depth1",
        fieldHeader: "Depth1"
      },
      {
        name: "Depth2",
        sourceField: "Depth2",
        fieldHeader: "Depth2"
      },
      {
        name: "Depth3",
        sourceField: "Depth3",
        fieldHeader: "Depth3"
      },
      {
        name: "name",
        sourceField: "name",
        fieldHeader: "name"
      },
      {
        name: "year",
        sourceField: "year",
        dateType: "year",
        fieldHeader: "년",
        displayFormat: "${value}년",
      },
      {
        name: "month",
        sourceField: "month",
        dateType: "month",
        fieldHeader: "월",
        displayFormat: "${value}월",
      },
      {
        name: "count",
        sourceField: "count",
        fieldHeader: "count",
      },
    ]);

    this.pivot.pivotView.setPivotFields({
      columns: ["year", "month"],
      rows: ["Area", "Depth1", "Depth2", "Depth3", "name"],
      values: [
        {
          name: "count",
          expression: "sum"
        }
      ]
    });

    this.pivot.dataProvider.setRows([
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2",
        "Depth3": "Depth3",
        name: "이현철",
        "year": "2023",
        "month": "05",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1 ",
        "Depth2": "Depth2 ",
        "Depth3": "Depth3 ",
        name: "이준호",
        "year": "2023",
        "month": "05",
        count: 2
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1 ",
        "Depth2": "Depth2 ",
        "Depth3": "Depth3",
        name: "박미리",
        "year": "2023",
        "month": "04",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2 ",
        "Depth3": "Depth3",
        name: "임다영",
        "year": "2023",
        "month": "07",
        count: 4
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2 ",
        "Depth3": "Depth3",
        name: "이해민",
        "year": "2023",
        "month": "05",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1 ",
        "Depth2": "Depth2",
        "Depth3": "Depth3",
        name: "권구현",
        "year": "2023",
        "month": "05",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2",
        "Depth3": "Depth3 ",
        name: "홍성아",
        "year": "2023",
        "month": "05",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2",
        "Depth3": "Depth3",
        name: "이준호",
        "year": "2023",
        "month": "05",
        count: 1
      },
      {
        "Area": "Package1",
        "Depth1": "Depth1",
        "Depth2": "Depth2",
        "Depth3": "Depth3",
        name: "이현철",
        "year": "2022",
        "month": "06",
        count: 1
      },
    ])

    this.pivot.pivotView.setSummaryOptions({
      columnVisible: false,
      rowVisible: true,
      columnGroupVisible: false,
      rowGroupVisible: false
    })

    var styleCallback = function (pivot, index, value) {
      if (value || 0 > 0) {
        return "background-high";
      }

    };

    this.pivot.pivotView.setDisplayOptions({ styleCallback: styleCallback });

    this.pivot.pivotView.drawView();

  },
  methods: {
    loginSubmit() {

      const form = new FormData();
      form.append("username", this.loginForm.username);
      form.append("password", this.loginForm.password);

      this.$axios.$post(`/api/v1/common/loginprocess`, form, {
        headers: {
          "content-type": "multipart/form-data"
        }
      })
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        })
    },
    loginCheck() {
      this.$axios.$get("/api/v1/login/session")
        .then(result => {
          this.loginUser = result;
        }).catch(err => {
          console.log(err);
        })
    }
  }
}
</script>
<style scoped>
.router-tab__container {
  background: #ecedf2 !important;
}

.rg_renderer {
  padding: 0 !important;
}
</style>
