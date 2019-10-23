/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author margaroth
 */
public class SharedData {
    public static String tipoUsuario;
    public static String clave;
    public static String carrera;

    public static String getCarrera() {
        return carrera;
    }

    public static void setCarrera(String carrera) {
        SharedData.carrera = carrera;
    }
    public static void setTipoUsuario(String tipoUsuario){
        SharedData.tipoUsuario = tipoUsuario;
    }
    
    public static String getTipoUsuario(){
        return tipoUsuario;
    }

    public static String getClave() {
        return clave;
    }

    public static void setClave(String clave) {
        SharedData.clave = clave;
    }
    
    
    
    
}
