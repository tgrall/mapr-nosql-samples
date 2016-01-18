package com.grallandco.demos.kafka;


import com.grallandco.demos.mapr.util.DBUtility;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.utils.Json;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MapRHerosConsumer {

  static Logger logger = Logger.getLogger(MapRHerosConsumer.class.getName());

  // Create Table if not present
  public final static String tableName = "/apps/heros";
  public final static String[] cf = {"general", "details"};


  public static void main(String[] args) throws IOException {

    DBUtility.createTable(tableName, cf);;


    Properties props = new Properties();
    props.put("zookeeper.connect", "localhost:2181");
    props.put("group.id", "test");
    props.put("zookeeper.session.timeout.ms", "413");
    props.put("zookeeper.sync.time.ms", "203");
    props.put("auto.commit.interval.ms", "1000");//    props.put("bootstrap.servers", "localhost:9092");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("partition.assignment.strategy", "range");

    ConsumerConfig cf = new ConsumerConfig(props) ;
    ConsumerConnector consumer = Consumer.createJavaConsumerConnector(cf) ;

    String topic = "heros"  ;
    Map topicCountMap = new HashMap();
    topicCountMap.put(topic, new Integer(3));
    Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap =
            consumer.createMessageStreams(topicCountMap);
    List<KafkaStream<byte[],byte[]>> streams = consumerMap.get(topic);

    ExecutorService executor = Executors.newFixedThreadPool(3); ;
    int threadnum = 0 ;
    for(KafkaStream stream  : streams) {
      executor.execute(new ConsumerStream(stream, threadnum));
      ++threadnum ;
    }


  }
}

class ConsumerStream implements Runnable {
  private KafkaStream m_stream;
  private int m_threadNumber;


  public ConsumerStream(KafkaStream a_stream, int a_threadNumber) {
    m_threadNumber = a_threadNumber;
    m_stream = a_stream;
  }

  public void run() {
    ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
    while (it.hasNext()) {
      String message = new String(it.next().message());
      System.out.println(message);

      try {

        // get JSON document and save it into the DB as cols
        JSONObject jsonMessage = new JSONObject(message);

        String rowkey = jsonMessage.getString("_id");

        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "general", "first_name", jsonMessage.getString("first_name"));
        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "general", "last_name", jsonMessage.getString("last_name" ));
        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "general", "powers", jsonMessage.getString("powers"));
        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "details", "city", jsonMessage.getString("city" ));
        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "details", "alias", jsonMessage.getString("alias" ));
        DBUtility.put(MapRHerosConsumer.tableName, rowkey, "details", "publisher", jsonMessage.getString("publisher" ));

      } catch (IOException e) {
        e.printStackTrace();
      } catch (JSONException e) {
        e.printStackTrace();
      }

    }
    System.out.println("Shutting down Thread: " + m_threadNumber);
  }
}
