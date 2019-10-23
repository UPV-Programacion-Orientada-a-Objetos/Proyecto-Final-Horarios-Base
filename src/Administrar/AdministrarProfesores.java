/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Control.SharedData;
import proyectofinal.*;
import Control.connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author josephjoestar
 */
public class AdministrarProfesores extends javax.swing.JFrame {

    /**
     * Creates new form AdminsitrarDirectores
     */
    ArrayList id_carreras;
    ArrayList carreras;
    String clave_usuario;
    boolean carrera1 = false;
    boolean carrera2 = false;
    boolean carrera3 = false;
    boolean carrera4 = false;

    public AdministrarProfesores() {
        initComponents();
        this.setLocationRelativeTo(null);

        bloquearCampos();
        actualizarCarreras();
        modificar_btn.setEnabled(false);
        eliminar_btn.setEnabled(false);
        compartir_btn.setEnabled(false);
        this.esconderCampos_2(false);
        actualizarTabla("");
    }

    private boolean verificarCamposVacios() {
        if (usuario_txt.getText().equals("")
                || contrasena_txt.getText().equals("")
                || nombre_txt.getText().equals("")
                || //telefono_txt.getText().equals("")||
                nivel_cb.getSelectedIndex() == 0
                || carrera_cb.getSelectedIndex() == 0
                || contrato_cb.getSelectedIndex() == 0
                || imr_txt.getText().equals("")) {
            return true;
        }
        return false;
    }

    private void esconderCampos_1(boolean verdadofalso) {
        usuario_txt.setVisible(verdadofalso);
        contrasena_txt.setVisible(verdadofalso);
        nombre_txt.setVisible(verdadofalso);
        telefono_txt.setVisible(verdadofalso);
        nivel_cb.setVisible(verdadofalso);
        carrera_cb.setVisible(verdadofalso);
        contrato_cb.setVisible(verdadofalso);
        imr_txt.setVisible(verdadofalso);
        aceptar_btn.setVisible(verdadofalso);
        cancelar_btn.setVisible(verdadofalso);
        jLabel1.setVisible(verdadofalso);
        jLabel2.setVisible(verdadofalso);
        jLabel6.setVisible(verdadofalso);
        jLabel7.setVisible(verdadofalso);
        jLabel8.setVisible(verdadofalso);
        jLabel9.setVisible(verdadofalso);
        jLabel11.setVisible(verdadofalso);
        jLabel12.setVisible(verdadofalso);
        jLabel13.setVisible(verdadofalso);
    }

    private void esconderCampos_2(boolean verdadofalso) {
        compartir_txt.setVisible(verdadofalso);
        carrera1_check.setVisible(verdadofalso);
        carrera2_check.setVisible(verdadofalso);
        carrera3_check.setVisible(verdadofalso);
        carrera4_check.setVisible(verdadofalso);
        guardar_btn.setVisible(verdadofalso);
    }

    private void bloquearCampos() {
        usuario_txt.setEnabled(false);
        contrasena_txt.setEnabled(false);
        nombre_txt.setEnabled(false);
        telefono_txt.setEnabled(false);
        nivel_cb.setEnabled(false);
        carrera_cb.setEnabled(false);
        contrato_cb.setEnabled(false);
        imr_txt.setEnabled(false);
        aceptar_btn.setEnabled(false);
        cancelar_btn.setEnabled(false);
    }

    private void desbloquearCampos() {
        usuario_txt.setEnabled(true);
        contrasena_txt.setEnabled(true);
        nombre_txt.setEnabled(true);
        telefono_txt.setEnabled(true);
        nivel_cb.setEnabled(true);
        carrera_cb.setEnabled(true);
        contrato_cb.setEnabled(true);
        imr_txt.setEnabled(true);
        aceptar_btn.setEnabled(true);
        cancelar_btn.setEnabled(true);
    }

    private void limpiarCampos() {
        usuario_txt.setText("");
        contrasena_txt.setText("");
        nombre_txt.setText("");
        telefono_txt.setText("");
        nivel_cb.setSelectedIndex(0);
        carrera_cb.setSelectedIndex(0);
        contrato_cb.setSelectedIndex(0);
        imr_txt.setText("");
    }

    private void actualizarCarreras() {
        carrera_cb.removeAllItems();
        try {
            ResultSet rs;
            connection con = new connection();
            rs = con.conectar("SELECT nombre_carrera FROM carrera");
            carrera_cb.addItem("-");
            while (rs.next()) {
                carrera_cb.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {

        }
    }

    /**
     * Función para llenar la tabla con los profesores de la base de datos.
     * Utiliza la clase connection para realizar una consulta a la tabla
     * 'usuarios' de la base de datos.
     *
     * @param nombre será llenado con "" si quiere regresar todo los usuario de
     * tipo PROF, en caso de queres buscar coincidencia en los nombres, usar
     * este parametro.
     */
    private void actualizarTabla(String nombre) {

        DefaultTableModel modelo;
        ResultSet rs;
        connection con = new connection();

        /* Se limpia el modelo de la tabla */
        modelo = (DefaultTableModel) profesores_tbl.getModel();
        for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }

        /* Se realiza la consulta a la base de datos */
        try {
            if (nombre.equals("")) {
                rs = con.conectar("SELECT nombre_usuario, clv_usuario, telefono, nivel_ads, contrato, nombre_carrera, IMR FROM usuarios NATURAL JOIN carrera WHERE tipo_usuario = 'PROF' AND idcarrera = " + SharedData.getCarrera());
            } else {
                rs = con.conectar("SELECT nombre_usuario, clv_usuario, telefono, nivel_ads, contrato, nombre_carrera, IMR FROM usuarios NATURAL JOIN carrera WHERE tipo_usuario = 'PROF' AND nombre_usuario LIKE '%" + nombre + "%' AND idcarrera = " + SharedData.getCarrera());
            }

            /* Se agregan las nuevas filas al modelo */
            while (rs.next()) {

                // Se crea un array que será una de las filas de la tabla.
                String[] fila = {rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)};

                // Se añade al modelo la fila completa.
                modelo.addRow(fila);
            }
        } catch (SQLException ex) {

        } finally {
            con.desconectar();
        }

        /* Se pone el nuevo modelo en la tabla */
        profesores_tbl.setModel(modelo);
    }

    private void insertarRegistro() {
        connection con = new connection();

        con.insert("INSERT INTO usuarios (nombre_usuario, pass_usuario, clv_usuario, telefono, nivel_ads, contrato, idcarrera, IMR) VALUES "
                + "('" + nombre_txt.getText() + "',"
                + " '" + contrasena_txt.getText() + "',"
                + " '" + usuario_txt.getText() + "',"
                + " '" + telefono_txt.getText() + "',"
                + " '" + nivel_cb.getSelectedItem().toString() + "',"
                + " '" + contrato_cb.getSelectedItem().toString() + "',"
                + " (SELECT idcarrera FROM carrera WHERE nombre_carrera = '" + carrera_cb.getSelectedItem().toString() + "'),"
                + " " + imr_txt.getText() + ")");

        String idCarrera = "";//Necesito el id de carrera, realizo una consulta para obtener este valor
        ResultSet rr = con.conectar("SELECT idcarrera FROM carrera WHERE nombre_carrera = '" + carrera_cb.getSelectedItem().toString() + "'");
        try {
            while (rr.next()) {
                idCarrera = rr.getString("idcarrera");
            }
        } catch (SQLException ex) {

        }
        insertarConfianza(usuario_txt.getText(),idCarrera);//Se envia el id carrera y la clave del usuario
        
        con.desconectar();
    }

    private void insertarConfianza(String clvU,String idCarrera){
        
        //String clvU = usuario_txt.getText();//Necesito la clave de usuario

        connection conn = new connection();
        
        ResultSet rs = conn.conectar("Select clv_plan From plan_estudios Where idcarrera = '" + idCarrera + "'");
        try {
            while (rs.next()) {

                String clvT = (rs.getString("clv_plan"));
                ResultSet rs2 = conn.conectar("Select clv_materia From materias Where clv_plan = '" + clvT + "'");
                while (rs2.next()) {
                    String clvM = rs2.getString("clv_materia");
                    conn.insert("Insert into materia_usuario2 values ('" + clvM + "','" + clvT + "','" + clvU + "','0','0')");
                }
            }
        } catch (SQLException ex) {

        }

        conn.desconectar();
    }
    
    private void eliminarConfianza(String clvU, String idCarrera){
        
        connection con = new connection();
        
        //String idCarrera = "";//Necesito el id de carrera
        /*ResultSet rr = con.conectar("SELECT idcarrera FROM carrera WHERE nombre_carrera = '" + carrera_cb.getSelectedItem().toString() + "'");
        try {
            while (rr.next()) {
                idCarrera = rr.getString("idcarrera");
            }
        } catch (SQLException ex) {

        }*/

        //String clvU = usuario_txt.getText();//Necesito la clave de usuario

        
        //
        ResultSet rs = con.conectar("Select clv_plan From plan_estudios Where idcarrera = '" + idCarrera + "'");
        try {
            while (rs.next()) {

                String clvT = (rs.getString("clv_plan"));
                ResultSet rs2 = con.conectar("Select clv_materia From materias Where clv_plan = '" + clvT + "'");
                while (rs2.next()) {
                    String clvM = rs2.getString("clv_materia");
                    con.insert("Delete from materia_usuario2 where clv_materia = '" + clvM + "' and clv_plan = '" + clvT + "' and clv_usuario = '" + clvU + "'");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    private void modificarRegistro() {
        connection con = new connection();
        con.insert("UPDATE usuarios SET"
                + " nombre_usuario = '" + nombre_txt.getText() + "',"
                + " pass_usuario = '" + contrasena_txt.getText() + "',"
                //+ " '"+ usuario_txt.getText() +"',"
                + " telefono = '" + telefono_txt.getText() + "',"
                + " nivel_ads = '" + nivel_cb.getSelectedItem().toString() + "',"
                + " contrato ='" + contrato_cb.getSelectedItem().toString() + "',"
                + " idcarrera = (SELECT idcarrera FROM carrera WHERE nombre_carrera = '" + carrera_cb.getSelectedItem().toString() + "'),"
                + " IMR = " + imr_txt.getText()
                + " WHERE clv_usuario = '" + usuario_txt.getText() + "'");

        con.desconectar();
    }

    private void eliminarRegistro() {
        connection con = new connection();

        //Primero se elimina materia_usuario porque para esto necesito los datos de usuario
        //Realizo una consulta pra obtener el idcarrera

        //Aqui estaba lo de alfredo, el metodod eliminarConfianza
        String idCarrera="";
        ResultSet rs = con.conectar("(SELECT idcarrera FROM carrera WHERE nombre_carrera = '" + carrera_cb.getSelectedItem().toString() + "')");
        try {
            idCarrera = rs.getString("idcarrera");
        } catch (SQLException ex) {

        }
        eliminarConfianza(usuario_txt.getText(),idCarrera);
        
        con.insert("DELETE FROM usuarios WHERE clv_usuario = '" + usuario_txt.getText() + "'");

        con.desconectar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        profesores_tbl = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        buscar_txt = new javax.swing.JTextField();
        buscar_btn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        carrera2_check = new javax.swing.JCheckBox();
        compartir_txt = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        usuario_txt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nombre_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        carrera_cb = new javax.swing.JComboBox<>();
        guardar_btn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        contrato_cb = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        nivel_cb = new javax.swing.JComboBox<>();
        cancelar_btn = new javax.swing.JButton();
        aceptar_btn = new javax.swing.JButton();
        telefono_txt = new javax.swing.JTextField();
        contrasena_txt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        agregar_btn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        modificar_btn = new javax.swing.JButton();
        eliminar_btn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        imr_txt = new javax.swing.JTextField();
        compartir_btn = new javax.swing.JButton();
        carrera1_check = new javax.swing.JCheckBox();
        carrera3_check = new javax.swing.JCheckBox();
        carrera4_check = new javax.swing.JCheckBox();
        mostrarTodo_btn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        LAB_nom = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        BTN_menu = new javax.swing.JButton();
        BTN_disponibilidad = new javax.swing.JButton();
        BTN_maestros = new javax.swing.JButton();
        BTN_materias = new javax.swing.JButton();
        BTN_carreras = new javax.swing.JButton();
        BTN_planes = new javax.swing.JButton();
        BTN_aulas = new javax.swing.JButton();
        BTN_grupo = new javax.swing.JButton();
        BTN_horario = new javax.swing.JButton();
        BTN_puntos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        profesores_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre de usuario", "Clave de usuario", "Teléfono", "Nivel academico", "Contrato", "Carrera", "IMR%"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        profesores_tbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        profesores_tbl.setColumnSelectionAllowed(true);
        profesores_tbl.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        profesores_tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        profesores_tbl.getTableHeader().setReorderingAllowed(false);
        profesores_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profesores_tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(profesores_tbl);
        profesores_tbl.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (profesores_tbl.getColumnModel().getColumnCount() > 0) {
            profesores_tbl.getColumnModel().getColumn(0).setMinWidth(150);
            profesores_tbl.getColumnModel().getColumn(0).setMaxWidth(250);
            profesores_tbl.getColumnModel().getColumn(1).setMinWidth(158);
            profesores_tbl.getColumnModel().getColumn(1).setMaxWidth(250);
            profesores_tbl.getColumnModel().getColumn(2).setMinWidth(80);
            profesores_tbl.getColumnModel().getColumn(2).setMaxWidth(80);
            profesores_tbl.getColumnModel().getColumn(3).setMinWidth(105);
            profesores_tbl.getColumnModel().getColumn(3).setMaxWidth(105);
            profesores_tbl.getColumnModel().getColumn(4).setMinWidth(60);
            profesores_tbl.getColumnModel().getColumn(4).setMaxWidth(60);
            profesores_tbl.getColumnModel().getColumn(5).setMinWidth(200);
            profesores_tbl.getColumnModel().getColumn(5).setMaxWidth(300);
            profesores_tbl.getColumnModel().getColumn(6).setMinWidth(45);
            profesores_tbl.getColumnModel().getColumn(6).setMaxWidth(45);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 660, -1));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel10.setText("Administrar profesores");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Ingrese nombre:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, 20));

        buscar_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_txtActionPerformed(evt);
            }
        });
        buscar_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                buscar_txtKeyTyped(evt);
            }
        });
        jPanel3.add(buscar_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 230, -1));

        buscar_btn.setText("Buscar");
        buscar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_btnActionPerformed(evt);
            }
        });
        jPanel3.add(buscar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        carrera2_check.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        carrera2_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrera2_checkActionPerformed(evt);
            }
        });
        jPanel4.add(carrera2_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        compartir_txt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        compartir_txt.setText("Compartir Profesor");
        jPanel4.add(compartir_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Usuario:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Datos de profesor");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, 20));

        usuario_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usuario_txtKeyTyped(evt);
            }
        });
        jPanel4.add(usuario_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 218, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Contraseña:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        nombre_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombre_txtKeyTyped(evt);
            }
        });
        jPanel4.add(nombre_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 218, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Teléfono:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Carrera:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, 20));

        carrera_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "ITI", "PYMES", "MANU", "MECA", "ISA" }));
        carrera_cb.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                carrera_cbPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        carrera_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrera_cbActionPerformed(evt);
            }
        });
        jPanel4.add(carrera_cb, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, -1));

        guardar_btn.setText("Guardar");
        guardar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(guardar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Contrato:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        contrato_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "PA", "PTC" }));
        contrato_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrato_cbActionPerformed(evt);
            }
        });
        jPanel4.add(contrato_cb, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 90, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Nivel academico:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        nivel_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Dr.", "M.C", "Lic." }));
        nivel_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nivel_cbActionPerformed(evt);
            }
        });
        jPanel4.add(nivel_cb, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 90, -1));

        cancelar_btn.setText("Cancelar");
        cancelar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(cancelar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, -1, -1));

        aceptar_btn.setText("Aceptar");
        aceptar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(aceptar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, -1, -1));

        telefono_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefono_txtActionPerformed(evt);
            }
        });
        telefono_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefono_txtKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefono_txtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                telefono_txtKeyReleased(evt);
            }
        });
        jPanel4.add(telefono_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 130, -1));

        contrasena_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contrasena_txtKeyTyped(evt);
            }
        });
        jPanel4.add(contrasena_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 218, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nombre Completo:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        agregar_btn.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        agregar_btn.setText("Agregar");
        agregar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(agregar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, 340, 10));

        modificar_btn.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        modificar_btn.setText("Modificar");
        modificar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(modificar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 90, -1));

        eliminar_btn.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        eliminar_btn.setText("Eliminar");
        eliminar_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_btnActionPerformed(evt);
            }
        });
        jPanel4.add(eliminar_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 80, -1));

        jLabel2.setText("IMR:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        imr_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                imr_txtKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                imr_txtKeyReleased(evt);
            }
        });
        jPanel4.add(imr_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 60, -1));

        compartir_btn.setFont(new java.awt.Font("Ubuntu", 0, 11)); // NOI18N
        compartir_btn.setText("Compartir");
        compartir_btn.setMaximumSize(new java.awt.Dimension(62, 27));
        compartir_btn.setMinimumSize(new java.awt.Dimension(62, 27));
        compartir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compartir_btnActionPerformed(evt);
            }
        });
        jPanel4.add(compartir_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 90, -1));

        carrera1_check.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        carrera1_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrera1_checkActionPerformed(evt);
            }
        });
        jPanel4.add(carrera1_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        carrera3_check.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        carrera3_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrera3_checkActionPerformed(evt);
            }
        });
        jPanel4.add(carrera3_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        carrera4_check.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        carrera4_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carrera4_checkActionPerformed(evt);
            }
        });
        jPanel4.add(carrera4_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 360, 440));

        mostrarTodo_btn.setText("Mostrar todo");
        mostrarTodo_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodo_btnActionPerformed(evt);
            }
        });
        jPanel3.add(mostrarTodo_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 1070, 610));

        jPanel2.setBackground(new java.awt.Color(162, 3, 136));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LAB_nom.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        LAB_nom.setForeground(new java.awt.Color(254, 254, 254));
        LAB_nom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/g12280.png"))); // NOI18N
        LAB_nom.setText("Director General: Fulanito de Tal");
        jPanel2.add(LAB_nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 46, 461, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/white_logo.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 110));

        jPanel1.setBackground(new java.awt.Color(56, 56, 56));
        jPanel1.setMaximumSize(new java.awt.Dimension(210, 610));
        jPanel1.setMinimumSize(new java.awt.Dimension(210, 610));
        jPanel1.setPreferredSize(new java.awt.Dimension(210, 610));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setForeground(new java.awt.Color(254, 254, 254));
        jLabel4.setText("____________________________________");
        jLabel4.setPreferredSize(new java.awt.Dimension(210, 17));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 230, -1));

        BTN_menu.setBackground(new java.awt.Color(30, 30, 30));
        BTN_menu.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        BTN_menu.setForeground(new java.awt.Color(254, 254, 254));
        BTN_menu.setText("MENÚ");
        BTN_menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_menu.setBorderPainted(false);
        BTN_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_menu.setFocusPainted(false);
        BTN_menu.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_menu.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_menu.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_menuActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 90));

        BTN_disponibilidad.setBackground(new java.awt.Color(30, 30, 30));
        BTN_disponibilidad.setForeground(new java.awt.Color(254, 254, 254));
        BTN_disponibilidad.setText("Disponibilidad");
        BTN_disponibilidad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_disponibilidad.setBorderPainted(false);
        BTN_disponibilidad.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_disponibilidad.setFocusPainted(false);
        BTN_disponibilidad.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_disponibilidad.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_disponibilidad.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_disponibilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_disponibilidadActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_disponibilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 210, 50));

        BTN_maestros.setBackground(new java.awt.Color(30, 30, 30));
        BTN_maestros.setForeground(new java.awt.Color(254, 254, 254));
        BTN_maestros.setText("Maestros");
        BTN_maestros.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_maestros.setBorderPainted(false);
        BTN_maestros.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_maestros.setFocusPainted(false);
        BTN_maestros.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_maestros.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_maestros.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_maestros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_maestrosActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_maestros, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 210, 50));

        BTN_materias.setBackground(new java.awt.Color(30, 30, 30));
        BTN_materias.setForeground(new java.awt.Color(254, 254, 254));
        BTN_materias.setText("Materias");
        BTN_materias.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_materias.setBorderPainted(false);
        BTN_materias.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_materias.setFocusPainted(false);
        BTN_materias.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_materias.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_materias.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_materias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_materiasActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_materias, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 210, 50));

        BTN_carreras.setBackground(new java.awt.Color(30, 30, 30));
        BTN_carreras.setForeground(new java.awt.Color(254, 254, 254));
        BTN_carreras.setText("Carreras");
        BTN_carreras.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_carreras.setBorderPainted(false);
        BTN_carreras.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_carreras.setFocusPainted(false);
        BTN_carreras.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_carreras.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_carreras.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_carreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_carrerasActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_carreras, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 210, 50));

        BTN_planes.setBackground(new java.awt.Color(30, 30, 30));
        BTN_planes.setForeground(new java.awt.Color(254, 254, 254));
        BTN_planes.setText("Planes de Estudio");
        BTN_planes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_planes.setBorderPainted(false);
        BTN_planes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_planes.setFocusPainted(false);
        BTN_planes.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_planes.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_planes.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_planes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_planesActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_planes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 210, 50));

        BTN_aulas.setBackground(new java.awt.Color(30, 30, 30));
        BTN_aulas.setForeground(new java.awt.Color(254, 254, 254));
        BTN_aulas.setText("Aulas");
        BTN_aulas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_aulas.setBorderPainted(false);
        BTN_aulas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_aulas.setFocusPainted(false);
        BTN_aulas.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_aulas.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_aulas.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_aulas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_aulasActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_aulas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 210, 50));

        BTN_grupo.setBackground(new java.awt.Color(30, 30, 30));
        BTN_grupo.setForeground(new java.awt.Color(254, 254, 254));
        BTN_grupo.setText("Grupos");
        BTN_grupo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_grupo.setBorderPainted(false);
        BTN_grupo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_grupo.setFocusPainted(false);
        BTN_grupo.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_grupo.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_grupo.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_grupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_grupoActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_grupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 210, 50));

        BTN_horario.setBackground(new java.awt.Color(30, 30, 30));
        BTN_horario.setForeground(new java.awt.Color(254, 254, 254));
        BTN_horario.setText("Horarios");
        BTN_horario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_horario.setBorderPainted(false);
        BTN_horario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_horario.setFocusPainted(false);
        BTN_horario.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_horario.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_horario.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_horario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_horarioActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_horario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 210, 50));

        BTN_puntos.setBackground(new java.awt.Color(30, 30, 30));
        BTN_puntos.setForeground(new java.awt.Color(254, 254, 254));
        BTN_puntos.setText("Puntos Confianza");
        BTN_puntos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        BTN_puntos.setBorderPainted(false);
        BTN_puntos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BTN_puntos.setFocusPainted(false);
        BTN_puntos.setMaximumSize(new java.awt.Dimension(210, 60));
        BTN_puntos.setMinimumSize(new java.awt.Dimension(210, 60));
        BTN_puntos.setPreferredSize(new java.awt.Dimension(210, 60));
        BTN_puntos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_puntosActionPerformed(evt);
            }
        });
        jPanel1.add(BTN_puntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 210, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 210, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscar_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscar_txtActionPerformed

    private void carrera_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carrera_cbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carrera_cbActionPerformed

    private void contrato_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrato_cbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrato_cbActionPerformed

    private void nivel_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nivel_cbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nivel_cbActionPerformed

    private void cancelar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelar_btnActionPerformed

        int resp = JOptionPane.showConfirmDialog(null, "Se perderán los datos no guardados\n¿Seguro que desea cancelar?", "Cancelar actividad", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            limpiarCampos();
            bloquearCampos();
            modificar_btn.setEnabled(false);
            eliminar_btn.setEnabled(false);
            compartir_btn.setEnabled(false);
        }
    }//GEN-LAST:event_cancelar_btnActionPerformed

    private void agregar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregar_btnActionPerformed
        limpiarCampos();
        desbloquearCampos();
        modificar_btn.setEnabled(false);
        eliminar_btn.setEnabled(false);
        compartir_btn.setEnabled(false);

        this.esconderCampos_1(true);
        this.esconderCampos_2(false);
    }//GEN-LAST:event_agregar_btnActionPerformed

    private void telefono_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefono_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefono_txtActionPerformed

    private void buscar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_btnActionPerformed
        if (buscar_txt.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Asegurese de ingresar un nombre", "Campo vacío", JOptionPane.INFORMATION_MESSAGE);
        } else {
            actualizarTabla(buscar_txt.getText());
        }
    }//GEN-LAST:event_buscar_btnActionPerformed

    private void buscar_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscar_txtKeyTyped
        if (buscar_txt.getText().length() == 50) {
            evt.consume();
        }
    }//GEN-LAST:event_buscar_txtKeyTyped

    private void mostrarTodo_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodo_btnActionPerformed
        actualizarTabla("");
    }//GEN-LAST:event_mostrarTodo_btnActionPerformed

    private void carrera_cbPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_carrera_cbPopupMenuWillBecomeVisible


    }//GEN-LAST:event_carrera_cbPopupMenuWillBecomeVisible

    private void usuario_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuario_txtKeyTyped
        if (usuario_txt.getText().length() == 50) {
            evt.consume();
        }
    }//GEN-LAST:event_usuario_txtKeyTyped

    private void aceptar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptar_btnActionPerformed

        if (!verificarCamposVacios()) {
            if (!usuario_txt.isEnabled()) {
                modificarRegistro();
            } else {
                insertarRegistro();
            }
            bloquearCampos();
            limpiarCampos();
            actualizarTabla("");
            modificar_btn.setEnabled(false);
            eliminar_btn.setEnabled(false);
            compartir_btn.setEnabled(false);

        } else {
            JOptionPane.showMessageDialog(null, "Asegurese de completar todo los campos", "Campo(s) sin llenar", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_aceptar_btnActionPerformed

    private void profesores_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profesores_tblMouseClicked
        limpiarCampos();
        bloquearCampos();

        int i = profesores_tbl.getSelectedRow();
        clave_usuario = profesores_tbl.getValueAt(i, 1).toString();
        String contrasena = "";

        connection con = new connection();
        try {
            ResultSet rs = con.conectar("SELECT pass_usuario FROM usuarios WHERE clv_usuario = '" + profesores_tbl.getValueAt(i, 1).toString() + "'");

            while (rs.next()) {
                contrasena = rs.getString(1);
            }

        } catch (SQLException ex) {

        } finally {
            con.desconectar();
        }

        usuario_txt.setText(clave_usuario);
        contrasena_txt.setText(contrasena);
        nombre_txt.setText(profesores_tbl.getValueAt(i, 0).toString());
        carrera_cb.setSelectedItem(profesores_tbl.getValueAt(i, 5));
        contrato_cb.setSelectedItem(profesores_tbl.getValueAt(i, 4));
        imr_txt.setText(profesores_tbl.getValueAt(i, 6).toString());
        nivel_cb.setSelectedItem(profesores_tbl.getValueAt(i, 3));

        if (profesores_tbl.getValueAt(i, 2) != null) {
            telefono_txt.setText(profesores_tbl.getValueAt(i, 2).toString());
        }

        modificar_btn.setEnabled(true);
        eliminar_btn.setEnabled(true);
        compartir_btn.setEnabled(true);
        this.esconderCampos_1(true);
        this.esconderCampos_2(false);


    }//GEN-LAST:event_profesores_tblMouseClicked

    private void modificar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificar_btnActionPerformed
        desbloquearCampos();
        usuario_txt.setEnabled(false);
        this.esconderCampos_1(true);
        this.esconderCampos_2(false);
    }//GEN-LAST:event_modificar_btnActionPerformed

    private void eliminar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_btnActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Está a punto de eliminar un registro de profesor\n¿Seguro que desea eliminar?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {
            eliminarRegistro();
            limpiarCampos();
            bloquearCampos();
            actualizarTabla("");
            eliminar_btn.setEnabled(false);
            modificar_btn.setEnabled(false);
            compartir_btn.setEnabled(false);
        }
    }//GEN-LAST:event_eliminar_btnActionPerformed

    private void contrasena_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contrasena_txtKeyTyped
        if (contrasena_txt.getText().length() == 41) {
            evt.consume();// TODO add your handling code here:
        }
    }//GEN-LAST:event_contrasena_txtKeyTyped

    private void nombre_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombre_txtKeyTyped
        if (nombre_txt.getText().length() == 50) {
            evt.consume();// TODO add your handling code here:
        }
    }//GEN-LAST:event_nombre_txtKeyTyped

    private void telefono_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono_txtKeyTyped
        if (telefono_txt.getText().length() == 10) {
            evt.consume();
        }

        if (evt.getKeyChar() < 48 || evt.getKeyChar() > 57) {
            evt.consume();
        }
    }//GEN-LAST:event_telefono_txtKeyTyped

    private void telefono_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono_txtKeyPressed

    }//GEN-LAST:event_telefono_txtKeyPressed

    private void telefono_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefono_txtKeyReleased

    }//GEN-LAST:event_telefono_txtKeyReleased

    private void imr_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imr_txtKeyTyped
        if (evt.getKeyChar() < 48 || evt.getKeyChar() > 57) {
            evt.consume();
        }
    }//GEN-LAST:event_imr_txtKeyTyped

    private void imr_txtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imr_txtKeyReleased
        if (!imr_txt.getText().equals("") && Integer.valueOf(imr_txt.getText()) > 100) {
            imr_txt.setText("100");
        }
    }//GEN-LAST:event_imr_txtKeyReleased

    private void compartir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compartir_btnActionPerformed
        // TODO add your handling code here:

        //Esconder los campos
        this.esconderCampos_1(false);
        this.esconderCampos_2(true);
        
        //Limpiar los checkbox
        carrera1_check.setSelected(false);
        carrera2_check.setSelected(false);
        carrera3_check.setSelected(false);
        carrera4_check.setSelected(false);

        //Cargar las carreras
        int i = profesores_tbl.getSelectedRow();
        String carrera_origen = (String) profesores_tbl.getValueAt(i, 5);

        id_carreras = new ArrayList<>();
        carreras = new ArrayList<>();
        connection con = new connection();

        try {
            ResultSet rs = con.conectar("SELECT idcarrera, nombre_carrera FROM carrera WHERE NOT nombre_carrera ='" + carrera_origen + "'");

            while (rs.next()) {
                id_carreras.add(rs.getInt("idcarrera"));
                carreras.add(rs.getString("nombre_carrera"));
            }

            for (int j = 0; j < carreras.size(); j++) {
                System.out.println(carreras.get(j));
            }

        } catch (SQLException ex) {

        } finally {
            con.desconectar();
        }

        carrera1_check.setText((String) carreras.get(0));
        carrera2_check.setText((String) carreras.get(1));
        carrera3_check.setText((String) carreras.get(2));
        carrera4_check.setText((String) carreras.get(3));
        
        //Cargar los valores de los checkbox
        ArrayList id_carreras_bd = new ArrayList<>();
        
        try {
            ResultSet rs = con.conectar("SELECT id_carrera FROM profesores_compartidos WHERE clv_usuario ='" + clave_usuario + "'");

            while (rs.next()) {
                id_carreras_bd.add(rs.getInt("id_carrera"));
            }

            for (int j = 0; j < id_carreras_bd.size(); j++) {
                for (int k = 0; k < id_carreras.size(); k++) {
                    if(id_carreras.get(k).equals(id_carreras_bd.get(j))){
                        switch(k){
                            case 0:
                                carrera1_check.setSelected(true);
                                break;
                            case 1:
                                carrera2_check.setSelected(true);
                                break;
                            case 2:
                                carrera3_check.setSelected(true);
                                break;
                            case 3:
                                carrera4_check.setSelected(true);
                                break;
                        }
                        break;
                    }
                }
            }

        } catch (SQLException ex) {

        } finally {
            con.desconectar();
        }

    }//GEN-LAST:event_compartir_btnActionPerformed

    private void carrera2_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carrera2_checkActionPerformed
        // TODO add your handling code here:
        this.carrera2 = carrera2_check.isSelected();
    }//GEN-LAST:event_carrera2_checkActionPerformed

    private void carrera1_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carrera1_checkActionPerformed
        // TODO add your handling code here:
        this.carrera1 = carrera1_check.isSelected();
    }//GEN-LAST:event_carrera1_checkActionPerformed

    private void carrera3_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carrera3_checkActionPerformed
        // TODO add your handling code here:
        this.carrera3 = carrera3_check.isSelected();
    }//GEN-LAST:event_carrera3_checkActionPerformed

    private void carrera4_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carrera4_checkActionPerformed
        // TODO add your handling code here:
        this.carrera4 = carrera4_check.isSelected();
    }//GEN-LAST:event_carrera4_checkActionPerformed

    private void guardar_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_btnActionPerformed

        //Meter los valores booleanos a un array
        ArrayList valores = new ArrayList<>();
        valores.add(this.carrera1);
        valores.add(this.carrera2);
        valores.add(this.carrera3);
        valores.add(this.carrera4);

        for (int i = 0; i < carreras.size(); i++) {
            connection con = new connection();

            String mensaje_insert = "INSERT INTO profesores_compartidos (clv_usuario, id_carrera) VALUES ('" + clave_usuario + "','" + id_carreras.get(i) + "')";
            String mensaje_delete = "DELETE FROM profesores_compartidos WHERE clv_usuario = '" + clave_usuario + "' AND id_carrera = '" + id_carreras.get(i) + "'";
            String mensaje_select = "SELECT clv_usuario, id_carrera FROM profesores_compartidos WHERE clv_usuario ='" + clave_usuario + "' AND id_carrera = " + id_carreras.get(i) + ";";
            boolean existe = false;
            ResultSet rs = con.conectar(mensaje_select);

            try {
                //Si no existe en la base de datos cumple la condicion
                if (rs.next()) {
                    existe = true;
                }
            } catch (SQLException ex) {
                
            }

            //Si el checkbox esta seleccionado
            if ((Boolean) valores.get(i)) {

                //Si no existe lo ingresamos
                if (!existe) {
                    con.insert(mensaje_insert);
                    insertarConfianza(clave_usuario, id_carreras.get(i)+"");
                }

            } else {
                //Si existe lo eliminamos
                if (existe) {
                    con.insert(mensaje_delete);
                    eliminarConfianza(clave_usuario,id_carreras.get(i)+"");
                }
            }
            con.desconectar();

        }
    }//GEN-LAST:event_guardar_btnActionPerformed

    private void BTN_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_menuActionPerformed
        // TODO add your handling code here:
        Menu abrir = new Menu();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_menuActionPerformed

    private void BTN_disponibilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_disponibilidadActionPerformed
        // TODO add your handling code here:
        AdministrarDisponibilidad abrir = new AdministrarDisponibilidad();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_disponibilidadActionPerformed

    private void BTN_maestrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_maestrosActionPerformed
        // TODO add your handling code here:
        AdministrarProfesores abrir = new AdministrarProfesores();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_maestrosActionPerformed

    private void BTN_materiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_materiasActionPerformed
        // TODO add your handling code here:
        AdministrarMaterias abrir = new AdministrarMaterias();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_materiasActionPerformed

    private void BTN_carrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_carrerasActionPerformed
        // TODO add your handling code here:
        AdministrarCarreras abrir = new AdministrarCarreras();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_carrerasActionPerformed

    private void BTN_planesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_planesActionPerformed
        // TODO add your handling code here:
        AdministrarPlanes abrir = new AdministrarPlanes();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_planesActionPerformed

    private void BTN_aulasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_aulasActionPerformed
        // TODO add your handling code here:
        AdministrarAula abrir = new AdministrarAula();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_aulasActionPerformed

    private void BTN_grupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_grupoActionPerformed
        // TODO add your handling code here:
        AdministrarGrupos abrir = new AdministrarGrupos();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_grupoActionPerformed

    private void BTN_horarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_horarioActionPerformed
        // TODO add your handling code here:
        AdministrarHorarios abrir = new AdministrarHorarios();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_horarioActionPerformed

    private void BTN_puntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_puntosActionPerformed
        // TODO add your handling code here:
        AdministrarPuntosConfianza abrir = new AdministrarPuntosConfianza();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_puntosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdministrarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarProfesores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_aulas;
    private javax.swing.JButton BTN_carreras;
    private javax.swing.JButton BTN_disponibilidad;
    private javax.swing.JButton BTN_grupo;
    private javax.swing.JButton BTN_horario;
    private javax.swing.JButton BTN_maestros;
    private javax.swing.JButton BTN_materias;
    private javax.swing.JButton BTN_menu;
    private javax.swing.JButton BTN_planes;
    private javax.swing.JButton BTN_puntos;
    private javax.swing.JLabel LAB_nom;
    private javax.swing.JButton aceptar_btn;
    private javax.swing.JButton agregar_btn;
    private javax.swing.JButton buscar_btn;
    private javax.swing.JTextField buscar_txt;
    private javax.swing.JButton cancelar_btn;
    private javax.swing.JCheckBox carrera1_check;
    private javax.swing.JCheckBox carrera2_check;
    private javax.swing.JCheckBox carrera3_check;
    private javax.swing.JCheckBox carrera4_check;
    private javax.swing.JComboBox<String> carrera_cb;
    private javax.swing.JButton compartir_btn;
    private javax.swing.JLabel compartir_txt;
    private javax.swing.JTextField contrasena_txt;
    private javax.swing.JComboBox<String> contrato_cb;
    private javax.swing.JButton eliminar_btn;
    private javax.swing.JButton guardar_btn;
    private javax.swing.JTextField imr_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton modificar_btn;
    private javax.swing.JButton mostrarTodo_btn;
    private javax.swing.JComboBox<String> nivel_cb;
    private javax.swing.JTextField nombre_txt;
    private javax.swing.JTable profesores_tbl;
    private javax.swing.JTextField telefono_txt;
    private javax.swing.JTextField usuario_txt;
    // End of variables declaration//GEN-END:variables
}
