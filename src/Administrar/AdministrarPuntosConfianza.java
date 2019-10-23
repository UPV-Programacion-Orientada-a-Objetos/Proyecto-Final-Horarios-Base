/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import proyectofinal.*;
import Control.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author margaroth
 */
public class AdministrarPuntosConfianza extends javax.swing.JFrame {

    /**
     * Creates new form AdminsitrarDirectores
     */
    String clv_m="-.";
    String clv_p="-.";
    String clv_u="-.";
    String USER;//Usuario para realizar pruebas
    
    public AdministrarPuntosConfianza() {
                initComponents();
        this.setLocationRelativeTo(null);
        validar val = new validar();
        LAB_nom.setText(val.tipoUsuario());
        if(SharedData.getTipoUsuario().equals("Profesor")){
            BTN_maestros.setEnabled(false);
            BTN_carreras.setEnabled(false);
            BTN_planes.setEnabled(false);
            BTN_aulas.setEnabled(false);
            BTN_horario.setEnabled(false);
        }
        
        tabla_jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla_jt.rowAtPoint(e.getPoint());
                int columna = tabla_jt.columnAtPoint(e.getPoint());
                tabla_jt.setColumnSelectionInterval(columna, columna);
                tabla_jt.setRowSelectionInterval(fila, fila);

                clv_m = (String) tabla_jt.getValueAt(fila,columna);//Variable que cuarda el codigo de la materia, esto es para el insert
                
                DefaultTableCellRenderer tabla1 = new DefaultTableCellRenderer();
                String TUs = SharedData.getTipoUsuario();
                //TUs = USER;
                datosIde();
                
                //Bloque de codigo para cargar automaticamente en el campo numerico
                if(TUs.equals("Profesor") && clv_m != null){//En caso de que entre un usuario como PROFESOR
                    try {
                        //Bloque de codigo para cargar la confianza
                        connection conn = new connection();
                        ResultSet rs = conn.conectar("SELECT puntos_confianza FROM materia_usuario2" + " WHERE clv_materia = '" + clv_m + "' and clv_plan = '" + clv_p + "' and clv_usuario = '" + clv_u + "'");
                        int ban=0;
                        while(rs.next()){
                            sp_puntos.setValue(rs.getInt("puntos_confianza"));
                            ban=1;
                        }
                        if(ban==0){
                            sp_puntos.setValue(0);
                        }
                        conn.desconectar();
                    } catch (Exception ee) {
                        sp_puntos.setValue(0);
                    }
                    
                }else if (TUs.equals("Director") && clv_m != null){//En caso de que entre un usuario como DIRECTOR
                    try {
                        //Bloque de codigo para cargar la confianza
                        connection conn = new connection();
                        ResultSet rs = conn.conectar("SELECT puntos_director FROM materia_usuario2" + " WHERE clv_materia = '" + clv_m + "' and clv_plan = '" + clv_p + "' and clv_usuario = '" + clv_u + "'");
                        int ban=0;
                        while(rs.next()){
                            sp_puntos.setValue(rs.getInt("puntos_director"));
                            ban=1;
                        }
                        if(ban==0){
                            sp_puntos.setValue(0);
                        }
                        conn.desconectar();
                    } catch (Exception ee) {
                        sp_puntos.setValue(0);
                    }
                }   
            }
        });
        agregar();
    }

    void agregar() {
        connection conn;
        ResultSet rs;
        //Bloque de codigo para mostrar el valor que se tiene en confianza

        //----------------------------------------------------------------
        cb_planes.removeAllItems();
        String carrera = "";//Variable para guardar clave de carrera, esto para traer solo lo necesario
        conn = new connection();
        rs = conn.conectar("SELECT idcarrera FROM usuarios where clv_usuario = '" + SharedData.getClave() + "'");
        try {
            while (rs.next()){
                carrera = rs.getString("idcarrera");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String TUs = SharedData.getTipoUsuario();
        //TUs = USER;
        if(TUs.equals("Profesor")){//En caso de que entre un usuario como PROFESOR
            lb_planesS.show(false);
            cb_PSecundaria.show(false);
            //Cambia etiqueta
            jLabel11.setText("Plan de Estudio");
            try {
                conn = new connection();
                rs = conn.conectar("SELECT clv_plan FROM plan_estudios where idcarrera = '" + carrera + "'");
                while (rs.next()) {
                    String clave = rs.getString("clv_plan");
                    cb_planes.addItem(clave);
                }
                
            } catch (Exception e) {

            }
        }else if (TUs.equals("Director")){//En caso de que entre un usuario como DIRECTOR
            lb_planesS.show(true);
            cb_PSecundaria.show(true);
            //Cambia etiqueta
            jLabel11.setText("Profesor");
            try {
                conn = new connection();
                rs = conn.conectar("SELECT nombre_usuario FROM usuarios where idcarrera = '" + carrera + "' and tipo_usuario != 'ADMI'");
                while (rs.next()) {
                    String clave = rs.getString("nombre_usuario");
                    cb_planes.addItem(clave);
                }
                
                cb_PSecundaria.removeAllItems();
                conn = new connection();
                rs = conn.conectar("SELECT clv_plan FROM plan_estudios where idcarrera = '" + carrera + "'");
                while (rs.next()) {
                    String clave = rs.getString("clv_plan");
                    cb_PSecundaria.addItem(clave);
                }

            } catch (Exception e) {

            }
        }
        //conn.desconectar();
    }

    //Funcion para obtener los datos necesario para identificar en donde actualizar la confianza
    void datosIde(){
        connection conn = new connection();
        if(clv_m != null && clv_m.contains("(")){
            clv_m = clv_m.substring(0,clv_m.length()-4);
        }
        
        ResultSet rs;
        /*ResultSet rs = conn.conectar("SELECT clv_plan FROM materias WHERE clv_materia = '" + clv_m + "'");
        try {
            while (rs.next()) {
                clv_p = rs.getString("clv_plan");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PuntosConfianza.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        String clv_usuario="";
        String TUs = SharedData.getTipoUsuario();
        //TUs = USER;
        if(TUs.equals("Profesor")){
            clv_usuario = SharedData.getClave();
            clv_u = SharedData.getClave();
            
            clv_p = (String)cb_planes.getSelectedItem();
        }else if(TUs.equals("Director")){
            clv_usuario = (String) cb_planes.getSelectedItem();
            conn = new connection();
            rs = conn.conectar("SELECT clv_usuario FROM usuarios WHERE nombre_usuario = '" + clv_usuario + "'");
            try {
                while(rs.next()){
                    String T = rs.getString("clv_usuario");
                    clv_usuario = T;
                    clv_u = T;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            clv_p = (String)cb_PSecundaria.getSelectedItem();
        }
        //conn.desconectar();
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
        cb_planes = new javax.swing.JComboBox<>();
        lb_planesS = new javax.swing.JLabel();
        cb_PSecundaria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_jt = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        BTN_Guardar = new javax.swing.JButton();
        sp_puntos = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
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

        cb_planes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ITI-08234 Matutino", "ITI-08224 Matutino", "ITI-08223 Vespertino", "Sin Grupo ", " " }));
        cb_planes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_planesActionPerformed(evt);
            }
        });
        jPanel3.add(cb_planes, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 180, -1));

        lb_planesS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_planesS.setText("Plan de Estudio");
        jPanel3.add(lb_planesS, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 110, 20));

        cb_PSecundaria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_PSecundaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_PSecundariaActionPerformed(evt);
            }
        });
        jPanel3.add(cb_PSecundaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 180, -1));

        tabla_jt.setBackground(new java.awt.Color(254, 254, 254));
        tabla_jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_jt.setCellSelectionEnabled(true);
        tabla_jt.setIntercellSpacing(new java.awt.Dimension(15, 1));
        tabla_jt.setRowHeight(65);
        tabla_jt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tabla_jt);
        if (tabla_jt.getColumnModel().getColumnCount() > 0) {
            tabla_jt.getColumnModel().getColumn(0).setResizable(false);
            tabla_jt.getColumnModel().getColumn(1).setResizable(false);
            tabla_jt.getColumnModel().getColumn(2).setResizable(false);
            tabla_jt.getColumnModel().getColumn(3).setResizable(false);
            tabla_jt.getColumnModel().getColumn(4).setResizable(false);
            tabla_jt.getColumnModel().getColumn(5).setResizable(false);
            tabla_jt.getColumnModel().getColumn(6).setResizable(false);
            tabla_jt.getColumnModel().getColumn(7).setResizable(false);
            tabla_jt.getColumnModel().getColumn(8).setResizable(false);
            tabla_jt.getColumnModel().getColumn(9).setResizable(false);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 950, 550));

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel18.setText("Puntos de Confianza");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Plan de Estudio");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 110, 20));

        BTN_Guardar.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        BTN_Guardar.setText("Guardar");
        BTN_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_GuardarActionPerformed(evt);
            }
        });
        jPanel3.add(BTN_Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 380, 80, -1));

        sp_puntos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        sp_puntos.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        jPanel3.add(sp_puntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 310, 40, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>Puntos de<br>confianza</html>");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 240, 100, 60));

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

    private void cb_planesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_planesActionPerformed
        // TODO add your handling code here:
        
        
        //Codigo para obtener la clave del profesor actual
        /*String clv_pp="";
        connection conn3 = new connection();
        ResultSet r3 = conn3.conectar("SELECT clv_usuario FROM usuarios WHERE nombre_usuario = '" + SharedData.getClave() + "'");
        try {
            while(r3.next()){
                clv_pp = r3.getString("clv_usuario");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PuntosConfianza.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn3.desconectar();*/
        //-----------------------------------------------
        String clv_pp;
        clv_pp = SharedData.getClave();
                
        String TUs = SharedData.getTipoUsuario();
        //TUs = USER;
        if(TUs.equals("Profesor")){
            
            String clave = (String) cb_planes.getSelectedItem();

            
            DefaultTableModel dtm  = (DefaultTableModel) tabla_jt.getModel();
            int a =dtm.getRowCount()-1;
            Object rowDataInt[] = new Object[10];
            for(int i=a; i>=0; i--){
                dtm.removeRow(i);
            }
            for (int i = 0; i < 8; i++) {
                dtm.addRow(rowDataInt);
            }
            try {
                connection conn = new connection();
                connection con2 = new connection();
                int coz = 100;
                ResultSet r2;
                ResultSet rs = conn.conectar("SELECT nombre_materia,clv_materia,cuatrimestre,posicion FROM materias WHERE clv_plan = '" + clave + "';");
                while (rs.next()) {
                    String clv_materia  = rs.getString("clv_materia");
                    String nombre_m = rs.getString("nombre_materia");
                    int cuatri  = rs.getInt("cuatrimestre");
                    int posicion  = rs.getInt("posicion");
                    r2 = conn.conectar("SELECT puntos_confianza FROM materia_usuario2 WHERE clv_materia = '" + clv_materia + "' and clv_plan = '" + clave + "' and clv_usuario = '" + clv_pp + "'");
                    while(r2.next()){
                        coz = r2.getInt("puntos_confianza");
                    }
                    
                    tabla_jt.setValueAt("<html><body>" + "\t(" + coz + ")" + nombre_m + "<br><br></body></html>", posicion-1, cuatri-1);
                }
                conn.desconectar();
            } catch (Exception e) {
                
            }
        }
        //cb_PSecundaria.actionPerformed(evt);
        if(TUs.equals("Director")){
            cb_PSecundariaActionPerformed(evt);//Se llama a la funcion para actualizar la tabla
        }
        
    }//GEN-LAST:event_cb_planesActionPerformed

    private void cb_PSecundariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_PSecundariaActionPerformed
        // TODO add your handling code here:

        
        //Codigo para obtener la clave del profesor actual
        String clv_pp="";
        connection conn3 = new connection();
        ResultSet r3 = conn3.conectar("SELECT clv_usuario FROM usuarios WHERE nombre_usuario = '" + (String)cb_planes.getSelectedItem() + "'");
        try {
            while(r3.next()){
                clv_pp = r3.getString("clv_usuario");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn3.desconectar();
        //-----------------------------------------------
        
        String TUs = SharedData.getTipoUsuario();
        //TUs = USER;
        if(TUs.equals("Director")){
            
            String clave = (String) cb_PSecundaria.getSelectedItem();
            
            
            DefaultTableModel dtm  = (DefaultTableModel) tabla_jt.getModel();
            int a =dtm.getRowCount()-1;
            Object rowDataInt[] = new Object[10];
            for(int i=a; i>=0; i--){
                dtm.removeRow(i);
            }
            for (int i = 0; i < 8; i++) {
                dtm.addRow(rowDataInt);
            }
            try {
                connection conn = new connection();
                connection con2 = new connection();
                ResultSet r2;
                int coz=100;
                ResultSet rs = conn.conectar("SELECT nombre_materia,clv_materia,cuatrimestre,posicion FROM materias WHERE clv_plan = '" + clave + "';");
                while (rs.next()) {
                    String clv_materia  = rs.getString("clv_materia");
                    String nombre_m = rs.getString("nombre_materia");
                    int cuatri  = rs.getInt("cuatrimestre");
                    int posicion  = rs.getInt("posicion");
                    
                    r2 = conn.conectar("SELECT puntos_director FROM materia_usuario2 WHERE clv_materia = '" + clv_materia + "' and clv_plan = '" + clave + "' and clv_usuario = '" + clv_pp + "'");
                    while(r2.next()){
                        coz = r2.getInt("puntos_director");
                    }
                    
                    
                    tabla_jt.setValueAt("<html><body>" + "\t(" + coz + ")" + nombre_m + "<br><br></body></html>", posicion-1, cuatri-1);
                }
                //con2.desconectar();
                //conn.desconectar();
            } catch (Exception e) {
                
            }
        }
    }//GEN-LAST:event_cb_PSecundariaActionPerformed

    private void BTN_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_GuardarActionPerformed
        // TODO add your handling code here:
        
        datosIde();
        connection conn = new connection();
        
        String TUs = SharedData.getTipoUsuario();
        
        if(clv_m == null){
            JOptionPane.showMessageDialog(null, "Seleccione una materia");
        }
        else{
            int Puntos = (int) sp_puntos.getValue();

            conn = new connection();
            try {
                if(TUs.equals("Profesor")){
                    conn.insert("UPDATE materia_usuario2 SET puntos_confianza = '" + Puntos + "' WHERE clv_materia = '" + clv_m + "' and clv_plan = '" + clv_p + "' and clv_usuario = '" + clv_u + "'");
                }else if (TUs.equals("Director")){
                    conn.insert("UPDATE materia_usuario2 SET puntos_director = '" + Puntos + "' WHERE clv_materia = '" + clv_m + "' and clv_plan = '" + clv_p + "' and clv_usuario = '" + clv_u + "'");
                    
                }
            } catch (Exception ex) {
            }
            
        }
        //conn.desconectar();
        if(TUs.equals("Director")){
            cb_PSecundariaActionPerformed(evt);
        }else if (TUs.equals("Profesor")){
            cb_planesActionPerformed(evt);
        }
        
    }//GEN-LAST:event_BTN_GuardarActionPerformed

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
            java.util.logging.Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarPuntosConfianza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarPuntosConfianza().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_Guardar;
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
    private javax.swing.JComboBox<String> cb_PSecundaria;
    private javax.swing.JComboBox<String> cb_planes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_planesS;
    private javax.swing.JSpinner sp_puntos;
    private javax.swing.JTable tabla_jt;
    // End of variables declaration//GEN-END:variables
}
