package com.grallandco.demos;

import com.grallandco.demos.mapr.util.DBUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tgrall on 09/01/16.
 */
@WebServlet("/home/*")
public class HomeServlet extends HttpServlet {

  private final static String tableName = "/apps/super_heros";

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String jsp = "/index.jsp";
    String action = request.getParameter("action");
    if (action == null) {
      action = "home";
    }

    if (action.equalsIgnoreCase("init")) {
      initData();
      request.setAttribute("message", "Table and Rows created");
    } else if (action.equalsIgnoreCase("delete")) {
      deleteAll();
      request.setAttribute("message", "Table and Rows deleted");
    }
    request.getRequestDispatcher(jsp).forward(request, response);

  }


  private void initData() throws IOException {

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

  }

  private void deleteAll() throws IOException {
    DBUtility.deleteTable(tableName);
  }



  }
