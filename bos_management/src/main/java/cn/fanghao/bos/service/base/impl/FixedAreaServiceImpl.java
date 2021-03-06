package cn.fanghao.bos.service.base.impl;

import cn.fanghao.bos.dao.base.CourierRepository;
import cn.fanghao.bos.dao.base.FixedAreaRepository;
import cn.fanghao.bos.dao.base.TakeTimeRepository;
import cn.fanghao.bos.domain.base.Courier;
import cn.fanghao.bos.domain.base.FixedArea;
import cn.fanghao.bos.domain.base.TakeTime;
import cn.fanghao.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eggdog on 2017/6/4.
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }


    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Override
    public void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId) {

        FixedArea persistFixedArea = fixedAreaRepository.findOne(fixedArea.getId());
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        // 快递员 关联到快递员上
        persistFixedArea.getCouriers().add(courier);
        //将收派标准 关联到 快递员上
        courier.setTakeTime(takeTime);

    }
}
