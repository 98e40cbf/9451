package x.y.z.bill.adapter.httpclient;

import java.util.Map;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.params.HttpParams;

/**
 * Created by cyh on 2016/3/31.
 */
public class HttpRequestDTO {
    private String url;
    private String httpMethod = "post";
    private HttpParams parentParams;
    private Map<String, String> header;
    private SSLSocketFactory sslSocketFactory;

    public HttpRequestDTO() {
    }

    public HttpRequestDTO(String url, String httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public HttpRequestDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public HttpRequestDTO setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpParams getParentParams() {
        return parentParams;
    }

    public HttpRequestDTO setParentParams(HttpParams parentParams) {
        this.parentParams = parentParams;
        return this;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public HttpRequestDTO setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public HttpRequestDTO setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }
}
