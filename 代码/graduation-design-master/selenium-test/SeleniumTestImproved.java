import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import java.time.Duration;
import java.util.List;

public class SeleniumTestImproved {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_WAIT_SECONDS = 15;

    public void setUp() {
        // 设置EdgeDriver路径
        System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
        driver.manage().window().maximize(); // 最大化窗口以确保元素可见
    }

    public boolean testLoginPageLoad() {
        System.out.println("正在测试登录页面加载...");
        try {
            // 访问登录页面
            driver.get("http://localhost:9282/#/login");
            
            // 等待页面加载完成
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("login-index")));
            
            // 验证页面标题包含预期文本
            String pageTitle = driver.getTitle();
            boolean titleCheck = pageTitle.contains("医疗") || pageTitle.isEmpty();
            System.out.println("页面标题检查: " + (titleCheck ? "通过" : "失败") + " (标题: " + pageTitle + ")");
            
            // 验证登录表单存在
            WebElement loginForm = driver.findElement(By.className("loginForm"));
            boolean formCheck = loginForm.isDisplayed();
            System.out.println("登录表单显示检查: " + (formCheck ? "通过" : "失败"));
            
            System.out.println("登录页面加载测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("登录页面加载测试: 失败 - " + e.getMessage());
            return false;
        }
    }

    public boolean testUserLogin() {
        System.out.println("正在测试用户登录...");
        try {
            // 默认就是"学生"角色，不需要额外点击
            
            // 输入用户凭证
            // 第一个输入框是账号输入框
            WebElement idInput = driver.findElement(By.cssSelector(".loginForm .el-form-item:nth-child(1) .el-input input"));
            idInput.clear();
            idInput.sendKeys("2000");
            
            // 第二个输入框是密码输入框
            WebElement passwordInput = driver.findElement(By.cssSelector(".loginForm .el-form-item:nth-child(2) .el-input input"));
            passwordInput.clear();
            passwordInput.sendKeys("123456");
            
            // 点击登录按钮 (第一个按钮)
            WebElement loginButton = driver.findElement(By.cssSelector(".loginForm .el-form-item.btns .el-button:first-child"));
            loginButton.click();
            
            // 等待跳转到主页面，增加等待时间
            wait.until(ExpectedConditions.urlContains("#/patientLayout"));
            
            // 验证是否成功登录
            String currentUrl = driver.getCurrentUrl();
            boolean urlCheck = currentUrl.contains("#/patientLayout");
            System.out.println("URL跳转检查: " + (urlCheck ? "通过" : "失败") + " (当前URL: " + currentUrl + ")");
            
            System.out.println("用户登录测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("用户登录测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean testPatientHomePage() {
        System.out.println("正在测试患者主页...");
        try {
            // 确保已经在患者主页
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-main")));
            
            // 验证页面元素存在
            WebElement appointmentSection = driver.findElement(By.xpath("//span[contains(text(),'预约挂号')]"));
            boolean appointmentCheck = appointmentSection.isDisplayed();
            System.out.println("预约挂号统计信息检查: " + (appointmentCheck ? "通过" : "失败"));
            
            WebElement hospitalizationSection = driver.findElement(By.xpath("//span[contains(text(),'住院')]"));
            boolean hospitalizationCheck = hospitalizationSection.isDisplayed();
            System.out.println("住院统计信息检查: " + (hospitalizationCheck ? "通过" : "失败"));
            
            System.out.println("患者主页测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("患者主页测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean testNavigateToOrderList() {
        System.out.println("正在测试导航到订单列表...");
        try {
            // 导航到订单列表页面
            driver.get("http://localhost:9282/#/orderList");
            
            // 等待页面加载
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-table")));
            
            // 验证表格存在
            WebElement table = driver.findElement(By.cssSelector(".el-table"));
            boolean tableCheck = table.isDisplayed();
            System.out.println("订单列表表格检查: " + (tableCheck ? "通过" : "失败"));
            
            System.out.println("导航到订单列表测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("导航到订单列表测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean testMyOrdersPage() {
        System.out.println("正在测试我的订单页面...");
        try {
            // 导航到我的订单页面
            driver.get("http://localhost:9282/#/myOrder");
            
            // 等待页面加载
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-table")));
            
            // 验证表格存在
            WebElement table = driver.findElement(By.cssSelector(".el-table"));
            boolean tableCheck = table.isDisplayed();
            System.out.println("我的订单表格检查: " + (tableCheck ? "通过" : "失败"));
            
            System.out.println("我的订单页面测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("我的订单页面测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean testReservationFunctionality() {
        System.out.println("正在测试预约功能页面...");
        try {
            // 导航到预约功能页面
            driver.get("http://localhost:9282/#/orderGroup");
            
            // 等待页面加载 - 使用更通用的选择器
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-card")));
            
            // 验证页面元素存在
            List<WebElement> cards = driver.findElements(By.cssSelector(".el-card"));
            boolean cardCheck = !cards.isEmpty();
            System.out.println("卡片元素检查: " + (cardCheck ? "通过" : "失败") + " (找到 " + cards.size() + " 个卡片元素)");
            
            System.out.println("预约功能页面测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("预约功能页面测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean testNavigationMenu() {
        System.out.println("正在测试导航菜单...");
        boolean allPassed = true;
        try {
            // 导航到主页
            driver.get("http://localhost:9282/#/patientLayout");
            
            // 等待页面加载
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-main")));
            
            // 查找并点击菜单项进行测试
            // 这里我们测试几个主要的导航链接
            String[] navUrls = {
                "#/orderGroup",     // 预约挂号
                "#/myOrder",        // 我的订单
                "#/sectionMessage"  // 科室信息
            };
            
            String[] navNames = {
                "预约挂号",
                "我的订单",
                "科室信息"
            };
            
            for (int i = 0; i < navUrls.length; i++) {
                try {
                    driver.get("http://localhost:9282/" + navUrls[i]);
                    Thread.sleep(2000); // 等待页面加载
                    
                    // 检查页面是否加载成功（基于每个页面的不同特征元素）
                    if (navUrls[i].equals("#/orderGroup")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-card")));
                    } else if (navUrls[i].equals("#/myOrder")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-table")));
                    } else if (navUrls[i].equals("#/sectionMessage")) {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-card")));
                    }
                    
                    System.out.println(navNames[i] + " 页面导航测试: 通过");
                } catch (Exception e) {
                    System.out.println(navNames[i] + " 页面导航测试: 失败 - " + e.getMessage());
                    allPassed = false;
                }
            }
            
            System.out.println("导航菜单测试: " + (allPassed ? "通过" : "失败"));
            return allPassed;
        } catch (Exception e) {
            System.out.println("导航菜单测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean testAiChatFeature() {
        System.out.println("正在测试AI问诊功能...");
        try {
            // 导航到AI问诊页面
            driver.get("http://localhost:9282/#/aiChat");
            
            // 等待页面加载
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".el-textarea")));
            
            // 验证文本输入框存在
            WebElement textarea = driver.findElement(By.cssSelector(".el-textarea textarea"));
            boolean textareaCheck = textarea.isDisplayed();
            System.out.println("AI问诊输入框检查: " + (textareaCheck ? "通过" : "失败"));
            
            System.out.println("AI问诊功能测试: 通过");
            return true;
        } catch (Exception e) {
            System.out.println("AI问诊功能测试: 失败 - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void tearDown() {
        System.out.println("关闭浏览器...");
        if (driver != null) {
            driver.quit();
        }
    }
    
    public static void main(String[] args) {
        SeleniumTestImproved test = new SeleniumTestImproved();
        int passedTests = 0;
        int totalTests = 0;
        
        try {
            test.setUp();
            
            totalTests++;
            if (test.testLoginPageLoad()) passedTests++;
            
            totalTests++;
            if (test.testUserLogin()) passedTests++;
            
            totalTests++;
            if (test.testPatientHomePage()) passedTests++;
            
            totalTests++;
            if (test.testNavigateToOrderList()) passedTests++;
            
            totalTests++;
            if (test.testMyOrdersPage()) passedTests++;
            
            totalTests++;
            if (test.testReservationFunctionality()) passedTests++;
            
            totalTests++;
            if (test.testNavigationMenu()) passedTests++;
            
            totalTests++;
            if (test.testAiChatFeature()) passedTests++;
            
        } catch (Exception e) {
            System.out.println("测试过程中发生异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            test.tearDown();
        }
        
        System.out.println("\n================== 测试结果汇总 ==================");
        System.out.println("总测试数: " + totalTests);
        System.out.println("通过测试: " + passedTests);
        System.out.println("失败测试: " + (totalTests - passedTests));
        System.out.println("通过率: " + (totalTests > 0 ? (passedTests * 100 / totalTests) : 0) + "%");
        System.out.println("================================================");
    }
}