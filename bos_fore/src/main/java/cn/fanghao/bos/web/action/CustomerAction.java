package cn.fanghao.bos.web.action;

import cn.fanghao.crm.domain.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

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
        return SUCCESS;
    }
}
