package com.pds.skillify.ui.controller;

import com.pds.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ConfigureUserController {
    private ConfigureUserWindow view;
    private User actualUser;
    private ImageIcon newProfilePic = null; // Nueva imagen seleccionada

    public ConfigureUserController(ConfigureUserWindow view) {
        this.view = view;
        this.actualUser = Controller.getInstance().getActualUser();
        initializeView();
        initializeHandlers();
    }

    private void initializeView() {
        if (actualUser != null) {
            view.getLblUsuario().setText(actualUser.getUsername());
            view.getLblEmailValor().setText(actualUser.getEmail());

            // Cargar la imagen de perfil del usuario
            ImageIcon avatarIcon;
            if (actualUser.getProfilePic() != null) {
                avatarIcon = new ImageIcon(actualUser.getProfilePic().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            } else {
                avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
            }
            view.getLblImagenPerfil().setIcon(avatarIcon);
        }
    }

    private void initializeHandlers() {
        // **Evento para cambiar la imagen de perfil**
        view.getLblImagenPerfil().addMouseListener(new MouseAdapter() {
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
                        view.getLblImagenPerfil().setIcon(new ImageIcon(img));
                    }
                }
            }
        
        });

        // **Evento para actualizar los datos al hacer clic en Guardar**
        view.getBtnGuardar().addActionListener(e -> actualizarUsuario());

        // **Evento para abrir la ventana MainWindow al cerrar la ventana**
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new MainWindow();
            }
        });
    }

    /**
     * Guarda la nueva contraseña y/o imagen de perfil si el usuario hizo cambios.
     */
    private void actualizarUsuario() {
        String nuevaContrasena = new String(view.getTxtNuevaContrasena().getPassword()).trim();

        // Verificar si la nueva contraseña es válida
        if (!nuevaContrasena.isEmpty() && nuevaContrasena.length() < 8) {
            JOptionPane.showMessageDialog(view, "La contraseña debe tener al menos 8 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar la contraseña si se ingresó una nueva
        if (!nuevaContrasena.isEmpty()) {
            actualUser.setPassword(nuevaContrasena);
            Controller.getInstance().updateCurrentUser();
        }

        // Actualizar foto de perfil si se seleccionó una nueva
        if (newProfilePic != null) {
            Controller.getInstance().setNewPfP(newProfilePic);
        }

        // Confirmar cambios
        JOptionPane.showMessageDialog(view, "Cambios guardados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Cerrar ventana y abrir MainWindow con los cambios aplicados
        view.dispose();
        new MainWindow();
    }
}
