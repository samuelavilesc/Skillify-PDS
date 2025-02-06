package com.pds.skillify.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.RegisterWindow;

public class RegisterWindowController {

	private RegisterWindow view;

	public RegisterWindowController(RegisterWindow view) {
		this.view = view;
		initializeControllers();
	}

	private void initializeControllers() {
		handleClickOnSelectAvatar(view.getSelectAvatarButton());
		handleClickOnCreateAccount(view.getCreateAccountButton());
		handleClickOnLogin(view.getLoginLabel());
	}

	private void handleClickOnSelectAvatar(JButton selectAvatarButton) {
		selectAvatarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Skillify");
				fileChooser.setFileFilter(
						new FileNameExtensionFilter("Archivo de imagen (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"));

				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					// TODO: seguir implementando

				}
			}
		});
	}

	private void handleClickOnCreateAccount(JButton createAccountButton) {
		createAccountButton.addActionListener(e -> {

			// TODO: implementar

		});
	}

	private void handleClickOnLogin(JLabel loginLabel) {
		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				new LoginWindow();
			}
		});
	}

}
