package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataImportExportCondition implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataImportExportCondition(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_importexportcondition = "http://192.168.11.231:8090/module_page/" + index_id + "/import_export_condition?page=1&pageSize=10";
            String entityStr_importexportcondition = HttpClientUtil.getResult(httpClient, url_importexportcondition);
            JSONObject jsonObject16 = JSONObject.parseObject(entityStr_importexportcondition);
            JSONObject ob16 = RemovePropertyUtil.removeProperty(jsonObject16);
            if (ob16!=null) {
                Map<String, Object> valueMap16 = new HashMap();
                valueMap16.putAll(ob16);
                valueMap.put("data_import_export_condition", valueMap16.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
