/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Optimus
 */
public class MdlConnexion {
    private static Connection cnx;
    static String id;

    private MdlConnexion() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/optimus_manage?zeroDateTimeBehavior=convertToNull","root","mysql2000");
    }

    public static Connection getCnx() throws ClassNotFoundException, SQLException {
        if (cnx == null) {
            new MdlConnexion();
        }
        return cnx;
    }

}
