package com.pds.skillify.ui;

import javax.swing.*;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;

import java.awt.*;

@SuppressWarnings("serial")
public class AchievementsWindow extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 300;
   
    private JLabel lblTitulo, lblAvatar;
    private JPanel panelCursos;

    public AchievementsWindow() {
        initialize();
        setVisible(true);
    }

    private void initialize() {
        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // **Panel superior con el título y el avatar centrados**
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // **Título "Tus logros"**
        lblTitulo = new JLabel("Tus logros");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));

        // **Cargar imagen del avatar**
        ImageIcon iconoAvatar = new ImageIcon(getClass().getResource("/user.png"));
        lblAvatar = new JLabel(new ImageIcon(iconoAvatar.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        // **Panel para centrar título y avatar juntos con separación**
        JPanel tituloAvatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        tituloAvatarPanel.add(lblTitulo);
        tituloAvatarPanel.add(lblAvatar);

        topPanel.add(tituloAvatarPanel, gbc);
        add(topPanel, BorderLayout.NORTH);

        // **Panel central con los cursos en horizontal**
        panelCursos = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 5)); // Cursos alineados en horizontal
        panelCursos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        actualizarCursos(); // Genera los cursos dinámicamente

        // **Hacer que el panel tenga scroll si hay más de 4 cursos**
        JScrollPane scrollPane = new JScrollPane(panelCursos, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null); // Eliminar bordes del scroll

        add(scrollPane, BorderLayout.CENTER);
    }

    public void actualizarCursos() {
        panelCursos.removeAll(); // Limpiar panel antes de actualizar

        ImageIcon iconoMedalla = new ImageIcon(getClass().getResource("/achievement.png"));

        for (Course curso : Controller.getInstance().getFinishedCourses()) {
            JPanel panelCurso = new JPanel(new BorderLayout());

            JLabel lblMedalla = new JLabel(new ImageIcon(iconoMedalla.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
            lblMedalla.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel lblNombreCurso = new JLabel(curso.getName(), SwingConstants.CENTER);
            lblNombreCurso.setFont(new Font("Arial", Font.BOLD, 16));

            panelCurso.add(lblMedalla, BorderLayout.NORTH);
            panelCurso.add(lblNombreCurso, BorderLayout.SOUTH);

            panelCursos.add(panelCurso);
        }

        panelCursos.revalidate();
        panelCursos.repaint();
    }

}
