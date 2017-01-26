package com.mapr.demo;

import com.mapr.db.MapRDB;
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
import org.ojai.Document;

import java.io.IOException;
import java.util.Arrays;

public class miTables {

  public static void main(String[] args) throws IOException {

    // insert new record in MapR-DB Binary
    Table binaryTable = getBinaryTable("/apps/user_profiles_binary");
    Put put = new Put(Bytes.toBytes("jdoeTest2"));
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("first_name"), Bytes.toBytes("John") );
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("flast_name"), Bytes.toBytes("Doe") );
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("age"), Bytes.toBytes( 40L )  );
    String emailList = "{\"type\" : \"work\", \"email\" : \"jdoe@mapr.com\"},{\"type\" : \"home\",\"email\" : \"jdoe@mac.com\"}";
    put.addColumn( Bytes.toBytes("default") , Bytes.toBytes("emails"), Bytes.toBytes(emailList) );
    binaryTable.put(put);


    // insert new record in MapR-DB JSON
    com.mapr.db.Table jsonTable = getJsonTable("/apps/user_profiles_json");
    Document email1 = MapRDB.newDocument()
            .set("type", "work")
            .set("email", "jdoe@mapr.com");

    Document email2 = MapRDB.newDocument()
            .set("type", "home")
            .set("email", "jdoe@mac.com");
    Document doc = MapRDB.newDocument()
            .set("_id","jdoeTest2")
            .set("first_name", "john")
            .set("last_name", "doe")
            .set("age", 40)
            .set("emails", Arrays.asList(email1, email2 ) );
    jsonTable.insertOrReplace(doc);


    // Read data from Binary table
    Get get = new Get(Bytes.toBytes("jdoe"));
    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("first_name"));
    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("last_name"));
    get.addColumn( Bytes.toBytes("default") , Bytes.toBytes("emails"));

    Result result = binaryTable.get(get);

    System.out.println( "Binary Rowkey : jdoeTest2" );
    for (Cell cell : result.rawCells()) {
      String columnFamily = new String(CellUtil.cloneFamily(cell));
      String column = new String(CellUtil.cloneQualifier(cell));
      String value = new String(CellUtil.cloneValue(cell));
      System.out.println( String.format("\t %s : %s", column, value)  );
    }

    // Read from JSON table
    Document docProjection = jsonTable.findById("jdoeTest2", "first_name", "age", "emails");

    System.out.println("\n\nJSON _id : jdoeTest2 \n\t"+ docProjection );





  }


  public static Table getBinaryTable(String tableName) throws IOException {
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

  public static com.mapr.db.Table getJsonTable(String tableName) {

    if (MapRDB.tableExists(tableName)) {
      return MapRDB.getTable(tableName);
    } else {
      return MapRDB.createTable(tableName);
    }


  }



}
