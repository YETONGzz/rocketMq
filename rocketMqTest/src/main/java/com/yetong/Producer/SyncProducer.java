package com.yetong.Producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 同步发送
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // 创建一个producer，参数为Producer Group名称
        DefaultMQProducer producer = new DefaultMQProducer("sync");
        // 指定nameServer地址
        producer.setNamesrvAddr("192.168.75.128:9876");
        // 设置当发送失败时重试发送的次数，默认为2次
        producer.setRetryTimesWhenSendFailed(3);
        // 设置发送超时时限为5s，默认3s
        producer.setSendMsgTimeout(5);
        // 开启生产者
        producer.start();
        for (int i = 0; i < 100; i++) {
            byte[] body = (i + "aaa").getBytes();
            Message message = new Message("TopicTest", "test1", body);
            //设置发送消息的超时时间
            SendResult send = producer.send(message,10000);
            System.out.println(send);
        }
    }
}
