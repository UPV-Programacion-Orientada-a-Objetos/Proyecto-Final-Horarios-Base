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
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author josephjoestar
 */
public class AdministrarGrupos extends javax.swing.JFrame {
//Variable que almacena las horas por materia de cada grupo
    Vector<Integer> VHrsMat = new Vector<Integer>();
    //variable que guarda los grupos de las base de datos guarda la clave del grupo
    Vector<String> VMaterias = new Vector<String>();
    //variables que obtendran los datos de grupo_asignacion
    Vector<String> VProfesores = new Vector<String>();
    Vector<Integer> VPHoras = new Vector<Integer>();
    Vector<String> VPDias = new Vector<String>();
    /**
     * Creates new form AdminsitrarDirectores
     */
    public AdministrarGrupos() {
        initComponents();
        this.setLocationRelativeTo(null);
        DefaultTableModel model = (DefaultTableModel) tabla_jt.getModel();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                model.setValueAt("", i, j);
            }
        }
        tabla_jt.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla_jt.rowAtPoint(e.getPoint());
                int columna = tabla_jt.columnAtPoint(e.getPoint());
                DefaultTableCellRenderer tabla1 = new DefaultTableCellRenderer();
            }
        });
        agregar();
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

    
    void ActualizarHorario() {
        DefaultTableModel model = (DefaultTableModel) tabla_jt.getModel();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                model.setValueAt("", i, j);
            }
        }

        this.VProfesores.removeAllElements();
        this.VMaterias.removeAllElements();
        this.VPDias.removeAllElements();
        this.VPHoras.removeAllElements();
        try {
            connection conn = new connection();
            String query = "";
            if (CBGrupo.getSelectedItem().equals("Matutino")) {
                query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + cb_grupos.getSelectedItem() + "' AND hora <= 8";
            } else {
                query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + cb_grupos.getSelectedItem() + "' AND hora >= 8";
            }

            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                this.VProfesores.add(rs.getString("nombre_profesor"));
                this.VPDias.add(rs.getString("dia"));
                this.VPHoras.add(rs.getInt("hora"));
                this.VMaterias.add(rs.getString("clv_materia"));
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-" + ex);
        }
        ////
        for (int i = 0; i < VMaterias.size(); i++) {
            try {
                connection conn = new connection();
                String query = "SELECT * FROM materias where clv_materia = '" + VMaterias.get(i) + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    if (CBGrupo.getSelectedItem().equals("Matutino")) {
                        model.setValueAt(rs.getString("nombre_materia") + this.VProfesores.get(i), (-1 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                    } else {
                        model.setValueAt(rs.getString("nombre_materia") + this.VProfesores.get(i), (-8 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                    }
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-" + ex);
            }
        }
    }
    
    void agregar() {
        cb_grupos.removeAllItems();
        cb_grupos.addItem("Elegir");
        try {
            connection conn = new connection();
            ResultSet rs = conn.conectar("SELECT clv_plan FROM plan_estudios WHERE idcarrera = '" + SharedData.getCarrera() + "'");
            while (rs.next()) {
                ResultSet rs2 = conn.conectar("SELECT clv_grupo FROM grupos WHERE clv_plan = '" + rs.getString("clv_plan") + "'");
                while (rs2.next()) {
                    String clave = rs2.getString("clv_grupo");
                    cb_grupos.addItem(clave);
                }
            }

            conn.desconectar();
        } catch (Exception e) {

        }
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
        cb_grupos = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        BTN_agregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_jt = new javax.swing.JTable();
        txt1 = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        txt3 = new javax.swing.JLabel();
        txt4 = new javax.swing.JLabel();
        txt5 = new javax.swing.JLabel();
        txt6 = new javax.swing.JLabel();
        txt7 = new javax.swing.JLabel();
        txt8 = new javax.swing.JLabel();
        CBGrupo = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
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

        cb_grupos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elegir", "Sin Grupo ", " " }));
        cb_grupos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_gruposItemStateChanged(evt);
            }
        });
        cb_grupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_gruposActionPerformed(evt);
            }
        });
        jPanel3.add(cb_grupos, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 180, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Turno");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 50, 20));

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel18.setText("Administrar Grupo");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        BTN_agregar.setLabel("Agregar");
        BTN_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_agregarActionPerformed(evt);
            }
        });
        jPanel3.add(BTN_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 100, -1));

        tabla_jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0,0", "1,0", "2,0", "3,0", "4,0", null},
                {"0,1", "1,1", "2,1", "3,1", "4,1", null},
                {"0,2", "1,2", "2,2", "3,2", "4,2", null},
                {"0,3", "1,3", "2,3", "3,3", "4,3", null},
                {"0,4", "1,4", "2,4", "3,4", "4,4", null},
                {"0,5", "1,5", "2,5", "3,5", "4,5", null},
                {"0,6", "1,6", "5,6", "3,6", "4,6", null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sabado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_jt.setCellSelectionEnabled(true);
        tabla_jt.setRowHeight(65);
        tabla_jt.setUpdateSelectionOnSort(false);
        tabla_jt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabla_jtMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_jtMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_jt);
        tabla_jt.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tabla_jt.getColumnModel().getColumnCount() > 0) {
            tabla_jt.getColumnModel().getColumn(0).setResizable(false);
            tabla_jt.getColumnModel().getColumn(1).setResizable(false);
            tabla_jt.getColumnModel().getColumn(2).setResizable(false);
            tabla_jt.getColumnModel().getColumn(3).setResizable(false);
            tabla_jt.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 880, 550));

        txt1.setText("7:00-7:54");
        jPanel3.add(txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        txt2.setText("7:55-8:49");
        jPanel3.add(txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        txt3.setText("8:50-9:44");
        jPanel3.add(txt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, -1, -1));

        txt4.setText("9:45-10:39");
        jPanel3.add(txt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        txt5.setText("11:10-12:04");
        jPanel3.add(txt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        txt6.setText("12:05-12:59");
        jPanel3.add(txt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

        txt7.setText("13:00-13:54");
        jPanel3.add(txt7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, -1));

        txt8.setText("14:00-14:54");
        jPanel3.add(txt8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, -1, -1));

        CBGrupo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matutino", "Vespertino" }));
        CBGrupo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBGrupoItemStateChanged(evt);
            }
        });
        jPanel3.add(CBGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 180, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Grupo");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 50, 20));

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

    private void BTN_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_agregarActionPerformed
        // TODO add your handling code here:
        DarDeAltaGrupo abrir = new DarDeAltaGrupo();
        abrir.setVisible(true);
        abrir.setLocationRelativeTo(null);
        abrir.setResizable(false);
        this.dispose();
    }//GEN-LAST:event_BTN_agregarActionPerformed

    private void cb_gruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_gruposActionPerformed


    }//GEN-LAST:event_cb_gruposActionPerformed

    private void tabla_jtMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_jtMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_tabla_jtMouseReleased

    private void tabla_jtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_jtMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tabla_jtMouseClicked

    private void cb_gruposItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_gruposItemStateChanged
        Actualizar();
        ActualizarHorario();
    }//GEN-LAST:event_cb_gruposItemStateChanged

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

    private void CBGrupoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBGrupoItemStateChanged
if (CBGrupo.getSelectedItem().equals("Matutino")) {
            txt1.setText("7:00 - 7:54");
            txt2.setText("7:55 - 8:49");
            txt3.setText("8:50 - 9:44");
            txt4.setText("9:45 - 10:39");
            txt5.setText("11:10 - 12:04");
            txt6.setText("12:05 - 12:59");
            txt7.setText("13:00 - 13:54");
            txt8.setText("14:00 - 13:54");
        } else {
            txt1.setText("13:00 - 13:54");
            txt2.setText("14:00 - 14:54");
            txt3.setText("14:55 - 15:49");
            txt4.setText("15:50 - 16:44");
            txt5.setText("16:45 - 17:39");
            txt6.setText("18:10 - 19:04");
            txt7.setText("19:05 - 19:59");
            txt8.setText("20:00 - 20:54");
        }        // TODO add your handling code here:
        Actualizar();
        ActualizarHorario();        // TODO add your handling code here:
    }//GEN-LAST:event_CBGrupoItemStateChanged

    void Actualizar() {
        int a = 2;
        DefaultTableModel model = (DefaultTableModel) tabla_jt.getModel();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                model.setValueAt("", i, j);
            }
        }

        try {
            connection conn = new connection();

            ResultSet rs = conn.conectar("SELECT * FROM grupo_asignacion where clv_grupo = '" + cb_grupos.getSelectedItem() + "'");
            while (rs.next()) {
                String dia = rs.getString("dia");
                int hora = rs.getInt("hora");
                if (hora <= 8 && CBGrupo.getSelectedItem().equals("Matutino")) {
                    a = 0;
                    model.setValueAt(rs.getString("clv_materia") + rs.getString("nombre_profesor"), (-1 + rs.getInt("hora")), -1 + Integer.parseInt(rs.getString("dia")));
                } else if (hora >= 8 && (!CBGrupo.getSelectedItem().equals("Matutino"))) {
                    a = 1;
                    model.setValueAt(rs.getString("clv_materia") + rs.getString("nombre_profesor"), (-8 + rs.getInt("hora")), -1 + Integer.parseInt(rs.getString("dia")));
                }

            }
            conn.desconectar();
        } catch (Exception e) {
            System.out.println("EEEEXX" + e);
        }
    }
    
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
            java.util.logging.Logger.getLogger(AdministrarGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarGrupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarGrupos().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_agregar;
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
    private javax.swing.JComboBox<String> CBGrupo;
    private javax.swing.JLabel LAB_nom;
    private javax.swing.JComboBox<String> cb_grupos;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tabla_jt;
    private javax.swing.JLabel txt1;
    private javax.swing.JLabel txt2;
    private javax.swing.JLabel txt3;
    private javax.swing.JLabel txt4;
    private javax.swing.JLabel txt5;
    private javax.swing.JLabel txt6;
    private javax.swing.JLabel txt7;
    private javax.swing.JLabel txt8;
    // End of variables declaration//GEN-END:variables
}
