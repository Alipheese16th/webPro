<template>
  <v-dialog v-model="dialog" :eager="true" persistent width="400px">
    <v-card class="container_pop" style="height: 300px !important">
      <v-toolbar flat class="mb-6">
        <!--월 선택-->
        <v-toolbar-title>{{ $t('LB00000715') }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <!--닫기-->
        <v-btn icon class="btn_pop_cls" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-toolbar>
      <div class="container_pop_section">
        <v-container style="padding: 0 30px 0 30px">
          <v-row>
            <v-col cols="12">
              <div class="container_sch" style="padding: 0 0 10px 0">
                <v-row>
                  <v-col cols="2">
                    <!--월-->
                    <div class="label_tit">{{ $t('LB00000555') }}</div>
                  </v-col>
                  <v-col cols="7">
                    <v-select
                      ref="month"
                      v-model="searchdatas.monthval"
                      label="SELECT"
                      item-text="cd_nm"
                      item-value="cd"
                      :items="codes.CO00000033"
                      outlined
                      single-line
                      :hide-details="true"
                      dense
                      height="30"
                    ></v-select>
                  </v-col>
                </v-row>
              </div>
              <div class="group_padd"></div>
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-card-actions class="pop_btn">
        <!--선택-->
        <v-btn color="primary" depressed width="120" height="40" @click="returnData()">{{ $t('LB00000106') }}</v-btn>
        <!--닫기-->
        <v-btn outlined width="120" height="40" @click="close()">{{ $t('LB00000109') }}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
export default {
  components: {},
  props: {
    mkey: {
      type: String,
      default: 'bsendgrid',
    },
  },
  data() {
    return {
      dialog: false,
      expanded: false,
      frmData: {},
      codeid: ['CO00000033'],
      codes: {
        CO00000033: [{ cd: '', cd_nm: '' }],
      },
      searchdatas: {
        monthval: '',
      },
    }
  },
  computed: {
    // $t() {
    //   return (cd) => {
    //     return this.$store.getters['mlang/getMultiLanguage'](cd)
    //   }
    // },
  },
  mounted() {
    this.$axios
      .$get(`/api/v1/comm/comm-cd`, { params: this.codeid })
      .then((res) => {
        this.codes = res
        this.codes.CO00000033.unshift({ cd: '', cd_nm: 'SELECT' })
      })
      .catch((err) => {
        console.log(err) // eslint-disable-line no-console
      })
  },
  methods: {
    open(frmData) {
      this.dialog = true
      // setTimeout(() => this.$refs.otsr_con_bsen_nm.$refs.input.focus(), 100)
    },
    close() {
      this.$emit('close', undefined)
      this.reset()
    },
    reset() {
      this.dialog = false
      this.frmData = {}
      this.searchdatas = {}
    },
    onClearClicked() {
      this.searchdatas.monthval = ''
    },
    async returnData() {
      const returnData = []
      await returnData.push(this.searchdatas)
      this.$emit('close', returnData)
      this.reset()
    },
  },
}
</script>
