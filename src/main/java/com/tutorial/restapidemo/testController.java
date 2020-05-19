/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.restapidemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author makis
 */
@Path("testController")
public class testController {

    @GET
    @Path("/getdata") //this path is used to identify method
    @Produces(MediaType.APPLICATION_JSON)
    public List<Test> getDataInJJSON() throws ClassNotFoundException, SQLException {

        List<Test> testData = new ArrayList<>();
        Connection conn = null;
        String query = "select * from test";
        Class.forName("org.mariadb.jdbc.Driver");

        conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/restdb", "root", "root");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Test tm = new Test();
            tm.setId(rs.getInt("id"));
            tm.setName(rs.getString("name"));
            tm.setName(rs.getString("email"));
            testData.add(tm);
        }
        return testData;
    }
}
