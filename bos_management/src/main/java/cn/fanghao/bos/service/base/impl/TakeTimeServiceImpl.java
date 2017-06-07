package cn.fanghao.bos.service.base.impl;

import cn.fanghao.bos.dao.base.TakeTimeRepository;
import cn.fanghao.bos.domain.base.TakeTime;
import cn.fanghao.bos.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by eggdog on 2017/6/7.
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {


        return takeTimeRepository.findAll();
    }
}
