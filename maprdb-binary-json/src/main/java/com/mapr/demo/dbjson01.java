package com.mapr.demo;


import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import org.apache.hadoop.hbase.client.Mutation;
import org.ojai.Document;
import org.ojai.store.DocumentMutation;

import java.util.Arrays;

public class dbjson01 {

  public static void main(String[] args) {

    System.out.println("\n=== START ===");


    Table table = getTable("/apps/user_profiles_json");

    Document email1 = MapRDB.newDocument()
            .set("type", "work")
            .set("email", "jdoe@mapr.com");

    Document email2 = MapRDB.newDocument()
            .set("type", "home")
            .set("email", "jdoe@mac.com");



    Document doc = MapRDB.newDocument()
            .set("_id","jdoe")
            .set("first_name", "john")
            .set("last_name", "doe")
            .set("age", 40)
            .set("emails", Arrays.asList(email1, email2 ) );

    table.insertOrReplace(doc);


    // get first name and email
    Document docProjection = table.findById("jdoe", "first_name", "age", "emails");

    System.out.println( docProjection );




    // Increment value
    DocumentMutation mutation = MapRDB.newMutation()
            .increment("age", 1);
    table.update("jdoe", mutation);


    System.out.println("\n\nAfter increment");
    System.out.println(
     table.findById("jdoe", "first_name", "age", "emails")
    );



    System.out.println("\n=== END ===");

  }


  public static Table getTable(String tableName) {

    if (MapRDB.tableExists(tableName)) {
      return MapRDB.getTable(tableName);
    } else {
      return MapRDB.createTable(tableName);
    }


  }



  private final static String JSON_DOC = "{ " +
          "  \"_id\" : \"jdoe\", " +
          "  \"first_name\" : \"John\", " +
          "  \"last_name\" : \"Doe\", " +
          "  \"age\" : 40, " +
          "  \"emails\" : [ " +
          "    { " +
          "      \"type\" : \"work\", " +
          "      \"email\" : \"jdoe@mapr.com\" " +
          "    } " +
          "    , " +
          "    { " +
          "      \"type\" : \"home\", " +
          "      \"email\" : \"jdoe@mac.com\" " +
          "    } " +
          "  ] " +
          "}";

}
