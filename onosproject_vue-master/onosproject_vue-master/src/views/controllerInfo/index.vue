<template>
  <div class="table-classic-wrapper">
    <Hints>
      <template slot="hintName">交换机表格</template>
      <template slot="hintInfo">
        <p>显示网络中的控制器信息</p>
      </template>
    </Hints>
    <el-table
      v-loading="listLoading"
      :data="controllerList"
      tooltip-effect="dark"
      style="width: 100%"
      size="medium"
    >
        <el-table-column prop="name" label="控制器名称" align="center" sortable/>
      <el-table-column prop="ip" label="控制器ip地址" align="center"/>
        <el-table-column prop="running" label="是否在线" align="center">
          <template slot-scope="scope">
            <i v-if="scope.row.running === true">是</i>
            <i v-else>否</i>
          </template>
        </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getControllerList } from '@/api/networkInfo'
import Hints from '@/components/Hints'
import * as echarts from 'echarts'
import 'default-passive-events'

export default {
  components: { Hints },
  watch: {
    // echarts图表自适应浏览器大小变化
    '$store.state.app.sidebar.opened'() {
      setTimeout(res => {
        const myCharts1 = echarts.init(document.getElementById('pkIn'))
        const myCharts2 = echarts.init(document.getElementById('pkOut'))
        myCharts1.resize()
        myCharts2.resize()
      }, 300)
    }
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      listLoading: false,
      controllerList: []
    }
  },
  created() {
    this.fetchControllers()
  },
  methods: {
    fetchControllers() {
      this.listLoading = true
      getControllerList().then(response => {
        if (response.data.code === 'OK') {
          this.controllerList = response.data.data
          this.listLoading = false
        }
      })
    }
  }
}
</script>
<style lang="less">
.table-classic-wrapper {
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

