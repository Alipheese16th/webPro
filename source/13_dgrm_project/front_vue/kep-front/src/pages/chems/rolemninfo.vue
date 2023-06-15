<template>
  <v-dialog v-model="isOpen" persistent width="300px">
    <v-card class="container_pop">
      <!-- [start 헤더] -->
      <v-toolbar flat class="mb-6">
        <!-- 권한부여 TODO: 번역필요 -->
        <v-toolbar-title>권한부여</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <!-- [end 헤더] -->
      <!-- [start 본문] -->
      <div class="container_detail">
        <!--
        [권한 부여할 사용자 선택]
        1. users 를 기반으로 추가/삭제
        2. 추가: users 에 해당 유저 추가
            2.1 예외: 이미 중복일 경우 추가 X
        3. 삭제: users 에 해당 유저 삭제
       -->
       <!-- TODO 번역필요 -->
        <v-container>
          <v-autocomplete v-model="userInput" :items="users" @change="changeUser" placeholder="권한에 추가할 사용자를 검색하세요."></v-autocomplete>
        </v-container>
        <v-container>
          <v-list dense>
            <v-list-item-group>
              <v-list-item v-for="(userItem, idx) in _users" :key="idx">
                <v-list-item-content>
                  <div>{{ userItem.text }}</div>
                </v-list-item-content>
                <v-list-item-action>
                  <v-btn small outlined width="28" @click="() => removeUser(userItem.value)">{{ $t('LB00000046') }}</v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-container>
      </div>
      <!-- [end 본문] -->
      <!-- [start 푸터] -->
      <v-card-actions>
        <v-btn color="primary" depressed width="120" height="40" @click="saveUsers">{{ $t('LB00000045') }}</v-btn>
        <v-btn outlined width="120" height="40" @click="close">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
      <!-- [end 푸터] -->
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  props: {
    isOpen: {
      type: Boolean,
      required: true,
      default: false,
    },
    // rowItem
    item: {
      type: Object,
      required: false,
      default: () => ({
        userIdsArray: [],
      }),
    },
  },
  data() {
    return {
      codeid: ['SH00000042', 'CO00000009'],
      // 공통코드
      codes: {
        SH00000042: [{ cd: '', cd_nm: '' }],
        CO00000009: [{ cd: '', cd_nm: '' }],
      },
      // 전체 userId-userName 쌍을 가진 사용자정보 객체[]
      users: [
        // {
        //  text: 'userKoreanName',
        //  value: 'userId'
        // }
      ],
      // 사용자 input value
      userInput: '',
      // 사용자조회 파라미터
      userSearchParam: {
        useYn: 'Y',
        delYn: 'N',
      },
    };
  },
  computed: {
    // 해당 권한(this.props.item) 의 사용자들을 "userId-userName" 쌍을 가진 객체[] 로 변환
    _users() {
      return this.item.userIdsArray.map((userId) => {
        const userInfo = this.users.find((user) => user.value === userId);
        return {
          text: userInfo ? userInfo.text : '',
          value: userId,
        };
      });
    },
  },
  async fetch() {
    await this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res;
        this.codes.SH00000042.unshift({ cd: '', cd_nm: 'SELECT' });
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'SELECT' });
      })
      .catch((err) => {
        console.log(err); // eslint-disable-line no-console
      });
    await this.searchUser();
  },
  methods: {
    doNothing() {},
    // 사용자 삭제
    removeUser(selectUserId){
      const currentIdx = this.item.userIdsArray.findIndex((userId) => userId === selectUserId);
      this.item.userIdsArray.splice(currentIdx, 1);
    },
    // 사용자 추가 (중복방지)
    changeUser(selectUserId) {
      const currentIdx = this.item.userIdsArray.findIndex((userId) => userId === selectUserId);
      currentIdx > -1 ? this.doNothing() : this.item.userIdsArray.push(selectUserId);
    },
    // 사용자 리스트 조회
    searchUser() {
      this.$axios.$get(`/api/v1/common/chems/user`, { params: this.userSearchParam }).then((res) => {
        const users = [];
        for (const user of res) {
          users.push({
            text: user.userKoreanName,
            value: user.userId,
          });
        }
        this.users = users;
      });
    },
    // 해당 권한에 물린 사용자들 저장하기
    saveUsers(){
      this.$emit("saveUsers", this.item.userIdsArray);
      this.close();
    },
    // 창 닫기
    close() {
      this.$emit('close');
      this.userInput = {};
    },
    save() {},
  },
};
</script>
