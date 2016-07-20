package x.y.z.bill.adapter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import io.alpha.core.util.ResourceUtils;

/**
 * 获取外网IP
 */
public class IPNetworkUtil {
    private static final String RES_FILE = "application.properties";
    private static final String NETWORK_URL = "payment.network.url";
    private static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    private static String IP_REGEX = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
    private static int begin = 0;
    // 请求获得的数据
    private static String ipAddress = "";

    static {
        // 每隔一段时间(5分钟)就触发
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ipAddress = getWebIp(getNextScrollUrl(getNetworkUrl()));
                while (StringUtils.isBlank(ipAddress)) {
                    ipAddress = getWebIp(getNextScrollUrl(getNetworkUrl()));
                }
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String resultIP = IPNetworkUtil.getIPAddress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("请求数据为" + i + ":" + resultIP);
        }
    }

    // 获得数据,没有数据则等待
    public static String getIPAddress() {
        while (StringUtils.isBlank(ipAddress)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        return ipAddress.trim();
    }

    /**
     * 根据url解析出ip
     *
     * @param rquestUrl
     * @return
     * @author 刘清有
     */
    private static String getWebIp(String rquestUrl) {
        try {
            URL url = new URL(rquestUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");
            String webContent = "";
            while ((s = br.readLine()) != null) {
                sb.append(s + "\r\n");
            }
            br.close();
            webContent = sb.toString();
            Pattern pattern = Pattern.compile(IP_REGEX);
            Matcher matcher = pattern.matcher(webContent.trim());
            StringBuffer buffer = new StringBuffer();
            if (matcher.matches()) {
                return webContent;
            }
            while (matcher.find()) {
                buffer.append(matcher.group());
                buffer.append("\r\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按顺序进行轮询
     *
     * @return
     */
    private static String getNextScrollUrl(String urlList) {
        String result = "";
        try {
            String urls[] = urlList.split(",");
            int total = urls.length;
            result = urls[begin].toString();
            // 设置下一次begin开始的位置
            begin++;
            if (begin >= total) {
                begin = begin - total;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取全部url
     *
     * @return
     */
    private static String getNetworkUrl() {
        String networkUrl = null;
        Properties properties = new Properties();
        InputStream inStream = null;
        try {
            inStream = ResourceUtils.getResourceAsStream(RES_FILE);
            properties.load(inStream);
            networkUrl = properties.getProperty(NETWORK_URL);
        } catch (Exception e) {
            // ignore
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        return networkUrl;
    }
}