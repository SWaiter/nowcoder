package com.nowcoder;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by uwangshan@163.com on 2019/1/17.
 */
public class KafkaConsumeSample {
    private final static String TOPIC = "firstTopic";

    public static void main(String[] args) {
        Properties properties = new Properties(); 
        properties.put("zookeeper.connect", "bd-31:2181,bd-38:2181,bd-39:2181");
        properties.put("group.id", "testGroup1");
        properties.put("zookeeper.session.timeout.ms", "400");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("zuto.commit.interval.ms", "1000");

        ConsumerConnector connector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(TOPIC, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(KafkaConsumeSample.TOPIC).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            System.out.println(new String(it.next().message()));
        }
    }
}
