<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input
          v-model="queryParams.orderNumber"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item v-if="userType===false"  label="店铺名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入店铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>



      <el-form-item label="活动名称" prop="eventName">
        <el-input
          v-model="queryParams.eventName"
          placeholder="请输入活动名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="用户账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>






      <el-form-item label="支付方式" prop="paymentMethod">
        <el-input
          v-model="queryParams.paymentMethod"
          placeholder="请输入支付方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="支付时间" prop="paymentTime">
        <el-date-picker clearable
                        v-model="queryParams.paymentTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择支付时间">
        </el-date-picker>
      </el-form-item>


      <el-form-item label="发货时间" prop="shippingTime">
        <el-date-picker clearable
                        v-model="queryParams.shippingTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择发货时间">
        </el-date-picker>
      </el-form-item>



      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">

      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderNumber" />
      <el-table-column v-if="userType===false" label="店铺ID" align="center" prop="shopId" />
      <el-table-column v-if="userType===false" label="店铺名称" align="center" prop="name" />
      <el-table-column v-if="userType===false" label="店铺活动ID" align="center" prop="shopEventId" />
      <el-table-column label="活动名称" align="center" prop="eventName" />
      <el-table-column label="商品图" align="center" prop="remark" >
        <template slot-scope="scope">
          <el-avatar :src="scope.row.remark"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column v-if="userType===false" label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户账号" align="center" prop="userName" />
      <el-table-column label="单价" align="center" prop="amount" />
      <el-table-column label="订单总金额" align="center" prop="totalAmount" />
      <el-table-column label="订单项数量" align="center" prop="itemCount" />
      <el-table-column label="优惠金额" align="center" prop="discountAmount" />
      <el-table-column label="优惠券代码" align="center" prop="couponCode" />
      <el-table-column label="运费" align="center" prop="shippingFee" />
      <el-table-column label="订单状态" align="center" prop="status" />
      <el-table-column label="支付方式" align="center" prop="paymentMethod" />
      <el-table-column label="支付时间" align="center" prop="paymentTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发货时间" align="center" prop="shippingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shippingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="配送地址" align="center" prop="shippingAddress" />
      <el-table-column label="用户备注" align="center" prop="userNote" />
      <el-table-column label="订单类型" align="center" prop="orderType" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改用户订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="店铺ID" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入店铺ID" />
        </el-form-item>
        <el-form-item label="店铺名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="店铺活动ID" prop="shopEventId">
          <el-input v-model="form.shopEventId" placeholder="请输入店铺活动ID" />
        </el-form-item>
        <el-form-item label="活动名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户账号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户账号" />
        </el-form-item>
        <el-form-item label="单价" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="订单总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入订单总金额" />
        </el-form-item>
        <el-form-item label="订单项数量" prop="itemCount">
          <el-input v-model="form.itemCount" placeholder="请输入订单项数量" />
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountAmount">
          <el-input v-model="form.discountAmount" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="优惠券代码" prop="couponCode">
          <el-input v-model="form.couponCode" placeholder="请输入优惠券代码" />
        </el-form-item>
        <el-form-item label="运费" prop="shippingFee">
          <el-input v-model="form.shippingFee" placeholder="请输入运费" />
        </el-form-item>
        <el-form-item label="支付方式" prop="paymentMethod">
          <el-input v-model="form.paymentMethod" placeholder="请输入支付方式" />
        </el-form-item>
        <el-form-item label="支付时间" prop="paymentTime">
          <el-date-picker clearable
                          v-model="form.paymentTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择支付时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="发货时间" prop="shippingTime">
          <el-date-picker clearable
                          v-model="form.shippingTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择发货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="配送地址" prop="shippingAddress">
          <el-input v-model="form.shippingAddress" placeholder="请输入配送地址" />
        </el-form-item>
        <el-form-item label="用户备注" prop="userNote">
          <el-input v-model="form.userNote" placeholder="请输入用户备注" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/order/order";
import Cookies from "js-cookie";
import store from "@/store";

export default {
  name: "Order",
  data() {
    return {
      userType: null,
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
      // 用户订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        shopId: null,
        name: null,
        shopEventId: null,
        eventName: null,
        userId: null,
        userName: null,
        amount: null,
        totalAmount: null,
        itemCount: null,
        discountAmount: null,
        couponCode: null,
        shippingFee: null,
        status: null,
        paymentMethod: null,
        paymentTime: null,
        shippingTime: null,
        shippingAddress: null,
        userNote: null,
        orderType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.userType = Cookies.get('source') === 'seller-user'
    this.getList();
  },
  methods: {
    /** 查询用户订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.shopId=store.getters.shopId
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orderNumber: null,
        shopId: null,
        name: null,
        shopEventId: null,
        eventName: null,
        userId: null,
        userName: null,
        amount: null,
        totalAmount: null,
        itemCount: null,
        discountAmount: null,
        couponCode: null,
        shippingFee: null,
        status: null,
        paymentMethod: null,
        paymentTime: null,
        shippingTime: null,
        shippingAddress: null,
        userNote: null,
        orderType: null,
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
      this.title = "添加用户订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
