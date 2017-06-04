package cn.fanghao.bos.web.action.base;

import cn.fanghao.bos.domain.base.Standard;
import cn.fanghao.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eggdog on 2017/6/2.
 */
@ParentPackage("json-default")
@Namespace("/")
@Actions
@Controller
@Scope(value = "prototype")
public class StandarAction extends ActionSupport implements ModelDriven<Standard> {

    //模型驱动
    private Standard standard = new Standard();

    @Override
    public Standard getModel() {
        return standard;
    }
    //注入Action 对象
    @Autowired
    private StandardService standardService;


    @Action(value = "standard_save",
            results = {@Result(name = "success",type ="redirect",
                    location = "./pages/base/standard.html")})
    public String save(){
        System.out.println("添加收派标准");
        System.out.println(standard);
        standardService.save(standard);
        return SUCCESS;
    }

    //属性驱动
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //分页列表查询
    @Action(value = "standard_pageQuery",
            results = {@Result(name = "success",type ="json")})
    public String pageQuery(){
        //调用业务层，查询数据结果

        //调用业务层，查询当前页数据
        Pageable pageable = new PageRequest(page - 1 ,rows);
        Page<Standard> pageData = standardService.findPageData(pageable);
        //返回客户端需要total和rows
        HashMap<String, Object> result = new HashMap<>();
        result.put("total",pageData.getNumberOfElements());
        result.put("rows",pageData.getContent());

        //将map转换成json数据返回，使用strut-json-plugin插件
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }

    //查询所有的收派便准方法
    @Action(value = "standard_findAll",
            results = {@Result(name = "success",type ="json")})
    public String findAll(){
        List<Standard> standards = standardService.findAll();
        ActionContext.getContext().getValueStack().push(standards);

        return SUCCESS;
    }

}
