import { Doughnut } from 'vue-chartjs'
import ChartDataLabels from 'chartjs-plugin-datalabels'

export default {
  extends: Doughnut,
  props: ['data', 'options'],
  plugins: [ChartDataLabels],
  mounted() {
    this.renderChart(this.data, {
      plugins: {
        datalabels: {
          color: 'white',
          formatter(value, context) {
            return value > 0 ? Math.round(value) + '%' : ''
          },
        },
      },
    })
  },
  methods: {
    update() {
      this.$data._chart.update()
    },
  },
}
