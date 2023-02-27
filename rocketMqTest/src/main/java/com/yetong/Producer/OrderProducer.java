package com.yetong.Producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class OrderProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
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
            byte[] body = ("Hi," + i).getBytes();
            Message msg = new Message("TopicTest", "TagA", body);
                                //队列选择器  mqs: 队列集合  message 消息  arg: 选择key与 send方法的三个参数相同
            SendResult send = producer.send(msg, (mqs, message, arg) -> mqs.get((Integer) arg % mqs.size()), i, 10000);
            System.out.println(send);
        }
        producer.shutdown();
    }
}
