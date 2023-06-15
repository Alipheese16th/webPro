<template>
  <v-app dark>
    <div class="container_error">
      <h1>An error occurred</h1>
      <div>
        <div class="con01">
          서비스 이용에 불편을 드려 죄송합니다. <br />
          일시적인 장애로 인해 요청하신 작업을 수행할 수 없습니다.
        </div>
        <div class="con02">잠시 후 다시 이용해주시기 바랍니다. 빠른 시간 내에 정상적인 서비스를 제공할 수 있도록 노력하겠습니다.</div>
        <NuxtLink to="/">Home</NuxtLink>
        <a href="javascript:history.go(-2)">이전페이지</a>
      </div>
    </div>
  </v-app>
</template>

<script>
export default {
  layout: 'empty',
  data() {
    return {
      home: '/',
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
