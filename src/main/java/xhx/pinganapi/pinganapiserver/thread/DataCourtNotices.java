package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataCourtNotices implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataCourtNotices(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_courtnotices = "http://192.168.11.231:8090/module_page/" + index_id + "/court_notices?page=1&pageSize=10";
            String entityStr_courtnotices = HttpClientUtil.getResult(httpClient, url_courtnotices);
            JSONObject jsonObject10 = JSONObject.parseObject(entityStr_courtnotices);
            //去除结果集中的id、indexId
            JSONObject ob10 = RemovePropertyUtil.removeProperty(jsonObject10);
            if(ob10!=null){
                Map<String, Object> valueMap10 = new HashMap();
                valueMap10.putAll(ob10);
                valueMap.put("data_court_notices", valueMap10.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
