<template>
  <div>
    <div class="location">
      <span>Home > 테스트 메뉴 > 사용자 상세  </span>
    </div>
    <div class="work_title">
      <h2>기본정보</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--기본정보-->
        <h3>기본정보</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>

            <v-row>
              <v-col cols="1">
                <!--사용자ID-->
                <div class="label_tit essn">사용자ID</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="savedatas.userId"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                  :readonly="readonly"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용자명-->
                <div class="label_tit essn">사용자명</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="savedatas.userName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용자영문명-->
                <div class="label_tit">사용자영문명</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field
                  v-model="savedatas.userEnglishName"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  height="30"
                  maxlength="199"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit">사용여부</div>
              </v-col>
              <v-col cols="3">
                <v-select v-model="savedatas.useYn" label="ALL" item-text="cd_nm" item-value="cd" :items="use_yn_cd"
                  outlined single-line :hide-details="true" dense height="30"></v-select>
              </v-col>
              <v-col cols="1">
                <!--사용자등록일-->
                <div class="label_tit">사용자등록일</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ savedatas.userRegistDate }}</div>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="1">
                <!--최초등록일시-->
                <div class="label_tit">최초등록일시</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ savedatas.createDateTime }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종수정일시-->
                <div class="label_tit">최종수정일시</div>
              </v-col>
              <v-col cols="7">
                <div class="label_con">{{ savedatas.updateDateTime }}</div>
              </v-col>
            </v-row>

          </v-container>

          <div class="group_padd"></div>

        </v-col>
      </v-row>
    </div>

    <!-- <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div> -->

    <div class="container_button">
      <!--저장-->
      <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
      <!--삭제-->
      <v-btn v-if="savedatas.userKey != ''" depressed width="120" color="primary" height="40" @click="del">{{ $t('LB00000046') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>

  </div>
</template>
<script>

export default {
  meta: {
    title: '사용자 상세',
    key(route) {
      return `/test/user`
    },
  },
  components: {

  },
  props: {},
  data() {
    return {

      readonly:false,

      // 목록 화면에서 보내준 데이터
      searchdatas: {
        userId: '',
        userName: '',
        useYn: '',
        sDate: '',
        eDate: '',
      },

      savedatas: {
        userKey:'',
        userId: '',
        userName: '',
        userEnglishName: '',
        useYn: '',
        userRegistDate: '',
        createDateTime: '',
        updateDateTime: '',
      },

      use_yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],

      menu1: false,
      menu2: false,
      menu3: false,
      menu4: false,
      editorOption: {
        // some quill options
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // toggled buttons
            ['blockquote', 'code-block'],

            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }], // outdent/indent

            [{ header: [1, 2, 3, 4, 5, 6, false] }],

            [{ color: [] }, { background: [] }], // dropdown with defaults from theme
            [{ align: [] }],

            ['link', 'image'],

            ['clean'], // remove formatting button
          ],
        },
        placeholder: '',
      },
      tempData: {
        userId: '',
        userName: '',
        userEnglishName: '',
        useYn: '',
      },
    }
  },
  computed: {
    routeTab() {
      return '사용자 상세'
    },

  },

  async beforePageLeave(tab, type) {
    if (
      this.tempData.userId !== this.savedatas.userId ||
      this.tempData.userName !== this.savedatas.userName ||
      this.tempData.userEnglishName !== this.savedatas.userEnglishName ||
      this.tempData.useYn !== this.savedatas.useYn
    ) {
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

  mounted() {

    // 검색데이터 유지
    console.log('검색데이터 유지');
    console.log(this.$route.params.schData);
    this.searchdatas.userId = this.$route.params.schData.userId;
    this.searchdatas.userName = this.$route.params.schData.userName;
    this.searchdatas.useYn = this.$route.params.schData.useYn;
    this.searchdatas.sDate = this.$route.params.schData.sDate;
    this.searchdatas.eDate = this.$route.params.schData.eDate;

    // 파라미터 조회 - 파라미터가 존재할 경우
    if (this.$route.params.userData !== null && this.$route.params.userData !== undefined) {
      console.log('파라미터로 넘어온 유저데이터');
      console.log(this.$route.params.userData);
      this.savedatas.userKey = this.$route.params.userData.userKey;
      this.selectDtl()
    }

  },

  methods: {

    async selectDtl() {

      let data = await this.$axios.$get(`/api/v1/test/user/list/`, { params: this.savedatas })

      console.log('db에서 select한 데이터');
      console.log(data[0]);

      this.savedatas.userId = data[0].userId;
      this.savedatas.userName = data[0].userName;
      this.savedatas.userEnglishName = data[0].userEnglishName;
      this.savedatas.useYn = data[0].useYn;
      this.savedatas.userRegistDate = data[0].userRegistDate;
      this.savedatas.createDateTime = data[0].createDateTime;
      this.savedatas.updateDateTime = data[0].updateDateTime;

      this.readonly = true;

      this.tempData.userId = data[0].userId;
      this.tempData.userName = data[0].userName;
      this.tempData.userEnglishName = data[0].userEnglishName;
      this.tempData.useYn = data[0].useYn;
     
    },

    goList() {
      this.$router.push({
        name: `test-usermgt`,
        params: {
          schData: {
                userId: this.searchdatas.userId,
                userName: this.searchdatas.userName,
                useYn: this.searchdatas.useYn,
                sDate: this.searchdatas.sDate,
                eDate: this.searchdatas.eDate,
            },
        },
      })
    },

    validationChk() {

      // id 유효성검사
      let chkUserId = this.savedatas.userId
      let num = chkUserId.search(/[0-9]/g)
      let eng = chkUserId.search(/[a-z]/ig)
      let kor = chkUserId.search(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g)
      let spc = chkUserId.search(/[~`!@#$%^&*()\-_+=|\\\[\]{}'";:?,.<>\/]/gi)
      if (!chkUserId) {
        this.$toast.show('사용자ID를 입력해 주십시오', { className: 'toast_error' })
        return false
      }else if (chkUserId.search(/\s/)!=-1){
        this.$toast.show('사용자ID에 공백을 입력하지마세요', { className: 'toast_error' })
        return false
      }else if(kor >= 0){
        this.$toast.show('사용자ID에 한글을 입력하지마세요', { className: 'toast_error' })
        return false
      }else if(spc >= 0){
        this.$toast.show('사용자ID에 특수문자를 입력하지마세요', { className: 'toast_error' })
        return false
      }else if(num < 0 && eng < 0){
        this.$toast.show('사용자ID는 영어와 숫자로만 입력 할 수 있습니다', { className: 'toast_error' })
        return false
      }else if(chkUserId.length < 2 || chkUserId.length > 20){
        this.$toast.show('사용자ID는 2~20글자로 작성해주세요', { className: 'toast_error' })
        return false
      }

      // 이름 유효성 검사
      let reg_name = /^[가-힣]{2,20}$/;
      let chkUserName = this.savedatas.userName;
      if (!chkUserName) {
        this.$toast.show('사용자명을 입력해 주세요', { className: 'toast_error' })
        return false
      } else if(chkUserName.search(/\s/)!=-1){
        this.$toast.show('사용자명에 공백을 입력하지마세요', { className: 'toast_error' })
        return false
      }else if(!chkUserName.match(reg_name)){
        this.$toast.show('사용자명은 2~20자의 완성형 한글로 입력해주세요', { className: 'toast_error' })
        return false
      }

      // 사용자영문명 유효성 검사
      let reg_englishName = /^[a-z0-9]{2,20}$/;
      let chkUserEnglishName = this.savedatas.userEnglishName;
      if (chkUserEnglishName) {
        if(chkUserEnglishName.search(/\s/)!=-1){
          this.$toast.show('사용자영문명에 공백을 입력하지마세요', { className: 'toast_error' })
          return false
        }else if(!chkUserEnglishName.match(reg_englishName)){
          this.$toast.show('사용자명은 2~20자의 영문이나 숫자로만 입력가능합니다', { className: 'toast_error' })
          return false
        }
      }

      // 사용여부 유효성 검사
      if (!this.savedatas.useYn) {
        this.$toast.show('사용여부를 골라주세요', { className: 'toast_error' })
        return false
      }

      return true

    },

    async save() {
      if (!this.validationChk()) {
        return
      }

      // 변경된 값이 없으면 저장하지 않는다
      if (
        this.tempData.userId === this.savedatas.userId &&
        this.tempData.userName === this.savedatas.userName &&
        this.tempData.userEnglishName === this.savedatas.userEnglishName &&
        this.tempData.useYn === this.savedatas.useYn
      ){
        this.$toast.show('변경된 내용이 없습니다')
        return
      }

      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      this.$axios.post('/api/v1/test/user/save', this.savedatas).then((res) => {

        if(res.data === 'Duplication'){
          this.$toast.show('아이디가 중복되었습니다')
        }else if(res.data === 'Fail'){
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }else{
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.savedatas.userKey = res.data
          //this.$toast.show(this.savedatas.userKey)
          this.selectDtl()
        }

      })
    },

    async del() {
      const res = await this.$confirm(this.$t('MS00000037')) // 삭제하시겠습니까?(KO)
      if (!res) {
        return
      }
      this.$axios.delete(`/api/v1/test/user/delete/${this.savedatas.userKey}`).then((res) => {
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },

  },
}
</script>
<style lang="scss" scoped></style>
