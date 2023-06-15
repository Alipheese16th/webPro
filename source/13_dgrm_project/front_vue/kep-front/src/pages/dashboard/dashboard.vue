<!-- eslint-disable vue/no-v-html -->
<template>
  <div>
    <div class="container_homeui">
      <div class="homeui">
        <div class="con_half">
          <div class="step">
            <div class="step_tit">
              <!-- 사업장 작업현황-->
              <h3>[{{ $t('사업장 작업현황') }}] {{ currentDateTime }}</h3>
              <!-- 단위:건수 -->
              <div class="info">({{ $t('LB00000470') }}:{{ $t('LB00001251') }})</div>
            </div>
            <div class="mrB20"></div>
            <div class="shut_wrap">
              <div class="inbox" style="max-width: 50%">
                <div style="width: 100%; height: 420px">
                  <v-img :src="imgUrl" width="100%" height="100%" class="img_location">
                    <div class="dp_flex" style="position: absolute; opacity: 0.9; top: 10px; left: 10px">
                      <v-select
                        ref="cmpy_cd"
                        v-model="schData.cmpy_cd"
                        item-text="cd_nm"
                        item-value="cd"
                        :items="codes.CO00000009"
                        outlined
                        single-line
                        :hide-details="true"
                        dense
                        height="30"
                        style="background: rgb(255, 255, 255); border: 1px solid #062c5e"
                        @change="chgCode"
                      ></v-select>
                      <div class="mrR8"></div>
                      <v-select
                        v-model="schData.wkpl_id"
                        item-text="cd_nm"
                        item-value="cd"
                        :items="F_codes.F_00000001"
                        outlined
                        single-line
                        :hide-details="true"
                        dense
                        height="30"
                        style="background: rgb(255, 255, 255); border: 1px solid #062c5e"
                        @change="chgImg"
                      ></v-select>
                    </div>
                    <div class="new_group" style="position: absolute; opacity: 0.9; width: calc(100% - 20px); margin: 0 0px 10px 10px; bottom: 0; left: 0; background: #ffffff">
                      <table class="float_tbl">
                        <colgroup>
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                          <col width="12.5%" />
                        </colgroup>
                        <thead>
                          <tr>
                            <!-- 화기 -->
                            <th class="bg_gray">{{ $t('LB00000881') }}</th>
                            <!-- 고소 -->
                            <th class="bg_gray">{{ $t('LB00000882') }}</th>
                            <!-- 전기 -->
                            <th class="bg_gray">{{ $t('LB00001281') }}</th>
                            <!-- 밀폐 -->
                            <th class="bg_gray">{{ $t('LB00000883') }}</th>
                            <!-- 중장비 -->
                            <th class="bg_gray">{{ $t('LB00001274') }}</th>
                            <!-- 위험물질 -->
                            <th class="bg_gray">{{ $t('위험물질') }}</th>
                            <!-- 일반 -->
                            <th class="bg_gray">{{ $t('LB00000887') }}</th>
                            <!-- 합계 -->
                            <th class="bg_gray">{{ $t('LB00001273') }}</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td class="right">{{ oprnLocTot.cnt1 }}</td>
                            <td>{{ oprnLocTot.cnt2 }}</td>
                            <td>{{ oprnLocTot.cnt3 }}</td>
                            <td>{{ oprnLocTot.cnt4 }}</td>
                            <td>{{ oprnLocTot.cnt5 }}</td>
                            <td>{{ oprnLocTot.cnt6 }}</td>
                            <td>{{ oprnLocTot.cnt7 }}</td>
                            <td>{{ oprnLocTot.tot }}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <v-menu v-for="(item, index) in oprnLocList" :key="index" v-model="item.index" :close-on-click="false" :close-on-content-click="false">
                      <template v-slot:activator="{ on, attrs }">
                        <div :style="{ position: 'absolute', top: item.wkpl_loc_ycrd + '%', left: item.wkpl_loc_xcrd + '%' }">
                          <span
                            v-bind="attrs"
                            :class="[
                              item.cnt1 + item.cnt2 + item.cnt3 + item.cnt4 + item.cnt5 + item.cnt6 + item.cnt7 > 2
                                ? 'material-symbols-outlined place-red'
                                : 'material-symbols-outlined place',
                            ]"
                            v-on="on"
                            >location_on</span
                          >
                        </div>
                      </template>
                      <h4>{{ item.wkpl_loc_nm }}</h4>
                      <v-btn outlined class="btn_cls" @click="item.index = !item.index"><v-icon class="material-icons">close</v-icon></v-btn>
                      <v-list class="menu_list">
                        <v-list-item>
                          <v-list-item-content>
                            <v-simple-table>
                              <tbody>
                                <tr>
                                  <!-- 화기 -->
                                  <td>{{ $t('LB00000881') }}</td>
                                  <td>{{ item.cnt1 }}</td>
                                </tr>
                                <tr>
                                  <!-- 고소 -->
                                  <td>{{ $t('LB00000882') }}</td>
                                  <td>{{ item.cnt2 }}</td>
                                </tr>
                                <tr>
                                  <!-- 전기 -->
                                  <td>{{ $t('LB00001281') }}</td>
                                  <td>{{ item.cnt3 }}</td>
                                </tr>
                                <tr>
                                  <!-- 밀폐 -->
                                  <td>{{ $t('LB00000883') }}</td>
                                  <td>{{ item.cnt4 }}</td>
                                </tr>
                                <tr>
                                  <!-- 중장비 -->
                                  <td>{{ $t('LB00001274') }}</td>
                                  <td>{{ item.cnt5 }}</td>
                                </tr>
                                <tr>
                                  <!-- 위험물질 -->
                                  <td>{{ $t('위험물질') }}</td>
                                  <td>{{ item.cnt6 }}</td>
                                </tr>
                                <tr>
                                  <!-- 일반 -->
                                  <td>{{ $t('LB00000887') }}</td>
                                  <td>{{ item.cnt7 }}</td>
                                </tr>
                              </tbody>
                            </v-simple-table>
                          </v-list-item-content>
                        </v-list-item>
                      </v-list>
                    </v-menu>
                  </v-img>
                </div>
              </div>
              <div class="gap guide_line"></div>
              <div class="inbox">
                <div class="warning_task">
                  <div class="item">
                    <p>{{ $t('위험작업') }}</p>
                    <p>{{ planRsltTotData.t_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 화기 -->
                    <p>{{ $t('LB00000881') }}</p>
                    <p>{{ planRsltTotData.f_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 고소 -->
                    <p>{{ $t('LB00000882') }}</p>
                    <p>{{ planRsltTotData.g_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 전기 -->
                    <p>{{ $t('LB00001281') }}</p>
                    <p>{{ planRsltTotData.e_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 밀폐 -->
                    <p>{{ $t('LB00000883') }}</p>
                    <p>{{ planRsltTotData.m_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 중장비 -->
                    <p>{{ $t('LB00001274') }}</p>
                    <p>{{ planRsltTotData.h_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 위험물질 -->
                    <p>{{ $t('위험물질') }}</p>
                    <p>{{ planRsltTotData.d_cnt }}</p>
                  </div>
                  <div class="item">
                    <!-- 일반 -->
                    <p>{{ $t('LB00000887') }}</p>
                    <p>{{ planRsltTotData.n_cnt }}</p>
                  </div>
                </div>
                <div class="mrB10"></div>
                <div class="new_group">
                  <table class="table02">
                    <colgroup>
                      <col />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                      <col width="10%" />
                    </colgroup>
                    <thead>
                      <tr>
                        <!-- 구분 -->
                        <th class="bg_gray">{{ $t('LB00000741') }}</th>
                        <!-- 합계 -->
                        <th class="bg_gray">{{ $t('LB00001273') }}</th>
                        <!-- 화기 -->
                        <th class="bg_gray">{{ $t('LB00000881') }}</th>
                        <!-- 고소 -->
                        <th class="bg_gray">{{ $t('LB00000882') }}</th>
                        <!-- 전기 -->
                        <th class="bg_gray">{{ $t('LB00001281') }}</th>
                        <!-- 밀폐 -->
                        <th class="bg_gray">{{ $t('LB00000883') }}</th>
                        <!-- 중장비 -->
                        <th class="bg_gray">{{ $t('LB00001274') }}</th>
                        <!-- 위험물질 -->
                        <th class="bg_gray">{{ $t('위험물질') }}</th>
                        <!-- 일반 -->
                        <th class="bg_gray">{{ $t('LB00000887') }}</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>제조</td>
                        <td class="bg_gray">{{ planRsltData.t_cnt_s }}</td>
                        <td>{{ planRsltData.f_cnt_s }}</td>
                        <td>{{ planRsltData.g_cnt_s }}</td>
                        <td>{{ planRsltData.e_cnt_s }}</td>
                        <td>{{ planRsltData.m_cnt_s }}</td>
                        <td>{{ planRsltData.h_cnt_s }}</td>
                        <td>{{ planRsltData.d_cnt_s }}</td>
                        <td>{{ planRsltData.n_cnt_s }}</td>
                      </tr>
                      <tr>
                        <td>연구소</td>
                        <td class="bg_gray">{{ planRsltData.t_cnt_r }}</td>
                        <td>{{ planRsltData.f_cnt_r }}</td>
                        <td>{{ planRsltData.g_cnt_r }}</td>
                        <td>{{ planRsltData.e_cnt_r }}</td>
                        <td>{{ planRsltData.m_cnt_r }}</td>
                        <td>{{ planRsltData.h_cnt_r }}</td>
                        <td>{{ planRsltData.d_cnt_r }}</td>
                        <td>{{ planRsltData.n_cnt_r }}</td>
                      </tr>
                      <tr>
                        <!-- AP 비제조 -->
                        <td>{{ $t('LB00001238') }}</td>
                        <td class="bg_gray">{{ planRsltData.t_cnt_b }}</td>
                        <td>{{ planRsltData.f_cnt_b }}</td>
                        <td>{{ planRsltData.g_cnt_b }}</td>
                        <td>{{ planRsltData.e_cnt_b }}</td>
                        <td>{{ planRsltData.m_cnt_b }}</td>
                        <td>{{ planRsltData.h_cnt_b }}</td>
                        <td>{{ planRsltData.d_cnt_b }}</td>
                        <td>{{ planRsltData.n_cnt_b }}</td>
                      </tr>
                      <tr>
                        <!-- 생산자회사 -->
                        <td>{{ $t('LB00001254') }}</td>
                        <td class="bg_gray">{{ planRsltData.t_cnt_m }}</td>
                        <td>{{ planRsltData.f_cnt_m }}</td>
                        <td>{{ planRsltData.g_cnt_m }}</td>
                        <td>{{ planRsltData.e_cnt_m }}</td>
                        <td>{{ planRsltData.m_cnt_m }}</td>
                        <td>{{ planRsltData.h_cnt_m }}</td>
                        <td>{{ planRsltData.d_cnt_m }}</td>
                        <td>{{ planRsltData.n_cnt_m }}</td>
                      </tr>
                      <tr>
                        <!-- 판매자회사 -->
                        <td>{{ $t('LB00001255') }}</td>
                        <td class="bg_gray">{{ planRsltData.t_cnt_p }}</td>
                        <td>{{ planRsltData.f_cnt_p }}</td>
                        <td>{{ planRsltData.g_cnt_p }}</td>
                        <td>{{ planRsltData.e_cnt_p }}</td>
                        <td>{{ planRsltData.m_cnt_p }}</td>
                        <td>{{ planRsltData.h_cnt_p }}</td>
                        <td>{{ planRsltData.d_cnt_p }}</td>
                        <td>{{ planRsltData.n_cnt_p }}</td>
                      </tr>
                    </tbody>
                  </table>
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
              <h3>{{ $t('지역별 기상정보') }}</h3>
            </div>
            <div class="dp_grid-3">
              <div class="item">
                <h4>
                  지역날씨 <em class="font-red" style="font-style: normal">{{ wtData.news }}</em>
                </h4>
                <div class="weather">
                  <div class="item">
                    <h5>지역</h5>
                    <div>
                      <v-select
                        v-model="mRegion.val"
                        :items="region"
                        item-text="cd_nm"
                        item-value="cd"
                        :hide-details="true"
                        style="align-items: center; width: 100%"
                        height="24"
                        outlined
                        dense
                        @change="selectApi"
                      ></v-select>
                    </div>
                  </div>
                  <div class="item">
                    <h5>
                      날씨<em>({{ wtData.sky }})</em>
                    </h5>
                    <div class="">
                      <div
                        :class="[
                          wtData.sky === '맑음'
                            ? 'weather_sunny'
                            : wtData.sky === '흐림'
                            ? 'weather_partly_cloudy'
                            : wtData.sky === '구름'
                            ? 'weather_cloudy'
                            : wtData.sky === '비'
                            ? 'weather_rain'
                            : wtData.sky === '눈'
                            ? 'weather_snow'
                            : wtData.sky === '비/눈'
                            ? 'weather_snow_rain'
                            : '',
                        ]"
                      ></div>
                    </div>
                  </div>
                  <div class="item">
                    <h5>온도</h5>
                    <div class="">
                      <p>{{ wtData.temp }}℃</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>습도</h5>
                    <div class="">
                      <p>{{ wtData.hum }}%</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>바람</h5>
                    <div class="">
                      <p>{{ wtData.wind1 }}</p>
                      <p>{{ wtData.wind2 }}m/s</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>시간 강수량</h5>
                    <div class="">
                      <p>{{ wtData.rain }}mm</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>미세먼지</h5>
                    <div class="">
                      <p>{{ wtData.dust1 }}㎍/m3</p>
                      <p>{{ wtData.dust2 }}</p>
                    </div>
                  </div>
                </div>
                <h5 style="text-align: right; font-size: 8px">
                  *데이터는 실시간 관측된 자료이며 측정소 현지 사정이나 데이터의 수신상태에 따라 미수신 될 수 있음. <br />
                  출처 : 환경부/한국환경공단 <a href="https://www.weather.go.kr" target="_blank" style="color: #218fde; text-decoration: none">(기상청바로가기)</a>
                </h5>
              </div>
              <div class="item">
                <!-- 오늘의 안전관리 포인트-->
                <div class="weather_info">
                  <h4>{{ $t('오늘의 안전관리 포인트') }}</h4>
                  <v-btn v-show="isAdmin" class="btn_set" height="24" outlined @click="openWtMsg">
                    <span class="material-symbols-outlined font-gray" title="오늘의 안전관리 포인트 설정"> settings </span>
                  </v-btn>
                </div>
                <ul class="weather_info_list">
                  <li v-for="(msg, idx) in wtData.info" :key="idx">{{ msg }}</li>
                </ul>
              </div>
              <div class="item">
                <!-- 생활기상지수-->
                <h4>{{ $t('생활기상지수') }}</h4>
                <div class="weather">
                  <div class="item">
                    <h5>자외선지수</h5>
                    <div>
                      <span
                        :class="[
                          wtData.uv === '낮음' ? 'material-symbols-outlined font-blue' : wtData.uv === '보통' || wtData.uv === '-' ? '' : 'material-symbols-outlined font-red',
                        ]"
                      >
                        {{ wtData.uv === '낮음' ? 'arrow_drop_down' : wtData.uv === '보통' || wtData.uv === '-' ? '' : 'arrow_drop_up' }}
                      </span>
                      <p :class="[wtData.uv === '낮음' ? 'font-blue' : wtData.uv === '보통' || wtData.uv === '-' ? '' : 'font-red']">{{ wtData.uv }}</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>체감온도</h5>
                    <div>
                      <span
                        :class="[
                          wtData.st === '낮음' ? 'material-symbols-outlined font-blue' : wtData.st === '관심' || wtData.st === '-' ? '' : 'material-symbols-outlined font-red',
                        ]"
                      >
                        {{ wtData.st === '낮음' ? 'arrow_drop_down' : wtData.st === '관심' || wtData.st === '-' ? '' : 'arrow_drop_up' }}
                      </span>
                      <p :class="[wtData.st === '낮음' ? 'font-blue' : wtData.st === '관심' || wtData.st === '-' ? '' : 'font-red']">{{ wtData.st }}</p>
                    </div>
                  </div>
                  <div class="item">
                    <h5>동파지수</h5>
                    <div>
                      <span
                        :class="[
                          wtData.freeze === '낮음'
                            ? 'material-symbols-outlined font-blue'
                            : wtData.freeze === '보통' || wtData.freeze === '-'
                            ? ''
                            : 'material-symbols-outlined font-red',
                        ]"
                      >
                        {{ wtData.freeze === '낮음' ? 'arrow_drop_down' : wtData.freeze === '보통' || wtData.freeze === '-' ? '' : 'arrow_drop_up' }}
                      </span>
                      <p :class="[wtData.freeze === '낮음' ? 'font-blue' : wtData.freeze === '보통' || wtData.freeze === '-' ? '' : 'font-red']">{{ wtData.freeze }}</p>
                    </div>
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
            <!-- AP Total -->
            <div class="step_tit">
              <h3>{{ $t('사고현황 (Total)') }}</h3>
              <div class="legend">
                <span>자사</span>
                <span>협력사</span>
              </div>
            </div>
            <div class="mrB20"></div>
            <div class="dp_grid-6">
              <div class="item">
                <div class="chart_module">
                  <h5>전사</h5>
                  <bar-chart v-if="loaded" :data="chartData1" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
                </div>
              </div>
              <div class="item">
                <!-- AP 제조 -->
                <div class="chart_module">
                  <h5>제조</h5>
                  <bar-chart v-if="loaded" :data="chartData2_1" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
                </div>
              </div>
              <div class="item">
                <!-- AP R&I -->
                <div class="chart_module">
                  <h5>연구소</h5>
                  <bar-chart v-if="loaded" :data="chartData2_2" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
                </div>
              </div>
              <div class="item">
                <!-- AP 비제조 -->
                <div class="chart_module">
                  <h5>비제조</h5>
                  <bar-chart v-if="loaded" :data="chartData2_3" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
                </div>
              </div>
              <div class="item">
                <!-- 생산자회사 -->
                <div class="chart_module">
                  <h5>{{ $t('생산회사') }}</h5>
                  <bar-chart v-if="loaded" :data="chartData2_4" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
                </div>
              </div>
              <div class="item">
                <!-- 판매자회사 -->
                <div class="chart_module">
                  <h5>{{ $t('판매회사') }}</h5>
                  <bar-chart v-if="loaded" :data="chartData2_5" :options="barOptions" style="width: 150px; height: 220px"></bar-chart>
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
              <h3>{{ $t('사고현황 (사업장별)') }}</h3>
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
                      <v-select ref="period1" v-model="schData.period1" :items="period1" item-text="txt" item-value="val" outlined dense :hide-details="true" height="24">
                      </v-select>
                    </div>
                    <mix-chart v-if="loaded" ref="chart3" :data="chartData3" :options="mixOptions" style="width: 100%; height: 220px"></mix-chart>
                  </div>
                </div>
              </div>
              <div class="gap"></div>
              <div class="inbox" style="width: 50%">
                <div class="chart_group">
                  <div class="chart_module">
                    <div class="chart_tit" style="width: 200px; margin-bottom: 16px">
                      <p class="bold" style="min-width: 32px">{{ $t('월별') }}</p>
                      <v-select ref="period2" v-model="schData.period2" :items="period2" item-text="txt" item-value="val" outlined dense :hide-details="true" height="24">
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
            <div class="shut_wrap">
              <div class="inbox">
                <div class="step_tit">
                  <!-- 투자예산 실적 -->
                  <h3>{{ $t('LB00001261') }}</h3>
                  <!-- 단위:억원 -->
                  <div class="info">{{ $t('LB00000470') }}:{{ $t('LB00001262') }}</div>
                </div>
                <div class="mrB30"></div>
                <div class="shut_wrap">
                  <div class="inbox" style="max-width: 30%">
                    <div class="invest mrR8">
                      <h5>전사</h5>
                      <div class="item" style="max-height: 38%">
                        <div>
                          <!-- 연간계획 -->
                          <p class="total">{{ invsTotData.plan_amt }}</p>
                          <p class="tit">{{ $t('LB00001263') }}</p>
                        </div>
                        <div>
                          <!-- 집행실적 -->
                          <p class="total">{{ invsTotData.rslt_amt }}</p>
                          <p class="tit">{{ $t('LB00001264') }}</p>
                        </div>
                      </div>
                      <div class="item">
                        <!-- 집행율 -->
                        <p class="total">{{ invsTotData.amt_per }}%</p>
                        <p class="tit">{{ $t('LB00001265') }}</p>
                      </div>
                    </div>
                  </div>
                  <div class="inbox">
                    <div class="new_group">
                      <table class="" style="border-top: 1px solid #ccc">
                        <colgroup>
                          <col />
                          <col width="20%" />
                          <col width="20%" />
                          <col width="20%" />
                        </colgroup>
                        <thead>
                          <tr>
                            <!-- 구분 -->
                            <th colspan="2" class="bg_gray">{{ $t('LB00000741') }}</th>
                            <!-- 계획 -->
                            <th class="bg_gray">{{ $t('LB00001266') }}</th>
                            <!-- 실적 -->
                            <th class="bg_gray">{{ $t('LB00001060') }}</th>
                            <!-- 집행율 -->
                            <th class="bg_gray">{{ $t('LB00001265') }}</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td rowspan="4" class="bg_gray">관계사</td>
                            <td class="bg_gray">전체</td>
                            <td>{{ invsData.plan_amt_sum1 }}</td>
                            <td>{{ invsData.rslt_amt_sum1 }}</td>
                            <td>{{ invsData.amt_per_sum1 }}%</td>
                          </tr>
                          <tr>
                            <td>제조</td>
                            <td>{{ invsData.plan_amt_s }}</td>
                            <td>{{ invsData.rslt_amt_s }}</td>
                            <td>{{ invsData.amt_per_s }}%</td>
                          </tr>
                          <tr>
                            <td>연구소</td>
                            <td>{{ invsData.plan_amt_r }}</td>
                            <td>{{ invsData.rslt_amt_r }}</td>
                            <td>{{ invsData.amt_per_r }}%</td>
                          </tr>
                          <tr>
                            <!-- AP 비제조 -->
                            <td>{{ $t('LB00001238') }}</td>
                            <td>{{ invsData.plan_amt_b }}</td>
                            <td>{{ invsData.rslt_amt_b }}</td>
                            <td>{{ invsData.amt_per_b }}%</td>
                          </tr>
                          <tr>
                            <td rowspan="3" class="bg_gray">자회사</td>
                            <td class="bg_gray">전체</td>
                            <td>{{ invsData.plan_amt_sum2 }}</td>
                            <td>{{ invsData.rslt_amt_sum2 }}</td>
                            <td>{{ invsData.amt_per_sum2 }}%</td>
                          </tr>
                          <tr>
                            <!-- 생산자회사 -->
                            <td>{{ $t('LB00001254') }}</td>
                            <td>{{ invsData.plan_amt_m }}</td>
                            <td>{{ invsData.rslt_amt_m }}</td>
                            <td>{{ invsData.amt_per_m }}%</td>
                          </tr>
                          <tr>
                            <!-- 판매자회사 -->
                            <td>{{ $t('LB00001255') }}</td>
                            <td>{{ invsData.plan_amt_p }}</td>
                            <td>{{ invsData.rslt_amt_p }}</td>
                            <td>{{ invsData.amt_per_p }}%</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <div class="gap"></div>
              <div class="inbox">
                <div class="step_tit">
                  <!-- 안전점검 현황 -->
                  <h3>{{ $t('LB00001241') }}</h3>
                </div>
                <div class="safety">
                  <div class="item">
                    <!-- 작업시작전 점검 -->
                    <h4>{{ $t('LB00001048') }}: {{ chartData5Per }}%</h4>
                    <div class="chart_pie" style="width: 90%; margin: 0 auto">
                      <div class=""><pie-chart v-if="loaded" ref="chart5" :data="chartData5" :options="pieOptions" style="height: 260px"></pie-chart></div>
                    </div>
                  </div>
                  <div class="gap"></div>
                  <div class="item">
                    <!-- 작업장 순회점검 -->
                    <h4>{{ $t('작업장 순회점검') }}: {{ chartData6Per }}%</h4>
                    <div class="chart_pie" style="width: 90%; margin: 0 auto">
                      <div class=""><pie-chart v-if="loaded" ref="chart6" :data="chartData6" :options="pieOptions" style="height: 260px"></pie-chart></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <wtmsg ref="wtmsg" />
  </div>
</template>

<script>
import wtmsg from './wtmsgsetpopup'
import BarChart from '~/components/BarChart.js'
import MixChart from '~/components/MixChart.js'
import PieChart from '~/components/PieChart.js'

export default {
  meta: {
    title: '안전/보건 Main',
  },
  components: {
    BarChart,
    MixChart,
    PieChart,
    wtmsg,
  },
  data() {
    return {
      loaded: false,
      isAdmin: false,
      imgUrl: '',
      codeid: ['ST00000023', 'CO00000009', 'ST00000022'],
      codes: {
        CO00000009: [], // 회사코드
        ST00000022: [{ cd: '', cd_nm: '' }],
      },
      FF_00000001: [],
      F_codes: {
        F_00000001: [],
      },
      region: [],
      mRegion: {},
      wtData: {},
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
      currentDateTime: '',
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
      pieOptions: {
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        cutoutPercentage: 30,
        plugins: {
          datalabels: {
            // align: 'center',
            rotation: 0,
            font: {
              size: 11,
              weight: 'bold',
            },
            color: 'white',
            formatter(value, context) {
              // const nm = context.chart.config.data.labels[context.dataIndex]
              // return value > 0 ? nm + ' ' + value + '%' : ''
              return value > 0 ? value + '%' : ''
            },
          },
        },
      },
      barColor: ['#142C61', '#587EC8'],
      mixColor: ['#142C61', '#587EC8'],
      pieColor: ['#587EC8', '#142C61', '#1ea185', '#9bbb5c', '#f39b26'],
      chartData1: {},
      chartData2_1: {},
      chartData2_2: {},
      chartData2_3: {},
      chartData2_4: {},
      chartData2_5: {},
      chartData3: {},
      chartData4: {},
      chartData5: {},
      chartData5Per: 0,
      chartData6: {},
      chartData6Per: 0,
      invsData: {},
      invsTotData: {},
      planRsltData: {},
      planRsltTotData: {},
      oprnLocList: [],
      oprnLocTot: {},
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
  },
  mounted() {
    const user = this.$store.getters['auth/getUser']
    user.roleTypeCds.forEach((e) => {
      if (e === 'M01') {
        this.isAdmin = true
      }
    })
    this.schData.cmpy_cd = user.cmpn_cd
    this.schData.wkpl_id = user.wkpl_id
    this.currentShortYear = this.getTzDate(user.time_zone).substr(2, 2)
    const dt = this.getTzDateTime(user.time_zone)
    const week = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
    const dow = week[this.getTzDateObj(user.time_zone).getDay()]
    this.currentDateTime = dt.substr(4, 2) + '월 ' + dt.substr(6, 2) + '일 ' + dow + ' ' + dt.substr(9, 2) + '시 ' + dt.substr(12, 2) + '분 기준'

    // 사업장 조회
    this.$axios
      .$get(`/api/v1/comm/wkpl`, {})
      .then((res) => {
        this.FF_00000001 = res
        this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT' })
        const cmpy = this.schData.cmpy_cd
        this.chgCode(cmpy, this.schData.wkpl_id)
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.region = res.ST00000023

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

        this.select().then(() => {
          this.selectApi(this.mRegion.val)
        })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    async select() {
      this.loaded = false
      this.schData.kind = 'select'
      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info`, { params: this.schData })
      this.cat1 = this.codes.ST00000022
      // this.cat1 = data.apCat
      this.cat2 = data.apCat
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

      this.mRegion.val = data.rgnCd

      // 사업장별 안전작업허가 현황
      this.oprnLocList = data.oprnLocList
      this.oprnLocTot = data.oprnLocTot[0]
      this.setImgUrl()

      // 안전작업허가 현황
      this.planRsltTotData = data.planRsltTot[0]
      for (let i = 0; i < data.planRsltlist.length; i++) {
        // 제조
        if (data.planRsltlist[i].gb_cd === 'SG02') {
          this.planRsltData.f_cnt_s = data.planRsltlist[i].f_cnt
          this.planRsltData.g_cnt_s = data.planRsltlist[i].g_cnt
          this.planRsltData.m_cnt_s = data.planRsltlist[i].m_cnt
          this.planRsltData.e_cnt_s = data.planRsltlist[i].e_cnt
          this.planRsltData.h_cnt_s = data.planRsltlist[i].h_cnt
          this.planRsltData.t_cnt_s = data.planRsltlist[i].t_cnt
          this.planRsltData.d_cnt_s = data.planRsltlist[i].d_cnt
          this.planRsltData.n_cnt_s = data.planRsltlist[i].n_cnt
        }
        // R&I
        if (data.planRsltlist[i].gb_cd === 'SG05') {
          this.planRsltData.f_cnt_r = data.planRsltlist[i].f_cnt
          this.planRsltData.g_cnt_r = data.planRsltlist[i].g_cnt
          this.planRsltData.m_cnt_r = data.planRsltlist[i].m_cnt
          this.planRsltData.e_cnt_r = data.planRsltlist[i].e_cnt
          this.planRsltData.h_cnt_r = data.planRsltlist[i].h_cnt
          this.planRsltData.t_cnt_r = data.planRsltlist[i].t_cnt
          this.planRsltData.d_cnt_r = data.planRsltlist[i].d_cnt
          this.planRsltData.n_cnt_r = data.planRsltlist[i].n_cnt
        }
        // 비제조
        if (data.planRsltlist[i].gb_cd === 'SG04') {
          this.planRsltData.f_cnt_b = data.planRsltlist[i].f_cnt
          this.planRsltData.g_cnt_b = data.planRsltlist[i].g_cnt
          this.planRsltData.m_cnt_b = data.planRsltlist[i].m_cnt
          this.planRsltData.e_cnt_b = data.planRsltlist[i].e_cnt
          this.planRsltData.h_cnt_b = data.planRsltlist[i].h_cnt
          this.planRsltData.t_cnt_b = data.planRsltlist[i].t_cnt
          this.planRsltData.d_cnt_b = data.planRsltlist[i].d_cnt
          this.planRsltData.n_cnt_b = data.planRsltlist[i].n_cnt
        }
        // 생산자회사
        if (data.planRsltlist[i].gb_cd === 'SG06') {
          this.planRsltData.f_cnt_m = data.planRsltlist[i].f_cnt
          this.planRsltData.g_cnt_m = data.planRsltlist[i].g_cnt
          this.planRsltData.m_cnt_m = data.planRsltlist[i].m_cnt
          this.planRsltData.e_cnt_m = data.planRsltlist[i].e_cnt
          this.planRsltData.h_cnt_m = data.planRsltlist[i].h_cnt
          this.planRsltData.t_cnt_m = data.planRsltlist[i].t_cnt
          this.planRsltData.d_cnt_m = data.planRsltlist[i].d_cnt
          this.planRsltData.n_cnt_m = data.planRsltlist[i].n_cnt
        }
        // 판매자회사
        if (data.planRsltlist[i].gb_cd === 'SG07') {
          this.planRsltData.f_cnt_p = data.planRsltlist[i].f_cnt
          this.planRsltData.g_cnt_p = data.planRsltlist[i].g_cnt
          this.planRsltData.m_cnt_p = data.planRsltlist[i].m_cnt
          this.planRsltData.e_cnt_p = data.planRsltlist[i].e_cnt
          this.planRsltData.h_cnt_p = data.planRsltlist[i].h_cnt
          this.planRsltData.t_cnt_p = data.planRsltlist[i].t_cnt
          this.planRsltData.d_cnt_p = data.planRsltlist[i].d_cnt
          this.planRsltData.n_cnt_p = data.planRsltlist[i].n_cnt
        }
      }

      // AP Total
      this.chartData1.labels = this.makeLabel1()
      this.chartData1.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          data: this.makeData0(data.apTot, '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
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
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG02', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG02', '02'),
        },
      ]

      // AP R&I
      this.chartData2_2.labels = this.makeLabel1()
      this.chartData2_2.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG05', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG05', '02'),
        },
      ]

      // AP 비제조
      this.chartData2_3.labels = this.makeLabel1()
      this.chartData2_3.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG04', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG04', '02'),
        },
      ]

      // 생산자회사
      this.chartData2_4.labels = this.makeLabel1()
      this.chartData2_4.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG06', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG06', '02'),
        },
      ]

      // 판매자회사
      this.chartData2_5.labels = this.makeLabel1()
      this.chartData2_5.datasets = [
        {
          label: '자사',
          backgroundColor: this.barColor[0],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG07', '01'),
        },
        {
          label: '협력사',
          backgroundColor: this.barColor[1],
          barThickness: 24,
          data: this.makeData2(data.apTot, 'SG07', '02'),
        },
      ]

      this.makeChart1(data)

      // 투자예산 실적
      this.invsTotData = data.invsTotData[0]
      this.invsData.plan_amt_sum1 = 0
      this.invsData.rslt_amt_sum1 = 0
      this.invsData.amt_per_sum1 = 0
      this.invsData.plan_amt_sum2 = 0
      this.invsData.rslt_amt_sum2 = 0
      this.invsData.amt_per_sum2 = 0
      for (let i = 0; i < data.invsData.length; i++) {
        // 제조
        if (data.invsData[i].gb_cd === 'SG02') {
          this.invsData.plan_amt_s = data.invsData[i].plan_amt
          this.invsData.rslt_amt_s = data.invsData[i].rslt_amt
          this.invsData.amt_per_s = data.invsData[i].amt_per
          this.invsData.plan_amt_sum1 = (this.invsData.plan_amt_sum1 * 10 + data.invsData[i].plan_amt * 10) / 10
          this.invsData.rslt_amt_sum1 = (this.invsData.rslt_amt_sum1 * 10 + data.invsData[i].rslt_amt * 10) / 10
        }
        // R&I
        if (data.invsData[i].gb_cd === 'SG05') {
          this.invsData.plan_amt_r = data.invsData[i].plan_amt
          this.invsData.rslt_amt_r = data.invsData[i].rslt_amt
          this.invsData.amt_per_r = data.invsData[i].amt_per
          this.invsData.plan_amt_sum1 = (this.invsData.plan_amt_sum1 * 10 + data.invsData[i].plan_amt * 10) / 10
          this.invsData.rslt_amt_sum1 = (this.invsData.rslt_amt_sum1 * 10 + data.invsData[i].rslt_amt * 10) / 10
        }
        // 비제조
        if (data.invsData[i].gb_cd === 'SG04') {
          this.invsData.plan_amt_b = data.invsData[i].plan_amt
          this.invsData.rslt_amt_b = data.invsData[i].rslt_amt
          this.invsData.amt_per_b = data.invsData[i].amt_per
          this.invsData.plan_amt_sum1 = (this.invsData.plan_amt_sum1 * 10 + data.invsData[i].plan_amt * 10) / 10
          this.invsData.rslt_amt_sum1 = (this.invsData.rslt_amt_sum1 * 10 + data.invsData[i].rslt_amt * 10) / 10
        }

        // 생산자회사
        if (data.invsData[i].gb_cd === 'SG06') {
          this.invsData.plan_amt_m = data.invsData[i].plan_amt
          this.invsData.rslt_amt_m = data.invsData[i].rslt_amt
          this.invsData.amt_per_m = data.invsData[i].amt_per
          this.invsData.plan_amt_sum2 = (this.invsData.plan_amt_sum2 * 10 + data.invsData[i].plan_amt * 10) / 10
          this.invsData.rslt_amt_sum2 = (this.invsData.rslt_amt_sum2 * 10 + data.invsData[i].rslt_amt * 10) / 10
        }
        // 판매자회사
        if (data.invsData[i].gb_cd === 'SG07') {
          this.invsData.plan_amt_p = data.invsData[i].plan_amt
          this.invsData.rslt_amt_p = data.invsData[i].rslt_amt
          this.invsData.amt_per_p = data.invsData[i].amt_per
          this.invsData.plan_amt_sum2 = (this.invsData.plan_amt_sum2 * 10 + data.invsData[i].plan_amt * 10) / 10
          this.invsData.rslt_amt_sum2 = (this.invsData.rslt_amt_sum2 * 10 + data.invsData[i].rslt_amt * 10) / 10
        }
      }

      this.invsData.amt_per_sum1 = this.invsData.plan_amt_sum1 === 0 ? 0 : ((this.invsData.rslt_amt_sum1 / this.invsData.plan_amt_sum1) * 100).toFixed(1)
      this.invsData.amt_per_sum2 = this.invsData.plan_amt_sum2 === 0 ? 0 : ((this.invsData.rslt_amt_sum2 / this.invsData.plan_amt_sum2) * 100).toFixed(1)

      this.chartData5.labels = ['점검율', '미점검율']
      this.chartData5Per = Math.round(data.oprnList[0].per)
      const data5 = [Math.round(data.oprnList[0].per), Math.round(data.oprnList[0].nper)]
      this.chartData5.datasets = [
        {
          data: data5,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      this.chartData6.labels = ['점검율', '미점검율']
      this.chartData6Per = Math.round(data.circList[0].per)
      const data6 = [Math.round(data.circList[0].per), Math.round(data.circList[0].nper)]
      this.chartData6.datasets = [
        {
          data: data6,
          backgroundColor: this.pieColor,
          hoverOffset: 9,
        },
      ]

      this.loaded = true
    },
    async selectApi(val) {
      const regObj = this.region.find((e) => e.cd === val)

      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info/${val}`)
      // console.log('api01 result : ', JSON.stringify(data.api01)) // eslint-disable-line no-console
      // console.log('api02 result : ', JSON.stringify(data.api02)) // eslint-disable-line no-console
      // console.log('api03 result : ', JSON.stringify(data.api03)) // eslint-disable-line no-console
      // console.log('api04 result : ', JSON.stringify(data.api04)) // eslint-disable-line no-console
      // console.log('api05 result : ', JSON.stringify(data.api05)) // eslint-disable-line no-console
      // console.log('api06 result : ', JSON.stringify(data.api06)) // eslint-disable-line no-console

      // 지역날씨
      if (Object.keys(data.api01).length && data.api01.response.header.resultCode && data.api01.response.header.resultCode === '00') {
        let item = {}
        const items = data.api01.response.body.items.item
        item = items.find((e) => e.category === 'SKY')
        const sky = item.fcstValue
        item = items.find((e) => e.category === 'PTY')
        const pty = item.fcstValue
        item = items.find((e) => e.category === 'T1H')
        const temp = item.fcstValue
        item = items.find((e) => e.category === 'REH')
        const hum = item.fcstValue
        item = items.find((e) => e.category === 'VEC')
        const wind1 = item.fcstValue
        item = items.find((e) => e.category === 'WSD')
        const wind2 = item.fcstValue
        item = items.find((e) => e.category === 'RN1')
        const rain = item.fcstValue

        if (pty === '0') {
          const txt = sky === '1' ? '맑음' : sky === '3' ? '구름' : sky === '4' ? '흐림' : ''
          this.$set(this.wtData, 'sky', txt)
        } else {
          const txt = pty === '1' || pty === '5' ? '비' : pty === '2' || pty === '6' ? '비/눈' : pty === '3' || pty === '7' ? '눈' : ''
          this.$set(this.wtData, 'sky', txt)
        }
        this.$set(this.wtData, 'temp', temp)
        this.$set(this.wtData, 'hum', hum)
        const windTxt = wind1 >= 0 && wind1 < 90 ? '북동' : wind1 >= 90 && wind1 < 180 ? '남동' : wind1 >= 180 && wind1 < 270 ? '남서' : '북서'
        this.$set(this.wtData, 'wind1', windTxt)
        this.$set(this.wtData, 'wind2', wind2)
        this.$set(this.wtData, 'rain', rain === '강수없음' ? 0 : rain.replace('mm', ''))

        // console.log('sky : ', sky) // eslint-disable-line no-console
        // console.log('pty : ', pty) // eslint-disable-line no-console
        // console.log('temp : ', temp) // eslint-disable-line no-console
        // console.log('hum : ', hum) // eslint-disable-line no-console
        // console.log('wind1 : ', wind1) // eslint-disable-line no-console
        // console.log('wind2 : ', wind2) // eslint-disable-line no-console
        // console.log('rain : ', rain) // eslint-disable-line no-console
      } else {
        this.$set(this.wtData, 'sky', '-')
        this.$set(this.wtData, 'temp', '-')
        this.$set(this.wtData, 'hum', '-')
        this.$set(this.wtData, 'wind1', '-')
        this.$set(this.wtData, 'wind2', '-')
        this.$set(this.wtData, 'rain', '-')
        // console.error('api01 result code : ' + data.api01.response.header.resultCode + ', msg : ' + data.api01.response.header.resultMsg) // eslint-disable-line no-console
      }

      const cityNm = regObj.buf_3rd_cntn.substr(regObj.buf_3rd_cntn.indexOf('/') + 1)
      // 미세먼지
      if (Object.keys(data.api02).length && data.api02.response.header.resultCode && data.api02.response.header.resultCode === '00') {
        let item = {}
        const items = data.api02.response.body.items
        item = items.find((e) => e.cityName === cityNm)
        const dust1 = item.pm10Value === '' ? '0' : item.pm10Value
        const dust2 = dust1 <= 30 ? '좋음' : dust1 > 30 && dust1 <= 80 ? '보통' : dust1 > 80 && dust1 <= 150 ? '나쁨' : '매우나쁨'

        this.$set(this.wtData, 'dust1', dust1)
        this.$set(this.wtData, 'dust2', dust2)

        // console.log('dust1 : ', dust1) // eslint-disable-line no-console
        // console.log('dust2 : ', dust2) // eslint-disable-line no-console
      } else {
        this.$set(this.wtData, 'dust1', '-')
        this.$set(this.wtData, 'dust2', '-')
        // console.error('api02 result code : ' + data.api02.response.header.resultCode + ', msg : ' + data.api02.response.header.resultMsg) // eslint-disable-line no-console
      }

      // 자외선지수
      if (Object.keys(data.api03).length && data.api03.response.header.resultCode && data.api03.response.header.resultCode === '00') {
        let item = {}
        const items = data.api03.response.body.items.item
        item = items[0]
        const uv = item.h0
        const uvTxt = uv <= 2 ? '낮음' : uv >= 3 && uv <= 5 ? '보통' : uv >= 6 && uv <= 7 ? '높음' : uv >= 8 && uv <= 10 ? '매우높음' : '위험'

        this.$set(this.wtData, 'uv', uvTxt)

        // console.log('uv : ', uvTxt) // eslint-disable-line no-console
      } else {
        this.$set(this.wtData, 'uv', '-')
        // console.error('api03 result code : ' + data.api03.response.header.resultCode + ', msg : ' + data.api03.response.header.resultMsg) // eslint-disable-line no-console
      }

      // 체감온도지수 (하절기 5 ~ 9월)
      if (Object.keys(data.api04).length && data.api04.response.header.resultCode && data.api04.response.header.resultCode === '00') {
        let item = {}
        const items = data.api04.response.body.items.item
        item = items[0]
        const st = item.h1
        const stTxt = st <= 28 ? '낮음' : st >= 29 && st <= 30 ? '관심' : st >= 31 && st <= 33 ? '주의' : st >= 34 && st <= 36 ? '경고' : '위험'

        this.$set(this.wtData, 'st', stTxt)

        // console.log('st : ', stTxt) // eslint-disable-line no-console
      } else if (Object.keys(data.api04).length) {
        this.$set(this.wtData, 'st', '-')
        console.error('api04 result code : ' + data.api04.response.header.resultCode + ', msg : ' + data.api04.response.header.resultMsg) // eslint-disable-line no-console
      } else {
        this.$set(this.wtData, 'st', '-')
      }

      // 동파지수 (동절기 11 ~ 3월)
      if (Object.keys(data.api05).length && data.api05.response.header.resultCode && data.api05.response.header.resultCode === '00') {
        let item = {}
        const items = data.api05.response.body.items.item
        item = items[0]
        const freeze = item.h1
        const freezeTxt = freeze === '25' ? '낮음' : freeze === '50' ? '보통' : freeze === '75' ? '높음' : '매우높음'

        this.$set(this.wtData, 'freeze', freezeTxt)

        // console.log('freeze : ', freezeTxt) // eslint-disable-line no-console
      } else if (Object.keys(data.api05).length) {
        this.$set(this.wtData, 'freeze', '-')
        console.error('api05 result code : ' + data.api05.response.header.resultCode + ', msg : ' + data.api05.response.header.resultMsg) // eslint-disable-line no-console
      } else {
        this.$set(this.wtData, 'freeze', '-')
      }

      // 기상특보
      if (Object.keys(data.api06).length && data.api06.response.header.resultCode && data.api06.response.header.resultCode === '00') {
        let item = {}
        const items = data.api06.response.body.items.item
        item = items[0]
        const news = item.title

        this.$set(this.wtData, 'news', news)

        // console.log('news : ', news) // eslint-disable-line no-console
      } else if (Object.keys(data.api06).length && data.api06.response.header.resultCode && data.api06.response.header.resultCode === '03') {
        this.$set(this.wtData, 'news', '')
      } else {
        this.$set(this.wtData, 'news', '-')
        // console.error('api06 result code : ' + data.api06.response.header.resultCode + ', msg : ' + data.api06.response.header.resultMsg) // eslint-disable-line no-console
      }

      this.selectMsg()
    },
    async selectMsg() {
      this.schData.kind = 'chkMsg'

      this.schData.value1 = this.wtData.temp === '-' ? null : this.wtData.temp
      this.schData.value2 = this.wtData.hum === '-' ? null : this.wtData.hum
      this.schData.value3 = this.wtData.wind2 === '-' ? null : this.wtData.wind2
      this.schData.value4 = this.wtData.rain === '-' ? null : this.wtData.rain
      this.schData.value5 = this.wtData.dust1 === '-' ? null : this.wtData.dust1

      const newsTxt = this.wtData.news.replace(' 변경', '').replace(' 발표', '')
      this.schData.weat_elmt_nm =
        newsTxt === '' || newsTxt === '-' || newsTxt.includes('해제') ? '' : newsTxt.substr(newsTxt.indexOf('/') + 1, newsTxt.indexOf('(') - (newsTxt.indexOf('/') + 1)).trim()

      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info`, { params: this.schData })

      const info = []
      for (let i = 0; i < data.weatMsgChk2.length; i++) {
        if (i < 2) {
          info.push(data.weatMsgChk2[i].msg_txt)
        }
      }

      for (let i = 0; i < data.weatMsgChk1.length; i++) {
        if (i < 3) {
          info.push(data.weatMsgChk1[i].msg_txt)
        }
      }
      this.$set(this.wtData, 'info', info)
      // console.log('wtData.info : ', JSON.stringify(this.wtData.info)) // eslint-disable-line no-console
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
    async select1() {
      this.schData.wkpl_list1 = this.wkpl1Val.join()
      if (!this.schData.wkpl_list1) {
        this.$toast.show(this.$t('MS00000268'), { className: 'toast_error' }) // 사업장을 선택해주세요.
        return false
      }

      this.schData.kind = 'select1'
      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info`, { params: this.schData })

      this.makeChart1(data)

      this.$refs.chart3.update()
      this.$refs.chart4.update()
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
    chgCode(data, wkplId) {
      // 상위 코드 조회하여 필터링
      this.F_codes.F_00000001 = this.FF_00000001.filter(function (n) {
        return n.up_cd === data
      })
      this.F_codes.F_00000001.unshift({ cd: '', cd_nm: 'SELECT', up_cd: '' })
      if (!wkplId) {
        this.$set(this.schData, 'wkpl_id', '')
      }
    },
    async chgImg(wkpl) {
      if (!wkpl) {
        return
      }
      this.schData.kind = 'oprnLoc'
      const data = await this.$axios.$get(`/api/v1/common/dashboard/dash-chart-info`, { params: this.schData })

      // 사업장별 안전작업허가 현황
      this.oprnLocList = data.oprnLocList
      this.oprnLocTot = data.oprnLocTot[0]
      this.setImgUrl()
    },
    async setImgUrl() {
      console.log(this.schData.wkpl_id) // eslint-disable-line no-console
      const data = await this.$axios.$get(`/api/v1/st/stndinfr/wkpl-img`, { params: this.schData })
      console.log(data[0]) // eslint-disable-line no-console
      if (!(typeof data[0] === 'undefined' || data[0] == null || data[0] === '' || data[0] === 0)) {
        this.$axios
          .$get(`/api/v1/common/image/${data[0]}-1`, {
            responseType: 'arraybuffer',
            headers: {
              'Content-Type': 'application/json',
            },
          })
          .then((res) => {
            const blob = new Blob([res])
            this.imgUrl = window.URL.createObjectURL(blob)
            console.log(window.URL.createObjectURL(blob)) // eslint-disable-line no-console
          })
      } else {
        this.imgUrl = require(`@/assets/images/wkpl/S35.png`)
      }
    },
    schReset1() {
      const user = this.$store.getters['auth/getUser']
      this.cat1Val = ['AP01']
      this.chkWkpl1()

      // this.schData.period1 = '2'

      this.$nextTick(() => {
        this.schData.period1 = '2'
        this.schData.period2 = this.getTzDate(user.time_zone).substr(0, 4)
      })
    },
    openWtMsg() {
      this.$refs.wtmsg.open()
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
  padding-top: 30px;
}
.container_homeui h2 {
  font-size: 42px;
  text-align: center;
  padding: 32px 0;
  letter-spacing: -1.8px;
  color: #062c5e;
}
.container_homeui h2 em {
  font-style: normal;
  color: #359feb;
  text-align: left;
}
.homeui {
  margin: 0 auto;
  min-width: 1280px;
  /* max-width: 1340px; */
  /* padding-top: 30px; */
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
.con_half .step h4 {
  font-size: 16px;
  margin: 24px 0 12px 0;
  color: #222222;
}
.con_half .step h4:before {
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
  /* border-radius: 4px; */
  padding: 32px 48px;
  /* border-bottom: 1px solid #ededed; */
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
.warning_task {
  display: flex;
  background: #062c5e;
  border-radius: 3px;
  height: 104px;
  padding: 16px 0;
}
.warning_task .item {
  padding: 0 16px;
  width: 100%;
  color: rgba(255, 255, 255, 0.75);
  position: relative;
  justify-content: space-between;
  display: flex;
  flex-direction: column;
}
.warning_task > .item:after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  width: 1px;
  height: 50px;
  background: rgba(255, 255, 255, 0.25);
}
.warning_task .item p {
  padding: 0;
  margin: 2px 0;
  font-size: 13px;
}
.warning_task .item p:last-child {
  font-size: 24px;
  font-weight: bold;
  color: #fff;
  text-align: right;
}
.warning_task .item p:last-child:after {
  content: '건';
  font-size: 11px;
  color: rgba(255, 255, 255, 0.65);
  margin-left: 4px;
}
.invest {
  display: flex;
  flex-direction: column;
  height: 289px;
  background: #062c5e;
  padding: 12px;
}
.invest .item {
  width: 100%;
  font-size: 15px;
  color: rgba(255, 255, 255, 0.75);
  text-align: center;
  position: relative;
  display: flex;
  flex-direction: row;
  height: 100%;
}
.invest .item:last-child {
  flex-direction: column;
  border-top: 1px solid rgba(255, 255, 255, 0.25);
  justify-content: center;
  font-size: 18px;
}
.invest .item div {
  width: 100%;
  position: relative;
  align-self: center;
}
.invest .item p {
  margin: 0;
}
.invest .item p:first-child {
  font-size: 32px;
  color: #fff;
}
.invest h5 {
  text-align: center;
  font-size: 28px;
  color: #fff;
  margin-bottom: 0;
}
.safety {
  display: flex;
}
.safety .item {
  width: 100%;
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
  height: 36px;
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
.new_group .table02 tr td {
  height: 54px;
  border: 1px solid #eee;
  text-align: right;
  font-size: 15px;
  padding: 0 10px;
  font-weight: bold;
  color: #062c5e;
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
.shut_wrap .guide_line:after {
  content: '';
  background: #ededed;
  width: 1px;
  height: 100%;
  display: block;
  margin-left: 50%;
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
.dp_grid-3 {
  display: grid;
  grid-template-columns: 50% 25% 19%;
  grid-gap: 0 3%;
}
.dp_grid-3 .item {
  width: 100%;
  display: flex;
  flex-direction: column;
}
.weather {
  display: flex;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ededed;
}
.weather .item {
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 12px 12px;
  border-right: 1px solid #ededed;
  font-size: 15px;
  font-weight: bold;
  color: #062c5e;
}
.weather .item:last-child {
  border-right: none;
}
.weather .item h5 {
  border-bottom: 1px solid #ededed;
  margin: 8px 0;
  padding-bottom: 8px;
  text-align: center;
}
.weather .item > div {
  padding: 2px 0;
  text-align: center;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.weather .item h5 em {
  color: #359feb;
  font-style: normal;
}
.weather .item div p {
  margin: 0;
}
.weather ::v-deep .v-select__selections input,
.weather ::v-deep .v-text-field__details {
  display: none;
}
.weather ::v-deep .v-input__control {
  justify-content: center;
}
.weather_info {
  display: flex;
  justify-content: space-between;
}
.weather_info .btn_set {
  align-self: center;
  text-align: right;
  min-width: 32px;
  width: 32px;
  margin-top: 8px;
  border: none;
}
.material-symbols-outlined {
  font-size: 24px;
}
.weather_info_list {
  padding: 0;
  border-top: 1px solid #ccc;
}
.weather_info_list li {
  padding: 6px 0 0 0;
}
.dp_grid-6 {
  display: grid;
  grid-template-columns: 15% 15% 15% 15% 15% 15%;
  grid-gap: 0 2%;
  width: 100%;
}
.dp_grid-6 .item {
  width: 100%;
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
.sch_zone-group ::v-deep .v-input__append-inner {
  align-self: center;
  margin: 0 !important;
}
.select_title {
  position: absolute;
  left: 0;
  top: -22px;
  font-size: 13px;
}
.sch_zone ::v-deep .v-select__selections > span {
  max-width: 40%;
}
.sch_zone ::v-deep .v-chip__content span {
  text-overflow: ellipsis;
  overflow: hidden;
  max-width: 100%;
  white-space: nowrap;
}
.sch_zone ::v-deep .v-select__selections input {
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

.v-menu__content {
  background: #fff;
  opacity: 0.9;
}
.v-menu__content h4 {
  font-size: 14px;
  font-weight: bold;
  padding: 8px;
  background: #f6f6f6;
}
.v-menu__content .btn_cls {
  min-width: 24px;
  width: 24px;
  position: absolute;
  right: 0;
  top: 0;
  border: none;
  background: transparent !important;
}
.v-menu__content .material-icons {
  font-size: 20px;
}
.menu_list {
  padding: 0;
}
.menu_list ::v-deep .v-data-table table td {
  padding: 2px 4px;
  height: 36px;
  font-size: 14px;
  color: #000;
}
.menu_list ::v-deep .v-data-table table td:last-child {
  width: 64px;
  text-align: right;
  font-size: 15px;
  font-weight: bold;
  color: #062c5e;
}
.menu_list ::v-deep .v-list-item {
  padding: 8px;
}
.menu_list ::v-deep .v-list-item__content {
  padding: 0;
}
.float_tbl tr {
  border: 1px solid #aaa;
}
.float_tbl td,
.float_tbl th {
  border: 1px solid #aaa !important;
}
.place {
  color: #fff;
  font-size: 24px;
  text-align: center;
  background: rgba(6 44 94 / 85%);
  padding: 6px 4px 0 4px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  box-shadow: 4px 4px 6px rgb(6 44 94 / 50%);
}
.place-red {
  color: #fff;
  font-size: 24px;
  text-align: center;
  background: rgba(220 56 67 / 90%);
  padding: 6px 4px 0 4px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  box-shadow: 4px 4px 6px rgb(6 44 94 / 50%);
}
.weather_sunny {
  background: url(../../assets/images/weather/sunny.png);
  width: 48px;
  height: 48px;
}
.weather_rain {
  background: url(../../assets/images/weather/rain.png);
  width: 48px;
  height: 48px;
}
.weather_snow {
  background: url(../../assets/images/weather/snow.png);
  width: 48px;
  height: 48px;
}
.weather_snow_rain {
  background: url(../../assets/images/weather/snow_rain.png);
  width: 48px;
  height: 48px;
}
.weather_cloudy {
  background: url(../../assets/images/weather/cloudy.png);
  width: 48px;
  height: 48px;
}
.weather_partly_cloudy {
  background: url(../../assets/images/weather/partly_cloudy.png);
  width: 48px;
  height: 48px;
}
.img_location ::v-deep .v-image__image {
  background-size: 100% 100%;
}
</style>
