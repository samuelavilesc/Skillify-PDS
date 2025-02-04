package com.pds.skillify;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.pds.skillify.ui.LoginWindow;

public class Skillify {

	private final static int BORDER_ROUNDNESS = 30;
	private final static Color GREEN_COLOR = new Color(0x80D855);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Font font = new Font("Segoe UI", Font.PLAIN, 14);
					Font buttonFont = new Font("Segoe UI", Font.PLAIN, 12);

					UIManager.put("Label.font", font);
					UIManager.put("TextField.font", font);
					UIManager.put("PasswordField.font", font);
					UIManager.put("TextArea.font", font);
					UIManager.put("ComboBox.font", font);
					UIManager.put("CheckBox.font", font);
					UIManager.put("RadioButton.font", font);
					UIManager.put("Button.font", buttonFont);

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

}
