import { Bar } from 'vue-chartjs'
import ChartDataLabels from 'chartjs-plugin-datalabels'

export default {
  extends: Bar,
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

        const max = Math.max(...totals)
        // const maxaxes = max > 10 ? Math.ceil((max + 5) * 0.1) * 10 : max + 1
        const maxaxes = max > 10 ? max + Math.round(max / 10) : max + 1
        chart.options.scales.yAxes[0].ticks.max = maxaxes
      },
    }

    this.addPlugin(totalizer)

    this.options.plugins.datalabels = {
      align: 'end',
      anchor: 'end',
      color: (ctx) => {
        const max = Math.max(...ctx.chart.$totalizer.totals)
        const min = Math.min(...ctx.chart.$totalizer.totals)
        return ctx.chart.$totalizer.totals[ctx.dataIndex] === max ? '#cc3636' : ctx.chart.$totalizer.totals[ctx.dataIndex] === min ? '#3672cc' : '#222222'
      },
      font: { size: 13, weight: 'bold' },
      formatter: (value, ctx) => {
        const total = ctx.chart.$totalizer.totals[ctx.dataIndex]
        return total
      },
      offset: 1,
    }

    this.renderChart(this.data, this.options)
  },
  methods: {
    update() {
      this.$data._chart.update()
    },
  },
}
