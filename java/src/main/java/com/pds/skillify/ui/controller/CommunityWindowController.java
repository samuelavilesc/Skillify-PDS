package com.pds.skillify.ui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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

	private void initializeControllers() {
		handleTypingUsernameField(view.getUsernameField());
		handleClickOnUser(view.getUsersList());
		handleHoverOverUser(view.getUsersList());
		handleClosingWindow();
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
					view.dispose();
					new AchievementsWindow(new ArrayList<>(), usersList.getSelectedValue());
				}
			}
		});
	}

	private void handleHoverOverUser(JList<User> usersList) {
		usersList.addMouseMotionListener(new MouseAdapter() {
			int hoveredIndex = -1;

			@Override
			public void mouseMoved(MouseEvent e) {
				int index = usersList.locationToIndex(e.getPoint()); // Get index of hovered cell
				if (index != hoveredIndex) {
					hoveredIndex = index;
					usersList.repaint(); // Force repaint to apply the new hover effect
				}
			}
		});

		// Add mouse listener to reset hover when mouse exits the list
		usersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				usersList.repaint();
			}
		});
	}

	private void handleClosingWindow() {
		view.addWindowListener(new WindowAdapter() {
			// Botón X
			@Override
			public void windowClosing(WindowEvent e) {
				SwingUtilities.invokeLater(() -> {
					new MainWindow();
				});
			}
			

		});

	}

}
