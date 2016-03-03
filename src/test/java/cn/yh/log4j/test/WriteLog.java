package cn.yh.log4j.test;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.DateUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.yh.log4j.model.LogBean;

public class WriteLog {  
    protected static final Log logger = LogFactory.getLog(WriteLog.class);  
  
    static  int count = 0 ;
    /** 
     * @param args 
     * @throws InterruptedException  
     */  
    public static void main(String[] args) throws InterruptedException {  
    	
    	 
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
        	executor.submit(new Runnable() {
                public void run() {
                	LogBean log = new LogBean();
                	log.setBrowser("gg");
                	log.setEventType("商品新增");
                	log.setLogType("1");
                	log.setRequestUri("product/add.do");
                	log.setUserId("2652");
                	log.setLogBody("We are Chinese, Taiwan is China.");
                	log.setTimestamp(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                	log.setTimeConsuming(String.valueOf(new Random().nextInt(23456)));
                	
                	while (true) {  
                	        //每隔两秒log输出一下当前系统时间戳  
                	            logger.info(log);  
//                	            Thread.sleep(50);
                	            count ++ ;
                	        } 
                }
            });
		}
        
        
       
    }  
}  