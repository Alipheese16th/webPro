<template>
  <div v-if="edit" class="container_filelist" style="display: flex">
    <div style="border: 1px solid #dcdcdc; width: 100%; min-height: 32px">
      <template v-if="files.length">
        <v-data-table :headers="headers" :items="files" item-key="name" style="max-height: 110px; overflow-y: auto" hide-default-header hide-default-footer>
          <template v-slot:item="row">
            <tr style="height: 30px !important">
              <td style="line-break: anywhere">{{ row.item.name }}</td>
              <td width="40" style="text-align: right">{{ getFileSizeNm(row.item.size) }}</td>
              <td width="35">
                <v-btn
                  v-if="row.item.key"
                  text
                  class="btn_download"
                  height="30"
                  style="width: 35px !important; min-width: 35px !important"
                  @click.stop="download(row.item.key, row.item.name)"
                ></v-btn>
              </td>
              <td width="35">
                <v-btn text class="btn_file_del" height="30" style="width: 35px !important; min-width: 35px !important" @click="remove(row.item)"></v-btn>
              </td>
            </tr>
          </template>
        </v-data-table>
      </template>
    </div>
    <div style="width: 120px; height: 32px">
      <vue-upload ref="vueUpload" v-model="files" :input-id="inputId" :multiple="multiple" :drop="true" :drop-directory="true" @input="onDrop($event)">
        <v-btn class="btn_upload">{{ $t('LB00000552') }}</v-btn>
      </vue-upload>
    </div>
  </div>
  <div v-else class="container_filelist">
    <template>
      <div style="border: 1px solid #dcdcdc; min-height: 32px">
        <v-data-table :headers="headers" :items="files" item-key="key" style="max-height: 110px; overflow-y: auto" hide-default-header hide-default-footer>
          <template v-slot:item="row">
            <tr>
              <td style="line-break: anywhere">{{ row.item.name }}</td>
              <td width="40" style="text-align: right">{{ getFileSizeNm(row.item.size) }}</td>
              <td width="35">
                <v-btn text class="btn_download" height="30" style="width: 35px !important; min-width: 35px !important" @click.stop="download(row.item.key, row.item.name)"></v-btn>
              </td>
            </tr>
          </template>
        </v-data-table>
      </div>
      <div style="float: right">
        <v-btn v-if="files.length > 1" text color="primary" style="width: 100px !important" @click.stop="downloadAll()">
          {{ $t('LB00000553') }}
        </v-btn>
      </div>
    </template>
  </div>
</template>

<script>
import VueUpload from 'vue-upload-component'

export default {
  components: {
    VueUpload,
  },
  props: {
    inputId: {
      type: [String],
      default() {
        return 'file'
      },
    },
    multiple: {
      type: [Boolean],
      default() {
        return false
      },
    },
  },
  data() {
    return {
      files: [],
      edit: true,
      delete: true,
      updateKey: '0',
      headers: [
        { text: 'name', value: 'name', width: '60%' },
        { text: 'size', value: 'size', width: '20%' },
        { text: 'down', value: 'down', width: '15%' },
        { text: 'delete', value: 'delete', width: '5%' },
      ],
      denyExt: ['.docx', '.doc', '.xlsx', '.xls', '.pptx', '.ppt', '.hwp', '.pdf', '.txt', '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tiff', '.zip', '.alz'],
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  methods: {
    getFileSizeNm(nFileSize) {
      let sFileSize = ''
      if (nFileSize < 1000000) {
        // 파일 사이즈가 1메가보다 작을때
        sFileSize = Math.round(nFileSize / 100) / 10 + ' Kb'
      } else {
        // 파일 사이즈가 1메가보다 클때
        sFileSize = Math.round(nFileSize / 100000) / 10 + ' Mb'
      }
      return sFileSize
    },
    getExtensionOfFilename(filename) {
      const _fileLen = filename.length

      const _lastDot = filename.lastIndexOf('.')

      const _fileExt = filename.substring(_lastDot, _fileLen).toLowerCase()

      return _fileExt
    },
    async onDrop(items) {
      // 파일 선택 직후 동작
      console.log(' upload files items : ', items) // eslint-disable-line no-console

      await this.uploadFileChk(items)

      this.$emit('onDrop')
      // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      if (items.length <= 0) {
        this.updateKey = '0'
      }
      console.log(' result updateKey1: ', this.updateKey) // eslint-disable-line no-console
      // 단건일 경우 파일을 업로드 했을때 업데이트 키를 초기화해서 새로 insert하게 한다.
      if (!this.multiple) {
        this.updateKey = '0'
      }
      console.log(' result updateKey2: ', this.updateKey) // eslint-disable-line no-console

      // if (this.files.length > 0) {
      //   console.log('upload files ondrop : ', this.files) // eslint-disable-line no-console
      //   const fileData = new FormData()
      //   const fileArr = []
      //   this.files.forEach((i) => {
      //     if (i.file) {
      //       fileArr.push(i)
      //       fileData.append('file', i.file)
      //     }
      //   })

      //   if (fileArr.length > 0) {
      //     fileData.append('updateKey', this.updateKey)
      //     this.$axios
      //       .post('/api/v1/common/file-check', fileData)
      //       .then((res) => {
      //         console.log(res) // eslint-disable-line no-console
      //         if (res.data !== 'SUCCESS') {
      //           this.$toast.show('업로드 할 수 없는 파일 확장자입니다.', { className: 'toast_error' })
      //           this.files.splice(
      //             this.files.findIndex((e) => e.name === item[item.length - 1].name),
      //             1
      //           )
      //           // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      //           if (this.files.length <= 0) {
      //             this.updateKey = '0'
      //           }
      //         } else {
      //           // 단건일 경우 파일을 업로드 했을때 업데이트 키를 초기화해서 새로 insert하게 한다.
      //           if (!this.multiple) {
      //             this.updateKey = '0'
      //           }
      //           this.$emit('onDrop')
      //         }
      //       })
      //       .catch((e) => {
      //         this.files.splice(
      //           this.files.findIndex((e) => e.name === item[item.length - 1].name),
      //           1
      //         )
      //         // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      //         if (this.files.length <= 0) {
      //           this.updateKey = '0'
      //         }
      //       })
      //   } else {
      //     this.files.splice(
      //       this.files.findIndex((e) => e.name === item[item.length - 1].name),
      //       1
      //     )
      //     // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      //     if (this.files.length <= 0) {
      //       this.updateKey = '0'
      //     }
      //   }
      // } else {
      //   this.files.splice(
      //     this.files.findIndex((e) => e.name === item[item.length - 1].name),
      //     1
      //   )
      //   // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      //   if (this.files.length <= 0) {
      //     this.updateKey = '0'
      //   }
      // }

      // if (item.length > 0) {
      //   const ext = this.getExtensionOfFilename(item[item.length - 1].name)
      //   if (!this.denyExt.includes(ext)) {
      //     this.$toast.show('업로드 할 수 없는 파일 확장자입니다.', { className: 'toast_error' })
      //     this.files.splice(
      //       this.files.findIndex((e) => e.name === item[item.length - 1].name),
      //       1
      //     )
      //     // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      //     if (this.files.length <= 0) {
      //       this.updateKey = '0'
      //     }
      //   } else {
      //     console.log(item) // eslint-disable-line no-console
      //     // 단건일 경우 파일을 업로드 했을때 업데이트 키를 초기화해서 새로 insert하게 한다.
      //     if (!this.multiple) {
      //       this.updateKey = '0'
      //     }
      //     this.$emit('onDrop')
      //   }
      // }
    },
    async uploadFileChk(fileItems) {
      let nMaxSizeOverCnt = 0
      let nExtDenyCnt = 0
      for (let i = fileItems.length - 1; i >= 0; i--) {
        console.log(' fileItems index: ', i) // eslint-disable-line no-console
        console.log(' fileItems[i]: ', fileItems[i]) // eslint-disable-line no-console
        console.log(' fileItems[i].file: ', fileItems[i].file) // eslint-disable-line no-console
        if (fileItems[i].file === undefined) {
          // 새로 추가한 파일이 아닌 경우 pass
          continue
        }

        if (fileItems[i].file.size > 500000000) {
          // 첨부 용량 체크 (500MB 한도)
          fileItems.splice(i, 1)
          nMaxSizeOverCnt++
          continue
        }

        // 파일확장자 검사
        const fileData = new FormData()
        fileData.append('file', fileItems[i].file)
        const res = await this.$axios.post('/api/v1/common/file-check', fileData)
        console.log(res) // eslint-disable-line no-console
        if (res.data !== 'SUCCESS') {
          fileItems.splice(i, 1)
          nExtDenyCnt++
        }
      }

      console.log(' result fileItems.length: ', fileItems.length) // eslint-disable-line no-console
      console.log(' result fileItems: ', fileItems) // eslint-disable-line no-console

      if (nMaxSizeOverCnt > 0) {
        this.$toast.show('업로드 가능한 최대 용량(500Mb)을 초과한 파일은 제외되었습니다.', { className: 'toast_warn' })
      }
      if (nExtDenyCnt > 0) {
        this.$toast.show('업로드 할 수 없는 확장자가 포함된 파일은 제외되었습니다.', { className: 'toast_warn' })
      }
    },
    uploadStart(isMsds = false) {
      return new Promise(
        function (resolve, reject) {
          if (this.edit) {
            if (this.files.length > 0) {
              console.log('upload files : ', this.files) // eslint-disable-line no-console
              const fileData = new FormData()
              const fileArr = []
              this.files.forEach((i) => {
                if (i.file) {
                  fileArr.push(i)
                  fileData.append('file', i.file)
                }
              })
              if (fileArr.length > 0) {
                fileData.append('updateKey', this.updateKey)
                if (isMsds) {
                  fileData.append('isMsds', 'Y')
                } else {
                  fileData.append('isMsds', 'N')
                }
                this.$axios
                  .post('/api/v1/common/file', fileData)
                  .then((res) => {
                    console.log(res) // eslint-disable-line no-console
                    this.reset()
                    resolve(res.data)
                  })
                  .catch((e) => {
                    console.warn(e) // eslint-disable-line no-console
                    this.reset()
                    resolve(undefined)
                  })
              } else {
                resolve(this.updateKey)
              }
            } else {
              resolve(undefined)
            }
          } else {
            resolve(this.updateKey)
          }
        }.bind(this)
      )
    },
    downloadAll() {
      this.files.forEach((i) => {
        this.download(i.key, i.name)
      })
    },
    download(key, fileName) {
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
            window.navigator.msSaveOrOpenBlob(new Blob([res]), fileName)
          } else {
            // not IE
            const link = document.createElement('a')
            link.href = window.URL.createObjectURL(new Blob([res]))
            link.target = '_self'
            link.download = fileName
            link.click()
          }
        })
    },
    async remove(item) {
      if (item.key) {
        // 삭제하시겠습니까?
        const res = await this.$confirm(this.$t('MS00000042'))
        if (!res) {
          return
        }
        if (this.delete) {
          this.$axios
            .delete(`/api/v1/common/file/${item.key}`)
            .then((res) => {
              // 삭제되었습니다.
              this.$toast.show(this.$t('MS00000041'))
            })
            .catch((e) => {
              console.warn(e) // eslint-disable-line no-console
              item.name = ''
              // 오류가 발생했습니다.
              this.$toast.show(this.$t('MS00000014'), { className: 'toast_error' })
            })
        } else {
          // 삭제되었습니다.
          this.$toast.show(this.$t('MS00000041'))
        }
      }
      this.files.splice(
        this.files.findIndex((e) => e.name === item.name),
        1
      )
      // 모든 파일이 삭제 되었을 경우 updatekey 초기화
      if (this.files.length <= 0) {
        this.updateKey = '0'
      }
      this.$emit('onDrop')
    },
    setFiles(files) {
      this.files = files
      this.updateKey = files[0].atfile_no
      console.log('updateKey : ', this.updateKey) // eslint-disable-line no-console
    },
    setEdit(edit) {
      this.edit = edit
    },
    setDelete(del) {
      this.delete = del
    },
    reset() {
      this.files = []
      this.updateKey = '0'
    },
  },
}
</script>
