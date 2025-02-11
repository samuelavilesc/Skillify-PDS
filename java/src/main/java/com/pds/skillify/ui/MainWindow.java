package com.pds.skillify.ui;

import com.pds.controller.Controller;
import com.pds.skillify.model.Course;

import com.pds.skillify.model.User;
import com.pds.skillify.ui.controller.MainWindowController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private static final int WIDTH = 650;
	private static final int HEIGHT = 650;

	private JList<Course> courseList;
	private DefaultListModel<Course> courseListModel;
	private JButton settingsButton, profileButton, communityButton, importButton, logoutButton;

	private JLabel welcomeLabel;
	private User actualUser;

	/**
	 * Constructor de la ventana principal
	 */
	public MainWindow() {
		actualUser = Controller.getInstance().getActualUser(); // Obtener el usuario actual
		initialize();
		new MainWindowController(this);
		setVisible(true);
	}

	/**
	 * Inicializa la ventana principal.
	 */
	private void initialize() {
		setTitle("Skillify");
		setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		// **Panel superior con el saludo y botones de perfil/configuración**
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// **Mensaje de bienvenida con el nombre del usuario**
		String welcomeMessage = (actualUser != null) ? "Bienvenido, " + actualUser.getUsername() + "!" : "Bienvenido!";
		welcomeLabel = new JLabel(welcomeMessage);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

		// **Usar la imagen de perfil del usuario si la tiene, si no, usar la
		// predeterminada**
		ImageIcon avatarIcon;
		if (actualUser != null && actualUser.getProfilePic() != null) {
			Image img = actualUser.getProfilePic().getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			avatarIcon = new ImageIcon(img);
		} else {
			avatarIcon = new ImageIcon(getClass().getResource("/user.png"));
		}

		// **Botones de perfil y configuración**
		JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		profileButton = new JButton(avatarIcon);
		settingsButton = new JButton(new ImageIcon(getClass().getResource("/settings.png")));
		communityButton = new JButton(new ImageIcon(getClass().getResource("/community_icon.png")));
		logoutButton = new JButton(new ImageIcon(getClass().getResource("/logout.png")));
		profileButton.setBorder(null);
		settingsButton.setBorder(null);
		communityButton.setBorder(null);
		profileButton.setContentAreaFilled(false);
		settingsButton.setContentAreaFilled(false);
		communityButton.setContentAreaFilled(false);
		logoutButton.setContentAreaFilled(false);

		iconsPanel.add(profileButton);
		iconsPanel.add(communityButton);
		iconsPanel.add(settingsButton);
		iconsPanel.add(logoutButton);

		// **Agregar elementos al panel superior**
		topPanel.add(welcomeLabel, BorderLayout.WEST);
		topPanel.add(iconsPanel, BorderLayout.EAST);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		// **Lista de cursos con modelo y render personalizado**
		courseListModel = new DefaultListModel<>();
		updateCoursesList();

		courseList = new JList<>(courseListModel);
		courseList.setCellRenderer(new CourseRenderer());
		courseList.setFixedCellHeight(100);

		JScrollPane scrollPane = new JScrollPane(courseList);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setSize(397, 365);

		// **Botón de importar curso**
		importButton = new JButton("Importar curso");

		// **Panel inferior con botón de importación**
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(importButton);

		// **Agregar los paneles a la ventana**
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Actualiza la lista de cursos del usuario.
	 */
	public void updateCoursesList() {
		courseListModel.clear();
		Controller.getInstance().getAllCurrentUserCourses().forEach(courseListModel::addElement);
	}

	// **Getters para que el controlador pueda acceder a los componentes**
	public JButton getSettingsButton() {
		return settingsButton;
	}

	public JButton getProfileButton() {
		return profileButton;
	}

	public JButton getImportButton() {
		return importButton;
	}

	public JButton getCommunityButton() {
		return communityButton;
	}

	public JList<Course> getCourseList() {
		return courseList;
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}

	public void setLogoutButton(JButton logoutButton) {
		this.logoutButton = logoutButton;
	}

	/**
	 * Renderizador de la lista de cursos dentro de la ventana
	 */
	private class CourseRenderer extends JPanel implements ListCellRenderer<Course> {
		private JLabel courseIcon, courseTitle, courseDescription;
		private JProgressBar progressBar;

		public CourseRenderer() {
			setLayout(new BorderLayout());
			setBorder(new EmptyBorder(10, 10, 10, 10));

			// **Cargar la imagen y escalarla**
			ImageIcon icon = new ImageIcon(getClass().getResource("/curso.png"));
			Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
			courseIcon = new JLabel(new ImageIcon(img));

			// **Etiquetas de texto**
			courseTitle = new JLabel();
			courseTitle.setFont(new Font("Arial", Font.BOLD, 14));

			courseDescription = new JLabel();
			courseDescription.setFont(new Font("Arial", Font.PLAIN, 12));
			courseDescription.setForeground(Color.DARK_GRAY);

			// **Barra de progreso**
			progressBar = new JProgressBar();
			progressBar.setForeground(new Color(50, 205, 50)); // Verde
			progressBar.setPreferredSize(new Dimension(100, 15));

			JPanel textPanel = new JPanel(new BorderLayout());
			textPanel.add(courseTitle, BorderLayout.NORTH);
			textPanel.add(courseDescription, BorderLayout.CENTER);
			textPanel.add(progressBar, BorderLayout.SOUTH);

			add(courseIcon, BorderLayout.WEST);
			add(textPanel, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends Course> list, Course course, int index,
				boolean isSelected, boolean cellHasFocus) {

			courseTitle.setText(course.getName());

			// Obtener progreso del usuario en este curso
			progressBar.setValue(Controller.getInstance().getCurrentUsersProgressInCourse(course));
			courseDescription.setText(course.getDescription());

			if (isSelected) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(Color.WHITE);
			}

			return this;
		}
	}
}
