<template>
  <v-app style="min-width: 0 !important">
    <Nuxt />
  </v-app>
</template>

<script>
export default {
  created() {
    this.$vuetify.theme.dark = false
  },
}
</script>
