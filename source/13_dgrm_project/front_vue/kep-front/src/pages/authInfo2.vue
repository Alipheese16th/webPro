<template>
  <v-app dark>
    <div class="container_error">
      <h1>Unauthorized</h1>
      <div>
        <div class="con01">90일 이상 로그인 기록이 없습니다.</div>
        <div class="con02">인증 후 다시 이용해주시기 바랍니다.</div>
        <a href="http://www.naver.com">그룹웨어</a>
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
