package cn.fanghao.bos.web.action.base;

import cn.fanghao.bos.domain.base.TakeTime;
import cn.fanghao.bos.service.base.TakeTimeService;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by eggdog on 2017/6/7.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {

    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value = "takeTime_findAll",
            results = {@Result(name = "success", type = "json")})
    public String findAll(){
        // 调用业务层，查询所有收派时间
        List<TakeTime> takeTimes = takeTimeService.findAll();

        ActionContext.getContext().getValueStack().push(takeTimes);

        return SUCCESS;
    }
}
