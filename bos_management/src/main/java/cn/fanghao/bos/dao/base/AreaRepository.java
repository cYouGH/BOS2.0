package cn.fanghao.bos.dao.base;

import cn.fanghao.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by eggdog on 2017/6/4.
 */
public interface AreaRepository extends
        JpaRepository<Area,String>
        ,JpaSpecificationExecutor<Area> {
}
