package maprdb.app;

import com.grallandco.demos.mapr.util.DBUtility;
import org.apache.commons.collections.keyvalue.MultiKey;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimpleApp {

  public static void main(String[] args) throws IOException {

    if (args.length != 1) {
      System.out.println("You MUST pass the name of the table");
      System.exit(0);
    }

    String tableName = args[0];
    String[] cf = {"general", "details"};

    DBUtility.createTable(tableName, cf);
    DBUtility.put(tableName, "jessica_jones", "general", "first_name", "Jessica");
    DBUtility.put(tableName, "jessica_jones", "general", "last_name", "Jones");
    DBUtility.put(tableName, "jessica_jones", "general", "powers", "Superhuman strength and endurance, Flight, Psionic protection");
    DBUtility.put(tableName, "jessica_jones", "details", "city", "New York");
    DBUtility.put(tableName, "jessica_jones", "details", "publisher", "Marvel");


    DBUtility.put(tableName, "daredevil", "general", "first_name", "Matt");
    DBUtility.put(tableName, "daredevil", "general", "last_name", "Murdock");
    DBUtility.put(tableName, "daredevil", "general", "alias", "Daredevil");
    DBUtility.put(tableName, "daredevil", "general", "powers", "Peak human physical and mental condition, Highly skilled acrobat and hand-to-hand combatant, Radar sense ,Superhuman senses");
    DBUtility.put(tableName, "daredevil", "details", "city", "New York");
    DBUtility.put(tableName, "daredevil", "details", "publisher", "Marvel");

    DBUtility.put(tableName, "wolverine", "general", "first_name", "James");
    DBUtility.put(tableName, "wolverine", "general", "last_name", "Howlett");
    DBUtility.put(tableName, "wolverine", "general", "alias", "Wolverine");
    DBUtility.put(tableName, "wolverine", "general", "powers", "Regenerative healing factor, Adamantium-plated skeletal structure and retractable claws");
    DBUtility.put(tableName, "wolverine", "details", "city", "Cold Lake");
    DBUtility.put(tableName, "wolverine", "details", "publisher", "Marvel");





    for (Map.Entry<String, Map<String, String>> entry : DBUtility.scan(tableName).entrySet()) {
      System.out.println( entry.getKey() );
      for (Map.Entry<String, String> row : entry.getValue().entrySet()  ) {
        System.out.println("\t"+  row.getKey() +"="+ row.getValue()) ;
      }
    }







  }


}
