package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataJudgeDoc implements Runnable  {
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataJudgeDoc(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_judgedoc = "http://192.168.11.231:8090/module_page/" + index_id + "/judge_doc?page=1&pageSize=10";
            String entityStr_judgedoc = HttpClientUtil.getResult(httpClient, url_judgedoc);
            JSONObject jsonObject7 = JSONObject.parseObject(entityStr_judgedoc);
            //去除结果集中的id、indexId
            JSONObject ob7 = RemovePropertyUtil.removeProperty(jsonObject7);
            if(ob7!=null){
                Map<String, Object> valueMap7 = new HashMap();
                valueMap7.putAll(ob7);
                valueMap.put("data_judge_doc", valueMap7.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
