# MapR-DB & Apache Kafka

In this module you can find:

* a Producer (`com.grallandco.demos.kafka.SuperHerosProducer`) that creates JSON message and send them to the `heros` topic
* a Consumer (`com/grallandco/demos/kafka/MapRHerosConsumer.java`) that consume this message and create new entry in MapR-DB

To avoid conflicts in classpath, you should remove/ignore the Zookeeper Client of Kafka library and use the one provided by MapR-DB Client


In the current code, the Kafka informations (port, host, topic) are hard coded:

```
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
    
```
