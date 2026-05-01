package com.yang.task;


import com.yang.websocket_mq.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class Scheduleds {

    @Autowired
    WebSocketServer webSocketServer;

    // 每秒控制台执行一次打印
   // @Scheduled(cron = "* * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执行：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 向客户端发送
        webSocketServer.sendToAllClient("每秒钟执行"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
