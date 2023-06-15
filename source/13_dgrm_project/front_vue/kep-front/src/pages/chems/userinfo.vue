<template>
  <v-dialog v-model="_isOpen" persistent width="1200px">
    <v-card class="container_pop">
      <v-toolbar flat class="mb-6">
        <!-- 사용자 등록 TODO: 번역필요 -->
        <v-toolbar-title>{{ _modify ? "사용자 수정" : "사용자 등록" }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- 닫기 -->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_detail">
        <v-container>
          <v-row>
            <v-col cols="1">
              <!-- 사번 TODO: 번역필요 -->
              <div class="label_tit">사번</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userId" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 부서 TODO: 번역필요 -->
              <div class="label_tit">부서</div>
            </v-col>
            <v-col cols="3">
              <v-autocomplete v-model="_infoData.departmentCode" label="ALL" item-text="dept_nm" item-value="dept_cd"
                :items="dept" outlined single-line :hide-details="true" dense height="30"></v-autocomplete>
            </v-col>
            <v-col cols="1">
              <!-- 사용여부 TODO: 번역필요 -->
              <div class="label_tit">사용여부</div>
            </v-col>
            <v-col cols="3">
              <v-select v-model="_infoData.useYn" label="ALL" item-text="cd_nm" item-value="cd" :items="yn_cd" outlined
                single-line :hide-details="true" dense height="30"></v-select>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 패스워드 TODO: 번역필요 -->
              <div class="label_tit">패스워드</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userPassword" outlined single-line :hide-details="true" dense
                  :append-icon="isShowPassword ? 'mdi-eye' : 'mdi-eye-off'" :type="isShowPassword ? 'text' : 'password'"
                  @click:append="isShowPassword = !isShowPassword" :placeholder="$t('LB00000033')" height="30"
                  maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 패스워드확인 TODO: 번역필요 -->
              <div class="label_tit">패스워드확인</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.passwordConfirm" outlined single-line
                  :append-icon="isShowPasswordConfirm ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="isShowPasswordConfirm ? 'text' : 'password'"
                  @click:append="isShowPasswordConfirm = !isShowPasswordConfirm" :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 삭제여부 TODO: 번역필요 -->
              <div class="label_tit">삭제여부</div>
            </v-col>
            <v-col cols="3">
              <v-select v-model="_infoData.delYn" label="ALL" item-text="cd_nm" item-value="cd" :items="yn_cd" outlined
                single-line :hide-details="true" dense height="30"></v-select>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 이름(한국어) TODO: 번역필요 -->
              <div class="label_tit">이름(한글)</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userKoreanName" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 이름(영어) TODO: 번역필요 -->
              <div class="label_tit">이름(영어)</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.userEnglishName" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 이름(독일어) TODO: 번역필요 -->
              <div class="label_tit">이름(독일어)</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.userGermanyName" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 직위 TODO: 번역필요 -->
              <div class="label_tit">직위</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.jkwCode" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 직무 TODO: 번역필요 -->
              <div class="label_tit">직무</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.jkgCode" outlined single-line :hide-details="true"
                  dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field></div>
            </v-col>
            <v-col cols="1">
              <!-- 직책 TODO: 번역필요 -->
              <div class="label_tit">직책</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.jkcCode" outlined single-line :hide-details="true"
                  dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field></div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 생년월일 TODO: 번역필요 -->
              <div class="label_tit">생년월일</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userBirthDate" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 입사일자 TODO: 번역필요 -->
              <div class="label_tit">입사일자</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.joinDate" outlined single-line :hide-details="true"
                  dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field></div>
            </v-col>
            <v-col cols="1">
              <!-- 재직구분 TODO: 번역필요 -->
              <div class="label_tit">재직구분</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.offiClsfCode" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 발령유형 TODO: 번역필요 -->
              <div class="label_tit">발령유형</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.appointmentTypeCode" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 파견회사 TODO: 번역필요 -->
              <div class="label_tit">파견회사</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.dispatchCompanyCode" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 파견부서 TODO: 번역필요 -->
              <div class="label_tit">파견부서</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.dispatchDepartmentCode" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 이메일 TODO: 번역필요 -->
              <div class="label_tit">이메일</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userEmail" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- IPT 전화번호 TODO: 번역필요 -->
              <div class="label_tit">IPT 전화번호</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.iptPhoneNo" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 회사 전화번호 TODO: 번역필요 -->
              <div class="label_tit">회사 전화번호</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.companyPhoneNo" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 휴대전화 TODO: 번역필요 -->
              <div class="label_tit">휴대전화</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-text-field v-model="_infoData.userCellPhoneNo" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 팩스 TODO: 번역필요 -->
              <div class="label_tit">팩스</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.faxNo" outlined single-line :hide-details="true"
                  dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field></div>
            </v-col>
            <v-col cols="1">
              <!-- 협력업체 여부 TODO: 번역필요 -->
              <div class="label_tit">협력업체 여부</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.partnersYn" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="1">
              <!-- 언어 TODO: 번역필요 -->
              <div class="label_tit">언어</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con">
                <v-autocomplete v-model="_infoData.languageCode" label="ALL" item-text="cd_nm" item-value="cd"
                  :items="codes.CO00000002" outlined single-line :hide-details="true" dense height="30"></v-autocomplete>
              </div>
            </v-col>
            <v-col cols="1">
              <!-- 퇴직일자 TODO: 번역필요 -->
              <div class="label_tit">퇴직일자</div>
            </v-col>
            <v-col cols="3">
              <div class="label_con"><v-text-field v-model="_infoData.retirementDate" outlined single-line
                  :hide-details="true" dense :placeholder="$t('LB00000033')" height="30" maxlength="19"></v-text-field>
              </div>
            </v-col>
            <v-col cols="1">
            </v-col>
            <v-col cols="3">
            </v-col>
          </v-row>

        </v-container>
      </div>
      <v-card-actions class="pop_btn" v-if="_modify">
        <v-btn color="primary" depressed width="120" height="40" @click="modify">{{ $t('LB00000045') }}</v-btn>
        <v-btn color="primary" depressed width="120" height="40" @click="del">{{ $t('LB00000046') }}</v-btn>
        <v-btn outlined width="120" height="40" @click="close">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
      <v-card-actions class="pop_btn" v-else>
        <v-btn color="primary" depressed width="120" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
        <v-btn outlined width="120" height="40" @click="close">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  props: {
    infoData: {
      type: Object,
      required: false,
      default: {
        userId: "",/* 사원_번호 */
        departmentCode: "",/* 부서_코드 */
        userKoreanName: "",/* 사원_명_한국어 */
        userEnglishName: "",/* 사원_명_엉어 */
        userGermanyName: "",/* 사원_명_독일 */
        jkwCode: "",/* 직위_코드 */
        jkwName: "",
        jkgCode: "",/* 직무_코드 */
        jkgName: "",
        jkcCode: "",/* 직책_코드 */
        jkcName: "",
        usr_birth_da: "",/* 사원_생년월일 */
        userBirthDate: "",/* 사원_입사_일자 */
        offiClsfCode: "",/* 재직_구분_코드 */
        appointmentTypeCode: "",/* 발령_유형_코드 */
        dispatchCompanyCode: "",/* 파견_회사_코드 */
        dispatchDepartmentCode: "",/* 파견_부서_코드 */
        userEmail: "",/* 사원_이메일 */
        iptPhoneNo: "",/* IPT_전화_번호 */
        companyPhoneNo: "",/* 회사_전화_번호 */
        userCellPhoneNo: "",/* 사원_휴대전화_번호 */
        faxNo: "",/* 팩스_번호 */
        partnersYn: "",/* 협력업체_여부 */
        languageCode: "",/* 언어_코드 */
        retirementDate: "",/* 퇴직_일자 */
        useYn: "",/* 사용_여부 */
        delYn: "",/* 삭제_여부 */
        userPassword: "",
        passwordConfirm: "",
        joinDate: "",
      }
    },
    isOpen: {
      type: Boolean,
      required: true,
      default: false,
    },
    isModify: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  data() {
    return {
      dept: [],
      select_dept: {},
      yn_cd: [
        { cd: '', cd_nm: 'ALL' },
        { cd: 'Y', cd_nm: 'Y' },
        { cd: 'N', cd_nm: 'N' },
      ],
      codeid: ['CO00000002'],
      codes: {
        CO00000002: [{ cd: '', cd_nm: '' }],
      },
      isShowPassword: false,
      isShowPasswordConfirm: false
    }
  },
  computed: {
    _isOpen() {
      return this.isOpen;
    },
    _modify() {
      return this.isModify;
    },
    _infoData() {
      return this.infoData;
    },
    _dept_cd: {
      get() {
        return this.infoData.departmentCode;
      },
      set(v) {
        this._infoData.departmentCode = v;
      }
    }
  },
  async fetch() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.SH00000042.unshift({ cd: '', cd_nm: 'ALL' })
        this.codes.CO00000009.unshift({ cd: '', cd_nm: 'ALL' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })

    // 부서코드 조회
    this.$axios.$get(`/api/v1/common/chems/user/dept-search`)
      .then(result => {
        this.dept = result;
      })
      .catch(err => {
        console.log(err);
      })
  },
  methods: {
    close() {
      this.$emit('close');
      this.reset();
    },
    reset() {
      // this.valid = true
      this.dialog = false
      this._infoData = {}
    },
    save() {
      this.validation();

      this.$axios.post("/api/v1/common/chems/user/create", this._infoData)
        .then(result => {
          if (result.status === 200) {
            this.close();
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    modify() {
      this.validation();

      this.$axios.patch("/api/v1/common/chems/user/update", this._infoData)
        .then(result => {
          if (result.status === 200) {
            this.close();
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    validation() {
      if (this._infoData.userPassword !== this._infoData.passwordConfirm) {
        alert("패스워드를 확인해 주십시오.(임시)");
        return false;
      }

      if (this._infoData.userPassword === undefined || this._infoData.userPassword === "") {
        alert("패스워드를 입력해 주십시오.(임시)");
        return false;
      }
      return true;
    },
    del() {
      this.$axios.patch(`/api/v1/common/chems/user/remove/${this._infoData.userId}`)
        .then(result => {
          if (result.status === 200) {
            alert("삭제되었습니다.");
            this.close();
          }
        })
        .catch(err => {
          console.log(err);
        })
    }
  },
}
</script>
