/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MiRender extends DefaultTableCellRenderer {

    Object celdas[][] = new Object[8][6];
    Object titulos[] = {"", "", "", "", "", "", ""};
    JTable tablaSecundaria = new JTable(celdas, titulos);

    MiRender() {

    }

    //Se reciben los valores de tablaSecundaria obtenida de la linea 561 de la clase AdministrarDisponibilidad,
    //estos valores se guardan dentro del atributo de esta clase.
    public MiRender(JTable secundaria) {
        this.tablaSecundaria = secundaria;
    }

    int contador = 0;

    //Se analiza cada campo de la tabla jTable1, getTableCellRendererComponent funciona como un iterador,
    //que recorre campo por campo obtienendo las siguientes entradas:
    // value: valor del campo.
    // isSelected: true si es campo esta seleccionado.
    // hasFocus: true si el campo tiene el foco puesto.
    // row: numero de fila.
    // column: numero de columna.
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //cell corresponde a cada celda o campo de la tabla que se esta recorriendo o con la cual se ha llamado a la clase, en este caso es llamada por JTable1.
        //Se obtiene el valor de tablaSecundaria por medio de la fila y columna de JTable1.
        //Nota: recordar que se esta recorriendo cada posición de la tabla que invoca el metodo, no solo la fila y columna
        //a la que se la ha dado clic.
        
        
        try {
            if (this.tablaSecundaria.getValueAt(row, column).equals("2")) {
                cell.setBackground(Color.green); //Se cambia el campo de jTable1 en color verde.
            } else if (this.tablaSecundaria.getValueAt(row, column).equals("0")) {
                cell.setBackground(Color.white);//Se cambia el campo de jTable1 en color blanco.
            } else if (this.tablaSecundaria.getValueAt(row, column).equals("1")) {
                cell.setBackground(Color.red);
            } else {
            }
        } catch (Exception ex) {
        }

        //Se pueden añadir más condiciones siguiendo la misma estructura.
        return cell;
    }

}
