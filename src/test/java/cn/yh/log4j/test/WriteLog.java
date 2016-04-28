package cn.yh.log4j.test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.yh.log4j.model.LogBean;
import cn.yh.log4j.util.LogClient;

public class WriteLog {  
    protected static final Log logger = LogFactory.getLog(WriteLog.class);  
  
    static  int count = 0 ;
    /** 
     * @param args 
     * @throws InterruptedException  
     */  
    public static void main(String[] args) throws InterruptedException {  
    	
    	 System.out.println("雅莉姿新款女靴韩版英伦马丁靴中跟女鞋复古短靴女及踝靴潮雅莉姿新款女靴韩版英伦马丁靴中跟女鞋复古短靴女及踝靴潮雅莉姿新款".length());
         ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
        	executor.submit(new Runnable() {
                public void run() {
                	LogBean log = new LogBean();
                	log.setBrowser("gg");
                	log.setEventType("商品修改");
                	log.setLogType("2");
                	log.setRequestUri("product/update.do");
                	log.setUserId("2256");
                	log.setLogBody("We are Chinese, Taiwan is China.");
                	FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                	log.setTimestamp(format.format(new Date()));
                	log.setTimeConsuming(String.valueOf(new Random().nextInt(23456)));
                	
                	while (true) {  
                		Random ra =new Random();
                	        //每隔两秒log输出一下当前系统时间戳  
//                				ThlogClient.info(log);
                		LogClient.info("product/update.do", "支付配送方式修改", String.valueOf(ra.nextInt(10000)), "ie", "修改成功");
//                	            Thread.sleep(50);
                	            count ++ ;
                	            try {
									Thread.sleep(ra.nextInt(50));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                	        } 
                }
            });
		} 
        
        
       
    }  
}  