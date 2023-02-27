package com.yetong.Producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class OneWayProducer {

    public static void main(String[] args) throws RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("oneWay");
        producer.setNamesrvAddr("192.168.75.128:9876");
        producer.setSendMsgTimeout(10);
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        for (int i = 0; i < 100; i++) {
            byte[] a = ("aaa" + i).getBytes();
            Message message = new Message("TopicTest", "aa", a);
            producer.sendOneway(message);
        }
        producer.shutdown();
    }
}
