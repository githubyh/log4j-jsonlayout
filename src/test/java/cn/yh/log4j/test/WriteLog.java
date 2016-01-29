package cn.yh.log4j.test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
                	 while (true) {  
                	        //每隔两秒log输出一下当前系统时间戳  
                	            logger.info(count + " 本地》》》》》》》》》》》"+new Date().toLocaleString());  
//                	            Thread.sleep(50);
                	            count ++ ;
                	        } 
                }
            });
		}
        
        
       
    }  
}  