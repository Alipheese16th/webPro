import { Doughnut } from 'vue-chartjs'
import ChartDataLabels from 'chartjs-plugin-datalabels'

export default {
  extends: Doughnut,
  plugins: [ChartDataLabels],
  props: ['data', 'options'],
  mounted() {
    this.renderChart(this.data, this.options)
  },
  methods: {
    update() {
      this.$data._chart.update()
    },
  },
}
