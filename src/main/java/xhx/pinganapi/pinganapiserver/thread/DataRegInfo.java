package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataRegInfo implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataRegInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_reginfo = "http://192.168.11.231:8090/module_page/" + index_id + "/reg_info?page=1&pageSize=10";
            String entityStr_reginfo = HttpClientUtil.getResult(httpClient, url_reginfo);
            JSONObject jsonObject2 = JSONObject.parseObject(entityStr_reginfo);
            //去除结果集中的id、indexId
            JSONObject ob2 = RemovePropertyUtil.removeProperty(jsonObject2);
            if(ob2!=null){
                Map<String, Object> valueMap2 = new HashMap();
                valueMap2.putAll(ob2);
                valueMap.put("data_reg_info", valueMap2.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
