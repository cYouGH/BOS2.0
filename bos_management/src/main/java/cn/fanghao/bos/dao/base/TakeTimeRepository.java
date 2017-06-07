package cn.fanghao.bos.dao.base;

import cn.fanghao.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by eggdog on 2017/6/7.
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer>,JpaSpecificationExecutor<TakeTime> {
}
