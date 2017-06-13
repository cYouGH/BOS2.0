package cn.fanghao.bos.dao.take_delivery;

import cn.fanghao.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by eggdog on 2017/6/11.
 */
public interface PromotionRepository extends
        JpaRepository<Promotion,Integer>,JpaSpecificationExecutor<Promotion> {

}
