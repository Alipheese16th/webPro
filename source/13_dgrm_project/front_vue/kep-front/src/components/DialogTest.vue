<template>
  <v-dialog v-model="dialog" persistent width="400px">
    <v-toolbar color="blue-grey">
      <v-toolbar-title>모달 팝업 테스트</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-container>
      <v-form ref="form">
        <v-text-field v-model="frmData.subject" label="제목"></v-text-field>
        <v-textarea v-model="frmData.contents" solo label="내용"></v-textarea>
      </v-form>
    </v-container>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text color="primary" @click="submit()">저장</v-btn>
      <v-btn text color="success" @click="close()">닫기</v-btn>
    </v-card-actions>
  </v-dialog>
</template>
<script>
export default {
  props: {
    // subject: {
    //   type: [String],
    //   default() {
    //     return ''
    //   },
    // },
  },
  data() {
    return {
      dialog: false,
      frmData: {},
    }
  },

  methods: {
    open(frmData) {
      this.frmData = frmData
      this.dialog = true
    },
    submit() {
      this.$emit('close', this.frmData)
      this.dialog = false
    },
    close() {
      this.$emit('close', undefined)
      this.dialog = false
    },
  },
}
</script>
