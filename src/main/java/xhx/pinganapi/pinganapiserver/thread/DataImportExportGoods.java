package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataImportExportGoods implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataImportExportGoods(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_importexportgoods = "http://192.168.11.231:8090/module_page/" + index_id + "/import_export_goods?page=1&pageSize=10";
            String entityStr_importexportgoods = HttpClientUtil.getResult(httpClient, url_importexportgoods);
            JSONObject jsonObject18 = JSONObject.parseObject(entityStr_importexportgoods);
            JSONObject ob18 = RemovePropertyUtil.removeProperty(jsonObject18);
            if(ob18!=null){
                Map<String, Object> valueMap18 = new HashMap();
                valueMap18.putAll(ob18);
                valueMap.put("data_import_export_goods", valueMap18.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
