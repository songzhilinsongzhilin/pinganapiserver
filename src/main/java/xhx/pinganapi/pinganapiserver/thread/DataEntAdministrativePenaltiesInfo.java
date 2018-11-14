package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataEntAdministrativePenaltiesInfo implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataEntAdministrativePenaltiesInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_entadministrativepenaltiesinfo = "http://192.168.11.231:8090/module_page/" + index_id + "/ent_administrative_penalties_info?page=1&pageSize=10";
            String entityStr_entadministrativepenaltiesinfo = HttpClientUtil.getResult(httpClient, url_entadministrativepenaltiesinfo);
            JSONObject jsonObject14 = JSONObject.parseObject(entityStr_entadministrativepenaltiesinfo);
            JSONObject ob14 = RemovePropertyUtil.removeProperty(jsonObject14);
            if(ob14!=null){
                Map<String, Object> valueMap14 = new HashMap();
                valueMap14.putAll(ob14);
                valueMap.put("data_ent_administrative_penalties_info", valueMap14.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}