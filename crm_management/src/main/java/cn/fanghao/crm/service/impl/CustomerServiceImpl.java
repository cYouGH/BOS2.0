package cn.fanghao.crm.service.impl;

import cn.fanghao.crm.dao.CustomerRepository;
import cn.fanghao.crm.domain.Customer;
import cn.fanghao.crm.service.CustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by eggdog on 2017/6/5.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    //注入Dao对象
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findNoAssociationCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        //解除关联动作
        customerRepository.clearFixedAreaId(fixedAreaId);
//        System.out.println(fixedAreaId+"     "+customerIdStr);

        // 切割字符串 1,2,3
        if (StringUtils.isBlank(customerIdStr)) {
            return;
        }
        String[] customerIdArray = customerIdStr.split(",");
        for (String idStr : customerIdArray) {
            Integer id = Integer.parseInt(idStr);
            customerRepository.updateFixedAreaId(fixedAreaId, id);
        }
    }
}
