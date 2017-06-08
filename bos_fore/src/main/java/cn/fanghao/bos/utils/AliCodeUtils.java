package cn.fanghao.bos.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AliCodeUtils {

	public static String sendSmsByHTTP(String telephone, String randomCode) throws ApiException {
		String url = "http://gw.api.taobao.com/router/rest";	
		String appkey = "24017694";		//appKey
		String secret = "82db6ffe9c39b73ba8a02fca8b364f84";		//appSecret
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		
		req.setExtend( "" );						
		req.setSmsType( "normal" );
		req.setSmsFreeSignName( "" );		//配置管理-短信签名名称
		req.setSmsParamString( "{code:'"+randomCode+"'}"  );	//短信模板的参数
		req.setRecNum( telephone );					//手机号
		req.setSmsTemplateCode( "" );	//配置管理-短信模板的ID
		
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
		return rsp.getBody();
	}

}
