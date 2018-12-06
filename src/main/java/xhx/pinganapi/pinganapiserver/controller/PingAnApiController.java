package xhx.pinganapi.pinganapiserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhx.pinganapi.pinganapiserver.bean.Result;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitApi;
import xhx.pinganapi.pinganapiserver.bean.XhxUserVisitDetail;
import xhx.pinganapi.pinganapiserver.config.LoginRequire;
import xhx.pinganapi.pinganapiserver.enums.ResultEnum;
import xhx.pinganapi.pinganapiserver.service.UserVisitDetailService;
import xhx.pinganapi.pinganapiserver.service.XhxUserVisitApiService;
import xhx.pinganapi.pinganapiserver.thread.*;
import xhx.pinganapi.pinganapiserver.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Controller
@RequestMapping("/api")
public class PingAnApiController {
    private static final Logger logger = LoggerFactory.getLogger(PingAnApiController.class);
    @Autowired
    XhxUserVisitApiService userVisitApiService;
    @Value("${token.key}")
    String tokenKey;
    @Autowired
    UserVisitDetailService userVisitDetailService;
    //接口掉方法，url？传入参数
    /*@LoginRequire
    @ResponseBody
    @RequestMapping(value = "/selectpingan",produces={"application/json;charset=utf-8"})
    public Result selectpingan(String regNo,String socialCreditNo,String taxNo,String name,String token,HttpServletRequest request) throws IOException, InterruptedException {
        if(token==null || (name==null && regNo==null && socialCreditNo==null && taxNo==null)){//判断是否缺少参数
            return ResultUtil.error(ResultEnum.LACK_PARAMETER);
        }
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        //Boolean result = userVisitApiService.verifytokenByLoginIp(request,token);
        CountDownLatch latch = new CountDownLatch(16);

        if(result) {
            CloseableHttpClient httpClient = CreateHttpClient.getHttpClient();
            //后期该uri调用可变参数的接口，利用name、regNo、socialCreditNo、taxNo中的一个或多个查询index_data中的数据
            HttpPost httpPost=null;
            List<BasicNameValuePair> params = new ArrayList<>();
            if(name!=null){//123.59.198.71:8090  192.168.10.104
                httpPost = new HttpPost("http://192.168.10.104:8090/index/accurate_name");
                params.add(new BasicNameValuePair("name", name));
            }else if(regNo!=null){
                httpPost = new HttpPost("http://192.168.10.104:8090/index/reg_no");
                params.add(new BasicNameValuePair("regNo", regNo));
            }else if(socialCreditNo!=null){
                httpPost = new HttpPost("http://192.168.10.104:8090/index/social_credit_no");
                params.add(new BasicNameValuePair("socialCreditNo", socialCreditNo));
            }else if(taxNo!=null){
                httpPost = new HttpPost("http://192.168.10.104:8090/index/tax_no");
                params.add(new BasicNameValuePair("taxNo", taxNo));
            }
            UrlEncodedFormEntity entityParams = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(entityParams);
            String entityStr = null;
            Map<String, Object> valueMap = new HashMap();
            try {
                //1.查询index_data
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity entity = httpResponse.getEntity();//取出返回结果
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();//执行结果状态
                if (statusCode != 200) {
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                entityStr = EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
                JSONObject jsonObject = JSONObject.parseObject(entityStr);
                Map<String, Object> valueMap1 = new HashMap();
                valueMap1.putAll(jsonObject);
                Integer index_id = (Integer) valueMap1.get("id");//取出indexid值
                valueMap1.remove("id");
                valueMap1.remove("juyuanId");
                valueMap1.remove("companyId");
                Date createdAt = new Date((Long) valueMap1.get("createdAt"));
                String format1 = DateFormat.getDateTimeInstance().format(createdAt);
                valueMap1.remove("createdAt");
                valueMap1.put("createdAt",format1);
                Date updatedAt = new Date((Long) valueMap1.get("updatedAt"));
                String format2 = DateFormat.getDateTimeInstance().format(updatedAt);
                valueMap1.remove("updatedAt");
                valueMap1.put("updatedAt",format2);
                valueMap.put("index_data", valueMap1);
                //2.查询data_reg_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long start = System.currentTimeMillis();
                            String url_reginfo = "http://192.168.10.104:8090/module_page/" + index_id + "/reg_info?page=1&pageSize=10";
                            String entityStr_reginfo = HttpClientUtil.getResult(httpClient, url_reginfo);
                            JSONObject jsonObject2 = JSONObject.parseObject(entityStr_reginfo);
                            //去除结果集中的id、indexId
                            JSONObject ob2 = RemovePropertyUtil.removeProperty(jsonObject2);
                            if (ob2 != null) {
                                Map<String, Object> valueMap2 = new HashMap();
                                valueMap2.putAll(ob2);
                                valueMap.put("data_reg_info", valueMap2.get("key"));
                                long time = System.currentTimeMillis() - start;
                                System.out.println("2 :" + time+"ms");
                                latch.countDown();
                            }
                        }catch (Exception e){
                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //3.查询data_ent_connect_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            long start = System.currentTimeMillis();
                        String url_entconnectinfo = "http://192.168.10.104:8090/module_page/" + index_id + "/ent_connect_info?page=1&pageSize=10";
                        String entityStr_entconnectinfo = HttpClientUtil.getResult(httpClient, url_entconnectinfo);
                        JSONObject jsonObject3 = JSONObject.parseObject(entityStr_entconnectinfo);
                        //去除结果集中的id、indexId
                        JSONObject ob3 = RemovePropertyUtil.removeProperty(jsonObject3);
                        if(ob3!=null){
                            Map<String, Object> valueMap3 = new HashMap();
                            valueMap3.putAll(ob3);
                            valueMap.put("data_ent_connect_info", valueMap3.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("3 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //4.查询data_ent_staff_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_entstaffinfo = "http://192.168.10.104:8090/module_page/" + index_id + "/ent_staff_info?page=1&pageSize=10";
                        String entityStr_entstaffinfo = HttpClientUtil.getResult(httpClient, url_entstaffinfo);
                        JSONObject jsonObject4 = JSONObject.parseObject(entityStr_entstaffinfo);
                        //去除结果集中的id、indexId
                        JSONObject ob4 = RemovePropertyUtil.removeProperty(jsonObject4);
                        if (ob4!=null){
                            Map<String, Object> valueMap4 = new HashMap();
                            valueMap4.putAll(ob4);
                            valueMap.put("data_ent_staff_info", valueMap4.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("4 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //5.查询data_court_announce
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_courtannounce = "http://192.168.10.104:8090/module_page/" + index_id + "/court_announce?page=1&pageSize=10";
                        String entityStr_courtannounce = HttpClientUtil.getResult(httpClient, url_courtannounce);
                        JSONObject jsonObject5 = JSONObject.parseObject(entityStr_courtannounce);
                        //去除结果集中的id、indexId
                        JSONObject ob5 = RemovePropertyUtil.removeProperty(jsonObject5);
                        if(ob5!=null){
                            Map<String, Object> valueMap5 = new HashMap();
                            valueMap5.putAll(ob5);
                            valueMap.put("data_court_announce", valueMap5.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("5 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //6.查询data_register_change
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_registerchange = "http://192.168.10.104:8090/module_page/" + index_id + "/register_change?page=1&pageSize=10";
                        String entityStr_registerchange = HttpClientUtil.getResult(httpClient, url_registerchange);
                        JSONObject jsonObject6 = JSONObject.parseObject(entityStr_registerchange);
                        //去除结果集中的id、indexId
                        JSONObject ob6 = RemovePropertyUtil.removeProperty(jsonObject6);
                        if(ob6!=null){
                            Map<String, Object> valueMap6 = new HashMap();
                            valueMap6.putAll(ob6);
                            valueMap.put("data_register_change", valueMap6.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("6 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //7.查询data_judge_doc
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_judgedoc = "http://192.168.10.104:8090/module_page/" + index_id + "/judge_doc?page=1&pageSize=10";
                        String entityStr_judgedoc = HttpClientUtil.getResult(httpClient, url_judgedoc);
                        JSONObject jsonObject7 = JSONObject.parseObject(entityStr_judgedoc);
                        //去除结果集中的id、indexId
                        JSONObject ob7 = RemovePropertyUtil.removeProperty(jsonObject7);
                        if(ob7!=null){
                            Map<String, Object> valueMap7 = new HashMap();
                            valueMap7.putAll(ob7);
                            valueMap.put("data_judge_doc", valueMap7.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("7 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //10.查询data_court_notices
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_courtnotices = "http://192.168.10.104:8090/module_page/" + index_id + "/court_notices?page=1&pageSize=10";
                        String entityStr_courtnotices = HttpClientUtil.getResult(httpClient, url_courtnotices);
                        JSONObject jsonObject10 = JSONObject.parseObject(entityStr_courtnotices);
                        //去除结果集中的id、indexId
                        JSONObject ob10 = RemovePropertyUtil.removeProperty(jsonObject10);
                        if(ob10!=null){
                            Map<String, Object> valueMap10 = new HashMap();
                            valueMap10.putAll(ob10);
                            valueMap.put("data_court_notices", valueMap10.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("10 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //11.查询data_dis_executor
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                            String url_disexecutor = "http://192.168.10.104:8090/module_page/" + index_id + "/dis_executor?page=1&pageSize=10";
                            String entityStr_disexecutor = HttpClientUtil.getResult(httpClient, url_disexecutor);
                            JSONObject jsonObject11 = JSONObject.parseObject(entityStr_disexecutor);
                            JSONObject ob11 = RemovePropertyUtil.removeProperty(jsonObject11);
                            if(ob11!=null){
                                Map<String, Object> valueMap11 = new HashMap();
                                valueMap11.putAll(ob11);
                                valueMap.put("data_dis_executor", valueMap11.get("key"));
                                long time = System.currentTimeMillis() - start;
                                System.out.println("11 :" + time+"ms");
                                latch.countDown();
                            } }catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }

                    }
                });
                //12.查询data_executor
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            long start = System.currentTimeMillis();
                        String url_executor = "http://192.168.10.104:8090/module_page/" + index_id + "/executor?page=1&pageSize=10";
                        String entityStr_executor = HttpClientUtil.getResult(httpClient, url_executor);
                        JSONObject jsonObject12 = JSONObject.parseObject(entityStr_executor);
                        JSONObject ob12 = RemovePropertyUtil.removeProperty(jsonObject12);
                        if(ob12!=null){
                            Map<String, Object> valueMap12 = new HashMap();
                            valueMap12.putAll(ob12);
                            valueMap.put("data_executor", valueMap12.get("key"));
                            long time = System.currentTimeMillis() - start;
                            System.out.println("12 :" + time+"ms");
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //13.查询data_ent_zhongdeng_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_entzhongdenginfo = "http://192.168.10.104:8090/module_page/" + index_id + "/ent_zhongdeng_info?page=1&pageSize=10";
                        String entityStr_entzhongdenginfo = HttpClientUtil.getResult(httpClient, url_entzhongdenginfo);
                        JSONObject jsonObject13 = JSONObject.parseObject(entityStr_entzhongdenginfo);
                        JSONObject ob13 = RemovePropertyUtil.removeProperty(jsonObject13);
                        if(ob13!=null){
                            Map<String, Object> valueMap13 = new HashMap();
                            valueMap13.putAll(ob13);
                            valueMap.put("data_ent_zhongdeng_info", valueMap13.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //14.查询data_ent_administrative_penalties_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_entadministrativepenaltiesinfo = "http://192.168.10.104:8090/module_page/" + index_id + "/ent_administrative_penalties_info?page=1&pageSize=10";
                        String entityStr_entadministrativepenaltiesinfo = HttpClientUtil.getResult(httpClient, url_entadministrativepenaltiesinfo);
                        JSONObject jsonObject14 = JSONObject.parseObject(entityStr_entadministrativepenaltiesinfo);
                        JSONObject ob14 = RemovePropertyUtil.removeProperty(jsonObject14);
                        if(ob14!=null){
                            Map<String, Object> valueMap14 = new HashMap();
                            valueMap14.putAll(ob14);
                            valueMap.put("data_ent_administrative_penalties_info", valueMap14.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //15.查询data_clear_infos
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_clearinfos = "http://192.168.10.104:8090/module_page/" + index_id + "/clear_infos?page=1&pageSize=10";
                        String entityStr_clearinfos = HttpClientUtil.getResult(httpClient, url_clearinfos);
                        JSONObject jsonObject15 = JSONObject.parseObject(entityStr_clearinfos);
                        JSONObject ob15 = RemovePropertyUtil.removeProperty(jsonObject15);
                        if(ob15!=null){
                            Map<String, Object> valueMap15 = new HashMap();
                            valueMap15.putAll(ob15);
                            valueMap.put("data_clear_infos", valueMap15.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //16.查询data_import_export_condition
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_importexportcondition = "http://192.168.10.104:8090/module_page/" + index_id + "/import_export_condition?page=1&pageSize=10";
                        String entityStr_importexportcondition = HttpClientUtil.getResult(httpClient, url_importexportcondition);
                        JSONObject jsonObject16 = JSONObject.parseObject(entityStr_importexportcondition);
                        JSONObject ob16 = RemovePropertyUtil.removeProperty(jsonObject16);
                        if (ob16!=null) {
                            Map<String, Object> valueMap16 = new HashMap();
                            valueMap16.putAll(ob16);
                            valueMap.put("data_import_export_condition", valueMap16.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //17.查询data_import_export_country
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_importexportcountry = "http://192.168.10.104:8090/module_page/" + index_id + "/import_export_country?page=1&pageSize=1000";
                        String entityStr_importexportcountry = HttpClientUtil.getResult(httpClient, url_importexportcountry);
                        JSONObject jsonObject17 = JSONObject.parseObject(entityStr_importexportcountry);
                        JSONObject ob17 = RemovePropertyUtil.removeProperty(jsonObject17);
                        if(ob17!=null){
                            Map<String, Object> valueMap17 = new HashMap();
                            valueMap17.putAll(ob17);
                            valueMap.put("data_import_export_country", valueMap17.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                //18.查询data_import_export_goods
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_importexportgoods = "http://192.168.10.104:8090/module_page/" + index_id + "/import_export_goods?page=1&pageSize=1000";
                        String entityStr_importexportgoods = HttpClientUtil.getResult(httpClient, url_importexportgoods);
                        JSONObject jsonObject18 = JSONObject.parseObject(entityStr_importexportgoods);
                        JSONObject ob18 = RemovePropertyUtil.removeProperty(jsonObject18);
                        if(ob18!=null){
                            Map<String, Object> valueMap18 = new HashMap();
                            valueMap18.putAll(ob18);
                            valueMap.put("data_import_export_goods", valueMap18.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                           // latch.countDown();
                        }
                    }
                });
                //19.查询data_ent_insdustry_code_info
                ThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                        String url_entinsdustrycodeinfo = "http://192.168.10.104:8090/module_page/" + index_id + "/ent_insdustry_code_info?page=1&pageSize=10";
                        System.out.println(url_entinsdustrycodeinfo);
                        String entityStr_entinsdustrycodeinfo = HttpClientUtil.getResult(httpClient, url_entinsdustrycodeinfo);
                        JSONObject jsonObject19 = JSONObject.parseObject(entityStr_entinsdustrycodeinfo);
                        JSONObject ob19 = RemovePropertyUtil.removeProperty(jsonObject19);
                        if(ob19!=null){
                            Map<String, Object> valueMap19 = new HashMap();
                            valueMap19.putAll(ob19);
                            valueMap.put("data_ent_insdustry_code_info", valueMap19.get("key"));
                            latch.countDown();
                        }}catch (Exception e){

                        }finally {
                            //latch.countDown();
                        }
                    }
                });
                latch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                //关闭httpClient连接
                httpClient.close();
            }

            if (valueMap == null) {
                return ResultUtil.error(ResultEnum.NOT_FIND);
            }
            //返回结果前将数据库中的查询次数loginCount加1
            XhxUserVisitApi userVisitApi = userVisitApiService.selectLoginCount(token);
            userVisitApi.setLoginCount(userVisitApi.getLoginCount()+1);
            userVisitApi.setUpdateTime(new Date());
            int i = userVisitApiService.updateLoginCount(userVisitApi);
            if(i<1){
                return ResultUtil.error(ResultEnum.NOT_FIND);
            }
            return ResultUtil.success(valueMap);
        }
        return ResultUtil.error(ResultEnum.NOT_FIND);
    }
*/
    //添加用户访问的接口,根据用户的username、company、loginIp生成token
    //该接口需要username、company、loginIp参数
    // http://123.59.198.72:8082/api/insertVisit?data={username:'沈岩',company:'新华信',loginIp:'192.168.11.133',uri:'/api/selectpingan'}
    @LoginRequire
    @ResponseBody
    @RequestMapping("/insertVisit")
    public Result insertpingan(HttpServletRequest request){
        Map map = new HashMap();
        String data=request.getParameter("data");
        if (StringUtils.isNotBlank(data)){
            map = JSON.parseObject(data,map.getClass());
        }
        Long id = SnowFlake.getSnowFlakeId("xhx_user_visit_api");
        map.put("id",id);
        String loginIp = (String) map.get("loginIp");
        String token = JwtUtil.encode(tokenKey, map, loginIp);
        map.put("token",token);
        map.put("isEnable",1);
        map.put("loginCount",0);
        map.put("createTime",new Date());
        map.put("updateTime",new Date());
        int i = userVisitApiService.insertVisit(map);
        if (i<0){
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(ResultEnum.ADD_SUCCESS,"添加成功");
    }

        //根据用户的id查询全量数据,未过滤  http://192.168.11.90:8082/api/selectAllByIndexId?indexId=132886
        @LoginRequire
        @ResponseBody
        @RequestMapping(value = "/selectAllByAnyOne")
        public Result selectAllByAnyOne(String regNo,String socialCreditNo,String taxNo,String name,String token,HttpServletRequest request) throws IOException {
            if (token == null || (name == null && regNo == null && socialCreditNo == null && taxNo == null)) {//判断是否缺少参数
                return ResultUtil.error(ResultEnum.LACK_PARAMETER);
            }
            //验证token,ip,权限是否正确
            Boolean result = userVisitApiService.verifytokenAndPermission(token, request);
            //Boolean result = userVisitApiService.verifytoken(token);
            //Boolean result = userVisitApiService.verifytokenByLoginIp(request,token);
            if(!result){
                return ResultUtil.error(ResultEnum.CHECKFAILPERMISSION);
            }
            try {
                if (result) {
                    CloseableHttpClient httpClient = CreateHttpClient.getHttpClient();
                    //后期该uri调用可变参数的接口，利用name、regNo、socialCreditNo、taxNo中的一个或多个查询index_data中的数据
                    HttpPost httpPost = null;
                    List<BasicNameValuePair> params = new ArrayList<>();
                    if (name != null) {//123.59.198.71:8090  192.168.10.104
                        httpPost = new HttpPost("http://192.168.10.111:8090/index/accurate_name");
                        params.add(new BasicNameValuePair("name", name));
                    } else if (regNo != null) {
                        httpPost = new HttpPost("http://192.168.10.111:8090/index/reg_no");
                        params.add(new BasicNameValuePair("regNo", regNo));
                    } else if (socialCreditNo != null) {
                        httpPost = new HttpPost("http://192.168.10.111:8090/index/social_credit_no");
                        params.add(new BasicNameValuePair("socialCreditNo", socialCreditNo));
                    } else if (taxNo != null) {
                        httpPost = new HttpPost("http://192.168.10.111:8090/index/tax_no");
                        params.add(new BasicNameValuePair("taxNo", taxNo));
                    }
                    UrlEncodedFormEntity entityParams = new UrlEncodedFormEntity(params, "utf-8");
                    httpPost.setEntity(entityParams);
                    String entityStr = null;
                    //查询index_data
                    HttpResponse httpResponse = null;
                    try {
                        httpResponse = httpClient.execute(httpPost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    HttpEntity entity = httpResponse.getEntity();//取出返回结果
                    StatusLine statusLine = httpResponse.getStatusLine();
                    int statusCode = statusLine.getStatusCode();//执行结果状态
                    if (statusCode != 200) {
                        return ResultUtil.error(ResultEnum.NOT_FIND);
                    }
                    entityStr = EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
                    JSONObject jsonObject = JSONObject.parseObject(entityStr);
                    Integer indexIdInt = (Integer) jsonObject.get("id");//取出indexid值
                    Long indexId = indexIdInt.longValue();
                    httpClient.close();
                    if (indexId == null) {
                        return ResultUtil.error(ResultEnum.NOT_FIND);
                    }
                    CloseableHttpClient httpClient1 = CreateHttpClient.getHttpClient();
                    HttpPost httpPost1 = new HttpPost("http://192.168.10.111:8090/getModuleData/" + indexId);
                    HttpResponse httpResponse1 = httpClient1.execute(httpPost1);
                    HttpEntity entity1 = httpResponse1.getEntity();//取出返回结果
                    StatusLine statusLine1 = httpResponse1.getStatusLine();
                    int statusCode1 = statusLine1.getStatusCode();//执行结果状态
                    if (statusCode1 != 200) {
                        return ResultUtil.error(ResultEnum.NOT_FIND);
                    }
                    String entityStr1 = EntityUtils.toString(entity1, "utf-8");//将结果转换为字符串
                    JSONObject jsonObject1 = JSONObject.parseObject(entityStr1);
                    httpClient1.close();
                    if (jsonObject1 == null) {
                        return ResultUtil.error(ResultEnum.NOT_FIND);
                    }
                    //返回结果前将数据库中的查询次数loginCount加1
                    XhxUserVisitApi userVisitApi = userVisitApiService.selectLoginCount(token);
                    userVisitApi.setLoginCount(userVisitApi.getLoginCount() + 1);
                    userVisitApi.setUpdateTime(new Date());
                    int i = userVisitApiService.updateLoginCount(userVisitApi);
                    if (i < 1) {
                        return ResultUtil.error(ResultEnum.NOT_FIND);
                    }
                    return ResultUtil.success(jsonObject1, "全量数据");
                }
            }catch (Exception e){
                e.printStackTrace();
                e.getMessage();
            }
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        //根据用户的id查询全量数据,过滤  http://192.168.11.90:8082/api/selectAllByIndexIdTuoMin?indexId=132886
        @LoginRequire
        @ResponseBody
        @RequestMapping(value = "/selectAllByAnyOneTuoMin")
        public Result selectAllByAnyOneTuoMin(String regNo,String socialCreditNo,String taxNo,String name,String token,HttpServletRequest request) throws IOException {
            if (token == null || (name == null && regNo == null && socialCreditNo == null && taxNo == null)) {//判断是否缺少参数
                return ResultUtil.error(ResultEnum.LACK_PARAMETER);
            }
            //验证token,ip,权限是否正确
            Boolean result = userVisitApiService.verifytokenAndPermission(token, request);
            //Boolean result = userVisitApiService.verifytoken(token);
            //Boolean result = userVisitApiService.verifytokenByLoginIp(request,token);
            if(!result){
                return ResultUtil.error(ResultEnum.CHECKFAILPERMISSION);
            }
            try{
            if(result) {
                CloseableHttpClient httpClient = CreateHttpClient.getHttpClient();
                //后期该uri调用可变参数的接口，利用name、regNo、socialCreditNo、taxNo中的一个或多个查询index_data中的数据
                HttpPost httpPost = null;
                List<BasicNameValuePair> params = new ArrayList<>();
                if (name != null) {//123.59.198.71:8090  192.168.10.111
                    httpPost = new HttpPost("http://192.168.10.111:8090/index/accurate_name");
                    params.add(new BasicNameValuePair("name", name));
                } else if (regNo != null) {
                    httpPost = new HttpPost("http://192.168.10.111:8090/index/reg_no");
                    params.add(new BasicNameValuePair("regNo", regNo));
                } else if (socialCreditNo != null) {
                    httpPost = new HttpPost("http://192.168.10.111:8090/index/social_credit_no");
                    params.add(new BasicNameValuePair("socialCreditNo", socialCreditNo));
                } else if (taxNo != null) {
                    httpPost = new HttpPost("http://192.168.10.111:8090/index/tax_no");
                    params.add(new BasicNameValuePair("taxNo", taxNo));
                }
                UrlEncodedFormEntity entityParams = new UrlEncodedFormEntity(params, "utf-8");
                httpPost.setEntity(entityParams);
                String entityStr = null;
                //查询index_data
                HttpResponse httpResponse = null;
                try {
                    httpResponse = httpClient.execute(httpPost);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpEntity entity = httpResponse.getEntity();//取出返回结果
                StatusLine statusLine = httpResponse.getStatusLine();
                int statusCode = statusLine.getStatusCode();//执行结果状态
                if (statusCode != 200) {
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                entityStr = EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
                JSONObject jsonObject = JSONObject.parseObject(entityStr);
                Integer indexIdInt = (Integer) jsonObject.get("id");//取出indexid值
                Long indexId = indexIdInt.longValue();
                httpClient.close();
                if (indexId == null) {
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                CloseableHttpClient httpClient1 = CreateHttpClient.getHttpClient();
                HttpPost httpPost1 = new HttpPost("http://192.168.10.111:8090/getModuleDataTuoMin/" + indexId);
                HttpResponse httpResponse1 = httpClient1.execute(httpPost1);
                HttpEntity entity1 = httpResponse1.getEntity();//取出返回结果
                StatusLine statusLine1 = httpResponse1.getStatusLine();
                int statusCode1 = statusLine1.getStatusCode();//执行结果状态
                if (statusCode1 != 200) {
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                String entityStr1 = EntityUtils.toString(entity1, "utf-8");//将结果转换为字符串
                JSONObject jsonObject1 = JSONObject.parseObject(entityStr1);
                if (jsonObject1 == null) {
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                //返回结果前将数据库中的查询次数loginCount加1
                XhxUserVisitApi userVisitApi = userVisitApiService.selectLoginCount(token);
                userVisitApi.setLoginCount(userVisitApi.getLoginCount()+1);
                userVisitApi.setUpdateTime(new Date());
                int i = userVisitApiService.updateLoginCount(userVisitApi);
                if(i<1){
                    return ResultUtil.error(ResultEnum.NOT_FIND);
                }
                return ResultUtil.success(jsonObject1, "全量数据");
            }
            }catch (Exception e){
                e.printStackTrace();
                e.getMessage();
            }
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }

    //接口掉方法，url？传入参数
    @LoginRequire
    @ResponseBody
    @RequestMapping(value = "/selectpingan",produces={"application/json;charset=utf-8"})
    public Result selectpinganTuoMin(String regNo,String socialCreditNo,String taxNo,String name,String token,HttpServletRequest request) throws IOException {
        if (token == null || (name == null && regNo == null && socialCreditNo == null && taxNo == null)) {//判断是否缺少参数
            return ResultUtil.error(ResultEnum.LACK_PARAMETER);
        }
        //验证token,ip,权限是否正确
        Boolean result = userVisitApiService.verifytokenAndPermission(token, request);
        //Boolean result = userVisitApiService.verifytoken(token);
        //Boolean result = userVisitApiService.verifytokenByLoginIp(request,token);
        if(!result){
            return ResultUtil.error(ResultEnum.CHECKFAILPERMISSION);
        }
        XhxUserVisitDetail userVisitDetail=new XhxUserVisitDetail();
        userVisitDetail.setLoginTime(new Date());
        userVisitDetail.setLoginIp(GetIpUtils.getIpAddress(request));
        Long indexId = null;
        if (result) {
            long start = System.currentTimeMillis();
            CloseableHttpClient httpClient = CreateHttpClient.getHttpClient();
            //后期该uri调用可变参数的接口，利用name、regNo、socialCreditNo、taxNo中的一个或多个查询index_data中的数据
            HttpPost httpPost = null;
            List<BasicNameValuePair> params = new ArrayList<>();
            if (name != null) {//123.59.198.88:8090  192.168.10.111
                userVisitDetail.setParamName("name");
                userVisitDetail.setParamValue(name);
                httpPost = new HttpPost("http://123.59.198.88:8090/index/accurate_name");
                params.add(new BasicNameValuePair("name", name));
            } else if (regNo != null) {
                userVisitDetail.setParamName("regNo");
                userVisitDetail.setParamValue(regNo);
                httpPost = new HttpPost("http://123.59.198.88:8090/index/reg_no");
                params.add(new BasicNameValuePair("regNo", regNo));
            } else if (socialCreditNo != null) {
                userVisitDetail.setParamName("socialCreditNo");
                userVisitDetail.setParamValue(socialCreditNo);
                httpPost = new HttpPost("http://123.59.198.88:8090/index/social_credit_no");
                params.add(new BasicNameValuePair("socialCreditNo", socialCreditNo));
            } else if (taxNo != null) {
                userVisitDetail.setParamName("taxNo");
                userVisitDetail.setParamValue(taxNo);
                httpPost = new HttpPost("http://123.59.198.88:8090/index/tax_no");
                params.add(new BasicNameValuePair("taxNo", taxNo));
            }
            UrlEncodedFormEntity entityParams = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(entityParams);
            String entityStr = null;
            //查询index_data
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = httpResponse.getEntity();//取出返回结果
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();//执行结果状态
            if (statusCode != 200) {
                return ResultUtil.error(ResultEnum.NOT_FIND);
            }
            entityStr = EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
            JSONObject jsonObject = JSONObject.parseObject(entityStr);
            Integer indexIdInt = (Integer) jsonObject.get("id");//取出indexid值
            indexId = indexIdInt.longValue();
            httpClient.close();
            long time = System.currentTimeMillis()-start;
            System.out.println("查询index_data表耗时："+time);
        }
        if(indexId==null){
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        long start = System.currentTimeMillis();
        CloseableHttpClient httpClientOther = CreateHttpClient.getHttpClient();
        HttpPost httpPostOther = new HttpPost("http://123.59.198.88:8090/getPingAnData/"+indexId);//192.168.10.111
        CloseableHttpResponse response = httpClientOther.execute(httpPostOther);
        HttpEntity entity = response.getEntity();//取出返回结果
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();//执行结果状态
        if (statusCode != 200) {
            httpClientOther.close();
            userVisitDetail.setResultStatus(0);
            userVisitDetailService.insertVisit(userVisitDetail);
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        userVisitDetail.setResultStatus(1);
        userVisitDetailService.insertVisit(userVisitDetail);
        String resultStr = EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        JSONObject data = (JSONObject) jsonObject.get("data");
        httpClientOther.close();
        long time = System.currentTimeMillis()-start;
        System.out.println("查询其余表耗时："+time);
        //返回结果前将数据库中的查询次数loginCount加1
        long start1 = System.currentTimeMillis();
        XhxUserVisitApi userVisitApi = userVisitApiService.selectLoginCount(token);
        userVisitApi.setLoginCount(userVisitApi.getLoginCount()+1);
        userVisitApi.setUpdateTime(new Date());
        int i = userVisitApiService.updateLoginCount(userVisitApi);
        long time1 = System.currentTimeMillis()-start1;
        System.out.println("记录查询次数耗时："+time1);
        if(i<1){
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        return ResultUtil.success(data);
    }

}
