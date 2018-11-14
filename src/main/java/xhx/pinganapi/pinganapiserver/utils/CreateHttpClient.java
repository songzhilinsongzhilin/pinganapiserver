package xhx.pinganapi.pinganapiserver.utils;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLContext;
import javax.xml.ws.spi.http.HttpContext;
import java.security.NoSuchAlgorithmException;

public class CreateHttpClient {

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient;
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        //客户端和服务器建立连接的timeout
        requestConfigBuilder.setConnectTimeout(30000);
        //从连接池获取连接的timeout
        requestConfigBuilder.setConnectionRequestTimeout(30000);
        //连接建立后，request没有回应的timeout
        requestConfigBuilder.setSocketTimeout(30000);
        requestConfigBuilder.setStaleConnectionCheckEnabled(true);//检验连接是否可用
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        clientBuilder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(30000).build()); //连接建立后，request没有回应的timeout
        //clientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        //httpClient = clientBuilder.setConnectionManager(cm).build();
        httpClient = clientBuilder.build();
        return httpClient;
    }

}
