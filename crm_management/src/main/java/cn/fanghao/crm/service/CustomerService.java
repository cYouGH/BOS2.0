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
    @Path("/noassociationcustomers")
    @GET
    @Produces({"application/xml","application/json"})
     List<Customer> findNoAssociationCustomers();

    //已经关联到指定定区的客户列表
    @Path("/associationfixedareacustomers/{fixedareaid}")
    @GET
    @Produces({"application/xml","application/json"})
      List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedareaid") String fixedAreaId);

    //将客户关联到定区上
    @Path("/associationcustomerstofixedarea")
    @PUT
     void associationCustomersToFixedArea(
            @QueryParam("customerIdStr") String customerIdStr,
            @QueryParam("fixedAreaId") String fixedAreaId);

    @Path("/customer")
    @POST
    @Consumes({"application/xml","application/json"})
     void regist(Customer customer);

    @Path("/customer/telephone/{telephone}")
    @GET
    @Consumes({"application/xml","application/json"})
     Customer findByTelephone(@PathParam("telephone") String telephone);

    @Path("/customer/updatetype/{telephone}")
    @GET
    void updateType(@PathParam("telephone") String telephone);
}
