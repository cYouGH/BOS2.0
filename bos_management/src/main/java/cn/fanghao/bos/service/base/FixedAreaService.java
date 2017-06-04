package cn.fanghao.bos.service.base;

import cn.fanghao.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by eggdog on 2017/6/4.
 */
public interface FixedAreaService {
    public void save(FixedArea fixedArea);

    Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable);
}
