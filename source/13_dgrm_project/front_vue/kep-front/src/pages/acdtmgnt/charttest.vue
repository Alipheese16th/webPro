<template>
  <v-container>
    <v-row style="height: 550px">
      <v-col cols="3">
        <div><bar-chart ref="barchart1" :data="barData" :options="barOptions" :height="300" :width="400"></bar-chart></div>
      </v-col>
      <v-col cols="3">
        <div><pie-chart ref="piechart1" :data="pieData" :options="pieOptions" :height="300" :width="400"></pie-chart></div>
      </v-col>
      <v-col cols="6">
        <div><line-chart ref="linechart1" :data="lineData" :options="lineOptions" :height="300" :width="800"></line-chart></div>
        <div><v-btn @click="plus">+</v-btn></div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import BarChart from '~/components/BarChart.js'
import PieChart from '~/components/PieChart.js'
import LineChart from '~/components/LineChart.js'

export default {
  meta: {
    title: 'Chart Test',
  },
  components: {
    BarChart,
    PieChart,
    LineChart,
  },
  data() {
    return {
      barData: {
        labels: ['January', 'February', 'March'],
        datasets: [
          {
            label: 'Data One',
            backgroundColor: '#348acb',
            data: [40, 20, 12],
          },
          {
            label: 'Data Two',
            backgroundColor: '#81c7a7',
            data: [22, 22, 15],
          },
        ],
      },
      barOptions: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          display: false,
        },
        scales: {
          xAxes: [
            {
              stacked: true,
              gridLines: {
                display: true,
                lineWidth: 0,
              },
            },
          ],
          yAxes: [
            {
              stacked: true,
              display: false,
            },
          ],
        },
      },
      lineData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'Data One',
            fill: false,
            tension: 0.3,
            borderColor: '#348acb',
            data: [2, 0, 5, 9, 3, 9, 8],
          },
        ],
      },
      lineOptions: {
        legend: {
          display: false,
        },
        responsive: false,
        plugins: {
          datalabels: {
            align: 'end',
            anchor: 'end',
            font: { size: 15, weight: 'bold' },
            color: (ctx) => {
              const arr = [...ctx.dataset.data]
              const max = Math.max(...arr)
              arr.forEach((e, i) => {
                if (e === max) arr[i] = 0
              })
              const second = Math.max(...arr)
              return ctx.dataset.data[ctx.dataIndex] === max || ctx.dataset.data[ctx.dataIndex] === second ? 'red' : 'black'
            },
            formatter(value, context) {
              return value > 0 ? value : ''
            },
          },
        },
      },
      pieData: {
        labels: ['Red', 'Blue', 'Yellow'],
        datasets: [
          {
            data: [300, 50, 100],
            backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)'],
            hoverOffset: 9,
          },
        ],
      },
      pieOptions: {
        legend: {
          display: false,
        },
        plugins: {
          datalabels: {
            // anchor: 'end',
            align: 'center',
            rotation: 0,
            // offset: 66,
            font: {
              size: 12,
            },
            color: 'white',
            formatter(value, context) {
              // console.log(context)
              const nm = context.chart.config.data.labels[context.dataIndex]
              return nm + ' ' + value + '개'
            },
          },
        },
      },
    }
  },
  mounted() {
    // this.$refs.barchart1.update()
  },
  methods: {
    plus() {
      this.lineData.datasets[0].data[3] = this.lineData.datasets[0].data[3] + 10
      this.$refs.linechart1.update()
    },
  },
}
</script>
