<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="账户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入账户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商铺名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入商铺名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商圈" prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入商圈"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销量" prop="sold">
        <el-input
          v-model="queryParams.sold"
          placeholder="请输入销量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="评分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入评分"
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
          v-hasPermi="['system:shop:add']"
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
          @click="handleUpdate"
          v-hasPermi="['system:shop:edit']"
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
          v-hasPermi="['system:shop:remove']"
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
          v-hasPermi="['system:shop:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="账户名" align="center" prop="userName"/>
      <el-table-column label="经营人" align="center" prop="createBy"/>
      <el-table-column label="商铺名称" align="center" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column label="商铺类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.distance_shop_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="商铺图片" align="center" prop="images">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.images"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center" prop="address" :show-overflow-tooltip="true"/>
      <el-table-column label="均价(元)" align="center" prop="avgPrice"/>
      <el-table-column label="销量" align="center" prop="sold"/>
      <el-table-column label="评论数量" align="center" prop="comments"/>
      <el-table-column label="评分" align="center" prop="score"/>
      <el-table-column label="营业时间" align="center" prop="openHours" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:shop:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:shop:remove']"
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

    <!-- 添加或修改店铺对话框 -->
    <el-dialog  :title="title" :visible.sync="open" width="1100px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="账户名" prop="userName">
          <el-select v-model="form.userId" filterable placeholder="请选择" style="width: 100%">
            <el-option
              v-for="item in userListId"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId">
            </el-option>
          </el-select>

        </el-form-item>

        <el-form-item label="商铺名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入商铺名称"/>
        </el-form-item>


        <el-form-item label="商铺类型" prop="name">

          <el-select v-model="form.type">
            <el-option
              v-for="dict in dict.type.distance_shop_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>


        </el-form-item>


        <el-form-item label="商铺图片" prop="images">
          <div class="text-center">
            <userAvatar :avatar="form.images"/>
          </div>
        </el-form-item>

        <el-form-item label="商铺坐标" prop="images">
          <div style="height: 600px">
            <mymap/>
          </div>

        </el-form-item>

        <el-form-item label="商圈" prop="area">
          <el-input v-model="form.area" placeholder="请输入商圈"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input disabled v-model="form.address" placeholder="请输入地址"/>
        </el-form-item>

        <el-form-item label="经度" prop="address">
          <el-input disabled v-model="form.x" placeholder="请输入经度"/>
        </el-form-item>

        <el-form-item label="纬度" prop="address">
          <el-input disabled v-model="form.y" placeholder="请输入纬度"/>
        </el-form-item>

        <el-form-item label="营业时间" prop="openHours">
          <el-input v-model="form.openHours" placeholder="请输入营业时间"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>

        <el-form-item label="销量" prop="sold">
          <el-input disabled v-model="form.sold" placeholder="请输入销量"/>
        </el-form-item>
        <el-form-item label="均价" prop="avgPrice">
          <el-input disabled v-model="form.sold" placeholder="请输入均价"/>
        </el-form-item>
        <el-form-item label="评论数量" prop="comments">
          <el-input disabled v-model="form.comments" placeholder="请输入评论数量"/>
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-input disabled v-model="form.score" placeholder="请输入评分"/>
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
import {addShop, delShop, getShop, listShop, updateShop} from "@/api/data/shop/shop";
import {mapGetters} from "vuex";
import userAvatar from "@/views/system/user/profile/userAvatar.vue";
import mymap from "@/views/map/map/index.vue";
import {listUserId} from "@/api/user/user";

export default {
  name: "Shop",
  components: {userAvatar,mymap},
  dicts: ['distance_shop_type'],
  computed: {
    ...mapGetters(['img','address','lng','lat'])
  },
  watch: {
    address(newValue, oldValue){
      this.form.address = newValue
    },
    lng(newValue, oldValue){
      this.form.x = newValue
    },
    lat(newValue, oldValue){
      this.form.y = newValue
    },

    img(newValue, oldValue) {
      this.flag = null
      this.form.images = newValue
    },
    'form.userId': function (newValue, oldValue) {
      this.form.userName = this.userListId.find(user => user.userId === newValue)?.nickName;
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
      // 店铺表格数据
      shopList: [],
      userListId: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        name: null,
        type: null,
        images: null,
        area: null,
        address: null,
        x: null,
        y: null,
        avgPrice: null,
        sold: null,
        comments: null,
        score: null,
        openHours: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: ''
    };
  },
  created() {
    this.getList();

  },

  methods: {


    /** 查询店铺列表 */
    getList() {
      this.loading = true;
      listShop(this.queryParams).then(response => {
        this.shopList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getUserIdList() {
      listUserId().then(response => {
        this.userListId = response.data;
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
        userId: null,
        userName: null,
        name: null,
        type: null,
        images: null,
        area: null,
        address: null,
        x: null,
        y: null,
        avgPrice: null,
        sold: null,
        comments: null,
        score: null,
        openHours: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getUserIdList();
      this.reset();
      this.open = true;
      this.title = "添加店铺";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShop(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改店铺";

        this.flag = this.form.images
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          if (this.flag === null) {
            this.form.images = this.$store.getters.img
          } else {
            this.form.images = this.flag
          }


          if (this.form.id != null) {
            updateShop(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShop(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除店铺编号为"' + ids + '"的数据项？').then(function () {
        return delShop(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/shop/export', {
        ...this.queryParams
      }, `shop_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
