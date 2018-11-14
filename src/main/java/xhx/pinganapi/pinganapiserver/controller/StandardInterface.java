package xhx.pinganapi.pinganapiserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xhx.pinganapi.pinganapiserver.bean.Result;
import xhx.pinganapi.pinganapiserver.config.LoginRequire;
import xhx.pinganapi.pinganapiserver.enums.ResultEnum;
import xhx.pinganapi.pinganapiserver.service.XhxUserVisitApiService;
import xhx.pinganapi.pinganapiserver.utils.CreateHttpClient;
import xhx.pinganapi.pinganapiserver.utils.GetPathUtil;
import xhx.pinganapi.pinganapiserver.utils.HttpClientUtil;
import xhx.pinganapi.pinganapiserver.utils.ResultUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Controller
@RequestMapping("/pinganStandardInterface")
public class StandardInterface {
    @Autowired
    XhxUserVisitApiService userVisitApiService;

    CloseableHttpClient httpClient = CreateHttpClient.getHttpClient();

    //3.1企业名称模糊查询
    /*
    /search/by_name
    http://123.59.198.71:8090/search/by_name?name=海康&page=1&pageSize=10
    http://192.168.11.90:8082/pinganStandardInterface/searchbyname?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&name=海康&page=1&pageSize=3
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/searchbyname")
    public Result searchbyname(String name, String token, int page, int pageSize) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/search/by_name?name=" + name + "&page=" + page + "&pageSize=" + pageSize;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        //关闭httpClient连接
        httpClient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        String msg = "查询企业字典表";
        if (jsonObject == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.2根据企业ID查询企业字典表
    /*
/index/{indexId}
http://123.59.198.71:8090/index/132886
http://192.168.11.90:8082/pinganStandardInterface/indexByIndexId?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/indexByIndexId")
    public Result indexByIndexId(Long indexId, String token) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/index/" + indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询企业字典表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.3根据企业名称查询企业字典表
    /*
/index/by_name
http://123.59.198.71:8090/index/by_name?name=传媒
http://192.168.11.90:8082/pinganStandardInterface/indexbyname?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&name=传媒
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/indexbyname")
    public Result indexbyname(String name, String token) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/index/by_name?name=" + name;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询企业字典表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.4根据企业ID和模块名称查询一条数据
    /*
/module/{indexId}/{moduleName}
http://123.59.198.71:8090/module/132886/reg_info
http://192.168.11.90:8082/pinganStandardInterface/moduleAndmoduleName?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/moduleAndmoduleName")
    public Result moduleAndmoduleName(Long indexId, String token) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/module/" + indexId + "/reg_info";
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询注册信息表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.5根据企业ID和模块名称分页查询/module_page/{indexId}/{moduleName}?page=1&pageSize=3
    /*
    /module_page/{indexId}/{moduleName}?page=1&pageSize=3
    http://123.59.198.71:8090/module_page/132886/manager_person_product?page=1&pageSize=3
http://192.168.11.90:8082/pinganStandardInterface/modulepageAndmoduleName?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&indexId=132886&moduleName=manager_person_product&page=1&pageSize=3
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/modulepageAndmoduleName")
    public Result modulepageAndmoduleName(Long indexId, String token, String moduleName, Integer page, Integer pageSize) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url;
        if (page != null && pageSize != null) {
            url = "http://123.59.198.71:8090/module_page/" + indexId + "/" + moduleName + "?page=" + page + "&pageSize=" + pageSize;
        } else {
            url = "http://123.59.198.71:8090/module_page/" + indexId + "/" + moduleName;
        }
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.6根据企业ID，模块名称分页查询排序/module_page_sort/{indexId}/{moduleName}
    /*http://192.168.11.90:8082/pinganStandardInterface/modulepagesortAndmoduleName?
    indexId=558&token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbX
    BhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.
    ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=market_outline&orderBy=daListedDate
    &sort=desc&page=1&pageSize=3*/
    @LoginRequire
    @ResponseBody
    @RequestMapping("/modulepagesortAndmoduleName")
    public Result modulepagesortAndmoduleName(Long indexId, String token, String moduleName, String orderBy, String sort, Integer page, Integer pageSize) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url;
        if (page != null && pageSize != null) {
            url = "http://123.59.198.71:8090/module_page_sort/" + indexId + "/" + moduleName + "?orderby=" + orderBy + "&sort=" + sort + "&page=" + page + "&pageSize=" + pageSize;
        } else {
            url = "http://123.59.198.71:8090/module_page_sort/" + indexId + "/" + moduleName + "?orderby=" + orderBy + "&sort=" + sort;
        }
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.7根据企业ID，模块名称查询所有数据排序/module_all/{indexId}/{moduleName}?orderBy={id}&sort={asc}
    /*
    http://192.168.11.90:8082/pinganStandardInterface/moduleallAndmoduleName?indexId=132886&token=eyJhbGciOiJIUzI1NiJ9.
    eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a
    6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=manager_person_product&orderBy=id&sort=desc
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/moduleallAndmoduleName")
    public Result moduleallAndmoduleName(Long indexId, String token, String moduleName, String orderBy, String sort) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/module_all/" + indexId + "/" + moduleName + "?orderby=" + orderBy + "&sort=" + sort;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.8多条件自定义查询/dic_List_Params/{moduleName}? prpMap={…}
    /*
    http://123.59.198.71:8090/dic_List_Params/manager_person_product?prpMap={index_id:'2000TO4000',page:1,pageSize:3}
    http://192.168.11.90:8082/pinganStandardInterface/dicListParamsAndmoduleName?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=manager_person_product&prpMap={index_id:'2000TO4000',page:1,pageSize:3}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/dicListParamsAndmoduleName")
    public Result dicListParamsAndmoduleName(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_List_Params/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.9通用统计/列表/em_pagemodule_or_count_pattern/{moduleName}? prpMap={…}
    /*http://123.59.198.71:8090/em_pagemodule_or_count_pattern/search_detail_product?prpMap={access:'list',area:'11like',pageSize:3}
http://192.168.11.90:8082/pinganStandardInterface/emPagemoduleorCountPattern?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=search_detail_product&prpMap={access:'list',area:'11like',pageSize:3}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/emPagemoduleorCountPattern")
    public Result emPagemoduleorCountPattern(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/em_pagemodule_or_count_pattern/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.10.	通用统计/列表/em_pagemodule_or_count_pattern_list/{moduleName}? prpMap={…}
    /*71未测试成功
    http://192.168.11.231:8090/em_pagemodule_or_count_pattern_list/search_detail_product?prpMap={access:'list',area:'11like',pageSize:3}
http://192.168.11.90:8082/pinganStandardInterface/emPagemoduleorCountPatternlist?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=search_detail_product&prpMap={access:'list',area:'11like',pageSize:3}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/emPagemoduleorCountPatternlist")
    public Result emPagemoduleorCountPatternlist(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/em_pagemodule_or_count_pattern_list/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.11.	商品搜索关联行业专用接口/em_pagemodule_groupby_pattern/{moduleName}? prpMap={…}
    /*71、231均未查出数据
    http://123.59.198.71:8090/em_pagemodule_groupby_pattern/import_export_goods?prpMap={s_hs_code:22042100,groupby:index_id}
    http://192.168.11.231:8090/em_pagemodule_groupby_pattern/import_export_goods?prpMap={s_hs_code:22042100,groupby:index_id}
http://192.168.11.90:8082/pinganStandardInterface/emPagemoduleGroupbyPattern?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=import_export_goods&prpMap={s_hs_code:22042100,groupby:index_id}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/emPagemoduleGroupbyPattern")
    public Result emPagemoduleGroupbyPattern(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/em_pagemodule_groupby_pattern/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }

    //3.12.	行业主要企业/em_industry_company/{moduleName}? prpMap={…}
    /*71未查出数据
    http://192.168.11.231:8090/em_industry_company/main_industry_ent_product?prpMap={code:3390}
    http://192.168.11.90:8082/pinganStandardInterface/emIndustryCompany?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=main_industry_ent_product&prpMap={code:3390}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/emIndustryCompany")
    public Result emIndustryCompany(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/em_industry_company/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.13.	法院公告和开庭公告/humanModule/{moduleName}?prpMap={…}&page=1&pageSize=10
    /*71未查出数据
    http://192.168.11.231:8090/humanModule/main_industry_ent_product?prpMap={humanId:'170',type:1}&page=1&pageSize=10
http://192.168.11.90:8082/pinganStandardInterface/humanModule?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=main_industry_ent_product&prpMap={humanId:'170',type:1}&page=1&pageSize=10
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/humanModule")
    public Result humanModule(String token, String moduleName, String prpMap,int page,int pageSize) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/humanModule/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap,page,pageSize);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.14.	人员名称模糊搜索统计/searchPerson/by_name?name={by_name}&page={page}&pageSize={pageSize}
    /*
    http://123.59.198.71:8090/searchPerson/by_name?name=陈宗年&page=1&pageSize=3
http://192.168.11.90:8082/pinganStandardInterface/searchPersonbyname?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&name=陈宗年&page=1&pageSize=3
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/searchPersonbyname")
    public Result searchPersonbyname(String token, String name,int page,int pageSize) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/searchPerson/by_name?name=" + name+"&page="+page+"&pageSize="+pageSize;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询人员表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //3.15.	根据人员ID查询公司统计/searchPersonById/by_id?id={humanid}
    /*
    http://192.168.11.231:8090/searchPersonById/by_id?id=132
    http://192.168.11.90:8082/pinganStandardInterface/searchPersonById?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&humanId=132
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/searchPersonById")
    public Result searchPersonById(String token, String humanId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/searchPersonById/by_id?id=" + humanId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询人员表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //3.16.	管理人员对外投资/humanModuleAndId/{moduleName}?humanId={humanId}?page={page}&pageSize={pageSize}
    /*
    http://123.59.198.71:8090/humanModuleAndId/shareholder?humanId=2593
http://192.168.11.90:8082/pinganStandardInterface/humanModuleAndId?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=shareholder&humanId=2593
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/humanModuleAndId")
    public Result humanModuleAndId(String token,String moduleName, String humanId,Integer page,Integer pageSize) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        if(page!=null&&pageSize!=null){
            page = page;
            pageSize=pageSize;
        }else {
            page = 1;
            pageSize=10;
        }
        String url = "http://123.59.198.71:8090/humanModuleAndId/"+moduleName+"?humanId=" + humanId+"&page="+page+"&pageSize="+pageSize;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询人员表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //3.17.	管理人员对外任职/by_humanid_external_service/{humanId}
    /*
    http://192.168.11.231:8090/by_humanid_external_service/11
    http://192.168.11.90:8082/pinganStandardInterface/byhumanidexternalservice?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&humanId=11
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/byhumanidexternalservice")
    public Result byhumanidexternalservice(String token,String humanId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/by_humanid_external_service/"+humanId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询人员表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //3.18.	股东链moduleProvider/by_indexId/{moduleName}?index_id={indexId}
    /*
    http://192.168.11.231:8090/moduleProvider/by_indexId/shareholder?index_id=132886
    http://192.168.11.90:8082/pinganStandardInterface/moduleProviderbyindexId?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=shareholder&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/moduleProviderbyindexId")
    public Result moduleProviderbyindexId(String token,String moduleName,int indexId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/moduleProvider/by_indexId/"+moduleName+"?index_id="+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询人员表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //3.19.	行业主要企业排名(1)em_main_industry_ranking/{moduleName}?prpMap={…}
    /*未查出数据
    http://123.59.198.71:8090/em_main_industry_ranking/main_industry_ent_product?prpMap={industrycode:'3990',year:2016}
    http://192.168.11.90:8082/pinganStandardInterface/emmainindustryranking?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=main_industry_ent_product&prpMap={industrycode:'3990',year:2016}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/emmainindustryranking")
    public Result emmainindustryranking(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/em_main_industry_ranking/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.20.	行业主要企业排名(2)industry_company_ranking/{moduleName}?prpMap={…}
    /*未查出数据
    http://192.168.11.231:8090/industry_company_ranking/SearchDetailProduct?prpMap={industry:3990,stype:true}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/industrycompanyranking")
    public Result industrycompanyranking(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/industry_company_ranking/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.21.	行业多Code统计累加em_param_statistic/{moduleName}?prpMap={…}
    /*
    http://192.168.11.231:8090/em_param_statistic/search_detail_product?prpMap={industrylist:['01','02','03','04','05']}
    http://192.168.11.90:8082/pinganStandardInterface/emparamstatistic?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=search_detail_product&
    prpMap={industrylist:"01,02,03,04,05"}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping(value = "/emparamstatistic",method = RequestMethod.POST)
    public Result emparamstatistic(String token, String moduleName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        JSONObject json = JSONObject.parseObject(prpMap);//{industrylist:"01,02,03,04,05"}
        String jsonValue = (String) json.get("industrylist");
        String[] strArry = jsonValue.split(",");//{"01","02","03","04","05"}
        List<String> resultList= new ArrayList<>(Arrays.asList(strArry));//[01, 02, 03, 04, 05]
        HashMap<String, Object> strHash = new HashMap<>();
        strHash.put("industrylist",resultList);//{industrylist=[01, 02, 03, 04, 05]}
        String jsonStr = JSONObject.toJSONString(strHash);
        String url = "http://192.168.11.231:8090/em_param_statistic/" + moduleName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.22.	企业对比/moduleName/{moduleName}?index_id={indexId}
    /*71无数据
    http://192.168.11.231:8090/moduleName/ent_comporation_product?index_id=132886
    http://192.168.11.90:8082/pinganStandardInterface/moduleName?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&moduleName=ent_comporation_product&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/moduleName")
    public Result moduleName(String token, String moduleName, String indexId)  {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/moduleName/" + moduleName+"?index_id="+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.23.	族谱by_id_relationship_map/{indexId}
    /*71无数据
    http://192.168.11.231:8090/by_id_relationship_map/132886
    http://192.168.11.90:8082/pinganStandardInterface/byidrelationshipmap?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/byidrelationshipmap")
    public Result byidrelationshipmap(String token, String indexId){
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/by_id_relationship_map/"+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询族谱表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //3.24.	知识产权-专利数量统计by_patent_groupby_pattype/{indexId}
    /*
    http://192.168.11.231:8090/by_patent_groupby_pattype/132886
    http://192.168.11.90:8082/pinganStandardInterface/bypatentgroupbypattype?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&indexId=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/bypatentgroupbypattype")
    public Result bypatentgroupbypattype(String token, int indexId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/by_patent_groupby_pattype/"+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询知识产权-专利表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //4.1.	根据字典库模块名称查询所有数据/dic_list/{dicName}
    /*
    http://123.59.198.71:8090/dic_list/area
    http://192.168.11.90:8082/pinganStandardInterface/diclist?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&dicName=area
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/diclist")
    public Result diclist(String token, String dicName) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_list/"+dicName;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询知识产权-专利表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //4.2.	根据模块名称自定义单条件查询/dic_list/{dicName}?{propName}={propValue}
    /*
    http://123.59.198.71:8090/dic_list/area?code=110000
    http://192.168.11.90:8082/pinganStandardInterface/dicfirst?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&dicName=area&propName=code&propValue=110000
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/diclistdicName")
    public Result diclistdicName(String token, String dicName,String propName,String propValue) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_list/"+dicName+"?"+propName+"="+propValue;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONArray jsonArray = JSONArray.parseArray(entityStr);
        String msg = "查询知识产权-专利表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonArray, msg);
    }
    //4.3.	根据模块名称单条件查询/dic_first/{dicName}?code={110000}
    /*
    http://123.59.198.71:8090/dic_first/area?code=110000
    http://192.168.11.90:8082/pinganStandardInterface/dicfirst?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&dicName=area&code=110000
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/dicfirst")
    public Result dicfirst(String token, String dicName,String code) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_first/"+dicName+"?code="+code;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询知识产权-专利表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //4.4.	字典表模糊查询/dic_List_like/{dicName}? prpMap={…}
    /*查无数据
    http://123.59.198.71:8090/dic_List_like/industry_indicator?prpMap={industry_name:’like农业like’,page:1,number:"20TO200",pageSize:10}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/dicListlike")
    public Result dicListlike(String token, String dicName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_List_like/" + dicName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + dicName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //4.5.	行业精确搜索自定义查询/统计/dic_List_Statistics_Params/{dicName}? prpMap={…}
    /*查无数据
    http://123.59.198.71:8090/dic_List_Statistics_Params/sic?prpMap={code:'A',type:list}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/dicListStatisticsParams")
    public Result dicListStatisticsParams(String token, String dicName, String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/dic_List_Statistics_Params/" + dicName;
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + dicName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //5.1.	财务比率查询/cal_industry_indicator/{indexId}
    /*
    http://123.59.198.71:8090/cal_industry_indicator/132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/calindustryindicator")
    public Result calindustryindicator(String token, int indexId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/cal_industry_indicator/"+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询财务比率表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //6.1.	进出口总额排名/indexId/{moduleName}?index_id={indexId}
    /*71无数据
    http://192.168.11.231:8090/indexId/import_export_product?index_id=132886
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/indexId")
    public Result indexId(String token, String moduleName, int indexId) throws IOException {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://192.168.11.231:8090/indexId/" + moduleName+"?index_id="+indexId;
        String entityStr = HttpClientUtil.getResult(httpClient, url);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询" + moduleName + "表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //6.2.	行业进出口总额排名/select_renkings_by_code?prpMap={…}
    /*
    http://123.59.198.71:8090/select_renkings_by_code?prpMap={type:'出口',code:'3990'}
    http://192.168.11.90:8082/pinganStandardInterface/selectrenkingsbycode?token=eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbklwIjoiMTkyLjE2OC4xMS45MCIsImNvbXBhbnkiOiLmlrDljY7kv6EiLCJpZCI6MjAzNTU1MDQyNzAzNDgyODgsInVzZXJuYW1lIjoi5a6L5b-X5p6XIn0.ax1gljrm9kzK-g4G_79q_LcKouarBwbLSGk_pyy7LC8&
    prpMap={type:'出口',code:'3990'}
     */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/selectrenkingsbycode")
    public Result selectrenkingsbycode(String token,String prpMap) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return ResultUtil.error(ResultEnum.NOT_FIND);
        }
        String url = "http://123.59.198.71:8090/select_renkings_by_code";
        String entityStr = HttpClientUtil.getResultByParams(httpClient, url, prpMap);
        JSONObject jsonObject = JSONObject.parseObject(entityStr);
        String msg = "查询行业进出口表";
        if (entityStr == null) {
            return ResultUtil.error(ResultEnum.ADD_ERROR);
        }
        return ResultUtil.success(jsonObject, msg);
    }
    //7.1.	PDF下载http://123.59.198.71:8083/index/{index_id}
/*未查询
http://192.168.11.231:8083/index/558

 */
    @LoginRequire
    @ResponseBody
    @RequestMapping("/indexPDF")
    public void indexPDF(String token, Long indexId,String localFileName) {
        //验证token是否正确
        Boolean result = userVisitApiService.verifytoken(token);
        if (!result) {
            return;
        }
        String url = "http://192.168.11.231:8083/index/" + indexId;
        HttpClient client = new HttpClient();
        GetMethod get = null;
        FileOutputStream output = null;
        try {
            get = new GetMethod(url);
            int i = client.executeMethod(get);
            if (200 == i) {
                File storeFile = new File(localFileName);
                output = new FileOutputStream(storeFile);
                output.write(get.getResponseBody());
            } else {
                System.out.println("DownLoad file occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            get.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
    }
}