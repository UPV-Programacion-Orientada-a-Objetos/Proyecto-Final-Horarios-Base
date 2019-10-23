/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author margaroth
 */
public class validar {
    
       
    public void validar (java.awt.event.KeyEvent evt){
        char car = evt.getKeyChar();
        if ((car >= 'a' && car <= 'z')|| (car >= 'A' && car <= 'Z')){}else{
            evt.consume();
        }
    }
    public void validarNum (java.awt.event.KeyEvent evt){
        char car = evt.getKeyChar();
        if ((car >= '0' && car <= '9')){}else{
            evt.consume();
        }
    }
    
    public String tipoUsuario(){
        return SharedData.getTipoUsuario() + " : " + SharedData.getClave();
    }
    
        public boolean validarID(int i ,String id){
        ArrayList<String> array = new ArrayList<>();
        boolean aux=false;
        try {
            connection conn = new connection();
            ResultSet rs = null;
            switch(i){
                case 1:
                    rs = conn.conectar("SELECT id FROM categorias_equipo");
                break;
                case 2:
                    rs = conn.conectar("SELECT id FROM aulas");
                break;
                case 3:
                    rs = conn.conectar("SELECT id_equipo FROM aula_equipo");
                break;
                case 4:
                    rs = conn.conectar("SELECT id FROM equipo");
                break;
                case 5:
                    rs = conn.conectar("SELECT * FROM aulas WHERE id='"+id+"'");
                break;
                case 6:
                    rs = conn.conectar("SELECT id_aula FROM aula_equipo");
                break;
            }
            array.removeAll(array);
            while(rs.next())
            {
                if(i==1){
                    array.add(rs.getString("id"));
                }else if(i==2){
                    array.add(rs.getString("id"));
                }else if(i==3){
                    array.add(rs.getString("id_equipo"));
                }else if(i==4){
                    array.add(rs.getString("id"));
                }else if(i==6){
                    array.add(rs.getString("id_aula"));
                }
            }
            try{
                for(int o=0;o<array.size();o++){
                    if (id.equals(array.get(o))){
                        if(i==1)
                        JOptionPane.showMessageDialog(null,"ID Existente, Intente de nuevo.","Alerta",JOptionPane.INFORMATION_MESSAGE);
                        if(i==2)
                        JOptionPane.showMessageDialog(null,"ID de equipo Existente, Intente de nuevo.","Alerta",JOptionPane.INFORMATION_MESSAGE);   
                        aux=true;
                    }
                }
            }catch(NullPointerException e){
            }
            rs.close();
            conn.desconectar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"El ID no existe.");
        } 
        return aux;
    }
}
