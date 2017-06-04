package cn.fanghao.bos.service.base;

import cn.fanghao.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 收派标准管理
 * Created by eggdog on 2017/6/2.
 */
public interface StandardService {
    public void save(Standard standard);

    long findTotal();
    //分页查询
    Page<Standard> findPageData(Pageable pageable);
    //查询所有的收派标准
    List<Standard> findAll();
}
