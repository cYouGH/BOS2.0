package cn.fanghao.bos.service.base;

import cn.fanghao.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by eggdog on 2017/6/3.
 */
public interface CourierService {
    void save(Courier courier);

    Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable);

    void delBatch(String[] idArray);

    List<Courier> findNoAssociation();
}
