package cn.fanghao.bos.service.base.impl;

import cn.fanghao.bos.dao.base.StandardRepository;
import cn.fanghao.bos.domain.base.Standard;
import cn.fanghao.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by eggdog on 2017/6/2.
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    //注入DAO
    @Autowired
    private StandardRepository standardRepository;


    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public long findTotal() {
        return standardRepository.count();
    }

    @Override
    public Page<Standard> findPageData(Pageable pageable) {
        return standardRepository.findAll(pageable);
    }

    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }
}
