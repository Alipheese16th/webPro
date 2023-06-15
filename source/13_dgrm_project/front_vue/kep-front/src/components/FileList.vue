<template>
  <div>
    <div class="container_filelist" style="max-height: 100px">
      <table>
        <tr>
          <td><a href="">첨부파일 01.pdf</a></td>
          <td>123kb</td>
          <td><button class="btn_download">다운로드</button></td>
        </tr>
        <tr>
          <td><a href="">첨부파일 01.pdf</a></td>
          <td>123kb</td>
          <td><button class="btn_download">다운로드</button></td>
        </tr>
        <tr>
          <td><a href="">첨부파일 01.pdf</a></td>
          <td>123kb</td>
          <td><button class="btn_download">다운로드</button></td>
        </tr>
        <tr>
          <td><a href="">첨부파일 01.pdf</a></td>
          <td>123kb</td>
          <td><button class="btn_download">다운로드</button></td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    propFiles: {
      type: [Array],
      default() {
        return []
      },
    },
  },
  data() {
    return {
      files: [],
    }
  },
  mounted() {
    // this.files = this.propFiles
  },
  methods: {
    reset() {
      console.log('reset files : ', this.files) // eslint-disable-line no-console
    },
  },
}
</script>

<style scoped></style>
