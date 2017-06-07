package cn.fanghao.bos.service.base.impl;

import cn.fanghao.bos.dao.base.CourierRepository;
import cn.fanghao.bos.domain.base.Courier;
import cn.fanghao.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by eggdog on 2017/6/3.
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService  {

    //注入DAO对象
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void save(Courier courier) {
    courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findPageData(Specification<Courier> specification , Pageable pageable) {
        return courierRepository.findAll(specification , pageable);
    }

    @Override
    public void delBatch(String[] idArray) {
        //调用Dao实现update修改操作，将deltag修改为1
        for (String idStr : idArray) {
            Integer id = Integer.parseInt(idStr);
            courierRepository.updateDelTag(id);


        }
        
    }

    @Override
    public List<Courier> findNoAssociation() {
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.isEmpty(root.get("fixedAreas").as(Set.class));

                return p;
            }
        };
        return courierRepository.findAll(specification);
    }
}
