package com.yetong.Consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SomeConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cg");
        consumer.setNamesrvAddr("192.168.75.128:9876");
        //指定consumer从第一个消息开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest", "*");

        //注册消息监听器                                  一旦broker中有了订阅的消息就会触发该方法的执行
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, consumeConcurrentlyContext) -> {
            messages.forEach(e -> System.out.println(new SimpleDateFormat("HH:ss").format(new Date()) + e));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //开始消费
        consumer.start();
    }
}
