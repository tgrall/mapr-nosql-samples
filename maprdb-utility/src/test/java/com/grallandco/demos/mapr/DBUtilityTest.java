package com.grallandco.demos.mapr;

import com.grallandco.demos.mapr.util.DBUtility;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.hadoop.hbase.client.HTable;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.io.IOException;
import java.util.List;
import java.util.Map;


public class DBUtilityTest {

  String[] colFamilies = {"infos", "sessions"};
  String  tableName = "/apps/user_profiles";


  @Test
  public void testUtility() throws IOException {
    DBUtility.createTable(tableName, colFamilies);


    DBUtility.put(tableName, "jdoe", "infos", "first_name", "John");
    DBUtility.put(tableName, "jdoe", "infos", "last_name", "Doe");
    DBUtility.put(tableName, "jdoe", "sessions", "application", "iPhone");
    DBUtility.put(tableName, "jdoe", "sessions", "version", "1.2.1");

    Map<String, String> rowAsMap = DBUtility.getRow(tableName, "jdoe");
    assertEquals("John", rowAsMap.get("infos|first_name"));

    DBUtility.put(tableName,  "jsmith", "infos", "first_name", "Jane");
    DBUtility.put(tableName, "jsmith", "infos", "last_name", "Smith");
    DBUtility.put(tableName, "jsmith", "sessions", "application", "S3");
    DBUtility.put(tableName, "jsmith", "sessions", "version", "1.4.1");

    Map rows = DBUtility.scan(tableName);
    assertEquals( 2 , rows.size() );

    DBUtility.delete(tableName, "jdoe");
    rows = DBUtility.scan(tableName);
    assertEquals( 1 , rows.size() );

    DBUtility.deleteTable(tableName);
  }




}
