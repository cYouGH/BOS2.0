package cn.fanghao.bos.web.action.take_delivery;


import cn.fanghao.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 处理Kindeditor图片上传 、管理
 * Created by eggdog on 2017/6/11.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class ImageAction extends BaseAction<Object>{

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    @Action(value = "image_upload",results = {@Result(name = "success",type = "json")})
    public String upload() throws IOException {
        System.out.println("---------------");
        System.out.println(imgFile + "    "+imgFileFileName+"    " +imgFileContentType );

        //文件保存路径
        String savePath = ServletActionContext.getServletContext().getRealPath("/upload/");
        //文件保存的绝对路径
        String saveUrl = ServletActionContext.getRequest().getContextPath() + "/upload/";

        //生成随机图片名
        UUID uuid = UUID.randomUUID();
        //获取文件格式
        String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
        String randomFileName = uuid + ext;

        // 保存图片（绝对路径）
        FileUtils.copyFile(imgFile,new File(savePath +"/"+ randomFileName));


        //通知浏览器 上传结果
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error",0);
        result.put("url",saveUrl + randomFileName);
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    @Action(value = "image_manager",results = {@Result(name = "success",type = "json")})
    public String manager(){

        //文件保存路径
        String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "upload";
        //文件保存的绝对路径
        String rootUrl = ServletActionContext.getRequest().getContextPath() + "/upload/";
        //图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        List<Map> fileList = new ArrayList<Map>();

        File currentPathFile = new File(rootPath);
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Map<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", rootPath);
        result.put("current_url", rootUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }
}
