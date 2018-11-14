package xhx.pinganapi.pinganapiserver.thread;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.RemovePropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class DataImportExportCountry implements Runnable{
    private Map<String, Object> valueMap;
    private CloseableHttpClient httpClient;
    private Integer index_id;
    public DataImportExportCountry(Map<String, Object> valueMap,CloseableHttpClient httpClient,Integer index_id) {
        this.valueMap=valueMap;
        this.httpClient=httpClient;
        this.index_id = index_id;
    }
    @Override
    public void run() {
        try {
            /*String url_importexportcountry = "http://192.168.11.231:8090/module_page/" + index_id + "/import_export_country?page=1&pageSize=10";
            String entityStr_importexportcountry = HttpClientUtil.getResult(httpClient, url_importexportcountry);
            JSONObject jsonObject17 = JSONObject.parseObject(entityStr_importexportcountry);
            JSONObject ob17 = RemovePropertyUtil.removeProperty(jsonObject17);
            if(ob17!=null){
                Map<String, Object> valueMap17 = new HashMap();
                valueMap17.putAll(ob17);
                valueMap.put("data_import_export_country", valueMap17.get("key"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
