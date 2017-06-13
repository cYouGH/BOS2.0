package cn.fanghao.bos.web.action.take_delivery;

import cn.fanghao.bos.domain.take_delivery.Promotion;
import cn.fanghao.bos.service.take_delivery.PromotionService;
import cn.fanghao.bos.web.action.base.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by eggdog on 2017/6/11.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class PromotionAction extends BaseAction<Promotion> {

    private File titleImgFile;
    private String titleImgFileFileName;

    @Autowired
    private PromotionService promotionService;

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    @Action(value = "promotion_save",results = {@Result(
            name = "success",type = "redirect",location = "./pages/take_delivery/promotion.html")})
    public String save() throws IOException {
        // 宣传图上传、在数据表保存，宣传图路径
        //文件保存路径
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload/");
        //文件保存的绝对路径
        String saveUrl = ServletActionContext.getRequest().getContextPath() + "/upload/";


        UUID uuid = UUID.randomUUID();
        //获取文件格式
        String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
        String randomFileName = uuid + ext;

        // 保存图片（绝对路径）
        File destFile = new File(savePath + "/" + randomFileName);
        FileUtils.copyFile(titleImgFile,destFile);

        // 将保存路径 相对工程web访问路径，保存在model中。
        model.setTitleImg(ServletActionContext.getRequest().getContextPath() + "/upload/"+ randomFileName);

        //调用PromotionService
        promotionService.save(model);
        return SUCCESS;
    }

    @Action(value = "promotion_pageQuery",results = {@Result(
            name = "success",type = "json")})
    public String pageQuery() {
        //构造分页查询参数
        Pageable pageable = new PageRequest(page - 1, rows);
        // 调用业务层完成查询
        Page<Promotion> pageData = promotionService.findPageData(pageable);

        pushPageDataToValueStack(pageData);
        return SUCCESS;
    }
}
