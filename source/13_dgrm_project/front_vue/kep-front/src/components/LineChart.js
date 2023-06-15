import { Line } from 'vue-chartjs'
import ChartDataLabels from 'chartjs-plugin-datalabels'

export default {
  extends: Line,
  plugins: [ChartDataLabels],
  props: ['data', 'options'],
  mounted() {
    const totalizer = {
      id: 'totalizer',

      beforeUpdate: (chart) => {
        const totals = []
        let utmost = 0

        chart.data.datasets.forEach((dataset, datasetIndex) => {
          if (chart.isDatasetVisible(datasetIndex)) {
            utmost = datasetIndex
            dataset.data.forEach((value, index) => {
              totals[index] = (totals[index] || 0) + value
            })
          }
        })

        chart.$totalizer = {
          totals,
          utmost,
        }

        const maxaxes = Math.max(...totals)
        chart.options.scales.yAxes[0].ticks.max = maxaxes % 2 ? maxaxes + 3 : maxaxes + 2
      },
    }

    this.addPlugin(totalizer)

    this.renderChart(this.data, this.options)
  },
  methods: {
    update() {
      this.$data._chart.update()
    },
  },
}
