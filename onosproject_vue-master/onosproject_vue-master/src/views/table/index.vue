<template>
  <div class="table-classic-wrapper">
    <Hints>
      <template slot="hintName">交换机表格</template>
      <template slot="hintInfo">
        <p>显示网络中的交换机数量</p>
      </template>
    </Hints>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      size="medium"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="60" />
      <el-table-column prop="id" label="编号" align="center" width="120" sortable />
      <el-table-column prop="name" label="姓名" align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>手机: {{ scope.row.phone }}</p>
            <p>爱好: {{ scope.row.hobby }}</p>
            <div slot="reference">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="gender" label="性别" align="center">
      </el-table-column>
      <el-table-column prop="phone" label="手机" align="center" />
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button size="mini" :disabled="scope.row.forbid" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
// import { getList } from '@/api/table'

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
      list: null,
      listLoading: false,
      // 多选数据暂存数组
      multipleSelection: [],
      tableData: [
        {
          id: '001',
          name: 'pxj',
          gender: '男',
          phone: '12345678910'
        }
      ]
    }
  },
  created() {
    // this.fetchData()
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
    }
    /* fetchData() {
      this.listLoading = true
      getList().then(response => {
        this.list = response.data.items
        this.listLoading = false
      })
    } */
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
