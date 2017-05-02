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
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("Duplicates")
public class binary02StoreFile {

  public static void main(String[] args) throws IOException {

    //org.apache.commons.io.FileUtils.readFileToByteArray(File file)

    System.out.println("\n=== START ===");

    Table table = getTable("/apps/user_profiles_binary");

    Put put = new Put(Bytes.toBytes("jdoe"));
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("first_name"), Bytes.toBytes("John") );
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("flast_name"), Bytes.toBytes("Doe") );

    // Add binary content
    File presentation = new File("/tmp/presentation.pptx");
    byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(presentation);
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("presentation"), bytes );

    table.put(put);


    // Read data
    // get data
    Get get = new Get(Bytes.toBytes("jdoe"));
    Result result = table.get(get);

    // Save file to disk
    File saveFromDb = new File("/tmp/save_from_binarytable.pptx");
    org.apache.commons.io.FileUtils.writeByteArrayToFile(saveFromDb, result.getValue(  Bytes.toBytes("default") , Bytes.toBytes("presentation") ) );


    System.out.println("\n=== STOP ===");

  }


  public static Table getTable(String tableName) throws IOException {
    Configuration conf = HBaseConfiguration.create();
    Connection conn = ConnectionFactory.createConnection(conf);
    Admin admin = conn.getAdmin();

    TableName tableNameValue = TableName.valueOf(tableName);


    if (!admin.tableExists(tableNameValue)) {
      admin.createTable(new HTableDescriptor(tableNameValue).addFamily(new HColumnDescriptor("default")));
    }

    Table table = conn.getTable(tableNameValue);
    return table;
  }


}
