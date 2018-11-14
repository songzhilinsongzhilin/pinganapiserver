package xhx.pinganapi.pinganapiserver.utils;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

public class HttpClientUtil {
    public static String getResult(CloseableHttpClient httpClient, String url){
            HttpGet httpGet_reginfo = new HttpGet(url);
            HttpResponse httpResponse_reginfo = null;
            try {
                httpResponse_reginfo = httpClient.execute(httpGet_reginfo);
            } catch (IOException e) {
                httpGet_reginfo.releaseConnection();
            }
        if(httpResponse_reginfo!=null){
            HttpEntity entity_reginfo = httpResponse_reginfo.getEntity();//取出返回结果
            StatusLine statusLine_reginfo = httpResponse_reginfo.getStatusLine();
            int statusCode_reginfo = statusLine_reginfo.getStatusCode();//执行结果状态
            try {
                return EntityUtils.toString(entity_reginfo,"utf-8");//将结果转换为字符串
            } catch (IOException e) {
                httpGet_reginfo.releaseConnection();
                return null;
            }finally{
                if(httpResponse_reginfo!=null){
                    try {
                        EntityUtils.consume(entity_reginfo);//由EntityUtils是否回收HttpEntity
                        ((CloseableHttpResponse) httpResponse_reginfo).close();
                        httpGet_reginfo.releaseConnection();
                    } catch (IOException e) {
                        System.out.println("关闭response失败:"+ e);
                    }
                }
            }
        }else{
            return null;
        }
    }

    public static String getResultByParams(CloseableHttpClient httpClient, String url,String prpMap) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<NameValuePair> list = new LinkedList<>();
        NameValuePair param1 = new BasicNameValuePair("prpMap", prpMap);
        list.add(param1);
        uriBuilder.setParameters(list);
        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet_reginfo = null;
        try {
            httpGet_reginfo = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpResponse httpResponse_reginfo = null;
        try {
            httpResponse_reginfo = httpClient.execute(httpGet_reginfo);
        } catch (IOException e) {
            httpGet_reginfo.releaseConnection();
        }
        HttpEntity entity = httpResponse_reginfo.getEntity();//取出返回结果
        if(httpResponse_reginfo!=null) {
            StatusLine statusLine_reginfo = httpResponse_reginfo.getStatusLine();
            int statusCode_reginfo = statusLine_reginfo.getStatusCode();//执行结果状态
            try {
                return EntityUtils.toString(entity, "utf-8");//将结果转换为字符串
            } catch (IOException e) {
                httpGet_reginfo.releaseConnection();
                return null;
            } finally {
                if (httpResponse_reginfo != null) {
                    try {
                        EntityUtils.consume(entity);//由EntityUtils是否回收HttpEntity
                        ((CloseableHttpResponse) httpResponse_reginfo).close();
                        httpGet_reginfo.releaseConnection();
                    } catch (IOException e) {
                        System.out.println("关闭response失败:" + e);
                    }
                }
            }
        }else {
            return null;
        }
    }

    public static String getResultByParams(CloseableHttpClient httpClient, String url,String prpMap,int page,int pageSize) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<NameValuePair> list = new LinkedList<>();
        NameValuePair param = new BasicNameValuePair("prpMap", prpMap);
        NameValuePair parampage = new BasicNameValuePair("page", page+"");
        NameValuePair parampageSize = new BasicNameValuePair("pageSize", pageSize+"");
        list.add(param);
        list.add(parampage);
        list.add(parampageSize);
        uriBuilder.setParameters(list);
        // 根据带参数的URI对象构建GET请求对象
        HttpGet httpGet_reginfo = null;
        try {
            httpGet_reginfo = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpResponse httpResponse_reginfo = null;
        try {
            httpResponse_reginfo = httpClient.execute(httpGet_reginfo);
        } catch (IOException e) {
            httpGet_reginfo.releaseConnection();
        }
        HttpEntity entity = httpResponse_reginfo.getEntity();//取出返回结果
        StatusLine statusLine_reginfo = httpResponse_reginfo.getStatusLine();
        int statusCode_reginfo = statusLine_reginfo.getStatusCode();//执行结果状态
        try {
            return EntityUtils.toString(entity,"utf-8");//将结果转换为字符串
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(httpResponse_reginfo!=null){
                try {
                    EntityUtils.consume(entity);//由EntityUtils是否回收HttpEntity
                    ((CloseableHttpResponse) httpResponse_reginfo).close();
                    httpGet_reginfo.releaseConnection();
                } catch (IOException e) {
                    System.out.println("关闭response失败:"+ e);
                }
            }
        }
    }
}
