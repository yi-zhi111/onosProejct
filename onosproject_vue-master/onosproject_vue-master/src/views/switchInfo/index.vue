<template>
  <div class="table-classic-wrapper">
    <Hints>
      <template slot="hintName">交换机表格</template>
      <template slot="hintInfo">
        <p>显示网络中的交换机信息</p>
      </template>
    </Hints>
    <el-table
      v-loading="listLoading"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      size="medium"
    >
      <el-table-column prop="id" label="交换机ID" align="center" width="200" sortable/>
      <el-table-column prop="switchType" label="交换机类型" align="center"/>
      <el-table-column prop="protocol" label="协议" align="center" width="200"/>
      <el-table-column prop="available" label="是否活跃" align="center">
        <template slot-scope="scope">
          <i v-if="scope.row.available === true">是</i>
          <i v-else>否</i>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import { getSwitchList } from '@/api/networkInfo'
import Hints from '@/components/Hints'

export default {
  components: { Hints },
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
      tableData: []
    }
  },
  created() {
    setInterval(() => {
      this.fetchData()
    }, 1000)
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getSwitchList().then(response => {
        if (response.data.code === 'OK') {
          this.tableData = response.data.data
          this.listLoading = false
        }
      })
    }
  }
}
</script>
<style lang="less">
.table-classic-wrapper {
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
