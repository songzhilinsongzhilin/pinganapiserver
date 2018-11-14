package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataEntInsdustryCodeInfo implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataEntInsdustryCodeInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_entinsdustrycodeinfo = "http://192.168.11.231:8090/module_page/" + index_id + "/ent_insdustry_code_info?page=1&pageSize=10";
            String entityStr_entinsdustrycodeinfo = HttpClientUtil.getResult(httpClient, url_entinsdustrycodeinfo);
            JSONObject jsonObject19 = JSONObject.parseObject(entityStr_entinsdustrycodeinfo);
            JSONObject ob19 = RemovePropertyUtil.removeProperty(jsonObject19);
            if(ob19!=null){
                Map<String, Object> valueMap19 = new HashMap();
                valueMap19.putAll(ob19);
                valueMap.put("data_ent_insdustry_code_info", valueMap19.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
