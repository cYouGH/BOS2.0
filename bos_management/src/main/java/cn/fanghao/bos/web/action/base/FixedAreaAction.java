package cn.fanghao.bos.web.action.base;

import cn.fanghao.bos.domain.base.Area;
import cn.fanghao.bos.domain.base.FixedArea;
import cn.fanghao.bos.service.base.FixedAreaService;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggdog on 2017/6/4.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;


    @Action(value = "fixedArea_save",
            results = {@Result(name = "success",
                    location = "./pages/base/fixed_area.html",
                    type = "redirect")})
    public String save(){
        fixedAreaService.save(model);
        return SUCCESS;

    }

    //分页列表查询
    @Action(value = "fixedArea_pageQuery",
            results = {@Result(name = "success", type = "json")})

    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows);

        //根据查询条件，构造Specification 条件查询对象（类似于Hibernate的QBC查询）
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            /**
             * 构造条件查询方法，如果方法返回null，代表无条件查询
             * Root 参数 获取条件表达式 name=？， age=？
             * CriteriaQuery 参数，构造简单查询条件返回，提供where方法
             * CriteriaBuilder 参数 构造Perdicate对象，条件对象，构造复杂查询效果
             */
            @Override
            public Predicate toPredicate(Root<FixedArea> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {

                //当前查询Root根对象Courier
                List<Predicate> list = new ArrayList<Predicate>();

                //构造查询条件
                if (StringUtils.isNotBlank(model.getId())) {

                    Predicate p1 = cb.equal(root.get("id").as(String.class),
                            "%" + model.getId() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCompany())) {

                    Predicate p2 = cb.like(root.get("company").as(String.class),
                            "%" + model.getCompany() + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(model.getFixedAreaName())) {

                    Predicate p3 = cb.like(root.get("fixedAreaName").as(String.class),
                            "%" + model.getFixedAreaName() + "%");
                    list.add(p3);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<FixedArea> pageData = fixedAreaService.findPageData(specification,pageable);

        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }

}
