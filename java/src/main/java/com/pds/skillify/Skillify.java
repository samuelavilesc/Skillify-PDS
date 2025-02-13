package com.pds.skillify;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.pds.skillify.ui.LoginWindow;

public class Skillify {

    private final static int BORDER_ROUNDNESS = 30;
    private final static Color GREEN_COLOR = new Color(0x80D855);
    private final static String FONT_RESOURCE_PATH = "/Roboto-Regular.ttf";

    /*
     * Lanzador de la aplicación.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Font robotoFont = loadRobotoFromResources();

                    // Aplicar la fuente globalmente en UIManager
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

                    // Aplicar bordes redondeados a todos los componentes
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
     * Carga la fuente Roboto desde los recursos interno
     */
    private static Font loadRobotoFromResources() {
        try {
            InputStream fontStream = Skillify.class.getResourceAsStream(FONT_RESOURCE_PATH);
            if (fontStream == null) {
                throw new IOException("No se pudo encontrar la fuente en resources.");
            }

            Font roboto = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 14);

            // Registrar la fuente en el sistema para que la reconozca Java
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(roboto);

            return roboto;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, 14);
        }
    }
}
