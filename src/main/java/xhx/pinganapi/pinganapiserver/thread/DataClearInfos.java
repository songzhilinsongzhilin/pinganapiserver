package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataClearInfos implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataClearInfos(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_clearinfos = "http://192.168.11.231:8090/module_page/" + index_id + "/clear_infos?page=1&pageSize=10";
            String entityStr_clearinfos = HttpClientUtil.getResult(httpClient, url_clearinfos);
            JSONObject jsonObject15 = JSONObject.parseObject(entityStr_clearinfos);
            JSONObject ob15 = RemovePropertyUtil.removeProperty(jsonObject15);
            if(ob15!=null){
                Map<String, Object> valueMap15 = new HashMap();
                valueMap15.putAll(ob15);
                valueMap.put("data_clear_infos", valueMap15.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}