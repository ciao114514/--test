<template>
    <div>
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
                <el-col :span="6">
                    <el-button
                        type="primary"
                        style="font-size: 18px"
                        @click="addFormVisible = true"
                    >
                    <i class="iconfont icon-r-add" style="font-size: 22px;"></i> 
                        增加套餐</el-button
                    >
                </el-col>
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
                            @click="add_Setmeal_Check(scope.row.tid)"
                        ><i class="iconfont icon-r-add" style="font-size: 22px;"></i> 检查项目</el-button>
                        <el-button
                            style="font-size: 18px"
                            type="success"
                            @click="modifyDialog(scope.row.tid)"
                        ><i class="iconfont icon-r-edit" style="font-size: 22px;"></i> 编辑</el-button>
                        <el-button
                            style="font-size: 18px"
                            type="danger"
                            @click="deleteDialog(scope.row.tid)"
                        ><i class="iconfont icon-r-delete" style="font-size: 22px;"></i> 删除</el-button>
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

        <!-- 修改检查项目药物对话框 -->
        <el-dialog title="修改套餐项目" :visible.sync="modifyFormVisible">
            <el-form :model="modifyForm" :rules="rules" ref="ruleForm">
                
                <el-form-item label="套餐" prop="tname" label-width="80px">
                    <el-input v-model="modifyForm.tname"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price" label-width="80px">
                    <el-input v-model="modifyForm.price"></el-input>
                </el-form-item>
                <el-form-item label="顺序" prop="sert" label-width="80px">
                    <el-input v-model="modifyForm.sert"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="modifyFormVisible = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="modifyCheck('ruleForm')"
                    style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button
                >
            </div>
        </el-dialog>

        
        <el-dialog title="增加检查项目" :visible.sync="addsertCheck">

            <div slot="footer" class="dialog-footer">
                <el-button @click="addsertCheck = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="addsetsCheck('ruleForm')"
                    style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button
                >
            </div>
            <div>
                <el-card>
                    <!-- <span>科室门诊</span> -->
                    <template v-for="tiem in allchecks" >
                        <el-divider></el-divider>
                        <div class="sectionIndex" style="font-size: 24px">
                            <i class="iconfont icon-r-love" style="font-size: 26px"
                                ></i
                            >
                            <b> {{tiem.kname}}</b>
                        </div>
                        <ul>
                            <li v-for="(inter, index) in tiem.checks" :key="index">
                                <el-button 
                                    :style="inter.isActive=='false'? 'margin: 5px;':'margin: 5px ; backgroundColor:#409EFF ; color:white;'"
                                    @click="sectionClick(inter,$event,inter.isActive=!inter.isActive)"
                                    >{{ inter.chName }}</el-button
                                >
                            </li>
                        </ul>

                     </template>
                  


                </el-card>
            </div>

           


        </el-dialog>    



    </div>
</template>
<script>
import request from "@/utils/request.js";
import { toLoad } from "@/utils/initialize.js";
export default {
    name: "SetmealList",
    data() {
        return {
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
        add_Setmeal_Check(id){
            if(this.tid==""){
                this.tid=id;
            }else if(this.tid!=id){
                this.tid=id;
                this.setmeal_checks=[];

            }

            
            console.log(this.tid)
            request
                .get("check/findChecks", {
                    params: {
                        tid: id,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("请求数据失败");
                   
                    this.allchecks = res.data.data;
                    this.addsertCheck= true;
                   
                });

        },
        //添加体检项目
        sectionClick(inter,event,isActive){
            console.log(!isActive)
            if(!isActive){
                this.setmeal_checks.push(inter.chId)
                 event.currentTarget.style.backgroundColor  = "#409EFF" 
                 event.currentTarget.style.color  = "white" 
                 console.log(this.setmeal_checks)
            }else{
                
                for(let i=0;i<this.setmeal_checks.length;i++){
                    if(this.setmeal_checks[i] == inter.chId){
                        this.setmeal_checks.splice(i,1)
                    }
                }
                event.currentTarget.style.backgroundColor  = "" 
                 event.currentTarget.style.color  = "" 
            }
            
        },
        addsetsCheck(){
            var params = new URLSearchParams();
            params.append("tid", this.tid);
            params.append("params", JSON.stringify(this.setmeal_checks));

            request
                .post("setmeal/addSetmealChecks", params)
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("请求数据失败");
                   
                        this.addsertCheck = false;
                           this.$message.success("增加检查项目成功！");
                   
                });

        },


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
        //打开修改对话框
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
        //删除检查操作
        deleteSetmeal(id) {
            request
                .get("setmeal/deleteSetmeal", {
                    params: {
                        tid: id,
                    },
                })
                .then((res) => {
                    this.requestChecks();
                    console.log(res);
                });
        },
        //删除对话框
        deleteDialog(id) {
            this.$confirm("此操作将删除该套餐信息, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.deleteSetmeal(id);
                    this.$message({
                        type: "success",
                        message: "删除成功!",
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
        //点击增加确认按钮
        addCheck(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("setmeal/addSetmeal", {
                            params: {
                                tid: this.addForm.tid,
                                tname: this.addForm.tname,
                                price: this.addForm.price,
                                sert: this.addForm.sert,
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error(
                                    "编号不合法或已被占用！"
                                );
                            this.addFormVisible = false;
                            this.requestChecks();
                            this.$message.success("增加检查项目成功！");
                            console.log(res);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
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
                .get("setmeal/findAllSetmeals", {
                    params: {
                        pageNumber: this.pageNumber,
                        size: this.size,
                        query: this.query,
                    },
                })
                .then((res) => {
                    this.setmealData = res.data.data.setmeals;
                    this.total = res.data.data.total;
                    toLoad();
                });
        },
        requestAllChecks(){
            request
                .get("check/findChecks", {
                    
                })
                .then((res) => {
                    this.posts = res.data.data;
                   
                });    


        }, 




    },
    created() {
        this.requestChecks();
      //  this.requestAllChecks();
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