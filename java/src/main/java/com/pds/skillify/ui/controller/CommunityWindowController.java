package com.pds.skillify.ui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class CommunityWindowController {

	private CommunityWindow view;
	private Controller controller;

	public CommunityWindowController(CommunityWindow view) {
		this.view = view;
		this.controller = Controller.getInstance();
		initializeControllers();
	}

	private void initializeControllers() {
		handleTypingUsernameField(view.getUsernameField());
		handleClickOnUser(view.getUsersList());
	}

	private void handleTypingUsernameField(JTextField usernameField) {
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Set<User> matchingUsers = controller.getUsersStartingWith(usernameField.getText());
				view.updateUserList(matchingUsers);
			}
		});
	}

	private void handleClickOnUser(JList<User> usersList) {
		usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					new AchievementsWindow(new ArrayList<>(), usersList.getSelectedValue());
				}
			}
		});
	}

}
