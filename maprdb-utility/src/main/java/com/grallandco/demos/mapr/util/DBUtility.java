package com.grallandco.demos.mapr.util;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tgrall on 04/01/16.
 */
public class DBUtility {

  private static Configuration configuration = null;

  static {
    // Create the configuration
    configuration = HBaseConfiguration.create();
  }

  /**
   * Create and return the table with specific column families
   * @param tableName if the name starts with "/" is will be a MapRDB table if not an HBase table
   * @param columnFamilies list of CF names
   * @return the HTable itself
   */
  public static void createTable(String tableName, String[] columnFamilies) throws IOException {
    HBaseAdmin admin = new HBaseAdmin(configuration);
    if (admin.tableExists(tableName)) {
      System.out.println("Table "+ tableName +" exists");
    } else {
      HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
      for (int i = 0; i < columnFamilies.length; i++) {
        tableDescriptor.addFamily(new HColumnDescriptor(columnFamilies[i]));
      }
      admin.createTable(tableDescriptor);
      System.out.println("Table "+ tableName +" created");
    }
  }


  /**
   * Delete the table passed as parameter
   * @param tableName
   */
  public static void deleteTable(String tableName) throws IOException {

    HBaseAdmin admin = new HBaseAdmin(configuration);

    if (admin.tableExists(tableName)) {
      admin.deleteTable(tableName);
      System.out.println("Table "+ tableName +" deleted");
    } else {
      System.out.println("Table "+ tableName +" does not exists");

    }
  }

  /**
   * Insert/Update a new value
   * @param tableName
   * @param rowkey
   * @param columnFamily
   * @param column
   * @param value support only String for now
   * @throws IOException
   */
  public static void put(String tableName, String rowkey, String columnFamily, String column, String value) throws IOException {
    HTable table = new HTable(configuration, tableName);
    Put put = new Put(Bytes.toBytes(rowkey));
    put.add( Bytes.toBytes(columnFamily) , Bytes.toBytes(column), Bytes.toBytes(value) );
    table.put(put);
    System.out.println("Row/Col Inserted : "+ rowkey +":"+ columnFamily +":"+ column +":"+ value);
  }

  /**
   * Delete a row
   * @param tableName
   * @param rowkey
   * @throws IOException
   */
  public static void delete(String tableName, String rowkey) throws IOException {
    HTable table = new HTable(configuration, tableName);
    Delete delete = new Delete(Bytes.toBytes(rowkey));
    table.delete(delete);
    System.out.println("Row Deleted: "+ rowkey);
  }


  /**
   * Get a single row as Map using multi key (CF,C)
   * @param tableName
   * @param rowkey
   * @return
   * @throws IOException
   */
  public static Map<String,String> getRow(String tableName, String rowkey) throws IOException {
    HTable table = new HTable(configuration, tableName);
    Get get = new Get(Bytes.toBytes(rowkey));
    Result result = table.get(get);
    return getResultAsMap(result);
  }


  /**
   *
   * @param tableName
   * @return
   * @throws IOException
   */
  public static Map<String,Map<String,String>> scan(String tableName) throws IOException {
    Map returnValue = new TreeMap();
    HTable table = new HTable(configuration, tableName);
    Scan scan = new Scan();
    ResultScanner scanner = table.getScanner(scan);
    for (Result result : scanner) {
      returnValue.put( Bytes.toString(result.getRow())  ,getResultAsMap(result));
    }
    return returnValue;
  }


  /**
   *
   * @param result
   * @return
   */
  private static Map<String,String> getResultAsMap(Result result){
    Map<String,String> resultAsMap = new HashMap<String, String>();
    for (Cell cell : result.rawCells()) {
      String columnFamily = new String(CellUtil.cloneFamily(cell));
      String column = new String(CellUtil.cloneQualifier(cell));
      String value = new String(CellUtil.cloneValue(cell));
      resultAsMap.put((columnFamily +"|"+ column) , value);
    }
    return resultAsMap;
  }

}
