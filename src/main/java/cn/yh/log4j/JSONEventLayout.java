package cn.yh.log4j;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import cn.yh.log4j.data.HostData;

import com.alibaba.fastjson.JSONObject;

public class JSONEventLayout extends Layout {

    private boolean locationInfo = false;
    private static String userFields;
    private static String[] userFieldArr  ;

    private static String userFieldValues;
    private boolean ignoreThrowable = false;

    private boolean activeIgnoreThrowable = ignoreThrowable;
    private static String hostname = new HostData().getHostName();
    private String threadName;
    private long timestamp;
    private String ndc;
    private Map mdc;
    private LocationInfo info;
    private HashMap<String, Object> exceptionInformation;
    private static Integer version = 1;


    private JSONObject logstashEvent;

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT_WITH_MILLIS = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
//    public static final String ADDITIONAL_DATA_PROPERTY = "net.logstash.log4j.JSONEventLayout.UserFields";

    public static String dateFormat(long timestamp) {
        return ISO_DATETIME_TIME_ZONE_FORMAT_WITH_MILLIS.format(timestamp);
    }

     

 


	/**
     * For backwards compatibility, the default is to generate location information
     * in the log messages.
     */
    public JSONEventLayout() {
        this(true);
    }

    /**
     * Creates a layout that optionally inserts location information into log messages.
     *
     * @param locationInfo whether or not to include location information in the log messages.
     */
    public JSONEventLayout(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String format(LoggingEvent loggingEvent) {
        threadName = loggingEvent.getThreadName();
        timestamp = loggingEvent.getTimeStamp();
        exceptionInformation = new HashMap<String, Object>();
//        mdc = loggingEvent.getProperties();
//        ndc = loggingEvent.getNDC();

        logstashEvent = new JSONObject();
        String whoami = this.getClass().getSimpleName();

        /**
         * All v1 of the event format requires is
         * "@timestamp" and "@version"
         * Every other field is arbitrary
         */
        logstashEvent.put("@version", version);
        logstashEvent.put("@timestamp", dateFormat(timestamp));

         
        if (StringUtils.isNotBlank(userFieldValues)) {
//            LogLog.warn("["+whoami+"] Loading UserFields from command-line. This will override any UserFields set in the log4j configuration file");
            addUserFields(userFieldValues);
        }

        /**
         * Now we start injecting our own stuff.
         */
        logstashEvent.put("source_host", hostname);
        
        String renderedmsg = loggingEvent.getRenderedMessage();
        logstashEvent.put("log_body", renderedmsg);

        if (loggingEvent.getThrowableInformation() != null) {
            final ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
            if (throwableInformation.getThrowable().getClass().getCanonicalName() != null) {
                exceptionInformation.put("exception_class", throwableInformation.getThrowable().getClass().getCanonicalName());
            }
            if (throwableInformation.getThrowable().getMessage() != null) {
                exceptionInformation.put("exception_message", throwableInformation.getThrowable().getMessage());
            }
            if (throwableInformation.getThrowableStrRep() != null) {
                String stackTrace = StringUtils.join(throwableInformation.getThrowableStrRep(), "\n");
                exceptionInformation.put("stacktrace", stackTrace);
            }
            addEventData("exception", exceptionInformation);
        }

        if (locationInfo) {
            info = loggingEvent.getLocationInformation();
            addEventData("file", info.getFileName());
            addEventData("line_number", info.getLineNumber());
            addEventData("class", info.getClassName());
            addEventData("method", info.getMethodName());
        }

        addEventData("logger_name", loggingEvent.getLoggerName());
        addEventData("mdc", mdc);
        addEventData("ndc", ndc);
        addEventData("level", loggingEvent.getLevel().toString());
        addEventData("thread_name", threadName);

        return logstashEvent.toString() + "\n";
    }

    public boolean ignoresThrowable() {
        return ignoreThrowable;
    }

    /**
     * Query whether log messages include location information.
     *
     * @return true if location information is included in log messages, false otherwise.
     */
    public boolean getLocationInfo() {
        return locationInfo;
    }

    /**
     * Set whether log messages should include location information.
     *
     * @param locationInfo true if location information should be included, false otherwise.
     */
    public void setLocationInfo(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getUserFields() { return userFields; }
    public void setUserFields(String userFields) {
    	this.userFields = userFields; 
    	addUserFieldArr(userFields);
    }
    
    public String getUserFieldValues() {
		return userFieldValues;
	}

	public void setUserFieldValues(String userFieldValues) {
		this.userFieldValues = userFieldValues;
	}

	public void activateOptions() {
        activeIgnoreThrowable = ignoreThrowable;
    }
    
    private void addUserFieldArr(String userFlds){
    	 if(userFieldArr == null && StringUtils.isNotBlank(userFlds)){
 	    	userFieldArr = userFlds.split(",");
 		}
    }

    private void addUserFields(String data) {
        if (null != data) {
            String[] pairs = data.split(",");
            for (String pair : pairs) {
                String[] userField = pair.split(":", 2);
                if (userField[0] != null) {
                    String key = userField[0];
                    String val = userField[1];
                    addEventData(key, val);
                }
            }
        }
    }
    private void addEventData(String keyname, Object keyval) {
        if (null != keyval) {
            logstashEvent.put(keyname, keyval);
        }
    }
}
