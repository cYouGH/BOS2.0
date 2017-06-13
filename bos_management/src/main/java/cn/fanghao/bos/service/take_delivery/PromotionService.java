package cn.fanghao.bos.service.take_delivery;

import cn.fanghao.bos.domain.page.PageBean;
import cn.fanghao.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;

/**
 * Created by eggdog on 2017/6/11.
 */
public interface PromotionService {
    // 保存宣传任务
    void save(Promotion promotion);
    // 分页查询
    Page<Promotion> findPageData(Pageable pageable);

    //根据Page和rows 返回分页数据
    @Path("/pageQuery")
    @GET
    @Produces({"application/xml","application/json"})
    PageBean<Promotion> findPageData(
            @QueryParam("page") int page,
            @QueryParam("rows") int rows);

    // 根据promotion 的id查询promotion
    @Path("/promotion/{id}")
    @GET
    @Produces({"application/xml","application/json"})
    Promotion findById( @PathParam("id") Integer id);
}