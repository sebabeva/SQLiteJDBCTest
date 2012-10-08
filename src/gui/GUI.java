package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

    private JFrame frame;

    private boolean estado;

    private ResultSet resultado;

    private Statement consulta;

    private Connection conexion;

    private JTextField textField;

    private JTextField textField_1;

    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
        this.estado = false;

        try {
            // Carga del driver de SQLite
            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            // Creo una conexion hacia la base de datos
            conexion = DriverManager.getConnection("jdbc:sqlite:db/alumno.db");
            consulta = conexion.createStatement();

            // Recorro el conjunto de resultados y muestro tupla por tupla

            // Cerramos las conexiones

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Estado:" + e.getSQLState());
        }

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
            }
        });
        frame.setBounds(100, 100, 200, 190);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textField = new JTextField();
        textField.setBounds(75, 11, 86, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(75, 42, 86, 20);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(75, 73, 86, 20);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel = new JLabel("DNI:");
        lblNewLabel.setBounds(10, 14, 46, 14);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(10, 45, 46, 14);
        frame.getContentPane().add(lblApellido);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 76, 46, 14);
        frame.getContentPane().add(lblNombre);

        JButton btnLeer = new JButton("Leer");

        btnLeer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                try {
                    if (estado == false)
                        resultado = consulta
                                .executeQuery("select * from alumno;");
                    estado = true;

                    if (resultado.next()) {
                        textField.setText(String.valueOf(resultado.getInt(1)));
                        textField_1.setText(String.valueOf(resultado
                                .getString("apellido")));
                        textField_2.setText(String.valueOf(resultado
                                .getString("nombre")));
                    } else {
                        resultado.close();
                        consulta.close();
                        conexion.close();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLeer.setBounds(72, 118, 89, 23);
        frame.getContentPane().add(btnLeer);
    }
}
