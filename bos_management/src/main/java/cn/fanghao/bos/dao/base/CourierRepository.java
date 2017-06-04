package cn.fanghao.bos.dao.base;

import cn.fanghao.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by eggdog on 2017/6/3.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer>,
        JpaSpecificationExecutor<Courier> {

    @Query(value = "update Courier set deltag = '1' where id = ?")
    @Modifying
    public void updateDelTag(Integer id);
}
