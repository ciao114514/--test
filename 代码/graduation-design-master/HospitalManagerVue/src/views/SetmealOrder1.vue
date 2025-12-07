<template>
    <div>
         <!-- 面包屑 -->
         <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/setmealChecks' }"
                >返回套餐选择</el-breadcrumb-item
            >
            <el-breadcrumb-item>{{ section.tname }}</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片 -->
        <el-card>
            <!-- 搜索栏及增加检查 -->
            <el-row type="flex">
                <el-col :span="6">
                    <el-input v-model="query" placeholder="请输入名称查询">
                        <el-button
                            slot="append"
                            style="font-size: 18px;"
                            @click="requestChecks"
                        ><i class="iconfont icon-r-find" style="font-size: 22px;"></i> 搜索</el-button>
                    </el-input>
                </el-col>
                <el-col :span="6"></el-col>
                
            </el-row>
            <!-- 表格 -->
            <el-table :data="setmealData" stripe style="width: 100%" border>
                <el-table-column label="编号" prop="tid"></el-table-column>
                <el-table-column label="套餐" prop="tname"></el-table-column>
                <el-table-column
                    label="价格/元"
                    prop="price"
                ></el-table-column>
                <el-table-column label="顺序" prop="sert"></el-table-column>
                <el-table-column label="操作" width="400" fixed="right">
                    <template slot-scope="scope">
                       
                        <el-button
                            style="font-size: 18px"
                            type="success"
                            @click="modifyDialog(scope.row.tid)"
                        ><i class="iconfont icon-r-edit" style="font-size: 22px;"></i> 添加人员</el-button>
                        
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                background
                layout="total, sizes, prev, pager, next, jumper"
                :current-page="pageNumber"
                :page-size="size"
                :page-sizes="[1, 2, 4, 8, 16]"
                :total="total"
            >
            </el-pagination>
        </el-card>

        <!-- 增加检查项目对话框 -->
        <el-dialog title="增加套餐项目" :visible.sync="addFormVisible">
            <el-form :model="addForm" :rules="rules" ref="ruleForm">
                
                <el-form-item label="套餐" prop="tname" label-width="80px">
                    <el-input v-model="addForm.tname"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price" label-width="80px">
                    <el-input v-model="addForm.price"></el-input>
                </el-form-item>
                <el-form-item label="顺序" prop="sert" label-width="80px">
                    <el-input v-model="addForm.sert"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="addCheck('ruleForm')"
                    style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button
                >
            </div>
            
        </el-dialog>


        



    </div>
</template>
<script>
import request from "@/utils/request.js";
import { toLoad } from "@/utils/initialize.js";
export default {
    name: "SetmealOrder",
    data() {
        return {
            section:JSON.parse(this.$route.query.section),
            tid:"",
            setmeal_checks:[],
            allchecks:"",
            pageNumber: 1,
            size: 8,
            query: "",
            setmealData: [],
            total: 3,
            addFormVisible: false,
            addsertCheck: false,
            addForm: {},
            activePath:"",
            rules: {
                
                tname: [
                    { required: true, message: "请输入套餐", trigger: "blur" },
                    {
                        min: 1,
                        max: 50,
                        message: "账号必须是1到50个字符",
                        trigger: "blur",
                    },
                ],
                price: [
                    { required: true, message: "请输入单价", trigger: "blur" },
                ],
            },
            modifyFormVisible: false,
            modifyForm: {},
        };
    },
    methods: {
       
       
     


        //点击修改药物信息
        modifyCheck(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("setmeal/modifySetmeal", {
                            params: {
                                tid: this.modifyForm.tid,
                                tname: this.modifyForm.tname,
                                price: this.modifyForm.price,
                                sert: this.modifyForm.sert,
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error("修改信息失败！");
                            this.modifyFormVisible = false;
                            this.requestChecks();
                            this.$message.success("修改套餐信息成功！");
                            console.log(res);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        //打开查询人员
        modifyDialog(id) {
            request
                .get("setmeal/findSetmeal", {
                    params: {
                        tid: id,
                        
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("请求数据失败");
                    this.modifyForm = res.data.data;
                    this.modifyFormVisible = true;
                    console.log(res);
                });
        },
       
        
        
        //页面大小改变时触发
        handleSizeChange(size) {
            this.size = size;
            this.requestChecks();
        },
        //   页码改变时触发
        handleCurrentChange(num) {
            console.log(num);
            this.pageNumber = num;
            this.requestChecks();
        },
        // 加载检查列表
        requestChecks() {
            request
                .get("setmeal/findAllSetmealss", {
                    params: {
                        pageNumber: this.pageNumber,
                        size: this.size,
                        query: this.query,
                        tid: this.section.tid,
                    },
                })
                .then((res) => {
                    this.setmealData = res.data.data.setmeals;
                    this.total = res.data.data.total;
                    toLoad();
                });
        },
      




    },
    created() {
        this.requestChecks();
       
    },
};
</script>
<style scoped lang="scss">
.el-table {
    margin-top: 20px;
    margin-bottom: 20px;
}
.el-form {
    margin-top: 0;
}
ul li {
    display: inline;
}
.el-breadcrumb {
    margin: 8px;
}
.active {
  color: red;
  background-color: yellow;
}
</style>