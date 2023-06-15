<template>
  <div>
    <div class="location">
      <span>Home > {{ getCurrent.subTitle }} > {{ getCurrent.menuName }}</span>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <h2>컴퍼넌트 가이드</h2>
          <v-container>
            <v-row>
              <v-col cols="1">
                <div class="sch_tit">TextFiled</div>
              </v-col>
              <v-col cols="3">
                <v-text-field outlined single-line :hide-details="true" dense placeholder="입력하세요." clearable height="30"></v-text-field>
              </v-col>
              <v-col cols="1">
                <div class="sch_tit">ComboBox</div>
              </v-col>
              <v-col cols="3">
                <v-select outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
              <v-col cols="1">
                <div class="sch_tit">FileField</div>
              </v-col>
              <v-col cols="3">
                <v-file-input multiple outlined single-line :hide-details="true" dense prepend-icon="" height="30"></v-file-input>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="sch_tit">Checkbox</div>
              </v-col>
              <v-col cols="3">
                <v-container>
                  <v-row>
                    <v-col cols="6">
                      <v-checkbox v-model="loading" label="Loading" height="30" :hide-details="true"></v-checkbox>
                    </v-col>
                    <v-col cols="6">
                      <v-checkbox v-model="flat" label="Flat" height="30" :hide-details="true"></v-checkbox>
                    </v-col>
                  </v-row>
                </v-container>
              </v-col>
              <v-col cols="1">
                <div class="sch_tit">Radio Button</div>
              </v-col>
              <v-col cols="3">
                <v-radio-group v-model="row" row>
                  <v-radio label="Option 1" value="radio-1" :hide-details="true"></v-radio>
                  <v-radio label="Option 2" value="radio-2" :hide-details="true"></v-radio>
                </v-radio-group>
              </v-col>
              <v-col cols="1">
                <div class="sch_tit">Calendar</div>
              </v-col>
              <v-col cols="3">
                <v-menu v-model="menu" :close-on-content-click="false" :nudge-right="40" min-width="290px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="date"
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
                  <v-date-picker v-model="date" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <div class="sch_tit">Textarea</div>
              </v-col>
              <v-col cols="11">
                <v-textarea
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  name="input-7-4"
                  label="Outlined textarea"
                  value="The Woodman set to work at once, and so sharp was his axe that the tree was soon chopped nearly through."
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="2">
          <div class="sch_btn">
            <v-btn color="" outlined height="36">초기화</v-btn>
            <v-btn color="primary" depressed height="36">검색</v-btn>
          </div>
        </v-col>
      </v-row>
    </div>
    <div class="realgrid_container">
      <div class="sub_title"><h3>법규목록</h3></div>
      <div class="grid_header">
        <div class="grid_header_left">총<em>54</em>건</div>
        <div>
          <v-btn color="" outlined height="30">엑셀</v-btn>
          <v-btn color="primary" depressed height="30">등록</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  meta: {
    title: '관리자-1',
  },
  data() {
    return {}
  },
  computed: {
    getCurrent() {
      return this.$store.getters['menu/getCurrent']
    },
  },
}
</script>
