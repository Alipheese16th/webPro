<script>
export default {
  name: 'VueUploadImages',
  /* eslint-disable vue/require-default-prop */
  props: {
    max: Number,
    uploadMsg: String,
    maxError: String,
    fileError: String,
  }, // vue component name
  data() {
    return {
      error: '',
      fileIds: [],
      files: [],
      dropped: 0,
      Imgs: [],
      delete: true,
      updateKey: '0',
      isEdit: true,
    }
  },
  methods: {
    dragOver() {
      this.dropped = 2
    },
    dragLeave() {},
    drop(e) {
      let status = true
      const files = Array.from(e.dataTransfer.files)
      if (e && files) {
        files.forEach((file) => {
          // eslint-disable-next-line prettier/prettier
          if (file.type.startsWith("image") === false) status = false
        })
        if (status === true) {
          if (this.$props.max && files.length + this.files.length > this.$props.max) {
            this.$toast.show('Maximum files is ' + this.$props.max, { className: 'toast_warn' })
            this.error = this.$props.maxError ? this.$props.maxError : `Maximum files is` + this.$props.max
          } else {
            this.files.push(...files)
            this.previewImgs()
          }
        } else {
          this.$toast.show('Unsupported file type ', { className: 'toast_warn' })
          this.error = this.$props.fileError ? this.$props.fileError : `Unsupported file type`
        }
      }
      this.dropped = 0
    },
    append() {
      this.$refs.uploadInput.click()
    },
    readAsDataURL(file) {
      return new Promise(function (resolve, reject) {
        const fr = new FileReader()
        fr.onload = function () {
          resolve({ key: file.key, name: file.name, dataUrl: fr.result })
        }
        fr.onerror = function () {
          reject(fr)
        }
        fr.readAsDataURL(file)
      })
    },
    async deleteImg(index, key) {
      if (key !== undefined) {
        const res = await this.$confirm('기존 첨부파일을 삭제하시겠습니까?')
        if (!res) {
          return
        }
        if (this.delete) {
          this.$axios
            .delete(`/api/v1/common/file/${key}`)
            .then((res) => {
              this.$toast.show('삭제하였습니다!')
            })
            .catch((e) => {
              console.warn(e) // eslint-disable-line no-console
              this.$toast.show('오류발생! 삭제하지 못하였습니다!', { className: 'toast_error' })
            })
        } else {
          this.$toast.show('삭제하였습니다!')
        }
      }
      this.Imgs.splice(index, 1)
      this.files.splice(index, 1)
      this.$emit('changed', this.files)
      this.$emit('changeFile', 0)
      this.$refs.uploadInput.value = null
    },
    downloadImg(index, key, name) {
      if (key !== undefined) {
        console.log('download...', key) // eslint-disable-line no-console
        this.$axios
          .$get(`/api/v1/common/file-down/${key}`, {
            responseType: 'arraybuffer',
            headers: {
              'Content-Type': 'application/json',
            },
          })
          .then((res) => {
            // IE 10+
            if (window.navigator.msSaveOrOpenBlob) {
              window.navigator.msSaveOrOpenBlob(new Blob([res]), name)
            } else {
              // not IE
              const link = document.createElement('a')
              link.href = window.URL.createObjectURL(new Blob([res]))
              link.target = '_self'
              link.download = name
              link.click()
            }
          })
      }
    },
    getAttachKey() {
      return this.updateKey
    },
    previewImgs(event) {
      if (this.$props.max && event && event.currentTarget.files.length + this.files.length > this.$props.max) {
        this.$toast.show('Maximum files is ' + this.$props.max, { className: 'toast_warn' })
        this.error = this.$props.maxError ? this.$props.maxError : `Maximum files is` + ' ' + this.$props.max
        return
      }
      if (this.dropped === 0) this.files.push(...event.currentTarget.files)
      this.error = ''
      this.$emit('changed', this.files)
      const readers = []
      if (!this.files.length) return
      for (let i = 0; i < this.files.length; i++) {
        // 현재 파일 배열 내부의 데이터 만큼 URL 값을 가져온다.
        const DataUrl = this.readAsDataURL(this.files[i])
        readers.push(DataUrl)
      }
      Promise.all(readers).then((values) => {
        this.Imgs = values
      })
    },
    reset() {
      this.$refs.uploadInput.value = null
      this.Imgs = []
      this.files = []
      this.fileIds = []
      this.dropped = 0
      this.delete = true
      this.updateKey = '0'
      this.$emit('changed', this.files)
    },
    setImages(attachId) {
      this.$axios.$get(`/api/v1/common/file/` + attachId).then((data) => {
        if (data.length > 0) {
          data.forEach((e) => {
            this.setImage(e)
          })
        }
      })
    },
    isOldFile() {
      // 파일은 존재하지만 key가 존재하지 않을 경우 => 신규 파일처리
      if (this.files.length > 0) {
        let cnt = 0
        this.files.forEach((i) => {
          if (i.key == null || i.key === undefined) {
            cnt++
          }
        })
        if (this.files.length === cnt) {
          // 모든 파일이 일반 파일일 경우
          return false
        }
      } else {
        return false
      }
      return true
    },
    setKey(attachId) {
      this.updateKey = attachId
    },
    setImage(data) {
      this.$axios
        .$get(`/api/v1/common/image/` + data.key, {
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        .then((res) => {
          const blob = new Blob([res])
          blob.key = data.key
          blob.name = data.name
          this.files.push(blob)
          this.error = ''
          this.$emit('changed', this.files)
          const readers = []
          if (!this.files.length) return
          for (let i = 0; i < this.files.length; i++) {
            // 현재 파일 배열 내부의 데이터 만큼 URL 값을 가져온다.
            const DataUrl = this.readAsDataURL(this.files[i])
            readers.push(DataUrl)
          }
          Promise.all(readers).then((values) => {
            this.Imgs = values
          })
        })
    },
    setEdit(isEdit) {
      this.isEdit = isEdit
    },
  },
}
</script>

<template>
  <div
    class="container"
    style="display: flex; justify-content: center; align-items: center"
    :eager="true"
    @dragover.prevent="dragOver"
    @dragleave.prevent="dragLeave"
    @drop.prevent="drop($event)"
  >
    <!-- <div v-show="dropped == 2" class="drop"></div> -->
    <!-- Error Message -->
    <!-- <div v-show="error" class="error">
      {{ error }}
    </div> -->

    <!-- To inform user how to upload image -->
    <div v-show="Imgs.length == 0" class="beforeUpload">
      <input v-show="isEdit" ref="uploadInput" type="file" style="z-index: 1" accept="image/*" multiple @change="previewImgs" />
      <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
        <title>Upload Image</title>
        <g id="Upload_Image" data-name="Upload Image">
          <g id="_Group_" data-name="&lt;Group&gt;">
            <g id="_Group_2" data-name="&lt;Group&gt;">
              <g id="_Group_3" data-name="&lt;Group&gt;">
                <circle id="_Path_" data-name="&lt;Path&gt;" cx="18.5" cy="16.5" r="5" style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round" />
              </g>
              <polyline
                id="_Path_2"
                data-name="&lt;Path&gt;"
                points="16.5 15.5 18.5 13.5 20.5 15.5"
                style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round"
              />
              <line
                id="_Path_3"
                data-name="&lt;Path&gt;"
                x1="18.5"
                y1="13.5"
                x2="18.5"
                y2="19.5"
                style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round"
              />
            </g>
            <g id="_Group_4" data-name="&lt;Group&gt;">
              <polyline
                id="_Path_4"
                data-name="&lt;Path&gt;"
                points="0.6 15.42 6 10.02 8.98 13"
                style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round"
              />
              <polyline
                id="_Path_5"
                data-name="&lt;Path&gt;"
                points="17.16 11.68 12.5 7.02 7.77 11.79"
                style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round"
              />
              <circle id="_Path_6" data-name="&lt;Path&gt;" cx="8" cy="6.02" r="1.5" style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round" />
              <path
                id="_Path_7"
                data-name="&lt;Path&gt;"
                d="M19.5,11.6V4A1.5,1.5,0,0,0,18,2.5H2A1.5,1.5,0,0,0,.5,4V15A1.5,1.5,0,0,0,2,16.5H13.5"
                style="fill: none; stroke: #303c42; stroke-linecap: round; stroke-linejoin: round"
              />
            </g>
          </g>
        </g>
      </svg>

      <p class="mainMessage">
        {{ uploadMsg ? uploadMsg : 'Click to upload or drop your images here' }}
      </p>
    </div>
    <div v-show="Imgs.length > 0" class="imgsPreview">
      <div v-for="(img, i) in Imgs" :key="i" class="imageHolder">
        <img :src="img.dataUrl" @click="downloadImg(--i, img.key, img.name)" />
        <span v-show="isEdit" class="delete" style="color: white" @click="deleteImg(--i, img.key)">
          <svg class="icon" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
            />
          </svg>
        </span>
        <div v-show="isEdit" v-if="++i == Imgs.length" class="plus" @click="append">+</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  background: #f7fafc;
  border: 0.5px solid #a3a8b1;
  border-radius: 10px;
  padding: 30px;
  position: relative;
}
.drop {
  width: 100%;
  height: 100%;
  top: 0;
  border-radius: 10px;
  position: absolute;
  background-color: #f4f6ff;
  left: 0;
  border: 3px dashed #a3a8b1;
}
.error {
  text-align: center;
  color: white;
  font-size: 15px;
}
.beforeUpload {
  position: relative;
  text-align: center;
}
.beforeUpload input {
  width: 100%;
  margin: auto;
  height: 100%;
  opacity: 0;
  position: absolute;
  background: red;
  display: block;
}
.beforeUpload input:hover {
  cursor: pointer;
}
.beforeUpload .icon {
  width: 150px;
  margin: auto;
  display: block;
}
.imgsPreview .imageHolder {
  width: 300px;
  height: 400px;
  background: #fff;
  position: relative;
  border-radius: 10px;
  margin: 5px 5px;
  display: inline-block;
}
.imgsPreview .imageHolder img {
  aspect-ratio: auto;
  object-fit: cover;
  max-width: 100%;
  max-height: 100%;
  width: auto;
  height: auto;
}
.imgsPreview .imageHolder .delete {
  position: absolute;
  top: 6px;
  right: 7px;
  width: 30px;
  height: 30px;
  color: #fff;
  background: red;
  border-radius: 50%;
}
.imgsPreview .imageHolder .delete:hover {
  cursor: pointer;
}
.imgsPreview .imageHolder .delete .icon {
  width: 66%;
  height: 66%;
  display: block;
  margin: 4px auto;
}
.imgsPreview .imageHolder .plus {
  color: #2d3748;
  background: #f7fafc;
  padding-top: 4px;
  border-radius: 50%;
  font-size: 21pt;
  height: 30px;
  width: 30px;
  text-align: center;
  border: 1px dashed;
  line-height: 23px;
  position: absolute;
  right: 42px;
  bottom: 91%;
}
.plus:hover {
  cursor: pointer;
}
.clearButton {
  color: #2d3748;
  position: absolute;
  top: 7px;
  right: 7px;
  background: none;
  border: none;
  cursor: pointer;
}
</style>
