/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

/**
 *
 * @author josephjoestar
 */

import Control.SharedData;
import proyectofinal.*;
import Control.connection;
import Control.validar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AdministrarPlanes extends javax.swing.JFrame {

    public AdministrarPlanes() {

        initComponents();
        menuDatos.setVisible(false);
        this.setLocationRelativeTo(null);
        getDatos();
        aceptarAgregar.setVisible(false);
        aceptarModificar.setVisible(false);
        validar val = new validar();
        LAB_nom.setText(val.tipoUsuario());
        if(SharedData.getTipoUsuario().equals("Profesor")){
            BTN_maestros.setEnabled(false);
            BTN_carreras.setEnabled(false);
            BTN_planes.setEnabled(false);
            BTN_aulas.setEnabled(false);
            BTN_horario.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        buscarNombre = new javax.swing.JTextField();
        mostrarTodo = new javax.swing.JButton();
        agregar = new javax.swing.JButton();
        menuDatos = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        nombreMD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        claveMD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nivelMD = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        carreraMD = new javax.swing.JComboBox<>();
        cancelar = new javax.swing.JButton();
        aceptarAgregar = new javax.swing.JButton();
        aceptarModificar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        buscar = new javax.swing.JButton();
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

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Clave del plan", "Nombre del plan", "Nivel academico", "Carrera"
            }
        ));
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(1).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(1).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(120);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(120);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 520, -1));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel10.setText("Administrar plan de estudio");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Ingrese nombre:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, 20));

        buscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarNombreActionPerformed(evt);
            }
        });
        jPanel3.add(buscarNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 140, -1));

        mostrarTodo.setText("Mostrar Todos");
        mostrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodoActionPerformed(evt);
            }
        });
        jPanel3.add(mostrarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, -1, -1));

        agregar.setText("Agregar");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });
        jPanel3.add(agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 230, 120, -1));

        menuDatos.setBackground(new java.awt.Color(255, 255, 255));
        menuDatos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        menuDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Nombre:");
        menuDatos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Plan de estudio");
        menuDatos.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));
        menuDatos.add(nombreMD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 218, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Clave:");
        menuDatos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));
        menuDatos.add(claveMD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 218, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Nivel academico:");
        menuDatos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 30));

        nivelMD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "ING", "LIC" }));
        nivelMD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nivelMDActionPerformed(evt);
            }
        });
        menuDatos.add(nivelMD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 210, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Carrera:");
        menuDatos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 20));

        carreraMD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "ITI", "PYMES", "MANU", "MECA", "ISA" }));
        carreraMD.setToolTipText("");
        carreraMD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carreraMDActionPerformed(evt);
            }
        });
        menuDatos.add(carreraMD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 210, -1));

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        menuDatos.add(cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 380, 100, -1));

        aceptarAgregar.setText("Aceptar");
        aceptarAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarAgregarActionPerformed(evt);
            }
        });
        menuDatos.add(aceptarAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, -1));

        aceptarModificar.setText("Aceptar");
        aceptarModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarModificarActionPerformed(evt);
            }
        });
        menuDatos.add(aceptarModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, -1));

        jPanel3.add(menuDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 360, 430));

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        jPanel3.add(modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 280, 120, -1));

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        jPanel3.add(buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, -1));

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

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        menuDatosVisible(true);
        aceptarAgregar.setVisible(true);
    }//GEN-LAST:event_agregarActionPerformed

    private void buscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarNombreActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        menuDatosVisible(false);
        aceptarAgregar.setVisible(false);
        aceptarModificar.setVisible(false);
        menuDatosLimpiar();
    }//GEN-LAST:event_cancelarActionPerformed

    private void carreraMDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carreraMDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carreraMDActionPerformed

    private void nivelMDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nivelMDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nivelMDActionPerformed

    private void mostrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodoActionPerformed
        getDatos();
    }//GEN-LAST:event_mostrarTodoActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        boolean feven = false;
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            if (tablaDatos.isRowSelected(i)) {
                feven = true;
            }
        }
        if (feven) {
            menuDatosVisible(true);
            aceptarModificar.setVisible(true);
            claveMD.setText("" + tablaDatos.getValueAt(tablaDatos.getSelectedRow(), 0));
            nombreMD.setText("" + tablaDatos.getValueAt(tablaDatos.getSelectedRow(), 1));
            if ((tablaDatos.getValueAt(tablaDatos.getSelectedRow(), 2)+"").equals("ING"))
                nivelMD.setSelectedIndex(1);
            else
                nivelMD.setSelectedIndex(2);
            carreraMD.setSelectedIndex((int)tablaDatos.getValueAt(tablaDatos.getSelectedRow(), 3));
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione algún plan de estudio de la tabla", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_modificarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        String buscar = buscarNombre.getText();
        boolean feven = true;
        try {
            connection con = new connection();
            ResultSet rs = con.conectar("SELECT * FROM plan_estudios");
            while (rs.next()) {
                if (buscar.equals(rs.getString(1))) {
                    DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
                    modelo.setRowCount(0);
                    Vector v = new Vector();
                    v.add(rs.getString(1));
                    v.add(rs.getString(2));
                    v.add(rs.getString(3));
                    v.add(rs.getInt(4));
                    modelo.addRow(v);
                    tablaDatos.setModel(modelo);
                    feven = false;
                }
            }
            if (feven) {
                getDatos();
                JOptionPane.showMessageDialog(this, "Ingrese un NOMBRE correcto", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            con.desconectar();
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_buscarActionPerformed

    private void aceptarAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarAgregarActionPerformed
        boolean feven = validar();
        if (feven) {
            String nuevoClave = "'" + claveMD.getText() + "'";
            String nuevoNombre = "'" + nombreMD.getText() + "'";
            String nuevoNivel = "'" + nivelMD.getSelectedItem() + "'";
            String nuevoCarrera = "'" + carreraMD.getSelectedIndex() + "'";
            connection con = new connection();
            con.update("INSERT INTO plan_estudios VALUES "
                    + "(" + nuevoClave + "," + nuevoNombre + "," + nuevoNivel + "," + nuevoCarrera + ")");
            JOptionPane.showMessageDialog(this, "Nuevo Plan de estudio agregado satisfactoriamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            menuDatosLimpiar();
            getDatos();
        }
    }//GEN-LAST:event_aceptarAgregarActionPerformed

    private void aceptarModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarModificarActionPerformed
        boolean feven = validar();
        if (feven) {
            String nuevoClave = "'" + claveMD.getText() + "'";
            String nuevoNombre = "'" + nombreMD.getText() + "'";
            String nuevoNivel = "'" + nivelMD.getSelectedItem() + "'";
            String nuevoCarrera = "'" + carreraMD.getSelectedIndex() + "'";
            connection con = new connection();
            con.update("UPDATE plan_estudios set "
                    + "clv_plan=" + nuevoClave + ",nombre_plan=" + nuevoNombre + ",nivel=" + nuevoNivel + ",idcarrera=" + nuevoCarrera + " "
                    + "WHERE clv_plan='" + tablaDatos.getValueAt(tablaDatos.getSelectedRow(), 0)+"'");
        }
        JOptionPane.showMessageDialog(this, "El plan de estudio se ha modificado satisfactoriamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        getDatos();
        menuDatosVisible(false);
    }//GEN-LAST:event_aceptarModificarActionPerformed

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

    public void menuDatosVisible(boolean o) {
        agregar.setVisible(!o);
        menuDatos.setVisible(o);
        modificar.setVisible(!o);
    }

    public void menuDatosLimpiar() {
        nombreMD.setText("");
        claveMD.setText("");
        nivelMD.setSelectedIndex(0);
        carreraMD.setSelectedIndex(0);
    }

    public void getDatos() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
            modelo.setRowCount(0);
            connection con = new connection();
            ResultSet rs = con.conectar("SELECT * FROM plan_estudios");
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getInt(4));
                modelo.addRow(v);
                tablaDatos.setModel(modelo);
            }
            con.desconectar();
        } catch (SQLException ex) {
        }
    }

    public boolean validar() {
        if (nombreMD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre correcto", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (claveMD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese una clave correcta", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (nivelMD.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese una clave correcta", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (carreraMD.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese una clave correcta", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdministrarPlanes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarPlanes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarPlanes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarPlanes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarPlanes().setVisible(true);
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
    private javax.swing.JButton aceptarAgregar;
    private javax.swing.JButton aceptarModificar;
    private javax.swing.JButton agregar;
    private javax.swing.JButton buscar;
    private javax.swing.JTextField buscarNombre;
    private javax.swing.JButton cancelar;
    private javax.swing.JComboBox<String> carreraMD;
    private javax.swing.JTextField claveMD;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel menuDatos;
    private javax.swing.JButton modificar;
    private javax.swing.JButton mostrarTodo;
    private javax.swing.JComboBox<String> nivelMD;
    private javax.swing.JTextField nombreMD;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables
}
