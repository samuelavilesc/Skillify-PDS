package com.pds.skillify.ui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.pds.controller.Controller;
import com.pds.skillify.model.User;
import com.pds.skillify.ui.AchievementsWindow;
import com.pds.skillify.ui.CommunityWindow;
import com.pds.skillify.ui.MainWindow;

public class CommunityWindowController {

	private CommunityWindow view;
	private Controller controller;

	public CommunityWindowController(CommunityWindow view) {
		this.view = view;
		this.controller = Controller.getInstance();
		initializeControllers();
	}

	/**
	 * Inicializa los controladores de eventos.
	 */
	private void initializeControllers() {
		handleTypingUsernameField(view.getUsernameField());
		handleClickOnUser(view.getUsersList());
		handleWindowClosing();
	}

	/**
	 * Maneja la escritura en el campo de usuario y actualiza la lista en tiempo real.
	 */
	private void handleTypingUsernameField(JTextField usernameField) {
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Set<User> matchingUsers = controller.getUsersStartingWith(usernameField.getText());
				view.updateUserList(matchingUsers);
			}
		});
	}

	/**
	 * Maneja la selección de un usuario en la lista y muestra su ventana de logros.
	 */
	private void handleClickOnUser(JList<User> usersList) {
		usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && usersList.getSelectedValue() != null) {
					new AchievementsWindow(new ArrayList<>(), usersList.getSelectedValue());
				}
			}
		});
	}

	/**
	 * Maneja el cierre de la ventana y abre `MainWindow` cuando `CommunityWindow` se cierra.
	 */
	private void handleWindowClosing() {
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainWindow main = new  MainWindow(); // Abre MainWindow al cerrar CommunityWindow
				main.setVisible(true);
			}
		});
	}
}
