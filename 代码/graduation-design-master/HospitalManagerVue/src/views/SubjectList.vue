<template>
    <!-- 卡片 -->
    <el-card>
        <!-- 搜索栏及增加医生 -->
        <el-row type="flex">
            
            <el-col :span="4"></el-col>
            <el-col :span="4">
                <el-button type="primary" @click="addFormVisible = true"
                        style="font-size: 18px;"
                    >
                    <i class="iconfont icon-r-add" style="font-size: 22px;"></i> 
                    增加类型</el-button
                >
            </el-col>
            <el-col :span="4"></el-col>
            <!-- 导入数据 -->
            <!-- <el-col :span="2">
                <el-upload
                    class="upload-demo"
                    action="doctor/uploadExcel"
                    accept=".xlsx,.xls "
                    :limit="1"
                    :show-file-list="false"
                    :on-progress="handleProgress"
                    :on-exceed="handleExceed"
                    :on-success="handleSuccess"
                    :on-error="handleError"
                    :file-list="fileList"
                >
                    <el-button size="small" type="success">一键导入</el-button>
                </el-upload>
            </el-col>

            <el-col :span="2">
                <el-button size="small" type="success" @click="exportDoctors"
                    >一键导出</el-button
                ></el-col
            > -->
        </el-row>
        <!-- 表格 -->
        <el-table :data="subjectData" stripe style="width: 100%" border>
            <el-table-column prop="kid" label="id">
            </el-table-column>
            <el-table-column prop="name" label="类型1" >
            </el-table-column>
            <el-table-column prop="kname" label="类型2" >
            </el-table-column>
            <el-table-column prop="sert" label="顺序" >
            </el-table-column>
           
            <el-table-column label="操作"  fixed="right">
                <template slot-scope="scope">
                    <el-button
                        style="font-size: 18px;"
                        type="success"
                        @click="modifyDialog(scope.row.kid)"
                    ><i class="iconfont icon-r-edit" style="font-size: 22px;"></i> 编辑</el-button>
                    <el-button
                        style="font-size: 18px;"
                        type="danger"
                        @click="deleteDialog(scope.row.kid)"
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
        <!-- 增加类型对话框 -->
        <el-dialog title="增加类型" :visible.sync="addFormVisible">
            <el-form :model="addForm" :rules="rules" ref="ruleForm">
                <el-form-item label="类型1" label-width="80px" prop="name">
                    <el-select v-model="addForm.name" placeholder="请选择类型1">
                        <el-option
                            v-for="post in posts"
                            :key="post"
                            :label="post"
                            :value="post"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>   
                <el-form-item label="类型2" label-width="80px" prop="kname">
                    <el-input
                        v-model.number="addForm.kname"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="排序" label-width="80px" prop="sert">
                    <el-input
                        v-model.number="addForm.sert"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
               
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="addSubject('ruleForm')"
                    style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button
                >
            </div>
        </el-dialog>

        <!-- 修改医生对话框 -->
        <el-dialog title="修改类型信息" :visible.sync="modifyFormVisible">
            <el-form :model="modifyForm" :rules="rules" ref="ruleForm">

            <el-form-item label="类型1" label-width="80px" prop="name">
                    <el-select
                        v-model="modifyForm.name"
                        placeholder="请选择类型1"
                    >
                        <el-option
                            v-for="post in posts"
                            :key="post"
                            :label="post"
                            :value="post"
                        >
                        </el-option>
                    </el-select>
                </el-form-item> 

                <el-form-item label="类型2" label-width="80px" prop="kname">
                    <el-input
                        v-model="modifyForm.kname"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
                <el-form-item label="排序" label-width="80px" prop="sert">
                    <el-input
                        v-model="modifyForm.sert"
                        autocomplete="off"
                    ></el-input>
                </el-form-item>
               
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="modifyFormVisible = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="modifyDoctor('ruleForm')"
                    style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button
                >
            </div>
        </el-dialog>
    </el-card>
</template>
<script>
import request from "@/utils/request.js";
import { toLoad } from "@/utils/initialize.js";
export default {
    name: "SubjectList",
    data() {
       
        return {
            pageNumber: 1,
            size: 8,
            query: "",
            total: 3,
            addFormVisible: false,
            modifyFormVisible: false,
            subjectData:[],
            addForm: {},
            modifyForm: {},
            posts: ["内科类", "外科类", "妇产科类", "儿科类", "五官科类", "中医类", "其他类"],
            rules: {
                name: [
                    { required: true, message: "请输入类型1", trigger: "blur" },
                    {
                        min: 2,
                        max: 10,
                        message: "账号必须是2到10个字符",
                        trigger: "blur",
                    },
                ],
                kname: [
                    { required: true, message: "请输入类型2", trigger: "blur" },
                    {
                        min: 2,
                        max: 10,
                        message: "账号必须是2到10个字符",
                        trigger: "blur",
                    },
                ],
                sert: [
                    { required: true, message: "请输入排序", trigger: "blur" },
                ],
            }    
        };
    },
    methods: {
    //点击修改医生信息
        modifyDoctor(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("subject/modifySubject", {
                            params: {
                                kid: this.modifyForm.kid,
                                name: this.modifyForm.name,
                                kname: this.modifyForm.kname,
                                sert: this.modifyForm.sert
                               
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error("修改信息失败！");
                            this.modifyFormVisible = false;
                            this.requestDoctors();
                            this.$message.success("修改医生信息成功！");
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
                .get("subject/findSubject", {
                    params: {
                        kid: id,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        this.$message.error("请求数据失败");
                    this.modifyForm = res.data.data;
                    this.modifyFormVisible = true;
                    console.log(res);
                });
        },
         //删除医生操作
         deleteSubject(id) {
            request
                .get("subject/deleteSubject", {
                    params: {
                        kid: id,
                    },
                })
                .then((res) => {
                    this.requestDoctors();
                    console.log(res);
                });
        },
      
        //删除对话框
        deleteDialog(id) {
            this.$confirm("此操作将删除该类型信息, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.deleteSubject(id);
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
        // 增加类型
        addSubject(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("subject/addSubject", {
                            params: {
                                name: this.addForm.name,
                                kname: this.addForm.kname,
                                sert:this.addForm.sert
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error(
                                    "账号不合法或已被占用！"
                                );
                            this.addFormVisible = false;
                            this.requestDoctors();
                            this.$message.success("增加类型成功！");
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
            this.requestDoctors();
        },
        //   页码改变时触发
        handleCurrentChange(num) {
            console.log(num);
            this.pageNumber = num;
            this.requestDoctors();
        },
        // 加载类型列表
        requestDoctors() {
            request
                .get("subject/findAllSubjects", {
                    params: {
                        pageNumber: this.pageNumber,
                        size: this.size,
                        query: this.query,
                    },
                })
                .then((res) => {
                    this.subjectData = res.data.data.subjects;

                    this.total = res.data.data.total;
                    toLoad()
                });
        },
    },
    created() {
        this.requestDoctors();
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
</style>