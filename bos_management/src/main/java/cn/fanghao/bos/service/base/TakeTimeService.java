package cn.fanghao.bos.service.base;

import cn.fanghao.bos.domain.base.TakeTime;

import java.util.List;

/**
 * Created by eggdog on 2017/6/7.
 */
public interface TakeTimeService {

    List<TakeTime> findAll();
}
