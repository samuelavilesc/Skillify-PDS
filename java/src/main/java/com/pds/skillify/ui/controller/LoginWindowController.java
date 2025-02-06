package com.pds.skillify.ui.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.pds.skillify.ui.LoginWindow;
import com.pds.skillify.ui.RegisterWindow;

public class LoginWindowController {

	private LoginWindow view;
	
	public LoginWindowController(LoginWindow view) {
		this.view = view;
		initializeControllers();
	}

	private void initializeControllers() {
		handleClickOnRegister(view.getRegisterLabel());
		handleClickOnForgotPassword(view.getForgotPasswordLabel());
		handleClickOnLogin(view.getLoginButton());
	}

	private void handleClickOnRegister(JLabel registerLabel) {
		registerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				new RegisterWindow();
			}
		});
	}

	private void handleClickOnForgotPassword(JLabel forgotPasswordLabel) {
		forgotPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// TODO: implementar

			}
		});
	}

	private void handleClickOnLogin(JButton loginButton) {
		loginButton.addActionListener(e -> {
			
			//TODO: implementar
			
		});
	}
	
}
