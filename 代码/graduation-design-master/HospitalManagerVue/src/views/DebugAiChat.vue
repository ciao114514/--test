<template>
  <div class="ai-chat-container">
    <div class="chat-header">
      <h2><i class="iconfont icon-r-edit" style="margin-right: 10px;"></i>AI智能问答调试版</h2>
      <p>您可以询问有关健康体检、医疗建议等相关问题</p>
    </div>
    
    <div class="chat-messages" ref="messageContainer">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message', message.role === 'user' ? 'user-message' : 'ai-message']"
      >
        <div class="message-content">
          <div class="message-text">{{ message.content }}</div>
          <div class="message-time">{{ message.time }}</div>
        </div>
      </div>
    </div>
    
    <div class="chat-input">
      <el-input
        type="textarea"
        :rows="3"
        placeholder="请输入您的问题..."
        v-model="inputMessage"
        :disabled="loading"
      ></el-input>
      <div class="chat-actions">
        <el-button type="primary" @click="sendMessage" :loading="loading" :disabled="!inputMessage.trim()">
          发送
        </el-button>
        <el-button @click="testApi">
          测试API
        </el-button>
        <el-button @click="clearChat">
          清空记录
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: "DebugAiChat",
  data() {
    return {
      inputMessage: "",
      loading: false,
      messages: [
        {
          role: "ai",
          content: "您好！我是AI健康助手调试版，可以为您解答有关健康体检、医疗建议等相关问题。",
          time: this.getCurrentTime()
        }
      ]
    };
  },
  methods: {
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
    },
    
    async testApi() {
      this.loading = true;
      try {
        // 测试科室API
        const deptResponse = await request.get('/ai/chat/departments');
        this.messages.push({
          role: "ai",
          content: "科室API测试结果: " + JSON.stringify(deptResponse.data),
          time: this.getCurrentTime()
        });
        
        // 测试体检项目API
        const checkResponse = await request.get('/check/findAllChecks', {
          params: {
            pageNumber: 1,
            size: 5,
            query: '',
            chId: ''
          }
        });
        this.messages.push({
          role: "ai",
          content: "体检项目API测试结果: " + JSON.stringify(checkResponse.data),
          time: this.getCurrentTime()
        });
      } catch (error) {
        console.error('API测试失败:', error);
        this.messages.push({
          role: "ai",
          content: "API测试失败: " + (error.message || JSON.stringify(error)),
          time: this.getCurrentTime()
        });
      } finally {
        this.loading = false;
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
    
    sendMessage() {
      if (!this.inputMessage.trim() || this.loading) return;
      
      // 添加用户消息
      this.messages.push({
        role: "user",
        content: this.inputMessage,
        time: this.getCurrentTime()
      });
      
      // 保存问题
      const question = this.inputMessage;
      
      // 清空输入框
      this.inputMessage = "";
      
      // 滚动到底部
      this.$nextTick(() => {
        this.scrollToBottom();
      });
      
      // 处理用户问题
      this.getAiResponse(question);
    },
    
    async getAiResponse(question) {
      this.loading = true;
      
      try {
        console.log("用户问题:", question);
        
        // 检查问题类型并从数据库获取相关信息
        if ((question.includes("体检") || question.includes("检查")) && question.includes("项目")) {
          console.log("匹配到体检项目查询");
          const dbInfo = await this.getCheckItemsInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("套餐") && question.includes("体检")) {
          console.log("匹配到体检套餐查询");
          const dbInfo = await this.getSetmealInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("医生") || question.includes("科室")) {
          console.log("匹配到医生或科室查询");
          const dbInfo = await this.getDepartmentAndDoctorInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if ((question.includes("体检") && question.includes("注意")) || (question.includes("体检") && question.includes("准备")) || (question.includes("检查") && !question.includes("项目"))) {
          console.log("匹配到体检准备查询");
          const dbInfo = this.getPhysicalExamPrepInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("血压")) {
          console.log("匹配到血压查询");
          const dbInfo = this.getBloodPressureInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("血糖")) {
          console.log("匹配到血糖查询");
          const dbInfo = this.getBloodSugarInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("胆固")) {
          console.log("匹配到胆固醇查询");
          const dbInfo = this.getCholesterolInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("饮食") || question.includes("营养")) {
          console.log("匹配到饮食查询");
          const dbInfo = this.getDietInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        } else if (question.includes("运动") || question.includes("锻炼")) {
          console.log("匹配到运动查询");
          const dbInfo = this.getExerciseInfo();
          if (dbInfo) {
            this.messages.push({
              role: "ai",
              content: dbInfo,
              time: this.getCurrentTime()
            });
            return;
          }
        }
        
        console.log("未匹配到特定查询，使用默认回复");
        // 默认回复
        this.messages.push({
          role: "ai",
          content: "感谢您的提问！作为AI健康助手，我可以为您提供以下方面的建议：\n\n🩺 体检注意事项和流程\n🩸 常见健康指标解读（血压、血糖、胆固醇等）\n🥗 健康饮食和营养指导\n🏃 健康生活方式和运动建议\n📋 体检项目和套餐信息\n📋 科室和医生信息\n\n请尽量详细描述您的问题，比如\"体检前需要注意什么\"、\"高血压如何调理\"、\"有哪些体检项目\"、\"体检套餐有哪些\"、\"发热门诊有哪些医生\"等，我会为您提供更精准的解答！",
          time: this.getCurrentTime()
        });
      } catch (error) {
        console.error('AI回复出错:', error);
        this.messages.push({
          role: "ai",
          content: "处理您的问题时出现错误: " + (error.message || JSON.stringify(error)),
          time: this.getCurrentTime()
        });
      } finally {
        this.loading = false;
        
        // 滚动到底部
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
    
    clearChat() {
      this.messages = [
        {
          role: "ai",
          content: "您好！我是AI健康助手调试版，可以为您解答有关健康体检、医疗建议等相关问题。",
          time: this.getCurrentTime()
        }
      ];
    },
    
    scrollToBottom() {
      const container = this.$refs.messageContainer;
      container.scrollTop = container.scrollHeight;
    },
    
    // 获取体检项目信息
    async getCheckItemsInfo() {
      try {
        console.log("开始获取体检项目信息");
        const checkResponse = await request.get('/check/findAllChecks', {
          params: {
            pageNumber: 1,
            size: 100, // 获取前100个项目
            query: '',
            chId: ''
          }
        });
        console.log("体检项目响应:", checkResponse);
        
        if (checkResponse.data.status === 200 && checkResponse.data.data.list) {
          const checks = checkResponse.data.data.list;
          let response = "【医院体检项目列表】\n\n";
          
          // 按科室分组显示
          const groupedChecks = {};
          checks.forEach(check => {
            if (!groupedChecks[check.kname]) {
              groupedChecks[check.kname] = [];
            }
            groupedChecks[check.kname].push(check);
          });
          
          for (const [kname, checkList] of Object.entries(groupedChecks)) {
            response += `科室：${kname}\n`;
            checkList.forEach(check => {
              response += `- ${check.chName} (价格: ¥${check.chPrice})\n`;
            });
            response += "\n";
          }
          
          response += "以上是从医院数据库中查询到的最新体检项目信息。";
          return response;
        } else {
          return "暂时无法获取体检项目信息，请稍后再试。";
        }
      } catch (error) {
        console.error('获取体检项目失败:', error);
        return "获取体检项目失败: " + (error.message || JSON.stringify(error));
      }
    },
    
    // 获取体检套餐信息
    async getSetmealInfo() {
      try {
        console.log("开始获取体检套餐信息");
        const setmealResponse = await request.get('/setmeal/findAllSetmeals', {
          params: {
            pageNumber: 1,
            size: 100, // 获取前100个套餐
            query: ''
          }
        });
        console.log("体检套餐响应:", setmealResponse);
        
        if (setmealResponse.data.status === 200 && setmealResponse.data.data.list) {
          const setmeals = setmealResponse.data.data.list;
          let response = "【医院体检套餐列表】\n\n";
          
          setmeals.forEach(setmeal => {
            response += `套餐名称：${setmeal.tName}\n`;
            response += `套餐价格：¥${setmeal.tPrice}\n`;
            response += `适用性别：${setmeal.tGender}\n`;
            response += `套餐描述：${setmeal.tDescribe}\n\n`;
          });
          
          response += "以上是从医院数据库中查询到的最新体检套餐信息。";
          return response;
        } else {
          return "暂时无法获取体检套餐信息，请稍后再试。";
        }
      } catch (error) {
        console.error('获取体检套餐失败:', error);
        return "获取体检套餐失败: " + (error.message || JSON.stringify(error));
      }
    },
    
    // 获取科室和医生信息
    async getDepartmentAndDoctorInfo() {
      try {
        console.log("开始获取科室和医生信息");
        // 获取科室列表
        const deptResponse = await request.get('/ai/chat/departments');
        console.log("科室响应:", deptResponse);
        
        if (deptResponse.data.status === 200) {
          const departments = deptResponse.data.data;
          let response = "【医院科室和医生信息】\n";
          
          // 获取每个科室的医生信息
          for (let i = 0; i < departments.length; i++) {
            const deptName = departments[i];
            response += `\n${deptName}科室:\n`;
            
            try {
              const doctorResponse = await request.get('/ai/chat/doctors', {
                params: { department: deptName }
              });
              console.log(`科室${deptName}的医生响应:`, doctorResponse);
              
              if (doctorResponse.data.status === 200 && doctorResponse.data.data.length > 0) {
                const doctors = doctorResponse.data.data;
                doctors.forEach(doctor => {
                  response += `- ${doctor.dName} (${doctor.dPost || '医生'})`;
                  if (doctor.dIntroduction) {
                    response += ` - ${doctor.dIntroduction}`;
                  }
                  response += "\n";
                });
              } else {
                response += "  暂无医生信息\n";
              }
            } catch (doctorError) {
              console.error(`获取${deptName}科室医生信息失败:`, doctorError);
              response += "  获取医生信息失败\n";
            }
          }
          
          response += "\n以上是从医院数据库中查询到的最新科室和医生信息。";
          return response;
        } else {
          return "暂时无法获取科室信息，请稍后再试。";
        }
      } catch (error) {
        console.error('获取科室和医生信息失败:', error);
        return "获取科室和医生信息失败: " + (error.message || JSON.stringify(error));
      }
    },
    
    // 获取血压相关信息
    getBloodPressureInfo() {
      return "【血压基础知识】\n正常血压范围：收缩压90-140mmHg，舒张压60-90mmHg\n\n【高血压建议】\n1. 控制食盐摄入量，每日不超过6克\n2. 保持适当体重，戒烟限酒\n3. 增加运动，保持心情愉快\n4. 定期监测血压，遵医嘱服药\n5. 多吃新鲜蔬菜水果，减少高脂肪食物\n\n【低血压建议】\n1. 适当增加盐的摄入\n2. 多喝水保持充足血容量\n3. 避免久站或突然起立\n4. 适当运动增强心血管功能";
    },
    
    // 获取血糖相关信息
    getBloodSugarInfo() {
      return "【血糖参考值】\n空腹血糖：3.9-6.1mmol/L\n餐后2小时血糖：<7.8mmol/L\n糖化血红蛋白(HbA1c)：<6.5%\n\n【血糖异常调理】\n1. 控制饮食，少食多餐，避免高糖食物\n2. 规律运动，增强胰岛素敏感性\n3. 定期监测血糖变化\n4. 遵医嘱用药，不可自行停药\n5. 控制体重，保持良好作息";
    },
    
    // 获取胆固醇相关信息
    getCholesterolInfo() {
      return "【胆固醇参考值】\n总胆固醇：<5.2mmol/L\n低密度脂蛋白胆固醇(LDL-C)：<3.4mmol/L\n高密度脂蛋白胆固醇(HDL-C)：>1.0mmol/L\n甘油三酯：<1.7mmol/L\n\n【胆固醇异常调理】\n1. 减少饱和脂肪酸和胆固醇的摄入\n2. 多吃蔬菜水果和全谷物\n3. 保持规律运动\n4. 控制体重，戒烟限酒\n5. 遵医嘱服用降脂药物";
    },
    
    // 获取饮食相关信息
    getDietInfo() {
      return "【健康饮食建议】\n\n1. 均衡营养\n- 每天摄入12种以上食物，每周25种以上\n- 适量摄入蛋白质（鱼、肉、蛋、奶、豆类）\n- 多吃新鲜蔬菜水果\n\n2. 合理搭配\n- 粗细搭配，增加全谷物摄入\n- 控制油盐糖摄入量\n- 足量饮水，成人每天7-8杯\n\n3. 饮食习惯\n- 规律三餐，避免暴饮暴食\n- 细嚼慢咽，控制进食速度\n- 注意饮食卫生";
    },
    
    // 获取运动相关信息
    getExerciseInfo() {
      return "【科学运动建议】\n\n1. 运动频率\n- 每周至少150分钟中等强度有氧运动\n- 或每周至少75分钟高强度有氧运动\n- 每周2-3次抗阻训练\n\n2. 运动类型\n- 有氧运动：快走、慢跑、游泳、骑车\n- 抗阻训练：举重、俯卧撑、深蹲\n- 柔韧性训练：瑜伽、太极\n\n3. 注意事项\n- 运动前充分热身\n- 循序渐进，量力而行\n- 运动后适当拉伸\n- 有慢性疾病者需咨询医生";
    },
    
    // 体检准备相关信息
    getPhysicalExamPrepInfo() {
      return "体检前请注意以下事项：\n\n【饮食方面】\n1. 体检前一天晚上8点后不再进食，12点后不再饮水\n2. 体检当日早晨请勿进食、喝水或服用药物\n3. 抽血检查前需空腹8-12小时\n\n【生活习惯】\n1. 前一天避免剧烈运动和情绪激动\n2. 保证充足睡眠\n3. 女性应避开月经期\n\n【其他事项】\n1. 有慢性疾病史或正在服用药物的患者，请携带相关资料\n2. 怀孕期间避免X光检查\n3. 带好身份证等有效证件";
    }
  },
  
  mounted() {
    this.scrollToBottom();
  }
};
</script>

<style scoped lang="scss">
.ai-chat-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
  border-radius: 8px;
  overflow: hidden;
}

.chat-header {
  padding: 20px;
  background-color: white;
  border-bottom: 1px solid #ebeef5;
  
  h2 {
    margin: 0 0 10px 0;
    color: #333;
  }
  
  p {
    margin: 0;
    color: #666;
    font-size: 14px;
  }
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.message {
  display: flex;
  max-width: 80%;
  
  &.user-message {
    align-self: flex-end;
    
    .message-content {
      background-color: #409eff;
      color: white;
      border-radius: 15px 5px 15px 15px;
    }
  }
  
  &.ai-message {
    align-self: flex-start;
    
    .message-content {
      background-color: white;
      color: #333;
      border-radius: 5px 15px 15px 15px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }
  }
}

.message-content {
  padding: 12px 16px;
  
  .message-text {
    white-space: pre-line;
    line-height: 1.5;
  }
  
  .message-time {
    font-size: 12px;
    text-align: right;
    margin-top: 5px;
    opacity: 0.7;
  }
}

.chat-input {
  padding: 20px;
  background-color: white;
  border-top: 1px solid #ebeef5;
  
  .chat-actions {
    margin-top: 15px;
    display: flex;
    gap: 10px;
    justify-content: flex-end;
  }
}
</style>