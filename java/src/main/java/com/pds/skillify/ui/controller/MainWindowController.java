package com.pds.skillify.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;

import com.pds.skillify.model.Course;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.MainWindow;

public class MainWindowController {

    private MainWindow view;

    public MainWindowController(MainWindow view) {
        this.view = view;
        initializeControllers();
    }

    /**
     * Inicializa los controladores de eventos.
     */
    private void initializeControllers() {
        handleClickOnSettings(view.getSettingsButton());
        handleClickOnProfile(view.getProfileButton());
        handleClickOnImportCourse(view.getImportButton());
        handleClickOnCourse(view.getCourseList());
    }

    /**
     * Maneja el clic en el botón de configuración.
     */
    private void handleClickOnSettings(JButton settingsButton) {
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserConfiguration();
            }
        });
    }

    /**
     * Maneja el clic en el botón de perfil (puede abrir el perfil del usuario en el futuro).
     */
    private void handleClickOnProfile(JButton profileButton) {
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implementar acción para ver perfil de usuario
            }
        });
    }

    /**
     * Maneja el clic en el botón de importar curso.
     */
    private void handleClickOnImportCourse(JButton importButton) {
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implementar la importación de cursos
            }
        });
    }

    /**
     * Maneja la selección de un curso en la lista.
     */
    private void handleClickOnCourse(JList<Course> courseList) {
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Course selectedCourse = courseList.getSelectedValue();
                if (selectedCourse != null) {
                    openCourseDetails(selectedCourse);
                }
            }
        });
    }

    /**
     * Abre la ventana de configuración del usuario y cierra la ventana principal.
     */
    private void openUserConfiguration() {
       

        ConfigureUserWindow configWindow = new ConfigureUserWindow();
        configWindow.setVisible(true); // Asegurar que la ventana se muestra correctamente

        view.dispose(); // Cierra la ventana principal
    }


    /**
     * Abre los detalles del curso seleccionado.
     */
    private void openCourseDetails(Course course) {
        // TODO: Implementar la apertura de la ventana de detalles del curso
        System.out.println("Abriendo detalles del curso: " + course.getName());
    }
}
