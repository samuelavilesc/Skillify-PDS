package com.pds.skillify;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.pds.skillify.ui.AchievementsWindow;
import com.pds.skillify.ui.ConfigureUserWindow;
import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skillify {

    private final static int BORDER_ROUNDNESS = 30;
    private final static Color GREEN_COLOR = new Color(0x80D855);
    private final static String FONT_RESOURCE_PATH = "/Roboto-Regular.ttf"; // Ruta en resources

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Cargar la fuente desde resources
                    Font robotoFont = loadRobotoFromResources();

                    // Aplicar la fuente globalmente
                    UIManager.put("Label.font", robotoFont);
                    UIManager.put("TextField.font", robotoFont);
                    UIManager.put("PasswordField.font", robotoFont);
                    UIManager.put("TextArea.font", robotoFont);
                    UIManager.put("ComboBox.font", robotoFont);
                    UIManager.put("CheckBox.font", robotoFont);
                    UIManager.put("RadioButton.font", robotoFont);
                    UIManager.put("Button.font", robotoFont);

                    UIManager.put("Button.foreground", Color.WHITE);
                    UIManager.put("Button.background", GREEN_COLOR);

                    UIManager.put("Button.arc", BORDER_ROUNDNESS);
                    UIManager.put("Component.arc", BORDER_ROUNDNESS);
                    UIManager.put("TextComponent.arc", BORDER_ROUNDNESS);
                    UIManager.put("ProgressBar.arc", BORDER_ROUNDNESS);

                    UIManager.setLookAndFeel(new FlatMacLightLaf());

                    
                

                    new LoginWindow();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Carga la fuente Roboto desde los recursos internos (resources/)
     */
    private static Font loadRobotoFromResources() {
        try {
            // Obtener la fuente como un stream desde los recursos
            InputStream fontStream = Skillify.class.getResourceAsStream(FONT_RESOURCE_PATH);
            if (fontStream == null) {
                throw new IOException("No se pudo encontrar la fuente en resources.");
            }

            // Crear la fuente desde el InputStream y establecer tamaño 14
            Font roboto = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 14);

            // Registrar la fuente en el sistema para que la reconozca Java
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(roboto);

            return roboto;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 14); // Fallback en caso de error
        }
    }
}
