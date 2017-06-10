package cn.fanghao.bos.web.action;

import cn.fanghao.bos.utils.MailUtils;
import cn.fanghao.crm.domain.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.jws.WebService;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by eggdog on 2017/6/8.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class CustomerAction extends BaseAction<Customer> {

    private String checkCode;

    @Action(value = "customer_sendSms")
    public String sendSms() {
        try {
            //手机号推荐保存在Customer对象中
            // 生成短信验证码

            //String randomCode = RandomStringUtils.randomNumeric(4);
            String randomCode = "8888";

            ServletActionContext.getRequest().getSession().
                    setAttribute(model.getTelephone(), randomCode);
            //获取用户手机号
            String telephone = model.getTelephone();
            //调用AliDaYu 短信验证

            //String result = AliCodeUtils.sendSmsByHTTP(telephone, randomCode);
            String result = "\"success\":true";

            if (result != null && result.contains("\"success\":true")) {
                return NONE;
            } else {
                throw new RuntimeException("短信发送失败：" + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    // 注入 redis
    @Autowired
    private RedisTemplate<String,String> redisTemplate;





    @Action(value = "customer_regist",
            results = {@Result(name = "success", type = "redirect",
                    location = "signup-success.html"),
                    @Result(name = "input", type = "redirect",
                            location = "signup.html")})
    public String regist() {
        // 先校验短信验证码，如果不通过，调回注册页面
        // 从session 获取之前生成的验证码
        String checkCodeSession = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
        if (checkCodeSession == null || !checkCodeSession.equals(checkCode)) {
            //验证码错误
            return INPUT;
        }
        //验证成功后  调用微博Service 连接CRM 保存客户信息
        WebClient.create("http://localhost:9002/crm_management/services/customerService/customer")
                .type(MediaType.APPLICATION_JSON).post(model);
        System.out.println("客户注册成功");

        // 发送一封激活邮件
        // 生成激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        System.out.println(model.getTelephone());
        System.out.println(activeCode);

        //将激活码保存在redis ，设置24小时失效
        redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24,
                TimeUnit.HOURS);
        //调用MailUtils发送激活邮件
        String content ="尊敬的客户您好，请于24小时内，进行邮箱账户的绑定:<br/><a href='" + MailUtils.activeUrl
                + "?telephone=" + model.getTelephone()
                + "&activeCode=" + activeCode + "'>速运快递邮箱绑定地址</a>";
        MailUtils.sendMail("速运快递激活邮件", content, model.getEmail());

        return SUCCESS;
    }

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Action("customer_activeMail")
    public String activeMail() throws IOException {
        // 防止 乱码
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");

        String activeCodeRedis = redisTemplate.opsForValue().get(model.getTelephone());
        if (activeCodeRedis == null || !activeCodeRedis.equals(activeCode)) {
            ServletActionContext.getResponse().getWriter()
                    .println("激活码无效，请重新登陆系统，进行校验！");
        }else {
            // 激活啊吗有效，避免重复绑定
            // 调用CRM WebService 查询客户端信息 ，判断是否重复绑定。
            Customer customer = WebClient
                    .create("http://localhost:9002/crm_management/services/customerService/customer/telephone/" + model.getTelephone())
                    .type(MediaType.APPLICATION_JSON).get(Customer.class);
            if (customer.getType() == null || customer.getType() != 1) {
                WebClient.create("http://localhost:9002/crm_management/services/customerService/customer/updatetype/" + model.getTelephone())
                        .type(MediaType.APPLICATION_JSON).get();
                ServletActionContext.getResponse().getWriter()
                        .println("邮箱绑定成功！");
            }else {
                ServletActionContext.getResponse().getWriter()
                        .println("邮箱已经绑定，请勿重复绑定");
            }

            redisTemplate.delete(model.getTelephone());
        }
        return NONE;
    }




}
