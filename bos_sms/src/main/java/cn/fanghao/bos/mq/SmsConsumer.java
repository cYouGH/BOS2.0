package cn.fanghao.bos.mq;


import cn.fanghao.bos.utils.AliCodeUtils;
import org.springframework.stereotype.Service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by eggdog on 2017/6/11.
 */
@Service("smsConsumer")
public class SmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;


        try {
            //调用AliDaYu 短信验证
            //String result = AliCodeUtils.sendSmsByHTTP(mapMessage.getString("telephone"), mapMessage.getString("randomCode"));
            String result = "\"success\":true";
            if (result != null && result.contains("\"success\":true")) {
                System.out.println("短信发送成功" + mapMessage.getString("telephone") + "----" + mapMessage.getString("randomCode"));
            } else {
                throw new RuntimeException("短信发送失败：" + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
