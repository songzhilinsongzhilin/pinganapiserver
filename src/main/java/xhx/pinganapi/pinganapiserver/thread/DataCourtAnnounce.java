package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataCourtAnnounce implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataCourtAnnounce(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_courtannounce = "http://192.168.11.231:8090/module_page/" + index_id + "/court_announce?page=1&pageSize=10";
            String entityStr_courtannounce = HttpClientUtil.getResult(httpClient, url_courtannounce);
            JSONObject jsonObject5 = JSONObject.parseObject(entityStr_courtannounce);
            //去除结果集中的id、indexId
            JSONObject ob5 = RemovePropertyUtil.removeProperty(jsonObject5);
            if(ob5!=null){
                Map<String, Object> valueMap5 = new HashMap();
                valueMap5.putAll(ob5);
                valueMap.put("data_court_announce", valueMap5.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
