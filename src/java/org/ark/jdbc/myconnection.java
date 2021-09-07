/*
 * The Ark Computer Programmers And Animators.
 */
package org.ark.jdbc;

import java.io.Serializable;
import java.sql.*;

public class myconnection implements Serializable {

    Connection connection = null;

    //method for invoking connection
    public Connection getConnection(){

        String DATABASE_URL = "jdbc:mysql://localhost:3306/fmanager";

        Connection conn = null;

        try {

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

//            connection = DriverManager.getConnection(DATABASE_URL, "bond", "the4rk");
            connection = DriverManager.getConnection(DATABASE_URL, "root", "the4rk");

            conn = connection;

        } catch (SQLException ex) {

            System.out.println("Connection To The Database Failed. " + ex);

        }

        return conn;

    }

}
