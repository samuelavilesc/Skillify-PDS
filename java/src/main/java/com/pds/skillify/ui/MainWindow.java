package com.pds.skillify.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.pds.skillify.model.Course;
import com.pds.skillify.ui.controller.MainWindowController;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    private static final int WIDTH = 550;
    private static final int HEIGHT = 650;
    private JList<Course> courseList;
    private DefaultListModel<Course> courseListModel;

    /**
     * Constructor de la ventana principal
     */
    public MainWindow() {
        initialize();
        new MainWindowController(this);
        setVisible(true);
    }

    /**
     * Inicializa la ventana principal.
     */
    private void initialize() {
        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Panel superior con el saludo y botones de perfil/configuración
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Bienvenido, usuario!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        FlowLayout fl_iconsPanel = new FlowLayout(FlowLayout.RIGHT);
        fl_iconsPanel.setHgap(15);
        JPanel iconsPanel = new JPanel(fl_iconsPanel);
        JButton profileButton = new JButton(new ImageIcon(getClass().getResource("/user.png")));
        JButton settingsButton = new JButton(new ImageIcon(getClass().getResource("/settings.png")));

        profileButton.setBorder(null);
        settingsButton.setBorder(null);
        profileButton.setContentAreaFilled(false);
        settingsButton.setContentAreaFilled(false);

        iconsPanel.add(profileButton);
        iconsPanel.add(settingsButton);

        topPanel.add(welcomeLabel, BorderLayout.WEST);
        topPanel.add(iconsPanel, BorderLayout.EAST);

        // Modelo de la lista de cursos
        courseListModel = new DefaultListModel<>();
        //TODO: usar el currentUsers.GetAllCourses () desde el controlador
        // courseListModel.addElement(new Course("Curso 1", 40,"Curso 1 muy curso curso curso curso"));
        // courseListModel.addElement(new Course("Curso 2", 70,"Curso 2 muy curso curso curso curso"));
        // courseListModel.addElement(new Course("Curso 3", 25,"Curso 3 muy curso curso curso curso"));

        // JList con render personalizado
        courseList = new JList<>(courseListModel);
        courseList.setCellRenderer(new CourseRenderer());
        courseList.setFixedCellHeight(100); // Espaciado entre elementos de la lista

        // Panel con scroll para la lista de cursos
        JScrollPane scrollPane = new JScrollPane(courseList);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(397,365);
        // Botón de importar curso
        JButton importButton = new JButton("Importar curso");

        // Panel inferior con botón de importación
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(importButton);

        // Agregar los paneles a la ventana
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }


    /**
     * Renderizador de la lista de cursos dentro de la ventana
     */
    private class CourseRenderer extends JPanel implements ListCellRenderer<Course> {
        private JLabel courseIcon;
        private JLabel courseTitle;
        private JLabel courseDescription;
        private JProgressBar progressBar;

        public CourseRenderer() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));

            // Cargar la imagen y escalarla a 48x48
            ImageIcon icon = new ImageIcon(getClass().getResource("/curso.png"));
            Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            courseIcon = new JLabel(new ImageIcon(img));

            // Etiqueta para el título del curso
            courseTitle = new JLabel();
            courseTitle.setFont(new Font("Arial", Font.BOLD, 14));

            // Etiqueta para la descripción del curso
            courseDescription = new JLabel();
            courseDescription.setFont(new Font("Arial", Font.PLAIN, 12));
            courseDescription.setForeground(Color.DARK_GRAY);

            // Barra de progreso más gruesa
            progressBar = new JProgressBar();
            progressBar.setForeground(new Color(50, 205, 50)); // Verde
            progressBar.setPreferredSize(new Dimension(100, 15));

            // Panel para agrupar título, descripción y barra de progreso
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.add(courseTitle, BorderLayout.NORTH);
            textPanel.add(courseDescription, BorderLayout.CENTER);
            textPanel.add(progressBar, BorderLayout.SOUTH);

            add(courseIcon, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Course> list,
                Course course,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            courseTitle.setText(course.getName());
            courseDescription.setText(course.getDescription()); // Nueva línea para descripción
            
            // TODO: ahora es a partir del mapa del currentUser, hacer en controlador
            // progressBar.setValue(course.getProgress());

            if (isSelected) {
                setBackground(new Color(220, 220, 220));
            } else {
                setBackground(Color.WHITE);
            }

            return this;
        }
    }

}
