<template>
  <v-dialog v-model="dialog" persistent width="400px">
    <v-form ref="form">
      <v-text-field v-model="frmData.username" label="User ID" prepend-icon="mdi-account"></v-text-field>
      <v-text-field v-model="frmData.email" label="Email" prepend-icon="mdi-email"></v-text-field>
    </v-form>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn text color="primary" @click="submit()">선택</v-btn>
      <v-btn text color="success" @click="close()">닫기</v-btn>
    </v-card-actions>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      frmData: {
        username: 'admin',
        email: 'admin@test.com',
      },
    }
  },

  methods: {
    open() {
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
