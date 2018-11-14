package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataEntStaffInfo implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataEntStaffInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_entstaffinfo = "http://192.168.11.231:8090/module_page/" + index_id + "/ent_staff_info?page=1&pageSize=10";
            String entityStr_entstaffinfo = HttpClientUtil.getResult(httpClient, url_entstaffinfo);
            JSONObject jsonObject4 = JSONObject.parseObject(entityStr_entstaffinfo);
            //去除结果集中的id、indexId
            JSONObject ob4 = RemovePropertyUtil.removeProperty(jsonObject4);
            if (ob4!=null){
                Map<String, Object> valueMap4 = new HashMap();
                valueMap4.putAll(ob4);
                valueMap.put("data_ent_staff_info", valueMap4.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
