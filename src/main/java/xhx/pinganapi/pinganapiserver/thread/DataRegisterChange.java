package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataRegisterChange implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataRegisterChange(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_registerchange = "http://192.168.11.231:8090/module_page/" + index_id + "/register_change?page=1&pageSize=10";
            String entityStr_registerchange = HttpClientUtil.getResult(httpClient, url_registerchange);
            JSONObject jsonObject6 = JSONObject.parseObject(entityStr_registerchange);
            //去除结果集中的id、indexId
            JSONObject ob6 = RemovePropertyUtil.removeProperty(jsonObject6);
            if(ob6!=null){
                Map<String, Object> valueMap6 = new HashMap();
                valueMap6.putAll(ob6);
                valueMap.put("data_register_change", valueMap6.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
