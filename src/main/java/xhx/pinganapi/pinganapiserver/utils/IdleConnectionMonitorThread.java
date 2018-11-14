package xhx.pinganapi.pinganapiserver.utils;

import org.apache.http.conn.ClientConnectionManager;

import java.util.concurrent.TimeUnit;

public class IdleConnectionMonitorThread extends Thread {
    /*private final ClientConnectionManager connMgr;
    private volatile boolean shutdown;
    public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }
    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // 关闭过期的连接
                    connMgr.closeExpiredConnections();
                    // 关闭空闲时间超过30秒的连接
                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                }
            }
        } catch (InterruptedException ex) {
            // terminate
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }*/
}
