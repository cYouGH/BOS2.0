package cn.fanghao.bos.dao.base;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by eggdog on 2017/6/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;
    @Test
    public void testQuery(){
        System.out.println(standardRepository.queryName("20-30公斤"));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testQuery1(){
        standardRepository.updateMinLength(1,15);
    }
}
