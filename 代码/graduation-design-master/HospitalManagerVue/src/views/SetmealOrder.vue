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
                        <el-tag
                            type="success"
                            v-if="scope.row.oPriceState === 0 &&
                                scope.row.oState === 0"
                            >未缴费</el-tag
                        >
                        <!-- <el-tag type="danger" v-if="scope.row.oPriceState === 0 && scope.row.oState === 1">未缴费</el-tag> -->
                        <el-button
                            type="warning"
                            icon="iconfont icon-r-mark1"
                            style="font-size: 14px"
                            v-if="
                                scope.row.oPriceState === 0 &&
                                scope.row.oState === 1
                            "
                            @click="pClick(scope.row.oId, scope.row.tid)"
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
                            >已完成</el-tag
                        >
                        <el-tag
                            type="success"
                            v-if="
                                scope.row.oState === 1 &&
                                scope.row.oPriceState === 0
                            "
                            >已预约</el-tag
                        >
                        <el-tag
                            type="success"
                            v-if="
                                scope.row.oState === 0 &&
                                scope.row.oPriceState === 0
                            "
                            >未预约</el-tag
                        >
                    </template>
                </el-table-column>
                <el-table-column prop="oState" label="操作" >
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
                </el-table-column>
                
            </el-table>
        </el-card>
        <!-- 评价对话框 -->
        <el-dialog title="填写挂号信息" :visible.sync="starVisible">
            <el-form :model="orderForm" ref="orderForm" :rules="orderRules">
                <el-form-item
                    label="挂号时间段"
                    label-width="100px"
                    prop="oTime"
                >
                    <el-select
                        v-model="orderForm.oTime"
                        placeholder="请选择"
                        no-data-text="请尝试预约明日"
                    >
                        <el-option
                            v-for="time in times"
                            :key="time"
                            :label="time"
                            :value="time"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button
                    @click="orderFormVisible = false"
                    style="font-size: 18px"
                    ><i
                        class="iconfont icon-r-left"
                        style="font-size: 20px"
                    ></i>
                    取 消</el-button
                >
                <el-button
                    type="primary"
                    @click="addSetmealOrdersclick()"
                    style="font-size: 18px"
                    ><i class="iconfont icon-r-yes" style="font-size: 20px"></i>
                    确 定</el-button
                >
            </div>
        </el-dialog>
    </div>
</template>
<script>
import request from "@/utils/request.js";
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";
export default {
    name: "MyOrder",
    data() {
        return {
            section:JSON.parse(this.$route.query.section),
            userId: 1,
            orderData: [],
            times: ["08:30-11:30  上午","14:30-17:30  下午"],
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
        //预约挂号
        orderSuccess() {
           
                console.log(this.orderForm.oTime)
            
            
        },
        //缴费
        pClick(oId, tid) {


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
       
        //预约挂号
        priceClick(pId, tid,oStart,tname,oTotalPrice) {
            this.setmealOrder.pId=pId;
            this.setmealOrder.tid=tid;
            this.setmealOrder.oStart=oStart;
            this.setmealOrder.tname=tname;
            this.setmealOrder.oTotalPrice=oTotalPrice;

            this.starVisible=true;

           
        },
       

        addSetmealOrdersclick(){
            //判断时间
            //if（）{

           // }
           console.log(this.orderForm.oTime)
           console.log(this.setmealOrder.oStart)

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
                .get("setmealOrder/selectSetmealOrder", {
                    params: {
                        pid: this.userId,
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