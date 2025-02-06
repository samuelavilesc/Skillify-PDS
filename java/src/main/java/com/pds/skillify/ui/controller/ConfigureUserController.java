package com.pds.skillify.ui.controller;

import com.pds.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.MainWindow;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ConfigureUserController {
    private ConfigureUserWindow view;
    private ImageIcon newProfilePic = null;

    public ConfigureUserController(ConfigureUserWindow view) {
        this.view = view;
        initializeControllers();
    }

    private void initializeControllers() {
        handleClickOnProfilePicture(view.getLblImagenPerfil());
        handleClickOnSave(view.getBtnGuardar());
    }

    private void handleClickOnProfilePicture(JLabel lblImagenPerfil) {
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
    }

    private void handleClickOnSave(JButton btnGuardar) {
        btnGuardar.addActionListener(e -> actualizarUsuario());
    }

    private void actualizarUsuario() {
        User actualUser = Controller.getInstance().getActualUser();
        String nuevaContrasena = new String(view.getTxtNuevaContrasena().getPassword()).trim();

        // Validar contraseña
        if (!nuevaContrasena.isEmpty() && nuevaContrasena.length() < 8) {
            JOptionPane.showMessageDialog(view, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar la información
        if (!nuevaContrasena.isEmpty()) {
            actualUser.setPassword(nuevaContrasena);
        }
        if (newProfilePic != null) {
            actualUser.setProfilePic(newProfilePic);
        }

        JOptionPane.showMessageDialog(view, "Cambios guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Cerrar la ventana de configuración y abrir la ventana principal con los datos actualizados
        view.dispose();
        new MainWindow();
    }
}
