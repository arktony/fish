/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ark.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ark.jdbc.myconnection;

/**
 *
 * @author fumes
 */
public class profileImageDAO1 {

    private static final long serialVersionUID = 187287287L;

    public byte[] getProductImage(String productId) {
        myconnection myc = new myconnection();
        Connection con = null;
        PreparedStatement stmt = null;
        byte[] productImage = null;

        try {

            con = myc.getConnection();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        try {
            stmt = con.prepareStatement("select profile from users where id=?");
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                if (rs.getBytes("profile") != null) {
                    System.out.println(".........................XX");
                    productImage = rs.getBytes("profile");
                }
            }

            rs.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(profileImageDAO1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productImage;
    }
}
