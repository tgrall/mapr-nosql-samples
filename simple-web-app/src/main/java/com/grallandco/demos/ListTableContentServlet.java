package com.grallandco.demos;

import com.grallandco.demos.beans.SuperHero;
import com.grallandco.demos.mapr.util.DBUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tgrall on 05/01/16.
 */
@WebServlet("/list/*")
public class ListTableContentServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    List<SuperHero> heros = new ArrayList();
    String tableName = "/apps/super_heros";

    try {
      for (Map.Entry<String, Map<String, String>> entry : DBUtility.scan(tableName).entrySet()) {
        String rowkey = entry.getKey();
        Map<String, String> values = entry.getValue();
        SuperHero hero = new SuperHero();
        hero.setId(rowkey);
        hero.setFirstName(values.get("general|first_name"));
        hero.setLastName(values.get("general|last_name"));
        hero.setAlias(values.get("general|alias"));
        hero.setPublisher(values.get("details|publisher"));
        hero.setSuperPower(values.get("general|powers"));
        hero.setCity(values.get("details|city"));
        heros.add(hero);
      }
      request.setAttribute("results", heros);
    } catch(Exception ex) {
      // table does not exist
      request.setAttribute("message", "Table "+ tableName +" does not exist.");
    }


    request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);


  }
}
