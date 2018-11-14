package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataEntConnectInfo implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataEntConnectInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_entconnectinfo = "http://192.168.11.231:8090/module_page/" + index_id + "/ent_connect_info?page=1&pageSize=10";
            String entityStr_entconnectinfo = HttpClientUtil.getResult(httpClient, url_entconnectinfo);
            JSONObject jsonObject3 = JSONObject.parseObject(entityStr_entconnectinfo);
            //去除结果集中的id、indexId
            JSONObject ob3 = RemovePropertyUtil.removeProperty(jsonObject3);
            if(ob3!=null){
                Map<String, Object> valueMap3 = new HashMap();
                valueMap3.putAll(ob3);
                valueMap.put("data_ent_connect_info", valueMap3.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
