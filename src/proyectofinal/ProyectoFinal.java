/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

/**
 *
 * @author margaroth
 */

    
public class ProyectoFinal {

    
    int tipo = 0;
    
//    public String url = "jdbc:mysql://192.168.1.102:3306/horarios";
//    public String user =  "otrapc";
//    public String clave = "contrasena";
    public String url = "jdbc:mysql://localhost:3306/horarios";
    public String user =  "root";
    public String clave = "";
        
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Abrir ventana de inicio de sesion
        InicioSesion is = new InicioSesion();
        is.setVisible(true);
        is.setLocationRelativeTo(null);
        is.setResizable(false);
    }
 
}
