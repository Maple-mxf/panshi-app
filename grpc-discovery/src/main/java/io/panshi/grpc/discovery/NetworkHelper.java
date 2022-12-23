package io.panshi.grpc.discovery;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class NetworkHelper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkHelper.class);
    
    private static final String LOCALHOST_VALUE = "127.0.0.1";
    
    /**
     * Gets the local address to which the socket is bound.
     *
     * @param host polaris server host
     * @param port polaris server port
     * @return local ip
     */
    public static String getLocalHost(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            InetAddress address = socket.getLocalAddress();
            return address.getHostAddress();
        } catch (IOException ex) {
            LOGGER.error("getLocalHost through socket fail : {}", ex.getMessage());
            return getLocalHostExactAddress();
        }
    }
    
    /**
     * Get real local ip.
     * <p>
     * You can use getNetworkInterfaces()+getInetAddresses() to get all the IP addresses of the node, and then judge to
     * find out the site-local address, this is a recommended solution.
     *
     * @return real ip
     */
    public static String getLocalHostExactAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr.getHostAddress();
                        }
                    }
                }
            }
            return getLocalHost();
        } catch (Exception e) {
            LOGGER.error("getLocalHostExactAddress error", e);
        }
        return null;
    }
    
    /**
     * Get local ip.
     * <p>
     * There are environmental restrictions. Different environments may get different ips.
     */
    public static String getLocalHost() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (Throwable e) {
            LOGGER.error("get local host", e);
        }
        if (inetAddress == null) {
            return LOCALHOST_VALUE;
        }
        return inetAddress.getHostAddress();
    }

    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>(0);
        if (Strings.isNullOrEmpty(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
}
