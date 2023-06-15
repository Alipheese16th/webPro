<template>
  <div>
    <div class="location">
      <!--시스템관리--><!--권한관리-->
      <span>Home > {{ $t('LB00000042') }} > {{ $t('LB00000065') }}</span>
    </div>
    <div class="work_title">
      <!--권한관리-->
      <h2>{{ $t('LB00000065') }}</h2>
    </div>
    <div class="container_sch">
      <v-row>
        <v-col cols="10">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  v-model="searchdatas.businessCode"
                  label="ALL"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.CO00000001"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
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
        <!--권한 목록(KO)-->
        <h3>{{ $t('LB00000079') }}</h3>
      </div>
      <div class="grid_header">
        <!--총--><!--건-->
        <div class="grid_header_left">{{ $t('LB00000039') }} {{ totalcnt }}{{ $t('LB00000040') }}</div>
        <div>
          <!--추가-->
          <v-btn color="" outlined height="28" @click="add">{{ $t('LB00000047') }}</v-btn>
          <!--삭제-->
          <v-btn color="" outlined height="28" @click="del">{{ $t('LB00000046') }}</v-btn>
        </div>
      </div>
      <div id="realgrid" style="width: 100%; height: 481px"></div>
    </div>
    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
    </div>
    <rolemninfo :is-open="dialog.isOpen" :item="dialog.item" @saveUsers="saveUsers" @close="close" />
  </div>
</template>

<script>
import { GridView, LocalDataProvider, ValueType } from 'realgrid';
import rolemninfo from './rolemninfo.vue';

export default {
  meta: {
    title: '권한관리',
  },
  components: {
    rolemninfo,
  },
  data: () => ({
    dialog: {
      isOpen: false,
      item: {
        userIdsString: '',
        userIdsArray: [],
      },
    },
    savedatas: [], //  저장될 배열 생성
    codeid: ['CO00000001', 'CO00000010'],
    // 공통코드
    codes: {
      CO00000001: [{ cd: '', cd_nm: '' }],
      CO00000010: [{ cd: '', cd_nm: '' }],
    },
    // 업무구분 코드쌍
    selCO00000001: {
      cd: [],
      cd_nm: [],
    },
    // 권한구분 코드쌍
    selCO00000010: {
      cd: [],
      cd_nm: [],
    },
    totalcnt: 0,
    searchdatas: {
      roleExplain: '',
      roleTypeCode: '',
    },
    grid: {
      dataProvider: null,
      gridView: null,
      field: [],
      rowData: [],
    },
    // 데이터 프로바이더 컬럼 모음
    fields: [
      {
        fieldName: 'businessCode', // 업무구분코드
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'businessCodeName', // 업무구분
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'roleTypeCode', // 권한구분코드
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'roleTypeCodeName', // 권한구분명
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'roleNo', // 권한관리키값 권한_번호
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'roleExplain', // 권한명
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userCount', // 사용자수
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'usageYn', // 사용여부
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'sortSeq', // 정렬순번
        dataType: ValueType.TEXT,
      },
      // TODO (구현완료 20230531/20230602) 등록자 컬럼 가져오기
      {
        fieldName: 'userName', // 등록자
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'createDateTime', // 등록일
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'rowStatus', // 셀 추가
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'mlang_no', // 다국어번호
        dataType: ValueType.TEXT,
      },
      {
        fieldName: 'userIdsString', // 해당 권한에 물린 사용자id string
        dataType: ValueType.TEXT,
      },
    ],
    // 각 컬럼의 editRow() 후처리 로직모음
    editRowPolicy: {
      // 업무구분코드
      businessCode: (thisObj, rowIdx, value) => thisObj.editRowForCode('businessCode', 'businessCodeName', thisObj.selCO00000001, rowIdx, value),
      // 업무구분
      businessCodeName: (thisObj, rowIdx, value) => thisObj.editRowForCodeName('businessCode', 'businessCodeName', thisObj.selCO00000001, rowIdx, value),
      // 권한구분코드
      roleTypeCode: (thisObj, rowIdx, value) => thisObj.editRowForCode('roleTypeCode', 'roleTypeCodeName', thisObj.selCO00000010, rowIdx, value),
      // 권한구분
      roleTypeCodeName: (thisObj, rowIdx, value) => thisObj.editRowForCodeName('roleTypeCode', 'roleTypeCodeName', thisObj.selCO00000010, rowIdx, value),
    },
    // 각 컬럼의 cellClick() 후처리 로직 모음
    cellClickPolicy: {
      userCount: (thisObj, clickData) => thisObj.cellClickForUserCount(clickData),
    },
  }),
  async fetch() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res;
        this.selCO00000001.cd.push('');
        this.selCO00000001.cd_nm.push('SELECT');
        this.selCO00000010.cd.push('');
        this.selCO00000010.cd_nm.push('SELECT');
        res.CO00000001.forEach((i) => {
          this.selCO00000001.cd.push(i.cd);
          this.selCO00000001.cd_nm.push(i.cd_nm);
        });
        this.codes.CO00000001.unshift({ cd: '', cd_nm: 'ALL' });
        res.CO00000010.forEach((i) => {
          this.selCO00000010.cd.push(i.cd);
          this.selCO00000010.cd_nm.push(i.cd_nm);
        });
        this.codes.CO00000010.unshift({ cd: '', cd_nm: 'ALL' });
      })
      .catch((err) => {
        // eslint-disable-next-line no-console
        console.log(err);
      });
  },
  mounted() {
    this.grid.dataProvider = new LocalDataProvider(false);
    this.grid.dataProvider.setFields(this.fields);

    // 그리드 셋팅
    this.grid.gridView = new GridView('realgrid');
    this.grid.gridView.setDataSource(this.grid.dataProvider);
    // ValueColumn type
    this.grid.gridView.setColumns([
      {
        header: '업무구분코드', // 업무구분코드
        name: 'businessCode',
        fieldName: 'businessCode',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        // CellEditor <-- DropDownCellEditor type
        editor: {
          type: 'dropdown',
          values: this.selCO00000001.cd,
          labels: this.selCO00000001.cd,
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: this.$t('LB00000062'), // 업무구분
        name: 'businessCodeName',
        fieldName: 'businessCodeName',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        editor: {
          type: 'dropdown',
          values: this.selCO00000001.cd_nm,
          labels: this.selCO00000001.cd_nm,
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: '권한구분코드', // 권한구분코드
        name: 'roleTypeCode',
        fieldName: 'roleTypeCode',
        width: '100',
        editor: {
          type: 'dropdown',
          values: this.selCO00000010.cd,
          labels: this.selCO00000010.cd,
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: '권한구분명', // 권한구분명
        name: 'roleTypeCodeName',
        fieldName: 'roleTypeCodeName',
        width: '100',
        sortable: false,
        lookupDisplay: true,
        editor: {
          type: 'dropdown',
          values: this.selCO00000010.cd_nm,
          labels: this.selCO00000010.cd_nm,
          domainOnly: true,
          textReadOnly: true,
          displayLabels: 'label',
        },
      },
      {
        header: '권한번호', // 권한_번호
        name: 'roleNo',
        fieldName: 'roleNo',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000064'), // 권한명
        name: 'roleExplain',
        fieldName: 'roleExplain',
        width: '300',
        editor: {},
        editor: {
          type: 'line',
          maxLength: 300,
        },
      },
      {
        header: this.$t('LB00000060'), // 사용자수
        name: 'userCount',
        fieldName: 'userCount',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000049'), // 사용여부
        name: 'usageYn',
        fieldName: 'usageYn',
        width: '100',
        sortable: false,
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
        header: this.$t('LB00000059'), // 정렬순번
        name: 'sortSeq',
        fieldName: 'sortSeq',
        width: '100',
        editor: {
          type: 'number',
          maxLength: 4,
        },
      },
      {
        header: this.$t('LB00000055'), // 등록자
        name: 'userName',
        fieldName: 'userName',
        width: '100',
        editable: false,
      },
      {
        header: this.$t('LB00000054'), // 등록일
        name: 'createDateTime',
        fieldName: 'createDateTime',
        width: '100',
        editable: false,
      },
    ]);
    this.grid.gridView.setFooters({ visible: false });
    this.grid.gridView.setStateBar({ visible: false });
    this.grid.gridView.setCheckBar({ visible: true });
    this.grid.gridView.rowIndicator.visible = true;
    this.grid.gridView.displayOptions.selectionStyle = 'singleRow';
    this.grid.gridView.displayOptions.rowHeight = 40;
    this.grid.gridView.displayOptions.fitStyle = 'fill';
    this.grid.gridView.header.height = 39;
    this.grid.gridView.footer.height = 40;

    this.grid.gridView.onCellDblClicked = this.cellClick;
    this.grid.gridView.onEditChange = this.editRow;

    this.select();
  },
  methods: {
    /**
     * 코드값 변경으로 인한 코드명 변경
     * @param codeProperty row 의 코드값 프로퍼티명
     * @param nameProperty row 의 코드명 프로퍼티명
     * @param selectCodes selectbox 용 해당 공통코드 []
     * @param rowIdx row 의 idx
     * @param value 바뀐 코드값
     */
    editRowForCode(codeProperty, nameProperty, selectCodes, rowIdx, value) {
      // this.grid.rowData 가 아닌 this.grid.dataProvider.getRows 를 이용한다.
      // 코드의 인덱스 가져오기
      const idx = selectCodes.cd.findIndex((codeItem) => codeItem === value);

      // 코드명도 바꿔주기: 코드의 인덱스로 코드명 가져오기. -1 이면 기본값.
      this.grid.gridView.commit();
      this.grid.dataProvider.updateRow(rowIdx, {
        [`${codeProperty}`]: value,
        [`${nameProperty}`]: selectCodes.cd_nm[idx > 0 ? idx : 0],
      });
    },
    /**
     * 코드명 변경으로 인한 코드값 변경
     * @param codeProperty row 의 코드값 프로퍼티명
     * @param nameProperty row 의 코드명 프로퍼티명
     * @param selectCodes selectbox 용 해당 공통코드 []
     * @param rowIdx row 의 idx
     * @param value 바뀐 코드명
     */
    editRowForCodeName(codeProperty, nameProperty, selectCodes, rowIdx, value) {
      // this.grid.rowData 가 아닌 this.grid.dataProvider.getRows 를 이용한다.
      // 코드명의 인덱스 가져오기
      const idx = selectCodes.cd_nm.findIndex((codeItem) => codeItem === value);

      // 코드값도 바꿔주기: 코드명의 인덱스로 코드값 가져오기. -1 이면 기본값.
      this.grid.gridView.commit();
      this.grid.dataProvider.updateRow(rowIdx, {
        [`${codeProperty}`]: selectCodes.cd[idx > 0 ? idx : 0],
        [`${nameProperty}`]: value,
      });
    },
    // 아무것도 하지 않는 빈 함수
    doNothing() {},
    /**
     * row 의 특정 프로퍼티 변경 핸들러.
     * 1. 업무구분-업무구분코드: 한 쪽이 바뀌면 둘 다 갱신
     * 2. 권한구분코드-권한구분명: 1번과 동일
     * @param {GridBase} grid
     * @param {CellIndex} index
     * @param {any} value
     */
    editRow(grid, index, value) {
      (this.editRowPolicy[index.column] || this.doNothing)(this, index.dataRow, value);
    },
    /**
     * cell 더블클릭 이벤트 핸들러.
     * 1. 사용자수: 다이얼로그에서 하나의 권한에게 여러 사용자 물리기
     *    1.1 예외) 아직 insert 되지 않은 권한 데이터는 저장 후 사용자 매핑이 가능함.
     */
    cellClick(grid, clickData) {
      (this.cellClickPolicy[clickData.column] || this.doNothing)(this, clickData);
    },
    // 사용자수 컬럼 더블클릭 이벤트 핸들러
    cellClickForUserCount(clickData) {
      const target = this.grid.dataProvider.getJsonRow(clickData.dataRow);
      if (target.roleNo) {
        this.dialog.isOpen = true;
        this.dialog.item = {
          ...target,
          userIdsArray: target.userIdsString ? target.userIdsString.split(',') : [],
        };
      } else {
        // TODO (번역필요 20230604/x)
        this.$toast.show('사용자를 연동하기 전에 권한 정보를 먼저 저장해 주세요.', { className: 'toast_error' });
      }
    },
    close() {
      this.dialog.isOpen = false;
      // this.select();
    },
    async select() {
      const rows = await this.$axios.$get(`/api/v1/common/chems/role`, { params: this.searchdatas });
      this.grid.rowData = rows;
      this.grid.gridView.commit();
      this.grid.dataProvider.setRows(rows);
      this.grid.gridView.refresh();
      this.grid.gridView.setTopItem(0);
      this.totalcnt = this.grid.gridView.getItemCount();
    },
    // 초기화
    reset() {
      this.grid.gridView.commit();
      this.searchdatas = {};
    },
    // 추가
    add() {
      this.grid.gridView.commit();
      this.grid.dataProvider.insertRows(0, [
        {
          businessCode: '',
          roleTypeCode: '',
          roleExplain: '',
          userCount: '0',
          usageYn: 'Y',
          sortSeq: '',
        },
      ]);
      this.totalcnt = this.grid.gridView.getItemCount();
    },
    /**
     * 삭제.
     * 1. 만약 해당 권한에게 사용자 1명이라도 연동되어있다면, 해당 권한은 삭제불가
     */
    del() {
      const checkedRowIndexs = this.grid.gridView.getCheckedRows();
      const checkedRows = checkedRowIndexs.map((rowIdx) => this.grid.dataProvider.getJsonRow(rowIdx)).flatMap((i) => i);
      if (checkedRows.find((i) => i.userCount > 0)) {
        // alert 처리 후 아무것도 안함
        // TODO (번역필요 20230604/x)
        this.$toast.show('사용자에게 연동된 권한은 삭제할 수 없습니다.', { className: 'toast_error' });
      } else {
        this.grid.gridView.commit();
        this.grid.dataProvider.setOptions({ softDeleting: true });
        checkedRowIndexs.forEach((i) => {
          this.grid.dataProvider.removeRow(i);
          this.grid.dataProvider.hideRows(i);
        });
        this.totalcnt = this.grid.gridView.getItemCount();
      }
    },
    validationChk(rowstatus) {
      if (this.grid.gridView.getItemCount() <= 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }); // 저장할 항목이 없습니다.
        return false;
      }
      if (this.grid.dataProvider.getRowStateCount('all') - this.grid.dataProvider.getRowStateCount('createAndDeleted') === 0) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }); // 저장할 항목이 없습니다.
        return false;
      }
      const chkRow = [...rowstatus.created, ...rowstatus.updated, ...rowstatus.deleted];
      if (chkRow.length === 0) {
        return true;
      }
      for (let i = 0; i < chkRow.length; i++) {
        if (this.grid.dataProvider.getValue(chkRow[i], 'businessCode') === '') {
          this.$toast.show(this.$t('MS00000018'), { className: 'toast_error' }); // 업무구분을 선택해 주세요.
          this.grid.gridView.setCurrent({ dataRow: i, fieldName: 'businessCode' });
          return false;
        } else if (this.grid.dataProvider.getValue(chkRow[i], 'roleTypeCode') === '') {
          this.$toast.show(this.$t('MS00000020'), { className: 'toast_error' }); // 권한구분을 선택해 주세요.
          this.grid.gridView.setCurrent({ dataRow: i, fieldName: 'roleTypeCode' });
          return false;
        } else if (this.grid.dataProvider.getValue(chkRow[i], 'roleExplain') === '') {
          this.$toast.show(this.$t('MS00000019'), { className: 'toast_error' }); // 권한명을 입력해 주세요.
          this.grid.gridView.setCurrent({ dataRow: i, fieldName: 'roleExplain' });
          return false;
        } else if (this.grid.dataProvider.getValue(chkRow[i], 'sortSeq') === '') {
          this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }); // 정렬순서를 입력해 주세요.
          this.grid.gridView.setCurrent({ dataRow: i, fieldName: 'sortSeq' });
          return false;
        } else if (!/^[0-9]*$/.test(this.grid.dataProvider.getValue(chkRow[i], 'sortSeq'))) {
          this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }); // 정렬순서는 숫자만 입력해주세요.
          this.grid.gridView.setCurrent({ dataRow: i, fieldName: 'sortSeq' });
          return false;
        } else if (i + 1 === chkRow.length) {
          return true;
        }
      }
    },
    async createPush(rowstatus) {
      // 새로 생성된 row push
      if ((await this.grid.dataProvider.getRowStateCount('created')) > 0) {
        rowstatus.created.forEach((i) => {
          this.grid.dataProvider.setValue(i, 'rowStatus', 'C');
          this.savedatas.push(this.grid.dataProvider.getJsonRow(i, i));
        });
        return true;
      } else {
        return true;
      }
    },
    async updatePush(rowstatus) {
      // 수정된 row push
      if ((await this.grid.dataProvider.getRowStateCount('updated')) > 0) {
        rowstatus.updated.forEach((i) => {
          this.grid.dataProvider.setValue(i, 'rowStatus', 'U');
          this.savedatas.push(this.grid.dataProvider.getJsonRow(i, i));
        });
        return true;
      } else {
        return true;
      }
    },
    async deletePush(rowstatus) {
      // 삭제 row push
      if ((await this.grid.dataProvider.getRowStateCount('deleted')) > 0) {
        rowstatus.deleted.forEach((i) => {
          this.grid.dataProvider.setValue(i, 'rowStatus', 'D');
          this.savedatas.push(this.grid.dataProvider.getJsonRow(i, i));
        });
        return true;
      } else {
        return true;
      }
    },
    async save() {
      this.grid.gridView.commit();
      const rowstatus = this.grid.dataProvider.getAllStateRows(); // 수정된 항목의 row index전부 가져오기
      this.savedatas = []; //  저장될 배열 생성
      if (await this.validationChk(rowstatus)) {
        const crechk = await this.createPush(rowstatus);
        const upchk = await this.updatePush(rowstatus);
        const dechk = await this.deletePush(rowstatus);
        if (await (crechk && upchk && dechk)) {
          // 저장 여부 확인
          const res = await this.$confirm(this.$t('MS00000010')); // 저장하시겠습니까?
          if (!res) {
            return false;
          } else {
            this.saveparams = {};
            this.saveparams.savedatas = this.savedatas;
            await this.$axios.post('/api/v1/common/chems/role', this.saveparams).then((res) => {
              if (res.data === 'Success') {
                this.$toast.show(this.$t('MS00000011')); // 저장되었습니다.
              } else if (res.data === 'Fail_CANNOTDELETE') {
                // TODO (번역필요 20230601/x)
                // TODO (확인필요 20230601/x) 예외 사항에 대한 코드 정의
                this.$toast.show('아직 해당 권한을 이용하는 사용자가 존재합니다.', { className: 'toast_error' });
              } else {
                this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }); // 오류가 발생했습니다.
              }

              this.select();
            });
          }
        }
      }
    },
    /**
     * 다이얼로그로부터 해당 권한에 물린 사용자를 받아 갱신하기.
     * 1. 해당 권한이 이미 DB 에 존재: 바로 업데이트 처리 후 화면에 반영
     * 2. 해당 권한이 아직 insert 되지 않음: 최종 저장 시 매핑 처리. 일단 화면에 먼저 반영.
     */
    saveUsers(userIdsArray) {
      // rowData 의 원본 권한 정보 찾기
      const rowItem = this.grid.rowData.find((row) => row.roleNo === this.dialog.item.roleNo);

      // userIdsArray, userIdsString, userCount 변경
      if (rowItem.roleNo) {
        // update row
        this.$axios
          .$post('/api/v1/common/chems/role-user', { roleNo: this.dialog.item.roleNo, userIds: userIdsArray })
          .then((res) => {
            if (res === 'Success') {
              this.saveUsersForRender(rowItem, userIdsArray);
              this.$toast.show(this.$t('MS00000011')); // 저장되었습니다.
            } else;
          })
          .catch(() => {
            this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }); // 오류가 발생했습니다.
          });
      } else {
        // insert row
        this.saveUsersForRender(rowItem, userIdsArray);
      }
    },
    /**
     * saveUsers 의 화면용 후처리
     * @param {*} rowItem 현재 화면의 rowItem
     * @param {*} userIdsArray 해당 rowItem 에 물린 사용자 아이디 []
     */
    saveUsersForRender(rowItem, userIdsArray) {
      // TODO 여기 바꾸고 CRUD 테스트하기.
      // const target = this.grid.dataProvider.getJsonRow(clickData.dataRow);
      rowItem.userIdsArray = userIdsArray;
      rowItem.userIdsString = userIdsArray.join(',');
      rowItem.userCount = userIdsArray.length;

      this.grid.gridView.commit();
      this.grid.dataProvider.setRows(this.grid.rowData);
    },
  },
};
</script>
<style lang="scss" scoped></style>
