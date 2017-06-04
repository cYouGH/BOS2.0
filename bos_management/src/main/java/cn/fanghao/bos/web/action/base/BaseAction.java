package cn.fanghao.bos.web.action.base;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eggdog on 2017/6/4.
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    //属性驱动
    protected T model;

    @Override
    public T getModel() {
        return model;
    }
    //使用构造器 完成model实例化
    public BaseAction() {
        //构造子类Action对象，获取继承父类型的泛型
        //AreaAction extends BaseAction
        // BaseAction<Area>
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型的第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];

        try {
            model = modelClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("模型构造失败");
        }
    }


    //分页查询属性驱动
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    protected void pushPageDataToValueStack(Page<T> pageData) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", pageData.getTotalElements());
        result.put("rows", pageData.getContent());

        //将map转换成json数据返回，使用strut-json-plugin插件
        ActionContext.getContext().getValueStack().push(result);
    }
}
