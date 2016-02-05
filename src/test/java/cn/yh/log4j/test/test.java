package cn.yh.log4j.test;

import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

public class test {

	
	
	public static void main(String[] args) {
		  FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM",
			      TimeZone.getTimeZone("Etc/UTC"));
		  long timestamp = System.currentTimeMillis();
		System.out.println(fastDateFormat.format(timestamp));
		
	}
}
