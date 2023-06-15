<template>
  <div class="container_homeui">
    <div class="homeui">
      <div class="container_step">
        <div style="display: flex">
          <div class="logo" style="width: 100%; height: 100px"></div>
        </div>
        <div style="display: flex">
          <div class="img_text" style="width: 100%; height: 80px"></div>
        </div>
        <div style="display: flex; justify-content: center">
          <div>
            <a href="/app/android.apk" type="application/vnd.android.package-archive" target="_blank">
              <div class="android" style="cursor: pointer; width: 180px; height: 175px"></div>
            </a>
          </div>
          <div>
            <a href="itms-services://?action=download-manifest&url=https://dehs.donggramisoft.co.kr/app/ipa.plist">
              <div class="iphone" style="cursor: pointer; width: 180px; height: 175px"></div>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // middleware: 'authenticated',
  meta: {
    title: 'Home',
    // closable: false,
  },
  layout: 'empty',
  mounted() {
    // eslint-disable-next-line no-console
    console.log(this.$route.query)
    if (!this.isEmpty(this.$route.query.new)) {
      alert('최신 앱과 버전이 상이합니다. 새 버전을 다운로드 받아주세요.')
    }
  },
  methods: {
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
  },
}
</script>

<style scoped>
.container_homeui {
  /* background: #ecedf2; */
  padding-bottom: 30px;
  align-content: center;
}
.homeui {
  margin: 0 auto;
  width: 100%;
  padding-top: 30px;
  display: flex;
}
.homeui > div {
  width: 100%;
}
.container_step {
  width: 100%;
  position: relative;
}
.container_step .logo {
  background: #fff url('~assets/images/logo1.png') no-repeat center center !important;
  background-size: 80% !important;
}
.container_step .img_text {
  background: #fff url('~assets/images/img_text.gif') no-repeat center center !important;
  background-size: 90% !important;
}
.container_step .android {
  background: #fff url('~assets/images/btn_androidN.gif') no-repeat 20px center !important;
  background-size: 80% !important;
}
.container_step .iphone {
  background: #fff url('~assets/images/btn_iphoneN.gif') no-repeat 20px center !important;
  background-size: 80% !important;
}
</style>
