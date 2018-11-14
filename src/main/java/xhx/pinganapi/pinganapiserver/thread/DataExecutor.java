package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataExecutor implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataExecutor(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_executor = "http://192.168.11.231:8090/module_page/" + index_id + "/executor?page=1&pageSize=10";
            String entityStr_executor = HttpClientUtil.getResult(httpClient, url_executor);
            JSONObject jsonObject12 = JSONObject.parseObject(entityStr_executor);
            JSONObject ob12 = RemovePropertyUtil.removeProperty(jsonObject12);
            if(ob12!=null){
                Map<String, Object> valueMap12 = new HashMap();
                valueMap12.putAll(ob12);
                valueMap.put("data_executor", valueMap12.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
