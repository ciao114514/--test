<template>
    <div>
         <!-- 面包屑 -->
         <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/setmealChecks' }"
                >返回套餐选择</el-breadcrumb-item
            >
            <el-breadcrumb-item>{{ section.tname }}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card>
            <div class="sectionIndex1" style="font-size: 24px">
                <el-button
                        type="success"
                        style="font-size: 18px"
                        @click="orderSuccess()"
                    >导入体检用户</el-button>
            </div>
            <el-table :data="orderData" stripe style="width: 100%" border>
                <el-table-column
                    prop="oId"
                    label="挂号单号"
                    width="100px"
                ></el-table-column>
                <el-table-column
                    prop="pId"
                    label="本人id"
                    width="80px"
                ></el-table-column>
                <el-table-column
                    prop="pName"
                    label="姓名"
                    width="80px"
                ></el-table-column>
                
                <el-table-column
                    prop="tname"
                    label="体检套餐"
                    width="150px"
                ></el-table-column>

                <el-table-column
                    prop="oStart"
                    label="挂号时间"
                    width="195px"
                ></el-table-column>
                <el-table-column
                    prop="oEnd"
                    label="结束时间"
                    width="185px"
                ></el-table-column>
                <el-table-column
                    prop="oTotalPrice"
                    label="需交费用/元"
                    width="80px"
                ></el-table-column>
                <el-table-column
                    prop="oPriceState"
                    label="缴费状态"
                    width="150"
                >
                    <template slot-scope="scope">
                        <el-tag
                            type="success"
                            v-if="scope.row.oPriceState === 1"
                            >已缴费</el-tag
                        >
                        <el-button
                            type="warning"
                            icon="iconfont icon-r-mark1"
                            style="font-size: 14px"
                            v-if="
                                scope.row.oPriceState === 0
                            "
                            @click="priceClick2(scope.row.oId, scope.row.otype)"
                        >
                            点击缴费</el-button
                        >
                       
                    </template>
                </el-table-column>
                
                <el-table-column prop="oState" label="预约状态" >
                    <template slot-scope="scope">
                        <el-tag
                            type="success"
                            v-if="
                                scope.row.oState === 1 &&
                                scope.row.oPriceState === 1
                            "
                            >已预约</el-tag
                        >
                        <el-tag
                            type="success"
                            v-if="
                                scope.row.oState === 1 &&
                                scope.row.oPriceState === 0
                            "
                            >未缴费</el-tag
                        >
                      
                    </template>
                </el-table-column>
                <!--<el-table-column prop="oState" label="操作" >
                    <template slot-scope="scope">
                      
                      
                        <el-button
                            type="success"
                            icon="iconfont icon-r-find"
                            style="font-size: 14px"
                            v-if="
                                 scope.row.oState === 0 && scope.row.oState === 0
                            "
                            @click="priceClick(
                                scope.row.pId, 
                                scope.row.tid,
                                scope.row.oStart,
                                scope.row.tname,
                                scope.row.oTotalPrice,
                                scope.row.tid,
                                scope.row.tid,
                            )"
                        >
                            预约挂号</el-button
                        >
                    </template>
                </el-table-column>-->
                
            </el-table>
        </el-card>
        <!-- 评价对话框 -->
        <el-dialog title="体检人员导入" :visible.sync="starVisible">
            
            <div>添加批量导入套餐用户人员，请<div  @click="outEexcel()" style="color: blue;text-decoration: underline;">导入模板</div></div>
            <el-upload
                class="upload-demo"
                :action="'http://localhost:9282/patient/intputPatientExcel?tid='+section.tid"
                :on-success="handleSuccess"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                multiple
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>

            <div slot="footer" class="dialog-footer">
                <el-button @click="starVisible = false" style="font-size: 18px;"><i class="iconfont icon-r-left" style="font-size: 20px;"></i> 取 消</el-button>
                <el-button type="primary" @click="starClick" style="font-size: 18px;"><i class="iconfont icon-r-yes" style="font-size: 20px;"></i> 确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import request from "@/utils/request.js";
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";
export default {
    name: "ASetmealOrder",
    data() {
        return {
            otype:"",
            fileList: [],
            section:JSON.parse(this.$route.query.section),
            userId: 1,
            orderData: [],
            times: ["08:30-11:30 ","14:30-17:30"],
            orderForm: { orderDate: "" },
            orderRules: {
                oTime: [
                    { required: true, message: "选择时间段", trigger: "blur" },
                ],
            },
            star: 5,
            starVisible: false,
            dId: 1,
            dName: "",
            setmealOrder:{
                pId:"",
                tid: "",
                oStart:"",
                tname:"",
                oTotalPrice:""
            },

        };
    },
    methods: {
        starClick(){
            this.starVisible=false;
            sessionStorage.setItem("otype",this.otype);
            this.requestOrder();
        },    


        //导出
        outEexcel(){
            window.location.href =
            "http://localhost:9281/excel/exportReport" ;      
        },
        handleRemove(file, fileList) {
        console.log(file, fileList);
        console.log("aaaaa")
        },
        handlePreview(file) {
            console.log(file);
            console.log("bbbbb")
        },
        handleExceed(files, fileList) {
           // this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${ file.name }？`);

        },
        handleSuccess(res, file) { // action 上传时的回调
           
            this.otype = res;

        },

        //弹框
        orderSuccess() {
           
                
                this.starVisible=true;
                
        },
 
       
        //预约挂号
        priceClick2(oId, otype) {
                    
            request
                        .get("setmealOrder/addOrders", {
                            params: {
                                oId: oId,
                            },
                        })
                        .then((res) => {
                            console.log("res");
                            console.log(res);
                            if (res.data.status == 200)
                            this.$message.success(res.data.msg);

                            if(res.data.msg == "缴费成功！"){
                                //缴费
                                request
                                    .get("setmealOrder/updateSetmealOrdersPrice", {
                                        params: {
                                            oId: oId,
                                        },
                                    })
                                    .then((res) => {
                                        if (res.data.status !== 200) {
                                            this.$message.error("请求数据失败");
                                            return;
                                        }
                                        this.$message.success("单号 " + oId + " 缴费成功！");
                                        this.requestOrder();

                                    });

                            }

                            this.requestDoctors();
                        })
                        .catch((e) => {
                            console.log(e);
                           
                        });   

        },
       

        addSetmealOrdersclick(){
            request
                .get("setmealOrder/addSetmealOrders", {
                    params: {
                        pId: this.setmealOrder.pId,
                        tid: this.setmealOrder.tid,
                        oStart:this.setmealOrder.oStart+' '+this.orderForm.oTime,
                        tname:this.setmealOrder.tname,
                        oTotalPrice:this.setmealOrder.oTotalPrice,
                        oPriceState:0,
                        oState:1,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("预约失败");
                    this.$message.success("谢谢您的预约成功，请及时缴费！");

                    this.starVisible=false;
                    this.requestOrder();
                });
        },

        //请求挂号信息
        requestOrder() {
            request
                .get("setmealOrder/selectAdminSetmealOrder", {
                    params: {
                        otype: sessionStorage.getItem("otype"),
                        tid: this.section.tid,
                        ostart:sessionStorage.getItem("arrDate"),
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        this.$message.error("请求数据失败");
                    this.orderData = res.data.data;
                    //this.orderData.dSection = res.data.data.map(item => item.doctor.dSection);
                    //console.log(res.data.data.map(item => item.doctor.dSection));
                    console.log(this.orderData.oId);
                    console.log(this.orderData.pName);
                    console.log(res);
                });
        },
        //token解码
        tokenDecode(token) {
            if (token !== null) return jwtDecode(token);
        },
    },
    created() {
        // 解码token
        //this.orderData.pName = this.tokenDecode(getToken()).pName;
        //this.orderData.pCard = this.tokenDecode(getToken()).pCard;
        this.userId = this.tokenDecode(getToken()).pId;
        console.log(this.orderData.pName);
        //this.orderData.pName = "dasda"
        this.requestOrder();
    },
};
</script>
<style lang="scss" scoped>
.el-dialog div {
    text-align: center;
    margin-bottom: 8px;
}
</style>