# log4j-jsonlayout  自定义日志输出格式-json

JSONEventLayout 实现log4jjson格式日志输出

实现flume agent1 source，通过配置UserFields（需要与 flume es sink 配置相匹配）推送至flume，UserFieldValues实现自定义自定值配置，每次输出会自动加入。

### flume ###
log4j.appender.flume=org.apache.flume.clients.log4jappender.Log4jExtAppender
log4j.appender.flume.Threshold=INFO
log4j.appender.flume.layout=cn.yh.log4j.JSONEventLayout
log4j.appender.flume.layout.UserFields=user_id,event_type,log_type,log_body,level,timestamp,request_body,remote_addr,response_body,time_consuming,request_uri,status
log4j.appender.flume.layout.UserFieldValues=log_source:1
log4j.appender.flume.Hostname=192.168.56.100
log4j.appender.flume.Port=55556
log4j.appender.flume.UnsafeMode = true


