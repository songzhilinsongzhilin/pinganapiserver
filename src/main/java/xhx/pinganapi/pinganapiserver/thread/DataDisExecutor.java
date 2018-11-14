package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataDisExecutor implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataDisExecutor(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_disexecutor = "http://192.168.11.231:8090/module_page/" + index_id + "/dis_executor?page=1&pageSize=10";
            String entityStr_disexecutor = HttpClientUtil.getResult(httpClient, url_disexecutor);
            JSONObject jsonObject11 = JSONObject.parseObject(entityStr_disexecutor);
            JSONObject ob11 = RemovePropertyUtil.removeProperty(jsonObject11);
            if(ob11!=null){
                Map<String, Object> valueMap11 = new HashMap();
                valueMap11.putAll(ob11);
                valueMap.put("data_dis_executor", valueMap11.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
