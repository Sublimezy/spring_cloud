<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item v-if="userType===false" label="店铺名" prop="shopName">
        <el-input
          v-model="queryParams.shopName"
          placeholder="请输入店铺名"
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

      <el-form-item label="开始时间" prop="startDate">
        <el-date-picker clearable
                        v-model="queryParams.startDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="结束时间" prop="endDate">
        <el-date-picker clearable
                        v-model="queryParams.endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择结束时间">
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-if="userType===true"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          v-if="userType===true"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="eventList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="活动图片" align="center" prop="eventLogo">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.eventLogo"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column v-if="userType===false" label="店铺id" align="center" prop="shopId"/>
      <el-table-column v-if="userType===false" label="店铺名" align="center" prop="shopName"/>
      <el-table-column label="活动名称" align="center" prop="eventName"/>
      <el-table-column label="活动描述" align="center" prop="description"/>
      <el-table-column label="开始时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否折扣" align="center" prop="isDiscount"/>
      <el-table-column label="折扣率" align="center" prop="discountRate"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="活动创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="amount"/>
      <el-table-column label="活动数量" align="center" prop="activityNum"/>
      <el-table-column label="浏览量" align="center" prop="pageView"/>
      <el-table-column label="点赞数" align="center" prop="likes"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="userType===true"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
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

    <!-- 添加或修改店铺活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="活动图片" prop="eventLogo">
          <div class="text-center">
            <userAvatar :avatar="form.eventLogo"/>
          </div>
        </el-form-item>

        <el-form-item label="活动名称" prop="eventName">
          <el-input v-model="form.eventName" placeholder="请输入活动名称"/>
        </el-form-item>

        <el-form-item label="活动描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>

        <el-form-item label="开始时间" prop="startDate">
          <el-date-picker clearable
                          v-model="form.startDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker clearable
                          v-model="form.endDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="是否折扣" prop="isDiscount">
          <el-input v-model="form.isDiscount" placeholder="请输入是否折扣"/>
        </el-form-item>

        <el-form-item label="折扣率" prop="discountRate">
          <el-input v-model="form.discountRate" placeholder="请输入折扣率"/>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>

        <el-form-item label="金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入金额"/>
        </el-form-item>

        <el-form-item label="活动数量" prop="activityNum">
          <el-input v-model="form.activityNum" placeholder="请输入活动数量"/>
        </el-form-item>

        <el-form-item label="浏览量" prop="pageView">
          <el-input :disabled="true" v-model="form.pageView" placeholder="请输入浏览量"/>
        </el-form-item>
        <el-form-item label="点赞数" prop="likes">
          <el-input :disabled="true" v-model="form.likes" placeholder="请输入点赞数"/>
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
import {listEvent, getEvent, delEvent, addEvent, updateEvent} from "@/api/data/shop-event/shopEvent";
import Cookies from "js-cookie";
import userAvatar from "@/views/system/user/profile/userAvatar.vue";
import {mapGetters} from "vuex";
import store from "@/store";

export default {
  name: "Event",
  components: {userAvatar},
  computed: {
    ...mapGetters(['img']),
    // 直接通过 this.$store.state 访问 Vuex state
  },

  watch: {
    img(newValue, oldValue) {
      this.flag = null
      this.form.eventLogo = newValue
    }
  },
  data() {
    return {
      flag: null,
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
      // 店铺活动表格数据
      eventList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eventLogo: null,
        shopId: null,
        shopName: null,
        eventName: null,
        description: null,
        startDate: null,
        endDate: null,
        isDiscount: null,
        discountRate: null,
        createTime: null,
        amount: null,
        activityNum: null,
        pageView: null,
        likes: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      userType: null
    };
  },
  created() {
    this.userType = Cookies.get('source') === 'seller-user'
    this.getList();
  },

  methods: {
    /** 查询店铺活动列表 */
    getList() {
      this.loading = true;
      this.queryParams.shopId=store.getters.shopId
      listEvent(this.queryParams).then(response => {
        this.eventList = response.rows;
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
        eventLogo: null,
        shopId: null,
        shopName: null,
        eventName: null,
        description: null,
        startDate: null,
        endDate: null,
        isDiscount: null,
        discountRate: null,
        remark: null,
        createTime: null,
        createBy: null,
        updateBy: null,
        updateTime: null,
        amount: null,
        activityNum: null,
        pageView: null,
        likes: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加店铺活动";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEvent(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺活动";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {

        if (this.flag === null) {
          this.form.eventLogo = this.$store.getters.img
        } else {
          this.form.eventLogo = this.flag
        }
        this.form.userType = this.userType

        if (valid) {
          if (this.form.id != null) {
            updateEvent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {

            addEvent(this.form, store.getters.id).then(response => {
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
      this.$modal.confirm('是否确认删除店铺活动编号为"' + ids + '"的数据项？').then(function () {
        return delEvent(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/event/export', {
        ...this.queryParams
      }, `event_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
