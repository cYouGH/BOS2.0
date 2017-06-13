package cn.fanghao.bos.web.action.base;

import cn.fanghao.bos.domain.base.Area;
import cn.fanghao.bos.service.base.AreaService;
import cn.fanghao.bos.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eggdog on 2017/6/3.
 */

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope(value = "prototype")
public class AreaAction extends BaseAction<Area>{

/*    private Area area = new Area();

    @Override
    public Area getModel() {
        return area;
    }*/

    //注入业务层对象
    @Autowired
    private AreaService areaService;

    //接收上传文件
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Action(value = "area_batchImport")
    public String batchImport() throws IOException {
        List<Area> areas = new ArrayList<Area>();
        // 编写解析代码逻辑
        // 基于。xls格式解析HSSF
        // 1.加载Excel文件对象
        HSSFWorkbook hssfWorkbook
                = new HSSFWorkbook(new FileInputStream(file));
        //读取一个sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //3.读取sheet中的每一行
        for (Row row : sheet) {
            // 一行数据，对应一个区域对象
            if (row.getRowNum() == 0) {
                //第一行 跳过
                continue;
            }
            // 跳过空行
            if (row.getCell(0) == null ||
                    StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());

            //基于pinyin4j生成城市编码和简码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            //简码
            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer sb = new StringBuffer();
            for (String s : headArray) {
                sb.append(s);
            }

            String shortCode = sb.toString();
            area.setShortcode(shortCode);
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);
            areas.add(area);
        }
        areaService.saveBatch(areas);

        return NONE;
    }



    //分页列表查询
    @Action(value = "area_pageQuery",
            results = {@Result(name = "success", type = "json")})

    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows);

        //根据查询条件，构造Specification 条件查询对象（类似于Hibernate的QBC查询）
        Specification<Area> specification = new Specification<Area>() {
            /**
             * 构造条件查询方法，如果方法返回null，代表无条件查询
             * Root 参数 获取条件表达式 name=？， age=？
             * CriteriaQuery 参数，构造简单查询条件返回，提供where方法
             * CriteriaBuilder 参数 构造Perdicate对象，条件对象，构造复杂查询效果
             */
            @Override
            public Predicate toPredicate(Root<Area> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {

                //当前查询Root根对象Courier
                List<Predicate> list = new ArrayList<Predicate>();

                //单表查询（查询当前对象 对应数据表）
                if (StringUtils.isNotBlank(model.getProvince())) {
                    //根据省份查询 模糊查询
                    //prinvince = ?
                    Predicate p1 = cb.like(root.get("province").as(String.class),
                            "%" + model.getProvince() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCity())) {
                    //根据省份查询 模糊查询
                    //prinvince = ?
                    Predicate p2 = cb.like(root.get("city").as(String.class),
                            "%" + model.getCity() + "%");
                    list.add(p2);
                }
                if (StringUtils.isNotBlank(model.getDistrict())) {
                    //根据省份查询 模糊查询
                    //prinvince = ?
                    Predicate p3 = cb.like(root.get("district").as(String.class),
                            "%" + model.getDistrict() + "%");
                    list.add(p3);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        Page<Area> pageData = areaService.findPageData(specification,pageable);

        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }

}
