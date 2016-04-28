/*
 * 天虹商场股份有限公司版权所有
 */
package cn.yh.log4j.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.yh.log4j.model.LogBean;

/**
 * 日志采集客户端
 *
 */
public class LogClient {

	 protected static final Log logger = LogFactory.getLog(LogClient.class);  
    

    /**
     * 警告日志
     *
     * @param header
     * @param body
     */
    public static void warn(String requestUri, String eventType , String userId, String browser,String body) {
    	LogBean log = new LogBean();
    	log.setBrowser(browser);
    	log.setEventType(eventType);
//    	log.setLogType("2");
    	log.setRequestUri(requestUri);
    	log.setUserId(userId);
    	log.setLogBody(body);
    	FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	log.setTimestamp(format.format(new Date()));
    	log.setTimeConsuming(String.valueOf(new Random().nextInt(23456)));
    	logger.warn(log.toString());
    }
    
    /**
     * 警告日志
     *
     * @param header
     * @param body
     */
    public static void warn(LogBean logbean) {
    	logger.warn(logbean.toString());
    }

    /**
     * 错误日志
     *
     * @param header
     * @param body
     */
    public static void error(LogBean logbean) {
    	logger.error(logbean.toString());
    }
    /**
     * 警告日志
     *
     * @param header
     * @param body
     */
    public static void info(String requestUri, String eventType , String userId, String browser,String body) {
    	LogBean log = new LogBean();
    	log.setBrowser(browser);
    	log.setEventType(eventType);
//    	log.setLogType("2");
    	log.setRequestUri(requestUri);
    	log.setUserId(userId);
    	log.setLogBody(body);
    	FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	log.setTimestamp(format.format(new Date()));
    	log.setTimeConsuming(String.valueOf(new Random().nextInt(23456)));
    	logger.info(log.toString());
    }
    /**
     * 信息日志
     * 
     * @param event
     * @param body 
     */
    public static void info(LogBean logbean) {
    	logger.info(logbean.toString());
    }
    
    /**
     * 信息日志
     * 
     * @param event
     * @param body 
     */
    public static void debug(LogBean logbean) {
    	logger.debug(logbean.toString());
    }

    /**
     * 获取主机地址信息
     *
     * @return
     */
    private static String getHostAddress() {
        String hostAddress;
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
            hostAddress = inetAddress.getHostName() + "/" + inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "unknown";
        }
        return hostAddress;
    }
    
}
