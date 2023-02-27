package com.yetong.Producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DelayProducer {

    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("delay");
        producer.setNamesrvAddr("192.168.75.128:9876");
        producer.setRetryTimesWhenSendFailed(5);
        producer.setSendMsgTimeout(6);
        producer.start();
        for (int i = 0; i < 100; i++) {
            byte[] body = ("djis" + i).getBytes();
            Message message = new Message("TopicTest", "dalay", body);
            //设置延时等级
            message.setDelayTimeLevel(3);
            SendResult send = producer.send(message, 10000);
            System.out.println(new SimpleDateFormat("HH:ss").format(new Date()) + "  " + send);
        }

    }
}
