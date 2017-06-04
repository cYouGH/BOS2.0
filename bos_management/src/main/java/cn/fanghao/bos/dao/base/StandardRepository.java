package cn.fanghao.bos.dao.base;

import cn.fanghao.bos.domain.base.Standard;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by eggdog on 2017/6/2.
 */
public interface StandardRepository extends JpaRepository<Standard,Integer> {

    //根据收派标准按名称查询
    public List<Standard> findByName(String name);
//    @SuppressWarnings("deprecation")
    @Query(value = "from Standard where name = ?",nativeQuery = false)
    public List<Standard> queryName(String name);

    @Query(value = "update Standard set minLength=?2 where id=?1")
    @Modifying
    public void updateMinLength(Integer id , Integer minLength);
}
