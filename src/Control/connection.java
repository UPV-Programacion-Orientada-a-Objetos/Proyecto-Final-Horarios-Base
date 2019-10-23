/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import proyectofinal.Menu;
import proyectofinal.ProyectoFinal;

/**
 *
 * @author margaroth
 */
public class connection {

    ProyectoFinal p = new ProyectoFinal();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public connection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(p.url, p.user, p.clave);
        } catch (Exception e) {
        }

    }

    public ResultSet conectar(String mensaje) {
        try {
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(mensaje);
            return rs;
        } catch (SQLException ex) {

        }
        return null;
    }

    public void desconectar() {
        try {
            //conn.close();
            //stmt.close();
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException ex) {

        }
    }

    public void insert(String mensaje) {

        try {
            Statement st = (Statement) conn.createStatement();
            st.executeUpdate(mensaje);
            System.out.println("Listo");
        } catch (Exception e) {

        }

    }

    public void update(String mensaje) {
        try {
            stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(mensaje);

        } catch (SQLException ex) {

        }

    }

}
