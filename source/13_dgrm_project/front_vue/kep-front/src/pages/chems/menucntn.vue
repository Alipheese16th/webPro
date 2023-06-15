<template>
  <v-dialog v-model="dialog" persistent width="1000px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 메뉴 등록/조회 -->
        <v-toolbar-title>{{ $t('LB00000227') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="2">
              <!-- 상위메뉴 -->
              <div class="label_tit">{{ $t('LB00000228') }}</div>
            </v-col>
            <v-col cols="10">
              <div class="label_con">{{ frmData.upMenuNo + ' ' + frmData.upMenuNm }}</div>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 메뉴ID -->
              <div v-if="!detail" class="label_tit essn">{{ $t('LB00000229') }}</div>
              <div v-if="detail" class="label_tit">{{ $t('LB00000229') }}</div>
            </v-col>
            <v-col cols="10">
              <div v-if="detail" class="label_con">{{ frmData.menuNo }}</div>
              <!-- 메뉴ID를 입력해주세요. -->
              <v-text-field v-if="!detail" ref="no" v-model="frmData.menuNo" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000061')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 메뉴명 -->
              <div class="label_tit essn">{{ $t('LB00000076') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 메뉴명을 입력해주세요. -->
              <v-text-field ref="nm" v-model="frmData.menuNm" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000062')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 화면URL -->
              <div class="label_tit">{{ $t('LB00000230') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 화면URL을 입력해주세요. -->
              <v-text-field ref="url" v-model="frmData.menuUrl" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000063')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 정렬순서 -->
              <div class="label_tit essn">{{ $t('LB00000091') }}</div>
            </v-col>
            <v-col cols="10">
              <!-- 정렬순서를 입력해주세요. -->
              <v-text-field ref="seq" v-model="frmData.sortSeq" type="number" outlined single-line :hide-details="true" dense :placeholder="$t('MS00000022')" height="30"></v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="2">
              <!-- 사용여부 -->
              <div class="label_tit">{{ $t('LB00000049') }}</div>
            </v-col>
            <v-col cols="10">
              <v-select v-model="frmData.useYn" :items="yn" item-text="name" item-value="val" outlined single-line :hide-details="true" dense height="30"></v-select>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <v-btn color="primary" depressed width="120" height="40" @click="save()">{{ $t('LB00000045') }}</v-btn>
        <v-btn v-if="detail" color="primary" depressed width="120" height="40" @click="del()">{{ $t('LB00000046') }}</v-btn>
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      yn: [
        {
          name: 'Y',
          val: 'Y',
        },
        {
          name: 'N',
          val: 'N',
        },
      ],
      dialog: false,
      valid: true,
      frmData: {},
      tempData: {},
      detail: false,
      writer: '',
      textRules: [(v) => !!v || '필수항목입니다.'],
    };
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  methods: {
    open(detail, frmData, writer) {
      // 수정여부, 데이터, 작성자
      this.detail = detail; // true일 경우 수정, false일 경우 삽입
      this.frmData = frmData;
      this.tempData = JSON.parse(JSON.stringify(frmData));
      this.writer = writer;
      this.dialog = true;
    },
    async save() {
      // Validation 체크
      if (!this.menuValidate()) {
        return;
      }
      // 저장 여부 확인
      const res = await this.$confirm(this.$t('MS00000010')); // 저장하시겠습니까?
      if (!res) {
        return;
      }
      this.frmData.username = this.$store.getters['auth/getUsername'];
      if (!this.detail) {
        // 신규 저장
        this.insert();
      } else {
        // 업데이트
        this.update();
      }
    },
    close() {
      this.$emit('close', this.frmData.menuNo);
      this.reset();
    },
    reset() {
      // this.valid = true
      this.dialog = false;
      this.frmData = {};
    },
    async insert() {
      await this.$axios.post('/api/v1/common/chems/menu', this.frmData).then((res) => {
        if (res.data === 'OverlapFail') {
          this.$toast.show(this.$t('MS00000064'), { className: 'toast_error' }); // 이미 존재하는 메뉴ID 입니다.
        } else if (res.data === 'Fail') {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }); // 오류가 발생했습니다.
        } else {
          this.detail = true;
          this.$toast.show(this.$t('MS00000011')); // 저장되었습니다.
        }
      });
    },
    async update() {
      await this.$axios.put('/api/v1/common/chems/menu', this.frmData).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000011')); // 저장되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }); // 오류가 발생했습니다.
        }
      });
    },
    async del() {
      const res = await this.$confirm(this.$t('MS00000042')); // 삭제하시겠습니까?
      if (!res) {
        return;
      }
      if (this.frmData.deleteYn === 'N') {
        this.$toast.show(this.$t('MS00000065'), { className: 'toast_error' }); // 하위메뉴가 존재하여 삭제할 수 없습니다.
        return;
      }
      this.frmData.username = this.$store.getters['auth/getUsername'];
      await this.$axios.delete(`/api/v1/common/chems/menu/${this.frmData.menuNo}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')); // 삭제되었습니다.
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }); // 오류가 발생했습니다.
        }
      });
      this.close();
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true;
      else return false;
    },
    menuValidate() {
      if (this.isEmpty(this.frmData.menuNo)) {
        this.$toast.show(this.$t('MS00000061'), { className: 'toast_error' }); // 메뉴ID를 입력해주세요.
        this.$refs.no.focus();
        return false;
      } else if (this.isEmpty(this.frmData.menuNm)) {
        this.$toast.show(this.$t('MS00000062'), { className: 'toast_error' }); // 메뉴명을 입력해주세요.
        this.$refs.nm.focus();
        return false;
      } else if (this.isEmpty(this.frmData.sortSeq)) {
        this.$toast.show(this.$t('MS00000022'), { className: 'toast_error' }); // 정렬순서를 입력해주세요.
        this.$refs.seq.focus();
        return false;
      } else if (this.frmData.sortSeq.length > 9) {
        this.$toast.show(this.$t('MS00000066'), { className: 'toast_error' }); // 정렬순서는 9자 이하로 작성해주세요.
        this.$refs.seq.focus();
        return false;
      } else if (this.frmData.menuNo.length > 20) {
        this.$toast.show(this.$t('MS00000067'), { className: 'toast_error' }); // 메뉴ID는 20자 이하로 작성해주세요.
        this.$refs.no.focus();
        return false;
      } else if (this.frmData.menuNm.length > 300) {
        this.$toast.show(this.$t('MS00000068'), { className: 'toast_error' }); // 메뉴명은 300자 이하로 작성해주세요.
        this.$refs.nm.focus();
        return false;
      } else if (!this.isEmpty(this.frmData.menuUrl) && this.frmData.menuUrl.length > 200) {
        this.$toast.show(this.$t('MS00000069'), { className: 'toast_error' }); // 화면 URL은 200자 이하로 작성해주세요.
        this.$refs.nm.focus();
        return false;
      } else if (/[\s]/g.test(this.frmData.menuNo)) {
        this.$toast.show(this.$t('MS00000070'), { className: 'toast_error' }); // 메뉴ID에 공백문자는 사용할 수 없습니다.
        this.$refs.no.focus();
        return false;
      } else if (!/^[A-Za-z0-9+]*$/.test(this.frmData.menuNo)) {
        this.$toast.show(this.$t('MS00000071'), { className: 'toast_error' }); // 메뉴ID는 영문, 숫자로 작성해주세요.
        this.$refs.no.focus();
        return false;
      } else if (!/^[0-9]*$/.test(this.frmData.sortSeq)) {
        this.$toast.show(this.$t('MS00000072'), { className: 'toast_error' }); // 정렬순서는 숫자만 입력해주세요.
        this.$refs.seq.focus();
        return false;
      } else if (this.isEmpty(this.frmData.useYn)) {
        // TODO (번역필요)
        this.$toast.show('사용 여부를 선택하세요.', { className: 'toast_error' }); // 사용 여부를 선택하세요.
        return false;
      }
      if (
        this.tempData.menuNo === this.frmData.menuNo &&
        this.tempData.menuNm === this.frmData.menuNm &&
        this.tempData.sortSeq === this.frmData.sortSeq &&
        this.tempData.menuUrl === this.frmData.menuUrl &&
        this.tempData.useYn === this.frmData.useYn
      ) {
        this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' });
        return false;
      }
      return true;
    },
  },
};
</script>
