<template>
    <div>
        <el-card>
             <!-- <span>科室门诊</span> -->
             <template v-for="tiem in setmealChecks" >
                        <el-divider></el-divider>
                        <el-button
                        @click="sectionClick(tiem)"
                         class="sectionIndex" style="font-size: 24px">
                            <i class="iconfont icon-r-love" style="font-size: 26px"
                                ></i
                            >
                            <b> {{tiem.tname}}({{tiem.price}}元)</b>
                        </el-button>
                        <ul>
                            <li v-for="(inter, index) in tiem.checks" :key="index">
                                <div  class="checks"
                                    style="margin: 5px; "
                                    >
                                    <span  style="color: #e6a23c;">
                                        {{index+1}}. 
                                        {{inter.kname }}</span> 
                                    ->{{ inter.chName }}</div
                                >
                            </li>
                        </ul>

                     </template>
                  
            <el-divider></el-divider>
        </el-card>
    </div>
</template>
<script>
import request from "@/utils/request.js";
import { toLoad } from "@/utils/initialize.js";
export default {
    name: "SectionIndex",
    data() {
        return {
            setmealChecks:"",
          
        };
    },
    methods: {
        //科室点击
        sectionClick(section) {
           
            this.$router.push({ path: "/setmealOrder?section=" + JSON.stringify(section) });
        },
        requestSetmealChecks() {
            request
                .get("setmeal/getSetmealCheck", {
                   
                })
                .then((res) => {
                    this.setmealChecks = res.data.data;
                
                    toLoad();
                });
        },
    },
    created() {
        this.requestSetmealChecks();
    },
    mounted() {
      toLoad()
    }
};
</script>
<style scoped lang="scss">
ul li {
    display: inline;
}
.el-breadcrumb {
    margin: 8px;
}
.checks{
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #FFF;
    
    color: #606266;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    transition: .1s;
    font-weight: 500;
    padding: 12px 20px;
    font-size: 14px;
   
}
</style>