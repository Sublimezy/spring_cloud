<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交通方式，如公交、出租车、租车等" prop="mode">
        <el-input
          v-model="queryParams.mode"
          placeholder="请输入交通方式，如公交、出租车、租车等"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出发时间" prop="departureTime">
        <el-date-picker clearable
                        v-model="queryParams.departureTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择出发时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到达时间" prop="arrivalTime">
        <el-date-picker clearable
                        v-model="queryParams.arrivalTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择到达时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录创建时间" prop="createdAt">
        <el-date-picker clearable
                        v-model="queryParams.createdAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择记录创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录更新时间" prop="updatedAt">
        <el-date-picker clearable
                        v-model="queryParams.updatedAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择记录更新时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="司机id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入司机id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="司机名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入司机名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="司机图片" prop="userImages">
        <el-input
          v-model="queryParams.userImages"
          placeholder="请输入司机图片"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:traffic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:traffic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:traffic:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:traffic:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="trafficList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="交通ID" align="center" prop="id" />
      <el-table-column label="交通方式，如公交、出租车、租车等" align="center" prop="mode" />
      <el-table-column label="交通路线描述" align="center" prop="route" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="出发时间" align="center" prop="departureTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.departureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到达时间" align="center" prop="arrivalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录创建时间" align="center" prop="createdAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录更新时间" align="center" prop="updatedAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatedAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="司机id" align="center" prop="userId" />
      <el-table-column label="司机名称" align="center" prop="userName" />
      <el-table-column label="司机图片" align="center" prop="userImages" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:traffic:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:traffic:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改交通信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="交通方式，如公交、出租车、租车等" prop="mode">
          <el-input v-model="form.mode" placeholder="请输入交通方式，如公交、出租车、租车等" />
        </el-form-item>
        <el-form-item label="交通路线描述" prop="route">
          <el-input v-model="form.route" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="出发时间" prop="departureTime">
          <el-date-picker clearable
                          v-model="form.departureTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择出发时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="到达时间" prop="arrivalTime">
          <el-date-picker clearable
                          v-model="form.arrivalTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择到达时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="记录创建时间" prop="createdAt">
          <el-date-picker clearable
                          v-model="form.createdAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择记录创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="记录更新时间" prop="updatedAt">
          <el-date-picker clearable
                          v-model="form.updatedAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择记录更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="司机id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入司机id" />
        </el-form-item>
        <el-form-item label="司机名称" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入司机名称" />
        </el-form-item>
        <el-form-item label="司机图片" prop="userImages">
          <el-input v-model="form.userImages" placeholder="请输入司机图片" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTraffic, getTraffic, delTraffic, addTraffic, updateTraffic } from "@/api/data/traffic/traffic";

export default {
  name: "Traffic",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 交通信息表格数据
      trafficList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mode: null,
        route: null,
        price: null,
        departureTime: null,
        arrivalTime: null,
        createdAt: null,
        updatedAt: null,
        userId: null,
        userName: null,
        userImages: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mode: [
          { required: true, message: "交通方式，如公交、出租车、租车等不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询交通信息列表 */
    getList() {
      this.loading = true;
      listTraffic(this.queryParams).then(response => {
        this.trafficList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        mode: null,
        route: null,
        price: null,
        departureTime: null,
        arrivalTime: null,
        createdAt: null,
        updatedAt: null,
        userId: null,
        userName: null,
        userImages: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加交通信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTraffic(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改交通信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTraffic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTraffic(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除交通信息编号为"' + ids + '"的数据项？').then(function() {
        return delTraffic(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/traffic/export', {
        ...this.queryParams
      }, `traffic_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
