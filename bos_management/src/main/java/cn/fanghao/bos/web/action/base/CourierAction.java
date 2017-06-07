package cn.fanghao.bos.web.action.base;

import cn.fanghao.bos.domain.base.Courier;
import cn.fanghao.bos.domain.base.Standard;
import cn.fanghao.bos.service.base.CourierService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * Created by eggdog on 2017/6/3.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier courier = new Courier();

    @Override
    public Courier getModel() {
        return courier;
    }

    @Autowired
    private CourierService courierService;

    @Action(value = "courier_save",
            results = {@Result(name = "success", type = "redirect",
                    location = "./pages/base/courier.html")})
    public String save() {
        courierService.save(courier);
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
    @Action(value = "courier_pageQuery",
            results = {@Result(name = "success", type = "json")})

    public String pageQuery() {


        //封装pageable对象, 构造分页查询对象
        Pageable pageable = new PageRequest(page - 1, rows);

        //根据查询条件，构造Specification 条件查询对象（类似于Hibernate的QBC查询）
        Specification<Courier> specification = new Specification<Courier>() {
            /**
             * 构造条件查询方法，如果方法返回null，代表无条件查询
             * Root 参数 获取条件表达式 name=？， age=？
             * CriteriaQuery 参数，构造简单查询条件返回，提供where方法
             * CriteriaBuilder 参数 构造Perdicate对象，条件对象，构造复杂查询效果
             */
            @Override
            public Predicate toPredicate(Root<Courier> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {

                //当前查询Root根对象Courier
                List<Predicate> list = new ArrayList<Predicate>();

                //单表查询（查询当前对象 对应数据表）
                if (StringUtils.isNotBlank(courier.getCourierNum())) {
                    //进行快递员工 工号查询
                    //courierNum = ?
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class),
                            courier.getCourierNum());
                    list.add(p1);
                }


                if (StringUtils.isNotBlank(courier.getCompany())) {
                    //进行公司查询，模糊查询
                    //company = ?
                    Predicate p2 = cb.like(
                            root.get("company").as(String.class),
                            "%"+courier.getCompany()+ "%") ;
                    list.add(p2);
                }


                if (StringUtils.isNotBlank(courier.getType())) {
                    //进行快递员工 工号查询
                    //type = ?
                    Predicate p3 = cb.equal(root.get("type").as(String.class),
                            courier.getType());
                    list.add(p3);
                }

                //多表查询（查询当前对象 关联 对象 对应数据表）
                //使用Courier（Root），关联Standard
                Join<Courier , Standard> standardRoot =
                        root.join("standard",JoinType.INNER);
                if (courier.getStandard() != null
                        && StringUtils.isNotBlank(courier.getStandard().getName())) {
                    // 进行 收派标准 名称的模糊查询
                    //standard.name like % ? %
                    Predicate p4 = cb.like(
                            standardRoot.get("name").as(String.class),
                            "%" + courier.getStandard().getName() + "%");

                    list.add(p4);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Courier> pageData = courierService.findPageData(specification, pageable);
        //返回客户端需要total和rows
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", pageData.getTotalElements());
        result.put("rows", pageData.getContent());

        //将map转换成json数据返回，使用strut-json-plugin插件
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }


    //属性驱动
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courier_delBatch",
            results = {@Result(name = "success", type = "redirect",
                    location = "./pages/base/courier.html")})
    public String delBatch(){
        //按“，” 分割ids
        String[] idArray = ids.split(",");
        //调用业务层，批量作废
        courierService.delBatch(idArray);

        return SUCCESS;
    }

    // 查询未关联的定区的快递员
    @Action(value = "courier_findNoAssociation",
            results = {@Result(name = "success", type = "json")})
    public String findNoAssociation() {
        List<Courier> couriers = courierService.findNoAssociation();

        ActionContext.getContext().getValueStack().push(couriers);

        return SUCCESS;
    }
}
