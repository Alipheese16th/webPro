<template>
  <div>
    <p>Login Sample</p>
    <v-form @submit.prevent="loginSubmit">
      <v-container>
        <v-row>
          <v-col cols="4">
            <v-text-field name="username" clearable label="userId" density="compact" variant="solo" single-line
              hide-details v-model="loginForm.username" />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="4">
            <v-text-field name="password" type="password" clearable label="password" density="compact" variant="solo"
              single-line hide-details v-model="loginForm.password" />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="8">
            <v-btn variant="tonal" type="submit">
              로그인
            </v-btn>
          </v-col>
          <v-col cols="12">
            <v-btn variant="tonal" @click="loginCheck">
              로그인 확인
            </v-btn>
          </v-col>
          <v-col cols="12">
            {{ loginUser }}
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>

<script>

import { mapActions, mapGetters } from "vuex";

export default {
  layout: "empty",
  data: () => ({
    loginForm: {
      username: "AC926031",
      password: "test",
    },
    loginUser: "",
  }),
  computed: {
    ...mapGetters({
      user: "auth/user"
    })
  },
  methods: {
    loginSubmit() {

      const form = new FormData();
      form.append("username", this.loginForm.username);
      form.append("password", this.loginForm.password);

      this.$axios.$post(`/api/v1/common/loginprocess`, form, {
        headers: {
          "content-type": "multipart/form-data"
        }
      })
        .then(response => {
          if (response !== "fail") {
            this.$store.dispatch("auth/setUser", response);
            this.$router.push("main");
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    loginCheck() {
      this.$axios.$get("/api/v1/login/session")
        .then(result => {
          this.loginUser = result;
        }).catch(err => {
          console.log(err);
        })
    }
  }
}
</script>