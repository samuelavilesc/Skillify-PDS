package com.pds.skillify.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

public class ConfigureUserWindow extends JFrame {
    private static final long serialVersionUID = 5433790596127130763L;
    private JLabel lblImagenPerfil, lblUsuario, lblEmail, lblNuevaContrasena;
    private JTextField txtEmail;
    private JPasswordField txtNuevaContrasena;
    private JButton btnGuardar;
    private ImageIcon iconoPerfil;
    private boolean isEmailClicked = false;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 460;

    public ConfigureUserWindow() {
        initialize();
        setVisible(true);
    }

    private void initialize() {
        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Reducir separación entre elementos

        // Cargar imagen de perfil desde resources
        URL imageUrl = getClass().getResource("/user.png");
        if (imageUrl != null) {
            iconoPerfil = new ImageIcon(imageUrl);
        } else {
            System.err.println("No se encontró la imagen en resources.");
            iconoPerfil = new ImageIcon();
        }

        lblImagenPerfil = new JLabel();
        if (iconoPerfil.getImage() != null) {
            lblImagenPerfil.setIcon(new ImageIcon(iconoPerfil.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        } else {
            lblImagenPerfil.setText("No Image");
            lblImagenPerfil.setHorizontalAlignment(SwingConstants.CENTER);
        }

        // Evento para cambiar la imagen al hacer clic en el avatar
        lblImagenPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblImagenPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen de perfil");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    if (archivo != null) {
                        ImageIcon nuevaImagen = new ImageIcon(archivo.getAbsolutePath());
                        Image img = nuevaImagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        lblImagenPerfil.setIcon(new ImageIcon(img));
                    }
                }
            }
        });

        // Avatar centrado
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblImagenPerfil, gbc);

        // Nombre del usuario
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        lblUsuario = new JLabel("Usuario", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblUsuario, gbc);

        // Email - Etiqueta alineada arriba a la izquierda
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblEmail = new JLabel("Email");
        add(lblEmail, gbc);

        // Campo de texto Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        txtEmail = new JTextField("emailusuario@email.com", 20);
        add(txtEmail, gbc);

        // Evento para borrar el texto del JTextField al hacer clic la primera vez
        txtEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!isEmailClicked) {
                    txtEmail.setText("");
                    isEmailClicked = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().isEmpty()) {
                    txtEmail.setText("emailusuario@email.com");
                    isEmailClicked = false;
                }
            }
        });

        // Nueva contraseña - Etiqueta alineada arriba a la izquierda
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblNuevaContrasena = new JLabel("Nueva Contraseña");
        add(lblNuevaContrasena, gbc);

        // Campo de texto Nueva Contraseña
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        txtNuevaContrasena = new JPasswordField(20);
        add(txtNuevaContrasena, gbc);

        // Ajustar separación entre etiquetas y campos de texto
        gbc.insets = new Insets(2, 10, 5, 10); // Reducir margen superior de los JTextFields

        // Botón de Guardar con el estilo del sistema (UIManager)
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 30));
        SwingUtilities.updateComponentTreeUI(btnGuardar);
        add(btnGuardar, gbc);
    }

    // Getters para acceder a los JTextField y JPasswordField
    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JPasswordField getTxtNuevaContrasena() {
        return txtNuevaContrasena;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JLabel getLblImagenPerfil() {
        return lblImagenPerfil;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public JLabel getLblEmail() {
        return lblEmail;
    }

    public JLabel getLblNuevaContrasena() {
        return lblNuevaContrasena;
    }

}
