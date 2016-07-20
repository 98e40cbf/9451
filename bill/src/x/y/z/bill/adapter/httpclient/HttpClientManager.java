package x.y.z.bill.adapter.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

public class HttpClientManager {

    public static final int MAX_TOTAL_CONNECTIONS = 100;// 连接池里的最大连接数

    public static final int MAX_ROUTE_CONNECTIONS = 50;// 每个路由的默认最大连接数

    public static final int CONNECT_TIMEOUT = 50000;// 连接超时时间

    public static final int SOCKET_TIMEOUT = 50000;// 套接字超时时间

    public static final long CONN_MANAGER_TIMEOUT = 60000;// 连接池中 连接请求执行被阻塞的超时时间

    private static HttpParams parentParams;// http连接相关参数

    private static PoolingClientConnectionManager manager;// http线程池管理器

    private static HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(5, true);// 默认的尝试策略

    static {

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        manager = new PoolingClientConnectionManager(schemeRegistry);
        manager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        manager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        // 设置头信息,模拟浏览器
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"));
        headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
        headers.add(new BasicHeader("Accept-Language", "zh-cn,zh,en-US,en;q=0.5"));
        headers.add(new BasicHeader("Accept-Charset", "ISO-8859-1,utf-8,gbk,gb2312;q=0.7,*;q=0.7"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));

        parentParams = new BasicHttpParams();
        parentParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        parentParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        parentParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
        parentParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT);
        parentParams.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        parentParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);
        parentParams.setParameter(ClientPNames.DEFAULT_HEADERS, headers);
        parentParams.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);// 在提交请求之前
        // 测试连接是否可用

        // 链接管理器链接管理线程
        new IdleConnectionMonitorThread(manager).start();
    }

    public static DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient(manager, parentParams);
        httpClient.setHttpRequestRetryHandler(retryHandler);
        return httpClient;
    }

    public static void register(final Scheme scheme) {
        manager.getSchemeRegistry().register(scheme);
    }

    public static PoolingClientConnectionManager getManager() {
        return manager;
    }
}
