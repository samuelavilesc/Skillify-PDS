package com.pds.skillify.ui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.pds.skillify.controller.Controller;
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

	/**
	 * Añade un KeyListener al campo de texto proporcionado que actualiza dinámicamente la lista de usuarios.
	 * Cada vez que se libera una tecla, se recupera del controlador el conjunto de usuarios cuyos nombres
	 * comienzan con el texto actual del campo y se actualiza la vista correspondiente.
	 *
	 * @param usernameField Campo de texto donde el usuario escribe el nombre de usuario.
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
	 * Añade un ListSelectionListener a la lista de usuarios proporcionada que maneja el evento
	 * cuando se selecciona un usuario. Al seleccionar un usuario, se cierra la vista actual
	 * y se abre una nueva ventana mostrando los logros del usuario seleccionado.
	 *
	 * @param usersList Lista de usuarios donde se realiza la selección.
	 */
	private void handleClickOnUser(JList<User> usersList) {
		usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					view.dispose();
					new AchievementsWindow(usersList.getSelectedValue());
				}
			}
		});
	}

	/**
	 * Añade efectos visuales de hover a la lista de usuarios proporcionada. Cuando el ratón se mueve
	 * sobre un elemento, se detecta el índice del elemento y se fuerza un repintado para aplicar
	 * el efecto visual de hover. También maneja la salida del ratón de la lista para eliminar
	 * el efecto visual.
	 *
	 * @param usersList Lista de usuarios a la que se aplican los efectos visuales de hover.
	 */
	private void handleHoverOverUser(JList<User> usersList) {
		usersList.addMouseMotionListener(new MouseAdapter() {
			int hoveredIndex = -1;

			@Override
			public void mouseMoved(MouseEvent e) {
				int index = usersList.locationToIndex(e.getPoint()); 
				if (index != hoveredIndex) {
					hoveredIndex = index;
					usersList.repaint(); 
				}
			}
		});

		
		usersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				usersList.repaint();
			}
		});
	}
	/**
	 * Añade un WindowListener a la vista que gestiona el evento de cierre de la ventana.
	 * Cuando se cierra la ventana actual (por ejemplo, al hacer clic en el botón X),
	 * se abre la ventana principal (MainWindow) en el hilo de eventos de Swing.
	 */
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