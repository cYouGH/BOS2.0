package cn.fanghao.bos.domain.page;

import cn.fanghao.bos.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * 自定义分页数据对象；
 * Created by eggdog on 2017/6/13.
 */
@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    private Long totalCount;    //总记录数
    private List<T> pageData;   // 当前页数据

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
