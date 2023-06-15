<template>
  <div class="container_homeui">
    <div class="container">
      <pie-chart ref="chart1" :data="chartData" :height="200"></pie-chart>
      <BarChart ref="chart2" :chart-data="chartData2" :options="chartOptions2" :height="200" />
    </div>
  </div>
</template>

<script>
import PieChart from '~/plugins/PieChart.js'
import BarChart from '~/plugins/BarChart.js'

export default {
  meta: {
    title: '안전/보건 Main',
  },
  components: {
    PieChart,
    BarChart,
  },
  data() {
    return {
      schData: {},
      chartOptions: {
        hoverBorderWidth: 20,
      },
      chartData: {
        hoverBackgroundColor: 'red',
        hoverBorderWidth: 10,
        labels: [],
        datasets: [
          {
            label: 'Data One',
            // backgroundColor: ['#41B883', '#E46651', '#00D8FF'],
            backgroundColor: [],
            data: [],
          },
        ],
      },
      chartData2: {
        labels: [],
        datasets: [
          {
            type: 'line',
            label: '본인',
            data: [],
            borderColor: '#eb3989',
            // backgroundColor: '#eb3989',
            fill: false,
            yAxisID: 'mycnt',
          },
          {
            type: 'bar',
            barThickness: 24,
            label: '총',
            data: [],
            // backgroundColor: '#3a22bd',
            yAxisID: 'totcnt',
          },
        ],
      },
      chartOptions2: {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          align: 'end',
        },
        scales: {
          yAxes: [
            {
              id: 'totcnt',
              ticks: {
                beginAtZero: true,
              },
              scaleLabel: {
                display: true,
                labelString: '총건수',
              },
            },
            {
              id: 'mycnt',
              position: 'right',
              ticks: {
                beginAtZero: true,
              },
              scaleLabel: {
                display: true,
                labelString: '내건수',
              },
            },
          ],
          xAxes: [
            {
              gridLines: {
                offset: false,
                offsetGridLines: false,
              },
            },
          ],
        },
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
    this.select()
  },
  methods: {
    getRandomColor() {
      const letters = '0123456789ABCDEF'.split('')
      let color = '#'
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)]
      }
      return color
    },
    async select() {
      const data = await this.$axios.$get(`/api/v1/she/cstprms/dash-test`, { params: this.schData })

      if (data.length > 0) {
        for (let i = 0; i < data.length; i++) {
          const colorVal = this.getRandomColor()
          console.log(colorVal) // eslint-disable-line no-console
          this.chartData.datasets[0].backgroundColor.push(colorVal)
          const pushVal = data[i].con_req_prst_nm
          this.chartData.labels.push(pushVal)
          this.chartData2.labels.push(pushVal)
          this.chartData.datasets[0].data.push(data[i].cnt)
          this.chartData2.datasets[0].data.push(data[i].my_cnt)
          this.chartData2.datasets[1].data.push(data[i].cnt)
        }
        this.$refs.chart1.update()
        this.$refs.chart2.update()
        console.log(this.chartData.labels) // eslint-disable-line no-console
      }
    },
  },
}
</script>
