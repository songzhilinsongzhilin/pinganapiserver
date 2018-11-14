package xhx.pinganapi.pinganapiserver.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.util.*;

public class RemovePropertyUtil {
    public static JSONObject removeProperty(JSONObject jsonObject) {
        //去除结果集中的id、indexId
        JSONArray data4 = (JSONArray) jsonObject.get("data");
        List<Map<String, Object>> mapList = new ArrayList<>();
        JSONObject ob = new JSONObject();
        if (data4.size() != 0) {
            List<Map<String, Object>> mapListJson = (List) data4;
            for (Map<String, Object> stringObjectMap : mapListJson) {
                stringObjectMap.remove("id");
                stringObjectMap.remove("indexId");
                stringObjectMap.remove("juyuanId");
                stringObjectMap.remove("lCompanyId");
                stringObjectMap.remove("lId");
                stringObjectMap.remove("externalId");
                stringObjectMap.remove("lZhixingid");
                if (stringObjectMap.get("createdAt") != null) {
                    Date createdAt = new Date((Long) stringObjectMap.get("createdAt"));
                    String format1 = DateFormat.getDateTimeInstance().format(createdAt);
                    stringObjectMap.remove("createdAt");
                    stringObjectMap.put("createdAt", format1);
                }
                if (stringObjectMap.get("updatedAt") != null) {
                    Date updatedAt = new Date((Long) stringObjectMap.get("updatedAt"));
                    String format2 = DateFormat.getDateTimeInstance().format(updatedAt);
                    stringObjectMap.remove("updatedAt");
                    stringObjectMap.put("updatedAt", format2);
                }
                if (stringObjectMap.get("daCaseCreateTime") != null) {
                    Date daCaseCreateTime = new Date((Long) stringObjectMap.get("daCaseCreateTime"));
                    String format3 = DateFormat.getDateTimeInstance().format(daCaseCreateTime);
                    stringObjectMap.remove("daCaseCreateTime");
                    stringObjectMap.put("daCaseCreateTime", format3);
                }
                if (stringObjectMap.get("daChangeTime") != null) {
                    Date daChangeTime = new Date((Long) stringObjectMap.get("daChangeTime"));
                    String format4 = DateFormat.getDateTimeInstance().format(daChangeTime);
                    stringObjectMap.remove("daChangeTime");
                    stringObjectMap.put("daChangeTime", format4);
                }
                if (stringObjectMap.get("daCreateTime") != null) {
                    Date daCreateTime = new Date((Long) stringObjectMap.get("daCreateTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daCreateTime);
                    stringObjectMap.remove("daCreateTime");
                    stringObjectMap.put("daCreateTime", format5);
                }
                if (stringObjectMap.get("daSubmitTime") != null) {
                    Date daSubmitTime = new Date((Long) stringObjectMap.get("daSubmitTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daSubmitTime);
                    stringObjectMap.remove("daSubmitTime");
                    stringObjectMap.put("daSubmitTime", format5);
                }
                if (stringObjectMap.get("daJudgeTime") != null) {
                    Date daJudgeTime = new Date((Long) stringObjectMap.get("daJudgeTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daJudgeTime);
                    stringObjectMap.remove("daJudgeTime");
                    stringObjectMap.put("daJudgeTime", format5);
                }
                if (stringObjectMap.get("daUpdateTime") != null) {
                    Date daUpdateTime = new Date((Long) stringObjectMap.get("daUpdateTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daUpdateTime);
                    stringObjectMap.remove("daUpdateTime");
                    stringObjectMap.put("daUpdateTime", format5);
                }
                if (stringObjectMap.get("daCrawledTime") != null) {
                    Date daCrawledTime = new Date((Long) stringObjectMap.get("daCrawledTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daCrawledTime);
                    stringObjectMap.remove("daCrawledTime");
                    stringObjectMap.put("daCrawledTime", format5);
                }
                if (stringObjectMap.get("daEstiblishTime") != null) {
                    Date daEstiblishTime = new Date((Long) stringObjectMap.get("daEstiblishTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daEstiblishTime);
                    stringObjectMap.remove("daEstiblishTime");
                    stringObjectMap.put("daEstiblishTime", format5);
                }
                if (stringObjectMap.get("daFromTime") != null) {
                    Date daFromTime = new Date((Long) stringObjectMap.get("daFromTime"));
                    String format5 = DateFormat.getDateTimeInstance().format(daFromTime);
                    stringObjectMap.remove("daFromTime");
                    stringObjectMap.put("daFromTime", format5);
                }
                mapList.add(stringObjectMap);
            }
            JSONArray jsonArray4 = JSONArray.parseArray(String.valueOf(mapList));
            ob.put("key",jsonArray4);
        }
        return ob;
    }
}
