package x.y.z.bill.adapter.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ClientConnectionManager;

public class IdleConnectionMonitorThread extends Thread {

    private final ClientConnectionManager connManager;

    public IdleConnectionMonitorThread(final ClientConnectionManager connManager) {
        super();
        this.connManager = connManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(60000);
                // 关闭失效的连接
                connManager.closeExpiredConnections();
                // 可选的, 关闭30秒内不活动的连接
                connManager.closeIdleConnections(30, TimeUnit.SECONDS);
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

}