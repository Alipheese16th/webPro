<template>
  <div>
    <div class="location">
      <!-- Home > 안전 > SOP 관리 -->
      <span>Home > {{ $t('LB00000717') }} > {{ $t('LB00000931') }}</span>
    </div>
    <div class="work_title">
      <!-- SOP 관리 -->
      <h2>{{ $t('LB00000931') }}</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 기본정보 -->
        <h3>{{ $t('LB00000731') }}</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--카테고리-->
                <div class="label_tit essn">{{ $t('LB00000934') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="sop_cat_cd"
                  v-model="frmData.sop_cat_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000008"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--업무구분-->
                <div class="label_tit essn">{{ $t('LB00000062') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="sop_bsns_cl_cd"
                  v-model="frmData.sop_bsns_cl_cd"
                  label="SELECT"
                  item-text="cd_nm"
                  item-value="cd"
                  :items="codes.SH00000009"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--최종 수정자-->
                <div class="label_tit">{{ $t('LB00000727') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.lsch_nm }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--등록회사-->
                <div class="label_tit">{{ $t('LB00000940') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.cmpy_nm }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록부서-->
                <div class="label_tit">{{ $t('LB00000938') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.fscr_dept }}</div>
              </v-col>
              <v-col cols="1">
                <!--최종 수정일-->
                <div class="label_tit">{{ $t('LB00000939') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ frmData.upt_dt }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--문서번호-->
                <div class="label_tit essn">{{ $t('LB00000935') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="sop_doc_no"
                  v-model="frmData.sop_doc_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--문서개정번호-->
                <div class="label_tit essn">{{ $t('LB00000941') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="sop_doc_rvsn_no"
                  v-model="frmData.sop_doc_rvsn_no"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--SOP명-->
                <div class="label_tit essn">{{ $t('LB00000932') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="sop_nm"
                  v-model="frmData.sop_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--관련법규 유무-->
                <div class="label_tit essn">{{ $t('LB00000936') }}</div>
              </v-col>
              <v-col cols="3">
                <v-select
                  ref="rglt_rel_sop_yn"
                  v-model="frmData.rglt_rel_sop_yn"
                  label="SELECT"
                  :items="sopYn"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  height="30"
                  @change="codChg"
                ></v-select>
              </v-col>
              <v-col cols="1">
                <!--관련 법규명-->
                <div :class="[basic, puse_yn]">{{ $t('LB00000942') }}</div>
              </v-col>
              <v-col cols="3">
                <v-text-field
                  ref="sop_rel_rglt_nm"
                  v-model="frmData.sop_rel_rglt_nm"
                  outlined
                  single-line
                  :hide-details="true"
                  dense
                  :placeholder="$t('LB00000033')"
                  clearable
                  height="30"
                  :disabled="frmData.rglt_rel_sop_yn === 'N' || frmData.rglt_rel_sop_yn === undefined"
                ></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--사용여부-->
                <div class="label_tit essn">{{ $t('LB00000049') }}</div>
              </v-col>
              <v-col cols="3">
                <v-switch ref="usg_yn" v-model="frmData.usg_yn" true-value="Y" false-value="N" outlined single-line :hide-details="true" dense height="30"></v-switch>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="1">
                <!--SOP 개요-->
                <div class="label_tit essn">{{ $t('LB00000943') }}</div>
              </v-col>
              <v-col cols="11">
                <v-textarea ref="sop_txt" v-model="frmData.sop_txt" outlined single-line :hide-details="true" dense :placeholder="$t('LB00000033')" name="input-7-4"></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </div>
    <div class="group_padd"></div>
    <div class="container_detail">
      <div class="sub_title">
        <!-- 주요내용 -->
        <h3>{{ $t('LB00000944') }}</h3>
      </div>
      <v-container>
        <v-row>
          <v-col cols="12" style="padding: 20px 0 !important">
            <quill-editor ref="quill" v-model="frmData.sop_rel_rglt_txt" :options="editorOption" />
          </v-col>
        </v-row>
      </v-container>
      <div class="group_padd"></div>
      <!--첨부파일 목록(KO)-->
      <h3>{{ $t('LB00000120') }}</h3>
      <div class="container_filelist">
        <table>
          <div v-if="true">
            <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
          </div>
        </table>
      </div>
    </div>
    <br />
    <div class="container_detail">
      <div class="grid_header">
        <div class="grid_header_left"></div>
        <div>
          <!--삭제-->
          <v-btn depressed width="120" color="primary" height="40" :disabled="!deleteModify" @click="delInfo()">{{ $t('LB00000046') }}</v-btn>
          <!--저장-->
          <v-btn depressed width="120" color="primary" height="40" @click="save">{{ $t('LB00000045') }}</v-btn>
          <!--목록-->
          <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: 'SOP 관리',
    key(route) {
      return `/sopmgnt/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data() {
    return {
      chkcnt: 0,
      modify: false,
      codeid: ['SH00000008', 'SH00000009'],
      codes: {
        SH00000008: [{ cd: '', cd_nm: '' }], // 카테고리
        SH00000009: [{ cd: '', cd_nm: '' }], // 업무구분
      },
      sopYn: ['Y', 'N'],
      useYn: ['Y', 'N'],
      basic: 'label_tit',
      puse_yn: '',
      schData: {},
      frmData: {},
      tempData: {},
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
      deleteModify: false,
      isAdminGrp: false,
    }
  },
  computed: {
    routeTab() {
      return this.$t('LB00000931') // SOP 관리
    },
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  async beforePageLeave(tab, type) {
    if (this.modify && JSON.stringify(this.tempData) !== JSON.stringify(this.frmData)) {
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
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res

        if (this.$route.params.sopmgntdtledt !== null && this.$route.params.sopmgntdtledt !== undefined) {
          this.frmData.sop_id = this.$route.params.sopmgntdtledt
          this.schData.sop_id = this.$route.params.sopmgntdtledt
        } else {
          this.frmData.sop_id = ''
          this.schData.sop_id = ''
        }

        // 이전 검색 결과 저장
        if (this.$route.params.schData) {
          this.schData = this.$route.params.schData
          this.frmData.sop_id = this.schData.sop_id
        }

        // ID 존재할 경우
        if (this.frmData.sop_id) {
          this.modify = true
          this.selectWkpl('select')
          this.$refs.quill.quill.enable(false)
        } else {
          const user = this.$store.getters['auth/getUser']
          this.frmData.lsch_nm = user.emp_nm
          this.frmData.cmpy_nm = user.cmpy_nm
          this.frmData.fscr_dept = user.dept_nm
          this.frmData.upt_dt = this.get_date_str_gubun(this.getTzDateObj(user.time_zone), '-')
          // this.frmData.rglt_rel_sop_yn = ''
          this.frmData.usg_yn = 'Y'
          this.selectWkpl()
        }
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    selectWkpl(mode) {
      this.$axios.$get(`/api/v1/she/sfty/sopmgnt`, { params: { type: 'base_wkpl' } }).then((res) => {
        if (mode) {
          this.select()
        }
      })
    },
    select() {
      this.modify = true
      const user = this.$store.getters['auth/getUser']
      this.$axios.$get(`/api/v1/she/sfty/sopmgnt/${this.frmData.sop_id}`).then((res) => {
        this.frmData.sop_id = res.sop_id
        this.frmData.sop_cat_cd = res.sop_cat_cd
        this.frmData.sop_bsns_cl_cd = res.sop_bsns_cl_cd
        this.frmData.sop_doc_no = res.sop_doc_no
        this.frmData.sop_doc_rvsn_no = res.sop_doc_rvsn_no
        this.frmData.sop_nm = res.sop_nm
        this.frmData.rglt_rel_sop_yn = res.rglt_rel_sop_yn
        this.frmData.sop_rel_rglt_nm = res.sop_rel_rglt_nm
        this.frmData.sop_rel_rglt_txt = res.sop_rel_rglt_txt
        this.frmData.usg_yn = res.usg_yn
        this.frmData.sop_atfl_no = res.sop_atfl_no
        this.frmData.cmpy_nm = res.cmpy_nm
        this.frmData.sop_txt = res.sop_txt
        this.frmData.fscr_dept = res.fscr_dept
        this.frmData.crt_emp_no = res.crt_emp_no
        this.frmData.lsch_nm = res.lsch_nm
        // this.frmData.upt_dt = res.upt_dt
        this.$set(this.frmData, 'upt_dt', res.upt_dt)
        // 적용사업장
        // for (let i = 0; i < dataProvider.getRowCount(); i++) {
        //   if (res.wkpl_list.findIndex((e) => e.wkpl_id === dataProvider.getValue(i, 'wkpl_id')) >= 0) {
        //     dataProvider.setValue(i, 'chk', 'Y')
        //   }
        // }

        if (this.frmData.sop_atfl_no) {
          this.$axios.$get(`/api/v1/common/file/${this.frmData.sop_atfl_no}`).then((data) => {
            console.log('files : ', data) // eslint-disable-line no-console
            if (data.length > 0) {
              this.setFiles(data, true)
            }
          })
        }

        const usrinfo = this.$store.getters['auth/getUser']
        // 시스템그룹 사용자
        usrinfo.roleTypeCds.forEach((i) => {
          if (i === 'M01') {
            this.isAdminGrp = true
          }
        })

        if (this.frmData.crt_emp_no === user.emp_no || this.isAdminGrp) {
          this.deleteModify = true
        }

        this.frmData.ckFlag = false
        this.frmData.ckFile = false
        this.tempData = JSON.parse(JSON.stringify(this.frmData))

        // 에디터 포커스 제거
        this.$nextTick(function () {
          this.$refs.quill.quill.enable(true)
          this.$refs.quill.quill.blur()
        })
      })
    },
    save() {
      if (this.modify) {
        if (JSON.stringify(this.tempData) === JSON.stringify(this.frmData)) {
          this.$toast.show(this.$t('MS00000013'), { className: 'toast_error' }) // 저장할 항목이 없습니다.
        } else {
          this.update()
        }
      } else {
        this.insert()
      }
    },
    async insert() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      await this.saveFiles(true)

      this.frmData.wkpl_list = []
      // for (let i = 0; i < dataProvider.getRowCount(); i++) {
      //   if (dataProvider.getValue(i, 'chk') === 'N') {
      //     continue
      //   }
      //   const wkpl = {}
      //   wkpl.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
      //   this.frmData.wkpl_list.push(wkpl)
      // }

      await this.$axios.post('/api/v1/she/sfty/sopmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.sop_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async update() {
      if (!this.validate()) {
        return
      }
      const res = await this.$confirm(this.$t('MS00000010')) // 저장하시겠습니까?
      if (!res) {
        return
      }

      if (this.frmData.ckFile) {
        await this.saveFiles(true)
      }

      this.frmData.wkpl_list = []
      // for (let i = 0; i < dataProvider.getRowCount(); i++) {
      //   if (dataProvider.getValue(i, 'chk') === 'N') {
      //     continue
      //   }
      //   const wkpl = {}
      //   wkpl.wkpl_id = dataProvider.getValue(i, 'wkpl_id')
      //   this.frmData.wkpl_list.push(wkpl)
      // }

      await this.$axios.put('/api/v1/she/sfty/sopmgnt', this.frmData).then((res) => {
        if (res.data !== 'Fail') {
          this.$toast.show(this.$t('MS00000011')) // 저장되었습니다.
          this.frmData.sop_id = res.data
          this.select()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    async delInfo() {
      const res = await this.$confirm(this.$t('MS00000042')) // 삭제하시겠습니까?
      if (!res) {
        return
      }

      await this.$axios.put(`/api/v1/she/sfty/sopmgnt/${this.frmData.sop_id}`).then((res) => {
        console.log(res) // eslint-disable-line no-console
        if (res.data === 'Success') {
          this.$toast.show(this.$t('MS00000041')) // 삭제되었습니다.
          this.goList()
        } else {
          this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' }) // 오류가 발생했습니다.
        }
      })
    },
    validate() {
      // gridView.commit()

      if (!this.frmData.sop_cat_cd) {
        this.$toast.show(this.$t('MS00000447'), { className: 'toast_error' }) // 카테고리를 선택해주세요.
        this.$refs.sop_cat_cd.focus()
        return false
      }
      if (!this.frmData.sop_bsns_cl_cd) {
        this.$toast.show(this.$t('MS00000402'), { className: 'toast_error' }) // 업무구분을 선택해주세요.
        this.$refs.sop_bsns_cl_cd.focus()
        return false
      }
      if (!this.frmData.sop_doc_no) {
        this.$toast.show(this.$t('MS00000448'), { className: 'toast_error' }) // 문서번호를 입력해주세요.
        this.$refs.sop_doc_no.focus()
        return false
      }
      if (this.frmData.sop_doc_no.length > 20) {
        this.$toast.show(this.$t('MS00000449'), { className: 'toast_error' }) // 문서번호를 20자 이하로 입력해주세요.
        this.$refs.sop_doc_no.focus()
        return false
      }
      if (!this.frmData.sop_doc_rvsn_no) {
        this.$toast.show(this.$t('MS00000450'), { className: 'toast_error' }) // 문서개정번호를 입력해주세요.
        this.$refs.sop_doc_rvsn_no.focus()
        return false
      }
      if (this.frmData.sop_doc_rvsn_no.length > 20) {
        this.$toast.show(this.$t('MS00000451'), { className: 'toast_error' }) // 문서개정번호를 20자 이하로 입력해주세요.
        this.$refs.sop_doc_rvsn_no.focus()
        return false
      }
      if (!this.frmData.sop_nm) {
        this.$toast.show(this.$t('MS00000452'), { className: 'toast_error' }) // SOP명을 입력해주세요.
        this.$refs.sop_nm.focus()
        return false
      }
      if (this.frmData.sop_nm.length > 100) {
        this.$toast.show(this.$t('MS00000453'), { className: 'toast_error' }) // SOP명을 100자 이하로 입력해주세요.
        this.$refs.sop_nm.focus()
        return false
      }
      if (!this.frmData.rglt_rel_sop_yn) {
        this.$toast.show(this.$t('MS00000454'), { className: 'toast_error' }) // 관련법규 유무를 선택해주세요.
        this.$refs.rglt_rel_sop_yn.focus()
        return false
      }
      if (this.frmData.rglt_rel_sop_yn === 'Y') {
        if (!this.frmData.sop_rel_rglt_nm) {
          this.$toast.show(this.$t('MS00000455'), { className: 'toast_error' }) // 관련 법규명을 입력해주세요.
          this.$refs.sop_rel_rglt_nm.focus()
          return false
        }
        if (!this.isEmpty(this.frmData.sop_rel_rglt_nm) && this.frmData.sop_rel_rglt_nm.length > 200) {
          this.$toast.show(this.$t('MS00000456'), { className: 'toast_error' }) // 관련 법규명을 200자 이하로 입력해주세요.
          this.$refs.sop_rel_rglt_nm.focus()
          return false
        }
      }
      // if (!this.frmData.usg_yn) {
      //  this.$toast.show(this.$t('MS00000457'), { className: 'toast_error' }) // 사용여부를 선택해주세요.
      //  this.$refs.usg_yn.focus()
      //  return false
      // }
      if (!this.frmData.sop_txt) {
        this.$toast.show(this.$t('MS00000458'), { className: 'toast_error' }) // SOP개요를 입력해주세요.
        this.$refs.sop_txt.focus()
        return false
      }
      if (this.frmData.sop_txt.length > 1000) {
        this.$toast.show(this.$t('MS00000459'), { className: 'toast_error' }) // SOP개요를 1000자 이하로 입력해주세요.
        this.$refs.sop_txt.focus()
        return false
      }
      return true
    },
    get_date_str_gubun(date, gubun) {
      const sYear = date.getFullYear()
      let sMonth = date.getMonth() + 1
      let sDate = date.getDate()

      sMonth = sMonth > 9 ? sMonth : '0' + sMonth
      sDate = sDate > 9 ? sDate : '0' + sDate
      return sYear + gubun + sMonth + gubun + sDate
    },
    goList() {
      this.$router.push({
        name: `sopmgnt-sopmgntlist`,
        params: {
          schData: this.schData,
        },
      })
    },
    codChg(data) {
      if (data === 'Y') {
        this.puse_yn = 'essn'
      } else {
        this.frmData.sop_rel_rglt_nm = ''
        this.puse_yn = ''
      }
    },
    chkFile() {
      this.frmData.ckFile = true
    },
    setFiles(files, mode) {
      this.$nextTick(() => {
        this.$refs.upload.setFiles(files)
        this.$refs.upload.setEdit(mode)
      })
    },
    async saveFiles(mode) {
      await this.$refs.upload.uploadStart().then((data) => {
        console.log('File Id : ', data) // eslint-disable-line no-console
        if (data) {
          this.frmData.sop_atfl_no = data
          // this.$axios.$get(`/api/v1/common/file/${this.frmData.sop_atfl_no}`).then((data) => {
          //   console.log('files : ', data) // eslint-disable-line no-console
          //   if (data.length > 0) {
          //     this.setFiles(data, mode)
          //   }
          // })
        }
      })
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
  },
}
</script>
<style></style>
