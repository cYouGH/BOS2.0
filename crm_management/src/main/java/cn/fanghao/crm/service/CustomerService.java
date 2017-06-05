package cn.fanghao.crm.service;

import cn.fanghao.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

/**
 * 客户操作
 * Created by eggdog on 2017/6/5.
 */
public interface CustomerService {


    //查询所有未关联的客户列表
    @Path("/noassciationcustomers")
    @GET
    @Produces({"application/xml","application/json"})
     List<Customer> findNoAssociationCustomers();

    //已经关联到指定定区的客户列表
    @Path("/assciationfixedareacustomers/{fixedareaid}")
    @GET
    @Produces({"application/xml","application/json"})
      List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedareaid") String fixedAreaId);

    //将客户关联到定区上
    @Path("/assciationcustomerstofixedarea")
    @PUT
     void associationCustomersToFixedArea(
            @QueryParam("customerIdStr") String customerIdStr,
            @QueryParam("fixedAreaId") String fixedAreaId);

}
