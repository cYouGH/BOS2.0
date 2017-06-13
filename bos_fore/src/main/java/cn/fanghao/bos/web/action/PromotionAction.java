package cn.fanghao.bos.web.action;

import cn.fanghao.bos.domain.contant.Constants;
import cn.fanghao.bos.domain.page.PageBean;
import cn.fanghao.bos.domain.take_delivery.Promotion;
import com.opensymphony.xwork2.ActionContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import sun.security.krb5.Config;


import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eggdog on 2017/6/13.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class PromotionAction extends BaseAction<Promotion> {

    @Action(value = "promotion_pageQuery",results = {@Result(
            name = "success",type = "json")})
    public String pageQuery() {
        System.out.println("page="
                + page + "&rows=" + rows);
        //基于WebService 获取bos_management的 活动列表 数据信息
        PageBean<Promotion> pageBean = WebClient
                .create("http://localhost:9001/bos_management/services/promotionService/pageQuery?page="
                        + page + "&rows=" + rows)
                .accept(MediaType.APPLICATION_JSON).get(PageBean.class);

        ActionContext.getContext().getValueStack().push(pageBean);
        return SUCCESS;
    }

    @Action(value = "promotion_showDetail")
    public String showDetail() throws Exception {
       /* // 先 判断 id 对应的HTML是否存在，如果存在 就直接返回
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/freemarker");
        File htmlFile = new File(htmlRealPath + "/" + model.getId() + ".html");
        // 如果html文件不存在，则查询数据库，结合freemaker生成页面
        if (!htmlFile.exists()) {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
            configuration.setDirectoryForTemplateLoading(
                    new File(ServletActionContext.getServletContext()
                            .getRealPath("/WEB-INF/freemarker_templates")));
            // 获取模板对象
            Template template = configuration.getTemplate("promotion_detail.ftl");
            // 获取页面 动态数据
            Promotion promotion = WebClient.create("http://localhost:9001/bos_management/services/promotionService/promotion/" + model.getId())
                    .accept(MediaType.APPLICATION_JSON).get(Promotion.class);
            Map<String, Object> parameterMap = new HashMap<String, Object>();

            // 合并输出
            template.process(parameterMap, new OutputStreamWriter(
                    new FileOutputStream(htmlFile), "utf-8"));
        }*/





        // 先判断 id 对应html 是否存在，如果存在 直接返回
        String htmlRealPath = ServletActionContext.getServletContext()
                .getRealPath("/freemarker");
        File htmlFile = new File(htmlRealPath + "/" + model.getId() + ".html");

        // 如果html文件不存在 ，查询数据库，结合freemarker模板生成 页面
        if (!htmlFile.exists()) {
            Configuration configuration = new Configuration(
                    Configuration.VERSION_2_3_22);
            configuration.setDirectoryForTemplateLoading(new File(
                    ServletActionContext.getServletContext().getRealPath(
                            "/WEB-INF/freemarker_templates")));
            // 获取模板对象
            Template template = configuration
                    .getTemplate("promotion_detail.ftl");

            // 动态数据
            Promotion promotion = WebClient
                    .create(Constants.BOS_MANAGEMENT_URL
                            + "/bos_management/services/promotionService/promotion/"
                            + model.getId()).accept(MediaType.APPLICATION_JSON)
                    .get(Promotion.class);
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("promotion", promotion);

            // 合并输出
            template.process(parameterMap, new OutputStreamWriter(
                    new FileOutputStream(htmlFile), "utf-8"));

        }

        // 存在，则直接将文件返回
        ServletActionContext.getResponse().setContentType("text/html;charset:utf-8");
        FileUtils.copyFile(htmlFile,ServletActionContext.getResponse().getOutputStream());
        return NONE;
    }

}
