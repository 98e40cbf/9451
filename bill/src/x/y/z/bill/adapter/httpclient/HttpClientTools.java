package x.y.z.bill.adapter.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import x.y.z.bill.adapter.util.SerialContext;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;

public class HttpClientTools {

    private static final transient Logger logger = LoggerFactory.getLogger(HttpClientTools.class);

    private static final String UTF8 = "UTF8";

    public static HttpResult sendRequest(final Map<String, String> params, HttpRequestDTO requestDTO) throws Exception {
        List<NameValuePair> temp = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            temp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return sendRequest(temp, requestDTO, null);
    }

    public static HttpResult postHttpsRequest(String json, String url, SSLSocketFactory sslSocketFactory)
            throws Exception {
        HttpRequestDTO requestDTO = new HttpRequestDTO();
        requestDTO.setSslSocketFactory(sslSocketFactory);
        requestDTO.setUrl(url);
        requestDTO.setHttpMethod("post");
        return sendRequest(null, requestDTO, json);
    }

    public static HttpResult postHttpsRequest(final List<NameValuePair> params, String url,
            SSLSocketFactory sslSocketFactory) throws Exception {
        HttpRequestDTO requestDTO = new HttpRequestDTO();
        requestDTO.setSslSocketFactory(sslSocketFactory);
        requestDTO.setUrl(url);
        requestDTO.setHttpMethod("post");
        return sendRequest(params, requestDTO, null);
    }

    public static HttpResult getHttpsRequest(final List<NameValuePair> params, String url,
            SSLSocketFactory sslSocketFactory) throws Exception {
        HttpRequestDTO requestDTO = new HttpRequestDTO();
        requestDTO.setSslSocketFactory(sslSocketFactory);
        requestDTO.setUrl(url);
        requestDTO.setHttpMethod("get");
        return sendRequest(params, requestDTO, null);
    }

    /**
     * 发送HTTP请求
     * 
     * @param params
     * @param requestDTO
     * @param json
     * @return
     * @throws Exception
     */
    public static HttpResult sendRequest(final List<NameValuePair> params, HttpRequestDTO requestDTO, String json)
            throws Exception {
        HttpResponse response = null;
        HttpResult httpResult = null;
        long currentTime = System.currentTimeMillis();
        try {
            initSSl(requestDTO);
            DefaultHttpClient httpClient = HttpClientManager.getHttpClient();
            if (requestDTO.getParentParams() != null) {
                httpClient.setParams(requestDTO.getParentParams());
            }
            if (requestDTO.getHttpMethod().equalsIgnoreCase("post")) {
                HttpPost method = new HttpPost(requestDTO.getUrl());
                if (requestDTO.getHeader() != null && !requestDTO.getHeader().isEmpty()) {
                    for (Map.Entry<String, String> entry : requestDTO.getHeader().entrySet()) {
                        method.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                if (params != null && !params.isEmpty()) {
                    method.setEntity(new UrlEncodedFormEntity(params));
                }
                if (StringUtils.isNotBlank(json)) {
                    method.setEntity(new StringEntity(json, UTF8));
                }
                response = httpClient.execute(method);
            } else {
                HttpGet method = new HttpGet(createPlainData(params, requestDTO.getUrl(), UTF8));
                if (requestDTO.getHeader() != null && !requestDTO.getHeader().isEmpty()) {
                    for (Map.Entry<String, String> entry : requestDTO.getHeader().entrySet()) {
                        method.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                response = httpClient.execute(method);
            }

            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String responseData = HttpClientTools.toString(entity);

            httpResult = new HttpResult(statusLine.getStatusCode(), responseData);
            return httpResult;
        } catch (ConnectTimeoutException | UnknownHostException | HttpHostConnectException e) {
            logger.error("发送HTTP请求至{}出现异常.", requestDTO.getUrl(), e);
            throw e;
        } finally {
            if (httpResult != null) {
                currentTime = System.currentTimeMillis() - currentTime;
                httpResult.setExecuteTime(currentTime);
                logger.info("<<<<<< 请求响应 >>>>>>\r\n{}\r\n{}\r\n<<<<<< ---------------- >>>>>>\r\n", requestDTO.getUrl(),
                        httpResult.toString());
            }
            // 获得响应具体内容
            // 关闭输入流同时会将连接交回至连接处理
            HttpClientUtils.closeQuietly(response);
        }
    }

    /**
     * 发送HTTP请求
     * 
     * @param data
     * @param requestDTO
     * @return
     * @throws Exception
     */
    public static HttpResult sendRequest(final String data, HttpRequestDTO requestDTO) throws Exception {
        HttpResponse response = null;
        HttpResult httpResult = null;
        long currentTime = System.currentTimeMillis();
        try {
            initSSl(requestDTO);
            DefaultHttpClient httpClient = HttpClientManager.getHttpClient();
            if (requestDTO.getParentParams() != null) {
                httpClient.setParams(requestDTO.getParentParams());
            }

            HttpPost method = new HttpPost(requestDTO.getUrl());
            if (requestDTO.getHeader() != null && !requestDTO.getHeader().isEmpty()) {
                for (Map.Entry<String, String> entry : requestDTO.getHeader().entrySet()) {
                    method.addHeader(entry.getKey(), entry.getValue());
                }
            }
            method.setEntity(new StringEntity(data, UTF8));
            response = httpClient.execute(method);

            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String responseData = HttpClientTools.toString(entity);

            httpResult = new HttpResult(statusLine.getStatusCode(), responseData);
            return httpResult;
        } catch (ConnectTimeoutException | UnknownHostException | HttpHostConnectException e) {
            logger.error("[{}] - [支付系统] - 发送渠道网络故障", SerialContext.get(), e);
            throw e;
        } finally {
            if (httpResult != null) {
                currentTime = System.currentTimeMillis() - currentTime;
                httpResult.setExecuteTime(currentTime);
            }
            // 获得响应具体内容
            // 关闭输入流同时会将连接交回至连接处理
            HttpClientUtils.closeQuietly(response);
        }
    }

    private static void initSSl(HttpRequestDTO requestDTO) {
        if (StringUtils.isNotBlank(requestDTO.getUrl()) && requestDTO.getUrl().toLowerCase().startsWith("https")) {
            SSLSocketFactory sslSocketFactory = null;
            if (requestDTO.getSslSocketFactory() == null) {
                sslSocketFactory = AllowAllSSLContextFatory.getSSLContext();
            } else {
                sslSocketFactory = requestDTO.getSslSocketFactory();
            }
            HttpClientManager.register(new Scheme("https", 443, sslSocketFactory));
        }
    }

    public static String createPlainData(List<NameValuePair> params, String url, String charset)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            if (url.contains("?")) {
                if (url.endsWith("?")) {
                    sb.append("&");
                }
            } else {
                sb.append("?");
            }
            for (NameValuePair param : params) {
                sb.append(URLEncoder.encode(param.getName(), charset));
                sb.append("=");
                sb.append(URLEncoder.encode(param.getValue(), charset));
                sb.append("&");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    public static String createPlainData(Map<String, String> params, String url, String charset)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            if (url.contains("?")) {
                if (!url.endsWith("&")) {
                    sb.append("&");
                }
            } else {
                sb.append("?");
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), charset));
                sb.append("=");
                if (StringUtils.isNotBlank(entry.getValue())) {
                    sb.append(URLEncoder.encode(entry.getValue(), charset));
                }
                sb.append("&");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    public static String toString(final HttpEntity entity) throws Exception {
        if (entity == null) {
            return "";
        } else {
            Header contentEncoding = entity.getContentEncoding();
            Header contentType = entity.getContentType();
            Charset charset = getCharset(contentType);
            if (contentEncoding != null && "gzip".equals(contentEncoding.getValue().toLowerCase())) {
                return EntityUtils.toString(new GzipDecompressingEntity(entity), charset);
            } else {
                return EntityUtils.toString(entity, charset);
            }
        }
    }

    public static Charset getCharset(final Header contentType) {
        if (contentType != null) {
            String value = contentType.getValue();
            try {
                Charset.forName(value.substring(value.lastIndexOf("=") + 1));
            } catch (Exception e) {
            }
        }
        return Charset.forName(UTF8);
    }

}
