<template>
  <div>
    <UploadImages ref="upload" @changed="handleImages" @changeFile="changeValCheck" />
  </div>
</template>

<script>
import UploadImages from '../../components/VueUploadImages'
export default {
  components: {
    UploadImages,
  },
  data() {
    return {
      files: [],
      updateKey: '0',
    }
  },
  methods: {
    changeValCheck(changeVal) {
      this.updateKey = changeVal
    },
    handleImages(files) {
      this.files = files
    },
    retrunData() {
      return this.files.length
    },
    setEdit(isEdit) {
      this.$refs.upload.setEdit(isEdit)
    },
    setMaxFileCnt(nCnt) {
      this.$refs.upload.$props.max = nCnt
    },
    isEmpty(str) {
      if (typeof str === 'undefined' || str == null || str === '') return true
      else return false
    },
    reset() {
      this.files = []
      this.updateKey = '0'
      this.$refs.upload.reset()
    },
    load(attachId) {
      this.reset() // 리셋으로 초기화 후 로드
      if (!this.isEmpty(attachId)) {
        this.$refs.upload.setImages(attachId)
        this.updateKey = attachId
      }
    },
    updateKeyChk() {
      if (!this.$refs.upload.isOldFile()) this.updateKey = '0'
    },
    validate() {
      if (this.files.length === 0) return false
      return true
    },
    save() {
      this.updateKeyChk() // 내부에 파일이 존재하지 않을 경우 updateKey 변경
      return new Promise(
        function (resolve, reject) {
          if (this.files.length > 0) {
            const fileData = new FormData()
            const fileArr = []
            this.files.forEach((i) => {
              if (i.key == null || i.key === undefined) {
                // Blob이 아닌 File 타입만 저장가능
                fileArr.push(i)
                fileData.append('file', i)
              }
            })
            if (fileArr.length > 0) {
              fileData.append('updateKey', this.updateKey)
              fileData.append('isMsds', 'Y')
              this.$axios
                .post('/api/v1/common/file', fileData)
                .then((res) => {
                  resolve(res.data)
                })
                .catch((e) => {
                  console.warn(e) // eslint-disable-line no-console
                  resolve(undefined)
                })
            } else {
              resolve(this.updateKey)
            }
          } else {
            resolve(undefined)
          }
        }.bind(this)
      )
    },
  },
}
</script>
