/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Control.*;
import static Control.SharedData.carrera;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import proyectofinal.*;

/**
 *
 * @author margaroth
 */
public class AdministrarHorarios extends javax.swing.JFrame {

    Object celdas[][] = new Object[8][6];
    Object titulos[] = {"", "", "", "", "", "", ""};
    JTable tablaSecundaria = new JTable(celdas, titulos);
    int fila = 1;
    int columna = 1;
    String plan = "";
    String cuatri = "";
    String Profesor = "Elegir";
    String turno = "";
    String Hora = "";
    String dia = "";
    String claveMateria = "";
    int hrsmax = 0;
    int y = 0;
    int hrs1 = 0;
    int hrs2 = 0;
    int hrs3 = 0;
    int hrs4 = 0;
    int hrs5 = 0;
    int hrs6 = 0;
    int chrs1 = 0;
    int chrs2 = 0;
    int chrs3 = 0;
    int chrs4 = 0;
    int chrs5 = 0;
    int chrs6 = 0;
    String clvProfe = "";
    int tabla[][] = new int[8][6];
    //Variable que almacena las horas por materia de cada grupo
    Vector<Integer> VHrsMat = new Vector<Integer>();
    //variable que guarda los grupos de las base de datos guarda la clave del grupo
    Vector<String> VMaterias = new Vector<String>();
    //variables que obtendran los datos de grupo_asignacion
    Vector<String> VProfesores = new Vector<String>();
    Vector<Integer> VPHoras = new Vector<Integer>();
    Vector<String> VPDias = new Vector<String>();
    int hrs = 0;

    /**
     * Creates new form AdminsitrarDirectores
     */
    public AdministrarHorarios() {
        initComponents();

        validar val = new validar();
        LAB_nom.setText(val.tipoUsuario());
        if (SharedData.getTipoUsuario().equals("Profesor")) {
            BTN_maestros.setEnabled(false);
            BTN_carreras.setEnabled(false);
            BTN_planes.setEnabled(false);
            BTN_aulas.setEnabled(false);
            BTN_horario.setEnabled(false);
        }
        this.setLocationRelativeTo(null);
        /// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //  this.setUndecorated(true);
        // this.setVisible(true);
        turno = "Matutino";
        //listener para dar clic en la tabla y seleccionar
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                actualizarHrsMateria();
                if (jComboBox1.getSelectedItem().equals("Elegir")) {
                    JOptionPane.showMessageDialog(null, "Asegurese de elegir un grupo");
                } else if (materiasCB.getSelectedItem().equals("Elegir")) {
                    JOptionPane.showMessageDialog(null, "Asegurese de elegir una materia");
                } else if (jComboBox2.getSelectedItem().equals("Elegir")) {
                    JOptionPane.showMessageDialog(null, "Asegurese de elegir un profesor");
                } else {
                    actualizarhrs();
                    fila = jTable1.rowAtPoint(e.getPoint());
                    columna = jTable1.columnAtPoint(e.getPoint());
                    DefaultTableCellRenderer tabla1 = new DefaultTableCellRenderer();
                    if (turno.equals("Matutino")) {
                        columna = columna + 1;
                        fila = fila + 1;
                    } else {
                        columna = columna + 1;
                        fila = fila + 8;
                    }
                    try {
                        ///validando integridad
                        ValidarIntegridad();
                        if (y == 1) {
                            try {
                                connection conn = new connection();
                                String query = "DELETE FROM grupo_asignacion WHERE dia = '" + (columna) + "' AND hora = '" + (fila) + "' AND nombre_profesor = '" + Profesor + "' AND clv_materia = '" + claveMateria + "'";
                                conn.insert(query);
                            } catch (Exception ex) {
                                System.out.println("ex-1" + ex);
                            }
                            Actualizar();

                        } else if (y == 0) {
                            try {
                                //hacer insert
                                Actualizar();

                                connection conn = new connection();
                                String query = "INSERT INTO grupo_asignacion values ('" + jComboBox1.getSelectedItem() + "','" + claveMateria + "','" + (columna) + "','" + (fila) + "','" + Profesor + "')";
                                conn.insert(query);
                            } catch (Exception ex) {
                                System.out.println("ex-2" + ex);
                            }
                        } else if (y == -1) {
                            JOptionPane.showMessageDialog(null, "El grupo ya tiene esa hora asignada.");
                        } else if (y == -5) {
                            JOptionPane.showMessageDialog(null, "No se puede dar una materia mas de dos horas al dia.");
                        } else if (y == -6) {
                            JOptionPane.showMessageDialog(null, "Si elige 2 horas de materia en un mismo dia, tienen que ser seguidas.");
                        } else if (y == -7) {
                            JOptionPane.showMessageDialog(null, "El profesor ya tiene esa hora asignada.");
                        } else if (y == -8) {
                            JOptionPane.showMessageDialog(null, "El profesor ya esta dando otra materia.");
                        }
                    } catch (Exception ex) {
                        System.out.println("ex-3" + ex);
                    }
                    ///
                    Actualizar();
                    actualizarhrs();
                    actualizarHrsMateria();
                    PintarTabla();
                    actualizarIMR();
                    Actualizar();
                }
            }

        });
        try {
            String carrera = SharedData.getCarrera();
        } catch (Exception ed) {
            System.out.println("ex-4" + ed);
        }

        String[] planes = new String[100];
        int f = 0;
        try {
            connection conn = new connection();
            String query = "SELECT * FROM plan_estudios WHERE idcarrera = '" + carrera + "'";

            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                planes[f] = rs.getString("clv_plan");
                f++;
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-5" + ex);
        }
        //
        connection conn = new connection();
        for (int i = 0; i < planes.length; i++) {
            try {

                String query = "SELECT * FROM grupos where clv_plan = '" + planes[i] + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    String grupo = rs.getString("clv_grupo");
                    this.jComboBox1.addItem(grupo);
                }

            } catch (Exception ex) {
                System.out.println("ex-6" + ex);
            }
        }
        conn.desconectar();

        Actualizar();

    }

    void ValidarIntegridad() throws Exception {
        Actualizar();
        this.y = 0;
        int diahr = 0;
        int r = 0;
        int r2 = 0;
        int r3 = 0;
        //codigo que valida si ya hay dos horas puestas en la materia en un dia
        try {
            connection conn = new connection();
            String query = "SELECT * FROM grupo_asignacion WHERE clv_materia = '" + this.claveMateria + "' AND dia = '" + columna + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                r2++;
            }

            if (r2 == 2) {
                y = -5;
                //JOptionPane.showMessageDialog(null, "No se puede dar una materia mas de dos horas al dia.");
            }
            //codigo para validar que si hay 2 horas de una materia, que estas sean contiguas
            //codigo que valida si ya hay dos horas puestas en la materia en un dia

            query = "SELECT * FROM grupo_asignacion WHERE clv_materia = '" + this.claveMateria + "' AND dia = '" + columna + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                int hora = rs.getInt("hora");
                if (hora == (fila - 1) || hora == (fila + 1)) {

                } else {
                    y = -6;
                }
            }

            //codigo que valida si el profesor puede dar clase en esa hora
            r = 0;

            query = "SELECT * FROM disponibilidad WHERE espacio_tiempo = '" + fila + "' AND dia = '" + columna + "' AND clv_usuario = '" + this.clvProfe + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                r++;
            }

            if (r == 0) {
                y = -4;
                JOptionPane.showMessageDialog(null, r + " El profesor no puede dar clases a esa hora. dia: " + columna + " hora: " + fila);
            }
            ////seccion para ver que no se superen las horas de un profesor

            query = "SELECT * FROM grupo_asignacion WHERE nombre_profesor = '" + this.Profesor + "' AND dia = '" + columna + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                diahr++;
            }

            if (columna == 1) {
                if (diahr >= this.hrs1) {
                    y = -3;
                }
            }
            if (columna == 2) {
                if (diahr >= this.hrs2) {
                    y = -3;
                }
            }
            if (columna == 3) {
                if (diahr >= this.hrs3) {
                    y = -3;
                }
            }
            if (columna == 4) {
                if (diahr >= this.hrs4) {
                    y = -3;
                }
            }
            if (columna == 5) {
                if (diahr >= this.hrs5) {
                    y = -3;
                }
            }
            if (columna == 6) {
                if (diahr >= this.hrs6) {
                    y = -3;
                }
            }
            if (y == -3) {
                //JOptionPane.showMessageDialog(null, "No puede superar el limite de horas del profesor.");
            }
            //seccion para checar que los profesores no se repitan en las mismas horas

            query = "SELECT * FROM grupo_asignacion WHERE dia = '" + (columna) + "' AND hora ='" + (fila) + "' AND nombre_profesor = '" + this.Profesor + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                this.y = -7;
            }

            int h = 0, y = 0;
            /////
            //En este apartado se checa que un grupo no tenga horas repetidas

            query = "SELECT * FROM grupo_asignacion WHERE dia = '" + (columna) + "' AND hora ='" + (fila) + "' AND clv_grupo = '" + jComboBox1.getSelectedItem() + "'";

            rs = conn.conectar(query);
            while (rs.next()) {
                if (jComboBox1.getSelectedItem().equals(rs.getString("clv_grupo"))) {
                    this.y = -1;
                }
            }

            ////
            ///Validacion para que un profesor no de dos materias distintas en un mismo grupo
            String clmat = "";

            query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + this.jComboBox1.getSelectedItem() + "' AND nombre_profesor ='" + jComboBox2.getSelectedItem() + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                clmat = rs.getString("clv_materia");
                if (!this.claveMateria.equals(clmat)) {
                    this.y = -8;
                }
            }

            ///
            ///Validacion que checa que una materia impartida en un mismo grupo no se de con distintos profes, puede haber otro grupo con la misma materia y un unico profesor
            int l = 0;

            query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + this.jComboBox1.getSelectedItem() + "' AND clv_materia ='" + this.claveMateria + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                h++;
                if (!this.Profesor.equals(rs.getString("nombre_profesor"))) {
                    l = 1;
                    this.y = -2;
                }
            }
            conn.desconectar();

            if (l == 1) {
                JOptionPane.showMessageDialog(null, "Ya hay otro profesor dando esa materia.");
            }
            ////
            /////caso en que un profe, materia y hora sean los mismos, para quitarlo de la base de datos

            query = "SELECT * FROM grupo_asignacion WHERE dia = '" + (columna) + "' AND hora ='" + (fila) + "' AND nombre_profesor = '" + this.Profesor + "'";
            rs = conn.conectar(query);
            while (rs.next()) {
                h++;
                if (this.claveMateria.equals(rs.getString("clv_materia"))) {
                    this.y = 1;
                }
            }

            //
            if (h > 0) {//si es mayor a 1 significa que ya hay un resgistro con esa hora, dia y el mismo profesor, entonces esta ocupado en esa hora
                if (this.y == 1) {
                    //do nothing
                    //JOptionPane.showMessageDialog(null, "Eliminando profesor de hora...");
                } else if (this.y >= 0) {
                    // JOptionPane.showMessageDialog(null, "El profesor en cuestion ya esta ocupado a esa hora. Dia:" + (columna) + " Hora:" + (fila));
                    //throw new Exception();
                }

            }

            //se evalua que una materia no supere el maximo de horas en el grupo
            int c = 0;

            for (int j = 0; j < this.VProfesores.size(); j++) {
                if (this.VMaterias.get(j).equals(claveMateria)) {
                    c++;

                }
            }
            if (hrsmax <= c && this.y == 0) {
                JOptionPane.showMessageDialog(null, "Ha superado el limite de horas de la materia:" + claveMateria);
                throw new Exception();
            }

            //
            Actualizar();
        } catch (Exception e) {
            System.out.println("ex-nose" + e);
        }
    }

    //Metodo que obtiene los datos de la tabla grupo_asignaci'on, actualizando la tabla
    void Actualizar() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
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
            if (jComboBox3.getSelectedItem().equals("Matutino")) {
                query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + jComboBox1.getSelectedItem() + "' AND hora <= 8";
            } else {
                query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + jComboBox1.getSelectedItem() + "' AND hora >= 8";
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
            System.out.println("ex-16" + ex);
        }
        ////
        connection conn = new connection();
        for (int i = 0; i < VMaterias.size(); i++) {
            try {

                String query = "SELECT * FROM materias where clv_materia = '" + VMaterias.get(i) + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    if (jComboBox3.getSelectedItem().equals("Matutino")) {
                        model.setValueAt("<html><body>" + (this.VProfesores.get(i).trim()) + " -- " + rs.getString("nombre_materia") + "</body></html>", (-1 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                        //model.setValueAt("hola", (-1 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                    } else {
                        //model.setValueAt("<html><body><b>" + this.VProfesores.get(i) + "</b><br>" + rs.getString("nombre_materia") + "<br><br></body></html>", (-8 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                        model.setValueAt("<html><body>" + (this.VProfesores.get(i).trim()) + " -- " + rs.getString("nombre_materia") + "</body></html>", (-8 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                        //model.setValueAt("hola2", (-8 + this.VPHoras.get(i)), -1 + Integer.parseInt(this.VPDias.get(i)));
                    }
                }

            } catch (Exception ex) {
                System.out.println("ex-17" + ex);
            }
        }
        conn.desconectar();
    }

    void actualizarHrsMateria() {
        hrs = 0;
        try {
            connection conn = new connection();
            String query = "SELECT * FROM grupo_asignacion where clv_grupo = '" + this.jComboBox1.getSelectedItem() + "' AND clv_materia = '" + this.claveMateria + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                hrs++;
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-18" + ex);
        }
        hrslbl.setText("Horas disponibles de la materia: " + (this.hrsmax - hrs));
    }

    void PintarTabla() {
        borrarTabla();
        if (this.Profesor.equals("Elegir") || this.Profesor == null) {
        } else {
            //// para puntar las horas disponibles del profesor
            try {
                connection conn = new connection();
                String query = "SELECT * FROM disponibilidad where clv_usuario = '" + this.clvProfe + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    int A = rs.getInt("dia");
                    int B = rs.getInt("espacio_tiempo");
                    if (jComboBox3.getSelectedItem().equals("Matutino") && B <= 8) {
                        A = A - 1;
                        B = B - 1;
                        if (A < 0) {
                            A = 0;
                        }
                        if (B < 0) {
                            B = 0;
                        }
                        this.tabla[B][A] = 2;
                    } else if ((!jComboBox3.getSelectedItem().equals("Matutino")) && B >= 8) {
                        this.tabla[B - 8][A - 1] = 2;
                    }
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-19" + ex);
            }
            //para checar las horas ocupadas del profesor y evitar que de dos clases a la misma hora
            try {
                connection conn = new connection();
                String query = "SELECT * FROM grupo_asignacion where nombre_profesor = '" + this.Profesor + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    int A = rs.getInt("dia");
                    int B = rs.getInt("hora");
                    A = A - 1;
                    B = B - 1;
                    if (A < 0) {
                        A = 0;
                    }
                    if (B < 0) {
                        B = 0;
                    }
                    if (jComboBox3.getSelectedItem().equals("Matutino") && B > 8) {
                    } else if (jComboBox3.getSelectedItem().equals("Matutino") && B <= 8) {
                        this.tabla[B][A] = 1;
                    } else if ((!jComboBox3.getSelectedItem().equals("Matutino")) && B <= 8) {
                    } else if ((!jComboBox3.getSelectedItem().equals("Matutino")) && B >= 8) {
                        this.tabla[B - 7][A] = 1;
                    }
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-20" + ex);
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    tablaSecundaria.setValueAt(Integer.toString(this.tabla[i][j]), i, j);
                }
            }
        }
        try {
            jTable1.setDefaultRenderer(Object.class, new MiRender(tablaSecundaria));
        } catch (Exception asd) {
            System.out.println("ex-21" + asd);
        }
        Actualizar();
    }

    private void borrarTabla() {
        //Modelo de tabla.
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        //Se eliminan los colores y valores de las columnas.
        for (int i = 0; i < tb.getRowCount(); i++) {
            for (int j = 0; j < tb.getColumnCount(); j++) {
                tb.setValueAt("", i, j);
                tablaSecundaria.setValueAt("0", i, j);
                this.tabla[i][j] = 0;
            }
        }
    }

    void actualizarhrs() {
        this.chrs1 = 0;
        this.chrs2 = 0;
        this.chrs3 = 0;
        this.chrs4 = 0;
        this.chrs5 = 0;
        this.chrs6 = 0;
        try {
            connection conn = new connection();
            String query = "SELECT * FROM grupo_asignacion WHERE nombre_profesor = '" + this.Profesor + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                int dia = rs.getInt("dia");
                if (dia == 1) {
                    this.chrs1++;
                }
                if (dia == 2) {
                    this.chrs2++;
                }
                if (dia == 3) {
                    this.chrs3++;
                }
                if (dia == 4) {
                    this.chrs4++;
                }
                if (dia == 5) {
                    this.chrs5++;
                }
                if (dia == 6) {
                    this.chrs6++;
                }
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-22" + ex);
        }
        hrsProfe1.setText("Horas restantes:     " + (this.hrs1 - this.chrs1) + "   " + (this.hrs2 - this.chrs2) + "    " + (this.hrs3 - this.chrs3) + "   " + (this.hrs4 - this.chrs4) + "   " + (this.hrs5 - this.chrs5) + "   " + (this.hrs6 - this.chrs6) + "   ");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        LAB_nom = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txt7 = new javax.swing.JLabel();
        txt1 = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        txt3 = new javax.swing.JLabel();
        txt4 = new javax.swing.JLabel();
        txt5 = new javax.swing.JLabel();
        txt6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        hrsProfe = new javax.swing.JLabel();
        horasMatDisp = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        materiasCB = new javax.swing.JComboBox<>();
        hrslbl = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        txt8 = new javax.swing.JLabel();
        probabilidadRep1 = new javax.swing.JLabel();
        hrsProfe1 = new javax.swing.JLabel();
        probabilidadRep2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
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

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elegir", "Sin Grupo" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 190, -1));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel10.setText("Turno:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 80, 30));

        txt7.setText("13:00-13:54");
        jPanel3.add(txt7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, -1, -1));

        txt1.setText("7:00-7:54");
        jPanel3.add(txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        txt2.setText("7:55-8:49");
        jPanel3.add(txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, -1, 20));

        txt3.setText("8:50-9:44");
        jPanel3.add(txt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, -1, -1));

        txt4.setText("9:45-10:39");
        jPanel3.add(txt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, -1, -1));

        txt5.setText("11:10-12:04");
        jPanel3.add(txt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, -1, -1));

        txt6.setText("12:05-12:59");
        jPanel3.add(txt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, -1, -1));

        jTable1.setBackground(new java.awt.Color(254, 254, 254));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
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
        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowHeight(65);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 660, 550));

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel5.setText("Profesor:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 80, 30));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elegir" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jPanel3.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 190, -1));

        hrsProfe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hrsProfe.setText("Horas máximas:     0    ");
        jPanel3.add(hrsProfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 280, 20));

        horasMatDisp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        horasMatDisp.setText("Horas máxima de materia:");
        jPanel3.add(horasMatDisp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 240, 20));

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel18.setText("Asiganción de horas a profesores");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        materiasCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elegir" }));
        materiasCB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                materiasCBItemStateChanged(evt);
            }
        });
        jPanel3.add(materiasCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 190, -1));

        hrslbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hrslbl.setText("Horas máxima de materia:");
        jPanel3.add(hrslbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 220, 20));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Matutino", "Vespertino" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jPanel3.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 190, -1));

        txt8.setText("13:00-13:54");
        jPanel3.add(txt8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 540, -1, -1));

        probabilidadRep1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        probabilidadRep1.setText(" L    M    M    J   V   S");
        jPanel3.add(probabilidadRep1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, -1, -1));

        hrsProfe1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hrsProfe1.setText("Horas restantes:    0");
        jPanel3.add(hrsProfe1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 290, 20));

        probabilidadRep2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        probabilidadRep2.setText("Probabilidad de reprobación: 0%");
        jPanel3.add(probabilidadRep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel11.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel11.setText("Grupo:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 50, 30));

        jLabel12.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel12.setText("Materias:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 1070, 610));

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

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        this.hrs1 = 0;
        this.hrs2 = 0;
        this.hrs3 = 0;
        this.hrs4 = 0;
        this.hrs5 = 0;
        this.hrs6 = 0;
        this.chrs1 = 0;
        this.chrs2 = 0;
        this.chrs3 = 0;
        this.chrs4 = 0;
        this.chrs5 = 0;
        this.chrs6 = 0;

        materiasCB.disable();
        materiasCB.removeAllItems();
        materiasCB.addItem("Elegir");
        try {
            connection conn = new connection();
            String query = "SELECT * FROM grupos where clv_grupo = '" + jComboBox1.getSelectedItem().toString() + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                String grupo = rs.getString("clv_plan");
                cuatri = rs.getString("cuatrimestre");
                plan = grupo;
            }
            rs.close();
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-23" + ex);
        }
        //
        try {
            connection conn = new connection();
            String query = "SELECT * FROM materias where clv_plan = '" + plan + "' AND cuatrimestre ='" + cuatri + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                String grupo = rs.getString("nombre_materia");
                this.materiasCB.addItem(grupo);
            }
            rs.close();
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-24" + ex);
        }
        Actualizar();
        materiasCB.enable();
        this.actualizarhrs();
        actualizarIMR();
        jComboBox2.setSelectedItem("Elegir");
        PintarTabla();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    void actualizarIMR() {
        int k = 0;
        ///codiho que va a obtener los nombres de los profesores para poder realizar la probabilidad de reprobacion
        String[] nombres = new String[20];//se guardan los nombres de los profesores, en teoria son 7 maximo por grupo
        int tam2 = 0;
        try {
            connection conn = new connection();
            String query = "SELECT * FROM grupo_asignacion WHERE clv_grupo = '" + jComboBox1.getSelectedItem().toString() + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                k = 0;
                String prof = rs.getString("nombre_profesor");
                for (int i = 0; i < nombres.length; i++) {
                    if (nombres[i] != null) {
                        if (nombres[i].equals(prof)) {
                            k = 1;
                        }
                    }
                }
                if (k == 0) {
                    nombres[tam2] = prof;
                    tam2++;
                }
            }
            rs.close();
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-25" + ex);
        }

        int tam = 0;
        String[] claves = new String[20];
        int[] IMR = new int[20];
        ////
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i] != null) {
                try {
                    connection conn = new connection();
                    String query = "SELECT * FROM usuarios WHERE nombre_usuario = '" + nombres[i] + "'";
                    ResultSet rs = conn.conectar(query);
                    while (rs.next()) {
                        k = 0;
                        claves[i] = rs.getString("clv_usuario");
                        IMR[i] = rs.getInt("IMR");
                        tam++;
                    }
                    rs.close();
                    conn.desconectar();
                } catch (Exception ex) {
                    System.out.println("ex-26" + ex);
                }
            }
        }
        int prom = 0;

        for (int i = 0; i < tam; i++) {
            if (IMR[i] != 0) {
                prom = prom + IMR[i];
            }
        }
        if (tam != 0) {
            probabilidadRep2.setText("Probabilidad de reprobación de grupo: " + prom / tam);
        }

    }
    private void jComboBox1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox1PopupMenuWillBecomeInvisible

    }//GEN-LAST:event_jComboBox1PopupMenuWillBecomeInvisible

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange

    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int column = jTable1.getSelectedColumn();
        int row = jTable1.getSelectedRow();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        this.Profesor = "" + jComboBox2.getSelectedItem();
        this.hrs1 = 0;
        this.hrs2 = 0;
        this.hrs3 = 0;
        this.hrs4 = 0;
        this.hrs5 = 0;
        this.hrs6 = 0;
        this.chrs1 = 0;
        this.chrs2 = 0;
        this.chrs3 = 0;
        this.chrs4 = 0;
        this.chrs5 = 0;
        this.chrs6 = 0;
        try {
            connection conn = new connection();
            String query = "SELECT * FROM usuarios where nombre_usuario = '" + this.Profesor + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                clvProfe = rs.getString("clv_usuario");
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-27" + ex);
        }
        //
        try {
            connection conn = new connection();
            String query = "SELECT * FROM disponibilidad where clv_usuario = '" + clvProfe + "'";
            ResultSet rs = conn.conectar(query);
            while (rs.next()) {
                int dia = rs.getInt("dia");
                if (dia == 1) {
                    this.hrs1++;
                }
                if (dia == 2) {
                    this.hrs2++;
                }
                if (dia == 3) {
                    this.hrs3++;
                }
                if (dia == 4) {
                    this.hrs4++;
                }
                if (dia == 5) {
                    this.hrs5++;
                }
                if (dia == 6) {
                    this.hrs6++;
                }
            }
            conn.desconectar();
        } catch (Exception ex) {
            System.out.println("ex-28" + ex);
        }
        hrsProfe.setText("Horas máximas:     " + this.hrs1 + "   " + this.hrs2 + "    " + this.hrs3 + "   " + this.hrs4 + "   " + this.hrs5 + "   " + this.hrs6 + "   ");
        actualizarhrs();
        PintarTabla();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void materiasCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_materiasCBItemStateChanged
        // TODO add your handling code here:
        if (!materiasCB.isEnabled()) {
        } else {
            ///
            /* try {
                connection conn = new connection();
                String query = "SELECT * FROM materias where nombre_materia = '" + materiasCB.getSelectedItem() + "' AND clv_plan = '" + plan + "'";
                System.out.println(query);
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    hrsmax = Integer.parseInt(rs.getString("num_horas"));
                    claveMateria = rs.getString("clv_materia");
                    horasMatDisp.setText("Horas máxima de materia: " + hrsmax);
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-111111111111111111111" + ex);
            }
            String idcarrera = null;
            try {
                connection conn = new connection();
                String query = "SELECT * FROM plan_estudios where clv_plan = '" + plan + "'";
                System.out.println(query);
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    idcarrera = rs.getString("idcarrera");
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-222222222222222222222" + ex);
            }
            //
            ///Codigo que realiza el check de las horas maximas y restantes de una materia
            actualizarHrsMateria();

            ////
            this.jComboBox2.removeAllItems();
            this.jComboBox2.addItem("Elegir");
            ////Parte donde se llenan los profes
            try {
                connection conn = new connection();
                String query = "SELECT * FROM usuarios where idcarrera = '" + idcarrera + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    jComboBox2.addItem(rs.getString("nombre_usuario"));
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-" + ex);
            }*/
            try {
                connection conn = new connection();
                String query = "SELECT * FROM materias where nombre_materia = '" + materiasCB.getSelectedItem() + "' AND clv_plan = '" + plan + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    hrsmax = Integer.parseInt(rs.getString("num_horas"));
                    claveMateria = rs.getString("clv_materia");
                    horasMatDisp.setText("Horas máxima de materia: " + hrsmax);
                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-29" + ex);
            }
            actualizarHrsMateria();
            try {
                connection conn = new connection();
                String query = "SELECT * FROM materias where clv_plan = '" + plan + "' AND cuatrimestre ='" + cuatri + "' AND nombre_materia = '" + materiasCB.getSelectedItem() + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {

                    this.claveMateria = rs.getString("clv_materia");
                }
                //rs.close();
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-30" + ex);
            }
            /////
            int s = 0;
            jComboBox2.removeAllItems();
            jComboBox2.addItem("Elegir");
            String[] A = new String[100];
            try {
                connection conn = new connection();
                String query = "SELECT * FROM materia_usuario2 where clv_materia = '" + this.claveMateria + "' AND clv_plan = '" + plan + "'";
                ResultSet rs = conn.conectar(query);
                while (rs.next()) {
                    String nope = (rs.getString("clv_usuario"));
                    int AA = rs.getInt("puntos_confianza");
                    int BB = rs.getInt("puntos_director");

                    if ((AA + BB) >= 0) {
                        A[s] = nope;
                        s++;
                    }

                }
                conn.desconectar();
            } catch (Exception ex) {
                System.out.println("ex-31" + ex);
            }
            //lenado
            for (int i = 0; i < s; i++) {
                try {
                    connection conn = new connection();
                    String query = "SELECT * FROM usuarios where clv_usuario = '" + A[i] + "'";
                    ResultSet rs = conn.conectar(query);
                    while (rs.next()) {
                        jComboBox2.addItem(rs.getString("nombre_usuario"));
                    }
                    conn.desconectar();
                } catch (Exception ex) {
                    System.out.println("ex-32" + ex);
                }
            }
            Actualizar();
            actualizarhrs();
        }

        /////ESTE ES EL METODO QUE OBTIENE LOS PROFESORES POR LA TABLA MATERIA_USUARIO
        /////

    }//GEN-LAST:event_materiasCBItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        Actualizar();
        PintarTabla();
        if (jComboBox3.getSelectedItem().equals("Matutino")) {
            txt1.setText("7:00 - 7:54");
            turno = "Matutino";
            txt2.setText("7:55 - 8:49");
            txt3.setText("8:50 - 9:44");
            txt4.setText("9:45 - 10:39");
            txt5.setText("11:10 - 12:04");
            txt6.setText("12:05 - 12:59");
            txt7.setText("13:00 - 13:54");
            txt8.setText("14:00 - 13:54");
        } else {
            turno = "Vespertino";
            txt1.setText("13:00 - 13:54");
            txt2.setText("14:00 - 14:54");
            txt3.setText("14:55 - 15:49");
            txt4.setText("15:50 - 16:44");
            txt5.setText("16:45 - 17:39");
            txt6.setText("18:10 - 19:04");
            txt7.setText("19:05 - 19:59");
            txt8.setText("20:00 - 20:54");
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

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
            System.out.println("ex-33" + ex);
        } catch (InstantiationException ex) {
            System.out.println("ex-34" + ex);
        } catch (IllegalAccessException ex) {
            System.out.println("ex-35" + ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

        }
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
                try {
                    new AdministrarHorarios().setVisible(true);
                } catch (Exception ex) {
                    System.out.println("ex-36" + ex);
                }

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
    private javax.swing.JLabel horasMatDisp;
    private javax.swing.JLabel hrsProfe;
    private javax.swing.JLabel hrsProfe1;
    private javax.swing.JLabel hrslbl;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> materiasCB;
    private javax.swing.JLabel probabilidadRep1;
    private javax.swing.JLabel probabilidadRep2;
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
