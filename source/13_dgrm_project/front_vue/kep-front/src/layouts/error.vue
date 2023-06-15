<template>
  <v-app dark>
    <div class="container_error">
      <h1 v-if="error.statusCode === 404">{{ pageNotFound }}</h1>
      <h1 v-else-if="error.statusCode === 403">{{ forbidden }}</h1>
      <h1 v-else-if="error.statusCode === 401">{{ unauthorized }}</h1>
      <h1 v-else>{{ otherError }}</h1>
      <div v-if="error.statusCode === 404">
        <div class="con01">요청하신 페이지를 찾을 수 없습니다.</div>
        <NuxtLink to="/">Home</NuxtLink>
      </div>
      <div v-else-if="error.statusCode === 403 || error.statusCode === 401">
        <div class="con01">
          인증되지 않았거나 <br />
          올바르지 못한 경로의 접근입니다.
        </div>
        <div class="con02">인증 후 다시 이용해주시기 바랍니다.</div>
        <a href="http://www.naver.com/jsso-agent/default.jsp">그룹웨어</a>
      </div>
      <div v-else>
        <div class="con01">
          서비스 이용에 불편을 드려 죄송합니다. <br />
          일시적인 장애로 인해 요청하신 작업을 수행할 수 없습니다.
        </div>
        <div class="con02">잠시 후 다시 이용해주시기 바랍니다. 빠른 시간 내에 정상적인 서비스를 제공할 수 있도록 노력하겠습니다.</div>
        <NuxtLink to="/">Home</NuxtLink>
      </div>
    </div>
  </v-app>
</template>

<script>
export default {
  layout: 'empty',
  props: {
    error: {
      type: Object,
      default: null,
    },
  },
  data() {
    return {
      unauthorized: 'Unauthorized',
      forbidden: 'Forbidden',
      pageNotFound: '404 Not Found',
      otherError: 'An error occurred',
    }
  },
  head() {
    const title =
      this.error.statusCode === 404 ? this.pageNotFound : this.error.statusCode === 403 ? this.forbidden : this.error.statusCode === 401 ? this.unauthorized : this.otherError
    return {
      title,
    }
  },
}
</script>

<style scoped>
.container_error {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -60%);
  margin: 0 auto;
  display: inline-block;
  text-align: center;
  line-height: 1.5;
  min-width: 1000px;
}
.container_error h1 {
  font-size: 48px;
  font-weight: 100;
  color: #000;
  letter-spacing: -1px;
}
.container_error .con01 {
  font-size: 22px;
  color: #000;
  margin-top: 20px;
}
.container_error .con02 {
  font-size: 18px;
  margin-top: 25px;
}
.container_error a {
  display: inline-block;
  margin-top: 45px;
  background: #062c5e;
  border-radius: 2px;
  color: #fff;
  font-weight: bold;
  text-decoration: none;
  padding: 10px 0;
  min-width: 160px;
}
.container_error a:hover {
  background-color: #023b85;
}
</style>
