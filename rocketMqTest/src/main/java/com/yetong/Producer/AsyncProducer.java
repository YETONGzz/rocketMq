package com.yetong.Producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * 异步发送
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 创建一个producer，参数为Producer Group名称
        DefaultMQProducer producer = new DefaultMQProducer("Async");
        // 指定nameServer地址
        producer.setNamesrvAddr("192.168.75.128:9876");
        // 指定异步发送失败后进行重试的次数
        producer.setRetryTimesWhenSendAsyncFailed(3);
        // 设置发送超时时限为5s，默认3s
        producer.setSendMsgTimeout(5);
        //设置创建的Queue数量
        producer.setDefaultTopicQueueNums(6);
        // 开启生产者
        producer.start();
        for (int i = 0; i < 100; i++) {
            byte[] body = (i + "aaa").getBytes();
            Message message = new Message("TopicAsync", "test1", body);
             producer.send(message, new SendCallback() {
                 // 当producer接收到MQ发送来的ACK后就会触发该回调方法的执行
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    throwable.printStackTrace();
                }
            },5);
        }

        TimeUnit.SECONDS.sleep(3);
        producer.shutdown();
    }

    @Test
    public void te() throws ParseException {
        String a = "20220510";
        String b = "20321202";
        Calendar calendar = new GregorianCalendar();
        Calendar c2 = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date parse = df.parse(a);
        Date parse2 = df.parse(b);
        System.out.println(parse);
        calendar.setTime(parse);
        c2.setTime(parse2);

        calendar.add(Calendar.MONTH, c2.get(Calendar.MONTH) + 1); // 从零开始的月
        calendar.add(Calendar.DATE, c2.get(Calendar.DATE));
        Date time = calendar.getTime();
        String format = df.format(time);
        System.out.println(format);
        System.out.println(calendar.getTime());
    }
}
