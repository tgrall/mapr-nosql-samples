package com.mapr.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.math.BigInteger;

public class binary01 {


  public static void main(String[] args) throws IOException {

    System.out.println("\n=== START ===");


    Table table = getTable("/apps/user_profiles_hbase");
    Put put = new Put(Bytes.toBytes("jdoe"));
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("first_name"), Bytes.toBytes("John") );
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("flast_name"), Bytes.toBytes("Doe") );
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("age"), Bytes.toBytes( 40L )  );
    String emailList = "{\"type\" : \"work\", \"email\" : \"jdoe@mapr.com\"},{\"type\" : \"home\",\"email\" : \"jdoe@mac.com\"}";
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("emails"), Bytes.toBytes(emailList) );
    table.put(put);


    // get data
    Get get = new Get(Bytes.toBytes("jdoe"));
//    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("first_name"));
//    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("age"));
//    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("emails"));



    Result result = table.get(get);

    System.out.println( String.format("Rowkey : %s", "jdoe") );
    for (Cell cell : result.rawCells()) {
      String columnFamily = new String(CellUtil.cloneFamily(cell));
      String column = new String(CellUtil.cloneQualifier(cell));
      String value = new String(CellUtil.cloneValue(cell));
      System.out.println( String.format("\t %s : %s", column, value)  );
    }

    // print integer....
    System.out.println( String.format("\t age : %s (converted from byte[])",
            new BigInteger(result.getValue(  Bytes.toBytes("default") , Bytes.toBytes("age") )).intValue()
    )    );



    // Increment


    Increment incr = new Increment(Bytes.toBytes("jdoe"));
    incr.addColumn(Bytes.toBytes("default"), Bytes.toBytes("age"), 1L);
    Result resultIncr = table.increment(incr);




    Get get2 = new Get(Bytes.toBytes("jdoe"));
    get2.addColumn( Bytes.toBytes("default") , Bytes.toBytes("age"));
    Result result2 = table.get(get2);
    System.out.println(

            new BigInteger(result2.getValue(  Bytes.toBytes("default") , Bytes.toBytes("age") )).intValue()


    );





    table.close();


    System.out.println("\n=== END ===");



  }


  public static Table getTable(String tableName) throws IOException {
    Configuration conf = HBaseConfiguration.create();
    Connection conn = ConnectionFactory.createConnection(conf);
    Admin admin = conn.getAdmin();

    TableName tableNameValue = TableName.valueOf(tableName);


    if (!admin.tableExists(tableNameValue)) {
      admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor("default")));
    }

    Table table = conn.getTable(tableNameValue);
    return table;
  }



}
