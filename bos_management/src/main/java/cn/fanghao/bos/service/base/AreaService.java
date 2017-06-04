package cn.fanghao.bos.service.base;

import cn.fanghao.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by eggdog on 2017/6/4.
 */

public interface AreaService {
    public void saveBatch(List<Area> areas);


    public Page<Area> findPageData(Specification<Area> specification, Pageable pageable);
}
