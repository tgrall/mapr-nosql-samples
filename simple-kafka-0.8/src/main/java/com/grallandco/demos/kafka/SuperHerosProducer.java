package com.grallandco.demos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class SuperHerosProducer {

  static Logger logger = Logger.getLogger(MapRHerosConsumer.class.getName());




  public static void main(String[] args) throws ExecutionException, InterruptedException {


    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    props.put("partition.assignment.strategy", "range");

    Producer<String, String> producer = new KafkaProducer<String, String>(
            props,
            new org.apache.kafka.common.serialization.StringSerializer(),
            new org.apache.kafka.common.serialization.StringSerializer());


    // Create Super Heros JSON String
    // Could be done with JSON API but for simplicity just a dummy String
    StringBuilder spiderman = new StringBuilder();
    spiderman
            .append("{")
            .append("'_id' : 'spiderman' ").append(",")
            .append("'first_name' : 'Peter' ").append(",")
            .append("'last_name' : 'Parker' ").append(",")
            .append("'alias' : 'Spider-Man' ").append(",")
            .append("'city' : 'New York' ").append(",")
            .append("'powers' : 'Peter can cling to most surfaces, has superhuman strength (able to lift 10 tons optimally) and is roughly 15 times more agile than a regular human.' ").append(",")
            .append("'publisher' : 'Marvel' ")
            .append("}");


    StringBuilder thor = new StringBuilder();
    thor
            .append("{")
            .append("'_id' : 'thor' ").append(",")
            .append("'first_name' : 'Thor' ").append(",")
            .append("'last_name' : 'Odinson' ").append(",")
            .append("'alias' : 'Thor' ").append(",")
            .append("'powers' : 'As the son of Odin and Gaea, Thor strength, endurance and resistance to injury are greater than the vast majority of his superhuman race.' ").append(",")
            .append("'publisher' : 'Marvel' ").append(",")
            .append("'city' : 'SFO' ")
            .append("}");



    producer.send(new ProducerRecord<String, String>( "heros", spiderman.toString() )).get(); // posting synchronously

    producer.send(new ProducerRecord<String, String>( "heros", thor.toString() )).get(); // posting synchronously



    producer.close();

  }


}
