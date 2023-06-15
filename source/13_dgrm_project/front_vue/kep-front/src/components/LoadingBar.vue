<template>
  <div v-if="loading" class="loading-page">
    <div class="loading"></div>
  </div>
</template>
<script>
export default {
  data: () => ({
    loading: false,
  }),
  methods: {
    start() {
      this.loading = true
    },
    finish() {
      this.loading = false
    },
  },
}
</script>
<style scoped>
.loading-page {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 10000;
  padding: 30rem;
  text-align: center;
  font-size: 3rem;
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
}
.loading {
  display: inline-block;
  width: 6.5rem;
  height: 6.5rem;
  border: 8px solid #588cd1;
  border-radius: 50%;
  border-top-color: #1965c9;
  animation: spin 1s ease-in-out infinite;
}
@keyframes spin {
  to {
    -webkit-transform: rotate(360deg);
  }
}
</style>
