<template>
  <div class="ai-chat-container">
    <div class="chat-header">
      <h2><i class="iconfont icon-r-edit" style="margin-right: 10px;"></i>AI智能问答</h2>
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
      
      <!-- 挂号表单 -->
      <div v-if="showRegistrationForm" class="registration-form">
        <el-card class="registration-card">
          <div slot="header" class="clearfix">
            <span>在线挂号</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="cancelRegistration">取消</el-button>
          </div>
          <el-form :model="registrationForm" :rules="registrationRules" ref="registrationForm" label-width="100px">
            <el-form-item label="选择科室" prop="department">
              <el-select v-model="registrationForm.department" placeholder="请选择科室" @change="loadDoctors">
                <el-option
                  v-for="dept in departments"
                  :key="dept"
                  :label="dept"
                  :value="dept">
                </el-option>
              </el-select>
              <div v-if="departmentCheckResult !== null" :class="['department-check', departmentCheckResult ? 'available' : 'unavailable']">
                {{ departmentCheckResult ? '✓ 该科室有医生上班' : '✗ 该科室暂无医生' }}
              </div>
            </el-form-item>
            
            <el-form-item label="选择医生" prop="doctorId">
              <el-select v-model="registrationForm.doctorId" placeholder="请选择医生" :disabled="!registrationForm.department || !departmentCheckResult">
                <el-option
                  v-for="doctor in doctors"
                  :key="doctor.dId"
                  :label="doctor.dName + ' (' + doctor.dPost + ')'"
                  :value="doctor.dId">
                </el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item label="选择日期" prop="date">
              <el-date-picker
                v-model="registrationForm.date"
                type="date"
                placeholder="选择日期"
                :picker-options="datePickerOptions"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
            
            <el-form-item label="选择时间" prop="timeSlot">
              <el-select v-model="registrationForm.timeSlot" placeholder="请选择时间段">
                <el-option label="上午 08:30-11:30" value="08:30-11:30"></el-option>
                <el-option label="下午 14:30-17:30" value="14:30-17:30"></el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="submitRegistration" :loading="registrationLoading">确认挂号</el-button>
              <el-button @click="cancelRegistration">取消</el-button>
            </el-form-item>
          </el-form>
        </el-card>
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
        <el-button @click="clearChat">
          清空记录
        </el-button>
        <el-button @click="loadDatabaseInfo">
          加载数据库信息
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';
import { getToken } from "@/utils/storage.js";
import jwtDecode from "jwt-decode";

export default {
  name: "AiChat",
  data() {
    return {
      inputMessage: "",
      loading: false,
      messages: [
        {
          role: "ai",
          content: "您好！我是AI健康助手，可以为您解答有关健康体检、医疗建议等相关问题。您也可以让我帮您挂号，只需说\"我要挂号\"或\"我想预约医生\"。",
          time: this.getCurrentTime()
        }
      ],
      latestAiResponse: "", // 保存最新的AI回复
      showRegistrationForm: false,
      registrationLoading: false,
      registrationForm: {
        department: '',
        doctorId: '',
        date: '',
        timeSlot: ''
      },
      registrationRules: {
        department: [
          { required: true, message: '请选择科室', trigger: 'change' }
        ],
        doctorId: [
          { required: true, message: '请选择医生', trigger: 'change' }
        ],
        date: [
          { required: true, message: '请选择日期', trigger: 'change' }
        ],
        timeSlot: [
          { required: true, message: '请选择时间段', trigger: 'change' }
        ]
      },
      departments: [],
      doctors: [],
      departmentCheckResult: null,
      datePickerOptions: {
        disabledDate(time) {
          // 禁用过去的日期和超过7天后的日期
          const today = new Date();
          const sevenDaysLater = new Date();
          sevenDaysLater.setDate(today.getDate() + 7);
          return time.getTime() < today.getTime() - 86400000 || time.getTime() > sevenDaysLater.getTime();
        }
      }
    };
  },
  methods: {
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
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
      
      // 检查是否是挂号相关请求
      if (this.isRegistrationRequest(question)) {
        this.handleRegistrationRequest();
        return;
      }
      
      // 从数据库获取真实信息并回复
      this.getAiResponse(question);
    },
    
    // 判断是否是挂号请求
    isRegistrationRequest(message) {
      // 简化判断逻辑，只要包含"挂号"就认为是挂号请求
      if (message.includes('挂号')) {
        return true;
      }
      
      // 其他触发挂号的关键词
      const regKeywords = ['我想预约', '需要预约', '进行预约', '帮我预约', '预约医生', '看病', '看医生', '门诊'];
      
      // 如果包含关键词，但不包含查询类词汇（如"谁"、"什么"、"哪里"等），则认为是挂号请求
      const queryWords = ['谁', '什么', '哪里', '哪个', '如何', '怎么', '介绍', '信息', '列表', '有哪些', '有哪些医生'];
      if (regKeywords.some(keyword => message.includes(keyword))) {
        // 如果同时包含查询词，则可能是在询问信息而非挂号
        if (queryWords.some(query => message.includes(query))) {
          return false;
        }
        return true;
      }
      
      return false;
    },
    
    // 处理挂号请求
    handleRegistrationRequest() {
      // 显示挂号表单
      this.showRegistrationForm = true;
      
       // 设置默认日期为明天
      const tomorrow = new Date();
      tomorrow.setDate(tomorrow.getDate() + 1);
      this.registrationForm.date = tomorrow.toISOString().slice(0, 10);
      
      // 设置默认时间为上午
      this.registrationForm.timeSlot = '08:30-11:30';
      
      // 获取科室列表
      console.log('开始加载科室列表');
      this.loadDepartments().then(() => {
        console.log('科室列表加载完成');
        // 在科室列表加载完成后检查AI建议
        this.applyAiDepartmentSuggestion();
      }).catch(error => {
        console.error('加载科室列表失败:', error);
      });
      
      // 添加AI回复
      this.messages.push({
        role: "ai",
        content: "好的，我可以帮您挂号。请填写以下信息完成挂号：",
        time: this.getCurrentTime()
      });
      
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },
    
    // 应用AI的科室和医生建议
    applyAiDepartmentSuggestion() {
      console.log('开始应用AI科室和医生建议');
      console.log('当前科室列表:', this.departments);
      console.log('当前医生列表:', this.doctors);
      console.log('最新的AI回复:', this.latestAiResponse);
      
      // 使用保存的最新AI回复进行匹配
      if (this.latestAiResponse) {
        console.log('检查AI消息:', this.latestAiResponse);
        
        // 多种科室匹配正则表达式
        const departmentPatterns = [
          // 匹配 "**消化内科 首选推荐**" 格式
          /\*{2}([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))[^*]*?\*{2}/g,
          // 匹配 "**1. 发热门诊**" 格式
          /\*{2}\d*[.、]?\s*([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))\*{2}/g,
          // 匹配 "**发热门诊**" 格式
          /\*{2}([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))\*{2}/g,
          // 匹配 "发热门诊" 格式（简单文本）
          /(?:推荐)?\s*(?:科室)?[:：]?\s*([^\s*。\n\r\t，,]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))/g,
          // 匹配 "建议您去发热门诊就诊" 格式
          /建议您去\s*([^\s*。\n\r\t，,]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))\s*就/g,
          // 原始模式（兜底）
          /^(?:\*\*[\d、.]\s*)?(?:\*\*)?([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))(?:\*\*)?/gm,
        ];
        
        // 多种医生匹配正则表达式
        const doctorPatterns = [
          // 匹配 "孙思邈医生" 格式
          /([\u4e00-\u9fa5]{2,5})(?:医生)/g,
          // 匹配 "- **医生**：111 (主任医师)" 格式
          /[-*]\s*\*{2}医生\*{2}[:：]?\s*([^\s，,。！!\(\)\n\r\t]{2,10})/g,
          // 原始模式
          /[-*]\s*\*\*医生\*\*：([^\s，,。！!\(\)\n\r\t]{2,10})(?:\s*\([^)]*\))?/g,
          // 匹配 "推荐医生：111" 格式
          /(?:推荐)?\s*(?:医生)?[:：]?\s*([^\s，,。！!\(\)\n\r\t]{2,10})/g,
          // 匹配 "医生111" 格式
          /医生\s*([^\s，,。！!\(\)\n\r\t]{2,10})/g,
          // 匹配 "**孙思邈医生**" 格式
          /\*{2}([\u4e00-\u9fa5]{2,5})医生\*{2}/g,
        ];
        
        let suggestedDepartment = null;
        let usedPattern = null;
        let suggestedDoctor = null;
        let usedDoctorPattern = null;
        
        // 尝试各种正则表达式匹配科室
        for (let i = 0; i < departmentPatterns.length; i++) {
          // 为全局正则表达式创建新实例，避免 lastIndex 问题
          let pattern;
          if (i === 0) {
            pattern = /\*{2}([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))[^*]*?\*{2}/g;
          } else if (i === 5) {
            pattern = /^(?:\*\*[\d、.]\s*)?(?:\*\*)?([^*。\n\r\t]{2,15}(?:科|门诊|内科|外科|妇科|儿科|眼科|耳鼻喉科|口腔科|皮肤科|精神科|肿瘤科|中医科|风湿科|传染科))(?:\*\*)?/gm;
          } else {
            pattern = new RegExp(departmentPatterns[i].source, 'g');
          }
          
          const matches = this.latestAiResponse.matchAll(pattern);
          for (const match of matches) {
            if (match[1]) {
              suggestedDepartment = match[1].trim().split(/\s/)[0]; // 只取第一个词作为科室名
              usedPattern = `Pattern ${i+1}: ${pattern.toString()}`;
              console.log('科室匹配成功，使用模式:', usedPattern, '匹配到:', suggestedDepartment);
              break;
            }
          }
          if (suggestedDepartment) break;
        }
        
        // 尝试各种正则表达式匹配医生
        for (let i = 0; i < doctorPatterns.length; i++) {
          const pattern = new RegExp(doctorPatterns[i].source, 'g');
          const matches = this.latestAiResponse.matchAll(pattern);
          for (const match of matches) {
            if (match[1]) {
              suggestedDoctor = match[1].trim(); // 去除首尾空格
              usedDoctorPattern = `Pattern ${i+1}: ${pattern.toString()}`;
              console.log('医生匹配成功，使用模式:', usedDoctorPattern, '匹配到:', suggestedDoctor);
              break;
            }
          }
          if (suggestedDoctor) break;
        }
        
        // 匹配结果变量
        let matchedDepartment = null;
        let matchedDoctor = null;
        
        // 匹配科室
        if (suggestedDepartment) {
          console.log('AI推荐科室:', suggestedDepartment);
          
          // 多级匹配方式
          // 1. 精确匹配
          for (const dept of this.departments) {
            console.log('比较科室:', dept, '与推荐科室:', suggestedDepartment);
            if (dept === suggestedDepartment) {
              matchedDepartment = dept;
              console.log('科室精确匹配成功:', dept);
              break;
            }
          }
          
          // 2. 包含匹配
          if (!matchedDepartment) {
            for (const dept of this.departments) {
              if (dept.includes(suggestedDepartment) || suggestedDepartment.includes(dept)) {
                matchedDepartment = dept;
                console.log('科室包含匹配成功:', dept);
                break;
              }
            }
          }
          
          // 3. 部分匹配（去掉"科"字后匹配）
          if (!matchedDepartment) {
            for (const dept of this.departments) {
              if (dept.replace('科', '') === suggestedDepartment.replace('科', '')) {
                matchedDepartment = dept;
                console.log('科室部分匹配成功:', dept);
                break;
              }
            }
          }
          
          // 4. 模糊匹配
          if (!matchedDepartment) {
              console.log('尝试科室模糊匹配');
            let bestMatch = null;
            let bestMatchRatio = 0;
            
            for (const dept of this.departments) {
              // 将两个字符串都转换为小写以进行不区分大小写的比较
              const lowerDept = dept.toLowerCase();
              const lowerSuggested = suggestedDepartment.toLowerCase();
              
              // 检查是否至少有一半的字符匹配
              let matchCount = 0;
              for (let i = 0; i < lowerSuggested.length; i++) {
                if (lowerDept.includes(lowerSuggested.charAt(i))) {
                  matchCount++;
                }
              }
              
              // 计算匹配度
              const matchRatio = matchCount / lowerSuggested.length;
              console.log('科室模糊匹配:', dept, 'vs', suggestedDepartment, '匹配度:', matchRatio);
              
              // 如果匹配度超过50%且比当前最佳匹配更好
              if (matchRatio > 0.5 && matchRatio > bestMatchRatio) {
                bestMatch = dept;
                bestMatchRatio = matchRatio;
              }
            }
            
            if (bestMatch) {
              matchedDepartment = bestMatch;
              console.log('科室模糊匹配成功:', bestMatch, '匹配度:', bestMatchRatio);
            }
          }
        }
        
        // 匹配医生
        if (suggestedDoctor) {
          console.log('AI推荐医生:', suggestedDoctor);
          
          // 多级医生匹配方式
          // 1. 精确匹配
          for (const doctor of this.doctors) {
            console.log('比较医生:', doctor.dName, '与推荐医生:', suggestedDoctor);
            if (doctor.dName === suggestedDoctor) {
              matchedDoctor = doctor;
              console.log('医生精确匹配成功:', doctor.dName);
              break;
            }
          }
          
          // 2. 包含匹配
          if (!matchedDoctor) {
            for (const doctor of this.doctors) {
              if (doctor.dName.includes(suggestedDoctor) || suggestedDoctor.includes(doctor.dName)) {
                matchedDoctor = doctor;
                console.log('医生包含匹配成功:', doctor.dName);
                break;
              }
            }
          }
          
          // 3. 姓名匹配（只比较前两个字符）
          if (!matchedDoctor) {
            for (const doctor of this.doctors) {
              if (doctor.dName.length >= 2 && suggestedDoctor.length >= 2 && 
                  doctor.dName.substring(0, 2) === suggestedDoctor.substring(0, 2)) {
                matchedDoctor = doctor;
                console.log('医生姓名匹配成功:', doctor.dName);
                break;
              }
            }
          }
          
          // 4. 模糊匹配
          if (!matchedDoctor) {
            console.log('尝试医生模糊匹配');
            let bestDoctorMatch = null;
            let bestDoctorMatchRatio = 0;
            
            for (const doctor of this.doctors) {
              // 将两个字符串都转换为小写以进行不区分大小写的比较
              const lowerDoctorName = doctor.dName.toLowerCase();
              const lowerSuggested = suggestedDoctor.toLowerCase();
              
              // 检查是否至少有一半的字符匹配
              let matchCount = 0;
              for (let i = 0; i < lowerSuggested.length; i++) {
                if (lowerDoctorName.includes(lowerSuggested.charAt(i))) {
                  matchCount++;
                }
              }
              
              // 计算匹配度
              const matchRatio = matchCount / lowerSuggested.length;
              console.log('医生模糊匹配:', doctor.dName, 'vs', suggestedDoctor, '匹配度:', matchRatio);
              
              // 如果匹配度超过50%且比当前最佳匹配更好
              if (matchRatio > 0.5 && matchRatio > bestDoctorMatchRatio) {
                bestDoctorMatch = doctor;
                bestDoctorMatchRatio = matchRatio;
              }
            }
            
            if (bestDoctorMatch) {
              matchedDoctor = bestDoctorMatch;
              console.log('医生模糊匹配成功:', bestDoctorMatch.dName, '匹配度:', bestDoctorMatchRatio);
            }
          }
        }
        
        console.log('科室匹配结果:', matchedDepartment);
        console.log('医生匹配结果:', matchedDoctor);
        
        // 根据匹配结果决定自动选择策略
        // 如果科室和医生都匹配成功，优先选择科室
        // 如果只有一个匹配成功，选择匹配成功的那个
        if (matchedDepartment) {
          // 科室匹配成功，设置科室
          this.$nextTick(() => {
            this.registrationForm.department = matchedDepartment;
            console.log('已设置科室:', matchedDepartment);
            
            // 加载医生列表
            setTimeout(() => {
              this.loadDoctors().then(() => {
                console.log('医生列表加载完成:', this.doctors);
                
                // 如果医生也匹配成功，则设置医生
                if (matchedDoctor) {
                  setTimeout(() => {
                    this.registrationForm.doctorId = matchedDoctor.dId;
                    console.log('已设置医生:', matchedDoctor.dName, matchedDoctor.dId);
                  }, 100);
                }
              }).catch(error => {
                console.error('加载医生列表失败:', error);
              });
            }, 100);
          });
        } else if (matchedDoctor) {
          // 只有医生匹配成功，设置医生（但需要先有科室）
          // 这种情况下我们无法直接设置医生，因为必须先选择科室
          console.log('仅有医生匹配成功，但需要先选择科室');
        }
      } else {
        console.log('没有保存的AI回复');
      }
    },
    
    // 加载科室列表
    async loadDepartments() {
      try {
        console.log('正在获取科室列表...');
        const response = await request.get('/ai/chat/departments');
        console.log('科室列表响应:', response);
        if (response.data.status === 200) {
          this.departments = response.data.data;
          console.log('获取到科室列表:', this.departments);
          return Promise.resolve(); // 返回一个 resolved 的 Promise
        } else {
          console.error('获取科室列表失败，状态码:', response.data.status);
          this.$message.error('获取科室列表失败');
          return Promise.reject(new Error('获取科室列表失败'));
        }
      } catch (error) {
        console.error('获取科室列表失败:', error);
        this.$message.error('获取科室列表失败: ' + error.message);
        return Promise.reject(error); // 返回一个 rejected 的 Promise
      }
    },
    
    // 检查科室是否有医生
    async checkDepartment(department) {
      if (!department) {
        this.departmentCheckResult = null;
        return Promise.resolve(); // 返回 resolved 的 Promise
      }
      
      try {
        console.log('正在检查科室是否有医生:', department);
        const response = await request.get('/ai/chat/checkDepartment', {
          params: { department }
        });
        console.log('检查科室响应:', response);
        
        this.departmentCheckResult = response.data.data;
        console.log('科室检查结果:', this.departmentCheckResult);
        return Promise.resolve(); // 返回 resolved 的 Promise
      } catch (error) {
        console.error('检查科室失败:', error);
        this.departmentCheckResult = null;
        return Promise.reject(error); // 返回 rejected 的 Promise
      }
    },
    
    // 加载医生列表
    async loadDoctors() {
      // 重置检查结果
      this.departmentCheckResult = null;
      this.doctors = [];
      this.registrationForm.doctorId = '';
      
      if (!this.registrationForm.department) {
        console.log('没有选择科室，跳过加载医生列表');
        return Promise.resolve(); // 返回 resolved 的 Promise
      }
      
      console.log('开始加载医生列表，科室:', this.registrationForm.department);
      
      // 检查科室是否有医生
      await this.checkDepartment(this.registrationForm.department);
      
      if (!this.departmentCheckResult) {
        console.log('科室没有医生或检查失败');
        return Promise.resolve(); // 返回 resolved 的 Promise
      }
      
      try {
        const response = await request.get('/ai/chat/doctors', {
          params: {
            department: this.registrationForm.department
          }
        });
        
        console.log('医生列表响应:', response);
        
        if (response.data.status === 200) {
          this.doctors = response.data.data;
          console.log('获取到医生列表:', this.doctors);
          console.log('医生列表数据结构:', JSON.stringify(this.doctors, null, 2));
          if (this.doctors.length === 0) {
            this.$message.warning('该科室暂无可用医生');
          }
        } else {
          this.doctors = [];
          console.error('获取医生列表失败，状态码:', response.data.status);
        }
        return Promise.resolve(); // 返回 resolved 的 Promise
      } catch (error) {
        console.error('获取医生列表失败:', error);
        this.doctors = [];
        this.$message.error('获取医生列表失败: ' + error.message);
        return Promise.reject(error); // 返回 rejected 的 Promise
      }
    },
    
    // 提交挂号
    submitRegistration() {
      this.$refs.registrationForm.validate(async (valid) => {
        if (!valid) return;
        
        this.registrationLoading = true;
        
        // 检查用户是否已登录
        const token = getToken();
        if (!token) {
          this.$message.error('请先登录再进行挂号操作');
          // 添加提示消息
          this.messages.push({
            role: "ai",
            content: "抱歉，您需要先登录才能进行挂号操作。请前往登录页面登录后再次尝试。",
            time: this.getCurrentTime()
          });
          this.registrationLoading = false;
          this.showRegistrationForm = false;
          this.$nextTick(() => {
            this.scrollToBottom();
          });
          return;
        }
        
        const patientInfo = jwtDecode(token);
        
        try {
          // 构造挂号时间
          const registrationTime = `${this.registrationForm.date} ${this.registrationForm.timeSlot}`;
          
          // 构造arId（用于检查号源）
          const arId = this.registrationForm.doctorId + this.registrationForm.date;
          
          // 提交挂号请求
          const response = await request.get('/patient/addOrder', {
            params: {
              pId: patientInfo.pId,
              dId: this.registrationForm.doctorId,
              oStart: registrationTime,
              arId: arId,
              oTotalPrice: 0 // 价格会在后端处理
            }
          });
          
          if (response.data.status === 200) {
            this.$message.success('挂号成功！');
            
            // 添加成功消息
            // 获取医生姓名
            let doctorName = '';
            for (let i = 0; i < this.doctors.length; i++) {
              if (this.doctors[i].dId == this.registrationForm.doctorId) {
                doctorName = this.doctors[i].dName;
                break;
              }
            }
            
            this.messages.push({
              role: "ai",
              content: `挂号成功！您已成功预约${this.registrationForm.date} ${this.registrationForm.timeSlot}的号。医生：${doctorName}。请按时就诊。`,
              time: this.getCurrentTime()
            });
            
            // 重置表单并隐藏
            this.resetRegistrationForm();
          } else {
            this.$message.error(response.data.message || '挂号失败');
            
            // 添加失败消息
            this.messages.push({
              role: "ai",
              content: `挂号失败：${response.data.message || '未知错误'}`,
              time: this.getCurrentTime()
            });
          }
        } catch (error) {
          console.error('挂号失败:', error);
          // 使用传统方式获取错误信息，避免可选链操作符
          var errorMessage = '未知错误';
          if (error && error.message) {
            errorMessage = error.message;
          }
          if (error && error.response && error.response.data) {
            if (error.response.data.message) {
              errorMessage = error.response.data.message;
            } else if (error.response.data.msg) {
              errorMessage = error.response.data.msg;
            } else {
              errorMessage = JSON.stringify(error.response.data);
            }
          }
          this.$message.error('挂号失败：' + errorMessage);
          
          // 添加失败消息
          this.messages.push({
            role: "ai",
            content: `挂号失败：${errorMessage}`,
            time: this.getCurrentTime()
          });
        }
        
        this.registrationLoading = false;
        this.showRegistrationForm = false;
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      });
    },
    
    // 取消挂号
    cancelRegistration() {
      this.showRegistrationForm = false;
      this.resetRegistrationForm();
      
      this.messages.push({
        role: "ai",
        content: "已取消挂号操作。如果您还需要其他帮助，请随时告诉我。",
        time: this.getCurrentTime()
      });
      
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },
    
    // 重置挂号表单
    resetRegistrationForm() {
      this.$refs.registrationForm.resetFields();
      this.doctors = [];
      this.departmentCheckResult = null;
    },
    
    // 加载数据库信息供AI学习
    async loadDatabaseInfo() {
      this.loading = true;
      try {
        const response = await request.get('/ai/chat/structuredInfo');
        if (response.data.status === 200) {
          // 将数据库信息添加到对话历史中
          const dbInfo = JSON.stringify(response.data.data, null, 2);
          this.messages.push({
            role: "ai",
            content: `我已经学习了医院的数据库信息，包括科室和医生信息。现在我可以更好地为您服务了。\n\n数据库信息预览：\n${dbInfo.substring(0, 500)}...`,
            time: this.getCurrentTime()
          });
          this.$message.success('数据库信息加载成功');
        }
      } catch (error) {
        console.error('加载数据库信息失败:', error);
        this.$message.error('加载数据库信息失败');
      } finally {
        this.loading = false;
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
    
    // 根据科室名称查询医生信息
    async queryDoctorsByDepartment(departmentName) {
      try {
        const response = await request.get('/ai/chat/doctors', {
          params: { department: departmentName }
        });
        
        if (response.data.status === 200) {
          const doctors = response.data.data;
          if (doctors && doctors.length > 0) {
            let doctorInfo = `【${departmentName}】的医生有：\n\n`;
            
            doctors.forEach(doctor => {
              doctorInfo += `${doctor.dName} ${doctor.dPost || ''}\n`;
              if (doctor.dIntroduction) {
                doctorInfo += `简介：${doctor.dIntroduction}\n`;
              }
              doctorInfo += "\n";
            });
            
            return doctorInfo;
          } else {
            return `目前${departmentName}暂无医生信息。`;
          }
        } else {
          return `暂时无法获取${departmentName}的医生信息，请稍后再试。`;
        }
      } catch (error) {
        console.error('获取医生信息失败:', error);
        return `暂时无法获取${departmentName}的医生信息，请稍后再试。`;
      }
    },
    
    // 获取完整的数据库信息
    async getFullDatabaseInfo() {
      let dbInfo = "【医院完整数据库信息】\n";
      try {
        // 获取科室和医生信息
        const deptResponse = await request.get('/ai/chat/departments');
        if (deptResponse.data.status === 200) {
          const departments = deptResponse.data.data;
          dbInfo += "\n科室列表：\n";
          
          // 获取每个科室的医生信息
          for (let i = 0; i < departments.length; i++) {
            const deptName = departments[i];
            dbInfo += `\n${deptName}科室:\n`;
            
            try {
              const doctorResponse = await request.get('/ai/chat/doctors', {
                params: { department: deptName }
              });
              
              if (doctorResponse.data.status === 200 && doctorResponse.data.data.length > 0) {
                const doctors = doctorResponse.data.data;
                doctors.forEach(doctor => {
                  dbInfo += `- ${doctor.dName} (${doctor.dPost || '医生'})`;
                  if (doctor.dIntroduction) {
                    dbInfo += ` - ${doctor.dIntroduction}`;
                  }
                  dbInfo += "\n";
                });
              } else {
                dbInfo += "  暂无医生信息\n";
              }
            } catch (doctorError) {
              console.error(`获取${deptName}科室医生信息失败:`, doctorError);
              dbInfo += "  获取医生信息失败\n";
            }
          }
        }
        
        // 获取体检项目信息
        try {
          const checkResponse = await request.get('/check/findAllChecks', {
            params: {
              pageNumber: 1,
              size: 100, // 获取前100个项目
              query: '',
              chId: ''
            }
          });
          
          if (checkResponse.data.status === 200 && checkResponse.data.data.list) {
            const checks = checkResponse.data.data.list;
            dbInfo += "\n\n体检项目列表：\n";
            
            // 按科室分组显示
            const groupedChecks = {};
            checks.forEach(check => {
              if (!groupedChecks[check.kname]) {
                groupedChecks[check.kname] = [];
              }
              groupedChecks[check.kname].push(check);
            });
            
            for (const [kname, checkList] of Object.entries(groupedChecks)) {
              dbInfo += `\n${kname}科室:\n`;
              checkList.forEach(check => {
                dbInfo += `- ${check.chName} (价格: ¥${check.chPrice})\n`;
              });
            }
          }
        } catch (error) {
          console.error('获取体检项目失败:', error);
        }
        
        // 获取体检套餐信息
        try {
          const setmealResponse = await request.get('/setmeal/findAllSetmeals', {
            params: {
              pageNumber: 1,
              size: 100, // 获取前100个套餐
              query: ''
            }
          });
          
          if (setmealResponse.data.status === 200 && setmealResponse.data.data.list) {
            const setmeals = setmealResponse.data.data.list;
            dbInfo += "\n\n体检套餐列表：\n";
            
            setmeals.forEach(setmeal => {
              dbInfo += `\n套餐名称：${setmeal.tName}\n`;
              dbInfo += `套餐价格：¥${setmeal.tPrice}\n`;
              dbInfo += `适用性别：${setmeal.tGender}\n`;
              dbInfo += `套餐描述：${setmeal.tDescribe}\n`;
            });
          }
        } catch (error) {
          console.error('获取体检套餐失败:', error);
        }
        
        dbInfo += "\n\n以上是从医院数据库中查询到的完整信息。";
        return dbInfo;
      } catch (error) {
        console.error('获取数据库信息失败:', error);
        return "获取数据库信息失败，请稍后再试。";
      }
    },
    
    async getAiResponse(question) {
      this.loading = true;
      
      try {
        // 构建对话历史用于DeepSeek API
        const messages = [
          {
            role: "system",
            content: "你是一个专业的健康体检助手，为用户提供医疗和体检相关的建议。系统会在每次对话时提供医院的完整数据库信息，包括科室、医生、体检项目和套餐等信息。\n\n回答时请遵循以下原则：\n1. 回答要专业、准确、详细\n2. 使用清晰的格式和结构化信息\n3. 如涉及具体医疗建议，请提醒用户以医生诊断为准\n4. 适当使用emoji让回答更生动\n5. 基于系统提供的数据库信息回答用户问题，不要编造信息\n6. 如果用户想挂号或预约医生，引导他们使用挂号功能\n7. 在推荐科室时，确保该科室在数据库中有医生上班\n8. 每次回答都应参考系统提供的最新数据库信息\n9. 数据库信息会作为上下文提供给您，请仔细阅读并基于这些信息回答用户问题"
          }
        ];
        
        // 添加历史对话（最多5轮）
        const historyMessages = this.messages.slice(-10); // 取最近10条消息（5轮对话）
        historyMessages.forEach(msg => {
          messages.push({
            role: msg.role === 'user' ? 'user' : 'assistant',
            content: msg.content
          });
        });
        
        // 添加当前用户问题
        messages.push({
          role: "user",
          content: question
        });
        
        // 每次提问都将除病人以外的数据库所有信息提供给AI
        const dbInfo = await this.getFullDatabaseInfo();
        
        // 如果有数据库信息，添加到系统提示中
        if (dbInfo) {
          messages[0].content += "\n\n以下是医院的数据库信息，请基于这些信息回答用户问题：\n" + dbInfo;
        }
        
        // 调用DeepSeek API
        const deepSeekResponse = await fetch('https://api.deepseek.com/v1/chat/completions', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer sk-21a30b6d0ea44c82bec57a7ac5182e27' // 使用您提供的API Key
          },
          body: JSON.stringify({
            model: "deepseek-chat",
            messages: messages,
            stream: false,
            temperature: 0.7
          })
        });
        
        if (deepSeekResponse.ok) {
          const data = await deepSeekResponse.json();
          const response = data.choices[0].message.content;
          
          // 保存AI回复
          this.latestAiResponse = response;
          
          // 添加AI回复
          this.messages.push({
            role: "ai",
            content: response,
            time: this.getCurrentTime()
          });
        } else {
          // DeepSeek API调用失败，回退到原有逻辑
          console.log('DeepSeek API调用失败，回退到基于规则的响应');
          await this.fallbackToRuleBasedResponse(question);
          return; // 确保不会继续执行下面的代码
        }
      } catch (error) {
        console.error('AI回复出错:', error);
        // 出错时回退到原有逻辑
        this.fallbackToRuleBasedResponse(question);
      } finally {
        this.loading = false;
        
        // 滚动到底部
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },
    
    // 回退到基于规则的响应（原有逻辑）
    async fallbackToRuleBasedResponse(question) {
      let response = "";

      // 根据问题类型提供更智能的回答
      if ((question.includes("体检") && question.includes("注意")) || (question.includes("体检") && question.includes("准备")) || (question.includes("检查") && !question.includes("项目"))) {
        response = "体检前请注意以下事项：\n\n【饮食方面】\n1. 体检前一天晚上8点后不再进食，12点后不再饮水\n2. 体检当日早晨请勿进食、喝水或服用药物\n3. 抽血检查前需空腹8-12小时\n\n【生活习惯】\n1. 前一天避免剧烈运动和情绪激动\n2. 保证充足睡眠\n3. 女性应避开月经期\n\n【其他事项】\n1. 有慢性疾病史或正在服用药物的患者，请携带相关资料\n2. 怀孕期间避免X光检查\n3. 带好身份证等有效证件";
      } else if (question.includes("血压")) {
        response = "【血压基础知识】\n正常血压范围：收缩压90-140mmHg，舒张压60-90mmHg\n\n【高血压建议】\n1. 控制食盐摄入量，每日不超过6克\n2. 保持适当体重，戒烟限酒\n3. 增加运动，保持心情愉快\n4. 定期监测血压，遵医嘱服药\n5. 多吃新鲜蔬菜水果，减少高脂肪食物\n\n【低血压建议】\n1. 适当增加盐的摄入\n2. 多喝水保持充足血容量\n3. 避免久站或突然起立\n4. 适当运动增强心血管功能";
      } else if (question.includes("血糖")) {
        response = "【血糖参考值】\n空腹血糖：3.9-6.1mmol/L\n餐后2小时血糖：<7.8mmol/L\n糖化血红蛋白(HbA1c)：<6.5%\n\n【血糖异常调理】\n1. 控制饮食，少食多餐，避免高糖食物\n2. 规律运动，增强胰岛素敏感性\n3. 定期监测血糖变化\n4. 遵医嘱用药，不可自行停药\n5. 控制体重，保持良好作息";
      } else if (question.includes("胆固")) {
        response = "【胆固醇参考值】\n总胆固醇：<5.2mmol/L\n低密度脂蛋白胆固醇(LDL-C)：<3.4mmol/L\n高密度脂蛋白胆固醇(HDL-C)：>1.0mmol/L\n甘油三酯：<1.7mmol/L\n\n【胆固醇异常调理】\n1. 减少饱和脂肪酸和胆固醇的摄入\n2. 多吃蔬菜水果和全谷物\n3. 保持规律运动\n4. 控制体重，戒烟限酒\n5. 遵医嘱服用降脂药物";
      } else if (question.includes("饮食") || question.includes("营养") || question.includes("吃")) {
        response = "【健康饮食建议】\n\n1. 均衡营养\n- 每天摄入12种以上食物，每周25种以上\n- 适量摄入蛋白质（鱼、肉、蛋、奶、豆类）\n- 多吃新鲜蔬菜水果\n\n2. 合理搭配\n- 粗细搭配，增加全谷物摄入\n- 控制油盐糖摄入量\n- 足量饮水，成人每天7-8杯\n\n3. 饮食习惯\n- 规律三餐，避免暴饮暴食\n- 细嚼慢咽，控制进食速度\n- 注意饮食卫生";
      } else if (question.includes("运动") || question.includes("锻炼")) {
        response = "【科学运动建议】\n\n1. 运动频率\n- 每周至少150分钟中等强度有氧运动\n- 或每周至少75分钟高强度有氧运动\n- 每周2-3次抗阻训练\n\n2. 运动类型\n- 有氧运动：快走、慢跑、游泳、骑车\n- 抗阻训练：举重、俯卧撑、深蹲\n- 柔韧性训练：瑜伽、太极\n\n3. 注意事项\n- 运动前充分热身\n- 循序渐进，量力而行\n- 运动后适当拉伸\n- 有慢性疾病者需咨询医生";
      } else if ((question.includes("体检") || question.includes("检查")) && question.includes("项目")) {
        // 从数据库获取真实的体检项目信息
        try {
          const checkResponse = await request.get('/check/findAllChecks', {
            params: {
              pageNumber: 1,
              size: 100, // 获取前100个项目
              query: '',
              chId: ''
            }
          });

          if (checkResponse.data.status === 200 && checkResponse.data.data.list) {
            const checks = checkResponse.data.data.list;
            response = "【医院体检项目列表】\n\n";

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

            response += "如需了解某个项目的详细信息，请询问具体项目名称。";
          } else {
            // 如果获取失败，使用默认回复
            response = "【常规体检项目】\n\n基础检查：\n- 身高、体重、血压、脉搏\n\n实验室检查：\n- 血常规、尿常规、便常规\n- 生化检查（肝功、肾功、血糖、血脂等）\n- 肿瘤标志物筛查\n\n影像学检查：\n- 心电图\n- 胸部X光或CT\n- 腹部B超\n\n专科检查：\n- 内科、外科、眼科、耳鼻喉科\n- 口腔科检查\n\n建议根据年龄、性别、职业和既往病史选择个性化检查项目";
          }
        } catch (error) {
          console.error('获取体检项目失败:', error);
          // 如果请求失败，使用默认回复
          response = "【常规体检项目】\n\n基础检查：\n- 身高、体重、血压、脉搏\n\n实验室检查：\n- 血常规、尿常规、便常规\n- 生化检查（肝功、肾功、血糖、血脂等）\n- 肿瘤标志物筛查\n\n影像学检查：\n- 心电图\n- 胸部X光或CT\n- 腹部B超\n\n专科检查：\n- 内科、外科、眼科、耳鼻喉科\n- 口腔科检查\n\n建议根据年龄、性别、职业和既往病史选择个性化检查项目";
        }
      } else if (question.includes("套餐") && question.includes("体检")) {
        // 从数据库获取真实的体检套餐信息
        try {
          const setmealResponse = await request.get('/setmeal/findAllSetmeals', {
            params: {
              pageNumber: 1,
              size: 100, // 获取前100个套餐
              query: ''
            }
          });

          if (setmealResponse.data.status === 200 && setmealResponse.data.data.list) {
            const setmeals = setmealResponse.data.data.list;
            response = "【医院体检套餐列表】\n\n";

            setmeals.forEach(setmeal => {
              response += `套餐名称：${setmeal.tName}\n`;
              response += `套餐价格：¥${setmeal.tPrice}\n`;
              response += `适用性别：${setmeal.tGender}\n`;
              response += `套餐描述：${setmeal.tDescribe}\n\n`;
            });

            response += "如需了解某个套餐的详细信息，请询问具体套餐名称。";
          } else {
            // 如果获取失败，使用默认回复
            response = "我们提供多种体检套餐，包括基础套餐、全面套餐、专项检查套餐等。\n\n建议根据您的年龄、性别、职业和健康状况选择合适的套餐。\n\n请前往体检中心或咨询医生获取最新的套餐信息。";
          }
        } catch (error) {
          console.error('获取体检套餐失败:', error);
          // 如果请求失败，使用默认回复
          response = "我们提供多种体检套餐，包括基础套餐、全面套餐、专项检查套餐等。\n\n建议根据您的年龄、性别、职业和健康状况选择合适的套餐。\n\n请前往体检中心或咨询医生获取最新的套餐信息。";
        }
      } else if (this.isRegistrationRequest(question)) {
        // 如果是挂号请求，引导用户使用挂号功能
        response = "我理解您想要挂号。请点击下方的\"我要挂号\"按钮，我会引导您完成挂号流程。您需要提供科室、医生、日期和时间段等信息。";
        this.handleRegistrationRequest();
        // 注意：这里不添加消息到messages数组，因为handleRegistrationRequest已经处理了
        return;
      } else {
        // 默认回复
        response = "感谢您的提问！作为AI健康助手，我可以为您提供以下方面的建议：\n\n🩺 体检注意事项和流程\n🩸 常见健康指标解读（血压、血糖、胆固醇等）\n🥗 健康饮食和营养指导\n🏃 健康生活方式和运动建议\n📋 体检项目和套餐信息\n📋 科室和医生信息\n🎫 挂号预约服务\n\n请尽量详细描述您的问题，比如\"体检前需要注意什么\"、\"高血压如何调理\"、\"有哪些体检项目\"、\"体检套餐有哪些\"、\"发热门诊有哪些医生\"、\"我要挂号\"等，我会为您提供更精准的解答！";
      }

      // 保存AI回复
      this.latestAiResponse = response;
      
      // 添加AI回复
      this.messages.push({
        role: "ai",
        content: response,
        time: this.getCurrentTime()
      });

      this.loading = false;

      // 滚动到底部
      this.$nextTick(() => {
        this.scrollToBottom();
      });
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

.registration-form {
  align-self: center;
  width: 100%;
  max-width: 500px;
  margin: 10px 0;
}

.registration-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.department-check {
  margin-top: 5px;
  font-size: 14px;
  
  &.available {
    color: #67c23a;
  }
  
  &.unavailable {
    color: #f56c6c;
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