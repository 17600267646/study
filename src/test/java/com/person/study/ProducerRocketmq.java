package com.person.study;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 测试连接mq并发送消息
 */
public class ProducerRocketmq {

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("producer_group");
        // 设置NameServer的地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("第"+i+"次");
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" , "TagA" ,("Hello RocketMQ " + i).getBytes());
            //设置消息类型
            msg.putUserProperty("MessageType","normal");
            // 发送消息到一个Broker
//            producer.setSendMessageWithVIPChannel(false);
            SendResult sendResult = producer.send(msg,Long.parseLong("20000"));
            // 通过sendResult返回消息是否成功送达
            System.out.printf("返回消息"+"%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
