<template>
  <div>
    <div class="location">
      <!--커뮤니티(KO)--><!--공지사항(KO)-->
      <span>Home > 샘플게시판 등록</span>
    </div>
    <div class="work_title">
      <!--공지사항(KO)-->
      <h2>샘플 게시판 등록222</h2>
    </div>
    <div class="container_detail">
      <div class="sub_title">
        <!--공지사항(KO)-->
        <h3>샘플 게시판222</h3>
      </div>
      <v-row>
        <v-col cols="12">
          <v-container>
            <v-row>
              <v-col cols="1">
                <!--제목(KO)-->
                <div class="label_tit">{{ $t('LB00000114') }}</div>
              </v-col>
              <v-col cols="3">
                <!--입력하세요-->
                <v-text-field v-model="boardForm.title" outlined single-line :hide-details="true" dense
                  :placeholder="$t('LB00000033')" height="30" maxlength="199"></v-text-field>
              </v-col>
              <v-col cols="1">
                <!--등록자-->
                <div class="label_tit">{{ $t('LB00000055') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ boardForm.username }}</div>
              </v-col>
              <v-col cols="1">
                <!--등록일-->
                <div class="label_tit">{{ $t('LB00000054') }}</div>
              </v-col>
              <v-col cols="3">
                <div class="label_con">{{ boardForm.date }}</div>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" style="padding: 20px 0 !important">
                <quill-editor v-model="boardForm.content" :options="editorOption" :disabled="quillChk"
                  @change="onEditorChange" />
              </v-col>
            </v-row>
          </v-container>
          <div class="group_padd"></div>
          <!--첨부파일 목록(KO)-->
          <!-- <h3>{{ $t('LB00000120') }}</h3>
          <div class="container_filelist">
            <table>
              <div v-if="true">
                <file-upload ref="upload" :multiple="true" @onDrop="chkFile()" />
              </div>
            </table>
          </div> -->
        </v-col>
      </v-row>
    </div>
    <div class="group_padd">&nbsp;</div>
    <div class="group_padd">&nbsp;</div>
    <div class="container_button">
      <!--저장-->
      <v-btn v-show="boardForm.boardId === undefined" depressed width="120" color="primary" height="40" @click="create">{{
        $t('LB00000045') }}</v-btn>
      <!--수정-->
      <v-btn v-show="boardForm.boardId !== undefined" depressed width="120" color="primary" height="40"
        @click="modify">수정</v-btn>
      <!--삭제-->
      <v-btn v-show="boardForm.boardId !== undefined" depressed width="120" color="primary" height="40" @click="remove">{{
        $t('LB00000046') }}</v-btn>
      <!--목록-->
      <v-btn outlined width="120" height="40" @click="goList">{{ $t('LB00000118') }}</v-btn>
    </div>
  </div>
</template>
<script>
import { quillEditor } from 'vue-quill-editor'
import FileUpload from '~/components/FileUpload.vue'

export default {
  meta: {
    title: '샘플 게시판 등록',
    key(route) {
      return `/sbbs/${route.params.catalog}`
    },
  },
  components: {
    quillEditor,
    FileUpload,
  },
  data: () => ({
    boardForm: {
      boardId: undefined,
      title: "",
      username: "",
      date: "",
      content: ""
    },
    quillChk: true,
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
  }),
  created() {

    this.boardForm.boardId = this.$route.params.boardedt;

    if (this.$route.params.boardedt !== "") {
      // 수정모드
      this.$axios.get(`/api/v1/public/sbbsNew/info/${this.$route.params.boardedt}`)
        .then(result => {
          if (result.status === 200) {
            const { boardId, title, content } = result.data;

            this.boardForm = {
              boardId, title, content
            }
          }
        }).catch(err => {
          console.log(err);
        })
    } else {
      // 등록모드
      // 샘플이기 때문에 Seoul로 하드코딩함.
      this.boardForm.date = this.getTzDate('Asia/Seoul', '-')
    }
  },
  mounted() {
    this.onEditorChange();
  },
  methods: {
    goList() {
      this.$router.push({
        name: "sbbs-boardNewlist"
      })
    },
    create() {
      this.$axios.post("/api/v1/public/sbbsNew/insertSelectKey", this.boardForm)
        .then(result => {
          if (result.status === 200) {
            alert("등록되었습니다.");
            this.goList();
          }
        }).catch(err => {
          console.log(err);
        })
    },
    modify() {
      this.$axios.post("/api/v1/public/sbbsNew/update", this.boardForm)
        .then(result => {
          if (result.status === 200) {
            alert("수정되었습니다.");
            this.goList();
          }
        }).catch(err => {
          console.log(err);
        })
    },
    remove() {
      this.$axios.post("/api/v1/public/sbbsNew/delete", this.boardForm)
        .then(result => {
          if (result.status === 200) {
            alert("삭제되었습니다.");
            this.goList();
          }
        }).catch(err => {
          console.log(err);
        })
    },
    changeEditMode(mode) {
      this.$refs.upload.setEdit(mode)
    },
    onEditorChange(editor) {
      this.quillChk = false
    },
  },
}
</script>
<style lang="scss" scoped></style>
