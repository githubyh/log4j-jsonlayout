package cn.yh.log4j.data;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostData {

    public String hostName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public HostData() {
        try {
            this.hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            setHostName("unknown-host");
        }
    }
}