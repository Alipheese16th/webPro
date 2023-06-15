import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins
export default {
  name: 'BarChart',
  extends: Bar,
  mixins: [reactiveProp],
  props: ['chartData', 'options'],
  mounted() {
    this.addPlugin({
      id: 'my-plugin',
    })
    this.renderChart(this.chartData, this.options)
  },
  methods: {
    update() {
      this.$data._chart.update()
    },
  },
}
