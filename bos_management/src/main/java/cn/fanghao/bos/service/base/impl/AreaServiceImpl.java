package cn.fanghao.bos.service.base.impl;

import cn.fanghao.bos.dao.base.AreaRepository;
import cn.fanghao.bos.domain.base.Area;
import cn.fanghao.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by eggdog on 2017/6/4.
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;


    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }

    @Override
    public Page<Area> findPageData(Specification<Area> specification, Pageable pageable) {

         return areaRepository.findAll(specification,pageable);
    }
}
