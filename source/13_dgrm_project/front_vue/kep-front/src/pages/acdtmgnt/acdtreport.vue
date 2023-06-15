<!-- eslint-disable vue/no-v-html -->
<template>
  <div>
    <div class="container_homeui">
      <div class="homeui">
        <div class="con_half">
          <div class="step">
            <div class="step_tit">
              <!-- '**년 사고발생현황-->
              <h3>['{{ currentShortYear + $t('년') }} {{ $t('LB00001277') }}]</h3>
              <!-- *사고 등록 절차 진행(미반영 건), 사업장 인원 정보 변경 등 사유로 실제 정보와 차이가 발생 할 수 있습니다. -->
              <!-- 단위:건수 -->
              <div class="info">
                {{ $t('*사고 등록 절차 진행(미반영 건), 사업장 인원 정보 변경 등 사유로 실제 정보와 차이가 발생 할 수 있습니다.') }}
                ({{ $t('LB00000470') }}:{{ $t('LB00001251') }})
              </div>
            </div>
            <div class="mrB10"></div>
            <h4>자사</h4>
            <div class="new_group">
              <table>
                <colgroup>
                  <col width="12%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="9%" />
                  <col width="9%" />
                </colgroup>
                <thead>
                  <tr class="bg_gray">
                    <!-- 사고구분 (자사) -->
                    <th>{{ $t('사고구분 (자사)') }}</th>
                    <!-- Total -->
                    <th>{{ $t('Total') }}</th>
                    <!-- 중대재해 -->
                    <th>{{ $t('LB00000818') }}</th>
                    <!-- 고위험 -->
                    <th>{{ $t('LB00001253') }}</th>
                    <!-- 병원치료 -->
                    <th>{{ $t('LB00000828') }}</th>
                    <!-- 경미사고 -->
                    <th>{{ $t('LB00000829') }}</th>
                    <!-- 아차사고 -->
                    <th>{{ $t('LB00000830') }}</th>
                    <!-- 1)재해율 -->
                    <th>{{ $t('1)재해율') }}</th>
                    <!-- 2)재해 도수율 -->
                    <th>{{ $t('2)재해 도수율') }}</th>
                    <!-- 산재승인 -->
                    <th>{{ $t('LB00001252') }}</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td class="bg_gray">{{ $t('발생건수') }}</td>
                    <td v-html="acdtData1.tot"></td>
                    <td v-html="acdtData1.s01_cnt"></td>
                    <td v-html="acdtData1.s02_cnt"></td>
                    <td v-html="acdtData1.s03_cnt"></td>
                    <td v-html="acdtData1.s04_cnt"></td>
                    <td v-html="acdtData1.s05_cnt"></td>
                    <td v-html="acdtData1.ratio1"></td>
                    <td v-html="acdtData1.ratio2"></td>
                    <td v-html="acdtData1.inac_cnt"></td>
                  </tr>
                  <tr>
                    <td class="bg_gray">{{ $t('전년比 (동일시점)') }}</td>
                    <td>{{ acdtData3.tot }}</td>
                    <td>{{ acdtData3.s01_cnt }}</td>
                    <td>{{ acdtData3.s02_cnt }}</td>
                    <td>{{ acdtData3.s03_cnt }}</td>
                    <td>{{ acdtData3.s04_cnt }}</td>
                    <td>{{ acdtData3.s05_cnt }}</td>
                    <td>{{ acdtData3.ratio1 }}</td>
                    <td>{{ acdtData3.ratio2 }}</td>
                    <td>{{ acdtData3.inac_cnt }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="mrB10"></div>
            <h4>협력사</h4>
            <div class="new_group">
              <table>
                <colgroup>
                  <col width="12%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="10%" />
                  <col width="9%" />
                  <col width="9%" />
                </colgroup>
                <thead>
                  <tr class="bg_gray">
                    <!-- 사고구분 (협력사) -->
                    <th>{{ $t('사고구분 (협력사)') }}</th>
                    <!-- Total -->
                    <th>{{ $t('Total') }}</th>
                    <!-- 중대재해 -->
                    <th>{{ $t('LB00000818') }}</th>
                    <!-- 고위험 -->
                    <th>{{ $t('LB00001253') }}</th>
                    <!-- 병원치료 -->
                    <th>{{ $t('LB00000828') }}</th>
                    <!-- 경미사고 -->
                    <th>{{ $t('LB00000829') }}</th>
                    <!-- 아차사고 -->
                    <th>{{ $t('LB00000830') }}</th>
                    <!-- 1)재해율 -->
                    <th>{{ $t('1)재해율') }}</th>
                    <!-- 2)재해 도수율 -->
                    <th>{{ $t('2)재해 도수율') }}</th>
                    <!-- 산재승인 -->
                    <th>{{ $t('LB00001252') }}</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td class="bg_gray">{{ $t('발생건수') }}</td>
                    <td v-html="acdtData2.tot"></td>
                    <td v-html="acdtData2.s01_cnt"></td>
                    <td v-html="acdtData2.s02_cnt"></td>
                    <td v-html="acdtData2.s03_cnt"></td>
                    <td v-html="acdtData2.s04_cnt"></td>
                    <td v-html="acdtData2.s05_cnt"></td>
                    <td v-html="acdtData2.ratio1"></td>
                    <td v-html="acdtData2.ratio2"></td>
                    <td v-html="acdtData2.inac_cnt"></td>
                  </tr>
                  <tr>
                    <td class="bg_gray">{{ $t('전년比 (동일시점)') }}</td>
                    <td>{{ acdtData4.tot }}</td>
                    <td>{{ acdtData4.s01_cnt }}</td>
                    <td>{{ acdtData4.s02_cnt }}</td>
                    <td>{{ acdtData4.s03_cnt }}</td>
                    <td>{{ acdtData4.s04_cnt }}</td>
                    <td>{{ acdtData4.s05_cnt }}</td>
                    <td>{{ acdtData4.ratio1 }}</td>
                    <td>{{ acdtData4.ratio2 }}</td>
                    <td>{{ acdtData4.inac_cnt }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="mrB10"></div>
            <div class="step_btm">
              <!-- 1) 산업재해율: 근로자 백명당 발생하는 재해자수의 비율 2) 재해 도수율:백만 근로 시간당 재해 발생 건수 -->
              <div class="info">
                {{ $t('1)산업재해율: 근로자 백명당 발생하는 재해자수의 비율 &nbsp;&nbsp;&nbsp; 2)재해 도수율:백만 근로 시간당 재해 발생 건수') }}
              </div>
            </div>
            <div class="mrB10"></div>
            <!-- Total -->
            <div class="title_bar">
              <h4>{{ $t('Total') }}</h4>
              <div class="legend">
                <span>자사</span>
                <span>협력사</span>
              </div>
            </div>
            <div class="ap_total">
              <div :class="[apShow === true ? 'ap_total_item01 mrR16' : 'ap_total_item01 mrR16 dp_none']">
                <div class="item">
                  <div class="chart_module">
                    <h5>{{ $t('전사') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData1" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 제조 -->
                  <div class="chart_module">
                    <h5>{{ $t('제조') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData2_1" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- AP R&I -->
                  <div class="chart_module">
                    <h5>{{ $t('연구소') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData2_2" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- AP 비제조 -->
                  <div class="chart_module">
                    <h5>{{ $t('비제조') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData2_3" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 생산자회사 -->
                  <div class="chart_module">
                    <h5>{{ $t('생산회사') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData2_4" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 판매자회사 -->
                  <div class="chart_module">
                    <h5>{{ $t('판매회사') }}</h5>
                    <bar-chart v-if="loaded" :data="chartData2_5" :options="barOptions" style="width: 130px; height: 220px"></bar-chart>
                  </div>
                </div>
              </div>
              <div class="ap_total_item02">
                <v-btn :class="[apShow === true ? 'show' : 'hide']" @click="apShow = !apShow"></v-btn>
                <div class="item">
                  <!-- 오산뷰티 -->
                  <div class="chart_module">
                    <h5>{{ $t('분당') }}</h5>
                    <bar-chart v-if="loaded" :data="chartDataWp1" :options="barOptions" style="width: 120px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 데일리뷰티 -->
                  <div class="chart_module">
                    <h5>{{ $t('당진') }}</h5>
                    <bar-chart v-if="loaded" :data="chartDataWp2" :options="barOptions" style="width: 120px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 헬스케어생산 -->
                  <div class="chart_module">
                    <h5>{{ $t('울산') }}</h5>
                    <bar-chart v-if="loaded" :data="chartDataWp3" :options="barOptions" style="width: 120px; height: 220px"></bar-chart>
                  </div>
                </div>
                <div class="item">
                  <!-- 오설록생산 -->
                  <div class="chart_module">
                    <h5>{{ $t('연구소') }}</h5>
                    <bar-chart v-if="loaded" :data="chartDataWp4" :options="barOptions" style="width: 120px; height: 220px"></bar-chart>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="homeui">
        <div class="con_half">
          <div class="step">
            <div class="step_tit">
              <h3>{{ $t('사고현황') }}</h3>
            </div>
            <div class="sch_zone">
              <div class="sch_zone-group">
                <p class="select_title m-w60">그룹 조회</p>
                <v-select ref="cat1" v-model="cat1Val" :hide-details="true" height="36" :items="cat1" item-text="cd_nm" item-value="cd" outlined dense multiple @change="chkWkpl1">
                  <template v-slot:selection="{ item, index }">
                    <v-chip v-if="index === 0" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <v-chip v-if="index === 1" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <span v-if="index === 2" class="grey--text text-caption"> 외 {{ cat1Val.length - 2 }}건 </span>
                  </template>
                </v-select>
              </div>
              <div class="mrR16"></div>
              <div class="sch_zone-group">
                <p class="select_title">사업장 조회</p>
                <v-select v-model="wkpl1Val" :hide-details="true" height="36" :items="wkpl1" item-text="txt" item-value="val" outlined dense multiple @change="fnWkpl1">
                  <template v-slot:prepend-item>
                    <v-list-item ripple @mousedown.prevent @click="wkpl1Toggle">
                      <v-list-item-action>
                        <v-icon :color="cat1Val.length > 0 ? 'indigo darken-4' : ''">
                          {{ wkpl1Icon }}
                        </v-icon>
                      </v-list-item-action>
                      <v-list-item-content>
                        <v-list-item-title>Select All</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                    <v-divider class="mt-2"></v-divider>
                  </template>
                  <template v-slot:selection="{ item, index }">
                    <v-chip v-if="index === 0" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 1" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 2" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 3" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <span v-if="index === 4" class="grey--text text-caption"> 외 {{ wkpl1Val.length - 4 }}건 </span>
                  </template>
                </v-select>
              </div>
              <div class="mrR16"></div>
              <v-btn class="mrR4" outlined @click="schReset1">{{ $t('LB00000029') }}</v-btn>
              <v-btn color="primary" depressed height="36" @click="select1">{{ $t('LB00000030') }}</v-btn>
            </div>
            <div class="mrB20"></div>
            <div class="title_bar">
              <h5></h5>
              <div class="legend">
                <span>자사</span>
                <span>협력사</span>
              </div>
            </div>
            <div class="shut_wrap">
              <div class="inbox" style="width: calc(50% - 32px)">
                <div class="chart_group">
                  <div class="chart_module">
                    <div class="chart_tit" style="width: 200px; margin-bottom: 16px">
                      <p class="bold" style="min-width: 44px">{{ $t('반기별') }}</p>
                      <v-select ref="period1" v-model="schData.period1" :items="period1" item-text="txt" item-value="val" :hide-details="true" outlined dense height="24">
                      </v-select>
                    </div>
                    <mix-chart v-if="loaded" ref="chart3" :data="chartData17" :options="mixOptions" style="width: 100%; height: 220px"></mix-chart>
                  </div>
                </div>
              </div>
              <div class="gap"></div>
              <div class="inbox" style="width: 50%">
                <div class="chart_group">
                  <div class="chart_module">
                    <div class="chart_tit" style="width: 200px; margin-bottom: 16px">
                      <p class="bold" style="min-width: 32px">{{ $t('월별') }}</p>
                      <v-select ref="period2" v-model="schData.period2" :hide-details="true" :items="period2" item-text="txt" item-value="val" outlined dense height="24">
                      </v-select>
                    </div>
                    <mix-chart v-if="loaded" ref="chart4" :data="chartData4" :options="mixOptions" style="width: 100%; height: 220px"></mix-chart>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="homeui">
        <div class="con_half">
          <div class="step">
            <div class="sch_zone" style="border-top: 1px solid #777">
              <div class="sch_zone-group">
                <p class="select_title">그룹 조회</p>
                <v-select
                  v-model="cat2Val"
                  class="mrR16"
                  :hide-details="true"
                  height="36"
                  :items="cat2"
                  item-text="cd_nm"
                  item-value="cd"
                  outlined
                  dense
                  multiple
                  @change="chkWkpl2"
                >
                  <template v-slot:selection="{ item, index }">
                    <v-chip v-if="index === 0" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <v-chip v-if="index === 1" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <v-chip v-if="index === 2" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <v-chip v-if="index === 3" style="height: 24px">
                      <span>{{ item.cd_nm }}</span>
                    </v-chip>
                    <span v-if="index === 4" class="grey--text text-caption"> 외 {{ cat2Val.length - 4 }}건 </span>
                  </template>
                </v-select>
              </div>
              <div class="sch_zone-group">
                <p class="select_title">사업장 조회</p>
                <v-select
                  v-model="wkpl2Val"
                  class="mrR16"
                  :hide-details="true"
                  height="36"
                  :items="wkpl2"
                  item-text="txt"
                  item-value="val"
                  outlined
                  dense
                  multiple
                  style="min-width: 400px"
                  @change="fnWkpl2"
                >
                  <template v-slot:prepend-item>
                    <v-list-item ripple @mousedown.prevent @click="wkpl2Toggle">
                      <v-list-item-action>
                        <v-icon :color="cat2Val.length > 0 ? 'indigo darken-4' : ''">
                          {{ wkpl2Icon }}
                        </v-icon>
                      </v-list-item-action>
                      <v-list-item-content>
                        <v-list-item-title>Select All</v-list-item-title>
                      </v-list-item-content>
                    </v-list-item>
                    <v-divider class="mt-2"></v-divider>
                  </template>
                  <template v-slot:selection="{ item, index }">
                    <v-chip v-if="index === 0" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 1" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 2" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <v-chip v-if="index === 3" style="height: 24px">
                      <span>{{ item.txt }}</span>
                    </v-chip>
                    <span v-if="index === 4" class="grey--text text-caption"> 외 {{ wkpl2Val.length - 4 }}건 </span>
                  </template>
                </v-select>
              </div>
              <div class="sch_zone-group" style="max-width: 268px">
                <p class="select_title">기간선택</p>
                <v-menu v-model="menu1" :close-on-content-click="false" :nudge-right="40">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.sDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="36"
                      v-bind="attrs"
                      class="comp_calendar"
                      style="max-width: 120px"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="schData.sDate" type="month" :max="schData.eDate" no-title @input="menu1 = false"> </v-date-picker>
                </v-menu>
                <div style="margin: 6px 4px; text-align: center">-</div>
                <v-menu v-model="menu2" :close-on-content-click="false" :nudge-right="40">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="schData.eDate"
                      prepend-icon="event"
                      readonly
                      outlined
                      :hide-details="true"
                      dense
                      height="36"
                      v-bind="attrs"
                      class="comp_calendar mrR8"
                      style="max-width: 120px"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="schData.eDate" type="month" :min="schData.sDate" no-title @input="menu2 = false"> </v-date-picker>
                </v-menu>
              </div>
              <v-btn color="" class="mrR4" outlined height="36" @click="schReset2">{{ $t('LB00000029') }}</v-btn>
              <v-btn color="primary" depressed height="36" @click="select2">{{ $t('LB00000030') }}</v-btn>
            </div>
            <div class="mrB48"></div>
            <div class="dp_grid">
              <div class="item">
                <!-- 사고유형 -->
                <h4>{{ $t('사고유형') }} - {{ $t('총') }}{{ chartData5Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie"><pie-chart v-if="loaded" ref="chart5" :data="chartData5" :options="pieOptions"></pie-chart></div>
              </div>
              <div class="item">
                <!-- 사고원인: 불안전한 행동 -->
                <h4>{{ $t('사고원인: 불안전한 행동') }} - {{ $t('총') }}{{ chartData7Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie"><pie-chart v-if="loaded" ref="chart7" :data="chartData7" :options="pieOptions"></pie-chart></div>
              </div>
              <div class="item">
                <!-- 사고 기인물 -->
                <h4>{{ $t('사고 기인물') }} - {{ $t('총') }}{{ chartData9Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie"><pie-chart v-if="loaded" ref="chart9" :data="chartData9" :options="pieOptions"></pie-chart></div>
              </div>
              <div class="item">
                <!-- 작업내용 -->
                <h4>{{ $t('작업내용') }} - {{ $t('총') }}{{ chartData6Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie"><pie-chart v-if="loaded" ref="chart6" :data="chartData6" :options="pieOptions"></pie-chart></div>
              </div>
              <div class="item">
                <!-- 사고원인: 불안전한 상태 -->
                <h4>{{ $t('사고원인: 불안전한 상태') }} - {{ $t('총') }}{{ chartData8Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie"><pie-chart v-if="loaded" ref="chart8" :data="chartData8" :options="pieOptions"></pie-chart></div>
              </div>
              <div class="item">
                <!-- 재발방지대책 조치율 -->
                <h4>{{ $t('재발방지대책 조치율') }} - {{ $t('총') }}{{ acdtPrst.ratio1 }}{{ $t('%') }}</h4>
                <div class="chart_pie" style="width: 80%">
                  <pie-chart v-if="loaded" ref="chart10" :data="chartData10" :options="pieOptions3" style="margin: 0 auto"></pie-chart>
                  <table>
                    <colgroup>
                      <col width="30%" />
                      <col width="23.5%" />
                      <col width="23.5%" />
                      <col width="23%" />
                    </colgroup>
                    <tbody>
                      <tr>
                        <td><span>Total : </span> {{ acdtPrst.tot }}</td>
                        <td><span>조치 : </span> {{ acdtPrst.cnt1 }}</td>
                        <td><span>진행 : </span> {{ acdtPrst.cnt2 }}</td>
                        <td style="color: #cc3636; cursor: pointer" @click="goList"><span style="color: #cc3636">지연 : </span> {{ acdtPrst.cnt3 }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="shut_wrap">
              <div class="inbox">
                <!-- 사고발생부서 -->
                <h4>{{ $t('사고발생부서') }}</h4>
                <div class="new_group">
                  <table>
                    <colgroup>
                      <col width="10%" />
                      <col width="40%" />
                      <col width="40%" />
                      <col width="10%" />
                    </colgroup>
                    <thead>
                      <tr class="bg_gray">
                        <!-- 구분 -->
                        <th>{{ $t('LB00000741') }}</th>
                        <!-- 사업장 -->
                        <th>{{ $t('사업장') }}</th>
                        <!-- 부서명 -->
                        <th>{{ $t('부서명') }}</th>
                        <!-- 사고(건) -->
                        <th>{{ $t('사고(건)') }}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td class="bg_gray">1</td>
                        <td class="left">{{ acdtDept[0].wkpl_nm }}</td>
                        <td class="left">{{ acdtDept[0].dfdt_dept_nm }}</td>
                        <td>{{ acdtDept[0].cnt }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">2</td>
                        <td class="left">{{ acdtDept[1].wkpl_nm }}</td>
                        <td class="left">{{ acdtDept[1].dfdt_dept_nm }}</td>
                        <td>{{ acdtDept[1].cnt }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">3</td>
                        <td class="left">{{ acdtDept[2].wkpl_nm }}</td>
                        <td class="left">{{ acdtDept[2].dfdt_dept_nm }}</td>
                        <td>{{ acdtDept[2].cnt }}</td>
                      </tr>
                      <tr>
                        <td class="" colspan="4"></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="gap"></div>
              <div class="inbox">
                <!-- 사고자 근속기간 -->
                <h4>{{ $t('사고자 근속기간') }}</h4>
                <div class="new_group">
                  <table>
                    <colgroup>
                      <col width="10%" />
                      <col width="70%" />
                      <col width="20%" />
                    </colgroup>
                    <thead>
                      <tr class="bg_gray">
                        <!-- 구분 -->
                        <th>{{ $t('LB00000741') }}</th>
                        <!-- 근속기간(년) -->
                        <th>{{ $t('근속기간(년)') }}</th>
                        <!-- 인원(명) -->
                        <th>{{ $t('인원(명)') }}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td class="bg_gray">1</td>
                        <td class="left">{{ $t('1년 미만') }}</td>
                        <td>{{ acdtPeriod.cnt1 }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">2</td>
                        <td class="left">{{ $t('1년 이상 ~ 3년 미만') }}</td>
                        <td>{{ acdtPeriod.cnt2 }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">3</td>
                        <td class="left">{{ $t('3년 이상 ~ 10년 미만') }}</td>
                        <td>{{ acdtPeriod.cnt3 }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">4</td>
                        <td class="left">{{ $t('10년 이상') }}</td>
                        <td>{{ acdtPeriod.cnt4 }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="mrB48"></div>
            <div class="shut_wrap">
              <div class="inbox" style="width: calc(50% - 32px)">
                <h4>{{ $t('사고발생시간') }}</h4>
                <div class="chart_group">
                  <div class="chart_module">
                    <line-chart v-if="loaded" ref="chart11" :data="chartData11" :options="lineOptions" style="width: 100%; height: 250px"></line-chart>
                  </div>
                </div>
                <div class="mrB10"></div>
                <div class="step_btm">
                  <div class="info left">* {{ maxHour }}{{ $t('시에 사고발생 위험이 높습니다.') }}</div>
                </div>
              </div>
              <div class="gap"></div>
              <div class="inbox" style="width: 50%">
                <h4>{{ $t('사고발생빈도') }}</h4>
                <div class="chart_group">
                  <div class="chart_module">
                    <line-chart v-if="loaded" ref="chart12" :data="chartData12" :options="lineOptions" style="width: 100%; height: 250px"></line-chart>
                  </div>
                </div>
                <div class="mrB10"></div>
                <div class="step_btm">
                  <div class="info left">* {{ maxMonth }}{{ $t('월에 사고발생 위험이 높습니다.') }}</div>
                </div>
              </div>
            </div>
            <div class="mrB48"></div>
            <div class="dp_grid-4">
              <div class="item">
                <!-- 사고발생요일 -->
                <h4>{{ $t('사고발생요일') }}</h4>
                <div class="chart_module" style="width: 100%; height: 340px">
                  <bar-chart2 v-if="loaded" ref="chart13" :data="chartData13" :options="barOptions2" style="height: 300px"></bar-chart2>
                </div>
                <div class="mrB10"></div>
                <!-- 요일에 사고발생 위험이 높습니다. -->
                <div class="step_btm">
                  <div class="info left">* {{ maxDow }}{{ $t('에 사고발생 위험이 높습니다.') }}</div>
                </div>
              </div>
              <div class="item">
                <!-- 휴업일수 -->
                <h4>{{ $t('휴업일수') }}</h4>
                <div class="chart_module" style="width: 100%; height: 340px">
                  <bar-chart2 v-if="loaded" ref="chart14" :data="chartData14" :options="barOptions2" style="height: 180px"></bar-chart2>
                  <div class="mrB10"></div>
                  <table>
                    <colgroup>
                      <col width="40%" />
                      <col width="30%" />
                      <col width="30%" />
                    </colgroup>
                    <thead>
                      <tr class="bg_gray">
                        <!-- 구분 -->
                        <th>{{ $t('LB00000741') }}</th>
                        <!-- 사고(건) -->
                        <th>{{ $t('사고(건)') }}</th>
                        <!-- 평균(일) -->
                        <th>{{ $t('평균(일)') }}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td class="bg_gray">3일 미만</td>
                        <td class="right">{{ acdtCareDays.cnt1 }}</td>
                        <td class="right">{{ acdtCareDays.avg1 }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">3일 이상</td>
                        <td class="right">{{ acdtCareDays.cnt2 }}</td>
                        <td class="right">{{ acdtCareDays.avg2 }}</td>
                      </tr>
                      <tr>
                        <td class="bg_gray">합계</td>
                        <td class="right">{{ acdtCareDays.tot }}</td>
                        <td class="right">{{ acdtCareDays.tot_avg }}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="mrB10"></div>
                <div class="step_btm">
                  <!-- 3일 이상 휴업 사고는 산업재해조사표 고용노동부 제출 대상임. -->
                  <div class="ls left info">* {{ $t('3일 이상 휴업 사고는 산업재해조사표 고용노동부 제출 대상임.') }}</div>
                </div>
              </div>
              <div class="item">
                <!-- 사고자 연령대 -->
                <h4>{{ $t('사고자 연령대') }} - {{ $t('총') }}{{ chartData15Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie" style="width: 80%; margin: 0 auto">
                  <pie-chart v-if="loaded" ref="chart15" :data="chartData15" :options="pieOptions"></pie-chart>
                </div>
              </div>
              <div class="item">
                <!-- 사고자 성비 -->
                <h4>{{ $t('사고자 성비') }} - {{ $t('총') }}{{ chartData16Tot }}{{ $t('건') }}</h4>
                <div class="chart_pie" style="width: 80%; margin: 0 auto">
                  <pie-chart v-if="loaded" ref="chart16" :data="chartData16" :options="pieOptions"></pie-chart>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BarChart from '~/components/BarChart.js'
import BarChart2 from '~/components/BarChart2.js'
import MixChart from '~/components/MixChart.js'
import PieChart from '~/components/PieChart.js'
import LineChart from '~/components/LineChart.js'

export default {
  meta: {
    title: '사고 통계',
  },
  components: {
    BarChart,
    BarChart2,
    MixChart,
    PieChart,
    LineChart,
  },
  data() {
    return {
      codeid: ['ST00000022'],
      codes: {
        ST00000022: [{ cd: '', cd_nm: '' }],
      },
      loaded: false,
      apShow: true,
      menu1: false,
      menu2: false,
      schData: {},
      cat1: [],
      wkpl1: [],
      cat2: [],
      wkpl2: [],
      cat1Val: ['AP01'],
      wkpl1Val: [],
      cat2Val: ['AP01'],
      wkpl2Val: [],
      catWkpl: [],
      period1: [],
      period2: [],
      currentShortYear: '',
      barOptions: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        scales: {
          xAxes: [
            {
              stacked: true,
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              stacked: true,
              display: true,
              ticks: {
                max: 30,
              },
              gridLines: {
                lineWidth: 0,
                color: '#ffffff',
              },
            },
          ],
        },
        plugins: {},
      },
      barOptions2: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        scales: {
          xAxes: [
            {
              stacked: true,
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              stacked: true,
              ticks: {
                max: 30,
              },
              gridLines: {
                lineWidth: 0,
                color: '#f9f9f9',
              },
            },
          ],
        },
        plugins: {},
      },
      mixOptions: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        scales: {
          xAxes: [
            {
              stacked: true,
              gridLines: {
                display: false,
              },
            },
          ],
          yAxes: [
            {
              stacked: true,
              ticks: {
                max: 30,
              },
              gridLines: {
                lineWidth: 1,
                color: '#f9f9f9',
              },
            },
          ],
        },
        plugins: {
          datalabels: {
            color: 'white',
            formatter(value, context) {
              if (context.dataset.type === 'line') {
                return ''
              }
              return value > 0 ? value : ''
            },
          },
        },
      },
      lineOptions: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        plugins: {
          datalabels: {
            align: 'end',
            anchor: 'end',
            font: { size: 15, weight: 'bold' },
            color: (ctx) => {
              const arr = [...ctx.dataset.data]
              const max = Math.max(...arr)
              arr.forEach((e, i) => {
                if (e === max) arr[i] = 0
              })
              const second = Math.max(...arr)
              return ctx.dataset.data[ctx.dataIndex] === max || ctx.dataset.data[ctx.dataIndex] === second ? '#cc3636' : '#222222'
            },
            formatter(value, context) {
              return value > 0 ? value : ''
            },
          },
        },
      },
      pieOptions: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        cutoutPercentage: 30,
        plugins: {
          datalabels: {
            textAlign: 'center',
            // anchor: 'start',
            // align: 'center',
            rotation: 0,
            font: {
              size: 11,
              weight: 'bold',
            },
            // backgroundColor: 'rgba(0, 0, 0, 0.1)',
            borderRadius: 2,
            color: '#dddddd',
            formatter(value, context) {
              const nm = context.chart.config.data.labels[context.dataIndex]
              return value > 0 ? nm + '\n' + value + '건' : ''
            },
          },
        },
      },
      pieOptions2: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        cutoutPercentage: 30,
        plugins: {
          datalabels: {
            // anchor: 'center',
            rotation: 0,
            font: {
              size: 10,
            },
            // backgroundColor: 'rgba(0, 0, 0, 0.1)',
            borderRadius: 2,
            color: '#ffffff',
            formatter(value, context) {
              const nm = context.chart.config.data.labels[context.dataIndex]
              return value > 0 ? nm + '\n' + value + '건' : ''
            },
          },
        },
      },
      pieOptions3: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        cutoutPercentage: 30,
        plugins: {
          datalabels: {
            textAlign: 'center',
            // align: 'center',
            rotation: 0,
            font: {
              size: 11,
              weight: 'bold',
            },
            // backgroundColor: 'rgba(0, 0, 0, 0.1)',
            borderRadius: 2,
            color: '#dddddd',
            formatter(value, context) {
              const nm = context.chart.config.data.labels[context.dataIndex]
              return value > 0 ? nm + '\n' + value + '%' : ''
            },
          },
        },
      },
      barColor: ['#142C61', '#587EC8'],
      mixColor: ['#EF798D', '#9470AE'],
      // pieColor: ['#485EA7', '#8E69A9', '#EF798D', '#A1C674', '#F19F39'],
      // pieColor: ['#062c5e', '#294c87', '#384582', '#a07daa', '#4c4c73'],
      // pieColor: ['#012548', '#005490', '#0070c0', '#2a93fb', '#40b0ff'],
      // pieColor: ['#034488', '#dd72a6', '#b665aa', '#8959a5', '#564ba7'],
      pieColor: ['#062c5e', '#0070c0', '#1ea185', '#9bbb5c', '#f39b26'],
      acdtData1: {}, // 자사 사고현황
      acdtData2: {}, // 협력사 사고현황
      acdtData3: {}, // 자사 사고현황 (전년)
      acdtData4: {}, // 협력사 사고현황 (전년)
      acdtPrst: {}, // 조치율
      acdtDept: [
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
      ], // 사고발생 부서
      acdtPeriod: {}, // 사고자 근속기간
      acdtCareDays: {}, // 휴업일수
      chartData1: {},
      chartData2_1: {},
      chartData2_2: {},
      chartData2_3: {},
      chartData2_4: {},
      chartData2_5: {},
      chartDataWp1: {},
      chartDataWp2: {},
      chartDataWp3: {},
      chartDataWp4: {},
      chartDataWp5: {},
      chartDataWp6: {},
      chartDataWp7: {},
      chartDataWp8: {},
      chartData3: {},
      chartData4: {},
      chartData5: {},
      chartData5Tot: 0,
      chartData6: {},
      chartData6Tot: 0,
      chartData7: {},
      chartData7Tot: 0,
      chartData8: {},
      chartData8Tot: 0,
      chartData9: {},
      chartData9Tot: 0,
      chartData10: {},
      chartData11: {},
      chartData12: {},
      chartData13: {},
      chartData14: {},
      chartData15: {},
      chartData15Tot: 0,
      chartData16: {},
      chartData16Tot: 0,
      chartData17: {},
      maxHour: '',
      maxMonth: '',
      maxDow: '',
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     if (cd.startsWith('LB') || cd.startsWith('MS')) return this.$store.getters['mlang/getMultiLanguage'](cd)
    //     else return cd
    //     // return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
    likesAllwkpl1() {
      return this.wkpl1Val.length === this.wkpl1.length
    },
    likesSomewkpl1() {
      return this.wkpl1Val.length > 0
    },
    wkpl1Icon() {
      if (this.likesAllwkpl1) return 'mdi-close-box'
      if (this.likesSomewkpl1) return 'mdi-minus-box'
      return 'mdi-checkbox-blank-outline'
    },

    likesAllwkpl2() {
      return this.wkpl2Val.length === this.wkpl2.length
    },
    likesSomewkpl2() {
      return this.wkpl2Val.length > 0
    },
    wkpl2Icon() {
      if (this.likesAllwkpl2) return 'mdi-close-box'
      if (this.likesSomewkpl2) return 'mdi-minus-box'
      return 'mdi-checkbox-blank-outline'
    },
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    this.currentShortYear = this.getTzDate(user.time_zone).substr(2, 2)
    this.schData.sDate = this.getTzDate(user.time_zone, '-', 0, 11).substr(0, 7)
    this.schData.eDate = this.getTzDate(user.time_zone, '-').substr(0, 7)

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res

        this.period1 = [
          { txt: '최근3년', val: '2' },
          { txt: '최근4년', val: '3' },
          { txt: '최근5년', val: '4' },
        ]
        for (let y = 2015; y <= this.getTzDate(user.time_zone).substr(0, 4); y++) {
          const obj = {}
          obj.txt = y + '년'
          obj.val = y + ''
          this.period2.unshift(obj)
        }

        this.schData.period1 = '2'
        this.schData.period2 = this.getTzDate(user.time_zone).substr(0, 4) + ''

        this.select()
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    async select() {
      this.loaded = false
      this.schData.kind = 'select'
      const data = await this.$axios.$get(`/api/v1/she/sfty/acdtrpt`, { params: this.schData })
      this.cat1 = this.codes.ST00000022
      this.cat2 = this.codes.ST00000022
      this.wkpl1 = data.apWkpl
      this.wkpl2 = data.apWkpl
      this.catWkpl = data.apCatWkpl

      this.catWkpl.forEach((row) => {
        if (row.wkpl_cat_cd === this.cat1Val[0]) {
          this.wkpl1Val.push(row.wkpl_id)
        }
      })

      this.catWkpl.forEach((row) => {
        if (row.wkpl_cat_cd === this.cat2Val[0]) {
          this.wkpl2Val.push(row.wkpl_id)
        }
      })

      const acdtTot1 = data.acdtTot1[0] // 자사 사고현황
      const acdtTot2 = data.acdtTot2[0] // 협력사 사고현황
      const acdtTot3 = data.acdtTot3[0] // 자사 사고현황 (전년)
      const acdtTot4 = data.acdtTot4[0] // 협력사 사고현황 (전년)

      this.acdtData1.tot = this.compare(acdtTot3.tot, acdtTot1.tot)
      this.acdtData1.inac_cnt = this.compare(acdtTot3.inac_cnt, acdtTot1.inac_cnt)
      this.acdtData1.s01_cnt = this.compare(acdtTot3.s01_cnt, acdtTot1.s01_cnt)
      this.acdtData1.s02_cnt = this.compare(acdtTot3.s02_cnt, acdtTot1.s02_cnt)
      this.acdtData1.s03_cnt = this.compare(acdtTot3.s03_cnt, acdtTot1.s03_cnt)
      this.acdtData1.s04_cnt = this.compare(acdtTot3.s04_cnt, acdtTot1.s04_cnt)
      this.acdtData1.s05_cnt = this.compare(acdtTot3.s05_cnt, acdtTot1.s05_cnt)
      this.acdtData1.ratio1 = acdtTot1.ratio1
      this.acdtData1.ratio2 = acdtTot1.ratio2

      this.acdtData3 = acdtTot3

      this.acdtData2.tot = this.compare(acdtTot4.tot, acdtTot2.tot)
      this.acdtData2.inac_cnt = this.compare(acdtTot4.inac_cnt, acdtTot2.inac_cnt)
      this.acdtData2.s01_cnt = this.compare(acdtTot4.s01_cnt, acdtTot2.s01_cnt)
      this.acdtData2.s02_cnt = this.compare(acdtTot4.s02_cnt, acdtTot2.s02_cnt)
      this.acdtData2.s03_cnt = this.compare(acdtTot4.s03_cnt, acdtTot2.s03_cnt)
      this.acdtData2.s04_cnt = this.compare(acdtTot4.s04_cnt, acdtTot2.s04_cnt)
      this.acdtData2.s05_cnt = this.compare(acdtTot4.s05_cnt, acdtTot2.s05_cnt)
      this.acdtData2.ratio1 = acdtTot2.ratio1
      this.acdtData2.ratio2 = acdtTot2.ratio2

      this.acdtData4 = acdtTot4

      // AP Total
      this.chartData1.labels = this.makeLabel1()
      this.chartData1.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData0(data.apTot, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData0(data.apTot, '02'),
        },
      ]

      const totals = []
      this.chartData1.datasets.forEach((dataset, datasetIndex) => {
        dataset.data.forEach((value, index) => {
          totals[index] = (totals[index] || 0) + value
        })
      })

      const max = Math.max(...totals)
      const maxaxes = max > 10 ? max + Math.round(max / 10) : max + 1
      this.barOptions.scales.yAxes[0].ticks.max = maxaxes

      // AP 제조
      this.chartData2_1.labels = this.makeLabel1()
      this.chartData2_1.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG02', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG02', '02'),
        },
      ]

      // AP R&I
      this.chartData2_2.labels = this.makeLabel1()
      this.chartData2_2.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG05', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG05', '02'),
        },
      ]

      // AP 비제조
      this.chartData2_3.labels = this.makeLabel1()
      this.chartData2_3.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG04', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG04', '02'),
        },
      ]

      // 생산자회사
      this.chartData2_4.labels = this.makeLabel1()
      this.chartData2_4.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG06', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG06', '02'),
        },
      ]

      // 판매자회사
      this.chartData2_5.labels = this.makeLabel1()
      this.chartData2_5.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG07', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData2(data.apTot, 'SG07', '02'),
        },
      ]

      // 오산뷰티
      this.chartDataWp1.labels = this.makeLabel1()
      this.chartDataWp1.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl1, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl1, '02'),
        },
      ]

      // 데일리뷰티
      this.chartDataWp2.labels = this.makeLabel1()
      this.chartDataWp2.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl2, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl2, '02'),
        },
      ]

      // 헬스케어생산
      this.chartDataWp3.labels = this.makeLabel1()
      this.chartDataWp3.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl3, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl3, '02'),
        },
      ]

      // 오설록생산
      this.chartDataWp4.labels = this.makeLabel1()
      this.chartDataWp4.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl4, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl4, '02'),
        },
      ]

      // R&I Center
      this.chartDataWp5.labels = this.makeLabel1()
      this.chartDataWp5.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl5, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl5, '02'),
        },
      ]

      // 물류
      this.chartDataWp6.labels = this.makeLabel1()
      this.chartDataWp6.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl6, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl6, '02'),
        },
      ]

      // 세계본사
      this.chartDataWp7.labels = this.makeLabel1()
      this.chartDataWp7.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl7, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl7, '02'),
        },
      ]

      // 지역사업부
      this.chartDataWp8.labels = this.makeLabel1()
      this.chartDataWp8.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: this.makeData1(data.apWkpl8, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 16,
          data: this.makeData1(data.apWkpl8, '02'),
        },
      ]

      this.makeChart1(data)

      this.makeChart2(data)

      this.loaded = true
    },
    makeChart1(data) {
      // 사고현황-반기별
      this.chartData3.labels = this.makeLabel3()
      this.chartData3.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          order: 1,
          data: this.makeData3(data.acdtPerQt, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          order: 1,
          data: this.makeData3(data.acdtPerQt, '02'),
        },
        {
          label: '자사',
          borderColor: this.barColor[0],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData3(data.acdtPerQt, '01'),
        },
        {
          label: '협력사',
          borderColor: this.barColor[1],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData3(data.acdtPerQt, '02'),
        },
      ]
      this.chartData17.labels = this.makeLabel3()
      this.chartData17.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          order: 1,
          data: this.makeData3(data.acdtPerQt, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          order: 1,
          data: this.makeData3(data.acdtPerQt, '02'),
        },
        {
          label: '자사',
          borderColor: this.barColor[0],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData3(data.acdtPerQt, '01'),
        },
        {
          label: '협력사',
          borderColor: this.barColor[1],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData3(data.acdtPerQt, '02'),
        },
      ]

      // 사고현황-월별
      this.chartData4.labels = this.makeLabel4()
      this.chartData4.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          order: 1,
          data: this.makeData4(data.acdtPerMon, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          order: 1,
          data: this.makeData4(data.acdtPerMon, '02'),
        },
        {
          label: '자사',
          borderColor: this.barColor[0],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData4(data.acdtPerMon, '01'),
        },
        {
          label: '협력사',
          borderColor: this.barColor[1],
          type: 'line',
          fill: false,
          tension: 0.0,
          datalabels: {
            labels: {
              value: {
                formatter: (value, ctx) => {
                  return ''
                },
              },
            },
          },
          data: this.makeData4(data.acdtPerMon, '02'),
        },
      ]
    },
    makeChart2(data) {
      const limit = 5
      // 사고유형
      this.chartData5Tot = 0
      this.chartData5.labels = []
      const data5 = []
      data.acdtType.forEach((e) => {
        this.chartData5Tot += e.cnt
        if (e.rownum <= limit) {
          this.chartData5.labels.push(e.label)
          data5.push(e.cnt)
        }
      })
      this.chartData5.datasets = [
        {
          data: data5,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 작업내용
      this.chartData6Tot = 0
      this.chartData6.labels = []
      const data6 = []
      data.acdtOprn.forEach((e) => {
        this.chartData6Tot += e.cnt
        if (e.rownum <= limit) {
          this.chartData6.labels.push(e.label)
          data6.push(e.cnt)
        }
      })
      this.chartData6.datasets = [
        {
          data: data6,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 사고원인: 불안전한 행동
      this.chartData7Tot = 0
      this.chartData7.labels = []
      const data7 = []
      data.acdtUsftTxt.forEach((e) => {
        this.chartData7Tot += e.cnt
        if (e.rownum <= limit) {
          this.chartData7.labels.push(e.label)
          data7.push(e.cnt)
        }
      })
      this.chartData7.datasets = [
        {
          data: data7,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 사고원인: 불안전한 상태
      this.chartData8Tot = 0
      this.chartData8.labels = []
      const data8 = []
      data.acdtUsftSt.forEach((e) => {
        this.chartData8Tot += e.cnt
        if (e.rownum <= limit) {
          this.chartData8.labels.push(e.label)
          data8.push(e.cnt)
        }
      })
      this.chartData8.datasets = [
        {
          data: data8,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 사고기인물
      this.chartData9Tot = 0
      this.chartData9.labels = []
      const data9 = []
      data.acdtCausTgt.forEach((e) => {
        this.chartData9Tot += e.cnt
        if (e.rownum <= limit) {
          this.chartData9.labels.push(e.label)
          data9.push(e.cnt)
        }
      })
      this.chartData9.datasets = [
        {
          data: data9,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 재발방지대책 조치율
      this.acdtPrst = data.acdtPrst[0]
      this.chartData10.labels = ['조치율', '미조치율']
      this.chartData10.datasets = [
        {
          data: [this.acdtPrst.ratio1, this.acdtPrst.ratio2],
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 사고발생부서
      this.acdtDept = [
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
        { wkpl_nm: '', dfdt_dept_nm: '', cnt: 0 },
      ]
      data.acdtDept.forEach((e, i) => {
        this.acdtDept[i] = e
      })

      // 사고자 근속기간
      this.acdtPeriod = data.acdtPeriod[0]

      // 사고발생시간
      this.chartData11.labels = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']
      const data11 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      data.acdtHh.forEach((row) => {
        this.chartData11.labels.forEach((e, i) => {
          if (row.hh === e) {
            data11[i] = row.cnt
          }
        })
      })
      this.chartData11.datasets = [
        {
          // label: 'Data One',
          fill: false,
          tension: 0.3,
          borderColor: this.barColor[0],
          data: data11,
        },
      ]

      this.maxHour = ''
      let arr = [...data11]
      let max = Math.max(...arr)
      arr.forEach((e, i) => {
        if (e === max) arr[i] = 0
      })
      let second = Math.max(...arr)
      data11.forEach((e, i) => {
        if ((max > 0 && e === max) || (second > 0 && e === second)) {
          this.maxHour += Number(this.chartData11.labels[i]) + ','
        }
      })
      this.maxHour = this.maxHour.length > 0 ? this.maxHour.slice(0, -1) : ''

      // 사고발생빈도
      this.chartData12.labels = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
      const labels12 = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12']
      const data12 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      data.acdtMm.forEach((row) => {
        labels12.forEach((e, i) => {
          if (row.mm === e) {
            data12[i] = row.cnt
          }
        })
      })
      this.chartData12.datasets = [
        {
          // label: 'Data One',
          fill: false,
          tension: 0.3,
          borderColor: this.barColor[0],
          data: data12,
        },
      ]

      this.maxMonth = ''
      arr = [...data12]
      max = Math.max(...arr)
      arr.forEach((e, i) => {
        if (e === max) arr[i] = 0
      })
      second = Math.max(...arr)
      data12.forEach((e, i) => {
        if ((max > 0 && e === max) || (second > 0 && e === second)) {
          this.maxMonth += Number(labels12[i]) + ','
        }
      })
      this.maxMonth = this.maxMonth.length > 0 ? this.maxMonth.slice(0, -1) : ''

      // 사고발생요일
      this.chartData13.labels = ['월', '화', '수', '목', '금', '토', '일']
      const labels13 = [1, 2, 3, 4, 5, 6, 0]
      const data13 = [0, 0, 0, 0, 0, 0, 0]
      data.acdtDow.forEach((row) => {
        labels13.forEach((e, i) => {
          if (row.dow === e) {
            data13[i] = row.cnt
          }
        })
      })
      this.chartData13.datasets = [
        {
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: data13,
        },
      ]

      this.maxDow = ''
      arr = [...data13]
      max = Math.max(...data13)
      arr.forEach((e, i) => {
        if (e === max) arr[i] = 0
      })
      second = Math.max(...arr)
      data13.forEach((e, i) => {
        if ((max > 0 && e === max) || (second > 0 && e === second)) {
          this.maxDow += this.chartData13.labels[i] + '요일,'
        }
      })
      this.maxDow = this.maxDow.length > 0 ? this.maxDow.slice(0, -1) : ''

      // 휴업일수
      this.acdtCareDays = data.acdtCareDays[0]
      this.chartData14.labels = ['3일 미만', '3일 이상']
      this.chartData14.datasets = [
        {
          backgroundColor: this.barColor[0],
          barThickness: 16,
          data: [data.acdtCareDays[0].cnt1, data.acdtCareDays[0].cnt2],
        },
      ]

      // 사고자 연령대
      this.chartData15Tot = data.acdtAge[0].cnt1 + data.acdtAge[0].cnt2 + data.acdtAge[0].cnt3 + data.acdtAge[0].cnt4 + data.acdtAge[0].cnt5
      this.chartData15.labels = ['20대', '30대', '40대', '50대', '60대']
      this.chartData15.datasets = [
        {
          data: [data.acdtAge[0].cnt1, data.acdtAge[0].cnt2, data.acdtAge[0].cnt3, data.acdtAge[0].cnt4, data.acdtAge[0].cnt5],
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      // 사고자 성비
      this.chartData16Tot = data.acdtSex[0].cnt1 + data.acdtSex[0].cnt2
      this.chartData16.labels = ['남', '여']
      this.chartData16.datasets = [
        {
          data: [data.acdtSex[0].cnt1, data.acdtSex[0].cnt2],
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]
    },
    async select1() {
      this.schData.wkpl_list1 = this.wkpl1Val.join()
      if (!this.schData.wkpl_list1) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }

      this.schData.kind = 'select1'
      const data = await this.$axios.$get(`/api/v1/she/sfty/acdtrpt`, { params: this.schData })

      this.makeChart1(data)

      this.$refs.chart3.update()
      this.$refs.chart4.update()
    },
    async select2() {
      this.schData.wkpl_list2 = this.wkpl2Val.join()
      if (!this.schData.wkpl_list2) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }

      this.schData.kind = 'select2'
      const data = await this.$axios.$get(`/api/v1/she/sfty/acdtrpt`, { params: this.schData })

      this.makeChart2(data)

      this.$refs.chart5.update()
      this.$refs.chart6.update()
      this.$refs.chart7.update()
      this.$refs.chart8.update()
      this.$refs.chart9.update()
      this.$refs.chart10.update()
      this.$refs.chart11.update()
      this.$refs.chart12.update()
      this.$refs.chart13.update()
      this.$refs.chart14.update()
      this.$refs.chart15.update()
      this.$refs.chart16.update()
    },
    wkpl1Toggle() {
      if (this.likesAllwkpl1) {
        this.wkpl1Val = []
        this.cat1Val.forEach((selected, i) => {
          if (selected === 'AP01') {
            this.cat1Val.splice(i, 1)
          }
        })
        this.cat1Val = []
      } else {
        this.wkpl1Val = []
        this.wkpl1Val = [...new Set(this.wkpl1)]
        this.wkpl1Val.sort()
        this.cat1Count = 1
        this.cat1Val.forEach((selected, i) => {
          if (selected === 'AP01') {
            this.cat1Count = 0
          }
        })
        if (this.cat1Count === 1) {
          this.cat1Val.push('AP01')
          this.chkWkpl1()
        }
      }
    },
    wkpl2Toggle() {
      if (this.likesAllwkpl2) {
        this.wkpl2Val = []
        this.cat2Val.forEach((selected, i) => {
          if (selected === 'AP01') {
            this.cat2Val.splice(i, 1)
          }
        })
        this.cat2Val = []
      } else {
        this.wkpl2Val = []
        this.wkpl2Val = [...new Set(this.wkpl2)]
        this.wkpl2Val.sort()
        this.cat2Count = 1
        this.cat2Val.forEach((selected, i) => {
          if (selected === 'AP01') {
            this.cat2Count = 0
          }
        })
        if (this.cat2Count === 1) {
          this.cat2Val.push('AP01')
          this.chkWkpl2()
        }
      }
    },
    chkWkpl1() {
      this.result1 = []
      this.wkpl1Val = []
      this.catWkpl.forEach((row) => {
        this.cat1Val.forEach((e) => {
          if (row.wkpl_cat_cd === e) {
            this.result1.push(row.wkpl_id)
          }
        })
      })
      this.wkpl1Val = [...new Set(this.result1)]
      this.cat1Val.sort()

      this.sort1 = []
      this.sort2 = []
      this.cat1.forEach((select) => {
        this.cat1Val.forEach((selected) => {
          if (select.val === selected) {
            this.sort1.push(select)
          } else {
            this.sort2.push(select)
          }
        })
      })

      this.sort1.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sort2.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })
      this.sortResult = this.sort1.concat(this.sort2)
      if (this.sortResult.length > 0) {
        this.cat1 = [...new Set(this.sortResult)]
      } else {
        this.cat1.sort(function (a, b) {
          if (a.val > b.val) return 1
          if (a.val === b.val) return 0
          if (a.val < b.val) return -1
        })
      }
    },
    fnWkpl1() {
      this.cat1Val = []
      this.wkpl1Val.sort()
      if (this.wkpl1Val[0] === 'all') {
        this.wkpl1Val = [...new Set(this.wkpl1)]
        this.cat1Val.push('AP01')
      }
      this.sort1 = []
      this.sort2 = []
      this.wkpl1.forEach((select) => {
        this.wkpl1Val.forEach((selected) => {
          if (select.val === selected) {
            this.sort1.push(select)
          } else {
            this.sort2.push(select)
          }
        })
      })

      this.sort1.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sort2.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sortResult = this.sort1.concat(this.sort2)

      if (this.sortResult.length > 0) {
        this.wkpl1 = [...new Set(this.sortResult)]
      } else {
        this.wkpl1.sort(function (a, b) {
          if (a.val > b.val) return 1
          if (a.val === b.val) return 0
          if (a.val < b.val) return -1
        })
      }
      /*
      this.sort_count = 0
      this.wkpl1.forEach((select, i) => {
        select.num = this.sort_count
        this.sort_count++
      })
      this.wkpl1.unshift({
        val: 'all',
        txt: 'SELECT ALL',
      })
      */
    },
    chkWkpl2() {
      this.result2 = []
      this.wkpl2Val = []
      this.catWkpl.forEach((row) => {
        this.cat2Val.forEach((e) => {
          if (row.wkpl_cat_cd === e) {
            this.result2.push(row.wkpl_id)
          }
        })
      })
      this.wkpl2Val = [...new Set(this.result2)]
      this.cat2Val.sort()

      this.sort1 = []
      this.sort2 = []
      this.cat2.forEach((select) => {
        this.cat2Val.forEach((selected) => {
          if (select.val === selected) {
            this.sort1.push(select)
          } else {
            this.sort2.push(select)
          }
        })
      })

      this.sort1.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sort2.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sortResult = this.sort1.concat(this.sort2)

      if (this.sortResult.length > 0) {
        this.cat2 = [...new Set(this.sortResult)]
      } else {
        this.cat2.sort(function (a, b) {
          if (a.val > b.val) return 1
          if (a.val === b.val) return 0
          if (a.val < b.val) return -1
        })
      }
    },
    fnWkpl2() {
      this.wkpl2Val.sort()
      this.cat2Val = []
      this.sort1 = []
      this.sort2 = []
      this.wkpl2.forEach((select) => {
        this.wkpl2Val.forEach((selected) => {
          if (select.val === selected) {
            this.sort1.push(select)
          } else {
            this.sort2.push(select)
          }
        })
      })

      this.sort1.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sort2.sort(function (a, b) {
        if (a.val > b.val) return 1
        if (a.val === b.val) return 0
        if (a.val < b.val) return -1
      })

      this.sortResult = this.sort1.concat(this.sort2)

      if (this.sortResult.length > 0) {
        this.wkpl2 = [...new Set(this.sortResult)]
      } else {
        this.wkpl2.sort(function (a, b) {
          if (a.val > b.val) return 1
          if (a.val === b.val) return 0
          if (a.val < b.val) return -1
        })
      }
    },
    compare(val1, val2) {
      return val1 === 0
        ? val2
        : val2 === val1
        ? val2 + '<span style="color: #359feb"> (-)</span>'
        : val2 > val1
        ? val2 + '<span style="color: #cc3636"> (' + Math.round(((val2 - val1) / val1) * 100) + '% 🠕)</span>'
        : val2 + '<span style="color: rgb(51,153,102)"> (' + Math.round(((val2 - val1) / val1) * 100) + '% 🠗)</span>'
    },
    makeLabel1() {
      const label = [this.currentShortYear - 2 + '년', this.currentShortYear - 1 + '년', this.currentShortYear + '년']
      return label
    },
    makeLabel3() {
      const label = []
      for (let i = 0; i <= this.schData.period1; i++) {
        label.unshift(this.currentShortYear - i + '.하')
        label.unshift(this.currentShortYear - i + '.상')
      }
      return label
    },
    makeLabel4() {
      const label = []
      const user = this.$store.getters['auth/getUser']

      if (this.schData.period2 === this.getTzDate(user.time_zone).substr(0, 4)) {
        for (let i = 0; i < 12; i++) {
          label.unshift(this.getTzDate(user.time_zone, '-', 0, i).substr(2, 2) + '.' + this.getTzDate(user.time_zone, '-', 0, i).substr(5, 2) + '월')
        }
      } else {
        for (let i = 1; i <= 12; i++) {
          label.push(this.schData.period2.substr(2, 2) + '.' + (i > 9 ? i : '0' + i) + '월')
        }
      }

      return label
    },
    makeData0(data, clcd) {
      const label = [this.currentShortYear - 2 + '', this.currentShortYear - 1 + '', this.currentShortYear]
      const rtData = [0, 0, 0]
      data.forEach((row) => {
        label.forEach((v, i) => {
          if (
            v === row.yy &&
            clcd === row.dfdt_cl_cd &&
            (row.wkpl_main_bsns_cd === 'SG01' ||
              row.wkpl_main_bsns_cd === 'SG02' ||
              row.wkpl_main_bsns_cd === 'SG03' ||
              row.wkpl_main_bsns_cd === 'SG04' ||
              row.wkpl_main_bsns_cd === 'SG05')
          ) {
            rtData[i] = rtData[i] + row.cnt
          }
        })
      })
      return rtData
    },
    makeData1(data, clcd) {
      const label = [this.currentShortYear - 2 + '', this.currentShortYear - 1 + '', this.currentShortYear]
      const rtData = [0, 0, 0]
      data.forEach((row) => {
        label.forEach((v, i) => {
          if (v === row.yy && clcd === row.dfdt_cl_cd) {
            rtData[i] = rtData[i] + row.cnt
          }
        })
      })
      return rtData
    },
    makeData2(data, bsnscd, clcd) {
      const label = [this.currentShortYear - 2 + '', this.currentShortYear - 1 + '', this.currentShortYear]
      const rtData = [0, 0, 0]
      data.forEach((row) => {
        label.forEach((v, i) => {
          if (v === row.yy && bsnscd === row.wkpl_main_bsns_cd && clcd === row.dfdt_cl_cd) {
            rtData[i] = rtData[i] + row.cnt
          }
        })
      })
      return rtData
    },
    makeData3(data, clcd) {
      const label = []
      const rtData = []
      for (let i = 0; i <= this.schData.period1; i++) {
        label.unshift(this.currentShortYear - i + '')
        label.unshift(this.currentShortYear - i + '')
        rtData.push(0)
        rtData.push(0)
      }
      data.forEach((row) => {
        label.forEach((v, i) => {
          if (v === row.yy && clcd === row.dfdt_cl_cd) {
            if (i % 2 === 0) {
              if (row.qt <= 2) {
                rtData[i] = rtData[i] + row.cnt
              }
            } else if (row.qt > 2) {
              rtData[i] = rtData[i] + row.cnt
            }
          }
        })
      })
      return rtData
    },
    makeData4(data, clcd) {
      const label = []
      const rtData = []
      const user = this.$store.getters['auth/getUser']

      if (this.schData.period2 === this.getTzDate(user.time_zone).substr(0, 4)) {
        for (let i = 0; i < 12; i++) {
          label.unshift(Number(this.getTzDate(user.time_zone, '-', 0, i).substr(5, 2)))
          rtData.push(0)
        }
      } else {
        for (let i = 1; i <= 12; i++) {
          label.push(i)
          rtData.push(0)
        }
      }
      data.forEach((row) => {
        label.forEach((v, i) => {
          if (v === row.mm && clcd === row.dfdt_cl_cd) {
            rtData[i] = rtData[i] + row.cnt
          }
        })
      })
      return rtData
    },
    schReset1() {
      const user = this.$store.getters['auth/getUser']
      this.cat1Val = ['AP01']
      this.chkWkpl1()

      // this.schData.period1 = '2'
      // this.schData.period2 = this.getTzDate(user.time_zone).substr(0, 4)

      this.$nextTick(() => {
        this.schData.period1 = '2'
        this.schData.period2 = this.getTzDate(user.time_zone).substr(0, 4)
      })
    },
    schReset2() {
      const user = this.$store.getters['auth/getUser']
      this.cat2Val = ['AP01']
      this.chkWkpl2()
      this.schData.sDate = this.getTzDate(user.time_zone, '-', 0, 11).substr(0, 7)
      this.schData.eDate = this.getTzDate(user.time_zone, '-').substr(0, 7)
    },
    goList() {
      const obj = {}
      obj.sDate = this.schData.sDate + '-01'
      obj.eDate = this.schData.eDate + '-' + new Date(this.schData.eDate.substr(0, 4), this.schData.eDate.substr(5, 2), 0).getDate()
      obj.acdt_prst_cd = '6'
      this.$router.push({
        name: `acdtmgnt-acdtmgntlist`,
        params: {
          schData: obj,
        },
      })
    },
  },
}
</script>
<style scoped>
.router-tab-page {
  padding: 0;
}
.container_homeui {
  background: #ecedf2;
  padding: 0 64px 64px 64px;
}
.homeui {
  margin: 0 auto;
  min-width: 1280px;
  /* max-width: 1340px; */
  padding-top: 30px;
}
.con_padd {
  max-width: 30px;
  width: 30px;
}
.con_half {
  position: relative;
}
.con_half {
  flex: 50%;
}
.mrB10 {
  margin-bottom: 10px;
}
.con_half h3 {
  font-size: 18px;
  /* margin-bottom: 14px; */
  color: #222222;
}
.con_half h4 {
  font-size: 16px;
  margin: 32px 0 12px 0;
  color: #222222;
}
.con_half h4:before {
  content: '';
  width: 4px;
  height: 4px;
  background: #777;
  margin-bottom: 3px;
  margin-right: 6px;
  display: inline-block;
}
.con_half h5 {
  font-size: 13px;
  margin: 0 0 16px 0;
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
.con_half .step {
  background: #fff;
  border-radius: 4px;
  padding: 48px;
  border-bottom: 1px solid #ededed;
}
.con_half .step .step_tit {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #777;
  padding-bottom: 8px;
}
.con_half .step_tit .info {
  background: transparent !important;
  font-size: 14px;
  align-self: center;
  /* color: #777; */
}
.con_half .step_btm .info {
  background: transparent !important;
  font-size: 14px;
  text-align: right;
  /* color: #777; */
}
.con_half .group_padd {
  margin-top: 35px;
}
.main-color {
  background: #062c5e !important;
  color: rgba(255, 255, 255, 0.85);
}
.new_group {
  display: flex;
  justify-content: space-between;
}
.new_group table {
  width: 100%;
  border-collapse: collapse;
  border-top: 1px solid #777;
  border-bottom: 1px solid #dcdcdc;
}
.new_group table th {
  height: 36px;
}
.new_group table tr td {
  height: 30px;
  border: 1px solid #eee;
  text-align: right;
  font-size: 15px;
  padding: 0 10px;
  font-weight: bold;
  color: #062c5e;
}
.new_group table tr td:first-child {
  text-align: left;
  font-weight: bold;
  color: #444;
  border-left: 0px none;
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

.step .sub_title {
  align-items: center;
}

.shut_wrap {
  display: flex;
  flex-direction: row;
}
.shut_wrap .inbox {
  width: 100%;
}
.shut_wrap .gap {
  min-width: 32px;
}
.dp_grid {
  display: grid;
  grid-template-columns: repeat(3, 30%);
  grid-gap: 48px 5%;
}
.dp_grid .item {
  width: 100%;
}
.dp_grid .item .chart_pie {
  width: 80%;
  margin: 0 auto;
}
.dp_grid .item table {
  width: 100%;
  margin: 0 auto;
}
.dp_grid .item table td {
  text-align: center;
  font-weight: bold;
  padding: 0 0 8px 0;
  color: #294c87;
  font-size: 15px;
}
.dp_grid .item table td:before {
  content: '';
  width: 4px;
  height: 4px;
  background-color: #777;
  display: inline-block;
  margin-right: 6px;
}
.dp_grid .item table td h4 {
  margin-bottom: 12px;
}
.dp_grid .item table td span {
  font-size: 13px;
  color: #444;
}
.dp_grid-4 {
  display: grid;
  grid-template-columns: 25% 22% 23.5% 23.5%;
  grid-gap: 0 2%;
}
.dp_grid-4 .item {
  width: 99%;
}
.dp_grid-4 .item table {
  width: 100%;
  border-collapse: collapse;
}
.dp_grid-4 .item table th,
.dp_grid-4 .item table td {
  padding: 6px 8px;
  border-bottom: 1px solid #ededed;
  border-right: 1px solid #ededed;
}
.dp_grid-4 .item table th,
.dp_grid-4 .item table td {
  padding: 4px 8px;
  border: 1px solid #ededed;
}
.ap_total {
  display: flex;
}
.ap_total_item01 {
  display: flex;
  width: 1020px;
}
.ap_total_item01 .item {
  width: 162px;
  margin-right: 8px;
}
.ap_total_item02 {
  display: flex;
  width: 100%;
  overflow-x: auto;
  position: relative;
}
.ap_total_item02 .item {
  width: 180px;
  margin-right: 8px;
}
.ap_total_item02 .item:last-child {
  margin-right: 0;
}
.ap_total_item02 >>> .v-btn {
  width: 12px;
  min-width: 12px;
  height: 100%;
  text-align: center;
  padding: 0;
  position: relative;
  left: 0;
}
.ap_total_item02 .show:after {
  content: '';
  width: 0px;
  height: 0px;
  position: absolute;
  right: 4px;
  border-top: 5px solid transparent;
  border-right: 5px solid #666666;
  border-bottom: 5px solid transparent;
  border-left: 5px solid transparent;
  box-shadow: none;
}
.ap_total_item02 .hide:after {
  content: '';
  width: 0px;
  height: 0px;
  position: absolute;
  left: 4px;
  border-top: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 5px solid transparent;
  border-left: 5px solid #666666;
  box-shadow: none;
}
.ap_total_item02::-webkit-scrollbar-thumb {
  border-radius: 10px;
}
.sch_zone {
  display: flex;
  background-color: #f7f7f7;
  padding: 32px 16px 16px 16px;
  align-items: center;
}
.sch_zone .v-text-field.v-text-field {
  background-color: #fff;
}
.sch_zone p {
  margin: 0;
  font-weight: bold;
}
.sch_zone-group {
  position: relative;
  display: flex;
  width: 100%;
}
.sch_zone-group >>> .v-input__append-inner {
  align-self: center;
  margin: 0 !important;
}
.select_title {
  position: absolute;
  left: 0;
  top: -22px;
  font-size: 13px;
}
.sch_zone >>> .v-select__selections > span {
  max-width: 40%;
}
.sch_zone >>> .v-chip__content span {
  text-overflow: ellipsis;
  overflow: hidden;
  max-width: 100%;
  white-space: nowrap;
}
.sch_zone >>> .v-select__selections input {
  display: none;
}
.chart_group {
  width: 100%;
}
.chart_module {
  border: 1px solid #ededed;
  padding: 16px;
}
.chart_group .chart_tit {
  display: flex;
  align-items: center;
}
.chart_group .chart_tit p {
  margin: 0 8px 0 0;
}
.board_list2 .chart canvas {
  width: 200px !important;
}

.bg_gray {
  background: #f6f6f6;
}
.title_bar {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
.legend {
  margin-bottom: 8px;
}
.legend span {
  margin-left: 16px;
}
.legend span::before {
  content: '';
  width: 8px;
  height: 8px;
  display: inline-block;
  background-color: #142c61;
  align-self: center;
  margin: 0 4px 0 0;
}
.legend span:last-child:before {
  content: '';
  width: 8px;
  height: 8px;
  display: inline-block;
  background-color: #668cd6;
  align-self: center;
  margin: 0 4px 0 0;
}
.dp_none {
  display: none;
}
</style>
