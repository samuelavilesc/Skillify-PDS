package com.pds.skillify.ui;

import com.pds.controller.Controller;
import com.pds.skillify.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ConfigureUserWindow extends JFrame {
    private static final long serialVersionUID = 5433790596127130763L;
    private JLabel lblImagenPerfil, lblUsuario, lblEmail, lblEmailValor, lblNuevaContrasena;
    private JPasswordField txtNuevaContrasena;
    private JButton btnGuardar;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 460;
    private User actualUser;
    private ImageIcon newProfilePic = null; // Nueva imagen de perfil seleccionada

    public ConfigureUserWindow() {
        actualUser = Controller.getInstance().getActualUser(); // Obtener usuario logueado

        setTitle("Skillify");
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Evento para abrir MainWindow al cerrar esta ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainWindow(); // Se abre de nuevo la ventana principal con los datos actualizados
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // **Imagen de perfil**
        ImageIcon avatarIcon;
        if (actualUser != null && actualUser.getProfilePic() != null) {
            Image img = actualUser.getProfilePic().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            avatarIcon = new ImageIcon(img);
        } else {
            avatarIcon = new ImageIcon(getClass().getResource("/user.png")); // Imagen predeterminada
        }

        lblImagenPerfil = new JLabel(avatarIcon);
        lblImagenPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Evento para cambiar la imagen al hacer clic en el avatar
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
                        newProfilePic = new ImageIcon(archivo.getAbsolutePath());
                        Image img = newProfilePic.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        lblImagenPerfil.setIcon(new ImageIcon(img));
                    }
                }
            }
        });

        // **Colocar la imagen de perfil**
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblImagenPerfil, gbc);

        // **Nombre del usuario**
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        lblUsuario = new JLabel((actualUser != null) ? actualUser.getUsername() : "Usuario", SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblUsuario, gbc);

        // **Email - Etiqueta**
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        lblEmail = new JLabel("Email:");
        add(lblEmail, gbc);

        // **Email - Valor (No editable)**
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblEmailValor = new JLabel((actualUser != null) ? actualUser.getEmail() : "email@dominio.com");
        lblEmailValor.setFont(new Font("Arial", Font.PLAIN, 14));
        add(lblEmailValor, gbc);

        // **Nueva contraseña - Etiqueta**
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        lblNuevaContrasena = new JLabel("Nueva Contraseña:");
        add(lblNuevaContrasena, gbc);

        // **Campo de texto Nueva Contraseña**
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        txtNuevaContrasena = new JPasswordField(20);
        add(txtNuevaContrasena, gbc);

        // **Botón de Guardar**
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 30));
        btnGuardar.addActionListener(e -> actualizarUsuario());
        add(btnGuardar, gbc);

        setVisible(true); // Hacer visible la ventana
    }

    /**
     * Guarda la nueva contraseña y/o imagen de perfil si el usuario hizo cambios.
     */
    private void actualizarUsuario() {
        String nuevaContrasena = new String(txtNuevaContrasena.getPassword()).trim();

        // Verificar si se ingresó una nueva contraseña válida
        if (!nuevaContrasena.isEmpty() && nuevaContrasena.length() < 8) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar contraseña si el usuario ingresó una nueva
        if (!nuevaContrasena.isEmpty()) {
            actualUser.setPassword(nuevaContrasena);
        }

        // Actualizar foto de perfil si el usuario seleccionó una nueva
        if (newProfilePic != null) {
            actualUser.setProfilePic(newProfilePic);
        }

        // Confirmar cambios
        JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Cerrar ventana y abrir MainWindow con los cambios aplicados
        dispose();
        new MainWindow();
    }

    // Getters
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

    public JLabel getLblEmailValor() {
        return lblEmailValor;
    }

    public JLabel getLblNuevaContrasena() {
        return lblNuevaContrasena;
    }
}
