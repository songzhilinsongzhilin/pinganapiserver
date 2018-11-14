package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataEntZhongdengInfo implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataEntZhongdengInfo(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_entzhongdenginfo = "http://192.168.11.231:8090/module_page/" + index_id + "/ent_zhongdeng_info?page=1&pageSize=10";
            String entityStr_entzhongdenginfo = HttpClientUtil.getResult(httpClient, url_entzhongdenginfo);
            JSONObject jsonObject13 = JSONObject.parseObject(entityStr_entzhongdenginfo);
            JSONObject ob13 = RemovePropertyUtil.removeProperty(jsonObject13);
            if(ob13!=null){
                Map<String, Object> valueMap13 = new HashMap();
                valueMap13.putAll(ob13);
                valueMap.put("data_ent_zhongdeng_info", valueMap13.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
