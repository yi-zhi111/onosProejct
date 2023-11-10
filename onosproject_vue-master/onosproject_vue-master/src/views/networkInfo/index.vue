<template>
  <div class="networkInfo">
    <Hints>
      <template slot="hintName">交换机表格</template>
      <template slot="hintInfo">
        <p>显示网络中的控制器信息</p>
      </template>
    </Hints>
    <el-button type="primary" round @click="fetchPacketData">点击获取速率</el-button>
    <el-row>
      <el-col :span="12">
        <div id="pkIn"></div>
      </el-col>
      <el-col :span="12">
        <div id="pkOut"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getPkinRate, getPkoutRate } from '@/api/networkInfo'
import Hints from '@/components/Hints'
import * as echarts from 'echarts'
import 'default-passive-events'

export default {
  components: { Hints },
  data() {
    return {
    }
  },
  watch: {
    // echarts图表自适应浏览器大小变化
    '$store.state.app.sidebar.opened'() {
      setTimeout(res => {
        const myCharts1 = echarts.init(this.$refs.pkIn)
        const myCharts2 = echarts.init(this.$refs.pkOut)
        myCharts1.resize()
        myCharts2.resize()
      }, 300)
    }
  },
  methods: {
    fetchPkin() {
      const data1 = [0, 0, 0, 0, 0, 0, 0]
      const data2 = [0, 0, 0, 0, 0, 0, 0]
      const data3 = [0, 0, 0, 0, 0, 0, 0]
      const data4 = [0, 0, 0, 0, 0, 0, 0]
      const data5 = [0, 0, 0, 0, 0, 0, 0]
      const time = [0, 0, 0, 0, 0, 0, 0]
      const myChart = echarts.init(document.getElementById('pkIn'))
      const option = {
        title: {
          text: 'packet-in rate'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          right: '3%',
          data: ['ONOS1', 'ONOS2', 'ONOS3', 'ONOS4', 'ONOS5']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: time
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'ONOS1',
            type: 'line',
            data: data1
          },
          {
            name: 'ONOS2',
            type: 'line',
            data: data2
          },
          {
            name: 'ONOS3',
            type: 'line',
            data: data3
          },
          {
            name: 'ONOS4',
            type: 'line',
            data: data4
          },
          {
            name: 'ONOS5',
            type: 'line',
            data: data5
          }
        ]
      }
      setInterval(() => {
        getPkinRate().then(res => {
          const load = res.data.data
          data1.shift()
          data1.push(load.ONOS1)
          data2.shift()
          data2.push(load.ONOS2)
          data3.shift()
          data3.push(load.ONOS3)
          data4.shift()
          data4.push(load.ONOS4)
          data5.shift()
          data5.push(load.ONOS5)
          time.shift()
          time.push(load.time)
          myChart.setOption({
            series: [
              {
                name: 'ONOS1',
                type: 'line',
                data: data1
              },
              {
                name: 'ONOS2',
                type: 'line',
                data: data2
              },
              {
                name: 'ONOS3',
                type: 'line',
                data: data3
              },
              {
                name: 'ONOS4',
                type: 'line',
                data: data4
              },
              {
                name: 'ONOS5',
                type: 'line',
                data: data5
              }
            ],
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: time
            }
          })
        })
      }, 2000)
      // prettier-ignore
      option && myChart.setOption(option)
      setTimeout(() => {
        window.addEventListener('resize', () => {
          if (myChart) {
            myChart.resize()
          }
        })
      }, 300)
    },
    fetchPkout() {
      const data1 = [0, 0, 0, 0, 0, 0, 0]
      const data2 = [0, 0, 0, 0, 0, 0, 0]
      const data3 = [0, 0, 0, 0, 0, 0, 0]
      const data4 = [0, 0, 0, 0, 0, 0, 0]
      const data5 = [0, 0, 0, 0, 0, 0, 0]
      const time = [0, 0, 0, 0, 0, 0, 0]
      const myChart = echarts.init(document.getElementById('pkOut'))
      const option = {
        title: {
          text: 'packet-out rate'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          right: '3%',
          data: ['ONOS1', 'ONOS2', 'ONOS3', 'ONOS4', 'ONOS5']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: time
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'ONOS1',
            type: 'line',
            data: data1
          },
          {
            name: 'ONOS2',
            type: 'line',
            data: data2
          },
          {
            name: 'ONOS3',
            type: 'line',
            data: data3
          },
          {
            name: 'ONOS4',
            type: 'line',
            data: data4
          },
          {
            name: 'ONOS5',
            type: 'line',
            data: data5
          }
        ]
      }
      setInterval(() => {
        getPkoutRate().then(res => {
          const load = res.data.data
          data1.shift()
          data1.push(load.ONOS1)
          data2.shift()
          data2.push(load.ONOS2)
          data3.shift()
          data3.push(load.ONOS3)
          data4.shift()
          data4.push(load.ONOS4)
          data5.shift()
          data5.push(load.ONOS5)
          time.shift()
          time.push(load.time)
          myChart.setOption({
            series: [
              {
                name: 'ONOS1',
                type: 'line',
                data: data1
              },
              {
                name: 'ONOS2',
                type: 'line',
                data: data2
              },
              {
                name: 'ONOS3',
                type: 'line',
                data: data3
              },
              {
                name: 'ONOS4',
                type: 'line',
                data: data4
              },
              {
                name: 'ONOS5',
                type: 'line',
                data: data5
              }
            ],
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: time
            }
          })
        })
      }, 2000)
      // prettier-ignore
      option && myChart.setOption(option)
      setTimeout(() => {
        window.addEventListener('resize', () => {
          if (myChart) {
            myChart.resize()
          }
        })
      }, 300)
    },
    fetchPacketData() {
      this.fetchPkin()
      this.fetchPkout()
    }
  }
}
</script>
<style lang="less">
.networkInfo {
  #pkIn, #pkOut {
    margin-top: 5%;
    height: 500px;
  }

  .el-card {
    min-height: 656px;
  }

  .control-btns {
    margin-bottom: 20px;
  }

  .search-form {
    padding-top: 18px;
    margin-bottom: 15px;
    background-color: #f7f8fb;
  }

  .el-table thead {
    font-weight: 600;

    th {
      background-color: #f2f3f7;
    }
  }

  .dialog-form {
    .el-input {
      width: 380px;
    }

    .footer-item {
      margin-top: 50px;
      text-align: right;
    }
  }

  .upload-box,
  .export-data {
    width: 300px;
    margin: 0 auto 30px;
  }

  .upload-box {
    width: 156px;

    .files-upload {
      color: #20a0ff;
    }
  }

  .hints {
    font-size: 12px;
    color: #aaa;
    text-align: center;
  }
}
</style>

