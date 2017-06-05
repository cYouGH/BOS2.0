package cn.fanghao.crm.service.test;

import cn.fanghao.crm.domain.Customer;
import cn.fanghao.crm.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by eggdog on 2017/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;
    @Test
    public void findNoAssociationCustomers() throws Exception {
        List<Customer> customers = customerService.findNoAssociationCustomers();
        System.out.println(customers);
    }

    @Test
    public void findHasAssociationFixedAreaCustomers() throws Exception {
        List<Customer> customers = customerService.findHasAssociationFixedAreaCustomers("dp001");
        System.out.println(customers);
    }

    @Test
    public void associationCustomersToFixedArea() throws Exception {
        customerService.associationCustomersToFixedArea("1,2", "dp001");
    }

}